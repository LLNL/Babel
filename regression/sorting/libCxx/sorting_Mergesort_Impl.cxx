// 
// File:          sorting_Mergesort_Impl.cxx
// Symbol:        sorting.Mergesort-v0.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for sorting.Mergesort
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "sorting_Mergesort_Impl.hxx"

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
// DO-NOT-DELETE splicer.begin(sorting.Mergesort._includes)
static void
mergeLists(::sorting::Container  &elems,
           ::sorting::Comparator &comp,
           ::sorting::Counter    &cmp,
           ::sorting::Counter    &swp,
           int32_t          start,
           int32_t          mid,
           const int32_t    end)
{
  int32_t j;
  while ((start < mid) && (mid < end)) {
    cmp.inc();
    if (elems.compare(start, mid, comp) > 0) {
      /* move first element of upper list into place */
      for(j = mid;j > start; --j) {
	swp.inc();
	elems.swap(j, j - 1);
      }
      ++mid;
    }
    ++start;
  }
}

/**
 * end is one past the end
 */
static void
mergeSort(::sorting::Container  &elems,
          ::sorting::Comparator &comp,
          ::sorting::Counter    &cmp,
          ::sorting::Counter    &swp,
          const int32_t   start,
          const int32_t   end)
{
  if ((end - start) > 1) {
    int32_t mid = (start + end) >> 1;
    mergeSort(elems, comp, cmp, swp, start, mid);
    mergeSort(elems, comp, cmp, swp, mid, end);
    mergeLists(elems, comp, cmp, swp, start, mid, end);
  }
}
// DO-NOT-DELETE splicer.end(sorting.Mergesort._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
sorting::Mergesort_impl::Mergesort_impl() : StubBase(reinterpret_cast< void*>(
  ::sorting::Mergesort::_wrapObj(reinterpret_cast< void*>(this))),false) , 
  _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(sorting.Mergesort._ctor2)
  // Insert-Code-Here {sorting.Mergesort._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(sorting.Mergesort._ctor2)
}

// user defined constructor
void sorting::Mergesort_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(sorting.Mergesort._ctor)
  // add construction details here
  // DO-NOT-DELETE splicer.end(sorting.Mergesort._ctor)
}

// user defined destructor
void sorting::Mergesort_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(sorting.Mergesort._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(sorting.Mergesort._dtor)
}

// static class initializer
void sorting::Mergesort_impl::_load() {
  // DO-NOT-DELETE splicer.begin(sorting.Mergesort._load)
  // guaranteed to be called at most once before any other method in this class
  // DO-NOT-DELETE splicer.end(sorting.Mergesort._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Sort elements using Merge Sort.
 */
void
sorting::Mergesort_impl::sort_impl (
  /* in */::sorting::Container& elems,
  /* in */::sorting::Comparator& comp ) 
{
  // DO-NOT-DELETE splicer.begin(sorting.Mergesort.sort)
  const int32_t num = elems.getLength();
  ::sorting::Counter cmp = getCompareCounter();
  ::sorting::Counter swp = getSwapCounter();
  mergeSort(elems, comp, cmp, swp, 0, num);
  // DO-NOT-DELETE splicer.end(sorting.Mergesort.sort)
}

/**
 * Return merge sorting.
 */
::std::string
sorting::Mergesort_impl::getName_impl () 

{
  // DO-NOT-DELETE splicer.begin(sorting.Mergesort.getName)
  return "Merge sort";
  // DO-NOT-DELETE splicer.end(sorting.Mergesort.getName)
}


// DO-NOT-DELETE splicer.begin(sorting.Mergesort._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(sorting.Mergesort._misc)

