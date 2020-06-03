/*
 * File:          Fib_Impl.java
 * Symbol:        Exceptions.Fib-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Exceptions.Fib
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

package Exceptions;

import Exceptions.FibException;
import Exceptions.NegativeValueException;
import sidl.BaseClass;
import sidl.BaseInterface;
import sidl.ClassInfo;
import sidl.MemAllocException;
import sidl.RuntimeException;
import sidl.SIDLException;

// DO-NOT-DELETE splicer.begin(Exceptions.Fib._imports)
// Put additional imports here...
// DO-NOT-DELETE splicer.end(Exceptions.Fib._imports)

/**
 * Symbol "Exceptions.Fib" (version 1.0)
 * 
 * This class holds the method <code>getFib</code> that generates the
 * requested Fibonacci numbers.
 */
public class Fib_Impl extends Fib
{

  // DO-NOT-DELETE splicer.begin(Exceptions.Fib._data)
  // Put additional private data here...
  // DO-NOT-DELETE splicer.end(Exceptions.Fib._data)


  static { 
  // DO-NOT-DELETE splicer.begin(Exceptions.Fib._load)
  // Put load function implementation here...
  // DO-NOT-DELETE splicer.end(Exceptions.Fib._load)

  }

  /**
   * User defined constructor
   */
  public Fib_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(Exceptions.Fib.Fib)
    // add construction details here
    // DO-NOT-DELETE splicer.end(Exceptions.Fib.Fib)

  }

  /**
   * Back door constructor
   */
  public Fib_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(Exceptions.Fib._wrap)
    // Insert-Code-Here {Exceptions.Fib._wrap} (_wrap)
    // DO-NOT-DELETE splicer.end(Exceptions.Fib._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Exceptions.Fib._dtor)
    // add destruction details here
    // DO-NOT-DELETE splicer.end(Exceptions.Fib._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Exceptions.Fib.finalize)
    // Insert-Code-Here {Exceptions.Fib.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(Exceptions.Fib.finalize)

  }

  // user defined static methods: (none)

  // user defined non-static methods:
  /**
   * <p>
   * Generate the requested Fibonacci number or generate Exceptions if
   * the input Fibonacci number is invalid or if any of the maximum depth
   * or maximum value parameters are exceeded.  The last argument of the
   * method should be zero.
   * </p>
   * <p>
   * The algorithm should be similar to the <code>Java</code> code below.
   * </p>
   * <pre>
   * public int getFib(int n, int max_depth, int max_value, int depth)
   * throws NegativeValueException, FibException {
   * 
   * if (n < 0) {
   * throw new NegativeValueException("n negative");
   * 
   * } else if (depth > max_depth) {
   * throw new TooDeepException("too deep");
   * 
   * } else if (n == 0) {
   * return 1;
   * 
   * } else if (n == 1) {
   * return 1;
   * 
   * } else {
   * int a = getFib(n-1, max_depth, max_value, depth+1);
   * int b = getFib(n-2, max_depth, max_value, depth+1);
   * if (a + b > max_value) {
   * throw new TooBigException("too big");
   * }
   * return a + b;
   * }
   * } 
   * </pre>
   */
  public int getFib_Impl (
    /*in*/ int n,
    /*in*/ int max_depth,
    /*in*/ int max_value,
    /*in*/ int depth ) 
    throws Exceptions.FibException, 
    Exceptions.NegativeValueException, 
    sidl.MemAllocException, 
    sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Exceptions.Fib.getFib)
    // insert implementation here
     
    if (n < 0) {
      NegativeValueException neg = new NegativeValueException();
      neg.setNote("n negative");
      throw neg;

    } else if (depth > max_depth) {
      TooDeepException t = new TooDeepException();
      t.setNote("Too Deep");
      throw t;

    } else if (n == 0) {
      return 1;
      
    } else if (n == 1) {
      return 1;
      
    } else {
      int a = getFib(n-1, max_depth, max_value, depth+1);
      int b = getFib(n-2, max_depth, max_value, depth+1);
      if (a + b > max_value) {
        TooBigException t = new TooBigException();
        t.setNote("Too Big");
        throw t;
      }
      return a + b;
    }

    // DO-NOT-DELETE splicer.end(Exceptions.Fib.getFib)

  }

  /**
   * Check for memory/reference leaks in the presence of an exception.
   * The impl will throw an exception and assign random values to
   * out parameters to prove that out values are ignored.
   * The intent is that row-major arrays should be passed to parameters
   * a1, a2, a3.
   */
  public sidl.Integer.Array2 noLeak_Impl (
    /*in*/ sidl.Integer.Array2 a1,
    /*inout*/ sidl.Integer.Array2.Holder a2,
    /*out*/ sidl.Integer.Array2.Holder a3,
    /*in*/ sidl.Integer.Array2 r1,
    /*inout*/ sidl.Integer.Array2.Holder r2,
    /*in*/ sidl.Integer.Array1 c1,
    /*inout*/ sidl.Integer.Array1.Holder c2,
    /*out*/ sidl.Integer.Array1.Holder c3,
    /*in*/ java.lang.String s1,
    /*inout*/ sidl.String.Holder s2,
    /*out*/ sidl.String.Holder s3,
    /*in*/ sidl.BaseClass o1,
    /*inout*/ sidl.BaseClass.Holder o2,
    /*out*/ sidl.BaseClass.Holder o3 ) 
    throws sidl.RuntimeException.Wrapper, 
    sidl.SIDLException
  {
    // DO-NOT-DELETE splicer.begin(Exceptions.Fib.noLeak)
    // No cleanup necessary
    sidl.SIDLException myException = new sidl.SIDLException();
    myException.setNote("This method must throw an exception.");
    throw myException;
    // DO-NOT-DELETE splicer.end(Exceptions.Fib.noLeak)

  }


  // DO-NOT-DELETE splicer.begin(Exceptions.Fib._misc)
  // Put miscellaneous code here
  // DO-NOT-DELETE splicer.end(Exceptions.Fib._misc)

} // end class Fib

