//
// File:        Cxx.java
// Package:     gov.llnl.babel.backend.ucxx
// Revision:    @(#) $$
// Description: common C++ binding routines shared by C++ code generators
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

package gov.llnl.babel.backend.ucxx;

import gov.llnl.babel.BabelConfiguration;
import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeConstants;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.LevelComparator;
import gov.llnl.babel.backend.Utilities;
import gov.llnl.babel.backend.c.C;
import gov.llnl.babel.backend.writers.LanguageWriterForCxx;
import gov.llnl.babel.backend.writers.LineCountingFilterWriter;
import gov.llnl.babel.symbols.Argument;
import gov.llnl.babel.symbols.AssertionExpression;
import gov.llnl.babel.symbols.CExprString;
import gov.llnl.babel.symbols.Class;
import gov.llnl.babel.symbols.Comment;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Interface;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.Struct;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.Type;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Class <code>Cxx</code> contains common C++ language binding routines
 * shared by the C++ backend code generators.  This class simply collects
 * many common C+ binding routines into one place.
 */
public class Cxx {
  /*
   * NOTE:  The following file role and file type literals represent static 
   * "enumerations" associated with a file's role (within the code generation 
   * context) and its source type, respectively.  Given the nature of these 
   * values and the limited operations to be performed, it was deemed 
   * sufficient to implement them in this manner (versus using a Java 
   * enumeration class with its associated overhead).
   */

  /*
   * Valid file role values
   */
  public final static int FILE_ROLE_MIN  = 0;
  public final static int FILE_ROLE_MAX  = 3;

  public final static int FILE_ROLE_NONE = 0;
  public final static int FILE_ROLE_IMPL = 1;
  public final static int FILE_ROLE_SKEL = 2;
  public final static int FILE_ROLE_STUB = 3;

  public final static String NULL = "NULL";


  /*  The indices for the following MUST correspond to the role values above */
  public final static String FILE_ROLE_SUFFIX[] =
  {
    "",
    "Impl",
    "Skel",
    "",
  }; 

  /*
   * Valid file type values
   */
  public final static int FILE_TYPE_MIN        = 0;
  public final static int FILE_TYPE_MAX        = 4;

  public final static int FILE_TYPE_NONE       = 0;
  public final static int FILE_TYPE_CXX_HEADER = 1;
  public final static int FILE_TYPE_CXX_SOURCE = 2;
  public final static int FILE_TYPE_C_HEADER   = 3;
  public final static int FILE_TYPE_C_SOURCE   = 4;
  

  /*  The indices for the following MUST correspond to the type values above */
  public final static String FILE_TYPE_EXTENSION[] =
  {
    "",
    ".hxx",
    ".cxx",
    ".h",
    ".c"
  }; 

  private final static String s_types[] = 
  {
    "void", 
    "bool",
    "char",
    "::std::complex<double>",
    "double",
    "::std::complex<float>",
    "float",
    "int32_t",
    "int64_t",
    "void*",
    "::std::string"
  };

  private final static String s_array_types[] = {
    null,                         
    "::sidl::array<bool>",
    "::sidl::array<char>",
    "::sidl::array< ::sidl::dcomplex>",
    "::sidl::array<double>",
    "::sidl::array< ::sidl::fcomplex>",
    "::sidl::array<float>",
    "::sidl::array<int32_t>",
    "::sidl::array<int64_t>",   
    "::sidl::array<void*>",
    "::sidl::array< ::std::string>"
  };

  private final static String temp_postfix = "_tmp";

  /**
   * This is a private inner class used to emit small code templates
   */
  private static class TemplatePrinter {
    LanguageWriterForCxx writer;
    Map map;
    
    public TemplatePrinter(LanguageWriterForCxx w, Map m) {
      writer = w;
      map = m;
    }
    
    public void print(String s) {
      writer.pushLineBreak(false);
      int size = s.length();
      StringBuffer out = new StringBuffer(2 * size);
      for(int j=0; j < size;) {
        if(s.charAt(j) == '@') {
          if(j+1 < size && s.charAt(j+1) == '@')
            out.append('@');
          else {
            int end = s.indexOf('@', j+1);
            if(end == -1)
              end = size;
            out.append(map.get(s.substring(j+1, end)));
            j = end+1;
          }
        }
        else {
          out.append(s.charAt(j++));
        }
      }
      writer.print(out.toString());
      writer.popLineBreak();
    }
  
    public void println(String s) {
      print(s);
      writer.println();
    }

    public void print(String[] template) {
      for(int i=0; i < template.length; ++i)
        println(template[i]);
    }
  }

  
  /**
   * Returns the appropriate <code>String</code> suffix associated with 
   * the specified role.
   * 
   * @param role the <code>int</code> associated with the role of the
   *               file to differentiate skeletons, stubs, impls, etc.
   */
  public static String getFileSuffix( int role )
  { 
    if ((FILE_ROLE_MIN <= role) && (role <= FILE_ROLE_MAX)) {
      return FILE_ROLE_SUFFIX [role];
    } else {
      return FILE_ROLE_SUFFIX [FILE_ROLE_NONE];
    }
  }

  /**
   * Returns the appropriate file extension <code>String</code> based on 
   * the file type, prepended with the period (e.g., ".hxx").
   * 
   * @param ftype the <code>int</code> associated with the type of the
   *               file to differentiate between header and source
   */
  public static String getFileExtension( int ftype )
  { 
    if ((FILE_TYPE_MIN <= ftype) && (ftype <= FILE_TYPE_MAX)) {
      return FILE_TYPE_EXTENSION [ftype];
    } else {
      // To Do...Consider raising an exception since extension unrecognized.
      return FILE_TYPE_EXTENSION [FILE_TYPE_NONE];
    }
  }

  /**
   * Generate the filename associated with a symbol identifier.  
   *
   * <ol>
   *  <li>Replaces the "." scope separators in the symbol by
   *      underscores</li>
   *  <li>Appends a "_" + suffix, if appropriate </li>
   *  <li>Appends the appropriate extension</li>
   * </ol>
   * 
   * @param id the <code>SymbolID</code> of the <code>Symbol</code>
   * @param role the <code>int</code> associated with the role of the
   *               file to differentiate skeletons, stubs, impls, etc.
   * @param ftype the <code>int</code> associated with the type of the
   *               file to differentiate between header and source
   *
   * When the --generate-subdirs and --short-file-names options are used
   * simultaneously, the generated file names will not include the package
   * names of the packages containing the symbol whose SymbolID is passed 
   * as an argument. Thus, either long or short names must be used in all 
   * clients or servers that have interdependencies; mixing short and long
   * names will result in compile and/or runtime errors.
   */
  public static String generateFilename( SymbolID id, 
                                         int role, 
                                         int ftype,
                                         Context context ) 
  { 
    if (context.getConfig().makePackageSubdirs() 
        && context.getConfig().getShortFileNames()) 
      {
        // [BRN] Short file names must be used everywhere or nowhere within a 
        // group of files that have dependencies.
        return generateFilename( id.getShortName(), role, ftype );
      } else {
      return generateFilename( id.getFullName(), role, ftype );
    }
  }

  /**
   * Generate the filename associated with a symbol identifier.  
   *
   * <ol>
   *  <li>Replaces the "." scope separators in the symbol by
   *      underscores</li>
   *  <li>Appends a "_" + suffix, if appropriate </li>
   *  <li>Appends the appropriate extension</li>
   * </ol>
   * 
   * @param symbolName the stringified name of the <code>Symbol</code>
   * @param role the <code>int</code> associated with the role of the
   *               file to differentiate skeletons, stubs, impls, etc.
   * @param ftype the <code>int</code> associated with the type of the
   *               file to differentiate between header and source
   */
  public static String generateFilename( String symbolName, int role, int
                                         ftype ) 
  { 
    String name = symbolName.replace('.','_');
    String suffix = getFileSuffix(role);
    String extension = getFileExtension( ftype );

    if (  suffix == null || suffix.equals("") ) { 
      return name + extension;
    } else {
      return name + "_" + suffix + extension;
    }
  }

  /**
   * Generate the role description associated with the symbol identifier
   * and the specified role of the file.
   *
   * @param id the <code>SymbolID</code> of the <code>Symbol</code>
   * @param role the <code>int</code> associated with the role of the
   *               file to differentiate skeletons, stubs, impls, etc.
   */
  public static String generateRoleDescription( SymbolID id, int role )
  { 
    // To Do...Consider raising an exception if the role is unrecognized.
    switch( role ) {
    case FILE_ROLE_IMPL: return CodeConstants.C_DESC_IMPL_PREFIX 
        + id.getFullName();
    case FILE_ROLE_SKEL: return CodeConstants.C_DESC_SKEL_PREFIX
        + id.getFullName();
    case FILE_ROLE_STUB: return CodeConstants.C_DESC_STUB_PREFIX
        + id.getFullName();
    case FILE_ROLE_NONE: 
    default            : return "Generated code for " + id.getFullName();
    }
  }

  /**
   * Create an empty header file and return the language writer 
   * to create subsequent content.
   *
   * @param symbol the <code>SymbolID</code> of the <code>Symbol</code>
   * @param role the <code>int</code> identifying the role of the
   *               file to differentiate skeletons, stubs, impls, etc.
   * @param filegroup a <code>String</code> to associate the file with
   *                  for possible makefile generation
   *
   */
  public static LanguageWriterForCxx createHeader( Symbol symbol, 
                                                   int role,
                                                   String filegroup,
                                                   Context context)
    throws CodeGenerationException
  { 
    final SymbolID id = symbol.getSymbolID();
    final int type = symbol.getSymbolType();

    String filename = generateFilename( id, role, FILE_TYPE_CXX_HEADER,
                                        context );
    LanguageWriterForCxx lw = null;
    if ( role==FILE_ROLE_IMPL) { 
      Writer fw = context.getFileManager().
        createWriter( id, type, filegroup,filename);
      LineCountingFilterWriter lcfw = new LineCountingFilterWriter( fw );
      PrintWriter pw = new PrintWriter( lcfw );
      lw = new LanguageWriterForCxx( pw , lcfw, context );
    } else { 
      PrintWriter pw = context.getFileManager().
        createFile( id, type, filegroup,filename);
      lw = new LanguageWriterForCxx( pw , context);
    }
    lw.writeBanner( symbol, filename, (role == FILE_ROLE_IMPL), 
                    generateRoleDescription(id, role) );
    return lw;
  }

  /**
   * Create an empty source file and return the language writer 
   * to create subsequent content.
   *
   * @param symbol the <code>SymbolID</code> of the <code>Symbol</code>
   * @param role the <code>int</code> identifying the role of the
   *               file to differentiate skeletons, stubs, impls, etc.
   * @param filegroup a <code>String</code> to associate the file with
   *                  for possible makefile generation
   *
   */
  public static LanguageWriterForCxx createSource( Symbol symbol, int role,
                                                   String filegroup,
                                                   Context context)
    throws CodeGenerationException
  { 
    final SymbolID id = symbol.getSymbolID();
    final int type = symbol.getSymbolType();

    String filename = generateFilename( id, role, FILE_TYPE_CXX_SOURCE,
                                        context);
    LanguageWriterForCxx lw = null;
    if ( role==FILE_ROLE_IMPL) { 
      Writer fw = context.getFileManager().
        createWriter( id, type, filegroup,filename);
      LineCountingFilterWriter lcfw = new LineCountingFilterWriter( fw );
      PrintWriter pw = new PrintWriter( lcfw );
      lw = new LanguageWriterForCxx( pw , lcfw , context);
    } else { 
      PrintWriter pw = context.getFileManager().
        createFile( id, type, filegroup,filename);
      lw = new LanguageWriterForCxx( pw, context );
    }
    lw.writeBanner( symbol, filename, (role == FILE_ROLE_IMPL), 
                    generateRoleDescription(id, role) );
    return lw;
  }

  /**
   * Generate a the namespaces in which the C++ class is nested.
   * Increase the tab levels in the language writer appropriately.
   *
   * @param writer the language writer for C++
   * @param symbol the symbol begin written to this file, containing
   *               the hierarchy of packages to which it belongs.
   * @see #unnestPackagesInNamespaces
   */
  public static void nestPackagesInNamespaces ( LanguageWriterForCxx writer,
                                                Symbol symbol ) { 
    openUCxxNamespace(writer);
    
    StringTokenizer tokens = new StringTokenizer( 
                                                 symbol.getSymbolID().getFullName(), "." );
    final int tokenCount = tokens.countTokens();
    for ( int i=0; (i+1)< tokenCount; ++i ) { // skip the last token
      writer.println( "namespace " + tokens.nextToken() + " { " );
      writer.tab();
    }
    writer.println();
  }

  /**
   * Close the namespaces in which the C++ class is nested.
   * Decrease the tab levels in the language writer appropriately.
   *
   * @param writer the language writer for C++
   * @param symbol the symbol begin written to this file, containing
   *               the hierarchy of packages to which it belongs.
   * @see #nestPackagesInNamespaces
   */
  public static void unnestPackagesInNamespaces ( LanguageWriterForCxx writer,
                                                  Symbol symbol ) { 
    java.util.Stack stack = new java.util.Stack();
    StringTokenizer tokens = new StringTokenizer( 
                                                 symbol.getSymbolID().getFullName(), "." );
    final int tokenCount = tokens.countTokens();
    for ( int i=0; (i+1)<tokenCount; ++i ) { // skip the last token
      stack.push( tokens.nextToken() );
    }
    while ( !stack.empty() ) { 
      writer.backTab();
      writer.print( "} " );
      writer.writeCommentLine("end namespace " + (String) stack.pop() );
    }
    closeUCxxNamespace(writer);
    writer.println();
  }

  /**
   * Generate a the namespaces in which the C++ class is nested.
   * Increase the tab levels in the language writer appropriately.
   *
   * @param writer the language writer for C++
   * @param symbol the symbol begin written to this file, containing
   *               the hierarchy of packages to which it belongs.
   * @see #unnestPackagesInNamespaces
   */
  public static void nestImplPackagesInNamespaces ( LanguageWriterForCxx writer,
                                                    Symbol symbol ) { 
    StringTokenizer tokens = new StringTokenizer(
                                                 symbol.getSymbolID().getFullName(), "." );
    final int tokenCount = tokens.countTokens();
    for ( int i=0; (i+1)< tokenCount; ++i ) { // skip the last token
      writer.println( "namespace " + tokens.nextToken() + " { " );
      writer.tab();
    }
    writer.println();
  }

  /**
   * Close the namespaces in which the C++ class is nested.
   * Decrease the tab levels in the language writer appropriately.
   *
   * @param writer the language writer for C++
   * @param symbol the symbol begin written to this file, containing
   *               the hierarchy of packages to which it belongs.
   * @see #nestPackagesInNamespaces
   */
  public static void unnestImplPackagesInNamespaces(LanguageWriterForCxx writer,
                                                    Symbol symbol ) 
  { 
    java.util.Stack stack = new java.util.Stack();
    StringTokenizer tokens = new StringTokenizer(
                                                 symbol.getSymbolID().getFullName(), "." );
    final int tokenCount = tokens.countTokens();
    for ( int i=0; (i+1)<tokenCount; ++i ) { // skip the last token
      stack.push( tokens.nextToken() );
    }
    while ( !stack.empty() ) { 
      writer.backTab();
      writer.print( "} " );
      writer.writeCommentLine("end namespace " + (String) stack.pop() );
    }
    writer.println();
  }

  /**
   * Generate a the namespaces in which the C++ class is nested.
   * Increase the tab levels in the language writer appropriately.
   *
   * @param writer the language writer for C++
   * @param symbolid the symbol begin written to this file, containing
   *               the hierarchy of packages to which it belongs.
   * @see #unnestPackagesInNamespaces
   */
  public static void nestPackagesInNamespaces ( LanguageWriterForCxx writer,
                                                SymbolID symbolid ) { 
    openUCxxNamespace(writer);
    StringTokenizer tokens = new StringTokenizer( 
                                                 symbolid.getFullName(), "." );
    final int tokenCount = tokens.countTokens();
    for ( int i=0; (i+1)< tokenCount; ++i ) { // skip the last token
      writer.println( "namespace " + tokens.nextToken() + " { " );
      writer.tab();
    }
    writer.println();
  }

  /**
   * Close the namespaces in which the C++ class is nested.
   * Decrease the tab levels in the language writer appropriately.
   *
   * @param writer the language writer for C++
   * @param symbolid the symbol begin written to this file, containing
   *               the hierarchy of packages to which it belongs.
   * @see #nestPackagesInNamespaces
   */
  public static void unnestPackagesInNamespaces ( LanguageWriterForCxx writer,
                                                  SymbolID symbolid ) { 
    java.util.Stack stack = new java.util.Stack();
    StringTokenizer tokens = new StringTokenizer( 
                                                 symbolid.getFullName(), "." );
    final int tokenCount = tokens.countTokens();
    for ( int i=0; (i+1)<tokenCount; ++i ) { // skip the last token
      stack.push( tokens.nextToken() );
    }
    while ( !stack.empty() ) { 
      writer.backTab();
      writer.print( "} " );
      writer.writeCommentLine("end namespace " + (String) stack.pop() );
    }
    closeUCxxNamespace(writer);
    writer.println();
  }

  /**
   * begin a region of method calls with C linkage
   *
   * @param writer languageWriter for the file
   */
  public static void beginExternCRegion( LanguageWriterForCxx writer ) { 
    writer.println("extern \"C\" {");
    writer.println();
    writer.tab();
  }

  /**
   * end region of method calls with C linkage
   *
   * @param writer languageWriter for the file
   */
  public static void endExternCRegion( LanguageWriterForCxx writer ) { 
    writer.backTab();
    writer.println();
    writer.println("} // end extern \"C\"");
  }

  /**
   * Convert a symbol name into its C++ identifier.  This method replaces
   * the "." scope separators in the symbol by "::".
   *
   * @param id the <code>SymbolID</code> of the <code>Symbol</code>.
   & @param postfix an optional postfix for the class itself
  */
  public static String getSymbolName(SymbolID id, String postfix ) {
    if ( postfix == null || postfix.trim().equals("") ) { 
      return "::" + Utilities.replace( id.getFullName(), ".", 
                                       "::" );
    } else { 
      return "::" + Utilities.replace( id.getFullName(), ".", 
                                       "::" ) + "_" + postfix;
    }
  }

  /**
   * Convert a symbol name into its C++ identifier.  This method replaces
   * the "." scope separators in the symbol by "::".
   *
   * @param id the <code>SymbolID</code> of the <code>Symbol</code>.
   & @param postfix an optional postfix for the class itself
  */
  public static String getImplSymbolName(SymbolID id, String postfix ) {
    if ( postfix == null || postfix.trim().equals("") ) { 
      return "::" + Utilities.replace(id.getFullName(), ".", "::" );
    } else { 
      return "::" + Utilities.replace(id.getFullName(), ".", "::" ) + "_" 
        + postfix;
    }

  }

  /**
   * Convert a symbol name into its C++ identifier.  This method replaces
   * the "." scope separators in the symbol by "::".
   *
   * @param id the <code>SymbolID</code> of the <code>Symbol</code>.
   * @param postfix an optional postfix for the class itself
   */
  public static String getImplSymbolNameWithoutLeadingColons(SymbolID id, 
                                                             String postfix ) 
  {
    if ( postfix == null || postfix.trim().equals("") ) { 
      return Utilities.replace(id.getFullName(), ".", "::" );
    } else { 
      return Utilities.replace(id.getFullName(), ".", "::" ) + "_" + postfix;
    }

  }
  
  public static String getSymbolName( SymbolID id ) { 
    return getSymbolName( id, null);
  }

  /** 
   * 
   * @see #getSymbolName
   */
  public static String getSymbolNameWithoutLeadingColons( SymbolID id, 
                                                          String postfix) 
  { 
    if ( postfix == null || postfix.trim().equals("") ) { 
      return 
        Utilities.replace( id.getFullName(), ".", "::" );
    } else { 
      return
        Utilities.replace( id.getFullName(), ".", "::" ) 
        + "_" + postfix;
    }
  }

  /**
   * Convert a sidl enumerated type into its symbol name, which is
   * just the colon separated symbol name
   *
   * @param id the <code>SymbolID</code> of the <code>Symbol</code>.
   */
  public static String getEnumName(SymbolID id) {
    return getSymbolName(id);
  }
  
  /**
   * Convert a sidl symbol name into its object name -- for the purposes of
   * this package that means convert it into its typedef object name.  The 
   * typedef name is the sidl symbol name with the "." scope separators 
   * replaced by "::".
   *
   * @param id the <code>SymbolID</code> of the <code>Symbol</code>.
   */
  public static String getObjectName(SymbolID id) {
    return getSymbolName(id); 
  }
 
  /**
   * convert an argument to a comment indicating the mode and perhaps
   * the type.
   *
   * @param arg
   * @return either a comment string
   */
  public static String argComment( Argument arg ) { 
    return C.argComment(arg);
  }
  
  /**
   * generate a reinterpretCast
   *
   * @param newtype the new type to cast to
   * @param arg the variable to cast
   * @return string that properly declares the cast
   */
  public static String reinterpretCast( String newtype, String arg ) { 
    if ( true ) { // NOTE: should check if new C++ casts are available
      return "reinterpret_cast< " + newtype + ">(" + arg + ")";
    } else { 
      // use old cast
      return  "( ( " + newtype + " ) " + arg + " )";
    }
  }
  
  /**
   * generate a constCast
   *
   * @param newtype the new type to cast to
   * @param arg the variable to cast
   * @return string that properly declares the cast
   */
  public static String constCast( String newtype, String arg ) { 
    if ( true ) { // NOTE: should check if new casts are available
      return "const_cast< " + newtype + ">(" + arg + ")";
    } else { 
      // use old cast
      return "( ( " + newtype + " ) " + arg + " )";
    }
  }

  /**
   * Opens ucxx namespace
   */
  public static void openUCxxNamespace(LanguageWriterForCxx writer ) { 
  }

  public static void closeUCxxNamespace(LanguageWriterForCxx writer ) { 
  }

  public static String prependGlobalUCxx() { 
    return "";
  }

  public static String prependLocalUCxx() { 
    return "";
  }

  /** 
   * Convert the type to a Cxx representation in string form
   * 
   */
  public static String getCxxString( Type type, boolean rarrays,
                                     Context context) 
    throws CodeGenerationException 
  { 
    int t = type.getType();
    if (t < s_types.length) { // If the type is one of the primitive types...
      
      // return its string value from the lookup table.
      return s_types[t];

    } else if (t == Type.SYMBOL) { 
      // else if the type is a symbol (e.g. class,interface,enm)

      // Look up the symbol type and return the associated type name.
      Symbol symbol = Utilities.lookupSymbol(context, type.getSymbolID());
      return getSymbolName( symbol.getSymbolID() ); 

    } else if (t == Type.ARRAY) { // else if the type is an array

      // either return one of the primitive
      // array types or construct the corresponding array type.
      Type atype = type.getArrayType();
      if(rarrays && type.isRarray()) {
        //Rarrays use the C structs instead of the C++ std library complexs
        if(atype.getType() == Type.DCOMPLEX || 
           atype.getType() == Type.FCOMPLEX) {
          return IOR.getReturnString(atype, context, true, false)+"*";
            
        } else {
          return getCxxString(atype, true, context)+"*";
        }
      } else if (null != atype) {
        int  a     = atype.getType();      
        if (a < s_array_types.length) {
          return s_array_types[a] ;
        } else {
          return "::sidl::array< " + getObjectName(atype.getSymbolID()) 
            + ">";
        }
      }
      else {
        return "::sidl::basearray";
      }
    } else { 
      return null;
    }
  }

  /**
   * Generate a Cxx return string for the specified sidl type.  Most of
   * the sidl return strings are listed in the static structures defined
   * at the start of the class.  Symbol types and array types require
   * special processing.
   *
   * This version will treat all rarrays as sidl arrays
   *
   * @param type the <code>Type</code> whose return string is being built.
   */
  public static String getReturnString(Type type,
                                       Context context) 
    throws CodeGenerationException 
  {
    return getCxxString( type, false, context );
  }

  /**
   * Generate a Cxx return string for the specified sidl type.  Most of
   * the sidl return strings are listed in the static structures defined
   * at the start of the class.  Symbol types and array types require
   * special processing.
   *
   * This one turns on the possibility of rarrays 
   *
   * @param type the <code>Type</code> whose return string is being built.
   */
  public static String getRarrayReturnString(Type type,
                                             Context context) 
    throws CodeGenerationException 
  {
    return getCxxString( type, true, context );
  }
 
  /**
   * Generate a C++ argument string for the specified sidl argument.
   * The formal argument name is not included.
   */
  private static String getArgumentString(Argument arg, 
                                          Context context,
                                          boolean rarrays,
                                          boolean isStub)
    throws CodeGenerationException 
  {
    int    mode = arg.getMode();
    StringBuffer argString = new StringBuffer(50);
    argString.append("/* " + argComment(arg) + " */");
 
    // next add the type
    Type type = arg.getType();
    final int dtype = type.getDetailedType();
    if ( (mode == Argument.IN) && 
         ( dtype == Type.STRING ||
           dtype == Type.STRUCT ||
           dtype == Type.FCOMPLEX ||
           dtype == Type.DCOMPLEX )) {
      argString.append("const ");
      
      argString.append( getReturnString( type, context ) );
      argString.append("&");
         
    } else if (rarrays && type.isRarray()) {

      argString.append( getRarrayReturnString( type, context ) );

    } else { 
      if ( (mode == Argument.IN) &&
           ( isStub && 
             ( dtype == Type.ARRAY ||
               dtype == Type.CLASS ||
               dtype == Type.INTERFACE))) {
        argString.append("const ");
      }
      argString.append( getReturnString( type, context ) );
      if ( (mode != Argument.IN )  ||
           (dtype == Type.ARRAY) ||
           (dtype == Type.CLASS) ||
           (dtype == Type.INTERFACE) ) {
        argString.append("&");
      } 
    }

    argString.append(" ");

    // finally add the name
    argString.append(arg.getFormalName());

    return argString.toString();
  }

  public static String getIORCall(String objName, Type t) {
    SymbolID id = t.getSymbolID();
    switch(t.getDetailedType()) {
    case Type.ARRAY:
      return objName + 
        ((null != t.getArrayType()) ? "._get_ior()" : "._get_baseior()");
      
    case Type.INTERFACE:
    case Type.CLASS:
      return getIORCall(objName, id);
    default:
      return null;
    }
  }

  public static String getIORCall(String objName, SymbolID id) {
    return "(" + C.getSymbolObjectPtr(id) + ") " + objName +"." + 
      getSymbolName(id,"") + "::_get_ior()";
  }

  public static String getLocalIOR(Type t) {
    SymbolID id = t.getSymbolID();
    switch(t.getDetailedType()) {
    case Type.ARRAY:
      return 
        ((null != t.getArrayType()) ? "_get_ior()" : "_get_baseior()");
    case Type.INTERFACE:
    case Type.CLASS:
      return "(" + C.getSymbolObjectPtr(id) + ") " + getSymbolName(id,"") + "::_get_ior();";
      //return "_get_ior();";
   
      //    case Type.INTERFACE:
      //return "(ior_t*) " + getSymbolName(id,"") + "::_cast((void*)(_get_ior()));";
      
    default:
      return null;
    }
  }

  /**
   * This returns the list of all methods that need to have stub methods
   * generated. This includes all locally defined methods and all locally
   * overloaded methods (i.e., if a method defined in a parent class is
   * overloaded by a locally defined method, it must be included).
   * 
   * @return list of {@link gov.llnl.babel.symbols.Method} objects
   */
  public static List getStubMethodList(Extendable ext)
  {
    final List allMethods = ext.getMethods(true);
    final ArrayList result = new ArrayList(allMethods.size());
    final Iterator i = allMethods.iterator();
    while(i.hasNext()) {
      final Method m = (Method)i.next();
      if (null != ext.lookupMethodByShortName(m.getShortMethodName(),false)) {
        result.add(m);
      }
    }
    return result;
  }
 
  /**
   * Generate the impl method's name.
   *
   * @param id the <code>SymbolID</code> of the <code>Symbol</code> 
   *           associated with the method.
   *
   * @param methodName the <code>String</code> version of the name of the
   *               method whose impl name is being built.
   */
  public static String getMethodImplName(SymbolID id, String methodName) {
    return getImplSymbolName( id, "impl")  + "::" + methodName+"_impl";
  }
 
  /**
   * Generate the skel method's name.
   *
   * @param id the <code>SymbolID</code> of the <code>Symbol</code> 
   *           associated with the method.
   *
   * @param methodName the <code>String</code> version of the name of the
   *               method whose skel name is being built.
   */
  public static String getMethodSkelName(SymbolID id, String methodName) {
    return "skel_" + id.getFullName().replace('.','_') + '_' + methodName;
  }
  
  /**
   * Generate the stub method's name.
   *
   * @param id the <code>SymbolID</code> of the <code>Symbol</code> 
   *           associated with the method.
   *
   * @param methodName the <code>String</code> version of the name of the
   *               method whose impl name is being built.
   */
  public static String getMethodStubName(SymbolID id, String methodName, boolean isSuper) {
    if(isSuper) {
      return Utilities.replace( id.getFullName(), ".", "::" )  + "_impl::Super::" + methodName;
    }
    return getSymbolNameWithoutLeadingColons( id, "")  + "::" + methodName;
  }

  /**
   * Generate the set of SymbolID's that this Extendable must #include.
   */
  public static Set generateIncludeSet(Extendable ext,
                                       Context context)
    throws CodeGenerationException
  {
    Set includes = new HashSet();
    
    for( Iterator i = ext.getMethods(false).iterator(); i.hasNext(); ) { 
      Method method = (Method) i.next();
      if ((!context.getConfig().getSkipRMI()) &&
          (!method.isStatic())&&
          (Method.NONBLOCKING==method.getCommunicationModifier())) {
        includes.add(Utilities.lookupSymbol(context,
                                            "sidl.rmi.Ticket").getSymbolID());
      }
      if (inlineStub(method)) {
        includes.addAll(method.getSymbolReferences());
      }
      else {
        Iterator j = method.getArgumentList().iterator();
        while (j.hasNext()) {
          Argument arg = (Argument)j.next();
          Type argType = arg.getType();
          switch(argType.getDetailedType()) {
          case Type.ENUM:
            includes.add(argType.getSymbolID());
            break;
          case Type.ARRAY:
            if ((arg.getMode() == Argument.IN) &&
                (argType.getArrayType() != null) &&
                (argType.getArrayType().getSymbolID() != null)) {
              includes.add(argType.getArrayType().getSymbolID());
            }
            break;
          case Type.CLASS:
          case Type.INTERFACE:
            if (arg.getMode() == Argument.IN) {
              includes.add(argType.getSymbolID());
            }
            break;
          }
        }
        Type returnType = method.getReturnType();
        switch(returnType.getDetailedType()) {
        case Type.ARRAY:
          if ((returnType.getArrayType() != null) && 
              (returnType.getArrayType().getSymbolID() != null)) {
            includes.add(returnType.getArrayType().getSymbolID());
          }
          break;
        case Type.ENUM:
        case Type.CLASS:
        case Type.INTERFACE:
        case Type.STRUCT:
          includes.add(returnType.getSymbolID());
          break;
        }
      }
    }

    // remove self Header file?  If invoked from Stub yes, 
    // If invoked from Skel no
    includes.remove(ext.getSymbolID());

    // Include parents
    java.util.Collection parents = ext.getParents(false);
    for(Iterator i = parents.iterator(); i.hasNext(); ){
      Extendable parent = (Extendable) i.next();
      includes.add(parent.getSymbolID());
    }

    return includes;
  }
 
  /**
   * Generates include directives for all the extendables that this
   * extendable inherits from.
   * 
   * @param writer Language writer for C++
   * @param ext Extendible (Class or Interface) to generate dependencies
   * @param removeSelf  True if called from a Stub generator since this
   *                    would cause an inclusion loop in Stub.h
   *                    False if called from an impl generator since
   *                    the impl may need to know about the stub.
   */
  public static Set getFrontIncludes( Extendable ext,
                                      Context context)
    throws CodeGenerationException
  {
    Set includes = new HashSet();
    for( Iterator i = ext.getMethods(false).iterator(); i.hasNext(); ) { 
      Method method = (Method) i.next();
      Set argTypes = method.getSymbolReferences();
      for(Iterator j = argTypes.iterator(); j.hasNext();) {
        SymbolID argID = (SymbolID) j.next();
        Symbol symbol = (Symbol) Utilities.lookupSymbol(context, argID);
        if (symbol.getSymbolType() == Type.ENUM || symbol.getSymbolType() == Type.STRUCT ) {
          includes.add(argID);
        } 
      }
    }
    
    // remove self Header file?  If invoked from Stub yes, 
    // If invoked from Skel no
    includes.remove(ext.getSymbolID());

    // Include parents
    java.util.Collection parents = ext.getParents(false);
    for(Iterator i = parents.iterator(); i.hasNext(); ){
      Extendable parent = (Extendable) i.next();
      includes.add(parent.getSymbolID());
    }

    return includes;
  } 

  /**
   * Generates include directives for all the extendables that this
   * extendable inherits from for Impls. For Impl header files only
   * 
   * @param writer Language writer for C++
   * @param ext Extendible (Class or Interface) to generate dependencies
   * @param removeSelf  True if called from a Stub generator since this
   *                    would cause an inclusion loop in Stub.h
   *                    False if called from an impl generator since
   *                    the impl may need to know about the stub.
   */
  public static Set generateImplHeaderDependencyIncludes( LanguageWriterForCxx writer,
                                                          Extendable ext , 
                                                          boolean removeSelf,
                                                          Context context) 
    throws CodeGenerationException 
  {
    Set includes = new HashSet();

    // always include the base header file.
    writer.generateInclude( "sidl_cxx.hxx", true );
    for( Iterator i = ext.getMethods(true).iterator(); i.hasNext(); ) { 
      Method method = (Method) i.next();
      if(method.hasExplicitExceptions()) {
        includes.addAll(method.getSymbolReferences());
      } else {
        includes.addAll(method.getSymbolReferencesWithoutExceptions());
      }
    }
    // remove self Header file?  If invoked from Stub yes, 
    // If invoked from Skel no
    if ( removeSelf ) { 
      includes.remove(ext.getSymbolID());
    } else { 
      includes.add(ext.getSymbolID());
    }

    // Include parents
    java.util.Collection parents = ext.getParents(false);
    for(Iterator i = parents.iterator(); i.hasNext(); ){
      Extendable parent = (Extendable) i.next();
      includes.add(parent.getSymbolID());
    }

    // generate include to my IOR
    writer.generateInclude( IOR.getHeaderFile( ext.getSymbolID() ), true );
    
    // sort remainder and generate includes.
    if (!includes.isEmpty()){
      //writer.writeComment("Includes for all method dependencies.",false);
      
      List entries = Utilities.sort(includes);
      
      for (Iterator i = entries.iterator(); i.hasNext(); ) { 
        String header = Cxx.generateFilename( (SymbolID) i.next(), 
                                              FILE_ROLE_STUB, 
                                              FILE_TYPE_CXX_HEADER,
                                              context);
        writer.generateInclude( header, true );
      }
      writer.println();
    } 
    return includes;
  } 


  /**
   * Generates include directives for all the Babel clases used in this 
   * Stub .cxx file
   * 
   * @param writer Language writer for C++
   * @param ext Extendible (Class or Interface) to generate dependencies
   */
  public static Set generateSourceIncludes( LanguageWriterForCxx writer,
                                            Extendable ext,
                                            Context context) 
    throws CodeGenerationException 
  {
    Set includes = new HashSet();
    for( Iterator i = ext.getMethods(true).iterator(); i.hasNext(); ) { 
      Method method = (Method) i.next();
      if(method.hasExplicitExceptions()) {
        includes.addAll(method.getSymbolReferences());
      } else {
        includes.addAll(method.getSymbolReferencesWithoutExceptions());
      }
    }
    if (!includes.isEmpty()){
      writer.writeComment("Includes for all method dependencies.",false);
      
      List entries = Utilities.sort(includes);
      
      for (Iterator i = entries.iterator(); i.hasNext(); ) { 
        String header = Cxx.generateFilename( (SymbolID) i.next(), 
                                              Cxx.FILE_ROLE_STUB, 
                                              Cxx.FILE_TYPE_CXX_HEADER,
                                              context);
        writer.generateInclude( header, true );
      }
    }
    return includes;
  } 

  /**
   * Generates include directives for all the Babel clases used in this 
   * Impl.cxx file
   * 
   * @param writer Language writer for C++
   * @param ext Extendible (Class or Interface) to generate dependencies
   */
  public static Set generateImplSourceIncludes( LanguageWriterForCxx writer,
                                                Class cls,
                                                Context context) 
    throws CodeGenerationException 
  {
    Set includes = new HashSet();
    for( Iterator i = cls.getMethods(true).iterator(); i.hasNext(); ) { 
      Method method = (Method) i.next();
      if(method.hasExplicitExceptions()) {
        includes.addAll(method.getSymbolReferences());
      } else {
        includes.addAll(method.getSymbolReferencesWithoutExceptions());
      }
    }

    if(cls.hasOverwrittenMethods()) {
      for( Iterator i = cls.getOverwrittenClassMethods().iterator(); i.hasNext(); ) { 
        Method method = (Method) i.next();
        includes.addAll(method.getSymbolReferences());
      }
    }

    if (!includes.isEmpty()){
      writer.writeComment("Includes for all method dependencies.",false);
      
      List entries = Utilities.sort(includes);
      
      for (Iterator i = entries.iterator(); i.hasNext(); ) { 
        String header = Cxx.generateFilename( (SymbolID) i.next(), 
                                              Cxx.FILE_ROLE_STUB, 
                                              Cxx.FILE_TYPE_CXX_HEADER,
                                              context);
        writer.generateInclude( header, true );
      }
    }


    return includes;
  } 

  /**
   * Generates include directives for all the extendables that this
   * extendable inherits from.
   * 
   * @param writer Language writer for C++
   * @param ext Extendible (Class or Interface) to generate dependencies
   * @param removeSelf  True if called from a Stub generator since this
   *                    would cause an inclusion loop in Stub.h
   *                    False if called from an impl generator since
   *                    the impl may need to know about the stub.
   */
  public static Set generateIncludes( LanguageWriterForCxx writer,
                                      Extendable ext , 
                                      boolean removeSelf,
                                      Context context) 
    throws CodeGenerationException 
  {
    Set includes = new HashSet();
    for( Iterator i = ext.getMethods(true).iterator(); i.hasNext(); ) { 
      Method method = (Method) i.next();
      
      if(method.hasExplicitExceptions()) {
        includes.addAll(method.getSymbolReferences());
      } else {
        includes.addAll(method.getSymbolReferencesWithoutExceptions());
      }
    }
  
    
    // always include the base header file.
    writer.generateInclude( "sidl_cxx.hxx", true );
    
    // remove self Header file?  If invoked from Stub yes, 
    // If invoked from Skel no
    if ( removeSelf ) { 
      includes.remove(ext.getSymbolID());
    } else { 
      includes.add(ext.getSymbolID());
    }

    // Include parents
    java.util.Collection parents = ext.getParents(false);
    for(Iterator i = parents.iterator(); i.hasNext(); ){
      Extendable parent = (Extendable) i.next();
      includes.add(parent.getSymbolID());
    }

    // generate include to my IOR
    writer.generateInclude( IOR.getHeaderFile( ext.getSymbolID() ), true );
    
    // sort remainder and generate includes.
    if (!includes.isEmpty()){
      //writer.writeComment("Includes for all method dependencies.",false);
      
      List entries = Utilities.sort(includes);
      
      for (Iterator i = entries.iterator(); i.hasNext(); ) { 
        String header = Cxx.generateFilename( (SymbolID) i.next(), 
                                              FILE_ROLE_STUB, 
                                              FILE_TYPE_CXX_HEADER,
                                              context);
        writer.generateInclude( header, true );
      }
      writer.println();
    } 
    return includes;
  }

  private static boolean isIncompleteType(Type t) {
    if(t == null)
      return false;
    else if(t.getType() == Type.SYMBOL)
      return true;
    else if(t.getType() == Type.ARRAY)
      return isIncompleteType(t.getArrayType());
    return false;
  }

  //this is true if the given method refers to an incomplete type, e.g., a
  //forward-declard class/interface. 
  public static boolean refersIncompleteTypes(Method method) {
    List args = method.getArgumentList();
    for(Iterator a = args.iterator(); a.hasNext(); ) { 
      Argument arg = (Argument) a.next();
      if(isIncompleteType(arg.getType()))
         return true;
    }
    if(isIncompleteType(method.getReturnType()))
      return true;
    return false;
  }

  private static boolean supportsFastCalls(Type t) {
    return !t.hasArrayOrderSpec() || t.isRarray();
  }
  
  //True, if fast native function calls are supported. This is currently
  //only false for methods with array order specs. 
  public static boolean supportsFastCalls(Method method) {
    if(!supportsFastCalls(method.getReturnType())) return false;
    for(Iterator i = method.getArgumentList().iterator(); i.hasNext(); ){
      if(!supportsFastCalls(((Argument)i.next()).getType())) return false;
    }
    return true;
  }

  private static void computeFastCallKeywordMap(HashMap map,
                                                Context d_context,
                                                Extendable ext,
                                                Method method,
                                                boolean rarrays_raw_version,
                                                boolean isStub)
    throws CodeGenerationException
  {
    String s_args = "";
    String s_arg_list = "";
    List args = null;

    //compute the argument list used for method declarations
    if(rarrays_raw_version)
      args = method.getArgumentListWithIndices();
    else 
      args = method.getArgumentListWithOutIndices();
    
    for(Iterator a = args.iterator(); a.hasNext(); ) { 
      Argument arg = (Argument) a.next();
      if(s_arg_list.length() > 0) s_arg_list += ", ";
      s_arg_list += getArgumentString(arg, d_context, rarrays_raw_version, isStub);
    }

    //compute the list of parameters used to call the implementation
    //there's a catch: we have to be careful always to call the full version
    //where index variables are _not_ omitted. the value for the missing
    //parameters can be obtained from the array objects passed.
    Set indices = method.getRarrayIndices();
    Map index_args = method.getRarrayInfo();
    args = method.getArgumentListWithIndices();
    for(Iterator a = args.iterator(); a.hasNext(); ) {
      Argument arg = (Argument) a.next();
      if(s_args.length() > 0) s_args += ", ";

      if(!rarrays_raw_version && arg.getType().isRarray()) {
        //pass-in the raw data pointer instead of the sidl array object
        s_args += arg.getFormalName() + "._get_ior()->d_firstElement";
      }
      else if(!rarrays_raw_version && indices != null && indices.contains(arg.getFormalName())) {
        //obtain the value from one of the rarrays that reference to this
        //index
        Set index_set = (Set) index_args.get(arg.getFormalName());
        Method.RarrayInfo info = (Method.RarrayInfo) index_set.iterator().next();
        s_args += "sidlLength(" + info.rarray.getFormalName() + "._get_ior(), " + info.dim + ")";
      }
      else {
        s_args += arg.getFormalName();
      }
    }

    String long_name = method.getLongMethodName();
    String impl_class = Cxx.getImplSymbolName(ext.getSymbolID(),"impl");
    
    map.put("name", method.getCorrectMethodName());
    map.put("class_type", ext.getShortName());
    map.put("class_type_qualified",
            Utilities.replace(ext.getSymbolID().getFullName(), ".", "::" ));
    map.put("method_p_type", Cxx.getNativeEPVEntryName(ext));
    map.put("ior_entry", IOR.getNativeVectorEntry(long_name));
    map.put("impl_name", getMethodImplName(ext.getSymbolID(), method.getShortMethodName()));
    map.put("impl_class", impl_class);
            
    map.put("long_name", long_name);
    map.put("arg_list", s_arg_list);
    map.put("args", s_args);
    map.put("ret", Cxx.getReturnString(method.getReturnType(), d_context));
    map.put("static", method.isStatic() ? "static " : "");
    map.put("self", ext.isInterface() ? "_ior->d_object" : "_ior");

    if(s_arg_list.length() > 0 ) {
      map.put("pmf_impl_arg_list", impl_class + "*, " + s_arg_list);
      map.put("pmf_stub_arg_list", ext.getShortName() + "*, " + s_arg_list);
      map.put("pmf_generic_arg_list", "sidl_generic_class_t*, " + s_arg_list);
    }
    else {
      map.put("pmf_impl_arg_list", impl_class + "*");
      map.put("pmf_stub_arg_list", ext.getShortName() + "*");
      map.put("pmf_generic_arg_list", "sidl_generic_class_t*");
    }

    if(s_args.length() > 0 ) {
      map.put("pmf_args", "__this_" + long_name + ", " + s_args);
    }
    else {
      map.put("pmf_args", "__this_" + long_name);
    }
    
    
  }


  /** generates private declarations for caching of function objects used in the
   *  experimental implementation of fast native function calls. 
   */
  public static void generateFastCallCache(LanguageWriterForCxx d_writer,
                                           Context d_context,
                                           Extendable ext,
                                           Method method)
    throws CodeGenerationException
  {
    HashMap map = new HashMap();
    computeFastCallKeywordMap(map, d_context, ext, method, true, true);
    TemplatePrinter P = new TemplatePrinter(d_writer, map);
    
    final String[] template = {
      "//local caching for method @long_name@",
      "#ifdef SIDL_FASTCALL_PMF_EXTENSION",
      "  typedef @ret@ (*@long_name@_pointer_t)(@pmf_generic_arg_list@);",
      "#else",
      "  typedef @ret@ (sidl_generic_class_t::*@long_name@_pointer_t)(@arg_list@);",
      "#endif",
      "void __cache_@long_name@(@long_name@_pointer_t&, sidl_generic_class_t *&);",
      "#ifndef SIDL_FASTCALL_DISABLE_CACHING",
      "  @long_name@_pointer_t __fun_@long_name@;",
      "  sidl_generic_class_t *__this_@long_name@;",
      "#endif",
      ""
    };

    final String[] static_template = {
      "//local caching for static method @long_name@",
      "typedef @ret@ (*@long_name@_pointer_t)(@arg_list@);",
      "static void __cache_@long_name@(@long_name@_pointer_t&);",
      "#ifndef SIDL_FASTCALL_DISABLE_CACHING",
      "  static @long_name@_pointer_t __fun_@long_name@;",
      "#endif",
      ""
    };

    P.print(method.isStatic() ? static_template : template);
  }
  
  /** generates definitions and inner types for function objects used in the
   *  experimental implementation of fast native function calls. 
   */
  public static void generateFastCallDispatch(LanguageWriterForCxx d_writer,
                                              Context d_context,
                                              Extendable ext,
                                              Method method)
    throws CodeGenerationException
  {
    if(!supportsFastCalls(method))
      return;
    
    HashMap map = new HashMap(), rmap=new HashMap();
    computeFastCallKeywordMap(map, d_context, ext, method, true, true);
    TemplatePrinter PR = null, P = new TemplatePrinter(d_writer, map);

    /*
     * For raw arrays, we need to generate a second method stub. One
     * accepts sidl array objects, the other one raw C++ pointers.
     */
    boolean has_rarray = method.hasRarray();
    if(has_rarray) {
      computeFastCallKeywordMap(rmap, d_context, ext, method, false, true);
      PR = new TemplatePrinter(d_writer, rmap);
    }

    /*
     * Emit the actual dispatch function.
     */
    if(refersIncompleteTypes(method)) {
      //For functions refering to incomplete C++ types, we move the
      //implementation to the cpp file
      final String t = "@static@@ret@ @name@ (@arg_list@);";
      P.println(t);
      if(has_rarray) PR.println(t);
    }
    else {
      final String[] template = {
        "inline @ret@ @name@ (@arg_list@) {",
        "#ifndef SIDL_FASTCALL_EAGER_CACHING",
        "  #ifdef SIDL_FASTCALL_DISABLE_CACHING",
        "    @long_name@_pointer_t __fun_@long_name@;",
        "    sidl_generic_class_t *__this_@long_name@;",
        "    __cache_@long_name@(__fun_@long_name@, __this_@long_name@);",
        "  #else",
        "    if(!__fun_@long_name@) __cache_@long_name@(__fun_@long_name@, __this_@long_name@);",
        "  #endif",
        "#endif",
        "#ifdef SIDL_FASTCALL_PMF_EXTENSION",
        "  return (__fun_@long_name@)(@pmf_args@);",
        "#else",
        "  return (__this_@long_name@->*__fun_@long_name@)(@args@);",
        "#endif",
        "}"
      };

      final String[] static_template = {
        "inline static @ret@ @name@ (@arg_list@) {",
        "#ifndef SIDL_FASTCALL_EAGER_CACHING",
        "  #ifdef SIDL_FASTCALL_DISABLE_CACHING",
        "    @long_name@_pointer_t __fun_@long_name@;",
        "    __cache_@long_name@(__fun_@long_name@);",
        "  #else",
        "    if(!__fun_@long_name@) __cache_@long_name@(__fun_@long_name@);",
        "  #endif",
        "#endif",
        "  return (*__fun_@long_name@)(@args@);",
        "}",
      };

      P.print(method.isStatic() ? static_template : template);
      if(has_rarray) PR.print(method.isStatic() ? static_template : template);
    }
  }
  

  /** generates implementations for native function call dispatch routines.
   *  This is only used for methods refering to imcomplete types. Otherwise,
   *  we generate an inline definition.
   */
  public static void generateFastCallDispatchImpl(LanguageWriterForCxx d_writer,
                                                  Context d_context,
                                                  Extendable ext,
                                                  Method method)
    throws CodeGenerationException
  {
    if(!supportsFastCalls(method))
      return;
    
    HashMap rmap = null, map = new HashMap();
    computeFastCallKeywordMap(map, d_context, ext, method, true, true);
    TemplatePrinter PR = null, P = new TemplatePrinter(d_writer, map);

    if(method.hasRarray()) {
      rmap = new HashMap();
      computeFastCallKeywordMap(rmap, d_context, ext, method, false, true);
      PR = new TemplatePrinter(d_writer, rmap);
    }
    
    //implementation of the __cache_ functions
    {
      final String[] template = {
        "void @class_type_qualified@::__cache_@long_name@(@long_name@_pointer_t &_f, sidl_generic_class_t *&_t) {",
        "  @class_type@::ior_t *_ior = _get_ior();",
        "  if(_ior && _ior->d_epv->f_@long_name@_native.lang == BABEL_LANG_CPP) {",
        "    @method_p_type@ *p = (@method_p_type@ *)_ior->d_epv->f_@long_name@_native.opaque;",
        "    _t = *((sidl_generic_class_t **)((void*)((int8_t *)@self@ + p->offset)));",
        "#ifdef SIDL_FASTCALL_PMF_EXTENSION",
        "    _f = (@long_name@_pointer_t)(_t->*(p->method_p.@long_name@__method_p));",
        "#else",        
        "    _f = p->method_p.@long_name@__method_p;",
        "#endif",
        "  }",
        "  else {",
        "    @ret@ (@class_type@::*tmp)(@arg_list@) = &@class_type@::@name@_default_stub;",
        "#ifdef SIDL_FASTCALL_PMF_EXTENSION",
        "    typedef @ret@ (*pmf_t)(@pmf_stub_arg_list@);",
        "    pmf_t tmp_pmf = (pmf_t) (this->*tmp);",
        "    _f = reinterpret_cast<@long_name@_pointer_t>(tmp_pmf);",
        "#else",
        "    assert(sizeof(tmp) == sizeof(_f) && ",
        "      \"not a standard compliant compiler\");",
        "    _f = reinterpret_cast<@long_name@_pointer_t>(tmp);",
        "#endif",
        "    _t = reinterpret_cast<sidl_generic_class_t *>(this);",
        "  }",
        "}",
      };

      final String[] static_template = {
        "void @class_type_qualified@::__cache_@long_name@(@long_name@_pointer_t &f) {",
        "  const @class_type@::sepv_t *_sepv = @class_type@::_get_sepv();",
        "  if(_sepv && _sepv->f_@long_name@_native.lang == BABEL_LANG_CPP)",
        "    f  = (@long_name@_pointer_t)_sepv->f_@long_name@_native.opaque;",
        "  else",
        "    f = &@class_type@::@name@_default_stub;",
        "}",
      };
      
      P.print(method.isStatic() ? static_template : template);
    }

    //implementation for the dispatch function. this is generated inline
    //unless the function deals with incomplete types
    if(refersIncompleteTypes(method)) {
      final String[] template = {
        "@ret@ @class_type_qualified@::@name@ (@arg_list@) {",
        "#ifndef SIDL_FASTCALL_EAGER_CACHING",
        "  #ifdef SIDL_FASTCALL_DISABLE_CACHING",
        "    @long_name@_pointer_t __fun_@long_name@;",
        "    sidl_generic_class_t *__this_@long_name@;",
        "    __cache_@long_name@(__fun_@long_name@, __this_@long_name@);",
        "  #else",
        "    if(!__fun_@long_name@) __cache_@long_name@(__fun_@long_name@, __this_@long_name@);",
        "  #endif",
        "#endif",
        "#ifdef SIDL_FASTCALL_PMF_EXTENSION",
        "  return (__fun_@long_name@)(@pmf_args@);",
        "#else",
        "  return (__this_@long_name@->*__fun_@long_name@)(@args@);",
        "#endif",
        "}"
      };

      final String[] static_template = {
        "@ret@ @class_type_qualified@::@name@ (@arg_list@) {",
        "#ifndef SIDL_FASTCALL_EAGER_CACHING",
        "  #ifdef SIDL_FASTCALL_DISABLE_CACHING",
        "    @long_name@_pointer_t __fun_@long_name@;",
        "    __cache_@long_name@(__fun_@long_name@);",
        "  #else",
        "    if(!__fun_@long_name@) __cache_@long_name@(__fun_@long_name@);",
        "  #endif",
        "#endif",
        "  return (*__fun_@long_name@)(@args@);",
        "}",
      };
      
      String[] t = method.isStatic() ? static_template : template;
      P.print(t);
      if(method.hasRarray()) PR.print(t);
    }
  }

  /** generates a small code template for the skeletton that casts a method function
   *  pointer to a "generic" function pointer and initializes the C++ specific
   *  data structure for native function calls.
   */
  public static void generateFastCallEPVInitialization(LanguageWriterForCxx d_writer,
                                                       Context d_context,
                                                       Extendable ext,
                                                       Method method,
                                                       int index)
    throws CodeGenerationException
  {
    HashMap map = new HashMap();
    computeFastCallKeywordMap(map, d_context, ext, method, true, false);
    map.put("index", "" + index);
    map.put("ior_object", IOR.getObjectName(ext.getSymbolID()));
    TemplatePrinter P = new TemplatePrinter(d_writer, map);
    
    final String[] template = {
      "//native EPV initialization for non-static method @long_name@",
      "{",
      "  typedef @ret@ (sidl_generic_class_t::*@long_name@_pointer_t)(@arg_list@);",
      "  @ret@ (@impl_class@::*t)(@arg_list@) = ",
      "    &@impl_name@;",
      "    assert(sizeof(t) == sizeof(@long_name@_pointer_t) && ",
      "      \"not a standard compliant compiler\");",
      "  s_native_method_buffer[@index@].method_p.@long_name@__method_p =",
      "    reinterpret_cast<@long_name@_pointer_t>(t);",
      "  s_native_method_buffer[@index@].offset = offsetof(@ior_object@, d_data);",
      "  epv->@ior_entry@.lang = BABEL_LANG_CPP;",
      "  epv->@ior_entry@.opaque = &s_native_method_buffer[@index@];",
      "}",
      ""
    };

    final String[] static_template = {
      "//native EPV initialization for static method @long_name@",
      "sepv->@ior_entry@.lang = BABEL_LANG_CPP;",
      "sepv->@ior_entry@.opaque = (void *) &@impl_name@;",
      ""
    };

    P.print(method.isStatic() ? static_template : template);
  }
  
  /**
   * returns a list of initializers to be invoked in the constructor
   */
  public static List getFastCallStubInitializers(Context d_context,
                                                 Extendable ext) {
    List result = new LinkedList();
    if(d_context.getConfig().getFastCall()) {
      for(Iterator m = Cxx.getStubMethodList(ext).iterator(); m.hasNext(); ) {
        final Method method = (Method) m.next();
        if(!method.isStatic() && supportsFastCalls(method)) {
          String name = method.getLongMethodName();
          result.add("__fun_" + name + "(NULL)");
          result.add("__this_" + name + "(NULL)");
        }
      }
    }
    return result;
  }

  /**
   * generates a helper function that caches all the method entry points
   */
  public static void generateFastCallCacheEntryPoints(LanguageWriterForCxx d_writer,
                                                      Context d_context,
                                                      Extendable ext) {
    d_writer.writeCommentLine("(re-)cache (native) method entry points");
    d_writer.printlnUnformatted("#ifndef SIDL_FASTCALL_DISABLE_CACHING");
    d_writer.println("virtual void _cache_entry_points() {");
    d_writer.tab();

    //call the same method recursively for parent classes first
    Object [] parents = Utilities.sort(ext.getParents(false)).toArray();
    for(int i = 0; i < parents.length; ++i) {
      Extendable parent = (Extendable) parents[i];
      String name = Cxx.getSymbolName(parent.getSymbolID(), "");
      String guard = IOR.getNativeEPVGuard(parent);
      d_writer.printlnUnformatted("#ifdef " + guard);
      d_writer.println(name + "::_cache_entry_points();");
      d_writer.printlnUnformatted("#endif /*" + guard + "*/");
    }

    boolean has_static = false;
    for(Iterator m = Cxx.getStubMethodList(ext).iterator(); m.hasNext(); ) {
      final Method method = (Method) m.next();
      final String lname = method.getLongMethodName();
      if(!Cxx.supportsFastCalls(method))
        continue;
      if(!method.isStatic())
        d_writer.println("__cache_" + lname +
                         "(__fun_" + lname + "," +
                         " __this_" + lname + ");");
      else
        has_static = true;
    }
    d_writer.backTab();
    d_writer.println("}");
    d_writer.printlnUnformatted("#endif");

    if(has_static) {
      d_writer.println();
      d_writer.writeCommentLine("a inner class used to initialize static" +
                                " method entry points");
      d_writer.printlnUnformatted("#ifndef SIDL_FASTCALL_DISABLE_CACHING");
      d_writer.println("static void _cache_static_entry_points() {");
      d_writer.tab();
      for(Iterator m = Cxx.getStubMethodList(ext).iterator(); m.hasNext(); ) {
        final Method method = (Method) m.next();
        final String lname = method.getLongMethodName();
        if(!Cxx.supportsFastCalls(method))
          continue;
        if(method.isStatic())
          d_writer.println(getSymbolNameWithoutLeadingColons(ext, null) +
                           "::__cache_" + lname +
                           "(__fun_" + lname + ");");
      }
      d_writer.backTab();
      d_writer.println("}");
      d_writer.printlnUnformatted("#endif");
    }
  }

  /**
   * declare helper variables for static function entry point caches
   */
  public static void generateFastCallStaticDeclarations(LanguageWriterForCxx d_writer,
                                                        Context d_context,
                                                        Extendable ext) {
    d_writer.println();
    d_writer.writeCommentLine("allocate memory for static function entry point caches");
    boolean has_static = false;
    for(Iterator m = Cxx.getStubMethodList(ext).iterator(); m.hasNext(); ) {
      final Method method = (Method) m.next();
      if(method.isStatic() && Cxx.supportsFastCalls(method)) {
        has_static = true;
        break;
      }
    }

    if(has_static) {
      d_writer.printlnUnformatted("#ifndef SIDL_FASTCALL_DISABLE_CACHING");
      for(Iterator m = Cxx.getStubMethodList(ext).iterator(); m.hasNext(); ) {
        final Method method = (Method) m.next();
        if(method.isStatic() && Cxx.supportsFastCalls(method)) {
          String name = method.getCorrectMethodName();
          d_writer.printlnUnformatted(getSymbolName(ext) + "::" +
                                      name + "_pointer_t " +
                                      getSymbolNameWithoutLeadingColons(ext, null) +
                                      "::__fun_" + name + " = NULL;");
        }
      }
      d_writer.printlnUnformatted("#endif");
    
      //This is a dummy object that caches static method entry points
      //in its constructor.
      String name = ext.getShortName() + "_initialize_static_epv";
      d_writer.println();
      d_writer.printlnUnformatted("#ifdef SIDL_FASTCALL_EAGER_CACHING");
      d_writer.println("class " + name + " {");
      d_writer.println("public:");
      d_writer.tab();
      d_writer.println(name + "() {");
      d_writer.tab();
      d_writer.println(getSymbolNameWithoutLeadingColons(ext, null) +
                       "::_cache_static_entry_points();");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("};");
      d_writer.println();
      d_writer.println("static " + name + " __dummy;");
      d_writer.printlnUnformatted("#endif");
    }
    
    d_writer.println();
  }
  
  public static void generateMethodSignature( LanguageWriterForCxx writer, 
                                              Context context,
                                              Method method, 
                                              String altcomment,
                                              int role, 
                                              boolean rarrays,
                                              boolean hasInvariants)
    throws CodeGenerationException
  {
    generateMethodSignature(writer,
                            context,
                            method,
                            altcomment,
                            role,
                            rarrays,
                            hasInvariants,
                            "");
  }
  
  public static void generateMethodSignature( LanguageWriterForCxx writer, 
                                              Context context,
                                              Method method, 
                                              String altcomment,
                                              int role, 
                                              boolean rarrays,
                                              boolean hasInvariants,
                                              String suffix)
    throws CodeGenerationException
  {
    if ( method == null ) { return; }
    Comment comment = method.getComment();
    String methodDeclName = method.getCorrectMethodName();
    writer.writeComment( comment, altcomment );
    boolean canThrowNullIORException = 
      ( (role == FILE_ROLE_STUB) && context.getConfig().makeCxxCheckNullIOR() );
    boolean isImpl = (role == Cxx.FILE_ROLE_IMPL);
    List vArgs = null;
    if(rarrays)
      vArgs = method.getArgumentListWithIndices();
    else
      vArgs = method.getArgumentListWithOutIndices();

    if ( method.isStatic() ) { 
      writer.print("static ");
    }
    writer.println( Cxx.getReturnString( method.getReturnType(), context ) );

    if(isImpl)
      writer.print( methodDeclName + "_impl");
    else
      writer.print( methodDeclName );

    writer.print(suffix);
      
    if ( vArgs.size() > 0 ) { 
      writer.println(" (");
      writer.tab();
      generateArgumentList( writer, method, context, rarrays, 
                            (role == FILE_ROLE_STUB));
      writer.println();
      writer.backTab();
      writer.println(")");
      generateThrowsList( writer, method, canThrowNullIORException, 
                          hasInvariants, context );
      writer.println(";");
    } else { 
      writer.print("() ");
      generateThrowsList( writer, method, canThrowNullIORException, 
                          hasInvariants, context );
      writer.print(";");
    }
    writer.println();
  }

  public static void generateInlineMethodSignature( LanguageWriterForCxx writer,
                                                    Context context,
                                                    Method method, 
                                                    String altcomment,
                                                    int role, boolean isSuper, 
                                                    boolean rarrays,
                                                    boolean hasInvariants)
    throws CodeGenerationException
  {
    if ( method == null ) { return; }
    Comment comment = method.getComment();
    String methodDeclName = method.getShortMethodName();
    writer.writeComment( comment, altcomment );
    boolean canThrowNullIORException = 
      ( (role == FILE_ROLE_STUB) && context.getConfig().makeCxxCheckNullIOR() );
    List vArgs = null;
    if(rarrays)
      vArgs = method.getArgumentListWithIndices();
    else
      vArgs = method.getArgumentListWithOutIndices();

    if(!isSuper)
      writer.print("inline ");

    if ( method.isStatic() ) { 
      writer.print("static ");
    }
    writer.println( Cxx.getReturnString( method.getReturnType(), context ) );
    writer.print( methodDeclName );
    
    if ( vArgs.size() > 0 ) { 
      writer.println(" (");
      writer.tab();
      generateArgumentList( writer, method, context,
                            rarrays, (role == FILE_ROLE_STUB));
      writer.println();
      writer.backTab();
      writer.println(")");
      generateThrowsList( writer, method, canThrowNullIORException, 
                          hasInvariants, context );
      writer.println("{");
    } else { 
      writer.print("() ");
      generateThrowsList( writer, method, canThrowNullIORException, 
                          hasInvariants, context );
      writer.print("{");
    }
    writer.println();
  }

  public static void  generateArgumentList( LanguageWriterForCxx writer, 
                                            Method method, 
                                            Context context,
                                            boolean rarrays,
                                            boolean isStub)
    throws CodeGenerationException 
  { 
    List args = null;
    if(rarrays)
      args = method.getArgumentListWithIndices();
    else 
      args = method.getArgumentListWithOutIndices();

    for( Iterator a = args.iterator(); a.hasNext(); ) { 
      Argument arg = (Argument) a.next();
      writer.print( getArgumentString( arg, context, rarrays, isStub ) );
      if ( a.hasNext() ) { 
        writer.println(",");
      }
    }
  }

  public static void generateThrowsList( LanguageWriterForCxx writer, 
					 Method method, 
					 boolean canThrowNullIORException,
                                         boolean hasInvariants,
                                         Context context) { 
    Set     exceptions = method.getThrows();
    boolean hasPre     = method.hasPreClause();
    boolean hasPost    = method.hasPostClause();

    if ( exceptions == null || exceptions.isEmpty() ) { 
      if (  canThrowNullIORException 
            && context.getConfig().makeCxxCheckNullIOR()) 
        { 
          writer.writeCommentLine("throws:");
          writer.writeCommentLine("   ::sidl::NullIORException");
          if (hasPre) {
            writer.writeCommentLine("   ::sidl::PreViolation");
          }
          if (hasPost) {
            writer.writeCommentLine("   ::sidl::PostViolation");
          }
          if (hasInvariants) {
            writer.writeCommentLine("   ::sidl::InvViolation");
          }
        } else { 
	writer.println( "throw () " );
      }
    } else {  /* Throws implicit and/or explicit exceptions */
      boolean hasPreExc  = false;
      boolean hasPostExc = false;
      boolean hasInvExc  = false;
      if (method.hasExplicitExceptions()) {
        writer.writeCommentLine("throws:");
        if ( canThrowNullIORException ) { 
          writer.writeCommentLine("       ::sidl::NullIORException");
        }
        for( Iterator e = exceptions.iterator(); e.hasNext(); ) { 
          SymbolID id    = (SymbolID) e.next();
          String excName = Cxx.getObjectName(id);
          hasPreExc  = hasPreExc  || excName.equals("::sidl::PreViolation");
          hasPostExc = hasPostExc || excName.equals("::sidl::PostViolation");
          hasInvExc  = hasInvExc  || excName.equals("::sidl::InvViolation");
          
          writer.writeCommentLine("   " + excName);
        }
      } else { 
        /*
         * Should at least output the "standard" implicit set...
         */
        if (hasPre || hasPost || hasInvariants) {
          writer.writeCommentLine("throws:");
        }
      }
      
      if (hasPre && (!hasPreExc)) {
        writer.writeCommentLine("   ::sidl::PreViolation");
      }
      if (hasPost && (!hasPostExc)) {
        writer.writeCommentLine("   ::sidl::PostViolation");
      }
      if (hasInvariants && (!hasInvExc)) {
        writer.writeCommentLine("   ::sidl::InvViolation");
      }
    }
    return;
  }

  public static String generateThrowsList(Method method, 
                                          boolean canThrowNullIORException,
                                          boolean hasInvariants,
                                          Context context) 
  { 
    Set exceptions   = method.getThrows();
    StringBuffer ret = new StringBuffer();
    boolean hasPre   = method.hasPreClause();
    boolean hasPost  = method.hasPostClause();
    
    if ( exceptions == null || exceptions.isEmpty() ) 
      {
        if (  canThrowNullIORException 
              && context.getConfig().makeCxxCheckNullIOR()) 
          {
            ret.append("// throws:\n");
            ret.append("//     ::sidl::NullIORException\n");
            if (hasPre) {
              ret.append("//   ::sidl::PreViolation\n");
            }
            if (hasPost) {
              ret.append("//   ::sidl::PostViolation\n");
            }
            if (hasInvariants) {
              ret.append("//   ::sidl::InvViolation\n");
            }
          } else { 
          ret.append("throw () \n");
        }
      } else { 
      boolean hasPreExc  = false;
      boolean hasPostExc = false;
      boolean hasInvExc  = false;
      if (!method.getExplicitThrows().isEmpty()) {
        ret.append("// throws:\n");
        if (  canThrowNullIORException 
              && context.getConfig().makeCxxCheckNullIOR()) 
          { 
            ret.append("//   ::sidl::NullIORException\n");
          }
        for( Iterator e = exceptions.iterator(); e.hasNext(); ) { 
          SymbolID id = (SymbolID) e.next();
          String excName = Cxx.getObjectName(id);
          hasPreExc  = hasPreExc  || excName.equals("::sidl::PreViolation");
          hasPostExc = hasPostExc || excName.equals("::sidl::PostViolation");
          hasInvExc  = hasInvExc  || excName.equals("::sidl::InvViolation");
          ret.append("//   ").append(excName).append('\n');
        }
      } else {
        /*
         * Should at least output the "standard" implicit set...
         */
        if (hasPre || hasPost || hasInvariants) {
          ret.append("// throws:\n");
        }
      }

      if (hasPre && (!hasPreExc)) {
        ret.append("//   ::sidl::PreViolation\n");
      }
      if (hasPost && (!hasPostExc)) {
        ret.append("//   ::sidl::PostViolation\n");
      }
      if (hasInvariants && (!hasInvExc)) {
        ret.append("//   ::sidl::InvViolation\n");
      }
    }
    return ret.toString();
  }

  /**
   *  Generate Signature for the exception throwing method
   */
  public static void generateExceptionSetSignature( LanguageWriterForCxx writer,
                                                    SymbolID id, Set throw_set,
                                                    int index, boolean stub, 
                                                    boolean isSuper) 
    throws CodeGenerationException {
    

    writer.println("void");
    if(stub) {
      writer.print(getMethodStubName(id,"throwException" +index, isSuper));
    } else {
      writer.print("throwException" +index);
    }
    writer.println("(" );
    writer.tab();
    writer.println("const char* methodName,");
    writer.println(IOR.getExceptionFundamentalType() + "_exception");
    writer.backTab();
    writer.println(")");
    writer.tab();
    writer.writeCommentLine("throws:");
    if(throw_set.size() > 1) {
      for(Iterator set_i = throw_set.iterator(); set_i.hasNext();) {
        SymbolID ex_id = (SymbolID) set_i.next();;
        
        writer.writeCommentLine("   " + Cxx.getObjectName(ex_id) );
        
      }
    }
    writer.backTab();
  }

  /**
   *  Generate Signature for the exception throwing method
   */
  public static void generateExceptionSetBody( LanguageWriterForCxx writer, 
                                               Set throw_set,
                                               Context context) {

    Object [] exceptions = throw_set.toArray();
    Arrays.sort(exceptions, new LevelComparator(context.getSymbolTable()));
    writer.println("void * _p = 0;");
    writer.println(IOR.getExceptionFundamentalType() + "throwaway_exception;");
    writer.println();
    for( int count=0; count<exceptions.length; ++count) {
      SymbolID exid = (SymbolID)exceptions[count];
      writer.println("if ( (_p=(*(_exception->d_epv->f__cast))(_exception->d_object,"
                       + " \"" + exid.getFullName() + "\", &throwaway_exception)) != 0 ) {");
      writer.tab();
      writer.println(IOR.getObjectName(exid) + " * _realtype = " 
                       + Cxx.reinterpretCast(IOR.getObjectName(exid)+"*" , "_p") + ";");
      writer.println("(*_exception->d_epv->f_deleteRef)(_exception->d_object, &throwaway_exception);");
      writer.writeCommentLine("Note: alternate constructor does not "
                                + "increment refcount.");
      writer.println(Cxx.getObjectName(exid) + " _resolved_exception = " +
                     Cxx.getObjectName(exid) + "( _realtype, false );");
      try {
        writer.pushLineBreak(false);
        /* We use C here to avoid allocating memory for the string.
         * C++ passes std::strings, which require memory allocation. 
         */
        Symbol ex_symbol = context.getSymbolTable().lookupSymbol(exid);
        String dobj = ex_symbol.isInterface() ? "->d_object" : "";
        writer.print("(_resolved_exception._get_ior()->d_epv->f_add) "+
                     "(_resolved_exception._get_ior()" + dobj +" , __FILE__, "+
                     "__LINE__, methodName, &throwaway_exception);");
        //writer.println("_resolved_exception.add(__FILE__,__LINE__, \"C++ stub.\");");
      }
      finally {
        writer.popLineBreak();
      }
      writer.println("throw _resolved_exception;");
      writer.backTab();
      writer.println("}");
    }
    writer.writeCommentLine("Any unresolved exception is treated as LangSpecificException");
    writer.println("::sidl::LangSpecificException _unexpected = ::sidl::LangSpecificException::_create();");
    try {
      writer.pushLineBreak(false);
      writer.println("_unexpected.add(__FILE__,__LINE__, \"Unknown method\");");
      writer.println("_unexpected.setNote(\"Unexpected exception received by C++ stub.\");");
    }
    finally {
      writer.popLineBreak();
    }
    writer.println("throw _unexpected;");
  }


  /**
   *  Determines if the stub function should be inlined based on the
   *  simplicity of the arguments and return type.  The logic is that the
   *  stub function should be inlined unless it throws an exception or 
   *  one or more of it's arguments
   *  or return type is: bool, dcomplex, fcomplex, string, object out/inout,
   *  or array out/inout.
   *  RETURNS: True if babel method should be inlined
   */
  public static boolean inlineStub(Method m) {
    Set thrws = m.getThrows();
    if (!thrws.isEmpty()) {
      return false;
    }

    int return_type = m.getReturnType().getType();    

    if(return_type == Type.BOOLEAN || return_type == Type.DCOMPLEX ||
       return_type == Type.FCOMPLEX || return_type >= Type.CLASS ||
       return_type == Type.STRING)
      return false;

    List args = m.getArgumentList();
    for (Iterator a = args.iterator(); a.hasNext(); ) {
      Argument arg = (Argument) a.next();
      int arg_type = arg.getType().getType();
      if(arg_type == Type.BOOLEAN || arg_type == Type.DCOMPLEX ||
         arg_type == Type.FCOMPLEX || arg_type == Type.STRING ||
         arg_type >= Type.CLASS) // && (arg_mode == Argument.OUT || arg_mode == Argument.INOUT)))
        return false;
    }

    return true;
  }

  public static String generateFunctionDeclaration(Method m, 
                                                   Extendable d_ext, 
                                                   Context context,
                                                   String d_self, 
                                                   boolean isSuper, 
                                                   boolean rarrays,
                                                   String suffix)
    throws CodeGenerationException 
  {
    List vArgs = null;
    if(rarrays)
      vArgs = m.getArgumentListWithIndices();
    else 
      vArgs = m.getArgumentListWithOutIndices();

    final int nargs = vArgs.size();
    // everything through first "{"
    StringBuffer func_decl = new StringBuffer( nargs * 32 );    
    String methodDeclName = m.getCorrectMethodName();
    Type return_type = m.getReturnType();

    func_decl.append( Cxx.getReturnString( return_type, context ) );
    func_decl.append( "\n" );
    func_decl.append( Cxx.getMethodStubName( d_ext.getSymbolID(), 
                                             methodDeclName, isSuper ) );
    func_decl.append(suffix);
    func_decl.append( "( " );

    // writeup the argument lists
    for( Iterator it = vArgs.iterator(); it.hasNext(); ) { 
      Argument arg = (Argument) it.next();
      func_decl.append( Cxx.getArgumentString( arg, context, rarrays, true ) );
      if ( it.hasNext() ) { 
        func_decl.append(", ");
      }
    }
    func_decl.append(" )\n");
    func_decl.append(Cxx.generateThrowsList(m, true, d_ext.hasInvClause(true), 
                     context ));
    
    return func_decl.toString().trim();
  }

  /**
   * Returns the name of the IOR cache variable for the passed in 
   * interface.
   
   * @param ifc the <code>Interface</code> the cache variable
   *            name is based on.
   */
  public static String getIORCacheVariable(Interface ifc) {
    return IOR.getSymbolName(ifc.getSymbolID())+"_IORCache";
  }


  /**
   * Prints a string that initializes the cache variable for the
   * passed in interface.
   *
   * @param writer Launguage writer to output on.
   * @param ifc the <code>Interface</code> the cache variable
   *            name is based on.
   * @param self String giving the name of the ior variable to be 
   *             assigned to the cache
   * @param inList True if this initialization is in a C++ 
   *               initialization list.  False prints a normal expression.
   */
  public static void initializeLocalIOR(LanguageWriterForCxx writer, 
                                        Interface ifc, String self,
                                        boolean inList) {
    //Every Interface has a localIOR, except for BaseInterface
    if(!BabelConfiguration.getBaseInterface().equals(ifc.getSymbolID().getFullName())) {
      if(inList) {
        writer.print(getIORCacheVariable(ifc)+"((ior_t*) "+self+")"); 
      } else {
        writer.println(getIORCacheVariable(ifc)+" = (ior_t*) "+self+";");
      }
    }
  }


  /**
   * Prints cache initialization for all parent interfaces of this 
   * extendable.  (For use in _set_ior())
   *
   * @param writer Launguage writer to output on.
   * @param ext the <code>Extendable</code> were generating this
   *            initialization for.
   * @param self String giving the name of the ior variable to be 
   *             assigned to the cache.
   */
  public static void writeInterfaceCacheInitialization(LanguageWriterForCxx writer,
                                                       Extendable ext, 
                                                       String self) 
    throws CodeGenerationException {
    Collection parentInterfaces = ext.getParentInterfaces(true);

    /** If this stub is being set to an interface type, invaidate all IOR
     * Cache entries */
    if(ext.isInterface()) {
      for(Iterator iter = parentInterfaces.iterator(); iter.hasNext();) {
        Interface pInt = (Interface) iter.next();
        if(!BabelConfiguration.getBaseInterface().equals(pInt.getSymbolID().getFullName())) {
          writer.println(Cxx.getIORCacheVariable(pInt) + " = NULL;");
        }
      }
      
      /** If this stub is being set to a class type, initialize
       *  all IOR Caches entries */
    } else {
      
      writer.println("if( " + self + " != NULL ) {");
      writer.tab();
      for(Iterator iter = parentInterfaces.iterator(); iter.hasNext();) {
        Interface pInt = (Interface) iter.next();
        if(!BabelConfiguration.getBaseInterface().equals(pInt.getSymbolID().getFullName())) {
          writer.println(Cxx.getIORCacheVariable(pInt) + " = " + 
                         IOR.classToInterfacePtr((Class)ext, pInt, self) + ";");
        }
      }
      writer.backTab();

      /** However, if the new self pointer is null, invalidate all IOR cache
       * entries. */
      writer.println("} else {");
      writer.tab();
      for(Iterator iter = parentInterfaces.iterator(); iter.hasNext();) {
        Interface pInt = (Interface) iter.next();
        if(!BabelConfiguration.getBaseInterface().equals(pInt.getSymbolID().getFullName())) {
          writer.println(Cxx.getIORCacheVariable(pInt) + " = NULL;");
        }
      }
      writer.backTab();
      writer.println("}");
    }
  }

  /**
   * Prints cache initialization for all parent interfaces of this 
   * extendable.  (For use in _set_ior())
   *
   * @param writer Launguage writer to output on.
   * @param cls the <code>Class</code> were generating this
   *            initialization for.  (Only classes need to call their parent
   *            interface constructors, because they can initialize 
   *            the IOR caches with correct values.  Interfaces can just use
   *            defualt constructors.)
   * @param self String giving the name of the ior variable to be 
   *             assigned to the cache.
   */
  public static void writeCallsToParentInterfaceConstructors(LanguageWriterForCxx writer,
                                                             Class cls, 
                                                             String self) 
    throws CodeGenerationException {
    final String baseInterfaceName = BabelConfiguration.getBaseInterface();
    boolean runOnce = false;
    List interfaces = cls.getAllParentsInOrder();//new ArrayList(cls.getParentInterfaces(true).size());
    for(Iterator iter = interfaces.iterator(); iter.hasNext(); ) {
      Extendable pInt = (Extendable)iter.next();//(Interface)interfaces.remove(interfaces.size()-1);
      //Only need interface constructors
      if(pInt.isInterface() && !baseInterfaceName.equals(pInt.getSymbolID().getFullName()) ) {  
        if (runOnce) {
          writer.println(",");
        }
        writer.print(Cxx.getSymbolName(pInt.getSymbolID(), "") + 
                     "(("+self+"==NULL) ? NULL : " +
                     IOR.classToInterfacePtr(cls, (Interface)pInt, self)+")");
        runOnce = true;
      }
    }
  }


  /**
   * Generates initialization of methods prior to IOR call.
   */
  public static String generateInitialization(Method m, 
                                              Extendable d_ext, 
                                              String d_self,
                                              Context context) 
    throws CodeGenerationException 
  {
    final List vArgs = m.getArgumentList();
    final int nargs = vArgs.size();
    // everything through first "{"
    StringBuffer init_stuff = new StringBuffer( nargs * 128 );
    Type return_type = m.getReturnType();
    
    if ( return_type.getType() != Type.VOID ) {
      init_stuff.append( Cxx.getCxxString( return_type, true, context ) );
        init_stuff.append( " _result;\n");
    }

    return init_stuff.toString().trim();
  }

  /** 
   *  Function generates argument initialization prior to IOR call.
   */
  public static String generatePreIORCall(Method m, 
                                          Extendable d_ext, 
                                          String d_self, 
                                          boolean isSuper, 
                                          boolean rarrays,
                                          Context context) 
    throws CodeGenerationException 
  {
    List vArgs = null;
    if(rarrays)
      vArgs = m.getArgumentListWithIndices();
    else 
      vArgs = m.getArgumentListWithOutIndices();

    final int nargs = vArgs.size();

    StringBuffer pre_ior = new StringBuffer( nargs * 128 );
    
    Type return_type = m.getReturnType();
    SymbolID id = return_type.getSymbolID();

    //cast d_self to the correct type
    if(!m.isStatic() && !isSuper) {
      pre_ior.append("ior_t* const loc_self = ");
      pre_ior.append(Cxx.getLocalIOR(new Type(d_ext.getSymbolID(), context))+"\n");
    }
    switch( return_type.getDetailedType() ) { 
    case Type.BOOLEAN:
      pre_ior.append("sidl_bool _local_result;\n");
      break;
    case Type.VOID:
    case Type.OPAQUE:
    case Type.CHAR:
    case Type.INT:
    case Type.STRUCT:
    case Type.LONG:
    case Type.FLOAT:
    case Type.DOUBLE:
      break;
    case Type.ENUM:
      pre_ior.append("int64_t _local_result;\n" );
      break;
    case Type.FCOMPLEX:
      pre_ior.append("struct sidl_fcomplex _local_result;\n");
      break;
    case Type.DCOMPLEX:
      pre_ior.append("struct sidl_dcomplex _local_result;\n");
      break;
    case Type.STRING:
      pre_ior.append("char * _local_result;\n");
      break;
    case Type.SYMBOL:
      throw new CodeGenerationException( "Type.SYMBOL should have been "
                  + "resolved to\nType.ENUM, Type.CLASS, or Type.INTERFACE");
      //break;
    case Type.CLASS:
      break;
    case Type.INTERFACE:
      break;
    case Type.ARRAY:
      String iorArrayName = IOR.getReturnString(return_type, context,
                                                true, false);
      pre_ior.append(iorArrayName + " _local_result;\n"); 
      break;
    default:
      throw new CodeGenerationException("Unexpected User Defined Return Type" + 
                                        return_type.getType());
    }

    // writeup the argument lists
    for( Iterator it = vArgs.iterator(); it.hasNext(); ) { 
      Argument arg = (Argument) it.next();
      Type type = arg.getType();
      int typeInt = type.getDetailedType();
      String argName = arg.getFormalName();
      int modeInt = arg.getMode();

      switch ( typeInt ) { 
      case Type.OPAQUE:
        break;
      case Type.BOOLEAN:
        switch( modeInt ) { 
        case Argument.OUT:
          pre_ior.append("sidl_bool _local_" + argName + ";\n");
          break;
        case Argument.IN:
        case Argument.INOUT:
          pre_ior.append("sidl_bool _local_" + argName + " = " + argName + 
                         ";\n");
          break;
        }
        break;
      case Type.CHAR:
      case Type.INT:
      case Type.LONG:
      case Type.FLOAT:
      case Type.DOUBLE:
        break;
      case Type.ENUM:
        switch (modeInt) {
        case Argument.OUT:
          pre_ior.append("int64_t _local_" + argName +";\n");
          break;
        case Argument.IN:
        case Argument.INOUT:
          pre_ior.append("int64_t _local_" + argName +" = " + argName + ";\n");
          break;
        }
        break;
      case Type.FCOMPLEX:
        switch( modeInt ) { 
        case Argument.OUT:
          pre_ior.append("struct sidl_fcomplex _local_" + argName + "; \n");
          break;
        case Argument.IN:
        case Argument.INOUT:
          pre_ior.append("struct sidl_fcomplex _local_" + argName + " = {");
          pre_ior.append( argName + ".real(), ");
          pre_ior.append( argName + ".imag() } ; \n");
          break;
        }
        break;
      case Type.DCOMPLEX:
        switch( modeInt ) { 
        case Argument.OUT:
          pre_ior.append("struct sidl_dcomplex _local_" + argName + "; \n");
          break;
        case Argument.IN:
        case Argument.INOUT:
          pre_ior.append("struct sidl_dcomplex _local_" + argName + " = {");
          pre_ior.append( argName + ".real(), ");
          pre_ior.append( argName + ".imag() } ; \n");
          break;
        }
        break;
      case Type.STRING:
        switch( modeInt ) { 
        case Argument.IN:
          break;
        case Argument.OUT:
          pre_ior.append("char * _local_" + argName + " = 0;\n");
          break;
        case Argument.INOUT:
          pre_ior.append("char * _local_" + argName + " = sidl_String_strdup( "
            + argName + ".c_str() );\n");
        }
        break; 
      case Type.SYMBOL:
        throw new CodeGenerationException( "Type.SYMBOL should have been "
                    + "resolved to\nType.ENUM, Type.CLASS, or Type.INTERFACE");
        // break;
      case Type.INTERFACE:
        id = type.getSymbolID();
        switch( modeInt ) { 
        case Argument.IN:
          pre_ior.append(IOR.getObjectName(id) + "* _local_" + 
                         argName + " = " + getIORCall(argName, type) + ";\n");
          break;
        case Argument.OUT:
          pre_ior.append( IOR.getObjectName(id) + "* _local_" + argName 
                          + ";\n" );
          break;
        case Argument.INOUT:
          pre_ior.append(IOR.getObjectName(id) + "* _local_" + 
                         argName + " = " + getIORCall(argName, type) + ";\n");
          //Getting rid of all deleterefing associated with interfaces
          //          pre_ior.append("if (" + argName + "._not_nil()) { " +
          //               argName + ".deleteRef(); }\n");
          break;
        }

      break;
      case Type.CLASS:
        id = type.getSymbolID();
        switch( modeInt ) { 
        case Argument.IN:
          pre_ior.append( IOR.getObjectName(id) + "* _local_" + argName 
                          + " = " + Cxx.getIORCall(argName, type)+";\n" );
          break;
        case Argument.OUT:
          pre_ior.append( IOR.getObjectName(id) + "* _local_" + argName 
            + ";\n" );
          break;
        case Argument.INOUT:
          pre_ior.append( IOR.getObjectName(id) + "* _local_" + argName 
                          + " = " + Cxx.getIORCall(argName, type)+";\n" );
          pre_ior.append( argName + "._set_ior( 0 );\n");
          break;
        }
        break;
      case Type.STRUCT:
        if (Argument.OUT == modeInt ) {
          Struct s = (Struct)Utilities.lookupSymbol(context, 
                                                    type.getSymbolID());
          if (!s.getItems().isEmpty()){
            pre_ior.append(argName + "._destroy();\n");
          }
        }
        break;
      case Type.ARRAY:
        String iorArrayName = IOR.getReturnString(type, 
                                                  context,
                                                  true, false);
        if(rarrays && type.isRarray()) {
          //There's no real difference between getting a in or inout rarray
          // ready to pass, and out arrays are illegal (caught by parser)
          String r_name = arg.getFormalName();
          int aIntType = type.getArrayType().getType();
          String sidl_array_name = IOR.getArrayNameWithoutAsterix(aIntType); 
          int dim = type.getArrayDimension();
          pre_ior.append("int32_t " + r_name + "_lower[" + dim + "], " + r_name
            + "_upper[" + dim + "], " + r_name + "_stride[" + dim + "];\n");
          //figure something out here.
          
          pre_ior.append(sidl_array_name + " " + r_name + "_real;\n");
          pre_ior.append(sidl_array_name + " *" + r_name + temp_postfix 
            + " = &" + r_name + "_real;\n");
        
          List indices = type.getArrayIndexExprs();
          String init_func_name = IOR.getArrayNameForFunctions(aIntType)
                                        + "_init";
          int x = 0;
          for(Iterator i = indices.iterator(); i.hasNext();++x) {
            AssertionExpression ae = (AssertionExpression)i.next();
            pre_ior.append(r_name + "_upper[" + x + "] = " + 
                           ae.accept(new CExprString(), null).toString() +
                           "-1;\n");
          }
          pre_ior.append(init_func_name + "(" + r_name + ", " + r_name 
            + temp_postfix + ", " + type.getArrayDimension() + ", "  + r_name 
            + "_lower, " + r_name + "_upper, " + r_name + "_stride);\n");
        
        } else {
          switch( modeInt ) { 
          case Argument.IN:
            break;
          case Argument.OUT:
            pre_ior.append(iorArrayName + " _local_" + argName + ";\n");
            break;
          case Argument.INOUT:
            pre_ior.append("if (" + argName + ") {\n");
            pre_ior.append("  " + argName + ".addRef();\n");
            pre_ior.append("}\n");
            pre_ior.append(iorArrayName + " _local_" + argName + 
                           " = " + Cxx.getIORCall(argName, type) +
                           ";\n");
            break;
          }
        }
        break;
       
      default:
        throw new CodeGenerationException("Unexpected User Defined Return Type" 
                    + return_type.getType());
      }      
    }
 
    // if method throws exceptions...
    if (m.getThrows().size()>0) { 
      pre_ior.append("sidl_BaseInterface__object * _exception;\n");
    }
    pre_ior.append("/*pack args to dispatch to ior*/\n");

    return pre_ior.toString().trim();
  }

  public static String generateIORCall(Method m, 
                                       Extendable d_ext, 
                                       Context context,
                                       String d_self, 
                                       boolean isSuper, 
                                       boolean rarrays) 
    throws CodeGenerationException 
  {
    final List vArgs = m.getArgumentList();
    final int nargs = vArgs.size();
    // everything through first "{"
    StringBuffer  ior_call = new StringBuffer( nargs * 128 );
    String extra_close_paren = "";
    String longMethodName = m.getLongMethodName();
    Type return_type = m.getReturnType();

    switch( return_type.getDetailedType() ) { 
    case Type.VOID:
      break;
    case Type.OPAQUE:
    case Type.CHAR:
    case Type.INT:
    case Type.LONG:
    case Type.FLOAT:
    case Type.DOUBLE:
      ior_call.append("_result = ");
      break;
    case Type.BOOLEAN:
    case Type.ENUM:
    case Type.FCOMPLEX:
    case Type.DCOMPLEX:
    case Type.STRING:
      ior_call.append("_local_result = ");
      break;
    case Type.SYMBOL:
      throw new CodeGenerationException( "Type.SYMBOL should have been resolved"
                  + " to\nType.ENUM, Type.CLASS, or Type.INTERFACE");
      //break;
    case Type.CLASS:
    case Type.INTERFACE:
      ior_call.append("_result = " + Cxx.getReturnString( return_type, context ) + "( ");
      extra_close_paren = ", false)";
      break;
    case Type.ARRAY:
      ior_call.append("_local_result = ");
      break;
    case Type.STRUCT:
      ior_call.append("_result = ");
      break;
    default:
      throw new CodeGenerationException("Unexpected User Defined Return Type" + 
                                        return_type.getType());
    }

    if(isSuper) {
      ior_call.append( "(*(superEPV->f_" + m.getLongMethodName() + "))(" 
        + d_self );
      if ( nargs > 0 ) { ior_call.append(", "); }
    } else {
      if ( m.isStatic() ) { 
        ior_call.append("( _get_sepv()->" + IOR.getVectorEntry(longMethodName) 
          + ")( ");
      } else { 
        ior_call.append( "(*(loc_self->d_epv->f_" + m.getLongMethodName() 
          + "))(" + d_self );
        if ( nargs > 0 ) { ior_call.append(", "); }
      }
    }

    // writeup the argument lists
    for( Iterator it = vArgs.iterator(); it.hasNext(); ) { 
      Argument arg = (Argument) it.next();
      Type type = arg.getType();
      int typeInt = type.getDetailedType();
      String argName = arg.getFormalName();
      String mode = "/* " + Cxx.argComment( arg ) + " */ ";
      int modeInt = arg.getMode();
      ior_call.append( mode );
      switch ( typeInt ) { 
      case Type.OPAQUE:
        switch( modeInt ) { 
        case Argument.IN:
          ior_call.append( argName );
          break;
        case Argument.OUT:
        case Argument.INOUT:
          ior_call.append( "&" + argName );
          break;
        }
        break;
      case Type.BOOLEAN:
        switch( modeInt ) { 
        case Argument.IN:
          ior_call.append("_local_" + argName );          
          break;
        case Argument.OUT:
        case Argument.INOUT:
          ior_call.append("&_local_" + argName);
          break;
        }
        break;
      case Type.CHAR:
      case Type.INT:
      case Type.LONG:
      case Type.FLOAT:
      case Type.DOUBLE:
        switch( modeInt ) { 
        case Argument.IN:
          ior_call.append( argName );
          break;
        case Argument.OUT:
        case Argument.INOUT:
          ior_call.append( "&" + argName );
          break;
        }
        break;
      case Type.ENUM:
        switch( modeInt ) { 
        case Argument.IN:
          ior_call.append( "_local_" + argName );
          break;
        case Argument.OUT:
        case Argument.INOUT:
          ior_call.append( "&_local_" + argName );
          break;
        }
        break;
      case Type.FCOMPLEX:
        switch( modeInt ) { 
        case Argument.IN:
          ior_call.append("_local_" + argName );
          break;
        case Argument.OUT:
        case Argument.INOUT:
          ior_call.append("&_local_" + argName );
          break;
        }
        break;
      case Type.DCOMPLEX:
        switch( modeInt ) { 
        case Argument.IN:
          ior_call.append("_local_" + argName );
          break;
        case Argument.OUT:
        case Argument.INOUT:
          ior_call.append("&_local_" + argName );
          break;
        }
        break;
      case Type.STRING:
        switch( modeInt ) { 
        case Argument.IN:
          ior_call.append( argName + ".c_str()" );
          break;
        case Argument.OUT:
        case Argument.INOUT:
          ior_call.append("&_local_" + argName );
          break;
        }
        break; 
      case Type.SYMBOL:
        throw new CodeGenerationException( "Type.SYMBOL should have been "
                    + "resolved to\nType.ENUM, Type.CLASS, or Type.INTERFACE");
        // break;
      case Type.INTERFACE:
      case Type.CLASS:
        switch( modeInt ) { 
        case Argument.IN:
          ior_call.append( "_local_" + argName );

          break;
        case Argument.OUT:
        case Argument.INOUT:
          ior_call.append( "&_local_" + argName );
          break;
        }
        break;
      case Type.ARRAY:
        if(rarrays && type.isRarray()) {
          if(modeInt == Argument.INOUT) ior_call.append("&");
          ior_call.append(argName + temp_postfix);
        } else {
          switch( modeInt ) { 
          case Argument.IN:
            ior_call.append( Cxx.getIORCall(argName, type) );
            break;
          case Argument.OUT:
          case Argument.INOUT:
            ior_call.append("&_local_" + argName);
            break;
          }
        }
        break;
      case Type.STRUCT:
        ior_call.append("reinterpret_cast< ");
        if (Argument.IN == modeInt) ior_call.append("const ");
        ior_call.append(IOR.getStructName(type.getSymbolID()));
        ior_call.append("* >(&");
        ior_call.append(argName);
        ior_call.append(")");
        break;
      default:
        throw new CodeGenerationException("Unexpected User Defined Return Type" 
                    + return_type.getType());
      }  
      if ( it.hasNext() ) { 
        ior_call.append(", ");
      }
    }
 
    // if method throws exceptions...
    if (m.getThrows().size()>0) { 
      if ( nargs > 0 || (!m.isStatic())) { // if other args are listed.
        ior_call.append( ", " );
      }
      ior_call.append("&_exception");
    }

    ior_call.append(" )" + extra_close_paren + ";\n");
    ior_call.append("/*dispatch to ior*/\n");
    return ior_call.toString().trim();
  }

  public static String generatePostIORCleanup(Method m,
                                              boolean rarrays)
    throws CodeGenerationException 
  {
    StringBuffer buf = new StringBuffer();
    final List vArgs = m.getArgumentList();
    for( Iterator it = vArgs.iterator(); it.hasNext(); ) { 
      Argument arg = (Argument) it.next();
      Type type = arg.getType();
      int typeInt = type.getDetailedType();
      String argName = arg.getFormalName();
      if (typeInt == Type.ARRAY) {
        if (type.isRarray() && rarrays) {
          buf.append("sidl__array_deleteRef((struct sidl__array *)" +
                     argName + temp_postfix + ");\n");
        }
      }
    }
    return buf.toString();
  }

  public static String generatePostIORCall(Method m, Extendable d_ext, 
                                           String d_self, boolean isSuper,
                                           boolean rarrays) 
    throws CodeGenerationException 
  {
    final List vArgs = m.getArgumentList();
    final int nargs = vArgs.size();
    boolean check_downcast_fail=false;
    // everything through first "{"
    StringBuffer  post_ior = new StringBuffer( nargs * 128 );
    Type return_type = m.getReturnType();

    switch( return_type.getDetailedType() ) { 
    case Type.BOOLEAN:
      post_ior.append("_result = _local_result;\n");
      break;
    case Type.CHAR:
    case Type.INT:
    case Type.LONG:
    case Type.FLOAT:
    case Type.DOUBLE:
    case Type.OPAQUE:
    case Type.VOID:
      break;
    case Type.ENUM:
      post_ior.append("_result = (" + Cxx.getEnumName(return_type.getSymbolID())
        + ")_local_result;\n");
      break;
    case Type.FCOMPLEX:
      post_ior.append("_result = ::std::complex<float>(_local_result.real,"
        + "_local_result.imaginary);\n");
      break;
    case Type.DCOMPLEX:
      post_ior.append("_result = ::std::complex<double>(_local_result.real, "
        + "_local_result.imaginary);\n");
      break;
    case Type.STRING:
      post_ior.append("if (_local_result) {\n  _result = _local_result;\n  "
        + "::sidl_String_free( _local_result );\n}\n");
      break;
    case Type.SYMBOL:
      throw new CodeGenerationException( "Type.SYMBOL should have been "
                  + "resolved to\nType.ENUM, Type.CLASS, or Type.INTERFACE");
      //break;
    case Type.CLASS:
    case Type.INTERFACE:
    case Type.STRUCT:
      break;
    case Type.ARRAY:
      post_ior.append("_result._set_ior(_local_result);\n");
      break;
    default:
      throw new CodeGenerationException("Unexpected User Defined Return Type" + 
                                        return_type.getType());
    }

    // writeup the argument lists
    for( Iterator it = vArgs.iterator(); it.hasNext(); ) { 
      Argument arg = (Argument) it.next();
      Type type = arg.getType();
      int typeInt = type.getDetailedType();
      String argName = arg.getFormalName();
      int modeInt = arg.getMode();
      switch ( typeInt ) { 
      case Type.BOOLEAN:
        switch( modeInt ) { 
        case Argument.IN:
          break;
        case Argument.OUT:
        case Argument.INOUT:
          post_ior.append(argName + " = _local_" + argName + ";\n");          
          break;
        }
        break;
      case Type.CHAR:
      case Type.INT:
      case Type.LONG:
      case Type.FLOAT:
      case Type.DOUBLE:
      case Type.OPAQUE:
      case Type.STRUCT:
        break;
      case Type.ENUM:
        switch(modeInt) {
        case Argument.IN:
          break;
        case Argument.OUT:
        case Argument.INOUT:
          post_ior.append(argName + " = (" +
                          Cxx.getEnumName(type.getSymbolID()) +
                          ")_local_" + argName + ";\n");
          break;
        }
        break;
      case Type.FCOMPLEX:
        switch( modeInt ) { 
        case Argument.IN:
          break;
        case Argument.OUT:
        case Argument.INOUT:
          post_ior.append( argName + " = ::std::complex<float>(_local_" +
                           argName + ".real, _local_" + argName + 
                           ".imaginary);\n");
          break;
        }
        break;
      case Type.DCOMPLEX:
        switch( modeInt ) { 
        case Argument.IN:
          break;
        case Argument.OUT:
        case Argument.INOUT:
          post_ior.append( argName + " = ::std::complex<double>(_local_" +
                           argName + ".real, _local_" + argName + 
                           ".imaginary);\n");
          break;
        }
        break;
      case Type.STRING:
        switch( modeInt ) { 
        case Argument.IN:
          break;
        case Argument.OUT:
        case Argument.INOUT:
          post_ior.append("if (_local_" + argName + ") {\n");
          post_ior.append("  " + argName + " = _local_" + argName + ";\n");
          post_ior.append("  ::sidl_String_free( _local_" + argName + ");\n");
          post_ior.append("} else {\n");
          post_ior.append("  " + argName + " = \"\";\n");
          post_ior.append("}\n");
          break;
        }
        break; 
      case Type.SYMBOL:
        throw new CodeGenerationException( "Type.SYMBOL should have been "
                    + "resolved to\nType.ENUM, Type.CLASS, or Type.INTERFACE");
        // break;
      case Type.INTERFACE:
      case Type.CLASS:
        switch( modeInt ) { 
        case Argument.IN:
          //Getting rid of all deleterefs associated with interfaces
          //if (Type.INTERFACE == typeInt) {
          //  post_ior.append("if (_local_" + argName + ") {\n");
          //  post_ior.append("  " + IOR.getExceptionFundamentalType() +
          //                  "throwaway_exception;");
            //            post_ior.append("  (_local_" + argName + "->d_epv->" +
            //                IOR.getVectorEntry("deleteRef") + ")(_local_" +
            //                argName + "->d_object, &throwaway_exception);\n");
            //post_ior.append("}\n");
          //         break;
          // }
          break;
        case Argument.OUT:
          post_ior.append("if ( " + argName + "._not_nil() ) {\n");
          post_ior.append("  " + argName + ".deleteRef();\n"); 
          post_ior.append("}\n");
          post_ior.append( "_downcast_fail += " + argName + 
                           "._set_ior_typesafe( reinterpret_cast<sidl_BaseInterface__object*>(_local_" 
                           + argName + "),typeid(" +  Cxx.getSymbolName(arg.getType().getSymbolID()) + "));\n");
          check_downcast_fail=true;
          break;
        case Argument.INOUT:
          post_ior.append( "_downcast_fail += " + argName + 
                           "._set_ior_typesafe( reinterpret_cast<sidl_BaseInterface__object*>(_local_" 
                           + argName + "),typeid(" +  Cxx.getSymbolName(arg.getType().getSymbolID()) + "));\n");
          check_downcast_fail=true;
          break;
        }
        break;
      case Type.ARRAY:
        if(!rarrays || !type.isRarray()) {
          switch( modeInt ) { 
          case Argument.IN:
            break;
          case Argument.OUT:
          case Argument.INOUT:
            // sieze ownership of what's returned.
            post_ior.append(argName + "._set_ior(_local_" + argName + ");\n");  
            break;
          }
        }
        else {
          post_ior.append("sidl__array_deleteRef((struct sidl__array *)" +
                          argName + temp_postfix + ");\n");
        }
        break;
       
      default:
        throw new CodeGenerationException("Unexpected User Defined Return Type" 
                    + return_type.getType());
      }      
    }
    if ( check_downcast_fail ) { 
        post_ior.append("if (_downcast_fail > 0 ) {\n");
        post_ior.append("  ::sidl::CastException ex = ::sidl::CastException::_create();\n");
        post_ior.append("  ex.setNote(\"Failed to properly downcast out argument for C++ stub\");\n");
        post_ior.append("  throw ex;\n");
        post_ior.append("}");
        post_ior = post_ior.insert(0,"int32_t _downcast_fail = 0;\n");
    }

    /*    if (!m.isStatic() && !isSuper && d_ext.isInterface()) {
          post_ior.append("{");
          post_ior.append("  " + IOR.getExceptionFundamentalType() + "throwaway_exception;");
          post_ior.append("  (*loc_self->d_epv->" +
          IOR.getVectorEntry("deleteRef") +
          ")(loc_self->d_object, &throwaway_exception);\n");
          post_ior.append("}");
          }
    */
    post_ior.append("/*unpack results and cleanup*/\n");
    return post_ior.toString().trim();
  }


  /** 
   * On the first time it is called it generates sets of exceptions for all
   * the methods in this Extendable.  Most methods probably don't declare
   * any exceptions, so they hold the "1 implicit exception" set.   
   * */
  static public Map getExceptionSets(Collection methods) {
    HashMap   all_exception_sets;  //For making exception throwing methods
    int i= 0;
    all_exception_sets = new HashMap();
    Iterator method_it = methods.iterator();
    while (method_it.hasNext()) {
      Method m = (Method)method_it.next();
      Set exp_set = m.getThrows();
      if(!all_exception_sets.containsKey(exp_set)) {
        all_exception_sets.put(exp_set, new Integer(i));
        ++i;
      }
    }
    return all_exception_sets;
  }

  public static String getNativeEPVEntryName(Extendable ext) {
    return ext.getFullName().replace('.', '_').toLowerCase() + "__native_epv_entry_t";
  }

  /** 
   * This is a union type that is used to dismantle the C++ typechecking for
   * native method function pointers. 
   * */
  public static void generateFastCallTypes(LanguageWriterForCxx writer,
                                           Extendable ext,
                                           Context context,
                                           boolean is_impl)
    throws CodeGenerationException
  { 
    String name = ext.getShortName();

    writer.println("class sidl_generic_class_t;");
    writer.println();
    
    writer.writeCommentLine("data structure to hold method " +
                            "function pointers for class " + name);
    writer.println("typedef struct {");
    writer.tab();

    writer.println("union {");
    writer.tab();

    List nonstatic_methods;
    if(is_impl)
      nonstatic_methods = (List) ext.getNonstaticMethods(false);
    else
      nonstatic_methods = (List) Cxx.getStubMethodList(ext);
    for(Iterator m = nonstatic_methods.iterator(); m.hasNext(); ) {
      Method method = (Method) m.next();
      writer.print(Cxx.getReturnString(method.getReturnType(), context) +
                   " (sidl_generic_class_t::*" +
                   method.getLongMethodName() + "__method_p)");
      
      if ( method.getArgumentList().size() > 0 ) { 
        writer.println(" (");
        writer.tab();
        Cxx.generateArgumentList(writer, method, context, true, !is_impl);
        writer.println(" );");
        writer.backTab();
      } else { 
        writer.println(" ();");
      }
    }
    writer.backTab();
    writer.println("} method_p;");
    writer.println("unsigned offset;");
    writer.backTab();
    writer.println("} " + getNativeEPVEntryName(ext) + ";");
    writer.println();
  }
}
