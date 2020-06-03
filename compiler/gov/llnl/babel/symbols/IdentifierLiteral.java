//
// File:        IdentifierLiteral.java
// Package:     gov.llnl.babel.symbols
// Revision:    @(#) $Id $
// Description: The identifier literal/catch-all expression for assertions.
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
//import gov.llnl.babel.symbols.Type;
import gov.llnl.babel.backend.c.C;
import gov.llnl.babel.symbols.Argument;
import gov.llnl.babel.symbols.AssertionException;
import gov.llnl.babel.symbols.ExprVisitor;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Literal;
import gov.llnl.babel.symbols.Method;
import java.util.ArrayList;


public class IdentifierLiteral extends Literal {
  private int    d_type          = ARGUMENT;
  private int    d_argument_mode = -1;
  private String d_identifier    = null;

  /*
   * Valid identifier literal types.
   *
   * ToDo...Eventually add support for enumerations.  One issue to 
   * address is how to determine to which enumeration the value
   * belongs.
   */
  public final static int ARGUMENT = 0;
  public final static int NULL     = 1;
  public final static int PURE     = 2;
  public final static int RESULT   = 3;

  private final static int MIN_TYPE_VALUE  = 0;  // Minimum valid value
  private final static int MAX_TYPE_VALUE  = 3;  // Maximum valid value

  private final static String s_type_name[] = {
    "argument", "null", "pure", "result"
  };

  /*
   * Valid modes for when the type is an argument.
   */
  public final static int IN    = Argument.IN;
  public final static int INOUT = Argument.INOUT;
  public final static int OUT   = Argument.OUT;

  /**
   * Construct a new object.  
   *
   * @param   id      The string identifier.
   * @throws  gov.llnl.babel.symbols.AssertionException
   *                  An exception that could be raised during any validation.
   */
  public IdentifierLiteral(String id, Context context) 
    throws AssertionException 
  {
    super(false, context);
    if ( (id == null) || (id.equals("")) ) {
      throw new AssertionException("Cannot instantiate an IdentifierLiteral "
                  + "without the identifier.");
    }
    d_identifier = id;
    if (id.equalsIgnoreCase("null")) {
      /* 
       * The return type could be string or object, depending upon the context
       * so leave it undetermined for now.  
       */
      d_type = NULL;
    } else if (id.equalsIgnoreCase("pure")) {
      /* 
       * The return type will have to be boolean because this identifier is
       * only allowed in an "is pure" clause...
       */
      d_type = PURE;
      setReturnToBoolean();
    } else if (id.equalsIgnoreCase("result")) {
      /* 
       * The return type cannot be known until the validation time where the 
       * context of the method is known so leave it undetermined for now.
       */
      d_type = RESULT;
    } else {
      /*
       * Now we're left with some unknown identifier whose type cannot be
       * determined until validation because there is no context information 
       * at this point.  The only possibilities (at this time) are that the
       * identifier belongs to an argument of a method OR it is a temporary
       * "expression" created during parsing of a SIDL file (i.e., an
       * assertion tag).
       */
      d_type = ARGUMENT;
    }
  }


  /**
   * Return the identifier.
   */
  public String getIdentifier() {
    return d_identifier;
  }


  /**
   * Return the identifier type.
   */
  public int getIdentifierType() {
    return d_type;
  }


  /**
   * Return TRUE if the identifier is NULL; otherwise, return FALSE.
   */
  public boolean isNull() {
    return d_type == NULL;
  }


  /**
   * Return TRUE if the identifier is ARGUMENT; otherwise, return FALSE.
   */
  public boolean isArgument() {
    return d_type == ARGUMENT;
  }


  /**
   * Return TRUE if the identifier is a reserved keyword; otherwise, return 
   * FALSE.
   */
  public boolean isReserved() {
    return ( (d_type == NULL) || (d_type == PURE) || (d_type == RESULT) );
  }


  /**
   * Return the name of the identifier type.  It is assumed the code 
   * for the type is valid since it is set internally in the constructor.
   */
  public String getIdentifierTypeName() {
    String nm = null;

    if (  (MIN_TYPE_VALUE <= d_type        )
       && (d_type         <= MAX_TYPE_VALUE) ) {
      nm = s_type_name[d_type];
    } 

    return nm;
  }


  /**
   * Return TRUE if the expression is a PURE clause; otherwise, return
   * FALSE.  In this case, TRUE will be returned if the identifier is "pure".
   */
  public boolean hasPure() {
    return d_type == PURE;
  }


  /**
   * Return TRUE if the expression contains RESULT; otherwise, return FALSE.
   */
  public boolean hasResult() {
    return d_type == RESULT;
  }


  /**
   * Return TRUE if a result clause or method argument is found within the
   * expression; otherwise, return FALSE.
   */
  public boolean hasResultOrArg() {
    return (d_type == RESULT) || (d_type == ARGUMENT);
  }


  /**
   * Return TRUE if a result clause or output argument is found within the
   * expression; otherwise, return FALSE.
   *
   * @param  outOnly  TRUE if only concerned with output arguments that are
   *                  out only; FALSE otherwise.
   */
  public boolean hasResultOrOutArg(boolean outOnly) {
    boolean has;

    if (outOnly) {
      has =    (d_type == RESULT)
          || ( (d_type == ARGUMENT) && (d_argument_mode == OUT) );
    } else {
      has =    (d_type == RESULT)
          || (  (d_type == ARGUMENT)
             && ( (d_argument_mode == OUT) || (d_argument_mode == INOUT) ) );
    }

    return has;
  }


  /**
   * Return TRUE if the expression has at least one method call; otherwise, 
   * return FALSE.  Since this is an identifier it cannot be a method call.
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
    return ((d_type == ARGUMENT) || (d_type == NULL) || (d_type == RESULT));
  }


  /**
   * Return TRUE if method context is required to validate the expression;
   * otherwise, return FALSE.  This is separate from and in addition to the
   * extendable context.
   */
  public boolean requiresMethodContext() {
    return ((d_type == ARGUMENT) || (d_type == RESULT));
  }


  /**
   * Return the message complaining that the owning entity is required.
   *
   * @param  entity  The kind of owning entity (e.g., method).
   */
  private String getMissingMessage(String entity) {
    return "IdentifierLiteral: Cannot determine the return type of the "
         + "identifier \"" + d_identifier + "\" in an assertion clause "
         + "without the owning " + entity + ".";
  }


  /**
   * Validate the expression semantics, if necessary, within the context of the
   * extendable and optional method.  
   *
   * Assumptions:
   * 1) If this is NULL, then it is associated either with a binary expression 
   *    or a method call.  In either case, it is assumed the corresponding
   *    expression container has already set the return type of this expression
   *     since there is no reasonable way to determine it here.
   * 2) ARGUMENT is (the name of) an argument of the specified method that 
   *    is supposed to be used in a method call.
   * 3) RESULT is the return value associated with the specified method
   *    within the specified extendable.
   *
   * @param   ext  The interface or class that owns this expression.
   * @param   m    The method that owns this expression.
   * @throws  gov.llnl.babel.symbols.AssertionException
   *               The exception raised if errors in any validation.
   */
  protected void validateSemantics(Extendable ext, Method m)
          throws AssertionException
  {
    switch (d_type) {
      case NULL:
        /* 
         * This only works if it is associated with a binary expression, 
         * in which case, the return type must be identical to that of the
         * expression on the other side of the operator.
         */
        if (getReturnType() == null) {
          throw new AssertionException("The return type associated with the "
                    + "NULL IdentifierLiteral for " + getExceptionPrefix(ext,m)
                    + " must have already been set prior to its validation.");
        }
        break;
      case PURE:
        if (getReturnType() == null) {
          setReturnToBoolean();
        } else if (!returnIsBoolean()) {
          throw new AssertionException("The return type for an "
                    + "IdentifierLiteral pure clause for " 
                    + getExceptionPrefix(ext, m) 
                    + " should never have been (re)set to anything other than "
                    + "boolean.");
        }
        break;
      case RESULT:
        /* 
         * This corresponds to the result of the specified method, so set
         * it if it hasn't already been done.
         */
        if (getReturnType() == null) {
          if (ext != null) {
            if (m != null) {
              setReturnType(m.getReturnType());
            } else {
              throw new AssertionException(getExceptionPrefix(ext, m), 
                                           getMissingMessage("method"));
            } 
          } else {
            throw new AssertionException(getMissingMessage("extendable"));
          }
        }
        break;
      case ARGUMENT:
        /* 
         * This corresponds to an argument of the specified method, so set
         * the return type if it hasn't already been done.
         *
         * ToDo:  In order to support things like enumeration constants,
         * it will be necessary to add more context information (or
         * access the symbol table).  Given a suitable convention for
         * their specification, then the symbol(s) must be looked up to
         * determine if it is a proper enumeration constant.  (Should
         * also be marked as an ENUMERATION instead of an ARGUMENT.)
         */
        if (getReturnType() == null) {
          if (ext != null) {
            if (m != null) {
              Type type = m.getArgumentType(d_identifier);
              int  mode = m.getArgumentMode(d_identifier);
              if (type != null) {
                setReturnType(type);
                d_argument_mode = mode;
              } else {
                throw new AssertionException( "The IdentifierLiteral " 
                          + d_identifier
                          + " is not a recognized argument of method "
                          + m.getLongMethodName() + " for "
                          + getExceptionPrefix(ext,m) + ".");
              }
            } else {
              throw new AssertionException(getExceptionPrefix(ext, m), 
                                           getMissingMessage("method"));
            } 
          } else {
            throw new AssertionException(getMissingMessage("extendable"));
          }
        }
        break;
      default:
        throw new AssertionException("Unrecognized type encountered during "
                  + "semantic validation for IdentifierLiteral \"" 
                  + d_identifier + "\" of " + getExceptionPrefix(ext, m)
                  + ". This should NEVER happen.");
    }
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
   * WARNING:  This should NOT be invoked for special literals such as
   * PURE because there is nothing to check, which means that the 
   * expression in the if-statement will be empty!
   */
  public String cExpression(String epvVar, int[] startInd) {
    String res;
    switch (d_type) {
      case RESULT:
        res = C.FUNCTION_RESULT;
        break;
      case PURE:
        res = null;
        break;
      case NULL:
        res = C.NULL;
        break;
      case ARGUMENT:
        if ( (d_argument_mode == INOUT) || (d_argument_mode == OUT) ) {
          res = "(*" + d_identifier + ")";
        } else {
          res = d_identifier;
        }
        break;
      default:
        res = null;
        break;
    }
    return res;
  }

  /**
   * Return the stringified version of the expression (in SIDL form).
   */
  public String toString() {
    return hasParens() ?  "(" + d_identifier + ")"  :  d_identifier;
  }

  /**
   * Implement the "visitor pattern".
   */
  public Object accept(ExprVisitor ev, Object data) {
    return ev.visitIdentifierLiteral(this, data);
  }
}
