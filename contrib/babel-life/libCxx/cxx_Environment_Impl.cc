// 
// File:          cxx_Environment_Impl.cc
// Symbol:        cxx.Environment-v2.0
// Symbol Type:   class
// Babel Version: 0.9.6
// sidl Created:  20041004 21:44:22 PDT
// Generated:     20041004 21:44:25 PDT
// Description:   Server-side implementation for cxx.Environment
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// babel-version = 0.9.6
// source-line   = 5
// source-url    = file:/home/kumfert/scdemo/hands-on/task_0/babel-life/libCxx/cxxlife.sidl
// 
#include "cxx_Environment_Impl.hh"

// DO-NOT-DELETE splicer.begin(cxx.Environment._includes)
#include "cxx_BoundsException.hh"
// DO-NOT-DELETE splicer.end(cxx.Environment._includes)

// user defined constructor
void cxx::Environment_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(cxx.Environment._ctor)
  // add construction details here
  // DO-NOT-DELETE splicer.end(cxx.Environment._ctor)
}

// user defined destructor
void cxx::Environment_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(cxx.Environment._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(cxx.Environment._dtor)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Create a grid of a certain height and width
 */
void
cxx::Environment_impl::init (
  /*in*/ int32_t height,
  /*in*/ int32_t width ) 
throw () 
{
  // DO-NOT-DELETE splicer.begin(cxx.Environment.init)
  int32_t lower[2]={0,0};
  int32_t upper[2];

  upper[0]=height-1;
  upper[1]=width-1;
  d_grid = ::sidl::array<int32_t>::createCol( 2, lower, upper );
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
cxx::Environment_impl::isAlive (
  /*in*/ int32_t x,
  /*in*/ int32_t y ) 
throw ( 
  ::conway::BoundsException
){
  // DO-NOT-DELETE splicer.begin(cxx.Environment.isAlive)
  if (!inBounds(x,y)) { 
    conway::BoundsException ex = cxx::BoundsException::_create();
    throw ex;
  }
  return d_grid.get(x,y);
  // DO-NOT-DELETE splicer.end(cxx.Environment.isAlive)
}

/**
 * Return the number of living adjacent cells
 */
int32_t
cxx::Environment_impl::nNeighbors (
  /*in*/ int32_t x,
  /*in*/ int32_t y ) 
throw ( 
  ::conway::BoundsException
){
  // DO-NOT-DELETE splicer.begin(cxx.Environment.nNeighbors)
  if (!inBounds(x,y)) { 
    conway::BoundsException ex = cxx::BoundsException::_create();
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
cxx::Environment_impl::getGrid () 
throw () 

{
  // DO-NOT-DELETE splicer.begin(cxx.Environment.getGrid)
  return d_grid;
  // DO-NOT-DELETE splicer.end(cxx.Environment.getGrid)
}

/**
 * Set an entire grid of data (may change height and width)
 */
void
cxx::Environment_impl::setGrid (
  /*in*/ ::sidl::array<int32_t> grid ) 
throw () 
{
  // DO-NOT-DELETE splicer.begin(cxx.Environment.setGrid)
  d_grid.copy(grid);
  // DO-NOT-DELETE splicer.end(cxx.Environment.setGrid)
}


// DO-NOT-DELETE splicer.begin(cxx.Environment._misc)

bool 
cxx::Environment_impl::inBounds( int32_t x, int32_t y)  throw() { 
  if ( d_grid._is_nil() ) return false;
  if (x<d_grid.lower(0) || x>d_grid.upper(0) ) return false;
  if (y<d_grid.lower(1) || y>d_grid.upper(1) ) return false;
  return true;
}

// DO-NOT-DELETE splicer.end(cxx.Environment._misc)

