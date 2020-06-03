/*
 * File:          Overload_BClass_Impl.h
 * Symbol:        Overload.BClass-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Overload.BClass
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_Overload_BClass_Impl_h
#define included_Overload_BClass_Impl_h

#ifndef included_sidl_header_h
#include "sidl_header.h"
#endif
#ifndef included_Overload_AClass_h
#include "Overload_AClass.h"
#endif
#ifndef included_Overload_BClass_h
#include "Overload_BClass.h"
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
/* DO-NOT-DELETE splicer.begin(Overload.BClass._hincludes) */
/* Put additional include files here... */
/* DO-NOT-DELETE splicer.end(Overload.BClass._hincludes) */

/*
 * Private data for class Overload.BClass
 */

struct Overload_BClass__data {
  /* DO-NOT-DELETE splicer.begin(Overload.BClass._data) */
  /* Put private data members here... */
  int ignore; /* dummy to force non-empty struct; remove if you add data */
  /* DO-NOT-DELETE splicer.end(Overload.BClass._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct Overload_BClass__data*
Overload_BClass__get_data(
  Overload_BClass);

extern void
Overload_BClass__set_data(
  Overload_BClass,
  struct Overload_BClass__data*);

extern
void
impl_Overload_BClass__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Overload_BClass__ctor(
  /* in */ Overload_BClass self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Overload_BClass__ctor2(
  /* in */ Overload_BClass self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Overload_BClass__dtor(
  /* in */ Overload_BClass self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_Overload_BClass_fconnect_sidl_BaseInterface(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_Overload_BClass_fconnect_sidl_BaseInterface(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
