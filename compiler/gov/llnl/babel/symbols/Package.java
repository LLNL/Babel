//
// File:        Package.java
// Package:     gov.llnl.babel.symbols
// Revision:    @(#) $Id: Package.java 7188 2011-09-27 18:38:42Z adrian $
// Description: sidl symbol representing a package naming scope
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

package gov.llnl.babel.symbols;

import gov.llnl.babel.Context;
import gov.llnl.babel.symbols.Comment;
import gov.llnl.babel.symbols.Metadata;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.SymbolTable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The <code>Package</code> class describes a sidl package naming scope.
 * Along with the standard sidl identifier, metadata, and comment, it
 * contains a list of included symbols and their symbol type.
 */
public class Package extends Symbol {
  private List d_ordered_refs;
  private Map   d_references;
  

  /**
   * Create a new <code>Package</code> with the specified symbol identifier
   * and comment.
   */
  public Package(SymbolID id, Comment comment, Context context) {
    super(id, Symbol.PACKAGE, comment, context);
    d_ordered_refs = new ArrayList();
    d_references   = new HashMap();
  }

  /**
   * Create a new <code>Package</code> with the specified symbol identifier,
   * comment, and metadata.
   */
  public Package(SymbolID id, Comment comment, Metadata metadata, 
                 Context context) {
    super(id, Symbol.PACKAGE, comment, metadata, context);
    d_ordered_refs = new ArrayList();
    d_references   = new HashMap();
  }

  /**
   * Add a symbol to the list of local references for this package.  Note
   * that a single symbol may be added multiple times to the hash table.
   * The version of the symbol added to this package will be the same version
   * as the package itself.  The argument must be a fully qualified name.
   */
  public void addSymbol(SymbolID id, int type) {
    checkFrozen();
    d_references.put(id, new Integer(type));
    d_ordered_refs.add(id);
  }

  /**
   * Convert the specified short name into a fully qualified name within
   * this package scope.
   */
  public String getScopedName(String name) {
    return getFullName() + SymbolID.SCOPE + name;
  }

  /**
   * Return the symbols referenced by this package as a <code>Set</code> of
   * <SymbolID> objects.
   */
  public Set getSymbolReferences() {
    return d_references.keySet();
  }

  /**
   * 
   */
  public Set getAllSymbolReferences() {
    SymbolTable table = d_context.getSymbolTable();
    Set result = new HashSet();
    Set local = getSymbolReferences();
    result.addAll(local);
    Iterator i = local.iterator();
    while (i.hasNext()) {
      SymbolID id = (SymbolID)i.next();
      Symbol sym = table.lookupSymbol(id);
      result.addAll(sym.getAllSymbolReferences());
    }
    return result;
  }

  /**
   * Return the symbols referenced by this package in an ordered 
   * <code>Collection</code> of <SymbolID> objects.
   */
  public Collection getOrderedSymbolReferences() {
    return d_ordered_refs;
  }

  /**
   * Return an empty set.
   */
  public Set getBasicArrayRefs() {
    return new HashSet(0);
  }

  /**
   * Return the set of symbols referenced by this package.  The key in the
   * <code>Map</code> is a <code>SymbolID</code> and the value is its type
   * in integer form as defined in <code>Symbol</code>.
   */
  public Map getSymbols() {
    return d_references;
  }

  /**
   * Return <code>true</code> if and only if a package is final.
   * If a package is final, it is non-reentrant; a non-final package is
   * reentrant.
   */
  public boolean getFinal() {
    return hasAttribute("final");
  }

  public void setFinal(boolean isFinal)
  {
    if (isFinal) {
      setAttribute("final");
    }
    else if (getFinal()) {
      removeAttribute("final");
    }
  }

  public void freeze()
  {
    if (!d_frozen) {
      super.freeze();
      d_ordered_refs = protectList(d_ordered_refs);
      d_references = protectMap(d_references);
    }
  }
}
