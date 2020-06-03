// 
// File:          cxx_Ruleset_Impl.hxx
// Symbol:        cxx.Ruleset-v2.0
// Symbol Type:   class
// Babel Version: 1.1.1
// Description:   Server-side implementation for cxx.Ruleset
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_cxx_Ruleset_Impl_hxx
#define included_cxx_Ruleset_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_cxx_Ruleset_IOR_h
#include "cxx_Ruleset_IOR.h"
#endif
#ifndef included_conway_BoundsException_hxx
#include "conway_BoundsException.hxx"
#endif
#ifndef included_conway_Environment_hxx
#include "conway_Environment.hxx"
#endif
#ifndef included_conway_Ruleset_hxx
#include "conway_Ruleset.hxx"
#endif
#ifndef included_cxx_Ruleset_hxx
#include "cxx_Ruleset.hxx"
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


// DO-NOT-DELETE splicer.begin(cxx.Ruleset._hincludes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(cxx.Ruleset._hincludes)

namespace cxx { 

  /**
   * Symbol "cxx.Ruleset" (version 2.0)
   */
  class Ruleset_impl : public virtual ::cxx::Ruleset 
  // DO-NOT-DELETE splicer.begin(cxx.Ruleset._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(cxx.Ruleset._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(cxx.Ruleset._implementation)
    // Put additional implementation details here...
    // DO-NOT-DELETE splicer.end(cxx.Ruleset._implementation)

  public:
    // default constructor, used for data wrapping(required)
    Ruleset_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
      Ruleset_impl( struct cxx_Ruleset__object * ior ) : StubBase(ior,true), 
    ::conway::Ruleset((ior==NULL) ? NULL : &((*ior).d_conway_ruleset)) , 
      _wrapped(false) {_ctor();}


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~Ruleset_impl() { _dtor(); }

    // user defined destruction
    void _dtor();

    // true if this object was created by a user newing the impl
    inline bool _isWrapped() {return _wrapped;}

    // static class initializer
    static void _load();

  public:


    /**
     * Birth: an empty cell has 3 living neighbors
     * Death: a living cell has 0 or 1 neighbors (loneliness)
     * or a living cell has 4-8 neighbors (overcrowding)
     * Life: a living cell has 2 or three neighbors
     */
    bool
    setAlive_impl (
      /* in */int32_t x,
      /* in */int32_t y,
      /* in */::conway::Environment& env
    )
    // throws:
    //     ::conway::BoundsException
    //     ::sidl::RuntimeException
    ;

  };  // end class Ruleset_impl

} // end namespace cxx

// DO-NOT-DELETE splicer.begin(cxx.Ruleset._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(cxx.Ruleset._hmisc)

#endif
