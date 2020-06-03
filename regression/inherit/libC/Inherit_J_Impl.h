/*
 * File:          Inherit_J_Impl.h
 * Symbol:        Inherit.J-v1.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Inherit.J
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_Inherit_J_Impl_h
#define included_Inherit_J_Impl_h

#ifndef included_sidl_header_h
#include "sidl_header.h"
#endif
#ifndef included_Inherit_J_IOR_h
#include "Inherit_J_IOR.h"
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
#ifndef included_Inherit_E2_h
#include "Inherit_E2.h"
#endif
#ifndef included_Inherit_J_h
#include "Inherit_J.h"
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
/* DO-NOT-DELETE splicer.begin(Inherit.J._hincludes) */
/* Put additional include files here... */
/* DO-NOT-DELETE splicer.end(Inherit.J._hincludes) */

/*
 * Private data for class Inherit.J
 */

struct Inherit_J__data {
  /* DO-NOT-DELETE splicer.begin(Inherit.J._data) */
  /* Put private data members here... */
  int ignore; /* dummy to force non-empty struct; remove if you add data */
  /* DO-NOT-DELETE splicer.end(Inherit.J._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct Inherit_J__data*
Inherit_J__get_data(
  Inherit_J);

extern void
Inherit_J__set_data(
  Inherit_J,
  struct Inherit_J__data*);

extern void Inherit_J__superEPV(
struct Inherit_E2__epv*);

extern
void
impl_Inherit_J__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Inherit_J__ctor(
  /* in */ Inherit_J self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Inherit_J__ctor2(
  /* in */ Inherit_J self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Inherit_J__dtor(
  /* in */ Inherit_J self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_Inherit_J_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern
char*
impl_Inherit_J_j(
  /* in */ Inherit_J self,
  /* out */ sidl_BaseInterface *_ex);

extern
char*
impl_Inherit_J_e(
  /* in */ Inherit_J self,
  /* out */ sidl_BaseInterface *_ex);

extern
char*
impl_Inherit_J_c(
  /* in */ Inherit_J self,
  /* out */ sidl_BaseInterface *_ex);

extern
char*
impl_Inherit_J_a(
  /* in */ Inherit_J self,
  /* out */ sidl_BaseInterface *_ex);

extern
char*
impl_Inherit_J_b(
  /* in */ Inherit_J self,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_Inherit_J_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
