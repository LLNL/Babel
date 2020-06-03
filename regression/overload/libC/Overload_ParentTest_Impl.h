/*
 * File:          Overload_ParentTest_Impl.h
 * Symbol:        Overload.ParentTest-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Overload.ParentTest
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_Overload_ParentTest_Impl_h
#define included_Overload_ParentTest_Impl_h

#ifndef included_sidl_header_h
#include "sidl_header.h"
#endif
#ifndef included_Overload_ParentTest_h
#include "Overload_ParentTest.h"
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
/* DO-NOT-DELETE splicer.begin(Overload.ParentTest._hincludes) */
/* Put additional include files here... */
/* DO-NOT-DELETE splicer.end(Overload.ParentTest._hincludes) */

/*
 * Private data for class Overload.ParentTest
 */

struct Overload_ParentTest__data {
  /* DO-NOT-DELETE splicer.begin(Overload.ParentTest._data) */
  /* Put private data members here... */
  int ignore; /* dummy to force non-empty struct; remove if you add data */
  /* DO-NOT-DELETE splicer.end(Overload.ParentTest._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct Overload_ParentTest__data*
Overload_ParentTest__get_data(
  Overload_ParentTest);

extern void
Overload_ParentTest__set_data(
  Overload_ParentTest,
  struct Overload_ParentTest__data*);

extern
void
impl_Overload_ParentTest__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Overload_ParentTest__ctor(
  /* in */ Overload_ParentTest self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Overload_ParentTest__ctor2(
  /* in */ Overload_ParentTest self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Overload_ParentTest__dtor(
  /* in */ Overload_ParentTest self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_Overload_ParentTest_fconnect_sidl_BaseInterface(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern
int32_t
impl_Overload_ParentTest_getValue(
  /* in */ Overload_ParentTest self,
  /* out */ sidl_BaseInterface *_ex);

extern
int32_t
impl_Overload_ParentTest_getValueInt(
  /* in */ Overload_ParentTest self,
  /* in */ int32_t v,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_Overload_ParentTest_getValueBool(
  /* in */ Overload_ParentTest self,
  /* in */ sidl_bool v,
  /* out */ sidl_BaseInterface *_ex);

extern
double
impl_Overload_ParentTest_getValueDouble(
  /* in */ Overload_ParentTest self,
  /* in */ double v,
  /* out */ sidl_BaseInterface *_ex);

extern
struct sidl_dcomplex
impl_Overload_ParentTest_getValueDcomplex(
  /* in */ Overload_ParentTest self,
  /* in */ struct sidl_dcomplex v,
  /* out */ sidl_BaseInterface *_ex);

extern
float
impl_Overload_ParentTest_getValueFloat(
  /* in */ Overload_ParentTest self,
  /* in */ float v,
  /* out */ sidl_BaseInterface *_ex);

extern
struct sidl_fcomplex
impl_Overload_ParentTest_getValueFcomplex(
  /* in */ Overload_ParentTest self,
  /* in */ struct sidl_fcomplex v,
  /* out */ sidl_BaseInterface *_ex);

extern
char*
impl_Overload_ParentTest_getValueString(
  /* in */ Overload_ParentTest self,
  /* in */ const char* v,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_Overload_ParentTest_fconnect_sidl_BaseInterface(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
