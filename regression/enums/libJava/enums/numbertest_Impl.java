/*
 * File:          numbertest_Impl.java
 * Symbol:        enums.numbertest-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for enums.numbertest
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

package enums;

import enums.number;
import sidl.BaseClass;
import sidl.BaseInterface;
import sidl.ClassInfo;
import sidl.RuntimeException;

// DO-NOT-DELETE splicer.begin(enums.numbertest._imports)
// Put additional imports here...
// DO-NOT-DELETE splicer.end(enums.numbertest._imports)

/**
 * Symbol "enums.numbertest" (version 1.0)
 */
public class numbertest_Impl extends numbertest
{

  // DO-NOT-DELETE splicer.begin(enums.numbertest._data)
  // Put additional private data here...
  // DO-NOT-DELETE splicer.end(enums.numbertest._data)


  static { 
  // DO-NOT-DELETE splicer.begin(enums.numbertest._load)
  // Put load function implementation here...
  // DO-NOT-DELETE splicer.end(enums.numbertest._load)

  }

  /**
   * User defined constructor
   */
  public numbertest_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(enums.numbertest.numbertest)
    // add construction details here
    // DO-NOT-DELETE splicer.end(enums.numbertest.numbertest)

  }

  /**
   * Back door constructor
   */
  public numbertest_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(enums.numbertest._wrap)
    // Insert-Code-Here {enums.numbertest._wrap} (_wrap)
    // DO-NOT-DELETE splicer.end(enums.numbertest._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(enums.numbertest._dtor)
    // add destruction details here
    // DO-NOT-DELETE splicer.end(enums.numbertest._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(enums.numbertest.finalize)
    // Insert-Code-Here {enums.numbertest.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(enums.numbertest.finalize)

  }

  // user defined static methods: (none)

  // user defined non-static methods:
  /**
   * Method:  returnback[]
   */
  public long returnback_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(enums.numbertest.returnback)
    // insert implementation here
    return enums.number.notOne;
    // DO-NOT-DELETE splicer.end(enums.numbertest.returnback)

  }

  /**
   * Method:  passin[]
   */
  public boolean passin_Impl (
    /*in*/ long n ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(enums.numbertest.passin)
    // insert implementation here
    return ( n == enums.number.notZero );
    // DO-NOT-DELETE splicer.end(enums.numbertest.passin)

  }

  /**
   * Method:  passout[]
   */
  public boolean passout_Impl (
    /*out*/ sidl.Enum.Holder n ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(enums.numbertest.passout)
    // insert implementation here
    n.set(enums.number.negOne);
    return true;

    // DO-NOT-DELETE splicer.end(enums.numbertest.passout)

  }

  /**
   * Method:  passinout[]
   */
  public boolean passinout_Impl (
    /*inout*/ sidl.Enum.Holder n ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(enums.numbertest.passinout)
    // insert implementation here
    final long value = n.get();
    if (value == enums.number.zero) {
      n.set(enums.number.notZero);
    } else if (value == enums.number.one) {
      n.set(enums.number.notOne);
    } else if (value == enums.number.negOne) {
      n.set(enums.number.notNeg);
    } else if (value == enums.number.notZero) {
      n.set(enums.number.zero);
    } else if (value == enums.number.notOne) {
      n.set(enums.number.one);
    } else if (value == enums.number.notNeg) {
      n.set(enums.number.negOne);
    } else {
      return false;
    }
    return true;
    // DO-NOT-DELETE splicer.end(enums.numbertest.passinout)

  }

  /**
   * Method:  passeverywhere[]
   */
  public long passeverywhere_Impl (
    /*in*/ long n1,
    /*out*/ sidl.Enum.Holder n2,
    /*inout*/ sidl.Enum.Holder n3 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(enums.numbertest.passeverywhere)
    // insert implementation here
    final long value = n3.get();
    n2.set(enums.number.negOne);
    if (value == enums.number.zero) {
      n3.set(enums.number.notZero);
    } else if (value == enums.number.one) {
      n3.set(enums.number.notOne);
    } else if (value == enums.number.negOne) {
      n3.set(enums.number.notNeg);
    } else if (value == enums.number.notZero) {
      n3.set(enums.number.zero);
    } else if (value == enums.number.notOne) {
      n3.set(enums.number.one);
    } else if (value == enums.number.notNeg) {
      n3.set(enums.number.negOne);
    } else {
      return 0;
    }
    return ( n1 == enums.number.notZero ) ? enums.number.notOne : 0;
    // DO-NOT-DELETE splicer.end(enums.numbertest.passeverywhere)

  }


  // DO-NOT-DELETE splicer.begin(enums.numbertest._misc)
  // Put miscellaneous code here
  // DO-NOT-DELETE splicer.end(enums.numbertest._misc)

} // end class numbertest

