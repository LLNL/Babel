//
// File:        C.java
// Package:     gov.llnl.babel.backend.c
// Revision:    @(#) $Id: C.java 7188 2011-09-27 18:38:42Z adrian $
// Description: common C binding routines shared by C code generators
//
// Copyright (c) 2000-2001, Lawrence Livermore National Security, LLC
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

package gov.llnl.babel.backend.c;

import gov.llnl.babel.BabelConfiguration;
import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.Utilities;
import gov.llnl.babel.backend.writers.LanguageWriter;
import gov.llnl.babel.backend.writers.LanguageWriterForC;
import gov.llnl.babel.symbols.Argument;
import gov.llnl.babel.symbols.AssertionExpression;
import gov.llnl.babel.symbols.CExprString;
import gov.llnl.babel.symbols.Comment;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.Struct;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.Type;
import gov.llnl.babel.symbols.Version;
import java.util.Iterator;
import java.util.List;

/**
 * Class <code>C</code> contains common C language binding routines
 * shared by the C backend code generators.  This class simply collects
 * many common C binding routines into one place.
 */
public class C 
{
  public final static String FUNCTION_RESULT   = "_retval";
  public final static String NULL              = "NULL";
  public final static String RAW_ARRAY_EXT     = "_tmp";

  private static String s_exceptionType   = null;
  private static String s_exceptionObjPtr = null;
  private static String s_self            = Utilities.s_self;

  static {
    SymbolID id = new SymbolID(BabelConfiguration.getBaseExceptionType(),
                               new Version());
    s_exceptionType   = getObjectName(id);
    s_exceptionObjPtr = getSymbolObjectPtr(id);
  }

  /**
   * Return the full self declaration (i.e., the type and standard self
   * variable.
   */
  public static String getFullSelfDecl(SymbolID id) {
    return getSymbolName(id) + " " + s_self;
  }

  /**
   * Generate the header filename associated with a symbol identifier.
   * Replace the "." scope separators in the symbol by underscores and
   * append the suffix ".h".
   *
   * @param id the <code>SymbolID</code> of the <code>Symbol</code>.
   */
  public static String getHeaderFile(SymbolID id) {
    return getSymbolName(id) + ".h";
  }

  /**
   * Generate the stub filename associated with a symbol identifier.
   * Replace the "." scope separators in the symbol by underscores and
   * append the suffix "_Stub.c".
   *
   * @param id the <code>SymbolID</code> of the <code>Symbol</code>.
   */
  public static String getStubFile(SymbolID id) {
    return getSymbolName(id) + "_Stub.c";
  }

  /**
   * Generate the skeleton filename associated with a symbol identifier.
   * Replace the "." scope separators in the symbol by underscores and
   * append the suffix "_Skel.c".
   *
   * @param id the <code>SymbolID</code> of the <code>Symbol</code>.
   */
  public static String getSkelFile(SymbolID id) {
    return getSymbolName(id) + "_Skel.c";
  }

  /**
   * Generate the implementation header filename associated with a
   * symbol identifier.  Replace the "." scope separators in the symbol
   * by underscores and append the suffix "_Impl.h".
   *
   * @param id the <code>SymbolID</code> of the <code>Symbol</code>.
   */
  public static String getImplHeaderFile(SymbolID id) {
    return getSymbolName(id) + "_Impl.h";
  }

  /**
   * Generate the implementation source filename associated with a
   * symbol identifier.  Replace the "." scope separators in the symbol
   * by underscores and append the suffix "_Impl.c".
   *
   * @param id the <code>SymbolID</code> of the <code>Symbol</code>.
   */
  public static String getImplSourceFile(SymbolID id) {
    return getSymbolName(id) + "_Impl.c";
  }

  /**
   * Convert a symbol name into its private data structure identifier.
   * Unlike the IOR, though, use the typedef version (i.e., no "struct")
   * where the SIDL name with the "." scope separators replaced by 
   * underscores is followed by "__data".
   *
   * @param id the <code>SymbolID</code> of the <code>Symbol</code>.
   */
  public static String getDataName(SymbolID id) {
    return "struct " + getSymbolName(id) + "__data";
  }

  /**
   * Convert a symbol name into its private data structure get access
   * function name.  The function name is the SIDL name with the "." scope
   * separators replaced by underscores followed by "__get_data".
   *
   * @param id the <code>SymbolID</code> of the <code>Symbol</code>.
   */
  public static String getDataGetName(SymbolID id) {
    return getSymbolName(id) + "__get_data";
  }

  /**
   * Convert a symbol name into its private data structure set access
   * function name.  The function name is the SIDL name with the "." scope
   * separators replaced by underscores followed by "__set_data".
   *
   * @param id the <code>SymbolID</code> of the <code>Symbol</code>.
   */
  public static String getDataSetName(SymbolID id) {
    return getSymbolName(id) + "__set_data";
  }

  /**
   * Convert a symbol name into its private destructor function name provided
   * by the SkelSource.
   *
   * @param id the <code>SymbolID</code> of the <code>Symbol</code>.
   */
  public static String getPrivateDestructor(SymbolID id) {
      return getSymbolName(id) + "__delete";
  }

  /**
   * Convert a symbol name into an IOR type pointer.  This method replaces
   * the "." scope separators in the symbol by underscores.
   *
   * @param id the <code>SymbolID</code> of the <code>Symbol</code>.
   */
  public static String getSymbolObjectPtr(SymbolID id) {
    return getObjectStructName(id) + "*";
  }

  /**
   * Convert a symbol name into an IOR identifier.  This method replaces
   * the "." scope separators in the symbol by underscores.
   *
   * @param id the <code>SymbolID</code> of the <code>Symbol</code>.
   */
  public static String getSymbolName(SymbolID id) {
    return id.getFullName().replace('.', '_');
  }

  public static String getInlineDecl(SymbolID id) {
    return getSymbolName(id).toUpperCase() + "_INLINE_DECL";
  }

  /**
   * Convert a SIDL enumerated type into its symbol name, which is
   * "enum " followed by the symbol name followed by "__enum".
   *
   * @param id the <code>SymbolID</code> of the <code>Symbol</code>.
   */
  public static String getEnumName(SymbolID id) {
    return "enum " + getSymbolName(id) + "__enum";
  }

  /**
   * Convert a SIDL symbol name into its object name -- for the purposes of
   * this package that means convert it into its typedef object name.  The 
   * typedef name is the SIDL symbol name with the "." scope separators 
   * replaced by underscores.
   *
   * @param id the <code>SymbolID</code> of the <code>Symbol</code>.
   */
  public static String getObjectName(SymbolID id) {
    return getSymbolName(id);
  }

  /**
   * Convert a SIDL symbol name into its object structure name.
   *
   * @param id the <code>SymbolID</code> of the <code>Symbol</code>.
   */
  public static String getObjectStructName(SymbolID id) {
    return "struct " + getObjectName(id) + "__object";
  }

  /**
   * Calculate the maximum name length of struct items.
   */
  public static int getLongestNameLen(Struct strct)
  {
    int result = 0;
    Iterator i = strct.getItems().iterator();
    while (i.hasNext()) {
      final int maxLen = ((Struct.Item)i.next()).getName().length();
      if (maxLen > result) {
        result = maxLen;
      }
    }
    return result;
  }

  /**
   * Convert a SIDL symbol into the name of its associated set EPV
   * method, which is the symbol name appended with "__set_epv".
   *
   * @param id the <code>SymbolID</code> of the <code>Symbol</code>.
   */
  public static String getSetEPVName(SymbolID id) {
    return getSymbolName(id) + "__set_epv";
  }

  /**
    * Convert a SIDL symbol into the name of its associated remote
    * connector.  This requires both the SybmolID of the class this is being
    * defined in (sourceid) and the SymbolID of the target class to be
    * connected (targetid) 
    */
  public static String getImplFConnectName(SymbolID sourceid, SymbolID targetid) {
      return "impl_"+getSymbolName(sourceid)+"_fconnect_" + getSymbolName(targetid);
   }

  /**
    * Convert a SIDL symbol into the name of its associated remote
    * connector.  This requires both the SybmolID of the class this is being
    * defined in (sourceid) and the SymbolID of the target class to be
    * connected (targetid) 
    */
  public static String getImplFCastName(SymbolID sourceid, SymbolID targetid) {
      return "impl_"+getSymbolName(sourceid)+"_fcast_" + getSymbolName(targetid);
   }


  /**
    * Convert a SIDL symbol into the name of its associated get URL function.
    * This requires both the SybmolID of the class this is being
    * defined in (sourceid) and the SymbolID of the target class to be
    * connected (targetid) 
    */
  public static String getImplFGetURLName(SymbolID sourceid, SymbolID targetid) {
      return "impl_"+getSymbolName(sourceid)+"_fgetURL_" + getSymbolName(targetid);
   }

  /**
   * Convert a SIDL symbol into the name of its associated set static
   * EPV method, which is the symbol name appended with "__set_sepv".
   *
   * @param id the <code>SymbolID</code> of the <code>Symbol</code>.
   */
  public static String getSetSEPVName(SymbolID id) {
    return getSymbolName(id) + "__set_sepv";
  }

  /**
   * Generate the impl method's name.
   *
   * @param id the <code>SymbolID</code> of the <code>Symbol</code> 
   *           associated with the method.
   *
   * @param methodName the <code>String</code> version of the name of the
   *               method whose impl name is being built.
   */
  public static String getMethodImplName(SymbolID id, String methodName) {
    return "impl_" + getSymbolName(id) + '_' + methodName;
  }

  private static boolean hasEnum(Method m)
  {
    boolean result = (Type.ENUM ==   m.getReturnType().getDetailedType());
    Iterator i = m.getArgumentList().iterator();
    while (!result && i.hasNext()) {
      Argument arg = (Argument)i.next();
      result = (arg.getType().getDetailedType() == Type.ENUM);
    }
    return result;
  }

  public static boolean methodNeedsSkel(Method method)
  {
    return method.hasArrayOrderSpec() || method.hasRarray() ||
      hasEnum(method);
  }

  /**
   * Generate the skel method's name.  In most cases, the skel name is the
   * impl name except when the method has an array with an ordering
   * specification.
   *
   * @param id the <code>SymbolID</code> of the <code>Symbol</code> 
   *           associated with the method.
   *
   * @param method the method
   */
  public static String getMethodSkelName(SymbolID id, Method method) {
    return methodNeedsSkel(method)
      ? ("skel_" + getSymbolName(id) + '_' + method.getLongMethodName())
      : getMethodImplName(id, method.getLongMethodName());
  }

  /**
   * Generate the full method name associated with the symbol id and the
   * specified method.  The returned name prepends the symbol name and
   * only one underbar to the method's name.
   *
   * @param id the <code>SymbolID</code> of the <code>Symbol</code> 
   *           associated with the method.
   *
   * @param method the <code>Method</code> whose full name is being built.
   */
  public static String getFullMethodName(SymbolID id, Method method) {
    return getSymbolName(id) + "_" + method.getLongMethodName();
  }


  /**
   * Generate the full method name associated with the symbol id and the
   * specified method.  The returned name prepends the symbol name and
   * only one underbar to the method's name.
   *
   * @param id the <code>SymbolID</code> of the <code>Symbol</code> 
   *           associated with the method.
   *
   * @param methodName the <code>String</code> version of the name of the
   *               method whose full name is being built.
   */
  public static String getFullMethodName(SymbolID id, String methodName) {
    return getSymbolName(id) + "_" + methodName;
  }

  public static String getEnsureArray(Type arrayType) {
    switch(arrayType.getDetailedType()) {
    case Type.CLASS:
    case Type.INTERFACE:
      return ArrayMethods.generateEnsureName(arrayType.getSymbolID());
    case Type.ENUM:
      return "sidl_long__array_ensure";
    default:
      return "sidl_" + arrayType.getTypeString() + "__array_ensure";
    }
  }

  public static String getDelRefArray(Type arrayType) {
    switch(arrayType.getDetailedType()) {
    case Type.CLASS:
    case Type.INTERFACE:
      return ArrayMethods.generateDelRefName(arrayType.getSymbolID());
    case Type.ENUM:
      return "sidl_long__array_deleteRef";
    default:
      return "sidl_" + arrayType.getTypeString() + "__array_deleteRef";
    }
  }

  public static String getExceptionType() {
    return s_exceptionType;
  }

  /**
   * Creates a Method that represents the static Exec method
   */
  public static Method getSExecMethod(Context context) 
    throws CodeGenerationException 
  {
    Method m_exec = new Method(context);
    String[] cmmnt = {"static Exec method for reflexity."};
    m_exec.setMethodName("_sexec");
    m_exec.setComment(new Comment(cmmnt));
    m_exec.setDefinitionModifier(Method.STATIC);
    m_exec.setReturnType(new Type(Type.VOID));
    Argument a = new Argument(Argument.IN, new Type(Type.STRING), 
                              "methodName");
    m_exec.addArgument(a);
    
    Symbol tmpSym = Utilities.lookupSymbol(context, "sidl.rmi.Call");
    a = new Argument(Argument.IN, new Type(tmpSym, context), 
                     "inArgs"); 
    m_exec.addArgument(a);  
    tmpSym = Utilities.lookupSymbol(context, "sidl.rmi.Return");
    a = new Argument(Argument.IN, new Type(tmpSym, context), 
                     "outArgs");
    m_exec.addArgument(a);
    return m_exec;
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
                         do_rarrays);
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
                                          boolean do_rarrays)
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
        if (obj_ptr) {
          m_self = getSymbolObjectPtr(id) + " " + self;
        } else {
          m_self = getObjectName(id) + " " + self;
        } 
      } else {
        m_self = self;
      }
    } else if (is_interface) {
      m_self = self + "->d_object";
    } else {
      m_self = s_self;
    }
    generateArguments(writer, context,
                      m_self, args, method.isStatic(), excVar,
                      returnType, add_type, obj_ptr, do_rarrays, false);
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
                      addType, objPtr, doRaw, deref_inout);
  }

  /**
   * Create a comment to describe the SIDL type for the C signature.
   * 
   * @param arg the argument to make a comment from
   * @return usually this is just the mode as a string. For arrays
   * and rarrays more information is returned.
   */
  public static String argComment(Argument arg)
  {
    final Type argType = arg.getType();
    if (Type.ARRAY == argType.getDetailedType()) {
      StringBuffer buf = new StringBuffer(arg.getModeString());
      if ( argType.isRarray()) {
        buf.append(" rarray[");
        Iterator i = argType.getArrayIndexExprs().iterator();
        while (i.hasNext()) {
          AssertionExpression ae = (AssertionExpression)i.next();
          buf.append(ae.accept(new CExprString(), null).toString());
          if (i.hasNext()) buf.append(',');
        }
        buf.append(']');
      }
      else {
        buf.append(' ');
        buf.append(argType.getTypeString());
      }
      return buf.toString();
    }
    else {
      return arg.getModeString();
    }
  }

  public static String getReturnString(Type type,
                                       Context context,
                                       boolean objPtr,
                                       boolean inStub)
    throws CodeGenerationException
  {
    return (Type.ENUM == type.getDetailedType())
      ? getEnumName(type.getSymbolID())
      : IOR.getReturnString(type, context, objPtr, inStub);
  }

  public static String
    getArgumentWithFormal(Argument arg,
                          Context context,
                          boolean objPtr,
                          boolean inStub,
                          boolean isExec)
    throws CodeGenerationException
  {
    final Type type = arg.getType();
    if (Type.ENUM == type.getDetailedType() ) {
      return getEnumName(type.getSymbolID()) +
        ((arg.getMode() != Argument.IN) ? "* " : " ") + 
        arg.getFormalName();
    }
    else {
      return IOR.getArgumentWithFormal(arg, context, objPtr, inStub, isExec);
    }
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
                                       List args, boolean isStatic,
                                       String excVar, Type returnType,
                                       boolean addType, boolean objPtr, 
                                       boolean doRaw, boolean deref_inout)
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
        writer.print("/* " + argComment(arg) + " */ ");
        if(deref_inout && arg.getMode() != Argument.IN) {
          writer.print(" *");
        }
        writer.print(C.getArgumentWithFormal(arg, context, objPtr, isRaw, false));
      } else if (isRaw) {
        if (arg.getMode() == Argument.INOUT) {
          writer.print("&");
        }
        writer.print(arg.getFormalName() + RAW_ARRAY_EXT);
      } else {
        if(deref_inout && arg.getMode() != Argument.IN) {
          writer.print(" *");
        }
        if ((arg.getMode() != Argument.IN) &&
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
        if (objPtr) {
          writer.print(s_exceptionObjPtr + " *");
        } else {
          writer.print(s_exceptionType + " *");
        } 
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
        writer.print(C.getReturnString(returnType, context, objPtr, false));
      }
      writer.print(" " + FUNCTION_RESULT);
    }
  }

  //wraps a plain data pointer for a raw array into a generic sidl data
  //structure by making up the necessary meta data. this is also used by the
  //C++ backend
  public static String wrapRawArrayFromStruct(LanguageWriter writer,
                                              String field_name,
                                              String struct_expr, 
                                              Type type,
                                              Context context) {
    if(type.isArray() && type.isRarray()) {
      final String temp_postfix = "_tmp";
      int int_type = type.getArrayType().getType();
      String sidl_array_name = IOR.getArrayNameWithoutAsterix(int_type);
      int dim = type.getArrayDimension();
      
      writer.println("int32_t " + field_name + "_lower[" + dim + "], " +
                     field_name + "_upper[" + dim + "], " +
                     field_name + "_stride[" + dim + "];\n");
      writer.println(sidl_array_name + " " + field_name + "_data;\n");
      writer.println(sidl_array_name + " *" + field_name + temp_postfix 
                     + " = &" + field_name + "_data;\n");
      
      List indices = type.getArrayIndexExprs();
      String init_func_name = IOR.getArrayNameForFunctions(int_type) + "_init";
      int x = 0;
      for(Iterator i = indices.iterator(); i.hasNext();++x) {
        AssertionExpression ae = (AssertionExpression) i.next();
        writer.println(field_name + "_lower[" + x + "] = 0;");
        writer.println(field_name + "_upper[" + x + "] = " + 
                       ae.accept(new CExprString(struct_expr + "->"), null).toString() + " - 1;\n");
      }
      writer.println(init_func_name + "(" + struct_expr + "->" + field_name + ", " + field_name 
                     + temp_postfix + ", " + type.getArrayDimension() + ", "  + field_name 
                     + "_lower, " + field_name + "_upper, " + field_name + "_stride);\n");
      return field_name + temp_postfix;
    }
    return new String("");
  }
  
}
