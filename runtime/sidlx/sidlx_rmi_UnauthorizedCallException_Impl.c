/*
 * File:          sidlx_rmi_UnauthorizedCallException_Impl.c
 * Symbol:        sidlx.rmi.UnauthorizedCallException-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463 trunk)
 * Description:   Server-side implementation for sidlx.rmi.UnauthorizedCallException
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "sidlx.rmi.UnauthorizedCallException" (version 0.1)
 * 
 *  A call was received that cannot be authenticaed. 
 */

#include "sidlx_rmi_UnauthorizedCallException_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(sidlx.rmi.UnauthorizedCallException._includes) */
/* insert code here (includes and arbitrary code) */
/* DO-NOT-DELETE splicer.end(sidlx.rmi.UnauthorizedCallException._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_UnauthorizedCallException__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_UnauthorizedCallException__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.UnauthorizedCallException._load) */
    /* insert code here (static class initializer) */
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.UnauthorizedCallException._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_UnauthorizedCallException__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_UnauthorizedCallException__ctor(
  /* in */ sidlx_rmi_UnauthorizedCallException self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.UnauthorizedCallException._ctor) */
    /*
     * // boilerplate constructor
     * struct sidlx_rmi_UnauthorizedCallException__data *dptr = (struct sidlx_rmi_UnauthorizedCallException__data*)malloc(sizeof(struct sidlx_rmi_UnauthorizedCallException__data));
     * if (dptr) {
     *   memset(dptr, 0, sizeof(struct sidlx_rmi_UnauthorizedCallException__data));
     *   // initialize elements of dptr here
     * sidlx_rmi_UnauthorizedCallException__set_data(self, dptr);
     * } else {
     *   sidl_MemAllocException ex = sidl_MemAllocException_getSingletonException(_ex);
     *   SIDL_CHECK(*_ex);
     *   sidl_MemAllocException_setNote(ex, "Out of memory.", _ex); SIDL_CHECK(*_ex);
     *   sidl_MemAllocException_add(ex, __FILE__, __LINE__, "sidlx.rmi.UnauthorizedCallException._ctor", _ex);
     *   SIDL_CHECK(*_ex);
     *   *_ex = (sidl_BaseInterface)ex;
     * }
     * EXIT:;
     */

    /* DO-NOT-DELETE splicer.end(sidlx.rmi.UnauthorizedCallException._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_UnauthorizedCallException__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_UnauthorizedCallException__ctor2(
  /* in */ sidlx_rmi_UnauthorizedCallException self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.UnauthorizedCallException._ctor2) */
    /* insert code here (special constructor) */
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.UnauthorizedCallException._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_UnauthorizedCallException__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_UnauthorizedCallException__dtor(
  /* in */ sidlx_rmi_UnauthorizedCallException self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.UnauthorizedCallException._dtor) */
    /*
     * // boilerplate destructor
     * struct sidlx_rmi_UnauthorizedCallException__data *dptr = sidlx_rmi_UnauthorizedCallException__get_data(self);
     * if (dptr) {
     *   // free contained in dtor before next line
     *   free(dptr);
     *   sidlx_rmi_UnauthorizedCallException__set_data(self, NULL);
     * }
     */

    /* DO-NOT-DELETE splicer.end(sidlx.rmi.UnauthorizedCallException._dtor) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* insert code here (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

