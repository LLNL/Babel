// 
// File:          Inherit_K_Impl.cxx
// Symbol:        Inherit.K-v1.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for Inherit.K
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "Inherit_K_Impl.hxx"

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
// DO-NOT-DELETE splicer.begin(Inherit.K._includes)
// Insert-Code-Here {Inherit.K._includes} (additional includes or code)
// DO-NOT-DELETE splicer.end(Inherit.K._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
Inherit::K_impl::K_impl() : StubBase(reinterpret_cast< void*>(
  ::Inherit::K::_wrapObj(reinterpret_cast< void*>(this))),false) , _wrapped(
  true){ 
  // DO-NOT-DELETE splicer.begin(Inherit.K._ctor2)
  // Insert-Code-Here {Inherit.K._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(Inherit.K._ctor2)
}

// user defined constructor
void Inherit::K_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(Inherit.K._ctor)
  // Insert-Code-Here {Inherit.K._ctor} (constructor)
  // DO-NOT-DELETE splicer.end(Inherit.K._ctor)
}

// user defined destructor
void Inherit::K_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(Inherit.K._dtor)
  // Insert-Code-Here {Inherit.K._dtor} (destructor)
  // DO-NOT-DELETE splicer.end(Inherit.K._dtor)
}

// static class initializer
void Inherit::K_impl::_load() {
  // DO-NOT-DELETE splicer.begin(Inherit.K._load)
  // Insert-Code-Here {Inherit.K._load} (class initialization)
  // DO-NOT-DELETE splicer.end(Inherit.K._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Method:  a[2]
 */
::std::string
Inherit::K_impl::a_impl (
  /* in */int32_t i ) 
{
  // DO-NOT-DELETE splicer.begin(Inherit.K.a2)
  return "K.a2";
  // DO-NOT-DELETE splicer.end(Inherit.K.a2)
}

/**
 * Method:  a[]
 */
::std::string
Inherit::K_impl::a_impl () 

{
  // DO-NOT-DELETE splicer.begin(Inherit.K.a)
  return "K.a";
  // DO-NOT-DELETE splicer.end(Inherit.K.a)
}

/**
 * Method:  h[]
 */
::std::string
Inherit::K_impl::h_impl () 

{
  // DO-NOT-DELETE splicer.begin(Inherit.K.h)
  return "K.h";
  // DO-NOT-DELETE splicer.end(Inherit.K.h)
}

/**
 * Method:  k[]
 */
::std::string
Inherit::K_impl::k_impl () 

{
  // DO-NOT-DELETE splicer.begin(Inherit.K.k)
  return "K.k";
  // DO-NOT-DELETE splicer.end(Inherit.K.k)
}


// DO-NOT-DELETE splicer.begin(Inherit.K._misc)
// Insert-Code-Here {Inherit.K._misc} (miscellaneous code)
// DO-NOT-DELETE splicer.end(Inherit.K._misc)

