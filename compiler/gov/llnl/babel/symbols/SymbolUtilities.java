//
// File:        SymbolUtilities.java
// Package:     gov.llnl.babel.symbols
// Revision:    @(#) $Id: SymbolUtilities.java 7188 2011-09-27 18:38:42Z adrian $
// Description: a collection of simple symbol manipulation utilities
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
import gov.llnl.babel.BabelConfiguration;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.SymbolTable;
import gov.llnl.babel.symbols.Version;

/**
 * Utility class <code>SymbolUtilities</code> is a collection of common,
 * simple symbol manipulation functions.  All methods are declared as static.
 */
public class SymbolUtilities {

   /**
    * This class is not intended for public instantiation.
    */
   private SymbolUtilities() {
     // not intended for instantiation
   }

   /**
    * Return the outermost package prefix for the name (everything up to
    * but not including the first ".").
    */
   public static String getOutermostPackage(String fqn) {
      int index = fqn.indexOf(SymbolID.SCOPE);
      if (index < 0) {
         return fqn;
      } else {
         return fqn.substring(0, index);
      }
   }

   /**
    * Return the parent package for the name (everything up to but not
    * including the last ".".  If the name does not contain a parent,
    * then null is returned.
    */
   public static String getParentPackage(String fqn) {
      int index = fqn.lastIndexOf(SymbolID.SCOPE);
      if (index < 0) {
         return null;
      } else {
         return fqn.substring(0, index);
      }
   }


  /**
   * Return true if the symbol is not at the highest level (i.e., has 
   * parent package); otherwise, return false.
   *
   * @param id  the symbol id whose parentage is being checked
   * @return a boolean indicating if the symbol has a parent package or not
   */
  public static boolean hasParentPackage(SymbolID id) {
    boolean has_parent;
    if (getParentPackage(id.getFullName()) == null) {
      has_parent = false;
    } else {
      has_parent = true;
    }
    return has_parent;
  }


  /**
   * Return true if the symbol is at the highest level (i.e., has no
   * parent package); otherwise, return false.
   *
   * @param fqn  the fully qualified name of the symbol whose parentage is 
   *             being checked
   * @return a boolean indicating if the symbol has a parent package or not
   */
  public static boolean hasParentPackage(String fqn) {
    boolean has_parent;
    if (getParentPackage(fqn) == null) {
      has_parent = false;
    } else {
      has_parent = true;
    }
    return has_parent;
  }


  /**
   * Return true if the symbol has a parent with the same version number;
   * otherwise, return false.
   *
   * @param id   the symbol id of the symbol being checked
   * @return a boolean indicating if the symbol has a parent with same version
   */
  public static boolean sameVersionAsParent(Context context, SymbolID id) {
    boolean same_version = false;
    String  pname        = getParentPackage(id.getFullName());
    if ( pname != null ) {
      Symbol psym = context.getSymbolTable().lookupSymbol(pname);
      if ( psym != null ) {
        Version pvers = psym.getVersion();
        same_version  = pvers.isSame(id.getVersion());
      }
    }
    return same_version;
  }


  /**
   * Return true if the symbol has a parent with the same version number;
   * otherwise, return false.
   *
   * @param fqn   the fully qualified name of the symbol being checked
   * @return a boolean indicating if the symbol has a parent with same version
   */
  public static boolean sameVersionAsParent(Context context, String fqn) {
    boolean     same_version = false;
    SymbolTable symtable     = context.getSymbolTable();
    Symbol      ssym         = symtable.lookupSymbol(fqn);
    if ( ssym != null ) {
      String  pname = getParentPackage(fqn);
      if ( pname != null ) {
        Symbol psym = symtable.lookupSymbol(pname);
        if ( psym != null ) {
          Version pvers = psym.getSymbolID().getVersion();
          same_version  = pvers.isSame(ssym.getSymbolID().getVersion());
        }
      }
    }
    return same_version;
  }


  /**
   * Return the version of the symbol.
   */
  public static Version getVersion(Context context, String name) {
    Version vers = null;
    if ( name != null ) {
      Symbol sym = context.getSymbolTable().lookupSymbol(name);
      if ( sym != null ) {
        vers = sym.getVersion();
      }
    }
    return vers;
  }


  /**
   * Return the version associated with the parent package.
   *
   * @param fqn   the fully qualified name of the symbol being checked
   * @return the version associated with its parent package, if any
   */
  public static Version getParentVersion(String fqn, Context context) {
    return getVersion(context, getParentPackage(fqn));
  }


  /**
   * Return true if the specified symbol is one of the base sidl 
   * exception symbols; otherwise, return false.
   *
   * @param id  the symbol id of the symbol being checked
   * @return a boolean indicating if the symbol is a base exception symbol
   */
  public static boolean isBaseException(SymbolID id) {
    boolean is_base = false;
    String  name    = id.getFullName();

    if (  name.equals(BabelConfiguration.getBaseExceptionInterface())
       || name.equals(BabelConfiguration.getBaseExceptionClass()) ) {
      is_base = true;
    }
    return is_base;
  }


  /**
   * Return true if the specified symbol is one of the base sidl symbols;
   * otherwise, return false.
   *
   * @param id  the symbol id of the symbol being checked
   * @return a boolean indicating if the symbol is a base sidl symbol
   */
  public static boolean isBase(SymbolID id) {
    boolean is_base = false;
    String  name    = id.getFullName();

    if (  isBaseException(id)
       || name.equals(BabelConfiguration.getBaseClass())
       || name.equals(BabelConfiguration.getBaseInterface()) ) {
      is_base = true;
    }
    return is_base;
  }


  /**
   * Return the short name of the first symbol if it is in the same package
   * as the second symbol; otherwise, return its long name.  
   *
   * @param fid  the symbol id of the first symbol being checked.
   * @param sid  the symbol id of the second symbol being checked.
   */
  public static String getSymbolName(SymbolID fid, SymbolID sid) {
    String name   = null;
    String flname = fid.getFullName();

    if ( isBase(fid) ) {
      if ( isBase(sid) ) {
        name = fid.getShortName();
      } else {
        name = flname;
      }
    } else {
      String fpkg = getParentPackage(flname);
      String spkg = getParentPackage(sid.getFullName());
      if ( (fpkg != null) && (spkg != null) ) {
        if (fpkg.equals(spkg)) {
          name = fid.getShortName();
        } else {
          name = flname;
        }
      } else {
        name = flname;
      }
    }
    return name;
  }


  /**
   * Return the short name of the string holding the fully qualified name
   * if it is in the specified package; otherwise, return the fully qualified
   * name.  Returns null if either parameters are null.
   *
   * @param full_name  the fully qualified name being checked
   * @param pkg        the parent package name being used for comparison
   */
  public static String getSymbolName(String full_name, String pkg) {
    String name   = null;

    if ( (full_name != null) && (pkg != null) ) {
      String fpkg = getParentPackage(full_name);
      if (fpkg != null) {
        if (fpkg.equals(pkg)) {
          name = SymbolID.getShortName(full_name);
        } else {
          name = full_name;
        }
      } else {
        name = full_name;
      }
    }
    return name;
  }


  /**
   * Return the version string (i.e., "version " followed by the number) or
   * null if the version is exactly "0" based on the specified version string.
   *
   * @param vers the symbol whose version string is to be built
   * @return the expanded version string
   */
  public static String getVersionString(String vers) {
    String out  = null;
    if (! ((vers == null) || "0".equals(vers)) ) {
      out = "version " + vers;
    }
    return out;
  }


  /**
   * Return the version string (i.e., " version " followed by the number) or
   * null if the version is exactly "0" for the specified symbol.
   *
   * @param id the version whose expanded version string is to be built
   * @return the expanded version string
   */
  public static String getVersionString(SymbolID id) {
    return getVersionString(id.getVersion().getVersionString());
  }

}
