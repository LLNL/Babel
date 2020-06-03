// 
// File:          cxx_ScalarField_Impl.cxx
// Symbol:        cxx.ScalarField-v1.0
// Symbol Type:   class
// Babel Version: 1.2.0
// Description:   Server-side implementation for cxx.ScalarField
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "cxx_ScalarField_Impl.hxx"

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
// DO-NOT-DELETE splicer.begin(cxx.ScalarField._includes)
// Insert-Code-Here {cxx.ScalarField._includes} (additional includes or code)
// DO-NOT-DELETE splicer.end(cxx.ScalarField._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
cxx::ScalarField_impl::ScalarField_impl() : StubBase(reinterpret_cast< void*>(
  ::cxx::ScalarField::_wrapObj(reinterpret_cast< void*>(this))),false) , 
  _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(cxx.ScalarField._ctor2)
  // Insert-Code-Here {cxx.ScalarField._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(cxx.ScalarField._ctor2)
}

// user defined constructor
void cxx::ScalarField_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(cxx.ScalarField._ctor)
  // Insert-Code-Here {cxx.ScalarField._ctor} (constructor)
  // DO-NOT-DELETE splicer.end(cxx.ScalarField._ctor)
}

// user defined destructor
void cxx::ScalarField_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(cxx.ScalarField._dtor)
  // Insert-Code-Here {cxx.ScalarField._dtor} (destructor)
  // DO-NOT-DELETE splicer.end(cxx.ScalarField._dtor)
}

// static class initializer
void cxx::ScalarField_impl::_load() {
  // DO-NOT-DELETE splicer.begin(cxx.ScalarField._load)
  // Insert-Code-Here {cxx.ScalarField._load} (class initialization)
  // DO-NOT-DELETE splicer.end(cxx.ScalarField._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Method:  init[]
 */
void
cxx::ScalarField_impl::init_impl (
  /* in */double minX,
  /* in */double minY,
  /* in */double maxX,
  /* in */double maxY,
  /* in */double spacing,
  /* in */double value ) 
{
  // DO-NOT-DELETE splicer.begin(cxx.ScalarField.init)
  d_minX = minX;
  d_maxX = maxX;
  d_minY = minY;
  d_maxY = maxX;
  d_spacing = spacing;
  const int nRows = (int) ceil( (maxX-minX)/spacing );
  const int nCols = (int) ceil( (maxY-minY)/spacing );  
  d_density = sidl::array<double>::create2dRow(nRows, nCols);
  for ( int32_t i=d_density.lower(0); i<= d_density.upper(0); ++i ) { 
    for ( int32_t j=d_density.lower(0); j<= d_density.upper(0); ++j ) { 
      d_density.set(i,j,value);
    }
  }
  // DO-NOT-DELETE splicer.end(cxx.ScalarField.init)
}

/**
 * Method:  getBounds[]
 */
void
cxx::ScalarField_impl::getBounds_impl (
  /* out */double& minX,
  /* out */double& minY,
  /* out */double& maxX,
  /* out */double& maxY,
  /* out */double& spacing ) 
{
  // DO-NOT-DELETE splicer.begin(cxx.ScalarField.getBounds)
  minX = d_minX;
  maxX = d_maxX;
  minY = d_minY;
  maxY = d_maxY;
  spacing = d_spacing;
  return;
  // DO-NOT-DELETE splicer.end(cxx.ScalarField.getBounds)
}

/**
 * Method:  getData[]
 */
::sidl::array<double>
cxx::ScalarField_impl::getData_impl () 

{
  // DO-NOT-DELETE splicer.begin(cxx.ScalarField.getData)
  return d_density;
  // DO-NOT-DELETE splicer.end(cxx.ScalarField.getData)
}

/**
 * Method:  render[]
 */
void
cxx::ScalarField_impl::render_impl (
  /* in */::wave2d::Shape& shape,
  /* in */double value ) 
{
  // DO-NOT-DELETE splicer.begin(cxx.ScalarField.render)
  const int min_i = d_density.lower(0);
  const int max_i = d_density.upper(0);
  const int min_j = d_density.lower(1);
  const int max_j = d_density.upper(1);

  double x = d_minX; 
  for( int i = min_i; i<=max_i; ++i ) { 
    double y = d_minY;
    for ( int j = min_j; j <= max_j; ++j ) { 
      if ( shape.inLocus(x,y) ) { 
	d_density.set(i,j,value);
      }
      y += d_spacing;
    } 
    x += d_spacing;
  } 
  // DO-NOT-DELETE splicer.end(cxx.ScalarField.render)
}

/**
 * Method:  setData[]
 */
void
cxx::ScalarField_impl::setData_impl (
  /* in array<double,2> */::sidl::array<double>& data ) 
{
  // DO-NOT-DELETE splicer.begin(cxx.ScalarField.setData)
  d_density = data;
  // DO-NOT-DELETE splicer.end(cxx.ScalarField.setData)
}


// DO-NOT-DELETE splicer.begin(cxx.ScalarField._misc)
// Insert-Code-Here {cxx.ScalarField._misc} (miscellaneous code)
// DO-NOT-DELETE splicer.end(cxx.ScalarField._misc)

