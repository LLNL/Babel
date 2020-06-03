#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "enums.h"
#include "synch.h"
#include "sidl_Exception.h"
#include "sidl_rmi_ProtocolFactory.h"

void declare_part(synch_RegOut tracker, int * part_no );
void end_part(synch_RegOut tracker,  int part_no, 
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
  declare_part(tracker,  &part_no ); \
  magicNumber = clearstack(magicNumber); \
  synch_RegOut_writeComment(tracker, #AAA, &exception);	SIDL_REPORT(exception);\
  if ( AAA ) result = synch_ResultType_PASS; \
  else result = synch_ResultType_FAIL;  \
  SIDL_REPORT(exception);\
  end_part( tracker, part_no, result);

#define MYXFAIL( AAA ) \
  declare_part( tracker, &part_no ); \
  magicNumber = clearstack(magicNumber); \
  synch_RegOut_writeComment(tracker, #AAA, &exception); SIDL_REPORT(exception);\
  if ( AAA ) result = synch_ResultType_XPASS; \
  else result = synch_ResultType_XFAIL;  \
  SIDL_REPORT(exception);\
  end_part( tracker, part_no, result);

#define MYBROKEN( AAA ) \
  declare_part( tracker, &part_no ); \
  synch_RegOut_writeComment(tracker, #AAA, &exception); SIDL_REPORT(exception);\
  end_part( tracker, part_no, sync_ResultType_FAIL );

static
struct enums_car__array *
createArray(void)
{
  struct enums_car__array *retval = enums_car__array_create1d(3);
  enums_car__array_set1(retval, 0, enums_car_ford);
  enums_car__array_set1(retval, 1, enums_car_mercedes);
  enums_car__array_set1(retval, 2, enums_car_porsche);
  return retval;
}

static int
checkArray(const struct enums_car__array *src)
{
  const enum enums_car__enum vals[] = {
    enums_car_ford, enums_car_mercedes, enums_car_porsche 
  };
  int retval = (src && (enums_car__array_length(src, 0) == 3)), i;
  for(i = 0; i < 3; ++i) {
    retval = retval && 
      (vals[i] == enums_car__array_get1(src, i +
                                        enums_car__array_lower(src, 0)));
  }
  return retval;
}

/* basic RMI support */
static char *remoteURL = NULL;

static sidl_bool withRMI( void ) {
  return remoteURL && *remoteURL;
}

static enums_colorwheel
make_colorwheel( void ) {
  enums_colorwheel obj = NULL;
  sidl_BaseInterface exception = NULL;

#ifdef WITH_RMI  
  if(withRMI()) {
    obj = enums_colorwheel__createRemote(remoteURL, &exception); SIDL_REPORT(exception);
    return obj;
  }
#endif
  
  obj = enums_colorwheel__create(&exception); SIDL_REPORT(exception);
  return obj;
  EXIT:
  exit(1);
}

static enums_cartest
make_cartest( void ) {
  enums_cartest obj = NULL;
  sidl_BaseInterface exception = NULL;

#ifdef WITH_RMI  
  if(withRMI()) {
    obj = enums_cartest__createRemote(remoteURL, &exception); SIDL_REPORT(exception);
    return obj;
  }
#endif
  
  obj = enums_cartest__create( &exception);SIDL_REPORT(exception);  
  return obj;
  
  EXIT:
  exit(1);
}

static enums_numbertest
make_numbertest( void ) {
  enums_numbertest obj = NULL;
  sidl_BaseInterface exception = NULL;

#ifdef WITH_RMI  
  if(withRMI()) {
    obj = enums_numbertest__createRemote(remoteURL, &exception); SIDL_REPORT(exception);
    return obj;
  }
#endif
  
  obj = enums_numbertest__create(&exception); SIDL_REPORT(exception);
  return obj;
  
  EXIT:
  exit(1);
}


int main(int argc, char** argv)
{
  sidl_BaseInterface exception = NULL;
  enum synch_ResultType__enum result = synch_ResultType_PASS; 
  int magicNumber = 1;
  int part_no = 0;
  synch_RegOut tracker = synch_RegOut__create(&exception); SIDL_REPORT(exception);

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
  
  synch_RegOut_setExpectations(tracker, 27, &exception); SIDL_REPORT(exception);
  
  { /* undefined integer values */
    enum enums_color__enum out = -5;
    enum enums_color__enum inout = enums_color_green;
    enums_colorwheel obj = make_colorwheel();
 
    MYASSERT( enums_colorwheel_returnback( obj, &exception) == enums_color_violet );
    MYASSERT( enums_colorwheel_passin( obj, enums_color_blue , &exception) == TRUE );
    MYASSERT( enums_colorwheel_passout( obj, &out, &exception ) == TRUE && out == enums_color_violet );
    MYASSERT( enums_colorwheel_passinout( obj, &inout, &exception ) == TRUE && inout == enums_color_red );
    out = enums_color_red;
    MYASSERT( enums_colorwheel_passeverywhere( obj, enums_color_blue, &out, &inout, 
					       &exception ) == enums_color_violet &&
	      out == enums_color_violet && inout == enums_color_green );
    enums_colorwheel_deleteRef( obj, &exception );SIDL_REPORT(exception);
  }

  { /* fully defined integer values */
    enum enums_car__enum out = -5;
    enum enums_car__enum inout = enums_car_ford;
    struct enums_car__array *tin, *tinout, *tout, *tret;
    enums_cartest obj = make_cartest();
 
    MYASSERT( enums_cartest_returnback( obj , &exception) == enums_car_porsche );
    MYASSERT( enums_cartest_passin( obj, enums_car_mercedes , &exception) == TRUE );
    MYASSERT( enums_cartest_passout( obj, &out , &exception) == TRUE && out == enums_car_ford );
    MYASSERT( enums_cartest_passinout( obj, &inout , &exception) == TRUE && inout == enums_car_porsche );
    out = -5;
    MYASSERT( enums_cartest_passeverywhere( obj, enums_car_mercedes, &out, &inout , &exception) == enums_car_porsche
	      && out == enums_car_ford && inout == enums_car_mercedes );
    tin = createArray();
    tinout = createArray();
    synch_RegOut_writeComment(tracker, "Calling enums.cartest.passarray", &exception); SIDL_REPORT(exception);\
    tret = enums_cartest_passarray( obj, tin, &tout, &tinout, &exception);
    SIDL_REPORT(exception);
    MYASSERT(checkArray(tret) && checkArray(tout) && checkArray(tinout) &&
             checkArray(tin));
    enums_car__array_deleteRef(tret);
    enums_car__array_deleteRef(tin);
    enums_car__array_deleteRef(tinout);
    enums_car__array_deleteRef(tout);
    enums_cartest_deleteRef( obj , &exception);SIDL_REPORT(exception);
  }

  { /* partially defined integer values */
    enum enums_number__enum out=-5;
    enum enums_number__enum inout = enums_number_zero;
    enums_numbertest obj = make_numbertest();
    SIDL_REPORT(exception);
    MYASSERT( enums_numbertest_returnback( obj , &exception) == enums_number_notOne );
    MYASSERT( enums_numbertest_passin( obj, enums_number_notZero , &exception) == TRUE );
    MYASSERT( enums_numbertest_passout( obj, &out , &exception) == TRUE && out == enums_number_negOne );
    MYASSERT( enums_numbertest_passinout( obj, &inout , &exception) == TRUE && inout == enums_number_notZero );
    out = -5;
    MYASSERT( enums_numbertest_passeverywhere( obj, enums_number_notZero, &out, &inout , &exception) == enums_number_notOne &&
	      out == enums_number_negOne && inout == enums_number_zero );
    enums_numbertest_deleteRef( obj , &exception);SIDL_REPORT(exception);
  }

  {
    const int32_t numElem[] = { 2 };
    const int32_t stride[] = { 2 };
    struct enums_car__array *enumArray = enums_car__array_create1d(4);
    struct enums_car__array *slicedArray = NULL;
    struct enums_car__array *cpArray = NULL;
    MYASSERT( enumArray );
    enums_car__array_set1(enumArray, 0, enums_car_porsche);
    enums_car__array_set1(enumArray, 1, enums_car_ford);
    enums_car__array_set1(enumArray, 2, enums_car_mercedes);
    enums_car__array_set1(enumArray, 3, enums_car_porsche);
    MYASSERT( enums_car_porsche == enums_car__array_get1(enumArray, 0) );
    MYASSERT( enums_car_ford == enums_car__array_get1(enumArray, 1) );
    MYASSERT( enums_car_mercedes == enums_car__array_get1(enumArray, 2) );
    MYASSERT( enums_car_porsche == enums_car__array_get1(enumArray, 3) );
    slicedArray =
      enums_car__array_slice(enumArray, 1, numElem, NULL, stride, NULL);
    MYASSERT( slicedArray );
    MYASSERT( enums_car_porsche == enums_car__array_get1(slicedArray, 0) );
    MYASSERT( enums_car_mercedes == enums_car__array_get1(slicedArray, 1));
    cpArray = enums_car__array_smartCopy(slicedArray);
    MYASSERT(cpArray);
    MYASSERT( enums_car_porsche == enums_car__array_get1(cpArray, 0) );
    MYASSERT( enums_car_mercedes == enums_car__array_get1(cpArray, 1));
    enums_car__array_deleteRef(cpArray);
    enums_car__array_deleteRef(slicedArray);
    enums_car__array_deleteRef(enumArray);
  }
  synch_RegOut_close(tracker, &exception);SIDL_REPORT(exception);
  synch_RegOut_deleteRef(tracker, &exception);SIDL_REPORT(exception);
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

void declare_part( synch_RegOut tracker,  int * part_no ) {
  sidl_BaseInterface throwaway = NULL;
  synch_RegOut_startPart(tracker, ++(*part_no), &throwaway);
}

void end_part( synch_RegOut tracker,
              int part_no, 
               enum synch_ResultType__enum result) 
{
  sidl_BaseInterface throwaway = NULL;
  synch_RegOut_endPart(tracker, part_no, result, &throwaway);
}
