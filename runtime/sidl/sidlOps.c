#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "sidlOps.h"
#include "sidl_BaseInterface.h"
#include "sidl_MemAllocException.h"
#ifdef HAVE_PTHREAD
#include <pthread.h>
static pthread_mutex_t s_atexit_mutex = PTHREAD_MUTEX_INITIALIZER;
#endif

#if defined(PIC) || !defined(SIDL_PURE_STATIC_RUNTIME)
#include "sidl_Loader.h"

/*
 * Dynamically load the named symbol.
 */

void* sidl_dynamicLoadIOR(const char* objName, const char* extName) {
  sidl_BaseInterface _throwaway_exception = NULL; /*(TODO: a way to not throw these away? */

  void * _locExternals = NULL;
  sidl_DLL dll = sidl_DLL__create(&_throwaway_exception);
  void* (*dll_f)(void);
  /* check global namespace for symbol first */
  if (dll && sidl_DLL_loadLibrary(dll, "main:", TRUE, FALSE,&_throwaway_exception)) {
    dll_f =
      (  void*(*)(void)) sidl_DLL_lookupSymbol(
					       dll, extName,&_throwaway_exception);
    _locExternals = (dll_f ? (*dll_f)() : NULL);
  }
  if (dll) sidl_DLL_deleteRef(dll,&_throwaway_exception);
  if (!_locExternals) {
    dll = sidl_Loader_findLibrary(objName,
				  "ior/impl", sidl_Scope_SCLSCOPE,
				  sidl_Resolve_SCLRESOLVE,&_throwaway_exception);
    if (dll) {
      dll_f =
        (  void*(*)(void)) sidl_DLL_lookupSymbol(
						 dll, extName,&_throwaway_exception);
      if (dll_f) {
        _locExternals = (*dll_f)();
      }
      else {
        char *uri;
        fputs("Babel: unable to find required symbol, '", stderr);
        fputs(extName, stderr);
        fputs("' in library: ", stderr);
        uri = sidl_DLL_getName(dll, &_throwaway_exception);
        if ((!_throwaway_exception) && uri) {
          fputs(uri, stderr);
          fputs("\n", stderr);
          free(uri);
        }
        else {
          fputs("error\n", stderr);
        }
        _locExternals = NULL;
      }
      sidl_DLL_deleteRef(dll,&_throwaway_exception);
    }
  }
  if (!_locExternals) {
    fputs("Babel: unable to load the implementation for ", stderr);
    fputs(objName, stderr);
    fputs(" please set SIDL_DLL_PATH\n", stderr);
    exit(-1);
  }
  return _locExternals;
}
#else
void *
sidl_dynamicLoadIOR(const char *objName, const char *extName)
{
  fputs("Babel: SIDL runtime configured not to support dynamic loading.\n\
Unable to load implementation for ", stderr);
  fputs(objName, stderr);
  fputs("\nPlease reconfigure without --enable-pure-static-runtime\n", stderr);
  exit(-1); /*NOTREACHED*/
  return NULL;
}
#endif

void
sidl_checkIORVersion(const char *libraryOrType,
                     int libMajor, int libMinor,
                     int progMajor, int progMinor)
{
  if (libMajor != progMajor) {
    fprintf(stderr, "babel: ERROR IOR version mismatch (library IOR version %d.%d, program IOR version %d.%d) for library/type %s\n",
            libMajor, libMinor, progMajor, progMinor, libraryOrType);
    exit(2); /* this is serious enough that the program should exit */
  }
  if (libMinor != progMinor) {
    fprintf(stderr, "babel: WARNING minor IOR version mismatch (library IOR version %d.%d, program IOR version %d.%d) for library/type %s\n",
            libMajor, libMinor, progMajor, progMinor, libraryOrType);
    /* do not exit */
  }
}

struct sidl_atexit_list {
  sidl_atexit_func         d_func;
  void                    *d_data;
  struct sidl_atexit_list *d_next;
};

struct sidl_atexit_list *g_atexit_list = NULL;
static int s_atexit_initialized = 0;


static struct sidl_atexit_list *
getNext(void)
{
  struct sidl_atexit_list *result;
#ifdef HAVE_PTHREAD
  pthread_mutex_lock(&s_atexit_mutex);
#endif
  result = g_atexit_list;
  if (result) {
    g_atexit_list = result->d_next;
  }
#ifdef HAVE_PTHREAD
  pthread_mutex_unlock(&s_atexit_mutex);
#endif
  return result;
}

static void 
sidl_atexit_impl(void)
{
  struct sidl_atexit_list *tmp;
  while ((tmp = getNext()) != NULL) {
    (*(tmp->d_func))(tmp->d_data);
    free((void *)tmp);
  }
}

void sidl_atexit(sidl_atexit_func fcn, void *data)
{
#ifdef HAVE_PTHREAD
  pthread_mutex_lock(&s_atexit_mutex);
  {
#endif
    struct sidl_atexit_list * const tmp = g_atexit_list;
    if (!s_atexit_initialized) {
      s_atexit_initialized = 1;
      atexit(sidl_atexit_impl);
    }
    g_atexit_list = malloc(sizeof(struct sidl_atexit_list));
    if (g_atexit_list) {
      g_atexit_list->d_next = tmp;
      g_atexit_list->d_data = data;
      g_atexit_list->d_func = fcn;
    }
    else {
      fputs("Babel: Error: Failed to allocate memory for sidl_exit\n", stderr);
      g_atexit_list = tmp;
    }
#ifdef HAVE_PTHREAD
  }
  pthread_mutex_unlock(&s_atexit_mutex);
#endif
}

void
sidl_deleteRef_atexit(void *objref)
{
  sidl_BaseInterface *obj=
    (sidl_BaseInterface *)objref;
  sidl_BaseInterface ignored;
  if (obj && *obj) {
    sidl_BaseInterface_deleteRef(*obj, &ignored);
    *obj = NULL;
  }
}

void *
sidl_malloc(size_t numbytes,
            const char *note,
            const char *filename,
            const int   lineno,
            const char *function,
            struct sidl_BaseInterface__object **exception)
{
  void *result = malloc(numbytes);
  if (!result) {
    sidl_MemAllocException mem = 
      sidl_MemAllocException_getSingletonException(exception);
    if (!*exception) {
      sidl_MemAllocException_setNote(mem, note, exception);
      if (!*exception) {
        sidl_MemAllocException_add(mem, __FILE__, __LINE__, 
                                   "sidl_malloc", exception);
        if (!*exception) {
          sidl_MemAllocException_add(mem, filename, (int32_t)lineno, 
                                     function, exception);
          if (!*exception) {
            *exception = (struct sidl_BaseInterface__object *)mem;
          }
        }
      }
    }
  }
  else {
    *exception = NULL;
  }
  return result;
}
