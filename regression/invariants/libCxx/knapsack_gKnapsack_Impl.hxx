// 
// File:          knapsack_gKnapsack_Impl.hxx
// Symbol:        knapsack.gKnapsack-v1.0
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7133M trunk)
// Description:   Server-side implementation for knapsack.gKnapsack
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_knapsack_gKnapsack_Impl_hxx
#define included_knapsack_gKnapsack_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_knapsack_gKnapsack_IOR_h
#include "knapsack_gKnapsack_IOR.h"
#endif
#ifndef included_knapsack_gKnapsack_hxx
#include "knapsack_gKnapsack.hxx"
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


// DO-NOT-DELETE splicer.begin(knapsack.gKnapsack._hincludes)
/*
 * Maximum number of weights.
 */
#define MAX_WEIGHTS 10

#define M_CHECK_POS_SIDL(ARR, I, MAX, ONLYPOS) {\
  ONLYPOS = false; \
  if (MAX > 0) { \
    ONLYPOS = true; \
    for (I=0; (I<MAX) && ONLYPOS; I++) { \
      if (ARR.get(I) <= 0) {\
        ONLYPOS = false;\
      }\
    }\
  }\
}

#define M_COPY_WEIGHTS(SSRC, I, MAX) {\
  for (I=0; I<MAX; I++) { \
    d_weights[I] = SSRC.get(I); \
  }\
}

#define M_THROW(TP, MSG, MNM) {\
  TP _ex = TP::_create(); \
  _ex.setNote(MSG); \
  _ex.add(__FILE__, __LINE__, MNM); \
  throw _ex; \
}

#ifndef VAR_UNUSED
#ifdef __GNUC__
#define VAR_UNUSED __attribute__ ((__unused__))
#else
#define VAR_UNUSED 
#endif /* __GNUC__ */
#endif /* VAR_UNUSED */

static const VAR_UNUSED char* L_MAX_WEIGHTS 
       = "Cannot exceed maximum number of weights.";
static const VAR_UNUSED char* L_POS_WEIGHTS 
       = "Non-positive weights are NOT supported.";

// DO-NOT-DELETE splicer.end(knapsack.gKnapsack._hincludes)

namespace knapsack { 

  /**
   * Symbol "knapsack.gKnapsack" (version 1.0)
   * 
   * gKnapsack:  A good implementation of the knapsack interface.
   */
  class gKnapsack_impl : public virtual ::knapsack::gKnapsack 
  // DO-NOT-DELETE splicer.begin(knapsack.gKnapsack._inherits)
     // Nothing to do here?
  // DO-NOT-DELETE splicer.end(knapsack.gKnapsack._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(knapsack.gKnapsack._implementation)
    int32_t d_nextIndex;
    int32_t d_weights[MAX_WEIGHTS];
    // DO-NOT-DELETE splicer.end(knapsack.gKnapsack._implementation)

  public:
    // default constructor, used for data wrapping(required)
    gKnapsack_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
      gKnapsack_impl( struct knapsack_gKnapsack__object * ior ) : StubBase(ior,
        true), 
    ::knapsack::iKnapsack((ior==NULL) ? NULL : &((*ior).d_knapsack_iknapsack)) ,
      _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~gKnapsack_impl() { _dtor(); }

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

  };  // end class gKnapsack_impl

} // end namespace knapsack

// DO-NOT-DELETE splicer.begin(knapsack.gKnapsack._hmisc)

/*
 * Determine if all entries in the list are positive, returning TRUE
 * if they are or FALSE if they are not.
 */
bool
onlyPos(int32_t* w, int32_t len);

/*
 * Check to see if the two lists match where order does not matter.
 */
bool
sameWeights(int32_t* nW, ::sidl::array<int32_t>& sW, int len);

/*
 * Recursive implementation of the simplified knapsack problem.
 *
 * Based on the algorithm defined in "Data Structures and
 * Algorithms" by Aho, Hopcroft, and Ullman (c) 1983.
 */
bool
solve(int32_t* weights, int32_t t, int32_t i, int32_t n);

// DO-NOT-DELETE splicer.end(knapsack.gKnapsack._hmisc)

#endif
