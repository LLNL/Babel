//
// File:        RMIIORSource.java
// Package:     gov.llnl.babel.backend.rmi
// Revision:    @(#) $Id: RMIIORSource.java 7188 2011-09-27 18:38:42Z adrian $
// Description: generate IOR implementation source to a pretty writer stream
//
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

package gov.llnl.babel.backend.rmi;

import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.c.C;
import gov.llnl.babel.backend.writers.LanguageWriter;
import gov.llnl.babel.backend.writers.LanguageWriterForC;
import gov.llnl.babel.symbols.Argument;
import gov.llnl.babel.symbols.Class;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.Type;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Class <code>RMIIORSource</code> generates an IOR implementation source file
 * to a language writer output stream.  The constructor takes a language
 * writer stream and method <code>generateCode</code> generates intermediate
 * object representation for the specified symbol to the output stream.  The
 * language writer output stream is not closed by this object.
 */
public class RMIIORSource {
  private static int s_longestBuiltin;

  private Context d_context; 

  /**
   * Store the SymbolID for sidl.BaseClass, if the extendable being
   * printed is not abstract (i.e., is a concrete class).
   */
  SymbolID d_baseClass = null;

  /**
   * Store the SymbolID for sidl.ClassInfo, if the extendable being
   * printed is not abstract (i.e., is a concrete class).
   */
  SymbolID d_classInfo = null;

  /**
   * Store the SymbolID for sidl.ClassInfoI, if the extendable being
   * printed is not abstract (i.e., is a concrete class).
   */
  SymbolID d_classInfoI = null;

  static {
    s_longestBuiltin = 0;
    for(int j = 0; j < IOR.CLASS_BUILT_IN_METHODS; ++j) {
      String mname = IOR.getBuiltinName(j);
      if (mname.length() > s_longestBuiltin) {
        s_longestBuiltin = mname.length();
      }
    }
  }

  private LanguageWriterForC d_writer;

  /**
   * This is a convenience utility function that writes the symbol
   * source information into the provided language writer output
   * stream.  The output stream is not closed on exit.  A code
   * generation exception is thrown if an error is detected.
   */
  public static void generateCode(Symbol symbol, LanguageWriterForC writer,
                                  Context context)
    throws CodeGenerationException 
  {
    RMIIORSource source = new RMIIORSource(writer, context);
    source.generateCode(symbol);
  }

  /**
   * Create a <code>RMIIORSource</code> object that will write symbol 
   * information to the provided output writer stream.
   */
  public RMIIORSource(LanguageWriterForC writer,
                      Context context) {
    d_writer = writer;
    d_context = context;
  }

  /**
   * Write IOR source information for the provided symbol to the language
   * writer output stream provided in the constructor.  This method does
   * not close the language writer output stream and may be called for more
   * than one symbol (although the generated source may not be valid input
   * for the C compiler).  A code generation exception is generated if an
   * error is detected.  No code is generated for enumerated and package
   * symbols.
   */
  public void generateCode(Symbol symbol) throws CodeGenerationException {
    if (symbol != null) {
      switch (symbol.getSymbolType()) {
      case Symbol.PACKAGE:
      case Symbol.INTERFACE:
      case Symbol.ENUM:
        break;
      case Symbol.CLASS:
        generateMethodExecs((Class) symbol);
        break;
      }
    }
  }

  /**
   * Generate the RStub source for a sidl class .  The source
   * file begins with a banner and include files followed by a declaration
   * of static methods and (for a class) external methods expected in the
   * skeleton file.  For classes, the source file then defines a number
   * of functions (cast, delete, initialize EPVs, new, init, and fini).
   */
  private void generateMethodExecs(Class cls) throws CodeGenerationException {
    SymbolID id = cls.getSymbolID();
    String my_symbolName = IOR.getSymbolName(id);
    Collection c = new LinkedList(cls.getMethods(true));
    String self = C.getObjectName(id) + " self";

    //Adding cast as a possiblity for exec.  We can use if for addrefing connect!
    c.add(IOR.getBuiltinMethod(IOR.CAST, cls.getSymbolID(), d_context, false));

    //Do the same for the set_hooks builtin
    if(d_context.getConfig().generateHooks())
      c.add(IOR.getBuiltinMethod(IOR.HOOKS, cls.getSymbolID(), d_context, false));
    
    for( Iterator i=c.iterator(); i.hasNext(); ) { 
      Method m = (Method) i.next();
      boolean needExecErr = false;
      if(!m.isStatic()) {
        int numSerialized;  // number of args + return value + exceptions
        String name = m.getLongMethodName();
        Type returnType = m.getReturnType();
        d_writer.println("static void");
        d_writer.println(my_symbolName + "_" + name + "__exec(");
        d_writer.println("        struct " + my_symbolName + "__object* self,");
        d_writer.println("        struct sidl_rmi_Call__object* inArgs,");
        d_writer.println("        struct sidl_rmi_Return__object* outArgs,");
        d_writer.println("        " +
                         IOR.getExceptionFundamentalType() +
                         "* _ex) {");
        d_writer.tab();
        List args = m.getArgumentList();
        comment("stack space for arguments");
        numSerialized = args.size();
        boolean hasReturn = returnType.getType() != Type.VOID;
        for (Iterator a = args.iterator(); a.hasNext(); ) { 
          Argument arg = (Argument) a.next();
          RMI.declareStackArgs(d_writer,arg, d_context);
        }
        
        if (hasReturn) {
          ++numSerialized;
          RMI.declareStackReturn(d_writer, returnType,m.isReturnCopy(),
                                 d_context);
        }
        d_writer.println("sidl_BaseInterface _throwaway_exception = " + C.NULL 
                         + ";");
	d_writer.println("sidl_BaseInterface _ex3   = " + C.NULL 
                         + ";");
        //A place to put exceptions that are thrown during serialization
        d_writer.println("sidl_BaseException _SIDLex = " + C.NULL 
                         + ";");
      
        comment("unpack in and inout argments");
        for (Iterator a = args.iterator(); a.hasNext(); ) { 
          Argument arg = (Argument) a.next();
          if(arg.getMode() == Argument.IN || 
             arg.getMode() == Argument.INOUT) {
            RMI.unpackArg(d_writer, d_context, 
                          cls, "sidl_rmi_Call", "inArgs", arg, true);
          }
        }
        d_writer.println();
      
        comment("make the call");
        if ( returnType.getType() != Type.VOID ) { 
          d_writer.print( "_retval = ");
        }
        if (m.isStatic()) {
          d_writer.print("(" 
                         + IOR.getStaticEPVVariable(id, IOR.EPV_STATIC, IOR.SET_PUBLIC) 
                         + "." + IOR.getVectorEntry(m.getLongMethodName()) + ")");
        }
        else {
          d_writer.print("(self->" + IOR.getEPVVar(IOR.PUBLIC_EPV) + "->" 
                         + IOR.getVectorEntry(m.getLongMethodName()) + ")");
        }
        d_writer.println("(");
        d_writer.tab();
        IOR.generateArgumentList(d_writer, d_context,
                                 self, false, id, m, false, false, false,
                               "_ex", false, false, false, true );
        d_writer.println(");  SIDL_CHECK(*_ex);");
        d_writer.backTab();
        d_writer.println();
        
        comment("pack return value");
        if ( returnType.getType() != Type.VOID ) { 
          RMI.packType(d_writer, d_context,
                       "sidl_rmi_Return","outArgs", returnType, 
                       "_retval","_retval", Argument.IN, m.isReturnCopy(), 
                       false, true);
        }

        comment("pack out and inout argments");
        for (Iterator a = args.iterator(); a.hasNext(); ) { 
          Argument arg = (Argument) a.next();
          if (arg.getMode() == Argument.OUT || 
              arg.getMode() == Argument.INOUT) {
            RMI.packArg(d_writer, d_context,
                        "sidl_rmi_Return", "outArgs", arg, true);
          }
        }

        comment("clean-up dangling references");

        for (Iterator a = args.iterator(); a.hasNext(); ) { 
          Argument arg = (Argument) a.next();
          String mod = "";
          if(arg.getMode() != Argument.IN) {
            mod = "*";
          }
          if (arg.getType().getDetailedType() == Type.STRING) {
            d_writer.println("if("+mod+arg.getFormalName()
                             +") {free("+mod+arg.getFormalName()+");}");
          } else if (arg.getType().getDetailedType() == Type.CLASS ||
                     arg.getType().getDetailedType() == Type.INTERFACE) {
          
            if(arg.getMode() == Argument.IN) {
              if(!arg.isCopy()) {
                d_writer.println("if("+arg.getFormalName()+
                                 "_str) {free("+arg.getFormalName()+"_str);}");
              }
              d_writer.println("if("+mod+arg.getFormalName()+") {");
              d_writer.tab();
              d_writer.println("sidl_BaseInterface_deleteRef((sidl_BaseInterface)"
                               +mod+arg.getFormalName()+", &_ex3); EXEC_CHECK(_ex3);");
              needExecErr = true;
              d_writer.backTab();
              d_writer.println("}"); 

            } else { //Mode is OUT or INOUT, transfer the reference
              if(arg.isCopy()) {
                d_writer.println("if("+mod+arg.getFormalName()+") {");
                d_writer.tab();
                d_writer.println("sidl_BaseInterface_deleteRef((sidl_BaseInterface)"
                                 +mod+arg.getFormalName()+", &_ex3); EXEC_CHECK(_ex3);");
                needExecErr = true;
              } else {
                d_writer.println("if("+mod+arg.getFormalName()+
                                 " && sidl_BaseInterface__isRemote("+
                                 "(sidl_BaseInterface)"+mod+arg.getFormalName()+", &_throwaway_exception)) {");
              
                d_writer.tab();
                d_writer.println("(*((sidl_BaseInterface)"+mod+arg.getFormalName()+
                                 ")->d_epv->f__raddRef)(((sidl_BaseInterface)"
                                 +mod+arg.getFormalName()+")->d_object, &_ex3); EXEC_CHECK(_ex3);");
                needExecErr = true;
              
                d_writer.println("sidl_BaseInterface_deleteRef((sidl_BaseInterface)"+
                                 mod+arg.getFormalName()+", &_ex3); EXEC_CHECK(_ex3);");
              }
            d_writer.backTab();
            d_writer.println("}"); 

            }
          } else if(arg.getType().getDetailedType() == Type.ARRAY) {
            d_writer.println("sidl__array_deleteRef((struct sidl__array*)"+
                             mod+arg.getFormalName()+");");
          }


        }

        //The caller must ignore any (in)out parameters if an exception occured.
        //We get to EXIT whether we see an exception or not.
        //We then attempt to clean up.  Any exceptions in cleanup jump to EXEC_ERR.
        //EXEC_ERR then throws one or zero exceptions, giving priority to _ex.
        //If that fails, throw it back to the caller (probably an ORB).
        //DeleteRef the new exception, it should be duplicatable.

        // retval
        d_writer.println("EXIT:");      
        if(returnType.getDetailedType() == Type.STRING) {
          d_writer.println("if(_retval) {");
          d_writer.tab();
          d_writer.println("free(_retval);");
          d_writer.backTab();
          d_writer.println("}");
        } else if (returnType.getDetailedType() == Type.CLASS ||
                   returnType.getDetailedType() == Type.INTERFACE){
          if(m.isReturnCopy()) {
            d_writer.println("if(_retval) {");
            d_writer.tab();
            d_writer.println("sidl_BaseInterface_deleteRef((sidl_BaseInterface)"+
                             "_retval, &_ex3); EXEC_CHECK(_ex3);");
            needExecErr = true;
            d_writer.backTab();
            d_writer.println("}");
          } else {
            d_writer.println("if(_retval && sidl_BaseInterface__isRemote("+
                             "(sidl_BaseInterface)_retval, &_throwaway_exception)) {");
            d_writer.tab();
            d_writer.println("(*((sidl_BaseInterface)_retval)->d_epv->f__raddRef)(((sidl_BaseInterface)_retval)->d_object, &_ex3); EXEC_CHECK(_ex3);");
          
            needExecErr = true;
            d_writer.println("sidl_BaseInterface_deleteRef((sidl_BaseInterface)"+
                             "_retval, &_ex3); EXEC_CHECK(_ex3);");
            d_writer.backTab();
            d_writer.println("}");
          }
        } else if(returnType.getDetailedType() == Type.ARRAY) {
          d_writer.println("sidl__array_deleteRef((struct sidl__array*)_retval);");
        }


        // exec_err
        if (needExecErr) {
          d_writer.println("EXEC_ERR:");
        }
        
        d_writer.println("if(*_ex) { ");
        d_writer.tab();
        d_writer.println("_SIDLex = sidl_BaseException__cast(*_ex,&_throwaway_exception);");
        d_writer.println("sidl_rmi_Return_throwException(outArgs, _SIDLex, &_throwaway_exception); ");

        d_writer.println("if(_throwaway_exception) {");
        d_writer.tab();
        comment("Throwing failed, throw _ex up the stack then.");
        d_writer.println("sidl_BaseInterface_deleteRef(_throwaway_exception, &_throwaway_exception);");
        d_writer.println("return;");
        d_writer.backTab();
        d_writer.println("}");

        d_writer.println("sidl_BaseException_deleteRef(_SIDLex, &_throwaway_exception);");
        d_writer.println("sidl_BaseInterface_deleteRef(*_ex, &_throwaway_exception);");
        d_writer.println("*_ex = NULL;");
        d_writer.println("if(_ex3) { ");
        d_writer.tab();
        d_writer.println("sidl_BaseInterface_deleteRef(_ex3, &_throwaway_exception);");
        d_writer.println("_ex3 = NULL;");
        d_writer.backTab();
        d_writer.println("}");
        d_writer.backTab();
        d_writer.println("} else if(_ex3) {");
        d_writer.tab();
        d_writer.println("_SIDLex = sidl_BaseException__cast(_ex3,&_throwaway_exception);");
        d_writer.println("sidl_rmi_Return_throwException(outArgs, _SIDLex, &_throwaway_exception); ");

        d_writer.println("if(_throwaway_exception) {");
        d_writer.tab();
        comment("Throwing failed throw _ex3 up the stack then.");
        d_writer.println("sidl_BaseInterface_deleteRef(_throwaway_exception, &_throwaway_exception);");
        d_writer.println("return;");
        d_writer.backTab();
        d_writer.println("}");

        d_writer.println("sidl_BaseException_deleteRef(_SIDLex, &_throwaway_exception);");
        d_writer.println("sidl_BaseInterface_deleteRef(_ex3, &_throwaway_exception);");
        d_writer.println("_ex3 = NULL;");
        d_writer.backTab();
        d_writer.println("}");
        d_writer.backTab();

        d_writer.println("}");
        d_writer.println();
      }
    }
  }

  /**
   * Generate a single line comment.  This is called out as a separate
   * method to make the code formatting below a little prettier.
   */
  private void comment(String s) {
    d_writer.writeCommentLine(s);
  }
  
  public static void generateExternalSignature(LanguageWriter lw, Symbol sym,
                                               String terminator)
  {
    final SymbolID id = sym.getSymbolID();
    lw.beginBlockComment(false);
    lw.println("This function returns a pointer to a static structure of");
    lw.println("pointers to function entry points.  Its purpose is to provide");
    lw.println("one-stop shopping for loading DLLs.");
    lw.endBlockComment(false);
    lw.println("const " + IOR.getExternalName(id) + "*");
    lw.println(IOR.getExternalFunc(id) + "(void)" + terminator);
  }
}
