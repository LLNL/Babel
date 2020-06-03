//
// File:        Extendable.java
// Package:     gov.llnl.babel.symbols
// Revision:    @(#) $Id: Extendable.java 7481 2012-06-25 17:12:19Z adrian $
// Description: sidl base symbol for both classes and interfaces
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

import gov.llnl.babel.BabelConfiguration;
import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.Utilities;
import gov.llnl.babel.symbols.Assertion;
import gov.llnl.babel.symbols.AssertionException;
import gov.llnl.babel.symbols.Comment;
import gov.llnl.babel.symbols.Interface;
import gov.llnl.babel.symbols.Metadata;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class <code>Extendable</code> is a base class for SIDL symbols of type
 * class and interface.  It brings together common methods and implementation
 * for both final classes.  Class and interfaces have a set of methods and
 * interface inheritance in common.  Classes have the additional property
 * that they can be extended by another class; that functionality is not
 * defined here.  Many of the member functions take a boolean argument that
 * selects whether the method refers to this particular extendable only or
 * to this extendable and all of its parents in the SIDL inheritance system.
 * Constraints on the validity of methods added to this object must be checked
 * by the parser.
 *
 * Key design goals include providing mechanisms to:
 * - ensure method lookups in O(1) by both long and short names;
 * - ensure fast return of abstract, static, non-static, local, and both
 *   local and parent (or all) methods;
 * - ensure original ordering of methods preserved when returning lists;
 */
public abstract class Extendable extends Symbol {
  private List d_all_abstract_methods;
  private Set  d_all_basicarrays;
  private Map  d_all_interfaces;
  private List d_all_methods;
  private List d_all_methods_with_nonblocking;
  private Map  d_all_methods_long;
  private Map  d_all_methods_short;
  private Set  d_all_references;
  private List d_all_nonstatic_methods;
  private List d_all_static_methods;

  private Map  d_local_interfaces;
  private List d_local_methods;
  private List d_local_methods_with_nonblocking;
  private Map  d_local_methods_long;
  private Map  d_local_methods_short;
  private List d_local_nonstatic_methods;
  private List d_local_static_methods;

  //For from clauses, maps from new Method to old Method
  private Map  d_renamed_parent_methods; 
  //For from clauses, maps from old Method to it's enclosing SymbolID
  private Map  d_renamed_method_symbolID;
  /* 
   * Maps from the fully qualified name of the old method, to the 
   * complete new Method.
   */
  private Map  d_old_name_new_method;  
   
  private List d_inv_clause;	// Local invariant clause


  /**
   * Create an empty <code>Extendable</code> object that will be constructed
   * by calls to other member functions.
   *
   * @param  id       The symbol id of the new object
   * @param  type     The value of the type of the new object
   * @param  comment  The comment associated with the object
   */
  public Extendable(SymbolID id, 
                    int type, 
                    Comment comment, 
                    Context context) {
    super(id, type, comment, context);
    d_all_abstract_methods    = new ArrayList();
    d_all_basicarrays         = new HashSet();
    d_all_interfaces          = new HashMap();
    d_all_methods             = new ArrayList();
    d_all_methods_long        = new HashMap();
    d_all_methods_short       = new HashMap();
    d_all_references          = new HashSet();
    d_all_nonstatic_methods   = new ArrayList();
    d_all_static_methods      = new ArrayList();

    d_local_interfaces        = new HashMap();
    d_local_methods           = new ArrayList();
    d_local_methods_long      = new HashMap();
    d_local_methods_short     = new HashMap();
    d_local_nonstatic_methods = new ArrayList();
    d_local_static_methods    = new ArrayList();

    d_renamed_parent_methods  = new HashMap();
    d_renamed_method_symbolID = new HashMap();
    d_old_name_new_method     = new HashMap();

    d_inv_clause              = new ArrayList();

    d_all_references.add(id);
  }

  /**
   * Create an empty <code>Extendable</code> object that will be constructed
   * by calls to other member functions.
   *
   * @param  id       The symbol id of the new object
   * @param  type     The value of the type of the new object
   * @param  comment  The comment associated with the object
   * @param  m        Object metadata
   */
  public Extendable(SymbolID id, int type, Comment comment, 
                    Metadata m, Context context) 
  {
    super(id, type, comment, m, context);
    d_all_abstract_methods    = new ArrayList();
    d_all_basicarrays         = new HashSet();
    d_all_interfaces          = new HashMap();
    d_all_methods             = new ArrayList();
    d_all_methods_long        = new HashMap();
    d_all_methods_short       = new HashMap();
    d_all_references          = new HashSet();
    d_all_nonstatic_methods   = new ArrayList();
    d_all_static_methods      = new ArrayList();

    d_local_interfaces        = new HashMap();
    d_local_methods           = new ArrayList();
    d_local_methods_long      = new HashMap();
    d_local_methods_short     = new HashMap();
    d_local_nonstatic_methods = new ArrayList();
    d_local_static_methods    = new ArrayList();

    d_renamed_parent_methods  = new HashMap();
    d_renamed_method_symbolID = new HashMap();
    d_old_name_new_method     = new HashMap();

    d_inv_clause              = new ArrayList();

    d_all_references.add(id);
  }

  /**
   * Return TRUE if this object contains any abstract methods; otherwise, 
   * return FALSE.  A class is abstract if and only if it has any abstract 
   * methods.  An interface must always be abstract, even if it contains no 
   * methods.
   */
  abstract public boolean isAbstract();

  /**
   * Return TRUE if this object represents an interface, FALSE if it is a 
   * class.
   */
  abstract public boolean isInterface();

  /**
   * Add the specified new method to this object.  No checking is done whether 
   * this method is valid for this particular extendable.  The new method will 
   * over-write any existing method unless the new method is abstract.  The
   * references and arrays will also be cached in this object.
   *
   * @param  method  The method to be added to this object
   */
  public void addMethod(Method method) {
    checkFrozen();
    addToMethodLists(method, false);
    d_all_references.addAll(method.getSymbolReferences());
    d_all_basicarrays.addAll(method.getBasicArrays());
    try {
      /*
       * ToDo:  This should be added as an implicit exception but
       * more work is needed to get C++ to properly handle additional
       * implicit exceptions beyond RuntimeException.
       */
      if (hasInvClause(true)) {
        method.addThrows(Utilities.lookupSymbol(d_context, 
                BabelConfiguration.getInvariantViolation()).getSymbolID());
      }
    } catch (CodeGenerationException ex) {
      System.err.println("Babel: Warning: Unable to add sidl.InvViolation "
        + "as implicit exception for " + method.getLongMethodName() + ".");
    }
  }

  /** 
   * For From clauses:Add the specified "new" method to the normal method lists, and the
   * "old" method to the d_renamed_parent_methods HashMap.  newM and oldM
   * are assumed to have the same signatures and only differ by name.
   */
  public void addRenamedMethod(Method newM, Method oldM, SymbolID old_sid ) {
    checkFrozen();
    removeFromMethodLists(oldM);  //Remove the renamed method if it exists.
    addMethod(newM);
    d_renamed_parent_methods.put(newM, oldM);
    d_renamed_method_symbolID.put(oldM, old_sid);
    d_old_name_new_method.put(old_sid.getFullName() + "." 
                             + oldM.getLongMethodName(), newM);
  }
  

  /** 
   * For From clauses: Find the Parent Method Object for a renamed method.
   */
  public Method getRenamedMethod(Method newM) {
    return (Method)d_renamed_parent_methods.get(newM);
  }

  /** 
   * For From clauses: Get all the orginal Method Objects of all the renamed 
   * methods.
   */
  public Set getRenamedMethods() {
    return d_renamed_method_symbolID.keySet();
  }

  /** 
   * For From clauses: Get all the new method objects of all the renamed 
   * methods.
   */
  public Set getNewMethods() {
    return d_renamed_parent_methods.keySet();
  }


  /** 
   * For From clauses: Check a parent has a different name for this method.
   */
  public boolean methodWasRenamed(Method newM) {
    return d_renamed_parent_methods.get(newM) != null;
  }

  /** 
   * For From clauses: Get the old old method's enclosing symbolID. 
   */
  public SymbolID getRenamedMethodSymbolID(Method oldM) {
    return (SymbolID) d_renamed_method_symbolID.get(oldM);
  }

  /** 
   * For From clauses: Take the FQN of the renamed method and return the new
   * Method.
   */
  public Method getNewMethod(String oldM_name) {
    return (Method) d_old_name_new_method.get(oldM_name);
  }

  /**
   * Return the number of methods associated with this extendable.
   *
   * @param  all  If TRUE, then return the number of local and parent methods; 
   *              otherwise, return the number of local methods only.
   */
  public int getNumberOfMethods(boolean all) {
    return all ? d_all_methods.size() : d_local_methods.size();
  }

  /**
   * Returns the number of this Extendable's methods with contract clauses.
   */
  public int getNumberOfMethodsWithContracts() throws CodeGenerationException 
  {
    int numMethods = 0;
    if (hasInvClause(true)) {
      numMethods = d_all_methods.size();
    } else {
      for (Iterator i = getMethods(true).iterator(); i.hasNext(); ) {
        Method method = (Method) i.next();
        if (  method.hasPreClause() || method.hasPostClause() ) {
          numMethods += 1;
        }
      }
    }
    return numMethods;
  }

  /**
   * Return the methods in this interface as a <code>Collection</code>.
   * Each element in the collection is of type <code>Method</code>.
   *
   * @param  all  If TRUE, then return local and parent methods; otherwise,
   *              return only local methods.
   */
  public List getMethods(boolean all) {
    return all ? d_all_methods : d_local_methods;
  }

  /**
   * Return the methods in this interface as a <code>Collection</code>.
   * Each element in the collection is of type <code>Method</code>.
   * This list includes methods NONBLOCKING_SEND and NONBLOCKING_RECV
   * methods spawned in response to NONBLOCKING methods.
   *
   * @param  all  If TRUE, then return local and parent methods; otherwise,
   *              return only local methods.
   */
  public List getMethodsWithNonblocking(boolean all) 
    throws CodeGenerationException 
  {
    /*
     * TBD:  Ask Tom if the intent is to really remove/replace existing
     * entries.  If not, then need to consider a side effect-free version 
     * of findExistingEntry().
     */
    if (d_all_methods_with_nonblocking == null) 
    {
      d_all_methods_with_nonblocking = new ArrayList();
      d_local_methods_with_nonblocking = new ArrayList();
      for (Iterator allMethods = d_all_nonstatic_methods.iterator(); 
          allMethods.hasNext();) 
      {
        Method method = (Method) allMethods.next();

        findExistingEntry(d_all_methods_with_nonblocking, method, true);
        if (method.getCommunicationModifier() == Method.NONBLOCKING) {
          Method send = method.spawnNonblockingSend();
          Method recv = method.spawnNonblockingRecv();
          findExistingEntry(d_all_methods_with_nonblocking, send, true);
          findExistingEntry(d_all_methods_with_nonblocking, recv, true);
        }
      }
      for (Iterator localMethods = d_local_nonstatic_methods.iterator(); 
          localMethods.hasNext();) 
      {
        Method method = (Method) localMethods.next();
        
        findExistingEntry(d_local_methods_with_nonblocking, method, true);
        if (method.getCommunicationModifier() == Method.NONBLOCKING) {
          Method send = method.spawnNonblockingSend();
          Method recv = method.spawnNonblockingRecv();
          findExistingEntry(d_local_methods_with_nonblocking, send, true);
          findExistingEntry(d_local_methods_with_nonblocking, recv, true);
        }
      }
    }
    return all ? d_all_methods_with_nonblocking 
               : d_local_methods_with_nonblocking;
  }


  /**
   * Return the <code>Collection</code> non-static methods in this interface.
   * Each element in the collection is of type <code>Method</code>.
   *
   * @param  all  If TRUE, then return local and parent non-static methods; 
   *              otherwise, return only local non-static methods.
   */
  public List getNonstaticMethods(boolean all) {
    return all ? d_all_nonstatic_methods
      : d_local_nonstatic_methods;
  }

  /**
   * Return the static methods in this interface as a <code>Collection</code>.
   * Each element in the collection is of type <code>Method</code>.
   *
   * @param  all  If TRUE, then return local and parent static methods; 
   *              otherwise, return only local static methods.
   */
  public List getStaticMethods(boolean all) {
    return all ? d_all_static_methods
      : d_local_static_methods;
  }

  /**
   * Return the abstract methods for this class or interface, which
   * includes all parent classes and interfaces.  Each element in the
   * collection is of type <code>Method</code>.
   */
  public List getAbstractMethods() {
    return d_all_abstract_methods;
  }

  /**
   *  Return all abstract methods and all local methods (both abstract and 
   *  concrete).
   *  This is a special case for Java abstract classes, although maybe it
   *  will be useful elsewhere.
   */
  public List getAbstractAndLocalMethods() {
    LinkedList ret = new LinkedList(d_local_methods);
    //Add all Abstracts that don't collide with locals
    for (Iterator abs = d_all_abstract_methods.iterator(); abs.hasNext(); ) {
      Method absM = (Method) abs.next();
      for (Iterator local = d_local_methods.iterator(); local.hasNext(); ) {
        Method locM = (Method) local.next();
        if (absM == locM)
          break;
        if (!local.hasNext())
          ret.add(absM);
      }
    }
    return ret;
  }
    

  /**
   * Return <code>true</code> if and only if the method given is locally
   * defined in this extendable.
   *
   * @param  m  The method of interest.
   * @return <code>true</code> if <code>m</code> is locally defined
   *         in this Extendable.
   */
  public boolean isLocal(Method m)
  {
    return (m != null) &&
      m.equals(d_local_methods_long.get(m.getLongMethodName()));
  }

  /**
   * Return the <code>Method</code> with the specified long method name.  
   * If there is none, return null.
   *
   * @param  name  The short method name for the method to be located.
   * @param  all   If TRUE then all local and parent methods are to 
   *               be searched; otherwise, only local methods are to
   *               be searched.
   */
  public Method lookupMethodByLongName(String name, boolean all) {
    return (Method) (all ? d_all_methods_long.get(name) 
                     : d_local_methods_long.get(name));
  }

  
  /**
   * Return the <code>Collection</code> of methods, each in <code>Method</code>
   * form, that are found to have the specified short method name, if any.  
   * If none are found, then return null.
   *
   * @param  name  The short method name for the method to be located.
   * @param  all   If TRUE then all local and parent methods are to 
   *               be searched; otherwise, only local methods are to
   *               be searched.
   */
  public Collection lookupMethodByShortName(String name, boolean all) {
    return protectCollection((Collection)
                             (all ? d_all_methods_short.get(name)
                              : d_local_methods_short.get(name)));
  }

  /**
   * Return TRUE if the specified method exists by long name; otherwise,
   * return FALSE.
   *
   * @param  name  The long method name for the method to be located.
   * @param  all   If TRUE then all local and parent methods are to 
   *               be searched; otherwise, only local methods are to
   *               be searched.
   */
  public boolean hasMethodByLongName(String name, boolean all) {
    return all ? d_all_methods_long.containsKey(name) 
      : d_local_methods_long.containsKey(name);
  }

  /**
   * Return TRUE if the specified method exists by short name; otherwise,
   * return FALSE.  Recall there may be multiple methods with the same short 
   * name but here we only care if there is at least one.
   *
   * @param  name  The short method name to be located
   * @param  all   If TRUE, then all local and parent methods are to be 
   *               searched; otherwise, only local methods are to be 
   *               searched
   */
  public boolean hasMethodByShortName(String name, boolean all) {
    return all ? d_all_methods_short.containsKey(name) 
               : d_local_methods_short.containsKey(name);
  }

  /**
   * Return TRUE if any of the methods are static methods; otherwise,
   * return FALSE.
   *
   * @param  all  If TRUE, then local and parent methods are to be searched;
   *              otherwise, only local methods are to be searched.
   */
  abstract public boolean hasStaticMethod(boolean all);

  /**
   * Return TRUE if any of the methods throws an exception; otherwise, return
   * FALSE.  
   *
   * @param  all  If TRUE, then local and parent methods are to be searched;
   *              otherwise, only local methods are to be searched.
   */
  public boolean hasExceptionThrowingMethod(boolean all) {
    boolean has_exception_throwing_method = false;
     
    for (Iterator i = getMethods(all).iterator(); i.hasNext(); ) {
      Method method = (Method) i.next();
      if (method.getThrows().size()>0) { 
        has_exception_throwing_method = true;
        break;
      }
    }
    return has_exception_throwing_method;
  }

  /**
   * Add a new parent interface to this object.  This method will be
   * implemented by the <code>Class</code> and <code>Interface</code>
   * subclasses.
   *
   * @param  parent  The parent interface to be added to this object.
   */
  abstract public void addParentInterface(Interface parent);

  /**
   * Return true if this extendable inherits from interfaces. 
   * BaseInterface does not count for this test.
   *
   */
  public boolean hasParentInterfaces() {
    return (d_all_interfaces.size() > 1);
  }

  /**
   * Return the parent interfaces in a <code>Collection</code>.  Each
   * member of the collection is an <code>Interface</code>.  
   *
   * @param  all  If TRUE, then all parents are to be returned; otherwise,
   *              only direct parents are to be returned.
   */
  public Collection getParentInterfaces(boolean all) {
    return all ? d_all_interfaces.values() 
               : d_local_interfaces.values();
  }

  /**
   * Return the parent interfaces and/or classes in hierarchical order
   * from top-most down to direct parents in an <code>List</code>.  
   * Each member of the list is an <code>Extendable</code> and there
   * are NO duplicates.
   * Does a DFS to return ancestors in "reverse" topolgical order.
   * by Jim Leek
   */
  public List getAllParentsInOrder() throws CodeGenerationException {
    LinkedList ancestors = new LinkedList();
    HashSet visited = new HashSet();
    visited.add(this);  //We actually don't want this in the ancestor list.

    getAllParentsInOrderHelper(this, ancestors, visited);
    return ancestors;
  }

  /**
   * getAllParentsInOrderHelper is a recursive DFS call.  It is started
   * by getAllParentsInOrder.  It returns the post order list of ancestors.
   * (a "reverse" topological sort)
   */
  private void getAllParentsInOrderHelper(Extendable current, List ancestors, 
                                          Set visited) 
    throws CodeGenerationException 
  {
    Collection parents = Utilities.sort(current.getParents(false));

    for (Iterator pIter = parents.iterator(); pIter.hasNext();) {
      Extendable parent = (Extendable) pIter.next();
      if (!visited.contains(parent)) {
        getAllParentsInOrderHelper(parent, ancestors, visited);
      }
    }
    
    //Keep "this" out of the ancestor list
    if (!visited.contains(current)) 
    {
      //This node sould never have been visited before, assert that.
      if (!ancestors.contains(current)) {
        ancestors.add(current); 
        visited.add(current);  //Finished visiting this node
      } else {
        throw new CodeGenerationException 
          ("Extendable: " + getFullName() + " Had ancestor " + 
           current.getFullName() + " appear in the ancestor list twice.");
      }
    }
  }

  /**
   * Return the parent interfaces and/or class in a <code>Collection</code>.
   * Each member of the collection is an <code>Extendable</code>.  No 
   * duplicate entries are returned in the collection.
   *
   * @param  all  If TRUE, all ancestors are to be returned; otherwise, only
   *              direct parents.  
   */
  public Collection getParents(boolean all) {
    return protectCollection(getParentInterfaces(all));
  }

  /**
   * Return TRUE if this object implements or extends the specified 
   * <code>Extendable</code> directly or indirectly.  Otherwise, return 
   * FALSE.
   *
   * @param  ext  The potential ancestor
   */
  public boolean hasAncestor(Extendable ext)
  {
    if (ext == null) return false;
    Extendable parent  = null;
    Collection parents = getParents(false);
    for (Iterator i = parents.iterator(); i.hasNext(); ) {
      parent = (Extendable)i.next();
      if (ext == parent) return true;
    }
    for (Iterator pi=parents.iterator(); pi.hasNext(); ) {
      parent = (Extendable)pi.next();
      if (parent.hasAncestor(ext)) return true;
    }
    return false;
  }

  /**
   * Return TRUE if this class has the specified parent interface; otherwise,
   * return FALSE.  
   *
   * @param  id   The parent interface to be located
   * @param  all  Indicator of whether all parents (TRUE) or only direct
   *              parents (FALSE) are to be searched
   */
  public boolean hasParentInterface(SymbolID id, boolean all) {
    return all ? d_all_interfaces.containsKey(id)
      : d_local_interfaces.containsKey(id);
  }

  /**
   * Return Interface if this class has the specified parent interface; 
   * otherwise, return FALSE.  
   *
   * @param  id   The parent interface to be located
   * @param  all  Indicator of whether all parents (TRUE) or only direct
   *              parents (FALSE) are to be searched
   */
  public Interface getParentInterface(SymbolID id, boolean all) {
    return (Interface) (all ? d_all_interfaces.get(id)
                        : d_local_interfaces.get(id));
  }

  /**
   * Return the <code>Set</code> of symbol references for this object, each
   * element of which is in <code>SymbolID</code> form.  These are
   * defined as all references for this object as well as its parents.
   * The set of references includes this symbol name.
   */
  public Set getSymbolReferences() {
    return d_all_references;
  }

  /**
   * Return the <code>Set</code> of all symbol references for this object, each
   * element of which is in <code>SymbolID</code> form.  Note the references
   * include those from this object's parents.
   */
  public Set getAllSymbolReferences() {
    return getSymbolReferences();
  }

  /**
   * Return the <code>Set</code> of basic array types for this object, each
   * element of the set being in <code>SymbolID</code> form.
   */
  public Set getBasicArrayRefs() {
    return d_all_basicarrays;
  }

  /**
   * Protected method called by parents to add their relevant information,
   * such as methods, interfaces, references, and arrays to this object.
   *
   * @param  ext  The parent whose methods and related information are to be 
   *              added
   */
  protected void addParentData(Extendable ext) {
    checkFrozen();
    /*
     * Add all parent methods to the list of all methods in this
     * class.  Do not override an existing method in the methods
     * list if the new method is abstract, since abstract methods
     * cannot override other methods.  If the method is abstract,
     * then add it to the list of abstract methods; otherwise, remove
     * it from the list.
     */
    for (Iterator m = ext.getMethods(true).iterator(); m.hasNext(); ) {
      Method method = (Method) m.next();
      //Only add the method if it hasn't already been renamed.
      if (!d_renamed_method_symbolID.containsKey(method)) {
        addToMethodLists(method, true);
      }
    }

    /*
     * Add the parent and its parents to the list of parents.  That is
     * a lot of parents, let me tell you.
     */
    if (ext.isInterface()) {
      d_all_interfaces.put(ext.getSymbolID(), ext);
      d_local_interfaces.put(ext.getSymbolID(), ext);
    }

    Collection parents = ext.getParentInterfaces(true);
    for (Iterator p = parents.iterator(); p.hasNext(); ) {
      Extendable e = (Extendable) p.next();
      d_all_interfaces.put(e.getSymbolID(), e);
    }

    d_all_references.addAll(ext.getSymbolReferences());
    d_all_basicarrays.addAll(ext.getBasicArrayRefs());
  }

  /**
   *  Get all methods with the same short name as the passed in method name.
   *  (This list includes the passed in method, so the collection has a
   *  minimum size of 1)
   *  This is necessary for F90 method overloading
   */
  public Collection getOverloadedMethodsByName(String shortName) {
    LinkedList ret = new LinkedList();
    for (Iterator i = d_all_methods.iterator(); i.hasNext(); ) {
      Method m = (Method) i.next();
      if (m.getShortMethodName().compareTo(shortName) == 0) {
        ret.add(m);
      }
    }
    return ret;
  }

  /**
   * Locate the corresponding <code>Method</code> in the given list of 
   * <code>Method</code> elements, removing and optionally replacing
   * it.
   *
   * Assumptions:  
   * 1) There is only one entry in the list for each long method name.
   *    (This is appropriate since the long method name is supposed to be 
   *    unique.)
   *
   * @param  list       List of methods to be searched.
   * @param  method     The method whose existing entry is to be located
   * @param  replaceIt  Indicator of whether or not the existing method is
   *                    to be replaced
   */
  private void findExistingEntry(List list, Method method, boolean replaceIt)
  {
    String  name  = method.getLongMethodName();
    boolean found = false;
    Method  item  = null;

    for (Iterator iter = list.iterator(); iter.hasNext() && !found; ) {
        item = (Method) iter.next();
        if (item.getLongMethodName().equals(name)) {
          found = true;
          iter.remove();
        }
      }
    if (replaceIt) {
      list.add(method);
    }
  }

  /**
   * Add the given method to the specified short-name method list.
   *
   * @param  list    The short-name method list
   * @param  method  The method to be added to the list
   */
  private void addToShortMethodList(Map list, Method method) {
    checkFrozen();
    String name = method.getShortMethodName();

    if (list.containsKey(name)) {
      findExistingEntry((List) list.get(name), method, true);
    } else {
      ArrayList sublist = new ArrayList();
      sublist.add(method);
      list.put(name, sublist);
    }
  }


  /**
   * Remove the given method from the specified short-name method list.
   *
   * @param  list    The short-name method list
   * @param  method  The method to be added to the list
   */
  private void removeFromShortMethodList(Map list, Method method) {
    String name = method.getShortMethodName();

    if (list.containsKey(name)) {
      findExistingEntry((List) list.get(name), method, false);
    } 
  }

  /**
   * Add the specified method to all and, if not isParent, local method lists
   * provided the method does not exist or is not abstract.  Note the latter 
   * case is used to overwrite existing methods.
   *
   * @param  method    The method to be added
   * @param  isParent  TRUE if method belongs to the parent; otherwise, FALSE.
   */
  private void addToMethodLists(Method method, boolean isParent) {
    String long_name = method.getLongMethodName();

    if (!(hasMethodByLongName(long_name, true) && method.isAbstract())) 
    {
      boolean is_static  = method.isStatic();

      /* 
       * First process the lists of local and parent methods (i.e., the
       * "all" lists).
       */
      findExistingEntry(d_all_methods, method, true);
      d_all_methods_long.put(long_name, method);
      addToShortMethodList(d_all_methods_short, method);

      if (is_static) {
        findExistingEntry(d_all_static_methods, method, true);
      } else {
        findExistingEntry(d_all_nonstatic_methods, method, true);
      }
      if (method.isAbstract()) {
        findExistingEntry(d_all_abstract_methods, method, true);
      } else {
        findExistingEntry(d_all_abstract_methods, method, false);
      }

      /* 
       * Now process the lists of local methods, provided the method does
       * not belong to a parent.
       */
      if (!isParent) 
      {
        findExistingEntry(d_local_methods, method, true);
        d_local_methods_long.put(long_name, method);
        addToShortMethodList(d_local_methods_short, method);
        if (is_static) {
          findExistingEntry(d_local_static_methods, method, true);
        } else {
          findExistingEntry(d_local_nonstatic_methods, method, true);
        }
      }
    }
  }

  /**
   * Remove the specified method from all method lists.  This is used in
   * From clauses
   *
   * @param  method    The method to be added
   */
  private void removeFromMethodLists(Method method) {
    String long_name = method.getLongMethodName();

    if (!(hasMethodByLongName(long_name, true) && method.isAbstract())) 
    {
      boolean is_static  = method.isStatic();

      /* 
       * First process the lists of local and parent methods (i.e., the
       * "all" lists).
       */
      findExistingEntry(d_all_methods, method, false);
      d_all_methods_long.remove(long_name);
      removeFromShortMethodList(d_all_methods_short, method);
      if (is_static) {
        findExistingEntry(d_all_static_methods, method, false);
      } else {
        findExistingEntry(d_all_nonstatic_methods, method, false);
      }
      findExistingEntry(d_all_abstract_methods, method, false);

      /* 
       * Now process the lists of local methods
       */
      findExistingEntry(d_local_methods, method, false);
      d_local_methods_long.remove(long_name);
      removeFromShortMethodList(d_local_methods_short, method);
      if (is_static) {
        findExistingEntry(d_local_static_methods, method, false);
      } else {
        findExistingEntry(d_local_nonstatic_methods, method, false);
        
      }
    }
  }

  /* 
   * This function returns the set of symbols that this Extendable is
   * dependent on.  It should include any classes or interfaces that are
   * passed by any method in the Extendable, but no enums, and no self.
   */
  public Set getObjectDependencies() throws CodeGenerationException {
    HashSet ret = new HashSet();
    if (d_all_references.size() > 1) 
    {
      for (Iterator i = d_all_references.iterator(); i.hasNext(); ) {
        SymbolID l_id = (SymbolID) i.next();
        Symbol l_sym  = (Symbol) Utilities.lookupSymbol(d_context, l_id);
        //if(l_id != this.getSymbolID() && l_sym.getSymbolType() != Symbol.ENUM) {
        if ((l_sym.getSymbolType() != Symbol.ENUM) &&
           (l_sym.getSymbolType() != Symbol.STRUCT)) {
          ret.add(l_id);
        }
      }
    }
    return ret;
  }

  /**
   * Adds the assertion to the invariant clause of this Extendable.
   *
   * Assumptions:
   * 1) Constraints on the assertion are being enforced elsewhere.
   * 2) Only invariant assertions local to this extendable are going to be 
   *    added!
   *
   * @param   assertion  The assertion to be added to this invariant clause.
   * @throws  gov.llnl.babel.symbols.AssertionException
   *                     The exception raised if the assertion cannot be added.
   */
  public void addInvAssertion(Assertion assertion) throws AssertionException {
    checkFrozen();
    if (!assertion.isInvariant()) {
      throw new AssertionException("Extendable: " + getFullName(), "Cannot add "
                + "a(n) \"" + assertion.getTypeName() + "\" assertion to the "
                + "invariant clause.");
    } else {
      d_inv_clause.add(assertion);
    } 
  }

  /**
   * Returns a <code>List</code> of <code>Assertion</code>s consisting of all
   * assertions in the local and inherited invariant clauses.
   */
  public List getAllInvAssertions() throws CodeGenerationException {
    List list = new LinkedList();
    for (Iterator i = getAllParentsInOrder().iterator(); i.hasNext(); ) {
      Extendable parent = (Extendable)i.next();
      list.addAll(parent.getAllInvAssertions());
    }
    list.addAll(d_inv_clause);
    return list;
  }

  /**
   * Returns a <code>List</code> of <code>Assertion</code>s that make up the
   * invariant clause.
   *
   * @param   all   If TRUE then return local and inherited invariant
   *                assertions; otherwise, return local invariants.
   */
  public List getInvClause(boolean all) throws CodeGenerationException {
    return all ? d_inv_clause : getAllInvAssertions();
  }

  /**
   * Returns a <code>List</code> of <code>Assertion</code>s that make up the
   * global invariant clause.
   */
  public List getInvClause() throws CodeGenerationException {
    return getInvClause(true);
  }

  /**
   * Returns the number of assertions within invariant clause(s) of this 
   * Extendable.
   *
   * @param   all   If TRUE then return the number in inherited clauses;
   *                otherwise, return only the number in the local clause.
   */
  public int getTotalInvAssertions(boolean all) throws CodeGenerationException {
    return getInvClause(all).size();
  }

  /**
   * Returns TRUE if this Extendable has any assertions in its invariant
   * clause; otherwise, returns FALSE.
   *
   * @param   all   If TRUE then check inherited invariant clauses; otherwise, 
   *                only check the local clause.
   */
  public boolean hasInvClause(boolean all) throws CodeGenerationException {
    boolean has = false;
    if (all) {
      has = getTotalInvAssertions(all) > 0 ? true : false;
    } else {
      if (d_inv_clause.size() > 0) {
        has = true;
      }
    }
    return has;
  }

  /**
   * Validates the invariant clause(s).
   *
   * @param   all   If TRUE then validate local and inherited invariant
   *                clauses; otherwise, only validate the local clause.
   * @param   skip  If TRUE then will skip the validation process for
   *                assertions already marked valid.
   * @throws  gov.llnl.babel.symbols.AssertionException
   *                The exception raised if errors are detected.
   */
  public void validateInvClause(boolean all, boolean skip) 
    throws AssertionException, CodeGenerationException
  {
    List list = (all ? getAllInvAssertions() : d_inv_clause);
    for (Iterator i = list.iterator(); i.hasNext(); ) {
      Assertion inv = (Assertion)i.next();
      inv.validateExpression(this, null, skip);
    }

    if (invHasResultOrArg()) {
      throw new AssertionException("Extendable: " + getFullName(),
                  "Invariant clauses cannot include result or method arguments."
                + "  Those are reserved for methods only.");
    }
  }

  /**
   * Return TRUE if the invariant clause includes a method call; FALSE
   * otherwise.
   */
  public boolean invHasMethodCall()
    throws CodeGenerationException 
  {
    boolean has  = false;
    for (Iterator i = d_inv_clause.iterator(); i.hasNext() && !has; ) {
      Assertion inv = (Assertion)i.next();
      if (inv.hasMethodCall()) {
        has = true;
      }
    }

    return has;
  }
                                                                                
  /**
   * Return TRUE if the invariant clause includes a method call to the
   * specified method; FALSE otherwise.
   */
  public boolean invHasMethodCall(String name)
    throws CodeGenerationException 
  {
    boolean has  = false;
    for (Iterator i = d_inv_clause.iterator(); i.hasNext() && !has; ) {
      Assertion inv = (Assertion)i.next();
      if (inv.hasMethodCall(name)) {
        has = true;
      }
    }

    return has;
  }
                                                                                
  /**
   * Return TRUE if the invariants include a Result or a method argument; FALSE
   * otherwise.  This should NEVER be true by code generation time.
   */
  public boolean invHasResultOrArg()
    throws CodeGenerationException 
  {
    boolean has  = false;
    for (Iterator i = d_inv_clause.iterator(); i.hasNext() && !has; ) {
      Assertion inv = (Assertion)i.next();
      if (inv.hasResultOrArg()) {
        has = true;
      }
    }

    return has;
  }
                                                                                
  /**
   * Returns the default complexity for the invariants; 0 if no invariants.
   */
  public int getInvDefaultComplexity()
    throws CodeGenerationException 
  {
    int       def  = 0;
    int       comp = 0;

    for (Iterator i = d_inv_clause.iterator(); i.hasNext(); ) {
      Assertion inv = (Assertion)i.next();
      comp          = inv.getDefaultComplexity();
      if (comp > def) {
        def = comp;
      }
    }

    return def;
  }

  /**
   * Returns TRUE if the Extendable has contracts (that contain assertions); 
   * otherwise, returns FALSE.
   */
  public boolean hasContracts() throws CodeGenerationException 
  {
    boolean has = false;
    if (hasInvClause(true)) {
      has = true;
    } else {
      for (Iterator i = getMethods(true).iterator(); i.hasNext() && (!has); ) 
      {
        Method method = (Method) i.next();
        if (  method.hasPreClause() || method.hasPostClause() ) {
          has = true;
        }
      }
    }
    return has;
  }

  /**
   * Returns TRUE if the Extendable has a method with an argument or return
   * value of type struct and FALSE otherwise.
   */
  public boolean hasStruct() throws CodeGenerationException 
  {
    for(Iterator I=getMethods(true).iterator(); I.hasNext();) {
      Method method = (Method) I.next();
      if(method.hasStruct())
        return true;
    }
    return false;
  }
  
  /**
   * Returns the total number of assertions contained within all contract
   * clauses associated with the method -- including invariant clauses.
   * Returns 0 if there is no such method in this Extendable's hierarchy 
   * or if there are no assertions in any associated clauses.  Since 
   * invariant clauses are enforced with the precondition clause and again 
   * with the postcondition clause, their assertions are double counted.
   *
   * @param  longname  The long name of the desired method.
   */
  public int getTotalContractAssertions(String longname) 
    throws CodeGenerationException 
  {
    return getTotalPreAssertions(longname) 
         + 2 * getTotalInvAssertions(true)
         + getTotalPostAssertions(longname);
  }

  /**
   * Returns TRUE if the Extendable has any preconditions(), explicit or
   * inherited; otherwise, returns FALSE.
   */
  public boolean hasPreconditions() throws CodeGenerationException 
  {
    boolean has = false;
    for (Iterator i = getMethods(true).iterator(); i.hasNext() && (!has); ) 
    {
      Method method = (Method) i.next();
      String lname  = method.getLongMethodName();
      if (getTotalPreAssertions(lname)  > 0) {
        has = true;
      }
    }
    return has;
  }

  /**
   * Return the number of precondition assertions associated with the method 
   * in this object, if it is present.  Will return an 0 if there is no such 
   * method in this object's hierarchy or if there are no precondition
   * assertions.
   *
   * @param  longname  The long name of the method the number of whose 
   *                   precondition assertions is to be returned.
   */
  public int getTotalPreAssertions(String longname) 
    throws CodeGenerationException 
  {
    return getAllPreAssertions(longname).size();
  }

  /**
   * Return all precondition assertions associated with the method in this 
   * object, if there is one, in a <code>List</code> with each entry being
   * in <code>Assertion</code> form.  Will return an empty list if no such 
   * method is in this object's hierarchy.
   *
   * WARNING:  
   *   There is no attempt at this point to optimize the precondition 
   *   assertions.  That is, duplicate assertions are NOT removed.
   *
   * @param  longname  The long name of the method whose precondition 
   *                   assertions are to be returned.
   */
  public List getAllPreAssertions(String longname) 
    throws CodeGenerationException 
  {
    List list = new LinkedList();
    for (Iterator i = getAllParentsInOrder().iterator(); i.hasNext(); ) {
      Extendable parent = (Extendable)i.next();
      list.addAll(parent.getAllPreAssertions(longname));
    }
    Method meth = lookupMethodByLongName(longname, false);
    if (meth != null) {
      list.addAll(meth.getPreClause());
    }
    return list;
  }

  /**
   * Validate the precondition clause(s) for the specified method.
   *
   * @param   meth  The method whose precondition clauses are to be validated.
   * @param   all   If TRUE then validate local and inherited precondition
   *                clauses; otherwise, only validate the local clause.
   * @param   skip  If TRUE then will skip the validation process for
   *                assertions already marked valid.
   * @throws  gov.llnl.babel.symbols.AssertionException
   *                The exception raised if errors are detected.
   */
  public void validatePreClause(Method meth, boolean all, boolean skip) 
    throws AssertionException, CodeGenerationException
  {
    if (meth != null) 
    {
      List list;
      if (all) {
        String longname = meth.getLongMethodName();
        list = getAllPreAssertions(longname);
      } else {
        list = meth.getPreClause();
      }
      for (Iterator i = list.iterator(); i.hasNext(); ) {
        Assertion pre = (Assertion)i.next();
        pre.validateExpression(this, meth, skip);
      }
    } else {
      throw new AssertionException("Extendable: " + getFullName(), 
                  "Cannot validate precondition clause for specified method "
                + "when no method given.");
    }
  }

  /**
   * Validate the precondition clauses for the specified method.
   *
   * @param   longname  The long name of the method whose precondition
   *                    clauses are to be validated.
   * @param   all       If TRUE then validate local and inherited precondition
   *                    clauses; otherwise, only validate the local clause.
   * @param   skip      If TRUE then will skip the validation process for
   *                    assertions already marked valid.
   * @throws  gov.llnl.babel.symbols.AssertionException
   *                    The exception raised if errors are detected.
   */
  public void validatePreClause(String longname, boolean all, boolean skip) 
    throws AssertionException, CodeGenerationException
  {
    validatePreClause(lookupMethodByLongName(longname, true), all, skip);
  }

  /**
   * Validate the precondition clauses.
   *
   * @param   all   If TRUE then validate local and inherited precondition
   *                clauses; otherwise, only validate the local clause.
   * @param   skip  If TRUE then will skip the validation process for
   *                assertions already marked valid.
   * @throws  gov.llnl.babel.symbols.AssertionException
   *                The exception raised if errors are detected.
   */
  public void validatePreClause(boolean all, boolean skip) 
    throws AssertionException, CodeGenerationException
  {
    Iterator im;
    if (all) {
      im = d_all_methods.iterator();
    } else {
      im = d_local_methods.iterator();
    }
    while (im.hasNext()) {
      Method meth = (Method)im.next();
      validatePreClause(meth, all, skip);
    }
  }

  /**
   * Returns TRUE if the Extendable has any postconditions(), explicit or
   * inherited; otherwise, returns FALSE.
   */
  public boolean hasPostconditions() throws CodeGenerationException 
  {
    boolean has = false;
    for (Iterator i = getMethods(true).iterator(); i.hasNext() && (!has); ) 
    {
      Method method = (Method) i.next();
      String lname  = method.getLongMethodName();
      if (getTotalPostAssertions(lname)  > 0) {
        has = true;
      }
    }
    return has;
  }

  /**
   * Return the total number of postcondition clause assertions for the 
   * specified method, if present.  Returns 0 if there is no such method
   * in this Extendable's hierarchy or if there are no postcondition
   * clause(s).
   *
   * @param  longname  The long name of the desired method.
   */
  public int getTotalPostAssertions(String longname) 
    throws CodeGenerationException 
  {
    return getAllPostAssertions(longname).size();
  }

  /**
   * Return all postcondition assertions associated with the method in this 
   * object, if there is one, in an <code>List</code> with each element in
   * <code>Assertion</code> form.  Will return an empty list if no such 
   * method in this object's hierarchy.
   *
   * WARNING:  
   *   There is no attempt at this point to optimize the postcondition 
   *   assertions.  That is, duplicate assertions are NOT removed.
   *
   * @param  longname  The long name of the method whose postcondition 
   *                   assertions are to be returned.
   */
  public List getAllPostAssertions(String longname) 
    throws CodeGenerationException 
  {
    List list = new LinkedList();
    for (Iterator i = getAllParentsInOrder().iterator(); i.hasNext(); ) {
      Extendable parent = (Extendable)i.next();
      list.addAll(parent.getAllPostAssertions(longname));
    }
    Method meth = lookupMethodByLongName(longname, false);
    if (meth != null) {
      list.addAll(meth.getPostClause());
    }
    return list;
  }

  /**
   * Validate the postcondition clause associated with this object and the 
   * specified method.  
   *
   * @param   meth  The method whose postcondition assertions are to be 
   *                validated.
   * @param   all   If TRUE then validate local and inherited postcondition
   *                clauses; otherwise, only validate the local clause.
   * @param   skip  If TRUE then will skip the validation process for
   *                assertions already marked valid.
   * @param   all   If TRUE then all postcondition assertions of the method 
   *                will be validated; otherwise, only the locally defined 
   *                ones will.
   * @param   skip  If TRUE then will skip the validation process if the
   *                expression has already been marked as valid.
   * @throws  gov.llnl.babel.symbols.AssertionException
   *                The exception raised if errors are detected.
   */
  public void validatePostClause(Method meth, boolean all, boolean skip) 
    throws AssertionException, CodeGenerationException
  {
    if (meth != null) 
    {
      List list;
      if (all) {
        String longname = meth.getLongMethodName();
        list = getAllPostAssertions(longname);
      } else {
        list = meth.getPostClause();
      }
      for (Iterator i = list.iterator(); i.hasNext(); ) {
        Assertion post = (Assertion)i.next();
        post.validateExpression(this, meth, skip);
      }
    } else {
      throw new AssertionException("Extendable: " + getFullName(), 
                  "Cannot validate postcondition clause(s) when method "
                + "not given.");
    }
  }

  /**
   * Validate the postcondition assertions associated with this object and 
   * the specified method.  
   *
   * @param   longname  The long name of the method whose postcondition 
   *                    assertions are to be validated.
   * @param   all       If TRUE then validate local and inherited postcondition
   *                    clauses; otherwise, only validate the local clause.
   * @param   skip      If TRUE then will skip the validation process for
   *                    assertions already marked valid.
   * @param   all       If TRUE then all postcondition assertions of the 
   *                    method will be validated; otherwise, only the locally 
   *                    defined ones will.
   * @param   skip      If TRUE then will skip the validation process if the
   *                    expression has already been marked as valid.
   * @throws  gov.llnl.babel.symbols.AssertionException
   *                    The exception raised if errors are detected.
   */
  public void validatePostClause(String longname, boolean all, boolean skip)
    throws AssertionException, CodeGenerationException
  {
    validatePostClause(lookupMethodByLongName(longname, true), all, skip);
  }

  /**
   * Validate postcondition clauses associated with this object.
   *
   * @param   skip  If TRUE then will skip the validation process if the
   *                expression has already been marked as valid.
   * @param   all   If TRUE then validate local and inherited postcondition
   *                clauses; otherwise, only validate the local clause.
   * @param   skip  If TRUE then will skip the validation process for
   *                assertions already marked valid.
   * @param   all   If TRUE then all postcondition clauses of the method will 
   *                be validated; otherwise, only the locally defined ones will.
   * @throws  gov.llnl.babel.symbols.AssertionException
   *                The exception raised if errors are detected.
   */
  public void validatePostClause(boolean all, boolean skip) 
    throws AssertionException, CodeGenerationException
  {
    Iterator im;
    if (all) {
      im = d_all_methods.iterator();
    } else {
      im = d_local_methods.iterator();
    }
    while (im.hasNext()) {
      Method meth = (Method)im.next();
      validatePostClause(meth, all, skip);
    }
  }

  /**
   * Validate contracts associated with this object.  That is, validate all 
   * invariant as well as any pre- and post- condition clauses associated 
   * with any methods.
   *
   * @param   all   If TRUE then validate local and inherited clauses; 
   *                otherwise, only validate local clauses.
   * @param   skip  If TRUE then will skip the validation process for
   *                assertions already marked valid.
   * @param   skip  If TRUE then will skip the validation process if the
   *                expression has already been marked as valid.
   * @param   all   If TRUE then all assertions including those for all 
   *                associated methods will be validated; otherwise, only 
   *                validate the locally defined ones.
   * @throws  gov.llnl.babel.symbols.AssertionException
   *                The exception raised if errors are detected.
   */
  public void validateContracts(boolean all, boolean skip) 
    throws AssertionException, CodeGenerationException
  {
    validateInvClause(all, skip);
    Iterator im;
    if (all) {
      im = d_all_methods.iterator();
    } else {
      im = d_local_methods.iterator();
    }
    while (im.hasNext()) {
      Method meth = (Method)im.next();
      validatePreClause(meth, all, skip);
      validatePostClause(meth, all, skip);
    }
  }

  /**
   * Returns true if the specified reserved method call is present in any
   * of the assertions; otherwise, returns false.
   */
  public boolean hasBuiltinMethodAssertion(int type) 
    throws CodeGenerationException 
  {
    boolean hasIt = false;
    for (Iterator i = getAllInvAssertions().iterator(); i.hasNext() && !hasIt; )
    {
      Assertion inv = (Assertion)i.next();
      if (inv.hasBuiltinMethod(type)) {
        hasIt = true;
      }
    }

    if (!hasIt) {
      for (Iterator im = d_all_methods.iterator(); im.hasNext() && !hasIt; ) {
        Method meth = (Method)im.next();
        if (meth.contractHasBuiltinMethod(type)) {
          hasIt = true;
        }
      }
    }
    return hasIt;
  }


  public void freeze()
  {
    if (!d_frozen) 
    {
      super.freeze();
      Iterator i;
      for (i = d_all_methods.iterator(); i.hasNext(); ) {
        Method m = (Method)i.next();
        m.freeze();
      }
      for (i = d_all_references.iterator(); i.hasNext(); ) {
        SymbolID id = (SymbolID)i.next();
        id.freeze();
      }
      for (i = d_all_basicarrays.iterator(); i.hasNext(); ) {
        SymbolID id = (SymbolID)i.next();
        id.freeze();
      }
      for (i = d_renamed_method_symbolID.values().iterator(); i.hasNext(); ) {
        SymbolID id = (SymbolID)i.next();
        id.freeze();
      }
      d_all_abstract_methods    = protectList(d_all_abstract_methods);
      d_all_basicarrays         = protectSet(d_all_basicarrays);
      d_all_interfaces          = protectMap(d_all_interfaces);
      d_all_methods             = protectList(d_all_methods);
      d_all_methods_with_nonblocking = 
                                  protectList(d_all_methods_with_nonblocking);
      d_all_methods_long        = protectMap(d_all_methods_long);
      d_all_methods_short       = protectMap(d_all_methods_short);
      d_all_references          = protectSet(d_all_references);
      d_all_nonstatic_methods   = protectList(d_all_nonstatic_methods);
      d_all_static_methods      = protectList(d_all_static_methods);
      d_local_interfaces        = protectMap(d_local_interfaces);
      d_local_methods           = protectList(d_local_methods);
      d_local_methods_with_nonblocking = 
                                  protectList(d_local_methods_with_nonblocking);
      d_local_methods_long      = protectMap(d_local_methods_long);
      d_local_methods_short     = protectMap(d_local_methods_short);
      d_local_nonstatic_methods = protectList(d_local_nonstatic_methods);
      d_local_static_methods    = protectList(d_local_static_methods);
      d_renamed_parent_methods  = protectMap(d_renamed_parent_methods);
      d_renamed_method_symbolID = protectMap(d_renamed_method_symbolID);
      d_old_name_new_method     = protectMap(d_old_name_new_method);
      d_inv_clause              = protectList(d_inv_clause);
    }
  }
}
