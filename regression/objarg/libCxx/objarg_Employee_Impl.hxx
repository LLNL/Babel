// 
// File:          objarg_Employee_Impl.hxx
// Symbol:        objarg.Employee-v0.5
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for objarg.Employee
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_objarg_Employee_Impl_hxx
#define included_objarg_Employee_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_objarg_Employee_IOR_h
#include "objarg_Employee_IOR.h"
#endif
#ifndef included_objarg_Employee_hxx
#include "objarg_Employee.hxx"
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


// DO-NOT-DELETE splicer.begin(objarg.Employee._hincludes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(objarg.Employee._hincludes)

namespace objarg { 

  /**
   * Symbol "objarg.Employee" (version 0.5)
   * 
   * This object type holds the basic information about an employee:
   * name, age, salary and marital status.  This object exists purely
   * to serve as a test case for sidl.  It is not intended for serious
   * use.
   */
  class Employee_impl : public virtual ::objarg::Employee 
  // DO-NOT-DELETE splicer.begin(objarg.Employee._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(objarg.Employee._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(objarg.Employee._implementation)
    int32_t d_age;
    std::string d_name;
    float d_salary;
    char d_status;
    // DO-NOT-DELETE splicer.end(objarg.Employee._implementation)

  public:
    // default constructor, used for data wrapping(required)
    Employee_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
    Employee_impl( struct objarg_Employee__object * ior ) : StubBase(ior,true) ,
      _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~Employee_impl() { _dtor(); }

    // user defined destruction
    void _dtor();

    // true if this object was created by a user newing the impl
    inline bool _isWrapped() {return _wrapped;}

    // static class initializer
    static void _load();

  public:


    /**
     * Provide the data for the employee object to hold.
     * <code>false</code> is returned when the data was unacceptable.
     * <code>true</code> means the employee object was successfully
     * initialized.
     */
    bool
    init_impl (
      /* in */const ::std::string& name,
      /* in */int32_t age,
      /* in */float salary,
      /* in */char status
    )
    ;


    /**
     * Change the name of an employee.
     */
    void
    setName_impl (
      /* in */const ::std::string& name
    )
    ;


    /**
     * Return the name of an employee.
     */
    ::std::string
    getName_impl() ;

    /**
     * Change the age of an employee.
     */
    void
    setAge_impl (
      /* in */int32_t age
    )
    ;


    /**
     * Return the age of an employee.
     */
    int32_t
    getAge_impl() ;

    /**
     * Set an employee's salary.
     */
    void
    setSalary_impl (
      /* in */float salary
    )
    ;


    /**
     * Return an employee's salary.
     */
    float
    getSalary_impl() ;

    /**
     * Set an employee's marital status.
     */
    void
    setStatus_impl (
      /* in */char status
    )
    ;


    /**
     * Return an employee's marital status.
     */
    char
    getStatus_impl() ;
  };  // end class Employee_impl

} // end namespace objarg

// DO-NOT-DELETE splicer.begin(objarg.Employee._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(objarg.Employee._hmisc)

#endif
