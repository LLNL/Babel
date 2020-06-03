/*
 * File:        hookstest.c
 * Copyright:   (c) 2004 Lawrence Livermore National Security, LLC
 * Revision:    @(#) $Revision: 4533 $
 * Date:        $Date: 2005-04-13 16:38:20 -0700 (Wed, 13 Apr 2005) $
 * Description: Assertion Test C client
 *
 */
#include <stdio.h>
#include "hooks_Basics.h"
#include "sidl_Exception.h"
#include <stdlib.h>
#include <string.h>
#include "synch.h"
#include "sidl_Exception.h"
#include "sidl_rmi_ProtocolFactory.h"

/* #define RMI 0 */

#ifndef FALSE
#define FALSE 0
#endif
#ifndef TRUE
#define TRUE 1
#endif

#define MYASSERT( AAA ) \
  declare_part( tracker, &part_no ); \
  magicNumber = clearstack(magicNumber); \
  synch_RegOut_writeComment(tracker, #AAA, &exception); SIDL_REPORT(exception); \
  { \
     int _result = AAA; SIDL_REPORT(exception); \
     if ( _result ) result = synch_ResultType_PASS; \
     else result = synch_ResultType_FAIL;  \
  } \
  end_part( tracker, part_no, result);


void declare_part( synch_RegOut tracker, int *part_no );
void end_part( synch_RegOut tracker, int part_no, 
               enum synch_ResultType__enum result);

/**
 * Fill the stack with random junk.
 */
int clearstack(int magicNumber) {
  int chunk[2048], i;
  for(i = 0; i < 2048; i++){
    chunk[i] = rand() + magicNumber;
  }
  for(i = 0; i < 16; i++){
    magicNumber += chunk[rand() & 2047];
  }
  return magicNumber;
}

void declare_part( synch_RegOut tracker, int * part_no ) {
  sidl_BaseInterface exception = NULL;
  synch_RegOut_startPart(tracker, ++(*part_no), &exception);
  if (exception) {
    sidl_BaseInterface throwaway_exception;
    sidl_BaseInterface exception2 = NULL;
    synch_RegOut_forceFailure(tracker, &exception2);
    if (exception2) {
      puts("TEST_RESULT FAIL\n");
      sidl_BaseInterface_deleteRef(exception2, &throwaway_exception);
    }
    sidl_BaseInterface_deleteRef(exception, &throwaway_exception);
  }
}

void end_part( synch_RegOut tracker, int part_no, 
               enum synch_ResultType__enum result) {
  sidl_BaseInterface exception = NULL;
  synch_RegOut_endPart(tracker, part_no, result, &exception);
  if (exception) {
    sidl_BaseInterface throwaway_exception;
    sidl_BaseInterface exception2 = NULL;
    synch_RegOut_forceFailure(tracker, &exception2);
    if (exception2) {
      puts("TEST_RESULT FAIL\n");
      sidl_BaseInterface_deleteRef(exception2, &throwaway_exception);
    }
    sidl_BaseInterface_deleteRef(exception, &throwaway_exception);
  }
}

/* basic RMI support */
static char *remoteURL = NULL;

static sidl_bool withRMI( void ) {
  return remoteURL && *remoteURL;
}

static hooks_Basics
makeObject( void ) {
  hooks_Basics obj = NULL;
  sidl_BaseInterface exception = NULL;

#ifdef WITH_RMI  
  if(withRMI()) {
    obj = hooks_Basics__createRemote(remoteURL, &exception); SIDL_REPORT(exception);
    return obj;
  }
#endif
  
  obj = hooks_Basics__create(&exception); SIDL_REPORT(exception);
  return obj;
  EXIT:
  exit(1);
}

/*************************** main ***************************/
int main(int argc, char**argv)
{ 
  hooks_Basics h;
  sidl_BaseInterface exception = NULL;
  int32_t b, c, ret = 0;
  synch_RegOut tracker = synch_RegOut_getInstance(&exception);
  enum synch_ResultType__enum result = synch_ResultType_PASS;
  int part_no = 0;
  int magicNumber = 1;

  /* Parse the command line  to see if we are running RMI tests */
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
  
  synch_RegOut_setExpectations(tracker, 4, &exception); SIDL_REPORT(exception);

  hooks_Basics__set_hooks_static(TRUE, &exception);SIDL_REPORT(exception);
  h = makeObject();
  hooks_Basics__set_hooks(h, TRUE, &exception);SIDL_REPORT(exception);

  b = c = -1;
  ret = hooks_Basics_aStaticMeth(part_no, &b, &c, &exception);SIDL_REPORT(exception);
  MYASSERT( b == 1 && c == 0 );
  ret = hooks_Basics_aStaticMeth(part_no, &b, &c, &exception);SIDL_REPORT(exception);
  MYASSERT( b == 2 && c == 1 );
  
  b = c = -1;
  ret = hooks_Basics_aNonStaticMeth(h, part_no, &b, &c, &exception);SIDL_REPORT(exception);
  MYASSERT( b == 1 && c == 0 );
  ret = hooks_Basics_aNonStaticMeth(h, part_no, &b, &c, &exception);SIDL_REPORT(exception);
  MYASSERT( b == 2 && c == 1 );

  hooks_Basics_deleteRef(h, &exception);SIDL_REPORT(exception);
  synch_RegOut_close(tracker, &exception);SIDL_REPORT(exception);
  synch_RegOut_deleteRef(tracker,&exception);SIDL_REPORT(exception);
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
  return -1;
}
