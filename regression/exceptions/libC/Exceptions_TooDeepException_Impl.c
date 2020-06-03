/*
 * File:          Exceptions_TooDeepException_Impl.c
 * Symbol:        Exceptions.TooDeepException-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Exceptions.TooDeepException
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "Exceptions.TooDeepException" (version 1.0)
 * 
 * This exception is thrown if the Fibonacci recursion is too deep.
 */

#include "Exceptions_TooDeepException_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(Exceptions.TooDeepException._includes) */
/* Put additional includes or other arbitrary code here... */
/* DO-NOT-DELETE splicer.end(Exceptions.TooDeepException._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_Exceptions_TooDeepException__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Exceptions_TooDeepException__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Exceptions.TooDeepException._load) */
  /* Insert the implementation of the static class initializer method here... */
    /* DO-NOT-DELETE splicer.end(Exceptions.TooDeepException._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_Exceptions_TooDeepException__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Exceptions_TooDeepException__ctor(
  /* in */ Exceptions_TooDeepException self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Exceptions.TooDeepException._ctor) */
  /* Insert the implementation of the constructor method here... */
    /* DO-NOT-DELETE splicer.end(Exceptions.TooDeepException._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_Exceptions_TooDeepException__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Exceptions_TooDeepException__ctor2(
  /* in */ Exceptions_TooDeepException self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Exceptions.TooDeepException._ctor2) */
  /* Insert-Code-Here {Exceptions.TooDeepException._ctor2} (special constructor method) */
    /* DO-NOT-DELETE splicer.end(Exceptions.TooDeepException._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_Exceptions_TooDeepException__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Exceptions_TooDeepException__dtor(
  /* in */ Exceptions_TooDeepException self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Exceptions.TooDeepException._dtor) */
  /* Insert the implementation of the destructor method here... */
    /* DO-NOT-DELETE splicer.end(Exceptions.TooDeepException._dtor) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

