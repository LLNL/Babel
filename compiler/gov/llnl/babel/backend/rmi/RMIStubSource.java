//
// File: RMIStubSource.java
// Package: gov.llnl.babel.backend.rmi
// Revision: @(#) $Id: RMIStubSource.java 7326 2011-11-10 20:48:31Z tdahlgren $
// Description: generate IOR stub source to a pretty writer stream
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

package gov.llnl.babel.backend.rmi;

import gov.llnl.babel.BabelConfiguration;
import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.Utilities;
import gov.llnl.babel.backend.c.C;
import gov.llnl.babel.backend.writers.LanguageWriter;
import gov.llnl.babel.backend.writers.LanguageWriterForC;
import gov.llnl.babel.symbols.Argument;
import gov.llnl.babel.symbols.Class;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Interface;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.Struct;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.SymbolTable;
import gov.llnl.babel.symbols.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
/**
 * Class <code>RMIStubSource</code> generates an IOR implementation source
 * file to a language writer output stream. The constructor takes a language
 * writer stream and method <code>generateCode</code> generates intermediate
 * object representation for the specified symbol to the output stream. The
 * language writer output stream is not closed by this object.
 */
public class RMIStubSource {
  private static int s_longestBuiltin;

  private static final String s_deleteBuiltin = IOR.getBuiltinName(IOR.DELETE);

  private static final String s_castBuiltin = IOR.getBuiltinName(IOR.CAST);

  private static final String s_execBuiltin = IOR.getBuiltinName(IOR.EXEC);

  private static final String s_getURLBuiltin = IOR.getBuiltinName(IOR.GETURL);

  private static final String s_raddRefBuiltin = IOR
      .getBuiltinName(IOR.RADDREF);

  private static final String s_isRemoteBuiltin = IOR
      .getBuiltinName(IOR.ISREMOTE);

  private static final String s_set_hooksBuiltin = IOR
      .getBuiltinName(IOR.HOOKS);

  private static final String s_set_contractsBuiltin = IOR
      .getBuiltinName(IOR.CONTRACTS);

  private static final String s_dump_statsBuiltin = IOR
      .getBuiltinName(IOR.DUMP_STATS);

  private Context d_context;

  /**
   * Store the SymbolID for sidl.BaseClass, if the extendable being printed is
   * not abstract (i.e., is a concrete class).
   */
  SymbolID d_baseClass = null;

  /**
   * Store the SymbolID for sidl.ClassInfo, if the extendable being printed is
   * not abstract (i.e., is a concrete class).
   */
  SymbolID d_classInfo = null;

  /**
   * Store the SymbolID for sidl.ClassInfoI, if the extendable being printed is
   * not abstract (i.e., is a concrete class).
   */
  SymbolID d_classInfoI = null;

  static {
    s_longestBuiltin = 0;
    for (int j = 0; j < IOR.CLASS_BUILT_IN_METHODS; ++j) {
      String mname = IOR.getBuiltinName(j);
      if (mname.length() > s_longestBuiltin) {
        s_longestBuiltin = mname.length();
      }
    }
  }

  private LanguageWriterForC d_writer;

  /**
   * This is a convenience utility function that writes the symbol source
   * information into the provided language writer output stream. The output
   * stream is not closed on exit. A code generation exception is thrown if an
   * error is detected.
   */
  public static void generateCode(Symbol symbol, LanguageWriterForC writer,
                                  Context context)
      throws CodeGenerationException {
    RMIStubSource source = new RMIStubSource(writer, context);
    source.generateCode(symbol);
  }

  /** Feature allows one to generate includes and code seperately.
   *  Currently this is a special feature for Python to avoid conflicts
   *  between the function connectI's and rmicast's in the sidl C binding,
   *  and the macros defined in Python.
   */
  public static void generateIncludes(Symbol symbol, 
                                      LanguageWriterForC writer,
                                      Context context)
    throws CodeGenerationException {
    RMIStubSource source = new RMIStubSource(writer, context);
    if (symbol != null) {
      switch (symbol.getSymbolType()) {
      case Symbol.PACKAGE:
      case Symbol.ENUM:
        break;
      case Symbol.INTERFACE:
        // Interface version take Interface and Anonymous Class
        source.generateIncludes(((Interface) symbol).generateAnonymousClass());
        
        break;
      case Symbol.CLASS:
        source.generateIncludes((Extendable) symbol);
        
        break;
      }
    }
  }

  /** Feature allows one to generate includes and code seperately.
   *  Currently this is a special feature for Python to avoid conflicts
   *  between the function connectI's and rmicast's in the sidl C binding,
   *  and the macros defined in Python.
   */
  public static void generateCodeNoIncludes(Symbol symbol, 
                                            LanguageWriterForC writer,
                                            Context context)
    throws CodeGenerationException {
    RMIStubSource source = new RMIStubSource(writer, context);
    if (symbol != null) {
      switch (symbol.getSymbolType()) {
      case Symbol.PACKAGE:
      case Symbol.ENUM:
        break;
      case Symbol.INTERFACE:
        // Interface version take Interface and Anonymous Class
        source.generateStaticVariables(((Interface) symbol).generateAnonymousClass());
        source.generateSource((Interface) symbol, ((Interface) symbol)
                       .generateAnonymousClass(), false);
        
        break;
      case Symbol.CLASS:
        source.generateStaticVariables((Extendable) symbol);
        source.generateSource((Extendable) symbol,false);
        break;
      }
    }
  }

  /**
   * Create a <code>RMIStubSource</code> object that will write symbol
   * information to the provided output writer stream.
   */
  public RMIStubSource(LanguageWriterForC writer, Context context) {
    d_writer = writer;
    d_context = context;
  }

  /**
   * Write IOR source information for the provided symbol to the language writer
   * output stream provided in the constructor. This method does not close the
   * language writer output stream and may be called for more than one symbol
   * (although the generated source may not be valid input for the C compiler).
   * A code generation exception is generated if an error is detected. No code
   * is generated for enumerated and package symbols.
   */
  public void generateCode(Symbol symbol) throws CodeGenerationException {
    if (symbol != null) {
      switch (symbol.getSymbolType()) {
      case Symbol.PACKAGE:
      case Symbol.ENUM:
        break;
      case Symbol.INTERFACE:
        // Interface version take Interface and Anonymous Class
        generateSource((Interface) symbol, ((Interface) symbol)
                       .generateAnonymousClass(),true);
        break;
      case Symbol.CLASS:
        generateSource((Extendable) symbol,true);
        break;
      }
    }
  }

  /**
   * Lookup the SymbolIDs for sidl.BaseClass, sidl.ClassInfo and
   * sidl.ClassInfoI.
   */
  private void lookupSymbolIDs() {
    SymbolTable table = d_context.getSymbolTable();
    d_baseClass = table.lookupSymbol(BabelConfiguration.getBaseClass())
        .getSymbolID();
    d_classInfo = table.lookupSymbol(BabelConfiguration.getClassInfo())
        .getSymbolID();
    d_classInfoI = table.lookupSymbol(BabelConfiguration.getClassInfoI())
        .getSymbolID();
  }

  /**
   * Generate the RStub source for a sidl symbol . The source file begins with a
   * banner and include files followed by a declaration of static methods and
   * (for a class) external methods expected in the skeleton file. For classes,
   * the source file then defines a number of functions (cast, delete,
   * initialize EPVs, new, init, and fini).
   */
  private void generateSource(Extendable ext, boolean includes) throws CodeGenerationException {
    /*
     * Generate the file banner and include files.
     */
    Class cls = (Class) ext;

    d_writer.printlnUnformatted("#ifdef WITH_RMI");
    d_writer.println();

    if(includes) {
      generateIncludes(ext);
      generateStaticVariables(ext);
    }
    /*
     * Generate internal static variables and external references to be supplied
     * by the skeleton file.
     */
    

    /*
     * Generate remote method support for interfaces and classes.
     */
    //generateRemoteCastFunction(cls, null);
    IOR.generateCastFunction(cls, "self", d_writer, true,true);
    generateRemoteDeleteFunction(ext);
    generateRemoteGetURLFunction(ext);
    generateRemoteAddRefFunction(ext);
    generateIsLocalIsRemote(cls);
    generateIsSetHooks(cls);
    generateIsSetContracts(cls);
    generateIsDumpStats(cls);
    generateRemoteExecFunction(ext);
    generateRemoteMethodBodies(ext);
    generateRemoteInitEPV(ext);
    generateRemoteConnect(ext);
    generateIHConnect(ext);
    if(!ext.isAbstract()) {  //No constructor for abstract classes
      generateRemoteConstructor(cls);
    }
    generateConnectInternal(cls);

    d_writer.println();
    d_writer.printlnUnformatted("#endif /*WITH_RMI*/");


  }

  /**
   * Generate the RStub source for a sidl interface. The source file begins with
   * a banner and include files followed by a declaration of static methods and
   * (for a class) external methods expected in the skeleton file. For classes,
   * the source file then defines a number of functions (cast, delete,
   * initialize EPVs, new, init, and fini). This is the version used for
   * interfaces.
   */
  private void generateSource(Interface ifc, Class anonCls, boolean includes)
      throws CodeGenerationException {

    /*
     * Generate the file banner and include files.
     */

    d_writer.printlnUnformatted("#ifdef WITH_RMI");
    d_writer.println();

    if(includes) {
      generateIncludes(anonCls);
      generateStaticVariables(anonCls);
    }
    
    /*
     * Generate remote method support for interfaces and classes.
     */
    IOR.generateCastFunction(anonCls, "self", d_writer, true,true);
    //generateRemoteCastFunction(anonCls, ifc);
    generateRemoteDeleteFunction(anonCls);
    generateRemoteGetURLFunction(anonCls);
    generateRemoteAddRefFunction(anonCls);
    generateIsLocalIsRemote(anonCls);
    generateIsSetHooks(anonCls);
    generateIsSetContracts(anonCls);
    generateIsDumpStats(anonCls);
    generateRemoteExecFunction(anonCls);
    generateRemoteMethodBodies(anonCls);
    generateRemoteInitEPV(anonCls);
    generateRemoteConnect(ifc, anonCls);
    generateIHConnect(ifc, anonCls);
    generateConnectInternal(ifc);

    d_writer.println();
    d_writer.printlnUnformatted("#endif /*WITH_RMI*/");


  }

  /**
   * Generate a single line comment. This is called out as a separate method to
   * make the code formatting below a little prettier.
   */
  private void comment(String s) {
    d_writer.writeCommentLine(s);
  }

  private void generateMethodIncludes(Method m, Set seen) {
    SymbolTable table = d_context.getSymbolTable();
    if(m.getCommunicationModifier() == Method.LOCAL) {
      String file = C.getHeaderFile(table.lookupSymbol("sidl.PreViolation").getSymbolID());
      if (!seen.contains(file)) {
        d_writer.generateInclude(file, true);
        seen.add(file);
      }
      Iterator i = m.getSymbolReferences().iterator();
      while (i.hasNext()) {
        final SymbolID id = (SymbolID)i.next();
        final Symbol sym = table.lookupSymbol(id);
        if ((sym != null) && (sym instanceof Struct)) {
          file = IOR.getHeaderFile(id);
          if (!seen.contains(file)) {
            d_writer.generateInclude(file, true);
            seen.add(file);
            if (!seen.contains("sidlOps.h")) {
              d_writer.printlnUnformatted("#ifdef SIDL_DYNAMIC_LIBRARY");
              d_writer.generateInclude("sidlOps.h", true);
              d_writer.printlnUnformatted("#endif");
              seen.add("sidlOps.h");
            }
          }
        }
      }
    }
  }

  private void generateIncludes(Extendable ext) throws CodeGenerationException {
    SymbolID id = ext.getSymbolID();
    SymbolTable table = d_context.getSymbolTable();
    
    d_writer.printlnUnformatted("#include <stdlib.h>");
    d_writer.printlnUnformatted("#include <string.h>");
    d_writer.printlnUnformatted("#include <stdio.h>");
    lookupSymbolIDs();
    d_writer.generateInclude(C.getHeaderFile(d_baseClass), true);
    d_writer.generateInclude(C.getHeaderFile(d_classInfo), true);

    d_writer.generateInclude(C.getHeaderFile(table.lookupSymbol(
        "sidl.rmi.ProtocolFactory").getSymbolID()), true);
    d_writer.generateInclude(C.getHeaderFile(table.lookupSymbol(
        "sidl.rmi.InstanceRegistry").getSymbolID()), true);
    d_writer.generateInclude(C.getHeaderFile(table.lookupSymbol(
        "sidl.rmi.InstanceHandle").getSymbolID()), true);
    d_writer.generateInclude(C.getHeaderFile(table.lookupSymbol(
        "sidl.rmi.Invocation").getSymbolID()), true);
    d_writer.generateInclude(C.getHeaderFile(table.lookupSymbol(
        "sidl.rmi.Response").getSymbolID()), true);
    d_writer.generateInclude(C.getHeaderFile(table.lookupSymbol(
        "sidl.rmi.ServerRegistry").getSymbolID()), true);
    d_writer.generateInclude(C.getHeaderFile(table.lookupSymbol(
        "sidl.rmi.ConnectRegistry").getSymbolID()), true);
    d_writer.generateInclude(C.getHeaderFile(table.lookupSymbol(
        "sidl.io.Serializable").getSymbolID()), true);
    d_writer.generateInclude(C.getHeaderFile(table.lookupSymbol(BabelConfiguration.getMemoryAllocationException()).getSymbolID()), true);
    d_writer.generateInclude(C.getHeaderFile(table.lookupSymbol("sidl.NotImplementedException").getSymbolID()), true);

    HashSet seen = new HashSet();
    for(Iterator i = ext.getMethods(true).iterator(); i.hasNext();) {
      Method m = (Method) i.next();
      generateMethodIncludes(m, seen);
    }



    d_writer.printlnUnformatted("#include \"sidl_Exception.h\"");

    d_writer.println();

    d_writer.printlnUnformatted("#ifndef " + C.NULL);
    d_writer.printlnUnformatted("#define " + C.NULL + " 0");
    d_writer.printlnUnformatted("#endif");
    d_writer.println();

    String my_mutex = IOR.getSymbolName(id) + "__mutex";
    String lock_name = IOR.getLockStaticGlobalsMacroName();
    String unlock_name = IOR.getUnlockStaticGlobalsMacroName();
    String have_lock = IOR.getHaveLockStaticGlobalsMacroName();
    d_writer.printlnUnformatted("#include \"sidl_thread.h\"");
    d_writer.printlnUnformatted("#ifdef HAVE_PTHREAD");
    d_writer.printlnUnformatted("static struct sidl_recursive_mutex_t "
        + my_mutex + "= SIDL_RECURSIVE_MUTEX_INITIALIZER;");
    d_writer.printlnUnformatted("#define " + lock_name
        + " sidl_recursive_mutex_lock( &" + my_mutex + " )");
    d_writer.printlnUnformatted("#define " + unlock_name
        + " sidl_recursive_mutex_unlock( &" + my_mutex + " )");
    d_writer.printlnUnformatted("/* #define " + have_lock
        + " (sidl_recursive_mutex_trylock( &" + my_mutex + " )==EDEADLOCK) */");
    d_writer.printlnUnformatted("#else");
    d_writer.printlnUnformatted("#define " + lock_name);
    d_writer.printlnUnformatted("#define " + unlock_name);
    d_writer.printlnUnformatted("/* #define " + have_lock + " (1) */");
    d_writer.printlnUnformatted("#endif");
    d_writer.println();

  }

  /**
   * Generate the static variables used to store the EPVs and also the
   * initialization flags for the EPVs. For interfaces, we only need to generate
   * a remote EPV structure. Classes require EPVs for their static methods (if
   * present), standard methods, remote methods, and old, new, and remote
   * versions for all parent classes and interfaces.
   */
  private void generateStaticVariables(Extendable ext) {
    comment("Static variables to hold version of IOR");
    d_writer.println("static const int32_t s_IOR_MAJOR_VERSION = "
        + IOR.MAJOR_VERSION + ";");
    d_writer.println("static const int32_t s_IOR_MINOR_VERSION = "
        + IOR.MINOR_VERSION + ";");
    d_writer.println();

    if (!ext.isAbstract()) {
      /*
       * TODO: ClassInfo and RMI? comment("Static variable to hold shared
       * ClassInfo interface."); d_writer.println("static " +
       * C.getObjectName(d_classInfo) + " s_classInfo = " + C.NULL + ";");
       * d_writer.println("static int s_classInfo_init = 1;");
       * d_writer.println();
       */
    }

    comment("Static variables for managing EPV initialization.");

    /*
     * Output the initialization flags for the EPV structures.
     */
    d_writer.println("static int s_remote_initialized = 0;");
    d_writer.println();

    /*
     * Output the EPV, remote EPV, and static EPV for this object.
     */
    IOR.generateStaticEPVVariables(d_writer, ext, false, true, IOR.SET_PUBLIC);

    /*
     * If the object is a class, then collect all the parents in a set and
     * output EPV structures for the parents.
     */
    if (!ext.isInterface()) {
      Class cls = (Class) ext;
      Set parents = Utilities.getAllParents(cls);
      Set new_interfaces = Utilities.getUniqueInterfaceIDs(cls);

      if (!parents.isEmpty()) {
        List sorted = Utilities.sort(parents);
        for (Iterator i = sorted.iterator(); i.hasNext();) {
          SymbolID p_id = (SymbolID) i.next();
          String p_epv = "static " + IOR.getEPVName(p_id);
          boolean is_old = !new_interfaces.contains(p_id);
          String p_epvStr = (is_old ? p_epv + "  " : p_epv + " ");

          d_writer.print(p_epvStr);
          d_writer.println(IOR.getStaticEPVVariable(p_id, IOR.EPV_REMOTE,
              IOR.SET_PUBLIC)
              + ";");
          d_writer.println();
        }
      }
    }
  }

  /**
   * Returns the name of the built-in method, prepending "ior_" and the name of
   * the symbol.
   */
  private String getRemoteMethodName(SymbolID id, String name) {
    return "remote_" + IOR.getSymbolName(id) + '_' + name;
  }

  /**
   * Generate a return string for the specified SIDL type. Most of the SIDL
   * return strings are listed in the static structures defined at the start of
   * this class. Symbol types and array types require special processing.
   */
  private String getReturnString(Type type)
      throws CodeGenerationException {
    return IOR.getReturnString(type, d_context, true, false);
  }

  /**
   * Generate the remote delete method body
   */
  private void generateRemoteDeleteFunction(Extendable ext) {
    comment("REMOTE DELETE: call the remote destructor for the object.");

    SymbolID id = ext.getSymbolID();
    String name = IOR.getSymbolName(id);

    d_writer
        .println("static void remote_" + name + '_' + s_deleteBuiltin + '(');
    d_writer.tab();
    if (ext.isInterface()) {
      d_writer.println("void* self,");
    } else {
      d_writer.println(IOR.getObjectName(id) + "* self,");
    }
    d_writer.println(IOR.getObjectName(BabelConfiguration.getBaseInterface())+"* *_ex)");
    d_writer.backTab();
    d_writer.println("{");
    d_writer.tab();
    d_writer.println("*_ex = NULL;");
    d_writer.println("free((void*) self);");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  /**
   * TODO: Generate the remote exec method body
   */
  private void generateRemoteExecFunction(Extendable ext)
      throws CodeGenerationException {
    comment("REMOTE EXEC: call the exec function for the object.");

    SymbolID id = ext.getSymbolID();
    String name = IOR.getSymbolName(id);
    String object = IOR.getObjectName(id);
    Method m_exec = IOR.getBuiltinMethod(IOR.EXEC, id, d_context, false);

    d_writer.println("static void remote_" + name + '_' + s_execBuiltin + '(');
    d_writer.tab();
    List args = m_exec.getArgumentList();

    d_writer.print(object + "* self,");

    for (Iterator a = args.iterator(); a.hasNext();) {
      Argument arg = (Argument) a.next();
      d_writer.print(IOR.getArgumentWithFormal(arg, d_context,
                                               false, false, true));
      d_writer.println(",");
    }
    d_writer.println(IOR.getObjectName(BabelConfiguration.getBaseInterface())+"* *_ex)");
    d_writer.backTab();
    d_writer.println("{");
    d_writer.tab();
    d_writer.println("*_ex = NULL;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  private void generateRemoteGetURLFunction(Extendable ext)
      throws CodeGenerationException {
    comment("REMOTE GETURL: call the getURL function for the object.");

    SymbolID id = ext.getSymbolID();
    String name = IOR.getSymbolName(id);
    String object = IOR.getObjectName(id);
    Method m_getURL = IOR.getBuiltinMethod(IOR.GETURL, id, d_context, false);

    d_writer.println("static char* remote_" + name + '_' + s_getURLBuiltin
        + '(');
    d_writer.tab();
    List args = m_getURL.getArgumentList();

    d_writer.print(object + "* self, ");
    for (Iterator a = args.iterator(); a.hasNext();) {
      Argument arg = (Argument) a.next();
      d_writer.print(IOR.getArgumentWithFormal(arg, d_context,
                                               false, false, true));
      d_writer.println(",");
    }
    d_writer.println(IOR.getObjectName(BabelConfiguration.getBaseInterface())+"* *_ex)");
    d_writer.backTab();
    d_writer.println("{");
    d_writer.tab();
    d_writer.println("struct sidl_rmi_InstanceHandle__object *conn = " + "(("
        + IOR.getRemoteStructName(id) + "*)self->d_data)->d_ih;");
    d_writer.println("*_ex = NULL;");

    d_writer.println("if(conn != NULL) {");
    d_writer.tab();
    d_writer.println("return sidl_rmi_InstanceHandle_getObjectURL(conn, _ex);");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println("return NULL;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  // TODO: Don't forget to add exception handling!
  private void generateRemoteAddRefFunction(Extendable ext)
      throws CodeGenerationException {
    comment("REMOTE ADDREF: For internal babel use only! Remote addRef.");

    SymbolID id = ext.getSymbolID();
    String name = IOR.getSymbolName(id);
    String object = IOR.getObjectName(id);
    Method m_raddRef = IOR.getBuiltinMethod(IOR.RADDREF, id, d_context, false);
    d_writer.println("static void remote_" + name + '_' + s_raddRefBuiltin
        + '(');
    d_writer.tab();
    List args = m_raddRef.getArgumentList();

    d_writer.print(object + "* self,");

    for (Iterator a = args.iterator(); a.hasNext();) {
      Argument arg = (Argument) a.next();
      d_writer.print(IOR.getArgumentWithFormal(arg, d_context,
                                               false, false, true));
      d_writer.println(",");
    }
    d_writer.println(IOR.getObjectName(BabelConfiguration.getBaseInterface())+"* *_ex)");
    d_writer.backTab();
    d_writer.println("{");
    d_writer.tab();
    d_writer.println(IOR.getObjectName(BabelConfiguration.getBaseExceptionInterface())+"* netex = NULL;");
    comment("initialize a new invocation");
    d_writer.println(IOR.getObjectName(BabelConfiguration.getBaseInterface())+"* _throwaway = " + C.NULL + ";");

    d_writer.println("struct sidl_rmi_InstanceHandle__object *_conn = " + "(("
        + IOR.getRemoteStructName(id) + "*)self->d_data)->d_ih;");
    d_writer.println("sidl_rmi_Response _rsvp = " + C.NULL + ";");
    d_writer.println("sidl_rmi_Invocation _inv = "
        + "sidl_rmi_InstanceHandle_createInvocation( _conn, "
        + "\"addRef\", _ex ); SIDL_CHECK(*_ex);");

    comment("send actual RMI request");

    d_writer.println("_rsvp = sidl_rmi_Invocation_invokeMethod(_inv,"
        + "_ex);SIDL_CHECK(*_ex);");

    comment("Check for exceptions");
    d_writer.println("netex = sidl_rmi_Response_getExceptionThrown(_rsvp, _ex);");
    d_writer.println("if(netex != NULL) {");
    d_writer.tab();
    d_writer.println("*_ex = ("+IOR.getObjectName(BabelConfiguration.getBaseInterface())+"*)netex;");
    d_writer.println("return;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();

    comment("cleanup and return");
    d_writer.println("EXIT:");
    d_writer.println("if(_inv) { sidl_rmi_Invocation_deleteRef(_inv,&_throwaway); }");
    d_writer.println("if(_rsvp) { sidl_rmi_Response_deleteRef(_rsvp,&_throwaway); }");

    d_writer.println("return;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  /**
   * @param cls
   */
  private void generateIsLocalIsRemote(Extendable ext) {
    SymbolID id = ext.getSymbolID();
    String my_symbolName = IOR.getSymbolName(id);

    comment("REMOTE ISREMOTE: returns true if this object is Remote (it is).");
    d_writer.println("static sidl_bool");
    d_writer.println("remote_" + IOR.getRemoteIsRemoteName(id) + "(");
    d_writer.println("    struct " + my_symbolName + "__object* self, ");
    d_writer.println("    "
      + IOR.getObjectName(BabelConfiguration.getBaseInterface()) + "* *_ex) {");
    d_writer.tab();
    d_writer.println("*_ex = NULL;");
    d_writer.println("return TRUE;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println("");
  }

  private void generateIsSetHooks(Extendable ext) throws CodeGenerationException
  {
    Method method = IOR.getBuiltinMethod(IOR.HOOKS, ext.getSymbolID(), 
                                         d_context, false);
    generateSingleRemoteMethodBody(ext, method);
  }

  private void generateIsSetContracts(Extendable ext) 
     throws CodeGenerationException
  {
    Method method = IOR.getBuiltinMethod(IOR.CONTRACTS, ext.getSymbolID(), 
                                         d_context, false);
    comment("Contract enforcement has not been implemented for remote use.");
    generateSingleRemoteMethodBody(ext, method);
  }

  private void generateIsDumpStats(Extendable ext) 
     throws CodeGenerationException
  {
    Method method = IOR.getBuiltinMethod(IOR.DUMP_STATS, ext.getSymbolID(), 
                                         d_context, false);
    comment("Contract enforcement has not been implemented for remote use.");
    generateSingleRemoteMethodBody(ext, method);
  }

  /**
   * Generate remote method bodies for all non-static user-defined methods in an
   * extendable.
   * 
   * @param ext
   *          The extendable to generate methods for
   * @throws CodeGenerationException
   * @see #generateSingleRemoteMethodBody(Extendable, Method)
   */
  private void generateRemoteMethodBodies(Extendable ext)
      throws CodeGenerationException {
    Iterator m = ext.getNonstaticMethods(true).iterator();
    while (m.hasNext()) {
      Method method = (Method) m.next();
      generateSingleRemoteMethodBody(ext, method);
      if (method.getCommunicationModifier() == Method.NONBLOCKING) {
        generateSingleRemoteMethodBody(ext, method.spawnNonblockingSend());
        generateSingleRemoteMethodBody(ext, method.spawnNonblockingRecv());
      }
    }
  }

  /**
   * Generate the body for a single remote user-defined method belonging to an
   * extendable
   * 
   * @param ext
   *          The extendable owning the method
   * @param method
   *          The method in particular
   * @throws CodeGenerationException
   */
  private void generateSingleRemoteMethodBody(Extendable ext, Method method)
      throws CodeGenerationException {
    SymbolID id = ext.getSymbolID();
    String name = IOR.getSymbolName(id);
    String method_name = method.getLongMethodName();
    Type returnType = method.getReturnType();
    boolean hasThrows = !method.getThrows().isEmpty();

    comment("REMOTE METHOD STUB:" + method_name);

    /* Write the method signature. */
    d_writer.println("static " + getReturnString(returnType));
    d_writer.println("remote_" + name + "_" + method_name + "(");
    d_writer.tab();
    IOR.generateArgumentList(d_writer, d_context,
                             "self ", ext.isInterface(), id, method,
        true, true, true, hasThrows, false, false, false);
    d_writer.println(")");
    d_writer.backTab();

    /* Write the method body. */
    d_writer.println("{");
    d_writer.tab();

    d_writer.println(RMI.LangSpecificInit()+";");
    d_writer.println("*_ex = NULL;");
    /* create an inner scope for value decls (if any) */
    d_writer.println("{");
    d_writer.tab();

    if(method.getCommunicationModifier() == Method.LOCAL) {
      comment("This method is local, it cannot be called remotely");
      Type retType = method.getReturnType();
      if(retType.getType() != Type.VOID) {
        d_writer.println(IOR.getReturnString(retType, d_context) + " _retval = "+
                         IOR.getInitialValue(retType)+";");
      }
      try {
        d_writer.pushLineBreak(false);
        /*
         * TODO:  Is it really correct to use the Precondition violation?
         */
        d_writer.print("SIDL_THROW(*_ex, sidl_PreViolation, ");
        d_writer.println("\"local method called on remote object\");");
      }
      finally {
        d_writer.popLineBreak();
      }
      d_writer.println("EXIT:");
      if(retType.getType() != Type.VOID) {
        d_writer.println("return _retval;");
      } else {
        d_writer.println("return;");
      }
    } else {

      if (method_name.compareTo("addRef") == 0) {
        remoteAddRefFragment(id);
      } else if (method_name.compareTo("deleteRef") == 0) {
        remoteDelRefFragment(id);
      } else {
        remoteMethodGenericFragment(ext, method);
      }
    }
    /* close out the inner scope */
    d_writer.backTab();
    d_writer.println("}");
    /* close the body out */
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  /**
   * Generate the fragment of code specific to a remote addRef method
   * 
   * @param id
   * @see #generateSingleRemoteMethodBody(Extendable, Method)
   */
  private void remoteAddRefFragment(SymbolID id) {
    d_writer.println(IOR.getRemoteStructName(id) + "* r_obj = ("
        + IOR.getRemoteStructName(id) + "*)self->d_data;");
    d_writer.println(IOR.getLockStaticGlobalsMacroName() + ";");
    d_writer.println("r_obj->d_refcount++;");
    d_writer.printUnformatted("#ifdef SIDL_DEBUG_REFCOUNT\n");
    d_writer.pushLineBreak(false);
    d_writer.println("fprintf(stderr, "
      + "\"babel: addRef %p new count %d (type %s)\\n\",");
    d_writer.tab();
    d_writer.println("r_obj, r_obj->d_refcount, ");
    d_writer.println("\"" + id.getFullName() + " Remote Stub\");"); 
    d_writer.backTab();
    d_writer.popLineBreak();
    d_writer.printUnformatted("#endif /* SIDL_DEBUG_REFCOUNT */ \n");
    d_writer.println(IOR.getUnlockStaticGlobalsMacroName() + ";");
  }

  /**
   * Generate the fragment of code specific to a remote deleteRef method
   * 
   * @param id
   * @see #generateSingleRemoteMethodBody(Extendable, Method)
   */
  private void remoteDelRefFragment(SymbolID id) {
    
    d_writer.println(IOR.getRemoteStructName(id) + "* r_obj = ("
        + IOR.getRemoteStructName(id) + "*)self->d_data;");
    d_writer.println(IOR.getLockStaticGlobalsMacroName() + ";");
    d_writer.println("r_obj->d_refcount--;");
    d_writer.printUnformatted("#ifdef SIDL_DEBUG_REFCOUNT\n");
    d_writer.pushLineBreak(false);
    d_writer.println("fprintf(stderr, \"babel: deleteRef %p new count %d (type %s)\\n\","+
             "r_obj, r_obj->d_refcount, \"" + id.getFullName() + " Remote Stub\");");
    d_writer.popLineBreak();
    d_writer.printUnformatted("#endif /* SIDL_DEBUG_REFCOUNT */ \n");
    d_writer.println("if(r_obj->d_refcount == 0) {");
    d_writer.tab();
    d_writer.println("sidl_rmi_InstanceHandle_deleteRef(r_obj->d_ih, _ex);");
    d_writer.println("free(r_obj);");
    d_writer.println("free(self);");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println(IOR.getUnlockStaticGlobalsMacroName() + ";");
  }

  /**
   * Generates the packing and unpacking commands for the remote case of
   * general, user-defined methods in an Extendable
   * 
   * @param ext
   * @param method
   * @throws CodeGenerationException
   */
  private void remoteMethodGenericFragment(Extendable ext, Method method)
      throws CodeGenerationException {
    SymbolID id = ext.getSymbolID();
    String blocking_method_name = method.getBlockingMethodName();
    List args = method.getArgumentList();
    Type returnType = method.getReturnType();
    int commType = method.getCommunicationModifier();
    boolean outDisallowed = (commType == Method.NONBLOCKING_SEND || commType == Method.ONEWAY);
    boolean inDisallowed = (commType == Method.NONBLOCKING_RECV);

    comment("initialize a new invocation");
    d_writer.println(IOR.getObjectName(BabelConfiguration.getBaseInterface())+"* _throwaway = " + C.NULL + ";");
    if (!outDisallowed) {

      d_writer.println("sidl_BaseException _be = " + C.NULL + ";");
      d_writer.println("sidl_rmi_Response _rsvp = " + C.NULL + ";");

      // Generate the url holders for any out or inout object arguments
      for (Iterator a = args.iterator(); a.hasNext();) {
        Argument arg = (Argument) a.next();
        if ((arg.getType().getDetailedType() == Type.CLASS || arg.getType()
            .getDetailedType() == Type.INTERFACE)
            && arg.getMode() != Argument.IN) {
          if(arg.isCopy()) {
            d_writer.println(IOR.getSymbolName(BabelConfiguration.getSerializableType()) + " " + 
                             arg.getFormalName()+ RMI.getSerializableExt()+" = "+C.NULL+ ";");
          } else {
            // We need a string to unserialize the url into
            d_writer.println(IOR.getReturnString(new Type(Type.STRING), 
                                                 d_context) + " "
              + arg.getFormalName() + "_str= NULL;");
          }
        }
        if (arg.getType().getDetailedType() == Type.ENUM 
            && arg.getMode() != Argument.IN) {
          d_writer.println(IOR.getReturnString(new Type(Type.LONG), 
                                               d_context) + " "
                           + arg.getFormalName() + "_tmp= 0;");
        }
      }
    }

    //send returns a Ticket, if you just check for !out, oneway get it too
    if (commType == Method.NONBLOCKING_SEND) {
      d_writer.println("struct sidl_rmi_Ticket__object *_retval = NULL;");
    } else {
      if ((returnType.getType() != Type.VOID)) {
        if (returnType.getDetailedType() == Type.CLASS
            || returnType.getDetailedType() == Type.INTERFACE) {
          if(method.isReturnCopy()) {
            d_writer.println(IOR.getSymbolName(BabelConfiguration.getSerializableType())+ " " +
                             RMI.getReturnArgName()+ RMI.getSerializableExt()+" = "+C.NULL+ ";");
          } else {
            d_writer.println(IOR.getReturnString(new Type(Type.STRING), 
                                                 d_context)
                             + RMI.getReturnArgName()+RMI.getStringExt() + " = "+C.NULL+";");
          }
        }
        else if (returnType.getDetailedType() == Type.ENUM) {
          d_writer.println(IOR.getReturnString(new Type(Type.LONG), 
                                               d_context) + " "
                           + RMI.getReturnArgName() + "_tmp = 0;");
        }
        d_writer.println(getReturnString(returnType) + " " +
                         RMI.getReturnArgName() +
                         Utilities.getTypeInitialization(returnType, d_context) 
                         + ";");
      }
    }


    if (!inDisallowed) {
      /* For copy arguments, we need somewhere to store the Serializable */
      for (Iterator a = args.iterator(); a.hasNext();) {
        Argument arg = (Argument) a.next();
        if ((arg.getType().getDetailedType() == Type.CLASS || 
             arg.getType().getDetailedType() == Type.INTERFACE)
            && arg.getMode() == Argument.IN && arg.isCopy()) {
          d_writer.println(IOR.getSymbolName(BabelConfiguration.getSerializableType()) + " " +
                           arg.getFormalName() + RMI.getSerializableExt()+" = "+C.NULL+ ";");
        }
      }
      d_writer.println("struct sidl_rmi_InstanceHandle__object * _conn = " + "(("
                       + IOR.getRemoteStructName(id) + "*)self->d_data)->d_ih;");
      
      d_writer.println("sidl_rmi_Invocation _inv = "
                       + "sidl_rmi_InstanceHandle_createInvocation( _conn, \""
                       + blocking_method_name + "\", _ex ); SIDL_CHECK(*_ex);");
      d_writer.println();
      comment("pack in and inout arguments");
      for (Iterator a = args.iterator(); a.hasNext();) {
        Argument arg = (Argument) a.next();
        if (arg.getMode() == Argument.IN || arg.getMode() == Argument.INOUT) {
          RMI.packArg(d_writer, d_context, 
                      "sidl_rmi_Invocation", "_inv", arg, false);
        }
      }
    }

    /* TODO: There may be something smarter we can do for transfering references */
    for (Iterator a = args.iterator(); a.hasNext();) {
      Argument arg = (Argument) a.next();
      if ((arg.getType().getDetailedType() == Type.CLASS || 
           arg.getType().getDetailedType() == Type.INTERFACE)
          && arg.getMode() == Argument.INOUT && !arg.isCopy()) {
        comment("Transfer this reference");
        d_writer.println("if(*" + arg.getFormalName()
            + " && "+IOR.getSymbolName(BabelConfiguration.getBaseInterface())+
                         "__isRemote(" + "("+IOR.getObjectName(BabelConfiguration.getBaseInterface())+"*)*"
            + arg.getFormalName() + ", _ex)) {");
        d_writer.tab();
        d_writer.println("SIDL_CHECK(*_ex);");
        d_writer.println("(((sidl_BaseInterface)(*" + arg.getFormalName()
            + "))->d_epv->f__raddRef)(((sidl_BaseInterface)(*" + arg.getFormalName() + "))->d_object, _ex);SIDL_CHECK(*_ex);");
        d_writer.println("(((sidl_BaseInterface)(*" + arg.getFormalName()
            + "))->d_epv->f_deleteRef)(((sidl_BaseInterface)(*" + arg.getFormalName() + "))->d_object, _ex);SIDL_CHECK(*_ex);");
        d_writer.backTab();
        d_writer.println("}");
      }
    }
    d_writer.println();

    comment("send actual RMI request");
    switch (commType) {
    case Method.ONEWAY:
      d_writer
          .println("sidl_rmi_Invocation_invokeOneWay(_inv, _ex);SIDL_CHECK(*_ex);");
      break;
    case Method.NONBLOCKING_SEND:
      d_writer
          .println("_retval = sidl_rmi_Invocation_invokeNonblocking(_inv, _ex);SIDL_CHECK(*_ex);");
      break;
    case Method.NONBLOCKING_RECV:
      d_writer.println("_rsvp = sidl_rmi_Ticket_getResponse(ticket, _ex);SIDL_CHECK(*_ex);");
      break;

   case Method.LOCAL:
      //TODO: Throw an exception here or something.  (Fall through to
      //default for now)  

    case Method.NONBLOCKING:
    /*
     * Intentional fallthrough. Nonblocking method called in blocking fashion is
     * same as NORMAL
     */
    case Method.NORMAL:
    default:
      d_writer
          .println("_rsvp = sidl_rmi_Invocation_invokeMethod(_inv, _ex);SIDL_CHECK(*_ex);");
      break;
    }
    d_writer.println();
    if (!outDisallowed ) { 
        d_writer.println("_be = sidl_rmi_Response_getExceptionThrown(_rsvp, _ex);SIDL_CHECK(*_ex);");
        d_writer.println("if (_be != NULL) {");
        d_writer.tab();
        d_writer.println(IOR.getObjectName(BabelConfiguration.getBaseInterface())
          + "* throwaway_exception = NULL;");
        d_writer.println("sidl_BaseException_addLine(_be, ");
        d_writer.tab();
        d_writer.printlnUnformatted("      \"Exception unserialized from " 
          + ext.getFullName() + "." + method.getLongMethodName() +".\",");
        d_writer.println("&throwaway_exception);"); 
        d_writer.backTab();
        d_writer.println("*_ex = (" 
           + IOR.getObjectName(BabelConfiguration.getBaseInterface())
           + "*) " + IOR.getSymbolName(BabelConfiguration.getBaseInterface())
           + "__cast(_be,&throwaway_exception);");
        d_writer.println("goto EXIT;");
        d_writer.backTab();
        d_writer.println("}");
        d_writer.println();
    }

    if ((returnType.getType() != Type.VOID)
        && (method.getCommunicationModifier() != Method.NONBLOCKING_SEND)) {
      // nothing to process for void returns
      // and return type for nonblocking send is a special case
      // ( sidl.rmi.Ticket is generated as a local instance by the protocol)
      comment("extract return value");
      RMI
        .unpackType(d_writer, d_context,
                    ext, "sidl_rmi_Response", "_rsvp", returnType,
                    "_retval", "_retval", Argument.IN, method.isReturnCopy(), true,
                    false);
      d_writer.println();
    }

    if (!outDisallowed) {
      comment("unpack out and inout arguments");
      for (Iterator a = args.iterator(); a.hasNext();) {
        Argument arg = (Argument) a.next();
        if (arg.getMode() != Argument.IN) {
          RMI
            .unpackArg(d_writer, d_context,
                       ext, "sidl_rmi_Response", "_rsvp", arg,
                       false);
        }
      }
      d_writer.println();
    }

    comment("cleanup and return");
    d_writer.println("EXIT:");
    if (!inDisallowed) {
      d_writer.println("if(_inv) { sidl_rmi_Invocation_deleteRef(_inv, &_throwaway); }");
    }
    if (!outDisallowed) {
      d_writer.println("if(_rsvp) { sidl_rmi_Response_deleteRef(_rsvp, &_throwaway); }");
    }

    if (returnType.getType() != Type.VOID) {
      d_writer.println("return _retval;");
    } else {
      d_writer.println("return;");
    }
  }

  /**
   * Generate the function that initializes the method entry point vector for a
   * remote instance. This method performs two basic functions. First, it
   * initializes the remote EPV with pointers to the remote stubs in the IOR
   * file. Second, it sets interface and parent EPV function pointers using the
   * pointer values generated in the first step.
   */
  private void generateRemoteInitEPV(Extendable ext)
      throws CodeGenerationException {
    comment("REMOTE EPV: create remote entry point vectors (EPVs).");

    /*
     * Decleare the method signature and open the implementation body.
     */
    SymbolID id = ext.getSymbolID();
    String name = IOR.getSymbolName(id);

    d_writer.println("static void " + name + "__init_remote_epv(void)");
    d_writer.println("{");
    d_writer.tab();
    comment("assert( " + IOR.getHaveLockStaticGlobalsMacroName() + " );");

    /*
     * Output entry point vectors aliases for each parent class and interface as
     * well as a special one for the current object.
     */
    List parents = null;
    if (ext.isInterface()) {
      d_writer.print(IOR.getEPVName(id) + "*");
      d_writer.println(" epv = &"
          + IOR.getStaticEPVVariable(id, IOR.EPV_REMOTE, IOR.SET_PUBLIC) + ";");
      d_writer.println();
    } else {
      parents = Utilities.sort(Utilities.getAllParents((Class) ext));
      aliasEPVs((Class) ext, parents, true);
    }

    /*
     * Generate a list of the nonstatic methods in the class and get the width
     * of every method name.
     */
    List methods = (List) ext.getNonstaticMethods(true);
    int mwidth = Math.max(Utilities.getWidth(methods), s_longestBuiltin)
        + IOR.getVectorEntry("").length() + IOR.GENERIC_POST_SUFFIX.length();

    /*
     * Output standard methods.
     */
    for (int j = 0; j < IOR.CLASS_BUILT_IN_METHODS; j++) {
      if (IOR.isBuiltinBasic(j)) {
        String mname = IOR.getBuiltinName(j);
        d_writer.print("epv->");
        d_writer.printAligned(IOR.getVectorEntry(mname), mwidth);
        if (   (j == IOR.CONSTRUCTOR) 
            || (j == IOR.DESTRUCTOR)
            || (j == IOR.CONSTRUCTOR2)  ) 
        {
          d_writer.println(" = " + C.NULL + ";");
        } else {
          d_writer.println(" = " + getRemoteMethodName(id, mname) + ';');
        }
      }
    }

    /*
     * Iterate through all of the nonstatic methods. Assign them to the remote
     * stub function.
     */
    for (Iterator i = methods.iterator(); i.hasNext();) {
      Method method = (Method) i.next();
      assignRemoteEPVPointer(name, method, mwidth);
      if (method.getCommunicationModifier() == Method.NONBLOCKING) {
        assignRemoteEPVPointer(name, method.spawnNonblockingSend(), mwidth);
        assignRemoteEPVPointer(name, method.spawnNonblockingRecv(), mwidth);
      }
    }

    /*
     * If this is a class, then iterate through all parent EPVs and set each of
     * the function pointers to use the corresponding function in the class EPV
     * structure.
     */
    
    if (parents != null) {
      HashMap renames = new HashMap();
      IOR.resolveRenamedMethods(ext, renames);
      d_writer.println();
      copyEPVs(parents, renames);
    }
    d_writer.println("s_remote_initialized = 1;");

    /*
     * Close the function definition.
     */
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  /**
   * Generate a single assignment in initialization of the remote EPV pointer
   * 
   * @param name
   *          String name of the extendable
   * @param method
   *          The method in the EPV to initialzie
   * @param mwidth
   *          formatting parameter related to width of longest method name
   */
  private void assignRemoteEPVPointer(String name, Method method, int mwidth) {
    String mname = method.getLongMethodName();
    String ename = IOR.getVectorEntry(mname);
    d_writer.print("epv->");
    d_writer.printAligned(ename, mwidth);
    d_writer.println(" = remote_" + name + "_" + mname + ";");
  }

  /**
   * Generate remote connect support for classes
   */
  private void generateRemoteConnect(Extendable ext) {
    comment("Create an instance that connects to an "
        + "existing remote object.");

    SymbolID id = ext.getSymbolID();
    String object = C.getSymbolObjectPtr(id);
    String bi_ior = BabelConfiguration.getBaseInterface().replace('.', '_');

    /*
     * Declare the function prototype.
     */
    d_writer.println("static " + object);
    d_writer.println(IOR.getSymbolName(id) + "__remoteConnect"
        + "(const char *url, sidl_bool ar, "+IOR.getObjectName(BabelConfiguration.getBaseInterface())+"* *_ex)");
    d_writer.println("{");
    d_writer.tab();

    /*
     * Data
     */
    declareRemoteEPV(ext);
    d_writer.println("sidl_rmi_InstanceHandle instance = NULL;");
    d_writer.println("char* objectID = NULL;");
    d_writer.println("objectID = NULL;");
    d_writer.println("*_ex = NULL;");
    d_writer.println("if(url == NULL) {return NULL;}");
    d_writer.println("objectID = sidl_rmi_ServerRegistry_isLocalObject(url, _ex);");
    d_writer.println("if(objectID) {");
    d_writer.tab();
    d_writer.println(IOR.getObjectName(id) + "* retobj = "+C.NULL+";"); 
    d_writer.println(IOR.getExceptionFundamentalType() + "throwaway_exception;");

    d_writer.println(bi_ior + " bi = (" + bi_ior
        + ")sidl_rmi_InstanceRegistry_getInstanceByString(objectID, _ex); SIDL_CHECK(*_ex);");

    d_writer.println("(*bi->d_epv->f_deleteRef)(bi->d_object, &throwaway_exception);");
    d_writer.println("retobj = ("+IOR.getObjectName(id)+"*) (*bi->d_epv->f__cast)(bi->d_object, \""+
                     id.getFullName()+"\", _ex);");
    d_writer.println("if(!ar) { ");
    d_writer.tab();
    //TODO: Is there some way to use the C binding to autogenerate this call?
    d_writer.println("(*bi->d_epv->f_deleteRef)(bi->d_object, &throwaway_exception);");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println("return retobj;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println("instance = sidl_rmi_ProtocolFactory_connectInstance(url, \""+
                 ext.getSymbolID().getFullName()+"\", ar, _ex ); SIDL_CHECK(*_ex);");

    d_writer.println("if ( instance == NULL) { return NULL; }");

    generateRemoteEPV(ext);

    d_writer.println();
    d_writer.println("return self;");
    d_writer.println("EXIT:");

    generateRemoteEPVCleanup();

    d_writer.println("return NULL;");
    d_writer.backTab();
    d_writer.println("}");
  }

  /**
   * Generate remote connect support for interfaces.
   */
  private void generateRemoteConnect(Interface ifc, Class anonCls) {
    comment("Create an instance that connects to an "
        + "existing remote object.");

    SymbolID ifcID = ifc.getSymbolID();
    String ifcSymName = IOR.getSymbolName(ifcID);
    String ifcObject = C.getSymbolObjectPtr(ifcID);
    String bi_ior = BabelConfiguration.getBaseInterface().replace('.', '_');

    /*
     * Declare the function prototype.
     */
    d_writer.println("static " + ifcObject);
    d_writer.println(ifcSymName + "__remoteConnect"
        + "(const char *url, sidl_bool ar, "+IOR.getObjectName(BabelConfiguration.getBaseInterface())+"* *_ex)");
    d_writer.println("{");
    d_writer.tab();

    /*
     * Data
     */
    declareRemoteEPV(anonCls);
    d_writer.println("sidl_rmi_InstanceHandle instance = NULL;");
    d_writer.println(ifcObject + " ret_self = NULL;");
    d_writer.println("char* objectID = NULL;");
    d_writer.println("objectID = sidl_rmi_ServerRegistry_isLocalObject(url, _ex);");
    d_writer.println("if(objectID) {");
    d_writer.tab();
    d_writer.println(bi_ior + " bi = (" + bi_ior
        + ") sidl_rmi_InstanceRegistry_getInstanceByString(objectID, _ex);");
    // TODO: REMOVE ADDREF WHEN CAST STARTS ADDREFING
    // REMOVED, OK?
    //    d_writer.println("if(ar) {");
    //d_writer.tab();
    //d_writer.println("sidl_BaseInterface_addRef(bi, _ex);");
    //d_writer.backTab();
    //d_writer.println("}");
    d_writer.println("return ("+ifcObject+")(*bi->d_epv->f__cast)(bi->d_object, \""+ifc.getFullName()+"\", _ex);");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println("instance = sidl_rmi_ProtocolFactory_connectInstance(url, \""+
                     ifc.getSymbolID().getFullName()+"\", ar, _ex );");
    d_writer.println("if ( instance == NULL) { return NULL; }");

    generateRemoteEPV(anonCls);

    // Cast it to the interface type before returning.
    d_writer.println();
    d_writer.println("ret_self = ("+ifcObject+") (*self->d_epv->f__cast)(self, \""+ifc.getFullName()+"\", _ex);");
    d_writer.println("if(*_ex || !ret_self) { goto EXIT; }");
    d_writer.println("return ret_self;"); 
    d_writer.println("EXIT:"); 

    generateRemoteEPVCleanup();

    d_writer.println("return NULL;");
    d_writer.backTab();
    d_writer.println("}");
  }

  /**
   * Generate connect that takes an already existing instace handle.
   */
  private void generateIHConnect(Extendable ext) {
    comment("Create an instance that uses an already existing ");
    comment("InstanceHandle to connect to an existing remote object.");

    SymbolID id = ext.getSymbolID();

    /*
     * Declare the function prototype.
     */
    d_writer.print("static " + IOR.MACRO_VAR_UNUSED + " ");
    d_writer.println(C.getSymbolObjectPtr(id));
    d_writer.println(IOR.getSymbolName(id) + "__IHConnect"
        + "(sidl_rmi_InstanceHandle instance, "+IOR.getObjectName(BabelConfiguration.getBaseInterface())+"* *_ex)");
    d_writer.println("{");
    d_writer.tab();

    /*
     * Data
     */
    declareRemoteEPV(ext);
    generateRemoteEPV(ext);

    d_writer.println();
    d_writer.println("sidl_rmi_InstanceHandle_addRef(instance,_ex);SIDL_CHECK(*_ex);");
    d_writer.println("return self;");
    d_writer.println("EXIT:");

    generateRemoteEPVCleanup();

    d_writer.println("return NULL;");
    d_writer.backTab();
    d_writer.println("}");
  }

  /**
   * Generate remote connect support for interfaces.
   */
  private void generateIHConnect(Interface ifc, Class anonCls) {
    comment("Create an instance that uses an already existing ");
    comment("InstanceHandle to connect to an existing remote object.");

    SymbolID ifcID = ifc.getSymbolID();
    String ifcSymName = IOR.getSymbolName(ifcID);

    /*
     * Declare the function prototype.
     */
    d_writer.println("static " + C.getSymbolObjectPtr(ifcID));
    d_writer.println(ifcSymName + "__IHConnect"
        + "(sidl_rmi_InstanceHandle instance, "+IOR.getObjectName(BabelConfiguration.getBaseInterface())+"* *_ex)");
    d_writer.println("{");
    d_writer.tab();

    /*
     * Data
     */
    declareRemoteEPV(anonCls);

    d_writer.println(C.getSymbolObjectPtr(ifcID) + " ret_self = NULL;");

    generateRemoteEPV(anonCls);

    // Cast it to the interface type before returning.
    d_writer.println();
    d_writer.println("sidl_rmi_InstanceHandle_addRef(instance, _ex);");
    d_writer.println();
    d_writer.println("ret_self = ("+C.getSymbolObjectPtr(ifcID)+") (*self->d_epv->f__cast)(self, \""+ifc.getFullName()+"\", _ex);");
    d_writer.println("if(*_ex || !ret_self) { goto EXIT; }");
    d_writer.println("return ret_self;"); 
    d_writer.println("EXIT:"); 

    generateRemoteEPVCleanup();

    //FIXME: Two returns?  This can't possibly be right.
    d_writer.println("return NULL;");
    //    d_writer.println("return (*self->d_epv->f__cast)(((sidl.BaseInterface)self)->d_object, "+ifc.getFullName()+", _ex);");

    d_writer.backTab();
    d_writer.println("}");
  }

  /**
   * Generate remote method support (not yet implemented).
   */
  private void generateRemoteConstructor(Class cls) {
    comment("REMOTE: generate remote instance given URL string.");

    SymbolID id = cls.getSymbolID();
    String idName = id.getFullName();
    String object = IOR.getObjectName(id);

    /*
     * Declare the function prototype.
     */
    d_writer.println("static " + object + "*");
    d_writer.println(IOR.getRemoteName(id)
        + "(const char *url, "+IOR.getObjectName(BabelConfiguration.getBaseInterface())+" **_ex)");
    d_writer.println("{");
    d_writer.tab();
    d_writer.println(IOR.getObjectName(BabelConfiguration.getBaseInterface())+"* _throwaway_exception = NULL;");
    /*
     * Data
     */
    declareRemoteEPV(cls);
    d_writer.println("sidl_rmi_InstanceHandle instance = "
        + "sidl_rmi_ProtocolFactory_createInstance(url, \"" + idName
        + "\", _ex ); SIDL_CHECK(*_ex);");

    d_writer.println("if ( instance == NULL) { return NULL; }");

    generateRemoteEPV(cls);

    d_writer.println();
    d_writer.println("return self;");
    d_writer.println("EXIT:");
    d_writer.println("if(instance) { sidl_rmi_InstanceHandle_deleteRef(instance, &_throwaway_exception); }");

    generateRemoteEPVCleanup();

    d_writer.println("return NULL;");
    d_writer.backTab();
    d_writer.println("}");
  }

  /*
   * This function is created to seperate the declerations of EPV variables from
   * the initilization of them. Therefore, the names declared here must agree
   * with those used in generateRemoteEPV.
   */
  private void declareRemoteEPV(Extendable ext) {
    SymbolID id = ext.getSymbolID();
    String object = IOR.getObjectName(id);

    d_writer.println(object + "* self = NULL;");
    d_writer.println();

    if (!ext.isInterface()) {
      declareParentSelf((Class) ext, 0);
      d_writer.println();
    }
    d_writer.println(IOR.getRemoteStructName(id) + "* r_obj = NULL;");

  }

  private void generateRemoteEPV(Extendable ext) {

    SymbolID id = ext.getSymbolID();
    String name = IOR.getSymbolName(id);
    String object = IOR.getObjectName(id);

    /*
     * Allocate the object data.
     */
    d_writer.println("self =");
    d_writer.tab();
    d_writer.println("(" + object + "*) malloc(");
    d_writer.tab();
    d_writer.println("sizeof(" + object + "));");
    d_writer.backTab();
    d_writer.backTab();
    d_writer.println();

    /*
     * Allocate remote object data
     */
    d_writer.println("r_obj =");
    d_writer.tab();
    d_writer.println("(" + IOR.getRemoteStructName(id) + "*) malloc(");
    d_writer.tab();
    d_writer.println("sizeof(" + IOR.getRemoteStructName(id) + "));");
    d_writer.backTab();
    d_writer.backTab();
    d_writer.println();

    /*
     * Throw MemAlloc exception?
     */
    SymbolID memAllocId = d_context.getSymbolTable().lookupSymbol(BabelConfiguration.getMemoryAllocationException()).getSymbolID();
    d_writer.println("if(!self || !r_obj) {");
    d_writer.tab();
    d_writer.println(C.getSymbolName(memAllocId)+" ex = "+C.getFullMethodName(memAllocId, "getSingletonException")+"(_ex);");
    d_writer.println("SIDL_CHECK(*_ex);");
    d_writer.println(C.getFullMethodName(memAllocId, "setNote")+"(ex, \"Out of memory.\", _ex); SIDL_CHECK(*_ex);");
    d_writer.println(C.getFullMethodName(memAllocId, "add")+"(ex, __FILE__, __LINE__, \"" + 
                     id.getFullName() + ".EPVgeneration\", _ex);");
    d_writer.println("SIDL_CHECK(*_ex);");
    d_writer.println("*_ex = ("+IOR.getObjectName(BabelConfiguration.getBaseInterface())+"*)ex;");
    d_writer.println("goto EXIT;");
    d_writer.backTab();
    d_writer.println("}");

    d_writer.println();
    d_writer.println("r_obj->d_refcount = 1;");
    d_writer.println("r_obj->d_ih = instance;");

    /*
     * Output parent pointers to simplify access to parent classes.
     */
    if (!ext.isInterface()) {
      generateParentSelf((Class) ext, 0, 0);
      d_writer.println();
    }

    /*
     * Ensure that the EPV has been initialized.
     */
    d_writer.println(IOR.getLockStaticGlobalsMacroName() + ";");
    d_writer.println("if (!s_remote_initialized) {");
    d_writer.tab();
    d_writer.println(name + "__init_remote_epv();");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println(IOR.getUnlockStaticGlobalsMacroName() + ";");
    d_writer.println();

    /*
     * Recursively modify the parent classes and set up interface pointers.
     */
    if (ext.isInterface()) {
      d_writer.println("self->" + IOR.getEPVVar(IOR.PUBLIC_EPV) + "    = &"
          + IOR.getStaticEPVVariable(id, IOR.EPV_REMOTE, IOR.SET_PUBLIC) + ";");
      // d_writer.println("self->d_object = (void*) instance;");
    } else {
      remoteEPVs((Class) ext, 0);
      d_writer.println("self->d_data = (void*) r_obj;");
    }
  }

  /*
   * This function cleans up anything malloc'd in generateRemoteEPV in the
   * case of an error.
   * The variable names are from generateRemoteEPV, and are only cleaned up
   * in the case of a failure!
   */
  private void generateRemoteEPVCleanup() {
    d_writer.println("if(self) { free(self); }");
    d_writer.println("if(r_obj) { free(r_obj); }");
  }

  /*
   * This function is created to seperate the declerations of EPV variables from
   * the initilization of them. Therefore, the names declared here must agree
   * with those used in generateParentSelf.
   */
  private void declareParentSelf(Class cls, int level) {
    if (cls != null) {
      SymbolID id = cls.getSymbolID();
      d_writer.print(IOR.getObjectName(id) + "*");
      d_writer.println(" s" + String.valueOf(level) + ";");
      declareParentSelf(cls.getParentClass(), level + 1);
    }
  }

  /**
   * Recursively output self pointers to the sidl objects for this class and its
   * parents. The self pointers are of the form sN, where N is an integer
   * represented by the level argument. If the width is zero, then the width of
   * all parents is generated automatically.
   */
  private void generateParentSelf(Class cls, int level, int width) {
    if (cls != null) {

      /*
       * Calculate the width of this class and all parents for pretty output.
       * Ooh, very pretty.
       */
      if (width == 0) {
        Class parent = cls;
        while (parent != null) {
          int w = IOR.getObjectName(parent.getSymbolID()).length();
          if (w > width) {
            width = w;
          }
          parent = parent.getParentClass();
        }
      }

      /*
       * Now use the width information to print out symbols.
       */
      SymbolID id = cls.getSymbolID();
      // d_writer.printAligned(IOR.getObjectName(id) + "*", width+1);
      d_writer.printAligned("s" + String.valueOf(level) + " = ", width + 1);
      if (level == 0) {
        d_writer.println("self;");
      } else {
        d_writer.print("&s" + String.valueOf(level - 1) + "->d_");
        d_writer.println(IOR.getSymbolName(id).toLowerCase() + ";");
      }
      generateParentSelf(cls.getParentClass(), level + 1, width);
    }
  }

  /*
   * Recursively modify the remote EPVs in parent classes and set up the
   * interfaces.
   */
  private void remoteEPVs(Class cls, int level) {
    if (cls != null) {
      String epvVar = IOR.getEPVVar(IOR.SET_PUBLIC);
      remoteEPVs(cls.getParentClass(), level + 1);

      /*
       * Update the EPVs for all of the new interfaces in this particular class.
       */
      String self = "s" + String.valueOf(level);
      List ifce = Utilities.sort(Utilities.getUniqueInterfaceIDs(cls));
      for (Iterator i = ifce.iterator(); i.hasNext();) {
        SymbolID id = (SymbolID) i.next();
        String n = IOR.getSymbolName(id).toLowerCase();
        d_writer.println(self + "->d_" + n + "." + epvVar + "    = &"
            + IOR.getStaticEPVVariable(id, IOR.EPV_REMOTE, IOR.SET_PUBLIC)
            + ";");
        d_writer.println(self + "->d_" + n + ".d_object = (void*) " + "self;");
        d_writer.println();
      }

      /*
       * Modify the class entry point vector.
       */
      d_writer.println(self + "->d_data = (void*) r_obj;"); // used to be:
      // instance
      d_writer.println(self
          + "->"
          + epvVar
          + "  = &"
          + IOR.getStaticEPVVariable(cls.getSymbolID(), IOR.EPV_REMOTE,
              IOR.SET_PUBLIC) + ";");
      d_writer.println();
    }
  }

  /**
   * Generate the entry point vector alias for each parent class and each
   * interface as well as a special one for the current object.
   */
  private void aliasEPVs(Class cls, Collection parents, boolean remote) {
    /*
     * Get the width of the symbols for pretty printing.
     */
    SymbolID id = cls.getSymbolID();
    String epv = IOR.getEPVName(id);
    int epvType = remote ? IOR.EPV_REMOTE : IOR.EPV_MINE;

    int width = epv.length() + 1;
    int w = Utilities.getWidth(parents) + "struct __epv*".length();
    if (w > width) {
      width = w;
    }

    /*
     * Output the EPV pointer for this class and its class and interface
     * parents.
     */
    d_writer.printAligned(epv + "*", width);
    d_writer.println(" epv = &"
        + IOR.getStaticEPVVariable(id, epvType, IOR.SET_PUBLIC) + ";");
    int e = 0;
    for (Iterator i = parents.iterator(); i.hasNext();) {
      SymbolID sid = (SymbolID) i.next();
      d_writer.printAligned(IOR.getEPVName(sid) + "*", width);
      d_writer.printAligned(" e" + String.valueOf(e++), 4);
      d_writer.println(" = &"
          + IOR.getStaticEPVVariable(sid, epvType, IOR.SET_PUBLIC) + ";");
    }
    d_writer.println();
  }

  /*
   * Copy EPV function pointers from the most derived EPV data structure into
   * all parent class and interface EPVs.
   */
  private void copyEPVs(Collection parents, HashMap renames) throws CodeGenerationException {
    int e = 0;
    for (Iterator i = parents.iterator(); i.hasNext();) {
      /*
       * Extract information about the parent extendable object. Generate a list
       * of the nonstatic methods and calculate the width of every method name.
       */
      SymbolID id = (SymbolID) i.next();
      Extendable ext = (Extendable) Utilities.lookupSymbol(d_context, id);

      List methods = (List) ext.getNonstaticMethods(true);
      int mwidth = Math.max(Utilities.getWidth(methods), s_longestBuiltin)
          + IOR.getVectorEntry("").length();

      /*
       * Calculate the "self" pointer for the cast function.
       */
      String self = null;
      if (ext.isInterface()) {
        self = "void*";
      } else {
        self = IOR.getObjectName(id) + "*";
      }

      /*
       * Generate the assignments for the built-in methods.
       */
      String estring = "e" + String.valueOf(e) + "->";
      String vecEntry = IOR.getVectorEntry(s_castBuiltin);

      d_writer.print(estring);
      d_writer.printAligned(vecEntry, mwidth);
      d_writer.print(" = (void* (*)(" + self);
      d_writer.print(", const char*, "
        + IOR.getObjectName(BabelConfiguration.getBaseInterface())
        + "**)) epv->");
      d_writer.print(vecEntry);
      d_writer.println(";");

      vecEntry = IOR.getVectorEntry(s_deleteBuiltin);
      d_writer.print(estring);
      d_writer.printAligned(vecEntry, mwidth);
      d_writer.println(" = (void (*)(" + self + ", "
        + IOR.getObjectName(BabelConfiguration.getBaseInterface())
        + "**)) epv->" + vecEntry + ";");

      vecEntry = IOR.getVectorEntry(s_getURLBuiltin);
      d_writer.print(estring);
      d_writer.printAligned(vecEntry, mwidth);
      d_writer.println(" = (char* (*)(" + self + ", "
        + IOR.getObjectName(BabelConfiguration.getBaseInterface())
        + "**)) epv->" + vecEntry + ";");

      vecEntry = IOR.getVectorEntry(s_raddRefBuiltin);
      d_writer.print(estring);
      d_writer.printAligned(vecEntry, mwidth);
      d_writer.println(" = (void (*)(" + self + ", "
        + IOR.getObjectName(BabelConfiguration.getBaseInterface())
        + "**)) epv->" + vecEntry + ";");

      vecEntry = IOR.getVectorEntry(s_isRemoteBuiltin);
      d_writer.print(estring);
      d_writer.printAligned(vecEntry, mwidth);
      d_writer.println(" = (sidl_bool (*)(" + self + ", "
        + IOR.getObjectName(BabelConfiguration.getBaseInterface())
        + "**)) epv->" + vecEntry + ";");

      vecEntry = IOR.getVectorEntry(s_set_hooksBuiltin);
      d_writer.print(estring);
      d_writer.printAligned(vecEntry, mwidth);
      d_writer.println(" = (void (*)(" + self + ", sidl_bool, "
        + IOR.getObjectName(BabelConfiguration.getBaseInterface())
        + "**)) epv->" + vecEntry + ";");

      vecEntry = IOR.getVectorEntry(s_set_contractsBuiltin);
      d_writer.print(estring);
      d_writer.printAligned(vecEntry, mwidth);
      d_writer.println(" = (void (*)(" + self 
        + ", sidl_bool, const char*, sidl_bool, "
        + IOR.getObjectName(BabelConfiguration.getBaseInterface())
        + "**)) epv->" + vecEntry + ";");

      vecEntry = IOR.getVectorEntry(s_dump_statsBuiltin);
      d_writer.print(estring);
      d_writer.printAligned(vecEntry, mwidth);
      d_writer.println(" = (void (*)(" + self 
        + ", const char*, const char*, "
        + IOR.getObjectName(BabelConfiguration.getBaseInterface())
        + "**)) epv->" + vecEntry + ";");


      /*
       * Generate the assignment for exec function
       */
      vecEntry = IOR.getVectorEntry(s_execBuiltin);
      Method m_exec = IOR.getBuiltinMethod(IOR.EXEC, id, d_context, false);
      String ename = m_exec.getLongMethodName();
      d_writer.print(estring);
      d_writer.printAligned(IOR.getVectorEntry(ename), mwidth);
      d_writer.print(" = " + IOR.getCast(m_exec, self, d_context));
      d_writer.println(" epv->" + IOR.getVectorEntry(ename) + ";");

      /*
       * Iterate over all methods in the EPV and set the method pointer.
       */
      for (Iterator j = methods.iterator(); j.hasNext();) {
        Method method = (Method) j.next();
        Method renamedMethod = (Method) renames.get(id.getFullName()+"."+
                                                    method.getLongMethodName());
        String oldname = method.getLongMethodName();
        String newname = null;
        if(renamedMethod != null) {
          newname = renamedMethod.getLongMethodName();
        } else { 
          newname = method.getLongMethodName();
        }
        d_writer.print(estring);
        d_writer.printAligned(IOR.getVectorEntry(oldname), mwidth);
        d_writer.print(" = " + IOR.getCast(method, self, d_context));
        d_writer.println(" epv->" + IOR.getVectorEntry(newname) + ";");
      }

      d_writer.println();
      e++;
    }
  }

  public static void generateExternalSignature(LanguageWriter lw, Symbol sym,
      String terminator) {
    final SymbolID id = sym.getSymbolID();
    lw.beginBlockComment(false);
    lw.println("This function returns a pointer to a static structure of");
    lw.println("pointers to function entry points.  Its purpose is to "
        + "provide");
    lw.println("one-stop shopping for loading DLLs.");
    lw.endBlockComment(false);
    lw.println("const " + IOR.getExternalName(id) + "*");
    lw.println(IOR.getExternalFunc(id) + "(void)" + terminator);
  }

  public void generateConnectInternal(Extendable ext) {
    SymbolID id = ext.getSymbolID();
    
    d_writer.writeComment("RMI connector function for the class.", false);
    d_writer.println(C.getSymbolObjectPtr(id));
    d_writer.println(C.getFullMethodName(id, "_connectI") 
                     + "(const char* url, sidl_bool ar, " +
                     IOR.getExceptionFundamentalType() +
                     "*_ex)");
      
    d_writer.println("{");
    d_writer.tab();
    
    d_writer.println("return " + C.getFullMethodName(id,"_remoteConnect") 
                     + "(url, ar, _ex);");
    
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println(); 

  }
}
