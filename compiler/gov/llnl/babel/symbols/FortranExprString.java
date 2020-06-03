//
// File:        FortranExprString.java
// Package:     gov.llnl.babel.symbols
// Copyright:   (c) 2006 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision: 6952 $
// Date:        $Date: 2010-09-30 17:10:42 -0700 (Thu, 30 Sep 2010) $
// Description: Generate a FORTRAN expression
// 

package gov.llnl.babel.symbols;
import gov.llnl.babel.symbols.AssertionExpression;
import gov.llnl.babel.symbols.BinaryExpression;
import gov.llnl.babel.symbols.BooleanLiteral;
import gov.llnl.babel.symbols.CharacterLiteral;
import gov.llnl.babel.symbols.DComplexLiteral;
import gov.llnl.babel.symbols.FComplexLiteral;
import gov.llnl.babel.symbols.LongLiteral;
import gov.llnl.babel.symbols.StringLiteral;
import gov.llnl.babel.symbols.UnaryExpression;

public class FortranExprString extends CExprString {

  private final static String s_FortranOps[] = {
    "",                         // no op
    ".and.",                    // logical and
    "/",                        // divide
    ".eq.",                     // equals
    ".ge.",                     // greater than or equal
    ".gt.",                     // greater than
    null,                       // if and only if
    null,                       // implies
    ".le.",                     // less than or equal
    ".lt",                      // less than
    "-",                        // minus
    null,                       // modulus
    "*",                        // multiply
    ".ne.",                     // not equal
    ".or.",                     // logical or
    "+",                        // plus
    "**",                       // power
    null,                       // remainder
    null,                       // shift left
    null,                       // shift right
    ".neqv.",                   // logical xor
    null,                       // bitwise and
    null,                       // bitwise or
    null                        // bitwise xor
  };


  private final static String s_FortranUOps[] = {
    "", null, null, "-", ".not.", "+"
  };
  
  public FortranExprString(String prefix) { super(prefix); }

  private String getOp(AssertionExpression ae) {
    if (ae instanceof BinaryExpression) {
      return s_FortranOps[((BinaryExpression)ae).getOp()];
    }
    else if (ae instanceof UnaryExpression) {
      return s_FortranUOps[((UnaryExpression)ae).getOp()];
    }
    else return "";
  }

  private String specialCase(BinaryExpression be)
  {
    final String lhs = (String)be.getLeftExpression().accept(this, null);
    final String rhs = (String)be.getRightExpression().accept(this, null);
    StringBuffer buffer = new StringBuffer();
    switch (be.getOp()) {
    case BinaryExpression.MODULUS:
    case BinaryExpression.REMAINDER:
      buffer.append("mod((").append(lhs).append("), (").append(rhs).
        append("))");
      break;
    case BinaryExpression.IF_AND_ONLY_IF:
      buffer.append("(((").append(lhs).append(") .and. (").
        append(rhs).append(")) .or. ( (.not.(").
        append( lhs).append(")) .and. (.not.(").append(rhs).append("))))");
      break;
    case BinaryExpression.IMPLIES:
      buffer.append("((.not.(").append(lhs).append(")) .or. (").append(rhs).
        append("))");
      break;
    case BinaryExpression.SHIFT_LEFT:
      buffer.append("iand((").append(lhs).append("), (").append(rhs).
        append("))");
      break;
    case BinaryExpression.SHIFT_RIGHT:
      buffer.append("iand((").append(lhs).append("), -(").append(rhs).
        append("))");
      break;
    case BinaryExpression.BITWISE_AND:
      buffer.append("iand((").append(lhs).append("), (").append(rhs).
        append("))");
      break;
    case BinaryExpression.BITWISE_OR:
      buffer.append("ior((").append(lhs).append("), (").append(rhs).
        append("))");
      break;
    case BinaryExpression.BITWISE_XOR:
      buffer.append("ieor((").append(lhs).append("), (").append(rhs).
        append("))");
      break;
    }
    return buffer.toString();
  }

  public Object visitBinaryExpression(BinaryExpression be, Object data) {
    if (getOp(be) == null) {
      return specialCase(be);
    }
    else {
      return super.visitBinaryExpression(be, data);
    }
  }

  public Object visitBooleanLiteral(BooleanLiteral bl, Object data) {
    return bl.getValue() ? ".true." : ".false.";
  }

  public Object visitCharacterLiteral(CharacterLiteral cl, Object data) {
    return "'" + cl.getValue() + "'";
  }

  public Object visitDComplexLiteral(DComplexLiteral dcl, Object data) {
    return "(" + dcl.getRealValue().toString() + "," +
      dcl.getRealValue().toString() + ")";
  }
  
  public Object visitFComplexLiteral(FComplexLiteral fcl, Object data) {
    return "(" + fcl.getRealValue().toString() + "," +
      fcl.getRealValue().toString() + ")";
  }

  public Object visitLongLiteral(LongLiteral ll, Object data) {
    return ll.getValue().toString();
  }

  public Object visitStringLiteral(StringLiteral sl, Object data) {
    return "'" + sl.getValue() + "'";
  }

  public static String toFortranString(AssertionExpression ae) {
    return (String)ae.accept(new FortranExprString(""), null);
  }
}
