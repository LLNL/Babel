//
// File:        GenerateServer.java
// Package:     gov.llnl.babel.backend.fortran
// Revision:    @(#) $Revision: 7421 $
// Description: generate C code and FORTRAN impl to implement a class
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
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.CodeGenerator;
import gov.llnl.babel.backend.CodeSplicer;
import gov.llnl.babel.backend.fortran.ImplModule;
import gov.llnl.babel.backend.fortran.ImplSource;
import gov.llnl.babel.backend.fortran.SkelSource;
import gov.llnl.babel.backend.writers.LanguageWriterForC;
import gov.llnl.babel.backend.writers.LanguageWriterForFortran;
import gov.llnl.babel.symbols.Class;
import gov.llnl.babel.symbols.Struct;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.SymbolTable;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * This class implements the {@link gov.llnl.babel.backend.CodeGenerator
 * CodeGenerator} interface for the FORTRAN server side code generator.
 * This generator creates C source code to provide the glue between
 * IOR and an object implemented in FORTRAN.  This will also generate an
 * implementation file in FORTRAN for the developer to fill in with
 * the appropriate behavior.
 */
public class GenerateServer implements CodeGenerator
{
  private String d_name = "f77";

  private Context d_context = null;

  /**
   * Create a new instance.
   */
  public GenerateServer() {
  }

  /**
   * Create an implementation source file for <code>symbol</code> if it is a
   * class.
   *
   * @param symbol the symbol to generate an implementation outline..
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   */
  private void generateImplSource(Symbol symbol)
    throws CodeGenerationException
  {
    final SymbolID id = symbol.getSymbolID();
    final int type = symbol.getSymbolType();
    if (type == Symbol.CLASS) {
      Class cls = (Class)symbol;
      PrintWriter pw = null;
      try {
        String f = Fortran.getImplFile(id, d_context);
        CodeSplicer splicer =
          d_context.getFileManager().getCodeSplicer(id, type, f, true, true);
        pw       = d_context.getFileManager().
          createFile(id, type, "IMPLSRCS", f);
        LanguageWriterForFortran writer = 
          new LanguageWriterForFortran(pw, d_context);
        ImplSource.generateCode(cls, writer, splicer, d_context);
      }
      catch ( IOException ex ) { 
        throw new CodeGenerationException( ex.getMessage() );
      } 
      catch ( NoSuchAlgorithmException nsae) {
        throw new CodeGenerationException( "NoSuchAlgorithmException: " +
                                           nsae.getMessage());
      }
      finally {
        if (pw != null) {
          pw.close();
        }
      }
    }
  }

  /**
   * Create an implementation module file for <code>symbol</code> if it is a
   * class.
   *
   * @param symbol the symbol to generate an implementation outline..
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   */
  private void generateImplModule(Symbol symbol)
    throws CodeGenerationException
  {
    final SymbolID id = symbol.getSymbolID();
    final int type = symbol.getSymbolType();
    if (type == Symbol.CLASS && Fortran.isFortran90(d_context)) {
      Class cls = (Class)symbol;
      PrintWriter pw = null;
      try {
        String f = Fortran.getImplModuleFile(id, d_context);
        CodeSplicer splicer =
          d_context.getFileManager().getCodeSplicer(id, type, f, false, true);
        pw       = d_context.getFileManager().
          createFile(id, type, "IMPLMODULESRCS", f);
        LanguageWriterForFortran writer =
          new LanguageWriterForFortran(pw, d_context);
        ImplModule.generateCode(cls, writer, splicer, d_context);
      }
      catch ( IOException ex ) { 
        throw new CodeGenerationException( ex.getMessage() );
      } 
      catch ( NoSuchAlgorithmException nsae) {
        throw new CodeGenerationException( "NoSuchAlgorithmException: " +
                                           nsae.getMessage());
      }
      finally {
        if (pw != null) {
          pw.close();
        }
      }
    } else if(type == Symbol.STRUCT && Fortran.hasBindC(d_context)) {
      //TODO: why is this in GenerateServer?
      Struct structSym = (Struct)symbol;
      PrintWriter pw = null;
      try {
        String f = Fortran.getStructModuleFile(id, d_context);
        if (!Fortran.isFortran77(d_context)){
          CodeSplicer splicer =
            d_context.getFileManager().getCodeSplicer(id, type, f, false, true);
          pw       = d_context.getFileManager().
          createFile(id, type, "TYPEMODULESRCS", f);
          LanguageWriterForFortran writer = 
            new LanguageWriterForFortran(pw, d_context);
          StructModule.generateCode(structSym, writer, splicer, d_context);
        }
      }
      catch ( IOException ex ) { 
        throw new CodeGenerationException( ex.getMessage() );
      } 
      catch ( NoSuchAlgorithmException nsae) {
        throw new CodeGenerationException( "NoSuchAlgorithmException: " +
                                           nsae.getMessage());
      }
      finally {
        if (pw != null) {
          pw.close();
        }
      }
    }
  }

  /**
   * Create a skeleton source file for <code>symbol</code> if it is a class.
   *
   * @param symbol the symbol to generate skels for.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   */
  private void generateSkelSource(Symbol symbol)
    throws CodeGenerationException 
  {
    final SymbolID id = symbol.getSymbolID();
    final int type = symbol.getSymbolType();
    if (type == Symbol.CLASS) {
      PrintWriter pw = null;
      try {
        String f = Fortran.getSkelFile(id);
        pw       = d_context.getFileManager().
          createFile(id, type, "SKELSRCS", f);
        LanguageWriterForC writer = 
          new LanguageWriterForC(pw, d_context);
        SkelSource.generateCode((gov.llnl.babel.symbols.Class)symbol, writer,
                                d_context);
      }
      catch (NoSuchAlgorithmException nsae) {
        throw new CodeGenerationException("NoSuchAlgorithmException: " +
                                          nsae.getMessage());
      }
      finally {
        if (pw != null) {
          pw.close();
        }
      }
    }
  }

  /**
   * Create a fortran skeleton source file for <code>symbol</code> if it is a struct.
   * 
   * @param symbol the symbol to generate skels for.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   */
  private void generatefSkelSource(Symbol symbol)
    throws CodeGenerationException 
  {
    final SymbolID id = symbol.getSymbolID();
    final int type = symbol.getSymbolType();
    if (type == Symbol.CLASS) {
      PrintWriter pwf = null;
      try {
        gov.llnl.babel.symbols.Class cls =
          (gov.llnl.babel.symbols.Class) symbol;
        
        /* Create second tier fortran skels for F90 structs */ 
        if (Fortran.hasBindC(d_context) ||
            (Fortran.isFortran90(d_context) && cls.hasStruct())) {
          String ff = Fortran.getfSkelFile(id, d_context);
          pwf = d_context.getFileManager().createFile(id, type, "FSKELSRCS", ff);
          LanguageWriterForFortran writer = 
            new LanguageWriterForFortran(pwf, d_context);
          fSkelSource.generateCode(cls, writer, d_context);
        }
      }
      catch (NoSuchAlgorithmException nsae) {
        throw new CodeGenerationException("NoSuchAlgorithmException: " +
                                          nsae.getMessage());
      }
      finally {
        if (pwf != null) pwf.close();
      }
    }
  }

  /**
   * Given a set of symbol ids, this method will generate FORTRAN 
   * skeletons (written in C) for all classes in the set.  This will
   * also generate a FORTRAN source file with templates for each
   * of the required methods. The end user who is implementing the
   * sidl object is expected to fill in the FORTRAN template.
   * 
   * @param symbols   the set of symbol names for which code will be
   *                  generated as needed.  Each object in the
   *                  set should be a {@link
   *                  gov.llnl.babel.symbols.SymbolID SymbolID}.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   */
  public void generateCode(Set symbols)
    throws CodeGenerationException 
  {
    Iterator i = symbols.iterator();
    SymbolTable table = d_context.getSymbolTable();
    while (i.hasNext()) {
      SymbolID id     = (SymbolID)i.next();
      Symbol   symbol = table.lookupSymbol(id);
      generateSkelSource(symbol);
      generatefSkelSource(symbol);
      generateImplSource(symbol);
      generateImplModule(symbol);
    }
  }

  public String getType()
  {
    return "skel";
  }

  public boolean getUserSymbolsOnly()
  {
    return true;
  }

  public Set getLanguages()
  {
    Set result = new TreeSet();
    result.add("f77");
    result.add("f77_31");
    result.add("f90");
    result.add("f03");
    return result;
  }

  public void setName(String name)
    throws CodeGenerationException
 {
    if (getLanguages().contains(name)) {
      d_name = name;
    }
    else {
      throw new CodeGenerationException
        ("\"" +name + "\" is not a valid name for the Fortran generator.");
    }
  }

  public String getName() { return d_name; }

  public void setContext(Context context) {
    d_context = context;
  }
}
