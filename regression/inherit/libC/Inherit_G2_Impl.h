/*
 * File:          Inherit_G2_Impl.h
 * Symbol:        Inherit.G2-v1.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Inherit.G2
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_Inherit_G2_Impl_h
#define included_Inherit_G2_Impl_h

#ifndef included_sidl_header_h
#include "sidl_header.h"
#endif
#ifndef included_Inherit_G2_IOR_h
#include "Inherit_G2_IOR.h"
#endif
#ifndef included_Inherit_A_h
#include "Inherit_A.h"
#endif
#ifndef included_Inherit_D_h
#include "Inherit_D.h"
#endif
#ifndef included_Inherit_G2_h
#include "Inherit_G2.h"
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
/* DO-NOT-DELETE splicer.begin(Inherit.G2._hincludes) */
/* Put additional include files here... */
/* DO-NOT-DELETE splicer.end(Inherit.G2._hincludes) */

/*
 * Private data for class Inherit.G2
 */

struct Inherit_G2__data {
  /* DO-NOT-DELETE splicer.begin(Inherit.G2._data) */
  /* Put private data members here... */
  int ignore; /* dummy to force non-empty struct; remove if you add data */
  /* DO-NOT-DELETE splicer.end(Inherit.G2._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct Inherit_G2__data*
Inherit_G2__get_data(
  Inherit_G2);

extern void
Inherit_G2__set_data(
  Inherit_G2,
  struct Inherit_G2__data*);

extern void Inherit_G2__superEPV(
struct Inherit_D__epv*);

extern
void
impl_Inherit_G2__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Inherit_G2__ctor(
  /* in */ Inherit_G2 self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Inherit_G2__ctor2(
  /* in */ Inherit_G2 self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Inherit_G2__dtor(
  /* in */ Inherit_G2 self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_Inherit_G2_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern
char*
impl_Inherit_G2_a(
  /* in */ Inherit_G2 self,
  /* out */ sidl_BaseInterface *_ex);

extern
char*
impl_Inherit_G2_d(
  /* in */ Inherit_G2 self,
  /* out */ sidl_BaseInterface *_ex);

extern
char*
impl_Inherit_G2_g(
  /* in */ Inherit_G2 self,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_Inherit_G2_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
