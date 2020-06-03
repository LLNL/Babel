//
// File:        CxxImplHeader.java
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
import gov.llnl.babel.backend.writers.LanguageWriterForCxx;
import gov.llnl.babel.backend.writers.LineRedirector;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Class;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.SymbolID;
import java.util.Iterator;


/**
 * Create and write a header for a Cxx C extension class to wrap a 
 * BABEL extendable in a Cxx object. The header has to expose a 
 * function to create a wrapped IOR, a function to check if a 
 * <code>PyObject</code> is an instance of this extension type, and
 * an import macro.
 */
public class CxxImplHeader {
  private Extendable d_ext = null;
  private LanguageWriterForCxx d_writer = null;
  private CodeSplicer d_splicer = null;
  
  /**
   * Create an object capable of generating the header file for a
   * BABEL extendable.
   *
   * @param ext   an interface or class symbol that needs a header
   *              file for a Cxx C extension class.
   */
  public CxxImplHeader(Extendable ext) {
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
    final String nm = "CxxImplHeader.generateCode: ";
    final SymbolID id = d_ext.getSymbolID();
    final int type = d_ext.getSymbolType();
    String filename = Cxx.generateFilename( id, Cxx.FILE_ROLE_IMPL,
                                            Cxx.FILE_TYPE_CXX_HEADER );
    //System.out.println("Create " + filename + "...");

    try { 
      d_splicer = FileManager.getInstance().
        getCodeSplicer(id, type, filename);
      d_writer = Cxx.createHeader( d_ext, Cxx.FILE_ROLE_IMPL, "IMPLHDRS");
      d_splicer.setLineRedirector( (LineRedirector) d_writer );
      d_writer.println();
      d_writer.openHeaderGuard( filename );

      Cxx.generateImplDependencyIncludes( d_writer, d_ext, false );

      spliceIncludes();

      Cxx.nestPackagesInNamespaces( d_writer, d_ext );
      
      writeClassBeginning();

      writeSIDLDefinedMethods();

      writeClassEnd();

      Cxx.unnestPackagesInNamespaces( d_writer, d_ext );

      writeMiscStuff();

      d_writer.closeHeaderGuard();

      if (d_splicer.hasUnusedSymbolEdits()) {
        d_writer.println();
        d_writer.printlnUnformatted("#error File has unused splicer blocks.");
        d_writer.beginBlockComment(true);
        d_writer.println(CodeConstants.C_BEGIN_UNREFERENCED_METHODS);
        d_writer.println(CodeConstants.C_UNREFERENCED_COMMENT1);
        d_writer.println(CodeConstants.C_UNREFERENCED_COMMENT2);
        d_writer.println(CodeConstants.C_UNREFERENCED_COMMENT3);
        d_writer.println(CodeConstants.C_END_UNREFERENCED_METHODS);
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

  /**
   * write all the include statements including a block for 
   * user's includes.
   */
  private void spliceIncludes() { 
    d_writer.println();

    String includes = d_ext.getSymbolID().getFullName() + "." + "_includes";
    d_splicer.splice( includes, d_writer, "includes or arbitrary code");
    d_writer.println();
  }

  private void writeClassBeginning() throws CodeGenerationException {
    String name = d_ext.getSymbolID().getShortName();
    SymbolID id = d_ext.getSymbolID();
    String splicer_symbol_impl = d_ext.getSymbolID().getFullName() 
                                   + "._implementation";
    String splicer_symbol_inherit = d_ext.getSymbolID().getFullName() 
                                      + "._inherits";
    /*    d_writer.println("class " + name + "_impl : public " 
            + name + "_skel {");
     */
    d_writer.writeComment( d_ext, true );

    d_writer.println("class " + name + "_impl");
    d_splicer.splice( splicer_symbol_inherit, d_writer, 
      "optional inheritance here");
    d_writer.println("{");
    d_writer.println();
    //An internal class used to call super function
    writeSuperClass();

    d_writer.println("private:");
    d_writer.tab();
    d_writer.writeCommentLine("Pointer back to IOR.");
    d_writer.writeCommentLine("Use this to dispatch back through IOR vtable.");
    d_writer.println( name + " self;");
    d_writer.println();
 
 
    d_splicer.splice( splicer_symbol_impl, d_writer, "additional details");
    d_writer.println();
    
    d_writer.backTab();
    d_writer.println("private:");
    d_writer.tab();
    d_writer.writeCommentLine("private default constructor (required)");
    d_writer.println(name + "_impl() ");
    if(hasOverwrittenClassMethods()) {
      d_writer.println(": super() ");
    }
    d_writer.println("{} ");
    d_writer.println();


    d_writer.backTab();
    d_writer.println("public:");
    d_writer.tab();
    d_writer.writeCommentLine("sidl constructor (required)");
    d_writer.writeCommentLine("Note: alternate Skel constructor doesn't call "
      + "addref()");
    d_writer.writeCommentLine("(fixes bug #275)");
    if(hasOverwrittenClassMethods()) {
      d_writer.println(name + "_impl( " + IOR.getObjectName(id) 
        + " * s ) : self(s,true), super(s, " + id.getShortName() 
        + "::_get_ext()) { _ctor(); }");
    } else {
      d_writer.println(name + "_impl( " + IOR.getObjectName(id) 
        + " * s ) : self(s,true) { _ctor(); }");
    }
    d_writer.println();
    d_writer.writeCommentLine("user defined construction");
    d_writer.println("void _ctor();");
    d_writer.println();

    d_writer.writeCommentLine("virtual destructor (required)");
    d_writer.println("virtual ~" + name + "_impl() { _dtor(); }");
    d_writer.println();
    d_writer.writeCommentLine("user defined destruction");
    d_writer.println("void _dtor();");
    d_writer.println();

    d_writer.writeCommentLine("static class initializer");
    d_writer.println("static void _load();");
    d_writer.println();

    final String handler_desc = "assertion error handler.";
    final String user_def     = "user-defined ";
    if (IOR.supportAssertions(d_ext)) {
      if (d_ext.hasStaticMethod(true)) {
        Cxx.generateMethodSignature(d_writer, 
          IOR.getBuiltinMethod(IOR.CHECK_ERROR, id, true), "", user_def
            + "static " + handler_desc, Cxx.FILE_ROLE_IMPL, false);
      }
      Cxx.generateMethodSignature(d_writer, 
        IOR.getBuiltinMethod(IOR.CHECK_ERROR, id), "", user_def + handler_desc,
        Cxx.FILE_ROLE_IMPL, false);
    }
  }

  private void writeClassEnd() {
    String name = d_ext.getSymbolID().getShortName();
    d_writer.backTab();
    d_writer.print("};  "); // end of class
    d_writer.writeCommentLine("end class " + name + "_impl" );
    d_writer.println();
  }
  
  private void writeMiscStuff() { 
    String splicer_symbol = d_ext.getSymbolID().getFullName() + "._misc";
    d_splicer.splice( splicer_symbol, d_writer, "miscellaneous things");
    d_writer.println();
  }

  /**
   *  This method generates a class called "Super" that contains functions
   *  for calling super functions from the Impls.
   */
  private void writeSuperClass() throws CodeGenerationException { 
    final String nm = "CxxImplHeader.writeSuperClass: ";
    if(!d_ext.isInterface()) {
      try {
        Class cls = (Class) d_ext; 

        if(cls.hasOverwrittenMethods()) {
          CxxStubHeader.generateSupers(cls, d_writer);
          d_writer.println();
          d_writer.println("private:");
          d_writer.writeCommentLine(nm + "Use this to dispatch to super "
            + "functions.");
          d_writer.println("Super super;");
          d_writer.println();
        }
      } catch (Exception ex) {
        throw new CodeGenerationException(nm + "Attempted to cast non Class to "
                    + "Class.");
      }   
    }
  }
      
  //Test to see if the local Extandable has any methods to overwrite.
  private boolean hasOverwrittenClassMethods() throws CodeGenerationException {
    final String nm = "CxxImplHeader.hasOverwrittenClassMethods: ";
    if(!d_ext.isInterface()) {
      try {
        Class cls = (Class) d_ext; 

        if(cls.hasOverwrittenMethods()) return true;
      } catch (Exception ex) {
        throw new CodeGenerationException(nm + "Attempted to cast non Class to "
                    + "Class.");
      }
    }
    return false;
  }

  private void writeSIDLDefinedMethods() throws CodeGenerationException { 
    final String user_def = "user defined ";
    final String pre      = "pre-call ";
    final String post     = "post-call ";
    final String hook     = "hooks.";
    final String meth     = "method.";
    d_writer.backTab();
    d_writer.println("public:");
    d_writer.tab();

    String desc = "static ";
    Iterator m = null;
    m = d_ext.getStaticMethods(false).iterator();
    while (m.hasNext()) {
      Method method = (Method) m.next();
      if (IOR.supportHooks(d_ext)) {
        Cxx.generateMethodSignature(d_writer, method, IOR.GENERIC_PRE_SUFFIX,
          user_def + desc + pre + hook, Cxx.FILE_ROLE_IMPL, true);
        Cxx.generateMethodSignature(d_writer, method, "", user_def + desc 
          + meth, Cxx.FILE_ROLE_IMPL, true);
        Cxx.generateMethodSignature(d_writer, method, IOR.GENERIC_POST_SUFFIX,
          user_def + desc + post + hook, Cxx.FILE_ROLE_IMPL, true);
      } else {
        Cxx.generateMethodSignature( d_writer, method, "", user_def + desc 
          + meth, Cxx.FILE_ROLE_IMPL, true);
      }
    }
    d_writer.println();

    desc = "non-static ";
    m = d_ext.getNonstaticMethods(false).iterator();
    while (m.hasNext()) {
      Method method = (Method) m.next();
      if ( !method.isAbstract() ) { 
        if (IOR.supportHooks(d_ext)) {
	  Cxx.generateMethodSignature(d_writer, method, IOR.GENERIC_PRE_SUFFIX,
            user_def + desc + pre + hook, Cxx.FILE_ROLE_IMPL, true);
	  Cxx.generateMethodSignature(d_writer, method, "", user_def + desc 
            + meth, Cxx.FILE_ROLE_IMPL, true );
	  Cxx.generateMethodSignature(d_writer, method, IOR.GENERIC_POST_SUFFIX,
            user_def + desc + post + hook, Cxx.FILE_ROLE_IMPL, true);
        } else {
	  Cxx.generateMethodSignature(d_writer, method, "", user_def + desc 
            + meth, Cxx.FILE_ROLE_IMPL, true );
        }
      }
    }
  }
}
