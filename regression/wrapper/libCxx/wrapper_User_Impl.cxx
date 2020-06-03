// 
// File:          wrapper_User_Impl.cxx
// Symbol:        wrapper.User-v1.0
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for wrapper.User
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "wrapper_User_Impl.hxx"

// 
// Includes for all method dependencies.
// 
#ifndef included_sidl_BaseInterface_hxx
#include "sidl_BaseInterface.hxx"
#endif
#ifndef included_sidl_ClassInfo_hxx
#include "sidl_ClassInfo.hxx"
#endif
#ifndef included_wrapper_Data_hxx
#include "wrapper_Data.hxx"
#endif
#ifndef included_sidl_NotImplementedException_hxx
#include "sidl_NotImplementedException.hxx"
#endif
// DO-NOT-DELETE splicer.begin(wrapper.User._includes)
// Insert-Code-Here {wrapper.User._includes} (additional includes or code)
// DO-NOT-DELETE splicer.end(wrapper.User._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
wrapper::User_impl::User_impl() : StubBase(reinterpret_cast< void*>(
  ::wrapper::User::_wrapObj(reinterpret_cast< void*>(this))),false) , _wrapped(
  true){ 
  // DO-NOT-DELETE splicer.begin(wrapper.User._ctor2)
  // Insert-Code-Here {wrapper.User._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(wrapper.User._ctor2)
}

// user defined constructor
void wrapper::User_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(wrapper.User._ctor)
  // Insert-Code-Here {wrapper.User._ctor} (constructor)
  // DO-NOT-DELETE splicer.end(wrapper.User._ctor)
}

// user defined destructor
void wrapper::User_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(wrapper.User._dtor)
  // Insert-Code-Here {wrapper.User._dtor} (destructor)
  // DO-NOT-DELETE splicer.end(wrapper.User._dtor)
}

// static class initializer
void wrapper::User_impl::_load() {
  // DO-NOT-DELETE splicer.begin(wrapper.User._load)
  // Insert-Code-Here {wrapper.User._load} (class initialization)
  // DO-NOT-DELETE splicer.end(wrapper.User._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Method:  accept[]
 */
void
wrapper::User_impl::accept_impl (
  /* in */::wrapper::Data& data ) 
{
  // DO-NOT-DELETE splicer.begin(wrapper.User.accept)
  data.setString("Hello World!");
  data.setInt(3);
  return;
  // DO-NOT-DELETE splicer.end(wrapper.User.accept)
}


// DO-NOT-DELETE splicer.begin(wrapper.User._misc)
// Insert-Code-Here {wrapper.User._misc} (miscellaneous code)
// DO-NOT-DELETE splicer.end(wrapper.User._misc)

