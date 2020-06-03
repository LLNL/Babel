// 
// File:        contractstest.java
// Copyright:   (c) 2009 Lawrence Livermore National Security, LLC
// Revision:    @(#) $Revision: 6173 $
// Date:        $Date: 2007-10-10 20:53:56 -0700 (Wed, 10 Oct 2007) $
// Description: Contract regression test case for Java calling other languages
// 

import java.lang.Exception;
import java.lang.InterruptedException;
import java.lang.Math;
import java.lang.String;

import sidl.ContractClass;
import sidl.Double.Array;
import sidl.Double.Array1;
import sidl.Double.Array2;
import sidl.EnfPolicy;
import sidl.PostViolation;
import sidl.PreViolation;
//import sidl.RuntimeException;
import sidl.SIDLException;

import synch.RegOut;
import synch.ResultType;

import vect.BadLevel;
import vect.ExpectExcept;
import vect.Utils;
import vect.vDivByZeroExcept;
import vect.vExcept;
import vect.vNegValExcept;


/**
 * The following class runs the contract regression test cases for Java.
 */
public class contractstest {
  /*
   * For testing and debugging.  Make sure set to 'false' BEFORE commit.
   */
  private final static boolean g_debug = false;   

  /*
   * ...For reporting purposes.
   */
  private final static String S_STATSFILE = "VUtils.stats";
  private final static String S_PRE       = "*** DEBUG: ";
  private final static int    S_MAX_PARTS = 128;

  /*
   * ...For driving testing.
   */
  private final static int    S_MAX_SIZE    = 6;
  private final static double S_SQRT_SIZE   = Math.sqrt((double)S_MAX_SIZE);
  private final static double S_TOL         = 1.0e-9;
  private final static double S_NTOL        = -1.0e-9;
  private final static double S_VAL         = 1.0/S_SQRT_SIZE;
  private final static double S_NVAL        = -1.0/S_SQRT_SIZE;

  /*
   * Attributes used to drive testing.
   */
  private int            d_numParts = 0;
  private int            d_partNum  = 0;
  private long           d_result   = synch.ResultType.PASS;
  private RegOut         d_tracker  = new synch.RegOut(); 
  private sidl.EnfPolicy d_policy   = new sidl.EnfPolicy();

  /*
   * Attributes associated with vector arguments.
   */
  private Array2 d_t  = new Array2(0, 0, S_MAX_SIZE-1, S_MAX_SIZE-1, false);
  private Array1 d_u  = new Array1(0, S_MAX_SIZE-1, false);
  private Array1 d_u1 = new Array1(0, S_MAX_SIZE, false);
  private Array1 d_nu = new Array1(0, S_MAX_SIZE-1, false);
  private Array1 d_z  = new Array1(0, S_MAX_SIZE-1, false);
  private Array1 d_n  = null;

  /**
   * Constructor.
   */
  public contractstest(int numParts) {
    if (numParts > 0) {
      d_numParts = numParts;

      init2DDouble(d_t, S_VAL);
      init1DDouble(d_u, S_VAL);
      init1DDouble(d_u1, S_VAL);
      init1DDouble(d_nu, S_NVAL);
      init1DDouble(d_z, 0.0);
    } else {
      System.out.println("Cannot set expectations to negative number. Assuming "
                        + d_numParts + " parts.");
    }

    d_tracker.setExpectations(d_numParts);
  } // contractstest(int): contractstest

/*
 ****************************************************************************
 *                       Support Routines
 ****************************************************************************
 */

  /**
   * Describe the test by outputing the part number and non-null description.
   */
  private void describeTest(String desc) {
    d_tracker.startPart(++d_partNum);
    if (desc != null) {
      d_tracker.writeComment(desc);
    }
  } // describeTest(String)


  /**
   * Dump enforcement statistics information.
   */
  private void dumpStats(String msg) throws InterruptedException {
/*
 * TBD:  Need to fix the Java client-side code so calls to the
 * new built-ins don't result in SIGSEGV's in the JVM.
 *
    try {
      vect.Utils._dump_stats_static(S_STATSFILE, msg);
 * OR
      vect.Utils._dump_stats(S_STATSFILE, msg);
    } catch (SIDLException exc) {
      forceFailure("Failure:  " + msg, exc);
    }
*/
  } // dumpStats(String)


  /**
   * Set enforcement option to enforce all contracts of the specified type.
   */
  private void enforceAll(long cClass, boolean b) throws InterruptedException {
    try {
      d_policy.setEnforceAll(cClass, b);
    } catch (SIDLException exc) {
      forceFailure("Failure:  Set enforcement option to " + cClass, exc);
    }
  } // enforceAll(long, boolean)


  /**
   * Evaluate exception.
   */
  private void evalExcept(SIDLException exc, long expected) {
    String myPre = S_PRE + "evalExcept: ";
    if (g_debug) {
      System.out.println(myPre + "expected=" + expected);
    }
    if (exc != null) {
      long excType = vect.ExpectExcept.NoneExp;
      try {
        if (exc.isType("sidl.PreViolation")) {
          excType = vect.ExpectExcept.PreExp;
        } else if (exc.isType("sidl.PostViolation")) {
          excType = vect.ExpectExcept.PostExp;
        } else if (exc.isType("vect.vDivByZeroExcept")) {
          excType = vect.ExpectExcept.DBZExp;
        } else if (exc.isType("vect.vNegValExcept")) {
          excType = vect.ExpectExcept.NVEExp;
        } else if (exc.isType("vect.vExcept")) {
          excType = vect.ExpectExcept.ExcExp;
        } else {
          excType = vect.ExpectExcept.NoneExp;
        }
      } catch (SIDLException exc2) {
        System.out.println(myPre + "Exception: Unknown SIDL Exception");
        excType = vect.ExpectExcept.ExcExp;
      }
      if (excType == expected) {
        if (g_debug) {
          if (excType != vect.ExpectExcept.NoneExp) {
            System.out.println(myPre + "Exception: " + excType);
          }
        }
        d_tracker.endPart(d_partNum, synch.ResultType.PASS);
      } else {
        if (g_debug) {
          if (  (excType != vect.ExpectExcept.NoneExp) 
             && (expected != vect.ExpectExcept.NoneExp))
          {
            System.out.println(myPre + "Exception: " + excType + ", expected: "
                              + expected);
          }
        }
        d_tracker.endPart(d_partNum, synch.ResultType.FAIL);
      } // endif exception matches expected
    } // endif have exception
  } // evalExcept(SIDLException, int)


  /**
   * Evaluate boolean results.
   */
  private void evalResB(boolean result, boolean expected) {
    String myPre = S_PRE + "evalResB: ";
    if (g_debug) {
      System.out.println(myPre + "result=" + result + ", expected=" + expected);
    }
    if (result == expected) {
      d_tracker.endPart(d_partNum, synch.ResultType.PASS);
    } else {
      d_tracker.endPart(d_partNum, synch.ResultType.FAIL);
    } // endif result matches expected
  } // evalResB(boolean, boolean)


  /**
   * Evaluate double results.
   */
  private void evalResD(double result, double expected, double tol) {
    String myPre = S_PRE + "evalResD: ";
    if (g_debug) {
      System.out.println(myPre + "result=" + result + ", expected=" + expected
                        + ", tol=" + tol);
    }
    if ((Math.abs(result) - Math.abs(expected)) <= Math.abs(tol)) {
      d_tracker.endPart(d_partNum, synch.ResultType.PASS);
    } else {
      d_tracker.endPart(d_partNum, synch.ResultType.FAIL);
    } // endif result within tolerance of expected
  } // evalResD(double, double, double)


  /**
   * Mark part as failure.
   */
  private void fail() {
    d_tracker.endPart(d_partNum, synch.ResultType.FAIL);
  } // fail()


  /**
   * Mark part as failure due to no exception.
   */
  private void failNoExcept(long expected) {
    String myPre = S_PRE + "failNoExcept: ";
    if (g_debug) {
      System.out.println(myPre + "expected=" + expected);
    }
    if (expected != vect.ExpectExcept.NoneExp) {
      System.out.println(myPre + "Expected an exception but none detected.");
      d_tracker.endPart(d_partNum, synch.ResultType.FAIL);
    }
  } // failNoExcept(int)


  /**
   * Finish/clean up.
   */
  private void finish() throws InterruptedException {
    d_tracker.close();

    /*
     * Try to force garbage collector to run.  (Necessary?  Why 3?)
     */
    System.gc(); Thread.sleep(50);
    System.gc(); Thread.sleep(50);
    System.gc(); Thread.sleep(50);
  } // finish()


  /*
   * Force failure of the regression test suite, printing message 
   * and exception information IF provided.
   */
  public void forceFailure(String msg, Exception exc) 
    throws InterruptedException
  {
    if (msg != null) {
      System.out.println(msg);
    }
    System.out.println("TEST_RESULT FAIL");
    finish();
    if (exc != null) {
      System.out.println(exc.toString());
      exc.printStackTrace();
    }
    System.exit(1);
  } // forceFailure(String, Exception)


  /**
   * Initialize all entries of specified array with given value.
   */
  private void init1DDouble(Array1 v, double val) {
    if (v != null) {
      int max = v._upper(0);
      for (int i = v._lower(0); i <= max; ++i) {
        v.set(i, val);
      }
    }
  } // initDouble(Array1, double)


  /**
   * Initialize all entries of specified 2-D array with given value.
   */
  private void init2DDouble(Array2 m, double val) {
    if (m != null) {
      int max1 = m._upper(0);
      int max2 = m._upper(1);
      for (int i = m._lower(0); i <= max1; ++i) {
        for (int j = m._lower(1); j <= max2; ++j) {
          m.set(i, j, val);
        }
      }
    }
  } // init2DDouble(Array2, double)


/*
 ****************************************************************************
 *                       Test Routines
 ****************************************************************************
 */
  private void runIsZero(Array v, double tol, boolean res, 
                         long expectExc, String desc) 
    throws InterruptedException
  {
    describeTest(desc);
    try {
      boolean x = vect.Utils.vuIsZero(v, tol);
      if (expectExc == vect.ExpectExcept.NoneExp) {
        evalResB(x, res);
      } else {
        failNoExcept(expectExc);
      }
    } catch (SIDLException exc) {
      evalExcept(exc, expectExc);
    }

    /*
     * Try to force garbage collector to run.
     */
    System.gc(); Thread.sleep(5);
  } // runIsZero(Array1, double, boolean, int, String)


  private void runIsUnit(Array v, double tol, boolean res, 
                         long expectExc, String desc) 
    throws InterruptedException
  {
    describeTest(desc);
    try {
      boolean x = vect.Utils.vuIsUnit(v, tol);
      if (expectExc == vect.ExpectExcept.NoneExp) {
        evalResB(x, res);
      } else {
        failNoExcept(expectExc);
      }
    } catch (SIDLException exc) {
      evalExcept(exc, expectExc);
    }

    /*
     * Try to force garbage collector to run.
     */
    System.gc(); Thread.sleep(5);
  } // runIsUnit(Array, double, boolean, int, String)


  private void runAreEqual(Array u, Array v, double tol, boolean res, 
                           long expectExc, String desc) 
    throws InterruptedException
  {
    describeTest(desc);
    try {
      boolean x = vect.Utils.vuAreEqual(u, v, tol);
      if (expectExc == vect.ExpectExcept.NoneExp) {
        evalResB(x, res);
      } else {
        failNoExcept(expectExc);
      }
    } catch (SIDLException exc) {
      evalExcept(exc, expectExc);
    }

    /*
     * Try to force garbage collector to run.
     */
    System.gc(); Thread.sleep(5);
  } // runAreEqual(Array, Array, double, boolean, int, String)


  private void runAreOrthogonal(Array u, Array v, double tol, boolean res, 
                                long expectExc, String desc) 
    throws InterruptedException
  {
    describeTest(desc);
    try {
      boolean x = vect.Utils.vuAreOrth(u, v, tol);
      if (expectExc == vect.ExpectExcept.NoneExp) {
        evalResB(x, res);
      } else {
        failNoExcept(expectExc);
      }
    } catch (SIDLException exc) {
      evalExcept(exc, expectExc);
    }

    /*
     * Try to force garbage collector to run.
     */
    System.gc(); Thread.sleep(5);
  } // runAreOrthogonal(Array, Array, double, boolean, int, String)


  private void runSchwarzHolds(Array u, Array v, double tol, boolean res, 
                               long expectExc, String desc) 
    throws InterruptedException
  {
    describeTest(desc);
    try {
      boolean x = vect.Utils.vuSchwarzHolds(u, v, tol);
      if (expectExc == vect.ExpectExcept.NoneExp) {
        evalResB(x, res);
      } else {
        failNoExcept(expectExc);
      }
    } catch (SIDLException exc) {
      evalExcept(exc, expectExc);
    }

    /*
     * Try to force garbage collector to run.
     */
    System.gc(); Thread.sleep(5);
  } // runSchwarzHolds(Array, Array, double, boolean, int, String)


  private void runTriangleInequalityHolds(Array u, Array v, double tol, 
                                          boolean res, 
                                          long expectExc, 
                                          String desc) 
    throws InterruptedException
  {
    describeTest(desc);
    try {
      boolean x = vect.Utils.vuTriIneqHolds(u, v, tol);
      if (expectExc == vect.ExpectExcept.NoneExp) {
        evalResB(x, res);
      } else {
        failNoExcept(expectExc);
      }
    } catch (SIDLException exc) {
      evalExcept(exc, expectExc);
    }

    /*
     * Try to force garbage collector to run.
     */
    System.gc(); Thread.sleep(5);
  } // runTriangleInequalityHolds(Array, Array, double, boolean, int, String)


  private void runNorm(Array u, double tol, long badLvl, double res, 
                       long expectExc, String desc) 
    throws InterruptedException
  {
    describeTest(desc);
    try {
      double x = vect.Utils.vuNorm(u, tol, badLvl);
      if (expectExc == vect.ExpectExcept.NoneExp) {
        evalResD(x, res, tol);
      } else {
        failNoExcept(expectExc);
      }
    } catch (SIDLException exc) {
      evalExcept(exc, expectExc);
    }

    /*
     * Try to force garbage collector to run.
     */
    System.gc(); Thread.sleep(5);
  } // runNorm(Array, double, int, double, int, String)


  private void runDot(Array u, Array v, double tol, long badLvl, double res, 
                      long expectExc, String desc) 
    throws InterruptedException
  {
    describeTest(desc);
    try {
      double x = vect.Utils.vuDot(u, v, tol, badLvl);
      if (expectExc == vect.ExpectExcept.NoneExp) {
        evalResD(x, res, tol);
      } else {
        failNoExcept(expectExc);
      }
    } catch (SIDLException exc) {
      evalExcept(exc, expectExc);
    }

    /*
     * Try to force garbage collector to run.
     */
    System.gc(); Thread.sleep(5);
  } // runDot(Array, Array, double, int, double, int, String)


  private void runProduct(double a, Array u, double tol, long badLvl, 
                          Array res, boolean okay, long expectExc,
                          String desc) 
    throws InterruptedException
  {
    describeTest(desc);
    boolean ok = false;
    try {
      gov.llnl.sidl.BaseArray x = vect.Utils.vuProduct(a, u, badLvl);
      if (x != null) {
        ok = vect.Utils.vuAreEqual(x, res, tol);
      } else { /* x == null */
        ok = (res != null) ? false : true;
      }
      if (expectExc == vect.ExpectExcept.NoneExp) {
        evalResB(ok, okay);
      } else {
        failNoExcept(expectExc);
      }
    } catch (SIDLException exc) {
      evalExcept(exc, expectExc);
    }

    /*
     * Try to force garbage collector to run.
     */
    System.gc(); Thread.sleep(5);
  } // runProduct(double, Array, double, int, Array, boolean, int, String)


  private void runNegate(Array u, double tol, long badLvl, Array res, 
                         boolean okay, long expectExc, String desc)
    throws InterruptedException
  {
    describeTest(desc);
    boolean ok = false;
    try {
      gov.llnl.sidl.BaseArray x = vect.Utils.vuNegate(u, badLvl);
      if (x != null) {
        ok = vect.Utils.vuAreEqual(x, res, tol);
      } else { /* x == null */
        ok = (res != null) ? false : true;
      }
      if (expectExc == vect.ExpectExcept.NoneExp) {
        evalResB(ok, okay);
      } else {
        failNoExcept(expectExc);
      }
    } catch (SIDLException exc) {
      evalExcept(exc, expectExc);
    }

    /*
     * Try to force garbage collector to run.
     */
    System.gc(); Thread.sleep(5);
  } // runNegate(Array, double, int, Array, boolean, int, String)


  private void runNormalize(Array u, double tol, long badLvl, Array res, 
                            boolean okay, long expectExc, 
                            String desc) 
    throws InterruptedException
  {
    describeTest(desc);
    boolean ok = false;
    String pre = "runNormalize:  ";
    try {
      gov.llnl.sidl.BaseArray x = vect.Utils.vuNormalize(u, tol, badLvl);
      if (x != null) {
        if (g_debug) {
          System.out.println(pre + "x != null");
        }
        ok = vect.Utils.vuAreEqual(x, res, tol);
      } else { /* x == null */
        ok = (res != null) ? false : true;
      }
      if (g_debug) {
        System.out.println(pre + "ok=" + ok + " (vs. okay=" + okay + ")");
      }
      if (expectExc == vect.ExpectExcept.NoneExp) {
        evalResB(ok, okay);
      } else {
        failNoExcept(expectExc);
      }
    } catch (SIDLException exc) {
      evalExcept(exc, expectExc);
    }

    /*
     * Try to force garbage collector to run.
     */
    System.gc(); Thread.sleep(5);
  } // runNormalize(Array, double, int, Array, boolean, int, String)


  private void runSum(Array u, Array v, double tol, long badLvl, Array res, 
                      boolean okay, long expectExc, String desc) 
    throws InterruptedException
  {
    describeTest(desc);
    boolean ok = false;
    try {
      gov.llnl.sidl.BaseArray x = vect.Utils.vuSum(u, v, badLvl);
      if (x != null) {
        ok = vect.Utils.vuAreEqual(x, res, tol);
      } else { /* x == null */
        ok = (res != null) ? false : true;
      }
      if (expectExc == vect.ExpectExcept.NoneExp) {
        evalResB(ok, okay);
      } else {
        failNoExcept(expectExc);
      }
    } catch (SIDLException exc) {
      evalExcept(exc, expectExc);
    }

    /*
     * Try to force garbage collector to run.
     */
    System.gc(); Thread.sleep(5);
  } // runSum(Array, Array, double, int, Array, boolean, int, String)


  private void runDiff(Array u, Array v, double tol, long badLvl, Array res, 
                       boolean okay, long expectExc, String desc) 
    throws InterruptedException
  {
    describeTest(desc);
    boolean ok = false;
    try {
      gov.llnl.sidl.BaseArray x = vect.Utils.vuDiff(u, v, badLvl);
      if (x != null) {
        ok = vect.Utils.vuAreEqual(x, res, tol);
      } else { /* x == null */
        ok = (res != null) ? false : true;
      }
      if (expectExc == vect.ExpectExcept.NoneExp) {
        evalResB(ok, okay);
      } else {
        failNoExcept(expectExc);
      }
    } catch (SIDLException exc) {
      evalExcept(exc, expectExc);
    }

    /*
     * Try to force garbage collector to run.
     */
    System.gc(); Thread.sleep(5);
  } // runDiff(Array, Array, double, int, Array, boolean, int, String)


/*
 ****************************************************************************
 */
  private void run() throws InterruptedException {
    d_tracker.writeComment("*** ENABLE FULL CONTRACT CHECKING ***");
    enforceAll(sidl.ContractClass.ALLCLASSES, true);

    /* vuIsZero() set */
    runIsZero(d_z, S_TOL, true, vect.ExpectExcept.NoneExp,
              "ensuring the zero vector is the zero vector");
    runIsZero(d_u, S_TOL, false, vect.ExpectExcept.NoneExp,
              "ensuring the unit vector is not the zero vector");
    runIsZero(d_n, S_TOL, false, vect.ExpectExcept.PreExp,
              "passing vuIsZero() a null array");
    runIsZero(d_t, S_TOL, false, vect.ExpectExcept.PreExp,
              "passing vuIsZero() a 2D array");
    runIsZero(d_z, S_NTOL, false, vect.ExpectExcept.PreExp,
              "passing vuIsZero() a negative tolerance");

    /* vuIsUnit() set */
    runIsUnit(d_u, S_TOL, true, vect.ExpectExcept.NoneExp,
              "ensuring the unit vector is the unit vector");
    runIsUnit(d_z, S_TOL, false, vect.ExpectExcept.NoneExp,
              "ensuring the zero vector is not the unit vector");
    runIsUnit(d_n, S_TOL, false, vect.ExpectExcept.PreExp,
              "passing vuIsUnit() a null array");
    runIsUnit(d_t, S_TOL, false, vect.ExpectExcept.PreExp,
              "passing vuIsUnit() a 2D array");
    runIsUnit(d_u, S_NTOL, false, vect.ExpectExcept.PreExp,
              "passing vuIsUnit() a negative tolerance");

    /* vuAreEqual() set */
    runAreEqual(d_u, d_z, S_TOL, false, vect.ExpectExcept.NoneExp,
                "ensuring the unit and zero vectors are not equal");
    runAreEqual(d_u, d_u, S_TOL, true, vect.ExpectExcept.NoneExp,
                "ensuring the unit vector is equal to itself");
    runAreEqual(d_n, d_u, S_TOL, false, vect.ExpectExcept.PreExp,
                "passing vuAreEqual() a null 1st array");
    runAreEqual(d_t, d_u, S_TOL, false, vect.ExpectExcept.PreExp,
                "passing vuAreEqual() a 2D 1st array");
    runAreEqual(d_u, d_n, S_TOL, false, vect.ExpectExcept.PreExp,
                "passing vuAreEqual() a null 2nd array");
    runAreEqual(d_u, d_t, S_TOL, false, vect.ExpectExcept.PreExp,
                "passing vuAreEqual() a 2D 2nd array");
    runAreEqual(d_u, d_u1, S_TOL, false, vect.ExpectExcept.PreExp,
                "passing vuAreEqual() different sized arrays");
    runAreEqual(d_u, d_u, S_NTOL, false, vect.ExpectExcept.PreExp,
                "passing vuAreEqual() a negative tolerance");

    /* vuAreOrth() set */
    runAreOrthogonal(d_u, d_z, S_TOL, true, vect.ExpectExcept.NoneExp,
     "ensuring the unit and zero vectors are orthogonal");
    runAreOrthogonal(d_u, d_nu, S_TOL, false, vect.ExpectExcept.NoneExp,
     "ensuring the unit and negative unit vectors are not orthogonal");
    runAreOrthogonal(d_n, d_u, S_TOL, false, vect.ExpectExcept.PreExp,
     "passing vuAreOrth() a null 1st array");
    runAreOrthogonal(d_t, d_u, S_TOL, false, vect.ExpectExcept.PreExp,
     "passing vuAreOrth() a 2D 1st array");
    runAreOrthogonal(d_u, d_n, S_TOL, false, vect.ExpectExcept.PreExp,
     "passing vuAreOrth() a null 2nd array");
    runAreOrthogonal(d_u, d_t, S_TOL, false, vect.ExpectExcept.PreExp,
     "passing vuAreOrth() a 2D 2nd array");
    runAreOrthogonal(d_u, d_u1, S_TOL, false, vect.ExpectExcept.PreExp,
     "passing vuAreOrth() different sized unit arrays");
    runAreOrthogonal(d_u, d_u, S_NTOL, false, vect.ExpectExcept.PreExp,
     "passing vuAreOrth() a negative tolerance");
    runAreOrthogonal(d_t, d_t, S_TOL, false, vect.ExpectExcept.PreExp,
     "passing vuAreOrth() 2D arrays in both arguments");

    /* vuSchwarzHolds() set */
    runSchwarzHolds(d_u, d_z, S_TOL, true, vect.ExpectExcept.NoneExp,
     "ensuring schwarz holds for the unit and zero vectors");
    runSchwarzHolds(d_n, d_z, S_TOL, false, vect.ExpectExcept.PreExp,
     "passing vuSchwarzHolds() a null 1st array");
    runSchwarzHolds(d_t, d_z, S_TOL, false, vect.ExpectExcept.PreExp,
     "passing vuSchwarzHolds() a 2D 1st array");
    runSchwarzHolds(d_z, d_n, S_TOL, false, vect.ExpectExcept.PreExp,
     "passing vuSchwarzHolds() a null 2nd array");
    runSchwarzHolds(d_u, d_t, S_TOL, false, vect.ExpectExcept.PreExp,
     "passing vuSchwarzHolds() a 2D 2nd array");
    runSchwarzHolds(d_u, d_u1, S_TOL, false, vect.ExpectExcept.PreExp,
     "passing vuSchwarzHolds() different sized unit arrays");
    runSchwarzHolds(d_u, d_z, S_NTOL, false, vect.ExpectExcept.PreExp,
     "passing vuSchwarzHolds() a negative tolerance");

    /* vuTriIneqHolds() set */
    runTriangleInequalityHolds(d_u, d_z, S_TOL, true, vect.ExpectExcept.NoneExp,
     "ensuring the triangle inequality holds for the unit and zero vectors");
    runTriangleInequalityHolds(d_n, d_u, S_TOL, false, vect.ExpectExcept.PreExp,
     "passing vuTriIneqHolds() a null 1st array");
    runTriangleInequalityHolds(d_t, d_u, S_TOL, false, vect.ExpectExcept.PreExp,
     "passing vuTriIneqHolds() a 2D 1st array");
    runTriangleInequalityHolds(d_u, d_n, S_TOL, false, vect.ExpectExcept.PreExp,
     "passing vuTriIneqHolds() a null 2nd array");
    runTriangleInequalityHolds(d_u, d_t, S_TOL, false, vect.ExpectExcept.PreExp,
     "passing vuTriIneqHolds() a 2D 2nd array");
    runTriangleInequalityHolds(d_u, d_u1, S_TOL, false, 
     vect.ExpectExcept.PreExp,
     "passing vuTriIneqHolds() different sized unit vectors");
    runTriangleInequalityHolds(d_u, d_u, S_NTOL, false, 
     vect.ExpectExcept.PreExp,
     "passing vuTriIneqHolds() a negative tolerance");

    /* vuNorm() set */
    runNorm(d_u, S_TOL, vect.BadLevel.NoVio, 1.0, vect.ExpectExcept.NoneExp,
     "ensuring the unit vector norm is 1.0");
    runNorm(d_n, S_TOL, vect.BadLevel.NoVio, 0.0, vect.ExpectExcept.PreExp,
     "passing vuNorm() a null vector");
    runNorm(d_t, S_TOL, vect.BadLevel.NoVio, 0.0, vect.ExpectExcept.PreExp,
     "passing vuNorm() a 2D array");
    runNorm(d_u, S_NTOL, vect.BadLevel.NoVio, 0.0, vect.ExpectExcept.PreExp,
     "passing vuNorm() a negative tolerance");
    runNorm(d_u, S_TOL, vect.BadLevel.NegRes, -5.0, vect.ExpectExcept.PostExp,
     "passing vuNorm() badness level for negative result");
    runNorm(d_z, S_TOL, vect.BadLevel.PosRes, 5.0, vect.ExpectExcept.PostExp,
    "passing vuNorm() badness level for positive result with zero vector");
    runNorm(d_u, S_TOL, vect.BadLevel.ZeroRes, 0.0, vect.ExpectExcept.PostExp,
    "passing vuNorm() badness level for zero result with non-zero vector");

    /* vuDot() set */
    runDot(d_u, d_z, S_TOL, vect.BadLevel.NoVio, 0.0, vect.ExpectExcept.NoneExp,
     "ensuring the dot of the unit and zero vectors is 0.0");
    runDot(d_n, d_u, S_TOL, vect.BadLevel.NoVio, 0.0, vect.ExpectExcept.PreExp,
     "passing vuDot() a null 1st array");
    runDot(d_t, d_u, S_TOL, vect.BadLevel.NoVio, 0.0, vect.ExpectExcept.PreExp,
     "passing vuDot() a 2D 1st array");
    runDot(d_u, d_n, S_TOL, vect.BadLevel.NoVio, 0.0, vect.ExpectExcept.PreExp,
     "passing vuDot() a null 2nd array");
    runDot(d_u, d_t, S_TOL, vect.BadLevel.NoVio, 0.0, vect.ExpectExcept.PreExp,
     "passing vuDot() a 2D 2nd array");
    runDot(d_u, d_u1, S_TOL, vect.BadLevel.NoVio, 0.0, vect.ExpectExcept.PreExp,
     "passing vuDot() different sized unit vectors");
    runDot(d_u, d_u, S_NTOL, vect.BadLevel.NoVio, 0.0, vect.ExpectExcept.PreExp,
     "passing vuDot() a negative tolerance");
    runDot(d_t, d_t, S_TOL, vect.BadLevel.NoVio, 0.0, vect.ExpectExcept.PreExp,
     "passing vuDot() a 2D arrays in both arguments");
    runDot(d_u, d_u, S_TOL, vect.BadLevel.NegRes, -5.0, 
     vect.ExpectExcept.PostExp,
     "passing vuDot() badness level for negative result with u=v");
    runDot(d_z, d_z, S_TOL, vect.BadLevel.PosRes, 5.0, 
     vect.ExpectExcept.PostExp,
     "passing vuDot() badness level for positive result with u=v=0");

    /* vuProduct() set */
    runProduct(1.0, d_u, S_TOL, vect.BadLevel.NoVio, d_u, true, 
     vect.ExpectExcept.NoneExp,
     "ensuring the product of 1 and the unit vector is the unit vector");
    runProduct(2.0, d_u, S_TOL, vect.BadLevel.NoVio, d_u, false, 
               vect.ExpectExcept.NoneExp,
     "ensuring the product of 2 and the unit vector is not the unit vector");
    runProduct(0.0, d_n, S_TOL, vect.BadLevel.NoVio, d_n, true, 
     vect.ExpectExcept.PreExp,
     "passing vuProduct() a null array");
    runProduct(1.0, d_t, S_TOL, vect.BadLevel.NoVio, d_u, false, 
     vect.ExpectExcept.PreExp,
     "passing vuProduct() a 2D array");
    runProduct(1.0, d_u, S_TOL, vect.BadLevel.NullRes, d_u, true, 
     vect.ExpectExcept.PostExp,
     "passing vuProduct() badness level for null result");
    runProduct(1.0, d_u, S_TOL, vect.BadLevel.TwoDRes, d_u, true, 
     vect.ExpectExcept.PostExp,
     "passing vuProduct() badness level for 2D result");
    runProduct(1.0, d_u, S_TOL, vect.BadLevel.WrongSizeRes, d_u, true, 
     vect.ExpectExcept.PostExp,
     "passing vuProduct() badness level for wrong size result");

    /* vuNegate() set */
    runNegate(d_u, S_TOL, vect.BadLevel.NoVio, d_nu, true, 
     vect.ExpectExcept.NoneExp,
     "ensuring the negation of the unit vector is its negative");
    runNegate(d_u, S_TOL, vect.BadLevel.NoVio, d_u, false, 
     vect.ExpectExcept.NoneExp,
     "ensuring the negation of the unit vector is not the unit vector");
    runNegate(d_n, S_TOL, vect.BadLevel.NoVio, d_nu, true, 
     vect.ExpectExcept.PreExp,
     "passing vuNegate() a null array");
    runNegate(d_t, S_TOL, vect.BadLevel.NoVio, d_nu, false, 
     vect.ExpectExcept.PreExp,
     "passing vuNegate() a 2D array");
    runNegate(d_u, S_TOL, vect.BadLevel.NullRes, d_nu, true, 
     vect.ExpectExcept.PostExp,
     "passing vuNegate() badness level for null result");
    runNegate(d_u, S_TOL, vect.BadLevel.TwoDRes, d_nu, true, 
     vect.ExpectExcept.PostExp,
     "passing vuNegate() badness level for 2D result");
    runNegate(d_u, S_TOL, vect.BadLevel.WrongSizeRes, d_nu, true, 
     vect.ExpectExcept.PostExp,
     "passing vuNegate() badness level for wrong size result");

    /* vuNormalize() set */
    runNormalize(d_u, S_TOL, vect.BadLevel.NoVio, d_u, true, 
     vect.ExpectExcept.NoneExp,
     "ensuring normalize of the unit vector is itself");
    runNormalize(d_u, S_TOL, vect.BadLevel.NoVio, d_nu, false, 
     vect.ExpectExcept.NoneExp,
     "ensuring normalize of the unit vector is not its negative");
    runNormalize(d_z, S_TOL, vect.BadLevel.NoVio, d_z, true, 
     vect.ExpectExcept.DBZExp,
     "ensuring normalize of the zero vector raises a DBZ exception");
    runNormalize(d_n, S_TOL, vect.BadLevel.NoVio, d_n, true, 
     vect.ExpectExcept.PreExp,
     "passing vuNormalize() a null array");
    runNormalize(d_t, S_TOL, vect.BadLevel.NoVio, d_u, false, 
     vect.ExpectExcept.PreExp,
     "passing vuNormalize() a 2D array");
    runNormalize(d_u, S_NTOL, vect.BadLevel.NoVio, d_u, true, 
     vect.ExpectExcept.PreExp,
     "passing vuNormalize() a negative tolerance using the unit vector");
    runNormalize(d_u, S_TOL, vect.BadLevel.NullRes, d_u, true, 
     vect.ExpectExcept.PostExp,
     "passing vuNormalize() a badness level for null result");
    runNormalize(d_u, S_TOL, vect.BadLevel.TwoDRes, d_u, true, 
     vect.ExpectExcept.PostExp,
     "passing vuNormalize() a badness level for 2D result");
    runNormalize(d_u, S_TOL, vect.BadLevel.WrongSizeRes, d_u, true, 
     vect.ExpectExcept.PostExp,
     "passing vuNormalize() a badness level for wrong size result");

    /* vuSum() set (NOTE: tolerance not relevant to vuSum() API.) */
    runSum(d_u, d_z, S_TOL, vect.BadLevel.NoVio, d_u, true, 
     vect.ExpectExcept.NoneExp,
     "ensuring the sum of the unit and zero vectors is the unit vector");
    runSum(d_u, d_z, S_TOL, vect.BadLevel.NoVio, d_nu, false, 
     vect.ExpectExcept.NoneExp,
     "ensuring the sum of unit and zero vectors is not the negative of the "
     + "unit");
    runSum(d_n, d_z, S_TOL, vect.BadLevel.NoVio, d_n, true, 
     vect.ExpectExcept.PreExp,
     "passing vuSum() a null 1st array");
    runSum(d_t, d_n, S_TOL, vect.BadLevel.NoVio, d_n, false, 
     vect.ExpectExcept.PreExp,
     "passing vuSum() a 2D 1st array");
    runSum(d_u, d_n, S_TOL, vect.BadLevel.NoVio, d_n, true, 
     vect.ExpectExcept.PreExp,
     "passing vuSum() a null 2nd array");
    runSum(d_u, d_t, S_TOL, vect.BadLevel.NoVio, d_n, false, 
     vect.ExpectExcept.PreExp,
     "passing vuSum() a 2D as second");
    runSum(d_u, d_u1, S_TOL, vect.BadLevel.NoVio, d_z, true, 
     vect.ExpectExcept.PreExp,
     "passing vuSum() different sized unit vectors");
    runSum(d_u, d_z, S_TOL, vect.BadLevel.NullRes, d_u, true, 
     vect.ExpectExcept.PostExp,
     "passing vuSum() badness level for null result");
    runSum(d_u, d_z, S_TOL, vect.BadLevel.TwoDRes, d_u, true, 
     vect.ExpectExcept.PostExp,
     "passing vuSum() badness level for 2D result");
    runSum(d_u, d_z, S_TOL, vect.BadLevel.WrongSizeRes, d_u, true, 
     vect.ExpectExcept.PostExp,
     "passing vuSum() badness level for wrong size result");

    /* vuDiff() set (NOTE: tolerance not relevant to vuDiff() API.) */
    runDiff(d_z, d_u, S_TOL, vect.BadLevel.NoVio, d_nu, true, 
     vect.ExpectExcept.NoneExp,
     "ensuring the diff of the zero and unit vectors is the negative unit "
     + "vector");
    runDiff(d_u, d_z, S_TOL, vect.BadLevel.NoVio, d_u, true, 
     vect.ExpectExcept.NoneExp,
     "ensuring the diff of the unit and zero vectors is the unit vector");
    runDiff(d_u, d_z, S_TOL, vect.BadLevel.NoVio, d_nu, false, 
     vect.ExpectExcept.NoneExp,
     "ensuring the diff of the unit and zero vectors is not the neg unit "
     + "vector");
    runDiff(d_n, d_u, S_TOL, vect.BadLevel.NoVio, d_n, true, 
     vect.ExpectExcept.PreExp,
     "passing vuDiff() a null 1st array");
    runDiff(d_t, d_u, S_TOL, vect.BadLevel.NoVio, d_u, false, 
     vect.ExpectExcept.PreExp,
     "passing vuDiff() a 2D 1st array");
    runDiff(d_u, d_n, S_TOL, vect.BadLevel.NoVio, d_n, true, 
     vect.ExpectExcept.PreExp,
     "passing vuDiff() a null 2nd array");
    runDiff(d_u, d_t, S_TOL, vect.BadLevel.NoVio, d_u, false, 
     vect.ExpectExcept.PreExp,
     "passing vuDiff() a 2D 2nd array");
    runDiff(d_u, d_u1, S_TOL, vect.BadLevel.NoVio, d_u, true, 
     vect.ExpectExcept.PreExp,
     "passing vuDiff() different sized vectors");
    runDiff(d_z, d_u, S_TOL, vect.BadLevel.NullRes, d_nu, true, 
     vect.ExpectExcept.PostExp,
     "passing vuDiff() badness level for null result");
    runDiff(d_z, d_u, S_TOL, vect.BadLevel.TwoDRes, d_nu, true, 
     vect.ExpectExcept.PostExp,
     "passing vuDiff() badness level for 2D result");
    runDiff(d_z, d_u, S_TOL, vect.BadLevel.WrongSizeRes, d_nu, true, 
     vect.ExpectExcept.PostExp,
     "passing vuDiff() badness level for wrong size result");

    dumpStats("After full checking");

    /****************************************************************
     * Now check preconditions only.  Only need three checks:
     *   1) successful execution;
     *   2) precondition violation that is not caught but is
     *      okay anyway; and
     *   3) postcondition violation that is caught.
     ****************************************************************/
    d_tracker.writeComment("*** ENABLE PRECONDITION ENFORCEMENT ONLY ***");
    enforceAll(sidl.ContractClass.PRECONDS, false);

    runDot(d_u, d_z, S_TOL, vect.BadLevel.NoVio, 0.0, vect.ExpectExcept.NoneExp,
     "ensuring the dot product of the unit and zero vectors is 0.0");
    runDot(d_u, d_u, S_NTOL, vect.BadLevel.NoVio, 1.0, vect.ExpectExcept.PreExp,
     "passing vuDot() a negative tolerance");
    runDot(d_u, d_u, S_TOL, vect.BadLevel.NegRes, -5.0, 
     vect.ExpectExcept.NoneExp,
     "passing vuDot() badness level for negative result with u=v");

    dumpStats("After precondition checking");

    /****************************************************************
     * Now check postconditions only.  Only need three checks:
     *   1) successful execution;
     *   2) precondition violation that gets caught; and
     *   3) postcondition violation that is not caught.
     ****************************************************************/
    d_tracker.writeComment("*** ENABLE POSTCONDITION ENFORCEMENT ONLY ***");
    enforceAll(sidl.ContractClass.POSTCONDS, false);

    runDot(d_u, d_z, S_TOL, vect.BadLevel.NoVio, 0.0, vect.ExpectExcept.NoneExp,
     "ensuring the dot product of the unit and zero vectors is 0.0");
    runDot(d_u, d_u, S_NTOL, vect.BadLevel.NoVio, 1.0, 
     vect.ExpectExcept.NoneExp,
     "passing vuDot() a negative tolerance");
    runDot(d_u, d_u, S_TOL, vect.BadLevel.NegRes, -5.0, 
     vect.ExpectExcept.PostExp,
     "passing vuDot() badness level for negative result with u=v");

    dumpStats("After Postcondition checking");

    /****************************************************************
     * Now make sure contract violations are not caught when contract
     * enforcement turned off.  Do this for each type of violation
     * for every method.
     ****************************************************************/
    d_tracker.writeComment("*** DISABLE ALL CONTRACT ENFORCEMENT ***");
    try {
      d_policy.setEnforceNone(false);
    } catch (SIDLException exc) {
      forceFailure("Failure:  Set enforcement option to disable checking",
                   exc);
    }

    runIsZero(d_n, S_TOL, false, vect.ExpectExcept.NoneExp,
      "passing vuIsZero() a null array - no precondition violation");
    runIsUnit(d_n, S_TOL, false, vect.ExpectExcept.NoneExp,
      "passing vuIsUnit() a null array - no precondition violation");
    runAreEqual(d_n, d_u, S_TOL, false, vect.ExpectExcept.NoneExp,
     "passing vuAreEqual() a null 1st array - no precondition violation");
    runAreOrthogonal(d_n, d_u, S_TOL, false, vect.ExpectExcept.NoneExp,
     "passing vuAreOrth() a null 1st array - no precondition violation");
    runSchwarzHolds(d_n, d_z, S_TOL, false, vect.ExpectExcept.NoneExp,
     "passing vuSchwarzHolds() a null 1st array - no precondition violation");
    runTriangleInequalityHolds(d_n, d_u, S_TOL, false, 
     vect.ExpectExcept.NoneExp,
     "passing() vuTriIneqHolds() a null 1st array - no precondition violation");
    runNorm(d_n, S_TOL, vect.BadLevel.NoVio, 0.0, vect.ExpectExcept.NoneExp,
     "passing() vuNorm() a null vector - no precondition violation");
    runNorm(d_u, S_TOL, vect.BadLevel.NegRes, -5.0, vect.ExpectExcept.NoneExp,
     "passing vuNorm() badness level for negative result - no post "
     + "violation");
    runDot(d_n, d_u, S_TOL, vect.BadLevel.NoVio, 0.0, vect.ExpectExcept.NoneExp,
     "passing vuDot() a null 1st array - no precondition violation");
    runDot(d_u, d_u, S_TOL, vect.BadLevel.NegRes, -5.0, 
     vect.ExpectExcept.NoneExp,
     "passing vuDot() badness level for negative result with u=v - no post "
     + "vio.");
    runProduct(0.0, d_n, S_TOL, vect.BadLevel.NoVio, d_n, true, 
     vect.ExpectExcept.NoneExp,
     "passing vuProduct() a null array - no precondition violation");
    runProduct(1.0, d_u, S_TOL, vect.BadLevel.NullRes, d_u, false, 
     vect.ExpectExcept.NoneExp,
     "passing vuProduct() badness level for null result - no post violation");
    runNegate(d_n, S_TOL, vect.BadLevel.NoVio, d_n, true, 
     vect.ExpectExcept.NoneExp,
     "passing vuNegate() a null array - no precondition violation");
    runNegate(d_u, S_TOL, vect.BadLevel.NullRes, d_nu, false, 
     vect.ExpectExcept.NoneExp,
     "passing vuNegate() badness level for null result - no post violation");
    runNormalize(d_n, S_TOL, vect.BadLevel.NoVio, d_n, true, 
     vect.ExpectExcept.NoneExp,
     "passing vuNormalize() a null array - no precondition violation");
    runNormalize(d_u, S_TOL, vect.BadLevel.NullRes, d_u, false, 
     vect.ExpectExcept.NoneExp,
     "passing vuNormalize() a badness level for null result - no post "
     + "violation");
    runSum(d_n, d_z, S_TOL, vect.BadLevel.NoVio, d_n, true, 
     vect.ExpectExcept.NoneExp,
     "passing vuSum() a null 1st array - no precondition violation");
    runSum(d_u, d_z, S_TOL, vect.BadLevel.NullRes, d_u, false, 
     vect.ExpectExcept.NoneExp,
     "passing vuSum() a badness level for null result - no post violation");
    runDiff(d_n, d_u, S_TOL, vect.BadLevel.NoVio, d_n, true, 
     vect.ExpectExcept.NoneExp,
     "passing vuDiff() a null 1st array - no precondition violation");
    runDiff(d_z, d_u, S_TOL, vect.BadLevel.NullRes, d_nu, false, 
     vect.ExpectExcept.NoneExp,
     "passing vuDiff() badness level for null result - no post violation");

    dumpStats("After no checking");

    d_tracker.close();
  } // run()


/*
 ****************************************************************************
 ****************************************************************************
 *                                  MAIN
 ****************************************************************************
 ****************************************************************************
 */
  public static void main(String args[]) {
    contractstest driver = new contractstest(S_MAX_PARTS);
    try {
      driver.run();

      System.gc(); Thread.sleep(5); // try to force garbage collector to run
      System.gc(); Thread.sleep(5); // try to force garbage collector to run
    } catch (Exception exc) {
      /*
       * Catch any unexpected exceptions and return a test failure
       */
      try {
        driver.forceFailure("", exc);
      } catch (InterruptedException iexc) {
        System.err.println("Forcing failure resulted in an exception.");
        iexc.printStackTrace();
      }
    }

    Runtime.getRuntime().exit(0); /* workaround for Linux JVM 1.3.1 bug */
  } // main(String)
} // contractstest

