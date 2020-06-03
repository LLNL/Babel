//
// File:        TranslateArguments.java
// Package:     gov.llnl.babel.backend.pythong
// Revision:    @(#) $Revision: 7188 $
// Date:        $Date: 2011-09-27 11:38:42 -0700 (Tue, 27 Sep 2011) $
// Description: Manage the translation of parameters to/from Python
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

package gov.llnl.babel.backend.python;
import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.Utilities;
import gov.llnl.babel.backend.writers.LanguageWriter;
import gov.llnl.babel.symbols.Argument;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class TranslateArguments {
  private LanguageWriter d_lw;
  private Method d_m;
  private Context d_context;
  private boolean d_convertIncoming = true;
  private boolean d_localVars;
  private boolean d_borrowArrays = true;
  private boolean d_isClient;
  public static final String RETURN_VAR = "_return";

  public TranslateArguments(LanguageWriter writer,
                            Method m,
                            Context context,
                            boolean localVars,
                            boolean isClient)
  {
    d_lw = writer;
    d_context = context;
    d_m = m;
    d_localVars = localVars;
    d_isClient = isClient;
  }

  public void setConvertIncoming(boolean convertIncoming)
  {
    d_convertIncoming = convertIncoming;
  }

  public boolean isConvertIncoming()
  {
    return d_convertIncoming;
  }

  public static boolean hasProxy(Type ty, 
                                 boolean inout,
                                 boolean isClient)
  {
    final int t = ty.getDetailedType();
    return ((t == Type.FCOMPLEX) || 
            (t == Type.DCOMPLEX) ||
            (t == Type.INT) ||
            (t == Type.LONG) ||
            (t == Type.ENUM) ||
            (t == Type.BOOLEAN) ||
            (ty.isRarray() && inout && !isClient));
  }
  
  public static String proxyVariable(String var)
  {
    return "_proxy_" + var;
  }

  private void translateArg(int type,
                            int arrayType,
                            String var,
                            String getValue,
                            boolean sidlToPython,
                            boolean readOnly,
                            boolean isRarray,
                            int arrayOrder)
  {
    switch (type) {
    case Type.FCOMPLEX:
    case Type.DCOMPLEX:
      if (sidlToPython) {
        d_lw.println(proxyVariable(var) + ".real = (" +
                     getValue + var + ").real;");
        d_lw.println(proxyVariable(var) + ".imag = (" +
                     getValue + var + ").imaginary;");
      }
      else {
        d_lw.println('(' + getValue + var + ").real = " + 
                     proxyVariable(var) + ".real;");
        d_lw.println('(' + getValue + var + ").imaginary = " +
                     proxyVariable(var) + ".imag;");
      }
      break;
    case Type.INT:
    case Type.LONG:
    case Type.ENUM:
      if (sidlToPython) {
        d_lw.println(proxyVariable(var) + " = " + getValue + var + ';');
      }
      else {
        d_lw.println(getValue + var + " = " + proxyVariable(var) + ';');
      }
      break;
    case Type.BOOLEAN:
      if (sidlToPython) {
        d_lw.println(proxyVariable(var) + " = " + getValue + var + ';');
      }
      else {
        d_lw.println(getValue + var + " = (" + proxyVariable(var) + 
                     " ? (TRUE) : (FALSE));");
      }
      break;
    case Type.STRING:
      if (!sidlToPython && !readOnly) {
        d_lw.println(getValue + var + " = (" + getValue + var +
                     " ? strcpy((char*)malloc(strlen(" +
                     getValue + var + ")+1), " +
                     getValue + var + ") : NULL);");
      }
      break;
    case Type.ARRAY:
      if (!(sidlToPython || readOnly || d_isClient) && isRarray) {
        d_lw.println("sidl_python_copy((struct sidl__array *)" +
                     proxyVariable(var) + ", (struct sidl__array *)(*" +
                     var + "));");
        Python.leavePython(d_lw);
        d_lw.println("sidl_python_deleteRef_array((struct sidl__array *)(" +
                     proxyVariable(var) + "));");
        Python.resumePython(d_lw);
      }
      else if (!(sidlToPython || isRarray) && 
               (arrayOrder != Type.UNSPECIFIED)) {
        d_lw.println(getValue + var + " = (struct sidl_" +
                     Type.getTypeName(arrayType) + "__array *)" +
                     "sidl_python_ensure((struct sidl__array *)(" +
                     getValue + var + 
                     ((arrayOrder == Type.COLUMN_MAJOR) 
                      ? "), 1);" : "), 0);"));
      }
      break;
    }
  }

  private void addSidlToPythonStart(Type t)
    throws CodeGenerationException
  {
    switch(t.getDetailedType()) {
    case Type.CHAR:
      d_lw.print("(int)");
      break;
    case Type.STRUCT:
      {
        Symbol sym = Utilities.lookupSymbol(d_context, t.getSymbolID());
        d_lw.println("(void *)" +
                     Python.getExtendableWrapper(sym) + ", ");
      }
      break;
    case Type.CLASS: 
    case Type.INTERFACE: 
      {
        Symbol sym = Utilities.lookupSymbol(d_context, t.getSymbolID());
        d_lw.print("(void *)");
        if (d_convertIncoming) {
          d_lw.print(Python.getExtendableNewRef(sym));
        }
        else {
          d_lw.print(Python.getExtendableWrapper(sym));
        }
        d_lw.print(", ");
      }
      break;
    case Type.ARRAY:
      d_lw.print("(void *)");
      if (d_borrowArrays) {
        d_lw.print(Python.getBorrowArrayFromSIDL(t.getArrayType()));
      }
      else {
        d_lw.print(Python.getCopyArrayFromSIDL(t.getArrayType()));
      }
      d_lw.print(", ");
      break;
    case Type.OPAQUE:
      d_lw.print("(void *)sidl_Opaque_Create, ");
      break;
    case Type.DCOMPLEX:
    case Type.FCOMPLEX:
      d_lw.print("PyComplex_FromCComplex(");
      break;
    }
  }

  /**
   * When converting variables into a Python return value, some types
   * require a function to convert the type into a Python object. This
   * adds end part of the wrapper function when needed.
   *
   * @param t   the type determines whether and which wrapper is needed.
   */
  private void addSidlToPythonEnd(Type t) {
    switch(t.getDetailedType()) {
    case Type.DCOMPLEX:
    case Type.FCOMPLEX:
      d_lw.print(")");
      break;
    }
  }

  private void addPythonToSidlStart(Type t)
    throws CodeGenerationException
  {
    switch(t.getDetailedType()) {
    case Type.STRUCT:
    case Type.CLASS: 
    case Type.INTERFACE: 
      {
        Symbol sym = Utilities.lookupSymbol(d_context, t.getSymbolID());
        d_lw.print("(void *)");
        d_lw.print(Python.getExtendableConverter(sym));
        d_lw.print(", ");
      }
      break;
    case Type.ARRAY:
      d_lw.print("(void *)");
      if (d_borrowArrays) {
        d_lw.print(Python.getBorrowArrayFromPython(t.getArrayType()));
      }
      else {
        d_lw.print(Python.getCopyArrayFromPython(t));
      }
      d_lw.print(", ");
      break;
    case Type.OPAQUE:
      d_lw.print("(void *)sidl_Opaque_Convert, ");
      break;
    }
  }

  private int numArguments()
  {
    int result = 0;
    if (!d_convertIncoming && (Type.VOID !=
                               d_m.getReturnType().getDetailedType())) {
      ++result;
    }
    Iterator i = d_m.getArgumentList().iterator();
    while(i.hasNext()) {
      final int mode = ((Argument)i.next()).getMode();
      if ((mode == Argument.INOUT) ||
          (d_convertIncoming && (mode == Argument.IN)) ||
          ((!d_convertIncoming) && (mode == Argument.OUT))) {
        ++result;
      }
    }
    return result;
  }

  private void outputFormatString(boolean sidlToPython) 
    throws CodeGenerationException
  {
    try {
      d_lw.pushLineBreak(false);
      d_lw.print((sidlToPython && d_convertIncoming) ? "\"(" : "\"");
      if (!d_convertIncoming) {
        argFormat(d_m.getReturnType(), sidlToPython);
      }
      Iterator i = d_m.getArgumentList().iterator();
      while (i.hasNext()) {
        Argument arg = (Argument)i.next();
        final int mode = arg.getMode();
        if ((mode == Argument.INOUT) ||
            (d_convertIncoming && (mode == Argument.IN)) ||
            ((!d_convertIncoming) && (mode == Argument.OUT))) {
          argFormat(arg.getType(), sidlToPython);
        }
      }
      d_lw.print((sidlToPython && d_convertIncoming) ? ")\"" : "\"");
    }
    finally {
      d_lw.popLineBreak();
    }
  }

  private void addParameter(String        param,
                            final Type    t,
                            final int     mode,
                            final boolean sidlToPython)
    throws CodeGenerationException
  {
    if ((d_convertIncoming && (mode != Argument.OUT)) ||
        !(d_convertIncoming || (mode == Argument.IN))) {
      if (t.getDetailedType() == Type.STRUCT) {
        d_lw.println(",");
        if (sidlToPython) {
          addSidlToPythonStart(t);
        }
        else {
          addPythonToSidlStart(t);
        }
        d_lw.print(((d_isClient || param.equals(RETURN_VAR)) ? "&" : "") + 
                   param);
        if (sidlToPython) {
          addSidlToPythonEnd(t);
        }
      }
      else {
        final boolean proxy = hasProxy(t, mode == Argument.INOUT, d_isClient);
        final boolean isPointer =
          !((proxy && !(t.isRarray() && sidlToPython)) || 
            d_localVars || (mode == Argument.IN) ||
            param.equals(RETURN_VAR));
        d_lw.println(",");
        if (proxy && !(t.isRarray() && sidlToPython)) {
          param = proxyVariable(param);
        }
        if (sidlToPython) {
          addSidlToPythonStart(t);
          if (isPointer) {
            d_lw.print("*");
          }
        }
        else {
          addPythonToSidlStart(t);
          if (!isPointer) {
            d_lw.print("&");
          }
        }
        d_lw.print(param);
        if (sidlToPython) {
          addSidlToPythonEnd(t);
        }
      }
    }
  }

  private void outputParameters(boolean sidlToPython)
    throws CodeGenerationException
  {
    if (!d_convertIncoming) {
      if (d_m.getReturnType().getDetailedType() != Type.VOID) {
        addParameter(RETURN_VAR, d_m.getReturnType(),
                     Argument.OUT, sidlToPython);
      }
    }
    Iterator i = d_m.getArgumentList().iterator();
    while (i.hasNext()) {
      Argument arg = (Argument)i.next();
      addParameter(arg.getFormalName(),
                   arg.getType(),
                   arg.getMode(),
                   sidlToPython);
    }
  }

  /**
   * Return the formatting character(s) for a Python
   * <code>PyArg_ParseTuple</code> (if <code>sidlToPython</code> is
   * <code>false</code>) or <code>Py_BuildValue</code> (if
   * <code>sidlToPython</code> is <code>true</code>).
   *
   * @param t            the type whose format string is to be returned.
   * @param sidlToPython <code>true</code> means return the string for a
   *                     <code>Py_BuildValue</code> call; <code>false</code>
   *                     means return the string for a
   *                     <code>PyArg_ParseTuple</code> call.
   * @exception gov.llnl.babel.backend.CodeGeneration
   *    the type is not one that is currently supported in Python.
   */
  private void argFormat(Type t, boolean sidlToPython) 
    throws CodeGenerationException
  {
    
    switch(t.getDetailedType()) {
    case Type.BOOLEAN:
      d_lw.print("i");
      break;
    case Type.CHAR:
      d_lw.print("c");
      break;
    case Type.FCOMPLEX:
    case Type.DCOMPLEX:
      d_lw.print(sidlToPython ? "N" : "D");
      break;
    case Type.DOUBLE:
      d_lw.print("d");
      break;
    case Type.FLOAT:
      d_lw.print("f");
      break;
    case Type.INT:
      d_lw.print("l");
      break;
    case Type.ENUM:
    case Type.LONG:
      d_lw.println("\"");
      d_lw.printlnUnformatted("#ifdef HAVE_LONG_LONG");
      d_lw.println("\"L\"");
      d_lw.printlnUnformatted("#else");
      d_lw.println("\"l\"");
      d_lw.printlnUnformatted("#endif");
      d_lw.print("\"");
      break;
    case Type.STRING:
      d_lw.print("z");
      break;
    case Type.VOID:
      /* don't print anything */
      break;
    case Type.STRUCT:
    case Type.CLASS:
    case Type.INTERFACE:
    case Type.ARRAY:
    case Type.OPAQUE:
      d_lw.print("O&");
      break;
    default:
      throw new CodeGenerationException
        ("The " + t.getTypeString() + 
         " type is not currently supported in Python.");
    }
  }

  private int getArrayType(Type t)
  {
    Type arrayType = t.getArrayType();
    return (arrayType != null) ? arrayType.getDetailedType() : Type.VOID;
  }

  public void convertIncomingArguments(boolean sidlToPython)
  {
    if (d_convertIncoming) {
      Iterator i = d_m.getArgumentList().iterator();
      while (i.hasNext()) {
        Argument arg = (Argument)i.next();
        final int mode = arg.getMode();
        if (mode != Argument.OUT) {
          final String getValue = 
            (mode == Argument.INOUT && !d_localVars) ? "*" : "";
          translateArg(arg.getType().getDetailedType(), 
                       getArrayType(arg.getType()),
                       arg.getFormalName(), getValue, sidlToPython,
                       mode == Argument.IN,
                       arg.getType().isRarray(),
                       Type.UNSPECIFIED);
        }
      }
    }
  }

  public void convertOutgoingArguments(boolean sidlToPython)
  {
    if (!d_convertIncoming) {
      final String getValue = (d_localVars ? "" : "*");
      final int returnType = d_m.getReturnType().getDetailedType();
      if (returnType != Type.VOID) {
        translateArg(returnType, 
                     getArrayType(d_m.getReturnType()),
                     RETURN_VAR, "", sidlToPython, false, false,
                     d_m.getReturnType().getArrayOrder());
      }
      Iterator i = d_m.getArgumentList().iterator();
      while (i.hasNext()) {
        Argument arg = (Argument)i.next();
        final int mode = arg.getMode();
        if (mode != Argument.IN) {
          translateArg(arg.getType().getDetailedType(), 
                       getArrayType(arg.getType()),
                       arg.getFormalName(), getValue, sidlToPython, false,
                       arg.getType().isRarray(),
                       arg.getType().getArrayOrder());
        }
      }
    }
  }

  /**
   * Create a list of out parameters.
   */
  public static List extractOut(Type returnType, List argList)
  {
    Iterator args = argList.iterator();
    ArrayList outList = new ArrayList(argList.size() + 1);
    if (returnType.getDetailedType() != Type.VOID) {
      outList.add(new 
        Argument(Argument.OUT, returnType, RETURN_VAR));
    }
    while (args.hasNext()){
      Argument arg = (Argument)args.next();
      if (arg.getMode() != Argument.IN) {
        outList.add(arg);
      }
    }
    return outList;
  }

  public void convertPythonToSidl(String arg)
    throws CodeGenerationException
  {
    if (d_convertIncoming || 
        (extractOut(d_m.getReturnType(), d_m.getArgumentList()).size() 
         > 0)) {
      d_lw.println("PyArg_ParseTuple(" + arg + ", ");
      d_lw.tab();
      outputFormatString(false);
      outputParameters(false);
      d_lw.println(");");
      d_lw.backTab();
    }
    else {
      d_lw.println("(" + arg + " == Py_None);");
    }
  }

  public void convertPythonToSidl(String arg,
                                  String kwDict,
                                  String kwList)
    throws CodeGenerationException
  {
    d_lw.println("PyArg_ParseTupleAndKeywords(");
    d_lw.tab();
    d_lw.println(arg + ", " + kwDict + ", ");
    outputFormatString(false);
    d_lw.print(", " + kwList);
    outputParameters(false);
    d_lw.println(");");
    d_lw.backTab();
  }

  public void convertSidlToPython()
    throws CodeGenerationException
  {
    if (numArguments() > 0) {
      d_lw.println("Py_BuildValue(");
      d_lw.tab();
      outputFormatString(true);
      outputParameters(true);
      d_lw.println(");");
      d_lw.backTab();
    }
    else {
      if (d_convertIncoming) {
        d_lw.println(" PyTuple_New(0);");
      }
      else {
        d_lw.println(" Py_None; Py_INCREF(Py_None);");
      }
    }
  }

  public void declareProxy(final Type t,
                           String name,
                           final int mode)
    throws gov.llnl.babel.backend.CodeGenerationException
  {
    if (hasProxy(t, mode == Argument.INOUT, d_isClient)) {
      final int typeCode = t.getDetailedType();
      switch (typeCode) {
      case Type.ARRAY:
        if (t.isRarray()) {
          d_lw.println(IOR.getReturnString(t, d_context) + 
                       proxyVariable(name) + " = NULL;");
        }
        break;
      case Type.BOOLEAN:
        d_lw.println("int " + proxyVariable(name) + ";");
        break;
      case Type.STRING:
        d_lw.println("const char *" + proxyVariable(name) + " = NULL;");
        break;
      case Type.DCOMPLEX:
        d_lw.println("Py_complex " + proxyVariable(name) + ";");
        break;
      case Type.FCOMPLEX:
        d_lw.println("Py_complex " + proxyVariable(name) + ";");
        break;
      case Type.INT:
        d_lw.println("long " + proxyVariable(name) + ";");
        break;
      case Type.ENUM:
      case Type.LONG:
        d_lw.printlnUnformatted("#ifdef HAVE_LONG_LONG");
        d_lw.println("long long int " + proxyVariable(name) + ";");
        d_lw.printlnUnformatted("#else");
        d_lw.println("long " + proxyVariable(name) + ";");
        d_lw.printlnUnformatted("#endif");
      }
    }
  }

  public void declareProxies()
    throws gov.llnl.babel.backend.CodeGenerationException
  {
    Iterator i = d_m.getArgumentList().iterator();
    while (i.hasNext()) {
      Argument arg = (Argument)i.next();
      declareProxy(arg.getType(), arg.getFormalName(), arg.getMode());
    }
  }
}
