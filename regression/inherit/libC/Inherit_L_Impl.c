/*
 * File:          Inherit_L_Impl.c
 * Symbol:        Inherit.L-v1.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Inherit.L
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "Inherit.L" (version 1.1)
 */

#include "Inherit_L_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(Inherit.L._includes) */
#include "sidl_String.h"
/* DO-NOT-DELETE splicer.end(Inherit.L._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_L__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Inherit_L__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.L._load) */
  /* Insert-Code-Here {Inherit.L._load} (static class initializer method) */
    /* DO-NOT-DELETE splicer.end(Inherit.L._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_L__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Inherit_L__ctor(
  /* in */ Inherit_L self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.L._ctor) */
  /* Insert-Code-Here {Inherit.L._ctor} (constructor method) */
    /* DO-NOT-DELETE splicer.end(Inherit.L._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_L__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Inherit_L__ctor2(
  /* in */ Inherit_L self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.L._ctor2) */
  /* Insert-Code-Here {Inherit.L._ctor2} (special constructor method) */
    /* DO-NOT-DELETE splicer.end(Inherit.L._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_L__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Inherit_L__dtor(
  /* in */ Inherit_L self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.L._dtor) */
  /* Insert-Code-Here {Inherit.L._dtor} (destructor method) */
    /* DO-NOT-DELETE splicer.end(Inherit.L._dtor) */
  }
}

/*
 * Method:  a[a]
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_L_aa"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_Inherit_L_aa(
  /* in */ Inherit_L self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.L.aa) */
  return sidl_String_strdup("L.a");
    /* DO-NOT-DELETE splicer.end(Inherit.L.aa) */
  }
}

/*
 * Method:  a[2]
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_L_a2"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_Inherit_L_a2(
  /* in */ Inherit_L self,
  /* in */ int32_t i,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.L.a2) */
  return sidl_String_strdup("L.a2");
    /* DO-NOT-DELETE splicer.end(Inherit.L.a2) */
  }
}

/*
 * Method:  l[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_L_l"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_Inherit_L_l(
  /* in */ Inherit_L self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.L.l) */
  return sidl_String_strdup("L.l");
    /* DO-NOT-DELETE splicer.end(Inherit.L.l) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

