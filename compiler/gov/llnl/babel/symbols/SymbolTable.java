//
// File:        SymbolTable.java
// Package:     gov.llnl.babel.symbols
// Revision:    @(#) $Id: SymbolTable.java 7188 2011-09-27 18:38:42Z adrian $
// Description: singleton symbol table of all cached sidl symbols
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

package gov.llnl.babel.symbols;

import gov.llnl.babel.symbols.AssertionException;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.SymbolNotFoundException;
import gov.llnl.babel.symbols.SymbolRedefinitionException;
import gov.llnl.babel.symbols.SymbolResolver;
import gov.llnl.babel.symbols.Version;
import gov.llnl.babel.BabelConfiguration;
import gov.llnl.babel.backend.CodeGenerationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * <code>SymbolTable</code> is a singleton class that represents the
 * locally cached SIDL symbols used during the parse of a SIDL file
 * or XML type descriptions.  Only one version of a particular symbol
 * may exist in the table, although different versions may exist in
 * the resolver search path.  A symbol may not be added to the table
 * if it already exists in the table.  A symbol may not be added to
 * the table if already exists in the <code>SymbolResolver</code>
 * search path with the same version.  The symbol table keeps a list
 * of modified symbols that must be written to a database at the end
 * of the parsing.
 */
public class SymbolTable implements SymbolResolver {

   private HashMap   d_fqn_to_id;       // map of fqn to symbol ids
   private HashSet   d_modified;        // names of new or modified symbols
   private ArrayList d_resolvers;       // collection of symbol resolvers
   private HashMap   d_table;           // symbol table as a hash table
  private boolean   d_input_from_sidl = false;
  private boolean   d_targetXML = false;

   /**
    * Create a new instance of a symbol table.  Although singleton classes
    * do not typically define a public constructor, this implementation does
    * so to support multiple symbol tables in the same application.  Most
    * implementations, however, will not directly create a symbol table
    * through the constructor and will instead use the one available in
    * the Context.
    */
   public SymbolTable() {
      d_fqn_to_id = new HashMap();
      d_modified  = new HashSet();
      d_resolvers = new ArrayList();
      d_table     = new HashMap();
   }

   /**
    * Return the current symbol table as a <code>Map</code>.  The keys
    * for the hash table are of type <code>SymbolID</code> and the values
    * are of type <code>Symbol</code>.
    */
   public Map getTable() {
      return d_table;
   }

   /**
    * Return the set of symbol names in the symbol table.  The set contains
    * the keys for the hash table as objects of type <code>SymbolID</code>.
    */
   public Set getSymbolNames() {
      return d_table.keySet();
   }

   /**
    * Return the set of symbol names in the symbol table that are new
    * or modified.  The set contains the symbol names as objects of type
    * <code>SymbolID</code>.
    */
   public Set getModifiedSymbolNames() {
      return d_modified;
   }

   /**
    * Mark a symbol in the symbol table as modified.  If the specified
    * symbol identifier does not exist in the symbol table, then this
    * routine does nothing.
    */
   public void markSymbolAsModified(SymbolID id) {
      if (d_table.containsKey(id)) {
         d_modified.add(id);
      }
   }

   /**
    * Look up a symbol in the symbol table based on the fully qualified
    * name.  If the symbol does not exist in the symbol table, then null
    * is returned.
    */
   public Symbol lookupSymbol(String fqn) {
      return (Symbol) d_table.get(d_fqn_to_id.get(fqn));
   }

   /**
    * Look up a symbol in the symbol table based on the fully qualified
    * name and the version number.  If the symbol does not exist in the
    * table with the same version number, then null is returned.
    */
   public Symbol lookupSymbol(SymbolID id) {
     if (id.getVersion().isUnspecified()) 
       return lookupSymbol(id.getFullName());
     return (Symbol) d_table.get(id);
   }

   /**
    * Add a new symbol resolver to the end of the list of current
    * resolvers.  The symbol resolvers will be searched in order of
    * addition to the symbol table resolver list.
    */
   public void addSymbolResolver(SymbolResolver resolver) {
      if (resolver != null) {
         d_resolvers.add(resolver);
      }
   }

  public void removeSymbolResolver(SymbolResolver resolver) {
    d_resolvers.remove(resolver);
  }

   /**
    * Resolve the specified symbol by fully qualified name and cache the
    * symbol in the symbol table.  This method will return the symbol with
    * the specified name if it already exists in the symbol table.  If the
    * symbol does not exist in the symbol table, then this method will search
    * through the resolver list for the symbol with the most recent version
    * number.  If no symbol is found, then null is returned.
    */
   public Symbol resolveSymbol(String fqn) {
      Symbol s = lookupSymbol(fqn);

      if (s == null) {

         /*
          * Search through the resolvers list for the largest version
          * that matches the symbol
          */
         for (Iterator i = d_resolvers.iterator(); i.hasNext(); ) {
            SymbolResolver resolver = (SymbolResolver) i.next();
            Symbol t = resolver.lookupSymbol(fqn);
            if (t != null) {
               if (s == null) {
                  s = t;
               } else {
                  Version sv = s.getSymbolID().getVersion();
                  Version tv = t.getSymbolID().getVersion();
                  if (tv.isGreaterThan(sv)) {
                     s = t;
                  }
               }
            }
         }

         /*
          * If we find a match, then add it to the symbol table.  There
          * is no possibility of a conflict, since we know that the symbol
          * cannot exist in the symbol table if we got here.
          */
         if (s != null) {
           SymbolID id = s.getSymbolID();
            d_fqn_to_id.put(fqn, id);
            d_table.put(id, s);
         }
      }

      return s;
   }

   /**
    * Resolve the specified symbol by fully qualified name and version number
    * and cache the symbol in the symbol table.  This method will return the
    * symbol with the specified ID if it already exists in the symbol table.
    * If a symbol exists in the symbol table with the same name but different
    * version, then a <code>SymbolRedefinitionException</code> is thrown.
    * If the symbol does not exist in the symbol table, then this method will
    * search through the resolver list for the symbol with the matching
    * version number.  If no symbol is found, then null is returned.
    */
   public Symbol resolveSymbol(SymbolID id)
         throws SymbolRedefinitionException {
     if (id.getVersion().isUnspecified()) 
       return resolveSymbol(id.getFullName());
      /*
       * If the symbol name exists in the symbol table, then make sure that
       * the version matches; otherwise, throw an exception.
       */
      Symbol s = lookupSymbol(id.getFullName());
      
      if (s != null) {
        if (!s.getSymbolID().getVersion().isSame(id.getVersion())) {
          throw new SymbolRedefinitionException(id, s.getSymbolID());
        } 
      } 
   
      
      /*
       * Search through the resolvers list for the symbol ID.  If we find
       * a match, then add it to the symbol table.  There is no possibility
       * of a version conflict, since we know that the symbol cannot exist
       * in the symbol table if we got here.
       */
      if (s == null) {
         for (Iterator i = d_resolvers.iterator(); i.hasNext(); ) {
            SymbolResolver resolver = (SymbolResolver) i.next();
            s = resolver.lookupSymbol(id);
            if (s != null) {
               d_fqn_to_id.put(id.getFullName(), id);
               d_table.put(id, s);
               break;
            }
         }
      }
      
      return s;
   }

   /**
    * Add a new symbol to the symbol table.  This method will throw a
    * <code>SymbolRedefinitionException</code> if the symbol already exists
    * in the symbol table with the same name or if the symbol exists in the
    * resolver search path with the same name and version.  This symbol
    * is also placed on the new or modified symbol list.
    */
   public void putSymbol(Symbol symbol) throws SymbolRedefinitionException {
      SymbolID id  = symbol.getSymbolID();
      String   fqn = id.getFullName();

      Symbol s1 = lookupSymbol(fqn);
      if (s1 != null) {
        if (conflictingSymbols(s1.getSymbolID(), symbol.getSymbolID())) {
          throw new SymbolRedefinitionException(id, s1.getSymbolID());
        }
      }

      Symbol s2 = resolveSymbol(id);
      if (s2 != null) {
        if (conflictingSymbols(s2.getSymbolID(), symbol.getSymbolID())) {
          throw new SymbolRedefinitionException(id, s2.getSymbolID());
        }
      }

      d_fqn_to_id.put(fqn, id);
      d_table.put(id, symbol);
      d_modified.add(id);
   }

   /**
    * Generate dependencies for the symbols in the input <code>Set</code>.
    * The input set contains a set of <code>SymbolID</code> symbol names.
    * All dependencies will be loaded into the symbol table and their
    * <code>SymbolID</code> names will be returned in the <code>Set</code>
    * return argument.  If the input argument is null or if there are no
    * dependencies, then the return argument will be null.
    */
   public Set generateDependencies(Set symbols)
         throws SymbolNotFoundException, SymbolRedefinitionException {
      Set dependencies = null;

      /*
       * Iterate over all symbols in the work list until the work list
       * is empty.
       */
      if ((symbols != null) && (!symbols.isEmpty())) {
         dependencies = new HashSet();
         LinkedList work = new LinkedList(symbols);
         while (!work.isEmpty()) {
            SymbolID id = (SymbolID) work.removeFirst();

            /*
             * Do not search dependencies if the identifier already exists
             * on the dependency list, which implies that it's already been
             * processed.  Otherwise, you could get into some gross infinite
             * loop problems if A depends on B and B depends on A.  Trust me.
             */
            if (!dependencies.contains(id)) {
               dependencies.add(id);
               Symbol symbol = resolveSymbol(id);
               if (symbol == null) {
                 throw new SymbolNotFoundException(id);
               }

               /*
                * Resolve all of the references and add them to the work
                * list if they are already not on the dependency list.
                */
               Set refs = symbol.getSymbolReferences();
               if (refs != null) {
                  for (Iterator i = refs.iterator(); i.hasNext(); ) {
                     SymbolID sid = (SymbolID) i.next();
                     if (!dependencies.contains(sid)) {
                        work.addLast(sid);
                     }
                  }
               }
            }
         }
      }

      return dependencies;
   }

   /**
    * Resolve all external symbol references for all symbols in the symbol
    * table.  If a symbol reference is not found, then an exception of the
    * type <code>SymbolNotFoundException</code> is thrown.  If a symbol
    * reference redefines a symbol with a different version number, then
    * a <code>SymbolRedefinitionException</code> is thrown.
    */
   public void resolveAllReferences()
         throws SymbolNotFoundException, SymbolRedefinitionException {
      generateDependencies(d_table.keySet());
      verifyReferences();
   }

  /**
   * Resolve the ancestor(s) of the specified symbol.
   */
  private void resolveParents(Symbol sym)
  {
    final String fullname = sym.getFullName();
    StringBuffer buf = new StringBuffer(fullname.length()+1);
    StringTokenizer tok = new StringTokenizer(fullname,".");
    while (tok.hasMoreTokens()) {
      buf.append(tok.nextToken());
      resolveSymbol(buf.toString());
      buf.append('.');
    }
  }

  /**
   * Resolve all external symbol references for all parents of symbols in
   * the symbol table.
   */
  public void resolveAllParents()
    throws SymbolNotFoundException
  {
    ArrayList symbols = new ArrayList(d_table.values());
    Iterator i = symbols.iterator();
    while (i.hasNext()) {
      resolveParents((Symbol)i.next());
    }
  }

   /**
    * Verify that any and all contracts that can be checked at this time
    * are valid.
    */
  public void validateContracts() throws AssertionException, 
     CodeGenerationException 
  {
    ArrayList symbols = new ArrayList(d_table.values());
    Iterator  i       = symbols.iterator();
    while (i.hasNext()) {
      Symbol sym = (Symbol)i.next();
      int    t   = sym.getSymbolType();
      if ( (t == Symbol.CLASS) || (t == Symbol.INTERFACE) ) {
        Extendable ext = (Extendable)sym;
        ext.validateContracts(false, false);
      }
    }
  }

   /**
    * Verify that all references in the table have been satisfied.  If
    * a symbol reference is not defined, then a symbol not found exception
    * is thrown.
    */
   public void verifyReferences() throws SymbolNotFoundException {
      for (Iterator i = d_table.values().iterator(); i.hasNext(); ) {
         Set refs = ((Symbol) i.next()).getSymbolReferences();
         if (refs != null) {
            for (Iterator r = refs.iterator(); r.hasNext(); ) {
               SymbolID id = (SymbolID) r.next();
               if (lookupSymbol(id) == null) {
                 throw new SymbolNotFoundException(id);
               }
            }
         }
      }
   }

  public boolean conflictingSymbols(SymbolID s1, SymbolID s2) {

    boolean conflict = false;

    if (s1.getVersion().isSame(s2.getVersion())) {
      boolean builtin = BabelConfiguration.isSIDLBaseClass(s1);
      if (! builtin ) {
        // If the current symbol is not builtin, and target language is not
        // xml OR the input is not a SIDL package (e.g., a sidl file),
        // then throw a symbol redefintion exception.
        if (! d_targetXML ) {
          if ( (s1.fromXML() || s2.fromXML()) &&
               d_input_from_sidl) {

            // The symbol originates from an xml repository and the 
            // conflicting symbol comes from a sidl file (not xml)
            
            conflict = true;
          }
        } 
      } 
    }

    return conflict;
  }

  public void setInputFromSIDL()
  {
    d_input_from_sidl = true;
  }

  public void setTargetIsXML(boolean value)
  {
    d_targetXML = value;
  }

  public void freezeAll()
  {
    Iterator i = d_table.values().iterator();
    while(i.hasNext()) {
      Symbol sym = (Symbol)i.next();
      sym.freeze();
    }
  }
}
