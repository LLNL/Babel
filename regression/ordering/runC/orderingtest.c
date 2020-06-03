/*
 * File:        orderingtest.c
 * Copyright:   (c) 2002 Lawrence Livermore National Security, LLC
 * Revision:    @(#) $Revision: 7452 $
 * Date:        $Date: 2012-04-19 09:44:42 -0700 (Thu, 19 Apr 2012) $
 * Description: Regression test for array ordering code
 *
 */

#include "Ordering_IntOrderTest.h"
#include <stdio.h>
#include <stdlib.h>
#include "synch.h"
#include "sidl_Exception.h"

static void declare_part(synch_RegOut tracker, int * part_no ) {
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

static void end_part( synch_RegOut tracker, int part_no, 
                      enum synch_ResultType__enum result) 
{
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
  synch_RegOut_writeComment(tracker, #AAA, &exception); SIDL_REPORT(exception); \
  if ( AAA ) { result = synch_ResultType_PASS;  SIDL_REPORT(exception); }\
  else result = synch_ResultType_FAIL;  \
  end_part( tracker, part_no, result);


static const int arraySize = 7;

static struct sidl_int__array*
make1DIMatrix(const int32_t size)
{
  struct sidl_int__array *result=NULL;
  int32_t i;
  result = sidl_int__array_create1d(size);
  for(i = 0; i < size; ++i ){
    sidlArrayElem1(result, i) = i;
  }
  return result;
}

int main(int argc, char **argv)
{
  int magicNumber = 1;
  sidl_BaseInterface exception = NULL;
  enum synch_ResultType__enum result = synch_ResultType_PASS;
  int part_no = 0;
  struct sidl_int__array *A = NULL;
  synch_RegOut tracker = synch_RegOut__create(&exception);SIDL_REPORT(exception);
  
  synch_RegOut_setExpectations(tracker, 32, &exception);SIDL_REPORT(exception);
  A = Ordering_IntOrderTest_makeColumnIMatrix(arraySize, TRUE, &exception);SIDL_REPORT(exception);
  MYASSERT(A && sidl_int__array_isColumnOrder(A));
  MYASSERT(Ordering_IntOrderTest_isIMatrixTwo(A, &exception));
  MYASSERT(Ordering_IntOrderTest_isColumnIMatrixTwo(A, &exception));
  MYASSERT(Ordering_IntOrderTest_isRowIMatrixTwo(A, &exception));
  Ordering_IntOrderTest_ensureRow(&A, &exception);SIDL_REPORT(exception);
  MYASSERT(A && sidl_int__array_isRowOrder(A) &&
           Ordering_IntOrderTest_isIMatrixTwo(A, &exception));
  Ordering_IntOrderTest_ensureColumn(&A, &exception);
  MYASSERT(A && sidl_int__array_isColumnOrder(A) &&
           Ordering_IntOrderTest_isIMatrixTwo(A, &exception));
  Ordering_IntOrderTest_ensureRow(&A, &exception);SIDL_REPORT(exception);
  MYASSERT(A && sidl_int__array_isRowOrder(A) &&
           Ordering_IntOrderTest_isIMatrixTwo(A, &exception));
  Ordering_IntOrderTest_ensureColumn(&A, &exception);SIDL_REPORT(exception);
  MYASSERT(A && sidl_int__array_isColumnOrder(A) &&
           Ordering_IntOrderTest_isIMatrixTwo(A, &exception));
  sidl_int__array_deleteRef(A); A = NULL;

  A = Ordering_IntOrderTest_makeColumnIMatrix(arraySize, FALSE, &exception);SIDL_REPORT(exception);
  MYASSERT(A && sidl_int__array_isColumnOrder(A));
  MYASSERT(Ordering_IntOrderTest_isIMatrixTwo(A, &exception));
  sidl_int__array_deleteRef(A); A = NULL;

  A = Ordering_IntOrderTest_makeRowIMatrix(arraySize, TRUE, &exception);SIDL_REPORT(exception);
  MYASSERT(A && sidl_int__array_isRowOrder(A));
  MYASSERT(Ordering_IntOrderTest_isIMatrixTwo(A, &exception));
  MYASSERT(Ordering_IntOrderTest_isColumnIMatrixTwo(A, &exception));
  MYASSERT(Ordering_IntOrderTest_isRowIMatrixTwo(A, &exception));
  sidl_int__array_deleteRef(A); A = NULL;

  A = Ordering_IntOrderTest_makeRowIMatrix(arraySize, FALSE, &exception);SIDL_REPORT(exception);
  MYASSERT(A && sidl_int__array_isRowOrder(A));
  MYASSERT(Ordering_IntOrderTest_isIMatrixTwo(A, &exception));
  sidl_int__array_deleteRef(A); A = NULL;

  Ordering_IntOrderTest_createColumnIMatrix(arraySize, TRUE, &A, &exception);SIDL_REPORT(exception);
  MYASSERT(A && sidl_int__array_isColumnOrder(A));
  MYASSERT(Ordering_IntOrderTest_isIMatrixTwo(A, &exception));
  sidl_int__array_deleteRef(A); A = NULL;

  Ordering_IntOrderTest_createColumnIMatrix(arraySize, FALSE, &A, &exception);SIDL_REPORT(exception);
  MYASSERT(A && sidl_int__array_isColumnOrder(A));
  MYASSERT(Ordering_IntOrderTest_isIMatrixTwo(A, &exception));
  sidl_int__array_deleteRef(A); A = NULL;

  Ordering_IntOrderTest_createRowIMatrix(arraySize, TRUE, &A, &exception);SIDL_REPORT(exception);
  MYASSERT(A && sidl_int__array_isRowOrder(A));
  MYASSERT(Ordering_IntOrderTest_isIMatrixTwo(A, &exception));
  sidl_int__array_deleteRef(A); A = NULL;

  Ordering_IntOrderTest_createRowIMatrix(arraySize, FALSE, &A, &exception);SIDL_REPORT(exception);
  MYASSERT(A && sidl_int__array_isRowOrder(A));
  MYASSERT(Ordering_IntOrderTest_isIMatrixTwo(A, &exception));
  sidl_int__array_deleteRef(A); A = NULL;

  A = Ordering_IntOrderTest_makeIMatrix(arraySize, TRUE, &exception);SIDL_REPORT(exception);
  MYASSERT(A && Ordering_IntOrderTest_isIMatrixFour(A, &exception));
  MYASSERT(Ordering_IntOrderTest_isColumnIMatrixFour(A, &exception));
  MYASSERT(Ordering_IntOrderTest_isRowIMatrixFour(A, &exception));
  sidl_int__array_deleteRef(A); A = NULL;

  A = make1DIMatrix(arraySize);
  MYASSERT(A && Ordering_IntOrderTest_isIMatrixOne(A, &exception));
  MYASSERT(Ordering_IntOrderTest_isColumnIMatrixOne(A, &exception));
  MYASSERT(Ordering_IntOrderTest_isRowIMatrixOne(A, &exception));
  sidl_int__array_deleteRef(A); A = NULL;

  MYASSERT(Ordering_IntOrderTest_isSliceWorking(TRUE, &exception));
  MYASSERT(Ordering_IntOrderTest_isSliceWorking(FALSE, &exception));

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
