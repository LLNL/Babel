//
// File:        PythonServerPySource.java
// Package:     gov.llnl.babel.backend.pythong
// Revision:    @(#) $Revision: 7188 $
// Date:        $Date: 2011-09-27 11:38:42 -0700 (Tue, 27 Sep 2011) $
// Description: Generate Python implementation file in Python
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

import gov.llnl.babel.BabelConfiguration;
import gov.llnl.babel.Context;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.CodeConstants;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.CodeSplicer;
import gov.llnl.babel.backend.Utilities;
import gov.llnl.babel.backend.python.Python;
import gov.llnl.babel.backend.writers.LanguageWriterForPython;
import gov.llnl.babel.symbols.Argument;
import gov.llnl.babel.symbols.Class;
import gov.llnl.babel.symbols.Comment;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.Type;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * The purpose of this class is to generate Python source code to implement
 * a sidl class. Parts of the Python source code must be filled by the
 * developer, and these parts are retained using a <code>CodeSplicer</code>.
 *
 * Each locally defined method is partially implemented in this file.
 */
public class PythonServerPySource {
  /**
   * A write for the Python source file.
   */
  private LanguageWriterForPython d_lw = null;

  private Context d_context;

  /**
   * This stores code segments from the previous impl file.
   */
  private CodeSplicer d_splicer = null;

  /**
   * The class whose implementation is being written.
   */
  private Class d_cls = null;
  
  public PythonServerPySource(Class cls, Context context)
  {
    d_cls = cls;
    d_context = context;
  }

  private void addSplicerBlock(String name, String alt_msg)
  {
    if (!d_context.getConfig().getStrictProvenance()) {
      d_splicer.splice(name, d_lw, alt_msg);
    }
  }

  private void addSplicerBlock(String name, boolean isMethod, boolean addDefaults)
  {
    if (!d_context.getConfig().getStrictProvenance()) {
      if (addDefaults) {
        String defaultComment[] = {
          "This method has not been implemented" 
        };
        String defaultCode[] = {};
        String notImplExWithCCA[] = {
          "    # DO-DELETE-WHEN-IMPLEMENTING exception.begin(" + name + ")",
          "    noImpl = sidl.NotImplementedException.NotImplementedException()",
          "    noImpl.setNote(\"This method has not been implemented.\")",
          "    raise  sidl.NotImplementedException._Exception, noImpl",
          "    # DO-DELETE-WHEN-IMPLEMENTING exception.end(" + name + ")"
        };
        if ( d_context.getConfig().getCCAMode() == true ) { 
          defaultCode = notImplExWithCCA;
        }
        d_splicer.splice(name, d_lw, null, defaultComment, defaultCode);
      }
      else if(isMethod) {
        d_splicer.splice(name, d_lw, "", "pass");
      } else {
        d_splicer.splice(name, d_lw, "");
      }
    }
  }

  private void importReferences()
  {
    if (d_context.getConfig().getStrictProvenance()) {
      d_lw.println("import " + Python.auxModule(d_cls));
    }
    Iterator i = Utilities.sort(d_cls.getSymbolReferences()).iterator();
    while (i.hasNext()) {
      SymbolID id = (SymbolID)i.next();
      d_lw.println("import " + id.getFullName());
    }

    d_lw.println("import " + BabelConfiguration.getNotImplemented());
    d_lw.println();
  }

  private void addDocComment(Comment comment)
  {
    if (comment != null){
      final String[] text = comment.getComment();
      if (text != null && (text.length > 0)) {
        d_lw.println("\"\"\"\\");
        for(int i = 0; i < text.length; ++i) {
          d_lw.printlnUnformatted(LanguageWriterForPython.
                                  toPythonString(text[i]));
        }
        d_lw.printlnUnformatted("\"\"\"");
      }
    }
  }

  private void addExpectedIncomingComment(List argList)
  {
    if (argList.size() > 0){
      Iterator args = argList.iterator();
      d_lw.beginBlockComment(false);
      d_lw.println("sidl EXPECTED INCOMING TYPES");
      d_lw.println("============================");
      while (args.hasNext()) {
        Argument arg= (Argument)args.next();
        if (Argument.OUT != arg.getMode()) {
          d_lw.println(arg.getType().getTypeString() + " " +
                       arg.getFormalName());
        }
      }
      d_lw.endBlockComment(false);
    }
  }

  private void addExpectedReturnComment(Type returnType, List argList)
  {
    List returnTypes = TranslateArguments.extractOut(returnType, argList);
    final int size = returnTypes.size();
    d_lw.beginBlockComment(false);
    d_lw.println("sidl EXPECTED RETURN VALUE(s)");
    d_lw.println("=============================");
    if (size > 0) {
      Iterator args;
      if (size > 1) {
        args = returnTypes.iterator();
        d_lw.print("(" + ((Argument)args.next()).getFormalName());
        while (args.hasNext()) {
          d_lw.print(", " + ((Argument)args.next()).getFormalName());
        }
        d_lw.println(")");
      }
      args = returnTypes.iterator();
      while (args.hasNext()){
        Argument arg = (Argument)args.next();
        d_lw.println(arg.getType().getTypeString() + " " +
                     arg.getFormalName());
      }
    }
    else {
      d_lw.println("None");
    }
    d_lw.endBlockComment(false);
  }

  private void writeMethodImpl(Method m, boolean defaultSplicer)
  {
    List argList = m.getArgumentList();
    boolean needComma = false;
    Iterator args = argList.iterator();
    d_lw.print("def " + m.getLongMethodName() + "(");
    if (Method.STATIC != m.getDefinitionModifier()) {
      d_lw.print("self");
      needComma = true;
    }
    while (args.hasNext()) {
      Argument arg = (Argument)args.next();
      if (Argument.OUT != arg.getMode()) {
        if (needComma) {
          d_lw.print(", ");
        }
        else {
          needComma = true;
        }
        d_lw.print(arg.getFormalName());
      }
    }
    d_lw.println("):");
    d_lw.tab();
    addExpectedIncomingComment(argList);
    addExpectedReturnComment(m.getReturnType(), argList);
    addDocComment(m.getComment());
    addSplicerBlock(m.getLongMethodName(), true, defaultSplicer);
    d_lw.backTab();
    d_lw.println();
  }

  private void writeStaticMethods() throws CodeGenerationException
  {
    // Get a list of static methods
    Iterator i = d_cls.getStaticMethods(false).iterator();
    while (i.hasNext()) {
      Method m = (Method)i.next();
      if (IOR.generateHookMethods(d_cls, d_context)) {
        Method pre = m.spawnPreHook();
        Method post = m.spawnPostHook();
        writeMethodImpl(pre,false);
        writeMethodImpl(m,true);
        writeMethodImpl(post,false);
      } else {
        writeMethodImpl(m,true);
      }
    }
  }

  private void writeClass() throws CodeGenerationException {
    SymbolID id = d_cls.getSymbolID();
    if (d_context.getConfig().getStrictProvenance()) {
      d_lw.println("class " + id.getShortName() + "(" + Python.auxModule(d_cls) + "." + id.getShortName() + "):");
    }
    else {
      d_lw.println("class " + id.getShortName() + "(");
      String[] comment = {
        "Name of type(s) to inherit from.",
        "If desired, please change `PlaceHolder' to your own base class." 
      };
      String[] code = { "PlaceHolder" };
      d_splicer.splice("_inherits", d_lw, null, comment, code);
      d_lw.tab();
      d_lw.println("):");
      d_lw.backTab();
    }
    d_lw.tab();
    addDocComment(d_cls.getComment());
    d_lw.println();
    d_lw.writeCommentLine("All calls to sidl methods should use __IORself");
    if (!d_context.getConfig().getStrictProvenance()) {
      if(!IOR.isSIDLSymbol(id)) {
        d_lw.println();
        d_lw.writeCommentLine("Normal Babel creation pases in an IORself. If IORself == None");
        d_lw.writeCommentLine("that means this Impl class is being constructed for native delegation");
        d_lw.println("def __init__(self, IORself = None):");
        d_lw.tab();
        d_lw.println("if (IORself == None):");
        d_lw.tab();
        d_lw.println("self.__IORself = "+id.getFullName()+"."+id.getShortName()+"(impl = self)");
        d_lw.backTab();
        d_lw.println("else:");
        d_lw.tab();
        d_lw.println("self.__IORself = IORself");
        d_lw.backTab();
      } else {
        d_lw.println();
        d_lw.println("def __init__(self, IORself):");
        d_lw.tab();
        d_lw.println("self.__IORself = IORself");
      }
      addSplicerBlock("__init__", false, false);
      d_lw.backTab();
      d_lw.println();
      
      
      d_lw.writeCommentLine("Returns the IORself (client stub) of the Impl, mainly for use");
      d_lw.writeCommentLine("with native delegation");
      d_lw.println("def _getStub(self):");
      d_lw.tab();
      d_lw.println("return self.__IORself");
      d_lw.backTab();
      d_lw.println();
    }
    else {
      d_lw.println("def __init__(self, IORself = None):");
      d_lw.tab();
      d_lw.writeCommentLine("Next line is required for proper Babel initialization.");
      d_lw.println(Python.auxModule(d_cls) + "." + d_cls.getShortName() + ".__init__(self, IORself)");
      d_lw.backTab();
      d_lw.println();
    }

    Iterator i = d_cls.getNonstaticMethods(false).iterator();
    while (i.hasNext()) {
      Method m = (Method)i.next();
      if (!m.isAbstract()) {
        if (IOR.generateHookMethods(d_cls, d_context)) {
          Method pre = m.spawnPreHook();
          Method post = m.spawnPostHook();
          writeMethodImpl(pre,false);
          writeMethodImpl(m,true);
          writeMethodImpl(post,false);
        } else {
          writeMethodImpl(m,true);
        }
        
      }
    }
    d_lw.backTab();
  }

  private boolean implFileExists(String filename)
    throws CodeGenerationException
  {
    final boolean saveState = d_context.getFileManager().getJavaStylePackageGeneration();
    boolean result;
    try {
      d_context.getFileManager().setJavaStylePackageGeneration(true);
      result = d_context.getFileManager().fileExists(d_cls, 
                                                     d_cls.getSymbolType(),
                                                     "PYTHONSRC", filename);
    }
    finally {
      d_context.getFileManager().setJavaStylePackageGeneration(saveState);
    }
    return result;
  }
  
  public synchronized void generateCode()
    throws CodeGenerationException
  {
    final String filename = Python.implFilename(d_cls);
    if (d_context.getConfig().getStrictProvenance() && 
        implFileExists(filename)) {
      final boolean saveState = d_context.getFileManager().getJavaStylePackageGeneration();
      try {
        d_context.getFileManager().setJavaStylePackageGeneration(true);
        d_context.getFileManager().fakeFileCreation(d_cls, 
                                                    d_cls.getSymbolType(),
                                                    "PYTHONSRC", filename);
      }
      finally {
        d_context.getFileManager().setJavaStylePackageGeneration(saveState);
      }
    }
    else {
      try {
        if (!d_context.getConfig().getStrictProvenance()) {
          d_splicer = Python.getPySplicer(d_cls, filename, d_context);
        }
        d_lw = Python.createPyWriter(d_cls, filename,
                                     "Implementation of sidl class " + 
                                     d_cls.getFullName() + 
                                     " in Python.", d_context);
        addSplicerBlock("_initial", false, false);
        d_lw.println();
        importReferences();
        if (d_cls.hasStaticMethod(false)) {
          addSplicerBlock("_before_static", false, false);
          d_lw.println();
          writeStaticMethods();
        }

        d_lw.println("\nclass PlaceHolder:");
        d_lw.println("  \"\"\"work around a limitation of the grammar of Python < 2.5\"\"\"");
        d_lw.println("  pass\n");

        addSplicerBlock("_before_type", false, false);
        d_lw.println();
        writeClass();
        addSplicerBlock("_final", false, false);
        if ((!d_context.getConfig().getStrictProvenance()) && d_splicer.hasUnusedSymbolEdits()) {
          d_lw.println();
          d_lw.printlnUnformatted("raise SyntaxError, \"Unused splicer blocks in Python impl!\"");
          d_lw.beginBlockComment(true);
          d_lw.println(CodeConstants.C_BEGIN_UNREFERENCED_METHODS);
          d_lw.println(CodeConstants.C_UNREFERENCED_COMMENT1);
          d_lw.println(CodeConstants.C_UNREFERENCED_COMMENT2);
          d_lw.println(CodeConstants.C_UNREFERENCED_COMMENT3);
          d_lw.endBlockComment(true);
          d_splicer.outputUnusedSymbolEdits(d_lw); 
          d_lw.writeCommentLine(CodeConstants.C_END_UNREFERENCED_METHODS);
        }
      }
      catch (IOException ioe) {
        throw new CodeGenerationException("Unable to get code splicer");
      }
      finally {
        if (d_lw != null) {
          d_lw.close();
          d_lw = null;
        }
      }
    }
  }
}
