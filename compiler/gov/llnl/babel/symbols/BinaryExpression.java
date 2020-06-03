//
// File:        BinaryExpression.java
// Package:     gov.llnl.babel.symbols
// Revision:    @(#) $Id $
// Description: The binary expression interface for assertions.
//
// Copyright (c) 2003-2004, Lawrence Livermore National Security, LLC
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
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Method;
import java.util.ArrayList;


public class BinaryExpression extends AssertionExpression {
  private AssertionExpression d_lhs            = null;
  private int                 d_op             = NOOP;
  private AssertionExpression d_rhs            = null;
  private boolean             d_array_relation = false;
  private String              d_array_name_lhs = null;
  private String              d_array_name_rhs = null;

  /*
   * Valid binary operators.
   */
  public final static int NOOP           = 0;
  public final static int LOGICAL_AND    = 1;
  public final static int DIVIDE         = 2;
  public final static int EQUALS         = 3;
  public final static int GREATER_EQUAL  = 4;
  public final static int GREATER_THAN   = 5;
  public final static int IF_AND_ONLY_IF = 6;
  public final static int IMPLIES        = 7;
  public final static int LESS_EQUAL     = 8;
  public final static int LESS_THAN      = 9;
  public final static int MINUS          = 10;
  public final static int MODULUS        = 11;
  public final static int MULTIPLY       = 12;
  public final static int NOT_EQUAL      = 13;
  public final static int LOGICAL_OR     = 14;
  public final static int PLUS           = 15;
  public final static int POWER          = 16;
  public final static int REMAINDER      = 17;
  public final static int SHIFT_LEFT     = 18;
  public final static int SHIFT_RIGHT    = 19;
  public final static int LOGICAL_XOR    = 20;
  public final static int BITWISE_AND    = 21;
  public final static int BITWISE_OR     = 22;
  public final static int BITWISE_XOR    = 23;

  public final static int MIN_OP_VALUE  = 1;   // Minimum valid value 
  public final static int MAX_OP_VALUE  = 23;  // Maximum valid value

  private final static String s_symbol[] = {
    "", "and", "/", "==", ">=", ">", "iff", "implies", "<=", "<", 
    "-", "mod", "*", "!=", "or", "+", "**", "rem", "<<", ">>", "xor",
    "&", "|", "^"
  };

  /*
   * These are supposed to be the equivalent symbols in C.  When there isn't
   * a "general" symbol (or it could have multiple meanings), the symbol
   * should be null.
   */
  private final static String s_Csymbol[] = {
    "", "&&", "/", "==", ">=", ">", "", "", "<=", "<", 
    "-", "%", "*", "!=", "||", "+", "", "", "<<", ">>", "",
    "&", "|", "^"
  };

  /**
   * WARNING: The following must correspond to the appropriate
   * complete class name in the symbols directory.
   */
  private static final String IDENTIFIER_LITERAL =
             "gov.llnl.babel.symbols.IdentifierLiteral";


  /**
   * Construct a new object.  An exception is thrown if an invalid
   * binary operator is specified.
   *
   * @param   lhs     The expression on the left side of the operator.
   * @param   op      The binary operator.
   * @param   rhs     The expression on the right side of the operator.
   * @throws  gov.llnl.babel.symbols.AssertionException
   *                  The exception raised if the specified return type is
   *                  out of range, the operator invalid, or either the left
   *                  or right expression is null.
   */
  public BinaryExpression(AssertionExpression lhs, int op, 
                          AssertionExpression rhs,
                          Context context)
       throws AssertionException
  {
    super(false, context);
    if ( (op < MIN_OP_VALUE) || (op > MAX_OP_VALUE) ) {
      throw new AssertionException("Cannot instantiate BinaryExpression since "
                  + "binary operator value \"" + op + "\" invalid, must be in " 
                  + MIN_OP_VALUE + "..." + MAX_OP_VALUE + ".");
    } else if ( (lhs == null) || (rhs == null) ) {
      throw new AssertionException("Cannot instantiate BinaryExpression when "
                  + "lhs \"" + lhs.toString() + "\" and/or rhs \""
                  + rhs.toString() + "\" is null.");
    } else {
      d_lhs = lhs;
      d_op  = op;
      d_rhs = rhs;
    }
  }


  /**
   * Return the left-hand expression.
   */
  public AssertionExpression getLeftExpression() {
    return d_lhs;
  }


  /**
   * Return the binary operator.
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
   * Return the C equivalent, if any, of the symbol associated with the 
   * operator.  It is assumed the operator is valid thanks to the check 
   * in the constructor.
   */
  private String getOpCSymbol() {
    return s_Csymbol[d_op];
  }


  /**
   * Return the right-hand expression.
   */
  public AssertionExpression getRightExpression() {
    return d_rhs;
  }


  /**
   * Set the array relation required attribute.
   */
  public void setArrayRelationRequired(boolean isReq) {
    d_array_relation = isReq;
  }


  /**
   * Return TRUE if the array relation is required (during validation);
   * otherwise, return FALSE.
   */
  public boolean isArrayRelationRequired() {
    return d_array_relation;
  }


  /**
   * Return the specified array relation's array variable.
   */
  public String getArrayRelationVariable(boolean onLeft) {
    return (onLeft) ? d_array_name_lhs : d_array_name_rhs;
  }


  /**
   * Return the array relation only.  For relations with a single array,
   * this is the operator and value being used for comparison.  When both
   * arrays are specified, this is only the operator.
   */
  public String getArrayRelation() {
    String rel = null;
    if (arrayOnLeft() && arrayOnRight()) {
       rel = s_symbol[d_op];
    } else if (arrayOnLeft()) {
       rel = s_symbol[d_op] + d_rhs.cExpression(null, null);
    } else {
       rel = d_lhs.cExpression(null, null) + s_symbol[d_op];
    }
    return rel;
  }


  /**
   * Return TRUE if an array appears on the LHS; otherwise, FALSE.
   */
  public boolean arrayOnLeft() {
    return (d_array_name_lhs != null) ? true : false;
  }


  /**
   * Return TRUE if an array appears on the RHS; otherwise, FALSE.
   */
  public boolean arrayOnRight() {
    return (d_array_name_rhs != null) ? true : false;
  }


  /**
   * Return TRUE if the expression has an "is pure" clause; otherwise, return
   * FALSE.  
   */
  public boolean hasPure() {
    return d_lhs.hasPure() || d_rhs.hasPure();
  }


  /**
   * Return TRUE if the expression uses the "result" keyword; otherwise,
   * return FALSE.
   */
  public boolean hasResult() {
    return d_lhs.hasResult() || d_rhs.hasResult();
  }


  /**
   * Return TRUE if a result clause or method argument is found within the
   * expression; otherwise, return FALSE.
   */
  public boolean hasResultOrArg() {
    return d_lhs.hasResultOrArg() || d_rhs.hasResultOrArg();
  }


  /**
   * Return TRUE if a result clause or output argument is found within the
   * expression; otherwise, return FALSE.
   *
   * @param  outOnly  TRUE if only concerned with output arguments that are
   *                  out only; FALSE otherwise.
   */
  public boolean hasResultOrOutArg(boolean outOnly) {
    return d_lhs.hasResultOrOutArg(outOnly) || d_rhs.hasResultOrOutArg(outOnly);
  }


  /**
   * Return TRUE if the expression has at least one method call; otherwise, 
   * return FALSE.
   */
  public boolean hasMethodCall() {
    return d_lhs.hasMethodCall() || d_rhs.hasMethodCall();
  }

  /**
   * Return TRUE if the expression is, or has, a call to the specified
   * method; otherwise, return FALSE.  
   */
  public boolean hasMethodCall(String name) {
    return d_lhs.hasMethodCall(name) || d_rhs.hasMethodCall(name);
  }



  /**
   * Return the default complexity of the expression (0 = constant, 1 = linear,
   * etc.).
   */
  public int getDefaultComplexity() {
    return (d_lhs.getDefaultComplexity() >= d_rhs.getDefaultComplexity())
         ?  d_lhs.getDefaultComplexity() :  d_rhs.getDefaultComplexity();
  }


  /**
   * Return TRUE if the expression is, or has, the specified built-in method
   * call; otherwise, return FALSE.
   */
  public boolean hasBuiltinMethod(int type) {
    return d_lhs.hasBuiltinMethod(type) || d_rhs.hasBuiltinMethod(type);
  }


  /**
   * Return TRUE if the expression has a method AND the method is any
   * user-defined method (when any is TRUE) or it is an user-defined
   * method with a throws clause (if any is FALSE); otherwise, return FALSE.
   */
  public boolean hasUserDefinedMethod(boolean any) {
    return d_lhs.hasUserDefinedMethod(any) || d_rhs.hasUserDefinedMethod(any);
  }


  /**
   * Return TRUE if extendable context is required to validate the expression;
   * otherwise, return FALSE.
   */
  public boolean requiresExtendableContext() {
    return d_lhs.requiresExtendableContext() 
        || d_rhs.requiresExtendableContext();
  }


  /**
   * Return TRUE if method context is required to validate the expression;
   * otherwise, return FALSE.
   */
  public boolean requiresMethodContext() {
    return d_lhs.requiresMethodContext() || d_rhs.requiresMethodContext();
  }


  /**
   * Return TRUE if the specified expression is a NULL identifier; otherwise,
   * return FALSE.
   *
   * @param  expr  The assertion expression being checked.
   */
  private boolean isNullIdentifier(AssertionExpression expr)
  {
    boolean           isNull  = false;
    IdentifierLiteral id      = null;
    String            litname = "gov.llnl.babel.symbols.IdentifierLiteral";
    if (expr.getClass().getName().equals(litname)) {
      id = (IdentifierLiteral)expr;
    }
    if ( (id != null) && id.isNull() ) {
      isNull = true;
    }
    return isNull;
  }


  /**
   * Validate the subexpressions, including updating return types
   * if either is an identifier.
   *
   * Assumptions:  
   * 1) Only identifier subexpressions need extra "help" in determining 
   *    their return types because, as in the cases of NULL, RESULT, and 
   *    ARGUMENT, they are dependent upon context information.  (The PURE 
   *    clause is obvious.) 
   * 2) Since RESULT and ARGUMENT can be gleaned from the method context
   *    provided in this call, it is assumed that invoking the validation
   *    of each subexpression (of either type) will result in the proper
   *    setting of its return type.
   * 3) Since the context associated with NULL depends upon the other
   *    subexpression in this binary expression, it is assumed that this
   *    is the only logical/reasonable place to determine the return types
   *    for NULL identifier subexpressions.
   *
   * @param   ext  The interface or class that owns this expression.
   * @param   m    The method that owns this expression.
   * @throws  gov.llnl.babel.symbols.AssertionException
   *               The exception raised if there are problems validating
   *               either individual subexpressions or, if either is
   *               an identifier, the combination.
   */
  private void validateSubexpressions(Extendable ext, Method m)
     throws AssertionException
  {
    /*
     * The following combinations of subexpressions/return values are
     * possible:
     *
     *   NULL, NULL           Okay. Null does equal Null.
     *   NULL, PURE           Bad. Subexpression cannot be a PURE identifier.
     *   PURE, NULL           Bad. Subexpression cannot be a PURE identifier.
     *   NULL, ARGUMENT       Okay. Other may be something reasonable.
     *   ARGUMENT, NULL       Okay. Other may be something reasonable.
     *   NULL, RESULT         Okay. Result may have reasonable return type.
     *   RESULT, NULL         Okay. Result may have reasonable return type.
     *
     *   PURE, PURE           Bad. Subexpression cannot be a PURE identifier.
     *   PURE, ARGUMENT       Bad. Subexpression cannot be a PURE identifier.
     *   ARGUMENT, PURE       Bad. Subexpression cannot be a PURE identifier.
     *   PURE, RESULT         Bad. Subexpression cannot be a PURE identifier.
     *   RESULT, PURE         Bad. Subexpression cannot be a PURE identifier.
     *
     *   ARGUMENT, ARGUMENT   Okay. Return types may be compatible.
     *   ARGUMENT, RESULT     Okay. Return types may be compatible.
     *   RESULT, ARGUMENT     Okay. Return types may be compatible.
     *
     *   RESULT, RESULT       Okay. Return types may be compatible.
     */

    /*
     * Clearly the pure clause does NOT belong in the 
     * subexpression of a binary expression.
     */
    if ( d_lhs.hasPure() || d_rhs.hasPure() ) {
      throw new AssertionException("Binary expression cannot contain a "
                  + "PURE clause.");
    }

    /*
     * Now make sure that if either subexpression is NULL then its return
     * type could be string or opaque/object depending upon the return
     * type of the other subexpression.  Since this information can only 
     * be known at this time, make sure they are consistent.
     */
    boolean setleft  = isNullIdentifier(d_lhs);
    boolean setright = isNullIdentifier(d_rhs);
    if ( setleft && setright) {
      /*
       * They're both NULL so might as well set their types to opaque
       * and be done with it. 
       *
       * Note: Technically the validation routines are not necessary so
       * they could be removed/commented out if performance is an issue.
       * However, they could be helpful in locating bugs that are 
       * inadvertently added during feature maintenance.
       */
      d_lhs.setReturnToOpaque();
      d_lhs.validateExpression(ext, m);
      d_rhs.setReturnToOpaque();
      d_rhs.validateExpression(ext, m);
    } else if (setleft) {
      /*
       * Only the lhs is NULL so give the rhs a chance to ensure its
       * return type is set properly before trying to set the lhs 
       * to the same type.
       */
      d_rhs.validateExpression(ext, m);
      d_lhs.setReturnType(d_rhs.getReturnType());
      d_lhs.validateExpression(ext, m);
    } else if (setright) {
      /*
       * Only the rhs is NULL so give the lhs a chance to ensure its
       * return type is set properly before trying to set the rhs 
       * to the same type.
       */
      d_lhs.validateExpression(ext, m);
      d_rhs.setReturnType(d_lhs.getReturnType());
      d_rhs.validateExpression(ext, m);
    } else {
      /*
       * Okay, both types are not NULL so, since all other (current)
       * identifiers either know their return types or can determine 
       * them from the method context, simply proceed with validating
       * the two subexpressions.
       */
      d_lhs.validateExpression(ext, m);
      d_rhs.validateExpression(ext, m);
    }
  }
  
  /**
   * Return TRUE if the return value is or should be boolean; otherwise, 
   * return FALSE.
   */
  private boolean returnBoolean(int lType, int rType) {
    boolean isBool = false;
    if (returnIsBoolean()) {
      isBool = true;
    } else if ( (lType == Type.BOOLEAN) && (rType == Type.BOOLEAN) ) {
      isBool = true;
    }
    return isBool;
  }


  /**
   * Return TRUE if the return value is or should be integer; otherwise, 
   * return FALSE.
   */
  private boolean returnInteger(int lType, int rType) {
    boolean isInt = false;
    if (returnIsInteger()) {
      isInt = true;
    } else if ( (lType == Type.INT) && (rType == Type.INT) ) {
      isInt = true;
    }
    return isInt;
  }


  /**
   * Return TRUE if the return value is or should be long; otherwise, 
   * return FALSE.
   */
  private boolean returnLong(int lType, int rType) {
    boolean isLong = false;
    if (returnIsLong()) {
      isLong = true;
    } else if (  ( (lType == Type.INT ) && (rType == Type.INT ) )
              || ( (lType == Type.INT)  && (rType == Type.LONG ) )
              || ( (lType == Type.LONG) && (rType == Type.INT ) )
              || ( (lType == Type.LONG) && (rType == Type.LONG) ) ) {
      isLong = true;
    }
    return isLong;
  }


  /**
   * Return TRUE if the return value is or should be float; otherwise, 
   * return FALSE.
   */
  private boolean returnFloat(int lType, int rType) {
    boolean isFloat = false;
    if (returnIsFloat()) {
      isFloat = true;
    } else if (  ( (lType == Type.INT  ) && (rType == Type.FLOAT) )
              || ( (lType == Type.FLOAT) && (rType == Type.INT  ) )
              || ( (lType == Type.LONG ) && (rType == Type.FLOAT) )
              || ( (lType == Type.FLOAT) && (rType == Type.LONG ) )
              || ( (lType == Type.FLOAT) && (rType == Type.FLOAT) ) ) {
      isFloat = true;
    }
   return isFloat;
  }


  /**
   * Return TRUE if the return value is or should be double; otherwise, 
   * return FALSE.
   */
  private boolean returnDouble(int lType, int rType) {
    boolean isDouble = false;
    if (returnIsDouble()) {
      isDouble = true;
    } else if (  ( (lType == Type.INT   ) && (rType == Type.DOUBLE) )
              || ( (lType == Type.DOUBLE) && (rType == Type.INT   ) )
              || ( (lType == Type.LONG  ) && (rType == Type.DOUBLE) )
              || ( (lType == Type.DOUBLE) && (rType == Type.LONG  ) )
              || ( (lType == Type.FLOAT ) && (rType == Type.DOUBLE) )
              || ( (lType == Type.DOUBLE) && (rType == Type.FLOAT ) )
              || ( (lType == Type.DOUBLE) && (rType == Type.DOUBLE) ) ) {
      isDouble = true;
    }
    return isDouble;
  }


  /**
   * Return TRUE if the return value is or should be float complex; otherwise, 
   * return FALSE.
   */
  private boolean returnFComplex(int lType, int rType) {
    boolean isFComplex = false;
    if (returnIsFComplex()) {
      isFComplex = true;
    } else if ( (lType == Type.FCOMPLEX) && (rType == Type.FCOMPLEX) ) {
      isFComplex = true;
    }
   return isFComplex;
  }


  /**
   * Return TRUE if the return value is or should be double complex; otherwise, 
   * return FALSE.
   */
  private boolean returnDComplex(int lType, int rType) {
    boolean isDComplex = false;
    if (returnIsDComplex()) {
      isDComplex = true;
    } else if (  ( (lType == Type.FCOMPLEX) && (rType == Type.DCOMPLEX) )
              || ( (lType == Type.DCOMPLEX) && (rType == Type.FCOMPLEX) )
              || ( (lType == Type.DCOMPLEX) && (rType == Type.DCOMPLEX) ) ) {
      isDComplex = true;
    }
    return isDComplex;
  }


  /**
   * Return TRUE if the return value is or should be string; otherwise,
   * return FALSE.
   */
  private boolean returnString(int lType, int rType) {
    boolean isString = false;
    if (returnIsString()) {
      isString = true;
    } else if (  ( (lType == Type.CHAR   ) && (rType == Type.CHAR)
                                             && (d_op == PLUS)            )
              || ( (lType == Type.CHAR   ) && (rType == Type.STRING   ) )
              || ( (lType == Type.STRING ) && (rType == Type.CHAR) )
              || ( (lType == Type.STRING ) && (rType == Type.STRING   ) ) ) {
      isString = true;
    }
    return isString;
  }


  /**
   * Return TRUE if the return value is or should be opaque; otherwise,
   * return FALSE.
   */
  private boolean returnOpaque(int lType, int rType) {
    boolean isOpaque = false;
    if (returnIsOpaque()) {
      isOpaque = true;
    } else if ( (lType == Type.OPAQUE) && (rType == Type.OPAQUE) ) {
      isOpaque = true;
    }
    return isOpaque;
  }


  /**
   * Return TRUE if the return value is or should be enumeration; otherwise,
   * return FALSE.
   */
  private boolean returnEnumeration(int lType, int rType) {
    boolean isEnum = false;
    if (returnIsEnum()) {
      isEnum = true;
    } else if ( (lType == Type.ENUM) && (rType == Type.ENUM) ) {
      isEnum = true;
    }
    return isEnum;
  }


  /**
   * Return TRUE if the return value is or should be class; otherwise,
   * return FALSE.
   */
  private boolean returnClass(int lType, int rType) {
    boolean isClass = false;
    if (returnIsClass()) {
      isClass = true;
    } else if ( (lType == Type.CLASS) && (rType == Type.CLASS) ) {
      isClass = true;
    }
    return isClass;
  }


  /**
   * Return TRUE if the return value is or should be interface; otherwise,
   * return FALSE.
   */
  private boolean returnInterface(int lType, int rType) {
    boolean isIfc = false;
    if (returnIsInterface()) {
      isIfc = true;
    } else if ( (lType == Type.INTERFACE) && (rType == Type.INTERFACE) ) {
      isIfc = true;
    }
    return isIfc;
  }


  /**
   * Return a string containing the standard return type error message.
   *
   * @param  required  Description of the return type(s) that are required
   */
  private String getReturnExceptionMessage(String required) {
    return "Unable to set return type for expression \"" + toString() 
           + "\" since both subexpressions must return " + required 
           + " values to apply the \"" + getOpSymbol() 
           + "\" operator.  The left expression returns \"" 
           + d_lhs.getReturnTypeName() + "\" while the right returns \"" 
           + d_rhs.getReturnTypeName() + "\".";
  }


  /**
   * Return a string containing the standard string comparison error message.
   */
  private String getStringCompareExceptionMessage() {
    return "Unable to set return type for expression \"" + toString() 
           + "\" since string comparison operations are not currently "
           + "supported.  The left expression returns \"" 
           + d_lhs.getReturnTypeName() + "\" while the right returns \"" 
           + d_rhs.getReturnTypeName() + "\".";
  }


  /**
   * Set the boolean relation array name given what should be the 
   * IdentifierLiteral expression associated with the array variable.
   *
   * Assumption(s):
   * 1) The return type of the expression has already been checked to
   *    ensure it's an array.
   * 
   * @throws  gov.llnl.babel.symbols.AssertionException
   *                   The exception raised if the expression is not a
   *                   simple array variable.
   */
  private void setArrayName(AssertionExpression ae, boolean onLeft) 
     throws AssertionException 
  {
    String className = ae.getClass().getName();
    if (className.equals(IDENTIFIER_LITERAL)) {
      IdentifierLiteral lit = (IdentifierLiteral)ae;
      if (lit.isArgument()) {
        if (onLeft) {
          d_array_name_lhs = lit.getIdentifier();
        } else {
          d_array_name_rhs = lit.getIdentifier();
        }
      } else {
        throw new AssertionException(ERROR_SEMANTIC_VALIDATION + "Expecting "
                  + "to find an array variable name in \"" + toString() 
                  + "\" but detected an identifier/literal expression of type " 
                  + lit.getIdentifierTypeName() + " instead.");
      }
    } else {
      throw new AssertionException(ERROR_SEMANTIC_VALIDATION + "Expecting "
                + "to find an array variable name in \"" + toString() 
                + "\" but detected an expression class of type " + className
                + " instead.");
    }
  }


  /**
   * Set the boolean relation data based on the subexpressions, including
   * return types.  
   *
   * Assumption(s):
   * 1)  This expression is a simple boolean relation with one array and 
   *     one (numeric) value.  In other words, the caller checked that
   *     d_array_relation is TRUE prior to invoking this method.
   *
   * @throws  gov.llnl.babel.symbols.AssertionException
   *                   The exception raised if not the same or compatible types.
   */
  private void setBooleanRelationData() throws AssertionException {
    int lType = d_lhs.getReturnTypeValue();
    int rType = d_rhs.getReturnTypeValue();
    if (lType == Type.ARRAY) {
      setArrayName(d_lhs, true);
    } 
    if (rType == Type.ARRAY) {
      setArrayName(d_rhs, false);
    }
    if ( (lType != Type.ARRAY) && (rType != Type.ARRAY) ) {
      throw new AssertionException(ERROR_SEMANTIC_VALIDATION + "Expecting "
                + "a simple boolean expression with an array variable "
                + "on one or both sides but no array detected in \""  
                + toString() + "\".  The left side returns type \""
                + d_lhs.getReturnTypeName() + "\" while the right returns \"" 
                + d_rhs.getReturnTypeName() + "\".");
    }
    setReturnToBoolean();
  }


  /**
   * Set the return type to the appropriate numeric type based on those of
   * the subexpressions.
   *
   * @throws  gov.llnl.babel.symbols.AssertionException
   *            The exception raised if subexpressions are not compatible 
   *            numeric types.
   */
  private void setNumericType() throws AssertionException {
    int lType = d_lhs.getReturnTypeValue();
    int rType = d_rhs.getReturnTypeValue();
    if (returnInteger(lType, rType)) {
      setReturnToInteger();
    } else if (returnLong(lType, rType)) {
      setReturnToLong();
    } else if (returnFloat(lType, rType)) {
      setReturnToFloat();
    } else if (returnDouble(lType, rType)) {
      setReturnToDouble();
    } else if (returnFComplex(lType, rType)) {
      setReturnToFComplex();
    } else if (returnDComplex(lType, rType)) {
      setReturnToDComplex();
    } else {
      throw new AssertionException("Unable to set return to a numeric type. "
                + "Numeric binary expression \"" + toString() 
                + "\" does not appear to contain expressions with compatible "
                + "return types.  Left returns " + d_lhs.getReturnTypeName() 
                + " while right returns " + d_rhs.getReturnTypeName() + ".");
    } 
  }


  /**
   * Set the return type to boolean provided the left and right expressions
   * return compatible numeric types.
   *
   * @throws  gov.llnl.babel.symbols.AssertionException
   *            The exception raised if subexpressions are not compatible 
   *            numeric types.
   */
  private void setNumericTypeToBoolean() throws AssertionException {
    if (!d_array_relation) {
      int lType = d_lhs.getReturnTypeValue();
      int rType = d_rhs.getReturnTypeValue();
      if (returnInteger(lType, rType)) {
        setReturnToBoolean();
      } else if (returnLong(lType, rType)) {
        setReturnToBoolean();
      } else if (returnFloat(lType, rType)) {
        setReturnToBoolean();
      } else if (returnDouble(lType, rType)) {
        setReturnToBoolean();
      } else if (returnFComplex(lType, rType)) {
        setReturnToBoolean();
      } else if (returnDComplex(lType, rType)) {
        setReturnToBoolean();
      } else {
        throw new AssertionException("Cannot set return of numeric to boolean. "
                  + "Numeric binary expression \"" + toString() 
                  + "\" does not appear to contain expressions with compatible "
                  + "return types.  Left returns " + d_lhs.getReturnTypeName() 
                  + " while right returns " + d_rhs.getReturnTypeName() + ".");
      } 
    } else {
      setBooleanRelationData();
    } 
  }


 /**
   * Set the return type to either boolean or integer based on the
   * return types of the subexpressions.
   *
   * @throws  gov.llnl.babel.symbols.AssertionException
   *                   The exception raised if type not suitable.
   */
  private void setBooleanOrIntegerType() throws AssertionException {
    int lType = d_lhs.getReturnTypeValue();
    int rType = d_rhs.getReturnTypeValue();
    if (returnBoolean(lType, rType)) {
      setReturnToBoolean();
    } else if (returnInteger(lType, rType)) {
      setReturnToInteger();
    } else if (returnLong(lType, rType)) {
      setReturnToLong();
    } else {
      throw new AssertionException(
                 getReturnExceptionMessage("either boolean, integer, or long"));
    }
  }


  /**
   * Set the return type to either integer or long based on the return types
   * of the subexpressions.
   *
   * @throws  gov.llnl.babel.symbols.AssertionException
   *                   The exception raised if not integer types.
   */
  private void setIntegerType() throws AssertionException {
    int lType = d_lhs.getReturnTypeValue();
    int rType = d_rhs.getReturnTypeValue();
    if (returnInteger(lType, rType)) {
      setReturnToInteger();
    } else if (returnLong(lType, rType)) {
      setReturnToLong();
    } else {
      throw new AssertionException(
                  getReturnExceptionMessage("integer or long"));
    } 
  }


  /**
   * Set the return type to boolean provided both subexpressions have the same
   * or compatible types.
   *
   * @throws  gov.llnl.babel.symbols.AssertionException
   *                   The exception raised if not the same or compatible types.
   */
  private void setSameTypeToBoolean() throws AssertionException {
    if (!d_array_relation) {
      if (d_lhs.getReturnTypeValue() == d_rhs.getReturnTypeValue()) {
        setReturnToBoolean();
      } else {
        setNumericTypeToBoolean();
      } 
    } else {
      setBooleanRelationData();
    } 
  }


  /**
   * Validate the expression semantics, first ensuring both subexpressions
   * are valid then that the binary expression is valid for the specified
   * operator.  Set the return type of this binary expression accordingly.
   *
   * @param   ext  The interface or class that owns this expression.
   * @param   m    The method that owns this expression.
   * @throws  gov.llnl.babel.symbols.AssertionException
   *               The exception that can be raised during the validation.
   */
  protected void validateSemantics(Extendable ext, Method m)
          throws AssertionException
  {
    /*
     * First ensure both subexpressions are valid.
     */
    validateSubexpressions(ext, m);

    /*
     * Now validate this binary expression, setting the return type
     * according to the return types of the left and right expressions
     * along with the operator.
     */
    switch (d_op) {
    case IF_AND_ONLY_IF:
    case IMPLIES:
    case LOGICAL_OR:
    case LOGICAL_AND:
    case LOGICAL_XOR:
      if (returnBoolean(d_lhs.getReturnTypeValue(), 
                        d_rhs.getReturnTypeValue())) {
        setReturnToBoolean();
      } else {
        throw new AssertionException(getReturnExceptionMessage("boolean"));
      }
      break;
    case BITWISE_AND:
    case MULTIPLY:
    case MINUS:
    case BITWISE_OR:
    case BITWISE_XOR:
    case POWER:
    case DIVIDE:
      setNumericType();
      break;
    case GREATER_EQUAL:
    case GREATER_THAN:
    case LESS_EQUAL:
      if (!returnString(d_lhs.getReturnTypeValue(), 
                        d_rhs.getReturnTypeValue())) {
        setNumericTypeToBoolean();
      } else {
        throw new AssertionException(getStringCompareExceptionMessage());
      }
      break;
    case EQUALS:
    case NOT_EQUAL:
      if (!returnString(d_lhs.getReturnTypeValue(), 
                        d_rhs.getReturnTypeValue())) {
        setSameTypeToBoolean();
      } else {
        throw new AssertionException(getStringCompareExceptionMessage());
      }
      break;
    case LESS_THAN:
      setNumericTypeToBoolean();
      break;
    case PLUS:
      if (returnString(d_lhs.getReturnTypeValue(), 
                       d_rhs.getReturnTypeValue())) {
        throw new AssertionException("Unable to validate expression semantics"
                   + " for \"" + getExceptionPrefix(ext, m) 
                   + "\" since do not currently support string concatentation"
                   + ", which is being attempted with the expressions \""
                   + d_lhs.toString() + "\" and \"" + d_rhs.toString() 
                   + "\".");
      } else {
        setNumericType();
      }
      break;
    case MODULUS:
    case REMAINDER:
    case SHIFT_LEFT:
    case SHIFT_RIGHT:
      setIntegerType();
      break;
    default:
      throw new AssertionException("Unable to validate expression semantics "
               + "for \"" + getExceptionPrefix(ext, m) 
               + "\" since invalid or unrecognized operator value \"" + d_op 
               + "\" for an expression whose left expression returns " 
               + d_lhs.getReturnTypeName() + " values and right returns " 
               + d_rhs.getReturnTypeName() + " values.");
    } 
  }


  /**
   * Returns the next set of starting indices (i.e., those for the RHS)
   * based on the macros contained in the subexpression (i.e., the LHS).
   */
  private int[] getNextStartInd(int[] startInd) {
    int[]  nextStartInd = new int[MethodCall.MAX_VALID_MACRO_RETURNS];
    for (int i=0; i<MethodCall.MAX_VALID_MACRO_RETURNS; i++) {
      if (startInd != null) {
        nextStartInd[i] = startInd[i];
        if (d_lhs!=null) {
          nextStartInd[i] += d_lhs.getNumArrayIterMacrosByType(
                                   MethodCall.MACRO_RETURN_TYPE[i]);
        }
      } else {
        nextStartInd[i] = 0;
      }
    }
    return nextStartInd;
  }


  /**
   * Return the list of array iteration macro messages, if any.  Each message
   * is a string where the first character indicates the return type associated
   * with the iteration.  The remaining characters will be the actual macro
   * invocation.
   */
  public ArrayList getArrayIterMacros(String epvVar, int[] startInd) {
    ArrayList list = new ArrayList();
    ArrayList ll = d_lhs.getArrayIterMacros(epvVar, startInd);
    ArrayList rl = d_rhs.getArrayIterMacros(epvVar, getNextStartInd(startInd));
    if ( (ll != null) && (ll.size() > 0) ) {
      list.addAll(ll);
    }
    if ( (rl != null) && (rl.size() > 0) ) {
      list.addAll(rl);
    }
    if (list.size() > 0) {
      return list;
    } else {
      return null;
    }
  }


  /**
   * Returns the number of macros supported by this assertion of the
   * specified type.  Valid types are given in MethodCall.java.
   */
  public int getNumArrayIterMacrosByType(char type) {
    return d_lhs.getNumArrayIterMacrosByType(type)
         + d_rhs.getNumArrayIterMacrosByType(type);
  }


  /**
   * Return the C version of the expression.
   *
   * ToDo...Need to finish this.  In particular, need to handle complex
   * subexpressions properly -- at least under the assumption that the
   * relevant complex struct has been declared!
   */
  public String cExpression(String epvVar, int[] startInd) {
    String expr = null;
    String lhs  = d_lhs.cExpression(epvVar, startInd);
    String rhs  = d_rhs.cExpression(epvVar, getNextStartInd(startInd));
    switch (d_op) {
    case BITWISE_OR:
    case BITWISE_XOR:
    case BITWISE_AND:
    case LOGICAL_OR:
    case LOGICAL_AND:
    case DIVIDE:
    case EQUALS:
    case GREATER_EQUAL:
    case GREATER_THAN:
    case LESS_EQUAL:
    case LESS_THAN:
    case MINUS:
    case MULTIPLY:
    case NOT_EQUAL:
    case PLUS:
    case MODULUS:
    case SHIFT_LEFT:
    case SHIFT_RIGHT:
      expr = "( (" + lhs + ") " + getOpCSymbol() + " (" + rhs + ") )";
      break;
    case POWER:
      expr = "pow(" + lhs + ", " + rhs + ")";
      break;
    case IF_AND_ONLY_IF:
      expr = "(  ( " + lhs + " && " + rhs + " ) "
        + "|| ( (!" + lhs + ") && (!" + rhs + ") )  )";
      break;
    case IMPLIES:
      expr = "( (!" + lhs + ") || (" + rhs + ") )";
      break;
    case REMAINDER:
      expr = "( ( (" + lhs + ") / (" + rhs + ") ) * (" + rhs + ") )";
      break;
    case LOGICAL_XOR:
      expr = "( ( (" + lhs + ") || (" + rhs + ") ) "
        + "&& !( (" + lhs + ") && (" + rhs + ") ) )";
      break;
    }
    return expr;
  }


  /**
   * Return the stringified version of the expression (in SIDL form).
   */
  public String toString() {
    String expr = d_lhs + " " + getOpSymbol() + " " + d_rhs;
    return hasParens() ?  "(" + expr + ")"  :  expr;
  }

  /**
   * Implement the "visitor pattern".
   */
  public Object accept(ExprVisitor ev, Object data) {
    return ev.visitBinaryExpression(this, data);
  }
}
