//
// File:        GenerateClientC.java
// Package:     gov.llnl.babel.backend.c
// Revision:    @(#) $Id: GenerateClientC.java 7188 2011-09-27 18:38:42Z adrian $
// Description: generate C client code based on a set of symbol identifiers
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

package gov.llnl.babel.backend.c;

import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.CodeGenerator;
import gov.llnl.babel.backend.c.C;
import gov.llnl.babel.backend.c.StubHeader;
import gov.llnl.babel.backend.c.StubSource;
import gov.llnl.babel.backend.writers.LanguageWriterForC;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.SymbolTable;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Class <code>GenerateClientC</code> is the main entry point for the C
 * client-side code generation routines.  This class generates the header
 * and the stub files.  It is assumed that all symbols necessary to generate
 * C code are available in the symbol table.
 */
public class GenerateClientC implements CodeGenerator {
  private Context d_context = null;

  /**
   * The constructor does nothing interesting.  The entry point for
   * the <code>GenerateClientC</code> class is <code>generateCode</code>.
   */
  public GenerateClientC() { }

  /**
   * Generate C client-side code for each symbol identifier in the set
   * argument.  This routine assumes that all necessary symbol references
   * are available in the symbol table.
   */
  public void generateCode(Set symbols) throws CodeGenerationException {
    SymbolTable table = d_context.getSymbolTable();
    for (Iterator s = symbols.iterator(); s.hasNext(); ) {
      SymbolID id     = (SymbolID) s.next();
      Symbol   symbol = table.lookupSymbol(id);
      
      if (symbol != null) {
          generateStubHeader(symbol);
          generateStubSource(symbol);
       }
    }
  }

  /**
   * Generate C (stub) header code for the specified symbol.
   */
  private void generateStubHeader(Symbol symbol) throws CodeGenerationException 
  {
    final int type = symbol.getSymbolType();
    PrintWriter pw = null;
    try {
      String f = C.getHeaderFile(symbol);
      pw       = d_context.getFileManager().createFile(symbol, type, "STUBHDRS", f);
      LanguageWriterForC writer = new LanguageWriterForC(pw, d_context);

      StubHeader.generateCode(symbol, writer, d_context);
    } finally {
      if (pw != null) 
      {
        pw.close();
      }
    }
  }

  /**
   * Generate C stub code for the specified class or interface.  Stub
   * files are not generated for package symbols or enumerated types.
   */
  private void generateStubSource(Symbol symbol) throws CodeGenerationException 
  {
    int type = symbol.getSymbolType();
    if (  (type == Symbol.CLASS) || (type == Symbol.INTERFACE) 
       || (type == Symbol.ENUM) || (type == Symbol.STRUCT)) {
      PrintWriter pw = null;

      try {
        String f = C.getStubFile(symbol);
        pw       = d_context.getFileManager().
          createFile(symbol, type, "STUBSRCS", f);
        LanguageWriterForC writer = new LanguageWriterForC(pw, d_context);

        StubSource.generateCode(symbol, writer, d_context);
      } finally {
        if (pw != null) {
          pw.close();
        }
      }
    }
  }

  public String getType() {
    return "stub";
  }

  public boolean getUserSymbolsOnly() {
    return true;
  }

  public Set getLanguages() {
    Set result = new TreeSet();
    result.add("c");
    return result;
  }

  public void setName(String name)
    throws CodeGenerationException
 {
    if (! "c".equals(name)) {
      throw new CodeGenerationException
        ("\"" +name + "\" is not a valid name for the c generator.");
    }
  }

  public String getName() { return "c"; }

  public void setContext(Context context) {
    d_context = context;
  }
}
