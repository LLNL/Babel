/*
 * File:          EmployeeArray_Impl.java
 * Symbol:        objarg.EmployeeArray-v0.5
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for objarg.EmployeeArray
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

package objarg;

import objarg.Employee;
import sidl.BaseClass;
import sidl.BaseInterface;
import sidl.ClassInfo;
import sidl.RuntimeException;

// DO-NOT-DELETE splicer.begin(objarg.EmployeeArray._imports)
// Put additional imports here...
// DO-NOT-DELETE splicer.end(objarg.EmployeeArray._imports)

/**
 * Symbol "objarg.EmployeeArray" (version 0.5)
 * 
 * This class manages a collection of employees.
 */
public class EmployeeArray_Impl extends EmployeeArray
{

  // DO-NOT-DELETE splicer.begin(objarg.EmployeeArray._data)
  // Put additional private data here...
    private java.util.Vector d_arraydata = null;
  // DO-NOT-DELETE splicer.end(objarg.EmployeeArray._data)


  static { 
  // DO-NOT-DELETE splicer.begin(objarg.EmployeeArray._load)
  // Put load function implementation here...
  // DO-NOT-DELETE splicer.end(objarg.EmployeeArray._load)

  }

  /**
   * User defined constructor
   */
  public EmployeeArray_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(objarg.EmployeeArray.EmployeeArray)
    // add construction details here
      d_arraydata = new java.util.Vector();
    // DO-NOT-DELETE splicer.end(objarg.EmployeeArray.EmployeeArray)

  }

  /**
   * Back door constructor
   */
  public EmployeeArray_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(objarg.EmployeeArray._wrap)
    // Insert-Code-Here {objarg.EmployeeArray._wrap} (_wrap)
    // DO-NOT-DELETE splicer.end(objarg.EmployeeArray._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(objarg.EmployeeArray._dtor)
    // add destruction details here
      d_arraydata = null;
    // DO-NOT-DELETE splicer.end(objarg.EmployeeArray._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(objarg.EmployeeArray.finalize)
    // Insert-Code-Here {objarg.EmployeeArray.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(objarg.EmployeeArray.finalize)

  }

  // user defined static methods: (none)

  // user defined non-static methods:
  /**
   * Return the number of employees in the employee array.
   */
  public int getLength_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(objarg.EmployeeArray.getLength)
    // insert implementation here
      return d_arraydata.size();
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
  public objarg.Employee at_Impl (
    /*in*/ int idx ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(objarg.EmployeeArray.at)
    // insert implementation here
      if(idx >= 1 && idx <= d_arraydata.size()) {
        return(objarg.Employee)d_arraydata.get(idx-1);
      }
    return null;
    // DO-NOT-DELETE splicer.end(objarg.EmployeeArray.at)

  }

  /**
   * Add an employee onto the end of the array.  It is perfectly
   * legal to add the same employee multiple times.
   * <code>true</code> is returned when the append was successful;
   * otherwise, <code>false</code> is returned to indicate
   * failure.  This method will not add a NULL employee.
   */
  public boolean appendEmployee_Impl (
    /*in*/ objarg.Employee e ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(objarg.EmployeeArray.appendEmployee)
    // insert implementation here
    if(e != null){
      d_arraydata.add(e);
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
  public int findByName_Impl (
    /*in*/ java.lang.String name,
    /*out*/ objarg.Employee.Holder e ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(objarg.EmployeeArray.findByName)
    // insert implementation here
  
    for(int i = 0; i < d_arraydata.size(); ++i) {
      if(((objarg.Employee)d_arraydata.get(i)).getName().compareTo(name) == 0) {
        e.set((objarg.Employee)d_arraydata.get(i));
        return i+1;
      }
    }
    e.set(null);
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
  public boolean promoteToMaxSalary_Impl (
    /*inout*/ objarg.Employee.Holder e ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(objarg.EmployeeArray.promoteToMaxSalary)
    // insert implementation here
      float max = 0;
      if(e != null && e.get() != null) {
	  for(int i = 0; i < d_arraydata.size(); ++i) {
	      if(((objarg.Employee)d_arraydata.get(i)).getSalary() > max) {
		  max = ((objarg.Employee)d_arraydata.get(i)).getSalary();
	      }
	  }
	  if(e.get().getSalary() < max){
	      e.get().setSalary(max);
	      return true;
	  }      
      }
    return false;
    // DO-NOT-DELETE splicer.end(objarg.EmployeeArray.promoteToMaxSalary)

  }


  // DO-NOT-DELETE splicer.begin(objarg.EmployeeArray._misc)
  // Put miscellaneous code here
  // DO-NOT-DELETE splicer.end(objarg.EmployeeArray._misc)

} // end class EmployeeArray

