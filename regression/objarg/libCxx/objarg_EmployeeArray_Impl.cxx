// 
// File:          objarg_EmployeeArray_Impl.cxx
// Symbol:        objarg.EmployeeArray-v0.5
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for objarg.EmployeeArray
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "objarg_EmployeeArray_Impl.hxx"

// 
// Includes for all method dependencies.
// 
#ifndef included_objarg_Employee_hxx
#include "objarg_Employee.hxx"
#endif
#ifndef included_sidl_BaseInterface_hxx
#include "sidl_BaseInterface.hxx"
#endif
#ifndef included_sidl_ClassInfo_hxx
#include "sidl_ClassInfo.hxx"
#endif
#ifndef included_sidl_NotImplementedException_hxx
#include "sidl_NotImplementedException.hxx"
#endif
// DO-NOT-DELETE splicer.begin(objarg.EmployeeArray._includes)
#include <float.h>
using namespace objarg;
// DO-NOT-DELETE splicer.end(objarg.EmployeeArray._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
objarg::EmployeeArray_impl::EmployeeArray_impl() : StubBase(reinterpret_cast< 
  void*>(::objarg::EmployeeArray::_wrapObj(reinterpret_cast< void*>(this))),
  false) , _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(objarg.EmployeeArray._ctor2)
  // Insert-Code-Here {objarg.EmployeeArray._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(objarg.EmployeeArray._ctor2)
}

// user defined constructor
void objarg::EmployeeArray_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(objarg.EmployeeArray._ctor)
  // add construction details here
  // DO-NOT-DELETE splicer.end(objarg.EmployeeArray._ctor)
}

// user defined destructor
void objarg::EmployeeArray_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(objarg.EmployeeArray._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(objarg.EmployeeArray._dtor)
}

// static class initializer
void objarg::EmployeeArray_impl::_load() {
  // DO-NOT-DELETE splicer.begin(objarg.EmployeeArray._load)
  // guaranteed to be called at most once before any other method in this class
  // DO-NOT-DELETE splicer.end(objarg.EmployeeArray._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Return the number of employees in the employee array.
 */
int32_t
objarg::EmployeeArray_impl::getLength_impl () 

{
  // DO-NOT-DELETE splicer.begin(objarg.EmployeeArray.getLength)
  return d_array.size();
  // DO-NOT-DELETE splicer.end(objarg.EmployeeArray.getLength)
}

/**
 * Return the employee in position <code>idx</code> where
 * <code>idx</code> ranges from 1 to the length of the array.
 * If <code>idx</code> is outside the range of the array (i.e.
 * less than or equal to zero or greater than the current number
 * of elements in the array), this method returns a NULL
 * employee object.
 */
::objarg::Employee
objarg::EmployeeArray_impl::at_impl (
  /* in */int32_t idx ) 
{
  // DO-NOT-DELETE splicer.begin(objarg.EmployeeArray.at)
  if ( idx >= 1 && idx <= (int32_t) d_array.size() ) {
    return d_array[idx-1];
  } else { 
    return Employee();
  }
  // DO-NOT-DELETE splicer.end(objarg.EmployeeArray.at)
}

/**
 * Add an employee onto the end of the array.  It is perfectly
 * legal to add the same employee multiple times.
 * <code>true</code> is returned when the append was successful;
 * otherwise, <code>false</code> is returned to indicate
 * failure.  This method will not add a NULL employee.
 */
bool
objarg::EmployeeArray_impl::appendEmployee_impl (
  /* in */::objarg::Employee& e ) 
{
  // DO-NOT-DELETE splicer.begin(objarg.EmployeeArray.appendEmployee)
  if (e._not_nil()) {
    d_array.push_back( e );
    return true;
  }
  return false;
  // DO-NOT-DELETE splicer.end(objarg.EmployeeArray.appendEmployee)
}

/**
 * Find the first employee in the array that has a name matching
 * <code>name</code>.  If a match exists, the index is returned,
 * and the employee is returned in parameter <code>e</code>.
 * 
 * If no match exists, 0 is returned, and <code>e</code> is NULL.
 */
int32_t
objarg::EmployeeArray_impl::findByName_impl (
  /* in */const ::std::string& name,
  /* out */::objarg::Employee& e ) 
{
  // DO-NOT-DELETE splicer.begin(objarg.EmployeeArray.findByName)
  int i=1;
  for( d_array_t::iterator cur = d_array.begin(), end = d_array.end();
       cur != end; ++cur,++i ) { 
    if ( cur->getName() == name ) { 
      e = *cur;
      return i;
    }
  }
  e = Employee();
  return 0;
  // DO-NOT-DELETE splicer.end(objarg.EmployeeArray.findByName)
}

/**
 * Determine the maximum salary in the array. If the maximum
 * salary in the array is greater than the current salary of
 * <code>e</code>, the salary of <code>e</code> will be 
 * increased to the maximum salary in the array.  If the
 * array is empty, no change will be made to <code>e</code>.
 * 
 * This method returns <code>true</code> iff the salary of
 * <code>e</code> is modified.
 */
bool
objarg::EmployeeArray_impl::promoteToMaxSalary_impl (
  /* inout */::objarg::Employee& e ) 
{
  // DO-NOT-DELETE splicer.begin(objarg.EmployeeArray.promoteToMaxSalary)
  if (d_array.size() && e._not_nil()) {
    float max_salary = -FLT_MAX;
    for( d_array_t::iterator cur = d_array.begin(), end = d_array.end();
	 cur != end; ++cur ) { 
      max_salary = std::max( cur->getSalary(), max_salary );
    }
    if ( e.getSalary() < max_salary ) { 
      e.setSalary( max_salary );
      return true;
    }
  }
  return false;
  // DO-NOT-DELETE splicer.end(objarg.EmployeeArray.promoteToMaxSalary)
}


// DO-NOT-DELETE splicer.begin(objarg.EmployeeArray._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(objarg.EmployeeArray._misc)

