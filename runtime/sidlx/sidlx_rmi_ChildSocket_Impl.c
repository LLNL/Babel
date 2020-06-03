/*
 * File:          sidlx_rmi_ChildSocket_Impl.c
 * Symbol:        sidlx.rmi.ChildSocket-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463 trunk)
 * Description:   Server-side implementation for sidlx.rmi.ChildSocket
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "sidlx.rmi.ChildSocket" (version 0.1)
 * 
 * Simple socket passed back by accept
 */

#include "sidlx_rmi_ChildSocket_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(sidlx.rmi.ChildSocket._includes) */
/* DO-NOT-DELETE splicer.end(sidlx.rmi.ChildSocket._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_ChildSocket__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_ChildSocket__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.ChildSocket._load) */
  /* insert implementation here: sidlx.rmi.ChildSocket._load (static class initializer method) */
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.ChildSocket._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_ChildSocket__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_ChildSocket__ctor(
  /* in */ sidlx_rmi_ChildSocket self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.ChildSocket._ctor) */
  /* insert implementation here: sidlx.rmi.ChildSocket._ctor (constructor method) */
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.ChildSocket._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_ChildSocket__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_ChildSocket__ctor2(
  /* in */ sidlx_rmi_ChildSocket self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.ChildSocket._ctor2) */
  /* Insert-Code-Here {sidlx.rmi.ChildSocket._ctor2} (special constructor method) */
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.ChildSocket._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_ChildSocket__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_ChildSocket__dtor(
  /* in */ sidlx_rmi_ChildSocket self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.ChildSocket._dtor) */
  /* insert implementation here: sidlx.rmi.ChildSocket._dtor (destructor method) */
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.ChildSocket._dtor) */
  }
}

/*
 * Method:  init[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_ChildSocket_init"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_ChildSocket_init(
  /* in */ sidlx_rmi_ChildSocket self,
  /* in */ int32_t fileDes,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.ChildSocket.init) */
    sidlx_rmi_ChildSocket_setFileDescriptor(self, fileDes, _ex);
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.ChildSocket.init) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

