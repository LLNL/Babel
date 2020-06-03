//
// File:        Ensures.java
// Package:     gov.llnl.babel.ast
// Copyright:   (c) 2006 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision: 6289 $
// Date:        $Date: 2008-01-11 07:59:07 -0800 (Fri, 11 Jan 2008) $
// Description: Hold an ensures assertion list (i.e., postcondition clause).
// 

package gov.llnl.babel.ast;
import gov.llnl.babel.visitor.Visitor;
import gov.llnl.babel.parsers.sidl2.ParseTreeNode;

import java.util.List;

public class Ensures extends ASTNode {
  List d_ensureExprs = null;

  public Ensures(ParseTreeNode node, ASTNode parent)
  {
    super(node, parent);
  }

  public Ensures()
  {
    super(null, null);
  }

  public List getEnsureExprs() { 
    return d_ensureExprs;
  }

  public void setEnsureExprs(List ensureExprs)
  {
    d_ensureExprs = ensureExprs;
  }

  public Object accept(Visitor v, Object data)
  {
    return v.visitEnsures(this, data);
  }
}
