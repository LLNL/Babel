// 
// File:          objarg_EmployeeArray_Impl.hxx
// Symbol:        objarg.EmployeeArray-v0.5
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for objarg.EmployeeArray
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_objarg_EmployeeArray_Impl_hxx
#define included_objarg_EmployeeArray_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_objarg_EmployeeArray_IOR_h
#include "objarg_EmployeeArray_IOR.h"
#endif
#ifndef included_objarg_Employee_hxx
#include "objarg_Employee.hxx"
#endif
#ifndef included_objarg_EmployeeArray_hxx
#include "objarg_EmployeeArray.hxx"
#endif
#ifndef included_sidl_BaseClass_hxx
#include "sidl_BaseClass.hxx"
#endif
#ifndef included_sidl_BaseInterface_hxx
#include "sidl_BaseInterface.hxx"
#endif
#ifndef included_sidl_ClassInfo_hxx
#include "sidl_ClassInfo.hxx"
#endif


// DO-NOT-DELETE splicer.begin(objarg.EmployeeArray._hincludes)
#ifndef included_objarg_Employee_hh
#include "objarg_Employee.hxx"
#endif
#include <vector>
// DO-NOT-DELETE splicer.end(objarg.EmployeeArray._hincludes)

namespace objarg { 

  /**
   * Symbol "objarg.EmployeeArray" (version 0.5)
   * 
   * This class manages a collection of employees.
   */
  class EmployeeArray_impl : public virtual ::objarg::EmployeeArray 
  // DO-NOT-DELETE splicer.begin(objarg.EmployeeArray._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(objarg.EmployeeArray._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(objarg.EmployeeArray._implementation)
    typedef std::vector<objarg::Employee> d_array_t;
    d_array_t d_array;
    // DO-NOT-DELETE splicer.end(objarg.EmployeeArray._implementation)

  public:
    // default constructor, used for data wrapping(required)
    EmployeeArray_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
    EmployeeArray_impl( struct objarg_EmployeeArray__object * ior ) : StubBase(
      ior,true) , _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~EmployeeArray_impl() { _dtor(); }

    // user defined destruction
    void _dtor();

    // true if this object was created by a user newing the impl
    inline bool _isWrapped() {return _wrapped;}

    // static class initializer
    static void _load();

  public:


    /**
     * Return the number of employees in the employee array.
     */
    int32_t
    getLength_impl() ;

    /**
     * Return the employee in position <code>idx</code> where
     * <code>idx</code> ranges from 1 to the length of the array.
     * If <code>idx</code> is outside the range of the array (i.e.
     * less than or equal to zero or greater than the current number
     * of elements in the array), this method returns a NULL
     * employee object.
     */
    ::objarg::Employee
    at_impl (
      /* in */int32_t idx
    )
    ;


    /**
     * Add an employee onto the end of the array.  It is perfectly
     * legal to add the same employee multiple times.
     * <code>true</code> is returned when the append was successful;
     * otherwise, <code>false</code> is returned to indicate
     * failure.  This method will not add a NULL employee.
     */
    bool
    appendEmployee_impl (
      /* in */::objarg::Employee& e
    )
    ;


    /**
     * Find the first employee in the array that has a name matching
     * <code>name</code>.  If a match exists, the index is returned,
     * and the employee is returned in parameter <code>e</code>.
     * 
     * If no match exists, 0 is returned, and <code>e</code> is NULL.
     */
    int32_t
    findByName_impl (
      /* in */const ::std::string& name,
      /* out */::objarg::Employee& e
    )
    ;


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
    promoteToMaxSalary_impl (
      /* inout */::objarg::Employee& e
    )
    ;

  };  // end class EmployeeArray_impl

} // end namespace objarg

// DO-NOT-DELETE splicer.begin(objarg.EmployeeArray._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(objarg.EmployeeArray._hmisc)

#endif
