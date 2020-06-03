// 
// File:        HelloDriver.java
// Copyright:   (c) 2001 Lawrence Livermore National Security, LLC
// Revision:    @(#) $Revision: 6171 $
// Date:        $Date: 2007-10-08 16:39:28 -0700 (Mon, 08 Oct 2007) $
// Description: Simple CCA Hello World Java driver
// 

public class HelloDriver {
  public static void main(String args[]) {
    try {
      decaf.Framework decaf = new decaf.Framework();
      gov.cca.TypeMap properties = decaf.createTypeMap();

      gov.cca.ComponentID server = decaf.createInstance("HelloServerInstance",
						    "HelloServer.Component",
                                                    properties);
      gov.cca.ComponentID client = decaf.createInstance("HelloClientInstance",
						    "HelloClient.Component",
                                                    properties);
      decaf.connect(client, "HelloServer", server, "HelloServer");

      gov.cca.Port port = decaf.lookupPort(client, "GoPort");
      gov.cca.ports.GoPort go = (gov.cca.ports.GoPort) port._cast2("gov.cca.ports.GoPort");
      go.go();

      decaf.destroyInstance(server,0.0F);
      decaf.destroyInstance(client,0.0F);
      Runtime.getRuntime().exit(0); /* workaround for Linux JVM 1.3.1 bug */
    } catch (gov.cca.CCAException.Wrapper ccaex) {
      System.err.println(ccaex.getTrace());
      System.exit(-1);
    } catch (Throwable ex) {
      ex.printStackTrace(System.err);
      System.exit(-1);
    }
  }
}
