// 
// File:          Inherit_G_Impl.cxx
// Symbol:        Inherit.G-v1.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for Inherit.G
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "Inherit_G_Impl.hxx"

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
// DO-NOT-DELETE splicer.begin(Inherit.G._includes)
#include <string>
using namespace std;
// DO-NOT-DELETE splicer.end(Inherit.G._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
Inherit::G_impl::G_impl() : StubBase(reinterpret_cast< void*>(
  ::Inherit::G::_wrapObj(reinterpret_cast< void*>(this))),false) , _wrapped(
  true){ 
  // DO-NOT-DELETE splicer.begin(Inherit.G._ctor2)
  // Insert-Code-Here {Inherit.G._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(Inherit.G._ctor2)
}

// user defined constructor
void Inherit::G_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(Inherit.G._ctor)
  // add construction details here
  // DO-NOT-DELETE splicer.end(Inherit.G._ctor)
}

// user defined destructor
void Inherit::G_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(Inherit.G._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(Inherit.G._dtor)
}

// static class initializer
void Inherit::G_impl::_load() {
  // DO-NOT-DELETE splicer.begin(Inherit.G._load)
  // guaranteed to be called at most once before any other method in this class
  // DO-NOT-DELETE splicer.end(Inherit.G._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Method:  g[]
 */
::std::string
Inherit::G_impl::g_impl () 

{
  // DO-NOT-DELETE splicer.begin(Inherit.G.g)
  return "G.g";
  // DO-NOT-DELETE splicer.end(Inherit.G.g)
}


// DO-NOT-DELETE splicer.begin(Inherit.G._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(Inherit.G._misc)

