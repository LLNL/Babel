//
// File:        UnaryExpr.java
// Package:     gov.llnl.babel.ast
// Copyright:   (c) 2006 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision: 6171 $
// Date:        $Date: 2007-10-08 16:39:28 -0700 (Mon, 08 Oct 2007) $
// Description: Unary expression data type
// 

package gov.llnl.babel.ast;

import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.visitor.Visitor;

public class UnaryExpr extends ASTNode {
  private ASTNode d_operand = null;   // operand
  private int     d_op = 0;

  public static final int PLUS = 1;
  public static final int MINUS = 2;
  public static final int COMPLEMENT = 3;
  public static final int IS = 4;
  public static final int NOT = 5;

  private static final String s_SIDLop[] = {
    "",
    "+",
    "-",
    "~",
    "is",
    "not"
  };

  public UnaryExpr(ParseTreeNode src, ASTNode parent) {
    super(src, parent);
  }
  
  public UnaryExpr(ParseTreeNode src, ASTNode parent,
                    int operator,
                    ASTNode operand)
  {
    super(src, parent);
    d_operand = operand;
    d_op = operator;
  }

  public void setOperand(ASTNode operand)
  {
    d_operand = operand;
    if (operand != null){
      operand.setParent(this);
    }
  }

  public ASTNode getOperand()
  {
    return d_operand;
  }

  public void setOperator(int op)
  {
    d_op = op;
  }

  public int getOperator()
  {
    return d_op;
  }

  public static String getOpSIDL(int op)
  {
    return s_SIDLop[op];
  }

  public Object accept(Visitor visitor, Object data)
  {
    return visitor.visitUnaryExpr(this, data);
  }
}
