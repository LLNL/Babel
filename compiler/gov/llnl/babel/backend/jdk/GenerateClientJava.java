//
// File:        GenerateClientJava.java
// Package:     gov.llnl.babel.backend.jdk
// Revision:    @(#) $Id: GenerateClientJava.java 7188 2011-09-27 18:38:42Z adrian $
// Description: generate Java client code based on a set of symbol identifiers
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

package gov.llnl.babel.backend.jdk;

import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.CodeGenerator;
import gov.llnl.babel.backend.jdk.ClientJNI;
import gov.llnl.babel.backend.jdk.ClientJava;
import gov.llnl.babel.backend.jdk.Java;
import gov.llnl.babel.backend.writers.LanguageWriterForC;
import gov.llnl.babel.backend.writers.LanguageWriterForJava;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.Struct;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Class <code>GenerateClientJava</code> is the main entry point for the Java
 * client-side code generation routines.  This class generates the Java files
 * and the C JNI files.  It is assumed that all symbols necessary to generate
 * Java code are available in the symbol table.
 */
public class GenerateClientJava implements CodeGenerator {

  private Context d_context = null;

  /**
   * The constructor does nothing interesting.  The entry point for
   * the <code>GenerateClientJava</code> class is <code>generateCode</code>.
   */
  public GenerateClientJava() {
  }

  /**
   * Generate Java client-side code for each symbol identifier in the set
   * argument.  This routine assumes that all necessary symbol references
   * are available in the symbol table.
   */
  public void generateCode(Set symbols) throws CodeGenerationException {
    for (Iterator s = symbols.iterator(); s.hasNext(); ) {
      SymbolID id = (SymbolID) s.next();
      Symbol symbol = d_context.getSymbolTable().lookupSymbol(id);
      if (symbol != null) {
        generateJava(symbol);
        generateJNI(symbol);
        generateHeader(symbol);
        generateUtilityFunctions(symbol);
      }
    }
  }

  /**
   * Generate Java code for the specified symbol.  Since Java code must follow
   * Java-style directory conventions, we must force the file manager to output
   * the Java code in a directory structure.  Reset the flags so that the other
   * C JNI code follows the default conventions.  We do not need to generate any
   * code for packages.
   */
  private void generateJava(Symbol symbol) throws CodeGenerationException {
    SymbolID id = symbol.getSymbolID();
    int type = symbol.getSymbolType();
    if ((type != Symbol.PACKAGE)) {
      PrintWriter pw = null;
      try {
        String file = Java.getClientJavaFile(id);
        boolean f = d_context.getFileManager().getJavaStylePackageGeneration();
        d_context.getFileManager().setJavaStylePackageGeneration(true);
        pw = d_context.getFileManager().createFile(id, type, "STUBJAVA", file);
        d_context.getFileManager().setJavaStylePackageGeneration(f);
        LanguageWriterForJava writer = new LanguageWriterForJava(pw, 
                                                                 d_context);
        ClientJava.generateCode(symbol, writer, d_context);
      } finally {
        if (pw != null) {
          pw.close();
        }
      }
    }
  }

  /**
   * Generate C JNI stub header code for the specified class or interface.  Stub
   * files are not generated for package symbols or enumerated types.
   */
  private void generateHeader(Symbol symbol) throws CodeGenerationException {
    final int type = symbol.getSymbolType();
    final SymbolID id = symbol.getSymbolID();
    if (symbol instanceof Extendable) {
      PrintWriter pw = null;
      try {
        String file = Java.getHeaderFile(id);
        pw = d_context.getFileManager().
          createFile(id, type, "STUBHDRS", file);
        LanguageWriterForC writer = new LanguageWriterForC(pw, d_context);
        StubHeader.generateCode((Extendable) symbol, writer, d_context);
      } finally {
        if (pw != null) {
          pw.close();
        }
      }
    }
  }

  /**
   * Generate C utility functions for struct support
   */
  private void generateUtilityFunctions(Symbol symbol) throws CodeGenerationException {
    if(symbol != null && symbol.getSymbolType() == Symbol.STRUCT) {
      Struct struct = (Struct) symbol;
      JavaStructSource codegen = new JavaStructSource(struct);
      codegen.generateUtilityHeader(d_context);
      codegen.generateUtilitySource(d_context);
    }
  }
  
  /**
   * Generate C JNI stub code for the specified class or interface.  Stub
   * files are not generated for package symbols or enumerated types.
   */
  private void generateJNI(Symbol symbol) throws CodeGenerationException {
    final int type = symbol.getSymbolType();
    final SymbolID id = symbol.getSymbolID();
    if (symbol instanceof Extendable) {
      PrintWriter pw = null;
      try {
        String file = Java.getClientJNIFile(id);
        pw = d_context.getFileManager().
          createFile(id, type, "STUBSRCS", file);
        LanguageWriterForC writer = new LanguageWriterForC(pw, d_context);
        ClientJNI.generateCode((Extendable) symbol, writer, d_context);
      } finally {
        if (pw != null) {
          pw.close();
        }
      }
    }
  }

  public String getType()
  {
    return "stub";
  }

  public boolean getUserSymbolsOnly()
  {
    return false;
  }

  public Set getLanguages()
  {
    Set result = new TreeSet();
    result.add("java");
    return result;
  }

  public void setName(String name)
    throws CodeGenerationException
 {
    if (!"java".equals(name)) {
      throw new CodeGenerationException
        ("\"" +name + "\" is not a valid name for the java generator.");
    }
  }

  public String getName() { return "java"; }

  public void setContext(Context context) {
    d_context = context;
  }
}
