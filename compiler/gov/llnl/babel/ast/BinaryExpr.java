//
// File:        BinaryExpr.java
// Package:     gov.llnl.babel.ast
// Copyright:   (c) 2006 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision: 6171 $
// Date:        $Date: 2007-10-08 16:39:28 -0700 (Mon, 08 Oct 2007) $
// Description: Store a binary expression
// 

package gov.llnl.babel.ast;

import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.visitor.Visitor;

public class BinaryExpr extends ASTNode {
  private ASTNode d_lhs = null;   // left hand side of operator
  private ASTNode d_rhs = null;   // right hand side of operator
  private int     d_op = 0;

  public static final int PLUS = 1;
  public static final int MINUS = 2;
  public static final int MULTIPLY = 3;
  public static final int DIVIDE = 4;
  public static final int POWER = 5;
  public static final int LESSTHAN = 6;
  public static final int LESSTHANEQ = 7;
  public static final int GREATERTHAN = 8;
  public static final int GREATERTHANEQ = 9;
  public static final int EQUAL = 10;
  public static final int LOGICAL_AND = 11;
  public static final int LOGICAL_OR = 12;
  public static final int LOGICAL_XOR = 13;
  public static final int BITWISE_AND = 14;
  public static final int BITWISE_OR = 15;
  public static final int BITWISE_XOR = 16;
  public static final int SHIFT_LEFT = 17;
  public static final int SHIFT_RIGHT = 18;
  public static final int IFF = 19;
  public static final int IMPLIES = 20;
  public static final int MODULUS = 21;
  public static final int REMAINDER = 22;
  public static final int NOT_EQUAL = 23;

  private static final String s_SIDLop[] = {
    "",
    "+",
    "-",
    "*",
    "/",
    "**",
    "<",
    "<=",
    ">",
    ">=",
    "==",
    "and",
    "or",
    "xor",
    "&",
    "|",
    "^",
    "<<<",
    ">>>",
    "iff",
    "implies",
    "%",
    "rem",
    "!="
  };

  public BinaryExpr(ParseTreeNode src, ASTNode parent) {
    super(src, parent);
  }
  
  public BinaryExpr(ParseTreeNode src, ASTNode parent,
                    int operator,
                    ASTNode lhs, ASTNode rhs)
  {
    super(src, parent);
    d_lhs = lhs;
    d_rhs = rhs;
    d_op = operator;
  }

  public void setRHS(ASTNode rhs)
  {
    d_rhs = rhs;
    if (rhs != null) {
      rhs.setParent(this);
    }
  }

  public void setLHS(ASTNode lhs)
  {
    d_lhs = lhs;
    if (lhs != null) {
      lhs.setParent(this);
    }
  }

  public ASTNode getRHS()
  {
    return d_rhs;
  }

  public ASTNode getLHS()
  {
    return d_lhs;
  }

  public void setOperator(int op)
  {
    d_op = op;
  }

  public int getOperator()
  {
    return d_op;
  }

  public Object accept(Visitor visitor, Object data)
  {
    return visitor.visitBinaryExpr(this, data);
  }

  /**
   * Return the SIDL operator string.
   */
  public static String getOpSIDL(int op)
  {
    return s_SIDLop[op];
  }
}
