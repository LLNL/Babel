// 
// File:          wrapper_User_Impl.hxx
// Symbol:        wrapper.User-v1.0
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for wrapper.User
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_wrapper_User_Impl_hxx
#define included_wrapper_User_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_wrapper_User_IOR_h
#include "wrapper_User_IOR.h"
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
#ifndef included_wrapper_Data_hxx
#include "wrapper_Data.hxx"
#endif
#ifndef included_wrapper_User_hxx
#include "wrapper_User.hxx"
#endif


// DO-NOT-DELETE splicer.begin(wrapper.User._hincludes)
// Insert-Code-Here {wrapper.User._includes} (includes or arbitrary code)
// DO-NOT-DELETE splicer.end(wrapper.User._hincludes)

namespace wrapper { 

  /**
   * Symbol "wrapper.User" (version 1.0)
   */
  class User_impl : public virtual ::wrapper::User 
  // DO-NOT-DELETE splicer.begin(wrapper.User._inherits)
  // Insert-Code-Here {wrapper.User._inherits} (optional inheritance here)
  // DO-NOT-DELETE splicer.end(wrapper.User._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(wrapper.User._implementation)
    // DO-NOT-DELETE splicer.end(wrapper.User._implementation)

  public:
    // default constructor, used for data wrapping(required)
    User_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
    User_impl( struct wrapper_User__object * ior ) : StubBase(ior,true) , 
      _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~User_impl() { _dtor(); }

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
    void
    accept_impl (
      /* in */::wrapper::Data& data
    )
    ;

  };  // end class User_impl

} // end namespace wrapper

// DO-NOT-DELETE splicer.begin(wrapper.User._hmisc)
// Insert-Code-Here {wrapper.User._misc} (miscellaneous things)
// DO-NOT-DELETE splicer.end(wrapper.User._hmisc)

#endif
