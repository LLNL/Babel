//
// File:        ExprString.java
// Package:     gov.llnl.babel.symbols
// Copyright:   (c) 2006 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision: 6171 $
// Date:        $Date: 2007-10-08 16:39:28 -0700 (Mon, 08 Oct 2007) $
// Description: 
// 

package gov.llnl.babel.symbols;
import gov.llnl.babel.symbols.AssertionExpression;
import gov.llnl.babel.symbols.BinaryExpression;
import gov.llnl.babel.symbols.BooleanLiteral;
import gov.llnl.babel.symbols.CharacterLiteral;
import gov.llnl.babel.symbols.DComplexLiteral;
import gov.llnl.babel.symbols.DoubleLiteral;
import gov.llnl.babel.symbols.FComplexLiteral;
import gov.llnl.babel.symbols.FloatLiteral;
import gov.llnl.babel.symbols.IdentifierLiteral;
import gov.llnl.babel.symbols.IntegerLiteral;
import gov.llnl.babel.symbols.LongLiteral;
import gov.llnl.babel.symbols.MethodCall;
import gov.llnl.babel.symbols.StringLiteral;
import gov.llnl.babel.symbols.UnaryExpression;

public class CExprString extends ExprVisitor {
  private String d_prefix = "";

  private final static int s_Cpriority[] = {
    0,                          // no op
    8,                          // logical AND
    13,                         // divide
    9,                          // equals
    10,                         // greater than or equal
    10,                         // greater
    3,                          // if and only iff
    3,                          // implies
    10,                         // less than or equal
    10,                         // less than
    12,                         // minus
    13,                         // modulus
    13,                         // multiply
    9,                          // not equal
    4,                          // logical or
    12,                         // plus
    14,                         // power
    13,                         // remainder
    11,                         // shift left
    11,                         // shift right
    4,                          // logical xor
    8,                          // bitwise and
    6,                          // bitwise or
    7                           // bitwise xor
  };

  private final static String s_Cops[] = {
    "", 
    "&&",                       // logical AND
    "/",                        // divide
    "==",                       // equals
    ">=",                       // greater than or equal
    ">",                        // greater
    null,                       // if and only iff
    null,                       // implies
    "<=",                       // less than or equal
    "<",                        // less than
    "-",                        // minus
    "%",                        // modulus
    "*",                        // multiply
    "!=",                       // not equal
    "||",                       // logical or
    "+",                        // plus
    null,                       // power
    null,                       // remainder
    "<<",                       // shift left
    ">>",                       // shift right
    null,                       // logical xor
    "&",                        // bitwise and
    "|",                        // bitwise or
    "^"                         // bitwise xor
  };

  private final static int s_Cunarypriority[] = {
    0, 15, 15, 15, 15, 15
  };

  private final static String s_Cunary[] = {
    "", "~", null, "-", "!", "+"
  };
  
  public CExprString() {}
  public CExprString(String prefix) {
    if (prefix != null) {
      d_prefix= prefix; 
    }
  }

  private int getOpPriority(AssertionExpression ae) {
    if (ae instanceof BinaryExpression) {
      return s_Cpriority[((BinaryExpression)ae).getOp()];
    }
    else if (ae instanceof UnaryExpression) {
      return s_Cunarypriority[((UnaryExpression)ae).getOp()];
    }
    return 20;
  }

  private String getOp(AssertionExpression ae) {
    String opStr = "";
    if (ae instanceof BinaryExpression) {
      opStr = s_Cops[((BinaryExpression)ae).getOp()];
    }
    else if (ae instanceof UnaryExpression) {
      opStr = s_Cunary[((UnaryExpression)ae).getOp()];
    }
    return opStr;
  }

  public String specialCase(BinaryExpression be, 
                            String lhs,
                            String rhs)
  {
    switch(be.getOp()) {
    case BinaryExpression.IF_AND_ONLY_IF:
      return "(((" + lhs + ") && (" + rhs + ")) "
        + "|| ( (!(" + lhs + ")) && (!(" + rhs + "))))";
    case BinaryExpression.IMPLIES:
      return "((!(" + lhs + ")) || (" + rhs +"))";
    case BinaryExpression.POWER:
      return "pow(" + lhs + ", " + rhs + ")";
    case BinaryExpression.REMAINDER:
      return "(((" + lhs + ")/(" + rhs + " ))*(" + rhs + "))";
    case BinaryExpression.LOGICAL_XOR:
      return "(((" + lhs + ")||(" + rhs + "))&&!(" +
        lhs + "&&" + rhs + "))";
    }
    return "";
  }

  public Object visitBinaryExpression(BinaryExpression be, Object data) {
    final int myOpPriority = getOpPriority(be);
    final String lhs = (String)be.getLeftExpression().accept(this, data);
    final int lhsPriority = getOpPriority(be.getLeftExpression());
    final String rhs = (String)be.getRightExpression().accept(this, data);
    final int rhsPriority = getOpPriority(be.getRightExpression());
    final String opStr = getOp(be);
    if (opStr == null) {
      return specialCase(be, lhs, rhs);
    }
    else {
      StringBuffer result = new StringBuffer(lhs.length() + rhs.length() + 8);
      if (lhsPriority < myOpPriority) {
        result.append('(').append(lhs).append(')');
      }
      else {
        result.append(lhs);
      }
      result.append(opStr);
      if (rhsPriority <= myOpPriority) {
        result.append('(').append(rhs).append(')');
      }
      else {
        result.append(rhs);
      }
      return result.toString();
    }
  }
  
  public Object visitBooleanLiteral(BooleanLiteral bl, Object data) {
    return bl.getValue() ? "TRUE" : "FALSE";
  }

  public Object visitCharacterLiteral(CharacterLiteral cl, Object data) {
    final char value = cl.getValue();
    if (Character.isISOControl(value)) {
      return "'\\0" + Integer.toOctalString(value) + "'";
    }
    else {
      return "'" + value + "'";
    }
  }

  public Object visitDComplexLiteral(DComplexLiteral dcl, Object data) {
    return null;
  }

  public Object visitDoubleLiteral(DoubleLiteral dl, Object data) {
    return dl.getValue().toString();
  }
  
  public Object visitFComplexLiteral(FComplexLiteral fcl, Object data) {
    return null;
  }

  public Object visitFloatLiteral(FloatLiteral fl, Object data) {
    return fl.getValue().toString();
  }

  public Object visitIdentifierLiteral(IdentifierLiteral il, Object data) {
    return d_prefix + il.getIdentifier();
  }

  public Object visitIntegerLiteral(IntegerLiteral il, Object data) {
    return il.getValue().toString();
  }

  public Object visitLongLiteral(LongLiteral ll, Object data) {
    return ll.getValue().toString() + "l";
  }

  public Object visitMethodCall(MethodCall mc, Object data) {
    return null;
  }

  public Object visitStringLiteral(StringLiteral sl, Object data) {
    return "\"" + sl.getValue() + "\"";
  }

  public Object visitUnaryExpression(UnaryExpression ue, Object data) {
    final int myOpPriority = getOpPriority(ue);
    final String rhs = ue.getExpression().accept(this, data).toString();
    final int rhsPriority = getOpPriority(ue.getExpression());
    StringBuffer result = new StringBuffer(rhs.length() + 5);
    result.append(getOp(ue));
    if (rhsPriority < myOpPriority) {
      result.append('(').append(rhs).append(')');
    }
    else {
      result.append(rhs);
    }
    return result.toString();
  }

  public static String toCString(AssertionExpression ae) {
    return (String)ae.accept(new CExprString(), null);
  }
}
