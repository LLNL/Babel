// 
// File:          sorting_Heapsort_Impl.cxx
// Symbol:        sorting.Heapsort-v0.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for sorting.Heapsort
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "sorting_Heapsort_Impl.hxx"

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
// DO-NOT-DELETE splicer.begin(sorting.Heapsort._includes)
// Put additional includes or other arbitrary code here...
static void remakeHeap(::sorting::Container &elem,
                       ::sorting::Comparator &comp,
                       ::sorting::Counter &cmp,
                       ::sorting::Counter &swp,
                       const int32_t last,
                       int32_t first)
{
  const int32_t half = (last >> 1) - 1;
  int32_t child;
  while (first <= half) {
    child = first + first + 1;
    if ((child+1) < last) {
      cmp.inc();
      if (elem.compare(child, child+1, comp) < 0) ++child;
    }
    cmp.inc();
    if (elem.compare(first, child, comp) >= 0) break;
    swp.inc();
    elem.swap(first, child);
    first = child;
  }
}
// DO-NOT-DELETE splicer.end(sorting.Heapsort._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
sorting::Heapsort_impl::Heapsort_impl() : StubBase(reinterpret_cast< void*>(
  ::sorting::Heapsort::_wrapObj(reinterpret_cast< void*>(this))),false) , 
  _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(sorting.Heapsort._ctor2)
  // Insert-Code-Here {sorting.Heapsort._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(sorting.Heapsort._ctor2)
}

// user defined constructor
void sorting::Heapsort_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(sorting.Heapsort._ctor)
  // add construction details here
  // DO-NOT-DELETE splicer.end(sorting.Heapsort._ctor)
}

// user defined destructor
void sorting::Heapsort_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(sorting.Heapsort._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(sorting.Heapsort._dtor)
}

// static class initializer
void sorting::Heapsort_impl::_load() {
  // DO-NOT-DELETE splicer.begin(sorting.Heapsort._load)
  // guaranteed to be called at most once before any other method in this class
  // DO-NOT-DELETE splicer.end(sorting.Heapsort._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Sort elements using Heap Sort.
 */
void
sorting::Heapsort_impl::sort_impl (
  /* in */::sorting::Container& elems,
  /* in */::sorting::Comparator& comp ) 
{
  // DO-NOT-DELETE splicer.begin(sorting.Heapsort.sort)
  int32_t i;
  const int32_t num = elems.getLength();
  ::sorting::Counter cmp = getCompareCounter();
  ::sorting::Counter swp = getSwapCounter();
  /* make the heap */
  for(i = ((num/2) - 1); i >= 0; --i) {
    remakeHeap(elems, comp, cmp, swp, num, i);
  }
  /* put top of heap at back and remake the heap */
  i = num - 1;
  while (i > 0) {
    swp.inc();
    elems.swap(0, i);
    remakeHeap(elems, comp, cmp, swp, i--, 0);
  }
  // DO-NOT-DELETE splicer.end(sorting.Heapsort.sort)
}

/**
 * Return heap sorting.
 */
::std::string
sorting::Heapsort_impl::getName_impl () 

{
  // DO-NOT-DELETE splicer.begin(sorting.Heapsort.getName)
  return "Heap sort";
  // DO-NOT-DELETE splicer.end(sorting.Heapsort.getName)
}


// DO-NOT-DELETE splicer.begin(sorting.Heapsort._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(sorting.Heapsort._misc)

