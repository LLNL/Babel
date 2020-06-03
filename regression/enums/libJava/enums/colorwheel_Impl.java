/*
 * File:          colorwheel_Impl.java
 * Symbol:        enums.colorwheel-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for enums.colorwheel
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

package enums;

import enums.color;
import sidl.BaseClass;
import sidl.BaseInterface;
import sidl.ClassInfo;
import sidl.RuntimeException;

// DO-NOT-DELETE splicer.begin(enums.colorwheel._imports)
// Put additional imports here...
// DO-NOT-DELETE splicer.end(enums.colorwheel._imports)

/**
 * Symbol "enums.colorwheel" (version 1.0)
 */
public class colorwheel_Impl extends colorwheel
{

  // DO-NOT-DELETE splicer.begin(enums.colorwheel._data)
  // Put additional private data here...
  // DO-NOT-DELETE splicer.end(enums.colorwheel._data)


  static { 
  // DO-NOT-DELETE splicer.begin(enums.colorwheel._load)
  // Put load function implementation here...
  // DO-NOT-DELETE splicer.end(enums.colorwheel._load)

  }

  /**
   * User defined constructor
   */
  public colorwheel_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(enums.colorwheel.colorwheel)
    // add construction details here
    // DO-NOT-DELETE splicer.end(enums.colorwheel.colorwheel)

  }

  /**
   * Back door constructor
   */
  public colorwheel_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(enums.colorwheel._wrap)
    // Insert-Code-Here {enums.colorwheel._wrap} (_wrap)
    // DO-NOT-DELETE splicer.end(enums.colorwheel._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(enums.colorwheel._dtor)
    // add destruction details here
    // DO-NOT-DELETE splicer.end(enums.colorwheel._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(enums.colorwheel.finalize)
    // Insert-Code-Here {enums.colorwheel.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(enums.colorwheel.finalize)

  }

  // user defined static methods: (none)

  // user defined non-static methods:
  /**
   * Method:  returnback[]
   */
  public long returnback_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(enums.colorwheel.returnback)
    // insert implementation here
    return enums.color.violet;
    // DO-NOT-DELETE splicer.end(enums.colorwheel.returnback)

  }

  /**
   * Method:  passin[]
   */
  public boolean passin_Impl (
    /*in*/ long c ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(enums.colorwheel.passin)
    // insert implementation here
    return (c == enums.color.blue);
    // DO-NOT-DELETE splicer.end(enums.colorwheel.passin)

  }

  /**
   * Method:  passout[]
   */
  public boolean passout_Impl (
    /*out*/ sidl.Enum.Holder c ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(enums.colorwheel.passout)
    // insert implementation here
    c.set(enums.color.violet);
    return true;
    // DO-NOT-DELETE splicer.end(enums.colorwheel.passout)

  }

  /**
   * Method:  passinout[]
   */
  public boolean passinout_Impl (
    /*inout*/ sidl.Enum.Holder c ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(enums.colorwheel.passinout)
    // insert implementation here
    final long value = c.get();
    if (value == enums.color.red) {
      c.set(enums.color.green); 
    } else if (value == enums.color.orange) {
      c.set(enums.color.blue);
    } else if (value == enums.color.yellow) {
      c.set(enums.color.violet);
    } else if (value == enums.color.green) {
      c.set(enums.color.red);
    } else if (value == enums.color.blue) {
      c.set(enums.color.orange);
    } else if (value == enums.color.violet) {
      c.set(enums.color.yellow);
    } else {
      return false;
    }
    return true;
    // DO-NOT-DELETE splicer.end(enums.colorwheel.passinout)

  }

  /**
   * Method:  passeverywhere[]
   */
  public long passeverywhere_Impl (
    /*in*/ long c1,
    /*out*/ sidl.Enum.Holder c2,
    /*inout*/ sidl.Enum.Holder c3 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(enums.colorwheel.passeverywhere)
    // insert implementation here
    final long value = c3.get();
    c2.set(enums.color.violet);
    if (value == enums.color.red) {
      c3.set(enums.color.green); 
    } else if (value == enums.color.orange) {
      c3.set(enums.color.blue);
    } else if (value == enums.color.yellow) {
      c3.set(enums.color.violet);
    } else if (value == enums.color.green) {
      c3.set(enums.color.red);
    } else if (value == enums.color.blue) {
      c3.set(enums.color.orange);
    } else if (value == enums.color.violet) {
      c3.set(enums.color.yellow);
    } else {
      return 0;
    }
    return ( c1 == enums.color.blue ) ? enums.color.violet : 0;
    // DO-NOT-DELETE splicer.end(enums.colorwheel.passeverywhere)

  }


  // DO-NOT-DELETE splicer.begin(enums.colorwheel._misc)
  // Put miscellaneous code here
  // DO-NOT-DELETE splicer.end(enums.colorwheel._misc)

} // end class colorwheel

