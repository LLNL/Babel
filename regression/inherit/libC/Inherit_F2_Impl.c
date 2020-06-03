/*
 * File:          Inherit_F2_Impl.c
 * Symbol:        Inherit.F2-v1.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Inherit.F2
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "Inherit.F2" (version 1.1)
 */

#include "Inherit_F2_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(Inherit.F2._includes) */
#include <stdlib.h>
#include "sidl_String.h"
/* DO-NOT-DELETE splicer.end(Inherit.F2._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
static const struct Inherit_C__epv* superEPV = NULL;

void Inherit_F2__superEPV(
struct Inherit_C__epv* parentEPV){
  superEPV = parentEPV;
}
/*
 * Method:  c[]
 */

static char*
super_c(
  /* in */ Inherit_F2 self,
  /* out */ sidl_BaseInterface *_ex)
{
  return (*superEPV->f_c)((struct Inherit_C__object*)
    self,
    _ex);
}

/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_F2__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Inherit_F2__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.F2._load) */
  /* Insert the implementation of the static class initializer method here... */
    /* DO-NOT-DELETE splicer.end(Inherit.F2._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_F2__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Inherit_F2__ctor(
  /* in */ Inherit_F2 self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.F2._ctor) */
  /* Insert the implementation of the constructor method here... */
    /* DO-NOT-DELETE splicer.end(Inherit.F2._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_F2__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Inherit_F2__ctor2(
  /* in */ Inherit_F2 self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.F2._ctor2) */
  /* Insert-Code-Here {Inherit.F2._ctor2} (special constructor method) */
    /* DO-NOT-DELETE splicer.end(Inherit.F2._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_F2__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Inherit_F2__dtor(
  /* in */ Inherit_F2 self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.F2._dtor) */
  /* Insert the implementation of the constructor method here... */
    /* DO-NOT-DELETE splicer.end(Inherit.F2._dtor) */
  }
}

/*
 * Method:  c[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_F2_c"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_Inherit_F2_c(
  /* in */ Inherit_F2 self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.F2.c) */
  return sidl_String_strdup("F2.c");
    /* DO-NOT-DELETE splicer.end(Inherit.F2.c) */
  }
}

/*
 * Method:  a[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_F2_a"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_Inherit_F2_a(
  /* in */ Inherit_F2 self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.F2.a) */
  return sidl_String_strdup("F2.a");
    /* DO-NOT-DELETE splicer.end(Inherit.F2.a) */
  }
}

/*
 * Method:  b[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_F2_b"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_Inherit_F2_b(
  /* in */ Inherit_F2 self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.F2.b) */
  return sidl_String_strdup("F2.b");
    /* DO-NOT-DELETE splicer.end(Inherit.F2.b) */
  }
}

/*
 * Method:  f[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_F2_f"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_Inherit_F2_f(
  /* in */ Inherit_F2 self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.F2.f) */
  return sidl_String_strdup("F2.f");
    /* DO-NOT-DELETE splicer.end(Inherit.F2.f) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

