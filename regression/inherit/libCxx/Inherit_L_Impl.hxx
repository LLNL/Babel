// 
// File:          Inherit_L_Impl.hxx
// Symbol:        Inherit.L-v1.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for Inherit.L
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_Inherit_L_Impl_hxx
#define included_Inherit_L_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_Inherit_L_IOR_h
#include "Inherit_L_IOR.h"
#endif
#ifndef included_Inherit_A_hxx
#include "Inherit_A.hxx"
#endif
#ifndef included_Inherit_A2_hxx
#include "Inherit_A2.hxx"
#endif
#ifndef included_Inherit_L_hxx
#include "Inherit_L.hxx"
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


// DO-NOT-DELETE splicer.begin(Inherit.L._hincludes)
// Insert-Code-Here {Inherit.L._includes} (includes or arbitrary code)
// DO-NOT-DELETE splicer.end(Inherit.L._hincludes)

namespace Inherit { 

  /**
   * Symbol "Inherit.L" (version 1.1)
   */
  class L_impl : public virtual ::Inherit::L 
  // DO-NOT-DELETE splicer.begin(Inherit.L._inherits)
  // Insert-Code-Here {Inherit.L._inherits} (optional inheritance here)
  // DO-NOT-DELETE splicer.end(Inherit.L._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(Inherit.L._implementation)
    // Insert-Code-Here {Inherit.L._implementation} (additional details)
    // DO-NOT-DELETE splicer.end(Inherit.L._implementation)

  public:
    // default constructor, used for data wrapping(required)
    L_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
      L_impl( struct Inherit_L__object * ior ) : StubBase(ior,true), 
      ::Inherit::A((ior==NULL) ? NULL : &((*ior).d_inherit_a)),
    ::Inherit::A2((ior==NULL) ? NULL : &((*ior).d_inherit_a2)) , _wrapped(
      false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~L_impl() { _dtor(); }

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
    ::std::string
    a_impl() ;
    /**
     * user defined non-static method.
     */
    ::std::string
    a_impl (
      /* in */int32_t i
    )
    ;

    /**
     * user defined non-static method.
     */
    ::std::string
    l_impl() ;
  };  // end class L_impl

} // end namespace Inherit

// DO-NOT-DELETE splicer.begin(Inherit.L._hmisc)
  // Insert-Code-Here {Inherit.L._misc} (miscellaneous things)
// DO-NOT-DELETE splicer.end(Inherit.L._hmisc)

#endif
