// 
// File:          cxx_Environment_Impl.cxx
// Symbol:        cxx.Environment-v2.0
// Symbol Type:   class
// Babel Version: 1.1.1
// Description:   Server-side implementation for cxx.Environment
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "cxx_Environment_Impl.hxx"

// 
// Includes for all method dependencies.
// 
#ifndef included_conway_BoundsException_hxx
#include "conway_BoundsException.hxx"
#endif
#ifndef included_sidl_BaseInterface_hxx
#include "sidl_BaseInterface.hxx"
#endif
#ifndef included_sidl_ClassInfo_hxx
#include "sidl_ClassInfo.hxx"
#endif
#ifndef included_sidl_RuntimeException_hxx
#include "sidl_RuntimeException.hxx"
#endif
#ifndef included_sidl_NotImplementedException_hxx
#include "sidl_NotImplementedException.hxx"
#endif
// DO-NOT-DELETE splicer.begin(cxx.Environment._includes)
#include "cxx_BoundsException.hxx"
// DO-NOT-DELETE splicer.end(cxx.Environment._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
cxx::Environment_impl::Environment_impl() : StubBase(reinterpret_cast< void*>(
  ::cxx::Environment::_wrapObj(reinterpret_cast< void*>(this))),false) , 
  _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(cxx.Environment._ctor2)
  // Insert-Code-Here {cxx.Environment._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(cxx.Environment._ctor2)
}

// user defined constructor
void cxx::Environment_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(cxx.Environment._ctor)
  // Insert-Code-Here {cxx.Environment._ctor} (constructor)
  // DO-NOT-DELETE splicer.end(cxx.Environment._ctor)
}

// user defined destructor
void cxx::Environment_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(cxx.Environment._dtor)
  // Insert-Code-Here {cxx.Environment._dtor} (destructor)
  // DO-NOT-DELETE splicer.end(cxx.Environment._dtor)
}

// static class initializer
void cxx::Environment_impl::_load() {
  // DO-NOT-DELETE splicer.begin(cxx.Environment._load)
  // Insert-Code-Here {cxx.Environment._load} (class initialization)
  // DO-NOT-DELETE splicer.end(cxx.Environment._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Create a grid of a certain height and width
 */
void
cxx::Environment_impl::init_impl (
  /* in */int32_t height,
  /* in */int32_t width ) 
{
  // DO-NOT-DELETE splicer.begin(cxx.Environment.init)
 int32_t lower[2]={0,0};
  int32_t upper[2];

  upper[0]=height-1;
  upper[1]=width-1;
  d_grid = UCXX ::sidl::array<int32_t>::createCol( 2, lower, upper );
  for ( int x=0; x<height; x++ ) { 
    for ( int y=0; y<width; y++ ) { 
      d_grid.set(x,y,0);
    }
  }
  // DO-NOT-DELETE splicer.end(cxx.Environment.init)
}

/**
 * Returns true iff that cell is alive
 */
bool
cxx::Environment_impl::isAlive_impl (
  /* in */int32_t x,
  /* in */int32_t y ) 
// throws:
//     ::conway::BoundsException
//     ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(cxx.Environment.isAlive)
  if (!inBounds(x,y)) { 
    UCXX::conway::BoundsException ex = 
      UCXX::sidl::babel_cast< UCXX::conway::BoundsException>(UCXX ::cxx::BoundsException::_create());
    throw ex;
  }
  return d_grid.get(x,y);
  // DO-NOT-DELETE splicer.end(cxx.Environment.isAlive)
}

/**
 * Return the number of living adjacent cells
 */
int32_t
cxx::Environment_impl::nNeighbors_impl (
  /* in */int32_t x,
  /* in */int32_t y ) 
// throws:
//     ::conway::BoundsException
//     ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(cxx.Environment.nNeighbors)
  if (!inBounds(x,y)) { 
    UCXX::conway::BoundsException ex = 
      UCXX::sidl::babel_cast<UCXX::conway::BoundsException> 
      ( UCXX::cxx::BoundsException::_create());
    throw ex;
  }
  int32_t sum=0;
  sum += inBounds(x-1,y-1) ?  isAlive(x-1,y-1) : 0 ;
  sum += inBounds(x-1, y ) ?  isAlive(x-1, y ) : 0 ;
  sum += inBounds(x-1,y+1) ?  isAlive(x-1,y+1) : 0 ;
  sum += inBounds( x ,y-1) ?  isAlive( x ,y-1) : 0 ;
  sum += inBounds( x ,y+1) ?  isAlive( x ,y+1) : 0 ;
  sum += inBounds(x+1,y-1) ?  isAlive(x+1,y-1) : 0 ;
  sum += inBounds(x+1, y ) ?  isAlive(x+1, y ) : 0 ;
  sum += inBounds(x+1,y+1) ?  isAlive(x+1,y+1) : 0 ;
  return sum;
  // DO-NOT-DELETE splicer.end(cxx.Environment.nNeighbors)
}

/**
 * Return the entire grid of data
 */
::sidl::array<int32_t>
cxx::Environment_impl::getGrid_impl () 

{
  // DO-NOT-DELETE splicer.begin(cxx.Environment.getGrid)
  return d_grid;
  // DO-NOT-DELETE splicer.end(cxx.Environment.getGrid)
}

/**
 * Set an entire grid of data (may change height and width)
 */
void
cxx::Environment_impl::setGrid_impl (
  /* in array<int,2,column-major> */::sidl::array<int32_t>& grid ) 
{
  // DO-NOT-DELETE splicer.begin(cxx.Environment.setGrid)
  d_grid.copy(grid);
  // DO-NOT-DELETE splicer.end(cxx.Environment.setGrid)
}


// DO-NOT-DELETE splicer.begin(cxx.Environment._misc)


bool 
cxx::Environment_impl::inBounds( int32_t x, int32_t y) { 
  if ( d_grid._is_nil() ) return false;
  if (x<d_grid.lower(0) || x>d_grid.upper(0) ) return false;
  if (y<d_grid.lower(1) || y>d_grid.upper(1) ) return false;
  return true;
}
// DO-NOT-DELETE splicer.end(cxx.Environment._misc)

