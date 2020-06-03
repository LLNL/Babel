//
// File:        Class.java
// Package:     gov.llnl.babel.symbols
// Revision:    @(#) $Id: Class.java 7421 2011-12-16 01:06:06Z adrian $
// Description: class representing sidl classes
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Class <code>Class</code> defines a sidl class.  Most of the basic
 * functionality for this class is implemented by <code>Extendable</code>.
 * This class adds the capability to be extended by a parent class.
 */
public class Class extends Extendable {
   private Class d_parent_class = null;

/** A list of interfaces this class implements-all*/
   private Map  d_implements_all;
   
/** Optional list of splicer blocks for the class */
   private SplicerList  d_splicers = null;

   /**
    * Create an empty <code>Class</code> object that will be constructed
    * by calls to other member functions.
    */
   public Class(SymbolID id, Comment comment, Context context) {
      super (id, Symbol.CLASS, comment, context);
      d_parent_class   = null;
      d_implements_all = new HashMap();
      d_splicers       = null;
   }

   /**
    * Create an empty <code>Class</code> object that will be constructed
    * by calls to other member functions.
    */
   public Class(SymbolID id, Comment comment, Metadata metadata, 
                Context context) {
      super(id, Symbol.CLASS, comment, metadata, context);
      d_parent_class = null;
      d_splicers     = null;
   }

   /**
    * Return whether this class is abstract.  A class is abstract if
    * and only if it has one or more abstract methods.
    */
   public boolean isAbstract() {
      return !getAbstractMethods().isEmpty();
   }
  
  public void setAbstractModifier(boolean abs) {
    checkFrozen();
    if (abs) {
      setAttribute("abstract");
    }
    else if (getAbstractModifier()) {
      removeAttribute("abstract");
    }
  }
   
   public boolean getAbstractModifier() {
     return hasAttribute("abstract");
   }
   
   /**
    * Return whether this object represents an interface (false).
    */
   public boolean isInterface() {
      return false;
   }
  
  /**
   * Return whether this class has any static methods.  If the flag
   * is true, then determines from all locally defined and parent methods.
   */
  public boolean hasStaticMethod(boolean all) {
    return !getStaticMethods(all).isEmpty();
  }
  
  /** This only adds the interface to the Implments-all map, it
   * does not actually implement the methods inside.  This must be done 
   * manually.
   * @param iface The Interface itself
   */
  public void addImplementsAll(Interface iface) {
    checkFrozen();
    d_implements_all.put(iface.getSymbolID(), iface);
  }
  
  public boolean hasImplmentsAll(SymbolID id) {
   return  d_implements_all.containsKey(id);
  }
  
  public Map getImplementsAllMap() {
    return  d_implements_all;
  }  
  
  public Interface getImplementsAll(SymbolID id){
   return  (Interface) d_implements_all.get(id);
  }
  
  /**
   *  This function returns all the methods that were defined in a parent
   *  class, and have been redefined locally.  It will not return methods
   *  that were abstract in the parent class, or methods that were inherited
   *  from an interface.
   */
  public Collection getOverwrittenClassMethods() {
    ArrayList overwrittenMethods = new ArrayList();
    if(d_parent_class != null) {
      //if we have a parent class, iterate over all the methods defined
      //locally.  If you find a method that is not abstract that exists in
      //the parent class, it is an overwritten method.
      
      List parentMethods = d_parent_class.getMethods(true);
      List localMethods = this.getMethods(false);
      for (Iterator loc = localMethods.iterator(); loc.hasNext(); ) {
        Method locM = (Method) loc.next();
        if(!locM.isAbstract()) {
          for (Iterator par = parentMethods.iterator(); par.hasNext(); ) {
            Method parM = (Method) par.next();
            if(!parM.isAbstract() && locM.sameSignature(parM))
              overwrittenMethods.add(locM);   
          }
        }
      }
    }
    return overwrittenMethods;
  }

  public boolean hasOverwrittenMethods() {
    return (getOverwrittenClassMethods().size() > 0);  
  }

   /**
    * Add a parent interface to this class.  This method does not check
    * that the interfaces and methods are compatible; it simply copies the
    * methods and interface parents and adds them to this interface.
    */
   public void addParentInterface(Interface parent) {
     checkFrozen();
     addParentData(parent);
   }

   /*
    * Set the parent class that this class extends.  This method does
    * not check that the class and its methods are compatible; it simply
    * copies methods and parent interfaces and adds them to this class.
    */
   public void setParentClass(Class parent) {
     checkFrozen();
     d_parent_class = parent;
     addParentData(parent);
   }

   /*
    * Return the parent class.  If no parent is defined, then the return
    * value is null.
    */
   public Class getParentClass() {
      return d_parent_class;
   }

  /**
   * Return the parent interfaces and parent class (if any) in a 
   * <code>Collection</code>.
   */
  public Collection getParents(boolean all)
  {
    Collection interfaces = getParentInterfaces(all);
    HashSet result = new HashSet(interfaces.size() + 1);
    Class parentClass = getParentClass();
    result.addAll(interfaces);
    if (parentClass != null) {
      result.add(parentClass);
      if (all) {
        while (parentClass != null) {
          parentClass = parentClass.getParentClass();
          if (parentClass != null) {
            result.add(parentClass);
          }
        }
      }
    }
    return result;
  }

  public int getDepth()
  {
    return (d_parent_class == null) 
      ? 0 
      : (d_parent_class.getDepth() + 1);
  }

   /**
    * Return whether this class has the specified parent class.  If the
    * boolean argument is true, then recursively search the inheritance
    * hierarchy.
    */
   public boolean hasParentClass(SymbolID id, boolean all) {
      if (d_parent_class != null) {
         if (d_parent_class.getSymbolID().equals(id)) {
            return true;
         } else if (all) {
            return d_parent_class.hasParentClass(id, true);
         }
      }
      return false;
   }

   /**
    * Add the specified contents to the splicer block identified by the
    * location and name.
    */
  public void addSplicerContents(String location, String name, String impl) {
    checkFrozen();
    if (d_splicers == null) {
      d_splicers = new SplicerList();
    }
    d_splicers.addSplicerContents(location, name, impl);
  }

  /**
   * Return splicer contents for the specified location and splicer name.
   */
  public List getSplicerContents(String location, String name) {
    List result = (d_splicers != null) 
      ? d_splicers.getSplicerContents(location, name) : null;
    if (result != null) {
      result = protectList(result);
    }
    return result;
  }

  /**
   * Return a list of splicer blocks for the specified location.
   */
  public List getSplicerBlocks(String location) {
    List result = (d_splicers != null) 
      ? d_splicers.getSplicerBlocks(location) 
      : null;
    if (result != null) {
      result = protectList(result);
    }
    return result;
  }

  public void freeze()
  {
    if (!d_frozen) {
      super.freeze();
      d_implements_all = protectMap(d_implements_all);
    }
  }
}
