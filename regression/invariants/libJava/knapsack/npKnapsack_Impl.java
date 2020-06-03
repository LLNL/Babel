/*
 * File:          npKnapsack_Impl.java
 * Symbol:        knapsack.npKnapsack-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7326M trunk)
 * Description:   Server-side implementation for knapsack.npKnapsack
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

package knapsack;

import knapsack.iKnapsack;
import knapsack.kBadWeightExcept;
import knapsack.kExcept;
import knapsack.kSizeExcept;
import sidl.BaseClass;
import sidl.BaseInterface;
import sidl.ClassInfo;
import sidl.InvViolation;
import sidl.PostViolation;
import sidl.PreViolation;
import sidl.RuntimeException;

// DO-NOT-DELETE splicer.begin(knapsack.npKnapsack._imports)
import knapsack.gKnapsack_Impl;
// DO-NOT-DELETE splicer.end(knapsack.npKnapsack._imports)

/**
 * Symbol "knapsack.npKnapsack" (version 1.0)
 * 
 * npKnapsack:  An implementation of knapsack that allows non-positive
 * weights.
 */
public class npKnapsack_Impl extends npKnapsack
{

  // DO-NOT-DELETE splicer.begin(knapsack.npKnapsack._data)
  private int[] d_weights = null;
  private int   d_num     = 0;
  // DO-NOT-DELETE splicer.end(knapsack.npKnapsack._data)


  static { 
  // DO-NOT-DELETE splicer.begin(knapsack.npKnapsack._load)
  // Nothing to do here
  // DO-NOT-DELETE splicer.end(knapsack.npKnapsack._load)

  }

  /**
   * User defined constructor
   */
  public npKnapsack_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(knapsack.npKnapsack.npKnapsack)
    d_weights = gKnapsack_Impl.zeroArray(gKnapsack_Impl.S_MAX_WEIGHTS);
    d_num = 0;
    return;
    // DO-NOT-DELETE splicer.end(knapsack.npKnapsack.npKnapsack)

  }

  /**
   * Back door constructor
   */
  public npKnapsack_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(knapsack.npKnapsack._wrap)
    return;
    // DO-NOT-DELETE splicer.end(knapsack.npKnapsack._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(knapsack.npKnapsack._dtor)
    d_weights = null;
    d_num = 0;
    return;
    // DO-NOT-DELETE splicer.end(knapsack.npKnapsack._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(knapsack.npKnapsack.finalize)
    return;
    // DO-NOT-DELETE splicer.end(knapsack.npKnapsack.finalize)

  }

  // user defined static methods: (none)

  // user defined non-static methods:
  /**
   * Initialize the knapsack with the specified weights, w.
   */
  public void initialize_Impl (
    /*in*/ sidl.Integer.Array1 w ) 
    throws knapsack.kBadWeightExcept, 
    knapsack.kExcept, 
    knapsack.kSizeExcept, 
    sidl.InvViolation, 
    sidl.PostViolation, 
    sidl.PreViolation, 
    sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(knapsack.npKnapsack.initialize)
    if (gKnapsack_Impl.isValidSIDLArray(w)) {
      int len = w._length(0);
      if (len <= gKnapsack_Impl.S_MAX_WEIGHTS) {
        for (int i=0; i<len; i++) {
          d_weights[i] = w.get(i);
        }
        d_num = len;
      } else {
        throw new knapsack.kSizeExcept(gKnapsack_Impl.L_MAX_WEIGHTS);
      }
    } else {
      throw new knapsack.kExcept(gKnapsack_Impl.L_BAD_TYPE);
    }
    return;
    // DO-NOT-DELETE splicer.end(knapsack.npKnapsack.initialize)

  }

  /**
   * Return TRUE if all weights in the knapsack are positive;
   * otherwise, return FALSE.
   */
  public boolean onlyPosWeights_Impl () 
    throws knapsack.kExcept, 
    sidl.InvViolation, 
    sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(knapsack.npKnapsack.onlyPosWeights)
    return gKnapsack_Impl.onlyPositive(d_weights, d_num);
    // DO-NOT-DELETE splicer.end(knapsack.npKnapsack.onlyPosWeights)

  }

  /**
   * Return TRUE if all of the specified weights, w, are in the knapsack
   * or there are no specified weights; otherwise, return FALSE.
   */
  public boolean hasWeights_Impl (
    /*in*/ sidl.Integer.Array1 w ) 
    throws knapsack.kExcept, 
    sidl.InvViolation, 
    sidl.PreViolation, 
    sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(knapsack.npKnapsack.hasWeights)
    return gKnapsack_Impl.sameWeights(d_weights, w, d_num);
    // DO-NOT-DELETE splicer.end(knapsack.npKnapsack.hasWeights)

  }

  /**
   * Return TRUE if there is a solution for the specified target
   * weight; otherwise, return FALSE.  Recall a solution is a
   * subset of weights that total exactly to the specified target
   * weight.
   */
  public boolean hasSolution_Impl (
    /*in*/ int t ) 
    throws knapsack.kExcept, 
    sidl.InvViolation, 
    sidl.PreViolation, 
    sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(knapsack.npKnapsack.hasSolution)
    return gKnapsack_Impl.solve(d_weights, t, 0, d_num);
    // DO-NOT-DELETE splicer.end(knapsack.npKnapsack.hasSolution)

  }


  // DO-NOT-DELETE splicer.begin(knapsack.npKnapsack._misc)
  // Nothing to do here
  // DO-NOT-DELETE splicer.end(knapsack.npKnapsack._misc)

} // end class npKnapsack

