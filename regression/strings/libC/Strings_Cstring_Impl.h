/*
 * File:          Strings_Cstring_Impl.h
 * Symbol:        Strings.Cstring-v1.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Strings.Cstring
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_Strings_Cstring_Impl_h
#define included_Strings_Cstring_Impl_h

#ifndef included_sidl_header_h
#include "sidl_header.h"
#endif
#ifndef included_Strings_Cstring_h
#include "Strings_Cstring.h"
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
/* DO-NOT-DELETE splicer.begin(Strings.Cstring._hincludes) */
/* Put additional include files here... */
/* DO-NOT-DELETE splicer.end(Strings.Cstring._hincludes) */

/*
 * Private data for class Strings.Cstring
 */

struct Strings_Cstring__data {
  /* DO-NOT-DELETE splicer.begin(Strings.Cstring._data) */
  /* Put private data members here... */
  int ignore; /* dummy to force non-empty struct; remove if you add data */
  /* DO-NOT-DELETE splicer.end(Strings.Cstring._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct Strings_Cstring__data*
Strings_Cstring__get_data(
  Strings_Cstring);

extern void
Strings_Cstring__set_data(
  Strings_Cstring,
  struct Strings_Cstring__data*);

extern
void
impl_Strings_Cstring__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Strings_Cstring__ctor(
  /* in */ Strings_Cstring self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Strings_Cstring__ctor2(
  /* in */ Strings_Cstring self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Strings_Cstring__dtor(
  /* in */ Strings_Cstring self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_Strings_Cstring_fconnect_sidl_BaseInterface(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern
char*
impl_Strings_Cstring_returnback(
  /* in */ Strings_Cstring self,
  /* in */ sidl_bool nonNull,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_Strings_Cstring_passin(
  /* in */ Strings_Cstring self,
  /* in */ const char* c,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_Strings_Cstring_passout(
  /* in */ Strings_Cstring self,
  /* in */ sidl_bool nonNull,
  /* out */ char** c,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_Strings_Cstring_passinout(
  /* in */ Strings_Cstring self,
  /* inout */ char** c,
  /* out */ sidl_BaseInterface *_ex);

extern
char*
impl_Strings_Cstring_passeverywhere(
  /* in */ Strings_Cstring self,
  /* in */ const char* c1,
  /* out */ char** c2,
  /* inout */ char** c3,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_Strings_Cstring_mixedarguments(
  /* in */ Strings_Cstring self,
  /* in */ const char* s1,
  /* in */ char c1,
  /* in */ const char* s2,
  /* in */ char c2,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_Strings_Cstring_fconnect_sidl_BaseInterface(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
