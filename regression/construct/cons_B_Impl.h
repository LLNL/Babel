/*
 * File:          cons_B_Impl.h
 * Symbol:        cons.B-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for cons.B
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_cons_B_Impl_h
#define included_cons_B_Impl_h

#ifndef included_sidl_header_h
#include "sidl_header.h"
#endif
#ifndef included_cons_B_IOR_h
#include "cons_B_IOR.h"
#endif
#ifndef included_cons_A_h
#include "cons_A.h"
#endif
#ifndef included_cons_B_h
#include "cons_B.h"
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
/* DO-NOT-DELETE splicer.begin(cons.B._hincludes) */
/* Insert-Code-Here {cons.B._includes} (include files) */
/* DO-NOT-DELETE splicer.end(cons.B._hincludes) */

/*
 * Private data for class cons.B
 */

struct cons_B__data {
  /* DO-NOT-DELETE splicer.begin(cons.B._data) */
  /* Insert-Code-Here {cons.B._data} (private data members) */
  int ignore; /* dummy to force non-empty struct; remove if you add data */
  /* DO-NOT-DELETE splicer.end(cons.B._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct cons_B__data*
cons_B__get_data(
  cons_B);

extern void
cons_B__set_data(
  cons_B,
  struct cons_B__data*);

extern void cons_B__superEPV(
struct cons_A__epv*);

extern
void
impl_cons_B__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_cons_B__ctor(
  /* in */ cons_B self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_cons_B__ctor2(
  /* in */ cons_B self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_cons_B__dtor(
  /* in */ cons_B self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_cons_B_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern
void
impl_cons_B_init(
  /* in */ cons_B self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_cons_B_destruct(
  /* in */ cons_B self,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_cons_B_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* insert code here (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
