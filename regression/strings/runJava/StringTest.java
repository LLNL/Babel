// 
// File:        StringTest.java
// Copyright:   (c) 2001 Lawrence Livermore National Security, LLC
// Revision:    @(#) $Revision: 7003 $
// Date:        $Date: 2010-11-18 17:01:04 -0800 (Thu, 18 Nov 2010) $
// Description: string regression test case for Java calling other languages
// 

/**
 * The following class runs the string regression test cases for Java.
 */
public class StringTest {

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

  //basic RMI support
  private static String remoteURL = null;

  private static boolean withRMI() {
    return remoteURL != null && remoteURL.length() > 0;
  }

  private static Strings.Cstring makeObject() {
    try {
      if(withRMI())
        return new Strings.Cstring(remoteURL);
      return new Strings.Cstring();
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
  {
    /*
     * Run the string tests
     */

    String             in    = "Three";
    sidl.String.Holder out   = new sidl.String.Holder();
    sidl.String.Holder inout = new sidl.String.Holder("Three");
    String             rbr   = null;
    
    Strings.Cstring obj = makeObject();

    startTest(null);
    check(synch.ResultType.PASS,
          (obj.returnback(true).equals("Three")),
          "(obj.returnback(true).equals(\"Three\"))");
    rbr = obj.returnback(false);
    startTest(null);
    check(synch.ResultType.PASS,
          ((rbr == null) || rbr.equals("")),
          "(obj.returnback(false) == null)");
    startTest(null);
    check(synch.ResultType.PASS,
          (obj.passin(in) == true),
          "(obj.passin(in) == true)");
    startTest(null);
    check(synch.ResultType.PASS,
          (obj.passin(null) == false),
          "(obj.passin(null) == false)");
    startTest(null);
    check(synch.ResultType.PASS,
          (obj.passout(true,out) == true && out.get().equals("Three")),
          "(obj.passout(true,out) == true && out.get().equals(\"Three\"))");
    startTest(null);
    check(synch.ResultType.PASS,
          (obj.passinout(inout) == true && inout.get().equals("threes")),
          "(obj.passinout(inout) == true && inout.get().equals(\"threes\"))");
    startTest(null);
    check(synch.ResultType.PASS,
          (obj.passeverywhere(in , out, inout).equals("Three")
           && out.get().equals("Three")
           && inout.get().equals("Three")),
          "(obj.passeverywhere(in, out, inout).equals(\"Three\")"
          + " && out.get().equals(\"Three\")"
          + " && inout.get().equals(\"Three\"))");
    startTest(null);
    check(synch.ResultType.PASS,
          (obj.mixedarguments("Test", 'z', "Test", 'z')),
          "(obj.mixedarguments(\"Test\", 'z', \"Test\", 'z'))");
    startTest(null);
    check(synch.ResultType.PASS,
          (!obj.mixedarguments("Not", 'A', "Equal", 'a')),
          "(!obj.mixedarguments(\"Not\", 'A', \"Equal\", 'a'))");
    
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
      System.gc(); Thread.sleep(50); // try to force GC to run

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
