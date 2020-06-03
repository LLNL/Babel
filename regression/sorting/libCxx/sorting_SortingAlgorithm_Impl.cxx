// 
// File:          sorting_SortingAlgorithm_Impl.cxx
// Symbol:        sorting.SortingAlgorithm-v0.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for sorting.SortingAlgorithm
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "sorting_SortingAlgorithm_Impl.hxx"

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
// DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm._includes)
#include "sorting_SimpleCounter.hxx"
// DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm._includes)

// default constructor, not to be used!
// user defined constructor
void sorting::SortingAlgorithm_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm._ctor)
  d_cmp = ::sorting::SimpleCounter::_create();
  d_swp = ::sorting::SimpleCounter::_create();
  // DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm._ctor)
}

// user defined destructor
void sorting::SortingAlgorithm_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm._dtor)
}

// static class initializer
void sorting::SortingAlgorithm_impl::_load() {
  // DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm._load)
  // guaranteed to be called at most once before any other method in this class
  // DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Return the comparison counter.
 */
::sorting::Counter
sorting::SortingAlgorithm_impl::getCompareCounter_impl () 

{
  // DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm.getCompareCounter)
  return d_cmp;
  // DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm.getCompareCounter)
}

/**
 * Return the swap counter.
 */
::sorting::Counter
sorting::SortingAlgorithm_impl::getSwapCounter_impl () 

{
  // DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm.getSwapCounter)
  return d_swp;
  // DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm.getSwapCounter)
}

/**
 * Reset the comparison and swap counter.
 */
void
sorting::SortingAlgorithm_impl::reset_impl () 

{
  // DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm.reset)
  d_cmp.reset();
  d_swp.reset();
  // DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm.reset)
}


// DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm._misc)

