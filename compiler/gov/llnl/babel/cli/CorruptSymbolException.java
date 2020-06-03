//
// File:        CorruptSymbolException.java
// Package:     gov.llnl.babel.cli
// Copyright:   (c) 2007 Lawrence Livermore National Security, LLC
// Revision:    @(#) $Revision: 6212 $
// Date:        $Date: 2007-10-31 17:07:17 -0700 (Wed, 31 Oct 2007) $
// Description: Exception thrown when the symbol table is corrupt
// 

package gov.llnl.babel.cli;

public class CorruptSymbolException extends Exception {

  /**
   * Create an exception to indicate that the symbol table is in an
   * undefined state.
   */
  public CorruptSymbolException() {
  }
}
