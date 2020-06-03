/*
 * File:          sorting_SimpleCounter_Impl.h
 * Symbol:        sorting.SimpleCounter-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for sorting.SimpleCounter
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_sorting_SimpleCounter_Impl_h
#define included_sorting_SimpleCounter_Impl_h

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
#ifndef included_sorting_Counter_h
#include "sorting_Counter.h"
#endif
#ifndef included_sorting_SimpleCounter_h
#include "sorting_SimpleCounter.h"
#endif
/* DO-NOT-DELETE splicer.begin(sorting.SimpleCounter._hincludes) */
/* Put additional include files here... */
/* DO-NOT-DELETE splicer.end(sorting.SimpleCounter._hincludes) */

/*
 * Private data for class sorting.SimpleCounter
 */

struct sorting_SimpleCounter__data {
  /* DO-NOT-DELETE splicer.begin(sorting.SimpleCounter._data) */
  int32_t d_count;
  /* DO-NOT-DELETE splicer.end(sorting.SimpleCounter._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct sorting_SimpleCounter__data*
sorting_SimpleCounter__get_data(
  sorting_SimpleCounter);

extern void
sorting_SimpleCounter__set_data(
  sorting_SimpleCounter,
  struct sorting_SimpleCounter__data*);

extern
void
impl_sorting_SimpleCounter__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sorting_SimpleCounter__ctor(
  /* in */ sorting_SimpleCounter self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sorting_SimpleCounter__ctor2(
  /* in */ sorting_SimpleCounter self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sorting_SimpleCounter__dtor(
  /* in */ sorting_SimpleCounter self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_sorting_SimpleCounter_fconnect_sidl_BaseInterface(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern
void
impl_sorting_SimpleCounter_reset(
  /* in */ sorting_SimpleCounter self,
  /* out */ sidl_BaseInterface *_ex);

extern
int32_t
impl_sorting_SimpleCounter_getCount(
  /* in */ sorting_SimpleCounter self,
  /* out */ sidl_BaseInterface *_ex);

extern
int32_t
impl_sorting_SimpleCounter_inc(
  /* in */ sorting_SimpleCounter self,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_sorting_SimpleCounter_fconnect_sidl_BaseInterface(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
