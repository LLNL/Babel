// 
// File:          Inherit_F_Impl.cxx
// Symbol:        Inherit.F-v1.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for Inherit.F
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "Inherit_F_Impl.hxx"

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
// DO-NOT-DELETE splicer.begin(Inherit.F._includes)
#include <string>
using namespace std;
// DO-NOT-DELETE splicer.end(Inherit.F._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
Inherit::F_impl::F_impl() : StubBase(reinterpret_cast< void*>(
  ::Inherit::F::_wrapObj(reinterpret_cast< void*>(this))),false) , _wrapped(
  true){ 
  // DO-NOT-DELETE splicer.begin(Inherit.F._ctor2)
  // Insert-Code-Here {Inherit.F._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(Inherit.F._ctor2)
}

// user defined constructor
void Inherit::F_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(Inherit.F._ctor)
  // add construction details here
  // DO-NOT-DELETE splicer.end(Inherit.F._ctor)
}

// user defined destructor
void Inherit::F_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(Inherit.F._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(Inherit.F._dtor)
}

// static class initializer
void Inherit::F_impl::_load() {
  // DO-NOT-DELETE splicer.begin(Inherit.F._load)
  // guaranteed to be called at most once before any other method in this class
  // DO-NOT-DELETE splicer.end(Inherit.F._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Method:  f[]
 */
::std::string
Inherit::F_impl::f_impl () 

{
  // DO-NOT-DELETE splicer.begin(Inherit.F.f)
  return "F.f";
  // DO-NOT-DELETE splicer.end(Inherit.F.f)
}

/**
 * Method:  a[]
 */
::std::string
Inherit::F_impl::a_impl () 

{
  // DO-NOT-DELETE splicer.begin(Inherit.F.a)
  return "F.a";
  // DO-NOT-DELETE splicer.end(Inherit.F.a)
}

/**
 * Method:  b[]
 */
::std::string
Inherit::F_impl::b_impl () 

{
  // DO-NOT-DELETE splicer.begin(Inherit.F.b)
  return "F.b";
  // DO-NOT-DELETE splicer.end(Inherit.F.b)
}


// DO-NOT-DELETE splicer.begin(Inherit.F._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(Inherit.F._misc)

