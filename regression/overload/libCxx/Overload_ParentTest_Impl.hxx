// 
// File:          Overload_ParentTest_Impl.hxx
// Symbol:        Overload.ParentTest-v1.0
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for Overload.ParentTest
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_Overload_ParentTest_Impl_hxx
#define included_Overload_ParentTest_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_Overload_ParentTest_IOR_h
#include "Overload_ParentTest_IOR.h"
#endif
#ifndef included_Overload_ParentTest_hxx
#include "Overload_ParentTest.hxx"
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


// DO-NOT-DELETE splicer.begin(Overload.ParentTest._hincludes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(Overload.ParentTest._hincludes)

namespace Overload { 

  /**
   * Symbol "Overload.ParentTest" (version 1.0)
   * 
   * This class is used as the work-horse, returning the value passed
   * in.
   */
  class ParentTest_impl : public virtual ::Overload::ParentTest 
  // DO-NOT-DELETE splicer.begin(Overload.ParentTest._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(Overload.ParentTest._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(Overload.ParentTest._implementation)
    // Put additional implementation details here...
    // DO-NOT-DELETE splicer.end(Overload.ParentTest._implementation)

  public:
    // default constructor, used for data wrapping(required)
    ParentTest_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
    ParentTest_impl( struct Overload_ParentTest__object * ior ) : StubBase(ior,
      true) , _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~ParentTest_impl() { _dtor(); }

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
    /**
     * user defined non-static method.
     */
    int32_t
    getValue_impl (
      /* in */int32_t v
    )
    ;

    /**
     * user defined non-static method.
     */
    bool
    getValue_impl (
      /* in */bool v
    )
    ;

    /**
     * user defined non-static method.
     */
    double
    getValue_impl (
      /* in */double v
    )
    ;

    /**
     * user defined non-static method.
     */
    ::std::complex<double>
    getValue_impl (
      /* in */const ::std::complex<double>& v
    )
    ;

    /**
     * user defined non-static method.
     */
    float
    getValue_impl (
      /* in */float v
    )
    ;

    /**
     * user defined non-static method.
     */
    ::std::complex<float>
    getValue_impl (
      /* in */const ::std::complex<float>& v
    )
    ;

    /**
     * user defined non-static method.
     */
    ::std::string
    getValue_impl (
      /* in */const ::std::string& v
    )
    ;

  };  // end class ParentTest_impl

} // end namespace Overload

// DO-NOT-DELETE splicer.begin(Overload.ParentTest._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(Overload.ParentTest._hmisc)

#endif
