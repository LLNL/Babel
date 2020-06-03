// 
// File:          cxx_internal_Reflection_Impl.cxx
// Symbol:        cxx.internal.Reflection-v1.0
// Symbol Type:   class
// Babel Version: 1.2.0
// Description:   Server-side implementation for cxx.internal.Reflection
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "cxx_internal_Reflection_Impl.hxx"

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
// DO-NOT-DELETE splicer.begin(cxx.internal.Reflection._includes)
// Insert-Code-Here {cxx.internal.Reflection._includes} (additional includes or code)
// DO-NOT-DELETE splicer.end(cxx.internal.Reflection._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
cxx::internal::Reflection_impl::Reflection_impl() : StubBase(reinterpret_cast< 
  void*>(::cxx::internal::Reflection::_wrapObj(reinterpret_cast< void*>(this))),
  false) , _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(cxx.internal.Reflection._ctor2)
  // Insert-Code-Here {cxx.internal.Reflection._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(cxx.internal.Reflection._ctor2)
}

// user defined constructor
void cxx::internal::Reflection_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(cxx.internal.Reflection._ctor)
  // Insert-Code-Here {cxx.internal.Reflection._ctor} (constructor)
  // DO-NOT-DELETE splicer.end(cxx.internal.Reflection._ctor)
}

// user defined destructor
void cxx::internal::Reflection_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(cxx.internal.Reflection._dtor)
  // Insert-Code-Here {cxx.internal.Reflection._dtor} (destructor)
  // DO-NOT-DELETE splicer.end(cxx.internal.Reflection._dtor)
}

// static class initializer
void cxx::internal::Reflection_impl::_load() {
  // DO-NOT-DELETE splicer.begin(cxx.internal.Reflection._load)
  // Insert-Code-Here {cxx.internal.Reflection._load} (class initialization)
  // DO-NOT-DELETE splicer.end(cxx.internal.Reflection._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Method:  init[]
 */
void
cxx::internal::Reflection_impl::init_impl (
  /* in */::wave2d::Shape& source,
  /* in */bool isXReflect ) 
{
  // DO-NOT-DELETE splicer.begin(cxx.internal.Reflection.init)
  // Insert-Code-Here {cxx.internal.Reflection.init} (init method)
  // 
  // This method has not been implemented
  // 
  // DO-DELETE-WHEN-IMPLEMENTING exception.begin(cxx.internal.Reflection.init)
  ::sidl::NotImplementedException ex = ::sidl::NotImplementedException::_create();
  ex.setNote("This method has not been implemented");
  ex.add(__FILE__, __LINE__, "init");
  throw ex;
  // DO-DELETE-WHEN-IMPLEMENTING exception.end(cxx.internal.Reflection.init)
  // DO-NOT-DELETE splicer.end(cxx.internal.Reflection.init)
}

/**
 * Method:  inLocus[]
 */
bool
cxx::internal::Reflection_impl::inLocus_impl (
  /* in */double x,
  /* in */double y ) 
{
  // DO-NOT-DELETE splicer.begin(cxx.internal.Reflection.inLocus)
  // Insert-Code-Here {cxx.internal.Reflection.inLocus} (inLocus method)
  // 
  // This method has not been implemented
  // 
  // DO-DELETE-WHEN-IMPLEMENTING exception.begin(cxx.internal.Reflection.inLocus)
  ::sidl::NotImplementedException ex = ::sidl::NotImplementedException::_create();
  ex.setNote("This method has not been implemented");
  ex.add(__FILE__, __LINE__, "inLocus");
  throw ex;
  // DO-DELETE-WHEN-IMPLEMENTING exception.end(cxx.internal.Reflection.inLocus)
  // DO-NOT-DELETE splicer.end(cxx.internal.Reflection.inLocus)
}


// DO-NOT-DELETE splicer.begin(cxx.internal.Reflection._misc)
// Insert-Code-Here {cxx.internal.Reflection._misc} (miscellaneous code)
// DO-NOT-DELETE splicer.end(cxx.internal.Reflection._misc)
