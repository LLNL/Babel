//
// File:        CxxSkelSource.java
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

import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.Utilities;
import gov.llnl.babel.backend.c.C;
import gov.llnl.babel.backend.cxx.Cxx;
import gov.llnl.babel.backend.writers.LanguageWriterForCxx;
import gov.llnl.babel.symbols.Argument;
import gov.llnl.babel.symbols.AssertionExpression;
import gov.llnl.babel.symbols.CExprString;
import gov.llnl.babel.symbols.Class;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Inverter;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Create and write a header for a Cxx C extension class to wrap a 
 * BABEL extendable in a Cxx object. The header has to expose a 
 * function to create a wrapped IOR, a function to check if a 
 * <code>PyObject</code> is an instance of this extension type, and
 * an import macro.
 */
public class CxxSkelSource {

  private static final String s_ensureOrderConstant[] = {
    "sidl::general_order",
    "sidl::column_major_order",
    "sidl::row_major_order"
  };

  private Extendable d_ext = null;
  private LanguageWriterForCxx d_writer = null;

  /**
   * Create an object capable of generating the header file for a
   * BABEL extendable.
   *
   * @param ext   an interface or class symbol that needs a header
   *              file for a Cxx C extension class.
   */
  public CxxSkelSource(Extendable ext) {
    d_ext = ext;
  }
  
  /**
   * Generate the header file for the extendable with which this object was
   * created.
   *
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception for problems during the code
   *    generation phase.
   */
  public synchronized void generateCode() 
    throws CodeGenerationException 
  {
    try { 
      d_writer = Cxx.createSource( d_ext, Cxx.FILE_ROLE_SKEL, "SKELSRCS" );
      SymbolID id = d_ext.getSymbolID();
      //writeBanner(cls, writer);
      d_writer.generateInclude( Cxx.generateFilename(d_ext.getSymbolID(), 
                                                     Cxx.FILE_ROLE_IMPL,
                                                     Cxx.FILE_TYPE_CXX_HEADER),
                                true );
      d_writer.generateInclude(IOR.getHeaderFile(id), true);
      if( d_ext.hasExceptionThrowingMethod(true) ) { 
        d_writer.generateInclude( Cxx.generateFilename("sidl.BaseException",
                                  Cxx.FILE_ROLE_STUB, 
                                  Cxx.FILE_TYPE_CXX_HEADER), 
                                  true );
      }
      // NOTE: the following is not a binding to a sidl type
      d_writer.generateInclude( "sidl_String.h", false );

      d_writer.printlnUnformatted("#include <stddef.h>");
      d_writer.println();
      Cxx.beginExternCRegion( d_writer );
      writeSkelFunctions();
      writeRMIAccessFunctions();
      writeLoad();
      writeConstructor();
      writeDestructor();
      if (IOR.supportAssertions(d_ext)) {
        if (d_ext.hasStaticMethod(true)) {
          writeCheckError(true);
        }
        writeCheckError(false);
      }
      writeInitializeEPV();
      if (d_ext.hasStaticMethod(true)) {
        writeInitializeSEPV();
      }
      Cxx.endExternCRegion( d_writer );
      //    } catch ( java.io.IOException ex) { 
      //      throw new CodeGenerationException("IOException : " + ex.getMessage() );
    } finally { 
      if (d_writer != null) {
        d_writer.close();
        d_writer = null;
      }
    }
  }

  /**
   * Write a function to initialize entries in the entry point vector for
   * a particular class. This will generate an assignment statement for
   * each non-<code>static</code> method defined locally in the class.
   * 
   */
  private void writeInitializeEPV() throws CodeGenerationException
  {
    SymbolID id = d_ext.getSymbolID();
    d_writer.println("void");
    d_writer.print(IOR.getSetEPVName(id));
    d_writer.print("(");
    d_writer.print(IOR.getEPVName(id));
    d_writer.println(" *epv) {");
    d_writer.tab();
    d_writer.writeCommentLine("initialize builtin methods");
    initializeMethodPointer(IOR.getBuiltinMethod(IOR.CONSTRUCTOR, id), id); 
    initializeMethodPointer(IOR.getBuiltinMethod(IOR.DESTRUCTOR, id), id); 
    if (IOR.supportAssertions(d_ext)) {
      initializeMethodPointer(IOR.getBuiltinMethod(IOR.CHECK_ERROR, id), id); 
    }
    d_writer.writeCommentLine("initialize local methods");
    Iterator i = d_ext.getMethods(false).iterator();
    while (i.hasNext()) {
      Method m = (Method)i.next();
      if ( !m.isAbstract() ) { 
          initializeMethodPointer( m, id);
      }
    }
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  /**
   * Write a function to initialize entries in the static entry point vector
   * for a particular class. This will generate an assignment statement for
   * each <code>static</code> method defined locally in the class.
   * 
   */
  private void writeInitializeSEPV() throws CodeGenerationException { 
    final String epv = "sepv";
    SymbolID id = d_ext.getSymbolID();
    d_writer.println("void");
    d_writer.print(IOR.getSetSEPVName(id) + "(" + IOR.getSEPVName(id));
    d_writer.println(" *" + epv + ") {");
    d_writer.tab();
    if (IOR.supportAssertions(d_ext)) {
      writeMethodAssignment(epv,
        IOR.getVectorEntry(IOR.getBuiltinName(IOR.CHECK_ERROR, true)),
        Cxx.getMethodSkelName(id, IOR.getBuiltinMethod(IOR.CHECK_ERROR, id, 
                                                       true)));
    }
    Iterator i = d_ext.getMethods(false).iterator();
    while (i.hasNext()) {
      Method m = (Method)i.next();
      if (m.isStatic()) {
        String name     = m.getLongMethodName();
        String skelName = Cxx.getMethodSkelName(id,name);
        if (  IOR.supportHooks(d_ext) 
           && (!IOR.isBuiltinMethod(name,true))) {
          writeMethodAssignment(epv,
                                IOR.getVectorEntry(name+IOR.GENERIC_PRE_SUFFIX),                                skelName + IOR.GENERIC_PRE_SUFFIX);
          writeMethodAssignment(epv, IOR.getVectorEntry(name), skelName);
          writeMethodAssignment(epv,
                               IOR.getVectorEntry(name+IOR.GENERIC_POST_SUFFIX),
                               skelName + IOR.GENERIC_POST_SUFFIX);
        } else {
          writeMethodAssignment(epv, IOR.getVectorEntry(name), skelName);
        }
      }
    }
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  /**
   * Write the function assignment for the specified var and method name
   * to the specified value.
   *
   * @param var        the pointer variable representing the EPV.
   * @param mname      the method name.
   * @param value      the desired value, or RHS.
   */
  private void writeMethodAssignment(String var, String mname, String value) {
    d_writer.println(var + "->" + mname + " = " + value + ";");
  }

  /**
   * For non-<code>static</code> methods, write an assignment statement to
   * initialize a particular membor of the entry point
   * vector. Initialization of <code>static</code> methods appears
   * elsewhere. 
   * 
   * @param m          a description of the method to initialize
   * @param id         the name of class that owns the method
   */
  private void initializeMethodPointer(Method m, SymbolID id) {
    final String methodName = m.getLongMethodName();
    switch (m.getDefinitionModifier()) {
    case Method.FINAL:
    case Method.NORMAL:
      d_writer.print("epv->");
      d_writer.print(IOR.getVectorEntry(methodName));
      d_writer.print(" = ");
      d_writer.print(Cxx.getMethodSkelName(id, methodName));
      d_writer.println(";");
      break;
    case Method.ABSTRACT:
      d_writer.print("epv->");
      d_writer.print(IOR.getVectorEntry(methodName));
      d_writer.println(" = NULL;");
      break;
    default:
      /* do nothing */
      break;
    }
  }

  private void writeConstructor() { 
    SymbolID id = d_ext.getSymbolID();
    d_writer.println("static void");
    d_writer.print( Cxx.getMethodSkelName(id,"_ctor") ); // Package_class
    d_writer.print("(");
    d_writer.print( IOR.getObjectName(id) ); 
    d_writer.println("* self, " + IOR.getExceptionFundamentalType() + "*_ex) { ");
    d_writer.tab();
    d_writer.print("self->d_data = ");
    d_writer.print(Cxx.reinterpretCast("void*","new " + 
                                       Cxx.getSymbolName(id,"impl") + 
                                       "(self)"));
    d_writer.println(";");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  private void writeLoad() { 
    SymbolID id = d_ext.getSymbolID();
    d_writer.println("void");
    d_writer.print( IOR.getSymbolName(id) + "__call_load" ); // Package_class
    d_writer.print("(void) {");
    d_writer.tab();
    d_writer.print(Cxx.getSymbolName(id,"impl") + "::_load();");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  private void writeDestructor() { 
    SymbolID id = d_ext.getSymbolID();
    d_writer.println("static void");
    d_writer.print( Cxx.getMethodSkelName(id,"_dtor") ); // Package_class
    d_writer.print("(");
    d_writer.print( IOR.getObjectName(id) ); 
    d_writer.println("* self, " + IOR.getExceptionFundamentalType() + "*_ex ) { ");
    d_writer.tab();
    d_writer.print("delete ( ");
    d_writer.print(Cxx.reinterpretCast(Cxx.getSymbolName(id,"impl") + "*", 
                                       "self->d_data"));
    d_writer.println(" );");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }
    
/******************************************************/
/* ToDo/TODO:  Need to verify this on static methods! */
/*             Also fix so NOT hard-coded!            */
/******************************************************/
  private void writeCheckError(boolean doStatic) 
    throws CodeGenerationException 
  { 
    SymbolID id = d_ext.getSymbolID();
    if (doStatic) {
      d_writer.print("static ");
    }
    d_writer.print("void ");
    d_writer.print( Cxx.getMethodSkelName(id,
                        IOR.getBuiltinMethod(IOR.CHECK_ERROR, id, doStatic)));
    d_writer.print("(");
    if (!doStatic) {
      d_writer.println();
      d_writer.print( IOR.getObjectName(id) ); 
      d_writer.print("* self, ");
    }
    d_writer.print("/* in */ const char* msg ");
    d_writer.println(") { ");
    d_writer.tab();
    d_writer.print( Cxx.getSymbolName( d_ext.getSymbolID(), "impl" ) );
    d_writer.print( "*_this = " );
    d_writer.print(Cxx.reinterpretCast(
      Cxx.getSymbolName(d_ext.getSymbolID(), "impl" ) + "*", "self->d_data") );
    d_writer.println( ";" );
    d_writer.println("::std::string _local_msg = ( msg ? msg: \"\" );");
    d_writer.println( " _this->" + IOR.getBuiltinName(IOR.CHECK_ERROR, doStatic)
      + " ( /* in */ _local_msg ); " );
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  /**
   * write skel functions that dispatch C IOR functionpointers
   * to C++ method calls
   */
  private void writeSkelFunctions() throws CodeGenerationException { 
    Iterator i = d_ext.getMethods(false).iterator();
    while (i.hasNext()) {
      Method m = (Method)i.next();
      if ( !m.isAbstract() ) { 
          writeImplDispatch( m );
      }
    }
  }

    /**
   * Write functions to call each RMI fconnect function
   *
   * @param cls    the class for which an routine will be written.
   * @param writer the output writer to which the routine will be written.
   */
  private void writeRMIAccessFunctions() 
    throws CodeGenerationException
  {
    Class cls = (Class) d_ext;
    SymbolID id = cls.getSymbolID();
    //TODO: When we RMI interfaces work, we should not exclude them! (edit getObjectDepe...)
    ArrayList dependencies = Utilities.sort(cls.getObjectDependencies());
    for (Iterator i = dependencies.iterator(); i.hasNext(); ) {
      SymbolID l_id = (SymbolID) i.next();
      d_writer.println(C.getSymbolObjectPtr(l_id) + " " + IOR.getSkelFConnectName(id,l_id)+ 
                     "(const char* url, sidl_bool ar, sidl_BaseInterface *_ex) { ");
      d_writer.tab();
      //TODO: Add this back in when we finish the C++ RMI stuff
      d_writer.println("return NULL;");
      //d_writer.println("return "+C.getImplFConnectName(id,ar,l_id)+"(url, _ex);");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();
      /*
      d_writer.println("char* " + IOR.getSkelFGetURLName(id,l_id)+ 
                     "("+C.getSymbolObjectPtr(l_id)+" obj) { ");
      d_writer.tab();
      d_writer.println("return NULL;");
      //TODO: Add this back in when we finish the C++ RMI stuff
      //d_writer.println("return "+C.getImplFGetURLName(id,l_id)+"(obj);");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();
      */
    }
  }

  /**
   * Create a wrapper function in the skeleton for a particular Impl method.
   * 
   * @param method    the description of the IOR method to be wrapped.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception. It can be caused by I/O trouble or
   *    violations of the data type invariants.
   */
  private void writeImplDispatch( Method m )
    throws CodeGenerationException 
  {
    final String nm = "CxxSkelSource.writeImplDispatch: ";
    final ArrayList vArgs = m.getArgumentList();
    final ArrayList callArgs = m.getArgumentListWithIndices();
    final int nargs = vArgs.size();
    final boolean throwsExceptions = (m.getThrows().size()>0);
    StringBuffer func_decl = new StringBuffer( nargs * 32 ); 
    // everything through first { 
    StringBuffer pre_impl = new StringBuffer( nargs * 128 );
    // everything before the impl call
    StringBuffer impl_call = new StringBuffer( nargs * 32 );
    // The actual call to the impl method
    StringBuffer impl_suffix = new StringBuffer( 16 );
    // might be "._get_ior()" iff return type is object
    StringBuffer post_impl = new StringBuffer( nargs * 128 );
    // after the impl returns
    String shortMethodName = m.getShortMethodName();
    String longMethodName = m.getLongMethodName();
    String className = d_ext.getSymbolID().getShortName();

    if ( shortMethodName.equals(className) ) { 
      shortMethodName = "f_" + shortMethodName;
      System.out.println("WARNING: gov.llnl.babel.backend.Cxx.CxxSkelSource: "
        + "sidl / C++ conflict!");
      System.out.println("         methodName == className is not allowed in "
        + "C++");
      System.out.println("         (this is restricted to constructors in "
        + "C++)");
      System.out.println("         changing to " + className + "::" 
        + shortMethodName + "()");
    }
    
    func_decl.append( "static " ); // all dispatch methods can be static

    Type return_type = m.getReturnType();
    func_decl.append( IOR.getReturnString(return_type, true, false) + " ");
    if ( return_type.getType() != Type.VOID ) { 
        pre_impl.append( IOR.getReturnString(return_type, true, false) );
        pre_impl.append(" _result");
        pre_impl.append(Utilities.getTypeInitialization(return_type));
        pre_impl.append(";\n");
    }
    switch( return_type.getDetailedType() ) { 
    case Type.VOID:
      break;
    case Type.OPAQUE:
      impl_call.append("_result = ");
      break;
    case Type.BOOLEAN:
      pre_impl.append( Cxx.getReturnString(return_type)+ " _local_result;\n" );
      impl_call.append( "_local_result = " );
      post_impl.append( "_result = ( _local_result ? TRUE : FALSE );\n" );
      break;
    case Type.CHAR:
    case Type.INT:
    case Type.LONG:
    case Type.FLOAT:
    case Type.DOUBLE:
      impl_call.append("_result = ");
      break;
    case Type.ENUM:
      pre_impl.append( Cxx.getEnumName( return_type.getSymbolID()) 
        + " _local_result;\n");
      impl_call.append("_local_result = ");
      post_impl.append("_result = (" 
        + IOR.getEnumName( return_type.getSymbolID()) + ")_local_result;\n");
      break;
    case Type.FCOMPLEX:
      pre_impl.append( Cxx.getReturnString(return_type) +" _local_result;\n" );
      impl_call.append( "_local_result = " );
      post_impl.append( "_result.real = _local_result.real();\n");
      post_impl.append( "_result.imaginary = _local_result.imag();\n" );
      break;
    case Type.DCOMPLEX:
      pre_impl.append( Cxx.getReturnString(return_type) + " _local_result;\n" );
      impl_call.append( "_local_result = " );
      post_impl.append( "_result.real = _local_result.real();\n");
      post_impl.append( "_result.imaginary = _local_result.imag();\n" );
      break;
    case Type.STRING:
      pre_impl.append( Cxx.getReturnString(return_type) + " _local_result;\n" );
      impl_call.append( "_local_result = " );
      post_impl.append( "_result = sidl_String_strdup( _local_result.c_str() )"
        + ";\n" );
      break;
    case Type.SYMBOL:
      throw new CodeGenerationException(nm + "Type.SYMBOL should have been "
                  + "resolved to\nType.ENUM, Type.CLASS, or Type.INTERFACE.");
      //break;
    case Type.CLASS:
    case Type.INTERFACE:
      pre_impl.append(Cxx.getReturnString(return_type) + " _local_result;\n");
      impl_call.append( "_local_result = " );
      post_impl.append( "if ( _local_result._not_nil() ) {\n");
      post_impl.append( "  _local_result.addRef();\n");
      post_impl.append( "}\n" );
      post_impl.append( "_result = _local_result._get_ior();\n");
      break;
    case Type.ARRAY:
      pre_impl.append(Cxx.getReturnString(return_type) + " _local_result;\n");
      impl_call.append("_local_result = " );
      post_impl.append("if ( _local_result._not_nil() ) {\n");
      if (return_type.hasArrayOrderSpec()) {
        post_impl.append("  _local_result.ensure(" 
          + return_type.getArrayDimension() + ", " 
          + s_ensureOrderConstant[return_type.getArrayOrder()] + ");\n");
      }
      post_impl.append("  _local_result.addRef();\n");
      post_impl.append("}\n" );
      post_impl.append("_result = " 
        + Cxx.getIORCall("_local_result", return_type) + ";\n");
      break;
    default:      
    } // end case( return_type.getType() )

    func_decl.append( Cxx.getMethodSkelName( d_ext.getSymbolID(), 
                                             longMethodName ) );
    func_decl.append( "( " );

    if ( m.isStatic() ) { 
      impl_call.append( Cxx.getMethodImplName( d_ext.getSymbolID(), 
                                               shortMethodName) );
    } else { 
      func_decl.append( "\n" );
      func_decl.append( IOR.getObjectName( d_ext.getSymbolID() ) + "* self" );
      if ( nargs > 0 ) { func_decl.append( ", " ); }
      pre_impl.append( Cxx.getSymbolName( d_ext.getSymbolID(), "impl" ) );
      pre_impl.append( " *_this = " );
      pre_impl.append(Cxx.reinterpretCast(Cxx.getSymbolName(d_ext.getSymbolID(),
        "impl" ) + "*", "self->d_data") );
      pre_impl.append( ";\n" );
      impl_call.append( "_this->" + shortMethodName);
    }
    impl_call.append("( ");

    //Due to rarrays, we need to separate the func_decl stuff from the 
    // rest of the arguments (2 different argument lists)
    for ( Iterator it = vArgs.iterator(); it.hasNext(); ) { 
      Argument arg = (Argument) it.next();
      String mode = Cxx.getArgModeComment( arg );
      String argName = arg.getFormalName();

      func_decl.append( mode );
      func_decl.append( IOR.getArgumentString(arg, true, false, false) + " " 
        + argName );
      if ( it.hasNext() ) { 
        func_decl.append(",\n");
      }
    }

    // writeup the argument lists
    for ( Iterator it = callArgs.iterator(); it.hasNext(); ) { 
      Argument arg = (Argument) it.next();
      Type type = arg.getType();
      int typeInt = type.getDetailedType();
      String argName = arg.getFormalName();
      String mode = Cxx.getArgModeComment( arg );
      int modeInt = arg.getMode();
      impl_call.append( mode );
      switch ( typeInt ) { 
      case Type.OPAQUE:
        switch( modeInt ) { 
        case Argument.IN:
          impl_call.append( argName );
          break;
        case Argument.OUT:
        case Argument.INOUT:
          impl_call.append( "*" + argName );
          break;
        }
        break;
      case Type.BOOLEAN:
        switch ( modeInt ) { 
        case Argument.IN:
          pre_impl.append( "bool _local_" + argName + " = (" + argName 
            + "==TRUE);\n" );
          impl_call.append( "_local_" + argName );
          break;
        case Argument.OUT:
          pre_impl.append( "bool _local_" + argName + ";\n" );
          impl_call.append( "_local_" + argName );
          post_impl.append( "*" + argName + " = (_local_" + argName 
            + " ? TRUE : FALSE);\n");
          break;
        case Argument.INOUT:
          pre_impl.append( "bool _local_" + argName + " = (*" + argName 
            + "==TRUE);\n" );
          impl_call.append( "_local_" + argName );
          post_impl.append( "*" + argName + " = (_local_" + argName 
            + " ? TRUE : FALSE);\n");
          break;
        }
        break;
      case Type.CHAR:
      case Type.INT:
      case Type.LONG:
      case Type.FLOAT:
      case Type.DOUBLE:
        switch ( modeInt ) { 
        case Argument.IN:
          impl_call.append( argName );
          break;
        case Argument.OUT:
        case Argument.INOUT:
          impl_call.append( "*" + argName );
          break;
        }
        break;
      case Type.ENUM:
        switch ( modeInt ) { 
        case Argument.IN:
          impl_call.append( "("+ Cxx.getEnumName(type.getSymbolID()) + "&)" 
            + argName );
          break;
        case Argument.OUT:
        case Argument.INOUT:
          impl_call.append( "("+ Cxx.getEnumName(type.getSymbolID()) + "&)*" 
            + argName );
          break;
        }
        break;
      case Type.FCOMPLEX:
        switch( modeInt ) { 
        case Argument.IN:
          pre_impl.append(Cxx.getReturnString(type) + " _local_" + argName 
            + " ( " + argName + ".real, " + argName + ".imaginary );\n");
          impl_call.append("_local_" + argName );
          break;
        case Argument.OUT:
          pre_impl.append( Cxx.getReturnString(type) + " _local_" + argName 
            + ";\n");
          impl_call.append("_local_" + argName );
          post_impl.append(argName + "->real = _local_" + argName + ".real();\n"
            + argName + "->imaginary = _local_" + argName + ".imag();\n" );
          break;
        case Argument.INOUT:
          pre_impl.append(Cxx.getReturnString(type) + " _local_" + argName 
            + " ( " + argName + "->real, " + argName + "->imaginary );\n");
          impl_call.append("_local_" + argName );
          post_impl.append(argName + "->real = _local_" + argName + ".real();\n"
            + argName + "->imaginary = _local_" + argName + ".imag();\n" );
          break;
        }
        break;
      case Type.DCOMPLEX:
        switch( modeInt ) { 
        case Argument.IN:
          pre_impl.append(Cxx.getReturnString(type) + " _local_" + argName 
            + " ( " + argName + ".real, " + argName + ".imaginary );\n");
          impl_call.append("_local_" + argName );
          break;
        case Argument.OUT:
          pre_impl.append(Cxx.getReturnString(type) + " _local_" + argName 
            + ";\n");
          impl_call.append("_local_" + argName );
          post_impl.append(argName + "->real = _local_" + argName + ".real();\n"
            + argName + "->imaginary = _local_" + argName + ".imag();\n" );
          break;
        case Argument.INOUT:
          pre_impl.append(Cxx.getReturnString(type) + " _local_" + argName 
            + " ( " + argName + "->real, " + argName + "->imaginary );\n");
          impl_call.append("_local_" + argName );
          post_impl.append(argName + "->real = _local_" + argName + ".real();\n"
            + argName + "->imaginary = _local_" + argName + ".imag();\n" );
          break;
        }
        break;
      case Type.STRING:
        switch ( modeInt ) { 
        case Argument.IN:
          pre_impl.append(Cxx.getReturnString(type) + " _local_" + argName 
            + "= ( " + argName + " ? " + argName + ": \"\" );\n");
          impl_call.append("_local_" + argName );
          break;
        case Argument.OUT:
          pre_impl.append(Cxx.getReturnString(type) + " _local_" + argName 
            + ";\n");
          impl_call.append("_local_" + argName );
          post_impl.append("*" + argName + " = sidl_String_strdup( _local_"
            + argName + ".c_str() );\n" );
          break;
        case Argument.INOUT:
          pre_impl.append(Cxx.getReturnString(type) + " _local_" + argName 
            + "= ( *" + argName + " ? *" + argName + ": \"\" );\n");
          impl_call.append("_local_" + argName );
          post_impl.append("if ( *" + argName + "== 0) {\n");
          post_impl.append("  *" + argName + " = sidl_String_strdup( _local_" 
            + argName + ".c_str() );\n" );
          post_impl.append("} else if ( strlen( *" + argName + " ) >= _local_" 
            + argName + ".length() ) {\n");
          post_impl.append("  _local_" + argName + ".copy( *" + argName 
            + ", ::std::string::npos );\n");
          post_impl.append("  (*" + argName + ")[ _local_" + argName 
            + ".length()] = 0;\n");
          post_impl.append("} else { \n" );
          post_impl.append("  sidl_String_free( *" + argName + ");\n");
          post_impl.append("  *" + argName + " = sidl_String_strdup( _local_" 
            + argName + ".c_str() );\n" );
          post_impl.append("}" );
          break;
        }
        break;
      case Type.SYMBOL:
        throw new CodeGenerationException(nm + "Type.SYMBOL should have been "
                    + "resolved to\nType.ENUM, Type.CLASS, or Type.INTERFACE.");
        //break;
      case Type.CLASS:
      case Type.INTERFACE:        
        switch( modeInt ) { 
        case Argument.IN:
          pre_impl.append(Cxx.getReturnString(type) + " _local_" + argName 
            + "(" + argName + ");\n");
          impl_call.append("_local_" + argName );
          break;
        case Argument.OUT:
          pre_impl.append(Cxx.getReturnString(type) + " _local_" + argName 
            + ";\n");
          impl_call.append("_local_" + argName );
          post_impl.append("if ( _local_" + argName + "._not_nil() ) {\n");
          post_impl.append("  _local_" + argName + ".addRef();\n");
          post_impl.append("}\n" );
          post_impl.append("*" + argName + " = _local_" + argName 
            + "._get_ior();\n" );
          break;
        case Argument.INOUT:
          pre_impl.append(Cxx.getReturnString(type) + " _local_" + argName 
            + "((*" + argName + "));\n");
          impl_call.append("_local_" + argName );
          post_impl.append("if ( _local_" + argName + "._not_nil() ) {\n");
          post_impl.append("  _local_" + argName + ".addRef();\n");
          post_impl.append("}\n" );
          post_impl.append("if (*" + argName + ") {\n");
          post_impl.append("  " + IOR.getExceptionFundamentalType() + "_delexp = NULL;\n");
          post_impl.append("  sidl_BaseInterface_deleteRef((struct sidl_BaseInterface__object*)(*" + argName 
            + ((typeInt == Type.INTERFACE) ? 
               ")->d_object, &_delexp);\n" 
               : "), &_delexp);\n"));
          post_impl.append("  if (_delexp) {\n");
          post_impl.append("    if (*_exception) {\n");
          post_impl.append("       " + IOR.getExceptionFundamentalType() +
                           " _delexp2;\n");
          post_impl.append("       sidl_BaseInterface_deleteRef(_delexp, &_delexp2);\n");
          post_impl.append("    }\n");
          post_impl.append("    else *_exception = _delexp;\n");
          post_impl.append("  }\n");
          post_impl.append("}\n");
          post_impl.append("*" + argName + " = _local_" + argName 
            + "._get_ior();\n" );
          break;
        }
        break;
      case Type.ARRAY:
        if(arg.getType().isRarray()) {
          String r_name = arg.getFormalName();
          
          pre_impl.append(IOR.getReturnString(arg.getType(), false, false) 
            + " _local_" + arg.getFormalName());
          
          if(modeInt == Argument.IN) {
            pre_impl.append(" = " 
              + C.getEnsureArray(arg.getType().getArrayType()) + "(" 
              + arg.getFormalName() + ", " + arg.getType().getArrayDimension() 
              + ", " + s_ensureOrderConstant[arg.getType().getArrayOrder()] 
              + ");\n");
          } else {
            pre_impl.append(" = " 
              + C.getEnsureArray(arg.getType().getArrayType()) + "(*" 
              + arg.getFormalName() + ", " + arg.getType().getArrayDimension() 
              + ", " + s_ensureOrderConstant[arg.getType().getArrayOrder()] 
              + ");\n");
            String init_func_name = IOR.getArrayNameForFunctions(
                                      type.getArrayType().getType()) + "_init";
            post_impl.append(init_func_name + "(" + r_name + C.RAW_ARRAY_EXT 
              + ", *" + r_name + ", " + arg.getType().getArrayDimension() 
              + ", (*" + r_name + ")->d_metadata.d_lower, (*" + r_name 
              + ")->d_metadata.d_upper, (*" + r_name 
              + ")->d_metadata.d_stride);\n");  
          }
          pre_impl.append(IOR.getArgumentWithFormal(arg, false, true, false) 
            + C.RAW_ARRAY_EXT
            + " = ");
          pre_impl.append("_local_" + argName+"->d_firstElement;\n");
          
          impl_call.append(argName + C.RAW_ARRAY_EXT);
          
          
        } else {
          switch( modeInt ) { 
          case Argument.IN:
            pre_impl.append(Cxx.getReturnString(type) + " _local_" + argName 
              + "(" + argName + ");\n");
            pre_impl.append("if (" + argName + ") { ");
            pre_impl.append("_local_" + argName + ".addRef();");
            if (type.hasArrayOrderSpec()) {
              pre_impl.append("_local_" + argName + ".ensure(" 
                + type.getArrayDimension() + ", " 
                + s_ensureOrderConstant[type.getArrayOrder()] + ");\n");
            }
            pre_impl.append(" }\n");
            impl_call.append( "_local_" + argName );
            break;
          case Argument.OUT:
            pre_impl.append(Cxx.getReturnString(type) + " _local_" + argName 
              + ";\n");
            impl_call.append("_local_" + argName);
            post_impl.append("if ( _local_" + argName + "._not_nil() ) {\n");
            if (type.hasArrayOrderSpec()) {
              post_impl.append("  _local_" + argName + ".ensure(" 
                + type.getArrayDimension() + ", " 
                + s_ensureOrderConstant[type.getArrayOrder()] + ");\n");
            }
            post_impl.append("  _local_" + argName + ".addRef();\n");
            post_impl.append("}\n" );
            post_impl.append("*" + argName + " = " + Cxx.getIORCall("_local_" 
              + argName, type) + ";\n" );
            break;
          case Argument.INOUT:
            pre_impl.append(Cxx.getReturnString(type) + " _local_" + argName 
              + "(*" + argName + ");\n");
            pre_impl.append("*" + argName + " = 0;\n");
            if (type.hasArrayOrderSpec()) {
              pre_impl.append("_local_" + argName + ".ensure(" 
                + type.getArrayDimension() + ", " 
                + s_ensureOrderConstant[type.getArrayOrder()] + ");\n");
            }
            impl_call.append("_local_" + argName );
            post_impl.append("if ( _local_" + argName + "._not_nil() ) {\n");
            if (type.hasArrayOrderSpec()) {
              post_impl.append("  _local_" + argName + ".ensure(" 
                + type.getArrayDimension() + ", " 
                + s_ensureOrderConstant[type.getArrayOrder()] + ");\n");
            }
            post_impl.append("_local_" + argName + ".addRef();\n");
            post_impl.append("}\n" );
            post_impl.append("*" + argName + " = " + Cxx.getIORCall("_local_" 
              + argName, type) + ";\n" );
            break;
          }
        }
        break;
      }
      if ( it.hasNext() ) { 
        impl_call.append(", ");
      }
    }

    Map index_args = m.getRarrayInfo();
    for(Iterator i = index_args.values().iterator(); i.hasNext();) {
      Collection index_collection = (Collection) i.next();
      Iterator tmp = index_collection.iterator();
      Method.RarrayInfo info = (Method.RarrayInfo) tmp.next();

      Argument index = info.index;
      int dim = info.dim;

      //String upper_func_name = IOR.getArrayNameForFunctions(arrayType.getType()) + "_length";   

      if(index.getType().getType() == Type.LONG)
        pre_impl.append("int64_t ");
      else
        pre_impl.append("int32_t ");
      pre_impl.append(index.getFormalName() + " = ");
      String lengthVar = "sidlLength(" +
        "_local_"+info.rarray.getFormalName() + "," + dim + ")";
      AssertionExpression ae = (AssertionExpression)
        info.rarray.getType().getArrayIndexExprs().get(dim);
      pre_impl.append(CExprString.
                      toCString(Inverter.invertExpr(ae, lengthVar)));
      pre_impl.append(";\n");
    }

    // if method throws exceptions...
    if (throwsExceptions ) { 
      if ( nargs > 0 || (!m.isStatic())) { // if other args are listed.
        func_decl.append( ", " );
      }
      pre_impl.append("*_exception = 0;//init just to be safe\n");
      func_decl.append("sidl_BaseInterface__object ** _exception");
    }

    // final details
    func_decl.append( " )\n");
    impl_call.append( " )" + impl_suffix.toString() + ";\n" ); 

    // finally dump everything out
    d_writer.println( func_decl.toString().trim() );
    d_writer.println( "{" );
    d_writer.tab();
    d_writer.writeCommentLine( "pack args to dispatch to impl" );
    d_writer.println( pre_impl.toString().trim() );
    d_writer.writeCommentLine( "dispatch to impl" );
    if ( throwsExceptions ) { 
      d_writer.println("try { ");
      d_writer.tab();
    }
    d_writer.println( impl_call.toString().trim() );
    if ( throwsExceptions ) { 
      d_writer.backTab();
      d_writer.println("} catch ( ::sidl::StubBase& _sb ) { ");
      d_writer.tab();
      d_writer.println("::sidl::BaseInterface _ex(_sb);");
      d_writer.println("_ex.addRef();");
      d_writer.println("*_exception = _ex._get_ior();");
      if ( return_type.getType() != Type.VOID ) { 
        d_writer.println( "return _result;" );
      }
      d_writer.backTab();
      d_writer.println("}");
    }
    d_writer.writeCommentLine( "unpack results and cleanup" );
    d_writer.println( post_impl.toString().trim() );
    if ( return_type.getType() != Type.VOID ) { 
      d_writer.println( "return _result;" );
    }
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  } // end writeImplDispatch
}
