//
// File:        PythonServerLaunch.java
// Package:     gov.llnl.babel.backend.python
// Revision:    @(#) $Revision: 7188 $
// Date:        $Date: 2011-09-27 11:38:42 -0700 (Tue, 27 Sep 2011) $
// Description: Initialize Python before loading the real implementation
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

import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.Utilities;
import gov.llnl.babel.backend.c.C;
import gov.llnl.babel.backend.writers.LanguageWriterForC;
import gov.llnl.babel.symbols.Argument;
import gov.llnl.babel.symbols.Class;
import gov.llnl.babel.symbols.Type;
import gov.llnl.babel.symbols.SymbolID;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class PythonServerLaunch {
  LanguageWriterForC d_lw = null;
  Class d_cls;
  Context d_context;
  boolean d_hasStatic;
  
  private static final String s_epv = "epv";
  private static final String s_pre_epv = "pre_epv";
  private static final String s_post_epv = "post_epv";
  private static final String s_sepv = "sepv";
  private static final String s_pre_sepv = "pre_sepv";
  private static final String s_post_sepv = "post_sepv";

  public PythonServerLaunch(Class cls, Context context)
  {
    d_context = context;
    d_cls = cls;
    d_hasStatic = d_cls.hasStaticMethod(true);
  }

  private void writeIncludes() throws CodeGenerationException
  {
    d_lw.generateInclude("sidl_Python.h", true);
    d_lw.generateInclude("sidl_Loader.h", true);
    d_lw.printlnUnformatted("#include <stdio.h>");
    /*    Iterator i = Utilities.convertIdsToSymbols(argSymbols()).iterator();
          while (i.hasNext()) {
          Symbol sym = (Symbol)i.next();
          if (Symbol.ENUM != sym.getSymbolType()) {
          d_lw.generateInclude(Python.getCHeaderPath(sym, "Module"), true);
          }
          }
          d_lw.generateInclude(Python.getCHeaderPath(d_cls, "Module"), true);
          java.util.Collection parents = d_cls.getParents(true);
          for(Iterator j = parents.iterator(); j.hasNext(); ){
          Extendable parent = (Extendable) j.next();
          d_lw.generateInclude(Python.getCHeaderPath(parent, "Module"), true);
          } 
    */
    d_lw.println();
  }

  private void writeForwardDecls()
  {
    final SymbolID id = d_cls.getSymbolID();
    d_lw.println(IOR.getEPVName(id) + ";");
    if (IOR.generateHookEPVs(d_cls, d_context)) {
      d_lw.println(IOR.getPreEPVName(id) + ";");
      d_lw.println(IOR.getPostEPVName(id) + ";");
    }
    if (d_hasStatic) {
      d_lw.println(IOR.getSEPVName(id) + ";");
      if (IOR.generateHookEPVs(d_cls, d_context)) {
        d_lw.println(IOR.getPreSEPVName(id) + ";");
        d_lw.println(IOR.getPostSEPVName(id) + ";");
      }

    }
    d_lw.println();
  }

  private void writeStaticVars() throws CodeGenerationException
  {
    final SymbolID id = d_cls.getSymbolID();
    d_lw.println("static void (*s_getEPV)");
    d_lw.tab();
    d_lw.print("(" + IOR.getEPVName(id) + "*");
    if (IOR.generateHookEPVs(d_cls, d_context)) {
      d_lw.print(", "+ IOR.getPreEPVName(id) + "*, " + IOR.getPostEPVName(id)
        + "*");
    }
    d_lw.println(");");

    d_lw.backTab();
    if (d_hasStatic) {
      d_lw.println("static void (*s_getSEPV)");
      d_lw.tab();
      d_lw.print("(" + IOR.getSEPVName(id) + "*");
      if (IOR.generateHookEPVs(d_cls, d_context)) {
        d_lw.print(", " + IOR.getPreSEPVName(id) + "*, " 
          + IOR.getPostSEPVName(id) + "*");
      }
      d_lw.println(");");
      d_lw.backTab();
    }
    d_lw.println();

    Set structIds = IOR.getStructSymbolIDs(d_cls, true);
    structIds.addAll(IOR.getStructSymbolIDs(d_cls, false));
    Iterator i = Utilities.sort(structIds).iterator();
    if (i.hasNext()) {
      d_lw.writeCommentLine("Forward declarations of structs");
      while (i.hasNext()) {
        SymbolID sid = (SymbolID)i.next();
        d_lw.println(IOR.getStructName(sid) + ";");
      }
      d_lw.println();
    }

    if (!d_context.getConfig().getSkipRMI()) {
      Python.generateRMIExternStruct(d_cls, d_lw, d_context);
      d_lw.println();
      d_lw.println("static struct " + IOR.getSymbolName(id)
                   + "__rmiExternals *s_rmiExternals;");
      d_lw.println();
    }

  }
  
  private void writeInit()
  {
    final SymbolID id = d_cls.getSymbolID();
    d_lw.println("static void");
    d_lw.println("initImplementation(void) {");
    d_lw.tab();
    d_lw.println(IOR.getExceptionFundamentalType() + "throwaway_exception;");
    d_lw.println("static int notInitialized = 1;");
    d_lw.println("if (notInitialized) {");
    d_lw.tab();
    d_lw.println("sidl_Python_Init(); /* must happen before findLibrary */");
    d_lw.println("{");
    d_lw.tab();
    d_lw.println("sidl_DLL dll = sidl_Loader_findLibrary(\"" 
      + id.getFullName() + "\",");
    d_lw.tab();
    d_lw.println("\"python/impl\", sidl_Scope_SCLSCOPE,");
    d_lw.println("sidl_Resolve_SCLRESOLVE, &throwaway_exception);");
    d_lw.backTab();
    d_lw.println("if (dll) {");
    d_lw.tab();
    if (!d_context.getConfig().getSkipRMI()) {
      d_lw.println("struct " + IOR.getSymbolName(id) 
                   + "__rmiExternals *((*rmi_func)(void));");
    }

    d_lw.println("notInitialized = 0;");
    d_lw.print("s_getEPV = (void (*)(");
    d_lw.print(IOR.getEPVName(id) + " *");
    if (IOR.generateHookEPVs(d_cls, d_context)) {
      d_lw.print(", " + IOR.getPreEPVName(id) + "*, " + IOR.getPostEPVName(id)
                 + "*");
    }
    d_lw.println("))");
    d_lw.tab();
    d_lw.println("sidl_DLL_lookupSymbol(dll, \"" + Python.getSetEPVName(id)
                 + "\", &throwaway_exception);");
    d_lw.backTab();
      
    if (!d_context.getConfig().getSkipRMI()) {
      d_lw.println("rmi_func = (struct " + IOR.getSymbolName(id)
                   + "__rmiExternals*((*)(void))) ");
      d_lw.tab();
      d_lw.println("sidl_DLL_lookupSymbol(dll, \"" 
                   + Python.getRMIExternName(d_cls.getSymbolID()) 
                   + "\", &throwaway_exception);");
      
      d_lw.println("if (rmi_func) {");
      d_lw.tab();
      d_lw.println("s_rmiExternals = (*rmi_func)();");
      d_lw.backTab();
      d_lw.println("}"); 
      d_lw.backTab();
    }
    if (d_hasStatic) {
      d_lw.tab();
      d_lw.print("s_getSEPV = (void (*)(");
      d_lw.print(IOR.getSEPVName(id) + "*");
      if (IOR.generateHookEPVs(d_cls, d_context)) {
        d_lw.print(", " + IOR.getPreSEPVName(id) + "*, " 
          + IOR.getPostSEPVName(id) + "*");
      }
      d_lw.println("))");
      d_lw.println("sidl_DLL_lookupSymbol(dll, \"" 
        + Python.getSetSEPVName(d_cls.getSymbolID()) 
        + "\", &throwaway_exception);");
      d_lw.backTab();
    }
    

    d_lw.println("sidl_DLL_deleteRef(dll, &throwaway_exception);");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println();
  }

  private void writeGetEPV()
  {
    final SymbolID id = d_cls.getSymbolID();
    d_lw.openCxxExtern();
    d_lw.println("void");
    d_lw.print(IOR.getSetEPVName(id) + "(" + IOR.getEPVName(id) + " *" + s_epv);
    if (IOR.generateHookEPVs(d_cls, d_context)) {
      d_lw.println(",");
      d_lw.tab();
      d_lw.println(IOR.getPreEPVName(id) + " *" + s_pre_epv + ", "
        + IOR.getPostEPVName(id) + " *" + s_post_epv + ")");
      d_lw.backTab();
    } else {
      d_lw.println(")");
    }
    d_lw.println("{");
    d_lw.tab();
    d_lw.println("initImplementation();");
    d_lw.print("if (s_getEPV) (*s_getEPV)(" + s_epv);
    if (IOR.generateHookEPVs(d_cls, d_context)) {
      d_lw.print("," + s_pre_epv + "," + s_post_epv);
    }
    d_lw.println(");");
    d_lw.println("else {");
    d_lw.tab();
    d_lw.println("fputs(\"\\");
    d_lw.printlnUnformatted("Babel: Fatal error loading stage 2 Python implementation for " 
      + id.getFullName() + "\",");
    d_lw.println("stderr);");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.closeCxxExtern();
    d_lw.println();
  }

  private void writeGetSEPV()
  {
    final SymbolID id = d_cls.getSymbolID();
    d_lw.openCxxExtern();
    d_lw.println("void");
    d_lw.print(IOR.getSetSEPVName(id) + "(" + IOR.getSEPVName(id) 
      + " *" + s_sepv);
    if (IOR.generateHookEPVs(d_cls, d_context)) {
      d_lw.println(",");
      d_lw.tab();
      d_lw.println(IOR.getPreSEPVName(id) + " *" + s_pre_sepv + ", " 
        + IOR.getPostSEPVName(id) + " *" + s_post_sepv + ")");
      d_lw.backTab();
    } else {
      d_lw.println(")");
    }
    d_lw.println("{");
    d_lw.tab();
    d_lw.println("initImplementation();");
    d_lw.println("if (s_getSEPV) (*s_getSEPV)(" + s_sepv);
    if (IOR.generateHookEPVs(d_cls, d_context)) {
      d_lw.println("," + s_pre_sepv + "," + s_post_sepv);
    }
    d_lw.println(");");
    d_lw.println("else {");
    d_lw.tab();
    d_lw.println("fputs(\"\\");
    d_lw.printlnUnformatted("Babel: Fatal error loading stage 2 Python implementation for " 
      + id.getFullName() + "\",");
    d_lw.println("stderr);");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.closeCxxExtern();
    d_lw.println();
  }

  private void writeCallLoad()
    throws CodeGenerationException
  {
    final SymbolID id = d_cls.getSymbolID();
    d_lw.println("void " + IOR.getSymbolName(id) + "__call_load(void) { ");
    d_lw.tab();
    d_lw.writeCommentLine("Python doesn't really need load");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println();
  }

  private void writeSerializePskel(Set ids,
                                   boolean serialize)
    throws CodeGenerationException
  {
    SymbolID pipe = Utilities.
      lookupSymbol(d_context, serialize ? "sidl.rmi.Return" : "sidl.rmi.Call");
    Iterator i = ids.iterator();
    ArrayList con_args = new ArrayList(4);
    con_args.add(null);
    con_args.add(new 
                 Argument(Argument.IN, 
                          new Type(pipe, Type.INTERFACE, null, 0, 0, d_context),
                          "pipe"));
    con_args.add(new Argument(Argument.IN, new Type(Type.STRING), "name"));
    con_args.add(new Argument(Argument.IN, new Type(Type.BOOLEAN), "copy"));
    while (i.hasNext()) {
      SymbolID id = (SymbolID)i.next();
      d_lw.println("void");
      d_lw.println(Python.getPSkelSerializeName(d_cls, id, serialize, true) +
                   "(");
      d_lw.tab();
      con_args.set(0, new Argument
                  (serialize ? Argument.IN : Argument.OUT,
                   new Type(id, Type.STRUCT, null, 0, 0, d_context),
                   "strct"));
                 
      IOR.generateArguments(d_lw, d_context,
                            "", con_args, true, true, null,
                            true, true, false, false);
      d_lw.println(")");
      d_lw.backTab();
      d_lw.println("{");
      d_lw.tab();
      d_lw.println("return (*s_rmiExternals->" +
                   IOR.getVectorEntry
                   (Python.getPSkelSerializeName(d_cls,id,serialize, false)) +
                   ")(");
      d_lw.tab();
      IOR.generateArguments(d_lw, d_context,
                            "", con_args, true, true, null,
                            false, true, false, false);
      d_lw.println(");");
      d_lw.backTab();
      d_lw.backTab();
      d_lw.println("}");
      d_lw.println();
    }
  }

  /**
   * Write functions to call each RMI fconnect and FGetURL function
   *
   * @param cls    the class for which an routine will be written.
   * @param writer the output writer to which the routine will be written.
   */
  private void writeRMIAccessFunctions() 
    throws CodeGenerationException
  {
    SymbolID id = d_cls.getSymbolID();
    //TODO: When we RMI interfaces work, we should not exclude them! (edit getObjectDepe...)
    Set fconnectSIDs = IOR.getFConnectSymbolIDs(d_cls);
    Set serializeSIDs = IOR.getStructSymbolIDs(d_cls, true);
    Set deserializeSIDs = IOR.getStructSymbolIDs(d_cls, false);
    
    d_lw.openCxxExtern();
    for (Iterator i = fconnectSIDs.iterator(); i.hasNext(); ) {        
      
      SymbolID destination_id = (SymbolID) i.next();
      
      d_lw.println();
      
      d_lw.println(C.getSymbolObjectPtr(destination_id) + " " + IOR.getSkelFConnectName(id,destination_id)+ 
                   "(const char* url, sidl_bool ar, "
                   + IOR.getExceptionFundamentalType() +
                   "*_ex) { ");
      d_lw.tab();
      
      //Actual call through to the pskels
      d_lw.println("return (*s_rmiExternals->f_" + Python.getPSkelFConnectName(id,destination_id)+")(url, ar, _ex);" );
      
      d_lw.backTab();
      d_lw.println("}");
      d_lw.println(); 
    }
    writeSerializePskel(serializeSIDs, true);
    writeSerializePskel(deserializeSIDs, false);
    d_lw.closeCxxExtern();
  }
  
  
  public synchronized void generateCode()
    throws CodeGenerationException
  {
    try {
      d_lw = Python.createLaunch(d_cls, "launch the implementation", 
                                 d_context);
      writeIncludes();
      writeForwardDecls();
      writeStaticVars();
      writeInit();
      writeGetEPV();
      if (d_hasStatic) {
        writeGetSEPV();
      }
      writeCallLoad();
      if (!d_context.getConfig().getSkipRMI()) {
        writeRMIAccessFunctions(); 
      }
    }
    finally {
      if (d_lw != null) {
        d_lw.close();
        d_lw = null;
      }
    }
  }
  
}
