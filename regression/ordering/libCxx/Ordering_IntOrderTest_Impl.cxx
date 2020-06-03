// 
// File:          Ordering_IntOrderTest_Impl.cxx
// Symbol:        Ordering.IntOrderTest-v0.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for Ordering.IntOrderTest
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "Ordering_IntOrderTest_Impl.hxx"

// 
// Includes for all method dependencies.
// 
#ifndef included_sidl_BaseInterface_hxx
#include "sidl_BaseInterface.hxx"
#endif
#ifndef included_sidl_ClassInfo_hxx
#include "sidl_ClassInfo.hxx"
#endif
#ifndef included_sidl_NotImplementedException_hxx
#include "sidl_NotImplementedException.hxx"
#endif
// DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest._includes)
#include "sidl_int_IOR.h"
#include <stdlib.h>
using namespace Ordering;

static int32_t
iFunc(const int32_t *ind, const int32_t dim)
{
  int32_t res = 0;
  int32_t i;
  for(i = 0; i < dim ; ++i) {
    res += (i+1)*ind[i];
  }
  return res;
}

static int
incIndex(int32_t ind[], const struct sidl_int__array* A, const int32_t dimen)
{
  int32_t i = 0;
  while (i < dimen) {
    if (++(ind[i]) > sidlUpper(A, i)) {
      ind[i] = sidlLower(A, i);
      ++i;
    }
    else {
      return TRUE;
    }
  }
  /* we're all done */
  return FALSE;
}

static sidl_bool
isIMatrix(const struct sidl_int__array* A)
{
  if (A) {
    const int32_t dimen = sidl_int__array_dimen(A);
    int32_t i;
    int32_t *cindex = (int32_t*)malloc(sizeof(int32_t)*dimen);
    for(i = 0; i < dimen; ++i){
      cindex[i] = sidlLower(A, i);
      if (sidlLower(A,i) > sidlUpper(A,i)) {
        free(cindex);
        return TRUE;
      }
    }
    do {
      if (iFunc(cindex, dimen) != sidl_int__array_get(A, cindex)) {
        free(cindex);
        return FALSE;
      }
    } while (incIndex(cindex, A, dimen));
    free(cindex);
    return TRUE;
  }
  return FALSE;
}

static void
fillIMatrix(struct sidl_int__array* A)
{
  if (A) {
    const int32_t dimen = sidl_int__array_dimen(A);
    int32_t i;
    int32_t *cindex = (int32_t*)malloc(sizeof(int32_t)*dimen);
    for(i = 0; i < dimen; ++i){
      cindex[i] = sidlLower(A, i);
      if (sidlLower(A,i) > sidlUpper(A,i)){
        free(cindex);
        return;
      }
    }
    do {
      sidl_int__array_set(A, cindex, iFunc(cindex, dimen));
    } while (incIndex(cindex, A, dimen));
    free(cindex);
  }
}
// DO-NOT-DELETE splicer.end(Ordering.IntOrderTest._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
Ordering::IntOrderTest_impl::IntOrderTest_impl() : StubBase(reinterpret_cast< 
  void*>(::Ordering::IntOrderTest::_wrapObj(reinterpret_cast< void*>(this))),
  false) , _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest._ctor2)
  // Insert-Code-Here {Ordering.IntOrderTest._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest._ctor2)
}

// user defined constructor
void Ordering::IntOrderTest_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest._ctor)
  // add construction details here
  // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest._ctor)
}

// user defined destructor
void Ordering::IntOrderTest_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest._dtor)
}

// static class initializer
void Ordering::IntOrderTest_impl::_load() {
  // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest._load)
  // guaranteed to be called at most once before any other method in this class
  // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest._load)
}

// user defined static methods:
/**
 * Create a column-major matrix satisfying condition I.
 */
::sidl::array<int32_t>
Ordering::IntOrderTest_impl::makeColumnIMatrix_impl (
  /* in */int32_t size,
  /* in */bool useCreateCol ) 
{
  // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.makeColumnIMatrix)
  sidl::array<int32_t> res;
  if (useCreateCol) {
    res = sidl::array<int32_t>::create2dCol(size, size);
  }
  else {
    res = sidl::array<int32_t>::create2dRow(size, size);
  }
  fillIMatrix(res._get_ior());
  return res;
  // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.makeColumnIMatrix)
}

/**
 * Create a row-major matrix satisfying condition I.
 */
::sidl::array<int32_t>
Ordering::IntOrderTest_impl::makeRowIMatrix_impl (
  /* in */int32_t size,
  /* in */bool useCreateRow ) 
{
  // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.makeRowIMatrix)
  ::sidl::array<int32_t> res;
  if (useCreateRow) {
    res = ::sidl::array<int32_t>::create2dRow(size, size);
  }
  else {
    res = ::sidl::array<int32_t>::create2dCol(size, size);
  }
  fillIMatrix(res._get_ior());
  return res;
  // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.makeRowIMatrix)
}

/**
 * Create a 4-D matrix satisfying condition I.  Each dimension has
 * size elements numbers 0 through size-1.
 */
::sidl::array<int32_t>
Ordering::IntOrderTest_impl::makeIMatrix_impl (
  /* in */int32_t size,
  /* in */bool useCreateColumn ) 
{
  // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.makeIMatrix)
  ::sidl::array<int32_t> res;
  static const int lower[4] = { 0, 0, 0, 0};
  const int upper[4] = { size - 1, size - 1, size -1, size - 1};
  if (useCreateColumn) {
    res = ::sidl::array<int32_t>::createCol(4, lower, upper);
  }
  else {
    res = ::sidl::array<int32_t>::createRow(4, lower, upper);
  }
  fillIMatrix(res._get_ior());
  return res;
  // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.makeIMatrix)
}

/**
 * Create a column-major matrix satisfying condition I.
 */
void
Ordering::IntOrderTest_impl::createColumnIMatrix_impl (
  /* in */int32_t size,
  /* in */bool useCreateCol,
  /* out array<int,2,column-major> */::sidl::array<int32_t>& res ) 
{
  // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.createColumnIMatrix)
  if (useCreateCol){
    res = IntOrderTest::makeColumnIMatrix(size, true);
  }
  else {
    res = IntOrderTest::makeRowIMatrix(size, true);
  }
  // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.createColumnIMatrix)
}

/**
 * Create a row-major matrix satisfying condition I.
 */
void
Ordering::IntOrderTest_impl::createRowIMatrix_impl (
  /* in */int32_t size,
  /* in */bool useCreateRow,
  /* out array<int,2,row-major> */::sidl::array<int32_t>& res ) 
{
  // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.createRowIMatrix)
  if (useCreateRow){
    res = IntOrderTest::makeRowIMatrix(size, true);
  }
  else {
    res = IntOrderTest::makeColumnIMatrix(size, true);
  }
  // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.createRowIMatrix)
}

/**
 * Make sure an array is column-major.  No changes to the dimension or
 * values in a are made.
 */
void
Ordering::IntOrderTest_impl::ensureColumn_impl (
  /* inout array<int,2,column-major> */::sidl::array<int32_t>& a ) 
{
  // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.ensureColumn)
  /* no action required */
  // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.ensureColumn)
}

/**
 * Make sure an array is row-major.  No changes to the dimension or
 * values in a are made.
 */
void
Ordering::IntOrderTest_impl::ensureRow_impl (
  /* inout array<int,2,row-major> */::sidl::array<int32_t>& a ) 
{
  // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.ensureRow)
  /* no action required */
  // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.ensureRow)
}

/**
 * Return <code>true</code> iff the implementation sees
 * an incoming array satisfying condition I.
 */
bool
Ordering::IntOrderTest_impl::isIMatrixOne_impl (
  /* in array<int> */::sidl::array<int32_t>& A ) 
{
  // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isIMatrixOne)
  return isIMatrix(A._get_ior());
  // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isIMatrixOne)
}

/**
 * Return <code>true</code> iff the implementation sees
 * an incoming column-major array satisfying condition I.
 */
bool
Ordering::IntOrderTest_impl::isColumnIMatrixOne_impl (
  /* in array<int,column-major> */::sidl::array<int32_t>& A ) 
{
  // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isColumnIMatrixOne)
  return A.isColumnOrder() && isIMatrix(A._get_ior());
  // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isColumnIMatrixOne)
}

/**
 * Return <code>true</code> iff the implementation sees
 * an incoming row-major array satisfying condition I.
 */
bool
Ordering::IntOrderTest_impl::isRowIMatrixOne_impl (
  /* in array<int,row-major> */::sidl::array<int32_t>& A ) 
{
  // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isRowIMatrixOne)
  bool i = A.isRowOrder();
  bool j = isIMatrix(A._get_ior());
  return (i && j);
  // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isRowIMatrixOne)
}

/**
 * Return <code>true</code> iff the implementation sees
 * an incoming array satisfying condition I.
 */
bool
Ordering::IntOrderTest_impl::isIMatrixTwo_impl (
  /* in array<int,2> */::sidl::array<int32_t>& A ) 
{
  // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isIMatrixTwo)
  return isIMatrix(A._get_ior());
  // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isIMatrixTwo)
}

/**
 * Return <code>true</code> iff the implementation sees
 * an incoming column-major array satisfying condition I.
 */
bool
Ordering::IntOrderTest_impl::isColumnIMatrixTwo_impl (
  /* in array<int,2,column-major> */::sidl::array<int32_t>& A ) 
{
  // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isColumnIMatrixTwo)
  return A.isColumnOrder() && isIMatrix(A._get_ior());
  // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isColumnIMatrixTwo)
}

/**
 * Return <code>true</code> iff the implementation sees
 * an incoming row-major array satisfying condition I.
 */
bool
Ordering::IntOrderTest_impl::isRowIMatrixTwo_impl (
  /* in array<int,2,row-major> */::sidl::array<int32_t>& A ) 
{
  // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isRowIMatrixTwo)
  bool i = A.isRowOrder();
  bool j = isIMatrix(A._get_ior());
  return (i && j);
  // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isRowIMatrixTwo)
}

/**
 * Return <code>true</code> iff the implementation sees
 * an incoming array satisfying condition I.
 */
bool
Ordering::IntOrderTest_impl::isIMatrixFour_impl (
  /* in array<int,4> */::sidl::array<int32_t>& A ) 
{
  // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isIMatrixFour)
  return isIMatrix(A._get_ior());
  // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isIMatrixFour)
}

/**
 * Return <code>true</code> iff the implementation sees
 * an incoming column-major array satisfying condition I.
 */
bool
Ordering::IntOrderTest_impl::isColumnIMatrixFour_impl (
  /* in array<int,4,column-major> */::sidl::array<int32_t>& A ) 
{
  // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isColumnIMatrixFour)
  return A.isColumnOrder() && isIMatrix(A._get_ior());
  // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isColumnIMatrixFour)
}

/**
 * Return <code>true</code> iff the implementation sees
 * an incoming row-major array satisfying condition I.
 */
bool
Ordering::IntOrderTest_impl::isRowIMatrixFour_impl (
  /* in array<int,4,row-major> */::sidl::array<int32_t>& A ) 
{
  // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isRowIMatrixFour)
  return A.isRowOrder() && isIMatrix(A._get_ior());
  // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isRowIMatrixFour)
}

/**
 * Return <code>true</code> iff the implementation of slice
 * and smart copy is correct.
 */
bool
Ordering::IntOrderTest_impl::isSliceWorking_impl (
  /* in */bool useCreateCol ) 
{
  // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isSliceWorking)
  bool res = true;
  const int32_t maxDim = 16;
  const int32_t halfDim = maxDim >> 1;
  ::sidl::array<int32_t> A, B;
  int32_t ind[2];
  int32_t stride[2] = {2, 2};
  int32_t numElem[2] = {halfDim, halfDim};
  int32_t newIndex[2];
  if (useCreateCol) {
    A = IntOrderTest::makeColumnIMatrix(maxDim,false);
  }
  else {
    A = IntOrderTest::makeRowIMatrix(maxDim,FALSE);
  }
  B = A.slice(2, numElem, 0, stride);
  if (!(B._not_nil() && B.dimen() == 2)) {
    res = false;
  }
  for(ind[1] = newIndex[1] = 0; newIndex[1] < halfDim; 
      ++newIndex[1], ind[1] += 2) {
    for(ind[0] = newIndex[0] = 0; newIndex[0] < halfDim;
        ++newIndex[0], ind[0] += 2) {
      if ((B.get(newIndex) != iFunc(ind,2)) ||
          (sidlArrayAddr2(B._get_ior(), newIndex[0], newIndex[1]) !=
           sidlArrayAddr2(A._get_ior(), ind[0], ind[1]))) {
        res = false;
      }
    }
  }

  ind[0] = ind[1] = 1;
  newIndex[0] = newIndex[1] = 0;
  B = A.slice(2, numElem, ind, stride, newIndex);
  if (!(B._not_nil() && B.dimen() == 2)) {
    res = false;
  }
  for(newIndex[1] = 0; newIndex[1] < halfDim; 
      ++newIndex[1], ind[1] += 2) {
    for(ind[0] = 1, newIndex[0] = 0; newIndex[0] < halfDim;
        ++newIndex[0], ind[0] += 2) {
      if ((B.get(newIndex) != iFunc(ind,2)) ||
          (sidlArrayAddr2(B._get_ior(), newIndex[0], newIndex[1]) !=
           sidlArrayAddr2(A._get_ior(), ind[0], ind[1]))) {
        res = FALSE;
      }
    }
  }

  ind[0] = ind[1] = 1;
  newIndex[1] = newIndex[0] = 1;
  B= A.slice(2, numElem, ind, stride, newIndex);
  if (!(B._not_nil() && B.dimen() == 2)) {
    res = false;
  }
  for(newIndex[1] = 1; newIndex[1] <= halfDim; 
      ++newIndex[1], ind[1] += 2) {
    for(ind[0] = 1, newIndex[0] = 1; newIndex[0] <= halfDim;
        ++newIndex[0], ind[0] += 2) {
      if ((B.get(newIndex) != iFunc(ind,2)) ||
          (sidlArrayAddr2(B._get_ior(), newIndex[0], newIndex[1]) != 
           sidlArrayAddr2(A._get_ior(), ind[0], ind[1]))){
        res = false;
      }
    }
  }

  numElem[0] = 0;
  numElem[1] = maxDim;
  B = A.slice(1, numElem);
  if (!(B._not_nil() && B.dimen() == 1)) {
    res = false;
  }
  ind[0] = newIndex[0] = 0;
  for(ind[1] = newIndex[1] = 0; newIndex[1] < maxDim; 
      ++newIndex[1], ++ind[1]) {
    if (sidlArrayAddr1(B._get_ior(), newIndex[1]) != 
        sidlArrayAddr2(A._get_ior(), ind[0], ind[1])) {
      res = false;
    }
  }
  
  numElem[0] = maxDim;
  numElem[1] = 0;
  ind[0] = 0;
  ind[1] = 8;
  newIndex[0] = newIndex[1] = 0;
  B = A.slice(1, numElem, ind, NULL, newIndex);
  if (!(B._not_nil() && (B.dimen() == 1))) {
    res = false;
  }
  for(ind[0] = newIndex[0] = 0; newIndex[0] < maxDim; 
      ++newIndex[0], ++ind[0]) {
    if (sidlArrayAddr1(B._get_ior(), newIndex[0]) != 
        sidlArrayAddr2(A._get_ior(), ind[0], ind[1])) {
      res = false;
    }
  }
  
  numElem[0] = maxDim;
  numElem[1] = 0;
  ind[0] = 0;
  ind[1] = 0;
  B = A.slice(1, numElem, ind);
  if (!(B._not_nil() && (B.dimen() == 1))) {
    res = false;
  }
  if (!(IntOrderTest::isIMatrixOne(B) &&
        IntOrderTest::isColumnIMatrixOne(B) &&
        IntOrderTest::isRowIMatrixOne(B))) {
    res = false;
  }
        
  
  if (B._not_nil()) B.deleteRef();
  if (A._not_nil()) A.deleteRef();
  return res;
  // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isSliceWorking)
}


// user defined non-static methods: (none)

// DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(Ordering.IntOrderTest._misc)

