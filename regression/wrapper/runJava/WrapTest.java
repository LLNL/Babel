//
// File:        WrapTest.java
// Package:     
// Copyright:   (c) 2006 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision$
// Date:        $Date$
// Description: 
// 

class WrapTest {

  private final static String[] s_results = {
    "FAIL",
    "XFAIL",
    "synch.ResultType.PASS",
    "Xsynch.ResultType.PASS",
    "UNSUPPORTED"
  };

  private static int s_part = 0;
  private static long s_result = synch.ResultType.PASS;

  private static boolean scomp(String a, String b) {
    if(a.compareTo(b) == 0)
      return true;
    else 
      return false;
  }

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

  private static void tests(synch.RegOut tracker)
  {
    wrapper.Data_Impl d_data = new wrapper.Data_Impl(); 
    startTest(null);
    check(synch.ResultType.PASS, d_data != null, "Data is not null");
    wrapper.User d_user = new wrapper.User();
    startTest(null);
    check(synch.ResultType.PASS, d_user != null, "User is not null");
    
    startTest(null);
    check(synch.ResultType.PASS, "ctor was run".equals(d_data.d_ctorTest) , 
          "The ctor was run properly");
    
    d_user.accept(d_data);
    startTest(null);
    check(synch.ResultType.PASS, "Hello World!".equals(d_data.d_string), "Hello World!");
    startTest(null);
    check(synch.ResultType.PASS, d_data.d_int == 3, "3");
    d_data = null;
  }
  

  /**
   * The main test driver takes no command-line arguments and runs the
   * regression tests.
   */
  public static void main(String args[]) {
    try {
      /*
       * Begin the test
       */
      tracker = new synch.RegOut();
      tracker.setExpectations(-1);

      s_part   = 0; 
      s_result = synch.ResultType.PASS;
      tests(tracker);
      System.gc(); Thread.sleep(50);

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
