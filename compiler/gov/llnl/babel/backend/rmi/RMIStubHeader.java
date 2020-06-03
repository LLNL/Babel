//
// File:        RMIStubHeader.java
// Package:     
// Copyright:   (c) 2005 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision: 7188 $
// Date:        $Date: 2011-09-27 11:38:42 -0700 (Tue, 27 Sep 2011) $
// Description: 
//
// Copyright (c) 2000-2005, Lawrence Livermore National Security, LLC
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

package gov.llnl.babel.backend.rmi;

import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.c.C;
import gov.llnl.babel.backend.writers.LanguageWriterForC;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;

/**
 * Class <code>RMIStubHeader</code> is called for the C portion of a Stub
 * Header.  It generated declerations necessary for RMI.
 */
public class RMIStubHeader {
  //private LanguageWriterForC d_writer;


  /**
   * This is a convenience utility function that writes the C client
   * header information into the provided language writer output stream.
   * The output stream is not closed on exit.  A code generation
   * exception is thrown if an error is detected.
   *
   * @param symbol the symbol for which a C client header will
   *               be written.
   *
   * @param writer the output writer to which the header will
   *               be written. This will not be closed.
   *
   * @exception gov.llnl.backend.CodeGenerationException
   *    this is a catch all exception. It can be caused by I/O trouble or
   *    violations of the data type invariants.
   */
  /*public static void generateCode(Symbol symbol, LanguageWriterForC writer)
    throws CodeGenerationException
    {
    RMIStubHeader header = new RMIStubHeader(writer);
    header.generateCode(symbol);
    }*/

  /**
   * Create a <code>RMIStubHeader</code> object that will write symbol
   * information to the provided output language writer stream.
   *
   * @param writer the output writer to which the header will
   *               be written. This will not be closed.
   */
  /*  public RMIStubHeader(LanguageWriterForC writer) {
      d_writer = writer;
      }*/

  /**
   * Write C client header information for the provided symbol to the
   * language writer output stream provided in the constructor.  This
   * method does not close the writer output stream and may be called
   * for more than one symbol (although the written header may not be
   * valid input for the C compiler).  A code generation exception is
   * written if an error is detected.
   *
   * @param symbol the <code>Symbol</code> whose header will be
   *               written.
   *
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception. It can be caused by I/O trouble
   *    or violations of the data type invariants.
   */
  public static void generateCode(Symbol symbol, LanguageWriterForC lw) 
    throws CodeGenerationException {
    if (symbol != null) {
      if ( symbol.getSymbolType() == Symbol.PACKAGE ) { 
	  lw.skipIncludeGuard(); 
      }

      switch (symbol.getSymbolType()) {
      case Symbol.CLASS:
      case Symbol.INTERFACE:
        generateExtendable((Extendable) symbol,lw);
        break;
      case Symbol.ENUM:
      case Symbol.PACKAGE:
        break;
      default:
        throw new CodeGenerationException("Unsupported symbol type.");
      }
    } else {
      throw new CodeGenerationException("Unexpected null Symbol.");
    }
  }

  /**
   * Generate a C client header for a SIDL class or interface description.
   * The header file consists of the typedef the defines the symbol type.
   * Note that the typedef comes before any external includes to solve
   * the problem with forward references.  After the typedef comes the
   * external includes, followed by special methods such as cast and new,
   * followed by the regular methods.  The header concludes with close
   * statements for the header guards.
   *
   * @param ext the <code>Extendable</code> whose header is being written.
   *
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception. It can be caused by I/O trouble
   *    or violations of the data type invariants.
   */
  public static void generateExtendable(Extendable ext, LanguageWriterForC lw) 
    throws CodeGenerationException
  {
    generatePragmas(ext,lw);
    generateConnectInternal(ext,lw);

    return;
  }

  public static void generateConnectInternal(Extendable ext, LanguageWriterForC lw) 
    throws CodeGenerationException 
  {
    SymbolID id = ext.getSymbolID();
    /*
      lw.writeComment("RMI connector function for the class.(addrefs)", true);
      lw.println(C.getSymbolName(id));
      
      lw.println(C.getFullMethodName(id, "_connect") 
      + "(const char *, sidl_BaseInterface *_ex);");
    */
    lw.writeComment("RMI connector function for the class. (no addref)", true);
    lw.println(C.getSymbolObjectPtr(id));
    lw.println(C.getFullMethodName(id, "_connectI") 
                     + "(const char * url, sidl_bool ar, " +
               IOR.getExceptionFundamentalType() +
               "*_ex);");
    
    lw.println();

  }

  public static void generatePragmas(Extendable ext, LanguageWriterForC lw) 
    throws CodeGenerationException 
  {
    SymbolID id = ext.getSymbolID();
    lw.println();
    lw.printlnUnformatted("#pragma weak "+C.getFullMethodName(id, "_connectI"));
    lw.println();
  }

}
