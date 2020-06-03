// 
// File:        ArgsTest.java
// Copyright:   (c) 2001 Lawrence Livermore National Security, LLC
// Revision:    @(#) $Revision: 7003 $
// Date:        $Date: 2010-11-18 17:01:04 -0800 (Thu, 18 Nov 2010) $
// Description: argument regression test case for Java calling other languages
// 

/**
 * The following class runs the argument regression test cases for Java.
 */
public class ArgsTest {
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
  /**
   * Check the results of the test case.
   */
  //Comments should really be passed in here.
  private static void startTest(String test) {
    tracker.startPart(++s_part);
    if(test != null)
      tracker.writeComment(test);
  }

  //basic RMI support
  private static String remoteURL = null;

  private static boolean withRMI() {
    return remoteURL != null && remoteURL.length() > 0;
  }
  
  //create a return a local or remote object, depending
  //on command-line arguments
  private static Args.Basic makeObject() {
    try {
      if(withRMI())
        return new Args.Basic(remoteURL);
      return new Args.Basic();
    } catch (Throwable ex) {
      ex.printStackTrace();
      System.exit(1);
    }
    return null;
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
     * Boolean arguments
     */
    if (true) {
      Args.Basic obj = makeObject();
      
      sidl.Boolean.Holder out   = new sidl.Boolean.Holder(false);
      sidl.Boolean.Holder inout = new sidl.Boolean.Holder(true);
      
      startTest(null);
      check(synch.ResultType.PASS,
            (obj.returnbackbool() == true),
            "(obj.returnbackbool() == true)");
      startTest(null);
      check(synch.ResultType.PASS,
            (obj.passinbool(true) == true),
            "(obj.passinbool(true) == true)");
      startTest(null);
      check(synch.ResultType.PASS,
            (obj.passoutbool(out) == true && out.get() == true),
            "(obj.passoutbool(out) == true && out.get() == true)");
      startTest(null);
      check(synch.ResultType.PASS,
            (obj.passinoutbool(inout) == true && inout.get() == false),
            "(obj.passinoutbool(inout) == true && inout.get() == false)");
      startTest(null);
      check(synch.ResultType.PASS,
            (obj.passeverywherebool(true, out, inout) == true
             && out.get() == true
             && inout.get() == true),
            "(obj.passeverywherebool(true, out, inout) == true"
            + " && out.get() == true"
            + " && inout.get() == true)");
      obj = null;
      System.gc(); // try to force garbage collector to run
    }
    
    /*
     * Character arguments
     */
    if (true) {
      Args.Basic obj = makeObject();
      
      sidl.Character.Holder out   = new sidl.Character.Holder();
      sidl.Character.Holder inout = new sidl.Character.Holder('A');
      
      startTest(null);
      check(synch.ResultType.PASS,
            (obj.returnbackchar() == '3'),
            "(obj.returnbackchar() == '3')");
      startTest(null);
      check(synch.ResultType.PASS,
            (obj.passinchar('3') == true),
            "(obj.passinchar('3') == true)");
      startTest(null);
      check(synch.ResultType.PASS,
            (obj.passoutchar(out) == true && out.get() == '3'),
            "(obj.passoutchar(out) == true && out.get() == '3')");
      startTest(null);
      check(synch.ResultType.PASS,
            (obj.passinoutchar(inout) == true && inout.get() == 'a'),
            "(obj.passinoutchar(inout) == true && inout.get() == 'a')");
      startTest(null);
      check(synch.ResultType.PASS,
            (obj.passeverywherechar('3', out, inout) == '3' 
             && out.get() == '3'
             && inout.get() == 'A'),
            "(obj.passeverywherechar('3', out, inout) == '3'"
            + " && out.get() == '3'"
            + " && inout.get() == 'A')");
      obj = null;
      System.gc(); // try to force garbage collector to run
    }

    /*
     * Integer arguments
     */
    if (true) {
      Args.Basic obj = makeObject();
      
      sidl.Integer.Holder out   = new sidl.Integer.Holder();
      sidl.Integer.Holder inout = new sidl.Integer.Holder(3);
      
      startTest(null);
      check(synch.ResultType.PASS,
            (obj.returnbackint() == 3),
            "(obj.returnbackint() == 3)");
      startTest(null);
      check(synch.ResultType.PASS,
            (obj.passinint(3) == true),
            "(obj.passinint(3) == true)");
      startTest(null);
      check(synch.ResultType.PASS,
            (obj.passoutint(out) == true && out.get() == 3),
            "(obj.passoutint(out) == true && out.get() == 3)");
      startTest(null);
      check(synch.ResultType.PASS,
            (obj.passinoutint(inout) == true && inout.get() == -3),
            "(obj.passinoutint(inout) == true && inout.get() == -3)");
      startTest(null);
      check(synch.ResultType.PASS,
            (obj.passeverywhereint(3, out, inout) == 3 
             && out.get() == 3
             && inout.get() == 3),
            "(obj.passeverywhereint(3, out, inout) == 3"
            + " && out.get() == 3"
            + " && inout.get() == 3)");
      obj = null;
      System.gc(); // try to force garbage collector to run
    }
    
    /*
     * Long arguments
     */
    if (true) {
      Args.Basic obj = makeObject();
      
      sidl.Long.Holder out   = new sidl.Long.Holder();
      sidl.Long.Holder inout = new sidl.Long.Holder(3);
      
      startTest(null);
      check(synch.ResultType.PASS,
            (obj.returnbacklong() == 3),
            "(obj.returnbacklong() == 3)");
      startTest(null);
      check(synch.ResultType.PASS,
            (obj.passinlong(3) == true),
            "(obj.passinlong(3) == true)");
      startTest(null);
      check(synch.ResultType.PASS,
            (obj.passoutlong(out) == true && out.get() == 3),
            "(obj.passoutlong(out) == true && out.get() == 3)");
      startTest(null);
      check(synch.ResultType.PASS,
            (obj.passinoutlong(inout) == true && inout.get() == -3),
            "(obj.passinoutlong(inout) == true && inout.get() == -3)");
      startTest(null);
      check(synch.ResultType.PASS,
            (obj.passeverywherelong(3, out, inout) == 3 
             && out.get() == 3
             && inout.get() == 3),
            "(obj.passeverywherelong(3, out, inout) == 3"
            + " && out.get() == 3"
            + " && inout.get() == 3)");
      obj = null;
      System.gc(); // try to force garbage collector to run
    }

    /*
     * Float arguments
     */
    if (true) {
      Args.Basic obj = makeObject();
      
      sidl.Float.Holder out   = new sidl.Float.Holder();
      sidl.Float.Holder inout = new sidl.Float.Holder(3.1F);
      
      startTest(null);
      check(synch.ResultType.PASS,
            (obj.returnbackfloat() == 3.1F),
            "(obj.returnbackfloat() == 3.1F)");
      startTest(null);
      check(synch.ResultType.PASS,
            (obj.passinfloat(3.1F) == true),
            "(obj.passinfloat(3.1F) == true)");
      startTest(null);
      check(synch.ResultType.PASS,
            (obj.passoutfloat(out) == true && out.get() == 3.1F),
            "(obj.passoutfloat(out) == true && out.get() == 3.1F)");
      startTest(null);
      check(synch.ResultType.PASS,
            (obj.passinoutfloat(inout) == true && inout.get() == -3.1F),
            "(obj.passinoutfloat(inout) == true && inout.get() == -3.1F)");
      startTest(null);
      check(synch.ResultType.PASS,
            (obj.passeverywherefloat(3.1F, out, inout) == 3.1F
             && out.get() == 3.1F
             && inout.get() == 3.1F),
            "(obj.passeverywherefloat(3.1F, out, inout) == 3.1F"
            + " && out.get() == 3.1F"
            + " && inout.get() == 3.1F)");
      obj = null;
      System.gc(); // try to force garbage collector to run
    }
    
    /*
     * Double arguments
     */
    if (true) {
      Args.Basic obj = makeObject();
      
      sidl.Double.Holder out   = new sidl.Double.Holder();
      sidl.Double.Holder inout = new sidl.Double.Holder(3.14);
      
      startTest(null);
      check(synch.ResultType.PASS,
            (obj.returnbackdouble() == 3.14),
            "(obj.returnbackdouble() == 3.14)");
      startTest(null);
      check(synch.ResultType.PASS,
            (obj.passindouble(3.14) == true),
            "(obj.passindouble(3.14) == true)");
      startTest(null);
      check(synch.ResultType.PASS,
            (obj.passoutdouble(out) == true && out.get() == 3.14),
            "(obj.passoutdouble(out) == true && out.get() == 3.14)");
      startTest(null);
      check(synch.ResultType.PASS,
            (obj.passinoutdouble(inout) == true && inout.get() == -3.14),
            "(obj.passinoutdouble(inout) == true && inout.get() == -3.14)");
      startTest(null);
      check(synch.ResultType.PASS,
            (obj.passeverywheredouble(3.14, out, inout) == 3.14
             && out.get() == 3.14
             && inout.get() == 3.14),
            "(obj.passeverywheredouble(3.14, out, inout) == 3.14"
            + " && out.get() == 3.14"
            + " && inout.get() == 3.1)");
      obj = null;
      System.gc(); // try to force garbage collector to run
    }
    
    /*
     * FComplex arguments
     */
    if (true) {
      Args.Basic obj = makeObject();
      
      sidl.FloatComplex retval       = null;
      sidl.FloatComplex in           = new sidl.FloatComplex(3.1F, 3.1F);
      sidl.FloatComplex.Holder out   = new sidl.FloatComplex.Holder();
      sidl.FloatComplex.Holder inout = new sidl.FloatComplex.Holder(new sidl.FloatComplex(3.1F, 3.1F));
      
      startTest(null);
      check(synch.ResultType.PASS,
            ((retval = obj.returnbackfcomplex()) != null
             && retval.real() == 3.1F && retval.imag() == 3.1F),
            "((retval = obj.returnbackfcomplex()) != null)"
            + " && (retval.real() == 3.1F) && (retval.imag() == 3.1F))");
      startTest(null);
      check(synch.ResultType.PASS,
            (obj.passinfcomplex(in) == true),
            "(obj.passinfcomplex(in) == true)");
      startTest(null);
      check(synch.ResultType.PASS,
            (obj.passoutfcomplex(out) == true
             && out.get().real() == 3.1F && out.get().imag() == 3.1F),
            "(obj.passoutfcomplex(out) == true"
            + " && out.get().real() == 3.1F && out.get().imag() == 3.1F)");
      startTest(null);
      check(synch.ResultType.PASS,
            (obj.passinoutfcomplex(inout) == true
             && inout.get().real() == 3.1F && inout.get().imag() == -3.1F),
            "(obj.passinoutfcomplex(inout) == true"
            + " && inout.get().real() == 3.1F && inout.get().imag() == -3.1F)");
      startTest(null);
      check(synch.ResultType.PASS,
            ((retval = obj.passeverywherefcomplex(in, out, inout)) != null
             && retval.real() == 3.1F && retval.imag() == 3.1F
             && out.get().real() == 3.1F && out.get().imag() == 3.1F
             && inout.get().real() == 3.1F && inout.get().imag() == 3.1F),
            "((retval = passeverywhereobj.fcomplex(in, out, inout)) != null"
            + " && retval.real() == 3.1F && retval.imag() == 3.1F"
            + " && out.get().real() == 3.1F && out.get().imag() == 3.1F"
            + " && inout.get().real() == 3.1F && inout.get().imag() == 3.1F)");
      obj = null;
      System.gc(); // try to force garbage collector to run
    }
    
    /*
     * DComplex arguments
     */
    if (true) {
      Args.Basic obj = makeObject();
      
      sidl.DoubleComplex retval       = null;
      sidl.DoubleComplex in           = new sidl.DoubleComplex(3.14, 3.14);
      sidl.DoubleComplex.Holder out   = new sidl.DoubleComplex.Holder();
      sidl.DoubleComplex.Holder inout = new sidl.DoubleComplex.Holder(
                                                                      new sidl.DoubleComplex(3.14, 3.14));
      
      startTest(null);
      check(synch.ResultType.PASS,
            ((retval = obj.returnbackdcomplex()) != null
             && retval.real() == 3.14 && retval.imag() == 3.14),
            "((retval = obj.returnbackdcomplex()) != null)"
            + " && (retval.real() == 3.14) && (retval.imag() == 3.14))");
      startTest(null);
      check(synch.ResultType.PASS,
            (obj.passindcomplex(in) == true),
            "(obj.passindcomplex(in) == true)");
      startTest(null);
      check(synch.ResultType.PASS,
            (obj.passoutdcomplex(out) == true
             && out.get().real() == 3.14 && out.get().imag() == 3.14),
            "(obj.passoutdcomplex(out) == true"
            + " && out.get().real() == 3.14 && out.get().imag() == 3.14)");
      startTest(null);
      check(synch.ResultType.PASS,
            (obj.passinoutdcomplex(inout) == true
             && inout.get().real() == 3.14 && inout.get().imag() == -3.14),
            "(obj.passinoutdcomplex(inout) == true"
            + " && inout.get().real() == 3.14 && inout.get().imag() == -3.14)");
      startTest(null);
      check(synch.ResultType.PASS,
            ((retval = obj.passeverywheredcomplex(in, out, inout)) != null
             && retval.real() == 3.14 && retval.imag() == 3.14
             && out.get().real() == 3.14 && out.get().imag() == 3.14
             && inout.get().real() == 3.14 && inout.get().imag() == 3.14),
            "((retval = obj.passeverywheredcomplex(in, out, inout)) != null"
            + " && retval.real() == 3.14 && retval.imag() == 3.14"
            + " && out.get().real() == 3.14 && out.get().imag() == 3.14"
            + " && inout.get().real() == 3.14 && inout.get().imag() == 3.14)");
      obj = null;
      System.gc(); // try to force garbage collector to run
    }
    
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
      
      tests(tracker);

      System.gc(); Thread.sleep(5); // try to force garbage collector to run
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
