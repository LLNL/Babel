//
// File:        ClientMatlab.java
// Package:     gov.llnl.babel.backend.jdk
// Revision:    @(#) $Id: ClientMatlab.java 7188 2011-09-27 18:38:42Z adrian $
// Description: write client (stub) code that supports Matlab clients
//
// Copyright (c) 2000-2003, Lawrence Livermore National Security, LLC
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

package gov.llnl.babel.backend.matlab;

import gov.llnl.babel.BabelConfiguration;
import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.matlab.Matlab;
import gov.llnl.babel.backend.writers.LanguageWriterForMatlab;
import gov.llnl.babel.backend.writers.LanguageWriterForC;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import java.io.PrintWriter;

/**
 * Class <code>ClientMatlab</code> writes the Matlab class constructor description 
 * that will point to the private mexFunction code written by <code>ClientMexStub</code>. 
 * The class constructor takes a language writer and method <code>generateCode</code>
 * writes the mat-function for the specified symbol to the output stream.
 */
public class ClientMatlab {

  private LanguageWriterForMatlab d_lw = null; 
  private Context d_context;
  private static final  String s_self  = "mobj";


  /**
   * This utility function Writes Matlab client information into the provided
   * language writer output stream.
   */
  public static void generateCode(Symbol symbol, 
                                  LanguageWriterForMatlab writer,
                                  Context context)
    throws CodeGenerationException {
    ClientMatlab source = new ClientMatlab(writer, context);
    source.generateCode(symbol);
  }

  /**
   * Create a <code>ClientMatlab</code> object that will write symbol
   * information to the provided output language writer stream.
   */
  public ClientMatlab(LanguageWriterForMatlab writer,
                      Context context) {
    d_lw = writer;
    d_context = context;
  }

  /**
   * Generate the Matlab client mat-function source for a sidl class or 
   * interface type. All of the real work is done by ClientMex code. 
   */
  public void generateCode(Symbol symbol) throws CodeGenerationException {
    if (symbol == null) {
      throw new CodeGenerationException("Unexpected null symbol object");
    }
    switch (symbol.getSymbolType()) {
    case Symbol.CLASS:
      generateExtendable((Extendable) symbol);
      break;
    case Symbol.ENUM:
      //generateEnumeration((Enumeration) symbol);
      break;
    case Symbol.INTERFACE:
      //generateExtendable((Extendable) symbol);
      break;
    case Symbol.PACKAGE:
      // do nothing for a package
      break;
    default:
      throw new CodeGenerationException("Unsupported symbol type");
    }
  }


  /**
   * Generate Matlab class constructor. It has a Mat-file and a CMEX file
   * in the private directory which loads IOR to get a Matlab object ID. 
   */
  private void generateExtendable(Extendable ext) 
      throws CodeGenerationException {

    /* Generate Matlab class constructor Mat file.  */
   	generateMatConstructor(ext);

    /* Generate a private C MEX source file for the Matlab class constructor. */
    SymbolID id = ext.getSymbolID();
    int d_type = ext.getSymbolType();
    PrintWriter pw = null;
    try {
    	String file =  Matlab.getConstructorStubFile(id);
    	boolean f = d_context.getFileManager().getJavaStylePackageGeneration();
    	d_context.getFileManager().setJavaStylePackageGeneration(true);
    	pw = d_context.getFileManager().createFile(id, d_type, "MATLAB_PRIVATE", file);
    	d_context.getFileManager().setJavaStylePackageGeneration(f);
    	LanguageWriterForC writer = new LanguageWriterForC(pw, d_context);
    	generateMexConstructor(ext, file, writer);
    } finally {
      if (pw != null) {
        pw.close();
      }
    }
  }


  /**
   * Create a Matlab class directory and generate code for class constructor. 
   * The class constructor has a filename @FullClassName/FullClassName.m.
   * and has no argument.
   */
  private void generateMatConstructor(Extendable ext)
       throws CodeGenerationException {

    SymbolID id = ext.getSymbolID();
    String className = Matlab.getSymbolName(id);
    String returnObj = s_self;

    /* Output the banner (Matlab help info).  */
    /*
    d_writer.writeBanner(ext, file, CodeConstants.C_IS_NOT_IMPL,
                         CodeConstants.C_DESC_STUB_PREFIX + id.getFullName());
		*/

    d_lw.println("function " + returnObj + " = " + className + "()");
    d_lw.println();
    d_lw.println(returnObj + ".id = int64(" 
         + Matlab.getConstructorStubName(id) +"());" );
    d_lw.println(returnObj + " = class(" 
         + returnObj +", \'" + className +"\');");
  }


    /*
     * Generate a C MEX source file for the Matlab class constructor. 
     * This file calls _get_ior() and returns a 64-bit Matlab object ID 
     * casted from a IOR pointer. 
     */
    PrintWriter pw = null;
  /** 
   * Create a file private/private_createID.c in each Matlab class directory.
   * It creates a Matlab object from IOR reference. A Matlab object is 
   * represented by a 64-bit integer field called "id". 
   */
  private void generateMexConstructor(
      Extendable ext, String file, LanguageWriterForC writer) 
    throws CodeGenerationException {

    SymbolID id = ext.getSymbolID();
    String iorHeader = IOR.getHeaderFile(id);
    String ext_name = IOR.getExternalName(id);
    String s_id      = s_self + "_id";
    String tmp_obj      = "p_" + s_self;
    String s_externals      = "_externals";
    String s_externals_func = "_getExternals";

    /* Generate the file banner.  */

    /* Include header files. */
    writer.generateInclude("../../" + iorHeader, false);
    Matlab.StubHeaderFiles(writer);
    writer.println();
    Matlab.StubNullDefine(writer);
    writer.println();

    /* Create Matlab object ID from IOR pointer.  */
    writer.writeComment("Hold pointer to IOR functions.", false);
    writer.println("static const " + ext_name + " *" + s_externals
                     + " = NULL;");

    writer.println();
		writer.writeComment("Return pointer to internal IOR functions.", false);
    writer.println("static const " + ext_name + "* _loadIOR(void)");
    writer.println("{");
    writer.tab();
    if (BabelConfiguration.isSIDLBaseClass(id)) {
      writer.println(s_externals + " = " + IOR.getExternalFunc(id) + "();");
    } else {
      writer.printlnUnformatted("#ifdef SIDL_STATIC_LIBRARY");
      writer.println(s_externals + " = " + IOR.getExternalFunc(id) + "();");
      writer.printlnUnformatted("#else");
      writer.println(s_externals + " = (" + ext_name + "*)sidl_dynamicLoadIOR(\""
            +id.getFullName() + "\",\"" + IOR.getExternalFunc(id) + "\") ;");
      writer.println("sidl_checkIORVersion(\"" + id.getFullName() + 
                       "\", " +
                     s_externals + "->d_ior_major_version, " +
                     s_externals + "->d_ior_minor_version, " +
                     Integer.toString(IOR.MAJOR_VERSION) + ", " +
                     Integer.toString(IOR.MINOR_VERSION) + ");");
      writer.printlnUnformatted("#endif");
    }
    writer.println("return " + s_externals + ";");
    writer.backTab();
    writer.println("}");

    writer.println();
    writer.printlnUnformatted("#define " + s_externals_func + "() ("
                + s_externals + " ? " + s_externals + " : _loadIOR())");


    /*
     * Generate the mexFunction. It takes an IOR reference, cast to void*,  
     * then cast to the a 64-bit int as a Matlab object id. 
     */
    writer.writeComment("Get the Matlab object ID from IOR.", true);
    writer.println("void mexFunction(int nlhs, mxArray *plhs[], ");
    writer.println("                 int nrhs, const mxArray *prhs[])");
    writer.println("{");
    writer.tab();
	  writer.writeComment("Cast IOR function pointer to int64 integer.", false);
    writer.println("void *" + tmp_obj + 
           " = (void*)(ptrdiff_t)(*_getExternals()->createObject)();");
    writer.println("int64_t "+ s_id + " = (int64_t)(ptrdiff_t) " + tmp_obj + ";");
    writer.println("plhs[0] = mxCreateNumericMatrix(1, 1, mxINT64_CLASS, mxREAL);");
    writer.println("*(int64_t*)mxGetPr(plhs[0]) = " + s_id + ";");
    writer.backTab();
    writer.println("}");
    writer.println();
  }

}

