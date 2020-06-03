// 
// File:          Inherit_K_Impl.hxx
// Symbol:        Inherit.K-v1.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for Inherit.K
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_Inherit_K_Impl_hxx
#define included_Inherit_K_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_Inherit_K_IOR_h
#include "Inherit_K_IOR.h"
#endif
#ifndef included_Inherit_A2_hxx
#include "Inherit_A2.hxx"
#endif
#ifndef included_Inherit_H_hxx
#include "Inherit_H.hxx"
#endif
#ifndef included_Inherit_K_hxx
#include "Inherit_K.hxx"
#endif
#ifndef included_sidl_BaseInterface_hxx
#include "sidl_BaseInterface.hxx"
#endif
#ifndef included_sidl_ClassInfo_hxx
#include "sidl_ClassInfo.hxx"
#endif


// DO-NOT-DELETE splicer.begin(Inherit.K._hincludes)
// Insert-Code-Here {Inherit.K._includes} (includes or arbitrary code)
// DO-NOT-DELETE splicer.end(Inherit.K._hincludes)

namespace Inherit { 

  /**
   * Symbol "Inherit.K" (version 1.1)
   */
  class K_impl : public virtual ::Inherit::K 
  // DO-NOT-DELETE splicer.begin(Inherit.K._inherits)
  // Insert-Code-Here {Inherit.K._inherits} (optional inheritance here)
  // DO-NOT-DELETE splicer.end(Inherit.K._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(Inherit.K._implementation)
    // Insert-Code-Here {Inherit.K._implementation} (additional details)
    // DO-NOT-DELETE splicer.end(Inherit.K._implementation)

  public:
    // default constructor, used for data wrapping(required)
    K_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
      K_impl( struct Inherit_K__object * ior ) : StubBase(ior,true), 
      ::Inherit::A2((ior==NULL) ? NULL : &((*ior).d_inherit_a2)),
    ::Inherit::A((ior==NULL) ? NULL : &((*ior).d_inherit_h.d_inherit_a)) , 
      _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~K_impl() { _dtor(); }

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
    a_impl (
      /* in */int32_t i
    )
    ;

    /**
     * user defined non-static method.
     */
    ::std::string
    a_impl() ;
    /**
     * user defined non-static method.
     */
    ::std::string
    h_impl() ;
    /**
     * user defined non-static method.
     */
    ::std::string
    k_impl() ;
  };  // end class K_impl

} // end namespace Inherit

// DO-NOT-DELETE splicer.begin(Inherit.K._hmisc)
  // Insert-Code-Here {Inherit.K._misc} (miscellaneous things)
// DO-NOT-DELETE splicer.end(Inherit.K._hmisc)

#endif
