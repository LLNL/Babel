//
// File:        SkelSource.java
// Package:     gov.llnl.babel.backend.c
// Revision:    @(#) $Revision: 7421 $
// Date:        $Date: 2011-12-15 17:06:06 -0800 (Thu, 15 Dec 2011) $
// Description: generate C skeleton based for a particular class/interface
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

package gov.llnl.babel.backend.c;

import gov.llnl.babel.backend.c.C;
import gov.llnl.babel.backend.CodeConstants;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.Utilities;
import gov.llnl.babel.backend.writers.LanguageWriter;
import gov.llnl.babel.backend.writers.LanguageWriterForC;
import gov.llnl.babel.Context;
import gov.llnl.babel.symbols.Argument;
import gov.llnl.babel.symbols.AssertionExpression;
import gov.llnl.babel.symbols.CExprString;
import gov.llnl.babel.symbols.Class;
import gov.llnl.babel.symbols.Inverter;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.Type;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * Class <code>SkelSource</code> generates a C skeleton source file to the
 * language writer output stream. The skeleton provides the glue between
 * the independent object representation (IOR) and the developer's C
 * implementation of the class.
 * </p>
 * <p>
 * The skeleton source contains free functions to fill the entry point
 * vector and optionally the static entry point vector. These functions
 * are what the IOR requires.
 * </p>
 * <p>
 * The skeleton source contains free functions to get/set the private
 * data pointer from the IOR. These functions are what the implementation
 * source requires. For the base class (i.e. the class without a parent
 * class), the skeleton also include a function to destroy the object.
 * This function is required for the implemention of
 * <code>deleteRef</code>.
 * </p>
 */
public class SkelSource {

  private static final String s_ensureOrderConstant[] = {
    "sidl_general_order",
    "sidl_column_major_order",
    "sidl_row_major_order"
  };

  private static final String s_self      = Utilities.s_self;
  private static final String s_epv       = "epv";
  private static final String s_pre_epv   = "pre_epv";
  private static final String s_post_epv  = "post_epv";
  private static final String s_sepv      = "sepv";
  private static final String s_pre_sepv  = "pre_sepv";
  private static final String s_post_sepv = "post_sepv";


  /**
   * Write the skeleton file for a particular class to the language writer
   * provided. The skeleton file is the glue between the IOR and the
   * developer's implementation of the class.
   *
   * @param cls    a skeleton file for this class will be written to
   *               <code>writer</code>
   * @param writer this is the output device to which the skeleton
   *               file will be written.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception. It can be caused by I/O trouble or
   *    violations of the data type invariants.
   */
  public static void generateCode(Class cls, LanguageWriterForC writer,
                                  Context context)
    throws CodeGenerationException
  {
    writer.writeBanner(cls, C.getSkelFile(cls), false,
      CodeConstants.C_DESC_SKEL_PREFIX + cls.getFullName());

    generateIncludes(cls, writer, context);

    if (cls.getParentClass() == null) {
      writeIORCall(cls, C.getPrivateDestructor(cls),
                   IOR.getBuiltinMethod(IOR.DELETE, cls, context), writer,
                   context);
    }

    ImplHeader.writeBuiltinDecls(writer, cls, context);
    ImplHeader.writeMethodDecls(writer, cls, true, context);
    ImplHeader.writeMethodDecls(writer, cls, false, context);

    writeSkelMethods(cls, writer, context);
    
    writeInitializeEPV(cls, writer, context);
    if (cls.hasStaticMethod(true)) {
      writeInitializeSEPV(cls, writer, context);
    }
    writer.openCxxExtern();
    writeCallLoad(cls,writer);
    if (!context.getConfig().getSkipRMI()) {
      writeRMIAccessFunctions(cls, writer, context);
    }
    
    writeGetDataPointer(cls, writer);
    writeSetDataPointer(cls, writer);
    writer.closeCxxExtern();
  }

  public static void generateIncludes(Class cls, 
                                      LanguageWriterForC writer,
                                      Context context)
    throws CodeGenerationException
  {
    writer.generateInclude(IOR.getHeaderFile(cls), false);
    writer.generateInclude(C.getHeaderFile(cls), false);
    writer.printlnUnformatted("#include <stddef.h>");
   if (cls.hasOverwrittenMethods()) {
      writer.generateInclude(IOR.getHeaderFile(cls), false);
      writer.printlnUnformatted("#ifdef SIDL_DYNAMIC_LIBRARY");
      writer.printlnUnformatted("#include <stdio.h>");
      writer.printlnUnformatted("#include <stdlib.h>");
      writer.printlnUnformatted("#include \"sidl_Loader.h\"");
      writer.printlnUnformatted("#endif");

      //function from the Impl file that sets the superEPV
      writer.println();
      writer.println("extern void " + C.getObjectName(cls) + "__superEPV(");
      writer.print("struct " + C.getObjectName(cls.getParentClass()));
      writer.println("__epv*);");

      StubSource.generateGetExternals(cls, writer, context);
    }
    writer.println();

    if (!context.getConfig().getSkipRMI()) {
      writer.printlnUnformatted("#ifdef WITH_RMI");
    
      Set fconnectSIDs = IOR.getFConnectSymbolIDs(cls);
    
      for (Iterator i = fconnectSIDs.iterator(); i.hasNext(); ) {
        SymbolID l_id = (SymbolID) i.next();
        writer.generateInclude(C.getHeaderFile(l_id), true);
      }
      writer.printlnUnformatted("#endif /* WITH_RMI */");
      
      Set serializeSIDs   = IOR.getStructSymbolIDs(cls, true);
      Set deserializeSIDs = IOR.getStructSymbolIDs(cls, false);

      if (!serializeSIDs.isEmpty()) {
        writer.generateInclude("sidl_io_Serializer.h", true);
        writer.println();
      }

      for(Iterator i = serializeSIDs.iterator(); i.hasNext(); ) {
        SymbolID l_id = (SymbolID) i.next();
        writer.generateInclude(C.getHeaderFile(l_id), true);
        writer.println();
      }

      if (!deserializeSIDs.isEmpty()) {
        writer.generateInclude("sidl_io_Deserializer.h", true);
        writer.println();
      }

      for(Iterator i = deserializeSIDs.iterator(); i.hasNext(); ) {
        SymbolID l_id = (SymbolID) i.next();
        writer.generateInclude(C.getHeaderFile(l_id), true);
        writer.println();
      }
    }
  }

  /**
   * Write skeleton functions for methods that need them.  For the C
   * skeleton, only functions with array ordering specifications require
   * a skeleton method.
   *
   * @param  cls   the class whose skeleton methods are to be written.
   * @param writer the output device where the skeleton methods are to be
   *               written.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception. It can be caused by I/O trouble or
   *    violations of the data type invariants.
   */
  private static void writeSkelMethods(Class cls, 
                                       LanguageWriterForC writer,
                                       Context context) 
    throws CodeGenerationException
  {
    Iterator i              = cls.getMethods(false).iterator();
    boolean  genHookMethods = IOR.generateHookMethods(cls, context);

    while (i.hasNext()) {
      Method m = (Method)i.next();
      if (C.methodNeedsSkel(m)) {
        if (genHookMethods) {
          Method pre  = m.spawnPreHook();
          Method post = m.spawnPostHook();
          writeSkelMethod(cls, pre,  writer, context);
          writeSkelMethod(cls, m,    writer, context);
          writeSkelMethod(cls, post, writer, context);
        } else {
          writeSkelMethod(cls, m, writer, context);
        }
      }
    }
  }

  private static String getArgValue(Argument arg) {
    return  (arg.getMode() == Argument.IN) ? arg.getFormalName()
                                           : "*" + arg.getFormalName();
  }

  /**
   * Write a skeleton function for a method with an array ordering
   * specification.
   *
   * @param id	   the name of the class who owns the method.
   * @param m      the method itself.
   * @param writer the output device where the skeleton function
   *               will be written.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception. It can be caused by I/O trouble or
   *    violations of the data type invariants.
   */
  private static void writeSkelMethod(SymbolID id, 
                                      Method m, 
                                      LanguageWriterForC writer,
                                      Context context)
    throws CodeGenerationException
  {
    Type    methodReturn = m.getReturnType();
    boolean useReturn    = (methodReturn.getType() != Type.VOID);
    boolean hasOrderSpec = false;
    final String method_name = C.getMethodSkelName(id, m);

    //Begin method signature
    writer.print("static ");
    writer.println(getReturnString(methodReturn, context));
    
    writer.println(method_name + "(");
    writer.tab();
    IOR.generateArgumentList(writer, context, s_self, false, id, m, true, 
                             true, false, true, false, false, false);
    writer.backTab();
    writer.println(")");

    //Begin method body
    writer.println("{");
    writer.tab();
    if (useReturn) {
      writer.println(getReturnString(methodReturn, context) + " _return;");
      if (methodReturn.hasArrayOrderSpec()) {
        hasOrderSpec = hasOrderSpec || !methodReturn.isRarray();
        writer.print(getReturnString(methodReturn, context));
        writer.println(" _return_proxy;");
      }
    }
    List     extArgs    = Utilities.extendArgs(id, m, false, context);
    Iterator args       = extArgs.iterator();
    Map      index_args = m.getRarrayInfo();

    while (args.hasNext()) {
      Argument arg = (Argument)args.next();
      //declare ensured array and ensure it
      if (arg.hasArrayOrderSpec()) {
        final Type argType = arg.getType();
        hasOrderSpec = hasOrderSpec 
                     || (!argType.isRarray() && (arg.getMode() != Argument.IN));
        writer.print(getReturnString(argType, context) + " ");
        writer.print(arg.getFormalName() + "_proxy");
        if (arg.getMode()!= Argument.OUT) {
          writer.print(" = " + C.getEnsureArray(argType.getArrayType()));
          writer.print("(" + getArgValue(arg) + ", ");
          writer.print(argType.getArrayDimension() + ", ");
          writer.println(s_ensureOrderConstant[argType.getArrayOrder()] + ");");
        } else {
          writer.println(" = " + C.NULL + ";");
        }
      }
      //declare rarray proxies.
      if (arg.getType().isRarray()) {
        writer.print(IOR.getArgumentWithFormal(arg, context, false, true, 
                                               false));
        writer.print(C.RAW_ARRAY_EXT + " = ");
        writer.println(arg.getFormalName() + "_proxy->d_firstElement;");
      }
      if (  (arg.getMode() != Argument.IN) 
         && (arg.getType().getDetailedType() == Type.ENUM)  ) 
      {
        writer.print(C.getEnumName(arg.getType().getSymbolID()));
        writer.print(" _proxy_" + arg.getFormalName());
        if (arg.getMode() == Argument.INOUT) {
          writer.print(" = (" + C.getEnumName(arg.getType().getSymbolID()));
          writer.print(")*" + arg.getFormalName());
        }
        writer.println(";");
      }
    }
    //Create rarray index variables and initialize them.
    for (Iterator i = index_args.values().iterator(); i.hasNext();) {
      //This is more than a little ugly, but I all I want is the first
      //element off the collection of array dimension that define
      //the index's value.
      HashSet           index_collection = (HashSet) i.next();
      Iterator          tmp              = index_collection.iterator();
      Method.RarrayInfo info             = (Method.RarrayInfo) tmp.next();
      Argument          index            = info.index;
      int               dim              = info.dim;
      String            upper_func_name  = "sidlLength";

      if (index.getType().getType() == Type.LONG) {
        writer.print("int64_t ");
      } else {
        writer.print("int32_t ");
      }
      writer.print(index.getFormalName() + " = ");
      writer.print(CExprString.toCString(Inverter.invertExpr
                                         ((AssertionExpression)
                                         info.rarray.getType().
                                         getArrayIndexExprs().get(dim),
                                         upper_func_name + "(" 
                                         + info.rarray.getFormalName() 
                                         + "_proxy," + dim + ")", context)));
      writer.println(";");
    }

    args = extArgs.iterator();
    while (args.hasNext()) {
      Argument arg = (Argument)args.next();
      if (  arg.hasArrayOrderSpec() 
         && arg.getMode() == Argument.INOUT ) 
      {
        writer.print(C.getDelRefArray(arg.getType().getArrayType()));
        writer.println("(" + getArgValue(arg) + ");");
        if (!arg.getType().isRarray()) {
          writer.println(getArgValue(arg) + " = NULL;");
        }
      }
    }
    if (useReturn) {
      if (methodReturn.hasArrayOrderSpec()) {
        writer.println("_return_proxy =");
      } else {
        writer.println("_return =");
      }
      writer.tab();
    }
    //Write Impl call
    writer.println(C.getMethodImplName(id, m.getLongMethodName()) + "(");
    writer.tab();
    List callArgs = Utilities.extendArgs(id, m, true, context);
    args = callArgs.iterator();
    while (args.hasNext()) {
      Argument arg = (Argument)args.next();
      if (arg.hasArrayOrderSpec() && !arg.getType().isRarray()) {
        if (Argument.IN != arg.getMode()) {
          writer.print("&");
        }
        writer.print(arg.getFormalName() + "_proxy");
      } else if (arg.getType().isRarray()) {
        writer.print(arg.getFormalName() + C.RAW_ARRAY_EXT);
      } else if (  (arg.getMode() != Argument.IN) 
                && (arg.getType().getDetailedType() == Type.ENUM)  ) 
      {
        writer.print("&_proxy_" + arg.getFormalName());
      } else {
        writer.print(arg.getFormalName());
      }
      if (args.hasNext()) {
        writer.println(",");
      }
    }
    writer.println(");");
    writer.backTab();
    if (useReturn) {
       writer.backTab();
    }
    if (hasOrderSpec && !m.getThrows().isEmpty()) {
      writer.println("if (!(*_ex)) {");
      writer.tab();
    }
    args = extArgs.iterator();
    while (args.hasNext()) {
      Argument arg = (Argument)args.next();
      if (arg.hasArrayOrderSpec()) {
        final Type argType = arg.getType();
        if (!argType.isRarray()) {
          if ((arg.getMode() != Argument.IN)) {
            writer.print(getArgValue(arg) + " = ");
            writer.print(C.getEnsureArray(argType.getArrayType()) + "(");
            writer.print(arg.getFormalName() + "_proxy, ");
            writer.print(arg.getType().getArrayDimension() + ", ");
            writer.print(s_ensureOrderConstant[argType.getArrayOrder()]);
            writer.println(");");
            writer.print(C.getDelRefArray(arg.getType().getArrayType()));
            writer.println("(" + arg.getFormalName() + "_proxy);");
          }
        }
        else if (arg.getMode() == Argument.INOUT) {
          writer.print(getArgValue(arg) + " = " + arg.getFormalName());
          writer.println("_proxy;");
          writer.println(arg.getFormalName() + "_proxy = NULL;");
        }
      }
      else if ((arg.getMode() != Argument.IN) &&
               (arg.getType().getDetailedType() == Type.ENUM)) {
        writer.print("*" + arg.getFormalName() + " = _proxy_");
        writer.println(arg.getFormalName() + ";");
      }
    }
    if (useReturn) {
      if (methodReturn.hasArrayOrderSpec()) {
        writer.print("_return = ");
        writer.print(C.getEnsureArray(methodReturn.getArrayType()));
        writer.print("(_return_proxy, " + methodReturn.getArrayDimension());
        writer.print(", ");
        writer.print(s_ensureOrderConstant[methodReturn.getArrayOrder()]);
        writer.println(");");
        writer.print(C.getDelRefArray(methodReturn.getArrayType()));
        writer.println("(_return_proxy);");
      }
    }
    if (hasOrderSpec && !m.getThrows().isEmpty()) {
      writer.backTab();
      writer.println("}");
      if (useReturn) {
        writer.println("else {");
        writer.tab();
        writer.println("_return = " + IOR.getInitialValue(methodReturn) + ";");
        writer.backTab();
        writer.println("}");
      }
    }
    args = extArgs.iterator();
    while (args.hasNext()) {
      Argument arg = (Argument)args.next();
      if (  (  arg.getType().isRarray() 
            && (arg.getMode() == Argument.INOUT) ) 
         || (  arg.getType().hasArrayOrderSpec() 
            && (arg.getMode() == Argument.IN) )  ) 
      {
        writer.print(C.getDelRefArray(arg.getType().getArrayType()));
        writer.println("(" + arg.getFormalName() + "_proxy);");
      }
    }
    if (useReturn) {
      writer.println("return _return;");
    }
    writer.backTab();
    writer.println("}");
    writer.println();
  }

  /**
   * Generate an return string for the specified SIDL type.  Most
   * of the SIDL return strings are listed in the static structures defined
   * at the start of this class.  Symbol types and array types require
   * special processing.
   */
  private static String getReturnString(Type type, Context context)
    throws CodeGenerationException
  {
    return IOR.getReturnString(type, context, false, false);
  }

  /**
   * Write the support code to get the data pointer from the class.
   */
  private static void writeGetDataPointer(Class cls, LanguageWriter writer) {
    writer.println(C.getDataName(cls) + "*");
    writer.print(C.getDataGetName(cls) + "(" + C.getObjectName(cls));
    writer.println(" self)");
    writer.println("{");
    writer.tab();
    writer.print("return (" + C.getDataName(cls) + "*)");
    writer.println("(self ? self->d_data : " + C.NULL + ");");
    writer.backTab();
    writer.println("}");
    writer.println();
  }

  /**
   * Write the support code to set the data pointer in the class.
   */
  private static void writeSetDataPointer(Class cls, LanguageWriter writer) {
    writer.println("void " + C.getDataSetName(cls) + "(");
    writer.tab();
    writer.println(C.getObjectName(cls) + " self,");
    writer.println(C.getDataName(cls) + "* data)");
    writer.backTab();
    writer.println("{");
    writer.tab();
    writer.println("if (self) {");
    writer.tab();
    writer.println("self->d_data = data;");
    writer.backTab();
    writer.println("}");
    writer.backTab();
    writer.println("}");
  }

  /**
   * Write a function to initialize entries in the entry point vector for
   * a particular class. This will generate an assignment statement for
   * each non-<code>static</code> method defined locally in the class.
   *
   * @param cls    the class for which an routine will be written.
   * @param writer the output writer to which the routine will be written.
   */
  private static void writeInitializeEPV(Class cls, 
                                         LanguageWriterForC writer,
                                         Context context)
    throws CodeGenerationException
  {
    SymbolID id = cls.getSymbolID();
    writer.openCxxExtern();
    writer.println("void");
    /*
     * The following assumes (basic) hook EPV option is a super set of 
     * hook methods.  That is, if hook methods are to be generated,
     * then so will the hook EPVs.  Otherwise, we'd have to checked for
     * both generation of hook EPV and hook methods.
     */
    writer.print(C.getSetEPVName(cls) + "(" + IOR.getEPVName(cls) + " *");
    writer.print(s_epv);
    if (IOR.generateHookEPVs(cls, context)) {
      writer.println(",");
      writer.tab();
      writer.println(IOR.getPreEPVName(cls) + " *" + s_pre_epv + ", ");
      writer.println(IOR.getPostEPVName(cls) + " *" + s_post_epv);
      writer.backTab();
    }
    writer.println(")");
    writer.println("{");
    writer.tab();
    initializeMethodPointer(writer, 
                            IOR.getBuiltinMethod(IOR.CONSTRUCTOR, id, context),
                            cls, context);
    initializeMethodPointer(writer, IOR.getBuiltinMethod(IOR.CONSTRUCTOR2, id,
                                                         context),
                            cls, context);

    initializeMethodPointer(writer, 
                            IOR.getBuiltinMethod(IOR.DESTRUCTOR, id, context), 
                            cls, context);
    Iterator i = cls.getMethods(false).iterator();
    while (i.hasNext()) {
      Method m = (Method)i.next();
      initializeMethodPointer(writer, m, cls, context);
    }
    writer.println();
    //if we have overwritten methods, initialize the super pointer
    if (cls.hasOverwrittenMethods()) {
      writer.print(C.getObjectName(cls) + "__superEPV(_getExternals()->");
      writer.println("getSuperEPV());");
    }
    writer.backTab();
    writer.println("}");
    writer.closeCxxExtern();
    writer.println();
  }

  /**
   * Write a function to call the _load builtin
   *
   * @param cls    the class for which an routine will be written.
   * @param writer the output writer to which the routine will be written.
   */
  private static void writeCallLoad(Class cls, LanguageWriterForC writer)
  {
    String name = IOR.getSymbolName(cls);

    writer.println("void " + IOR.getCallLoadName(cls) + "(void) { ");
    writer.tab();
    writer.println("sidl_BaseInterface _throwaway_exception = NULL;");
    writer.println("impl_" + name + "__load(&_throwaway_exception);");
    writer.backTab();
    writer.println("}");
  }

  /**
   * Write functions to call each RMI fconnect function
   *
   * @param cls    the class for which an routine will be written.
   * @param writer the output writer to which the routine will be written.
   */
  private static void writeRMIAccessFunctions(Class cls, 
                                              LanguageWriterForC writer,
                                              Context context) 
    throws CodeGenerationException
  {

    Set fconnectSIDs    = IOR.getFConnectSymbolIDs(cls);
    Set serializeSIDs   = IOR.getStructSymbolIDs(cls, true);
    Set deserializeSIDs = IOR.getStructSymbolIDs(cls, false);
    //Set fcastSIDs = IOR.getFCastSymbolIDs(cls);

    writer.printlnUnformatted("#ifdef WITH_RMI");
    
    for (Iterator i = fconnectSIDs.iterator(); i.hasNext(); ) {
      SymbolID l_id = (SymbolID) i.next();
      writer.print(C.getSymbolObjectPtr(l_id) + " ");
      writer.print(IOR.getSkelFConnectName(cls,l_id));
      writer.print("(const char* url, sidl_bool ar, sidl_BaseInterface *_ex)");
      writer.println(" { ");
      writer.tab();
      writer.print("return "+ IOR.getRemoteConnectName(l_id));
      writer.println("(url, ar, _ex);");
      //writer.print("return " + C.getImplFConnectName(cls,l_id));
      //writer.println("(url, ar, _ex);");
      writer.backTab();
      writer.println("}");
      writer.println();
    }

    writer.printlnUnformatted("#endif /*WITH_RMI*/");

    for(Iterator i = serializeSIDs.iterator(); i.hasNext(); ) {
      SymbolID l_id = (SymbolID) i.next();
      writer.println("void");
      writer.println(IOR.getSkelSerializationName(cls,l_id, true));
      writer.tab();
      writer.print("(const " + IOR.getStructName(l_id) + " *strct, ");
      writer.print("struct sidl_rmi_Return__object *pipe, const char *name, ");
      writer.print("sidl_bool copyArg, " + IOR.getExceptionFundamentalType());
      writer.println("*exc)");
      writer.backTab();
      writer.println("{");
      writer.tab();
      writer.print("struct sidl_io_Serializer__object *__pipe = ");
      writer.println("sidl_io_Serializer__cast(pipe, exc);");
      writer.println("if (*exc) return;");
      writer.println("if (__pipe) {");
      writer.tab();
      writer.println(C.getExceptionType() + " __throwaway__;");
      writer.print(IOR.getSymbolName(l_id));
      writer.println("__serialize(strct, __pipe, name, copyArg, exc);");
      writer.println("sidl_io_Serializer_deleteRef(__pipe, &__throwaway__);");
      writer.backTab();
      writer.println("}");
      writer.backTab();
      writer.println("}");
      writer.println();
    }

    for(Iterator i = deserializeSIDs.iterator(); i.hasNext(); ) {
      SymbolID l_id = (SymbolID) i.next();
      writer.println("void");
      writer.println(IOR.getSkelSerializationName(cls, l_id, false));
      writer.tab();
      writer.print("(" + IOR.getStructName(l_id) + " *strct, ");
      writer.print("struct sidl_rmi_Call__object *pipe, const char *name, ");
      writer.print("sidl_bool copyArg, " + IOR.getExceptionFundamentalType());
      writer.println(" *exc)");
      writer.backTab();
      writer.println("{");
      writer.tab();
      writer.print("struct sidl_io_Deserializer__object *__pipe = ");
      writer.println("sidl_io_Deserializer__cast(pipe, exc);");
      writer.println("if (*exc) return;");
      writer.println("if (__pipe) {");
      writer.tab();
      writer.println(C.getExceptionType() + " __throwaway__;");
      writer.print(IOR.getSymbolName(l_id));
      writer.println("__deserialize(strct, __pipe, name, copyArg, exc);");
      writer.println("sidl_io_Deserializer_deleteRef(__pipe, &__throwaway__);");
      writer.backTab();
      writer.println("}");
      writer.backTab();
      writer.println("}");
      writer.println();
    }

  }

  /**
   * For non-<code>static</code> methods, write an assignment statement to
   * initialize a particular member of the entry point vector.
   * Initialization of <code>static</code> methods appears elsewhere.
   *
   * @param writer     the output writer to which the assignment statement
   *                   is written.
   * @param m          a description of the method to initialize
   * @param id         the name of class that owns the method
   */
  private static void initializeMethodPointer(LanguageWriter writer,
                                              Method m, Class cls,
                                              Context context)
    throws CodeGenerationException
  {
    final String methodName = m.getLongMethodName();
    final String ename      = IOR.getVectorEntry(methodName);
    final SymbolID id       = cls.getSymbolID();

    switch (m.getDefinitionModifier()) {
    case Method.FINAL:
    case Method.NORMAL:
      String sname = C.getMethodSkelName(id, m);
      if (!IOR.isBuiltinMethod(methodName)) {
        Method pre  = m.spawnPreHook();
        Method post = m.spawnPostHook();

        /*
         * Order is (currently) critical!  If hook methods (i.e.,
         * pre- and post-hooks) are being generated then be sure to
         * initialize the EPV pointers to the skel/impl methods!  
         * Otherwise, if we're just making sure to include the 
         * basic hook skeletons (but not the implementations), then
         * initialize the EPV pointers to null.
         */
        if (IOR.generateHookMethods(cls, context)) {
          writeMethodAssignment(writer, s_pre_epv, 
                                IOR.getVectorEntry(pre.getLongMethodName()), 
                                C.getMethodSkelName(id, pre));
          writeMethodAssignment(writer, s_epv, ename, sname);
          writeMethodAssignment(writer, s_post_epv, 
                                IOR.getVectorEntry(post.getLongMethodName()), 
                                C.getMethodSkelName(id, post));
        } else if (IOR.generateHookEPVs(cls, context)) {
          writeMethodAssignment(writer, s_pre_epv, 
                                IOR.getVectorEntry(pre.getLongMethodName()), 
                                C.NULL);
          writeMethodAssignment(writer, s_epv, ename, sname);
          writeMethodAssignment(writer, s_post_epv, 
                                IOR.getVectorEntry(post.getLongMethodName()), 
                                C.NULL);
        } else {
          writeMethodAssignment(writer, s_epv, ename, sname);
        }
      } else {
        writeMethodAssignment(writer, s_epv, ename, sname);
      }
      break;
    case Method.ABSTRACT:
      writeMethodAssignment(writer, s_epv, ename, C.NULL);
      break;
    default:
      /* do nothing */
      break;
    }
  }

  /**
   * Write a function to initialize entries in the static entry point vector
   * for a particular class.  This will generate an assignment statement for
   * each <code>static</code> method defined locally in the class.
   *
   * @param cls    the class for which an routine will be written.
   * @param writer the output writer to which the routine will be written.
   */
  private static void writeInitializeSEPV(Class cls, 
                                          LanguageWriterForC writer,
                                          Context context)
    throws CodeGenerationException
  {
    //SymbolID id = cls.getSymbolID();

    writer.openCxxExtern();
    writer.println("void");

    /*
     * The following assumes if hook methods are to be generated, so are 
     * hook EPVs.  Otherwise, we'd have to checked for generation 
     * of both hook EPVs and methods.
     */
    writer.print(C.getSetSEPVName(cls) + "(" + IOR.getSEPVName(cls) + " *");
    writer.print(s_sepv);
    if (IOR.generateHookEPVs(cls, context)) {
      writer.println(",");
      writer.tab();
      writer.println(IOR.getPreSEPVName(cls) + " *" + s_pre_sepv + ", ");
      writer.println(IOR.getPostSEPVName(cls) + " *" + s_post_sepv + ")");
      writer.backTab();
    } else {
      writer.println(")");
    }

    writer.println("{");
    writer.tab();
    Iterator i = cls.getMethods(false).iterator();
    while (i.hasNext()) {
      Method m = (Method) i.next();
      initializeStaticMethodPointer(writer, m, cls, context);
    }
    writer.backTab();
    writer.println("}");
    writer.closeCxxExtern();
    writer.println();
  }

  /**
   * For <code>static</code> methods, write an assignment statement to
   * initialize a particular member of the entry point vector.
   * Initialization of non-<code>static</code> methods appears elsewhere.
   *
   * @param writer     the output writer to which the assignment statement
   *                   is written.
   * @param m          a description of the method to initialize
   * @param cls        the class that owns the method
   */
  private static void initializeStaticMethodPointer(LanguageWriter writer,
                                                    Method m, Class cls,
                                                    Context context)
    throws CodeGenerationException
  {
    final String methodName = m.getLongMethodName();
    final String ename      = IOR.getVectorEntry(methodName);
    final SymbolID id       = cls.getSymbolID();

    switch (m.getDefinitionModifier()) {
    case Method.STATIC:
      String sname = C.getMethodSkelName(id, m);
      if (!IOR.isBuiltinMethod(methodName)) {
        Method pre  = m.spawnPreHook();
        Method post = m.spawnPostHook();

        /*
         * Order is (currently) critical!  If hook methods (i.e.,
         * pre- and post-hooks) are being generated then be sure to
         * initialize the EPV pointers to the skel/impl methods!  
         * Otherwise, if we're just making sure to include the 
         * basic hook skeletons (but not the implementations), then
         * initialize the EPV pointers to null.
         */
        if (IOR.generateHookMethods(cls, context)) {
          writeMethodAssignment(writer, s_pre_sepv, 
                                IOR.getVectorEntry(pre.getLongMethodName()), 
                                C.getMethodSkelName(id, pre));
          writeMethodAssignment(writer, s_sepv, ename, sname);
          writeMethodAssignment(writer, s_post_sepv, 
                                IOR.getVectorEntry(post.getLongMethodName()), 
                                C.getMethodSkelName(id, post));
        } else if (IOR.generateHookEPVs(cls, context)) {
          writeMethodAssignment(writer, s_pre_sepv, 
                                IOR.getVectorEntry(pre.getLongMethodName()), 
                                C.NULL);
          writeMethodAssignment(writer, s_sepv, ename, sname);
          writeMethodAssignment(writer, s_post_sepv, 
                                IOR.getVectorEntry(post.getLongMethodName()), 
                                C.NULL);
        } else {
          writeMethodAssignment(writer, s_sepv, ename, sname);
        }
      } else {
        writeMethodAssignment(writer, s_sepv, ename, sname);
      }
      break;
    default:
      /* do nothing */
      break;
    }
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
   * Create a wrapper function in the skeleton for a particular IOR method.
   *
   * @param cls       the class for which the routine is created.
   * @param funcName  the name of the wrapper function to be written.
   * @param iorMethod the description of the IOR method to be wrapped.
   * @param writer    the output writer to which the routine will be
   *                  written.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception. It can be caused by I/O trouble or
   *    violations of the data type invariants.
   */
  private static void writeIORCall(Class cls, 
                                   String funcName, 
                                   Method iorMethod,
                                   LanguageWriter writer,
                                   Context context)
    throws CodeGenerationException
  {
    List extArgs = Utilities.extendArgs(cls, iorMethod, false, context);
    Iterator args = extArgs.iterator();
    writer.println("void");
    writer.print(funcName);
    writer.print("(");
    while (args.hasNext()) {
      Argument a = (Argument)args.next();
      writer.print(IOR.getArgumentWithFormal(a, context, false, false, false));
      if (args.hasNext()) {
        writer.print(", ");
      }
    }
    writer.println(") {");
    writer.tab();
    writer.println("if (self) {");
    writer.tab();
    writer.writeCommentLine("call the IOR method");
    writer.print("self->" + IOR.getEPVVar(IOR.PUBLIC_EPV) + "->");
    writer.print(IOR.getVectorEntry(iorMethod.getLongMethodName()));
    writer.print("(");
    args = extArgs.iterator();
    while (args.hasNext()){
      Argument a = (Argument)args.next();
      writer.print(a.getFormalName());
      if (args.hasNext()) {
        writer.print(", ");
      }
    }
    writer.println(");");
    writer.backTab();
    writer.println("}");
    writer.backTab();
    writer.println("}");
    writer.println();
  }
}
