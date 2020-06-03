// 
// File:          sorting_SimpleCounter_Impl.cxx
// Symbol:        sorting.SimpleCounter-v0.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for sorting.SimpleCounter
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "sorting_SimpleCounter_Impl.hxx"

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
// DO-NOT-DELETE splicer.begin(sorting.SimpleCounter._includes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(sorting.SimpleCounter._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
sorting::SimpleCounter_impl::SimpleCounter_impl() : StubBase(reinterpret_cast< 
  void*>(::sorting::SimpleCounter::_wrapObj(reinterpret_cast< void*>(this))),
  false) , _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(sorting.SimpleCounter._ctor2)
  // Insert-Code-Here {sorting.SimpleCounter._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(sorting.SimpleCounter._ctor2)
}

// user defined constructor
void sorting::SimpleCounter_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(sorting.SimpleCounter._ctor)
  d_counter = 0;
  // DO-NOT-DELETE splicer.end(sorting.SimpleCounter._ctor)
}

// user defined destructor
void sorting::SimpleCounter_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(sorting.SimpleCounter._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(sorting.SimpleCounter._dtor)
}

// static class initializer
void sorting::SimpleCounter_impl::_load() {
  // DO-NOT-DELETE splicer.begin(sorting.SimpleCounter._load)
  // guaranteed to be called at most once before any other method in this class
  // DO-NOT-DELETE splicer.end(sorting.SimpleCounter._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Set the count to zero.
 */
void
sorting::SimpleCounter_impl::reset_impl () 

{
  // DO-NOT-DELETE splicer.begin(sorting.SimpleCounter.reset)
  d_counter = 0;
  // DO-NOT-DELETE splicer.end(sorting.SimpleCounter.reset)
}

/**
 * Return the current count.
 */
int32_t
sorting::SimpleCounter_impl::getCount_impl () 

{
  // DO-NOT-DELETE splicer.begin(sorting.SimpleCounter.getCount)
  return d_counter;
  // DO-NOT-DELETE splicer.end(sorting.SimpleCounter.getCount)
}

/**
 * Increment the count (i.e. add one).
 */
int32_t
sorting::SimpleCounter_impl::inc_impl () 

{
  // DO-NOT-DELETE splicer.begin(sorting.SimpleCounter.inc)
  return ++d_counter;
  // DO-NOT-DELETE splicer.end(sorting.SimpleCounter.inc)
}


// DO-NOT-DELETE splicer.begin(sorting.SimpleCounter._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(sorting.SimpleCounter._misc)

