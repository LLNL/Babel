// 
// File:          Inherit_E_Impl.hxx
// Symbol:        Inherit.E-v1.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for Inherit.E
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_Inherit_E_Impl_hxx
#define included_Inherit_E_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_Inherit_E_IOR_h
#include "Inherit_E_IOR.h"
#endif
#ifndef included_Inherit_C_hxx
#include "Inherit_C.hxx"
#endif
#ifndef included_Inherit_E_hxx
#include "Inherit_E.hxx"
#endif
#ifndef included_sidl_BaseInterface_hxx
#include "sidl_BaseInterface.hxx"
#endif
#ifndef included_sidl_ClassInfo_hxx
#include "sidl_ClassInfo.hxx"
#endif


// DO-NOT-DELETE splicer.begin(Inherit.E._hincludes)
// Put additional includes or other arbitrary code here...
#include "Inherit_C_Impl.hxx"
// DO-NOT-DELETE splicer.end(Inherit.E._hincludes)

namespace Inherit { 

  /**
   * Symbol "Inherit.E" (version 1.1)
   */
  class E_impl : public virtual ::Inherit::E 
  // DO-NOT-DELETE splicer.begin(Inherit.E._inherits)
					       , public virtual C_impl
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(Inherit.E._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(Inherit.E._implementation)
    // Put additional implementation details here...
    // DO-NOT-DELETE splicer.end(Inherit.E._implementation)

  public:
    // default constructor, used for data wrapping(required)
    E_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
    E_impl( struct Inherit_E__object * ior ) : StubBase(ior,true) , _wrapped(
      false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~E_impl() { _dtor(); }

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
    e_impl() ;
  };  // end class E_impl

} // end namespace Inherit

// DO-NOT-DELETE splicer.begin(Inherit.E._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(Inherit.E._hmisc)

#endif
