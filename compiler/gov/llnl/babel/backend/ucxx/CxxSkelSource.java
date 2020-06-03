//
// File:        CxxSkelSource.java
// Package:     gov.llnl.babel.backend.ucxx
// Revision:    @(#) $Revision: 7421 $
// Date:        $Date: 2011-12-15 17:06:06 -0800 (Thu, 15 Dec 2011) $
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
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.Utilities;
import gov.llnl.babel.backend.c.C;
import gov.llnl.babel.backend.ucxx.Cxx;
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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Create and write a header for a Cxx C extension class to wrap a 
 * BABEL extendable in a Cxx object. The header has to expose a 
 * function to create a wrapped IOR, a function to check if a 
 * <code>PyObject</code> is an instance of this extension type, and
 * an import macro.
 */
public class CxxSkelSource {

  private static final String s_ensureOrderConstant[] = {
    Cxx.prependLocalUCxx()+"sidl::general_order",
    Cxx.prependLocalUCxx()+"sidl::column_major_order",
    Cxx.prependLocalUCxx()+"sidl::row_major_order"
  };

  private Extendable d_ext = null;

  private Context d_context;
  private LanguageWriterForCxx d_writer = null;

  private static final String s_epv = "epv";
  private static final String s_pre_epv = "pre_epv";
  private static final String s_post_epv = "post_epv";
  private static final String s_sepv = "sepv";
  private static final String s_pre_sepv = "pre_sepv";
  private static final String s_post_sepv = "post_sepv";


  /**
   * Create an object capable of generating the header file for a
   * BABEL extendable.
   *
   * @param ext   an interface or class symbol that needs a header
   *              file for a Cxx C extension class.
   */
  public CxxSkelSource(Extendable ext, Context context) {
    d_ext = ext;
    d_context = context;
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
      d_writer = Cxx.createSource( d_ext, Cxx.FILE_ROLE_SKEL, "SKELSRCS",
                                   d_context );
      SymbolID id = d_ext.getSymbolID();
      //writeBanner(cls, writer);
      d_writer.generateInclude( Cxx.generateFilename(d_ext.getSymbolID(), 
                                                     Cxx.FILE_ROLE_IMPL,
                                                     Cxx.FILE_TYPE_CXX_HEADER,
                                                     d_context),
                                true );
      d_writer.generateInclude(IOR.getHeaderFile(id), true);
      if( d_ext.hasExceptionThrowingMethod(true) ) { 
        d_writer.generateInclude( Cxx.generateFilename(BabelConfiguration.getBaseExceptionInterface(),
                                  Cxx.FILE_ROLE_STUB, 
                                  Cxx.FILE_TYPE_CXX_HEADER), 
                                  true );
        d_writer.generateInclude( Cxx.generateFilename(BabelConfiguration.getLangSpecific(),
                                  Cxx.FILE_ROLE_STUB, 
                                  Cxx.FILE_TYPE_CXX_HEADER), 
                                  true );
      }
      //Generate includes for all the used babel classes
      Cxx.generateSourceIncludes(d_writer, d_ext, d_context);

      // NOTE: the following is not a binding to a sidl type
      d_writer.generateInclude( "sidl_String.h", false );

      d_writer.printlnUnformatted("#include <stddef.h>");
      d_writer.printlnUnformatted("#include <cstring>");
 
      Class cls = (Class) d_ext;
      Set serializeSIDs = IOR.getStructSymbolIDs(cls, true);
      if ((!d_context.getConfig().getSkipRMI()) && !serializeSIDs.isEmpty()) {
        d_writer.generateInclude("sidl_rmi_Return.hxx", true);
        d_writer.println();
      }

      d_writer.println();
      if ((!d_context.getConfig().getSkipRMI()) && 
          !(IOR.getStructSymbolIDs((Class)d_ext, false).isEmpty())) {
        d_writer.generateInclude("sidl_rmi_Call.hxx", true);
        d_writer.println();
      }


      /* declare a static buffer that is used to hold method function
       * pointers.
       */
      if (d_context.getConfig().getFastCall()) {
        d_writer.printlnUnformatted("#include <assert.h>");
        
        Cxx.generateFastCallTypes(d_writer, cls, d_context, true);
        d_writer.println("static " +
                         Cxx.getNativeEPVEntryName(cls) +
                         " s_native_method_buffer[" +
                         /* the +1 only prevents 0-sized arrays */
                         (1 + cls.getNonstaticMethods(false).size()) + 
                         "];");
        
        d_writer.println();
      }
      
      Cxx.beginExternCRegion( d_writer );
      writeSkelFunctions();
      if (!d_context.getConfig().getSkipRMI()) {
        writeRMIAccessFunctions();
      }
      writeLoad();
      writeConstructor();
      writeDestructor();
      writeInitializeEPV();
      if (d_ext.hasStaticMethod(true)) {
        writeInitializeSEPV();
      }
      Cxx.endExternCRegion( d_writer );
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
  private void writeInitializeEPV() throws CodeGenerationException {
    SymbolID id = d_ext.getSymbolID();
    d_writer.println("void");
    d_writer.print(C.getSetEPVName(id) + "(" + IOR.getEPVName(id) + " *");
    d_writer.print(s_epv);
    if (IOR.generateHookEPVs(d_ext, d_context)) {
      d_writer.println(",");
      d_writer.tab();
      d_writer.print(IOR.getPreEPVName(id) + " *" + s_pre_epv + ", ");
      d_writer.print(IOR.getPostEPVName(id) + " *" + s_post_epv + ")");
      d_writer.backTab();
    } else {
      d_writer.println(")");
    }
    d_writer.println("{");
    d_writer.tab();
    d_writer.writeCommentLine("initialize builtin methods");
    initializeMethodPointer
      (IOR.getBuiltinMethod(IOR.CONSTRUCTOR, id, d_context), id, -1);
    initializeMethodPointer
      (IOR.getBuiltinMethod(IOR.CONSTRUCTOR2, id, d_context), id, -1);
    initializeMethodPointer
      (IOR.getBuiltinMethod(IOR.DESTRUCTOR, id, d_context), id, -1);
    d_writer.writeCommentLine("initialize local methods");
    Iterator i = d_ext.getMethods(false).iterator();
    int index = 0;
    while (i.hasNext()) {
      Method m = (Method)i.next();
      if ( !m.isAbstract() && !m.isStatic()) { 
        initializeMethodPointer( m, id, index++);
      }
    }
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  /**
   * For non-<code>static</code> methods, write an assignment statement to
   * initialize a particular membor of the entry point vector.
   * Initialization of <code>static</code> methods appears elsewhere. 
   * 
   * @param m          a description of the method to initialize
   * @param id         the name of class that owns the method
   * @param index      a unique index in a 0-based dense ordering of the methods
   */
  private void initializeMethodPointer(Method m, SymbolID id, int index) 
    throws CodeGenerationException
  {
    final String methodName = m.getLongMethodName();
    final String ename      = IOR.getVectorEntry(methodName);

    switch (m.getDefinitionModifier()) {
    case Method.FINAL:
    case Method.NORMAL:
      String sname = Cxx.getMethodSkelName(id, methodName);
      if (!IOR.isBuiltinMethod(methodName)) {
        Method pre      = m.spawnPreHook();
        String preName  = pre.getLongMethodName();

        Method post     = m.spawnPostHook();
        String postName = post.getLongMethodName();

        /*
         * Order is (currently) critical!  If hook methods (i.e.,
         * pre- and post-hooks) are being generated then be sure to
         * initialize the EPV pointers to the skel/impl methods!
         * Otherwise, if we're just making sure to include the
         * basic hook skeletons (but not the implementations), then
         * initialize the EPV pointers to null.
         */
        if (IOR.generateHookMethods(d_ext, d_context)) {
          writeMethodAssignment(s_pre_epv, IOR.getVectorEntry(preName), 
                                Cxx.getMethodSkelName(id, preName));
          writeMethodAssignment(s_epv, ename, sname);
          writeMethodAssignment(s_post_epv, IOR.getVectorEntry(postName),
                                Cxx.getMethodSkelName(id, postName));
        } else if (IOR.generateHookEPVs(d_ext, d_context)) {
          writeMethodAssignment(s_pre_epv, IOR.getVectorEntry(preName), 
                                Cxx.NULL);
          writeMethodAssignment(s_epv, ename, sname);
          writeMethodAssignment(s_post_epv, IOR.getVectorEntry(postName),
                                Cxx.NULL);
        } else {
          writeMethodAssignment(s_epv, ename, sname);
        }

        /*
         * initialize native entry point vectors. This is a little bit
         * tricky as we need to return a _method_ function pointer, which
         * differs from compiler to compiler. In general, it's larger than a
         * regular function pointer as it includes some meta-information for
         * multiple inheritance. We thus use a static buffer to hold our
         * method pointers and let the opaque pointer in the EPV data
         * structure point into this buffer. The same structure also
         * contains an offset that specifies the location of the this
         * pointer relative to the beginning of the IOR data structure. 
         */
        if (d_context.getConfig().getFastCall()) {
          Cxx.generateFastCallEPVInitialization(d_writer,
                                                d_context,
                                                d_ext,
                                                m,
                                                index);
        }
      } else {
        writeMethodAssignment(s_epv, ename, sname);
      }
      break;
    case Method.ABSTRACT:
      writeMethodAssignment(s_epv, ename, Cxx.NULL);
      break;
    default:
      /* do nothing */
      break;
    }
  }

  /**
   * Write a function to initialize entries in the static entry point vector
   * for a particular class. This will generate an assignment statement for
   * each <code>static</code> method defined locally in the class.
   * 
   */
  private void writeInitializeSEPV() throws CodeGenerationException { 
    SymbolID id = d_ext.getSymbolID();
    d_writer.println("void");
    d_writer.print(C.getSetSEPVName(id) + "(" + IOR.getSEPVName(id) + " *");
    d_writer.print(s_sepv);
    if (IOR.generateHookEPVs(d_ext, d_context)) {
      d_writer.println(",");
      d_writer.tab();
      d_writer.print(IOR.getPreSEPVName(id) + " *" + s_pre_sepv + ", ");
      d_writer.println(IOR.getPostSEPVName(id) + " *" + s_post_sepv + ")");
      d_writer.backTab();
    } else {
      d_writer.println(")");
    }

    d_writer.println("{");
    d_writer.tab();
    Iterator i = d_ext.getMethods(false).iterator();
    while (i.hasNext()) {
      Method m = (Method)i.next();
      if (m.isStatic()) {
        initializeStaticMethodPointer(m, id);
      }
    }
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  /**
   * For <code>static</code> methods, write an assignment statement to
   * initialize a particular member of the entry point vector.
   * Initialization of non-<code>static</code> methods appears elsewhere.
   *
   * @param m          a description of the method to initialize
   * @param id         the name of class that owns the method
   */
  private void initializeStaticMethodPointer(Method m, SymbolID id)
    throws CodeGenerationException
  {
    final String methodName = m.getLongMethodName();
    final String ename      = IOR.getVectorEntry(methodName);

    switch (m.getDefinitionModifier()) {
    case Method.STATIC:
      String sname = Cxx.getMethodSkelName(id, methodName);
      if (!IOR.isBuiltinMethod(methodName)) {
        Method pre     = m.spawnPreHook();
        String preName = pre.getLongMethodName();

        Method post     = m.spawnPostHook();
        String postName = post.getLongMethodName();

        /*
         * Order is (currently) critical!  If hook methods (i.e.,
         * pre- and post-hooks) are being generated then be sure to
         * initialize the EPV pointers to the skel/impl methods!
         * Otherwise, if we're just making sure to include the
         * basic hook skeletons (but not the implementations), then
         * initialize the EPV pointers to null.
         */
        if (IOR.generateHookMethods(d_ext, d_context)) {
          writeMethodAssignment(s_pre_sepv, IOR.getVectorEntry(preName),
                                Cxx.getMethodSkelName(id, preName));
          writeMethodAssignment(s_sepv, ename, sname);
          writeMethodAssignment(s_post_sepv, IOR.getVectorEntry(postName),
                                Cxx.getMethodSkelName(id, postName));
        } else if (IOR.generateHookEPVs(d_ext, d_context)) {
          writeMethodAssignment(s_pre_sepv, IOR.getVectorEntry(preName),
                                Cxx.NULL);
          writeMethodAssignment(s_sepv, ename, sname);
          writeMethodAssignment(s_post_sepv, IOR.getVectorEntry(postName),
                                Cxx.NULL);
        } else {
          writeMethodAssignment(s_sepv, ename, sname);
        }

        /*
         * initialize the static native entry point. We use the opaque
         * pointer to directly point to the implementation. This assumes
         * that the size of a static function pointer is the same as the
         * size of a (void *). There is a compile-time check for this
         * elsewhere! 
         */
        if (d_context.getConfig().getFastCall()) {
          Cxx.generateFastCallEPVInitialization(d_writer,
                                                d_context,
                                                d_ext,
                                                m,
                                                -1);
        }
      } else {
        writeMethodAssignment(s_sepv, ename, sname);
      }
      break;
    default:
      /* do nothing */
      break;
    }
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


  private void writeConstructor() { 
    SymbolID id = d_ext.getSymbolID();
    d_writer.println("static void");
    d_writer.print( Cxx.getMethodSkelName(id,"_ctor") ); // Package_class
    d_writer.print("(");
    d_writer.print( IOR.getObjectName(id) ); 
    d_writer.println("* self, " +
                     IOR.getExceptionFundamentalType() + "*_ex ) { ");
    d_writer.tab();
    d_writer.println("try {");
    d_writer.tab();
    d_writer.writeCommentLine("the constructor saves a reference to the newly created object in self");
    d_writer.println("new" + Cxx.getImplSymbolName(id,"impl") + "(self);");
    d_writer.backTab();
    d_writer.println("} catch ( " + Cxx.prependGlobalUCxx() +
                     "::sidl::RuntimeException _exc ) {");
    d_writer.tab();
    try {
      d_writer.pushLineBreak(false);
      /* We use C here to avoid allocating memory for the string.
       * C++ passes std::strings, which require memory allocation. 
       */
      d_writer.println("sidl_BaseInterface _throwaway_exception;");
      d_writer.println("(_exc._get_ior()->d_epv->f_add) (_exc._get_ior()->d_object, __FILE__, "+
                       "__LINE__,\" C++ skeleton for _ctor.\", &_throwaway_exception);");

      //d_writer.println("_exc.add(__FILE__,__LINE__,\"C++ skeleton for _ctor.\");");
    }
    finally {
      d_writer.popLineBreak();
    }
    d_writer.println("_exc.addRef();");
    d_writer.println("*_ex = reinterpret_cast< struct sidl_BaseInterface__object * >(_exc._get_ior());");
    d_writer.backTab();
    d_writer.println("} catch (...) {");
    d_writer.tab();
    d_writer.writeCommentLine("Convert all other exceptions to LangSpecific");
    d_writer.println(Cxx.prependGlobalUCxx() + 
                     "::sidl::LangSpecificException _unexpected = " +
                     Cxx.prependGlobalUCxx() + 
                     "::sidl::LangSpecificException::_create();");
    try {
      d_writer.pushLineBreak(false);
      d_writer.println("_unexpected.setNote(\"Unexpected C++ exception in _ctor.\");");
    }
    finally {
      d_writer.popLineBreak();
    }
    d_writer.println("_unexpected.addRef();");
    d_writer.println("*_ex = reinterpret_cast< struct sidl_BaseInterface__object * >(_unexpected._get_ior());");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();

    d_writer.println("static void");
    d_writer.print( Cxx.getMethodSkelName(id,"_ctor2") ); // Package_class
    d_writer.print("(");
    d_writer.print( IOR.getObjectName(id) ); 
    d_writer.println("* self, void* private_data, " +
                     IOR.getExceptionFundamentalType() + "*_ex ) { ");
    d_writer.tab();
    d_writer.println("*_ex = NULL;");
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
    d_writer.println("try {");
    d_writer.tab();
    d_writer.println(Cxx.getImplSymbolName(id,"impl") + "* loc_data = "+
                     Cxx.reinterpretCast(Cxx.getImplSymbolName(id,"impl") + "*", 
                                         "self->d_data")+";");
    d_writer.println("if(!loc_data->_isWrapped()) {");
    d_writer.tab();
    d_writer.println("delete (loc_data);");
    //    d_writer.print(Cxx.reinterpretCast(Cxx.getImplSymbolName(id,"impl") + "*", 
    //                                    "self->d_data"));
    //d_writer.println(" );");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("} catch ( " + Cxx.prependGlobalUCxx() +
                     "::sidl::RuntimeException _exc ) {");
    d_writer.tab();
    try {
      d_writer.pushLineBreak(false);
      d_writer.pushLineBreak(false);
      /* We use C here to avoid allocating memory for the string.
       * C++ passes std::strings, which require memory allocation. 
       */
      d_writer.println("sidl_BaseInterface _throwaway_exception;");
      d_writer.println("(_exc._get_ior()->d_epv->f_add) (_exc._get_ior()->d_object, __FILE__, "+
                       "__LINE__,\" C++ skeleton for _dtor.\", &_throwaway_exception);");

      //      d_writer.println("_exc.add(__FILE__,__LINE__,\"C++ skeleton for _dtor.\");");
    }
    finally {
      d_writer.popLineBreak();
    }
    d_writer.println("_exc.addRef();");
    d_writer.println("*_ex = reinterpret_cast< struct sidl_BaseInterface__object * >(_exc._get_ior());");
    d_writer.backTab();
    d_writer.println("} catch (...) {");
    d_writer.tab();
    d_writer.writeCommentLine("Convert all other exceptions to LangSpecific");
    d_writer.println(Cxx.prependGlobalUCxx() + 
                     "::sidl::LangSpecificException _unexpected = " +
                     Cxx.prependGlobalUCxx() + 
                     "::sidl::LangSpecificException::_create();");
    try {
      d_writer.pushLineBreak(false);
      d_writer.println("_unexpected.setNote(\"Unexpected C++ exception in _dtor.\");");
    }
    finally {
      d_writer.popLineBreak();
    }
    d_writer.println("_unexpected.addRef();");
    d_writer.println("*_ex = reinterpret_cast< struct sidl_BaseInterface__object * >(_unexpected._get_ior());");
    d_writer.backTab();
    d_writer.println("}");
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
    d_writer.print(Cxx.getImplSymbolName(id,"impl") + "::_load();");
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
        if (IOR.generateHookMethods(d_ext, d_context)) {
          Method pre = m.spawnPreHook();
          Method post = m.spawnPostHook();
          writeImplDispatch( pre );
          writeImplDispatch( post );
        }
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
    Set fconnectSIDs = IOR.getFConnectSymbolIDs(cls);
    //    Set fcastSIDs = IOR.getFCastSymbolIDs(cls);
    d_writer.printlnUnformatted("#ifdef WITH_RMI");
    Set serializeSIDs = IOR.getStructSymbolIDs(cls, true);
    Set deserializeSIDs = IOR.getStructSymbolIDs(cls, false);

    for (Iterator i = fconnectSIDs.iterator(); i.hasNext(); ) {  
      SymbolID destination_id = (SymbolID) i.next();
      d_writer.println(C.getSymbolObjectPtr(destination_id) + " " + IOR.getSkelFConnectName(id,destination_id)+ 
                     "(const char* url, sidl_bool ar, sidl_BaseInterface *_ex) { ");
      d_writer.tab();
      d_writer.println("return "+C.getFullMethodName(destination_id, "_connectI")+"(url, ar, _ex);");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();
    }

    //    for (Iterator i = fcastSIDs.iterator(); i.hasNext(); ) {
    //  SymbolID destination_id = (SymbolID) i.next();
    //  d_writer.println(C.getSymbolObjectPtr(destination_id) + " " + IOR.getSkelFCastName(id,destination_id)+ 
    //                "(void* bi, " + IOR.getExceptionFundamentalType() + "*_ex) { ");
    //  d_writer.tab();
    // d_writer.println("return "+C.getFullMethodName(destination_id, "_rmicast")+"(bi, _ex);");
    //  d_writer.backTab();
    //  d_writer.println("}");
    //  d_writer.println();
    //    }

    d_writer.printlnUnformatted("#endif /*WITH_RMI*/");

    for(Iterator i = serializeSIDs.iterator(); i.hasNext(); ) {
      SymbolID l_id = (SymbolID) i.next();
      d_writer.println("void");
      d_writer.println(IOR.getSkelSerializationName(id,l_id, true));
      d_writer.tab();
      d_writer.println("(const " +
                       IOR.getStructName(l_id) + " *strct, " +
                       "struct sidl_rmi_Return__object *pipe, const char *name, sidl_bool copyArg, " +
                       IOR.getExceptionFundamentalType() + "*exc)");
      d_writer.backTab();
      d_writer.println("{");
      d_writer.tab();
      d_writer.println("::sidl::rmi::Return __pipe(pipe);");
      d_writer.println("const " + Cxx.getSymbolName(l_id) +
                       " *_strct = reinterpret_cast< const " +
                       Cxx.getSymbolName(l_id) + " * >(strct);");
      d_writer.println("try {");
      d_writer.tab();
      d_writer.println("_strct->serialize(__pipe, name, copyArg);");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println("catch (::sidl::BaseException &_be) {");
      d_writer.tab();
      d_writer.println("::sidl::BaseInterface &_bi(_be);");
      d_writer.println("_bi.addRef();");
      d_writer.println("*exc = _bi._get_ior();");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();
    }

    for(Iterator i = deserializeSIDs.iterator(); i.hasNext(); ) {
      SymbolID l_id = (SymbolID) i.next();
      d_writer.println("void");
      d_writer.println(IOR.getSkelSerializationName(id, l_id, false));
      d_writer.tab();
      d_writer.println("(" + IOR.getStructName(l_id) + " *strct, " +
                       "struct sidl_rmi_Call__object *pipe, const char *name, sidl_bool copyArg," +
                       IOR.getExceptionFundamentalType() + " *exc)");
      d_writer.backTab();
      d_writer.println("{");
      d_writer.tab();
      d_writer.println("::sidl::rmi::Call __pipe(pipe);");
      d_writer.println(Cxx.getSymbolName(l_id) +
                       " *_strct = reinterpret_cast< " +
                       Cxx.getSymbolName(l_id) + " * >(strct);");
      d_writer.println("try {");
      d_writer.tab();
      d_writer.println("_strct->deserialize(__pipe, name, copyArg);");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println("catch (::sidl::BaseException &_be) {");
      d_writer.tab();
      d_writer.println("::sidl::BaseInterface &_bi(_be);");
      d_writer.println("_bi.addRef();");
      d_writer.println("*exc = _bi._get_ior();");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();
    }
  }


  /**
   * Create a wrapper function in the skeleton for a particular IOR method.
   * 
   * @param method    the description of the impl method to be wrapped.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception. It can be caused by I/O trouble or
   *    violations of the data type invariants.
   */
  private void writeImplDispatch( Method m ) throws CodeGenerationException {
    final List vArgs = m.getArgumentList();
    final List callArgs = m.getArgumentListWithIndices();
    final int nargs = vArgs.size();
    final boolean throwsExceptions = (m.getThrows().size()>0);
    StringBuffer func_decl = new StringBuffer( nargs * 32 ); 
    // clean up inout references
    StringBuffer pre_call = new StringBuffer( nargs * 128);
    // everything through first { 
    StringBuffer pre_impl = new StringBuffer( nargs * 128 );
    // everything before the impl call
    StringBuffer impl_call = new StringBuffer( nargs * 32 );
    // The actual call to the impl method
    StringBuffer impl_suffix = new StringBuffer( 16 );
    // might be "._get_ior()" iff return type is object
    StringBuffer post_impl = new StringBuffer( nargs * 128 );
    // inout strings must be freed in an exception
    StringBuffer pre_exception_return = new StringBuffer();
    // after the impl returns
    String shortMethodName = m.getShortMethodName();
    String longMethodName = m.getLongMethodName();
    String className = d_ext.getSymbolID().getShortName();
    boolean needExit = false;

    if ( shortMethodName.equals(className) ) { 
      shortMethodName = "f_" + shortMethodName;
      System.out.println("WARNING: gov.llnl.babel.backend.UCxx.CxxSkelSource: "
        + "sidl / C++ conflict!");
      System.out.println("         methodName == className is not allowed in "
        + "C++");
      System.out.println("         (this is restricted to constructors in "
        + "C++)");
      System.out.println("         changing to " + className + "::" 
        + shortMethodName + "()");
    }
    
    func_decl.append( "static " ); // all dispatch methods can be static

    String res = "_result";
    String lres = "_local" + res;

    Type return_type = m.getReturnType();
    int  type_val    = return_type.getDetailedType();
    func_decl.append( IOR.getReturnString( return_type, d_context, true, false ) );
    func_decl.append( "\n" );
    if ( type_val != Type.VOID ) { 
        pre_impl.append( IOR.getReturnString( return_type, d_context, true, false ) );
        pre_impl.append(" " + res);
        pre_impl.append(Utilities.getTypeInitialization(return_type, 
                                                        d_context));
        pre_impl.append(";\n");
    }

    String defRetLine = Cxx.getReturnString(return_type, d_context) +
      " " + lres + ";\n";
    String addRefLine = "  " + lres + ".addRef();\n";
    switch( return_type.getDetailedType() ) { 
    case Type.STRUCT:
      pre_impl.append( defRetLine);
      impl_call.append( lres + " = ");
      post_impl.append( lres + ".toIOR(" + res + ");");
      break;
    case Type.ARRAY:
      pre_impl.append( defRetLine );
      impl_call.append( lres + " = " );
      post_impl.append( "if ( " + lres + "._not_nil() ) {\n" );
      if (return_type.hasArrayOrderSpec()) {
        post_impl.append("  " + lres + ".ensure(" 
          + return_type.getArrayDimension() + ", " 
          + s_ensureOrderConstant[return_type.getArrayOrder()] + ");\n");
      }
      post_impl.append( addRefLine );
      post_impl.append("}\n" );
      post_impl.append(res + " = " + Cxx.getIORCall(lres, return_type) + ";\n");
      break;
    case Type.BOOLEAN:
      pre_impl.append( defRetLine );
      impl_call.append( lres + " = " );
      post_impl.append( res + " = ( " + lres + " ? TRUE : FALSE );\n" );
      break;
    case Type.CHAR:
    case Type.DOUBLE:
    case Type.FLOAT:
    case Type.INT:
    case Type.LONG:
    case Type.OPAQUE:
      impl_call.append(res + " = ");
      break;
    case Type.INTERFACE:
    case Type.CLASS:
      pre_impl.append( defRetLine );
      impl_call.append(lres + " = ");
      post_impl.append( "if ( " + lres + "._not_nil() ) {\n");
      post_impl.append( addRefLine );
      post_impl.append( "}\n" );
      post_impl.append( res + " = " + Cxx.getIORCall(lres, return_type) + ";\n");
      break;

    case Type.DCOMPLEX:
    case Type.FCOMPLEX:
      pre_impl.append( defRetLine );
      impl_call.append( lres + " = " );
      post_impl.append( res + ".real = " + lres + ".real();\n");
      post_impl.append( res + ".imaginary = " + lres + ".imag();\n" );
      break;
    case Type.ENUM:
      pre_impl.append( Cxx.getEnumName( return_type.getSymbolID() ) + " " 
        + lres + ";\n");
      impl_call.append(lres + " = ");
      post_impl.append(res + " = " + lres + ";\n");
      break;
    case Type.STRING:
      pre_impl.append( defRetLine );
      impl_call.append(lres + " = ");
      post_impl.append(res + " = sidl_String_strdup(" + lres + ".c_str());\n");
      break;
    case Type.SYMBOL:
      throw new CodeGenerationException("Type.SYMBOL should have been resolved"
                  + " to\nType.ENUM, Type.CLASS, or Type.INTERFACE");
      //break;
    case Type.VOID:
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
      func_decl.append( "/* in */ " );
      func_decl.append( IOR.getObjectName( d_ext.getSymbolID() ) + "* self" );
      if ( nargs > 0 ) { func_decl.append( ", " ); }
      pre_impl.append( Cxx.getImplSymbolName( d_ext.getSymbolID(), "impl" ) );
      pre_impl.append( " *_this = " );
      pre_impl.append(Cxx.reinterpretCast(Cxx.getImplSymbolName(
                                              d_ext.getSymbolID(), "impl" ) 
                                              + "*", "self->d_data") );
      pre_impl.append( ";\n" );
      impl_call.append( "_this->" + shortMethodName + "_impl");
    }
    impl_call.append( "( " );

    //Due to rarrays, we need to seperate the func_decl stuff from the 
    // rest of the arguments (2 different argument lists)
    for ( Iterator it = vArgs.iterator(); it.hasNext(); ) { 
      Argument arg = (Argument) it.next();
      String mode = "/* " + Cxx.argComment( arg ) + " */ ";
      String argName = arg.getFormalName();

      func_decl.append(mode);
      func_decl.append(IOR.getArgumentString(arg, d_context, 
                                             true, false, false) + 
                       " " + argName);
      if ( it.hasNext() ) { 
        func_decl.append(",");
      }
    }

    // writeup the argument lists
    for ( Iterator it = callArgs.iterator(); it.hasNext(); ) { 
      Argument arg = (Argument) it.next();
      Type type = arg.getType();
      int typeInt = type.getDetailedType();
      String argName = arg.getFormalName();
      String mode = "/* " + Cxx.argComment( arg ) + " */ ";
      int modeInt = arg.getMode();

      impl_call.append( mode );
      boolean argGoesOut = true;
      if (modeInt == Argument.IN) argGoesOut = false;
      String localArgName = "_local_" + argName;
      addRefLine   = "  " + localArgName + ".addRef();\n";

      switch ( typeInt ) { 
      case Type.ARRAY:
        if(arg.getType().isRarray()) {
          String r_name  = arg.getFormalName();
          Type argType   = arg.getType();
          int  argDim    = argType.getArrayDimension();
          
          pre_impl.append(IOR.getReturnString(arg.getType(), d_context,
                                              false, false) 
            + localArgName + " = " + C.getEnsureArray(argType.getArrayType()) 
            + "(");
          if (argGoesOut) {
            pre_impl.append("*");
          }
          pre_impl.append( arg.getFormalName() + ", " + argDim + ", " 
            + s_ensureOrderConstant[argType.getArrayOrder()] + ");\n");
         
          if (modeInt != Argument.IN) {
            pre_impl.append("sidl__array_deleteRef((struct sidl__array *)*" +
                             r_name + ");\n");
            post_impl.append("*" + r_name + " = " + localArgName + ";\n");
          }
          else {
            post_impl.append("sidl__array_deleteRef((struct sidl__array *)" +
                             localArgName + ");\n");
          }
          pre_exception_return.append("sidl__array_deleteRef((struct sidl__array *)" +
                                      localArgName + ");\n");
          pre_impl.append(IOR.getArgumentWithFormal(arg, d_context,
                                                    false, true, false) 
            + C.RAW_ARRAY_EXT + " = ");
          pre_impl.append(localArgName + "->d_firstElement;\n");
          impl_call.append(argName + C.RAW_ARRAY_EXT);
        } else {
          boolean doEnsureCall  = type.hasArrayOrderSpec();
          String ensureCallLine = localArgName + ".ensure("
                                  + type.getArrayDimension() + ", " 
                                  + s_ensureOrderConstant[type.getArrayOrder()]
                                  + ");\n";

          pre_impl.append( Cxx.getReturnString(type, d_context) + 
                           " " + localArgName);
          if (modeInt != Argument.OUT) {
            pre_impl.append("(");
            if (argGoesOut) pre_impl.append("*");
            pre_impl.append(argName + ")");
          }
          pre_impl.append(";\n");
          if (modeInt != Argument.OUT) {
            if (modeInt == Argument.IN) {
              pre_impl.append("if (" + argName + ") {\n");
              pre_impl.append(addRefLine);
              if (doEnsureCall) pre_impl.append("  ");
            } else {
              pre_impl.append( "*" + argName + " = 0;\n");
            }
            if (doEnsureCall) pre_impl.append(ensureCallLine);
            if (modeInt == Argument.IN) pre_impl.append(" }\n");
          }
          impl_call.append(localArgName);
          if (modeInt != Argument.IN) {
            post_impl.append("if ( " + localArgName + "._not_nil() ) {\n");
            if (doEnsureCall) post_impl.append("  " + ensureCallLine);
            post_impl.append(addRefLine);
            post_impl.append( "}\n" );
            post_impl.append( "*" + argName + " = " 
              + Cxx.getIORCall(localArgName, type) + ";\n" );
          }
        }
        break;
      case Type.BOOLEAN:
        pre_impl.append("bool " + localArgName);
        if (modeInt != Argument.OUT) {
          String argPtr = argGoesOut ? "*" : "";
          pre_impl.append(" = (" + argPtr + argName + " == TRUE)" );
        }
        pre_impl.append(";\n");
        impl_call.append( localArgName );
        if (argGoesOut) {
          post_impl.append( "*" + argName + " = (" + localArgName 
            + " ? TRUE : FALSE);\n");
        }
        break;
      case Type.CHAR:
      case Type.DOUBLE:
      case Type.FLOAT:
      case Type.INT:
      case Type.LONG:
      case Type.OPAQUE:
        if (argGoesOut) impl_call.append("*");
        impl_call.append( argName );
        break;
      case Type.CLASS:
      case Type.INTERFACE:        
        switch( modeInt ) { 
        case Argument.IN:
          pre_impl.append(Cxx.getReturnString(type, d_context) +
                          "  " + localArgName 
            + "(" + argName + ");\n"); 
          impl_call.append(localArgName );
          break;
        case Argument.OUT:
          pre_impl.append(Cxx.getReturnString(type, d_context) +
                          " " + localArgName 
            + ";\n");
          impl_call.append(localArgName );
          post_impl.append("if ( " + localArgName + "._not_nil() ) {\n");
          post_impl.append("  " + localArgName + ".addRef();\n");
          post_impl.append("}\n" );
          post_impl.append("*" + argName + " = " + 
                           Cxx.getIORCall(localArgName,type)+";\n" );
          break;
        case Argument.INOUT:
          pre_impl.append(Cxx.getReturnString(type, d_context) +
                          " " + localArgName 
            + "((*" + argName + "));\n");
          pre_call.append("if (*" + argName + ") {\n");
          pre_call.append("  ((*" + argName + ")->d_epv->f_deleteRef)((*" +
                          argName + ")" + 
                          ((typeInt == Type.INTERFACE) ? "->d_object" : "") +
                          ", _exception);\n");
          pre_call.append("  (*" + argName +") = 0;\n" );
          pre_call.append("  if (*_exception) goto EXIT;\n");
          pre_call.append("}\n");
          needExit = true;
          impl_call.append(localArgName );
          post_impl.append("if ( " + localArgName + "._not_nil() ) {\n");
          post_impl.append("  " + localArgName + ".addRef();\n");
          post_impl.append("}\n");
          post_impl.append("*" + argName + " = " +  
                           Cxx.getIORCall(localArgName,type)+";\n" );
          break;
        }
        break;
      case Type.DCOMPLEX:
      case Type.FCOMPLEX:
        pre_impl.append( Cxx.getReturnString(type, d_context) +
                         " " + localArgName ); 
        if (modeInt != Argument.OUT) {
          String sep = argGoesOut ? "->" : ".";
          pre_impl.append(" (" + argName + sep + "real, " + argName + sep 
            + "imaginary)");
        }
        pre_impl.append(";\n");
        impl_call.append( localArgName );
        if (argGoesOut) {
          post_impl.append(argName + "->real = " + localArgName + ".real();\n");
          post_impl.append(argName + "->imaginary = " + localArgName 
            + ".imag();\n");
        }
        break;
      case Type.ENUM:
        if (argGoesOut) {
          pre_impl.append(Cxx.getEnumName(type.getSymbolID()) + " " +
                          localArgName);
          if (modeInt == Argument.INOUT) {
            pre_impl.append(" = (" +
                            Cxx.getEnumName(type.getSymbolID()) +
                            ")*" + argName);
          }
          pre_impl.append(";\n");
          impl_call.append(localArgName);
          post_impl.append("*" + argName + " = " + localArgName + "\n;");
        }
        else {
          impl_call.append( "(" + Cxx.getEnumName(type.getSymbolID()) + ")" + argName);
        }
        break;
      case Type.STRING:
        pre_impl.append(Cxx.getReturnString(type, d_context) +
                        " " + localArgName); 
        if (modeInt != Argument.OUT) {
          String argNamePtr = argGoesOut ? "*" : "";
          pre_impl.append(" = ( " + argNamePtr + argName + " ? " + argNamePtr);
          pre_impl.append(argName + ": \"\" )");
        }
        pre_impl.append(";\n");
        impl_call.append( localArgName );
        String dup_line = argGoesOut ? "*" + argName + " = sidl_String_strdup( "
                                           + localArgName + ".c_str() );\n"
                                     : "";
        if (modeInt == Argument.OUT) {
          post_impl.append(dup_line);
        } else if (modeInt == Argument.INOUT) {
          post_impl.append( "if ( *" + argName + " == 0) {\n");
          post_impl.append( "  " + dup_line);
          post_impl.append( "} else if ( strlen( *" + argName + " ) >= "
            + localArgName + ".length() ) {\n");
          post_impl.append( "  " + localArgName + ".copy( *" + argName 
            + ", ::std::string::npos );\n");
          post_impl.append( "  (*" + argName + ")[ " + localArgName 
            + ".length()] = 0;\n");
          post_impl.append( "} else { \n" );
          post_impl.append( "  sidl_String_free( *" + argName + " );\n");
          post_impl.append( "  " + dup_line);
          post_impl.append( "}\n" );
          pre_exception_return.append("sidl_String_free( *" + argName + ");\n");
          pre_exception_return.append("(*" + argName + ") = 0;\n");
        }
        break;
      case Type.STRUCT:
        if (Argument.OUT == modeInt) {
          pre_impl.append("/* C++ placement new to run ctor */\n");
          pre_impl.append(Cxx.getReturnString(type, d_context) + " *" + 
                          localArgName + " =\n");
          pre_impl.append("   new((void *)" +
                          argName + ") " +
                          Cxx.getReturnString(type, d_context) +
                          "();\n");
          impl_call.append("*" + localArgName);
        }
        else {
          impl_call.append("*(reinterpret_cast< ");
          if (Argument.IN == modeInt) impl_call.append("const ");
          impl_call.append(Cxx.getReturnString(type, d_context));
          impl_call.append("* >(");
          impl_call.append(argName);
          impl_call.append("))");
        }
        break;
      case Type.SYMBOL:
        throw new CodeGenerationException("Type.SYMBOL should have been "
                    + "resolved to\nType.ENUM, Type.CLASS, or Type.INTERFACE");
        //break;
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

      if(index.getType().getType() == Type.LONG)
        pre_impl.append("int64_t ");
      else
        pre_impl.append("int32_t ");
      pre_impl.append(index.getFormalName() + " = ");
      pre_impl.append(CExprString.
                      toCString(Inverter.invertExpr
                                ((AssertionExpression)
                                 info.rarray.getType().
                                 getArrayIndexExprs().get(dim),
                                 "sidlLength(" + "_local_" +
                                 info.rarray.getFormalName() + "," + 
                                 dim + ")", d_context)));
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
    d_writer.println( pre_call.toString().trim() );
    d_writer.writeCommentLine( "dispatch to impl" );
    if ( throwsExceptions ) { 
      d_writer.println("try { ");
      d_writer.tab();
    }
    d_writer.println( impl_call.toString().trim() );
    if ( throwsExceptions ) { 
      d_writer.backTab();
      if (m.hasExplicitExceptions()) {
        d_writer.println("} catch ( " + Cxx.prependGlobalUCxx() 
                         + "::sidl::BaseException _ex ) { ");
      }
      else {
        d_writer.println("} catch ( " + Cxx.prependGlobalUCxx() 
                         + "::sidl::RuntimeException _ex ) { ");
      }
      d_writer.tab();
      //      d_writer.println("::sidl::BaseInterface _ex(_sb);");
      try {
        d_writer.pushLineBreak(false);
        /* We use C here to avoid allocating memory for the string.
         * C++ passes std::strings, which require memory allocation. 
         */
        d_writer.println("sidl_BaseInterface _throwaway_exception;");
        d_writer.println("(_ex._get_ior()->d_epv->f_add) (_ex._get_ior()->d_object, "+ 
                         " __FILE__, __LINE__,\"C++ skeleton for " + longMethodName + 
                         ".\", &_throwaway_exception);");
      }
      finally {
        d_writer.popLineBreak();
      }
      d_writer.println("_ex.addRef();");
      d_writer.println("*_exception = reinterpret_cast< struct sidl_BaseInterface__object * >(_ex._get_ior());");
      d_writer.println(pre_exception_return.toString().trim());
      if ( return_type.getType() != Type.VOID ) { 
        d_writer.println( "return " + res + ";" );
      }
      d_writer.backTab();
      d_writer.println("} catch (...) {");
      d_writer.tab();
      d_writer.writeCommentLine("Convert all other exceptions to LangSpecific");
      d_writer.println(Cxx.prependGlobalUCxx() + 
                       "::sidl::LangSpecificException _unexpected = " +
                       Cxx.prependGlobalUCxx() + 
                       "::sidl::LangSpecificException::_create();");
      d_writer.println("sidl_BaseInterface _throwaway_exception;");
      d_writer.println("(_unexpected._get_ior()->d_epv->f_setNote) (_unexpected._get_ior(),"+
                       "\"Unexpected C++ exception\", &_throwaway_exception);");
      //      d_writer.println("_unexpected.setNote(\"Unexpected C++ exception\");");
      d_writer.println("_unexpected.addRef();");
      d_writer.println("*_exception = reinterpret_cast< struct sidl_BaseInterface__object * >(_unexpected._get_ior());");
      d_writer.println(pre_exception_return.toString().trim());
      if ( return_type.getType() != Type.VOID ) { 
        d_writer.println( "return " + res + ";" );
      }
      d_writer.backTab();
      d_writer.println("}");
    }
    d_writer.writeCommentLine( "unpack results and cleanup" );
    d_writer.println( post_impl.toString().trim() );
    if (needExit) {
      d_writer.println("EXIT:;");
    }
    if ( return_type.getType() != Type.VOID ) { 
      d_writer.println( "return " + res + ";" );
    }
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  } // end writeImplDispatch
}
