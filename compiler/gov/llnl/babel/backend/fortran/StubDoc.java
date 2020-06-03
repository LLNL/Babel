//
// File:        StubDoc.java
// Package:     gov.llnl.babel.backend.fortran
// Revision:    @(#) $Revision: 7188 $
// Description: Generate a pseudo-code documentation file for FORTRAN clients
//
// Copyright (c) 2000-2002, Lawrence Livermore National Security, LLC
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


package gov.llnl.babel.backend.fortran;

import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeConstants;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.fortran.Fortran;
import gov.llnl.babel.backend.fortran.StubSource;
import gov.llnl.babel.backend.writers.LanguageWriterForFortran;
import gov.llnl.babel.symbols.Argument;
import gov.llnl.babel.symbols.Comment;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.Type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * This class provides the ability to write a FORTRAN file with a
 * subroutine template for each method the end user has to implement to
 * implement a sidl class. The class will retain the previous user provided
 * implmentation when overwriting a implementation file.
 */
public class StubDoc {

  /**
   * This is the output device.
   */
  private LanguageWriterForFortran d_lw;

  private Context d_context;

  /**
   * Indicator of version of FORTRAN being generated.
   */
  /**
   * Generate an instance to generate documentation for a FORTRAN 
   * client.
   * 
   * @param writer    the output device to which the FORTRAN documentation
   *                  should be written.
   */
  public StubDoc(LanguageWriterForFortran writer,
                 Context context) {
    d_lw      = writer;
    d_context = context;
  }

  /**
   * Write the FORTRAN documentation for a subroutine that 
   * connects to a sidl method. This writes the method
   * signature and declares the types of the arguments.
   * 
   * @param m     the method whose implementation template is to be
   *              written.
   * @param id    the name of the class that owns this method.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   */
  private void extendAndGenerate(Method   m,
                                 SymbolID id)
    throws CodeGenerationException
  {
    List extendedArgs = StubSource.extendArgs(id, m, d_context, true);

    d_lw.println();
    d_lw.println();

    d_lw.writeComment(m, false);
    d_lw.print("subroutine " + Fortran.getMethodStubName(id, m, d_context) +
               "(");
    Iterator i = extendedArgs.iterator();
    while (i.hasNext()) {
      d_lw.print(((Argument)i.next()).getFormalName());
      if (i.hasNext()) {
        d_lw.print(", ");
      }
    }
    d_lw.println(")");
    d_lw.println("implicit none");
    i = Fortran.reorderArguments(extendedArgs).iterator();
    while (i.hasNext()) {
      Argument a = (Argument)i.next();
      d_lw.writeCommentLine(a.getArgumentString());
      d_lw.print(Fortran.getReturnString(a.getType(), d_context,false) + " " 
                 + a.getFormalName());
      if (a.getType().isRarray()) {
        d_lw.println(Fortran.arrayIndices(a.getType().getArrayIndexExprs(), ""));
      }
      else {
        d_lw.println();
      }
    }
    d_lw.println("end");
  }

  /**
   * Generate a create method object.
   */
  private Method create(SymbolID id)
  {
    Method m = new Method(d_context);
    m.setMethodName("_create");
    m.setDefinitionModifier(Method.STATIC);
    String[] s = new String[1];
    s[0] = "Create an instance of class " + id.getFullName();
    m.setComment(new Comment(s));
    m.setReturnType(new Type(id, d_context));
    return m;
  }

  /**
   * Generate a wrapObj method object.
   */
  private Method wrap(SymbolID id)
  {
    Method m = new Method(d_context);
    m.setMethodName("_wrapObj");
    m.setDefinitionModifier(Method.STATIC);
    String[] s = new String[1];
    s[0] = "Create an instance of class that wraps the passed in private_data" + 
      id.getFullName();
    m.setComment(new Comment(s));
    m.setReturnType(new Type(id, d_context));
    Argument a = new Argument(Argument.IN, new Type(Type.OPAQUE), "private_data");
    m.addArgument(a);

    return m;
  }

  /**
   * Add the implicit stub methods to the list of those that must
   * be documented.
   *
   * @param ext the class whose implementation is being written.
   */
  private Collection extendMethods(Extendable ext) 
    throws CodeGenerationException {
    Collection allMethods = ext.getMethods(true);
    final SymbolID id = ext.getSymbolID();
    ArrayList  extendedMethods = new ArrayList(allMethods.size()+3);
    if (!ext.isAbstract()) {
      extendedMethods.add(create(id));
      if(!IOR.isSIDLSymbol(id) ) {
        extendedMethods.add(wrap(id));
      }
    }
    extendedMethods.add(Fortran.createCast(d_context, id));
    extendedMethods.add(Fortran.createCastTwo(d_context, id));

    extendedMethods.addAll(allMethods);
    return extendedMethods;
  }

  /**
   * Generate the implementation FORTRAN file for a sidl class.  The
   * implementation file contains all the subroutines that need to be
   * implemented for the class, and when replacing an implementation file, 
   * the previous implementation is retained.
   * 
   * @param ext    the sidl class whose implementation is to be written.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   */
  public void generateCode(Extendable ext)
    throws CodeGenerationException
  {
    Collection methods = extendMethods(ext);
    final SymbolID id = ext.getSymbolID();

    d_lw.writeBanner(ext, Fortran.getImplFile(id, d_context), false,
                     CodeConstants.C_FORTRAN_DESC_STUB_PREFIX 
                     + id.getFullName());
    d_lw.println("THIS FILE IS DOCUMENTATION - DO NOT COMPILE IT!");
    d_lw.println();
    d_lw.writeComment(ext, false);
    d_lw.println();

    Iterator i = methods.iterator();
    while (i.hasNext()) {
      Method m = (Method) i.next();
      if ( (!m.isAbstract()) && (IOR.generateHookMethods(ext, d_context)) 
           && (!m.isBuiltIn()) ) {
        extendAndGenerate(m.spawnPreHook(), id);
        extendAndGenerate(m, id);
        extendAndGenerate(m.spawnPostHook(false, false), id);
      } else {
        extendAndGenerate(m, id);
      } 
    }

    //Generate documentation for the builtin RMI functions
    if (!d_context.getConfig().getSkipRMI()) {
      extendAndGenerate(IOR.getBuiltinMethod(IOR.EXEC,id, d_context,false), id);
      extendAndGenerate(IOR.getBuiltinMethod(IOR.GETURL,id, d_context,false), id);
      extendAndGenerate(IOR.getBuiltinMethod(IOR.ISREMOTE,id, d_context,false), id);

      Method islocal = IOR.getBuiltinMethod(IOR.ISREMOTE,id,d_context, false);
      islocal.setMethodName("_isLocal");
      String[] comments = new String[1];
      comments[0] = "TRUE if this object is local, false if remote";
      islocal.setComment(new Comment(comments));
      extendAndGenerate(islocal, id);
    }

    extendAndGenerate(IOR.getBuiltinMethod(IOR.HOOKS, id, d_context, false),  
                      id);
    boolean genContractBuiltins = IOR.generateContractBuiltins(ext, d_context);
    if (genContractBuiltins) {
      extendAndGenerate(IOR.getBuiltinMethod(IOR.CONTRACTS, id, d_context, 
                        false), id);
      extendAndGenerate(IOR.getBuiltinMethod(IOR.DUMP_STATS, id, d_context, 
                        false), id);
    }

    if (ext.hasStaticMethod(false)) {
      extendAndGenerate(IOR.getBuiltinMethod(IOR.HOOKS, id, d_context, true), 
                        id);
      if (genContractBuiltins) {
        Method cMeth = IOR.getBuiltinMethod(IOR.CONTRACTS, id, d_context, true);
        cMeth.setMethodName(Fortran.getAltBuiltinName(IOR.CONTRACTS, true));
        extendAndGenerate(cMeth, id);
        cMeth = IOR.getBuiltinMethod(IOR.DUMP_STATS, id, d_context, true);
        cMeth.setMethodName(Fortran.getAltBuiltinName(IOR.DUMP_STATS, true));
        extendAndGenerate(cMeth, id);
      }
    }
  }

  /**
   * Generate the implementation FORTRAN file for a sidl class.  The
   * implementation file contains all the subroutines that need to be
   * implemented for the class, and when replacing an implementation file, 
   * the previous implementation is retained.
   *
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
  */
  public static void generateCode(Symbol                   sym,
                                  LanguageWriterForFortran writer,
                                  Context                  context)
    throws CodeGenerationException
  {
    if (sym instanceof Extendable) {
      StubDoc doc = new StubDoc(writer, context);
      doc.generateCode((Extendable)sym);
    }
  }
}
