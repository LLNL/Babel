// 
// File:          Overload_AClass_Impl.hxx
// Symbol:        Overload.AClass-v1.0
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for Overload.AClass
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_Overload_AClass_Impl_hxx
#define included_Overload_AClass_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_Overload_AClass_IOR_h
#include "Overload_AClass_IOR.h"
#endif
#ifndef included_Overload_AClass_hxx
#include "Overload_AClass.hxx"
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


// DO-NOT-DELETE splicer.begin(Overload.AClass._hincludes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(Overload.AClass._hincludes)

namespace Overload { 

  /**
   * Symbol "Overload.AClass" (version 1.0)
   * 
   * This class is passed into the overloaded method as an example
   * of passing classes.
   */
  class AClass_impl : public virtual ::Overload::AClass 
  // DO-NOT-DELETE splicer.begin(Overload.AClass._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(Overload.AClass._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(Overload.AClass._implementation)
    // Put additional implementation details here...
    // DO-NOT-DELETE splicer.end(Overload.AClass._implementation)

  public:
    // default constructor, used for data wrapping(required)
    AClass_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
    AClass_impl( struct Overload_AClass__object * ior ) : StubBase(ior,true) , 
      _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~AClass_impl() { _dtor(); }

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
    int32_t
    getValue_impl() ;
  };  // end class AClass_impl

} // end namespace Overload

// DO-NOT-DELETE splicer.begin(Overload.AClass._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(Overload.AClass._hmisc)

#endif
