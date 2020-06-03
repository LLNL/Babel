//
// File:        OrderingTestDriver.java
// Package:     
// Copyright:   (c) 2002 Lawrence Livermore National Security, LLC
// Revision:    @(#) $Revision: 7003 $
// Date:        $Date: 2010-11-18 17:01:04 -0800 (Thu, 18 Nov 2010) $
// Description: Regression test for array ordering
// 

public class OrderingTestDriver {

  private final static String[] s_results = {
    "FAIL",
    "XFAIL",
    "synch.ResultType.PASS",
    "Xsynch.ResultType.PASS",
    "UNSUPPORTED"
  };

  private static int s_part = 0;
  private static long s_result = synch.ResultType.PASS;
  private static synch.RegOut tracker; 
  /**
   * Check the results of the test case.
   */
  //Comments should really be passed in here.
  private static void startTest(String test) {
    tracker.startPart(++s_part);
    if(test != null)
      tracker.writeComment(test);
  }

  private static void check(long expected, boolean pass, String test ) {
    if (test != null)
      tracker.writeComment(test);

    if(expected == synch.ResultType.PASS)
      if(pass)
        tracker.endPart(s_part, synch.ResultType.PASS);
      else
        tracker.endPart(s_part, synch.ResultType.FAIL);
    else if (expected == synch.ResultType.XFAIL)
      if(pass)
        tracker.endPart(s_part, synch.ResultType.XPASS);
      else
        tracker.endPart(s_part, synch.ResultType.XFAIL);
    else
      tracker.endPart(s_part, synch.ResultType.FAIL);
  }


  public static sidl.Integer.Array1 
    make1DIMatrix(int size)
  {
    sidl.Integer.Array1 result = null;
    int i;
    result = new sidl.Integer.Array1(size, true);
    for(i = 0; i < size; ++i ){
      result.set(i,i);
    }
    return result;
  }

  private static void tests(synch.RegOut tracker)
  {
      sidl.Integer.Array2 array = 
        Ordering.IntOrderTest.makeColumnIMatrix(10,true);
      sidl.Integer.Array2.Holder hold = null;

      startTest(null);
      check(synch.ResultType.PASS,
            (array != null),
            "1 (!array != null)");
      startTest(null);
      check(synch.ResultType.PASS, array._isColumnOrder(), "2 Array is in ColumnOrder");
      startTest(null);
      check(synch.ResultType.PASS,
            Ordering.IntOrderTest.isIMatrixTwo(array),
            "3 Ordering.IntOrderTest.isIMatrixTwo(array)");

      startTest(null);
      check(synch.ResultType.PASS, Ordering.IntOrderTest.isColumnIMatrixTwo(array),
            "4 isColumnIMatrixTwo");

      startTest(null);
      check(synch.ResultType.PASS, Ordering.IntOrderTest.isRowIMatrixTwo(array), 
            "5 isRowIMatrixTwo");

      //Ordering_IntOrderTest_ensureRow(&A);
      array = Ordering.IntOrderTest.makeRowIMatrix(10,true);
      startTest(null);
      check(synch.ResultType.PASS,
            (array != null),
            "6 (!array != null)");
      startTest(null);
      check(synch.ResultType.PASS, array._isRowOrder(), "7 Array is in ColumnOrder");
      startTest(null);
      check(synch.ResultType.PASS, Ordering.IntOrderTest.isRowIMatrixTwo(array), 
            "8 isRowIMatrixTwo");
      startTest(null);
      check(synch.ResultType.PASS, Ordering.IntOrderTest.isColumnIMatrixTwo(array),
            "9 isColumnIMatrixTwo");

      /************* Round 2 *************/
      array = Ordering.IntOrderTest.makeRowIMatrix(10,false);
      startTest(null);
      check(synch.ResultType.PASS,
            (array != null),
            "10 (!array != null)");
      startTest(null);
      check(synch.ResultType.PASS, array._isRowOrder(), "11 Array is in ColumnOrder");
      startTest(null);
      check(synch.ResultType.PASS,
            Ordering.IntOrderTest.isIMatrixTwo(array),
            "12 Ordering.IntOrderTest.isIMatrixTwo(array)");

      startTest(null);
      check(synch.ResultType.PASS, Ordering.IntOrderTest.isColumnIMatrixTwo(array),
            "13 isColumnIMatrixTwo");

      startTest(null);
      check(synch.ResultType.PASS, Ordering.IntOrderTest.isRowIMatrixTwo(array), 
            "14 isRowIMatrixTwo");

      //Ordering_IntOrderTest_ensureRow(&A);
      array = Ordering.IntOrderTest.makeColumnIMatrix(10,false);
      startTest(null);
      check(synch.ResultType.PASS,
            (array != null),
            "15 (!array != null)");
      startTest(null);
      check(synch.ResultType.PASS, array._isColumnOrder(), "16 Array is in ColumnOrder");
      startTest(null);
      check(synch.ResultType.PASS, Ordering.IntOrderTest.isRowIMatrixTwo(array), 
            "17 isRowIMatrixTwo");
      startTest(null);
      check(synch.ResultType.PASS, Ordering.IntOrderTest.isColumnIMatrixTwo(array),
            "18 isColumnIMatrixTwo");

      /******** Round 3: in out arrays ************/
      hold = new sidl.Integer.Array2.Holder();
      Ordering.IntOrderTest.createColumnIMatrix(10, true, hold);
      array = hold.get();
      
      startTest(null);
      check(synch.ResultType.PASS,
            (array != null),
            "19 (!array != null)");
      startTest(null);
      check(synch.ResultType.PASS, array._isColumnOrder(), "20 Array is in ColumnOrder");
      startTest(null);
      check(synch.ResultType.PASS,
            Ordering.IntOrderTest.isIMatrixTwo(array),
            "21 Ordering.IntOrderTest.isIMatrixTwo(array)");

      startTest(null);
      check(synch.ResultType.PASS, Ordering.IntOrderTest.isColumnIMatrixTwo(array),
            "22 isColumnIMatrixTwo");

      startTest(null);
      check(synch.ResultType.PASS, Ordering.IntOrderTest.isRowIMatrixTwo(array), 
            "23 isRowIMatrixTwo");

     
      Ordering.IntOrderTest.createRowIMatrix(10, true, hold);
      array = hold.get();
      
      startTest(null);
      check(synch.ResultType.PASS,
            (array != null),
            "24 (!array != null)");
      startTest(null);
      check(synch.ResultType.PASS, array._isRowOrder(), "25 Array is in ColumnOrder");
      startTest(null);
      check(synch.ResultType.PASS, Ordering.IntOrderTest.isRowIMatrixTwo(array), 
            "26 isRowIMatrixTwo");
      startTest(null);
      check(synch.ResultType.PASS, Ordering.IntOrderTest.isColumnIMatrixTwo(array),
            "27 isColumnIMatrixTwo");
     
      /************* Round 4, false outs *************/

      Ordering.IntOrderTest.createRowIMatrix(10, false, hold);
      array = hold.get();

      startTest(null);
      check(synch.ResultType.PASS,
            (array != null),
            "28 (!array != null)");
      startTest(null);
      check(synch.ResultType.PASS, array._isRowOrder(), "29 Array is in ColumnOrder");
      startTest(null);
      check(synch.ResultType.PASS,
            Ordering.IntOrderTest.isIMatrixTwo(array),
            "30 Ordering.IntOrderTest.isIMatrixTwo(array)");

      startTest(null);
      check(synch.ResultType.PASS, Ordering.IntOrderTest.isColumnIMatrixTwo(array),
            "31 isColumnIMatrixTwo");

      startTest(null);
      check(synch.ResultType.PASS, Ordering.IntOrderTest.isRowIMatrixTwo(array), 
            "32 isRowIMatrixTwo");
     
      array = new sidl.Integer.Array2();
      Ordering.IntOrderTest.createColumnIMatrix(10, false, hold);
      array= hold.get();

      startTest(null);
      check(synch.ResultType.PASS,
            (array != null),
            "33 (!array != null)");
      startTest(null);
      check(synch.ResultType.PASS, array._isColumnOrder(), "34 Array is in RowOrder");
      startTest(null);
      check(synch.ResultType.PASS, Ordering.IntOrderTest.isRowIMatrixTwo(array), 
            "35 isRowIMatrixTwo");
      startTest(null);
      check(synch.ResultType.PASS, Ordering.IntOrderTest.isColumnIMatrixTwo(array),
            "36 isColumnIMatrixTwo");

      /***** ROUND 5, 1 DIMENSION *****/
      sidl.Integer.Array1 a1 = null;
      a1 = make1DIMatrix(10);
      startTest(null);
      check(synch.ResultType.PASS,
            (a1 != null),
            "37 (!array != null)");
      startTest(null);
      check(synch.ResultType.PASS, a1._isRowOrder(), "38 Array is in RowOrder");
      startTest(null);
      check(synch.ResultType.PASS, Ordering.IntOrderTest.isIMatrixOne(a1),
            "39 Array isIMatrixOne");
      startTest(null);
      check(synch.ResultType.PASS, Ordering.IntOrderTest.isRowIMatrixOne(a1),
            "40 Array isIMatrixOne");
      startTest(null);
      check(synch.ResultType.PASS, Ordering.IntOrderTest.isColumnIMatrixOne(a1),
            "41 Array isIMatrixOne");
      a1 = null;   //cleanup
      array = null;
   

      /********** Round 6, slice ********/

      startTest(null);
      check(synch.ResultType.PASS, Ordering.IntOrderTest.isSliceWorking(true),
            "42 Ordering.IntOrderTest.isSliceWorking(true)");
      startTest(null);
      check(synch.ResultType.PASS, Ordering.IntOrderTest.isSliceWorking(false),
            "43 Ordering.IntOrderTest.isSliceWorking(false)");
  }
  

  public static void main(String args[]) {
    try {
      tracker = new synch.RegOut();
      tracker.setExpectations(-1);
      
      s_part = 0;
      s_result = synch.ResultType.PASS;

      tests(tracker);
      System.gc(); Thread.sleep(5);

      // Output final test results
      tracker.close();
      tracker = null;

      // try to force garbage collector to run
      System.gc(); Thread.sleep(50);
      System.gc(); Thread.sleep(50);
      System.gc(); Thread.sleep(50);

      Runtime.getRuntime().exit(0); // workaround for Linux JVM 1.3.1 bug

    } catch (Throwable ex) {
      // Catch any unexpected exceptions and return a test failure
      if (tracker != null) {
        tracker.forceFailure();
        tracker.close();
      }	else {
        System.out.println("TEST_RESULT FAIL");
      }
      ex.printStackTrace();
    }
  }
}
