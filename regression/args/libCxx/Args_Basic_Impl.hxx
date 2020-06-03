// 
// File:          Args_Basic_Impl.hxx
// Symbol:        Args.Basic-v1.0
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for Args.Basic
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_Args_Basic_Impl_hxx
#define included_Args_Basic_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_Args_Basic_IOR_h
#include "Args_Basic_IOR.h"
#endif
#ifndef included_Args_Basic_hxx
#include "Args_Basic.hxx"
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


// DO-NOT-DELETE splicer.begin(Args.Basic._hincludes)
// Insert-Code-Here {Args.Basic._includes} (includes or arbitrary code)
// DO-NOT-DELETE splicer.end(Args.Basic._hincludes)

namespace Args { 

  /**
   * Symbol "Args.Basic" (version 1.0)
   */
  class Basic_impl : public virtual ::Args::Basic 
  // DO-NOT-DELETE splicer.begin(Args.Basic._inherits)
  // Insert-Code-Here {Args.Basic._inherits} (optional inheritance here)
  // DO-NOT-DELETE splicer.end(Args.Basic._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(Args.Basic._implementation)
    // Insert-Code-Here {Args.Basic._implementation} (additional details)
    // DO-NOT-DELETE splicer.end(Args.Basic._implementation)

  public:
    // default constructor, used for data wrapping(required)
    Basic_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
    Basic_impl( struct Args_Basic__object * ior ) : StubBase(ior,true) , 
      _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~Basic_impl() { _dtor(); }

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
    bool
    returnbackbool_impl() ;
    /**
     * user defined non-static method.
     */
    bool
    passinbool_impl (
      /* in */bool b
    )
    ;

    /**
     * user defined non-static method.
     */
    bool
    passoutbool_impl (
      /* out */bool& b
    )
    ;

    /**
     * user defined non-static method.
     */
    bool
    passinoutbool_impl (
      /* inout */bool& b
    )
    ;

    /**
     * user defined non-static method.
     */
    bool
    passeverywherebool_impl (
      /* in */bool b1,
      /* out */bool& b2,
      /* inout */bool& b3
    )
    ;

    /**
     * user defined non-static method.
     */
    char
    returnbackchar_impl() ;
    /**
     * user defined non-static method.
     */
    bool
    passinchar_impl (
      /* in */char c
    )
    ;

    /**
     * user defined non-static method.
     */
    bool
    passoutchar_impl (
      /* out */char& c
    )
    ;

    /**
     * user defined non-static method.
     */
    bool
    passinoutchar_impl (
      /* inout */char& c
    )
    ;

    /**
     * user defined non-static method.
     */
    char
    passeverywherechar_impl (
      /* in */char c1,
      /* out */char& c2,
      /* inout */char& c3
    )
    ;

    /**
     * user defined non-static method.
     */
    int32_t
    returnbackint_impl() ;
    /**
     * user defined non-static method.
     */
    bool
    passinint_impl (
      /* in */int32_t i
    )
    ;

    /**
     * user defined non-static method.
     */
    bool
    passoutint_impl (
      /* out */int32_t& i
    )
    ;

    /**
     * user defined non-static method.
     */
    bool
    passinoutint_impl (
      /* inout */int32_t& i
    )
    ;

    /**
     * user defined non-static method.
     */
    int32_t
    passeverywhereint_impl (
      /* in */int32_t i1,
      /* out */int32_t& i2,
      /* inout */int32_t& i3
    )
    ;

    /**
     * user defined non-static method.
     */
    int64_t
    returnbacklong_impl() ;
    /**
     * user defined non-static method.
     */
    bool
    passinlong_impl (
      /* in */int64_t l
    )
    ;

    /**
     * user defined non-static method.
     */
    bool
    passoutlong_impl (
      /* out */int64_t& l
    )
    ;

    /**
     * user defined non-static method.
     */
    bool
    passinoutlong_impl (
      /* inout */int64_t& l
    )
    ;

    /**
     * user defined non-static method.
     */
    int64_t
    passeverywherelong_impl (
      /* in */int64_t l1,
      /* out */int64_t& l2,
      /* inout */int64_t& l3
    )
    ;

    /**
     * user defined non-static method.
     */
    float
    returnbackfloat_impl() ;
    /**
     * user defined non-static method.
     */
    bool
    passinfloat_impl (
      /* in */float f
    )
    ;

    /**
     * user defined non-static method.
     */
    bool
    passoutfloat_impl (
      /* out */float& f
    )
    ;

    /**
     * user defined non-static method.
     */
    bool
    passinoutfloat_impl (
      /* inout */float& f
    )
    ;

    /**
     * user defined non-static method.
     */
    float
    passeverywherefloat_impl (
      /* in */float f1,
      /* out */float& f2,
      /* inout */float& f3
    )
    ;

    /**
     * user defined non-static method.
     */
    double
    returnbackdouble_impl() ;
    /**
     * user defined non-static method.
     */
    bool
    passindouble_impl (
      /* in */double d
    )
    ;

    /**
     * user defined non-static method.
     */
    bool
    passoutdouble_impl (
      /* out */double& d
    )
    ;

    /**
     * user defined non-static method.
     */
    bool
    passinoutdouble_impl (
      /* inout */double& d
    )
    ;

    /**
     * user defined non-static method.
     */
    double
    passeverywheredouble_impl (
      /* in */double d1,
      /* out */double& d2,
      /* inout */double& d3
    )
    ;

    /**
     * user defined non-static method.
     */
    ::std::complex<float>
    returnbackfcomplex_impl() ;
    /**
     * user defined non-static method.
     */
    bool
    passinfcomplex_impl (
      /* in */const ::std::complex<float>& c
    )
    ;

    /**
     * user defined non-static method.
     */
    bool
    passoutfcomplex_impl (
      /* out */::std::complex<float>& c
    )
    ;

    /**
     * user defined non-static method.
     */
    bool
    passinoutfcomplex_impl (
      /* inout */::std::complex<float>& c
    )
    ;

    /**
     * user defined non-static method.
     */
    ::std::complex<float>
    passeverywherefcomplex_impl (
      /* in */const ::std::complex<float>& c1,
      /* out */::std::complex<float>& c2,
      /* inout */::std::complex<float>& c3
    )
    ;

    /**
     * user defined non-static method.
     */
    ::std::complex<double>
    returnbackdcomplex_impl() ;
    /**
     * user defined non-static method.
     */
    bool
    passindcomplex_impl (
      /* in */const ::std::complex<double>& c
    )
    ;

    /**
     * user defined non-static method.
     */
    bool
    passoutdcomplex_impl (
      /* out */::std::complex<double>& c
    )
    ;

    /**
     * user defined non-static method.
     */
    bool
    passinoutdcomplex_impl (
      /* inout */::std::complex<double>& c
    )
    ;

    /**
     * user defined non-static method.
     */
    ::std::complex<double>
    passeverywheredcomplex_impl (
      /* in */const ::std::complex<double>& c1,
      /* out */::std::complex<double>& c2,
      /* inout */::std::complex<double>& c3
    )
    ;

  };  // end class Basic_impl

} // end namespace Args

// DO-NOT-DELETE splicer.begin(Args.Basic._hmisc)
// Insert-Code-Here {Args.Basic._misc} (miscellaneous things)
// DO-NOT-DELETE splicer.end(Args.Basic._hmisc)

#endif
