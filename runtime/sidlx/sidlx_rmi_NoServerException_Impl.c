/*
 * File:          sidlx_rmi_NoServerException_Impl.c
 * Symbol:        sidlx.rmi.NoServerException-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463 trunk)
 * Description:   Server-side implementation for sidlx.rmi.NoServerException
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "sidlx.rmi.NoServerException" (version 0.1)
 * 
 * No Server Exception: No server has been initilizaed in the Server Registry
 */

#include "sidlx_rmi_NoServerException_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(sidlx.rmi.NoServerException._includes) */
/* Insert-Code-Here {sidlx.rmi.NoServerException._includes} (includes and 
  arbitrary code) */
/* DO-NOT-DELETE splicer.end(sidlx.rmi.NoServerException._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_NoServerException__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_NoServerException__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.NoServerException._load) */

    /* DO-NOT-DELETE splicer.end(sidlx.rmi.NoServerException._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_NoServerException__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_NoServerException__ctor(
  /* in */ sidlx_rmi_NoServerException self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.NoServerException._ctor) */
    /* Insert-Code-Here {sidlx.rmi.NoServerException._ctor} (constructor 
      method) */
    /*
     * // boilerplate constructor
     * struct sidlx_rmi_NoServerException__data *dptr = (struct sidlx_rmi_NoServerException__data*)malloc(sizeof(struct sidlx_rmi_NoServerException__data));
     * if (dptr) {
     *   memset(dptr, 0, sizeof(struct sidlx_rmi_NoServerException__data));
     *   // initialize elements of dptr here
     * sidlx_rmi_NoServerException__set_data(self, dptr);
     * } else {
     *   sidl_MemAllocException ex = sidl_MemAllocException_getSingletonException(_ex);
     *   SIDL_CHECK(*_ex);
     *   sidl_MemAllocException_setNote(ex, "Out of memory.", _ex); SIDL_CHECK(*_ex);
     *   sidl_MemAllocException_add(ex, __FILE__, __LINE__, "sidlx.rmi.NoServerException._ctor", _ex);
     *   SIDL_CHECK(*_ex);
     *   *_ex = (sidl_BaseInterface)ex;
     * }
     * EXIT:;
     */

    /* DO-NOT-DELETE splicer.end(sidlx.rmi.NoServerException._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_NoServerException__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_NoServerException__ctor2(
  /* in */ sidlx_rmi_NoServerException self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.NoServerException._ctor2) */

    /* DO-NOT-DELETE splicer.end(sidlx.rmi.NoServerException._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_NoServerException__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_NoServerException__dtor(
  /* in */ sidlx_rmi_NoServerException self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.NoServerException._dtor) */
    /* Insert-Code-Here {sidlx.rmi.NoServerException._dtor} (destructor method) 
      */
    /*
     * // boilerplate destructor
     * struct sidlx_rmi_NoServerException__data *dptr = sidlx_rmi_NoServerException__get_data(self);
     * if (dptr) {
     *   // free contained in dtor before next line
     *   free(dptr);
     *   sidlx_rmi_NoServerException__set_data(self, NULL);
     * }
     */

    /* DO-NOT-DELETE splicer.end(sidlx.rmi.NoServerException._dtor) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

