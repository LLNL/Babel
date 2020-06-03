/*
 * File:          cons_C_Impl.h
 * Symbol:        cons.C-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for cons.C
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_cons_C_Impl_h
#define included_cons_C_Impl_h

#ifndef included_sidl_header_h
#include "sidl_header.h"
#endif
#ifndef included_cons_C_IOR_h
#include "cons_C_IOR.h"
#endif
#ifndef included_cons_A_h
#include "cons_A.h"
#endif
#ifndef included_cons_B_h
#include "cons_B.h"
#endif
#ifndef included_cons_C_h
#include "cons_C.h"
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
/* DO-NOT-DELETE splicer.begin(cons.C._hincludes) */
/* Insert-Code-Here {cons.C._includes} (include files) */
/* DO-NOT-DELETE splicer.end(cons.C._hincludes) */

/*
 * Private data for class cons.C
 */

struct cons_C__data {
  /* DO-NOT-DELETE splicer.begin(cons.C._data) */
  /* Insert-Code-Here {cons.C._data} (private data members) */
  int ignore; /* dummy to force non-empty struct; remove if you add data */
  /* DO-NOT-DELETE splicer.end(cons.C._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct cons_C__data*
cons_C__get_data(
  cons_C);

extern void
cons_C__set_data(
  cons_C,
  struct cons_C__data*);

extern void cons_C__superEPV(
struct cons_B__epv*);

extern
void
impl_cons_C__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_cons_C__ctor(
  /* in */ cons_C self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_cons_C__ctor2(
  /* in */ cons_C self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_cons_C__dtor(
  /* in */ cons_C self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_cons_C_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern
void
impl_cons_C_init(
  /* in */ cons_C self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_cons_C_destruct(
  /* in */ cons_C self,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_cons_C_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* insert code here (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
