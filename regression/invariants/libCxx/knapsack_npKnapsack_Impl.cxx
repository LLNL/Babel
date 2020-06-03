// 
// File:          knapsack_npKnapsack_Impl.cxx
// Symbol:        knapsack.npKnapsack-v1.0
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7133M trunk)
// Description:   Server-side implementation for knapsack.npKnapsack
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "knapsack_npKnapsack_Impl.hxx"

// 
// Includes for all method dependencies.
// 
#ifndef included_knapsack_kBadWeightExcept_hxx
#include "knapsack_kBadWeightExcept.hxx"
#endif
#ifndef included_knapsack_kExcept_hxx
#include "knapsack_kExcept.hxx"
#endif
#ifndef included_knapsack_kSizeExcept_hxx
#include "knapsack_kSizeExcept.hxx"
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
#ifndef included_sidl_NotImplementedException_hxx
#include "sidl_NotImplementedException.hxx"
#endif
// DO-NOT-DELETE splicer.begin(knapsack.npKnapsack._includes)
#include <stdio.h>
#include <string.h>
#include "sidlArray.h"
#include "knapsack_kExcept.hxx"
#include "knapsack_kBadWeightExcept.hxx"
//#include "knapsack_gKnapsack_Impl.hxx"

using namespace std;
// DO-NOT-DELETE splicer.end(knapsack.npKnapsack._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
knapsack::npKnapsack_impl::npKnapsack_impl() : StubBase(reinterpret_cast< 
  void*>(::knapsack::npKnapsack::_wrapObj(reinterpret_cast< void*>(this))),
  false) , _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(knapsack.npKnapsack._ctor2)
  return;
  // DO-NOT-DELETE splicer.end(knapsack.npKnapsack._ctor2)
}

// user defined constructor
void knapsack::npKnapsack_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(knapsack.npKnapsack._ctor)
    memset(d_weights, 0, (size_t)(MAX_WEIGHTS*sizeof(int32_t)));
    d_nextIndex = 0;
    return;
  // DO-NOT-DELETE splicer.end(knapsack.npKnapsack._ctor)
}

// user defined destructor
void knapsack::npKnapsack_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(knapsack.npKnapsack._dtor)
    return;
  // DO-NOT-DELETE splicer.end(knapsack.npKnapsack._dtor)
}

// static class initializer
void knapsack::npKnapsack_impl::_load() {
  // DO-NOT-DELETE splicer.begin(knapsack.npKnapsack._load)
  return;
  // DO-NOT-DELETE splicer.end(knapsack.npKnapsack._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Initialize the knapsack with the specified weights, w.
 */
void
knapsack::npKnapsack_impl::initialize_impl (
  /* in array<int> */::sidl::array<int32_t>& w ) 
// throws:
//    ::knapsack::kBadWeightExcept
//    ::knapsack::kExcept
//    ::knapsack::kSizeExcept
//    ::sidl::InvViolation
//    ::sidl::PostViolation
//    ::sidl::PreViolation
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(knapsack.npKnapsack.initialize)
    const string myName = "knapsack::npKnapsack::initialize";
    int          i, num;

    if (w._not_nil()) {
      num = w.length();   // sidlLength(w._get_ior(), 0);
      if (num <= MAX_WEIGHTS) {
        M_COPY_WEIGHTS(w, i, num)
        d_nextIndex = num;
      } else {
        M_THROW(knapsack::kExcept, L_MAX_WEIGHTS, myName)
      }
    }

    return;
  // DO-NOT-DELETE splicer.end(knapsack.npKnapsack.initialize)
}

/**
 * Return TRUE if all weights in the knapsack are positive;
 * otherwise, return FALSE.
 */
bool
knapsack::npKnapsack_impl::onlyPosWeights_impl () 
// throws:
//    ::knapsack::kExcept
//    ::sidl::InvViolation
//    ::sidl::RuntimeException
//    ::sidl::PostViolation

{
  // DO-NOT-DELETE splicer.begin(knapsack.npKnapsack.onlyPosWeights)
    return onlyPos(d_weights, d_nextIndex);;
  // DO-NOT-DELETE splicer.end(knapsack.npKnapsack.onlyPosWeights)
}

/**
 * Return TRUE if all of the specified weights, w, are in the knapsack
 * or there are no specified weights; otherwise, return FALSE.
 */
bool
knapsack::npKnapsack_impl::hasWeights_impl (
  /* in array<int> */::sidl::array<int32_t>& w ) 
// throws:
//    ::knapsack::kExcept
//    ::sidl::InvViolation
//    ::sidl::PreViolation
//    ::sidl::RuntimeException
//    ::sidl::PostViolation
{
  // DO-NOT-DELETE splicer.begin(knapsack.npKnapsack.hasWeights)
    return sameWeights(d_weights, w, d_nextIndex);
  // DO-NOT-DELETE splicer.end(knapsack.npKnapsack.hasWeights)
}

/**
 * Return TRUE if there is a solution for the specified target
 * weight; otherwise, return FALSE.  Recall a solution is a
 * subset of weights that total exactly to the specified target
 * weight.
 */
bool
knapsack::npKnapsack_impl::hasSolution_impl (
  /* in */int32_t t ) 
// throws:
//    ::knapsack::kExcept
//    ::sidl::InvViolation
//    ::sidl::PreViolation
//    ::sidl::RuntimeException
//    ::sidl::PostViolation
{
  // DO-NOT-DELETE splicer.begin(knapsack.npKnapsack.hasSolution)
    return solve(d_weights, t, 0, d_nextIndex);
  // DO-NOT-DELETE splicer.end(knapsack.npKnapsack.hasSolution)
}


// DO-NOT-DELETE splicer.begin(knapsack.npKnapsack._misc)
// Nothing to do here
// DO-NOT-DELETE splicer.end(knapsack.npKnapsack._misc)

