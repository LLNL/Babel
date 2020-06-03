/*
 * File:          c_BoundsException_Impl.c
 * Symbol:        c.BoundsException-v2.0
 * Symbol Type:   class
 * Babel Version: 1.1.1
 * Description:   Server-side implementation for c.BoundsException
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "c.BoundsException" (version 2.0)
 */

#include "c_BoundsException_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(c.BoundsException._includes) */
/* Put additional includes or other arbitrary code here... */
/* DO-NOT-DELETE splicer.end(c.BoundsException._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_c_BoundsException__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_c_BoundsException__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(c.BoundsException._load) */
  /* Insert-Code-Here {c.BoundsException._load} (static class initializer method) */
    /* DO-NOT-DELETE splicer.end(c.BoundsException._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_c_BoundsException__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_c_BoundsException__ctor(
  /* in */ c_BoundsException self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(c.BoundsException._ctor) */
  /* Insert the implementation of the constructor method here... */
    /* DO-NOT-DELETE splicer.end(c.BoundsException._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_c_BoundsException__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_c_BoundsException__ctor2(
  /* in */ c_BoundsException self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(c.BoundsException._ctor2) */
  /* Insert-Code-Here {c.BoundsException._ctor2} (special constructor method) */
  /*
   * This method has not been implemented
   */

    SIDL_THROW(*_ex, sidl_NotImplementedException,     "This method has not been implemented");
  EXIT:;
    /* DO-NOT-DELETE splicer.end(c.BoundsException._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_c_BoundsException__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_c_BoundsException__dtor(
  /* in */ c_BoundsException self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(c.BoundsException._dtor) */
  /* Insert the implementation of the destructor method here... */
    /* DO-NOT-DELETE splicer.end(c.BoundsException._dtor) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

