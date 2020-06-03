/*
 * File:          cons_A_Impl.h
 * Symbol:        cons.A-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for cons.A
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_cons_A_Impl_h
#define included_cons_A_Impl_h

#ifndef included_sidl_header_h
#include "sidl_header.h"
#endif
#ifndef included_cons_A_h
#include "cons_A.h"
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
/* DO-NOT-DELETE splicer.begin(cons.A._hincludes) */
/* Insert-Code-Here {cons.A._includes} (include files) */
/* DO-NOT-DELETE splicer.end(cons.A._hincludes) */

/*
 * Private data for class cons.A
 */

struct cons_A__data {
  /* DO-NOT-DELETE splicer.begin(cons.A._data) */
  /* Insert-Code-Here {cons.A._data} (private data members) */
  int ignore; /* dummy to force non-empty struct; remove if you add data */
  /* DO-NOT-DELETE splicer.end(cons.A._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct cons_A__data*
cons_A__get_data(
  cons_A);

extern void
cons_A__set_data(
  cons_A,
  struct cons_A__data*);

extern
void
impl_cons_A__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_cons_A__ctor(
  /* in */ cons_A self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_cons_A__ctor2(
  /* in */ cons_A self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_cons_A__dtor(
  /* in */ cons_A self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_cons_A_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern
void
impl_cons_A_init(
  /* in */ cons_A self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_cons_A_destruct(
  /* in */ cons_A self,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_cons_A_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* insert code here (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
