// 
// File:          objarg_Employee_Impl.cxx
// Symbol:        objarg.Employee-v0.5
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for objarg.Employee
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "objarg_Employee_Impl.hxx"

// 
// Includes for all method dependencies.
// 
#ifndef included_sidl_BaseInterface_hxx
#include "sidl_BaseInterface.hxx"
#endif
#ifndef included_sidl_ClassInfo_hxx
#include "sidl_ClassInfo.hxx"
#endif
#ifndef included_sidl_NotImplementedException_hxx
#include "sidl_NotImplementedException.hxx"
#endif
// DO-NOT-DELETE splicer.begin(objarg.Employee._includes)
// Put additional includes or other arbitrary code here...
using namespace objarg;
// DO-NOT-DELETE splicer.end(objarg.Employee._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
objarg::Employee_impl::Employee_impl() : StubBase(reinterpret_cast< void*>(
  ::objarg::Employee::_wrapObj(reinterpret_cast< void*>(this))),false) , 
  _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(objarg.Employee._ctor2)
  // Insert-Code-Here {objarg.Employee._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(objarg.Employee._ctor2)
}

// user defined constructor
void objarg::Employee_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(objarg.Employee._ctor)
  // add construction details here
  // DO-NOT-DELETE splicer.end(objarg.Employee._ctor)
}

// user defined destructor
void objarg::Employee_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(objarg.Employee._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(objarg.Employee._dtor)
}

// static class initializer
void objarg::Employee_impl::_load() {
  // DO-NOT-DELETE splicer.begin(objarg.Employee._load)
  // guaranteed to be called at most once before any other method in this class
  // DO-NOT-DELETE splicer.end(objarg.Employee._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Provide the data for the employee object to hold.
 * <code>false</code> is returned when the data was unacceptable.
 * <code>true</code> means the employee object was successfully
 * initialized.
 */
bool
objarg::Employee_impl::init_impl (
  /* in */const ::std::string& name,
  /* in */int32_t age,
  /* in */float salary,
  /* in */char status ) 
{
  // DO-NOT-DELETE splicer.begin(objarg.Employee.init)
  d_name = name;
  d_age = age;
  d_salary = salary;
  d_status = status;
  return true;
  // DO-NOT-DELETE splicer.end(objarg.Employee.init)
}

/**
 * Change the name of an employee.
 */
void
objarg::Employee_impl::setName_impl (
  /* in */const ::std::string& name ) 
{
  // DO-NOT-DELETE splicer.begin(objarg.Employee.setName)
  d_name = name;
  // DO-NOT-DELETE splicer.end(objarg.Employee.setName)
}

/**
 * Return the name of an employee.
 */
::std::string
objarg::Employee_impl::getName_impl () 

{
  // DO-NOT-DELETE splicer.begin(objarg.Employee.getName)
  return d_name;
  // DO-NOT-DELETE splicer.end(objarg.Employee.getName)
}

/**
 * Change the age of an employee.
 */
void
objarg::Employee_impl::setAge_impl (
  /* in */int32_t age ) 
{
  // DO-NOT-DELETE splicer.begin(objarg.Employee.setAge)
  d_age = age;
  // DO-NOT-DELETE splicer.end(objarg.Employee.setAge)
}

/**
 * Return the age of an employee.
 */
int32_t
objarg::Employee_impl::getAge_impl () 

{
  // DO-NOT-DELETE splicer.begin(objarg.Employee.getAge)
  return d_age;
  // DO-NOT-DELETE splicer.end(objarg.Employee.getAge)
}

/**
 * Set an employee's salary.
 */
void
objarg::Employee_impl::setSalary_impl (
  /* in */float salary ) 
{
  // DO-NOT-DELETE splicer.begin(objarg.Employee.setSalary)
  d_salary = salary;
  // DO-NOT-DELETE splicer.end(objarg.Employee.setSalary)
}

/**
 * Return an employee's salary.
 */
float
objarg::Employee_impl::getSalary_impl () 

{
  // DO-NOT-DELETE splicer.begin(objarg.Employee.getSalary)
  return d_salary;
  // DO-NOT-DELETE splicer.end(objarg.Employee.getSalary)
}

/**
 * Set an employee's marital status.
 */
void
objarg::Employee_impl::setStatus_impl (
  /* in */char status ) 
{
  // DO-NOT-DELETE splicer.begin(objarg.Employee.setStatus)
  d_status = status;
  // DO-NOT-DELETE splicer.end(objarg.Employee.setStatus)
}

/**
 * Return an employee's marital status.
 */
char
objarg::Employee_impl::getStatus_impl () 

{
  // DO-NOT-DELETE splicer.begin(objarg.Employee.getStatus)
  return d_status;
  // DO-NOT-DELETE splicer.end(objarg.Employee.getStatus)
}


// DO-NOT-DELETE splicer.begin(objarg.Employee._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(objarg.Employee._misc)

