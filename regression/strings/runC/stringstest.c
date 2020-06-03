
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "Strings.h"
#include "synch.h"
#include "sidl_Exception.h"

#define MYASSERT( AAA ) {\
  int _result; \
  synch_RegOut_startPart(tracker, ++part_no, &exception); SIDL_REPORT(exception);\
  synch_RegOut_writeComment(tracker, #AAA, &exception); SIDL_REPORT(exception);\
  _result = (int) (AAA); SIDL_REPORT(exception);\
  if ( _result ) result = synch_ResultType_PASS; \
  else result = synch_ResultType_FAIL;  \
  synch_RegOut_endPart(tracker, part_no, result, &exception); \
  SIDL_REPORT(exception);\
}


/* basic RMI support */
static char *remoteURL = NULL;

static sidl_bool withRMI( void ) {
  return remoteURL && *remoteURL;
}

static Strings_Cstring
makeObject( void ) {
  Strings_Cstring obj = NULL;
  sidl_BaseInterface exception = NULL;

#ifdef WITH_RMI  
  if(withRMI()) {
    obj = Strings_Cstring__createRemote(remoteURL, &exception); SIDL_REPORT(exception);
    return obj;
  }
#endif
  
  obj = Strings_Cstring__create(&exception); SIDL_REPORT(exception);
  return obj;
  EXIT:
  exit(1);
}

int main(int argc, char **argv) { 
  sidl_BaseInterface exception = 0;
  enum synch_ResultType__enum result = synch_ResultType_PASS; 
  int part_no = 0;
  Strings_Cstring obj;
  synch_RegOut tracker = synch_RegOut__create(&exception);
  SIDL_REPORT(exception);
  synch_RegOut_setExpectations(tracker, -1, &exception);
  SIDL_REPORT(exception);
  
  { 
    char* out;
    char* temp;
    char* inout = (char*)(malloc(sizeof(char)*6));

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
    
    strcpy(inout,"Three");
    MYASSERT(obj = makeObject());
 
    MYASSERT((temp =  Strings_Cstring_returnback( obj, TRUE, &exception )) &&
             !strcmp(temp, "Three"));
    free(temp);
    MYASSERT( (!(temp = Strings_Cstring_returnback(obj, FALSE, &exception))) || 
               !strcmp(temp, ""));
    if (temp) free(temp);
    MYASSERT( Strings_Cstring_passin( obj, "Three", &exception ) == TRUE );
    MYASSERT( Strings_Cstring_passin( obj, NULL, &exception ) == FALSE );
    MYASSERT( Strings_Cstring_passout( obj, TRUE, &out, &exception ) == TRUE && !(strcmp(out, "Three")));
    free(out);
    out = NULL;
    MYASSERT( Strings_Cstring_passout( obj, FALSE, &out, &exception ) == FALSE && 
              !(out && strcmp(out, "")));
    if (out) free(out);
    MYASSERT( Strings_Cstring_passinout( obj, &inout, &exception ) == TRUE && !(strcmp(inout,"threes")));
    MYASSERT( (temp=Strings_Cstring_passeverywhere( obj, "Three", &out,
                                                   &inout, &exception )) &&
              !(strcmp(temp, "Three"))  &&
	      !(strcmp(out, "Three")) && !(strcmp(inout, "Three")) );
    MYASSERT( Strings_Cstring_mixedarguments( obj, "Test", 'z', "Test", 'z', &exception)
              );
    MYASSERT( !Strings_Cstring_mixedarguments( obj, "Not", 'A', "Equal", 'a', &exception)
             );

    free(out);
    free(temp);
    free(inout);
    inout = NULL;
    MYASSERT( (Strings_Cstring_passinout( obj, &inout, &exception) == FALSE));
    if (inout) free(inout);
    
    Strings_Cstring_deleteRef( obj, &exception ); SIDL_REPORT(exception);
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
