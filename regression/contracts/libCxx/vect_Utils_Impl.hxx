// 
// File:          vect_Utils_Impl.hxx
// Symbol:        vect.Utils-v1.0
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for vect.Utils
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_vect_Utils_Impl_hxx
#define included_vect_Utils_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_vect_Utils_IOR_h
#include "vect_Utils_IOR.h"
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
#ifndef included_sidl_PostViolation_hxx
#include "sidl_PostViolation.hxx"
#endif
#ifndef included_sidl_PreViolation_hxx
#include "sidl_PreViolation.hxx"
#endif
#ifndef included_sidl_RuntimeException_hxx
#include "sidl_RuntimeException.hxx"
#endif
#ifndef included_vect_BadLevel_hxx
#include "vect_BadLevel.hxx"
#endif
#ifndef included_vect_Utils_hxx
#include "vect_Utils.hxx"
#endif
#ifndef included_vect_vDivByZeroExcept_hxx
#include "vect_vDivByZeroExcept.hxx"
#endif
#ifndef included_vect_vNegValExcept_hxx
#include "vect_vNegValExcept.hxx"
#endif


// DO-NOT-DELETE splicer.begin(vect.Utils._hincludes)
// Nothing to do here.
// DO-NOT-DELETE splicer.end(vect.Utils._hincludes)

namespace vect { 

  /**
   * Symbol "vect.Utils" (version 1.0)
   */
  class Utils_impl : public virtual ::vect::Utils 
  // DO-NOT-DELETE splicer.begin(vect.Utils._inherits)
  // Nothing to do here.
  // DO-NOT-DELETE splicer.end(vect.Utils._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(vect.Utils._implementation)
    // Nothing to do here.
    // DO-NOT-DELETE splicer.end(vect.Utils._implementation)

  public:
    // default constructor, used for data wrapping(required)
    Utils_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
    Utils_impl( struct vect_Utils__object * ior ) : StubBase(ior,true) , 
      _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~Utils_impl() { _dtor(); }

    // user defined destruction
    void _dtor();

    // true if this object was created by a user newing the impl
    inline bool _isWrapped() {return _wrapped;}

    // static class initializer
    static void _load();

  public:

    /**
     * boolean result operations 
     * Return TRUE if the specified vector is the zero vector, within the
     * given tolerance level; otherwise, return FALSE.
     */
    static bool
    vuIsZero_impl (
      /* in array<> */::sidl::basearray& u,
      /* in */double tol
    )
    // throws:
    //    ::sidl::PreViolation
    //    ::sidl::RuntimeException
    //    ::sidl::PostViolation
    ;


    /**
     * Return TRUE if the specified vector is the unit vector, within the
     * given tolerance level; otherwise, return FALSE.
     */
    static bool
    vuIsUnit_impl (
      /* in array<> */::sidl::basearray& u,
      /* in */double tol
    )
    // throws:
    //    ::sidl::PreViolation
    //    ::sidl::RuntimeException
    //    ::vect::vNegValExcept
    //    ::sidl::PostViolation
    ;


    /**
     * Return TRUE if the specified vectors are equal, within the given
     * tolerance level; otherwise, return FALSE.
     */
    static bool
    vuAreEqual_impl (
      /* in array<> */::sidl::basearray& u,
      /* in array<> */::sidl::basearray& v,
      /* in */double tol
    )
    // throws:
    //    ::sidl::PreViolation
    //    ::sidl::RuntimeException
    //    ::sidl::PostViolation
    ;


    /**
     * Return TRUE if the specified vectors are orthogonal, within the given
     * tolerance; otherwise, return FALSE.
     */
    static bool
    vuAreOrth_impl (
      /* in array<> */::sidl::basearray& u,
      /* in array<> */::sidl::basearray& v,
      /* in */double tol
    )
    // throws:
    //    ::sidl::PreViolation
    //    ::sidl::RuntimeException
    //    ::sidl::PostViolation
    ;


    /**
     * Return TRUE if the Schwarz (or Cauchy-Schwarz) inequality holds, within
     * the given tolerance; otherwise, return FALSE.
     */
    static bool
    vuSchwarzHolds_impl (
      /* in array<> */::sidl::basearray& u,
      /* in array<> */::sidl::basearray& v,
      /* in */double tol
    )
    // throws:
    //    ::sidl::PreViolation
    //    ::sidl::RuntimeException
    //    ::vect::vNegValExcept
    //    ::sidl::PostViolation
    ;


    /**
     * Return TRUE if the Minkowski (or triangle) inequality holds, within the
     * given tolerance; otherwise, return FALSE.
     */
    static bool
    vuTriIneqHolds_impl (
      /* in array<> */::sidl::basearray& u,
      /* in array<> */::sidl::basearray& v,
      /* in */double tol
    )
    // throws:
    //    ::sidl::PreViolation
    //    ::sidl::RuntimeException
    //    ::vect::vNegValExcept
    //    ::sidl::PostViolation
    ;


    /**
     * double result operations 
     * Return the norm (or length) of the specified vector.
     * 
     * Note that the size exception is given here simply because the
     * implementation is leveraging the vuDot() method.  Also the tolerance
     * is included to enable the caller to specify the tolerance used in
     * contract checking.
     */
    static double
    vuNorm_impl (
      /* in array<> */::sidl::basearray& u,
      /* in */double tol,
      /* in */::vect::BadLevel badLevel
    )
    // throws:
    //    ::sidl::PostViolation
    //    ::sidl::PreViolation
    //    ::sidl::RuntimeException
    //    ::vect::vNegValExcept
    ;


    /**
     * Return the dot (, inner, or scalar) product of the specified vectors.
     */
    static double
    vuDot_impl (
      /* in array<> */::sidl::basearray& u,
      /* in array<> */::sidl::basearray& v,
      /* in */double tol,
      /* in */::vect::BadLevel badLevel
    )
    // throws:
    //    ::sidl::PostViolation
    //    ::sidl::PreViolation
    //    ::sidl::RuntimeException
    ;


    /**
     * vector result operations 
     * Return the (scalar) product of the specified vector.
     */
    static ::sidl::basearray
    vuProduct_impl (
      /* in */double a,
      /* in array<> */::sidl::basearray& u,
      /* in */::vect::BadLevel badLevel
    )
    // throws:
    //    ::sidl::PostViolation
    //    ::sidl::PreViolation
    //    ::sidl::RuntimeException
    ;


    /**
     * Return the negation of the specified vector.
     */
    static ::sidl::basearray
    vuNegate_impl (
      /* in array<> */::sidl::basearray& u,
      /* in */::vect::BadLevel badLevel
    )
    // throws:
    //    ::sidl::PostViolation
    //    ::sidl::PreViolation
    //    ::sidl::RuntimeException
    ;


    /**
     * Return the normalizaton of the specified vector.
     * 
     * Note the tolerance is included because the implementation invokes 
     * vuDot().
     */
    static ::sidl::basearray
    vuNormalize_impl (
      /* in array<> */::sidl::basearray& u,
      /* in */double tol,
      /* in */::vect::BadLevel badLevel
    )
    // throws:
    //    ::sidl::PostViolation
    //    ::sidl::PreViolation
    //    ::sidl::RuntimeException
    //    ::vect::vDivByZeroExcept
    ;


    /**
     * Return the sum of the specified vectors.
     */
    static ::sidl::basearray
    vuSum_impl (
      /* in array<> */::sidl::basearray& u,
      /* in array<> */::sidl::basearray& v,
      /* in */::vect::BadLevel badLevel
    )
    // throws:
    //    ::sidl::PostViolation
    //    ::sidl::PreViolation
    //    ::sidl::RuntimeException
    ;


    /**
     * Return the difference of the specified vectors.
     */
    static ::sidl::basearray
    vuDiff_impl (
      /* in array<> */::sidl::basearray& u,
      /* in array<> */::sidl::basearray& v,
      /* in */::vect::BadLevel badLevel
    )
    // throws:
    //    ::sidl::PostViolation
    //    ::sidl::PreViolation
    //    ::sidl::RuntimeException
    ;


  };  // end class Utils_impl

} // end namespace vect

// DO-NOT-DELETE splicer.begin(vect.Utils._hmisc)
// Nothing to do here.
// DO-NOT-DELETE splicer.end(vect.Utils._hmisc)

#endif
