// 
// File:          enums_numbertest_Impl.hxx
// Symbol:        enums.numbertest-v1.0
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for enums.numbertest
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_enums_numbertest_Impl_hxx
#define included_enums_numbertest_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_enums_numbertest_IOR_h
#include "enums_numbertest_IOR.h"
#endif
#ifndef included_enums_number_hxx
#include "enums_number.hxx"
#endif
#ifndef included_enums_numbertest_hxx
#include "enums_numbertest.hxx"
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


// DO-NOT-DELETE splicer.begin(enums.numbertest._hincludes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(enums.numbertest._hincludes)

namespace enums { 

  /**
   * Symbol "enums.numbertest" (version 1.0)
   */
  class numbertest_impl : public virtual ::enums::numbertest 
  // DO-NOT-DELETE splicer.begin(enums.numbertest._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(enums.numbertest._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(enums.numbertest._implementation)
    // Put additional implementation details here...
    // DO-NOT-DELETE splicer.end(enums.numbertest._implementation)

  public:
    // default constructor, used for data wrapping(required)
    numbertest_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
    numbertest_impl( struct enums_numbertest__object * ior ) : StubBase(ior,
      true) , _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~numbertest_impl() { _dtor(); }

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
    ::enums::number
    returnback_impl() ;
    /**
     * user defined non-static method.
     */
    bool
    passin_impl (
      /* in */::enums::number n
    )
    ;

    /**
     * user defined non-static method.
     */
    bool
    passout_impl (
      /* out */::enums::number& n
    )
    ;

    /**
     * user defined non-static method.
     */
    bool
    passinout_impl (
      /* inout */::enums::number& n
    )
    ;

    /**
     * user defined non-static method.
     */
    ::enums::number
    passeverywhere_impl (
      /* in */::enums::number n1,
      /* out */::enums::number& n2,
      /* inout */::enums::number& n3
    )
    ;

  };  // end class numbertest_impl

} // end namespace enums

// DO-NOT-DELETE splicer.begin(enums.numbertest._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(enums.numbertest._hmisc)

#endif
