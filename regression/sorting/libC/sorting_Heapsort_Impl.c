/*
 * File:          sorting_Heapsort_Impl.c
 * Symbol:        sorting.Heapsort-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for sorting.Heapsort
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "sorting.Heapsort" (version 0.1)
 * 
 * Heap sort
 */

#include "sorting_Heapsort_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(sorting.Heapsort._includes) */
#include "sidl_String.h"
#include "sorting_Container.h"

static void remakeHeap(sorting_Container elem,
                       sorting_Comparator comp,
                       sorting_Counter cmp,
                       sorting_Counter swp,
                       const int32_t last,
                       int32_t first)
{
  sidl_BaseInterface throwaway_exception;
  const int32_t half = (last >> 1) - 1;
  int32_t child;
  while (first <= half) {
    child = first + first + 1;
    if ((child+1) < last) {
      sorting_Counter_inc(cmp, &throwaway_exception);
      if (sorting_Container_compare(elem, child, child+1, comp, &throwaway_exception) < 0) ++child;
    }
    sorting_Counter_inc(cmp, &throwaway_exception);
    if (sorting_Container_compare(elem,first, child,comp, &throwaway_exception) >= 0) break;
    sorting_Counter_inc(swp, &throwaway_exception);
    sorting_Container_swap(elem, first, child, &throwaway_exception);
    first = child;
  }
}
/* DO-NOT-DELETE splicer.end(sorting.Heapsort._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_sorting_Heapsort__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sorting_Heapsort__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sorting.Heapsort._load) */
  /* Insert the implementation of the static class initializer method here... */
    /* DO-NOT-DELETE splicer.end(sorting.Heapsort._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_sorting_Heapsort__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sorting_Heapsort__ctor(
  /* in */ sorting_Heapsort self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sorting.Heapsort._ctor) */
  /* Insert the implementation of the constructor method here... */
    /* DO-NOT-DELETE splicer.end(sorting.Heapsort._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_sorting_Heapsort__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sorting_Heapsort__ctor2(
  /* in */ sorting_Heapsort self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sorting.Heapsort._ctor2) */
  /* Insert-Code-Here {sorting.Heapsort._ctor2} (special constructor method) */
    /* DO-NOT-DELETE splicer.end(sorting.Heapsort._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_sorting_Heapsort__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sorting_Heapsort__dtor(
  /* in */ sorting_Heapsort self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sorting.Heapsort._dtor) */
  /* Insert the implementation of the destructor method here... */
    /* DO-NOT-DELETE splicer.end(sorting.Heapsort._dtor) */
  }
}

/*
 * Sort elements using Heap Sort.
 */

#undef __FUNC__
#define __FUNC__ "impl_sorting_Heapsort_sort"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sorting_Heapsort_sort(
  /* in */ sorting_Heapsort self,
  /* in */ sorting_Container elems,
  /* in */ sorting_Comparator comp,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sorting.Heapsort.sort) */
  int32_t i;
  const int32_t num = sorting_Container_getLength(elems, _ex);
  sorting_Counter cmp = sorting_Heapsort_getCompareCounter(self, _ex);
  sorting_Counter swp = sorting_Heapsort_getSwapCounter(self, _ex);
  /* make the heap */
  for(i = ((num/2) - 1); i >= 0; --i) {
    remakeHeap(elems, comp, cmp, swp, num, i);
  }
  /* put top of heap at back and remake the heap */
  i = num - 1;
  while (i > 0) {
    sorting_Counter_inc(swp, _ex);
    sorting_Container_swap(elems, 0, i, _ex);
    remakeHeap(elems, comp, cmp, swp, i--, 0);
  }
  sorting_Counter_deleteRef(cmp, _ex);
  sorting_Counter_deleteRef(swp, _ex);
    /* DO-NOT-DELETE splicer.end(sorting.Heapsort.sort) */
  }
}

/*
 * Return heap sorting.
 */

#undef __FUNC__
#define __FUNC__ "impl_sorting_Heapsort_getName"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_sorting_Heapsort_getName(
  /* in */ sorting_Heapsort self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sorting.Heapsort.getName) */
  return sidl_String_strdup("Heap sort");
    /* DO-NOT-DELETE splicer.end(sorting.Heapsort.getName) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

