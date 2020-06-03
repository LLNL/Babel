/*
 * File:          hooks_Basics_Impl.c
 * Symbol:        hooks.Basics-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for hooks.Basics
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "hooks.Basics" (version 1.0)
 */

#include "hooks_Basics_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(hooks.Basics._includes) */
#include <stdio.h>
#include <stdlib.h>
#ifndef NULL
#define NULL 0
#endif

static int num_prehooks_static;
static int num_posthooks_static;

/* DO-NOT-DELETE splicer.end(hooks.Basics._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_hooks_Basics__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_hooks_Basics__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(hooks.Basics._load) */
  /* Insert-Code-Here {hooks.Basics._load} (static class initializer method) */
    /* DO-NOT-DELETE splicer.end(hooks.Basics._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_hooks_Basics__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_hooks_Basics__ctor(
  /* in */ hooks_Basics self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(hooks.Basics._ctor) */
    struct hooks_Basics__data *d =
      (struct hooks_Basics__data*)malloc(sizeof(struct hooks_Basics__data));
    d->num_prehooks = 0;
    d->num_posthooks = 0;
    hooks_Basics__set_data(self, d);
    /* DO-NOT-DELETE splicer.end(hooks.Basics._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_hooks_Basics__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_hooks_Basics__ctor2(
  /* in */ hooks_Basics self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(hooks.Basics._ctor2) */
  /* Insert-Code-Here {hooks.Basics._ctor2} (special constructor method) */
    /* DO-NOT-DELETE splicer.end(hooks.Basics._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_hooks_Basics__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_hooks_Basics__dtor(
  /* in */ hooks_Basics self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(hooks.Basics._dtor) */
    if(hooks_Basics__get_data(self))
      free(hooks_Basics__get_data(self));
    /* DO-NOT-DELETE splicer.end(hooks.Basics._dtor) */
  }
}

/*
 * Basic illustration of hooks for static methods.
 */

#undef __FUNC__
#define __FUNC__ "impl_hooks_Basics_aStaticMeth_pre"

#ifdef __cplusplus
extern "C"
#endif
void
impl_hooks_Basics_aStaticMeth_pre(
  /* in */ int32_t i,
  /* in */ int32_t io,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(hooks.Basics.aStaticMeth_pre) */
    num_prehooks_static ++;
    /* DO-NOT-DELETE splicer.end(hooks.Basics.aStaticMeth_pre) */
  }
}

/*
 * Basic illustration of hooks for static methods.
 */

#undef __FUNC__
#define __FUNC__ "impl_hooks_Basics_aStaticMeth"

#ifdef __cplusplus
extern "C"
#endif
int32_t
impl_hooks_Basics_aStaticMeth(
  /* in */ int32_t i,
  /* out */ int32_t* o,
  /* inout */ int32_t* io,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(hooks.Basics.aStaticMeth) */
    *o = num_prehooks_static;
    *io = num_posthooks_static;
    return 1;
    /* DO-NOT-DELETE splicer.end(hooks.Basics.aStaticMeth) */
  }
}

/*
 * Basic illustration of hooks for static methods.
 */

#undef __FUNC__
#define __FUNC__ "impl_hooks_Basics_aStaticMeth_post"

#ifdef __cplusplus
extern "C"
#endif
void
impl_hooks_Basics_aStaticMeth_post(
  /* in */ int32_t i,
  /* in */ int32_t o,
  /* in */ int32_t io,
  /* in */ int32_t _retval,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(hooks.Basics.aStaticMeth_post) */
    num_posthooks_static++;
    /* DO-NOT-DELETE splicer.end(hooks.Basics.aStaticMeth_post) */
  }
}

/*
 * Basic illustration of hooks for static methods.
 */

#undef __FUNC__
#define __FUNC__ "impl_hooks_Basics_aNonStaticMeth_pre"

#ifdef __cplusplus
extern "C"
#endif
void
impl_hooks_Basics_aNonStaticMeth_pre(
  /* in */ hooks_Basics self,
  /* in */ int32_t i,
  /* in */ int32_t io,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(hooks.Basics.aNonStaticMeth_pre) */
    hooks_Basics__get_data(self)->num_prehooks++;
    /* DO-NOT-DELETE splicer.end(hooks.Basics.aNonStaticMeth_pre) */
  }
}

/*
 * Basic illustration of hooks for static methods.
 */

#undef __FUNC__
#define __FUNC__ "impl_hooks_Basics_aNonStaticMeth"

#ifdef __cplusplus
extern "C"
#endif
int32_t
impl_hooks_Basics_aNonStaticMeth(
  /* in */ hooks_Basics self,
  /* in */ int32_t i,
  /* out */ int32_t* o,
  /* inout */ int32_t* io,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(hooks.Basics.aNonStaticMeth) */
    *o = hooks_Basics__get_data(self)->num_prehooks;
    *io = hooks_Basics__get_data(self)->num_posthooks;
    return 1;
    /* DO-NOT-DELETE splicer.end(hooks.Basics.aNonStaticMeth) */
  }
}

/*
 * Basic illustration of hooks for static methods.
 */

#undef __FUNC__
#define __FUNC__ "impl_hooks_Basics_aNonStaticMeth_post"

#ifdef __cplusplus
extern "C"
#endif
void
impl_hooks_Basics_aNonStaticMeth_post(
  /* in */ hooks_Basics self,
  /* in */ int32_t i,
  /* in */ int32_t o,
  /* in */ int32_t io,
  /* in */ int32_t _retval,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(hooks.Basics.aNonStaticMeth_post) */
    hooks_Basics__get_data(self)->num_posthooks++;
    /* DO-NOT-DELETE splicer.end(hooks.Basics.aNonStaticMeth_post) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

