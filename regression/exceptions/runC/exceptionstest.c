/*
 * File:        exceptionstest.c
 * Copyright:   (c) 2001 Lawrence Livermore National Security, LLC
 * Revision:    @(#) $Revision: 7451 $
 * Date:        $Date: 2012-04-19 09:33:07 -0700 (Thu, 19 Apr 2012) $
 * Description: Exception Test C client
 *
 */

#include <stdio.h>
#include "sidl_Exception.h"
#include "sidl_String.h"
#include "Exceptions_Fib.h"
#include "sidl_int_IOR.h"
#include "sidl_SIDLException.h"
#include "sidl_rmi_ProtocolFactory.h"
#include "sidl_BaseClass.h"
#include "synch.h"
#include "sidl_String.h"
#include <stddef.h>
#include <stdlib.h>
#include <string.h>

#ifndef FALSE
#define FALSE 0
#endif
#ifndef TRUE
#define TRUE 1
#endif


static void declare_part(synch_RegOut tracker,
                         int * part_no )
{
  sidl_BaseInterface throwaway = NULL;
  synch_RegOut_startPart(tracker, ++(*part_no), &throwaway);
}

static void end_part(synch_RegOut tracker, int part_no, 
                     enum synch_ResultType__enum result)
{
  sidl_BaseInterface throwaway = NULL;
  synch_RegOut_endPart(tracker, part_no, result, &throwaway);
}

static void traceback(sidl_BaseInterface _ex)
{
  sidl_BaseInterface throwaway = NULL;
  sidl_BaseException be = sidl_BaseException__cast(_ex, &throwaway);
  
  char* msg = NULL;
  if (be) {
    msg = sidl_BaseException_getNote(be, &throwaway);
    printf("%s\n", msg);
    sidl_String_free(msg);

    msg = sidl_BaseException_getTrace(be, &throwaway);
    printf("%s\n", msg);
    sidl_String_free(msg);
    sidl_BaseException_deleteRef(be, &throwaway);
  }
}

#define CHECK(FUNC,COMMENT)                   \
  declare_part(tracker, &part_no);             \
  synch_RegOut_writeComment(tracker, COMMENT, &exception); SIDL_REPORT(exception); \
  result = (FUNC) ? synch_ResultType_PASS : synch_ResultType_FAIL;            \
  end_part(tracker, part_no, result);

int runTest1(Exceptions_Fib f)
{
  int x;
  sidl_BaseInterface _ex = NULL;

  x = Exceptions_Fib_getFib(f, 10, 25, 200, 0, &_ex); SIDL_REPORT(_ex);
  return TRUE;

  EXIT:;
    traceback(_ex);
    SIDL_CLEAR(_ex);
    return FALSE;
}

int runTest2(Exceptions_Fib f)
{
  int x;
  sidl_BaseInterface _ex = NULL;

  x = Exceptions_Fib_getFib(f, -1, 10, 10, 0, &_ex);
  if (SIDL_CATCH(_ex, "Exceptions.NegativeValueException")) {
    traceback(_ex);
    SIDL_CLEAR(_ex);
  } else if (_ex == NULL) {
    return FALSE;
  }
  SIDL_CHECK(_ex);
  return TRUE;

  EXIT:;
    traceback(_ex);
    SIDL_CLEAR(_ex);
    return FALSE;
}

int runTest3(Exceptions_Fib f)
{
  int x;
  sidl_BaseInterface _ex = NULL;

  x = Exceptions_Fib_getFib(f, 10, 1, 100, 0, &_ex);
  if (SIDL_CATCH(_ex, "Exceptions.TooDeepException")) {
    traceback(_ex);
    SIDL_CLEAR(_ex);
  } else if (_ex == NULL) {
    return FALSE;
  }
  SIDL_CHECK(_ex);
  return TRUE;

  EXIT:;
    traceback(_ex);
    SIDL_CLEAR(_ex);
    return FALSE;
}

int runTest4(Exceptions_Fib f)
{
  int x;
  sidl_BaseInterface _ex = NULL;

  x = Exceptions_Fib_getFib(f, 10, 100, 1, 0, &_ex);
  if (SIDL_CATCH(_ex, "Exceptions.TooBigException")) {
    traceback(_ex);
    SIDL_CLEAR(_ex);
  } else if (_ex == NULL) {
    return FALSE;
  }
  SIDL_CHECK(_ex);
  return TRUE;

  EXIT:;
    traceback(_ex);
    SIDL_CLEAR(_ex);
    return FALSE;
}

int runTest5(Exceptions_Fib f, int row)
{
  sidl_BaseInterface exception, throwaway;
  int result = 0;
  int32_t buffer1[4], buffer2[4];
  int32_t m = 2, n = 2;
  struct sidl_int__array *a1 = 
    (row ? sidl_int__array_create2dRow(3,3) :sidl_int__array_create2dCol(3,3));
  struct sidl_int__array *a2 =
    (row ? sidl_int__array_create2dRow(4,3) :sidl_int__array_create2dCol(4,4));
  struct sidl_int__array *a3 = (struct sidl_int__array *)
    (ptrdiff_t)random();
  struct sidl_int__array *c1 = sidl_int__array_create1d(3);
  struct sidl_int__array *c2 = sidl_int__array_create1d(4);
  struct sidl_int__array *c3 = (struct sidl_int__array *)
    (ptrdiff_t)random();
  struct sidl_int__array *retval;
  const char *s1 = "foo";
  char *s2 = sidl_String_strdup("foo");
  char *s3 = (char *)(ptrdiff_t)random();
  sidl_BaseClass o1=NULL, o2, o3;
  o1 = sidl_BaseClass__create(&exception);SIDL_REPORT(exception);
  o2 = sidl_BaseClass__create(&exception);SIDL_REPORT(exception);
  o3 = (sidl_BaseClass)(ptrdiff_t)random();
  retval = Exceptions_Fib_noLeak(f, a1, &a2, &a3,
                                    buffer1, buffer2, m, n,
                                    c1, &c2, &c3,
                                    s1, &s2, &s3,
                                    o1, &o2, &o3,
                                    &exception);
  SIDL_CHECK(exception);
  return 0;
 EXIT:
  if (o1) sidl_BaseClass_deleteRef(o1, &throwaway);
  if (c1) sidl_int__array_deleteRef(c1);
  if (a1) sidl_int__array_deleteRef(a1);
  if (exception && 
      sidl_BaseInterface_isType(exception, "sidl.SIDLException", &throwaway)){
    result = 1;
  }
  if (exception) {
    sidl_BaseInterface_deleteRef(exception, &throwaway);
  }
  return result;
}

/* basic RMI support */
static char *remoteURL = NULL;

static sidl_bool withRMI( void ) {
  return remoteURL && *remoteURL;
}

static Exceptions_Fib
makeObject( void ) {
  Exceptions_Fib obj = NULL;
  sidl_BaseInterface exception = NULL;

#ifdef WITH_RMI  
  if(withRMI()) {
    obj = Exceptions_Fib__createRemote(remoteURL, &exception); SIDL_REPORT(exception);
    return obj;
  }
#endif
  
  obj = Exceptions_Fib__create(&exception); SIDL_REPORT(exception);
  return obj;
  EXIT:
  exit(1);
}

int main(int argc, char**argv)
{ 
  enum synch_ResultType__enum result       = synch_ResultType_PASS; 
  int magicNumber  = 1;
  int part_no      = 0;
  sidl_BaseInterface exception = NULL;
  Exceptions_Fib fib = NULL;
  synch_RegOut tracker = NULL;
  tracker = synch_RegOut__create(&exception);SIDL_REPORT(exception);

  /* Parse the command line to see if we are running RMI tests */
#ifdef WITH_RMI  
  {
    unsigned _arg;
    for(_arg = 0; _arg < argc; ++_arg) {
      if(strncmp(argv[_arg], "--url=", 6) == 0) {
        remoteURL = argv[_arg] + 6;
        synch_RegOut_replaceMagicVars(tracker, &remoteURL, argv[0], &exception); SIDL_REPORT(exception);
      }
    }
    if(withRMI()) {
      fprintf(stdout, "using remote URL %s\n", remoteURL);
    }
  }

  /* Setup RMI if necessary */
  if(withRMI()) {
    sidl_BaseInterface _ex = NULL;
    fprintf(stdout, "registering RMI protocol simhandle\n");
    if(!sidl_rmi_ProtocolFactory_addProtocol("simhandle","sidlx.rmi.SimHandle",&_ex )){
      printf("sidl.rmi.ProtocolFactor.addProtocol() failed\n");
      exit(2);
    }
  }
#endif
  
  fib = makeObject();
  (void) argc;
  (void) argv;
  synch_RegOut_setExpectations(tracker, 6, &exception);SIDL_REPORT(exception);
  CHECK(runTest1(fib), "checking no exception thrown");
  CHECK(runTest2(fib), "checking Exceptions.NegativeValueException");
  CHECK(runTest3(fib), "checking Exceptions.TooDeepException");
  CHECK(runTest4(fib), "checking Exceptions.TooBigException");
  CHECK(runTest5(fib, 1), "checking exception leaks and ignored out values");
  CHECK(runTest5(fib, 0), "checking exception leaks and ignored out values");
  synch_RegOut_close(tracker,&exception );SIDL_REPORT(exception);
  synch_RegOut_deleteRef(tracker, &exception);SIDL_REPORT(exception);

  Exceptions_Fib_deleteRef (fib, &exception);SIDL_REPORT(exception);
  return 0;
 EXIT:
  {
    sidl_BaseInterface throwaway_exception = NULL;
    if (tracker) {
      sidl_BaseInterface exception2 = NULL;
      synch_RegOut_forceFailure(tracker, &exception2);
      if (exception2) {
        puts("TEST_RESULT FAIL\n");
        sidl_BaseInterface_deleteRef(exception2, &throwaway_exception);
      }
      synch_RegOut_deleteRef(tracker, &throwaway_exception);
    }
    sidl_BaseInterface_deleteRef(exception, &throwaway_exception);
    return -1;
  }
}
