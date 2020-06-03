//
// File:        fSkelSource.java
// Package:     gov.llnl.babel.backend.fortran
// Revision:    @(#) $Revision: 6676 $
// Description: Generate to allow the IOR to call FORTRAN implementations
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
import gov.llnl.babel.backend.fortran.ImplSource;
import gov.llnl.babel.backend.fortran.StubSource;
import gov.llnl.babel.backend.mangler.FortranMangler;
import gov.llnl.babel.backend.mangler.NameMangler;
import gov.llnl.babel.backend.mangler.NonMangler;
import gov.llnl.babel.symbols.Argument;
import gov.llnl.babel.backend.writers.LanguageWriterForFortran;
import gov.llnl.babel.symbols.Class;
import gov.llnl.babel.symbols.Struct;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.Type;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

/**
 * The purpose of this class is to generate the Fortran skeleton code to 
 * support sidl objects implemented in FORTRAN. The skeleton code makes the link
 * between the independent object representation (IOR) and the FORTRAN 
 * implementation of a sidl class.
 * 
 * The skeleton must map datatypes in C to datatypes in FORTRAN. It must
 * must also provide C functions to populate the static and object entry
 * point vectors for the IOR.
 */
public class fSkelSource {
  private LanguageWriterForFortran d_lw;
  private NameMangler              d_mang;
  private Context                  d_context;

  /**
   * Create an object to generate the skeleton code in Fortran for a FORTRAN 
   * object.
   *
   * @param writer   the skeleton code is written to this device.
   * @exception java.security.NoSuchAlgorithmException
   *   problem with the name mangler.
   */
  public fSkelSource(LanguageWriterForFortran writer, Context context) 
    throws NoSuchAlgorithmException 
  {
    d_lw = writer;
    d_context = context;
    if(Fortran.needsAbbrev(context)) {
      d_mang =  new FortranMangler(AbbrevHeader.getMaxName(context), 
                                   AbbrevHeader.getMaxUnmangled(context));
    } else {
      d_mang  = new NonMangler();
    }
  }

  /**
   * This writes out a procedure declaration for Fortran 90. 
   * @param m        the method whose declaration is to be written.
   * @param exprMap  a Map of index expressions.
   * @param id       the id of the class that owns this method.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   */
  private void writeF90Decl(Method m, Map exprMap, SymbolID id)
    throws CodeGenerationException
  {
    final String name = Fortran.getMethodImplName(id, m, d_mang, d_context);
    final List args = m.getArgumentListWithIndices();
    
    d_lw.println("recursive subroutine " + name + "( &");
    d_lw.tab();
    for(Iterator I=args.iterator(); I.hasNext();) {
      Argument arg = (Argument)I.next();
      d_lw.print(arg.getFormalName());
      if(I.hasNext())
        d_lw.println(", &");
    }
    d_lw.println(")\n");

    //generate use statements
    d_lw.println("use, intrinsic :: iso_c_binding");
    ImplSource.addUseForReferences(m, id, d_lw, d_context, true); 
    d_lw.generateUse(Fortran.getImplModule(id), new TreeMap());

    //generate declarations
    d_lw.println("implicit none");
    for(Iterator I=args.iterator(); I.hasNext();) {
      Argument arg = (Argument)I.next();

      d_lw.writeCommentLine(arg.getArgumentString());
      // Iterator it = exprMap.entrySet().iterator();
      // while (it.hasNext()) {
      //   Map.Entry pair = (Map.Entry)it.next();
      //   d_lw.println("! "+pair.getKey() + " = " + pair.getValue());
      // }
      String prefix = "";
      if (arg.getType().isRarray() && arg.hasAttribute("F90_flattened_struct_arg")) {
        // FIXME: Better include a basename in the exprMap
        prefix = (String)exprMap.get(arg.getFormalName());
        prefix = prefix.substring(prefix.indexOf('*')+1, prefix.indexOf(')')) + "_";
      }
      d_lw.println(ImplSource.getArgumentDeclaration(arg, prefix, d_context));
    }
  }

  /**
   * Writes assignments of the form
   * name<lhs_sep>field = name<rhs_sep>field
   * for each field in turn. Nested structs are handled recursively.
   */
  private void writeStructCopies(Struct s,
                                 String lhs_sep,
                                 String rhs_sep,
                                 String lhs,
                                 String rhs)
    throws CodeGenerationException
  {
    for(Iterator I=s.getItems().iterator(); I.hasNext(); ) {
      Struct.Item item = (Struct.Item) I.next();
      String l = lhs + lhs_sep + item.getName();
      String r = rhs + rhs_sep + item.getName();
      if(item.getType().isStruct()) {
        writeStructCopies((Struct)d_context.getSymbolTable().
                          lookupSymbol(item.getType().getSymbolID()),
                          lhs_sep, rhs_sep, l, r);
      }
      else {
        switch(item.getType().getDetailedType()) {
        case Type.STRING:
          d_lw.println("! FIXME " + l + " = " + r);
          break;
        case Type.ARRAY:
          if (item.getType().isRarray()) {
            // Rarrays are stored as pointers to the data of a temporary
            // (sidl-array generated by SkelSource.java) in the user-visible
            // part of the F90 implementation. We therefore do the pointer
            // assignment only for the flattening direction.
            if (lhs_sep == "%")
              d_lw.println(l + " => " + r);
            break;
          }
          // else fall through
        default:
          d_lw.println(l + " = " + r);
        }
      }
    }
  }


  private void generateSetEPV(Extendable ext, Collection methods, boolean do_static)
  {
    if(do_static && !ext.hasStaticMethod(true)) return;

    NameMangler non  = null;
    NameMangler fort = null;
    try {
      non  = new NonMangler();
      fort = new FortranMangler(AbbrevHeader.getMaxName(d_context),
                                AbbrevHeader.getMaxUnmangled(d_context));
      SymbolID id = ext.getSymbolID();
      String bindc_name = (do_static ?
                           IOR.getSetSEPVName(id) : IOR.getSetEPVName(id)) +
        "_bindc";
      String f2003_name = bindc_name;
      String epv_t = Fortran.getBindCEPVTypeName(id, do_static);
      String pre_epv_t = Fortran.getBindCPreEPVTypeName(id, do_static);
      String post_epv_t = Fortran.getBindCPostEPVTypeName(id, do_static);
      boolean with_hooks = IOR.generateHookMethods(ext, d_context);
      
      d_lw.println("subroutine " + f2003_name +
                   (!with_hooks ? "(c_epv)" : "(c_epv, c_pre_epv, c_post_epv)") +
                   " bind(c, name=\"" + bindc_name + "\")");
      d_lw.tab();
      d_lw.println("type(c_ptr), value :: c_epv");
      d_lw.println("type(" + epv_t + "), pointer  :: f_epv => null()");
      if(with_hooks) {
        d_lw.println("type(c_ptr), value  :: c_pre_epv");
        d_lw.println("type(" + pre_epv_t + "), pointer  :: f_pre_epv => null()");
        d_lw.println("type(c_ptr), value  :: c_post_epv");
        d_lw.println("type(" + post_epv_t + "), pointer  :: f_post_epv => null()");
      }

      d_lw.println();
      d_lw.println("call c_f_pointer(c_epv, f_epv)");
      if(with_hooks) {
        d_lw.println("call c_f_pointer(c_pre_epv, f_pre_epv)");
        d_lw.println("call c_f_pointer(c_post_epv, f_post_epv)");
      }
    
      d_lw.println();
      for(Iterator I = methods.iterator(); I.hasNext(); ) {
        Method m = (Method) I.next();
        if(Fortran.needsCSkel(m, d_context)) continue;
        if(do_static == m.isStatic()) {
          switch (m.getDefinitionModifier()) {
          case Method.FINAL:
          case Method.NORMAL:
          case Method.STATIC:
            d_lw.println("f_epv%" + IOR.getVectorEntry(m.getLongMethodName()) +
                         " = c_funloc(" +
                         Fortran.getMethodFSkelName(id, m, non, fort, d_context) + ")");
            break;
          case Method.ABSTRACT:
            d_lw.println("f_epv%" + IOR.getVectorEntry(m.getLongMethodName()) +
                         " = c_null_funptr");
            break;
          }
          
          if(with_hooks && !m.isBuiltIn()) {
            Method hook = m.spawnPreHook();
            d_lw.println("f_pre_epv%" + IOR.getVectorEntry(hook.getLongMethodName()) +
                         " = c_funloc(" +
                         Fortran.getMethodFSkelName(id, hook, non, fort, d_context) + ")");
            hook = m.spawnPostHook();
            d_lw.println("f_post_epv%" + IOR.getVectorEntry(hook.getLongMethodName()) +
                         " = c_funloc(" +
                         Fortran.getMethodFSkelName(id, hook, non, fort, d_context) + ")");
          }
        }
      }
    
      d_lw.backTab();
      d_lw.println("end subroutine " + f2003_name);
      d_lw.println();
    } catch (Exception e)  { }
  }
  
  private void generateImplCast(Extendable ext)
  {
    SymbolID id = ext.getSymbolID();
    String bindc_name = Fortran.getSymbolName(id) + "_getData";
    String f2003_name = Fortran.getSymbolName(id) + "_impl_cast";
    d_lw.println("subroutine " + f2003_name + "(c_ior, impl)");
    d_lw.tab();
    d_lw.println("implicit none");
    d_lw.println("type(c_ptr), intent(in) :: c_ior");
    d_lw.println("type(" + Fortran.getImplTypeName(id) +
                 "), pointer, intent(out) :: impl");
    d_lw.println("type(c_ptr) :: cptr");
    d_lw.println("interface");
    d_lw.tab();
    d_lw.println("type(c_ptr) function get_data_ptr(ior) bind(c, name=\"" +
                 bindc_name + "\")");
    d_lw.tab();
    d_lw.println("use iso_c_binding");
    d_lw.println("type(c_ptr), value :: ior");
    d_lw.backTab();
    d_lw.println("end function get_data_ptr");
    d_lw.backTab();
    d_lw.println("end interface");
    d_lw.println("if(c_associated(c_ior)) then");
    d_lw.tab();
    d_lw.println("cptr = get_data_ptr(c_ior)");
    d_lw.println("if(c_associated(cptr)) then");
    d_lw.tab();
    d_lw.println("call c_f_pointer(cptr, impl)");
    d_lw.backTab();
    d_lw.println("endif");
    d_lw.backTab();
    d_lw.println("endif");
    d_lw.backTab();
    d_lw.println("end subroutine " + f2003_name);
    d_lw.println();
  }

  /**
   * Prints a declaration for a proxy variables necessary for some data types.
   */
  private void printBindCProxyDecl(Argument arg) {
    String name = arg.getFormalName();
    Type t = arg.getType();
    try {
      switch(t.getDetailedType()) {
      case Type.INTERFACE:
      case Type.CLASS:
        if(arg.getFormalName().equals(Fortran.s_self))
          d_lw.print("type(" + Fortran.getImplTypeName(t.getSymbolID()) + "), pointer");
        else
          d_lw.print(Fortran.getReturnString(t, d_context, false));
        d_lw.println(" :: proxy_" + name);
        break;
      case Type.STRING:
        d_lw.println("character(len=sidl_f03_str_minsize) :: " +
                     "proxy_" + name);
        break;
      case Type.STRUCT:
        if (arg.getFormalName().equals(Fortran.s_return)) {
          d_lw.writeCommentLine("Additional proxy for struct by-vale return values");
          d_lw.println(Fortran.getArgumentString(t, d_context, false) + 
                       ", target :: retproxy_" + name);
        }
        d_lw.println(Fortran.getArgumentString(t, d_context, false) + 
                     ", pointer :: proxy_" + name);
        break;
      case Type.ARRAY:
        d_lw.println("type(c_ptr) :: " + name + "_tmp = c_null_ptr");
        if(t.isRarray()) t = Fortran.convertRarrayToArray(t, d_context);
        //fallthrough intended!
      case Type.BOOLEAN:
      case Type.OPAQUE:
      case Type.CHAR:
        d_lw.println(Fortran.getArgumentString(t, d_context, false) + 
                     " :: proxy_" + name);
        break;
      }
    }
    catch(CodeGenerationException e) { } 
  }
  
  /**
   * Emits an init expression for proxy variables necessary for some data types.
   */
  private void printBindCProxyInit(Argument arg) {
    int mode = arg.getMode();
    String name = arg.getFormalName();
    Type t = arg.getType();
    if(mode == Argument.IN || mode == Argument.INOUT ||
       arg.getType().isStruct() && !arg.getFormalName().equals(Fortran.s_return) 
       // Structs as out parameters come preallocated, need to set the pointer
       ) {
      Fortran.printBindC2Fortran(t, name, "proxy_" + name, mode,
                                 false /* no default copying */,
                                 true  /* ensure array ordering */,
                                 d_lw);
    }
  }
  
  /**
   * Returns a String that translates among the usual Fortran representation
   * and Bind(C) equivalents. 
   */
  private static String getFortran2BindCExpr(Argument arg) {
    String expr = arg.getFormalName();
    Type t = arg.getType();
    switch(t.getDetailedType()) {
    case Type.ARRAY:
    case Type.INTERFACE:
    case Type.CLASS:
    case Type.BOOLEAN:
    case Type.STRUCT:
      if (t.isStruct() && expr.equals(Fortran.s_return)) { 
        return "retproxy_" + expr;
      }
    case Type.OPAQUE:
    case Type.CHAR:
    case Type.STRING:
      return "proxy_" + expr;
    default:
      return expr;
    }
  }

  /**
   * Emits code that copies proxy variables to out arguments.
   */
  private void printBindCProxyReturn(Argument arg) {
    int mode = arg.getMode();
    Type t = arg.getType();
    String name = arg.getFormalName();
    if (t.isStruct() && name.equals(Fortran.s_return)) {
      // Kif, we have a conundrum:
      // Structs are returned by value if they are the return value
      d_lw.println("call c_f_pointer(" + name + ", proxy_" + name + ")");
      d_lw.println("proxy_" + name + " = retproxy_" + name);
      return;
    }
    if ((mode == Argument.OUT || mode == Argument.INOUT) && !arg.getType().isRarray())
      Fortran.printFortran2BindC(t, "proxy_" + name, name, 
                                 mode, false /* no copying */,
                                 true  /* ensure array ordering */,
                                 d_lw);
    else return;
  }
  
  /**
   * Write the FORTRAN fSkel that implements a Bind(C) interface for Fortran
   * implementations.
   * 
   * @param m     the method whose implementation template is to be
   *              written.
   * @param id    the id of the class that owns this method.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   */
  private void generateBindCSkel(Method m, SymbolID id)
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

    String name = Fortran.getMethodFSkelName(id, m, non, fort, d_context);
    String impl_name = Fortran.getMethodImplName(id, m, d_mang, d_context);
    boolean is_function = Fortran.isFortranFunction(m, d_context);
    boolean needs_c_skel = Fortran.needsCSkel(m, d_context);
    boolean is_bindc_function = !needs_c_skel &&
      m.getReturnType().getDetailedType() != Type.VOID;
    String proc_type = is_bindc_function ? "function" : "subroutine";
    List extendedArgs = StubSource.extendArgs(id, m, d_context, false, false);

    //we need to do some extra work for constructor and destructor
    boolean is_ctor = m.isBuiltIn() && m.getLongMethodName().equals("_ctor");
    boolean is_dtor = m.isBuiltIn() && m.getLongMethodName().equals("_dtor");

    //some methods need to be externally visible
    String bindc_name = null;
    if(needs_c_skel)
      bindc_name = Fortran.getBindCSkelName(id, m, d_context);
    
    d_lw.print(proc_type + " " + name + "(");
    for(Iterator I = extendedArgs.iterator(); I.hasNext();) {
      Argument a = (Argument)I.next();
      if(is_bindc_function && a.getFormalName().equals(Fortran.s_return))
        continue;
      d_lw.print(a.getFormalName());
      if (I.hasNext()) d_lw.print(", ");
    }
    
    d_lw.print(")");
    if(is_bindc_function) d_lw.print(" result(" + Fortran.s_return + ")");
    d_lw.print(" bind(c");
    if(bindc_name != null) d_lw.print(", name=\"" + bindc_name + "\"");
    d_lw.println(")");
    d_lw.tab();
    
    //declare arguments
    for(Iterator I = extendedArgs.iterator(); I.hasNext();) {
      Argument a = (Argument) I.next();
      if(is_bindc_function && a.getFormalName().equals(Fortran.s_return))
         continue;
      Type argType = a.getType();
      d_lw.writeCommentLine(a.getArgumentString());
      d_lw.print(Fortran.getBindCType(argType, a.getMode(), true /*skel*/));
      d_lw.println(" :: " + a.getFormalName());
    }

    if(is_bindc_function) {
      String f_init_expr = "";
      d_lw.writeCommentLine(" function result");
      d_lw.println(Fortran.getBindCReturnType(m.getReturnType())  +
                   " :: " + Fortran.s_return + f_init_expr);
    }
    
    //skeleton body
    for(Iterator I = extendedArgs.iterator(); I.hasNext(); )
      printBindCProxyDecl((Argument) I.next());

    //this is for rarray indices
    Map rarray_info = m.getRarrayInfo();
    Fortran.declareIndices(rarray_info, d_lw, d_context);
    
    if(is_ctor) {
      String setter_name = Fortran.getSymbolName(id) + "_setData";
      d_lw.println("interface");
      d_lw.tab();
      d_lw.println("subroutine set_data_ptr(ior, ptr) bind(c, name=\"" +
                   setter_name + "\")");
      d_lw.tab();
      d_lw.println("use iso_c_binding");
      d_lw.println("type(c_ptr), value :: ior");
      d_lw.println("type(c_ptr), value :: ptr");
      d_lw.backTab();
      d_lw.println("end subroutine set_data_ptr");
      d_lw.backTab();
      d_lw.println("end interface");
      d_lw.println();
      d_lw.writeComment("allocation for user-defined data type", false);
      d_lw.println("allocate(proxy_self)");
      d_lw.println("proxy_self%d_ior = self");
      d_lw.println("call cache_epv(proxy_self)");
      d_lw.println("call set_data_ptr(self, c_loc(proxy_self))");
    }
    
    d_lw.println();
    for(Iterator I = extendedArgs.iterator(); I.hasNext(); )
      printBindCProxyInit((Argument) I.next());

    if(m.hasRarray())
      extendedArgs = StubSource.extendArgs(id, m, d_context, true, false);
    
    Map rhs_map = new HashMap();
    for(Iterator I = extendedArgs.iterator(); I.hasNext(); ) {
      Argument a = (Argument) I.next();
      rhs_map.put(a.getFormalName(), getFortran2BindCExpr(a));
    }

    Fortran.printIndexExprs(rarray_info, rhs_map, "length", d_lw, d_context);
    
    d_lw.println();
    if(is_function)
      d_lw.print((String)rhs_map.get(Fortran.s_return) + " = ");
    else
      d_lw.print("call ");
    d_lw.print(impl_name + "(");
    
    for(Iterator I = extendedArgs.iterator(); I.hasNext();) {
      Argument a = (Argument)I.next();
      if(is_function && a.getFormalName().equals(Fortran.s_return))
        continue;
      d_lw.print((String)rhs_map.get(a.getFormalName()));
      if(a.getType().isRarray()) d_lw.print("%d_data");
      if(I.hasNext()) d_lw.print(", ");
    }
    
    d_lw.println(")");
    d_lw.println();

    //(r)arrays require some cleanup as calls to sidl__array_ensure either
    //clone the array or increment the reference count.
    //* For IN arrays, we have to call deleteRef on the (potentially cloned)
    //  result array
    //* For OUT arrays, we return the new array reference and we have to
    //  call deleteRef on the orginal array
    //* For INOUT arrays, we have to do both
    for(Iterator I = extendedArgs.iterator(); I.hasNext(); ) {
      Argument arg = (Argument) I.next();
      Type type = arg.getType();
      int mode = arg.getMode();
      if(type.isArray() && (mode == Argument.IN)) {
        if(type.isRarray() || type.hasArrayOrderSpec()) {
          d_lw.println("call sidl__array_deleteRef(" +arg.getFormalName() + ")");
        }
      }
    }
    
    for(Iterator I = extendedArgs.iterator(); I.hasNext(); )
      printBindCProxyReturn((Argument) I.next());

    //this is the symmetric case from above
    for(Iterator I = extendedArgs.iterator(); I.hasNext(); ) {
      Argument arg = (Argument) I.next();
      Type type = arg.getType();
      int mode = arg.getMode();
      if(type.isArray() && (mode == Argument.OUT || mode == Argument.INOUT)) {
        if(type.isRarray() || type.hasArrayOrderSpec()) {
          d_lw.println("call sidl__array_deleteRef(" + arg.getFormalName() + "_tmp)");
        }
      }
    }
    
    if(is_dtor) {
      d_lw.println();
      d_lw.writeComment("deallocation for user-defined data type", false);
      d_lw.println("deallocate(proxy_self)");
    }
    
    d_lw.backTab();
    d_lw.println("end " + proc_type + " " + name);
    d_lw.println();
  }
  
  
  /**
   * Write the FORTRAN fSkel that corresponds to the given sidl method.
   * 
   * @param m     the method whose implementation template is to be
   *              written.
   * @param id    the id of the class that owns this method.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   */
  private void generateF90StructSkel(Method m, SymbolID id)
    throws CodeGenerationException
  {
    final String short_name = m.getShortMethodName();
    List extendedArgs = StubSource.extendArgs(id, m, d_context, true, false);
    Map exprMap = new HashMap();
    Method f90_skel = m.spawnF90Wrapper(extendedArgs, exprMap, false);
    
    //write declaration
    d_lw.writeComment("Flattening/unflattening for method " + short_name, false);
    writeF90Decl(f90_skel, exprMap, id);

    //pass through the argument list once collecting in and out mode structs and
    //declaring temporary variables for in/inout arguments
    List ins  = new LinkedList(), outs = new LinkedList();
    d_lw.println();
    for(Iterator I=extendedArgs.iterator(); I.hasNext();) {
      Argument arg = (Argument) I.next();
      int mode = arg.getMode();
      if(arg.getType().isStruct()) {
        d_lw.writeCommentLine("temporary variable for" + arg.getArgumentString());
        d_lw.println(ImplSource.getArgumentDeclaration(arg, "", d_context));
        
        if(mode == Argument.OUT || mode == Argument.INOUT) outs.add(arg);
        if(mode == Argument.IN || mode == Argument.INOUT)  ins.add(arg);
      }
    }
    d_lw.println();

    //copy the flattened arguments to the temporary instances of the derived
    //type for in/inout mode structs
    for(Iterator I=ins.iterator(); I.hasNext();) {
      Argument arg = (Argument)I.next();
      Struct s = (Struct)d_context.getSymbolTable().
        lookupSymbol(arg.getType().getSymbolID());
      d_lw.writeCommentLine("unflattening for argument" + arg.getArgumentString());
      writeStructCopies(s, "%", "_", arg.getFormalName(), arg.getFormalName()); 
    }
    d_lw.println();

    //emit the call to the actual F90 implementation
    d_lw.println("call " +
                 Fortran.getMethodImplName(id, m, d_mang, d_context) +
                 "( &");
    d_lw.tab();
    for(Iterator I = extendedArgs.iterator(); I.hasNext(); ) {
      d_lw.print(((Argument)I.next()).getFormalName());
      if(I.hasNext()) d_lw.println(", & ");
    }
    d_lw.println(")");
    d_lw.backTab();
    d_lw.println();

    //again, flatten out/inout mode structs so they can be passed back
    //properly to the C skeleton
    for(Iterator I=outs.iterator(); I.hasNext();) {
      Argument arg = (Argument)I.next();
      Struct s = (Struct)d_context.getSymbolTable().
        lookupSymbol(arg.getType().getSymbolID());
      d_lw.writeCommentLine("flattening for argument" + arg.getArgumentString());
      writeStructCopies(s, "_", "%", arg.getFormalName(), arg.getFormalName()); 
    }

    d_lw.println();
    d_lw.backTab();
    d_lw.println("end subroutine " +
                 Fortran.getMethodImplName(id, f90_skel, d_mang, d_context));
    d_lw.println();
  }

  private void generateF03Super(Class cls)
    throws CodeGenerationException
  {
    if(cls.hasOverwrittenMethods()) {
      SymbolID id = cls.getSymbolID();
      SymbolID parent_id = cls.getParentClass().getSymbolID();
      String parent_type = Fortran.getTypeName(parent_id);
      String impl_type = Fortran.getImplTypeName(id);
      String fqn_super = Fortran.getSymbolName(id) + "_super";
      String c_get_super = IOR.getSymbolName(id) + "__super_epv";

      d_lw.println("subroutine " + fqn_super + "(self, retval)");
      d_lw.tab();
      d_lw.generateUse(Fortran.getTypeModule(parent_id, d_context), new HashMap());
      d_lw.generateUse(Fortran.getImplModule(id), new HashMap());
      d_lw.println("use, intrinsic :: iso_c_binding");
      d_lw.println("implicit none");
      d_lw.println("type(" + impl_type + "), intent(in) :: self");
      d_lw.println("type(" + parent_type + "), intent(out) :: retval");
      d_lw.println("type(c_ptr) :: cptr = c_null_ptr");
      //super_epv() interface
      d_lw.println("interface");
      d_lw.tab();
      d_lw.println("type(c_ptr) function " + c_get_super + "() bind(c, name=\"" +
                   c_get_super + "\")");
      d_lw.println("use, intrinsic :: iso_c_binding");
      d_lw.println("end function " + c_get_super);
      d_lw.backTab();
      d_lw.println("end interface");
      //don't do the regular cache_epv here as we need the epv to point to
      //the parent's entry structure instead
      d_lw.println("retval%d_ior = self%d_ior");
      d_lw.println("retval%d_epv => null()");
      d_lw.println("if(c_associated(retval%d_ior)) then");
      d_lw.tab();
      d_lw.println("cptr = " + c_get_super + "()");
      d_lw.println("if(c_associated(cptr)) then");
      d_lw.tab();
      d_lw.println("call c_f_pointer(cptr, retval%d_epv)");
      d_lw.backTab();
      d_lw.println("endif");
      d_lw.backTab();
      d_lw.println("endif");
      
      d_lw.backTab();
      d_lw.println("end subroutine " + fqn_super);
      d_lw.println();
    }
  }

  private void generateF03Wrap(Class cls)
    throws CodeGenerationException
  {
    SymbolID id = cls.getSymbolID();
    String impl_type = Fortran.getImplTypeName(id);
    String stub_type = Fortran.getTypeName(id);
    String fqn_wrap = Fortran.getSymbolName(id) + "_wrap";
    String setter_name = Fortran.getSymbolName(id) + "_setData";

    //don't do anything for abstract classes
    if(cls.isAbstract()) return;
    
    d_lw.println("subroutine " + fqn_wrap + "(obj, self, exception)");
    d_lw.tab();
    d_lw.generateUse(Fortran.getImplModule(id), new HashMap());
    d_lw.println("use, intrinsic :: iso_c_binding");
    d_lw.println("implicit none");
    d_lw.println("type(" + impl_type + "), target :: obj");
    d_lw.println("type(" + stub_type + ") :: self");
    d_lw.println("type(sidl_BaseInterface_t) :: exception");

    d_lw.println("interface");
    d_lw.tab();
    d_lw.println("subroutine set_data_ptr(ior, ptr) bind(c, name=\"" +
                 setter_name + "\")");
    d_lw.tab();
    d_lw.println("use iso_c_binding");
    d_lw.println("type(c_ptr), value :: ior");
    d_lw.println("type(c_ptr), value :: ptr");
    d_lw.backTab();
    d_lw.println("end subroutine set_data_ptr");
    d_lw.backTab();
    d_lw.println("end interface");
    d_lw.println();
    d_lw.writeComment("wrapping of user-defined data type", false);
    d_lw.println("call new(self, exception)");
    d_lw.println("call set_data_ptr(self%d_ior, c_loc(obj))");
    d_lw.println("obj%d_ior = self%d_ior");
    d_lw.println("call cache_epv(obj)");
    d_lw.println("call ctor_impl(obj, exception)");
    d_lw.backTab();
    d_lw.println("end subroutine " + fqn_wrap);
    d_lw.println();
  }
  
  /**
   * This is a convenience routine to create a skeleton file for a class
   * without having to make an instance of <code>SkelSource</code>.  The
   * skeleton file is a C module that is the glue between the IOR and
   * the implementation of a class written in FORTRAN.
   *
   * @param cls    the class for whom a skeleton will be made.
   * @param writer the output device where the skeleton file will be
   *               sent.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   * @exception java.security.NoSuchAlgorithmException
   *   problem with the name mangler.
   */
  public static void generateCode(Extendable               ext,
                                  LanguageWriterForFortran writer,
                                  Context                  context)
    throws CodeGenerationException, NoSuchAlgorithmException
  {
    fSkelSource source = new fSkelSource(writer, context);
    source.generateCode(ext);
  }
  
  /**
   * This method creates a skeleton file for a class. The skeleton file is a
   * Fortran module that (for F90) "flattens" derived types corresponding to
   * structs as we can't assume they are laid out the same as in C
   *
   * @param cls  the class to create.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   */
  public void generateCode(Extendable ext) throws CodeGenerationException {
    final SymbolID id = ext.getSymbolID();
    d_lw.writeBanner(ext, Fortran.getfSkelFile(id, d_context), false,
                     CodeConstants.C_DESC_FSKEL_PREFIX + id.getFullName());
    d_lw.println();
    d_lw.writeComment(ext, false);
    d_lw.println();

    if (Fortran.needsAbbrev(d_context)) {
      ModuleSource.writeIncludes(ext, d_lw, d_context);
      d_lw.println();
    }

    String module_name = null;
    try {
      module_name = d_mang.shortName(ext.getFullName(), "_fSkelf");
    }
    catch(Exception e) { 
      throw new CodeGenerationException(e.getMessage()); 
    }

    Collection methods;
    if(Fortran.isFortran90(d_context)) methods = ext.getMethods(false);
    else if(Fortran.hasBindC(d_context)) 
      methods = ImplSource.extendMethods(ext, d_context);
    else return;

    //super has to be a global function
    if(Fortran.hasBindC(d_context) &&
       ext instanceof gov.llnl.babel.symbols.Class) {
      Class cls = (Class) ext;
      if(cls.hasOverwrittenMethods()) {
        generateF03Super(cls);
      }
      generateF03Wrap(cls);
    }
    
    if (Fortran.hasBindC(d_context)) {
      d_lw.println();
      d_lw.println("module " + module_name);
      d_lw.tab();

      //generate use statements
      d_lw.println("use, intrinsic :: iso_c_binding");
      ImplSource.addUseForReferences(ext, methods, d_lw, d_context, true); 
      d_lw.generateUse(Fortran.getImplModule(id), new TreeMap());

      d_lw.println("implicit none");
      d_lw.backTab();
      d_lw.println("contains");
      d_lw.tab();
    }

    if(Fortran.isFortran90(d_context)) {
      //for each function in turn, check if it has a struct argument and, if
      //so, create a wrapper function that will perform the necessary
      //flattening/unflattening
      for(Iterator I = methods.iterator(); I.hasNext();) {
        Method m = (Method)I.next();
        if (!m.isAbstract() && m.hasStruct()) {
          if(IOR.generateHookMethods(ext, d_context) && !m.isBuiltIn()) {
            generateF90StructSkel(m.spawnPreHook(), id);
            generateF90StructSkel(m, id);
            generateF90StructSkel(m.spawnPostHook(false, false), id);
          } else {
            generateF90StructSkel(m, id);
          }
          d_lw.println();
        }
      }
    }
    else if(Fortran.hasBindC(d_context)) {
      generateImplCast(ext);
      
      for(Iterator I = methods.iterator(); I.hasNext();) {
        Method m = (Method)I.next();
        if (!m.isAbstract()) {
          if(IOR.generateHookMethods(ext, d_context) && !m.isBuiltIn()) {
            generateBindCSkel(m.spawnPreHook(), id);
            generateBindCSkel(m, id);
            generateBindCSkel(m.spawnPostHook(false, false), id);
          } else {
            generateBindCSkel(m, id);
          }
          d_lw.println();
        }
      }

      generateSetEPV(ext, methods, false);
      generateSetEPV(ext, methods, true /*static*/);
      
    }
    if (Fortran.hasBindC(d_context)) {
      d_lw.println();
      d_lw.backTab();
      d_lw.println("end module " + module_name);
    }
  }
}
