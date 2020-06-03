/*
 * File:          Overload_AnException_Impl.c
 * Symbol:        Overload.AnException-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Overload.AnException
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "Overload.AnException" (version 1.0)
 * 
 * This exception is passed into the overloaded method as an example
 * of passing classes.
 */

#include "Overload_AnException_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(Overload.AnException._includes) */
#include "Overload.h"
#include "sidl_Exception.h"
/* DO-NOT-DELETE splicer.end(Overload.AnException._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_Overload_AnException__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Overload_AnException__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Overload.AnException._load) */
  /* Insert the implementation of the static class initializer method here... */
    /* DO-NOT-DELETE splicer.end(Overload.AnException._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_Overload_AnException__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Overload_AnException__ctor(
  /* in */ Overload_AnException self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Overload.AnException._ctor) */
  Overload_AnException_setNote(self, "AnException",_ex); SIDL_REPORT(*_ex);
 EXIT:
  ;
    /* DO-NOT-DELETE splicer.end(Overload.AnException._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_Overload_AnException__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Overload_AnException__ctor2(
  /* in */ Overload_AnException self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Overload.AnException._ctor2) */
  /* Insert-Code-Here {Overload.AnException._ctor2} (special constructor method) */
    /* DO-NOT-DELETE splicer.end(Overload.AnException._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_Overload_AnException__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Overload_AnException__dtor(
  /* in */ Overload_AnException self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Overload.AnException._dtor) */
  /* Insert the implementation of the destructor method here... */
    /* DO-NOT-DELETE splicer.end(Overload.AnException._dtor) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

