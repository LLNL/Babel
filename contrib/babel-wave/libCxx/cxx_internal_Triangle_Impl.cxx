// 
// File:          cxx_internal_Triangle_Impl.cxx
// Symbol:        cxx.internal.Triangle-v1.0
// Symbol Type:   class
// Babel Version: 1.2.0
// Description:   Server-side implementation for cxx.internal.Triangle
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "cxx_internal_Triangle_Impl.hxx"

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
// DO-NOT-DELETE splicer.begin(cxx.internal.Triangle._includes)
// Insert-Code-Here {cxx.internal.Triangle._includes} (additional includes or code)
// DO-NOT-DELETE splicer.end(cxx.internal.Triangle._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
cxx::internal::Triangle_impl::Triangle_impl() : StubBase(reinterpret_cast< 
  void*>(::cxx::internal::Triangle::_wrapObj(reinterpret_cast< void*>(this))),
  false) , _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(cxx.internal.Triangle._ctor2)
  // Insert-Code-Here {cxx.internal.Triangle._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(cxx.internal.Triangle._ctor2)
}

// user defined constructor
void cxx::internal::Triangle_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(cxx.internal.Triangle._ctor)
  // Insert-Code-Here {cxx.internal.Triangle._ctor} (constructor)
  // DO-NOT-DELETE splicer.end(cxx.internal.Triangle._ctor)
}

// user defined destructor
void cxx::internal::Triangle_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(cxx.internal.Triangle._dtor)
  // Insert-Code-Here {cxx.internal.Triangle._dtor} (destructor)
  // DO-NOT-DELETE splicer.end(cxx.internal.Triangle._dtor)
}

// static class initializer
void cxx::internal::Triangle_impl::_load() {
  // DO-NOT-DELETE splicer.begin(cxx.internal.Triangle._load)
  // Insert-Code-Here {cxx.internal.Triangle._load} (class initialization)
  // DO-NOT-DELETE splicer.end(cxx.internal.Triangle._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Method:  init[]
 */
void
cxx::internal::Triangle_impl::init_impl (
  /* in */double x1,
  /* in */double y1,
  /* in */double x2,
  /* in */double y2,
  /* in */double x3,
  /* in */double y3 ) 
{
  // DO-NOT-DELETE splicer.begin(cxx.internal.Triangle.init)
  // Insert-Code-Here {cxx.internal.Triangle.init} (init method)
  // 
  // This method has not been implemented
  // 
  // DO-DELETE-WHEN-IMPLEMENTING exception.begin(cxx.internal.Triangle.init)
  ::sidl::NotImplementedException ex = ::sidl::NotImplementedException::_create();
  ex.setNote("This method has not been implemented");
  ex.add(__FILE__, __LINE__, "init");
  throw ex;
  // DO-DELETE-WHEN-IMPLEMENTING exception.end(cxx.internal.Triangle.init)
  // DO-NOT-DELETE splicer.end(cxx.internal.Triangle.init)
}

/**
 * Method:  inLocus[]
 */
bool
cxx::internal::Triangle_impl::inLocus_impl (
  /* in */double x,
  /* in */double y ) 
{
  // DO-NOT-DELETE splicer.begin(cxx.internal.Triangle.inLocus)
  // Insert-Code-Here {cxx.internal.Triangle.inLocus} (inLocus method)
  // 
  // This method has not been implemented
  // 
  // DO-DELETE-WHEN-IMPLEMENTING exception.begin(cxx.internal.Triangle.inLocus)
  ::sidl::NotImplementedException ex = ::sidl::NotImplementedException::_create();
  ex.setNote("This method has not been implemented");
  ex.add(__FILE__, __LINE__, "inLocus");
  throw ex;
  // DO-DELETE-WHEN-IMPLEMENTING exception.end(cxx.internal.Triangle.inLocus)
  // DO-NOT-DELETE splicer.end(cxx.internal.Triangle.inLocus)
}


// DO-NOT-DELETE splicer.begin(cxx.internal.Triangle._misc)
// Insert-Code-Here {cxx.internal.Triangle._misc} (miscellaneous code)
// DO-NOT-DELETE splicer.end(cxx.internal.Triangle._misc)

