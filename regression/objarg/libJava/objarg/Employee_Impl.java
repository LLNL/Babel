/*
 * File:          Employee_Impl.java
 * Symbol:        objarg.Employee-v0.5
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for objarg.Employee
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

package objarg;

import sidl.BaseClass;
import sidl.BaseInterface;
import sidl.ClassInfo;
import sidl.RuntimeException;

// DO-NOT-DELETE splicer.begin(objarg.Employee._imports)
// Put additional imports here...
// DO-NOT-DELETE splicer.end(objarg.Employee._imports)

/**
 * Symbol "objarg.Employee" (version 0.5)
 * 
 * This object type holds the basic information about an employee:
 * name, age, salary and marital status.  This object exists purely
 * to serve as a test case for sidl.  It is not intended for serious
 * use.
 */
public class Employee_Impl extends Employee
{

  // DO-NOT-DELETE splicer.begin(objarg.Employee._data)
    private String d_name = "";
    private int d_age = 0;
    private float d_salary = 0;
    private char d_status = '\0';
  static public int i = 0;
  // DO-NOT-DELETE splicer.end(objarg.Employee._data)


  static { 
  // DO-NOT-DELETE splicer.begin(objarg.Employee._load)
    ++i;
  // DO-NOT-DELETE splicer.end(objarg.Employee._load)

  }

  /**
   * User defined constructor
   */
  public Employee_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(objarg.Employee.Employee)
    // add construction details here
    // DO-NOT-DELETE splicer.end(objarg.Employee.Employee)

  }

  /**
   * Back door constructor
   */
  public Employee_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(objarg.Employee._wrap)
    // Insert-Code-Here {objarg.Employee._wrap} (_wrap)
    // DO-NOT-DELETE splicer.end(objarg.Employee._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(objarg.Employee._dtor)
    // add destruction details here
    // DO-NOT-DELETE splicer.end(objarg.Employee._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(objarg.Employee.finalize)
    // Insert-Code-Here {objarg.Employee.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(objarg.Employee.finalize)

  }

  // user defined static methods: (none)

  // user defined non-static methods:
  /**
   * Provide the data for the employee object to hold.
   * <code>false</code> is returned when the data was unacceptable.
   * <code>true</code> means the employee object was successfully
   * initialized.
   */
  public boolean init_Impl (
    /*in*/ java.lang.String name,
    /*in*/ int age,
    /*in*/ float salary,
    /*in*/ char status ) 
    throws sidl.RuntimeException.Wrapper
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
  public void setName_Impl (
    /*in*/ java.lang.String name ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(objarg.Employee.setName)
      d_name = name;
      return ;
    // DO-NOT-DELETE splicer.end(objarg.Employee.setName)

  }

  /**
   * Return the name of an employee.
   */
  public java.lang.String getName_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(objarg.Employee.getName)
     return d_name;
    // DO-NOT-DELETE splicer.end(objarg.Employee.getName)

  }

  /**
   * Change the age of an employee.
   */
  public void setAge_Impl (
    /*in*/ int age ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(objarg.Employee.setAge)
      d_age = age;  
      return ;
    // DO-NOT-DELETE splicer.end(objarg.Employee.setAge)

  }

  /**
   * Return the age of an employee.
   */
  public int getAge_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(objarg.Employee.getAge)
    // insert implementation here
    return d_age;
    // DO-NOT-DELETE splicer.end(objarg.Employee.getAge)

  }

  /**
   * Set an employee's salary.
   */
  public void setSalary_Impl (
    /*in*/ float salary ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(objarg.Employee.setSalary)
    // insert implementation here
       d_salary = salary; 
      return ;
    // DO-NOT-DELETE splicer.end(objarg.Employee.setSalary)

  }

  /**
   * Return an employee's salary.
   */
  public float getSalary_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(objarg.Employee.getSalary)
    // insert implementation here
      
    return d_salary;
    // DO-NOT-DELETE splicer.end(objarg.Employee.getSalary)

  }

  /**
   * Set an employee's marital status.
   */
  public void setStatus_Impl (
    /*in*/ char status ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(objarg.Employee.setStatus)
    // insert implementation here
      d_status = status;
    return ;
    // DO-NOT-DELETE splicer.end(objarg.Employee.setStatus)

  }

  /**
   * Return an employee's marital status.
   */
  public char getStatus_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(objarg.Employee.getStatus)
    // insert implementation here
    return d_status;
    // DO-NOT-DELETE splicer.end(objarg.Employee.getStatus)

  }


  // DO-NOT-DELETE splicer.begin(objarg.Employee._misc)
 
  // DO-NOT-DELETE splicer.end(objarg.Employee._misc)

} // end class Employee

