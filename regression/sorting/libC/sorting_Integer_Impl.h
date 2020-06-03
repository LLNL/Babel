/*
 * File:          sorting_Integer_Impl.h
 * Symbol:        sorting.Integer-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for sorting.Integer
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_sorting_Integer_Impl_h
#define included_sorting_Integer_Impl_h

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
#ifndef included_sorting_Integer_h
#include "sorting_Integer.h"
#endif
/* DO-NOT-DELETE splicer.begin(sorting.Integer._hincludes) */

/* DO-NOT-DELETE splicer.end(sorting.Integer._hincludes) */

/*
 * Private data for class sorting.Integer
 */

struct sorting_Integer__data {
  /* DO-NOT-DELETE splicer.begin(sorting.Integer._data) */
  int32_t d_num;
  /* DO-NOT-DELETE splicer.end(sorting.Integer._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct sorting_Integer__data*
sorting_Integer__get_data(
  sorting_Integer);

extern void
sorting_Integer__set_data(
  sorting_Integer,
  struct sorting_Integer__data*);

extern
void
impl_sorting_Integer__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sorting_Integer__ctor(
  /* in */ sorting_Integer self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sorting_Integer__ctor2(
  /* in */ sorting_Integer self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sorting_Integer__dtor(
  /* in */ sorting_Integer self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_sorting_Integer_fconnect_sidl_BaseInterface(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern
int32_t
impl_sorting_Integer_getValue(
  /* in */ sorting_Integer self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sorting_Integer_setValue(
  /* in */ sorting_Integer self,
  /* in */ int32_t value,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_sorting_Integer_fconnect_sidl_BaseInterface(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
