/*
 * File:          knapsack_gKnapsack_Impl.h
 * Symbol:        knapsack.gKnapsack-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7133M trunk)
 * Description:   Server-side implementation for knapsack.gKnapsack
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_knapsack_gKnapsack_Impl_h
#define included_knapsack_gKnapsack_Impl_h

#ifndef included_sidl_header_h
#include "sidl_header.h"
#endif
#ifndef included_knapsack_gKnapsack_h
#include "knapsack_gKnapsack.h"
#endif
#ifndef included_knapsack_iKnapsack_h
#include "knapsack_iKnapsack.h"
#endif
#ifndef included_knapsack_kBadWeightExcept_h
#include "knapsack_kBadWeightExcept.h"
#endif
#ifndef included_knapsack_kExcept_h
#include "knapsack_kExcept.h"
#endif
#ifndef included_knapsack_kSizeExcept_h
#include "knapsack_kSizeExcept.h"
#endif
#ifndef included_sidl_BaseClass_h
#include "sidl_BaseClass.h"
#endif
#ifndef included_sidl_BaseInterface_h
#include "sidl_BaseInterface.h"
#endif
#ifndef included_sidl_ClassInfo_h
#include "sidl_ClassInfo.h"
#endif
#ifndef included_sidl_InvViolation_h
#include "sidl_InvViolation.h"
#endif
#ifndef included_sidl_PostViolation_h
#include "sidl_PostViolation.h"
#endif
#ifndef included_sidl_PreViolation_h
#include "sidl_PreViolation.h"
#endif
#ifndef included_sidl_RuntimeException_h
#include "sidl_RuntimeException.h"
#endif
/* DO-NOT-DELETE splicer.begin(knapsack.gKnapsack._hincludes) */
/*
 * Maximum number of weights.
 */
#define MAX_WEIGHTS 10

#ifndef FALSE
#define FALSE 0
#endif
#ifndef TRUE
#define TRUE 1
#endif

#define M_CHECK_POS_SIDL(ARR, I, MAX, ONLYPOS) {\
  ONLYPOS = FALSE; \
  if (MAX > 0) { \
    ONLYPOS = TRUE; \
    for (I=0; (I<MAX) && ONLYPOS; I++) { \
      if (sidl_int__array_get1(ARR, I) <= 0) {\
        ONLYPOS = FALSE;\
      }\
    }\
  }\
}

#ifndef VAR_UNUSED
#ifdef __GNUC__
#define VAR_UNUSED __attribute__ ((__unused__))
#else
#define VAR_UNUSED 
#endif /* __GNUC__ */
#endif /* VAR_UNUSED */

static const VAR_UNUSED char* L_DPTR_MISSING 
       = "Private data is erroneously missing.";
static const VAR_UNUSED char* L_MAX_WEIGHTS 
       = "Cannot exceed maximum number of weights.";
static const VAR_UNUSED char* L_POS_WEIGHTS 
       = "Non-positive weights are NOT supported.";

/* DO-NOT-DELETE splicer.end(knapsack.gKnapsack._hincludes) */

/*
 * Private data for class knapsack.gKnapsack
 */

struct knapsack_gKnapsack__data {
  /* DO-NOT-DELETE splicer.begin(knapsack.gKnapsack._data) */
  int32_t d_nextIndex;
  int32_t d_weights[MAX_WEIGHTS];
  /* DO-NOT-DELETE splicer.end(knapsack.gKnapsack._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct knapsack_gKnapsack__data*
knapsack_gKnapsack__get_data(
  knapsack_gKnapsack);

extern void
knapsack_gKnapsack__set_data(
  knapsack_gKnapsack,
  struct knapsack_gKnapsack__data*);

extern
void
impl_knapsack_gKnapsack__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_knapsack_gKnapsack__ctor(
  /* in */ knapsack_gKnapsack self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_knapsack_gKnapsack__ctor2(
  /* in */ knapsack_gKnapsack self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_knapsack_gKnapsack__dtor(
  /* in */ knapsack_gKnapsack self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_knapsack_gKnapsack_fconnect_sidl_BaseInterface(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern
void
impl_knapsack_gKnapsack_initialize(
  /* in */ knapsack_gKnapsack self,
  /* in array<int> */ struct sidl_int__array* w,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_knapsack_gKnapsack_onlyPosWeights(
  /* in */ knapsack_gKnapsack self,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_knapsack_gKnapsack_hasWeights(
  /* in */ knapsack_gKnapsack self,
  /* in array<int> */ struct sidl_int__array* w,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_knapsack_gKnapsack_hasSolution(
  /* in */ knapsack_gKnapsack self,
  /* in */ int32_t t,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_knapsack_gKnapsack_fconnect_sidl_BaseInterface(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */

/*
 * Determine if all entries in the list are positive, returning TRUE
 * if they are or FALSE if they are not.
 */
extern
sidl_bool
onlyPos(int32_t* w, int32_t len);

/*
 * Check to see if the two lists match where order does not matter.
 */
extern
sidl_bool
sameWeights(int32_t* nW, struct sidl_int__array* sW, int32_t len);

/*
 * Recursive implementation of the simplified knapsack problem.
 *
 * Based on the algorithm defined in "Data Structures and
 * Algorithms" by Aho, Hopcroft, and Ullman (c) 1983.
 */
extern
sidl_bool
solve(int32_t* weights, int32_t t, int32_t i, int32_t n);
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
