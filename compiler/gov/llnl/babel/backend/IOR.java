//
// File:        IOR.java
// Package:     gov.llnl.babel.backend
// Revision:    @(#) $Id: IOR.java 7202 2011-09-27 20:11:43Z adrian $
// Description: common sidl to IOR routines shared by code generators
//
// Copyright (c) 2000-2007, Lawrence Livermore National Security, LLC
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

package gov.llnl.babel.backend;

import gov.llnl.babel.BabelConfiguration;
import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.Utilities;
import gov.llnl.babel.backend.c.C;
import gov.llnl.babel.backend.fortran.ImplSource;
import gov.llnl.babel.backend.writers.LanguageWriterForC;
import gov.llnl.babel.symbols.Argument;
import gov.llnl.babel.symbols.AssertionExpression;
import gov.llnl.babel.symbols.Class;
import gov.llnl.babel.symbols.CExprString;
import gov.llnl.babel.symbols.Comment;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Interface;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.Struct;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.SymbolTable;
import gov.llnl.babel.symbols.Type;
import gov.llnl.babel.symbols.Version;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Class <code>IOR</code> contains common SIDL to IOR translation
 * routines shared by the backend code generators.  This class simply
 * collects many common IOR language bindings into one place.
 */
public class IOR {
  public final static int MAJOR_VERSION = 2;
  public final static int MINOR_VERSION = 0;

   private final static String s_types[] = {
      "void",
      "sidl_bool",
      "char",
      "struct sidl_dcomplex",
      "double",
      "struct sidl_fcomplex",
      "float",
      "int32_t",
      "int64_t",
      "void*",
      "char*"
   };

   private final static String s_array_types[] = {
      null,
      "struct sidl_bool__array*",
      "struct sidl_char__array*",
      "struct sidl_dcomplex__array*",
      "struct sidl_double__array*",
      "struct sidl_fcomplex__array*",
      "struct sidl_float__array*",
      "struct sidl_int__array*",
      "struct sidl_long__array*",
      "struct sidl_opaque__array*",
      "struct sidl_string__array*"
   };

  private final static String [] s_builtinMethods = {
    "_cast",                    // the CAST method
    "_delete",                  // the DELETE method
    "_exec",                    // the reflexive EXEC method
    "_getURL",                  // get's the object's URL (for RMI)
    "_raddRef",                 // Remote addRef, Internal Babel  
    "_isRemote",                // TRUE if this object is Remote
    "_set_hooks",	        // the HOOKS method
    "_set_contracts",           // the Contract CONTRACTS method
    "_dump_stats",              // the DUMP_STATS method
    "_ctor",                    // the CONSTRUCTOR method
    "_ctor2",                    // the CONSTRUCTOR2 method
    "_dtor",                    // the DESTRUCTOR method
    "_load",                    // the LOAD method
  };

  private final static boolean [] s_builtinRMI = {
    false,
    false,
    true,
    true,
    true,
    true,
    false,
    false,
    false,
    false,
    false,
    false,
    false
  };

  private final static String[] s_builtin_comments = {
    "Cast method for interface and class type conversions.",
    "Delete method called automatically by IOR to destroy object.",
    "Select and execute a method by name",
    "Get the URL of the Implementation of this object (for RMI)",
    "On a remote object, addrefs the remote instance.",
    "TRUE if this object is remote, false if local",
    "Method to enable/disable method hooks invocation.",
    "Method to enable/disable interface contract enforcement.",
    "Method to dump contract enforcement statistics.",
    "Class constructor called when the class is created.",
    "Special Class constructor called when the user wants to wrap his own private data.",
    "Class destructor called when the class is deleted.",
    "Static class initializer called exactly once before any user-defined method is dispatched",
  };

  /**
   * The number of built-in methods that an interface has. Builtin methods
   * are implicitly defined methods that are required for the inner
   * workings of the IOR or to support the language bindings. The names
   * of the built-ins are numbers
   * <code>0...INTERFACE_BUILT_IN_METHODS-1</code> and are available from
   * the method <code>getBuiltinMethod</code>.
   *
   * @see #getBuiltinMethod
   */
  public static final int INTERFACE_BUILT_IN_METHODS = 9;

  /**
   * The number of built-in methods that a class has. Builtin methods are
   * implicitly defined methods that are required for the inner
   * workings of the IOR or to support the language bindings. The names
   * of the built-ins are numbers
   * <code>0...CLASS_BUILT_IN_METHODS-1</code> and are available from
   * the method <code>getBuiltinMethod</code>.
   */
  public static final int CLASS_BUILT_IN_METHODS = 13;

  /**
   * The index of the built-in method for casting.
   */
  public static final int CAST = 0;

  /**
   * The index of the built-in method for deleting an object.
   */
  public static final int DELETE = 1;

  /**
   * The index of the built-in method for executing a named method.
   */
  public static final int EXEC = 2;

  /**
   * The index of the built-in method for getting the object's URL.
   */
  public static final int GETURL = 3;

  /**
   * The index of the built-in method for determining if an object is local.
   */
  public static final int RADDREF = 4;

  /**
   * The index of the built-in method for determining if an object is remote.
   */
  public static final int ISREMOTE = 5;

  /**
   * The index of the built-in method for enabling/disabling hooks
   * execution.
   */
  public static final int HOOKS = 6;


  /**
   * The index of the built-in method for contract checking.
   */
  public static final int CONTRACTS = 7;

  /**
   * The index of the built-in method for dumping contract checking
   * statistics.
   */
  public static final int DUMP_STATS = 8;

  /**
   * The index of the built-in method for constructing a
   * class instance
   */
  public static final int CONSTRUCTOR = 9;

  /**
   * The index of the special built-in method for constructing a
   * class instance with user passed in private data
   */
  public static final int CONSTRUCTOR2 = 10;

  /**
   * The index of the built-in method for destructing a
   * class instance
   */
  public static final int DESTRUCTOR = 11;

  /**
   * The index of the built-in method for initializing a class
   * (before first instance, or static method is called).
   */
  public static final int LOAD = 12;

  private static final int BUILT_IN_MIN = 0;
  private static final int BUILT_IN_MAX = 12;

  private static final String s_default_version    = MAJOR_VERSION + "." 
                                                     + MINOR_VERSION + ".0";

  private static SymbolID s_exceptionID            = null;
  private static String s_exceptionFundamentalType = null;
  private static String s_objectType               = null;
  private static String s_interfaceType            = null;

  public static String FUND_EXCEPTION_CALL_PREFIX = 
               BabelConfiguration.getFundamentalException().replace('.', '_');
  public static String PRECONDITION_CALL_PREFIX   = 
               BabelConfiguration.getPreconditionViolation().replace('.', '_');
  public static String POSTCONDITION_CALL_PREFIX  = 
               BabelConfiguration.getPostconditionViolation().replace('.', '_');
  public static String INVARIANT_CALL_PREFIX      = 
               BabelConfiguration.getInvariantViolation().replace('.', '_');

  static {
    s_exceptionID = new SymbolID(BabelConfiguration.getBaseExceptionType(), 
                                 new Version(s_default_version));
    s_exceptionFundamentalType = getObjectName(s_exceptionID) + " *";
    SymbolID id = new SymbolID(BabelConfiguration.getBaseClass(),
                      new Version());
    s_objectType = getObjectName(id) + " *";
    id = new SymbolID(BabelConfiguration.getBaseInterface(),
                      new Version());
    s_interfaceType = getObjectName(id) + " *";
  }

   private final static String[] s_epv_type = { "", "b"};
   public  final static int PUBLIC_EPV      = 0;
   public  final static int BASE_EPV        = 1;
   private final static int MIN_EPV_TYPE    = 0;
   private final static int MAX_EPV_TYPE    = 1;

   public  final static int    SET_PUBLIC        = 0;
   public  final static int    SET_CONTRACTS     = 1;
   public  final static int    SET_HOOKS         = 2;
   private final static int    SET_MINIMUM       = 0;
   private final static int    SET_MAXIMUM       = 2;
   private final static String s_SET_EPV_NAMES[] = {
     "",
     "contracts",
     "hooks"
   };

   public  final static int   EPV_MINE       = 0;
   public  final static int   EPV_PARENT    = 1;
   public  final static int   EPV_REMOTE    = 2;
   public  final static int   EPV_STATIC    = 3;
   public  final static int   EPV_ARG       = 4;
   private final static int   EPV_MINIMUM   = 0;
   private final static int   EPV_MAXIMUM   = 4;
   private final static String s_EPV_DESC[] = {
     "my",
     "par",
     "rem",
     "stc",
     "arg"
   };

   public final static String GENERIC_PRE_SUFFIX    = "_pre";
   public final static String GENERIC_POST_SUFFIX   = "_post";

   public final static String ATTR_UNUSED = "__attribute__ ((__unused__))";
   public final static String MACRO_VAR_UNUSED = "VAR_UNUSED";

   /* F90 uses 2-way stubs/skeletons to pass along structs */
   public final static String GENERIC_FLATTENED_SUFFIX   = "_flat";
  
   public final static String D_CSTATS              = "d_cstats";
   public final static String D_DATA                = "d_data";
   public final static String D_ENABLED             = "enabled";
   public final static String D_HOOKS               = "use_hooks";
   public final static String D_METHOD_CSTATS       = "method_cstats";
   public final static String D_NONVIO_EXCEPTS      = "nonvio_exceptions";
   public final static String D_SUCCESSES           = "successes";
   public final static String D_FAILURES            = "failures";
   public final static String D_TRIES               = "tries";

   public final static String D_IS_STATIC           = "is_static";
   public final static String D_INV_COMPLEXITY      = "inv_complexity";
   public final static String D_PRE_COMPLEXITY      = "pre_complexity";
   public final static String D_POST_COMPLEXITY     = "post_complexity";

   public final static String D_EXEC_METH_TIME      = "meth_exec_time";
   public final static String D_EXEC_INV_TIME       = "inv_exec_time";
   public final static String D_EXEC_PRE_TIME       = "pre_exec_time";
   public final static String D_EXEC_POST_TIME      = "post_exec_time";
   public final static String D_EST_INTERVAL        = "est_interval";

   public final static String S_CSTATS              = "s_cstats";
   public final static String S_DUMP_FPTR           = "s_dump_fptr";
   public final static String S_TRACE_FPTR          = "s_trace_fptr";

   /**
    * Some defaults.
    */
   public final static String DEFAULT_OPTION_HOOKS  = "0";
   public final static String DEFAULT_STATS_FN      = "ContractStats.out";

   public final static String s_static_suffix       = "_static";

   /*------------------------------------------------------------------*/

    /** A CPP Macro Name */
    public static String getLockStaticGlobalsMacroName() { 
	return "LOCK_STATIC_GLOBALS";
    }
    /** A CPP macro name */
    public static String getUnlockStaticGlobalsMacroName() { 
	return "UNLOCK_STATIC_GLOBALS";
    }
    /** A CPP macro name */
    public static String getHaveLockStaticGlobalsMacroName() { 
	return "HAVE_LOCKED_STATIC_GLOBALS";
    }

  /**
   * Return the method description of a particular built-in method. This will
   * raise an <code>ArrayIndexOutOfBoundsException</code> if
   * <code>index</code> is less than zero or greater than or equal to the
   * number of built-in methods.
   *
   * @param index       the index of the built-in method that is
   *                    desired. Generally, one of <code>CAST</code>,
   *                    <code>DELETE</code>, <code>CONSTRUCTOR</code>,
   *                    or <code>DESTRUCTOR</code> though others possible.
   * @param id          the name of the symbol
   * @param sVersion    TRUE if the static version is desired; else FALSE
   * @return a description the method.
   * @exception java.lang.ArrayIndexOutOfBoundsException
   *    this runtime exception is thrown if <code>index</code> is out of
   *    bounds.
   */
  public static Method getBuiltinMethod(int      index,
                                        SymbolID id,
                                        Context  context,
                                        boolean  sVersion)
    throws CodeGenerationException
  {
    Method m = new Method(context);
    m.setBuiltIn(true);
    m.setMethodName(getBuiltinName(index, sVersion));
    if ((index==LOAD) || (sVersion)) { 
	m.setDefinitionModifier(Method.STATIC);
    } else { 
	m.setDefinitionModifier(Method.NORMAL);
    }

    String[] s = new String[1];
    if (sVersion) {
      s[0] = "Static " + s_builtin_comments[index];
    } else {
      s[0] = s_builtin_comments[index];
    }
    m.setComment(new Comment(s));
    
    Argument a = null;
    switch (index) {
    case CAST: 
      a = new Argument(Argument.IN, new Type(Type.STRING), "name");
      m.addArgument(a);
      m.setReturnType(new Type(Type.OPAQUE));
      break;
    case CONSTRUCTOR2:
      m.setReturnType(new Type(Type.VOID));
      a = new Argument(Argument.IN, new Type(Type.OPAQUE), "private_data");
      m.addArgument(a);
      break;
    case CONTRACTS:
      a = new Argument(Argument.IN, new Type(Type.BOOLEAN), "enable");
      m.addArgument(a);
/*
 * TLD/ToDo:  Need to remove enfFilename (once ready to remove it from
 *    all regression tests) since appears to cause problems in CCA
 *    context (for C++).
 */
      a = new Argument(Argument.IN, new Type(Type.STRING), "enfFilename");
      m.addArgument(a);
      a = new Argument(Argument.IN, new Type(Type.BOOLEAN), "resetCounters");
      m.addArgument(a);
      m.setReturnType(new Type(Type.VOID));
      break;
    case DUMP_STATS:
/*
 * TLD/ToDo:  Need to remove filename and prefix (once ready to remove 
 *    it from all regression tests) since appears to cause problems in 
 *    CCA context (for C++).
 */
      a = new Argument(Argument.IN, new Type(Type.STRING), "filename");
      m.addArgument(a);
      a = new Argument(Argument.IN, new Type(Type.STRING), "prefix");
      m.addArgument(a);
      m.setReturnType(new Type(Type.VOID));
      break;
    case EXEC:
      a = new Argument(Argument.IN, new Type(Type.STRING), "methodName");
      m.addArgument(a);
      Symbol tmpSym = Utilities.lookupSymbol(context, "sidl.rmi.Call");
      a = new Argument(Argument.IN, new Type(tmpSym, context), 
                       "inArgs");
      m.addArgument(a);
      tmpSym = Utilities.lookupSymbol(context, "sidl.rmi.Return");
      a = new Argument(Argument.IN, new Type(tmpSym, context), 
                       "outArgs");
      m.addArgument(a);
      m.setReturnType(new Type(Type.VOID));
      break;
    case GETURL:
      m.setReturnType(new Type(Type.STRING));
      break;
    case HOOKS:
      a = new Argument(Argument.IN, new Type(Type.BOOLEAN), "enable");
      m.addArgument(a);
      m.setReturnType(new Type(Type.VOID));
      break;
    case ISREMOTE:
      m.setReturnType(new Type(Type.BOOLEAN));
      break;
    case RADDREF:
      m.setReturnType(new Type(Type.VOID));
      break;
    default:
      m.setReturnType(new Type(Type.VOID));
      break;
    }
    m.addThrows(getRuntimeException(context));
    return m;
  }

  /**
   * Return the method description of a particular non-static built-in method. 
   * This will raise an <code>ArrayIndexOutOfBoundsException</code> if
   * <code>index</code> is less than zero or greater than or equal to the
   * number of built-in methods.
   *
   * @param index       the index of the desired built-in method.
   * @param id          the name of the symbol
   * @return a description the method.
   * @exception java.lang.ArrayIndexOutOfBoundsException
   *    this runtime exception is thrown if <code>index</code> is out of
   *    bounds.
   */
  public static Method getBuiltinMethod(int index, SymbolID id, Context context) 
    throws CodeGenerationException
  {
    return getBuiltinMethod(index, id, context, false);
  }

  /**
   * Return TRUE if there is a built-in static version of the method; FALSE 
   * otherwise.
   *
   * @param index       the index of the desired built-in method.
   */
  public static boolean hasStaticBuiltin(int index) {
    return (index == CONTRACTS) || (index == HOOKS) || (index == DUMP_STATS);
  }

  public static boolean isRMIRelated(int index) {
    return s_builtinRMI[index];
  }

  /**
   * Return the name of the specified version of the built-in method.
   *
   * @param index       the index of the built-in method that is
   *                    desired. Generally, one of <code>CAST</code>,
   *                    <code>DELETE</code>, <code>CONSTRUCTOR</code>,
   *                    or <code>DESTRUCTOR</code> though others possible.
   * @param sVersion    TRUE if the static version is desired; FALSE otherwise.
   * @exception java.lang.ArrayIndexOutOfBoundsException
   *    this runtime exception is thrown if <code>index</code> is out of
   *    bounds.
   */
  public static String getBuiltinName(int index, boolean sVersion) {
    return s_builtinMethods[index] + (sVersion ? s_static_suffix : "");
  }

  /**
   * Return the comment for the specified built-in method.
   *
   * @param index       the index of the built-in method that is
   *                    desired. Generally, one of <code>CAST</code>,
   *                    <code>DELETE</code>, <code>CONSTRUCTOR</code>,
   *                    or <code>DESTRUCTOR</code> though others possible.
   * @exception java.lang.ArrayIndexOutOfBoundsException
   *    this runtime exception is thrown if <code>index</code> is out of
   *    bounds.
   */
  public static String getBuiltinComment(int index) {
    return s_builtin_comments[index];
  }

  /**
   * Return the normal name of the built-in method.
   *
   * @param index       the index of the desired built-in method.
   * @exception java.lang.ArrayIndexOutOfBoundsException
   *    this runtime exception is thrown if <code>index</code> is out of
   *    bounds.
   */
  public static String getBuiltinName(int index) {
    return getBuiltinName(index, false);
  }

  /**
   * Return TRUE if the method name is one of the built-in methods, FALSE
   * otherwise.
   *
   * @param methodName  the name of the method being checked
   * @param sVersion    TRUE if the static version is desired; FALSE otherwise.
   */
  public static boolean isBuiltinMethod(String methodName, boolean sVersion) {
    for (int i=BUILT_IN_MIN; (i<=BUILT_IN_MAX); i++) {
      if(!sVersion || (sVersion && hasStaticBuiltin(i)))
        if(methodName.equals(getBuiltinName(i, sVersion))) {
          return true;
      }
    }
    return false;
  }

  /**
   * Return TRUE if the method name is one of the non-static built-in methods, 
   * FALSE otherwise.
   *
   * @param methodName  the name of the method being checked
   */
  public static boolean isBuiltinMethod(String methodName) {
    for (int i=BUILT_IN_MIN; (i<=BUILT_IN_MAX); i++) {
      if (methodName.equals(getBuiltinName(i, false)))
        return true;
    }
    return false;
  }

  /**
   * Return TRUE if the index is associated with a basic built-in method,
   * FALSE otherwise.
   */
  public static boolean isBuiltinBasic(int ind) {
    boolean is = false;
    if ( (CAST <= ind) && (ind <= DESTRUCTOR) ) {
      is = true;
    }
    return is;
  }

  /**
   * Return TRUE if the index is associated with a contract-related built-in 
   * method, FALSE otherwise.
   */
  public static boolean isBuiltinContractMeth(int ind) {
    boolean is = false;
    if ( (ind == CONTRACTS) || (ind == DUMP_STATS) ) {
      is = true;
    }
    return is;
  }

  /**
   * Generate the name of an entry in the entry point vector or the
   * static entry point vector.
   *
   * @param methodName  the name of the method that is an element
   *                    in the entry point vector.
   */
  public static String getVectorEntry(String methodName) {
    StringBuffer buf = new StringBuffer(2 + methodName.length());
    buf.append("f_").append(methodName);
    return buf.toString();
  }

  /**
   * Generate the name of the native entry in the entry point vector or the
   * static entry point vector.
   *
   * @param methodName  the name of the method that is an element
   *                    in the entry point vector.
   */
  public static String getNativeVectorEntry(String methodName) {
    StringBuffer buf = new StringBuffer(2 + methodName.length());
    buf.append("f_").append(methodName).append("_native");
    return buf.toString();
  }

  /**
   * Generate the name of the native entry in the entry point vector or the
   * static entry point vector.
   *
   * @param methodName  the name of the method that is an element
   *                    in the entry point vector.
   */
  public static String getNativeEPVGuard(Extendable ext) {
    String res = ext.getFullName().toUpperCase().replace('.', '_');
    return "SIDL_" + res + "_FASTCALL_ENABLED";
  }
  
   /**
    * Generate the header filename associated with a symbol identifier.
    * Replace the "." scope separators in the symbol by underscores and
    * append the suffix "_IOR.h".
    */
   public static String getHeaderFile(SymbolID id) {
      return id.getFullName().replace('.', '_') + "_IOR.h";
   }

   /**
    * Generate the source filename associated with a symbol identifier.
    * Replace the "." scope separators in the symbol by underscores and
    * append the suffix "_IOR.c".
    */
   public static String getSourceFile(SymbolID id) {
      return id.getFullName().replace('.', '_') + "_IOR.c";
   }

   /**
    * Convert a symbol name into an IOR identifier.  This method replaces
    * the "." scope separators in the symbol by underscores.
    */
   public static String getSymbolName(SymbolID id) {
      return id.getFullName().replace('.', '_');
   }

   /**
    * Convert a symbol name into an IOR identifier.  This method replaces
    * the "." scope separators in the symbol by underscores.
    */
   public static String getSymbolName(String name) {
      return name.replace('.', '_');
   }

  /**
   * Returns the name of the built-in method, prepending "ior_" and the name of
   * the symbol.
   */
  public static String getMethodName(SymbolID id, String name) {
    return "ior_" + getSymbolName(id) + '_' + name;
  }

   /**
    * Convert a SIDL enumerated type into its symbol name, which is
    * "enum " followed by the symbol name followed by "__enum".
    */
   public static String getEnumName(SymbolID id) {
     return C.getEnumName(id);
   }

  /**
   * Return the enum value symbol for a particular enum type and string.
   */
  public static String getEnumValueSymbol(SymbolID id, String value)
  {
    return id.getFullName().replace('.','_') + "_" + value;
  }

  /**
   * Get struct name for extern entry point structure.
   */
  public static String getExternalName(SymbolID id)
  {
    return "struct " + getSymbolName(id) + "__external";
  }

  /**
   * Get struct name for extern entry point variable.
   */
  public static String getExternalVariableName(SymbolID id)
  {
    return "_" + getSymbolName(id) + "__external";
  }

  /**
   * Get the name of the function that returns the structure of
   * external entry points.
   */
  public static String getExternalFunc(SymbolID id)
  {
    return getSymbolName(id) + "__externals";
  }

   /**
    * Convert a SIDL interface or class into its symbol name, which is
    * "struct " followed by the symbol name followed by "__object".
    */
   public static String getObjectName(SymbolID id) {
      return "struct " + getSymbolName(id) + "__object";
   }

   /**
    * Convert a SIDL interface or class into its symbol name, which is
    * "struct " followed by the symbol name followed by "__object".
    */
   public static String getObjectName(String fqn) {
      return "struct " + getSymbolName(fqn) + "__object";
   }

  /**
   * Convert a SIDL struct into its IOR data type name, which is
   * "struct " followed by the symbol name followed by "__data".
   */
  public static String getStructName(SymbolID id) {
    return "struct " + getSymbolName(id) + "__data";
  }

  /**
    * Convert a SIDL interface or class into its remote struct name, which is
    * "struct " followed by the symbol name followed by "__remote".
    */
   public static String getRemoteStructName(SymbolID id) {
      return "struct " + getSymbolName(id) + "__remote";
   }

   /**
    * Convert a SIDL symbol into its array representation, which is
    * "struct " followed by the symbol name followed by "__array".
    * Passing <code>null</code> to this function causes it to 
    * return the generic (typeless) SIDL array.
    */
   public static String getArrayName(SymbolID id) {
      return (null != id) 
        ? ("struct " + getSymbolName(id) + "__array")
        : "struct sidl__array";
   }

  /** 
   * Get the sidl array name for a given type.  Use the arrayType from the array.
   */
  public static String getArrayName(int intType) {
    return s_array_types[intType];
  }

  /** 
   * Gets the sidl array name for a given type, and removes the trailing
   * asterix.  Use the arrayType from the array.
   */
  public static String getArrayNameWithoutAsterix(int intType) {
    return s_array_types[intType].substring(0,s_array_types[intType].length()-1);
  }

  /** 
   * Gets the sidl array name for a given type, for use in C functions.
   */
  public static String getArrayNameForFunctions(int intType) {
    return s_array_types[intType].substring(7,s_array_types[intType].length()-1);
  }

   /**
    * Return TRUE if the Symbol ID corresponds to a SIDL symbol; FALSE 
    * otherwise.
    */
   public static boolean isSIDLSymbol(SymbolID id) {
      return id.getFullName().toUpperCase().startsWith("SIDL.");
   }

   /**
    * Return TRUE if the Symbol ID corresponds to a SIDLX symbol; FALSE 
    * otherwise.
    */
   public static boolean isSIDLXSymbol(SymbolID id) {
      return id.getFullName().toUpperCase().startsWith("SIDLX.");
   }

   /**
    * Return TRUE if the contract-related built-in methods are to be
    * generated.
    *
    * Assumptions:  
    * 1) Contract-related EPVs are not generated for SIDL interfaces or classes.
    * 2) Contract-related EPVs are not generated for exceptions.
    */
   public static boolean generateContractBuiltins(Extendable ext, 
                                                  Context context) 
   {
     return (!isSIDLSymbol(ext)) && (!isSIDLXSymbol(ext))
            && (!Utilities.isException(ext, context));
   }
 
   /**
    * Return TRUE if the contract-related EPVs are supposed to be generated.  
    *
    * Assumptions:  
    * 1) Contract-related EPVs are only generated for concrete classes.
    * 2) Contract-related EPVs are not generated for SIDL classes.
    * 3) Contract-related EPVs are not generated for exceptions.
    */
   public static boolean generateContractEPVs(Extendable ext, Context context) 
   {
     return (!ext.isAbstract()) && (!ext.isInterface()) 
          && generateContractBuiltins(ext, context);
   }
 
   /**
    * Return TRUE if contract checks are supposed to be generated.
    * 
    * Assumptions:
    * 1) Assumptions in generateContractEPVs() apply.
    * 2) Checks are only generated if the class has its own or
    *    inherited contract clauses.
    * 3) Checks are only generated if the configuration indicates
    *    their generation is required.
    */
   public static boolean generateContractChecks(Extendable ext, Context context)
     throws CodeGenerationException 
   {
     return generateContractEPVs(ext, context) && ext.hasContracts() 
            && context.getConfig().generateContracts();
   }

   /**
    * Return TRUE if the hooks-related built-ins are supposed to be generated.  
    *
    * Assumption:  Only non-SIDL interfaces and classes are to include 
    * the hook EPVs.  Exceptions are _not_ to be included.
    */
   public static boolean generateHookBuiltins(Extendable ext, Context context) {
     return !isSIDLSymbol(ext) && !isSIDLXSymbol(ext) 
            && !Utilities.isException(ext, context);
   }
 
   /**
    * Return TRUE if the hooks-related EPVs are supposed to be generated.  
    *
    * Assumption:  Only non-SIDL interfaces and classes are to include 
    * the hook EPVs.  Exceptions are _not_ to be included.
    */
   public static boolean generateHookEPVs(Extendable ext, Context context) {
     return !isSIDLSymbol(ext) && !isSIDLXSymbol(ext) 
            && !Utilities.isException(ext, context);
   }
 
   /**
    * Return TRUE if hook methods are to be generated; FALSE otherwise.
    * 
    * Assumptions:
    * 1) Assumptions in generateHookEPVs() apply.
    * 2) Hook methods are only generated if configuration indicates
    *    their generation is required.
    */
   public static boolean generateHookMethods(Extendable ext, Context context) {
     return generateHookEPVs(ext, context) 
            && context.getConfig().generateHooks();
   }

   /**
    * Return TRUE if the base EPV attribute needs to be supported; FALSE 
    * otherwise.
    */
   public static boolean generateBaseEPVAttr(Extendable ext, Context context)
     throws CodeGenerationException 
   {
     return generateHookMethods(ext, context) 
            && generateContractChecks(ext, context);
   }

   /**
    * Return the name of the invariant description data structure name.
    */
  public static String getInvDescDataStruct(SymbolID id) {
    return "struct " + getSymbolName(id) + "__inv_desc";
  }

   /**
    * Return the name of the static variable associated with the invariant
    * description data.
    */
  public static String getInvDescDataName(SymbolID id) {
    return "s_ior_" + getSymbolName(id) + "_inv";
  }

   /**
    * Return the name of the method description data structure name.
    */
  public static String getMethodDescDataStruct(SymbolID id) {
    return "struct " + getSymbolName(id) + "__method_desc";
  }

   /**
    * Return the name of the static variable associated with the method
    * description data.
    */
  public static String getMethodDescDataName(SymbolID id) {
    return "s_ior_" + getSymbolName(id) + "_method";
  }

   /**
    * Convert a SIDL symbol into its control structure.
    */
   public static String getControlsNStatsStruct(SymbolID id) {
      return "struct " + getSymbolName(id) + "__cstats";
   }

   /**
    * Convert a SIDL symbol into its method control structure.
    */
   public static String getMethodControlsNStatsStruct(SymbolID id) {
      return "struct " + getSymbolName(id) + "__method_cstats";
   }

   /**
    * Convert a SIDL symbol into its method entry point vector (EPV) name.  
    */
   public static String getEPVName(SymbolID id) {
      return "struct " + getSymbolName(id) + "__epv";
   }

   /**
    * Convert a SIDL symbol into its method that returns initialized epv
    * pointers.
    */
   public static String getGetEPVsName(SymbolID id) {
      return getSymbolName(id) + "__getEPVs";
   }

   /**
    * Convert a SIDL symbol into its static entry point vector (SEPV) name.
    */
   public static String getSEPVName(SymbolID id) {
      return "struct " + getSymbolName(id) + "__sepv";
   }

   /**
    * Convert a SIDL symbol into its Pre hooks method entry point vector 
    * (EPV) name.  
    */
   public static String getPreEPVName(SymbolID id) {
      return "struct " + getSymbolName(id) + "__pre_epv";
   }

   /**
    * Convert a SIDL symbol into its pre hooks static entry point vector 
    * (SEPV) name.
    */
   public static String getPreSEPVName(SymbolID id) {
      return "struct " + getSymbolName(id) + "__pre_sepv";
   }

   /**
    * Convert a SIDL symbol into its Post hooks method entry point vector 
    * (EPV) name.  
    */
   public static String getPostEPVName(SymbolID id) {
      return "struct " + getSymbolName(id) + "__post_epv";
   }

   /**
    * Convert a SIDL symbol into its post hooks static entry point vector 
    * (SEPV) name.
    */
   public static String getPostSEPVName(SymbolID id) {
      return "struct " + getSymbolName(id) + "__post_sepv";
   }


   /**
    * Return the type associated with the specified EPV type index,
    * or an empty string if the index is out of range.
    */
   public static String getEPVType(int type) {
      String ret = "";
      if ( (MIN_EPV_TYPE <= type) && (type <= MAX_EPV_TYPE) ) {
        ret = s_epv_type[type];
      }
      return ret;
   }

   /**
    * Return the standard method entry point vector (EPV) variable.  
    */
   public static String getEPVVar(int type) {
      String etype = getEPVType(type);
      return (etype.equals("")) ? "d_epv" : "d_" + etype + "epv";
   }

   /**
    * Returns the name of the set EPV type.
    */
   public static String getSetEPVTypeName(int type) {
     int t = SET_PUBLIC;
     if ( (SET_MINIMUM <= type) && (type <= SET_MAXIMUM) ) {
       t = type;
     }
     return s_SET_EPV_NAMES[t];
   }

   /**
    * Convert a SIDL symbol into the name of its associated constructor,
    * which is the symbol name appended with "__createObject".
    *
    * WARNING: This method name must be consistent with the implementation
    * of the sidl.DLL.createClass method.
    */
   public static String getNewName(SymbolID id) {
      return getSymbolName(id) + "__createObject";
   }

   /**
    * Convert a SIDL symbol into the name of its associated remote
    * constructor, which is the symbol name appended with "__remote".
    */
   public static String getRemoteName(SymbolID id) {
      return getSymbolName(id) + "__remoteCreate";
   }

   /**
    * Convert a SIDL symbol into the name of its associated remote
    * connector, which is the symbol name appended with "__connect".
    */
   public static String getRemoteConnectName(SymbolID id) {
      return getSymbolName(id) + "__connectI";
   }

  /**
    * Convert a SIDL symbol into the name of its associated remote
    * cast, which is the symbol name appended with "__rmicast".
    */
  public static String getRemoteCastName(SymbolID id) {
    return getSymbolName(id) + "__rmicast";
  }


   /**
    * Convert a SIDL symbol into the name of its associated getURL
    * function, which is the symbol name appended with "__getURL".
    */
   public static String getRemoteGetURLName(SymbolID id) {
      return getSymbolName(id) + "__getURL";
   }

   /**
    * Convert a SIDL symbol into the name of its associated getURL
    * function, which is the symbol name appended with "__getURL".
    */
   public static String getRemoteIsRemoteName(SymbolID id) {
      return getSymbolName(id) + "__isRemote";
   }
   /**
    * Convert a SIDL symbol into the name of its associated getURL
    * function, which is the symbol name appended with "__getURL".
    */
   public static String getRaddRefName(SymbolID id) {
      return getSymbolName(id) + "__raddRef";
   }

  /**
   * Returns a Set containing all the Symbol IDs that need FConnect
   * methods generated for them in this Extendable.
   */
  public static Set getFConnectSymbolIDs(Extendable ext) 
    throws CodeGenerationException
  {
    HashSet ret = new HashSet();
    for(Iterator mm = ext.getMethods(true).iterator(); mm.hasNext(); ) {
      Method method = (Method) mm.next();

      for(Iterator aa = method.getArgumentList().iterator(); aa.hasNext(); ) {
        Argument arg = (Argument) aa.next();
        
        /* We need an fconnect for class and interface args that are 
           IN or INOUT and not copied.*/
        if (((arg.getType().getDetailedType() == Type.CLASS) || 
            (arg.getType().getDetailedType() == Type.INTERFACE)) &&
           (arg.getMode() != Argument.OUT) && 
           (!arg.isCopy())) {
          
          if (arg.getType().getSymbolID() == null) {
            throw new CodeGenerationException("Failure: Symbol Type argument "
                        + "has no SymbolID!");
          }
          ret.add(arg.getType().getSymbolID());
        }
      }
    }
    return ret;
  }


  /**
   * Returns a Set containing all the Symbol IDs that need FCast
   * methods generated for them in this Extendable.
   */
  public static Set getFCastSymbolIDs(Extendable ext) 
    throws CodeGenerationException 
  {
    HashSet ret = new HashSet();
    for(Iterator mm = ext.getMethods(true).iterator(); mm.hasNext(); ) {
      Method method = (Method) mm.next();

      for(Iterator aa = method.getArgumentList().iterator(); aa.hasNext(); ) {
        Argument arg = (Argument) aa.next();
        
        /* We need an fconnect for class and interface args that are 
           IN or INOUT and copy.*/
        if (((arg.getType().getDetailedType() == Type.CLASS) || 
            (arg.getType().getDetailedType() == Type.INTERFACE)) &&
           (arg.getMode() != Argument.OUT) && 
           (arg.isCopy())) {
          
          if (arg.getType().getSymbolID() == null) {
            throw new CodeGenerationException("Failure: Symbol Type argument "
                        + "has no SymbolID!");
          }
          ret.add(arg.getType().getSymbolID());
        }
      }
    }
    return ret;
  }

  /**
   * Returns a Set containing all the Symbol IDs of structs that
   * need serialize/deserialize methods generated for them in this
   * Extendable.
   */
  public static Set getStructSymbolIDs(Extendable ext, boolean serialize) 
    throws CodeGenerationException 
  {
    HashSet ret = new HashSet();
    for(Iterator mm = ext.getMethods(true).iterator(); mm.hasNext(); ) {
      Method method = (Method) mm.next();

      for(Iterator aa = method.getArgumentList().iterator(); aa.hasNext(); ) {
        Argument arg = (Argument) aa.next();

        //Clients currently expect the implementation to export support routines
        //for (de)serialization. These routines should probably not be part of a
        //specific class or interface at all since structs are global
        //symbols. For now, we simply make sure the Skeletton contains the full
        //set of functions, effectively disregarding the "serialize"
        //flag. Revisit this. 
        if ((arg.getType().getDetailedType() == Type.STRUCT) /*&&
            ((serialize && (arg.getMode() != Argument.OUT)) ||
            !(serialize || (arg.getMode() == Argument.IN)))*/) {
          
          if (arg.getType().getSymbolID() == null) {
            throw new CodeGenerationException("Failure: Symbol Type argument "
                        + "has no SymbolID!");
          }
          ret.add(arg.getType().getSymbolID());
        }
      }

      //same as above
      if (/*(!serialize) && */
          (Type.STRUCT == method.getReturnType().getDetailedType())) {
        ret.add(method.getReturnType().getSymbolID());
      }
    }
    return ret;
  }

  public static String getSkelSerializationName(SymbolID extid,
                                                SymbolID structid,
                                                boolean serialize)
  {
    return "skel_" + getSymbolName(extid) + 
      (serialize ? "_serialize_" : "_deserialize_") +
      getSymbolName(structid);
  }

  /**
    * Convert a SIDL symbol into the name of its associated remote
    * connector.  This requires both the SybmolID of the class this is being
    * defined in (sourceid) and the SymbolID of the target class to be
    * connected (targetid) 
    */
  public static String getSkelFConnectName(SymbolID sourceid, SymbolID targetid) {
    return "skel_" + getSymbolName(sourceid) + "_fconnect_" 
             + getSymbolName(targetid);
   }

  /**
    * Convert a SIDL symbol into the name of its associated remote
    * connector.  This requires both the SybmolID of the class this is being
    * defined in (sourceid) and the SymbolID of the target class to be
    * connected (targetid) 
    */
  public static String getSkelFCastName(SymbolID sourceid, SymbolID targetid) {
    return "skel_"+getSymbolName(sourceid)+"_fcast_" + getSymbolName(targetid);
   }

   /**
    * Convert a SIDL symbol into the name of its set EPV method.
    */
   public static String getSetEPVName(SymbolID id) {
      return getSymbolName(id) + "__set_epv";
   }

   /**
    * Convert a SIDL symbol into the name of its set static EPV method.
    */
   public static String getSetSEPVName(SymbolID id) {
      return getSymbolName(id) + "__set_sepv";
   }

  /**
   * Return the static EPV prefix string or, if invalid, the one with the
   * minimum type value.
   */
  public static String getEPVPrefix(int epvType) {
    int type = EPV_MINIMUM;
    if ( (EPV_MINIMUM <= epvType) && (epvType <= EPV_MAXIMUM) ) {
      type = epvType;
    }
    return "s_" + s_EPV_DESC[type] + "_epv";
  }

  /**
   * Return the name of the specified static EPV variable.
   */
  public static String getStaticEPVVariable(SymbolID id, int epvType, 
                                            int setType) 
  {
    String name = getSymbolName(id).toLowerCase();
    String type = getSetEPVTypeName(setType);
    return getEPVPrefix(epvType) + (type.equals("") ? "" : "_" + type)
           + "__" + name;
  }

  /**
   * Generate the static EPV variables for the specified extendable and
   * EPV type.
   */
  public static void generateStaticEPVVariables(LanguageWriterForC lw, 
                                                Extendable ext,
                                                boolean has_static, 
                                                boolean is_remote, 
                                                int setType)
  {
    String sType     = "static " + getEPVName(ext);
    String sTypeStr  = has_static ? sType + "  " : sType + " ";
    String suType    = "static " +MACRO_VAR_UNUSED+" "+getEPVName(ext);
    String suTypeStr = has_static ? suType + "  " : suType + " ";
    String ssTypeStr = "static "+MACRO_VAR_UNUSED+" " + getSEPVName(ext) + " ";

    if (!ext.isInterface() && !is_remote) {
      if (setType == IOR.SET_CONTRACTS) {
        lw.print(suTypeStr);
      } else {
        lw.print(sTypeStr);
      }
      lw.println(getStaticEPVVariable(ext, EPV_MINE, setType) + ";");
    }

    if (is_remote) {
      lw.print(sTypeStr);
      lw.println(getStaticEPVVariable(ext, EPV_REMOTE, setType) + ";");
    }

    if (has_static) {
      lw.print(ssTypeStr);
      lw.println(getStaticEPVVariable(ext, EPV_STATIC, setType) + ";");
    }
    lw.println();
  }

    /**
     * Convert a sidl symbol into the name of its associated _call_load method
     * which is the symbol name appended with "__call_load"
     */
    public static String getCallLoadName(SymbolID id ) { 
	return getSymbolName(id) + "__call_load";
    }

   /**
    * Convert a SIDL symbol into the name of its associated local 
    * statics method.
    */
   public static String getLocalStaticsName(SymbolID id) {
      return getSymbolName(id) + "__getTypeStaticEPV";
   }

   /**
    * Convert a SIDL symbol into the name of its associated statics
    * method, which is the symbol name appended with "__getStaticEPV".
    */
   public static String getStaticsName(SymbolID id) {
      return getSymbolName(id) + "__getStaticEPV";
   }

   /**
    * Convert a SIDL symbol into the name of its associated init
    * method, which is the symbol name appended with "__init".
    */
   public static String getInitName(SymbolID id) {
      return getSymbolName(id) + "__init";
   }

   /**
    * Convert a SIDL symbol into the name of its associated init
    * method, which is the symbol name appended with "__init".
    */
   public static String getInitEPVName(SymbolID id) {
      return getSymbolName(id) + "__init_epv";
   }

   /**
    * Convert a SIDL symbol into the name of its associated fini
    * method, which is the symbol name appended with "__fini".
    */
   public static String getFiniName(SymbolID id) {
      return getSymbolName(id) + "__fini";
   }

   /**
    * Convert a SIDL symbol into the name of its associated fini
    * method, which is the symbol name appended with "__fini".
    */
   public static String getVersionName(SymbolID id) {
      return getSymbolName(id) + "__IOR_version";
   }

  /**
   * Return the name of the type of the implicit exception argument;
   * namely, sidl_BaseInterface__object.  This is deemed necessary
   * in order to minimize the impact on existing Impl codes due to
   * the memory layout of the epv.  That is, it is not necessary for
   * the implementation writer to cast a newly created exception 
   * (to the base exception interface) IF the pointer is declared
   * to be the start of the epv structure to begin with.
   */
  public static String getExceptionFundamentalType() {
    return s_exceptionFundamentalType;
  }

  /**
   * Return the name of the type of the implicit base class type.
   * The return value is of the form "struct X_Y_Z *" where X_Y_Z
   * depends on the name of the base class and its mapping
   * to a C struct name.
   */
  public static String getClassType() {
    return s_objectType;
  }

  /**
   * Return the name of the type of the base interface type.
   * The return value is of the form "struct X_Y_Z *" where X_Y_Z
   * depends on the name of the base interface and its mapping
   * to a C struct name.
   */
  public static String getInterfaceType() {
    return s_interfaceType;
  }

  public static String getPreconditionExceptType() {
    return "struct " + PRECONDITION_CALL_PREFIX + "__object *";
  }

  public static String getPostconditionExceptType() {
    return "struct " + POSTCONDITION_CALL_PREFIX + "__object *";
  }

  public static String getInvariantExceptType() {
    return "struct " + INVARIANT_CALL_PREFIX + "__object *";
  }

  public static String getSymbolType(Symbol sym) {
    if (sym.getSymbolType() == Symbol.ENUM) {
      return getEnumName(sym);
    } else {
      return getObjectName(sym) + "*";
    }
  }

  /**
   * Generate a return string for the specified SIDL type.  Most of
   * the SIDL return strings are listed in the static structures defined
   * at the start of the class.  Symbol types and array types require
   * special processing.
   *
   * @param type   the <code>Type</code> whose return string is being built.
   */
  public static String getReturnString(Type type, Context context)
    throws CodeGenerationException
  {
    return getReturnString(type, context, true, false);
  }

  /**
   * Gives an initial value based on the type of the argument
   *
   * @param type   the <code>Type</code> whose return string is being built.
   */
  public static String getInitialValue(Type type) throws CodeGenerationException
  {
    int t = type.getDetailedType();
    switch(t) {
    case Type.FCOMPLEX:
    case Type.DCOMPLEX:
      return "{0,0}";
    case Type.CLASS:
    case Type.INTERFACE:
    case Type.ARRAY:
    case Type.PACKAGE:
    case Type.OPAQUE:
      return C.NULL;
    case Type.STRUCT:
      return "{}";
    default:
      return "0";
    }
  }

  /**
   * Generate a C return string for the specified SIDL type.  Most of
   * the SIDL return strings are listed in the static structures defined
   * at the start of the class.  Symbol types and array types require
   * special processing.
   *
   * @param type   the <code>Type</code> whose return string is being built.
   * @param objPtr TRUE if the object pointer type should be returned; FALSE
   *               otherwise.
   * @param inStub TRUE is the string is for the stub; FALSE otherwise.
   */
  public static String getReturnString(Type type, 
                                       Context context,
                                       boolean objPtr,
                                       boolean inStub)
    throws CodeGenerationException
  {
    /*
     * If the type is one of the primitive types, then just return
     * its string value from the lookup table.
     */
    int t = type.getType();
    if (t < s_types.length) {
      return s_types[t];
    }

   /*
     * If the type is a symbol, then look up the symbol type and return
     * the associated type name.
     */
    if (t == Type.SYMBOL) {
      Symbol symbol = Utilities.lookupSymbol(context, type.getSymbolID());

      if (symbol.getSymbolType() == Symbol.ENUM) {
        return "int64_t";
      } else if (symbol.getSymbolType() == Symbol.STRUCT) {
        return getStructName(symbol);
      }
      else {
        if (objPtr) {
          return C.getSymbolObjectPtr(symbol);
        } else {
          return C.getObjectName(symbol);
        }
      }
    }

    /*
     * If the type is an array, then either return one of the primitive
     * array types or construct the corresponding array type.
     */
    if (t == Type.ARRAY) {
      Type atype = type.getArrayType();

      if (inStub && type.isRarray())
        return getReturnString(atype, context, objPtr, inStub) + "*";

      if (null != atype) {
        int a = atype.getType();

        if (a < s_array_types.length) {
          return s_array_types[a];
        } else {
          return getArrayName(atype.getSymbolID()) + "*";
        }
      } else {
        return getArrayName(null) + "*";
      }
    }
    return null;
  }

  /**
   * Generate a string containing only the specified method's arguments,
   * including exceptions, if any.
   */
  public static String getArgumentString(Method method, Context context)
    throws CodeGenerationException 
  {
     StringBuffer argstring = new StringBuffer();

     boolean has_throws = !method.getThrows().isEmpty();
     List args = method.getArgumentList();

     if ((args.size() > 0) || has_throws) {
        argstring.append(",");
     }

     for (Iterator a = args.iterator(); a.hasNext(); ) {
        Argument arg = (Argument) a.next();
        argstring.append(getArgumentString(arg, context, true, false, false));
        if (a.hasNext() || has_throws) {
           argstring.append(",");
        }
     }

     if (has_throws) {
       argstring.append(getExceptionFundamentalType());
       argstring.append('*');
     }

     return argstring.toString();
  }

   /**
    * Generate an argument string for the specified SIDL argument.
    * The formal argument name is not included.
    *
    * @param arg    the <code>Argument</code> whose string is being built.
    */
   public static String getArgumentString(Argument arg, 
                                          Context context)
     throws CodeGenerationException
   {
     return getArgumentString(arg, context, false, false, false);
   }

   /**
    * Generate a C argument string for the specified SIDL argument.
    * The formal argument name is not included.
    *
    * @param arg    the <code>Argument</code> whose string is being built.
    * @param objPtr TRUE if the object pointer type should be returned; FALSE
    *               otherwise.
    * @param inStub TRUE is the string is for the stub; FALSE otherwise.
    * @param isExec TRUE if the string is for declaring variables in an Exec
    *               function, FALSE otherwise
    */
   public static String getArgumentString(Argument arg, 
                                          Context context,
                                          boolean objPtr,
                                          boolean inStub, 
                                          boolean isExec)
     throws CodeGenerationException
   {
     final Type   type = arg.getType();
     final int    iType = type.getDetailedType();
     String s    = getReturnString(type, context, objPtr, inStub);
 
     if (arg.getMode() == Argument.IN) {
       if ((iType == Type.STRING) || (iType == Type.STRUCT)) {
         s = "const " + s;
       }
       if (iType == Type.STRUCT) {
         s = s + "*";
       }
     } else if (((!inStub) || (!type.isRarray())) && !isExec ) {
       s = s + "*";
     }
 
     return s;
   }

   /**
    * Generate an argument string with the formal argument name.
    *
    * @param arg    the <code>Argument</code> whose string is being built.
    */
   public static String getArgumentWithFormal(Argument arg,
                                              Context context)
     throws CodeGenerationException
   {
     return getArgumentWithFormal(arg, context, false, false, false);
   }

   /**
    * Generate a C argument string with the formal argument name.
    *
    * @param arg    the <code>Argument</code> whose string is being built.
    * @param objPtr TRUE if the object pointer type should be returned; FALSE
    *               otherwise.
    * @param inStub TRUE if the string is for the stub; FALSE otherwise.
    * @param isExec TRUE if the string is generated for an exec function
    */
   public static String getArgumentWithFormal(Argument arg, 
                                              Context context,
                                              boolean objPtr,
                                              boolean inStub, boolean isExec)
     throws CodeGenerationException
   {
     return getArgumentString(arg, context, objPtr, inStub, isExec) + " " 
              + arg.getFormalName();
   }

   /**
    * Generate a cast string for the specified method.  The string
    * argument self represents the name of the object.  A code generation
    * exception is thrown if any of the required symbols do not exist in
    * the symbol table.
    */
   public static String getCast(Method method, String self,
                                Context context)
     throws CodeGenerationException 
   {

      /*
       * Begin the cast string with the return type and self object reference.
       */
      StringBuffer cast = new StringBuffer();
      cast.append("(");
      cast.append(getReturnString(method.getReturnType(), context, 
                                  true, false));
      cast.append(" (*)(");
      cast.append(self);

      /*
       * Add the method arguments to the cast clause as well as an
       * optional exception argument.
       */
      cast.append(getArgumentString(method, context));
      cast.append("))");

      return cast.toString();
   }

   /**
    * Return the static epv type option name.
    */
   public static String getStaticTypeOption(SymbolID id, int type) {
      int t = SET_PUBLIC;
      if ( (SET_MINIMUM <= type) && (type <= SET_MAXIMUM) ) {
         t = type;
      }
      String name = getSetEPVTypeName(t).toUpperCase();
      return "s_SEPV_" + getSymbolName(id).toUpperCase() + "_"
           + (name.equals("") ? "BASE" : name);
   }

   /**
    * Return the method index constant name associated with the specified 
    * method.
    */
  public static String getMethodIndex(SymbolID id, Method meth) {
     return "s_IOR_" + getSymbolName(id).toUpperCase() + "_" 
           + meth.getLongMethodName().toUpperCase();
  }

   /**
    * Return the method index constant name associated with the specified 
    * literal.
    */
  public static String getMethodIndex(SymbolID id, String lit) {
     return "s_IOR_" + getSymbolName(id).toUpperCase() + "_" 
           + lit.toUpperCase();
  }

  public static class CompareMethods implements Comparator {
    public int compare(Object o1, Object o2)
    {
      Method m1 = (Method)o1;
      Method m2 = (Method)o2;
      return m1.getLongMethodName().compareTo(m2.getLongMethodName());
    }
  }

  public static SymbolID getRuntimeException(Context context) {
    return context.getSymbolTable().
                   lookupSymbol(BabelConfiguration.getRuntimeException());
  }

  public static Symbol getRuntimeExceptionSymbol(Context context) {
    return context.getSymbolTable().
                   lookupSymbol(BabelConfiguration.getRuntimeException());
  }


  /**
   * Generate the cast function for a class. This will return null if the cast
   * is invalid and a pointer to the object otherwise. The logic generates tests
   * for the current class and then recursively queries the parent classes.
   */
  public static void generateCastFunction(Class cls, String self, 
                                          LanguageWriterForC writer, 
                                          boolean rmi, boolean addref) 
    throws CodeGenerationException
  {
    writer.println();
    /*
     * Define the method signature and begin the method implementation.
     */
    if (rmi) {
      writer.writeCommentLine("REMOTE CAST: dynamic type casting for remote "
                             + "objects.");
      writer.print("static void* remote_");
      writer.println(getSymbolName(cls) + "_" + getBuiltinName(IOR.CAST) + '(');

    } else {
      writer.writeCommentLine("CAST: dynamic type casting support.");
      
      writer.print("static void* ");
      writer.println(getMethodName(cls, getBuiltinName(IOR.CAST)) + '(');
    }
    writer.tab();
    writer.println(IOR.getObjectName(cls) + "* " + self + ",");

    writer.println("const char* name, sidl_BaseInterface* _ex)");
    writer.backTab();
    writer.println("{");
    writer.tab();
    ArrayList sortedTypes = new ArrayList(cls.getParents(true));
    sortedTypes.add(cls);
    Collections.sort(sortedTypes, new CompareSymbols());
    writer.println("int cmp;");
    writer.println("void* cast = NULL;");
    writer.println("*_ex = NULL; /* default to no exception */");
    castBinarySearch(sortedTypes, cls, 0, sortedTypes.size(), 0, writer,addref);
    if (rmi) {
      // A little hack to get the correct isType name if we're generating
      // inside an anonymous class. There should be a smarter way....
      //    if (ifc != null) {
      writer.println("if ((*self->d_epv->f_isType)("+self+",name, _ex)) {");
      
      writer.tab();
      writer.print("void* (*func)(struct sidl_rmi_InstanceHandle__object*, ");
      writer.println("struct sidl_BaseInterface__object**) = ");
      writer.tab();
      writer.print("(void* (*)(struct sidl_rmi_InstanceHandle__object*, ");
      writer.println("struct sidl_BaseInterface__object**)) ");
      writer.print("sidl_rmi_ConnectRegistry_getConnect(name, _ex);");
      writer.println("SIDL_CHECK(*_ex);");
      writer.backTab();
      
      writer.print("cast =  (*func)(((" + IOR.getRemoteStructName(cls));
      writer.println("*)" + self + "->d_data)->d_ih, _ex);");
      writer.backTab();
      writer.println("}");
      
      writer.println();
    }
    writer.println("return cast;");
    writer.println("EXIT:"); 
    writer.println("return NULL;");
    writer.backTab();
    writer.println("}");
    writer.println();
  }

  public static void generateMacroVarNotUsed(LanguageWriterForC writer) {
    writer.printlnUnformatted("#ifndef " + MACRO_VAR_UNUSED);
    writer.printlnUnformatted("#ifdef __GNUC__");
    writer.printlnUnformatted("#define " + MACRO_VAR_UNUSED + " " +ATTR_UNUSED);
    writer.printlnUnformatted("#else");
    writer.printlnUnformatted("#define "+ MACRO_VAR_UNUSED);
    writer.printlnUnformatted("#endif /* __GNUC__ */");
    writer.printlnUnformatted("#endif /* " + MACRO_VAR_UNUSED + " */");
    writer.println();
  }

  private static void castBinarySearch(ArrayList types,
                                       Class cls,
                                       int lower,
                                       int upper,
                                       int level, 
                                       LanguageWriterForC writer, 
                                       boolean addref)
    throws CodeGenerationException
  {
    if (lower < upper) {
      final int middle = (lower + upper) / 2;
      final Extendable e = (Extendable)types.get(middle);
      writer.println("cmp = strcmp(name, \"" + e.getFullName() + "\");");
      writer.println("if (!cmp) {");
      castReturn(cls, e, "self", writer, addref);
      
      writer.println("}"); 
      if (lower < middle) {
        writer.println("else if (cmp < 0) {");
        writer.tab();
        castBinarySearch(types, cls, lower, middle, level + 1,writer,addref);
        writer.backTab();
        writer.println("}");
      }
      if (middle+1 < upper) {
        writer.println("else if (cmp > 0) {");
        writer.tab();
        castBinarySearch(types, cls, middle+1, upper, level + 1,writer,addref);
        writer.backTab();
        writer.println("}");
      }
    }
  }

  private static boolean hasAncestor(Collection excluded,
                                     Extendable search,
                                     Extendable target)
  {
    if (excluded.contains(search)) return false;
    if (search.equals(target)) return true;
    Iterator i = search.getParentInterfaces(false).iterator();
    while (i.hasNext()) {
      Extendable e = (Extendable)i.next();
      if (hasAncestor(excluded, e, target)) return true;
    }
    return false;
  }

  private static boolean implementsByInheritance(Class cls, Extendable e)
  {
    Collection excludedInterfaces;
    Class parent = cls.getParentClass();
    if (parent != null) {
      excludedInterfaces = parent.getParentInterfaces(true);
    } else {
      excludedInterfaces = new ArrayList();
    }
    
    Iterator i = Utilities.getUniqueInterfaces(cls).iterator();
    while (i.hasNext()) {
      Extendable ext = (Extendable)i.next();
      if (hasAncestor(excludedInterfaces, ext, e)) return true;
    }
    return false;
  }

  private static boolean directlyImplements(Class cls, Extendable e) 
  {
    while (cls != null) {
      if (cls.getParentInterfaces(false).contains(e)) return true;
      cls = cls.getParentClass();
    }
    return false;
  }

  private static Class nextAncestor(Class ancestor, StringBuffer result)
  {
    ancestor = ancestor.getParentClass();
    if (ancestor != null) {
      result.append(".d_");
      result.append(IOR.getSymbolName(ancestor).toLowerCase());
    }
    return ancestor;
  }

  /**
   * Generate an expression to obtain a pointer to an interface or
   * subclass from an object pointer.
   *
   * @param self   this string holds the name to the object pointer
   *               that the interface/subclass pointer will be
   *               obtained from
   * @param cls    the object pointer self is a class pointer to
   *               this type
   * @param e      this is the type of the interface/subclass pointer
   *               to be obtained
   * @return  a String containing the expression to cast & (if necessary)
   *          dereference the self pointer to the appropriate internal
   *          data structure
   */
  public static String classToInterfacePtr(Class cls, 
                                           Extendable e,
                                           String self)
    throws CodeGenerationException
  {
    if (cls.equals(e) || cls.hasAncestor(e)) {
      if (e instanceof Class) {
        return "((" + getSymbolType(e) + ")" + self + ")";
      }
      else {
        Class ancestor = cls;
        StringBuffer result = new StringBuffer();
        final boolean direct = directlyImplements(cls, e);
        result.append("&((*" + self + ")");
        while (ancestor != null) {
          if ((direct && Utilities.getUniqueInterfaces(ancestor).contains(e)) ||
              ((!direct) && implementsByInheritance(ancestor, e))) {
            result.append(".d_");
            result.append(IOR.getSymbolName(e).toLowerCase());
            break;
          } else {
            ancestor = nextAncestor(ancestor, result);
          }
        }
        if (ancestor == null) {
          throw new CodeGenerationException("Illegal symbol table entry: " 
                                           + cls.getFullName() + " and " 
                                           + e.getFullName());
        }
        result.append(')');
        return result.toString();
      }
    } else {
      return "NULL";
    }
  }
              
  private static void castReturn(Class cls, Extendable e, String self,
                          LanguageWriterForC writer, boolean addref)
    throws CodeGenerationException
  {
    writer.tab();
    if (addref) {
      writer.print("(*" + self + "->d_epv->");
      writer.print(IOR.getVectorEntry("addRef"));
      writer.println(")(" + self + ", _ex); SIDL_CHECK(*_ex);");
    }
    writer.println("cast = " + classToInterfacePtr(cls, e, self) + ";");
    writer.println("return cast;");
    writer.backTab();
  }

  /**
   * Class to compare types.
   */
  private static class CompareSymbols implements Comparator {
    public int compare(Object o1, Object o2) {
      return ((Symbol)o1).getFullName().compareTo(((Symbol)o2).getFullName());
    }
  }

  public static void resolveRenamedMethods(Extendable ext, HashMap renames) {
    
    //Check is current ext has renamed, if so add them
    Set renamed = ext.getNewMethods();
    for(Iterator i = renamed.iterator(); i.hasNext();) {
      Method newM = (Method) i.next();
      Method oldM = ext.getRenamedMethod(newM);
      SymbolID old_sid = ext.getRenamedMethodSymbolID(oldM);
      renames.put(old_sid.getFullName() + "." + oldM.getLongMethodName(), newM);
    }
    
    //Check if parent has any methods that are renamed, if so, add to map 
    Collection parents = ext.getParents(false);
    for(Iterator i = parents.iterator(); i.hasNext();) {
      Extendable parent = (Extendable) i.next();
      for(Iterator ms = parent.getMethods(true).iterator(); ms.hasNext();) {
        Method curM = (Method) ms.next();
        //If the method was renamed in a child
        if (renames.containsKey(ext.getFullName() + "."
                                                  + curM.getLongMethodName()))
        {
          //and we haven't already added this parents's version
          if (!(renames.containsKey(parent.getFullName() + "."
                                   + curM.getLongMethodName()))) 
          {
            //add this parents version
            renames.put(parent.getFullName() + "." + curM.getLongMethodName(), 
                        ext.lookupMethodByLongName(
                              curM.getLongMethodName(), true));
          }
        }
      }
      
      //After adding the methods from this parent, recursively call the parent
      resolveRenamedMethods(parent, renames);
    }
  }


  public static void declareEPVsAsArgs(LanguageWriterForC lw, Class cls, 
                                       Context context, boolean first) 
  {
    if (cls != null) {
      declareEPVsAsArgs(lw, cls.getParentClass(), context, false);
      List ifce = Utilities.sort(Utilities.getUniqueInterfaces(cls));
      for (Iterator i = ifce.iterator(); i.hasNext();) {
        Interface ifc = (Interface) i.next();
        lw.print(getEPVName(ifc) + " **");
        lw.print(getStaticEPVVariable(ifc, IOR.EPV_ARG, IOR.SET_PUBLIC));
        lw.println(",");
        if (generateHookEPVs(ifc, context)) { 
          lw.print(getEPVName(ifc) + " **");
          lw.print(getStaticEPVVariable(ifc, IOR.EPV_ARG, IOR.SET_HOOKS));
          lw.println(",");
        }
      }
      lw.print(getEPVName(cls) + " **");
      lw.print(getStaticEPVVariable(cls, IOR.EPV_ARG, IOR.SET_PUBLIC));
      if (generateHookEPVs(cls, context)) {
        lw.println(",");
        lw.print(getEPVName(cls) + " **");
        lw.print(getStaticEPVVariable(cls, IOR.EPV_ARG, IOR.SET_HOOKS));
      }

      if (!first){
        lw.println(",");
      } 
    }
  }

  public static boolean isUnserializable(Context context,
                                         Type t)
  {
    SymbolTable table = context.getSymbolTable();
    if (t != null) {
      switch(t.getDetailedType()) {
      case Type.VOID:
      case Type.PACKAGE:
        return true;
      case Type.STRUCT:
      case Type.CLASS:
      case Type.INTERFACE:
        Symbol sym = table.lookupSymbol(t.getSymbolID());
        if (sym != null) {
          if (sym instanceof Extendable) {
            Extendable ext = (Extendable)sym;
            Extendable serializable =  (Extendable)
              table.lookupSymbol(BabelConfiguration.getSerializableType());
            return !ext.hasAncestor(serializable);
          }
          else if (sym instanceof Struct) {
            return isUnserializable(context, (Struct)sym);
          }
        }
        return true;
      case Type.ARRAY:
        return isUnserializable(context, t.getArrayType());
      }
    }
    return false;
  }

  public static boolean isUnserializable(Context context,
                                         Struct strct)
  {
    Iterator i = strct.getItems().iterator();
    while (i.hasNext()) {
      final Struct.Item item = (Struct.Item)i.next();
      if (isUnserializable(context, item.getType())) return true;
    }
    return false;
  }

 /**
  * Generate the method's argument list.
  *
  * @param writer the language writer.
  *
  * @param self the String representing the method's self argument name.
  *
  * @param is_interface the boolean indicating whether working with a class
  *                     or an interface.
  *
  * @param id the <code>SymbolID</code> of the <code>Extendable</code> whose
  *           stub source is being written.
  *
  * @param method the <code>Method</code> whose list is being output.
  *
  * @param in_signature the boolean indicating whether the argument list is
  *                     being generated in a signature.
  *
  * @param add_type the boolean indicating whether the argument types are 
  *                 to be added.
  *
  * @param do_throws the boolean indicating whether the exception is to be 
  *                  added.
  *
  * @param do_return the boolean indicating whether the return type is to be
  *                  added.
  *
  * @param do_rarrays the boolean indicating if special raw array argument 
  *                   handling is needed.
  *
  * @exception gov.llnl.babel.backend.CodeGenerationException
  *    this is a catch all exception. It can be caused by I/O trouble or
  *    violations of the data type invariants.
  */
  public static void generateArgumentList(LanguageWriterForC writer, 
                                          Context context,
                                          String self, boolean is_interface,
                                          SymbolID id, Method method,
                                          boolean in_signature,
                                          boolean add_type, boolean obj_ptr, 
                                          boolean do_throws, boolean do_return,
                                          boolean do_indices,
                                          boolean do_rarrays)
    throws CodeGenerationException
  {
    String excVar = do_throws ? Utilities.s_exception : "";

    generateArgumentList(writer, context,
                         self, is_interface, id, method, in_signature,
                         add_type, obj_ptr, excVar, do_return, do_indices, 
                         do_rarrays, false);
  }

 /**
  * Generate the method's argument list.
  *
  * @param writer the language writer.
  *
  * @param self the String representing the method's self argument name.
  *
  * @param is_interface the boolean indicating whether working with a class
  *                     or an interface.
  *
  * @param id the <code>SymbolID</code> of the <code>Extendable</code> whose
  *           stub source is being written.
  *
  * @param method the <code>Method</code> whose list is being output.
  *
  * @param in_signature the boolean indicating whether the argument list is
  *                     being generated in a signature.
  *
  * @param add_type the boolean indicating whether the argument types are 
  *                 to be added.
  *
  * @param exc_var the variable to be used for the exception argument; NULL
  *                if no exception argument to be generated.
  *
  * @param do_return the boolean indicating whether the return type is to be
  *                  added.
  *
  * @param do_rarrays the boolean indicating if special raw array argument 
  *                   handling is needed.
  *
  * @exception gov.llnl.babel.backend.CodeGenerationException
  *    this is a catch all exception. It can be caused by I/O trouble or
  *    violations of the data type invariants.
  */
  public static void generateArgumentList(LanguageWriterForC writer, 
                                          Context context,
                                          String self, boolean is_interface,
                                          SymbolID id, Method method,
                                          boolean in_signature,
                                          boolean add_type, boolean obj_ptr, 
                                          String exc_var, boolean do_return,
                                          boolean do_indices,
                                          boolean do_rarrays,
                                          boolean isExec)
    throws CodeGenerationException
  {
    boolean doThrows   = !exc_var.equals("") && !method.getThrows().isEmpty();
    String  excVar     = doThrows ? exc_var : "";
    Type    returnType = do_return ? method.getReturnType() : null;

    List args = null;
    if (do_indices) {
      args = method.getArgumentListWithIndices();
    } else {
      args = method.getArgumentListWithOutIndices();
    }

    String m_self;
    if (in_signature) {
      if (add_type) {
        m_self = getObjectName(id) + "*" + self;
      } else {
        m_self = self;
      }
    } else if (is_interface) {
      m_self = self + "->d_object";
    } else {
      m_self = Utilities.s_self;
    }
    generateArguments(writer, context,
                      m_self, args, method.isStatic(), excVar,
                      returnType, add_type, obj_ptr, do_rarrays, false, isExec);
  }

 /**
  * Generate the specified argument list.
  *
  * @param writer the language writer.
  *
  * @param self the String representing the method's self argument name.
  *
  * @param args the basic argument list for the method.
  *
  * @param isStatic the boolean indicating whether the method is static.
  *
  * @param doThrows the boolean indicating if an exception argument is to be
  *                 added.
  *
  * @param returnType the return type of the method OR null if the method
  *                   return type should not be included.
  *
  * @param objPtr TRUE if the object pointer type should be returned; FALSE
  *               otherwise.
  *
  * @param doRaw the boolean indicating if special raw array argument handling
  *              is needed.
  *
  * @param deref_inout if true, out and inout arguments are dereferenced in
  *                    passing.  (Used for pre and post hooks)
  *
  * @exception gov.llnl.babel.backend.CodeGenerationException
  *    this is a catch all exception. It can be caused by I/O trouble or
  *    violations of the data type invariants.
  */
  public static void generateArguments(LanguageWriterForC writer, 
                                       Context context,
                                       String self,
                                       List args, boolean isStatic,
                                       boolean doThrows, Type returnType,
                                       boolean addType, boolean objPtr,
                                       boolean doRaw, boolean deref_inout)
    throws CodeGenerationException
  {
    String excVar = doThrows ? Utilities.s_exception : "";

    generateArguments(writer, context, 
                      self, args, isStatic, excVar, returnType, 
                      addType, objPtr, doRaw, deref_inout, false);
  }

 /**
  * Generate the specified argument list.
  *
  * @param writer the language writer.
  *
  * @param self the String representing the method's self argument name.
  *
  * @param args the basic argument list for the method.
  *
  * @param isStatic the boolean indicating whether the method is static.
  *
  * @param excVar the variable to be used for the exception argument; NULL
  *               if no exception argument to be generated.
  *
  * @param returnType the return type of the method OR null if the method
  *                   return type should not be included.
  *
  * @param objPtr TRUE if the object pointer type should be returned; FALSE
  *               otherwise.
  *
  * @param doRaw the boolean indicating if special raw array argument handling
  *              is needed.
  * 
  * @param deref_inout if true, out and inout arguments are dereferenced in
  *                    passing.  (Used for pre and post hooks)
  * 
  * @exception gov.llnl.babel.backend.CodeGenerationException
  *    this is a catch all exception. It can be caused by I/O trouble or
  *    violations of the data type invariants.
  */
  public static void generateArguments(LanguageWriterForC writer, 
                                       Context context,
                                       String self,
                                       List args,
                                       boolean isStatic,
                                       String excVar, 
                                       Type returnType,
                                       boolean addType, 
                                       boolean objPtr, 
                                       boolean doRaw, 
                                       boolean deref_inout,
                                       boolean isExec)
    throws CodeGenerationException
  {
    boolean doThrows = (excVar != null) && !excVar.equals("");
    boolean doReturn = false; //(returnType != null)
    //&& (returnType.getType() != Type.VOID);

    /*
     * If the method is not static, then it will begin with an
     * object reference.
     */
    if (!isStatic) {
      if (addType) { writer.print("/* in */ "); }
      writer.print(self);
      if ((args.size() > 0) || doThrows || doReturn) {
        writer.println(",");
      }
    } else if (addType && (args.size() == 0) && !doThrows && !doReturn) {
      writer.print("void");
    }

    /*
     * Output each argument in turn.
     */
    for (Iterator a = args.iterator(); a.hasNext(); ) {
      Argument arg = (Argument) a.next();
      boolean  isRaw = doRaw && arg.getType().isRarray();

      if (addType) {
        writer.print("/* " + C.argComment(arg) + " */ ");
        if (deref_inout && arg.getMode() != Argument.IN) {
          writer.print(" *");
        }
        writer.print(IOR.getArgumentWithFormal(arg, context, 
                                               objPtr, isRaw, false));
      } else if (isRaw) {
        if (arg.getMode() == Argument.INOUT) {
          writer.print("&");
        }
        writer.print(arg.getFormalName() + C.RAW_ARRAY_EXT);
      } else {
        if (deref_inout && arg.getMode() != Argument.IN) {
          writer.print(" *");
        }
        if (!isExec &&
            (arg.getMode() != Argument.IN) &&
            (arg.getType().getDetailedType() == Type.ENUM)) {
          writer.print("&_proxy_");
        }
        writer.print(arg.getFormalName());
      }
      if (a.hasNext() || doThrows || doReturn) {
        writer.println(",");
      }
    }

    /*
     * If there is a throws clause that is to be included, return the 
     * exception type as the last item in the argument list associated with 
     * the method.
     */
    if (doThrows) {
      if (addType) {
        writer.print("/* out */ ");
        writer.print(s_exceptionFundamentalType + "*");
      }
      writer.print(excVar);
      if (doReturn) {
        writer.println(",");
      }
    }

    /*
     * Finally, the return type (if appropriate).
     */
    if (doReturn) {
      if (addType) {
        writer.print("/* in */ ");
        writer.print(IOR.getReturnString(returnType, context, objPtr, false));
      }
      writer.print(" " + C.FUNCTION_RESULT);
    }
  }


 /**
  * Generate the interface contract enforcement controls and statistics 
  * structures.
  *
  * @param writer the language writer.
  *
  * @param ext    the class.
  *
  * @exception gov.llnl.babel.backend.CodeGenerationException
  *    This is a catch all exception. It can be caused by I/O trouble.
  */
  public static void generateControlNStats(LanguageWriterForC writer, 
                                           Extendable ext,
                                           Context    context)
    throws CodeGenerationException
  {
      boolean  doContracts = generateContractChecks(ext, context);
      SymbolID id          = ext.getSymbolID();
      int      num         = ext.getNumberOfMethodsWithContracts();
                                                                                
      writer.writeComment("Define the controls and statistics structure.",
                            false);
      writer.println();
      writer.println(getControlsNStatsStruct(id) + " {");
      writer.tab();
      writer.println("sidl_bool " + D_HOOKS + ";");
      if (doContracts) {
        writer.println("sidl_bool " + D_ENABLED + ";");
        writer.println(getMethodControlsNStatsStruct(id) + " {");
        writer.tab();
        writer.println("int32_t " + D_TRIES + ";");
        writer.println("int32_t " + D_SUCCESSES + ";");
        writer.println("int32_t " + D_FAILURES + ";");
        writer.println("int32_t " + D_NONVIO_EXCEPTS + ";");
        writer.backTab();
        writer.println("} " + D_METHOD_CSTATS + "[" + num + "];");
      }
      writer.backTab();
      writer.println("};");
      writer.println();
  }

  /**
   * returns true if the given type represents a fixed size raw array
   */
   public static boolean isFixedSizeRarray(Type type) {
    if(!type.isRarray())
      return false;

    boolean is_fixed_array = true;
    
    List indices = type.getArrayIndexExprs();
    for(Iterator i = indices.iterator(); i.hasNext();) {
      AssertionExpression ae = (AssertionExpression)i.next();
      is_fixed_array &= ImplSource.isInt(ae.toString());
    }
    return is_fixed_array;
  }

  /**
   * returns true if the given type represents a raw(=dynamic size)
   * raw-array
   */
   public static boolean isRawRarray(Type type) {
     return type.isRarray() && !isFixedSizeRarray(type);
   }

  /**
   * returns a C expression that computes the size of the given raw array
   */
  public static String getRarraySizeExpr(Type type, String iorprefix) {
    if(!type.isRarray())
      return "0";

    String size_expr = new String("");
    
    List indices = type.getArrayIndexExprs();
    for(Iterator i = indices.iterator(); i.hasNext();) {
      AssertionExpression ae = (AssertionExpression)i.next();
      size_expr += ae.accept(new CExprString(iorprefix), null).toString() + " * ";
    }
    size_expr += "sizeof(" + type.getArrayType().getTypeName() + ")";
    return size_expr;
  }
  
}
