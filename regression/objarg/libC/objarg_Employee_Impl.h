/*
 * File:          objarg_Employee_Impl.h
 * Symbol:        objarg.Employee-v0.5
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for objarg.Employee
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_objarg_Employee_Impl_h
#define included_objarg_Employee_Impl_h

#ifndef included_sidl_header_h
#include "sidl_header.h"
#endif
#ifndef included_objarg_Employee_h
#include "objarg_Employee.h"
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
/* DO-NOT-DELETE splicer.begin(objarg.Employee._hincludes) */
/* Put additional include files here... */
/* DO-NOT-DELETE splicer.end(objarg.Employee._hincludes) */

/*
 * Private data for class objarg.Employee
 */

struct objarg_Employee__data {
  /* DO-NOT-DELETE splicer.begin(objarg.Employee._data) */
  char    *d_name;
  int32_t  d_age;
  float    d_salary;
  char     d_status;
  /* DO-NOT-DELETE splicer.end(objarg.Employee._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct objarg_Employee__data*
objarg_Employee__get_data(
  objarg_Employee);

extern void
objarg_Employee__set_data(
  objarg_Employee,
  struct objarg_Employee__data*);

extern
void
impl_objarg_Employee__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_objarg_Employee__ctor(
  /* in */ objarg_Employee self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_objarg_Employee__ctor2(
  /* in */ objarg_Employee self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_objarg_Employee__dtor(
  /* in */ objarg_Employee self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_objarg_Employee_fconnect_sidl_BaseInterface(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern
sidl_bool
impl_objarg_Employee_init(
  /* in */ objarg_Employee self,
  /* in */ const char* name,
  /* in */ int32_t age,
  /* in */ float salary,
  /* in */ char status,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_objarg_Employee_setName(
  /* in */ objarg_Employee self,
  /* in */ const char* name,
  /* out */ sidl_BaseInterface *_ex);

extern
char*
impl_objarg_Employee_getName(
  /* in */ objarg_Employee self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_objarg_Employee_setAge(
  /* in */ objarg_Employee self,
  /* in */ int32_t age,
  /* out */ sidl_BaseInterface *_ex);

extern
int32_t
impl_objarg_Employee_getAge(
  /* in */ objarg_Employee self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_objarg_Employee_setSalary(
  /* in */ objarg_Employee self,
  /* in */ float salary,
  /* out */ sidl_BaseInterface *_ex);

extern
float
impl_objarg_Employee_getSalary(
  /* in */ objarg_Employee self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_objarg_Employee_setStatus(
  /* in */ objarg_Employee self,
  /* in */ char status,
  /* out */ sidl_BaseInterface *_ex);

extern
char
impl_objarg_Employee_getStatus(
  /* in */ objarg_Employee self,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_objarg_Employee_fconnect_sidl_BaseInterface(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
