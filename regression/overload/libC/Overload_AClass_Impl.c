/*
 * File:          Overload_AClass_Impl.c
 * Symbol:        Overload.AClass-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Overload.AClass
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "Overload.AClass" (version 1.0)
 * 
 * This class is passed into the overloaded method as an example
 * of passing classes.
 */

#include "Overload_AClass_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(Overload.AClass._includes) */
/* Put additional includes or other arbitrary code here... */
/* DO-NOT-DELETE splicer.end(Overload.AClass._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_Overload_AClass__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Overload_AClass__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Overload.AClass._load) */
  /* Insert the implementation of the static class initializer method here... */
    /* DO-NOT-DELETE splicer.end(Overload.AClass._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_Overload_AClass__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Overload_AClass__ctor(
  /* in */ Overload_AClass self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Overload.AClass._ctor) */
  /* Insert the implementation of the constructor method here... */
    /* DO-NOT-DELETE splicer.end(Overload.AClass._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_Overload_AClass__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Overload_AClass__ctor2(
  /* in */ Overload_AClass self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Overload.AClass._ctor2) */
  /* Insert-Code-Here {Overload.AClass._ctor2} (special constructor method) */
    /* DO-NOT-DELETE splicer.end(Overload.AClass._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_Overload_AClass__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Overload_AClass__dtor(
  /* in */ Overload_AClass self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Overload.AClass._dtor) */
  /* Insert the implementation of the destructor method here... */
    /* DO-NOT-DELETE splicer.end(Overload.AClass._dtor) */
  }
}

/*
 * Method:  getValue[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Overload_AClass_getValue"

#ifdef __cplusplus
extern "C"
#endif
int32_t
impl_Overload_AClass_getValue(
  /* in */ Overload_AClass self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Overload.AClass.getValue) */
  return 2;
    /* DO-NOT-DELETE splicer.end(Overload.AClass.getValue) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

