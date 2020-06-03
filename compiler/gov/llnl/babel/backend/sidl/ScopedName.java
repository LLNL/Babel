//
// File:        ScopedName.java
// Package:     gov.llnl.babel.backend.sidl
// Revision:    @(#) $Revision: 7188 $
// Date:        $Date: 2011-09-27 11:38:42 -0700 (Tue, 27 Sep 2011) $
// Description: Try to figure out a reasonable set of import's/require's in
//              generating SIDL
//
// Copyright (c) 2003, Lawrence Livermore National Security, LLC
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
// 

package gov.llnl.babel.backend.sidl;

import gov.llnl.babel.Context;
import gov.llnl.babel.backend.writers.LanguageWriter;
import gov.llnl.babel.symbols.Package;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.SymbolTable;
import gov.llnl.babel.symbols.SymbolUtilities;
import gov.llnl.babel.symbols.Version;
import java.lang.Integer;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 * When generating SIDL from the information stored in the Symbol table, it
 * does not know what symbols where given as fully qualified names (fqn) and
 * what symbols where given as short names using an import or the current
 * scope to shorten the name. This class tries to provide a reasonable set
 * of <code>require</code> statements to give a meaningful file.
 */

public class ScopedName {
  /**
   * The delimiter used to separate the symbol and version in require entry
   * keys.
   */
  public static char s_delim = ' ';

  /**
   * The desired form of the version statement associated with the required
   * symbol entries that are to be output to the SIDL file in the form of
   * requires statements.
   */
  private static String s_require = "require";


  /**
   * The class is not (currently) intended for public instantiation.
   */
  private ScopedName() {
  }


  /**
   * Return the symbol-version string given a symbol and version.
   *
   * @param sym  the symbol
   * @param vers the version
   * @return     the concatenation of the symbol, delimiter, and version string
   */
  private static String buildSymVers(String sym, String vers) {
    return sym + s_delim + vers;
  }


  /**
   * Return the symbol-version string given a symbol.
   *
   * @param sym  the symbol
   * @return     the concatenation of the symbol, delimiter, and version string
   */
  private static String buildSymVers(Context context,
                                     String sym) 
  {
    Version  vers   = SymbolUtilities.getVersion(context, sym);
    String   vstr   = null;
    if (vers != null) {
      vstr = vers.getVersionString();
    } else {
      vstr = "0";  // for simplifying logic in print routine
    }
    return buildSymVers(sym, vstr);
  }


  /**
   * Return the value associated with the number of times the symbol was
   * encountered as a symbol and, if applicable, as a parent of another
   * symbol.
   *
   * @param symvers   the coupled symbol-version string
   * @param requires  the expanded requires map
   * @return          the number of valid occurrances of the symbol in the
   *                  original collection of symbols; otherwise, if not found,
   *                  returns -1.
   */
  private static int getOccurs(String symvers, TreeMap requires) 
  {
    int occurs = -1;	// = Not found
    if (requires.containsKey(symvers)) {
      Integer val = (Integer)requires.get((Object)symvers);
      occurs      = val.intValue();
    }
    return occurs;
  }


  /**
   * Return the determination of whether or not the parent of the specified
   * symbol-version pair is reportable as an import/require.
   *
   * @param symvers   the coupled symbol-version string
   * @param requires  the expanded requires map
   * @return          true if the parent is reportable; else false
   */
  private static boolean reportableParent(String symvers, TreeMap requires) 
  {
    boolean is_reportable = false;
    int     bpos          = symvers.indexOf(s_delim);
    if (bpos > 0) {
      int dpos = symvers.substring(0,bpos).lastIndexOf('.');
      if (dpos > 0) {
        String psymvers = buildSymVers(symvers.substring(0,dpos),
                                       symvers.substring(bpos+1));
        int    has      = getOccurs(psymvers, requires);
        if (has > 0) {  // Parent appeared in the collection of symbols
          is_reportable = true;
        } else {
          is_reportable = false;
        }
      }
    }
    return is_reportable;
  }


  /**
   * Build then print the require statements associated with the package.
   * The statements are output in alphabetical order, with the highest level
   * parent package with the same version as appears in the symbols associated
   * with the package.
   *
   * @param pkg  the package whose requires list is to be built
   * @param lw   the language writer to which the requires are to be written
   */
  public static void printRequires(Context context,
                                   Package pkg, LanguageWriter lw) 
  {
    SymbolTable stbl = context.getSymbolTable();
    String  pkgscope = SymbolUtilities.getOutermostPackage(pkg.getSymbolID().
                                                                getFullName());
    Symbol  opkg     = stbl.lookupSymbol(pkgscope);
    Set     symset   = opkg.getAllSymbolReferences();
    TreeMap requires = new TreeMap();
    if (!symset.isEmpty()) {
      for (Iterator iter = symset.iterator(); iter.hasNext();) {
        SymbolID sid    = (SymbolID)iter.next();
        String   fqn    = sid.getFullName();
        String   scope  = SymbolUtilities.getOutermostPackage(fqn);
        String   sym;
        if (stbl.lookupSymbol(sid).getSymbolType() == Symbol.PACKAGE) {
          sym    = fqn;
        } else {
          sym    = SymbolUtilities.getParentPackage(fqn);
        }

        if (  (!scope.equals(pkgscope)) 
           && (!scope.equals("sidl")) )  // Should grab from base class...
        {
          /*
           * Add the symbol, along with its version, to the requires.  It is
           * assumed the names of the symbols in the collection are unique.
           * Consequently, if a given symbol is encountered when it is added,
           * it is assumed the symbol has been added as the parent of one or 
           * more already encountered symbol(s).  Which means it is more 
           * desirable to print this parent symbol.
           */
          String symvers = buildSymVers(context, sym);

          if (!requires.containsKey(symvers)) {
            requires.put(symvers, new Integer(1));
          } else {
            int val = getOccurs(symvers, requires) + 1;
            requires.put(symvers, new Integer(val));
          }
  
          /*
           * Now add the parent of the symbol to the requires.  If present,
           * then we want to increment the number of times it's been 
           * encountered.  
           */
          String  psym    = SymbolUtilities.getParentPackage(sym);
          if (psym != null) {
            String psymvers = buildSymVers(context, psym);
            if (!requires.containsKey(psymvers)) {
              requires.put(psymvers, new Integer(0)); 
            } else {
              int pval = getOccurs(psymvers, requires);
              if (pval > 0) {
                requires.put(psymvers, new Integer(pval+1));
              }
            }
          }
        }
      }
    }

    /*
     * Now generate the necessary require statements.
     */
    if (!requires.isEmpty()) {
      Set impset = requires.entrySet();
      for (Iterator iter = impset.iterator(); iter.hasNext();) {
        Entry   e       = (Entry)iter.next();
        String  symvers = (String)e.getKey();
        int     dind    = symvers.indexOf(s_delim);
        String  sym     = symvers.substring(0,dind);
        String  vers    = symvers.substring(dind+1);
        String  vstr    = SymbolUtilities.getVersionString(vers);
        String  sttmt;
        if (vstr != null) {
          sttmt = s_require + " " + sym + " " + vstr + ";";
        } else {
          sttmt = s_require + " " + sym + ";";
        }
        Integer occurs  = (Integer)e.getValue();
        int     num     = occurs.intValue();
        if (!reportableParent(symvers, requires)) {
          if (num == 1) {   // child => print
            lw.println(sttmt);
          } else {          // parent => only print if multiple children
            if (num > 1) {
              lw.println(sttmt);
            }
          }
        }
      }
      lw.println();
    }
    requires.clear();
  }


  /**
   * Generate the scope sensitive name for a symbol. This method takes into
   * effect the <code>requires</code> list and the current scope.
   *
   * @param currentScope this is name of the scope in which you are writing.
   *                     For example, if you're writing the package
   *                     <code>gov.cca</code>, you would pass in "gov.cca".
   * @param fullyQualifiedName you want the short name for this fully
   *                          qualified name in the current scope and
   *                          given a set of <code>require</code> statements
   *                          printed at the beginning of the file.
   * @return a short name if a short name is possible; otherwise, this 
   * returns the fully qualified name. fullyQualifiedName.endsWith(return
   * value) should always be true.
   */
  public static String getScopedName(String currentScope,
                                     String fullyQualifiedName)
  {
    final int len = currentScope.length();
    if (fullyQualifiedName.startsWith(currentScope) &&
        (fullyQualifiedName.length() > len+1) &&
        (fullyQualifiedName.charAt(len) == SymbolID.SCOPE.charAt(0))) {
      return fullyQualifiedName.substring(len+1);
    }
    return fullyQualifiedName;
  }
}
