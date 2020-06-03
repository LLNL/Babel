/*
 * File:          sorting_Integer_Impl.c
 * Symbol:        sorting.Integer-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for sorting.Integer
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "sorting.Integer" (version 0.1)
 * 
 * An object to hold a simple integer.
 */

#include "sorting_Integer_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(sorting.Integer._includes) */
#include <stdlib.h>
/* DO-NOT-DELETE splicer.end(sorting.Integer._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_sorting_Integer__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sorting_Integer__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sorting.Integer._load) */
  /* Insert the implementation of the static class initializer method here... */
    /* DO-NOT-DELETE splicer.end(sorting.Integer._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_sorting_Integer__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sorting_Integer__ctor(
  /* in */ sorting_Integer self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sorting.Integer._ctor) */
  struct sorting_Integer__data *dptr =
    malloc(sizeof(struct sorting_Integer__data));
  if (dptr) {
    dptr->d_num = 0;
  }
  sorting_Integer__set_data(self, dptr);
    /* DO-NOT-DELETE splicer.end(sorting.Integer._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_sorting_Integer__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sorting_Integer__ctor2(
  /* in */ sorting_Integer self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sorting.Integer._ctor2) */
  /* Insert-Code-Here {sorting.Integer._ctor2} (special constructor method) */
    /* DO-NOT-DELETE splicer.end(sorting.Integer._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_sorting_Integer__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sorting_Integer__dtor(
  /* in */ sorting_Integer self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sorting.Integer._dtor) */
  struct sorting_Integer__data *dptr =
    sorting_Integer__get_data(self);
  if (dptr) {
    free((void *)dptr);
    sorting_Integer__set_data(self, NULL);
  }
    /* DO-NOT-DELETE splicer.end(sorting.Integer._dtor) */
  }
}

/*
 * Method:  getValue[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sorting_Integer_getValue"

#ifdef __cplusplus
extern "C"
#endif
int32_t
impl_sorting_Integer_getValue(
  /* in */ sorting_Integer self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sorting.Integer.getValue) */
  struct sorting_Integer__data *dptr =
    sorting_Integer__get_data(self);
  return dptr ? dptr->d_num : 0;
    /* DO-NOT-DELETE splicer.end(sorting.Integer.getValue) */
  }
}

/*
 * Method:  setValue[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sorting_Integer_setValue"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sorting_Integer_setValue(
  /* in */ sorting_Integer self,
  /* in */ int32_t value,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sorting.Integer.setValue) */
  struct sorting_Integer__data *dptr =
    sorting_Integer__get_data(self);
  if (dptr) {
    dptr->d_num = value;
  }
    /* DO-NOT-DELETE splicer.end(sorting.Integer.setValue) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

