// 
// File:          Inherit_I_Impl.hxx
// Symbol:        Inherit.I-v1.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for Inherit.I
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_Inherit_I_Impl_hxx
#define included_Inherit_I_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_Inherit_I_IOR_h
#include "Inherit_I_IOR.h"
#endif
#ifndef included_Inherit_H_hxx
#include "Inherit_H.hxx"
#endif
#ifndef included_Inherit_I_hxx
#include "Inherit_I.hxx"
#endif
#ifndef included_sidl_BaseInterface_hxx
#include "sidl_BaseInterface.hxx"
#endif
#ifndef included_sidl_ClassInfo_hxx
#include "sidl_ClassInfo.hxx"
#endif


// DO-NOT-DELETE splicer.begin(Inherit.I._hincludes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(Inherit.I._hincludes)

namespace Inherit { 

  /**
   * Symbol "Inherit.I" (version 1.1)
   */
  class I_impl : public virtual ::Inherit::I 
  // DO-NOT-DELETE splicer.begin(Inherit.I._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(Inherit.I._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(Inherit.I._implementation)
    // Put additional implementation details here...
    // DO-NOT-DELETE splicer.end(Inherit.I._implementation)

  public:
    // default constructor, used for data wrapping(required)
    I_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
      I_impl( struct Inherit_I__object * ior ) : StubBase(ior,true), 
    ::Inherit::A((ior==NULL) ? NULL : &((*ior).d_inherit_h.d_inherit_a)) , 
      _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~I_impl() { _dtor(); }

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
    h_impl() ;
  };  // end class I_impl

} // end namespace Inherit

// DO-NOT-DELETE splicer.begin(Inherit.I._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(Inherit.I._hmisc)

#endif
