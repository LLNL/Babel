/*
 * File:          Ordering_IntOrderTest_Impl.h
 * Symbol:        Ordering.IntOrderTest-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Ordering.IntOrderTest
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_Ordering_IntOrderTest_Impl_h
#define included_Ordering_IntOrderTest_Impl_h

#ifndef included_sidl_header_h
#include "sidl_header.h"
#endif
#ifndef included_Ordering_IntOrderTest_h
#include "Ordering_IntOrderTest.h"
#endif
#ifndef included_sidl_BaseClass_h
#include "sidl_BaseClass.h"
#endif
#ifndef included_sidl_BaseInterface_h
#include "sidl_BaseInterface.h"
#endif
#ifndef included_sidl_ClassInfo_h
#include "sidl_ClassInfo.h"
#endif
#ifndef included_sidl_RuntimeException_h
#include "sidl_RuntimeException.h"
#endif
/* DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest._hincludes) */
/* Put additional include files here... */
/* DO-NOT-DELETE splicer.end(Ordering.IntOrderTest._hincludes) */

/*
 * Private data for class Ordering.IntOrderTest
 */

struct Ordering_IntOrderTest__data {
  /* DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest._data) */
  /* Put private data members here... */
  int ignore; /* dummy to force non-empty struct; remove if you add data */
  /* DO-NOT-DELETE splicer.end(Ordering.IntOrderTest._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct Ordering_IntOrderTest__data*
Ordering_IntOrderTest__get_data(
  Ordering_IntOrderTest);

extern void
Ordering_IntOrderTest__set_data(
  Ordering_IntOrderTest,
  struct Ordering_IntOrderTest__data*);

extern
void
impl_Ordering_IntOrderTest__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Ordering_IntOrderTest__ctor(
  /* in */ Ordering_IntOrderTest self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Ordering_IntOrderTest__ctor2(
  /* in */ Ordering_IntOrderTest self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Ordering_IntOrderTest__dtor(
  /* in */ Ordering_IntOrderTest self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

extern
struct sidl_int__array*
impl_Ordering_IntOrderTest_makeColumnIMatrix(
  /* in */ int32_t size,
  /* in */ sidl_bool useCreateCol,
  /* out */ sidl_BaseInterface *_ex);

extern
struct sidl_int__array*
impl_Ordering_IntOrderTest_makeRowIMatrix(
  /* in */ int32_t size,
  /* in */ sidl_bool useCreateRow,
  /* out */ sidl_BaseInterface *_ex);

extern
struct sidl_int__array*
impl_Ordering_IntOrderTest_makeIMatrix(
  /* in */ int32_t size,
  /* in */ sidl_bool useCreateColumn,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Ordering_IntOrderTest_createColumnIMatrix(
  /* in */ int32_t size,
  /* in */ sidl_bool useCreateCol,
  /* out array<int,2,column-major> */ struct sidl_int__array** res,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Ordering_IntOrderTest_createRowIMatrix(
  /* in */ int32_t size,
  /* in */ sidl_bool useCreateRow,
  /* out array<int,2,row-major> */ struct sidl_int__array** res,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Ordering_IntOrderTest_ensureColumn(
  /* inout array<int,2,column-major> */ struct sidl_int__array** a,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Ordering_IntOrderTest_ensureRow(
  /* inout array<int,2,row-major> */ struct sidl_int__array** a,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_Ordering_IntOrderTest_isIMatrixOne(
  /* in array<int> */ struct sidl_int__array* A,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_Ordering_IntOrderTest_isColumnIMatrixOne(
  /* in array<int,column-major> */ struct sidl_int__array* A,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_Ordering_IntOrderTest_isRowIMatrixOne(
  /* in array<int,row-major> */ struct sidl_int__array* A,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_Ordering_IntOrderTest_isIMatrixTwo(
  /* in array<int,2> */ struct sidl_int__array* A,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_Ordering_IntOrderTest_isColumnIMatrixTwo(
  /* in array<int,2,column-major> */ struct sidl_int__array* A,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_Ordering_IntOrderTest_isRowIMatrixTwo(
  /* in array<int,2,row-major> */ struct sidl_int__array* A,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_Ordering_IntOrderTest_isIMatrixFour(
  /* in array<int,4> */ struct sidl_int__array* A,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_Ordering_IntOrderTest_isColumnIMatrixFour(
  /* in array<int,4,column-major> */ struct sidl_int__array* A,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_Ordering_IntOrderTest_isRowIMatrixFour(
  /* in array<int,4,row-major> */ struct sidl_int__array* A,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_Ordering_IntOrderTest_isSliceWorking(
  /* in */ sidl_bool useCreateCol,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_Ordering_IntOrderTest_fconnect_sidl_BaseInterface(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_Ordering_IntOrderTest_fconnect_sidl_BaseInterface(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
