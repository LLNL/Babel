// 
// File:          cxx_internal_Ellipse_Impl.cxx
// Symbol:        cxx.internal.Ellipse-v1.0
// Symbol Type:   class
// Babel Version: 1.2.0
// Description:   Server-side implementation for cxx.internal.Ellipse
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "cxx_internal_Ellipse_Impl.hxx"

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
// DO-NOT-DELETE splicer.begin(cxx.internal.Ellipse._includes)
// Insert-Code-Here {cxx.internal.Ellipse._includes} (additional includes or code)
// DO-NOT-DELETE splicer.end(cxx.internal.Ellipse._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
cxx::internal::Ellipse_impl::Ellipse_impl() : StubBase(reinterpret_cast< void*>(
  ::cxx::internal::Ellipse::_wrapObj(reinterpret_cast< void*>(this))),false) , 
  _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(cxx.internal.Ellipse._ctor2)
  // Insert-Code-Here {cxx.internal.Ellipse._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(cxx.internal.Ellipse._ctor2)
}

// user defined constructor
void cxx::internal::Ellipse_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(cxx.internal.Ellipse._ctor)
  // Insert-Code-Here {cxx.internal.Ellipse._ctor} (constructor)
  // DO-NOT-DELETE splicer.end(cxx.internal.Ellipse._ctor)
}

// user defined destructor
void cxx::internal::Ellipse_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(cxx.internal.Ellipse._dtor)
  // Insert-Code-Here {cxx.internal.Ellipse._dtor} (destructor)
  // DO-NOT-DELETE splicer.end(cxx.internal.Ellipse._dtor)
}

// static class initializer
void cxx::internal::Ellipse_impl::_load() {
  // DO-NOT-DELETE splicer.begin(cxx.internal.Ellipse._load)
  // Insert-Code-Here {cxx.internal.Ellipse._load} (class initialization)
  // DO-NOT-DELETE splicer.end(cxx.internal.Ellipse._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Method:  init[]
 */
void
cxx::internal::Ellipse_impl::init_impl (
  /* in */double center_x,
  /* in */double center_y,
  /* in */double x_radius,
  /* in */double y_radius ) 
{
  // DO-NOT-DELETE splicer.begin(cxx.internal.Ellipse.init)
  // Insert-Code-Here {cxx.internal.Ellipse.init} (init method)
  // 
  // This method has not been implemented
  // 
  // DO-DELETE-WHEN-IMPLEMENTING exception.begin(cxx.internal.Ellipse.init)
  ::sidl::NotImplementedException ex = ::sidl::NotImplementedException::_create();
  ex.setNote("This method has not been implemented");
  ex.add(__FILE__, __LINE__, "init");
  throw ex;
  // DO-DELETE-WHEN-IMPLEMENTING exception.end(cxx.internal.Ellipse.init)
  // DO-NOT-DELETE splicer.end(cxx.internal.Ellipse.init)
}

/**
 * Method:  inLocus[]
 */
bool
cxx::internal::Ellipse_impl::inLocus_impl (
  /* in */double x,
  /* in */double y ) 
{
  // DO-NOT-DELETE splicer.begin(cxx.internal.Ellipse.inLocus)
  // Insert-Code-Here {cxx.internal.Ellipse.inLocus} (inLocus method)
  // 
  // This method has not been implemented
  // 
  // DO-DELETE-WHEN-IMPLEMENTING exception.begin(cxx.internal.Ellipse.inLocus)
  ::sidl::NotImplementedException ex = ::sidl::NotImplementedException::_create();
  ex.setNote("This method has not been implemented");
  ex.add(__FILE__, __LINE__, "inLocus");
  throw ex;
  // DO-DELETE-WHEN-IMPLEMENTING exception.end(cxx.internal.Ellipse.inLocus)
  // DO-NOT-DELETE splicer.end(cxx.internal.Ellipse.inLocus)
}


// DO-NOT-DELETE splicer.begin(cxx.internal.Ellipse._misc)
// Insert-Code-Here {cxx.internal.Ellipse._misc} (miscellaneous code)
// DO-NOT-DELETE splicer.end(cxx.internal.Ellipse._misc)

