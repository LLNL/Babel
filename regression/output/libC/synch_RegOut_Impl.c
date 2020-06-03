/*
 * File:          synch_RegOut_Impl.c
 * Symbol:        synch.RegOut-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for synch.RegOut
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "synch.RegOut" (version 0.1)
 * 
 * Regression test output class. 
 */

#include "synch_RegOut_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(synch.RegOut._includes) */
#include <stdlib.h>
#include <stdio.h>
#include "sidlOps.h"
#include <string.h> 
#include <regex.h> /* this is POSIX standard */

#define CTORSTATE   0
#define TALLYING    1
#define CLOSED      2

static synch_RegOut g_sharedInstance = NULL;

static const char * const s_result_strings[] = {
  "PASS",
  "FAIL",
  "XFAIL",
  "XPASS"
};

static void beginTallying(struct synch_RegOut__data *private)
{
  private->state = TALLYING;
  private->current_part = 1;
  printf("NPARTS %d\n", private->expected_parts);
  fflush(stdout);
}

static
int calculateResult(struct synch_RegOut__data *private) {
  if ((private->num_fails != 0) ||
      (private->num_xpass != 0) ||
      ((private->expected_parts >= 0) &&
       ((private->num_passes + private->num_xfails) !=
        private->expected_parts)))
    return synch_ResultType_FAIL;
  if (private->num_xfails > 0) return synch_ResultType_XFAIL;
  return synch_ResultType_PASS;
}
    
/* DO-NOT-DELETE splicer.end(synch.RegOut._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_synch_RegOut__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_synch_RegOut__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(synch.RegOut._load) */
    /* DO-NOT-DELETE splicer.end(synch.RegOut._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_synch_RegOut__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_synch_RegOut__ctor(
  /* in */ synch_RegOut self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(synch.RegOut._ctor) */
  struct synch_RegOut__data *private = 
    malloc(sizeof(struct synch_RegOut__data));
  synch_RegOut__set_data(self, private);
  private->expected_parts = -1;
  private->current_part = 0;
  private->num_passes = 0;
  private->num_xfails = 0;
  private->num_xpass = 0;
  private->num_fails = 0;
  private->state = CTORSTATE;
    /* DO-NOT-DELETE splicer.end(synch.RegOut._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_synch_RegOut__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_synch_RegOut__ctor2(
  /* in */ synch_RegOut self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(synch.RegOut._ctor2) */
  /* Insert-Code-Here {synch.RegOut._ctor2} (special constructor method) */
    /* DO-NOT-DELETE splicer.end(synch.RegOut._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_synch_RegOut__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_synch_RegOut__dtor(
  /* in */ synch_RegOut self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(synch.RegOut._dtor) */
  sidl_BaseInterface throwaway_exception;
  struct synch_RegOut__data *private =
    synch_RegOut__get_data(self);
  if (private->state < CLOSED) synch_RegOut_close(self, &throwaway_exception);
  free((void *)private);
  synch_RegOut__set_data(self, NULL);
    /* DO-NOT-DELETE splicer.end(synch.RegOut._dtor) */
  }
}

/*
 * In some circumstances, it is necessary to use the
 * singleton pattern.
 */

#undef __FUNC__
#define __FUNC__ "impl_synch_RegOut_getInstance"

#ifdef __cplusplus
extern "C"
#endif
synch_RegOut
impl_synch_RegOut_getInstance(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(synch.RegOut.getInstance) */
  if (g_sharedInstance == NULL) {
    g_sharedInstance = synch_RegOut__create(_ex);
    if (*_ex) {
      g_sharedInstance = NULL;
    }
    else {
      sidl_atexit(sidl_deleteRef_atexit, &g_sharedInstance);
    }
  }
  if (g_sharedInstance != NULL) synch_RegOut_addRef(g_sharedInstance, _ex);
  return g_sharedInstance;
    /* DO-NOT-DELETE splicer.end(synch.RegOut.getInstance) */
  }
}

/*
 * Tell this class how many parts to expect.
 * Calling this method twice or after any of the other
 * methods in this class causes it to execute
 * forceFailure.
 * @param numparts  a negative number indicates the number
 * of parts is unknown
 */

#undef __FUNC__
#define __FUNC__ "impl_synch_RegOut_setExpectations"

#ifdef __cplusplus
extern "C"
#endif
void
impl_synch_RegOut_setExpectations(
  /* in */ synch_RegOut self,
  /* in */ int32_t numparts,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(synch.RegOut.setExpectations) */
  struct synch_RegOut__data *private =
    synch_RegOut__get_data(self);
  if (private->state == CTORSTATE) {
    private->expected_parts = numparts;
    beginTallying(private);
  }
  else {
    synch_RegOut_forceFailure(self, _ex);
  }
    /* DO-NOT-DELETE splicer.end(synch.RegOut.setExpectations) */
  }
}

/*
 * Return the current part number.
 */

#undef __FUNC__
#define __FUNC__ "impl_synch_RegOut_getCurrentPart"

#ifdef __cplusplus
extern "C"
#endif
int32_t
impl_synch_RegOut_getCurrentPart(
  /* in */ synch_RegOut self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(synch.RegOut.getCurrentPart) */
  struct synch_RegOut__data *private =
    synch_RegOut__get_data(self);
  if (private->state < TALLYING) {
    beginTallying(private);
  }
  if (private->state == TALLYING) return private->current_part;
  return -1;
    /* DO-NOT-DELETE splicer.end(synch.RegOut.getCurrentPart) */
  }
}

/*
 * Print the start part line.
 */

#undef __FUNC__
#define __FUNC__ "impl_synch_RegOut_startPart"

#ifdef __cplusplus
extern "C"
#endif
void
impl_synch_RegOut_startPart(
  /* in */ synch_RegOut self,
  /* in */ int32_t part,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(synch.RegOut.startPart) */
  struct synch_RegOut__data *private =
    synch_RegOut__get_data(self);
  if (private->state < TALLYING) {
    beginTallying(private);
  }
  if ((private->state == TALLYING) && (part == private->current_part)) {
    printf("PART %d\n", part);
    fflush(stdout);
  }
  else {
    synch_RegOut_forceFailure(self, _ex);
  }
    /* DO-NOT-DELETE splicer.end(synch.RegOut.startPart) */
  }
}

/*
 * Print the end part line.
 */

#undef __FUNC__
#define __FUNC__ "impl_synch_RegOut_endPart"

#ifdef __cplusplus
extern "C"
#endif
void
impl_synch_RegOut_endPart(
  /* in */ synch_RegOut self,
  /* in */ int32_t part,
  /* in */ enum synch_ResultType__enum res,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(synch.RegOut.endPart) */
  struct synch_RegOut__data *private =
    synch_RegOut__get_data(self);
  if ((private->state == TALLYING) && (private->current_part == part)) {
    if ((res < 0) || (res > sizeof(s_result_strings)/sizeof(sizeof(char *)))) {
      res = synch_ResultType_FAIL;
    }
    printf("RESULT %d %s\n", part, s_result_strings[res]);
    fflush(stdout);
    switch(res) {
    case synch_ResultType_PASS:
      ++(private->num_passes);
      break;
    case synch_ResultType_FAIL:
      ++(private->num_fails);
      break;
    case synch_ResultType_XFAIL:
      ++(private->num_xfails);
      break;
    case synch_ResultType_XPASS:
      ++(private->num_xpass);
      break;
    }
    ++(private->current_part);
  }
  else {
    synch_RegOut_forceFailure(self, _ex);
  }
    /* DO-NOT-DELETE splicer.end(synch.RegOut.endPart) */
  }
}

/*
 * Print COMMENT: <comment>\n.
 */

#undef __FUNC__
#define __FUNC__ "impl_synch_RegOut_writeComment"

#ifdef __cplusplus
extern "C"
#endif
void
impl_synch_RegOut_writeComment(
  /* in */ synch_RegOut self,
  /* in */ const char* comment,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(synch.RegOut.writeComment) */
  printf("COMMENT: %s\n", comment);
    /* DO-NOT-DELETE splicer.end(synch.RegOut.writeComment) */
  }
}

/*
 * Unconditionally fail right now.
 */

#undef __FUNC__
#define __FUNC__ "impl_synch_RegOut_forceFailure"

#ifdef __cplusplus
extern "C"
#endif
void
impl_synch_RegOut_forceFailure(
  /* in */ synch_RegOut self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(synch.RegOut.forceFailure) */
  struct synch_RegOut__data *private =
    synch_RegOut__get_data(self);
  switch(private->state) {
  case CTORSTATE:
    beginTallying(private); /* fall through to next case intended */
  case TALLYING:
    private->num_fails = 1;
    synch_RegOut_close(self, _ex);
  case CLOSED:
    break;
  }
    /* DO-NOT-DELETE splicer.end(synch.RegOut.forceFailure) */
  }
}

/*
 * Finish the test. This is called by the destructor if you
 * forget.
 */

#undef __FUNC__
#define __FUNC__ "impl_synch_RegOut_close"

#ifdef __cplusplus
extern "C"
#endif
void
impl_synch_RegOut_close(
  /* in */ synch_RegOut self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(synch.RegOut.close) */
  struct synch_RegOut__data *private =
    synch_RegOut__get_data(self);
  if (private->state == TALLYING) {
    int result = calculateResult(private);
    
    printf("TEST_RESULT %s\n", s_result_strings[result]);
    fflush(stdout);
  }
  private->state = CLOSED;
    /* DO-NOT-DELETE splicer.end(synch.RegOut.close) */
  }
}

/*
 * Based on argv[0] and the value of SIDL_DLL_PATH, replace the
 * substring "%magic_port%" of the given string with the port
 * number used in the Babel regression tests for the particular
 * backend language. Further magic variables might be added later
 * as needed. 
 */

#undef __FUNC__
#define __FUNC__ "impl_synch_RegOut_replaceMagicVars"

#ifdef __cplusplus
extern "C"
#endif
void
impl_synch_RegOut_replaceMagicVars(
  /* in */ synch_RegOut self,
  /* inout */ char** orig,
  /* in */ const char* prog_name,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(synch.RegOut.replaceMagicVars) */
    char *p = *orig;
    int first_match = 1;
    unsigned port=9000;

    /* supported languages in historical order */
    const char *langs[] = {
      "C", "Cxx", "F77", "F90", "Python", "Java", "F03"
    };
    const unsigned num_langs = 7;

    while( (p = strstr(p, "%magic_port%")) != NULL) {
      /* try to guess the backend language */
      regex_t regex;
      int reti;

      if(first_match) {
        int i;
        char impl_lang[64];
        
        first_match = 0;
        memset(impl_lang,0,sizeof(impl_lang));
        
        /* first, see if the executable name is of the form run<lang>2<lang> */
        reti = regcomp(&regex, "run[A-Za-z0-9]+2[A-Za-z0-9]+$", REG_EXTENDED);
        if(reti) {
          fprintf(stderr, "could not compile regex\n");
          exit(1);
        }
        reti = regexec(&regex, prog_name, 0, NULL, 0);
        if(!reti) {
          strncpy(impl_lang, strrchr(prog_name, '2') + 1, 63);
        }
        else if( reti == REG_NOMATCH ) {
          char *dll_path = getenv("SIDL_DLL_PATH");
          if(dll_path) {
            char *match = strstr(dll_path, "libImpl.scl");
            if(match && match > dll_path && *(match-1) == '/') {
              char *q = match-2;
              while(q > dll_path && *(q-1) != '/')
                --q;
              if(strncmp(q, "lib", 3) == 0) {
                int len = match - q  - 4;
                if (len < 64) {
                  strncpy(impl_lang, q + 3, len);
                  impl_lang[len] = '\0';
                }
                else {
                  fprintf(stderr, "Buffer overrun badness!\n");
                  exit(1);
                }
              }
            }
          }
        }
        else {
          fprintf(stderr, "regex match failed\n");
          exit(1);
        }

	/* else die horribly */
        if(!*impl_lang) {
	  fprintf(stderr, "could not auto-detect implementation language,\n");
	  fprintf(stderr, "please try to set at least SIDL_DLL_PATH.\n");
          exit(1);
        } 

        /* try to map the implementation language to a port number */
        for(i=0; i < num_langs; ++i) {
          if(strcmp(impl_lang, langs[i]) == 0) {
            port += i+1;
            break;
          }
        }
      }

      /* replace the current match with the precomputed port number */
      {
        char *tmp = p + strlen("%magic_port%");
        p += sprintf(p, "%d", port);
        while(*tmp)
          *p++ = *tmp++;
        *p = '\0';
      }
      ++p;
    }
    /* DO-NOT-DELETE splicer.end(synch.RegOut.replaceMagicVars) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

