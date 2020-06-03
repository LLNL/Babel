/*
 * File:          knapsack_npKnapsack_Impl.c
 * Symbol:        knapsack.npKnapsack-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7133M trunk)
 * Description:   Server-side implementation for knapsack.npKnapsack
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "knapsack.npKnapsack" (version 1.0)
 * 
 * npKnapsack:  An implementation of knapsack that allows non-positive
 * weights.
 */

#include "knapsack_npKnapsack_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(knapsack.npKnapsack._includes) */
#include <stdio.h>
#include <string.h>
#include "sidlArray.h"
#include "knapsack_kExcept.h"
#include "knapsack_kBadWeightExcept.h"
//#include "knapsack_gKnapsack_Impl.h"
/* DO-NOT-DELETE splicer.end(knapsack.npKnapsack._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_knapsack_npKnapsack__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_knapsack_npKnapsack__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(knapsack.npKnapsack._load) */
    return;
    /* DO-NOT-DELETE splicer.end(knapsack.npKnapsack._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_knapsack_npKnapsack__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_knapsack_npKnapsack__ctor(
  /* in */ knapsack_npKnapsack self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(knapsack.npKnapsack._ctor) */
    struct knapsack_npKnapsack__data* dptr = (struct knapsack_npKnapsack__data*)
                    malloc(sizeof(struct knapsack_npKnapsack__data));
    if (dptr) {
      // initialize elements of dptr here
      memset(dptr->d_weights, 0, (size_t)(MAX_WEIGHTS*sizeof(int32_t)));
      dptr->d_nextIndex = 0;
      knapsack_npKnapsack__set_data(self, dptr);
    } else {
      sidl_MemAllocException ex =
           sidl_MemAllocException_getSingletonException(_ex);
      SIDL_CHECK(*_ex);
      sidl_MemAllocException_setNote(ex, "Out of memory.",
           _ex); SIDL_CHECK(*_ex);
      sidl_MemAllocException_add(ex, __FILE__, __LINE__,
           "knapsack.npKnapsack._ctor", _ex);
      SIDL_CHECK(*_ex);
      *_ex = (sidl_BaseInterface)ex;
    }

    EXIT:;
    return;
    /* DO-NOT-DELETE splicer.end(knapsack.npKnapsack._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_knapsack_npKnapsack__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_knapsack_npKnapsack__ctor2(
  /* in */ knapsack_npKnapsack self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(knapsack.npKnapsack._ctor2) */
    return;
    /* DO-NOT-DELETE splicer.end(knapsack.npKnapsack._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_knapsack_npKnapsack__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_knapsack_npKnapsack__dtor(
  /* in */ knapsack_npKnapsack self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(knapsack.npKnapsack._dtor) */
    struct knapsack_npKnapsack__data* dptr = 
                    knapsack_npKnapsack__get_data(self);
    if (dptr) {
      memset(dptr->d_weights, 0, (size_t)(MAX_WEIGHTS*sizeof(int32_t)));
      memset(dptr, 0, sizeof(struct knapsack_npKnapsack__data));
      free(dptr);
      knapsack_npKnapsack__set_data(self, NULL);
    } else {
      /* WARNING:  This should NEVER happen. */
      SIDL_THROW(*_ex, knapsack_kExcept, L_DPTR_MISSING);
    }

    EXIT:;
    return;
    /* DO-NOT-DELETE splicer.end(knapsack.npKnapsack._dtor) */
  }
}

/*
 * Initialize the knapsack with the specified weights, w.
 */

#undef __FUNC__
#define __FUNC__ "impl_knapsack_npKnapsack_initialize"

#ifdef __cplusplus
extern "C"
#endif
void
impl_knapsack_npKnapsack_initialize(
  /* in */ knapsack_npKnapsack self,
  /* in array<int> */ struct sidl_int__array* w,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(knapsack.npKnapsack.initialize) */
    int32_t num = 0;

    struct knapsack_npKnapsack__data* dptr = 
                    knapsack_npKnapsack__get_data(self);
    if (dptr) {
      if (w) {
        num = sidlLength(w, 0);
        if (num <= MAX_WEIGHTS) {
          memcpy(dptr->d_weights, sidl_int__array_first(w),
                 (size_t)(num*sizeof(int32_t)));
          dptr->d_nextIndex = num;
        } else {
          SIDL_THROW(*_ex, knapsack_kSizeExcept, L_MAX_WEIGHTS);
        }
      } /* else no weights provied => no solution */
    } else {
      /* WARNING:  This should NEVER happen. */
      SIDL_THROW(*_ex, knapsack_kExcept, L_DPTR_MISSING);
    }

    EXIT:;
    return;
    /* DO-NOT-DELETE splicer.end(knapsack.npKnapsack.initialize) */
  }
}


/*
 * Return TRUE if all weights in the knapsack are positive;
 * otherwise, return FALSE.
 */

#undef __FUNC__
#define __FUNC__ "impl_knapsack_npKnapsack_onlyPosWeights"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_knapsack_npKnapsack_onlyPosWeights(
  /* in */ knapsack_npKnapsack self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(knapsack.npKnapsack.onlyPosWeights) */
    sidl_bool arePos = TRUE;

    struct knapsack_npKnapsack__data* dptr = 
                    knapsack_npKnapsack__get_data(self);
    if (dptr) {
      arePos = onlyPos(dptr->d_weights, dptr->d_nextIndex);
    } else {
      /* WARNING:  This should NEVER happen. */
      SIDL_THROW(*_ex, knapsack_kExcept, L_DPTR_MISSING);
    }

    EXIT:;
    return arePos;
    /* DO-NOT-DELETE splicer.end(knapsack.npKnapsack.onlyPosWeights) */
  }
}

/*
 * Return TRUE if all of the specified weights, w, are in the knapsack
 * or there are no specified weights; otherwise, return FALSE.
 */

#undef __FUNC__
#define __FUNC__ "impl_knapsack_npKnapsack_hasWeights"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_knapsack_npKnapsack_hasWeights(
  /* in */ knapsack_npKnapsack self,
  /* in array<int> */ struct sidl_int__array* w,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(knapsack.npKnapsack.hasWeights) */
    sidl_bool has = FALSE;

    struct knapsack_npKnapsack__data* dptr = 
                    knapsack_npKnapsack__get_data(self);
    if (dptr) {
      has = sameWeights(dptr->d_weights, w, dptr->d_nextIndex);
    } else {
      /* WARNING:  This should NEVER happen. */
      SIDL_THROW(*_ex, knapsack_kExcept, L_DPTR_MISSING);
    }

    EXIT:;
    return has;
    /* DO-NOT-DELETE splicer.end(knapsack.npKnapsack.hasWeights) */
  }
}


/*
 * Return TRUE if there is a solution for the specified target
 * weight; otherwise, return FALSE.  Recall a solution is a
 * subset of weights that total exactly to the specified target
 * weight.
 */

#undef __FUNC__
#define __FUNC__ "impl_knapsack_npKnapsack_hasSolution"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_knapsack_npKnapsack_hasSolution(
  /* in */ knapsack_npKnapsack self,
  /* in */ int32_t t,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(knapsack.npKnapsack.hasSolution) */
    sidl_bool has = FALSE;

    struct knapsack_npKnapsack__data* dptr = 
                    knapsack_npKnapsack__get_data(self);

    if (dptr) {
      has = solve(dptr->d_weights, t, 0, dptr->d_nextIndex);
    } else {
      /* WARNING:  This should NEVER happen. */
      SIDL_THROW(*_ex, knapsack_kExcept, L_DPTR_MISSING);
    }

    EXIT:;
    return has;
    /* DO-NOT-DELETE splicer.end(knapsack.npKnapsack.hasSolution) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Nothing needed here */
/* DO-NOT-DELETE splicer.end(_misc) */

