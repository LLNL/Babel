// 
// File:          Inherit_G_Impl.hxx
// Symbol:        Inherit.G-v1.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for Inherit.G
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_Inherit_G_Impl_hxx
#define included_Inherit_G_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_Inherit_G_IOR_h
#include "Inherit_G_IOR.h"
#endif
#ifndef included_Inherit_D_hxx
#include "Inherit_D.hxx"
#endif
#ifndef included_Inherit_G_hxx
#include "Inherit_G.hxx"
#endif
#ifndef included_sidl_BaseInterface_hxx
#include "sidl_BaseInterface.hxx"
#endif
#ifndef included_sidl_ClassInfo_hxx
#include "sidl_ClassInfo.hxx"
#endif


// DO-NOT-DELETE splicer.begin(Inherit.G._hincludes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(Inherit.G._hincludes)

namespace Inherit { 

  /**
   * Symbol "Inherit.G" (version 1.1)
   */
  class G_impl : public virtual ::Inherit::G 
  // DO-NOT-DELETE splicer.begin(Inherit.G._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(Inherit.G._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(Inherit.G._implementation)
    // Put additional implementation details here...
    // DO-NOT-DELETE splicer.end(Inherit.G._implementation)

  public:
    // default constructor, used for data wrapping(required)
    G_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
      G_impl( struct Inherit_G__object * ior ) : StubBase(ior,true), 
    ::Inherit::A((ior==NULL) ? NULL : &((*ior).d_inherit_d.d_inherit_a)) , 
      _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~G_impl() { _dtor(); }

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
    g_impl() ;
  };  // end class G_impl

} // end namespace Inherit

// DO-NOT-DELETE splicer.begin(Inherit.G._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(Inherit.G._hmisc)

#endif
