/*
 * File:          Ordering_IntOrderTest_Impl.c
 * Symbol:        Ordering.IntOrderTest-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Ordering.IntOrderTest
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "Ordering.IntOrderTest" (version 0.1)
 * 
 * This class provides methods to verify that the array ordering
 * capabilities work for arrays of int.
 */

#include "Ordering_IntOrderTest_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest._includes) */
#include <stdlib.h>
#include "sidl_Exception.h"

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
    int32_t *cindex = malloc(sizeof(int32_t)*dimen);
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
    int32_t *cindex = malloc(sizeof(int32_t)*dimen);
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
/* DO-NOT-DELETE splicer.end(Ordering.IntOrderTest._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_Ordering_IntOrderTest__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Ordering_IntOrderTest__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest._load) */
  /* Insert the implementation of the static class initializer method here... */
    /* DO-NOT-DELETE splicer.end(Ordering.IntOrderTest._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_Ordering_IntOrderTest__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Ordering_IntOrderTest__ctor(
  /* in */ Ordering_IntOrderTest self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest._ctor) */
  /* Insert the implementation of the constructor method here... */
    /* DO-NOT-DELETE splicer.end(Ordering.IntOrderTest._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_Ordering_IntOrderTest__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Ordering_IntOrderTest__ctor2(
  /* in */ Ordering_IntOrderTest self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest._ctor2) */
  /* Insert-Code-Here {Ordering.IntOrderTest._ctor2} (special constructor method) */
    /* DO-NOT-DELETE splicer.end(Ordering.IntOrderTest._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_Ordering_IntOrderTest__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Ordering_IntOrderTest__dtor(
  /* in */ Ordering_IntOrderTest self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest._dtor) */
  /* Insert the implementation of the destructor method here... */
    /* DO-NOT-DELETE splicer.end(Ordering.IntOrderTest._dtor) */
  }
}

/*
 * Create a column-major matrix satisfying condition I.
 */

#undef __FUNC__
#define __FUNC__ "impl_Ordering_IntOrderTest_makeColumnIMatrix"

#ifdef __cplusplus
extern "C"
#endif
struct sidl_int__array*
impl_Ordering_IntOrderTest_makeColumnIMatrix(
  /* in */ int32_t size,
  /* in */ sidl_bool useCreateCol,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.makeColumnIMatrix) */
  struct sidl_int__array *res;
  if (useCreateCol) {
    res = sidl_int__array_create2dCol(size, size);
  }
  else {
    res = sidl_int__array_create2dRow(size, size);
  }
  fillIMatrix(res);
  return res;
    /* DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.makeColumnIMatrix) */
  }
}

/*
 * Create a row-major matrix satisfying condition I.
 */

#undef __FUNC__
#define __FUNC__ "impl_Ordering_IntOrderTest_makeRowIMatrix"

#ifdef __cplusplus
extern "C"
#endif
struct sidl_int__array*
impl_Ordering_IntOrderTest_makeRowIMatrix(
  /* in */ int32_t size,
  /* in */ sidl_bool useCreateRow,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.makeRowIMatrix) */
  struct sidl_int__array *res;
  if (useCreateRow) {
    res = sidl_int__array_create2dRow(size, size);
  }
  else {
    res = sidl_int__array_create2dCol(size, size);
  }
  fillIMatrix(res);
  return res;
    /* DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.makeRowIMatrix) */
  }
}

/*
 * Create a 4-D matrix satisfying condition I.  Each dimension has
 * size elements numbers 0 through size-1.
 */

#undef __FUNC__
#define __FUNC__ "impl_Ordering_IntOrderTest_makeIMatrix"

#ifdef __cplusplus
extern "C"
#endif
struct sidl_int__array*
impl_Ordering_IntOrderTest_makeIMatrix(
  /* in */ int32_t size,
  /* in */ sidl_bool useCreateColumn,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.makeIMatrix) */
  struct sidl_int__array *res;
  static const int lower[4] = { 0, 0, 0, 0};
  int upper[4];
  upper[0] = upper[1] = upper[2] = upper[3] = size - 1;
  if (useCreateColumn) {
    res = sidl_int__array_createCol(4, lower, upper);
  }
  else {
    res = sidl_int__array_createRow(4, lower, upper);
  }
  fillIMatrix(res);
  return res;
    /* DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.makeIMatrix) */
  }
}

/*
 * Create a column-major matrix satisfying condition I.
 */

#undef __FUNC__
#define __FUNC__ "impl_Ordering_IntOrderTest_createColumnIMatrix"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Ordering_IntOrderTest_createColumnIMatrix(
  /* in */ int32_t size,
  /* in */ sidl_bool useCreateCol,
  /* out array<int,2,column-major> */ struct sidl_int__array** res,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.createColumnIMatrix) */
  if (useCreateCol) {
    *res = Ordering_IntOrderTest_makeColumnIMatrix(size, TRUE, _ex);
  }
  else{
    *res = Ordering_IntOrderTest_makeRowIMatrix(size, TRUE, _ex);
  }
    /* DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.createColumnIMatrix) */
  }
}

/*
 * Create a row-major matrix satisfying condition I.
 */

#undef __FUNC__
#define __FUNC__ "impl_Ordering_IntOrderTest_createRowIMatrix"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Ordering_IntOrderTest_createRowIMatrix(
  /* in */ int32_t size,
  /* in */ sidl_bool useCreateRow,
  /* out array<int,2,row-major> */ struct sidl_int__array** res,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.createRowIMatrix) */
  if (useCreateRow) {
    *res = Ordering_IntOrderTest_makeRowIMatrix(size, TRUE, _ex);
  }
  else{
    *res = Ordering_IntOrderTest_makeColumnIMatrix(size, TRUE, _ex);
  }
    /* DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.createRowIMatrix) */
  }
}

/*
 * Make sure an array is column-major.  No changes to the dimension or
 * values in a are made.
 */

#undef __FUNC__
#define __FUNC__ "impl_Ordering_IntOrderTest_ensureColumn"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Ordering_IntOrderTest_ensureColumn(
  /* inout array<int,2,column-major> */ struct sidl_int__array** a,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.ensureColumn) */
  /* no action required */
    /* DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.ensureColumn) */
  }
}

/*
 * Make sure an array is row-major.  No changes to the dimension or
 * values in a are made.
 */

#undef __FUNC__
#define __FUNC__ "impl_Ordering_IntOrderTest_ensureRow"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Ordering_IntOrderTest_ensureRow(
  /* inout array<int,2,row-major> */ struct sidl_int__array** a,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.ensureRow) */
  /* no action required */
    /* DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.ensureRow) */
  }
}

/*
 * Return <code>true</code> iff the implementation sees
 * an incoming array satisfying condition I.
 */

#undef __FUNC__
#define __FUNC__ "impl_Ordering_IntOrderTest_isIMatrixOne"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_Ordering_IntOrderTest_isIMatrixOne(
  /* in array<int> */ struct sidl_int__array* A,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isIMatrixOne) */
  return isIMatrix(A);
    /* DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isIMatrixOne) */
  }
}

/*
 * Return <code>true</code> iff the implementation sees
 * an incoming column-major array satisfying condition I.
 */

#undef __FUNC__
#define __FUNC__ "impl_Ordering_IntOrderTest_isColumnIMatrixOne"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_Ordering_IntOrderTest_isColumnIMatrixOne(
  /* in array<int,column-major> */ struct sidl_int__array* A,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isColumnIMatrixOne) */
  return sidl_int__array_isColumnOrder(A) && isIMatrix(A);
    /* DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isColumnIMatrixOne) */
  }
}

/*
 * Return <code>true</code> iff the implementation sees
 * an incoming row-major array satisfying condition I.
 */

#undef __FUNC__
#define __FUNC__ "impl_Ordering_IntOrderTest_isRowIMatrixOne"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_Ordering_IntOrderTest_isRowIMatrixOne(
  /* in array<int,row-major> */ struct sidl_int__array* A,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isRowIMatrixOne) */
  return sidl_int__array_isRowOrder(A) && isIMatrix(A);
    /* DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isRowIMatrixOne) */
  }
}

/*
 * Return <code>true</code> iff the implementation sees
 * an incoming array satisfying condition I.
 */

#undef __FUNC__
#define __FUNC__ "impl_Ordering_IntOrderTest_isIMatrixTwo"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_Ordering_IntOrderTest_isIMatrixTwo(
  /* in array<int,2> */ struct sidl_int__array* A,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isIMatrixTwo) */
  return isIMatrix(A);
    /* DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isIMatrixTwo) */
  }
}

/*
 * Return <code>true</code> iff the implementation sees
 * an incoming column-major array satisfying condition I.
 */

#undef __FUNC__
#define __FUNC__ "impl_Ordering_IntOrderTest_isColumnIMatrixTwo"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_Ordering_IntOrderTest_isColumnIMatrixTwo(
  /* in array<int,2,column-major> */ struct sidl_int__array* A,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isColumnIMatrixTwo) */
  return sidl_int__array_isColumnOrder(A) && isIMatrix(A);
    /* DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isColumnIMatrixTwo) */
  }
}

/*
 * Return <code>true</code> iff the implementation sees
 * an incoming row-major array satisfying condition I.
 */

#undef __FUNC__
#define __FUNC__ "impl_Ordering_IntOrderTest_isRowIMatrixTwo"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_Ordering_IntOrderTest_isRowIMatrixTwo(
  /* in array<int,2,row-major> */ struct sidl_int__array* A,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isRowIMatrixTwo) */
  return sidl_int__array_isRowOrder(A) && isIMatrix(A);
    /* DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isRowIMatrixTwo) */
  }
}

/*
 * Return <code>true</code> iff the implementation sees
 * an incoming array satisfying condition I.
 */

#undef __FUNC__
#define __FUNC__ "impl_Ordering_IntOrderTest_isIMatrixFour"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_Ordering_IntOrderTest_isIMatrixFour(
  /* in array<int,4> */ struct sidl_int__array* A,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isIMatrixFour) */
  return isIMatrix(A);
    /* DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isIMatrixFour) */
  }
}

/*
 * Return <code>true</code> iff the implementation sees
 * an incoming column-major array satisfying condition I.
 */

#undef __FUNC__
#define __FUNC__ "impl_Ordering_IntOrderTest_isColumnIMatrixFour"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_Ordering_IntOrderTest_isColumnIMatrixFour(
  /* in array<int,4,column-major> */ struct sidl_int__array* A,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isColumnIMatrixFour) */
  return sidl_int__array_isColumnOrder(A) && isIMatrix(A);
    /* DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isColumnIMatrixFour) */
  }
}

/*
 * Return <code>true</code> iff the implementation sees
 * an incoming row-major array satisfying condition I.
 */

#undef __FUNC__
#define __FUNC__ "impl_Ordering_IntOrderTest_isRowIMatrixFour"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_Ordering_IntOrderTest_isRowIMatrixFour(
  /* in array<int,4,row-major> */ struct sidl_int__array* A,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isRowIMatrixFour) */
  return sidl_int__array_isRowOrder(A) && isIMatrix(A);
    /* DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isRowIMatrixFour) */
  }
}

/*
 * Return <code>true</code> iff the implementation of slice
 * and smart copy is correct.
 */

#undef __FUNC__
#define __FUNC__ "impl_Ordering_IntOrderTest_isSliceWorking"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_Ordering_IntOrderTest_isSliceWorking(
  /* in */ sidl_bool useCreateCol,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isSliceWorking) */
  sidl_bool res = TRUE;
  const int32_t maxDim = 16;
  const int32_t halfDim = maxDim >> 1;
  struct sidl_int__array *A=NULL, *B=NULL;
  int32_t ind[2];
  int32_t stride[2] = {2, 2};
  int32_t numElem[2];
  int32_t newIndex[2];
  numElem[0] = numElem[1] = halfDim;
  if (useCreateCol) {
    A = Ordering_IntOrderTest_makeColumnIMatrix(maxDim,FALSE, _ex); SIDL_REPORT(*_ex);
  }
  else {
    A = Ordering_IntOrderTest_makeRowIMatrix(maxDim,FALSE, _ex);SIDL_REPORT(*_ex);
  }
  B = sidl_int__array_slice(A, 2, numElem, NULL, stride, NULL);
  if (!(B && sidl_int__array_dimen(B) == 2)) {
    res = FALSE;
    goto EXIT;
  }
  for(ind[1] = newIndex[1] = 0; newIndex[1] < halfDim; 
      ++newIndex[1], ind[1] += 2) {
    for(ind[0] = newIndex[0] = 0; newIndex[0] < halfDim;
        ++newIndex[0], ind[0] += 2) {
      if ((sidlArrayElem2(B, newIndex[0], newIndex[1]) != iFunc(ind,2)) ||
          (sidlArrayAddr2(B, newIndex[0], newIndex[1]) !=
           sidlArrayAddr2(A, ind[0], ind[1]))) {
        res = FALSE;
        goto EXIT;
      }
    }
  }

  sidl_int__array_deleteRef(B);
  ind[0] = ind[1] = 1;
  newIndex[0] = newIndex[1] = 0;
  B = sidl_int__array_slice(A, 2, numElem, ind, stride, newIndex);
  if (!(B && sidl_int__array_dimen(B) == 2)) {
    res = FALSE;
    goto EXIT;
  }
  for(newIndex[1] = 0; newIndex[1] < halfDim; 
      ++newIndex[1], ind[1] += 2) {
    for(ind[0] = 1, newIndex[0] = 0; newIndex[0] < halfDim;
        ++newIndex[0], ind[0] += 2) {
      if ((sidlArrayElem2(B, newIndex[0], newIndex[1]) != iFunc(ind,2)) ||
          (sidlArrayAddr2(B, newIndex[0], newIndex[1]) !=
           sidlArrayAddr2(A, ind[0], ind[1]))) {
        res = FALSE;
        goto EXIT;
      }
    }
  }

  sidl_int__array_deleteRef(B);
  ind[0] = ind[1] = 1;
  newIndex[1] = newIndex[0] = 1;
  B = sidl_int__array_slice(A, 2, numElem, ind, stride, newIndex);
  if (!(B && sidl_int__array_dimen(B) == 2)) {
    res = FALSE;
    goto EXIT;
  }
  for(newIndex[1] = 1; newIndex[1] <= halfDim; 
      ++newIndex[1], ind[1] += 2) {
    for(ind[0] = 1, newIndex[0] = 1; newIndex[0] <= halfDim;
        ++newIndex[0], ind[0] += 2) {
      if ((sidlArrayElem2(B, newIndex[0], newIndex[1]) != iFunc(ind,2)) ||
          (sidlArrayAddr2(B, newIndex[0], newIndex[1]) != 
           sidlArrayAddr2(A, ind[0], ind[1]))){
        res = FALSE;
        goto EXIT;
      }
    }
  }

  sidl_int__array_deleteRef(B);
  B = NULL;
  numElem[0] = 0;
  numElem[1] = maxDim;
  B = sidl_int__array_slice(A, 1, numElem, NULL, NULL, NULL);
  if (!(B && sidl_int__array_dimen(B) == 1)) {
    res = FALSE;
    goto EXIT;
  }
  ind[0] = newIndex[0] = 0;
  for(ind[1] = newIndex[1] = 0; newIndex[1] < maxDim; 
      ++newIndex[1], ++ind[1]) {
    if (sidlArrayAddr1(B, newIndex[1]) != 
        sidlArrayAddr2(A, ind[0], ind[1])) {
      res = FALSE;
      goto EXIT;
    }
  }
  
  sidl_int__array_deleteRef(B);
  B = NULL;
  numElem[0] = maxDim;
  numElem[1] = 0;
  ind[0] = 0;
  ind[1] = 8;
  newIndex[0] = newIndex[1] = 0;
  B = sidl_int__array_slice(A, 1, numElem, ind, NULL, newIndex);
  if (!(B && (sidl_int__array_dimen(B) == 1))) {
    res = FALSE;
    goto EXIT;
  }
  for(ind[0] = newIndex[0] = 0; newIndex[0] < maxDim; 
      ++newIndex[0], ++ind[0]) {
    if (sidlArrayAddr1(B, newIndex[0]) != 
        sidlArrayAddr2(A, ind[0], ind[1])) {
      res = FALSE;
      goto EXIT;
    }
  }
  
  sidl_int__array_deleteRef(B);
  B = NULL;
  numElem[0] = maxDim;
  numElem[1] = 0;
  ind[0] = 0;
  ind[1] = 0;
  B = sidl_int__array_slice(A, 1, numElem, ind, NULL, NULL);
  if (!(B && (sidl_int__array_dimen(B) == 1))) {
    res = FALSE;
    goto EXIT;
  }
  if (!(Ordering_IntOrderTest_isIMatrixOne(B, _ex) &&
        Ordering_IntOrderTest_isColumnIMatrixOne(B, _ex) &&
        Ordering_IntOrderTest_isRowIMatrixOne(B, _ex))) {
    res = FALSE;
    goto EXIT;
  }
        
  
 EXIT:
  if (B) sidl_int__array_deleteRef(B);
  if (A) sidl_int__array_deleteRef(A);
  return res;
    /* DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isSliceWorking) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

