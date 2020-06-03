/*
 * File:          IntOrderTest_Impl.java
 * Symbol:        Ordering.IntOrderTest-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Ordering.IntOrderTest
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

package Ordering;

import sidl.BaseClass;
import sidl.BaseInterface;
import sidl.ClassInfo;
import sidl.RuntimeException;

// DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest._imports)
// Put additional imports here...
// DO-NOT-DELETE splicer.end(Ordering.IntOrderTest._imports)

/**
 * Symbol "Ordering.IntOrderTest" (version 0.1)
 * 
 * This class provides methods to verify that the array ordering
 * capabilities work for arrays of int.
 */
public class IntOrderTest_Impl extends IntOrderTest
{

  // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest._data)

  static int iFunc(int[] ind, int dim)
  {
    int res = 0;
    int i;
    for(i = 0; i < dim ; ++i) {
      res += (i+1)*ind[i];
    }
    return res;
  }

  static boolean incIndex(int[] ind, sidl.Integer.Array A)
  {
    int i = 0;
    int dimen = A._dim();
    while (i < dimen) {
      if (++(ind[i]) > A._upper(i)) {
        ind[i] = A._lower(i);
        ++i;
      }
      else {
        return true;
      }
    }
    /* we're all done */
    return false;
  }

  static boolean isIMatrix(sidl.Integer.Array A)
  {
    if (A != null) {
      int dimen = A._dim();
      int i;
      int[] cindex = {0,0,0,0,0,0,0}; 
      for(i = 0; i < dimen; ++i){
        cindex[i] = A._lower(i);
        if (A._lower(i) > A._upper(i)) {
          return true;
        }
      }
      do { 
        if (iFunc(cindex, dimen) != 
            A._get(cindex[0],cindex[1],cindex[2],cindex[3],cindex[4],cindex[5],cindex[6])) { 
          return false;
        }
      } while (incIndex(cindex, A));
      return true;
    }
    return false;
  }

  static void fillIMatrix(sidl.Integer.Array A)
  {
    if (A != null) {
      int dimen = A._dim();
      int i;
      int[] cindex = {0,0,0,0,0,0,0};  
      for(i = 0; i < dimen; ++i){
        cindex[i] = A._lower(i);
        if (A._lower(i) > A._upper(i)){
          return;
        }
      }
      do {
        A._set(cindex[0], cindex[1], cindex[2], cindex[3], cindex[4], cindex[5],
               cindex[6], iFunc(cindex, dimen));
      } while (incIndex(cindex, A));
    }
  }


  // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest._data)


  static { 
  // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest._load)
  // Put load function implementation here...
  // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest._load)

  }

  /**
   * User defined constructor
   */
  public IntOrderTest_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.IntOrderTest)
    // add construction details here
    // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.IntOrderTest)

  }

  /**
   * Back door constructor
   */
  public IntOrderTest_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest._wrap)
    // Insert-Code-Here {Ordering.IntOrderTest._wrap} (_wrap)
    // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest._dtor)
    // add destruction details here
    // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.finalize)
    // Insert-Code-Here {Ordering.IntOrderTest.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.finalize)

  }

  // user defined static methods:
  /**
   * Create a column-major matrix satisfying condition I.
   */
  public static sidl.Integer.Array2 makeColumnIMatrix_Impl (
    /*in*/ int size,
    /*in*/ boolean useCreateCol ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.makeColumnIMatrix)
    sidl.Integer.Array2 res;
    if (useCreateCol) {
      res = new sidl.Integer.Array2(size, size, false);
    }
    else {
      res = new sidl.Integer.Array2(size, size, true);
    }
    fillIMatrix(res);
    return res;
    // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.makeColumnIMatrix)

  }

  /**
   * Create a row-major matrix satisfying condition I.
   */
  public static sidl.Integer.Array2 makeRowIMatrix_Impl (
    /*in*/ int size,
    /*in*/ boolean useCreateRow ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.makeRowIMatrix)
    // insert implementation here
        sidl.Integer.Array2 res;
    if (useCreateRow) {
      res = new sidl.Integer.Array2(size, size, true);
    }
    else {
      res = new sidl.Integer.Array2(size, size, false);
    }
    fillIMatrix(res);
    return res;
    // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.makeRowIMatrix)

  }

  /**
   * Create a 4-D matrix satisfying condition I.  Each dimension has
   * size elements numbers 0 through size-1.
   */
  public static sidl.Integer.Array4 makeIMatrix_Impl (
    /*in*/ int size,
    /*in*/ boolean useCreateColumn ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.makeIMatrix)
    sidl.Integer.Array4 res;
    sidl.Integer.Array A;
    int[] lower = {0,0,0,0};
    int upper[] = new int[4];
    upper[0] = upper[1] = upper[2] = upper[3] = size - 1;
    if (useCreateColumn) {
      A = new sidl.Integer.Array(4, lower,upper, false);
    }
    else {
      A = new sidl.Integer.Array(4, lower,upper, true);
    }
    fillIMatrix(A);
    res = (sidl.Integer.Array4) A._dcast();
    return res;
    // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.makeIMatrix)

  }

  /**
   * Create a column-major matrix satisfying condition I.
   */
  public static void createColumnIMatrix_Impl (
    /*in*/ int size,
    /*in*/ boolean useCreateCol,
    /*out*/ sidl.Integer.Array2.Holder res ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.createColumnIMatrix)
    sidl.Integer.Array2 A;
    if (useCreateCol) {
      A = new sidl.Integer.Array2(size, size, false);
    }
    else {
      A = new sidl.Integer.Array2(size, size, true);
    }
    fillIMatrix(A);
    res.set(A);
    return ;
    // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.createColumnIMatrix)

  }

  /**
   * Create a row-major matrix satisfying condition I.
   */
  public static void createRowIMatrix_Impl (
    /*in*/ int size,
    /*in*/ boolean useCreateRow,
    /*out*/ sidl.Integer.Array2.Holder res ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.createRowIMatrix)
    // insert implementation here
        sidl.Integer.Array2 A;
    if (useCreateRow) {
      A = new sidl.Integer.Array2(size, size, true);
    }
    else {
      A = new sidl.Integer.Array2(size, size, false);
    }
    fillIMatrix(A);
    res.set(A);
    return ;
    // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.createRowIMatrix)

  }

  /**
   * Make sure an array is column-major.  No changes to the dimension or
   * values in a are made.
   */
  public static void ensureColumn_Impl (
    /*inout*/ sidl.Integer.Array2.Holder a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.ensureColumn)
    // Do nothing?
    return ;
    // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.ensureColumn)

  }

  /**
   * Make sure an array is row-major.  No changes to the dimension or
   * values in a are made.
   */
  public static void ensureRow_Impl (
    /*inout*/ sidl.Integer.Array2.Holder a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.ensureRow)
    // Do nothing?
    return ;
    // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.ensureRow)

  }

  /**
   * Return <code>true</code> iff the implementation sees
   * an incoming array satisfying condition I.
   */
  public static boolean isIMatrixOne_Impl (
    /*in*/ sidl.Integer.Array1 A ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isIMatrixOne)
    // insert implementation here
    return isIMatrix(A);
    // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isIMatrixOne)

  }

  /**
   * Return <code>true</code> iff the implementation sees
   * an incoming column-major array satisfying condition I.
   */
  public static boolean isColumnIMatrixOne_Impl (
    /*in*/ sidl.Integer.Array1 A ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isColumnIMatrixOne)
    // insert implementation here
    return A._isColumnOrder() && isIMatrix(A);
    // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isColumnIMatrixOne)

  }

  /**
   * Return <code>true</code> iff the implementation sees
   * an incoming row-major array satisfying condition I.
   */
  public static boolean isRowIMatrixOne_Impl (
    /*in*/ sidl.Integer.Array1 A ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isRowIMatrixOne)
    // insert implementation here
    return A._isRowOrder() && isIMatrix(A);
    // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isRowIMatrixOne)

  }

  /**
   * Return <code>true</code> iff the implementation sees
   * an incoming array satisfying condition I.
   */
  public static boolean isIMatrixTwo_Impl (
    /*in*/ sidl.Integer.Array2 A ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isIMatrixTwo)
    // insert implementation here
    return isIMatrix(A);
    // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isIMatrixTwo)

  }

  /**
   * Return <code>true</code> iff the implementation sees
   * an incoming column-major array satisfying condition I.
   */
  public static boolean isColumnIMatrixTwo_Impl (
    /*in*/ sidl.Integer.Array2 A ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isColumnIMatrixTwo)
    // insert implementation here
    return A._isColumnOrder() && isIMatrix(A);
    // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isColumnIMatrixTwo)

  }

  /**
   * Return <code>true</code> iff the implementation sees
   * an incoming row-major array satisfying condition I.
   */
  public static boolean isRowIMatrixTwo_Impl (
    /*in*/ sidl.Integer.Array2 A ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isRowIMatrixTwo)
    // insert implementation here
    return A._isRowOrder() && isIMatrix(A);
    // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isRowIMatrixTwo)

  }

  /**
   * Return <code>true</code> iff the implementation sees
   * an incoming array satisfying condition I.
   */
  public static boolean isIMatrixFour_Impl (
    /*in*/ sidl.Integer.Array4 A ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isIMatrixFour)
    // insert implementation here
    return isIMatrix(A);
    // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isIMatrixFour)

  }

  /**
   * Return <code>true</code> iff the implementation sees
   * an incoming column-major array satisfying condition I.
   */
  public static boolean isColumnIMatrixFour_Impl (
    /*in*/ sidl.Integer.Array4 A ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isColumnIMatrixFour)
    // insert implementation here
    return A._isColumnOrder() && isIMatrix(A);
    // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isColumnIMatrixFour)

  }

  /**
   * Return <code>true</code> iff the implementation sees
   * an incoming row-major array satisfying condition I.
   */
  public static boolean isRowIMatrixFour_Impl (
    /*in*/ sidl.Integer.Array4 A ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isRowIMatrixFour)
    // insert implementation here
    return A._isRowOrder() && isIMatrix(A);
    // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isRowIMatrixFour)

  }

  /**
   * Return <code>true</code> iff the implementation of slice
   * and smart copy is correct.
   */
  public static boolean isSliceWorking_Impl (
    /*in*/ boolean useCreateCol ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isSliceWorking)
    // insert implementation here
    boolean res = true;
    int maxDim = 16;
    int halfDim = maxDim >> 1;
    sidl.Integer.Array A = null;
    sidl.Integer.Array B = null; 
    sidl.Integer.Array1 B1 =null;
    sidl.Integer.Array2 B2 =null;
    sidl.Integer.Array3 B3 =null;
    sidl.Integer.Array4 B4 =null;
    sidl.Integer.Array5 B5 =null;
    sidl.Integer.Array6 B6 =null;
    int ind[] = new int[2];
    int stride[] = {2, 2};
    int numElem[] = new int[2];
    int newIndex[] = new int[2];
    numElem[0] = numElem[1] = halfDim;
    if (useCreateCol) {
      A = Ordering.IntOrderTest.makeColumnIMatrix(maxDim,false);
    } else {
      A = Ordering.IntOrderTest.makeRowIMatrix(maxDim,false);
    }
    B = A._slice(2, numElem, null, stride, null);
    if (!(B != null && B._dim() == 2)) {
      return false;
    }
    B2 = (sidl.Integer.Array2) B._dcast();
    for(ind[1] = newIndex[1] = 0; newIndex[1] < halfDim; 
        ++newIndex[1], ind[1] += 2) {
      for(ind[0] = newIndex[0] = 0; newIndex[0] < halfDim;
          ++newIndex[0], ind[0] += 2) {
        if ((B2.get(newIndex[0], newIndex[1]) != iFunc(ind,2))) {
          return false;
        }
      }
    }

    B = null;
    B2 = null;
    ind[0] = ind[1] = 1;
    newIndex[0] = newIndex[1] = 0;
    B = A._slice(2, numElem, ind, stride, newIndex);
    if (!(B != null && B._dim() == 2)) {
      return false;
    }
    B2 = (sidl.Integer.Array2) B._dcast();
    for(newIndex[1] = 0; newIndex[1] < halfDim; 
        ++newIndex[1], ind[1] += 2) {
      for(ind[0] = 1, newIndex[0] = 0; newIndex[0] < halfDim;
          ++newIndex[0], ind[0] += 2) {
        if ((B2.get(newIndex[0], newIndex[1]) != iFunc(ind,2))) {
          return false;
        }
      }
    }
    
    B = null;
    B2 = null;

    ind[0] = ind[1] = 1;
    newIndex[1] = newIndex[0] = 1;
    B = A._slice(2, numElem, ind, stride, newIndex);
    if (!(B != null && B._dim() == 2)) {
      return false;
    }
    B2 = (sidl.Integer.Array2) B._dcast();

    for(newIndex[1] = 1; newIndex[1] <= halfDim; 
        ++newIndex[1], ind[1] += 2) {
      for(ind[0] = 1, newIndex[0] = 1; newIndex[0] <= halfDim;
          ++newIndex[0], ind[0] += 2) {
        if ((B2.get(newIndex[0], newIndex[1]) != iFunc(ind,2)) || 
            (B2.get(newIndex[0], newIndex[1]) != 
            A._get(ind[0], ind[1], 0,0,0,0,0))) {
          
          return false;
        }
      }
    }
    
    B = null;
    B2 = null;
    numElem[0] = 0;
    numElem[1] = maxDim;
    B = A._slice(1, numElem, null, null, null);
    if (!(B != null && B._dim() == 1)) {
      return false;
    }
    B1 = (sidl.Integer.Array1) B._dcast();
    ind[0] = newIndex[0] = 0;
    for(ind[1] = newIndex[1] = 0; newIndex[1] < maxDim; 
        ++newIndex[1], ++ind[1]) {
      if (B1.get(newIndex[1]) != 
          A._get(ind[0], ind[1],0,0,0,0,0)) {
        return false;
      }
    }
  
    B = null;
    B1 = null;
    numElem[0] = maxDim;
    numElem[1] = 0;
    ind[0] = 0;
    ind[1] = 8;
    newIndex[0] = newIndex[1] = 0;
    B = A._slice(1, numElem, ind, null, newIndex);
    if (!(B != null && (B._dim() == 1))) {
      return false;
    }
    B1 = (sidl.Integer.Array1) B._dcast();

    for(ind[0] = newIndex[0] = 0; newIndex[0] < maxDim; 
        ++newIndex[0], ++ind[0]) {
      if (B1.get(newIndex[0]) != 
          A._get(ind[0], ind[1],0,0,0,0,0)) {
        return false;
      }
    }
  
    B = null;
    B1 = null;
    numElem[0] = maxDim;
    numElem[1] = 0;
    ind[0] = 0;
    ind[1] = 0;
    B = A._slice(1, numElem, ind, null, null);
    if (!(B != null && (B._dim() == 1))) {
      return false;
    }
    B1 = (sidl.Integer.Array1) B._dcast();
    if (!(Ordering.IntOrderTest.isIMatrixOne(B1) &&
          Ordering.IntOrderTest.isColumnIMatrixOne(B1) &&
          Ordering.IntOrderTest.isRowIMatrixOne(B1))) {
      return false;
    }
  
    return res;

    // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isSliceWorking)

  }


  // user defined non-static methods: (none)

  // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest._misc)
  // Put miscellaneous code here
  // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest._misc)

} // end class IntOrderTest

