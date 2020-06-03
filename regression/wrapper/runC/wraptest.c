#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "wrapper_User.h"
#include "wrapper_Data.h"
#include "wrapper_Data_Impl.h"
#include "synch.h"
#include "sidl_Exception.h"

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

int main(int argc, char** argv) { 
  sidl_BaseInterface exception = NULL;
  enum synch_ResultType__enum result = synch_ResultType_PASS;
  int magicNumber = 1;
  int part_no = 0;
  const char *language = "";


  wrapper_Data data = NULL;
  wrapper_User user = NULL;
  struct wrapper_Data__data *d_data = NULL;
  struct wrapper_Data__data *dptr = NULL;
  synch_RegOut tracker = synch_RegOut__create(&exception); 

  SIDL_REPORT(exception);
  synch_RegOut_setExpectations(tracker, -1, &exception); SIDL_REPORT(exception);
  if (argc == 2){
    language = argv[1];
  }

  /*Create the data*/
  dptr = malloc(sizeof(struct wrapper_Data__data));
  /*Wrap the data*/
  data = wrapper_Data__wrapObj(dptr, &exception);SIDL_REPORT(exception);
  user = wrapper_User__create(&exception);SIDL_REPORT(exception);

  /*Check everthing was created properly*/
  MYASSERT( data != NULL );
  MYASSERT( user != NULL );

  /* Get a copy of the data from the object*/
  d_data = wrapper_Data__get_data(data);

  /* Check that it's the same pointer we wrapper*/
  MYASSERT( d_data != NULL );
  MYASSERT( d_data == dptr );
  MYASSERT( strcmp(d_data->d_ctorTest, "ctor was run") == 0);

  /* Test teh data setting*/
  wrapper_User_accept(user, data, &exception);

  MYASSERT( strcmp(d_data->d_string, "Hello World!") == 0);
  MYASSERT( d_data->d_int == 3);

  wrapper_Data_deleteRef(data, &exception); SIDL_REPORT(exception);
  wrapper_User_deleteRef(user, &exception); SIDL_REPORT(exception);
  synch_RegOut_close(tracker, &exception); SIDL_REPORT(exception);
  synch_RegOut_deleteRef(tracker, &exception); SIDL_REPORT(exception);
  free((void *)dptr);
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
