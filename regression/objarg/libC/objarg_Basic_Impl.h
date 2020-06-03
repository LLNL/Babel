/*
 * File:          objarg_Basic_Impl.h
 * Symbol:        objarg.Basic-v0.5
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for objarg.Basic
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_objarg_Basic_Impl_h
#define included_objarg_Basic_Impl_h

#ifndef included_sidl_header_h
#include "sidl_header.h"
#endif
#ifndef included_objarg_Basic_h
#include "objarg_Basic.h"
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
/* DO-NOT-DELETE splicer.begin(objarg.Basic._hincludes) */
/* Insert-Code-Here {objarg.Basic._includes} (include files) */
/* DO-NOT-DELETE splicer.end(objarg.Basic._hincludes) */

/*
 * Private data for class objarg.Basic
 */

struct objarg_Basic__data {
  /* DO-NOT-DELETE splicer.begin(objarg.Basic._data) */
  /* Insert-Code-Here {objarg.Basic._data} (private data members) */
  int ignore; /* dummy to force non-empty struct; remove if you add data */
  /* DO-NOT-DELETE splicer.end(objarg.Basic._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct objarg_Basic__data*
objarg_Basic__get_data(
  objarg_Basic);

extern void
objarg_Basic__set_data(
  objarg_Basic,
  struct objarg_Basic__data*);

extern
void
impl_objarg_Basic__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_objarg_Basic__ctor(
  /* in */ objarg_Basic self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_objarg_Basic__ctor2(
  /* in */ objarg_Basic self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_objarg_Basic__dtor(
  /* in */ objarg_Basic self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_BaseClass__object* impl_objarg_Basic_fconnect_sidl_BaseClass(
  const char* url, sidl_bool ar, sidl_BaseInterface *_ex);
extern struct sidl_BaseInterface__object* 
  impl_objarg_Basic_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern
sidl_bool
impl_objarg_Basic_passIn(
  /* in */ objarg_Basic self,
  /* in */ sidl_BaseClass o,
  /* in */ sidl_bool inNotNull,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_objarg_Basic_passInOut(
  /* in */ objarg_Basic self,
  /* inout */ sidl_BaseClass* o,
  /* in */ sidl_bool inNotNull,
  /* in */ sidl_bool outNotNull,
  /* in */ sidl_bool retSame,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_objarg_Basic_passOut(
  /* in */ objarg_Basic self,
  /* out */ sidl_BaseClass* o,
  /* in */ sidl_bool passOutNull,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_BaseClass
impl_objarg_Basic_retObject(
  /* in */ objarg_Basic self,
  /* in */ sidl_bool retNull,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseClass__object* impl_objarg_Basic_fconnect_sidl_BaseClass(
  const char* url, sidl_bool ar, sidl_BaseInterface *_ex);
extern struct sidl_BaseInterface__object* 
  impl_objarg_Basic_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
