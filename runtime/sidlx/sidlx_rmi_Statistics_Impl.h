/*
 * File:          sidlx_rmi_Statistics_Impl.h
 * Symbol:        sidlx.rmi.Statistics-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463 trunk)
 * Description:   Server-side implementation for sidlx.rmi.Statistics
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_sidlx_rmi_Statistics_Impl_h
#define included_sidlx_rmi_Statistics_Impl_h

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
#ifndef included_sidlx_rmi_Statistics_h
#include "sidlx_rmi_Statistics.h"
#endif
/* DO-NOT-DELETE splicer.begin(sidlx.rmi.Statistics._hincludes) */
/* Insert-Code-Here {sidlx.rmi.Statistics._hincludes} (include files) */
/* DO-NOT-DELETE splicer.end(sidlx.rmi.Statistics._hincludes) */

/*
 * Private data for class sidlx.rmi.Statistics
 */

struct sidlx_rmi_Statistics__data {
  /* DO-NOT-DELETE splicer.begin(sidlx.rmi.Statistics._data) */
  /* Insert-Code-Here {sidlx.rmi.Statistics._data} (private data members) */
  int ignore; /* dummy to force non-empty struct; remove if you add data */
  /* DO-NOT-DELETE splicer.end(sidlx.rmi.Statistics._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct sidlx_rmi_Statistics__data*
sidlx_rmi_Statistics__get_data(
  sidlx_rmi_Statistics);

extern void
sidlx_rmi_Statistics__set_data(
  sidlx_rmi_Statistics,
  struct sidlx_rmi_Statistics__data*);

extern
void
impl_sidlx_rmi_Statistics__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sidlx_rmi_Statistics__ctor(
  /* in */ sidlx_rmi_Statistics self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sidlx_rmi_Statistics__ctor2(
  /* in */ sidlx_rmi_Statistics self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sidlx_rmi_Statistics__dtor(
  /* in */ sidlx_rmi_Statistics self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

extern
int64_t
impl_sidlx_rmi_Statistics_getTotalAcceptsFirstTry(
  /* out */ sidl_BaseInterface *_ex);

extern
int64_t
impl_sidlx_rmi_Statistics_getTotalAcceptRequests(
  /* out */ sidl_BaseInterface *_ex);

extern
int64_t
impl_sidlx_rmi_Statistics_getTotalAcceptSucceded(
  /* out */ sidl_BaseInterface *_ex);

extern
int32_t
impl_sidlx_rmi_Statistics_getMaxAcceptRetries(
  /* out */ sidl_BaseInterface *_ex);

extern
double
impl_sidlx_rmi_Statistics_getAvgAcceptRetries(
  /* out */ sidl_BaseInterface *_ex);

extern
int64_t
impl_sidlx_rmi_Statistics_getTotalConnectsFirstTry(
  /* out */ sidl_BaseInterface *_ex);

extern
int64_t
impl_sidlx_rmi_Statistics_getTotalConnectRequests(
  /* out */ sidl_BaseInterface *_ex);

extern
int64_t
impl_sidlx_rmi_Statistics_getTotalConnectSucceded(
  /* out */ sidl_BaseInterface *_ex);

extern
int32_t
impl_sidlx_rmi_Statistics_getMaxConnectRetries(
  /* out */ sidl_BaseInterface *_ex);

extern
double
impl_sidlx_rmi_Statistics_getAvgConnectRetries(
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_sidlx_rmi_Statistics_fconnect_sidl_BaseInterface(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_sidlx_rmi_Statistics_fconnect_sidl_BaseInterface(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
