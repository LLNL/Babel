/*
 * File:          ParentTest_Impl.java
 * Symbol:        Overload.ParentTest-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Overload.ParentTest
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

package Overload;

import sidl.BaseClass;
import sidl.BaseInterface;
import sidl.ClassInfo;
import sidl.RuntimeException;

// DO-NOT-DELETE splicer.begin(Overload.ParentTest._imports)
// Put additional imports here...
// DO-NOT-DELETE splicer.end(Overload.ParentTest._imports)

/**
 * Symbol "Overload.ParentTest" (version 1.0)
 * 
 * This class is used as the work-horse, returning the value passed
 * in.
 */
public class ParentTest_Impl extends ParentTest
{

  // DO-NOT-DELETE splicer.begin(Overload.ParentTest._data)
  // Put additional private data here...
  // DO-NOT-DELETE splicer.end(Overload.ParentTest._data)


  static { 
  // DO-NOT-DELETE splicer.begin(Overload.ParentTest._load)
  // Put load function implementation here...
  // DO-NOT-DELETE splicer.end(Overload.ParentTest._load)

  }

  /**
   * User defined constructor
   */
  public ParentTest_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(Overload.ParentTest.ParentTest)
    // add construction details here
    // DO-NOT-DELETE splicer.end(Overload.ParentTest.ParentTest)

  }

  /**
   * Back door constructor
   */
  public ParentTest_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(Overload.ParentTest._wrap)
    // Insert-Code-Here {Overload.ParentTest._wrap} (_wrap)
    // DO-NOT-DELETE splicer.end(Overload.ParentTest._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Overload.ParentTest._dtor)
    // add destruction details here
    // DO-NOT-DELETE splicer.end(Overload.ParentTest._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Overload.ParentTest.finalize)
    // Insert-Code-Here {Overload.ParentTest.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(Overload.ParentTest.finalize)

  }

  // user defined static methods: (none)

  // user defined non-static methods:
  /**
   * Method:  getValue[]
   */
  public int getValue_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValue)
    // insert implementation here
    return 1;
    // DO-NOT-DELETE splicer.end(Overload.ParentTest.getValue)

  }

  /**
   * Method:  getValue[Int]
   */
  public int getValueInt_Impl (
    /*in*/ int v ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueInt)
    // insert implementation here
    return v;
    // DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueInt)

  }

  /**
   * Method:  getValue[Bool]
   */
  public boolean getValueBool_Impl (
    /*in*/ boolean v ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueBool)
    // insert implementation here
    return v;
    // DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueBool)

  }

  /**
   * Method:  getValue[Double]
   */
  public double getValueDouble_Impl (
    /*in*/ double v ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueDouble)
    // insert implementation here
    return v;
    // DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueDouble)

  }

  /**
   * Method:  getValue[Dcomplex]
   */
  public sidl.DoubleComplex getValueDcomplex_Impl (
    /*in*/ sidl.DoubleComplex v ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueDcomplex)
    // insert implementation here
    return v;
    // DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueDcomplex)

  }

  /**
   * Method:  getValue[Float]
   */
  public float getValueFloat_Impl (
    /*in*/ float v ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueFloat)
    // insert implementation here
    return v;
    // DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueFloat)

  }

  /**
   * Method:  getValue[Fcomplex]
   */
  public sidl.FloatComplex getValueFcomplex_Impl (
    /*in*/ sidl.FloatComplex v ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueFcomplex)
    // insert implementation here
    return v;
    // DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueFcomplex)

  }

  /**
   * Method:  getValue[String]
   */
  public java.lang.String getValueString_Impl (
    /*in*/ java.lang.String v ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueString)
    // insert implementation here
    return v;
    // DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueString)

  }


  // DO-NOT-DELETE splicer.begin(Overload.ParentTest._misc)
  // Put miscellaneous code here
  // DO-NOT-DELETE splicer.end(Overload.ParentTest._misc)

} // end class ParentTest

