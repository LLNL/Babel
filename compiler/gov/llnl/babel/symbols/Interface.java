//
// File:        Interface.java
// Package:     gov.llnl.babel.symbols
// Revision:    @(#) $Id: Interface.java 7188 2011-09-27 18:38:42Z adrian $
// Description: class representing sidl interfaces
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
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Metadata;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.backend.CodeGenerationException;
import java.util.Collection;
import java.util.Iterator;
/**
 * Class <code>Interface</code> defines a sidl interface.  All the
 * basic functionality for this class is implemented by the parent
 * extendable class.
 */
public class Interface extends Extendable {

   /**
    * Create an empty <code>Interface</code> object that will be constructed
    * by calls to other member functions.
    */
   public Interface(SymbolID id, Comment comment, Context context) {
      super(id, Symbol.INTERFACE, comment, context);
   }

   /**
    * Create an empty <code>Interface</code> object that will be constructed
    * by calls to other member functions.
    */
  public Interface(SymbolID id, Comment comment, Metadata metadata,
                    Context context)
  {
    super(id, Symbol.INTERFACE, comment, metadata, context);
  }

   /**
    * Return whether this interface is abstract.  An interface is always
    * abstract, even if it contains no methods.
    */
   public boolean isAbstract() {
      return true;
   }

   /**
    * Return whether this object represents an interface (true).
    */
   public boolean isInterface() {
      return true;
   }

   /**
    * Return whether this interface has any static methods (always false).
    */
   public boolean hasStaticMethod(boolean all) {
      return false;
   }

   /**
    * Add a parent interface to this interface.  This method does not check
    * that the interfaces and methods are compatible; it simply copies the
    * methods and interface parents and adds them to this interface.
    */
   public void addParentInterface(Interface parent) {
     checkFrozen();
      addParentData(parent);
   }

  /**
   *  generateAnonymousClass generates the anonymous class for this
   *  Interface.  This anonymous class is only used to connect an interface remotely.
   *
   */

  public Class generateAnonymousClass() throws CodeGenerationException{
    SymbolID ifcID = getSymbolID();
    String ifcFQN = ifcID.getFullName();
    Version clsVersion = new Version("1.0");
    int index = ifcFQN.lastIndexOf(SymbolID.SCOPE);
    String clsFQN = ifcFQN.substring(0,index+1)+"_"+ifcFQN.substring(index+1);
    SymbolID clsID = new SymbolID(clsFQN, clsVersion);
    Class cls = new Class(clsID, null, d_context);
    //Symbol baseClassSymbol = Utilities.lookupSymbol(BabelConfiguration.getBaseClass());
    //cls.setParentClass((Class) baseClassSymbol);
    cls.addParentInterface((Interface) this);

    Collection methods = getMethods(false);
    for(Iterator m = methods.iterator(); m.hasNext(); ) {
      Method method = (Method) m.next();
      cls.addMethod(method);
    }
    return cls;
  }

  public int getDepth()
  {
    return minimumDepth(getParentInterfaces(false));
  }
}
