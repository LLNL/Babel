//
// File:        SymbolNotFoundException.java
// Package:     gov.llnl.babel.symbols
// Revision:    @(#) $Id: SymbolNotFoundException.java 7188 2011-09-27 18:38:42Z adrian $
// Description: exception thrown when symbols are not found in symbol table
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
 * A <code>SymbolNotFoundException</code> is thrown if a specified
 * symbol identifier cannot be found.
 */
public class SymbolNotFoundException extends Exception {
   /**
	 * 
	 */
	private static final long serialVersionUID = -1415030620975550060L;
private SymbolID d_id;
   
   /**
    * Create a new exception object for the specified symbol identifier.
    */
   public SymbolNotFoundException(SymbolID id) {
      super("Symbol not found: \"" + id.getSymbolName() + "\"");
      d_id = id;
   }

   /**
    * Return the symbol identifier that could not be found in the symbol
    * table.
    */
   public SymbolID getSymboID() {
      return d_id;
   }
}
