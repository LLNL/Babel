// 
// File:          Inherit_L_Impl.cxx
// Symbol:        Inherit.L-v1.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for Inherit.L
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "Inherit_L_Impl.hxx"

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
// DO-NOT-DELETE splicer.begin(Inherit.L._includes)
// Insert-Code-Here {Inherit.L._includes} (additional includes or code)
// DO-NOT-DELETE splicer.end(Inherit.L._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
Inherit::L_impl::L_impl() : StubBase(reinterpret_cast< void*>(
  ::Inherit::L::_wrapObj(reinterpret_cast< void*>(this))),false) , _wrapped(
  true){ 
  // DO-NOT-DELETE splicer.begin(Inherit.L._ctor2)
  // Insert-Code-Here {Inherit.L._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(Inherit.L._ctor2)
}

// user defined constructor
void Inherit::L_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(Inherit.L._ctor)
  // Insert-Code-Here {Inherit.L._ctor} (constructor)
  // DO-NOT-DELETE splicer.end(Inherit.L._ctor)
}

// user defined destructor
void Inherit::L_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(Inherit.L._dtor)
  // Insert-Code-Here {Inherit.L._dtor} (destructor)
  // DO-NOT-DELETE splicer.end(Inherit.L._dtor)
}

// static class initializer
void Inherit::L_impl::_load() {
  // DO-NOT-DELETE splicer.begin(Inherit.L._load)
  // Insert-Code-Here {Inherit.L._load} (class initialization)
  // DO-NOT-DELETE splicer.end(Inherit.L._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Method:  a[a]
 */
::std::string
Inherit::L_impl::a_impl () 

{
  // DO-NOT-DELETE splicer.begin(Inherit.L.aa)
  return "L.a";
  // DO-NOT-DELETE splicer.end(Inherit.L.aa)
}

/**
 * Method:  a[2]
 */
::std::string
Inherit::L_impl::a_impl (
  /* in */int32_t i ) 
{
  // DO-NOT-DELETE splicer.begin(Inherit.L.a2)
  return "L.a2";
  // DO-NOT-DELETE splicer.end(Inherit.L.a2)
}

/**
 * Method:  l[]
 */
::std::string
Inherit::L_impl::l_impl () 

{
  // DO-NOT-DELETE splicer.begin(Inherit.L.l)
  return "L.l";
  // DO-NOT-DELETE splicer.end(Inherit.L.l)
}


// DO-NOT-DELETE splicer.begin(Inherit.L._misc)
// Insert-Code-Here {Inherit.L._misc} (miscellaneous code)
// DO-NOT-DELETE splicer.end(Inherit.L._misc)

