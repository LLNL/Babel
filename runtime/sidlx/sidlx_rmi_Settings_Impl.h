/*
 * File:          sidlx_rmi_Settings_Impl.h
 * Symbol:        sidlx.rmi.Settings-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463 trunk)
 * Description:   Server-side implementation for sidlx.rmi.Settings
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_sidlx_rmi_Settings_Impl_h
#define included_sidlx_rmi_Settings_Impl_h

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
#ifndef included_sidlx_rmi_Settings_h
#include "sidlx_rmi_Settings.h"
#endif
/* DO-NOT-DELETE splicer.begin(sidlx.rmi.Settings._hincludes) */
/* Insert-Code-Here {sidlx.rmi.Settings._hincludes} (include files) */
/* DO-NOT-DELETE splicer.end(sidlx.rmi.Settings._hincludes) */

/*
 * Private data for class sidlx.rmi.Settings
 */

struct sidlx_rmi_Settings__data {
  /* DO-NOT-DELETE splicer.begin(sidlx.rmi.Settings._data) */
  /* Insert-Code-Here {sidlx.rmi.Settings._data} (private data members) */
  int ignore; /* dummy to force non-empty struct; remove if you add data */
  /* DO-NOT-DELETE splicer.end(sidlx.rmi.Settings._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct sidlx_rmi_Settings__data*
sidlx_rmi_Settings__get_data(
  sidlx_rmi_Settings);

extern void
sidlx_rmi_Settings__set_data(
  sidlx_rmi_Settings,
  struct sidlx_rmi_Settings__data*);

extern
void
impl_sidlx_rmi_Settings__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sidlx_rmi_Settings__ctor(
  /* in */ sidlx_rmi_Settings self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sidlx_rmi_Settings__ctor2(
  /* in */ sidlx_rmi_Settings self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sidlx_rmi_Settings__dtor(
  /* in */ sidlx_rmi_Settings self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

extern
int32_t
impl_sidlx_rmi_Settings_getMaxAcceptRetries(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sidlx_rmi_Settings_setMaxAcceptRetries(
  /* in */ int32_t retries,
  /* out */ sidl_BaseInterface *_ex);

extern
int64_t
impl_sidlx_rmi_Settings_getAcceptRetryInitialSleep(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sidlx_rmi_Settings_setAcceptRetryInitialSleep(
  /* in */ int64_t usecs,
  /* out */ sidl_BaseInterface *_ex);

extern
int32_t
impl_sidlx_rmi_Settings_getMaxConnectRetries(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sidlx_rmi_Settings_setMaxConnectRetries(
  /* in */ int32_t retries,
  /* out */ sidl_BaseInterface *_ex);

extern
int64_t
impl_sidlx_rmi_Settings_getConnectRetryInitialSleep(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sidlx_rmi_Settings_setConnectRetryInitialSleep(
  /* in */ int64_t usecs,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_sidlx_rmi_Settings_fconnect_sidl_BaseInterface(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_sidlx_rmi_Settings_fconnect_sidl_BaseInterface(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
