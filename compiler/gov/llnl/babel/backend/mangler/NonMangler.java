//
// File:        NonMangler.java
// Package:     gov.llnl.babel.backend.mangler
// Copyright:   (c) 2002 Lawrence Livermore National Security, LLC
// Revision:    @(#) $Revision: 6171 $
// Date:        $Date: 2007-10-08 16:39:28 -0700 (Mon, 08 Oct 2007) $
// Description: A NameMangler that doesn't mangle names (leaves them long)
// 

package gov.llnl.babel.backend.mangler;

/**
 * This mangler doesn't do any mangling. It simply creates the whole
 * long name.
 */
public class NonMangler implements NameMangler {
  
  /**
   * This returns the full, unmangled name.
   */
  public String shortName(String symbol, String method, String suffix)
  {
    StringBuffer result = new StringBuffer
      (symbol.length() + method.length() + suffix.length() + 1);
    result.append(symbol.replace('.','_')).append('_').append(method)
      .append(suffix);
    return result.toString();
  }

  /**
   * This returns the full, unmangled name.
   */
  public String shortArrayName(String symbol, String method, String suffix)
  {
    StringBuffer result = new StringBuffer
      (symbol.length() + method.length() + suffix.length() + 9);
    result.append(symbol.replace('.', '_')).append("__array_").append(method)
      .append(suffix);
    return result.toString();
  }

  /**
   * This return the full, unmangled name.
   */
  public String shortName(String symbol, String suffix)
  {
    StringBuffer result = new StringBuffer(symbol.length() + suffix.length());
    result.append(symbol.replace('.', '_')).append(suffix);
    return result.toString();
  }
}
