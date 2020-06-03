// 
// File:          cxx_internal_Rectangle_Impl.cxx
// Symbol:        cxx.internal.Rectangle-v1.0
// Symbol Type:   class
// Babel Version: 1.2.0
// Description:   Server-side implementation for cxx.internal.Rectangle
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "cxx_internal_Rectangle_Impl.hxx"

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
// DO-NOT-DELETE splicer.begin(cxx.internal.Rectangle._includes)
#include <cmath>
// DO-NOT-DELETE splicer.end(cxx.internal.Rectangle._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
cxx::internal::Rectangle_impl::Rectangle_impl() : StubBase(reinterpret_cast< 
  void*>(::cxx::internal::Rectangle::_wrapObj(reinterpret_cast< void*>(this))),
  false) , _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(cxx.internal.Rectangle._ctor2)
  // Insert-Code-Here {cxx.internal.Rectangle._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(cxx.internal.Rectangle._ctor2)
}

// user defined constructor
void cxx::internal::Rectangle_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(cxx.internal.Rectangle._ctor)
  // Insert-Code-Here {cxx.internal.Rectangle._ctor} (constructor)
  // DO-NOT-DELETE splicer.end(cxx.internal.Rectangle._ctor)
}

// user defined destructor
void cxx::internal::Rectangle_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(cxx.internal.Rectangle._dtor)
  // Insert-Code-Here {cxx.internal.Rectangle._dtor} (destructor)
  // DO-NOT-DELETE splicer.end(cxx.internal.Rectangle._dtor)
}

// static class initializer
void cxx::internal::Rectangle_impl::_load() {
  // DO-NOT-DELETE splicer.begin(cxx.internal.Rectangle._load)
  // Insert-Code-Here {cxx.internal.Rectangle._load} (class initialization)
  // DO-NOT-DELETE splicer.end(cxx.internal.Rectangle._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Method:  init[]
 */
void
cxx::internal::Rectangle_impl::init_impl (
  /* in */double x1,
  /* in */double y1,
  /* in */double x2,
  /* in */double y2 ) 
{
  // DO-NOT-DELETE splicer.begin(cxx.internal.Rectangle.init)
  d_minx = ::std::min(x1,x2);
  d_miny = ::std::min(y1,y2);
  d_maxx = ::std::max(x1,x2);
  d_maxy = ::std::max(y1,y2);
  // DO-NOT-DELETE splicer.end(cxx.internal.Rectangle.init)
}

/**
 * Method:  inLocus[]
 */
bool
cxx::internal::Rectangle_impl::inLocus_impl (
  /* in */double x,
  /* in */double y ) 
{
  // DO-NOT-DELETE splicer.begin(cxx.internal.Rectangle.inLocus)
  return ( d_minx <= x ) && ( d_maxx >= x ) && 
    ( d_miny <= y ) && ( d_maxy >= y );
  // DO-NOT-DELETE splicer.end(cxx.internal.Rectangle.inLocus)
}


// DO-NOT-DELETE splicer.begin(cxx.internal.Rectangle._misc)
// Insert-Code-Here {cxx.internal.Rectangle._misc} (miscellaneous code)
// DO-NOT-DELETE splicer.end(cxx.internal.Rectangle._misc)

