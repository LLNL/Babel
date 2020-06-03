//
// File:        PythonClientCSource.java
// Package:     gov.llnl.babel.backend.python
// Revision:    @(#) $Revision: 7188 $
// Date:        $Date: 2011-09-27 11:38:42 -0700 (Tue, 27 Sep 2011) $
// Description: Generate the C source file for a Python for an extendable
// 
// Copyright (c) 2000-2009, Lawrence Livermore National Security, LLC
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

import gov.llnl.babel.BabelConfiguration;
import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.LevelComparator;
import gov.llnl.babel.backend.Utilities;
import gov.llnl.babel.backend.c.C;
import gov.llnl.babel.backend.python.Python;
import gov.llnl.babel.backend.rmi.RMI;
import gov.llnl.babel.backend.rmi.RMIStubHeader;
import gov.llnl.babel.backend.rmi.RMIStubSource;
import gov.llnl.babel.symbols.Argument;
import gov.llnl.babel.symbols.Class;
import gov.llnl.babel.symbols.Comment;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.Struct;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.SymbolTable;
import gov.llnl.babel.symbols.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


/**
 * This class will write the C source file for a Python C extension
 * module to provide access from Python to a class or interface.
 */
public class PythonClientCSource extends PythonClientC {

  /**
   * The class or interface for which a Python C extension module
   * is being written.
   */
  private Extendable         d_ext           = null;

  /**
   * <code>true</code> means this extenable has one or more static
   * methods; <code>false</code> means this extendable has
   * exactly zero static methods.
   */
  private boolean            d_hasStaticMethods;

  private boolean            d_isBaseInterface;

  private String []          d_externalMethods;

  /**
   * Create an object capable of generating the source C file for a
   * sidl extendable (ie class or interface).
   *
   * @param ext    an interface or class that needs a header
   *               file for a Python C extension class.
   */
  public PythonClientCSource(Extendable ext,
                             Context context)
  {
    super(ext, context);
    Type extType    = new Type(ext.getSymbolID(), d_context);
    d_ext           = ext;
    d_hasStaticMethods = d_ext.hasStaticMethod(true);
    d_isBaseInterface = BabelConfiguration.getBaseInterface().
      equals(ext.getFullName());
    if (d_isBaseInterface) {
      d_externalMethods = new String[11];
      d_externalMethods[10] = Python.getBorrowArrayFromSIDL(null);
    }
    else {
      d_externalMethods = new String[10];
    }
    d_externalMethods[0] = Python.getExtendableWrapper(d_ext);
    d_externalMethods[1] = Python.getExtendableConverter(d_ext);
    d_externalMethods[2] = Python.getBorrowArrayFromPython(extType);
    d_externalMethods[3] = Python.getBorrowArrayFromSIDL(extType);
    d_externalMethods[4] = Python.getExtendableBorrow(d_ext);
    d_externalMethods[5] = Python.getExtendableDeref(d_ext);
    d_externalMethods[6] = Python.getExtendableNewRef(d_ext);
    d_externalMethods[7] = Python.getExtendableAddRef(d_ext);
    d_externalMethods[8] = Python.getExtendableType(d_ext);
    d_externalMethods[9] = Python.getExtendableConnect(d_ext);
    //    d_externalMethods[10] = Python.getExtendableCast(d_ext);
    
  }

  /**
   * Write code to acquire the Python GIL lock.
   */
  private void acquirePythonLock()
  {
    d_lw.printlnUnformatted("#if (PY_VERSION_HEX >= 0x02040000)");
    d_lw.println("_gstate = PyGILState_Ensure();");
    d_lw.println("sidl_Python_LogGILEnsure(__func__, __FILE__, __LINE__, (int)_gstate);");
    d_lw.printlnUnformatted("#endif /* Python 2.4 or later */");
  }

  private void releasePythonLock()
  {
    d_lw.printlnUnformatted("#if (PY_VERSION_HEX >= 0x02040000)");
    d_lw.println("PyGILState_Release(_gstate);");
    d_lw.println("sidl_Python_LogGILRelease(__func__, __FILE__, __LINE__, (int)_gstate);");
    d_lw.printlnUnformatted("#endif /* Python 2.4 or later */");
  }
  
  private void writeRMISerialization(Symbol sym, boolean serialize)
    throws CodeGenerationException
  {
    final String name = IOR.getSymbolName(sym.getSymbolID());
    final String method = (serialize ? "serialize" : "deserialize");
    final String sym_prefix = sym.getFullName().replace('.', '_');
    
    d_lw.printlnUnformatted("#if (PY_VERSION_HEX >= 0x02040000)");
   
    d_lw.printlnUnformatted("  #define RMI_" + name +
                            "_" + method +  "(strct, pipe, name, copy, exc) { \\");
    
    d_lw.printlnUnformatted("    PyGILState_STATE _gstate; \\");
    d_lw.printlnUnformatted("    _gstate = PyGILState_Ensure(); \\");
    d_lw.printlnUnformatted("    sidl_Python_LogGILEnsure(__func__, __FILE__, __LINE__, (int)_gstate); \\");
    d_lw.printlnUnformatted("    " + sym_prefix +
                            (serialize ? "__pySerialize" : "__pyDeserialize") + "(" +
                            "strct, sidl_io_" + (serialize ? "S" : "Des") +
                            "erializer__cast(pipe, exc), name, copy, exc); \\");
    d_lw.printlnUnformatted("    PyGILState_Release(_gstate); \\");
    d_lw.printlnUnformatted("    sidl_Python_LogGILRelease(__func__, __FILE__, __LINE__, (int)_gstate); \\");
    d_lw.printlnUnformatted("  }");

    d_lw.printlnUnformatted("#else /* pre Python 2.4 */");

    d_lw.printlnUnformatted("  #define RMI_" + name +
                            "_" + method +  "(strct, pipe, name, copy, exc) { \\");
    d_lw.printlnUnformatted("    " + sym_prefix +
                            (serialize ? "__pySerialize" : "__pyDeserialize") + "(" +
                            "strct, sidl_io_" + (serialize ? "S" : "Des") +
                            "erializer__cast(pipe, exc), name, copy, exc); \\");
    d_lw.printlnUnformatted("  }");
    d_lw.printlnUnformatted("#endif /* Python 2.4 or later */");
  }

  /**
   * Add <code>#include</code> lines for all the system headers and
   * the referenced types.
   */
  private void includeHeaderFiles() 
    throws CodeGenerationException
  {
    SymbolTable table = d_context.getSymbolTable();
    d_lw.printUnformatted("#define ");
    d_lw.printUnformatted(Python.getInternalGuard(d_ext));
    d_lw.printlnUnformatted(" 1");
    addInclude(Python.getCHeaderPath(d_ext, "Module"), false);
    addInclude(IOR.getHeaderFile(d_ext.getSymbolID()), true);
    addInclude("sidlObjA.h", false);
    addInclude("sidlPyArrays.h", false);
    d_lw.printlnUnformatted("#ifdef SIDL_HAVE_NUMPY");
    addInclude("oldnumeric.h", false);
    d_lw.printlnUnformatted("#else");
    d_lw.printlnUnformatted("#ifdef SIDL_HAVE_NUMERIC_PYTHON");
    addInclude("Numeric/arrayobject.h", false);
    d_lw.printlnUnformatted("#else");
    d_lw.printlnUnformatted("#error Neither Numeric Python nor NumPy installed");
    d_lw.printlnUnformatted("#endif");
    d_lw.printlnUnformatted("#endif");
    addInclude("sidl_Loader.h", true);
    addInclude("sidl_header.h", true);
    addInclude("sidl_String.h", true);
    addInclude("sidl_Python.h", true);
    addInclude("sidl_interface_IOR.h", true);
    if (!d_context.getConfig().getSkipRMI()) {
      addInclude(Python.getCHeaderPath(d_context.getSymbolTable().lookupSymbol("sidl.rmi.NetworkException"), "Module"), false);
    }
    Iterator i = Utilities.convertIdsToSymbols(d_context,
                                               d_ext.getSymbolReferences()).
      iterator();
    while (i.hasNext()) {
      Symbol sym = (Symbol)i.next();
      if (!(Symbol.ENUM == sym.getSymbolType())) {
        addInclude(Python.getCHeaderPath(sym, "Module"), false);
      }
      if (Symbol.STRUCT == sym.getSymbolType()) {
        if (!d_context.getConfig().getSkipRMI()) {
          addInclude(Python.getCHeaderPath
                     (Utilities.lookupSymbol(d_context,"sidl.rmi.Invocation"), 
                      "Module"), true);
        }
        addInclude(IOR.getHeaderFile(sym.getSymbolID()), true);
        if (!d_context.getConfig().getSkipRMI()) {
          writeRMISerialization(sym, true);
          writeRMISerialization(sym, false);
        }
      }
    }
    if (!d_context.getConfig().getSkipRMI()) {
      addInclude(Python.getCHeaderPath(table.lookupSymbol("sidl.rmi.Call"), 
                                       "Module"), false);
      addInclude(Python.getCHeaderPath(table.lookupSymbol("sidl.rmi.Return"), 
                                       "Module"), false);
      addInclude(Python.getCHeaderPath(table.lookupSymbol("sidl.rmi.Ticket"), 
                                       "Module"), false);
    }

    d_lw.printlnUnformatted("#include <stdlib.h>");
    d_lw.printlnUnformatted("#include <string.h>");
    d_lw.printlnUnformatted("#include <stddef.h>");
    d_lw.printlnUnformatted("#include <stdio.h>");

    d_lw.println();
  }


  /**
   * If the extendable has static methods, create a static
   * variable to hold the classes static entry point
   * vector.
   */
  private void staticMethods() {
    d_lw.printlnUnformatted("#if PY_MAJOR_VERSION >= 3");
    d_lw.println("static");
    d_lw.printlnUnformatted("#else");
    d_lw.println("staticforward");
    d_lw.printlnUnformatted("#endif");

    d_lw.println("PyTypeObject _" +
                 d_ext.getFullName().replace('.','_')
                 + "Type;");
    d_lw.println();
    if (d_hasStaticMethods){
      d_lw.print("static ");
      d_lw.print(IOR.getSEPVName(d_ext.getSymbolID()));
      d_lw.print(" *_sepv");
      d_lw.println(" = NULL;");
      d_lw.println();
    }
    if (!d_ext.isInterface()) {
      d_lw.println("static const " + 
                   IOR.getExternalName(d_ext.getSymbolID()) + 
                   " *_implEPV = NULL;");
      d_lw.println();

      if (!BabelConfiguration.isSIDLBaseClass(d_ext)) {
        writeLoadImpl();
      }
    }
  }

  /**
   * Write out the function signature for the function to
   * handle cast and create requests.
   */
  private void pythonCastSignature() {
    d_lw.println("static int");
    d_lw.println(d_ext.getFullName().replace('.','_') +
                 "_createCast(PyObject *self, PyObject *args, PyObject *kwds) {");
    d_lw.tab();
  }

  /**
   * Write out code to do the input argument processing for the
   * cast function. The success of input processing is stored
   * the in the variable <code>_okay</code>.
   */
  private void pythonCastInArguments() {
    final SymbolID id = d_ext.getSymbolID();
    d_lw.print(IOR.getObjectName(id));
    d_lw.println(" *optarg = NULL;");
    if(d_ext.isAbstract()) {
      d_lw.println("static char *_kwlist[] = { \"sobj\", NULL };");
      d_lw.print("int _okay = PyArg_ParseTupleAndKeywords(args, kwds, \"");
      d_lw.print("O&\", _kwlist, (void *)");
      d_lw.print(Python.getExtendableConverter(d_ext));
      d_lw.println(", &optarg);");
    } else {
      d_lw.println("char* url = NULL;");
      d_lw.println("PyObject * implObj = NULL;");
      d_lw.println("static char *_kwlist[] = {\"sobj\",  \"url\", \"impl\", NULL };");
      d_lw.print("int _okay = PyArg_ParseTupleAndKeywords(args, kwds, \"");
      d_lw.print("|O&zO\", _kwlist, (void *)");
      d_lw.print(Python.getExtendableConverter(d_ext));
      d_lw.println(", &optarg, &url, &implObj);");
    }

  }

  /**
   * If this a create object request and the class is not abstract,
   * call the constructor to create a new sidl object.
   */
  private void pythonCastCreate() throws CodeGenerationException{

    if (!d_ext.isAbstract()) {
      d_lw.println("if (!optarg && !url && !implObj) {");
      d_lw.tab();
      d_lw.println(IOR.getExceptionFundamentalType() +
                   "_exception;");
      if (!BabelConfiguration.isSIDLBaseClass(d_ext)) {
        d_lw.println("if (!_loadClassImpl()) return -1;");
      }
      Python.leavePython(d_lw);
      d_lw.print("optarg = (*(_implEPV->createObject))");
      d_lw.println("(NULL,&_exception);");
      Python.resumePython(d_lw);
      d_lw.println("if (_exception) {");
      d_lw.tab();
      d_lw.println("sidl_RuntimeException__import();");
      d_lw.println("{");
      d_lw.tab();
      processExceptions(IOR.getBuiltinMethod(IOR.CONSTRUCTOR,d_ext.getSymbolID(), d_context));
      d_lw.println("return -1;");
      d_lw.backTab();
      d_lw.println("}");
      d_lw.backTab();
      d_lw.println("}");
      d_lw.backTab();
      d_lw.println("}");
      d_lw.println("else if (!optarg && !url && implObj) {");
      d_lw.tab();
      d_lw.println(IOR.getExceptionFundamentalType() +
                   "_exception;");
      if (!BabelConfiguration.isSIDLBaseClass(d_ext)) {
        d_lw.println("if (!_loadClassImpl()) return -1;");
      }
      d_lw.println("Py_INCREF(implObj);");
      Python.leavePython(d_lw);
      d_lw.print("optarg = (*(_implEPV->createObject))");
      d_lw.println("((void*)implObj,&_exception);");
      Python.resumePython(d_lw);
      d_lw.println("if (_exception) {");
      d_lw.tab();
      d_lw.println("sidl_RuntimeException__import();");
      d_lw.println("{");
      d_lw.tab();
      processExceptions(IOR.getBuiltinMethod(IOR.CONSTRUCTOR,d_ext.getSymbolID(), d_context));
      d_lw.println("return -1;");
      d_lw.backTab();
      d_lw.println("}");
      d_lw.backTab();
      d_lw.println("}");
      d_lw.backTab();
      d_lw.println("}");

      d_lw.println();
      if (!d_context.getConfig().getSkipRMI()) {
        d_lw.printlnUnformatted("#ifdef WITH_RMI");
        d_lw.println();
        d_lw.println("else if(url && !optarg && !implObj) {");
        d_lw.tab();
        d_lw.print(IOR.getExceptionFundamentalType());
        d_lw.println("_exception = NULL;");
        String cName = C.getFullMethodName(d_ext.getSymbolID(), "_remoteCreate");
        Python.leavePython(d_lw);
        d_lw.println("optarg = "+cName+"(url,&_exception);");
        Python.resumePython(d_lw);
        d_lw.println("if (_exception) {");
        d_lw.tab();
        d_lw.println("sidl_rmi_NetworkException__import();");

        d_lw.println("{");
        d_lw.tab();
        processExceptions(Python.createRemoteMethod(d_ext, d_context));
        d_lw.println("}"); 
        d_lw.backTab();
      
        d_lw.println("return -1;");
        d_lw.backTab();      
        d_lw.println("}"); 
        d_lw.backTab();
        d_lw.println("}");
        
        d_lw.println();
        d_lw.printlnUnformatted("#endif /*WITH_RMI*/");
        d_lw.println();
      }

      d_lw.writeCommentLine("OK, but fall though");
      d_lw.println("else if(!url && optarg && !implObj) {}");

      d_lw.writeCommentLine("Error case.");
      d_lw.println("else {");
      d_lw.tab(); 
      d_lw.println("return -1;");
      d_lw.backTab();        
      d_lw.println("}"); 
    }
  }

  /**
   * Wrap up the IOR in a wrapper Python class.
   */
  private void pythonCastWrapAndReturn() {
    d_lw.println("return sidl_Object_Init(");
    d_lw.tab();
    d_lw.println("(SPObject *)self,");
    if (d_ext.isInterface()) {
      d_lw.println("(struct sidl_BaseInterface__object *)optarg->d_object,");
    }
    else {
      d_lw.println("(struct sidl_BaseInterface__object *)optarg,");
    }
    d_lw.println("sidl_PyStealRef);");
    d_lw.backTab();
  }

  /**
   * Write the Python cast/create function. This method is called
   * when the Python user says X.X() or X.X(obj).  This function
   * is exposed as a static method.
   */
  private void pythonCastConstructor() throws CodeGenerationException {
    pythonCastSignature();
    pythonCastInArguments();

    d_lw.println("if (_okay) {");
    d_lw.tab();
    pythonCastCreate();
    pythonCastWrapAndReturn();
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println("return -1;");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println();
  }

  //emit a static method that overrides static member functions implemented
  //in Python (only applies for --fast-call)
  private void writeResolveStaticMethods()
    throws CodeGenerationException {
    d_lw.println("static PyObject *_babel_resolve_static_methods" +
                 "(PyObject *dict) {");
    d_lw.tab();

    Iterator i = d_ext.getStaticMethods(true).iterator();
    boolean first=true;
    while (i.hasNext()) {
      Method m = (Method)i.next();
      String long_name = m.getLongMethodName();
      if(first) {
        d_lw.println(IOR.getSEPVName(d_ext.getSymbolID()) + "*_sepv = NULL;");
        first=false;
        loadSEPV(m);
        d_lw.println();
      }
      if (isFastCallFeasibe(m) && m.isStatic()) {
        d_lw.println("if(_sepv->f_" + long_name +
                     "_native.lang == BABEL_LANG_PYTHON) {");
        d_lw.tab();
        d_lw.println("PyObject *name = PyString_FromString(\"" + long_name + "\");");
        d_lw.println("PyObject *impl = (PyObject *)_sepv->f_" +
                     long_name + "_native.opaque;");
        d_lw.println("PyObject *member = PyObject_GetAttr(impl, name);");
        d_lw.println("if(member) PyDict_SetItem(dict, name, member);");
        d_lw.println("Py_XDECREF(name);");
        d_lw.backTab();
        d_lw.println("}");
      }
    }
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println();
  }    
  
  //emit a static method that overrides Python's generic getattro
  //implementation
  private void writeCustomGetattro()
    throws CodeGenerationException {
    final SymbolID id = d_ext.getSymbolID();
    String ior_self = d_ext.isInterface() ? "ior->d_object" : "ior";

    d_lw.println("static PyObject *_babel_getattro" +
                 "(PyObject *self, PyObject *name) {");
    d_lw.tab();
    
    //initialize a static lookup table that allows us to quickly map a
    //function name to the corresponding offset in the epv table. This code
    //is executed only once!
    d_lw.println("static PyObject *offsets = NULL;");
    d_lw.println("PyObject *epv_offset = NULL;");
    d_lw.println("if(!offsets) {");
    d_lw.tab();
    d_lw.println("offsets = PyDict_New();");
    Iterator i = d_ext.getNonstaticMethods(true).iterator();
    while (i.hasNext()) {
      Method m = (Method)i.next();
      String long_name = m.getLongMethodName();
      if (isFastCallFeasibe(m) && !m.isStatic()) {
        d_lw.println("PyDict_SetItem(offsets, ");
        d_lw.tab();
        d_lw.println("PyString_FromString(\"" + long_name + "\"),");
	d_lw.printlnUnformatted("#if PY_MAJOR_VERSION < 3");
        d_lw.println("PyInt_FromSsize_t(offsetof(" +
                     IOR.getEPVName(id) + ", f_" + long_name + "_native)));");
	d_lw.printlnUnformatted("#else");
        d_lw.println("PyLong_FromSsize_t(offsetof(" +
                     IOR.getEPVName(id) + ", f_" + long_name + "_native)));");
	d_lw.printlnUnformatted("#endif");
        d_lw.backTab();
      }
    }
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println();

    //see if the current method is included in the dictionary and, if so,
    //return a bound member function obtained from the impl object instead
    //of the regular client stub.
    d_lw.println("if((epv_offset = PyDict_GetItem(offsets, name)) != NULL) {");
    d_lw.tab();
    d_lw.println(IOR.getObjectName(id) + " *ior =");
    d_lw.tab();
    d_lw.println("(" + IOR.getObjectName(id) + "*)sidl_Get_IOR(self);");
    d_lw.backTab();
    d_lw.println("sidl_babel_native_epv_t *p_native_epv = ");
    d_lw.tab();
    d_lw.println("(sidl_babel_native_epv_t *) " +
                 "((int8_t *)ior->d_epv + ");
    d_lw.printlnUnformatted("#if PY_MAJOR_VERSION < 3");
    d_lw.println("(size_t) PyInt_AsLong(epv_offset));");
    d_lw.printlnUnformatted("#else");
    d_lw.println("PyLong_AsSize_t(epv_offset));");
    d_lw.printlnUnformatted("#endif");
    d_lw.backTab();
    d_lw.println("if(p_native_epv->lang == BABEL_LANG_PYTHON) {");
    d_lw.tab();
    d_lw.println("int offset = (int) p_native_epv->opaque;");
    d_lw.println("PyObject *impl_self = *((PyObject **)((int8_t *)" +
                 ior_self + " + offset));");
    d_lw.println("PyObject *method = PyObject_GetAttr(impl_self, name);");
    d_lw.println("if(method) {");
    d_lw.tab();
    d_lw.println("Py_XINCREF(method);");
    d_lw.println("return(method);");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println();
    
    d_lw.println("return PyObject_GenericGetAttr(self, name);");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println();
  }
  
  private void writePythonType() {
    final SymbolID id = d_ext.getSymbolID();
    final boolean fastcall = d_context.getConfig().getFastCall();
    d_lw.printlnUnformatted("#ifndef PyVarObject_HEAD_INIT");
    d_lw.printlnUnformatted("#define PyVarObject_HEAD_INIT(type, size) \\");
    d_lw.printlnUnformatted("    PyObject_HEAD_INIT(type) size,");
    d_lw.printlnUnformatted("#endif");
    d_lw.println();

    d_lw.println("static PyTypeObject _" + id.getFullName().replace('.','_')
                 + "Type = {");
    d_lw.tab();
    d_lw.println("PyVarObject_HEAD_INIT(NULL,0)");
    d_lw.println("\"" + id.getFullName() + "." + id.getShortName() + "\", /* tp_name */");
    d_lw.println("0,      /* tp_basicsize */");
    d_lw.println("0,      /* tp_itemsize */");
    d_lw.println("0,      /* tp_dealloc */");
    d_lw.println("0,      /* tp_print */");
    d_lw.println("0,      /* tp_getattr */");
    d_lw.println("0,      /* tp_setattr */");
    d_lw.println("0,      /* tp_compare */");
    d_lw.println("0,      /* tp_repr */");
    d_lw.println("0,      /* tp_as_number */");
    d_lw.println("0,      /* tp_as_sequence */");
    d_lw.println("0,      /* tp_as_mapping */");
    d_lw.println("0,      /* tp_hash  */");
    d_lw.println("0,      /* tp_call */");
    d_lw.println("0,      /* tp_str */");
    
    if(!fastcall)
      d_lw.println("0,      /* tp_getattro */");
    else
      d_lw.println("_babel_getattro,      /* tp_getattro */");
    
    d_lw.println("0,      /* tp_setattro */");
    d_lw.println("0,      /* tp_as_buffer */");
    d_lw.println("Py_TPFLAGS_DEFAULT, /* tp_flags */");
    d_lw.println("\"\\");
    pythonDocComment();
    d_lw.printlnUnformatted("\", /* tp_doc */");
    d_lw.println("0,      /* tp_traverse */");
    d_lw.println("0,       /* tp_clear */");
    d_lw.println("0,       /* tp_richcompare */");
    d_lw.println("0,       /* tp_weaklistoffset */");
    d_lw.println("0,       /* tp_iter */");
    d_lw.println("0,       /* tp_iternext */");
    d_lw.println("_" + getName() + 
                 "ObjectMethods, /* tp_methods */");
    d_lw.println("0,       /* tp_members */");
    if (d_ext.getRenamedMethods().isEmpty()) {
      d_lw.println("0,       /* tp_getset */");
    }
    else {
      d_lw.println("_" + getName() +
                   "RenamedMethods, /* tp_getset */");
    }
    d_lw.println("0,       /* tp_base */");
    d_lw.println("0,       /* tp_dict */");
    d_lw.println("0,       /* tp_descr_get */");
    d_lw.println("0,       /* tp_descr_set */");
    d_lw.println("0,       /* tp_dictoffset */");
    d_lw.println(id.getFullName().replace('.','_') +
                 "_createCast,   /* tp_init */");
    d_lw.println("0,       /* tp_alloc */");
    d_lw.println("0,       /* tp_new */");
    d_lw.backTab();
    d_lw.println("};");
    d_lw.println();
  }

  /**
   * Write a method signature for a static class method or a normal
   * class method. The argument list is determine by the Python
   * extension standards.  For normal class methods, it includes
   * code to fetch the IOR and test if it is non-<code>NULL</code>.
   *
   * @param m   the method whose signature is to be written.
   */
  private void writeMethodSignature(Method m) {
    d_lw.println("static PyObject *");
    d_lw.print(Python.getStubMethod(d_ext.getSymbolID(),m));      
    if (m.isStatic()){
      d_lw.println("(PyObject *_ignored, PyObject *_args, PyObject *_kwdict) {");
    }
    else {
      d_lw.println("(PyObject *_self, PyObject *_args, PyObject *_kwdict) {");
    }
    d_lw.tab();
    d_lw.println("PyObject *_return_value = NULL;");
    if (!m.isStatic()) {
      SymbolID id = d_ext.getSymbolID();
      d_lw.print(IOR.getObjectName(id));
      d_lw.println(" *_self_ior =");
      d_lw.tab();
      d_lw.print("((");
      d_lw.print(IOR.getObjectName(id));
      d_lw.println(" *)");
      d_lw.print(" sidl_Cast(_self, \"");
      d_lw.print(id.getFullName());
      d_lw.println("\"));");
      d_lw.backTab();
      d_lw.println("if (_self_ior) {");
      d_lw.tab();
    }
  }

  /**
   * Write a type declaration for a variable.
   * 
   * @param t        the type of the variable.
   * @param name     the formal name of the variable.
   * @param mode     the mode (in, inout or out) of the parameter.
   * @exception gov.llnl.babel.backend.CodeGeneration
   *    this is a catch all exception. It can be caused by I/O trouble or
   *    violations of the data type invariants.
   */
  private void declareVariable(Type t,
                               String name,
                               final int mode)
    throws CodeGenerationException
  {
    d_lw.print(IOR.getReturnString(t, d_context));
    d_lw.print(" ");
    d_lw.print(name);
    if (Utilities.isPointer(t)) {
      d_lw.print(" = NULL");
    } else if (t.getDetailedType() == Type.DCOMPLEX) {
      d_lw.print(" = { 0.0, 0.0 }");
    } else if (t.getDetailedType() == Type.FCOMPLEX) {
      d_lw.print(" = { 0.0, 0.0 }");
    } else if (t.getDetailedType() != Type.STRUCT) {
      d_lw.print(" = (" + IOR.getReturnString(t, d_context) + ") 0");
    }
    d_lw.println(";");
  }

  /**
   * Declare variables to hold the incoming and outgoing arguments.
   *
   * @param m   the method whose arguments will be declared.
   * @exception gov.llnl.babel.backend.CodeGeneration
   *    this is a catch all exception. It can be caused by I/O trouble or
   *    violations of the data type invariants.
   */
  private void declareArguments(Method m) 
    throws CodeGenerationException 
  {
    Iterator i = m.getArgumentList().iterator();
    while (i.hasNext()) {
      Argument arg = (Argument)i.next();
      declareVariable(arg.getType(), arg.getFormalName(), arg.getMode());
    }
    if (!m.getThrows().isEmpty()) {
      d_lw.print(IOR.getExceptionFundamentalType());
      d_lw.println("_exception = NULL;");
    }
  }

  private void initStructArgs(Method m)
    throws CodeGenerationException
  {
    Iterator i = m.getArgumentList().iterator();
    while (i.hasNext()) {
      Argument arg = (Argument)i.next();
      if ((Argument.OUT != arg.getMode()) &&
          (Type.STRUCT == arg.getType().getDetailedType())) {
        Symbol sym = Utilities.lookupSymbol(d_context, 
                                            arg.getType().getSymbolID());
        d_lw.println(Python.getStructInit(sym) +
                     "(&" + arg.getFormalName() + ");");
      }
    }
  }

  /**
   * Return <code>true</code> if and only if this method has a Python return
   * value. A method has a return value in Python if it has <code>out</code>
   * or <code>inout</code> parameters or if it has a non-<code>void</code>
   * return type.
   *
   * @param m  the method to interrogate about whether it has
   *           a return value in Python.
   * @return   <code>true</code> if and only if this method has a Python
   *           return value.
   */
  private boolean hasReturn(Method m) {
    if (m.getReturnType().getType() != Type.VOID) {
      return true;
    }
    Iterator i = m.getArgumentList().iterator();
    while (i.hasNext()) {
      if (isOutgoing((Argument)i.next())) {
        return true;
      }
    }
    return false;
  }

  /**
   * Return <code>true</code> if and only if the argument is an 
   * <code>out</code> or <code>inout</code> argument.
   *
   * @param arg  the argument whose direction is interrogated.
   * @return     <code>true</code> means the argument has an
   *             output value.
   */
  private final static boolean isOutgoing(Argument arg) {
    final int mode = arg.getMode();
    return (mode == Argument.OUT) || (mode == Argument.INOUT);
  }

  /**
   * Return <code>true</code> if and only if the argument is an 
   * <code>in</code> or <code>inout</code> argument.
   *
   * @param arg  the argument whose direction is interrogated.
   * @return     <code>true</code> means the argument has an
   *             input value.
   */
  private final static boolean isIncoming(Argument arg) {
    final int mode = arg.getMode();
    return (mode == Argument.IN) || (mode == Argument.INOUT);
  }
  
  /**
   * Write code to declare and initialize a static variable holding
   * a <code>NULL</code> terminated list of parameter names.
   * 
   * @param m    the method whose argument names are to be listed.
   */
  private void listArgumentNames(Method m) {
    Iterator i = m.getArgumentList().iterator();
    d_lw.println("static char *_kwlist[] = {");
    d_lw.tab();
    while (i.hasNext()) {
      Argument a = (Argument)i.next();
      if (isIncoming(a)) {
        d_lw.print("\"");
        d_lw.print(a.getFormalName());
        d_lw.println("\",");
      }
    }
    d_lw.println("NULL");
    d_lw.backTab();
    d_lw.println("};");
  }

  /*
   * Load the static EPV.  If there is a problem, deliberately return
   * a NULL to ensure a python exception is raised.
   */
  private void loadSEPV(Method m)
  {
    if (m.isStatic() && !BabelConfiguration.isSIDLBaseClass(d_ext)) {
      d_lw.println("if (_loadClassImpl()) {");
      d_lw.tab();
      d_lw.println("_sepv = (*_implEPV->getStaticEPV)();");
      d_lw.backTab();
      d_lw.println("} else {");
      d_lw.tab();
      d_lw.println("return NULL;");
      d_lw.backTab();
      d_lw.println("}");
    }
  }

  private void callSuperMethod(Method m, String extra)
  {
    Class parent = ((Class)d_ext).getParentClass();
    if (m.getReturnType().getType() != Type.VOID) {
      d_lw.print("_return = " + extra);
    }
    d_lw.print("(*(((*_implEPV->getSuperEPV)())->");
    d_lw.print(IOR.getVectorEntry(m.getLongMethodName()));
    d_lw.print("))(&(_self_ior->d_" +
               IOR.getSymbolName(parent).toLowerCase() + ")");
    Iterator i = m.getArgumentList().iterator();
    while (i.hasNext()) {
      Argument arg = (Argument)i.next();
      d_lw.print(", ");
      if (isOutgoing(arg) || 
          (arg.getType().getDetailedType() == Type.STRUCT)) {
        d_lw.print("&");
      }
      d_lw.print(arg.getFormalName());
    }
    if (!m.getThrows().isEmpty()) {
      d_lw.print(", ");
      d_lw.print("&_exception");
    }
    d_lw.println(");");
  }


  /**
   * Write code to call the sidl method using the static entry point vector
   * or the entry point vector in the IOR.
   *
   * @param m           the method to call.
   * @param isInterface <code>true</code> if the method is on an interface;
   *                    <code>false</code> if the method is on an object.
   */
  private void callMethod(Method m, boolean isInterface) {
    final boolean checkSuper = (d_ext instanceof Class) &&
      ((Class)d_ext).getOverwrittenClassMethods().contains(m);
    String extra = "";
    boolean addComma = false;
    if (checkSuper) {
      d_lw.println("const int _notSuper = !sidl_IsSuper(_self);");
    }
    Python.leavePython(d_lw);
    if (checkSuper) {
      d_lw.println("if (_notSuper) {");
      d_lw.tab();
    }
    if (m.getReturnType().getType() != Type.VOID) {
      d_lw.print("_return = ");
    }

    //Supid special case, if we're generating the builtin _isLocal, it's !isremote
    if(m.getLongMethodName().compareTo("_isLocal") == 0) {
      m.setMethodName("_isRemote");
      extra = "!";
      d_lw.print(extra);
    }

    if (m.isStatic()) {
      d_lw.print("(*(_sepv->");
    }
    else {
      d_lw.print("(*(_self_ior->d_epv->");
    }
    d_lw.print(IOR.getVectorEntry(m.getLongMethodName()));
    d_lw.print("))(");
    if (!m.isStatic()) {
      if (isInterface) {
        d_lw.print("_self_ior->d_object");
      }
      else {
        d_lw.print("_self_ior");
      }
      addComma = true;
    }
    Iterator i = m.getArgumentList().iterator();
    while (i.hasNext()) {
      Argument arg = (Argument)i.next();
      if (addComma) {
        d_lw.print(", ");
      }
      if (isOutgoing(arg) || 
          (arg.getType().getDetailedType() == Type.STRUCT)) {
        d_lw.print("&");
      }
      d_lw.print(arg.getFormalName());
      addComma = true;
    }
    if (!m.getThrows().isEmpty()) {
      if (addComma) {
        d_lw.print(", ");
      }
      d_lw.print("&_exception");
      addComma = true;
    }
    d_lw.println(");");
    if (checkSuper) {
      d_lw.backTab();
      d_lw.println("}");
      d_lw.println("else {");
      d_lw.tab();
      callSuperMethod(m, extra);
      d_lw.backTab();
      d_lw.println("}");
    }
    Python.resumePython(d_lw);
  }

  /**
   * Write the code to determine which exception was thrown and to create
   * the corresponding Python exception.
   *
   * @param m   a method with throws.
   * @exception gov.llnl.babel.backend.CodeGeneration
   *    this is a catch all exception. It can be caused by I/O trouble or
   *    violations of the data type invariants.
   */
  private void processExceptions(Method m)
    throws CodeGenerationException 
  {
    SymbolTable symtab = d_context.getSymbolTable();
    Object [] exceptions = m.getThrows().toArray();
    Arrays.sort(exceptions, new LevelComparator(symtab));
    int i;

    /* declare exception pointers */
    for(i = 0; i < exceptions.length ; ++i) {
      d_lw.print(IOR.getObjectName((SymbolID)exceptions[i]));
      d_lw.print(" *_ex");
      d_lw.print(Integer.toString(i));
      d_lw.println(";");
    }
    /* test each exception in order */
    for(i = 0; i < exceptions.length; ++i) {
      SymbolID id = (SymbolID)exceptions[i];
      Symbol sym = symtab.lookupSymbol(id);
      if (i > 0) {
        d_lw.print("else ");
      }
      d_lw.print("if ((_ex");
      d_lw.print(Integer.toString(i));
      d_lw.print(" = ");
      d_lw.print("(");
      d_lw.print(IOR.getObjectName(id));
      d_lw.println(" *)");
      d_lw.tab();
      d_lw.print("sidl_PyExceptionCast(_exception, \"");
      d_lw.print(id.getFullName());
      d_lw.print("\")))");
      d_lw.backTab();
      d_lw.println(" {");
      d_lw.tab();
      d_lw.println(IOR.getExceptionFundamentalType() +
                   "throwaway_exception;");
      d_lw.print("PyObject *_obj = ");
      d_lw.print(Python.getExtendableWrapper(sym));
      d_lw.print("(_ex");
      d_lw.print(Integer.toString(i));
      d_lw.println(");");
      d_lw.println("PyObject *_eargs = PyTuple_New(1);");
      d_lw.println("PyTuple_SetItem(_eargs, 0, _obj);");
      d_lw.println("_obj = PyObject_CallObject(" +
                   Python.getExceptionType(sym) + ", _eargs);");
      d_lw.print("PyErr_SetObject(");
      d_lw.print(Python.getExceptionType(sym));
      d_lw.println(", _obj);");
      d_lw.println("Py_XDECREF(_obj);");
      Python.leavePython(d_lw);
      d_lw.println("(*(_exception->d_epv->" +
                   IOR.getVectorEntry("deleteRef") +
                   "))(_exception->d_object, &throwaway_exception);");
      Python.resumePython(d_lw);
      d_lw.println("Py_XDECREF(_eargs);");
      d_lw.backTab();
      d_lw.println("}");
    }
  }

  void cleanOutgoingArrays(Method m )
  {
    boolean first = true;
    Iterator i = m.getArgumentList().iterator();
    while (i.hasNext()) {
      Argument arg = (Argument)i.next();
      if (Argument.IN != arg.getMode()) {
        switch(arg.getType().getDetailedType()) {
        case Type.ARRAY:
          if (first) {
            Python.leavePython(d_lw);
            first = false;
          }
          d_lw.println(Python.getDestroyArray(arg.getType()) +
                       "((struct sidl__array *)" +
                       arg.getFormalName() + ");");
          break;
        case Type.STRING:
          d_lw.print("free((void *)" + arg.getFormalName() + ");");
          break;
        }
      }
    }
    if (!first) {
      Python.resumePython(d_lw);
    }
  }

  /**
   * Write the code to check for exceptions, build the return value and/or
   * prepare a void return.  First check for exceptions if necessary. If
   * there is no exception, either build the return value or prepare to
   * return <code>Py_None</code> (the Python void equivalent).
   *
   * @param m   the method whose return value processing will be written.
   * @exception gov.llnl.babel.backend.CodeGeneration
   *    this is a catch all exception. It can be caused by I/O trouble or
   *    violations of the data type invariants.
   */
  private void processReturnValues(Method m,
                                   TranslateArguments translator)
    throws CodeGenerationException 
  {
    boolean hasThrows = !m.getThrows().isEmpty();
    if (hasThrows) {
      d_lw.println("if (_exception) {");
      d_lw.tab();
      processExceptions(m);
      d_lw.backTab();
      d_lw.println("}");
      d_lw.println("else {");
      d_lw.tab();
    }
    if (hasReturn(m)) {
      d_lw.print("_return_value = ");
      translator.setConvertIncoming(false);
      translator.convertSidlToPython();
      if (Type.STRING == m.getReturnType().getType()) {
        d_lw.println("free((void *)_return);");
      }
      else if (Type.ARRAY == m.getReturnType().getType()) {
        Python.leavePython(d_lw);
        destroyArrayArg(m.getReturnType().getArrayType(), "_return");
        Python.resumePython(d_lw);
      }
      else if (Type.STRUCT == m.getReturnType().getDetailedType()) {
        Symbol sym = Utilities.lookupSymbol(d_context,
                                            m.getReturnType().getSymbolID());
        Python.leavePython(d_lw);
        d_lw.println(Python.getStructDestroy(sym) +
                     "(&_return);");
        Python.resumePython(d_lw);
      }
      cleanOutgoingArrays(m);
    }
    else {
      d_lw.println("_return_value = Py_None;");
      d_lw.println("Py_INCREF(_return_value);");
    }
    if (hasThrows) {
      d_lw.backTab();
      d_lw.println("}");
    }
  }

  /**
   * Write the closing part of method.
   * 
   * @param m   the method whose closing will be written.
   */
  private void writeMethodClosing(Method m) throws CodeGenerationException {
    if (!m.isStatic()) {
      d_lw.println("{");
      d_lw.tab();
      d_lw.println(IOR.getExceptionFundamentalType() +
                   "throwaway_exception;");
      Python.leavePython(d_lw);
      if (d_ext.isInterface()) {
        d_lw.println("(*(_self_ior->d_epv->" +
                     IOR.getVectorEntry("deleteRef") +
                     "))(_self_ior->d_object, &throwaway_exception);");
      }
      else {
        d_lw.println("(*(_self_ior->d_epv->" +
                     IOR.getVectorEntry("deleteRef") +
                     "))(_self_ior, &throwaway_exception);");
      }
      Python.resumePython(d_lw);
      d_lw.backTab();
      d_lw.println("}");
      d_lw.backTab();
      d_lw.println("}");
      d_lw.println("else {");
      d_lw.tab();
      d_lw.println("PyErr_SetString(PyExc_TypeError, ");
      d_lw.tab();
      try {
        d_lw.pushLineBreak(false);
        d_lw.print("\"self pointer is not a ");
        d_lw.print(d_ext.getFullName());
        d_lw.println("\");");
      }
      finally {
        d_lw.popLineBreak();
      }
      d_lw.backTab();
      d_lw.backTab();
      d_lw.println("}");
    }
    //Reset the sepv if we have to (a little hackish)
    if (  m.equals(IOR.getBuiltinMethod(IOR.HOOKS, d_ext.getSymbolID(), 
                                        d_context, true))
       || m.equals(IOR.getBuiltinMethod(IOR.CONTRACTS, d_ext.getSymbolID(), 
                                        d_context, true)) ) 
    {
      if (!BabelConfiguration.isSIDLBaseClass(d_ext)) {
        d_lw.println("_loadClassImpl();");
      }
      d_lw.println("if(_implEPV) {");
      d_lw.tab();
      d_lw.println("_sepv = (*_implEPV->getStaticEPV)();");
      d_lw.backTab();
      d_lw.println("}");
    }
    d_lw.println("return _return_value;");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println();
  }

  /**
   * Declare a variable to hold the method return value.
   * 
   * @exception gov.llnl.babel.backend.CodeGeneration
   *    this is a catch all exception. It can be caused by I/O trouble or
   *    violations of the data type invariants.
   */
  private void declareReturnValue(Method m,
                                  TranslateArguments translator) 
    throws CodeGenerationException 
  {
    if (m.getReturnType().getType() != Type.VOID) {
      declareVariable(m.getReturnType(), "_return", Argument.OUT);
      translator.declareProxy(m.getReturnType(),
                              "_return", Argument.OUT);
    }
  }

  /**
   * Call the array destructor for this argument.
   *
   * @param arrayType  the type of the array.
   * @param argName    the name of the argument to destroy.
   */
  private void destroyArrayArg(Type arrayType,
                               String argName)
  {
    d_lw.print(Python.getDestroyArray(arrayType));
    d_lw.print("((struct sidl__array *)");
    d_lw.print(argName);
    d_lw.println(");");
  }

  /**
   * Perform any clean up activities required.
   *
   * @param m  the method.
   * @exception gov.llnl.babel.backend.CodeGeneration
   *    this is a catch all exception. It can be caused by I/O trouble or
   *    violations of the data type invariants.
   */
  private void cleanUpArguments(Method m)
    throws CodeGenerationException
  {
    boolean first = true;
    Iterator i = m.getArgumentList().iterator();
    while (i.hasNext()) {
      Argument a = (Argument)i.next();
      switch(a.getType().getDetailedType()) {
      case Type.INTERFACE:
      case Type.CLASS:
        if (Argument.IN == a.getMode()) {
          Extendable ext = 
            (Extendable)Utilities.lookupSymbol(d_context,
                                               a.getType().getSymbolID());
          if (first) {
            Python.leavePython(d_lw);
            first = false;
          }
          d_lw.println(Python.getExtendableDeref(ext) +
                       "(" + a.getFormalName() + ");");
        }
        break;
      case Type.ARRAY:
        if (Argument.IN == a.getMode()) {
          if (first) {
            Python.leavePython(d_lw);
            first = false;
          }
          destroyArrayArg(a.getType().getArrayType(),
                          a.getFormalName());
        }
        break;
      case Type.STRUCT:
        Symbol sym = Utilities.lookupSymbol(d_context,
                                            a.getType().getSymbolID());
        if (first) {
          Python.leavePython(d_lw);
          first = false;
        }
        d_lw.println(Python.getStructDestroy(sym) +
                     "(&" + a.getFormalName() + ");");
        break;
      }
    }
    if (!first) {
      Python.resumePython(d_lw);
    }
  }

  /**
   * If any type refers to a generic array, we need to add a
   * reference to sidl.BaseInterface because the routine
   * to create a generic array from a Python value lives
   * in the sidl.BaseInterface module.
   */
  private static boolean addRefsFromType(Set references, Type t,
                                         Context context)
  {
    if (t.isGenericArray()) {
      try {
        Symbol bi = 
          Utilities.lookupSymbol(context,
                                 BabelConfiguration.getBaseInterface());
        references.add(bi.getSymbolID());
        return true;
      }
      catch (CodeGenerationException cge) {
        // should never happen
      }
    }
    return false;
  }

  private static Set getPythonReferences(Method m,
                                         Context context)
  {
    HashSet pyrefs = new HashSet();
    pyrefs.addAll(m.getSymbolReferences());
    /* look for references to a generic array */
    if (!addRefsFromType(pyrefs, m.getReturnType(), context)) {
      Iterator i = m.getArgumentList().iterator();
      while (i.hasNext()) {
        Argument a = (Argument)i.next();
        if (addRefsFromType(pyrefs, a.getType(), context)) break;
      }
    }
    return pyrefs;
  }

  private Set getNonParentRefs(Method m)
  {
    HashSet nonparents = 
      new HashSet(Utilities.convertIdsToSymbols(d_context,
                                                getPythonReferences(m, 
                                                                    d_context)));
    nonparents.removeAll(d_ext.getParents(false));
    nonparents.remove(d_ext);
    return nonparents;
  }

  private void writeImports(Collection imports)
  {
    Object[] importList = imports.toArray();
    Arrays.sort(importList);
    for(int i = 0 ; i < importList.length ; ++i) {
      Symbol sym = (Symbol)importList[i];
      if ((sym instanceof Extendable) || (sym instanceof Struct)) {
        d_lw.print(Python.getExtendableImport(sym));
        d_lw.println("();");
      }
    }
  }

  /**
   * Decide whether or not it's worth to check if a method is
   * implemented natively
   */
  private boolean isFastCallFeasibe(Method m) {
    return d_context.getConfig().getFastCall() &&
      !IOR.isBuiltinMethod(m.getLongMethodName()) &&
      //stupid special case, if we're generating the builtin _isLocal, it's !isremote
      m.getLongMethodName().compareTo("_isLocal") != 0 &&
      //TODO: find out why these functions are not recognized as a builtin
      m.getLongMethodName().compareTo("_dump_stats_static") != 0 && 
      m.getLongMethodName().compareTo("_set_hooks_static") != 0 && 
      m.getLongMethodName().compareTo("_set_contracts_static") != 0 
      ;
  }

  /**
   * Write a function in C that will be exposed in Python to execute a sidl
   * method (static or normal). This is the overall manager for writing
   * the method.
   * 
   * @param m  the method.
   * @param isInterface <code>true</code> if the method is on an interface;
   *                    <code>false</code> if the method is on an object.
   * @exception gov.llnl.babel.backend.CodeGeneration
   *    this is a catch all exception. It can be caused by I/O trouble or
   *    violations of the data type invariants.
   */
  private void convertMethod(Method m, boolean isInterface) 
    throws CodeGenerationException 
  {
    TranslateArguments 
      translator  = new TranslateArguments(d_lw, m, d_context, true, true);
    writeMethodSignature(m);
    declareArguments(m);
    translator.declareProxies();
    listArgumentNames(m);
    translator.setConvertIncoming(true);
    d_lw.println("int _okay;");
    writeImports(getNonParentRefs(m));
    initStructArgs(m);
    d_lw.print("_okay = ");
    translator.convertPythonToSidl("_args", "_kwdict", "_kwlist");
    d_lw.println("if (_okay) {");
    d_lw.tab();
    declareReturnValue(m, translator);
    loadSEPV(m);
    translator.convertIncomingArguments(false);
    callMethod(m, isInterface);
    translator.setConvertIncoming(false);
    translator.convertOutgoingArguments(true);
    processReturnValues(m, translator);
    cleanUpArguments(m);
    d_lw.backTab();
    d_lw.println("}");
    writeMethodClosing(m);
  }

  private void connectMethod() throws CodeGenerationException 
  {
    Method m = Python.connectRemoteMethod(d_ext, d_context);
    TranslateArguments 
      translator  = new TranslateArguments(d_lw, m, d_context, true, true);
    String cName = C.getFullMethodName(d_ext.getSymbolID(), "_remoteConnect");

    d_lw.printlnUnformatted("#ifdef WITH_RMI");
    d_lw.println();

    writeMethodSignature(m);
    declareArguments(m);
    translator.declareProxies();
    listArgumentNames(m);
    translator.setConvertIncoming(true);
    d_lw.println("int _okay;");
    writeImports(getNonParentRefs(m));
    initStructArgs(m);
    d_lw.print("_okay = ");
    translator.convertPythonToSidl("_args", "_kwdict", "_kwlist");
    d_lw.println("if (_okay) {");
    d_lw.tab();
    declareReturnValue(m, translator);
    translator.convertIncomingArguments(false);
    Python.leavePython(d_lw);
    d_lw.println("self = "+cName+"(url,1,&_exception);");
    Python.resumePython(d_lw);
    translator.setConvertIncoming(false);
    translator.convertOutgoingArguments(true);
    processReturnValues(m, translator);
    cleanUpArguments(m);
    d_lw.backTab();
    d_lw.println("}");
    writeMethodClosing(m);
    
    d_lw.println();
    d_lw.printlnUnformatted("#endif /*WITH_RMI*/");
  }

  /**
   * Return the list of methods only defined first in this extendable. This
   * excludes any methods that were defined in a parent class or interface.
   * 
   * @param includeStatics if <code>true</code> the return value has
   *                       static and non-static methods.
   */
  private Collection pythonMethodList(boolean includeStatics) throws CodeGenerationException
  {
    SymbolID id = d_ext.getSymbolID();
    HashSet localMethods = new HashSet
      (includeStatics ? d_ext.getMethods(false) 
       : d_ext.getNonstaticMethods(false));
    if (!localMethods.isEmpty()) {
      Iterator i = d_ext.getParentInterfaces(false).iterator();
      while (!localMethods.isEmpty() && i.hasNext()) {
        localMethods.removeAll(((Extendable)i.next()).
                               getNonstaticMethods(true));
      }
    }

    if (!d_context.getConfig().getSkipRMI()) {
      localMethods.add(IOR.getBuiltinMethod(IOR.EXEC,id, d_context,false));
      localMethods.add(IOR.getBuiltinMethod(IOR.GETURL,id, d_context,false));
      localMethods.add(IOR.getBuiltinMethod(IOR.ISREMOTE,id, d_context,false));

      Method islocal = IOR.getBuiltinMethod(IOR.ISREMOTE,id, d_context,false);
      islocal.setMethodName("_isLocal");
      String[] comments = new String[1];
      comments[0] = "TRUE if this object is local, false if remote";
      islocal.setComment(new Comment(comments));
      localMethods.add(islocal);
    }

    localMethods.add(IOR.getBuiltinMethod(IOR.HOOKS,id,d_context,false));

    boolean genContractBuiltins = IOR.generateContractBuiltins(d_ext, 
                                                               d_context);
    if (genContractBuiltins) {
      localMethods.add(IOR.getBuiltinMethod(IOR.CONTRACTS, id, d_context, 
                                            false));
      localMethods.add(IOR.getBuiltinMethod(IOR.DUMP_STATS, id, d_context, 
                                            false));
    }
    if(d_ext.hasStaticMethod(false) && includeStatics) {
      localMethods.add(IOR.getBuiltinMethod(IOR.HOOKS,id,d_context,true));
      if (genContractBuiltins) {
        localMethods.add(IOR.getBuiltinMethod(IOR.CONTRACTS, id, d_context, 
                                              true));
        localMethods.add(IOR.getBuiltinMethod(IOR.DUMP_STATS, id, d_context, 
                                              true));
      }
    }

    return localMethods;
  }

  /**
   * Write the functions in C that will be exposed in Python to execute the
   * sidl methods (static and/or normal). This converts each method in 
   * turn.
   * 
   * @param isInterface <code>true</code> if the method is on an interface;
   *                    <code>false</code> if the method is on an object.
   * @exception gov.llnl.babel.backend.CodeGeneration
   *    this is a catch all exception. It can be caused by I/O trouble or
   *    violations of the data type invariants.
   */
  private void convertMethods(boolean isInterface)
    throws CodeGenerationException 
  {
    Iterator i = Utilities.sort(pythonMethodList(true)).iterator();
    while (i.hasNext()) {
      Method method = (Method)i.next();
      convertMethod(method, isInterface);
      if (( method.getCommunicationModifier() == Method.NONBLOCKING ) &&
          !d_context.getConfig().getSkipRMI()) { 
          Method send = method.spawnNonblockingSend();
          convertMethod(send, isInterface);
 
          Method recv = method.spawnNonblockingRecv();
          convertMethod(recv, isInterface);
 
        }

    }
  }

  /**
   * Create a Python doc comment for this method.
   *
   * @parameter m the method to document
   */
  private void documentMethod(Method m)
  {
    d_lw.printlnUnformatted("\"\\");
    printMethodSignature(m);
    if (m.getComment() != null && 
        m.getComment().getComment() != null) {
      d_lw.printlnUnformatted("\\n\\");
      copyComment(m.getComment());
    }
    d_lw.printlnUnformatted("\"");
  }

  private String indentString(int width)
  {
    StringBuffer buf = new StringBuffer(width);
    while (width-- > 0){
      buf.append(' ');
    }
    return buf.toString();
  }

  private void writeArgumentList(Iterator args,
                                 final int excluding,
                                 final int indent,
                                 boolean notFirst)
  {
    final String indStr = indentString(indent);
    while (args.hasNext()) {
      Argument a = (Argument)args.next();
      if (excluding != a.getMode()) {
        if (notFirst) {
          d_lw.printlnUnformatted(",\\n\\");
          d_lw.printUnformatted(indStr);
        }
        else {
          notFirst = true;
        }
        d_lw.printUnformatted(a.getArgumentString());
      }
    }
    d_lw.printlnUnformatted(")\\n\\");
  }

  private void printMethodSignature(Method m)
  {
    final String name = m.getLongMethodName();
    d_lw.printUnformatted(name + "(");
    writeArgumentList(m.getArgumentList().iterator(), Argument.OUT, 
                      name.length() + 1, false);
    d_lw.printlnUnformatted("RETURNS\\n\\");
    if (hasReturn(m)){ 
      d_lw.printUnformatted("   (");
      if (m.getReturnType().getType() != Type.VOID) {
        d_lw.printUnformatted(m.getReturnType().getTypeString() +
                              " _return");
      }
      writeArgumentList(m.getArgumentList().iterator(), Argument.IN, 
                        4, m.getReturnType().getType() != Type.VOID);
    }
    else {
      d_lw.printlnUnformatted("    None\\n\\");
    }
    if (!m.getThrows().isEmpty()) {
      Iterator i = m.getThrows().iterator();
      d_lw.printlnUnformatted("RAISES\\n\\");
      while(i.hasNext()) {
        SymbolID id = (SymbolID)i.next();
        d_lw.printUnformatted("    ");
        d_lw.printUnformatted(id.getFullName());
        d_lw.printlnUnformatted("\\n\\");
      }
    }
  }

  /**
   * Create a Python virtual function table for a set of methods.
   *
   * @param i              the set of methods to include in the
   *                       virtual function table.
   */
  private void makeVirtualTable(Iterator i) throws CodeGenerationException {
    SymbolID id = d_ext.getSymbolID();
    while (i.hasNext()) {
      Method m = (Method)i.next();
      makeVirtualTableEntry(id, m);
      if ( (m.getCommunicationModifier() == Method.NONBLOCKING ) &&
           !d_context.getConfig().getSkipRMI()) { 
          Method send = m.spawnNonblockingSend();
          makeVirtualTableEntry(id, send);
 
          Method recv = m.spawnNonblockingRecv();
          makeVirtualTableEntry(id, recv);
        }
    }
  }

  /**
   * Create a Python virtual function table entry for a method
   *
   * @param m              the the method whose entry should be made.
   */
  private void makeVirtualTableEntry(SymbolID id, Method m) {
    d_lw.print("{ \"");
    d_lw.print(m.getLongMethodName());
    d_lw.print("\", (PyCFunction)");
    d_lw.print(Python.getStubMethod(id, m));
    d_lw.println(",");
    d_lw.println("(METH_VARARGS | METH_KEYWORDS),");
    documentMethod(m);
    d_lw.println(" },");
    
  }


  private void generateConnectTableEntry() {
    SymbolID id = d_ext.getSymbolID();
    Method m = Python.connectRemoteMethod(d_ext, d_context);

    d_lw.println();
    d_lw.printlnUnformatted("#ifdef WITH_RMI");
    d_lw.println();
    d_lw.print("{ \"");
    d_lw.print(m.getLongMethodName());
    d_lw.print("\", (PyCFunction)");
    d_lw.print(Python.getStubMethod(id, m));
    d_lw.println(",");
    d_lw.println("(METH_VARARGS | METH_KEYWORDS),");
    documentMethod(m);
    d_lw.println(" },");
    d_lw.println();
    d_lw.printlnUnformatted("#endif /*WITH_RMI*/");
    d_lw.println();
  }

  private void generateBuiltinEntry(int iorType) throws CodeGenerationException{
    SymbolID id = d_ext.getSymbolID();
    Method   m  = IOR.getBuiltinMethod(iorType, id, d_context, true);
    makeVirtualTableEntry(id, m);
  }


  /**
   * Write the static method virtual function table for the Python module.
   * This table is needed to initialize the Python C extension module.
   * It includes the cast/create function and any static class methods
   * from the extendable.
   */
  private void pythonStaticVirtualTable() throws CodeGenerationException {
    d_lw.print("static PyMethodDef _");
    d_lw.print(getName());
    d_lw.println("ModuleMethods[] = {");
    d_lw.tab();
    makeVirtualTable(Utilities.sort(d_ext.getStaticMethods(false)).iterator());
    if (!d_context.getConfig().getSkipRMI()) {
      generateConnectTableEntry();
    }
    if (d_ext.hasStaticMethod(false)) {
      generateBuiltinEntry(IOR.HOOKS);
      if (IOR.generateContractBuiltins(d_ext, d_context)) {
        generateBuiltinEntry(IOR.CONTRACTS);
        generateBuiltinEntry(IOR.DUMP_STATS);
      }
    }
    d_lw.println("{ NULL, NULL }");
    d_lw.backTab();
    d_lw.println("};");
    d_lw.println();
  }

  /**
   * Write the virtual function table for the Python extension type.
   * This table is needed when creating an instance of the Python
   * object that wraps an IOR.
   */
  private void pythonObjectVirtualTable() throws CodeGenerationException {
    d_lw.print("static PyMethodDef _");
    d_lw.print(getName());
    d_lw.println("ObjectMethods[] = {");
    d_lw.tab();
    makeVirtualTable(Utilities.sort(pythonMethodList(false))
                                         .iterator());
    d_lw.println("{ NULL, NULL }");
    d_lw.backTab();
    d_lw.println("};");
    d_lw.println();
  }

  private Set renamedMethods()
  {
    Set methods = d_ext.getRenamedMethods();
    HashSet methodNames = new HashSet();
    if (methods != null) {
      Iterator i = methods.iterator();
      while (i.hasNext()) {
        Method m = (Method)i.next();
        methodNames.add(m.getLongMethodName());
      }
    }
    return methodNames;
  }

  private void writeGenericGetSet()
  {
    d_lw.println("static int");
    d_lw.println("_setNonexistent(PyObject *self, PyObject *value, void *closure) {");
    d_lw.tab();
    d_lw.println("PyErr_SetString(PyExc_AttributeError,");
    d_lw.tab();
    try {
      d_lw.pushLineBreak(false);
      d_lw.println("\"This method has been renamed.\");");
    }
    finally {
      d_lw.popLineBreak();
    }
    d_lw.println("return -1;");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println();
    d_lw.println("static PyObject *");
    d_lw.println("_getNonexistent(PyObject *self, void *closure) {");
    d_lw.tab();
    d_lw.println("PyErr_SetString(PyExc_AttributeError,");
    d_lw.tab();
    try {
      d_lw.pushLineBreak(false);
      d_lw.println("\"This method has been renamed.\");");
    }
    finally {
      d_lw.popLineBreak();
    }
    d_lw.println("return 0;");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println();
  }

  /**
   * Write the get/set structures for renamed methods.
   */
  private void overwriteRenamedMethods() 
    throws CodeGenerationException
  {
    Set names = renamedMethods();
    if (!names.isEmpty()) {
      writeGenericGetSet();
      d_lw.print("static PyGetSetDef _");
      d_lw.print(getName());
      d_lw.println("RenamedMethods[] = {");
      d_lw.tab();
      Iterator i = Utilities.sort(names).iterator();
      while (i.hasNext()) {
        String name = (String)i.next();
        d_lw.println("{");
        d_lw.tab();
        d_lw.println("\"" + name + "\",");
        d_lw.println("(getter)_getNonexistent,");
        d_lw.println("(setter)_setNonexistent,");
        d_lw.println("\"renamed method\", NULL},");
        d_lw.backTab();
      }
      d_lw.println("{ NULL } /* Sentinel */");
      d_lw.backTab();
      d_lw.println("};");
      d_lw.println();
    }
  }

  /**
   * Write a function to "wrap" an IOR inside a Python C extension type.
   * This create a Python object implemented in C to expose the sidl
   * object/interface to Python clients.  This function is part of the
   * external API of the Python extension module.
   */
  private void wrapperMethod() {
    SymbolID id = d_ext.getSymbolID();
    String wrapper = Python.getExtendableWrapper(d_ext);
    d_lw.print(wrapper);
    d_lw.println("_RETURN");
    d_lw.print(wrapper);
    d_lw.print(" ");
    d_lw.print(wrapper);
    d_lw.println("_PROTO {");
    d_lw.tab();
    d_lw.println("PyObject *result;");
    d_lw.println("if (sidlobj) {");
    d_lw.tab();
    d_lw.println("result = _" +
                 id.getFullName().replace('.','_')
                 + "Type.tp_new(&_" +
                 id.getFullName().replace('.','_') + "Type, NULL, NULL);");
    d_lw.println("if (result) {");
    d_lw.tab();
    d_lw.println("if (sidl_Object_Init(");
    d_lw.tab();
    d_lw.println("(SPObject *)result,");
    d_lw.println("(" + IOR.getInterfaceType()+  ")(sidlobj" +
                 (d_ext.isInterface() ? "->d_object)," : "),"));
    d_lw.println("sidl_PyStealRef))");
    d_lw.backTab();
    d_lw.println("{");
    d_lw.tab();
    d_lw.println("Py_DECREF(result);");
    d_lw.println("result = NULL;");
    d_lw.backTab();
    d_lw.println("}");
    
    d_lw.backTab();
    d_lw.println("}");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println("else {");
    d_lw.tab();
    d_lw.println("result = Py_None;");
    d_lw.println("Py_INCREF(result);");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println("return result;");
    
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println();
  }

  /**
   * Write a function to call delete reference on an extendable of this
   * type.
   */
  private void derefMethod()
  {
    final String deref = Python.getExtendableDeref(d_ext);
    d_lw.print(deref);
    d_lw.println("_RETURN");
    d_lw.print(deref);
    d_lw.print(" ");
    d_lw.print(deref);
    d_lw.println("_PROTO {");
    d_lw.tab();
    d_lw.println("if (sidlobj) {");
    d_lw.tab();
    d_lw.println(IOR.getExceptionFundamentalType() +
                 "throwaway_exception;");
    if (d_ext.isInterface()){
      d_lw.println("(*(sidlobj->d_epv->" + 
                   IOR.getVectorEntry("deleteRef") +
                   "))(sidlobj->d_object, &throwaway_exception);");
    }
    else {
      d_lw.println("(*(sidlobj->d_epv->" + 
                   IOR.getVectorEntry("deleteRef") +
                   "))(sidlobj, &throwaway_exception);");
    }
    d_lw.backTab();
    d_lw.println("}");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println();
  }

  /**
   * Write a function to "wrap" an IOR inside a Python C extension type
   * with a weak reference. This create a Python object implemented in C
   * to expose the sidl object/interface to Python clients.  This function
   * is part of the external API of the Python extension module.
   */
  private void borrowMethod() {
    final SymbolID id = d_ext.getSymbolID();
    final String borrow = Python.getExtendableBorrow(d_ext);
    d_lw.print(borrow);
    d_lw.println("_RETURN");
    d_lw.print(borrow);
    d_lw.print(" ");
    d_lw.print(borrow);
    d_lw.println("_PROTO {");
    d_lw.tab();
    d_lw.println("PyObject *result;");
    d_lw.println("if (sidlobj) {");
    d_lw.tab();
    d_lw.println("result = _" +
                 id.getFullName().replace('.','_')
                 + "Type.tp_new(&_" +
                 id.getFullName().replace('.','_') + "Type, NULL, NULL);");
    d_lw.println("if (result) {");
    d_lw.tab();
    d_lw.println("if (sidl_Object_Init(");
    d_lw.tab();
    d_lw.println("(SPObject *)result,");
    d_lw.println("(" + IOR.getInterfaceType() + ")(sidlobj" +
                 (d_ext.isInterface() ? "->d_object)," : "),"));
    d_lw.println("sidl_PyWeakRef))");
    d_lw.backTab();
    d_lw.println("{");
    d_lw.tab();
    d_lw.println("Py_DECREF(result);");
    d_lw.println("result = NULL;");
    d_lw.backTab();
    d_lw.println("}");

    d_lw.backTab();
    d_lw.println("}");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println("else {");
    d_lw.tab();
    d_lw.println("result = Py_None;");
    d_lw.println("Py_INCREF(result);");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println("return result;");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println();
  }

  /**
   * Write a function to "wrap" an IOR inside a Python C extension type
   * with a new reference. This create a Python object implemented in C
   * to expose the sidl object/interface to Python clients.  This function
   * is part of the external API of the Python extension module.
   */
  private void newRefMethod() {
    final SymbolID id = d_ext.getSymbolID();
    final String newRef = Python.getExtendableNewRef(d_ext);
    d_lw.print(newRef);
    d_lw.println("_RETURN");
    d_lw.print(newRef);
    d_lw.print(" ");
    d_lw.print(newRef);
    d_lw.println("_PROTO {");
    d_lw.tab();
    d_lw.println("PyObject *result;");
    d_lw.println("if (sidlobj) {");
    d_lw.tab();
    d_lw.println("result = _" +
                 id.getFullName().replace('.','_')
                 + "Type.tp_new(&_" +
                 id.getFullName().replace('.','_') + "Type, NULL, NULL);");
    d_lw.println("if (result) {");
    d_lw.tab();
    d_lw.println("if (sidl_Object_Init(");
    d_lw.tab();
    d_lw.println("(SPObject *)result,");
    d_lw.println("(" + IOR.getInterfaceType() + ")(sidlobj" +
                 (d_ext.isInterface() ? "->d_object)," : "),"));
    d_lw.println("sidl_PyNewRef))");
    d_lw.backTab();
    d_lw.println("{");
    d_lw.tab();
    d_lw.println("Py_DECREF(result);");
    d_lw.println("result = NULL;");
    d_lw.backTab();
    d_lw.println("}");

    d_lw.backTab();
    d_lw.println("}");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println("else {");
    d_lw.tab();
    d_lw.println("result = Py_None;");
    d_lw.println("Py_INCREF(result);");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println("return result;");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println();
  }

  /**
   * Write a function to call addRef on a sidl class or interface.
   * This function is part of the external API of the Python extension module.
   */
  private void addRefMethod() {
    final String addRef = Python.getExtendableAddRef(d_ext);
    d_lw.print(addRef);
    d_lw.println("_RETURN");
    d_lw.print(addRef);
    d_lw.print(" ");
    d_lw.print(addRef);
    d_lw.println("_PROTO {");
    d_lw.tab();
    d_lw.println("if (sidlobj) {");
    d_lw.tab();
    d_lw.println(IOR.getExceptionFundamentalType() +
                 "throwaway_exception;");
    if (d_ext.isInterface()){
      d_lw.println("(*(sidlobj->d_epv->" +
                   IOR.getVectorEntry("addRef") + "))(sidlobj->d_object, &throwaway_exception);");
    }
    else {
      d_lw.println("(*(sidlobj->d_epv->" +
                   IOR.getVectorEntry("addRef") + "))(sidlobj, &throwaway_exception);");
    }
    d_lw.backTab();
    d_lw.println("}");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println();
  }

  /**
   * Write a function to call addRef on a sidl class or interface.
   * This function is part of the external API of the Python extension module.
   */
  private void typeMethod() {
    final SymbolID id = d_ext.getSymbolID();
    final String typeMethod = Python.getExtendableType(d_ext);
    d_lw.print(typeMethod);
    d_lw.println("_RETURN");
    d_lw.print(typeMethod);
    d_lw.print(" ");
    d_lw.print(typeMethod);
    d_lw.println("_PROTO {");
    d_lw.tab();
    d_lw.println("Py_INCREF(&_" + id.getFullName().replace('.','_')
                 + "Type);");
    d_lw.println("return &_" +id.getFullName().replace('.','_')
                 + "Type;");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println();
  }

  /**
   * Write a function to try to convert a <code>PyObject*</code> into an IOR
   * pointer for this extendable.  This function is part of the external API
   * of the Python extension module.
   */
  private void converterMethod() {
    SymbolID id = d_ext.getSymbolID();
    String fullname = id.getFullName();
    String convert = Python.getExtendableConverter(d_ext);
    d_lw.print(convert);
    d_lw.println("_RETURN");
    d_lw.print(convert);
    d_lw.print(" ");
    d_lw.print(convert);
    d_lw.println("_PROTO {");
    d_lw.tab();
    d_lw.println("if (*sidlobj) {");
    d_lw.tab();
    d_lw.println("struct sidl_BaseInterface__object *throwaway_exception;");
    Python.leavePython(d_lw);
    d_lw.println("(*((*sidlobj)->d_epv->f_deleteRef))((*sidlobj)" 
                 + (d_ext.isInterface() ? "->d_object" : "") +
                 ", &throwaway_exception);");
    Python.resumePython(d_lw);
    d_lw.backTab();
    d_lw.println("}");
    d_lw.print("*sidlobj = sidl_Cast(obj, \"");
    d_lw.print(fullname);
    d_lw.println("\");");
    d_lw.println("if ((!(*sidlobj)) && (obj != Py_None)) {");
    d_lw.tab();
    d_lw.println("PyErr_SetString(PyExc_TypeError, ");
    d_lw.tab();
    d_lw.print("\"argument is not a(n) ");
    d_lw.print(fullname);
    d_lw.println("\");");
    d_lw.backTab();
    d_lw.println("return 0;");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println("return 1;");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println();
  }

  private boolean hasStruct() {
    Iterator i = Utilities.convertIdsToSymbols(d_context,
                                               d_ext.getSymbolReferences()).
      iterator();
    while (i.hasNext()) {
      Symbol sym = (Symbol)i.next();
      if (Symbol.STRUCT == sym.getSymbolType()) return true;
    }
    return false;
  }

  private void generateLangSpecificInit() throws CodeGenerationException {
    ArrayList dependencies = Utilities.sort(d_ext.getObjectDependencies());

    if (!d_context.getConfig().getSkipRMI()) {
      d_lw.writeComment("lang_inited is a boolean value showing if "+
                        "we have already imported all the nescessary modules",false);
      d_lw.println("static int lang_inited = 0;");  
      d_lw.println();
      d_lw.println("static void _loadDependentMods(void) {");
      d_lw.tab();
      d_lw.println("PyGILState_STATE _gstate;");
      d_lw.printlnUnformatted("#if (PY_VERSION_HEX >= 0x02040000)");
      d_lw.println("_gstate = PyGILState_Ensure();");
      d_lw.println("sidl_Python_LogGILEnsure(__func__, __FILE__, __LINE__, (int)_gstate);");
      d_lw.printlnUnformatted("#endif /* Python 2.4 or later */");
      
      for (Iterator i = dependencies.iterator(); i.hasNext(); ) {
        SymbolID destination_id = (SymbolID) i.next();
        d_lw.println(Python.getImport(destination_id.getFullName())+"();");
      }
      if(!d_context.getConfig().getSkipRMI()) {
        d_lw.println(Python.getImport("sidl.rmi.Ticket")+"();");
        if (hasStruct()) {
          d_lw.println(Python.getImport("sidl.rmi.Invocation")+"();");
        }
      }
      d_lw.printlnUnformatted("#if (PY_VERSION_HEX >= 0x02040000)");
      d_lw.println("PyGILState_Release(_gstate);");
      d_lw.println("sidl_Python_LogGILRelease(__func__, __FILE__, __LINE__, (int)_gstate);");
      d_lw.printlnUnformatted("#endif /* Python 2.4 or later */");
      d_lw.backTab();
      d_lw.println("}");
      d_lw.println();
      try {
        d_lw.pushLineBreak(false);
        d_lw.println("#define "+RMI.LangSpecificInit()+" if(!lang_inited) { \\");
        d_lw.tab();
        d_lw.println("lang_inited = 1; \\");
        d_lw.println("_loadDependentMods(); \\");
        d_lw.backTab();
        d_lw.println("}");
      }
      finally {
        d_lw.popLineBreak();
      }
    }
  }


  private void convertAndStore() {
    final SymbolID id = d_ext.getSymbolID();
    d_lw.println("static int");
    d_lw.println
      ("_convertPython(void *sidlarray, const int *ind, PyObject *pyobj)");
    d_lw.println("{");
    d_lw.tab();
    d_lw.print(IOR.getObjectName(id));
    d_lw.println(" *sidlobj = NULL;");
    d_lw.print("if (");
    d_lw.print(Python.getExtendableConverter(d_ext));
    d_lw.println("(pyobj, &sidlobj)) {");
    d_lw.tab();
    d_lw.print("sidl_interface__array_set((struct sidl_interface__array *)");
    d_lw.println("sidlarray,");
    d_lw.println("ind, (struct sidl_BaseInterface__object *)sidlobj);");
    d_lw.println("if (sidlobj) {");
    d_lw.tab();
    d_lw.println("struct sidl_BaseInterface__object *asInt = (struct sidl_BaseInterface__object *)sidlobj;");
    d_lw.println(IOR.getExceptionFundamentalType() +
                 "throwaway_exception;");
    Python.leavePython(d_lw);
    d_lw.println("(*asInt->d_epv->f_deleteRef)(asInt->d_object, &throwaway_exception);");
    Python.resumePython(d_lw);
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println("return FALSE;");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println("return TRUE;");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println();
  }

  private void convertPythonArray() {
    final SymbolID id = d_ext.getSymbolID();
    final String convert = 
      Python.getBorrowArrayFromPython(new Type(id, d_context));
    d_lw.print(convert);
    d_lw.println("_RETURN");
    d_lw.print(convert);
    d_lw.print(" ");
    d_lw.print(convert);
    d_lw.println("_PROTO {");
    d_lw.tab();
    d_lw.println("int result = 0;");
    d_lw.println("if (*sidlarray) {");
    d_lw.tab();
    Python.leavePython(d_lw);
    d_lw.println("sidl__array_deleteRef((struct sidl__array *)*sidlarray);");
    Python.resumePython(d_lw);
    d_lw.println("*sidlarray = NULL;");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println("if (obj == Py_None) {");
    d_lw.tab();
    d_lw.println("result = TRUE;");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println("else {");
    d_lw.tab();
    d_lw.println
      ("PyObject *pya = PyArray_FromObject(obj, PyArray_OBJECT, 0, 0);");
    d_lw.println("if (pya) {");
    d_lw.tab();
    d_lw.println
      ("if (PyArray_OBJECT == ((PyArrayObject *)pya)->descr->type_num) {");
    d_lw.tab();
    d_lw.println("int dimen, lower[SIDL_MAX_ARRAY_DIMENSION],");
    d_lw.tab();
    d_lw.println("upper[SIDL_MAX_ARRAY_DIMENSION],");
    d_lw.println("stride[SIDL_MAX_ARRAY_DIMENSION];");
    d_lw.backTab();
    d_lw.println("if (sidl_array__extract_python_info");
    d_lw.tab();
    d_lw.println("(pya, &dimen, lower, upper, stride))");
    d_lw.backTab();
    d_lw.println("{");
    d_lw.tab();
    d_lw.print("*sidlarray = ("+ IOR.getArrayName(id) + "*)");
    d_lw.tab();
    d_lw.println("sidl_interface__array_createRow");
    d_lw.println("(dimen, lower, upper);");
    d_lw.backTab();
    d_lw.println("result = sidl_array__convert_python");
    d_lw.tab();
    d_lw.println("(pya, dimen, *sidlarray, _convertPython);");
    d_lw.backTab();
    d_lw.println("if (*sidlarray && !result) {");
    d_lw.tab();
    Python.leavePython(d_lw);
    d_lw.println("sidl_interface__array_deleteRef(");
    d_lw.tab();
    d_lw.println("(struct  sidl_interface__array *)*sidlarray);");
    d_lw.backTab();
    Python.resumePython(d_lw);
    d_lw.println("*sidlarray = NULL;");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println("Py_DECREF(pya);");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println("return result;");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println();
  }

  private void getAndConvert() {
    final SymbolID id = d_ext.getSymbolID();
    d_lw.println("static int");
    d_lw.println
      ("_convertSIDL(void *sidlarray, const int *ind, PyObject **dest)");
    d_lw.println("{");
    d_lw.tab();
    d_lw.print(IOR.getObjectName(id));
    d_lw.println(" *sidlobj = (" + IOR.getObjectName(id) + "*)");
    d_lw.println("sidl_interface__array_get((struct sidl_interface__array *)");
    d_lw.tab();
    d_lw.println("sidlarray, ind);");
    d_lw.backTab();
    d_lw.print("*dest = ");
    d_lw.print(Python.getExtendableWrapper(d_ext));
    d_lw.println("(sidlobj);");
    d_lw.println("return (*dest == NULL);");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println();
  }

  private void convertSIDLArray() {
    final SymbolID id = d_ext.getSymbolID();
    final String convert = 
      Python.getBorrowArrayFromSIDL(new Type(id, d_context));
    final String maxArray = 
      Integer.toString(BabelConfiguration.getMaximumArray());
    d_lw.println(convert + "_RETURN");
    d_lw.println(convert + " " + convert + "_PROTO {");
    d_lw.tab();
    d_lw.println("PyObject *pya = NULL;");
    d_lw.println("if (sidlarray) {");
    d_lw.tab();
    d_lw.println("const int dimen = sidlArrayDim(sidlarray);");
    d_lw.printlnUnformatted("#ifdef SIDL_HAVE_NUMPY");
    d_lw.println("npy_intp numelem[" + maxArray + "];");
    d_lw.printlnUnformatted("#else");
    d_lw.println("int numelem[" + maxArray + "];");
    d_lw.printlnUnformatted("#endif");
    d_lw.println("int32_t start[" + maxArray + "];");
    d_lw.println("int32_t s_numelem[" + maxArray + "];");
    d_lw.printlnUnformatted("#if SIZEOF_INT != 4");
    d_lw.println("int lower[" + maxArray + "];");
    d_lw.println("int upper[" + maxArray + "];");
    d_lw.printlnUnformatted("#else");
    d_lw.println("int * const lower = (int *)(sidlarray->d_lower);");
    d_lw.println("int * const upper = (int *)(sidlarray->d_upper);");
    d_lw.printlnUnformatted("#endif");
    d_lw.println("int i;");
    d_lw.println("for(i = 0; i < dimen; ++i) {");
    d_lw.tab();
    d_lw.println("start[i] = sidlLower(sidlarray, i);");
    d_lw.printlnUnformatted("#if SIZEOF_INT != 4");
    d_lw.println("lower[i] = sidlLower(sidlarray, i);");
    d_lw.println("upper[i] = sidlUpper(sidlarray, i);");
    d_lw.printlnUnformatted("#endif");
    d_lw.println("s_numelem[i] = sidlLength(sidlarray, i);");
    d_lw.println("numelem[i] = 1 + upper[i] - lower[i];");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.printlnUnformatted("#ifdef SIDL_HAVE_NUMPY");
    d_lw.println("pya = PyArray_SimpleNew(dimen, numelem, PyArray_OBJECT);");
    d_lw.printlnUnformatted("#else");
    d_lw.printlnUnformatted("#ifdef SIDL_HAVE_NUMERIC_PYTHON");
    d_lw.println("pya = PyArray_FromDims(dimen, numelem, PyArray_OBJECT);");
    d_lw.printlnUnformatted("#else");
    d_lw.printlnUnformatted("#error Neither NumPy nor Numeric installed");
    d_lw.printlnUnformatted("#endif");
    d_lw.printlnUnformatted("#endif");
    d_lw.println("if (pya) {");
    d_lw.tab();
    d_lw.println("if (!sidl_array__convert_sidl(pya, dimen, start,");
    d_lw.tab();
    d_lw.println("sidlarray->d_upper,");
    d_lw.println("s_numelem, sidlarray, _convertSIDL))");
    d_lw.backTab();
    d_lw.println("{");
    d_lw.tab();
    d_lw.println("Py_DECREF(pya);");
    d_lw.println("pya = NULL;");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println("else {");
    d_lw.tab();
    d_lw.println("Py_INCREF(Py_None);");
    d_lw.println("pya = Py_None;");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println("return pya;");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println();
  }

  private void convertGenericSIDL()
  {
    final String convert = Python.getBorrowArrayFromSIDL(null);
    d_lw.println(convert + "_RETURN");
    d_lw.println(convert + " " + convert + "_PROTO {");
    d_lw.tab();
    d_lw.println("if (sidlarray &&");
    d_lw.tab();
    d_lw.println("(sidl_interface_array == sidl__array_type(sidlarray))) {");
    d_lw.println("return " + 
                 Python.getBorrowArrayFromSIDL
                 (new Type(d_ext.getSymbolID(), d_context))
                 + "(sidlarray);");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println("else {");
    d_lw.tab();
    d_lw.println("return sidl_python_borrow_array(sidlarray);");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.backTab();
    d_lw.println("}");
  }

  /**
   * Write the methods that are part of the external C API of this
   * Python C extension module.
   */
  private void externalMethods() {
    wrapperMethod();
    borrowMethod();
    derefMethod();
    newRefMethod();
    addRefMethod();
    typeMethod();
    converterMethod();
    convertAndStore();
    convertPythonArray();
    getAndConvert();
    //connectConvertMethod();
    //castConvertMethod();
    if (d_isBaseInterface) {
      convertGenericSIDL();
    }
    convertSIDLArray();
  }

  private void preparePythonType()
  {
    final String typeObj = "_" + d_ext.getFullName().replace('.','_') + "Type";
    final String shortName = d_ext.getSymbolID().getShortName();
    LinkedList parents = new LinkedList(d_ext.getParentInterfaces(false));
    if (d_ext instanceof Class) {
      Extendable parent = ((Class)d_ext).getParentClass();
      if (parent != null) {
        parents.addFirst(parent);
      }
    }
    if (parents.isEmpty()) {
      d_lw.println(typeObj + ".tp_base = sidl_PyType();");
    }
    else {
      // parents must be imported before the type is initialized
      writeImports(parents);
      Extendable base = (Extendable)parents.getFirst();
      d_lw.println(typeObj + ".tp_base = " +
                   Python.getExtendableType(base) +
                   "();");
      d_lw.println(typeObj + ".tp_bases = PyTuple_New(" + 
                   parents.size() + ");");
      Iterator i = parents.iterator();
      int count = 0;
      while (i.hasNext()) {
        d_lw.println("PyTuple_SetItem(" + typeObj + ".tp_bases," + count++ 
                     + ", (PyObject *)" + 
                     Python.getExtendableType((Symbol)i.next()) +
                     "());");
      }
    }
    
    d_lw.println("if (PyType_Ready(&" + typeObj + ") < 0) {");
    d_lw.tab();
    d_lw.println("PyErr_Print();");
    d_lw.println("fprintf(stderr, \"PyType_Ready on " +
                 d_ext.getFullName() + " failed.\\n\");");
    d_lw.println("return;");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println("Py_INCREF(&" + typeObj +");");
    d_lw.println("PyDict_SetItemString(dict, \"" + shortName +
                 "\", (PyObject *)&" + typeObj + ");");
  }

  private void generateExceptionClass()
  {
    final String exceptType = Python.getExceptionType(d_ext);
    Iterator i = d_ext.getParents(false).iterator();
    HashSet exceptionParents = new HashSet();
    while (i.hasNext()) {
      Extendable ext = (Extendable)i.next();
      if (Utilities.isException(ext, d_context)) {
        exceptionParents.add(ext);
      }
    }
    final int numBases = ((exceptionParents.size() > 0) 
      ? exceptionParents.size() : 1);
    d_lw.println("_exceptionBases = PyTuple_New(" + numBases + ");");
    if (0 == exceptionParents.size()) {
      d_lw.println("{");
      d_lw.tab();
      d_lw.println("PyObject *baseModule = PyImport_ImportModule(\"sidlBaseException\");");
      d_lw.println("PyObject *baseDict = PyModule_GetDict(baseModule);");
      d_lw.println("PyObject *baseException = PyDict_GetItemString(baseDict, ");
      d_lw.tab();
      d_lw.println("\"sidlBaseException\");");
      d_lw.backTab();
      d_lw.println("Py_XINCREF(baseException);");
      d_lw.println("PyTuple_SetItem(_exceptionBases, 0, baseException);");
      d_lw.backTab();
      d_lw.println("}");
    }
    else {
      int index = 0;
      i = exceptionParents.iterator();
      while (i.hasNext()) {
        final Symbol sym = (Symbol)i.next();
        final String expType = Python.getExceptionType(sym);
        d_lw.println("Py_INCREF(" + expType + ");");
        d_lw.println("PyTuple_SetItem(_exceptionBases, " + index++ +
                     ", " + expType + ");");
      }
    }
    d_lw.println("_exceptionDict = PyDict_New();");
    d_lw.println("PyDict_SetItemString(_exceptionDict, \"__module__\",");
    d_lw.tab();
    d_lw.println("PyString_InternFromString(\"" + d_ext.getFullName() + "\"));");
    d_lw.backTab();

    d_lw.println("_exceptionName = PyString_InternFromString(\"_Exception\");");
    d_lw.println(exceptType +
                 " = PyClass_New(_exceptionBases, _exceptionDict, _exceptionName);");
    d_lw.println("Py_XDECREF(_exceptionBases);");
    d_lw.println("Py_XDECREF(_exceptionDict);");
    d_lw.println("Py_XDECREF(_exceptionName);");

    
  }

  private void writeLoadImpl() {
    final SymbolID id = d_ext.getSymbolID();
    d_lw.println("static void *");
    d_lw.println("_loadClassImpl(void) {");
    d_lw.tab();
    d_lw.println("if (!_implEPV) {");
    d_lw.tab();
    d_lw.writeCommentLine("Load the implementation.");
    d_lw.println(IOR.getExceptionFundamentalType() +
                 "throwaway_exception;");
    d_lw.println("sidl_DLL dll = NULL;");
    d_lw.println("char *errmsg = NULL;");
    d_lw.println(IOR.getExternalName(id) + "*(*_extFunc)(void) = NULL;");
    d_lw.writeCommentLine("Try search global namespace first");
    Python.leavePython(d_lw);
    d_lw.println("dll = sidl_DLL__create(&throwaway_exception);");
    d_lw.println("if (dll && sidl_DLL_loadLibrary(dll, \"main:\", TRUE, FALSE, &throwaway_exception)) {");
    d_lw.tab();
    d_lw.println("_extFunc = (" + IOR.getExternalName(id) + "*(*)(void))");
    d_lw.tab();
    d_lw.println("sidl_DLL_lookupSymbol(dll, \"" +
                 IOR.getExternalFunc(id) + "\", &throwaway_exception);");
    d_lw.backTab();
    d_lw.println("if ((!throwaway_exception) && _extFunc) {");
    d_lw.tab();
    d_lw.println("_implEPV = (*_extFunc)();");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println("if (dll) sidl_DLL_deleteRef(dll, &throwaway_exception);");
    
    d_lw.println("if (!_implEPV) {");
    d_lw.tab();
    d_lw.println("dll = sidl_Loader_findLibrary(\"" + 
                 id.getFullName() + "\",");
    d_lw.tab();
    d_lw.println("\"ior/impl\", sidl_Scope_SCLSCOPE,");
    d_lw.println("sidl_Resolve_SCLRESOLVE, &throwaway_exception);");
    d_lw.backTab();
    d_lw.println("if (dll) {");
    d_lw.tab();
    d_lw.println("_extFunc = (" + IOR.getExternalName(id) + "*(*)(void))");
    d_lw.tab();
    d_lw.println("sidl_DLL_lookupSymbol(dll, \"" +
                 IOR.getExternalFunc(id) + "\", &throwaway_exception);");
    d_lw.backTab();
    d_lw.println("if (!throwaway_exception && _extFunc) {");
    d_lw.tab();
    d_lw.println("_implEPV = (*_extFunc)();");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println("else {");
    d_lw.tab();
    try {
      d_lw.pushLineBreak(false);
      d_lw.println("char *name = sidl_DLL_getName(dll, &throwaway_exception);");
      d_lw.println("if (throwaway_exception) {");
      d_lw.tab();
      d_lw.println("name = sidl_String_strdup(\"(unknown)\");");
      d_lw.backTab();
      d_lw.println("}");
      d_lw.println("errmsg = sidl_String_concat3(");
      d_lw.tab();
      d_lw.println("\"babel: The shared library containing SIDL type " +
                   id.getFullName() + ", \",");
      d_lw.println("name,");
      d_lw.println("\", was loaded but is missing a required symbol (" +
                   IOR.getExternalFunc(id) + ").\");");
      d_lw.backTab();
      d_lw.println("sidl_String_free(name);");
    }
    finally {
      d_lw.popLineBreak();
    }
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println("sidl_DLL_deleteRef(dll, &throwaway_exception);");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println("else {");
    d_lw.tab();
    try {
      d_lw.pushLineBreak(false);
      d_lw.println("errmsg = sidl_String_strdup(\"babel: Unable to find or load a shared library containing the implementation of SIDL type " +
                   id.getFullName() + ". To get more debugging information trying setting the SIDL_DEBUG_DLOPEN environment variable and rerunning.\");");
    }
    finally {
      d_lw.popLineBreak();
    }
    d_lw.backTab();
    d_lw.println("}");
    d_lw.backTab();
    d_lw.println("}");
    Python.resumePython(d_lw);
    d_lw.println("if (!_implEPV) {");
    d_lw.tab();
    d_lw.println("if (errmsg) PyErr_SetString(PyExc_RuntimeError, errmsg);");
    try {
      d_lw.pushLineBreak(false);
      d_lw.println("else PyErr_SetString(PyExc_RuntimeError, \"Unexpected error loading SIDL implementation for " + id.getFullName() + ".\");");
    }
    finally {
      d_lw.popLineBreak();
    }
    d_lw.println("sidl_String_free(errmsg);");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println("return (void *)_implEPV;");
    d_lw.backTab();
    d_lw.println("}");
  }

  /**
   * Write the C extension module's initialization function. This 
   * function is called when the module is loaded into memory or
   * when Python is started in cases where static linking is used.
   */
  private void initModule() {
    final String extType = Python.getExceptionType(d_ext);
    final SymbolID id = d_ext.getSymbolID();
    final boolean isBaseClass = BabelConfiguration.isSIDLBaseClass(id);
    final String connect = Python.getExtendableConnect(d_sym);
    //    final String cast = Python.getExtendableCast(d_sym);
    Python.writeModuleDefBlock(d_lw);
    boolean docComment = (d_ext.getComment() != null) && 
      (d_ext.getComment().getComment() != null);
    d_lw.println("MOD_INIT(" + getName() + ") {");
    d_lw.tab();
    d_lw.println("PyObject *module, *dict, *c_api;");
    if (Utilities.isException(d_ext, d_context)) {
      d_lw.println("PyObject *_exceptionBases, *_exceptionDict, *_exceptionName;");
    }
    d_lw.print("static void *ExternalAPI[");
    d_lw.print(Python.getAPIVarName(d_ext));
    d_lw.println("_NUM];");
    d_lw.println(IOR.getExceptionFundamentalType() +
                 "throwaway_exception;");
    d_lw.println("MOD_DEF(module, \"" + getName() + "\",");
    if (docComment) {
      d_lw.println("\"\\");
      copyComment(d_ext.getComment());
      d_lw.printlnUnformatted("\", _" + getName() + "ModuleMethods);");
    }
    else {
	d_lw.println("\"\", _" + getName() + "ModuleMethods);");
    }
    d_lw.println("if (module == NULL) return MOD_ERROR_VAL;");
    d_lw.println("dict = PyModule_GetDict(module);");
    for(int i = 0; i < d_externalMethods.length; ++i) {
      if ((!d_context.getConfig().getSkipRMI() ) ||
          d_externalMethods[i].compareTo(connect) != 0) {
        if(d_externalMethods[i].compareTo(connect) == 0) {
          //||(d_externalMethods[i].compareTo(cast) == 0)) {
          d_lw.printlnUnformatted("#ifdef WITH_RMI");
        }
        d_lw.print("ExternalAPI[");
        d_lw.print(d_externalMethods[i]);
        d_lw.print("_NUM] = (void*)");
        d_lw.print(d_externalMethods[i]);
        d_lw.println(";");
        if(d_externalMethods[i].compareTo(connect) == 0) {
          //||  (d_externalMethods[i].compareTo(cast) == 0)) {
          d_lw.printlnUnformatted("#endif /*WITH_RMI*/");
        }
      }
    }
    d_lw.println("import_SIDLObjA();");
    d_lw.println("if (PyErr_Occurred()) {");
    d_lw.tab();
    d_lw.println("Py_FatalError(\"Error importing sidlObjA module.\");");
    d_lw.backTab();
    d_lw.println("}");
    if (Utilities.isException(d_ext, d_context)) {
      d_lw.writeCommentLine("Initialize to temporary value.");
      d_lw.println("Py_INCREF(Py_None);");
      d_lw.print("ExternalAPI[");
      d_lw.print(extType);
      d_lw.println("_NUM] = Py_None;");
    }
    d_lw.println
      ("c_api = PyCObject_FromVoidPtr((void *)ExternalAPI, NULL);");
    d_lw.println("PyDict_SetItemString(dict, \"_C_API\", c_api);");
    d_lw.println("Py_XDECREF(c_api);");
    d_lw.println("import_SIDLPyArrays();");
    d_lw.println("if (PyErr_Occurred()) {");
    d_lw.tab();
    d_lw.println("Py_FatalError(\"Error importing sidlPyArrays module.\");");
    d_lw.backTab();
    d_lw.println("}");
    d_lw.println("import_array();");
    d_lw.println("if (PyErr_Occurred()) {");
    d_lw.tab();
    d_lw.println("Py_FatalError(\"Error importing Numeric Python module.\");");
    d_lw.backTab();
    d_lw.println("}");
    preparePythonType();
    if (Utilities.isException(d_ext, d_context)) {
      generateExceptionClass();
      d_lw.println("Py_XINCREF(" + extType + ");");
      d_lw.print("PyDict_SetItemString(dict, \"_Exception\", ");
      d_lw.print(extType);
      d_lw.println(");");
      d_lw.print("ExternalAPI[");
      d_lw.print(extType);
      d_lw.print("_NUM] = ");
      d_lw.print(extType);
      d_lw.println(";");
      d_lw.beginBlockComment(false);
      d_lw.println("If it's safe, add \"Exception\" for backward compatibility");
      d_lw.println("with Babel 0.10.2 (and previous).");
      d_lw.endBlockComment(false);
      d_lw.println("if (!PyDict_GetItemString(dict, \"Exception\")) {");
      d_lw.tab();
      d_lw.println("Py_XINCREF(" + extType + ");");
      d_lw.print("PyDict_SetItemString(dict, \"Exception\", ");
      d_lw.print(extType);
      d_lw.println(");");
      d_lw.backTab();
      d_lw.println("}");
    }
    if (!d_ext.isInterface() && isBaseClass) {
      d_lw.println("_implEPV = " +
                   IOR.getExternalFunc(id) + "();");
      if (d_hasStaticMethods) {
        d_lw.println("_sepv = (*_implEPV->getStaticEPV)();");
      }
      d_lw.println("if (PyErr_Occurred()) {");
      d_lw.tab();
      try {
        d_lw.pushLineBreak(false);
        d_lw.print("Py_FatalError(\"Cannot initialize Python module ");
        d_lw.print(d_ext.getFullName());
        d_lw.println(".\");");
      }
      finally{
        d_lw.popLineBreak();
      }
      d_lw.println("return MOD_SUCCESS_VAL(module);");
      d_lw.backTab();
      d_lw.println("}");
    }

    copyInheritedStatics();

    d_lw.println();
    if (!d_context.getConfig().getSkipRMI()) {
      d_lw.printlnUnformatted("#ifdef WITH_RMI");
      d_lw.println();
      //Add IH connect to the connect registry
      d_lw.println();
      d_lw.println("sidl_rmi_ConnectRegistry_registerConnect(\""+id.getFullName()+
                   "\", (void*)"+IOR.getSymbolName(id)+"__IHConnect, &throwaway_exception);");
      d_lw.println();
      d_lw.printlnUnformatted("#endif /*WITH_RMI*/");
      d_lw.println();
    }

    if(d_hasStaticMethods && d_context.getConfig().getFastCall()) {
      d_lw.writeCommentLine("This will override method stubs with their ");
      d_lw.writeCommentLine("actual implementation if implemented in Python.");
      d_lw.println("_babel_resolve_static_methods(dict);");
    }
    
    d_lw.backTab();
    d_lw.println("}");

  }

  private List inheritedStatics() 
  {
    HashSet staticMethods = new HashSet(d_ext.getStaticMethods(true));
    staticMethods.removeAll(d_ext.getStaticMethods(false));
    return Utilities.sort(staticMethods);
  }

  public void writeInheritedStatics()
  {
    List staticMethods = inheritedStatics();
    if (!staticMethods.isEmpty()) {
      Iterator i = staticMethods.iterator();
      d_lw.println("static const char *s_inherited_statics[] = {");
      d_lw.tab();
      while (i.hasNext()) {
        Method m = (Method)i.next();
        d_lw.println("\"" + m.getShortMethodName() + "\",");
      }
      d_lw.println("NULL");
      d_lw.backTab();
      d_lw.println("};");
      d_lw.println();
    }
  }

  public void copyInheritedStatics()
  {
    List staticMethods = inheritedStatics();
    if (!staticMethods.isEmpty()) {
      d_lw.println("{");
      d_lw.tab();
      d_lw.println("PyObject *pmod = PyImport_ImportModule(\"" +
                   ((Class)d_ext).getParentClass().getFullName() +
                   "\");");
      d_lw.println("PyObject *pdict = (pmod ? PyModule_GetDict(pmod) : NULL);");
      d_lw.println("if (pdict) {");
      d_lw.tab();
      d_lw.println("const char * const *namep = s_inherited_statics;");
      d_lw.println("while (*namep) {");
      d_lw.tab();
      d_lw.println("PyObject *meth = PyDict_GetItemString(pdict, *namep);");
      d_lw.println("if (!meth || PyDict_SetItemString(dict, *namep, meth)) {");
      d_lw.tab();
      d_lw.println("Py_DECREF(pmod);");
      d_lw.println("pmod = NULL;");
      try {
        d_lw.pushLineBreak(false);
        d_lw.println("PyErr_SetString(PyExc_RuntimeError, \"Unable to initialize static method for class " +
                     d_ext.getFullName() + ".\");");
      }
      finally {
        d_lw.popLineBreak();
      }
      d_lw.println("break;");
      d_lw.backTab();
      d_lw.println("}");
      d_lw.println("++namep;");
      d_lw.backTab();
      d_lw.println("}");
      d_lw.backTab();
      d_lw.println("}");
      d_lw.println("Py_XDECREF(pmod);");
      d_lw.backTab();
      d_lw.println("}");
    }
  }

  /**
   * Generate the source file for the extendable with which this object was
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
      d_lw = Python.createStub
        (d_ext, "implement a C extension type for a sidl extendable",
         d_context);
      d_lw.enableLineBreak(78, null, "\\");
      explainExtensionSource();
      d_lw.writeComment(d_ext, true);

      
      d_lw.printlnUnformatted("#include <Python.h>");
      if (!d_context.getConfig().getSkipRMI()) {
        RMIStubSource.generateIncludes(d_ext, d_lw, d_context);
      }
      includeHeaderFiles();
      
      //      generateConnectLoaded();
      generateLangSpecificInit();
      if (!d_context.getConfig().getSkipRMI()) {
        //Forward declare RMICast since we don't do it in the Header. (better way?)
        d_lw.println();
        d_lw.printlnUnformatted("#ifdef WITH_RMI");
        d_lw.println();

        //      RMIStubHeader.generateRMICast(d_ext, d_lw);   
        RMIStubHeader.generateConnectInternal(d_ext, d_lw);
        d_lw.println();
        d_lw.printlnUnformatted("#endif /*WITH_RMI*/");
      }
      d_lw.println();

      //generateExternalModuleAccessMethods();
      if (!d_context.getConfig().getSkipRMI()) {
        RMIStubSource.generateCodeNoIncludes(d_ext, d_lw, d_context);
        connectMethod();
      }

      staticMethods();
      if(d_context.getConfig().getFastCall()) {
        writeCustomGetattro();
        if(d_hasStaticMethods)
          writeResolveStaticMethods();
      }
      convertMethods(d_ext.isInterface());
      pythonCastConstructor();
      pythonStaticVirtualTable();
      pythonObjectVirtualTable();
      overwriteRenamedMethods();
      writePythonType();
      externalMethods();
      writeInheritedStatics();
      initModule();
    }
    finally {
      if (d_lw != null) {
        d_lw.close();
        d_lw = null;
      }
    }
  }
}
