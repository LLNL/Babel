// 
// File:          Overload_Test_Impl.hxx
// Symbol:        Overload.Test-v1.0
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for Overload.Test
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_Overload_Test_Impl_hxx
#define included_Overload_Test_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_Overload_Test_IOR_h
#include "Overload_Test_IOR.h"
#endif
#ifndef included_Overload_AClass_hxx
#include "Overload_AClass.hxx"
#endif
#ifndef included_Overload_AnException_hxx
#include "Overload_AnException.hxx"
#endif
#ifndef included_Overload_BClass_hxx
#include "Overload_BClass.hxx"
#endif
#ifndef included_Overload_ParentTest_hxx
#include "Overload_ParentTest.hxx"
#endif
#ifndef included_Overload_Test_hxx
#include "Overload_Test.hxx"
#endif
#ifndef included_sidl_BaseInterface_hxx
#include "sidl_BaseInterface.hxx"
#endif
#ifndef included_sidl_ClassInfo_hxx
#include "sidl_ClassInfo.hxx"
#endif


// DO-NOT-DELETE splicer.begin(Overload.Test._hincludes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(Overload.Test._hincludes)

namespace Overload { 

  /**
   * Symbol "Overload.Test" (version 1.0)
   * 
   * This class is used as the work-horse, returning the value passed
   * in.
   */
  class Test_impl : public virtual ::Overload::Test 
  // DO-NOT-DELETE splicer.begin(Overload.Test._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(Overload.Test._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(Overload.Test._implementation)
    // Put additional implementation details here...
    // DO-NOT-DELETE splicer.end(Overload.Test._implementation)

  public:
    // default constructor, used for data wrapping(required)
    Test_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
    Test_impl( struct Overload_Test__object * ior ) : StubBase(ior,true) , 
      _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~Test_impl() { _dtor(); }

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
    double
    getValue_impl (
      /* in */int32_t a,
      /* in */double b
    )
    ;

    /**
     * user defined non-static method.
     */
    double
    getValue_impl (
      /* in */double a,
      /* in */int32_t b
    )
    ;

    /**
     * user defined non-static method.
     */
    double
    getValue_impl (
      /* in */int32_t a,
      /* in */double b,
      /* in */float c
    )
    ;

    /**
     * user defined non-static method.
     */
    double
    getValue_impl (
      /* in */double a,
      /* in */int32_t b,
      /* in */float c
    )
    ;

    /**
     * user defined non-static method.
     */
    double
    getValue_impl (
      /* in */int32_t a,
      /* in */float b,
      /* in */double c
    )
    ;

    /**
     * user defined non-static method.
     */
    double
    getValue_impl (
      /* in */double a,
      /* in */float b,
      /* in */int32_t c
    )
    ;

    /**
     * user defined non-static method.
     */
    double
    getValue_impl (
      /* in */float a,
      /* in */int32_t b,
      /* in */double c
    )
    ;

    /**
     * user defined non-static method.
     */
    double
    getValue_impl (
      /* in */float a,
      /* in */double b,
      /* in */int32_t c
    )
    ;

    /**
     * user defined non-static method.
     */
    ::std::string
    getValue_impl (
      /* in */::Overload::AnException& v
    )
    ;

    /**
     * user defined non-static method.
     */
    int32_t
    getValue_impl (
      /* in */::Overload::AClass& v
    )
    ;

    /**
     * user defined non-static method.
     */
    int32_t
    getValue_impl (
      /* in */::Overload::BClass& v
    )
    ;

  };  // end class Test_impl

} // end namespace Overload

// DO-NOT-DELETE splicer.begin(Overload.Test._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(Overload.Test._hmisc)

#endif
