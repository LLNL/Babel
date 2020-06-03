/*
 * File:          Inherit_C_Impl.h
 * Symbol:        Inherit.C-v1.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Inherit.C
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_Inherit_C_Impl_h
#define included_Inherit_C_Impl_h

#ifndef included_sidl_header_h
#include "sidl_header.h"
#endif
#ifndef included_Inherit_C_h
#include "Inherit_C.h"
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
/* DO-NOT-DELETE splicer.begin(Inherit.C._hincludes) */
/* Put additional include files here... */
/* DO-NOT-DELETE splicer.end(Inherit.C._hincludes) */

/*
 * Private data for class Inherit.C
 */

struct Inherit_C__data {
  /* DO-NOT-DELETE splicer.begin(Inherit.C._data) */
  /* Put private data members here... */
  int ignore; /* dummy to force non-empty struct; remove if you add data */
  /* DO-NOT-DELETE splicer.end(Inherit.C._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct Inherit_C__data*
Inherit_C__get_data(
  Inherit_C);

extern void
Inherit_C__set_data(
  Inherit_C,
  struct Inherit_C__data*);

extern
void
impl_Inherit_C__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Inherit_C__ctor(
  /* in */ Inherit_C self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Inherit_C__ctor2(
  /* in */ Inherit_C self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Inherit_C__dtor(
  /* in */ Inherit_C self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_Inherit_C_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern
char*
impl_Inherit_C_c(
  /* in */ Inherit_C self,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_Inherit_C_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
