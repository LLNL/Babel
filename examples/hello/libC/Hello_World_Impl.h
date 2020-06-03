/*
 * File:          Hello_World_Impl.h
 * Symbol:        Hello.World-v1.2
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7399M trunk)
 * Description:   Server-side implementation for Hello.World
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_Hello_World_Impl_h
#define included_Hello_World_Impl_h

#ifndef included_sidl_header_h
#include "sidl_header.h"
#endif
#ifndef included_Hello_World_h
#include "Hello_World.h"
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
/* DO-NOT-DELETE splicer.begin(Hello.World._hincludes) */
/* Put additional include files here... */
/* DO-NOT-DELETE splicer.end(Hello.World._hincludes) */

/*
 * Private data for class Hello.World
 */

struct Hello_World__data {
  /* DO-NOT-DELETE splicer.begin(Hello.World._data) */
  /* Put private data members here... */
  int ignore; /* dummy to force non-empty struct; remove if you add data */
  /* DO-NOT-DELETE splicer.end(Hello.World._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct Hello_World__data*
Hello_World__get_data(
  Hello_World);

extern void
Hello_World__set_data(
  Hello_World,
  struct Hello_World__data*);

extern
void
impl_Hello_World__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Hello_World__ctor(
  /* in */ Hello_World self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Hello_World__ctor2(
  /* in */ Hello_World self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Hello_World__dtor(
  /* in */ Hello_World self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_Hello_World_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern
char*
impl_Hello_World_getMsg(
  /* in */ Hello_World self,
  /* out */ sidl_BaseInterface *_ex);

extern
int32_t
impl_Hello_World_foo(
  /* in */ Hello_World self,
  /* in */ int32_t i,
  /* out */ int32_t* o,
  /* inout */ int32_t* io,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_Hello_World_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
