//
// File:        AbbrevHeader.java
// Package:     gov.llnl.babel.backend.fortran
// Copyright:   (c) 2002 Lawrence Livermore National Security, LLC
// Revision:    @(#) $Revision: 7188 $
// Date:        $Date: 2011-09-27 11:38:42 -0700 (Tue, 27 Sep 2011) $
// Description: Write #include file to mangle names when necessary
// 
//
// Copyright (c) 2002, Lawrence Livermore National Security, LLC
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
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.fortran.Fortran;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.mangler.FortranMangler;
import gov.llnl.babel.backend.mangler.NameMangler;
import gov.llnl.babel.backend.mangler.NonMangler;
import gov.llnl.babel.backend.writers.LanguageWriter;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Enumeration;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;


public class AbbrevHeader {

  static private final String s_arrayMethods[] = {
    "access",
    "addRef",
    "borrow",
    "cborrow",
    "copy",
    "create1d",
    "create2dCol",
    "create2dRow",
    "createCol",
    "createRow",
    "deleteRef",
    "dimen",
    "ensure",
    "get",
    "get1",
    "get2",
    "get3",
    "get4",
    "get5",
    "get6",
    "get7",
    "isColumnOrder",
    "isRowOrder",
    "lower",
    "set",
    "set1",
    "set2",
    "set3",
    "set4",
    "set5",
    "set6",
    "set7",
    "slice",
    "smartCopy",
    "stride",
    "upper",
    "length"
  };

  static private final String s_skelMethods[] = {
    "_get_data",
    "_set_data"
  };

  private LanguageWriter d_lw;

  private Context d_context;

  public AbbrevHeader(LanguageWriter writer,
                      Context context) 
  {
    d_lw = writer;
    d_context = context;
  }
  
  static public int getMaxName(Context context) {
    return Fortran.hasBindC(context) ? 63 : 31;
  }
  
  public int getMaxName() { return getMaxName(d_context); }

  static public int getMaxUnmangled(Context context) {
    return Fortran.hasBindC(context) ? 53 : 21;
  }
  
  public int getMaxUnmangled() { return getMaxUnmangled(d_context); }
  
  private void writeLine(String longName,
                          String shortName)
  {
    try {
      d_lw.pushLineBreak(false);
      d_lw.print("#define ");
      d_lw.print(longName);
      d_lw.print(" ");
      d_lw.println(shortName);
    }
    finally {
      d_lw.popLineBreak();
    }
  }

  private void writeEntry(String longName,
                          String shortName)
  {
    String monocase = longName.toLowerCase();
    writeLine(longName, shortName);
    if (!monocase.equals(longName)) {
      writeLine(monocase, shortName.toLowerCase());
    }
    monocase = longName.toUpperCase();
    if (!monocase.equals(longName)) {
      writeLine(monocase, shortName.toUpperCase());
    }
  }

  public void generateMethods(String symName,
                              NameMangler non,
                              NameMangler fort,
                              String [] methodNames)
    throws UnsupportedEncodingException
  {
    final String suffix = Fortran.getMethodSuffix(d_context);
    int i;
    for(i = 0; i < methodNames.length; ++i){
      String longName = 
        non.shortArrayName(symName, methodNames[i], suffix);
      if (longName.length() > getMaxName()) {
        String shortName =
          fort.shortArrayName(symName, methodNames[i], suffix);
        writeEntry(longName, shortName);
      }
    } 
  }

  public void generateClassMethods(String symName,
                                   NameMangler non,
                                   NameMangler fort,
                                   String [] methodNames)
    throws UnsupportedEncodingException
  {
    final String suffix = Fortran.getMethodSuffix(d_context);
    int i;
    for(i = 0; i < methodNames.length; ++i){
      String longName = 
        non.shortName(symName, methodNames[i], suffix);
      if (longName.length() > getMaxName()) {
        String shortName =
          fort.shortName(symName, methodNames[i], suffix);
        writeEntry(longName, shortName);
      }
    } 
  }

  public void generateNamedMethod(String symName,
                                  String methName,
                                  String suffix,
                                  NameMangler non,
                                  NameMangler fort)
    throws UnsupportedEncodingException
  {
    String longName = non.shortName(symName, methName, suffix);
    if (longName.length() > getMaxName()) {
      String shortName = fort.shortName(symName, methName, suffix);
      writeEntry(longName, shortName);
    }
  }

  private void processMethod(String symName,
                             Method m,
                             NameMangler non,
                             NameMangler fort)
    throws UnsupportedEncodingException
  {
    generateNamedMethod(symName, m.getLongMethodName(), 
                        Fortran.getMethodSuffix(d_context), non, fort);
    if ((Fortran.isFortran90(d_context) || 
         Fortran.hasBindC(d_context)) && m.hasRarray()) {
      generateNamedMethod(symName, m.getLongMethodName(), 
                          Fortran.getAltSuffix(d_context), non, fort);
    }
  }

  private void generateType(String symName,
                            NameMangler non,
                            NameMangler fort,
                            String suffix)
    throws UnsupportedEncodingException
  {
    String longName = non.shortName(symName, suffix);
    if (longName.length() > getMaxName()) {
      String shortName = fort.shortName(symName, suffix);
      writeEntry(longName, shortName);
    }
  }

  private void generateBindCDefine(SymbolID id) {
    if(Fortran.hasBindC(d_context)) {
      d_lw.writeCommentLine("Renaming stub symbols so they " +
                            "don't conflict with F90 bindings");
      String mod_name = Fortran.getModule(id, d_context);
      String type_name = Fortran.getTypeModule(id, d_context);
      String array_name = Fortran.getArrayModule(id, d_context);

      d_lw.pushLineBreak(false);
      d_lw.println("#ifdef __FORTRAN03__");
      d_lw.popLineBreak();
      writeLine(mod_name, mod_name + "_F03");
      writeLine(type_name, type_name + "_F03");
      writeLine(array_name, array_name + "_F03");
      d_lw.pushLineBreak(false);
      d_lw.println("#endif /*__FORTRAN03__*/");
      d_lw.popLineBreak();
      d_lw.println();
    }
  }
  
  private void generateExtendable(Extendable ext,
                                  NameMangler non,
                                  NameMangler fort)
    throws UnsupportedEncodingException, CodeGenerationException
  {
    SymbolID     id       = ext.getSymbolID();
    final String symName  = ext.getFullName();
    Iterator     i        = ext.getMethods(true).iterator();
    boolean      f90CBind = Fortran.isFortran90(d_context) 
                            || Fortran.hasBindC(d_context);
    String       mSuffix  = Fortran.getMethodSuffix(d_context);


    //For F2003, we use the C pre-processor to rename stub
    //symbols so they don't conflict with F90 bindings
    generateBindCDefine(id);
    
    while (i.hasNext()) {
      Method m = (Method)i.next();
      /*
       * WARNING:  Assumes pre and post hooks do NOT need send/recv
       * equivalents.
       */
      if ( (!m.isAbstract()) && (IOR.generateHookMethods(ext, d_context)) 
           && (!m.isBuiltIn()) ) {
        processMethod(symName, m.spawnPreHook(), non, fort);
        processMethod(symName, m, non, fort);
        processMethod(symName, m.spawnPostHook(false, false), non, fort);
      } else {
        processMethod(symName, m, non, fort);
      }
      if ((m.getCommunicationModifier() == Method.NONBLOCKING ) &&
          !d_context.getConfig().getSkipRMI()) {
        Method send = m.spawnNonblockingSend();
        processMethod(symName, send, non, fort);
        Method recv = m.spawnNonblockingRecv();
        processMethod(symName, recv, non, fort);
      }
    }
    Method bi;
    if (!d_context.getConfig().getSkipRMI()) {
      bi = IOR.getBuiltinMethod(IOR.EXEC,id, d_context,false);
      processMethod(symName, bi, non, fort);

      bi = IOR.getBuiltinMethod(IOR.GETURL,id, d_context,false);
      processMethod(symName, bi, non, fort);

      bi = IOR.getBuiltinMethod(IOR.ISREMOTE,id, d_context,false);
      processMethod(symName, bi, non, fort);

      bi = IOR.getBuiltinMethod(IOR.ISREMOTE,id, d_context,false);
      bi.setMethodName("_isLocal");
      processMethod(symName, bi, non, fort);
    }

    bi = IOR.getBuiltinMethod(IOR.HOOKS, id, d_context, false);
    processMethod(symName, bi, non, fort);

    boolean genContractBuiltins = IOR.generateContractBuiltins(ext, d_context);
    if (genContractBuiltins) {
      bi = IOR.getBuiltinMethod(IOR.CONTRACTS, id, d_context, false);
      processMethod(symName, bi, non, fort);
  
      bi = IOR.getBuiltinMethod(IOR.DUMP_STATS, id, d_context, false);
      processMethod(symName, bi, non, fort);
    }

    if (ext.hasStaticMethod(true)) {
      bi = IOR.getBuiltinMethod(IOR.HOOKS, id, d_context,true);
      processMethod(symName, bi, non, fort);

      /*
       * Need to deal with the overloading issue for static hooks method.
       */
      if (f90CBind) {
        generateNamedMethod(symName, bi.getLongMethodName(), "", non, fort);
      }

      if (genContractBuiltins) {
        bi = IOR.getBuiltinMethod(IOR.CONTRACTS, id, d_context, true);
        bi.setMethodName(Fortran.getAltBuiltinName(IOR.CONTRACTS, true));
        processMethod(symName, bi, non, fort);
        if (f90CBind) {
          generateNamedMethod(symName, bi.getLongMethodName(), "", non, fort);
        }

        bi = IOR.getBuiltinMethod(IOR.DUMP_STATS, id, d_context, true);
        bi.setMethodName(Fortran.getAltBuiltinName(IOR.DUMP_STATS, true));
        processMethod(symName, bi, non, fort);
        if (f90CBind) {
          generateNamedMethod(symName, bi.getLongMethodName(), "", non, fort);
        }
      }
    }

    processMethod(symName, 
                  Fortran.createCast(d_context, ext.getSymbolID()),
                  non, fort);
    processMethod(symName, 
                  Fortran.createCastTwo(d_context, ext.getSymbolID()),
                  non, fort);
    if (!ext.isAbstract()) {
      if (f90CBind) {
        generateNamedMethod(symName, "newRemote", mSuffix, non, fort);
        generateNamedMethod(symName, "newLocal", mSuffix, non, fort);
      }
      else {
        generateNamedMethod(symName, "_create", mSuffix, non, fort);
        generateNamedMethod(symName, "_createRemote", mSuffix, non, fort);
      }
    }

    if (!ext.isAbstract() && !IOR.isSIDLSymbol(id)) {
      if (f90CBind) {
        generateNamedMethod(symName, "wrapObj", mSuffix, non, fort);
      } else {
        generateNamedMethod(symName, "_wrapObj", mSuffix, non, fort);
      }
    }

    if (f90CBind) {
      generateNamedMethod(symName, "rConnect", mSuffix, non, fort);
    }
    else {
      generateNamedMethod(symName, "_connect", mSuffix, non, fort);
    }
    

    if (f90CBind) {
      final int maxArray = BabelConfiguration.getMaximumArray();
      if (!ext.isInterface()) {
        generateType(symName, non, fort, "_impl");
        generateType(symName, non, fort, "_priv");
        generateType(symName, non, fort, "_wrap");
      }
      generateType(symName, non, fort, "");
      for(int d = 1; d <= maxArray ; ++d) {
        generateType(symName, non, fort, "_" + Integer.toString(d) + "d");
      }
      generateType(symName, non, fort, "_array");
      generateType(symName, non, fort, "_t");
      generateType(symName, non, fort, "_type");
    }
  }

  public void generateCode(Symbol sym)
    throws CodeGenerationException
  {
    try {
      final String symName = sym.getFullName();
      NameMangler non = new NonMangler();
      NameMangler fort = new FortranMangler(getMaxName(), getMaxUnmangled());
      if(sym instanceof Extendable) {
        generateExtendable((Extendable)sym, non, fort);
        generateClassMethods(symName, non, fort, s_skelMethods);
      }
      else if(sym instanceof Enumeration) {
        generateBindCDefine(sym.getSymbolID());
      }
      generateMethods(symName, non, fort, s_arrayMethods);
    }
    catch (NoSuchAlgorithmException nsae) {
      throw new CodeGenerationException("NoSuchAlgorithmException: "
                                        + nsae.getMessage());
    }
    catch (UnsupportedEncodingException uee) {
      throw new CodeGenerationException("UnsupportedEncodingException: " +
                                        uee.getMessage());
    }
  }

  public static void generateCode(Symbol	sym,
                                  LanguageWriter writer,
                                  Context      context)
    throws CodeGenerationException
  {
    AbbrevHeader source = new AbbrevHeader(writer, context);
    source.generateCode(sym);
  }
}
