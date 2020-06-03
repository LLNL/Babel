/*
 * File:          sorting_SortingAlgorithm_Impl.h
 * Symbol:        sorting.SortingAlgorithm-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for sorting.SortingAlgorithm
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_sorting_SortingAlgorithm_Impl_h
#define included_sorting_SortingAlgorithm_Impl_h

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
#ifndef included_sorting_Comparator_h
#include "sorting_Comparator.h"
#endif
#ifndef included_sorting_Container_h
#include "sorting_Container.h"
#endif
#ifndef included_sorting_Counter_h
#include "sorting_Counter.h"
#endif
#ifndef included_sorting_SortingAlgorithm_h
#include "sorting_SortingAlgorithm.h"
#endif
/* DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm._hincludes) */
/* Put additional include files here... */
/* DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm._hincludes) */

/*
 * Private data for class sorting.SortingAlgorithm
 */

struct sorting_SortingAlgorithm__data {
  /* DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm._data) */
  sorting_Counter d_cmp;
  sorting_Counter d_swp;
  /* DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct sorting_SortingAlgorithm__data*
sorting_SortingAlgorithm__get_data(
  sorting_SortingAlgorithm);

extern void
sorting_SortingAlgorithm__set_data(
  sorting_SortingAlgorithm,
  struct sorting_SortingAlgorithm__data*);

extern
void
impl_sorting_SortingAlgorithm__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sorting_SortingAlgorithm__ctor(
  /* in */ sorting_SortingAlgorithm self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sorting_SortingAlgorithm__ctor2(
  /* in */ sorting_SortingAlgorithm self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sorting_SortingAlgorithm__dtor(
  /* in */ sorting_SortingAlgorithm self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_sorting_SortingAlgorithm_fconnect_sidl_BaseInterface(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
extern struct sorting_Comparator__object* 
  impl_sorting_SortingAlgorithm_fconnect_sorting_Comparator(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
extern struct sorting_Container__object* 
  impl_sorting_SortingAlgorithm_fconnect_sorting_Container(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern
sorting_Counter
impl_sorting_SortingAlgorithm_getCompareCounter(
  /* in */ sorting_SortingAlgorithm self,
  /* out */ sidl_BaseInterface *_ex);

extern
sorting_Counter
impl_sorting_SortingAlgorithm_getSwapCounter(
  /* in */ sorting_SortingAlgorithm self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sorting_SortingAlgorithm_reset(
  /* in */ sorting_SortingAlgorithm self,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_sorting_SortingAlgorithm_fconnect_sidl_BaseInterface(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
extern struct sorting_Comparator__object* 
  impl_sorting_SortingAlgorithm_fconnect_sorting_Comparator(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
extern struct sorting_Container__object* 
  impl_sorting_SortingAlgorithm_fconnect_sorting_Container(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
