//
// File:        PythonClientHeader.java
// Package:     gov.llnl.babel.backend.python
// Revision:    @(#) $Revision: 7188 $
// Date:        $Date: 2011-09-27 11:38:42 -0700 (Tue, 27 Sep 2011) $
// Description: Write Python extension header file for a BABEL extendable
// 
// This is typically directed by GenPythonClient.
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

package gov.llnl.babel.backend.python;

import gov.llnl.babel.BabelConfiguration;
import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.Utilities;
import gov.llnl.babel.backend.python.Python;
import gov.llnl.babel.backend.writers.LanguageWriterForC;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.Type;
/**
 * Create and write a header for a Python C extension class to wrap a 
 * BABEL extendable in a Python object. The header has to expose a 
 * function to create a wrapped IOR, a function to check if a 
 * <code>PyObject</code> is an instance of this extension type, and
 * an import macro.
 */
public class PythonClientHeader {
  private Symbol d_sym = null;
  private LanguageWriterForC d_lw = null;
  private int d_numMethods;
  private String [] d_methodNames = null;
  private String [] d_methodPrototypes = null;
  private String [] d_methodReturn = null;
  private Context d_context;

  private final boolean isExtendable()
  {
    return (d_sym instanceof Extendable);
  }
  
  /**
   * Create an object capable of generating the header file for a
   * BABEL extendable.
   *
   * @param ext   an interface or class symbol that needs a header
   *              file for a Python C extension class.
   */
  public PythonClientHeader(Symbol sym,
                            Context context)
    throws CodeGenerationException
  {
    d_sym = sym;
    d_context = context;
    final boolean isExt = isExtendable();
    final SymbolID id = sym.getSymbolID();
    final String iorType = isExt ? IOR.getObjectName(id) 
      : IOR.getStructName(id);
    final String arrayType = isExt ? IOR.getArrayName(id) : "";
    final boolean isBaseInterface = isExt &&
      BabelConfiguration.getBaseInterface().equals(sym.getFullName());
    d_sym = sym;
    d_numMethods = isExt ? (isBaseInterface ? 11 : 10) 
      : 8;
    
    d_methodNames = new String[d_numMethods];
    d_methodPrototypes = new String[d_numMethods];
    d_methodReturn = new String[d_numMethods];

    d_methodNames[0] = Python.getExtendableWrapper(d_sym);
    d_methodReturn[0] = "PyObject *";
    d_methodPrototypes[0] = "(const " + iorType + " *sidlobj)";

    if (isExt) {
      d_methodNames[1] = Python.getExtendableConverter(d_sym);
      d_methodReturn[1] = "int";
      d_methodPrototypes[1] = "(PyObject *obj, " + iorType + " **sidlobj)";

      d_methodNames[2] = Python.getBorrowArrayFromPython(new Type(id,d_context));
      d_methodReturn[2] = "int";
      d_methodPrototypes[2] = "(PyObject *obj, " + arrayType + " **sidlarray)";

      d_methodNames[3] = Python.getBorrowArrayFromSIDL(new Type(id, d_context));
      d_methodReturn[3] = "PyObject *";
      d_methodPrototypes[3] = "(struct sidl__array *sidlarray)";

      d_methodNames[4] = Python.getExtendableBorrow(d_sym);
      d_methodReturn[4] = "PyObject *";
      d_methodPrototypes[4] = "(" + iorType + " *sidlobj)";

      d_methodNames[5] = Python.getExtendableDeref(d_sym);
      d_methodReturn[5] = "void";
      d_methodPrototypes[5] = "(" + iorType + " *sidlobj)";

      d_methodNames[6] = Python.getExtendableNewRef(d_sym);
      d_methodReturn[6] = "PyObject *";
      d_methodPrototypes[6] = "(" + iorType + " *sidlobj)";

      d_methodNames[7] = Python.getExtendableAddRef(d_sym);
      d_methodReturn[7] = "void";
      d_methodPrototypes[7] = "(" + iorType + " *sidlobj)";

      d_methodNames[8] = Python.getExtendableType(d_sym);
      d_methodReturn[8] = "PyTypeObject *";
      d_methodPrototypes[8] = "(void)";

      d_methodNames[9] = Python.getExtendableConnect(d_sym);
      d_methodReturn[9] = iorType+"* ";
      d_methodPrototypes[9] = "(const char* url, sidl_bool ar, " +
        IOR.getExceptionFundamentalType() + "* _ex)";

      /*      d_methodNames[10] = Python.getExtendableCast(d_sym);
      d_methodReturn[10] = iorType+"* ";
      d_methodPrototypes[10] = "(void* bi, " +
        IOR.getExceptionFundamentalType() + "* _ex)";
      */

      if (isBaseInterface) {
        d_methodNames[10] = Python.getBorrowArrayFromSIDL(null);
        d_methodReturn[10] = "PyObject *";
        d_methodPrototypes[10] = "( struct sidl__array *sidlarray)";
      }
    }
    else {
      final String bi = getExceptionStruct();
      d_methodNames[1] = Python.getExtendableConverter(d_sym);
      d_methodReturn[1] = "int";
      d_methodPrototypes[1] = "(PyObject *obj, " + iorType + " *sidlobj)";

      d_methodNames[2] = Python.getStructSerialize(d_sym);
      d_methodReturn[2] = "void";
      d_methodPrototypes[2] = "(const " + iorType +
        "* arg, struct sidl_io_Serializer__object *pipe, const char *name, int copyArg, " +
        bi + " **exception)";

      d_methodNames[3] = Python.getStructDeserialize(d_sym);
      d_methodReturn[3] = "void";
      d_methodPrototypes[3] = "(" + iorType +
        "* arg, struct sidl_io_Deserializer__object *pipe, const char *name, int copyArg, " +
        bi + " **exception)";

      d_methodNames[4] = Python.getStructInit(d_sym);
      d_methodReturn[4] = "void";
      d_methodPrototypes[4] = "(" + iorType + "* strct)";

      d_methodNames[5] = Python.getStructDestroy(d_sym);
      d_methodReturn[5] = "void";
      d_methodPrototypes[5] = "(" + iorType + "* strct)";

      d_methodNames[6] = Python.getStructCopy(d_sym);
      d_methodReturn[6] = "void";
      d_methodPrototypes[6] = "(const " + iorType + "* src, " +
        iorType + "* dest)";

      d_methodNames[7] = Python.getStructBorrow(d_sym);
      d_methodReturn[7] = "PyObject *";
      d_methodPrototypes[7] = "(" + iorType + 
        "* src, PyObject *owner, void *ignored)";
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
  public synchronized void generateCode() 
    throws CodeGenerationException 
  {
    String guard = Python.getIncludeGuard(d_sym, "MODULE");
    try {
      d_lw = Python.createCHeader
        (d_sym, "Module", 
         "expose a constructor for the Python wrapper", d_context);
      explainExtHeader();
      d_lw.openHeaderGuard(guard);
      d_lw.printlnUnformatted("#include <Python.h>");
      d_lw.printlnUnformatted("#include \"sidlType.h\"");
      d_lw.printlnUnformatted("#ifdef HAVE_PTHREAD");
      d_lw.printlnUnformatted("#include <pthread.h>");
      d_lw.printlnUnformatted("#endif /* HAVE_PTHREAD */");
      d_lw.println();
      d_lw.openCxxExtern();
      if (isExtendable()) {
        d_lw.println("struct sidl__array;");
      }
      addSharedDefs();

      d_lw.printUnformatted("#ifdef ");
      d_lw.printlnUnformatted(Python.getInternalGuard(d_sym));
      d_lw.println();
      d_lw.printUnformatted("#define ");
      d_lw.printlnUnformatted(Python.getExtendableImport(d_sym) + "() ;");
      d_lw.println();
      addInternalDecls();
      d_lw.printlnUnformatted("#else");
      addClientMacroDefs();
      d_lw.printlnUnformatted("#endif");
      d_lw.println();
      d_lw.closeCxxExtern();
      d_lw.println();
      d_lw.closeHeaderGuard();
    }
    finally {
      if (d_lw != null) {
        d_lw.close();
        d_lw = null;
      }
    }
  }

  private void printImportMacro(String apivar, boolean pthread) 
  {
    String indent  = (pthread ? "  " : "");
    try {
      d_lw.printlnUnformatted("#ifndef Py_TYPE");
      d_lw.printlnUnformatted("#define Py_TYPE(ob) (((PyObject *)(ob))->ob_type)");
      d_lw.printlnUnformatted("#endif");
      d_lw.println();
      d_lw.pushLineBreak(false);
      d_lw.print("#define ");
      d_lw.print(Python.getExtendableImport(d_sym));
      d_lw.println("() \\");
      if (pthread) {
        d_lw.println("{ \\");
        d_lw.println("  pthread_mutex_t __sidl_pyapi_mutex = PTHREAD_MUTEX_INITIALIZER; \\");
        d_lw.println("  pthread_mutex_lock(&__sidl_pyapi_mutex); \\");
      }
      d_lw.println(indent + "if (!" + apivar + ") { \\");
      d_lw.print(indent + "  PyObject *_imp_module = PyImport_ImportModule(\"");
      d_lw.print(d_sym.getFullName());
      d_lw.println("\"); \\");
      d_lw.println(indent + "  if (_imp_module != NULL) { \\");
      d_lw.println(indent +
                   "    PyObject *_imp_module_dict = PyModule_GetDict(_imp_module); \\");
      d_lw.println(indent + 
                   "    PyObject *_imp_c_api_object = \\");
      d_lw.println(indent +
                   "      PyDict_GetItemString(_imp_module_dict, \"_C_API\"); \\");
      d_lw.println(indent + 
                   "    if (_imp_c_api_object && PyCObject_Check(_imp_c_api_object)) { \\");
      d_lw.print(indent + "      ");
      d_lw.print(apivar);
      d_lw.println(" = \\");
      d_lw.println(indent + 
                   "        (void **)PyCObject_AsVoidPtr(_imp_c_api_object); \\");
      d_lw.println(indent + "    } \\");
      d_lw.print(indent + "    else { fprintf(stderr, \"babel: ");
      d_lw.print(Python.getExtendableImport(d_sym));
      d_lw.println(" failed to lookup _C_API (%p %p %s).\\n\", _imp_c_api_object, _imp_c_api_object ? Py_TYPE(_imp_c_api_object) : NULL, _imp_c_api_object ? Py_TYPE(_imp_c_api_object)->tp_name : \"\"); }\\");
      d_lw.println(indent + "    Py_DECREF(_imp_module); \\");
      d_lw.print(indent + "  } else { fprintf(stderr, \"babel: ");
      d_lw.print(Python.getExtendableImport(d_sym));
      d_lw.println(" failed to import its module.\\n\"); \\");
      d_lw.println(indent + "    " + 
                   "if (PyErr_Occurred()) { PyErr_Print(); PyErr_Clear();}\\");
      d_lw.println(indent + "  }\\");
      if (pthread) {
        d_lw.println("  }\\");
        d_lw.println("  pthread_mutex_unlock(&__sidl_pyapi_mutex); \\");
        d_lw.println("  pthread_mutex_destroy(&__sidl_pyapi_mutex); \\");
      }
      d_lw.println("}");
    }
    finally {
      d_lw.popLineBreak();
    }
  }

  private void addClientMacroDefs()
    throws CodeGenerationException
  {
    String apivar = Python.getAPIVarName(d_sym);
    d_lw.println();
    d_lw.print("static void **");
    d_lw.print(apivar);
    d_lw.println(" = NULL;");
    d_lw.println();
    try {
      d_lw.pushLineBreak(false);
      for(int i = 0; i < d_numMethods; ++i) {
        d_lw.print("#define ");
        d_lw.print(d_methodNames[i]);
        d_lw.println(" \\");
        d_lw.print("  (*((");
        d_lw.print(d_methodNames[i]);
        d_lw.println("_RETURN (*) \\");
        d_lw.print("  ");
        d_lw.print(d_methodNames[i]);
        d_lw.println("_PROTO) \\");
        d_lw.print("  (");
        d_lw.print(apivar);
        d_lw.println(" \\");
        d_lw.print("  [");
        d_lw.print(d_methodNames[i]);
        d_lw.println("_NUM])))");
        d_lw.println();
      }
      if (Utilities.isException(d_sym, d_context)) {
        String exType = Python.getExceptionType(d_sym);
        d_lw.print("#define ");
        d_lw.print(exType);
        d_lw.println(" \\");
        d_lw.print("  ((PyObject *)(");
        d_lw.print(apivar);
        d_lw.print("[");
        d_lw.print(exType);
        d_lw.println("_NUM]))");
        d_lw.println();
      }
      d_lw.println("#ifdef HAVE_PTHREAD");
      printImportMacro(apivar, true);
      d_lw.println("#else /* !HAVE_PTHREAD */");
      printImportMacro(apivar, false);
      d_lw.println("#endif /* HAVE_PTHREAD */");
    }
    finally {
      d_lw.popLineBreak();
    }
    d_lw.println();
  }

  private void addInternalDecls()
    throws CodeGenerationException
  {
    final String connect = Python.getExtendableConnect(d_sym);
    //    final String cast = Python.getExtendableCast(d_sym);
    d_lw.println();
    d_lw.beginBlockComment(false);
    d_lw.println("This declaration is not for clients.");
    d_lw.endBlockComment(false);
    for(int i = 0 ; i < d_numMethods; ++i) {
      //rmicast and connectI are defined internally in the module.c Do not
      //define them here internally
      if(d_methodNames[i].compareTo(connect) != 0) {
        //&&         (d_methodNames[i].compareTo(cast) != 0)) {
        d_lw.print("static ");
        d_lw.print(d_methodNames[i]);
        d_lw.println("_RETURN");
        d_lw.println(d_methodNames[i]);
        d_lw.print(d_methodNames[i]);
        d_lw.println("_PROTO;");
        d_lw.println();
      }
    }
    if (Utilities.isException(d_sym, d_context)) {
      String exType = Python.getExceptionType(d_sym);
      d_lw.println("static PyObject *");
      d_lw.print(exType);
      d_lw.println(";");
      d_lw.println();
    }
  }

  private final String getExceptionStruct()
    throws CodeGenerationException
  {
    return IOR.
      getObjectName(d_context.getSymbolTable().
                    lookupSymbol("sidl.BaseInterface").getSymbolID());
  }

  private void addSharedDefs()
    throws CodeGenerationException 
  {
    int i;
    d_lw.println();
    d_lw.writeCommentLine("Forward declaration of IOR structure");
    if (isExtendable()) {
      d_lw.print(IOR.getObjectName(d_sym.getSymbolID()));
      d_lw.println(";");
      d_lw.print(IOR.getArrayName(d_sym.getSymbolID()));
      d_lw.println(";");
    }
    else {
      d_lw.println(IOR.getStructName(d_sym.getSymbolID()) + ";");
      d_lw.println("struct sidl_io_Serializer__object;");
      d_lw.println("struct sidl_io_Deserializer__object;");
    }
    d_lw.print(getExceptionStruct());
    d_lw.println(";");
    d_lw.println();
    try {
      d_lw.pushLineBreak(false);
      for(i = 0; i < d_numMethods; ++i) {
        d_lw.print("#define ");
        d_lw.print(d_methodNames[i]);
        d_lw.print("_NUM ");
        d_lw.println(Integer.toString(i));
        d_lw.print("#define ");
        d_lw.print(d_methodNames[i]);
        d_lw.print("_RETURN ");
        d_lw.println(d_methodReturn[i]);
        d_lw.print("#define ");
        d_lw.print(d_methodNames[i]);
        d_lw.print("_PROTO ");
        d_lw.println(d_methodPrototypes[i]);
        d_lw.println();
      }
      if (Utilities.isException(d_sym, d_context)) {
        d_lw.print("#define ");
        d_lw.print(Python.getExceptionType(d_sym));
        d_lw.print("_NUM ");
        d_lw.println(Integer.toString(i++));
        d_lw.println();
      }
      d_lw.print("#define ");
      d_lw.print(Python.getAPIVarName(d_sym));
      d_lw.print("_NUM ");
      d_lw.println(Integer.toString(i));
    }
    finally {
      d_lw.popLineBreak();
    }
    d_lw.println();
  }

   private void explainExtHeader()
     throws CodeGenerationException
   {
      d_lw.beginBlockComment(false);
      d_lw.println("THIS CODE IS AUTOMATICALLY GENERATED BY THE BABEL");
      d_lw.println("COMPILER. DO NOT EDIT THIS!");
      d_lw.println();
      d_lw.println("External clients need an entry point to wrap a pointer");
      d_lw.print("to an instance of ");
      d_lw.print(d_sym.getFullName());
      d_lw.println(".");
      d_lw.println("This header files defines two methods that such clients");
      d_lw.println("will need.");
      d_lw.print("    ");
      d_lw.println(Python.getExtendableImport(d_sym));
      d_lw.println("        This should be called in the client's init");
      d_lw.println("        module method.");
      d_lw.print("    ");
      d_lw.println(Python.getExtendableWrapper(d_sym));
      d_lw.println("        This will wrap an IOR in a Python object.");
      if (Utilities.isException(d_sym, d_context)) {
        d_lw.println("This object can be used as an exception. It exports");
        d_lw.println("a Python exception type that may be needed as well.");
        d_lw.print("    ");
        d_lw.println(Python.getExceptionType(d_sym));
        d_lw.println("        A Python exception type corresponding to");
        d_lw.println("        this object type.");
        d_lw.println("Here is the pattern for throwing an exception:");
        d_lw.print("  PyObject *obj = ");
        d_lw.print(Python.getExtendableWrapper(d_sym));
        d_lw.println("(ex);");
        d_lw.print("  PyErr_SetObject(");
        d_lw.print(Python.getExceptionType(d_sym));
        d_lw.println(", obj);");
        d_lw.println("  Py_XDECREF(obj);");
      }
      d_lw.endBlockComment(false);
   }
}
