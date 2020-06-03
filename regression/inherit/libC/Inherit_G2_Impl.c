/*
 * File:          Inherit_G2_Impl.c
 * Symbol:        Inherit.G2-v1.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Inherit.G2
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "Inherit.G2" (version 1.1)
 */

#include "Inherit_G2_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(Inherit.G2._includes) */
#include <stdlib.h>
#include "sidl_String.h"
/* DO-NOT-DELETE splicer.end(Inherit.G2._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
static const struct Inherit_D__epv* superEPV = NULL;

void Inherit_G2__superEPV(
struct Inherit_D__epv* parentEPV){
  superEPV = parentEPV;
}
/*
 * Method:  a[]
 */

static char*
super_a(
  /* in */ Inherit_G2 self,
  /* out */ sidl_BaseInterface *_ex)
{
  return (*superEPV->f_a)((struct Inherit_D__object*)
    self,
    _ex);
}

/*
 * Method:  d[]
 */

static char*
super_d(
  /* in */ Inherit_G2 self,
  /* out */ sidl_BaseInterface *_ex)
{
  return (*superEPV->f_d)((struct Inherit_D__object*)
    self,
    _ex);
}

/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_G2__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Inherit_G2__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.G2._load) */
  /* Insert the implementation of the static class initializer method here... */
    /* DO-NOT-DELETE splicer.end(Inherit.G2._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_G2__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Inherit_G2__ctor(
  /* in */ Inherit_G2 self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.G2._ctor) */
  /* Insert the implementation of the constructor method here... */
    /* DO-NOT-DELETE splicer.end(Inherit.G2._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_G2__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Inherit_G2__ctor2(
  /* in */ Inherit_G2 self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.G2._ctor2) */
  /* Insert-Code-Here {Inherit.G2._ctor2} (special constructor method) */
    /* DO-NOT-DELETE splicer.end(Inherit.G2._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_G2__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Inherit_G2__dtor(
  /* in */ Inherit_G2 self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.G2._dtor) */
  /* Insert the implementation of the constructor method here... */
    /* DO-NOT-DELETE splicer.end(Inherit.G2._dtor) */
  }
}

/*
 * Method:  a[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_G2_a"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_Inherit_G2_a(
  /* in */ Inherit_G2 self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.G2.a) */
  return sidl_String_strdup("G2.a");
    /* DO-NOT-DELETE splicer.end(Inherit.G2.a) */
  }
}

/*
 * Method:  d[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_G2_d"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_Inherit_G2_d(
  /* in */ Inherit_G2 self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.G2.d) */
  return sidl_String_strdup("G2.d");
    /* DO-NOT-DELETE splicer.end(Inherit.G2.d) */
  }
}

/*
 * Method:  g[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_G2_g"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_Inherit_G2_g(
  /* in */ Inherit_G2 self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.G2.g) */
  return sidl_String_strdup("G2.g");
    /* DO-NOT-DELETE splicer.end(Inherit.G2.g) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

