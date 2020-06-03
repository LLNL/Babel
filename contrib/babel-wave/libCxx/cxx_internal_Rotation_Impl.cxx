// 
// File:          cxx_internal_Rotation_Impl.cxx
// Symbol:        cxx.internal.Rotation-v1.0
// Symbol Type:   class
// Babel Version: 1.2.0
// Description:   Server-side implementation for cxx.internal.Rotation
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "cxx_internal_Rotation_Impl.hxx"

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
// DO-NOT-DELETE splicer.begin(cxx.internal.Rotation._includes)
// Insert-Code-Here {cxx.internal.Rotation._includes} (additional includes or code)
// DO-NOT-DELETE splicer.end(cxx.internal.Rotation._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
cxx::internal::Rotation_impl::Rotation_impl() : StubBase(reinterpret_cast< 
  void*>(::cxx::internal::Rotation::_wrapObj(reinterpret_cast< void*>(this))),
  false) , _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(cxx.internal.Rotation._ctor2)
  // Insert-Code-Here {cxx.internal.Rotation._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(cxx.internal.Rotation._ctor2)
}

// user defined constructor
void cxx::internal::Rotation_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(cxx.internal.Rotation._ctor)
  // Insert-Code-Here {cxx.internal.Rotation._ctor} (constructor)
  // DO-NOT-DELETE splicer.end(cxx.internal.Rotation._ctor)
}

// user defined destructor
void cxx::internal::Rotation_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(cxx.internal.Rotation._dtor)
  // Insert-Code-Here {cxx.internal.Rotation._dtor} (destructor)
  // DO-NOT-DELETE splicer.end(cxx.internal.Rotation._dtor)
}

// static class initializer
void cxx::internal::Rotation_impl::_load() {
  // DO-NOT-DELETE splicer.begin(cxx.internal.Rotation._load)
  // Insert-Code-Here {cxx.internal.Rotation._load} (class initialization)
  // DO-NOT-DELETE splicer.end(cxx.internal.Rotation._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Method:  init[]
 */
void
cxx::internal::Rotation_impl::init_impl (
  /* in */::wave2d::Shape& source,
  /* in */double angle ) 
{
  // DO-NOT-DELETE splicer.begin(cxx.internal.Rotation.init)
  d_source = source;
  d_angle = angle;
  d_cos = ::std::cos(-d_angle);
  d_sin = ::std::sin(-d_angle);
  // DO-NOT-DELETE splicer.end(cxx.internal.Rotation.init)
}

/**
 * Method:  inLocus[]
 */
bool
cxx::internal::Rotation_impl::inLocus_impl (
  /* in */double x,
  /* in */double y ) 
{
  // DO-NOT-DELETE splicer.begin(cxx.internal.Rotation.inLocus)
  double x_ = d_cos*x + d_sin*y;
  double y_ = -d_sin*x + d_cos*y; 
  return d_source.inLocus(x_,y_);
  // DO-NOT-DELETE splicer.end(cxx.internal.Rotation.inLocus)
}


// DO-NOT-DELETE splicer.begin(cxx.internal.Rotation._misc)
// Insert-Code-Here {cxx.internal.Rotation._misc} (miscellaneous code)
// DO-NOT-DELETE splicer.end(cxx.internal.Rotation._misc)

