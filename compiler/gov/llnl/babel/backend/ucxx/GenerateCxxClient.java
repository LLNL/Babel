//
// File:        GenerateCxxClient.java
// Package:     gov.llnl.babel.backend.ucxx
// Revision:    @(#) $Revision: 7188 $
// Date:        $Date: 2011-09-27 11:38:42 -0700 (Tue, 27 Sep 2011) $
// Description: Generate a C++ client for a set of sidl symbols
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

package gov.llnl.babel.backend.ucxx;

import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.CodeGenerator;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.Utilities;
import gov.llnl.babel.backend.ucxx.Cxx;
import gov.llnl.babel.backend.writers.LanguageWriterForCxx;
import gov.llnl.babel.symbols.Comment;
import gov.llnl.babel.symbols.Enumeration;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Package;
import gov.llnl.babel.symbols.Struct;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.SymbolTable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * <p>
 * This class is responsible for generating the C++ client-side code. It
 * will generate C++ extension types for classes and interfaces, and it will
 * generate native Cxx for enumerated types.
 * </p>
 */
public class GenerateCxxClient implements CodeGenerator {

  private Context d_context = null;

   /**
    * Create a C++ client generator.
    */
   public GenerateCxxClient() {
   }

   /**
    * Generate C++ client-side code for each <code>SymbolID</code> in the
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
      for(Iterator s = symbols.iterator(); s.hasNext() ; ){
         SymbolID id = (SymbolID)s.next();
         Symbol   symbol = table.lookupSymbol(id);
         if (symbol != null){
            switch(symbol.getSymbolType()){
            case Symbol.STRUCT:
              generateStruct((Struct)symbol);
              break;
            case Symbol.CLASS:
            case Symbol.INTERFACE:
              generateExtendable((Extendable)symbol);
              break;
            case Symbol.ENUM:
              generateEnumeration( (Enumeration) symbol);
              break;
            case Symbol.PACKAGE:
              generatePackage((Package)symbol);
              break;
            }
         }
      }
   }

   /**
    * Generate the C++ package.
    *
    * @param package  a non-null package to create
    * @exception gov.llnl.babel.backend.CodeGenerationException
    *    all catch all exception for any kind of problem while generating
    *    the package file.
    */
   private void generatePackage(Package pkg)
      throws CodeGenerationException
   {
    LanguageWriterForCxx out = null;
    SymbolID id   = pkg.getSymbolID();

    String filename = Cxx.generateFilename( id, Cxx.FILE_ROLE_STUB, 
                                            Cxx.FILE_TYPE_CXX_HEADER,
                                            d_context);
    //System.out.println("Create " + filename + "..." );
    
    out = Cxx.createHeader( pkg, Cxx.FILE_ROLE_STUB, "STUBHDRS", d_context );
    out.println();
    if (!pkg.getFinal()) {
      out.skipIncludeGuard();
    }
    out.openHeaderGuard( filename );
    out.println();
    
    /*
     * Write out the C++ include files for each of the symbols within
     * the package.
     */
    List entries = Utilities.sort(pkg.getSymbols().keySet());
    for (Iterator i = entries.iterator(); i.hasNext(); ) {
      String include = Cxx.generateFilename( (SymbolID) i.next(), 
                                             Cxx.FILE_ROLE_STUB,
                                             Cxx.FILE_TYPE_CXX_HEADER,
                                             d_context);
      out.generateInclude(include, true);
      out.println();
    }
    out.println();
    
    out.closeHeaderGuard( );
   }


  private void writeArrayDefinition(LanguageWriterForCxx lw,
                                    Enumeration enm) {
    final SymbolID id = enm.getSymbolID();
    String ior_item_t = IOR.getEnumName(id);
    String cxx_item_t = Cxx.getEnumName(id);
    String cxx_array_t = "array< " + cxx_item_t + " >";
    String ior_array_t = IOR.getArrayName(id);
    String array_traits = "array_traits< " + cxx_item_t + " >";

    lw.println(IOR.getArrayName(id) + ";");

    Cxx.openUCxxNamespace(lw);
    lw.println("namespace sidl {");
    lw.tab();
    lw.writeCommentLine("traits specialization");
    lw.println("template<>");
    lw.println("struct " + array_traits + " {");
    lw.tab();
    lw.println("typedef " + cxx_array_t + " cxx_array_t;");
    lw.println("typedef " + cxx_item_t + " cxx_item_t;");
    lw.println("typedef " + ior_array_t + " ior_array_t;");
    lw.println("typedef sidl_long__array ior_array_internal_t;");
    lw.println("typedef " +  ior_item_t + " ior_item_t;");
    lw.println("typedef cxx_item_t value_type;");
    lw.println("typedef value_type& reference;");
    lw.println("typedef value_type* pointer;");
    lw.println("typedef const value_type& const_reference;");
    lw.println("typedef const value_type* const_pointer;");
    lw.println("typedef array_iter< " + array_traits + " > iterator;");
    lw.println("typedef const_array_iter< " + array_traits+ " > const_iterator;");
    lw.backTab();
    lw.println("};");
    lw.println();

    lw.writeCommentLine("array specialization");
    lw.println("template<>");
    lw.println("class " + cxx_array_t +
                     ": public enum_array< " + array_traits + " > {");
    lw.println("public:");
    lw.tab();

    lw.println("typedef enum_array< " + array_traits + " > Base;");
    lw.println("typedef " + array_traits + "::cxx_array_t          cxx_array_t;");
    lw.println("typedef " + array_traits + "::cxx_item_t           cxx_item_t;");
    lw.println("typedef " + array_traits + "::ior_array_t          ior_array_t;");
    lw.println("typedef " + array_traits + "::ior_array_internal_t ior_array_internal_t;");
    lw.println("typedef " + array_traits + "::ior_item_t           ior_item_t;");
    lw.println();

    lw.beginBlockComment(true);
    lw.println("conversion from ior to C++ class");
    lw.println("(constructor/casting operator)");
    lw.endBlockComment(true);
    lw.println("array( " + ior_array_t + "* src = 0) : Base(src) {}");
    lw.println();

    lw.beginBlockComment(true);
    lw.println("copy constructor");
    lw.endBlockComment(true);
    lw.println("array( const " + cxx_array_t + " &src) {");
    lw.tab();
    lw.println("d_array = src.d_array;");
    lw.println("if (d_array) addRef();");
    lw.backTab();
    lw.println("}");
    lw.println();
 
    lw.beginBlockComment(true);
    lw.println("assignment");
    lw.endBlockComment(true);
    lw.println(cxx_array_t + "&");
    lw.println("operator =( const " + cxx_array_t + " &rhs) {");
    lw.tab();
    lw.println("if (d_array != rhs.d_array) {");
    lw.tab();
    lw.println("if (d_array) deleteRef();");
    lw.println("d_array = rhs.d_array;");
    lw.println("if (d_array) addRef();");
    lw.backTab();
    lw.println("}");
    lw.println("return *this;");
    lw.backTab();
    lw.println("}");
    lw.println();
 
    lw.backTab();
    lw.println("};");
    lw.backTab();
    lw.println("}");
    lw.println();
    Cxx.closeUCxxNamespace(lw);
  }

   /**
    * Generate a C++ module in C++, as opposed to an C extension
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
    LanguageWriterForCxx out = null;
    if ( enumeration == null ) { 
      System.out.println("enum == null");
    }
     try { 
       String filename = Cxx.generateFilename( enumeration.getSymbolID(), 
                                               Cxx.FILE_ROLE_STUB,
                                               Cxx.FILE_TYPE_CXX_HEADER,
                                               d_context );
       //System.out.println("Create " + filename + "..." );
       
       out = Cxx.createHeader( enumeration, Cxx.FILE_ROLE_STUB, "STUBHDRS" ,
                               d_context );
       out.println();
       
       out.openHeaderGuard( filename );
       out.println();
       
       out.generateInclude( "sidl_cxx.hxx", false );
       out.generateInclude( IOR.getHeaderFile( enumeration.getSymbolID()), false);
       out.println();
       
       Cxx.nestPackagesInNamespaces( out, enumeration );

       out.println( "enum " + enumeration.getSymbolID().getShortName() + " {");
       out.tab();
       
       /*
	* Output each of the enumerators (with fill space) along with its
	* assigned value.  For pretty output, find the maximum enumerator
	* length and then space over that much to align the equals signs.
	*/
       String enum_name = enumeration.getSymbolID().getShortName();
       int maxlength = Utilities.getWidth(enumeration.getEnumerators());
       for (Iterator e = enumeration.getIterator(); e.hasNext(); ) {
	   String name = (String) e.next();
           Comment cmt = enumeration.getEnumeratorComment(name);
           out.writeComment(cmt, true);
	   out.printAligned(enum_name + "_" + name, maxlength);
	   out.print(" = ");
	   out.print(String.valueOf(enumeration.getEnumeratorValue(name)));
	   if (e.hasNext()) {
	       out.print(",");
	   }
	   out.println();
           if (cmt != null) {
	     out.println();
           }
       }
       
       out.backTab();
       out.println("};");
       out.println();

       
       
       Cxx.unnestPackagesInNamespaces( out, enumeration );
       out.println();
       
       writeArrayDefinition(out, enumeration);
       out.println();

       out.closeHeaderGuard();

     } finally { 
       if ( out != null ) { 
         out.close();
       }
     }
   }
  
  /**
   * Generate a C++ to wrap the extendable.
   *
   * @param extendable		the extendable to wrap in C++
   *				C extension type.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    all catch all exception for any kind of problem while generating
   *    the package file.
   */
  private void generateExtendable(Extendable extendable)
    throws CodeGenerationException
  {
    CxxStubHeader header = new CxxStubHeader(extendable, d_context);
    CxxStubSource source = new CxxStubSource(extendable, d_context);
    header.generateCode();
    source.generateCode();
  }

  private void generateStruct(Struct st)
    throws CodeGenerationException
  {
    CxxStructHeader header = new CxxStructHeader(st, d_context);
    CxxStructSource source = new CxxStructSource(st, d_context);
    header.generateCode();
    source.generateCode();
  }

  public String getType()
  {
    return "stub";
  }

  public boolean getUserSymbolsOnly()
  {
    return false;
  }

  public Set getLanguages()
  {
    Set result = new TreeSet();
    result.add("uc++");
    result.add("ucxx");
    result.add("c++");
    result.add("cxx");
    return result;
  }

  public void setName(String name)
    throws CodeGenerationException
 {
    if (! getLanguages().contains(name)) {
      throw new CodeGenerationException
        ("\"" +name + "\" is not a valid name for the cxx generator.");
    }
  }

  public String getName() { return "cxx"; }

  public void setContext(Context context) {
    d_context = context;
  }
}
