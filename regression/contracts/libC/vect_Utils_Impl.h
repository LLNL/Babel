/*
 * File:          vect_Utils_Impl.h
 * Symbol:        vect.Utils-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for vect.Utils
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_vect_Utils_Impl_h
#define included_vect_Utils_Impl_h

#ifndef included_sidl_header_h
#include "sidl_header.h"
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
#ifndef included_sidl_PostViolation_h
#include "sidl_PostViolation.h"
#endif
#ifndef included_sidl_PreViolation_h
#include "sidl_PreViolation.h"
#endif
#ifndef included_sidl_RuntimeException_h
#include "sidl_RuntimeException.h"
#endif
#ifndef included_vect_Utils_h
#include "vect_Utils.h"
#endif
#ifndef included_vect_vDivByZeroExcept_h
#include "vect_vDivByZeroExcept.h"
#endif
#ifndef included_vect_vNegValExcept_h
#include "vect_vNegValExcept.h"
#endif
/* DO-NOT-DELETE splicer.begin(vect.Utils._hincludes) */
/* Nothing needed here. */
/* DO-NOT-DELETE splicer.end(vect.Utils._hincludes) */

/*
 * Private data for class vect.Utils
 */

struct vect_Utils__data {
  /* DO-NOT-DELETE splicer.begin(vect.Utils._data) */
  /* Nothing needed here. */
  int ignore; /* dummy to force non-empty struct; remove if you add data */
  /* DO-NOT-DELETE splicer.end(vect.Utils._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct vect_Utils__data*
vect_Utils__get_data(
  vect_Utils);

extern void
vect_Utils__set_data(
  vect_Utils,
  struct vect_Utils__data*);

extern
void
impl_vect_Utils__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_vect_Utils__ctor(
  /* in */ vect_Utils self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_vect_Utils__ctor2(
  /* in */ vect_Utils self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_vect_Utils__dtor(
  /* in */ vect_Utils self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

extern
sidl_bool
impl_vect_Utils_vuIsZero(
  /* in array<> */ struct sidl__array* u,
  /* in */ double tol,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_vect_Utils_vuIsUnit(
  /* in array<> */ struct sidl__array* u,
  /* in */ double tol,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_vect_Utils_vuAreEqual(
  /* in array<> */ struct sidl__array* u,
  /* in array<> */ struct sidl__array* v,
  /* in */ double tol,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_vect_Utils_vuAreOrth(
  /* in array<> */ struct sidl__array* u,
  /* in array<> */ struct sidl__array* v,
  /* in */ double tol,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_vect_Utils_vuSchwarzHolds(
  /* in array<> */ struct sidl__array* u,
  /* in array<> */ struct sidl__array* v,
  /* in */ double tol,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_vect_Utils_vuTriIneqHolds(
  /* in array<> */ struct sidl__array* u,
  /* in array<> */ struct sidl__array* v,
  /* in */ double tol,
  /* out */ sidl_BaseInterface *_ex);

extern
double
impl_vect_Utils_vuNorm(
  /* in array<> */ struct sidl__array* u,
  /* in */ double tol,
  /* in */ enum vect_BadLevel__enum badLevel,
  /* out */ sidl_BaseInterface *_ex);

extern
double
impl_vect_Utils_vuDot(
  /* in array<> */ struct sidl__array* u,
  /* in array<> */ struct sidl__array* v,
  /* in */ double tol,
  /* in */ enum vect_BadLevel__enum badLevel,
  /* out */ sidl_BaseInterface *_ex);

extern
struct sidl__array*
impl_vect_Utils_vuProduct(
  /* in */ double a,
  /* in array<> */ struct sidl__array* u,
  /* in */ enum vect_BadLevel__enum badLevel,
  /* out */ sidl_BaseInterface *_ex);

extern
struct sidl__array*
impl_vect_Utils_vuNegate(
  /* in array<> */ struct sidl__array* u,
  /* in */ enum vect_BadLevel__enum badLevel,
  /* out */ sidl_BaseInterface *_ex);

extern
struct sidl__array*
impl_vect_Utils_vuNormalize(
  /* in array<> */ struct sidl__array* u,
  /* in */ double tol,
  /* in */ enum vect_BadLevel__enum badLevel,
  /* out */ sidl_BaseInterface *_ex);

extern
struct sidl__array*
impl_vect_Utils_vuSum(
  /* in array<> */ struct sidl__array* u,
  /* in array<> */ struct sidl__array* v,
  /* in */ enum vect_BadLevel__enum badLevel,
  /* out */ sidl_BaseInterface *_ex);

extern
struct sidl__array*
impl_vect_Utils_vuDiff(
  /* in array<> */ struct sidl__array* u,
  /* in array<> */ struct sidl__array* v,
  /* in */ enum vect_BadLevel__enum badLevel,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_vect_Utils_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_vect_Utils_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Nothing needed here. */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
