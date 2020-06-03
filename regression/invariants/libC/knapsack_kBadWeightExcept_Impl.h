/*
 * File:          knapsack_kBadWeightExcept_Impl.h
 * Symbol:        knapsack.kBadWeightExcept-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7133M trunk)
 * Description:   Server-side implementation for knapsack.kBadWeightExcept
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_knapsack_kBadWeightExcept_Impl_h
#define included_knapsack_kBadWeightExcept_Impl_h

#ifndef included_sidl_header_h
#include "sidl_header.h"
#endif
#ifndef included_knapsack_kBadWeightExcept_h
#include "knapsack_kBadWeightExcept.h"
#endif
#ifndef included_knapsack_kExcept_h
#include "knapsack_kExcept.h"
#endif
#ifndef included_sidl_BaseClass_h
#include "sidl_BaseClass.h"
#endif
#ifndef included_sidl_BaseException_h
#include "sidl_BaseException.h"
#endif
#ifndef included_sidl_BaseInterface_h
#include "sidl_BaseInterface.h"
#endif
#ifndef included_sidl_ClassInfo_h
#include "sidl_ClassInfo.h"
#endif
#ifndef included_sidl_RuntimeException_h
#include "sidl_RuntimeException.h"
#endif
#ifndef included_sidl_SIDLException_h
#include "sidl_SIDLException.h"
#endif
#ifndef included_sidl_io_Deserializer_h
#include "sidl_io_Deserializer.h"
#endif
#ifndef included_sidl_io_Serializable_h
#include "sidl_io_Serializable.h"
#endif
#ifndef included_sidl_io_Serializer_h
#include "sidl_io_Serializer.h"
#endif
/* DO-NOT-DELETE splicer.begin(knapsack.kBadWeightExcept._hincludes) */
  // Nothing needed here
/* DO-NOT-DELETE splicer.end(knapsack.kBadWeightExcept._hincludes) */

/*
 * Private data for class knapsack.kBadWeightExcept
 */

struct knapsack_kBadWeightExcept__data {
  /* DO-NOT-DELETE splicer.begin(knapsack.kBadWeightExcept._data) */
  /* insert code here (private data members) */
  int ignore; /* dummy to force non-empty struct; remove if you add data */
  /* DO-NOT-DELETE splicer.end(knapsack.kBadWeightExcept._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct knapsack_kBadWeightExcept__data*
knapsack_kBadWeightExcept__get_data(
  knapsack_kBadWeightExcept);

extern void
knapsack_kBadWeightExcept__set_data(
  knapsack_kBadWeightExcept,
  struct knapsack_kBadWeightExcept__data*);

extern
void
impl_knapsack_kBadWeightExcept__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_knapsack_kBadWeightExcept__ctor(
  /* in */ knapsack_kBadWeightExcept self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_knapsack_kBadWeightExcept__ctor2(
  /* in */ knapsack_kBadWeightExcept self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_knapsack_kBadWeightExcept__dtor(
  /* in */ knapsack_kBadWeightExcept self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_io_Deserializer__object* 
  impl_knapsack_kBadWeightExcept_fconnect_sidl_io_Deserializer(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
extern struct sidl_io_Serializer__object* 
  impl_knapsack_kBadWeightExcept_fconnect_sidl_io_Serializer(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
extern struct sidl_BaseInterface__object* 
  impl_knapsack_kBadWeightExcept_fconnect_sidl_BaseInterface(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
#ifdef WITH_RMI
extern struct sidl_io_Deserializer__object* 
  impl_knapsack_kBadWeightExcept_fconnect_sidl_io_Deserializer(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
extern struct sidl_io_Serializer__object* 
  impl_knapsack_kBadWeightExcept_fconnect_sidl_io_Serializer(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
extern struct sidl_BaseInterface__object* 
  impl_knapsack_kBadWeightExcept_fconnect_sidl_BaseInterface(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
  // Nothing needed here
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
