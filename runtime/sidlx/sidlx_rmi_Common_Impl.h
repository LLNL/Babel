/*
 * File:          sidlx_rmi_Common_Impl.h
 * Symbol:        sidlx.rmi.Common-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463 trunk)
 * Description:   Server-side implementation for sidlx.rmi.Common
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_sidlx_rmi_Common_Impl_h
#define included_sidlx_rmi_Common_Impl_h

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
#ifndef included_sidlx_rmi_Common_h
#include "sidlx_rmi_Common.h"
#endif
/* DO-NOT-DELETE splicer.begin(sidlx.rmi.Common._hincludes) */
/* insert implementation here: sidlx.rmi.Common._hincludes (include files) */
/* DO-NOT-DELETE splicer.end(sidlx.rmi.Common._hincludes) */

/*
 * Private data for class sidlx.rmi.Common
 */

struct sidlx_rmi_Common__data {
  /* DO-NOT-DELETE splicer.begin(sidlx.rmi.Common._data) */
  /* insert implementation here: sidlx.rmi.Common._data (private data members) */
  int ignore; /* dummy to force non-empty struct; remove if you add data */
  /* DO-NOT-DELETE splicer.end(sidlx.rmi.Common._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct sidlx_rmi_Common__data*
sidlx_rmi_Common__get_data(
  sidlx_rmi_Common);

extern void
sidlx_rmi_Common__set_data(
  sidlx_rmi_Common,
  struct sidlx_rmi_Common__data*);

extern
void
impl_sidlx_rmi_Common__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sidlx_rmi_Common__ctor(
  /* in */ sidlx_rmi_Common self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sidlx_rmi_Common__ctor2(
  /* in */ sidlx_rmi_Common self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sidlx_rmi_Common__dtor(
  /* in */ sidlx_rmi_Common self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

extern
int32_t
impl_sidlx_rmi_Common_fork(
  /* out */ sidl_BaseInterface *_ex);

extern
int32_t
impl_sidlx_rmi_Common_getHostIP(
  /* in */ const char* hostname,
  /* out */ sidl_BaseInterface *_ex);

extern
char*
impl_sidlx_rmi_Common_getCanonicalName(
  /* in */ const char* hostname,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_sidlx_rmi_Common_fconnect_sidl_BaseInterface(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_sidlx_rmi_Common_fconnect_sidl_BaseInterface(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
