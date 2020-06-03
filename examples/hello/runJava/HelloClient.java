// 
// File:        HelloClient.java
// Copyright:   (c) 2001 Lawrence Livermore National Security, LLC
// Revision:    @(#) $Revision: 6171 $
// Date:        $Date: 2007-10-08 16:39:28 -0700 (Mon, 08 Oct 2007) $
// Description: Simple Hello World Java client
// 

public class HelloClient {
  public static void main(String args[]) {
    try {
      Hello.World h = new Hello.World();
      String msg = h.getMsg();
      System.out.println(msg);
      h = null;
      System.gc();
      Runtime.getRuntime().exit(0); /* workaround for Linux JVM 1.3.1 bug */
    } catch (Throwable ex) {
      System.err.println(ex.toString());
    }
  }
}
