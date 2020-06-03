/*
 * File:          knapsack_gKnapsack_Impl.c
 * Symbol:        knapsack.gKnapsack-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7133M trunk)
 * Description:   Server-side implementation for knapsack.gKnapsack
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "knapsack.gKnapsack" (version 1.0)
 * 
 * gKnapsack:  A good implementation of the knapsack interface.
 */

#include "knapsack_gKnapsack_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(knapsack.gKnapsack._includes) */
#include <stdio.h>
#include <string.h>
#include "sidlArray.h"
#include "knapsack_kExcept.h"
#include "knapsack_kBadWeightExcept.h"

#define KSIDL_CHECK(EX_VAR) \
  SIDL_CHECK((struct sidl_BaseInterface__object*)(EX_VAR))

/* DO-NOT-DELETE splicer.end(knapsack.gKnapsack._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_knapsack_gKnapsack__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_knapsack_gKnapsack__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(knapsack.gKnapsack._load) */
    return;
    /* DO-NOT-DELETE splicer.end(knapsack.gKnapsack._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_knapsack_gKnapsack__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_knapsack_gKnapsack__ctor(
  /* in */ knapsack_gKnapsack self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(knapsack.gKnapsack._ctor) */
    struct knapsack_gKnapsack__data* dptr = (struct knapsack_gKnapsack__data*)
                    malloc(sizeof(struct knapsack_gKnapsack__data));
    if (dptr) {
      // initialize elements of dptr here
      memset(dptr, 0, sizeof(struct knapsack_gKnapsack__data));
      knapsack_gKnapsack__set_data(self, dptr);
    } else {
      sidl_MemAllocException ex = 
           sidl_MemAllocException_getSingletonException(_ex); SIDL_CHECK(*_ex);
      sidl_MemAllocException_setNote(ex, "Out of memory.", 
           _ex); SIDL_CHECK(*_ex);
      sidl_MemAllocException_add(ex, __FILE__, __LINE__, 
           "knapsack.gKnapsack._ctor", _ex); SIDL_CHECK(*_ex);
      *_ex = (sidl_BaseInterface)ex;
    }

    EXIT:;
    return;
    /* DO-NOT-DELETE splicer.end(knapsack.gKnapsack._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_knapsack_gKnapsack__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_knapsack_gKnapsack__ctor2(
  /* in */ knapsack_gKnapsack self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(knapsack.gKnapsack._ctor2) */
     return;
    /* DO-NOT-DELETE splicer.end(knapsack.gKnapsack._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_knapsack_gKnapsack__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_knapsack_gKnapsack__dtor(
  /* in */ knapsack_gKnapsack self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(knapsack.gKnapsack._dtor) */
    struct knapsack_gKnapsack__data* dptr = knapsack_gKnapsack__get_data(self);
    if (dptr) {
      memset(dptr, 0, sizeof(struct knapsack_gKnapsack__data));
      free(dptr);
      knapsack_gKnapsack__set_data(self, NULL);
    } else {
      /* WARNING:  This should NEVER happen. */
      SIDL_THROW(*_ex, knapsack_kExcept, L_DPTR_MISSING);
    }

    EXIT:;
    return;
    /* DO-NOT-DELETE splicer.end(knapsack.gKnapsack._dtor) */
  }
}

/*
 * Initialize the knapsack with the specified weights, w.
 */

#undef __FUNC__
#define __FUNC__ "impl_knapsack_gKnapsack_initialize"

#ifdef __cplusplus
extern "C"
#endif
void
impl_knapsack_gKnapsack_initialize(
  /* in */ knapsack_gKnapsack self,
  /* in array<int> */ struct sidl_int__array* w,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(knapsack.gKnapsack.initialize) */
    int32_t   i, num;
    sidl_bool onlyPos;

    struct knapsack_gKnapsack__data* dptr = knapsack_gKnapsack__get_data(self);
    if (dptr) {
      if (w) {
        num = sidlLength(w, 0);
        if (num <= MAX_WEIGHTS) {
          M_CHECK_POS_SIDL(w, i, num, onlyPos);
          if (onlyPos) {
            memcpy(dptr->d_weights, sidl_int__array_first(w), 
                   (size_t)(num*sizeof(int32_t)));
            dptr->d_nextIndex = num;
          } else {
            SIDL_THROW(*_ex, knapsack_kBadWeightExcept, L_POS_WEIGHTS);
          }
        } else {
          SIDL_THROW(*_ex, knapsack_kSizeExcept, L_MAX_WEIGHTS);
        }
      } /* else no weights provided ==> no solution */
    } else {
      /* WARNING:  This should NEVER happen. */
      SIDL_THROW(*_ex, knapsack_kExcept, L_DPTR_MISSING);
    }

    EXIT:;
    return;
    /* DO-NOT-DELETE splicer.end(knapsack.gKnapsack.initialize) */
  }
}


/*
 * Return TRUE if all weights in the knapsack are positive;
 * otherwise, return FALSE.
 */

#undef __FUNC__
#define __FUNC__ "impl_knapsack_gKnapsack_onlyPosWeights"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_knapsack_gKnapsack_onlyPosWeights(
  /* in */ knapsack_gKnapsack self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(knapsack.gKnapsack.onlyPosWeights) */
    sidl_bool arePos = TRUE;

    struct knapsack_gKnapsack__data* dptr = knapsack_gKnapsack__get_data(self);
    if (dptr) {
      arePos = onlyPos(dptr->d_weights, dptr->d_nextIndex);
    } else {
      /* WARNING:  This should NEVER happen. */
      SIDL_THROW(*_ex, knapsack_kExcept, L_DPTR_MISSING);
    }

    EXIT:;
    return arePos;
    /* DO-NOT-DELETE splicer.end(knapsack.gKnapsack.onlyPosWeights) */
  }
}

/*
 * Return TRUE if all of the specified weights, w, are in the knapsack
 * or there are no specified weights; otherwise, return FALSE.
 */

#undef __FUNC__
#define __FUNC__ "impl_knapsack_gKnapsack_hasWeights"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_knapsack_gKnapsack_hasWeights(
  /* in */ knapsack_gKnapsack self,
  /* in array<int> */ struct sidl_int__array* w,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(knapsack.gKnapsack.hasWeights) */
    sidl_bool has = FALSE;

    struct knapsack_gKnapsack__data* dptr = knapsack_gKnapsack__get_data(self);
    if (dptr) {
      has = sameWeights(dptr->d_weights, w, dptr->d_nextIndex);
    } else {
      /* WARNING:  This should NEVER happen. */
      SIDL_THROW(*_ex, knapsack_kExcept, L_DPTR_MISSING);
    }

    EXIT:;
    return has;
    /* DO-NOT-DELETE splicer.end(knapsack.gKnapsack.hasWeights) */
  }
}


/*
 * Return TRUE if there is a solution for the specified target
 * weight; otherwise, return FALSE.  Recall a solution is a
 * subset of weights that total exactly to the specified target
 * weight.
 */

#undef __FUNC__
#define __FUNC__ "impl_knapsack_gKnapsack_hasSolution"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_knapsack_gKnapsack_hasSolution(
  /* in */ knapsack_gKnapsack self,
  /* in */ int32_t t,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(knapsack.gKnapsack.hasSolution) */
    sidl_bool has = FALSE;

    struct knapsack_gKnapsack__data* dptr = knapsack_gKnapsack__get_data(self);
    if (dptr) {
      has = solve(dptr->d_weights, t, 0, dptr->d_nextIndex);
    } else {
      /* WARNING:  This should NEVER happen. */
      SIDL_THROW(*_ex, knapsack_kExcept, L_DPTR_MISSING);
    }

    EXIT:;
    return has;
    /* DO-NOT-DELETE splicer.end(knapsack.gKnapsack.hasSolution) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */

/*
 * Determine if all entries in the list are positive, returning TRUE
 * if they are or FALSE if they are not.
 */
#undef __FUNC__
#define __FUNC__ "impl_knapsack_gKnapsack__onlyPos_"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
onlyPos(int32_t* w, int32_t len) {
  int32_t i;
  sidl_bool isPos = FALSE;

  if (len > 0) {
    isPos = TRUE;
    for (i=0; (i<len) && isPos; i++) {
      if (w[i] <= 0) {
        isPos = FALSE;
      }
    }
  }
  return isPos;
} /* onlyPos(int32_t*, int32_t) */

/*
 * Check to see if the two lists match where order does not matter.
 */
#undef __FUNC__
#define __FUNC__ "impl_knapsack_gKnapsack__sameWeights_"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
sameWeights(int32_t* nW, struct sidl_int__array* sW, int32_t len) {
  int32_t   i, j, num, w;
  int32_t*  p;
  sidl_bool same = FALSE;
  sidl_bool found = FALSE;

  if (nW && sW) {
    num = sidlLength(sW, 0);
    if (num == len) {
      p = (int32_t*)malloc(num*sizeof(int32_t));
      if (p) {
        memset(p, FALSE, (size_t)(num*sizeof(int32_t)));
        for (i=0; i<num; i++) {
          w = sidl_int__array_get1(sW, i);
          found = FALSE;
          for (j=0; (j<num) && !found; j++) {
            if ((w == nW[j]) && !p[j]) {
              p[j] = TRUE;
              found = TRUE;
            }
          }
        }
        same = onlyPos(p, num);
        free(p);
      }
    }  /* else weights list size mismatch so can't match */
  }
  return same;
} /* sameWeights(int32_t*, struct sidl_int__array*, len) */

/*
 * Recursive implementation of the simplified knapsack problem.
 *
 * Based on the algorithm defined in "Data Structures and
 * Algorithms" by Aho, Hopcroft, and Ullman (c) 1983.
 */
#undef __FUNC__
#define __FUNC__ "impl_knapsack_gKnapsack__knapsack_"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
solve(int32_t* weights, int32_t t, int32_t i, int32_t n) {
  sidl_bool has = FALSE;
  if (t==0) {
    has = TRUE;
  } else if ( (t < 0) || (i >= n) ) {
    has = FALSE;
  } else if (solve(weights, t-weights[i], i+1, n)) {
    has = TRUE;
  } else {
    has = solve(weights, t, i+1, n);
  }
  return has;
}
/* DO-NOT-DELETE splicer.end(_misc) */

