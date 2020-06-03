// 
// File:        InheritTest.java
// Copyright:   (c) 2001 Lawrence Livermore National Security, LLC
// Revision:    @(#) $Revision: 7003 $
// Date:        $Date: 2010-11-18 17:01:04 -0800 (Thu, 18 Nov 2010) $
// Description: inherit regression test case for Java calling other languages
// 

/**
 * The following class runs the inherit regression test cases for Java.
 */
public class InheritTest {

  private final static String[] s_results = {
    "FAIL",
    "XFAIL",
    "synch.ResultType.PASS",
    "Xsynch.ResultType.PASS",
    "UNSUPPORTED"
  };

  private static int s_part = 0;
  private static long s_result = synch.ResultType.PASS;

  //basic RMI support
  private static String remoteURL = null;

  private static boolean withRMI() {
    return remoteURL != null && remoteURL.length() > 0;
  }
  
  // C D E E2 F F2 G G2 I J K L
  private static Inherit.C makeCObject() {
    try {
      if(withRMI())
        return new Inherit.C(remoteURL);
      return new Inherit.C();
    } catch (Throwable ex) {
      ex.printStackTrace();
      System.exit(1);
    }
    return null;
  }
  
  private static Inherit.D makeDObject() {
    try {
      if(withRMI())
        return new Inherit.D(remoteURL);
      return new Inherit.D();
    } catch (Throwable ex) {
      ex.printStackTrace();
      System.exit(1);
    }
    return null;
  }
  
  private static Inherit.E makeEObject() {
    try {
      if(withRMI())
        return new Inherit.E(remoteURL);
      return new Inherit.E();
    } catch (Throwable ex) {
      ex.printStackTrace();
      System.exit(1);
    }
    return null;
  }
  
  private static Inherit.E2 makeE2Object() {
    try {
      if(withRMI())
        return new Inherit.E2(remoteURL);
      return new Inherit.E2();
    } catch (Throwable ex) {
      ex.printStackTrace();
      System.exit(1);
    }
    return null;
  }
  
  private static Inherit.F makeFObject() {
    try {
      if(withRMI())
        return new Inherit.F(remoteURL);
      return new Inherit.F();
    } catch (Throwable ex) {
      ex.printStackTrace();
      System.exit(1);
    }
    return null;
  }
  
  private static Inherit.F2 makeF2Object() {
    try {
      if(withRMI())
        return new Inherit.F2(remoteURL);
      return new Inherit.F2();
    } catch (Throwable ex) {
      ex.printStackTrace();
      System.exit(1);
    }
    return null;
  }
  
  private static Inherit.G makeGObject() {
    try {
      if(withRMI())
        return new Inherit.G(remoteURL);
      return new Inherit.G();
    } catch (Throwable ex) {
      ex.printStackTrace();
      System.exit(1);
    }
    return null;
  }
  
  private static Inherit.G2 makeG2Object() {
    try {
      if(withRMI())
        return new Inherit.G2(remoteURL);
      return new Inherit.G2();
    } catch (Throwable ex) {
      ex.printStackTrace();
      System.exit(1);
    }
    return null;
  }
  
  private static Inherit.I makeIObject() {
    try {
      if(withRMI())
        return new Inherit.I(remoteURL);
      return new Inherit.I();
    } catch (Throwable ex) {
      ex.printStackTrace();
      System.exit(1);
    }
    return null;
  }
  
  private static Inherit.J makeJObject() {
    try {
      if(withRMI())
        return new Inherit.J(remoteURL);
      return new Inherit.J();
    } catch (Throwable ex) {
      ex.printStackTrace();
      System.exit(1);
    }
    return null;
  }
  
  private static Inherit.K makeKObject() {
    try {
      if(withRMI())
        return new Inherit.K(remoteURL);
      return new Inherit.K();
    } catch (Throwable ex) {
      ex.printStackTrace();
      System.exit(1);
    }
    return null;
  }
  
  private static Inherit.L makeLObject() {
    try {
      if(withRMI())
        return new Inherit.L(remoteURL);
      return new Inherit.L();
    } catch (Throwable ex) {
      ex.printStackTrace();
      System.exit(1);
    }
    return null;
  }
  
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
    /*
     * Test Class C
     */
    if (true) {
      Inherit.C c = makeCObject();
      startTest(null);
      check(synch.ResultType.PASS, scomp(c.c(),"C.c"), "C.c");
    }

    /*
     * Test Class D
     */
    if (true) {
      Inherit.D d = makeDObject();
      startTest(null);
      check(synch.ResultType.PASS,scomp(d.a(),"D.a"), "D.a");
      startTest(null);
      check(synch.ResultType.PASS,scomp(d.d(),"D.d"), "D.d");
      Inherit.A a = d;
      startTest(null);
      check(synch.ResultType.PASS,scomp(a.a(),"D.a"), "D.a");
    }

    /*
     * Test Class E
     */
    if (true) {
      Inherit.E e = makeEObject();
      startTest(null);
      check(synch.ResultType.PASS,scomp(e.c(),"C.c"), "C.c");
      startTest(null);
      check(synch.ResultType.PASS,scomp(e.e(),"E.e"), "E.e");
      Inherit.C c = e;
      startTest(null);
      check(synch.ResultType.PASS,scomp(c.c(),"C.c"), "C.c");
    }
    
    /*
     * Test Class E2
     */
    if (true) {
      Inherit.E2 e2 = makeE2Object();
      startTest(null);
      check(synch.ResultType.PASS,scomp(e2.c(),"E2.c"), "E2.c");
      startTest(null);
      check(synch.ResultType.PASS,scomp(e2.e(),"E2.e"), "E2.e");
      Inherit.C c = e2;
      startTest(null);
      check(synch.ResultType.PASS,scomp(c.c(),"E2.c"), "E2.c");
      startTest(null);
      check(synch.ResultType.PASS,scomp(Inherit.E2.m(), "E2.m"), "E2.m");
    }

    /*
     * Test Class F
     */
    if (true) {
      Inherit.F f = makeFObject();
      startTest(null);
      check(synch.ResultType.PASS,scomp(f.a(),"F.a"), "F.a");
      startTest(null);
      check(synch.ResultType.PASS,scomp(f.b(),"F.b"), "F.b");
      startTest(null);
      check(synch.ResultType.PASS,scomp(f.c(),"C.c"), "C.c");
      startTest(null);
      check(synch.ResultType.PASS,scomp(f.f(),"F.f"), "F.f");
      Inherit.A a = f;
      startTest(null);
      check(synch.ResultType.PASS,scomp(a.a(),"F.a"), "F.a");
      Inherit.B b = f;
      startTest(null);
      check(synch.ResultType.PASS,scomp(b.b(),"F.b"), "F.b");
      Inherit.C c = f;
      startTest(null);
      check(synch.ResultType.PASS,scomp(c.c(),"C.c"), "C.c");
    }

    /*
     * Test Class F2
     */
    if (true) {
      Inherit.F2 f2 = makeF2Object();
      startTest(null);
      check(synch.ResultType.PASS,scomp(f2.a(),"F2.a"), "F2.a");
      startTest(null);
      check(synch.ResultType.PASS,scomp(f2.b(),"F2.b"), "F2.b");
      startTest(null);
      check(synch.ResultType.PASS,scomp(f2.c(),"F2.c"), "F2.c");
      startTest(null);
      check(synch.ResultType.PASS,scomp(f2.f(),"F2.f"), "F2.f");
      Inherit.A a = f2;
      startTest(null);
      check(synch.ResultType.PASS,scomp(a.a(),"F2.a"), "F2.a");
      Inherit.B b = f2;
      startTest(null);
      check(synch.ResultType.PASS,scomp(b.b(),"F2.b"), "F2.b");
      Inherit.C c = f2;
      startTest(null);
      check(synch.ResultType.PASS,scomp(c.c(),"F2.c"), "F2.c");
    }

    /*
     * Test Class G
     */
    if (true) {
      Inherit.G g = makeGObject();
      startTest(null);
      check(synch.ResultType.PASS,scomp(g.a(),"D.a"), "D.a");
      startTest(null);
      check(synch.ResultType.PASS,scomp(g.d(),"D.d"), "D.d");
      startTest(null);
      check(synch.ResultType.PASS,scomp(g.g(),"G.g"), "G.g");
      Inherit.A a = g;
      startTest(null);
      check(synch.ResultType.PASS,scomp(a.a(),"D.a"), "D.a");
      Inherit.D d = g;
      startTest(null);
      check(synch.ResultType.PASS,scomp(d.a(),"D.a"), "D.a");
      startTest(null);
      check(synch.ResultType.PASS,scomp(d.d(),"D.d"), "D.d");
    }

    /*
     * Test Class G
     */
    if (true) {
      Inherit.G2 g2 = makeG2Object();
      startTest(null);
      check(synch.ResultType.PASS,scomp(g2.a(),"G2.a"), "G2.a");
      startTest(null);
      check(synch.ResultType.PASS,scomp(g2.d(),"G2.d"), "G2.d");
      startTest(null);
      check(synch.ResultType.PASS,scomp(g2.g(),"G2.g"), "G2.g");
      Inherit.A a = g2;
      startTest(null);
      check(synch.ResultType.PASS,scomp(a.a(),"G2.a"), "G2.a");
      Inherit.D d = g2;
      startTest(null);
      check(synch.ResultType.PASS,scomp(d.a(),"G2.a"), "G2.a");
      startTest(null);
      check(synch.ResultType.PASS,scomp(d.d(),"G2.d"), "G2.d");
    }

    /*
     * Test Class I
     */
    if (true) {
      Inherit.I i = makeIObject();
      startTest(null);
      check(synch.ResultType.PASS,scomp(i.a(),"I.a"), "I.a");
      startTest(null);
      check(synch.ResultType.PASS,scomp(i.h(),"I.h"), "I.h");
      Inherit.A a = i;
      startTest(null);
      check(synch.ResultType.PASS,scomp(a.a(),"I.a"), "I.a");
      Inherit.H h = i;
      startTest(null);
      check(synch.ResultType.PASS,scomp(h.a(),"I.a"), "I.a");
      startTest(null);
      check(synch.ResultType.PASS,scomp(h.h(),"I.h"), "I.h");
    }
      
    /*
     * Test Class J
     */
    if (true) {
      Inherit.J j = makeJObject();
      startTest(null);
      check(synch.ResultType.PASS,scomp(j.a(),"J.a"), "J.a");
      startTest(null);
      check(synch.ResultType.PASS,scomp(j.b(),"J.b"), "J.b");
      startTest(null);
      check(synch.ResultType.PASS,scomp(j.j(),"J.j"), "J.j");
      startTest(null);
      check(synch.ResultType.PASS,scomp(j.c(),"J.E2.c"), "J.E2.c");
      startTest(null);
      check(synch.ResultType.PASS,scomp(j.e(),"J.E2.e"), "J.E2.e");
      startTest(null);
      check(synch.ResultType.PASS,scomp(Inherit.J.m(), "E2.m"), "J.m");
    } 

    /*
     * Test Class K
     */
    if (true) {
      Inherit.K k = makeKObject();
      startTest(null);
      check(synch.ResultType.PASS,scomp(k.a(),"K.a"), "K.a");
      startTest(null);
      check(synch.ResultType.PASS,scomp(k.a(0),"K.a2"), "K.a2");
      startTest(null);
      check(synch.ResultType.PASS,scomp(k.k(),"K.k"), "K.k");
      startTest(null);
      check(synch.ResultType.PASS,scomp(k.h(),"K.h"), "K.h");
      Inherit.A a = k;
      startTest(null);
      check(synch.ResultType.PASS,scomp(a.a(),"K.a"), "K.a");
      Inherit.A2 a2 = k;
      startTest(null);
      check(synch.ResultType.PASS,scomp(a2.a(0),"K.a2"), "K.a2");
      
      Inherit.H h = k;
      startTest(null);
      check(synch.ResultType.PASS,scomp(h.a(),"K.a"), "K.a");
      startTest(null);
      check(synch.ResultType.PASS,scomp(h.h(),"K.h"), "K.h");
    }


    /*
     * Test Class L
     */
    if (true) {
      Inherit.L l = makeLObject();
      startTest(null);
      check(synch.ResultType.PASS,scomp(l.a(),"L.a"), "L.a");
      startTest(null);
      check(synch.ResultType.PASS,scomp(l.a(0),"L.a2"), "L.a2");
      startTest(null);
      check(synch.ResultType.PASS,scomp(l.l(),"L.l"), "L.l");
      
      Inherit.A a = l;
      startTest(null);
      check(synch.ResultType.PASS,scomp(a.a(),"L.a"), "L.a");
      Inherit.A2 a2 = l;
      startTest(null);
      check(synch.ResultType.PASS,scomp(a2.a(0),"L.a2"), "L.a2");
      
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
      System.gc(); Thread.sleep(5); //try to force garbage collector to run
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
