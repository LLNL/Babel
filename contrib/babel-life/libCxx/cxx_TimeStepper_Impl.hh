// 
// File:          cxx_TimeStepper_Impl.hh
// Symbol:        cxx.TimeStepper-v2.0
// Symbol Type:   class
// Babel Version: 0.9.6
// sidl Created:  20041004 21:44:22 PDT
// Generated:     20041004 21:44:24 PDT
// Description:   Server-side implementation for cxx.TimeStepper
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// babel-version = 0.9.6
// source-line   = 7
// source-url    = file:/home/kumfert/scdemo/hands-on/task_0/babel-life/libCxx/cxxlife.sidl
// 

#ifndef included_cxx_TimeStepper_Impl_hh
#define included_cxx_TimeStepper_Impl_hh

#ifndef included_sidl_cxx_hh
#include "sidl_cxx.hh"
#endif
#ifndef included_cxx_TimeStepper_IOR_h
#include "cxx_TimeStepper_IOR.h"
#endif
// 
// Includes for all method dependencies.
// 
#ifndef included_conway_Environment_hh
#include "conway_Environment.hh"
#endif
#ifndef included_conway_Ruleset_hh
#include "conway_Ruleset.hh"
#endif
#ifndef included_cxx_TimeStepper_hh
#include "cxx_TimeStepper.hh"
#endif
#ifndef included_sidl_BaseInterface_hh
#include "sidl_BaseInterface.hh"
#endif
#ifndef included_sidl_ClassInfo_hh
#include "sidl_ClassInfo.hh"
#endif


// DO-NOT-DELETE splicer.begin(cxx.TimeStepper._includes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(cxx.TimeStepper._includes)

namespace cxx { 

  /**
   * Symbol "cxx.TimeStepper" (version 2.0)
   */
  class TimeStepper_impl
  // DO-NOT-DELETE splicer.begin(cxx.TimeStepper._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(cxx.TimeStepper._inherits)
  {

  private:
    // Pointer back to IOR.
    // Use this to dispatch back through IOR vtable.
    TimeStepper self;

    // DO-NOT-DELETE splicer.begin(cxx.TimeStepper._implementation)
    ::conway::Environment d_env;
    ::conway::Ruleset d_rules;
    ::sidl::array<int> d_next;
    int32_t d_step;
    // DO-NOT-DELETE splicer.end(cxx.TimeStepper._implementation)

  private:
    // private default constructor (required)
    TimeStepper_impl() {} 

  public:
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
    TimeStepper_impl( struct cxx_TimeStepper__object * s ) : self(s,
      true) { _ctor(); }

    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~TimeStepper_impl() { _dtor(); }

    // user defined destruction
    void _dtor();

  public:

    /**
     * user defined non-static method.
     */
    void
    init (
      /*in*/ ::conway::Environment env,
      /*in*/ ::conway::Ruleset rules
    )
    throw () 
    ;


    /**
     * advance one more timestep 
     * returns population count at new timestep
     */
    int32_t
    step() throw () 
    ;

    /**
     * check the number of steps taken 
     */
    int32_t
    nStepsTaken() throw () 
    ;
  };  // end class TimeStepper_impl

} // end namespace cxx

// DO-NOT-DELETE splicer.begin(cxx.TimeStepper._misc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(cxx.TimeStepper._misc)

#endif
