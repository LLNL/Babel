// 
// File:          Overload_BClass_Impl.hxx
// Symbol:        Overload.BClass-v1.0
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for Overload.BClass
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_Overload_BClass_Impl_hxx
#define included_Overload_BClass_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_Overload_BClass_IOR_h
#include "Overload_BClass_IOR.h"
#endif
#ifndef included_Overload_AClass_hxx
#include "Overload_AClass.hxx"
#endif
#ifndef included_Overload_BClass_hxx
#include "Overload_BClass.hxx"
#endif
#ifndef included_sidl_BaseInterface_hxx
#include "sidl_BaseInterface.hxx"
#endif
#ifndef included_sidl_ClassInfo_hxx
#include "sidl_ClassInfo.hxx"
#endif


// DO-NOT-DELETE splicer.begin(Overload.BClass._hincludes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(Overload.BClass._hincludes)

namespace Overload { 

  /**
   * Symbol "Overload.BClass" (version 1.0)
   * 
   * This class is passed into the overloaded method as another example
   * of passing classes.
   */
  class BClass_impl : public virtual ::Overload::BClass 
  // DO-NOT-DELETE splicer.begin(Overload.BClass._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(Overload.BClass._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(Overload.BClass._implementation)
    // Put additional implementation details here...
    // DO-NOT-DELETE splicer.end(Overload.BClass._implementation)

  public:
    // default constructor, used for data wrapping(required)
    BClass_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
    BClass_impl( struct Overload_BClass__object * ior ) : StubBase(ior,true) , 
      _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~BClass_impl() { _dtor(); }

    // user defined destruction
    void _dtor();

    // true if this object was created by a user newing the impl
    inline bool _isWrapped() {return _wrapped;}

    // static class initializer
    static void _load();

  public:

  };  // end class BClass_impl

} // end namespace Overload

// DO-NOT-DELETE splicer.begin(Overload.BClass._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(Overload.BClass._hmisc)

#endif
