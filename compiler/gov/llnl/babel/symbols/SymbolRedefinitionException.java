//
// File:        SymbolRedefinitionException.java
// Package:     gov.llnl.babel.symbols
// Revision:    @(#) $Id: SymbolRedefinitionException.java 7188 2011-09-27 18:38:42Z adrian $
// Description: exception thrown for improper symbol definitions
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

import gov.llnl.babel.symbols.SymbolID;

/**
 * A <code>SymbolRedefinitionException</code> is thrown if a symbol is
 * redefined improperly or if two symbols exist in the symbol table with
 * the different versions.
 */
public class SymbolRedefinitionException extends Exception {
   /**
	 * 
	 */
	private static final long serialVersionUID = -8991682236605265312L;
private SymbolID d_new;
   private SymbolID d_old;
   
   /**
    * Create a new exception object for the specified symbol identifiers.
    */
   public SymbolRedefinitionException(SymbolID new_id, SymbolID old_id) {
      super("Symbol \""
          + new_id.getSymbolName()
          + "\" conflicts with previously defined symbol \""
          + old_id.getSymbolName()
          + "\"");
      d_new = new_id;
      d_old = old_id;
   }

   /**
    * Return the symbol identifier for the old symbol.
    */
   public SymbolID getOldSymboID() {
      return d_old;
   }

   /**
    * Return the symbol identifier for the new symbol.
    */
   public SymbolID getNewSymbolID() {
      return d_new;
   }
}
