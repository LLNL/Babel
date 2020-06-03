/*
 * File:          Inherit_F_Impl.h
 * Symbol:        Inherit.F-v1.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Inherit.F
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_Inherit_F_Impl_h
#define included_Inherit_F_Impl_h

#ifndef included_sidl_header_h
#include "sidl_header.h"
#endif
#ifndef included_Inherit_A_h
#include "Inherit_A.h"
#endif
#ifndef included_Inherit_B_h
#include "Inherit_B.h"
#endif
#ifndef included_Inherit_C_h
#include "Inherit_C.h"
#endif
#ifndef included_Inherit_F_h
#include "Inherit_F.h"
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
/* DO-NOT-DELETE splicer.begin(Inherit.F._hincludes) */
/* Put additional include files here... */
/* DO-NOT-DELETE splicer.end(Inherit.F._hincludes) */

/*
 * Private data for class Inherit.F
 */

struct Inherit_F__data {
  /* DO-NOT-DELETE splicer.begin(Inherit.F._data) */
  /* Put private data members here... */
  int ignore; /* dummy to force non-empty struct; remove if you add data */
  /* DO-NOT-DELETE splicer.end(Inherit.F._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct Inherit_F__data*
Inherit_F__get_data(
  Inherit_F);

extern void
Inherit_F__set_data(
  Inherit_F,
  struct Inherit_F__data*);

extern
void
impl_Inherit_F__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Inherit_F__ctor(
  /* in */ Inherit_F self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Inherit_F__ctor2(
  /* in */ Inherit_F self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Inherit_F__dtor(
  /* in */ Inherit_F self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_Inherit_F_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern
char*
impl_Inherit_F_f(
  /* in */ Inherit_F self,
  /* out */ sidl_BaseInterface *_ex);

extern
char*
impl_Inherit_F_a(
  /* in */ Inherit_F self,
  /* out */ sidl_BaseInterface *_ex);

extern
char*
impl_Inherit_F_b(
  /* in */ Inherit_F self,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_Inherit_F_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
