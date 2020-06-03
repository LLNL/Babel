/*
 * File:          c_Ruleset_Impl.h
 * Symbol:        c.Ruleset-v2.0
 * Symbol Type:   class
 * Babel Version: 1.1.1
 * Description:   Server-side implementation for c.Ruleset
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_c_Ruleset_Impl_h
#define included_c_Ruleset_Impl_h

#ifndef included_sidl_header_h
#include "sidl_header.h"
#endif
#ifndef included_c_Ruleset_h
#include "c_Ruleset.h"
#endif
#ifndef included_conway_BoundsException_h
#include "conway_BoundsException.h"
#endif
#ifndef included_conway_Environment_h
#include "conway_Environment.h"
#endif
#ifndef included_conway_Ruleset_h
#include "conway_Ruleset.h"
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

/* DO-NOT-DELETE splicer.begin(c.Ruleset._hincludes) */
/* Insert-Code-Here {c.Ruleset._hincludes} (include files) */
/* DO-NOT-DELETE splicer.end(c.Ruleset._hincludes) */

/*
 * Private data for class c.Ruleset
 */

struct c_Ruleset__data {
  /* DO-NOT-DELETE splicer.begin(c.Ruleset._data) */
  /* Put private data members here... */
  int ignore; /* dummy to force non-empty struct; remove if you add data */
  /* DO-NOT-DELETE splicer.end(c.Ruleset._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct c_Ruleset__data*
c_Ruleset__get_data(
  c_Ruleset);

extern void
c_Ruleset__set_data(
  c_Ruleset,
  struct c_Ruleset__data*);

extern
void
impl_c_Ruleset__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_c_Ruleset__ctor(
  /* in */ c_Ruleset self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_c_Ruleset__ctor2(
  /* in */ c_Ruleset self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_c_Ruleset__dtor(
  /* in */ c_Ruleset self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_c_Ruleset_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
extern struct conway_Environment__object* 
  impl_c_Ruleset_fconnect_conway_Environment(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern
sidl_bool
impl_c_Ruleset_setAlive(
  /* in */ c_Ruleset self,
  /* in */ int32_t x,
  /* in */ int32_t y,
  /* in */ conway_Environment env,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_c_Ruleset_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
extern struct conway_Environment__object* 
  impl_c_Ruleset_fconnect_conway_Environment(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
