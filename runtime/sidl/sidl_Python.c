/*
 * File:        sidl_Python.c
 * Revision:    @(#) $Revision: 7513 $
 * Date:        $Date: 2013-01-23 08:32:10 -0800 (Wed, 23 Jan 2013) $
 * Description: Initialize a Python language interpretter
 *
 */

#include <string.h>
#ifndef PYTHON_DISABLED
#include "Python.h"
#else
typedef int PyGILState_STATE;
#endif
#include "sidl_Python.h"
#include "babel_config.h"
#ifndef included_sidl_DLL_h
#include "sidl_DLL.h"
#endif
#ifndef included_sidl_Loader_h
#include "sidl_Loader.h"
#endif
#ifndef included_sidl_String_h
#include "sidl_String.h"
#endif
#include <stdio.h>
#include <stdlib.h>
#include "sidlOps.h"
#include "sidlCas.h"
#ifdef HAVE_PTHREAD
#include <pthread.h>
#ifndef CAS32
static pthread_mutex_t pc_mutex = PTHREAD_MUTEX_INITIALIZER;
#endif

#ifdef HAVE_UNISTD_H
#include <unistd.h>
#endif
#endif

static volatile int32_t g_python_object_count = 0;
static void (*g_python_shutdown)(void) = NULL;
static PyGILState_STATE (*g_python_ensure)(void) = NULL;
static void *(*g_python_savethread)(void) = NULL;
static void (*g_python_restorethread)(void *) = NULL;
static void *main_python_thread_state = NULL;

static void
sidl_Python_shutdown(void *ignored)
{
  if (g_python_shutdown) {
    PyGILState_STATE _gstate;
#ifdef WITH_THREAD
    /* Acquire GIL once again */
    if (main_python_thread_state && g_python_restorethread) {
      (*g_python_restorethread)(main_python_thread_state);
      sidl_Python_LogRelock(__func__, __FILE__, __LINE__);
    }
#ifdef HAVE_UNISTD_H
    {
      /* give Java or other threads time to finalize objects */
      int time_slept = 0;
      void *tstate = NULL;
      if (g_python_savethread) {
        tstate = (*g_python_savethread)();
        sidl_Python_LogUnlock(__func__, __FILE__, __LINE__);
      }

      while ((g_python_object_count > 0) && (time_slept < 2000)) {
        usleep(100);
        time_slept += 100;
      }
      
      if (g_python_restorethread) {
        (*g_python_restorethread)(tstate);
	sidl_Python_LogRelock(__func__, __FILE__, __LINE__);
      }
    }
#endif
#endif
    if (g_python_object_count <= 0) {
      (*g_python_shutdown)();
      sidl_Python_LogGILRelease(__func__, __FILE__, __LINE__, _gstate);
    }
    else {
      fprintf(stderr, "Not shutting down Python due to lingering references: %ld\n", 
              (long)g_python_object_count);
    }
  }
}

static int
FindAndCallSymbols(sidl_DLL dll, const char *url)
{
  static const char initName[] = "Py_Initialize";
  static const char setName[] = "Py_SetProgramName";
  static const char checkInit[] = "Py_IsInitialized";
  static const char finalName[] = "Py_Finalize";
  static const char ensureName[] = "PyGILState_Ensure";
  static const char saveThreadName[] = "PyEval_SaveThread";
  static const char restoreThreadName[] = "PyEval_RestoreThread";
  static const char argvName[] = "PySys_SetArgv";
  static const char threadCheckName[] = "PyEval_ThreadsInitialized";
  static const char threadInitName[] = "PyEval_InitThreads";
  /* static const char threadReleaseName[] = "PyEval_ReleaseLock"; */
  sidl_BaseInterface throwaway_exception; /*TODO: a way to not throw these away? */
  int result = 0;
  int threadsInitialized = 0;
  int ownLock = 0;
  int (*check)(void);
  void (*pyinit)(void);
  void (*pysetname)(const char *);
  check = (int (*)(void))
    sidl_DLL_lookupSymbol(dll, threadCheckName, &throwaway_exception);
  if (check) {
    threadsInitialized = (*check)();
    if (!threadsInitialized) {
      pyinit = (void (*)(void))
        sidl_DLL_lookupSymbol(dll, threadInitName, &throwaway_exception);
      if (pyinit) {
        (*pyinit)();
        ownLock = 1;
      }
    }
  }
  check = (int (*)(void))
    sidl_DLL_lookupSymbol(dll, checkInit, &throwaway_exception);
  if (check) {
    result = (*check)();
  }
  else {
    if (url) {
      fprintf(stderr, "babel: unable to locate %s in library %s\n",
              checkInit, url);
    }
  }
  if (!result) {
    pysetname = (void (*)(const char *))
      sidl_DLL_lookupSymbol(dll, setName, &throwaway_exception);
    if (pysetname) {
      (*pysetname)(PYTHON_EXECUTABLE_NAME);
    }
    pyinit = (void (*)(void))
      sidl_DLL_lookupSymbol(dll, initName,&throwaway_exception);
    if (pyinit) {
      void (*setargv)(int, char **);
      static char *fake_args[] = { "" };
      (*pyinit)();
      sidl_Python_LogControlling(__func__, __FILE__, __LINE__);
      result = 1;

      g_python_shutdown = 
        (void (*)(void))sidl_DLL_lookupSymbol(dll, finalName,
                                              &throwaway_exception);
      if (g_python_shutdown) {
        sidl_atexit(sidl_Python_shutdown, NULL);
#if PY_VERSION_HEX >= 0x02040000
        g_python_ensure =
          (PyGILState_STATE (*)(void))
          sidl_DLL_lookupSymbol(dll, ensureName, &throwaway_exception);
        if (g_python_ensure) {
          g_python_savethread =
            (void *(*)(void))
            sidl_DLL_lookupSymbol(dll, saveThreadName, &throwaway_exception);
          if (g_python_savethread) {
            g_python_restorethread =
              (void (*)(void *))
              sidl_DLL_lookupSymbol(dll, restoreThreadName, 
                                    &throwaway_exception);
            if (!g_python_restorethread) {
              g_python_savethread = NULL;
            }
          }
        }
#endif
      }
      setargv = (void (*)(int, char **))
        sidl_DLL_lookupSymbol(dll, argvName, &throwaway_exception);
      if (setargv) {
        (*setargv)(0, fake_args);
      }
    }
    else {
      if (url) {
        fprintf(stderr, "babel: unable to locate %s in library %s\n",
                initName, url);
      }
    }
  }
  if (ownLock) {

    /* PyEval_SaveThread should do a better job at initializing
       threads and acquiring the GIL 

       Lessons learned: The Python GIL is a semaphore; if we release
         it one time too many, we get two ore more threads with access
         to the lock...
    */
    if (g_python_savethread) {
      main_python_thread_state = (*g_python_savethread)();
      sidl_Python_LogUnlock(__func__, __FILE__, __LINE__);
    } else {
      fprintf(stderr, "babel: unable to locate %s in library %s (thread lock unreleased)\n",
              saveThreadName, url ? url : "main:");
    }

  }

  return result;
}

void sidl_Python_Init(void)
{
  sidl_BaseInterface throwaway_exception; /*TODO: a way to not throw these away? */
  static int python_notinitialized = 1;
#ifdef PYTHON_SHARED_LIBRARY
  static const char libName[] = PYTHON_SHARED_LIBRARY;
#endif
  sidl_DLL dll;
  if (python_notinitialized) {
    dll = sidl_Loader_loadLibrary("main:", TRUE, TRUE, &throwaway_exception);
    if (dll) {
      python_notinitialized = !FindAndCallSymbols(dll, NULL);
      sidl_DLL_deleteRef(dll,&throwaway_exception);
    }

    if (python_notinitialized) {
#ifdef PYTHON_SHARED_LIBRARY
      char *url = sidl_String_concat2("file:", PYTHON_SHARED_LIBRARY);
      if (url) {
        dll = sidl_Loader_loadLibrary(url, TRUE, TRUE,&throwaway_exception);
        if (dll) {
          python_notinitialized = !FindAndCallSymbols(dll, url);
          sidl_DLL_deleteRef(dll,&throwaway_exception);
        }
        else {
          fprintf(stderr,
                  "Babel: Error: Unable to load library %s\n", libName);
        }
        sidl_String_free(url);
      }
      else {
        fprintf(stderr, "Unable to allocate string or sidl.DDL object\n");
      }
#else
      fprintf(stderr, "Babel: Error: Unable to initialize Python.\n\
The BABEL runtime library was not configured for Python support,\n\
and Python is not already loaded into the global symbol space.\n");
      python_notinitialized = 0;
#endif
    }
  }
}

#ifdef SIDL_C_HAS_INLINE
inline
#endif
static void
changeCount(int32_t delta)
{
#if defined(CAS32) && defined(HAVE_PTHREAD)
  volatile int32_t *ref_ptr = &g_python_object_count;
  int32_t oldRefcount, newRefcount;
  do {
    oldRefcount = *ref_ptr;
    newRefcount = oldRefcount + delta;
  } while (oldRefcount != CAS32(ref_ptr, oldRefcount, newRefcount));
#else
#ifdef HAVE_PTHREAD
  pthread_mutex_lock(&pc_mutex);
  g_python_object_count += delta;
  pthread_mutex_unlock(&pc_mutex);
#else
  g_python_object_count += delta;
#endif  
#endif
}

void
sidl_Python_IncGlobalRef(void)
{
  changeCount(1);
}

void
sidl_Python_DecGlobalRef(void)
{
  changeCount(-1);
}

#ifndef SIDL_PYTHON_THREAD_DEBUGGING
#undef sidl_Python_LogGILEnsure
#undef sidl_Python_LogGILRelease
#undef sidl_Python_LogUnlock
#undef sidl_Python_LogRelock
#undef sidl_Python_LogControlling
#endif

#ifdef HAVE_PTHREAD
#define SIDL_MAX_THREAD_ID  128
static int num_known_threads = 0;
static int num_excess_threads = 0;
static pthread_mutex_t known_thread_mutex = PTHREAD_MUTEX_INITIALIZER;
static pthread_t s_thread_ids[SIDL_MAX_THREAD_ID];

static int
getUniqueThreadID(void) {
  int i, result = -1; /* negative indicates error */
  const pthread_t self = pthread_self();
  pthread_mutex_lock(&known_thread_mutex);
  for(i = 0; i < num_known_threads; ++i) {
    if (pthread_equal(self, s_thread_ids[i])) {
      result = i;
      break;
    }
  }
  if (result < 0) {
    if (num_known_threads < SIDL_MAX_THREAD_ID) {
      s_thread_ids[num_known_threads] = self;
      result = num_known_threads++;
    }
    else {
      num_excess_threads++;
    }
  }
  pthread_mutex_unlock(&known_thread_mutex);
  if (result < 0) {
    fprintf(stderr, "babel: Thread debugging limited to %d unique thread IDs -- %d needed.\n",
            SIDL_MAX_THREAD_ID, num_known_threads + num_excess_threads);
  }
  return result;
}
#else
static int
getUniqueThreadID(void) {
  return -1;
}
#endif

void sidl_Python_LogUnlock(const char *func,
                           const char *file,
                           const int   line)
{
  fprintf(stderr, "babel: BEGIN_ALLOW_THREADS %s %s %d thread %d\n",
          func, file, line, getUniqueThreadID()); fflush(stderr);
}

void sidl_Python_LogRelock(const char *func,
                           const char *file,
                           const int   line)
{
  fprintf(stderr, "babel: END_ALLOW_THREADS %s %s %d thread %d\n",
          func, file, line, getUniqueThreadID()); fflush(stderr);
}

void sidl_Python_LogGILEnsure(const char *func,
                                 const char *file,
                                 const int   line, 
                                 const int   state)
{
  fprintf(stderr, "babel: ACQUIRE_THREAD_LOCK %s %s %d %d thread %d (previously %s)\n",
          func, file, line, state, getUniqueThreadID(),
	  state == PyGILState_LOCKED ? "PyGILState_LOCKED": "PyGILState_UNLOCKED");
  fflush(stderr);
}

void sidl_Python_LogGILRelease(const char *func,
                                  const char *file,
                                  const int   line,
                                  const int   state)
{
  fprintf(stderr, "babel: RELEASE_THREAD_LOCK %s %s %d %d thread %d\n",
          func, file, line, state, getUniqueThreadID()); fflush(stderr);
}

void 
sidl_Python_LogControlling(const char *func,
                           const char *file,
                           const int line)
{
  fprintf(stderr, "babel: THREAD_CONTROLLING_GIL %s %s %d thread %d\n",
          func, file, line, getUniqueThreadID()); fflush(stderr);
}
