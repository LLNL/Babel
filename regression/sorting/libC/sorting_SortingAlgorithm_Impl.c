/*
 * File:          sorting_SortingAlgorithm_Impl.c
 * Symbol:        sorting.SortingAlgorithm-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for sorting.SortingAlgorithm
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "sorting.SortingAlgorithm" (version 0.1)
 * 
 * An abstract sorting algorithm.
 */

#include "sorting_SortingAlgorithm_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm._includes) */
#include <stdlib.h>
#include "sorting_SimpleCounter.h"
#include "sidl_Exception.h"
/* DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_sorting_SortingAlgorithm__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sorting_SortingAlgorithm__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm._load) */
  /* Insert the implementation of the static class initializer method here... */
    /* DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_sorting_SortingAlgorithm__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sorting_SortingAlgorithm__ctor(
  /* in */ sorting_SortingAlgorithm self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm._ctor) */
  struct sorting_SortingAlgorithm__data *dptr = 
    malloc(sizeof(struct sorting_SortingAlgorithm__data ));
  if (dptr) {
    sorting_SimpleCounter sc;
    dptr->d_swp = NULL;
    dptr->d_cmp = NULL;
    sc = sorting_SimpleCounter__create(_ex); SIDL_REPORT(*_ex);
    dptr->d_swp = sorting_Counter__cast(sc, _ex); SIDL_REPORT(*_ex);
    sorting_SimpleCounter_deleteRef(sc, _ex); SIDL_REPORT(*_ex);
    sc = sorting_SimpleCounter__create(_ex); SIDL_REPORT(*_ex);
    dptr->d_cmp = sorting_Counter__cast(sc, _ex); SIDL_REPORT(*_ex);
    sorting_SimpleCounter_deleteRef(sc, _ex); SIDL_REPORT(*_ex);
  }
  sorting_SortingAlgorithm__set_data(self, dptr);
  return;
 EXIT:
  {
    sidl_BaseInterface throwaway_exception;
    if (dptr) {
      if (dptr->d_swp)
        sorting_Counter_deleteRef(dptr->d_swp, &throwaway_exception);
      if (dptr->d_cmp)
        sorting_Counter_deleteRef(dptr->d_cmp, &throwaway_exception);
      free(dptr);
    }
  } 
    /* DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_sorting_SortingAlgorithm__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sorting_SortingAlgorithm__ctor2(
  /* in */ sorting_SortingAlgorithm self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm._ctor2) */
  /* Insert-Code-Here {sorting.SortingAlgorithm._ctor2} (special constructor method) */
    /* DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_sorting_SortingAlgorithm__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sorting_SortingAlgorithm__dtor(
  /* in */ sorting_SortingAlgorithm self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm._dtor) */
  sidl_BaseInterface throwaway_exception;
  struct sorting_SortingAlgorithm__data *dptr = 
    sorting_SortingAlgorithm__get_data(self);
  if (dptr) {
    sorting_Counter_deleteRef(dptr->d_swp, &throwaway_exception);
    dptr->d_swp = NULL;
    sorting_Counter_deleteRef(dptr->d_cmp, &throwaway_exception);
    dptr->d_cmp = NULL;
    free((void *)dptr);
  }
    /* DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm._dtor) */
  }
}

/*
 * Return the comparison counter.
 */

#undef __FUNC__
#define __FUNC__ "impl_sorting_SortingAlgorithm_getCompareCounter"

#ifdef __cplusplus
extern "C"
#endif
sorting_Counter
impl_sorting_SortingAlgorithm_getCompareCounter(
  /* in */ sorting_SortingAlgorithm self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm.getCompareCounter) */
  struct sorting_SortingAlgorithm__data *dptr = 
    sorting_SortingAlgorithm__get_data(self);
  sorting_Counter result = NULL;
  if (dptr) {
    sorting_Counter_addRef(dptr->d_cmp, _ex); SIDL_REPORT(*_ex);
    result = dptr->d_cmp;
  }
 EXIT:
  return result;
    /* DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm.getCompareCounter) */
  }
}

/*
 * Return the swap counter.
 */

#undef __FUNC__
#define __FUNC__ "impl_sorting_SortingAlgorithm_getSwapCounter"

#ifdef __cplusplus
extern "C"
#endif
sorting_Counter
impl_sorting_SortingAlgorithm_getSwapCounter(
  /* in */ sorting_SortingAlgorithm self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm.getSwapCounter) */
  struct sorting_SortingAlgorithm__data *dptr = 
    sorting_SortingAlgorithm__get_data(self);
  sorting_Counter result = NULL;
  if (dptr) {
    sorting_Counter_addRef(dptr->d_swp, _ex); SIDL_REPORT(*_ex);
    result = dptr->d_swp;
  }
 EXIT:
  return result;
    /* DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm.getSwapCounter) */
  }
}

/*
 * Reset the comparison and swap counter.
 */

#undef __FUNC__
#define __FUNC__ "impl_sorting_SortingAlgorithm_reset"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sorting_SortingAlgorithm_reset(
  /* in */ sorting_SortingAlgorithm self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm.reset) */
  struct sorting_SortingAlgorithm__data *dptr = 
    sorting_SortingAlgorithm__get_data(self);
  if (dptr) {
    sorting_Counter_reset(dptr->d_swp, _ex); SIDL_REPORT(*_ex);
    sorting_Counter_reset(dptr->d_cmp, _ex); SIDL_REPORT(*_ex);
  }
 EXIT:;
    /* DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm.reset) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

