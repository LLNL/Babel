//
// File:        GenerateXML.java
// Package:     gov.llnl.babel.backend.xml
// Copyright:   (c) 2005 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Id: GenerateXML.java 7188 2011-09-27 18:38:42Z adrian $
// Date:        $Date: 2011-09-27 11:38:42 -0700 (Tue, 27 Sep 2011) $
// Description: generate XML into an XML repository
// 
// Copyright (c) 2005, Lawrence Livermore National Security, LLC
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

package gov.llnl.babel.backend.xml;

import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.CodeGenerator;
import gov.llnl.babel.parsers.xml.SymbolToDOM;
import gov.llnl.babel.repository.Repository;
import gov.llnl.babel.repository.RepositoryException;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.SymbolTable;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Class <code>GenerateXML</code> is the main entry point for the XML
 * backend. This code generates XML type descriptions for the
 * symbols on the command line.
 */
public class GenerateXML implements CodeGenerator {

  private Context d_context = null;

  /**
   * The constructor does nothing interesting.  The entry point for
   * the <code>GenerateXML</code> class is <code>generateCode</code>.
   */
  public GenerateXML() { }

  private boolean networkRepository() {
    final String outDir = 
      d_context.getConfig().getOutputDirectory();
    try {
      final URL url = new URL(outDir);
      String protocol = url.getProtocol();
      return "http".equals(protocol) || "https".equals(protocol);
    }
    catch (MalformedURLException mue) {
      return false;
    }
  }

  /**
   * Generate XML type descriptions for each symbol identifier in the set
   * argument.  This routine assumes that all necessary symbol references
   * are available in the symbol table.
   */
  public void generateCode(Set symbols) 
    throws CodeGenerationException 
  {
    if (networkRepository()) {
      try {
        Repository repo = d_context.getRepoFactory().
          createRepository((d_context.getConfig().
                            getOutputDirectory()));
        repo.writeSymbols(symbols);
      }
      catch (RepositoryException re) {
        throw new CodeGenerationException("Babel: FATAL: Unable to create output repository\n" +
                                          re.getMessage());
      }
    }
    else {
      for (Iterator s = symbols.iterator(); s.hasNext(); ) {
        SymbolTable table = d_context.getSymbolTable();
        SymbolID id     = (SymbolID) s.next();
        Symbol   symbol = table.lookupSymbol(id);
      
        if (symbol != null) {
          String xmlString = SymbolToDOM.convertToString(symbol, d_context);
          PrintWriter writer = null;
          try {
            writer = d_context.getFileManager().
              createFile(id, symbol.getSymbolType(), "XML",
                         id.getSymbolName() + ".xml");
            writer.println(xmlString);
          }
          finally {
            if (writer != null) {
              writer.close();
            }
          }
        }
      }
    }
  }

  public String getType() {
    return "text";
  }

  public boolean getUserSymbolsOnly() {
    return false;
  }

  public Set getLanguages() {
    Set result = new TreeSet();
    result.add("xml");
    return result;
  }

  public void setName(String name)
    throws CodeGenerationException
 {
    if (!"xml".equals(name)) {
      throw new CodeGenerationException
        ("\"" +name + "\" is not a valid name for the xml generator.");
    }
  }


  public String getName() { return "xml"; }

  public void setContext(Context context) {
    d_context = context;
  }
}
