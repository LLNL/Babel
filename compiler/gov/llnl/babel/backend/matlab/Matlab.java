//
// File:        Matlab.java
// Package:     gov.llnl.babel.backend.python
// Revision:    @(#) $Revision: 7188 $
// Date:        $Date: 2011-09-27 11:38:42 -0700 (Tue, 27 Sep 2011) $
// Description: Matlab naming conventions shared by Matlab code generators
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

package gov.llnl.babel.backend.matlab;

import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeConstants;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.writers.LanguageWriterForC;
import gov.llnl.babel.backend.writers.LanguageWriterForMatlab;
import gov.llnl.babel.symbols.Argument;
import gov.llnl.babel.symbols.AssertionExpression;
import gov.llnl.babel.symbols.CExprString;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * This class defines some of the fundamental mapping rules for translating
 * a symbol in the sidl file into Python. These fundamental mapping rules
 * are used repeatedly in the generation of client and server side bindings,
 * so they are seperated into a class that can be shared by all Python code
 * generators.
 * </p>
 * <p>
 * Some of the particular features include:
 * <ul>
 * <li>Provide the header file name</li>
 * </ul>
 * </p>
 */
public class Matlab {
  private static final HashMap s_mxType   = new HashMap();

  static {
    s_mxType.put("void",       "");
    s_mxType.put("int",        "int32");
    s_mxType.put("long",       "int64");
    s_mxType.put("float",      "single");
    s_mxType.put("double",     "double");
    s_mxType.put("bool",       "logic");
    s_mxType.put("char",       "char");
    s_mxType.put("string",     "string");
  }

  /**
   * Generate the symbol name using the specified * symbol identifier. 
   * Simply replace the "." to "_". name.
   */
  public static String getSymbolName(SymbolID id) {
    return id.getFullName().replace('.', '_');
  }

  public static String getHeaderFile(SymbolID id) {
    return getSymbolName(id) + ".h";
  }

  /**
   * Generate the Matlab filename for the client using the specified
   * symbol identifier. 
   */
  public static String getClientMatFile(SymbolID id) {
    return getSymbolName(id) + ".m";
  }

  public static String getConstructorStubName(SymbolID id) {
    return "private_" + getSymbolName(id); 
  }

  public static String getConstructorStubFile(SymbolID id) {
    return  getConstructorStubName(id) + ".c";
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


  public static String getMethodStubFile(SymbolID id, Method method) {
    return  getFullMethodName(id, method) + ".c";
  }

  public static void writeMexFunctionSignature(LanguageWriterForC writer) {
    writer.println("void mexFunction(int nlhs, mxArray *plhs[],");
    writer.tab();
    writer.println("int nrhs, const mxArray *prhs[])");
    writer.backTab();
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
    //return getSymbolName(id);
    return IOR.getObjectName(id);
  }

 /**
  * Generate the argument list for Matlab method function.
  *
  */

  public static void generateMatArgumentList(String self, 
    LanguageWriterForMatlab writer, SymbolID id, Method method)
    throws CodeGenerationException
  {
    writer.print("(" + self);

    List args = null;
    args = method.getArgumentListWithOutIndices();
    if (args.size() > 0) {
        writer.print(", ");
    }

    /*  Output each argument in turn.  */
    for (Iterator a = args.iterator(); a.hasNext(); ) {
      Argument arg = (Argument) a.next();
      writer.print(arg.getFormalName());
      if (a.hasNext()) {
        writer.print(", ");
      }
    }
    writer.print(")");
  }


  public static void 
  generateCArgumentList(LanguageWriterForC writer, 
                        Context context,
                        String self, 
                        SymbolID id, Method method, 
                        boolean addType, boolean isStatic)
    throws CodeGenerationException
  {

    List args = null;
    //if (do_indices) {
    //  args = method.getArgumentListWithIndices();
    //} else {
      args = method.getArgumentListWithOutIndices();
    //}

    /*
     * If the method is not static, then it will begin with an
     * object reference.
     */
    if (!isStatic) {
      if(addType) { writer.print("/* in */ "); }
      writer.print(self);
    }else if (addType && (args.size() == 0)){
      writer.print("void");
    }

    if (args.size() > 0) {
        writer.print(", ");
    }

    /*  Output each argument in turn.  */
    for (Iterator a = args.iterator(); a.hasNext(); ) {
      Argument arg = (Argument) a.next();
      
      /* Add argument type  */
      if(addType) {
        writer.print("/* " + argComment(arg) + " */ ");
        writer.print(IOR.getArgumentWithFormal(arg, context) + " ");
			} else{
        writer.print(arg.getFormalName());
      }

      if (a.hasNext()) {
        writer.print(", ");
      }
    }
  }

/*
  public static String 
  getMexArgumentList(Method method, String p_str)
    throws CodeGenerationException
  {
    StringBuffer buf = new StringBuffer();

    List args = null;
    Argument arg = null;
    args = method.getArgumentListWithOutIndices();

    for (Iterator a = args.iterator(); a.hasNext(); ) {
      arg = (Argument) a.next();

      if (arg.getMode() == Argument.IN) {
        buf.append(", " + arg.getFormalName());
      } else { // argMode = INOUT or OUT
        buf.append(", " + p_str + arg.getFormalName());
			} 
    } 

    return buf.toString();
  }
*/

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

  /**
   * Generate a return string for the specified IOR type.
   */
  public static String getReturnType(Method method,
                                     Context context)
    throws CodeGenerationException
  {
    return IOR.getReturnString(method.getReturnType(), context, false, false);
  }
                                                                                             
  /**
   * Generate a return string for the specified SIDL type. 
   */
  public static String getSidlReturnType(Method method)
  {
    return method.getReturnType().getTypeName();
  }


  /**
   * Generate an return string for the Matlab type mapping to 
   * the specific SIDL Type. Currently supported SIDL types: 
   * int, long, float, double, bool. 
   */
  public static String getMxType(String sidlType)
  {
    return (String)s_mxType.get(sidlType);
  }


  /**
   * Generate a string which allocate space a character array.
   */
  public static String allocateStringSpace(String s_strSize)
  {
    StringBuffer buf = new StringBuffer();
    buf.append("mxCalloc(" + s_strSize + ", sizeof(char));" );
    return buf.toString();
  }


  /**
   * Generate a string which checks if a matrix has the proper class
   * to match the argument type in SIDL specification. 
   */
  public static String checkMxClass(
    Type argType, String mxName, int i, boolean isStatic)
  {
    StringBuffer buf = new StringBuffer();

    switch(argType.getDetailedType()) {

    case Type.BOOLEAN:
      buf.append(typeMismatchMsg(isStatic, i, mxName, "Logical"));
      break;   

    case Type.CHAR:
    case Type.STRING:
      buf.append(typeMismatchMsg(isStatic, i, mxName, "Char"));
      break;   

    case Type.DCOMPLEX:
      buf.append(typeMismatchMsg(isStatic, i, mxName, "Complex")); 
      buf.append(typeMismatchMsg(isStatic, i, mxName, "Double"));
      break;   

    case Type.FCOMPLEX:
      buf.append(typeMismatchMsg(isStatic, i, mxName, "Complex")); 
      buf.append(typeMismatchMsg(isStatic, i, mxName, "Single"));
      break;   

    /* add type checking later if necessary.  */
    case Type.DOUBLE:
    case Type.ENUM:
    case Type.INT:
    case Type.FLOAT:
    case Type.LONG:

    case Type.VOID:
    case Type.CLASS:
    case Type.INTERFACE:
    case Type.ARRAY:
    case Type.OPAQUE:
      break;   
    default:
    }

    return buf.toString();
  }


  /**
   * Generate a string of error message if prhs[] class does not match SIDL 
   * specification
   */
  private static String typeMismatchMsg(
    boolean isStatic, int indx, String arrayName, String clsType) 
  {
    StringBuffer buf = new StringBuffer();
    buf.append("\n"); 
    buf.append("if(!mxIs" + clsType +"(" + arrayName + "[" + indx + "])) {\n"); 
    if(!isStatic) {
        indx++;
    }
    buf.append("  mexErrMsgTxt(\"input argument #" + indx 
      + " must be "+ clsType + ".\");\n"); 
    buf.append("}\n"); 

    return buf.toString();
  }

  /**
   * Generate a string which creates a Mx matrix for the given
   * Matlab data type. 
   */
  public static String generatePlhsMatrix(
    Type t, int idx, String varName)
  {
    StringBuffer buf = new StringBuffer();

    buf.append("plhs[" + Integer.toString(idx) + "] = ");

    switch(t.getDetailedType()) {

    case Type.BOOLEAN:
      buf.append("mxCreateLogicalScalar(" + varName + ");\n");
      break;   

    case Type.CHAR:
      buf.append("mxCreateString(&_dummy_char);\n");
      buf.append("*mxGetChars(plhs[" + Integer.toString(idx) 
        + "]) = ");
      buf.append(varName + ";\n");
      break;   

    case Type.DCOMPLEX:
      buf.append("mxCreateDoubleMatrix(1, 1, mxCOMPLEX);\n");
      buf.append("*mxGetPr(plhs[" 
        + idx + "]) = *" + varName + ".real;\n");
      buf.append("*mxGetPi(plhs[" 
        + idx + "]) = *" + varName + ".imaginary;\n");
      break;   

    case Type.FCOMPLEX:
      buf.append("mxCreateNumericMatrix" 
        + "(1, 1, mxSINGLE_CLASS, mxCOMPLEX);\n");
      buf.append("*(float*) mxGetData(plhs[" + idx + "]) = " 
        + varName + ".real;\n");
      buf.append("*(float*) mxGetImagData(plhs[" + idx + "]) = " 
        + varName + ".imaginary;\n");
      break;   

    case Type.DOUBLE:
      buf.append("mxCreateDoubleMatrix(1, 1, mxREAL);\n");
      buf.append("*mxGetPr(plhs[" 
        + idx + "]) = " + varName + ";\n");
      break;   

    case Type.FLOAT:
      buf.append("mxCreateNumericMatrix" 
        + "(1, 1, mxSINGLE_CLASS, mxREAL);\n");
      buf.append("*(float *)mxGetData(plhs[" 
        + idx + "]) = " + varName + ";\n");
      break;   

    case Type.INT:
      buf.append("mxCreateNumericMatrix" + 
        "(1, 1, mxINT32_CLASS, mxREAL);\n");
      buf.append("*(int32_t *)mxGetData(plhs[" 
        + idx + "]) = " + varName + ";\n");
      break;   
    case Type.LONG:
      buf.append("mxCreateNumericMatrix" 
        + "(1, 1, mxINT64_CLASS, mxREAL);\n");
      buf.append("*(int64_t *)mxGetData(plhs[" 
        + idx + "]) = " + varName + ";\n");
      break;   
    case Type.STRING:
      buf.append("mxCreateString(" + varName +");\n");
      break;   
    case Type.ENUM:
      buf.append("mxCreateNumericMatrix" + 
        "(1, 1, mxINT32_CLASS, mxREAL);\n");
      buf.append("*(int32_t *)mxGetData(plhs[" 
        + idx + "]) = " + varName + ";\n");
      break;   
    case Type.VOID:
    case Type.CLASS:
    case Type.INTERFACE:
    case Type.ARRAY:
    case Type.OPAQUE:
         break;   
    default:
      /*
      throw new CodeGenerationException
        ("The " + t.getTypeString() +
         " type is not currently supported in Python.");
      */
    }
    return buf.toString();
  }

  /**
   * Generate an include file for a symbol.
   */

  public static void StubHeaderFiles(LanguageWriterForC writer)
  {
    writer.printlnUnformatted("#include \"babel_config.h\" ");
    writer.printlnUnformatted("#include \"mex.h\" ");
    writer.printlnUnformatted("#include <stddef.h>");
    writer.printlnUnformatted("#include <stdlib.h>");
  }

  public static void addInclude(LanguageWriterForC writer, 
                                String filename, 
                                boolean useGuard)
  {
     writer.generateInclude(filename, useGuard);
  }

  public static void StubNullDefine(LanguageWriterForC writer)
  {
    writer.printlnUnformatted("#ifndef NULL");
    writer.printlnUnformatted("#define NULL 0");
    writer.printlnUnformatted("#endif");
    writer.println();
  }

/*
  public static String getIncludeGuard(Symbol symbol,
                                       String modifier)
  {
    String fullname = symbol.getFullName().replace('.','_');
    StringBuffer buf = 
      new StringBuffer(2 + fullname.length() + modifier.length());
    buf.append(fullname)
      .append('_')
      .append(modifier.toUpperCase());
    return buf.toString();
  }

  public static String headerFilename(Symbol symbol,
                                      String modifier)
  {
    SymbolID id = symbol.getSymbolID();
    String name = id.getFullName().replace('.','_');
    StringBuffer buf = 
      new StringBuffer(name.length() + modifier.length() + 3);
    buf.append(name).append('_').append(modifier).append(".h");
    return buf.toString();
  }
*/

  /**
   * Return a string for the JNI native type corresponding to the specified
   * Java type.  Everything that is not a primitive type like integer or
   * boolean is converted into an JNI object.
   */
/*
  public static String getJNINativeType(String type) {
    String jni = (String) s_jni_arg.get(type);
    if (jni == null) {
      jni = "jobject";
    }
    return jni;
  }
*/





  public static String sourceFilename(Symbol symbol,
                                      String modifier)
  {
    SymbolID id = symbol.getSymbolID();
    String longname = id.getFullName().replace('.','_');
    StringBuffer buf = 
      new StringBuffer(longname.length() + modifier.length() + 3);
    buf.append(longname).append('_').append(modifier).append(".c");
    return buf.toString();
  }

/*
  public static String skelFilename(Symbol symbol,
                                    String modifier)
  {
    final String name = symbol.getFullName().replace('.','_');
    StringBuffer buf = 
      new StringBuffer(name.length() + modifier.length() + 3);
    buf.append(name).append('_').append(modifier).append(".c");
    return buf.toString();
  }

  public static String implFilename(Symbol symbol)
  {
    return symbol.getSymbolID().getShortName() + "_Impl.py";
  }

*/

  /**
   * Build a Python support object.
   */
/*
  public static String getCHeaderPath(Symbol symbol,
                                      String modifier)
  {
    return headerFilename(symbol, modifier);
  }

  public static LanguageWriterForC createCHeader(Symbol symbol,
                                                 String modifier,
                                                 String description,
                                                 Context context)
    throws CodeGenerationException
  {
    final SymbolID id = symbol.getSymbolID();
    final int type = symbol.getSymbolType();
    String filename = headerFilename(symbol, modifier);
    LanguageWriterForC lw = null;
    lw = new LanguageWriterForC
        (d_context.getFileManager().
        createFile(id, type, "PYMOD_HDRS", filename));
    lw.writeBanner(symbol, filename, CodeConstants.C_IS_IMPL, 
                   description);
    return lw;
  }
*/
   
  /**
   * Generate an IO stream to receive the C stub file for the Matlab clients.
   *
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception for problems during the code
   *    generation phase.
   *
   * Currently: setJavaStylePackageGeneration is set to false,
	 * need set to true later and does some work on adding new
   * matpath automatically for each created stub directory named after
   * sidl package name
   */

  public static LanguageWriterForC createStub(Symbol symbol,
                                              String description,
                                              Context context)
    throws CodeGenerationException
  {
    final boolean saveState = context.getFileManager().getJavaStylePackageGeneration();
    final SymbolID id = symbol.getSymbolID();
    final int type = symbol.getSymbolType();
    String filename = sourceFilename(symbol, "Mex");
    LanguageWriterForC lw = null;
    try {
      /*context.getFileManager().setJavaStylePackageGeneration(true); */
      context.getFileManager().setJavaStylePackageGeneration(false); // flat file structure
      lw = new LanguageWriterForC
        (context.getFileManager().createFile(id, type, "MATMEX_SRCS", filename)
         , context);
      lw.writeBanner(symbol, filename, CodeConstants.C_IS_NOT_IMPL, 
                     description);
    }
    finally {
      context.getFileManager().setJavaStylePackageGeneration(saveState);
    }
    return lw;
  }

  /**
   * Generate an IO stream to receive the C skeleton file for the Python
   * implementations.
   *
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception for problems during the code
   *    generation phase.
   */
/*
  public static LanguageWriterForC createSkel(Symbol symbol,
                                              String description,
                                              Context context)
    throws CodeGenerationException
  {
    final SymbolID id = symbol.getSymbolID();
    final int type = symbol.getSymbolType();
    String filename = skelFilename(symbol, "pSkel");
    LanguageWriterForC lw = null;
    lw = new LanguageWriterForC
      (context.getFileManager().createFile(id, type, "SKELSRCS", filename));
    lw.writeBanner(symbol, filename, CodeConstants.C_IS_NOT_IMPL,
                   description);
    return lw;
  }
*/

  /**
   * Generate an IO stream to receive the C skeleton file for the Python
   * implementations.
   *
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception for problems during the code
   *    generation phase.
   */
/*
  public static LanguageWriterForC createLaunch(Symbol symbol,
                                                String description)
    throws CodeGenerationException
  {
    String filename = skelFilename(symbol, "pLaunch");
    final SymbolID id = symbol.getSymbolID();
    final int type = symbol.getSymbolType();
    LanguageWriterForC lw = null;
    lw = new LanguageWriterForC
      (context.getFileManager().createFile(id, type, "LAUNCHSRCS", filename));
    lw.writeBanner(symbol, filename, CodeConstants.C_IS_NOT_IMPL,
                   description);
    return lw;
  }

*/

}
