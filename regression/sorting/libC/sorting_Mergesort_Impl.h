/*
 * File:          sorting_Mergesort_Impl.h
 * Symbol:        sorting.Mergesort-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for sorting.Mergesort
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_sorting_Mergesort_Impl_h
#define included_sorting_Mergesort_Impl_h

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
#ifndef included_sorting_Mergesort_h
#include "sorting_Mergesort.h"
#endif
#ifndef included_sorting_SortingAlgorithm_h
#include "sorting_SortingAlgorithm.h"
#endif
/* DO-NOT-DELETE splicer.begin(sorting.Mergesort._hincludes) */
/* Put additional include files here... */
/* DO-NOT-DELETE splicer.end(sorting.Mergesort._hincludes) */

/*
 * Private data for class sorting.Mergesort
 */

struct sorting_Mergesort__data {
  /* DO-NOT-DELETE splicer.begin(sorting.Mergesort._data) */
  /* Put private data members here... */
  int ignore; /* dummy to force non-empty struct; remove if you add data */
  /* DO-NOT-DELETE splicer.end(sorting.Mergesort._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct sorting_Mergesort__data*
sorting_Mergesort__get_data(
  sorting_Mergesort);

extern void
sorting_Mergesort__set_data(
  sorting_Mergesort,
  struct sorting_Mergesort__data*);

extern
void
impl_sorting_Mergesort__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sorting_Mergesort__ctor(
  /* in */ sorting_Mergesort self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sorting_Mergesort__ctor2(
  /* in */ sorting_Mergesort self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sorting_Mergesort__dtor(
  /* in */ sorting_Mergesort self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_sorting_Mergesort_fconnect_sidl_BaseInterface(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
extern struct sorting_Comparator__object* 
  impl_sorting_Mergesort_fconnect_sorting_Comparator(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
extern struct sorting_Container__object* 
  impl_sorting_Mergesort_fconnect_sorting_Container(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern
void
impl_sorting_Mergesort_sort(
  /* in */ sorting_Mergesort self,
  /* in */ sorting_Container elems,
  /* in */ sorting_Comparator comp,
  /* out */ sidl_BaseInterface *_ex);

extern
char*
impl_sorting_Mergesort_getName(
  /* in */ sorting_Mergesort self,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_sorting_Mergesort_fconnect_sidl_BaseInterface(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
extern struct sorting_Comparator__object* 
  impl_sorting_Mergesort_fconnect_sorting_Comparator(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
extern struct sorting_Container__object* 
  impl_sorting_Mergesort_fconnect_sorting_Container(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
