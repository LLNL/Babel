//
// File:        StringLiteral.java
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

public class StringLiteral extends Literal {
  String d_value = "";

  public StringLiteral(ParseTreeNode src, ASTNode parent) {
    super(src,parent);
    d_value = src.name.substring(1, src.name.length()-1);
  }

  public StringLiteral() {
    super(null, null);
  }
  
  public String getString() { return d_value; }

  public void setString(String value) { d_value = value; }

  public Object accept(Visitor v, Object data)
  {
    return v.visitStringLiteral(this, data);
  }
}
