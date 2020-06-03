// 
// File:          Inherit_C_Impl.cxx
// Symbol:        Inherit.C-v1.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for Inherit.C
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "Inherit_C_Impl.hxx"

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
// DO-NOT-DELETE splicer.begin(Inherit.C._includes)
#include <string>
using namespace std;
// DO-NOT-DELETE splicer.end(Inherit.C._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
Inherit::C_impl::C_impl() : StubBase(reinterpret_cast< void*>(
  ::Inherit::C::_wrapObj(reinterpret_cast< void*>(this))),false) , _wrapped(
  true){ 
  // DO-NOT-DELETE splicer.begin(Inherit.C._ctor2)
  // Insert-Code-Here {Inherit.C._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(Inherit.C._ctor2)
}

// user defined constructor
void Inherit::C_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(Inherit.C._ctor)
  // add construction details here
  // DO-NOT-DELETE splicer.end(Inherit.C._ctor)
}

// user defined destructor
void Inherit::C_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(Inherit.C._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(Inherit.C._dtor)
}

// static class initializer
void Inherit::C_impl::_load() {
  // DO-NOT-DELETE splicer.begin(Inherit.C._load)
  // guaranteed to be called at most once before any other method in this class
  // DO-NOT-DELETE splicer.end(Inherit.C._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Method:  c[]
 */
::std::string
Inherit::C_impl::c_impl () 

{
  // DO-NOT-DELETE splicer.begin(Inherit.C.c)
  return "C.c";
  // DO-NOT-DELETE splicer.end(Inherit.C.c)
}


// DO-NOT-DELETE splicer.begin(Inherit.C._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(Inherit.C._misc)

