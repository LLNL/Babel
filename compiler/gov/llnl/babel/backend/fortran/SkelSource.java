//
// File:        SkelSource.java
// Package:     gov.llnl.babel.backend.fortran
// Revision:    @(#) $Revision: 7421 $
// Description: Generate to allow the IOR to call FORTRAN implementations
//
// Copyright (c) 2000-2002, Lawrence Livermore National Security, LLC
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


package gov.llnl.babel.backend.fortran;

import gov.llnl.babel.BabelConfiguration;
import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeConstants;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.Utilities;
import gov.llnl.babel.backend.c.C;
import gov.llnl.babel.backend.fortran.Fortran;
import gov.llnl.babel.backend.fortran.StubSource;
import gov.llnl.babel.backend.mangler.FortranMangler;
import gov.llnl.babel.backend.mangler.NameMangler;
import gov.llnl.babel.backend.mangler.NonMangler;
import gov.llnl.babel.backend.writers.LanguageWriter;
import gov.llnl.babel.backend.writers.LanguageWriterForC;
import gov.llnl.babel.symbols.Argument;
import gov.llnl.babel.symbols.AssertionExpression;
import gov.llnl.babel.symbols.CExprString;
import gov.llnl.babel.symbols.Class;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Inverter;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.Type;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
  
/**
 * The purpose of this class is to generate the C skeleton code to support
 * sidl objects implemented in FORTRAN. The skeleton code makes the link
 * between the independent object representation (IOR) and the FORTRAN 
 * implementation of a sidl class.
 * 
 * The skeleton must map datatypes in C to datatypes in FORTRAN. It must
 * must also provide C functions to populate the static and object entry
 * point vectors for the IOR.
 */
public class SkelSource {

  private static final String s_ensureOrderConstant[] = {
    "sidl_general_order",
    "sidl_column_major_order",
    "sidl_row_major_order"
  };

  private static final String s_epv       = "epv";
  private static final String s_pre_epv   = "pre_epv";
  private static final String s_post_epv  = "post_epv";
  private static final String s_sepv      = "sepv";
  private static final String s_pre_sepv  = "pre_sepv";
  private static final String s_post_sepv = "post_sepv";

  /**
   * This string is prepended to the name of an argument to get the name of
   * a proxy variable. A proxy variable is a stand in for an argument that
   * has incompatible representations in the C and FORTRAN.
   */
  private static final String s_proxy = "_proxy_";

  /**
   * This string is prepended to the name of an argument to get the name of
   * a proxy variable. A proxy variable is a stand in for an argument that
   * has incompatible representations in the C and FORTRAN.
   */
  private static final String s_proxyTwo = "_alt_";

  /**
   * This string is prepended to the name of an argument to get the name of
   * a buffer variables for a CHAR argument.
   */
  private static final String s_buffer = "_buf_";

  /**
   * Store the name of the fundamental base exception type used in BABEL.
   */
  private static final String s_exceptionFundamentalType = 
    BabelConfiguration.getBaseExceptionType();

  /**
   * This stores the output device to be used when generating the skeleton.
   */
  private LanguageWriterForC d_writer;

  private NameMangler d_mang;
  
  private Context d_context;

  /**
   * Create an object to generate the skeleton code in C for a FORTRAN 
   * object.
   *
   * @param writer   the skeleton code is written to this device.
   * @exception java.security.NoSuchAlgorithmException
   *   problem with the name mangler.
   */
  public SkelSource(LanguageWriterForC writer,
                    Context context) 
    throws NoSuchAlgorithmException 
  {
    d_writer = writer;
    d_context = context;
    if (Fortran.needsAbbrev(context)) {
      d_mang =  new FortranMangler(AbbrevHeader.getMaxName(context), 
                                   AbbrevHeader.getMaxUnmangled(context));
    } else {
      d_mang  =  new NonMangler();
    }
  }

  /**
   * Generate the external declaration for the FORTRAN subroutine.
   * This uses C preprocessor macros to handle the various compiler
   * specific relationship between C and FORTRAN.
   *
   * @param name  the name of the method.
   * @param args  the extended argument list.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   */
  private void generateExtern(Method m, SymbolID id, List args)
    throws CodeGenerationException
  {
    String name;
    //For F90, we have to consider the flattened version if there is a
    //struct argument
    if(Fortran.isFortran90(d_context) && m.hasStruct()) {
      m = m.spawnF90Wrapper(args, null, false);
      args = m.getArgumentListWithIndices();
    }

    if(Fortran.hasBindC(d_context))
      name = Fortran.getBindCSkelName(id, m, d_context);
    else
      name = Fortran.getMethodImplName(id, m, d_mang, d_context);

    //generate the prototype
    StubSource.generateSignature(d_writer, name, args, d_context, m);
    d_writer.println(";");
  }

  /**
   * This function checks if the given argument corresponds to the builtin
   * name for the return value. In order to handle flattened struct
   * arguments, the name may have an arbitrary postfix attached. 
   *
   * @param argName  the name of the argument.
   */
  private boolean isRetval(String name) {
    //Note: The current code breaks for for arguments named "retval". I
    //guess we should fix that.
    return name.equals(Fortran.s_return);
  }
  
  /**
   * Write something to take the value of the arguments.
   *
   * @param argName  the name of the argument.
   * @param mode     the mode of the argument.
   */
  private void writeArgValue(String argName, int mode) {
    if (mode != Argument.IN && !isRetval(argName)) {
      d_writer.print("*");
    }
    d_writer.print(argName);
  }

  private String getArgValueStr(String argName, int mode) {
    return 
      ((mode != Argument.IN && !isRetval(argName)) 
       ? "*" : "" ) + argName;
  }

  /**
   * Returns an expression that evaluates to the address of the given rhs
   * expression (which refers to the value).
   */
  private String getAddressOfExpr(String rhs_expr) {
    if(rhs_expr.startsWith("*"))
      return rhs_expr.substring(1);
    else
      return "&" + rhs_expr;
  }
  
  /**
   * Write the argument list for the skeleton function.  The signature of
   * the skeleton function must match the declaration in the EPV.
   *
   * @param  args     the extended list of arguments.
   * @param  builtIn  the method is built-in (e.g., pre/post hooks) so
   *                  do want the return added to the argument list.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   */
  private void writeSkelArgs(List args, boolean builtIn) 
     throws CodeGenerationException 
  {
    int argsWritten = 0;
    Iterator i = args.iterator();
    boolean comma = false;
    while (i.hasNext() ) {
      Argument a = (Argument)i.next();
        if ( (!isRetval(a.getFormalName())) || (builtIn) )  {
          if (comma) {
            d_writer.println(",");
          }
          d_writer.print(IOR.getArgumentWithFormal(a, d_context, true, false, false));
          ++argsWritten;
          comma = true;
        }
    }
    if (argsWritten == 0) {
      d_writer.print("void");
    }
  }

  /**
   * Declare the proxy variables.  The proxy variable is a FORTRAN 
   * compatible stand in for the argument from the IOR.
   * 
   * @param argName   the name of the original argument.
   * @param argType   the type of the original argument.
   * @param argMode   the calling mode of the argument.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   */
  private void declareProxy(Argument a)
    throws CodeGenerationException
  {
    final String pre = "fortran.SkelSource.declareProxy: ";
    String argName = a.getFormalName();
    Type argType = a.getType();
    int argMode = a.getMode();
    
    switch(argType.getDetailedType()) {
    case Type.BOOLEAN:
      d_writer.println(Fortran.getFortranPrefix(d_context) + 
                       "_Bool " + s_proxy 
                       + argName + ";");
      break;
    case Type.ARRAY:
      if (Fortran.isFortran90(d_context)) {
        if (argType.isRarray()) {
          d_writer.println(getReturnString(argType) + " " + s_proxy + argName + " = 0;");
        } else {
          d_writer.println(Fortran.getFortranTypeInC(argType, d_context) 
                           + " " + s_proxy + argName + " = { 0 };");
          if (Fortran.hasDirectAccess(argType) && 
              (Argument.OUT != argMode)) {
            d_writer.println(getReturnString(argType) + " " + s_proxyTwo 
                             + argName + " = 0;");
          }
        }
      } else {
        d_writer.println((argType.isRarray() ? getReturnString(argType)
          : Fortran.getFortranTypeInC(argType,d_context)) +
                         " " + s_proxy + argName 
            + " = 0;");
      }
      break;
    case Type.CLASS:
    case Type.INTERFACE:
      d_writer.println("int64_t " + s_proxy + argName + " = 0;");
      break;
    case Type.OPAQUE:
      d_writer.println("int64_t " + s_proxy + argName + " = 0;");
      break;
    case Type.STRING:
      d_writer.println(Fortran.getFortranPrefix(d_context) +
                       "_STR_LOCAL(" + s_proxy 
        + argName + ");");
      break;
    case Type.CHAR:
      d_writer.printlnUnformatted(StubSource.charCheck(d_context));
      d_writer.println(Fortran.getFortranPrefix(d_context) + "_STR_LOCAL(" + s_proxy 
        + argName + ");");
      d_writer.println("char " + s_buffer + argName + "[2];");
      d_writer.printlnUnformatted("#endif");
      break;
    case Type.SYMBOL:
      throw new CodeGenerationException(pre + "Unable to process " 
                  + argType.getTypeString() + ' ' + argName);
      // fall through intended
    case Type.DCOMPLEX:
    case Type.DOUBLE:
    case Type.FCOMPLEX:
    case Type.FLOAT:
    case Type.ENUM:
    case Type.INT:
    case Type.LONG:
      if (isRetval(argName)) {
        d_writer.println(getReturnString(argType) + ' ' + s_proxy + argName
                         + ";");
      }
      break;
    case Type.STRUCT:
      if(Fortran.isFortran77(d_context)) {
        d_writer.println("int64_t " + s_proxy + argName + ";");
      }
      else if(isRetval(argName)) {
        d_writer.println(getReturnString(argType) + ' ' + s_proxy + argName);
      }
      break;
    default:
      if (isRetval(argName)) {
        d_writer.println(getReturnString(argType) + ' ' + s_proxy + argName
          + ";");
      }
      break;
    }
  }

  /**
   * Loop through the list of arguments and declare proxies for those that
   * need them.
   *
   * @param args the extended list of arguments.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   */
  private void declareProxies(List args) throws CodeGenerationException {
    Iterator i = args.iterator();
    while (i.hasNext()) 
      declareProxy((Argument)i.next());
  }

  private void declareIndexAndInit(Argument rawArray, Argument index, int dimen)
    throws CodeGenerationException
  {
    d_writer.print(Fortran.getFortranTypeInC(index.getType(), d_context) + ' ' 
      + index.getFormalName() + " = ");
    d_writer.print(CExprString.
                   toCString(Inverter.invertExpr
                             ((AssertionExpression)
                              rawArray.getType().
                              getArrayIndexExprs().get(dimen),
                              "sidlLength(" + 
                              getArgValueStr(rawArray.getFormalName(),
                                             rawArray.getMode()) +
                              ", " + dimen + ")",d_context)));
    d_writer.println(";");
  }

  private void declareIndices(Map indexArgs) throws CodeGenerationException {
    if (indexArgs != null) {
      Iterator i = indexArgs.keySet().iterator();
      while(i.hasNext()) {
        Collection choice = (Collection)indexArgs.get(i.next());
        if (choice.size() > 0) {
          Method.RarrayInfo info = (Method.RarrayInfo)choice.iterator().next();
          declareIndexAndInit(info.rarray, info.index, info.dim);
        }
      }
    }
  }

  /**
   * Provide an expression to provide the array value in a specified
   * ordering, if necessary.
   *
   * @param array      the type of the array.
   * @param rhs        a right-hand-side expression
   * @param mode       the argument mode
   */
  private void ensureArray(Type array, String rhs, int mode) {
    if (array.hasArrayOrderSpec()) {
      Type elemType = array.getArrayType();
      d_writer.println();
      d_writer.tab();
      d_writer.println(Fortran.getEnsureArray(elemType));
      d_writer.tab();
      d_writer.println(rhs + ",");
      d_writer.println(array.getArrayDimension() + ",");
      d_writer.println(s_ensureOrderConstant[array.getArrayOrder()] + ")");
      d_writer.backTab();
      d_writer.backTab();
    } else {
      d_writer.print(rhs);
    }
  }

  public static void borrowRarray(String lhs, Type argType, String rhs,
                                  String indexExprBase,
                                  String prefix,
                                  boolean withDecl,
                                  Context d_context,
                                  LanguageWriter d_writer)
    throws CodeGenerationException
  {
    int internalType = argType.getArrayType().getType();
    //String sidl_array_name = IOR.getArrayNameWithoutAsterix(internalType);
    int dim = argType.getArrayDimension();

    if (withDecl) {
      //declare and initialize some temporary datastructures
      d_writer.println("int32_t " + prefix + "_lower[" + dim + "], " 
                       + prefix + "_upper[" + dim + "], " 
                       + prefix + "_stride[" + dim + "];");
    }

    List indices = argType.getArrayIndexExprs();
    int x = 0;
    for(Iterator i = indices.iterator(); i.hasNext(); ++x) {
      AssertionExpression ae = (AssertionExpression) i.next();
      d_writer.println(prefix + "_lower[" + x + "] = 0;");
      d_writer.println(prefix + "_upper[" + x + "] = " + 
                       ae.accept(new CExprString(indexExprBase), null).toString() + "-1;");
      d_writer.println(prefix + "_stride[" + x + "] = 1;");
    }

    d_writer.println(lhs + " = " 
                     + "(" + (Fortran.isFortran77(d_context)
                              ? (Fortran.getFortranTypeInC(argType, d_context) + " *")
                              : IOR.getReturnString(argType, d_context, true, false))
                     + ")" 
                     + IOR.getArrayNameForFunctions(internalType)
                     + "_borrow(" + rhs + ", " 
                     + dim + ", "
                     + prefix + "_lower, "
                     + prefix + "_upper, "
                     + prefix + "_stride);\n");
              /*} else { // Argument.IN
                d_writer.println(s_proxy + prefix + " = " 
                                 + IOR.getArrayNameForFunctions(internalType)
                                 + "_createCol("
                                 + dim + ", "
                                 + prefix + "_lower, "
                                 + prefix + "_upper);");                

                d_writer.println("memcpy(" + s_proxy + prefix + "._ior_p->d_firstElement, "
                                 + (IOR.isFixedSizeRarray(argType) ? "&" : "") 
                                 + rhs + ", " 
                                 + IOR.getRarraySizeExpr(argType, getBase(rhs)) + ");\n");
                                 }*/
  }

  /**
   * Provide initial values for the proxy variables. For <code>in</code> and
   * <code>inout</code> arguments, this generates code to translate the
   * incoming value to a FORTRAN compatible version.
   *
   * @param a   The argument to be processed
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   */
  private void initializeProxy(Method m, Argument a, Map rhs_map)
    throws CodeGenerationException
  {
    final String pre = "fortran.SkelSource.initializeProxy: ";
    String argName = a.getFormalName();
    Type argType = a.getType();
    int mode = a.getMode();
    String rhs = (String)rhs_map.get(a.getFormalName());
    
    switch(argType.getDetailedType()) {
    case Type.ARRAY:
      if (mode != Argument.OUT) {
        Type dataType = argType.getArrayType();
        if (Fortran.isFortran90(d_context) && Fortran.hasDirectAccess(argType)) {
          if (argType.isRarray()) {
            if (a.hasAttribute("F90_flattened_struct_arg")) {
              borrowRarray(s_proxy + argName, argType, rhs, getBase(rhs),
                           argName, true, d_context, d_writer);           
            } else { 
              d_writer.print(s_proxy + argName + " = ");
              ensureArray(argType, rhs, mode);
              d_writer.println(";");
            }
          } else {
            d_writer.print(s_proxyTwo + argName + " = ");
            ensureArray(argType, rhs, mode);
            d_writer.println(";");
          
            d_writer.println("if (sidl_" + dataType.getTypeString() 
                             + "__array_convert2f90(" + s_proxyTwo + argName + ", " 
                             + argType.getArrayDimension() + ", &" + s_proxy + argName 
                             + ")) {");
            d_writer.tab();
            if (!argType.hasArrayOrderSpec()) {
              d_writer.println(s_proxyTwo + argName + " = " 
                               + Fortran.getEnsureArray(dataType) + s_proxyTwo + argName 
                               + ", " + argType.getArrayDimension() 
                               + ", sidl_column_major_order);");
              d_writer.println("if (sidl_" + dataType.getTypeString() 
                               + "__array_convert2f90(" + s_proxyTwo + argName + ", " 
                               + argType.getArrayDimension() + ", &" + s_proxy + argName 
                               + ")) {");
              d_writer.tab();
            }
            d_writer.writeCommentLine("We're S.O.L");
            d_writer.println("fprintf(stderr, \"convert2f90 failed: %p %d\\n\"," +
                             " (void*)" + getAddressOfExpr(rhs) + ", " +
                             argType.getArrayDimension() + ");");
            d_writer.println("abort(); /*NOTREACHED*/");
            if (!argType.hasArrayOrderSpec()) {
              d_writer.backTab();
              d_writer.println("}");
            }
            d_writer.backTab();
            d_writer.println("}");
          }
        } else {
          d_writer.print(s_proxy + argName + Fortran.arrayIOR(d_context) + 
                         " = (");
          if (!argType.isRarray()) {
            d_writer.print("(ptrdiff_t)");
          }
          ensureArray(argType, rhs, mode);
          d_writer.println(");");
        }
      }
      break;
    case Type.OPAQUE:
      if (mode != Argument.OUT) {
        d_writer.println(s_proxy + argName + " = ((ptrdiff_t)" + rhs + ");");
      }
      break;

    case Type.CLASS:
    case Type.INTERFACE:
      if (mode != Argument.OUT) {
        d_writer.println(s_proxy + argName + " = ((ptrdiff_t)" + rhs + ");");
      }
      break;
    case Type.BOOLEAN:
      d_writer.print(s_proxy + argName + " = ");
      if (mode != Argument.OUT) { 
        d_writer.println("(((" + rhs + ")== TRUE) ? " +
                         Fortran.getFortranPrefix(d_context) + "_TRUE " + 
                         ": " + Fortran.getFortranPrefix(d_context) + "_FALSE);"); 
      } else {
        d_writer.println(Fortran.getFortranPrefix(d_context) + "_TRUE;");
      }
      break;
    case Type.STRING:
      if ((mode == Argument.IN) || (mode == Argument.INOUT)) {
        d_writer.print(Fortran.getFortranPrefix(d_context) + "_STR_COPY(" + s_proxy 
          + argName + ", " + rhs);
        if (mode == Argument.IN) {
          d_writer.println(", 0);");
        } else {
          d_writer.println(", " + Fortran.getFortranPrefix(d_context) +"_STR_MINSIZE);");
          d_writer.println("free((void *)" + rhs + ");");
          d_writer.println(rhs + " = NULL;");
        }
      } else {
        d_writer.println(Fortran.getFortranPrefix(d_context) + "_STR_COPY(" + s_proxy 
          + argName + ", NULL, " + Fortran.getFortranPrefix(d_context) + "_STR_MINSIZE)"
          + ";");
      }
      break;
    case Type.CHAR:
      d_writer.printlnUnformatted(StubSource.charCheck(d_context));
      d_writer.println(Fortran.getFortranPrefix(d_context) + "_STR_LOCAL_LEN(" 
        + s_proxy + argName + ") = 1;");
      d_writer.println(Fortran.getFortranPrefix(d_context) + "_STR_LOCAL_STR(" 
        + s_proxy + argName + ") = " + s_buffer + argName + ";");
      d_writer.print(s_buffer + argName + "[0] = ");
      if (Argument.OUT != mode) {
        d_writer.println(rhs + ";");
      } else {
        d_writer.println("' ';");
      }
      d_writer.println(s_buffer + argName + "[1] = '\\0';");
      d_writer.printlnUnformatted("#endif");
      break;
    case Type.STRUCT:
      if(Fortran.isFortran77(d_context)) {
        if (mode == Argument.OUT && isRetval(argName)) {
          d_writer.println(s_proxy + argName +
                         " = ((ptrdiff_t)&" + Fortran.s_return + ");");
        }
        else {
          d_writer.println(s_proxy + argName + " = ((ptrdiff_t)" + argName + ");");
        }
      }
      break;
    case Type.SYMBOL:
      throw new CodeGenerationException(pre + "Unable to process " 
                  + argType.getTypeString() + ' ' + argName);
    }
  }

  /**
   * Loop through the argument list and write the code to initialize
   * the proxy variables.
   *
   * @param args  the list of arguments.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   */
  private void initializeProxies(Method m, List args, Map rhs_map)
    throws CodeGenerationException
  {
    Iterator i = args.iterator();
    while (i.hasNext()) {
      Argument a    = (Argument)i.next();
      initializeProxy(m, a, rhs_map);
    }
  }

  /**
   * List each normal argument for the FORTRAN subroutine call.
   *
   * @param args   the list of arguments.
   */
  private void listNormalArguments(List args, Map rhs_map) 
    throws CodeGenerationException
  {
    Iterator i = args.iterator();
    while (i.hasNext()) {
      Argument a    = (Argument)i.next();
      Type     t    = a.getType();
      String   name = a.getFormalName();
      String   rhs  = getArgValueStr(name, a.getMode());
      if(rhs_map != null) rhs = (String) rhs_map.get(name);
      
      if (StubSource.hasProxy(t, d_context) || isRetval(name)) {
        switch(t.getDetailedType()) {
        case Type.STRING:
          d_writer.println(Fortran.getFortranPrefix(d_context) + "_STR_LOCAL_ARG(" 
                           + s_proxy + name + ")");
          d_writer.println(Fortran.getFortranPrefix(d_context) + "_STR_NEAR_LEN(" 
                           + s_proxy + name + ")");
          break;
        case Type.CHAR:
          d_writer.printlnUnformatted(StubSource.charCheck(d_context));
          d_writer.println(Fortran.getFortranPrefix(d_context) + "_STR_LOCAL_ARG(" 
                           + s_proxy + name + ")");
          d_writer.println(Fortran.getFortranPrefix(d_context) + "_STR_NEAR_LEN(" 
                           + s_proxy + name + ")");
          d_writer.printlnUnformatted("#else");
          d_writer.println("&" + s_proxy + name);
          d_writer.printlnUnformatted("#endif");
          break;
        case Type.ARRAY:
          if (t.isRarray()) {
            if (Fortran.isFortran77(d_context))  d_writer.print("(int64_t*)");
            d_writer.print(s_proxy + name + "->d_firstElement");
            break;
          }
          // fall through to default intended
        default:
          d_writer.print("&" + s_proxy + name);
          break;
        }
      } else {
        d_writer.print(getAddressOfExpr(rhs));
      }
      
      if (i.hasNext()) d_writer.println(",");
    }
  }

  /**
   * Deal with characters as Function return types.  This should ONLY be called
   * if the F2003 binding is being used and we are writing a function in the fSkel
   * files.
   *
   * @param args  the list of arguments.
   */
  private void listStringLengthFuncArgs(List args)
  {
    Iterator i = args.iterator();
    while (i.hasNext()) {
      Argument a    = (Argument)i.next();
      Type     t    = a.getType();
      String   name = a.getFormalName();
      if (!isRetval(name)){
        switch (t.getDetailedType()) {
          case Type.STRING:
            d_writer.println("#error Strings not supported as F03 function return types");
            break;
          case Type.CHAR:
            d_writer.println("#error Characters not supported as F03 function return types");
            break;
        }
      }
    }
  }

  /**
   * On some compilers, the string lengths are passed as extra arguments at
   * the end of the argument list.  List the length of each string argument.
   *
   * @param args  the list of arguments.
   */
  private void listStringLengthArguments(List args)
  {
    Iterator i = args.iterator();
    while (i.hasNext()) {
      Argument a    = (Argument)i.next();
      Type     t    = a.getType();
      String   name = a.getFormalName();
      switch (t.getDetailedType()) {
      case Type.STRING:
        d_writer.println();
        d_writer.print(Fortran.getFortranPrefix(d_context) + "_STR_FAR_LEN(" + s_proxy 
          + name + ")");
        break;
      case Type.CHAR:
        d_writer.println();
        d_writer.printlnUnformatted(StubSource.charCheck(d_context));
        d_writer.println(Fortran.getFortranPrefix(d_context) + "_STR_FAR_LEN(" + s_proxy
          + name + ")");
        d_writer.printlnUnformatted("#endif");
        break;
      }
    }
  }

  /**
   * Generate the code to call the FORTRAN implementation method.
   * This is complicated by the fact that the signature for a FORTRAN
   * method is compiler dependent. This generated code uses
   * a combination of preprocessor macros to handle compiler
   * specific changes.
   *
   * @param methodName   the name of the FORTRAN subroutine.
   * @param args         the extended list of arguments.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   */
  private void callMethod(SymbolID id, Method m, List args, Map rhs_map)
    throws CodeGenerationException
  {
    boolean with_bindc = Fortran.hasBindC(d_context);

    if(with_bindc) {
      d_writer.print(Fortran.getBindCSkelName(id, m, d_context) +  "(");
      for(Iterator I = args.iterator(); I.hasNext(); ) {
        String name = ((Argument)I.next()).getFormalName();
        if(name.equals(Fortran.s_return)) /*retval is a struct, in all cases*/
          d_writer.print("&" + name);
        else
          d_writer.print(name);
        if(I.hasNext()) d_writer.print(", ");
      }
      d_writer.println(");");
    }
    else {
      String methodName = Fortran.getMethodImplName(id, m, d_mang, d_context);
      d_writer.println(Fortran.getFortranSymbol(d_context) + "(" +
                       methodName.toLowerCase() + "," +
                       methodName.toUpperCase() + "," +
                       methodName + ")(");
      d_writer.tab();
      listNormalArguments(args, rhs_map);
      listStringLengthArguments(args);
      d_writer.println(");");
      d_writer.backTab();
    }
  }

  /**
   * When dealing with a method that throws exceptions, generate code to
   * check whether an exception has occured.  The generated code will
   * process the exception if it has occured.
   * 
   * @param method   details of the method.
   */
  private void checkExceptionBlock(Method m) throws CodeGenerationException {
    if (!m.getThrows().isEmpty()) {
      Symbol symbol = Utilities.lookupSymbol(d_context, 
                                             s_exceptionFundamentalType);
      d_writer.println("if (" + s_proxy + Fortran.s_exception + ") {");
      d_writer.tab();
      /* add assignment to lessen compiler warnings about unassigned variables */
      switch (m.getReturnType().getDetailedType()) {
      case Type.VOID: 
	    break;
      case Type.BOOLEAN: 
        d_writer.println(Fortran.s_return + " = FALSE;");
	    break;
      case Type.INT: 
      case Type.LONG: 
        d_writer.println(Fortran.s_return + " = 0;");
	    break;
      case Type.DOUBLE: 
      case Type.FLOAT: 
        d_writer.println(Fortran.s_return + " = 0.0;");
	    break;
      case Type.DCOMPLEX:
      case Type.FCOMPLEX:
        d_writer.println(Fortran.s_return + ".real = 0;");
        d_writer.println(Fortran.s_return + ".imaginary = 0;");
      break;
      case Type.STRUCT:
        /* structs are set to NULL when declared */
      break;
      default:
          /* do nothing */
      break;
      }
      d_writer.println("*" + Fortran.s_exception + " = (" 
        + IOR.getSymbolType(symbol) + ")");
      d_writer.tab();
      d_writer.println("(ptrdiff_t)(" + s_proxy + Fortran.s_exception 
        + ");");
      d_writer.backTab();
      d_writer.backTab();
      d_writer.println("} else {");
      d_writer.tab();
    }
  }

  /**
   * When dealing with a method that throws exceptions, this method 
   * generates code to close the exception block.
   *
   * @param m  details of the method.
   */
  private void endExceptionBlock(Method m) {
    if (!m.getThrows().isEmpty()) {
      d_writer.backTab();
      d_writer.println("}");
    }
  }

  private static String getBase(String lhs) {
    // FIXME: this is broken
    // use lhsMap instead!
    return lhs.substring(0, lhs.lastIndexOf('.')+1);
  }

  /**
   * For <code>out</code> and <code>inout</code> parameters, convert the
   * values from the proxy variables into something that the 
   * IOR can handle.
   * 
   * @param argType   the type of the argument.
   * @param argName   the original name of the formal argument.
   * @param mode      the mode of the argument.
   * @param lhs       a left-hand-side expression used to assign the result value
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   */
  public void doOutArg(Argument arg, String lhs)
    throws CodeGenerationException
  {

    Type argType = arg.getType();
    String argName = arg.getFormalName();
    int mode = arg.getMode();

    final String pre = "fortran.SkelSource.doOutArg: ";
    switch(argType.getDetailedType()) {
    case Type.ARRAY:
      if (Argument.IN != mode &&
          // F90: Won't work for fix rarrays struct members
          (!(arg.hasAttribute("F90_flattened_struct_arg") && argType.isRarray()))) {
        String modName = "(" + getReturnString(argType) + ")" 
                             + (argType.isRarray() 
                                ? ( s_proxy + argName )
                                : ("(ptrdiff_t)(" + s_proxy + argName 
                                   + Fortran.arrayIOR(d_context) + ")"));
     
        d_writer.print(lhs + " = (" + getReturnString(argType) + ")");
        ensureArray(argType, modName, Argument.IN);
        d_writer.println(";");
      
        if (argType.hasArrayOrderSpec()) {
          d_writer.print(Fortran.getDelRefArray(argType.getArrayType()) 
                         + (argType.isRarray() ? "(" : "(ptrdiff_t)("));
          d_writer.println(s_proxy + argName 
                           + (argType.isRarray() ? "" :
                              Fortran.arrayIOR(d_context))+ "));");
        }
        if (argType.isRarray()) {
          d_writer.println(s_proxy + argName + " = NULL;");
        }
      }
      break;
    case Type.OPAQUE:
    case Type.CLASS:
    case Type.INTERFACE:
      d_writer.print(lhs + " = (" + getReturnString(argType) + ")(ptrdiff_t)");
      d_writer.println(s_proxy + argName + ";");
      break;
    case Type.BOOLEAN:
      d_writer.println(lhs + " = ((" + Fortran.getFortranPrefix(d_context) + "_TRUE == " 
        +  s_proxy + argName + ") ? TRUE : FALSE);");
      break;
    case Type.STRING:
      d_writer.println(lhs + " = sidl_trim_trailing_space(");
      d_writer.tab();
      d_writer.println(Fortran.getFortranPrefix(d_context) + "_STR_LOCAL_STR(" 
        + s_proxy + argName + "),(ptrdiff_t)");
      d_writer.println(Fortran.getFortranPrefix(d_context) + "_STR_LOCAL_LEN(" 
        + s_proxy + argName + "));");
      d_writer.backTab();
      d_writer.println(Fortran.getFortranPrefix(d_context) + "_STR_LOCAL_STR(" 
        + s_proxy + argName + ") = NULL;");
      break;
    case Type.CHAR:
      d_writer.printlnUnformatted(StubSource.charCheck(d_context));
      d_writer.println(lhs + " = *(" + Fortran.getFortranPrefix(d_context) +
                       "_STR_LOCAL_STR(" + s_proxy + argName + "));");
      d_writer.printlnUnformatted("#endif");
      break;
    case Type.SYMBOL:
      throw new CodeGenerationException(pre + "Unable to process " 
                  + argType.getTypeString() + ' ' + argName);
    case Type.STRUCT:
      if (Fortran.hasBindC(d_context)) {
        if (argName.equals(Fortran.s_return)) {
          d_writer.println(lhs + " = &" + s_proxy + argName + ";");
        }
      }
      break;
    default:
      if (argName.equals(Fortran.s_return)) {
        d_writer.println(lhs + " = " + s_proxy + argName + ";");
      }
      break;
    }
  }

  private void releaseIncomingRefs(List args) 
    throws CodeGenerationException 
  {
    Iterator i = args.iterator();
    while (i.hasNext()) {
      Argument  a    = (Argument)i.next();
      Type argType = a.getType();

      if (argType.hasArrayOrderSpec()) {
        int mode = a.getMode();      
        final String argName = a.getFormalName();

        if ((mode == Argument.INOUT) && argType.isRarray()) {
          d_writer.writeCommentLine("release proxy references");
          d_writer.println(Fortran.getDelRefArray(argType.getArrayType()) +
                           s_proxy + argName + ");");
        }

        if (mode == Argument.IN) {
          d_writer.writeCommentLine("release incoming references");
          
          d_writer.print(Fortran.getDelRefArray(argType.getArrayType()) 
                         + (argType.isRarray() ? "(" : "(ptrdiff_t)("));
          d_writer.println(s_proxy + argName 
                           + (argType.isRarray() ? "" :
                              Fortran.arrayIOR(d_context))+ "));");
        }
      }
    }
  }

  /**
   * Loop through the list of arguments and generate code to
   * process the <code>out</code> and <code>inout</code> parameters.
   *
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   */
  private void processOutgoing(List args, Map rhs_map)
    throws CodeGenerationException
  {
    Iterator i = args.iterator();
    while (i.hasNext()) {
      Argument  a    = (Argument)i.next();
      final Type t   = a.getType();
      final int mode = a.getMode();
      if ((mode != Argument.IN) || t.hasArrayOrderSpec()) {
        String lhs = rhs_map == null ?
          getArgValueStr(a.getFormalName(), a.getMode()) :
          (String) rhs_map.get(a.getFormalName());
        doOutArg(a, lhs);
      }
    }
  }

  /**
   * Free resources that were temporarily allocated for the method call.
   * For example, free the memory allocated for a string.
   *
   * @param args  the extended list of arguments.
   */
  private void freeResources(Method m, List args) {
    Iterator i = args.iterator();
    while (i.hasNext()) {
      Argument a    = (Argument)i.next();
      String   name = a.getFormalName();
      Type     t    = a.getType();
      if (t.getDetailedType() == Type.STRING){
        d_writer.println("free((void *)" + Fortran.getFortranPrefix(d_context) 
          + "_STR_LOCAL_STR(" + s_proxy + name + "));");
      }
    }
    if ((Fortran.isFortran90(d_context)) &&
        m.getLongMethodName().equals(IOR.getBuiltinName(IOR.DESTRUCTOR))) {
      d_writer.println("if (self->d_data) free(self->d_data);");
    }
  }

  /**
   * Declare a variable to hold the return value of the method.
   * 
   * @param t  the type of the method's return value.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   */
  private void declareReturn(Type t) throws CodeGenerationException {
    if (Type.VOID != t.getDetailedType()) {
      switch (t.getDetailedType()) {
        case Type.STRUCT:
          if(Fortran.hasBindC(d_context)) {
            d_writer.print(getReturnString(t) + " " + Fortran.s_return+" = { 0 }");
            break;
          }
          //intended fallthrough
        default:
          d_writer.print(getReturnString(t) + " " + Fortran.s_return);
          if(Utilities.isPointer(t)) d_writer.print(" = NULL");
          if(t.isStruct()) d_writer.print(" = { 0 }");
          break;
        }

      d_writer.println(";"); 
    }
  }

  /**
   * The statement that actually returns the return value of the method.
   * 
   * @param t  the type of the method's return value.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   */
  private void addReturnStatement(Type t) throws CodeGenerationException {
    if (Type.VOID != t.getDetailedType())
      d_writer.println("return " + Fortran.s_return + ";");
  }

  /**
   * This manages the generation of a skeleton function for a particular
   * method.
   * 
   * @param args   the extended argument list for the method.
   * @param argsWithInidices the extended argument list for the method plus
   *                         the index variables.
   * @param m      other information about the method.
   * @param id     the name of the symbol who owns the method.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   */
  private void generateMethod(List args, List argsWithIndices, Method m,
                              SymbolID id)
    throws CodeGenerationException
  {
    boolean with_bindc = Fortran.hasBindC(d_context);
    Type ret_type = m.getReturnType();

    d_writer.println("static " + getReturnString(ret_type));
    d_writer.println(Fortran.getMethodSkelName(id, m) + "(");
    d_writer.tab();
    writeSkelArgs(args, m.isBuiltIn());
    d_writer.println(")");
    d_writer.backTab();
    d_writer.println("{");
    d_writer.tab();

    //If this is not one of the built-in methods (e.g., pre/post hooks),
    //then be sure to declare the return type.
    if(!m.isBuiltIn()) declareReturn(ret_type);
    
    //for Fortran 90 with struct arguments, we actually end up calling a
    //flattened wrapper function; We precompute a map of Arguments to rhs
    //expressions that allows us to re-use the rest of the codegeneration
    //infrastructure
    Map rhs_map = new HashMap();
    if(Fortran.isFortran90(d_context) && m.hasStruct()) {
      m = m.spawnF90Wrapper(args, rhs_map, false);
      args = m.getArgumentList();
      argsWithIndices = m.getArgumentListWithIndices();
    }
    else {
      //compute a sensible default mapping
      for(Iterator I  = argsWithIndices.iterator(); I.hasNext(); ) {
        Argument a = (Argument) I.next();
        rhs_map.put(a.getFormalName(),
                    getArgValueStr(a.getFormalName(), a.getMode()));
      }
    }

    //for Bind(C), all the actual work is done in Fortran
    if(with_bindc) { // F2003
      callMethod(id, m, argsWithIndices, null);
      addReturnStatement(ret_type);
    }
    else { // F77, F90
      d_writer.writeCommentLine("declare proxies");
      declareProxies(args);
      d_writer.writeCommentLine("declare indices");
      declareIndices(m.getRarrayInfo());
      d_writer.writeCommentLine("initialize proxies");
      initializeProxies(m, args, rhs_map);
      d_writer.writeCommentLine("call method");
      callMethod(id, m, argsWithIndices, rhs_map);
      d_writer.writeCommentLine("check exception block");
      checkExceptionBlock(m);
      d_writer.writeCommentLine("process outgoing parameters");
      processOutgoing(args, rhs_map);
      d_writer.writeCommentLine("end exception block");
      endExceptionBlock(m);
      releaseIncomingRefs(args);
      freeResources(m, args);
      addReturnStatement(ret_type);
    }
    d_writer.backTab();
    d_writer.println("}");
  }

  /**
    * Generate a return string for the specified SIDL type.  Most
    * of the SIDL return strings are listed in the static structures defined
    * at the start of this class.  Symbol types and array types require
    * special processing.
    */
  public String getReturnString(Type type)
     throws CodeGenerationException
   {
     return IOR.getReturnString(type, d_context, true, false);
   }

  /**
   * Extend the argument list to include implicit arguments, generate the
   * extern declaration for the FORTRAN subroutine and then generate the
   * skeleton code for the method.
   * 
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   */
  private void extendAndGenerate(Method m, SymbolID id)
    throws CodeGenerationException
  {
    //For Bind(C), we only need a C-skeleton if we return a struct by value
    if(!Fortran.hasBindC(d_context) || Fortran.needsCSkel(m, d_context)) { 
      List extendedArgs = StubSource.extendArgs(id, m, d_context, false);
      List extendedArgsWithIndices = StubSource.extendArgs(id, m, d_context,
                                                           true);
      d_writer.println();
      generateExtern(m, id, extendedArgsWithIndices);
    
      d_writer.println();
      generateMethod(extendedArgs, extendedArgsWithIndices, m, id);
    }
  }

  /**
   * Add the implicit builtin methods to the list of methods. There are
   * implicit functions for the constructor and destructor.
   * 
   * @param ext   the extendable object.
   */
  public static Collection extendMethods(Extendable ext, Context d_context)
    throws CodeGenerationException {
    Collection localMethods = ext.getMethods(false);
    final SymbolID id = ext.getSymbolID();
    ArrayList  extendedMethods = new ArrayList(localMethods.size()+2);
    extendedMethods.add(IOR.getBuiltinMethod(IOR.CONSTRUCTOR, id, d_context));
    if (!Fortran.isFortran90(d_context) && !Fortran.hasBindC(d_context)) {
      extendedMethods.add(IOR.getBuiltinMethod(IOR.CONSTRUCTOR2, id, d_context ));
    }
    extendedMethods.add(IOR.getBuiltinMethod(IOR.DESTRUCTOR, id, d_context));
    extendedMethods.addAll(localMethods);
    return extendedMethods;
  }

  /**
   * Write a function to initialize entries in the static entry point vector
   * for a particular class. This will generate an assignment statement for
   * each <code>static</code> method defined locally in the class.
   * 
   * @param cls    the class for which an routine will be written.
   * @param writer the output writer to which the routine will be written.
   */
  private static void writeInitializeSEPV(Class              cls, 
                                          Collection         methods,
                                          LanguageWriterForC writer,
                                          Context            context)
     throws CodeGenerationException
  {
    SymbolID id = cls.getSymbolID();
    boolean with_bindc = Fortran.hasBindC(context);
    
    writer.openCxxExtern();

    if(with_bindc) {
      writer.println();
      writer.print("void " + IOR.getSetSEPVName(id) +
                   "_bindc(" + IOR.getSEPVName(id) + " *");
      if (IOR.generateHookMethods(cls, context)) {
        writer.print("," + IOR.getPreSEPVName(id) + " *"  +
                     "," + IOR.getPostSEPVName(id) + " *");
      }
      writer.println(");");
    }
    
    writer.println("void");
    writer.print(IOR.getSetSEPVName(id) + "(" + IOR.getSEPVName(id) + " * ");
    writer.print(s_sepv);
    if (IOR.generateHookMethods(cls, context)) {
      writer.println(",");
      writer.tab();
      writer.println(IOR.getPreSEPVName(id) + " *" + s_pre_sepv + ", ");
      writer.println(IOR.getPostSEPVName(id) + " *" + s_post_sepv + ") ");
      writer.backTab();
    } else {
      writer.print(") ");
    } 
    writer.println("{");
    writer.tab();

    if(with_bindc) {
      writer.print(IOR.getSetSEPVName(id) + "_bindc(" + s_sepv);
      if(IOR.generateHookMethods(cls, context))
        writer.print(", " + s_pre_sepv + ", " + s_post_sepv);
      writer.println(");");
    }
    
    Iterator i = methods.iterator();
    while (i.hasNext()) {
      Method m = (Method)i.next();
      if (m.isStatic()) {
        initializeMethodPointer(writer, m, cls, true, context);
      }
    }
    writer.backTab();
    writer.println("}");
    writer.closeCxxExtern();
    writer.println();
  }

  private void writeDataAccess(SymbolID id)
    throws java.io.UnsupportedEncodingException
  {
    String getData = d_mang.shortName(id.getFullName(), "_get_data", 
                                      Fortran.getMethodSuffix(d_context));
    String setData = d_mang.shortName(id.getFullName(), "_set_data", 
                                      Fortran.getMethodSuffix(d_context));
    String humanGetName = id.getFullName().replace('.','_') 
                          + "__get_data" + Fortran.getMethodSuffix(d_context);
    String humanSetName = id.getFullName().replace('.','_') 
                          + "__set_data" + Fortran.getMethodSuffix(d_context);
    d_writer.beginBlockComment(false);
    d_writer.println("The FORTRAN impl calls this to get the data pointer.");
    d_writer.println("This function name may be a mangled form of");
    d_writer.println(humanGetName);
    d_writer.endBlockComment(false);
    d_writer.println("void");
    d_writer.println(Fortran.getFortranSymbol(d_context) + "(" + getData.toLowerCase() 
      + ',');
    d_writer.tab();
    d_writer.println(getData.toUpperCase() + ',');
    d_writer.println(getData + ')');
    d_writer.print("(int64_t *object, ");
    d_writer.println(Fortran.isFortran90(d_context) ? "void *data)" : "int64_t *data)");
    d_writer.backTab();
    d_writer.println("{");
    d_writer.tab();
    d_writer.println(IOR.getObjectName(id) + " *local = ");
    d_writer.tab();
    d_writer.println("((" + IOR.getObjectName(id) + " *)(ptrdiff_t)*object);");
    d_writer.backTab();
                     
    if(Fortran.isFortran90(d_context)) {
      d_writer.println("if (local && local->d_data) {");
      d_writer.tab();
      d_writer.println("memcpy(data, local->d_data, SIDL_F90_POINTER_SIZE);");
      d_writer.backTab();
      d_writer.println("} else {");
      d_writer.tab();
      d_writer.println("/* _get_data called before _set_data */");
      d_writer.printlnUnformatted("fputs(\"Babel:" + humanGetName 
        + " called before " + humanSetName + "! FATAL ERROR (unitialized "
        + "pointer)!\", stderr);");
      d_writer.println("abort();/*NOTREACHED*/");
      d_writer.backTab();
      d_writer.println("}");
    } else {
      d_writer.println("*data = local ? (ptrdiff_t)(local->d_data) : "
        + "(ptrdiff_t)0;");
    }
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();

    d_writer.beginBlockComment(false);
    d_writer.println("The FORTRAN impl calls this to set the data pointer.");
    d_writer.println("This function name may be a mangled form of");
    d_writer.println(humanSetName);
    d_writer.endBlockComment(false);
    d_writer.println("void");
    d_writer.println(Fortran.getFortranSymbol(d_context) + "(" + setData.toLowerCase() 
      + ',');
    d_writer.tab();
    d_writer.println(setData.toUpperCase() + ',');
    d_writer.println(setData + ')');
    d_writer.print("(int64_t *object, ");
    d_writer.println(Fortran.isFortran90(d_context) ? "void *data)" : "int64_t *data)");
    d_writer.backTab();
    d_writer.println("{");
    d_writer.tab();
    d_writer.println(IOR.getObjectName(id) + " *local = ");
    d_writer.tab();
    d_writer.println("((" + IOR.getObjectName(id) + " *)(ptrdiff_t)*object);");
    d_writer.backTab();
    d_writer.println("if (local) {");
    d_writer.tab();
    // I decided not to throw a MemAlloc exception here, since the malloc
    // already has a failure mode, and no good way to throw exceptions.
    if(Fortran.isFortran90(d_context)) {
      d_writer.println("if (!(local->d_data)) {");
      d_writer.tab();
      d_writer.println("local->d_data = malloc(SIDL_F90_POINTER_SIZE);");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println("if (local->d_data) {");
      d_writer.tab();
      d_writer.println("memcpy(local->d_data, data, SIDL_F90_POINTER_SIZE);");
      d_writer.backTab();
      d_writer.println("} else {");
      d_writer.tab();
      d_writer.printlnUnformatted("fputs(\"" + humanSetName + " failed in a "
        + "malloc call!\", stderr);");
      d_writer.backTab();
      d_writer.println("}");
    } else {
      d_writer.println("local->d_data = (void *)(ptrdiff_t)*data;");
    }
    d_writer.backTab();
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  /**
   * Write the function assignment for the specified var and method name
   * to the specified value.
   *
   * @param writer     the output writer to which the assignment statement
   *                   is written.
   * @param var        the pointer variable representing the EPV.
   * @param mname      the method name.
   * @param value      the desired value, or RHS.
   */
  private static void writeMethodAssignment(LanguageWriter writer, String var,
                                            String mname, String value)
  {
    writer.println(var + "->" + mname + " = " + value + ";");
  }

  /**
   * Include initializations of pre- and post-method hooks.
   * 
   * @param writer     the output writer to which the assignment statement
   *                   is written.
   * @param m          a description of the method to initialize
   * @param cls        the class that owns the method
   * @param doStatic   TRUE if initializing a static epv; FALSE otherwise
   */
  private static void initializePointers(LanguageWriter writer, 
                                         Method         m,
                                         Class          cls,
                                         boolean        doStatic,
                                         Context        context)
     throws CodeGenerationException
  {
    final SymbolID id       = cls.getSymbolID();
    final String methodName = m.getLongMethodName();
    final String ename      = IOR.getVectorEntry(methodName);
    String sname            = Fortran.getMethodSkelName(id, m);
    String epvStr           = doStatic ? s_sepv : s_epv;

    if(IOR.generateHookMethods(cls, context) && (!m.isBuiltIn()) ) {
      String preEpv  = doStatic ? s_pre_sepv : s_pre_epv;
      String postEpv = doStatic ? s_post_sepv : s_post_epv;
      Method hook    = m.spawnPreHook();
      if(!Fortran.hasBindC(context) || Fortran.needsCSkel(hook, context))
        writeMethodAssignment(writer, preEpv, 
                              IOR.getVectorEntry(hook.getLongMethodName()), 
                              Fortran.getMethodSkelName(id, hook));
      if(!Fortran.hasBindC(context) || Fortran.needsCSkel(m, context))
        writeMethodAssignment(writer, epvStr, ename, sname);
      hook = m.spawnPostHook(false, false);
      if(!Fortran.hasBindC(context) || Fortran.needsCSkel(hook, context))
        writeMethodAssignment(writer, postEpv, 
                              IOR.getVectorEntry(hook.getLongMethodName()), 
                              Fortran.getMethodSkelName(id, hook));
    } else {
      if(!Fortran.hasBindC(context) || Fortran.needsCSkel(m, context))
        writeMethodAssignment(writer, epvStr, ename, sname);
    }
  }

  /**
   * Write an assignment statement to * initialize a particular membor of 
   * the entry point vector. 
   * 
   * @param writer     the output writer to which the assignment statement
   *                   is written.
   * @param m          a description of the method to initialize
   * @param cls        the class that owns the method
   * @param doStatic   TRUE if initializing a static epv; FALSE otherwise
   */
  private static void initializeMethodPointer(LanguageWriter writer, 
                                              Method         m,
                                              Class          cls,
                                              boolean        doStatic,
                                              Context        context)
     throws CodeGenerationException
  {
    if (doStatic) {
      initializePointers(writer, m, cls, doStatic, context);
    } else {
      switch (m.getDefinitionModifier()) {
        case Method.FINAL:
        case Method.NORMAL:
          initializePointers(writer, m, cls, doStatic, context);
          break;
        case Method.ABSTRACT:
          String methodName = m.getLongMethodName();
          writeMethodAssignment (writer, s_epv, IOR.getVectorEntry(methodName),
                                 "NULL");
          break;
        default:
          /* do nothing */
          break;
        }
    }
  }

  /**
   * Write a function to initialize entries in the entry point vector for
   * a particular class. This will generate an assignment statement for
   * each non-<code>static</code> method defined locally in the class.
   * 
   * @param cls    the class for which an routine will be written.
   * @param writer the output writer to which the routine will be written.
   */
  private static void writeInitializeEPV(Class              cls,
                                         Collection         methods,
                                         LanguageWriterForC writer, 
                                         boolean            isSuper,
                                         Context            context) 
     throws CodeGenerationException
  {
    SymbolID id = cls.getSymbolID();
    boolean with_bindc = Fortran.hasBindC(context);
      
    writer.openCxxExtern();

    if(with_bindc) {
      writer.println();
      writer.print("void " + IOR.getSetEPVName(id) +
                   "_bindc(" + IOR.getEPVName(id) + " *");
      if (IOR.generateHookMethods(cls, context)) {
        writer.print("," + IOR.getPreEPVName(id) + " *"  +
                     "," + IOR.getPostEPVName(id) + " *");
      }
      writer.println(");");
    }
    
    writer.println();
    writer.println("void");
    writer.print(IOR.getSetEPVName(id) + "(" + IOR.getEPVName(id) + " * ");
    writer.print(s_epv);
    if (IOR.generateHookMethods(cls, context)) {
      writer.println(",");
      writer.tab();
      writer.println(IOR.getPreEPVName(id) + " *" + s_pre_epv + ", ");
      writer.println(IOR.getPostEPVName(id) + " *" + s_post_epv + ") ");
      writer.backTab();
    } else {
      writer.print(") ");
    } 
    writer.println("{");
    writer.tab();

    if(with_bindc) {
      writer.print(IOR.getSetEPVName(id) + "_bindc(" + s_epv);
      if(IOR.generateHookMethods(cls, context))
        writer.print(", " + s_pre_epv + ", " + s_post_epv);
      writer.println(");");
    }
    
    Iterator i = methods.iterator();
    while (i.hasNext()) {
      Method m = (Method)i.next();
      if (!m.isStatic()) {
        initializeMethodPointer(writer, m, cls, false, context);
      }
    }

    if(isSuper)
      writer.println("superEPV = _getIOR()->getSuperEPV();");

    writer.backTab();
    writer.println("}");
    writer.closeCxxExtern();
    writer.println();
  }

  private void generateCtor2(SymbolID id)
    throws CodeGenerationException
  {
    Method m = IOR.getBuiltinMethod(IOR.CONSTRUCTOR2, id, d_context);
    final String implMethod = Fortran.getMethodImplName(id, m, d_mang,
                                                        d_context);
    List extendedArgs = StubSource.extendArgs(id, m, d_context, false);
    try {
      d_writer.pushLineBreak(false);
      StubSource.generateMethodSymbol(d_writer, implMethod, d_context,m.getReturnType());
      d_writer.tab();
      d_writer.println("int64_t *self,");
      d_writer.println("void    *private_data,");
      d_writer.println("int64_t *exception");
      d_writer.backTab();
      d_writer.println(");");
    }
    finally {
      d_writer.popLineBreak();
    }
    d_writer.println();
    
    d_writer.println("static void");
    d_writer.println(Fortran.getMethodSkelName(id, m) + "(");
    d_writer.tab();
    writeSkelArgs(extendedArgs, m.isBuiltIn());
    d_writer.println(")");
    d_writer.backTab();
    d_writer.println("{");
    d_writer.tab();
    d_writer.println("int64_t _proxy_self = (ptrdiff_t)self;");
    d_writer.println("int64_t _proxy_" + Fortran.s_exception + 
                     " = 0;");
    d_writer.println(Fortran.getFortranSymbol(d_context) + "(" + 
                     implMethod.toLowerCase()
                     + "," + implMethod.toUpperCase() + "," + 
                     implMethod + ")(");
    d_writer.tab();
    d_writer.println("&_proxy_self, private_data, &_proxy_" +
                     Fortran.s_exception);
    d_writer.backTab();
    d_writer.println(");");
    d_writer.println("*"  + Fortran.s_exception + 
                     " = (struct sidl_BaseInterface__object *)" +
                     "(ptrdiff_t)_proxy_"+  Fortran.s_exception + ";");
    d_writer.backTab();
    d_writer.println("}");
  }

  private void generateGetSuperEPV(Class cls) {
    SymbolID id = cls.getSymbolID();
    d_writer.println(IOR.getEPVName(cls.getParentClass().getSymbolID()) + " *" +
                     IOR.getSymbolName(id) + "__super_epv() {");
    d_writer.tab();
    d_writer.println("return superEPV;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }
  
  /**
   * This method creates a skeleton file for a class.  The
   * skeleton file is a C module that is the glue between the IOR and
   * the implementation of a class written in FORTRAN.
   *
   * @param cls  the class to create.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   */
  public void generateCode(Class cls) throws CodeGenerationException {
    final String pre = "fortran.SkelSource.generateCode: ";
    Collection methods = extendMethods(cls, d_context);
    final SymbolID id = cls.getSymbolID();

    d_writer.writeBanner(cls, Fortran.getSkelFile(id), false,
                         CodeConstants.C_DESC_SKEL_PREFIX + id.getFullName());
    d_writer.println();

    StubSource.generateIncludes(d_writer, cls, d_context);
    d_writer.generateSystemInclude("string.h");
    d_writer.generateSystemInclude("stdio.h");

    d_writer.println();

    if(cls.hasOverwrittenMethods()) {
      if(Fortran.hasBindC(d_context)) {
        StubSource.generateSuperEPV(cls, d_writer);
        StubSource.generateGetIOR(cls.getSymbolID(), d_writer);
        generateGetSuperEPV(cls);
      }
      else {
        //Generate the code necessary for super methods
        StubSource.generateSupers(cls, d_writer, d_context);
      }
    }

    Iterator i = methods.iterator();
    while (i.hasNext()) {
      Method m = (Method)i.next();
      if (!m.isAbstract()) {
        if (IOR.generateHookMethods(cls, d_context) && (!m.isBuiltIn()) ) {
          extendAndGenerate(m.spawnPreHook(), id);
          extendAndGenerate(m, id);
          extendAndGenerate(m.spawnPostHook(false, false), id);
        } else {
          extendAndGenerate(m, id);
        } 
      }
    }
    if (Fortran.isFortran90(d_context)) {
      generateCtor2(id);
      methods.add(IOR.getBuiltinMethod(IOR.CONSTRUCTOR2, id, d_context));
    }
    
    if (!d_context.getConfig().getSkipRMI()) {
      writeRMIAccessFunctions(cls); 
    }

    /* now do _load (which is special (not extern, fancy name, etc)). */
    Method load_meth = IOR.getBuiltinMethod(IOR.LOAD, id, d_context);
    List load_args = StubSource.extendArgs(id, load_meth, d_context, false);
    generateExtern(load_meth, id, load_args);
    d_writer.println();
    d_writer.println("void " + IOR.getSymbolName(id) + "__call_load(void) { ");
    d_writer.tab();
    if(!Fortran.hasBindC(d_context)) {
      d_writer.println("int64_t _proxy_exception;");
    }
    else {
      d_writer.println("struct sidl_BaseInterface__object *throwaway = NULL;");
      d_writer.println("struct sidl_BaseInterface__object **exception = &throwaway;");
    }
    callMethod(id, load_meth, load_args, null);
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
    
    d_writer.println();
    writeInitializeEPV(cls, methods, d_writer, cls.hasOverwrittenMethods(), 
                       d_context);
    if (cls.hasStaticMethod(true)) {
      d_writer.println();
      writeInitializeSEPV(cls, methods, d_writer, d_context);
      d_writer.println();
    }

    if(!Fortran.hasBindC(d_context)) {
      try {
        writeDataAccess(id);
      } catch (java.io.UnsupportedEncodingException uee) {
        throw new CodeGenerationException(pre + uee.getMessage());
      }
    }
  }

  /**
   * This is a convenience routine to create a skeleton file for a class
   * without having to make an instance of <code>SkelSource</code>.  The
   * skeleton file is a C module that is the glue between the IOR and
   * the implementation of a class written in FORTRAN.
   *
   * @param cls    the class for whom a skeleton will be made.
   * @param writer the output device where the skeleton file will be
   *               sent.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   * @exception java.security.NoSuchAlgorithmException
   *   problem with the name mangler.
   */
  public static void generateCode(Class              cls,
                                  LanguageWriterForC writer,
                                  Context            context)
    throws CodeGenerationException, NoSuchAlgorithmException
  {
    SkelSource source = new SkelSource(writer, context);
    source.generateCode(cls);
  }

  /**
   * Write functions to call each RMI fconnect function
   *
   * @param cls    the class for which an routine will be written.
   * @param writer the output writer to which the routine will be written.
   */
  private void writeRMIAccessFunctions(Class cls) 
    throws CodeGenerationException
  {
    SymbolID id = cls.getSymbolID();

    Set fconnectSIDs = IOR.getFConnectSymbolIDs(cls);
    d_writer.printlnUnformatted("#ifdef WITH_RMI");

    for (Iterator i = fconnectSIDs.iterator(); i.hasNext(); ) {        
      SymbolID destination_id = (SymbolID) i.next();
      d_writer.println(C.getSymbolObjectPtr(destination_id) + " " + IOR.getSkelFConnectName(id,destination_id)+ 
                       "(const char* url, sidl_bool ar, sidl_BaseInterface *_ex) { ");
      d_writer.tab();
      d_writer.println("return "+C.getFullMethodName(destination_id, "_connectI")+"(url, ar, _ex);");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();
    }

    d_writer.printlnUnformatted("#endif /*WITH_RMI*/");
   
    Iterator i = IOR.getStructSymbolIDs(cls, true).iterator();
    while(i.hasNext()) {
      writeStructSerialize(cls, (SymbolID)i.next(), true);
    }
    i = IOR.getStructSymbolIDs(cls, false).iterator();
    while(i.hasNext()) {
      writeStructSerialize(cls, (SymbolID)i.next(), false);
    }
  }

  private void writeStructSerialize(Extendable ext,
                                    SymbolID structid,
                                    boolean serialize)
    throws CodeGenerationException
  {
    final String pipeType = 
      (serialize ? "sidl_io_Serializer" : "sidl_io_Deserializer");
    StubSource.writeStructSerializeSig(d_writer, structid, serialize, d_context);
    d_writer.println("void");
    d_writer.println(IOR.getSkelSerializationName(ext, structid, serialize) +
                     "(");
    d_writer.tab();
    d_writer.println((serialize ? "const " : "") +
                     IOR.getStructName(structid) + " *strct,");
    d_writer.println("struct sidl_rmi_" +
                     (serialize ? "Return" : "Call") +
                     "__object *pipe,");
    d_writer.println("const char *name,");
    d_writer.println("sidl_bool copy,");
    d_writer.println("sidl_BaseInterface *_ex)");
    d_writer.backTab();
    d_writer.println("{");
    d_writer.tab();
    d_writer.println("sidl_BaseInterface throwaway_exception;");
    d_writer.println("sidl_BaseInterface bipipe = (sidl_BaseInterface)pipe;");
    d_writer.println("struct " + pipeType + "__object *uppipe =");
    d_writer.tab();
    d_writer.println("(struct " + pipeType + "__object *)");
    d_writer.println("(*bipipe->d_epv->f__cast)(bipipe->d_object,");
    d_writer.println("\"" + pipeType.replace('_','.') + "\",");
    d_writer.println("&throwaway_exception);");
    d_writer.backTab();
    d_writer.println(Fortran.structSerializeStub(structid, serialize,
                                                 d_context) + "(");
    d_writer.tab();
    d_writer.println("strct, uppipe, name, copy, _ex);");
    d_writer.backTab();
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }
}
