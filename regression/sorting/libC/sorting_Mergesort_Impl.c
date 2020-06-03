/*
 * File:          sorting_Mergesort_Impl.c
 * Symbol:        sorting.Mergesort-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for sorting.Mergesort
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "sorting.Mergesort" (version 0.1)
 * 
 * Merge sort
 */

#include "sorting_Mergesort_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(sorting.Mergesort._includes) */
#include "sorting_Container.h"
#include "sorting_Comparator.h"
#include "sorting_Counter.h"
#include "sidl_String.h"
#include "sidl_Exception.h"

static void
mergeLists(sorting_Container  elems,
           sorting_Comparator comp,
           sorting_Counter    cmp,
           sorting_Counter    swp,
           int32_t         start,
           int32_t         mid,
           const int32_t   end, 
           sidl_BaseInterface *_ex)
{
  int32_t j;
  while ((start < mid) && (mid < end)) {
    sorting_Counter_inc(cmp, _ex); SIDL_REPORT(*_ex);
    if (sorting_Container_compare(elems, start, mid, comp, _ex) > 0) {
      SIDL_REPORT(*_ex);
      /* move first element of upper list into place */
      for(j = mid;j > start; --j) {
        sorting_Counter_inc(swp, _ex);  SIDL_REPORT(*_ex);
        sorting_Container_swap(elems, j, j - 1, _ex); SIDL_REPORT(*_ex);
      }
      ++mid;
    }
    ++start;
  }
 EXIT:;
}

/**
 * end is one past the end
 */
static void
mergeSort(sorting_Container  elems,
          sorting_Comparator comp,
          sorting_Counter    cmp,
          sorting_Counter    swp,
          const int32_t   start,
          const int32_t   end,
          sidl_BaseInterface *_ex)
{
  if ((end - start) > 1) {
    int32_t mid = (start + end) >> 1;
    mergeSort(elems, comp, cmp, swp, start, mid, _ex); SIDL_REPORT(*_ex);
    mergeSort(elems, comp, cmp, swp, mid, end, _ex); SIDL_REPORT(*_ex);
    mergeLists(elems, comp, cmp, swp, start, mid, end, _ex); SIDL_REPORT(*_ex);
  }
 EXIT:;
}
/* DO-NOT-DELETE splicer.end(sorting.Mergesort._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_sorting_Mergesort__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sorting_Mergesort__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sorting.Mergesort._load) */
  /* Insert the implementation of the static class initializer method here... */
    /* DO-NOT-DELETE splicer.end(sorting.Mergesort._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_sorting_Mergesort__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sorting_Mergesort__ctor(
  /* in */ sorting_Mergesort self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sorting.Mergesort._ctor) */
  /* Insert the implementation of the constructor method here... */
    /* DO-NOT-DELETE splicer.end(sorting.Mergesort._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_sorting_Mergesort__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sorting_Mergesort__ctor2(
  /* in */ sorting_Mergesort self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sorting.Mergesort._ctor2) */
  /* Insert-Code-Here {sorting.Mergesort._ctor2} (special constructor method) */
    /* DO-NOT-DELETE splicer.end(sorting.Mergesort._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_sorting_Mergesort__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sorting_Mergesort__dtor(
  /* in */ sorting_Mergesort self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sorting.Mergesort._dtor) */
  /* Insert the implementation of the destructor method here... */
    /* DO-NOT-DELETE splicer.end(sorting.Mergesort._dtor) */
  }
}

/*
 * Sort elements using Merge Sort.
 */

#undef __FUNC__
#define __FUNC__ "impl_sorting_Mergesort_sort"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sorting_Mergesort_sort(
  /* in */ sorting_Mergesort self,
  /* in */ sorting_Container elems,
  /* in */ sorting_Comparator comp,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sorting.Mergesort.sort) */
  sorting_Counter cmp = NULL;
  sorting_Counter swp = NULL;
  const int32_t num = sorting_Container_getLength(elems, _ex); SIDL_REPORT(*_ex);
  cmp = sorting_Mergesort_getCompareCounter(self, _ex); SIDL_REPORT(*_ex);
  swp = sorting_Mergesort_getSwapCounter(self, _ex); SIDL_REPORT(*_ex);
  mergeSort(elems, comp, cmp, swp, 0, num, _ex); SIDL_REPORT(*_ex);
 EXIT:
  {
    sidl_BaseInterface throwaway_exception = NULL;
    if (cmp) sorting_Counter_deleteRef(cmp,&throwaway_exception);
    if (swp) sorting_Counter_deleteRef(swp, &throwaway_exception);
  }
    /* DO-NOT-DELETE splicer.end(sorting.Mergesort.sort) */
  }
}

/*
 * Return merge sorting.
 */

#undef __FUNC__
#define __FUNC__ "impl_sorting_Mergesort_getName"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_sorting_Mergesort_getName(
  /* in */ sorting_Mergesort self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sorting.Mergesort.getName) */
  return sidl_String_strdup("Merge sort");
    /* DO-NOT-DELETE splicer.end(sorting.Mergesort.getName) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

