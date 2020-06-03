//
// File:        StubHeader.java
// Package:     gov.llnl.babel.backend.jdk
// Copyright:   (c) 2005 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision: 7421 $
// Date:        $Date: 2011-12-15 17:06:06 -0800 (Thu, 15 Dec 2011) $
// Description: Generate code to extern Stub functions that need to be
// availible in to stubs.
// 

package gov.llnl.babel.backend.jdk;

import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeConstants;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.rmi.RMIStubHeader;
import gov.llnl.babel.backend.writers.LanguageWriterForC;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;

/**
 * This class generates the C header code that externs rmicast and connect
 * to the other stubs.
 *
 */
public class StubHeader {

  /**
   * This writer controls where the generated C code goes.
   */
  private LanguageWriterForC d_writer;

  private Context d_context;

  /**
   * Create an object to generate the stub code for a sidl class/interface.
   * This is frequently called from {@link #generateCode(Symbol,
   * LanguageWriter) generateCode} rather than used directly.
   *
   * @param writer the stub code is generated to this output device.
   */
  public StubHeader(LanguageWriterForC writer,
                    Context context) {
    d_writer = writer;
    d_context = context;
  }

  /**
   * Generate a C (.h) file to provide other stubs with certain functions
   * that must be available globally.
   *
   * @param symbol  the symbol for which stubs will be generated.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *  a catch all exception to indicate problems during the code generation
   *  phase of the sidl processing.
   */
  public void generateCode(Symbol symbol) throws CodeGenerationException {
    final String pre = "Java.StubHeader.generateCode: ";
    switch(symbol.getSymbolType()) {
    case Symbol.CLASS:
    case Symbol.INTERFACE:
      if (d_writer instanceof LanguageWriterForC) {
        generateExtendable((Extendable)symbol);
       
      } else {
        throw new CodeGenerationException(pre + "Extendable stub requires C "
                    + "language writer.");
      }
      break;
    case Symbol.ENUM:
    case Symbol.PACKAGE:
      break;
    default:
      throw new CodeGenerationException(pre + "Unsupported symbol type.");
    }
  }

  private void generateExtendable(Extendable ext)  throws CodeGenerationException {
    SymbolID id = ext.getSymbolID();
    String   header = Java.getHeaderFile(id);
    d_writer.writeBanner(ext, header, false, 
                         CodeConstants.C_DESC_HEADER_PREFIX  + id.getFullName());
    d_writer.openHeaderGuard(header);
    d_writer.writeComment(ext, true);
    d_writer.println();
    d_writer.generateInclude(IOR.getHeaderFile(id), true);
    d_writer.println();
    d_writer.openCxxExtern();
    if (!d_context.getConfig().getSkipRMI()) {
      RMIStubHeader.generateCode(ext, d_writer);
    }
    d_writer.closeCxxExtern();
    d_writer.closeHeaderGuard();

  }

  /**
   * Generate a C (.h) file to provide other stubs with certain functions
   * that must be available globally.
   *
   * @param ext     the symbol for which stubs will be generated.
   * @param writer  the output device where the stub should be written.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *  a catch all exception to indicate problems during the code generation
   *  phase of the sidl processing.
   */
  public static void generateCode(Symbol ext, 
                                  LanguageWriterForC writer,
                                  Context context)
    throws CodeGenerationException
  {
    StubHeader header = new StubHeader(writer, context);
    header.generateCode(ext);
  }

}
