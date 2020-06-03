//
// File:        StubSource.java
// Package:     gov.llnl.babel.backend.c
// Revision:    @(#) $Id: StubSource.java 7421 2011-12-16 01:06:06Z adrian $
// Description: generate C backend stub source to a pretty writer stream
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

import gov.llnl.babel.BabelConfiguration;
import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeConstants;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.Utilities;
import gov.llnl.babel.backend.fortran.ImplSource;
import gov.llnl.babel.backend.rmi.RMI;
import gov.llnl.babel.backend.rmi.RMIStubSource;
import gov.llnl.babel.backend.writers.LanguageWriter;
import gov.llnl.babel.backend.writers.LanguageWriterForC;
import gov.llnl.babel.symbols.Argument;
import gov.llnl.babel.symbols.AssertionExpression;
import gov.llnl.babel.symbols.CExprString;
import gov.llnl.babel.symbols.Class;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.Struct;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.SymbolTable;
import gov.llnl.babel.symbols.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Class <code>StubSource</code> generates an C Backend stub source file
 * to a language writer output stream.  The constructor takes a language
 * writer stream and method <code>generateCode</code> generates intermediate
 * object representation for the specified symbol to the output stream.  The
 * language writer output stream is not closed by this object.
 */
public class StubSource {
  private LanguageWriterForC d_writer;

  private Context d_context;

  private static final String s_self            = StubHeader.s_self;
  private static final String s_epv             = StubHeader.s_epv;

  private static final String s_externals       = "_externals";
  private static final String s_externals_func  = "_getExternals";

  private static final String s_static_epv_var  = "_sepv";
  private static final String s_sepv_reset_func = "_resetSEPV()";


  /**
   * This is a convenience utility function that writes the C client
   * stub source information into the provided language writer output
   * stream.  The output stream is not closed on exit.  A code
   * generation exception is thrown if an error is detected.
   *
   * @param symbol the <code>Symbol</code> whose stub source is being written.
   *
   * @param writer the output writer to which the stub source will
   *               be written. This will not be closed.
   *
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception. It can be caused by I/O trouble or
   *    violations of the data type invariants.
   */
  public static void generateCode(Symbol symbol, 
                                  LanguageWriterForC writer,
                                  Context context)
    throws CodeGenerationException
  {
    StubSource source = new StubSource(writer, context);
    source.generateCode(symbol);
  }

  /**
   * This is a convenience utility function specifically for the generation
   * of super "Stub" functions in the Impl files.
   * The output stream is not closed on exit.  A code
   * generation exception is thrown if an error is detected.
   *
   * @param methods is a collection of super methods to be generated.
   *
   * @param writer the output writer to which the stub source will
   *               be written. This will not be closed.
   *
   * @param cls The class in which these supers are to be generated
   *
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception. It can be caused by I/O trouble or
   *    violations of the data type invariants.
   */
  public static void generateSupers(Collection methods, 
                                    Class cls,
                                    LanguageWriterForC writer,
                                    Context context)
    throws CodeGenerationException
  {
    StubSource source = new StubSource(writer, context);
    source.generateSupers(methods, cls);
  }

  public static void generateGetExternals(Class cls, 
                                          LanguageWriterForC writer,
                                          Context context)
    throws CodeGenerationException
  {
    StubSource source = new StubSource(writer, context);
    source.generateGetExternals(cls.getSymbolID());
  }

  /**
   * Create a <code>StubSource</code> object that will write symbol
   * information to the provided output writer stream.
   *
   * @param writer the output writer to which the header will be written.
   *               This will not be closed.
   */
  public StubSource(LanguageWriterForC writer,
                    Context context) {
    d_writer  = writer;
    d_context = context;
  }

  /**
   * Write C stub source information for the provided symbol to the
   * language writer output stream provided in the constructor.  This
   * method does not close the language writer output stream and may
   * be called for more than one symbol (although the generated source
   * may not be valid input for the C compiler).  A code generation
   * exception is generated if an error is detected.  No code is
   * generated for enumerated and package symbols.
   *
   * @param symbol the <code>Symbol</code> whose stub source is being written.
   *
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception. It can be caused by I/O trouble or
   *    violations of the data type invariants.
   */
  public void generateCode(Symbol symbol) throws CodeGenerationException {
    if (symbol != null) {
      switch (symbol.getSymbolType()) {
      case Symbol.CLASS:
      case Symbol.INTERFACE:
        generateExtendable((Extendable) symbol);
        break;
      case Symbol.ENUM:
        generateEnum(symbol);
        break;
      case Symbol.STRUCT:
        generateStruct((Struct)symbol);
        break;
      case Symbol.PACKAGE:
        break;
      default:
        throw new CodeGenerationException("Unsupported symbol type.");
      }
    } else {
      throw new CodeGenerationException("Unexpected null Symbol.");
    }
  }

  /**
   * This function is designed to generate stubs to the super functions
   * available in this Impl files.  These stubs are generated IN the Impl
   * file.  This method does not close the language writer output stream
   * A code generation exception is generated if an error is detected.
   * No code is generated for enumerated and package symbols.
   *
   * @param methods A collection of methods to write out.
   *
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception. It can be caused by I/O trouble or
   *    violations of the data type invariants.
   */
  public void generateSupers(Collection methods, Class cls)
    throws CodeGenerationException
  {
    SymbolID clsID = cls.getSymbolID();
    String   self  = "self";
    if (methods != null) {
      if (methods.size() > 0) {
        //declaring the IOR for super functions
        d_writer.print("static const struct ");
        d_writer.print(C.getObjectName(cls.getParentClass().getSymbolID()));
        d_writer.println("__epv* superEPV = " + C.NULL + ";");

        d_writer.println();
        d_writer.println("void " + C.getObjectName(clsID) + "__superEPV(");
        d_writer.print("struct ");
        d_writer.print(C.getObjectName(cls.getParentClass().getSymbolID()));
        d_writer.println("__epv* parentEPV){");
        d_writer.tab();
        d_writer.println("superEPV = parentEPV;");
        d_writer.backTab();
        d_writer.println("}");

        for (Iterator m = methods.iterator(); m.hasNext(); ) {
          Method method = (Method) m.next();
          generateMethodSignature(d_writer, d_context, self, false, clsID, 
                                  method, true, true);
          d_writer.println("{");
          d_writer.tab();
          if (method.hasRarray()) {
            generateRarrays(d_writer,method, d_context);
          }
          if (getReturnString(method.getReturnType(), d_context) != "void") {
            d_writer.print("return ");
          }
          d_writer.print("(*superEPV");
          d_writer.print("->");
          d_writer.print(IOR.getVectorEntry(method.getLongMethodName()));
          d_writer.print(")((");
          d_writer.print(IOR.getObjectName(
                               cls.getParentClass().getSymbolID()));
          d_writer.println("*)");
          d_writer.tab();
          C.generateArgumentList(d_writer, d_context, self, false, clsID, 
                                 method, false, false, false, true, false, 
                                 false, true);
          d_writer.println(");");
          d_writer.backTab();
          d_writer.backTab();
          d_writer.println("}");
          d_writer.println();
        }
      }
    } else {
      throw new CodeGenerationException("Unexpected null Symbol.");
    }
  }

  /**
   * Generate a return string for the specified SIDL type.  Most
   * of the SIDL return strings are listed in the static structures defined
   * at the start of this class.  Symbol types and array types require
   * special processing.
   */
  private static String getReturnString(Type type, Context context)
    throws CodeGenerationException
  {
    return (Type.ENUM == type.getDetailedType()) 
      ? C.getEnumName(type.getSymbolID())
      : IOR.getReturnString(type, context, false, false);
  }

  /**
   * Generate the Stub source for a SIDL class or interface.  The source
   * file begins with a banner and include files followed by the stub
   * implementations for the methods.
   *
   * @param ext the <code>Extendable</code> whose stub source is being written.
   */
  private void generateExtendable(Extendable ext) throws CodeGenerationException
  {
    /*
     * Generate the file banner and include files.
     */
    SymbolID id        = ext.getSymbolID();
    String   source    = C.getStubFile(id);
    String   header    = C.getHeaderFile(id);
    String   iorHeader = IOR.getHeaderFile(id);

    d_writer.writeBanner(ext, source, false, 
      CodeConstants.C_DESC_STUB_PREFIX + id.getFullName());

    d_writer.printlnUnformatted("#define " +
                                C.getInlineDecl(id));
    d_writer.generateInclude(header, false);
    d_writer.generateInclude(iorHeader, false);
    d_writer.generateInclude("sidl_interface_IOR.h", true);
    if (!d_context.getConfig().getSkipRMI()) {
      d_writer.generateInclude("sidl_rmi_InstanceHandle.h", true);
      d_writer.generateInclude("sidl_rmi_ConnectRegistry.h", true);
    }
    d_writer.generateInclude("sidl_Exception.h",false);
    d_writer.generateInclude("sidl_interface_IOR.h", true);
    d_writer.printlnUnformatted("#include <stddef.h>");
    d_writer.printlnUnformatted("#include <string.h>");
    if (!iorHeader.equals("sidl_BaseInterface_IOR.h")) {
      d_writer.generateInclude("sidl_BaseInterface_IOR.h", false);
    }
    if (!BabelConfiguration.isSIDLBaseClass(id)) {
      d_writer.printlnUnformatted("#include \"babel_config.h\"");
      d_writer.printlnUnformatted("#ifdef SIDL_DYNAMIC_LIBRARY");
      d_writer.printlnUnformatted("#include <stdio.h>");
      d_writer.printlnUnformatted("#include <stdlib.h>");
      d_writer.printlnUnformatted("#include \"sidl_Loader.h\"");
      d_writer.printlnUnformatted("#endif");
    }
    
    generateStructIncludes(ext);
    d_writer.println();

    if (!d_context.getConfig().getSkipRMI()) {
      //No Language specific RMI initialization for C
      d_writer.printlnUnformatted("#define "+RMI.LangSpecificInit());
    }
    /*
     * Output standard function stubs for methods in the object.
     */

    d_writer.println();

    if ((!ext.isAbstract()) || ext.hasStaticMethod(true) ) {
      generateGetExternals(id);
    }
    if (ext.hasStaticMethod(true)) {
      generateGetStaticEPV(id);
    }
    generateMethodStubs(ext);
    ArrayMethods ar = new ArrayMethods(id, false, d_context);
    ar.generateStub(d_writer);
    if (!d_context.getConfig().getSkipRMI()) {
      RMIStubSource.generateCode(ext, d_writer,d_context);
    }
  }

  /**
   * Generate the Stub source for a SIDL enum.  The source
   * file begins with a banner and include files followed by the stub
   * implementations for the methods.
   *
   * @param enm the <code>Enumeration</code> whose stub source is being
   * written .
   */
  private void generateEnum(Symbol enm) throws CodeGenerationException {
    /*
     * Generate the file banner and include files.
     */
    SymbolID id     = enm.getSymbolID();
    String   source = C.getStubFile(id);
    String   header = C.getHeaderFile(id);

    d_writer.writeBanner(enm, source, false, 
      CodeConstants.C_DESC_STUB_PREFIX + id.getFullName());

    d_writer.generateInclude(header, false);
    d_writer.generateInclude("sidl_long_IOR.h", true);
    d_writer.printlnUnformatted("#include <stddef.h>");
    d_writer.println();

    ArrayMethods ar = new ArrayMethods(id, true, d_context);
    ar.generateStub(d_writer);
  }

  public void writeStructDestroy(Struct strct, String suffix)
  {
    final SymbolID id = strct.getSymbolID();
    Iterator       i  = strct.getItems().iterator();
    d_writer.println("void");
    d_writer.print(id.getFullName().replace('.','_') + "__destroy" +
                   suffix + "(");
    d_writer.println(IOR.getStructName(id) + " *arg,");
    d_writer.tab();
    d_writer.println("struct sidl_BaseInterface__object **exception)");
    d_writer.backTab();
    d_writer.println("{");
    d_writer.tab();
    d_writer.println("*exception = NULL;");
    while (i.hasNext()) {
      final Struct.Item item = (Struct.Item)i.next();
      final Type t = item.getType();
      switch(t.getDetailedType()) {
      case Type.STRING:
        d_writer.print("if (arg->" + item.getName());
        d_writer.println(") free(arg->" + item.getName() + ");");
        break;
      case Type.ARRAY:
        if (t.isRarray()) {
          if (t.isNumericArray()) {
            if (IOR.isFixedSizeRarray(t)) {
              d_writer.println("/* "+item.getName()+" skipped since it is static */");
            } else {
              d_writer.println("/* "+item.getName()+" skipped since user will manage */");
            }
          }
        } else {
          d_writer.print("if (arg->" + item.getName());
          d_writer.print(") sidl__array_deleteRef((struct sidl__array*)arg->");
          d_writer.println(item.getName() + ");");
        } 
        break;
      case Type.INTERFACE:
        d_writer.print("if (arg->" + item.getName() + ") (*(arg->");
        d_writer.print(item.getName() + "->d_epv->f_deleteRef))(arg->");
        d_writer.println(item.getName() +"->d_object, exception);");
        d_writer.println("if (*exception) return;");
        break;
      case Type.CLASS:
        d_writer.print("if (arg->" + item.getName() + ") (*(arg->");
        d_writer.print(item.getName() + "->d_epv->f_deleteRef))(arg->");
        d_writer.println(item.getName() +", exception);");
        d_writer.println("if (*exception) return;");
        break;
      case Type.STRUCT:
        d_writer.print(IOR.getSymbolName(t.getSymbolID()));
        d_writer.print("__destroy" + suffix + "(&(arg->" + item.getName());
        d_writer.println("), exception);");
        d_writer.println("if (*exception) return;");
        break;
      }
    }
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  public void writeStructInit(Struct strct, String suffix)
  {
    final SymbolID id = strct.getSymbolID();
    Iterator       i  = strct.getItems().iterator();
    d_writer.println("void");
    d_writer.print(id.getFullName().replace('.','_') + "__init" +
                   suffix + "(");
    d_writer.println(IOR.getStructName(id) + " *arg) {");

    d_writer.tab();
    while (i.hasNext()) {
      final Struct.Item item = (Struct.Item)i.next();
      final Type t = item.getType();
      switch(t.getDetailedType()) {
      case Type.STRING:
      case Type.ARRAY:
        if (t.isRarray()) {
          if (t.isNumericArray()){
            if (IOR.isFixedSizeRarray(t)) {
              d_writer.println("/* "+item.getName()+" skipped since it is static */");
            } else {
              d_writer.println("arg->" + item.getName() + " = NULL;");
            }
          }
        } else {
          d_writer.println("arg->" + item.getName() + " = NULL;");
        }
        break;
      case Type.INTERFACE:
      case Type.CLASS:
        d_writer.println("arg->" + item.getName() + " = NULL;");
        break;
      case Type.STRUCT:
        d_writer.print(IOR.getSymbolName(t.getSymbolID()));
        d_writer.println("__init" + suffix + "(&(arg->" + item.getName() + "));");
        break;
      }
    }
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  static private boolean hasRefType(Collection col)
  {
    Iterator i = col.iterator();
    while(i.hasNext()) {
      final int dt = ((Struct.Item)i.next()).getType().getDetailedType();
      if ((Type.INTERFACE == dt) || (Type.CLASS == dt)) {
        return true;
      }
    }
    return false;
  }

  public void writeStructCopy(Struct strct, String suffix)
    throws CodeGenerationException
  {
    final SymbolID id = strct.getSymbolID();
    Iterator i = strct.getItems().iterator();
    d_writer.println("void");
    d_writer.print(IOR.getSymbolName(id) + "__copy" + suffix + "(const ");
    d_writer.println(IOR.getStructName(id) + " *src,");
    d_writer.tab();
    d_writer.println(IOR.getStructName(id) + " *dest,");
    d_writer.println("struct sidl_BaseInterface__object **exception)");
    d_writer.backTab();
    d_writer.println("{");
    d_writer.tab();
    d_writer.println("*exception = NULL;");
    while (i.hasNext()) {
      final Struct.Item item = (Struct.Item)i.next();
      final Type t = item.getType();
      switch(t.getDetailedType()) {
      case Type.BOOLEAN:
      case Type.CHAR:
      case Type.DCOMPLEX:
      case Type.DOUBLE:
      case Type.FCOMPLEX:
      case Type.FLOAT:
      case Type.INT:
      case Type.LONG:
      case Type.OPAQUE:
      case Type.ENUM:
        d_writer.print("dest->" + item.getName() + " = src->");
        d_writer.println(item.getName() + ";");
        break;
      case Type.STRING:
        d_writer.print("dest->" + item.getName());
        d_writer.println(" = sidl_String_strdup(src->" + item.getName() + ");");
        break;
      case Type.ARRAY:
        if (t.isRarray()) {
          if (t.isNumericArray()){
            Type aType = t.getArrayType();
            //Integer objInt = new Integer(aType.getBasicType());
            //StringBuffer rawString = new StringBuffer();
            Iterator indexVar = t.getArrayIndexExprs().iterator();
            AssertionExpression ae = (AssertionExpression)indexVar.next();
            String checkInt = ae.toString();
            if (ImplSource.isInt(checkInt)){
              d_writer.println("/* "+item.getName()+" skipped since it is static */");
            } else {
              d_writer.print("dest->" + item.getName() +  " = (");
              d_writer.println(IOR.getReturnString(t, d_context, true, true) + ")");
              d_writer.tab();
              d_writer.print("sidl__array_smartCopy((struct sidl__array *)(src->");
              d_writer.println(item.getName() + "));");
              d_writer.backTab();
            }
          }
        } else {
            d_writer.print("dest->" + item.getName() +  " = (");
            d_writer.println(IOR.getReturnString(t, d_context) + ")");
            d_writer.tab();
            d_writer.print("sidl__array_smartCopy((struct sidl__array *)(src->");
            d_writer.println(item.getName() + "));");
            d_writer.backTab();
        }
        break;
      case Type.INTERFACE:
      case Type.CLASS:
        d_writer.print("dest->" + item.getName() + " = (");
        d_writer.print(IOR.getReturnString(t, d_context) + ")src->");
        d_writer.println(item.getName() + ";");
        d_writer.println("if (dest->" + item.getName() + ") {");
        d_writer.tab();
        d_writer.println("(*(dest->" + item.getName() + "->d_epv->f_addRef))(");
        d_writer.tab();
        d_writer.print("dest->" + item.getName());
        d_writer.print((t.getDetailedType() == Type.INTERFACE) ? "->d_object" 
                                                               : "");
        d_writer.println(", exception);");
        d_writer.backTab();
        d_writer.println("if (*exception) return;");
        d_writer.backTab();
        d_writer.println("}");
        break;
      case Type.STRUCT:
        d_writer.print(IOR.getSymbolName(t.getSymbolID()) + "__copy" + suffix +
                       "(&(src->");
        d_writer.print(item.getName() + "), &(dest->" + item.getName());
        d_writer.println("), exception);");
        d_writer.println("if (*exception) return;");
        break;
      }
    }
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  public static void writeStructSerialize(LanguageWriter writer,
                                           Struct strct,
                                           String suffix,
                                           Context context)
    throws CodeGenerationException
  {
    final SymbolID id = strct.getSymbolID();
    final int maxName = C.getLongestNameLen(strct);
    final String funcName = IOR.getSymbolName(strct) + "__serialize" + 
	suffix;
    writer.println("void");
    writer.print(funcName + "(const ");
    writer.println(IOR.getStructName(id) + " *arg,");
    writer.tab();
    writer.println("struct sidl_io_Serializer__object *pipe,");
    writer.println("const char *name,");
    writer.println("sidl_bool copyArg,");
    writer.println("struct sidl_BaseInterface__object **exception)");
    writer.backTab();
    writer.println("{");
    writer.tab();
    writer.println("*exception = NULL;");
    if (!strct.getItems().isEmpty()) {
      writer.println("{");
      writer.tab();
      writer.printlnUnformatted("#ifndef __func__");
      writer.printlnUnformatted("#define __func__ \"" + funcName + "\"");
      writer.printlnUnformatted("#endif");
      if (hasRefType(strct.getItems())) {
        writer.println("struct sidl_io_Serializable__object *_serial;");
      }
      writer.println("const size_t namelen = (name ? strlen(name) : 0);");
      writer.print("char * const namebuf = sidl_malloc(namelen + ");
      writer.println(Integer.toString(maxName + 2) + ",");
      writer.tab();
      writer.println("\"Unable to allocate name buffer\",");
      writer.println("__FILE__, __LINE__, __func__, exception);");
      writer.backTab();
      writer.println("if (namebuf) {");
      writer.tab();
      writer.println("char * const innerName = namebuf + namelen + 1;");
      writer.println("memcpy(namebuf, name, namelen);");
      writer.println("namebuf[namelen] = '.';");
      writer.print("namebuf[namelen + " + Integer.toString(maxName + 1));
      writer.println("] = '\\0';");
      writer.println("*exception = NULL;");
      Iterator i = strct.getItems().iterator();
      while(i.hasNext()) {
        final Struct.Item item = (Struct.Item)i.next();
        final Type t = item.getType();
        writer.println("strcpy(innerName, \"" + item.getName() + "\");");
        switch(t.getDetailedType()) {
        case Type.BOOLEAN:
        case Type.CHAR:
        case Type.DCOMPLEX:
        case Type.DOUBLE:
        case Type.FCOMPLEX:
        case Type.FLOAT:
        case Type.INT:
        case Type.LONG:
        case Type.OPAQUE:
        case Type.STRING:
          writer.print("sidl_io_Serializer_pack");
          writer.print(Utilities.capitalize(Type.getTypeName(
                                                   t.getDetailedType())));
          writer.print("(pipe, namebuf, arg->" + item.getName());
          writer.println(", exception); SIDL_CHECK(*exception);");
          break;
        case Type.ENUM:
          writer.print("sidl_io_Serializer_packLong(pipe, namebuf, arg->");
          writer.println(item.getName() + ", exception); SIDL_CHECK(*exception);");
          break;
        case Type.ARRAY:
          if(!RMI.isSerializable(t, context)) {
            writer.pushLineBreak(false);
            writer.println("{");
            writer.tab();
            writer.println("SIDL_THROW(*exception, sidl_NotImplementedException, " +
                           "\"Cannot serialize struct " + strct.getSymbolID().getShortName() +
                           " due to nonserializable field " + item.getName() + ".\");");
            writer.backTab();
            writer.println("}");
            writer.popLineBreak();
          }
          else {
            String ext = RMI.getMethodExtension(t);
            String var_prefix = "";

            if(t.getArrayType() != null) {
              if(t.getArrayType().getDetailedType() == Symbol.ENUM) {
                var_prefix = "(" + IOR.getArrayName(Type.LONG) + ")";
              } else if((t.getArrayType().getDetailedType() == Symbol.CLASS ||
                         t.getArrayType().getDetailedType() == Symbol.INTERFACE)) {
                SymbolTable table = context.getSymbolTable();
                Extendable serializable = (Extendable) table.lookupSymbol(BabelConfiguration.getSerializableType());
                var_prefix = "(" + IOR.getArrayName(serializable.getSymbolID()) + "*)"+ var_prefix;
              }
            }

            //for raw arrays, we have to make up the missing metadata for
            //the call to the generic pack<Type>Array functions
            writer.println("{");
            writer.tab();

            String array_arg = var_prefix + "arg->" + item.getName();
            if(t.isRarray()) {
              array_arg = var_prefix + C.wrapRawArrayFromStruct(writer,
                                                                item.getName(),
                                                                "arg",
                                                                t,
                                                                context);
            }
            
            writer.println("sidl_io_Serializer_pack" + ext + "(pipe, namebuf, " +
                           array_arg + ", " +
                           BabelConfiguration.getArrayOrderName(t.getArrayOrder()) +
                           "," + t.getArrayDimension() + ", 0" + 
                           ", exception); SIDL_CHECK(*exception);");
            writer.backTab();
            writer.println("}");
          }
          break;
        case Type.INTERFACE:
        case Type.CLASS:
          writer.println("if (copyArg) {");
          writer.tab();
          writer.println("if (arg->" + item.getName() + ") {");
          writer.tab();
          writer.println("_serial = (struct sidl_io_Serializable__object *)");
          writer.tab();
          writer.println("(*arg->" + item.getName() + "->d_epv->f__cast)(");
          writer.print("arg->" + item.getName());
          writer.print((t.getDetailedType() == Type.INTERFACE) ? "->d_object" 
                                                                 : "");
          writer.println(",");
          writer.println("\"sidl.rmi.Serializable\", exception);");
          writer.backTab();
          writer.println("SIDL_CHECK(*exception);");
          writer.println("if (!_serial) {");
          writer.tab();
          writer.println("SIDL_THROW(*exception, sidl_CastException,");
          writer.tab();
          try {
            writer.pushLineBreak(false);
            writer.print("\"struct item " + item.getName());
            writer.println(" cannot be cast to a sidl.io.Serializable.\");");
          }
          finally {
            writer.popLineBreak();
          }
          writer.backTab();
          writer.backTab();
          writer.println("}");
          writer.backTab();
          writer.println("}");
          writer.println("else { _serial = NULL; }");
          writer.println("sidl_io_Serializer_packSerializable(pipe, ");
          writer.tab();
          writer.println("namebuf , _serial, exception);");
          writer.backTab();
          writer.println("if (_serial) {");
          writer.tab();
          writer.println("struct sidl_BaseInterface__object *_throwaway;");
          writer.println("(*_serial->d_epv->f_deleteRef)(_serial->d_object,");
          writer.tab();
          writer.println("((*exception) ? &_throwaway : exception));");
          writer.backTab();
          writer.backTab();
          writer.println("}");
          writer.backTab();
          writer.println("}");
          writer.println("else {");
          writer.tab();
          writer.println("char *url = ");
          writer.tab();
          writer.println("(*arg->" + item.getName() + "->d_epv->f__getURL)(");
          writer.print("arg->" + item.getName());
          writer.print((t.getDetailedType() == Type.INTERFACE) ? "->d_object" 
                                                                 : "");
          writer.println(", exception);");
          writer.backTab();
          writer.println("SIDL_CHECK(*exception);");
          writer.print("sidl_io_Serializer_packString(pipe, namebuf, ");
          writer.println("url, exception);");
          writer.println("sidl_String_free(url);");
          writer.println("SIDL_CHECK(*exception);");
          writer.backTab();
          writer.println("}");
          break;
        case Type.STRUCT:
          writer.print(IOR.getSymbolName(t.getSymbolID()));
          writer.print("__serialize" + suffix + 
                       "(&(arg->" + item.getName());
          writer.println("), pipe, namebuf, copyArg, exception); SIDL_CHECK(*exception);");
          break;
        default:
          throw new CodeGenerationException("Unexpected type");
        }
        if (i.hasNext()) {
          writer.println("SIDL_CHECK(*exception);");
        }
      }
      writer.println("EXIT:");
      writer.println("free(namebuf);");
      writer.backTab();
      writer.println("}");
      writer.backTab();
      writer.println("}");
    }
    writer.backTab();
    writer.println("}");
  }

  public static void writeStructDeserialize(LanguageWriter writer,
                                            Struct strct,
                                            String suffix,
                                            Context context)
    throws CodeGenerationException
  {
    final SymbolID id = strct.getSymbolID();
    final int maxName = C.getLongestNameLen(strct);
    final String funcName = IOR.getSymbolName(id) + "__deserialize" +
	suffix;
    writer.println("void");
    writer.print(funcName + "(");
    writer.println(IOR.getStructName(id) + " *arg,");
    writer.tab();
    writer.println("struct sidl_io_Deserializer__object *pipe,");
    writer.println("const char *name,");
    writer.println("sidl_bool copyArg,");
    writer.println("struct sidl_BaseInterface__object **exception)");
    writer.backTab();
    writer.println("{");
    writer.tab();
    writer.println("*exception = NULL;");
    if (!strct.getItems().isEmpty()) {
      writer.println("{");
      writer.tab();
      writer.printlnUnformatted("#ifndef __func__");
      writer.printlnUnformatted("#define __func__ \"" + funcName + "\"");
      writer.printlnUnformatted("#endif");
      if (hasRefType(strct.getItems())) {
        writer.println("struct sidl_io_Serializable__object *_serial;");
      }
      writer.println("const size_t namelen = (name ? strlen(name) : 0);");
      writer.print("char * const namebuf = sidl_malloc(namelen + ");
      writer.println(Integer.toString(maxName + 2) + ",");
      writer.tab();
      writer.println("\"Unable to allocate name buffer\",");
      writer.println("__FILE__, __LINE__, __func__, exception);");
      writer.backTab();
      writer.println("if (namebuf) {");
      writer.tab();
      writer.println("char * const innerName = namebuf + namelen + 1;");
      writer.println("memcpy(namebuf, name, namelen);");
      writer.println("namebuf[namelen] = '.';");
      writer.print("namebuf[namelen + ");
      writer.println(Integer.toString(maxName + 1) + "] = '\\0';");
      writer.println("*exception = NULL;");
      Iterator i = strct.getItems().iterator();
      while(i.hasNext()) {
        final Struct.Item item = (Struct.Item)i.next();
        final Type t = item.getType();
        writer.println("strcpy(innerName, \"" + item.getName() + "\");");
        switch(t.getDetailedType()) {
        case Type.BOOLEAN:
        case Type.CHAR:
        case Type.DCOMPLEX:
        case Type.DOUBLE:
        case Type.FCOMPLEX:
        case Type.FLOAT:
        case Type.INT:
        case Type.LONG:
        case Type.OPAQUE:
        case Type.STRING:
          writer.print("sidl_io_Deserializer_unpack");
          writer.print(Utilities.capitalize(Type.getTypeName(
                                                   t.getDetailedType())));
          writer.print("(pipe, namebuf, &(arg->" + item.getName());
          writer.println("), exception); SIDL_CHECK(*exception);");
          break;
        case Type.ENUM:
          writer.print("sidl_io_Deserializer_unpackLong(pipe, namebuf,");
          writer.println("&(arg->" + item.getName() + "), exception); SIDL_CHECK(*exception);");
          break;
        case Type.ARRAY:
          if(!RMI.isSerializable(t, context)) {
            writer.pushLineBreak(false);
            writer.println("{");
            writer.tab();
            writer.println("SIDL_THROW(*exception, sidl_NotImplementedException, " +
                           "\"Cannot deserialize struct " + strct.getSymbolID().getShortName() +
                           " due to nondeserializable field " + item.getName() + ".\");");
            writer.backTab();
            writer.println("}");
            writer.popLineBreak();
          }
          else {
            String var_prefix = "&";

            if(t.getArrayType() != null) {
              if(t.getArrayType().getDetailedType() == Symbol.ENUM) {
                var_prefix = "(" + IOR.getArrayName(Type.LONG) + "*)" + var_prefix;
              } else if(t.getArrayType().getDetailedType() == Symbol.CLASS ||
                        t.getArrayType().getDetailedType() == Symbol.INTERFACE) {
                SymbolTable table = context.getSymbolTable();
                Extendable serializable = (Extendable)table.lookupSymbol(BabelConfiguration.getSerializableType());
                var_prefix = "(" + IOR.getArrayName(serializable.getSymbolID()) + "**)"+ var_prefix;
              }
            }

            //for raw arrays, we have to make up the missing metadata for
            //the call to the generic pack<Type>Array functions
            writer.println("{");
            writer.tab();

            String array_arg = var_prefix + "arg->" + item.getName();
            if(t.isRarray()) {
              array_arg = var_prefix + C.wrapRawArrayFromStruct(writer,
                                                                item.getName(),
                                                                "arg",
                                                                t,
                                                                context);
            }
            
            writer.println("sidl_io_Deserializer_unpack" + RMI.getMethodExtension(t) +
                           "(pipe, namebuf, " +
                           array_arg + ", " +
                           BabelConfiguration.getArrayOrderName(t.getArrayOrder()) +
                           ", " + t.getArrayDimension() + ", " + 
                           (t.isRarray() ? "TRUE" : "FALSE") +
                           ", exception); SIDL_CHECK(*exception);"); 
            writer.backTab();
            writer.println("}");
          }
          break;
        case Type.INTERFACE:
        case Type.CLASS:
          writer.println("if (copyArg) {");
          writer.tab();
          writer.println("sidl_io_Deserializer_unpackSerializable(pipe, ");
          writer.tab();
          writer.println("namebuf, &_serial, exception);");
          writer.backTab();
          writer.println("SIDL_CHECK(*exception);");
          writer.println("if (_serial) {");
          writer.tab();
          writer.println("struct sidl_BaseInterface__object *_throwaway;");
          writer.println("arg->" + item.getName() + " = ");
          writer.tab();
          writer.println("(*_serial->d_epv->f__cast)(_serial->d_object,");
          writer.println("\"" + t.getSymbolID().getFullName() + "\",");
          writer.println("exception);");
          writer.backTab();
          writer.println("(*_serial->d_epv->f_deleteRef)(_serial->d_object,");
          writer.tab();
          writer.println("((*exception) ? &_throwaway : exception));");
          writer.backTab();
          writer.backTab();
          writer.println("}");
          writer.println("else {");
          writer.tab();
          writer.println("arg->" + item.getName() + " = NULL;");
          writer.backTab();
          writer.println("}");
          writer.backTab();
          writer.println("}");
          writer.println("else {");
          writer.tab();
          writer.println("char *url;");
          writer.print("sidl_io_Deserializer_unpackString(pipe, namebuf, ");
          writer.println("&url, exception);");
          writer.println("SIDL_CHECK(*exception);");
          writer.println("arg->" + item.getName() + " = ");
          writer.tab();
          writer.printlnUnformatted("#ifdef WITH_RMI");
          writer.println("((url && *url)");
          writer.print("? " + getReturnString(t, context));
          writer.println("__connect(url, exception) : NULL);");
          writer.printlnUnformatted("#else");
          writer.println("NULL;");
          writer.printlnUnformatted("#endif /* WITH_RMI */");
          writer.backTab();
          writer.backTab();
          writer.println("}");
          break;
        case Type.STRUCT:
          writer.print(IOR.getSymbolName(t.getSymbolID()));
          writer.print("__deserialize" + suffix + 
                       "(&(arg->" + item.getName());
          writer.println("), pipe, namebuf, copyArg, exception); SIDL_CHECK(*exception);");
          break;
        default:
          throw new CodeGenerationException("Unexpected type");
        }
        if (i.hasNext()) {
          writer.println("SIDL_CHECK(*exception);");
        }
      }
      writer.println("EXIT:");
      writer.println("free(namebuf);");
      writer.backTab();
      writer.println("}");
      writer.backTab();
      writer.println("}");
    }
    writer.backTab();
    writer.println("}");
  }

  private void includeExtra(Struct strct)
    throws CodeGenerationException
  {
    Set      refs = strct.getSymbolReferences();
    Iterator i    = refs.iterator();
    while (i.hasNext()) { 
      SymbolID id  = (SymbolID)i.next();
      Symbol   sym = Utilities.lookupSymbol(d_context, id);
      if (sym instanceof Extendable) {
        d_writer.generateInclude(C.getHeaderFile(id), true);
        d_writer.generateInclude(IOR.getHeaderFile(id), true);
      }
      else if (sym instanceof Struct) {
        d_writer.generateInclude(C.getHeaderFile(id), true);
      }
    }
  }

  private void generateStruct(Struct strct)
    throws CodeGenerationException
  {
    SymbolID id        = strct.getSymbolID();
    String   source    = C.getStubFile(id);
    String   header    = C.getHeaderFile(id);
    String   iorHeader = IOR.getHeaderFile(id);

    d_writer.writeBanner(strct, source, false, 
      CodeConstants.C_DESC_STUB_PREFIX + id.getFullName());
    d_writer.generateInclude(header, false);
    d_writer.generateInclude(iorHeader, true);
    includeExtra(strct);
    d_writer.generateInclude("sidl_io_Serializer.h", true);
    d_writer.generateInclude("sidl_io_Deserializer.h", true);
    d_writer.generateInclude("sidl_io_Serializable_IOR.h", true);
    d_writer.generateInclude("sidlOps.h", true);
    d_writer.generateInclude("sidl_Exception.h", true);
    d_writer.generateInclude("sidl_CastException.h", true);
    if(strct.hasType(Type.INTERFACE) || strct.hasType(Type.CLASS)) {
      d_writer.generateInclude("sidl_String.h", true);
      d_writer.generateInclude("sidl_BaseClass.h", true);
      d_writer.generateInclude("sidl_BaseInterface.h", true);
      d_writer.generateInclude(C.getHeaderFile(
                               d_context.getSymbolTable().lookupSymbol(
                               BabelConfiguration.getNotImplemented())), true);
    }
    else if(strct.hasType(Type.STRING)) {
      d_writer.generateInclude("sidl_String.h", true);
    }

    
    d_writer.printlnUnformatted("#include <stdlib.h>");
    d_writer.printlnUnformatted("#include <stddef.h>");
    d_writer.printlnUnformatted("#include <string.h>");
    
    d_writer.println();
    writeStructInit(strct, "");
    d_writer.println();
    writeStructDestroy(strct, "");
    d_writer.println();
    writeStructCopy(strct, "");
    d_writer.println();
    if (!d_context.getConfig().getSkipRMI()) {
      writeStructSerialize(d_writer, strct, "", d_context);
      d_writer.println();
      writeStructDeserialize(d_writer,strct, "", d_context);
    }
  }

  /**
   * Generate includes for struct references
   */
  private void generateStructIncludes(Symbol sym)
  {
    SymbolTable table = d_context.getSymbolTable();
    Iterator    i     = sym.getSymbolReferences().iterator();
    while (i.hasNext()) {
      final SymbolID id   = (SymbolID)i.next();
      final Symbol   ssym = table.lookupSymbol(id);
      if (ssym != null) {
         if (ssym instanceof Struct) {
            d_writer.generateInclude(C.getHeaderFile(id), true);
            writeRMISerialization(id, true);
            writeRMISerialization(id, false);
         }
      }
    }
  }

  private void writeRMISerialization(SymbolID id, 
                                     boolean serialize)
  {
    final String name     = IOR.getSymbolName(id);
    String       method   = null;
    String       pipetype = null;

    if (serialize) {
      method   = "serialize";
      pipetype = "sidl_io_Serializer";
    } else {
      method   = "deserialize";
      pipetype = "sidl_io_Deserializer";
    }

    d_writer.printUnformatted("#define RMI_" + name + "_" + method);
    d_writer.printlnUnformatted("(strct, pipe, name, copyArg, exc) { \\");
    d_writer.printUnformatted("  " + pipetype + " __pipe = " + pipetype);
    d_writer.printUnformatted("__cast((pipe), (exc)); ");
    d_writer.printlnUnformatted("SIDL_CHECK(*(exc)); \\");
    d_writer.printlnUnformatted("  if (__pipe) { \\");
    d_writer.printlnUnformatted("    sidl_BaseInterface __throwaway__; \\");
    d_writer.printUnformatted("    " + name + "__" + method);
    d_writer.printUnformatted("((strct), __pipe, (name), (copyArg), ");
    d_writer.printlnUnformatted("(exc)); \\");
    d_writer.printUnformatted("    " + pipetype);
    d_writer.printlnUnformatted("_deleteRef(__pipe, &__throwaway__); \\");
    d_writer.printlnUnformatted("  } \\");
    d_writer.printlnUnformatted("}");
  }

  /**
   * Generate the specified built-in stub.
   */
  private void generateBuiltinStub(SymbolID id, int stubType, int iorType,
                                   boolean doStatic)
    throws CodeGenerationException
  {
    d_writer.writeComment(StubHeader.getBuiltinComment(stubType, doStatic), 
                          false);
    StubHeader.generateBuiltinSignature(d_writer, stubType, id, doStatic, "");
    d_writer.println("{");
    d_writer.tab();
    d_writer.println(StubHeader.getDerefFunctionPtr(
      IOR.getBuiltinName(iorType, doStatic), doStatic) + "(");
    if (!doStatic) {
      d_writer.println(s_self + ",");
    }
    d_writer.println(StubHeader.getBuiltinArgList(stubType) + ");");
    if (doStatic) {
      d_writer.println(s_sepv_reset_func + ";");
    }
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  /**
   * Generate method implementations for the specified interface contract 
   * enforcement method(s).
   */
  private void generateContractStubs(SymbolID id, boolean doStatic) 
    throws CodeGenerationException
  {
    generateBuiltinStub(id, StubHeader.SET_CONTRACTS, IOR.CONTRACTS, doStatic);
    generateBuiltinStub(id, StubHeader.DUMP_STATS, IOR.DUMP_STATS, doStatic);
  }

  private void generateInlinedMethodStub(Extendable ext,
                                         Method method)
    throws CodeGenerationException
  {
    final SymbolID id           = ext.getSymbolID();
    final String   self         = s_self;
    final boolean  inlineMethod = StubHeader.stubInlineMethod(method);

    if (!inlineMethod) {
      generateMethodSignature(d_writer, d_context, self, ext.isInterface(), 
                              id, method, false, true);
      generateMethodStubBody(d_writer, d_context, id, ext.isInterface(), method);
      d_writer.println();
    }
  }

  /**
   * Generate method implementations for the methods in the interface
   * or class.  This method will also generate the constructor and cast
   * functions.
   */
  private void generateMethodStubs(Extendable ext)
    throws CodeGenerationException
  {
    /*
     * Generate the name for this entry point vector as well as a pointer
     * to "self" for this structure.  For classes, self will be the object
     * structure whereas for interfaces it is void*.
     */
    SymbolID id   = ext.getSymbolID();
    String   self = s_self;

    /*
     * Output the normal and remote constructors if this extendable is a class.
     */
    if (!ext.isAbstract()) {
      d_writer.writeComment("Constructor function for the class.", false);
      d_writer.println(C.getSymbolName(id));
      d_writer.print(C.getFullMethodName(id, "_create"));
      d_writer.println("(sidl_BaseInterface* _ex)");
      d_writer.println("{");
      d_writer.tab();
      d_writer.print("return (*(" + s_externals_func);
      d_writer.println("()->createObject))(NULL,_ex);");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();
      
      if (!IOR.isSIDLSymbol(id)) {
        d_writer.writeComment("Wraps up the private data struct pointer (" 
                           + C.getDataName(id) 
                           + ") passed in rather than running the constructor.",
                              true);
        d_writer.println(C.getSymbolName(id));
        d_writer.print(C.getFullMethodName(id, "_wrapObj"));
        d_writer.println("(void* data, sidl_BaseInterface* _ex)");
        d_writer.println("{");
        d_writer.tab();
        d_writer.print("return (*(" + s_externals_func);
        d_writer.println("()->createObject))(data, _ex);");
        d_writer.backTab();
        d_writer.println("}");
        d_writer.println();
      }
    }

    if (!d_context.getConfig().getSkipRMI()) {
      d_writer.printlnUnformatted("#ifdef WITH_RMI");
      d_writer.println();
    
      if (!ext.isAbstract()) {
        d_writer.print("static " + C.getSymbolName(id) + " ");
        d_writer.print(IOR.getRemoteName(id));
        d_writer.println("(const char* url, sidl_BaseInterface *_ex);");
        
        d_writer.writeComment("RMI constructor function for the class.", false);
        d_writer.println(C.getSymbolName(id));
      
        d_writer.print(C.getFullMethodName(id, "_createRemote"));
        d_writer.println("(const char* url, sidl_BaseInterface *_ex)");
      
        d_writer.println("{");
        d_writer.tab();
      
        d_writer.println("return " + IOR.getRemoteName(id) + "(url, _ex);");
      
        d_writer.backTab();
        d_writer.println("}");
        d_writer.println();
      }


      d_writer.print("static " + C.getSymbolObjectPtr(id) + " ");
      d_writer.print(C.getFullMethodName(id, "_remoteConnect"));
      d_writer.print("(const char* url, sidl_bool ar, sidl_BaseInterface *_ex)");
      d_writer.println(";");
      d_writer.print("static " + C.getSymbolObjectPtr(id) + " ");
      d_writer.print(C.getFullMethodName(id, "_IHConnect"));
      d_writer.print("(struct sidl_rmi_InstanceHandle__object* instance, ");
      d_writer.println("sidl_BaseInterface *_ex);");
      
      d_writer.writeComment("RMI connector function for the class.", false);
      d_writer.println(C.getSymbolName(id));
      d_writer.print(C.getFullMethodName(id, "_connect"));
      d_writer.println("(const char* url, sidl_BaseInterface *_ex)");
      
      d_writer.println("{");
      d_writer.tab();
      
      d_writer.print("return " + C.getFullMethodName(id,"_remoteConnect"));
      d_writer.println("(url, TRUE, _ex);");
      
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();
      
      d_writer.printlnUnformatted("#endif /*WITH_RMI*/");
    }
    d_writer.println();

    /*
     * Output the built-in interface contract enforcement methods.
     */
    if (IOR.generateContractBuiltins(ext, d_context)) {
      if (ext.hasStaticMethod(true)) {
        generateContractStubs(id, true);
      }
      generateContractStubs(id, false);
    }

    /*
     * Output each of the method implementations from the interface or class.
     */
    Collection methods = ext.getMethods(true);
    for (Iterator m = methods.iterator(); m.hasNext(); ) {
      Method method = (Method) m.next();
      generateInlinedMethodStub(ext, method);
      if (( method.getCommunicationModifier() == Method.NONBLOCKING )  &&
          !d_context.getConfig().getSkipRMI()) { 
        Method send = method.spawnNonblockingSend();
        generateSingleMethodStub(d_writer, d_context, id, ext.isInterface(),
                                 send, true);
        Method recv = method.spawnNonblockingRecv();
        generateSingleMethodStub(d_writer, d_context, id, ext.isInterface(), 
                                 recv, true);
      }
    }
    
    //Unused and Doesn't work, just comment it out till we can fix it later.
    /* 
     * Output static exec functions
     *
     if (!ext.isInterface()) {
     Class cls = (Class) ext;
     if (cls.hasStaticMethod(true)){
     generateMethodSExecs(cls);
     }
     }
    */
    /*
     * Output the cast methods for the class or interface.
     */
    final Method castMethod = IOR.getBuiltinMethod(IOR.CAST, id, d_context);
    d_writer.writeComment("Cast method for interface and class type "
      + "conversions.", false);
    d_writer.println(C.getSymbolName(id));
    d_writer.println(C.getFullMethodName(id, "_cast") + "(");
    d_writer.tab();
    d_writer.println("void* obj,");
    d_writer.println("sidl_BaseInterface* _ex)");
    d_writer.backTab();
    d_writer.println("{");
    d_writer.tab();
    d_writer.println(C.getSymbolName(id) + " cast = " + C.NULL + ";");
    d_writer.println();

    if (!d_context.getConfig().getSkipRMI()) {
      d_writer.printlnUnformatted("#ifdef WITH_RMI");
      d_writer.println("static int connect_loaded = 0;");
      d_writer.println("if (!connect_loaded) {");
      d_writer.tab();
      d_writer.println("connect_loaded = 1;");
      d_writer.print("sidl_rmi_ConnectRegistry_registerConnect(\"");
      d_writer.print(id.getFullName() + "\", (void*)" + IOR.getSymbolName(id));
      d_writer.println("__IHConnect,_ex);SIDL_CHECK(*_ex);");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.printlnUnformatted("#endif /*WITH_RMI*/");
    }

    d_writer.println("if (obj != " + C.NULL + ") {");
    d_writer.tab();
    d_writer.println("sidl_BaseInterface base = (sidl_BaseInterface) obj;");
    d_writer.print("cast = (" + C.getSymbolName(id) + ") (*base->" + s_epv);
    d_writer.print("->" + IOR.getVectorEntry(castMethod.getLongMethodName()));
    d_writer.println(")(");
    d_writer.tab();
    d_writer.println("base->d_object,");
    d_writer.println("\"" + id.getFullName() + "\", _ex); SIDL_CHECK(*_ex);");
    d_writer.backTab();
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
    d_writer.println("EXIT:");
    d_writer.println("return cast;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();

    d_writer.writeComment("String cast method for interface and class "
      + "type conversions.", false);
    d_writer.println("void*");
    d_writer.println(C.getFullMethodName(id, "_cast2") + "(");
    d_writer.tab();
    d_writer.println("void* obj,");
    d_writer.println("const char* type,");
    d_writer.println("sidl_BaseInterface* _ex)");
    d_writer.backTab();
    d_writer.println("{");
    d_writer.tab();
    d_writer.println("void* cast = " + C.NULL + ";");
    d_writer.println();
    d_writer.println("if (obj != " + C.NULL + ") {");
    d_writer.tab();
    d_writer.println("sidl_BaseInterface base = (sidl_BaseInterface) obj;");
    d_writer.print("cast = (*base->" + s_epv + "->");
    d_writer.print(IOR.getVectorEntry(castMethod.getLongMethodName()));
    d_writer.println(")(base->d_object, type, _ex); SIDL_CHECK(*_ex);");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
    d_writer.println("EXIT:");
    d_writer.println("return cast;");
    d_writer.backTab();
    d_writer.println("}");
    
    if (!d_context.getConfig().getSkipRMI()) {
      /* Generate the __exec method! */
      Method m_exec = IOR.getBuiltinMethod(IOR.EXEC, id, d_context, false); 
      generateInlinedMethodStub(ext, m_exec);
      d_writer.println();


      /* Generate the __getURL method! */
      Method m_geturl = IOR.getBuiltinMethod(IOR.GETURL, id, d_context, false); 
      generateInlinedMethodStub(ext, m_geturl);
      d_writer.println();

      /* Generate the __raddRef method! */
      Method m_raddRef = IOR.getBuiltinMethod(IOR.RADDREF, id, d_context, false); 
      generateInlinedMethodStub(ext, m_raddRef);
      d_writer.println();

      /* Generate the __isRemote method! */
      Method m_isremote = IOR.getBuiltinMethod(IOR.ISREMOTE, id, d_context,
                                               false); 
      generateInlinedMethodStub(ext,m_isremote);
      d_writer.println();

      //Just have this call !isRemote Generate the __isLocal method!
      Method m_isLocal = IOR.getBuiltinMethod(IOR.ISREMOTE, id, d_context, false);
      m_isLocal.setMethodName("_isLocal", "");
      generateMethodSignature(d_writer, d_context, self, ext.isInterface(), id, 
                              m_isLocal, false, true);
      d_writer.println("{");
      d_writer.tab();
      d_writer.print("return !" + id.getFullName().replace('.','_'));
      d_writer.println("__isRemote(self, _ex);");
      d_writer.backTab();
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();    
    }

    /* Generate the __set_hooks method(s)! */
    if (ext.hasStaticMethod(true)) {
      generateBuiltinStub(id, StubHeader.SET_HOOKS, IOR.HOOKS, true);
    }
    Method m_hooks = IOR.getBuiltinMethod(IOR.HOOKS, id, d_context, false);
    generateInlinedMethodStub(ext, m_hooks);


  }

  private static void generateEnumProxies(LanguageWriterForC writer,
                                          Method m,
                                          Context context)
    throws CodeGenerationException
  {
    Iterator i = m.getArgumentList().iterator();
    while (i.hasNext()) {
      final Argument arg = (Argument)i.next();
      if (  (arg.getMode() != Argument.IN) 
         && (arg.getType().getDetailedType() == Type.ENUM )) 
      {
        writer.print(IOR.getReturnString(arg.getType(), context));
        writer.print(" _proxy_" + arg.getFormalName());
        if (arg.getMode() == Argument.INOUT) {
          writer.print(" = *" + arg.getFormalName());
        }
        writer.println(";");
      }
    }
  }

  private static void copyEnumProxies(LanguageWriterForC writer,
                                      Method m)
  {
    Iterator i = m.getArgumentList().iterator();
    while (i.hasNext()) {
      final Argument arg = (Argument)i.next();
      if (  (arg.getMode() != Argument.IN) 
         && (arg.getType().getDetailedType() == Type.ENUM )) 
      {
        writer.print("*" + arg.getFormalName() + " = (");
        writer.print(C.getEnumName(arg.getType().getSymbolID()));
        writer.println(")_proxy_" + arg.getFormalName() + ";");
      }
    }
  }

  public static void generateMethodStubBody(LanguageWriterForC writer,
                                            Context context,
                                            SymbolID id,
                                            boolean isInterface,
                                            Method method)
      throws CodeGenerationException
  {
    String   self = s_self;
    boolean hasReturn = (getReturnString(method.getReturnType(), context) 
                         != "void");
    writer.println("{");
    writer.tab();
    if (hasReturn) {
      writer.print(getReturnString(method.getReturnType(), context));
      writer.println(" _result;");
    }
    generateEnumProxies(writer, method, context);
    if (method.hasRarray()) {
      generateRarrays(writer,method, context);
    }

    if (hasReturn) {
      writer.print("_result = ");
      if (method.getReturnType().getDetailedType() == Type.ENUM) {
        writer.print("(" + C.getEnumName(method.getReturnType().getSymbolID()));
        writer.print(")");
      }
    }

    writer.print(StubHeader.getDerefFunctionPtr(method.getLongMethodName(), 
                                                  method.isStatic()));
    writer.println("(");
    writer.tab();
    C.generateArgumentList(writer, context, self, isInterface, id, method, 
                           false, false, false, true, false, false, true);
    writer.println(");");
    writer.backTab();
    copyEnumProxies(writer, method);
    if (method.hasRarray()) {
      cleanupRarrays(writer,method);
    }
    if (hasReturn) {
      if (Type.ENUM == method.getReturnType().getDetailedType()) {
        writer.print("return (");
        writer.print(C.getEnumName(method.getReturnType().getSymbolID()));
        writer.println(")_result;");
      }
      else {
        writer.println("return _result;");
      }
    }
    writer.backTab();
    writer.println("}");
  }

    /**
     * Generate a single method implementation for the methods in the interface
     * or class.  This method will also generate the constructor and cast
     * functions.
     */
  public static void generateSingleMethodStub(LanguageWriterForC writer,
                                              Context context,
                                              SymbolID id,
                                              boolean isInterface,
                                              Method method,
                                              boolean writeComment)
      throws CodeGenerationException
    {   
      String self = s_self;
      generateMethodSignature(writer, context, self, isInterface, id, method, 
                              false, writeComment);
      generateMethodStubBody(writer, context, id, isInterface, method);
      if (writeComment) {
        writer.println();
      }
    }
    

  /**
   * Generate the method's signature.
   *
   * @param self the String representing the method's self argument variable.
   *
   * @param is_interface the boolean indicating whether working with a class
   *                     or an interface
   *
   * @param id the <code>SymbolID</code> of the <code>Extendable</code> whose
   *   stub source is being written.
   *
   * @param method the <code>Method</code> whose signature is being output.
   *
   * @param is_super is a special parameter that is true ONLY if we are
   *   generating code in Impls for calling super methods.
   *
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception. It can be caused by I/O trouble or
   *    violations of the data type invariants.
   */
  private static void generateMethodSignature(LanguageWriterForC writer,
                                              Context context,
                                              String self,
                                              boolean is_interface,
                                              SymbolID id,
                                              Method method,
                                              boolean is_super,
                                              boolean writeComment)
    throws CodeGenerationException
  {
    if (writeComment) {
      writer.writeComment(method, false);
    }
    if (is_super) {
      writer.print("static ");
    }
    writer.println(getReturnString(method.getReturnType(), context));
    if (is_super) {
      writer.print("super_" + method.getLongMethodName());
    } else {
      writer.print(C.getFullMethodName(id, method));
    }
    writer.println("(");
    writer.tab();
    C.generateArgumentList(writer, context, self, is_interface, id, method, 
                           true, true, false, true, false, true, true);
    writer.println(")");
    writer.backTab();
  }
  /*
   * Generate the _getExternals function that provides access to the IOR 
   * functions either through static linking or dynamic loading.
   */
  private void generateGetExternals(SymbolID id) {
    final String ext_name = IOR.getExternalName(id);
    d_writer.writeComment("Hold pointer to IOR functions.", false);
    d_writer.print("static const " + ext_name + " *" + s_externals);
    d_writer.println(" = " + C.NULL + ";");
    d_writer.writeComment("Lookup the symbol to get the IOR functions.",
                          false);
    d_writer.println("static const " + ext_name + "* _loadIOR(void)");
    
    d_writer.writeComment("Return pointer to internal IOR functions.", false);
    d_writer.println("{");
    d_writer.tab();
    if (BabelConfiguration.isSIDLBaseClass(id)) {
      d_writer.println(s_externals + " = " + IOR.getExternalFunc(id) + "();");
    } else {
      d_writer.printlnUnformatted("#ifdef SIDL_STATIC_LIBRARY");
      d_writer.println(s_externals + " = " + IOR.getExternalFunc(id) + "();");
      d_writer.printlnUnformatted("#else");
      d_writer.print(s_externals + " = (" + ext_name);
      d_writer.print("*)sidl_dynamicLoadIOR(\"" + id.getFullName() + "\",\"");
      d_writer.println(IOR.getExternalFunc(id)+"\") ;");
      d_writer.print("sidl_checkIORVersion(\"" + id.getFullName() + "\", ");
      d_writer.print(s_externals + "->d_ior_major_version, ");
      d_writer.print(s_externals + "->d_ior_minor_version, ");
      d_writer.print(Integer.toString(IOR.MAJOR_VERSION) + ", ");
      d_writer.println(Integer.toString(IOR.MINOR_VERSION) + ");");
      d_writer.printlnUnformatted("#endif");

    }
    d_writer.println("return " + s_externals + ";");
    d_writer.backTab();
    d_writer.println("}");
    
    d_writer.println();
    d_writer.printUnformatted("#define " + s_externals_func + "() (");
    d_writer.printUnformatted(s_externals + " ? " + s_externals);
    d_writer.printlnUnformatted(" : _loadIOR())");
    
    d_writer.println();
  }

  /**
   * Return the pointer that provides access to the static EPV.
   */
  private void generateGetStaticEPV(SymbolID id) {
    String sepv_name = IOR.getSEPVName(id);
    d_writer.writeComment("Hold pointer to static entry point vector",false);
    d_writer.print("static const " + sepv_name + " *" + s_static_epv_var);
    d_writer.println(" = " + C.NULL + ";");
    d_writer.writeComment("Return pointer to static functions.",false);
    d_writer.printUnformatted("#define " + StubHeader.s_sepv_func);
    d_writer.printUnformatted(" (" + s_static_epv_var + " ? ");
    d_writer.printUnformatted(s_static_epv_var + " : (" + s_static_epv_var);
    d_writer.printUnformatted(" = (*(" + s_externals_func);
    d_writer.printlnUnformatted("()->getStaticEPV))()))");
    d_writer.writeComment("Reset point to static functions.",false);
    d_writer.printUnformatted("#define " + s_sepv_reset_func + " (");
    d_writer.printUnformatted(s_static_epv_var + " = (*(" + s_externals_func);
    d_writer.printlnUnformatted("()->getStaticEPV))())");
    d_writer.println();
  }

  private static void cleanupRarrays(LanguageWriterForC writer,
                                     Method m)
    throws CodeGenerationException
  {
    Iterator a;
    boolean  hasInOut = false;
    writer.printlnUnformatted("#ifdef SIDL_DEBUG_REFCOUNT");
    //Get a list of rarrays
    for (a = m.getArgumentList().iterator(); a.hasNext();) {
      Argument arg = (Argument)a.next();
      if (arg.getType().isRarray()) {
        if (arg.getMode() == Argument.IN) {
          writer.print("sidl__array_deleteRef((struct sidl__array*)");
          writer.println(arg.getFormalName() + C.RAW_ARRAY_EXT + ");");
        }
        else {
          hasInOut = true;
        }
      }
    }
    if (hasInOut && !m.getThrows().isEmpty()) {
      writer.println("if (!(*_ex)) {");
      writer.tab();
      for (a = m.getArgumentList().iterator(); a.hasNext();) {
        Argument arg = (Argument)a.next();
        if (arg.getType().isRarray() && (arg.getMode() == Argument.INOUT)) {
          writer.print("sidl__array_deleteRef((struct sidl__array*)");
          writer.println(arg.getFormalName() + C.RAW_ARRAY_EXT + ");");
        }
      }
      writer.backTab();
      writer.println("}");
    }
    writer.printlnUnformatted("#endif /* SIDL_DEBUG_REFCOUNT */");
  }

  /**
   *  This generates the temporary variables for bundling up rarrays into
   *  sidl arrays.
   */
  private static void generateRarrays(LanguageWriterForC writer,
                                      Method m,
                                      Context context) 
    throws CodeGenerationException
  {
    Collection args    = m.getArgumentList();
    ArrayList  rarrays = new ArrayList(args.size());

    //Get a list of rarrays going
    for (Iterator a = args.iterator(); a.hasNext();) {
      Argument arg = (Argument)a.next();
      if (arg.getType().isRarray())
        rarrays.add(arg);
    }

    for (Iterator r = rarrays.iterator(); r.hasNext();) {
      Argument rarray = (Argument) r.next();
      String   r_name = rarray.getFormalName();
      Type     type   = rarray.getType();
      int      dim    = type.getArrayDimension();
      writer.print("int32_t " + r_name + "_lower[" + dim + "], " + r_name);
      writer.print("_upper[" + dim + "], " + r_name + "_stride[" + dim);
      writer.println("]; ");
      String sidl_array_name = getReturnString(type, context);
      writer.print(sidl_array_name.substring(0, sidl_array_name.length()-1));
      writer.println(" " + r_name + "_real;");
      writer.print(sidl_array_name + " " + r_name + C.RAW_ARRAY_EXT + " = &");
      writer.println(r_name + "_real;");
    }
    
    for (Iterator r = rarrays.iterator(); r.hasNext();) {
      Argument rarray         = (Argument)r.next();
      String   r_name         = rarray.getFormalName();
      Type     type           = rarray.getType();
      List     indices        = type.getArrayIndexExprs();
      String   init_func_name = IOR.getArrayNameForFunctions(
                                  rarray.getType().getArrayType().getType()) 
                                 + "_init";
      int      x              = 0;

      for (Iterator i = indices.iterator(); i.hasNext();++x) {
        AssertionExpression ae = (AssertionExpression)i.next();
        writer.print(r_name + "_upper[" + x + "] = ");
        writer.println(ae.accept(new CExprString(), null).toString() + "-1;");
      }
      writer.print(init_func_name + "(" + r_name + ", " + r_name);
      writer.print(C.RAW_ARRAY_EXT + ", " + type.getArrayDimension() + ", ");
      writer.print(r_name + "_lower, " + r_name + "_upper, " + r_name);
      writer.println("_stride);");
    }
  }
}
