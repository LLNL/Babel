//
// File:        UnknownAttributeException.java
// Package:     gov.llnl.babel.symbols
// Copyright:   (c) 2006 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision: 6171 $
// Date:        $Date: 2007-10-08 16:39:28 -0700 (Mon, 08 Oct 2007) $
// Description: Thrown when client requests an unknown attribute
// 
package gov.llnl.babel.symbols;

public class UnknownAttributeException extends RuntimeException {
  public UnknownAttributeException(String msg) {
    super(msg);
  }
};
