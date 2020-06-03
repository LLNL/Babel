/*
 * File:          enums_numbertest_Impl.h
 * Symbol:        enums.numbertest-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for enums.numbertest
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_enums_numbertest_Impl_h
#define included_enums_numbertest_Impl_h

#ifndef included_sidl_header_h
#include "sidl_header.h"
#endif
#ifndef included_enums_numbertest_h
#include "enums_numbertest.h"
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
/* DO-NOT-DELETE splicer.begin(enums.numbertest._hincludes) */
/* Put additional include files here... */
/* DO-NOT-DELETE splicer.end(enums.numbertest._hincludes) */

/*
 * Private data for class enums.numbertest
 */

struct enums_numbertest__data {
  /* DO-NOT-DELETE splicer.begin(enums.numbertest._data) */
  /* Put private data members here... */
  int ignore; /* dummy to force non-empty struct; remove if you add data */
  /* DO-NOT-DELETE splicer.end(enums.numbertest._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct enums_numbertest__data*
enums_numbertest__get_data(
  enums_numbertest);

extern void
enums_numbertest__set_data(
  enums_numbertest,
  struct enums_numbertest__data*);

extern
void
impl_enums_numbertest__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_enums_numbertest__ctor(
  /* in */ enums_numbertest self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_enums_numbertest__ctor2(
  /* in */ enums_numbertest self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_enums_numbertest__dtor(
  /* in */ enums_numbertest self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_enums_numbertest_fconnect_sidl_BaseInterface(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern
int64_t
impl_enums_numbertest_returnback(
  /* in */ enums_numbertest self,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_enums_numbertest_passin(
  /* in */ enums_numbertest self,
  /* in */ enum enums_number__enum n,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_enums_numbertest_passout(
  /* in */ enums_numbertest self,
  /* out */ enum enums_number__enum* n,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_enums_numbertest_passinout(
  /* in */ enums_numbertest self,
  /* inout */ enum enums_number__enum* n,
  /* out */ sidl_BaseInterface *_ex);

extern
int64_t
impl_enums_numbertest_passeverywhere(
  /* in */ enums_numbertest self,
  /* in */ enum enums_number__enum n1,
  /* out */ enum enums_number__enum* n2,
  /* inout */ enum enums_number__enum* n3,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_enums_numbertest_fconnect_sidl_BaseInterface(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
