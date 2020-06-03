//
// File:        Python.java
// Package:     gov.llnl.babel.backend.python
// Revision:    @(#) $Revision: 7188 $
// Date:        $Date: 2011-09-27 11:38:42 -0700 (Tue, 27 Sep 2011) $
// Description: Python naming conventions shared by Python code generators
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

package gov.llnl.babel.backend.python;

import gov.llnl.babel.BabelConfiguration;
import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeConstants;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.CodeSplicer;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.Utilities;
import gov.llnl.babel.backend.c.C;
import gov.llnl.babel.backend.writers.LanguageWriter;
import gov.llnl.babel.backend.writers.LanguageWriterForC;
import gov.llnl.babel.backend.writers.LanguageWriterForPython;
import gov.llnl.babel.symbols.Argument;
import gov.llnl.babel.symbols.Class;
import gov.llnl.babel.symbols.Comment;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.Struct;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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
public class Python {
  /**
   * Generate an include file for a symbol.
   */
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

  public static String auxFilename(Symbol symbol)
  {
    return symbol.getSymbolID().getShortName() + "_Aux.py";
  }

  public static String auxModule(Symbol symbol)
  {
    return symbol.getSymbolID().getFullName() + "_Aux";
  }


  public static String getAPIVarName(Symbol symbol)
  {
    String fullname = symbol.getFullName().replace('.','_');
    StringBuffer buf = new StringBuffer(fullname.length() + 5);
    buf.append(fullname).append("__API");
    return buf.toString();
  }

  public static String getInternalGuard(Symbol symbol)
  {
    String fullname = symbol.getFullName().replace('.','_');
    StringBuffer buf = 
      new StringBuffer(fullname.length() + 9);
    buf.append(fullname)
      .append("_INTERNAL");
    return buf.toString();
  }

  /**
   * Build a Python support object.
   */
  public Python()
  {
  }
   
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
        (context.getFileManager().createFile(id, type, "PYMOD_HDRS", filename),
         context);
    lw.writeBanner(symbol, filename, CodeConstants.C_IS_IMPL, 
                   description);
    return lw;
  }
   
  /**
   * Generate an IO stream to receive the C stub file for the Python clients.
   *
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception for problems during the code
   *    generation phase.
   */
  public static LanguageWriterForC createStub(Symbol symbol,
                                              String description,
                                              Context context)
    throws CodeGenerationException
  {
    final boolean saveState = context.getFileManager().getJavaStylePackageGeneration();
    final SymbolID id = symbol.getSymbolID();
    final int type = symbol.getSymbolType();
    String filename = sourceFilename(symbol, "Module");
    LanguageWriterForC lw = null;
    try {
      context.getFileManager().setJavaStylePackageGeneration(true);
      lw = new LanguageWriterForC
        (context.getFileManager().createFile(id, type, "PYMOD_SRCS", filename),
         context);
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
      (context.getFileManager().createFile(id, type, "SKELSRCS", filename),
       context);
    lw.writeBanner(symbol, filename, CodeConstants.C_IS_NOT_IMPL,
                   description);
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
  public static LanguageWriterForC createLaunch(Symbol symbol,
                                                String description,
                                                Context context)
    throws CodeGenerationException
  {
    String filename = skelFilename(symbol, "pLaunch");
    final SymbolID id = symbol.getSymbolID();
    final int type = symbol.getSymbolType();
    LanguageWriterForC lw = null;
    lw = new LanguageWriterForC
      (context.getFileManager().createFile(id, type, "LAUNCHSRCS", filename),
       context);
    lw.writeBanner(symbol, filename, CodeConstants.C_IS_NOT_IMPL,
                   description);
    return lw;
  }

  /**
   * Return the name of the function that should be used for the
   * skeleton.
   *
   * @param id  the symbol who owns the method.
   * @param m   the method
   */
  public static String getSkelMethod(SymbolID id, Method m)
  {
    return "pSkel_" + id.getShortName() + "_" + m.getLongMethodName();
  }

  /**
   * Return the name of the function that should be used for the
   * Python stub code.
   *
   * @param id  the symbol who owns the method.
   * @param m   the method
   */
  public static String getStubMethod(SymbolID id, Method m)
  {
    return "pStub_" + id.getShortName() + "_" + m.getLongMethodName();
  }

  /**
   * If <code>filename</code> already exists, extract the code splicer
   * blocks from it and store the contents in the returned code splicer;
   * otherwise, return an empty code splicer.
   * 
   * @param symbol   the symbol whose splicer is to be returned.
   * @param filename the name of the file
   * @return a valid (though possibly empty) <code>CodeSplicer</code>
   * @exception java.io.IOException
   *   where there is IO, there is the possibility for an
   *   <code>IOException</code>.
   */
  public static CodeSplicer getPySplicer(Symbol symbol, 
                                         String filename,
                                         Context context)
    throws java.io.IOException
  {
    final boolean saveState = context.getFileManager().getJavaStylePackageGeneration();
    try {
      context.getFileManager().setJavaStylePackageGeneration(true);
      return context.getFileManager().getCodeSplicer(symbol.getSymbolID(),
                                      symbol.getSymbolType(),
                                      filename, true, false);
    }
    finally {
      context.getFileManager().setJavaStylePackageGeneration(saveState);
    }
  }
   
  /**
   * Create a Python <code>LanguageWriterForPython</code> with a banner
   * comment a documentation string in the <code>FileManager</code> group
   * PYTHON.
   * 
   * @param symbol	 the symbol for which the
   *                    <code>LanguageWriter</code> is being created.
   * @param file	 the name of the file to be created. This contains
   *                    no directory references.
   * @param description a brief statement of the purpose of the file.
   *                    This string should have no newlines.
   * @exception gov.llnl.backend.CodeGenerationException
   *    something went wrong while trying to create the file.
   */
  public static LanguageWriterForPython createPyWriter(Symbol symbol,
                                                       String file,
                                                       String description,
                                                       Context context)
    throws CodeGenerationException
  {
    final boolean saveState = context.getFileManager().getJavaStylePackageGeneration();
    LanguageWriterForPython out = null;
    try {
      context.getFileManager().setJavaStylePackageGeneration(true);
      if (file.charAt(0) != '_') {
        out = new LanguageWriterForPython
          (context.getFileManager().createFile(symbol.getSymbolID(),
                                symbol.getSymbolType(), 
                                "PYTHONSRC", file), context);
      }
      else {
        out = new LanguageWriterForPython
          (context.getFileManager().createFile(symbol.getSymbolID(),
                                symbol.getSymbolType(),
                                "PYTHONADMIN", file), context);
      }
    }
    finally {
      context.getFileManager().setJavaStylePackageGeneration(saveState);
    }
    Comment comment = symbol.getComment();
    out.writeBanner(symbol, file, CodeConstants.C_IS_NOT_IMPL,
                    description);
    if (comment != null){
      String [] lines = comment.getComment();
      if (lines != null){
        // print the comment as a Python DOC string
        out.println();
        out.print("\"\"\"");
        for(int i = 0; i < lines.length; i++){
          out.println(LanguageWriterForPython.toPythonString(lines[i]));
        }
        out.println("\"\"\"");
      }
    }
    out.println();
    return out;
  }

   /**
    * Convert a sidl symbol into the name of its associated set EPV
    * method, which is the symbol name appended with "__set_epv".
    */
   public static String getSetEPVName(SymbolID id) {
      return IOR.getSymbolName(id) + "__impl_set_epv";
   }

   /**
    * Convert a sidl symbol into the name of its associated get RMI
    * Externals method, which is the symbol name appended with 
    * "__impl_rmi_externals".
    */
   public static String getRMIExternName(SymbolID id) {
      return IOR.getSymbolName(id) + "__impl_rmi_externals";
   }

   /**
    * Convert a sidl symbol into the name of its associated set static
    * EPV method, which is the symbol name appended with "__set_sepv".
    */
   public static String getSetSEPVName(SymbolID id) {
      return IOR.getSymbolName(id) + "__impl_set_sepv";
   }

  public static String getImport(String className) {
    return className.replace('.', '_')+"__import";
  }

  public static String getExtendableImport(Symbol symbol) {
    String symbolName = symbol.getFullName().replace('.', '_');
    StringBuffer buf = new StringBuffer(symbolName.length() + 8);
    buf.append(symbolName)
      .append("__import");
    return buf.toString();
  }

  public static String getExtendableWrapper(Symbol symbol) {
    String symbolName = symbol.getFullName();
    StringBuffer buf = new StringBuffer(symbolName.length() + 7);
    buf.append(symbolName.replace('.','_'))
      .append("__wrap");
    return buf.toString();
  }

  public static String getExtendableBorrow(Symbol symbol) {
    String symbolName = symbol.getFullName();
    StringBuffer buf = new StringBuffer(symbolName.length() + 9);
    buf.append(symbolName.replace('.','_'))
      .append("__weakRef");
    return buf.toString();
  }

  public static String getExceptionType(Symbol symbol) {
    String symbolName = symbol.getFullName();
    StringBuffer buf = new StringBuffer(symbolName.length() + 7);
    buf.append(symbolName.replace('.', '_'))
      .append("__type");
    return buf.toString();
  }

  public static int maxNameLength(Collection items)
  {
    int nameLength = 0;
    Iterator i = items.iterator();
    while (i.hasNext()) {
      final int size = ((Struct.Item)i.next()).getName().length();
      if (size > nameLength) {
        nameLength = size;
      }
    }
    return nameLength;
  }

  public static String getExtendableConverter(Symbol symbol) {
    String symbolName = symbol.getFullName();
    StringBuffer buf = new StringBuffer(symbolName.length() + 7);
    buf.append(symbolName.replace('.','_'))
      .append("__convert");
    return buf.toString();
  }

  public static String getExtendableNewRef(Symbol symbol) {
    String symbolName = symbol.getFullName();
    StringBuffer buf = new StringBuffer(symbolName.length() + 7);
    buf.append(symbolName.replace('.','_'))
      .append("__newRef");
    return buf.toString();
  }

  public static String getExtendableType(Symbol symbol) {
    String symbolName = symbol.getFullName();
    StringBuffer buf = new StringBuffer(symbolName.length() + 7);
    buf.append(symbolName.replace('.','_'))
      .append("_PyType");
    return buf.toString();
  }

  public static String getPyStructType(Symbol symbol)
  {
    return symbol.getFullName().replace('.','_') + "__pyInst";
  }

  public static String getStructInit(Symbol symbol) {
    String symbolName = symbol.getFullName();
    StringBuffer buf = new StringBuffer(symbolName.length() + 13);
    buf.append(symbolName.replace('.','_'))
      .append("__pyInit");
    return buf.toString();
  }

  public static String getStructCopy(Symbol symbol) {
    String symbolName = symbol.getFullName();
    StringBuffer buf = new StringBuffer(symbolName.length() + 13);
    buf.append(symbolName.replace('.','_'))
      .append("__pyCopy");
    return buf.toString();
  }

  public static String getStructBorrow(Symbol symbol) {
    String symbolName = symbol.getFullName();
    StringBuffer buf = new StringBuffer(symbolName.length() + 13);
    buf.append(symbolName.replace('.','_'))
      .append("__pyBorrow");
    return buf.toString();
  }

  public static String getStructDestroy(Symbol symbol) {
    String symbolName = symbol.getFullName();
    StringBuffer buf = new StringBuffer(symbolName.length() + 13);
    buf.append(symbolName.replace('.','_'))
      .append("__pyDestroy");
    return buf.toString();
  }

  public static String getStructSerialize(Symbol symbol) {
    String symbolName = symbol.getFullName();
    StringBuffer buf = new StringBuffer(symbolName.length() + 13);
    buf.append(symbolName.replace('.','_'))
      .append("__pySerialize");
    return buf.toString();
  }

  public static String getStructDeserialize(Symbol symbol) {
    String symbolName = symbol.getFullName();
    StringBuffer buf = new StringBuffer(symbolName.length() + 15);
    buf.append(symbolName.replace('.','_'))
      .append("__pyDeserialize");
    return buf.toString();
  }

  public static String getExtendableAddRef(Symbol symbol) {
    String symbolName = symbol.getFullName();
    StringBuffer buf = new StringBuffer(symbolName.length() + 7);
    buf.append(symbolName.replace('.','_'))
      .append("__addRef");
    return buf.toString();
  }

  public static String getExtendableConnect(Symbol symbol) {
    String symbolName = symbol.getFullName();
    StringBuffer buf = new StringBuffer(symbolName.length() + 10);
    buf.append(symbolName.replace('.','_'))
      .append("__connectI");
    return buf.toString();
  }
  
  /*  public static String getExtendableCast(Symbol symbol) {
    String symbolName = symbol.getFullName();
    StringBuffer buf = new StringBuffer(symbolName.length() + 7);
    buf.append(symbolName.replace('.','_'))
      .append("__cast");
    return buf.toString();
  }
  */

  public static String getExtendableDeref(Symbol symbol) {
    return symbol.getFullName().replace('.','_') + "_deref";
  }

  public static String getBorrowArrayFromPython(Type arrayType) {
    if (arrayType == null) {
      return "sidl_generic_borrow_python_array";
    }
    else {
      switch (arrayType.getDetailedType()) {
      case Type.INTERFACE:
      case Type.CLASS:
        return arrayType.getSymbolID().getFullName().replace('.', '_')
          + "__convert_python_array";
      case Type.ENUM:
        return "sidl_long__borrow_python_array";
      default:
        return "sidl_" + arrayType.getTypeString() + "__borrow_python_array";
      }
    }
  }

  public static String getBorrowArrayFromSIDL(Type arrayType) {
    if (arrayType == null) {
      return BabelConfiguration.getBaseInterface().replace('.', '_')
        + "__convert_generic_array";
    }
    else {
      switch(arrayType.getDetailedType()) {
      case Type.INTERFACE:
      case Type.CLASS:
        return arrayType.getSymbolID().getFullName().replace('.', '_')
          + "__convert_sidl_array";
      default:
        return "sidl_python_borrow_array";
      }
    }
  }

  public static String getCopyArrayFromPython(Type arrayType) {
    final Type elemType = arrayType.getArrayType();
    if (elemType == null) {
      return "sidl_generic_borrow_python_array";
    }
    else {
      switch(elemType.getDetailedType()) {
      case Type.CLASS:
      case Type.INTERFACE:
        return elemType.getSymbolID().getFullName().replace('.', '_')
          + "__convert_python_array";
      default:
        final String atype = 
          (elemType.getDetailedType() == Type.ENUM) ? "long" :
          elemType.getTypeString();
        if (arrayType.getArrayOrder() == Type.COLUMN_MAJOR) {
          return "sidl_" + atype + "__clone_python_array_column";
        }
        else if (arrayType.getArrayOrder() == Type.ROW_MAJOR) {
          return "sidl_" + atype + "__clone_python_array_row";
        }
        else {
          return "sidl_" + atype + "__borrow_python_array";
        }
      }
    }
  }

  public static String getCopyArrayFromSIDL(Type arrayType) {
    if (arrayType == null) {
      return BabelConfiguration.getBaseInterface().replace('.', '_')
        + "__convert_generic_array";
    }
    else {
      switch(arrayType.getDetailedType()) {
      case Type.CLASS:
      case Type.INTERFACE:
        return arrayType.getSymbolID().getFullName().replace('.', '_')
          + "__convert_sidl_array";
      default:
        return "sidl_python_clone_array";
      }
    }
  }

  public static void leavePython(LanguageWriter lw)
  {
    lw.printlnUnformatted("#if (PY_VERSION_HEX >= 0x02040000)");
    lw.println("Py_BEGIN_ALLOW_THREADS");
    lw.println("sidl_Python_LogUnlock(__func__, __FILE__, __LINE__);");
    lw.printlnUnformatted("#endif /* Python 2.4 or later */");
  }

  public static void resumePython(LanguageWriter lw)
  {
    lw.printlnUnformatted("#if (PY_VERSION_HEX >= 0x02040000)");
    lw.println("Py_END_ALLOW_THREADS");
    lw.println("sidl_Python_LogRelock(__func__, __FILE__, __LINE__);");
    lw.printlnUnformatted("#endif /* Python 2.4 or later */");
  }

  public static String getDestroyArray(Type arrayType) {
    return "sidl_python_deleteRef_array";
  }


  /**
    * Convert a SIDL symbol into the name of its associated remote
    * connector.  This requires both the SybmolID of the class this is being
    * defined in (sourceid) and the SymbolID of the target class to be
    * connected (targetid) 
    */
  public static String getPSkelFConnectName(SymbolID sourceid, SymbolID targetid) {
    return "pskel_"+IOR.getSymbolName(sourceid)+"_fconnect_" + IOR.getSymbolName(targetid);
   }

  /**
    * Convert a SIDL symbol into the name of its associated remote
    * (de)serialize method.  This requires both the SybmolID of the class
    * this is being defined in (sourceid) and the SymbolID of the target
    * class to be connected (targetid) 
    */
  public static String getPSkelSerializeName(SymbolID sourceid, 
                                             SymbolID targetid, 
                                             boolean serialize,
                                             boolean inLaunch) {
    return (inLaunch ? "" : "p") +
      "skel_"+IOR.getSymbolName(sourceid)+
      (serialize ? "_serialize_" : "_deserialize_") +
      IOR.getSymbolName(targetid);
   }

  /**
    * Convert a SIDL symbol into the name of its associated remote
    * connector.  This requires both the SybmolID of the class this is being
    * defined in (sourceid) and the SymbolID of the target class to be
    * connected (targetid) 
    */
  public static String getPSkelFCastName(SymbolID sourceid, SymbolID targetid) {
    return "pskel_"+IOR.getSymbolName(sourceid)+"_fcast_" + IOR.getSymbolName(targetid);
   }

  public static Method createRemoteMethod(Extendable ext, Context context) {
    SymbolID id = ext.getSymbolID();
    Method ret = new Method(context);
    Argument url = new Argument(Argument.IN, new Type(Type.STRING), "url");
    Argument self = new Argument(Argument.OUT, new Type(id, context), "self");
    //Type retType = new Type(ext.getSymbolID());
    ret.setMethodName("_create","Remote");

    ret.setDefinitionModifier(Method.STATIC);
    ret.setReturnType(new Type(Type.VOID)); 
    if (!context.getConfig().getSkipRMI()) {
      ret.addThrows(context.getSymbolTable().lookupSymbol("sidl.rmi.NetworkException").getSymbolID());
    }
    ret.addArgument(self);
    ret.addArgument(url);
    return ret;
  }

  public static Method connectRemoteMethod(Extendable ext, Context context) {
    SymbolID id = ext.getSymbolID();
    Method ret = new Method(context);
    Argument url = new Argument(Argument.IN, new Type(Type.STRING), "url");
    Argument self = new Argument(Argument.OUT, new Type(id, context), "self");
    //Type retType = new Type(ext.getSymbolID());
    ret.setMethodName("_connect");
 
    ret.setDefinitionModifier(Method.STATIC);
    if (!context.getConfig().getSkipRMI()) {
      ret.addThrows(context.getSymbolTable().lookupSymbol("sidl.rmi.NetworkException").getSymbolID());
    }
    ret.addArgument(self);
    ret.setReturnType(new Type(Type.VOID)); 
    ret.addArgument(url);
    return ret;
  }

  private static void generateRMISerialize(Class cls, 
                                           LanguageWriterForC lw,
                                           Context context,
                                           boolean serialize)
    throws CodeGenerationException
  {
    SymbolID pipe = Utilities.
      lookupSymbol(context, serialize ? "sidl.rmi.Return" : "sidl.rmi.Call");
    ArrayList serializeSIDs = 
      Utilities.sort(IOR.getStructSymbolIDs(cls, serialize));
    ArrayList con_args = new ArrayList(4);
    con_args.add(null);
    con_args.add(new 
                 Argument(Argument.IN, 
                          new Type(pipe, Type.INTERFACE, null, 0, 0, context),
                          "pipe"));
    con_args.add(new Argument(Argument.IN, new Type(Type.STRING), "name"));
    con_args.add(new Argument(Argument.IN, new Type(Type.BOOLEAN), "copy"));
    for (Iterator i = serializeSIDs.iterator(); i.hasNext(); ) {
      final SymbolID did = (SymbolID)i.next();
      lw.println("void (*" +
               IOR.getVectorEntry(getPSkelSerializeName(cls, did, serialize, false)) +
               ")(");
      lw.tab();
      con_args.set(0,
                   new Argument(serialize ? Argument.IN : Argument.OUT,
                                new Type(did, Type.STRUCT, null, 0, 
                                         0, context),
                                "strct"));
      IOR.generateArguments(lw, context,
                            "", con_args, true, true, null, true, true, false, false);
      lw.println(");");
      lw.backTab();
      lw.println("");
    }

  }

  public static void writeModuleDefBlock(LanguageWriterForC lw)
  {
    lw.printlnUnformatted("#if PY_MAJOR_VERSION >= 3");
    lw.printlnUnformatted("#define MOD_ERROR_VAL NULL");
    lw.printlnUnformatted("#define MOD_SUCCESS_VAL(val) val");
    lw.printlnUnformatted("#define MOD_INIT(name) PyMODINIT_FUNC PyInit_##name(void)");
    lw.printlnUnformatted("#define MOD_DEF(ob, name, doc, methods)	\\");
    lw.printlnUnformatted("static struct PyModuleDef moduledef = {	      \\");
    lw.printlnUnformatted("PyModuleDef_HEAD_INIT, name, doc, -1, methods, }; \\");
    lw.printlnUnformatted("ob = PyModule_Create(&moduledef);");
    lw.printlnUnformatted("#else");
    lw.printlnUnformatted("#define MOD_ERROR_VAL");
    lw.printlnUnformatted("#define MOD_SUCCESS_VAL(val)");
    lw.printlnUnformatted("#define MOD_INIT(name) void init##name(void)");
    lw.printlnUnformatted("#define MOD_DEF(ob, name, doc, methods)			\\");
    lw.printlnUnformatted("ob = Py_InitModule3(name, methods, doc);");
    lw.printlnUnformatted("#endif");
  }

  /** This method generates the rmi struct that allows connect and rmicast
   * to be called through the pSkel.
   */
  public static void generateRMIExternStruct(Class cls, LanguageWriterForC lw,
                                             Context context) 
  throws CodeGenerationException {
    SymbolID id = cls.getSymbolID();
    ArrayList fconnectSIDs = Utilities.sort(IOR.getFConnectSymbolIDs(cls));
    lw.println("struct "+IOR.getSymbolName(id)+"__rmiExternals {");
    lw.tab();
    ArrayList con_args = new ArrayList(2);
    
    con_args.add(new Argument(Argument.IN, new Type(Type.STRING), "url"));
    con_args.add(new Argument(Argument.IN, new Type(Type.BOOLEAN), "ar"));
    for (Iterator i = fconnectSIDs.iterator(); i.hasNext(); ) {
      SymbolID destination_id = (SymbolID) i.next();
       
      lw.print(C.getSymbolObjectPtr(destination_id));
      lw.print(" (*");
      lw.print(IOR.getVectorEntry(getPSkelFConnectName(id,destination_id)));
      lw.println(")(");
      lw.tab();
      IOR.generateArguments(lw, context,
                            "", con_args, true, true, null, true, true, false, false);
      lw.println(");");
      lw.backTab();

      //Forward declaration for the used functions
      lw.println();
    }
    fconnectSIDs = null;
    if (!context.getConfig().getSkipRMI()) {
      generateRMISerialize(cls, lw, context, true);
      generateRMISerialize(cls, lw, context, false);
    }
    lw.backTab();
    lw.println("};");
  }


}
