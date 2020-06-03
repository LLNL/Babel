//
// File:        CxxStubHeader.java
// Package:     gov.llnl.babel.backend.cxx
// Revision:    @(#) $Revision: 7188 $
// Date:        $Date: 2011-09-27 11:38:42 -0700 (Tue, 27 Sep 2011) $
// Description: Write Cxx extension header file for a BABEL extendable
// 
// This is typically directed by GenCxxClient.
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
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.cxx.Cxx;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.c.C;
import gov.llnl.babel.backend.LevelComparator;
import gov.llnl.babel.backend.writers.LanguageWriterForCxx;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Class;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.SymbolTable;
import gov.llnl.babel.symbols.Type;
import gov.llnl.babel.backend.Utilities;
import java.util.Collection;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
/**
 * 
 */
public class CxxStubHeader {
  private Extendable d_ext = null;
  private LanguageWriterForCxx d_writer = null;
  private String d_self = null;

  private final static String TYPE_DEF = "typedef ";

  /**
   * Indices associated with the special, stub-only built-in methods.
   */
  public final static int DUMP_STATS       = 0;
  public final static int SET_CHECKING     = 1;
  public final static int SET_HOOKS = 2;

  /**
   * Create an object capable of generating the header file for a
   * BABEL extendable.
   *
   * @param ext   an interface or class symbol that needs a header
   *              file for a Cxx C extension class.
   */
  public CxxStubHeader(Extendable ext) {
    d_ext = ext;
  }

  /**
   * Special constructor for generating super methods for IMPL files.
   * Since these can only exist for classes, d_self is just "self"
   *
   * @param ext   an interface or class symbol that needs a header
   *              file for a Cxx C extension class.
   */
  public CxxStubHeader(Extendable ext, LanguageWriterForCxx writer) {
    d_ext = ext;
    d_writer = writer;
    d_self = "self";
  }

  /**
   * This is a convenience utility function specifically for the generation
   * of super "Stub" functions in the Impl files. 
   * The output stream is not closed on exit.  A code
   * generation exception is thrown if an error is detected.
   *
   * @param cls The class in which these supers are to be generated      
   *
   * @param writer the output writer to which the stub source will
   *               be written. This will not be closed.
   *
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception. It can be caused by I/O trouble or
   *    violations of the data type invariants.
   */
  public static void generateSupers(Class                cls,
                                    LanguageWriterForCxx writer)
    throws CodeGenerationException
  {
    CxxStubHeader source = new CxxStubHeader(cls, writer);
    source.generateSupers();
  }  
  
  /**
   * This special function is only to be used when generating super
   * functions for the Super class in an Impl.  It may only be called though
   * the static method above, "generateSupers"
   *
   */
  private void generateSupers() 
    throws CodeGenerationException {
    Class cls = (Class) d_ext;
    SymbolID clsID = cls.getSymbolID();
    d_writer.println("public:");
    d_writer.writeComment("Hold pointer to IOR functions.", false);

    d_writer.println("class Super {");
    d_writer.tab();
    d_writer.println();
    d_writer.println("private:");
    d_writer.tab();
    d_writer.writeComment("Hold pointer to Super EPV", false);
    d_writer.println(/*"const " + */
      IOR.getEPVName(cls.getParentClass().getSymbolID()) + "* superEPV;");
    d_writer.println(/*"const " + */IOR.getObjectName(clsID) + "* superSelf;");
    d_writer.println();
    d_writer.backTab();
    d_writer.println("public:"); 
    d_writer.tab();
    d_writer.println("Super() : superEPV(NULL), superSelf(NULL) {}");

    d_writer.println();
    d_writer.println("Super(" + IOR.getObjectName(clsID) + "* loc_self, const "
      + IOR.getExternalName(cls.getSymbolID()) + "* loc_ext) {");
    //"Super("+/*const "+*/d_ext.getSymbolID().getShortName()+" loc_self) {");
    d_writer.tab();
    d_writer.println("superEPV = loc_ext->getSuperEPV();");
    d_writer.println("superSelf = loc_self;");
    //d_writer.println("superEPV = loc_self._get_ext()->getSuperEPV();");
    //d_writer.println("superSelf = loc_self._get_ior();");
    d_writer.backTab();
    d_writer.println("}");
    Collection methods = cls.getOverwrittenClassMethods();
    for(Iterator mit = methods.iterator(); mit.hasNext();){
      Method method = (Method)mit.next();
      generateInlineMethodDispatch(method, null, true);
    }
    
    d_writer.backTab();
    d_writer.backTab();
    d_writer.println("};");
  }
                              
  private void includeSet(Set symbols)
  {
    if (!symbols.isEmpty()) {
      List entries = Utilities.sort(symbols);
      for(Iterator i = entries.iterator(); i.hasNext(); ) {
        d_writer.
          generateInclude(Cxx.generateFilename((SymbolID)i.next(),
                                               Cxx.FILE_ROLE_STUB,
                                               Cxx.FILE_TYPE_CXX_HEADER),
                          true);
      }
    }
  }

  public void generateFrontIncludes()
    throws CodeGenerationException
  {
    Set symbols = Cxx.getFrontIncludes(d_ext);
    d_writer.generateInclude( "sidl_cxx.hh", true);
    d_writer.generateInclude(IOR.getHeaderFile(d_ext.getSymbolID()), true);
    includeSet(symbols);
  }

  public void generateEndIncludes()
    throws CodeGenerationException
  {
    Set symbols = Cxx.generateIncludeSet(d_ext);
    symbols.removeAll(Cxx.getFrontIncludes(d_ext));
    includeSet(symbols);
  }

  /**
   * Generate the header file for the extendable with which this object was
   * created.
   *
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception for problems during the code
   *    generation phase.
   */
  public synchronized void generateCode() throws CodeGenerationException {
    String filename = Cxx.generateFilename( d_ext.getSymbolID(), 
                                            Cxx.FILE_ROLE_STUB, 
                                            Cxx.FILE_TYPE_CXX_HEADER );
    //System.out.println("Create " + filename + "..." );
    
    if ( d_ext.isInterface() ) { 
      d_self = "d_self->d_object";
    } else { 
      d_self = "d_self";
    }

    try { 
      d_writer = Cxx.createHeader( d_ext, Cxx.FILE_ROLE_STUB, "STUBHDRS");
      d_writer.println();
      d_writer.openHeaderGuard( filename );

      writeDefineNullIORException();

      writeClassDeclaration();

      generateFrontIncludes();

      Cxx.nestPackagesInNamespaces( d_writer, d_ext );
      
      writeClassBegin();

      writeAssertionMethods();
      
      writeUserDefinedMethods();
      
      writeTypedefs();

      writeConstructors();

      writeCastingOperators();

      writeBindingSpecificMethods();

      writeClassEnd(); 
      
      Cxx.unnestPackagesInNamespaces( d_writer, d_ext );
      
      writeArrayDefinition();

      writeGetDData(d_ext.getSymbolID());

      generateEndIncludes();

      d_writer.closeHeaderGuard();
    } catch ( Exception ex ) { 
      throw new CodeGenerationException("Exception : " + ex.getMessage() );
    } finally { 
      if (d_writer != null) {
        d_writer.close();
        d_writer = null;
      }
    }
  }

  /**
   * This method write out a #define if Null IOR Exceptions are defined in
   * this file.
   */
  private void writeDefineNullIORException() { 
    String flagname = d_ext.getSymbolID().getFullName().replace('.', '_').
                            toUpperCase() + "_NULL_IOR_EXCEPTION";

    if(BabelConfiguration.getInstance().makeCxxCheckNullIOR()) {
      d_writer.printlnUnformatted("#ifndef " + flagname);
      d_writer.printlnUnformatted("#define " + flagname + " 1");
      d_writer.printlnUnformatted("#endif ");
    }
  }

  private void writeClassDeclaration() throws CodeGenerationException { 
    SymbolID id = d_ext.getSymbolID();
    String name = id.getShortName();
    
    d_writer.writeCommentLine("declare class before #includes");
    d_writer.writeCommentLine("(this alleviates circular #include guard "
      + "problems)[BUG#393]");
    Cxx.nestPackagesInNamespaces( d_writer, d_ext );
    d_writer.println("class " + name + ";");
    Cxx.unnestPackagesInNamespaces( d_writer, d_ext );
    d_writer.writeCommentLine("Some compilers need to define array template "
      + "before the specializations");
    d_writer.generateInclude("sidl_cxx.hh", true );
    d_writer.println("namespace sidl {");
    d_writer.tab();
    d_writer.println("template<>");
    d_writer.println("class array< " +  Cxx.getObjectName(id) + " >;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();

    //Forward declarations for method dependencies

    Set decls = new HashSet();
    for( Iterator i = d_ext.getMethods(true).iterator(); i.hasNext(); ) { 
      Method method = (Method) i.next();
      decls.addAll(method.getSymbolReferences()); 
    }
    if (!decls.isEmpty()){
      d_writer.writeComment("Forward declarations for method dependencies.",false);
      
      List entries = Utilities.sort(decls);
      
      for (Iterator i = entries.iterator(); i.hasNext(); ) { 
        SymbolID s = (SymbolID) i.next();
        Symbol symbol = (Symbol) Utilities.lookupSymbol(s);
        //Enumerated types are included, and hence shouldn't be forward declared.
        if(symbol.getSymbolType() != Type.ENUM) {
          Cxx.nestPackagesInNamespaces( d_writer, symbol );
          d_writer.println("class " + s.getShortName() +";");
          Cxx.unnestPackagesInNamespaces( d_writer, symbol );
        }
      }
    }
  }

  private void writeClassBegin() { 
    SymbolID id = d_ext.getSymbolID();
    String name = id.getShortName();

    d_writer.writeComment(d_ext, true);    
    d_writer.println("class " + name + " : public ::sidl::StubBase {");
    d_writer.tab();
  }

  /**
   * Return the comment description associated with the specified
   * built-in stub.
   */
  public static String getBuiltinComment(int type, boolean doStatic) {
    String cmt  = null;
    String desc = doStatic ? "static " : "";
    switch (type) {
      case SET_CHECKING:
        cmt = "Method to set the " + desc + "assertion checking level.";
        break;
      case DUMP_STATS:
        cmt = "Method to dump " + desc +  "assertion checking statistics.";
        break;
      case SET_HOOKS:
        cmt = "Method to enable/disable " + desc +  "hooks execution.";
        break;
    }
    return cmt;
  }

  /**
   * Generate the specified stub-only built-in method signature.
   */
  public static void generateBuiltinSignature(LanguageWriterForCxx lw, 
                                              int type, SymbolID id, 
                                              boolean doStatic,
                                              String terminator)
  {
    String suffix = doStatic ? IOR.s_static_suffix : "";
    String basename = null;
    switch (type) {
      case SET_CHECKING:
        basename = "_set_checking";
        break;
      case DUMP_STATS:
        basename = "_dump_stats";
        break;
      case SET_HOOKS:
        basename = "_set_hooks";
        break;
    }
    if (doStatic) {
      lw.println("static ");
    }
    lw.println("void");
    lw.print(Cxx.getSymbolNameWithoutLeadingColons(id,"") + "::" + basename);
    lw.println(suffix + "(");
    lw.tab();
    switch (type) {
      case SET_CHECKING:
        lw.println("int32_t level,");
        lw.println("double  threshold,");
        lw.println("int32_t resetCounters)" + terminator);
        break;
      case DUMP_STATS:
        lw.println("const char* filename)"+ terminator);
        break;
      case SET_HOOKS:
        lw.println("int32_t on)" + terminator);
        break;
    }
    lw.backTab();
  }

  /**
   * Return the comma-separated list of arguments associated with the
   * specified built-in method.  The entries must match the names and
   * ordering found (above) in generateStubBuiltinSignature().
   */
  public static String getBuiltinArgList(int type) {
    String args = null;
    switch (type) {
      case SET_CHECKING:
        args = "level, threshold, resetCounters";
        break;
      case DUMP_STATS:
        args = "filename";
        break;
      case SET_HOOKS:
        args = "on";
        break;
    }
    return args;
  }

  private void generateBuiltinPrototypes(SymbolID id, int stubType,
                                         boolean doStatic) 
  {
    d_writer.writeComment(getBuiltinComment(stubType, doStatic), true);
    generateBuiltinSignature(d_writer, stubType, id, doStatic, ";");
    d_writer.println();
  }

  private void generateAssertionPrototypes(SymbolID id, boolean doStatic) {
    generateBuiltinPrototypes(id, SET_CHECKING, doStatic);
    generateBuiltinPrototypes(id, DUMP_STATS, doStatic);
  }

  private void writeAssertionMethods() {
    if (!d_ext.isAbstract() && !d_ext.isInterface()) {
      SymbolID id        = d_ext.getSymbolID();
      boolean  hasStatic = d_ext.hasStaticMethod(true);

      if (IOR.supportAssertions(d_ext)) {
        if (hasStatic) {
          generateAssertionPrototypes(id, true);
        }
        generateAssertionPrototypes(id, false);
      }
      if (IOR.supportHooks(d_ext)) {
        if (hasStatic) {
          generateBuiltinPrototypes(id, SET_HOOKS, true);
        }
        generateBuiltinPrototypes(id, SET_HOOKS, false);
      }
    }
  }

  private void writeUserDefinedMethods() throws CodeGenerationException { 
    final String static_desc = "User-defined static method.";
    final String nonstatic_desc = "User-defined non-static method.";
    d_writer.beginBoldComment();
    d_writer.println("User Defined Methods");
    d_writer.endBoldComment();

    d_writer.backTab();
    d_writer.println("public:");
    d_writer.tab();
    
    Iterator m = null;
    m = d_ext.getStaticMethods(true).iterator();
    while (m.hasNext()) {
      Method method = (Method) m.next();
      if(!Cxx.inlineStub(method)) {
        Cxx.generateMethodSignature( d_writer, method, "", static_desc,
                                     Cxx.FILE_ROLE_STUB, true );
        d_writer.println();
        if(method.hasRarray()) {
          Cxx.generateMethodSignature( d_writer, method, "", static_desc,
                                       Cxx.FILE_ROLE_STUB, false );
          d_writer.println();
        }
      } else {
        generateInlineMethodDispatch(method, static_desc, false);
        d_writer.println();
      }
    }
    
    m = d_ext.getNonstaticMethods(true).iterator();
    while (m.hasNext()) {
      Method method = (Method) m.next();
      if(!Cxx.inlineStub(method)) {
        Cxx.generateMethodSignature( d_writer, method, "", nonstatic_desc,
                                     Cxx.FILE_ROLE_STUB, true );
        d_writer.println();
        if(method.hasRarray()) {
          Cxx.generateMethodSignature( d_writer, method, "", nonstatic_desc,
                                       Cxx.FILE_ROLE_STUB, false );
          d_writer.println();
        }
        
      } else {
        generateInlineMethodDispatch(method, nonstatic_desc, false);
        d_writer.println();
      }
    }
    d_writer.beginBoldComment();
    d_writer.println("End User Defined Methods");
    d_writer.println("(everything else in this file is specific to");
    d_writer.println(" Babel's C++ bindings)");
    d_writer.endBoldComment();
  }

  private void writeTypedefs() { 
    SymbolID id = d_ext.getSymbolID();

    d_writer.backTab();
    d_writer.println("public:");
    d_writer.tab();
    d_writer.println(TYPE_DEF + IOR.getObjectName( id ) + " ior_t;");
    d_writer.println(TYPE_DEF + IOR.getExternalName( id ) + " ext_t;");
    d_writer.println(TYPE_DEF + IOR.getSEPVName( id ) + " sepv_t;");
    d_writer.println();
  }

  private void writeConstructors() { 
    SymbolID id = d_ext.getSymbolID();
    String name = id.getShortName();

    d_writer.writeCommentLine("default constructor");
    d_writer.println( name + "() : d_self(0), d_weak_reference(false) { }");
    d_writer.println();
    
    if( ! d_ext.isAbstract() ) {
      d_writer.writeCommentLine("static constructor");
      d_writer.println("static " + Cxx.getObjectName(id) + " _create();");
      d_writer.println();
    }

    d_writer.writeCommentLine("default destructor");
    d_writer.println("virtual ~" + name + " ();");
    d_writer.println();
    
    d_writer.writeCommentLine("copy constructor");
    d_writer.println( name + " ( const " + name + "& original );");
    d_writer.println();

    d_writer.writeCommentLine("assignment operator");
    d_writer.println( name + "& operator= ( const " + name + "& rhs );");
    d_writer.println();
  }
  
  private void writeCastingOperators() { 
    SymbolID id = d_ext.getSymbolID();
    String name = id.getShortName();

    d_writer.writeCommentLine("conversion from ior to C++ class");
    d_writer.println( name + " ( " + name + "::ior_t* ior );");
    d_writer.println();

    d_writer.writeCommentLine("Alternate constructor: does not call addRef()");
    d_writer.writeCommentLine("(sets d_weak_reference=isWeak)");
    d_writer.writeCommentLine("For internal use by Impls (fixes bug#275)");
    d_writer.println( name + " ( " + name + "::ior_t* ior, bool isWeak );");
    d_writer.println();

    d_writer.writeCommentLine("conversion from a StubBase");
    d_writer.println( name + " ( const ::sidl::StubBase& base );");
    d_writer.println();
  }

  private void writeBindingSpecificMethods() { 
    String ior_ptr = "ior_t*";

    d_writer.println( ior_ptr + " _get_ior() { return d_self; }");
    d_writer.println();

    d_writer.println("const " + ior_ptr + " _get_ior() const { return d_self;"
      + " }");
    d_writer.println();

    d_writer.println( "void _set_ior( " + ior_ptr + " ptr ) { d_self = ptr; }");
    d_writer.println();

    d_writer.println("bool _is_nil() const { return (d_self==0); }");
    d_writer.println();

    d_writer.println("bool _not_nil() const { return (d_self!=0); }");
    d_writer.println();

    d_writer.println("bool operator !() const { return (d_self==0); }");
    d_writer.println();
    
    d_writer.backTab();
    d_writer.println("protected:");
    d_writer.tab();
 
    d_writer.println("virtual void* _cast(const char* type) const;");
    d_writer.println();

    d_writer.backTab();
    d_writer.println("private:");
    d_writer.tab();
    d_writer.writeCommentLine("Pointer to sidl's IOR type (one per instance)");
    d_writer.println("ior_t * d_self;");
    d_writer.println();
    d_writer.writeCommentLine("Weak references (used by Impl's only) don't "
      + "add/deleteRef()");
    d_writer.println("bool d_weak_reference;");
    d_writer.println();
    d_writer.writeCommentLine("Pointer to external (DLL loadable) symbols "
      + "(shared among instances)");
    d_writer.println("static const ext_t * s_ext;");
    d_writer.println();
    d_writer.backTab();
    d_writer.println("public:");
    d_writer.tab();
    d_writer.println("static const ext_t * _get_ext() throw ( ::sidl::"
      + "NullIORException );");
    d_writer.println();
    if ( d_ext.hasStaticMethod(true) ) { // if has static methods
      d_writer.println("static const sepv_t * _get_sepv() {");
      d_writer.tab();
      d_writer.println("return (*(_get_ext()->getStaticEPV))();");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();
    }
  }
  private void writeClassEnd() { 
    String name = d_ext.getSymbolID().getShortName();
    d_writer.backTab();
    d_writer.println("}; // end class " + name);
  } 

  private void writeArrayDefinition() { 
    SymbolID id = d_ext.getSymbolID();
    String cxx_item_t = Cxx.getObjectName(id);
    String cxx_array_t = "array< " + cxx_item_t + " >";
    String ior_item_t = IOR.getObjectName(id);
    String ior_array_t = IOR.getArrayName(id);
    String array_traits = "array_traits< " +cxx_item_t + " >";

    d_writer.println("namespace sidl {");
    d_writer.tab();
    
    d_writer.writeCommentLine("traits specialization");
    d_writer.println("template<>");
    d_writer.println("struct " + array_traits + " {");
    d_writer.tab();
    d_writer.println(TYPE_DEF + cxx_array_t + " cxx_array_t;");
    d_writer.println(TYPE_DEF + cxx_item_t + " cxx_item_t;");
    d_writer.println(TYPE_DEF + ior_array_t + " ior_array_t;");
    d_writer.println(TYPE_DEF + "sidl_interface__array ior_array_internal_t;");
    d_writer.println(TYPE_DEF +  ior_item_t + " ior_item_t;");
    d_writer.println(TYPE_DEF + " cxx_item_t value_type;");
    d_writer.println(TYPE_DEF + " value_type reference;");
    d_writer.println(TYPE_DEF + " value_type* pointer;");
    d_writer.println(TYPE_DEF + " const value_type const_reference;");
    d_writer.println(TYPE_DEF + " const value_type* const_pointer;");
    d_writer.println(TYPE_DEF + " array_iter< " + array_traits 
      + " > iterator;");
    d_writer.println(TYPE_DEF + " const_array_iter< " + array_traits
      + " > const_iterator;");
    d_writer.backTab();
    d_writer.println("};");
    d_writer.println();

    d_writer.writeCommentLine("array specialization");
    d_writer.println("template<>");
    d_writer.println("class " + cxx_array_t +
                     ": public interface_array< " + array_traits + " > {");
    d_writer.println("public:");
    d_writer.tab();
    
    d_writer.println("typedef interface_array< " + array_traits + " > Base;");
    d_writer.println(TYPE_DEF + array_traits + "::cxx_array_t          "
      + "cxx_array_t;");
    d_writer.println(TYPE_DEF + array_traits + "::cxx_item_t           "
      + "cxx_item_t;");
    d_writer.println(TYPE_DEF + array_traits + "::ior_array_t          "
      + "ior_array_t;");
    d_writer.println(TYPE_DEF + array_traits + "::ior_array_internal_t "
      + "ior_array_internal_t;");
    d_writer.println(TYPE_DEF + array_traits + "::ior_item_t           "
      + "ior_item_t;");
    d_writer.println();
    d_writer.beginBlockComment(true);
    d_writer.println("conversion from ior to C++ class");
    d_writer.println("(constructor/casting operator)");
    d_writer.endBlockComment(true);
    d_writer.println("array( " + ior_array_t + "* src = 0) : Base(src) {}");
    d_writer.println();
 

    d_writer.beginBlockComment(true);
    d_writer.println("copy constructor");
    d_writer.endBlockComment(true);
    d_writer.println("array( const " + cxx_array_t + "&src) : Base(src) {}");
    d_writer.println();

    if (BabelConfiguration.getBaseInterface().equals(id.getFullName())) {
      d_writer.beginBlockComment(true);
      d_writer.println("Assignment to promote a generic array to an");
      d_writer.println("array of sidl.BaseInterface references. This");
      d_writer.println("will produce a nil array if the generic array");
      d_writer.println("isn't an array of objects/interfaces.");
      d_writer.endBlockComment(true);
      d_writer.println(cxx_array_t + "&");
      d_writer.println("operator =(const basearray &rhs) throw() {");
      d_writer.tab();
      d_writer.println("if (this->d_array != rhs._get_baseior()) {");
      d_writer.tab();
      d_writer.println("deleteRef();");
      d_writer.println("this->d_array =");
      d_writer.tab();
      d_writer.println("(rhs._get_baseior() &&");
      d_writer.println(" (sidl_interface_array == rhs.arrayType()))");
      d_writer.println("? const_cast<sidl__array *>(rhs._get_baseior())");
      d_writer.println(": NULL;");
      d_writer.backTab();
      d_writer.println("addRef();");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println("return *this;");
      d_writer.backTab();
      d_writer.println("}");
    }
 
    d_writer.beginBlockComment(true);
    d_writer.println("assignment");
    d_writer.endBlockComment(true);
    d_writer.println(cxx_array_t + "&");
    d_writer.println("operator =( const " + cxx_array_t + "&rhs ) { ");
    d_writer.tab();
    d_writer.println("if (d_array != rhs._get_baseior()) {");
    d_writer.tab();
    d_writer.println("if (d_array) deleteRef();");
    d_writer.println("d_array = const_cast<sidl__array *>(rhs._get_baseior())"
      + ";");
    d_writer.println("if (d_array) addRef();");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println("return *this;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
 
    d_writer.backTab();
    d_writer.println("};");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  //isSuper is only true if we are generating super methods for Impl files.
  private void generateInlineMethodDispatch( Method m, String altcomment, 
                                             boolean isSuper ) 
    throws CodeGenerationException 
  { 
    if ( m == null ) { return; }
    String shortMethodName = m.getShortMethodName();
    String className = d_ext.getSymbolID().getShortName();
    Type return_type = m.getReturnType();

    if ( shortMethodName.equals(className) ) { 
      shortMethodName = "f_" + shortMethodName;
      System.out.println("WARNING: gov.llnl.babel.backend.Cxx.CxxStubHeader: "
        + "sidl / C++ conflict!");
      System.out.println("         methodName == className is not allowed in "
        + "C++");
      System.out.println("         (this is restricted to constructors in C++"
        + ")");
      System.out.println("         changing to " + className + "::" 
        + shortMethodName + "()");
    }

    //Output function deleration
    Cxx.generateInlineMethodSignature(d_writer, m, "user defined static method",
                                      Cxx.FILE_ROLE_STUB, isSuper);
    d_writer.tab();

    if ( (! m.isStatic()) &&  BabelConfiguration.getInstance().makeCxxCheckNullIOR()) { 

      d_writer.println("if ( d_self == 0 ) {");
      d_writer.tab();
      d_writer.println("throw ::sidl::NullIORException( ::std::string (");
      d_writer.tab();
      d_writer.disableLineBreak();
      d_writer.println( "\"" + "Null IOR Pointer in \\\"" 
        + Cxx.getMethodStubName(d_ext.getSymbolID(),shortMethodName) 
        + "()\\\"\"");
      d_writer.enableLineBreak();
      d_writer.backTab();
      d_writer.println("));");
      d_writer.backTab();
      d_writer.println("}");
    }

    //Output almost nothing!
    d_writer.println(Cxx.generateInitialization(m, d_ext, d_self));

    if ( ! m.isStatic() ) { 
      if (shortMethodName.equals("addRef") ||
          shortMethodName.equals("deleteRef") ) { 
        d_writer.println("if ( !d_weak_reference ) {");
        d_writer.tab();
      }
    }

    //Prepare for IOR call!
    d_writer.println(Cxx.generatePreIORCall(m, d_ext, d_self, false));

    if(isSuper) {
      Class cls = (Class) d_ext;
      String tmp_self = Cxx.reinterpretCast(IOR.getObjectName(cls.
                          getParentClass().getSymbolID()) + "*", "superSelf");
      d_writer.println(Cxx.generateIORCall(m, d_ext, tmp_self, isSuper, true));
    }
    else
      d_writer.println(Cxx.generateIORCall(m, d_ext, d_self, isSuper, true));
    if (m.getThrows().size()>0) { // if throws exception
      d_writer.println("if (_exception != 0 ) {");      
      d_writer.tab();
      d_writer.println("void * _p = 0;");
      d_writer.println(IOR.getExceptionFundamentalType() +
                       "throwaway_exception;");
      Object [] exceptions = m.getThrows().toArray();
      Arrays.sort(exceptions, new LevelComparator(SymbolTable.getInstance()));
      
      for( int i=0; i<exceptions.length; ++i) {
        SymbolID exid = (SymbolID)exceptions[i];
        d_writer.println("if ( (_p=(*(_exception->d_epv->f__cast))(_exception->d_object,"
          + " \"" + exid.getFullName() + "\", &throwaway_exception)) != 0 ) {");
        d_writer.tab();
        d_writer.println(IOR.getObjectName(exid) + " * _realtype = " 
          + Cxx.reinterpretCast(IOR.getObjectName(exid)+"*" , "_p") + ";");
        d_writer.println("(*_exception->d_epv->f_deleteRef)(_exception->d_object, &throwaway_exception);");
        d_writer.writeCommentLine("Note: alternate constructor does not "
          + "increment refcount.");
        d_writer.println("throw " + Cxx.getObjectName(exid) + "( _realtype, "
          + "false );");
        d_writer.backTab();
        d_writer.println("}");
      }
      d_writer.backTab();
      d_writer.println("}");
    }

    //Clean up from return from IOR
    d_writer.println(Cxx.generatePostIORCall(m, d_ext, d_self, true));

    //d_writer.println( post_ior.toString().trim() );
    if ( ! m.isStatic() ) { 
      if ( shortMethodName.equals("deleteRef") ) { 
        d_writer.println("d_self = 0;");
      }
      if (  shortMethodName.equals("addRef") 
         || shortMethodName.equals("deleteRef") ) { 
        d_writer.backTab();
        d_writer.println("}");
      }
    }

    if ( return_type.getType() != Type.VOID ) { 
      d_writer.println("return _result;");
    }
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  private void writeGetDData(SymbolID id) {
    Cxx.beginExternCRegion( d_writer );
    d_writer.writeComment("Get class's d_data (usually for getting InstanceHandle).", true);
    d_writer.println("void*");
    d_writer.println(C.getFullMethodName(id, "_get_ddata") 
                     + "("+IOR.getObjectName(id)+"* self);");
    Cxx.endExternCRegion( d_writer );
  }
}
