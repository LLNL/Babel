// 
// File:          Overload_BClass_Impl.cxx
// Symbol:        Overload.BClass-v1.0
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for Overload.BClass
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "Overload_BClass_Impl.hxx"

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
// DO-NOT-DELETE splicer.begin(Overload.BClass._includes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(Overload.BClass._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
Overload::BClass_impl::BClass_impl() : StubBase(reinterpret_cast< void*>(
  ::Overload::BClass::_wrapObj(reinterpret_cast< void*>(this))),false) , 
  _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(Overload.BClass._ctor2)
  // Insert-Code-Here {Overload.BClass._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(Overload.BClass._ctor2)
}

// user defined constructor
void Overload::BClass_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(Overload.BClass._ctor)
  // DO-NOT-DELETE splicer.end(Overload.BClass._ctor)
}

// user defined destructor
void Overload::BClass_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(Overload.BClass._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(Overload.BClass._dtor)
}

// static class initializer
void Overload::BClass_impl::_load() {
  // DO-NOT-DELETE splicer.begin(Overload.BClass._load)
  // guaranteed to be called at most once before any other method in this class
  // DO-NOT-DELETE splicer.end(Overload.BClass._load)
}

// user defined static methods: (none)

// user defined non-static methods: (none)

// DO-NOT-DELETE splicer.begin(Overload.BClass._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(Overload.BClass._misc)

