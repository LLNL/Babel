//
// File:        GenPythonServer.java
// Package:     gov.llnl.babel.backend.python
// Revision:    @(#) $Revision: 7188 $
// Date:        $Date: 2011-09-27 11:38:42 -0700 (Tue, 27 Sep 2011) $
// Description: Generate a Python server for a set of sidl symbols
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

package gov.llnl.babel.backend.python;

import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.CodeGenerator;
import gov.llnl.babel.backend.python.PythonServerCSource;
import gov.llnl.babel.symbols.Class;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.SymbolTable;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * <p>
 * This class is responsible for generating the Python server-side code. It
 * will generate C extension types for classes and interfaces, and it will
 * generate a native Python for enumerated types.
 * </p>
 */
public class GenPythonServer implements CodeGenerator {

  private Context d_context = null;

  /**
   * Create a Python server generator.
   */
  public GenPythonServer() {
  }

  /**
   * Generate Python server-side code for each <code>SymbolID</code> in the
   * set argument. This is the initial entry point for generating the
   * server-side code. This routine assumes that all necessary symbols are
   * available in the symbol table. This method generates the server-side
   * code as a set of files and directories.
   *
   * @param symbols  a set of <code>SymbolID</code> objects.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *     provide feedback when code generation fails for one reason or
   *     another.
   * @see gov.llnl.babel.symbols.SymbolID
   */
  public void generateCode(Set symbols)
    throws CodeGenerationException
  {
    SymbolTable table = d_context.getSymbolTable();
    for(Iterator s = symbols.iterator(); s.hasNext() ; ){
      SymbolID id = (SymbolID)s.next();
      Symbol   symbol = table.lookupSymbol(id);
      if (symbol != null){
        switch(symbol.getSymbolType()){
        case Symbol.CLASS:
          generateClass((Class)symbol);
          break;
        }
      }
    }
  }

  /**
   * Generate skeleton code to link the IOR to a Python implementation.
   *
   * @param cls		the class to link to the IOR with
   *				skeletons implemented in C.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    all catch all exception for any kind of problem while generating
   *    the package file.
   */
  private void generateClass(Class cls)
    throws CodeGenerationException
  {
    PythonServerCSource source = new PythonServerCSource(cls, d_context);
    PythonServerLaunch launch = new PythonServerLaunch(cls, d_context);
    PythonServerPySource pySource = new PythonServerPySource(cls, d_context);
    source.generateCode();
    launch.generateCode();
    pySource.generateCode();
    if (d_context.getConfig().getStrictProvenance()) {
      PythonServerPyAux pyaux = new PythonServerPyAux(cls, d_context);
      pyaux.generateCode();
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
    result.add("python");
    return result;
  }

  public void setName(String name) 
    throws CodeGenerationException
  {
    if (! "python".equals(name)) {
      throw new CodeGenerationException
        ("\"" +name + "\" is not a valid name for the python generator.");
    }
  }

  public String getName() { return "python"; }

  public void setContext(Context context) {
    d_context = context;
  }
}
