//
// File:        GenerateMatlabClient.java
// Package:     gov.llnl.babel.backend.matlab
// Revision:    @(#) $Revision: 7188 $
// Date:        $Date: 2011-09-27 11:38:42 -0700 (Tue, 27 Sep 2011) $
// Description: Generate a Matlab client for a set of sidl symbols
// 
// Copyright (c) 2000-2003, Lawrence Livermore National Security, LLC
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

package gov.llnl.babel.backend.matlab;

import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.CodeGenerator;
import gov.llnl.babel.backend.matlab.Matlab;
import gov.llnl.babel.backend.writers.LanguageWriterForMatlab;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;


/**
 * <p>
 * This class is responsible for generating the Matlab client-side code. It
 * will generate C extension types for classes and interfaces, and it will
 * generate a native Matlab for enumerated types.
 * </p>
 */
public class GenerateMatlabClient implements CodeGenerator {

  private Context d_context = null;

  /**
   * Create a Matlab client generator.
   */
  public GenerateMatlabClient() {
  }


  /**
   * Generate Matlab client-side code for each <code>SymbolID</code> in the
   * set argument. This is the initial entry point for generating the
   * client-side code. This routine assumes that all necessary symbols are
   * available in the symbol table. This method generates the client-side
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
    for(Iterator s = symbols.iterator(); s.hasNext() ; ){
      SymbolID id = (SymbolID)s.next();
      Symbol   symbol = d_context.getSymbolTable().lookupSymbol(id);
      if (symbol != null){
          generateMatlab(symbol);
          generateMexStub(symbol);
			}
    }
  }
                                                                                       

  /**
   * Generate Matlab constructor code for the specified calss or 
   * interface symbol.  
   * Matlab-style directory convetions: Matlab class constructor 
   * is under the directory @MatlabClass. Since Matlab code must follow 
   * Matlab-style directory conventions, we must force the file manager 
   * to output the Matlab code in a directory structure. Reset the flags 
   * so that the other C Matlab stub code follows the default conventions. 
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    all catch all exception for any kind of problem while generating
   *    the package file.
   */

   /**
    * Generate Matlab class constructor file with className.m
    * A Matlab object is represented by a struct, which identifies itself
    * by a 64-bit field "id" casted from an IOR pointer. The "id" is
    * taken from the constructor stub file in the class private directory.
    */

  private void generateMatlab(Symbol symbol)
    throws CodeGenerationException
  {
    SymbolID id = symbol.getSymbolID();
    int type = symbol.getSymbolType();
    if ((type != Symbol.PACKAGE)) {
      PrintWriter pw = null;
      try {
        String file = Matlab.getClientMatFile(id);
        boolean f = d_context.getFileManager().getJavaStylePackageGeneration();
        d_context.getFileManager().setJavaStylePackageGeneration(true);
        pw = d_context.getFileManager().createFile(id, type, "MATLAB_STUB", file);
        d_context.getFileManager().setJavaStylePackageGeneration(f);
        LanguageWriterForMatlab writer = 
          new LanguageWriterForMatlab(pw, d_context);
        ClientMatlab.generateCode(symbol, writer, d_context);
      } finally {
        if (pw != null) {
          pw.close();
        }
      }
    }
  }


  /**
   * Generate MexFunction source code for the specified class or interface symbol.  
   * Matlab directory convetions: non-static method functions are under 
   * @MatlabClass directory, static method functions are in the same directory 
   * as @MatlabClass.
   * Since Matlab code must follow Matlab-style directory conventions, 
   * we must force the file manager to output the Matlab code in a directory 
   * structure. Reset the flags so that the other C Matlab stub code 
   * follows the default conventions. 
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    all catch all exception for any kind of problem while generating
   *    the package file.
   */

  private void generateMexStub(Symbol symbol)
    throws CodeGenerationException
  {

    int type = symbol.getSymbolType();
    if ((type == Symbol.CLASS) || (type == Symbol.INTERFACE)) {
      ClientMexStub.generateCode((Extendable)symbol, d_context);
    }
  }


  public String getType()
  {
    return "stub";
  }

  public boolean getUserSymbolsOnly()
  {
    return true;
  }

  public Set getLanguages()
  {
    Set result = new TreeSet();
    result.add("matlab");
    return result;
  }

  public void setName(String name)
    throws CodeGenerationException
  {
    if (! "matlab".equals(name)) {
      throw new CodeGenerationException
        ("\"" +name + "\" is not a valid name for the c generator.");
    }
  }
                                                                                                  
  public String getName() {
    return "matlab";
  }

  public void setContext(Context context) {
    d_context = context;
  }
}
