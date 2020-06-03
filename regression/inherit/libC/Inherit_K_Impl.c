/*
 * File:          Inherit_K_Impl.c
 * Symbol:        Inherit.K-v1.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Inherit.K
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "Inherit.K" (version 1.1)
 */

#include "Inherit_K_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(Inherit.K._includes) */
#include "sidl_String.h"
/* DO-NOT-DELETE splicer.end(Inherit.K._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_K__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Inherit_K__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.K._load) */
  /* Insert-Code-Here {Inherit.K._load} (static class initializer method) */
    /* DO-NOT-DELETE splicer.end(Inherit.K._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_K__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Inherit_K__ctor(
  /* in */ Inherit_K self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.K._ctor) */
  /* Insert-Code-Here {Inherit.K._ctor} (constructor method) */
    /* DO-NOT-DELETE splicer.end(Inherit.K._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_K__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Inherit_K__ctor2(
  /* in */ Inherit_K self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.K._ctor2) */
  /* Insert-Code-Here {Inherit.K._ctor2} (special constructor method) */
    /* DO-NOT-DELETE splicer.end(Inherit.K._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_K__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Inherit_K__dtor(
  /* in */ Inherit_K self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.K._dtor) */
  /* Insert-Code-Here {Inherit.K._dtor} (destructor method) */
    /* DO-NOT-DELETE splicer.end(Inherit.K._dtor) */
  }
}

/*
 * Method:  a[2]
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_K_a2"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_Inherit_K_a2(
  /* in */ Inherit_K self,
  /* in */ int32_t i,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.K.a2) */
  return sidl_String_strdup("K.a2");
    /* DO-NOT-DELETE splicer.end(Inherit.K.a2) */
  }
}

/*
 * Method:  a[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_K_a"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_Inherit_K_a(
  /* in */ Inherit_K self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.K.a) */
  return sidl_String_strdup("K.a");
    /* DO-NOT-DELETE splicer.end(Inherit.K.a) */
  }
}

/*
 * Method:  h[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_K_h"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_Inherit_K_h(
  /* in */ Inherit_K self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.K.h) */
  return sidl_String_strdup("K.h");
    /* DO-NOT-DELETE splicer.end(Inherit.K.h) */
  }
}

/*
 * Method:  k[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_K_k"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_Inherit_K_k(
  /* in */ Inherit_K self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.K.k) */
   return sidl_String_strdup("K.k");
    /* DO-NOT-DELETE splicer.end(Inherit.K.k) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

