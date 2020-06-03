/*
 * File:          Inherit_E_Impl.h
 * Symbol:        Inherit.E-v1.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Inherit.E
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_Inherit_E_Impl_h
#define included_Inherit_E_Impl_h

#ifndef included_sidl_header_h
#include "sidl_header.h"
#endif
#ifndef included_Inherit_C_h
#include "Inherit_C.h"
#endif
#ifndef included_Inherit_E_h
#include "Inherit_E.h"
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
/* DO-NOT-DELETE splicer.begin(Inherit.E._hincludes) */
/* Put additional include files here... */
/* DO-NOT-DELETE splicer.end(Inherit.E._hincludes) */

/*
 * Private data for class Inherit.E
 */

struct Inherit_E__data {
  /* DO-NOT-DELETE splicer.begin(Inherit.E._data) */
  /* Put private data members here... */
  int ignore; /* dummy to force non-empty struct; remove if you add data */
  /* DO-NOT-DELETE splicer.end(Inherit.E._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct Inherit_E__data*
Inherit_E__get_data(
  Inherit_E);

extern void
Inherit_E__set_data(
  Inherit_E,
  struct Inherit_E__data*);

extern
void
impl_Inherit_E__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Inherit_E__ctor(
  /* in */ Inherit_E self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Inherit_E__ctor2(
  /* in */ Inherit_E self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Inherit_E__dtor(
  /* in */ Inherit_E self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_Inherit_E_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern
char*
impl_Inherit_E_e(
  /* in */ Inherit_E self,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_Inherit_E_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
