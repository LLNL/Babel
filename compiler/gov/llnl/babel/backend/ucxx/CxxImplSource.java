//
// File:        CxxImplSource.java
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

import gov.llnl.babel.BabelConfiguration;
import gov.llnl.babel.Context;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.CodeConstants;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.CodeSplicer;
import gov.llnl.babel.backend.ucxx.Cxx;
import gov.llnl.babel.backend.ucxx.CxxStubSource;
import gov.llnl.babel.backend.writers.LanguageWriterForCxx;
import gov.llnl.babel.backend.writers.LineRedirector;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Class;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.SymbolID;
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
  private Context d_context;
  
  /**
   * Create an object capable of generating the header file for a
   * BABEL extendable.
   *
   * @param ext   an interface or class symbol that needs a header
   *              file for a Cxx C extension class.
   */
  public CxxImplSource(Extendable ext,
                       Context context) {
    d_ext = ext;
    d_context = context;
  }

  /**
   * Add splicer block.
   *
   * @param ext         splicer block-specific name extension.
   * @param method      Either a method instance, if splicer is for a method,
   *                    or null. 
   * @param addDefaults TRUE if splicer defaults to be added for method;
   *                    otherwise, FALSE.
   * @param defComment  Default comment (for non-method splicer blocks).
   */
  public void addSplicerBlock(String ext, Method method, boolean addDefaults,
                              String defComment)
  {
    String name  = d_ext.getSymbolID().getFullName() + "." + ext;
    
    if (method != null) {
      d_writer.pushLineBreak(false);
      String mName = method.getShortMethodName();
      if (addDefaults) {
        if ( d_context.getConfig().getCCAMode()==true ) { 
          final String defaultComment[] = {"This method has not been implemented",};
          final String defaultCode[]={
              "  // DO-DELETE-WHEN-IMPLEMENTING exception.begin(" + name + ")",
              "  ::sidl::NotImplementedException ex = "
              + "::sidl::NotImplementedException::_create();",
              "  ex.setNote(\"This method has not been implemented\");",
              "  ex.add(__FILE__, __LINE__, \""+method.getLongMethodName()+"\");",
              "  throw ex;",
              "  // DO-DELETE-WHEN-IMPLEMENTING exception.end(" + name + ")"
          };
          d_splicer.splice(name, d_writer, mName + " method", defaultComment, defaultCode);
        } else { 
          d_splicer.splice(name, d_writer, "", null, null );
        }
      } else {
        d_splicer.splice(name, d_writer, mName);
      } 
      d_writer.popLineBreak();
    } else {
      d_splicer.splice(name, d_writer, defComment);
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
    final SymbolID id = d_ext.getSymbolID();
    final int type= d_ext.getSymbolType();
    String filename = Cxx.generateFilename( id, 
                                            Cxx.FILE_ROLE_IMPL,
                                            Cxx.FILE_TYPE_CXX_SOURCE,
                                            d_context);
    //System.out.println("Create " + filename + "..." );
    
    try { 
      d_splicer = d_context.getFileManager().
        getCodeSplicer(id, type, filename, true, true);
      d_writer = Cxx.createSource( d_ext, Cxx.FILE_ROLE_IMPL, "IMPLSRCS",
                                   d_context );
      d_splicer.setLineRedirector( (LineRedirector) d_writer );
      writeIncludes();

      writeCtorDtor();

      writeSIDLDefinedMethods();

      addSplicerBlock("_misc", null, false, "miscellaneous code");
      d_writer.println();

      CxxStubSource.generateSupers((Class)d_ext, d_writer, d_context);

      if (d_splicer.hasUnusedSymbolEdits()) {
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

  private void writeIncludes() throws CodeGenerationException {
    Class cls = (Class) d_ext;

    d_writer.generateInclude( Cxx.generateFilename( d_ext.getSymbolID(), 
                                                    Cxx.FILE_ROLE_IMPL,  
                                                    Cxx.FILE_TYPE_CXX_HEADER,
                                                    d_context),
                              false );
    d_writer.println();
    Cxx.generateImplSourceIncludes(d_writer, cls, d_context);
    d_writer.generateInclude( Cxx.generateFilename(BabelConfiguration.getNotImplemented(),
                                                    Cxx.FILE_ROLE_STUB, 
                                                    Cxx.FILE_TYPE_CXX_HEADER), 
                              true );

    if (cls.getOverwrittenClassMethods().size() > 0) {
      d_writer.generateInclude( Cxx.generateFilename( "sidl.LangSpecificException",
                                                      Cxx.FILE_ROLE_STUB, 
                                                      Cxx.FILE_TYPE_CXX_HEADER), 
                                true );
      d_writer.generateInclude("sidl_String.h", true);
    }

    addSplicerBlock("_includes", null, false, "additional includes or code");
    d_writer.println();
  }                   
  
  private void writeCtorDtor() {
    SymbolID id = d_ext.getSymbolID();
    String name = Cxx.getImplSymbolNameWithoutLeadingColons(d_ext.getSymbolID(), null);

    if(!d_ext.isAbstract() && !IOR.isSIDLSymbol(id)) {
    d_writer.writeCommentLine("special constructor, used for data wrapping"
                              + "(required).  Do not put code here unless you"+
                              " really know what you're doing!");
    d_writer.println(name+"_impl::"+id.getShortName()+"_impl() : StubBase("+
                     Cxx.reinterpretCast("void*",Cxx.getObjectName(id)+"::_wrapObj(" + Cxx.reinterpretCast("void*", "this") + ")")+
                     ",false) , _wrapped(true){ ");
    d_writer.tab();
    addSplicerBlock("_ctor2", null, false, "ctor2");
    d_writer.backTab();
    d_writer.println("}"); 
    d_writer.println();
    } else {
      d_writer.writeCommentLine("default constructor, not to be used!");
    }

    d_writer.writeCommentLine("user defined constructor");
    d_writer.print("void " 
      + Cxx.getImplSymbolNameWithoutLeadingColons(d_ext.getSymbolID(), null));
    d_writer.println("_impl::_ctor() {");
    d_writer.tab();
    addSplicerBlock("_ctor", null, false, "constructor");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();

    d_writer.writeCommentLine("user defined destructor");
    d_writer.print("void " 
      + Cxx.getImplSymbolNameWithoutLeadingColons(d_ext.getSymbolID(), null));
    d_writer.println("_impl::_dtor() {");
    d_writer.tab();
    addSplicerBlock("_dtor", null, false, "destructor");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();

    d_writer.writeCommentLine("static class initializer");
    d_writer.print("void " 
      + Cxx.getImplSymbolNameWithoutLeadingColons(d_ext.getSymbolID(), null));
    d_writer.println("_impl::_load() {");
    d_writer.tab();
    addSplicerBlock("_load", null, false, "class initialization");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  private void writeSIDLDefinedMethods() throws CodeGenerationException { 
    boolean hasInvariants = d_ext.hasInvClause(true);
    List static_methods = (List) d_ext.getStaticMethods(false);
    if ( static_methods.size() > 0 ) { 
      d_writer.writeCommentLine("user defined static methods:");
      for (Iterator m = static_methods.iterator(); m.hasNext(); ) {
        Method method = (Method) m.next();
        if (IOR.generateHookMethods(d_ext, d_context)) {
          Method pre = method.spawnPreHook();
          Method post = method.spawnPostHook();
          generateMethod(pre, false, false);
          generateMethod(method, true, hasInvariants);
          generateMethod(post, false, false);
        } else {
          generateMethod(method, true, hasInvariants);
        }
      }
    } else { 
      d_writer.writeCommentLine("user defined static methods: (none)");
    }
    
    d_writer.println();
    List nonstatic_methods = (List) d_ext.getNonstaticMethods(false);
    if ( nonstatic_methods.size() > 0 ) { 
      d_writer.writeCommentLine("user defined non-static methods:");
      for (Iterator m = nonstatic_methods.iterator(); m.hasNext(); ) {
        Method method = (Method) m.next();
	if ( !method.isAbstract() ) { 	    
          if (IOR.generateHookMethods(d_ext, d_context)) {
            Method pre = method.spawnPreHook();
            Method post = method.spawnPostHook();
            generateMethod(pre, false, false);
            generateMethod(method, true, hasInvariants);
            generateMethod(post, false, false);
          } else {
            generateMethod(method, true, hasInvariants);
          }
	}
      }
    } else { 
      d_writer.writeCommentLine("user defined non-static methods: (none)");
    }
    d_writer.println();

  }

  private void generateMethod( Method method, boolean defaultSplicer,
                               boolean hasInvariants ) 
    throws CodeGenerationException 
  { 
    if ( method == null ) { return ; }

    d_writer.writeComment ( method, true );

    d_writer.println( Cxx.getReturnString( method.getReturnType(), d_context ) );
    d_writer.print(Cxx.getImplSymbolNameWithoutLeadingColons(
                        d_ext.getSymbolID(), null));
    d_writer.print("_impl::");
    d_writer.print( method.getShortMethodName() );
    d_writer.print("_impl");

    if ( method.getArgumentList().size() > 0 ) { 
      d_writer.println(" (");
      d_writer.tab();
      Cxx.generateArgumentList( d_writer, method, d_context, true, false );
      d_writer.println(" ) ");
      d_writer.backTab();
      Cxx.generateThrowsList( d_writer, method, false, hasInvariants, 
                              d_context);
      d_writer.println("{");
    } else { 
      d_writer.println(" () ");      
      Cxx.generateThrowsList( d_writer, method, false, hasInvariants, 
                              d_context);
      d_writer.println();
      d_writer.println("{");
    }
    d_writer.tab();

    addSplicerBlock(method.getLongMethodName(), method, defaultSplicer, null);
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();    
  }

}
