// 
// File:          cxx_TimeStepper_Impl.hxx
// Symbol:        cxx.TimeStepper-v2.0
// Symbol Type:   class
// Babel Version: 1.1.1
// Description:   Server-side implementation for cxx.TimeStepper
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_cxx_TimeStepper_Impl_hxx
#define included_cxx_TimeStepper_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_cxx_TimeStepper_IOR_h
#include "cxx_TimeStepper_IOR.h"
#endif
#ifndef included_conway_Environment_hxx
#include "conway_Environment.hxx"
#endif
#ifndef included_conway_Ruleset_hxx
#include "conway_Ruleset.hxx"
#endif
#ifndef included_conway_TimeStepper_hxx
#include "conway_TimeStepper.hxx"
#endif
#ifndef included_cxx_TimeStepper_hxx
#include "cxx_TimeStepper.hxx"
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


// DO-NOT-DELETE splicer.begin(cxx.TimeStepper._hincludes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(cxx.TimeStepper._hincludes)

namespace cxx { 

  /**
   * Symbol "cxx.TimeStepper" (version 2.0)
   */
  class TimeStepper_impl : public virtual ::cxx::TimeStepper 
  // DO-NOT-DELETE splicer.begin(cxx.TimeStepper._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(cxx.TimeStepper._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(cxx.TimeStepper._implementation)
    UCXX::conway::Environment d_env;
    UCXX::conway::Ruleset d_rules;
    UCXX::sidl::array<int> d_next;
    int32_t d_step;
    // DO-NOT-DELETE splicer.end(cxx.TimeStepper._implementation)

  public:
    // default constructor, used for data wrapping(required)
    TimeStepper_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
      TimeStepper_impl( struct cxx_TimeStepper__object * ior ) : StubBase(ior,
        true), 
    ::conway::TimeStepper((ior==NULL) ? NULL : &((*ior).d_conway_timestepper)) ,
      _wrapped(false) {_ctor();}


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~TimeStepper_impl() { _dtor(); }

    // user defined destruction
    void _dtor();

    // true if this object was created by a user newing the impl
    inline bool _isWrapped() {return _wrapped;}

    // static class initializer
    static void _load();

  public:

    /**
     * user defined non-static method.
     */
    void
    init_impl (
      /* in */::conway::Environment& env,
      /* in */::conway::Ruleset& rules
    )
    ;


    /**
     *  
     * advance one more timestep 
     * returns population count at new timestep
     */
    int32_t
    step_impl() ;

    /**
     *  check the number of steps taken 
     */
    int32_t
    nStepsTaken_impl() ;
  };  // end class TimeStepper_impl

} // end namespace cxx

// DO-NOT-DELETE splicer.begin(cxx.TimeStepper._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(cxx.TimeStepper._hmisc)

#endif
