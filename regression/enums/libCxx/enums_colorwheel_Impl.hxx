// 
// File:          enums_colorwheel_Impl.hxx
// Symbol:        enums.colorwheel-v1.0
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for enums.colorwheel
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_enums_colorwheel_Impl_hxx
#define included_enums_colorwheel_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_enums_colorwheel_IOR_h
#include "enums_colorwheel_IOR.h"
#endif
#ifndef included_enums_color_hxx
#include "enums_color.hxx"
#endif
#ifndef included_enums_colorwheel_hxx
#include "enums_colorwheel.hxx"
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


// DO-NOT-DELETE splicer.begin(enums.colorwheel._hincludes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(enums.colorwheel._hincludes)

namespace enums { 

  /**
   * Symbol "enums.colorwheel" (version 1.0)
   */
  class colorwheel_impl : public virtual ::enums::colorwheel 
  // DO-NOT-DELETE splicer.begin(enums.colorwheel._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(enums.colorwheel._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(enums.colorwheel._implementation)
    // Put additional implementation details here...
    // DO-NOT-DELETE splicer.end(enums.colorwheel._implementation)

  public:
    // default constructor, used for data wrapping(required)
    colorwheel_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
    colorwheel_impl( struct enums_colorwheel__object * ior ) : StubBase(ior,
      true) , _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~colorwheel_impl() { _dtor(); }

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
    ::enums::color
    returnback_impl() ;
    /**
     * user defined non-static method.
     */
    bool
    passin_impl (
      /* in */::enums::color c
    )
    ;

    /**
     * user defined non-static method.
     */
    bool
    passout_impl (
      /* out */::enums::color& c
    )
    ;

    /**
     * user defined non-static method.
     */
    bool
    passinout_impl (
      /* inout */::enums::color& c
    )
    ;

    /**
     * user defined non-static method.
     */
    ::enums::color
    passeverywhere_impl (
      /* in */::enums::color c1,
      /* out */::enums::color& c2,
      /* inout */::enums::color& c3
    )
    ;

  };  // end class colorwheel_impl

} // end namespace enums

// DO-NOT-DELETE splicer.begin(enums.colorwheel._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(enums.colorwheel._hmisc)

#endif
