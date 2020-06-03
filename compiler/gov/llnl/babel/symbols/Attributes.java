//
// File:        Attributes.java
// Package:     gov.llnl.babel.symbols
// Copyright:   (c) 2006 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision: 6171 $
// Date:        $Date: 2007-10-08 16:39:28 -0700 (Mon, 08 Oct 2007) $
// Description: A generalized interface for language attributes
// 

package gov.llnl.babel.symbols;
import java.util.Set;

public interface Attributes {
  public boolean hasAttribute(String key);

  public String getAttribute(String key) throws UnknownAttributeException;

  public void setAttribute(String key);

  public void setAttribute(String key, String value);

  public void removeAttribute(String key) throws UnknownAttributeException;

  public Set getAttributes();
}
