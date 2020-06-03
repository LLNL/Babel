//
// File:        UnaryExpression.java
// Package:     gov.llnl.babel.symbols
// Revision:    @(#) $Id $
// Description: The unary expression interface for assertions.
//
// Copyright (c) 2003-2007, Lawrence Livermore National Security, LLC
// Produced at the Lawrence Livermore National Laboratory.
// Written by the Components Team <components@llnl.gov>
// UCRL-CODE-2002-054
// All rights reserved.
// 
// This file is part of Babel. For more information, see
// http://www.llnl.gov/CASC/components/. Please read the COPYRIGHT file
// for Our Notice and the LICENSE file for the GNU Lesser General Public
// License.
// 
// This program is free software; you can redistribute it and/or modify it
// under the terms of the GNU Lesser General Public License (as published by
// the Free Software Foundation) version 2.1 dated February 1999.
// 
// This program is distributed in the hope that it will be useful, but
// WITHOUT ANY WARRANTY; without even the IMPLIED WARRANTY OF
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the terms and
// conditions of the GNU Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public License
// along with this program; if not, write to the Free Software Foundation,
// Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

package gov.llnl.babel.symbols;

import gov.llnl.babel.Context;
import gov.llnl.babel.symbols.AssertionException;
import gov.llnl.babel.symbols.AssertionExpression;
import gov.llnl.babel.symbols.ExprVisitor;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Method;
import java.util.ArrayList;


public class UnaryExpression extends AssertionExpression {
  private int                 d_op         = NOOP;
  private AssertionExpression d_expression = null;

  /*
   * Valid unary operators.
   */
  public final static int NOOP          = 0;
  public final static int COMPLEMENT    = 1;
  public final static int IS            = 2;
  public final static int MINUS         = 3;
  public final static int NOT           = 4;
  public final static int PLUS          = 5;

  private final static int MIN_OP_VALUE = 1;  // Minimum valid value 
  private final static int MAX_OP_VALUE = 5;  // Maximum valid value

  private final static String s_symbol[] = {
    "", "~", "is", "-", "not", "+"
  };

  /**
   * Construct a new object.  
   *
   * @param   op      The operator associated with this unary expression.
   * @param   expr    The expression being paired with the operator.
   * @throws  gov.llnl.babel.symbols.AssertionException  
   *                  The exception raised if the operator does not fall within 
   *                  the range of supported operators OR if the expression
   *                  is null.
   */
  public UnaryExpression(int op, AssertionExpression expr, Context context)
       throws AssertionException
  {
    super(false, context);
    if ( (op <= MIN_OP_VALUE) || (op > MAX_OP_VALUE) ) {
      throw new AssertionException("UnaryExpression", "Operator value \"" + op 
                + "\" invalid, must be in " + MIN_OP_VALUE + "..." 
                + MAX_OP_VALUE + ".");
    } else if (expr == null) {
      throw new AssertionException("UnaryExpression", "Cannot instantiate if "
                + "the expr is null.");
    } else {
      d_op         = op;
      d_expression = expr;
    }
  }


  /**
   * Return the unary operator.
   */
  public int getOp() {
    return d_op;
  }


  /**
   * Return the symbol associated with the operator.  It is assumed the
   * operator is valid thanks to the check in the constructor.
   */
  public String getOpSymbol() {
    return s_symbol[d_op];
  }


  /**
   * Return the expression.
   */
  public AssertionExpression getExpression() {
    return d_expression;
  }


  /**
   * Return TRUE if the expression is the pure identifier literal; otherwise, 
   * return FALSE.
   */
  private boolean isPure() {
    String classname = d_expression.getClass().getName();
    String litname   = "gov.llnl.babel.symbols.IdentifierLiteral";
    return classname.equals(litname) && d_expression.hasPure();
  }


  /**
   * Return TRUE if the expression is PURE clause; otherwise, return FALSE.  
   */
  public boolean hasPure() {
    return (d_op == IS) && isPure();
  }


  /**
   * Return TRUE if the expression contains RESULT; otherwise, return FALSE.
   */
  public boolean hasResult() {
    return d_expression.hasResult();
  }


                                                                                
  /**
   * Return TRUE if a result clause or method argument is found within the
   * expression; otherwise, return FALSE.
   */
  public boolean hasResultOrArg() {
    return d_expression.hasResultOrArg();
  }


  /**
   * Return TRUE if a result clause or output argument is found within the
   * expression; otherwise, return FALSE.
   *
   * @param  outOnly  TRUE if only concerned with output arguments that are
   *                  out only; FALSE otherwise.
   */
  public boolean hasResultOrOutArg(boolean outOnly) {
    return d_expression.hasResultOrOutArg(outOnly);
  }


  /**
   * Return TRUE if the expression has at least one method call; otherwise, 
   * return FALSE.  Clearly this expression isn't a method call BUT the
   * expression may be or contain one.
   */
  public boolean hasMethodCall() {
    return d_expression.hasMethodCall();
  }

  /**
   * Return TRUE if the expression is, or has, a call to the specified
   * method; otherwise, return FALSE.  
   */
  public boolean hasMethodCall(String name) {
    return d_expression.hasMethodCall(name);
  }



 /**
   * Return the default complexity of the expression (0 = constant, 1 = linear,
   * etc.).
   */
  public int getDefaultComplexity() {
    return d_expression.getDefaultComplexity();
  }


  /**
   * Return TRUE if the expression is, or has, the specified built-in method
   * call; otherwise, return FALSE.
   */
  public boolean hasBuiltinMethod(int type) {
    return d_expression.hasBuiltinMethod(type);
  }


  /**
   * Return TRUE if the expression has a method AND the method is any
   * user-defined method (when any is TRUE) or it is an user-defined
   * method with a throws clause (if any is FALSE); otherwise, return FALSE.
   */
  public boolean hasUserDefinedMethod(boolean any) {
    return d_expression.hasUserDefinedMethod(any);
  }


  /**
   * Return TRUE if extendable context is required to validate the expression;
   * otherwise, return FALSE.
   */
  public boolean requiresExtendableContext() {
    return d_expression.requiresExtendableContext();
  }


  /**
   * Return TRUE if method context is required to validate the expression;
   * otherwise, return FALSE.
   */
  public boolean requiresMethodContext() {
    return d_expression.requiresMethodContext();
  }


  /**
   * Return the message for incompatible return type and operation.
   *
   * @param  type  The description of the type whose value was to be used.
   */
  private String getIncompatibleMessage(String desc) {
    return "The expression must return " + desc + " value to apply the unary "
         + getOpSymbol() + " operation.";
  }


  /**
   * Set the return type to the appropriate numeric type based on that of
   * the subexpression.
   *
   * @throws  gov.llnl.babel.symbols.AssertionException  
   *               The exception that can be raised during validation.
   */
  private void setNumericType() throws AssertionException {
    if ( d_expression.returnIsInteger() ) {
      setReturnToInteger();
    } else if ( d_expression.returnIsLong() ) {
      setReturnToLong();
    } else if ( d_expression.returnIsFloat() ) {
      setReturnToFloat();
    } else if ( d_expression.returnIsDouble() ) {
      setReturnToDouble();
    } else if ( d_expression.returnIsFComplex() ) {
      setReturnToFComplex();
    } else if ( d_expression.returnIsDComplex() ) {
      setReturnToDComplex();
    } else {
      throw new AssertionException(getIncompatibleMessage("a numeric"));
    }
  }


  /**
   * Validate the expression semantics, if necessary, within the context of the
   * extendable and optional method.  
   *
   * @param   ext  The interface or class that owns this expression.
   * @param   m    The method that owns this expression.
   * @throws  gov.llnl.babel.symbols.AssertionException  
   *               The exception that can be raised during validation.
   */
  protected void validateSemantics(Extendable ext, Method m)
          throws AssertionException
  {
    /*
     * First ensure the subexpression is valid.
     */
    d_expression.validateExpression(ext, m);

    /*
     * Now make sure this unary expression is valid.
     */
    switch (d_op) {
      case COMPLEMENT:
        if (d_expression.returnIsInteger() || d_expression.returnIsLong()) {
          setReturnType(d_expression.getReturnTypeValue());
        } else {
          throw new AssertionException(getExceptionPrefix(ext, m),
                             getIncompatibleMessage("an integer or long"));
        }
        break;
      case IS:
        if (isPure()) {
          setReturnToBoolean();
        } else {
          throw new AssertionException(getExceptionPrefix(ext, m), 
                             getIncompatibleMessage("a \"pure\" identifier"));
        }
        break;
      case MINUS:
        setNumericType();
        break;
      case NOT:
//
// ToDo...o Was this also supposed to support complement if numeric?
//        o Should this disallow "is pure" subexpressions?
//
        if (d_expression.returnIsBoolean()) {
          setReturnToBoolean();
        } else {
          throw new AssertionException(getExceptionPrefix(ext, m),
                             getIncompatibleMessage("a boolean"));
        }
        break;
      case PLUS:
        setNumericType();
        break;
      default:
        throw new AssertionException(getExceptionPrefix(ext, m), "Invalid or "
                  + "unrecognized unary operator value \"" + d_op + "\" with "
                  + "subexpression return type \""
                  + d_expression.getReturnTypeName() + "\".");
    } 
  }


  /**
   * Return the list of array iteration macro messages, if any.  Each message
   * is a string where the first character indicates the return type associated
   * with the iteration.  The remaining characters will be the actual macro
   * invocation.
   */
  public ArrayList getArrayIterMacros(String epvVar, int[] startInd) {
    return d_expression.getArrayIterMacros(epvVar, startInd);
  }


  /**
   * Returns the number of macros supported by this assertion of the
   * specified type.  Valid types are given in MethodCall.java.
   */
  public int getNumArrayIterMacrosByType(char type) {
    return d_expression.getNumArrayIterMacrosByType(type);
  }

  /**
   * Return the C version of the expression.
   */
  public String cExpression(String epvVar, int[] startInd) {
    String expr = null;
    switch (d_op) {
      case COMPLEMENT:
        expr = getOpSymbol() + d_expression.cExpression(epvVar, startInd);
        break;
      case IS:
        expr = "";
        break;
      case MINUS:
        expr = getOpSymbol() + d_expression.cExpression(epvVar, startInd);
        break;
      case NOT:
        expr = "!" + d_expression.cExpression(epvVar, startInd);
        break;
      case PLUS:
        expr = getOpSymbol() + d_expression.cExpression(epvVar, startInd);
        break;
    } 
    return expr;
  }


  /**
   * Return the stringified version of the expression (in SIDL form).
   */
  public String toString() {
    String expr = getOpSymbol() + " " + d_expression;
    return hasParens() ?   "(" + expr + ")"  :  expr;
  }

  public Object accept(ExprVisitor ev, Object data) {
    return ev.visitUnaryExpression(this, data);
  }
}
