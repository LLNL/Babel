//
// File:        SortComparator.java
// Package:     gov.llnl.babel.backend
// Revision:    @(#) $Id: SortComparator.java 7188 2011-09-27 18:38:42Z adrian $
// Description: a comparator for sorting a variety of object types
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

package gov.llnl.babel.backend;

import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.Symbol;
import java.util.Comparator;
import java.util.Map;

/**
 * The <code>SortComparator</code> class compares two objects of a variety
 * of concrete types for use in sorting.  Currently supported object types
 * are map entries, methods, strings, and symbols.  Map entries are sorted
 * on the key string value.  Methods are sorted by method names.  Strings
 * are sorted by string value.  Symbols are sorted by fully qualified name.
 */
public class SortComparator implements Comparator {
   /**
    * Compare two objects.  This method returns a negative one if o1 is
    * less than o2, 0 if they are the same, and 1 if o1 is greater than 02.
    */
   public int compare(Object o1, Object o2) {
      /*
       * Compare map entries with a string as first argument.
       */
      if ((o1 instanceof Map.Entry) && (o2 instanceof Map.Entry)) {
         Map.Entry m1 = (Map.Entry) o1;
         Map.Entry m2 = (Map.Entry) o2;
         Object k1 = m1.getKey();
         Object k2 = m2.getKey();
         if ((k1 instanceof String) && (k2 instanceof String)) {
            String s1 = (String) k1;
            String s2 = (String) k2;
            return s1.compareTo(s2);
         }
      }

      /*
       * Compare two methods and sort on method name.
       */
      if ((o1 instanceof Method) && (o2 instanceof Method)) {
         Method m1 = (Method) o1;
         Method m2 = (Method) o2;
         return m1.getLongMethodName().compareTo(m2.getLongMethodName());
      }

      /*
       * Compare two strings.
       */
      if ((o1 instanceof String) && (o2 instanceof String)) {
         String s1 = (String) o1;
         String s2 = (String) o2;
         return s1.compareTo(s2);
      }

      /*
       * Compare two symbol identifiers and sort on fully qualified name.
       */
      if ((o1 instanceof SymbolID) && (o2 instanceof SymbolID)) {
         SymbolID s1 = (SymbolID) o1;
         SymbolID s2 = (SymbolID) o2;
         return s1.getFullName().compareTo(s2.getFullName());
      }

      /*
       * Compare two symbols.
       */
      if ((o1 instanceof Symbol) && (o2 instanceof Symbol)) {
        Symbol s1 = (Symbol)o1;
        Symbol s2 = (Symbol)o2;
        return s1.getFullName().compareTo(s2.getFullName());
      }

      return 0;
   }

   /**
    * The following equals method does not make sense for a sort
    * comparator.  It always returns false.
    */
   public boolean equals(Object obj) {
      return false;
   }
}
