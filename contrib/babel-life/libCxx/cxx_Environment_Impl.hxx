// 
// File:          cxx_Environment_Impl.hxx
// Symbol:        cxx.Environment-v2.0
// Symbol Type:   class
// Babel Version: 1.1.1
// Description:   Server-side implementation for cxx.Environment
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_cxx_Environment_Impl_hxx
#define included_cxx_Environment_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_cxx_Environment_IOR_h
#include "cxx_Environment_IOR.h"
#endif
#ifndef included_conway_BoundsException_hxx
#include "conway_BoundsException.hxx"
#endif
#ifndef included_conway_Environment_hxx
#include "conway_Environment.hxx"
#endif
#ifndef included_cxx_Environment_hxx
#include "cxx_Environment.hxx"
#endif
#ifndef included_sidl_BaseClass_hxx
#include "sidl_BaseClass.hxx"
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


// DO-NOT-DELETE splicer.begin(cxx.Environment._hincludes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(cxx.Environment._hincludes)

namespace cxx { 

  /**
   * Symbol "cxx.Environment" (version 2.0)
   */
  class Environment_impl : public virtual ::cxx::Environment 
  // DO-NOT-DELETE splicer.begin(cxx.Environment._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(cxx.Environment._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(cxx.Environment._implementation)
    UCXX::sidl::array<int32_t> d_grid;

    bool inBounds(int32_t x, int32_t y);
    // DO-NOT-DELETE splicer.end(cxx.Environment._implementation)

  public:
    // default constructor, used for data wrapping(required)
    Environment_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
      Environment_impl( struct cxx_Environment__object * ior ) : StubBase(ior,
        true), 
    ::conway::Environment((ior==NULL) ? NULL : &((*ior).d_conway_environment)) ,
      _wrapped(false) {_ctor();}


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~Environment_impl() { _dtor(); }

    // user defined destruction
    void _dtor();

    // true if this object was created by a user newing the impl
    inline bool _isWrapped() {return _wrapped;}

    // static class initializer
    static void _load();

  public:


    /**
     * Create a grid of a certain height and width
     */
    void
    init_impl (
      /* in */int32_t height,
      /* in */int32_t width
    )
    ;


    /**
     * Returns true iff that cell is alive
     */
    bool
    isAlive_impl (
      /* in */int32_t x,
      /* in */int32_t y
    )
    // throws:
    //     ::conway::BoundsException
    //     ::sidl::RuntimeException
    ;


    /**
     * Return the number of living adjacent cells
     */
    int32_t
    nNeighbors_impl (
      /* in */int32_t x,
      /* in */int32_t y
    )
    // throws:
    //     ::conway::BoundsException
    //     ::sidl::RuntimeException
    ;


    /**
     * Return the entire grid of data
     */
    ::sidl::array<int32_t>
    getGrid_impl() ;

    /**
     * Set an entire grid of data (may change height and width)
     */
    void
    setGrid_impl (
      /* in array<int,2,column-major> */::sidl::array<int32_t>& grid
    )
    ;

  };  // end class Environment_impl

} // end namespace cxx

// DO-NOT-DELETE splicer.begin(cxx.Environment._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(cxx.Environment._hmisc)

#endif
