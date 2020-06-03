/*
 * File:          Inherit_J_Impl.c
 * Symbol:        Inherit.J-v1.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Inherit.J
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "Inherit.J" (version 1.1)
 */

#include "Inherit_J_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(Inherit.J._includes) */
#include "sidl_String.h"
#include <stdlib.h>
/* Put additional includes or other arbitrary code here... */
/* DO-NOT-DELETE splicer.end(Inherit.J._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
static const struct Inherit_E2__epv* superEPV = NULL;

void Inherit_J__superEPV(
struct Inherit_E2__epv* parentEPV){
  superEPV = parentEPV;
}
/*
 * Method:  e[]
 */

static char*
super_e(
  /* in */ Inherit_J self,
  /* out */ sidl_BaseInterface *_ex)
{
  return (*superEPV->f_e)((struct Inherit_E2__object*)
    self,
    _ex);
}

/*
 * Method:  c[]
 */

static char*
super_c(
  /* in */ Inherit_J self,
  /* out */ sidl_BaseInterface *_ex)
{
  return (*superEPV->f_c)((struct Inherit_E2__object*)
    self,
    _ex);
}

/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_J__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Inherit_J__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.J._load) */
  /* Insert the implementation of the static class initializer method here... */
    /* DO-NOT-DELETE splicer.end(Inherit.J._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_J__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Inherit_J__ctor(
  /* in */ Inherit_J self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.J._ctor) */
  /* Insert the implementation of the constructor method here... */
    /* DO-NOT-DELETE splicer.end(Inherit.J._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_J__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Inherit_J__ctor2(
  /* in */ Inherit_J self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.J._ctor2) */
  /* Insert-Code-Here {Inherit.J._ctor2} (special constructor method) */
    /* DO-NOT-DELETE splicer.end(Inherit.J._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_J__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Inherit_J__dtor(
  /* in */ Inherit_J self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.J._dtor) */
  /* Insert the implementation of the destructor method here... */
    /* DO-NOT-DELETE splicer.end(Inherit.J._dtor) */
  }
}

/*
 * Method:  j[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_J_j"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_Inherit_J_j(
  /* in */ Inherit_J self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.J.j) */
  return sidl_String_strdup("J.j");
    /* DO-NOT-DELETE splicer.end(Inherit.J.j) */
  }
}

/*
 * Method:  e[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_J_e"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_Inherit_J_e(
  /* in */ Inherit_J self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.J.e) */
  sidl_BaseInterface throwaway_exception;
  char* super_value = super_e(self, &throwaway_exception);
  char* ret = sidl_String_alloc(sidl_String_strlen(super_value) + 3);
  sidl_String_strcpy(ret, "J.");
  sidl_String_strcpy(ret+2, super_value);
  sidl_String_free(super_value);
  return ret;

    /* DO-NOT-DELETE splicer.end(Inherit.J.e) */
  }
}

/*
 * Method:  c[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_J_c"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_Inherit_J_c(
  /* in */ Inherit_J self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.J.c) */
  sidl_BaseInterface throwaway_exception;
  char* super_result = super_c(self, &throwaway_exception);
  char* ret = sidl_String_alloc(sidl_String_strlen(super_result) + 3);
  sidl_String_strcpy(ret, "J.");
  sidl_String_strcpy(ret+2, super_result);
  sidl_String_free(super_result);
  return ret;
    /* DO-NOT-DELETE splicer.end(Inherit.J.c) */
  }
}

/*
 * Method:  a[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_J_a"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_Inherit_J_a(
  /* in */ Inherit_J self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.J.a) */
  return sidl_String_strdup("J.a");

    /* DO-NOT-DELETE splicer.end(Inherit.J.a) */
  }
}

/*
 * Method:  b[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Inherit_J_b"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_Inherit_J_b(
  /* in */ Inherit_J self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Inherit.J.b) */
  return sidl_String_strdup("J.b");

    /* DO-NOT-DELETE splicer.end(Inherit.J.b) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

