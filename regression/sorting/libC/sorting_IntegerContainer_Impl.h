/*
 * File:          sorting_IntegerContainer_Impl.h
 * Symbol:        sorting.IntegerContainer-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for sorting.IntegerContainer
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_sorting_IntegerContainer_Impl_h
#define included_sorting_IntegerContainer_Impl_h

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
#ifndef included_sorting_IntegerContainer_h
#include "sorting_IntegerContainer.h"
#endif
/* DO-NOT-DELETE splicer.begin(sorting.IntegerContainer._hincludes) */
#include "sorting_Integer.h"
/* DO-NOT-DELETE splicer.end(sorting.IntegerContainer._hincludes) */

/*
 * Private data for class sorting.IntegerContainer
 */

struct sorting_IntegerContainer__data {
  /* DO-NOT-DELETE splicer.begin(sorting.IntegerContainer._data) */
  struct sorting_Integer__array *d_elements;
  /* DO-NOT-DELETE splicer.end(sorting.IntegerContainer._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct sorting_IntegerContainer__data*
sorting_IntegerContainer__get_data(
  sorting_IntegerContainer);

extern void
sorting_IntegerContainer__set_data(
  sorting_IntegerContainer,
  struct sorting_IntegerContainer__data*);

extern
void
impl_sorting_IntegerContainer__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sorting_IntegerContainer__ctor(
  /* in */ sorting_IntegerContainer self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sorting_IntegerContainer__ctor2(
  /* in */ sorting_IntegerContainer self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sorting_IntegerContainer__dtor(
  /* in */ sorting_IntegerContainer self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_sorting_IntegerContainer_fconnect_sidl_BaseInterface(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
extern struct sorting_Comparator__object* 
  impl_sorting_IntegerContainer_fconnect_sorting_Comparator(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern
void
impl_sorting_IntegerContainer_setLength(
  /* in */ sorting_IntegerContainer self,
  /* in */ int32_t len,
  /* out */ sidl_BaseInterface *_ex);

extern
int32_t
impl_sorting_IntegerContainer_getLength(
  /* in */ sorting_IntegerContainer self,
  /* out */ sidl_BaseInterface *_ex);

extern
int32_t
impl_sorting_IntegerContainer_compare(
  /* in */ sorting_IntegerContainer self,
  /* in */ int32_t i,
  /* in */ int32_t j,
  /* in */ sorting_Comparator comp,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sorting_IntegerContainer_swap(
  /* in */ sorting_IntegerContainer self,
  /* in */ int32_t i,
  /* in */ int32_t j,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sorting_IntegerContainer_output(
  /* in */ sorting_IntegerContainer self,
  /* in */ int32_t s,
  /* in */ int32_t e,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_sorting_IntegerContainer_fconnect_sidl_BaseInterface(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
extern struct sorting_Comparator__object* 
  impl_sorting_IntegerContainer_fconnect_sorting_Comparator(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
