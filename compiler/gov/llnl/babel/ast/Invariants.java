//
// File:        Invariants.java
// Package:     gov.llnl.babel.ast
// Copyright:   (c) 2006 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision: 5332 $
// Date:        $Date: 2006-04-03 12:25:47 -0700 (Mon, 03 Apr 2006) $
// Description: Hold an invariant assertion list (i.e., invariant clause).
// 

package gov.llnl.babel.ast;
import gov.llnl.babel.visitor.Visitor;
import gov.llnl.babel.parsers.sidl2.ParseTreeNode;

import java.util.List;

public class Invariants extends ASTNode {
  List d_invariantExprs = null;

  public Invariants(ParseTreeNode node, ASTNode parent)
  {
    super(node, parent);
  }

  public Invariants()
  {
    super(null, null);
  }

  public List getInvariantExprs() { 
    return d_invariantExprs;
  }

  public void setInvariantExprs(List invariantExprs)
  {
    d_invariantExprs = invariantExprs;
  }

  public Object accept(Visitor v, Object data)
  {
    return v.visitInvariants(this, data);
  }
}
