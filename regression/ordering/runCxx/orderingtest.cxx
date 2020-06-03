/*
 * File:        orderingtest.c
 * Copyright:   (c) 2002 Lawrence Livermore National Security, LLC
 * Revision:    @(#) $Revision: 7452 $
 * Date:        $Date: 2012-04-19 09:44:42 -0700 (Thu, 19 Apr 2012) $
 * Description: Regression test for array ordering code
 *
 */

#include "Ordering_IntOrderTest.hxx"
#include <sstream>
#include "synch.hxx"


static const int TEST_SIZE = 345; /* size of one dimensional arrays */
static const int TEST_DIM1 = 17; /* first dimension of 2-d arrays */
static const int TEST_DIM2 = 13; /* second dimension of 2-d arrays */


#define MYASSERT( AAA ) \
  tracker.startPart( ++part_no ); \
  magicNumber = clearstack(magicNumber); \
  { \
    std::ostringstream buf; \
    buf << "(" << __LINE__ << ") " #AAA; \
    tracker.writeComment(buf.str()); \
  } \
  if ( AAA ) result = synch::ResultType_PASS; \
  else result = synch::ResultType_FAIL;  \
  tracker.endPart(part_no, result);


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


static const int arraySize = 7;

static sidl::array<int32_t> 
make1DIMatrix(const int32_t size)
{
  sidl::array<int32_t> result= sidl::array<int32_t>::create1d(size);
  int32_t i;
  for(i = 0; i < size; ++i ){
    result.set(i,i);
  }
  return result;
}

int main(int argc, char **argv)
{
  int magicNumber = 1;
  synch::ResultType result = synch::ResultType_PASS;
  int part_no = 0;
  
  sidl::array<int32_t> A = NULL;
  synch::RegOut tracker = synch::RegOut::_create();
  tracker.setExpectations(32);
  
  
  A = Ordering::IntOrderTest::makeColumnIMatrix(arraySize, true);
  MYASSERT(A._not_nil() && A.isColumnOrder());
  MYASSERT(Ordering::IntOrderTest::isIMatrixTwo(A));
  MYASSERT(Ordering::IntOrderTest::isColumnIMatrixTwo(A));
  MYASSERT(Ordering::IntOrderTest::isRowIMatrixTwo(A));
  Ordering::IntOrderTest::ensureRow(A);
  MYASSERT(A._not_nil() && A.isRowOrder() &&
           Ordering::IntOrderTest::isIMatrixTwo(A));
  Ordering::IntOrderTest::ensureColumn(A);
  MYASSERT(A._not_nil() && A.isColumnOrder() &&
           Ordering::IntOrderTest::isIMatrixTwo(A));
  Ordering::IntOrderTest::ensureRow(A);
  MYASSERT(A._not_nil() && A.isRowOrder() &&
           Ordering::IntOrderTest::isIMatrixTwo(A));
  Ordering::IntOrderTest::ensureColumn(A);
  MYASSERT(A._not_nil() && A.isColumnOrder() &&
           Ordering::IntOrderTest::isIMatrixTwo(A));
  //  A.deleteRef();

  A = Ordering::IntOrderTest::makeColumnIMatrix(arraySize, false);
  MYASSERT(A._not_nil() && A.isColumnOrder());
  MYASSERT(Ordering::IntOrderTest::isIMatrixTwo(A));
  //  A.deleteRef();

  A = Ordering::IntOrderTest::makeRowIMatrix(arraySize, true);
  MYASSERT(A._not_nil() && A.isRowOrder());
  MYASSERT(Ordering::IntOrderTest::isIMatrixTwo(A));
  MYASSERT(Ordering::IntOrderTest::isColumnIMatrixTwo(A));
  MYASSERT(Ordering::IntOrderTest::isRowIMatrixTwo(A));
  //  A.deleteRef();

  A = Ordering::IntOrderTest::makeRowIMatrix(arraySize, false);
  MYASSERT(A._not_nil() && A.isRowOrder());
  MYASSERT(Ordering::IntOrderTest::isIMatrixTwo(A));
  //  A.deleteRef();

  Ordering::IntOrderTest::createColumnIMatrix(arraySize, true, A);
  MYASSERT(A._not_nil() && A.isColumnOrder());
  MYASSERT(Ordering::IntOrderTest::isIMatrixTwo(A));
  //  A.deleteRef();

  Ordering::IntOrderTest::createColumnIMatrix(arraySize, false, A);
  MYASSERT(A._not_nil() && A.isColumnOrder());
  MYASSERT(Ordering::IntOrderTest::isIMatrixTwo(A));
  //  A.deleteRef();

  Ordering::IntOrderTest::createRowIMatrix(arraySize, true, A);
  MYASSERT(A._not_nil() && A.isRowOrder());
  MYASSERT(Ordering::IntOrderTest::isIMatrixTwo(A));
  //  A.deleteRef();

  Ordering::IntOrderTest::createRowIMatrix(arraySize, false, A);
  MYASSERT(A._not_nil() && A.isRowOrder());
  MYASSERT(Ordering::IntOrderTest::isIMatrixTwo(A));
  //  A.deleteRef();

  A = Ordering::IntOrderTest::makeIMatrix(arraySize, true);
  MYASSERT(A._not_nil() && Ordering::IntOrderTest::isIMatrixFour(A));
  MYASSERT(Ordering::IntOrderTest::isColumnIMatrixFour(A));
  MYASSERT(Ordering::IntOrderTest::isRowIMatrixFour(A));
  //  A.deleteRef();

  A = make1DIMatrix(arraySize);
  MYASSERT(A._not_nil() && Ordering::IntOrderTest::isIMatrixOne(A));
  MYASSERT(Ordering::IntOrderTest::isColumnIMatrixOne(A));
  MYASSERT(Ordering::IntOrderTest::isRowIMatrixOne(A));
  //  A.deleteRef();

  MYASSERT(Ordering::IntOrderTest::isSliceWorking(true));
  MYASSERT(Ordering::IntOrderTest::isSliceWorking(false));

  tracker.close();
  return 0;
}
