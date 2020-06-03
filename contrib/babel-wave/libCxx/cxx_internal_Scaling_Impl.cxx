// 
// File:          cxx_internal_Scaling_Impl.cxx
// Symbol:        cxx.internal.Scaling-v1.0
// Symbol Type:   class
// Babel Version: 1.2.0
// Description:   Server-side implementation for cxx.internal.Scaling
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "cxx_internal_Scaling_Impl.hxx"

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
// DO-NOT-DELETE splicer.begin(cxx.internal.Scaling._includes)
#include "sidl_PreViolation.hxx"
// DO-NOT-DELETE splicer.end(cxx.internal.Scaling._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
cxx::internal::Scaling_impl::Scaling_impl() : StubBase(reinterpret_cast< void*>(
  ::cxx::internal::Scaling::_wrapObj(reinterpret_cast< void*>(this))),false) , 
  _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(cxx.internal.Scaling._ctor2)
  // Insert-Code-Here {cxx.internal.Scaling._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(cxx.internal.Scaling._ctor2)
}

// user defined constructor
void cxx::internal::Scaling_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(cxx.internal.Scaling._ctor)
  // Insert-Code-Here {cxx.internal.Scaling._ctor} (constructor)
  // DO-NOT-DELETE splicer.end(cxx.internal.Scaling._ctor)
}

// user defined destructor
void cxx::internal::Scaling_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(cxx.internal.Scaling._dtor)
  // Insert-Code-Here {cxx.internal.Scaling._dtor} (destructor)
  // DO-NOT-DELETE splicer.end(cxx.internal.Scaling._dtor)
}

// static class initializer
void cxx::internal::Scaling_impl::_load() {
  // DO-NOT-DELETE splicer.begin(cxx.internal.Scaling._load)
  // Insert-Code-Here {cxx.internal.Scaling._load} (class initialization)
  // DO-NOT-DELETE splicer.end(cxx.internal.Scaling._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Method:  init[]
 */
void
cxx::internal::Scaling_impl::init_impl (
  /* in */::wave2d::Shape& source,
  /* in */double scale_x,
  /* in */double scale_y ) 
{
  // DO-NOT-DELETE splicer.begin(cxx.internal.Scaling.init)
  sidl::PreViolation ex; 
  if ( scale_x == 0.0 ) { 
    ex = sidl::PreViolation::_create();
    ex.setNote("input error: scale_x cannot == 0.0");
    ex.add(__FILE__,__LINE__,"cxx::internal::Scaling_impl::init_impl()");
    throw ex;
  } if ( scale_y == 0.0 ) { 
    ex = sidl::PreViolation::_create();
    ex.setNote("input error: scale_y cannot == 0.0");
    ex.add(__FILE__,__LINE__,"cxx::internal::Scaling_impl::init_impl()");
    throw ex;
  }
  d_source = source;
  d_scale_x = scale_x;
  d_scale_y = scale_y;
  // DO-NOT-DELETE splicer.end(cxx.internal.Scaling.init)
}

/**
 * Method:  inLocus[]
 */
bool
cxx::internal::Scaling_impl::inLocus_impl (
  /* in */double x,
  /* in */double y ) 
{
  // DO-NOT-DELETE splicer.begin(cxx.internal.Scaling.inLocus)
  return d_source.inLocus( x/d_scale_x, y/d_scale_y );
  // DO-NOT-DELETE splicer.end(cxx.internal.Scaling.inLocus)
}


// DO-NOT-DELETE splicer.begin(cxx.internal.Scaling._misc)
// Insert-Code-Here {cxx.internal.Scaling._misc} (miscellaneous code)
// DO-NOT-DELETE splicer.end(cxx.internal.Scaling._misc)

