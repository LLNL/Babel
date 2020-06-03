//
// File:        ModuleSource.java
// Package:     gov.llnl.babel.backend.fortran
// Revision:    @(#) $Revision: 7473 $
// Description: Generate a module file for FORTRAN 90 clients
//
// Copyright (c) 2002-2003, Lawrence Livermore National Security, LLC
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

import gov.llnl.babel.BabelConfiguration;
import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.CodeSplicer;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.Utilities;
import gov.llnl.babel.backend.fortran.AbbrevHeader;
import gov.llnl.babel.backend.fortran.Fortran;
import gov.llnl.babel.backend.fortran.StubSource;
import gov.llnl.babel.backend.mangler.FortranMangler;
import gov.llnl.babel.backend.mangler.NameMangler;
import gov.llnl.babel.backend.mangler.NonMangler;
import gov.llnl.babel.backend.writers.LanguageWriterForFortran;
import gov.llnl.babel.symbols.Argument;
import gov.llnl.babel.symbols.AssertionExpression;
import gov.llnl.babel.symbols.Class;
import gov.llnl.babel.symbols.Comment;
import gov.llnl.babel.symbols.Enumeration;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.SymbolTable;
import gov.llnl.babel.symbols.Type;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * This class provides the ability to write a FORTRAN 90 module for
 * a sidl class/interface. 
 */
public class ModuleSource {
  private Context d_context;

  private static final int s_CASTS_PER_LINE = 7;

  /**
   * If Fortran90 is being generated, we need to keep track of 
   * what explicit method interfaces have been already generated.
   */
  private static HashSet interfacesWritten = null;

  /**
   * If Fortran90 is being generated, we need to keep track of
   * what explicit method dispatches have been already generated.
   */
  private static HashSet dispatchesWritten = null;

  /**
   * This is the output device.
   */
  private LanguageWriterForFortran d_lw, d_bindc_decls;

  private static final String[] s_intent_spec = {
    ", intent(in)",
    ", intent(inout)",
    ", intent(out)"
  };

  /**
   * If Fortran03 is being generated, we need to keep track of 
   * what bind(C) interfaces have already been generated because of
   * shortcomings in IBM's compiler.
   *
   * Even worse, it forces us to generate all the bind(c) declarations in
   * one place. This is the only reason for the exitstance of the
   * d_bindc_decls include file.
   */
  private HashSet d_bindc_interfaces_written = null;


  private SymbolID d_id;

  /**
   * Generate an instance to write the module for a FORTRAN 90
   * client.
   * 
   * @param writer  the output device to which the FORTRAN 90 module
   *                  should be written.
   */
  public ModuleSource(SymbolID id,
                      LanguageWriterForFortran writer,
                      Context context)
    throws CodeGenerationException
  {
    d_lw = writer;
    d_context = context;
    d_id = id;

    d_bindc_interfaces_written = new HashSet();
    String nf = Fortran.getModBindCdeclFile(d_id);
    PrintWriter nw        = d_context.getFileManager().
      createFile(d_id, Symbol.CLASS, "STUBHDRS", nf);
    d_bindc_decls = new LanguageWriterForFortran(nw, d_context);
    d_bindc_decls.writeCommentLine("-*- F90 -*-");
    d_bindc_decls.writeComment("IBM's F2003 compiler gets confused if we have lots of "+
                               "identical local interface declarations, "+
                               "so we gather them in this include file.", true);
  }

  /**
   * This is a convenience utility function specifically for the generation
   * of super "Stub" functions in the Impl files. 
   * The output stream is not closed on exit.  A code
   * generation exception is thrown if an error is detected.
   *
   * @param writer the output writer to which the stub source will
   *               be written. This will not be closed.
   *
   * @param cls The class in which these supers are to be generated      
   *
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception. It can be caused by I/O trouble or
   *    violations of the data type invariants.
   */
  public static void generateSupers(Class             cls,
                                    LanguageWriterForFortran writer,
                                    CodeSplicer              splicer,
                                    Context                  context,
                                    NameMangler mang)
    throws CodeGenerationException
  {
    ModuleSource source = new ModuleSource(cls.getSymbolID(), writer, context);
    source.generateSupers(cls, splicer, mang);
  }  

  public void generateSupers(Class cls, CodeSplicer splicer, NameMangler mang)
    throws CodeGenerationException
  {
    Collection methods = cls.getOverwrittenClassMethods();
    for (Iterator m = methods.iterator(); m.hasNext(); ) {
      Method method = (Method) m.next();
      extendAndGenerateSuper(cls, method, splicer, mang);
    }
  }  


  /**
   * Generate the argument list for the subroutine, including the
   * opening and closing parens.
   *
   * @param List     List of arguments to be printed
   * @param rhs_map  An (optional) map of argument names to substitute
   *                 expressions (used for F90 struct flattening)
   * @param line_break Emit a line break after all the arguments
   */
  private void printArgs(LanguageWriterForFortran lw,
                         List args,
                         Map rhs_map,
                         boolean line_break,
                         boolean skip_retval)
  {
    lw.print("(");
    Iterator i = args.iterator();
    while (i.hasNext()) {
      Argument arg = (Argument)i.next();
      String expr = arg.getFormalName();
      if(skip_retval && Fortran.s_return.equals(expr))
        continue;
      if(rhs_map != null) expr = (String)rhs_map.get(expr);
      lw.print(expr);
      if (i.hasNext()) {
        lw.print(", ");
      }
    }
    lw.print(")");
    if(line_break) lw.println();
  }

  private void printArgs(List args,
                         Map rhs_map,
                         boolean line_break,
                         boolean skip_retval)
  {
    printArgs(d_lw, args, rhs_map, line_break, skip_retval);
  }

  private void printArgs(List args, Map rhs_map)
  {
    printArgs(d_lw, args, rhs_map, true, false);
  }
  
  /**
   * Write out the declaration of an argument.
   */
  private void declareArgument(LanguageWriterForFortran lw,
                               Argument a,
                               boolean showIntent,
                               boolean do_bindc,
                               boolean do_alternate)
    throws CodeGenerationException
  {
    Type argType = a.getType();
    lw.writeCommentLine(a.getArgumentString());

    if(do_bindc) {
      if((argType.isRarray() && !do_alternate) || argType.isStruct())
        lw.print("type(c_ptr), value");
      else
        lw.print(Fortran.getBindCType(argType, a.getMode()));
    }
    else {
      switch(argType.getDetailedType()) {
      case Type.INTERFACE:
      case Type.STRUCT:
      case Type.CLASS:
        if(Fortran.hasBindC(d_context) && a.getFormalName().equals(Fortran.s_self)) {
          lw.print("class(" + Fortran.getTypeName(argType.getSymbolID()) + ")");
          break;
        }
        //fallthrough intended
      default:
        lw.print(Fortran.getArgumentString(argType, d_context, false));
      }
    }
      
    if (showIntent) lw.print(" " + s_intent_spec[a.getMode()]);
    
    if (!do_bindc && argType.isRarray()) {
      Iterator indexVar = argType.getArrayIndexExprs().iterator();
      AssertionExpression ae = (AssertionExpression)indexVar.next();
      final int dimen = argType.getArrayDimension();
      if(Fortran.hasBindC(d_context)) lw.print(", target");
      lw.print(", dimension(:");
      int count = 1;
      while(indexVar.hasNext()) {
        ae = (AssertionExpression)indexVar.next();
        lw.print(", :");
        count++;
      }

      if (count != dimen) {
        throw new CodeGenerationException("Babel: Error in Rarray dimensions. Dimensions="+dimen+" of type don't match those given in name "+a.getFormalName()+" (dimensions="+count+").");
      }
      lw.print(")");
    }
    lw.println(" :: " + a.getFormalName());
  }

  private void declareArgument(Argument a,
                               boolean showIntent,
                               boolean do_bindc,
                               boolean do_alternate)
    throws CodeGenerationException
  {
    declareArgument(d_lw, a, showIntent, do_bindc, do_alternate);
  }
 
  /**
   * Prints a declaration for a proxy variables necessary for some data types.
   */
  private void printBindCProxyDecl(Argument arg)
    throws CodeGenerationException {

    Fortran.printBindCProxyDecl(arg.getType(),
                                arg.getMode(),
                                "bindc_" + arg.getFormalName(),
                                d_lw, d_context);
  }
  
  /**
   * Emits a init expression for proxy variables necessary for some data types.
   */
  private void printBindCProxyInit(Argument arg) {
    int mode = arg.getMode();
    String name = arg.getFormalName();
    Type t = arg.getType();
    if(mode == Argument.OUT) {
      //default initialization
      Fortran.printInitializeEmpty(t, "bindc_" + name, d_lw);
    }
    else {
      switch(t.getDetailedType()) {
      // TODO: can we use Fortran.printFortran2BindC for these two cases, too?
      case Type.STRING:
        if(mode != Argument.IN)
          d_lw.println("bindc_" + name + " = sidl_String_strdup(trim(" + name +
                       ") // c_null_char)");
        break;
      case Type.STRUCT:
        d_lw.println("bindc_" + name + " = " + name);
        break;
      case Type.INTERFACE:
        //method invocations using interfaces have to pass the object
        //pointer as self instead of the reference to the interface!
        if(Fortran.s_self.equals(name)) {
          d_lw.println("bindc_" + name + " = " + name + "%d_object");
          break;
        }
        //intended fallthrough
      default:
        Fortran.printFortran2BindC(t, name, "bindc_" + name, mode, false, false, d_lw);
      }
    }
  }

  /**
   * Emits code that copies proxy variables to out arguments.
   */
  private void printBindCProxyReturn(Argument arg) {
    int mode = arg.getMode();
    if(mode != Argument.OUT && mode != Argument.INOUT)
      return;
    String name = arg.getFormalName();
    Type t = arg.getType();
    switch(t.getDetailedType()) {
    // TODO: can we use Fortran.printFortran2BindC for these two cases, too?
    case Type.STRING:
      d_lw.println("if (c_associated(bindc_" + name + ")) then");
      d_lw.tab();
      d_lw.println("call sidl_copy_c_str(" + name + ", len(" + name +
                   ", c_size_t), bindc_" + name + ")");
      d_lw.println("call sidl_String_free(bindc_" + name + ")");
      d_lw.backTab();
      d_lw.println("else");
      d_lw.tab();
      d_lw.println(name + " = ''");
      d_lw.backTab();
      d_lw.println("endif");
      break;      
    case Type.STRUCT:
      d_lw.println(name + " = bindc_" + name);
      break;
    case Type.ARRAY:
      if(t.isRarray()) break;
      //fallthrough intended
    default:
      Fortran.printBindC2Fortran(t, "bindc_" + name, name , mode, false, false, d_lw);
      break;
    }
  }

  private void writeMethodStubHeader(Method m,
                                     String name,
                                     List args,
                                     boolean do_f_function)
    throws CodeGenerationException
  {
    
    boolean with_bindc = Fortran.hasBindC(d_context);
    String proc_type = do_f_function ? "function" : "recursive subroutine";
    d_lw.print(proc_type + " " + name);
    printArgs(args, null, /*line break*/ !do_f_function, /*skip retval*/do_f_function);
    if(do_f_function) d_lw.println(" result(" + Fortran.s_return + ")");
    
    d_lw.tab();

    if(with_bindc) d_lw.println("use, intrinsic :: iso_c_binding");

    Set seen = new HashSet();
    for(Iterator I = Fortran.getStructTypes(args).iterator(); I.hasNext(); ) {
      Type t = (Type) I.next();
      if(t.isStruct() && !seen.contains(t.getSymbolID()))
        seen.add(t.getSymbolID());
      d_lw.generateUse(Fortran.getModule(t.getSymbolID(), d_context), new TreeMap());
    }

    d_lw.println("implicit none");
    
    for(Iterator I = args.iterator(); I.hasNext(); ) {
      Argument arg = (Argument)I.next();
      if(do_f_function && arg.getFormalName().equals(Fortran.s_return))
        continue;
      declareArgument(arg, true, false, false);
    }

    if(do_f_function) {
      //explicity declare retval
      d_lw.writeCommentLine(" function return value");
      d_lw.print(Fortran.getReturnString(m.getReturnType(), d_context, false));
      d_lw.println(" :: " + Fortran.s_return);
    }
    d_lw.println();
  }

  private boolean hasOutgoingValue(Method m) {
    for(Iterator I = m.getArgumentListWithIndices().iterator(); I.hasNext(); ) {
      Argument a = (Argument)I.next();
      int mode = a.getMode();
      if(mode == Argument.OUT || mode == Argument.INOUT)
        return true;
    }
    return false;
  }

  private Argument findException(List L) {
    for(Iterator I = L.iterator(); I.hasNext();) {
      Argument arg = (Argument) I.next();
      if(arg.getFormalName().equals(Fortran.s_exception))
        return arg;
    }
    return null;
  }
  
  
  /**
   * Write the FORTRAN 90 module for a subroutine that corresponds to a 
   * sidl class/interface method. This writes method signatures and declares 
   * the types of the arguments.
   * 
   * @param m             the method whose module method is to be written.
   * @param id            the name of the class that owns this method.
   * @param suffix        string to add to method name
   * @param convertRarray <code>true</code> means that rarray arguments
   *                      should be converted to normal arrays before
   *                      writing them out.
   * @param isBuiltin     <code>true</code> means this is one of the 
   *                      built-in methods.
   * @param non           Non-name mangler
   * @param fort          Fortran name mangler
   *
   * @exception  gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   */
  private void writeMethodStub(Method      m,
                               SymbolID    id,
                               String      suffix,
                               boolean     convertRarray,
                               boolean     isBuiltin,
                               NameMangler non,
                               NameMangler fort)
    throws CodeGenerationException
  {
    boolean with_bindc = Fortran.hasBindC(d_context);
    boolean gen_direct_dispatch = with_bindc &&
      !Fortran.needsCStub(m, !convertRarray,  d_context);
    boolean is_f_function = Fortran.isFortranFunction(m, d_context);
    boolean is_c_function = gen_direct_dispatch && is_f_function;
    
    List extendedArgs = StubSource.extendArgs(id, m, d_context, false);
    String proc_type = is_f_function ? "function" : "subroutine";
    String procptr_name = null;
    Map indexMap = null;
    
    String methodName = m.getLongMethodName() + suffix;

    //TODO: there's an inconsistency in the way static contract builtins are
    //implemented; I don't really understand the underlying problem; Need to
    //talk to Tammy about this. This is basically the hack that was in place
    //to make it work in the meantime.
    boolean is_contract_builtin = isBuiltin &&
      (methodName.equals("set_contracts_static" + suffix) ||
       methodName.equals("dump_stats_static" + suffix));
    
    if(isBuiltin && !is_contract_builtin)
      m.setMethodName("_" + m.getShortMethodName());
    
    /*
     * The following is a "quick" fix.  Some thought should be given
     * to the proper way of doing this.
     */
    String stubName;
    if (convertRarray) {
      stubName = Fortran.getAltStubName(id, m, d_context);
      extendedArgs = StubSource.convertRarrayToArray(extendedArgs, d_context);
      indexMap = new HashMap(0);
    } else {
      stubName = Fortran.getMethodStubName(id, m, non, fort, d_context);
      indexMap = m.getRarrayInfo();
    }
    
    writeMethodStubHeader(m, methodName, extendedArgs, is_f_function);
    
    // For Bind(C) bindings, we need proxy variables for some data types
    if(with_bindc) {
      //declare the Bind(C) interface
      generateBindCInterface(id, m, stubName, gen_direct_dispatch,
                             is_c_function, convertRarray);
      
      for(Iterator I = extendedArgs.iterator(); I.hasNext(); ) {
        printBindCProxyDecl((Argument) I.next());
      }

      //declare a fortran procedure pointer
      if(gen_direct_dispatch) {
        procptr_name = IOR.getVectorEntry((is_contract_builtin ? "_" : "") +
                                          m.getLongMethodName());
        d_lw.println("procedure(" + stubName + "), pointer :: " + procptr_name);

        if(m.isStatic()) {
          String bindc_name = Fortran.getSymbolName(id) + "_getSEPV";
          d_lw.println("type(c_ptr) :: c_sepv_ptr");

          // only once:(for IBM Fortran)
          if (!d_bindc_interfaces_written.contains(bindc_name)) {
            d_bindc_interfaces_written.add(bindc_name);

            d_bindc_decls.printUnformatted("#ifdef FORTRAN_BINDC_PRIVATE\n");    
            d_bindc_decls.writeCommentLine("visibility hack for IBM Fortran");
            d_bindc_decls.println("private :: get_sepv_c");
            d_bindc_decls.printUnformatted("#endif\n");
            d_bindc_decls.println("interface");
            d_bindc_decls.tab();
            d_bindc_decls.println("type(c_ptr) function get_sepv_c() bind(c, name=\"" +
                                  bindc_name + "\")");
            d_bindc_decls.println("use, intrinsic :: iso_c_binding");
            d_bindc_decls.println("end function get_sepv_c");
            d_bindc_decls.backTab();
            d_bindc_decls.println("end interface");
          }
        }
      }
    }
    
    // declare array indices
    Fortran.declareIndices(indexMap, d_lw, d_context);
    d_lw.println();

    if(with_bindc) {
      for(Iterator I = extendedArgs.iterator(); I.hasNext(); ) {
        printBindCProxyInit((Argument) I.next());
      }
    }
    else {
      d_lw.println("external " + stubName);
    }

    //emit rarray index expressions
    Fortran.printIndexExprs(indexMap, null, "size", d_lw, d_context);

    List args = convertRarray ?
      extendedArgs : StubSource.extendArgs(id, m, d_context, true);
    Map rhs_map = null;

    // For F90, structs are "flattened" before calling the stub in order to
    // avoid dependencies on the memory layout of various compilers
    if(Fortran.isFortran90(d_context) && m.hasStruct()) {
      rhs_map = new HashMap();
      m = m.spawnF90Wrapper(args, rhs_map, true);
      args = m.getArgumentList();
    }

    // Fortran 2003 requires some conversion between native Fortran types
    // and their Bind(C) equivalents
    if(with_bindc) {
      rhs_map = new HashMap();
      for(Iterator I = args.iterator(); I.hasNext(); ) {
        Argument a = (Argument) I.next();
        String name = a.getFormalName();
        rhs_map.put(name,
                    Fortran.getFortran2BindCExpr(name,
                                                 a.getType(),
                                                 a.getMode()));
      }
    }
    
    if(gen_direct_dispatch) {
      if(m.isStatic()) {
        d_lw.println("if( .not. associated(d_sepv)) then");
        d_lw.tab();
        d_lw.println("c_sepv_ptr = get_sepv_c()");
        d_lw.println("call c_f_pointer(c_sepv_ptr, d_sepv)");
        d_lw.backTab();
        d_lw.println("endif");
      }
      
      String epv_expr = m.isStatic() ? "d_sepv" : "self%d_epv";
      d_lw.println("call c_f_procpointer(" + epv_expr + "%" +
                   IOR.getVectorEntry((is_contract_builtin ? "_" : "") +
                                      m.getLongMethodName())
                   + ", " + procptr_name + ")");
      if(is_c_function)
        d_lw.print(rhs_map.get(Fortran.s_return) + " = ");
      else
        d_lw.print("call ");
      d_lw.print(procptr_name);
    }
    else {
      d_lw.print("call " + stubName);
    }
    
    printArgs(args, rhs_map, true, is_c_function /*skip retval*/);

    // For Bind(C) bindings, we need proxy variables for some data types
    if(with_bindc) {
      //make sure we touch out arguments only if no exception has been
      //thrown
      boolean check_exception =
        !m.getThrows().isEmpty() && hasOutgoingValue(m);
      if(check_exception) {
        d_lw.println("if(c_associated(bindc_" + Fortran.s_exception + ")) then");
        d_lw.tab();
        printBindCProxyReturn(findException(extendedArgs));
        d_lw.backTab();
        d_lw.println("else");
        d_lw.tab();
      }
      
      for(Iterator I = extendedArgs.iterator(); I.hasNext(); )
        printBindCProxyReturn((Argument) I.next());

      if(check_exception) {
        d_lw.backTab();
        d_lw.println("endif");
      }
    }
    
    d_lw.backTab();
    d_lw.println();
    d_lw.println("end " + proc_type + " " + methodName);
  }

  /**
   * Write the FORTRAN 90 module for a subroutine that corresponds to a 
   * sidl class/interface method. This writes method signatures and declares 
   * the types of the arguments.
   * 
   * @param m     the method whose module method is to be written.
   * @param id    the name of the class that owns this method.
   * @param isInterface true iff the method is in an interface (as opposed
   *                    to a class.
   * @exception   gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   */
  private void extendAndGenerate(Method   m,
                                 SymbolID id,
                                 NameMangler non,
                                 NameMangler fort)
    throws CodeGenerationException
  {
    d_lw.println();
    d_lw.println();

    if (hasTwoStubs(m)) {
      writeMethodStub(m, id, "_1s", true, false, non, fort);
      d_lw.println();
      writeMethodStub(m, id, "_2s", false, false, non, fort);
    } else {
      writeMethodStub(m, id, "_s", false, false, non, fort);
    }
  }
  
  /**
   * Generate the extended argument list for a method and then generate the
   * stub code for the method.
   * 
   * @param m           the method to be generated.
   * @param id          the name of the class/interface who owns the method.
   * @param isInterface is the method owned by a class or interface.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *  a catch all exception for problems in the code generation phase.
   */
  private void extendAndGenerateSuper(Class cls, 
                                      Method   m,
                                      CodeSplicer              splicer,
                                      NameMangler mang)
    throws CodeGenerationException
  {
    SymbolID id = cls.getSymbolID();
    List extendedArgs = StubSource.extendArgs(id, m, d_context, false);
    final String methodName = Fortran.getMethodSuperImplName(id,m,mang,
                                                             d_context);
                             //"super_"+ m.getLongMethodName() + "_s";
    final String stubName = Fortran.getMethodSuperName(id, m, mang, d_context);
    d_lw.print("recursive subroutine " + methodName);
    printArgs(extendedArgs, null);

    ImplSource.useStatementsForSupers(m,id,d_lw, splicer, d_context);

    d_lw.tab();
    d_lw.println("implicit none");

    Iterator i = extendedArgs.iterator();
    while (i.hasNext()) {
      Argument a = (Argument)i.next();
      d_lw.writeCommentLine(a.getArgumentString());
      d_lw.println(Fortran.getArgumentString(a.getType(), d_context,false) + " " +
                   s_intent_spec[a.getMode()] + " :: " 
                   + a.getFormalName());
    }
    d_lw.println();
    d_lw.println("external " + stubName);
    d_lw.print("call " + stubName);
    printArgs(extendedArgs, null);

    d_lw.backTab();
    d_lw.println();
    d_lw.println("end subroutine " + methodName);
    d_lw.println();
  }




  /**
   * Generate a create method object.
   */
  private Method create(SymbolID id)
  {
    Method m = new Method(d_context);
    m.setMethodName("new", "Local");
    m.setDefinitionModifier(Method.STATIC);
    String[] s = new String[1];
    s[0] = "Create an instance of class " + id.getFullName();
    m.setComment(new Comment(s));
    m.setReturnType(new Type(Type.VOID));
    m.addArgument(new Argument(Argument.OUT, new Type(id, d_context), "self"));
    m.addImplicitThrows(IOR.getRuntimeException(d_context));
    return m;
  }

  public static Set extendedReferences(Extendable ext, Context context)
    throws CodeGenerationException
  {
    Set s = StubSource.extendedReferences(ext, context);
    s.add(ext.getSymbolID());
    Iterator i = ext.getParents(true).iterator();
    while (i.hasNext()) {
      s.add(((Symbol)i.next()).getSymbolID());
    }
    return s;
  }


  /**
   * Add the implicit stub methods to the list of those that must
   * be included.
   *
   * @param ext the class whose module file is being written.
   */
  private Collection extendMethods(Extendable ext)
  {
    Collection allMethods = ext.getMethods(true);
    final SymbolID id = ext.getSymbolID();
    ArrayList  extendedMethods = new ArrayList(allMethods.size()+1);
    if (!ext.isAbstract()) {
      extendedMethods.add(create(id));
      extendedMethods.add(Fortran.createRemoteMethod(ext, d_context, ( Fortran.isFortran90(d_context) || Fortran.hasBindC(d_context) )));
    }
    extendedMethods.add(Fortran.connectRemoteMethod(ext, d_context, Fortran.isFortran90(d_context) || Fortran.hasBindC(d_context)));
    
    extendedMethods.addAll(allMethods);
    return extendedMethods;
  }


  /**
   * Write #include for all the abbreviation files for
   * referenced symbols.
   */
  private void writeIncludes(Extendable ext)
    throws CodeGenerationException
  {
    writeIncludes(ext, d_lw, d_context);
  }

  /**
   * A public function exposing the functionality of writeIncludes
   */
  public static void writeIncludes(Extendable ext,
                                   LanguageWriterForFortran d_lw,
                                   Context d_context)
    throws CodeGenerationException
  {
    Set s = extendedReferences(ext, d_context);
    Set seen = new HashSet();
    s.add(ext.getSymbolID());
    Iterator i = s.iterator();
    try {
      d_lw.pushLineBreak(false);
      while (i.hasNext()) {
        SymbolID id = (SymbolID)i.next();
        seen.add(id);
        d_lw.generateInclude(Fortran.getStubNameFile(id));
      }

      //basic array types
      if(Fortran.hasBindC(d_context)) {
        s = basicArrayReferences(ext, d_context);
        for(Iterator I = s.iterator(); I.hasNext();) {
          SymbolID array = (SymbolID)I.next();
          if(!seen.contains(array))
            d_lw.generateInclude(Fortran.getStubNameFile(array));
        }
      }
      
      if (!d_context.getConfig().getSkipRMI()) {
        d_lw.generateInclude("sidl_rmi_Call_fAbbrev.h");
        d_lw.generateInclude("sidl_rmi_Return_fAbbrev.h");
        d_lw.generateInclude("sidl_rmi_Ticket_fAbbrev.h");
      }
      
    }
    finally {
      d_lw.popLineBreak();
    }
  }
  
  private void includeType(SymbolID id)
  {
    d_lw.generateUse(Fortran.getTypeModule(id, d_context), new TreeMap());
  }

  private static void checkType(Type t, Set result, Context d_context)
  {
    SymbolID id = Fortran.getArraySymbolID(t, d_context);
    if (id != null) 
      result.add(id);
  }

  private static Set basicArrayReferences(Extendable ext, Context d_context)
  {
    HashSet result = new HashSet();
    Iterator i = ext.getMethods(true).iterator();
    while (i.hasNext()) {
      Method m = (Method)i.next();
      checkType(m.getReturnType(), result, d_context);
      Iterator j = m.getArgumentList().iterator();
      while (j.hasNext()) {
        checkType(((Argument)j.next()).getType(), result, d_context);
      }
    }
    return result;
  }

  private void includeTypes(Extendable ext) 
    throws CodeGenerationException
  {
    final SymbolID id = ext.getSymbolID();
    final SymbolTable table = d_context.getSymbolTable();
    Set s = extendedReferences(ext, d_context);
    d_lw.generateUse("sidl", new TreeMap());
    s.add(id);

    Iterator i = s.iterator();
    while (i.hasNext()) {
      SymbolID current = (SymbolID)i.next();
      Symbol sym = table.lookupSymbol(current);
      if ((sym instanceof Extendable) || (sym instanceof Enumeration)) {
        includeType(current);
      }
    }
    if (!d_context.getConfig().getSkipRMI()) {
      includeType(table.lookupSymbol("sidl.rmi.Call").getSymbolID());
      includeType(table.lookupSymbol("sidl.rmi.Return").getSymbolID());
      includeType(table.lookupSymbol("sidl.rmi.Ticket").getSymbolID());
    }

    TreeMap emptyMap = new TreeMap();
    s = basicArrayReferences(ext, d_context);
    i = s.iterator();
    while (i.hasNext()) {
      SymbolID basic_array = (SymbolID)i.next();
      d_lw.generateUse(Fortran.getArrayModule(basic_array, d_context), emptyMap);
    }
  }

  /**
   * Generate a CAST function in the module.
   */
  private void generateCast(SymbolID oldType,
                            SymbolID newType,
                            int      num)
    throws CodeGenerationException
  {
    SymbolID exceptType = Utilities.lookupSymbol(d_context, BabelConfiguration.getBaseExceptionType());
    Method cast_m = Fortran.createCast(d_context, newType);
    String castMethod = Fortran.getMethodStubName
      (newType, cast_m, d_context);
    d_lw.beginBlockComment(false);
    d_lw.println("Static function to cast from " + oldType.getFullName());
    d_lw.println("to " + newType.getFullName() + ".");
    d_lw.endBlockComment(false);
    d_lw.println("subroutine cast_" + num + "(oldType, newType, exception)");
    d_lw.tab();
    d_lw.println("implicit none");
    d_lw.println("type(" + Fortran.getTypeName(oldType) + 
                 "), intent(in) :: oldType");
    d_lw.println("type(" + Fortran.getTypeName(newType) + 
                 "), intent(out) :: newType");
    d_lw.println("type(" + Fortran.getTypeName(exceptType) + 
                 "), intent(out) :: exception");

    if(!Fortran.hasBindC(d_context)) {
      d_lw.println("external " + castMethod);
      d_lw.println();
      d_lw.println("call " + castMethod + "(oldType, newType, exception)");
    }
    else {
      generateBindCInterface(newType, cast_m, castMethod, false, false, false); 
      d_lw.println("call " + castMethod +
                   "(oldType%d_ior, newType%d_ior, exception%d_ior)");
      d_lw.println("call cache_epv(newType)");
      d_lw.println("call cache_epv(exception)");
    }
    
    d_lw.backTab();
    d_lw.println("end subroutine cast_" + num);
    d_lw.println();
  }

  private void generateCastMethods(Extendable ext,
                                   Collection parents)
    throws CodeGenerationException
  {
    /*
     * Experience has shown that we cannot count on parents to
     * have a consistent ordering from run to run. Therefore we
     * must sort parents.
     */
    final Object[] parentArray = parents.toArray();
    final SymbolID  id = ext.getSymbolID();
    int num = 0;
    Arrays.sort(parentArray);
    for(int i = 0; i < parentArray.length; ++i) {
      SymbolID parentID = ((Extendable)parentArray[i]).getSymbolID();
      generateCast(id, parentID, num++);
      generateCast(parentID, id, num++);
    }
  }

  private void writeCastList(int numCasts)
  {
    d_lw.print("cast_0");
    for(int i = 1; i < numCasts; ++i) {
      if (((numCasts % s_CASTS_PER_LINE) == 0) 
          && (i < (numCasts - 1))) {
        d_lw.println(", &");
        if (numCasts == 7) {
          d_lw.tab();
        }
      }
      else {
        d_lw.print(", ");
      }
      d_lw.print("cast_" + i);
    }
  }

  private void writeCastInterface(int numCasts)
  {
    if (numCasts > 0) {
      d_lw.print("private :: ");
      writeCastList(numCasts);
      d_lw.println();
      if (numCasts > 7) {
        d_lw.backTab();
      }
      d_lw.println("interface cast");
      d_lw.tab();
      d_lw.print("module procedure ");
      writeCastList(numCasts);
      d_lw.println();
      if (numCasts > 7) {
        d_lw.backTab();
      }
      d_lw.backTab();
      d_lw.println("end interface");
    }
  }

  private void generateNullSubroutines(Extendable ext)
  {
    boolean with_bindc = Fortran.hasBindC(d_context);
    String null_str = "0";
    String type_str = "type(" + Fortran.getTypeName(ext.getSymbolID()) + ")";

    if(with_bindc) {
      null_str = "c_null_ptr";
      //there's not derived type for interfaces anyway
      type_str = ext.isInterface() ? "type" : "class";
      type_str += "(" + Fortran.getTypeName(ext.getSymbolID()) + ")";
    }
    d_lw.println("logical function is_null_s(ext)");
    d_lw.tab();
    d_lw.println(type_str + ", intent(in) :: ext");
    if(with_bindc)
      d_lw.println("is_null_s = .not. c_associated(ext%d_ior)");
    else
      d_lw.println("is_null_s = (ext%d_ior .eq. " + null_str + ")");
    d_lw.backTab();
    d_lw.println("end function is_null_s");
    d_lw.println();
    d_lw.println("logical function not_null_s(ext)");
    d_lw.tab();
    d_lw.println(type_str + ", intent(in) :: ext");
    if(with_bindc)
      d_lw.println("not_null_s = c_associated(ext%d_ior)");
    else
      d_lw.println("not_null_s = (ext%d_ior .ne. " + null_str + ")");
    d_lw.backTab();
    d_lw.println("end function not_null_s");
    d_lw.println();
    d_lw.println("subroutine set_null_s(ext)");
    d_lw.tab();
    d_lw.println(type_str + ", intent(inout) :: ext");
    d_lw.println("ext%d_ior = " + null_str);
    if(with_bindc) {
      d_lw.println("ext%d_epv => null()");
      if(ext.isInterface()) d_lw.println("ext%d_object = " + null_str);
    }
    d_lw.backTab();
    d_lw.println("end subroutine set_null_s");
    d_lw.println();
  }
  //write the interface for generated (non-user) methods
  private void writeMethodInterface(SymbolID id, String methodName, 
                                    NameMangler non, NameMangler fort,
                                    boolean extendIfcName,
                                    boolean hasTwoNames)
  {
    String sIfcName;
    if (hasTwoNames)
      d_lw.println("private :: " + methodName + "_1s, " + methodName + "_2s");
    else
      d_lw.println("private :: " + methodName + "_s");

    if (extendIfcName) {
        try {
          sIfcName = Fortran.getExtendedMethodName(id, methodName, non, fort, d_context);
        } catch (gov.llnl.babel.backend.CodeGenerationException cge) {
          sIfcName = Fortran.getExtendedMethodName(id, methodName);
        }
    } else {
      sIfcName = methodName;
    }
    d_lw.println("interface " + sIfcName);
    d_lw.tab();
    if (hasTwoNames)
      d_lw.println("module procedure " + methodName + "_1s, " + methodName + "_2s");
    else
      d_lw.println("module procedure " + methodName + "_s");

    d_lw.backTab();
    d_lw.println("end interface");
    d_lw.println();
  }

  private void writeMethodInterface(Extendable ext, Method m, 
                                    NameMangler non, NameMangler fort,
                                    boolean extendIfcName)
  {
    SymbolID id  = ext.getSymbolID();
    String sName = m.getCorrectMethodName();
    String sIfcName;
    Collection overloadedMethods = ext.getOverloadedMethodsByName(sName);

    if(sName.compareTo("new") == 0) {
      overloadedMethods.add(create(ext.getSymbolID()));
      overloadedMethods.add(Fortran.createRemoteMethod(ext, d_context, (Fortran.isFortran90(d_context) || Fortran.hasBindC(d_context) )));
    }

    //TODO: Is this irrelevent now?  I think so.
    //This is only empty in one wierd case for the method new.
    if(overloadedMethods.isEmpty()) {
      writeMethodInterface(id, sName, non, fort, extendIfcName, hasTwoStubs(m));
    } else {
      //This simply lists all the long method names as private (it only
      //looks complex)
      d_lw.print("private :: "); 
      for(Iterator i = overloadedMethods.iterator(); i.hasNext();) {
        Method meth = (Method) i.next();
        //Rarrays actually map to 2 different methods, one takes rarrays,
        //the other sidl arrays.  We do this in F90 and C++
        if(hasTwoStubs(meth)) {
          d_lw.print(meth.getLongMethodName() + "_1s, " + 
                     meth.getLongMethodName() + "_2s");
        } else {
          d_lw.print(meth.getLongMethodName() + "_s");
        }
        if(i.hasNext()) d_lw.print(", ");
      }
      d_lw.println();
      
      //This maps the method short name to all the long names.
      if (extendIfcName) {
        try {
          sIfcName = Fortran.getExtendedMethodName(id, sName, non, fort, d_context);
        } catch (gov.llnl.babel.backend.CodeGenerationException cge) {
          sIfcName = Fortran.getExtendedMethodName(id, sName);
        }
      } else {
        sIfcName = sName;
      }

      d_lw.println("interface " + sIfcName);
      d_lw.tab();
      d_lw.print("module procedure ");
      for(Iterator i = overloadedMethods.iterator(); i.hasNext();) {
        Method meth = (Method) i.next();

        if (hasTwoStubs(meth)) {
          d_lw.print(meth.getLongMethodName() + "_1s, ");
          d_lw.print(meth.getLongMethodName() + "_2s");
        } else {
          d_lw.print(meth.getLongMethodName() + "_s");
        }
        if(i.hasNext()) d_lw.print(", ");
      }
      d_lw.println();
      d_lw.backTab();
      d_lw.println("end interface");
      d_lw.println();
      d_lw.println();
    } 
  }


  private void writeMethodInterface(Extendable ext, Method m,
                                    NameMangler non, NameMangler fort)
  {
    writeMethodInterface(ext, m, non, fort, false);
  }


  private void writeMethodInterfaces(Extendable ext, NameMangler non,
                                     NameMangler fort, boolean doLocal) 
    throws CodeGenerationException 
  {
    Collection methods = doLocal ? ext.getMethods(false) : extendMethods(ext);

    Iterator i = methods.iterator();
    while (i.hasNext()) {
      Method m = (Method)i.next();
      if(!interfacesWritten.contains(m.getShortMethodName())) {
        writeMethodInterface(ext, m, non, fort);
        interfacesWritten.add(m.getShortMethodName());
      }
      if (( m.getCommunicationModifier() == Method.NONBLOCKING ) &&
          !d_context.getConfig().getSkipRMI()) {
        Method send = m.spawnNonblockingSend();
        if(!interfacesWritten.contains(send.getCorrectMethodName())) {
          writeMethodInterface(ext, send, non, fort); 
          interfacesWritten.add(send.getCorrectMethodName());
        }
        Method recv = m.spawnNonblockingRecv();
        if(!interfacesWritten.contains(recv.getCorrectMethodName())) {
          writeMethodInterface(ext, recv, non, fort); 
          interfacesWritten.add(recv.getCorrectMethodName());
        }
      }
    }
  }
  

  private void writeBuiltinMethodInterfaces(Extendable ext, NameMangler non,
                                            NameMangler fort)
    throws CodeGenerationException 
  {
    SymbolID id = ext.getSymbolID();
    Method bi;
    if (!d_context.getConfig().getSkipRMI()) {
      bi = IOR.getBuiltinMethod(IOR.EXEC,id,d_context,false);
      bi.setMethodName("exec");
      d_lw.println();
      writeMethodInterface(ext, bi, non, fort);
      interfacesWritten.add(bi.getShortMethodName());
    
      bi = IOR.getBuiltinMethod(IOR.GETURL,id, d_context,false);
      bi.setMethodName("getURL");
      d_lw.println();
      writeMethodInterface(ext, bi, non, fort);
      interfacesWritten.add(bi.getShortMethodName());
    
      bi = IOR.getBuiltinMethod(IOR.ISREMOTE,id,d_context,false);
      bi.setMethodName("isRemote");
      d_lw.println();
      writeMethodInterface(ext, bi, non, fort);
      interfacesWritten.add(bi.getShortMethodName());

      bi = IOR.getBuiltinMethod(IOR.ISREMOTE,id,d_context,false);
      bi.setMethodName("isLocal");
      d_lw.println();
      writeMethodInterface(ext, bi, non, fort);
      interfacesWritten.add(bi.getShortMethodName());
    }

    bi = IOR.getBuiltinMethod(IOR.HOOKS, id, d_context, false);
    bi.setMethodName("set_hooks");
    d_lw.println();
    writeMethodInterface(ext, bi, non, fort);
    interfacesWritten.add(bi.getShortMethodName());

    boolean genContractBuiltins = IOR.generateContractBuiltins(ext, d_context);
    if (genContractBuiltins) {
      bi = IOR.getBuiltinMethod(IOR.CONTRACTS, id, d_context, false);
      bi.setMethodName("set_contracts");
      d_lw.println();
      writeMethodInterface(ext, bi, non, fort);
      interfacesWritten.add(bi.getShortMethodName());

      bi = IOR.getBuiltinMethod(IOR.DUMP_STATS, id, d_context, false);
      bi.setMethodName("dump_stats");
      d_lw.println();
      writeMethodInterface(ext, bi, non, fort);
      interfacesWritten.add(bi.getShortMethodName());
    }

    if(ext.hasStaticMethod(true)) {
      bi = IOR.getBuiltinMethod(IOR.HOOKS, id, d_context, true);
      bi.setMethodName("set_hooks_static");
      d_lw.println();
      writeMethodInterface(ext, bi, non, fort, true);
      interfacesWritten.add(bi.getShortMethodName());

      if (genContractBuiltins) {
        bi = IOR.getBuiltinMethod(IOR.CONTRACTS, id, d_context, true);
        bi.setMethodName("set_contracts_static");
        d_lw.println();
        writeMethodInterface(ext, bi, non, fort, true);
        interfacesWritten.add(bi.getShortMethodName());
  
        bi = IOR.getBuiltinMethod(IOR.DUMP_STATS, id, d_context, true);
        bi.setMethodName("dump_stats_static");
        d_lw.println();
        writeMethodInterface(ext, bi, non, fort, true);
        interfacesWritten.add(bi.getShortMethodName());
      }
    }

  }

  private void generateBuiltinMethods(Extendable ext, NameMangler non,
                                      NameMangler fort) 
    throws CodeGenerationException
  {
    SymbolID id = ext.getSymbolID();
    Method bi;
    if (!d_context.getConfig().getSkipRMI()) {
      bi = IOR.getBuiltinMethod(IOR.EXEC,id,d_context,false);
      bi.setMethodName("exec");
      d_lw.println();
      writeMethodStub(bi, id, "_s", false, true, non, fort);

    
      bi = IOR.getBuiltinMethod(IOR.GETURL,id,d_context,false);
      bi.setMethodName("getURL");
      d_lw.println();
      writeMethodStub(bi, id, "_s", false, true, non, fort);
    

      bi = IOR.getBuiltinMethod(IOR.ISREMOTE,id,d_context,false);
      bi.setMethodName("isRemote");
      d_lw.println();
      writeMethodStub(bi, id, "_s", false, true, non, fort);

      bi = IOR.getBuiltinMethod(IOR.ISREMOTE,id,d_context,false);
      bi.setMethodName("isLocal");
      d_lw.println();
      writeMethodStub(bi, id, "_s", false, true, non, fort);
    }

    bi = IOR.getBuiltinMethod(IOR.HOOKS, id, d_context, false);
    bi.setMethodName("set_hooks");
    d_lw.println();
    writeMethodStub(bi, id, "_s", false, true, non, fort);

    boolean genContractBuiltins = IOR.generateContractBuiltins(ext, d_context);
    if (genContractBuiltins) {
      bi = IOR.getBuiltinMethod(IOR.CONTRACTS, id, d_context, false);
      bi.setMethodName("set_contracts");
      d_lw.println();
      writeMethodStub(bi, id, "_s", false, true, non, fort);
  
      bi = IOR.getBuiltinMethod(IOR.DUMP_STATS, id, d_context, false);
      bi.setMethodName("dump_stats");
      d_lw.println();
      writeMethodStub(bi, id, "_s", false, true, non, fort);
    }

    if(ext.hasStaticMethod(true)) {
      bi = IOR.getBuiltinMethod(IOR.HOOKS, id, d_context, true);
      bi.setMethodName("set_hooks_static");
      d_lw.println();
      writeMethodStub(bi, id, "_s", false, true, non, fort);

      if (genContractBuiltins) {
        bi = IOR.getBuiltinMethod(IOR.CONTRACTS, id, d_context, true);
        bi.setMethodName("set_contracts_static");
        d_lw.println();
        writeMethodStub(bi, id, "_s", false, true, non, fort);
  
        bi = IOR.getBuiltinMethod(IOR.DUMP_STATS, id, d_context, true);
        bi.setMethodName("dump_stats_static");
        d_lw.println();
        writeMethodStub(bi, id, "_s", false, true, non, fort);
      }
    }
  }


private void generateMethodDispatches(Extendable ext, boolean doLocal,
                                      NameMangler non,
                                      NameMangler fort)
    throws CodeGenerationException
  {
    Collection methods = doLocal ? ext.getMethods(false) : extendMethods(ext);
    final SymbolID id  = ext.getSymbolID();

    Iterator i = methods.iterator();
    while (i.hasNext()) {
      Method m = (Method)i.next();
      if( (!dispatchesWritten.contains(m.getLongMethodName()))
        && (!m.getLongMethodName().equals("new"))) {
        extendAndGenerate(m, id, non, fort);

        dispatchesWritten.add(m.getLongMethodName());
        if (( m.getCommunicationModifier() == Method.NONBLOCKING ) &&
            !d_context.getConfig().getSkipRMI()) {
          Method send = m.spawnNonblockingSend();
          extendAndGenerate(send, id, non, fort);
          dispatchesWritten.add(send.getLongMethodName());
          Method recv = m.spawnNonblockingRecv();
          extendAndGenerate(recv, id, non, fort);
          dispatchesWritten.add(recv.getLongMethodName());
        }
      }
    }
  }

  private void generateGenericModule(Extendable ext)
    throws CodeGenerationException
  {
    NameMangler non  = null;
    NameMangler fort = null;
    try {
      non  = new NonMangler();
      fort = new FortranMangler(AbbrevHeader.getMaxName(d_context),
                                AbbrevHeader.getMaxUnmangled(d_context));
    } catch (java.security.NoSuchAlgorithmException nsae) {
      non  = null;
      fort = null;
    }
    
    Collection parents = ext.getParents(true);
    final SymbolID id = ext.getSymbolID();
    writeCastInterface(parents.size()*2);
    d_lw.println();
    /* Write local method interfaces */
    writeMethodInterfaces(ext, non, fort, true); 
    /* Write parent/extended interfaces */
    writeMethodInterfaces(ext, non, fort, false); 
    writeBuiltinMethodInterfaces(ext, non, fort);

    writeMethodInterface(id, "not_null", non, fort, false, false);
    writeMethodInterface(id, "is_null", non, fort, false, false);
    writeMethodInterface(id, "set_null", non, fort, false, false);

    //For Bind(C) bindings, we need a global reference to the static EPV
    boolean with_sepv = Fortran.hasBindC(d_context) && ext.hasStaticMethod(true);
    if(with_sepv) {
      d_lw.println("type(" + Fortran.getBindCEPVTypeName(id, true) +
                   "), pointer, private :: d_sepv => null ()");
      d_lw.println();
    }
    
    d_lw.println();
    if (Fortran.hasBindC(d_context)) {
      d_lw.generateInclude( Fortran.getModBindCdeclFile(d_id) );
      d_lw.println();
    }
    d_lw.backTab();
    d_lw.println("contains");
    d_lw.println();

    d_lw.tab();

    generateMethodDispatches(ext, true, non, fort);  /* Generate local methods first */
    generateMethodDispatches(ext, false, non, fort); /* Generate parent/extended methods */
    d_lw.println();
    generateCastMethods(ext, parents);
    generateBuiltinMethods(ext, non, fort);
    generateNullSubroutines(ext);
  }

  private void generateBindCInterface(SymbolID id,
                                      Method m,
                                      String name,
                                      boolean gen_direct_dispatch,
                                      boolean is_function,
                                      boolean do_alternate)
    throws CodeGenerationException
  {
    // only once:(for IBM Fortran)
    if (d_bindc_interfaces_written.contains(name)) return;
    else d_bindc_interfaces_written.add(name);

    String proc_type = is_function ? "function" : "subroutine";
    List extendedArgs = StubSource.extendArgs(id, m, d_context, !do_alternate, is_function);
    String bindc_name;

    if(do_alternate) {
      extendedArgs = StubSource.convertRarrayToArray(extendedArgs, d_context);
      bindc_name = Fortran.getAltBindCName(id, m, d_context);
    } else {
      bindc_name = Fortran.getBindCName(id, m, d_context);
    }

    d_bindc_decls.printUnformatted("#ifdef FORTRAN_BINDC_PRIVATE\n");    
    d_bindc_decls.writeCommentLine("visibility hack for IBM Fortran");
    d_bindc_decls.println("private :: "+name);
    d_bindc_decls.printUnformatted("#endif\n");
    d_bindc_decls.println("interface");
    d_bindc_decls.tab();

    if(is_function) d_bindc_decls.print(Fortran.getBindCReturnType(m.getReturnType())  + " ");
    d_bindc_decls.print(proc_type + " " + name);
    printArgs(d_bindc_decls, extendedArgs, null, false, false);
    d_bindc_decls.print(" bind(c");
    if(!gen_direct_dispatch)
      d_bindc_decls.println(", name=\"" + bindc_name + "\")");
    else
      d_bindc_decls.println(")");
    
    d_bindc_decls.tab();
    d_bindc_decls.println("use, intrinsic :: iso_c_binding");
    for(Iterator I = extendedArgs.iterator(); I.hasNext();)
      declareArgument(d_bindc_decls, (Argument)I.next(), false, true, do_alternate);

    d_bindc_decls.backTab();
    d_bindc_decls.println("end " + proc_type + " " + name);
    d_bindc_decls.backTab();
    d_bindc_decls.println("end interface");
  }
  
  /**
   * Generate the FORTRAN 90 module file for a sidl class.  
   * 
   * @param ext    the sidl class whose module is to be written.
   * @exception    gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   */
  public void generateCode(Extendable ext)
    throws CodeGenerationException
  {
    final String name = Fortran.getModule(ext.getSymbolID(), d_context);
    interfacesWritten = new HashSet();
    dispatchesWritten = new HashSet();
    d_lw.println();
    d_lw.writeComment(ext, false);
    d_lw.println();
    writeIncludes(ext);
    d_lw.println();
    d_lw.println("module " + name);
    d_lw.println();
    d_lw.tab();

    includeTypes(ext);
    d_lw.println();

    generateGenericModule(ext);
        
    d_lw.backTab();
    d_lw.println();
    d_lw.println("end module " + name);
  }

  /**
   * Generate the FORTRAN 90 module file for a sidl enumerated type.  
   * 
   * @param enm    the sidl enumeration whose module is to be written.
   * @exception    gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   */
  public void generateCode(Enumeration enm)
    throws CodeGenerationException
  {
    final SymbolID id = enm.getSymbolID();
    final String name = Fortran.getModule(id, d_context);
    d_lw.println();
    d_lw.println("module " + name);
    d_lw.writeComment(enm, false);
    d_lw.tab();
    d_lw.println();
    d_lw.generateUse("sidl", new TreeMap());

    Iterator  i = enm.getEnumerators().iterator();
    while (i.hasNext()){
      String sym = (String)i.next();
      Comment cmt = enm.getEnumeratorComment(sym);
      d_lw.writeComment(cmt, true);
      d_lw.print(Fortran.getArgumentString(new Type(id, d_context), d_context,false));
      d_lw.print(", parameter :: ");
      d_lw.print(sym);
      d_lw.println(" = " + enm.getEnumeratorValue(sym));
      if (cmt != null) {
        d_lw.println();
      }
    }
    
    d_lw.backTab();
    d_lw.println("end module " + name);
    d_bindc_decls.close();
  }


  /**
   * Generate the FORTRAN 90 module file for a sidl class.  
   *
   * Note:  This is the assumed entry point; otherwise, the test for
   * the version of the language should be repeated.
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
    if (Fortran.isFortran90(context) || Fortran.hasBindC(context)) {
      if (sym instanceof Extendable) {
        ModuleSource modFile = new ModuleSource(sym.getSymbolID(), writer, context);
        modFile.generateCode((Extendable)sym);
      }
      else if (sym instanceof Enumeration) {
        ModuleSource modFile = new ModuleSource(sym.getSymbolID(), writer, context);
        modFile.generateCode((Enumeration)sym);
      }
    } else {  // Assuming this means F77
      throw new CodeGenerationException("Generation of module files only " +
                                        "supported for FORTRAN 90");
    }
  }

  private static boolean hasTwoStubs(Method m)
  {
    if (m.hasRarray()) {
      if (Method.NONBLOCKING_RECV != m.getCommunicationModifier()) {
        return true;
      }
      /* For NONBLOCKING_RECV two stubs are only required if one array
         argument is inout
      */
      Iterator i = m.getArgumentList().iterator();
      while (i.hasNext()) {
        Argument a = (Argument)i.next();
        if ((Argument.IN != a.getMode()) &&
            a.getType().isRarray()) return true;
      }
    }
    return false;
  }
}
