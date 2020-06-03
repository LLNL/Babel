//
// File:        DComplexLiteral.java
// Package:     gov.llnl.babel.symbols
// Revision:    @(#) $Id $
// Description: The double complex literal assertion expression interface.
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
import gov.llnl.babel.symbols.DoubleLiteral;
import gov.llnl.babel.symbols.ExprVisitor;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Literal;
import gov.llnl.babel.symbols.LongLiteral;
import gov.llnl.babel.symbols.Method;
import java.util.ArrayList;


public class DComplexLiteral extends Literal {
  private DoubleLiteral d_real      = null;
  private DoubleLiteral d_imaginary = null;


  /**
   * Create a new object.
   *
   * Assumption(s):
   * 1) The double literals that make up the real and imaginary parts are
   *    already marked as valid.  This is reasonable since all basic
   *    literals are assumed to be valid immediately after creation.
   *
   * @param   r       The real part of the number.
   * @param   i       The imaginary part of the number.
   * @throws  gov.llnl.babel.symbols.AssertionException
   *                  The exception raised if errors in any validation.
   */
  public DComplexLiteral(DoubleLiteral r, DoubleLiteral i, 
                         Context context) 
       throws AssertionException 
  {
    /*
     * Go ahead and claim this literal is valid even though the checks 
     * for valid input have not completed.  This is "okay" since the 
     * literal will be valid IF this method completes without exception.
     */
    super(true, context);
    if ( (r == null) || (!r.isValid()) ) {
      throw new AssertionException("DComplexLiteral", "Cannot create a "
                + "complex literal with the real part null or invalid.");
    } else if ( (i == null) || (!i.isValid()) ) {
      throw new AssertionException("DComplexLiteral", "Cannot create a "
                + "complex literal with the imaginary part null or invalid.");
    }
    d_real      = r;
    d_imaginary = i;
    setReturnToDComplex();
  }


  /**
   * Create a new object.
   *
   * Assumption(s):
   * 1) The double literal that makes up the real part and the long literal 
   *    that makes up the imaginary part are already marked as valid.  This 
   *    is reasonable since all basic literals are assumed to be valid 
   *    immediately after creation.
   *
   * @param   r       The double literal real part of the number.
   * @param   i       The long literal imaginary part of the number.
   * @throws  gov.llnl.babel.symbols.AssertionException
   *                  The exception raised if errors in any validation.
   */
  public DComplexLiteral(DoubleLiteral r, LongLiteral i, Context context) 
       throws AssertionException 
  {
    /*
     * Go ahead and claim this literal is valid even though the checks 
     * for valid input have not completed.  This is "okay" since the 
     * literal will be valid IF this method completes without exception.
     */
    super(true, context);
    if ( (r == null) || (!r.isValid()) ) {
      throw new AssertionException("DComplexLiteral", "Cannot create a "
                + "complex literal with the real part null or invalid.");
    } else if ( (i == null) || (!i.isValid()) ) {
      throw new AssertionException("DComplexLiteral", "Cannot create a "
                + "complex literal with the imaginary part null or invalid.");
    }
    d_real      = r;
    d_imaginary = new DoubleLiteral(new Double(i.getValue().doubleValue()), 
                                    i.getImage(), context);
    setReturnToDComplex();
  }


  /**
   * Create a new object.
   *
   * Assumption(s):
   * 1) The long literal that makes up the real and the double literal
   *    that makes up the imaginary part are already marked as valid.  
   *    This is reasonable since all basic literals are assumed to be 
   *    valid immediately after creation.
   *
   * @param   r       The long literal real part of the number.
   * @param   i       The double literal imaginary part of the number.
   * @throws  gov.llnl.babel.symbols.AssertionException
   *                  The exception raised if errors in any validation.
   */
  public DComplexLiteral(LongLiteral r, DoubleLiteral i, Context context) 
       throws AssertionException 
  {
    /*
     * Go ahead and claim this literal is valid even though the checks 
     * for valid input have not completed.  This is "okay" since the 
     * literal will be valid IF this method completes without exception.
     */
    super(true, context);
    if ( (r == null) || (!r.isValid()) ) {
      throw new AssertionException("DComplexLiteral", "Cannot create a "
                + "complex literal with the real part null or invalid.");
    } else if ( (i == null) || (!i.isValid()) ) {
      throw new AssertionException("DComplexLiteral", "Cannot create a "
                + "complex literal with the imaginary part null or invalid.");
    }
    d_real      = new DoubleLiteral(new Double(r.getValue().doubleValue()), 
                                    r.getImage(), context);
    d_imaginary = i;
    setReturnToDComplex();
  }


  /**
   * Return the literal of the real part of this object.
   */
  public DoubleLiteral getRealLiteral() {
    return d_real;
  }


  /**
   * Return the value of the real part of this object.
   */
  public Double getRealValue() {
    return d_real.getValue();
  }


  /**
   * Return the string image of the real part of this object.
   */
  public String getRealImage() {
    return d_real.getImage();
  }


  /**
   * Return the literal of the imaginary part of this object.
   */
  public DoubleLiteral getImaginaryLiteral() {
    return d_imaginary;
  }


  /**
   * Return the value of the imaginary part of this object.
   */
  public Double getImaginaryValue() {
    return d_imaginary.getValue();
  }


  /**
   * Return the string image of the imaginary part of this object.
   */
  public String getImaginaryImage() {
    return d_imaginary.getImage();
  }


  /**
   * Return TRUE if the expression is, or has, a PURE clause; otherwise,
   * return FALSE.
   */
  public boolean hasPure() {
    return false;
  }


  /**
   * Return TRUE if the expression contains RESULT; otherwise, return FALSE.
   */
  public boolean hasResult() {
    return false;
  }


  /**
   * Return TRUE if a result clause or method argument is found within the
   * expression; otherwise, return FALSE.
   */
  public boolean hasResultOrArg() {
    return false;
  }


  /**
   * Return TRUE if a result clause or output argument is found within the
   * expression; otherwise, return FALSE.
   *
   * @param  outOnly  TRUE if only concerned with output arguments that are
   *                  out only; FALSE otherwise.
   */
  public boolean hasResultOrOutArg(boolean outOnly) {
    return false;
  }



  /**
   * Return TRUE if the expression is, or has, at least one method call;
   * otherwise, return FALSE.
   */
  public boolean hasMethodCall() {
    return false;
  }

  /**
   * Return TRUE if the expression is, or has, a call to the specified
   * method; otherwise, return FALSE.  
   */
  public boolean hasMethodCall(String name) {
    return false;
  }



  /**
   * Return the default complexity of the expression (0 = constant, 1 = linear,
   * etc.).
   */
  public int getDefaultComplexity() {
    return 0;
  }


  /**
   * Return TRUE if the expression is, or has, the specified built-in method
   * call; otherwise, return FALSE.
   */
  public boolean hasBuiltinMethod(int type) {
    return false;
  }


  /**
   * Return TRUE if the expression has a method AND the method is any
   * user-defined method (when any is TRUE) or it is an user-defined
   * method with a throws clause (if any is FALSE); otherwise, return FALSE.
   */
  public boolean hasUserDefinedMethod(boolean any) {
    return false;
  }


  /**
   * Return TRUE if extendable context is required to validate the expression; 
   * otherwise, return FALSE.
   */
  public boolean requiresExtendableContext() {
    return false;
  }


  /**
   * Return TRUE if method context is required to validate the expression; 
   * otherwise, return FALSE.
   */
  public boolean requiresMethodContext() {
    return false;
  }


  /**
   * Validate the expression semantics, if necessary, within the context of the 
   * extendable and optional method.  
   *
   * @param   ext  The interface or class that owns this expression.
   * @param   m    The method that owns this expression.
   * @throws  gov.llnl.babel.symbols.AssertionException
   *               The exception that can be raised during any validation.
   */
  protected void validateSemantics(Extendable ext, Method m)
          throws AssertionException
  {
    /*
     * There is nothing to do here since this is a literal that consists
     * of two simple literals.
     */
  }


  /**
   * Return the list of array iteration macro messages, if any.  Each message
   * is a string where the first character indicates the return type associated
   * with the iteration.  The remaining characters will be the actual macro
   * invocation.
   */
  public ArrayList getArrayIterMacros(String epvVar, int[] startInd) {
    return null;
  }


  /**
   * Returns the number of macros supported by this assertion of the
   * specified type.  Valid types are given in MethodCall.java.
   */
  public int getNumArrayIterMacrosByType(char type) {
    return 0;
  }

  /**
   * Return the C version of the expression.
   *
   * ToDo...This is actually more involved if this is part of a unary or
   * binary expression because the <var>.real and <var>.imaginary
   * parts of a sidl_<type>complex must be used instead of this.  However,
   * this should be okay for passing the literal to a method call.
   */
  public String cExpression(String epvVar, int[] startInd) {
    return "{" + d_real.toString() + ", " + d_imaginary.toString() + "}";
  }


  /**
   * Return the stringified version of the expression (in SIDL form).
   */
  public String toString() {
    String expr = d_real.toString() + ", " + d_imaginary.toString();
    return hasParens() ?   "(" + expr + ")"  :  expr;
  }
  /**
   * Implement the "visitor pattern".
   */
  public Object accept(ExprVisitor ev, Object data) {
    return ev.visitDComplexLiteral(this, data);
  }
}
