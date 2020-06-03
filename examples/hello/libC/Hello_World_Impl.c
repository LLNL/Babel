/*
 * File:          Hello_World_Impl.c
 * Symbol:        Hello.World-v1.2
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7399M trunk)
 * Description:   Server-side implementation for Hello.World
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "Hello.World" (version 1.2)
 */

#include "Hello_World_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(Hello.World._includes) */
#include "sidl_String.h"
/* DO-NOT-DELETE splicer.end(Hello.World._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_Hello_World__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Hello_World__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Hello.World._load) */
  /* Insert the implementation of the static class initializer method here... */
    /* DO-NOT-DELETE splicer.end(Hello.World._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_Hello_World__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Hello_World__ctor(
  /* in */ Hello_World self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Hello.World._ctor) */
  /* nothing needed */
    /* DO-NOT-DELETE splicer.end(Hello.World._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_Hello_World__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Hello_World__ctor2(
  /* in */ Hello_World self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Hello.World._ctor2) */
  /* Insert-Code-Here {Hello.World._ctor2} (special constructor method) */
    /* DO-NOT-DELETE splicer.end(Hello.World._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_Hello_World__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Hello_World__dtor(
  /* in */ Hello_World self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Hello.World._dtor) */
  /* nothing needed */
    /* DO-NOT-DELETE splicer.end(Hello.World._dtor) */
  }
}

/*
 * Method:  getMsg[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Hello_World_getMsg"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_Hello_World_getMsg(
  /* in */ Hello_World self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Hello.World.getMsg) */
  return sidl_String_strdup("Hello world!");
    /* DO-NOT-DELETE splicer.end(Hello.World.getMsg) */
  }
}

/*
 * Method:  foo[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Hello_World_foo"

#ifdef __cplusplus
extern "C"
#endif
int32_t
impl_Hello_World_foo(
  /* in */ Hello_World self,
  /* in */ int32_t i,
  /* out */ int32_t* o,
  /* inout */ int32_t* io,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Hello.World.foo) */
  /* TODO: Is this supposed to be here? */
  return 0;
    /* DO-NOT-DELETE splicer.end(Hello.World.foo) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

