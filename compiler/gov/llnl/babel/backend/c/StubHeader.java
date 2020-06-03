//
// File:        StubHeader.java
// Package:     gov.llnl.babel.backend.c
// Revision:    @(#) $Id: StubHeader.java 7188 2011-09-27 18:38:42Z adrian $
// Description: write C client (i.e., stub) header to a pretty writer stream
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

import gov.llnl.babel.backend.CodeConstants;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.rmi.RMIStubHeader;
import gov.llnl.babel.backend.Utilities;
import gov.llnl.babel.backend.writers.LanguageWriterForC;
import gov.llnl.babel.Context;
import gov.llnl.babel.symbols.Enumeration;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.Package;
import gov.llnl.babel.symbols.Struct;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.Type;
import gov.llnl.babel.BabelConfiguration;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Class <code>StubHeader</code> writes a C client header to a language
 * writer output stream.  The constructor takes a language writer stream
 * and method <code>generateCode</code> writes the C client header code
 * for the specified symbol to the output stream.  The language writer
 * output stream is not closed by this object.
 */
public class StubHeader {
  private final static String SIDL_EXCEPTION =
    BabelConfiguration.getBaseExceptionClass();
  private final static String SIDL_EXCEPTION_INTERFACE =
    BabelConfiguration.getBaseExceptionInterface();
  private final static String SIDL_RMI_TICKET = 
    BabelConfiguration.getRMITicket();

  public final static String s_epv       = IOR.getEPVVar(IOR.PUBLIC_EPV);
  public final static String s_self      = Utilities.s_self;
  public final static String s_sepv_func = "_getSEPV()";

  /**
   * Indices associated with the special, stub-only built-in methods.
   */
  public final static int DUMP_STATS    = 0;
  public final static int SET_CONTRACTS = 1;
  public final static int SET_HOOKS     = 2;

  /**
   * Maximum number of rarray arguments allowed in an inlined method.
   * <= 0 means no inlining of methods with rarray arguments
   * > 0 allows some inlining of methods with rarray arguments.
   * It's unclear what a good value of this should be. Each rarray argument
   * increases the size of the stub which may cause binary file sizes to
   * increase.
   */
  public final static int MAX_RARRAY_INLINE = 1;

  private LanguageWriterForC d_writer;

  private boolean d_comment_all;

  private Context d_context;

  /**
   * This is a convenience utility function that writes the C client
   * header information into the provided language writer output stream.
   * The output stream is not closed on exit.  A code generation
   * exception is thrown if an error is detected.
   *
   * @param symbol the symbol for which a C client header will
   *               be written.
   *
   * @param writer the output writer to which the header will
   *               be written. This will not be closed.
   *
   * @exception gov.llnl.backend.CodeGenerationException
   *    this is a catch all exception. It can be caused by I/O trouble or
   *    violations of the data type invariants.
   */
  public static void generateCode(Symbol symbol, LanguageWriterForC writer,
                                  Context context)
    throws CodeGenerationException
  {
    StubHeader header = new StubHeader(writer, context);
    header.generateCode(symbol);
  }

  /**
   * Create a <code>StubHeader</code> object that will write symbol
   * information to the provided output language writer stream.
   *
   * @param writer the output writer to which the header will
   *               be written. This will not be closed.
   */
  public StubHeader(LanguageWriterForC writer, Context context) {
    d_writer      = writer;
    d_context     = context;
    d_comment_all = !context.getConfig().getCommentLocalOnly();
  }

  /**
   * Write C client header information for the provided symbol to the
   * language writer output stream provided in the constructor.  This
   * method does not close the writer output stream and may be called
   * for more than one symbol (although the written header may not be
   * valid input for the C compiler).  A code generation exception is
   * written if an error is detected.
   *
   * @param symbol the <code>Symbol</code> whose header will be
   *               written.
   *
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception. It can be caused by I/O trouble
   *    or violations of the data type invariants.
   */
  public void generateCode(Symbol symbol) throws CodeGenerationException {
    if (symbol != null) {
      if ( symbol.getSymbolType() == Symbol.PACKAGE ) { 
        d_writer.skipIncludeGuard(); 
      }
      generatePrologue(symbol);

      switch (symbol.getSymbolType()) {
      case Symbol.CLASS:
        generateExtendable((Extendable) symbol);
        break;
      case Symbol.ENUM:
        generateEnumeration((Enumeration) symbol);
        break;
      case Symbol.INTERFACE:
        generateExtendable((Extendable) symbol);
        break;
      case Symbol.PACKAGE:
        generatePackage((Package) symbol);
        break;
      case Symbol.STRUCT:
        generateStruct((Struct)symbol);
        break;
      default:
        throw new CodeGenerationException("Unsupported symbol type.");
      }
    } else {
      throw new CodeGenerationException("Unexpected null Symbol.");
    }
  }

  /**
   * Output a banner and the main header guard at the top of the file.
   *
   * @param sym the <code>Symbol</code> whose header will be written.
   */
  private void generatePrologue(Symbol sym) {
     String header = C.getHeaderFile(sym);

    d_writer.writeBanner(sym, header, false, 
                         CodeConstants.C_DESC_STUB_PREFIX + sym.getFullName());
    d_writer.openHeaderGuard(header);
  }

  /**
   * Output the closing block methods as needed at the end of the file.
   *
   * @param closeExtern the boolean used to indicate if the C++ extern block
   *     needs to be closed.
   */
  private void generateEpilogue(boolean closeExtern) {
    /*
     * Conclude the header file by closing the C++ extern block, if
     * indicated, and the header include guard.
     */
    if (closeExtern) {
      d_writer.closeCxxExtern();
    }
    d_writer.closeHeaderGuard();
  }

  private void generateInlinePrologue(SymbolID id)
  {
    final String decl = C.getInlineDecl(id);
    d_writer.printlnUnformatted("#if !defined(" + decl + ")");
    d_writer.printlnUnformatted("#if defined(SIDL_C_HAS_INLINE)");
    d_writer.printlnUnformatted
      ("#if (defined(__STDC_VERSION__) && (__STDC_VERSION__ >= 199901L)) || defined(__INTEL_COMPILER) || !defined(__GNUC__)");
    d_writer.printlnUnformatted("#define " + decl + " inline");
    d_writer.printlnUnformatted("#else");
    d_writer.printlnUnformatted("#define " + decl + " extern inline");
    d_writer.printlnUnformatted("#endif /* can inline */");

    d_writer.printlnUnformatted("#else  /* still need definition */");
    d_writer.printlnUnformatted("#define " + decl);
    d_writer.printlnUnformatted("#endif /* inline definition needed */");
    d_writer.printlnUnformatted("#endif /* " + decl + " */");
    d_writer.println();
  }

  /**
   * Generate the C header for a SIDL enumerated type.  This method simply
   * includes the IOR enumerated type header file and typedefs that type
   * to a C type.
   *
   * @param enm the <code>Enumeration</code> whose header is being written.
   */
  private void generateEnumeration(Enumeration enm) {
    ArrayMethods ar = new ArrayMethods(enm, true, d_context);

    d_writer.generateInclude(IOR.getHeaderFile(enm), true);
    d_writer.println();

    d_writer.openCxxExtern();
    ar.generateHeader(d_writer);
    generateEpilogue(true);
  }

  /**
   * Generate a C client header for a SIDL class or interface description.
   * The header file consists of the typedef the defines the symbol type.
   * Note that the typedef comes before any external includes to solve
   * the problem with forward references.  After the typedef comes the
   * external includes, followed by special methods such as cast and new,
   * followed by the regular methods.  The header concludes with close
   * statements for the header guards.
   *
   * @param ext the <code>Extendable</code> whose header is being written.
   *
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception. It can be caused by I/O trouble
   *    or violations of the data type invariants.
   */
  private void generateExtendable(Extendable ext) throws CodeGenerationException
  {

    /*
     * Output a documentation comment, forward declare the object, and
     * define the symbol typedef.
     */
    d_writer.writeComment(ext, true);
    d_writer.println(IOR.getObjectName(ext) + ";");
    d_writer.println(IOR.getArrayName(ext) + ";");
    d_writer.print("typedef " + IOR.getObjectName(ext) + "* ");
    d_writer.println(C.getObjectName(ext) + ";");
    d_writer.println();

    /*
     * Generate the includes and open the C++ extern block.
     */
    generateIncludes(ext);
    d_writer.openCxxExtern();

    generateInlinePrologue(ext);
    /*
     * Output standard function signatures for all methods.
     */
    generateMethodPrototypes(ext);

    /*
     * Output array method signatures
     */
    ArrayMethods ar = new ArrayMethods(ext, false, d_context);
    ar.generateHeader(d_writer);

    /*
     * Output any RMI function headers
     */

    if (!d_context.getConfig().getSkipRMI()) {
      RMIStubHeader.generateCode(ext, d_writer);
    }

    /*
     * Conclude the header file by closing block(s) as needed.
     */
    generateEpilogue(true);
  }

  /**
   * Generate the C client header for a SIDL package description.  The
   * package header file consists of the standard header information along
   * with include statements for all package symbols.
   *
   * @param p the <code>Package</code> whose header is being written.
   */
  private void generatePackage(Package p) {
    /*
     * Write out the C include files for each of the symbols within
     * the package.
     */
    List entries = Utilities.sort(p.getSymbols().keySet());
    for (Iterator i = entries.iterator(); i.hasNext(); ) {
      String include = C.getHeaderFile((SymbolID) i.next());
      d_writer.generateInclude(include, true);
    }
    d_writer.println();

    generateEpilogue(false);
  }

  private void generateStruct(Struct strct)
  {
    d_writer.writeCommentLine("Include IOR for declaration of struct");
    d_writer.generateInclude(IOR.getHeaderFile(strct), true);
    d_writer.println("struct sidl_BaseInterface__object;");
    d_writer.println("struct sidl_io_Serializer__object;");
    d_writer.println("struct sidl_io_Deserializer__object;");
    d_writer.println();
    d_writer.openCxxExtern();
    d_writer.println();
    d_writer.beginBlockComment(true);
    d_writer.println("This function will initializes all pointer types to");
    d_writer.println("NULL (if any).");
    d_writer.endBlockComment(true);
    d_writer.println("void");
    d_writer.print(IOR.getSymbolName(strct) + "__init(");
    d_writer.println(IOR.getStructName(strct) + "* strct);");

    d_writer.println();
    d_writer.beginBlockComment(true);
    d_writer.println("This function will delete all resources contained");
    d_writer.println("inside a struct. It does not free the struct itself.");
    d_writer.endBlockComment(true);
    d_writer.println("void");
    d_writer.print(IOR.getSymbolName(strct) + "__destroy(");
    d_writer.println(IOR.getStructName(strct) + "* strct,");
    d_writer.tab();
    d_writer.println("struct sidl_BaseInterface__object **exception);");
    d_writer.backTab();

    d_writer.println();
    d_writer.beginBlockComment(true);
    d_writer.println("Copy data from one struct to another.  It is assumed");
    d_writer.println("that the incoming contents of the destination can");
    d_writer.println("safely be overwritten without any prior actions.");
    d_writer.println("If the destination has unfreed strings or active");
    d_writer.println("object references, this will cause a leak.");
    d_writer.println("On exit, dest is an independent copy of src.");
    d_writer.endBlockComment(true);
    d_writer.println("void");
    d_writer.print(IOR.getSymbolName(strct) + "__copy(const ");
    d_writer.println(IOR.getStructName(strct) + "* src, ");
    d_writer.tab();
    d_writer.println(IOR.getStructName(strct) + "* dest,");
    d_writer.println("struct sidl_BaseInterface__object **exception);");
    d_writer.backTab();

    d_writer.println();
    d_writer.beginBlockComment(true);
    d_writer.println("This function will deserialize all resources into");
    d_writer.println("a struct. It assumes that the contents of arg");
    d_writer.println("can be safely overridden without any prior action");
    d_writer.endBlockComment(true);
    d_writer.println("void");
    d_writer.print(IOR.getSymbolName(strct));
    d_writer.println("__deserialize(" + IOR.getStructName(strct) + " *arg,");
    d_writer.tab();
    d_writer.println("struct sidl_io_Deserializer__object *pipe,");
    d_writer.println("const char *name,");
    d_writer.println("sidl_bool copyArg,");
    d_writer.println("struct sidl_BaseInterface__object **exception);");
    d_writer.backTab();
    d_writer.println();
    d_writer.beginBlockComment(true);
    d_writer.println("This function will serialize all resources contained");
    d_writer.println("inside a struct. It does not free the struct itself.");
    d_writer.endBlockComment(true);
    d_writer.println("void");
    d_writer.print(IOR.getSymbolName(strct));
    d_writer.println("__serialize(const "+ IOR.getStructName(strct) + " *arg,");
    d_writer.tab();
    d_writer.println("struct sidl_io_Serializer__object *pipe,");
    d_writer.println("const char *name,");
    d_writer.println("sidl_bool copyArg,");
    d_writer.println("struct sidl_BaseInterface__object **exception);");
    d_writer.backTab();
    d_writer.println();
    generateEpilogue(true);
  }

  /**
   * Generate the list of include files required to satisfy data
   * dependencies within this header file and output the appropriate
   * include lines.  If any of the symbols do not exist in the symbol
   * table, then throw a code generation exception.
   */
  private void generateIncludes(Extendable ext) throws CodeGenerationException {
    /*
     * Create the set of include symbol identifiers.
     */
    Set includes = new HashSet();
    
    for (Iterator i = ext.getMethods(true).iterator(); i.hasNext(); ) {
      Method method = (Method) i.next();
      includes.addAll(method.getSymbolReferences());

      if (!method.getThrows().isEmpty()) {
        Symbol symbol = Utilities.lookupSymbol(d_context, SIDL_EXCEPTION);
        includes.add(symbol);
        symbol = Utilities.lookupSymbol(d_context, SIDL_EXCEPTION_INTERFACE);
        includes.add(symbol);
      }
      if ( method.getCommunicationModifier() == Method.NONBLOCKING  &&
           !d_context.getConfig().getSkipRMI()) { 
        Symbol symbol = Utilities.lookupSymbol(d_context, SIDL_RMI_TICKET);
        includes.add(symbol);
      }
    }

    /*
     * We should always include the base header file.
     */
    d_writer.writeComment("Includes for all header dependencies.", false);
    d_writer.generateInclude("sidl_header.h", true);

    /*
     * Remove this symbol from the dependency set and iterate over symbols if
     * there are any remaining symbols in the set.
     */
    includes.remove(ext);
    if (!includes.isEmpty()) {
      List entries = Utilities.sort(includes);
      for (Iterator i = entries.iterator(); i.hasNext(); ) {
        String header = C.getHeaderFile((SymbolID) i.next());
        d_writer.generateInclude(header, true);
      }
      d_writer.println();
    }
    if (!d_context.getConfig().getSkipRMI()) {
      d_writer.generateInclude(
        C.getHeaderFile(Utilities.lookupSymbol(d_context, "sidl.rmi.Call")),
                        true);
      d_writer.generateInclude(
        C.getHeaderFile(Utilities.lookupSymbol(d_context, "sidl.rmi.Return")), 
                        true);
    }
    d_writer.printlnUnformatted("#ifdef SIDL_C_HAS_INLINE");
    d_writer.generateInclude(IOR.getHeaderFile(ext), true);
    d_writer.printlnUnformatted("#endif /* SIDL_C_HAS_INLINE */");
  }

  /**
   * Generate prototypes for the specified built-in method.
   */
  private void generateBuiltinPrototypes(SymbolID id, int stubType, 
                                         boolean doStatic) 
    throws CodeGenerationException 
  {
    d_writer.writeComment(getBuiltinComment(stubType, doStatic), true);
    generateBuiltinSignature(d_writer, stubType, id, doStatic, ";");
    d_writer.println();
  }

  /**
   * Generate prototypes for the specified interface contract method(s).
   */
  private void generateContractPrototypes(SymbolID id, boolean doStatic) 
    throws CodeGenerationException 
  {
    generateBuiltinPrototypes(id, SET_CONTRACTS, doStatic);
    generateBuiltinPrototypes(id, DUMP_STATS, doStatic);
  }

  public static boolean stubInlineMethod(Method m) {
    return !(m.isStatic() || (m.numRarray() > MAX_RARRAY_INLINE));
  }

  private void generateMethodStub(SymbolID id,
                                  boolean isInterface,
                                  Method method,
                                  boolean docComment)
    throws CodeGenerationException
  {
    final boolean inlineMethod = stubInlineMethod(method);
    generateMethodSignature(id, isInterface, method, docComment, inlineMethod);
    if (inlineMethod) {
      d_writer.printlnUnformatted("#ifdef " +
                                  C.getInlineDecl(id));
      StubSource.generateMethodStubBody(d_writer, d_context,
                                        id, isInterface, method);
      d_writer.printlnUnformatted("#else");
      d_writer.println(";");
      d_writer.printlnUnformatted("#endif /* " +
                                  C.getInlineDecl(id) + " */");
      d_writer.println();
    }
  }
 
  /**
   * Generate method prototypes for the methods in the interface or class.
   */
  private void generateMethodPrototypes(Extendable ext)
    throws CodeGenerationException 
  {
    /*
     * Generate the name for this entry point vector as well as a pointer
     * to "self" for this structure.  For classes, self will be the object
     * structure whereas for interfaces it is void*.
     */

    /*
     * Output the normal and remote constructors if this extendable is a class.
     */
    if (!ext.isAbstract()) {
      d_writer.writeComment("Constructor function for the class.", true);
      d_writer.println(C.getSymbolObjectPtr(ext));
      d_writer.print(C.getFullMethodName(ext, "_create"));
      d_writer.println("(sidl_BaseInterface* _ex);");
      d_writer.println();

      if(!IOR.isSIDLSymbol(ext)) {
        d_writer.writeComment("Wraps up the private data struct pointer (" 
                             + C.getDataName(ext) + ") passed in rather "
                             + "than running the constructor.", true);
        d_writer.println(C.getSymbolName(ext));
        
        d_writer.print(C.getFullMethodName(ext, "_wrapObj"));
        d_writer.println("(void * data, sidl_BaseInterface *_ex);");
      }

      d_writer.println();

    }

    if (!d_context.getConfig().getSkipRMI()) {
      d_writer.printlnUnformatted("#ifdef WITH_RMI");
      d_writer.println();

      d_writer.writeComment("RMI constructor function for the class.", true);
      d_writer.println(C.getSymbolName(ext));
    
      d_writer.print(C.getFullMethodName(ext, "_createRemote"));
      d_writer.println("(const char * url, sidl_BaseInterface *_ex);");
    
      d_writer.println();
    
      d_writer.writeComment("RMI connector function for the class.(addrefs)", 
                            true);
      d_writer.println(C.getSymbolName(ext));
    
      d_writer.print(C.getFullMethodName(ext, "_connect"));
      d_writer.println("(const char *, sidl_BaseInterface *_ex);");
      d_writer.println();                 
 
      d_writer.println();
      d_writer.printlnUnformatted("#endif /*WITH_RMI*/");
    }

    /*
     * Output the built-in contract method(s).
     */
    boolean hasStatic = ext.hasStaticMethod(true);
    if (IOR.generateContractBuiltins(ext, d_context)) {
      if (hasStatic) {
        generateContractPrototypes(ext, true);
      }
      generateContractPrototypes(ext, false);
    }

    /*
     * Output each of the method prototypes from the interface or class.
     */
    boolean isIfc = ext.isInterface();
    Collection methods = ext.getMethods(true);
    for (Iterator m = methods.iterator(); m.hasNext(); ) {
      Method method = (Method) m.next();
      generateMethodStub(ext, isIfc, method, 
                         d_comment_all || ext.isLocal(method));
      d_writer.println();
      if (( method.getCommunicationModifier() == Method.NONBLOCKING ) &&
          !d_context.getConfig().getSkipRMI()) { 
        Method send = method.spawnNonblockingSend();
        generateMethodSignature(ext, isIfc, send, 
                                d_comment_all || ext.isLocal(method), false);
        d_writer.println();
        Method recv = method.spawnNonblockingRecv();
        generateMethodSignature(ext, isIfc, recv, 
                                d_comment_all || ext.isLocal(method), false);
        d_writer.println();        
      }
    }
      
    /*
     * Output the cast methods for the class or interface.
     */
    d_writer.writeComment("Cast method for interface and class type "
                         + "conversions.", true);
    d_writer.println(C.getSymbolObjectPtr(ext));
    d_writer.println(C.getFullMethodName(ext, "_cast") + "(");
    d_writer.tab();
    d_writer.println("void* obj,");
    d_writer.println("sidl_BaseInterface* _ex);");
    d_writer.backTab();
    d_writer.println();

    d_writer.writeComment("String cast method for interface and class type "
                          + "conversions.", true);
    d_writer.println("void*");
    d_writer.println(C.getFullMethodName(ext, "_cast2") + "(");
    d_writer.tab();
    d_writer.println("void* obj,");
    d_writer.println("const char* type,");
    d_writer.println("sidl_BaseInterface *_ex);");
    d_writer.backTab();
    d_writer.println();
    
    if (!d_context.getConfig().getSkipRMI()) {
      /*
       * Output the exec methods for the class or interface.
       */
      Method m_exec = IOR.getBuiltinMethod(IOR.EXEC,ext, d_context,false);
      generateMethodStub(ext, isIfc, m_exec, true);
    
      //Unused and Doesn't work, just comment it out till we can fix it later.
      //m_exec = C.getSExecMethod();
      //generateMethodSignature(id, isIfc, m_exec, true);

      Method m_getURL = IOR.getBuiltinMethod(IOR.GETURL, ext, d_context, false);
      generateMethodStub(ext, isIfc, m_getURL, true);

      Method m_raddRef = IOR.getBuiltinMethod(IOR.RADDREF, ext, d_context, false);
      generateMethodStub(ext, isIfc, m_raddRef, true);

      Method m_isRemote = IOR.getBuiltinMethod(IOR.ISREMOTE, ext, d_context,
                                               false);
      generateMethodStub(ext, isIfc, m_isRemote, true);

      Method m_isLocal = IOR.getBuiltinMethod(IOR.ISREMOTE, ext, d_context, 
                                              false);
      m_isLocal.setMethodName("_isLocal", "");
      generateMethodSignature(ext, isIfc, m_isLocal, true, false);
    }

    if (hasStatic) {
      Method m_s_hook = IOR.getBuiltinMethod(IOR.HOOKS, ext, d_context, true);
      generateMethodStub(ext, isIfc, m_s_hook, true);
    }
    Method m_hook = IOR.getBuiltinMethod(IOR.HOOKS, ext, d_context, false);
    generateMethodStub(ext, isIfc, m_hook, true);
  }
  

  /**
   * Generate the method signature.
   *
   * @param id the <code>SymbolID</code> of the <code>Extendable</code> whose
   *   header is being written.
   *
   * @param isInterface TRUE if the <code>Extendable</code> is an interface,
   *                    otherwise, use FALSE.
   * 
   * @param method the <code>Method</code> whose signature is being output.
   * 
   * @param docComment iff <code>true</code> this will print a document
   *                   comment for the method if one is available. If
   *                   <code>false</code>, not document comment will
   *                   be printed.
   *
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception. It can be caused by I/O trouble or
   *    violations of the data type invariants.
   */
    private void generateMethodSignature(SymbolID id, 
                                       boolean isInterface,
                                       Method method, 
                                       boolean docComment,
                                       boolean isInline)
    throws CodeGenerationException
  {
    if (docComment) {
      d_writer.writeComment(method, true);
    }
    if (isInline) {
      d_writer.println(C.getInlineDecl(id));
    }
    d_writer.println(getReturnString(method.getReturnType()));
    d_writer.print(C.getFullMethodName(id, method));
    d_writer.println("(");
    d_writer.tab();
    C.generateArgumentList(d_writer, d_context, s_self, isInterface, id, 
                           method, true, true, false, true, false, true, true);
    d_writer.println(isInline ? ")" : ");");
    d_writer.backTab();
  }

   /**
    * Generate a return string for the specified SIDL type.  Most
    * of the SIDL return strings are listed in the static structures defined
    * at the start of this class.  Symbol types and array types require
    * special processing.
    */
   private String getReturnString(Type type)
     throws CodeGenerationException
   {
     return (Type.ENUM == type.getDetailedType()) 
       ? C.getEnumName(type.getSymbolID())
       : IOR.getReturnString(type, d_context, false, true);
   }


  /**
   * Return the comment description associated with the specified
   * built-in stub.
   */
  public static String getBuiltinComment(int type, boolean doStatic) {
    String cmt  = null;
    String desc = doStatic ? "static " : "";
    switch (type) {
      case DUMP_STATS:
        cmt = "Method to dump " + desc;
        cmt += "interface contract enforcement statistics.";
        break;
      case SET_CONTRACTS:
        cmt = "Method to enable/disable " + desc;
        cmt += "interface contract enforcement.";
        break;
      case SET_HOOKS:
        cmt = "Method to enable/disable " + desc +  "hooks execution.";
        break;
    }
    return cmt;
  }

  /**
   * Generate the specified stub-only built-in method signature.
   */
  public static void generateBuiltinSignature(LanguageWriterForC lw, int type, 
                                              SymbolID id, boolean doStatic,
                                              String terminator)
  {
    String suffix   = doStatic ? IOR.s_static_suffix : "";
    String basename = null;
    switch (type) {
      case DUMP_STATS:
        basename = "_dump_stats";
        break;
      case SET_CONTRACTS:
        basename = "_set_contracts";
        break;
      case SET_HOOKS:
        basename = "_set_hooks";
        break;
    }
    lw.println("void");
    lw.println(C.getFullMethodName(id, basename + suffix) + "(");
    lw.tab();
    if (!doStatic) {
      lw.println(C.getFullSelfDecl(id) + ",");
    }
    switch (type) {
      case DUMP_STATS:
        lw.println("const char* filename,");
        lw.println("const char* prefix,");
        lw.println(IOR.getExceptionFundamentalType() + "*_ex)" + terminator);
        break;
      case SET_CONTRACTS:
        lw.println("sidl_bool   enable,");
        lw.println("const char* enfFilename,");
        lw.println("sidl_bool   resetCounters,");
        lw.println(IOR.getExceptionFundamentalType() + "*_ex)" + terminator);
        break;
      case SET_HOOKS:
        lw.println("sidl_bool enable,");
        lw.println(IOR.getExceptionFundamentalType() + "*_ex)" + terminator);
        break;
    }
    lw.backTab();
  }

  /**
   * Return the comma-separated list of arguments associated with the 
   * specified built-in method.  The entries must match the names and
   * ordering found (above) in generateStubBuiltinSignature().
   */
  public static String getBuiltinArgList(int type) {
    String args = null;
    switch (type) {
      case DUMP_STATS:
        args = "filename, prefix, _ex";
        break;
      case SET_CONTRACTS:
        args = "enable, enfFilename, resetCounters, _ex";
        break;
      case SET_HOOKS:
        args = "enable, _ex";
        break;
    }
    return args;
  }

  /**
   * Return a string that dereferences the specified IOR function pointer.
   */
  public static String getDerefFunctionPtr(String baseName, boolean doStatic) {
    String base = doStatic ? s_sepv_func : "*" + s_self + "->" + s_epv;
    return "(" + base + "->" + IOR.getVectorEntry(baseName) + ")";
  }
}
