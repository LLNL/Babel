//
// File:        CodeGenerator.java
// Package:     gov.llnl.babel.backend
// Revision:    @(#) $Id: CodeGenerator.java 7188 2011-09-27 18:38:42Z adrian $
// Description: interface for backend code generators
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

import gov.llnl.babel.backend.CodeGenerationException;
import java.util.Set;

/**
 * Interface <code>CodeGenerator</code> is implemented by the backend code
 * generators.  It has several methods to identify what type of generator
 * it is and what language it serves. The real work is done by the
 * {@link #generateCode} method that generates code for a set of
 * {@link gov.llnl.babel.symbols.Symbol} objects.
 *
 * @version $Id: CodeGenerator.java 7188 2011-09-27 18:38:42Z adrian $
 */
public interface CodeGenerator extends ContextAware {

  /**
   * Generate IOR code for each symbol identifier in the set argument.
   * These routines assume that all symbols necessary to generate code
   * are available in the symbol table, which can be guaranteed by calling
   * <code>resolveAllReferences</code> on the symbol table prior to invoking
   * this routine.
   * @param symbols a set of {@link gov.llnl.babel.symbols.Symbol}
   * instances. The generator is expetected to write code for each symbol.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *  this indicates that something failed during the code generation.
   * It could be anything from an I/O error to a illegal data type.
   */
  public void generateCode(Set symbols) throws CodeGenerationException;

  /**
   * Return <code>true</code> if and only if this code factory should 
   * only operate on symbols outside the sidl namespace (i.e., exclude
   * symbols from the sidl runtime library). This is typically true
   * for C and Python where the stubs for the sidl runtime library
   * are pregenerated. Generally, this should be <code>true</code> for
   * anything other than a stub. Implementors do not need to worry
   * about the special case of generating the sidl runtime library
   * itself.
   */
  public boolean getUserSymbolsOnly();

  /**
   * Return the set of language names that this generator supports. Normally,
   * there is one one name per generator. However, the C++ generator can
   * be referred to as cxx or c++, so it has two in its set. The names
   * should be lower case. Some examples are "c", "ior", "c++", etc.
   * @return a Set of strings. Each string is a language name that this
   * generator supports.
   */
  public Set getLanguages();
  
  /**
   * Return the type of generator. Currently, there are three types
   * of generator "stub", "skel" and "ior". "xml" might be added someday.
   */
  public String getType();

  /**
   * Set the name of the generator. This sets the name of the generator as
   * it appeared on the command line. This method should be called at least
   * once before {@link #getName()} is called.
   * @param name this should be the name that the end user designated for
   *             the generator.
   *
   * @exception An exception is thrown if the name doesn't match one of the
   * allowable names in {@link #getLanguages()}.
   */
  public void setName(String name) throws CodeGenerationException;

  /**
   * Return the canonical name of this generator. This exists for cases
   * where a backend may have more than one name. {@link #setName(String)}
   * should be called at least once before this method is called.
   * @return the return value should always be a string in the set returned
   * by {@link #getLanguages()}. It may not be equal to the name given in
   * {@link #setName(String)}.
   */
  public String getName();
}
