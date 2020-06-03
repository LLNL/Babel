//
// File:        AssertionExpression.java
// Package:     gov.llnl.babel.symbols
// Revision:    @(#) $Id $
// Description: The assertion expression interface.
//
//*************************************************************
// Usage: 
//
// The life of an assertion expression is expected to conform to
// the following steps:
//
// 1)  The expression is instantiated with a call to super(),
//     setting the values of the arguments accordingly.
//
// 2)  Once the context (e.g., owning class and, if applicable, 
//     method) is known, validateExpression() is invoked to 
//     validate the semantics of the expression while setting 
//     deferred return types, etc. as needed.
// 
//*************************************************************
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
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.Type;
import java.util.ArrayList;


public abstract class AssertionExpression {
  private boolean d_parens      = false;
  private Type    d_return_type = null;
  private boolean d_valid       = false;
  protected Context d_context;

  public static final String ERROR_SEMANTIC_VALIDATION  =
                                         "Semantic Validation Failure: ";

  /**
   * Used to initialize the basic expression attributes.
   *
   * @param  valid   Use TRUE only if the expression is clearly valid from the 
   *                 start (e.g., most literals).
   */
  public AssertionExpression(boolean valid,
                             Context context) {
    d_valid  = valid;
    d_context = context;
  }


  /**
   * Sets the parens attribute to reflect whether or not the expression
   * is specified to be contained within parentheses.
   *
   * @param  parens  TRUE if the expression is known to be specified to be
   *                 contained within parentheses; FALSE otherwise.  
   */
  public void setParens(boolean parens) {
    d_parens = parens;
  }


  /**
   * Return TRUE if the original expression had parentheses; otherwise,
   * return FALSE.
   */
  public boolean hasParens() {
    return d_parens;
  }


  /**
   * Set the return type associated with the expression based on the 
   * specified type value.  
   *
   * @param   return_type  A return type value for this expression.
   * @throws  gov.llnl.babel.symbols.AssertionException
   *                       The exception raised if the specified return type is 
   *                       still not determined.
   */
  protected void setReturnType(int return_type) throws AssertionException {
    if (return_type != Type.ARRAY) {
      if (  (return_type < Type.BOOLEAN) 
         || (return_type > Type.INTERFACE ) ) {
        throw new AssertionException("Return type value \"" + return_type 
                    + "\" invalid, must be in " + Type.BOOLEAN + "..." 
                    + Type.INTERFACE + " or " + Type.ARRAY + ".");
      } else {
        d_return_type = new Type(return_type);
      }
    } else {
      throw new AssertionException("Programming Error: Cannot set return type "
                  + "of an array using setReturnType(int).");
    }
  }


  /**
   * Set the return type associated with the expression based on the value
   * associated with the specified return type.  
   *
   * @param   return_type  A return type containing the value for this 
   *                       expression.
   * @throws  gov.llnl.babel.symbols.AssertionException
   *                       The exception raised if the specified return type is 
   *                       still not determined.
   */
  protected void setReturnType(Type return_type) throws AssertionException {
    if (return_type != null) {
      int type = return_type.getDetailedType();
      if (type == Type.ARRAY) {
        d_return_type = new Type(return_type.getArrayType(), 
                                 return_type.getArrayDimension(), 
                                 return_type.getArrayOrder(),
                                 d_context );
      } else if ((type == Type.ENUM) || (type == Type.SYMBOL)) {
        d_return_type = new Type(return_type.getSymbolID(), d_context);
      } else {
        setReturnType(return_type.getBasicType());
      }
    } else {
      throw new AssertionException("Cannot set return type of an "
                  + "AssertionExpression to a null Type.");
    }
  }


  /**
   * Return the return type.
   */
  public Type getReturnType() {
    return d_return_type;
  }


  /**
   * Return the name of the return type or, if empty, null.
   */
  public String getReturnTypeName() {
    return (d_return_type == null) ? null : d_return_type.getTypeName();
  }


  /**
   * Return the value of the return type or -1 if no return type.
   *
   * Assumption(s):
   * 1) Value of -1 is not a valid return type value per the Type class.
   */
  public int getReturnTypeValue() {
    return (d_return_type == null) ? -1 : d_return_type.getDetailedType();
  }


  /**
   * Return TRUE if the return type is an array; otherwise, return FALSE.
   */
  public boolean returnIsArray() {
    return (  (d_return_type               != null        ) 
           && (d_return_type.getBasicType() == Type.ARRAY) );
   }


  /**
   * Return TRUE if the return type is a boolean; otherwise, return FALSE.
   */
  public boolean returnIsBoolean() {
    return (  (d_return_type               != null        ) 
           && (d_return_type.getBasicType() == Type.BOOLEAN) );
   }


  /**
   * Return TRUE if the return type is a character; otherwise, return FALSE.
   */
  public boolean returnIsCharacter() {
    return (  (d_return_type               != null     ) 
           && (d_return_type.getBasicType() == Type.CHAR) );
   }


  /**
   * Return TRUE if the return type is a double complex; otherwise, return 
   * FALSE.
   */
  public boolean returnIsDComplex() {
    return (  (d_return_type                != null        ) 
           && (d_return_type.getBasicType() == Type.DCOMPLEX) );
   }


  /**
   * Return TRUE if the return type is a double; otherwise, return FALSE.
   */
  public boolean returnIsDouble() {
    return (  (d_return_type                != null        ) 
           && (d_return_type.getBasicType() == Type.DOUBLE) );
   }


  /**
   * Return TRUE if the return type is a float complex; otherwise, return 
   * FALSE.
   */
  public boolean returnIsFComplex() {
    return (  (d_return_type                != null        ) 
           && (d_return_type.getBasicType() == Type.FCOMPLEX) );
   }


  /**
   * Return TRUE if the return type is a float; otherwise, return FALSE.
   */
  public boolean returnIsFloat() {
    return (  (d_return_type                != null        ) 
           && (d_return_type.getBasicType() == Type.FLOAT) );
   }


  /**
   * Return TRUE if the return type is a integer; otherwise, return FALSE.
   */
  public boolean returnIsInteger() {
    return (  (d_return_type                != null    ) 
           && (d_return_type.getBasicType() == Type.INT) );
   }


  /**
   * Return TRUE if the return type is a long; otherwise, return FALSE.
   */
  public boolean returnIsLong() {
    return (  (d_return_type                != null        ) 
           && (d_return_type.getBasicType() == Type.LONG) );
   }


  /**
   * Return TRUE if the return type is an array; otherwise, return FALSE.
   */
  public boolean returnIsNumericArray() {
    return (  (d_return_type != null         ) 
           && (d_return_type.isNumericArray()) );
   }


  /**
   * Return TRUE if the return type is a opaque; otherwise, return FALSE.
   */
  public boolean returnIsOpaque() {
    return (  (d_return_type                != null        ) 
           && (d_return_type.getBasicType() == Type.OPAQUE) );
   }


  /**
   * Return TRUE if the return type is a string; otherwise, return FALSE.
   */
  public boolean returnIsString() {
    return (  (d_return_type                != null        ) 
           && (d_return_type.getBasicType() == Type.STRING) );
   }


  /**
   * Return TRUE if the return type is an enumeration; otherwise, return FALSE.
   */
  public boolean returnIsEnum() {
    return (  (d_return_type                != null        )
           && (d_return_type.getBasicType() == Type.ENUM) );
   }


  /**
   * Return TRUE if the return type is a class; otherwise, return FALSE.
   */
  public boolean returnIsClass() {
    return (  (d_return_type                != null        )
           && (d_return_type.getBasicType() == Type.CLASS) );
   }


  /**
   * Return TRUE if the return type is an interface; otherwise, return FALSE.
   */
  public boolean returnIsInterface() {
    return (  (d_return_type                != null        )
           && (d_return_type.getBasicType() == Type.INTERFACE) );
   }


  /**
   * Set the return type to boolean.
   */
  protected void setReturnToBoolean() throws AssertionException {
    setReturnType(Type.BOOLEAN);
  }


  /**
   * Set the return type to character.
   */
  protected void setReturnToCharacter() throws AssertionException {
    setReturnType(Type.CHAR);
  }


  /**
   * Set the return type to double complex.
   */
  protected void setReturnToDComplex() throws AssertionException {
    setReturnType(Type.DCOMPLEX);
  }


  /**
   * Set the return type to double.
   */
  protected void setReturnToDouble() throws AssertionException {
    setReturnType(Type.DOUBLE);
  }


  /**
   * Set the return type to float complex.
   */
  protected void setReturnToFComplex() throws AssertionException {
    setReturnType(Type.FCOMPLEX);
  }


  /**
   * Set the return type to float.
   */
  protected void setReturnToFloat() throws AssertionException {
    setReturnType(Type.FLOAT);
  }


  /**
   * Set the return type to integer.
   */
  protected void setReturnToInteger() throws AssertionException {
    setReturnType(Type.INT);
  }


  /**
   * Set the return type to long.
   */
  protected void setReturnToLong() throws AssertionException {
    setReturnType(Type.LONG);
  }


  /**
   * Set the return type to opaque.
   */
  protected void setReturnToOpaque() throws AssertionException {
    setReturnType(Type.OPAQUE);
  }


  /**
   * Set the return type to string.
   */
  protected void setReturnToString() throws AssertionException {
    setReturnType(Type.STRING);
  }


  /**
   * Set the return type to enumeration.
   */
  protected void setReturnToEnum() throws AssertionException {
    setReturnType(Type.ENUM);
  }


  /**
   * Set the return type to class.
   */
  protected void setReturnToClass() throws AssertionException {
    setReturnType(Type.CLASS);
  }


  /**
   * Set the return type to interface.
   */
  protected void setReturnToInterface() throws AssertionException {
    setReturnType(Type.INTERFACE);
  }


  /**
   * Return TRUE if the expression has been marked as having passed the
   * validation checks; otherwise, return FALSE.
   */
  public boolean isValid() {
    return ( (d_return_type != null) && (d_valid) );
  }


  /**
   * Return TRUE if the expression is, or has, a PURE clause; otherwise,
   * return FALSE.
   */
  abstract public boolean hasPure();


  /**
   * Return TRUE if the expression contains RESULT; otherwise, return FALSE.
   */
  abstract public boolean hasResult();


  /**
   * Return TRUE if a result clause or method argument is found within the
   * expression; otherwise, return FALSE.
   */
  abstract public boolean hasResultOrArg();


  /**
   * Return TRUE if a result clause or output argument is found within the
   * expression; otherwise, return FALSE.
   *
   * @param  outOnly  TRUE if only concerned with output arguments that are
   *                  out only; FALSE otherwise.
   */
  abstract public boolean hasResultOrOutArg(boolean outOnly);


  /**
   * Return TRUE if the expression is, or has, at least one method call;
   * otherwise, return FALSE.
   */
  abstract public boolean hasMethodCall();


  /**
   * Return TRUE if the expression is, or has, a call to the specified
   * method; otherwise, return FALSE.  
   */
  abstract public boolean hasMethodCall(String name);


  /**
   * Return the default complexity of the expression.
   */
  abstract public int getDefaultComplexity();


  /**
   * Return TRUE if the expression is, or has, the specified built-in method
   * call; otherwise, return FALSE.
   */
  abstract public boolean hasBuiltinMethod(int type);


  /**
   * Return TRUE if the expression has a method AND the method is any 
   * user-defined method (when any is TRUE) or it is an user-defined 
   * method with a throws clause (if any is FALSE); otherwise, return FALSE.
   */
  abstract public boolean hasUserDefinedMethod(boolean any);


  /**
   * Return TRUE if extendable context is required to validate the expression; 
   * otherwise, return FALSE.
   */
  abstract public boolean requiresExtendableContext();


  /**
   * Return TRUE if method context is required to validate the expression; 
   * otherwise, return FALSE.
   */
  abstract public boolean requiresMethodContext();


  /**
   * Return the prefix for exception messages based on the specified 
   * extendable and method.  
   *
   * @param  ext  The interface or class that owns this expression.
   * @param  m    The method that owns this expression.
   */
  protected String getExceptionPrefix(Extendable ext, Method m) {
    return (ext == null) ? "AssertionExpression" 
                : (m == null) ? ext.getFullName() 
                              : ext.getFullName() + "." + m.getLongMethodName();
  }


  /**
   * Return the prefix for exception messages based on the specified 
   * extendable.
   *
   * @param  ext  The interface or class that owns this expression.
   */
  protected String getExceptionPrefix(Extendable ext) {
    return (ext == null) ? "AssertionExpression" : ext.getFullName();
  }


  /**
   * Validate the expression semantics, if necessary, within the context of the 
   * extendable and/or method.  If the method does not apply for the context,
   * then the argument can be passed in as null.
   *
   * @param   ext  The interface or class that owns this expression.
   * @param   m    The method that owns this expression.
   * @throws  gov.llnl.babel.symbols.AssertionException
   *               The exception that can be raised during the validation.
   */
  abstract protected void validateSemantics(Extendable ext, Method m)
         throws AssertionException;


  /**
   * Validate this assertion expression within the context of the given
   * extendable and optional method.  
   *
   * Assumptions:
   * o  All methods within the extendable have been populated in the symbol
   *    table prior to invocation;
   *
   * Requirements:
   * o This method MUST be invoked and execute successfully before the
   *   expression can be marked as valid.  
   *
   * @param   ext  The interface or class that owns this expression.
   * @param   m    The method that owns this expression.
   * @throws  gov.llnl.babel.symbols.AssertionException
   *               The exception raised if sufficient context is unavailable
   *               or that can be propagated by validations.
   */
  public void validateExpression(Extendable ext, Method m)
       throws AssertionException
  {
    if ( (ext == null) && requiresExtendableContext() ) {
      throw new AssertionException(getExceptionPrefix(ext,m), 
                  "Cannot validate the expression without access to its "
                + "extendable context.");
    }
    if ( (m == null) && requiresMethodContext() ) {
      throw new AssertionException(getExceptionPrefix(ext,m), 
                  "Cannot validate the expression without access to its "
                + "method context.");
    } 
    validateSemantics(ext, m);
    d_valid = true;
  }


  /**
   * Return the list of array iteration macro messages, if any.  Each message
   * is a string where the first character indicates the return type associated
   * with the iteration.  The remaining characters will be the actual macro
   * invocation.
   */
  abstract public ArrayList getArrayIterMacros(String epvVar, int[] startInd);


  /**
   * Returns the number of macros supported by this assertion of the
   * specified type.  Valid types are given in MethodCall.java.
   */
  abstract public int getNumArrayIterMacrosByType(char type);


  /**
   * Return the C version of the expression.
   * 
   * @exception AssertionException this indicates that the expression
   * contained unsupported node types.
   */
  abstract public String cExpression(String epvVar, int[] startInd);

  /**
   * Return <code>true</code> iff the operator is parsed left to right.
   */
  public boolean leftAssociative() { return true; }

  /**
   * Return the stringified version of the expression (in SIDL form).
   */
  abstract public String toString();

  /**
   * Implement the "visitor pattern".
   */
  abstract public Object accept(ExprVisitor ev, Object data);
}
