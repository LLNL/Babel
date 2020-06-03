// 
// File:          cxx_internal_Unification_Impl.cxx
// Symbol:        cxx.internal.Unification-v1.0
// Symbol Type:   class
// Babel Version: 1.2.0
// Description:   Server-side implementation for cxx.internal.Unification
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "cxx_internal_Unification_Impl.hxx"

// 
// Includes for all method dependencies.
// 
#ifndef included_sidl_BaseInterface_hxx
#include "sidl_BaseInterface.hxx"
#endif
#ifndef included_sidl_ClassInfo_hxx
#include "sidl_ClassInfo.hxx"
#endif
#ifndef included_wave2d_Shape_hxx
#include "wave2d_Shape.hxx"
#endif
#ifndef included_sidl_NotImplementedException_hxx
#include "sidl_NotImplementedException.hxx"
#endif
// DO-NOT-DELETE splicer.begin(cxx.internal.Unification._includes)
// Insert-Code-Here {cxx.internal.Unification._includes} (additional includes or code)
// DO-NOT-DELETE splicer.end(cxx.internal.Unification._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
cxx::internal::Unification_impl::Unification_impl() : StubBase(
  reinterpret_cast< void*>(::cxx::internal::Unification::_wrapObj(
  reinterpret_cast< void*>(this))),false) , _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(cxx.internal.Unification._ctor2)
  // Insert-Code-Here {cxx.internal.Unification._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(cxx.internal.Unification._ctor2)
}

// user defined constructor
void cxx::internal::Unification_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(cxx.internal.Unification._ctor)
  // Insert-Code-Here {cxx.internal.Unification._ctor} (constructor)
  // DO-NOT-DELETE splicer.end(cxx.internal.Unification._ctor)
}

// user defined destructor
void cxx::internal::Unification_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(cxx.internal.Unification._dtor)
  // Insert-Code-Here {cxx.internal.Unification._dtor} (destructor)
  // DO-NOT-DELETE splicer.end(cxx.internal.Unification._dtor)
}

// static class initializer
void cxx::internal::Unification_impl::_load() {
  // DO-NOT-DELETE splicer.begin(cxx.internal.Unification._load)
  // Insert-Code-Here {cxx.internal.Unification._load} (class initialization)
  // DO-NOT-DELETE splicer.end(cxx.internal.Unification._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Method:  init[]
 */
void
cxx::internal::Unification_impl::init_impl (
  /* in */::wave2d::Shape& first,
  /* in */::wave2d::Shape& second ) 
{
  // DO-NOT-DELETE splicer.begin(cxx.internal.Unification.init)
  d_source1 = first;
  d_source2 = second;
  // DO-NOT-DELETE splicer.end(cxx.internal.Unification.init)
}

/**
 * Method:  inLocus[]
 */
bool
cxx::internal::Unification_impl::inLocus_impl (
  /* in */double x,
  /* in */double y ) 
{
  // DO-NOT-DELETE splicer.begin(cxx.internal.Unification.inLocus)
  return d_source1.inLocus(x,y) && d_source2.inLocus(x,y);
  // DO-NOT-DELETE splicer.end(cxx.internal.Unification.inLocus)
}


// DO-NOT-DELETE splicer.begin(cxx.internal.Unification._misc)
// Insert-Code-Here {cxx.internal.Unification._misc} (miscellaneous code)
// DO-NOT-DELETE splicer.end(cxx.internal.Unification._misc)
