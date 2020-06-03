//
// File:        Symbol.java
// Package:     gov.llnl.babel.symbols
// Revision:    @(#) $Id: Symbol.java 7188 2011-09-27 18:38:42Z adrian $
// Description: abstract base class for all symbols in a symbol table
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
import gov.llnl.babel.symbols.SymbolID;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * The <code>Symbol</code> abstract base class describes the basic
 * characteristics shared by all symbols in the symbol table.  All
 * SIDL symbols have a symbol identifier, a symbol type, a metadata
 * description, and an associated comment.  Four different types of
 * symbols are supported: package, enum, class, and interface.
 */
public abstract class Symbol extends SymbolID
  implements Comparable, Attributes, IMetadata {
  // these have to mesh with the constants in Type.java
  public final static int ENUM      = 11;// must be numerically least
  public final static int STRUCT    = 12;
  public final static int CLASS     = 13;
  public final static int INTERFACE = 14;
  public final static int PACKAGE   = 15;
  
  public final static String s_type[] = {
   "enum", "struct", "class", "interface", "package"
  };

  public final static String SCOPE = ".";

  private Comment  d_comment;
  private Metadata d_metadata;
  private int      d_symbol_type;
  private boolean  d_user_specified;
  private Map  d_attributes = new HashMap();

  protected Context d_context;

  /**
   * Initialize the <code>Symbol</code> abstact base class by providing
   * a symbol identifier and its type (one of CLASS, ENUM, INTERFACE, or
   * PACKAGE, or STRUCT).  The metadata is constructed using the current date and time.
   */
  public Symbol(SymbolID id, int type, Comment comment, Context context) {
    super(id);
    d_comment     = comment;
    d_metadata    = new Metadata(new Date());
    d_symbol_type = type;
    d_user_specified = false;
    d_context = context;
  }

  /**
   * Initialize the <code>Symbol</code> abstract base class by providing
   * a symbol identifier, a symbol type, a comment, and a metadata object.
   */
  public Symbol(SymbolID id, int type, Comment comment, Metadata metadata,
                Context context) {
    super(id);
    d_comment     = comment;
    d_metadata    = metadata;
    d_symbol_type = type;
    d_user_specified = false;
    d_context = context;
  }

  /**
   * Return the comment associated with this symbol.  The return argument
   * may be null if no comment was defined.
   */
  public Comment getComment() {
    return d_comment;
  }

  /**
   * Alter the comment.
   * @param comment the new comment.
   */
  public void setComment(Comment comment) {
    checkFrozen();
    d_comment = comment;
  }
   
  /** 
   * Indicate whether this is a user-specified symbol (or one resulting 
   * from dependency resolution)
   */
  public boolean getUserSpecified() {
      return d_user_specified;
  } 

  /**
   * Return how many levels of refinement this type is from a basic type.
   * For enumerations and packages, this returns zero. For classes,
   * it returns the distance from sidl.BaseClass. For interfaces, it
   * returns the distance from sidl.BaseInterface.
   */
  public int getDepth() {
    return 0;
  }

  /**
   * Return the minimum depth for a collection of symbols. 
   * @parameter symbols a collection of Symbol objects.
   *
   * @return This will always return a value greater or equal to zero.
   */
  public static int minimumDepth(Collection symbols)
  {
    if (symbols.size() == 0) return 0;
    int result = Integer.MAX_VALUE;
    Iterator i = symbols.iterator();
    while (i.hasNext()) {
      final int depth = ((Symbol)i.next()).getDepth();
      if (depth < result) result = depth;
    }
    return result;
  }

  /** 
   * Specify whether this symbol was given by the user on the Babel command line
   */
  public void setUserSpecified(boolean val) {
    d_user_specified = val;
  }

  /**
   * Return the metadata associated with this symbol.
   */
  public Metadata getMetadata() {
    return d_metadata;
  }

  /**
   * Add a (keyword,value) pair to the metadata for this symbol.
   */
  public void addMetadata(String keyword, String value) {
    checkFrozen();
    d_metadata.addMetadata(keyword, value);
  }

  /**
   * Return the identifier associated with this symbol.
   */
  public SymbolID getSymbolID() {
    return this;
  }

  /**
   * Return the type of this symbol, one of CLASS, ENUM, INTERFACE, or
   * PACKAGE.
   */
  public int getSymbolType() {
    return d_symbol_type;
  }

  /**
   * Return TRUE if the symbol is a package; otherwise, return FALSE.
   */
  public boolean isPackage() {
    return d_symbol_type == PACKAGE;
  }

  /**
   * Return TRUE if the type of this symbol is an interface; otherwise,
   * return FALSE.
   */
  public boolean isInterface() {
    return d_symbol_type == INTERFACE;
  }

  /**
   * Return TRUE if the symbol is a class; otherwise, return FALSE.
   */
  public boolean isClass() {
    return d_symbol_type == CLASS;
  }

  /**
   * Return TRUE if the symbol is a class; otherwise, return FALSE.
   */
  public boolean isStruct() {
    return d_symbol_type == STRUCT;
  }

  /**
   * Return the type string associated with this symbol.  The return argument
   * may be null if no type was defined.
   */
  public String getSymbolTypeString() {
    String s = null;
    if ( (ENUM <= d_symbol_type) && (d_symbol_type <= PACKAGE) ) {
      s = s_type[d_symbol_type - ENUM];
    }
    return s;
  }

  /**
   * Return the set of symbols (in the form of <code>SymbolID</code>)
   * referenced by this particular symbol.  The return argument may be
   * null if this symbol contains no external references.
   */
  public abstract Set getSymbolReferences();

  /**
   * Return the symbol references for the whole type hierarchy rooted 
   * with this symbol. For packages, this recurses down the package
   * hierarchy.
   */
  public abstract Set getAllSymbolReferences();

  /**
   * Return the set of all references to arrays of fundamental types.
   */
  public abstract Set getBasicArrayRefs();

  public boolean hasAttribute(String key)
  {
    return d_attributes.containsKey(key);
  }

  public String getAttribute(String key)
  {
    if (hasAttribute(key)) {
      return (String)d_attributes.get(key);
    }
    throw new UnknownAttributeException("Symbol " + getFullName() +
                                        " does not have attribute: " + key);
  }

  public void setAttribute(String key)
  {
    setAttribute(key, null);
  }

  public void setAttribute(String key, String value)
  {
    d_attributes.put(key, value);
  }

  public Set getAttributes()
  {
    return d_attributes.keySet();
  }

  public void removeAttribute(String key)
    throws UnknownAttributeException
  {
    checkFrozen();
    if (hasAttribute(key)) {
      d_attributes.remove(key);
    }
    else {
      throw new UnknownAttributeException("Symbol " + getFullName() + 
                                          " does not have attribute: " + key);
    }
  }

  public void freeze()
  {
    if (!d_frozen) {
      super.freeze();
      if (d_comment != null) d_comment.freeze();
      d_attributes = protectMap(d_attributes);
    }
  }
}
