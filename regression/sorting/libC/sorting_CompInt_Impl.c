/*
 * File:          sorting_CompInt_Impl.c
 * Symbol:        sorting.CompInt-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for sorting.CompInt
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "sorting.CompInt" (version 0.1)
 * 
 * Compare two Integer's.  By default, this will sort in increasing order.
 */

#include "sorting_CompInt_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(sorting.CompInt._includes) */
#include <stdlib.h>
#include "sorting_Integer.h"
/* DO-NOT-DELETE splicer.end(sorting.CompInt._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_sorting_CompInt__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sorting_CompInt__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sorting.CompInt._load) */
  /* Insert the implementation of the static class initializer method here... */
    /* DO-NOT-DELETE splicer.end(sorting.CompInt._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_sorting_CompInt__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sorting_CompInt__ctor(
  /* in */ sorting_CompInt self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sorting.CompInt._ctor) */
  struct sorting_CompInt__data *dptr =
    malloc(sizeof(struct sorting_CompInt__data));
  if (dptr) {
    dptr->d_increasing = TRUE;
  }
  sorting_CompInt__set_data(self, dptr);
    /* DO-NOT-DELETE splicer.end(sorting.CompInt._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_sorting_CompInt__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sorting_CompInt__ctor2(
  /* in */ sorting_CompInt self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sorting.CompInt._ctor2) */
  /* Insert-Code-Here {sorting.CompInt._ctor2} (special constructor method) */
    /* DO-NOT-DELETE splicer.end(sorting.CompInt._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_sorting_CompInt__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sorting_CompInt__dtor(
  /* in */ sorting_CompInt self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sorting.CompInt._dtor) */
  struct sorting_CompInt__data *dptr = 
    sorting_CompInt__get_data(self);
  if (dptr) {
    free((void*)dptr);
    sorting_CompInt__set_data(self, NULL);
  }
    /* DO-NOT-DELETE splicer.end(sorting.CompInt._dtor) */
  }
}

/*
 * If increasing is true, this will cause the comparator to
 * report a normal definition of less than; otherwise, it will
 * reverse the normal ordering.
 */

#undef __FUNC__
#define __FUNC__ "impl_sorting_CompInt_setSortIncreasing"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sorting_CompInt_setSortIncreasing(
  /* in */ sorting_CompInt self,
  /* in */ sidl_bool increasing,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sorting.CompInt.setSortIncreasing) */
  struct sorting_CompInt__data *dptr = 
    sorting_CompInt__get_data(self);
  if (dptr) {
    dptr->d_increasing = increasing;
  }
    /* DO-NOT-DELETE splicer.end(sorting.CompInt.setSortIncreasing) */
  }
}

/*
 * This method is used to define an ordering of objects.  This method
 * will return -1 if i1 < i2, 0 if i1 = i2; and 1 if i1 > i2.
 */

#undef __FUNC__
#define __FUNC__ "impl_sorting_CompInt_compare"

#ifdef __cplusplus
extern "C"
#endif
int32_t
impl_sorting_CompInt_compare(
  /* in */ sorting_CompInt self,
  /* in */ sidl_BaseInterface i1,
  /* in */ sidl_BaseInterface i2,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sorting.CompInt.compare) */
  int result = 0;
  struct sorting_CompInt__data *dptr = 
    sorting_CompInt__get_data(self);
  if (dptr) {
    sorting_Integer int1 = sorting_Integer__cast(i1, _ex);
    sorting_Integer int2 = sorting_Integer__cast(i2, _ex);
    if (int1 && int2) {
      const int32_t val1 = sorting_Integer_getValue(int1, _ex);
      const int32_t val2 = sorting_Integer_getValue(int2, _ex);
      if (val1 < val2) result = -1;
      if (val1 > val2) result = 1;
      if (!dptr->d_increasing) {
        result = -result;
      }
    }
    if (int1) sorting_Integer_deleteRef(int1, _ex);
    if (int2) sorting_Integer_deleteRef(int2, _ex);
  }
  return result;
    /* DO-NOT-DELETE splicer.end(sorting.CompInt.compare) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

