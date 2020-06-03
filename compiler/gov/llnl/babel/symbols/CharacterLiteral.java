//
// File:        CharacterLiteral.java
// Package:     gov.llnl.babel.symbols
// Revision:    @(#) $Id $
// Description: The character literal assertion expression interface.
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
import gov.llnl.babel.symbols.ExprVisitor;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Literal;
import gov.llnl.babel.symbols.Method;
import java.util.ArrayList;


public class CharacterLiteral extends Literal {
  private char d_value;


  /**
   * Create a new object.
   *
   * @param   cc      The character.
   * @throws  gov.llnl.babel.symbols.AssertionException
   *                  The exception raised if errors during any validation.
   */
  public CharacterLiteral(char cc, Context context) 
    throws AssertionException 
  {
    super(true, context);
    d_value = cc;
    setReturnToCharacter();
  }


  /**
   * Return the character value.
   */
  public char getValue() {
    return d_value;
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
     * There is nothing to do here since this is a simple literal.
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
   */
  public String cExpression(String epvVar, int[] startInd) {
    final char value = getValue();
    if (Character.isISOControl(value)) {
      return "'\\0" + Integer.toOctalString(value) + "'";
    }
    else {
      return "'" + value + "'";
    }
  }



  /**
   * Return the stringified version of the expression (in SIDL form).
   */
  public String toString() {
    String expr = "\'" + d_value + "\'";
    return hasParens() ?  ("(" + expr + ")")  : expr;
  }

  /**
   * Implement the "visitor pattern".
   */
  public Object accept(ExprVisitor ev, Object data) {
    return ev.visitCharacterLiteral(this, data);
  }
}
