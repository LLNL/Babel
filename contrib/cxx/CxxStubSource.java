//
// File:        CxxStubSource.java
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
import gov.llnl.babel.backend.LevelComparator;
import gov.llnl.babel.backend.writers.LanguageWriterForCxx;
import gov.llnl.babel.symbols.Comment;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.SymbolTable;
import gov.llnl.babel.symbols.Type;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Create and write a header for a Cxx C extension class to wrap a 
 * BABEL extendable in a Cxx object. The header has to expose a 
 * function to create a wrapped IOR, a function to check if a 
 * <code>PyObject</code> is an instance of this extension type, and
 * an import macro.
 */
public class CxxStubSource {
  private Extendable d_ext = null;
  private LanguageWriterForCxx d_writer = null;
  private String d_self = null;
  
  /**
   * Create an object capable of generating the header file for a
   * BABEL extendable.
   *
   * @param ext   an interface or class symbol that needs a header
   *              file for a Cxx C extension class.
   */
  public CxxStubSource(Extendable ext) {
    d_ext = ext;
  }

//   /**
//    *
//    * @param ext   a class symbol that we're generating a super class to
//    *              put in the impl file.
//    *
//    * @param writer LanguageWriterForCxx 
//    */
//   public CxxStubSource(Class cls, LanguageWriterForCxx writer) {
//     d_ext = (Extendable) cls;
//     d_writer = writer;
//   }
  
//   public static void generateSuper(Class cls, LanguageWriterForCxx writer) throws CodeGenerationException {
//     CxxStubSource stub = new CxxStubSource(cls, writer);
//     stub.generateSuper();

//   }

//   public void generateSuper() throws CodeGenerationException {
//     Class cls = (Class) d_ext;
//     SymbolID clsID = cls.getSymbolID();
//     final String ext_name = IOR.getExternalName(clsID);
//     d_writer.println("public:");
//     d_writer.writeComment("Hold pointer to IOR functions.", false);

//     d_writer.println("class Super {");
//     d_writer.tab();
//     d_writer.println();
//     d_writer.println("private:");
//     d_writer.tab();
//     d_writer.writeComment("Hold pointer to Super EPV", false);
//     d_writer.println(/*"const " + */IOR.getEPVName(cls.getParentClass().getSymbolID()) + 
//                      "* superEPV;");
//     d_writer.println(/*"const " + */IOR.getObjectName(clsID) + "* superSelf;");
//     d_writer.println();
//     d_writer.backTab();
//     d_writer.println("public:"); 
//     d_writer.tab();
//     d_writer.println("Super() : superEPV(NULL), superSelf(NULL) {}");



//     d_writer.println();
//     d_writer.println("Super("+/*const "+*/d_ext.getSymbolID().getShortName()+" loc_self) {");
//     d_writer.tab();
//     d_writer.println("superEPV = loc_self._get_ext()->getSuperEPV();");
//     d_writer.println("superSelf = loc_self._get_ior();");
//     d_writer.backTab();
//     d_writer.println("}");
//     //GENERATE FUNCTIONS HERE!
//     CxxStubHeader.generateSupers(cls.getOverwrittenClassMethods(), cls, d_writer);
    
//     d_writer.backTab();
//     d_writer.backTab();
//     d_writer.println("};");
//   }

  /**
   * Generate the header file for the extendable with which this object was
   * created.
   *
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception for problems during the code
   *    generation phase.
   */
  public synchronized void generateCode() throws CodeGenerationException {

    if ( d_ext.isInterface() ) { 
      d_self = "d_self->d_object";
    } else { 
      d_self = "d_self";
    }

    try { 
      d_writer = Cxx.createSource( d_ext, Cxx.FILE_ROLE_STUB, "STUBSRCS" );
      d_writer.println();

      writeIncludes();

      writeUserDefinedMethods();

      writeConstructors();

      writeCastingOperators();

      if ( !d_ext.isInterface()) {
        if ( !d_ext.isAbstract() ) {
          writeAssertNHooks();
        }
        writeDynamicImplStuff();
      }
    } finally { 
      if (d_writer != null) {
        d_writer.close();
        d_writer = null;
      }
    }
  }

  private void writeIncludes() throws CodeGenerationException { 
    SymbolID id = d_ext.getSymbolID();
    d_writer.generateInclude( Cxx.generateFilename( d_ext.getSymbolID(), 
                                                    Cxx.FILE_ROLE_STUB, 
                                                    Cxx.FILE_TYPE_CXX_HEADER ),
                              true);
    d_writer.generateInclude( Cxx.generateFilename( "sidl.BaseInterface",
                                                    Cxx.FILE_ROLE_STUB, 
                                                    Cxx.FILE_TYPE_CXX_HEADER
                                                    ),
                              true );
    d_writer.generateInclude(  Cxx.generateFilename( "sidl.BaseClass",
                                                    Cxx.FILE_ROLE_STUB, 
                                                    Cxx.FILE_TYPE_CXX_HEADER
                                                    ),
                               true );
    if ( d_ext.hasExceptionThrowingMethod(true) ) { 
      d_writer.generateInclude( Cxx.generateFilename( "sidl.BaseException",
                                                    Cxx.FILE_ROLE_STUB, 
                                                    Cxx.FILE_TYPE_CXX_HEADER), 
                                true );
    }
    d_writer.generateInclude( "sidl_String.h", false );
    if (!BabelConfiguration.isSIDLBaseClass(id)) {
      d_writer.generateInclude("babel_config.h", false);
      d_writer.printlnUnformatted("#ifdef SIDL_DYNAMIC_LIBRARY");
      d_writer.printlnUnformatted("#include <stdio.h>");
      d_writer.printlnUnformatted("#include <stdlib.h>");
      d_writer.generateInclude("sidl_Loader.hh", false);
      d_writer.printlnUnformatted("#endif");
    }
    //Generate method dependency includes
    Cxx.generateSourceIncludes(d_writer, d_ext);

    d_writer.println();
  } 

  private void writeConstructors() { 
    SymbolID id = d_ext.getSymbolID();
    final String fullName = Cxx.getObjectName(id);
    final String fullNameWithoutLeadingColons = Cxx.
                                       getSymbolNameWithoutLeadingColons(id,"");
    final String name = id.getShortName();    
    final String ior_ptr = "ior_t*";

    if( !d_ext.isAbstract() ) { 
      d_writer.writeCommentLine("static constructor");
      d_writer.println(fullName);
      d_writer.println(fullNameWithoutLeadingColons + "::_create() {");
      d_writer.tab();
      d_writer.println(IOR.getExceptionFundamentalType() + "_exception;");
      d_writer.println(IOR.getObjectName(id) + 
                       " *ior_self = (*_get_ext()->createObject)(&_exception);");
      d_writer.println("if (_exception) {");
      d_writer.tab();
      d_writer.println("void *_p;");
      d_writer.println(IOR.getExceptionFundamentalType() + "throwaway_exception;");
      d_writer.println("if ((_p=(*(_exception->d_epv->f__cast))(_exception->d_object, \"sidl.RuntimeException\", &throwaway_exception))) {");
      d_writer.tab();
      d_writer.println("(*_exception->d_epv->f_deleteRef)(_exception->d_object, &throwaway_exception);");
      d_writer.println("throw ::sidl::RuntimeException(reinterpret_cast<struct sidl_RuntimeException__object * >( _p), false);");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println("else {");
      d_writer.tab();
      d_writer.println("(*_exception->d_epv->f_deleteRef)(_exception->d_object, &throwaway_exception);");
      d_writer.println("ior_self = 0;");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println("return " +  Cxx.getObjectName(id) 
        + "(ior_self , false );");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();
    }

    d_writer.writeCommentLine("default destructor");
    d_writer.println(fullNameWithoutLeadingColons + "::~" + name + " () {");
    d_writer.tab();
    d_writer.println("if ( d_self != 0 ) {");
    d_writer.tab();
    d_writer.println("deleteRef();");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();

    d_writer.writeCommentLine("copy constructor");
    d_writer.println( fullNameWithoutLeadingColons + "::" + name + 
                      " ( const " + fullName + "& original ) {");
    d_writer.tab();
    d_writer.println("d_self = " + Cxx.constCast( ior_ptr, "original.d_self" ) 
      + ";");
    d_writer.println("d_weak_reference = false;");
    d_writer.println("if (d_self != 0 ) {");
    d_writer.tab();
    d_writer.println("addRef();");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();

    d_writer.writeCommentLine("assignment operator");
    d_writer.println( fullName + "&");
    d_writer.println( fullNameWithoutLeadingColons + "::operator=( const " 
      + fullName + "& rhs ) {");
    d_writer.tab();
    d_writer.println("if ( d_self != rhs.d_self ) {");
    d_writer.tab();
    d_writer.println("if ( d_self != 0 ) {");
    d_writer.tab();
    d_writer.println("deleteRef();");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println("d_self = " + Cxx.constCast( ior_ptr, "rhs.d_self") + ";");
    d_writer.println("d_weak_reference = false;");
    d_writer.println("if ( d_self != 0 ) {");
    d_writer.tab();
    d_writer.println("addRef();");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println("return *this;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  private void writeCastingOperators() { 
    SymbolID id = d_ext.getSymbolID();
    final String fullName = Cxx.getObjectName(id);
    final String fullNameWithoutLeadingColons = Cxx.
                                      getSymbolNameWithoutLeadingColons(id,"");
    final String name = id.getShortName();   

 
    d_writer.writeCommentLine("conversion from ior to C++ class");
    d_writer.println( fullNameWithoutLeadingColons + "::" + name + " ( " 
      + fullName + "::ior_t* ior ) ");
    d_writer.println("    : d_self( ior ), d_weak_reference(false) {");
    d_writer.tab();
    d_writer.println("if ( d_self != 0 ) {");
    d_writer.tab();
    d_writer.println("addRef();");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();

    d_writer.writeCommentLine("Alternate constructor: does not call addRef()");
    d_writer.writeCommentLine("(sets d_weak_reference=isWeak)");
    d_writer.writeCommentLine("For internal use by Impls (fixes bug#275)");
    d_writer.println( fullNameWithoutLeadingColons + "::" + name +" ( " 
      + fullName + "::ior_t* ior, bool isWeak ) ");
    d_writer.println("    : d_self( ior ), d_weak_reference(isWeak) { ");
    //d_writer.tab();
    //d_writer.println("// addRef();");
    //d_writer.backTab();
    d_writer.println("}");
    d_writer.println();


    d_writer.writeCommentLine("conversion from a StubBase");
    d_writer.println( fullNameWithoutLeadingColons + "::" + name 
      + " ( const ::sidl::StubBase& base )");
    d_writer.println("{");
    d_writer.tab();
    d_writer.println( "d_self = " 
      + Cxx.reinterpretCast("ior_t*", "base._cast(\"" + id.getFullName() 
          + "\")") + ";");
    d_writer.println("d_weak_reference = false;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();

    d_writer.writeCommentLine("protected method that implements casting");
    d_writer.println("void* " + fullNameWithoutLeadingColons 
      + "::_cast(const char* type) const");
    d_writer.println("{");
    d_writer.tab();
    d_writer.println("void* ptr = 0;");
    d_writer.println("if ( d_self != 0 ) {");
    d_writer.tab();
    d_writer.println(IOR.getExceptionFundamentalType() + "throwaway_exception;");
    d_writer.print("ptr = ");
    if (d_ext.isInterface()) {
      d_writer.print(Cxx.reinterpretCast( "void*", "(*d_self->" 
                       + IOR.getEPVVar(IOR.PUBLIC_EPV) 
                       + "->f__cast)(d_self->d_object, type, &throwaway_exception)"));
    } else {
      d_writer.print(Cxx.reinterpretCast( "void*", "(*d_self->" 
                       + IOR.getEPVVar(IOR.PUBLIC_EPV) 
                       + "->f__cast)(d_self, type, &throwaway_exception)"));
    }
    d_writer.println(";");
    d_writer.backTab();
    d_writer.println("}");    
    d_writer.println("return ptr;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  private void generateBuiltinStub(SymbolID id, int stubType, int iorType, 
                                   boolean doStatic)
     throws CodeGenerationException
  {
    d_writer.writeComment(CxxStubHeader.getBuiltinComment(stubType, doStatic),
      false);
    CxxStubHeader.generateBuiltinSignature(d_writer, stubType, id, doStatic, 
      "");
    d_writer.println("{");
    d_writer.tab();
    String baseName = IOR.getBuiltinName(iorType, doStatic);
    if (doStatic) {
      d_writer.print("( _get_sepv()->" + baseName + ")( ");
      d_writer.println(CxxStubHeader.getBuiltinArgList(stubType) + ");");
    } else { 
      d_writer.println("if ( d_self != 0 ) {");
      d_writer.tab();
      d_writer.print( "(*d_self->" + IOR.getEPVVar(IOR.PUBLIC_EPV) 
        + "->f_" + baseName + ")(" + d_self + ", ");
      d_writer.println(CxxStubHeader.getBuiltinArgList(stubType) + ");");
      d_writer.backTab();
      d_writer.println("}");
    }
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  private void writeAssertNHooks() throws CodeGenerationException {
    boolean  hasStatic = d_ext.hasStaticMethod(true);
    SymbolID id        = d_ext.getSymbolID();

    if (IOR.supportAssertions(d_ext)) {
      if (hasStatic) {
        generateBuiltinStub(id, CxxStubHeader.SET_CHECKING, IOR.CHECKS, true);
        generateBuiltinStub(id, CxxStubHeader.DUMP_STATS, IOR.DUMP_STATS, true);
      }
      generateBuiltinStub(id, CxxStubHeader.SET_CHECKING, IOR.CHECKS, false);
      generateBuiltinStub(id, CxxStubHeader.DUMP_STATS, IOR.DUMP_STATS, false);
    }
    if (IOR.supportHooks(d_ext)) {
      if (hasStatic) {
        generateBuiltinStub(id, CxxStubHeader.SET_HOOKS, IOR.HOOKS, true);
      }
      generateBuiltinStub(id, CxxStubHeader.SET_HOOKS, IOR.HOOKS, false);
    }
  }

  private void writeDynamicImplStuff() throws CodeGenerationException { 
    SymbolID id = d_ext.getSymbolID();
    final String extName = IOR.getExternalName(id);
    final String fullName = Cxx.getObjectName(id);
    final String fullNameWithoutLeadingColons = Cxx.
                                      getSymbolNameWithoutLeadingColons(id,"");
    d_writer.writeCommentLine("Static data type");
    d_writer.println("const " + fullName + "::ext_t * " 
      + fullNameWithoutLeadingColons + "::s_ext = 0;");
    d_writer.println();

    d_writer.writeCommentLine("private static method to get static data type");
    d_writer.println("const " + fullName + "::ext_t *");
    d_writer.println( fullNameWithoutLeadingColons + "::_get_ext()");
    d_writer.println("  throw (::sidl::NullIORException)");
    d_writer.println("{");
    d_writer.tab();
    d_writer.println("if (! s_ext ) {");
    d_writer.tab();
    if (BabelConfiguration.isSIDLBaseClass(id)) { 
      d_writer.println("s_ext = " + IOR.getExternalFunc(id) + "();");
    } else {
      d_writer.printlnUnformatted("#ifdef SIDL_STATIC_LIBRARY");
      d_writer.println("s_ext = " + IOR.getExternalFunc(id) + "();");
      d_writer.printlnUnformatted("#else");
      d_writer.println("s_ext = ("+extName+"*)sidl_dynamicLoadIOR(\""+id.getFullName()+"\",\""+IOR.getExternalFunc(id)+"\") ;");
      d_writer.printlnUnformatted("#endif");
    }
    d_writer.backTab();
    d_writer.println("}");
    

    d_writer.println("return s_ext;");
    d_writer.backTab();
    d_writer.println("}");   
    d_writer.println();    
  }

  private void writeUserDefinedMethods() throws CodeGenerationException { 
    final String stat_desc = "user-defined static method.";
    final String nonstat_desc = "user-defined non-static method.";
    d_writer.beginBoldComment();
    d_writer.println("User Defined Methods");
    d_writer.endBoldComment();

    Iterator m = null;
    m = d_ext.getStaticMethods(true).iterator();
    while (m.hasNext()) {
      Method method = (Method) m.next();
      if(!Cxx.inlineStub(method)) {
        generateMethodDispatch( method, stat_desc, true );
        d_writer.println();
        if(method.hasRarray()) {
          generateMethodDispatch( method, stat_desc, false);
          d_writer.println();
        }
      }
    }

    m = d_ext.getNonstaticMethods(true).iterator();
    while (m.hasNext()) {
      Method method = (Method) m.next();
      if(!Cxx.inlineStub(method)) {
        generateMethodDispatch( method, nonstat_desc, true );
        d_writer.println();
        if(method.hasRarray()) {
          generateMethodDispatch( method, nonstat_desc, false);
          d_writer.println();
        }
      }
    }

    d_writer.beginBoldComment();
    d_writer.println("End User Defined Methods");
    d_writer.println("(everything else in this file is specific to");
    d_writer.println(" Babel's C++ bindings)");
    d_writer.endBoldComment();
  }

  /** 
   *  Function generates function definitions. 
   */
  private void generateMethodDispatch( Method m, String altcomment, boolean rarrays ) 
    throws CodeGenerationException { 
    if ( m == null ) { return; }
    String shortMethodName = m.getShortMethodName();
    String className = d_ext.getSymbolID().getShortName();
    Comment comment = m.getComment();
    Type return_type = m.getReturnType();

    d_writer.writeComment( comment, altcomment );
    if ( shortMethodName.equals(className) ) { 
      shortMethodName = "f_" + shortMethodName;
      System.out.println("WARNING: gov.llnl.babel.backend.Cxx.CxxStubSource: "
        + "sidl / C++ conflict!");
      System.out.println("         methodName == className is not allowed in "
        + "C++");
      System.out.println("         (this is restricted to constructors in C++"
        + ")");
      System.out.println("         changing to " + className + "::" 
        + shortMethodName + "()");
    }

    //Output function deleration
    d_writer.println(Cxx.generateFunctionDeclaration(m, d_ext, d_self, rarrays));

    d_writer.println();
    d_writer.println("{");
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
    d_writer.println(Cxx.generatePreIORCall(m, d_ext, d_self, rarrays));

    d_writer.println(Cxx.generateIORCall(m, d_ext, d_self, false, rarrays));
    if (m.getThrows().size()>0) { // if throws exception
      d_writer.println("if (_exception != 0 ) {");      
      d_writer.tab();
      d_writer.println("void * _p = 0;");
      d_writer.println(IOR.getExceptionFundamentalType() + "throwaway_exception;");
      Object [] exceptions = m.getThrows().toArray();
      Arrays.sort(exceptions, new LevelComparator(SymbolTable.getInstance()));
      
      for( int i=0; i<exceptions.length; ++i) {
        SymbolID exid = (SymbolID)exceptions[i];
        d_writer.println("if ( (_p=(*(_exception->" 
          + IOR.getEPVVar(IOR.PUBLIC_EPV) +
          "->f__cast))(_exception->d_object, \"" 
          + exid.getFullName() + "\", &throwaway_exception)) != 0 ) {");
        d_writer.tab();
        d_writer.println(IOR.getObjectName(exid) + " * _realtype = " 
          + Cxx.reinterpretCast(IOR.getObjectName(exid)+"*" , "_p") + ";");
        d_writer.println("(*_exception->d_epv->f_deleteRef)(_exception->d_object, &throwaway_exception);");
        d_writer.writeCommentLine("Note: alternate constructor does not "
          + "increment refcount.");
        d_writer.println("throw " + Cxx.getObjectName(exid) 
          + "( _realtype, false );");
        d_writer.backTab();
        d_writer.println("}");
      }
      d_writer.backTab();
      d_writer.println("}");
    }

    //Clean up from return from IOR
    d_writer.println(Cxx.generatePostIORCall(m, d_ext, d_self, rarrays));

    //d_writer.println( post_ior.toString().trim() );
    if ( ! m.isStatic() ) { 
      if ( shortMethodName.equals("deleteRef") ) { 
        d_writer.println("d_self = 0;");
      }
      if (shortMethodName.equals("addRef") ||
          shortMethodName.equals("deleteRef") ) { 
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
}
