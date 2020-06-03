//
// File:        MethodCall.java
// Package:     gov.llnl.babel.symbols
// Revision:    @(#) $Id $
// Description: The assertion expression interface for a method call..
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

//
// An unfortunate consequence of this structure is the need to include
// (or worse, assume) the naming scheme for IOR method names.  Obviously 
// this needs more thought for a better dependency situation.
//
import gov.llnl.babel.Context;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.Utilities;
import gov.llnl.babel.symbols.AssertionException;
import gov.llnl.babel.symbols.AssertionExpression;
import gov.llnl.babel.symbols.ExprVisitor;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


public class MethodCall extends AssertionExpression {
  /** Method name attribute.  */
  private String    d_name                  = null;

  /** User-defined method is static */
  private boolean   d_is_static             = true;

  /** User-defined method throws exceptions */
  private boolean   d_throws_exceptions     = false;

  /** Argument list attribute.  */
  private ArrayList d_arguments             = null;

  /** Method type attribute. */
  private int       d_method_type        = 0;

  /** Relation association for methods with relations */
  private int       d_method_relation      = METHOD_RELATION_NONE;

  /** 
   * Relation string that is either "<op> <val>" or "<val> <op>" depending
   * upon whether the array is on the left or right of the binary expression.
   * This information is saved from the validation process for built-in
   * methods that require a relation argument to be passed to the corresponding
   * macro to avoid extra argument processing during code generation.
   */
  private String    d_method_relation_rel  = null;

  /**
   * The basic type information associated with the last array variable
   * detected in the called method's argument list.  It is saved off during
   * the validation process to facilitate code generation.
   */
  private Type      d_builtin_array_type   = null;

  /**
   **********************************************************************
   * Relevant public and private literals.
   */

  /*
   * Relevant public array iteration variables.
   */
  public static final String ARRAY_BOOLEAN_RESULT_VAR = "bres";
  public static final String ARRAY_COUNT_VAR          = "cnt";
  public static final String ARRAY_DOUBLE_RESULT_VAR  = "dres";
  public static final String ARRAY_INTEGER_RESULT_VAR = "ires";
  public static final String ARRAY_ITER_VAR           = "i";
  public static final String ARRAY_SIZE_VAR           = "max";

  /*
   * Valid macro return types and their associated indices.
   */
  public static final char[] MACRO_RETURN_TYPE = { '0', '1', '2', };

  public static final int    MACRO_RETURNS_BOOLEAN_IND = 0;
  public static final int    MACRO_RETURNS_DOUBLE_IND  = 1;
  public static final int    MACRO_RETURNS_INTEGER_IND = 2;

  public static final int    MAX_VALID_MACRO_RETURNS   = 3;

  /*
   * Relevant public array iteration variables.
   */

  /*
   * ...To facilitate checking on built-in methods...
   */
  public static final int  METHOD_ANY_ARRAY        = -3;
  public static final int  METHOD_NUMERIC_ARRAY    = -2;
  public static final int  METHOD_ANY_BUILTIN      = -1;

  /*
   * ...Actual built-in method attribute types...
   */
  public static final int  METHOD_IS_USER_DEFINED  = 0;
  public static final int  METHOD_ARRAY_ALL        = 1;
  public static final int  METHOD_ARRAY_ANY        = 2;
  public static final int  METHOD_ARRAY_COUNT      = 3;
  public static final int  METHOD_ARRAY_DIMEN      = 4;
  public static final int  METHOD_ARRAY_IRANGE     = 5;
  public static final int  METHOD_ARRAY_LOWER      = 6;
  public static final int  METHOD_ARRAY_MAX        = 7;
  public static final int  METHOD_ARRAY_MIN        = 8;
  public static final int  METHOD_ARRAY_NEAR_EQUAL = 9;
  public static final int  METHOD_ARRAY_NON_DECR   = 10;
  public static final int  METHOD_ARRAY_NON_INCR   = 11;
  public static final int  METHOD_ARRAY_NONE       = 12;
  public static final int  METHOD_ARRAY_RANGE      = 13;
  public static final int  METHOD_ARRAY_SIZE       = 14;
  public static final int  METHOD_ARRAY_STRIDE     = 15;
  public static final int  METHOD_ARRAY_SUM        = 16;
  public static final int  METHOD_ARRAY_UPPER      = 17;
  public static final int  METHOD_IRANGE           = 18;
  public static final int  METHOD_NEAR_EQUAL       = 19;
  public static final int  METHOD_RANGE            = 20;

  public static final int  MINIMUM_METHOD          = 1;
  public static final int  MAXIMUM_ARRAY_METHOD    = 17;
  public static final int  MAXIMUM_METHOD          = 20;

  private static final String s_builtin_name[] = {
    "",
    "all",
    "any",
    "count",
    "dimen",
    "irange",
    "lower",
    "max",
    "min",
    "nearequal",
    "nondecr",
    "nonincr",
    "none",
    "range",
    "size",
    "stride",
    "sum",
    "upper",
    "irange",
    "nearequal",
    "range",
  };

  private static final String s_builtin_macro[] = {
    "",
    "SIDL_ARRAY_ALL",
    "SIDL_ARRAY_ANY",
    "SIDL_ARRAY_COUNT",
    "SIDL_ARRAY_DIMEN",
    "SIDL_ARRAY_IRANGE",
    "SIDL_ARRAY_LOWER",
    "SIDL_ARRAY_MAX",
    "SIDL_ARRAY_MIN",
    "SIDL_ARRAY_NEAR_EQUAL",
    "SIDL_ARRAY_NON_DECR",
    "SIDL_ARRAY_NON_INCR",
    "SIDL_ARRAY_NONE",
    "SIDL_ARRAY_RANGE",
    "SIDL_ARRAY_SIZE",
    "SIDL_ARRAY_STRIDE",
    "SIDL_ARRAY_SUM",
    "SIDL_ARRAY_UPPER",
    "SIDL_IRANGE",
    "SIDL_NEAR_EQUAL",
    "SIDL_RANGE",
  };

  private static final int MAX_ARRAY_ARGS = 2;

  public static final int  METHOD_RELATION_NONE  = 0;
  public static final int  METHOD_RELATION_VALUE_LEFT  = 1;
  public static final int  METHOD_RELATION_VALUE_RIGHT = 2;
  public static final int  METHOD_RELATION_BOTH  = 3;

  private static final String s_builtin_relation[] = {
    "",
    "_VL",
    "_VR",
    "_BOTH",
  };

  /**
   * WARNING: The following must correspond to the appropriate
   * complete class names in the symbols directory.
   */
  private static final String IDENTIFIER_LITERAL =
             "gov.llnl.babel.symbols.IdentifierLiteral";
  private static final String BINARY_EXPRESSION  =
             "gov.llnl.babel.symbols.BinaryExpression";

  /**
   * WARNING: The following relation operators must 
   * correspond to those found in sidlArray.h.
   */
  public static final int RELATION_OP_EQUAL         = 0;
  public static final int RELATION_OP_NOT_EQUAL     = 1;
  public static final int RELATION_OP_LESS_THAN     = 2;
  public static final int RELATION_OP_LESS_EQUAL    = 3;
  public static final int RELATION_OP_GREATER_THAN  = 4;
  public static final int RELATION_OP_GREATER_EQUAL = 5;


  /**
   * Create a new object.
   *
   * @param   name    The name of the method that is to be called.
   * @throws  gov.llnl.babel.symbols.AssertionException
   *                  The exception raised if error during any validation.
   */
  public MethodCall(String name,
                    Context context)
    throws AssertionException 
  {
    super(false, context);
    if ( (name == null) || (name.equals("")) ) {
      throw new AssertionException("Cannot instantiate a method call without "
                + "the name of the method to be called.");
    }
    for (int i=MINIMUM_METHOD; (i<=MAXIMUM_METHOD) && (d_method_type==0); 
         i++) {
      if (name.toLowerCase().equals(s_builtin_name[i])) {
        d_method_type = i;
      }
    }
    d_name      = name;
    d_arguments = new ArrayList();
  }


  /**
   * Return the name of the method call.
   */
  public String getMethodName() {
    return d_name;
  }


  /**
   * Return TRUE if the user-defined method is static; otherwise, return
   * FALSE.
   *
   * WARNING:  This is not known until _after_ the method has been validated!
   */
  public boolean isStatic() {
    return d_is_static;
  }


  /**
   * Return TRUE if the user-defined method throws exceptions; otherwise, 
   * return FALSE.
   *
   * WARNING:  This is not known until _after_ the method has been validated!
   */
  public boolean throwsExceptions() {
    return d_throws_exceptions;
  }


  /**
   * Add the specified expression as an argument to the method call.
   *
   * @param  arg  An assertion expression as an argument to the method call.
   */
  public void addArgument(AssertionExpression arg) {
    d_arguments.add(arg);
  }


  /**
   * Return an <code>ArrayList</code> of argument expressions with each
   * element as a <code>AssertionExpression</code>.
   */
  public ArrayList getArguments() {
    return d_arguments;
  }


  /**
   * Return TRUE if the method is one of the special array ones; otherwise,
   * return FALSE.
   */
  public boolean isArrayMethod() {
    boolean is = false;
    if (  (MINIMUM_METHOD  <= d_method_type     ) 
       && (d_method_type <= MAXIMUM_ARRAY_METHOD) ) {
       is = true;
    }
    return is; 
  }


  /**
   * Return TRUE if the method is one of the special array macro ones; 
   * otherwise, return FALSE.
   */
  public boolean isArrayMacroMethod() {
    boolean is = false;
    if (  (d_method_type == METHOD_ARRAY_DIMEN) 
       || (d_method_type == METHOD_ARRAY_LOWER)
       || (d_method_type == METHOD_ARRAY_SIZE)
       || (d_method_type == METHOD_ARRAY_STRIDE)
       || (d_method_type == METHOD_ARRAY_UPPER) ) {
       is = true;
    }
    return is; 
  }


  /**
   * Return TRUE if the method is one of the special built-in ones whose
   * array argument(s) must contain numeric values only.
   */
  public boolean isBuiltinNumericArrayMethod() {
    boolean is = false;
    if (  isArrayMethod() && (!isArrayMacroMethod()) ) {
       is = true;
    }
    return is; 
  }


  /**
   * Return TRUE if the method is one of the special built-in ones whose
   * only argument should be a relation; otherwise, return FALSE.
   */
  public boolean isBuiltinRelationMethod() {
    boolean is = false;
    if (  (d_method_type == METHOD_ARRAY_ALL) 
       || (d_method_type == METHOD_ARRAY_ANY)
       || (d_method_type == METHOD_ARRAY_COUNT)
       || (d_method_type == METHOD_ARRAY_NONE) ) {
       is = true;
    }
    return is; 
  }


  /**
   * Return TRUE if the expression is, or has, PURE clause; otherwise,
   * return FALSE.  In this case, a PURE clause that shows up within the 
   * method call would be a semantic error so FALSE is always returned.
   */
  public boolean hasPure() {
    return false;
  }


  /**
   * Return TRUE if the expression contains RESULT; otherwise, return FALSE.
   */
  public boolean hasResult() {
    boolean   has  = false;
    ArrayList args = d_arguments;

    if ( (args != null) && (!args.isEmpty()) ) {
      ListIterator i = args.listIterator();

      while (i.hasNext() && !has) {
        AssertionExpression arg = (AssertionExpression)i.next();
        if (arg.hasResult()) {
          has = true;
        }
      }
    }

    return has;
  }


  /**
   * Return TRUE if a result clause or method argument is found within the
   * expression; otherwise, return FALSE.
   */
  public boolean hasResultOrArg() {
    boolean   has = false;
    ArrayList args = d_arguments;

    if ( (args != null) && (!args.isEmpty()) ) {
      ListIterator i = args.listIterator();

      while (i.hasNext() && !has) {
        AssertionExpression arg = (AssertionExpression)i.next();
        if (arg.hasResultOrArg()) {
          has = true;
        }
      }
    }

    return has;
  }


  /**
   * Return TRUE if a result clause or output argument is found within the
   * expression; otherwise, return FALSE.
   *
   * @param  outOnly  TRUE if only concerned with output arguments that are
   *                  out only; FALSE otherwise.
   */
  public boolean hasResultOrOutArg(boolean outOnly) {
    boolean   has  = false;
    ArrayList args = d_arguments;

    if ( (args != null) && (!args.isEmpty()) ) {
      ListIterator i = args.listIterator();

      while (i.hasNext() && !has) {
        AssertionExpression arg = (AssertionExpression)i.next();
        if (arg.hasResultOrOutArg(outOnly)) {
          has = true;
        }
      }
    }

    return has;
  }


  /**
   * Return TRUE if the expression is, or has, at least one method call;
   * otherwise, return FALSE.  In this case, this expression is a method
   * call so it always returns TRUE.  Note that this method assumes there
   * is an associated method that is valid and is being invoked correctly.
   */
  public boolean hasMethodCall() {
    return true;
  }


  /**
   * Return TRUE if the expression is, or has, a call to the specified
   * method; otherwise, return FALSE.  
   */
  public boolean hasMethodCall(String name) {
    return (d_name != null) ? d_name.equals(name) : false;
  }


  /**
   * Return the default complexity of the expression (0 = constant, 1 = linear,
   * etc.).
   */
  public int getDefaultComplexity() {
    /*
     * ToDo...Need to revisit this once distinguish between one- and multi-
     * dimensional arrays.
     */
    return isBuiltinNumericArrayMethod() ? 1 : 0;
  }


  /**
   * Return TRUE if the expression is, or has, the specified built-in method; 
   * otherwise, return FALSE.  To facilitate checking, the following options
   * for type can also be used to check if the method is one of many:
   * 
   *   METHOD_ANY_BUILTIN       TRUE for any built-in method
   *   METHOD_NUMERIC_ARRAY     TRUE for any built-in, numeric array method
   *   METHOD_ANY_ARRAY         TRUE for any built-in array method
   */
  public boolean hasBuiltinMethod(int type) {
    boolean hasIt = false;
    switch (type) {
      case METHOD_ANY_BUILTIN:
        if ( (MINIMUM_METHOD <= type) && (type <= MAXIMUM_METHOD) ) {
          hasIt = true;
        }
        break;
      case METHOD_NUMERIC_ARRAY:
        if (isBuiltinNumericArrayMethod()) {
          hasIt = true;
        }
        break;
      case METHOD_ANY_ARRAY:
        if (isArrayMethod()) {
          hasIt = true;
        }
        break;
      default:
        if (  ( (MINIMUM_METHOD <= type) && (type <= MAXIMUM_METHOD) )
           && (type == d_method_type) ) {
          hasIt = true;
        }
        break;
    }
    return hasIt;
  }


  /**
   * Return TRUE if the expression has a method AND the method is any
   * user-defined method (when any is TRUE) or it is an user-defined
   * method with a throws clause (if any is FALSE); otherwise, return FALSE.
   */
  public boolean hasUserDefinedMethod(boolean any) {
    boolean hasIt = false;
    if (  (d_method_type == METHOD_IS_USER_DEFINED) 
       && ( any || d_throws_exceptions ) ) {
      hasIt = true;
    }
    return hasIt;
  }


  /**
   * Return TRUE if extendable context is required to validate the expression; 
   * otherwise, return FALSE.
   */
  public boolean requiresExtendableContext() {
    return true;
  }


  /**
   * Return TRUE if method context is required to validate the expression; 
   * otherwise, return FALSE.  This is separate from and in addition to the
   * extendable context.
   */
  public boolean requiresMethodContext() {
    boolean   rqr  = false;
    ArrayList args = d_arguments;
    if ( (args != null) && (!args.isEmpty()) ) {
      for (ListIterator iter = args.listIterator(); iter.hasNext(); ) {
        AssertionExpression arg  = (AssertionExpression) iter.next();
        if (arg.requiresMethodContext()) {
          rqr = true;
        }
      }
    }
    return rqr;
  }


  /**
   * Return a list of argument return type values (Integer) that correspond to 
   * the Type class equivalents of the argument expressions.  Each element of
   * the <code>ArrayList</code> is an <code>Integer</code>.
   *
   * @throws  gov.llnl.babel.symbols.AssertionException
   *            The exception is raised if there is a problem with any of the
   *            argument return types (e.g., return type not yet determined).
   */
  private ArrayList getArgumentReturnTypes() throws AssertionException {
    ArrayList list = null;
    ArrayList args = d_arguments;
    if ( (args != null) && (!args.isEmpty()) ) {
      list = new ArrayList();
      for (ListIterator iter = args.listIterator(); iter.hasNext(); ) {
        AssertionExpression arg  = (AssertionExpression) iter.next();
        int                 type = arg.getReturnTypeValue();
        if (type != -1) {
          list.add(new Integer(type));
        } else {
          throw new AssertionException("Unable to obtain the return type for "
                    + "argument " + arg + " when attempting to build a list of "
                    + "argument return types for method " + d_name + ".");
        }
      }
    }
    return list;
  }


  /**
   * Return a list of argument return type values (Integer) that correspond to 
   * the Type class equivalents of the arguments associated with the specified
   * method.  Each element of the <code>ArrayList</code> is an 
   * <code>Integer</code>.
   */
  public ArrayList getArgumentReturnTypes(Method meth) {
    ArrayList list  = null;
    if (meth != null) {
      List args = meth.getArgumentList();

      if ( (args != null) && (!args.isEmpty()) ) {
        list = new ArrayList();
        for (ListIterator iter = args.listIterator(); iter.hasNext(); ) {
          Argument arg  = (Argument) iter.next();
          int      type = arg.getType().getBasicType();
          list.add(new Integer(type));
        }
      }
    }
    return list;
  }


  /**
   * Return TRUE if the specified argument type list is compatible with 
   * the arguments in this call; otherwise, return FALSE.
   *
   * @throws  gov.llnl.babel.symbols.AssertionException
   *            The exception is raised if there is a problem with any of the
   *            argument return types (e.g., return type not yet determined).
   */
  public boolean hasCompatibleArgumentTypes(ArrayList list) 
       throws AssertionException
  {
    boolean   compatible = false;
    ArrayList myargs     = getArgumentReturnTypes();
    int       mysize     = (myargs == null) ? 0 : myargs.size();
    int       listsize   = (list == null) ? 0 : list.size();
    if (mysize != listsize) {
      throw new AssertionException(ERROR_SEMANTIC_VALIDATION + "Number of "
                + " arguments in call to " + d_name + " is " + mysize
                + " while definition's size is " + listsize + ".");
    } else if (mysize > 0) {
      int a = 0;
      for (ListIterator iter = list.listIterator(); iter.hasNext(); a++) {
        int itemvalue = ((Integer) iter.next()).intValue();
        int argvalue  = ((Integer) myargs.get(a)).intValue();
        /*
         * ToDo...Considering "fixing" this once we can parse an integer!
         */
        if (argvalue == itemvalue) {
          compatible = true;
        } else if (  ((argvalue == Type.LONG)   && (itemvalue == Type.INT))
                  || ((argvalue == Type.FLOAT)  && (itemvalue == Type.INT))
                  || ((argvalue == Type.DOUBLE) && (itemvalue == Type.INT))
                  || ((argvalue == Type.INT)    && (itemvalue == Type.LONG))
                  || ((argvalue == Type.FLOAT)  && (itemvalue == Type.LONG))
                  || ((argvalue == Type.DOUBLE) && (itemvalue == Type.LONG)) ) {
          compatible = true;
        } else if (  ((argvalue == Type.DOUBLE) && (itemvalue == Type.FLOAT))
                  || ((argvalue == Type.FLOAT) && (itemvalue == Type.DOUBLE)) ){
          compatible = true;
        } else if ( ((argvalue==Type.DCOMPLEX) && (itemvalue==Type.FCOMPLEX))
                  ||((argvalue==Type.FCOMPLEX) && (itemvalue==Type.DCOMPLEX))) {
          compatible = true;
        } else {
          throw new AssertionException(ERROR_SEMANTIC_VALIDATION
                + "Incompatible argument type " + Type.getTypeName(argvalue)
                + " in call to " + d_name + ", expecting " 
                + Type.getTypeName(itemvalue) + ".");
        }
      } 
    } else {
      compatible = true;
    }
    return compatible;
  }


  /**
   * Return the method of the specified extendable that corresponds to this 
   * method call, if there is one; otherwise, return null.  For the purposes
   * of this method, it doesn't matter if the specification was written with
   * the long or short method name.
   *
   * @throws  gov.llnl.babel.symbols.AssertionException
   *            The exception that can be propagated if problems are detected
   *            with the arguments.
   */
  private Method getMethod(Extendable ext) throws AssertionException {
    Method meth = null;

    if (d_method_type == 0) {
      /*
       * Try to see if extendable has this method based on the long
       * names of it's methods.
       */
      Method m = ext.lookupMethodByLongName(d_name, true);
      
      if (m != null) {
         if (hasCompatibleArgumentTypes(getArgumentReturnTypes(m))) {
           meth = m;
         } else {
            throw new AssertionException("Method " + d_name + " was located in "
                   + getExceptionPrefix(ext)
                   + " but the arguments were determined to be incompatible.");
         }
      } else {
         throw new AssertionException("Cannot locate method associated with "
                + " the assertion invocation of " + d_name + " since the method"
                + " was not found in " + getExceptionPrefix(ext) + ".");
      }
    } else {
      throw new AssertionException("Will not attempt to look up method " 
                + d_name + " in " + getExceptionPrefix(ext) 
                + " since the method has been "
                + "marked as one of the built-in assertion methods.");
    }
    return meth;
  }


  /**
   * Return the invalid built-in usage exception message.
   */
  private String getInvalidBuiltinMsg(Extendable ext) {
     return ERROR_SEMANTIC_VALIDATION + "Invalid call to " + d_name + "() in " 
            + getExceptionPrefix(ext) + " since signature is incompatible with "
            + "that of the built-in " + s_builtin_name[d_method_type] 
            + " method.";
  }


  /**
   * Validate the expression semantics, if necessary, within the context of the 
   * extendable and optional method.  
   *
   * WARNING: Changes to the validations, such as number or type of arguments,
   * must also be reflected in the method that generates the code.
   *
   * @param   ext  The interface or class that owns this expression.
   * @param   m    The method that owns this expression.
   * @throws  gov.llnl.babel.symbols.AssertionException
   *               The exception that can be raised during the validation.
   */
  protected void validateSemantics(Extendable ext, Method m)
        throws AssertionException
  {
    if (ext == null) {
      throw new AssertionException("Cannot validate the semantics of the method"
                + " call " + d_name + " without the context of the "
                + "extendable to which it belongs.");
    } 

    /*
     * Before proceeding, must make sure the argument expression(s) are 
     * valid and, if possible, have their return types set.  (If these 
     * types are not set, then we're in trouble at this point anyway!)
     */
    BinaryExpression  beArg     = null;
    IdentifierLiteral idArg     = null;
    int               numArrays = 0;
    boolean           hasArray  = false;
    if ( (d_arguments != null) && (!d_arguments.isEmpty()) ) {
      for (ListIterator iter = d_arguments.listIterator(); iter.hasNext(); ) {
        AssertionExpression arg  = (AssertionExpression) iter.next();
        /* 
         * Since we do not know apriori the type associated with a NULL argument,
         * cannot readily determine if the method is available for use in an
         * assertion.  Hence, must disallow it.
         */
        if (arg.getClass().getName().equals(IDENTIFIER_LITERAL)) {
          idArg = (IdentifierLiteral)arg;
          if (idArg.isNull()) {
            throw new AssertionException(ERROR_SEMANTIC_VALIDATION + "Do NOT "
                      + "support passing NULL as an argument to an assertion "
                      + "method call since no mechanism in place to resolve "
                      + "such a reference.  See call to method " + d_name 
                      + " in " + getExceptionPrefix(ext) + ".");
          } 
          arg.validateExpression(ext, m);
          if (isBuiltinNumericArrayMethod() && idArg.returnIsArray()) {
            if (idArg.returnIsNumericArray()) {
              d_builtin_array_type = getReturnType();
              numArrays++;
              if (numArrays > MAX_ARRAY_ARGS) {
                throw new AssertionException(ERROR_SEMANTIC_VALIDATION 
                          + "Do not currently support more than " 
                          + MAX_ARRAY_ARGS 
                          + " array arguments at a time in built-in assertion "
                          + "methods.  Attempt to exceed this limit was "
                          + "detected in a call to " + d_name + " in "
                          + getExceptionPrefix(ext) + ".");
              }
            } else {
              throw new AssertionException(ERROR_SEMANTIC_VALIDATION 
                        + "Arrays passed to built-in method " + d_name 
                        + " must contain numeric data.  See call in "
                        + getExceptionPrefix(ext) + ".");
            }
          }
        } else if (  isBuiltinRelationMethod()
                  && arg.getClass().getName().equals(BINARY_EXPRESSION)) {
          /*
           * We have a built-in method that expects a single relation argument
           * whose left- or right-hand side is an array.  So ensure that
           * the argument knows that this is expected _before_ attempting
           * to validate the argument's expression.
           */
          beArg = (BinaryExpression)arg;
          beArg.setArrayRelationRequired(true);
          beArg.validateExpression(ext, m);

          if (beArg.arrayOnLeft() && beArg.arrayOnRight()) {
            d_method_relation = METHOD_RELATION_BOTH;
          } else if (beArg.arrayOnLeft()) {
            d_method_relation = METHOD_RELATION_VALUE_RIGHT;
          } else {
            d_method_relation = METHOD_RELATION_VALUE_LEFT;
          }
          d_method_relation_rel = beArg.getArrayRelation();
        } else {
          arg.validateExpression(ext, m);
        }
        if (arg.returnIsArray()) {
          hasArray = true;
        }
      }
      if (!hasArray) {
         if (d_method_type == METHOD_ARRAY_IRANGE) {
           d_method_type = METHOD_IRANGE;
         } else if (d_method_type == METHOD_ARRAY_NEAR_EQUAL) {
           d_method_type = METHOD_NEAR_EQUAL;
         } else if (d_method_type == METHOD_ARRAY_RANGE) {
           d_method_type = METHOD_RANGE;
         }
      }
    }

    /*
     * Now we can perform method-specific validations based on the simple
     * return types of each argument expression.
     *
     * ...First ensure it's not trying to call itself. (Is this right?)
     */
    if (m != null) {
      if (  m.getLongMethodName().equals(d_name) 
         && hasCompatibleArgumentTypes(getArgumentReturnTypes(m)) ) {
          throw new AssertionException(ERROR_SEMANTIC_VALIDATION + "Method " 
                    + d_name + " cannot call itself in an assertion clause.  "
                    + "Did you mean to use the return keyword in " 
                    + getExceptionPrefix(ext) + " instead?");
      }
    }

    /*
     * ...Now ensure it's an available method.
     */
    ArrayList rList = null;
    switch (d_method_type) {
      case METHOD_ARRAY_ALL: 
      case METHOD_ARRAY_ANY: 
      case METHOD_ARRAY_NONE: 
        rList = new ArrayList();
        rList.add(new Integer(Type.BOOLEAN));	/* relation */
        if (!hasCompatibleArgumentTypes(rList)) {
          throw new AssertionException(getInvalidBuiltinMsg(ext));
        }
        setReturnType(Type.BOOLEAN);
        break;
      case METHOD_ARRAY_COUNT: 
        rList = new ArrayList();
        rList.add(new Integer(Type.BOOLEAN));	/* relation */
        if (!hasCompatibleArgumentTypes(rList)) {
          throw new AssertionException(getInvalidBuiltinMsg(ext));
        }
        setReturnType(Type.INT);
        break;
      case METHOD_ARRAY_IRANGE: 
        rList = new ArrayList();
        rList.add(new Integer(Type.ARRAY)); /* array */
        rList.add(new Integer(Type.LONG));  /* min comparison value */
        rList.add(new Integer(Type.LONG));  /* max comparison value */
        if (!hasCompatibleArgumentTypes(rList)) {
          throw new AssertionException(getInvalidBuiltinMsg(ext));
        }
        setReturnType(Type.BOOLEAN);
        break;
      case METHOD_ARRAY_MAX: 
      case METHOD_ARRAY_MIN: 
      case METHOD_ARRAY_SUM: 
        rList = new ArrayList();
        rList.add(new Integer(Type.ARRAY));    /* array var */
        if (!hasCompatibleArgumentTypes(rList)) {
          throw new AssertionException(getInvalidBuiltinMsg(ext));
        }
        setReturnType(Type.DOUBLE);
        break;
      case METHOD_ARRAY_NEAR_EQUAL: 
        /*
         * ToDo...Will need a different built-in to support complex
         */
        rList = new ArrayList();
        rList.add(new Integer(Type.ARRAY));   /* array #1 */
        rList.add(new Integer(Type.ARRAY));   /* array #2 */
        rList.add(new Integer(Type.DOUBLE));  /* tolerance */
        if (!hasCompatibleArgumentTypes(rList)) {
          throw new AssertionException(getInvalidBuiltinMsg(ext));
        }
        setReturnType(Type.BOOLEAN);
        break;
      case METHOD_ARRAY_RANGE: 
        /*
         * ToDo...Will need a different built-in to support complex
         */
        rList = new ArrayList();
        rList.add(new Integer(Type.ARRAY));   /* array */
        rList.add(new Integer(Type.DOUBLE));  /* min comparison value */
        rList.add(new Integer(Type.DOUBLE));  /* max comparison value */
        rList.add(new Integer(Type.DOUBLE));  /* tolerance */
        if (!hasCompatibleArgumentTypes(rList)) {
          throw new AssertionException(getInvalidBuiltinMsg(ext));
        }
        setReturnType(Type.BOOLEAN);
        break;
      case METHOD_ARRAY_DIMEN: 
      case METHOD_ARRAY_SIZE: 
        rList = new ArrayList();
        rList.add(new Integer(Type.ARRAY));    /* array var */
        if (!hasCompatibleArgumentTypes(rList)) {
          throw new AssertionException(getInvalidBuiltinMsg(ext));
        }
        setReturnType(Type.INT);
        break;
      case METHOD_ARRAY_NON_DECR: 
      case METHOD_ARRAY_NON_INCR: 
        rList = new ArrayList();
        rList.add(new Integer(Type.ARRAY));    /* array var */
        if (!hasCompatibleArgumentTypes(rList)) {
          throw new AssertionException(getInvalidBuiltinMsg(ext));
        }
        setReturnType(Type.BOOLEAN);
        break;
      case METHOD_ARRAY_LOWER: 
      case METHOD_ARRAY_UPPER: 
      case METHOD_ARRAY_STRIDE: 
        rList = new ArrayList();
        rList.add(new Integer(Type.ARRAY));    /* array var */
        rList.add(new Integer(Type.INT));      /* dimension var */
        if (!hasCompatibleArgumentTypes(rList)) {
          throw new AssertionException(getInvalidBuiltinMsg(ext));
        }
        setReturnType(Type.INT);
        break;
      case METHOD_IRANGE: 
        rList = new ArrayList();
        rList.add(new Integer(Type.LONG));   /* value */
        rList.add(new Integer(Type.LONG));   /* min comparison value */
        rList.add(new Integer(Type.LONG));   /* max comparison value */
        if (!hasCompatibleArgumentTypes(rList)) {
          throw new AssertionException(getInvalidBuiltinMsg(ext));
        }
        setReturnType(Type.BOOLEAN);
        break;
      case METHOD_NEAR_EQUAL: 
        /*
         * ToDo...Will need a different built-in to support complex
         */
        rList = new ArrayList();
        rList.add(new Integer(Type.DOUBLE));   /* value */
        rList.add(new Integer(Type.DOUBLE));   /* comparison value */
        rList.add(new Integer(Type.DOUBLE));   /* tolerance */
        if (!hasCompatibleArgumentTypes(rList)) {
          throw new AssertionException(getInvalidBuiltinMsg(ext));
        }
        setReturnType(Type.BOOLEAN);
        break;
      case METHOD_RANGE: 
        /*
         * ToDo...Will need a different built-in to support complex
         */
        rList = new ArrayList();
        rList.add(new Integer(Type.DOUBLE));   /* value */
        rList.add(new Integer(Type.DOUBLE));   /* min comparison value */
        rList.add(new Integer(Type.DOUBLE));   /* max comparison value */
        rList.add(new Integer(Type.DOUBLE));   /* tolerance */
        if (!hasCompatibleArgumentTypes(rList)) {
          throw new AssertionException(getInvalidBuiltinMsg(ext));
        }
        setReturnType(Type.BOOLEAN);
        break;
      default:
        Method meth = getMethod(ext);
        if (meth == null) {
          throw new AssertionException(ERROR_SEMANTIC_VALIDATION 
                    + "Invalid method call to " + d_name 
                    + ".  Cannot locate compatible method in " 
                    + getExceptionPrefix(ext));
        } else if (!meth.hasPureAssertion()) {
          throw new AssertionException(ERROR_SEMANTIC_VALIDATION 
                    + "Invalid method call to " + d_name 
                    + " in " + getExceptionPrefix(ext) 
                    + ".  The method must have a PURE clause to be callable in "
                    + "an assertion clause.");
        } else if (!meth.isStatic()) {
          if ( (m!=null) && m.isStatic() ) {
            throw new AssertionException(ERROR_SEMANTIC_VALIDATION 
                      + "Invalid method call to " + d_name 
                      + " in " + getExceptionPrefix(ext) 
                      + ".  Cannot invoke non-static method, " 
                      + meth.getLongMethodName() 
                      + " from within static method " 
                      + m.getLongMethodName() + ".");
          }
        }

        /*
         * If we get here then this expression represents a valid method so
         * we should be able to set the return type.
         */
        setReturnType(meth.getReturnType());
        d_throws_exceptions = (meth.getThrows().size() > 0);
        d_is_static         = meth.isStatic();
        break;
    } 
  }


  /**
   * Return the stringified version of the expression (in SIDL form).
   */
  public String toString() {
    StringBuffer call = new StringBuffer();
    call.append(d_name + "(");
    for (ListIterator iter = d_arguments.listIterator();
         iter.hasNext(); ) {
      AssertionExpression arg = (AssertionExpression) iter.next();
      call.append(arg);
      if (iter.hasNext()) {
        call.append(", ");
      }
    }
    call.append(")");

    return hasParens() ?  "(" + call.toString() + ")"  :  call.toString();
  }

  /**
   * Return the sidl type of the array type associated with the specified
   * array return type.
   */
  private String getArrayTypeStr(Type type) {
    String typeBase = type.isGenericArray() ? "" : type.getArrayTypeName();
    if (typeBase.equalsIgnoreCase(Type.s_names[Type.ENUM])) {
      typeBase = Type.s_names[Type.INT];
    }
    return "sidl_" + typeBase + "__array";
  }


  /**
   * Return the array variable arguments for built-in array macros 
   * based on what is expected to be a binary expression; otherwise, 
   * returns erroneous argument(s) in the form of comments.
   *
   * ToDo:  Should be throwing an exception if there is a problem
   * with the expression.
   */
  private String getArrayVars(AssertionExpression ae) {
    StringBuffer argList  = new StringBuffer();

    if (ae.getClass().getName().equals(BINARY_EXPRESSION)) {
      BinaryExpression be = (BinaryExpression) ae;
      boolean haveLeft = false;
      if (be.arrayOnLeft()) {
        argList.append(getArrayTypeStr(be.getLeftExpression().getReturnType()));
        argList.append(", ");
        argList.append(be.getArrayRelationVariable(true));
        haveLeft = true;
      }
      if (be.arrayOnRight()) {
        if (haveLeft) {
          argList.append(", ");
        }
        argList.append(getArrayTypeStr(be.getRightExpression()
                                         .getReturnType()));
        argList.append(", ");
        argList.append(be.getArrayRelationVariable(false));
      }
    } else {
      argList.append("/* ERROR:  Expecting binary expression arg. */");
    }

    return argList.toString();
  }


  /**
   * Return the C version of the argument list for a call to this method.
   * Note that the self and exception arguments are attached as needed.
   *
   * WARNING:  This should NEVER be invoked until after validations have
   * been performed.
   *
   * ToDo:  Should be throwing an exception if more than one argument
   * to built-in numeric array methods.
   */
  private String getCArgList(String epvVar, int[] startInd, 
                             boolean addArrayType, boolean addRelation, 
                             boolean addArrayIterVars) 
  {
    StringBuffer argList  = new StringBuffer();

    if (isBuiltinNumericArrayMethod()) {
      if (d_arguments.size() == 1) {
        AssertionExpression ae = (AssertionExpression) d_arguments.get(0);
        argList.append(getArrayVars(ae));
      } else {
        argList.append("/* ERROR:  Too many arguments to built-in */");
      }
    } else {
      if (!d_is_static) {
        argList.append(Utilities.s_self);
        if ( (d_arguments.size() > 0) || d_throws_exceptions) {
          argList.append(", ");
        }
      }

      for (ListIterator iter = d_arguments.listIterator(); iter.hasNext(); ) {
        AssertionExpression arg = (AssertionExpression) iter.next();
        if (arg.returnIsArray()) {
          if (addArrayType) {
            argList.append(getArrayTypeStr(arg.getReturnType()) + ", ");
          }
          argList.append(arg.cExpression(epvVar, startInd));
        } else {
          argList.append(arg.cExpression(epvVar, startInd));
        } 
        if (iter.hasNext()) {
          argList.append(", ");
        }
      }
      if (d_throws_exceptions) {
        if  (d_arguments.size() > 0) {
          argList.append(", ");
        }
        argList.append(Utilities.s_exception);
      }
    }

    /*
     * TBD Are there any cases where these apply outside numeric built-ins? 
     */
    if (addRelation) {
      argList.append(", " + d_method_relation_rel);
    }
    if (addArrayIterVars) {
      argList.append(", " + ARRAY_ITER_VAR + ", " + ARRAY_SIZE_VAR);
    }

    return argList.toString();
  }


  /**
   * Return the list of array iteration macros, if any.
   *
   * WARNING: Changes to the code generated below must match the validations
   * in the method that validates the semantics of the call.
   *
   * ASSUMPTION:  None of the standard arguments to these macros has a macro 
   * return type associated with it.  That is, only the result that is 
   * appended to the macros has the relevant index!
   */
  public ArrayList getArrayIterMacros(String epvVar, int[] startInd) {
    ArrayList    list = null;
    StringBuffer call = new StringBuffer();
    
    switch (d_method_type) {
      case METHOD_ARRAY_ALL: 
      case METHOD_ARRAY_NONE: 
        call.append(s_builtin_macro[d_method_type]);
        call.append(s_builtin_relation[d_method_relation] + "(");
        call.append(getCArgList(epvVar, null, true, true, true));
        call.append(", " + ARRAY_COUNT_VAR);
        call.append(", " + ARRAY_BOOLEAN_RESULT_VAR + "[" 
            + startInd[MACRO_RETURNS_BOOLEAN_IND] + "])");
        break;
      case METHOD_ARRAY_ANY: 
        call.append(s_builtin_macro[d_method_type]);
        call.append(s_builtin_relation[d_method_relation] + "(");
        call.append(getCArgList(epvVar, null, true, true, true));
        call.append(", " + ARRAY_BOOLEAN_RESULT_VAR + "[" 
            + startInd[MACRO_RETURNS_BOOLEAN_IND] + "])");
        break;
      case METHOD_ARRAY_COUNT: 
        call.append(s_builtin_macro[d_method_type]);
        call.append(s_builtin_relation[d_method_relation] + "(");
        call.append(getCArgList(epvVar, null, true, true, true));
        call.append(", " + ARRAY_INTEGER_RESULT_VAR + "[" 
            + startInd[MACRO_RETURNS_INTEGER_IND] + "])");
        break;
      case METHOD_ARRAY_NON_DECR: 
      case METHOD_ARRAY_NON_INCR: 
        call.append(s_builtin_macro[d_method_type] + "(");
        call.append(getCArgList(epvVar, null, true, false, true));
/*
 * ToDo...Originally just this...but wasn't factoring in properly.
 *        Is this still preferrable?
 *
        call.append(", " + ARRAY_DOUBLE_RESULT_VAR);
 */
        call.append(", " + ARRAY_DOUBLE_RESULT_VAR + "["
            + startInd[MACRO_RETURNS_DOUBLE_IND] + "] ");
        call.append(", " + ARRAY_BOOLEAN_RESULT_VAR + "[" 
            + startInd[MACRO_RETURNS_BOOLEAN_IND] + "])");
        break;
      case METHOD_ARRAY_MAX: 
      case METHOD_ARRAY_MIN: 
        /*
         * ToDo...Need to figure out if better to pass yet another
         * argument for the specific result type argument or if it's
         * better to leave as-is (i.e., as a double).  Of course,
         * this won't work with complex results!
         */
        call.append(s_builtin_macro[d_method_type] + "(");
        call.append(getCArgList(epvVar, null, true, false, true));
        call.append(", " + d_builtin_array_type.getArrayTypeName());
        call.append(", " + ARRAY_DOUBLE_RESULT_VAR + "[" 
            + startInd[MACRO_RETURNS_DOUBLE_IND] + "])");
        break;
      case METHOD_ARRAY_IRANGE: 
      case METHOD_ARRAY_NEAR_EQUAL: 
      case METHOD_ARRAY_RANGE: 
        call.append(s_builtin_macro[d_method_type] + "(");
        call.append(getCArgList(epvVar, null, true, false, true));
        call.append(", " + ARRAY_COUNT_VAR);
        call.append(", " + ARRAY_BOOLEAN_RESULT_VAR + "[" 
            + startInd[MACRO_RETURNS_BOOLEAN_IND] + "])");
        break;
      case METHOD_ARRAY_SUM: 
        call.append(s_builtin_macro[d_method_type] + "(");
        call.append(getCArgList(epvVar, null, true, false, true));
        call.append(", " + ARRAY_DOUBLE_RESULT_VAR + "[" 
            + startInd[MACRO_RETURNS_DOUBLE_IND] + "])");
        break;
      default:
        break;
    }

    if (call.length() > 0) {
      list = new ArrayList();
      list.add(call.toString());
    }
    return list;
  }


  /**
   * Returns the number of macros supported by this assertion of the
   * specified type.  Valid types are MACRO_RETURN*.
   */
  public int getNumArrayIterMacrosByType(char type) {
    int num = 0;

    if (type == MACRO_RETURN_TYPE[MACRO_RETURNS_BOOLEAN_IND]) {
      if (  (d_method_type == METHOD_ARRAY_ALL)
         || (d_method_type == METHOD_ARRAY_ANY)
         || (d_method_type == METHOD_ARRAY_IRANGE)
         || (d_method_type == METHOD_ARRAY_NEAR_EQUAL)
         || (d_method_type == METHOD_ARRAY_NON_DECR)
         || (d_method_type == METHOD_ARRAY_NON_INCR)
         || (d_method_type == METHOD_ARRAY_NONE)
         || (d_method_type == METHOD_ARRAY_RANGE) ) {
        num = 1;
      }
    } else if (type == MACRO_RETURN_TYPE[MACRO_RETURNS_DOUBLE_IND]) {
      if (  (d_method_type == METHOD_ARRAY_MAX)
         || (d_method_type == METHOD_ARRAY_MIN)
         || (d_method_type == METHOD_ARRAY_NON_DECR)
         || (d_method_type == METHOD_ARRAY_NON_INCR)
         || (d_method_type == METHOD_ARRAY_SUM) ) {
          num = 1;
      }
    } else if (type == MACRO_RETURN_TYPE[MACRO_RETURNS_INTEGER_IND]) {
      if (d_method_type == METHOD_ARRAY_COUNT) {
        num = 1;
      }
    } else {
      num = 0;
    }
    return num;
  }


  /**
   * Return the C version of the expression.
   *
   * WARNING: Changes to the code generated below must match the validations
   * in the method that validates the semantics of the call.
   */
  public String cExpression(String epvVar, int[] startInd) {
    StringBuffer call = new StringBuffer();
    
    switch (d_method_type) {
      case METHOD_ARRAY_ALL: 
      case METHOD_ARRAY_ANY: 
      case METHOD_ARRAY_IRANGE: 
      case METHOD_ARRAY_NEAR_EQUAL: 
      case METHOD_ARRAY_NON_INCR: 
      case METHOD_ARRAY_NON_DECR: 
      case METHOD_ARRAY_NONE: 
      case METHOD_ARRAY_RANGE: 
        call.append(ARRAY_BOOLEAN_RESULT_VAR + "[" 
            + startInd[MACRO_RETURNS_BOOLEAN_IND] + "]");
        break;
      case METHOD_ARRAY_COUNT: 
        call.append(ARRAY_INTEGER_RESULT_VAR + "[" 
            + startInd[MACRO_RETURNS_INTEGER_IND] + "]");
        break;
      case METHOD_ARRAY_DIMEN: 
      case METHOD_ARRAY_LOWER: 
      case METHOD_ARRAY_SIZE: 
      case METHOD_ARRAY_STRIDE: 
      case METHOD_ARRAY_UPPER: 
        call.append(s_builtin_macro[d_method_type] + "(");
        call.append(getCArgList(epvVar, startInd, true, false, false));
        call.append(")");
        break;
      case METHOD_ARRAY_MAX: 
      case METHOD_ARRAY_MIN: 
      case METHOD_ARRAY_SUM: 
        call.append(ARRAY_DOUBLE_RESULT_VAR + "[" 
            + startInd[MACRO_RETURNS_DOUBLE_IND] + "]");
        break;
      case METHOD_IRANGE: 
      case METHOD_NEAR_EQUAL: 
      case METHOD_RANGE: 
        call.append(s_builtin_macro[d_method_type] + "(");
        call.append(getCArgList(epvVar, startInd, true, false, false));
        call.append(")");
        break;
      default:
        call.append("(" + epvVar + "->"+IOR.getVectorEntry(d_name) + ")(");
        call.append(getCArgList(epvVar, startInd, false, false, false));
        call.append(")");
        break;
    }

    return call.toString();
  }

  public Object accept(ExprVisitor ev, Object data) {
    return ev.visitMethodCall(this, data);
  }
}
