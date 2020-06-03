/*
 * File:          wrapper_Data_Impl.c
 * Symbol:        wrapper.Data-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for wrapper.Data
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "wrapper.Data" (version 1.0)
 */

#include "wrapper_Data_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(wrapper.Data._includes) */
#include <stdio.h>
/* DO-NOT-DELETE splicer.end(wrapper.Data._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_wrapper_Data__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_wrapper_Data__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(wrapper.Data._load) */
  /* Insert-Code-Here {wrapper.Data._load} (static class initializer method) */
    /* DO-NOT-DELETE splicer.end(wrapper.Data._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_wrapper_Data__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_wrapper_Data__ctor(
  /* in */ wrapper_Data self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(wrapper.Data._ctor) */
  /* Insert-Code-Here {wrapper.Data._ctor} (constructor method) */
  /*
   * boilerplate constructor
   * struct wrapper_Data__data *dptr = (struct wrapper_Data__data*)malloc(sizeof(struct wrapper_Data__data));
   * if (dptr) {
   *   memset(dptr, 0, sizeof(struct wrapper_Data__data));
   *   initialize elements of dptr here
   * }
   * wrapper_Data__set_data(self, dptr);
   */
  fprintf(stderr, "Wrong Constructor Called to wrapper_Data, implemented in C\n");
    /* DO-NOT-DELETE splicer.end(wrapper.Data._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_wrapper_Data__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_wrapper_Data__ctor2(
  /* in */ wrapper_Data self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(wrapper.Data._ctor2) */
  struct wrapper_Data__data *dptr = (struct wrapper_Data__data *) private_data;
  dptr->d_ctorTest = "ctor was run";
    /* DO-NOT-DELETE splicer.end(wrapper.Data._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_wrapper_Data__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_wrapper_Data__dtor(
  /* in */ wrapper_Data self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(wrapper.Data._dtor) */
  /* Insert-Code-Here {wrapper.Data._dtor} (destructor method) */
  /*
   * boilerplate destructor
   * struct wrapper_Data__data *dptr = wrapper_Data__get_data(self);
   * if (dptr) {
   *   free contained in dtor before next line
   *   free(dptr);
   *   wrapper_Data__set_data(self, NULL);
   * }
   */

    /* DO-NOT-DELETE splicer.end(wrapper.Data._dtor) */
  }
}

/*
 * Method:  setString[]
 */

#undef __FUNC__
#define __FUNC__ "impl_wrapper_Data_setString"

#ifdef __cplusplus
extern "C"
#endif
void
impl_wrapper_Data_setString(
  /* in */ wrapper_Data self,
  /* in */ const char* s,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(wrapper.Data.setString) */
  struct wrapper_Data__data *dptr = 
    wrapper_Data__get_data(self);
  if (dptr) {
    dptr->d_string = "Hello World!";
  }
    /* DO-NOT-DELETE splicer.end(wrapper.Data.setString) */
  }
}

/*
 * Method:  setInt[]
 */

#undef __FUNC__
#define __FUNC__ "impl_wrapper_Data_setInt"

#ifdef __cplusplus
extern "C"
#endif
void
impl_wrapper_Data_setInt(
  /* in */ wrapper_Data self,
  /* in */ int32_t i,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(wrapper.Data.setInt) */
  struct wrapper_Data__data *dptr = 
    wrapper_Data__get_data(self);
  if (dptr) {
    dptr->d_int = 3;
  }
    /* DO-NOT-DELETE splicer.end(wrapper.Data.setInt) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

