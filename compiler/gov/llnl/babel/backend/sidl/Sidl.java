//
// File:        Sidl.java
// Package:     gov.llnl.babel.backend.sidl
// Revision:    @(#) $Revision: 7188 $
// Description: Collection of static methods for the sidl binding
//
// Copyright (c) 2002-2003, Lawrence Livermore National Security, LLC
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


package gov.llnl.babel.backend.sidl;

import gov.llnl.babel.backend.CodeConstants;
import gov.llnl.babel.symbols.SymbolID;

/**
 * Provide a collection of static methods to provide the mapping of SIDL
 * concepts into SIDL.  This class provides the mapping of symbol names
 * to SIDL symbols and the mapping of types.
 */
public class Sidl implements CodeConstants 
{

  /**
   * This class is not intended for public instantiation.
   */
  private Sidl()
  {
    // not intended for instantiation
  }

  /**
   * Convert a symbol name into string with the pieces of the symbol joined
   * together with underline characters.
   *
   * @param id  the symbol id to convert.
   * @return a string representation of the symbol with periods replaced
   * with underline characters.
   */
  public static String getSymbolName(SymbolID id) {
    return id.getFullName();
  }

  /**
   * Return the name of the Sidl file for a particular symbol (i.e., package).
   *
   * @param id  the symbol whose sidl file name will be returned.
   * @return the filename of file containing the SIDL source.
   */
  public static String getFileName(SymbolID id) {
    return getSymbolName(id) + ".sidl";
  }
}
