/*
 * File:          sidlx_rmi_Settings_Impl.c
 * Symbol:        sidlx.rmi.Settings-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463 trunk)
 * Description:   Server-side implementation for sidlx.rmi.Settings
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "sidlx.rmi.Settings" (version 0.1)
 * 
 * Low level settings
 */

#include "sidlx_rmi_Settings_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(sidlx.rmi.Settings._includes) */
#include <stdlib.h>
#include <limits.h>
#include "sidl_MemAllocException.h"

static long s_accept_retries = 0;
static unsigned long s_accept_initial_sleep_time = 1024;
static long s_connect_retries = 0;
static unsigned long s_connect_initial_sleep_time = 1024;
/* DO-NOT-DELETE splicer.end(sidlx.rmi.Settings._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_Settings__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_Settings__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.Settings._load) */
    char * envval; 
    if (((envval=getenv("SIDLX_ACCEPT_MAX_RETRIES"))!=NULL)  
	&& (s_accept_retries=strtol(envval,NULL,10))){ 
      /* accept_retries is set... but it may be an strtol() error code */
      if ( s_accept_retries == LONG_MAX || s_accept_retries == LONG_MIN ) { 
      /* these are error flags for strtol, conversion failed so reset to zero */
	s_accept_retries = 0;
      }
    }

    if (((envval=getenv("SIDLX_ACCEPT_RETRY_INITIAL_SLEEP_USECS"))!=NULL)  
	&& (s_accept_initial_sleep_time=strtol(envval,NULL,10))){ 
      /* accept_retries is set... but it may be an strtol() error code */
      if ( s_accept_initial_sleep_time == LONG_MAX || 
           s_accept_initial_sleep_time == LONG_MIN ) { 
        /* these are error flags for strtol, conversion failed so reset to zero */
	s_accept_initial_sleep_time = 1024;
      }
    }

    if (((envval=getenv("SIDLX_CONNECT_MAX_RETRIES"))!=NULL)  
	&& (s_connect_retries=strtol(envval,NULL,10))){ 
      /* connect_retries is set... but it may be an strtol() error code */
      if ( s_connect_retries == LONG_MAX || s_connect_retries == LONG_MIN ) { 
      /* these are error flags for strtol, conversion failed so reset to zero */
	s_connect_retries = 0;
      }
    }

    if (((envval=getenv("SIDLX_CONNECT_RETRY_INITIAL_SLEEP_USECS"))!=NULL)  
	&& (s_connect_initial_sleep_time=strtol(envval,NULL,10))){ 
      /* connect_retries is set... but it may be an strtol() error code */
      if ( s_connect_initial_sleep_time == LONG_MAX || 
           s_connect_initial_sleep_time == LONG_MIN ) { 
      /* these are error flags for strtol, conversion failed so reset to zero */
	s_connect_initial_sleep_time = 1024;
      }
    }

    /* DO-NOT-DELETE splicer.end(sidlx.rmi.Settings._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_Settings__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_Settings__ctor(
  /* in */ sidlx_rmi_Settings self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.Settings._ctor) */
    /* Insert-Code-Here {sidlx.rmi.Settings._ctor} (constructor method) */
    /*
     * // boilerplate constructor
     * struct sidlx_rmi_Settings__data *dptr = (struct sidlx_rmi_Settings__data*)malloc(sizeof(struct sidlx_rmi_Settings__data));
     * if (dptr) {
     *   memset(dptr, 0, sizeof(struct sidlx_rmi_Settings__data));
     *   // initialize elements of dptr here
     * }
     * sidlx_rmi_Settings__set_data(self, dptr);
     */

    /* DO-NOT-DELETE splicer.end(sidlx.rmi.Settings._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_Settings__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_Settings__ctor2(
  /* in */ sidlx_rmi_Settings self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.Settings._ctor2) */
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.Settings._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_Settings__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_Settings__dtor(
  /* in */ sidlx_rmi_Settings self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.Settings._dtor) */
    /* Insert-Code-Here {sidlx.rmi.Settings._dtor} (destructor method) */
    /*
     * // boilerplate destructor
     * struct sidlx_rmi_Settings__data *dptr = sidlx_rmi_Settings__get_data(self);
     * if (dptr) {
     *   // free contained in dtor before next line
     *   free(dptr);
     *   sidlx_rmi_Settings__set_data(self, NULL);
     * }
     */

    /* DO-NOT-DELETE splicer.end(sidlx.rmi.Settings._dtor) */
  }
}

/*
 *  default = getenv(SIDLX_ACCEPT_MAX_RETRIES) else 0 
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_Settings_getMaxAcceptRetries"

#ifdef __cplusplus
extern "C"
#endif
int32_t
impl_sidlx_rmi_Settings_getMaxAcceptRetries(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.Settings.getMaxAcceptRetries) */
    return (int32_t)s_connect_retries;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.Settings.getMaxAcceptRetries) */
  }
}

/*
 *  override default 
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_Settings_setMaxAcceptRetries"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_Settings_setMaxAcceptRetries(
  /* in */ int32_t retries,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.Settings.setMaxAcceptRetries) */
     s_connect_retries = (long)retries;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.Settings.setMaxAcceptRetries) */
  }
}

/*
 *  default = getenv(SIDLX_ACCEPT_RETRY_INITIAL_SLEEP_USECS) else 1024 
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_Settings_getAcceptRetryInitialSleep"

#ifdef __cplusplus
extern "C"
#endif
int64_t
impl_sidlx_rmi_Settings_getAcceptRetryInitialSleep(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.Settings.getAcceptRetryInitialSleep) */
    return s_accept_initial_sleep_time;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.Settings.getAcceptRetryInitialSleep) */
  }
}

/*
 *  overrride default 
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_Settings_setAcceptRetryInitialSleep"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_Settings_setAcceptRetryInitialSleep(
  /* in */ int64_t usecs,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.Settings.setAcceptRetryInitialSleep) */
    s_accept_initial_sleep_time = usecs;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.Settings.setAcceptRetryInitialSleep) */
  }
}

/*
 *  default = getenv(SIDLX_CONNECT_MAX_RETRIES) else 0 
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_Settings_getMaxConnectRetries"

#ifdef __cplusplus
extern "C"
#endif
int32_t
impl_sidlx_rmi_Settings_getMaxConnectRetries(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.Settings.getMaxConnectRetries) */
    return (int32_t)s_connect_retries;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.Settings.getMaxConnectRetries) */
  }
}

/*
 *  override default 
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_Settings_setMaxConnectRetries"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_Settings_setMaxConnectRetries(
  /* in */ int32_t retries,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.Settings.setMaxConnectRetries) */
    s_connect_retries = (long)retries;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.Settings.setMaxConnectRetries) */
  }
}

/*
 *  default = getenv(SIDLX_CONNECT_RETRY_INITIAL_SLEEP_USECS) else 1024 
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_Settings_getConnectRetryInitialSleep"

#ifdef __cplusplus
extern "C"
#endif
int64_t
impl_sidlx_rmi_Settings_getConnectRetryInitialSleep(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.Settings.getConnectRetryInitialSleep) */
    return s_connect_initial_sleep_time;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.Settings.getConnectRetryInitialSleep) */
  }
}

/*
 *  overrride default 
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_Settings_setConnectRetryInitialSleep"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_Settings_setConnectRetryInitialSleep(
  /* in */ int64_t usecs,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.Settings.setConnectRetryInitialSleep) */
    s_connect_initial_sleep_time = usecs;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.Settings.setConnectRetryInitialSleep) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

