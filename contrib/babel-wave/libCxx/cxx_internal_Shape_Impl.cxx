// 
// File:          cxx_internal_Shape_Impl.cxx
// Symbol:        cxx.internal.Shape-v1.0
// Symbol Type:   class
// Babel Version: 1.2.0
// Description:   Server-side implementation for cxx.internal.Shape
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "cxx_internal_Shape_Impl.hxx"

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
// DO-NOT-DELETE splicer.begin(cxx.internal.Shape._includes)
#include "cxx_internal_Translation.hxx"
#include "cxx_internal_Rotation.hxx"
#include "cxx_internal_Unification.hxx"
#include "cxx_internal_Intersection.hxx"
#include "cxx_internal_Subtraction.hxx"
#include "cxx_internal_Inversion.hxx"
#include "cxx_internal_Scaling.hxx"
#include "cxx_internal_Reflection.hxx"
// DO-NOT-DELETE splicer.end(cxx.internal.Shape._includes)

// default constructor, not to be used!
// user defined constructor
void cxx::internal::Shape_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(cxx.internal.Shape._ctor)
  // Insert-Code-Here {cxx.internal.Shape._ctor} (constructor)
  // DO-NOT-DELETE splicer.end(cxx.internal.Shape._ctor)
}

// user defined destructor
void cxx::internal::Shape_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(cxx.internal.Shape._dtor)
  // Insert-Code-Here {cxx.internal.Shape._dtor} (destructor)
  // DO-NOT-DELETE splicer.end(cxx.internal.Shape._dtor)
}

// static class initializer
void cxx::internal::Shape_impl::_load() {
  // DO-NOT-DELETE splicer.begin(cxx.internal.Shape._load)
  // Insert-Code-Here {cxx.internal.Shape._load} (class initialization)
  // DO-NOT-DELETE splicer.end(cxx.internal.Shape._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Method:  translate[]
 */
::wave2d::Shape
cxx::internal::Shape_impl::translate_impl (
  /* in */double delta_x,
  /* in */double delta_y ) 
{
  // DO-NOT-DELETE splicer.begin(cxx.internal.Shape.translate)
  cxx::internal::Translation t = cxx::internal::Translation::_create();
  t.init(*this, delta_x, delta_y);
  return t; 
  // DO-NOT-DELETE splicer.end(cxx.internal.Shape.translate)
}

/**
 * Method:  rotate[]
 */
::wave2d::Shape
cxx::internal::Shape_impl::rotate_impl (
  /* in */double radians ) 
{
  // DO-NOT-DELETE splicer.begin(cxx.internal.Shape.rotate)
  cxx::internal::Rotation r = cxx::internal::Rotation::_create();
  r.init(*this, radians );
  return r; 
  // DO-NOT-DELETE splicer.end(cxx.internal.Shape.rotate)
}

/**
 * Method:  unify[]
 */
::wave2d::Shape
cxx::internal::Shape_impl::unify_impl (
  /* in */::wave2d::Shape& other ) 
{
  // DO-NOT-DELETE splicer.begin(cxx.internal.Shape.unify)
  cxx::internal::Unification u = cxx::internal::Unification::_create();
  u.init(*this, other);
  return u; 
  // DO-NOT-DELETE splicer.end(cxx.internal.Shape.unify)
}

/**
 * Method:  intersect[]
 */
::wave2d::Shape
cxx::internal::Shape_impl::intersect_impl (
  /* in */::wave2d::Shape& other ) 
{
  // DO-NOT-DELETE splicer.begin(cxx.internal.Shape.intersect)
  cxx::internal::Intersection i = cxx::internal::Intersection::_create();
  i.init(*this, other);
  return i; 
  // DO-NOT-DELETE splicer.end(cxx.internal.Shape.intersect)
}

/**
 * Method:  subtract[]
 */
::wave2d::Shape
cxx::internal::Shape_impl::subtract_impl (
  /* in */::wave2d::Shape& other ) 
{
  // DO-NOT-DELETE splicer.begin(cxx.internal.Shape.subtract)
  cxx::internal::Subtraction s = cxx::internal::Subtraction::_create();
  s.init(*this, other);
  return s; 
  // DO-NOT-DELETE splicer.end(cxx.internal.Shape.subtract)
}

/**
 * Method:  invert[]
 */
::wave2d::Shape
cxx::internal::Shape_impl::invert_impl () 

{
  // DO-NOT-DELETE splicer.begin(cxx.internal.Shape.invert)
  cxx::internal::Inversion i = cxx::internal::Inversion::_create();
  i.init(*this);
  return i; 
  // DO-NOT-DELETE splicer.end(cxx.internal.Shape.invert)
}

/**
 * Method:  scale[]
 */
::wave2d::Shape
cxx::internal::Shape_impl::scale_impl (
  /* in */double scale_x,
  /* in */double scale_y ) 
{
  // DO-NOT-DELETE splicer.begin(cxx.internal.Shape.scale)
  cxx::internal::Scaling s = cxx::internal::Scaling::_create();
  s.init(*this, scale_x, scale_y);
  return s;  
  // DO-NOT-DELETE splicer.end(cxx.internal.Shape.scale)
}

/**
 * Method:  reflectX[]
 */
::wave2d::Shape
cxx::internal::Shape_impl::reflectX_impl () 

{
  // DO-NOT-DELETE splicer.begin(cxx.internal.Shape.reflectX)
  cxx::internal::Reflection r = cxx::internal::Reflection::_create();
  r.init(*this, true);
  return r;  
  // DO-NOT-DELETE splicer.end(cxx.internal.Shape.reflectX)
}

/**
 * Method:  reflectY[]
 */
::wave2d::Shape
cxx::internal::Shape_impl::reflectY_impl () 

{
  // DO-NOT-DELETE splicer.begin(cxx.internal.Shape.reflectY)
  cxx::internal::Reflection r = cxx::internal::Reflection::_create();
  r.init(*this, false);
  return r;  
  // DO-NOT-DELETE splicer.end(cxx.internal.Shape.reflectY)
}

/**
 * Method:  render[]
 */
void
cxx::internal::Shape_impl::render_impl (
  /* inout array<double,2> */::sidl::array<double>& field,
  /* in */double value ) 
{
  // DO-NOT-DELETE splicer.begin(cxx.internal.Shape.render)
  // DO-NOT-DELETE splicer.end(cxx.internal.Shape.render)
}


// DO-NOT-DELETE splicer.begin(cxx.internal.Shape._misc)
// Insert-Code-Here {cxx.internal.Shape._misc} (miscellaneous code)
// DO-NOT-DELETE splicer.end(cxx.internal.Shape._misc)

