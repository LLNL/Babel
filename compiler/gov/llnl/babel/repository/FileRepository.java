//
// File:        FileRepository.java
// Package:     gov.llnl.babel.repository
// Revision:    @(#) $Id: FileRepository.java 7188 2011-09-27 18:38:42Z adrian $
// Description: class for managing sidl symbol XML in a file repository
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

package gov.llnl.babel.repository;

import gov.llnl.babel.Context;
import gov.llnl.babel.parsers.xml.ParseSymbolException;
import gov.llnl.babel.parsers.xml.ParseSymbolXML;
import gov.llnl.babel.parsers.xml.SymbolToDOM;
import gov.llnl.babel.repository.Repository;
import gov.llnl.babel.repository.RepositoryException;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.Version;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.xml.sax.InputSource;

/**
 * The <code>FileRepository</code> class implements a simple repository
 * for sidl symbols using XML files stored in a single root directory.
 * Each symbol is mapped to a file in that directory of the generic form
 * NAME-vVERSION.xml, where NAME and VERSION are taken from the symbol
 * identifier.  This class does not cache the symbols in the file repository
 * directory because external writers may change the repository.
 */
public class FileRepository implements Repository {
   private File d_repository; // root directory of repository

  private Context d_context;

   /**
    * The constructor takes a file directory path to the repository.
    * If the path is not a directory or does not exist, then a repository
    * exception will be thrown by the constructor.
    */
   public FileRepository(String repository, Context context) 
     throws RepositoryException 
  {
    d_context = context;
      d_repository = new File(repository);
      if (!d_repository.isDirectory()) {
         throw new RepositoryException("File \""
            + repository
            + "\" is not a repository directory");
      }
   }

   /**
    * Look up the symbol based on the fully qualified name and version in
    * the file repository.  If the symbol name is not found, the versions
    * do not match, or there is a problem with the XML format, then null
    * is returned.  Note that since version 1.0 is the same as 1.0.0, we
    * need to construct all versions that match the name and then search
    * for the one equal to the symbol identifier.
    */
   public Symbol lookupSymbol(SymbolID id) {
     if (id.getVersion().isUnspecified()) 
       return lookupSymbol(id.getFullName());
      Symbol symbol = null;
      Set matching = findMatchingSymbols(id.getFullName());
      if (matching.contains(id)) {
         for (Iterator i = matching.iterator(); i.hasNext(); ) {
            SymbolID s = (SymbolID) i.next();
            if (id.equals(s)) {
               symbol = parseSymbolXML(s, d_context);
               break;
            }
         }
      }
      return symbol;
   }

   /**
    * Look up a symbol based on the fully qualified name and retrieve the
    * most recent version that matches the symbol name.  If the symbol name
    * is not found or the XML file is invalid, then null is returned.  Note
    * that if there is a format problem with the most recent version, this
    * routine will try older versions until a well-formed version is found.
    */
   public Symbol lookupSymbol(String fqn) {
      Symbol symbol = null;
      Set matching = findMatchingSymbols(fqn);
      while ((symbol == null) && (!matching.isEmpty())) {
         SymbolID id = getMostRecentVersion(matching);
         symbol = parseSymbolXML(id, d_context);
         if (symbol == null) {
            matching.remove(id);
         }
      }
      return symbol;
   }

   /**
    * Retrieve all symbols currently in the repository.  The symbols are
    * returned in a <code>Set</code> in which each set element is an object
    * of type <code>SymbolID</code>.  The return argument will not be null
    * but may contain no objects.
    */
   public Set getAllSymbols() {
      return findMatchingSymbols(null);
   }

   /**
    * Write the symbols in the <code>Set</code> from the symbol table to
    * the file repository.  Each set entry is a <code>SymbolID</code> of
    * the symbol to be written.
    */
   public void writeSymbols(Set symbol_names)
     throws RepositoryException 
  {
      for (Iterator i = symbol_names.iterator(); i.hasNext(); ) {
         writeSymbol((SymbolID) i.next());
      }
   }

   /**
    * Write a particular symbol to the file repository.  An exception
    * may be thrown if (1) the symbol does not exist in the symbol table
    * or (2) there is an error writing the output file.
    */
   private void writeSymbol(SymbolID id) throws RepositoryException {
      Symbol symbol = d_context.getSymbolTable().lookupSymbol(id);
      if (symbol == null) {
         throw new RepositoryException("Symbol \""
            + id.getSymbolName()
            + "\" not found in symbol table");
      }

      File file = new File(d_repository, id.getSymbolName() + ".xml");
      PrintWriter writer = null;
      try {
         writer = new PrintWriter(new FileWriter(file));
         writer.println(SymbolToDOM.convertToString(symbol, d_context));
      } catch (IOException ex) {
         throw new RepositoryException(ex.getMessage());
      } finally {
         if (writer != null) {
            writer.close();
         }
      }
   }

   /**
    * Find all symbol identifiers that match the specified string prefix.
    * If the previx is null, then all symbol identifiers are returned.
    * Each element of the <code>Set</code> is a <code>SymbolID</code>.
    */
   private Set findMatchingSymbols(String prefix) {
      Set symbols = new HashSet();
      String[] files = d_repository.list();
      if (files != null) {
         for (int f = 0; f < files.length; f++) {
            String s = files[f];
            int dash = s.indexOf("-v");
            if ((dash > 0) && s.endsWith(".xml")) {
               String name = s.substring(0, dash);
               String vers = s.substring(dash+2, s.length()-4);
               if ((prefix == null) || prefix.equals(name)) {
                  try {
                     symbols.add(new SymbolID(name, new Version(vers)));
                  } catch (NumberFormatException ex) {
                  }
               }
            }
         }
      }
      return symbols;
   }

   /**
    * Search the set of symbol identifiers and return the symbol with
    * the greatest version number.
    */
   private SymbolID getMostRecentVersion(Set symbols) {
      SymbolID s = null;
      for (Iterator i = symbols.iterator(); i.hasNext(); ) {
         SymbolID t = (SymbolID) i.next();
         if ((s == null) || (t.getVersion().isGreaterThan(s.getVersion()))) {
            s = t;
         }
      }
      return s;
   }

   /**
    * Parse the repository file corresponding to the symbol identifier and
    * return the corresponding symbol object.  Return null if any errors are
    * detected.
    */
   private Symbol parseSymbolXML(SymbolID id, Context context)
  {
      Symbol     symbol = null;
      FileReader reader = null;

      File file = new File(d_repository, id.getSymbolName() + ".xml");
      try {
         reader = new FileReader(file);
         try {             
           if (d_context.getConfig().isVerbose()) {
             System.out.println("Trying to parse " + file.getAbsolutePath());
           }
            symbol = ParseSymbolXML.convert(new InputSource(reader), context);
            if ( ! d_context.getConfig().suppressTimestamps() ) { 
              symbol.addMetadata("xml-url", file.getPath()); // path + file
                                                             // name
            }

         } catch (ParseSymbolException ex) {
           if (d_context.getConfig().isVerbose()) {
             ex.printStackTrace();
           }
            // do nothing
         }
      } catch (IOException ex) {
        if (d_context.getConfig().isVerbose()) {
          ex.printStackTrace();
        }
         // do nothing
      } finally {
         if (reader != null) {
            try {
               reader.close();
            } catch (IOException ex) {
               // do nothing
            }
         }
      }

      return symbol;
   }

  public boolean equals(Object obj)
  {
    if (obj instanceof FileRepository) {
      FileRepository repo = (FileRepository)obj;
      return d_repository.equals(repo.d_repository);
    }
    return false;
  }
}
