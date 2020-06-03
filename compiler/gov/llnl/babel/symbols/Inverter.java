//
// File:        Inverter.java
// Package:     gov.llnl.babel.symbols
// Copyright:   (c) 2006 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision: 6196 $
// Date:        $Date: 2007-10-24 16:42:37 -0700 (Wed, 24 Oct 2007) $
// Description: Visitor to invert an expression
// 

package gov.llnl.babel.symbols;

import gov.llnl.babel.Context;
import gov.llnl.babel.symbols.AssertionExpression;
import gov.llnl.babel.symbols.AssertionException;
import gov.llnl.babel.symbols.BinaryExpression;
import gov.llnl.babel.symbols.ExprVisitor;
import gov.llnl.babel.symbols.IdentifierLiteral;
import gov.llnl.babel.symbols.UnaryExpression;

public class Inverter extends ExprVisitor {
  private Context d_context;

  public Inverter(Context context) {
    d_context = context;
  }

  private static class HasVariable extends ExprVisitor {
    public Object visitIdentifierLiteral(IdentifierLiteral il, Object data) {
      return il;
    }
  }
  
  public Object visitBinaryExpression(BinaryExpression be, Object data)   {
    final boolean lhsHasVariable = null !=
      be.getLeftExpression().accept(new HasVariable(), null);
    final boolean rhsHasVariable = null !=
      be.getRightExpression().accept(new HasVariable(), null);
    try {
      if (lhsHasVariable) {
        switch(be.getOp()) {
        case BinaryExpression.DIVIDE:
          return be.getLeftExpression().
            accept(this, new BinaryExpression((AssertionExpression)data,
                                              BinaryExpression.MULTIPLY,
                                              be.getRightExpression(), 
                                              d_context));
        case BinaryExpression.MULTIPLY:
          return be.getLeftExpression().
            accept(this, new BinaryExpression((AssertionExpression)data,
                                              BinaryExpression.DIVIDE,
                                              be.getRightExpression(),
                                              d_context));
        case BinaryExpression.PLUS:
          return be.getLeftExpression().
            accept(this, new BinaryExpression((AssertionExpression)data,
                                              BinaryExpression.MINUS,
                                              be.getRightExpression(),
                                              d_context));
        case BinaryExpression.MINUS:
          return be.getLeftExpression().
            accept(this, new BinaryExpression((AssertionExpression)data,
                                              BinaryExpression.PLUS,
                                              be.getRightExpression(),
                                              d_context));
        }
      } else if (rhsHasVariable) {
        switch(be.getOp()) {
        case BinaryExpression.DIVIDE:
          return be.getRightExpression().
            accept(this, new BinaryExpression((AssertionExpression)data,
                                              BinaryExpression.DIVIDE,
                                              be.getLeftExpression(),
                                              d_context));
        case BinaryExpression.MULTIPLY:
          return be.getRightExpression().
            accept(this, new BinaryExpression((AssertionExpression)data,
                                              BinaryExpression.DIVIDE,
                                              be.getLeftExpression(),
                                              d_context));
        case BinaryExpression.PLUS:
          return be.getRightExpression().
            accept(this, new BinaryExpression((AssertionExpression)data,
                                              BinaryExpression.MINUS,
                                              be.getLeftExpression(),
                                              d_context));
        case BinaryExpression.MINUS:
          return be.getRightExpression().
            accept(this, new BinaryExpression(be.getLeftExpression(),
                                              BinaryExpression.MINUS,
                                              (AssertionExpression)data,
                                              d_context));
        }
      }
    }
    catch (AssertionException aexcpt) {
      aexcpt.printStackTrace();
    }
    return null;
  }

  public Object visitUnaryExpression(UnaryExpression ue, Object data) {
    try {
      if ( null != ue.getExpression().accept(new HasVariable(), null)) {
        switch(ue.getOp()) {
        case UnaryExpression.PLUS: 
          return ue.getExpression().accept(this, data);
        case UnaryExpression.MINUS:
          return ue.getExpression().
            accept(this, new UnaryExpression(UnaryExpression.MINUS,
                                             (AssertionExpression)data,
                                             d_context));
        }
      }
    }
    catch (AssertionException ae) {
      ae.printStackTrace();
    }
    return null;
  }

  public static AssertionExpression invertExpr(AssertionExpression ae,
                                               String lengthExpr,
                                               Context context) {
    try {
      return (AssertionExpression)
        ae.accept(new Inverter(context), new IdentifierLiteral(lengthExpr, context));
    }
    catch (AssertionException aexcpt) {
      aexcpt.printStackTrace();
    }
    return null;
  }
}
