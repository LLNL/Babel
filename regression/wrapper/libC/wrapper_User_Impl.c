/*
 * File:          wrapper_User_Impl.c
 * Symbol:        wrapper.User-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for wrapper.User
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "wrapper.User" (version 1.0)
 */

#include "wrapper_User_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(wrapper.User._includes) */
/* Insert-Code-Here {wrapper.User._includes} (includes and arbitrary code) */
/* DO-NOT-DELETE splicer.end(wrapper.User._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_wrapper_User__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_wrapper_User__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(wrapper.User._load) */
  /* Insert-Code-Here {wrapper.User._load} (static class initializer method) */
    /* DO-NOT-DELETE splicer.end(wrapper.User._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_wrapper_User__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_wrapper_User__ctor(
  /* in */ wrapper_User self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(wrapper.User._ctor) */
  /* Insert-Code-Here {wrapper.User._ctor} (constructor method) */
  /*
   * // boilerplate constructor
   * struct wrapper_User__data *dptr = (struct wrapper_User__data*)malloc(sizeof(struct wrapper_User__data));
   * if (dptr) {
   *   memset(dptr, 0, sizeof(struct wrapper_User__data));
   *   // initialize elements of dptr here
   * }
   * wrapper_User__set_data(self, dptr);
   */

    /* DO-NOT-DELETE splicer.end(wrapper.User._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_wrapper_User__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_wrapper_User__ctor2(
  /* in */ wrapper_User self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(wrapper.User._ctor2) */
  /* Insert-Code-Here {wrapper.User._ctor2} (special constructor method) */
    /* DO-NOT-DELETE splicer.end(wrapper.User._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_wrapper_User__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_wrapper_User__dtor(
  /* in */ wrapper_User self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(wrapper.User._dtor) */
  /* Insert-Code-Here {wrapper.User._dtor} (destructor method) */
  /*
   * // boilerplate destructor
   * struct wrapper_User__data *dptr = wrapper_User__get_data(self);
   * if (dptr) {
   *   // free contained in dtor before next line
   *   free(dptr);
   *   wrapper_User__set_data(self, NULL);
   * }
   */

    /* DO-NOT-DELETE splicer.end(wrapper.User._dtor) */
  }
}

/*
 * Method:  accept[]
 */

#undef __FUNC__
#define __FUNC__ "impl_wrapper_User_accept"

#ifdef __cplusplus
extern "C"
#endif
void
impl_wrapper_User_accept(
  /* in */ wrapper_User self,
  /* in */ wrapper_Data data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(wrapper.User.accept) */
  wrapper_Data_setString(data, "Hello World!", _ex);
  wrapper_Data_setInt(data, 3, _ex);
  return;
    /* DO-NOT-DELETE splicer.end(wrapper.User.accept) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

