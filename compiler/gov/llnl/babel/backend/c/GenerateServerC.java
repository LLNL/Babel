//
// File:        GenerateServerC.java
// Package:     gov.llnl.babel.backend.c
// Revision:    @(#) $Id: GenerateServerC.java 7188 2011-09-27 18:38:42Z adrian $
// Description: generate C server code based on a set of symbol identifiers
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
import gov.llnl.babel.backend.CodeSplicer;
import gov.llnl.babel.backend.c.C;
import gov.llnl.babel.backend.c.ImplHeader;
import gov.llnl.babel.backend.c.ImplSource;
import gov.llnl.babel.backend.c.SkelSource;
import gov.llnl.babel.backend.writers.LanguageWriterForC;
import gov.llnl.babel.backend.writers.LineCountingFilterWriter;
import gov.llnl.babel.backend.writers.LineRedirector;
import gov.llnl.babel.symbols.Class;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.SymbolTable;
import gov.llnl.babel.symbols.Type;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Class <code>GenerateServerC</code> is the main entry point for the C
 * server-side code generation routines.  This class generates the skeleton,
 * implementation header, and implementation source files.  It is assumed
 * that all symbols necessary to generate C code are available in the symbol
 * table (call <code>resolveAllReferences</code> before calling this routine).
 */
public class GenerateServerC implements CodeGenerator {

  private Context d_context = null;

   /**
    * The constructor does nothing interesting.  The entry point for
    * the <code>GenerateServerC</code> class is <code>generateCode</code>.
    */
   public GenerateServerC() { }

   /**
    * Generate C server-side code for each symbol identifier in the set
    * argument.  This routine assumes that all necessary symbol references
    * are available in the symbol table.  Files are only generated for
    * class symbol types.
    *
    * @param     symbols  the set of all symbols to be processed. Server
    *                     code will be generated for members of this
    *                     set that are <code>Class<code>es.
    * @exception gov.llnl.babel.backend.CodeGenerationException
    *    this is a catch all exception. It can be caused by I/O trouble or
    *    violations of the data type invariants.
    */
   public void generateCode(Set symbols) throws CodeGenerationException {
     SymbolTable table = d_context.getSymbolTable();
      for (Iterator s = symbols.iterator(); s.hasNext(); ) {
         SymbolID id     = (SymbolID) s.next();
         Symbol   symbol = table.lookupSymbol(id);
         if ((symbol != null) && (symbol.getSymbolType() == Symbol.CLASS)) {
            Class cls = (Class) symbol;
            generateSkeleton(cls);
            generateImplHeader(cls);
            generateImplSource(cls);
         }
      }
   }

   /**
    * Generate C skeleton source code for the specified class. The skeleton
    * code is the layer between the IOR and the developers implementation of
    * the class. It initializes the entry point vectors and provides methods
    * to access the object's private data pointer.
    *
    * @param     cls  the class for which a skeleton source 
    * @exception gov.llnl.babel.backend.CodeGenerationException
    *    this is a catch all exception. It can be caused by I/O trouble or
    *    violations of the data type invariants.
    */
   private void generateSkeleton(Class cls) throws CodeGenerationException {
      PrintWriter pw = null;
      try {
         String f = C.getSkelFile(cls);
         pw = d_context.getFileManager().createFile(cls, Type.CLASS, 
                                                   "SKELSRCS", f);
         LanguageWriterForC writer = new LanguageWriterForC(pw, d_context);

         SkelSource.generateCode(cls, writer, d_context);
      } finally {
         if (pw != null) {
            pw.close();
         }
      }
   }

   /**
    * Generate C implementation header code for the specified class.
    *
    * @param cls    the class for which an implementation header will
    *               be created.
    * @exception gov.llnl.babel.backend.CodeGenerationException
    *    this is a catch all exception. It can be caused by I/O trouble or
    *    violations of the data type invariants.
    */
   private void generateImplHeader(Class cls) throws CodeGenerationException {
      PrintWriter pw = null;
      try {
         String f = C.getImplHeaderFile(cls);
         CodeSplicer splicer = 
           d_context.getFileManager().getCodeSplicer(cls, Type.CLASS, f, false,
                                                    true);
         Writer fw = d_context.getFileManager().
           createWriter(cls, Type.CLASS, "IMPLHDRS", f);
         LineCountingFilterWriter lcfw = new LineCountingFilterWriter(fw);
         pw = new PrintWriter( lcfw );
         LanguageWriterForC writer = 
           new LanguageWriterForC( pw, lcfw, d_context );
         splicer.setLineRedirector( (LineRedirector) writer );
         ImplHeader.generateCode(cls, writer, splicer, d_context);
      } catch ( IOException ex ) { 
        throw new CodeGenerationException( ex.getMessage() );
      } finally {
         if (pw != null) {
            pw.close();
         }
      }
   }

   /**
    * Generate C implementation source code for the specified class.
    * Previous edits to the C implementation file are preserved in a
    * code splicer object and are spliced into the new file.
    *
    * @param cls    the class for which an implementation source file
    *               will be created.
    * @exception gov.llnl.babel.backend.CodeGenerationException
    *    this is a catch all exception. It can be caused by I/O trouble or
    *    violations of the data type invariants.
    */
   private void generateImplSource(Class cls) throws CodeGenerationException {
      PrintWriter pw = null;
      try {
         String f = C.getImplSourceFile(cls);
         CodeSplicer splicer = 
           d_context.getFileManager().getCodeSplicer(cls, Type.CLASS, f, true,
                                                    true);
         Writer fw = d_context.getFileManager().
           createWriter(cls, Type.CLASS, "IMPLSRCS", f);
         LineCountingFilterWriter lcfw = new LineCountingFilterWriter( fw );
         pw = new PrintWriter( lcfw );
         LanguageWriterForC writer = 
           new LanguageWriterForC(pw, lcfw, d_context);
         splicer.setLineRedirector( (LineRedirector) writer );
         ImplSource.generateCode(cls, writer, splicer, d_context);
      } catch ( IOException ex ) { 
        throw new CodeGenerationException( ex.getMessage() );
      } finally {
         if (pw != null) {
            pw.close();
         }
      }
   }

  public String getType() {
    return "skel";
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
