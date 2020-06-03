//
// File:        FileListener.java
// Package:     gov.llnl.babel.backend
// Revision:    @(#) $Revision: 7188 $
// Date:        $Date: 2011-09-27 11:38:42 -0700 (Tue, 27 Sep 2011) $
// Description: File manager listener.
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

import gov.llnl.babel.symbols.SymbolID;

/**
 * Various other objects, primarily the Makefile generators, need to know
 * all the files that the file manager creates. Objects that implement this
 * interface can register themselves with the
 * {@link gov.llnl.babel.backend.FileManager} to receive notification when
 * files are created.
 */
public interface FileListener {

  /**
   * This method is called by the {@link gov.llnl.babel.backend.FileManager}
   * for each new file it creates.
   *
   * @param id     the file is related to this symbol id.
   * @param type   this indicates the type of the symbol. A constant
   *               from {@link gov.llnl.babel.symbols.Type}.
   * @param role   this describes the role the file plays. For example,
   *               the file could be a <code>STUBSRCS</code> file or a
   *               <code>IMPLSRCS</code> file. The role strings used
   *               are determined by the backend.
   * @param dir    the path (relative or absolute) of the directory where
   *               the file will be created.
   * @param name   the name of the file not including any directory
   *               information. The complete name of the file should
   *               be <code>dir + name</code>.
   */
  public void newFile(SymbolID id,
                      int      type,
                      String   role,
                      String   dir,
                      String   name);
}
