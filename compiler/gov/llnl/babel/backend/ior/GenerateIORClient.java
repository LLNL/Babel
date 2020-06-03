//
// File:        GenerateIORClient.java
// Package:     gov.llnl.babel.backend.ior
// Revision:    @(#) $Id: GenerateIORClient.java 7188 2011-09-27 18:38:42Z adrian $
// Description: generate IOR code based on a set of symbol identifiers
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

package gov.llnl.babel.backend.ior;

import gov.llnl.babel.BabelConfiguration;
import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.CodeGenerator;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.ior.IORHeader;
import gov.llnl.babel.backend.writers.LanguageWriterForC;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Class <code>GenerateIORClient</code> is the main entry point for the IOR
 * code generation routines.  This class calls the IOR header and source
 * file generation routines.  It is assumed that all symbols necessary
 * to generate IOR code are available in the symbol table, which can be
 * guaranteed by calling <code>resolveAllReferences</code> on the symbol
 * table.
 */
public class GenerateIORClient implements CodeGenerator {

  private Context d_context = null;

   /**
    * The constructor does nothing interesting.  The entry point for
    * the <code>GenerateIORClient</code> class is <code>generateCode</code>.
    */
   public GenerateIORClient() { }

   /**
    * Generate IOR code for each symbol identifier in the set argument.
    * This routine assumes that all necessary symbol references are available
    * in the symbol table.
    */
   public void generateCode(Set symbols) throws CodeGenerationException {
     for (Iterator s = symbols.iterator(); s.hasNext(); ) {
       SymbolID id     = (SymbolID) s.next();
       if (   BabelConfiguration.isSIDLBaseClass(id) 
          && !d_context.getConfig().generateStdlib() )
       {
         continue;
       }
       Symbol   symbol = d_context.getSymbolTable().lookupSymbol(id);
       if (symbol != null) {
         generateHeader(symbol);
       }
     }
   }

   /**
    * Generate IOR header code for the specified symbol.
    */
   private void generateHeader(Symbol symbol) throws CodeGenerationException {
      final int type = symbol.getSymbolType();
      final SymbolID id = symbol.getSymbolID();
      PrintWriter pw = null;
      try {
         String f = IOR.getHeaderFile(id);
         pw = d_context.getFileManager().
           createFile(id, type, "IORHDRS", f);
         LanguageWriterForC writer = new LanguageWriterForC(pw, d_context);
         IORHeader.generateCode(symbol, writer, d_context);
      } finally {
         if (pw != null) {
            pw.close();
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
    result.add("ior");
    return result;
  }

  public void setName(String name)
    throws CodeGenerationException
 {
    if (! "ior".equals(name)) {
      throw new CodeGenerationException
        ("\"" +name + "\" is not a valid name for the ior generator.");
    }
  }

  public String getName() { return "ior"; }

  public void setContext(Context context) {
    d_context = context;
  }
}
