/*
 * File:          objarg_Employee_Impl.c
 * Symbol:        objarg.Employee-v0.5
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for objarg.Employee
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "objarg.Employee" (version 0.5)
 * 
 * This object type holds the basic information about an employee:
 * name, age, salary and marital status.  This object exists purely
 * to serve as a test case for sidl.  It is not intended for serious
 * use.
 */

#include "objarg_Employee_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(objarg.Employee._includes) */
#include <stdlib.h>
#include "sidl_String.h"
#include "sidl_Exception.h"
/* DO-NOT-DELETE splicer.end(objarg.Employee._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_objarg_Employee__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_objarg_Employee__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(objarg.Employee._load) */
  /* Insert the implementation of the static class initializer method here... */
    /* DO-NOT-DELETE splicer.end(objarg.Employee._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_objarg_Employee__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_objarg_Employee__ctor(
  /* in */ objarg_Employee self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(objarg.Employee._ctor) */
  struct objarg_Employee__data *dptr = 
    malloc(sizeof(struct objarg_Employee__data));
  if (dptr) {
    dptr->d_name = NULL;
    dptr->d_salary = 0;
    dptr->d_status = '\0';
    dptr->d_age = 0;
  }
  objarg_Employee__set_data(self, dptr);
    /* DO-NOT-DELETE splicer.end(objarg.Employee._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_objarg_Employee__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_objarg_Employee__ctor2(
  /* in */ objarg_Employee self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(objarg.Employee._ctor2) */
  /* Insert-Code-Here {objarg.Employee._ctor2} (special constructor method) */
    /* DO-NOT-DELETE splicer.end(objarg.Employee._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_objarg_Employee__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_objarg_Employee__dtor(
  /* in */ objarg_Employee self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(objarg.Employee._dtor) */
  struct objarg_Employee__data *dptr = 
    objarg_Employee__get_data(self);
  if (dptr) {
    sidl_String_free((char *)dptr->d_name);
    dptr->d_name = NULL;
    free((void*)dptr);
    objarg_Employee__set_data(self, NULL);
  }
    /* DO-NOT-DELETE splicer.end(objarg.Employee._dtor) */
  }
}

/*
 * Provide the data for the employee object to hold.
 * <code>false</code> is returned when the data was unacceptable.
 * <code>true</code> means the employee object was successfully
 * initialized.
 */

#undef __FUNC__
#define __FUNC__ "impl_objarg_Employee_init"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_objarg_Employee_init(
  /* in */ objarg_Employee self,
  /* in */ const char* name,
  /* in */ int32_t age,
  /* in */ float salary,
  /* in */ char status,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(objarg.Employee.init) */
  objarg_Employee_setName(self, name, _ex); SIDL_REPORT(*_ex); 
  objarg_Employee_setAge(self, age, _ex); SIDL_REPORT(*_ex);
  objarg_Employee_setSalary(self, salary, _ex); SIDL_REPORT(*_ex);
  objarg_Employee_setStatus(self, status, _ex); SIDL_REPORT(*_ex);
  return TRUE;
 EXIT:
  return FALSE;
    /* DO-NOT-DELETE splicer.end(objarg.Employee.init) */
  }
}

/*
 * Change the name of an employee.
 */

#undef __FUNC__
#define __FUNC__ "impl_objarg_Employee_setName"

#ifdef __cplusplus
extern "C"
#endif
void
impl_objarg_Employee_setName(
  /* in */ objarg_Employee self,
  /* in */ const char* name,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(objarg.Employee.setName) */
  struct objarg_Employee__data *dptr = 
    objarg_Employee__get_data(self);
  if (dptr) {
    dptr->d_name = sidl_String_strdup(name);
  }
    /* DO-NOT-DELETE splicer.end(objarg.Employee.setName) */
  }
}

/*
 * Return the name of an employee.
 */

#undef __FUNC__
#define __FUNC__ "impl_objarg_Employee_getName"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_objarg_Employee_getName(
  /* in */ objarg_Employee self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(objarg.Employee.getName) */
  struct objarg_Employee__data *dptr = 
    objarg_Employee__get_data(self);
  return dptr ? sidl_String_strdup(dptr->d_name) : 0;
    /* DO-NOT-DELETE splicer.end(objarg.Employee.getName) */
  }
}

/*
 * Change the age of an employee.
 */

#undef __FUNC__
#define __FUNC__ "impl_objarg_Employee_setAge"

#ifdef __cplusplus
extern "C"
#endif
void
impl_objarg_Employee_setAge(
  /* in */ objarg_Employee self,
  /* in */ int32_t age,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(objarg.Employee.setAge) */
  struct objarg_Employee__data *dptr = 
    objarg_Employee__get_data(self);
  if (dptr) {
    dptr->d_age = age;
  }
    /* DO-NOT-DELETE splicer.end(objarg.Employee.setAge) */
  }
}

/*
 * Return the age of an employee.
 */

#undef __FUNC__
#define __FUNC__ "impl_objarg_Employee_getAge"

#ifdef __cplusplus
extern "C"
#endif
int32_t
impl_objarg_Employee_getAge(
  /* in */ objarg_Employee self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(objarg.Employee.getAge) */
  struct objarg_Employee__data *dptr = 
    objarg_Employee__get_data(self);
  return dptr ? dptr->d_age : 0;
    /* DO-NOT-DELETE splicer.end(objarg.Employee.getAge) */
  }
}

/*
 * Set an employee's salary.
 */

#undef __FUNC__
#define __FUNC__ "impl_objarg_Employee_setSalary"

#ifdef __cplusplus
extern "C"
#endif
void
impl_objarg_Employee_setSalary(
  /* in */ objarg_Employee self,
  /* in */ float salary,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(objarg.Employee.setSalary) */
  struct objarg_Employee__data *dptr = 
    objarg_Employee__get_data(self);
  if (dptr) {
    dptr->d_salary = salary;
  }
    /* DO-NOT-DELETE splicer.end(objarg.Employee.setSalary) */
  }
}

/*
 * Return an employee's salary.
 */

#undef __FUNC__
#define __FUNC__ "impl_objarg_Employee_getSalary"

#ifdef __cplusplus
extern "C"
#endif
float
impl_objarg_Employee_getSalary(
  /* in */ objarg_Employee self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(objarg.Employee.getSalary) */
  struct objarg_Employee__data *dptr = 
    objarg_Employee__get_data(self);
  return dptr ? dptr->d_salary : 0;
    /* DO-NOT-DELETE splicer.end(objarg.Employee.getSalary) */
  }
}

/*
 * Set an employee's marital status.
 */

#undef __FUNC__
#define __FUNC__ "impl_objarg_Employee_setStatus"

#ifdef __cplusplus
extern "C"
#endif
void
impl_objarg_Employee_setStatus(
  /* in */ objarg_Employee self,
  /* in */ char status,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(objarg.Employee.setStatus) */
  struct objarg_Employee__data *dptr = 
    objarg_Employee__get_data(self);
  if (dptr) {
    dptr->d_status = status;
  }
    /* DO-NOT-DELETE splicer.end(objarg.Employee.setStatus) */
  }
}

/*
 * Return an employee's marital status.
 */

#undef __FUNC__
#define __FUNC__ "impl_objarg_Employee_getStatus"

#ifdef __cplusplus
extern "C"
#endif
char
impl_objarg_Employee_getStatus(
  /* in */ objarg_Employee self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(objarg.Employee.getStatus) */
  struct objarg_Employee__data *dptr = 
    objarg_Employee__get_data(self);
  return dptr ? dptr->d_status : '\0';
    /* DO-NOT-DELETE splicer.end(objarg.Employee.getStatus) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

