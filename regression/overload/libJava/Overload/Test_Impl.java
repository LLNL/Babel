/*
 * File:          Test_Impl.java
 * Symbol:        Overload.Test-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Overload.Test
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

package Overload;

import Overload.AClass;
import Overload.AnException;
import Overload.BClass;
import Overload.ParentTest;
import sidl.BaseClass;
import sidl.BaseInterface;
import sidl.ClassInfo;
import sidl.RuntimeException;

// DO-NOT-DELETE splicer.begin(Overload.Test._imports)
// Put additional imports here...
// DO-NOT-DELETE splicer.end(Overload.Test._imports)

/**
 * Symbol "Overload.Test" (version 1.0)
 * 
 * This class is used as the work-horse, returning the value passed
 * in.
 */
public class Test_Impl extends Test
{

  // DO-NOT-DELETE splicer.begin(Overload.Test._data)
  // Put additional private data here...
  // DO-NOT-DELETE splicer.end(Overload.Test._data)


  static { 
  // DO-NOT-DELETE splicer.begin(Overload.Test._load)
  // Put load function implementation here...
  // DO-NOT-DELETE splicer.end(Overload.Test._load)

  }

  /**
   * User defined constructor
   */
  public Test_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(Overload.Test.Test)
    // add construction details here
    // DO-NOT-DELETE splicer.end(Overload.Test.Test)

  }

  /**
   * Back door constructor
   */
  public Test_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(Overload.Test._wrap)
    // Insert-Code-Here {Overload.Test._wrap} (_wrap)
    // DO-NOT-DELETE splicer.end(Overload.Test._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Overload.Test._dtor)
    // add destruction details here
    // DO-NOT-DELETE splicer.end(Overload.Test._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Overload.Test.finalize)
    // Insert-Code-Here {Overload.Test.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(Overload.Test.finalize)

  }

  // user defined static methods: (none)

  // user defined non-static methods:
  /**
   * Method:  getValue[IntDouble]
   */
  public double getValueIntDouble_Impl (
    /*in*/ int a,
    /*in*/ double b ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Overload.Test.getValueIntDouble)
    // insert implementation here
    return ((double)a + b);
    // DO-NOT-DELETE splicer.end(Overload.Test.getValueIntDouble)

  }

  /**
   * Method:  getValue[DoubleInt]
   */
  public double getValueDoubleInt_Impl (
    /*in*/ double a,
    /*in*/ int b ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Overload.Test.getValueDoubleInt)
    // insert implementation here
    return (a + (double)b);
    // DO-NOT-DELETE splicer.end(Overload.Test.getValueDoubleInt)

  }

  /**
   * Method:  getValue[IntDoubleFloat]
   */
  public double getValueIntDoubleFloat_Impl (
    /*in*/ int a,
    /*in*/ double b,
    /*in*/ float c ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Overload.Test.getValueIntDoubleFloat)
    // insert implementation here
    return ((double)a + b + (double)c);
    // DO-NOT-DELETE splicer.end(Overload.Test.getValueIntDoubleFloat)

  }

  /**
   * Method:  getValue[DoubleIntFloat]
   */
  public double getValueDoubleIntFloat_Impl (
    /*in*/ double a,
    /*in*/ int b,
    /*in*/ float c ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Overload.Test.getValueDoubleIntFloat)
    // insert implementation here
    return (a + (double)b + (double)c);
    // DO-NOT-DELETE splicer.end(Overload.Test.getValueDoubleIntFloat)

  }

  /**
   * Method:  getValue[IntFloatDouble]
   */
  public double getValueIntFloatDouble_Impl (
    /*in*/ int a,
    /*in*/ float b,
    /*in*/ double c ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Overload.Test.getValueIntFloatDouble)
    // insert implementation here
    return ((double)a + (double)b + c);
    // DO-NOT-DELETE splicer.end(Overload.Test.getValueIntFloatDouble)

  }

  /**
   * Method:  getValue[DoubleFloatInt]
   */
  public double getValueDoubleFloatInt_Impl (
    /*in*/ double a,
    /*in*/ float b,
    /*in*/ int c ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Overload.Test.getValueDoubleFloatInt)
    // insert implementation here
    return (a + (double)b + (double)c);
    // DO-NOT-DELETE splicer.end(Overload.Test.getValueDoubleFloatInt)

  }

  /**
   * Method:  getValue[FloatIntDouble]
   */
  public double getValueFloatIntDouble_Impl (
    /*in*/ float a,
    /*in*/ int b,
    /*in*/ double c ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Overload.Test.getValueFloatIntDouble)
    // insert implementation here
    return ((double)a + (double)b + c);
    // DO-NOT-DELETE splicer.end(Overload.Test.getValueFloatIntDouble)

  }

  /**
   * Method:  getValue[FloatDoubleInt]
   */
  public double getValueFloatDoubleInt_Impl (
    /*in*/ float a,
    /*in*/ double b,
    /*in*/ int c ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Overload.Test.getValueFloatDoubleInt)
    // insert implementation here
    return ((double)a + b + (double)c);
    // DO-NOT-DELETE splicer.end(Overload.Test.getValueFloatDoubleInt)

  }

  /**
   * Method:  getValue[Exception]
   */
  public java.lang.String getValueException_Impl (
    /*in*/ Overload.AnException v ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Overload.Test.getValueException)
    // insert implementation here
    return v.getNote();
    // DO-NOT-DELETE splicer.end(Overload.Test.getValueException)

  }

  /**
   * Method:  getValue[AClass]
   */
  public int getValueAClass_Impl (
    /*in*/ Overload.AClass v ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Overload.Test.getValueAClass)
    // insert implementation here
    return v.getValue();
    // DO-NOT-DELETE splicer.end(Overload.Test.getValueAClass)

  }

  /**
   * Method:  getValue[BClass]
   */
  public int getValueBClass_Impl (
    /*in*/ Overload.BClass v ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Overload.Test.getValueBClass)
    // insert implementation here
    return v.getValue();
    // DO-NOT-DELETE splicer.end(Overload.Test.getValueBClass)

  }


  // DO-NOT-DELETE splicer.begin(Overload.Test._misc)
  // Put miscellaneous code here
  // DO-NOT-DELETE splicer.end(Overload.Test._misc)

} // end class Test

