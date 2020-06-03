// 
// File:          cxx_ShapeFactory_Impl.cxx
// Symbol:        cxx.ShapeFactory-v1.0
// Symbol Type:   class
// Babel Version: 1.2.0
// Description:   Server-side implementation for cxx.ShapeFactory
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "cxx_ShapeFactory_Impl.hxx"

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
// DO-NOT-DELETE splicer.begin(cxx.ShapeFactory._includes)
#include "cxx_internal_Rectangle.hxx"
#include "cxx_internal_Ellipse.hxx"
#include "cxx_internal_Triangle.hxx"
// DO-NOT-DELETE splicer.end(cxx.ShapeFactory._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
cxx::ShapeFactory_impl::ShapeFactory_impl() : StubBase(reinterpret_cast< void*>(
  ::cxx::ShapeFactory::_wrapObj(reinterpret_cast< void*>(this))),false) , 
  _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(cxx.ShapeFactory._ctor2)
  // Insert-Code-Here {cxx.ShapeFactory._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(cxx.ShapeFactory._ctor2)
}

// user defined constructor
void cxx::ShapeFactory_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(cxx.ShapeFactory._ctor)
  // Insert-Code-Here {cxx.ShapeFactory._ctor} (constructor)
  // DO-NOT-DELETE splicer.end(cxx.ShapeFactory._ctor)
}

// user defined destructor
void cxx::ShapeFactory_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(cxx.ShapeFactory._dtor)
  // Insert-Code-Here {cxx.ShapeFactory._dtor} (destructor)
  // DO-NOT-DELETE splicer.end(cxx.ShapeFactory._dtor)
}

// static class initializer
void cxx::ShapeFactory_impl::_load() {
  // DO-NOT-DELETE splicer.begin(cxx.ShapeFactory._load)
  // Insert-Code-Here {cxx.ShapeFactory._load} (class initialization)
  // DO-NOT-DELETE splicer.end(cxx.ShapeFactory._load)
}

// user defined static methods:
/**
 * Method:  createRectangle[]
 */
::wave2d::Shape
cxx::ShapeFactory_impl::createRectangle_impl (
  /* in */double x1,
  /* in */double y1,
  /* in */double x2,
  /* in */double y2 ) 
{
  // DO-NOT-DELETE splicer.begin(cxx.ShapeFactory.createRectangle)
  cxx::internal::Rectangle rect = cxx::internal::Rectangle::_create();
  rect.init( x1, y1, x2, y2);
  return rect;
  // DO-NOT-DELETE splicer.end(cxx.ShapeFactory.createRectangle)
}

/**
 * Method:  createEllipse[]
 */
::wave2d::Shape
cxx::ShapeFactory_impl::createEllipse_impl (
  /* in */double center_x,
  /* in */double center_y,
  /* in */double x_radius,
  /* in */double y_radius ) 
{
  // DO-NOT-DELETE splicer.begin(cxx.ShapeFactory.createEllipse)
  cxx::internal::Ellipse ellipse = cxx::internal::Ellipse::_create();
  ellipse.init( center_x, center_y, x_radius, y_radius );
  return ellipse; 
  // DO-NOT-DELETE splicer.end(cxx.ShapeFactory.createEllipse)
}

/**
 * Method:  createTriangle[]
 */
::wave2d::Shape
cxx::ShapeFactory_impl::createTriangle_impl (
  /* in */double x1,
  /* in */double y1,
  /* in */double x2,
  /* in */double y2,
  /* in */double x3,
  /* in */double y3 ) 
{
  // DO-NOT-DELETE splicer.begin(cxx.ShapeFactory.createTriangle)
  cxx::internal::Triangle tri = cxx::internal::Triangle::_create();
  tri.init( x1, y1, x2, y2, x3, y3 );
  return tri;
  // DO-NOT-DELETE splicer.end(cxx.ShapeFactory.createTriangle)
}


// user defined non-static methods: (none)

// DO-NOT-DELETE splicer.begin(cxx.ShapeFactory._misc)
// Insert-Code-Here {cxx.ShapeFactory._misc} (miscellaneous code)
// DO-NOT-DELETE splicer.end(cxx.ShapeFactory._misc)

