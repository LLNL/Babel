// 
// File:          knapsack_npKnapsack_Impl.hxx
// Symbol:        knapsack.npKnapsack-v1.0
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7133M trunk)
// Description:   Server-side implementation for knapsack.npKnapsack
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_knapsack_npKnapsack_Impl_hxx
#define included_knapsack_npKnapsack_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_knapsack_npKnapsack_IOR_h
#include "knapsack_npKnapsack_IOR.h"
#endif
#ifndef included_knapsack_iKnapsack_hxx
#include "knapsack_iKnapsack.hxx"
#endif
#ifndef included_knapsack_kBadWeightExcept_hxx
#include "knapsack_kBadWeightExcept.hxx"
#endif
#ifndef included_knapsack_kExcept_hxx
#include "knapsack_kExcept.hxx"
#endif
#ifndef included_knapsack_kSizeExcept_hxx
#include "knapsack_kSizeExcept.hxx"
#endif
#ifndef included_knapsack_npKnapsack_hxx
#include "knapsack_npKnapsack.hxx"
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
#ifndef included_sidl_InvViolation_hxx
#include "sidl_InvViolation.hxx"
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


// DO-NOT-DELETE splicer.begin(knapsack.npKnapsack._hincludes)
#ifndef included_knapsack_gKnapsack_Impl_hxx
#include "knapsack_gKnapsack_Impl.hxx"
#endif
// DO-NOT-DELETE splicer.end(knapsack.npKnapsack._hincludes)

namespace knapsack { 

  /**
   * Symbol "knapsack.npKnapsack" (version 1.0)
   * 
   * npKnapsack:  An implementation of knapsack that allows non-positive
   * weights.
   */
  class npKnapsack_impl : public virtual ::knapsack::npKnapsack 
  // DO-NOT-DELETE splicer.begin(knapsack.npKnapsack._inherits)
    // Anything needed here???
  // DO-NOT-DELETE splicer.end(knapsack.npKnapsack._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(knapsack.npKnapsack._implementation)
    int32_t d_nextIndex;
    int32_t d_weights[MAX_WEIGHTS];
    // DO-NOT-DELETE splicer.end(knapsack.npKnapsack._implementation)

  public:
    // default constructor, used for data wrapping(required)
    npKnapsack_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
      npKnapsack_impl( struct knapsack_npKnapsack__object * ior ) : StubBase(
        ior,true), 
    ::knapsack::iKnapsack((ior==NULL) ? NULL : &((*ior).d_knapsack_iknapsack)) ,
      _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~npKnapsack_impl() { _dtor(); }

    // user defined destruction
    void _dtor();

    // true if this object was created by a user newing the impl
    inline bool _isWrapped() {return _wrapped;}

    // static class initializer
    static void _load();

  public:


    /**
     * Initialize the knapsack with the specified weights, w.
     */
    void
    initialize_impl (
      /* in array<int> */::sidl::array<int32_t>& w
    )
    // throws:
    //    ::knapsack::kBadWeightExcept
    //    ::knapsack::kExcept
    //    ::knapsack::kSizeExcept
    //    ::sidl::InvViolation
    //    ::sidl::PostViolation
    //    ::sidl::PreViolation
    //    ::sidl::RuntimeException
    ;


    /**
     * Return TRUE if all weights in the knapsack are positive;
     * otherwise, return FALSE.
     */
    bool
    onlyPosWeights_impl() // throws:
    //    ::knapsack::kExcept
    //    ::sidl::InvViolation
    //    ::sidl::RuntimeException
    //    ::sidl::PostViolation
    ;

    /**
     * Return TRUE if all of the specified weights, w, are in the knapsack
     * or there are no specified weights; otherwise, return FALSE.
     */
    bool
    hasWeights_impl (
      /* in array<int> */::sidl::array<int32_t>& w
    )
    // throws:
    //    ::knapsack::kExcept
    //    ::sidl::InvViolation
    //    ::sidl::PreViolation
    //    ::sidl::RuntimeException
    //    ::sidl::PostViolation
    ;


    /**
     * Return TRUE if there is a solution for the specified target
     * weight; otherwise, return FALSE.  Recall a solution is a
     * subset of weights that total exactly to the specified target
     * weight.
     */
    bool
    hasSolution_impl (
      /* in */int32_t t
    )
    // throws:
    //    ::knapsack::kExcept
    //    ::sidl::InvViolation
    //    ::sidl::PreViolation
    //    ::sidl::RuntimeException
    //    ::sidl::PostViolation
    ;

  };  // end class npKnapsack_impl

} // end namespace knapsack

// DO-NOT-DELETE splicer.begin(knapsack.npKnapsack._hmisc)
// Nothing needed here
// DO-NOT-DELETE splicer.end(knapsack.npKnapsack._hmisc)

#endif
