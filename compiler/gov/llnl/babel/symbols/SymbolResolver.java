//
// File:        SymbolResolver.java
// Package:     gov.llnl.babel.symbols
// Revision:    @(#) $Id: SymbolResolver.java 7188 2011-09-27 18:38:42Z adrian $
// Description: interface for resolving symbols based on symbol name
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

import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;

/**
 * The <code>SymbolResolver</code> interface is implemented by concrete
 * symbol resolvers used by the <code>SymbolTable</code>.  Sample resolvers
 * might query a type database or search through specified directories in
 * the file system.
 */
public interface SymbolResolver {
   /**
    * Look up a symbol based on the fully qualified name but accept any
    * version.  In general, resolvers should return the most recent version
    * that matches the symbol name, but this behavior is not guaranteed.
    * If the symbol name is not found or there is a problem with the symbol
    * entry (for example, the XML format is invalid), then null is returned.
    */
   public Symbol lookupSymbol(String fqn);

   /**
    * Look up a symbol based on the fully qualified name and the version.
    * If the symbol name is not found, the versions do not match, or there
    * is a problem with the symbol entry, then null is returned.
    */
   public Symbol lookupSymbol(SymbolID id);
}
