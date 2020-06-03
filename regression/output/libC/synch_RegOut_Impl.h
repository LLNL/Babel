/*
 * File:          synch_RegOut_Impl.h
 * Symbol:        synch.RegOut-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for synch.RegOut
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_synch_RegOut_Impl_h
#define included_synch_RegOut_Impl_h

#ifndef included_sidl_header_h
#include "sidl_header.h"
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
#ifndef included_synch_RegOut_h
#include "synch_RegOut.h"
#endif
/* DO-NOT-DELETE splicer.begin(synch.RegOut._hincludes) */
#include <stdio.h>
/* DO-NOT-DELETE splicer.end(synch.RegOut._hincludes) */

/*
 * Private data for class synch.RegOut
 */

struct synch_RegOut__data {
  /* DO-NOT-DELETE splicer.begin(synch.RegOut._data) */
  int32_t expected_parts;
  int32_t current_part;
  int32_t num_passes;
  int32_t num_xfails;
  int32_t num_xpass;
  int32_t num_fails;
  int32_t state;
  /* DO-NOT-DELETE splicer.end(synch.RegOut._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct synch_RegOut__data*
synch_RegOut__get_data(
  synch_RegOut);

extern void
synch_RegOut__set_data(
  synch_RegOut,
  struct synch_RegOut__data*);

extern
void
impl_synch_RegOut__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_synch_RegOut__ctor(
  /* in */ synch_RegOut self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_synch_RegOut__ctor2(
  /* in */ synch_RegOut self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_synch_RegOut__dtor(
  /* in */ synch_RegOut self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

extern
synch_RegOut
impl_synch_RegOut_getInstance(
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_synch_RegOut_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern
void
impl_synch_RegOut_setExpectations(
  /* in */ synch_RegOut self,
  /* in */ int32_t numparts,
  /* out */ sidl_BaseInterface *_ex);

extern
int32_t
impl_synch_RegOut_getCurrentPart(
  /* in */ synch_RegOut self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_synch_RegOut_startPart(
  /* in */ synch_RegOut self,
  /* in */ int32_t part,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_synch_RegOut_endPart(
  /* in */ synch_RegOut self,
  /* in */ int32_t part,
  /* in */ enum synch_ResultType__enum res,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_synch_RegOut_writeComment(
  /* in */ synch_RegOut self,
  /* in */ const char* comment,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_synch_RegOut_forceFailure(
  /* in */ synch_RegOut self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_synch_RegOut_close(
  /* in */ synch_RegOut self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_synch_RegOut_replaceMagicVars(
  /* in */ synch_RegOut self,
  /* inout */ char** orig,
  /* in */ const char* prog_name,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_synch_RegOut_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
