// 
// File:          sorting_Integer_Impl.cxx
// Symbol:        sorting.Integer-v0.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for sorting.Integer
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "sorting_Integer_Impl.hxx"

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
// DO-NOT-DELETE splicer.begin(sorting.Integer._includes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(sorting.Integer._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
sorting::Integer_impl::Integer_impl() : StubBase(reinterpret_cast< void*>(
  ::sorting::Integer::_wrapObj(reinterpret_cast< void*>(this))),false) , 
  _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(sorting.Integer._ctor2)
  // Insert-Code-Here {sorting.Integer._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(sorting.Integer._ctor2)
}

// user defined constructor
void sorting::Integer_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(sorting.Integer._ctor)
  d_num = 0;
  // DO-NOT-DELETE splicer.end(sorting.Integer._ctor)
}

// user defined destructor
void sorting::Integer_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(sorting.Integer._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(sorting.Integer._dtor)
}

// static class initializer
void sorting::Integer_impl::_load() {
  // DO-NOT-DELETE splicer.begin(sorting.Integer._load)
  // guaranteed to be called at most once before any other method in this class
  // DO-NOT-DELETE splicer.end(sorting.Integer._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Method:  getValue[]
 */
int32_t
sorting::Integer_impl::getValue_impl () 

{
  // DO-NOT-DELETE splicer.begin(sorting.Integer.getValue)
  return d_num;
  // DO-NOT-DELETE splicer.end(sorting.Integer.getValue)
}

/**
 * Method:  setValue[]
 */
void
sorting::Integer_impl::setValue_impl (
  /* in */int32_t value ) 
{
  // DO-NOT-DELETE splicer.begin(sorting.Integer.setValue)
  d_num = value;
  // DO-NOT-DELETE splicer.end(sorting.Integer.setValue)
}


// DO-NOT-DELETE splicer.begin(sorting.Integer._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(sorting.Integer._misc)

