// 
// File:          Overload_AClass_Impl.cxx
// Symbol:        Overload.AClass-v1.0
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for Overload.AClass
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "Overload_AClass_Impl.hxx"

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
// DO-NOT-DELETE splicer.begin(Overload.AClass._includes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(Overload.AClass._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
Overload::AClass_impl::AClass_impl() : StubBase(reinterpret_cast< void*>(
  ::Overload::AClass::_wrapObj(reinterpret_cast< void*>(this))),false) , 
  _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(Overload.AClass._ctor2)
  // Insert-Code-Here {Overload.AClass._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(Overload.AClass._ctor2)
}

// user defined constructor
void Overload::AClass_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(Overload.AClass._ctor)
  // add construction details here
  // DO-NOT-DELETE splicer.end(Overload.AClass._ctor)
}

// user defined destructor
void Overload::AClass_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(Overload.AClass._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(Overload.AClass._dtor)
}

// static class initializer
void Overload::AClass_impl::_load() {
  // DO-NOT-DELETE splicer.begin(Overload.AClass._load)
  // guaranteed to be called at most once before any other method in this class
  // DO-NOT-DELETE splicer.end(Overload.AClass._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Method:  getValue[]
 */
int32_t
Overload::AClass_impl::getValue_impl () 

{
  // DO-NOT-DELETE splicer.begin(Overload.AClass.getValue)
  return 2;
  // DO-NOT-DELETE splicer.end(Overload.AClass.getValue)
}


// DO-NOT-DELETE splicer.begin(Overload.AClass._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(Overload.AClass._misc)

