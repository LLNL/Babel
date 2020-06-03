// 
// File:          Inherit_C_Impl.hxx
// Symbol:        Inherit.C-v1.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for Inherit.C
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_Inherit_C_Impl_hxx
#define included_Inherit_C_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_Inherit_C_IOR_h
#include "Inherit_C_IOR.h"
#endif
#ifndef included_Inherit_C_hxx
#include "Inherit_C.hxx"
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


// DO-NOT-DELETE splicer.begin(Inherit.C._hincludes)
#include <string>
#include <iostream>
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(Inherit.C._hincludes)

namespace Inherit { 

  /**
   * Symbol "Inherit.C" (version 1.1)
   */
  class C_impl : public virtual ::Inherit::C 
  // DO-NOT-DELETE splicer.begin(Inherit.C._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(Inherit.C._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(Inherit.C._implementation)
  public:
    // Put additional implementation details here...
    // DO-NOT-DELETE splicer.end(Inherit.C._implementation)

  public:
    // default constructor, used for data wrapping(required)
    C_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
    C_impl( struct Inherit_C__object * ior ) : StubBase(ior,true) , _wrapped(
      false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~C_impl() { _dtor(); }

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
    c_impl() ;
  };  // end class C_impl

} // end namespace Inherit

// DO-NOT-DELETE splicer.begin(Inherit.C._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(Inherit.C._hmisc)

#endif
