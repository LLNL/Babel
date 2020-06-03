/*
 * File:          enums_cartest_Impl.h
 * Symbol:        enums.cartest-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for enums.cartest
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_enums_cartest_Impl_h
#define included_enums_cartest_Impl_h

#ifndef included_sidl_header_h
#include "sidl_header.h"
#endif
#ifndef included_enums_cartest_h
#include "enums_cartest.h"
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
/* DO-NOT-DELETE splicer.begin(enums.cartest._hincludes) */
/* Put additional include files here... */
/* DO-NOT-DELETE splicer.end(enums.cartest._hincludes) */

/*
 * Private data for class enums.cartest
 */

struct enums_cartest__data {
  /* DO-NOT-DELETE splicer.begin(enums.cartest._data) */
  /* Put private data members here... */
  int ignore; /* dummy to force non-empty struct; remove if you add data */
  /* DO-NOT-DELETE splicer.end(enums.cartest._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct enums_cartest__data*
enums_cartest__get_data(
  enums_cartest);

extern void
enums_cartest__set_data(
  enums_cartest,
  struct enums_cartest__data*);

extern
void
impl_enums_cartest__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_enums_cartest__ctor(
  /* in */ enums_cartest self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_enums_cartest__ctor2(
  /* in */ enums_cartest self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_enums_cartest__dtor(
  /* in */ enums_cartest self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_enums_cartest_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern
int64_t
impl_enums_cartest_returnback(
  /* in */ enums_cartest self,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_enums_cartest_passin(
  /* in */ enums_cartest self,
  /* in */ enum enums_car__enum c,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_enums_cartest_passout(
  /* in */ enums_cartest self,
  /* out */ enum enums_car__enum* c,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_enums_cartest_passinout(
  /* in */ enums_cartest self,
  /* inout */ enum enums_car__enum* c,
  /* out */ sidl_BaseInterface *_ex);

extern
int64_t
impl_enums_cartest_passeverywhere(
  /* in */ enums_cartest self,
  /* in */ enum enums_car__enum c1,
  /* out */ enum enums_car__enum* c2,
  /* inout */ enum enums_car__enum* c3,
  /* out */ sidl_BaseInterface *_ex);

extern
struct enums_car__array*
impl_enums_cartest_passarray(
  /* in */ enums_cartest self,
  /* in array<enums.car> */ struct enums_car__array* c1,
  /* out array<enums.car> */ struct enums_car__array** c2,
  /* inout array<enums.car> */ struct enums_car__array** c3,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_enums_cartest_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
