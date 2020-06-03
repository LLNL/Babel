/*
 * File:          wrapper_Data_Impl.h
 * Symbol:        wrapper.Data-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for wrapper.Data
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_wrapper_Data_Impl_h
#define included_wrapper_Data_Impl_h

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
#ifndef included_sidl_RuntimeException_h
#include "sidl_RuntimeException.h"
#endif
#ifndef included_wrapper_Data_h
#include "wrapper_Data.h"
#endif
/* DO-NOT-DELETE splicer.begin(wrapper.Data._hincludes) */
/* Insert-Code-Here {wrapper.Data._includes} (include files) */
/* DO-NOT-DELETE splicer.end(wrapper.Data._hincludes) */

/*
 * Private data for class wrapper.Data
 */

struct wrapper_Data__data {
  /* DO-NOT-DELETE splicer.begin(wrapper.Data._data) */
  /* Insert-Code-Here {wrapper.Data._data} (private data members) */
  char* d_string;
  int d_int;
  char* d_ctorTest;
  /* DO-NOT-DELETE splicer.end(wrapper.Data._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct wrapper_Data__data*
wrapper_Data__get_data(
  wrapper_Data);

extern void
wrapper_Data__set_data(
  wrapper_Data,
  struct wrapper_Data__data*);

extern
void
impl_wrapper_Data__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_wrapper_Data__ctor(
  /* in */ wrapper_Data self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_wrapper_Data__ctor2(
  /* in */ wrapper_Data self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_wrapper_Data__dtor(
  /* in */ wrapper_Data self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_wrapper_Data_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern
void
impl_wrapper_Data_setString(
  /* in */ wrapper_Data self,
  /* in */ const char* s,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_wrapper_Data_setInt(
  /* in */ wrapper_Data self,
  /* in */ int32_t i,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_wrapper_Data_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
