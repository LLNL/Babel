/*
 * File:          sorting_Quicksort_Impl.c
 * Symbol:        sorting.Quicksort-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for sorting.Quicksort
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "sorting.Quicksort" (version 0.1)
 * 
 * Quick sort
 */

#include "sorting_Quicksort_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(sorting.Quicksort._includes) */
#include "sorting_Container.h"
#include "sorting_Counter.h"
#include "sorting_Comparator.h"
#include "sidl_String.h"
#include <stdio.h>
#include "sidl_Exception.h"

/**
 * Choose the middle of the first, middle and last element of the
 * list.  For small lists, return the middle without checking.
 */
static int32_t
choosePivot(sorting_Container  elems,
            sorting_Comparator comp,
            sorting_Counter    cmp,
            int32_t         start,
            int32_t         end, 
            sidl_BaseInterface *_ex)
{
  int32_t pivot = (start + end) >> 1;
  if ((end - start) > 4) {
    int32_t mid = pivot;
    sorting_Counter_inc(cmp, _ex); SIDL_REPORT(*_ex);
    if (sorting_Container_compare(elems, start, mid, comp, _ex) <= 0) {
      SIDL_REPORT(*_ex);
      sorting_Counter_inc(cmp, _ex); SIDL_REPORT(*_ex);
      if (sorting_Container_compare(elems, mid, end - 1, comp, _ex) > 0) {
        SIDL_REPORT(*_ex);
        sorting_Counter_inc(cmp, _ex); SIDL_REPORT(*_ex);
        if (sorting_Container_compare(elems, start, end - 1, comp, _ex) < 0) {
          SIDL_REPORT(*_ex);
          pivot = end - 1;
        }
        else {
          SIDL_REPORT(*_ex);
          pivot = start;
        }
      }
    }
    else {
      SIDL_REPORT(*_ex);
      sorting_Counter_inc(cmp, _ex); SIDL_REPORT(*_ex);
      if (sorting_Container_compare(elems, mid, end - 1, comp, _ex) < 0) {
        SIDL_REPORT(*_ex);
        sorting_Counter_inc(cmp, _ex); SIDL_REPORT(*_ex);
        if (sorting_Container_compare(elems, start, end - 1, comp, _ex) > 0) {
          SIDL_REPORT(*_ex);
          pivot = end - 1;
        }
        else {
          SIDL_REPORT(*_ex);
          pivot = start;
        }
      }
    }
  }
 EXIT:
  return pivot;
}

static void 
quickSort(sorting_Container  elems,
          sorting_Comparator comp,
          sorting_Counter    cmp,
          sorting_Counter    swp,
          int32_t         start,
          int32_t         end,
          sidl_BaseInterface *_ex)
{
  if ((end - start) > 1) {
    int32_t i = start;
    int32_t j = end;
    int32_t pivot = choosePivot(elems, comp, cmp, start, end, _ex); SIDL_REPORT(*_ex);
    if (pivot != start) {
      sorting_Counter_inc(swp, _ex); SIDL_REPORT(*_ex);

      sorting_Container_swap(elems, start, pivot, _ex); SIDL_REPORT(*_ex);
    }
    for(;;) {
      do {
        SIDL_REPORT(*_ex);
        --j;
        sorting_Counter_inc(cmp, _ex); SIDL_REPORT(*_ex);
      } while (sorting_Container_compare(elems, start, j, comp, _ex) < 0);
      SIDL_REPORT(*_ex);
      while (++i < j) {
        const int icmp = sorting_Container_compare(elems, start, i, comp, _ex); 
        SIDL_REPORT(*_ex);
        sorting_Counter_inc(cmp, _ex); SIDL_REPORT(*_ex);
        if (icmp < 0) break;
      }
      if (i >= j) break;
      sorting_Counter_inc(swp, _ex); SIDL_REPORT(*_ex);
      sorting_Container_swap(elems, i, j, _ex); SIDL_REPORT(*_ex);
    }
    if (j != start) {
      sorting_Counter_inc(swp, _ex); SIDL_REPORT(*_ex);
      sorting_Container_swap(elems, start, j, _ex); SIDL_REPORT(*_ex);
    }
    quickSort(elems, comp, cmp, swp, start, j, _ex); SIDL_REPORT(*_ex);
    quickSort(elems, comp, cmp, swp, j + 1, end, _ex); SIDL_REPORT(*_ex);
  }
 EXIT:;
}
/* DO-NOT-DELETE splicer.end(sorting.Quicksort._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_sorting_Quicksort__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sorting_Quicksort__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sorting.Quicksort._load) */
  /* Insert the implementation of the static class initializer method here... */
    /* DO-NOT-DELETE splicer.end(sorting.Quicksort._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_sorting_Quicksort__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sorting_Quicksort__ctor(
  /* in */ sorting_Quicksort self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sorting.Quicksort._ctor) */
  /* Insert the implementation of the constructor method here... */
    /* DO-NOT-DELETE splicer.end(sorting.Quicksort._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_sorting_Quicksort__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sorting_Quicksort__ctor2(
  /* in */ sorting_Quicksort self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sorting.Quicksort._ctor2) */
  /* Insert-Code-Here {sorting.Quicksort._ctor2} (special constructor method) */
    /* DO-NOT-DELETE splicer.end(sorting.Quicksort._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_sorting_Quicksort__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sorting_Quicksort__dtor(
  /* in */ sorting_Quicksort self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sorting.Quicksort._dtor) */
  /* Insert the implementation of the destructor method here... */
    /* DO-NOT-DELETE splicer.end(sorting.Quicksort._dtor) */
  }
}

/*
 * Sort elements using Quick Sort.
 */

#undef __FUNC__
#define __FUNC__ "impl_sorting_Quicksort_sort"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sorting_Quicksort_sort(
  /* in */ sorting_Quicksort self,
  /* in */ sorting_Container elems,
  /* in */ sorting_Comparator comp,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sorting.Quicksort.sort) */
  sorting_Counter cmp = NULL;
  sorting_Counter swp = NULL;
  const int32_t num = sorting_Container_getLength(elems, _ex); SIDL_REPORT(*_ex);
  cmp = sorting_Quicksort_getCompareCounter(self, _ex); SIDL_REPORT(*_ex);
  swp = sorting_Quicksort_getSwapCounter(self, _ex); SIDL_REPORT(*_ex);
  quickSort(elems, comp, cmp, swp, 0, num, _ex); SIDL_REPORT(*_ex);
 EXIT:
  {
    sidl_BaseInterface throwaway_exception;
    if (cmp) sorting_Counter_deleteRef(cmp, &throwaway_exception);
    if (swp) sorting_Counter_deleteRef(swp, &throwaway_exception);
  }
    /* DO-NOT-DELETE splicer.end(sorting.Quicksort.sort) */
  }
}

/*
 * Return quick sorting.
 */

#undef __FUNC__
#define __FUNC__ "impl_sorting_Quicksort_getName"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_sorting_Quicksort_getName(
  /* in */ sorting_Quicksort self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sorting.Quicksort.getName) */
  return sidl_String_strdup("Quick sort");
    /* DO-NOT-DELETE splicer.end(sorting.Quicksort.getName) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

