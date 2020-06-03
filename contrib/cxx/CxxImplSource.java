//
// File:        CxxImplSource.java
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

import gov.llnl.babel.backend.CodeConstants;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.CodeSplicer;
import gov.llnl.babel.backend.cxx.Cxx;
import gov.llnl.babel.backend.FileManager;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.Utilities;
import gov.llnl.babel.backend.writers.LanguageWriterForCxx;
import gov.llnl.babel.backend.writers.LineRedirector;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.Type;
import java.util.Iterator;
import java.util.List;

/**
 * Create and write a header for a Cxx C extension class to wrap a 
 * BABEL extendable in a Cxx object. The header has to expose a 
 * function to create a wrapped IOR, a function to check if a 
 * <code>PyObject</code> is an instance of this extension type, and
 * an import macro.
 */
public class CxxImplSource {
  private Extendable d_ext = null;
  private LanguageWriterForCxx d_writer = null;
  private CodeSplicer d_splicer = null;

  private final static String USER_DEF    = "user-defined ";
  
  /**
   * Create an object capable of generating the header file for a
   * BABEL extendable.
   *
   * @param ext   an interface or class symbol that needs a header
   *              file for a Cxx C extension class.
   */
  public CxxImplSource(Extendable ext) {
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
    final String nm = "CxxImplSource: generateCode: ";
    final SymbolID id = d_ext.getSymbolID();
    final int type= d_ext.getSymbolType();
    String filename = Cxx.generateFilename( id, Cxx.FILE_ROLE_IMPL,
                                            Cxx.FILE_TYPE_CXX_SOURCE );
    //System.out.println("Create " + filename + "..." );
    
    try { 
      d_splicer = FileManager.getInstance().
        getCodeSplicer(id, type, filename);
      d_writer = Cxx.createSource( d_ext, Cxx.FILE_ROLE_IMPL, "IMPLSRCS");
      d_splicer.setLineRedirector( (LineRedirector) d_writer );
      writeIncludes();
      writeCtorDtor();
      writeCheckErr();
      writeSIDLDefinedMethods();

      d_splicer.splice(d_ext.getSymbolID().getFullName() + "._misc", 
        d_writer, "miscellaneous code");
      d_writer.println();

      if (d_splicer.hasUnusedSymbolEdits()) {
        d_writer.printlnUnformatted("#error File has unused splicer blocks.");
        d_writer.beginBlockComment(true);
        d_writer.println(CodeConstants.C_BEGIN_UNREFERENCED_METHODS);
        d_writer.println(CodeConstants.C_UNREFERENCED_COMMENT1);
        d_writer.println(CodeConstants.C_UNREFERENCED_COMMENT2);
        d_writer.println(CodeConstants.C_UNREFERENCED_COMMENT3);
        d_writer.endBlockComment(true);
        d_splicer.outputUnusedSymbolEdits( d_writer.getPrintWriter() );
        d_writer.writeCommentLine(CodeConstants.C_END_UNREFERENCED_METHODS);
      }
    } catch ( java.io.IOException ex) { 
      throw new CodeGenerationException(nm + "IOException : " 
                  + ex.getMessage());
    } finally { 
      if (d_writer != null) {
        d_writer.close();
        d_writer = null;
      }
    }
  }

  private void writeIncludes() { 
    d_writer.generateInclude( Cxx.generateFilename( d_ext.getSymbolID(), 
                                                    Cxx.FILE_ROLE_IMPL,  
                                                    Cxx.FILE_TYPE_CXX_HEADER),
                              false );
    d_writer.println();
    d_splicer.splice(d_ext.getSymbolID().getFullName() + "._includes", 
      d_writer, "additional includes or code");
    d_writer.println();
  }                   
  
  private void writeCtorDtor() {
    d_writer.writeCommentLine(USER_DEF + "constructor.");
    d_writer.print("void " 
      + Utilities.replace(d_ext.getSymbolID().getFullName(),".","::"));
    d_writer.println("_impl::_ctor() {");
    d_writer.tab();
    d_splicer.splice(d_ext.getSymbolID().getFullName() + "._ctor", 
      d_writer, "constructor");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();

    d_writer.writeCommentLine(USER_DEF + "destructor.");
    d_writer.print("void " 
      + Utilities.replace(d_ext.getSymbolID().getFullName(),".","::"));
    d_writer.println("_impl::_dtor() {");
    d_writer.tab();
    d_splicer.splice(d_ext.getSymbolID().getFullName() + "._dtor",
      d_writer, "destructor");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();

    d_writer.writeCommentLine("static class initializer.");
    d_writer.print("void " 
      + Utilities.replace(d_ext.getSymbolID().getFullName(),".","::"));
    d_writer.println("_impl::_load() {");
    d_writer.tab();
    d_splicer.splice(d_ext.getSymbolID().getFullName() + "._load", 
      d_writer, "class initialization");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  private void writeCheckErr() throws CodeGenerationException { 
    final String handler_desc = "assertion exception handler.";
    if (IOR.supportAssertions(d_ext)) {
      SymbolID id = d_ext.getSymbolID();
      Method method;
      if (d_ext.hasStaticMethod(true)) {
        method = IOR.getBuiltinMethod(IOR.CHECK_ERROR, id, true);
        d_writer.writeCommentLine(USER_DEF + "static " + handler_desc);
        d_writer.println();
        generateMethod( method, "" );
      }
      d_writer.writeCommentLine(USER_DEF + handler_desc);
      method = IOR.getBuiltinMethod(IOR.CHECK_ERROR, id, false);
      d_writer.println();
      generateMethod( method, "" );
    }
  }

  private void writeSIDLDefinedMethods() throws CodeGenerationException { 
    final String meth_desc = "methods:";
    String desc = "static ";
    List static_methods = (List) d_ext.getStaticMethods(false);
    if ( static_methods.size() > 0 ) { 
      d_writer.writeCommentLine(USER_DEF + desc + meth_desc);
      for (Iterator m = static_methods.iterator(); m.hasNext(); ) {
        Method method = (Method) m.next();
        if (IOR.supportHooks(d_ext)) {
          generateMethod( method, IOR.GENERIC_PRE_SUFFIX );
          generateMethod( method, "" );
          generateMethod( method, IOR.GENERIC_POST_SUFFIX );
        } else {
          generateMethod( method, "" );
        }
      }
    } else { 
      d_writer.writeCommentLine(USER_DEF + desc + meth_desc + " (none)");
    }
    
    desc = "non-static ";
    d_writer.println();
    List nonstatic_methods = (List) d_ext.getNonstaticMethods(false);
    if ( nonstatic_methods.size() > 0 ) { 
      d_writer.writeCommentLine(USER_DEF + desc + meth_desc);
      for (Iterator m = nonstatic_methods.iterator(); m.hasNext(); ) {
        Method method = (Method) m.next();
	if ( !method.isAbstract() ) { 	    
	    generateMethod( method, "" );
	}
      }
    } else { 
      d_writer.writeCommentLine(USER_DEF + desc + meth_desc + " (none)");
    }
    d_writer.println();

  }

  private void generateMethod( Method method, String suffix ) 
    throws CodeGenerationException 
  { 
    if ( method == null ) { return ; }

    boolean isOrig   = suffix.equals("");
    boolean doReturn = suffix.equals(IOR.GENERIC_POST_SUFFIX) 
                       && (method.getReturnType().getType() != Type.VOID);

    d_writer.writeComment ( method, true );

    if (isOrig) {
      d_writer.println( Cxx.getReturnString( method.getReturnType() ) );
    } else {
      d_writer.println( "void" );
    }
    d_writer.print(Utilities.replace(d_ext.getSymbolID().getFullName(),".",
                                     "::"));
    d_writer.print("_impl::");
    d_writer.print( method.getShortMethodName() + suffix );
    
    if ( method.getArgumentList().size() > 0 ) { 
      d_writer.println(" (");
      d_writer.tab();
      Cxx.generateArgumentList( d_writer, method, true );
      if (doReturn) {
        d_writer.println("," + Cxx.FUNCTION_RESULT);
      }
      d_writer.println(" ) ");
      d_writer.backTab();
      Cxx.generateThrowsList( d_writer, method, false );
    } else { 
      d_writer.print(" (");      
      if (doReturn) {
        d_writer.print(Cxx.FUNCTION_RESULT);
      }
      d_writer.println(")");      
      Cxx.generateThrowsList( d_writer, method, false);
      d_writer.println();
    }
    d_writer.println("{");
    d_writer.tab();
    d_splicer.splice(d_ext.getSymbolID().getFullName() + "." 
      + method.getLongMethodName() + suffix, d_writer, 
      method.getShortMethodName() + " method");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();    
  }
}
