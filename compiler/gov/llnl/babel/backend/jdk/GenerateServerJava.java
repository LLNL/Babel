//
// File:        GenerateClientJava.java
// Package:     gov.llnl.babel.backend.jdk
// Revision:    @(#) $Id: GenerateServerJava.java 7188 2011-09-27 18:38:42Z adrian $
// Description: generate Java client code based on a set of symbol identifiers
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

package gov.llnl.babel.backend.jdk;

import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.CodeGenerator;
import gov.llnl.babel.backend.jdk.JavaImplSource;
import gov.llnl.babel.backend.jdk.ServerJNI;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Class <code>GenerateServerJava</code> is the main entry point for the Java
 * client-side code generation routines.  This class generates the Java files
 * and the C JNI files.  It is assumed that all symbols necessary to generate
 * Java code are available in the symbol table.
 */
public class GenerateServerJava implements CodeGenerator {

  private Context d_context = null;

  /**
   * The constructor does nothing interesting.  The entry point for
   * the <code>GenerateServerJava</code> class is <code>generateCode</code>.
   */
  public GenerateServerJava() {
  }

  /**
   * Generate Java server-side code for each symbol identifier in the set
   * argument.  This routine assumes that all necessary symbol references
   * are available in the symbol table.
   */
  public void generateCode(Set symbols) throws CodeGenerationException {
    for (Iterator s = symbols.iterator(); s.hasNext(); ) {
      SymbolID id = (SymbolID) s.next();
      Symbol symbol = d_context.getSymbolTable().lookupSymbol(id);
      if (symbol != null) {
        switch (symbol.getSymbolType()) {
        case Symbol.CLASS:
          generateJava((Extendable)symbol);
          generateJNI(symbol);
          break;
        case Symbol.INTERFACE:
        case Symbol.ENUM:
        case Symbol.PACKAGE:
          break;
        }
      }
    }
  }

  /**
   * Generate Java code for the specified symbol.  Since Java code must follow
   * Java-style directory conventions, we must force the file manager to output
   * the Java code in a directory structure.  Reset the flags so that the other
   * C JNI code follows the default conventions.  We do not need to generate any
   * code for packages.
   */
  private void generateJava(Extendable extendable) throws CodeGenerationException {
    final int type = extendable.getSymbolType();
    if ((type != Symbol.PACKAGE)) {
      JavaImplSource.generateCode(extendable, d_context); 
    }
  }

  /**
   * Generate C JNI stub code for the specified class or interface.  Stub
   * files are not generated for package symbols or enumerated types.
   */
  private void generateJNI(Symbol symbol) throws CodeGenerationException {
    if (symbol instanceof Extendable) {
      ServerJNI.generateCode((Extendable) symbol, d_context);
    }
  }

  public String getType()
  {
    return "skel";
  }

  public boolean getUserSymbolsOnly()
  {
    return true;
  }

  public Set getLanguages()
  {
    Set result = new TreeSet();
    result.add("java");
    return result;
  }

  public void setName(String name)
    throws CodeGenerationException
 {
    if (!"java".equals(name)) {
      throw new CodeGenerationException
        ("\"" +name + "\" is not a valid name for the java generator.");
    }
  }

  public String getName() { return "java"; }

  public void setContext(Context context) {
    d_context = context;
  }
}
