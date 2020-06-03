// 
// File:          knapsack_nwKnapsack_Impl.cxx
// Symbol:        knapsack.nwKnapsack-v1.0
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7133M trunk)
// Description:   Server-side implementation for knapsack.nwKnapsack
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "knapsack_nwKnapsack_Impl.hxx"

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
// DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack._includes)
#include <stdio.h>
#include <string.h>
#include "sidlArray.h"
#include "knapsack_kExcept.hxx"
#include "knapsack_kBadWeightExcept.hxx"
//#include "knapsack_gKnapsack_Impl.hxx"

using namespace std;
// DO-NOT-DELETE splicer.end(knapsack.nwKnapsack._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
knapsack::nwKnapsack_impl::nwKnapsack_impl() : StubBase(reinterpret_cast< 
  void*>(::knapsack::nwKnapsack::_wrapObj(reinterpret_cast< void*>(this))),
  false) , _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack._ctor2)
  return;
  // DO-NOT-DELETE splicer.end(knapsack.nwKnapsack._ctor2)
}

// user defined constructor
void knapsack::nwKnapsack_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack._ctor)
    memset(d_weights, 0, (size_t)(MAX_WEIGHTS*sizeof(int32_t)));
    d_nextIndex = 0;
    return;
  // DO-NOT-DELETE splicer.end(knapsack.nwKnapsack._ctor)
}

// user defined destructor
void knapsack::nwKnapsack_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack._dtor)
    return;
  // DO-NOT-DELETE splicer.end(knapsack.nwKnapsack._dtor)
}

// static class initializer
void knapsack::nwKnapsack_impl::_load() {
  // DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack._load)
  return;
  // DO-NOT-DELETE splicer.end(knapsack.nwKnapsack._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Initialize the knapsack with the specified weights, w.
 */
void
knapsack::nwKnapsack_impl::initialize_impl (
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
  // DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack.initialize)
    const string myName = "knapsack::nwKnapsack::initialize";
    int          i, num;
    int32_t      val;
    sidl_bool    onlyPos;

    if (w._not_nil()) {
      num = w.length();   // sidlLength(w._get_ior(), 0);
      if (num <= MAX_WEIGHTS) {
        M_CHECK_POS_SIDL(w, i, num, onlyPos);
        if (onlyPos) {
          for (i=0; i<num; i++) {
            if (i%2) {
              val = w.get(i);
              d_weights[d_nextIndex] = val;
              d_nextIndex += 1;
            }
          }
        } else {
          M_THROW(knapsack::kBadWeightExcept, L_POS_WEIGHTS, myName)
        }
      } else {
        M_THROW(knapsack::kExcept, L_MAX_WEIGHTS, myName)
      }
    }

    return;
  // DO-NOT-DELETE splicer.end(knapsack.nwKnapsack.initialize)
}

/**
 * Return TRUE if all weights in the knapsack are positive;
 * otherwise, return FALSE.
 */
bool
knapsack::nwKnapsack_impl::onlyPosWeights_impl () 
// throws:
//    ::knapsack::kExcept
//    ::sidl::InvViolation
//    ::sidl::RuntimeException
//    ::sidl::PostViolation

{
  // DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack.onlyPosWeights)
    return onlyPos(d_weights, d_nextIndex);
  // DO-NOT-DELETE splicer.end(knapsack.nwKnapsack.onlyPosWeights)
}

/**
 * Return TRUE if all of the specified weights, w, are in the knapsack
 * or there are no specified weights; otherwise, return FALSE.
 */
bool
knapsack::nwKnapsack_impl::hasWeights_impl (
  /* in array<int> */::sidl::array<int32_t>& w ) 
// throws:
//    ::knapsack::kExcept
//    ::sidl::InvViolation
//    ::sidl::PreViolation
//    ::sidl::RuntimeException
//    ::sidl::PostViolation
{
  // DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack.hasWeights)
    return sameWeights(d_weights, w, d_nextIndex);
  // DO-NOT-DELETE splicer.end(knapsack.nwKnapsack.hasWeights)
}

/**
 * Return TRUE if there is a solution for the specified target
 * weight; otherwise, return FALSE.  Recall a solution is a
 * subset of weights that total exactly to the specified target
 * weight.
 */
bool
knapsack::nwKnapsack_impl::hasSolution_impl (
  /* in */int32_t t ) 
// throws:
//    ::knapsack::kExcept
//    ::sidl::InvViolation
//    ::sidl::PreViolation
//    ::sidl::RuntimeException
//    ::sidl::PostViolation
{
  // DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack.hasSolution)
    return solve(d_weights, t, 0, d_nextIndex);
  // DO-NOT-DELETE splicer.end(knapsack.nwKnapsack.hasSolution)
}


// DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack._misc)
// Nothing to do here
// DO-NOT-DELETE splicer.end(knapsack.nwKnapsack._misc)

