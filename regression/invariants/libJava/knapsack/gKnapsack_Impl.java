/*
 * File:          gKnapsack_Impl.java
 * Symbol:        knapsack.gKnapsack-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7326M trunk)
 * Description:   Server-side implementation for knapsack.gKnapsack
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

// DO-NOT-DELETE splicer.begin(knapsack.gKnapsack._imports)
// Nothing needed here
// DO-NOT-DELETE splicer.end(knapsack.gKnapsack._imports)

/**
 * Symbol "knapsack.gKnapsack" (version 1.0)
 * 
 * gKnapsack:  A good implementation of the knapsack interface.
 */
public class gKnapsack_Impl extends gKnapsack
{

  // DO-NOT-DELETE splicer.begin(knapsack.gKnapsack._data)
  public static final int S_MAX_WEIGHTS = 10;
  public static final String L_BAD_TYPE    = 
         "Encountered unexpected type(s) during processing.";
  public static final String L_MAX_WEIGHTS = 
         "Cannot exceed maximum number of weights.";
  public static final String L_POS_WEIGHTS = 
         "Non-positive weights are NOT supported.";

  private int[] d_weights = null;
  private int   d_num     = 0;
  // DO-NOT-DELETE splicer.end(knapsack.gKnapsack._data)


  static { 
  // DO-NOT-DELETE splicer.begin(knapsack.gKnapsack._load)
  // Nothing to do here
  // DO-NOT-DELETE splicer.end(knapsack.gKnapsack._load)

  }

  /**
   * User defined constructor
   */
  public gKnapsack_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(knapsack.gKnapsack.gKnapsack)
    /* Probably should be using a Vector or ArrayList... */
    d_weights = zeroArray(S_MAX_WEIGHTS);
    d_num = 0;
    return;
    // DO-NOT-DELETE splicer.end(knapsack.gKnapsack.gKnapsack)

  }

  /**
   * Back door constructor
   */
  public gKnapsack_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(knapsack.gKnapsack._wrap)
    return;
    // DO-NOT-DELETE splicer.end(knapsack.gKnapsack._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(knapsack.gKnapsack._dtor)
    d_weights = null;
    d_num = 0;
    return;
    // DO-NOT-DELETE splicer.end(knapsack.gKnapsack._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(knapsack.gKnapsack.finalize)
    return;
    // DO-NOT-DELETE splicer.end(knapsack.gKnapsack.finalize)

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
    // DO-NOT-DELETE splicer.begin(knapsack.gKnapsack.initialize)
    if (isValidSIDLArray(w)) {
      int len = w._length(0);
      if (len <= S_MAX_WEIGHTS) {
        for (int i=0; i<len; i++) {
          int currW = w.get(i);
          if (currW > 0) {
            d_weights[i] = currW;
          } else {
            throw new knapsack.kBadWeightExcept(L_POS_WEIGHTS);
          }
        }
        d_num = len;
      } else {
        throw new knapsack.kSizeExcept(L_MAX_WEIGHTS);
      } 
    } else {
      throw new knapsack.kExcept(L_BAD_TYPE);
    }
    return;
    // DO-NOT-DELETE splicer.end(knapsack.gKnapsack.initialize)

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
    // DO-NOT-DELETE splicer.begin(knapsack.gKnapsack.onlyPosWeights)
    return onlyPositive(d_weights, d_num);
    // DO-NOT-DELETE splicer.end(knapsack.gKnapsack.onlyPosWeights)

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
    // DO-NOT-DELETE splicer.begin(knapsack.gKnapsack.hasWeights)
    return sameWeights(d_weights, w, d_num);
    // DO-NOT-DELETE splicer.end(knapsack.gKnapsack.hasWeights)

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
    // DO-NOT-DELETE splicer.begin(knapsack.gKnapsack.hasSolution)
    return solve(d_weights, t, 0, d_num);
    // DO-NOT-DELETE splicer.end(knapsack.gKnapsack.hasSolution)

  }


  // DO-NOT-DELETE splicer.begin(knapsack.gKnapsack._misc)
  /*
   * Returns true if the contents of the two lists match, false otherwise.
   * The order of entries does not matter.
   */
  public static boolean isValidSIDLArray(sidl.Integer.Array sW) {
    return ((sW != null) && (sW._dim() == 1));
  }

  /*
   * Returns true if the vector, w, only contains positive values;
   * otherwise, returns false.  
   */
  public static boolean onlyPositive(int[] w, int len) {
    boolean onlyPos = false;
    if (w != null) {
      if (len > 0) {
        onlyPos = true;
        for (int i=0; i<len; i++) {
          if (w[i] <= 0) {
            onlyPos = false;
          }
        }
      }
    }
    return onlyPos;
  } /* onlyPositive */

  /*
   * Returns true if the contents of the two lists match; otherwise, returns
   * false.  The order of entries does not matter.
   */
  public static boolean sameWeights(int[] nW, sidl.Integer.Array1 sW, int len) 
    throws knapsack.kExcept, knapsack.kSizeExcept
  {
    boolean same = false;
    int     lenSW;
    int[]   match;
    if ( isValidSIDLArray(sW) && (nW != null) ) {
      lenSW = sW._length(0);  
      if (  (len == lenSW) && (len <= S_MAX_WEIGHTS) ) {
        match = zeroArray(lenSW);
        for (int i=0; i<lenSW; i++) {
          for (int j=0; j<len; j++) {
            if ((sW._get(i) == nW[j]) && (match[j] == 0)) {
              match[j] = 1;
            }
          }
        }
        same = onlyPositive(match, len);
      } else {
        throw new knapsack.kSizeExcept(L_MAX_WEIGHTS);
      }
    } else {
      throw new knapsack.kExcept(L_BAD_TYPE);
    }
    return same;
  } /* sameWeights */


  /*
   * Recursive implementation of the simplified knapsack problem.
   *
   * Based on the algorithm defined in "Data Structures and Algorithms"
   * by Aho, Hopcroft, and Ullman (c) 1983.
   */
  public static boolean solve(int[] w, int t, int i, int n) 
    throws knapsack.kExcept
  {
    boolean has = false;
    if (w != null) {
      if (t == 0) {
        has = true;
      } else if ( (t < 0) || (i >= n) ) {
        has = false;
      } else if (solve(w, t-w[i], i+1, n)) {
        has = true;
      } else {
        has = solve(w, t, i+1, n);
      }
    }
    return has;
  } /* solve */


  /*
   * Returns a pre-allocated integer array of zeros.
   */
  public static int[] zeroArray(int len) {
    int[] zArr = new int[len];
    /* Can't remember.  Is it necessary to initialize a Java int array? */
    for (int i=0; i<len; i++) {
      zArr[i] = 0;
    }
    return zArr;
  } /* zeroArray */
  // DO-NOT-DELETE splicer.end(knapsack.gKnapsack._misc)

} // end class gKnapsack

