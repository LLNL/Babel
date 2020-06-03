// 
// File:          cxx_Environment_Impl.hh
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

#ifndef included_cxx_Environment_Impl_hh
#define included_cxx_Environment_Impl_hh

#ifndef included_sidl_cxx_hh
#include "sidl_cxx.hh"
#endif
#ifndef included_cxx_Environment_IOR_h
#include "cxx_Environment_IOR.h"
#endif
// 
// Includes for all method dependencies.
// 
#ifndef included_conway_BoundsException_hh
#include "conway_BoundsException.hh"
#endif
#ifndef included_cxx_Environment_hh
#include "cxx_Environment.hh"
#endif
#ifndef included_sidl_BaseInterface_hh
#include "sidl_BaseInterface.hh"
#endif
#ifndef included_sidl_ClassInfo_hh
#include "sidl_ClassInfo.hh"
#endif


// DO-NOT-DELETE splicer.begin(cxx.Environment._includes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(cxx.Environment._includes)

namespace cxx { 

  /**
   * Symbol "cxx.Environment" (version 2.0)
   */
  class Environment_impl
  // DO-NOT-DELETE splicer.begin(cxx.Environment._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(cxx.Environment._inherits)
  {

  private:
    // Pointer back to IOR.
    // Use this to dispatch back through IOR vtable.
    Environment self;

    // DO-NOT-DELETE splicer.begin(cxx.Environment._implementation)
    ::sidl::array<int32_t> d_grid;

    bool inBounds(int32_t x, int32_t y) throw () ;
    // DO-NOT-DELETE splicer.end(cxx.Environment._implementation)

  private:
    // private default constructor (required)
    Environment_impl() {} 

  public:
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
    Environment_impl( struct cxx_Environment__object * s ) : self(s,
      true) { _ctor(); }

    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~Environment_impl() { _dtor(); }

    // user defined destruction
    void _dtor();

  public:


    /**
     * Create a grid of a certain height and width
     */
    void
    init (
      /*in*/ int32_t height,
      /*in*/ int32_t width
    )
    throw () 
    ;


    /**
     * Returns true iff that cell is alive
     */
    bool
    isAlive (
      /*in*/ int32_t x,
      /*in*/ int32_t y
    )
    throw ( 
      ::conway::BoundsException
    );


    /**
     * Return the number of living adjacent cells
     */
    int32_t
    nNeighbors (
      /*in*/ int32_t x,
      /*in*/ int32_t y
    )
    throw ( 
      ::conway::BoundsException
    );


    /**
     * Return the entire grid of data
     */
    ::sidl::array<int32_t>
    getGrid() throw () 
    ;

    /**
     * Set an entire grid of data (may change height and width)
     */
    void
    setGrid (
      /*in*/ ::sidl::array<int32_t> grid
    )
    throw () 
    ;

  };  // end class Environment_impl

} // end namespace cxx

// DO-NOT-DELETE splicer.begin(cxx.Environment._misc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(cxx.Environment._misc)

#endif
