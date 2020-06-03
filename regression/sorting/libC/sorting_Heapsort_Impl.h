/*
 * File:          sorting_Heapsort_Impl.h
 * Symbol:        sorting.Heapsort-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for sorting.Heapsort
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_sorting_Heapsort_Impl_h
#define included_sorting_Heapsort_Impl_h

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
#ifndef included_sorting_Heapsort_h
#include "sorting_Heapsort.h"
#endif
#ifndef included_sorting_SortingAlgorithm_h
#include "sorting_SortingAlgorithm.h"
#endif
/* DO-NOT-DELETE splicer.begin(sorting.Heapsort._hincludes) */
/* Put additional include files here... */
/* DO-NOT-DELETE splicer.end(sorting.Heapsort._hincludes) */

/*
 * Private data for class sorting.Heapsort
 */

struct sorting_Heapsort__data {
  /* DO-NOT-DELETE splicer.begin(sorting.Heapsort._data) */
  /* Put private data members here... */
  int ignore; /* dummy to force non-empty struct; remove if you add data */
  /* DO-NOT-DELETE splicer.end(sorting.Heapsort._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct sorting_Heapsort__data*
sorting_Heapsort__get_data(
  sorting_Heapsort);

extern void
sorting_Heapsort__set_data(
  sorting_Heapsort,
  struct sorting_Heapsort__data*);

extern
void
impl_sorting_Heapsort__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sorting_Heapsort__ctor(
  /* in */ sorting_Heapsort self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sorting_Heapsort__ctor2(
  /* in */ sorting_Heapsort self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sorting_Heapsort__dtor(
  /* in */ sorting_Heapsort self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_sorting_Heapsort_fconnect_sidl_BaseInterface(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
extern struct sorting_Comparator__object* 
  impl_sorting_Heapsort_fconnect_sorting_Comparator(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
extern struct sorting_Container__object* 
  impl_sorting_Heapsort_fconnect_sorting_Container(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern
void
impl_sorting_Heapsort_sort(
  /* in */ sorting_Heapsort self,
  /* in */ sorting_Container elems,
  /* in */ sorting_Comparator comp,
  /* out */ sidl_BaseInterface *_ex);

extern
char*
impl_sorting_Heapsort_getName(
  /* in */ sorting_Heapsort self,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_sorting_Heapsort_fconnect_sidl_BaseInterface(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
extern struct sorting_Comparator__object* 
  impl_sorting_Heapsort_fconnect_sorting_Comparator(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
extern struct sorting_Container__object* 
  impl_sorting_Heapsort_fconnect_sorting_Container(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
