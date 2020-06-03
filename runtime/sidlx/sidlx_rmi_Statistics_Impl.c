/*
 * File:          sidlx_rmi_Statistics_Impl.c
 * Symbol:        sidlx.rmi.Statistics-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463 trunk)
 * Description:   Server-side implementation for sidlx.rmi.Statistics
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "sidlx.rmi.Statistics" (version 0.1)
 * 
 *  A read-only class of statistics 
 */

#include "sidlx_rmi_Statistics_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(sidlx.rmi.Statistics._includes) */
#include "sidlx_common.h"
#include "sidl_MemAllocException.h"
/* DO-NOT-DELETE splicer.end(sidlx.rmi.Statistics._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_Statistics__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_Statistics__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.Statistics._load) */
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.Statistics._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_Statistics__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_Statistics__ctor(
  /* in */ sidlx_rmi_Statistics self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.Statistics._ctor) */
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.Statistics._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_Statistics__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_Statistics__ctor2(
  /* in */ sidlx_rmi_Statistics self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.Statistics._ctor2) */
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.Statistics._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_Statistics__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_Statistics__dtor(
  /* in */ sidlx_rmi_Statistics self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.Statistics._dtor) */
    /* Insert-Code-Here {sidlx.rmi.Statistics._dtor} (destructor method) */
    /*
     * // boilerplate destructor
     * struct sidlx_rmi_Statistics__data *dptr = sidlx_rmi_Statistics__get_data(self);
     * if (dptr) {
     *   // free contained in dtor before next line
     *   free(dptr);
     *   sidlx_rmi_Statistics__set_data(self, NULL);
     * }
     */

    /* DO-NOT-DELETE splicer.end(sidlx.rmi.Statistics._dtor) */
  }
}

/*
 *  total number of acceptions that succeed on first try 
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_Statistics_getTotalAcceptsFirstTry"

#ifdef __cplusplus
extern "C"
#endif
int64_t
impl_sidlx_rmi_Statistics_getTotalAcceptsFirstTry(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.Statistics.getTotalAcceptsFirstTry) */
    return get_sidlx_stats_struct()->totalAcceptsFirstTry;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.Statistics.getTotalAcceptsFirstTry) */
  }
}

/*
 *  total number of acceptions requested 
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_Statistics_getTotalAcceptRequests"

#ifdef __cplusplus
extern "C"
#endif
int64_t
impl_sidlx_rmi_Statistics_getTotalAcceptRequests(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.Statistics.getTotalAcceptRequests) */
    return get_sidlx_stats_struct()->totalAcceptsRequested;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.Statistics.getTotalAcceptRequests) */
  }
}

/*
 *  total number of acceptions granted (regardless of retries) 
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_Statistics_getTotalAcceptSucceded"

#ifdef __cplusplus
extern "C"
#endif
int64_t
impl_sidlx_rmi_Statistics_getTotalAcceptSucceded(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.Statistics.getTotalAcceptSucceded) */
    return get_sidlx_stats_struct()->totalAcceptsGranted;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.Statistics.getTotalAcceptSucceded) */
  }
}

/*
 *  maximum number of retries needed to succeed 
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_Statistics_getMaxAcceptRetries"

#ifdef __cplusplus
extern "C"
#endif
int32_t
impl_sidlx_rmi_Statistics_getMaxAcceptRetries(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.Statistics.getMaxAcceptRetries) */
     return get_sidlx_stats_struct()->maxAcceptRetries;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.Statistics.getMaxAcceptRetries) */
  }
}

/*
 *  total number of retries /#successful accepts retried at least once 
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_Statistics_getAvgAcceptRetries"

#ifdef __cplusplus
extern "C"
#endif
double
impl_sidlx_rmi_Statistics_getAvgAcceptRetries(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.Statistics.getAvgAcceptRetries) */
    struct sidlx_stats * stats = get_sidlx_stats_struct(); 
    long nGrantedOnRetry = stats->totalAcceptsGranted - stats->totalAcceptsFirstTry;
    if ( nGrantedOnRetry ) { 
      return (double) stats->totalAcceptRetries / nGrantedOnRetry; 
    } else {
      return 0.0; 
    }
      
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.Statistics.getAvgAcceptRetries) */
  }
}

/*
 *  total number of connections that succeed on first try 
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_Statistics_getTotalConnectsFirstTry"

#ifdef __cplusplus
extern "C"
#endif
int64_t
impl_sidlx_rmi_Statistics_getTotalConnectsFirstTry(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.Statistics.getTotalConnectsFirstTry) */
    return get_sidlx_stats_struct()->totalConnectsFirstTry;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.Statistics.getTotalConnectsFirstTry) */
  }
}

/*
 *  total number of connections requested 
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_Statistics_getTotalConnectRequests"

#ifdef __cplusplus
extern "C"
#endif
int64_t
impl_sidlx_rmi_Statistics_getTotalConnectRequests(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.Statistics.getTotalConnectRequests) */
    return get_sidlx_stats_struct()->totalConnectsRequested;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.Statistics.getTotalConnectRequests) */
  }
}

/*
 *  total number of connections granted (regardless of retries) 
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_Statistics_getTotalConnectSucceded"

#ifdef __cplusplus
extern "C"
#endif
int64_t
impl_sidlx_rmi_Statistics_getTotalConnectSucceded(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.Statistics.getTotalConnectSucceded) */
    return get_sidlx_stats_struct()->totalConnectsGranted;

    /* DO-NOT-DELETE splicer.end(sidlx.rmi.Statistics.getTotalConnectSucceded) */
  }
}

/*
 *  maximum number of retries needed to succeed 
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_Statistics_getMaxConnectRetries"

#ifdef __cplusplus
extern "C"
#endif
int32_t
impl_sidlx_rmi_Statistics_getMaxConnectRetries(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.Statistics.getMaxConnectRetries) */
    return get_sidlx_stats_struct()->maxConnectRetries;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.Statistics.getMaxConnectRetries) */
  }
}

/*
 *  total number of retries /#successful connects retried at least once 
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_Statistics_getAvgConnectRetries"

#ifdef __cplusplus
extern "C"
#endif
double
impl_sidlx_rmi_Statistics_getAvgConnectRetries(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.Statistics.getAvgConnectRetries) */
    struct sidlx_stats * stats = get_sidlx_stats_struct(); 
    long nGrantedOnRetry = stats->totalConnectsGranted - stats->totalConnectsFirstTry;
    if ( nGrantedOnRetry ) { 
      return (double) stats->totalConnectRetries / nGrantedOnRetry; 
    } else {
      return 0.0; 
    }
      
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.Statistics.getAvgConnectRetries) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

