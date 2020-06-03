// 
// File:          Inherit_I_Impl.cxx
// Symbol:        Inherit.I-v1.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for Inherit.I
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "Inherit_I_Impl.hxx"

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
// DO-NOT-DELETE splicer.begin(Inherit.I._includes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(Inherit.I._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
Inherit::I_impl::I_impl() : StubBase(reinterpret_cast< void*>(
  ::Inherit::I::_wrapObj(reinterpret_cast< void*>(this))),false) , _wrapped(
  true){ 
  // DO-NOT-DELETE splicer.begin(Inherit.I._ctor2)
  // Insert-Code-Here {Inherit.I._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(Inherit.I._ctor2)
}

// user defined constructor
void Inherit::I_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(Inherit.I._ctor)
  // add construction details here
  // DO-NOT-DELETE splicer.end(Inherit.I._ctor)
}

// user defined destructor
void Inherit::I_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(Inherit.I._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(Inherit.I._dtor)
}

// static class initializer
void Inherit::I_impl::_load() {
  // DO-NOT-DELETE splicer.begin(Inherit.I._load)
  // guaranteed to be called at most once before any other method in this class
  // DO-NOT-DELETE splicer.end(Inherit.I._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Method:  a[]
 */
::std::string
Inherit::I_impl::a_impl () 

{
  // DO-NOT-DELETE splicer.begin(Inherit.I.a)
  return "I.a";
  // DO-NOT-DELETE splicer.end(Inherit.I.a)
}

/**
 * Method:  h[]
 */
::std::string
Inherit::I_impl::h_impl () 

{
  // DO-NOT-DELETE splicer.begin(Inherit.I.h)
  return "I.h";
  // DO-NOT-DELETE splicer.end(Inherit.I.h)
}


// DO-NOT-DELETE splicer.begin(Inherit.I._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(Inherit.I._misc)

