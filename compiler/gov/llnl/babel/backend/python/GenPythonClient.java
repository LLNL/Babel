//
// File:        GenPythonClient.java
// Package:     gov.llnl.babel.backend.python
// Revision:    @(#) $Revision: 7188 $
// Date:        $Date: 2011-09-27 11:38:42 -0700 (Tue, 27 Sep 2011) $
// Description: Generate a Python client for a set of sidl symbols
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

package gov.llnl.babel.backend.python;

import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.CodeGenerator;
import gov.llnl.babel.backend.Utilities;
import gov.llnl.babel.backend.python.Python;
import gov.llnl.babel.backend.python.PythonClientHeader;
import gov.llnl.babel.backend.writers.LanguageWriter;
import gov.llnl.babel.symbols.Comment;
import gov.llnl.babel.symbols.Enumeration;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Package;
import gov.llnl.babel.symbols.Struct;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.SymbolTable;
import gov.llnl.babel.symbols.Version;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * <p>
 * This class is responsible for generating the Python client-side code. It
 * will generate C extension types for classes and interfaces, and it will
 * generate a native Python for enumerated types.
 * </p>
 */
public class GenPythonClient implements CodeGenerator {

  private Context d_context = null;

  /**
   * Create a Python client generator.
   */
  public GenPythonClient() {
  }

  /**
   * If any of the parents of <code>id</code> aren't in
   * <code>symbols</code>, add them to <code>extraParents</code> if they
   * aren't already included in it.
   */
  private void checkExtraParents(Set 	     symbols,
                                 Set         extraParents,
                                 SymbolTable table,
                                 String      fullname)
  {
    Symbol sym;
    int index;
    while ((index = fullname.lastIndexOf('.')) >= 0) {
      // name of parent
      fullname = fullname.substring(0, index);
      if (extraParents.contains(fullname)) return;
      sym = table.lookupSymbol(fullname);
      if ((sym != null) && 
          (symbols.contains(sym.getSymbolID()))) return;
      extraParents.add(fullname);
    }
  }

  /**
   * Generate Python client-side code for each <code>SymbolID</code> in the
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
    SymbolTable table = d_context.getSymbolTable();
    HashSet extraParents = new HashSet();
    for(Iterator s = symbols.iterator(); s.hasNext() ; ){
      SymbolID id = (SymbolID)s.next();
      Symbol   symbol = table.lookupSymbol(id);
      checkExtraParents(symbols, extraParents, table, id.getFullName());
      if (symbol != null){
        switch(symbol.getSymbolType()){
        case Symbol.CLASS:
        case Symbol.INTERFACE:
          generateExtendable((Extendable)symbol);
          break;
        case Symbol.STRUCT:
          generateStruct((Struct)symbol);
          break;
        case Symbol.ENUM:
          generateEnumeration
            ((Enumeration)symbol);
          break;
        case Symbol.PACKAGE:
          generatePackage((Package)symbol);
          break;
        }
      }
    }
    // create empty __init__.py files for unmentioned packages
    // we need to create "made up" package objects for the packages
    Version v = new Version();
    LanguageWriter out = null;
    try {
      for(Iterator s = extraParents.iterator(); s.hasNext(); ) {
        SymbolID id = new SymbolID((String)s.next(), v);
        Package pkg = new Package(id, null, d_context);
        out = Python.createPyWriter(pkg, "__init__.py",
                                    "Fabricated package initialization",
                                    d_context);
        out.println();
        callExtendPath(out);
        out.close();
      }
    }  
    finally {
      if (out != null) {
        out.close();
      }
    }
  }

  private void callExtendPath(LanguageWriter out)
  {
    out.println("try:");
    out.tab();
    out.println("from pkgutil import extend_path");
    out.println("__path__ = extend_path(__path__, __name__)");
    out.backTab();
    out.println("except: # ignore all exceptions");
    out.tab();
    out.println("pass");
    out.backTab();
  }

  /**
   * Generate the directory and __init__.py file for the
   * package. Eventually, this should probably use a code splicer approach
   * to handle incremental additions to a package.
   *
   * @param package  a non-null package to create
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    all catch all exception for any kind of problem while generating
   *    the package file.
   */
  private void generatePackage(Package packge)
    throws CodeGenerationException
  {
    LanguageWriter out = null;
    try {
      out = Python.createPyWriter(packge, "__init__.py",
                                  "package initialization code",
                                  d_context);
      out.println("__all__ = [");
      Iterator i = Utilities.sort(packge.getSymbolReferences()).iterator();
      while (i.hasNext()) {
        SymbolID id = (SymbolID)i.next();
        out.print("   \"");
        out.print(id.getShortName());
        out.print("\"");
        if (i.hasNext()){
          out.println(",");
        }
      }
      out.println(" ]");
      out.println();
      callExtendPath(out);
    }
    finally {
      if (out != null){
        out.close();
      }
    }
  }

  /**
   * Generate a Python module in Python, as opposed to an C extension
   * type, to hold the enumerator definitions.
   *
   * @param enumeration  the <code>Enumeration</code> to generate.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    all catch all exception for any kind of problem while generating
   *    the package file.
   */
  private void generateEnumeration(Enumeration enumeration)
    throws CodeGenerationException 
  {
    LanguageWriter out = null;
    try {
      out = Python.createPyWriter
        (enumeration, enumeration.getSymbolID().getShortName() + ".py",
         "define enumeration constants",
         d_context);
      Iterator i = enumeration.getIterator();
      while (i.hasNext()) {
        String enumerator = (String)i.next();
        Comment cmt = enumeration.getEnumeratorComment(enumerator);
        out.writeComment(cmt, true);
        int value = enumeration.getEnumeratorValue(enumerator);
        out.print(enumerator);
        out.print(" = ");
        out.println(Integer.toString(value));
        if (cmt != null) {
          out.println();
        }
      }
    }
    finally {
      if (out != null) {
        out.close();
      }
    }
  }

  /**
   * Generate a Python C extension in C to wrapper the extendable.
   *
   * @param extendable		the extendable to wrap in Python
   *				C extension type.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    all catch all exception for any kind of problem while generating
   *    the package file.
   */
  private void generateExtendable(Extendable extendable)
    throws CodeGenerationException
  {
    PythonClientHeader header = new PythonClientHeader(extendable, d_context);
    PythonClientCSource source = new PythonClientCSource(extendable,
                                                         d_context);
    header.generateCode();
    source.generateCode();
  }

  private void generateStruct(Struct strct)
    throws CodeGenerationException
  {
    PythonClientHeader header = new PythonClientHeader(strct, d_context);
    PythonClientStructSource source = new 
      PythonClientStructSource(strct,d_context);
    header.generateCode();
    source.generateCode();
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
