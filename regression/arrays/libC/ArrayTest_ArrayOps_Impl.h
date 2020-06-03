/*
 * File:          ArrayTest_ArrayOps_Impl.h
 * Symbol:        ArrayTest.ArrayOps-v1.3
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for ArrayTest.ArrayOps
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_ArrayTest_ArrayOps_Impl_h
#define included_ArrayTest_ArrayOps_Impl_h

#ifndef included_sidl_header_h
#include "sidl_header.h"
#endif
#ifndef included_ArrayTest_ArrayOps_h
#include "ArrayTest_ArrayOps.h"
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
/* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps._hincludes) */
/* Put additional include files here... */
/* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps._hincludes) */

/*
 * Private data for class ArrayTest.ArrayOps
 */

struct ArrayTest_ArrayOps__data {
  /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps._data) */
  /* Put private data members here... */
  int ignore; /* dummy to force non-empty struct; remove if you add data */
  /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct ArrayTest_ArrayOps__data*
ArrayTest_ArrayOps__get_data(
  ArrayTest_ArrayOps);

extern void
ArrayTest_ArrayOps__set_data(
  ArrayTest_ArrayOps,
  struct ArrayTest_ArrayOps__data*);

extern
void
impl_ArrayTest_ArrayOps__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_ArrayTest_ArrayOps__ctor(
  /* in */ ArrayTest_ArrayOps self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_ArrayTest_ArrayOps__ctor2(
  /* in */ ArrayTest_ArrayOps self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_ArrayTest_ArrayOps__dtor(
  /* in */ ArrayTest_ArrayOps self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

extern
sidl_bool
impl_ArrayTest_ArrayOps_checkBool(
  /* in array<bool> */ struct sidl_bool__array* a,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_ArrayTest_ArrayOps_checkChar(
  /* in array<char> */ struct sidl_char__array* a,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_ArrayTest_ArrayOps_checkInt(
  /* in array<int> */ struct sidl_int__array* a,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_ArrayTest_ArrayOps_checkLong(
  /* in array<long> */ struct sidl_long__array* a,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_ArrayTest_ArrayOps_checkString(
  /* in array<string> */ struct sidl_string__array* a,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_ArrayTest_ArrayOps_checkDouble(
  /* in array<double> */ struct sidl_double__array* a,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_ArrayTest_ArrayOps_checkFloat(
  /* in array<float> */ struct sidl_float__array* a,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_ArrayTest_ArrayOps_checkFcomplex(
  /* in array<fcomplex> */ struct sidl_fcomplex__array* a,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_ArrayTest_ArrayOps_checkDcomplex(
  /* in array<dcomplex> */ struct sidl_dcomplex__array* a,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_ArrayTest_ArrayOps_check2Int(
  /* in array<int,2> */ struct sidl_int__array* a,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_ArrayTest_ArrayOps_check2Double(
  /* in array<double,2> */ struct sidl_double__array* a,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_ArrayTest_ArrayOps_check2Float(
  /* in array<float,2> */ struct sidl_float__array* a,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_ArrayTest_ArrayOps_check2Fcomplex(
  /* in array<fcomplex,2> */ struct sidl_fcomplex__array* a,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_ArrayTest_ArrayOps_check2Dcomplex(
  /* in array<dcomplex,2> */ struct sidl_dcomplex__array* a,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_ArrayTest_ArrayOps_check3Int(
  /* in array<int,3> */ struct sidl_int__array* a,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_ArrayTest_ArrayOps_check4Int(
  /* in array<int,4> */ struct sidl_int__array* a,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_ArrayTest_ArrayOps_check5Int(
  /* in array<int,5> */ struct sidl_int__array* a,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_ArrayTest_ArrayOps_check6Int(
  /* in array<int,6> */ struct sidl_int__array* a,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_ArrayTest_ArrayOps_check7Int(
  /* in array<int,7> */ struct sidl_int__array* a,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_ArrayTest_ArrayOps_check2String(
  /* in array<string,2> */ struct sidl_string__array* a,
  /* out */ sidl_BaseInterface *_ex);

extern
int32_t
impl_ArrayTest_ArrayOps_checkObject(
  /* in array<ArrayTest.ArrayOps> */ struct ArrayTest_ArrayOps__array* a,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_ArrayTest_ArrayOps_reverseBool(
  /* inout array<bool> */ struct sidl_bool__array** a,
  /* in */ sidl_bool newArray,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_ArrayTest_ArrayOps_reverseChar(
  /* inout array<char> */ struct sidl_char__array** a,
  /* in */ sidl_bool newArray,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_ArrayTest_ArrayOps_reverseInt(
  /* inout array<int> */ struct sidl_int__array** a,
  /* in */ sidl_bool newArray,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_ArrayTest_ArrayOps_reverseLong(
  /* inout array<long> */ struct sidl_long__array** a,
  /* in */ sidl_bool newArray,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_ArrayTest_ArrayOps_reverseString(
  /* inout array<string> */ struct sidl_string__array** a,
  /* in */ sidl_bool newArray,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_ArrayTest_ArrayOps_reverseDouble(
  /* inout array<double> */ struct sidl_double__array** a,
  /* in */ sidl_bool newArray,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_ArrayTest_ArrayOps_reverseFloat(
  /* inout array<float> */ struct sidl_float__array** a,
  /* in */ sidl_bool newArray,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_ArrayTest_ArrayOps_reverseFcomplex(
  /* inout array<fcomplex> */ struct sidl_fcomplex__array** a,
  /* in */ sidl_bool newArray,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_ArrayTest_ArrayOps_reverseDcomplex(
  /* inout array<dcomplex> */ struct sidl_dcomplex__array** a,
  /* in */ sidl_bool newArray,
  /* out */ sidl_BaseInterface *_ex);

extern
struct sidl_bool__array*
impl_ArrayTest_ArrayOps_createBool(
  /* in */ int32_t len,
  /* out */ sidl_BaseInterface *_ex);

extern
struct sidl_char__array*
impl_ArrayTest_ArrayOps_createChar(
  /* in */ int32_t len,
  /* out */ sidl_BaseInterface *_ex);

extern
struct sidl_int__array*
impl_ArrayTest_ArrayOps_createInt(
  /* in */ int32_t len,
  /* out */ sidl_BaseInterface *_ex);

extern
struct sidl_long__array*
impl_ArrayTest_ArrayOps_createLong(
  /* in */ int32_t len,
  /* out */ sidl_BaseInterface *_ex);

extern
struct sidl_string__array*
impl_ArrayTest_ArrayOps_createString(
  /* in */ int32_t len,
  /* out */ sidl_BaseInterface *_ex);

extern
struct sidl_double__array*
impl_ArrayTest_ArrayOps_createDouble(
  /* in */ int32_t len,
  /* out */ sidl_BaseInterface *_ex);

extern
struct sidl_float__array*
impl_ArrayTest_ArrayOps_createFloat(
  /* in */ int32_t len,
  /* out */ sidl_BaseInterface *_ex);

extern
struct sidl_fcomplex__array*
impl_ArrayTest_ArrayOps_createFcomplex(
  /* in */ int32_t len,
  /* out */ sidl_BaseInterface *_ex);

extern
struct sidl_dcomplex__array*
impl_ArrayTest_ArrayOps_createDcomplex(
  /* in */ int32_t len,
  /* out */ sidl_BaseInterface *_ex);

extern
struct ArrayTest_ArrayOps__array*
impl_ArrayTest_ArrayOps_createObject(
  /* in */ int32_t len,
  /* out */ sidl_BaseInterface *_ex);

extern
struct sidl_int__array*
impl_ArrayTest_ArrayOps_create2Int(
  /* in */ int32_t d1,
  /* in */ int32_t d2,
  /* out */ sidl_BaseInterface *_ex);

extern
struct sidl_double__array*
impl_ArrayTest_ArrayOps_create2Double(
  /* in */ int32_t d1,
  /* in */ int32_t d2,
  /* out */ sidl_BaseInterface *_ex);

extern
struct sidl_float__array*
impl_ArrayTest_ArrayOps_create2Float(
  /* in */ int32_t d1,
  /* in */ int32_t d2,
  /* out */ sidl_BaseInterface *_ex);

extern
struct sidl_dcomplex__array*
impl_ArrayTest_ArrayOps_create2Dcomplex(
  /* in */ int32_t d1,
  /* in */ int32_t d2,
  /* out */ sidl_BaseInterface *_ex);

extern
struct sidl_fcomplex__array*
impl_ArrayTest_ArrayOps_create2Fcomplex(
  /* in */ int32_t d1,
  /* in */ int32_t d2,
  /* out */ sidl_BaseInterface *_ex);

extern
struct sidl_string__array*
impl_ArrayTest_ArrayOps_create2String(
  /* in */ int32_t d1,
  /* in */ int32_t d2,
  /* out */ sidl_BaseInterface *_ex);

extern
struct sidl_int__array*
impl_ArrayTest_ArrayOps_create3Int(
  /* out */ sidl_BaseInterface *_ex);

extern
struct sidl_int__array*
impl_ArrayTest_ArrayOps_create4Int(
  /* out */ sidl_BaseInterface *_ex);

extern
struct sidl_int__array*
impl_ArrayTest_ArrayOps_create5Int(
  /* out */ sidl_BaseInterface *_ex);

extern
struct sidl_int__array*
impl_ArrayTest_ArrayOps_create6Int(
  /* out */ sidl_BaseInterface *_ex);

extern
struct sidl_int__array*
impl_ArrayTest_ArrayOps_create7Int(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_ArrayTest_ArrayOps_makeBool(
  /* in */ int32_t len,
  /* out array<bool> */ struct sidl_bool__array** a,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_ArrayTest_ArrayOps_makeChar(
  /* in */ int32_t len,
  /* out array<char> */ struct sidl_char__array** a,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_ArrayTest_ArrayOps_makeInt(
  /* in */ int32_t len,
  /* out array<int> */ struct sidl_int__array** a,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_ArrayTest_ArrayOps_makeLong(
  /* in */ int32_t len,
  /* out array<long> */ struct sidl_long__array** a,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_ArrayTest_ArrayOps_makeString(
  /* in */ int32_t len,
  /* out array<string> */ struct sidl_string__array** a,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_ArrayTest_ArrayOps_makeDouble(
  /* in */ int32_t len,
  /* out array<double> */ struct sidl_double__array** a,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_ArrayTest_ArrayOps_makeFloat(
  /* in */ int32_t len,
  /* out array<float> */ struct sidl_float__array** a,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_ArrayTest_ArrayOps_makeFcomplex(
  /* in */ int32_t len,
  /* out array<fcomplex> */ struct sidl_fcomplex__array** a,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_ArrayTest_ArrayOps_makeDcomplex(
  /* in */ int32_t len,
  /* out array<dcomplex> */ struct sidl_dcomplex__array** a,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_ArrayTest_ArrayOps_makeInOutBool(
  /* inout array<bool> */ struct sidl_bool__array** a,
  /* in */ int32_t len,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_ArrayTest_ArrayOps_makeInOutChar(
  /* inout array<char> */ struct sidl_char__array** a,
  /* in */ int32_t len,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_ArrayTest_ArrayOps_makeInOutInt(
  /* inout array<int> */ struct sidl_int__array** a,
  /* in */ int32_t len,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_ArrayTest_ArrayOps_makeInOutLong(
  /* inout array<long> */ struct sidl_long__array** a,
  /* in */ int32_t len,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_ArrayTest_ArrayOps_makeInOutString(
  /* inout array<string> */ struct sidl_string__array** a,
  /* in */ int32_t len,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_ArrayTest_ArrayOps_makeInOutDouble(
  /* inout array<double> */ struct sidl_double__array** a,
  /* in */ int32_t len,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_ArrayTest_ArrayOps_makeInOutFloat(
  /* inout array<float> */ struct sidl_float__array** a,
  /* in */ int32_t len,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_ArrayTest_ArrayOps_makeInOutDcomplex(
  /* inout array<dcomplex> */ struct sidl_dcomplex__array** a,
  /* in */ int32_t len,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_ArrayTest_ArrayOps_makeInOutFcomplex(
  /* inout array<fcomplex> */ struct sidl_fcomplex__array** a,
  /* in */ int32_t len,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_ArrayTest_ArrayOps_makeInOut2Int(
  /* inout array<int,2> */ struct sidl_int__array** a,
  /* in */ int32_t d1,
  /* in */ int32_t d2,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_ArrayTest_ArrayOps_makeInOut2Double(
  /* inout array<double,2> */ struct sidl_double__array** a,
  /* in */ int32_t d1,
  /* in */ int32_t d2,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_ArrayTest_ArrayOps_makeInOut2Float(
  /* inout array<float,2> */ struct sidl_float__array** a,
  /* in */ int32_t d1,
  /* in */ int32_t d2,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_ArrayTest_ArrayOps_makeInOut2Dcomplex(
  /* inout array<dcomplex,2> */ struct sidl_dcomplex__array** a,
  /* in */ int32_t d1,
  /* in */ int32_t d2,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_ArrayTest_ArrayOps_makeInOut2Fcomplex(
  /* inout array<fcomplex,2> */ struct sidl_fcomplex__array** a,
  /* in */ int32_t d1,
  /* in */ int32_t d2,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_ArrayTest_ArrayOps_makeInOut3Int(
  /* inout array<int,3> */ struct sidl_int__array** a,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_ArrayTest_ArrayOps_makeInOut4Int(
  /* inout array<int,4> */ struct sidl_int__array** a,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_ArrayTest_ArrayOps_makeInOut5Int(
  /* inout array<int,5> */ struct sidl_int__array** a,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_ArrayTest_ArrayOps_makeInOut6Int(
  /* inout array<int,6> */ struct sidl_int__array** a,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_ArrayTest_ArrayOps_makeInOut7Int(
  /* inout array<int,7> */ struct sidl_int__array** a,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_ArrayTest_ArrayOps_checkGeneric(
  /* in array<> */ struct sidl__array* a,
  /* out */ int32_t* dmn,
  /* out */ int32_t* tp,
  /* out */ sidl_BaseInterface *_ex);

extern
struct sidl__array*
impl_ArrayTest_ArrayOps_createGeneric(
  /* in */ int32_t dmn,
  /* in */ int32_t tp,
  /* out */ sidl_BaseInterface *_ex);

extern
struct sidl__array*
impl_ArrayTest_ArrayOps_passGeneric(
  /* in array<> */ struct sidl__array* inArg,
  /* inout array<> */ struct sidl__array** inOutArg,
  /* out array<> */ struct sidl__array** outArg,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_ArrayTest_ArrayOps_initRarray1Int(
  /* inout rarray[n] */ int32_t* a,
  /* in */ int32_t n,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_ArrayTest_ArrayOps_initRarray3Int(
  /* inout rarray[n,m,o] */ int32_t* a,
  /* in */ int32_t n,
  /* in */ int32_t m,
  /* in */ int32_t o,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_ArrayTest_ArrayOps_initRarray7Int(
  /* inout rarray[n,m,o,p,q,r,s] */ int32_t* a,
  /* in */ int32_t n,
  /* in */ int32_t m,
  /* in */ int32_t o,
  /* in */ int32_t p,
  /* in */ int32_t q,
  /* in */ int32_t r,
  /* in */ int32_t s,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_ArrayTest_ArrayOps_initRarray1Double(
  /* inout rarray[n] */ double* a,
  /* in */ int32_t n,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_ArrayTest_ArrayOps_initRarray1Dcomplex(
  /* inout rarray[n] */ struct sidl_dcomplex* a,
  /* in */ int32_t n,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_ArrayTest_ArrayOps_checkRarray1Int(
  /* in rarray[n] */ int32_t* a,
  /* in */ int32_t n,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_ArrayTest_ArrayOps_checkRarray3Int(
  /* in rarray[n,m,o] */ int32_t* a,
  /* in */ int32_t n,
  /* in */ int32_t m,
  /* in */ int32_t o,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_ArrayTest_ArrayOps_checkRarray7Int(
  /* in rarray[n,m,o,p,q,r,s] */ int32_t* a,
  /* in */ int32_t n,
  /* in */ int32_t m,
  /* in */ int32_t o,
  /* in */ int32_t p,
  /* in */ int32_t q,
  /* in */ int32_t r,
  /* in */ int32_t s,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_ArrayTest_ArrayOps_checkRarray1Double(
  /* in rarray[n] */ double* a,
  /* in */ int32_t n,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_ArrayTest_ArrayOps_checkRarray1Dcomplex(
  /* in rarray[n] */ struct sidl_dcomplex* a,
  /* in */ int32_t n,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_ArrayTest_ArrayOps_matrixMultiply(
  /* in rarray[n,m] */ int32_t* a,
  /* in rarray[m,o] */ int32_t* b,
  /* inout rarray[n,o] */ int32_t* res,
  /* in */ int32_t n,
  /* in */ int32_t m,
  /* in */ int32_t o,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_ArrayTest_ArrayOps_checkMatrixMultiply(
  /* in rarray[n,m] */ int32_t* a,
  /* in rarray[m,o] */ int32_t* b,
  /* in rarray[n,o] */ int32_t* res,
  /* in */ int32_t n,
  /* in */ int32_t m,
  /* in */ int32_t o,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_ArrayTest_ArrayOps_fconnect_sidl_BaseInterface(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern
void
impl_ArrayTest_ArrayOps_mm(
  /* in */ ArrayTest_ArrayOps self,
  /* in rarray[n,m] */ int32_t* a,
  /* in rarray[m,o] */ int32_t* b,
  /* inout rarray[n,o] */ int32_t* res,
  /* in */ int32_t n,
  /* in */ int32_t m,
  /* in */ int32_t o,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_ArrayTest_ArrayOps_checkmm(
  /* in */ ArrayTest_ArrayOps self,
  /* in rarray[n,m] */ int32_t* a,
  /* in rarray[m,o] */ int32_t* b,
  /* in rarray[n,o] */ int32_t* res,
  /* in */ int32_t n,
  /* in */ int32_t m,
  /* in */ int32_t o,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_ArrayTest_ArrayOps_fconnect_sidl_BaseInterface(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
