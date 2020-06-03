/*
 * File:          objarg_EmployeeArray_Impl.c
 * Symbol:        objarg.EmployeeArray-v0.5
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for objarg.EmployeeArray
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "objarg.EmployeeArray" (version 0.5)
 * 
 * This class manages a collection of employees.
 */

#include "objarg_EmployeeArray_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(objarg.EmployeeArray._includes) */
#include <stdlib.h>
#include <string.h>
#include "sidl_String.h"
#include "objarg_Employee.h"
#include "sidl_Exception.h"
/* DO-NOT-DELETE splicer.end(objarg.EmployeeArray._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_objarg_EmployeeArray__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_objarg_EmployeeArray__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(objarg.EmployeeArray._load) */
  /* Insert the implementation of the static class initializer method here... */
    /* DO-NOT-DELETE splicer.end(objarg.EmployeeArray._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_objarg_EmployeeArray__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_objarg_EmployeeArray__ctor(
  /* in */ objarg_EmployeeArray self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(objarg.EmployeeArray._ctor) */
  struct objarg_EmployeeArray__data *dptr =
    malloc(sizeof(struct objarg_EmployeeArray__data));
  if (dptr) {
    dptr->d_length = 0;
    dptr->d_capacity = 0;
    dptr->d_employees = NULL;
  }
  objarg_EmployeeArray__set_data(self, dptr);
    /* DO-NOT-DELETE splicer.end(objarg.EmployeeArray._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_objarg_EmployeeArray__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_objarg_EmployeeArray__ctor2(
  /* in */ objarg_EmployeeArray self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(objarg.EmployeeArray._ctor2) */
  /* Insert-Code-Here {objarg.EmployeeArray._ctor2} (special constructor method) */
    /* DO-NOT-DELETE splicer.end(objarg.EmployeeArray._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_objarg_EmployeeArray__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_objarg_EmployeeArray__dtor(
  /* in */ objarg_EmployeeArray self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(objarg.EmployeeArray._dtor) */
  struct objarg_EmployeeArray__data *dptr = 
    objarg_EmployeeArray__get_data(self);
  if (dptr) {
    int i;
    for(i = 0; i < dptr->d_length; ++i) {
      objarg_Employee employee = (dptr->d_employees)[i];
      (dptr->d_employees)[i] = NULL;
      objarg_Employee_deleteRef(employee, _ex); SIDL_REPORT(*_ex);
    }
    if (dptr->d_employees) {
      free((void *)(dptr->d_employees));
    }
    memset(dptr, 0, sizeof(struct objarg_EmployeeArray__data));
    free((void *)dptr);
  }
 EXIT:
  return;
    /* DO-NOT-DELETE splicer.end(objarg.EmployeeArray._dtor) */
  }
}

/*
 * Return the number of employees in the employee array.
 */

#undef __FUNC__
#define __FUNC__ "impl_objarg_EmployeeArray_getLength"

#ifdef __cplusplus
extern "C"
#endif
int32_t
impl_objarg_EmployeeArray_getLength(
  /* in */ objarg_EmployeeArray self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(objarg.EmployeeArray.getLength) */
  struct objarg_EmployeeArray__data *dptr = 
    objarg_EmployeeArray__get_data(self);
  return dptr ? dptr->d_length : 0;
    /* DO-NOT-DELETE splicer.end(objarg.EmployeeArray.getLength) */
  }
}

/*
 * Return the employee in position <code>idx</code> where
 * <code>idx</code> ranges from 1 to the length of the array.
 * If <code>idx</code> is outside the range of the array (i.e.
 * less than or equal to zero or greater than the current number
 * of elements in the array), this method returns a NULL
 * employee object.
 */

#undef __FUNC__
#define __FUNC__ "impl_objarg_EmployeeArray_at"

#ifdef __cplusplus
extern "C"
#endif
objarg_Employee
impl_objarg_EmployeeArray_at(
  /* in */ objarg_EmployeeArray self,
  /* in */ int32_t idx,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(objarg.EmployeeArray.at) */
  struct objarg_EmployeeArray__data *dptr = 
    objarg_EmployeeArray__get_data(self);
  if (dptr && (idx > 0) && (idx <= dptr->d_length)) {
    objarg_Employee employee = (dptr->d_employees)[idx-1];
    objarg_Employee_addRef(employee, _ex); SIDL_REPORT(*_ex);
    return employee;
  }
 EXIT:
  return NULL;
    /* DO-NOT-DELETE splicer.end(objarg.EmployeeArray.at) */
  }
}

/*
 * Add an employee onto the end of the array.  It is perfectly
 * legal to add the same employee multiple times.
 * <code>true</code> is returned when the append was successful;
 * otherwise, <code>false</code> is returned to indicate
 * failure.  This method will not add a NULL employee.
 */

#undef __FUNC__
#define __FUNC__ "impl_objarg_EmployeeArray_appendEmployee"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_objarg_EmployeeArray_appendEmployee(
  /* in */ objarg_EmployeeArray self,
  /* in */ objarg_Employee e,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(objarg.EmployeeArray.appendEmployee) */
  struct objarg_EmployeeArray__data *dptr = 
    objarg_EmployeeArray__get_data(self);
  if (dptr && e) {
    if (dptr->d_length >= dptr->d_capacity) {
      /* expand array capacity */
      int32_t newcapacity = dptr->d_capacity + 4;
      objarg_Employee *newArray;
      if ((dptr->d_capacity / 10) > 4) {
        newcapacity = dptr->d_capacity + dptr->d_capacity/10;
      }
      newArray = malloc(newcapacity * sizeof(objarg_Employee));
      if (!newArray) return FALSE;
      if (newArray && dptr->d_employees) {
        memcpy(newArray, dptr->d_employees, dptr->d_length *
               sizeof(objarg_Employee));
        free(dptr->d_employees);
      }
      dptr->d_employees = newArray;
      dptr->d_capacity = newcapacity;
    }
    objarg_Employee_addRef(e, _ex); SIDL_REPORT(*_ex);
    dptr->d_employees[dptr->d_length++] = e;
    return TRUE;
  }
 EXIT:
  return FALSE;
    /* DO-NOT-DELETE splicer.end(objarg.EmployeeArray.appendEmployee) */
  }
}

/*
 * Find the first employee in the array that has a name matching
 * <code>name</code>.  If a match exists, the index is returned,
 * and the employee is returned in parameter <code>e</code>.
 * 
 * If no match exists, 0 is returned, and <code>e</code> is NULL.
 */

#undef __FUNC__
#define __FUNC__ "impl_objarg_EmployeeArray_findByName"

#ifdef __cplusplus
extern "C"
#endif
int32_t
impl_objarg_EmployeeArray_findByName(
  /* in */ objarg_EmployeeArray self,
  /* in */ const char* name,
  /* out */ objarg_Employee* e,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(objarg.EmployeeArray.findByName) */
  if (name && e) {
    struct objarg_EmployeeArray__data *dptr = 
      objarg_EmployeeArray__get_data(self);
    *e = NULL;
    if (dptr) {
      int32_t i;
      for(i = 0; i < dptr->d_length; ++i) {
        objarg_Employee tmp = (dptr->d_employees)[i];
        char *match = objarg_Employee_getName(tmp,_ex);SIDL_REPORT(*_ex);
	{
	  const int equal = !strcmp(match, name);
	  sidl_String_free(match);
	  if (equal) {
	    objarg_Employee_addRef(tmp, _ex); SIDL_REPORT(*_ex);
	    *e = tmp;
	    return i + 1;
	  }
	}
      }
    }
  }
 EXIT:
  return 0;
    /* DO-NOT-DELETE splicer.end(objarg.EmployeeArray.findByName) */
  }
}

/*
 * Determine the maximum salary in the array. If the maximum
 * salary in the array is greater than the current salary of
 * <code>e</code>, the salary of <code>e</code> will be 
 * increased to the maximum salary in the array.  If the
 * array is empty, no change will be made to <code>e</code>.
 * 
 * This method returns <code>true</code> iff the salary of
 * <code>e</code> is modified.
 */

#undef __FUNC__
#define __FUNC__ "impl_objarg_EmployeeArray_promoteToMaxSalary"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_objarg_EmployeeArray_promoteToMaxSalary(
  /* in */ objarg_EmployeeArray self,
  /* inout */ objarg_Employee* e,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(objarg.EmployeeArray.promoteToMaxSalary) */
  if (*e) {
    struct objarg_EmployeeArray__data *dptr = 
      objarg_EmployeeArray__get_data(self);
    if (dptr && dptr->d_length) {
      float maxSalary = -1.0e30;
      int i;
      for(i = 0; i < dptr->d_length; ++i){
        float salary = objarg_Employee_getSalary(dptr->d_employees[i], _ex); 
	SIDL_REPORT(*_ex);
        if (salary > maxSalary) {
          maxSalary = salary;
        }
      }
      if (maxSalary > objarg_Employee_getSalary(*e, _ex)) {
	SIDL_REPORT(*_ex);
        objarg_Employee_setSalary(*e, maxSalary, _ex); SIDL_REPORT(*_ex);
        return TRUE;
      }
    }
  }
 EXIT:
  return FALSE;
    /* DO-NOT-DELETE splicer.end(objarg.EmployeeArray.promoteToMaxSalary) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

