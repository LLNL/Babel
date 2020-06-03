//
// File:        CxxStubSource.java
// Package:     gov.llnl.babel.backend.ucxx
// Revision:    @(#) $Revision: 7480 $
// Date:        $Date: 2012-06-21 14:44:45 -0700 (Thu, 21 Jun 2012) $
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

package gov.llnl.babel.backend.ucxx;

import gov.llnl.babel.BabelConfiguration;
import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.ucxx.Cxx;
import gov.llnl.babel.backend.rmi.RMIStubSource;
import gov.llnl.babel.backend.rmi.RMI;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.writers.LanguageWriterForCxx;
import gov.llnl.babel.symbols.Comment;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Interface;
import gov.llnl.babel.symbols.Class;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.Type;
import java.util.Iterator;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.List;


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
  private Context d_context;
  
  /**
   * Create an object capable of generating the header file for a
   * BABEL extendable.
   *
   * @param ext   an interface or class symbol that needs a header
   *              file for a Cxx C extension class.
   */
  public CxxStubSource(Extendable ext,
                       Context context) {
    d_ext = ext;
    d_context = context;
  }
  /**
   * Create an object capable of generating the header file for a
   * BABEL extendable.
   *
   * @param ext   an interface or class symbol that needs a header
   *              file for a Cxx C extension class.
   */
  public CxxStubSource(Extendable ext,
                       LanguageWriterForCxx writer,
                       Context context) {
    d_ext = ext;
    d_writer = writer;
    d_context = context;
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
  public static void generateSupers(Class             cls,
                                    LanguageWriterForCxx writer,
                                    Context context)
    throws CodeGenerationException
  {
    CxxStubSource source = new CxxStubSource(cls, writer, context);
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
    Collection methods = cls.getOverwrittenClassMethods();

    if(methods.size() > 0) {
      generateExceptionSets(true);

      for(Iterator mit = methods.iterator(); mit.hasNext();){
        Method method = (Method)mit.next();
        generateMethodDispatch( method, "super method", true, true);
        d_writer.println();
        if(method.hasRarray()) {
          generateMethodDispatch( method, "super method (with rarray!)",true, false);
          d_writer.println();
        }
      }
    }
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
    if ( d_ext.isInterface() ) { 
      d_self = "loc_self->d_object";
    } else { 
      d_self = "loc_self";
    }

    try { 
      d_writer = Cxx.createSource( d_ext, Cxx.FILE_ROLE_STUB, "STUBSRCS",
                                   d_context );
      d_writer.println();

      writeIncludes();

      if (!d_context.getConfig().getSkipRMI()) {
        writeStructDefns(true);
        writeStructDefns(false);
      }

      if (!d_context.getConfig().getSkipRMI()) {
        //No Language specific RMI initialization for C++
        d_writer.printlnUnformatted("#define "+RMI.LangSpecificInit());
      }

      if (!d_context.getConfig().getSkipRMI()) {
        d_writer.println("extern \"C\" {");
        d_writer.tab();
        RMIStubSource.generateCode(d_ext,d_writer, d_context);
        d_writer.backTab();
        d_writer.println("}");
      }

      generateExceptionSets(false);

      if(d_context.getConfig().getFastCall())
        Cxx.generateFastCallStaticDeclarations(d_writer, d_context, d_ext);

      final String full_name =
        Cxx.getSymbolNameWithoutLeadingColons(d_ext.getSymbolID(),"");
      d_writer.println();
      d_writer.println(full_name + "::sepv_t *" + full_name + "::_sepv;");
      d_writer.println();
      
      writeUserDefinedMethods();

      writeConstructors();

      writeCastingOperators();

      if ( !d_ext.isInterface()) {
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
                                                    Cxx.FILE_TYPE_CXX_HEADER,
                                                    d_context),
                              true);
    d_writer.generateInclude( Cxx.generateFilename( "sidl.BaseInterface",
                                                    Cxx.FILE_ROLE_STUB, 
                                                    Cxx.FILE_TYPE_CXX_HEADER),
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
      d_writer.generateInclude( Cxx.generateFilename( "sidl.LangSpecificException",
                                                      Cxx.FILE_ROLE_STUB, 
                                                      Cxx.FILE_TYPE_CXX_HEADER), 
                                true );
    }
    d_writer.generateInclude( Cxx.generateFilename( "sidl.RuntimeException",
                                                    Cxx.FILE_ROLE_STUB, 
                                                    Cxx.FILE_TYPE_CXX_HEADER), 
                              true );

    d_writer.generateInclude( Cxx.generateFilename( "sidl.CastException",
                                                    Cxx.FILE_ROLE_STUB, 
                                                    Cxx.FILE_TYPE_CXX_HEADER), 
                              true );
    
    if (!d_context.getConfig().getSkipRMI()) {
      d_writer.generateInclude(  Cxx.generateFilename( "sidl.rmi.Call",
                                                       Cxx.FILE_ROLE_STUB, 
                                                       Cxx.FILE_TYPE_CXX_HEADER
                                                       ),
                                 true );
      d_writer.generateInclude(  Cxx.generateFilename( "sidl.rmi.Return",
                                                       Cxx.FILE_ROLE_STUB, 
                                                       Cxx.FILE_TYPE_CXX_HEADER
                                                       ),
                                 true );
      d_writer.generateInclude(  Cxx.generateFilename( "sidl.rmi.Ticket",
                                                       Cxx.FILE_ROLE_STUB, 
                                                       Cxx.FILE_TYPE_CXX_HEADER
                                                       ),
                                 true );
      d_writer.generateInclude(  Cxx.generateFilename( "sidl.rmi.InstanceHandle",
                                                       Cxx.FILE_ROLE_STUB, 
                                                       Cxx.FILE_TYPE_CXX_HEADER
                                                       ),
                                 true );
    
      d_writer.generateInclude("sidl_rmi_ConnectRegistry.h", false);
    }
    d_writer.generateInclude( "sidl_String.h", false );
    if (d_ext.hasInvClause(true)) {
      d_writer.generateInclude(Cxx.generateFilename(
                                                    BabelConfiguration.getInvariantViolation(),
                                                    Cxx.FILE_ROLE_STUB, 
                                                    Cxx.FILE_TYPE_CXX_HEADER),
                               true );
    }
    if (d_ext.hasPreconditions()) {
      d_writer.generateInclude(Cxx.generateFilename( 
                                                    BabelConfiguration.getPostconditionViolation(),
                                                    Cxx.FILE_ROLE_STUB, 
                                                    Cxx.FILE_TYPE_CXX_HEADER),
                               true );
    }
    if (d_ext.hasPostconditions()) {
      d_writer.generateInclude(Cxx.generateFilename( 
                                                    BabelConfiguration.getPreconditionViolation(),
                                                    Cxx.FILE_ROLE_STUB, 
                                                    Cxx.FILE_TYPE_CXX_HEADER),
                               true );
    }
    if (!BabelConfiguration.isSIDLBaseClass(id)) {
      d_writer.generateInclude("babel_config.h", false);
      d_writer.printlnUnformatted("#ifdef SIDL_DYNAMIC_LIBRARY");
      d_writer.printlnUnformatted("#include <stdio.h>");
      d_writer.printlnUnformatted("#include <stdlib.h>");
      d_writer.generateInclude("sidl_Loader.hxx", false);
      d_writer.generateInclude("sidl_DLL.hxx", false);

      d_writer.printlnUnformatted("#endif");
    }

    if(d_context.getConfig().getFastCall()) {
      d_writer.printlnUnformatted("#include <assert.h>");
      
      Cxx.generateFastCallTypes(d_writer,
                                d_ext,
                                d_context,
                                false);
    }
    
    //Generate method dependency includes
    Cxx.generateSourceIncludes(d_writer, d_ext, d_context);

    d_writer.println();
  } 

  private void writeStructDefns(boolean serialize) 
    throws CodeGenerationException
  {
    final String method = (serialize ? "serialize" : "deserialize");
    final String writable = (serialize ? "const " : "");
    final String pipetype = (serialize ? "::sidl::rmi::Invocation" :
                             "::sidl::rmi::Response");
    Set SIDs = IOR.getStructSymbolIDs(d_ext, serialize);
    Iterator i = SIDs.iterator();
    while (i.hasNext()) {
      SymbolID id = (SymbolID)i.next();
      final String name = IOR.getSymbolName(id);
      d_writer.printlnUnformatted("#define RMI_" +
                                  name + "_" + method +
                                  "(strct, pipe, name, copyArg, exc) { \\");
      d_writer.printlnUnformatted("  try { \\");
      d_writer.printlnUnformatted("    " + pipetype + " _s_inv(pipe); \\");
      d_writer.printlnUnformatted("    " + writable +
                                  Cxx.getSymbolName(id) + 
                                  " *_strct = reinterpret_cast< " + 
                                  writable +
                                  Cxx.getSymbolName(id) + " * >(strct); \\");
      d_writer.printlnUnformatted("    _strct->" +
                                  method + "(_s_inv, ::std::string(name), (copyArg)); \\");
      d_writer.printlnUnformatted("  } \\");
      d_writer.printlnUnformatted("  catch (::sidl::BaseException &_be) { \\");
      d_writer.printlnUnformatted("    ::sidl::BaseInterface &_bi(_be); \\");
      d_writer.printlnUnformatted("    _bi.addRef(); \\");
      d_writer.printlnUnformatted("    *(exc) = _bi._get_ior(); \\");
      d_writer.printlnUnformatted("  } \\");
      d_writer.printlnUnformatted("}");
      d_writer.println();
    }
  }

  private void writeConstructors() throws CodeGenerationException { 
    SymbolID id = d_ext.getSymbolID();
    final String fullName = Cxx.getObjectName(id);
    final String fullNameWithoutLeadingColons = 
      Cxx.getSymbolNameWithoutLeadingColons(id,"");
    final String name = id.getShortName();
    final String ior_name = IOR.getSymbolName(id);

    //A little cheat, all of these functions are constructors, so they all
    //throw the same set of exceptions.  Just use the constructor's set.

    List initializers = Cxx.getFastCallStubInitializers(d_context, d_ext);
    Method ctor = IOR.getBuiltinMethod(IOR.CONSTRUCTOR, d_ext.getSymbolID(), d_context, false);
    Map sets_to_nums = Cxx.getExceptionSets(d_ext.getMethods(true));
    Integer num = (Integer)sets_to_nums.get(ctor.getThrows());
    int index = num.intValue();

    if( !d_ext.isAbstract() ) { 
      d_writer.writeCommentLine("static constructor");
      d_writer.println(fullName);
      d_writer.println(fullNameWithoutLeadingColons + "::_create() {");
      d_writer.tab();

      d_writer.println(IOR.getExceptionFundamentalType() 
                       + " _exception = NULL;");
      d_writer.println( Cxx.getObjectName(id) 
                        + " self( (*_get_ext()->createObject)(NULL, &_exception), false );");
      d_writer.println("if (_exception != NULL) {");
      d_writer.tab();
      d_writer.println("throwException" +index+"(\""+fullName+"\"");
      d_writer.tab();
      d_writer.println("\"static constructor\", _exception);");
      d_writer.backTab();
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println("return self;");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();

      if(!IOR.isSIDLSymbol(id)) {
        d_writer.writeCommentLine("Internal data wrapping method");
        d_writer.println(fullName+"::ior_t*");
        d_writer.println(fullNameWithoutLeadingColons + 
                         "::_wrapObj(void* private_data) {");
        d_writer.tab();
        d_writer.println(IOR.getExceptionFundamentalType() 
                         + "_exception = NULL;");
        d_writer.print(fullName+"::ior_t* returnValue = ");
        d_writer.println("(*_get_ext()->createObject)"
                         + "(private_data ,&_exception);");
     
        d_writer.println("if (_exception) {");
        d_writer.tab();
        d_writer.println("throwException" + index + "(\"" + fullName 
                         + "._wrap\", _exception);");
        d_writer.backTab();
        d_writer.println("}");
        d_writer.println("return returnValue;"); 
        d_writer.backTab();
        d_writer.println("}"); 
        d_writer.println();
      }

      if (!d_context.getConfig().getSkipRMI()) {
        d_writer.printlnUnformatted("#ifdef WITH_RMI");
        d_writer.writeCommentLine("remote constructor");
        d_writer.println(fullName);
        d_writer.println(fullNameWithoutLeadingColons + "::_create(" 
                         + "const std::string& url) {");
        d_writer.tab();
        
        d_writer.println( "ior_t* ior_self;");
        d_writer.println(IOR.getExceptionFundamentalType() 
                         + "_exception = NULL;");
        d_writer.println( "ior_self = " + ior_name 
                          + "__remoteCreate( url.c_str(), &_exception );" );
        d_writer.println( "if (_exception != NULL ) {");
        d_writer.tab();
        try {
          d_writer.pushLineBreak(false);
          d_writer.println("throwException" +index+"(\""+fullName+" remoteCreate\", _exception);");
        }
        finally {
          d_writer.popLineBreak();
        }
        d_writer.backTab();
        d_writer.println("}");
        d_writer.println( "return " + fullName + "( ior_self, false );" );
        d_writer.backTab();
        d_writer.println("}");
        d_writer.println();
        d_writer.printlnUnformatted("#endif /* WITH_RMI */");
      }
    }

    if (!d_context.getConfig().getSkipRMI()) {
      d_writer.printlnUnformatted("#ifdef WITH_RMI");
      d_writer.writeCommentLine("remote connector");
      d_writer.println(fullName);
      d_writer.println(fullNameWithoutLeadingColons + "::_connect(" 
                       + "const std::string& url, const bool ar ) {");
      d_writer.tab();
      d_writer.println( "ior_t* ior_self;");
      d_writer.println(IOR.getExceptionFundamentalType() + "_exception = NULL;");
      
      d_writer.println( "ior_self = " + ior_name 
                        + "__remoteConnect( url.c_str(), ar?TRUE:FALSE, &_exception );" );
      d_writer.println( "if (_exception != NULL ) {");
      d_writer.tab();
      try {
        d_writer.pushLineBreak(false);
        d_writer.println("throwException" +index+"(\""+fullName+" connect\",_exception);");
      }
      finally {
        d_writer.popLineBreak();
      }
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println( "return " + fullName + "( ior_self, false );" );
      d_writer.backTab();
      d_writer.println("}");
      d_writer.printlnUnformatted("#endif /* WITH_RMI */");
      d_writer.println();
    }
      
    d_writer.writeCommentLine("copy constructor");
    d_writer.print( fullNameWithoutLeadingColons + "::" + name 
                    + " ( const " + fullName + "& original )");
    
    if(initializers.size() > 0) {
      d_writer.println();
      d_writer.printlnUnformatted("#ifndef SIDL_FASTCALL_DISABLE_CACHING");
      d_writer.println(" : ");
      d_writer.tab();
      for(Iterator i = initializers.iterator(); i.hasNext(); ) {
        String s = (String) i.next();
        d_writer.println(s + (i.hasNext() ? ", " : ""));
      }
      d_writer.backTab();
      d_writer.printlnUnformatted("#endif");
    }
    d_writer.println(" {");
    
    d_writer.tab();
    //Cxx.getObjectName(id)+"::_cast(original._get_ior());");
    d_writer.println("_set_ior("+ Cxx.getIORCall("original", d_ext.getSymbolID())+");"); 
    //    d_writer.println("d_self = "+ Cxx.getIORCall("original", d_ext.getSymbolID())+";"); 
    //      if(d_ext.isInterface()) {
    //      Cxx.initializeLocalIOR(d_writer, d_ext, true);
    //      } 
    
    d_writer.println("if(d_self) {");
    d_writer.tab();
    //    if(!d_ext.isInterface()) {
    //writeInterfaceCacheInitialization((Class)d_ext);
    //}

    d_writer.println("addRef();");
    d_writer.backTab();
    d_writer.println("}");

    d_writer.println("d_weak_reference = false;");
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
    d_writer.println("_set_ior("+Cxx.getIORCall("rhs", d_ext.getSymbolID())+");");
    //   d_writer.println("d_self = "+Cxx.getIORCall("rhs", d_ext.getSymbolID())+";"); 
    //Cxx.getObjectName(id)+"::_cast(rhs._get_ior());"); 
    //d_writer.writeCommentLine("note _cast incremements the reference count");
    //if(d_ext.isInterface()) {
    //  Cxx.initializeLocalIOR(d_writer, d_ext, true);
    //} 

    d_writer.println("if(d_self) {");
    d_writer.tab();
    /*   if(!d_ext.isInterface()) {
         writeInterfaceCacheInitialization((Class)d_ext);
         }*/
    d_writer.println("addRef();");
    d_writer.backTab();
    d_writer.println("}");
    
    d_writer.println("d_weak_reference = false;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println("return *this;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  private void writeCastingOperators() throws CodeGenerationException { 
    SymbolID id = d_ext.getSymbolID();
    final String fullName = Cxx.getObjectName(id);
    final String fullNameWithoutLeadingColons = 
      Cxx.getSymbolNameWithoutLeadingColons(id,"");
    final String name = id.getShortName();   
    List initializers = Cxx.getFastCallStubInitializers(d_context, d_ext);
    
    d_writer.writeCommentLine("conversion from ior to C++ class");
    d_writer.println( fullNameWithoutLeadingColons + "::" + name 
                      + " ( " + fullName + "::ior_t* ior ) : ");
    d_writer.tab();
    d_writer.print("StubBase("+Cxx.reinterpretCast("void*","ior") + ")");
    if(d_ext.isInterface() && 
       !BabelConfiguration.getBaseInterface().equals(d_ext.getSymbolID().getFullName())) {
      d_writer.println(", ");
      Cxx.initializeLocalIOR(d_writer, (Interface)d_ext, "ior", true);
    } else if(d_ext.hasParentInterfaces()) {
      d_writer.println(", ");
      Cxx.writeCallsToParentInterfaceConstructors(d_writer, (Class)d_ext, "ior");
    }
    d_writer.println();
    d_writer.printlnUnformatted("#ifndef SIDL_FASTCALL_DISABLE_CACHING");
    for(Iterator i = initializers.iterator(); i.hasNext(); ) {
      String s = (String) i.next();
      d_writer.println(", " + s);
    }
    d_writer.printlnUnformatted("#endif");
    d_writer.backTab();

    if(d_context.getConfig().getFastCall()) {
      d_writer.println("{");
      d_writer.tab();
      d_writer.printlnUnformatted("#ifdef SIDL_FASTCALL_EAGER_CACHING");
      d_writer.println("_cache_entry_points();");
      d_writer.printlnUnformatted("#endif");
      d_writer.backTab();
      d_writer.println("}");
    } else {
      d_writer.println(" { }");
    }
    
    d_writer.println();

    d_writer.writeCommentLine("Alternate constructor: does not call addRef()");
    d_writer.writeCommentLine("(sets d_weak_reference=isWeak)");
    d_writer.writeCommentLine("For internal use by Impls (fixes bug#275)");
    d_writer.println( fullNameWithoutLeadingColons + "::" + name +" ( " 
                      + fullName + "::ior_t* ior, bool isWeak ) : ");

    d_writer.tab();
    d_writer.print("StubBase(" + Cxx.reinterpretCast("void*","ior") + ", isWeak)");
    if(d_ext.isInterface() && 
       !BabelConfiguration.getBaseInterface().equals(d_ext.getSymbolID().getFullName())) {
      d_writer.println(", ");
      Cxx.initializeLocalIOR(d_writer, (Interface)d_ext, "ior", true);
      d_writer.backTab();
    }else if(d_ext.hasParentInterfaces()) {
      d_writer.println(", ");
      Cxx.writeCallsToParentInterfaceConstructors(d_writer, (Class)d_ext, "ior");
    }
    d_writer.println();
    d_writer.printlnUnformatted("#ifndef SIDL_FASTCALL_DISABLE_CACHING");
    d_writer.tab();
    for(Iterator i = initializers.iterator(); i.hasNext(); ) {
      String s = (String) i.next();
      d_writer.println(", " + s);
    }
    d_writer.backTab();
    d_writer.printlnUnformatted("#endif");
    
    d_writer.backTab();
    if(d_context.getConfig().getFastCall()) {
      d_writer.println("{");
      d_writer.tab();
      d_writer.printlnUnformatted("#ifdef SIDL_FASTCALL_EAGER_CACHING");
      d_writer.println("_cache_entry_points();");
      d_writer.printlnUnformatted("#endif");
      d_writer.backTab();
      d_writer.println("}");
    } else {
      d_writer.println(" { }");
    }
    d_writer.println();      

    d_writer.writeCommentLine("This safe IOR cast addresses Roundup issue475");
    d_writer.println("int " + fullName + "::_set_ior_typesafe( struct sidl_BaseInterface__object *obj,");
    d_writer.println("                                         const ::std::type_info &argtype) { ");
    d_writer.tab();
    d_writer.println("if ( obj == NULL || argtype == typeid(*this) ) {");
    d_writer.tab();
    d_writer.writeCommentLine("optimized case:  _set_ior() is sufficient");
    d_writer.println("_set_ior( reinterpret_cast<ior_t*>(obj) );");
    d_writer.println("return 0;");
    d_writer.backTab();
    d_writer.println("} else {");
    d_writer.tab();
    d_writer.writeCommentLine("Attempt to downcast ior pointer to matching stub type");
    d_writer.println( "ior_t* _my_ptr = NULL;");
    d_writer.println( "if ((_my_ptr = _cast( obj )) == NULL ) {");
    d_writer.tab();
    d_writer.println("return 1;");
    d_writer.backTab();
    d_writer.println("} else {");
    d_writer.tab();
    d_writer.println("_set_ior(_my_ptr);");
    d_writer.println("struct sidl_BaseInterface__object* _throwaway=NULL;");
    d_writer.println("sidl_BaseInterface_deleteRef(obj,&_throwaway);");
    d_writer.println("return 0;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();

    if (!d_context.getConfig().getSkipRMI()) {
      d_writer.writeCommentLine("exec has special argument passing to avoid #include circularities");
      d_writer.println("void " + fullName + "::_exec( const std::string& methodName, ");
      d_writer.println("                        sidl::rmi::Call& inArgs,");
      d_writer.println("                        sidl::rmi::Return& outArgs) { ");
      
      d_writer.tab();
      d_writer.println(fullName + "::ior_t* const loc_self = _get_ior();");     
      // + Cxx.reinterpretCast("ior_t*","this->d_self") + ";");
      d_writer.println(IOR.getExceptionFundamentalType() + "throwaway_exception;");
      d_writer.println("(*loc_self->d_epv->f__exec)(" + d_self + ",");
      d_writer.println("                              methodName.c_str(),");
      d_writer.println("                              inArgs._get_ior(),");
      d_writer.println("                              outArgs._get_ior(),");
      d_writer.println("                              &throwaway_exception);");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();

      generateMethodDispatch(IOR.getBuiltinMethod(IOR.GETURL, 
                                                  d_ext.getSymbolID(), d_context, false),
                             "get URL of the implementation of this object", 
                             false, false);
    }

    boolean hasStatic = d_ext.hasStaticMethod(true);

    /* Generate built-in hook methods. */
    /* Instance version of enable/disable hooks. */
    generateMethodDispatch(
                           IOR.getBuiltinMethod(IOR.HOOKS, id, d_context, false),
                           "Method to enable/disable hooks execution.", false, false);
    if (hasStatic) {
      /* Static version of enable/disable hooks. */
      generateMethodDispatch(
                             IOR.getBuiltinMethod(IOR.HOOKS, id, d_context, true),
                             "Method to enable/disable static hooks execution.",
                             false, false);
    }
    

    /* Generate built-in contract methods. */
    if (IOR.generateContractBuiltins(d_ext, d_context)) {
      /* Instance version of enable/disable. */
      generateMethodDispatch(
                             IOR.getBuiltinMethod(IOR.CONTRACTS, id, d_context, false),
                             "Method to enable/disable interface contract enforcement.",
                             false, false);
      if (hasStatic) {
        /* Static version of enable/disable. */
        generateMethodDispatch(
                               IOR.getBuiltinMethod(IOR.CONTRACTS, id, d_context, true),
                               "Method to enable/disable static interface contract enforcement.",
                               false, false);
      }
  
      /* Instance version of stats dump. */
      generateMethodDispatch(
                             IOR.getBuiltinMethod(IOR.DUMP_STATS, id, d_context, false),
                             "Method to dump interface contract enforcement statistics.",
                             false, false);
      if (hasStatic) {
        /* Static version of stats dump. */
        generateMethodDispatch(
                               IOR.getBuiltinMethod(IOR.DUMP_STATS, id, d_context, true),
                               "Method to dump static interface contract enforcement statistics.",
                               false, false);
      }
    }
    

    d_writer.writeCommentLine("protected method that implements casting");
    d_writer.println(IOR.getObjectName(id)+ "* " + fullNameWithoutLeadingColons 
                     + "::_cast(const void* src)"); 
    d_writer.println("{");

    d_writer.tab();
    d_writer.println("ior_t* cast = NULL;"); 
    if (!d_context.getConfig().getSkipRMI()) {
      d_writer.printlnUnformatted("#ifdef WITH_RMI");
      d_writer.println("static int connect_loaded = 0;");

 
      d_writer.println();
      d_writer.println("if(!connect_loaded) {");
      d_writer.tab();
      d_writer.println(IOR.getExceptionFundamentalType() + "throwaway_exception;");
      
      d_writer.println("sidl_rmi_ConnectRegistry_registerConnect(\""+id.getFullName()+
                       "\", (void*)"+IOR.getSymbolName(id)+"__IHConnect, &throwaway_exception);");
      d_writer.println("connect_loaded = 1;");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.printlnUnformatted("#endif /* WITH_RMI */");
    }

    d_writer.println("if ( src != 0 ) {");
    d_writer.tab();
    d_writer.writeCommentLine("Actually, this thing is still const");
    d_writer.println("void* tmp = const_cast<void*>(src);"); 
    d_writer.println(IOR.getExceptionFundamentalType() + "throwaway_exception;");
    d_writer.println(IOR.getExceptionFundamentalType() + " base = " +  
                     Cxx.reinterpretCast(IOR.getExceptionFundamentalType(), "tmp") + ";");
    d_writer.print("cast = "); 
    d_writer.print(Cxx.reinterpretCast( "ior_t*",
                                        "(*base->d_epv->f__cast)(base->d_object, \""+
                                        id.getFullName()+"\", &throwaway_exception)"));
    
    d_writer.println(";");
    d_writer.backTab();
    d_writer.println("}");    
    d_writer.println("return cast;");//+fullNameWithoutLeadingColons+"(cast);");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
    

    /*
      String ior_t = IOR.getObjectName(id);

      d_writer.writeCommentLine("Internal remote connect (Other stubs need access)");
      d_writer.println(IOR.getObjectName(id)+ "* " + fullNameWithoutLeadingColons 
      + "::_connectI( const char* url, sidl_bool ar,"
      + " sidl_BaseInterface__object**_ex) {"); 
      d_writer.tab();
      d_writer.println("return " + ior_name + "__remoteConnect( url, ar, _ex);");  
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();
    */

  }

  private void writeDynamicImplStuff() throws CodeGenerationException { 
    SymbolID id = d_ext.getSymbolID();
    final String extName = IOR.getExternalName(id);
    final String fullName = Cxx.getObjectName(id);
    final String fullNameWithoutLeadingColons = 
      Cxx.getSymbolNameWithoutLeadingColons(id,"");
    d_writer.writeCommentLine("Static data type");
    d_writer.println("const " + fullName + "::ext_t * " 
                     + fullNameWithoutLeadingColons + "::s_ext = 0;");
    d_writer.println();

    d_writer.writeCommentLine("private static method to get static data type");
    d_writer.println("const " + fullName + "::ext_t *");
    d_writer.println( fullNameWithoutLeadingColons + "::_get_ext()");
    d_writer.println("  throw ( " + Cxx.prependGlobalUCxx() 
                     + "::sidl::NullIORException)");
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
      d_writer.println("sidl_checkIORVersion(\"" + id.getFullName() + 
                       "\", s_ext->d_ior_major_version, s_ext->d_ior_minor_version, " +
                       Integer.toString(IOR.MAJOR_VERSION) + ", " +
                       Integer.toString(IOR.MINOR_VERSION) + ");");
    }
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println("return s_ext;");
    d_writer.backTab();
    d_writer.println("}");   
    d_writer.println();    
  }

  private void writeUserDefinedMethods() throws CodeGenerationException { 
    d_writer.beginBoldComment();
    d_writer.println("User Defined Methods");
    d_writer.endBoldComment();

    Iterator m = null;
    m = Cxx.getStubMethodList(d_ext).iterator();
    while (m.hasNext()) {
      final Method method = (Method) m.next();
      final boolean isStatic = method.isStatic();
      final String desc = isStatic ? "user defined static method" :
        "user defined non-static method.";

      if(!Cxx.inlineStub(method)) {
        generateMethodDispatch( method, desc, false, true);
        d_writer.println();
        if ((!isStatic) && 
            (method.getCommunicationModifier() == Method.NONBLOCKING) &&
            !d_context.getConfig().getSkipRMI()) {
          Method send = method.spawnNonblockingSend();
          generateMethodDispatch(send, desc, false, true);
          d_writer.println();
          Method recv = method.spawnNonblockingRecv();
          generateMethodDispatch(recv, desc, false, true);
          d_writer.println();
        }
        if(method.hasRarray()) {
          generateMethodDispatch( method, desc, false, false);
          d_writer.println();
          if ((!isStatic) && 
              (method.getCommunicationModifier() == Method.NONBLOCKING) &&
              !d_context.getConfig().getSkipRMI()) {
            Method send = method.spawnNonblockingSend();
            generateMethodDispatch(send, desc, false, false);
            d_writer.println();
            //Method recv = method.spawnNonblockingRecv();
            //generateMethodDispatch(recv, desc, false, false);
            //d_writer.println();
          }
        }
        
        /* in fast-call mode, emit a small helper function hat replaces
         * the standard stub allowing for faster native function calls.
         */
        if(d_context.getConfig().getFastCall()) {
          Cxx.generateFastCallDispatchImpl(d_writer, d_context, d_ext, method);
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
  private void generateMethodDispatch( Method m, String altcomment, 
                                       boolean isSuper, boolean rarrays) 
    throws CodeGenerationException 
  { 
    if ( m == null ) { return; }

    String shortMethodName = m.getShortMethodName();
    String className = d_ext.getSymbolID().getShortName();
    Comment comment = m.getComment();
    Type return_type = m.getReturnType();

    d_writer.writeComment( comment, altcomment );
    if ( shortMethodName.equals(className) ) { 
      shortMethodName = "f_" + shortMethodName;
      System.out.println("WARNING: gov.llnl.babel.backend.UCxx.CxxStubSource: "
                         + "sidl / C++ conflict!");
      System.out.println("         methodName == className is not allowed in "
                         + "C++");
      System.out.println("         (this is restricted to constructors in C++"
                         + ")");
      System.out.println("         changing to " + className + "::" 
                         + shortMethodName + "()");
    }

    //Output function deleration
    boolean is_builtin = (!m.isStatic() && IOR.isBuiltinMethod(m.getLongMethodName())) || 
      (m.isStatic() && IOR.isBuiltinMethod(m.getLongMethodName(), true));
      
    String suffix = "";
    if(!isSuper && !is_builtin &&
       d_context.getConfig().getFastCall() && Cxx.supportsFastCalls(m)) {
      suffix = "_default_stub";
    }
    d_writer.println(Cxx.generateFunctionDeclaration(m, d_ext, d_context,
                                                     d_self, 
                                                     isSuper, rarrays, suffix));

    d_writer.println();
    d_writer.println("{");

    d_writer.tab();
    if ( (! m.isStatic()) &&  d_context.getConfig().makeCxxCheckNullIOR()) { 

      d_writer.println("if ( d_self == 0 ) {");
      d_writer.tab();
      d_writer.println("throw " + Cxx.prependGlobalUCxx() 
                       + "::sidl::NullIORException( ::std::string (");
      d_writer.tab();
      try {
        d_writer.pushLineBreak(false);
        d_writer.println( "\"" + "Null IOR Pointer in \\\"" 
                          + Cxx.getMethodStubName(d_ext.getSymbolID(),shortMethodName, isSuper) 
                          + "()\\\"\"");
      }
      finally {
        d_writer.popLineBreak();
      }
      d_writer.backTab();
      d_writer.println("));");
      d_writer.backTab();
      d_writer.println("}");
    }

    //Output almost nothing!
    d_writer.println(Cxx.generateInitialization(m, d_ext, d_self, d_context));

    if ( ! m.isStatic() ) { 
      if (shortMethodName.equals("addRef") ||
          shortMethodName.equals("deleteRef") ) { 
        d_writer.println("if ( !d_weak_reference ) {");
        d_writer.tab();
      }
    }

    //Prepare for IOR call!
    d_writer.println(Cxx.generatePreIORCall(m, d_ext, d_self, isSuper, rarrays, d_context));


    if(isSuper) {
      Class cls = (Class) d_ext;
      String tmp_self = Cxx.reinterpretCast(IOR.getObjectName(cls.getParentClass().getSymbolID())
                                            + "*", "superSelf");
      d_writer.println(Cxx.generateIORCall(m, d_ext, d_context, tmp_self, isSuper, rarrays));
    } else {
      d_writer.println(Cxx.generateIORCall(m, d_ext, d_context, d_self, isSuper, rarrays));
    }

    if (m.getThrows().size()>0) { // if throws exception (and they all do now)
      Map sets_to_nums = null;
      String methodName = null;
      if(isSuper) {
        Class cls = (Class) d_ext;
        sets_to_nums = Cxx.getExceptionSets(cls.getOverwrittenClassMethods());
        methodName = "Super::"+m.getLongMethodName();
      } else {
        sets_to_nums = Cxx.getExceptionSets(d_ext.getMethods(true));        
        methodName = m.getLongMethodName();
      }
      Integer num = (Integer)sets_to_nums.get(m.getThrows());
      int index = num.intValue();
      d_writer.println("if (_exception != NULL ) {");      
      d_writer.tab();
      d_writer.println(Cxx.generatePostIORCleanup(m, rarrays));
      
      d_writer.println("throwException" +index+"(\""+methodName+
                       "\", _exception);");
      d_writer.backTab();
      d_writer.println("}");
    }

    //Clean up from return from IOR
    d_writer.println(Cxx.generatePostIORCall(m, d_ext, d_self, isSuper, rarrays));

    //d_writer.println( post_ior.toString().trim() );
    if ( ! m.isStatic() ) { 
      if ( shortMethodName.equals("addRef") 
           || shortMethodName.equals("deleteRef") ) { 
        if ( shortMethodName.equals("deleteRef") ) { 
          //d_writer.println("d_self = 0;");
          d_writer.println("_set_ior(0);");
        }
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

  /** 
   *  Function generates exception set throwing methods. 
   */
  private void generateExceptionSets(boolean isSuper)  throws CodeGenerationException 
  { 
    Map sets_to_nums = null;
    if(isSuper) {
      Class cls = (Class) d_ext;
      sets_to_nums = Cxx.getExceptionSets(cls.getOverwrittenClassMethods());
    } else {
      sets_to_nums = Cxx.getExceptionSets(d_ext.getMethods(true));        
    }
    Set exp_sets = sets_to_nums.keySet();
    d_writer.beginBoldComment();
    d_writer.println("Special methods for throwing exceptions");
    d_writer.endBoldComment();

    //For each set of exceptions
    for(Iterator i = exp_sets.iterator(); i.hasNext();) {
      Set exp_set = (Set) i.next();
      Integer num = (Integer)sets_to_nums.get(exp_set);
      
      Cxx.generateExceptionSetSignature(d_writer, d_ext.getSymbolID(), exp_set, num.intValue(), true, isSuper);
      d_writer.println("{");
      d_writer.tab();
      Cxx.generateExceptionSetBody(d_writer, exp_set, d_context);
      d_writer.backTab();
      d_writer.println("}");
    }
  }


}
