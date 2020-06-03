/*
 * File:          hello_World_Impl.h
 * Symbol:        hello.World-v1.0
 * Symbol Type:   class
 * Babel Version: 1.5.0 (Revision: 6727M trunk)
 * Description:   Server-side implementation for hello.World
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_hello_World_Impl_h
#define included_hello_World_Impl_h

#ifndef included_sidl_header_h
#include "sidl_header.h"
#endif
#ifndef included_hello_World_h
#include "hello_World.h"
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
/* DO-NOT-DELETE splicer.begin(hello.World._hincludes) */
/* insert code here (include files) */
/* DO-NOT-DELETE splicer.end(hello.World._hincludes) */

/*
 * Private data for class hello.World
 */

struct hello_World__data {
  /* DO-NOT-DELETE splicer.begin(hello.World._data) */
  char * d_name;
  /* DO-NOT-DELETE splicer.end(hello.World._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct hello_World__data*
hello_World__get_data(
  hello_World);

extern void
hello_World__set_data(
  hello_World,
  struct hello_World__data*);

extern
void
impl_hello_World__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_hello_World__ctor(
  /* in */ hello_World self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_hello_World__ctor2(
  /* in */ hello_World self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_hello_World__dtor(
  /* in */ hello_World self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_hello_World_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern
void
impl_hello_World_setName(
  /* in */ hello_World self,
  /* in */ const char* name,
  /* out */ sidl_BaseInterface *_ex);

extern
char*
impl_hello_World_getMsg(
  /* in */ hello_World self,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_hello_World_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* insert code here (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
