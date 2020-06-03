// 
// File:        ExceptionsDriver.java
// Copyright:   (c) 2001 Lawrence Livermore National Security, LLC
// Revision:    @(#) $Revision: 7138 $
// Date:        $Date: 2011-07-15 16:48:01 -0700 (Fri, 15 Jul 2011) $
// Description: exception regression test case for Java calling other languages
// 

/**
 * The following class runs the exception regression test cases for Java.
 */
public class ExceptionsDriver {

  private static int s_part = 0;
  private static long s_result = synch.ResultType.PASS;

  private static synch.RegOut tracker;

  //basic RMI support
  private static String remoteURL = null;

  private static boolean withRMI() {
    return remoteURL != null && remoteURL.length() > 0;
  }
  
  private static Exceptions.Fib makeObject() {
    try {
      if(withRMI())
        return new Exceptions.Fib(remoteURL);
      return new Exceptions.Fib();
    } catch (Throwable ex) {
      ex.printStackTrace();
      System.exit(1);
    }
    return null;
  }

  private static sidl.BaseClass makeBaseClassObject() {
    try {
      if(withRMI())
        return new sidl.BaseClass(remoteURL);
      return new sidl.BaseClass();
    } catch (Throwable ex) {
      ex.printStackTrace();
      System.exit(1);
    }
    return null;
  }
  
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
    /*
     * No exception thrown
     */
    Exceptions.Fib fib = makeObject();
    try {
      fib.getFib(10, 25, 200, 0);
      startTest(null);
      check(synch.ResultType.PASS, 
            true, 
            "no exception thrown (none expected)");
    } catch (java.lang.Exception ex) {
      startTest(null);
      check(synch.ResultType.PASS, 
            false, 
            "unexpected exception thrown");
    }

    /*
     * Throw a NegativeValueException
     */

    try {
      fib.getFib(-1, 10, 10, 0);
      startTest(null);
      check(synch.ResultType.PASS, 
            false, 
            "no exception (NegativeValueException expected)");
    } catch (java.lang.Exception ex) {
      
      if (((sidl.BaseException)ex).isType("Exceptions.NegativeValueException")) {
        startTest(null);
        check(synch.ResultType.PASS, 
              true, 
              "NegativeValueException thrown (as expected)");
      } else {
        startTest(null);
        check(synch.ResultType.PASS, 
              false, 
              "unexpected sidl exception thrown");
      }
    }

    /*
     * Throw a TooDeepException 
     */

    try {
      fib.getFib(10, 1, 1000, 0);
      startTest(null);
      check(synch.ResultType.PASS, 
            false, 
            "no exception (TooDeepException expected)");
    } catch (java.lang.Exception ex) {
      if (((sidl.SIDLException)ex).isType("Exceptions.TooDeepException")) {
        startTest(null);
        check(synch.ResultType.PASS, 
              true, 
              "TooDeepException thrown (as expected)");
      } else {
        startTest(null);
        check(synch.ResultType.PASS, 
              false, 
              "unexpected exception thrown");
      }
    }

    /*
     * Throw a TooBigException 
     */

    try {
      fib.getFib(10, 1000, 1, 0);
      startTest(null);
      check(synch.ResultType.PASS, 
            false, 
            "no exception (TooBigException expected)");
    } catch (java.lang.Exception ex) {
      if (((sidl.SIDLException)ex).isType("Exceptions.TooBigException")) {
        startTest(null);
        check(synch.ResultType.PASS, 
              true, 
              "TooBigException thrown (as expected)");
      } else {
        startTest(null);
        check(synch.ResultType.PASS, 
              false, 
              "unexpected exception thrown");
      }
    }
  }


  /**
   * The main test driver takes no command-line arguments and runs the
   * regression tests.
   */
  public static void main(String args[]) {
    try {

      tracker = new synch.RegOut();
      
      /*
       * Parse the command line  to see if we are running RMI tests
       */
      for(int _arg = 0; _arg < args.length; ++_arg) {
        if(args[_arg].startsWith("--url=")) {
          sidl.String.Holder _tmp = new sidl.String.Holder(args[_arg].substring(6));
          tracker.replaceMagicVars(_tmp, ""); /* we always look at $SIDL_DLL_PATH for Java*/
          remoteURL = _tmp.get();
        }
      }
      
      if(withRMI()) {
        System.out.println("using remote URL " + remoteURL);
      }
      
      /*
       * Setup RMI if necessary
       */
      if(withRMI()) {
        try {
          System.out.println("registering RMI protocol simhandle");
          sidl.rmi.ProtocolFactory.addProtocol("simhandle","sidlx.rmi.SimHandle");
        } catch (Throwable ex) {
          ex.printStackTrace();
          System.exit(1);
        }
      }

      /*
       * Begin the test
       */
      tracker.setExpectations(-1);
      s_part   = 0;
      s_result = synch.ResultType.PASS;

      tests(tracker);
      System.gc(); Thread.sleep(5); // try to force the garbage collector 
      
      // Output final test results
      tracker.close();
      tracker = null;

      // try to force garbage collector to run
      System.gc(); Thread.sleep(50);
      System.gc(); Thread.sleep(50);
      System.gc(); Thread.sleep(50);

      Runtime.getRuntime().exit(0); // workaround for Linux JVM 1.3.1 bug

    } catch (Throwable ex) {
      // Catch any unexpected Exceptions and return a test failure
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
