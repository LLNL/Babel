//
// File:        CollectionException.java
// Package:     gov.llnl.babel
// Copyright:   (c) 2003 Lawrence Livermore National Security, LLC
// Revision:    @(#) $Revision: 6211 $
// Date:        $Date: 2007-10-29 21:09:40 -0700 (Mon, 29 Oct 2007) $
// Description: An exception to hold a collection of exceptions
// 

package gov.llnl.babel;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * When parsing a collection of files, it is better to collect all the
 * exceptions until the end instead of bailing out on the first exception.
 * This exception class holds a collection of other exceptions.
 */
public class CollectionException extends Exception
{
  /**
   * 
   */

  private static final long serialVersionUID = -8616804579566144307L;

  final private static String EOL="\n";
  
  /**
   * This struct exists to hold the (uri, exception) pair that makes
   * up each element of the collection.
   */
  final public static class UriExceptionPair {
    private String    d_uri;
    private Exception d_exception;

    public UriExceptionPair(String uri, Exception ex)
    {
      d_uri = uri;
      d_exception = ex;
    }
    
    public String getUri()
    {
      return d_uri;
    }
    
    public Exception getException()
    {
      return d_exception;
    }
  }

  List d_exceptions = new LinkedList();

  public CollectionException(String uri, Exception ex)
  {
    addException(uri, ex);
  }

  public void addException(String uri, Exception ex)
  {
    UriExceptionPair uep = new UriExceptionPair(uri, ex);
    d_exceptions.add(uep);
  }

  public String getMessage()
  {
    StringBuffer buf = new StringBuffer();
    Iterator i = d_exceptions.iterator();
    while(i.hasNext()) {
      UriExceptionPair uep = (UriExceptionPair)i.next();
      buf.append("Babel: ");
      buf.append(uep.getException().getClass().getName());
      if (uep.getUri() != null) {
        buf.append(" when parsing URL \"");
        buf.append(uep.getUri());
        buf.append("\"...");
      }
      buf.append(EOL);
      buf.append(uep.getException().getMessage());
      buf.append(EOL);
    }
    return buf.toString();
  }
}
