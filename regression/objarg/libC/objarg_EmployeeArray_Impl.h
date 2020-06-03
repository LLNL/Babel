/*
 * File:          objarg_EmployeeArray_Impl.h
 * Symbol:        objarg.EmployeeArray-v0.5
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for objarg.EmployeeArray
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_objarg_EmployeeArray_Impl_h
#define included_objarg_EmployeeArray_Impl_h

#ifndef included_sidl_header_h
#include "sidl_header.h"
#endif
#ifndef included_objarg_Employee_h
#include "objarg_Employee.h"
#endif
#ifndef included_objarg_EmployeeArray_h
#include "objarg_EmployeeArray.h"
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
/* DO-NOT-DELETE splicer.begin(objarg.EmployeeArray._hincludes) */
/* Put additional include files here... */
/* DO-NOT-DELETE splicer.end(objarg.EmployeeArray._hincludes) */

/*
 * Private data for class objarg.EmployeeArray
 */

struct objarg_EmployeeArray__data {
  /* DO-NOT-DELETE splicer.begin(objarg.EmployeeArray._data) */
  objarg_Employee *d_employees;
  int32_t          d_length;
  int32_t          d_capacity;
  /* DO-NOT-DELETE splicer.end(objarg.EmployeeArray._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct objarg_EmployeeArray__data*
objarg_EmployeeArray__get_data(
  objarg_EmployeeArray);

extern void
objarg_EmployeeArray__set_data(
  objarg_EmployeeArray,
  struct objarg_EmployeeArray__data*);

extern
void
impl_objarg_EmployeeArray__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_objarg_EmployeeArray__ctor(
  /* in */ objarg_EmployeeArray self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_objarg_EmployeeArray__ctor2(
  /* in */ objarg_EmployeeArray self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_objarg_EmployeeArray__dtor(
  /* in */ objarg_EmployeeArray self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_objarg_EmployeeArray_fconnect_sidl_BaseInterface(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
extern struct objarg_Employee__object* 
  impl_objarg_EmployeeArray_fconnect_objarg_Employee(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern
int32_t
impl_objarg_EmployeeArray_getLength(
  /* in */ objarg_EmployeeArray self,
  /* out */ sidl_BaseInterface *_ex);

extern
objarg_Employee
impl_objarg_EmployeeArray_at(
  /* in */ objarg_EmployeeArray self,
  /* in */ int32_t idx,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_objarg_EmployeeArray_appendEmployee(
  /* in */ objarg_EmployeeArray self,
  /* in */ objarg_Employee e,
  /* out */ sidl_BaseInterface *_ex);

extern
int32_t
impl_objarg_EmployeeArray_findByName(
  /* in */ objarg_EmployeeArray self,
  /* in */ const char* name,
  /* out */ objarg_Employee* e,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_objarg_EmployeeArray_promoteToMaxSalary(
  /* in */ objarg_EmployeeArray self,
  /* inout */ objarg_Employee* e,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_objarg_EmployeeArray_fconnect_sidl_BaseInterface(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
extern struct objarg_Employee__object* 
  impl_objarg_EmployeeArray_fconnect_objarg_Employee(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
