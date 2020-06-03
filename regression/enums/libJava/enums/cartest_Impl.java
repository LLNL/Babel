/*
 * File:          cartest_Impl.java
 * Symbol:        enums.cartest-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for enums.cartest
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

package enums;

import enums.car;
import sidl.BaseClass;
import sidl.BaseInterface;
import sidl.ClassInfo;
import sidl.RuntimeException;

// DO-NOT-DELETE splicer.begin(enums.cartest._imports)
// Put additional imports here...
// DO-NOT-DELETE splicer.end(enums.cartest._imports)

/**
 * Symbol "enums.cartest" (version 1.0)
 */
public class cartest_Impl extends cartest
{

  // DO-NOT-DELETE splicer.begin(enums.cartest._data)
  // Put additional private data here...
  // DO-NOT-DELETE splicer.end(enums.cartest._data)


  static { 
  // DO-NOT-DELETE splicer.begin(enums.cartest._load)
  // Put load function implementation here...
  // DO-NOT-DELETE splicer.end(enums.cartest._load)

  }

  /**
   * User defined constructor
   */
  public cartest_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(enums.cartest.cartest)
    // add construction details here
    // DO-NOT-DELETE splicer.end(enums.cartest.cartest)

  }

  /**
   * Back door constructor
   */
  public cartest_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(enums.cartest._wrap)
    // Insert-Code-Here {enums.cartest._wrap} (_wrap)
    // DO-NOT-DELETE splicer.end(enums.cartest._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(enums.cartest._dtor)
    // add destruction details here
    // DO-NOT-DELETE splicer.end(enums.cartest._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(enums.cartest.finalize)
    // Insert-Code-Here {enums.cartest.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(enums.cartest.finalize)

  }

  // user defined static methods: (none)

  // user defined non-static methods:
  /**
   * Method:  returnback[]
   */
  public long returnback_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(enums.cartest.returnback)
    // insert implementation here
    return enums.car.porsche;
    // DO-NOT-DELETE splicer.end(enums.cartest.returnback)

  }

  /**
   * Method:  passin[]
   */
  public boolean passin_Impl (
    /*in*/ long c ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(enums.cartest.passin)
    // insert implementation here
    return (c == enums.car.mercedes);
    // DO-NOT-DELETE splicer.end(enums.cartest.passin)

  }

  /**
   * Method:  passout[]
   */
  public boolean passout_Impl (
    /*out*/ sidl.Enum.Holder c ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(enums.cartest.passout)
    // insert implementation here
    c.set(enums.car.ford);
    return true;
    // DO-NOT-DELETE splicer.end(enums.cartest.passout)

  }

  /**
   * Method:  passinout[]
   */
  public boolean passinout_Impl (
    /*inout*/ sidl.Enum.Holder c ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(enums.cartest.passinout)
    // insert implementation here
    final long value = c.get();
    if (value == enums.car.ford) {
      c.set(enums.car.porsche); 
      return true;
    }
    if (value == enums.car.porsche) {
      c.set(enums.car.mercedes); 
      return true;
    }
    return (value == enums.car.mercedes);
    // DO-NOT-DELETE splicer.end(enums.cartest.passinout)

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
    // DO-NOT-DELETE splicer.begin(enums.cartest.passeverywhere)
    // insert implementation here
    final long value = c3.get();
    c2.set(enums.car.ford);
    if (value == enums.car.ford) {
      c3.set(enums.car.porsche); 
    } else if (value == enums.car.porsche) {
      c3.set(enums.car.mercedes); 
    } else if (value != enums.car.mercedes) {
      return 0;
    }
    return ( c1 == enums.car.mercedes ) ? enums.car.porsche : 0;
    // DO-NOT-DELETE splicer.end(enums.cartest.passeverywhere)

  }

  /**
   * All incoming/outgoing arrays should be [ ford, mercedes, porsche ]
   * in that order.
   */
  public sidl.Enum.Array1 passarray_Impl (
    /*in*/ sidl.Enum.Array1 c1,
    /*out*/ sidl.Enum.Array1.Holder c2,
    /*inout*/ sidl.Enum.Array1.Holder c3 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(enums.cartest.passarray)
    long [] vals = { enums.car.ford, enums.car.mercedes, enums.car.porsche };
    sidl.Enum.Array1 retval = null, build = null;
    boolean failed = false;
    c2.set(null);
    if ((c1 != null) && (c3 != null) && (c3.get() != null)) {
      if ((c1.length() == 3) && (c3.get().length() == 3)) {
        retval = new sidl.Enum.Array1(0, 2, false);
        build = new sidl.Enum.Array1(0, 2, false);
        for(int i = 0; i < 3; ++i ) {
          retval.set(i, vals[i]);
          build.set(i, vals[i]);
          failed = failed || (c1.get(i+c1.lower(0)) != vals[i]) ||
            (c3.get().get(i+c3.get().lower(0)) != vals[i]);
        }
      }
      if (failed) {
        build = null;
        retval = null;
      }
      else {
        c2.set(build);
      }
    }
    return retval;
    // DO-NOT-DELETE splicer.end(enums.cartest.passarray)

  }


  // DO-NOT-DELETE splicer.begin(enums.cartest._misc)
  // Put miscellaneous code here
  // DO-NOT-DELETE splicer.end(enums.cartest._misc)

} // end class cartest

