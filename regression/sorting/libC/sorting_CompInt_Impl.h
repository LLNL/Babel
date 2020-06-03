/*
 * File:          sorting_CompInt_Impl.h
 * Symbol:        sorting.CompInt-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for sorting.CompInt
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_sorting_CompInt_Impl_h
#define included_sorting_CompInt_Impl_h

#ifndef included_sidl_header_h
#include "sidl_header.h"
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
#ifndef included_sidl_RuntimeException_h
#include "sidl_RuntimeException.h"
#endif
#ifndef included_sorting_CompInt_h
#include "sorting_CompInt.h"
#endif
#ifndef included_sorting_Comparator_h
#include "sorting_Comparator.h"
#endif
/* DO-NOT-DELETE splicer.begin(sorting.CompInt._hincludes) */
/* Put additional include files here... */
/* DO-NOT-DELETE splicer.end(sorting.CompInt._hincludes) */

/*
 * Private data for class sorting.CompInt
 */

struct sorting_CompInt__data {
  /* DO-NOT-DELETE splicer.begin(sorting.CompInt._data) */
  sidl_bool d_increasing;
  /* DO-NOT-DELETE splicer.end(sorting.CompInt._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct sorting_CompInt__data*
sorting_CompInt__get_data(
  sorting_CompInt);

extern void
sorting_CompInt__set_data(
  sorting_CompInt,
  struct sorting_CompInt__data*);

extern
void
impl_sorting_CompInt__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sorting_CompInt__ctor(
  /* in */ sorting_CompInt self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sorting_CompInt__ctor2(
  /* in */ sorting_CompInt self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sorting_CompInt__dtor(
  /* in */ sorting_CompInt self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_sorting_CompInt_fconnect_sidl_BaseInterface(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern
void
impl_sorting_CompInt_setSortIncreasing(
  /* in */ sorting_CompInt self,
  /* in */ sidl_bool increasing,
  /* out */ sidl_BaseInterface *_ex);

extern
int32_t
impl_sorting_CompInt_compare(
  /* in */ sorting_CompInt self,
  /* in */ sidl_BaseInterface i1,
  /* in */ sidl_BaseInterface i2,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_sorting_CompInt_fconnect_sidl_BaseInterface(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
