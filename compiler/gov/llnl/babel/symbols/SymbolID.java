//
// File:        SymbolID.java
// Package:     gov.llnl.babel.symbols
// Revision:    @(#) $Id: SymbolID.java 7188 2011-09-27 18:38:42Z adrian $
// Description: symbol ID consisting of a fully qualified name and version
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

import gov.llnl.babel.symbols.ASTNode;
import gov.llnl.babel.symbols.Version;


/**
 * The <code>SymbolID</code> class represents the name of a symbol,
 * which consists of a fully qualified name (e.g., "sidl.Object") and
 * a version number.  The <code>equals</code> and <code>hashCode</code>
 * members have been defined such that two symbol ids are considered
 * equal if they have the same version and string.
 */
public class SymbolID extends ASTNode implements Comparable {
   private String  d_full_name; // fully qualified name of the symbol
   private Version d_version;   // symbol version
   private boolean d_from_xml;  // Marks whether symbol originates from xml repo.

  public final static String SCOPE = ".";

   /**
    * The constructor for the <code>SymbolID</code> class takes a
    * fully qualified symbol name and a symbol version.
    */
   public SymbolID(String fully_qualified_name, Version version) {
      d_full_name = fully_qualified_name;
      d_version   = version;
   }

  public SymbolID(SymbolID id) {
    d_full_name = id.d_full_name;
    d_version = id.d_version;
    d_from_xml = id.d_from_xml;
  }

   /**
    * The constructor for the <code>SymbolID</code> class takes a
    * fully qualified symbol name and a symbol version.
    * Flag fromxml indicates whether symbol originates from xml repository.
    */
  public SymbolID(String fully_qualified_name, Version version, boolean fromxml) {
      d_full_name = fully_qualified_name;
      d_version   = version;
      d_from_xml  = fromxml;
   }
   /**
    * Return the fully qualified name of the symbol.
    */
   public String getFullName() {
      return d_full_name;
   }

   /**
    * Return the short, relative name of the fully qualified name.
    */
   public static String getShortName(String fqn) {
      int index = fqn.lastIndexOf(SCOPE);
      return fqn.substring(index+1);
   }

   /**
    * Return the short, relative name of the symbol.
    */
   public String getShortName() {
      return getShortName(d_full_name);
   }

   /**
    * Return the version of the symbol.
    */
   public Version getVersion() {
      return d_version;
   }

   /**
    * Return the symbol name of the form "NAME-vVERSION".
    */
   public String getSymbolName() {
      return d_full_name + "-v" + d_version.getVersionString();
   }

   /**
    * Return the hash code of the symbol name as the hash value for a
    * symbol identifier to facilitiate searching in hash tables.
    */
   public int hashCode() {
      return d_full_name.hashCode() + d_version.hashCode();
   }

   /**
    * Two symbol identifiers are identical only if they have the same
    * symbol identifiers and the same version.
    */
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      }
      if ((obj != null) && (obj instanceof SymbolID)) {
         SymbolID id = (SymbolID) obj;
         return ((d_full_name.equals(id.d_full_name))
              && (d_version.isSame(id.d_version)));
      }
      return false;
   }

  /**
   * Compare this <code>SymbolID</code> with another one.
   */
  public int compareTo(Object o)
  {
    SymbolID id = (SymbolID)o;
    return d_full_name.compareTo(id.d_full_name);
  }

  public boolean fromXML() {
    return d_from_xml;
  }
  
  public void setFromXML(boolean fromxml) {
    d_from_xml = fromxml;
  }
}
