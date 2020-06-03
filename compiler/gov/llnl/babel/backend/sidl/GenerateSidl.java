//
// File:        GenerateSidl.java
// Package:     gov.llnl.babel.backend.sidl
// Revision:    @(#) $Revision: 7188 $
// Description: generate SIDL from the symbol table contents
//
// Copyright (c) 2002-2003, Lawrence Livermore National Security, LLC
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


package gov.llnl.babel.backend.sidl;

import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.CodeGenerator;
import gov.llnl.babel.backend.sidl.SidlSource;
import gov.llnl.babel.backend.writers.LanguageWriterForSidl;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.SymbolTable;
import gov.llnl.babel.symbols.SymbolUtilities;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;


/**
 * This class implements the {@link gov.llnl.babel.backend.CodeGenerator
 * CodeGenerator} interface for the SIDL code generator.  This generator 
 * creates a SIDL file for a package based on the contents of the symbol
 * table.
 */
public class GenerateSidl implements CodeGenerator
{

  private Context d_context = null;

  /**
   * Create a new instance.
   */
  public GenerateSidl() {
    
  }

  /**
   * If <code>symbol</code> is a top-level package, create a SIDL file for
   * it.
   *
   * @param symbol  a particlar symbol.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *  a catch all exception.
   */
  private void generateSource(Symbol symbol)
    throws CodeGenerationException 
  {
    PrintWriter sw = null;
    LanguageWriterForSidl sidlWriter;
    final int type = symbol.getSymbolType();
    switch (type){
    case Symbol.PACKAGE:
      SymbolID sid = symbol.getSymbolID();
      String pname = SymbolUtilities.getParentPackage(sid.getFullName());
      if (pname == null) {
        try {
          String sf = Sidl.getFileName(sid);
          sw        = d_context.getFileManager().createFile(sid,
                                                           Symbol.PACKAGE,
                                                           "SIDLSRCS", 
                                                           sf);
	  sidlWriter = new LanguageWriterForSidl(sw, d_context);
          SidlSource.generateCode(symbol, sidlWriter, d_context);
        }
        finally {
          if (sw != null) {
            sw.close();
          }
        }
      }
      break;
    }
  }
  
  /**
   * Given a set of symbol ids, this method will generate SIDL source
   * for all the corresponding packages in the set as needed.  
   *
   * @param symbols    a set of symbol id (symbol names) for whom stubs
   *                   should be written as needed.  Each object in the
   *                   set should be a {@link
   *                   gov.llnl.babel.symbols.SymbolID SymbolID}.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   */
  public void generateCode(Set symbols)
    throws CodeGenerationException 
  {
    Iterator i = symbols.iterator();
    SymbolTable table = d_context.getSymbolTable();
    while (i.hasNext()) {
      SymbolID id     = (SymbolID)i.next();
      Symbol   symbol = table.lookupSymbol(id);
      if (symbol != null) {
        generateSource(symbol);
      }
    }
  }

  /**
   * Return "text" since we are generating a textual representation of
   * the interface.
   */
  public String getType()
  {
    return "text";
  }

  /**
   * Returns false since we are not limited it to user symbols only.
   */
  public boolean getUserSymbolsOnly()
  {
    return false;
  }

  /**
   * Returns a set containing only "sidl" since we are only generating
   * the sidl representation of the interface.
   */
  public Set getLanguages()
  {
    Set result = new TreeSet();
    result.add("sidl");
    return result;
  }

  public void setName(String name)
    throws CodeGenerationException
 {
    if (! "sidl".equals(name)) {
      throw new CodeGenerationException
        ("\"" +name + "\" is not a valid name for the sidl generator.");
    }
  }

  public String getName() { return "sidl"; }

  public void setContext(Context context) {
    d_context = context;
  }
}
