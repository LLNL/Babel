/*
 * File:          knapsack_nwKnapsack_Impl.c
 * Symbol:        knapsack.nwKnapsack-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7133M trunk)
 * Description:   Server-side implementation for knapsack.nwKnapsack
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "knapsack.nwKnapsack" (version 1.0)
 * 
 * nwKnapsack:  An implementation of knapsack that drops about half 
 * of the input weights.
 */

#include "knapsack_nwKnapsack_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack._includes) */
#include <stdio.h>
#include <string.h>
#include "sidlArray.h"
#include "knapsack_kExcept.h"
#include "knapsack_kBadWeightExcept.h"
//#include "knapsack_gKnapsack_Impl.h"
/* DO-NOT-DELETE splicer.end(knapsack.nwKnapsack._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_knapsack_nwKnapsack__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_knapsack_nwKnapsack__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack._load) */
    return;
    /* DO-NOT-DELETE splicer.end(knapsack.nwKnapsack._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_knapsack_nwKnapsack__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_knapsack_nwKnapsack__ctor(
  /* in */ knapsack_nwKnapsack self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack._ctor) */
    struct knapsack_nwKnapsack__data* dptr = (struct knapsack_nwKnapsack__data*)
                    malloc(sizeof(struct knapsack_nwKnapsack__data));
    if (dptr) {
      // initialize elements of dptr here
      memset(dptr->d_weights, 0, (size_t)(MAX_WEIGHTS*sizeof(int32_t)));
      dptr->d_nextIndex = 0;
      knapsack_nwKnapsack__set_data(self, dptr);
    } else {
      sidl_MemAllocException ex =
           sidl_MemAllocException_getSingletonException(_ex); SIDL_CHECK(*_ex);
      sidl_MemAllocException_setNote(ex, "Out of memory.",
           _ex); SIDL_CHECK(*_ex);
      sidl_MemAllocException_add(ex, __FILE__, __LINE__,
           "knapsack.nwKnapsack._ctor", _ex); SIDL_CHECK(*_ex);
      *_ex = (sidl_BaseInterface)ex;
    }

    EXIT:;
    return;
    /* DO-NOT-DELETE splicer.end(knapsack.nwKnapsack._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_knapsack_nwKnapsack__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_knapsack_nwKnapsack__ctor2(
  /* in */ knapsack_nwKnapsack self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack._ctor2) */
    return;
    /* DO-NOT-DELETE splicer.end(knapsack.nwKnapsack._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_knapsack_nwKnapsack__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_knapsack_nwKnapsack__dtor(
  /* in */ knapsack_nwKnapsack self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack._dtor) */
    struct knapsack_nwKnapsack__data* dptr = 
                   knapsack_nwKnapsack__get_data(self);
    if (dptr) {
      memset(dptr->d_weights, 0, (size_t)(MAX_WEIGHTS*sizeof(int32_t)));
      memset(dptr, 0, sizeof(struct knapsack_nwKnapsack__data));
      free(dptr);
      knapsack_nwKnapsack__set_data(self, NULL);
    } else {
      /* WARNING:  This should NEVER happen. */
      SIDL_THROW(*_ex, knapsack_kExcept, L_DPTR_MISSING);
    }

    EXIT:;
    return;
    /* DO-NOT-DELETE splicer.end(knapsack.nwKnapsack._dtor) */
  }
}

/*
 * Initialize the knapsack with the specified weights, w.
 */

#undef __FUNC__
#define __FUNC__ "impl_knapsack_nwKnapsack_initialize"

#ifdef __cplusplus
extern "C"
#endif
void
impl_knapsack_nwKnapsack_initialize(
  /* in */ knapsack_nwKnapsack self,
  /* in array<int> */ struct sidl_int__array* w,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack.initialize) */
    int32_t   i, num;
    int32_t   val;
    sidl_bool onlyPos;

    struct knapsack_nwKnapsack__data* dptr = 
                    knapsack_nwKnapsack__get_data(self);
    if (dptr) {
      if (w) {
        num = sidlLength(w, 0);
        if (num <= MAX_WEIGHTS) {
          M_CHECK_POS_SIDL(w, i, num, onlyPos);
          if (onlyPos) {
            for (i=0; i<num; i++) {
              if (i%2) {
                val = sidl_int__array_get1(w, i);
                dptr->d_weights[dptr->d_nextIndex] = val;
                dptr->d_nextIndex += 1;
              }
            }
          } else {
            SIDL_THROW(*_ex, knapsack_kBadWeightExcept, L_POS_WEIGHTS);
          }
        } else {
          SIDL_THROW(*_ex, knapsack_kExcept, L_MAX_WEIGHTS);
        }
      } /* else no weights provided => no solution */
    } else {
      SIDL_THROW(*_ex, knapsack_kExcept, L_DPTR_MISSING);
    }

    EXIT:;
    return;
    /* DO-NOT-DELETE splicer.end(knapsack.nwKnapsack.initialize) */
  }
}


/*
 * Return TRUE if all weights in the knapsack are positive;
 * otherwise, return FALSE.
 */

#undef __FUNC__
#define __FUNC__ "impl_knapsack_nwKnapsack_onlyPosWeights"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_knapsack_nwKnapsack_onlyPosWeights(
  /* in */ knapsack_nwKnapsack self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack.onlyPosWeights) */
    sidl_bool arePos = TRUE;

    struct knapsack_nwKnapsack__data* dptr = 
                    knapsack_nwKnapsack__get_data(self);
    if (dptr) {
      arePos = onlyPos(dptr->d_weights, dptr->d_nextIndex);
    } else {
      /* WARNING:  This should NEVER happen. */
      SIDL_THROW(*_ex, knapsack_kExcept, L_DPTR_MISSING);
    }

    EXIT:;
    return arePos;
    /* DO-NOT-DELETE splicer.end(knapsack.nwKnapsack.onlyPosWeights) */
  }
}

/*
 * Return TRUE if all of the specified weights, w, are in the knapsack
 * or there are no specified weights; otherwise, return FALSE.
 */

#undef __FUNC__
#define __FUNC__ "impl_knapsack_nwKnapsack_hasWeights"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_knapsack_nwKnapsack_hasWeights(
  /* in */ knapsack_nwKnapsack self,
  /* in array<int> */ struct sidl_int__array* w,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack.hasWeights) */
    sidl_bool has = FALSE;

    struct knapsack_nwKnapsack__data* dptr = 
                    knapsack_nwKnapsack__get_data(self);
    if (dptr) {
      has = sameWeights(dptr->d_weights, w, dptr->d_nextIndex);
    } else {
      /* WARNING:  This should NEVER happen. */
      SIDL_THROW(*_ex, knapsack_kExcept, L_DPTR_MISSING);
    }

    EXIT:;
    return has;
    /* DO-NOT-DELETE splicer.end(knapsack.nwKnapsack.hasWeights) */
  }
}

/*
 * Return TRUE if there is a solution for the specified target
 * weight; otherwise, return FALSE.  Recall a solution is a
 * subset of weights that total exactly to the specified target
 * weight.
 */

#undef __FUNC__
#define __FUNC__ "impl_knapsack_nwKnapsack_hasSolution"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_knapsack_nwKnapsack_hasSolution(
  /* in */ knapsack_nwKnapsack self,
  /* in */ int32_t t,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack.hasSolution) */
    sidl_bool has = FALSE;

    struct knapsack_nwKnapsack__data* dptr = 
                    knapsack_nwKnapsack__get_data(self);
    if (dptr) {
      has = solve(dptr->d_weights, t, 0, dptr->d_nextIndex);
    } else {
      /* WARNING:  This should NEVER happen. */
      SIDL_THROW(*_ex, knapsack_kExcept, L_DPTR_MISSING);
    }

    EXIT:;
    return has;
    /* DO-NOT-DELETE splicer.end(knapsack.nwKnapsack.hasSolution) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Nothing needed here */
/* DO-NOT-DELETE splicer.end(_misc) */

