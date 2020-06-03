// 
// File:          knapsack_gKnapsack_Impl.cxx
// Symbol:        knapsack.gKnapsack-v1.0
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7133M trunk)
// Description:   Server-side implementation for knapsack.gKnapsack
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "knapsack_gKnapsack_Impl.hxx"

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
// DO-NOT-DELETE splicer.begin(knapsack.gKnapsack._includes)
#include <stdio.h>
#include <string.h>
#include "sidlArray.h"
#include "knapsack_kExcept.hxx"
#include "knapsack_kBadWeightExcept.hxx"

using namespace std;
// DO-NOT-DELETE splicer.end(knapsack.gKnapsack._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
knapsack::gKnapsack_impl::gKnapsack_impl() : StubBase(reinterpret_cast< void*>(
  ::knapsack::gKnapsack::_wrapObj(reinterpret_cast< void*>(this))),false) , 
  _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(knapsack.gKnapsack._ctor2)
  return;
  // DO-NOT-DELETE splicer.end(knapsack.gKnapsack._ctor2)
}

// user defined constructor
void knapsack::gKnapsack_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(knapsack.gKnapsack._ctor)
  d_nextIndex = 0;
  memset(d_weights, 0, (size_t)(MAX_WEIGHTS*sizeof(int32_t)));
  return;
  // DO-NOT-DELETE splicer.end(knapsack.gKnapsack._ctor)
}

// user defined destructor
void knapsack::gKnapsack_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(knapsack.gKnapsack._dtor)
  return;
  // DO-NOT-DELETE splicer.end(knapsack.gKnapsack._dtor)
}

// static class initializer
void knapsack::gKnapsack_impl::_load() {
  // DO-NOT-DELETE splicer.begin(knapsack.gKnapsack._load)
  return;
  // DO-NOT-DELETE splicer.end(knapsack.gKnapsack._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Initialize the knapsack with the specified weights, w.
 */
void
knapsack::gKnapsack_impl::initialize_impl (
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
  // DO-NOT-DELETE splicer.begin(knapsack.gKnapsack.initialize)
    const string myName = "knapsack::gKnapsack::initialize";
    int32_t      i, num;
    bool         onlyPos;

    if (w._not_nil()) {
      num = w.length();   // sidlLength(w._get_ior(), 0);
      if (num <= MAX_WEIGHTS) {
        M_CHECK_POS_SIDL(w, i, num, onlyPos);
        if (onlyPos) {
          M_COPY_WEIGHTS(w, i, num)
          d_nextIndex = num;
        } else {
          M_THROW(knapsack::kBadWeightExcept, L_POS_WEIGHTS, myName)
        }
      } else {
        M_THROW(knapsack::kExcept, L_MAX_WEIGHTS, myName)
      }
    }

    return;  
  // DO-NOT-DELETE splicer.end(knapsack.gKnapsack.initialize)
}

/**
 * Return TRUE if all weights in the knapsack are positive;
 * otherwise, return FALSE.
 */
bool
knapsack::gKnapsack_impl::onlyPosWeights_impl () 
// throws:
//    ::knapsack::kExcept
//    ::sidl::InvViolation
//    ::sidl::RuntimeException
//    ::sidl::PostViolation

{
  // DO-NOT-DELETE splicer.begin(knapsack.gKnapsack.onlyPosWeights)
    return onlyPos(d_weights, d_nextIndex);
  // DO-NOT-DELETE splicer.end(knapsack.gKnapsack.onlyPosWeights)
}

/**
 * Return TRUE if all of the specified weights, w, are in the knapsack
 * or there are no specified weights; otherwise, return FALSE.
 */
bool
knapsack::gKnapsack_impl::hasWeights_impl (
  /* in array<int> */::sidl::array<int32_t>& w ) 
// throws:
//    ::knapsack::kExcept
//    ::sidl::InvViolation
//    ::sidl::PreViolation
//    ::sidl::RuntimeException
//    ::sidl::PostViolation
{
  // DO-NOT-DELETE splicer.begin(knapsack.gKnapsack.hasWeights)
    return sameWeights(d_weights, w, d_nextIndex);
  // DO-NOT-DELETE splicer.end(knapsack.gKnapsack.hasWeights)
}


/**
 * Return TRUE if there is a solution for the specified target
 * weight; otherwise, return FALSE.  Recall a solution is a
 * subset of weights that total exactly to the specified target
 * weight.
 */
bool
knapsack::gKnapsack_impl::hasSolution_impl (
  /* in */int32_t t ) 
// throws:
//    ::knapsack::kExcept
//    ::sidl::InvViolation
//    ::sidl::PreViolation
//    ::sidl::RuntimeException
//    ::sidl::PostViolation
{
  // DO-NOT-DELETE splicer.begin(knapsack.gKnapsack.hasSolution)
    return solve(d_weights, t, 0, d_nextIndex);
  // DO-NOT-DELETE splicer.end(knapsack.gKnapsack.hasSolution)
}


// DO-NOT-DELETE splicer.begin(knapsack.gKnapsack._misc)

/*
 * Determine if all entries in the list are positive, returning TRUE
 * if they are or FALSE if they are not.
 */
bool
onlyPos(int32_t* w, int32_t len) {
  int32_t i;
  bool    isPos = false;

  if (len > 0) {
    isPos = true;
    for (i=0; (i<len) && isPos; i++) {
      if (w[i] <= 0) {
        isPos = false;
      }
    }
  }
  return isPos;
} /* onlyPos(int32_t*, int32_t) */

/*
 * Check to see if the two lists match where order does not matter.
 */
bool
sameWeights(int32_t* nW, ::sidl::array<int32_t>& sW, int32_t len) {
  bool     same = false;
  bool     found;
  int32_t* p;
  int32_t  i, j, num, w;

  if ((nW != NULL) && sW._not_nil()) {
    num = sW.length();   // sidlLength(w._get_ior(), 0);
    if (num == len && num > 0) {
      p = (int32_t*)malloc(num*sizeof(int32_t));
      if (p) {
        memset(p, 0, (size_t)(num*sizeof(int32_t)));
        for (i=0; i<num; i++) {
          w = sW.get(i);
          found = false;
          for (j=0; (j<num) && !found; j++) {
            if ((w == nW[j]) && !p[j]) {
              p[j] = 1;
              found = true;
            }
          }
        }
        same = onlyPos(p, num);
        free(p);
      }
    }  /* else weights list size mismatch so assume will false */
  }  /* else no input weights provided so automatically false */
  return same;
} /* sameWeights(int32_t*, ::sidl::array<int32_t>&, int32_t) */

/*
 * Recursive implementation of the simplified knapsack problem.
 *
 * Based on the algorithm defined in "Data Structures and
 * Algorithms" by Aho, Hopcroft, and Ullman (c) 1983.
 */
bool
solve(int32_t* weights, int32_t t, int32_t i, int32_t n) {
  bool has = false;
  if (t==0) {
    has = true;
  } else if ( (t < 0) || (i >= n) ) {
    has = false;
  } else if (solve(weights, t-weights[i], i+1, n)) {
    has = true;
  } else {
    has = solve(weights, t, i+1, n);
  }
  return has;
} /* solve(int32_t*, int32_t, int32_t, int32_t) */

// DO-NOT-DELETE splicer.end(knapsack.gKnapsack._misc)

