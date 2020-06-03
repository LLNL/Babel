//
// File:        Cxx.java
// Package:     gov.llnl.babel.backend.c
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

package gov.llnl.babel.backend.cxx;

import gov.llnl.babel.BabelConfiguration;
import gov.llnl.babel.backend.CodeConstants;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.FileManager;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.Utilities;
import gov.llnl.babel.backend.c.C;
import gov.llnl.babel.backend.writers.LanguageWriterForCxx;
import gov.llnl.babel.backend.writers.LineCountingFilterWriter;
import gov.llnl.babel.symbols.Argument;
import gov.llnl.babel.symbols.AssertionException;
import gov.llnl.babel.symbols.AssertionExpression;
import gov.llnl.babel.symbols.CExprString;
import gov.llnl.babel.symbols.Comment;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.Type;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Class <code>Cxx</code> contains common C++ language binding routines
 * shared by the C++ backend code generators.  This class simply collects
 * many common C+ binding routines into one place.
 */
public class Cxx {
  public final static String FUNCTION_RESULT   = C.FUNCTION_RESULT;
  public final static String NULL              = C.NULL;
  public final static String RAW_ARRAY_EXT      = C.RAW_ARRAY_EXT;

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
    ".hh",
    ".cc",
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
   * the file type, prepended with the period (e.g., ".hh").
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
  public static String generateFilename( SymbolID id, int role, int ftype ) 
  { 
    if (  BabelConfiguration.getInstance().makePackageSubdirs() 
       && BabelConfiguration.getInstance().getShortFileNames()) {

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
                                         ftype ) { 
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
                                                   int    role,
                                                   String filegroup )
    throws CodeGenerationException
  { 
    final SymbolID id = symbol.getSymbolID();
    final int type = symbol.getSymbolType();

    String filename = generateFilename( id, role, FILE_TYPE_CXX_HEADER );
    LanguageWriterForCxx lw = null;
    if ( role==FILE_ROLE_IMPL) { 
        Writer fw = FileManager.getInstance().
          createWriter( id, type, filegroup,filename);
        LineCountingFilterWriter lcfw = new LineCountingFilterWriter( fw );
        PrintWriter pw = new PrintWriter( lcfw );
        lw = new LanguageWriterForCxx( pw , lcfw );
    } else { 
        PrintWriter pw = FileManager.getInstance().
          createFile( id, type, filegroup,filename);
        lw = new LanguageWriterForCxx( pw );
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
  public static LanguageWriterForCxx createSource( Symbol symbol,
                                                   int    role,
                                                   String filegroup )
    throws CodeGenerationException
  { 
    final SymbolID id = symbol.getSymbolID();
    final int type = symbol.getSymbolType();

    String filename = generateFilename( id, role, FILE_TYPE_CXX_SOURCE );
    LanguageWriterForCxx lw = null;
    if ( role==FILE_ROLE_IMPL) { 
        Writer fw = FileManager.getInstance().
          createWriter( id, type, filegroup,filename);
        LineCountingFilterWriter lcfw = new LineCountingFilterWriter( fw );
        PrintWriter pw = new PrintWriter( lcfw );
        lw = new LanguageWriterForCxx( pw , lcfw );
    } else { 
        PrintWriter pw = FileManager.getInstance().
          createFile( id, type, filegroup,filename);
        lw = new LanguageWriterForCxx( pw );
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
   *               the heirarchy of packages to which it belongs.
   * @see #unnestPackagesInNamespaces
   */
  public static void nestPackagesInNamespaces ( LanguageWriterForCxx writer,
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
   *               the heirarchy of packages to which it belongs.
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
      return "::" + Utilities.replace( id.getFullName(), ".", "::" );
    } else { 
      return "::" + Utilities.replace( id.getFullName(), ".", "::" ) + "_" 
           + postfix;
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
                                                          String postfix) { 
    if ( postfix == null || postfix.trim().equals("") ) { 
      return Utilities.replace( id.getFullName(), ".", "::" );
    } else { 
      return Utilities.replace( id.getFullName(), ".", "::" ) + "_" + postfix;
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
   * Convert the type to a Cxx representation in string form
   * 
   */
  public static String getCxxString( Type type, boolean rarrays) 
    throws CodeGenerationException 
  { 
    int t = type.getType();
    if (t < s_types.length) { // If the type is one of the primitive types...
      
      // return its string value from the lookup table.
      return s_types[t];

    } else if (t == Type.SYMBOL) { 
      // else if the type is a symbol (e.g. class,interface,enm)

      // Look up the symbol type and return the associated type name.
      Symbol symbol = Utilities.lookupSymbol(type.getSymbolID());
      return getSymbolName( symbol.getSymbolID() ); 

    } else if (t == Type.ARRAY) { // else if the type is an array

      // either return one of the primitive
      // array types or construct the corresponding array type.
      Type atype = type.getArrayType();
      if(rarrays && type.isRarray()) {
        if(atype.getType() == Type.DCOMPLEX || 
           atype.getType() == Type.FCOMPLEX) {
          return IOR.getReturnString(atype, true, false) + "*";
          
        } else {
          return getCxxString(atype, true) + "*";
        }
       
      } else if (null != atype) {
        int  a     = atype.getType();      
        if (a < s_array_types.length) {
          return s_array_types[a] ;
        } else {
          return "::sidl::array< " + getObjectName(atype.getSymbolID()) + ">";
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
   public static String getReturnString(Type type) 
      throws CodeGenerationException 
   {
     return getCxxString( type, false );
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
   public static String getRarrayReturnString(Type type) 
      throws CodeGenerationException 
   {
     return getCxxString( type, true );
   }
 
  public static String getArgModeComment(Argument arg) {
    return "/* " + arg.getModeString() + " */ ";
  }

  /**
   * Generate a C++ argument string for the specified sidl argument.
   * The formal argument name is not included.
   */
  public static String getArgumentString(Argument arg, boolean rarrays)
    throws CodeGenerationException 
  {
    int    mode = arg.getMode();
    StringBuffer argString = new StringBuffer(50);

    // first show the mode
    argString.append(getArgModeComment(arg));

    // next add the type
    Type type = arg.getType();
    if ( mode == Argument.IN && ( type.getType() == Type.STRING ||
				  type.getType() == Type.FCOMPLEX ||
				  type.getType() == Type.DCOMPLEX)) { 
      argString.append("const ");
      
      argString.append( getReturnString( type ) );
      argString.append("&");

    } else if (rarrays && type.isRarray()) {      
      argString.append( getRarrayReturnString( type ) );

    } else { 
      argString.append( getReturnString( type ) );
      if ( mode != Argument.IN ) { 
        argString.append("&");
      } 
    }

    argString.append(" ");

    // finally add the name
    argString.append(arg.getFormalName());

    return argString.toString();
  }

  public static String getIORCall(String objName, Type t) {
    switch(t.getDetailedType()) {
    case Type.ARRAY:
      return objName + 
        ((null != t.getArrayType()) ? "._get_ior()" : "._get_baseior()");
    case Type.CLASS:
    case Type.INTERFACE:
      return objName + "._get_ior()";
    default:
      return null;
    }
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
    return getSymbolName( id, "impl")  + "::" + methodName;
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
   * Generate the skel method's name.  In most cases, the skel name is the
   * impl name except when the method has an array with an ordering
   * specification.
   *
   * @param id the <code>SymbolID</code> of the <code>Symbol</code>
   *           associated with the method.
   *
   * @param method the method
   */
  public static String getMethodSkelName(SymbolID id, Method method) {
    return getMethodSkelName(id, method.getLongMethodName());
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
  public static String getMethodStubName(SymbolID id, String methodName) {
    return getSymbolNameWithoutLeadingColons( id, "")  + "::" + methodName;
  }
 
  /**
   * Generate the set of SymbolID's that this Extendable must #include.
   */
  public static Set generateIncludeSet(Extendable ext) 
  {
    Set includes = new HashSet();
    
    for( Iterator i = ext.getMethods(true).iterator(); i.hasNext(); ) { 
      Method method = (Method) i.next();
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
          case Type.STRUCT:
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
        if(method.hasExplicitExceptions()) {
          includes.addAll(method.getThrows());
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
  public static Set getFrontIncludes( Extendable ext )
    throws CodeGenerationException
  {
    Set includes = new HashSet();
    for( Iterator i = ext.getMethods(true).iterator(); i.hasNext(); ) { 
      Method method = (Method) i.next();
      Set argTypes = method.getSymbolReferences();
      for(Iterator j = argTypes.iterator(); j.hasNext();) {
        SymbolID argID = (SymbolID) j.next();
        Symbol symbol = (Symbol) Utilities.lookupSymbol(argID);
        if(symbol.getSymbolType() == Type.ENUM) {
          includes.add(argID);
        } 
      }
      if(method.hasExplicitExceptions()) {
        includes.addAll(method.getThrows());
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
  public static Set generateImplDependencyIncludes( LanguageWriterForCxx writer,
                                                Extendable ext , 
                                                boolean removeSelf ) 
    throws CodeGenerationException 
  {
    Set includes = new HashSet();
    for( Iterator i = ext.getMethods(true).iterator(); i.hasNext(); ) { 
      Method method = (Method) i.next();
      includes.addAll(method.getSymbolReferences());
      
    }

    // always include the base header file.
    writer.generateInclude( "sidl_cxx.hh", true );
    
    // remove self Header file?  If invoked from Stub yes, 
    // If invoked from Skel no
    if ( removeSelf ) { 
      includes.remove(ext.getSymbolID());
    } else { 
      includes.add(ext.getSymbolID());
    }

    // generate include to my IOR
    writer.generateInclude( IOR.getHeaderFile( ext.getSymbolID() ), true );
    
    // sort remainder and generate includes.
    if (!includes.isEmpty()){
      writer.writeComment("Includes for all method dependencies.",false);
      
      List entries = Utilities.sort(includes);
      
      for (Iterator i = entries.iterator(); i.hasNext(); ) { 
        String header = generateFilename( (SymbolID) i.next(), 
                                          FILE_ROLE_STUB, 
                                          FILE_TYPE_CXX_HEADER );
        writer.generateInclude( header, true );
      }
      writer.println();
    } 
    return includes;
  } 

  public static void generateMethodSignature( LanguageWriterForCxx writer, 
                                                Method method, 
                                                String suffix,
                                                String altcomment,
                                                int role, boolean rarrays )
    throws CodeGenerationException
  {
    if ( method == null ) { return; }
    Comment comment = method.getComment();
    writer.writeComment( comment, altcomment );
    boolean canThrowNullIORException = 
      ( (role == FILE_ROLE_STUB) && BabelConfiguration.getInstance().makeCxxCheckNullIOR() );

    ArrayList vArgs = null;
    if(rarrays)
      vArgs = method.getArgumentListWithIndices();
    else
      vArgs = method.getArgumentListWithOutIndices();
    

    if ( method.isStatic() ) { 
      writer.print("static ");
    }
    writer.println( getReturnString( method.getReturnType() ) );
    writer.print( method.getShortMethodName() );
    if ( vArgs.size() > 0 ) { 
      writer.println(" (");
      writer.tab();
      generateArgumentList( writer, method, rarrays );
      writer.println();
      writer.backTab();
      writer.println(")");
      generateThrowsList( writer, method, canThrowNullIORException );
      writer.println(";");
    } else { 
      writer.print("() ");
      generateThrowsList( writer, method, canThrowNullIORException );
      writer.print(";");
    }
    writer.println();
  }

  public static void generateInlineMethodSignature( LanguageWriterForCxx writer,
                                                    Method method, 
                                                    String altcomment,
                                                    int role, boolean isSuper )
    throws CodeGenerationException
  {
    if ( method == null ) { return; }
    Comment comment = method.getComment();
    writer.writeComment( comment, altcomment );
    boolean canThrowNullIORException = 
      ( (role == FILE_ROLE_STUB) && BabelConfiguration.getInstance().makeCxxCheckNullIOR() );
    
    if(!isSuper)
      writer.print("inline ");

    if ( method.isStatic() ) { 
      writer.print("static ");
    }
    writer.println( getReturnString( method.getReturnType() ) );
    writer.print( method.getShortMethodName() );
    if ( method.getArgumentList().size() > 0 ) { 
      writer.println(" (");
      writer.tab();
      generateArgumentList( writer, method, true );
      writer.println();
      writer.backTab();
      writer.println(")");
      generateThrowsList( writer, method, canThrowNullIORException );
      writer.println("{");
    } else { 
      writer.print("() ");
      generateThrowsList( writer, method, canThrowNullIORException );
      writer.print("{");
    }
    writer.println();
  }


  public static void  generateArgumentList( LanguageWriterForCxx writer, 
                                            Method method , boolean rarrays )
    throws CodeGenerationException 
  { 
    List args = null;
    if(rarrays)
      args = method.getArgumentListWithIndices();
    else 
      args = method.getArgumentListWithOutIndices();
    
    for( Iterator a = args.iterator(); a.hasNext(); ) { 
      Argument arg = (Argument) a.next();
      writer.print( getArgumentString( arg, rarrays ) );
      if ( a.hasNext() ) { 
        writer.println(",");
      }
    }
  }

  public static void generateThrowsList( LanguageWriterForCxx writer, 
					 Method method, 
					 boolean canThrowNullIORException) 
  { 
    Set exceptions = method.getThrows();

    if ( exceptions == null || exceptions.isEmpty() ) { 
	if ( canThrowNullIORException && BabelConfiguration.getInstance().makeCxxCheckNullIOR()) { 
	    writer.println(
                  "throw ( ::sidl::NullIORException ) ");
	} else { 
	    writer.println( "throw () " );
	}
      return;
    } 
    else { 
      if (!method.getExplicitThrows().isEmpty()) {
        writer.println( "throw ( " );
        writer.tab();
        if ( canThrowNullIORException && BabelConfiguration.getInstance().makeCxxCheckNullIOR()) { 
	  writer.print ( "::sidl::NullIORException, ");
        }
        for( Iterator e = exceptions.iterator(); e.hasNext(); ) { 
          SymbolID id = (SymbolID) e.next();
          
          writer.print( getObjectName(id) );
          if ( e.hasNext() ) { 
            writer.println(", ");
          }
        }
        writer.println();
        writer.backTab();
        writer.print(")");
      }
    }
  }

  public static String generateThrowsList( Method method, 
					   boolean canThrowNullIORException ) 
  { 
    Set exceptions = method.getThrows();
    StringBuffer ret = new StringBuffer();

    if ( exceptions == null || exceptions.isEmpty() ) { 
	if (canThrowNullIORException && BabelConfiguration.getInstance().makeCxxCheckNullIOR()) {
	    return "throw ( ::sidl::NullIORException ) \n";
	} else { 
	    return "throw () \n";
	}
 
    } else { 
      if (!method.getExplicitThrows().isEmpty()) {
        ret.append("throw ( ");
        if (canThrowNullIORException && BabelConfiguration.getInstance().makeCxxCheckNullIOR()) { 
          ret.append("::sidl::NullIORException, \n");
        }
        for( Iterator e = exceptions.iterator(); e.hasNext(); ) { 
          SymbolID id = (SymbolID) e.next();
          
          ret.append(getObjectName(id));
          if ( e.hasNext() ) { 
            ret.append(",\n");
          }
        }
        ret.append(")");
      }
    }
    return ret.toString();
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


  public static String generateFunctionDeclaration( Method m, Extendable d_ext, 
                                                    String d_self, 
                                                    boolean rarrays ) 
    throws CodeGenerationException 
  {
    ArrayList vArgs = null;
    if(rarrays)
      vArgs = m.getArgumentListWithIndices();
    else 
      vArgs = m.getArgumentListWithOutIndices();
    final int nargs = vArgs.size();
    // everything through first "{"
    StringBuffer func_decl = new StringBuffer( nargs * 32 );
    String shortMethodName = m.getShortMethodName();
    Type return_type = m.getReturnType();

    func_decl.append( getReturnString( return_type ) );
    func_decl.append( "\n" );
    func_decl.append( getMethodStubName( d_ext.getSymbolID(), 
                                             shortMethodName ) );
    func_decl.append( "( " );

    // writeup the argument lists
    for( Iterator it = vArgs.iterator(); it.hasNext(); ) { 
      Argument arg = (Argument) it.next();
      func_decl.append( getArgumentString( arg, rarrays ) );
      if ( it.hasNext() ) { 
        func_decl.append(", ");
      }
    }
    func_decl.append(" )\n");
    func_decl.append(generateThrowsList(m, true ));
    
    return func_decl.toString().trim();
  }

  public static String generateInitialization( Method m, Extendable d_ext, 
                                               String d_self ) 
    throws CodeGenerationException 
  {
    final ArrayList vArgs = m.getArgumentList();
    final int nargs = vArgs.size();
    // everything through first "{"
    StringBuffer init_stuff = new StringBuffer( nargs * 128 );
    Type return_type = m.getReturnType();
    
    if ( return_type.getType() != Type.VOID ) { 
      init_stuff.append( getCxxString( return_type , true ));
      init_stuff.append( " _result;\n");
    }

    return init_stuff.toString().trim();
  }

  /** 
   *  Function generates argument initialization prior to IOR call.
   */

  public static String generatePreIORCall( Method m, Extendable d_ext, 
                                           String d_self, boolean rarrays ) 
    throws CodeGenerationException 
  {
    final String nm = "Cxx.generatePreIORCall: ";
    ArrayList vArgs = null;
    if(rarrays)
      vArgs = m.getArgumentListWithIndices();
    else 
      vArgs = m.getArgumentListWithOutIndices();

    final int nargs = vArgs.size();
    StringBuffer pre_ior = new StringBuffer( nargs * 128 );
    Type return_type = m.getReturnType();
    SymbolID id = return_type.getSymbolID();

    switch( return_type.getDetailedType() ) { 
    case Type.VOID:
      break;
    case Type.OPAQUE:
      break;
    case Type.BOOLEAN:
      pre_ior.append("sidl_bool _local_result;\n");
      break;
    case Type.CHAR:
    case Type.INT:
    case Type.LONG:
    case Type.FLOAT:
    case Type.DOUBLE:
      break;
    case Type.ENUM:
      pre_ior.append( IOR.getEnumName(return_type.getSymbolID()) 
             + " _local_result;\n" );
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
      throw new CodeGenerationException(nm + "Type.SYMBOL should have been "
                  + "resolved to\nType.ENUM, Type.CLASS, or Type.INTERFACE.");
      //break;
    case Type.CLASS:
    case Type.INTERFACE:
      break;
    case Type.ARRAY:
      String iorArrayName = IOR.getReturnString(return_type, true, false);
      pre_ior.append(iorArrayName + " _local_result;\n");
      break;
    default:
      throw new CodeGenerationException(nm + "Unexpected User Defined Return "
                  + "Type \"" + return_type.getType() + "\".");
    }

    // writeup the argument lists
    for( Iterator it = vArgs.iterator(); it.hasNext(); ) { 
      Argument arg = (Argument) it.next();
      Type type = arg.getType();
      int typeInt = type.getDetailedType();
      String argName = arg.getFormalName();
      int modeInt = arg.getMode();

      switch ( typeInt ) { 
      case Type.ARRAY:
        String iorArrayName = IOR.getReturnString(type, true, false);
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
          pre_ior.append(sidl_array_name + " *" + r_name + RAW_ARRAY_EXT
            + " = &" + r_name + "_real;\n");
          
          List indices = type.getArrayIndexExprs();
          String init_func_name = IOR.getArrayNameForFunctions(aIntType)
                                    + "_init";
          int x = 0;
          for(Iterator i = indices.iterator(); i.hasNext();++x) {
            AssertionExpression ae =(AssertionExpression) i.next();
            pre_ior.append(r_name + "_upper[" + x + "] = " + 
                           ae.accept(new CExprString(), null).toString() +
                           "-1;\n");
          }
          pre_ior.append(init_func_name + "(" + r_name + ", " + r_name 
            + RAW_ARRAY_EXT + ", " + type.getArrayDimension() + ", " + r_name 
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
            pre_ior.append(iorArrayName + " _local_" + argName + " = " 
              + getIORCall(argName, type) + ";\n");
            break;
          }
        }
        break;
      case Type.BOOLEAN:
        switch( modeInt ) { 
        case Argument.IN:
        case Argument.INOUT:
          pre_ior.append("sidl_bool _local_" + argName + " = " + argName 
            + ";\n");
          break;
        case Argument.OUT:
          pre_ior.append("sidl_bool _local_" + argName + ";\n");
          break;
        }
        break;
      case Type.CHAR:
      case Type.DOUBLE:
      case Type.FLOAT:
      case Type.INT:
      case Type.LONG:
      case Type.OPAQUE:
        break;
      case Type.CLASS:
      case Type.INTERFACE:
        id = type.getSymbolID();
        switch( modeInt ) { 
        case Argument.IN:
          break;
        case Argument.OUT:
          pre_ior.append(IOR.getObjectName(id) + "* _local_" + argName + ";\n");
          break;
        case Argument.INOUT:
          pre_ior.append(IOR.getObjectName(id) + "* _local_" + argName 
            + " = " + argName + "._get_ior();\n" );
          pre_ior.append( argName + "._set_ior( 0 );\n");
          break;
        }
        break;
      case Type.DCOMPLEX:
        switch( modeInt ) { 
        case Argument.IN:
        case Argument.INOUT:
          pre_ior.append("struct sidl_dcomplex _local_" + argName + " = {");
          pre_ior.append( argName + ".real(), ");
          pre_ior.append( argName + ".imag() } ; \n");
          break;
        case Argument.OUT:
          pre_ior.append("struct sidl_dcomplex _local_" + argName + "; \n");
          break;
        }
        break;
      case Type.ENUM:
        break;
      case Type.FCOMPLEX:
        switch( modeInt ) { 
        case Argument.IN:
        case Argument.INOUT:
          pre_ior.append("struct sidl_fcomplex _local_" + argName + " = {");
          pre_ior.append( argName + ".real(), ");
          pre_ior.append( argName + ".imag() } ; \n");
          break;
        case Argument.OUT:
          pre_ior.append("struct sidl_fcomplex _local_" + argName + "; \n");
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
          pre_ior.append("char * _local_" + argName 
                 + " = sidl_String_strdup( " + argName + ".c_str() );\n");
        }
        break; 
      case Type.SYMBOL:
        throw new CodeGenerationException(nm + "Type.SYMBOL should have been "
                    + "resolved to\nType.ENUM, Type.CLASS, or Type.INTERFACE.");
        // break;
       
      default:
        throw new CodeGenerationException(nm + "Unexpected User Defined Return "
                    + "Type \"" + return_type.getType() + "\".");
      }      
    }
 
    // if method throws exceptions...
    if (m.getThrows().size()>0) { 
      pre_ior.append("sidl_BaseInterface__object * _exception = 0;\n");
    }
    pre_ior.append("/*pack args to dispatch to ior*/\n");

    return pre_ior.toString().trim();
  }

  public static String generateIORCall( Method m, Extendable d_ext, 
                                        String d_self, boolean isSuper, 
                                        boolean rarrays) 
    throws CodeGenerationException 
  {
    final String nm = "Cxx.generateIORCall: ";
    final ArrayList vArgs = m.getArgumentList();
    final int nargs = vArgs.size();
    // everything through first "{"
    StringBuffer  ior_call = new StringBuffer( nargs * 128 );
    String extra_close_paren = "";
    String longMethodName = m.getLongMethodName();
    Type return_type = m.getReturnType();
    switch( return_type.getDetailedType() ) { 
    case Type.ARRAY:
      ior_call.append("_local_result = ");
      break;
    case Type.BOOLEAN:
    case Type.DCOMPLEX:
    case Type.ENUM:
    case Type.FCOMPLEX:
    case Type.STRING:
      ior_call.append("_local_result = ");
      break;
    case Type.CHAR:
    case Type.DOUBLE:
    case Type.FLOAT:
    case Type.INT:
    case Type.LONG:
    case Type.OPAQUE:
      ior_call.append("_result = ");
      break;
    case Type.CLASS:
    case Type.INTERFACE:
      ior_call.append("_result = " + getReturnString( return_type ) + "( ");
      extra_close_paren = ", false)";
      break;
    case Type.SYMBOL:
      throw new CodeGenerationException(nm + "Type.SYMBOL should have been "
                  + "resolved to\nType.ENUM, Type.CLASS, or Type.INTERFACE.");
      //break;
    case Type.VOID:
      break;

    default:
      throw new CodeGenerationException(nm + "Unexpected User Defined Return "
                  + "Type \"" + return_type.getType() + "\".");
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
        ior_call.append( "(*(d_self->d_epv->f_" + m.getLongMethodName() + "))("
          + d_self );
        if ( nargs > 0 ) { ior_call.append(", "); }
      }
    }
    // writeup the argument lists
    for( Iterator it = vArgs.iterator(); it.hasNext(); ) { 
      Argument arg = (Argument) it.next();
      Type type = arg.getType();
      int typeInt = type.getDetailedType();
      String argName = arg.getFormalName();
      String mode = getArgModeComment(arg);
      int modeInt = arg.getMode();
      ior_call.append( mode );
      switch ( typeInt ) { 
      case Type.ARRAY:
        if(rarrays && type.isRarray()) {
          if(modeInt == Argument.INOUT)
            ior_call.append("&");
          ior_call.append(argName + RAW_ARRAY_EXT);
        } else {
          switch( modeInt ) { 
          case Argument.IN:
            ior_call.append( getIORCall(argName, type) );
            break;
          case Argument.OUT:
          case Argument.INOUT:
            ior_call.append("&_local_" + argName);
            break;
          }
        }
        break;
      case Type.BOOLEAN:
      case Type.DCOMPLEX:
      case Type.FCOMPLEX:
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
      case Type.DOUBLE:
      case Type.FLOAT:
      case Type.INT:
      case Type.LONG:
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
      case Type.CLASS:
      case Type.INTERFACE:
        switch( modeInt ) { 
        case Argument.IN:
          ior_call.append( argName + "._get_ior()");
          break;
        case Argument.OUT:
        case Argument.INOUT:
          ior_call.append( "&_local_" + argName );
          break;
        }
        break;
      case Type.ENUM:
        String ior_name = IOR.getEnumName(  type.getSymbolID() );  
        switch( modeInt ) { 
        case Argument.IN:
          ior_call.append( "(" + ior_name + ")" + argName );
          break;
        case Argument.OUT:
        case Argument.INOUT:
          ior_call.append( "(" + ior_name + "*)&" + argName );
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
        throw new CodeGenerationException(nm + "Type.SYMBOL should have been "
                    + "resolved to\nType.ENUM, Type.CLASS, or Type.INTERFACE.");
        // break;
       
      default:
        throw new CodeGenerationException(nm + "Unexpected User Defined Return "
                    + "Type \"" + return_type.getType() + "\".");
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

  public static String generatePostIORCall( Method m, Extendable d_ext, 
                                            String d_self, boolean rarrays ) 
    throws CodeGenerationException 
  {
    final String nm = "Cxx.generatePostIORCall: ";
    final ArrayList vArgs = m.getArgumentList();
    final int nargs = vArgs.size();
    // everything through first "{"
    StringBuffer  post_ior = new StringBuffer( nargs * 128 );
    Type return_type = m.getReturnType();
    switch( return_type.getDetailedType() ) { 
    case Type.ARRAY:
      post_ior.append("_result._set_ior(_local_result);\n");
      break;
    case Type.BOOLEAN:
      post_ior.append("_result = _local_result;\n");
      break;
    case Type.CHAR:
    case Type.CLASS:
    case Type.DOUBLE:
    case Type.FLOAT:
    case Type.INT:
    case Type.INTERFACE:
    case Type.LONG:
    case Type.OPAQUE:
    case Type.VOID:
      break;
    case Type.ENUM:
      post_ior.append("_result = (" + getEnumName(return_type.getSymbolID())
              + ")_local_result;\n");
      break;
    case Type.FCOMPLEX:
      post_ior.append("_result = ::std::complex<float>(_local_result.real,"
              + "_local_result.imaginary);\n");
      break;
    case Type.DCOMPLEX:
      post_ior.append("_result = ::std::complex<double>(_local_result.real,"
              + "_local_result.imaginary);\n");
      break;
    case Type.STRING:
      post_ior.append("if (_local_result) {\n  _result = _local_result;\n"
              + "  free( _local_result );\n}\n");
      break;
    case Type.SYMBOL:
      throw new CodeGenerationException(nm + "Type.SYMBOL should have been "
                  + "resolved to\nType.ENUM, Type.CLASS, or Type.INTERFACE.");
      //break;

    default:
      throw new CodeGenerationException(nm + "Unexpected User Defined Return "
                  + "Type " + return_type.getType() + "\".");
    }

    // writeup the argument lists
    for( Iterator it = vArgs.iterator(); it.hasNext(); ) { 
      Argument arg = (Argument) it.next();
      Type type = arg.getType();
      int typeInt = type.getDetailedType();
      String argName = arg.getFormalName();
      int modeInt = arg.getMode();
      boolean isOut = (modeInt == Argument.OUT) || (modeInt == Argument.INOUT);
      switch ( typeInt ) { 
      case Type.ARRAY:
        if(!rarrays || !type.isRarray()) {
          if (isOut) {
            // sieze ownership of what's returned.
            post_ior.append(argName + "._set_ior(_local_" + argName + ");\n");
          }
        }
        break;
      case Type.BOOLEAN:
        if (isOut) {
          post_ior.append(argName + " = _local_" + argName + ";\n");          
        }
        break;
      case Type.CHAR:
      case Type.DOUBLE:
      case Type.FLOAT:
      case Type.INT:
      case Type.LONG:
      case Type.OPAQUE:
        break;
      case Type.CLASS:
      case Type.INTERFACE:
        if (modeInt == Argument.OUT) {
          post_ior.append("if ( " + argName + "._not_nil() ) {\n");
          post_ior.append("  " + argName + ".deleteRef();\n"); 
          post_ior.append("}\n");
        }
        if (isOut) {
          post_ior.append( argName + "._set_ior( _local_" + argName + ");\n");
        }
        break;
      case Type.ENUM:
        break;
      case Type.DCOMPLEX:
        if (isOut) {
          post_ior.append( argName + " = ::std::complex<double>(_local_"
                  + argName + ".real, _local_" + argName + ".imaginary);\n");
        }
        break;
      case Type.FCOMPLEX:
        if (isOut) {
          post_ior.append( argName + " = ::std::complex<float>(_local_" 
                  + argName + ".real, _local_" + argName + ".imaginary);\n");
        }
        break;
      case Type.STRING:
        if (isOut) {
          post_ior.append("if (_local_" + argName + ") {\n");
          post_ior.append("  " + argName + " = _local_" + argName + ";\n");
          post_ior.append("  sidl_String_free( _local_" + argName + ");\n");
          post_ior.append("}\n");
          post_ior.append("else {\n");
          post_ior.append("  " + argName + " = \"\";\n");
          post_ior.append("}\n");
        }
        break; 
      case Type.SYMBOL:
        throw new CodeGenerationException(nm + "Type.SYMBOL should have been "
                    + "resolved to\nType.ENUM, Type.CLASS, or Type.INTERFACE.");
        // break;
       
      default:
        throw new CodeGenerationException(nm + "Unexpected User Defined Return "
                    + "Type \"" + return_type.getType() + "\".");
      }      
    }
 
    post_ior.append("/*unpack results and cleanup*/\n");
    return post_ior.toString().trim();
  }

  /**
   * Generates include directives for all the Babel clases used in this 
   * .cc file
   * 
   * @param writer Language writer for C++
   * @param ext Extendible (Class or Interface) to generate dependencies
   */
  public static Set generateSourceIncludes( LanguageWriterForCxx writer,
                                            Extendable ext) 
    throws CodeGenerationException 
  {
    Set includes = new HashSet();
    for( Iterator i = ext.getMethods(true).iterator(); i.hasNext(); ) { 
      Method method = (Method) i.next();
      includes.addAll(method.getSymbolReferences());
    }
    if (!includes.isEmpty()){
      writer.writeComment("Includes for all method dependencies.",false);
      
      List entries = Utilities.sort(includes);
      
      for (Iterator i = entries.iterator(); i.hasNext(); ) { 
        String header = Cxx.generateFilename( (SymbolID) i.next(), 
                                              Cxx.FILE_ROLE_STUB, 
                                              Cxx.FILE_TYPE_CXX_HEADER );
        writer.generateInclude( header, true );
      }
    } 
    return includes;
  } 

}
