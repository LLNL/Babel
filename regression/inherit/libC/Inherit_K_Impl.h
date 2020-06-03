/*
 * File:          Inherit_K_Impl.h
 * Symbol:        Inherit.K-v1.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Inherit.K
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_Inherit_K_Impl_h
#define included_Inherit_K_Impl_h

#ifndef included_sidl_header_h
#include "sidl_header.h"
#endif
#ifndef included_Inherit_A_h
#include "Inherit_A.h"
#endif
#ifndef included_Inherit_A2_h
#include "Inherit_A2.h"
#endif
#ifndef included_Inherit_H_h
#include "Inherit_H.h"
#endif
#ifndef included_Inherit_K_h
#include "Inherit_K.h"
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
/* DO-NOT-DELETE splicer.begin(Inherit.K._hincludes) */
/* Insert-Code-Here {Inherit.K._includes} (include files) */
/* DO-NOT-DELETE splicer.end(Inherit.K._hincludes) */

/*
 * Private data for class Inherit.K
 */

struct Inherit_K__data {
  /* DO-NOT-DELETE splicer.begin(Inherit.K._data) */
  /* Insert-Code-Here {Inherit.K._data} (private data members) */
  int ignore; /* dummy to force non-empty struct; remove if you add data */
  /* DO-NOT-DELETE splicer.end(Inherit.K._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct Inherit_K__data*
Inherit_K__get_data(
  Inherit_K);

extern void
Inherit_K__set_data(
  Inherit_K,
  struct Inherit_K__data*);

extern
void
impl_Inherit_K__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Inherit_K__ctor(
  /* in */ Inherit_K self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Inherit_K__ctor2(
  /* in */ Inherit_K self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Inherit_K__dtor(
  /* in */ Inherit_K self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_Inherit_K_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern
char*
impl_Inherit_K_a2(
  /* in */ Inherit_K self,
  /* in */ int32_t i,
  /* out */ sidl_BaseInterface *_ex);

extern
char*
impl_Inherit_K_a(
  /* in */ Inherit_K self,
  /* out */ sidl_BaseInterface *_ex);

extern
char*
impl_Inherit_K_h(
  /* in */ Inherit_K self,
  /* out */ sidl_BaseInterface *_ex);

extern
char*
impl_Inherit_K_k(
  /* in */ Inherit_K self,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_Inherit_K_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
