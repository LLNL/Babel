// 
// File:          wrapper_Data_Impl.hxx
// Symbol:        wrapper.Data-v1.0
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for wrapper.Data
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_wrapper_Data_Impl_hxx
#define included_wrapper_Data_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_wrapper_Data_IOR_h
#include "wrapper_Data_IOR.h"
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


// DO-NOT-DELETE splicer.begin(wrapper.Data._hincludes)
// Insert-Code-Here {wrapper.Data._includes} (includes or arbitrary code)
// DO-NOT-DELETE splicer.end(wrapper.Data._hincludes)

namespace wrapper { 

  /**
   * Symbol "wrapper.Data" (version 1.0)
   */
  class Data_impl : public virtual ::wrapper::Data 
  // DO-NOT-DELETE splicer.begin(wrapper.Data._inherits)
  // Insert-Code-Here {wrapper.Data._inherits} (optional inheritance here)
  // DO-NOT-DELETE splicer.end(wrapper.Data._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(wrapper.Data._implementation)
  public:
    char* d_string;
    int d_int;
    char* d_ctorTest;
    // DO-NOT-DELETE splicer.end(wrapper.Data._implementation)

  public:
    // default constructor, used for data wrapping(required)
    Data_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
    Data_impl( struct wrapper_Data__object * ior ) : StubBase(ior,true) , 
      _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~Data_impl() { _dtor(); }

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
    setString_impl (
      /* in */const ::std::string& s
    )
    ;

    /**
     * user defined non-static method.
     */
    void
    setInt_impl (
      /* in */int32_t i
    )
    ;

  };  // end class Data_impl

} // end namespace wrapper

// DO-NOT-DELETE splicer.begin(wrapper.Data._hmisc)
// Insert-Code-Here {wrapper.Data._misc} (miscellaneous things)
// DO-NOT-DELETE splicer.end(wrapper.Data._hmisc)

#endif
