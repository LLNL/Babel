//
// File:        BooleanLiteral.java
// Package:     gov.llnl.babel.ast
// Copyright:   (c) 2006 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision: 6171 $
// Date:        $Date: 2007-10-08 16:39:28 -0700 (Mon, 08 Oct 2007) $
// Description: AST node to hold a string
// 

package gov.llnl.babel.ast;
import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.visitor.Visitor;

public class BooleanLiteral extends Literal {
  private boolean d_value = false;

  public BooleanLiteral(ParseTreeNode src, ASTNode parent) {
    super(src,parent);
    d_value = "true".equals(src.name);
  }

  public BooleanLiteral() {
    super(null, null);
  }
  
  public boolean getBoolean() { return d_value; }

  public void setBoolean(boolean value) { d_value = value; }

  public Object accept(Visitor v, Object data)
  {
    return v.visitBooleanLiteral(this, data);
  }
}
