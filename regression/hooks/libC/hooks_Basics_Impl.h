/*
 * File:          hooks_Basics_Impl.h
 * Symbol:        hooks.Basics-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for hooks.Basics
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_hooks_Basics_Impl_h
#define included_hooks_Basics_Impl_h

#ifndef included_sidl_header_h
#include "sidl_header.h"
#endif
#ifndef included_hooks_Basics_h
#include "hooks_Basics.h"
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
/* DO-NOT-DELETE splicer.begin(hooks.Basics._hincludes) */
/* DO-NOT-DELETE splicer.end(hooks.Basics._hincludes) */

/*
 * Private data for class hooks.Basics
 */

struct hooks_Basics__data {
  /* DO-NOT-DELETE splicer.begin(hooks.Basics._data) */
  /* Put private data members here... */
  int num_prehooks;
  int num_posthooks;
  /* DO-NOT-DELETE splicer.end(hooks.Basics._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct hooks_Basics__data*
hooks_Basics__get_data(
  hooks_Basics);

extern void
hooks_Basics__set_data(
  hooks_Basics,
  struct hooks_Basics__data*);

extern
void
impl_hooks_Basics__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_hooks_Basics__ctor(
  /* in */ hooks_Basics self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_hooks_Basics__ctor2(
  /* in */ hooks_Basics self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_hooks_Basics__dtor(
  /* in */ hooks_Basics self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

extern
void
impl_hooks_Basics_aStaticMeth_pre(
  /* in */ int32_t i,
  /* in */ int32_t io,
  /* out */ sidl_BaseInterface *_ex);

extern
int32_t
impl_hooks_Basics_aStaticMeth(
  /* in */ int32_t i,
  /* out */ int32_t* o,
  /* inout */ int32_t* io,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_hooks_Basics_aStaticMeth_post(
  /* in */ int32_t i,
  /* in */ int32_t o,
  /* in */ int32_t io,
  /* in */ int32_t _retval,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_hooks_Basics_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern
void
impl_hooks_Basics_aNonStaticMeth_pre(
  /* in */ hooks_Basics self,
  /* in */ int32_t i,
  /* in */ int32_t io,
  /* out */ sidl_BaseInterface *_ex);

extern
int32_t
impl_hooks_Basics_aNonStaticMeth(
  /* in */ hooks_Basics self,
  /* in */ int32_t i,
  /* out */ int32_t* o,
  /* inout */ int32_t* io,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_hooks_Basics_aNonStaticMeth_post(
  /* in */ hooks_Basics self,
  /* in */ int32_t i,
  /* in */ int32_t o,
  /* in */ int32_t io,
  /* in */ int32_t _retval,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_hooks_Basics_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
