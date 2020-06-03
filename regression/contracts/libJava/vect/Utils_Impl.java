/*
 * File:          Utils_Impl.java
 * Symbol:        vect.Utils-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for vect.Utils
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

package vect;

import sidl.BaseClass;
import sidl.BaseInterface;
import sidl.ClassInfo;
import sidl.PostViolation;
import sidl.PreViolation;
import sidl.RuntimeException;
import vect.BadLevel;
import vect.vDivByZeroExcept;
import vect.vNegValExcept;

// DO-NOT-DELETE splicer.begin(vect.Utils._imports)
import java.lang.Math;
// DO-NOT-DELETE splicer.end(vect.Utils._imports)

/**
 * Symbol "vect.Utils" (version 1.0)
 */
public class Utils_Impl extends Utils
{

  // DO-NOT-DELETE splicer.begin(vect.Utils._data)
  // Nothing needed here.
  // DO-NOT-DELETE splicer.end(vect.Utils._data)


  static { 
  // DO-NOT-DELETE splicer.begin(vect.Utils._load)
  // Nothing needed here.
  // DO-NOT-DELETE splicer.end(vect.Utils._load)

  }

  /**
   * User defined constructor
   */
  public Utils_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(vect.Utils.Utils)
    return;
    // DO-NOT-DELETE splicer.end(vect.Utils.Utils)

  }

  /**
   * Back door constructor
   */
  public Utils_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(vect.Utils._wrap)
    return;
    // DO-NOT-DELETE splicer.end(vect.Utils._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(vect.Utils._dtor)
    return;
    // DO-NOT-DELETE splicer.end(vect.Utils._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(vect.Utils.finalize)
    return;
    // DO-NOT-DELETE splicer.end(vect.Utils.finalize)

  }

  // user defined static methods:
  /**
   * boolean result operations 
   * Return TRUE if the specified vector is the zero vector, within the
   * given tolerance level; otherwise, return FALSE.
   */
  public static boolean vuIsZero_Impl (
    /*in*/ gov.llnl.sidl.BaseArray u,
    /*in*/ double tol ) 
    throws sidl.PreViolation, 
    sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(vect.Utils.vuIsZero)
    boolean is = true;
    int     i, maxI;
    double  absVal;

    if (u != null) 
    {
      sidl.Double.Array1 du = (sidl.Double.Array1)((sidl.Double.Array)u)._dcast();
      maxI = u.upper(0);
      for (i=u.lower(0); (i <= maxI) && is; i++)
      {
        absVal = Math.abs(du.get(i));
        if ( absVal > Math.abs(tol) ) {
           is = false;
        }
      }
    } else {
      is = false;
    }

    return is;
    // DO-NOT-DELETE splicer.end(vect.Utils.vuIsZero)

  }

  /**
   * Return TRUE if the specified vector is the unit vector, within the
   * given tolerance level; otherwise, return FALSE.
   */
  public static boolean vuIsUnit_Impl (
    /*in*/ gov.llnl.sidl.BaseArray u,
    /*in*/ double tol ) 
    throws sidl.PreViolation, 
    sidl.RuntimeException.Wrapper, 
    vect.vNegValExcept
  {
    // DO-NOT-DELETE splicer.begin(vect.Utils.vuIsUnit)
    boolean is   = false;
                                                                                
    double absDiff = Math.abs(vuNorm_Impl(u, tol, 0) - 1.0);
    if ( absDiff <= Math.abs(tol) ) {
      is = true;
    } else {
      is = false;
    }
                                                                                
    return is;
    // DO-NOT-DELETE splicer.end(vect.Utils.vuIsUnit)

  }

  /**
   * Return TRUE if the specified vectors are equal, within the given
   * tolerance level; otherwise, return FALSE.
   */
  public static boolean vuAreEqual_Impl (
    /*in*/ gov.llnl.sidl.BaseArray u,
    /*in*/ gov.llnl.sidl.BaseArray v,
    /*in*/ double tol ) 
    throws sidl.PreViolation, 
    sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(vect.Utils.vuAreEqual)
    boolean are = false;
    int     i, lenU, lenV;
    double  absDiff;

    if ( (u != null) && (v != null) )
    {
      sidl.Double.Array1 du = (sidl.Double.Array1)((sidl.Double.Array)u)._dcast();
      sidl.Double.Array1 dv = (sidl.Double.Array1)((sidl.Double.Array)v)._dcast();
      lenU = u._length(0);
      lenV = v._length(0);
      if ( (lenU == lenV) && (u._dim() == 1) && (v._dim() == 1) )
      {
        are = true;
        for (i=0; (i < lenU) && are; i++)
        {
          absDiff = Math.abs(du.get(i) - dv.get(i));
          if ( absDiff > Math.abs(tol) ) {
            are = false;
          }
        }
      }
    }

    return are;
    // DO-NOT-DELETE splicer.end(vect.Utils.vuAreEqual)

  }

  /**
   * Return TRUE if the specified vectors are orthogonal, within the given
   * tolerance; otherwise, return FALSE.
   */
  public static boolean vuAreOrth_Impl (
    /*in*/ gov.llnl.sidl.BaseArray u,
    /*in*/ gov.llnl.sidl.BaseArray v,
    /*in*/ double tol ) 
    throws sidl.PreViolation, 
    sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(vect.Utils.vuAreOrth)
    boolean are = false;
    double  absVal;

    if ( (u != null) && (v != null) )
    {
      absVal = Math.abs(vuDot_Impl(u, v, tol, 0));
      if ( absVal <= Math.abs(tol) ) {
        are = true;
      } else {
        are = false;
      }
    }

    return are;
    // DO-NOT-DELETE splicer.end(vect.Utils.vuAreOrth)

  }

  /**
   * Return TRUE if the Schwarz (or Cauchy-Schwarz) inequality holds, within
   * the given tolerance; otherwise, return FALSE.
   */
  public static boolean vuSchwarzHolds_Impl (
    /*in*/ gov.llnl.sidl.BaseArray u,
    /*in*/ gov.llnl.sidl.BaseArray v,
    /*in*/ double tol ) 
    throws sidl.PreViolation, 
    sidl.RuntimeException.Wrapper, 
    vect.vNegValExcept
  {
    // DO-NOT-DELETE splicer.begin(vect.Utils.vuSchwarzHolds)
    boolean holds = false;
    double  absDot, absNorms;
  
    if ( (u != null) && (v != null) )
    {
      absDot   = Math.abs(vuDot_Impl(u, v, tol, 0));
      absNorms = Math.abs(vuNorm_Impl(u, tol, 0) * vuNorm_Impl(v, tol, 0));
      if ( absDot <= (absNorms + Math.abs(tol)) ) {
        holds = true;
      } else {
        holds = false;
      }
    }
  
    return holds;
    // DO-NOT-DELETE splicer.end(vect.Utils.vuSchwarzHolds)

  }

  /**
   * Return TRUE if the Minkowski (or triangle) inequality holds, within the
   * given tolerance; otherwise, return FALSE.
   */
  public static boolean vuTriIneqHolds_Impl (
    /*in*/ gov.llnl.sidl.BaseArray u,
    /*in*/ gov.llnl.sidl.BaseArray v,
    /*in*/ double tol ) 
    throws sidl.PreViolation, 
    sidl.RuntimeException.Wrapper, 
    vect.vNegValExcept
  {
    // DO-NOT-DELETE splicer.begin(vect.Utils.vuTriIneqHolds)
    boolean            holds = false;
    gov.llnl.sidl.BaseArray sum   = null;
    double             absNormSum, normU, normV, absSumNorms;
  
    if ( (u != null) && (v != null) )
    {
      if ( (u._dim() == 1) && (v._dim() == 1) ) {
        sum         = vuSum_Impl(u, v, 0);
        absNormSum  = Math.abs(vuNorm_Impl(sum, tol, 0));
        normU       = vuNorm_Impl(u, tol, 0);
        normV       = vuNorm_Impl(v, tol, 0);
        absSumNorms = Math.abs(normU + normV);
        if ( absNormSum <= absSumNorms + Math.abs(tol)) {
          holds = true;
        } else {
          holds = false;
        }
      }
    }
  
    return holds;
    // DO-NOT-DELETE splicer.end(vect.Utils.vuTriIneqHolds)

  }

  /**
   * double result operations 
   * Return the norm (or length) of the specified vector.
   * 
   * Note that the size exception is given here simply because the
   * implementation is leveraging the vuDot() method.  Also the tolerance
   * is included to enable the caller to specify the tolerance used in
   * contract checking.
   */
  public static double vuNorm_Impl (
    /*in*/ gov.llnl.sidl.BaseArray u,
    /*in*/ double tol,
    /*in*/ long badLevel ) 
    throws sidl.PostViolation, 
    sidl.PreViolation, 
    sidl.RuntimeException.Wrapper, 
    vect.vNegValExcept
  {
    // DO-NOT-DELETE splicer.begin(vect.Utils.vuNorm)
    double  dot;
    double  res = 0.0;   

    if (badLevel == vect.BadLevel.NoVio)
    {
      if (u != null)
      {
        dot = vuDot_Impl(u, u, tol, 0);
        if (dot > 0.0) {
          res = Math.sqrt(dot);
        } else if (dot < 0.0) {
          /* Note that this should NEVER happen! */
          res = -5.0;
          vNegValExcept ex = new vNegValExcept();
          ex.setNote("vuNorm: vNegValExcept: Cannot sqrt() a negative value.");
          throw ex;
        }
      }
    } else if (badLevel == vect.BadLevel.NegRes) {
      res = -5.0;
    } else if (badLevel == vect.BadLevel.PosRes) {
      res = 5.0;
    } else if (badLevel == vect.BadLevel.ZeroRes) {
      res = 0.0;
    } else {
      res = -5.0;
    }

    return res;
    // DO-NOT-DELETE splicer.end(vect.Utils.vuNorm)

  }

  /**
   * Return the dot (, inner, or scalar) product of the specified vectors.
   */
  public static double vuDot_Impl (
    /*in*/ gov.llnl.sidl.BaseArray u,
    /*in*/ gov.llnl.sidl.BaseArray v,
    /*in*/ double tol,
    /*in*/ long badLevel ) 
    throws sidl.PostViolation, 
    sidl.PreViolation, 
    sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(vect.Utils.vuDot)
    double dot = 0.0;
    int    i, lenU, lenV;

    if (u != null) { lenU = u._length(0); } else { lenU = 0; }
    if (badLevel == vect.BadLevel.NoVio)
    {
      if ( (u != null) && (v != null) )
      {
        sidl.Double.Array1 du = (sidl.Double.Array1)((sidl.Double.Array)u)._dcast();
        sidl.Double.Array1 dv = (sidl.Double.Array1)((sidl.Double.Array)v)._dcast();
        lenV = v._length(0);
        if ( (lenU == lenV) && (u._dim() == 1) && (v._dim() == 1) )
        {
          for (i=0; i < lenU; ++i) {
            dot += du.get(i) * dv.get(i);
          }
        }
      }
    } else if (badLevel == vect.BadLevel.NegRes) {
      dot = -5.0;
    } else if (badLevel == vect.BadLevel.PosRes) {
      dot = 5.0;
    } else {
      dot = -1.0;
    }

    return dot;
    // DO-NOT-DELETE splicer.end(vect.Utils.vuDot)

  }

  /**
   * vector result operations 
   * Return the (scalar) product of the specified vector.
   */
  public static gov.llnl.sidl.BaseArray vuProduct_Impl (
    /*in*/ double a,
    /*in*/ gov.llnl.sidl.BaseArray u,
    /*in*/ long badLevel ) 
    throws sidl.PostViolation, 
    sidl.PreViolation, 
    sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(vect.Utils.vuProduct)
    sidl.Double.Array1 prod = null;
    int                i;
    int                lenU  = 0;
    int                lower = 0;
    int                upper = 0;

    if (u != null) { 
      lenU = u._length(0); 
      upper = lenU - 1;
    }

    if (badLevel == vect.BadLevel.NoVio)
    {
      if (u != null) {
        sidl.Double.Array1 du = (sidl.Double.Array1)((sidl.Double.Array)u)._dcast();
        prod = new sidl.Double.Array1(lower, upper, false);
        for (i=0; i < lenU; i++) {
          prod.set(i, a * du.get(i));
        }
      }
    } else if (badLevel == vect.BadLevel.NullRes) {
      prod = null;
    } else if (badLevel == vect.BadLevel.TwoDRes) {
      /*
       * Cannot create 2D array so just make sure the one created 
       * is the wrong size.
       */
      //prod = new sidl.Double.Array2(lower, lower, upper, upper, false);
      prod = new sidl.Double.Array1(lower, lenU*2-1, false);
    } else if (badLevel == vect.BadLevel.WrongSizeRes) {
      prod = new sidl.Double.Array1(lower, upper+5, false);
    } else {
      prod = null;
    }

     return prod;
    // DO-NOT-DELETE splicer.end(vect.Utils.vuProduct)

  }

  /**
   * Return the negation of the specified vector.
   */
  public static gov.llnl.sidl.BaseArray vuNegate_Impl (
    /*in*/ gov.llnl.sidl.BaseArray u,
    /*in*/ long badLevel ) 
    throws sidl.PostViolation, 
    sidl.PreViolation, 
    sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(vect.Utils.vuNegate)
    gov.llnl.sidl.BaseArray negU = null;
    int                lenU = 0;
    int                lower = 0;
    int                upper = 0;

    if (u != null) { 
      lenU = u._length(0); 
      upper = lenU - 1;
    }
  
    if (badLevel == vect.BadLevel.NoVio) {
      if (u != null) {
        negU = vuProduct_Impl(-1.0, u, 0);
      }
    } else if (badLevel == vect.BadLevel.NullRes) {
      negU = null;
    } else if (badLevel == vect.BadLevel.TwoDRes) {
      /*
       * Cannot create 2D array so just make sure the one created 
       * is the wrong size.
       */
      //negU = new sidl.Double.Array2(lower, lower, upper, upper, false);
      negU = new sidl.Double.Array1(lower, lenU*2-1, false);
    } else if (badLevel == vect.BadLevel.WrongSizeRes) {
      negU = new sidl.Double.Array1(lower, upper+5, false);
    } else {
      negU = null;
    }
  
    return negU;
    // DO-NOT-DELETE splicer.end(vect.Utils.vuNegate)

  }

  /**
   * Return the normalizaton of the specified vector.
   * 
   * Note the tolerance is included because the implementation invokes 
   * vuDot().
   */
  public static gov.llnl.sidl.BaseArray vuNormalize_Impl (
    /*in*/ gov.llnl.sidl.BaseArray u,
    /*in*/ double tol,
    /*in*/ long badLevel ) 
    throws sidl.PostViolation, 
    sidl.PreViolation, 
    sidl.RuntimeException.Wrapper, 
    vect.vDivByZeroExcept
  {
    // DO-NOT-DELETE splicer.begin(vect.Utils.vuNormalize)
    gov.llnl.sidl.BaseArray prod = null;
    double             val;
    int                lenU = 0;
    int                lower = 0;
    int                upper = 0;

    if (u != null) { 
      lenU = u._length(0); 
      upper = lenU - 1;
    } else { 
      lenU = 0; 
    }
  
    if (badLevel == vect.BadLevel.NoVio)
    {
      if (u != null)
      {
        val = vuNorm_Impl(u, tol, 0);
        if (val != 0.0) {
          prod = vuProduct_Impl(1.0/val, u, 0);
        } else {
          prod = null;
          vDivByZeroExcept ex = new vDivByZeroExcept();
          ex.setNote(
            "vuNormalize: vDivByZeroExcept: Cannot divide by zero.");
          throw ex;
        }
      }
    } else if (badLevel == vect.BadLevel.NullRes) {
      prod = null;
    } else if (badLevel == vect.BadLevel.TwoDRes) {
      /*
       * Cannot create 2D array so just make sure the one created 
       * is the wrong size.
       */
      //prod = new sidl.Double.Array2(lower, lower, upper, upper, false);
      prod = new sidl.Double.Array1(lower, lenU*2-1, false);
    } else if (badLevel == vect.BadLevel.WrongSizeRes) {
      prod = new sidl.Double.Array1(lower, upper+5, false);
    } else {
      prod = null;
    }
  
    return prod;
    // DO-NOT-DELETE splicer.end(vect.Utils.vuNormalize)

  }

  /**
   * Return the sum of the specified vectors.
   */
  public static gov.llnl.sidl.BaseArray vuSum_Impl (
    /*in*/ gov.llnl.sidl.BaseArray u,
    /*in*/ gov.llnl.sidl.BaseArray v,
    /*in*/ long badLevel ) 
    throws sidl.PostViolation, 
    sidl.PreViolation, 
    sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(vect.Utils.vuSum)
    sidl.Double.Array1 sum = null;
    int                i, lenV;
    int                lenU  = 0;
    int                lower = 0;
    int                upper = 0;

    if (u != null) { 
      lenU = u._length(0); 
      upper = lenU - 1;
    }

    if (badLevel == vect.BadLevel.NoVio)
    {
      if ( (u != null) && (v != null) )
      {
        sidl.Double.Array1 du = (sidl.Double.Array1)((sidl.Double.Array)u)._dcast();
        sidl.Double.Array1 dv = (sidl.Double.Array1)((sidl.Double.Array)v)._dcast();
        lenV = v._length(0);  
        if ( (lenU == lenV) && (u._dim() == 1) && (v._dim() == 1) )
        {
          sum = new sidl.Double.Array1(lower, upper, false);
          for (i=0; i < lenU; i++) {
            sum.set(i, du.get(i) + dv.get(i));
          }
        }
      }
    } else if (badLevel == vect.BadLevel.NullRes) {
      sum = null;
    } else if (badLevel == vect.BadLevel.TwoDRes) {
      /*
       * Cannot create 2D array so just make sure the one created 
       * is the wrong size.
       */
      //sum = new sidl.Double.Array2(lower, lower, upper, upper, false);
      sum = new sidl.Double.Array1(lower, lenU*2-1, false);
    } else if (badLevel == vect.BadLevel.WrongSizeRes) {
      sum = new sidl.Double.Array1(lower, upper+5, false);
    } else {
      sum = null;
    }

    return sum;
    // DO-NOT-DELETE splicer.end(vect.Utils.vuSum)

  }

  /**
   * Return the difference of the specified vectors.
   */
  public static gov.llnl.sidl.BaseArray vuDiff_Impl (
    /*in*/ gov.llnl.sidl.BaseArray u,
    /*in*/ gov.llnl.sidl.BaseArray v,
    /*in*/ long badLevel ) 
    throws sidl.PostViolation, 
    sidl.PreViolation, 
    sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(vect.Utils.vuDiff)
    sidl.Double.Array1 diff = null;
    int                i, lenV;
    int                lenU  = 0;
    int                lower = 0;
    int                upper = 0;

    if (u != null) { 
      lenU = u._length(0); 
      upper = lenU - 1;
    }

    if (badLevel == vect.BadLevel.NoVio)
    {
      if ( (u != null) && (v != null) )
      {
        sidl.Double.Array1 du = (sidl.Double.Array1)((sidl.Double.Array)u)._dcast();
        sidl.Double.Array1 dv = (sidl.Double.Array1)((sidl.Double.Array)v)._dcast();

        lenV = v._length(0);
        if ( (lenU == lenV) && (u._dim() == 1) && (v._dim() == 1) )
        {
          diff = new sidl.Double.Array1(lower, upper, false);
          for (i=0; i < lenU; i++) {
            diff.set(i, du.get(i) - dv.get(i));
          }
        }
      }
    } else if (badLevel == vect.BadLevel.NullRes) {
      diff = null;
    } else if (badLevel == vect.BadLevel.TwoDRes) {
      /*
       * Cannot create 2D array so just make sure the one created 
       * is the wrong size.
       */
      //diff = new sidl.Double.Array2(lower, lower, upper, upper, false);
      diff = new sidl.Double.Array1(lower, lenU*2-1, false);
    } else if (badLevel == vect.BadLevel.WrongSizeRes) {
      diff = new sidl.Double.Array1(lower, upper+5, false);
    } else {
      diff = null;
    }

    return diff;
    // DO-NOT-DELETE splicer.end(vect.Utils.vuDiff)

  }


  // user defined non-static methods: (none)

  // DO-NOT-DELETE splicer.begin(vect.Utils._misc)
  // Nothing needed here.
  // DO-NOT-DELETE splicer.end(vect.Utils._misc)

} // end class Utils

