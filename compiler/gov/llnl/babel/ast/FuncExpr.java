//
// File:        FuncExpr.java
// Package:     gov.llnl.babel.ast
// Copyright:   (c) 2006 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision: 6171 $
// Date:        $Date: 2007-10-08 16:39:28 -0700 (Mon, 08 Oct 2007) $
// Description: AST node to hold a function call
// 

package gov.llnl.babel.ast;
import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.visitor.Visitor;

import java.util.List;

public class FuncExpr extends ASTNode {
  private String   d_funcName = null;  // name of the function
  private List     d_arguments = null; 

  public FuncExpr(ParseTreeNode node, ASTNode parent)
  {
    super(node, parent);
  }

  public FuncExpr() {
    super(null, null);
  }

  public void setName(String funcName)
  {
    d_funcName = funcName;
  }

  public String getName() { return d_funcName; }

  public List getArguments() { return d_arguments; }
  
  public void setArguments(List arguments) 
  {
    d_arguments = arguments;
  }

  public Object accept(Visitor v, Object data)
  {
    return v.visitFuncExpr(this, data);
  }
}
