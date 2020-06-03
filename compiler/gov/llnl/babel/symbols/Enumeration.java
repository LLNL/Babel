//
// File:        Enumeration.java
// Package:     gov.llnl.babel.symbols
// Revision:    @(#) $Id: Enumeration.java 7188 2011-09-27 18:38:42Z adrian $
// Description: sidl symbol representing a collection of enumerated types
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

package gov.llnl.babel.symbols;

import gov.llnl.babel.Context;
import gov.llnl.babel.symbols.Comment;
import gov.llnl.babel.symbols.Metadata;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The <code>Enumeration</code> class represents a collection of enumerated
 * symbols with specified integer values.  Values for enumeration symbols not
 * specified by the user are assigned values in an arbitrary fashion.
 */
public class Enumeration extends Symbol {
   private Set  d_assigned_values;
   private List d_enumerators;
   private Map  d_enumerator_comments;
   private int  d_next_unused;
   private Map  d_user_defined;
   private Map  d_values;

   /**
    * Create a new <code>Enumeration</code> with the specified symbol
    * identifier and comment.
    */
   public Enumeration(SymbolID id, Comment comment, Context context) {
      super(id, Symbol.ENUM, comment, context);
      d_assigned_values     = new HashSet();
      d_enumerators         = new ArrayList();
      d_enumerator_comments = new HashMap();
      d_next_unused         = 0;
      d_user_defined        = new HashMap();
      d_values              = new HashMap();
   }

   /**
    * Create a new <code>Enumeration</code> with the specified symbol
    * identifier, comment, and metadata.
    */
   public Enumeration(SymbolID id, Comment comment, Metadata metadata,
                      Context context) {
      super(id, Symbol.ENUM, comment, metadata, context);
      d_assigned_values     = new HashSet();
      d_enumerators         = new ArrayList();
      d_enumerator_comments = new HashMap();
      d_next_unused         = 0;
      d_user_defined        = new HashMap();
      d_values              = new HashMap();
   }

   /**
    * Add a new enumerator with a specified value.  The flag indicates
    * whether the value was assigned by the user or by the parser.
    * If a previous enumerator exists with the same name, then this 
    * enumerator will replace the previous one.
    */
   public void addEnumerator(String name, int value, boolean from_user, 
                             Comment comment) {
     checkFrozen();
      Integer ivalue = new Integer(value);
      d_assigned_values.add(ivalue);
      if (!d_user_defined.containsKey(name)) {
         d_enumerators.add(name);
      }
      d_enumerator_comments.put(name, comment);
      d_user_defined.put(name, Boolean.valueOf(from_user));
      d_values.put(name, ivalue);
   }

   /**
    * Add a new enumerator with a specified value.  If a previous enumerator 
    * exists with the same name, then this enumerator will replace the 
    * previous one.
    */
   public void addEnumerator(String name, int value, Comment comment) {
     checkFrozen();
      addEnumerator(name, value, true, comment);
   }

   /**
    * Add a new enumerator without a specified value.  If a previous
    * enumerator exists with the same name, then this enumerator will
    * replace the previous one.  This enumerator will be assigned a
    * value when its value is requested.
    */
   public void addEnumerator(String name, Comment comment) {
     checkFrozen();
      if (!d_user_defined.containsKey(name)) {
         d_enumerators.add(name);
      } else {
         d_values.remove(name);
         d_enumerator_comments.remove(name);
      }
      d_enumerator_comments.put(name, comment);
      d_user_defined.put(name, Boolean.valueOf(false));
   }

   /**
    * Add a new enumerator with a specified value.  The flag indicates
    * whether the value was assigned by the user or by the parser.
    */
   public void addEnumerator(String name, int value, boolean from_user) {
     checkFrozen();
      Integer ivalue = new Integer(value);
      d_assigned_values.add(ivalue);
      if (!d_user_defined.containsKey(name)) {
         d_enumerators.add(name);
      }
      d_user_defined.put(name, Boolean.valueOf(from_user));
      d_values.put(name, ivalue);
   }


   /**
    * Return whether the current enumeration contains an enumerator with
    * the specified name.
    */
   public boolean hasEnumerator(String name) {
      return d_user_defined.containsKey(name);
   }

   /**
    * Return the list of enumerators as a sorted list.  Each element of the
    * list is an element of the enumeration, each of which is a string.
    */
   public List getEnumerators() {
     return d_enumerators;
   }

   /**
    * Return an <code>Iterator</code> that will iterate over the elements
    * of the enumeration.  The iterator will iterate over the names of the
    * elements of the enumeration, each of which is a string.
    */
   public Iterator getIterator() {
     return d_enumerators.iterator();
   }

   /**
    * Return whether the specified enumerator name was defined by the
    * user (true) or given a value (false).  If the specified name is
    * not part of this enumerated collection, then return false.
    */
   public boolean definedByUser(String name) {
      Object obj = d_user_defined.get(name);
      return (obj == null ? false : ((Boolean) obj).booleanValue());
   }

   /**
    * Return the value for the specified enumerated name.  If the name
    * was not previously defined, then assign it a unique integer value.
    */
   public int getEnumeratorValue(String name) {
      int value = 0;
      if (d_values.containsKey(name)) {
         value = ((Integer) d_values.get(name)).intValue();
      } else {
         value = searchForFreeValue();
         d_values.put(name, new Integer(value));
      }
      return value;
   }

   /**
    * Return the comment, if any, for the specified enumerated name.  
    */
   public Comment getEnumeratorComment(String name) {
      Object obj = d_enumerator_comments.get(name);
      return (Comment) obj;
   }

   /**
    * Search for the next unused integer value for an enumerator.
    * Start at the next unused value and find the next integer that
    * does not already exist in the assigned values set.
    */
   private int searchForFreeValue() {
      int value = d_next_unused;
      while (d_assigned_values.contains(new Integer(value))) {
         value++;
      }
      d_next_unused = value + 1;
      return value;
   }

   /**
    * Return a null object that represents the external symbols referenced
    * by this enumerated type.
    */
   public Set getSymbolReferences() {
      return null;
   }

  public Set getAllSymbolReferences()
  {
    Set result = new HashSet();
    result.add(getSymbolID());
    return result;
  }

  /**
   * Return a null object that represents the external basic arrays
   * references by this enumerated type.
   */
  public Set getBasicArrayRefs() {
    return null;
  }

  public void freeze()
  {
    if (!d_frozen) {
      super.freeze();
      Iterator i = d_enumerator_comments.values().iterator();
      while (i.hasNext()) {
        Comment com = (Comment)i.next();
        if (com != null) {
          com.freeze();
        }
      }
      d_assigned_values = protectSet(d_assigned_values);
      d_enumerators = protectList(d_enumerators);
      d_enumerator_comments = protectMap(d_enumerator_comments);
      d_user_defined = protectMap(d_user_defined);
      d_values = protectMap(d_values);
    }
  }
}
