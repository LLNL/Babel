//
// File:        ImplHeader.java
// Package:     gov.llnl.babel.backend.c
// Revision:    @(#) $Id: ImplHeader.java 7188 2011-09-27 18:38:42Z adrian $
// Description: generate C implementation header to a pretty writer stream
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
import gov.llnl.babel.backend.CodeSplicer;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.Utilities;
import gov.llnl.babel.backend.writers.LanguageWriterForC;
import gov.llnl.babel.Context;
import gov.llnl.babel.symbols.Argument;
import gov.llnl.babel.symbols.Class;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.Type;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * Class <code>ImplHeader</code> generates a C implementation header to a
 * language writer output stream.  The constructor takes a language writer
 * stream and method <code>generateCode</code> generates the implementation
 * header file for the specified symbol to the output stream.  The language
 * writer stream is not closed by this object.
 */
public class ImplHeader {

  /**
   * The writer is the output stream for the new file. The content of the
   * new file is writtern to this writer.
   */
  private LanguageWriterForC d_writer;

  /**
   * The splicer is used when replacing an existing file. Sections from the
   * previous file are stored in the splicer and can be grafted back into
   * the file being generated.
   */
  private CodeSplicer        d_splicer;

  private Context d_context;

  /**
   * Create a <code>ImplHeader</code> object that will write symbol
   * information to the provided output language writer stream.
   *
   * Assumption:  This code is completed generated so no code splicer
   * capabilities are needed.
   *
   * @param writer  the output writer for the new implementation header
   *                file. This writer will not be closed by this method.
   */
  public ImplHeader(LanguageWriterForC writer, CodeSplicer splicer,
                    Context context) {
    d_splicer = splicer;
    d_writer  = writer;
    d_context = context;
  }

  /**
   * This is a convenience utility function that writes the symbol
   * header information into the provided language writer output stream.
   * The output stream is not closed on exit.
   *
   * @param cls     the <code>Class</code> whose implementation header file
   *                is to be created.
   * @param writer  the output writer with which the new header file is
   *                created.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception. It can be caused by I/O trouble or
   *    violations of the data type invariants.
   *
   */
  public static void generateCode(Class cls, LanguageWriterForC writer,
                                  CodeSplicer splicer,
                                  Context context)
    throws CodeGenerationException
  {
    ImplHeader header = new ImplHeader(writer, splicer, context);
    header.generateCode(cls);
  }

  /**
   * Output a banner and the main header guard at the top of the file.
   *
   * @param cls the <code>Class</code> whose header will be written.
   */
  private void writePrologue(Class cls) {
    String header = C.getImplHeaderFile(cls);
    d_writer.writeBanner(cls, header, true, CodeConstants.C_DESC_IMPL_PREFIX 
      + cls.getFullName());
    d_writer.openHeaderGuard(header);
  }

  /**
   * Output the includes for the implementation header file.
   *
   * @param cls  the <code>Class</code> whose header will be written.
   */
  private void writeIncludeSection(Class cls, Collection localRefs) {
    String includes = cls.getFullName() + "." + "_hincludes";
    d_writer.generateInclude("sidl_header.h", true);
    if(cls.hasOverwrittenMethods()) {
      d_writer.generateInclude(IOR.getHeaderFile(cls), true);
    }

    Iterator i = Utilities.sort(localRefs).iterator();
    while (i.hasNext()) {
      SymbolID sid = (SymbolID)i.next();
      d_writer.generateInclude(C.getHeaderFile(sid), true);
    }
    i = null;

    d_splicer.splice(includes, d_writer, "include files");
    d_writer.println();
  }

  /**
   * Output the signatures for the get private data access methods.
   *
   * @param cls  the <code>Class</code> whose header will be written.
   */
  private void writeGetPrivateDataDecl(Class cls) {
      d_writer.println("extern " + C.getDataName(cls) + "*");
    d_writer.println(C.getDataGetName(cls) + "(");
    d_writer.tab();
    d_writer.println(C.getObjectName(cls)+ ");");
    d_writer.backTab();
    d_writer.println();
  }

  /**
   * Output the signatures for the set private data access methods.
   *
   * @param cls  the <code>Class</code> whose header will be written.
   */
  private void writeSetPrivateDataDecl(Class cls) {
    d_writer.println("extern void");
    d_writer.println(C.getDataSetName(cls) + "(");
    d_writer.tab();
    d_writer.println(C.getObjectName(cls) + ",");
    d_writer.println(C.getDataName(cls) + "*);");
    d_writer.backTab();
    d_writer.println();
  }

  /**
   * This will write an external declaration for a function that
   * wraps a method from the IOR. This routine implemented in the skeleton
   * provides access to the IOR method.
   *
   * @param cls       the <code>Class</code> whose header will be written.
   * @param funcName  the name of the wrapper function.
   * @param iorMethod a description of the IOR method to be wrapped.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception. It can be caused by I/O trouble or
   *    violations of the data type invariants.
   */
  private void writeIORWrapperDecl(Class cls, String funcName, Method iorMethod)
    throws CodeGenerationException
  {
    Iterator args = Utilities.extendArgs(cls, iorMethod,
                                         false, d_context).iterator();
    d_writer.println("extern void");
    d_writer.print(funcName);
    d_writer.println("(");
    d_writer.tab();
    while (args.hasNext()) {
      Argument a = (Argument)args.next();
      d_writer.print(getArgumentString(a));
      if (args.hasNext()) {
        d_writer.print(", ");
      }
    }
    d_writer.println(");");
    d_writer.backTab();
    d_writer.println();
  }

   /**
    * Generate an argument string for the specified sidl argument.
    * The formal argument name is not included.
    *
    * WARNING:  In general we do not want the object pointer version
    *           of the argument's type so the IOR source code has
    *           been changed to use the C version when that version
    *           is needed leaving this one for all other cases (especially
    *           back ends that rely on it).
    */
  private String getArgumentString(Argument arg)
     throws CodeGenerationException
   {
     return IOR.getArgumentString(arg, d_context, false, false, false);
   }

  /**
   * Write the method signature with arguments indented one level.  The 
   * caller is expected to decrease the indentation one level after
   * terminating the signature with either a simple println() or a
   * println(";") as needed.
   *
   * @param lw        the target language writer 
   * @param meth      the method 
   * @param self      the self variable, if appropriate
   * @param addExtern TRUE if the signature is to include the "extern" 
   *                  modifier (as in the header file case).
   * @param isIfc     TRUE if the method belongs to an interface
   * @param id        the name of the symbol owning the method
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception. It can be caused by I/O trouble or
   *    violations of the data type invariants.
   */
  public static void writeMethodSignature(LanguageWriterForC lw, 
                                          Method meth,
                                          String self,
                                          boolean addExtern, boolean isIfc, 
                                          boolean addType, SymbolID id,
                                          Context context)
    throws CodeGenerationException
  {
    Type    returnType    = meth.getReturnType();
    String  returnTypeStr = getReturnString(returnType, context);
    if (addExtern) {
      lw.println("extern");
    }
    else {
      lw.printlnUnformatted("#ifdef __cplusplus");
      lw.println("extern \"C\"");
      lw.printlnUnformatted("#endif");
    }

    lw.println(returnTypeStr);

    lw.print(C.getMethodImplName(id, meth.getLongMethodName()));
    lw.println("(");
    lw.tab();
    C.generateArgumentList(lw, context,
                           self, isIfc, id, meth, true, addType, false, 
                           true, false, true, true);
    lw.print(")");
  }

   /**
    * Generate a return string for the specified SIDL type.  Most
    * of the SIDL return strings are listed in the static structures defined
    * at the start of this class.  Symbol types and array types require
    * special processing.
    */
   private static String getReturnString(Type type,
                                         Context context)
     throws CodeGenerationException
   {
     return IOR.getReturnString(type, context, false, false);
   }

  /**
   * Write a method declaration for a generic implementation method.
   *
   * @param lw       the language writer to write to
   * @param isIfc     TRUE if the method belongs to an interface
   * @param m        the method description
   * @param id       the name of the symbol owning the method
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception. It can be caused by I/O trouble or
   *    violations of the data type invariants.
   */
  private static void writeMethodDecl(LanguageWriterForC lw, boolean isIfc, 
                                      Method m, SymbolID id, Context context)
    throws CodeGenerationException
  {
    writeMethodSignature(lw, m, Utilities.s_self, true, isIfc, true, 
                         id, context);
    lw.println(";");
    lw.backTab();
    lw.println();
  }

  /**
   * Write external declarations for all non-<code>abstract</code> methods
   * in the class.
   *
   * @param lw       the language writer to write to.
   * @param cls      the <code>Class</code> whose header will be written.
   * @param isStatic <code>true</code> means write only static methods;
   *                 <code>false</code> means write only non-static
   *                 methods.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception. It can be caused by I/O trouble or
   *    violations of the data type invariants.
   */
  public static void writeMethodDecls(LanguageWriterForC lw, 
                                      Class cls,
                                      boolean isStatic,
                                      Context context)
    throws CodeGenerationException
  {
    Iterator i = null;
    if (isStatic) {
      i = cls.getStaticMethods(false).iterator();
    } else {
      i = cls.getNonstaticMethods(false).iterator();
    }
    while (i.hasNext()) {
      Method m = (Method)i.next();
      if (!m.isAbstract()) {
        if (IOR.generateHookMethods(cls, context)) {
          Method pre = m.spawnPreHook();
          Method post = m.spawnPostHook();
          writeMethodDecl(lw, false, pre, cls, context);
          writeMethodDecl(lw, false, m, cls, context);
          writeMethodDecl(lw, false, post, cls, context);

        } else {
          writeMethodDecl(lw, false, m, cls, context);
        }
      }
    }
    if (!context.getConfig().getSkipRMI()) {
      writeRMIAccessDecls(lw,cls,isStatic);
    }
  }


  /**
   * Write external declarations for the RMI fconnects methods in the class.
   *
   * @param lw       the language writer to write to.
   * @param cls      the <code>Class</code> whose header will be written.
   * @param isStatic <code>true</code> means write only static methods;
   *                 <code>false</code> means write only non-static
   *                 methods.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception. It can be caused by I/O trouble or
   *    violations of the data type invariants.
   */
  public static void writeRMIAccessDecls(LanguageWriterForC lw, Class cls,
                                      boolean isStatic)
    throws CodeGenerationException
  {
    Set fconnectSIDs = IOR.getFConnectSymbolIDs(cls);
    Set serializeSIDs = IOR.getStructSymbolIDs(cls, true);
    Set deserializeSIDs = IOR.getStructSymbolIDs(cls, false);

    lw.printlnUnformatted("#ifdef WITH_RMI");

    for (Iterator i = fconnectSIDs.iterator(); i.hasNext(); ) {
      SymbolID l_id = (SymbolID) i.next();
      lw.print("extern " + C.getSymbolObjectPtr(l_id) + " ");
      lw.print(C.getImplFConnectName(cls, l_id) + "(");
      lw.println("const char* url, sidl_bool ar, sidl_BaseInterface *_ex);");
    }

    lw.printlnUnformatted("#endif /*WITH_RMI*/");

    for (Iterator i = serializeSIDs.iterator(); i.hasNext(); ) {
      SymbolID l_id = (SymbolID) i.next();
      lw.println("extern void");
      lw.print(IOR.getSkelSerializationName(cls, l_id, true) + "(");
      lw.print("const " + IOR.getStructName(l_id) + " *strct, ");
      lw.print("struct sidl_rmi_Return__object *pipe, ");
      lw.print("const char *name, sidl_bool copyArg, ");
      lw.println(IOR.getExceptionFundamentalType() + " *exc);");
    }
    for (Iterator i = deserializeSIDs.iterator(); i.hasNext(); ) {
      SymbolID l_id = (SymbolID) i.next();
      lw.println("extern void");
      lw.print(IOR.getSkelSerializationName(cls, l_id, false) + "(");
      lw.print(IOR.getStructName(l_id) + " *strct, ");
      lw.print("struct sidl_rmi_Call__object *pipe, ");
      lw.print("const char *name, ");
      lw.print("sidl_bool copyArg, ");
      lw.println(IOR.getExceptionFundamentalType() + " *exc);");
    }


  }



  /**
   * Write external declarations for the <code>Class</code> constructor and
   * destructor.
   *
   * @param lw   the language writer to write to.
   * @param cls  the <code>Class</code> whose header will be written.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception. It can be caused by I/O trouble or
   *    violations of the data type invariants.
   */
  public static void writeBuiltinDecls(LanguageWriterForC lw, 
                                       Class cls,
                                       Context context)
    throws CodeGenerationException
  {
    if(cls.hasOverwrittenMethods()) {
      //function from the Impl file that sets the superEPV
      lw.println("extern void " + C.getObjectName(cls) + "__superEPV(");
      lw.print("struct " + C.getObjectName(cls.getParentClass()));
      lw.println("__epv*);");
      lw.println();
    }
    writeMethodDecl(lw, false, IOR.getBuiltinMethod(IOR.LOAD, cls, context), 
                    cls, context);
    writeMethodDecl(lw, false, IOR.getBuiltinMethod(IOR.CONSTRUCTOR, 
                                                    cls, context), 
                    cls, context);
    writeMethodDecl(lw, false, IOR.getBuiltinMethod(IOR.CONSTRUCTOR2, 
                                                    cls, context), 
                    cls, context);
    writeMethodDecl(lw, false, IOR.getBuiltinMethod(IOR.DESTRUCTOR, 
                                                    cls, context),  
                    cls, context);
  }

  public Collection getLocalReferences(Extendable ext) 
    throws CodeGenerationException
  {
    Set refs = ext.getObjectDependencies();
    refs.add(ext); 
    return refs;
  }

  /**
   * Define the data structure for the private class data. Each
   * implementation class has some private data associated with it. The
   * struct to hold that information is defined by this method.
   * 
   * @param cls  the <code>Class</code> whose data header will be written.
   */
  private void writePrivateDataType(Class cls) {
    String name = cls.getFullName();
    d_writer.writeComment("Private data for class " + name, false);

    d_writer.println(C.getDataName(cls) +" {");
    d_writer.tab();

    String data = cls.getFullName() + "." + "_data";
    d_splicer.splice(data, d_writer, "private data members", "int ignore; " 
      + "/* dummy to force non-empty struct; remove if you add data */");

    d_writer.backTab();
    d_writer.println("};");
    d_writer.println();
  }

  /**
   * Write C implementation header information for the provided class
   * to the language writer output stream provided in the constructor.
   * This method does not close the writer output stream.
   *
   * @param cls  the <code>Class</code> whose header will be written.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception. It can be caused by I/O trouble or
   *    violations of the data type invariants.
   */
  public void generateCode(Class cls) throws CodeGenerationException {
     final String fullname = cls.getFullName();
    Collection localRefs = getLocalReferences(cls);
    if (d_context.getConfig().getRenameSplicers()) {
      d_splicer.renameSymbol(fullname + "._misc", fullname + "._hmisc");
      d_splicer.renameSymbol(fullname + "._includes", 
                             fullname + "._hincludes");
    }
    writePrologue(cls);
    writeIncludeSection(cls, localRefs);
    //writeForwardStructDecls(cls, localRefs);
    writePrivateDataType(cls);
    d_writer.openCxxExtern();
    d_writer.writeComment("Access functions for class private data and built-in"
      + " methods", false);
    writeGetPrivateDataDecl(cls);
    writeSetPrivateDataDecl(cls);
    if (cls.getParentClass() == null) {
      writeIORWrapperDecl(cls, C.getPrivateDestructor(cls),
                          IOR.getBuiltinMethod(IOR.DELETE, cls, d_context));
    }
    writeBuiltinDecls(d_writer, cls, d_context);
    d_writer.writeComment("User-defined object methods", false);
    writeMethodDecls(d_writer, cls, true, d_context);  // static methods
    writeMethodDecls(d_writer, cls, false, d_context); // object methods

    d_writer.println();
    d_splicer.splice("_hmisc", d_writer, "miscellaneous things");
    d_writer.println();

    /*
     * Close the header file and include unused splicer symbols, if any.
     */
    if (d_splicer.hasUnusedSymbolEdits()) {
      d_writer.println();
      d_writer.printlnUnformatted("#error File has unused splicer blocks.");
      d_writer.beginBlockComment(true);
      d_writer.println(CodeConstants.C_BEGIN_UNREFERENCED_METHODS);
      d_writer.println(CodeConstants.C_UNREFERENCED_COMMENT1);
      d_writer.println(CodeConstants.C_UNREFERENCED_COMMENT2);
      d_writer.println(CodeConstants.C_UNREFERENCED_COMMENT3);
      d_writer.endBlockComment(true);
      d_splicer.outputUnusedSymbolEdits(d_writer);
      d_writer.writeCommentLine(CodeConstants.C_END_UNREFERENCED_METHODS);
    }
    d_writer.closeCxxExtern();
    d_writer.closeHeaderGuard();
  }
}
