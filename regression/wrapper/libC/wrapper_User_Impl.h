/*
 * File:          wrapper_User_Impl.h
 * Symbol:        wrapper.User-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for wrapper.User
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_wrapper_User_Impl_h
#define included_wrapper_User_Impl_h

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
#ifndef included_wrapper_User_h
#include "wrapper_User.h"
#endif
/* DO-NOT-DELETE splicer.begin(wrapper.User._hincludes) */
/* Insert-Code-Here {wrapper.User._includes} (include files) */
/* DO-NOT-DELETE splicer.end(wrapper.User._hincludes) */

/*
 * Private data for class wrapper.User
 */

struct wrapper_User__data {
  /* DO-NOT-DELETE splicer.begin(wrapper.User._data) */
  /* Insert-Code-Here {wrapper.User._data} (private data members) */
  int ignore; /* dummy to force non-empty struct; remove if you add data */
  /* DO-NOT-DELETE splicer.end(wrapper.User._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct wrapper_User__data*
wrapper_User__get_data(
  wrapper_User);

extern void
wrapper_User__set_data(
  wrapper_User,
  struct wrapper_User__data*);

extern
void
impl_wrapper_User__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_wrapper_User__ctor(
  /* in */ wrapper_User self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_wrapper_User__ctor2(
  /* in */ wrapper_User self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_wrapper_User__dtor(
  /* in */ wrapper_User self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_wrapper_User_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
extern struct wrapper_Data__object* impl_wrapper_User_fconnect_wrapper_Data(
  const char* url, sidl_bool ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern
void
impl_wrapper_User_accept(
  /* in */ wrapper_User self,
  /* in */ wrapper_Data data,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_wrapper_User_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
extern struct wrapper_Data__object* impl_wrapper_User_fconnect_wrapper_Data(
  const char* url, sidl_bool ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
