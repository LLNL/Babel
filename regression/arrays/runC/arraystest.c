/*
 * File:        arraystests.c
 * Copyright:   (c) 2001-2003 Lawrence Livermore National Security, LLC
 * Revision:    @(#) $Revision: 7450 $
 * Date:        $Date: 2012-04-19 09:08:27 -0700 (Thu, 19 Apr 2012) $
 * Description: Simple test on the ArrayTest static methods
 *
 */

#include "ArrayTest_ArrayOps.h"
#include "sidl_header.h"
#include <stdlib.h>
#include <stddef.h>
#include <time.h>
#include <string.h>
#include <stdio.h>
#include "synch.h"
#include "sidl_Exception.h"

/*static const int TEST_SIZE = 345; / size of one dimensional arrays */
/*static const int TEST_DIM1 = 17;  /first dimension of 2-d arrays */
/*static const int TEST_DIM2 = 13; /second dimension of 2-d arrays */
/*static const int TEST_DIM3 = 4;   /third dimension of 3-d arrays */

#define TEST_SIZE 345 /* size of one dimensional arrays */
#define TEST_DIM1 17 /* first dimension of 2-d arrays */
#define TEST_DIM2 13 /* second dimension of 2-d arrays */
#define TEST_DIM3 4  /* third dimension of 3-d arrays */

static void declare_part(synch_RegOut tracker,
                         int * part_no , sidl_BaseInterface* _ex)
{
  synch_RegOut_startPart(tracker,  ++(*part_no), _ex);
}

static int isPrime(const int64_t num) {
  register int64_t i;
  for(i = 3; i*i <= num; ++i) {
    if (!(num % i)) return 0;
  }
  return 1;
}

static int64_t
nextPrime(int64_t prev) {
  if (prev <= 1L) {
    return 2L;
  }
  else if (prev == 2L) {
    return 3L;
  }
  else {
    do {
      prev += 2;
    } while (!isPrime(prev));
    return prev;
  }
}

static void end_part(synch_RegOut tracker,
                     int part_no,
                     enum synch_ResultType__enum result, sidl_BaseInterface* _ex) 
{
  synch_RegOut_endPart(tracker, part_no, result, _ex);
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

#define MYASSERT( CMT, AAA ) \
  declare_part( tracker,  &part_no,&exception );  SIDL_REPORT(exception);		\
  magicNumber = clearstack(magicNumber);				\
  synch_RegOut_writeComment(tracker,  #CMT ": " #AAA,&exception);  SIDL_REPORT(exception); \
  if ( AAA ) {result = synch_ResultType_PASS;  SIDL_REPORT(exception); } \
  else {result = synch_ResultType_FAIL; SIDL_REPORT(exception);	} \
  end_part( tracker, part_no, result,&exception); SIDL_REPORT(exception);

int main(int argc, char **argv) {
  enum synch_ResultType__enum result = synch_ResultType_PASS;
  sidl_BaseInterface exception = NULL;
  int part_no = 0;
  int magicNumber = 1;
  synch_RegOut tracker = synch_RegOut__create(&exception);
  
  struct sidl_bool__array     *barray;
  struct sidl_char__array     *carray;
  struct sidl_int__array      *iarray;
  struct sidl_dcomplex__array *dcarray;
  struct sidl_fcomplex__array *fcarray;
  struct sidl_long__array     *larray;
  struct sidl_double__array   *darray;
  struct sidl_float__array    *farray;
  struct sidl_string__array   *sarray;
  struct sidl__array          *garray;

  struct ArrayTest_ArrayOps__array *oarray;
  SIDL_REPORT(exception);
  srand(time(NULL));

  synch_RegOut_setExpectations(tracker, 161,&exception);SIDL_REPORT(exception);

  barray = NULL;
  carray = NULL;
  iarray = NULL;
  dcarray = NULL;
  fcarray = NULL;
  larray = NULL;
  darray = NULL;
  sarray = NULL;
  farray = NULL;
  garray = NULL;

  magicNumber = clearstack(magicNumber);
  barray = ArrayTest_ArrayOps_createBool(TEST_SIZE,&exception);SIDL_REPORT(exception);
  MYASSERT(createBool, barray);
  MYASSERT(createBool, ArrayTest_ArrayOps_checkBool(barray,&exception) == TRUE);
  MYASSERT(createBool, ArrayTest_ArrayOps_reverseBool(&barray, TRUE,&exception) == TRUE);
  magicNumber = clearstack(magicNumber);
  sidl_bool__array_deleteRef(barray);
  /* initialize out array reference to a random value to check that it can
     be arbitrary */
  barray = (struct sidl_bool__array *)(ptrdiff_t)rand();

  ArrayTest_ArrayOps_makeBool(218, &barray,&exception);SIDL_REPORT(exception);
  MYASSERT(makeBool218, ArrayTest_ArrayOps_checkBool(barray,&exception) == TRUE);
  MYASSERT(makeBool218, 
            ArrayTest_ArrayOps_reverseBool(&barray, FALSE,&exception) == TRUE);
  MYASSERT(makeBool218, ArrayTest_ArrayOps_checkBool(barray,&exception) == FALSE);
  magicNumber = clearstack(magicNumber);
  
  sidl_bool__array_deleteRef(barray);
  barray = (struct sidl_bool__array *)(ptrdiff_t)rand();
  ArrayTest_ArrayOps_makeBool(9, &barray,&exception);SIDL_REPORT(exception);
  MYASSERT(makeBool9, ArrayTest_ArrayOps_reverseBool(&barray, FALSE,&exception) == TRUE);
  MYASSERT(makeBool9, ArrayTest_ArrayOps_checkBool(barray,&exception) == TRUE);
  magicNumber = clearstack(magicNumber);
  sidl_bool__array_deleteRef(barray);
  
  barray = (struct sidl_bool__array *)(ptrdiff_t)rand();
  MYASSERT(createBoolNegOne, ArrayTest_ArrayOps_createBool(-1,&exception) == NULL);
  ArrayTest_ArrayOps_makeBool(-1, &barray,&exception);SIDL_REPORT(exception);
  MYASSERT(makeBoolNegOne, barray == NULL);
  if (barray) {
    sidl_bool__array_deleteRef(barray);
    barray = NULL;
  }
  
  magicNumber = clearstack(magicNumber);
  carray = ArrayTest_ArrayOps_createChar(TEST_SIZE,&exception);SIDL_REPORT(exception);
  MYASSERT(createChar, carray);
  MYASSERT(createChar, ArrayTest_ArrayOps_checkChar(carray,&exception) == TRUE);
  MYASSERT(createChar, ArrayTest_ArrayOps_reverseChar(&carray, TRUE,&exception) == TRUE);
  magicNumber = clearstack(magicNumber);
  sidl_char__array_deleteRef(carray);

  carray = (struct sidl_char__array *)(ptrdiff_t)rand();
  ArrayTest_ArrayOps_makeChar(218, &carray,&exception);SIDL_REPORT(exception);
  MYASSERT(makeChar, ArrayTest_ArrayOps_checkChar(carray,&exception) == TRUE);
  MYASSERT(makeChar, ArrayTest_ArrayOps_reverseChar(&carray, FALSE,&exception) == TRUE);
  MYASSERT(makeChar, ArrayTest_ArrayOps_checkChar(carray,&exception) == FALSE);
  magicNumber = clearstack(magicNumber);
  sidl_char__array_deleteRef(carray);

  carray = (struct sidl_char__array *)(ptrdiff_t)rand();
  MYASSERT(createCharNegOne, ArrayTest_ArrayOps_createChar(-1,&exception) == NULL);
  ArrayTest_ArrayOps_makeChar(-1, &carray,&exception);SIDL_REPORT(exception);
  MYASSERT(makeCharNegOne, carray == NULL);
  if (carray) {
    sidl_char__array_deleteRef(carray);
    carray = NULL;
  }
  
  magicNumber = clearstack(magicNumber);
  iarray = ArrayTest_ArrayOps_createInt(TEST_SIZE,&exception);SIDL_REPORT(exception);
  MYASSERT(createInt, iarray);
  MYASSERT(createInt, ArrayTest_ArrayOps_checkInt(iarray,&exception) == TRUE);
  MYASSERT(createInt, ArrayTest_ArrayOps_reverseInt(&iarray, TRUE,&exception) == TRUE);
  magicNumber = clearstack(magicNumber);
  sidl_int__array_deleteRef(iarray);

  iarray = (struct sidl_int__array *)(ptrdiff_t)rand();
  ArrayTest_ArrayOps_makeInt(218, &iarray,&exception);SIDL_REPORT(exception);
  MYASSERT(makeInt, ArrayTest_ArrayOps_checkInt(iarray,&exception) == TRUE);
  MYASSERT(makeInt, ArrayTest_ArrayOps_reverseInt(&iarray, FALSE,&exception) == TRUE);
  MYASSERT(makeInt, ArrayTest_ArrayOps_checkInt(iarray,&exception) == FALSE);
  magicNumber = clearstack(magicNumber);
  sidl_int__array_deleteRef(iarray);

  iarray = (struct sidl_int__array *)(ptrdiff_t)rand();
  MYASSERT(createIntNegOne, ArrayTest_ArrayOps_createInt(-1,&exception) == NULL);
  ArrayTest_ArrayOps_makeInt(-1, &iarray,&exception);SIDL_REPORT(exception);
  MYASSERT(makeIntNegOne, iarray == NULL);
  if (iarray) {
    sidl_int__array_deleteRef(iarray);
    iarray = NULL;
  }
  
  
  magicNumber = clearstack(magicNumber);
  larray = ArrayTest_ArrayOps_createLong(TEST_SIZE,&exception);SIDL_REPORT(exception);
  MYASSERT(createLong, larray);
  MYASSERT(createLong, ArrayTest_ArrayOps_checkLong(larray,&exception) == TRUE);
  MYASSERT(createLong, ArrayTest_ArrayOps_reverseLong(&larray, TRUE,&exception) == TRUE);
  magicNumber = clearstack(magicNumber);
  sidl_long__array_deleteRef(larray);
  larray = (struct sidl_long__array *)(ptrdiff_t)rand();
  ArrayTest_ArrayOps_makeLong(218, &larray,&exception);SIDL_REPORT(exception);
  MYASSERT(makeLong, ArrayTest_ArrayOps_checkLong(larray,&exception) == TRUE);
  MYASSERT(makeLong, ArrayTest_ArrayOps_reverseLong(&larray, FALSE,&exception) == TRUE);
  MYASSERT(makeLong, ArrayTest_ArrayOps_checkLong(larray,&exception) == FALSE);
  magicNumber = clearstack(magicNumber);
  sidl_long__array_deleteRef(larray);

  MYASSERT(createLongNegOne, ArrayTest_ArrayOps_createLong(-1,&exception) == NULL);
  larray = (struct sidl_long__array *)(ptrdiff_t)rand();
  ArrayTest_ArrayOps_makeLong(-1, &larray,&exception);SIDL_REPORT(exception);
  MYASSERT(makeLongNegOne, larray == NULL);
  if (larray) {
    sidl_long__array_deleteRef(larray);
    larray = NULL;
  }
  
  magicNumber = clearstack(magicNumber);
  sarray = ArrayTest_ArrayOps_createString(TEST_SIZE,&exception);SIDL_REPORT(exception);
  MYASSERT(createString, sarray);
  MYASSERT(createString, ArrayTest_ArrayOps_checkString(sarray,&exception) == TRUE);
  MYASSERT(createString, 
           ArrayTest_ArrayOps_reverseString(&sarray, TRUE,&exception) == TRUE);
  magicNumber = clearstack(magicNumber);
  sidl_string__array_deleteRef(sarray);
  sarray = (struct sidl_string__array *)(ptrdiff_t)rand();
  ArrayTest_ArrayOps_makeString(218, &sarray,&exception);SIDL_REPORT(exception);
  MYASSERT(makeString, ArrayTest_ArrayOps_checkString(sarray,&exception) == TRUE);
  MYASSERT(makeString, 
           ArrayTest_ArrayOps_reverseString(&sarray, FALSE,&exception) == TRUE);
  MYASSERT(makeString, ArrayTest_ArrayOps_checkString(sarray,&exception) == FALSE);
  magicNumber = clearstack(magicNumber);
  sidl_string__array_deleteRef(sarray);

  MYASSERT(createStringNegOne, ArrayTest_ArrayOps_createString(-1,&exception) == NULL);
  sarray = (struct sidl_string__array *)(ptrdiff_t)rand();
  ArrayTest_ArrayOps_makeString(-1, &sarray,&exception);SIDL_REPORT(exception);
  MYASSERT(makeStringNegOne, sarray == NULL);
  if (sarray) {
    sidl_string__array_deleteRef(sarray);
    sarray = NULL;
  }
  
  magicNumber = clearstack(magicNumber);
  darray = ArrayTest_ArrayOps_createDouble(TEST_SIZE,&exception);SIDL_REPORT(exception);
  MYASSERT(createDouble, darray);
  MYASSERT(createDouble, ArrayTest_ArrayOps_checkDouble(darray,&exception) == TRUE);
  MYASSERT(createDouble, 
           ArrayTest_ArrayOps_reverseDouble(&darray, TRUE,&exception) == TRUE);
  magicNumber = clearstack(magicNumber);
  sidl_double__array_deleteRef(darray);
  darray = (struct sidl_double__array *)(ptrdiff_t)rand();
  ArrayTest_ArrayOps_makeDouble(218, &darray,&exception);SIDL_REPORT(exception);
  MYASSERT(makeDouble, ArrayTest_ArrayOps_checkDouble(darray,&exception) == TRUE);
  MYASSERT(makeDouble, 
           ArrayTest_ArrayOps_reverseDouble(&darray, FALSE,&exception) == TRUE);
  MYASSERT(makeDouble, ArrayTest_ArrayOps_checkDouble(darray,&exception) == FALSE);
  magicNumber = clearstack(magicNumber);
  sidl_double__array_deleteRef(darray);

  MYASSERT(createDoubleNegOne, ArrayTest_ArrayOps_createDouble(-1,&exception) == NULL);
  darray = (struct sidl_double__array *)(ptrdiff_t)rand();
  ArrayTest_ArrayOps_makeDouble(-1, &darray,&exception);SIDL_REPORT(exception);
  MYASSERT(makeDoubleNegOne, darray == NULL);
  if (darray) {
    sidl_double__array_deleteRef(darray);
    darray = NULL;
  }
  
  magicNumber = clearstack(magicNumber);
  farray = ArrayTest_ArrayOps_createFloat(TEST_SIZE,&exception);SIDL_REPORT(exception);
  MYASSERT(createFloat, farray);
  MYASSERT(createFloat, ArrayTest_ArrayOps_checkFloat(farray,&exception) == TRUE);
  MYASSERT(createFloat, 
           ArrayTest_ArrayOps_reverseFloat(&farray, TRUE,&exception) == TRUE);
  magicNumber = clearstack(magicNumber);
  sidl_float__array_deleteRef(farray);
  farray = (struct sidl_float__array *)(ptrdiff_t)rand();
  ArrayTest_ArrayOps_makeFloat(218, &farray,&exception);SIDL_REPORT(exception);
  MYASSERT(makeFloat, ArrayTest_ArrayOps_checkFloat(farray,&exception) == TRUE);
  MYASSERT(makeFloat, 
           ArrayTest_ArrayOps_reverseFloat(&farray, FALSE,&exception) == TRUE);
  MYASSERT(makeFloat, ArrayTest_ArrayOps_checkFloat(farray,&exception) == FALSE);
  magicNumber = clearstack(magicNumber);
  sidl_float__array_deleteRef(farray);
  
  MYASSERT(createFloatNegOne, ArrayTest_ArrayOps_createFloat(-1,&exception) == NULL);
  farray = (struct sidl_float__array *)(ptrdiff_t)rand();
  ArrayTest_ArrayOps_makeFloat(-1, &farray,&exception);SIDL_REPORT(exception);
  MYASSERT(makeFloatNegOne, farray == NULL);
  if (farray) {
    sidl_float__array_deleteRef(farray);
    farray = NULL;
  }
  magicNumber = clearstack(magicNumber);
  fcarray = ArrayTest_ArrayOps_createFcomplex(TEST_SIZE,&exception);SIDL_REPORT(exception);
  MYASSERT(createFcomplex, fcarray);
  MYASSERT(createFcomplex, ArrayTest_ArrayOps_checkFcomplex(fcarray,&exception) == TRUE);
  MYASSERT(createFcomplex, 
           ArrayTest_ArrayOps_reverseFcomplex(&fcarray, TRUE,&exception) == TRUE);
  magicNumber = clearstack(magicNumber);
  sidl_fcomplex__array_deleteRef(fcarray);
  fcarray = (struct sidl_fcomplex__array *)(ptrdiff_t)rand();
  ArrayTest_ArrayOps_makeFcomplex(218, &fcarray,&exception);SIDL_REPORT(exception);
  MYASSERT(makeFcomplex, ArrayTest_ArrayOps_checkFcomplex(fcarray,&exception) == TRUE);
  MYASSERT(makeFcomplex, 
           ArrayTest_ArrayOps_reverseFcomplex(&fcarray, FALSE,&exception) == TRUE);
  MYASSERT(makeFcomplex, ArrayTest_ArrayOps_checkFcomplex(fcarray,&exception) == FALSE);
  magicNumber = clearstack(magicNumber);
  sidl_fcomplex__array_deleteRef(fcarray);
  
  MYASSERT(createFcomplexNegOne, 
           ArrayTest_ArrayOps_createFcomplex(-1,&exception) == NULL);
  fcarray = (struct sidl_fcomplex__array *)(ptrdiff_t)rand();
  ArrayTest_ArrayOps_makeFcomplex(-1, &fcarray,&exception);SIDL_REPORT(exception);
  MYASSERT(makeFcomplexNegOne, fcarray == NULL);
  if (fcarray) {
    sidl_fcomplex__array_deleteRef(fcarray);
    fcarray = NULL;
  }
  
  magicNumber = clearstack(magicNumber);
  dcarray = ArrayTest_ArrayOps_createDcomplex(TEST_SIZE,&exception);SIDL_REPORT(exception);
  MYASSERT(createDcomplex, dcarray);
  MYASSERT(createDcomplex, ArrayTest_ArrayOps_checkDcomplex(dcarray,&exception) == TRUE);
  MYASSERT(createDcomplex, 
           ArrayTest_ArrayOps_reverseDcomplex(&dcarray, TRUE,&exception) == TRUE);
  magicNumber = clearstack(magicNumber);
  sidl_dcomplex__array_deleteRef(dcarray);
  dcarray = (struct sidl_dcomplex__array *)(ptrdiff_t)rand();
  ArrayTest_ArrayOps_makeDcomplex(218, &dcarray,&exception);SIDL_REPORT(exception);
  MYASSERT(makeDcomplex, ArrayTest_ArrayOps_checkDcomplex(dcarray,&exception) == TRUE);
  MYASSERT(makeDcomplex, 
           ArrayTest_ArrayOps_reverseDcomplex(&dcarray, FALSE,&exception) == TRUE);
  MYASSERT(makeDcomplex, ArrayTest_ArrayOps_checkDcomplex(dcarray,&exception) == FALSE);
  magicNumber = clearstack(magicNumber);
  sidl_dcomplex__array_deleteRef(dcarray);

  MYASSERT(createDcomplexNegOne, 
           ArrayTest_ArrayOps_createDcomplex(-1,&exception) == NULL);
  dcarray = (struct sidl_dcomplex__array *)(ptrdiff_t)rand();
  ArrayTest_ArrayOps_makeDcomplex(-1, &dcarray,&exception);SIDL_REPORT(exception);
  MYASSERT(makeDcomplexNegOne, dcarray == NULL);
  if (dcarray) {
    sidl_dcomplex__array_deleteRef(dcarray);
    dcarray = NULL;
  }
  
  magicNumber = clearstack(magicNumber);
  iarray = ArrayTest_ArrayOps_create2Int(TEST_DIM1,TEST_DIM2,&exception);SIDL_REPORT(exception);
  MYASSERT(create2Int, ArrayTest_ArrayOps_check2Int(iarray,&exception) == TRUE);
  sidl_int__array_deleteRef(iarray);
  iarray = NULL;

  MYASSERT(create2IntNegOne, ArrayTest_ArrayOps_create2Int(-1,-1,&exception) == NULL);

  magicNumber = clearstack(magicNumber);
  darray = ArrayTest_ArrayOps_create2Double(TEST_DIM1,TEST_DIM2,&exception);
  MYASSERT(create2Double, ArrayTest_ArrayOps_check2Double(darray,&exception) == TRUE);
  sidl_double__array_deleteRef(darray);
  darray = NULL;

  MYASSERT(create2DoubleNegOne, ArrayTest_ArrayOps_create2Double(-1,-1,&exception) == NULL);

  magicNumber = clearstack(magicNumber);
  farray = ArrayTest_ArrayOps_create2Float(TEST_DIM1,TEST_DIM2,&exception);SIDL_REPORT(exception);
  MYASSERT(create2Float, ArrayTest_ArrayOps_check2Float(farray,&exception) == TRUE);
  sidl_float__array_deleteRef(farray);
  farray = NULL;

  MYASSERT(create2FloatNegOne, ArrayTest_ArrayOps_create2Float(-1,-1,&exception) == NULL);

  magicNumber = clearstack(magicNumber);
  dcarray = ArrayTest_ArrayOps_create2Dcomplex(TEST_DIM1,TEST_DIM2,&exception);SIDL_REPORT(exception);
  MYASSERT(create2Dcomplex, 
           ArrayTest_ArrayOps_check2Dcomplex(dcarray,&exception) == TRUE);
  sidl_dcomplex__array_deleteRef(dcarray);
  dcarray = NULL;

  MYASSERT(create2DcomplexNegOne, ArrayTest_ArrayOps_create2Dcomplex(-1,-1,&exception) == NULL);

  magicNumber = clearstack(magicNumber);
  fcarray = ArrayTest_ArrayOps_create2Fcomplex(TEST_DIM1,TEST_DIM2,&exception);SIDL_REPORT(exception);
  MYASSERT(create2Fcomplex, 
           ArrayTest_ArrayOps_check2Fcomplex(fcarray,&exception) == TRUE);
  sidl_fcomplex__array_deleteRef(fcarray);
  fcarray = NULL;

  MYASSERT(create2FcomplexNegOne, ArrayTest_ArrayOps_create2Fcomplex(-1,-1,&exception) == NULL);

  magicNumber = clearstack(magicNumber);
  iarray = ArrayTest_ArrayOps_create3Int(&exception);SIDL_REPORT(exception);
  MYASSERT(create3Int, ArrayTest_ArrayOps_check3Int(iarray,&exception) == TRUE);
  sidl_int__array_deleteRef(iarray);
  iarray = NULL;

  magicNumber = clearstack(magicNumber);
  iarray = ArrayTest_ArrayOps_create4Int(&exception);SIDL_REPORT(exception);
  MYASSERT(create4Int, ArrayTest_ArrayOps_check4Int(iarray,&exception) == TRUE);
  sidl_int__array_deleteRef(iarray);
  iarray = NULL;

  magicNumber = clearstack(magicNumber);
  iarray = ArrayTest_ArrayOps_create5Int(&exception);SIDL_REPORT(exception);
  MYASSERT(create5Int, ArrayTest_ArrayOps_check5Int(iarray,&exception) == TRUE);
  sidl_int__array_deleteRef(iarray);
  iarray = NULL;

  magicNumber = clearstack(magicNumber);
  iarray = ArrayTest_ArrayOps_create6Int(&exception);SIDL_REPORT(exception);
  MYASSERT(create6Int, ArrayTest_ArrayOps_check6Int(iarray,&exception) == TRUE);
  sidl_int__array_deleteRef(iarray);
  iarray = NULL;

  magicNumber = clearstack(magicNumber);
  iarray = ArrayTest_ArrayOps_create7Int(&exception);SIDL_REPORT(exception);
  MYASSERT(create7Int, ArrayTest_ArrayOps_check7Int(iarray,&exception) == TRUE);
  sidl_int__array_deleteRef(iarray);
  iarray = NULL;

  barray = NULL;
  magicNumber = clearstack(magicNumber);
  ArrayTest_ArrayOps_makeInOutBool(&barray, 218,&exception);SIDL_REPORT(exception);
  MYASSERT(makeInOutBool, ArrayTest_ArrayOps_checkBool(barray,&exception) == TRUE);
  sidl_bool__array_deleteRef(barray);
  barray = NULL;

  magicNumber = clearstack(magicNumber);
  ArrayTest_ArrayOps_makeInOutBool(&barray, -1,&exception);SIDL_REPORT(exception);
  MYASSERT(makeInOutBoolNegOne, barray == NULL);
  if (barray) { 
    sidl_bool__array_deleteRef(barray);
    barray = NULL;
  }

  magicNumber = clearstack(magicNumber);
  carray = NULL;
  ArrayTest_ArrayOps_makeInOutChar(&carray, 218,&exception);SIDL_REPORT(exception);
  MYASSERT(makeInOutChar, ArrayTest_ArrayOps_checkChar(carray,&exception) == TRUE);
  sidl_char__array_deleteRef(carray);
  carray = NULL;

  magicNumber = clearstack(magicNumber);
  ArrayTest_ArrayOps_makeInOutChar(&carray, -1,&exception);SIDL_REPORT(exception);
  MYASSERT(makeInOutCharNegOne, carray == NULL);
  if (carray) {
    sidl_char__array_deleteRef(carray);
    carray = NULL;
  }

  magicNumber = clearstack(magicNumber);
  iarray = NULL;
  ArrayTest_ArrayOps_makeInOutInt(&iarray, 218,&exception);SIDL_REPORT(exception);
  MYASSERT(makeInOutInt, ArrayTest_ArrayOps_checkInt(iarray,&exception) == TRUE);
  sidl_int__array_deleteRef(iarray);
  iarray = NULL;

  magicNumber = clearstack(magicNumber);
  ArrayTest_ArrayOps_makeInOutInt(&iarray, -1,&exception);SIDL_REPORT(exception);
  MYASSERT(makeInOutIntNegOne, iarray == NULL);
  if (iarray) {
    sidl_int__array_deleteRef(iarray);
    iarray = NULL;
  }

  larray = NULL;
  magicNumber = clearstack(magicNumber);
  ArrayTest_ArrayOps_makeInOutLong(&larray, 218,&exception);SIDL_REPORT(exception);
  MYASSERT(makeInOutLong, ArrayTest_ArrayOps_checkLong(larray,&exception) == TRUE);
  sidl_long__array_deleteRef(larray);
  larray = NULL;

  magicNumber = clearstack(magicNumber);
  ArrayTest_ArrayOps_makeInOutLong(&larray, -1,&exception);SIDL_REPORT(exception);
  MYASSERT(makeInOutLongNegOne, larray == NULL);
  if (larray) {
    sidl_long__array_deleteRef(larray);
    larray = NULL;
  }

  magicNumber = clearstack(magicNumber);
  sarray = NULL;
  ArrayTest_ArrayOps_makeInOutString(&sarray, 218,&exception);SIDL_REPORT(exception);
  MYASSERT(makeInOutString, ArrayTest_ArrayOps_checkString(sarray,&exception) == TRUE);
  sidl_string__array_deleteRef(sarray);
  sarray = NULL;

  magicNumber = clearstack(magicNumber);
  ArrayTest_ArrayOps_makeInOutString(&sarray, -1,&exception);SIDL_REPORT(exception);
  MYASSERT(makeInOutStringNegOne, sarray == NULL);
  if (sarray) {
    sidl_string__array_deleteRef(sarray);
    sarray = NULL;
  }
  magicNumber = clearstack(magicNumber);
  ArrayTest_ArrayOps_makeInOutDouble(&darray, 218,&exception);SIDL_REPORT(exception);
  MYASSERT(makeInOutDouble, ArrayTest_ArrayOps_checkDouble(darray,&exception) == TRUE);
  sidl_double__array_deleteRef(darray);
  darray = NULL;

  magicNumber = clearstack(magicNumber);
  ArrayTest_ArrayOps_makeInOutDouble(&darray, -1,&exception);SIDL_REPORT(exception);
  MYASSERT(makeInOutDoubleNegOne, darray == NULL);
  if (darray) {
    sidl_double__array_deleteRef(darray);
    darray = NULL;
  }

  farray = NULL;
  magicNumber = clearstack(magicNumber);
  ArrayTest_ArrayOps_makeInOutFloat(&farray, 218,&exception);SIDL_REPORT(exception);
  MYASSERT(makeInOutFloat, ArrayTest_ArrayOps_checkFloat(farray,&exception) == TRUE);
  sidl_float__array_deleteRef(farray);
  farray = NULL;

  magicNumber = clearstack(magicNumber);
  ArrayTest_ArrayOps_makeInOutFloat(&farray, -1,&exception);SIDL_REPORT(exception);
  MYASSERT(makeInOutFloatNegOne, farray == NULL);
  if (farray) {
    sidl_float__array_deleteRef(farray);
    farray = NULL;
  }
  
  dcarray = NULL;
  magicNumber = clearstack(magicNumber);
  ArrayTest_ArrayOps_makeInOutDcomplex(&dcarray, 218,&exception);SIDL_REPORT(exception);
  MYASSERT(makeInOutDcomplex, 
           ArrayTest_ArrayOps_checkDcomplex(dcarray,&exception) == TRUE);
  sidl_dcomplex__array_deleteRef(dcarray);
  dcarray = NULL;

  magicNumber = clearstack(magicNumber);
  ArrayTest_ArrayOps_makeInOutDcomplex(&dcarray, -1,&exception);SIDL_REPORT(exception);
  MYASSERT(makeInOutDcomplexNegOne, dcarray == NULL);
  if (dcarray) {
    sidl_dcomplex__array_deleteRef(dcarray);
    dcarray = NULL;
  }

  fcarray = NULL;
  magicNumber = clearstack(magicNumber);
  ArrayTest_ArrayOps_makeInOutFcomplex(&fcarray, 218,&exception);SIDL_REPORT(exception);
  MYASSERT(makeInOutFcomplex, 
           ArrayTest_ArrayOps_checkFcomplex(fcarray,&exception) == TRUE);
  sidl_fcomplex__array_deleteRef(fcarray);
  fcarray = NULL;

  magicNumber = clearstack(magicNumber);
  ArrayTest_ArrayOps_makeInOutFcomplex(&fcarray, -1,&exception);SIDL_REPORT(exception);
  MYASSERT(makeInOutFcomplexNegOne,  fcarray == NULL);
  if (fcarray) {
    sidl_fcomplex__array_deleteRef(fcarray);
    fcarray = NULL;
  }

  iarray = NULL;
  magicNumber = clearstack(magicNumber);
  ArrayTest_ArrayOps_makeInOut2Int(&iarray, TEST_DIM1, TEST_DIM2,&exception);SIDL_REPORT(exception);
  MYASSERT(makeInOut2Int, ArrayTest_ArrayOps_check2Int(iarray,&exception) == TRUE);
  sidl_int__array_deleteRef(iarray);
  iarray = NULL;

  magicNumber = clearstack(magicNumber);
  ArrayTest_ArrayOps_makeInOut2Int(&iarray, -1, -1,&exception);SIDL_REPORT(exception);
  MYASSERT(makeInOut2IntNegOne, iarray == NULL);
  if (iarray) {
    sidl_int__array_deleteRef(iarray);
    iarray = NULL;
  }

  darray = NULL;
  magicNumber = clearstack(magicNumber);
  ArrayTest_ArrayOps_makeInOut2Double(&darray, TEST_DIM1, TEST_DIM2,&exception);SIDL_REPORT(exception);
  MYASSERT(makeInOut2Double, ArrayTest_ArrayOps_check2Double(darray,&exception) == TRUE);
  sidl_double__array_deleteRef(darray);
  darray = NULL;

  magicNumber = clearstack(magicNumber);
  ArrayTest_ArrayOps_makeInOut2Double(&darray, -1, -1,&exception);SIDL_REPORT(exception);
  MYASSERT(makeInOut2DoubleNegOne, darray == NULL);
  if (darray) {
    sidl_double__array_deleteRef(darray);
    darray = NULL;
  }

  farray = NULL;
  magicNumber = clearstack(magicNumber);
  ArrayTest_ArrayOps_makeInOut2Float(&farray, TEST_DIM1, TEST_DIM2,&exception);SIDL_REPORT(exception);
  MYASSERT(makeInOut2Float, ArrayTest_ArrayOps_check2Float(farray,&exception) == TRUE);
  sidl_float__array_deleteRef(farray);
  farray = NULL;

  magicNumber = clearstack(magicNumber);
  ArrayTest_ArrayOps_makeInOut2Float(&farray, -1, -1,&exception);SIDL_REPORT(exception);
  MYASSERT(makeInOut2FloatNegOne, farray == NULL);
  if (farray) {
    sidl_float__array_deleteRef(farray);
    farray = NULL;
  }

  dcarray = NULL;
  magicNumber = clearstack(magicNumber);
  ArrayTest_ArrayOps_makeInOut2Dcomplex(&dcarray, TEST_DIM1, TEST_DIM2,&exception);SIDL_REPORT(exception);
  MYASSERT(makeInOut2Dcomplex, 
            ArrayTest_ArrayOps_check2Dcomplex(dcarray,&exception) == TRUE);
  sidl_dcomplex__array_deleteRef(dcarray);
  dcarray = NULL;

  magicNumber = clearstack(magicNumber);
  ArrayTest_ArrayOps_makeInOut2Dcomplex(&dcarray, -1, -1,&exception);SIDL_REPORT(exception);
  MYASSERT(makeInOut2DcomplexNegOne, dcarray == NULL);
  if (dcarray) {
    sidl_dcomplex__array_deleteRef(dcarray);
    dcarray = NULL;
  }

  fcarray = NULL;
  magicNumber = clearstack(magicNumber);
  ArrayTest_ArrayOps_makeInOut2Fcomplex(&fcarray, TEST_DIM1, TEST_DIM2,&exception);SIDL_REPORT(exception);
  MYASSERT(makeInOut2Fcomplex, 
           ArrayTest_ArrayOps_check2Fcomplex(fcarray,&exception) == TRUE);
  sidl_fcomplex__array_deleteRef(fcarray);
  fcarray = NULL;

  magicNumber = clearstack(magicNumber);
  ArrayTest_ArrayOps_makeInOut2Fcomplex(&fcarray, -1, -1,&exception);SIDL_REPORT(exception);
  MYASSERT(makeInOut2FcomplexNegOne, fcarray == NULL);
  if (fcarray) {
    sidl_fcomplex__array_deleteRef(fcarray);
    fcarray = NULL;
  }
  
  iarray = NULL;
  magicNumber = clearstack(magicNumber);
  ArrayTest_ArrayOps_makeInOut3Int(&iarray,&exception);SIDL_REPORT(exception);
  MYASSERT(makeInOut3Int, ArrayTest_ArrayOps_check3Int(iarray,&exception) == TRUE);
  sidl_int__array_deleteRef(iarray);
  iarray = NULL;
 
  magicNumber = clearstack(magicNumber);
  ArrayTest_ArrayOps_makeInOut4Int(&iarray,&exception);SIDL_REPORT(exception);
  MYASSERT(makeInOut4Int, ArrayTest_ArrayOps_check4Int(iarray,&exception) == TRUE);
  sidl_int__array_deleteRef(iarray);
  iarray = NULL;

  magicNumber = clearstack(magicNumber);
  ArrayTest_ArrayOps_makeInOut5Int(&iarray,&exception);SIDL_REPORT(exception);
  MYASSERT(makeInOut5Int, ArrayTest_ArrayOps_check5Int(iarray,&exception) == TRUE);
  sidl_int__array_deleteRef(iarray);
  iarray = NULL;

  magicNumber = clearstack(magicNumber);
  ArrayTest_ArrayOps_makeInOut6Int(&iarray,&exception);SIDL_REPORT(exception);
  MYASSERT(makeInOut6Int, ArrayTest_ArrayOps_check6Int(iarray,&exception) == TRUE);
  sidl_int__array_deleteRef(iarray);
  iarray = NULL;
  
  iarray = NULL;
  magicNumber = clearstack(magicNumber);
  ArrayTest_ArrayOps_makeInOut7Int(&iarray,&exception);SIDL_REPORT(exception);
  fflush(stdout);
  MYASSERT(makeInOut7Int, ArrayTest_ArrayOps_check7Int(iarray,&exception) == TRUE);
  sidl_int__array_deleteRef(iarray);
  iarray = NULL;
  
  oarray = NULL;
  magicNumber = clearstack(magicNumber);
  MYASSERT(createObjectNegOne, ArrayTest_ArrayOps_createObject(-1,&exception) == NULL);
  oarray = ArrayTest_ArrayOps_createObject(16,&exception);SIDL_REPORT(exception);
  MYASSERT(checkObject, ArrayTest_ArrayOps_checkObject(oarray,&exception) == 16);
  if (oarray) { 
    ArrayTest_ArrayOps__array_deleteRef(oarray);
    oarray = NULL;
  }
  fflush(stdout);
  MYASSERT(checkObjectNull, !ArrayTest_ArrayOps_checkObject(NULL,&exception));
  oarray = NULL;
  
  {
    int dimen = 0;
    int type = 0;
    int dimen2 = 0;
    int type2 = 0;
    struct sidl__array *garrayret = NULL;
    struct sidl__array *garrayout = NULL;
    struct sidl__array *garrayinout = NULL;
    ArrayTest_ArrayOps_checkGeneric(garray, &dimen, &type,&exception);SIDL_REPORT(exception);
    MYASSERT(Generic array is still Null, (garray == NULL));
    MYASSERT(NULL Generic array has no dimension, (dimen == 0));
    MYASSERT(NULL Generic array has no type, (type == 0));
    dimen = 1;
    type = sidl_int_array;
    garray = ArrayTest_ArrayOps_createGeneric(dimen, type,&exception);SIDL_REPORT(exception);
    MYASSERT(Generic (int) array is not Null, (garray != NULL));
    MYASSERT(Generic (int) array has 1 dimension, (dimen == sidl__array_dimen(garray)));
    MYASSERT(Generic (int) array has int type, (type == sidl__array_type(garray)));

    ArrayTest_ArrayOps_checkGeneric(garray, &dimen2, &type2,&exception);SIDL_REPORT(exception);
    MYASSERT(checkGeneric (int) array has 1 dimension, (dimen == dimen2));
    MYASSERT(checkGeneric (int) array has int type, (type == type2));
    garrayout = (struct sidl__array *)(ptrdiff_t)rand();

    garrayret = ArrayTest_ArrayOps_passGeneric(garray, &garrayinout,
					       &garrayout,&exception);SIDL_REPORT(exception);
    
    MYASSERT(Generic returned array not NULL, garrayret != NULL);
    MYASSERT(Generic returned array correct dimension, 
	     sidl__array_dimen(garrayret) == sidl__array_dimen(garrayret));
    MYASSERT(Generic returned array correct type, 
	     sidl__array_type(garrayret) == sidl__array_type(garrayret));
    MYASSERT(Generic returned array correct length, 
	     sidl__array_length(garrayret,0) == sidl__array_length(garrayret,0));

    MYASSERT(Generic out array not NULL, garrayout != NULL);
    MYASSERT(Generic out array correct dimension, 
	     sidl__array_dimen(garray) == sidl__array_dimen(garrayout));
    MYASSERT(Generic out array correct type, 
	     sidl__array_type(garray) == sidl__array_type(garrayout));
    MYASSERT(Generic out array correct length, 
	     sidl__array_length(garray,0) == sidl__array_length(garrayout,0));


    MYASSERT(Generic inout is correct, 
	     ArrayTest_ArrayOps_check2Int((struct sidl_int__array *)garrayinout,&exception) == TRUE);
    
    if (garray) sidl__array_deleteRef(garray);
    if (garrayinout) sidl__array_deleteRef(garrayinout);
    if (garrayout) sidl__array_deleteRef(garrayout);
    if (garrayret) sidl__array_deleteRef(garrayret);
  }

  {
    int32_t* irarray = NULL;
    magicNumber = clearstack(magicNumber);
    iarray = ArrayTest_ArrayOps_createInt(TEST_SIZE,&exception);SIDL_REPORT(exception);
    MYASSERT(createInt, iarray);
    MYASSERT(createInt, ArrayTest_ArrayOps_checkInt(iarray,&exception) == TRUE);

    irarray = iarray->d_firstElement;
    MYASSERT(Check rarray int 1, ArrayTest_ArrayOps_checkRarray1Int(irarray, TEST_SIZE,&exception) == TRUE);
    sidl_int__array_deleteRef(iarray);
    iarray = NULL;
    irarray = NULL;
  }

  {
    int32_t* irarray = NULL;
    struct sidl_int__array *iarray2;
    int32_t n,m,o;
    magicNumber = clearstack(magicNumber);
    iarray = ArrayTest_ArrayOps_create3Int(&exception);SIDL_REPORT(exception);
    MYASSERT(create3Int, ArrayTest_ArrayOps_check3Int(iarray,&exception) == TRUE);
    iarray2 = sidl_int__array_ensure(iarray,3,sidl_column_major_order);
    irarray = iarray2->d_firstElement;
    n = sidlLength(iarray2,0);
    m = sidlLength(iarray2,1);
    o = sidlLength(iarray2,2);
    MYASSERT(Check rarray int 3, ArrayTest_ArrayOps_checkRarray3Int(irarray, n,m,o,&exception) == TRUE);
    sidl_int__array_deleteRef(iarray);
    sidl_int__array_deleteRef(iarray2);
    iarray = NULL;
    irarray = NULL;
  }

    {
    int32_t* irarray = NULL;
    struct sidl_int__array *iarray2;
    int32_t n,m,o,p,q,r,s;
    magicNumber = clearstack(magicNumber);
    iarray = ArrayTest_ArrayOps_create7Int(&exception);SIDL_REPORT(exception);
    MYASSERT(create7Int, ArrayTest_ArrayOps_check7Int(iarray,&exception) == TRUE);
    iarray2 = sidl_int__array_ensure(iarray,7,sidl_column_major_order);
    irarray = iarray2->d_firstElement;
    n = sidlLength(iarray2,0);
    m = sidlLength(iarray2,1);
    o = sidlLength(iarray2,2);
    p = sidlLength(iarray2,3);
    q = sidlLength(iarray2,4);
    r = sidlLength(iarray2,5);
    s = sidlLength(iarray2,6);
    MYASSERT(Check rarray int 7, ArrayTest_ArrayOps_checkRarray7Int(irarray, n,m,o,p,q,r,s,&exception) == TRUE);
    sidl_int__array_deleteRef(iarray);
    sidl_int__array_deleteRef(iarray2);
    iarray = NULL;
  }


  {
    int32_t irarray[TEST_SIZE]; 
    memset(irarray, rand(), sizeof(irarray));
    magicNumber = clearstack(magicNumber);
    ArrayTest_ArrayOps_initRarray1Int(irarray, TEST_SIZE,&exception);SIDL_REPORT(exception);
    MYASSERT(Check rarray int 1, ArrayTest_ArrayOps_checkRarray1Int(irarray, TEST_SIZE,&exception) == TRUE);
  }

  {
    int32_t irarray[24]; /* 2*3*4 */
    int32_t n=2, m=3 , o=4;
    magicNumber = clearstack(magicNumber);
    memset(irarray, rand(), sizeof(irarray));
    ArrayTest_ArrayOps_initRarray3Int(irarray, n,m,o,&exception);SIDL_REPORT(exception);
    MYASSERT(Check rarray int 3, ArrayTest_ArrayOps_checkRarray3Int(irarray, n,m,o,&exception) == TRUE);

  }

  {
    int32_t irarray[432]; /* 2*2*2*2*3*3*3  */
    int32_t n=2, m=2 , o=2, p=2, q=3,r=3,s=3;
    magicNumber = clearstack(magicNumber);
    memset(irarray, rand(), sizeof(irarray));
    ArrayTest_ArrayOps_initRarray7Int(irarray, n,m,o,p,q,r,s,&exception);SIDL_REPORT(exception);
    MYASSERT(Check rarray int 7, ArrayTest_ArrayOps_checkRarray7Int(irarray, n,m,o,p,q,r,s,&exception) == TRUE);

  }


  {
    double drarray[TEST_SIZE]; 
    magicNumber = clearstack(magicNumber);
    memset(drarray, rand(), sizeof(drarray));
    ArrayTest_ArrayOps_initRarray1Double(drarray, TEST_SIZE,&exception);SIDL_REPORT(exception);
    MYASSERT(Check rarray double 1, ArrayTest_ArrayOps_checkRarray1Double(drarray, TEST_SIZE,&exception) == TRUE);
  }

  {
    struct sidl_dcomplex dcrarray[TEST_SIZE]; 
    magicNumber = clearstack(magicNumber);
    memset(dcrarray, rand(), sizeof(dcrarray));
    ArrayTest_ArrayOps_initRarray1Dcomplex(dcrarray, TEST_SIZE,&exception);SIDL_REPORT(exception);
    MYASSERT(Check rarray Dcomplex 1, ArrayTest_ArrayOps_checkRarray1Dcomplex(dcrarray, TEST_SIZE,&exception) == TRUE);
  }

  {
    int32_t n = 3, m = 3, o = 2, a[9], b[6], x[6];
    int i = 0;
    for(i = 0; i < 9; ++i) {
      a[i]=i;
    }
    for(i = 0; i < 6; ++i) {
      b[i]=i;
    }
    ArrayTest_ArrayOps_matrixMultiply(a,b,x,n,m,o,&exception);SIDL_REPORT(exception);
    MYASSERT(Check Matrix Multiply, ArrayTest_ArrayOps_checkMatrixMultiply(a,b,x,n,m,o,&exception) == TRUE);
    
  }

  {
    const int32_t len = TEST_SIZE;
    static const int32_t lower = 0;
    static const int32_t negOne = -1;
    const int32_t upper = len - 1;
    int32_t *data = malloc(len*sizeof(int32_t));
    int32_t *end = data + upper;
    int32_t i;
    int32_t prime = nextPrime(0);
    iarray = sidl_int__array_borrow(end, 1, &lower, &upper, &negOne);
    MYASSERT(borrow, iarray);
    if (iarray) {
      for(i = lower; i <= upper; ++i, prime = nextPrime(prime) ) {
        switch(rand() % 3) {
        case 0:  sidlArrayElem1(iarray, i) = prime;      break;
        case 1:  sidl_int__array_set1(iarray, i, prime); break;
        default: sidl_int__array_set(iarray, &i, prime); break;
        }
      }
    }
    MYASSERT(negativeStride, 
             iarray && ArrayTest_ArrayOps_checkInt(iarray,&exception) == TRUE);
    if (iarray) sidl_int__array_deleteRef(iarray);
    free(data);
  }


  {
    static const int32_t lower[] = { 0, 0, 0};
    const int32_t upper[] = { TEST_DIM1-1, TEST_DIM2-1, TEST_DIM3-1};
    const int32_t stride[] = { -TEST_DIM2*TEST_DIM3, -TEST_DIM3, -1};
    const int32_t numElems = TEST_DIM1 * TEST_DIM2 * TEST_DIM3;
    int32_t *data = malloc(numElems*sizeof(int32_t));
    int32_t *end = data + numElems - 1;
    int32_t i, j, k, part;
    iarray = sidl_int__array_borrow(end, 3, lower, upper, stride);
    MYASSERT(borrowThree, iarray);
    if (iarray) {
      for(i = lower[0]; i <= upper[0]; ++i) {
        for(j = lower[1]; j <= upper[1]; ++j) {
          part = (i + 1) * (j + 2);
          for(k = lower[2]; k <= upper[2]; ++k) {
            sidlArrayElem3(iarray, i, j, k) = part*(k+3);
          }
        }
      }
    }
    MYASSERT(negativeStrideThree,
             iarray && ArrayTest_ArrayOps_check3Int(iarray, &exception) == TRUE);
    if (iarray) sidl_int__array_deleteRef(iarray);
    free(data);
  }
  {
    magicNumber = clearstack(magicNumber);
    sarray = ArrayTest_ArrayOps_create2String(12, 15,&exception);SIDL_REPORT(exception);
    MYASSERT(create2String, sarray);
    MYASSERT(create2String, ArrayTest_ArrayOps_check2String(sarray,&exception) == TRUE);
    if (sarray) {
      sidl_string__array_deleteRef(sarray);
      sarray = NULL;
    }
  }  
  synch_RegOut_close(tracker,&exception);
  synch_RegOut_deleteRef(tracker,&exception);
  return 0;
 EXIT:
  {
    sidl_BaseInterface throwaway_exception;
    if (tracker) {
      synch_RegOut_forceFailure(tracker, &throwaway_exception);
      synch_RegOut_deleteRef(tracker, &throwaway_exception);
    }
    sidl_BaseInterface_deleteRef(exception, &throwaway_exception);
  }
  return -1;
}
