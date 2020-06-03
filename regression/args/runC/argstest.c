#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "Args.h"
#include "synch.h"
#include "sidl_Exception.h"

/* #define RMI 0 */

#ifdef RMI
#include "sidl_rmi_ProtocolFactory.h"
#endif

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

#define MYXFAIL( AAA ) \
  declare_part( tracker, &part_no ); \
  magicNumber = clearstack(magicNumber); \
  synch_RegOut_writeComment(tracker, #AAA, &exception); SIDL_REPORT(exception); \
  { \
     int _result = AAA; SIDL_REPORT(exception); \
     if ( _result ) result = synch_ResultType_XPASS; \
     else result = synch_ResultType_XFAIL;  \
  } \
  end_part( tracker, part_no, result);

#define MYBROKEN( AAA ) \
  declare_part( tracker,  &part_no ); \
  synch_RegOut_writeComment(tracker, #AAA, &exception); SIDL_REPORT(exception); \
  end_part( tracker, part_no, synch_ResultType_FAIL);

/* basic RMI support */
static char *remoteURL = NULL;

static sidl_bool withRMI( void ) {
  return remoteURL && *remoteURL;
}

static Args_Basic
makeObject( void ) {
  Args_Basic obj = NULL;
  sidl_BaseInterface exception = NULL;

#ifdef WITH_RMI  
  if(withRMI()) {
    obj = Args_Basic__createRemote(remoteURL, &exception); SIDL_REPORT(exception);
    return obj;
  }
#endif
  
  obj = Args_Basic__create(&exception); SIDL_REPORT(exception);
  return obj;
  EXIT:
  exit(1);
}

int main(int argc, char** argv) { 
  sidl_BaseInterface exception = NULL;
  enum synch_ResultType__enum result = synch_ResultType_PASS;
  synch_RegOut tracker = synch_RegOut__create(&exception); 
  int magicNumber = 1;
  int part_no = 0;
  int P;
  const char *language = "";
  SIDL_REPORT(exception);

  /* Parse the command line  to see if we are running RMI tests */
#ifdef WITH_RMI
  {
    unsigned _arg;
    for(_arg = 1; _arg < argc; ++_arg) {
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
  
  synch_RegOut_setExpectations(tracker, -1, &exception); SIDL_REPORT(exception);
  if (argc == 2){
    language = argv[1];
  }
  
  { /* bool */
    int out;
    int inout = TRUE;
    Args_Basic obj = makeObject();
    MYASSERT( Args_Basic_returnbackbool( obj, &exception ) == TRUE );
    MYASSERT( Args_Basic_passinbool( obj, TRUE, &exception ) == TRUE );
    MYASSERT( Args_Basic_passoutbool( obj, &out, &exception ) == TRUE && out == TRUE );
    MYASSERT( Args_Basic_passinoutbool( obj, &inout, &exception ) == TRUE && inout == FALSE );
    MYASSERT( Args_Basic_passeverywherebool( obj, TRUE, &out, &inout, &exception ) == TRUE &&
	      out == TRUE && inout == TRUE );
    
    Args_Basic_deleteRef( obj, &exception); SIDL_REPORT(exception);
  } 

  { /* char */
    char out;
    char inout = 'A';
    Args_Basic  obj = makeObject();
 
    MYASSERT( Args_Basic_returnbackchar( obj, &exception ) == '3' );
    MYASSERT( Args_Basic_passinchar( obj, '3', &exception ) == TRUE );
    MYASSERT( Args_Basic_passoutchar( obj, &out, &exception ) == TRUE && out == '3' );
    MYASSERT( Args_Basic_passinoutchar( obj, &inout, &exception ) == TRUE && inout == 'a' );
    MYASSERT( Args_Basic_passeverywherechar( obj, '3', &out, &inout, &exception ) == '3' &&
	     out == '3' && inout == 'A' );

    Args_Basic_deleteRef( obj , &exception); SIDL_REPORT(exception);
  }


  { /* int */
    int32_t out;
    int32_t inout = 3;
    Args_Basic obj = makeObject();
 
    MYASSERT( Args_Basic_returnbackint( obj, &exception ) == 3 );
    MYASSERT( Args_Basic_passinint( obj, 3, &exception ) == TRUE );
    MYASSERT( Args_Basic_passoutint( obj, &out, &exception ) == TRUE && out == 3 );
    MYASSERT( Args_Basic_passinoutint( obj, &inout, &exception ) == TRUE && inout == -3 );
    MYASSERT( Args_Basic_passeverywhereint( obj, 3, &out, &inout, &exception ) == 3 &&
	      out == 3 && inout == 3 );
    
    Args_Basic_deleteRef( obj , &exception); SIDL_REPORT(exception);
  }

  { /* long */
    int64_t out;
    int64_t inout = 3;
    Args_Basic obj = makeObject();
 
    MYASSERT( Args_Basic_returnbacklong( obj, &exception ) == 3 );
    MYASSERT( Args_Basic_passinlong( obj, 3,  &exception ) == TRUE );
    MYASSERT( Args_Basic_passoutlong( obj, &out, &exception ) == TRUE && out == 3 );
    MYASSERT( Args_Basic_passinoutlong( obj, &inout, &exception ) == TRUE && inout == -3 );
    MYASSERT( Args_Basic_passeverywherelong( obj, 3, &out, &inout, &exception ) == 3 &&
	      out == 3 && inout == 3 );
    
    Args_Basic_deleteRef( obj , &exception); SIDL_REPORT(exception);
  }

  { /* float */
    float out;
    float inout = 3.1F;
    Args_Basic obj = makeObject();
 
    MYASSERT( Args_Basic_returnbackfloat( obj, &exception ) == 3.1F );
    MYASSERT( Args_Basic_passinfloat( obj, 3.1F, &exception ) == TRUE );
    MYASSERT( Args_Basic_passoutfloat( obj, &out, &exception ) == TRUE && out == 3.1F );
    MYASSERT( Args_Basic_passinoutfloat( obj, &inout, &exception ) == TRUE && inout == -3.1F
              );
    MYASSERT( Args_Basic_passeverywherefloat( obj, 3.1F, &out, &inout, &exception ) ==
              3.1F && out == 3.1F && inout == 3.1F );
    
    Args_Basic_deleteRef( obj, &exception ); SIDL_REPORT(exception);
  }


  { /* double */
    double out;
    double inout = 3.14;
    Args_Basic obj = makeObject();
 
    MYASSERT( Args_Basic_returnbackdouble( obj, &exception ) == 3.14 );
    MYASSERT( Args_Basic_passindouble( obj, 3.14, &exception ) == TRUE );
    MYASSERT( Args_Basic_passoutdouble( obj, &out, &exception ) == TRUE && out == 3.14 );
    MYASSERT( Args_Basic_passinoutdouble( obj, &inout, &exception ) == TRUE && inout == -3.14 );
    MYASSERT( Args_Basic_passeverywheredouble( obj, 3.14, &out, &inout, &exception ) == 3.14 &&
	      out == 3.14 && inout == 3.14 );
    
    Args_Basic_deleteRef( obj, &exception ); SIDL_REPORT(exception);
  }


  { /* fcomplex */
    struct sidl_fcomplex retval;
    struct sidl_fcomplex in = { 3.1F, 3.1F };
    struct sidl_fcomplex out;
    struct sidl_fcomplex inout = { 3.1F, 3.1F };
    Args_Basic obj = makeObject();
 
    printf("COMMENT: retval = Args_Basic_returnbackfcomplex( obj );\n");
    retval = Args_Basic_returnbackfcomplex( obj, &exception );
    MYASSERT( retval.real == 3.1F && retval.imaginary == 3.1F);
    MYASSERT( Args_Basic_passinfcomplex( obj, in, &exception ) == TRUE );
    
    MYASSERT( Args_Basic_passoutfcomplex( obj, &out, &exception ) == TRUE && 
	      out.real == 3.1F && out.imaginary == 3.1F );
    MYASSERT( Args_Basic_passinoutfcomplex( obj, &inout, &exception ) == TRUE && 
	      inout.real == 3.1F && inout.imaginary == -3.1F );
    printf("COMMENT: retval = Args_Basic_passeverywherefcomplex( obj, in, &out, &inout );\n");
    retval = Args_Basic_passeverywherefcomplex( obj, in, &out, &inout, &exception);
    MYASSERT( retval.real == 3.1F && retval.imaginary == 3.1F &&
              out.real == 3.1F && out.imaginary == 3.1F && 
              inout.real == 3.1F && inout.imaginary == 3.1F );
    
    Args_Basic_deleteRef( obj, &exception ); SIDL_REPORT(exception);
  }

  
  { /* dcomplex */
    struct sidl_dcomplex retval;
    struct sidl_dcomplex in = { 3.14, 3.14 };
    struct sidl_dcomplex out;
    struct sidl_dcomplex inout = { 3.14, 3.14 };
    Args_Basic obj = makeObject();

    printf("COMMENT: retval = Args_Basic_returnbackdcomplex( obj );\n");
    retval = Args_Basic_returnbackdcomplex( obj, &exception ); 
    MYASSERT( retval.real == 3.14 && retval.imaginary == 3.14);
    MYASSERT( Args_Basic_passindcomplex( obj, in, &exception ) == TRUE );
    MYASSERT( Args_Basic_passoutdcomplex( obj, &out, &exception ) == TRUE && 
	      out.real == 3.14 && out.imaginary == 3.14 );
    MYASSERT( Args_Basic_passinoutdcomplex( obj, &inout, &exception ) == TRUE && 
	      inout.real == 3.14 && inout.imaginary == -3.14 );
    printf("COMMENT: retval = Args_Basic_passeverywheredcomplex( obj, in, &out, &inout );\n");
    retval = Args_Basic_passeverywheredcomplex( obj, in, &out, &inout, &exception ); 
    MYASSERT( retval.real == 3.14 && retval.imaginary == 3.14 &&
	      out.real == 3.14 && out.imaginary == 3.14 && 
	      inout.real == 3.14 && inout.imaginary == 3.14 );
    
    Args_Basic_deleteRef( obj, &exception ); SIDL_REPORT(exception);
  }

  synch_RegOut_close(tracker, &exception); SIDL_REPORT(exception);
  synch_RegOut_deleteRef(tracker, &exception); SIDL_REPORT(exception);
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

