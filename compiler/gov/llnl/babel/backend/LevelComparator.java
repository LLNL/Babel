//
// File:        LevelComparator.java
// Package:     gov.llnl.babel.backend
// Revision:    @(#) $Revision: 7188 $
// Date:        $Date: 2011-09-27 11:38:42 -0700 (Tue, 27 Sep 2011) $
// Description: Compare SymbolID's based on distance from the BaseException 
//              class
// 
// Copyright (c) 2000-2003, Lawrence Livermore National Security, LLC
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

package gov.llnl.babel.backend;

import gov.llnl.babel.BabelConfiguration;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Interface;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.SymbolTable;
import java.lang.Integer;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

public class LevelComparator implements Comparator {

  private SymbolTable d_table;
  private Interface   d_baseExceptionInterface;

  public LevelComparator(SymbolTable table) {
    d_table = table;
    d_baseExceptionInterface = (Interface)
      d_table.lookupSymbol(BabelConfiguration.getBaseExceptionInterface());
  }

  /**
   * Determine the maximum distance the extendable is from the specified
   * parent.
   *
   * @param  ext The <code>Extendable</code> being checked.
   * @param  par The desired parent <code>Extendable</code>.
   * @return max The maximum number of levels in the inheritance 
   *             hierarchy between the specified extendable and parent.
   *             If negative, then the extendable does NOT inherit from 
   *             the parent OR one or both parameters are null.
   */
  private final int getMaxLevel(Extendable ext, Extendable par) {
    int max = Integer.MIN_VALUE;
    if ( (ext != null) && (par != null) ) {
       if (ext == par) {
         max = 0;
       } else {
          Collection parents = ext.getParents(false);
          Iterator i = parents.iterator();
          while (i.hasNext()) {
            Extendable parent = (Extendable)i.next();
            if (par == parent) {
              max = 1;
            }
          }
          i = parents.iterator();
          while (i.hasNext()) {
             Extendable parent = (Extendable)i.next();
             int level = getMaxLevel(parent, par) + 1; 
             if (level > max) { 
               max = level;
             }
          }
       }
    }
    return max;
  }

  /**
   * Determine the maximum distance the extendable is from the base 
   * exception interface.
   *
   * @param  ext The <code>Extendable</code> being checked.
   * @return max The maximum number of levels in the inheritance 
   *             hierarchy between the specified extendable and parent.
   *             If negative, then the extendable does NOT inherit from 
   *             the the base exception interface OR the extendable is 
   *             null.
   */
  private final int getMaxExceptionLevel(Extendable ext) {
    return getMaxLevel(ext, d_baseExceptionInterface);
  }

  /**
   * Compare two <code>SymbolID</code>s to see which type is more refined.
   * An object is considered lesser if there are more types in the type
   * hierarchy between it and the base exception type than the
   * object it is being compared with.
   *
   * @param o1  this should be a <code>SymbolID</code> object.
   * @param o2  this should be a <code>SymbolID</code> object.
   * @return -1 if <code>o1</code> has more types in the type hierarchy
   *         between it and the base exception type than <code>o2</code>.
   *         1  if <code>o1</code> has fewer types in the type hierarchy
   *         between it and the base exception type than <code>o2</code>.
   *         Otherwise, 0 is returned.
   */
  public final int compare(Object o1, Object o2) {
    if ((o1 instanceof SymbolID) && (o2 instanceof SymbolID)) {
      Symbol sym1 = d_table.lookupSymbol((SymbolID)o1);
      Symbol sym2 = d_table.lookupSymbol((SymbolID)o2);
      if ((sym1 instanceof gov.llnl.babel.symbols.Extendable) &&
          (sym2 instanceof gov.llnl.babel.symbols.Extendable)) {
        int   l1 = getMaxExceptionLevel((Extendable)sym1);
        int   l2 = getMaxExceptionLevel((Extendable)sym2);
        return (l1 < l2) ? 1 : ((l1 > l2) ? -1 : 0);
      }
    }
    return 0;
  }

  /**
   * The following equals method does not make sense for a sort
   * comparator.  It always returns <code>false</code>.
   *
   * @param obj   ignored
   * @return <code>false</code>
   */
  public final boolean equals(Object obj) {
    return false;
  }
}
