// 
// File:          cxx_internal_Inversion_Impl.cxx
// Symbol:        cxx.internal.Inversion-v1.0
// Symbol Type:   class
// Babel Version: 1.2.0
// Description:   Server-side implementation for cxx.internal.Inversion
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "cxx_internal_Inversion_Impl.hxx"

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
// DO-NOT-DELETE splicer.begin(cxx.internal.Inversion._includes)
// Insert-Code-Here {cxx.internal.Inversion._includes} (additional includes or code)
// DO-NOT-DELETE splicer.end(cxx.internal.Inversion._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
cxx::internal::Inversion_impl::Inversion_impl() : StubBase(reinterpret_cast< 
  void*>(::cxx::internal::Inversion::_wrapObj(reinterpret_cast< void*>(this))),
  false) , _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(cxx.internal.Inversion._ctor2)
  // Insert-Code-Here {cxx.internal.Inversion._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(cxx.internal.Inversion._ctor2)
}

// user defined constructor
void cxx::internal::Inversion_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(cxx.internal.Inversion._ctor)
  // Insert-Code-Here {cxx.internal.Inversion._ctor} (constructor)
  // DO-NOT-DELETE splicer.end(cxx.internal.Inversion._ctor)
}

// user defined destructor
void cxx::internal::Inversion_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(cxx.internal.Inversion._dtor)
  // Insert-Code-Here {cxx.internal.Inversion._dtor} (destructor)
  // DO-NOT-DELETE splicer.end(cxx.internal.Inversion._dtor)
}

// static class initializer
void cxx::internal::Inversion_impl::_load() {
  // DO-NOT-DELETE splicer.begin(cxx.internal.Inversion._load)
  // Insert-Code-Here {cxx.internal.Inversion._load} (class initialization)
  // DO-NOT-DELETE splicer.end(cxx.internal.Inversion._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Method:  init[]
 */
void
cxx::internal::Inversion_impl::init_impl (
  /* in */::wave2d::Shape& source ) 
{
  // DO-NOT-DELETE splicer.begin(cxx.internal.Inversion.init)
  d_source = source;
  // DO-NOT-DELETE splicer.end(cxx.internal.Inversion.init)
}

/**
 * Method:  inLocus[]
 */
bool
cxx::internal::Inversion_impl::inLocus_impl (
  /* in */double x,
  /* in */double y ) 
{
  // DO-NOT-DELETE splicer.begin(cxx.internal.Inversion.inLocus)
  return ! d_source.inLocus(x,y);
  // DO-NOT-DELETE splicer.end(cxx.internal.Inversion.inLocus)
}


// DO-NOT-DELETE splicer.begin(cxx.internal.Inversion._misc)
// Insert-Code-Here {cxx.internal.Inversion._misc} (miscellaneous code)
// DO-NOT-DELETE splicer.end(cxx.internal.Inversion._misc)

