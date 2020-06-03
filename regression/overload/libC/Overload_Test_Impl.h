/*
 * File:          Overload_Test_Impl.h
 * Symbol:        Overload.Test-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Overload.Test
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_Overload_Test_Impl_h
#define included_Overload_Test_Impl_h

#ifndef included_sidl_header_h
#include "sidl_header.h"
#endif
#ifndef included_Overload_AClass_h
#include "Overload_AClass.h"
#endif
#ifndef included_Overload_AnException_h
#include "Overload_AnException.h"
#endif
#ifndef included_Overload_BClass_h
#include "Overload_BClass.h"
#endif
#ifndef included_Overload_ParentTest_h
#include "Overload_ParentTest.h"
#endif
#ifndef included_Overload_Test_h
#include "Overload_Test.h"
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
/* DO-NOT-DELETE splicer.begin(Overload.Test._hincludes) */
/* Put additional include files here... */
/* DO-NOT-DELETE splicer.end(Overload.Test._hincludes) */

/*
 * Private data for class Overload.Test
 */

struct Overload_Test__data {
  /* DO-NOT-DELETE splicer.begin(Overload.Test._data) */
  /* Put private data members here... */
  int ignore; /* dummy to force non-empty struct; remove if you add data */
  /* DO-NOT-DELETE splicer.end(Overload.Test._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct Overload_Test__data*
Overload_Test__get_data(
  Overload_Test);

extern void
Overload_Test__set_data(
  Overload_Test,
  struct Overload_Test__data*);

extern
void
impl_Overload_Test__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Overload_Test__ctor(
  /* in */ Overload_Test self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Overload_Test__ctor2(
  /* in */ Overload_Test self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Overload_Test__dtor(
  /* in */ Overload_Test self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct Overload_AClass__object* 
  impl_Overload_Test_fconnect_Overload_AClass(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
extern struct Overload_BClass__object* 
  impl_Overload_Test_fconnect_Overload_BClass(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
extern struct Overload_AnException__object* 
  impl_Overload_Test_fconnect_Overload_AnException(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
extern struct sidl_BaseInterface__object* 
  impl_Overload_Test_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern
double
impl_Overload_Test_getValueIntDouble(
  /* in */ Overload_Test self,
  /* in */ int32_t a,
  /* in */ double b,
  /* out */ sidl_BaseInterface *_ex);

extern
double
impl_Overload_Test_getValueDoubleInt(
  /* in */ Overload_Test self,
  /* in */ double a,
  /* in */ int32_t b,
  /* out */ sidl_BaseInterface *_ex);

extern
double
impl_Overload_Test_getValueIntDoubleFloat(
  /* in */ Overload_Test self,
  /* in */ int32_t a,
  /* in */ double b,
  /* in */ float c,
  /* out */ sidl_BaseInterface *_ex);

extern
double
impl_Overload_Test_getValueDoubleIntFloat(
  /* in */ Overload_Test self,
  /* in */ double a,
  /* in */ int32_t b,
  /* in */ float c,
  /* out */ sidl_BaseInterface *_ex);

extern
double
impl_Overload_Test_getValueIntFloatDouble(
  /* in */ Overload_Test self,
  /* in */ int32_t a,
  /* in */ float b,
  /* in */ double c,
  /* out */ sidl_BaseInterface *_ex);

extern
double
impl_Overload_Test_getValueDoubleFloatInt(
  /* in */ Overload_Test self,
  /* in */ double a,
  /* in */ float b,
  /* in */ int32_t c,
  /* out */ sidl_BaseInterface *_ex);

extern
double
impl_Overload_Test_getValueFloatIntDouble(
  /* in */ Overload_Test self,
  /* in */ float a,
  /* in */ int32_t b,
  /* in */ double c,
  /* out */ sidl_BaseInterface *_ex);

extern
double
impl_Overload_Test_getValueFloatDoubleInt(
  /* in */ Overload_Test self,
  /* in */ float a,
  /* in */ double b,
  /* in */ int32_t c,
  /* out */ sidl_BaseInterface *_ex);

extern
char*
impl_Overload_Test_getValueException(
  /* in */ Overload_Test self,
  /* in */ Overload_AnException v,
  /* out */ sidl_BaseInterface *_ex);

extern
int32_t
impl_Overload_Test_getValueAClass(
  /* in */ Overload_Test self,
  /* in */ Overload_AClass v,
  /* out */ sidl_BaseInterface *_ex);

extern
int32_t
impl_Overload_Test_getValueBClass(
  /* in */ Overload_Test self,
  /* in */ Overload_BClass v,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct Overload_AClass__object* 
  impl_Overload_Test_fconnect_Overload_AClass(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
extern struct Overload_BClass__object* 
  impl_Overload_Test_fconnect_Overload_BClass(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
extern struct Overload_AnException__object* 
  impl_Overload_Test_fconnect_Overload_AnException(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
extern struct sidl_BaseInterface__object* 
  impl_Overload_Test_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
