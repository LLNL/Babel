/*
 * File:          Overload_AClass_Impl.h
 * Symbol:        Overload.AClass-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Overload.AClass
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_Overload_AClass_Impl_h
#define included_Overload_AClass_Impl_h

#ifndef included_sidl_header_h
#include "sidl_header.h"
#endif
#ifndef included_Overload_AClass_h
#include "Overload_AClass.h"
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
/* DO-NOT-DELETE splicer.begin(Overload.AClass._hincludes) */
/* Put additional include files here... */
/* DO-NOT-DELETE splicer.end(Overload.AClass._hincludes) */

/*
 * Private data for class Overload.AClass
 */

struct Overload_AClass__data {
  /* DO-NOT-DELETE splicer.begin(Overload.AClass._data) */
  /* Put private data members here... */
  int ignore; /* dummy to force non-empty struct; remove if you add data */
  /* DO-NOT-DELETE splicer.end(Overload.AClass._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct Overload_AClass__data*
Overload_AClass__get_data(
  Overload_AClass);

extern void
Overload_AClass__set_data(
  Overload_AClass,
  struct Overload_AClass__data*);

extern
void
impl_Overload_AClass__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Overload_AClass__ctor(
  /* in */ Overload_AClass self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Overload_AClass__ctor2(
  /* in */ Overload_AClass self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Overload_AClass__dtor(
  /* in */ Overload_AClass self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_Overload_AClass_fconnect_sidl_BaseInterface(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern
int32_t
impl_Overload_AClass_getValue(
  /* in */ Overload_AClass self,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_Overload_AClass_fconnect_sidl_BaseInterface(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
