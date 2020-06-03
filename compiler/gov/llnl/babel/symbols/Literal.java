//
// File:        Literal.java
// Package:     gov.llnl.babel.symbols
// Copyright:   (c) 2006 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision: 6196 $
// Date:        $Date: 2007-10-24 16:42:37 -0700 (Wed, 24 Oct 2007) $
// Description: A intermediate class for literals
// 

package gov.llnl.babel.symbols;

import gov.llnl.babel.Context;

public abstract class Literal extends AssertionExpression {

  Literal(boolean valid, Context context) {
    super(valid, context);
  }

}
