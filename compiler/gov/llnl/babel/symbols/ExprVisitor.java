//
// File:        ExprVisitor.java
// Package:     gov.llnl.babel.symbols
// Copyright:   (c) 2006 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision: 6171 $
// Date:        $Date: 2007-10-08 16:39:28 -0700 (Mon, 08 Oct 2007) $
// Description: A visitor for the assertion expression.
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
import java.util.Iterator;

public class ExprVisitor {
  
  public Object visitExpr(AssertionException ae, Object data) {
    return data;
  }

  public Object visitBinaryExpression(BinaryExpression be, Object data) {
    data = be.getLeftExpression().accept(this, data);
    data = be.getRightExpression().accept(this, data);
    return data;
  }
  
  public Object visitBooleanLiteral(BooleanLiteral bl, Object data) {
    return data;
  }

  public Object visitCharacterLiteral(CharacterLiteral cl, Object data) {
    return data;
  }

  public Object visitDComplexLiteral(DComplexLiteral dcl, Object data) {
    return data;
  }

  public Object visitDoubleLiteral(DoubleLiteral dl, Object data) {
    return data;
  }
  
  public Object visitFComplexLiteral(FComplexLiteral fcl, Object data) {
    return data;
  }

  public Object visitFloatLiteral(FloatLiteral fl, Object data) {
    return data;
  }

  public Object visitIdentifierLiteral(IdentifierLiteral il, Object data) {
    return data;
  }

  public Object visitIntegerLiteral(IntegerLiteral il, Object data) {
    return data;
  }

  public Object visitLongLiteral(LongLiteral ll, Object data) {
    return data;
  }

  public Object visitMethodCall(MethodCall mc, Object data) {
    for (Iterator i = mc.getArguments().iterator(); i.hasNext(); ) {
      AssertionExpression ae = (AssertionExpression)i.next();
      ae.accept(this, data);
    }
    return data;
  }

  public Object visitStringLiteral(StringLiteral sl, Object data) {
    return data;
  }

  public Object visitUnaryExpression(UnaryExpression ue, Object data) {
    data = ue.getExpression().accept(this, data);
    return data;
  }
}
