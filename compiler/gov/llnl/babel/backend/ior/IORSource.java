//
// File:        IORSource.java
// Package:     gov.llnl.babel.backend.ior
// Revision:    @(#) $Id: IORSource.java 7409 2011-12-09 01:11:10Z tdahlgren $
// Description: generate IOR implementation source to a pretty writer stream
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

package gov.llnl.babel.backend.ior;

import gov.llnl.babel.BabelConfiguration;
import gov.llnl.babel.backend.c.C;
import gov.llnl.babel.backend.CodeConstants;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.rmi.RMIIORSource;
import gov.llnl.babel.backend.Utilities;
import gov.llnl.babel.backend.writers.LanguageWriterForC;
import gov.llnl.babel.Context;
import gov.llnl.babel.symbols.Assertion;
import gov.llnl.babel.symbols.Class;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Interface;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.MethodCall;
import gov.llnl.babel.symbols.Struct;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.SymbolTable;
import gov.llnl.babel.symbols.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Class <code>IORSource</code> generates an IOR implementation source file 
 * to a language writer output stream. The constructor takes a language 
 * writer stream and method <code>generateCode</code> generates intermediate 
 * object representation for the specified symbol to the output stream. The 
 * language writer output stream is not closed by this object.
 */
public class IORSource {
  private static int s_longestBuiltin;

  private static final String s_castBuiltin   = IOR.getBuiltinName(IOR.CAST);
  private static final String s_deleteBuiltin = IOR.getBuiltinName(IOR.DELETE);
  private static final String s_execBuiltin   = IOR.getBuiltinName(IOR.EXEC);
  private static final String s_getURLBuiltin = IOR.getBuiltinName(IOR.GETURL);
  private static final String s_raddRefBuiltin  = IOR
    .getBuiltinName(IOR.RADDREF);
  private static final String s_isRemoteBuiltin = IOR
    .getBuiltinName(IOR.ISREMOTE);

  private static final String s_self          = Utilities.s_self;
  private static final String s_exception_var = Utilities.s_exception;
  private static final String s_superBuiltin  = "__getSuperEPV";

  private static final String s_contracts_debug = "SIDL_CONTRACTS_DEBUG";
  private static final String s_contracts_okay = "cOkay";
  private static final String s_sim_trace     = "SIDL_SIM_TRACE";
  private static final String s_vio_dispatch  = "SIDL_NO_DISPATCH_ON_VIOLATION";

  private static final String s_exc_check    = "SIDL_CHECK(*_ex);";
  private static final String s_set_to_null  = " = " + C.NULL + ";";

  private static final String s_preEPV   = "s_preEPV";
  private static final String s_postEPV  = "s_postEPV";
  private static final String s_preSEPV  = "s_preSEPV";
  private static final String s_postSEPV = "s_postSEPV";


  /**
   * Some contract checking defaults...at least until we provide another
   * mechanism.
   */
  private final static int CONTRACT_DECLARE = 1;
  private final static int CONTRACT_RAISE   = 2;

  private Context d_context = null;

  /**
   * Store the SymbolID for sidl.BaseClass, if the extendable being 
   * printed is not abstract (i.e., is a concrete class).
   */
  SymbolID d_baseClass = null;

  /**
   * Store the SymbolID for sidl.ClassInfo, if the extendable being 
   * printed is not abstract (i.e., is a concrete class).
   */
  SymbolID d_classInfo = null;

  /**
   * Store the SymbolID for sidl.ClassInfoI, if the extendable being 
   * printed is not abstract (i.e., is a concrete class).
   */
  SymbolID d_classInfoI = null;

  static {
    s_longestBuiltin = 0;
    for (int j = 0; j < IOR.CLASS_BUILT_IN_METHODS; ++j) {
      String mname = IOR.getBuiltinName(j, true);
      if (mname.length() > s_longestBuiltin) {
        s_longestBuiltin = mname.length();
      }
    }
  }

  private LanguageWriterForC d_writer;

  /**
   * This is a convenience utility function that writes the symbol source
   * information into the provided language writer output stream. The output
   * stream is not closed on exit. A code generation exception is thrown if 
   * an error is detected.
   */
  public static void generateCode(Symbol symbol, 
                                  LanguageWriterForC writer,
                                  Context context)
    throws CodeGenerationException 
  {
    IORSource source = new IORSource(writer, context);
    source.generateCode(symbol);
  }

  /**
   * Create a <code>IORSource</code> object that will write symbol information
   * to the provided output writer stream.
   */
  public IORSource(LanguageWriterForC writer,
                   Context context) {
    d_writer = writer;
    d_context = context;
  }

  /**
   * Write IOR source information for the provided symbol to the language 
   * writer output stream provided in the constructor. This method does not 
   * close the language writer output stream and may be called for more than 
   * one symbol (although the generated source may not be valid input for 
   * the C compiler).  A code generation exception is generated if an error 
   * is detected.  No code is generated for enumerated and package symbols.
   */
  public void generateCode(Symbol symbol) throws CodeGenerationException {
    if (symbol != null) {
      switch (symbol.getSymbolType()) {
      case Symbol.PACKAGE:
      case Symbol.INTERFACE:
        break;
      case Symbol.CLASS:
        generateSource((Class) symbol);
        break;
      }
    }
  }

  private void writeStructInclude(Struct strct)
  {
    Iterator i         = strct.getItems().iterator();
    boolean  hasStruct = false;
    HashSet  set       = new HashSet();
    if (strct.hasArrayReference()) {
      d_writer.generateInclude("sidlArray.h", true);
    }
    while (i.hasNext()) {
      final Struct.Item item = (Struct.Item)i.next();
      final Type        t    = item.getType();
      switch(t.getDetailedType()) {
      case Type.STRUCT:
        hasStruct = true; /* fall through intended */
      case Type.INTERFACE:
      case Type.CLASS:
        final String header = IOR.getHeaderFile(t.getSymbolID());
        if (!set.contains(header)) {
          d_writer.generateInclude(header, true);
          set.add(header);
        }
        break;
      case Type.STRING:
        final String strheader = "sidl_String.h";
        if (!set.contains(strheader)) {
          d_writer.generateInclude(strheader, true);
          set.add(strheader);
        }
      }
    }
    if (hasStruct) {
      d_writer.printlnUnformatted("#ifdef SIDL_DYNAMIC_LIBRARY");
      d_writer.generateInclude("sidlOps.h", true);
      d_writer.printlnUnformatted("#endif");
    }
    d_writer.println();

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
   * Generate the IOR source for a SIDL class or interface. The source 
   * file begins with a banner and include files followed by a declaration 
   * of static methods and (for a class) external methods expected in 
   * the skeleton file.  For classes, the source file then defines a number 
   * of functions (cast, delete, initialize EPVs, new, init, and fini).
   */
  private void generateSource(Class cls) throws CodeGenerationException {
    /*
     * Generate the file banner and include files.
     */
    SymbolID id     = cls.getSymbolID();
    String   source = IOR.getSourceFile(id);
    String   header = IOR.getHeaderFile(id);

    boolean genContractChecks = IOR.generateContractChecks(cls, d_context);

    d_writer.writeBanner(cls, source, CodeConstants.C_IS_NOT_IMPL,
                         CodeConstants.C_DESC_IOR_PREFIX + id.getFullName());
    if (!d_context.getConfig().getSkipRMI()) {
      comment("Begin: RMI includes");
      d_writer.printlnUnformatted("#include \"sidl_rmi_InstanceHandle.h\"");
      d_writer.printlnUnformatted("#include \"sidl_rmi_InstanceRegistry.h\"");
      d_writer.printlnUnformatted("#include \"sidl_rmi_ServerRegistry.h\"");
      d_writer.printlnUnformatted("#include \"sidl_rmi_Call.h\"");
      d_writer.printlnUnformatted("#include \"sidl_rmi_Return.h\"");
      d_writer.printlnUnformatted("#include \"sidl_exec_err.h\"");
      d_writer.printlnUnformatted("#include \"sidl_PreViolation.h\"");
      d_writer.printlnUnformatted("#include \"sidl_NotImplementedException.h\"");
      d_writer.printlnUnformatted("#include <stdio.h>");
      comment("End: RMI includes");
    }
    d_writer.printlnUnformatted("#include \"sidl_Exception.h\"");
    d_writer.printlnUnformatted("#include <stdlib.h>");
    d_writer.printlnUnformatted("#include <stddef.h>");
    d_writer.printlnUnformatted("#include <string.h>");
    if (genContractChecks) {
      d_writer.printlnUnformatted("#include <stdio.h>");
      d_writer.printlnUnformatted("#if TIME_WITH_SYS_TIME");
      d_writer.printlnUnformatted("#  include <sys/time.h>");
      d_writer.printlnUnformatted("#  include <time.h>");
      d_writer.printlnUnformatted("#else");
      d_writer.printlnUnformatted("#  if HAVE_SYS_TIME_H");
      d_writer.printlnUnformatted("#    include <sys/time.h>");
      d_writer.printlnUnformatted("#  else");
      d_writer.printlnUnformatted("#    include <time.h>");
      d_writer.printlnUnformatted("#  endif");
      d_writer.printlnUnformatted("#endif");
      d_writer.printlnUnformatted("#include \"sidlAsserts.h\"");
      d_writer.printlnUnformatted("#include \"sidl_Enforcer.h\"");
      d_writer.printlnUnformatted("/* #define " + s_contracts_debug + " 1 */");
      d_writer.println();
      d_writer.printlnUnformatted("#define " + s_vio_dispatch + " 1");
      d_writer.printlnUnformatted("#define " + s_sim_trace + " 1");
      d_writer.printlnUnformatted("#ifdef " + s_sim_trace);
      d_writer.printlnUnformatted("#include <stdio.h>");
      d_writer.printlnUnformatted("#include <string.h>");
      d_writer.printlnUnformatted("#endif /* " + s_sim_trace + " */");
      d_writer.println();
    }
    d_writer.generateInclude("sidlOps.h", true);
    d_writer.generateInclude(header, false);
    generateIncludes(cls);
    lookupSymbolIDs();
    d_writer.generateInclude(C.getImplHeaderFile(d_baseClass), true);
    d_writer.generateInclude(C.getHeaderFile(d_baseClass), true);
    d_writer.generateInclude(C.getHeaderFile(d_classInfo), true);
    d_writer.generateInclude(C.getHeaderFile(d_classInfoI), true);
    d_writer.println();

    d_writer.printlnUnformatted("#ifndef " + C.NULL);
    d_writer.printlnUnformatted("#define " + C.NULL + " 0");
    d_writer.printlnUnformatted("#endif");
    d_writer.println();

    String my_mutex    = IOR.getSymbolName(id) + "__mutex";
    String lock_name   = IOR.getLockStaticGlobalsMacroName();
    String unlock_name = IOR.getUnlockStaticGlobalsMacroName();
    String have_lock   = IOR.getHaveLockStaticGlobalsMacroName();
    String mutexPrefix = "sidl_recursive_mutex_";

    d_writer.printlnUnformatted("#include \"sidl_thread.h\"");
    d_writer.printlnUnformatted("#ifdef HAVE_PTHREAD");
    d_writer.printUnformatted("static struct " + mutexPrefix + "t ");
    d_writer.printUnformatted(my_mutex);
    d_writer.printlnUnformatted("= SIDL_RECURSIVE_MUTEX_INITIALIZER;");
    d_writer.printUnformatted("#define " + lock_name);
    d_writer.printUnformatted(" " + mutexPrefix + "lock( &" + my_mutex);
    d_writer.printlnUnformatted(" )");
    d_writer.printUnformatted("#define " + unlock_name);
    d_writer.printUnformatted(" " + mutexPrefix + "unlock( &" + my_mutex);
    d_writer.printlnUnformatted(" )");
    d_writer.printUnformatted("/* #define " + have_lock);
    d_writer.printUnformatted(" (" + mutexPrefix + "trylock( &" + my_mutex);
    d_writer.printlnUnformatted(" )==EDEADLOCK) */");
    d_writer.printlnUnformatted("#else");
    d_writer.printlnUnformatted("#define " + lock_name);
    d_writer.printlnUnformatted("#define " + unlock_name);
    d_writer.printlnUnformatted("/* #define " + have_lock + " (1) */");
    d_writer.printlnUnformatted("#endif");
    d_writer.println();

    if (genContractChecks) {
      String  traceArgs = "MID, PRC, POC, INC, MT, PRT, POT, IT1, IT2";
      String  defTrace  = "#define TRACE(CN, MD, " + traceArgs + ")";

      d_writer.printlnUnformatted("#define RESETCD(MD) { \\");
      d_writer.printUnformatted("  if (MD) { (MD)->" + IOR.D_EST_INTERVAL);
      d_writer.printUnformatted(" = ");
      d_writer.printlnUnformatted("sidl_Enforcer_getEstimatesInterval(); } \\");
      d_writer.printlnUnformatted("}");
      d_writer.printlnUnformatted("#ifdef " + s_sim_trace);
      d_writer.printlnUnformatted(defTrace + " { \\");
      d_writer.printUnformatted("  if (MD) { ");
      d_writer.printUnformatted("sidl_Enforcer_logTrace(CN, (MD)->name, ");
      d_writer.printlnUnformatted(traceArgs + "); } \\");
      d_writer.printlnUnformatted("}");
      d_writer.printlnUnformatted("#else /* !" + s_sim_trace + " */");
      d_writer.printlnUnformatted(defTrace);
      d_writer.printlnUnformatted("#endif /* " + s_sim_trace + " */");
      d_writer.println();
    }

    /*
     * Generate internal static variables and external references to be 
     * supplied by the skeleton file.
     */
    generateStaticVariables(cls);
    generateExternalReferences(cls);

    /*
     * Generate a number of local functions (cast, delete, initialization 
     * of EPVs, new, init, and fini). These functions are only needed for
     * classes.
     */
    boolean doStatic = cls.hasStaticMethod(true);

    if (!d_context.getConfig().getSkipRMI()) {
      RMIIORSource.generateCode(cls, d_writer, d_context);
    }
    if (doStatic) {
      generateChecksFunction(cls, true);
      generateDumpStatsFunction(cls, true);
    }
    generateChecksFunction(cls, false);
    generateDumpStatsFunction(cls, false);

    if (genContractChecks) {
      generateAllChecks(cls);
    }
  
    generateEnsureLoad(cls);
    IOR.generateCastFunction(cls,s_self,d_writer, false,true);

    if (doStatic) {
      generateHooksFunction(cls, true);
    }
    generateHooksFunction(cls, false);

    boolean genHookMethods = IOR.generateHookMethods(cls, d_context);
    if (genHookMethods) {
      generateAllHooks(cls);
    }
    generateDeleteFunction(cls);
    if (!d_context.getConfig().getSkipRMI()) {
      generateGetURLFunction(cls);
      generateIsLocalIsRemote(cls);
      generateMainExec(cls);
      generateNonblockingMethods(cls);
    }
    generateInitEPV(cls);
    generateInitSEPV(cls);
    declareGetEPVs(cls);
    generateStaticFunction(cls, false);
    generateSuperFunction(cls);
    if (genContractChecks || genHookMethods) {
      generateStaticFunction(cls, true);
    }
    generateInitClassInfo(cls);
    generateInitMetadata(cls);
    generateNewFunction(cls);
    generateInitFunction(cls);
    generateFiniFunction(cls);
    generateVersionFunction(cls);
    generateExternalFunc(cls);
  }

  private void generateIncludes(Class ext)
    throws CodeGenerationException
  {
    final SymbolID eid        = ext.getSymbolID();
    Set            references = ext.getSymbolReferences();
    if (references != null ) {
      Iterator i = references.iterator();
      while (i.hasNext()) {
        final SymbolID id  = (SymbolID)i.next();
        final Symbol   sym = Utilities.lookupSymbol(d_context, id);
        if (sym instanceof Struct) {

          d_writer.generateInclude(IOR.getHeaderFile(sym.getSymbolID()),true);

          if (!d_context.getConfig().getSkipRMI()) {
            d_writer.printUnformatted("#define RMI_" + IOR.getSymbolName(id));
            d_writer.printUnformatted("_serialize ");
            d_writer.printlnUnformatted(IOR.getSkelSerializationName(eid, id, 
                                                                     true));
            d_writer.printUnformatted("#define RMI_" + IOR.getSymbolName(id));
            d_writer.printUnformatted("_deserialize ");
            d_writer.printlnUnformatted(IOR.getSkelSerializationName(eid, id, 
                                                                     false));
          }
        }
      }
    }
  }

  /**
   * Generate a single line comment. This is called out as a separate 
   * method to make the code formatting below a little prettier.
   */
  private void comment(String s) {
    d_writer.writeComment(s, false);
  }

  private void generateStaticEPVDecl(SymbolID id, String epvStr, int epvType,
                                     int setType) 
  {
    String var = IOR.getStaticEPVVariable(id, epvType, setType);
    d_writer.println(epvStr + var + ";");
  }

  /**
   * Generate the static variables used to store the EPVs and also the
   * initialization flags for the EPVs. Classes require EPVs for their 
   * static methods (if present), standard methods, and old and new 
   * versions for all parent classes and interfaces.
   */
  private void generateStaticVariables(Class cls) 
    throws CodeGenerationException 
  {
    String declStr = "static const int32_t ";
    comment("Static variables to hold version of IOR");
    d_writer.print(declStr);
    d_writer.println("s_IOR_MAJOR_VERSION = " + IOR.MAJOR_VERSION + ";");
    d_writer.print(declStr);
    d_writer.println("s_IOR_MINOR_VERSION = " + IOR.MINOR_VERSION + ";");
    d_writer.println();

    if (!cls.isAbstract()) {
      comment("Static variable to hold shared ClassInfo interface.");
      d_writer.print("static " + C.getObjectName(d_classInfo));
      d_writer.println(" s_classInfo " + s_set_to_null);
      if (d_classInfoI.equals(cls.getSymbolID())) {
        d_writer.println("static int s_classInfo_init = 1;");
      }
      d_writer.println();
    }

    comment("Static variable to make sure _load called no more than once.");
    d_writer.println("static int s_load_called = 0;");
    d_writer.println();

    comment("Static variables for managing EPV initialization.");

    /*
     * Output the initialization flags for the EPV structures.
     */
    boolean has_static = cls.hasStaticMethod(true);
    
    d_writer.println("static int s_method_initialized = 0;");
    if (has_static) {
      d_writer.println("static int s_static_initialized = 0;");
    }
    d_writer.println();

    /*
     * Output the EPV and static EPV for this object for each supported 
     * type of static EPV.
     */
    IOR.generateStaticEPVVariables(d_writer, cls, has_static, false,
                                   IOR.SET_PUBLIC);

    boolean genContractEPVs = IOR.generateContractEPVs(cls, d_context);
    if (genContractEPVs) {
      IOR.generateStaticEPVVariables(d_writer, cls, has_static, false,
                                     IOR.SET_CONTRACTS);
    }

    boolean genHookEPVs = IOR.generateHookEPVs(cls, d_context);
    if (genHookEPVs) {
      IOR.generateStaticEPVVariables(d_writer, cls, has_static, false,
                                     IOR.SET_HOOKS);
    }

    //    comment("Parent class hooks epvs");
    //Class parentclass = cls.getParentClass();
    //if (parentclass != null) {
    //  IOR.generateStaticEPVVariables(d_writer, parentclass, has_static, false,
    //                                IOR.SET_HOOKS);
    //}
    /*
     * Collect all the parents of the class in a set and output EPV structures
     * for the parents.
     */
    Set parents        = Utilities.getAllParents(cls);
    Set new_interfaces = Utilities.getUniqueInterfaceIDs(cls);

    if (!parents.isEmpty()) {
      List sorted = Utilities.sort(parents);
      for (Iterator i = sorted.iterator(); i.hasNext();) {
        Extendable p_ext    = (Extendable) i.next();
        SymbolID   p_id     = p_ext.getSymbolID();
        String     p_epv    = "static " + IOR.getEPVName(p_id);
        boolean    is_par   = !new_interfaces.contains(p_id);
        String     p_epvStr = (is_par ? p_epv + "  " : p_epv + " ");

        generateStaticEPVDecl(p_id, p_epvStr, IOR.EPV_MINE, IOR.SET_PUBLIC);
        if (IOR.generateHookEPVs(p_ext, d_context)) {
          generateStaticEPVDecl(p_id, p_epvStr, IOR.EPV_MINE, IOR.SET_HOOKS);
        }
        if (is_par) {
          String epvStr = p_epv + "* ";
          generateStaticEPVDecl(p_id, epvStr, IOR.EPV_PARENT, IOR.SET_PUBLIC);
          if (IOR.generateHookEPVs(p_ext, d_context)) {
            generateStaticEPVDecl(p_id, epvStr, IOR.EPV_PARENT, IOR.SET_HOOKS);
          }
        }
        d_writer.println();
      }
    }

    if (  has_static && ( genContractEPVs || genHookEPVs ) ) {
      comment("Static variables for interface contract enforcement and/or "
             + "hooks controls.");

      String cStats = "static " + IOR.MACRO_VAR_UNUSED + " ";
      cStats        += IOR.getControlsNStatsStruct(cls.getSymbolID());

      int cWidth = cStats.length() + 1;
      d_writer.printAligned(cStats, cWidth);
      d_writer.println(IOR.S_CSTATS + ";");
      d_writer.println();
    }
    if (IOR.generateContractChecks(cls, d_context)) {
      comment("Static file for interface contract enforcement statistics.");
      d_writer.println("static FILE* " + IOR.S_DUMP_FPTR + s_set_to_null);
      d_writer.println();
    }

    //Declare static hooks epvs
    if (genHookEPVs) {
      SymbolID id = cls.getSymbolID();
      d_writer.print("static " + IOR.getPreEPVName(id) + " ");
      d_writer.println(s_preEPV + ";");
      d_writer.print("static " + IOR.getPostEPVName(id) + " ");
      d_writer.println(s_postEPV + ";");

      if (has_static) {
        d_writer.print("static " + IOR.getPreSEPVName(id) + " ");
        d_writer.println(s_preSEPV + ";");
        d_writer.print("static " + IOR.getPostSEPVName(id) + " ");
        d_writer.println(s_postSEPV + ";");
      }

      d_writer.println();
    }

    if(d_context.getConfig().getFastCall()) {
      comment("used for initialization of native epv entries.");
      d_writer.println("static const sidl_babel_native_epv_t NULL_NATIVE_EPV  = { BABEL_LANG_UNDEF, NULL};");
    }
    
  }

  /**
   * Generate external references for skeleton routines that define the
   * functions in the EPVs. A class will have a method EPV. If there are static
   * functions, then there must also be a static EPV.
   */
  private void generateExternalReferences(Class cls) {
    comment("Declare EPV routines defined in the skeleton file.");

    SymbolID id = cls.getSymbolID();
    d_writer.openCxxExtern();
    d_writer.println("extern void " + IOR.getSetEPVName(id) + "(");
    d_writer.tab();
    d_writer.print(IOR.getEPVName(id) + "* epv");

    boolean genHookEPVs = IOR.generateHookEPVs(cls, d_context);
    if (genHookEPVs) {
      d_writer.println(",");
      d_writer.tab();
      d_writer.println(IOR.getPreEPVName(id) + "* pre_epv,");
      d_writer.println(IOR.getPostEPVName(id) + "* post_epv);");
      d_writer.backTab();
    } else {
      d_writer.println(");");
    }
    d_writer.backTab();
    d_writer.println();

    if (cls.hasStaticMethod(true)) {
      d_writer.println("extern void " + IOR.getSetSEPVName(id) + "(");
      d_writer.tab();
      d_writer.print(IOR.getSEPVName(id) + "* sepv");
      if (genHookEPVs) {
        d_writer.println(",");
        d_writer.tab();
        d_writer.println(IOR.getPreSEPVName(id) + "* pre_sepv,");
        d_writer.println(IOR.getPostSEPVName(id) + "* post_sepv);");
        d_writer.backTab();
      } else {
        d_writer.println(");");
      }
      d_writer.backTab();
      d_writer.println();
    }
    d_writer.println("extern void " + IOR.getCallLoadName(id) + "(void);");
    d_writer.closeCxxExtern();

    d_writer.println();
  }

  /**
   * Returns the controls and statistics variable base for the specified 
   * version.
   */
  private String getBaseControlsNStats(boolean doStatic, String self) {
    return (doStatic ? IOR.S_CSTATS : self + "->" + IOR.D_CSTATS);
  }

  /**
   * Returns the method controls and statistics variable base for the 
   * specified base and index variable name.
   */
  private String getMethodControlsNStats(String base, String indexVar) {
    return base + IOR.D_METHOD_CSTATS + "[" + indexVar + "]";
  }

  /**
   * Returns the method controls variable base for the specified version
   * and index variable name.
   */
  private String getMethodControlsNStats(boolean doStatic, String self,
                                         String indexVar)
  {
    return getMethodControlsNStats(getBaseControlsNStats(doStatic, self), 
                                   indexVar);
  }

  /**
   * Returns the name of the built-in method, prepending "ior_" and the name of
   * the symbol.
   */
  private String getIORMethodName(SymbolID id, String name) {
    return "ior_" + IOR.getSymbolName(id) + '_' + name;
  }

  /**
   * Returns the name of the built-in trace method, prepending "ior_" and the
   * name of the symbol.
   */
  private String getDumpTraceMethodName(SymbolID id) {
    return "ior_" + IOR.getSymbolName(id) + "_dumpTrace";
  }

  /**
   * Returns the name of the basic trace output file, prepending "ior_" and the
   * name of the symbol.
   */
  private String getDumpTraceFileName(SymbolID id) {
    return IOR.getSymbolName(id) + "_trace.out";
  }

  /**
   * Returns the name of the specified version of the function to set the
   * contract checking level.
   */
  private String getSetChecksMethodName(SymbolID id, boolean doStatic) {
    return getIORMethodName(id, IOR.getBuiltinName(IOR.CONTRACTS, doStatic));
  }

  /**
   * Generate the EPV assignment.
   */
  private void generateEPVAssignment(SymbolID id, String epvStr, int epvType, 
                                     int setType) 
  {
    String var = IOR.getStaticEPVVariable(id, epvType, setType);

    d_writer.println(epvStr + "  = &" + var + ";");
  }


  /**
   * Generate the specified function to set the contract checking level.
   */
  private void generateChecksFunction(Extendable ext, boolean doStatic) 
    throws CodeGenerationException 
  {
    String desc = doStatic ? "static " : "";
    comment("CHECKS: Enable/disable " + desc + "contract enforcement.");

    SymbolID id                 = ext.getSymbolID();
    String   name               = IOR.getSymbolName(id);
    boolean  genContractChecks  = IOR.generateContractChecks(ext, d_context);
    boolean  genContractsNHooks = genContractChecks 
                                && IOR.generateHookMethods(ext, d_context);

    d_writer.print("static void " + getSetChecksMethodName(id, doStatic));
    d_writer.println("(");
    d_writer.tab();
    if (!doStatic) {
      d_writer.println(IOR.getObjectName(id) + "* " + s_self + ",");
    }
    d_writer.println("sidl_bool   enable,");
/*
 * TLD/ToDo:  Remove enfFilename parameter since appears to cause problems
 *            within CCA.
 */
    d_writer.println("const char* enfFilename,");
    d_writer.println("sidl_bool   resetCounters,");
    d_writer.println(IOR.getExceptionFundamentalType() + "*_ex)");
    d_writer.backTab();

    d_writer.println("{");
    d_writer.tab();
    d_writer.println("*_ex " + s_set_to_null);
    d_writer.println("{");
    d_writer.tab();

    if (genContractChecks) {
      String  base   = getBaseControlsNStats(doStatic, s_self) + ".";
      String  mBase  = "ms->";

      boolean hasInv = ext.hasInvClause(true);

      d_writer.println(IOR.getMethodDescDataStruct(id) + " *md;");
      if (hasInv) {
        d_writer.println(IOR.getInvDescDataStruct(id) + " *invd= ");
        d_writer.tab();
        d_writer.println("&" + IOR.getInvDescDataName(id) + ";");
        d_writer.backTab();
      }
      d_writer.println(IOR.getMethodControlsNStatsStruct(id) + " *ms;");
      d_writer.println();
/*
 * TLD/ToDo:  Simply use the default name when eliminate enfFilename arg.
 */
      d_writer.println("const char* filename = (enfFilename) ? enfFilename");
      d_writer.println("                     : \"" + name + ".dat\";");
      d_writer.println("FILE*  fptr  " + s_set_to_null);
      d_writer.println("int    ind, invc, prec, posc;");
      d_writer.println("double invt, mt, pret, post;");
      d_writer.println();
      d_writer.println(base + IOR.D_ENABLED + " = enable;");
      d_writer.println();
      d_writer.println("if (  (filename) ");
      d_writer.println("   && (sidl_Enforcer_usingTimingData()) ) {");
      d_writer.tab();
      d_writer.println("fptr = fopen(filename, \"r\");");
      d_writer.println("if (fptr != " + C.NULL + ") {");
      d_writer.tab();

      d_writer.beginBlockComment(false);
      d_writer.println(" * The first line is assumed to contain the invariant");
      d_writer.println(" * complexity and average enforcement cost REGARDLESS");
      d_writer.println(" * of specification of invariants.");
      d_writer.endBlockComment(false);

      if (hasInv) {
        d_writer.print("if (fscanf(fptr, \"%d %lf\\n\", ");
        d_writer.println("&invc, &invt) != EOF) {");
        d_writer.tab();
        d_writer.println("invd->" + IOR.D_INV_COMPLEXITY + " = invc;");
        d_writer.println("invd->" + IOR.D_EXEC_INV_TIME + "  = invt;");
        d_writer.backTab();
        d_writer.println("}");
      } else {
        d_writer.print("fscanf(fptr, \"%d %lf\\n\", &invc, &invt);");
      }
      d_writer.println();
      d_writer.println("while (fscanf(fptr, \"%d %d %d %lf %lf %lf\\n\",");
      d_writer.tab();
      d_writer.println("&ind, &prec, &posc, &mt, &pret, &post) != EOF)");
      d_writer.backTab();
      d_writer.println("{");
      d_writer.tab();
      d_writer.println("if (  (" + IOR.getMethodIndex(id, "MIN") + " <= ind)");
      d_writer.print("   && (ind <= " + IOR.getMethodIndex(id, "MAX"));
      d_writer.println(") )" + " {");
      d_writer.tab();
      d_writer.println("md = &" + IOR.getMethodDescDataName(id) + "[ind];");
      d_writer.println("md->" + IOR.D_PRE_COMPLEXITY + "  = prec;");
      d_writer.println("md->" + IOR.D_POST_COMPLEXITY + " = posc;");
      d_writer.println("md->" + IOR.D_EXEC_METH_TIME + "  = mt;");
      d_writer.println("md->" + IOR.D_EXEC_PRE_TIME + "   = pret;");
      d_writer.println("md->" + IOR.D_EXEC_POST_TIME + "  = post;");
      d_writer.backTab();
      d_writer.println("} else {");
      d_writer.tab();
      d_writer.print("printf(\"ERROR:  Invalid method index, %d, ");
      d_writer.print("in contract metrics file %s\\n\", ");
      printLineBreak("ind, filename);");
      d_writer.println("return;");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println("fclose(fptr);");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();
      d_writer.println("if (resetCounters) {");
      d_writer.tab();
      d_writer.println("int i;");
      d_writer.println("for (i =" + IOR.getMethodIndex(id, "MIN") + ";");
      d_writer.print("     i<=" + IOR.getMethodIndex(id, "MAX") + "; i++)");
      d_writer.println(" {");
      d_writer.tab();
      d_writer.println("ms = &" + getMethodControlsNStats(base, "i") + ";");
      d_writer.println(mBase + IOR.D_TRIES + "          = 0;");
      d_writer.println(mBase + IOR.D_SUCCESSES + "      = 0;");
      d_writer.println(mBase + IOR.D_FAILURES + "       = 0;");
      d_writer.println(mBase + IOR.D_NONVIO_EXCEPTS + " = 0;");
      d_writer.println();
      d_writer.println("md = &" + IOR.getMethodDescDataName(id) + "[i];");
      d_writer.println("RESETCD(md);");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("}");

      if (!doStatic) {
        d_writer.println();
        d_writer.println("/* Ensure the EPVs are set properly. */");
      }
    }

    /*
     * ToDo...Need to figure out a way to get the Stub to reset its
     *        _externals pointer for static classes so we can bypass
     *        the enforcement code when it is disabled through the
     *        local _set_checks() method.
     */
    if (!doStatic) {
      String publicepv = s_self + "->" + IOR.getEPVVar(IOR.PUBLIC_EPV);
      String csepv     = IOR.getStaticEPVVariable(id, IOR.EPV_MINE,
                                                  IOR.SET_CONTRACTS);
      String psepv     = IOR.getStaticEPVVariable(id, IOR.EPV_MINE,
                                                  IOR.SET_PUBLIC);
      if (genContractsNHooks) {
        String use     = getBaseControlsNStats(false, s_self) + ".";
        use += IOR.D_HOOKS;
        d_writer.println("if (enable && (" + use + ")) {");
        d_writer.tab();
        fixContractEPVs((Class)ext, IOR.PUBLIC_EPV, IOR.SET_CONTRACTS);
        fixContractEPVs((Class)ext, IOR.BASE_EPV, IOR.SET_HOOKS);
        d_writer.backTab();
        d_writer.println("} else if (enable) {");
        d_writer.tab();
        fixContractEPVs((Class)ext, IOR.PUBLIC_EPV, IOR.SET_CONTRACTS);
        fixContractEPVs((Class)ext, IOR.BASE_EPV, IOR.SET_PUBLIC);
        d_writer.backTab();
        d_writer.println("} else if (" + use + ") {");
        d_writer.tab();
        fixContractEPVs((Class)ext, IOR.PUBLIC_EPV, IOR.SET_HOOKS);
        fixContractEPVs((Class)ext, IOR.BASE_EPV, IOR.SET_PUBLIC);
        d_writer.backTab();
        d_writer.println("} else {");
        d_writer.tab();
        fixContractEPVs((Class)ext, IOR.PUBLIC_EPV, IOR.SET_PUBLIC);
        fixContractEPVs((Class)ext, IOR.BASE_EPV, IOR.SET_PUBLIC);
        d_writer.backTab();
        d_writer.println("}");
      } else if (genContractChecks) {
        d_writer.println("if (enable) {");
        d_writer.tab();
        fixContractEPVs((Class)ext, IOR.PUBLIC_EPV, IOR.SET_CONTRACTS);
        d_writer.backTab();
        d_writer.println("} else {");
        d_writer.tab();
        fixContractEPVs((Class)ext, IOR.PUBLIC_EPV, IOR.SET_PUBLIC);
        d_writer.backTab();
        d_writer.println("}");
      } else {
        comment("Nothing to do since contract enforcement not needed.");
      }
    }
                                                                                
    d_writer.backTab();
    d_writer.println("}");

    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  /**
   * Returns the name of the contract statistics dump method.
   */
  private String getDumpStatsMethodName(SymbolID id, boolean doStatic) {
    return getIORMethodName(id, IOR.getBuiltinName(IOR.DUMP_STATS, doStatic));
  }

  /**
   * Outputs the string on a line within a try-finally block.
   */
  private void printLineBreak(String str) {
    try {
      d_writer.pushLineBreak(false);
      d_writer.println(str);
    }
    finally {
      d_writer.popLineBreak();
    }
  }

  /**
   * Generate the specified version of the dump contract statistics and control
   * data function.
   */
  private void generateDumpStatsFunction(Extendable ext, boolean doStatic) 
    throws CodeGenerationException 
  {
    String desc = doStatic ? "static " : "";
    comment("DUMP: Dump " + desc 
           + "interface contract enforcement statistics.");

    SymbolID id          = ext.getSymbolID();
    String   name        = IOR.getSymbolName(id);

    d_writer.print("static void " + getDumpStatsMethodName(id, doStatic));
    d_writer.println("(");
    d_writer.tab();
    if (!doStatic) {
      d_writer.println(IOR.getObjectName(id) + "* " + s_self + ",");
    }
/*
 * TLD/ToDo:  Remove filename and prefix parameters since likely to suffer
 *            same problems within CCA framework as the set method's use
 *            of string.
 */
    d_writer.println("const char* filename,");
    d_writer.println("const char* prefix,");
    d_writer.println(IOR.getExceptionFundamentalType() + "*_ex)");
    d_writer.backTab();
    d_writer.println("{");
    d_writer.tab();

    d_writer.println("*_ex = NULL;");
    d_writer.println("{");
    d_writer.tab();

    if (IOR.generateContractChecks(ext, d_context)) {
      String base  = getBaseControlsNStats(doStatic, s_self) + ".";
      String mBase = "ms->";

      d_writer.println(IOR.getMethodControlsNStatsStruct(id) + " *ms;");
      d_writer.println();
      d_writer.println("int         i;");
      d_writer.println("sidl_bool   firstTime = FALSE;");
      d_writer.println("sidl_bool   reported  = FALSE;");
/*
 * TLD/ToDo:  Change to use the default filename.
 */
      d_writer.print("const char* fname     = (filename) ? filename : \"");
      d_writer.println(IOR.DEFAULT_STATS_FN + "\";");
      d_writer.println();
                                                                                
      String dumpPtr      = IOR.S_DUMP_FPTR;
/*
 * TLD/ToDo:  Change prefix to be name of this class...
 */
      String dumpDataCall = "sidl_Enforcer_dumpStatsData(" + dumpPtr;
      dumpDataCall       += ", prefix, FALSE);";

      d_writer.println("if (" + dumpPtr + " == " + C.NULL + ") {");
      d_writer.tab();
      d_writer.println("firstTime = TRUE;");
      d_writer.print("if ((" + dumpPtr + "=fopen(fname,\"w\")) == ");
      d_writer.println(C.NULL + ") {");
      d_writer.tab();

      d_writer.print("printf(\"Cannot open file %s to dump the " + desc);
      d_writer.print("interface contract enforcement statistics.\\n\", ");
      printLineBreak("fname);");

      d_writer.println("return;");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();

      d_writer.println("if (firstTime) {");
      d_writer.tab();
      d_writer.print("sidl_Enforcer_dumpStatsHeader(" + dumpPtr);
      d_writer.println(", FALSE);");

      d_writer.print("fprintf(" + dumpPtr + ", \"; Method; Checked; ");
      printLineBreak("Okay; Violated; MethExcepts\\n\\n\");");

      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();

      d_writer.println("for (i = " + IOR.getMethodIndex(id, "MIN") + ";");
      d_writer.print("     i<="  + IOR.getMethodIndex(id, "MAX") + "; i++)");
      d_writer.println(" {");
      d_writer.tab();
      d_writer.println("ms = &" + getMethodControlsNStats(base, "i") + ";");
      d_writer.print("if (  (");
      if (!doStatic) {
        d_writer.print("!");
      }
      d_writer.print(IOR.getMethodDescDataName(id) + "[i]." );
      d_writer.println(IOR.D_IS_STATIC + ") ");
      d_writer.println("   && (" + mBase + IOR.D_TRIES + " > 0) ) {");
      d_writer.tab();
      d_writer.println("reported = TRUE;");
      d_writer.println(dumpDataCall);
      d_writer.println("fprintf(" + dumpPtr + ", \"; %s; %d; %d; %d; %d\\n\",");
      d_writer.tab();
      d_writer.tab();
      d_writer.println(IOR.getMethodDescDataName(id) + "[i].name,");
      d_writer.println(mBase + IOR.D_TRIES + ",");
      d_writer.println(mBase + IOR.D_SUCCESSES + ",");
      d_writer.println(mBase + IOR.D_FAILURES + ",");
      d_writer.println(mBase + IOR.D_NONVIO_EXCEPTS + ");");
      d_writer.backTab();
      d_writer.backTab();
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();

      d_writer.println("if (reported) {");
      d_writer.tab();
      d_writer.println("fprintf(" + dumpPtr + ", \"\\n\");");
      d_writer.backTab();
      d_writer.println("} else {");
      d_writer.tab();
      d_writer.println(dumpDataCall);

      String noEnfMsg = "\"; No attempts to enforce contracts detected\\n\\n\"";
      printLineBreak("fprintf(" + dumpPtr + ", " + noEnfMsg + ");");

      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();

      d_writer.println("fflush(" + dumpPtr + ");");
      d_writer.println("return;");
    } else {
      comment("Nothing to do since contract checks not generated.");
    }

    d_writer.backTab();
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  /**
   * Generate any contract checks associated with each method of the class.
   */
  private void generateAllChecks(Class cls) throws CodeGenerationException {
    List     normal  = (List) cls.getMethods(true);
    Iterator i       = normal.iterator();
    Method   meth    = null;
    boolean  hasInvs = cls.hasInvClause(true);

    while (i.hasNext()) {
      meth = (Method) i.next();
      if (hasInvs || meth.hasPreClause() || meth.hasPostClause()) {
        generateMethodChecks(cls, meth);
      }
    }
  }

  /**
   * Returns the name of the contract checking method for the specified method.
   */
  private String getCheckMethodName(Extendable ext, Method meth) {
    String mPre = "check_" + IOR.getSymbolName(ext.getSymbolID()) + '_';
    return mPre + meth.getLongMethodName();
  }

  /**
   * Generate call to the base method.
   */
  private void generateBaseCall(String name, String var, List args, 
                                boolean isStatic, boolean doThrows, 
                                Type returnType, boolean deref_inout,
                                boolean checkPtr)
    throws CodeGenerationException 
  {
    String meth = var + "->" + IOR.getVectorEntry(name);
    if (checkPtr) {
      d_writer.println("if (" + meth + ") {");
      d_writer.tab();
    }
    if ((returnType != null) && (returnType.getType() != Type.VOID)) {
      d_writer.print(C.FUNCTION_RESULT + " = ");
    }
    d_writer.println("(" + meth + ")(");
    d_writer.tab();
    d_writer.tab();
    IOR.generateArguments(d_writer, d_context, s_self, args, isStatic, 
                          doThrows, returnType, false, false, false, 
                          deref_inout);
    d_writer.println(");");
    d_writer.backTab();
    d_writer.backTab();
    if (checkPtr) {
      d_writer.backTab();
      d_writer.println("} else {");
      d_writer.tab();
      d_writer.println("SIDL_THROW(*_ex, sidl_SIDLException, ");
      d_writer.printlnUnformatted("           \"Null function pointer "
        + "detected for " + name + ".\");");
      d_writer.backTab();
      d_writer.println("}"); 
    }
  }


  /**
   * Returns the enforcer check invocation.
   */
  private String getEnforcerCheckCall(boolean firstPerCall, String mTimeArg, 
                                      String cTimeArg, String cClassArg, 
                                      String cComplexArg, boolean hasMethCall, 
                                      boolean hasResult)
  {
     String firstTime = firstPerCall ? "TRUE" : "FALSE";
     String methCall  = hasMethCall ? "TRUE" : "FALSE";
     String resOrOut  = hasResult ? "TRUE" : "FALSE";
     String argStr    = firstTime + ", ";
     argStr += cClassArg + ", " + cComplexArg + ", " + methCall + ", ";
     argStr += resOrOut  + ", " + mTimeArg    + ", " + cTimeArg;
     return "sidl_Enforcer_enforceClause(" + argStr + ")";
  }


  /**
   * Generates the macro invocation for using the timestamp data.
   */
				
  private void generateTrace(boolean atEnd, String mIndex, int tsNum, 
                             int numPre, int numPost, int numInv)
    throws CodeGenerationException
  {
    final String invComp  = "invd->" + IOR.D_INV_COMPLEXITY;
    final String invTime  = "invd->" + IOR.D_EXEC_INV_TIME;
    final String preComp  = "md->"   + IOR.D_PRE_COMPLEXITY;
    final String postComp = "md->"   + IOR.D_POST_COMPLEXITY;
    final String methTime = "md->"   + IOR.D_EXEC_METH_TIME;
    final String preTime  = "md->"   + IOR.D_EXEC_PRE_TIME;
    final String postTime = "md->"   + IOR.D_EXEC_POST_TIME;

    final String noTimeSetStr  = " = 0.0;";
    final String noInvStr      = ", 0.0, 0.0";
    final String ts1ts0Str     = "SIDL_DIFF_MICROSECONDS(ts1, ts0)";
    final String ts1ts0SetStr  = " = " + ts1ts0Str + ";";
    final String ts2ts1Str     = "SIDL_DIFF_MICROSECONDS(ts2, ts1)";
    final String ts2ts1SetStr  = " = " + ts2ts1Str + ";";
    final String ts3ts2Str     = "SIDL_DIFF_MICROSECONDS(ts3, ts2)";
    final String ts3ts2SetStr  = " = " + ts3ts2Str + ";";
    final String ts4ts3Str     = "SIDL_DIFF_MICROSECONDS(ts4, ts3)";
    final String ts4ts3SetStr  = " = " + ts4ts3Str + ";";
    final String ts5ts4Str     = "SIDL_DIFF_MICROSECONDS(ts5, ts4)";
    final String ts5ts4SetStr  = " = " + ts5ts4Str + ";";

    final String noComp = "0";
    final String noTime = "0.0";

    String invCompStr  = noComp;
    String preCompStr  = noComp;
    String postCompStr = noComp;
    String methTimeStr = noTime;
    String preTimeStr  = noTime;
    String postTimeStr = noTime;

    String cNameVar = "cName";

    /*
     * Now generate trace call in the following format:
     *   TRACE(cName, md, mId, preComp, postComp, invComp, mTime, preTime, \
     *         postTime, Inv1Time, Inv2Time)
     */
    String inconsistentStr = "Inconsistency in trace generation.  ";
    String traceStr        = null;

    d_writer.println("if (!sidl_Enforcer_areTracing()) {");
    d_writer.tab();
    if (atEnd) {
      switch (tsNum) {
        case 2:  /* Method call only */
          if ( (numPre <= 0) && (numPost <= 0) && (numInv <= 0) ) {
            d_writer.println(preTime  + noTimeSetStr);
            d_writer.println(methTime + ts1ts0SetStr);
            d_writer.println(postTime + noTimeSetStr);
            methTimeStr = ts1ts0Str;
            traceStr    = noInvStr;
          } else {
            throw new CodeGenerationException(inconsistentStr
              + "Timestamp variable generation assumed only method call "
              + "but number of constraints do not match.");
          } 
          break;
        case 3:  /* Preconditions || Postconditions */
          if ( (numPre > 0) && (numPost <= 0) && (numInv <= 0) ) {
            d_writer.println(preTime  + ts1ts0SetStr);
            d_writer.println(methTime + ts2ts1SetStr);
            d_writer.println(postTime + noTimeSetStr);
            preTimeStr  = ts1ts0Str;
            methTimeStr = ts2ts1Str;
            traceStr    = noInvStr;
          } else if ( (numPre <= 0) && (numPost > 0) && (numInv <= 0) ) {
            d_writer.println(preTime  + noTimeSetStr);
            d_writer.println(methTime + ts1ts0SetStr);
            d_writer.println(postTime + ts2ts1SetStr);
            methTimeStr = ts1ts0Str;
            postTimeStr = ts2ts1Str;
            traceStr    = noInvStr;
          } else {
            throw new CodeGenerationException(inconsistentStr
              + "Timestamp variable generation assumed preconditions or "
              + "postconditions but number of constraints do not match.");
          }
          break;
        case 4:  /* Preconditions and Postconditions || Invariants */
          if ( (numPre > 0) && (numPost > 0) && (numInv <= 0) ) {
            d_writer.println(preTime  + ts1ts0SetStr);
            d_writer.println(methTime + ts2ts1SetStr);
            d_writer.println(postTime + ts3ts2SetStr);
            preTimeStr  = ts1ts0Str;
            methTimeStr = ts2ts1Str;
            postTimeStr = ts3ts2Str;
            traceStr    = noInvStr;
          } else if ( (numPre <= 0) && (numPost <= 0) && (numInv > 0) ) {
            d_writer.println(preTime  + noTimeSetStr);
            d_writer.println(methTime + ts2ts1SetStr);
            d_writer.println(postTime + noTimeSetStr);
            /* Invariant is the average of the two measures. */
            d_writer.print(invTime  + " = (" + ts1ts0Str + " + ");
            d_writer.tab();
            d_writer.println(ts3ts2Str + ") / 2.0;");
            d_writer.backTab();
            methTimeStr = ts2ts1Str;
            traceStr    = ", " + ts1ts0Str + ", " + ts3ts2Str;
          } else {
            throw new CodeGenerationException(inconsistentStr
              + "Timestamp variable generation assumed preconditions and "
              + "postconditions OR invariants but number of constraints do "
              + "not match.");
          }
          break;
        case 5:  /* Pre and Invariants || Post and Invariants */
          if ( (numPre > 0) && (numPost <= 0) && (numInv > 0) ) {
            d_writer.println(preTime  + ts1ts0SetStr);
            d_writer.println(methTime + ts3ts2SetStr);
            d_writer.println(postTime + noTimeSetStr);
            /* Invariant is the average of the two measures. */
            d_writer.print(invTime  + " = (" + ts2ts1Str + " + ");
            d_writer.tab();
            d_writer.println(ts4ts3Str + ") / 2.0;");
            d_writer.backTab();
            preTimeStr  = ts1ts0Str;
            methTimeStr = ts3ts2Str;
            traceStr    = ", " + ts2ts1Str + ", " + ts4ts3Str;
          } else if ( (numPre <= 0) && (numPost > 0) && (numInv > 0) ) {
            d_writer.println(preTime  + noTimeSetStr);
            d_writer.println(methTime + ts2ts1SetStr);
            d_writer.println(postTime + ts3ts2SetStr);
            /* Invariant is the average of the two measures. */
            d_writer.print(invTime  + " = (" + ts1ts0Str + " + ");
            d_writer.tab();
            d_writer.println(ts4ts3Str + ") / 2.0;");
            d_writer.backTab();
            methTimeStr = ts2ts1Str;
            postTimeStr = ts3ts2Str;
            traceStr    = ", " + ts1ts0Str + ", " + ts4ts3Str;
          } else {
            throw new CodeGenerationException(inconsistentStr
              + "Timestamp variable generation assumed preconditions and "
              + "invariants OR postconditions and invariants but number "
              + "of constraints do not match.");
          }
          break;
        case 6:  /* All times */
          if ( (numPre > 0) && (numPost > 0) && (numInv > 0) ) {
            d_writer.println(preTime  + ts1ts0SetStr);
            d_writer.println(methTime + ts3ts2SetStr);
            d_writer.println(postTime + ts4ts3SetStr);
            /* Invariant is the average of the two measures. */
            d_writer.print(invTime  + " = (" + ts2ts1Str + " + ");
            d_writer.tab();
            d_writer.println(ts5ts4Str + ") / 2.0;");
            d_writer.backTab();
            preTimeStr  = ts1ts0Str;
            methTimeStr = ts3ts2Str;
            postTimeStr = ts4ts3Str;
            traceStr    = ", " + ts2ts1Str + ", " + ts5ts4Str;
          } else {
            throw new CodeGenerationException(inconsistentStr
              + "Timestamp variable generation assumed complete timing "
              + "instrumentation but number of constraints do not match.");
          }
          break;
        default:
          throw new CodeGenerationException(inconsistentStr
            + "Unrecognized number of timestamp variables generated but "
            + "corresponding trace generation skipped.");
      }
    } else {
      /*
       * Assumes tsn was not incremented in this case...since this is
       * an exceptional condition.
       */
      switch (tsNum) {
        case 1:  /* Method call only */
          if ( (numPre <= 0) && (numInv <= 0) ) {
            d_writer.println(preTime  + noTimeSetStr);
            d_writer.println(methTime + ts1ts0SetStr);
            d_writer.println(postTime + noTimeSetStr);
            methTimeStr = ts1ts0Str;
            traceStr    = noInvStr;
          } else {
            throw new CodeGenerationException(inconsistentStr
              + "Timestamp variable generation assumed only method call "
              + "but number of constraints do not match.");
          }
          break;
        case 2:  /* Preconditions || Invariants */
          if ( (numPre > 0) && (numInv <= 0) ) {
            d_writer.println(preTime  + ts1ts0SetStr);
            d_writer.println(methTime + ts2ts1SetStr);
            d_writer.println(postTime + noTimeSetStr);
            preTimeStr  = ts1ts0Str;
            methTimeStr = ts2ts1Str;
            traceStr    = noInvStr;
          } else if ( (numPre <= 0) && (numInv > 0) ) {
            d_writer.println(preTime  + noTimeSetStr);
            d_writer.println(methTime + ts2ts1SetStr);
            d_writer.println(postTime + noTimeSetStr);
            /* Invariant can only be the first measure in this case. */
            d_writer.println(invTime + ts1ts0SetStr);
            methTimeStr = ts2ts1Str;
            traceStr    = ", " + ts1ts0Str + ", " + noTime;
          } else {
            throw new CodeGenerationException(inconsistentStr
              + "Timestamp variable generation assumed preconditions or "
              + "postconditions but number of constraints do not match.");
          }
          break;
        case 3:  /* Preconditions and Invariants */
          if ( (numPre > 0) && (numInv > 0) ) {
            d_writer.println(preTime  + ts1ts0SetStr);
            d_writer.println(methTime + ts3ts2SetStr);
            d_writer.println(postTime + noTimeSetStr);
            /* Invariant can only be the first measure in this case. */
            d_writer.println(invTime + ts2ts1SetStr);
            preTimeStr  = ts1ts0Str;
            methTimeStr = ts3ts2Str;
            traceStr    = ", " + ts2ts1Str + ", " + noTime;
          } else {
            throw new CodeGenerationException(inconsistentStr
              + "Timestamp variable generation assumed preconditions or "
              + "postconditions but number of constraints do not match.");
          }
          break;
        default:
          throw new CodeGenerationException(inconsistentStr
            + "Unrecognized number of timestamp variables generated but "
            + "corresponding trace generation skipped.");
      }
    }
    d_writer.backTab();

    if (traceStr != null) {
      d_writer.println("} else {");
      d_writer.tab();
      d_writer.print("TRACE(" + cNameVar + ", md, " + mIndex);
      d_writer.print(", " + preCompStr + ", " + postCompStr);
      d_writer.print(", " + invCompStr + ", " + methTimeStr);
      d_writer.print(", " + preTimeStr + ", " + postTimeStr);
      d_writer.println(traceStr + ");");
      d_writer.backTab();
      d_writer.println("}"); 
    } else {
      d_writer.println("}");
    }
  }


  /**
   * Returns a timeofday() call for tracing on the specified trace
   * variable.
   */
  private String getTraceTimestampCall(int tsn) {
    return "gettimeofday(&ts" + tsn + ", NULL);";
  }

  /**
   * Generate the actual precondition clause checks with optional timestamp.
   */ 
  private void generatePreChecks(Method m, List preClause, int tsn, 
                                 String methAvg, String cAvg, String cComp, 
                                 String var, String methBase,
                                 boolean isStatic, String excvar)
    throws CodeGenerationException
  {
    String enfCall;

    d_writer.println(methAvg + " = md->" + IOR.D_EXEC_METH_TIME + ";");
    d_writer.println(cAvg + "    = md->" + IOR.D_EXEC_PRE_TIME + ";");
    d_writer.println(cComp + "   = md->" + IOR.D_PRE_COMPLEXITY + ";");
    enfCall = getEnforcerCheckCall(true, methAvg, cAvg, 
                                   "sidl_ClauseType_PRECONDITION", 
                                   cComp, m.preHasMethodCall(), false);

    d_writer.printlnUnformatted("#ifdef " + s_contracts_debug);
    d_writer.println("printf(\"...Precondition: enforceClause=%d\\n\", ");
    d_writer.tab();
    d_writer.println(enfCall + ");");
    d_writer.backTab();
    d_writer.printlnUnformatted("#endif /* " + s_contracts_debug + " */");

    d_writer.println("if (" + enfCall + ") {");
    d_writer.tab();
    generateContractChecks(m.getShortMethodName(), preClause, var, 
                           methBase, isStatic, excvar);
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();

    if (tsn >= 0) {
      d_writer.println(getTraceTimestampCall(tsn));
    }
    d_writer.println();
  }


  /**
   * Generate the actual invariant clause checks with optional timestamp.
   */ 
  private void generateInvChecks(Method m, List invClause, boolean invHasMC,
                                 boolean firstTime, int tsn, String methAvg, 
                                 String cAvg, String cComp, String var, 
                                 String methBase, boolean isStatic, 
                                 String excvar)
    throws CodeGenerationException
  {
    String enfCall;

    if (firstTime) {
      d_writer.println(methAvg + " = md->" + IOR.D_EXEC_METH_TIME + ";");
    }
    d_writer.print(cAvg + "    = invd->" + IOR.D_EXEC_INV_TIME);
    d_writer.println(";");
    d_writer.print(cComp + "   = invd->" + IOR.D_INV_COMPLEXITY);
    d_writer.println(";");

    enfCall = getEnforcerCheckCall(firstTime, methAvg, cAvg, 
                                   "sidl_ClauseType_INVARIANT", 
                                   cComp, invHasMC, false);

    d_writer.printlnUnformatted("#ifdef " + s_contracts_debug);
    d_writer.println("printf(\"...Invariant: enforceClause=%d\\n\", ");
    d_writer.tab();
    d_writer.println(enfCall + ");");
    d_writer.backTab();
    d_writer.printlnUnformatted("#endif /* " + s_contracts_debug + " */");

    d_writer.println("if (" + enfCall + ") {");
    d_writer.tab();
    generateContractChecks(m.getShortMethodName(), invClause, var, 
                           methBase, isStatic, excvar);
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();

    if (tsn >= 0) {
      d_writer.println(getTraceTimestampCall(tsn));
    }
    d_writer.println();
  }


  /**
   * Generate the actual postcondition clause checks with optional timestamp.
   */ 
  private void generatePostChecks(Method m, List postClause, boolean firstTime,
                                  int tsn, String methAvg, String cAvg, 
                                  String cComp, String var, String methBase, 
                                  boolean isStatic, String excvar)
    throws CodeGenerationException
  {
    String enfCall;

    /*
     * ToDo...Really need to properly handle inherited postconditions
     * (i.e., used to weaken the contract NOT strengthen)!
     */
    if (firstTime) { 
      d_writer.println(methAvg + " = md->" + IOR.D_EXEC_METH_TIME + ";");
    }
    d_writer.println(cAvg + "    = md->" + IOR.D_EXEC_POST_TIME + ";");
    d_writer.println(cComp + "   = md->" + IOR.D_POST_COMPLEXITY + ";");
    enfCall = getEnforcerCheckCall(firstTime, methAvg, cAvg,
                                   "sidl_ClauseType_POSTCONDITION", 
                                   cComp, m.postHasMethodCall(), 
                                   m.postHasResultOrOutArg());

    d_writer.printlnUnformatted("#ifdef " + s_contracts_debug);
    d_writer.println("printf(\"...Postcondition: enforceClause=%d\\n\", ");
    d_writer.tab();
    d_writer.println(enfCall + ");");
    d_writer.backTab();
    d_writer.printlnUnformatted("#endif /* " + s_contracts_debug + " */");

    if (!firstTime) {
      d_writer.println("if (   (" + s_contracts_okay + ")");
      d_writer.println("   && " + enfCall + " ) {");
    } else {
      d_writer.println("if (" + enfCall + ") {");
    }
    d_writer.tab();
    generateContractChecks(m.getShortMethodName(), postClause, var, 
                           methBase, isStatic, excvar);
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();

    if (tsn >= 0) {
      d_writer.println(getTraceTimestampCall(tsn));
    }
    d_writer.println();
  }


  /**
   * Generate all contract checks.
   */ 
  private void generateChecks(Class cls, Method m, String mIndex, boolean doTS, 
                              String methAvg, String cAvg, String cComp, 
                              String var, String methBase, String excvar)
    throws CodeGenerationException
  {
    String    name        = m.getLongMethodName();
    List      args        = m.getArgumentList();
    boolean   hasReturn   = false;
    boolean   hasThrows   = !m.getThrows().isEmpty();
    boolean   isStatic    = m.isStatic();
    Type      type        = m.getReturnType();
    List      preClause   = m.getPreClause();
    List      postClause  = m.getPostClause();

    List      invClause   = cls.getAllInvAssertions();
    boolean   invHasMC    = cls.invHasMethodCall();
    boolean   invCallsM   = cls.invHasMethodCall(m.getShortMethodName());

    /*
     * Do NOT generate invariant checks if they include a call to the 
     * current method.  We do NOT concern ourselves with whether the
     * pre- or post-conditions include the method because the validations
     * should have precluded that possibility.
     *
     * TBD:  What SHOULD if the invariant contains more than one assertion
     *   where one is the current method call?
     *
     * Also TBD, what if any use cases justify checking invariants on
     * "is pure" methods?
     */
    int       numInv      = (!invCallsM) ? invClause.size() : 0;

    int       numPre      = preClause.size();
    int       numPure     = (m.hasPureAssertion()) ? 1 : 0;
    int       numPost     = postClause.size() - numPure;

    boolean   firstTime   = true;
    int       tsn         = doTS ? 0 : -1;

    if ( (type != null) && (type.getType() != Type.VOID) ) {
       hasReturn = true;
    }

    d_writer.tab(); 

    if (doTS) {
      d_writer.println(getTraceTimestampCall(tsn));
      tsn++;
      d_writer.println();
    }

    if (numPre > 0) {
      generatePreChecks(m, preClause, tsn, methAvg, cAvg, cComp, 
                        var, methBase, isStatic, excvar);
      firstTime = false;
      if (doTS) tsn++;
    }

    if (numInv > 0) {
      generateInvChecks(m, invClause, invHasMC, firstTime, tsn, methAvg, cAvg,
                        cComp, var, methBase, isStatic, excvar);
      firstTime = false;
      if (doTS) tsn++;
    }

    if (!firstTime) {
      d_writer.printlnUnformatted("#ifdef " + s_vio_dispatch);
      d_writer.println("if (" + s_contracts_okay + ") {");
      d_writer.printlnUnformatted("#endif /* " + s_vio_dispatch + " */");
      d_writer.tab();
    }

    generateBaseCall(name, var, args, isStatic, hasThrows, type, false, false);
    d_writer.println();

    if (hasThrows) {
      d_writer.println("if ((*" + s_exception_var + ") != " + C.NULL + ") {");
      d_writer.tab();
      d_writer.println("(" + methBase + IOR.D_NONVIO_EXCEPTS + ") += 1;");

      if (doTS) {
        d_writer.println(getTraceTimestampCall(tsn));
        generateTrace(false, mIndex, tsn, numPre, numPost, numInv);
        d_writer.println();
      }

      d_writer.printlnUnformatted("#ifdef " + s_contracts_debug);
      d_writer.println("printf(\"...Exiting due to base call exception\\n\");");
      d_writer.printlnUnformatted("#endif /* " + s_contracts_debug + " */");

      d_writer.println();
      d_writer.print("return");
      if (hasReturn) {
        d_writer.print(" " + C.FUNCTION_RESULT);
      }
      d_writer.println(";");

      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();
    }

    if (!firstTime) {
      d_writer.backTab();
      d_writer.printlnUnformatted("#ifdef " + s_vio_dispatch);
      d_writer.println("}");
      d_writer.printlnUnformatted("#endif /* " + s_vio_dispatch + " */");
      d_writer.println();
    }

    if (doTS) {
      d_writer.println(getTraceTimestampCall(tsn));
      tsn++;
    }
    d_writer.println();

    if (numPost > 0) {
      generatePostChecks(m, postClause, firstTime, tsn, methAvg, cAvg,
                         cComp, var, methBase, isStatic, excvar);
      firstTime = false;
      if (doTS) tsn++;
    }
      
    if (numInv > 0) {
      generateInvChecks(m, invClause, invHasMC, firstTime, tsn, methAvg, cAvg,
                        cComp, var, methBase, isStatic, excvar);
      if (doTS) tsn++;
    }

    if (doTS) {
      generateTrace(true, mIndex, tsn, numPre, numPost, numInv);
      d_writer.println("RESETCD(md)");
    } else {
      d_writer.println("(md->" + IOR.D_EST_INTERVAL + ") -= 1;");
    }

    d_writer.backTab();
  }


  /**
   * Generate the checks associated with the invariant, precondition, and
   * postcondition clauses, if any, for the given method.  Assumes being 
   * called only if contract check generation is enabled.
   *
   * @param cls         The class associated with the method.
   * @param m           Method to be checked.
   */ 
  private void generateMethodChecks(Class cls, Method m)
    throws CodeGenerationException
  {
    List      invClause   = cls.getAllInvAssertions();
    SymbolID  id          = cls.getSymbolID();
    String    name        = m.getLongMethodName();
    List      preClause   = m.getPreClause();
    List      postClause  = m.getPostClause();
    Assertion as          = null;
    boolean   invCallsM   = cls.invHasMethodCall(m.getShortMethodName());

    /*
     * Do NOT generate invariant checks if they include a call to the 
     * current method.  We do NOT concern ourselves with whether the
     * pre- or post-conditions include the method because the validations
     * should have precluded that possibility.
     *
     * TBD:  What SHOULD if the invariant contains more than one assertion
     *   where one is the current method call?
     *
     * Also TBD, what if any use cases justify checking invariants on
     * "is pure" methods?
     */
    int       numInv      = (!invCallsM) ? invClause.size() : 0;
    int       numPre      = preClause.size();
    int       numPure     = (m.hasPureAssertion()) ? 1 : 0;
    int       numPost     = postClause.size() - numPure;
    int       total       = (2 * numInv) + numPre + numPost;
    int       tsn         = 0;

    comment("Check relevant contracts, if any, before and after the method "
      + "call.");

    String methname = getCheckMethodName(cls, m);
    Type    type      = m.getReturnType();
    boolean hasReturn = false;
    if ( (type != null) && (type.getType() != Type.VOID) ) {
       hasReturn = true;
    }
    d_writer.println("static " + getReturnString(type));
    d_writer.println(methname + '(');
    d_writer.tab();
    List    args      = m.getArgumentList();
    boolean hasThrows = !m.getThrows().isEmpty();
    boolean isStatic  = m.isStatic();
    IOR.generateArguments(d_writer, d_context, 
                          IOR.getObjectName(id) + "* " + s_self, args, 
                          isStatic, hasThrows, type, true, true, false, 
                          false);
    d_writer.println(")");
    d_writer.backTab();
    d_writer.println("{");
    d_writer.tab();

    if (hasReturn) {
      d_writer.print(getReturnString(type) + " " + C.FUNCTION_RESULT);
      d_writer.println(Utilities.getTypeInitialization(type, d_context) + ";");
    }

    /*
     * WARNING/ToDo...Must figure out how to ensure the exception type is 
     * declared should the class never throw an exception AND the base 
     * exception type ever stop being the base interface!
     */
    if ( (!hasThrows) && (m.contractHasUserDefinedMethod(false)) ) {
       d_writer.print(IOR.getExceptionFundamentalType() + "* ");
       d_writer.println(s_exception_var + ";");
    }
    String  var;
    boolean addBlank       = false;
    boolean genHookMethods = IOR.generateHookMethods(cls, d_context);
    if (isStatic) {
       var = "sepv";
       if (genHookMethods) {
         d_writer.print("int _type = ");
         d_writer.print(getBaseControlsNStats(isStatic, "") + ".");
         d_writer.print(IOR.D_HOOKS + " ? ");
         d_writer.print(IOR.getStaticTypeOption(id, IOR.SET_HOOKS) + " : ");
         d_writer.println(IOR.getStaticTypeOption(id, IOR.SET_PUBLIC) + ";");
         d_writer.print(IOR.getSEPVName(id) + "* " + var + " = ");
         d_writer.println(IOR.getLocalStaticsName(id) + "(_type);");
       } else {
         d_writer.print(IOR.getSEPVName(id) + "* " + var + " = ");
         d_writer.print(IOR.getLocalStaticsName(id) + "(");
         d_writer.println(IOR.getStaticTypeOption(id, IOR.SET_PUBLIC) + ");");
       }
       addBlank = true;
    } else {
       if (genHookMethods) {
         var = s_self + "->" + IOR.getEPVVar(IOR.BASE_EPV);
       } else {
         var = "epv";
         d_writer.print(IOR.getEPVName(id) + "* " + var + " = &");
         d_writer.print(IOR.getStaticEPVVariable(id, IOR.EPV_MINE, 
                                                   IOR.SET_PUBLIC));
         d_writer.println(";");
         addBlank = true;
       }
    }
    if (addBlank) {
      d_writer.println();
    }
    if (total > 0) {
      String statsBase = getBaseControlsNStats(isStatic, s_self) + ".";
      String methIndex = IOR.getMethodIndex(id, m);
      String excvar    = hasThrows ? s_exception_var : "";
      String tuExcept  = "tae";
      String methBase  = "ms->";
      String enfCall   = null;

      String methAvg   = "methAvg";
      String cAvg      = "cAvg";
      String cComp     = "cComp";      
      String mName     = m.getShortMethodName();

      boolean invHasMethCall = false;

      d_writer.println("int     " + s_contracts_okay + "    = 1;");
      d_writer.println();

      d_writer.println("double  " + methAvg + " = 0.0;");
      d_writer.println("double  " + cAvg + "    = 0.0;");
      d_writer.println("int     " + cComp + "   = 0;");
      d_writer.println();

      d_writer.print(IOR.getExceptionFundamentalType());
      d_writer.println(tuExcept + s_set_to_null);
      d_writer.println();

      /*
       * ToDo...Need to determine the number of each macro return type
       * actually needed.  (This would be the maximum number associated
       * with any given contract clause!)
       */
      int totalMacros = 0;
      int maxMacros = m.getMaxArrayIterMacros(
          MethodCall.MACRO_RETURN_TYPE[MethodCall.MACRO_RETURNS_BOOLEAN_IND]);
      if (maxMacros > 0) {
        d_writer.print("int     " + MethodCall.ARRAY_BOOLEAN_RESULT_VAR);
        d_writer.println("[" + maxMacros + "];");
        totalMacros += maxMacros;
      } 
      maxMacros = m.getMaxArrayIterMacros(
         MethodCall.MACRO_RETURN_TYPE[MethodCall.MACRO_RETURNS_DOUBLE_IND]);
      if (maxMacros > 0) {
        d_writer.print("double  " + MethodCall.ARRAY_DOUBLE_RESULT_VAR);
        d_writer.println("[" + maxMacros + "];");
        totalMacros += maxMacros;
      } 
/*
 * ToDo...Restore this if the wrong thing is done in MethodCall...which
 *        means it must be fixed as well.
 *
else {
        d_writer.println("double  " + MethodCall.ARRAY_DOUBLE_RESULT_VAR + ";");
      }
*/
      maxMacros = m.getMaxArrayIterMacros(
         MethodCall.MACRO_RETURN_TYPE[MethodCall.MACRO_RETURNS_INTEGER_IND]);
      if (maxMacros > 0) {
        d_writer.print("int32_t " + MethodCall.ARRAY_INTEGER_RESULT_VAR);
        d_writer.println("[" + maxMacros + "];");
        totalMacros += maxMacros;
      }
      if (totalMacros > 0) {
        d_writer.print("int32_t " + MethodCall.ARRAY_COUNT_VAR + ", ");
        d_writer.print(MethodCall.ARRAY_ITER_VAR + ", ");
        d_writer.println(MethodCall.ARRAY_SIZE_VAR + ";");
      }
      d_writer.print("char*   cName = \"");
      d_writer.println(IOR.getSymbolName(cls.getSymbolID()) + "\";");

      d_writer.println();
      if (numInv > 0) {
        generateContractViolation(Assertion.INVARIANT, CONTRACT_DECLARE, null, 
                                  mName, excvar);
      }
      if (numPre > 0) {
        generateContractViolation(Assertion.REQUIRE, CONTRACT_DECLARE, null, 
                                  mName, excvar);
      }
      if (numPost > 0) {
        generateContractViolation(Assertion.ENSURE, CONTRACT_DECLARE, null, 
                                  mName, excvar);
      }

      d_writer.print("struct timeval ts0, ts1");
      tsn=2;
      if (numPre > 0) {
        d_writer.print(", ts" + tsn++);
      }
      if (numPost > 0) {
        d_writer.print(", ts" + tsn++);
      }
      if (numInv > 0) {
        d_writer.print(", ts" + (tsn++) + ", ts" + (tsn++));
      }
      d_writer.println(";");
      tsn=0;
      d_writer.println();

      d_writer.println();
      d_writer.println(IOR.getMethodControlsNStatsStruct(id) + " *ms = "); 
      d_writer.tab();
      d_writer.print("&" + getMethodControlsNStats(statsBase, methIndex));
      d_writer.println(";");
      d_writer.backTab();

      if (numInv > 0) {
        d_writer.println(IOR.getInvDescDataStruct(id) + " *invd= "); 
        d_writer.tab();
        d_writer.println("&" + IOR.getInvDescDataName(id) + ";");
        d_writer.backTab();
      }

      if ((numPre + numPost + numInv) > 0) {
        d_writer.println(IOR.getMethodDescDataStruct(id) + " *md = "); 
        d_writer.tab();
        d_writer.print("&" + IOR.getMethodDescDataName(id) + "[");
        d_writer.println(methIndex + "];");
        d_writer.backTab();
      }

      if (hasThrows) {
        d_writer.println("(*" + s_exception_var + ") " + s_set_to_null);
      }
      d_writer.println();

      d_writer.printlnUnformatted("#ifdef " + s_contracts_debug);
      d_writer.println("printf(\"" + methname + ": Entered\\n\");");
      d_writer.printlnUnformatted("#endif /* " + s_contracts_debug + " */");

      /*
       * First generate the enforcement checks WITHOUT timing instrumentation.
       */
      d_writer.println("if (md->" + IOR.D_EST_INTERVAL + " > 1) {");

      generateChecks(cls, m, methIndex, false, methAvg, cAvg, cComp, var, 
                     methBase, excvar);
      d_writer.println("} else {");

      /*
       * Now generate the enforcement checks WITH timing instrumentation.
       */
      generateChecks(cls, m, methIndex, true, methAvg, cAvg, cComp, var, 
                     methBase, excvar);
      d_writer.println("}");
    } else {
      d_writer.printlnUnformatted("#ifdef " + s_contracts_debug);
      d_writer.println("printf(\"" + methname + ": Entered\\n\");");
      d_writer.printlnUnformatted("#endif /* " + s_contracts_debug + " */");

      generateBaseCall(name, var, args, isStatic, hasThrows, type, false, 
                       false);
    }

    d_writer.printlnUnformatted("#ifdef " + s_contracts_debug);
    d_writer.println("printf(\"" + methname + ": Exiting normally\\n\");");
    d_writer.printlnUnformatted("#endif /* " + s_contracts_debug + " */");

    d_writer.println();
    d_writer.print("return");
    if (hasReturn) {
      d_writer.print(" " + C.FUNCTION_RESULT);
    }
    d_writer.println(";");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }


  /**
   * Generate a return string for the specified SIDL type.  Most of the SIDL
   * return strings are listed in the static structures defined at the start 
   * of this class.  Symbol types and array types require special processing.
   */
  private String getReturnString(Type type)
    throws CodeGenerationException 
  {
    return IOR.getReturnString(type, d_context, true, false);
  }

  /**
   * Returns the next set of starting indices after those in the specified
   * contract's assertion.
   */
  private int[] getNextStartInd(Assertion as, int[] startInd) {
    int[] nextStartInd = new int[MethodCall.MAX_VALID_MACRO_RETURNS];
    for (int i = 0; i < MethodCall.MAX_VALID_MACRO_RETURNS; i++) {
      nextStartInd[i] = startInd[i]
        + as.getNumArrayIterMacrosByType(MethodCall.MACRO_RETURN_TYPE[i]);
    }
    return nextStartInd;
  }

  /**
   * Generate the contract checks associated with the contract clause.
   */
  private void generateContractChecks(String mName, List clause,
                                      String var, String mBase,
                                      boolean doStatic, String excvar)
  {
     if (!clause.isEmpty()) {
        boolean  first    = true;
        Iterator i        = clause.iterator();
        int[]    startInd = new int[MethodCall.MAX_VALID_MACRO_RETURNS];
        for (int j=0; j<MethodCall.MAX_VALID_MACRO_RETURNS; j++) {
           startInd[j] = 0;
        }

        d_writer.println("(" + mBase + IOR.D_TRIES + ") += 1;");
        while (i.hasNext()) {
           Assertion as = (Assertion)i.next();
           String expr = as.cExpression(var, startInd);
           if ( (expr != null) && (!expr.equals("")) ) {
             generateCheck(mName, as, var, startInd, doStatic, expr, 
                           excvar, first);
             first = false;
           }
/*
 * ToDo...Need to determine if it really is necessary to increment the
 * starting indices.
 *
           startInd = getNextStartInd(as, startInd);
 */
        }
        d_writer.println();
        d_writer.print("SIDL_INCR_IF_THEN(" + s_contracts_okay + ",");
        d_writer.print(mBase + IOR.D_SUCCESSES + "," + mBase);
        d_writer.println(IOR.D_FAILURES + ")");
     }
  }

  /**
   * Generate the contract check based on the specified assertion.
   */
  private void generateCheck(String mName, Assertion as, String var, 
                             int[] startInd, boolean doStatic, String expr,
                             String excvar, boolean first) 
  {
    List    mlist         = as.getArrayIterMacros(var, startInd);
    int     numIterMacros = (mlist != null) ? mlist.size() : 0;
    boolean extraIndent   = false;
    if (!first) {
      if (numIterMacros <= 0) {
        printLineBreak("else if (!" + expr + ") {");
      } else {
        d_writer.println("if ( " + s_contracts_okay + " ) {");
        extraIndent = true;
        d_writer.tab();
        generateArrayIterMacros(mlist);
        printLineBreak("if (!" + expr + ") {");
      }
    } else {
      if (numIterMacros > 0) {
        generateArrayIterMacros(mlist);
      }
      printLineBreak("if (!" + expr + ") {");
    }
    d_writer.tab();
    d_writer.println(s_contracts_okay + "  = 0;");
 
    if (!excvar.equals("")) {
      d_writer.println("if ((*" + excvar + ") == " + C.NULL + ") {");
      d_writer.tab();
      generateContractViolation(as.getType(), CONTRACT_RAISE, as, mName, 
                                excvar);
      d_writer.backTab();
      d_writer.println("}");
    }
    if (extraIndent) {
      d_writer.backTab();
      d_writer.println("}");
    }
    d_writer.backTab();
    d_writer.println("}");
  }

  /**
   * Generate the array iteration macro calls.
   */
  private void generateArrayIterMacros(List list) {
    Iterator i = list.iterator();
    try {
      d_writer.pushLineBreak(false);
      while (i.hasNext()) {
        String mac = (String) i.next();
        d_writer.println(mac);
      }
    }
    finally {
      d_writer.popLineBreak();
    }
  }

  /**
   * Generate the appropriate contract violation code based on genType as
   * follows: 
   *
   *   CONTRACT_DECLARE = Generate the contract violation variable declaration 
   *   CONTRACT_RAISE   = Generate the contract violation instantiation
   *
   * Note that as and excVar are only needed when CONTRACT_RAISE is specified.
   *
   * TODO:  On this initial pass, treating all flavors of 
   *        requires and all flavors of ensures the same. 
   *        However, they really should NOT be the same.
   */
  private void generateContractViolation(int clauseType, int genType,
                                         Assertion as, String mName, 
                                         String excvar) 
  {
    String prefix;
    String errStr;
    String errType;
    String tuExcept = "&tae";

    switch (clauseType) {
    case Assertion.REQUIRE:
    case Assertion.REQUIRE_ELSE:
      errStr  = "pre_err";
      errType = IOR.getPreconditionExceptType();
      prefix  = IOR.PRECONDITION_CALL_PREFIX;
      break;
    case Assertion.ENSURE:
    case Assertion.ENSURE_THEN:
      errStr  = "post_err";
      errType = IOR.getPostconditionExceptType();
      prefix  = IOR.POSTCONDITION_CALL_PREFIX;
      break;
    case Assertion.INVARIANT:
    default:
      errStr  = "inv_err";
      errType = IOR.getInvariantExceptType();
      prefix  = IOR.INVARIANT_CALL_PREFIX;
    }

    if (genType == CONTRACT_DECLARE) {
      d_writer.println(errType + errStr + ";");
    } else {
      d_writer.print(errStr + " = " + prefix);
      d_writer.println("__create(" + tuExcept + ");");

      d_writer.println(prefix + "_setNote(" + errStr + ",");
      d_writer.tab();
      printLineBreak("\"" + as.errorMessage(mName) + "\", ");
      d_writer.println(tuExcept + ");");
      d_writer.backTab();

      d_writer.print("(*" + excvar + ") = ");
      d_writer.print(IOR.FUND_EXCEPTION_CALL_PREFIX);
      d_writer.println("__cast(" + errStr + ", " + tuExcept + ");");
      d_writer.print(prefix + "_deleteRef(" + errStr);
      d_writer.println(", " + tuExcept + ");");
    }
  }


  /**
   * Return the specified built-in error check function name.
   */
  private String getBuiltinHooksName(SymbolID id, boolean doStatic) {
    return getIORMethodName(id, IOR.getBuiltinName(IOR.HOOKS, doStatic));
  }

  /**
   * Generate the specified function to set hooks activation.
   */
  private void generateHooksFunction(Class cls, boolean doStatic) 
    throws CodeGenerationException 
  {
    
    String desc = doStatic ? "static " : "";
    comment("HOOKS: Enable/disable " + desc + "hooks.");

    SymbolID id = cls.getSymbolID();
    d_writer.println("static void " + getBuiltinHooksName(id, doStatic) + '(');
    d_writer.tab();
    if (!doStatic) {
      d_writer.println(IOR.getObjectName(id) + "* " + s_self + ",");
    }
    d_writer.print("sidl_bool enable, ");
    d_writer.println(IOR.getExceptionFundamentalType() + "*_ex )");
    d_writer.backTab();
    d_writer.println("{");
    d_writer.tab();
    d_writer.println("*_ex " + s_set_to_null);

    if (IOR.generateHookMethods(cls, d_context)) {
      String base = getBaseControlsNStats(doStatic, s_self) + ".";
      if (!doStatic) {
        d_writer.println();
        d_writer.println("/* Ensure the EPVs are set properly. */");
/*
 * ToDo...Need to consider checking with Enforcer to set properly
 * if contracts are supported.
 */
        if (!IOR.generateContractChecks(cls, d_context)) {
          String publicepv = s_self + "->" + IOR.getEPVVar(IOR.PUBLIC_EPV);
          d_writer.println("if (enable) {"); 
          d_writer.tab();
          d_writer.print(publicepv + "  = &");
          d_writer.print(IOR.getStaticEPVVariable(id, IOR.EPV_MINE, 
                                                  IOR.SET_HOOKS));
          d_writer.println(";");
          d_writer.backTab();
          d_writer.println("} else {");
          d_writer.tab();
          d_writer.print(publicepv + "  = &");
          d_writer.print(IOR.getStaticEPVVariable(id, IOR.EPV_MINE, 
                                                  IOR.SET_PUBLIC));
          d_writer.println(";");
          d_writer.backTab();
          d_writer.println("}");
          d_writer.println("");
        }
      } else {  //setting static
        d_writer.println(base + IOR.D_HOOKS + " = enable;");
      }
    } else {
      comment("Nothing else to do since hook methods not generated.");
    }

    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  /**
   * Returns the name of the hooks method for the specified method.
   */
  private String getHooksMethodName(Extendable ext, Method meth) {
    return "hooks_" + IOR.getSymbolName(ext.getSymbolID()) + '_'
      + meth.getLongMethodName();
  }

  /**
   * Generate all hooks functions. These are the functions that invoke the
   * internal pre- and post-method calls before invoking the actual call. It is
   * assumed these methods are only generated when hooks generation is enabled.
   */
  private void generateAllHooks(Class cls) throws CodeGenerationException {
    List     normal = (List) cls.getMethods(false);
    Iterator i      = normal.iterator();
    Method   meth   = null;
    while (i.hasNext()) {
      meth = (Method) i.next();
      generateHooks(cls, meth);
    }
  }

  /**
   * Generate default exception assignment.
   */
  private void printSetDefaultExcept() {
    d_writer.print("*_ex " + s_set_to_null);
    d_writer.println(" /* default to no exception */");
  }

  /**
   * Generate the hooks method associated with the specified method.
   */
  private void generateHooks(Class cls, Method meth)
    throws CodeGenerationException 
  {
    SymbolID id        = cls.getSymbolID();
    String   name      = meth.getLongMethodName();
    String   methname  = getHooksMethodName(cls, meth);
    Type     type      = meth.getReturnType();
    String   retStr    = getReturnString(type);
    String   var;
    boolean  hasReturn = false;

    if ((type != null) && (type.getType() != Type.VOID)) {
      hasReturn = true;
    }
    comment("Sandwich the execution of the method between the pre- and post- "
            + "calls.");
    d_writer.println("static " + retStr + " " + methname + "(");
    d_writer.tab();

    List    args      = meth.getArgumentList();
    boolean hasThrows = !meth.getThrows().isEmpty();
    boolean isStatic  = meth.isStatic();
    IOR.generateArguments(d_writer, d_context,
                          IOR.getObjectName(id) + "* " + s_self, args,
                          isStatic, hasThrows, type, true, true, false, false);
    d_writer.println(")");
    d_writer.backTab();

    d_writer.println("{");
    d_writer.tab();

    boolean addBlank = false;
    if (hasReturn) {
      d_writer.print(retStr + " " + C.FUNCTION_RESULT + " = ");
      d_writer.println(IOR.getInitialValue(type) + ";");
      addBlank = true;
    }
    String preEPV  = null;
    String postEPV = null;
    if (isStatic) {
      var     = "sepv";
      preEPV  = "(&" + s_preSEPV + ")";
      postEPV = "(&" + s_postSEPV + ")";
      d_writer.print(IOR.getSEPVName(id) + "* " + var + " = ");
      d_writer.print(IOR.getLocalStaticsName(id) + "(");
      d_writer.println(IOR.getStaticTypeOption(id, IOR.SET_PUBLIC) + ");");
      addBlank = true;
    } else {
      if (IOR.generateContractChecks(cls, d_context)) {
        var     = s_self + "->" + IOR.getEPVVar(IOR.BASE_EPV);
      } else {
        var     = "epv";
        preEPV  = "(&" + s_preEPV + ")";
        postEPV = "(&" + s_postEPV + ")";
        d_writer.print(IOR.getEPVName(id) + "* " + var + " = &");
        d_writer.print(IOR.getStaticEPVVariable(id, IOR.EPV_MINE, 
                                                IOR.SET_PUBLIC));
        d_writer.println(";");
        addBlank = true;
      }
    }
    if (addBlank) {
      d_writer.println();
    }

    Method pre = meth.spawnPreHook(true);
    generateBaseCall(pre.getLongMethodName(), preEPV, pre.getArgumentList(), 
                     pre.isStatic(), !pre.getThrows().isEmpty(), 
                     pre.getReturnType(), true, true);
    d_writer.println(s_exc_check);

    generateBaseCall(name, var, args, isStatic, hasThrows, type, false, false);
    d_writer.println(s_exc_check);

    Method post = meth.spawnPostHook(true, true);
    generateBaseCall(post.getLongMethodName(), postEPV, post.getArgumentList(), 
                     post.isStatic(), !post.getThrows().isEmpty(), 
                     post.getReturnType(), true, true);
    d_writer.println(s_exc_check);

    d_writer.printUnformatted("EXIT:\n");

    d_writer.println();
    d_writer.print("return");
    if (hasReturn) {
      d_writer.print(" " + C.FUNCTION_RESULT);
    }
    d_writer.println(";");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  /**
   * Call the destructor for the object and then deallocate the associated
   * storage using <code>free</code>.
   */
  private void generateDeleteFunction(Class cls) {
    comment("DELETE: call destructor and free object memory.");

    SymbolID id   = cls.getSymbolID();
    String   name = IOR.getSymbolName(id);

    d_writer.println("static void ior_" + name + '_' + s_deleteBuiltin + '(');
    d_writer.tab();
    d_writer.print(IOR.getObjectName(id) + "* self, ");
    d_writer.println(IOR.getExceptionFundamentalType() + "*_ex)");
    d_writer.backTab();

    d_writer.println("{");
    d_writer.tab();

    printSetDefaultExcept();
    d_writer.println(IOR.getFiniName(id) + "(self, _ex);");
    d_writer.print("memset((void*)self, 0, sizeof(" + IOR.getObjectName(id));
    d_writer.println("));");
    d_writer.println("free((void*) self);");

    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  /**
   * @param cls
   */
  private void generateMainExec(Class cls) throws CodeGenerationException {
    SymbolID id            = cls.getSymbolID();
    String   my_symbolName = IOR.getSymbolName(id);
    d_writer.println("struct " + my_symbolName + "__method {");
    d_writer.tab();
    d_writer.println("const char *d_name;");
    d_writer.println("void (*d_func)(struct " + my_symbolName + "__object*,");
    d_writer.tab();
    d_writer.println("struct sidl_rmi_Call__object *,");
    d_writer.println("struct sidl_rmi_Return__object *,");
    d_writer.println(IOR.getExceptionFundamentalType() + "*);");
    d_writer.backTab();
    d_writer.backTab();
    d_writer.println("};");
    d_writer.println();
    d_writer.println("static void");
    d_writer.println("ior_" + my_symbolName + "__exec(");
    d_writer.tab();
    d_writer.println("struct " + my_symbolName + "__object* self,");
    d_writer.println("const char* methodName,");
    d_writer.println("struct sidl_rmi_Call__object* inArgs,");
    d_writer.println("struct sidl_rmi_Return__object* outArgs,");
    d_writer.println(IOR.getExceptionFundamentalType() + "*_ex )");
    d_writer.backTab();
    d_writer.println("{");
    d_writer.tab();

    List methods = new ArrayList(cls.getMethods(true));

    //Add Cast so we can use it to connect.
    methods.add(IOR.getBuiltinMethod(IOR.CAST, cls.getSymbolID(), d_context, 
                false));

    //Also, add the set_hooks builtin so it is accessible via RMI
    if(d_context.getConfig().generateHooks()) {
      methods.add(IOR.getBuiltinMethod(IOR.HOOKS, cls.getSymbolID(),
                                       d_context, false));
    }
    
    Collections.sort(methods, new IOR.CompareMethods());
    d_writer.print("static const struct " + my_symbolName);
    d_writer.println("__method  s_methods[] = {");
    d_writer.tab();
    for (Iterator i = methods.iterator(); i.hasNext();) {
      Method m = (Method) i.next();
      if (!m.isStatic()) {
        d_writer.print("{ \"" + m.getLongMethodName() + "\", ");
        d_writer.print(my_symbolName + '_' + m.getLongMethodName());
        d_writer.println("__exec }" + (i.hasNext() ? "," : ""));
      }
    }
    d_writer.backTab();
    d_writer.println("};");
    d_writer.println("int i, cmp, l = 0;");
    d_writer.print("int u = sizeof(s_methods)/sizeof(struct ");
    d_writer.println(my_symbolName + "__method);");
    printSetDefaultExcept();
    d_writer.println();
    d_writer.println("if (methodName) {");
    d_writer.tab();
    d_writer.writeCommentLine("Use binary search to locate method");
    d_writer.println("while (l < u) {");
    d_writer.tab();
    d_writer.println("i = (l + u) >> 1;");
    d_writer.println("if (!(cmp=strcmp(methodName, s_methods[i].d_name))) {");
    d_writer.tab();

    d_writer.print("(s_methods[i].d_func)(self, inArgs, outArgs, _ex); ");
    printLineBreak(s_exc_check);

    d_writer.println("return;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println("else if (cmp < 0) u = i;");
    d_writer.println("else l = i + 1;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("}");

    /* 
     * TODO:  Is a precondition clause violation the right exception 
     *        to be throwing here?
     */
    d_writer.writeCommentLine("TODO: add code for method not found");
    d_writer.pushLineBreak(false);
    d_writer.print("SIDL_THROW(*_ex, sidl_PreViolation, ");
    d_writer.println("\"method name not found\");");

    d_writer.println("EXIT:");
    d_writer.println("return;");
    d_writer.backTab();
    d_writer.println("}");
  }

  /**
   * @param cls
   */
  private void generateNonblockingMethods(Class cls) 
    throws CodeGenerationException 
  {
    for (Iterator i = cls.getMethods(true).iterator(); i.hasNext();) {
      Method m = (Method) i.next();
      if ( m.getCommunicationModifier() == Method.NONBLOCKING) { 
        Method send = m.spawnNonblockingSend();
        Method recv = m.spawnNonblockingRecv();
        generateNonblockingMethod(cls, send);
        generateNonblockingMethod(cls, recv);

      }
    }
  }

  /**
   * @param cls
   * @param m
   */
  private void generateNonblockingMethod(Class cls, Method m) 
    throws CodeGenerationException 
  {
    SymbolID id = cls.getSymbolID();

    d_writer.print(IOR.getReturnString(m.getReturnType(), d_context,
                                       true, false));
    d_writer.print(" " + IOR.getMethodName(id,m.getLongMethodName()));
    d_writer.println("(");
    String self = IOR.getObjectName(id) + "* self";
    List args = m.getArgumentList();
    Type retType = m.getReturnType();
    IOR.generateArguments(d_writer, d_context, self, args, m.isStatic(), 
                          true, retType, true, true, false, false);
    d_writer.println(") {");
    d_writer.tab();
    if (retType.getType() != Type.VOID) {
      d_writer.print(IOR.getReturnString(retType, d_context) + " _retval = ");
      d_writer.println(IOR.getInitialValue(retType)+";");
    }
    d_writer.print("sidl_BaseInterface _throwaway_exception ");
    d_writer.println(s_set_to_null);

    /*
     * TODO:  Should a precondition violation really be used to denote
     *        an attempt to call a non-blocking method?
     */
    d_writer.print("sidl_PreViolation pv = sidl_PreViolation__create");
    d_writer.println("(&_throwaway_exception);");
    d_writer.println("sidl_PreViolation_setNote(pv,");
    d_writer.tab();
    d_writer.print("\"Nonblocking methods on local objects not yet ");
    printLineBreak("supported\",");
    d_writer.println("&_throwaway_exception);");
    d_writer.backTab();
    d_writer.println("*_ex = (sidl_BaseInterface) pv;");

    if (retType.getType() != Type.VOID) {
      d_writer.println("return _retval;");
    } else {
      d_writer.println("return;");
    }

    d_writer.backTab();
    d_writer.println("}");
  }

  /**
   * @param cls
   */
  private void generateGetURLFunction(Extendable ext) {
    SymbolID id            = ext.getSymbolID();
    String   my_symbolName = IOR.getSymbolName(id);

    d_writer.println("static char*");
    d_writer.println("ior_" + IOR.getRemoteGetURLName(id) + "(");
    d_writer.tab();
    d_writer.println("struct " + my_symbolName + "__object* self,");
    d_writer.println(IOR.getExceptionFundamentalType() + "*_ex)");
    d_writer.backTab();
    d_writer.println("{");
    d_writer.tab();
    
    d_writer.println("char* ret " + s_set_to_null);

    d_writer.print("char* objid = sidl_rmi_InstanceRegistry_get");
    d_writer.print("InstanceByClass((sidl_BaseClass)self, _ex); ");
    printLineBreak(s_exc_check);

    d_writer.println("if (!objid) {");
    d_writer.tab();

    d_writer.print("objid = sidl_rmi_InstanceRegistry_register");
    d_writer.print("Instance((sidl_BaseClass)self, _ex); ");
    printLineBreak(s_exc_check);
    d_writer.backTab();
    d_writer.println("}");

    d_writer.printlnUnformatted("#ifdef WITH_RMI");
    d_writer.println();

    String getURL = "ret = sidl_rmi_ServerRegistry_getServerURL(objid, _ex); ";
    getURL       += s_exc_check;
    d_writer.println(getURL);
    d_writer.println();
    d_writer.printlnUnformatted("#else");
    d_writer.println();
    d_writer.println("ret = objid;");
    d_writer.println();
    d_writer.printlnUnformatted("#endif /*WITH_RMI*/");

    d_writer.println("return ret;");
    d_writer.println("EXIT:");

    d_writer.println("return " + C.NULL + ";");
    d_writer.backTab();
    d_writer.println("}");
  }

  /**
   * @param cls
   */
  private void generateIsLocalIsRemote(Extendable ext) {
    SymbolID id            = ext.getSymbolID();
    String   my_symbolName = IOR.getSymbolName(id);

    d_writer.println("static void");
    d_writer.println("ior_" + IOR.getRaddRefName(id) + "(");
    d_writer.print("    struct " + my_symbolName);
    d_writer.println("__object* self, sidl_BaseInterface* _ex) {");
    d_writer.tab();

    d_writer.print("sidl_BaseInterface_addRef((sidl_BaseInterface)self, ");
    d_writer.println("_ex);");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println("");

    d_writer.println("static sidl_bool");
    d_writer.println("ior_" + IOR.getRemoteIsRemoteName(id) + "(");
    d_writer.print("    struct " + my_symbolName);
    d_writer.println("__object* self, sidl_BaseInterface* _ex) {");
    d_writer.tab();

    printSetDefaultExcept();
    d_writer.println("return FALSE;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println("");
  }

  /**
   * Call the destructor for the object and then deallocate the associated
   * storage using <code>free</code>.
   */
  private void generateSuperFunction(Class cls) {
    comment(s_superBuiltin + ": returns parent's non-overrided EPV");

    SymbolID id     = cls.getSymbolID();
    Class    parent = cls.getParentClass();
    if (parent != null) {
      SymbolID pid = parent.getSymbolID();

      d_writer.print("static " + IOR.getEPVName(pid) + "* ");
      d_writer.print(IOR.getSymbolName(id) + s_superBuiltin);
      d_writer.println("(void) {");
      d_writer.tab();
      d_writer.print("return ");
      d_writer.print(IOR.getStaticEPVVariable(pid, IOR.EPV_PARENT, 
                                              IOR.SET_PUBLIC));
      d_writer.println(";");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();
    }
  }

  /**
   * Generate the function that initializes the method entry point vector for
   * this class. This class performs three functions. First, it saves the EPVs
   * from the parent classes and interfaces (assuming there is a parent).
   * Second, it fills the EPV for this class using information from the parent
   * (if there is a parent, and NULL otherwise). It then calls the skeleton
   * method initialization function for the EPV. Third, this function resets
   * interface EPV pointers and parent EPV pointers to use the correct function
   * pointers from the class EPV structure.
   */
  private void generateInitEPV(Class cls) throws CodeGenerationException {
    comment("EPV: create method entry point vector (EPV) structure.");

    /*
     * Declare the method signature and open the implementation body.
     */
    SymbolID id     = cls.getSymbolID();
    String   name   = IOR.getSymbolName(id);
    String   object = IOR.getObjectName(id);

    d_writer.println("static void " + name + "__init_epv(void)");

    d_writer.println("{");
    comment("assert( " + IOR.getHaveLockStaticGlobalsMacroName() + " );");
    d_writer.tab();

    /*
     * Output entry point vectors aliases for each parent class and interface as
     * well as a special one for the current object.
     */
    List parents = Utilities.sort(Utilities.getAllParents(cls));
    aliasEPVs(cls, parents);

    /*
     * Output pointers to the parent classes to simplify access to their data
     * structures.
     */
    if (cls.getParentClass() != null) {
      generateParentEPVs(cls, 0, 0);
      d_writer.println();
    }

    /*
     * Get the parent class EPVs so our versions start with theirs.
     */
    Class parent = cls.getParentClass();
    if (parent != null) {
      generateGetParentEPVs(parent);
      
      /*
       * Save all parent interface and class EPVs in static pointers.
       */
      d_writer.println();
      comment("Alias the static epvs to some handy small names.");
      saveEPVs(parent, 1);
      d_writer.println();
    }

    /*
     * Generate a list of the nonstatic methods in the class and get the width
     * of every method name.
     */
    List methods = (List) cls.getNonstaticMethods(true);
    int  mwidth  = Math.max(Utilities.getWidth(methods), s_longestBuiltin)
                   + IOR.getVectorEntry("").length();

    /*
     * Output builtin methods.
     */
    for (int j = 0; j < IOR.CLASS_BUILT_IN_METHODS; j++) {
      if (IOR.isBuiltinBasic(j) &&
          !(IOR.isRMIRelated(j) && d_context.getConfig().getSkipRMI())) {
        String mname = IOR.getBuiltinName(j);
        d_writer.print("epv->");
        d_writer.printAligned(IOR.getVectorEntry(mname), mwidth);
        if (  (j == IOR.CONSTRUCTOR) 
           || (j == IOR.DESTRUCTOR)
           || (j == IOR.CONSTRUCTOR2) ) 
        {
          d_writer.println(s_set_to_null);
        } else {
          d_writer.println(" = " + getIORMethodName(id, mname) + ';');
        }
      }
    }

    /*
     * Output the class methods.
     */
    generateNonstaticMethods(cls, object, methods, "epv", parent, mwidth);

    /*
     * Iterate through all parent EPVs and set each of the function pointers to
     * use the corresponding function in the parent EPV structure.
     */
    HashMap renames = new HashMap();
    IOR.resolveRenamedMethods(cls, renames);
    copyEPVs(parents, renames);
    d_writer.println("s_method_initialized = 1;");
    d_writer.println("ior_" + name + "__ensure_load_called();");

    /*
     * Close the function definition.
     */
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  /**
   * Generate the non-static methods for the specified class.
   */
  private void generateNonstaticMethods(Class cls, String object, List methods,
                                        String var, Class parent, int mwidth) 
    throws CodeGenerationException 
  {
    SymbolID id = cls.getSymbolID();

    /*
     * Iterate through all of the nonstatic methods. Assign them to NULL if the
     * parent class does not have the method and to the parent function pointer
     * if the parent has the method.
     */
    for (Iterator i = methods.iterator(); i.hasNext();) {
      Method  method    = (Method) i.next();
      String  mname     = method.getLongMethodName();
      boolean parentHas = (  (parent != null) 
                          && (parent.hasMethodByLongName(mname, true)));

      generateNonstaticEPV(method, object, var, parent, parentHas, mwidth);
      
      if (( method.getCommunicationModifier() == Method.NONBLOCKING) &&
          !d_context.getConfig().getSkipRMI()) { 
        Method send = method.spawnNonblockingSend();
        if ( send != null ) { 
          mname     = send.getLongMethodName();
          parentHas = (  (parent != null) 
                      && (parent.hasMethodByLongName(mname, true)));
          assignNonblockingPointer(cls, send, mwidth); 
        }
        Method recv = method.spawnNonblockingRecv();
        if ( recv != null ) { 
          mname     = recv.getLongMethodName();
          parentHas = (  (parent != null) 
                      && (parent.hasMethodByLongName(mname, true)));
          assignNonblockingPointer(cls, recv, mwidth); 
        }
      }
    }
    d_writer.println();

    /*
     * Call the user initialization function to set up the EPV structure.
     */
    d_writer.print(IOR.getSetEPVName(id) + "(" + var);
    if (IOR.generateHookEPVs(cls, d_context)) {
      d_writer.print(", &" + s_preEPV + ", &" + s_postEPV);
    }
    d_writer.println(");");

    /*
     * Now, if necessary, clone the static that has been set up and overlay with
     * local methods for contracts and/or hooks.
     */
    String xEPV;
    d_writer.println();
    boolean addBlank = false;
    if (IOR.generateContractChecks(cls, d_context)) {
      xEPV = "cepv";
      d_writer.print("memcpy((void*)" + xEPV + ", " + var + ", sizeof(");
      d_writer.println(IOR.getEPVName(id) + "));");
      generateEPVMethodAssignments(cls, IOR.SET_CONTRACTS, xEPV, false);
      addBlank = true;
    }

    if (IOR.generateHookMethods(cls, d_context)) {
      xEPV = "hepv";
      d_writer.print("memcpy((void*)" + xEPV + ", " + var + ", sizeof(");
      d_writer.println(IOR.getEPVName(id) + "));");
      if (addBlank) {
        d_writer.println();
        addBlank = false;
      }
      generateEPVMethodAssignments(cls, IOR.SET_HOOKS, xEPV, false);
      addBlank = true;
    }

    if (addBlank) {
      d_writer.println();
    }
  }

  /**
   * Generate a nonblocking single assignment in initialization of the EPV 
   * pointer.
   *
   * TODO: This kludge should probably be removed when we figure out how we
   * want to do nonblocking for local objects permanently.  This just
   * generates an exception.
   * 
   * @param name
   *          String name of the extendable
   * @param method
   *          The method in the EPV to initialzie
   * @param mwidth
   *          formatting parameter related to width of longest method name
   */
  private void assignNonblockingPointer(Class cls, Method method, int mwidth) {
    String mname = method.getLongMethodName();
    String ename = IOR.getVectorEntry(mname);
    d_writer.print("epv->");
    d_writer.printAligned(ename, mwidth);
    d_writer.print(" = ");
    d_writer.print(IOR.getMethodName(cls.getSymbolID(), 
                                     method.getLongMethodName()));
    d_writer.println(";");
  }

  /**
   * Generate the non-static method epv entry for the method.
   */
  private void generateNonstaticEPV(Method m, String object, String var,
                                    Class parent, boolean parentHas, int width)
    throws CodeGenerationException {
    String name = m.getLongMethodName();
    d_writer.print(var + "->");
    d_writer.printAligned(IOR.getVectorEntry(name), width);
    if (parentHas) {
      d_writer.print(" = ");
      d_writer.print(IOR.getCast(m, object + "*", d_context));
      d_writer.print(" s1->");
      d_writer.println(IOR.getVectorEntry(name) + ";");
    } else {
      d_writer.println(s_set_to_null);
    }

    if (d_context.getConfig().getFastCall() && !IOR.isBuiltinMethod(name)) {
      SymbolID pid = parent.getSymbolID();
      if (parentHas) {
        String guard = IOR.getNativeEPVGuard(parent);
        d_writer.printlnUnformatted("#ifdef " + guard);
        d_writer.print(var + "->");
        d_writer.printAligned(IOR.getNativeVectorEntry(name), width);
        d_writer.print(" = s1->");
        d_writer.println(IOR.getNativeVectorEntry(name) + ";");
        d_writer.printlnUnformatted("#else");
        d_writer.print(var + "->");
        d_writer.printAligned(IOR.getNativeVectorEntry(name), width);
        d_writer.println(" = NULL_NATIVE_EPV;");
        d_writer.printlnUnformatted("#endif /*" + guard + "*/");
      } else {
        d_writer.print(var + "->");
        d_writer.printAligned(IOR.getNativeVectorEntry(name), width);
        d_writer.println(" = NULL_NATIVE_EPV;");
      }
    }
  }

  /**
   * Generate the function that initializes the static entry point vector
   * (assuming the class contains static methods). If the parent class contains
   * static methods, then copy the pointers from the parent class, since static
   * methods are "inherited" from the parent (in a funny kinda way). Call the
   * skeleton-supplied function to initialize the remaining static methods.
   */
  private void generateInitSEPV(Class cls) throws CodeGenerationException {
    if (cls.hasStaticMethod(true)) {
      comment("SEPV: create the static entry point vector (SEPV).");

      /*
       * Define the method signature and begin the method body.
       */
      SymbolID id    = cls.getSymbolID();
      String   name  = IOR.getSymbolName(id);
      int      width = name.length();

      d_writer.println("static void " + name + "__init_sepv(void)");
      d_writer.println("{");
      d_writer.tab();
      comment("assert( " + IOR.getHaveLockStaticGlobalsMacroName() + " );");

      boolean  genHookEPVs = IOR.generateHookEPVs(cls, d_context);

      if (genHookEPVs) {
        d_writer.print(IOR.getExceptionFundamentalType());
        d_writer.println("throwaway_exception" + s_set_to_null);
      }

      /*
       * Get information about the parent (if it exists and has static methods).
       */
      Class    parent        = cls.getParentClass();
      SymbolID pid           = null;
      boolean  includeParent = (parent != null) && parent.hasStaticMethod(true);

      boolean  genContractChecks   = IOR.generateContractChecks(cls, d_context);
      boolean  genContractEPVs     = IOR.generateContractEPVs(cls, d_context);
      boolean  genHookMethods      = IOR.generateHookMethods(cls, d_context);

      if (includeParent) {
        pid = parent.getSymbolID();
        int w = IOR.getSymbolName(pid).length();
        if (w > width) {
          width = w;
        }
      }
      width += "struct __sepv*".length() + 1;

      /*
       * Create pointers to the local static EPV structure and one 
       * to the parent SEPV structure (if the parent exists and has 
       * static members).
       */
      d_writer.printAligned(IOR.getSEPVName(id) + "*", width);
      d_writer.print(" s = &");
      d_writer.print(IOR.getStaticEPVVariable(id, IOR.EPV_STATIC, 
                                              IOR.SET_PUBLIC));
      d_writer.println(";");
      if (genContractChecks) {
        d_writer.printAligned(IOR.getSEPVName(id) + "*", width);
        d_writer.print("cs = &");
        d_writer.print(IOR.getStaticEPVVariable(id, IOR.EPV_STATIC, 
                                                IOR.SET_CONTRACTS));
        d_writer.println(";");
      }
      if (genHookMethods) {
        d_writer.printAligned(IOR.getSEPVName(id) + "*", width);
        d_writer.print("hs = &");
        d_writer.print(IOR.getStaticEPVVariable(id, IOR.EPV_STATIC, 
                                                IOR.SET_HOOKS));
        d_writer.println(";");
      }
      if (includeParent) {
        d_writer.printAligned(IOR.getSEPVName(pid) + "*", width);
        d_writer.println(" p = " + IOR.getStaticsName(pid) + "();");
      }
      d_writer.println();

      String var = "s";

      /*
       * Output builtin methods.
       */
      for (int j = 0; j < IOR.CLASS_BUILT_IN_METHODS; j++) {
        if (IOR.hasStaticBuiltin(j)
            && !(IOR.isRMIRelated(j) && d_context.getConfig().getSkipRMI()))
        {
          String mname = IOR.getBuiltinName(j, true);
          d_writer.print(var + "->");
          d_writer.printAligned(IOR.getVectorEntry(mname), width);
          if (  (IOR.CONTRACTS  == j) 
             || (IOR.HOOKS      == j) 
             || (IOR.DUMP_STATS == j)  ) 
          {
            d_writer.println(" = " + getIORMethodName(id, mname) + ';');
          } else {
            d_writer.println(s_set_to_null);
          }
        }
      }

      /*
       * Output the static methods.
       */
      generateStaticMethods(cls, var, parent);

      /*
       * Initialize the remainder of the static functions by calling the
       * skeleton initialization routine.
       */
      d_writer.print(IOR.getSetSEPVName(id) + "(" + var);
      if (genHookEPVs) {
        d_writer.println(", &" + s_preSEPV + ", &" + s_postSEPV + ");");
      } else {
        d_writer.println(");");
      }
      d_writer.println();

      /*
       * If necessary, initialize the static contract and/or hooks 
       * functions as well.
       */
      String  sEPV     = "hs";
      boolean addBlank = false;

      if (genHookMethods) {
        d_writer.print("memcpy((void*)" + sEPV + ", " + var + ", sizeof(");
        d_writer.println(IOR.getSEPVName(id) + "));");
        d_writer.println();
        generateEPVMethodAssignments(cls, IOR.SET_HOOKS, sEPV, true);
        d_writer.println();
        d_writer.print(getBaseControlsNStats(true, "") + "." + IOR.D_HOOKS);
        d_writer.println(" = " + IOR.DEFAULT_OPTION_HOOKS + ";");
        addBlank = true;
      }

      /*
       * TODO:  Is this really needed if not generating hooks methods?
       */
      if (genHookEPVs) {
        d_writer.print(getBuiltinHooksName(id, true) + "(");
        if (genHookMethods) {
          d_writer.print("TRUE");
        } else {
          d_writer.print("FALSE");
        }
        d_writer.println(", &throwaway_exception);");
        d_writer.println();
      }

      if (genContractChecks) {
        if (addBlank) {
          d_writer.println();
        }
        sEPV = "cs";
        d_writer.print("memcpy((void*)" + sEPV + ", " + var + ", sizeof(");
        d_writer.println(IOR.getSEPVName(id) + "));");
        generateEPVMethodAssignments(cls, IOR.SET_CONTRACTS, sEPV, true);
        d_writer.println();
        addBlank = true;
      }
      if (addBlank) {
        d_writer.println();
      }
      d_writer.println("s_static_initialized = 1;");
      d_writer.println("ior_" + name + "__ensure_load_called();");

      /*
       * Close the initialization guard and end the function body.
       */
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();
    }
  }

  /**
   */
  private void generateEnsureLoad(Class cls) throws CodeGenerationException {
    SymbolID id   = cls.getSymbolID();
    String   name = IOR.getSymbolName(id);

    d_writer.print("static void ior_" + name);
    d_writer.println("__ensure_load_called(void) {");
    d_writer.tab();
    comment("assert( " + IOR.getHaveLockStaticGlobalsMacroName() + " );");
    d_writer.println("if (! s_load_called ) {");
    d_writer.tab();
    d_writer.println("s_load_called=1;");
    d_writer.println(name + "__call_load();");
    if (  IOR.generateContractChecks(cls, d_context) 
       && cls.hasStaticMethod(true) ) 
    {
      d_writer.println();
      d_writer.print(IOR.getExceptionFundamentalType());
      d_writer.println("tae;");
      d_writer.print(getSetChecksMethodName(id, true));
      d_writer.println("(sidl_Enforcer_areEnforcing(), ");
      d_writer.tab();
      d_writer.println(C.NULL + ", TRUE, &tae);");
      d_writer.backTab();
    }
    d_writer.backTab();
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("}");
  }

  /**
   * Generate the static methods for the specified class.
   */
  private void generateStaticMethods(Class cls, String var, Class parent)
    throws CodeGenerationException 
  {
    List methods = (List) cls.getStaticMethods(true);
    int  mwidth  = Utilities.getWidth(methods) 
                 + IOR.getVectorEntry("").length() + var.length() + 2;

    /*
     * Iterate through all of the static methods. Assign them to NULL if the
     * parent class does not have the method and to the parent function pointer
     * if the parent has the method.
     */
    for (Iterator i = methods.iterator(); i.hasNext();) {
      Method  method    = (Method) i.next();
      String  mname     = method.getLongMethodName();
      boolean parentHas = (  (parent != null) 
                          && (parent.hasMethodByLongName(mname, true)));
      String ename      = IOR.getVectorEntry(mname);
      generateStaticEPV(var, ename, parentHas, mwidth);
    }
    d_writer.println();
  }

  /**
   * Generate the static method epv entry for the method.
   */
  private void generateStaticEPV(String var, String ename, boolean parentHas,
                                 int mwidth) 
    throws CodeGenerationException 
  {
    d_writer.print(var + "->");
    d_writer.printAligned(ename, mwidth);
    if (parentHas) {
      d_writer.println(" = p->" + ename + ";");
    } else {
      d_writer.println(s_set_to_null);
    }
  }

  /**
   * Generate the appropriate contract or hooks methods assignments.
   */
  void generateEPVMethodAssignments(Extendable ext, int setType, String ptrVar,
                                    boolean doStatics) 
  {
    List     methods = (doStatics ? (List) ext.getStaticMethods(false) 
                                  : (List) ext.getNonstaticMethods(false));
    Iterator i       = methods.iterator();
    Method   m       = null;
    String   mName, tName;
    int      mwidth  = Math.max(Utilities.getWidth(methods), s_longestBuiltin)
                     + IOR.getVectorEntry("").length() + ptrVar.length() + 2;
    boolean  doChecks = (setType == IOR.SET_CONTRACTS);

    boolean  hasInvs = false;
    try {
       hasInvs = ext.hasInvClause(true);
    } catch (CodeGenerationException cge) {
      /* Just drop it! */
    }

    while (i.hasNext()) {
      m     = (Method) i.next();
      if ( (!doChecks) || (hasInvs || m.hasPreClause() || m.hasPostClause()) )
      {
        mName = m.getLongMethodName();
        d_writer.printAligned(ptrVar + "->" 
          + IOR.getVectorEntry(mName), mwidth);
        tName = doChecks ? getCheckMethodName(ext, m)
                         : getHooksMethodName(ext, m);
        d_writer.println(" = " + tName + ";");
      }
    }
  }

  /**
   * Generate the appropriate contract or hooks methods assignments.
   *
   * TBD:  Where did this come from?  Why is it passing (what was unused)
   *       p_ext argument?  Is this routine even used?  If so, who calls it?
   */
  void generateEPVMethodAssignments(Extendable p_ext, Extendable ext, 
                                    int setType, String ptrVar,
                                    boolean doStatics) 
    throws CodeGenerationException 
  {
    /* 
     * Replacing details with call to existing method since the routines
     * looked identical.
     */
    generateEPVMethodAssignments(ext, setType, ptrVar, doStatics);
  }


  /**
   * Generate a function that will return a pointer to the static entry point
   * vector. This function is called by clients to get access to static methods
   * in the class. If this class has no static methods, then do not generate an
   * implementation.
   */
  private void generateStaticFunction(Class cls, boolean doLocal) 
    throws CodeGenerationException 
  {
    SymbolID id  = cls.getSymbolID();

    boolean genContractChecks = IOR.generateContractChecks(cls, d_context);
    boolean genHookMethods    = IOR.generateHookMethods(cls, d_context);

    if (  cls.hasStaticMethod(true) 
       && ( (!doLocal) || (genContractChecks || genHookMethods) )  ) 
    {
      String   desc  = null;
      String   mName = null;
      String   args  = null;
      String   name  = IOR.getSymbolName(id);

      if (doLocal) {
        desc  = "specified ";
        args  = "(int type)";
        mName = IOR.getLocalStaticsName(id);
      } else {
        desc  = "";
        args  = "(void)";
        mName = IOR.getStaticsName(id);
      }

      comment(mName + ": return pointer to " + desc + "static EPV structure.");

      d_writer.println(IOR.getSEPVName(id) + "*");
      d_writer.println(mName + args + "{");
      d_writer.tab();

      if (genContractChecks || genHookMethods) {
        d_writer.println(IOR.getSEPVName(id) + "* sepv;");
      }

      d_writer.println(IOR.getLockStaticGlobalsMacroName() + ";");

      d_writer.println("if (!s_static_initialized) {");
      d_writer.tab();
      d_writer.println(name + "__init_sepv();");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println(IOR.getUnlockStaticGlobalsMacroName() + ";");

      /*
       * TODO:  Need to reassess the combination of contract checks
       * and hooks to make sure the EPV is set properly when both
       * are on.
       */
      boolean cont = false;
      if (genContractChecks) {
        d_writer.println();
        cont = true;
        if (doLocal) {
          d_writer.print("if (type == ");
          d_writer.print(IOR.getStaticTypeOption(id, IOR.SET_CONTRACTS));
          d_writer.println(") {");
        } else {
          String base = getBaseControlsNStats(true, s_self) + ".";
          d_writer.println("if (sidl_Enforcer_areEnforcing()) {");
          d_writer.tab();
          d_writer.println("if (!" + base + IOR.D_ENABLED + ") {");
          d_writer.tab();
          d_writer.print(IOR.getExceptionFundamentalType());
          d_writer.println("tae;");
          d_writer.print(getSetChecksMethodName(id, true));
          d_writer.println("(sidl_Enforcer_areEnforcing(),");
          d_writer.tab();
          d_writer.println(C.NULL + ", TRUE, &tae);");
          d_writer.backTab();
          d_writer.backTab();
          d_writer.println("}");
          d_writer.backTab();
        }
        d_writer.tab();
        d_writer.print("sepv = &");
        d_writer.print(IOR.getStaticEPVVariable(id, IOR.EPV_STATIC, 
                                                IOR.SET_CONTRACTS));
        d_writer.println(";");
        d_writer.backTab();
        d_writer.print("}");
      }

      if (genHookMethods) {
        if (cont) {
          d_writer.print(" else ");
        }
        if (doLocal) {
          d_writer.print("if (type == ");
          d_writer.println(IOR.getStaticTypeOption(id, IOR.SET_HOOKS) + ") {");
        } else {
          d_writer.print("if (" + getBaseControlsNStats(true, "") + ".");
          d_writer.println(IOR.D_HOOKS + ") {");
        }
        d_writer.tab();
        d_writer.print("sepv = &");
        d_writer.print(IOR.getStaticEPVVariable(id, IOR.EPV_STATIC, 
                                                IOR.SET_HOOKS));
        d_writer.println(";");
        d_writer.backTab();
        d_writer.print("}");
      }

      if (genContractChecks || genHookMethods) {
        d_writer.println(" else {");
        d_writer.tab();
        d_writer.print("sepv = &");
        d_writer.print(IOR.getStaticEPVVariable(id, IOR.EPV_STATIC, 
                                                IOR.SET_PUBLIC));
        d_writer.println(";");
        d_writer.backTab();
        d_writer.println("}");
        d_writer.println("return sepv;");
      } else {
        d_writer.print("return &");
        d_writer.print(IOR.getStaticEPVVariable(id, IOR.EPV_STATIC, 
                                                IOR.SET_PUBLIC));
        d_writer.println(";");
      }
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();
    }
  }

  private void generateInitClassInfo(Class cls) {
    if (!cls.isAbstract()) {
      if (d_classInfoI.equals(cls.getSymbolID())) {
        d_writer.println("static void");
        d_writer.println("cleanupClassInfo(void *ignored) {");
        d_writer.tab();
        d_writer.print(IOR.getExceptionFundamentalType());
        d_writer.println("throwaway_exception" + s_set_to_null);
        d_writer.println("if (s_classInfo) {");
        d_writer.tab();
        if (d_classInfoI.equals(cls.getSymbolID())) {
          d_writer.print(IOR.getObjectName(d_baseClass) + " *bc = ");
          d_writer.print(C.getFullMethodName(d_baseClass, "_cast"));
          d_writer.println("(s_classInfo, &throwaway_exception);");
          d_writer.print(C.getDataName(d_baseClass) + " *data = ");
          d_writer.println(C.getDataGetName(d_baseClass) + "(bc);" );
          d_writer.println("if (data && data->d_classinfo) {");
          d_writer.tab();
          d_writer.print(IOR.getObjectName(d_classInfo));
          d_writer.println("* ci = data->d_classinfo;");
          d_writer.println("data->d_classinfo" + s_set_to_null);
          d_writer.print(C.getFullMethodName(d_baseClass, "deleteRef"));
          d_writer.println("(bc, &throwaway_exception);");
          d_writer.print(C.getFullMethodName(d_classInfo, "deleteRef"));
          d_writer.println("(ci, &throwaway_exception);");
          d_writer.backTab();
          d_writer.println("}");
        }
        d_writer.print(C.getFullMethodName(d_classInfo, "deleteRef"));
        d_writer.println("(s_classInfo, &throwaway_exception);");
        d_writer.backTab();
        d_writer.println("}");
        d_writer.println("s_classInfo" + s_set_to_null);
        d_writer.println("s_classInfo_init = 1;");
        d_writer.backTab();
        d_writer.println("}");
        d_writer.println();
        d_writer.print("static void initMetadata(");
        d_writer.print(IOR.getObjectName(cls.getSymbolID()) + "*,");
        d_writer.println(IOR.getExceptionFundamentalType() + "*_ex);");
        d_writer.println();
      }
      comment("initClassInfo: create a ClassInfo interface if necessary.");
      d_writer.println("static void");
      d_writer.print("initClassInfo(" + C.getObjectName(d_classInfo));
      d_writer.print(" *info, " + IOR.getExceptionFundamentalType());
      d_writer.println("*_ex)");
      d_writer.println("{");
      d_writer.tab();
      if (!d_classInfoI.equals(cls.getSymbolID())) {
        d_writer.println(IOR.getLockStaticGlobalsMacroName() + ";");
      } else {
        d_writer.writeCommentLine("ClassInfo for ClassInfoI is a special case");
        d_writer.writeCommentLine(IOR.getUnlockStaticGlobalsMacroName() + ";");
      }
      printSetDefaultExcept();
      d_writer.println();
      if (!d_classInfoI.equals(cls.getSymbolID())) {
        d_writer.println("if (!s_classInfo) {");
      }
      else {
        d_writer.println("if (s_classInfo_init) {");
      }
      d_writer.tab();
      d_writer.println(C.getObjectName(d_classInfoI) + " impl;");
      if (d_classInfoI.equals(cls.getSymbolID())) {
        d_writer.println("s_classInfo_init = 0;");
      }
      d_writer.print("impl = ");
      d_writer.print(C.getFullMethodName(d_classInfoI, "_create"));
      d_writer.println("(_ex);");
      d_writer.print("s_classInfo = ");
      d_writer.print(C.getFullMethodName(d_classInfo, "_cast"));
      d_writer.println("(impl,_ex);");
      d_writer.println("if (impl) {");
      d_writer.tab();
      d_writer.print(C.getFullMethodName(d_classInfoI, "setName"));
      d_writer.println("(impl, \"" + cls.getFullName() + "\", _ex);");
      d_writer.print(C.getFullMethodName(d_classInfoI, "setVersion"));
      d_writer.print("(impl, \"");
      d_writer.print(cls.getSymbolID().getVersion().getVersionString());
      d_writer.println("\", _ex);");
      d_writer.print(C.getFullMethodName(d_classInfoI, "setIORVersion"));
      d_writer.println("(impl, s_IOR_MAJOR_VERSION,");
      d_writer.tab();
      d_writer.println("s_IOR_MINOR_VERSION, _ex);");
      d_writer.backTab();
      if (d_classInfoI.equals(cls.getSymbolID())) {
        d_writer.println("initMetadata(impl,_ex);");
      }
      d_writer.print(C.getFullMethodName(d_classInfoI, "deleteRef"));
      d_writer.println("(impl,_ex);");
      if (d_classInfoI.equals(cls.getSymbolID())) {
        d_writer.println("sidl_atexit(cleanupClassInfo, " + C.NULL + ");");
      }
      else {
        d_writer.println("sidl_atexit(sidl_deleteRef_atexit, &s_classInfo);");
      }
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("}");
      if (!d_classInfoI.equals(cls.getSymbolID())) {
        d_writer.println(IOR.getUnlockStaticGlobalsMacroName() + ";");
      } else {
        d_writer.writeCommentLine("ClassInfo for ClassInfoI is a special "
                                  + "case");
        d_writer.writeCommentLine(IOR.getUnlockStaticGlobalsMacroName() + ";");
      }
      d_writer.println("if (s_classInfo) {");
      d_writer.tab();
      d_writer.println("if (*info) {");
      d_writer.tab();
      d_writer.println(C.getFullMethodName(d_classInfo, "deleteRef")
                       + "(*info,_ex);");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println("*info = s_classInfo;");
      d_writer.print(C.getFullMethodName(d_classInfo, "addRef"));
      d_writer.println("(*info,_ex);");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();
    }
  }

  private void generateInitMetadata(Class cls) {
    if (!cls.isAbstract()) {
      SymbolID id = cls.getSymbolID();
      Class parent;
      String object = IOR.getObjectName(id);
      comment("initMetadata: store IOR version & class in sidl.BaseClass's "
              + "data");
      d_writer.println("static void");
      d_writer.print("initMetadata(" + object + "* " + s_self);
      d_writer.println(", sidl_BaseInterface* _ex)");
      d_writer.println("{");
      d_writer.tab();
      d_writer.println("*_ex = 0; /* default no exception */");
      d_writer.println("if (" + s_self + ") {");
      d_writer.tab();
      d_writer.print(C.getDataName(d_baseClass) + " *data = (");
      d_writer.print(C.getDataName(d_baseClass) + "*)((*");
      d_writer.print(s_self + ")");
      parent = cls.getParentClass();
      while (parent != null) {
        d_writer.print(".d_");
        d_writer.print(IOR.getSymbolName(parent.getSymbolID()).toLowerCase());
        parent = parent.getParentClass();
      }
      d_writer.println("." + IOR.D_DATA + ");");
      d_writer.println("if (data) {");
      d_writer.tab();
      d_writer.println("data->d_IOR_major_version = s_IOR_MAJOR_VERSION;");
      d_writer.println("data->d_IOR_minor_version = s_IOR_MINOR_VERSION;");
      d_writer.print("initClassInfo(&(data->d_classinfo),_ex); ");
      d_writer.println(s_exc_check);
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("EXIT:");
      d_writer.println("return;");
      d_writer.println("}");
      d_writer.println();
    }
  }

  /**
   * Allocate the memory for a new instance of the class and then call the
   * constructor (initializer) on the object. This method is not output for
   * abstract classes.
   */
  private void generateNewFunction(Class cls) throws CodeGenerationException 
  {
    if (!cls.isAbstract()) {
      SymbolID id     = cls.getSymbolID();
      String   object = IOR.getObjectName(id);
      String   mName  = IOR.getNewName(id);

      comment(mName + ": Allocate the object and initialize it.");

      d_writer.println(object + "*");
      d_writer.print(mName + "(void* ddata, ");
      d_writer.print(IOR.getExceptionFundamentalType());
      d_writer.println("* _ex)");//sidl_BaseInterface* _ex)");
      d_writer.println("{");
      d_writer.tab();

      d_writer.println(object + "* " + s_self + " =");
      d_writer.tab();
      d_writer.println("(" + object + "*) sidl_malloc(");
      d_writer.tab();
      d_writer.println("sizeof(" + object + "),");

      printLineBreak("\"Object allocation failed for " + object + "\",");
      d_writer.tab();
      d_writer.print("__FILE__, __LINE__, \"");
      d_writer.println(IOR.getNewName(id) + "\", _ex);");
      d_writer.backTab();
      d_writer.backTab();
      d_writer.backTab();
      d_writer.println("if (!" + s_self +") goto EXIT;");

      d_writer.print(IOR.getInitName(id) + "(" + s_self + ", ddata, _ex); ");
      printLineBreak(s_exc_check);

      d_writer.println("initMetadata(" + s_self + ", _ex); " + s_exc_check);
      d_writer.println("return " + s_self + ";");
      d_writer.println();
      d_writer.println("EXIT:");
      d_writer.println("return " + C.NULL + ";");

      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();
    }
  }

  /**
   * Generate the initialization function that acts as the constructor for a
   * class object. The logic is as follows. First, if there is a parent for this
   * class, call its constructor. Then, make sure that the EPVs have been
   * initialized. If there are parents, make sure that all parent EPVs are
   * updated to point to the corrected EPVs. Then initialize the new interfaces
   * in this object. Finally, call the user-defined constructor.
   */
  private void generateInitFunction(Class cls) throws CodeGenerationException {
    comment("INIT: initialize a new instance of the class object.");

    /*
     * Declare the method signature and open the implementation body.
     */
    SymbolID id = cls.getSymbolID();
    d_writer.println("void " + IOR.getInitName(id) + "(");
    d_writer.tab();
    d_writer.println(IOR.getObjectName(id) + "* " + s_self + ",");
    d_writer.println(" void* ddata,");
    d_writer.println("" + IOR.getExceptionFundamentalType() + "*_ex)");
    d_writer.backTab();

    d_writer.println("{");
    d_writer.tab();

    /*
     * Output parent pointers to simplify access to parent classes.
     */
    generateParentSelf(cls, 0, 0);
    d_writer.println();
    d_writer.println("*_ex = 0; /* default no exception */");

    //JIM MOVED
    /*
     * Ensure that the EPV has been initialized.
     */
    d_writer.println(IOR.getLockStaticGlobalsMacroName() + ";");
    d_writer.println("if (!s_method_initialized) {");
    d_writer.tab();
    d_writer.println(IOR.getInitEPVName(id) + "();");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println(IOR.getUnlockStaticGlobalsMacroName() + ";");
    d_writer.println();


    /*
     * If there is a parent class, then call its constructor.
     */
    Class parent = cls.getParentClass();
    if (parent != null) {
      d_writer.print(IOR.getInitName(parent.getSymbolID()));
      d_writer.print("(s1, " + C.NULL + ", _ex); ");
      printLineBreak(s_exc_check);
      d_writer.println();
    }


    /*
     * Modify the EPVs in parent classes and their interfaces.
     */
    fixEPVs(cls, 0, true);

    /*
     * Iterate through the interfaces defined in this class and allocate the
     * symbol identifier and set the self pointer.
     */
    List interfaces = Utilities.sort(Utilities.getUniqueInterfaceIDs(cls));
    for (Iterator i = interfaces.iterator(); i.hasNext();) {
      SymbolID iid = (SymbolID) i.next();
      String name = IOR.getSymbolName(iid).toLowerCase();
      d_writer.println("s0->d_" + name + ".d_object = " + s_self + ";");
      d_writer.println();
    }

    /*
     * Finally, set the state and data pointer for this class and allocate the
     * symbol identifier.
     */
    d_writer.println("s0->" + IOR.D_DATA + s_set_to_null);
    d_writer.println();

    if (IOR.generateHookMethods(cls, d_context)) {
      d_writer.println();
      d_writer.print(getBuiltinHooksName(id, false) + "(s0, ");
      d_writer.println("TRUE, _ex);");
      d_writer.println();

      d_writer.print("s0->" + IOR.D_CSTATS + "." + IOR.D_HOOKS);
      d_writer.println(" = " + IOR.DEFAULT_OPTION_HOOKS + ";");
      d_writer.println();
    }


    /*
     * Call the user constructor now that the object has been constructed.
     * If ddata is null, otherwise, just stick data in 
     */
    d_writer.println("if (ddata) {");
    d_writer.tab();
    d_writer.println("self->d_data = ddata;");

    d_writer.print(methodCall(cls, s_self, 
                              IOR.getBuiltinName(IOR.CONSTRUCTOR2), 
                              ",ddata,_ex"));
    printLineBreak(" " + s_exc_check);

    d_writer.backTab();
    d_writer.println("} else { ");
    d_writer.tab();

    d_writer.print(methodCall(cls, s_self, 
                              IOR.getBuiltinName(IOR.CONSTRUCTOR), ",_ex"));
    printLineBreak(" " + s_exc_check);

    d_writer.backTab();
    d_writer.println("}");
    d_writer.println("EXIT:");
    d_writer.println("return;");

    /*
     * Close the function definition.
     */
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  /**
   * Generate the finalization function that acts as the destructor for a class
   * object. The logic is as follows. First, call the user-defined destructor
   * for the object. Deallocate all identifiers for this object. If there are
   * parents, reset the EPVs to their previous values and call the parent
   * destructor.
   */
  private void generateFiniFunction(Class cls) throws CodeGenerationException {
    comment("FINI: deallocate a class instance (destructor).");

    /*
     * Declare the method signature and open the implementation block.
     */
    SymbolID id = cls.getSymbolID();
    d_writer.println("void " + IOR.getFiniName(id) + "(");
    d_writer.tab();
    d_writer.println(IOR.getObjectName(id) + "* " + s_self + ",");
    d_writer.println(IOR.getExceptionFundamentalType() + "*_ex)");
    d_writer.backTab();

    d_writer.println("{");
    d_writer.tab();

    /*
     * Output parent pointers to simplify access to parent classes.
     */
    generateParentSelf(cls, 0, 0);
    d_writer.println();
    printSetDefaultExcept();
    d_writer.println();

    /*
     * Dump statistics (if enforcing contracts).
     */
    if (IOR.generateContractChecks(cls, d_context)) {
      d_writer.println("if (sidl_Enforcer_areEnforcing()) {");
      d_writer.tab();
      d_writer.print(methodCall(cls, "s0", IOR.getBuiltinName(IOR.DUMP_STATS),
                                ", \"\", \"FINI\",_ex"));
      printLineBreak(" " + s_exc_check);
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();
    }

    /*
     * Call the user-defined destructor for this class.
     */
    d_writer.print(methodCall(cls, "s0", IOR.getBuiltinName(IOR.DESTRUCTOR),
                              ",_ex"));
    printLineBreak(" " + s_exc_check);
 
    /*
     * If there is a parent class, then reset all parent pointers and call the
     * parent destructor.
     */
    Class parent = cls.getParentClass();
    if (parent != null) {
      d_writer.println();
      fixEPVs(parent, 1, false);

      d_writer.print(IOR.getFiniName(parent.getSymbolID()) + "(s1, _ex);");
      d_writer.println(" " + s_exc_check);
    }
    
    d_writer.println();
    d_writer.println("EXIT:");  
    d_writer.println("return;");

    /*
     * Close the function definition.
     */
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  /**
   * Generate the version function.
   */
  private void generateVersionFunction(Class cls) {
    comment("VERSION: Return the version of the IOR used to generate this "
            + "IOR.");

    /*
     * Declare the method signature and open the implementation block.
     */
    SymbolID id = cls.getSymbolID();
    d_writer.println("void");
    d_writer.print(IOR.getVersionName(id));
    d_writer.println("(int32_t *major, int32_t *minor)");
    d_writer.println("{");
    d_writer.tab();
    d_writer.println("*major = s_IOR_MAJOR_VERSION;");
    d_writer.println("*minor = s_IOR_MINOR_VERSION;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  /**
   * Recursively output self pointers to the SIDL objects for this class and its
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
      d_writer.printAligned(IOR.getObjectName(id) + "*", width + 1);
      d_writer.print(" s" + String.valueOf(level) + " = ");
      if (level == 0) {
        d_writer.println(s_self + ";");
      } else {
        d_writer.print("&s" + String.valueOf(level - 1) + "->d_");
        d_writer.println(IOR.getSymbolName(id).toLowerCase() + ";");
      }
      generateParentSelf(cls.getParentClass(), level + 1, width);
    }
  }


  /**
   * Recursively output epv pointers to the SIDL objects for this class and its
   * parents. The self pointers are of the form sN, where N is an integer
   * represented by the level argument. If the width is zero, then the width of
   * all parents is generated automatically.
   */
  private void generateParentEPVs(Class cls, int level, int width) {
    if (cls != null) {

      /*
       * Calculate the width of this class and all parents for pretty output.
       * Ooh, very pretty.
       */
      if (width == 0) {
        Class parent = cls;
        while (parent != null) {
          int w = IOR.getEPVName(parent.getSymbolID()).length();
          if (w > width) {
            width = w;
          }
          parent = parent.getParentClass();
        }
      }

      /*
       * Now use the width information to print out symbols.
       * Get rid of the now unused s0 var. 
       */
      if (level != 0) {
        SymbolID id = cls.getSymbolID();
        d_writer.printAligned(IOR.getEPVName(id) + "*", width + 1);
        d_writer.println(" s" + String.valueOf(level) + s_set_to_null);

        if (IOR.generateHookMethods(cls, d_context)) {
          d_writer.printAligned(IOR.getEPVName(id) + "*", width + 1);
          d_writer.println(" h" + String.valueOf(level) + s_set_to_null);
        }

      }
      generateParentEPVs(cls.getParentClass(), level + 1, width);
    }
  }


  /*
   * Recursively modify the EPVs in parent classes and set up interface
   * pointers. Nothing is done if the class argument is null. The flag is_new
   * determines whether the EPVs are being set to a newly defined EPV or to a
   * previously saved EPV.
   */
  private void fixEPVs(Class cls, int level, boolean is_new) 
    throws CodeGenerationException 
  {
    if (cls != null) {
      SymbolID cid = cls.getSymbolID();
      fixEPVs(cls.getParentClass(), level + 1, is_new);

      /*
       * Update the EPVs for all of the new interfaces in this particular class.
       */
      String self    = "s" + String.valueOf(level);
      int    epvType = is_new ? IOR.EPV_MINE : IOR.EPV_PARENT;
      String prefix  = is_new ? "&" : "";
      List   ifce    = Utilities.sort(Utilities.getUniqueInterfaceIDs(cls));
      String epv     = IOR.getEPVVar(IOR.PUBLIC_EPV);
      int    width   = Utilities.getWidth(ifce) + epv.length() + 3;
      String name;
      for (Iterator i = ifce.iterator(); i.hasNext();) {
        SymbolID id = (SymbolID) i.next();
        name        = IOR.getSymbolName(id).toLowerCase();
        d_writer.print(self + "->");
        d_writer.printAligned("d_" + name + "." + epv, width);
        d_writer.print(" = ");
        d_writer.print(prefix);
        d_writer.print(IOR.getStaticEPVVariable(id, epvType, IOR.SET_PUBLIC));
        d_writer.println(";");
      }

      /*
       * Modify the class entry point vector.
       */
      boolean setContractsEPV =  IOR.generateContractChecks(cls, d_context)
                              && (level == 0) && is_new;
      if (setContractsEPV) {
        d_writer.println();
        d_writer.printlnUnformatted("#ifdef " + s_contracts_debug);
        d_writer.print("printf(\"Setting epv...areEnforcing=%d\\n\", ");
        d_writer.tab();
        d_writer.println("sidl_Enforcer_areEnforcing());");
        d_writer.backTab();
        d_writer.printlnUnformatted("#endif /* " + s_contracts_debug + " */");
        String base = getBaseControlsNStats(false, s_self) + ".";
        d_writer.println("if (sidl_Enforcer_areEnforcing()) {");
        d_writer.tab();
        d_writer.println("if (!" + base + IOR.D_ENABLED + ") {");
        d_writer.tab();
        d_writer.printlnUnformatted("#ifdef " + s_contracts_debug);
        d_writer.println("printf(\"Calling set_contracts()...\\n\");");
        d_writer.printlnUnformatted("#endif /* " + s_contracts_debug + " */");
        d_writer.print(IOR.getExceptionFundamentalType());
        d_writer.println("tae;");
        d_writer.print(getSetChecksMethodName(cid, false));
        d_writer.println("(" + s_self + ", sidl_Enforcer_areEnforcing(),");
        d_writer.tab();
        d_writer.println(C.NULL + ", TRUE, &tae);");
        d_writer.backTab();
        d_writer.backTab();
        d_writer.println("}");
        /*
         * TBD:  Should the Base EPV also be set to a contracts version here?
         *       Can't remember off-hand.
         */
        d_writer.printlnUnformatted("#ifdef " + s_contracts_debug);
        d_writer.println("printf(\"Setting epv to contracts version...\\n\");");
        d_writer.printlnUnformatted("#endif /* " + s_contracts_debug + " */");
        d_writer.print(self + "->");
        d_writer.printAligned(epv, width);
        d_writer.println(" = " + prefix
          + IOR.getStaticEPVVariable(cid, epvType, IOR.SET_CONTRACTS) + ";");
        d_writer.backTab();
        d_writer.println("} else {");
        d_writer.tab();
        d_writer.printlnUnformatted("#ifdef " + s_contracts_debug);
        d_writer.println("printf(\"Setting epv to regular version...\\n\");");
        d_writer.printlnUnformatted("#endif /* " + s_contracts_debug + " */");
      }
      String rhs = prefix;
      rhs += IOR.getStaticEPVVariable(cid, epvType, IOR.SET_PUBLIC);
      rhs += ";";
      d_writer.print(self + "->");
      d_writer.printAligned(epv, width);
      d_writer.println(" = " + rhs);
      if (setContractsEPV) {
        d_writer.backTab();
        d_writer.println("}");
      }
      if (IOR.generateBaseEPVAttr(cls, d_context)) {
        d_writer.print(self + "->");
        d_writer.printAligned(IOR.getEPVVar(IOR.BASE_EPV), width);
        d_writer.println(" = " + rhs);
      }
      d_writer.println();
    }
  }


  /*
   * Modify the contract EPV(s) and set up interface pointers.
   * Nothing is done if the class argument is null.
   */
  private void fixContractEPVs(Class cls, int epvVarType, int epvType)
    throws CodeGenerationException 
  {
    if ( (cls != null) && (!IOR.isSIDLSymbol(cls)) ) {
      /*
       * Recursion NOT yet supported.
      fixContractEPVs(cls.getParentClass(), epvVarType, epvType);
       */

      List   ifce   = Utilities.sort(Utilities.getUniqueInterfaceIDs(cls));
      String epv    = IOR.getEPVVar(epvVarType);
      int    width  = Utilities.getWidth(ifce) + epv.length() + 3;

      /*
       * Modify the class entry point vector.
       */
      String rhs = "&" + IOR.getStaticEPVVariable(cls.getSymbolID(),
                                                  IOR.EPV_MINE, epvType) + ";";
      d_writer.print(s_self + "->");
      d_writer.printAligned(epv, width);
      d_writer.println(" = " + rhs);

      /*
       * Modify the EPVs for the new interfaces in this particular class.
       */
      /*
       * Interface-specific contract EPVs not supported...at least not yet.
      String name;
      for (Iterator i = ifce.iterator(); i.hasNext(); ) {
        SymbolID id = (SymbolID) i.next();
        name        = IOR.getSymbolName(id).toLowerCase();
        d_writer.print(s_self + "->");
        d_writer.printAligned("d_" + name + "." + epv, width);
        d_writer.print(" = ");
        d_writer.print("&");
        d_writer.print(IOR.getStaticEPVVariable(id, IOR.EPV_MINE, epvType));
        d_writer.println(";");
      }
      */
    }
  }


  /*
   * Recursively save the class and interface EPVs in this class and all parent
   * classes. Nothing is done if the class argument is null.
   */
  private void saveEPVs(Class cls, int level) {
    if (cls != null) {
      saveEPVs(cls.getParentClass(), level + 1);
      SymbolID id = cls.getSymbolID();

      /*
       * Save the class entry point vector.
       */
      d_writer.print("s" + String.valueOf(level) +"  =  ");
      d_writer.print(IOR.getStaticEPVVariable(id, IOR.EPV_PARENT,
                                              IOR.SET_PUBLIC));
      d_writer.println(";");

      if (IOR.generateHookMethods(cls, d_context)) {
        d_writer.print("h" + String.valueOf(level) +"  =  ");
        d_writer.print(IOR.getStaticEPVVariable(id, IOR.EPV_PARENT, 
                                                IOR.SET_HOOKS));
        d_writer.println(";");
      }

    }
  }

  /**
   * Generate the entry point vector alias for each parent class and each
   * interface as well as a special one for the current object.
   */
  private void aliasEPVs(Class cls, Collection parents) 
    throws CodeGenerationException 
  {
    /*
     * Get the width of the symbols for pretty printing.
     */
    SymbolID id    = cls.getSymbolID();
    String   epv   = IOR.getEPVName(id);
    int      width = epv.length() + 2;
    int      w     = Utilities.getWidth(parents) + "struct __epv*".length();

    if (w > width) {
      width = w;
    }

    /*
     * Output the EPV pointer for this class and its class and interface
     * parents.
     */
    d_writer.printAligned(epv + "*", width);
    d_writer.print(" epv  = &");
    d_writer.print(IOR.getStaticEPVVariable(id, IOR.EPV_MINE, IOR.SET_PUBLIC));
    d_writer.println(";");
    if (IOR.generateContractChecks(cls, d_context)) {
      d_writer.printAligned(epv + "*", width);
      d_writer.print(" cepv = &");
      d_writer.print(IOR.getStaticEPVVariable(id, IOR.EPV_MINE, 
                                              IOR.SET_CONTRACTS));
      d_writer.println(";");
    }
    if (IOR.generateHookMethods(cls, d_context)) {
      d_writer.printAligned(epv + "*", width);
      d_writer.print(" hepv = &");
      d_writer.print(IOR.getStaticEPVVariable(id, IOR.EPV_MINE, IOR.SET_HOOKS));
      d_writer.println(";");
    }

    int e = 0;
    for (Iterator i = parents.iterator(); i.hasNext();) {
      Extendable par = (Extendable) i.next();
      SymbolID   sid = par.getSymbolID();
      d_writer.printAligned(IOR.getEPVName(sid) + "*", width);
      d_writer.printAligned(" e" + String.valueOf(e), 4);
      d_writer.print("  = &");
      d_writer.print(IOR.getStaticEPVVariable(sid, IOR.EPV_MINE, 
                                              IOR.SET_PUBLIC));
      d_writer.println(";");

      if (IOR.generateHookMethods(par, d_context)) {
        d_writer.printAligned(IOR.getEPVName(sid) + "*", width);
        d_writer.printAligned(" he" + String.valueOf(e), 4);
        d_writer.print("  = &");
        d_writer.print(IOR.getStaticEPVVariable(sid, IOR.EPV_MINE, 
                       IOR.SET_HOOKS));
        d_writer.println(";");
      }
      ++e;
    }
    d_writer.println();
  }

  /*
   * Copy EPV function pointers from the most derived EPV data structure into
   * all parent class and interface EPVs.
   */
  private void copyEPVs(Collection parents, HashMap renames) 
    throws CodeGenerationException 
  {
    int e = 0;
    for (Iterator i = parents.iterator(); i.hasNext();) {
      /*
       * Extract information about the parent extendable object. Generate a list
       * of the nonstatic methods and calculate the width of every method name.
       */
      SymbolID   id  = (SymbolID) i.next();
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
       * Generate the assignments for the cast and delete functions.
       */
      String estring  = "e" + String.valueOf(e) + "->";
      String vecEntry = IOR.getVectorEntry(s_castBuiltin);

      comment("Override function pointers for " + ext.getFullName() 
        + " with mine, as needed.");
      d_writer.print(estring);
      d_writer.printAligned(vecEntry, mwidth);
      d_writer.print(" = (void* (*)(" + self);
      d_writer.print(",const char*, struct sidl_BaseInterface__object**))");
      d_writer.print("epv->");
      d_writer.print(vecEntry);
      d_writer.println(";");

      vecEntry = IOR.getVectorEntry(s_deleteBuiltin);
      d_writer.print(estring);
      d_writer.printAligned(vecEntry, mwidth);
      d_writer.print(" = (void (*)(" + self + ", ");
      d_writer.print(IOR.getExceptionFundamentalType());
      d_writer.println("*)) epv->" + vecEntry + ";");

      Collection methList;
      if (!d_context.getConfig().getSkipRMI()) {

        vecEntry = IOR.getVectorEntry(s_getURLBuiltin);
        d_writer.print(estring);
        d_writer.printAligned(vecEntry, mwidth);
        d_writer.print(" = (char* (*)(" + self + ", ");
        d_writer.print(IOR.getExceptionFundamentalType());
        d_writer.println("*)) epv->" + vecEntry + ";");

        vecEntry = IOR.getVectorEntry(s_raddRefBuiltin);
        d_writer.print(estring);
        d_writer.printAligned(vecEntry, mwidth);
        d_writer.print(" = (void (*)(" + self + ", ");
        d_writer.print(IOR.getExceptionFundamentalType());
        d_writer.println("*)) epv->" + vecEntry + ";");
        
        vecEntry = IOR.getVectorEntry(s_isRemoteBuiltin);
        d_writer.print(estring);
        d_writer.printAligned(vecEntry, mwidth);
        d_writer.print(" = (sidl_bool (*)(" + self + ", ");
        d_writer.print(IOR.getExceptionFundamentalType());
        d_writer.println("*)) epv->" + vecEntry + ";");

        /**
         * Generate the assignment for exec function
         */
        vecEntry      = IOR.getVectorEntry(s_execBuiltin);
        Method m_exec = IOR.getBuiltinMethod(IOR.EXEC, id, d_context, false);
        String ename  = m_exec.getLongMethodName();
        d_writer.print(estring);
        d_writer.printAligned(IOR.getVectorEntry(ename), mwidth);
        d_writer.print(" = " + IOR.getCast(m_exec, self, d_context));
        d_writer.println(" epv->" + IOR.getVectorEntry(ename) + ";");

        methList = ext.getMethodsWithNonblocking(true);
      }
      else {
        methList = ext.getNonstaticMethods(true);
      }

      /*
       * Iterate over all methods in the EPV and set the method pointer.
       */
      for (Iterator j = methList.iterator(); j.hasNext();) 
      {
        Method method        = (Method) j.next();
        String name          = id.getFullName() + ".";
        name                += method.getLongMethodName();
        Method renamedMethod = (Method) renames.get(name);
        String oldname       = method.getLongMethodName();
        String newname       = null;
        if (renamedMethod != null) {
          newname = renamedMethod.getLongMethodName();
        } else { 
          newname = method.getLongMethodName();
        }
        d_writer.print(estring);
        d_writer.printAligned(IOR.getVectorEntry(oldname), mwidth);
        d_writer.print(" = " + IOR.getCast(method, self, d_context));
        d_writer.println(" epv->" + IOR.getVectorEntry(newname) + ";");
      }

      /*
       * Do the same for the native EPV entries if enabled
       */
      d_writer.println();
      if(d_context.getConfig().getFastCall()) {
        String guard = IOR.getNativeEPVGuard(ext);
        d_writer.printlnUnformatted("#ifdef " + guard);

        for (Iterator j = methList.iterator(); j.hasNext(); ) {
          Method method        = (Method) j.next();
          String name          = id.getFullName() + ".";
          name                += method.getLongMethodName();
          Method renamedMethod = (Method) renames.get(name);
          String oldname       = method.getLongMethodName();
          String newname       = null;
          if (renamedMethod != null) {
            newname = renamedMethod.getLongMethodName();
          } else { 
            newname = method.getLongMethodName();
          }
          d_writer.print(estring);
          d_writer.printAligned(IOR.getNativeVectorEntry(oldname), mwidth);
          d_writer.println(" = epv->" + IOR.getNativeVectorEntry(newname) + ";");
        }
        
        d_writer.printlnUnformatted("#endif /*" + guard + "*/");
      }
      
      //JIM add in he stuff here?
      d_writer.println();
      boolean genHookMethods = IOR.generateHookMethods(ext, d_context);
      if (genHookMethods) {
        d_writer.print("memcpy((void*) he" + e + ", e" + e + ", sizeof(");
        d_writer.println(IOR.getEPVName(id) + "));");
      //  generateEPVMethodAssignments(ext, IOR.SET_HOOKS, "he"+e, false);
      }

      /*
       * Iterate over all methods in the EPV and set the method pointer.
       * if we're using hooks
       */
      if (genHookMethods) {
        for (Iterator j = methods.iterator(); j.hasNext();) {
          Method method        = (Method) j.next();
          String rName         = id.getFullName() + ".";
          rName               += method.getLongMethodName();
          Method renamedMethod = (Method) renames.get(rName);
          String oldname       = method.getLongMethodName();
          String newname       = null;
          if (renamedMethod != null) {
            newname = renamedMethod.getLongMethodName();
          } else { 
            newname = method.getLongMethodName();
          }
          d_writer.print("he" + e + "->");
          d_writer.printAligned(IOR.getVectorEntry(oldname), mwidth);
          d_writer.print(" = " + IOR.getCast(method, self, d_context));
          d_writer.println(" hepv->" + IOR.getVectorEntry(newname) + ";");
        }
      }

      d_writer.println();
      e++;
    }
  }

  private void generateGetParentEPVs(Class parent) {
    comment("Get my parent's EPVs so I can start with their functions.");

    d_writer.println(IOR.getGetEPVsName(parent.getSymbolID())+"(");
    d_writer.tab();
    listEPVs(parent, true);
    d_writer.println(");");
    d_writer.backTab();
    d_writer.println("");
  }

  private void listEPVs(Class cls, boolean first) {
    if (cls != null) {
      listEPVs(cls.getParentClass(), false);
      List ifce = Utilities.sort(Utilities.getUniqueInterfaceIDs(cls));
      for (Iterator i = ifce.iterator(); i.hasNext();) {
        Interface ifc = (Interface) i.next();
        SymbolID id = ifc;
        d_writer.print("&");
        d_writer.print(IOR.getStaticEPVVariable(id, IOR.EPV_PARENT, 
                                                IOR.SET_PUBLIC));
        d_writer.println(",");
        if (IOR.generateHookEPVs(ifc, d_context)) {
          d_writer.print("&");
          d_writer.print(IOR.getStaticEPVVariable(id, IOR.EPV_PARENT, 
                                                  IOR.SET_HOOKS));
          d_writer.println(",");
        }
      }
      d_writer.print("&");
      d_writer.print(IOR.getStaticEPVVariable(cls.getSymbolID(), IOR.EPV_PARENT,
                                              IOR.SET_PUBLIC));
      if (IOR.generateHookEPVs(cls, d_context)) {
        d_writer.println(",");
        d_writer.print("&");
        d_writer.print(IOR.getStaticEPVVariable(cls.getSymbolID(), 
                                                IOR.EPV_PARENT, IOR.SET_HOOKS));
      } 

      if (!first){
        d_writer.println(",");
      } 
    }
  }

  /*
   * Generate method to return my version of the relevant EPVs -- 
   * mine and all of my ancestors.
   *
   * The purpose of this method is to make my version of the
   * EPVs available to my immediate descendants (so they can
   * use my functions in their versions of their EPVs).
   */
  private void declareGetEPVs(Class cls) {
    String name = IOR.getGetEPVsName(cls.getSymbolID());

    comment(name + ": Get my version of all relevant EPVs.");
    d_writer.println("void " + name + " (");
    d_writer.tab();
    IOR.declareEPVsAsArgs(d_writer, cls, d_context, true);
    d_writer.println(")");
    d_writer.backTab();
    d_writer.println("{");
    d_writer.tab();

    /*
     * Ensure that the EPV has been initialized.
     */
    d_writer.println(IOR.getLockStaticGlobalsMacroName() + ";");
    d_writer.println("if (!s_method_initialized) {");
    d_writer.tab();
    d_writer.println(IOR.getSymbolName(cls.getSymbolID()) + "__init_epv();");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println(IOR.getUnlockStaticGlobalsMacroName() + ";");
    d_writer.println();

    setEPVsInGetEPVs(cls);
    d_writer.backTab();
    d_writer.println("}");
        
  }

  private void printEPVAssign(SymbolID id, int eVar1, int eVar2, int setVar) {
    d_writer.print("*");
    d_writer.print(IOR.getStaticEPVVariable(id, eVar1, setVar));
    d_writer.print(" = &");
    d_writer.print(IOR.getStaticEPVVariable(id, eVar2, setVar));
    d_writer.println(";");
  }

  public void setEPVsInGetEPVs(Class cls) {
    if (cls != null) {
      setEPVsInGetEPVs(cls.getParentClass());
      List ifce = Utilities.sort(Utilities.getUniqueInterfaces(cls));
      for (Iterator i = ifce.iterator(); i.hasNext();) {
        Extendable par = (Extendable) i.next();
        SymbolID   id  = par.getSymbolID();
        printEPVAssign(id, IOR.EPV_ARG, IOR.EPV_MINE, IOR.SET_PUBLIC);
        if (IOR.generateHookEPVs(par, d_context)) {
          printEPVAssign(id, IOR.EPV_ARG, IOR.EPV_MINE, IOR.SET_HOOKS);
        }
      }
      SymbolID cId = cls.getSymbolID();
      printEPVAssign(cId, IOR.EPV_ARG, IOR.EPV_MINE, IOR.SET_PUBLIC);
      if (IOR.generateHookEPVs(cls, d_context)) {
        printEPVAssign(cId, IOR.EPV_ARG, IOR.EPV_MINE, IOR.SET_HOOKS);
      }
    }
  }

  private static String methodCall(Extendable ext, String var, String method,
                                   String args) 
  {
    String result = "(*(" + var + "->" + IOR.getEPVVar(IOR.PUBLIC_EPV) + "->";
    result       += IOR.getVectorEntry(method) + "))(" + var;
    if (ext.isInterface()) {
      result += "->d_object";
    }
    result += args;
    result += ");";
    return result;
  }

  public static void generateExternalSignature(LanguageWriterForC lw,
                                               Symbol sym, String terminator) 
  {
    final SymbolID id = sym.getSymbolID();
    lw.beginBlockComment(false);
    lw.println("This function returns a pointer to a static structure of");
    lw.println("pointers to function entry points.  Its purpose is to provide");
    lw.println("one-stop shopping for loading DLLs.");
    lw.endBlockComment(false);
    lw.println("const " + IOR.getExternalName(id) + "*");
    lw.println(IOR.getExternalFunc(id) + "(void)" + terminator);
  }

  private void generateExternalFunc(Symbol sym) {
    SymbolID id  = sym.getSymbolID();
    Class    cls = null;
    if (sym instanceof Class) {
      cls = (Class) sym;
      /*
       * if (  IOR.generateHookMethods(cls, d_context) 
       *    || IOR.generateContractChecks(cls, d_context) ) 
       * { d_writer.println("const " + IOR.getExternalName(id)); } else {
       */
      d_writer.println("static const " + IOR.getExternalName(id));
      /*
       * }
       */
    }
    d_writer.println("s_externalEntryPoints = {");
    d_writer.tab();
    if (sym instanceof Class) {
      if (!cls.isAbstract()) {
        d_writer.println(IOR.getNewName(id) + ",");
      }
      if (cls.hasStaticMethod(true)) {
        d_writer.println(IOR.getStaticsName(id) + ",");
      }
      if (cls.getParentClass() != null) {
        d_writer.println(IOR.getSymbolName(id) + s_superBuiltin + ",");
      }
    }
    d_writer.println(Integer.toString(IOR.MAJOR_VERSION) + ", ");
    d_writer.println(Integer.toString(IOR.MINOR_VERSION));
    d_writer.backTab();
    d_writer.println("};");
    d_writer.println();
    generateExternalSignature(d_writer, sym, "");
    d_writer.println("{");
    d_writer.tab();
    d_writer.println("return &s_externalEntryPoints;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

}
