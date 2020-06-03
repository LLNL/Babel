// 
// File:          sorting_CompInt_Impl.cxx
// Symbol:        sorting.CompInt-v0.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for sorting.CompInt
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "sorting_CompInt_Impl.hxx"

// 
// Includes for all method dependencies.
// 
#ifndef included_sidl_BaseInterface_hxx
#include "sidl_BaseInterface.hxx"
#endif
#ifndef included_sidl_ClassInfo_hxx
#include "sidl_ClassInfo.hxx"
#endif
#ifndef included_sidl_NotImplementedException_hxx
#include "sidl_NotImplementedException.hxx"
#endif
// DO-NOT-DELETE splicer.begin(sorting.CompInt._includes)
#include "sorting_Integer.hxx"
// DO-NOT-DELETE splicer.end(sorting.CompInt._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
sorting::CompInt_impl::CompInt_impl() : StubBase(reinterpret_cast< void*>(
  ::sorting::CompInt::_wrapObj(reinterpret_cast< void*>(this))),false) , 
  _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(sorting.CompInt._ctor2)
  // Insert-Code-Here {sorting.CompInt._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(sorting.CompInt._ctor2)
}

// user defined constructor
void sorting::CompInt_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(sorting.CompInt._ctor)
  d_increasing = true;
  // DO-NOT-DELETE splicer.end(sorting.CompInt._ctor)
}

// user defined destructor
void sorting::CompInt_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(sorting.CompInt._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(sorting.CompInt._dtor)
}

// static class initializer
void sorting::CompInt_impl::_load() {
  // DO-NOT-DELETE splicer.begin(sorting.CompInt._load)
  // guaranteed to be called at most once before any other method in this class
  // DO-NOT-DELETE splicer.end(sorting.CompInt._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * If increasing is true, this will cause the comparator to
 * report a normal definition of less than; otherwise, it will
 * reverse the normal ordering.
 */
void
sorting::CompInt_impl::setSortIncreasing_impl (
  /* in */bool increasing ) 
{
  // DO-NOT-DELETE splicer.begin(sorting.CompInt.setSortIncreasing)
  d_increasing = increasing;
  // DO-NOT-DELETE splicer.end(sorting.CompInt.setSortIncreasing)
}

/**
 * This method is used to define an ordering of objects.  This method
 * will return -1 if i1 < i2, 0 if i1 = i2; and 1 if i1 > i2.
 */
int32_t
sorting::CompInt_impl::compare_impl (
  /* in */::sidl::BaseInterface& i1,
  /* in */::sidl::BaseInterface& i2 ) 
{
  // DO-NOT-DELETE splicer.begin(sorting.CompInt.compare)
  int32_t result = 0;
  ::sorting::Integer int1 = ::sidl::babel_cast< ::sorting::Integer>(i1);
  ::sorting::Integer int2 = ::sidl::babel_cast< ::sorting::Integer>(i2);
  if (int1._not_nil() && int2._not_nil()) {
    const int32_t val1 = int1.getValue();
    const int32_t val2 = int2.getValue();
    if (val1 < val2) result = -1;
    if (val1 > val2) result = 1;
    if (!d_increasing) result = -result;
  }
  return result;
  // DO-NOT-DELETE splicer.end(sorting.CompInt.compare)
}


// DO-NOT-DELETE splicer.begin(sorting.CompInt._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(sorting.CompInt._misc)

