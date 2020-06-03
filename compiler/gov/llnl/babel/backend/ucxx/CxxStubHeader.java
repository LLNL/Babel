//
// File:        CxxStubHeader.java
// Package:     gov.llnl.babel.backend.ucxx
// Revision:    @(#) $Revision: 7480 $
// Date:        $Date: 2012-06-21 14:44:45 -0700 (Thu, 21 Jun 2012) $
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
import gov.llnl.babel.backend.LevelComparator;
import gov.llnl.babel.backend.Utilities;
import gov.llnl.babel.backend.rmi.RMIStubHeader;
import gov.llnl.babel.backend.ucxx.Cxx;
import gov.llnl.babel.backend.writers.LanguageWriterForCxx;
import gov.llnl.babel.symbols.Class;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Interface;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 */
public class CxxStubHeader {
  private Extendable d_ext = null;
  private LanguageWriterForCxx d_writer = null;
  private String d_self = null;
  private Context d_context;

  /**
   * Create an object capable of generating the header file for a
   * BABEL extendable.
   *
   * @param ext   an interface or class symbol that needs a header
   *              file for a Cxx C extension class.
   */
  public CxxStubHeader(Extendable ext,
                       Context context) {
    d_ext = ext;
    d_context = context;
  }
  
    /**
   * Special constructor for generating super methods for IMPL files.
   * Since these can only exist for classes, d_self is just "self"
   *
   * @param ext   an interface or class symbol that needs a header
   *              file for a Cxx C extension class.
   */
  public CxxStubHeader(Extendable ext, LanguageWriterForCxx writer,
                       Context context) {
    d_ext = ext;
    d_writer = writer;
    d_self = "self";
    d_context = context;
  }

  /**
   * This is a convenience utility function specifically for the generation
   * of super "Stub" functions in the Impl files. 
   * The output stream is not closed on exit.  A code
   * generation exception is thrown if an error is detected.
   *
   * @param cls The class in which these supers are to be generated  
   * 
   * @param writer the output writer to which the stub source will
   *               be written. This will not be closed.
   *    
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception. It can be caused by I/O trouble or
   *    violations of the data type invariants.
   */
  public static void generateSupers(Class             cls,
                                  LanguageWriterForCxx writer,
                                    Context context)
    throws CodeGenerationException
  {
    CxxStubHeader source = new CxxStubHeader(cls, writer, context);
    source.generateSupers();
  }  
  
  /**
   * This special function is only to be used when generating super
   * functions for the Super class in an Impl.  It may only be called though
   * the static method above, "generateSupers"
   *
   */
  private void generateSupers() 
    throws CodeGenerationException {
    Class cls = (Class) d_ext;
    SymbolID clsID = cls.getSymbolID();
    d_writer.println("public:");
    d_writer.writeComment("Hold pointer to IOR functions.", false);

    d_writer.println("class Super {");
    d_writer.tab();
    d_writer.println();
    d_writer.println("private:");
    d_writer.tab();
    d_writer.writeComment("Hold pointer to Super EPV", false);
    d_writer.println(/*"const " + */IOR.getEPVName(cls.getParentClass().getSymbolID()) + 
                     "* superEPV;");
    d_writer.println(/*"const " + */IOR.getObjectName(clsID) + "* superSelf;");
    d_writer.println();
    
    throwExceptionMethods(true);

    d_writer.backTab();
    d_writer.println("public:"); 
    d_writer.tab();
    d_writer.println("Super() : superEPV(NULL), superSelf(NULL) {}");

    d_writer.println();
    d_writer.println("Super("+IOR.getObjectName(clsID)+"* loc_self, const "+IOR.getExternalName(cls.getSymbolID())+"* loc_ext) {");
    d_writer.tab();
    d_writer.println("superEPV = loc_ext->getSuperEPV();");
    d_writer.println("superSelf = loc_self;");
    d_writer.backTab();
    d_writer.println("}");
    Collection methods       = cls.getOverwrittenClassMethods();
    /* TBD:  Is it appropriate to include invariants exception here? */
    boolean    hasInvariants = cls.hasInvClause(true);
    for(Iterator mit = methods.iterator(); mit.hasNext();){
      Method method = (Method)mit.next();
      //generateInlineMethodDispatch(method, null, true, true, hasInvariants);
      if(!Cxx.inlineStub(method)) {
        Cxx.generateMethodSignature( d_writer, d_context, method, 
                                     "user defined static method", 
                                     Cxx.FILE_ROLE_STUB, true, hasInvariants);
        d_writer.println();
        if(method.hasRarray()) {
          Cxx.generateMethodSignature( d_writer, d_context, method, 
                                       "user defined static method", 
                                       Cxx.FILE_ROLE_STUB, false, 
                                       hasInvariants);
          d_writer.println();
        }
      }
    }
    d_writer.backTab();
    d_writer.backTab();
    d_writer.println("};");

  }

  private void includeSet(Set symbols)
  {
    if (!symbols.isEmpty()) {
      List entries = Utilities.sort(symbols);
      for(Iterator i = entries.iterator(); i.hasNext(); ) {
        d_writer.
          generateInclude(Cxx.generateFilename((SymbolID)i.next(),
                                               Cxx.FILE_ROLE_STUB,
                                               Cxx.FILE_TYPE_CXX_HEADER,
                                               d_context),
                          true);
      }
    }
  }

  public void generateFrontIncludes()
    throws CodeGenerationException
  {
    Set symbols = Cxx.getFrontIncludes(d_ext, d_context);
    d_writer.generateInclude("sidl_cxx.hxx", true);
    d_writer.generateSystemInclude("stdint.h");
    d_writer.generateInclude(IOR.getHeaderFile(d_ext.getSymbolID()), true);
    includeSet(symbols);
  }

  public void generateEndIncludes()
    throws CodeGenerationException
  {
    Set symbols = Cxx.generateIncludeSet(d_ext, d_context);
    symbols.removeAll(Cxx.getFrontIncludes(d_ext, d_context));
    includeSet(symbols);
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
    String filename = Cxx.generateFilename( d_ext.getSymbolID(), 
                                            Cxx.FILE_ROLE_STUB, 
                                            Cxx.FILE_TYPE_CXX_HEADER ,
                                            d_context);
    
    if ( d_ext.isInterface() ) { 
      d_self = "loc_self->d_object";
    } else { 
      d_self = "loc_self";
    }

    try { 
      d_writer = Cxx.createHeader( d_ext, Cxx.FILE_ROLE_STUB, "STUBHDRS",
                                   d_context );
      d_writer.println();
      d_writer.openHeaderGuard( filename );

      writeDefineNullIORException();

      writeClassDeclaration();

      generateFrontIncludes();

      Cxx.openUCxxNamespace(d_writer);
      if (!d_context.getConfig().getSkipRMI()) {
        d_writer.println("namespace sidl {");
        d_writer.tab();
        d_writer.println("namespace rmi {");
        d_writer.tab();
        d_writer.println("class Call;");
        d_writer.println("class Return;");
        d_writer.println("class Ticket;");
        d_writer.backTab();
        d_writer.println("}");
        d_writer.println("namespace rmi {");
        d_writer.tab();
        d_writer.println("class InstanceHandle;");
        d_writer.backTab();
        d_writer.println("}");
        d_writer.backTab();
        d_writer.println("}");
      }
      Cxx.closeUCxxNamespace(d_writer);

      if(d_context.getConfig().getFastCall()) {
        /* declare a global anonymous class type */
        d_writer.println("class sidl_generic_class_t;");

        /* for native calls, we can cache the entry point for client stubs at
         * object creation time or at the first invocation. The user might
         * undef the following to achieve the latter behavior.
         */
        d_writer.writeCommentLine("this enables eager caching for " +
                                  "method entry points per default");
        d_writer.printlnUnformatted("#if !defined SIDL_FASTCALL_LAZY_CACHING &&" +
                                    " !defined SIDL_FASTCALL_DISABLE_CACHING &&" +
                                    " !defined SIDL_FASTCALL_EAGER_CACHING");
        d_writer.printlnUnformatted("#define SIDL_FASTCALL_EAGER_CACHING");
        d_writer.printlnUnformatted("#endif");
        d_writer.println();
      }

      Cxx.nestPackagesInNamespaces( d_writer, d_ext );
      
      writeClassBegin();

      throwExceptionMethods(false);

      writeTypedefs();

      if(d_ext.isInterface()) {
        writeLocalIOR();
      }
      
      writeUserDefinedMethods();
      
      writeConstructors();

      writeCastingOperators();

      writeBindingSpecificMethods();

      writeClassEnd(); 
      
      Cxx.unnestPackagesInNamespaces( d_writer, d_ext );
      Cxx.beginExternCRegion(d_writer); 
      if (!d_context.getConfig().getSkipRMI()) {
        RMIStubHeader.generateCode(d_ext, d_writer);
      }
      Cxx.endExternCRegion(d_writer); 

      writeArrayDefinition();

      generateEndIncludes();

      d_writer.closeHeaderGuard();
    } catch ( Exception ex ) { 
      if(ex != null) {
        System.out.println(ex.getMessage());
        ex.printStackTrace();
        throw new CodeGenerationException("Exception : " + ex.getMessage() );
      } else {
        System.out.println("ex is NULL?!?");
      }
    } finally { 
      if (d_writer != null) {
        d_writer.close();
        d_writer = null;
      }
    }
  }

  /**
   * This method write out a #define if Null IOR Exceptions are defined in
   * this file.
   */
  private void writeDefineNullIORException() { 
    String flagname = d_ext.getSymbolID().getFullName().replace('.', '_').toUpperCase() + 
      "_NULL_IOR_EXCEPTION";

    if(d_context.getConfig().makeCxxCheckNullIOR()) {
      d_writer.printlnUnformatted("#ifndef " + flagname);
      d_writer.printlnUnformatted("#define " + flagname + " 1");
      d_writer.printlnUnformatted("#endif ");
    }
  }

  private void writeClassDeclaration() throws CodeGenerationException { 
    SymbolID id = d_ext.getSymbolID();
    String name = id.getShortName();
    
    d_writer.generateInclude("sidl_cxx.hxx", true );

    d_writer.writeCommentLine("declare class before main #includes");
    d_writer.writeCommentLine("(this alleviates circular #include guard problems)[BUG#393]");
    Cxx.nestPackagesInNamespaces( d_writer, d_ext );
    d_writer.println("class " + name + ";");
    Cxx.unnestPackagesInNamespaces( d_writer, d_ext );

    

    d_writer.writeCommentLine("Some compilers need to define array template before the specializations");
    Cxx.openUCxxNamespace(d_writer);
    d_writer.println("namespace sidl {");
    d_writer.tab();
    d_writer.println("template<>");
    d_writer.println("class array< " +  Cxx.getObjectName(id) + 
                     " >;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.backTab();
    Cxx.closeUCxxNamespace(d_writer);
    
    //Forward declarations for method dependencies

    Set decls = new HashSet();
    for( Iterator i = Cxx.getStubMethodList(d_ext).iterator(); i.hasNext(); ) {
      Method method = (Method) i.next();
      decls.addAll(method.getSymbolReferences()); 
    }
    if (!decls.isEmpty()){
      d_writer.writeComment("Forward declarations for method dependencies.",false);
      
      List entries = Utilities.sort(decls);
      
      for (Iterator i = entries.iterator(); i.hasNext(); ) { 
        SymbolID s = (SymbolID) i.next();
        Symbol symbol = (Symbol) Utilities.lookupSymbol(d_context, s);
        //Enumerated types are included, and hence shouldn't be forward
        //declared.
        switch(symbol.getSymbolType()) {
        case Type.CLASS:
        case Type.INTERFACE:
          Cxx.nestPackagesInNamespaces( d_writer, s );
          d_writer.println("class " + s.getShortName() +";");
          Cxx.unnestPackagesInNamespaces( d_writer, s );
          break;
        case Type.STRUCT:
          Cxx.nestPackagesInNamespaces( d_writer, s );
          d_writer.println("struct " + s.getShortName() +";");
          Cxx.unnestPackagesInNamespaces( d_writer, s );
          break;
        }
      }
    }
    
  }

    private void writeClassBegin() { 
    SymbolID id = d_ext.getSymbolID();
    String name = id.getShortName();
    
    d_writer.writeComment(d_ext, true);    
    d_writer.print("class " + name);
    java.util.Collection parents = d_ext.getParents(false);
    boolean first = true;

    if(parents.size() == 0) {
      d_writer.print(": public virtual " +
                     Cxx.prependGlobalUCxx() + "::sidl::StubBase");
 
    } else {

      Object [] parentArray = Utilities.sort(parents).toArray();
      //Arrays.sort(parentArray);
 
      for(int i = 0; i < parentArray.length; ++i) {
        Extendable parent = (Extendable) parentArray[i];
        String parentName = Cxx.getSymbolName(parent.getSymbolID(), "");
        if(first){
          d_writer.print(": ");
          first = false;
        }
        else
          d_writer.print(", ");
        d_writer.print("public virtual " + parentName);
      }
    }
    d_writer.println(" {");
    d_writer.tab();
    
  } 

  /**Declares A set of methods for handling exceptions.  This takes the place
   * of exception handing code in the stubs.  (To reduce code size) */
  private void throwExceptionMethods(boolean isSuper) throws CodeGenerationException {
    Map sets_to_nums = null;
    if(isSuper) {
      Class cls = (Class) d_ext;
      sets_to_nums = Cxx.getExceptionSets(cls.getOverwrittenClassMethods());
    } else {
      sets_to_nums = Cxx.getExceptionSets(d_ext.getMethods(true));        
    }
    Set exp_sets = sets_to_nums.keySet();
    d_writer.beginBoldComment();
    d_writer.println("Special methods for throwing exceptions");
    d_writer.endBoldComment();
    
    d_writer.backTab();
    d_writer.println("private:");
    d_writer.tab();
    
    //For each set of exceptions
    for(Iterator i = exp_sets.iterator(); i.hasNext();) {
      Set exp_set = (Set) i.next();
      Integer num = (Integer)sets_to_nums.get(exp_set);
      
      d_writer.println("static ");
      
      Cxx.generateExceptionSetSignature(d_writer, d_ext.getSymbolID(), exp_set, num.intValue(), false, isSuper);
      d_writer.println(";");
      
    }
  }

  /**
   * Declares a local IOR pointer on this Stub.  This is used for caching
   * the IOR pointer.
   *
   * This method is for interfaces ONLY.  We don't need to cache class stubs
   * because they are always the same.
   */
  private void writeLocalIOR() throws CodeGenerationException {
  
    /*************************************************************
     * Only interfaces have cache variables, and not BaseInterface
     *************************************************************/
    if(d_ext.isInterface() && 
       !BabelConfiguration.getBaseInterface().equals(d_ext.getSymbolID().getFullName())) {

      d_writer.beginBoldComment();
      d_writer.println("Locally Cached IOR pointer");
      d_writer.endBoldComment();
    
      d_writer.backTab();
      d_writer.println("protected:");
      d_writer.tab();
      
      d_writer.println("mutable ior_t* " + 
                       Cxx.getIORCacheVariable((Interface)d_ext)+ ";");
    }
  }


  private void writeUserDefinedMethods() throws CodeGenerationException {

    Iterator m = null;

    if(d_context.getConfig().getFastCall()) {
      /* These fields are used to cache the entry points for fast native 
       * function calls. We could use arrays here. However, this would require
       * an index calculation at runtime that we want to avoid. 
       */
      d_writer.beginBoldComment();
      d_writer.println("private fields used to cache method entry points for");
      d_writer.println("fast native function calls.");
      d_writer.endBoldComment();
      d_writer.backTab();
      d_writer.println("private:");
      d_writer.tab();
      m = Cxx.getStubMethodList(d_ext).iterator();
      while (m.hasNext()) {
        final Method method = (Method) m.next();
        if(Cxx.supportsFastCalls(method)) {
          Cxx.generateFastCallCache(d_writer, d_context, d_ext, method);
          d_writer.println();
        }
      }
    }
    
    d_writer.beginBoldComment();
    d_writer.println("User Defined Methods");
    d_writer.endBoldComment();

    d_writer.backTab();
    d_writer.println("public:");
    d_writer.tab();
    
    m = Cxx.getStubMethodList(d_ext).iterator();
    boolean hasInvariants = d_ext.hasInvClause(true);
    while (m.hasNext()) {
      final Method method = (Method) m.next();
      final boolean isStatic = method.isStatic();
      final String desc = isStatic ? "user defined static method"
        : "user defined non-static method";
      if(!Cxx.inlineStub(method)) {
        String suffix = "";
        if(d_context.getConfig().getFastCall() && Cxx.supportsFastCalls(method))
          suffix = "_default_stub";
        Cxx.generateMethodSignature( d_writer, d_context, method, desc,
                                     Cxx.FILE_ROLE_STUB, true, hasInvariants,
                                     suffix);
        
        if ( (!isStatic) &&
            (method.getCommunicationModifier() == Method.NONBLOCKING)  &&
            !d_context.getConfig().getSkipRMI()) {
          Method send = method.spawnNonblockingSend();
          Cxx.generateMethodSignature( d_writer, d_context, send, desc,
                                       Cxx.FILE_ROLE_STUB, true, hasInvariants);
          d_writer.println();
          Method recv = method.spawnNonblockingRecv();
          Cxx.generateMethodSignature( d_writer, d_context, recv, desc,
                                       Cxx.FILE_ROLE_STUB, true, hasInvariants);
          d_writer.println();
        }

        if(method.hasRarray()) {
          Cxx.generateMethodSignature( d_writer, d_context, method, desc,
                                       Cxx.FILE_ROLE_STUB, false, 
                                       hasInvariants, suffix);
          d_writer.println();
          if ((!isStatic) &&
              (method.getCommunicationModifier() == Method.NONBLOCKING) &&
              !d_context.getConfig().getSkipRMI()) {
            Method send = method.spawnNonblockingSend();
            Cxx.generateMethodSignature( d_writer, d_context, send, desc,
                                         Cxx.FILE_ROLE_STUB, false, 
                                         hasInvariants);
            d_writer.println();
            //Method recv = method.spawnNonblockingRecv();
            //Cxx.generateMethodSignature( d_writer, d_context, recv, desc,
            //                            Cxx.FILE_ROLE_STUB, false, 
            //                             hasInvariants);
            //d_writer.println();
          }
        }

        //in fast-call mode, emit a dispatch function that replaces the standard stub
        //allowing for faster native function calls
        if(d_context.getConfig().getFastCall()) {
          Cxx.generateFastCallDispatch(d_writer, d_context, d_ext, method);
        }
        
      } else {
        generateInlineMethodDispatch(method, desc, false, true, hasInvariants);
        d_writer.println();
      }
    }
    
    d_writer.beginBoldComment();
    d_writer.println("End User Defined Methods");
    d_writer.println("(everything else in this file is specific to");
    d_writer.println(" Babel's C++ bindings)");
    d_writer.endBoldComment();
  }

  private void writeTypedefs() { 
    SymbolID id = d_ext.getSymbolID();

    d_writer.backTab();
    d_writer.println("public:");
    d_writer.tab();
    d_writer.println("typedef " + IOR.getObjectName( id ) + " ior_t;");
    d_writer.println("typedef " + IOR.getExternalName( id ) + " ext_t;");
    d_writer.println("typedef " + IOR.getSEPVName( id ) + " sepv_t;");
    d_writer.println();
  }

  private void writeConstructors() { 
    SymbolID id = d_ext.getSymbolID();
    String name = id.getShortName();

    d_writer.writeCommentLine("default constructor");
    d_writer.print( name + "() ");

    boolean have_colon = false;

    if(d_ext.isInterface() && 
       !BabelConfiguration.getBaseInterface().equals(d_ext.getSymbolID().getFullName())) {
      
      d_writer.println(" : ");
      have_colon = true;
      Cxx.initializeLocalIOR(d_writer,(Interface)d_ext,"NULL",true);
    }    
    
    List initializers = Cxx.getFastCallStubInitializers(d_context, d_ext);
    if(initializers.size() > 0) {
      d_writer.println();
      d_writer.printlnUnformatted("#ifndef SIDL_FASTCALL_DISABLE_CACHING");
      if(!have_colon)
        d_writer.println(" : ");
      else
        d_writer.println(", ");
      for(Iterator i = initializers.iterator(); i.hasNext(); ) {
        String s = (String) i.next();
        d_writer.println(s + (i.hasNext() ? ", " : ""));
      }
      d_writer.printlnUnformatted("#endif");
    }
    
    d_writer.println("{ }");
    
    if( ! d_ext.isAbstract() ) {
      d_writer.writeCommentLine("static constructor");
      d_writer.println("static " + Cxx.getObjectName(id) + " _create();");
      d_writer.println();
    }

    d_writer.println();
    if (!d_context.getConfig().getSkipRMI()) {
      d_writer.printlnUnformatted("#ifdef WITH_RMI");
      d_writer.println();

      if( ! d_ext.isAbstract() ) {
        d_writer.writeCommentLine("RMI constructor");
        d_writer.println("static " + Cxx.getObjectName(id) + 
                         " _create( /*in*/ const std::string& url );");
        d_writer.println();
      }
    
      d_writer.writeCommentLine("RMI connect");
      d_writer.println("static inline " + Cxx.getObjectName(id) + 
                       " _connect( /*in*/ const std::string& url ) { ");
      d_writer.tab();

      d_writer.println("return _connect(url, true);");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();
    
      d_writer.writeCommentLine("RMI connect 2");
      d_writer.println("static " + Cxx.getObjectName(id) + 
                       " _connect( /*in*/ const std::string& url, /*in*/ const bool ar  );");
      d_writer.println();

      d_writer.println();
      d_writer.printlnUnformatted("#endif /*WITH_RMI*/");
    }
    d_writer.println();
    

    d_writer.writeCommentLine("default destructor");
    d_writer.println("virtual ~" + name + " () { }");
    d_writer.println();
    
    d_writer.writeCommentLine("copy constructor");
    d_writer.println( name + " ( const " + name + "& original );");
    d_writer.println();

    d_writer.writeCommentLine("assignment operator");
    d_writer.println( name + "& operator= ( const " + name + "& rhs );");
    d_writer.println();

    if( ! d_ext.isAbstract() && !IOR.isSIDLSymbol(id)) {
      d_writer.println();
      d_writer.println("protected:");
      d_writer.writeCommentLine("Internal data wrapping method");
      d_writer.println("static ior_t*  _wrapObj(void* private_data);");
      d_writer.println();
      d_writer.println();
      d_writer.println("public:");
    }

  }
  
  private void writeCastingOperators() { 
    SymbolID id = d_ext.getSymbolID();
    String name = id.getShortName();

    d_writer.writeCommentLine("conversion from ior to C++ class");
    d_writer.println( name + " ( " + name + "::ior_t* ior );");
    d_writer.println();

    d_writer.writeCommentLine("Alternate constructor: does not call addRef()");
    d_writer.writeCommentLine("(sets d_weak_reference=isWeak)");
    d_writer.writeCommentLine("For internal use by Impls (fixes bug#275)");
    d_writer.println( name + " ( " + name + "::ior_t* ior, bool isWeak );");
    d_writer.println();

  }

  private void writeBindingSpecificMethods()  throws CodeGenerationException { 
    SymbolID id = d_ext.getSymbolID();
    String ior_ptr = "ior_t*";

    d_writer.println("inline " + ior_ptr + " _get_ior() const throw() {");
    d_writer.tab();

    /*************************************************************
     * Only interfaces have cache variables, and not BaseInterface
     *************************************************************/
    if(d_ext.isInterface() &&
       !BabelConfiguration.getBaseInterface().equals(d_ext.getSymbolID().getFullName())) {
      Interface ifc = (Interface) d_ext;
      d_writer.println("if(!"+Cxx.getIORCacheVariable(ifc)+") { ");
      d_writer.tab();
      d_writer.println(Cxx.getIORCacheVariable(ifc)+
                       " = " +  Cxx.getSymbolName(ifc.getSymbolID(),"") + 
                       "::_cast((void*)d_self);");
      d_writer.println("if ("+Cxx.getIORCacheVariable(ifc)+") {");
      d_writer.tab();
      d_writer.println(IOR.getExceptionFundamentalType() + "throwaway_exception;");
      d_writer.println("("+Cxx.getIORCacheVariable(ifc)+ "->d_epv->" + 
                       IOR.getVectorEntry("deleteRef") + 
                       ")("+Cxx.getIORCacheVariable(ifc)+
                       "->d_object, &throwaway_exception);  ");
      d_writer.backTab();
      d_writer.println("}  ");
      
      d_writer.backTab();
      d_writer.println("}"); 
      d_writer.println("return " + Cxx.getIORCacheVariable(ifc)+";");
    } else {
      d_writer.println("return " + Cxx.reinterpretCast(ior_ptr, "d_self") + ";");
    }

    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();

    d_writer.println( "inline void _set_ior( " + ior_ptr + " ptr ) throw () { ");
    d_writer.tab();
    d_writer.println("d_self = " +
                     Cxx.reinterpretCast("void*", "ptr") + ";");
    if(d_ext.isInterface()) {
      Cxx.initializeLocalIOR(d_writer, (Interface)d_ext, "ptr", false);
    }
    d_writer.println();
    
    Cxx.writeInterfaceCacheInitialization(d_writer, d_ext, "ptr");

    if(d_context.getConfig().getFastCall()) {
      d_writer.printlnUnformatted("#ifdef SIDL_FASTCALL_EAGER_CACHING");
      d_writer.println("_cache_entry_points();");
      d_writer.printlnUnformatted("#endif");
    }
    
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();

    d_writer.println("virtual int _set_ior_typesafe( struct sidl_BaseInterface__object *obj,");
    d_writer.println("                               const ::std::type_info &argtype );");
    d_writer.println();
    
    d_writer.println("bool _is_nil() const throw () { return (d_self==0); }");
    d_writer.println();

    d_writer.println("bool _not_nil() const throw () { return (d_self!=0); }");
    d_writer.println();

    d_writer.println("bool operator !() const throw () { return (d_self==0); }");
    d_writer.println();
    
    d_writer.println("static inline const char * type_name() throw () { return \"" + 
                     id.getFullName()+"\";}");
    d_writer.println();

    d_writer.println("static "+IOR.getObjectName(id)+"* _cast(const void* src);");
    d_writer.println();

    if (!d_context.getConfig().getSkipRMI()) {
      d_writer.writeCommentLine("execute member function by name");
      d_writer.println("void _exec(const std::string& methodName,");
      d_writer.println("           ::sidl::rmi::Call& inArgs,");
      d_writer.println("           ::sidl::rmi::Return& outArgs);");
  
      if (d_ext.hasStaticMethod(true) ) { 
	d_writer.writeCommentLine("exec static member function by name");
	d_writer.println("static void _sexec(const std::string& methodName,");
	d_writer.println("                   ::sidl::rmi::Call& inArgs,");
	d_writer.println("                   ::sidl::rmi::Return& outArgs);");
	d_writer.println();
      }

      Cxx.generateMethodSignature( d_writer, d_context, 
                                   IOR.getBuiltinMethod(IOR.GETURL,id,d_context, false), 
                                   "get URL of the implementation of this object", 
                                   Cxx.FILE_ROLE_STUB, true, false);
      d_writer.println();

    }

    if(d_context.getConfig().getFastCall()) {
      /*
       * For native function calls, cache the method entry points and the
       * proper "this" pointer.
       */
      Cxx.generateFastCallCacheEntryPoints(d_writer, d_context, d_ext);
    }
    
    boolean hasStatic = d_ext.hasStaticMethod(true);

    /* Generate hooks built-ins. */
    /* Instance version of enable/disable. */
    Cxx.generateMethodSignature( d_writer, d_context,
        IOR.getBuiltinMethod(IOR.HOOKS, id, d_context, false), 
        "Method to enable/disable method hooks execution.",
        Cxx.FILE_ROLE_STUB, true, false);
    if (hasStatic) {
      /* Static version of enable/disable. */
      Cxx.generateMethodSignature( d_writer, d_context,
          IOR.getBuiltinMethod(IOR.HOOKS, id, d_context, true), 
          "Method to enable/disable static method hooks execution.",
          Cxx.FILE_ROLE_STUB, true, false );
    }

    /* Generate contract built-ins. */
    if (IOR.generateContractBuiltins(d_ext, d_context)) {
      /* Instance version of enable/disable. */
      Cxx.generateMethodSignature( d_writer, d_context,
          IOR.getBuiltinMethod(IOR.CONTRACTS, id, d_context, false), 
          "Method to enable/disable interface contract enforcement.",
          Cxx.FILE_ROLE_STUB, true, false);
      if (hasStatic) {
        /* Static version of enable/disable. */
        Cxx.generateMethodSignature( d_writer, d_context,
            IOR.getBuiltinMethod(IOR.CONTRACTS, id, d_context, true), 
            "Method to enable/disable static interface contract enforcement.",
            Cxx.FILE_ROLE_STUB, true, false );
      }

      /* Instance version of stats dump. */
      Cxx.generateMethodSignature( d_writer, d_context,
          IOR.getBuiltinMethod(IOR.DUMP_STATS, id, d_context, false), 
          "Method to dump interface contract enforcement statistics.",
          Cxx.FILE_ROLE_STUB, true, false);
      if (hasStatic) {
        /* Static version of stats dump. */
        Cxx.generateMethodSignature( d_writer, d_context,
            IOR.getBuiltinMethod(IOR.DUMP_STATS, id, d_context, true), 
            "Method to dump static interface contract enforcement statistics.",
            Cxx.FILE_ROLE_STUB, true, false );
      }
    }
    
    if (!d_context.getConfig().getSkipRMI()) {
      //d_writer.writeCommentLine("get URL of the implementation of this object");
      //d_writer.println("std::string _getURL();");
      //d_writer.println();

      d_writer.writeCommentLine("return true iff object is remote");
      d_writer.println("bool _isRemote() const { ");
      d_writer.tab();
      d_writer.println("ior_t* self = const_cast<ior_t*>(_get_ior() );");
      d_writer.print(IOR.getExceptionFundamentalType());
      d_writer.println("throwaway_exception;");
      d_writer.print("return (*self->d_epv->f__isRemote)");
      d_writer.println("(self, &throwaway_exception) == TRUE;");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();
      
      d_writer.writeCommentLine("return true iff object is local");
      d_writer.println("bool _isLocal() const {");
      d_writer.tab();
      d_writer.println("return !_isRemote();");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();
    }

    d_writer.backTab();
    d_writer.println("protected:");
    d_writer.tab();
  
    /*
    d_writer.writeCommentLine("perform addRef on remote object");
    d_writer.println("void _raddRef();");
    d_writer.println();
    */
    d_writer.writeCommentLine("Pointer to external (DLL loadable) symbols (shared among instances)");
    d_writer.println("static const ext_t * s_ext;");
    d_writer.println();

    d_writer.writeCommentLine("Global cache for _get_sepv()");
    d_writer.println("static sepv_t *_sepv;");
    d_writer.println();
    
    d_writer.backTab();
    d_writer.println("public:");
    d_writer.tab();
    d_writer.println("static const ext_t * _get_ext() throw ( "+Cxx.prependGlobalUCxx()+"::sidl::NullIORException );");
    d_writer.println();
    if ( d_ext.hasStaticMethod(true) ) { // if has static methods
      d_writer.println("static const sepv_t * _get_sepv() {");
      d_writer.tab();
      d_writer.println("if(!_sepv) _sepv = (*(_get_ext()->getStaticEPV))();");
      d_writer.println("return _sepv;");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();
    }
  }
  private void writeClassEnd() { 
    String name = d_ext.getSymbolID().getShortName();
    d_writer.backTab();
    d_writer.println("}; // end class " + name);
  } 

  private void writeArrayDefinition() { 
    SymbolID id = d_ext.getSymbolID();
    String cxx_item_t = Cxx.getObjectName(id);
    String cxx_array_t = "array< " + cxx_item_t + " >";
    String ior_item_t = IOR.getObjectName(id);
    String ior_array_t = IOR.getArrayName(id);
    String array_traits = "array_traits< " +cxx_item_t + " >";

    Cxx.openUCxxNamespace(d_writer);
    d_writer.println("namespace sidl {");
    d_writer.tab();
    
    d_writer.writeCommentLine("traits specialization");
    d_writer.println("template<>");
    d_writer.println("struct " + array_traits + " {");
    d_writer.tab();
    d_writer.println("typedef " + cxx_array_t + " cxx_array_t;");
    d_writer.println("typedef " + cxx_item_t + " cxx_item_t;");
    d_writer.println("typedef " + ior_array_t + " ior_array_t;");
    d_writer.println("typedef sidl_interface__array ior_array_internal_t;");
    d_writer.println("typedef " +  ior_item_t + " ior_item_t;");
    d_writer.println("typedef cxx_item_t value_type;");
    d_writer.println("typedef value_type reference;");
    d_writer.println("typedef value_type* pointer;");
    d_writer.println("typedef const value_type const_reference;");
    d_writer.println("typedef const value_type* const_pointer;");
    d_writer.println("typedef array_iter< " + array_traits + " > iterator;");
    d_writer.println("typedef const_array_iter< " + array_traits+ " > const_iterator;");
    d_writer.backTab();
    d_writer.println("};");
    d_writer.println();

    d_writer.writeCommentLine("array specialization");
    d_writer.println("template<>");
    d_writer.println("class " + cxx_array_t +
                     ": public interface_array< " + array_traits + " > {");
    d_writer.println("public:");
    d_writer.tab();
    
    d_writer.println("typedef interface_array< " + array_traits + " > Base;");
    d_writer.println("typedef " + array_traits + "::cxx_array_t          cxx_array_t;");
    d_writer.println("typedef " + array_traits + "::cxx_item_t           cxx_item_t;");
    d_writer.println("typedef " + array_traits + "::ior_array_t          ior_array_t;");
    d_writer.println("typedef " + array_traits + "::ior_array_internal_t ior_array_internal_t;");
    d_writer.println("typedef " + array_traits + "::ior_item_t           ior_item_t;");
    d_writer.println();
    d_writer.beginBlockComment(true);
    d_writer.println("conversion from ior to C++ class");
    d_writer.println("(constructor/casting operator)");
    d_writer.endBlockComment(true);
    d_writer.println("array( " + ior_array_t + 
                     "* src = 0) : Base(src) {}");
    d_writer.println();
 

    d_writer.beginBlockComment(true);
    d_writer.println("copy constructor");
    d_writer.endBlockComment(true);
    d_writer.println("array( const " + cxx_array_t + "&src) : Base(src) {}");
    d_writer.println();

    if (BabelConfiguration.getBaseInterface().equals(id.getFullName())) {
      d_writer.beginBlockComment(true);
      d_writer.println("Assignment to promote a generic array to an");
      d_writer.println("array of sidl.BaseInterface references. This");
      d_writer.println("will produce a nil array if the generic array");
      d_writer.println("isn't an array of objects/interfaces.");
      d_writer.endBlockComment(true);
      d_writer.println(cxx_array_t + "&");
      d_writer.println("operator =(const basearray &rhs) throw() {");
      d_writer.tab();
      d_writer.println("if (this->d_array != rhs._get_baseior()) {");
      d_writer.tab();
      d_writer.println("deleteRef();");
      d_writer.println("this->d_array =");
      d_writer.tab();
      d_writer.println("(rhs._get_baseior() &&");
      d_writer.println(" (sidl_interface_array == rhs.arrayType()))");
      d_writer.println("? const_cast<sidl__array *>(rhs._get_baseior())");
      d_writer.println(": NULL;");
      d_writer.backTab();
      d_writer.println("addRef();");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println("return *this;");
      d_writer.backTab();
      d_writer.println("}");
    }
 
    d_writer.beginBlockComment(true);
    d_writer.println("assignment");
    d_writer.endBlockComment(true);
    d_writer.println(cxx_array_t + "&");
    d_writer.println("operator =( const " + cxx_array_t + "&rhs ) { ");

    d_writer.tab();
    d_writer.println("if (d_array != rhs._get_baseior()) {");
    d_writer.tab();
    d_writer.println("if (d_array) deleteRef();");
    d_writer.println("d_array = const_cast<sidl__array *>(rhs._get_baseior());");
    d_writer.println("if (d_array) addRef();");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println("return *this;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
 
    d_writer.backTab();
    d_writer.println("};");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
    Cxx.closeUCxxNamespace(d_writer);
  }

  private void generateInlineMethodDispatch( Method m, String altcomment, 
                                             boolean isSuper, boolean rarrays,
                                             boolean hasInvariants ) 
    throws CodeGenerationException { 
    if ( m == null ) { return; }
    String shortMethodName = m.getShortMethodName();
    String className = d_ext.getSymbolID().getShortName();
    Type return_type = m.getReturnType();

    if ( shortMethodName.equals(className) ) { 
      System.out.println("WARNING: gov.llnl.babel.backend.UCxx.CxxStubSource: sidl / C++ conflict!");
      System.out.println("         methodName == className is not allowed in C++");
      System.out.println("         (this is restricted to constructors in C++)");
      System.out.println("         changing to " + className + "::f_" + shortMethodName + "()");
      shortMethodName = "f_" + shortMethodName;
    }

    //Output function deleration
    Cxx.generateInlineMethodSignature( d_writer, d_context, m, 
                                     "user defined static method", 
                                       Cxx.FILE_ROLE_STUB, isSuper, rarrays,
                                       hasInvariants);
    d_writer.tab();

    if ( (! m.isStatic()) &&  d_context.getConfig().makeCxxCheckNullIOR()) { 

      d_writer.println("if ( d_self == 0 ) {");
      d_writer.tab();
      d_writer.println("throw "+Cxx.prependGlobalUCxx()+"::sidl::NullIORException( ::std::string (");
      d_writer.tab();
      try {
        d_writer.pushLineBreak(false);
        d_writer.println( "\"" + 
                          "Null IOR Pointer in \\\"" + 
                          Cxx.getMethodStubName(d_ext.getSymbolID(),shortMethodName, isSuper) +
                          "()\\\"\"");
      }
      finally {
        d_writer.popLineBreak();
      }
      d_writer.backTab();
      d_writer.println("));");
      d_writer.backTab();
      d_writer.println("}");
    }

    //Output almost nothing!
    d_writer.println(Cxx.generateInitialization(m, d_ext, d_self, d_context));

    if ( ! m.isStatic() ) { 
      if (shortMethodName.equals("addRef") ||
          shortMethodName.equals("deleteRef") ) { 
        d_writer.println("if ( !d_weak_reference ) {");
        d_writer.tab();
      }
    }

    //Prepare for IOR call!
    d_writer.println(Cxx.generatePreIORCall(m, d_ext, d_self, isSuper, rarrays, d_context));

    if(isSuper) {
      Class cls = (Class) d_ext;
      String tmp_self = Cxx.reinterpretCast(IOR.getObjectName(cls.getParentClass().getSymbolID())
                                            + "*", "superSelf");
      d_writer.println(Cxx.generateIORCall(m, d_ext, d_context, tmp_self, isSuper, rarrays));
    } else {
      d_writer.println(Cxx.generateIORCall(m, d_ext, d_context, d_self, isSuper, rarrays));
    }

    d_writer.println(Cxx.generatePostIORCleanup(m, rarrays));
    if (m.getThrows().size()>0) { // if throws exception
      d_writer.println("if (_exception != 0 ) {");      
      d_writer.tab();
      d_writer.println("void * _p = 0;");
      d_writer.println(IOR.getExceptionFundamentalType() + "throwaway_exception;");
      Object [] exceptions = m.getThrows().toArray();
      Arrays.sort(exceptions, new LevelComparator(d_context.getSymbolTable()));
      
      for( int i=0; i<exceptions.length; ++i) {
        SymbolID exid = (SymbolID)exceptions[i];
        d_writer.println("if ( (_p=(*(_exception->d_epv->f__cast))(_exception->d_object, \"" +
                         exid.getFullName() + "\", &throwaway_exception)) != 0 ) {");
        d_writer.tab();
        d_writer.println(IOR.getObjectName(exid) + " * _realtype = " + 
                         Cxx.reinterpretCast(IOR.getObjectName(exid)+"*" ,
                                             "_p") + ";");
        d_writer.println("(*_exception->d_epv->f_deleteRef)(_exception->d_object, &throwaway_exception);");
        d_writer.writeCommentLine("Note: alternate constructor does not increment refcount.");
        d_writer.println("throw " + Cxx.getObjectName(exid) + "( _realtype, false );");
        d_writer.backTab();
        d_writer.println("}");
      }
      d_writer.backTab();
      d_writer.println("}");
    }

    //Clean up from return from IOR
    d_writer.println(Cxx.generatePostIORCall(m, d_ext, d_self, isSuper, rarrays));

    if ( ! m.isStatic() ) { 
      if (shortMethodName.equals("addRef") ||
          shortMethodName.equals("deleteRef") ) { 
        if ( shortMethodName.equals("deleteRef") ) { 
          d_writer.println("d_self = 0;");
        }
        d_writer.backTab();
        d_writer.println("}");
      }
    }

    if ( return_type.getType() != Type.VOID ) { 
      d_writer.println("return _result;");
    }
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();

  }

}
