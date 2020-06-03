// 
// File:          Inherit_E_Impl.cxx
// Symbol:        Inherit.E-v1.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for Inherit.E
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "Inherit_E_Impl.hxx"

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
// DO-NOT-DELETE splicer.begin(Inherit.E._includes)
#include <string>
using namespace std;
// DO-NOT-DELETE splicer.end(Inherit.E._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
Inherit::E_impl::E_impl() : StubBase(reinterpret_cast< void*>(
  ::Inherit::E::_wrapObj(reinterpret_cast< void*>(this))),false) , _wrapped(
  true){ 
  // DO-NOT-DELETE splicer.begin(Inherit.E._ctor2)
  // Insert-Code-Here {Inherit.E._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(Inherit.E._ctor2)
}

// user defined constructor
void Inherit::E_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(Inherit.E._ctor)
  // add construction details here
  // DO-NOT-DELETE splicer.end(Inherit.E._ctor)
}

// user defined destructor
void Inherit::E_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(Inherit.E._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(Inherit.E._dtor)
}

// static class initializer
void Inherit::E_impl::_load() {
  // DO-NOT-DELETE splicer.begin(Inherit.E._load)
  // guaranteed to be called at most once before any other method in this class
  // DO-NOT-DELETE splicer.end(Inherit.E._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Method:  e[]
 */
::std::string
Inherit::E_impl::e_impl () 

{
  // DO-NOT-DELETE splicer.begin(Inherit.E.e)
  return "E.e";
  // DO-NOT-DELETE splicer.end(Inherit.E.e)
}


// DO-NOT-DELETE splicer.begin(Inherit.E._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(Inherit.E._misc)

