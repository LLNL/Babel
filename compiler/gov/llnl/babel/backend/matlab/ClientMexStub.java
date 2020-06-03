//
// File:        ClientMexStub.java
// Package:     gov.llnl.babel.backend.jdk
// Revision:    @(#) $Id: ClientMexStub.java 7188 2011-09-27 18:38:42Z adrian $
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

import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.matlab.Matlab;
import gov.llnl.babel.backend.writers.LanguageWriterForC;
import gov.llnl.babel.symbols.Argument;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.Type;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Class <code>ClientMexStub</code> writes the Matlab native code descriptions that
 * will point to the JNI C code written by <code>ClientJNI</code>.  The class
 * constructor takes a language writer and method <code>generateCode</code>
 * writes the Matlab client code for the specified symbol to the output stream.
 * The language writer output stream is not closed by this object.
 */
public class ClientMexStub {

  private Extendable d_ext = null;
  private Context d_context;
  private static final String s_self           = "mobj";
  private static final String s_mat_id           = s_self + "ID";
  private static final String s_ior           = "_ior";
  private static final String s_stringSize    = "MAX_STRING_SIZE";
  private static final String s_return    = "_ret";


  /**
   * Generate Matlab Mex function stub for CLASS symbol. 
   */
  public static void generateCode(Extendable ext,
                                  Context context) 
    throws CodeGenerationException 
  {
    ClientMexStub source = new ClientMexStub(ext, context);
    source.generateCode();
  }


  /**
   * Create a <code>ClientMexStub</code> object that will write symbol
   * information to the provided output language writer stream.
   */
  public ClientMexStub(Extendable ext, Context context) 
  {
		d_ext = ext;
                d_context = context;
  }


  private void generateCode() throws CodeGenerationException 
  {
    if (d_ext == null) {
      throw new CodeGenerationException("Unexpected null symbol object");
    }
    switch (d_ext.getSymbolType()) 
    {
    case Symbol.CLASS:
    case Symbol.INTERFACE:
      generateExtendableStub();
      break;
    case Symbol.ENUM:
    case Symbol.PACKAGE:
      break;
    default:
      throw new CodeGenerationException("Unsupported symbol type");
    }
  }


  /**
   * Generate the Mat client mexFunction source for a sidl class or interface type.
   * For the most part, the Java source defines the interfaces and classes
   * and the native methods.  All of the real work is done by the JNI code.
   */
  public void generateExtendableStub() 
     throws CodeGenerationException 
  {
   /*
    * Output the banner, function statement, and comment (Matlab help info).
    */
   /*
    d_writer.writeBanner(ext, file, CodeConstants.C_IS_NOT_IMPL,
                         CodeConstants.C_DESC_STUB_PREFIX + id.getFullName());
		*/


  /**
    * Generate a stub file (C Mex function source code) for each method.
    * Non-static method: 
    * the file is under the Matlab class directory. The Matlab class 
    * object ID represented by a 64-bit int is always the first argument.
    *
    * Static method:
    * the file is in the same directory as the Matlab class directory.
    * The function call is not related to the objectID.
    */

    Collection methods = d_ext.getMethods(false);

    for (Iterator m = methods.iterator(); m.hasNext(); ) 
    {
      Method method = (Method) m.next();
      //String matClassName = Matlab.getSymbolName(id);
      //String shortMethodName = method.getShortMethodName();
      //String fullMethodName = Matlab.getFullMethodName(id, method);
      SymbolID id = d_ext.getSymbolID();
      int d_type = d_ext.getSymbolType();

      PrintWriter pw = null;

		  try{
        String methodStubFile = Matlab.getMethodStubFile(id, method); 

		    /*
    	   * Create method filename 
			   */
   	  	boolean f = d_context.getFileManager().getJavaStylePackageGeneration();
   	  	d_context.getFileManager().setJavaStylePackageGeneration(true);
   	   	pw = d_context.getFileManager().createFile(id, d_type, 
              "MATLAB_STUB", methodStubFile);
   		  d_context.getFileManager().setJavaStylePackageGeneration(f);
   	  	LanguageWriterForC writer = 
                  new LanguageWriterForC(pw, d_context);

		  	/*
       	 * Include header files
			   */
		  	Matlab.StubHeaderFiles(writer);
   	    Matlab.addInclude(writer, "sidl_Loader.h", false);
   	    Matlab.addInclude(writer, "sidl_String.h", false);
   	    Matlab.addInclude(writer, "../" + IOR.getHeaderFile(id), false);
       	writer.println();

			  /*
     	   * Define macros.
			   */
        writer.printlnUnformatted("#define " + s_stringSize + " 256");
     	  writer.println();

        stubMexFunction(writer, method);

   	  } finally {
       	if (pw != null) {
        	pw.close();
        }
	  	}
    }// end [for()]
  }


	/**
   * Generate C MEX source function for the C function above.
   */

  private void stubMexFunction(
    LanguageWriterForC writer, Method method)
    throws CodeGenerationException 
  {
    SymbolID id = d_ext.getSymbolID();
    String methodName = method.getShortMethodName();
    String argTypeStr = null;
    String argFormalName = null;
    List args = null;
    args = method.getArgumentListWithOutIndices();
    Argument arg = null;
    StringBuffer plhs_outBuf = new StringBuffer();
    int prh_idx = 0;
    int plh_idx = 0;

		/**
     *  Declaration of MEX function.
     */
    writer.println();
    writer.writeComment("C MEX Function for method " 
      + methodName + "[].", false);
    Matlab.writeMexFunctionSignature(writer);

    writer.println("{");
    writer.tab();


    /** 
      * check for proper number of input and output arguments.
      * check for proper class for input arguments.
      */
    writer.writeCommentLine(
      "check proper number of input and output arguments.");

    StringBuffer argListBuffer = new StringBuffer();
    boolean iorArgFlag = false; 
    int num_input_args = 0; 
    int num_output_args = 0;

    /* iorArgFlag: 
     * if true, add "," in argListBuf once for non-static method.
     * num_input_args = num_of_IN_arg + num_of_INOUT.
     * num_output_args = num_of_INOUT + 1 if return not void.
     */
    for (Iterator a = args.iterator(); a.hasNext(); ) 
    {
      arg = (Argument) a.next();

      switch(arg.getMode()){

      case Argument.IN:
        num_input_args++; 
        if((!iorArgFlag) && (!method.isStatic())) { 
          num_input_args++; 
          argListBuffer.append(s_ior + ", "); 
          iorArgFlag = true;
        }

        break;

      case Argument.INOUT:
        num_input_args++; 
        num_output_args++; 
        if((!iorArgFlag) && (!method.isStatic())) { 
          num_input_args++; 
          argListBuffer.append(s_ior + ", "); 
          iorArgFlag = true;
        }
        break;

      case Argument.OUT:
        num_output_args++; 
        break;

      default:

      }

    } // end for(...)


    if(method.getReturnType().getType() != Type.VOID) {
      num_output_args++; 
    }

    /* check proper number of input */
    writer.println("if (nrhs != " 
      + Integer.toString(num_input_args) + ") {" );
    writer.tab();
    writer.println("mexErrMsgTxt(\"" 
      + Integer.toString(num_input_args) 
      + " input arguments required.\");");
    writer.backTab();
    writer.println("}");

    /* check proper number of output arguments */
    writer.println("if (nlhs > " 
      + Integer.toString(num_output_args) + ") {" );
    writer.tab();
    writer.println("mexErrMsgTxt" 
      + "(\"too many output arguments.\");");
    writer.backTab();
    writer.println("}");

    /* 
     * non-static method only: 
     * check data type of first input argument 
     */
    if ( !method.isStatic()) {
      String matObjName = Matlab.getSymbolName(id);
      writer.println();
      writer.writeCommentLine(
        "check data type of first input argument");
      writer.println("if (!mxIsClass(prhs[0], \"" 
        + matObjName + "\")) {");
      writer.tab();
      writer.println("mexErrMsgTxt(\"First input argument must be type " 
        + matObjName + "\");");
      writer.backTab();
      writer.println("}");
    }
    writer.println();

    /**
     * Get Matlab object ID from prhs[0].
     */
    writer.writeCommentLine(
      "cast Matlab object ID to IOR pointer.");

    writer.println("int64_t " + s_mat_id + ";"); 
    writer.println(s_mat_id 
      + " = mxGetScalar(mxGetFieldByNumber(prhs[0], 0, 0));");
    prh_idx++;


    /**
     * Cast Matlab object ID to IOR pointer.
     */
    String objectName = Matlab.getObjectName(id);
    if (!method.isStatic()) {
     	writer.println(objectName + "* " + s_ior + " = ");
     	writer.println("  (" + objectName + "*)(void*)(ptrdiff_t)" 
        + s_mat_id + ";");
   	}
    writer.println();


    /**
     * Generate IOR function parameters from prhs[] and create plhs[]. 
     * Because Matlab functions pass by value, need get argument value
     * from prhs[i], i = 1, 2, ...
     * If argumentMode==IN, just add the argument in the parameter list. 
     * If argumentMode==INOUT, create a pointer type argument and add it 
     * in the parameter list. 
     */


    /* 
     * The return list of Matlab function call includes original 
     * SIDL function return argument, INOUT and OUT function arguments.
     * If function return is not void, it is always the first argument
     * in its Matlab return list, so plhs_returnBuf saves plhs[0].
     * If Sidl function arguments have INOUT/OUT type, plhs_outBuff all
     * othersaves plhs[].
     */
    Type sidlReturnType = method.getReturnType();
    String returnVar = s_return +  "val"; 
    String plhs_returnBuf = 
      Matlab.generatePlhsMatrix(sidlReturnType, plh_idx, returnVar); 
    String plhsMatrix = null; 
    boolean argHasOutChar = false;

    if(sidlReturnType.getType() != Type.VOID) {
      plh_idx++;
      if( (sidlReturnType.getType() == Type.CHAR) 
        ||(sidlReturnType.getType() == Type.STRING) ) 
      {
        argHasOutChar = true;
      }
    }


    /*  ---  Improve me:  ----
     * this iteration is seperate from the first iteration.
     * We can merge to one iteration later for efficiency. 
     */
    writer.writeCommentLine("create function arguments.");
    for (Iterator a = args.iterator(); a.hasNext(); ) {
      arg = (Argument) a.next();
      Type argType = arg.getType();
      argTypeStr = IOR.getReturnString(
        arg.getType(), d_context, true, false);
      argFormalName = arg.getFormalName();
      int argMode = arg.getMode();


      /* 
       * INOUT argument: 
       * - Generate a variable of original type from prhs[].
       * - Add the variable in IOR function argument list buffer.
       */
        /*
        writer.println(argTypeStr + " " + argFormalName
           + " = *mxGetData(prhs[" + Integer.toString(prh_idx) + "]);");
        */

      if (argMode == Argument.IN) {

        /* Check if a prhs[] matrix class type matches the required type 
         * in SIDL specification. The main reason is that in MxFunction,
         * the mxArray for fcomplex and dcomplex are different. Mix them
         * without type checking will cause error. 
         */
       writer.print(Matlab.checkMxClass
         (argType, "prhs", prh_idx, method.isStatic())); 

       switch(argType.getDetailedType()) {

       case Type.CHAR:
          writer.println(argTypeStr + " " 
            + argFormalName + " = *mxGetChars(prhs[" 
            + prh_idx + "]);");
          break;

       case Type.STRING:
          writer.println(argTypeStr + " " + argFormalName + " = "
            + Matlab.allocateStringSpace(s_stringSize));
          writer.println("int32_t buflen_" + argFormalName
            + " = mxGetM(prhs[" + prh_idx + "])"
            + " * mxGetN(prhs[" + prh_idx + "]) + 1;");
          writer.println("mxGetString(prhs[" 
            + Integer.toString(prh_idx) 
            + "], " + argFormalName +", " 
            + "buflen_" + argFormalName +");");
          break;

       case Type.FCOMPLEX:
          writer.println(argTypeStr + " " + argFormalName + ";");
          writer.println(argFormalName + ".real = *(float*)mxGetData(prhs[" 
            + prh_idx + "]);" );
          writer.println(argFormalName 
            + ".imaginary = *(float*)mxGetImagData(prhs[" + prh_idx + "]);" );
          break;

       case Type.DCOMPLEX:
          writer.println(argTypeStr + " " + argFormalName + ";");
          writer.println(argFormalName + ".real = *mxGetPr(prhs[" 
            + prh_idx + "]);" );
          writer.println(argFormalName 
            + ".imaginary = *mxGetPi(prhs[" + prh_idx + "]);" );
          break;

        default:
          writer.println(argTypeStr + " " + argFormalName
            + " = mxGetScalar(prhs[" + prh_idx + "]);");

        } // end switch()

        argListBuffer.append(argFormalName); 
        if(a.hasNext()) {
          argListBuffer.append(", "); 
        }
        prh_idx++;
        //break;


      /* 
       * INOUT or OUT argument: 
       * - Generate a variable of POINTER type from prhs[].
       *   Allocate the memory on heap because the return argument plhs[] 
       *   points to the same memory location.
       * - Add the variable in function argument list buffer.
       * - Create a plhs[] pointer to the pointer type variable and
       *   write to a buffer, which will be flushed after IOR function call.  
       */

      } else if (argMode == Argument.INOUT) {

        /* Check if a prhs[] matrix class type matches the required type 
         * in SIDL specification. The main reason is that in MxFunction,
         * the mxArray for fcomplex and dcomplex are different. Mix them
         * without type checking will cause error. It als check char/string 
         * type.
         */
        writer.print(Matlab.checkMxClass
          (argType, "prhs", prh_idx, method.isStatic())); 

        // Improve me: use switch instead of argTypeStr 
        if(argTypeStr.equals("char"))
        {
          argHasOutChar = true;
          writer.println(argTypeStr + " *" + argFormalName 
            + " = mxMalloc(sizeof(" + argTypeStr + "));");
          writer.println("*" + argFormalName 
            + " = *mxGetChars(prhs[" 
            + Integer.toString(prh_idx) + "]);");
          argListBuffer.append(argFormalName); 
          plhsMatrix = Matlab.generatePlhsMatrix(argType, plh_idx, 
            "*" + argFormalName); 

        } else if(argTypeStr.equals("char*")) {
          writer.println(argTypeStr + " " + argFormalName + " = "
            + Matlab.allocateStringSpace(s_stringSize));
          writer.println("*" + argFormalName 
            + " = *mxGetChars(prhs[" 
            + Integer.toString(prh_idx) + "]);");
          argListBuffer.append("&" + argFormalName); 
          plhsMatrix = Matlab.generatePlhsMatrix(argType, plh_idx, 
            argFormalName); 

        } else {
          writer.println(argTypeStr + " *" + argFormalName 
            + " = mxMalloc(sizeof(" + argTypeStr + "));");
          writer.println("*" + argFormalName 
            + " = (" + argTypeStr + ") mxGetScalar(prhs[" 
            + Integer.toString(prh_idx) + "]);");
          argListBuffer.append(argFormalName); 
          plhsMatrix = Matlab.generatePlhsMatrix(argType, plh_idx, 
            "*" + argFormalName); 
        }

        if(a.hasNext()) {
          argListBuffer.append(", "); 
        }

        plhs_outBuf.append(plhsMatrix);
        prh_idx++;
        plh_idx++;
        //break;

     } else { // argMode == Argument.OUT
        if(argTypeStr.equals("char")) 
        {
          argHasOutChar = true;
          writer.println(argTypeStr + " *" + argFormalName 
            + " = mxMalloc(sizeof(" + argTypeStr + "));");
          argListBuffer.append(argFormalName); 
          plhsMatrix = Matlab.generatePlhsMatrix(
            argType, plh_idx, "*" + argFormalName); 
        }
        else if(argTypeStr.equals("char*")) 
        {
          argHasOutChar = true;
          writer.println(argTypeStr + " " 
            + argFormalName + " = "
            + Matlab.allocateStringSpace(s_stringSize));
          argListBuffer.append("&" + argFormalName); 
          plhsMatrix = Matlab.generatePlhsMatrix(
            argType, plh_idx, argFormalName); 
        }
        else {
          writer.println(argTypeStr + " *" + argFormalName 
            + " = mxMalloc(sizeof(" + argTypeStr + "));");
          argListBuffer.append(argFormalName); 
          plhsMatrix = Matlab.generatePlhsMatrix(
            argType, plh_idx, "*" + argFormalName); 
        }

        if(a.hasNext()) {
          argListBuffer.append(", "); 
        }

        plhs_outBuf.append(plhsMatrix);
        plh_idx++;
        //break;
     // default:
     }
  
    } //[end for(...)]

    writer.println();


    /**
     * Invoke IOR functions.
     */
    writer.writeCommentLine("invoke IOR function");

    /* create _return_typename if function has return. */
    if(sidlReturnType.getType() != Type.VOID) 
    {
      String returnType = Matlab.getReturnType(method, d_context); 

      /* allocate space for string type return */
      if(sidlReturnType.getType() == Type.STRING) {
        writer.println(returnType + " " + returnVar  + " = "
            + Matlab.allocateStringSpace(s_stringSize));
        writer.print(returnVar + " = ");
      } else {
        writer.print(returnType + " " + returnVar + " = ");
      }
    }
    writer.print("(*_ior->d_epv->f_" + methodName + ")(");
    argListBuffer.append(");");
    writer.println(argListBuffer.toString() );
    writer.println();



    /**
     * Generate return arguments plhs[] for MexFunction.
     * plhs[] has two parts: non-void function return, 
     * and INOUT/OUT argument return. 
     */

    writer.writeCommentLine("return");

    /* 
     * Create a dummy char to initialize plhs[] of type CHAR.
     */
    if(argHasOutChar)
    {
      writer.println("char _dummy_char = ' ';");
      writer.println();
    }


    /**
     * plhs[]: Function return.  
     */

   /* plhs[0] if function has non-void return.  */
    writer.print(plhs_returnBuf);

    /* plhs[] for SIDL argument of INOUT and OUT mode */
    writer.println(plhs_outBuf.toString()); 

    writer.println("return;\n"); 

    /*
     * Close MexFunction.
     */
    writer.backTab();
    writer.println("}");

  }

}

