//
// File:        RarrayIndices.java
// Package:     gov.llnl.babel.symbols
// Copyright:   (c) 2006 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision: 6171 $
// Date:        $Date: 2007-10-08 16:39:28 -0700 (Mon, 08 Oct 2007) $
// Description: Visitor to create a set of rarray indices
// 

package gov.llnl.babel.symbols;

import gov.llnl.babel.symbols.ExprVisitor;
import java.util.Set;

public class RarrayIndices extends ExprVisitor
{
  public Object visitIdentifierLiteral(IdentifierLiteral il, Object data)
  {
    Set s = (Set)data;
    s.add(il.getIdentifier());
    return s;
  }
}
