/*
 * File:          sorting_SortTest_Impl.h
 * Symbol:        sorting.SortTest-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for sorting.SortTest
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_sorting_SortTest_Impl_h
#define included_sorting_SortTest_Impl_h

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
#ifndef included_sorting_SortTest_h
#include "sorting_SortTest.h"
#endif
#ifndef included_sorting_SortingAlgorithm_h
#include "sorting_SortingAlgorithm.h"
#endif
/* DO-NOT-DELETE splicer.begin(sorting.SortTest._hincludes) */
/* Put additional include files here... */
/* DO-NOT-DELETE splicer.end(sorting.SortTest._hincludes) */

/*
 * Private data for class sorting.SortTest
 */

struct sorting_SortTest__data {
  /* DO-NOT-DELETE splicer.begin(sorting.SortTest._data) */
  /* Put private data members here... */
  int ignore; /* dummy to force non-empty struct; remove if you add data */
  /* DO-NOT-DELETE splicer.end(sorting.SortTest._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct sorting_SortTest__data*
sorting_SortTest__get_data(
  sorting_SortTest);

extern void
sorting_SortTest__set_data(
  sorting_SortTest,
  struct sorting_SortTest__data*);

extern
void
impl_sorting_SortTest__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sorting_SortTest__ctor(
  /* in */ sorting_SortTest self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sorting_SortTest__ctor2(
  /* in */ sorting_SortTest self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sorting_SortTest__dtor(
  /* in */ sorting_SortTest self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

extern
sidl_bool
impl_sorting_SortTest_stressTest(
  /* in array<sorting.SortingAlgorithm> */ struct 
    sorting_SortingAlgorithm__array* algs,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_sorting_SortTest_fconnect_sidl_BaseInterface(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_sorting_SortTest_fconnect_sidl_BaseInterface(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
