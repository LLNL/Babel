// 
// File:        knapsacktest.java
// Copyright:   (c) 2011 Lawrence Livermore National Security, LLC
// Revision:    @(#) $Revision: 6173 $
// Date:        $Date: 2011-01-05 20:53:56 -0700 (Wed, 05 Jan 2011) $
// Description: Java invariants regression tests 
// 

import java.lang.Exception;
import java.lang.InterruptedException;
import java.lang.Math;
import java.lang.String;

import sidl.ContractClass;
import sidl.Integer.Array1;
import sidl.EnfPolicy;
import sidl.InvViolation;
import sidl.PostViolation;
import sidl.PreViolation;
import sidl.SIDLException;

import synch.RegOut;
import synch.ResultType;

import knapsack.ExpectExcept;
import knapsack.gKnapsack;
import knapsack.iKnapsack;
import knapsack.kExcept;
import knapsack.kBadWeightExcept;
import knapsack.kSizeExcept;
import knapsack.npKnapsack;
import knapsack.nwKnapsack;


/**
 * The following class runs the contract regression test cases for Java.
 */
public class invariantstest {
  /*
   * For testing and debugging.  Make sure set to 'false' BEFORE commit.
   */
  private final static boolean g_debug = false;   

  /*
   * ...For reporting purposes.
   */
  private final static String S_PRE       = "*** DEBUG: ";
  private final static int    S_MAX_PARTS = 81;

  /*
   * ...For driving testing.
   */
  private final static int    S_MAX_SIZE    = 6;

  /*
   * Enumeration for managing different versions.
   */
  public enum KnapsackEnum {
    E_GOOD,     /* gKnapsack */
    E_NON_POS,  /* npKnapsack */
    E_NO_WT,    /* nwKnapsack */
  };


  /*
   * Attributes used to drive testing.
   */
  private int            d_numParts = 0;
  private int            d_partNum  = 0;
  private long           d_result   = synch.ResultType.PASS;
  private RegOut         d_tracker  = new synch.RegOut(); 
  private sidl.EnfPolicy d_policy   = new sidl.EnfPolicy();

  /*
   * Attributes associated with knapsack arguments.
   */
  private int    d_total = 0;

  private Array1 d_good   = new Array1(0, S_MAX_SIZE, false);
  private Array1 d_gLong  = new Array1(0, S_MAX_SIZE+1, false);
  private Array1 d_negEnd = new Array1(0, S_MAX_SIZE, false);
  private Array1 d_zStart = new Array1(0, S_MAX_SIZE, false);
  private Array1 d_zMid   = new Array1(0, S_MAX_SIZE, false);
  private Array1 d_zEnd   = new Array1(0, S_MAX_SIZE, false);
  private Array1 d_nArr   = null;

  /**
   * Constructor.
   */
  public invariantstest(int numParts) {
    int tempTotal;

    if (numParts > 0) {
      d_numParts = numParts;

      d_total = initArray(KnapsackEnum.E_GOOD, d_good, 0);
      tempTotal = initArray(KnapsackEnum.E_GOOD, d_gLong, 0);
      tempTotal = initArray(KnapsackEnum.E_NON_POS, d_negEnd, S_MAX_SIZE-1);
      tempTotal = initArray(KnapsackEnum.E_NO_WT, d_zStart, 0);
      tempTotal = initArray(KnapsackEnum.E_NO_WT, d_zMid, 0);
      tempTotal = initArray(KnapsackEnum.E_NO_WT, d_zEnd, 0);
    } else {
      System.out.println("Cannot set expectations to negative number. Assuming "
                        + d_numParts + " parts.");
    }

    d_tracker.setExpectations(d_numParts);
  } // knapsacktest(int): knapsacktest

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
   * Set enforcement option to enforce no contracts.
   */
  private void enforceNone(boolean b) throws InterruptedException {
    try {
      d_policy.setEnforceNone(b);
    } catch (SIDLException exc) {
      forceFailure("Failure:  Set enforce none option to " + b, exc);
    }
  } // enforceNone(boolean)


  /**
   * Evaluate exception.
   */
  private void evalExcept(SIDLException exc, long expected) {
    String myPre = S_PRE + "evalExcept: ";
    if (g_debug) {
      System.out.println(myPre + "expected=" + expected);
    }
    if (exc != null) {
      long excType = knapsack.ExpectExcept.NoneExp;
      try {
        if (exc.isType("knapsack.kBadWeightExcept")) {
          excType = knapsack.ExpectExcept.BWExp;
        } else if (exc.isType("knapsack.kSizeExcept")) {
          excType = knapsack.ExpectExcept.SizeExp;
        } else if (exc.isType("sidl.InvViolation")) {
          excType = knapsack.ExpectExcept.InvExp;
        } else if (exc.isType("sidl.PreViolation")) {
          excType = knapsack.ExpectExcept.PreExp;
        } else if (exc.isType("sidl.PostViolation")) {
          excType = knapsack.ExpectExcept.PostExp;
        } else if (exc.isType("knapsack.kExcept")) {
          excType = knapsack.ExpectExcept.ExcExp;
        } else {
          excType = knapsack.ExpectExcept.NoneExp;
        }
      } catch (SIDLException exc2) {
        System.out.println(myPre + "Exception: Unknown SIDL Exception");
        excType = knapsack.ExpectExcept.ExcExp;
      }
      if (excType == expected) {
        if (g_debug) {
          if (excType != knapsack.ExpectExcept.NoneExp) {
            System.out.println(myPre + "Exception: " + excType);
          }
        }
        d_tracker.endPart(d_partNum, synch.ResultType.PASS);
      } else {
        if (g_debug) {
          if (  (excType != knapsack.ExpectExcept.NoneExp) 
             && (expected != knapsack.ExpectExcept.NoneExp))
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
   * Evaluate results.
   */
  private void evalRes(boolean result, boolean expected) {
    String myPre = S_PRE + "evalRes: ";
    if (g_debug) {
      System.out.println(myPre + "result=" + result + ", expected=" + expected);
    }
    if (result == expected) {
      d_tracker.endPart(d_partNum, synch.ResultType.PASS);
    } else {
      d_tracker.endPart(d_partNum, synch.ResultType.FAIL);
    } // endif result matches expected
  } // evalRes(boolean, boolean)


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
    if (expected != knapsack.ExpectExcept.NoneExp) {
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
   * Initialize all entries of specified array, setting the value at
   * the specified position according to the type of knapsack being
   * initialized.
   */
  private int initArray(KnapsackEnum tp, Array1 w, int pos) {
    int total = 0;
    int val = 0;
    if ( (w != null) && (0 <= pos) && (pos < S_MAX_SIZE) ) {
      int max = w._upper(0);
      for (int i = w._lower(0); i <= max; ++i) {
        val = val + 2;
        w.set(i, val);
        total += val;
      }
    }
    return total;
  } // initArray(KnapsackEnum, Array1, int)


/*
 ****************************************************************************
 *                       Test Routines
 ****************************************************************************
 */
  private void runBaseCase(KnapsackEnum tp, Array1 w, int t, boolean res, 
                           long expectExc, String desc) 
    throws InterruptedException
  {
    boolean x = false;

    describeTest(desc);
    try {
      iKnapsack k = null;
      switch (tp) {
        case E_GOOD:     k = new gKnapsack(); break;
        case E_NON_POS:  k = new npKnapsack(); break;
        case E_NO_WT:    k = new nwKnapsack(); break;
      }
      if (k != null) {
        k.initialize(w);
        x = k.hasSolution(t);
        if (expectExc == knapsack.ExpectExcept.NoneExp) {
          evalRes(x, res);
        } else {
          failNoExcept(expectExc);
        }
      } else {
        forceFailure("Unrecognized or unsupported knapsack type encountered",
                     null);
      }
    } catch (SIDLException exc) {
      evalExcept(exc, expectExc);
    }

    /*
     * Try to force garbage collector to run.
     */
    System.gc(); Thread.sleep(5);
  } // runBaseCase(KnapsackEnum, Array1, int, boolean, expectExc, desc) 


/*
 ****************************************************************************
 */
  private void run() throws InterruptedException {
    KnapsackEnum useGood = KnapsackEnum.E_GOOD;
    KnapsackEnum useNonPos = KnapsackEnum.E_NON_POS;
    KnapsackEnum useNoWt = KnapsackEnum.E_NO_WT;

    /*
     ***********************************************************************
     * FULL CONTRACT ENFORCEMENT
     ***********************************************************************
     */
    d_tracker.writeComment("COMMENT: *** ENABLE CONTRACT ENFORCEMENT ***");
    enforceAll(sidl.ContractClass.ALLCLASSES, true);
  
  
    /* gKnapsack.BaseCase.Full set */
    runBaseCase(useGood, d_good, d_total, true, 
     knapsack.ExpectExcept.NoneExp, 
     "gKnapsack.BaseCase.Full: good: expect solution for total");
  
    runBaseCase(useGood, d_good, d_total-1, false, 
     knapsack.ExpectExcept.NoneExp, 
     "gKnapsack.BaseCase.Full: good: do not expect solution for total-1");
  
    runBaseCase(useGood, d_good, -1, false, 
     knapsack.ExpectExcept.PreExp, 
     "gKnapsack.BaseCase.Full: good: precondition vio (neg. target);");
  
    runBaseCase(useGood, d_gLong, d_total, false, 
     knapsack.ExpectExcept.SizeExp, 
     "gKnapsack.BaseCase.Full: gLong: size exception expected");
  
    runBaseCase(useGood, d_zStart, d_total, false, 
     knapsack.ExpectExcept.PreExp, 
     "gKnapsack.BaseCase.Full: zStart: precondition vio/no solution expected");
  
    runBaseCase(useGood, d_zMid, d_total, false, 
     knapsack.ExpectExcept.PreExp, 
     "gKnapsack.BaseCase.Full: zMid: precondition vio/no solution expected");
  
    runBaseCase(useGood, d_zEnd, d_total, false, 
     knapsack.ExpectExcept.PreExp, 
     "gKnapsack.BaseCase.Full: zEnd: precondition vio/no solution expected");
  
    runBaseCase(useGood, d_negEnd, d_total, false, 
     knapsack.ExpectExcept.PreExp, 
     "gKnapsack.BaseCase.Full: negEnd: precondition vio/no solution expected");
  
    /*
     * TODO/TBD:  This will either be an invariant or postcondition violation,
     * depending on ordering in generated code
     */
    runBaseCase(useGood, d_nArr, d_total, false, 
     knapsack.ExpectExcept.InvExp, 
     "gKnapsack.BaseCase.Full: nArr: invariants vio/no solution expected");
  
  
    /* npKnapsack.BaseCase.Full set */
    runBaseCase(useNonPos, d_good, d_total, true, 
     knapsack.ExpectExcept.NoneExp, 
     "npKnapsack.BaseCase.Full: good: expect solution for total");
  
    runBaseCase(useNonPos, d_good, d_total-1, false, 
     knapsack.ExpectExcept.NoneExp, 
     "npKnapsack.BaseCase.Full: good: do not expect solution for total-1");
  
    runBaseCase(useNonPos, d_good, -1, false, 
     knapsack.ExpectExcept.PreExp, 
     "npKnapsack.BaseCase.Full: good: precondition vio (neg. target);");
  
    runBaseCase(useNonPos, d_gLong, d_total, false, 
     knapsack.ExpectExcept.SizeExp,
     "npKnapsack.BaseCase.Full: gLong: size exception expected");
  
    runBaseCase(useNonPos, d_zStart, d_total, false, 
     knapsack.ExpectExcept.PreExp,
     "npKnapsack.BaseCase.Full: zStart: precondition vio/no solution expected");
  
    runBaseCase(useNonPos, d_zMid, d_total, false, 
     knapsack.ExpectExcept.PreExp, 
     "npKnapsack.BaseCase.Full: zMid: precondition vio/no solution expected");
  
    runBaseCase(useNonPos, d_zEnd, d_total, false, 
     knapsack.ExpectExcept.PreExp, 
     "npKnapsack.BaseCase.Full: zEnd: precondition vio/no solution expected");
  
    runBaseCase(useNonPos, d_negEnd, d_total, false, 
     knapsack.ExpectExcept.PreExp,
     "npKnapsack.BaseCase.Full: negEnd: precondition vio/no solution expected");
  
    /*
     * TODO/TBD:  This will either be an invariant or postcondition violation,
     * depending on ordering in generated code
     */
    runBaseCase(useNonPos, d_nArr, d_total, false, 
     knapsack.ExpectExcept.InvExp, 
     "npKnapsack.BaseCase.Full: nArr: invariants/no solution expected");
  
  
    /* nwKnapsack.BaseCase.Full set */
    runBaseCase(useNoWt, d_good, d_total, false, 
     knapsack.ExpectExcept.NoneExp, 
     "nwKnapsack.BaseCase.Full: good: do not expect solution for total");
  
    runBaseCase(useNoWt, d_good, d_total-1, false, 
     knapsack.ExpectExcept.NoneExp, 
     "nwKnapsack.BaseCase.Full: good: do not expect solution for total-1");
  
    runBaseCase(useNoWt, d_good, -1, false, 
     knapsack.ExpectExcept.PreExp, 
     "nwKnapsack.BaseCase.Full: good: precondition vio (neg. target);");
  
    runBaseCase(useNoWt, d_gLong, d_total, false, 
     knapsack.ExpectExcept.SizeExp, 
     "nwKnapsack.BaseCase.Full: gLong: size exception expected");
  
    runBaseCase(useNoWt, d_zStart, d_total, false, 
     knapsack.ExpectExcept.PreExp, 
     "nwKnapsack.BaseCase.Full: zStart: precondition vio/no solution expected");
  
    runBaseCase(useNoWt, d_zMid, d_total, false, 
     knapsack.ExpectExcept.PreExp, 
     "nwKnapsack.BaseCase.Full: zMid: precondition vio/no solution expected");
  
    runBaseCase(useNoWt, d_zEnd, d_total, false, 
     knapsack.ExpectExcept.PreExp, 
     "nwKnapsack.BaseCase.Full: zEnd: precondition vio/no solution expected");
  
    runBaseCase(useNoWt, d_negEnd, d_total, false, 
     knapsack.ExpectExcept.PreExp, 
     "nwKnapsack.BaseCase.Full: negEnd: precondition vio/no solution expected");
  
    /*
     * TODO/TBD:  This will either be an invariant or postcondition violation,
     * depending on ordering in generated code
     */
    runBaseCase(useNoWt, d_nArr, d_total, false, 
     knapsack.ExpectExcept.InvExp, 
     "nwKnapsack.BaseCase.Full: nArr: invariant vio/no solution expected");
  
  
    /*
     ***********************************************************************
     * POSTCONDITION-ONLY ENFORCEMENT
     ***********************************************************************
     */
    d_tracker.writeComment("COMMENT: *** POSTCONDITION-ONLY ENFORCEMENT ***");
    enforceAll(sidl.ContractClass.POSTCONDS, false);
                                 
  
    /* gKnapsack.BaseCase.Post set */
    runBaseCase(useGood, d_good, d_total, true, 
     knapsack.ExpectExcept.NoneExp, 
     "gKnapsack.BaseCase.Post: good: expect solution for total");
  
    runBaseCase(useGood, d_good, -1, false, 
     knapsack.ExpectExcept.NoneExp, 
     "gKnapsack.BaseCase.Post: good: no solution expected");
  
    runBaseCase(useGood, d_gLong, d_total, false, 
     knapsack.ExpectExcept.SizeExp, 
     "gKnapsack.BaseCase.Post: gLong: size exception expected");
  
    runBaseCase(useGood, d_negEnd, d_total, false, 
     knapsack.ExpectExcept.BWExp, 
     "gKnapsack.BaseCase.Post: negEnd: bad weight exception expected");
  
    runBaseCase(useGood, d_nArr, d_total, false, 
     knapsack.ExpectExcept.PostExp, 
     "gKnapsack.BaseCase.Post: nArr: postcondition vio/no solution expected");
  
  
    /* npKnapsack.BaseCase.Post set */
    runBaseCase(useNonPos, d_good, d_total, true, 
     knapsack.ExpectExcept.NoneExp, 
     "npKnapsack.BaseCase.Post: good: expect solution for total");
  
    runBaseCase(useNonPos, d_good, -1, false, 
     knapsack.ExpectExcept.NoneExp, 
     "npKnapsack.BaseCase.Post: good: no solution expected");
  
    runBaseCase(useNonPos, d_gLong, d_total, false, 
     knapsack.ExpectExcept.SizeExp, 
     "npKnapsack.BaseCase.Post: gLong: size exception expected");
  
    runBaseCase(useNonPos, d_zStart, d_total, false, 
     knapsack.ExpectExcept.NoneExp, 
     "npKnapsack.BaseCase.Post: zStart: no solution expected");
  
    runBaseCase(useNonPos, d_zMid, d_total, false, 
     knapsack.ExpectExcept.NoneExp, 
     "npKnapsack.BaseCase.Post: zMid: no solution expected");
  
    runBaseCase(useNonPos, d_zEnd, d_total, false, 
     knapsack.ExpectExcept.NoneExp, 
     "npKnapsack.BaseCase.Post: zEnd: no solution expected");
  
    runBaseCase(useNonPos, d_negEnd, d_total, false, 
     knapsack.ExpectExcept.NoneExp, 
     "npKnapsack.BaseCase.Post: negEnd: no solution expected");
  
    runBaseCase(useNonPos, d_nArr, d_total, false, 
     knapsack.ExpectExcept.PostExp, 
     "npKnapsack.BaseCase.Post: nArr: postcondition vio/no solution expected");
  
  
    /* nwKnapsack.BaseCase.Post set */
    runBaseCase(useNoWt, d_good, d_total, false, 
     knapsack.ExpectExcept.PostExp, 
     "nwKnapsack.BaseCase.Post: good: postcondition vio/no solution expected");
  
    runBaseCase(useNoWt, d_good, -1, false, 
     knapsack.ExpectExcept.PostExp, 
     "nwKnapsack.BaseCase.Post: good: postcondition vio/no solution expected");
  
    runBaseCase(useNoWt, d_gLong, d_total, false, 
     knapsack.ExpectExcept.SizeExp, 
     "nwKnapsack.BaseCase.Post: gLong: size exception expected");
  
    runBaseCase(useNoWt, d_negEnd, d_total, false, 
     knapsack.ExpectExcept.PostExp, 
    "nwKnapsack.BaseCase.Post: negEnd: postcondition vio/no solution expected");
  
    runBaseCase(useNoWt, d_nArr, d_total, false, 
     knapsack.ExpectExcept.PostExp, 
     "nwKnapsack.BaseCase.Post: nArr: postcondition vio/no solution expected");
  
  
    /*
     ***********************************************************************
     * INVARIANTS-ONLY ENFORCEMENT
     ***********************************************************************
     */
    d_tracker.writeComment("COMMENT: *** INVARIANTS-ONLY ENFORCEMENT ***");
    enforceAll(sidl.ContractClass.INVARIANTS, false);
  
    /* gKnapsack.BaseCase.Inv set */
    runBaseCase(useGood, d_good, d_total, true, 
     knapsack.ExpectExcept.NoneExp, 
     "gKnapsack.BaseCase.Inv: good: expect solution for total");
  
    runBaseCase(useGood, d_good, -1, false, 
     knapsack.ExpectExcept.NoneExp, 
     "gKnapsack.BaseCase.Inv: good: no solution expected");
  
    runBaseCase(useGood, d_gLong, d_total, false, 
     knapsack.ExpectExcept.SizeExp, 
     "gKnapsack.BaseCase.Inv: gLong: size exception expected");
  
    runBaseCase(useGood, d_negEnd, d_total, false, 
     knapsack.ExpectExcept.BWExp, 
     "gKnapsack.BaseCase.Inv: negEnd: bad weight exception expected");
  
    runBaseCase(useGood, d_nArr, d_total, false, 
     knapsack.ExpectExcept.InvExp, 
     "gKnapsack.BaseCase.Inv: nArr: invariants vio/no solution expected");
  
  
    /* npKnapsack.BaseCase.Inv set */
    runBaseCase(useNonPos, d_good, d_total, true, 
     knapsack.ExpectExcept.NoneExp, 
     "npKnapsack.BaseCase.Inv: good: expect solution for total");
  
    runBaseCase(useNonPos, d_good, -1, false, 
     knapsack.ExpectExcept.NoneExp, 
     "npKnapsack.BaseCase.Inv: good: no solution expected");
  
    runBaseCase(useNonPos, d_gLong, d_total, false, 
     knapsack.ExpectExcept.SizeExp, 
     "npKnapsack.BaseCase.Inv: gLong: size exception expected");
  
    runBaseCase(useNonPos, d_zStart, d_total, false, 
     knapsack.ExpectExcept.InvExp, 
     "npKnapsack.BaseCase.Inv: zStart: invariants vio/no solution expected");
  
    runBaseCase(useNonPos, d_zMid, d_total, false, 
     knapsack.ExpectExcept.InvExp, 
     "npKnapsack.BaseCase.Inv: zMid: invariants vio/no solution expected");
  
    runBaseCase(useNonPos, d_zEnd, d_total, false, 
     knapsack.ExpectExcept.InvExp, 
     "npKnapsack.BaseCase.Inv: zEnd: invariants vio/no solution expected");
  
    runBaseCase(useNonPos, d_negEnd, d_total, false, 
     knapsack.ExpectExcept.InvExp, 
     "npKnapsack.BaseCase.Inv: negEnd: invariants vio/no solution expected");
  
    runBaseCase(useNonPos, d_nArr, d_total, false, 
     knapsack.ExpectExcept.InvExp, 
     "npKnapsack.BaseCase.Inv: nArr: invariants vio/no solution expected");
  
  
    /* nwKnapsack.BaseCase.Inv set */
    runBaseCase(useNoWt, d_good, d_total, false, 
     knapsack.ExpectExcept.NoneExp, 
     "nwKnapsack.BaseCase.Inv: good: no solution expected");
  
    runBaseCase(useNoWt, d_good, -1, false, 
     knapsack.ExpectExcept.NoneExp, 
     "nwKnapsack.BaseCase.Inv: good: no solution expected");
  
    runBaseCase(useNoWt, d_gLong, d_total, false, 
     knapsack.ExpectExcept.SizeExp, 
     "nwKnapsack.BaseCase.Inv: gLong: size exception expected");
  
    /*
     * WARNING:  The following assumes the non-positive weight does get added
     * to the knapsack.
     */
    runBaseCase(useNoWt, d_negEnd, d_total, false, 
     knapsack.ExpectExcept.InvExp, 
     "nwKnapsack.BaseCase.Inv: negEnd: invariants vio/no solution expected");
  
    runBaseCase(useNoWt, d_nArr, d_total, false, 
     knapsack.ExpectExcept.InvExp, 
     "nwKnapsack.BaseCase.Inv: nArr: invariants vio/no solution expected");
  
  
    /*
     ***********************************************************************
     * NO CONTRACT ENFORCEMENT
     ***********************************************************************
     */
    d_tracker.writeComment("COMMENT: *** DISABLE CONTRACT ENFORCEMENT ***");
    enforceNone(false);
  
    /* gKnapsack.BaseCase.None set */
    runBaseCase(useGood, d_good, d_total, true, 
     knapsack.ExpectExcept.NoneExp, 
     "gKnapsack.BaseCase.None: good: expect solution for total");
  
    runBaseCase(useGood, d_good, -1, false, 
     knapsack.ExpectExcept.NoneExp, 
     "gKnapsack.BaseCase.None: good: no solution expected");
  
    runBaseCase(useGood, d_gLong, d_total, false, 
     knapsack.ExpectExcept.SizeExp, 
     "gKnapsack.BaseCase.None: gLong: size exception expected");
  
    runBaseCase(useGood, d_negEnd, d_total, false, 
     knapsack.ExpectExcept.BWExp, 
     "gKnapsack.BaseCase.None: negEnd: bad weight exception expected");
  
    runBaseCase(useGood, d_nArr, d_total, false, 
     knapsack.ExpectExcept.NoneExp, 
     "gKnapsack.BaseCase.None: nArr: invariants vio/no solution expected");
  
  
    /* npKnapsack.BaseCase.None set */
    runBaseCase(useNonPos, d_good, d_total, true, 
     knapsack.ExpectExcept.NoneExp, 
     "npKnapsack.BaseCase.None: good: expect solution for total");
  
    runBaseCase(useNonPos, d_good, -1, false, 
     knapsack.ExpectExcept.NoneExp, 
     "npKnapsack.BaseCase.None: good: no solution expected");
  
    runBaseCase(useNonPos, d_gLong, d_total, false, 
     knapsack.ExpectExcept.SizeExp, 
     "npKnapsack.BaseCase.None: gLong: size exception expected");
  
    runBaseCase(useNonPos, d_zStart, d_total, false, 
     knapsack.ExpectExcept.NoneExp, 
     "npKnapsack.BaseCase.None: zStart: no solution expected");
  
    runBaseCase(useNonPos, d_zMid, d_total, false, 
     knapsack.ExpectExcept.NoneExp, 
     "npKnapsack.BaseCase.None: zMid: no solution expected");
  
    runBaseCase(useNonPos, d_zEnd, d_total, false, 
     knapsack.ExpectExcept.NoneExp, 
     "npKnapsack.BaseCase.None: zEnd: no solution expected");
  
    runBaseCase(useNonPos, d_negEnd, d_total, false, 
     knapsack.ExpectExcept.NoneExp, 
     "npKnapsack.BaseCase.None: negEnd: no solution expected");
  
    runBaseCase(useNonPos, d_nArr, d_total, false, 
     knapsack.ExpectExcept.NoneExp, 
     "npKnapsack.BaseCase.None: nArr: no solution expected");
  
  
    /* nwKnapsack.BaseCase.None set */
    runBaseCase(useNoWt, d_good, d_total, false, 
     knapsack.ExpectExcept.NoneExp, 
     "nwKnapsack.BaseCase.None: good: no solution expected");
  
    runBaseCase(useNoWt, d_good, -1, false, 
     knapsack.ExpectExcept.NoneExp, 
     "nwKnapsack.BaseCase.None: good: no solution expected");
  
    runBaseCase(useNoWt, d_gLong, d_total, false, 
     knapsack.ExpectExcept.SizeExp, 
     "nwKnapsack.BaseCase.None: gLong: size exception expected");
  
    runBaseCase(useNoWt, d_negEnd, d_total, false, 
     knapsack.ExpectExcept.NoneExp, 
     "nwKnapsack.BaseCase.None: negEnd: no solution expected");
  
    runBaseCase(useNoWt, d_nArr, d_total, false, 
     knapsack.ExpectExcept.NoneExp, 
     "nwKnapsack.BaseCase.None: nArr: no solution expected");
  
    /*
     * TODO/TBD:  Other possible cases:
     * 1) create, initialize, and explicitly call hasWeights
        x = k.hasWeights(k, w);
  
     * 2) create, initialize, and explicitly call onlyPosWeights
        x = k.onlyPosWeights(k);
     */

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
    invariantstest driver = new invariantstest(S_MAX_PARTS);
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
} // knapsacktest

