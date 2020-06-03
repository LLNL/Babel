/*
 * File:          knapsack_npKnapsack_Impl.h
 * Symbol:        knapsack.npKnapsack-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7133M trunk)
 * Description:   Server-side implementation for knapsack.npKnapsack
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_knapsack_npKnapsack_Impl_h
#define included_knapsack_npKnapsack_Impl_h

#ifndef included_sidl_header_h
#include "sidl_header.h"
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
#ifndef included_knapsack_npKnapsack_h
#include "knapsack_npKnapsack.h"
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
/* DO-NOT-DELETE splicer.begin(knapsack.npKnapsack._hincludes) */
#ifndef included_knapsack_gKnapsack_Impl_h
#include "knapsack_gKnapsack_Impl.h"
#endif
/* DO-NOT-DELETE splicer.end(knapsack.npKnapsack._hincludes) */

/*
 * Private data for class knapsack.npKnapsack
 */

struct knapsack_npKnapsack__data {
  /* DO-NOT-DELETE splicer.begin(knapsack.npKnapsack._data) */
  int32_t d_nextIndex;
  int32_t d_weights[MAX_WEIGHTS];
  /* DO-NOT-DELETE splicer.end(knapsack.npKnapsack._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct knapsack_npKnapsack__data*
knapsack_npKnapsack__get_data(
  knapsack_npKnapsack);

extern void
knapsack_npKnapsack__set_data(
  knapsack_npKnapsack,
  struct knapsack_npKnapsack__data*);

extern
void
impl_knapsack_npKnapsack__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_knapsack_npKnapsack__ctor(
  /* in */ knapsack_npKnapsack self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_knapsack_npKnapsack__ctor2(
  /* in */ knapsack_npKnapsack self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_knapsack_npKnapsack__dtor(
  /* in */ knapsack_npKnapsack self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_knapsack_npKnapsack_fconnect_sidl_BaseInterface(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern
void
impl_knapsack_npKnapsack_initialize(
  /* in */ knapsack_npKnapsack self,
  /* in array<int> */ struct sidl_int__array* w,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_knapsack_npKnapsack_onlyPosWeights(
  /* in */ knapsack_npKnapsack self,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_knapsack_npKnapsack_hasWeights(
  /* in */ knapsack_npKnapsack self,
  /* in array<int> */ struct sidl_int__array* w,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_knapsack_npKnapsack_hasSolution(
  /* in */ knapsack_npKnapsack self,
  /* in */ int32_t t,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_knapsack_npKnapsack_fconnect_sidl_BaseInterface(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Nothing needed here */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
