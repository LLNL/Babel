//
// File:        Assertion.java
// Package:     gov.llnl.babel.ast
// Copyright:   (c) 2006 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision: 6171 $
// Date:        $Date: 2007-10-08 16:39:28 -0700 (Mon, 08 Oct 2007) $
// Description: Hold a requires or ensures assertion.
// 

package gov.llnl.babel.ast;

import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.visitor.Visitor;

public class Assertion extends ASTNode implements INameable {
  private Name d_name = null;
  private ASTNode d_expr = null;
  private String d_source = "";

  public Assertion(ParseTreeNode src, ASTNode parent) {
    super(src, parent);
  }

  public Assertion()
  {
    super(null, null);
  }

  public void setName(Name name)
  {
    d_name = name;
  }

  public Name getName()
  {
    return d_name;
  }

  public ASTNode getExpr()
  {
    return d_expr;
  }

  public void setExpr(ASTNode expr)
  {
    d_expr = expr;
  }

  public String getSource()
  {
    return d_source;
  }

  public void setSource(String source)
  {
    d_source = source;
  }

  public Object accept(Visitor v, Object data) {
    return v.visitAssertion(this, data);
  }
}
