/*
 * File:          Inherit_D_Impl.c
 * Symbol:        Inherit.D-v1.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Inherit.D
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "Inherit.D" (version 1.1)
 */

#include "Inherit_D_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(Inherit.D._includes) */
#include <stdlib.h>
#include "sidl_String.h"
/* DO-NOT-DELETE splicer.end(Inherit.D._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_D__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Inherit_D__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.D._load) */
  /* Insert the implementation of the static class initializer method here... */
    /* DO-NOT-DELETE splicer.end(Inherit.D._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_D__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Inherit_D__ctor(
  /* in */ Inherit_D self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.D._ctor) */
  /* Insert the implementation of the constructor method here... */
    /* DO-NOT-DELETE splicer.end(Inherit.D._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_D__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Inherit_D__ctor2(
  /* in */ Inherit_D self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.D._ctor2) */
  /* Insert-Code-Here {Inherit.D._ctor2} (special constructor method) */
    /* DO-NOT-DELETE splicer.end(Inherit.D._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_D__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Inherit_D__dtor(
  /* in */ Inherit_D self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.D._dtor) */
  /* Insert the implementation of the constructor method here... */
    /* DO-NOT-DELETE splicer.end(Inherit.D._dtor) */
  }
}

/*
 * Method:  a[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_D_a"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_Inherit_D_a(
  /* in */ Inherit_D self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.D.a) */
  return sidl_String_strdup("D.a");
    /* DO-NOT-DELETE splicer.end(Inherit.D.a) */
  }
}

/*
 * Method:  d[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_D_d"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_Inherit_D_d(
  /* in */ Inherit_D self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.D.d) */
  return sidl_String_strdup("D.d");
    /* DO-NOT-DELETE splicer.end(Inherit.D.d) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

