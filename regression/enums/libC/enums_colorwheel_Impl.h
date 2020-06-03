/*
 * File:          enums_colorwheel_Impl.h
 * Symbol:        enums.colorwheel-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for enums.colorwheel
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_enums_colorwheel_Impl_h
#define included_enums_colorwheel_Impl_h

#ifndef included_sidl_header_h
#include "sidl_header.h"
#endif
#ifndef included_enums_colorwheel_h
#include "enums_colorwheel.h"
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
/* DO-NOT-DELETE splicer.begin(enums.colorwheel._hincludes) */
/* Put additional include files here... */
/* DO-NOT-DELETE splicer.end(enums.colorwheel._hincludes) */

/*
 * Private data for class enums.colorwheel
 */

struct enums_colorwheel__data {
  /* DO-NOT-DELETE splicer.begin(enums.colorwheel._data) */
  /* Put private data members here... */
  int ignore; /* dummy to force non-empty struct; remove if you add data */
  /* DO-NOT-DELETE splicer.end(enums.colorwheel._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct enums_colorwheel__data*
enums_colorwheel__get_data(
  enums_colorwheel);

extern void
enums_colorwheel__set_data(
  enums_colorwheel,
  struct enums_colorwheel__data*);

extern
void
impl_enums_colorwheel__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_enums_colorwheel__ctor(
  /* in */ enums_colorwheel self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_enums_colorwheel__ctor2(
  /* in */ enums_colorwheel self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_enums_colorwheel__dtor(
  /* in */ enums_colorwheel self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_enums_colorwheel_fconnect_sidl_BaseInterface(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern
int64_t
impl_enums_colorwheel_returnback(
  /* in */ enums_colorwheel self,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_enums_colorwheel_passin(
  /* in */ enums_colorwheel self,
  /* in */ enum enums_color__enum c,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_enums_colorwheel_passout(
  /* in */ enums_colorwheel self,
  /* out */ enum enums_color__enum* c,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_enums_colorwheel_passinout(
  /* in */ enums_colorwheel self,
  /* inout */ enum enums_color__enum* c,
  /* out */ sidl_BaseInterface *_ex);

extern
int64_t
impl_enums_colorwheel_passeverywhere(
  /* in */ enums_colorwheel self,
  /* in */ enum enums_color__enum c1,
  /* out */ enum enums_color__enum* c2,
  /* inout */ enum enums_color__enum* c3,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_enums_colorwheel_fconnect_sidl_BaseInterface(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
