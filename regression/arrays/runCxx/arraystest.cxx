// 
// File:        arraystests.cc
// Copyright:   (c) 2001 Lawrence Livermore National Security, LLC
// Revision:    @(#) $Revision: 7450 $
// Date:        $Date: 2012-04-19 09:08:27 -0700 (Thu, 19 Apr 2012) $
// Description: Simple test on the ArrayTest static methods
// 
// 
#include <cstdio>
#include "synch.hxx"
using namespace std;
#include "ArrayTest_ArrayOps.hxx"

#ifdef SIDL_USE_UCXX
using namespace ucxx;
#endif
using ArrayTest::ArrayOps;

static const int TEST_SIZE = 345; /* size of one dimensional arrays */
static const int TEST_DIM1 = 17; /* first dimension of 2-d arrays */
static const int TEST_DIM2 = 13; /* second dimension of 2-d arrays */


#define MYASSERT( CMT, AAA ) \
  tracker.startPart(++part_no ); \
  tracker.writeComment( #CMT ": " #AAA); \
  if ( AAA ) result = synch::ResultType_PASS; \
  else result = synch::ResultType_FAIL;  \
  tracker.endPart( part_no, result);




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


int main(int argc, char **argv) {
  synch::ResultType result = synch::ResultType_PASS;
  synch::RegOut tracker = synch::RegOut::_create();
  int part_no = 0;
  int magicNumber = 13;
  
  tracker.setExpectations(158);

  {
    const int32_t numElem[] = { TEST_SIZE/2 };
    const int32_t start[] = { 0 };
    const int32_t stride[] = { 2 };
    magicNumber = clearstack(magicNumber);
    sidl::array<bool> barray = ArrayOps::createBool(TEST_SIZE);
    MYASSERT(createBool, barray._not_nil());
    MYASSERT(createBool, ArrayOps::checkBool(barray) == true);
    MYASSERT(createBool, ArrayOps::reverseBool(barray, true) == true);
    sidl::array<bool> sliced = barray.slice(1, numElem, start, stride);
    sidl::array<bool> cpy;
    MYASSERT(createBool, sliced._not_nil());
    sliced.smartCopy();
    MYASSERT(createBool, sliced._not_nil());
    cpy = barray;
    cpy.smartCopy();
    MYASSERT(createBool, cpy._not_nil());
  }

  {
    magicNumber = clearstack(magicNumber);
    sidl::array<bool> barray;
    ArrayOps::makeBool(218, barray);
    MYASSERT(makeBool218, ArrayOps::checkBool(barray) == true);
    MYASSERT(makeBool218, ArrayOps::reverseBool(barray, false) == true);
    MYASSERT(makeBool218, ArrayOps::checkBool(barray) == false);
    barray.deleteRef();
    magicNumber = clearstack(magicNumber);
  }
  
  {
    magicNumber = clearstack(magicNumber);
    sidl::array<bool> barray;
    ArrayOps::makeBool(9, barray);
    MYASSERT(makeBool9, ArrayOps::reverseBool(barray, false) == true);
    MYASSERT(makeBool9, ArrayOps::checkBool(barray) == true);
    barray.deleteRef();
    magicNumber = clearstack(magicNumber);
  }
  
  {
    magicNumber = clearstack(magicNumber);
    sidl::array<char> carray = ArrayOps::createChar(TEST_SIZE);
    MYASSERT(createChar, carray._not_nil());
    MYASSERT(createChar, ArrayOps::checkChar(carray) == true);
    MYASSERT(createChar, ArrayOps::reverseChar(carray, true) == true);
    carray.deleteRef();
    magicNumber = clearstack(magicNumber);
  }

  {
    magicNumber = clearstack(magicNumber);
    sidl::array<char> carray;
    ArrayOps::makeChar(218, carray);
    MYASSERT(makeChar, ArrayOps::checkChar(carray) == true);
    MYASSERT(makeChar, ArrayOps::reverseChar(carray, false) == true);
    MYASSERT(makeChar, ArrayOps::checkChar(carray) == false);
    carray.deleteRef();
    magicNumber = clearstack(magicNumber);
  }
  
  {
    magicNumber = clearstack(magicNumber);
    sidl::array<int32_t> iarray = ArrayOps::createInt(TEST_SIZE);
    MYASSERT(createInt, iarray._not_nil());
    MYASSERT(createInt, ArrayOps::checkInt(iarray) == true);
    MYASSERT(createInt, ArrayOps::reverseInt(iarray, true) == true);
    iarray.deleteRef();
    magicNumber = clearstack(magicNumber);
  } 

  {
    magicNumber = clearstack(magicNumber);
    sidl::array<int32_t> iarray;
    ArrayOps::makeInt(218, iarray);
    MYASSERT(makeInt, ArrayOps::checkInt(iarray) == true);
    MYASSERT(makeInt, ArrayOps::reverseInt(iarray, false) == true);
    MYASSERT(makeInt, ArrayOps::checkInt(iarray) == false);
    iarray.deleteRef();
    magicNumber = clearstack(magicNumber);
  } 

  {
    magicNumber = clearstack(magicNumber);
    sidl::array<int32_t> borrowed;
    int32_t elements[] = {
      2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37,
      41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83,
      89, 97, 101, 103, 107, 109, 113, 127, 131
    };
    const int32_t lower[] = { 0 };
    const int32_t upper[] = { sizeof(elements)/sizeof(int32_t)-1 };
    const int32_t stride[] = { 1 };
    MYASSERT(borrowed_int, !borrowed);
    borrowed.borrow(elements, 1, lower, upper, stride);
    MYASSERT(borrowed_int, borrowed._not_nil());
    MYASSERT(borrowed int, ArrayOps::checkInt(borrowed) == true);
    borrowed.smartCopy();
    MYASSERT(borrowed int, ArrayOps::checkInt(borrowed) == true);
    magicNumber = clearstack(magicNumber);
  }

  {
    magicNumber = clearstack(magicNumber);
    sidl::array<int64_t> larray = ArrayOps::createLong(TEST_SIZE);
    MYASSERT(createLong, larray._not_nil());
    MYASSERT(createLong, ArrayOps::checkLong(larray) == true);
    MYASSERT(createLong, ArrayOps::reverseLong(larray, true) == true);
    larray.deleteRef();
    magicNumber = clearstack(magicNumber);
  } 

  { 
    magicNumber = clearstack(magicNumber);
    sidl::array<int64_t> larray;
    ArrayOps::makeLong(218, larray);
    MYASSERT(makeLong, ArrayOps::checkLong(larray) == true);
    MYASSERT(makeLong, ArrayOps::reverseLong(larray, false) == true);
    MYASSERT(makeLong, ArrayOps::checkLong(larray) == false);
    larray.deleteRef();
    magicNumber = clearstack(magicNumber);
  } 

  {
    magicNumber = clearstack(magicNumber);
    sidl::array<string> sarray = ArrayOps::createString(TEST_SIZE);
    MYASSERT(createString, sarray._not_nil());
    MYASSERT(createString, ArrayOps::checkString(sarray) == true);
    MYASSERT(createString, ArrayOps::reverseString(sarray, true) == true);
    sarray.deleteRef();
    magicNumber = clearstack(magicNumber);
  }

  { 
    magicNumber = clearstack(magicNumber);
    sidl::array<string> sarray; 
    ArrayOps::makeString(218, sarray);
    MYASSERT(makeString, ArrayOps::checkString(sarray) == true);
    MYASSERT(makeString, ArrayOps::reverseString(sarray, false) == true);
    MYASSERT(makeString, ArrayOps::checkString(sarray) == false);
    sarray.deleteRef();
    magicNumber = clearstack(magicNumber);
  }

  {
    magicNumber = clearstack(magicNumber);
    sidl::array<double> darray = ArrayOps::createDouble(TEST_SIZE);
    MYASSERT(createDouble, darray._not_nil());
    MYASSERT(createDouble, ArrayOps::checkDouble(darray) == true);
    MYASSERT(createDouble, ArrayOps::reverseDouble(darray, true) == true);
    darray.deleteRef();
    magicNumber = clearstack(magicNumber);
  } 

  {
    magicNumber = clearstack(magicNumber);
    sidl::array<double> darray;
    ArrayOps::makeDouble(218, darray);
    MYASSERT(makeDouble, ArrayOps::checkDouble(darray) == true);
    MYASSERT(makeDouble, ArrayOps::reverseDouble(darray, false) == true);
    MYASSERT(makeDouble, ArrayOps::checkDouble(darray) == false);
    darray.deleteRef();
    magicNumber = clearstack(magicNumber);
  } 
  
  { 
    magicNumber = clearstack(magicNumber);
    sidl::array<float> farray = ArrayOps::createFloat(TEST_SIZE);
    MYASSERT(createFloat, farray._not_nil());
    MYASSERT(createFloat, ArrayOps::checkFloat(farray) == true);
    MYASSERT(createFloat, ArrayOps::reverseFloat(farray, true) == true);
    farray.deleteRef();
    magicNumber = clearstack(magicNumber);
  }

  { 
    magicNumber = clearstack(magicNumber);
    sidl::array<float> farray; 
    ArrayOps::makeFloat(218, farray);
    MYASSERT(makeFloat, ArrayOps::checkFloat(farray) == true);
    MYASSERT(makeFloat, ArrayOps::reverseFloat(farray, false) == true);
    MYASSERT(makeFloat, ArrayOps::checkFloat(farray) == false);
    farray.deleteRef();
    magicNumber = clearstack(magicNumber);
  } 
  
  {
    magicNumber = clearstack(magicNumber);
    sidl::array<sidl::fcomplex> fcarray = ArrayOps::createFcomplex(TEST_SIZE);
    MYASSERT(createFcomplex, fcarray._not_nil());
    MYASSERT(createFcomplex, ArrayOps::checkFcomplex(fcarray) == true);
    MYASSERT(createFcomplex, ArrayOps::reverseFcomplex(fcarray, true) == true);
    fcarray.deleteRef();
    magicNumber = clearstack(magicNumber);
  } 

  { 
    magicNumber = clearstack(magicNumber);
    sidl::array<sidl::fcomplex> fcarray;
    ArrayOps::makeFcomplex(218, fcarray);
    MYASSERT(makeFcomplex, ArrayOps::checkFcomplex(fcarray) == true);
    MYASSERT(makeFcomplex, ArrayOps::reverseFcomplex(fcarray, false) == true);
    MYASSERT(makeFcomplex, ArrayOps::checkFcomplex(fcarray) == false);
    fcarray.deleteRef();
    magicNumber = clearstack(magicNumber);
  } 
  
  {
    magicNumber = clearstack(magicNumber);
    sidl::array<sidl::dcomplex> dcarray = ArrayOps::createDcomplex(TEST_SIZE);
    MYASSERT(createDcomplex, dcarray._not_nil());
    MYASSERT(createDcomplex, ArrayOps::checkDcomplex(dcarray) == true);
    MYASSERT(createDcomplex, ArrayOps::reverseDcomplex(dcarray, true) == true);
    dcarray.deleteRef();
    magicNumber = clearstack(magicNumber);
  } 

  { 
    magicNumber = clearstack(magicNumber);
    sidl::array<sidl::dcomplex> dcarray;
    ArrayOps::makeDcomplex(218, dcarray);
    MYASSERT(makeDcomplex, ArrayOps::checkDcomplex(dcarray) == true);
    MYASSERT(makeDcomplex, ArrayOps::reverseDcomplex(dcarray, false) == true);
    MYASSERT(makeDcomplex, ArrayOps::checkDcomplex(dcarray) == false);
    dcarray.deleteRef();
    magicNumber = clearstack(magicNumber);
  } 

  { 
    magicNumber = clearstack(magicNumber);
    sidl::array<int32_t> iarray;
    magicNumber = clearstack(magicNumber);
    iarray = ArrayOps::create2Int(TEST_DIM1,TEST_DIM2);
    MYASSERT(create2Int, ArrayOps::check2Int(iarray) == true);
    iarray.deleteRef();
    magicNumber = clearstack(magicNumber);
  } 

  { 
    magicNumber = clearstack(magicNumber);
    sidl::array<double> darray;
    darray = ArrayOps::create2Double(TEST_DIM1,TEST_DIM2);
    MYASSERT(create2Double, ArrayOps::check2Double(darray) == true);
    darray.deleteRef();
    magicNumber = clearstack(magicNumber);
  } 

  { 
    magicNumber = clearstack(magicNumber);
    sidl::array<float> farray;
    farray = ArrayOps::create2Float(TEST_DIM1,TEST_DIM2);
    MYASSERT(create2Float, ArrayOps::check2Float(farray) == true);
    farray.deleteRef();
    magicNumber = clearstack(magicNumber);
  } 

  { 
    magicNumber = clearstack(magicNumber);
    sidl::array<sidl::dcomplex> dcarray;
    dcarray = ArrayOps::create2Dcomplex(TEST_DIM1,TEST_DIM2);
    MYASSERT(create2Dcomplex, ArrayOps::check2Dcomplex(dcarray) == true);
    dcarray.deleteRef();
    magicNumber = clearstack(magicNumber);
  } 

  { 
    magicNumber = clearstack(magicNumber);
    sidl::array<sidl::fcomplex> fcarray;
    fcarray = ArrayOps::create2Fcomplex(TEST_DIM1,TEST_DIM2);
    MYASSERT(create2Fcomplex, ArrayOps::check2Fcomplex(fcarray) == true);
    fcarray.deleteRef();
    magicNumber = clearstack(magicNumber);
  } 

  {
    magicNumber = clearstack(magicNumber);
    sidl::array<int32_t> iarray;
    iarray = ArrayOps::create3Int();
    MYASSERT(create3Int, ArrayOps::check3Int(iarray) == true);
    iarray.deleteRef();
    magicNumber = clearstack(magicNumber);
  }

  {
    magicNumber = clearstack(magicNumber);
    sidl::array<int32_t> iarray;
    iarray = ArrayOps::create4Int();
    MYASSERT(create4Int, ArrayOps::check4Int(iarray) == true);
    iarray.deleteRef();
    magicNumber = clearstack(magicNumber);
  }

  {
    magicNumber = clearstack(magicNumber);
    sidl::array<int32_t> iarray;
    iarray = ArrayOps::create5Int();
    MYASSERT(create5Int, ArrayOps::check5Int(iarray) == true);
    iarray.deleteRef();
    magicNumber = clearstack(magicNumber);
  }

  {
    magicNumber = clearstack(magicNumber);
    sidl::array<int32_t> iarray;
    iarray = ArrayOps::create6Int();
    MYASSERT(create6Int, ArrayOps::check6Int(iarray) == true);
    iarray.deleteRef();
    magicNumber = clearstack(magicNumber);
  }

  {
    magicNumber = clearstack(magicNumber);
    sidl::array<int32_t> iarray;
    iarray = ArrayOps::create7Int();
    MYASSERT(create7Int, ArrayOps::check7Int(iarray) == true);
    iarray.deleteRef();
    magicNumber = clearstack(magicNumber);
  }

  {
    magicNumber = clearstack(magicNumber);
    sidl::array<bool> barray;
    ArrayOps::makeInOutBool(barray, 218);
    MYASSERT(makeInOutBool, ArrayOps::checkBool(barray) == true);
    barray.deleteRef();
    magicNumber = clearstack(magicNumber);
  }

  {
    magicNumber = clearstack(magicNumber);
    sidl::array<char> carray;
    ArrayOps::makeInOutChar(carray, 218);
    MYASSERT(makeInOutChar, ArrayOps::checkChar(carray) == true);
    carray.deleteRef();
    magicNumber = clearstack(magicNumber);
  }

  {
    magicNumber = clearstack(magicNumber);
    sidl::array<int32_t> iarray;
    ArrayOps::makeInOutInt(iarray, 218);
    MYASSERT(makeInOutInt, ArrayOps::checkInt(iarray) == true);
    iarray.deleteRef();
    magicNumber = clearstack(magicNumber);
  }

  {
    magicNumber = clearstack(magicNumber);
    sidl::array<int64_t> larray;
    ArrayOps::makeInOutLong(larray, 218);
    MYASSERT(makeInOutLong, ArrayOps::checkLong(larray) == true);
    larray.deleteRef();
    magicNumber = clearstack(magicNumber);
  }

  {
    magicNumber = clearstack(magicNumber);
    sidl::array<string> sarray;
    ArrayOps::makeInOutString(sarray, 218);
    MYASSERT(makeInOutString, ArrayOps::checkString(sarray) == true);
    sarray.deleteRef();
    magicNumber = clearstack(magicNumber);
  }

  {
    magicNumber = clearstack(magicNumber);
    sidl::array<double> darray;
    ArrayOps::makeInOutDouble(darray, 218);
    MYASSERT(makeInOutDouble, ArrayOps::checkDouble(darray) == true);
    darray.deleteRef();
    magicNumber = clearstack(magicNumber);
  }

  {
    magicNumber = clearstack(magicNumber);
    sidl::array<float> farray;
    ArrayOps::makeInOutFloat(farray, 218);
    MYASSERT(makeInOutFloat, ArrayOps::checkFloat(farray) == true);
    farray.deleteRef();
    magicNumber = clearstack(magicNumber);
  }

  {
    magicNumber = clearstack(magicNumber);
    sidl::array<sidl::dcomplex> dcarray;
    ArrayOps::makeInOutDcomplex(dcarray, 218);
    MYASSERT(makeInOutDcomplex, ArrayOps::checkDcomplex(dcarray) == true);
    dcarray.deleteRef();
    magicNumber = clearstack(magicNumber);
  }

  {
    magicNumber = clearstack(magicNumber);
    sidl::array<sidl::fcomplex> fcarray;
    ArrayOps::makeInOutFcomplex(fcarray, 218);
    MYASSERT(makeInOutFcomplex, ArrayOps::checkFcomplex(fcarray) == true);
    fcarray.deleteRef();
    magicNumber = clearstack(magicNumber);
  }

  {
    magicNumber = clearstack(magicNumber);
    sidl::array<int32_t> iarray;
    ArrayOps::makeInOut2Int(iarray, TEST_DIM1, TEST_DIM2);
    MYASSERT(makeInOut2Int, ArrayOps::check2Int(iarray) == true);
    iarray.deleteRef();
    magicNumber = clearstack(magicNumber);
  }

  {
    magicNumber = clearstack(magicNumber);
    sidl::array<double> darray;
    ArrayOps::makeInOut2Double(darray, TEST_DIM1, TEST_DIM2);
    MYASSERT(makeInOut2Double, ArrayOps::check2Double(darray) == true);
    darray.deleteRef();
    magicNumber = clearstack(magicNumber);
  }

  {
    magicNumber = clearstack(magicNumber);
    sidl::array<float> farray;
    ArrayOps::makeInOut2Float(farray, TEST_DIM1, TEST_DIM2);
    MYASSERT(makeInOut2Float, ArrayOps::check2Float(farray) == true);
    farray.deleteRef();
    magicNumber = clearstack(magicNumber);
  }

  {
    magicNumber = clearstack(magicNumber);
    sidl::array<sidl::dcomplex> dcarray;
    ArrayOps::makeInOut2Dcomplex(dcarray, TEST_DIM1, TEST_DIM2);
    MYASSERT(makeInOut2Dcomplex, ArrayOps::check2Dcomplex(dcarray) == true);
    dcarray.deleteRef();
    magicNumber = clearstack(magicNumber);
  }

  {
    magicNumber = clearstack(magicNumber);
    sidl::array<sidl::fcomplex> fcarray;
    ArrayOps::makeInOut2Fcomplex(fcarray, TEST_DIM1, TEST_DIM2);
    MYASSERT(makeInOut2Fcomplex, ArrayOps::check2Fcomplex(fcarray) == true);
    fcarray.deleteRef();
    magicNumber = clearstack(magicNumber);
  }

  {
    magicNumber = clearstack(magicNumber);
    sidl::array<int> iarray;
    ArrayOps::makeInOut3Int(iarray);
    MYASSERT(makeInOut3Int, ArrayOps::check3Int(iarray) == true);
    iarray.deleteRef();
    magicNumber = clearstack(magicNumber);
  }

  {
    magicNumber = clearstack(magicNumber);
    sidl::array<int> iarray;
    ArrayOps::makeInOut4Int(iarray);
    MYASSERT(makeInOut4Int, ArrayOps::check4Int(iarray) == true);
    iarray.deleteRef();
    magicNumber = clearstack(magicNumber);
  }

  {
    magicNumber = clearstack(magicNumber);
    sidl::array<int> iarray;
    ArrayOps::makeInOut5Int(iarray);
    MYASSERT(makeInOut5Int, ArrayOps::check5Int(iarray) == true);
    iarray.deleteRef();
    magicNumber = clearstack(magicNumber);
  }

  {
    magicNumber = clearstack(magicNumber);
    sidl::array<int> iarray;
    ArrayOps::makeInOut6Int(iarray);
    MYASSERT(makeInOut6Int, ArrayOps::check6Int(iarray) == true);
    iarray.deleteRef();
    magicNumber = clearstack(magicNumber);
  }

  {
    magicNumber = clearstack(magicNumber);
    sidl::array<int> iarray;
    ArrayOps::makeInOut7Int(iarray);
    MYASSERT(makeInOut7Int, ArrayOps::check7Int(iarray) == true);
    iarray.deleteRef();
    magicNumber = clearstack(magicNumber);
  }

  {
    const int32_t numElem[] = { (TEST_SIZE+1)/2 };
    const int32_t numElemTwo[] = { TEST_SIZE/2 };
    const int32_t start[] = { 0 };
    const int32_t startTwo[] = { 1 };
    const int32_t stride[] = { 2 };
    magicNumber = clearstack(magicNumber);
    sidl::array<ArrayTest::ArrayOps> objarray = 
      sidl::array<ArrayTest::ArrayOps>::create1d(TEST_SIZE);
    MYASSERT(create1d, objarray._not_nil());
    MYASSERT(create1d, ArrayOps::checkObject(objarray) == 0);
    for(int32_t i = 0; i < TEST_SIZE; i += 2) {
      objarray.set(i, ArrayOps::_create());
    }
    MYASSERT(create1d, ArrayOps::checkObject(objarray) == ((TEST_SIZE+1)/2));
    sidl::array<ArrayTest::ArrayOps> sliced = 
      objarray.slice(1, numElem, start, stride);
    MYASSERT(create1d, sliced._not_nil());
    MYASSERT(create1d, ArrayOps::checkObject(sliced) == ((TEST_SIZE+1)/2));
    sliced = objarray.slice(1, numElemTwo, startTwo, stride);
    MYASSERT(create1d, sliced._not_nil());
    MYASSERT(create1d, ArrayOps::checkObject(sliced) == 0);
    objarray.smartCopy();
    MYASSERT(create1d, ArrayOps::checkObject(objarray) == ((TEST_SIZE+1)/2));
    sliced.smartCopy();
    MYASSERT(create1d, ArrayOps::checkObject(sliced) == 0);

    MYASSERT(createObjectNegOne, ArrayOps::createObject(-1)._is_nil());
    MYASSERT(checkObjectNull, !ArrayOps::checkObject(NULL));
    magicNumber = clearstack(magicNumber);
  }


  {
    magicNumber = clearstack(magicNumber);
    ::sidl::array<bool> ary;
    MYASSERT(createBoolNegOne, ArrayOps::createBool(-1)._is_nil());
    ArrayOps::makeBool(-1, ary);
    MYASSERT(makeBoolNegOne, ary._is_nil());
    magicNumber = clearstack(magicNumber);
  }



  {
    magicNumber = clearstack(magicNumber);
    ::sidl::array<char> ary;
    MYASSERT(createCharNegOne, ArrayOps::createChar(-1)._is_nil());
    ArrayOps::makeChar(-1, ary);
    MYASSERT(makeCharNegOne, ary._is_nil());
    magicNumber = clearstack(magicNumber);
  }



  {
    magicNumber = clearstack(magicNumber);
    ::sidl::array<int32_t> ary;
    MYASSERT(createIntNegOne, ArrayOps::createInt(-1)._is_nil());
    ArrayOps::makeInt(-1, ary);
    MYASSERT(makeIntNegOne, ary._is_nil());
    magicNumber = clearstack(magicNumber);
  }



  {
    magicNumber = clearstack(magicNumber);
    ::sidl::array<int64_t> ary;
    MYASSERT(createLongNegOne, ArrayOps::createLong(-1)._is_nil());
    ArrayOps::makeLong(-1, ary);
    MYASSERT(makeLongNegOne, ary._is_nil());
    magicNumber = clearstack(magicNumber);
  }



  {
    magicNumber = clearstack(magicNumber);
    ::sidl::array<string> ary;
    MYASSERT(createStringNegOne, ArrayOps::createString(-1)._is_nil());
    ArrayOps::makeString(-1, ary);
    MYASSERT(makeStringNegOne, ary._is_nil());
    magicNumber = clearstack(magicNumber);
  }



  {
    magicNumber = clearstack(magicNumber);
    ::sidl::array<double> ary;
    MYASSERT(createDoubleNegOne, ArrayOps::createDouble(-1)._is_nil());
    ArrayOps::makeDouble(-1, ary);
    MYASSERT(makeDoubleNegOne, ary._is_nil());
    magicNumber = clearstack(magicNumber);
  }



  {
    magicNumber = clearstack(magicNumber);
    ::sidl::array<float> ary;
    MYASSERT(createFloatNegOne, ArrayOps::createFloat(-1)._is_nil());
    ArrayOps::makeFloat(-1, ary);
    MYASSERT(makeFloatNegOne, ary._is_nil());
    magicNumber = clearstack(magicNumber);
  }



  {
    magicNumber = clearstack(magicNumber);
    ::sidl::array<sidl::dcomplex> ary;
    MYASSERT(createDcomplexNegOne, ArrayOps::createDcomplex(-1)._is_nil());
    ArrayOps::makeDcomplex(-1, ary);
    MYASSERT(makeDcomplexNegOne, ary._is_nil());
    magicNumber = clearstack(magicNumber);
  }



  {
    magicNumber = clearstack(magicNumber);
    ::sidl::array<sidl::fcomplex> ary;
    MYASSERT(createFcomplexNegOne, ArrayOps::createFcomplex(-1)._is_nil());
    ArrayOps::makeFcomplex(-1, ary);
    MYASSERT(makeFcomplexNegOne, ary._is_nil());
    magicNumber = clearstack(magicNumber);
  }



  {
    magicNumber = clearstack(magicNumber);
    MYASSERT(create2DoubleNegOne, ArrayOps::create2Double(-1,-1)._is_nil());
    magicNumber = clearstack(magicNumber);
  }



  {
    magicNumber = clearstack(magicNumber);
    MYASSERT(create2FcomplexNegOne, ArrayOps::create2Fcomplex(-1,-1)._is_nil());
    magicNumber = clearstack(magicNumber);
  }



  {
    magicNumber = clearstack(magicNumber);
    MYASSERT(create2DcomplexNegOne, ArrayOps::create2Dcomplex(-1,-1)._is_nil());
    magicNumber = clearstack(magicNumber);
  }



  {
    magicNumber = clearstack(magicNumber);
    MYASSERT(create2FloatNegOne, ArrayOps::create2Float(-1,-1)._is_nil());
    magicNumber = clearstack(magicNumber);
  }



  {
    magicNumber = clearstack(magicNumber);
    MYASSERT(create2IntNegOne, ArrayOps::create2Int(-1,-1)._is_nil());
    magicNumber = clearstack(magicNumber);
  }

  {
    int32_t dimen, dimen2, type, type2 = 0;
    ::sidl::basearray garray, garrayret, garrayout, garrayinout;
    ::sidl::array<int32_t> iarray;
    magicNumber = clearstack(magicNumber);
    
    ArrayOps::checkGeneric(garray, dimen, type);
    MYASSERT(Generic array is still Null, garray._is_nil());
    MYASSERT(NULL Generic array has no dimension, (dimen == 0));
    MYASSERT(NULL Generic array has no type, (type == 0));

    dimen = 1;
    type = sidl_int_array;
    garray = ArrayOps::createGeneric(dimen, type);
    MYASSERT(Generic (int) array is not Null, garray._not_nil());
    MYASSERT(Generic (int) array has 1 dimension, (dimen == garray.dimen()));
    MYASSERT(Generic (int) array has int type, type == garray.arrayType());
    
    ArrayOps::checkGeneric(garray, dimen2, type2);
    MYASSERT(checkGeneric (int) array has 1 dimension, (dimen == dimen2));
    MYASSERT(checkGeneric (int) array has int type, (type == type2));

    garrayret = ArrayOps::passGeneric(garray, iarray,
					       garrayout);
    
    MYASSERT(Generic returned array not NULL, garrayret._not_nil());
    MYASSERT(Generic returned array correct dimension, 
	     dimen == garrayret.dimen());
    MYASSERT(Generic returned array correct type, 
	     type == garrayret.arrayType());
    MYASSERT(Generic returned array correct length, 
	     garray.length(0) == garrayret.length(0));

    MYASSERT(Generic returned array not NULL, garrayout._not_nil());
    MYASSERT(Generic returned array correct dimension, 
	     dimen == garrayout.dimen());
    MYASSERT(Generic returned array correct type, 
	     type == garrayout.arrayType());
    MYASSERT(Generic returned array correct length, 
	     garray.length(0) == garrayout.length(0));

    //iarray = ::sidl::array<int32_t>(garray);

    MYASSERT(Generic inout is correct, 
	     ArrayOps::check2Int(iarray) == TRUE);

  }
  {
    int32_t* irarray = NULL;
    magicNumber = clearstack(magicNumber);
    sidl::array<int32_t> iarray = ArrayOps::createInt(TEST_SIZE);

    MYASSERT(createInt, iarray);
    MYASSERT(createInt, ArrayOps::checkInt(iarray) == TRUE);

    irarray = iarray.first();//->d_firstElement;
    MYASSERT(Check rarray int 1, ArrayOps::checkRarray1Int(irarray, TEST_SIZE) == TRUE);
    
  }

  {
    int32_t* irarray = NULL;
    int32_t n,m,o;
    magicNumber = clearstack(magicNumber);
    sidl::array<int32_t> iarray = ArrayOps::create3Int();//iarray = ArrayOps::create3Int();
    MYASSERT(create3Int, ArrayOps::check3Int(iarray) == TRUE);
    iarray.ensure(3,sidl::column_major_order);
    irarray = iarray.first();//->d_firstElement;
    n = iarray.length(0);
    m = iarray.length(1);
    o = iarray.length(2);
    MYASSERT(Check rarray int 3, ArrayOps::checkRarray3Int(irarray, n,m,o) == TRUE);
 
  }

    {
    int32_t* irarray = NULL;
    int32_t n,m,o,p,q,r,s;
    magicNumber = clearstack(magicNumber);
    sidl::array<int32_t> iarray = ArrayOps::create7Int();//iarray = ArrayOps::create7Int();
    MYASSERT(create3Int, ArrayOps::check7Int(iarray) == TRUE);
    iarray.ensure(7,sidl::column_major_order);
    irarray = iarray.first();//->d_firstElement;
    n = iarray.length(0);
    m = iarray.length(1);
    o = iarray.length(2);
    p = iarray.length(3);
    q = iarray.length(4);
    r = iarray.length(5);
    s = iarray.length(6);
    MYASSERT(Check rarray int 7, ArrayOps::checkRarray7Int(irarray, n,m,o,p,q,r,s) == TRUE);

  }


  {
    int32_t irarray[TEST_SIZE]; 
    magicNumber = clearstack(magicNumber);
    ArrayOps::initRarray1Int(irarray, TEST_SIZE);
    MYASSERT(Check rarray int 1, ArrayOps::checkRarray1Int(irarray, TEST_SIZE) == TRUE);
  }

  {
    int32_t irarray[24]; //2*3*4 
    int32_t n=2, m=3 , o=4;
    magicNumber = clearstack(magicNumber);
    ArrayOps::initRarray3Int(irarray, n,m,o);
    MYASSERT(Check rarray int 3, ArrayOps::checkRarray3Int(irarray, n,m,o) == TRUE);

  }

  {
    int32_t irarray[432]; //2*2*2*2*3*3*3 
    int32_t n=2, m=2 , o=2, p=2, q=3,r=3,s=3;
    magicNumber = clearstack(magicNumber);
    ArrayOps::initRarray7Int(irarray, n,m,o,p,q,r,s);
    MYASSERT(Check rarray int 7, ArrayOps::checkRarray7Int(irarray, n,m,o,p,q,r,s) == TRUE);

  }


  {
    double drarray[TEST_SIZE]; 
    magicNumber = clearstack(magicNumber);
    ArrayOps::initRarray1Double(drarray, TEST_SIZE);
    MYASSERT(Check rarray double 1, ArrayOps::checkRarray1Double(drarray, TEST_SIZE) == TRUE);
  }

  {
    struct sidl_dcomplex dcrarray[TEST_SIZE]; 
    magicNumber = clearstack(magicNumber);
    ArrayOps::initRarray1Dcomplex(dcrarray, TEST_SIZE);
    MYASSERT(Check rarray Dcomplex 1, ArrayOps::checkRarray1Dcomplex(dcrarray, TEST_SIZE) == TRUE);
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
    ArrayOps::matrixMultiply(a,b,x,n,m,o);
    MYASSERT(Check Matrix Multiply, ArrayOps::checkMatrixMultiply(a,b,x,n,m,o) == TRUE);
	
  }
  
  {
    magicNumber = clearstack(magicNumber);
    sidl::array<string> sarray = ArrayOps::create2String(12,13);
    MYASSERT(createString, sarray._not_nil());
    MYASSERT(createString, ArrayOps::check2String(sarray) == true);
    sarray.deleteRef();
    magicNumber = clearstack(magicNumber);
  }


  tracker.close();
  return 0;
}
