//
// File:        Requires.java
// Package:     gov.llnl.babel.ast
// Copyright:   (c) 2006 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision: 6289 $
// Date:        $Date: 2008-01-11 07:59:07 -0800 (Fri, 11 Jan 2008) $
// Description: Hold an requires assertion list (i.e., precondition clause).
// 

package gov.llnl.babel.ast;
import gov.llnl.babel.visitor.Visitor;
import gov.llnl.babel.parsers.sidl2.ParseTreeNode;

import java.util.List;

public class Requires extends ASTNode {
  List d_requireExprs = null;

  public Requires(ParseTreeNode node, ASTNode parent)
  {
    super(node, parent);
  }

  public Requires()
  {
    super(null, null);
  }

  public List getRequireExprs() { 
    return d_requireExprs;
  }

  public void setRequireExprs(List requireExprs)
  {
    d_requireExprs = requireExprs;
  }

  public Object accept(Visitor v, Object data)
  {
    return v.visitRequires(this, data);
  }
}
