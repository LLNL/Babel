// 
// File:        ArgsTest.java
// Copyright:   (c) 2001 Lawrence Livermore National Security, LLC
// Revision:    @(#) $Revision: 4618 $
// Date:        $Date: 2005-05-09 15:54:29 -0700 (Mon, 09 May 2005) $
// Description: argument regression test case for Java calling other languages
// 

/**
 * The following class runs the argument regression test cases for Java.
 */
public class HooksTest {
  private final static int FAIL        = 0;
  private final static int XFAIL       = 1;
  private final static int PASS        = 2;
  private final static int XPASS       = 3;
  private final static int UNSUPPORTED = 4;

  private final static String[] s_results = {
    "FAIL",
    "XFAIL",
    "PASS",
    "XPASS",
    "UNSUPPORTED"
  };

  private static int s_part = 0;
  private static long s_result =  synch.ResultType.PASS;
  private static synch.RegOut tracker = null; 

  //basic RMI support
  private static String remoteURL = null;

  private static boolean withRMI() {
    return remoteURL != null && remoteURL.length() > 0;
  }
  
  private static hooks.Basics makeObject() {
    try {
      if(withRMI())
        return new hooks.Basics(remoteURL);
      return new hooks.Basics();
    } catch (Throwable ex) {
      ex.printStackTrace();
      System.exit(1);
    }
    return null;
  }

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
    throws sidl.RuntimeException.Wrapper
  {
    hooks.Basics._set_hooks_static(true);
    /*
     * Boolean arguments
     */
    hooks.Basics obj = makeObject();
    obj._set_hooks(true);

    sidl.Integer.Holder b = new sidl.Integer.Holder(-1);
    sidl.Integer.Holder c = new sidl.Integer.Holder(-1);
    int ret = 0, test = 0;
    
    startTest(null);
    ret = hooks.Basics.aStaticMeth(test++, b, c);
    check(synch.ResultType.PASS, b.get() == 1 && c.get() == 0, "b == 1 && c == 0");

    startTest(null);
    ret = hooks.Basics.aStaticMeth(test++, b, c);
    check(synch.ResultType.PASS, b.get() == 2 && c.get() == 1, "b == 2 && c == 1");

    b.set(-1);
    c.set(-1);
    
    startTest(null);
    ret = obj.aNonStaticMeth(test++,b,c);
    check(synch.ResultType.PASS, b.get() == 1 && c.get() == 0, "b == 1 && c == 0");
    
    startTest(null);
    ret = obj.aNonStaticMeth(test++,b,c);
    check(synch.ResultType.PASS, b.get() == 2 && c.get() == 1, "b == 2 && c == 1");
  }

  /**
   * The main test driver takes no command-line arguments and runs the
   * regression tests.
   */
  public static void main(String args[]) {
    try {
      tracker = synch.RegOut.getInstance();
      
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
      tracker.setExpectations(4);
      s_part   = 0;
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
