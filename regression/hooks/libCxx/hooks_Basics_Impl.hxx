// 
// File:          hooks_Basics_Impl.hxx
// Symbol:        hooks.Basics-v1.0
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for hooks.Basics
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_hooks_Basics_Impl_hxx
#define included_hooks_Basics_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_hooks_Basics_IOR_h
#include "hooks_Basics_IOR.h"
#endif
#ifndef included_hooks_Basics_hxx
#include "hooks_Basics.hxx"
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


// DO-NOT-DELETE splicer.begin(hooks.Basics._hincludes)
// Insert-Code-Here {hooks.Basics._includes} (includes or arbitrary code)
// DO-NOT-DELETE splicer.end(hooks.Basics._hincludes)

namespace hooks { 

  /**
   * Symbol "hooks.Basics" (version 1.0)
   */
  class Basics_impl : public virtual ::hooks::Basics 
  // DO-NOT-DELETE splicer.begin(hooks.Basics._inherits)
  // Insert-Code-Here {hooks.Basics._inherits} (optional inheritance here)
  // DO-NOT-DELETE splicer.end(hooks.Basics._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(hooks.Basics._implementation)
    // Insert-Code-Here {hooks.Basics._implementation} (additional details)
    int num_prehooks;
    int num_posthooks;
    static int num_prehooks_static;
    static int num_posthooks_static;
    // DO-NOT-DELETE splicer.end(hooks.Basics._implementation)

  public:
    // default constructor, used for data wrapping(required)
    Basics_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
    Basics_impl( struct hooks_Basics__object * ior ) : StubBase(ior,true) , 
      _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~Basics_impl() { _dtor(); }

    // user defined destruction
    void _dtor();

    // true if this object was created by a user newing the impl
    inline bool _isWrapped() {return _wrapped;}

    // static class initializer
    static void _load();

  public:

    /**
     * Basic illustration of hooks for static methods.
     */
    static int32_t
    aStaticMeth_impl (
      /* in */int32_t i,
      /* out */int32_t& o,
      /* inout */int32_t& io
    )
    ;


    /**
     * Basic illustration of hooks for static methods.
     */
    static void
    aStaticMeth_pre_impl (
      /* in */int32_t i,
      /* in */int32_t io
    )
    ;


    /**
     * Basic illustration of hooks for static methods.
     */
    static void
    aStaticMeth_post_impl (
      /* in */int32_t i,
      /* in */int32_t o,
      /* in */int32_t io,
      /* in */int32_t _retval
    )
    ;



    /**
     * Basic illustration of hooks for static methods.
     */
    int32_t
    aNonStaticMeth_impl (
      /* in */int32_t i,
      /* out */int32_t& o,
      /* inout */int32_t& io
    )
    ;


    /**
     * Basic illustration of hooks for static methods.
     */
    void
    aNonStaticMeth_pre_impl (
      /* in */int32_t i,
      /* in */int32_t io
    )
    ;


    /**
     * Basic illustration of hooks for static methods.
     */
    void
    aNonStaticMeth_post_impl (
      /* in */int32_t i,
      /* in */int32_t o,
      /* in */int32_t io,
      /* in */int32_t _retval
    )
    ;

  };  // end class Basics_impl

} // end namespace hooks

// DO-NOT-DELETE splicer.begin(hooks.Basics._hmisc)
// Insert-Code-Here {hooks.Basics._misc} (miscellaneous things)
// DO-NOT-DELETE splicer.end(hooks.Basics._hmisc)

#endif
