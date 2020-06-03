//
// File:        CxxImplHeader.java
// Package:     gov.llnl.babel.backend.ucxx
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

package gov.llnl.babel.backend.ucxx;

import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeConstants;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.CodeSplicer;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.ucxx.Cxx;
import gov.llnl.babel.backend.writers.LanguageWriterForCxx;
import gov.llnl.babel.backend.writers.LineRedirector;
import gov.llnl.babel.symbols.Class;
import gov.llnl.babel.symbols.Extendable;
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
  private Context d_context;

  /**
   * Create an object capable of generating the header file for a
   * BABEL extendable.
   *
   * @param ext   an interface or class symbol that needs a header
   *              file for a Cxx C extension class.
   */
  public CxxImplHeader(Extendable ext, Context context) {
    d_ext = ext;
    d_context = context;
  }
  
  /**
   * Add splicer block.
   *
   * @param ext         splicer block-specific name extension.
   * @param defComment  Default comment.
   */
  public void addSplicerBlock(String ext, String defComment)
  {
    d_splicer.splice(d_ext.getSymbolID().getFullName() + "." + ext, 
                     d_writer, defComment);
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
    final SymbolID id = d_ext.getSymbolID();
    final String fullname = id.getFullName();
    final int type = d_ext.getSymbolType();
    String filename = Cxx.generateFilename( id, 
                                            Cxx.FILE_ROLE_IMPL,
                                            Cxx.FILE_TYPE_CXX_HEADER,
                                            d_context);
    //System.out.println("Create " + filename + "...");

    try { 
      d_splicer = d_context.getFileManager().
        getCodeSplicer(id, type, filename, false, true);
      if (d_context.getConfig().getRenameSplicers()) {
        d_splicer.renameSymbol(fullname + "._misc", fullname + "._hmisc");
        d_splicer.renameSymbol(fullname + "._includes", 
                               fullname + "._hincludes");
      }
      d_writer = Cxx.createHeader( d_ext, Cxx.FILE_ROLE_IMPL, "IMPLHDRS",
                                   d_context );
      d_splicer.setLineRedirector( (LineRedirector) d_writer );
      d_writer.println();
      d_writer.openHeaderGuard( filename );

      Cxx.generateImplHeaderDependencyIncludes( d_writer, d_ext, false,
                                                d_context );

      spliceIncludes();

      Cxx.nestImplPackagesInNamespaces( d_writer, d_ext );
      
      writeClassBeginning();

      writeSIDLDefinedMethods();

      writeClassEnd();

      Cxx.unnestImplPackagesInNamespaces( d_writer, d_ext );

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
        d_writer.endBlockComment(true);
        d_splicer.outputUnusedSymbolEdits( d_writer );
        d_writer.writeCommentLine(CodeConstants.C_END_UNREFERENCED_METHODS);
      }
    } catch ( java.io.IOException ex) { 
      throw new CodeGenerationException("IOException : " + ex.getMessage() );
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
    addSplicerBlock("_hincludes", "includes or arbitrary code");
    d_writer.println();
  }

  private void writeClassBeginning() throws CodeGenerationException { 
    String name = d_ext.getSymbolID().getShortName();
    SymbolID id = d_ext.getSymbolID();

    d_writer.writeComment( d_ext, true );

    d_writer.println("class " + name + "_impl : public virtual " 
      + Cxx.getSymbolName(d_ext.getSymbolID()) + " ");
    
    //If this class has a parent (in SIDL) have this impl inherit from the
    //parent impl.  There should only be one.  (Interfaces have no impls) NOT!
//     java.util.Collection parents = d_ext.getParents(false);
//     boolean seenClass = false;
//     for(Iterator i = parents.iterator(); i.hasNext(); ){
//       Extendable parent = (Extendable) i.next();
//       if(!parent.isInterface()) { //If it's a class, do something
//         if(seenClass)
//           throw new CodeGenerationException(name + "_impl has two classes "
//                       + "as parents!");
//         seenClass = true; 
//         String parentName = Cxx.getSymbolName(parent.getSymbolID(), "");
//         d_writer.print(", " + parentName + "_impl ");
        
//       }
//     }

    addSplicerBlock("_inherits", "optional inheritance here");
    d_writer.println();
    d_writer.println("{");
    
    //An internal class used to call super function
    writeSuperClass();
    
    d_writer.println();
    d_writer.writeCommentLine("All data marked protected will be accessable "
      + "by ");
    d_writer.writeCommentLine("descendant Impl classes");
    d_writer.println("protected:");
    d_writer.tab();
    d_writer.println();

    d_writer.println("bool _wrapped;");
    d_writer.println();

    addSplicerBlock("_implementation", "additional details");
    d_writer.println();

    if(!d_ext.isAbstract() && !IOR.isSIDLSymbol(id)) {
      d_writer.backTab();
      d_writer.println("public:");
      d_writer.tab();
      d_writer.writeCommentLine("default constructor, used for data wrapping"
                                + "(required)");
      d_writer.println(name+"_impl();");
    } else {
      d_writer.backTab();
      d_writer.println("private:");
      d_writer.tab();
      d_writer.writeCommentLine("default constructor, do not use! ");
      d_writer.println(name+"_impl();");
      d_writer.println("public:");
    }
      
    //Start impl side conversion from ior to stub
    d_writer.writeCommentLine("sidl constructor (required)");
    d_writer.writeCommentLine("Note: alternate Skel constructor doesn't call "
      + "addref()");
    d_writer.writeCommentLine("(fixes bug #275)");
    d_writer.print(name + "_impl( " + IOR.getObjectName(id) 
                   + " * ior ) : StubBase(ior,true)");
    
    //Initialize any interface cache variables with interface ctors
    d_writer.tab();
    if(d_ext.hasParentInterfaces()) {
      d_writer.println(", ");
      Cxx.writeCallsToParentInterfaceConstructors(d_writer, (Class)d_ext, "ior");
    }
    d_writer.backTab();

    //If there are overwritten class methods, there will be a 'super' object
    //to initialize
    if(hasOverwrittenClassMethods()) { 
      d_writer.print(", super(ior, " + id.getShortName()+ "::_get_ext())");
    }
    d_writer.println(" , _wrapped(false) {");
    d_writer.tab();
    
    //we can record the data pointer in the IOR object right away
    d_writer.println("ior->d_data = this;");

    if(d_context.getConfig().getFastCall()) {
      d_writer.printlnUnformatted("#ifdef SIDL_FASTCALL_EAGER_CACHING");
      d_writer.println("_cache_entry_points();");
      d_writer.printlnUnformatted("#endif");
    }
    
    d_writer.println("_ctor();");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
    //end constructor


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

    d_writer.writeCommentLine("true if this object was created by a user newing the impl");
    d_writer.println("inline bool _isWrapped() {return _wrapped;}");
    d_writer.println();

    d_writer.writeCommentLine("static class initializer");
    d_writer.println("static void _load();");
    d_writer.println();
  }

  //Test to see if the local Extandable has any methods to overwrite.
  private boolean hasOverwrittenClassMethods() throws CodeGenerationException {
    if(!d_ext.isInterface()) {
      try {
        Class cls = (Class) d_ext; 

        if(cls.hasOverwrittenMethods())
          return true;
      } catch (Exception ex) {
        throw new CodeGenerationException("Attempted to cast non Class to "
                    + "Class");
      }
    }
    return false;
  }

  private void writeClassEnd() {
    String name = d_ext.getSymbolID().getShortName();
    d_writer.backTab();
    d_writer.print("};  "); // end of class
    d_writer.writeCommentLine("end class " + name + "_impl" );
    d_writer.println();
  }
  
  private void writeMiscStuff() { 
    addSplicerBlock("_hmisc", "miscellaneous things");
    d_writer.println();
  }

  /**
   *  This method generates a class called "Super" that contains functions
   *  for calling super functions from the Impls.
   */
  private void writeSuperClass() throws CodeGenerationException { 
    if(!d_ext.isInterface()) {
      try {
        Class cls = (Class) d_ext; 
        
        if(cls.hasOverwrittenMethods()) {
          CxxStubHeader.generateSupers(cls, d_writer, d_context);
          d_writer.println();
          d_writer.println("private:");
          d_writer.writeCommentLine("Use this to dispatch to super functions.");
          d_writer.println("Super super;");
          d_writer.println();
        }
      } catch (Exception ex) {
        throw new CodeGenerationException("Attempted to cast non Class to "
                    + "Class");
      }   
    }
  }
  
  private void writeSIDLDefinedMethods() throws CodeGenerationException { 
    d_writer.backTab();
    d_writer.println("public:");
    d_writer.tab();

    Iterator m = null;
    m = d_ext.getStaticMethods(false).iterator();
    boolean hasInvariants = d_ext.hasInvClause(true);
    while (m.hasNext()) {
      Method method = (Method) m.next();
      Cxx.generateMethodSignature( d_writer, d_context, method, 
                                   "user defined static method", 
                                   Cxx.FILE_ROLE_IMPL, true, hasInvariants);
      if (IOR.generateHookMethods(d_ext, d_context)) {
        Method pre  = method.spawnPreHook();
        Method post = method.spawnPostHook();
        Cxx.generateMethodSignature( d_writer, d_context,  pre, 
                                     "User Defined static method pre-hook", 
                                     Cxx.FILE_ROLE_IMPL, true, false);
        Cxx.generateMethodSignature( d_writer, d_context, post, 
                                     "user defined static method post-hook", 
                                     Cxx.FILE_ROLE_IMPL, true, false);
      }
    }
    d_writer.println();
    
    m = d_ext.getNonstaticMethods(false).iterator();
    while (m.hasNext()) {
      Method method = (Method) m.next();
      if ( !method.isAbstract() ) { 
        Cxx.generateMethodSignature( d_writer, d_context, method, 
                                     "user defined non-static method.", 
                                     Cxx.FILE_ROLE_IMPL, true, hasInvariants);
        if (IOR.generateHookMethods(d_ext, d_context)) {
          Method pre  = method.spawnPreHook();
          Method post = method.spawnPostHook();
          Cxx.generateMethodSignature( d_writer, d_context, pre, 
                                       "User Defined static method pre-hook", 
                                       Cxx.FILE_ROLE_IMPL, true, false);
          Cxx.generateMethodSignature( d_writer, d_context, post, 
                                       "user defined static method post-hook", 
                                       Cxx.FILE_ROLE_IMPL, true, false);
        }
      }
    }
  }
}
