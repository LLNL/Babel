/*
 * File:          Inherit_F_Impl.c
 * Symbol:        Inherit.F-v1.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Inherit.F
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "Inherit.F" (version 1.1)
 */

#include "Inherit_F_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(Inherit.F._includes) */
#include <stdlib.h>
#include "sidl_String.h"
/* DO-NOT-DELETE splicer.end(Inherit.F._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_F__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Inherit_F__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.F._load) */
  /* Insert the implementation of the static class initializer method here... */
    /* DO-NOT-DELETE splicer.end(Inherit.F._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_F__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Inherit_F__ctor(
  /* in */ Inherit_F self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.F._ctor) */
  /* Insert the implementation of the constructor method here... */
    /* DO-NOT-DELETE splicer.end(Inherit.F._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_F__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Inherit_F__ctor2(
  /* in */ Inherit_F self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.F._ctor2) */
  /* Insert-Code-Here {Inherit.F._ctor2} (special constructor method) */
    /* DO-NOT-DELETE splicer.end(Inherit.F._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_F__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Inherit_F__dtor(
  /* in */ Inherit_F self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.F._dtor) */
  /* Insert the implementation of the constructor method here... */
    /* DO-NOT-DELETE splicer.end(Inherit.F._dtor) */
  }
}

/*
 * Method:  f[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_F_f"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_Inherit_F_f(
  /* in */ Inherit_F self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.F.f) */
  return sidl_String_strdup("F.f");
    /* DO-NOT-DELETE splicer.end(Inherit.F.f) */
  }
}

/*
 * Method:  a[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_F_a"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_Inherit_F_a(
  /* in */ Inherit_F self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.F.a) */
  return sidl_String_strdup("F.a");
    /* DO-NOT-DELETE splicer.end(Inherit.F.a) */
  }
}

/*
 * Method:  b[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_F_b"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_Inherit_F_b(
  /* in */ Inherit_F self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.F.b) */
  return sidl_String_strdup("F.b");
    /* DO-NOT-DELETE splicer.end(Inherit.F.b) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

