//
// File:        Assertion.java
// Package:     gov.llnl.babel.symbols
// Revision:    @(#) $Id $
// Description: An assertion clause.
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

import gov.llnl.babel.symbols.AssertionException;
import gov.llnl.babel.symbols.AssertionExpression;
import gov.llnl.babel.symbols.ASTNode;
import gov.llnl.babel.symbols.Comment;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Method;
import java.util.ArrayList;


public class Assertion extends ASTNode 	{
  private int                 d_type       = UNKNOWN;
  private String              d_source     = null;
  private String              d_tag        = null;
  private Comment             d_comment    = null;
  private AssertionExpression d_expression = null;

  /*
   * Valid assertion clause types.
   */
  public final static int UNKNOWN      = 0;
  public final static int INVARIANT    = 1;
  public final static int REQUIRE      = 2;
  public final static int REQUIRE_ELSE = 3;
  public final static int ENSURE       = 4;
  public final static int ENSURE_THEN  = 5;

  private final static int MIN_VALID_TYPE = 1;
  private final static int MAX_VALID_TYPE = 5;

  public final static String s_names[] = {
    "unknown", "invariant", "require", "require else", "ensure", "ensure then"
  };


  /**
   * Create a new object.
   *
   * @param   type     The type of the assertion.
   * @param   source   The owning interface or class.  For use in generated 
   *                   debug messages.
   * @param   tag      The tag, if any, associated with the assertion.  For
   *                   use in generated debug messags.
   * @param   comment  The comment, if any, associated with the assertion.
   * @throws  gov.llnl.babel.symbols.AssertionException
   *                   The exception raised if assertion type is invalid.
   */
  public Assertion(int type, String source, String tag, Comment comment)
       throws AssertionException
  {
    String prefix = (tag == null) ? "Assertion: " : "Assertion: " + tag;
    if (!isValidAssertionType(type)) {
      throw new AssertionException(prefix, "Invalid assertion type value \"" 
                + type + "\", must be in " + MIN_VALID_TYPE + "..." 
                + MAX_VALID_TYPE + ".");
    } else if ( (source == null) || (source.equals("")) ) {
      throw new AssertionException(prefix, "Cannot create assertion without "
                + "the source information since it is required for debug "
                + "messages.");
    }
    d_type       = type;
    d_source     = source;
    d_tag        = tag;
    d_comment    = comment;
    d_expression = null;
  }


  /**
   * Return TRUE if the type is valid; otherwise, return FALSE.
   */
  public static boolean isValidAssertionType(int type) {
    return ( (MIN_VALID_TYPE <= type) && (type <= MAX_VALID_TYPE) );
  }

  /**
   * Return the type of the assertion.
   */
  public int getType() {
    return d_type;
  }


  /**
   * Return the type of the assertion specified by name.
   *
   * @param  name  The name of the type of assertion whose type is to be 
   *               returned.
   */
  public int getType(String name) {
     int t = 1; 
     while ( (t<=MAX_VALID_TYPE) && (!name.equalsIgnoreCase(s_names[t])) ) {
       t++;
     }
     if (t>MAX_VALID_TYPE) {
       t = 0;
     }
     return t;
  }

  
  /**
   * Return TRUE if a precondition; otherwise, return FALSE.
   */
  public boolean isPrecondition() {
    return ( (d_type==REQUIRE) || (d_type==REQUIRE_ELSE) );
 }


  /**
   * Return TRUE if the specified type is a precondition; otherwise, 
   * return FALSE.
   */
  public static boolean isPrecondition(int type) {
    return ( (type==REQUIRE) || (type==REQUIRE_ELSE) );
 }


  /**
   * Return TRUE if a postcondition; otherwise, return FALSE.
   */
  public boolean isPostcondition() {
    return ( (d_type==ENSURE) || (d_type==ENSURE_THEN) );
  }


  /**
   * Return TRUE if the specified type is a postcondition; otherwise, 
   * return FALSE.
   */
  public static boolean isPostcondition(int type) {
    return ( (type==ENSURE) || (type==ENSURE_THEN) );
  }


  /**
   * Return TRUE if an invariant; otherwise, return FALSE.
   */
  public boolean isInvariant() {
    return (d_type==INVARIANT);
  }


  /**
   * Return TRUE if the specified type is an invariant; otherwise, 
   * return FALSE.
   */
  public static boolean isInvariant(int type) {
    return (type==INVARIANT);
  }


  /**
   * Return the name of the type of the assertion.  It is assumed the
   * type is valid/known thanks to the check in the constructor.
   */
  public String getTypeName() {
    return s_names[d_type];
  }


  /**
   * Return the name of the specified type of the assertion, if valid;
   * otherwise, return NULL.
   */
  public static String getTypeName(int type) {
    return isValidAssertionType(type) ? s_names[type] : null;
  }


  /**
   * Return the source associated with this assertion.
   */
  public String getSource() {
    return d_source;
  }


  /**
   * Return the tag associated with this list of object states.
   */
  public String getTag() {
    return d_tag;
  }


  /**
   * Return the comment, if any, associated with this list of object states.
   */
  public Comment getComment() {
    return d_comment;
  }


  /**
   * Return the prefix for exception messages based on the specified
   * extendable and method.
   *
   * @param  ext  The interface or class that owns this expression.
   * @param  m    The method that owns this expression.
   */
  protected String getExceptionPrefix(Extendable ext, Method m) {
    return (ext == null) ? "Assertion"
                : (m == null) ? ext.getFullName()
                              : ext.getFullName() + "." + m.getLongMethodName();
  }


  /**
   * Ensure the expression does not contain any (sub)expressions specifically
   * for post-condition assertions.  For example, the assertion should not 
   * contain a PURE or RESULT clause.
   *
   * @param   expr  The assertion expression being checked.
   * @throws  gov.llnl.babel.symbols.AssertionException
   *                The exception raised if post-condition assertions present.
   */
  protected void ensureNoPostAssertions(AssertionExpression expr)
          throws AssertionException
  {
    if (expr.hasPure()) {
      throw new AssertionException("Assertion", "Cannot specify a PURE clause "
                + "in a(n) " + getTypeName() + "assertion.");
    } 
    if (expr.hasResult()) {
      throw new AssertionException("Assertion", "Cannot specify RESULT in a(n) "
                + getTypeName() + " assertion.");
    }
    if (expr.hasResultOrOutArg(true)) {
      throw new AssertionException("Assertion", "Cannot specify output-only "
                + "arguments in a(n) " + getTypeName() + " assertion.");
    }
  }


  /**
   * Set the assertion expression.  Note this is added during syntactic
   * parsing so not all of the information for evaluating the validity of 
   * the expression is available (e.g., function return values).  Hence,
   * the validity of the expression itself is left as a separate step in 
   * the process once the symbols for the associated extendable are created.
   *
   * Assumptions:
   * 1)  requiresMethodContext() returns TRUE ONLY if the assertion expression
   *     really requires method context.  For example, an expression that 
   *     contains a method call with no arguments only requires the extendable
   *     context, NOT the method context.  Same is TRUE if the arguments are
   *     literals.  However, method context is required if one or more of the
   *     arguments are identifiers.
   *
   * @param   expr  The assertion expression being checked.
   * @throws  gov.llnl.babel.symbols.AssertionException
   *                The exception raised if type validation fails.
   */
  public void setExpression(AssertionExpression expr) 
       throws AssertionException
  {
    checkFrozen();
    if ( isInvariant() ) {
      ensureNoPostAssertions(expr);
      if ( expr.requiresMethodContext() ) {
        throw new AssertionException("Assertion", "An invariant cannot contain "
                  + "an assertion expression that is associated with a "
                  + "method.");
      }
    } else if ( isPrecondition() ) {
      ensureNoPostAssertions(expr);
    } 
    d_expression = expr;
  }

 
  /**
   * Return the assertion expression.
   */
  public AssertionExpression getExpression() {
    return d_expression;
  }


  /**
   * Return TRUE if the expression has been validated; otherwise, return
   * FALSE.
   */
  public boolean isValid() {
    return d_expression.isValid();
  }


  /**
   * Validate the assertion expression within the context of the given
   * extendable and optional method.  
   *
   * @param   ext   The interface or class that owns this expression.
   * @param   m     The method that owns this expression.
   * @param   skip  If TRUE, will skip the validation process if the
   *                expression has already been marked as being valid.
   * @throws  gov.llnl.babel.symbols.AssertionException
   *                The exception that can be raised during the validation.
   */
  public void validateExpression(Extendable ext, Method m, boolean skip) 
       throws AssertionException
  {
    if (d_expression == null) {
      throw new AssertionException(getExceptionPrefix(ext, m), 
                   "Cannot validate a " + getTypeName() + " assertion when its "
                 + "expression is null.");
    }
//    if ( !(skip && isValid()) ) {
      if ( (ext == null) && d_expression.requiresExtendableContext() ) {
        throw new AssertionException(getExceptionPrefix(ext, m), 
                      "Cannot validate the " + getTypeName() + " assertion "
                    + "expression without access to the extendable's context.");
      }
      if ( (m == null) && d_expression.requiresMethodContext() ) {
        throw new AssertionException(getExceptionPrefix(ext, m), 
                     "Cannot validate the " + getTypeName() + " assertion "
                   + "expression without access to the method's context.");
      }

      d_expression.validateExpression(ext, m);
//    }
  }


  /**
   * Return TRUE if a pure clause is found within the expression; otherwise,
   * return FALSE.
   */
  public boolean hasPureClause() {
    return d_expression.hasPure();
  }


  /**
   * Return TRUE if a result clause is found within the expression; otherwise,
   * return FALSE.
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
   * Return TRUE if the expression is, or has, at least one method call;
   * otherwise, return FALSE.
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
   * Returns the default complexity of the expression.
   */
  public int getDefaultComplexity() {
    return d_expression.getDefaultComplexity();
  }


  /**
   * Return TRUE if the assertion's expression is, or has, the specified
   * built-in method call; otherwise, return FALSE.
   */
  public boolean hasBuiltinMethod(int type) {
    return d_expression.hasBuiltinMethod(type);
  }

                                                                                
  /**
   * Return TRUE if the assertion's expression has a method AND the method 
   * is any user-defined method (when any is TRUE) or it is an user-defined 
   * method with a throws clause (if any is FALSE); otherwise, return FALSE.
   */
  public boolean hasUserDefinedMethod(boolean any) {
    return d_expression.hasUserDefinedMethod(any);
  }


  /**
   * Return the error message associated with a failure of this assertion.
   */
  public String errorMessage() {
    return getTypeName().toUpperCase() + " VIOLATION: " + d_source + ": " 
      + toString() + ".";
  }


  /**
   * Return the error message associated with a failure of this assertion,
   * appending the given mehod name to the error message.  (ToDo...This is 
   * only necessary until it gets added properly by the parser!)
   */
  public String errorMessage(String methName) {
    return getTypeName().toUpperCase() + " VIOLATION: " + d_source + ": "
      + methName + ": " + toString() + ".";
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
   * Return the C version of the expression used to check for violation.
   */
  public String cExpression(String epvVar, int[] startInd) {
    return d_expression.cExpression(epvVar, startInd);
  }


  /**
   * Return the stringified version of the expression (in SIDL form) BUT 
   * without the comment.  That must be retrieved separately.
   */
  public String toString() {
    return ((d_tag != null) && (!d_tag.equals(""))) ?  
                                      d_tag + ": " + d_expression.toString() 
                                    : d_expression.toString();
  }

  public void freeze()
  {
    if (!d_frozen) {
      super.freeze();
      if (d_comment != null) d_comment.freeze();
    }
  }
}

