// 
// File:          enums_cartest_Impl.hxx
// Symbol:        enums.cartest-v1.0
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for enums.cartest
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_enums_cartest_Impl_hxx
#define included_enums_cartest_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_enums_cartest_IOR_h
#include "enums_cartest_IOR.h"
#endif
#ifndef included_enums_car_hxx
#include "enums_car.hxx"
#endif
#ifndef included_enums_cartest_hxx
#include "enums_cartest.hxx"
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


// DO-NOT-DELETE splicer.begin(enums.cartest._hincludes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(enums.cartest._hincludes)

namespace enums { 

  /**
   * Symbol "enums.cartest" (version 1.0)
   */
  class cartest_impl : public virtual ::enums::cartest 
  // DO-NOT-DELETE splicer.begin(enums.cartest._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(enums.cartest._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(enums.cartest._implementation)
    // Put additional implementation details here...
    // DO-NOT-DELETE splicer.end(enums.cartest._implementation)

  public:
    // default constructor, used for data wrapping(required)
    cartest_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
    cartest_impl( struct enums_cartest__object * ior ) : StubBase(ior,true) , 
      _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~cartest_impl() { _dtor(); }

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
    ::enums::car
    returnback_impl() ;
    /**
     * user defined non-static method.
     */
    bool
    passin_impl (
      /* in */::enums::car c
    )
    ;

    /**
     * user defined non-static method.
     */
    bool
    passout_impl (
      /* out */::enums::car& c
    )
    ;

    /**
     * user defined non-static method.
     */
    bool
    passinout_impl (
      /* inout */::enums::car& c
    )
    ;

    /**
     * user defined non-static method.
     */
    ::enums::car
    passeverywhere_impl (
      /* in */::enums::car c1,
      /* out */::enums::car& c2,
      /* inout */::enums::car& c3
    )
    ;


    /**
     * All incoming/outgoing arrays should be [ ford, mercedes, porsche ]
     * in that order.
     */
    ::sidl::array< ::enums::car>
    passarray_impl (
      /* in array<enums.car> */::sidl::array< ::enums::car>& c1,
      /* out array<enums.car> */::sidl::array< ::enums::car>& c2,
      /* inout array<enums.car> */::sidl::array< ::enums::car>& c3
    )
    ;

  };  // end class cartest_impl

} // end namespace enums

// DO-NOT-DELETE splicer.begin(enums.cartest._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(enums.cartest._hmisc)

#endif
