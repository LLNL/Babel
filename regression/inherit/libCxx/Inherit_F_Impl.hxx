// 
// File:          Inherit_F_Impl.hxx
// Symbol:        Inherit.F-v1.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for Inherit.F
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_Inherit_F_Impl_hxx
#define included_Inherit_F_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_Inherit_F_IOR_h
#include "Inherit_F_IOR.h"
#endif
#ifndef included_Inherit_A_hxx
#include "Inherit_A.hxx"
#endif
#ifndef included_Inherit_B_hxx
#include "Inherit_B.hxx"
#endif
#ifndef included_Inherit_C_hxx
#include "Inherit_C.hxx"
#endif
#ifndef included_Inherit_F_hxx
#include "Inherit_F.hxx"
#endif
#ifndef included_sidl_BaseInterface_hxx
#include "sidl_BaseInterface.hxx"
#endif
#ifndef included_sidl_ClassInfo_hxx
#include "sidl_ClassInfo.hxx"
#endif


// DO-NOT-DELETE splicer.begin(Inherit.F._hincludes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(Inherit.F._hincludes)

namespace Inherit { 

  /**
   * Symbol "Inherit.F" (version 1.1)
   */
  class F_impl : public virtual ::Inherit::F 
  // DO-NOT-DELETE splicer.begin(Inherit.F._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(Inherit.F._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(Inherit.F._implementation)
    // Put additional implementation details here...
    // DO-NOT-DELETE splicer.end(Inherit.F._implementation)

  public:
    // default constructor, used for data wrapping(required)
    F_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
      F_impl( struct Inherit_F__object * ior ) : StubBase(ior,true), 
      ::Inherit::A((ior==NULL) ? NULL : &((*ior).d_inherit_a)),
    ::Inherit::B((ior==NULL) ? NULL : &((*ior).d_inherit_b)) , _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~F_impl() { _dtor(); }

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
    f_impl() ;
    /**
     * user defined non-static method.
     */
    ::std::string
    a_impl() ;
    /**
     * user defined non-static method.
     */
    ::std::string
    b_impl() ;
  };  // end class F_impl

} // end namespace Inherit

// DO-NOT-DELETE splicer.begin(Inherit.F._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(Inherit.F._hmisc)

#endif
