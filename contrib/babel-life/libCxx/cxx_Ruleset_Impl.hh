// 
// File:          cxx_Ruleset_Impl.hh
// Symbol:        cxx.Ruleset-v2.0
// Symbol Type:   class
// Babel Version: 0.9.6
// sidl Created:  20041004 21:44:22 PDT
// Generated:     20041004 21:44:24 PDT
// Description:   Server-side implementation for cxx.Ruleset
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// babel-version = 0.9.6
// source-line   = 6
// source-url    = file:/home/kumfert/scdemo/hands-on/task_0/babel-life/libCxx/cxxlife.sidl
// 

#ifndef included_cxx_Ruleset_Impl_hh
#define included_cxx_Ruleset_Impl_hh

#ifndef included_sidl_cxx_hh
#include "sidl_cxx.hh"
#endif
#ifndef included_cxx_Ruleset_IOR_h
#include "cxx_Ruleset_IOR.h"
#endif
// 
// Includes for all method dependencies.
// 
#ifndef included_conway_BoundsException_hh
#include "conway_BoundsException.hh"
#endif
#ifndef included_conway_Environment_hh
#include "conway_Environment.hh"
#endif
#ifndef included_cxx_Ruleset_hh
#include "cxx_Ruleset.hh"
#endif
#ifndef included_sidl_BaseInterface_hh
#include "sidl_BaseInterface.hh"
#endif
#ifndef included_sidl_ClassInfo_hh
#include "sidl_ClassInfo.hh"
#endif


// DO-NOT-DELETE splicer.begin(cxx.Ruleset._includes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(cxx.Ruleset._includes)

namespace cxx { 

  /**
   * Symbol "cxx.Ruleset" (version 2.0)
   */
  class Ruleset_impl
  // DO-NOT-DELETE splicer.begin(cxx.Ruleset._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(cxx.Ruleset._inherits)
  {

  private:
    // Pointer back to IOR.
    // Use this to dispatch back through IOR vtable.
    Ruleset self;

    // DO-NOT-DELETE splicer.begin(cxx.Ruleset._implementation)
    // Put additional implementation details here...
    // DO-NOT-DELETE splicer.end(cxx.Ruleset._implementation)

  private:
    // private default constructor (required)
    Ruleset_impl() {} 

  public:
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
    Ruleset_impl( struct cxx_Ruleset__object * s ) : self(s,true) { _ctor(); }

    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~Ruleset_impl() { _dtor(); }

    // user defined destruction
    void _dtor();

  public:


    /**
     * Birth: an empty cell has 3 living neighbors
     * Death: a living cell has 0 or 1 neighbors (loneliness)
     *        or a living cell has 4-8 neighbors (overcrowding)
     * Life: a living cell has 2 or three neighbors
     */
    bool
    setAlive (
      /*in*/ int32_t x,
      /*in*/ int32_t y,
      /*in*/ ::conway::Environment env
    )
    throw ( 
      ::conway::BoundsException
    );

  };  // end class Ruleset_impl

} // end namespace cxx

// DO-NOT-DELETE splicer.begin(cxx.Ruleset._misc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(cxx.Ruleset._misc)

#endif
