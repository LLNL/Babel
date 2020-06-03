// 
// File:          Inherit_H_Impl.hxx
// Symbol:        Inherit.H-v1.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for Inherit.H
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_Inherit_H_Impl_hxx
#define included_Inherit_H_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_Inherit_H_IOR_h
#include "Inherit_H_IOR.h"
#endif
#ifndef included_Inherit_A_hxx
#include "Inherit_A.hxx"
#endif
#ifndef included_Inherit_H_hxx
#include "Inherit_H.hxx"
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


// DO-NOT-DELETE splicer.begin(Inherit.H._hincludes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(Inherit.H._hincludes)

namespace Inherit { 

  /**
   * Symbol "Inherit.H" (version 1.1)
   */
  class H_impl : public virtual ::Inherit::H 
  // DO-NOT-DELETE splicer.begin(Inherit.H._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(Inherit.H._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(Inherit.H._implementation)
    // Put additional implementation details here...
    // DO-NOT-DELETE splicer.end(Inherit.H._implementation)

  private:
    // default constructor, do not use! 
    H_impl();
    public:
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
      H_impl( struct Inherit_H__object * ior ) : StubBase(ior,true), 
    ::Inherit::A((ior==NULL) ? NULL : &((*ior).d_inherit_a)) , _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~H_impl() { _dtor(); }

    // user defined destruction
    void _dtor();

    // true if this object was created by a user newing the impl
    inline bool _isWrapped() {return _wrapped;}

    // static class initializer
    static void _load();

  public:

  };  // end class H_impl

} // end namespace Inherit

// DO-NOT-DELETE splicer.begin(Inherit.H._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(Inherit.H._hmisc)

#endif
