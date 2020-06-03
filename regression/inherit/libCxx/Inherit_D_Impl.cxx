// 
// File:          Inherit_D_Impl.cxx
// Symbol:        Inherit.D-v1.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for Inherit.D
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "Inherit_D_Impl.hxx"

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
// DO-NOT-DELETE splicer.begin(Inherit.D._includes)
#include <string>
using namespace std;
// DO-NOT-DELETE splicer.end(Inherit.D._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
Inherit::D_impl::D_impl() : StubBase(reinterpret_cast< void*>(
  ::Inherit::D::_wrapObj(reinterpret_cast< void*>(this))),false) , _wrapped(
  true){ 
  // DO-NOT-DELETE splicer.begin(Inherit.D._ctor2)
  // Insert-Code-Here {Inherit.D._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(Inherit.D._ctor2)
}

// user defined constructor
void Inherit::D_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(Inherit.D._ctor)
  // add construction details here
  // DO-NOT-DELETE splicer.end(Inherit.D._ctor)
}

// user defined destructor
void Inherit::D_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(Inherit.D._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(Inherit.D._dtor)
}

// static class initializer
void Inherit::D_impl::_load() {
  // DO-NOT-DELETE splicer.begin(Inherit.D._load)
  // guaranteed to be called at most once before any other method in this class
  // DO-NOT-DELETE splicer.end(Inherit.D._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Method:  a[]
 */
::std::string
Inherit::D_impl::a_impl () 

{
  // DO-NOT-DELETE splicer.begin(Inherit.D.a)
  return "D.a";
  // DO-NOT-DELETE splicer.end(Inherit.D.a)
}

/**
 * Method:  d[]
 */
::std::string
Inherit::D_impl::d_impl () 

{
  // DO-NOT-DELETE splicer.begin(Inherit.D.d)
  return "D.d";
  // DO-NOT-DELETE splicer.end(Inherit.D.d)
}


// DO-NOT-DELETE splicer.begin(Inherit.D._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(Inherit.D._misc)

