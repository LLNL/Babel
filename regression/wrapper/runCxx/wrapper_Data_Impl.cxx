// 
// File:          wrapper_Data_Impl.cxx
// Symbol:        wrapper.Data-v1.0
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for wrapper.Data
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "wrapper_Data_Impl.hxx"

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
// DO-NOT-DELETE splicer.begin(wrapper.Data._includes)
// Insert-Code-Here {wrapper.Data._includes} (additional includes or code)
// DO-NOT-DELETE splicer.end(wrapper.Data._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
wrapper::Data_impl::Data_impl() : StubBase(reinterpret_cast< void*>(
  ::wrapper::Data::_wrapObj(reinterpret_cast< void*>(this))),false) , _wrapped(
  true){ 
  // DO-NOT-DELETE splicer.begin(wrapper.Data._ctor2)
  d_ctorTest = "ctor was run";
  // DO-NOT-DELETE splicer.end(wrapper.Data._ctor2)
}

// user defined constructor
void wrapper::Data_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(wrapper.Data._ctor)
  // Insert-Code-Here {wrapper.Data._ctor} (constructor)
  // DO-NOT-DELETE splicer.end(wrapper.Data._ctor)
}

// user defined destructor
void wrapper::Data_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(wrapper.Data._dtor)
  // Insert-Code-Here {wrapper.Data._dtor} (destructor)
  // DO-NOT-DELETE splicer.end(wrapper.Data._dtor)
}

// static class initializer
void wrapper::Data_impl::_load() {
  // DO-NOT-DELETE splicer.begin(wrapper.Data._load)
  // Insert-Code-Here {wrapper.Data._load} (class initialization)
  // DO-NOT-DELETE splicer.end(wrapper.Data._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Method:  setString[]
 */
void
wrapper::Data_impl::setString_impl (
  /* in */const ::std::string& s ) 
{
  // DO-NOT-DELETE splicer.begin(wrapper.Data.setString)
  d_string = "Hello World!";
  // DO-NOT-DELETE splicer.end(wrapper.Data.setString)
}

/**
 * Method:  setInt[]
 */
void
wrapper::Data_impl::setInt_impl (
  /* in */int32_t i ) 
{
  // DO-NOT-DELETE splicer.begin(wrapper.Data.setInt)
  d_int = 3;
  // DO-NOT-DELETE splicer.end(wrapper.Data.setInt)
}


// DO-NOT-DELETE splicer.begin(wrapper.Data._misc)
// Insert-Code-Here {wrapper.Data._misc} (miscellaneous code)
// DO-NOT-DELETE splicer.end(wrapper.Data._misc)

