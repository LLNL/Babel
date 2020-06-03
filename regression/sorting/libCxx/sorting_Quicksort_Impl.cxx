// 
// File:          sorting_Quicksort_Impl.cxx
// Symbol:        sorting.Quicksort-v0.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for sorting.Quicksort
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "sorting_Quicksort_Impl.hxx"

// 
// Includes for all method dependencies.
// 
#ifndef included_sidl_BaseInterface_hxx
#include "sidl_BaseInterface.hxx"
#endif
#ifndef included_sidl_ClassInfo_hxx
#include "sidl_ClassInfo.hxx"
#endif
#ifndef included_sorting_Comparator_hxx
#include "sorting_Comparator.hxx"
#endif
#ifndef included_sorting_Container_hxx
#include "sorting_Container.hxx"
#endif
#ifndef included_sorting_Counter_hxx
#include "sorting_Counter.hxx"
#endif
#ifndef included_sidl_NotImplementedException_hxx
#include "sidl_NotImplementedException.hxx"
#endif
// DO-NOT-DELETE splicer.begin(sorting.Quicksort._includes)
/**
 * Choose the middle of the first, middle and last element of the
 * list.  For small lists, return the middle without checking.
 */
static int32_t
choosePivot(::sorting::Container  &elems,
            ::sorting::Comparator &comp,
            ::sorting::Counter    &cmp,
            int32_t           start,
            int32_t           end)
{
  int32_t pivot = (start + end) >> 1;
  if ((end - start) > 4) {
    int32_t mid = pivot;
    cmp.inc();
    if (elems.compare(start, mid, comp) <= 0) {
      cmp.inc();
      if (elems.compare(mid, end - 1, comp) > 0) {
        cmp.inc();
        if (elems.compare( start, end - 1, comp) < 0) {
          pivot = end - 1;
        }
        else {
          pivot = start;
        }
      }
    }
    else {
      cmp.inc();
      if (elems.compare( mid, end - 1, comp) < 0) {
        cmp.inc();
        if (elems.compare( start, end - 1, comp) > 0) {
          pivot = end - 1;
        }
        else {
          pivot = start;
        }
      }
    }
  }
  return pivot;
}

static void 
quickSort(::sorting::Container  &elems,
          ::sorting::Comparator &comp,
          ::sorting::Counter    &cmp,
          ::sorting::Counter    &swp,
          int32_t           start,
          int32_t           end)
{
  if ((end - start) > 1) {
    int32_t pivot = choosePivot(elems, comp, cmp, start, end);
    int32_t i = start;
    int32_t j = end;
    if (pivot != start) {
      swp.inc();
      elems.swap(start, pivot);
    }
    for(;;) {
      do {
        --j;
        cmp.inc();
      } while (elems.compare( start, j, comp) < 0);
      while (++i < j) {
        cmp.inc();
        if (elems.compare( start, i, comp) < 0) break;
      }
      if (i >= j) break;
      swp.inc();
      elems.swap(i, j);
    }
    if (j != start) {
      swp.inc();
      elems.swap(start, j);
    }
    quickSort(elems, comp, cmp, swp, start, j);
    quickSort(elems, comp, cmp, swp, j + 1, end);
  }
}
// DO-NOT-DELETE splicer.end(sorting.Quicksort._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
sorting::Quicksort_impl::Quicksort_impl() : StubBase(reinterpret_cast< void*>(
  ::sorting::Quicksort::_wrapObj(reinterpret_cast< void*>(this))),false) , 
  _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(sorting.Quicksort._ctor2)
  // Insert-Code-Here {sorting.Quicksort._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(sorting.Quicksort._ctor2)
}

// user defined constructor
void sorting::Quicksort_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(sorting.Quicksort._ctor)
  // add construction details here
  // DO-NOT-DELETE splicer.end(sorting.Quicksort._ctor)
}

// user defined destructor
void sorting::Quicksort_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(sorting.Quicksort._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(sorting.Quicksort._dtor)
}

// static class initializer
void sorting::Quicksort_impl::_load() {
  // DO-NOT-DELETE splicer.begin(sorting.Quicksort._load)
  // guaranteed to be called at most once before any other method in this class
  // DO-NOT-DELETE splicer.end(sorting.Quicksort._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Sort elements using Quick Sort.
 */
void
sorting::Quicksort_impl::sort_impl (
  /* in */::sorting::Container& elems,
  /* in */::sorting::Comparator& comp ) 
{
  // DO-NOT-DELETE splicer.begin(sorting.Quicksort.sort)
  const int32_t num = elems.getLength();
  ::sorting::Counter cmp = getCompareCounter();
  ::sorting::Counter swp = getSwapCounter();
  quickSort(elems, comp, cmp, swp, 0, num);
  // DO-NOT-DELETE splicer.end(sorting.Quicksort.sort)
}

/**
 * Return quick sorting.
 */
::std::string
sorting::Quicksort_impl::getName_impl () 

{
  // DO-NOT-DELETE splicer.begin(sorting.Quicksort.getName)
  return "Quick sort";
  // DO-NOT-DELETE splicer.end(sorting.Quicksort.getName)
}


// DO-NOT-DELETE splicer.begin(sorting.Quicksort._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(sorting.Quicksort._misc)

