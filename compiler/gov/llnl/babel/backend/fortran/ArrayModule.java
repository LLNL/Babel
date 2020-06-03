//
// File:        ArrayModule.java
// Package:     gov.llnl.babel.backend.fortran
// Copyright:   (c) 2003 Lawrence Livermore National Security, LLC
// Revision:    @(#) $Revision: 7505 $
// Date:        $Date: 2012-12-18 09:01:57 -0800 (Tue, 18 Dec 2012) $
// Description: Create a F90 module to provide stubs for arrays
// 
// Copyright (c) 2003, Lawrence Livermore National Security, LLC
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
import gov.llnl.babel.backend.CodeConstants;
import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.fortran.AbbrevHeader;
import gov.llnl.babel.backend.mangler.FortranMangler;
import gov.llnl.babel.backend.mangler.NameMangler;
import gov.llnl.babel.backend.writers.LanguageWriterForFortran;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.Type;
import gov.llnl.babel.symbols.Version;
import gov.llnl.babel.symbols.Argument;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.TreeMap;

/**
 * Create a FORTRAN 90 module to provide client-side bindings for arrays of 
 * classes and interfaces.
 */
public class ArrayModule {
  private final static int GENERIC = -1;
  private final static int BASETYPE = 0;
  private final static int BASEINTERFACE = 1;
  private final static int USERDEFINED = 2;

  static private final int s_maxArray = BabelConfiguration.getMaximumArray();

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


  private NameMangler d_mang;

  private LanguageWriterForFortran d_lw, d_bindc_decls;
  
  private SymbolID d_id;

  private String d_intType;
  
  private boolean d_access;

  private Context d_context;

  private boolean d_borrow;

  private boolean d_is_base_type;

  private boolean d_is_generic_array;

  private int d_type;
  private int d_casttype;

  private static final String[] s_constant_methods = {
    "copy",
    "createCol",
    "createRow",
    "ensure",
    "addRef",
    "deleteRef",
    "dimen",
    "isColumnOrder",
    "isRowOrder",
    "is_null",
    "lower",
    "not_null",
    "set_null",
    "smartCopy",
    "stride",
    "upper",
    "length"
  };

  private static final String[] s_generic_methods = {
    "addRef",
    "deleteRef",
    "dimen",
    "type",
    "isColumnOrder",
    "isRowOrder",
    "is_null",
    "lower",
    "not_null",
    "set_null",
    "smartCopy",
    "stride",
    "upper",
    "length"
  };


  public static final String int_v_t =     "integer(c_int), value";
  public static final String int_r_t =     "integer(c_int)";
  public static final String int_arr_t =   "integer(c_int), dimension(*)";
  public static final String int32_v_t =   "integer(c_int32_t), value";
  public static final String int32_r_t =   "integer(c_int32_t)";
  public static final String int32_arr_t = "integer(c_int32_t), dimension(*)";
  public static final String bool_v_t =    "integer(c_int), value";
  public static final String bool_r_t =    "integer(c_int)";
  public static final String bool_arr_t =  "integer(c_int), dimension(*)";
  public static final String ptr_v_t =     "type(c_ptr), value";
  public static final String ptr_r_t =     "type(c_ptr)";
  public static final String ptr_arr_t =   "type(c_ptr), dimension(*)";
  
  /**
   * The name of the type use to hold values going in/out.
   */
  private String d_implDataType;

  private SymbolID d_implID;

  private String getImplArrayType(int dim)
  {
    return "type(" + Fortran.getArrayName(d_id, dim) + ")";
  }

  private String getPrivateName(String name, int dim)
  {
    if(dim == 0) {
      return name + Fortran.s_privateSuffix;
    }
    return name + dim + Fortran.s_privateSuffix;
  }

  /**
   * The function returns an int that determines what sort of cast functions
   * should be written out.  There are 3 possibilities:
   * if d_is_base_type is true, we need to be able to cast to and from
   * generic arrays.
   * if this is a base interface, we also need to be able to cast to and
   * from generic arrays
   * However, if niether of those are true, we should only be able cast to
   * generic arrays, not from them.
   */
  private int castType() {
    if(d_is_base_type)
      return BASETYPE;
    else if (d_id.getFullName().compareTo(BabelConfiguration.getBaseInterface()) == 0)
      return BASEINTERFACE;
    else
      return USERDEFINED;
  }

  private void initImplID(int type)
    throws CodeGenerationException
  {
    d_access = true;
    d_borrow = true;
    d_is_generic_array = false;
    switch (type) {
    case Type.VOID:
      throw new CodeGenerationException("Cannot create an array for a void");
    case Type.CLASS:
    case Type.SYMBOL:
    case Type.INTERFACE:
      d_access = false;
      d_borrow = false;
      d_is_base_type = false;
      d_implID = d_id;
      break;
    case Type.ENUM:
      d_is_base_type = false;
      d_access = false;
      d_borrow = false;
      d_implID = new SymbolID("sidl.long", new Version());
      break;
    case Type.CHAR:
    case Type.OPAQUE:
    case Type.STRING:
    case Type.BOOLEAN:
      d_access = false;
      d_borrow = false;
      // fall through intended!
    default:
      d_is_base_type = true;
      d_implID = Fortran.getSymbolIDforBasicType(new Type(type));
      break;
    }
  }
    
  /**
   * Create an object to create a FORTRAN 90 module to provide
   * client-side bindings for arrays of classes, interfaces, and
   * enumerated types.
   *
   * @param id    the array module should be created for this
   *              id. This can be a real id or a fake id (for
   *              sidl.double and the like).
   * @param type  this should be a constant from 
   *              {@link gov.llnl.babel.symbols.Type}. It indicates
   *              the type of id.
   * @param lw    here is where the file is created.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *  this means the arguments were incorrect
   */
  public ArrayModule(SymbolID id,
                     int      type,
                     LanguageWriterForFortran lw,
                     Context context)
    throws CodeGenerationException
  {
    try {
      d_id = id;
      d_lw = lw;
      d_type = type;
      d_context = context;

      init_bindc_decls();
      d_mang = new FortranMangler(AbbrevHeader.getMaxName(context),
                                  AbbrevHeader.getMaxUnmangled(context));
      if (type <= Type.STRING) {
        d_implDataType = Fortran.getArgumentString(new Type(type), d_context,false);
      }
      else {
        d_implDataType = Fortran.getArgumentString(new Type(id, d_context), 
                                                   d_context,false);
      }
      d_intType = Fortran.getArgumentString(new Type(Type.INT), d_context,false);
      initImplID(type);
      d_casttype = castType();
    }
    catch (NoSuchAlgorithmException nsae) {
      throw new CodeGenerationException(nsae.getMessage());
    }
  }

    
  /**
   * SPECIAL CONSTRUCTOR for creating the GENERIC ARRAY MODULE.
   * DO NOT CALL FOR ANY OTHER REASON!
   *
   * @param lw    here is where the file is created.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *  this means the arguments were incorrect
   */
  public ArrayModule(LanguageWriterForFortran lw,
                     Context context)
    throws CodeGenerationException
  {
    try {
      d_context = context;
      d_id = new SymbolID("sidl.array", new Version());
      d_lw = lw;
      d_type = -1;
      init_bindc_decls();
      d_mang = new FortranMangler(AbbrevHeader.getMaxName(context),
                                  AbbrevHeader.getMaxUnmangled(context));
      d_implDataType = null;
      d_intType = Fortran.getReturnString(new Type(Type.INT), d_context,false);
      // Instead of calling initImplID(type), we do this directly.
      d_access = false;
      d_borrow = false;
      d_is_base_type = false;
      d_is_generic_array = true;
      d_implID = new SymbolID("sidl", new Version());
      d_casttype = GENERIC;
    }
    catch (NoSuchAlgorithmException nsae) {
      throw new CodeGenerationException(nsae.getMessage());
    }
  }

  private void init_bindc_decls()
    throws CodeGenerationException 
  {
    d_bindc_interfaces_written = new HashSet();
    String nf = Fortran.getArrayBindCdeclFile(d_id);
    PrintWriter nw        = d_context.getFileManager().
      createFile(d_id, d_type, "STUBHDRS", nf);
    d_bindc_decls = new LanguageWriterForFortran(nw, d_context);
    d_bindc_decls.writeCommentLine("-*- F90 -*-");
    d_bindc_decls.writeComment("IBM's F2003 compiler gets confused if we have lots of "+
                               "identical local interface declarations, "+
                               "so we gather them in this include file.", true);
  }

  private String getMethodName(String meth) {
    String array_type = "";

    switch (d_type) {
    case Type.CLASS:
    case Type.SYMBOL:
    case Type.INTERFACE:
      array_type = "sidl_interface";
      break;
    default:
      array_type = Fortran.getSymbolName(d_implID);
      break;
    }
    
    return array_type + "__array_" + meth;
  }
  
  private void writeGenericBindCInterface(String name,
                                          String[] args,
                                          String[] types,
                                          boolean is_function) {
    writeGenericBindCInterface(name, args, types, is_function, null);
  }
  
  
  private void writeGenericBindCInterface(String name,
                                          String[] args,
                                          String[] types,
                                          boolean is_function,
                                          String import_stmt) {
    // only once:(for IBM Fortran)
    if (d_bindc_interfaces_written.contains(name)) return;
    else d_bindc_interfaces_written.add(name);

    String type_str = is_function ? "function" : "subroutine";
    int i0 = is_function ? 1 : 0;    

    d_bindc_decls.printUnformatted("#ifdef FORTRAN_BINDC_PRIVATE\n");    
    d_bindc_decls.writeCommentLine("visibility hack for IBM Fortran");
    d_bindc_decls.println("private :: "+name);
    d_bindc_decls.printUnformatted("#endif\n");

    d_bindc_decls.println("interface");
    d_bindc_decls.tab();
    
    if(is_function) d_bindc_decls.print(types[0] + " ");
    d_bindc_decls.print(type_str + " " + name + "(");
    for(int i = i0; i < args.length; ++i) {
      d_bindc_decls.print(args[i]);
      if(i + 1 < args.length) d_bindc_decls.print(", ");
    }
    d_bindc_decls.println(") bind(c, name=\"" + name + "\")");
    d_bindc_decls.tab();
    d_bindc_decls.println("use, intrinsic :: iso_c_binding");
    d_bindc_decls.println("use :: sidl");
    if(import_stmt != null) d_bindc_decls.println(import_stmt);
    for(int i = i0; i < args.length; ++i)
      d_bindc_decls.println(types[i] + " :: " + args[i]);
    d_bindc_decls.backTab();
    d_bindc_decls.println("end " + type_str + " " + name);
    d_bindc_decls.backTab();
    d_bindc_decls.println("end interface");
  }

  
  private void generateIsNull(int dim)
  {
    d_lw.println("logical function " + getPrivateName("is_null", dim) +
                 "(array)");
    d_lw.tab();
    if(Fortran.hasBindC(d_context)) {
      d_lw.println("use, intrinsic :: iso_c_binding");
      d_lw.println("use :: sidl");
    }
    d_lw.println(getImplArrayType(dim) + ", intent(in) :: array");
    if (Fortran.hasBindC(d_context)) {
      d_lw.println(getPrivateName("is_null", dim) +
                 " = (.not. c_associated(array%d_array))");
    } else {
      d_lw.println(getPrivateName("is_null", dim) +
                 " = (array%d_array .eq. 0)");
    }
    d_lw.backTab();
    d_lw.println("end function " + getPrivateName("is_null", dim));
    d_lw.println();
  }

  private void generateNotNull(int dim)
  {
    d_lw.println("logical function " + getPrivateName("not_null", dim) +
                 "(array)");
    d_lw.tab();
    if (Fortran.hasBindC(d_context)) {
      d_lw.println("use, intrinsic :: iso_c_binding");
      d_lw.println("use :: sidl");
    }
    d_lw.println(getImplArrayType(dim) + ", intent(in) :: array");
    if (Fortran.hasBindC(d_context)) {
      d_lw.println(getPrivateName("not_null", dim) +
                 " = c_associated(array%d_array)");
    } else {
      d_lw.println(getPrivateName("not_null", dim) +
                 " = (array%d_array .ne. 0)");
    }
    d_lw.backTab();
    d_lw.println("end function " + getPrivateName("not_null", dim));
    d_lw.println();
  }

  private void generateSetNull(int dim)
  {
    d_lw.println("subroutine " + getPrivateName("set_null", dim) +
                 "(array)");
    d_lw.tab();
    d_lw.println(getImplArrayType(dim) + ", intent(inout) :: array");
    if (Fortran.hasBindC(d_context)) {
      d_lw.println("array%d_array = c_null_ptr");
    } else {
      d_lw.println("array%d_array = 0");
    }
    if (d_access) {
      d_lw.println("nullify(array%d_data)");
    }
    d_lw.backTab();
    d_lw.println("end subroutine " + getPrivateName("set_null", dim));
    d_lw.println();
  }

  private void generateNullRelated(int dim)
  {
    generateIsNull(dim);
    generateNotNull(dim);
    generateSetNull(dim);
  }

  

  
  private void generateCreate(String type, int dim)
    throws UnsupportedEncodingException
  {
    final String createFunc = "create" + type;
    final String function = d_mang.shortArrayName
      (d_implID.getFullName(), createFunc, "_m");
    d_lw.beginBlockComment(false);
    d_lw.println("The size of lower determines the dimension of the");
    d_lw.println("array.");
    d_lw.endBlockComment(false);
    d_lw.println("subroutine " + getPrivateName(createFunc, dim) +
                 "(lower, upper, array)");
    d_lw.tab();
    d_lw.println(d_intType + ", dimension(" + dim + "), intent(in) :: lower");
    d_lw.println(d_intType +
                 ", dimension(" + dim + "), intent(in) :: upper");
    d_lw.println(getImplArrayType(dim) + ", intent(out) :: array");
    
    if(!Fortran.hasBindC(d_context)) {
      d_lw.println("external " + function);
      d_lw.println("call " + function + "(" + dim + ", lower, upper, array)");
    }
    else {
      d_lw.println("type(c_ptr) :: cptr = c_null_ptr");
      String[] args = {"retval", "dimen", "lower", "upper"};
      String[] types = {ptr_r_t, int32_v_t, int32_arr_t, int32_arr_t};
      writeGenericBindCInterface(getMethodName(createFunc), args, types, true);
      d_lw.println("cptr = " + getMethodName(createFunc) +
                   "(" + dim + ", lower, upper)");
      if(d_access)
        d_lw.println("call cast(cptr, array)");
      else
        d_lw.println("array%d_array = cptr");
        
    }
    d_lw.backTab();
    d_lw.println("end subroutine " + getPrivateName(createFunc, dim));
    d_lw.println();
  }

  private void generateCreate1d()
    throws UnsupportedEncodingException
  {
    final String createFunc = "create1d";
    final String function = d_mang.shortArrayName
      (d_implID.getFullName(), createFunc, "_m");
    d_lw.println("subroutine " + getPrivateName(createFunc, 1) +
                 "(len, array)");
    d_lw.tab();
    d_lw.println(d_intType + ", intent(in) :: len");
    d_lw.println(getImplArrayType(1) + ", intent(out) :: array");

    if(!Fortran.hasBindC(d_context)) {
      d_lw.println("external " + function);
      d_lw.println("call " + function + "(len, array)");
    }
    else {
      d_lw.println("type(c_ptr) :: cptr = c_null_ptr");
      String[] args = {"retval", "len"};
      String[] types = {ptr_r_t, int_v_t};
      writeGenericBindCInterface(getMethodName(createFunc), args, types, true);
      d_lw.println("cptr = " + getMethodName(createFunc) +
                   "(len)");
      if(d_access)
        d_lw.println("call cast(cptr, array)");
      else
        d_lw.println("array%d_array = cptr");      
    }
    d_lw.backTab();
    d_lw.println("end subroutine " + getPrivateName(createFunc, 1));
    d_lw.println();
  }
  
  private void generateCreate2d(String type)
    throws UnsupportedEncodingException
  {
    final String createFunc = "create2d" + type;
    final String function = d_mang.shortArrayName
      (d_implID.getFullName(), createFunc, "_m");
    d_lw.println("subroutine " + getPrivateName(createFunc, 2) +
                 "(m, n, array)");
    d_lw.tab();
    d_lw.println(d_intType + ", intent(in) :: m, n");
    d_lw.println(getImplArrayType(2) + ", intent(out) :: array");
    if(!Fortran.hasBindC(d_context)) {
      d_lw.println("external " + function);
      d_lw.println("call " + function + "(m, n, array)");
    }
    else {
      d_lw.println("type(c_ptr) :: cptr = c_null_ptr");
      String[] args = {"retval", "m", "n"};
      String[] types = {ptr_r_t, int32_v_t, int32_v_t};
      writeGenericBindCInterface(getMethodName(createFunc), args, types, true);
      d_lw.println("cptr = " + getMethodName(createFunc) +
                   "(m, n)");
      if(d_access)
        d_lw.println("call cast(cptr, array)");
      else
        d_lw.println("array%d_array = cptr");      
    }
    d_lw.backTab();
    d_lw.println("end subroutine " + getPrivateName(createFunc, 2));
    d_lw.println();
  }
  

  private void generateCreates(int dim)
    throws UnsupportedEncodingException
  {
    generateCreate("Col", dim);
    generateCreate("Row", dim);
    if (dim == 1) generateCreate1d();
    if (dim == 2) {
      generateCreate2d("Col");
      generateCreate2d("Row");
    }
  }

  private void generateCopy(String func, int dim)
    throws UnsupportedEncodingException
  {
    final String function = d_mang.shortArrayName
      (d_implID.getFullName(), func, "_m");
    d_lw.println("subroutine " + getPrivateName(func, dim) +
                 "(src, dest)");
    d_lw.tab();
    d_lw.println(getImplArrayType(dim) + ", intent(in) :: src");
    d_lw.println(getImplArrayType(dim) + ", intent(in) :: dest");

    if(!Fortran.hasBindC(d_context)) {
      d_lw.println("external " + function);
      d_lw.println("call " + function + "(src, dest)");
    }
    else {
      String[] args = {"src", "dst"};
      String[] types = {ptr_v_t, ptr_v_t};
      writeGenericBindCInterface(getMethodName(func), args, types, false);
      d_lw.println("call " + getMethodName(func) + "(src%d_array, dest%d_array)");
    }
    d_lw.backTab();
    d_lw.println("end subroutine " + getPrivateName(func, dim));
    d_lw.println();
  }

  private void generateSmartCopy(String func, int dim)
    throws UnsupportedEncodingException
  {
    final String function = d_mang.shortArrayName
      (d_implID.getFullName(), func, "_m");
    d_lw.println("subroutine " + getPrivateName(func, dim) +
                 "(src, dest)");
    d_lw.tab();
    d_lw.println(getImplArrayType(dim) + ", intent(in) :: src");
    d_lw.println(getImplArrayType(dim) + ", intent(out) :: dest");
    d_lw.println("integer(sidl_int) :: dim");

    if(!Fortran.hasBindC(d_context)) {
      d_lw.println("external " + function);
      d_lw.println("dim = " + dim);
      d_lw.println("call " + function + "(src, " + dim +", dest)");
    }
    else {
      d_lw.println("type(c_ptr) :: cptr = c_null_ptr");
      String[] args = {"retval", "src"};
      String[] types = {ptr_r_t, ptr_v_t};
      writeGenericBindCInterface(getMethodName(func), args, types, true);
      d_lw.println("dim = " + dim);
      d_lw.println("cptr = " + getMethodName(func) + "(src%d_array)");
      if(d_access)
        d_lw.println("call cast(cptr, dest)");
      else
        d_lw.println("dest%d_array = cptr");      
    }

    d_lw.backTab();
    d_lw.println("end subroutine " + getPrivateName(func, dim));
    d_lw.println();
  }

  private void generateEnsure(int dim)
    throws UnsupportedEncodingException
  {
    final String function = d_mang.shortArrayName
      (d_implID.getFullName(), "ensure", "_m");
    d_lw.println("subroutine " + getPrivateName("ensure", dim) +
                 "(src, dim, ordering, result)");
    d_lw.tab();
    d_lw.println(getImplArrayType(dim) + ", intent(in)  :: src");
    d_lw.println(getImplArrayType(dim) + ", intent(out) :: result");
    d_lw.println(d_intType + ", intent(in) :: dim");
    d_lw.println(d_intType + ", intent(in) :: ordering");
    
    if(!Fortran.hasBindC(d_context)) {
      d_lw.println("external " + function);
      d_lw.println("call " + function + 
                   "(src, " + dim + ", ordering, result)");
    }
    else {
      d_lw.println("type(c_ptr) :: cptr = c_null_ptr");
      if (d_type >= Type.CLASS) {
        // This check is necessary to work around an issue with ifort. The
        // problem is that the interfaces for all basic types are also
        // defined in the 'sidl' module.
        String[] args = {"retval", "src", "dimen", "dst"};
        String[] types = {ptr_r_t, ptr_v_t, int32_v_t, int_v_t};
        writeGenericBindCInterface(getMethodName("ensure"), args, types, true);
      }
      d_lw.println("cptr = " + getMethodName("ensure") + 
                   "(src%d_array, " + dim + ", ordering)");
      if (d_access)
        d_lw.println("call cast(cptr, result)");
      else
        d_lw.println("result%d_array = cptr");      
    }
    d_lw.backTab();
    d_lw.println("end subroutine " + getPrivateName("ensure", dim));
    d_lw.println();
  }

  private void generateSlice(int dim)
    throws UnsupportedEncodingException
  {
    for(int j = 1; j <= dim; ++j) {
      final String function = d_mang.shortArrayName
        (d_implID.getFullName(), "slice", "_m");
      d_lw.println("subroutine " + getPrivateName("slice" + j, dim) +
                   "(src, numElem, srcStart, srcStride, newLower, result)");
      d_lw.tab();
      d_lw.println(getImplArrayType(dim) + ", intent(in)  :: src");
      d_lw.println(d_intType + ", dimension(" + dim + "), intent(in) :: numElem");
      d_lw.println
        (d_intType + 
         ", dimension(" + dim + "), intent(in) :: srcStart, srcStride");
      d_lw.println(getImplArrayType(j) + ", intent(out) :: result");
      d_lw.println(d_intType + ", dimension(:), intent(in) :: newLower");
      if(!Fortran.hasBindC(d_context)) {
        d_lw.println("external " + function);
        d_lw.println("call " + function + 
                     "(src, " + j + ", numElem, srcStart, srcStride, newLower, result)");
      }
      else {
        d_lw.println("type(c_ptr) :: cptr = c_null_ptr");
        String[] args = {"retval", "src", "dimen", "numElem",
                         "srcStart", "srcStride", "newStart"};
        String[] types = {ptr_r_t, ptr_v_t, int32_v_t, int_arr_t,
                          int_arr_t, int_arr_t, int_arr_t};
        writeGenericBindCInterface(getMethodName("slice"), args, types, true);
        d_lw.println("cptr = " + getMethodName("slice") + "(src%d_array, " + j +
                     ", numElem, srcStart, srcStride, newLower)");
        if(d_access)
          d_lw.println("call cast(cptr, result)");
        else
          d_lw.println("result%d_array = cptr");        
      }
      d_lw.backTab();
      d_lw.println("end subroutine " + getPrivateName("slice" + j, dim));
      d_lw.println();
    }
  }

  private void slicePrivateNames()
  {
    int i, j;
    for(i = 1; i <= s_maxArray; ++i) {
      for(j = 1; j <= i; ++j) {
        d_lw.print(getPrivateName("slice" + j, i));
        if ((i < s_maxArray) || (j < s_maxArray)) {
          d_lw.println(", &");
        }
      }
    }
    d_lw.println();
  }
  
  private void writeSliceInterface()
  {
    d_lw.println("private :: &");
    d_lw.tab();
    slicePrivateNames();
    d_lw.backTab();
    d_lw.println();
    d_lw.println("interface slice");
    d_lw.tab();
    d_lw.println("module procedure &");
    d_lw.tab();
    slicePrivateNames();
    d_lw.backTab();
    d_lw.backTab();
    d_lw.println("end interface");
    d_lw.println();
  }

  
  private void generateQuery(String func, int dim, boolean is_int)
    throws UnsupportedEncodingException
  {
    //the returnType is logical or integer, depending on @is_int
    String returnType = is_int ? d_intType : "logical";
    
    final String function = d_mang.shortArrayName
      (d_implID.getFullName(), func, "_m");
    d_lw.println(returnType + " function  " + getPrivateName(func, dim) +
                 "(array)");
    d_lw.tab();
    d_lw.println(getImplArrayType(dim) + ", intent(in) :: array");

    if(!Fortran.hasBindC(d_context)) {
      d_lw.println("external " + function);
      d_lw.println("call " + function + 
                   "(array, " + getPrivateName(func, dim) + ")");
    }
    else {
      String[] args = {"retval", "src"};
      String[] types = {is_int ? int32_r_t : bool_r_t, ptr_v_t};
      writeGenericBindCInterface(getMethodName(func), args, types, true);
      if(!is_int ) {
        d_lw.println(getPrivateName(func, dim) + " = .false.");
        d_lw.println("if(" + getMethodName(func) + "(array%d_array) .ne. 0) then");
        d_lw.tab();
        d_lw.println(getPrivateName(func, dim) + " = .true.");
        d_lw.backTab();
        d_lw.println("end if");
      }
      else {
        d_lw.println(getPrivateName(func, dim) + " = " +
                     getMethodName(func) + "(array%d_array)");
      }
    }
    
    d_lw.backTab();
    d_lw.println("end function " + getPrivateName(func, dim));
    d_lw.println();
  }

  private void generateCasts()
    throws UnsupportedEncodingException
  {
    //Actually, this should never be called on a generic array at all.
    //Double check
    if(d_casttype >= BASETYPE) {
      //Functions that cast TO a generic array
      for(int dim = 1; dim <= s_maxArray; ++dim) {
        generateCastToGeneric(d_type, dim); 
      }      
      d_lw.println();

      //Function to cast ptr to array of objects
      String checkHack = Fortran.getStubFile(d_id);
      if (d_type == Type.CLASS && checkHack.equals("sidl_BaseClass_fStub.c") &&
          Fortran.isFortran90(d_context)) {
        for(int dim = 1; dim <= s_maxArray; ++dim) {
          generateCastFromPtr(d_type, dim);
        }
        d_lw.println();
      }
      
      //USER Defined class array cannot be cast to from a generic array.
      if(d_casttype != USERDEFINED) {
        //Functions that cast FROM a generic array
        for(int dim = 1; dim <= s_maxArray; ++dim) {
          generateCastFromGeneric(d_type, dim);
        }
        d_lw.println();
      }
    }
    
    if(Fortran.hasBindC(d_context)) {
      for(int dim = 1; dim <= s_maxArray; ++dim)
        generateCastFromCPtr(d_type, dim);
      d_lw.println();
    } else if(d_casttype == BASETYPE && Fortran.isFortran90(d_context)) {
      for(int dim = 1; dim <= s_maxArray; ++dim)
        generateCastFromPtr(d_type, dim);
      d_lw.println();
    }
  }

  private void generateCastFromPtr(int type, int dim)
    throws UnsupportedEncodingException
  {
    String typeString = null;
    if(d_casttype == USERDEFINED) {  //Not a base type or sidl.BaseInterface
      typeString = d_id.getFullName().replace('.','_');
    } else {
      typeString = "sidl_"+BabelConfiguration.arrayType(d_type);
    }

    String funcname = d_mang.shortName("castPtrTo" + typeString + dim+"d",Fortran.s_privateSuffix);

    final String function = d_mang.shortName
      (typeString+"__array_cast", "_m");

    //String newTypeName = typeString+"_"+dim+"d";

    d_lw.println("subroutine " + funcname + "(oldType, newType)");
    d_lw.tab();
    d_lw.println("integer(kind=sidl_arrayptr), intent(in) :: oldType");
    d_lw.println("type("+typeString+"_"+dim+"d), intent(out) :: newType");

    d_lw.println("external " + function);
    d_lw.println("call "+function+"(oldType,"+dim+",newType)");

    d_lw.backTab();
    d_lw.println("end subroutine " + funcname);  
    d_lw.println();
  }

  private void generateCastFromCPtr(int type, int dim)
    throws UnsupportedEncodingException
  {
    String array_name = Fortran.getArrayName(d_id, dim);
    String array_type = getImplArrayType(dim);
    String funcname = d_mang.shortName("cast_cptr_to_" + array_name,
                                       Fortran.s_privateSuffix);

    d_lw.println("subroutine " + funcname + "(from, to)");
    d_lw.tab();
    d_lw.println("type(c_ptr), intent(in) :: from");
    d_lw.println(array_type + ", intent(out) :: to");
    
    //If there is no direct access from fortran, we just assign the cptr;
    //otherwhise, we call out to C to do a proper type cast using
    //libchasm. This is the only part of F03 bindings that cannot be done
    //by means of the iso_c_binding module as the types are not C
    //interoperable. We have to fall back to what we do in F90 instead and
    //"guess" how the compiler implements things.
    if(d_access) {
      String function = getMethodName("bindc_cast") +
        CodeConstants.C_F03_BINDC_SUFFIX;
      d_lw.println("integer(c_int32_t) :: dimension = " + dim);
      d_lw.println("external " + function);
      d_lw.println("call " + function + "(from, dimension, to)");
    }
    else if(d_is_generic_array) {
      d_lw.println("to%d_array = from");
    }
    else {
      String[] args = {"retval", "array"};
      String[] types = {ptr_r_t, ptr_v_t};
      writeGenericBindCInterface(getMethodName("cast"), args, types, true);
      d_lw.println("to%d_array = " + getMethodName("cast") + "(from)");
      d_lw.println("if(not_null(to) .and. dimen(to) .ne. " + dim + ") call set_null(to)");
    }
    d_lw.backTab();
    d_lw.println("end subroutine " + funcname);  
    d_lw.println();
  }

  //Doesn't exist for non-base types! (except BaseInterface)
  private void generateCastFromGeneric(int type, int dim)
    throws UnsupportedEncodingException
  {
    String typeString = "sidl_" + BabelConfiguration.arrayType(d_type);
    String funcname = d_mang.shortName("castGenericTo"+typeString+dim+"d",
                                       Fortran.s_privateSuffix);
    String oldTypeName = typeString+"_"+dim+"d";
    String extfunc = d_mang.shortArrayName(typeString, "cast", "_m");  

    d_lw.println("subroutine " + funcname + "(oldType, newType)");
    d_lw.tab();    
    d_lw.println("type(sidl__array), intent(in) :: oldType");
    d_lw.println("type("+oldTypeName+"), intent(out) :: newType");
    


    //There is no BaseInterface array cast, so do that manually
    if(!Fortran.hasBindC(d_context) && d_casttype == BASEINTERFACE) {
      d_lw.println("if(sidl__array_dimen_m(oldType%d_array) .ne. " +
                   dim + " .or. sidl__array_type_m(oldType%d_array) .ne. " +
                   type + ") then");
      d_lw.tab();
      if(Fortran.hasBindC(d_context)) {
        d_lw.println("newType%d_array = c_null_ptr");
      }else {
        d_lw.println("newType%d_array=0");
      }
      d_lw.backTab();
      d_lw.println("else");
      d_lw.tab();
      d_lw.println("newType%d_array = oldType%d_array");
      d_lw.backTab();
      d_lw.println("end if"); 
    } else { //A Base type (int, bool, etc.) has a cast function, use it.
      if(!Fortran.hasBindC(d_context)) {
        d_lw.println("external " + extfunc);
        d_lw.println("call " + extfunc + "(oldType%d_array, "+dim+", newType)");
      }
      else {
        d_lw.println("newType%d_array = oldType%d_array");
        if(d_access) d_lw.println("call cast(oldType%d_array, newType)");
      }
    }

    d_lw.backTab();
    d_lw.println("end subroutine " + funcname);
    d_lw.println();

  }

  private void generateCastToGeneric(int type, int dim)
    throws UnsupportedEncodingException
  {
    String typeString = null;
    if(d_casttype == USERDEFINED) {  //Not a base type or sidl.BaseInterface
      typeString = d_id.getFullName().replace('.','_');
    } else {
      typeString = "sidl_"+BabelConfiguration.arrayType(d_type);
    }
    
    String funcname = d_mang.shortName("cast" + typeString + dim + "dToGeneric",
                                       Fortran.s_privateSuffix);
    String newTypeName = typeString+"_"+dim+"d";

    d_lw.println("subroutine " + funcname + "(oldType, newType)");
    d_lw.tab();
    d_lw.println("type(sidl__array), intent(out) :: newType");
    d_lw.println("type("+newTypeName+"), intent(in) :: oldType");

    //Anything can get cast to a generic array.  That's no big deal.
    d_lw.println("newType%d_array = oldType%d_array");
    
    d_lw.backTab();
    d_lw.println("end subroutine " + funcname);  
    d_lw.println();
  }

  private void generateOrderingTests(int dim)
    throws UnsupportedEncodingException
  {
    generateQuery("isColumnOrder", dim, false);
    generateQuery("isRowOrder", dim, false);
  }

  private void generateIndexedQuery(String func, int dim)
    throws UnsupportedEncodingException
  {
    final String function = d_mang.shortArrayName
      (d_implID.getFullName(), func, "_m");
    d_lw.println(d_intType + " function  " + getPrivateName(func, dim) +
                 "(array, index)");
    d_lw.tab();
    d_lw.println(getImplArrayType(dim) + ", intent(in) :: array");
    d_lw.println(d_intType + ", intent(in) :: index");
    if(!Fortran.hasBindC(d_context)) {
      d_lw.println("external " + function);
      d_lw.println("call " + function + 
                   "(array, index, " + getPrivateName(func, dim) + ")");
    }
    else {
      String[] args = {"retval", "array", "index"};
      String[] types = {int32_r_t, ptr_v_t, int32_v_t};
      writeGenericBindCInterface(getMethodName(func), args, types, true);
      d_lw.println(getPrivateName(func, dim) + " = " +
                   getMethodName(func) + "(array%d_array, index)");
    }
    
    d_lw.backTab();
    d_lw.println("end function " + getPrivateName(func, dim));
    d_lw.println();
  }

  private void generateSizeQueries(int dim)
    throws UnsupportedEncodingException
  {
    generateQuery("dimen", dim, true);
    generateIndexedQuery("stride", dim);
    generateIndexedQuery("lower", dim);
    generateIndexedQuery("upper", dim);
    generateIndexedQuery("length", dim);
  }

  private void generateGenericGet(int dim)
    throws UnsupportedEncodingException
  {
    final String function = d_mang.shortArrayName
      (d_implID.getFullName(), "get", "_m");
    d_lw.println("subroutine " + getPrivateName("getg", dim) +
                 "(array, index, value)");
    d_lw.tab();
    d_lw.println(getImplArrayType(dim) + ", intent(in) :: array");
    d_lw.println(d_intType + ", intent(in), dimension(" + dim + ") :: index");
    d_lw.println(d_implDataType + ", intent(out) :: value");

    if(!Fortran.hasBindC(d_context)) {
      d_lw.println("external " + function);
      d_lw.println("call " + function + 
                   "(array, index, value)");
    }
    else {
      if(d_access) {
        d_lw.print("if(associated(array%d_data)) value = array%d_data(");
        for(int i = 1; i <= dim; ++i) {
          d_lw.print("index(" + i + ")");
          if(i < dim) d_lw.print(", ");
        }
        d_lw.println(")");
      }
      else {
        Type type = new Type(d_type);
        String[] args = {"retval", "array", "indices", };
        String[] types = {Fortran.getBindCReturnType(type), ptr_v_t, int32_arr_t};
        boolean has_proxy = Fortran.hasBindCProxy(type, Argument.OUT);
        //declare a Bind(C) proxy variable
        Fortran.printBindCProxyDecl(type, Argument.OUT, "bindc_value", d_lw, d_context);
        //spell out the interface
        writeGenericBindCInterface(getMethodName("get"), args, types, true);
        //do the actual call
        String lhs = has_proxy ? "bindc_value" : "value";
        d_lw.println(lhs + " = " + getMethodName("get") +"(array%d_array, index)");
        //convert to fortran if necessary
        if(has_proxy) Fortran.printBindC2Fortran(type, "bindc_value", "value",
                                                 Argument.OUT, false, false, d_lw);
      }
    }
    
    d_lw.backTab();
    d_lw.println("end subroutine " + getPrivateName("getg", dim));
    d_lw.println();
  }

  private String[] copyOf(String[] from, int dim) {
    String[] to = new String[dim];
    for(int i=0; i < dim; ++i)
      to[i] = from[i];
    return to;
  }
  
  private void generateGet(int dim)
    throws UnsupportedEncodingException
  {
    final String func = "get";
    int i;
    final String function = d_mang.shortArrayName
      (d_implID.getFullName(), func + dim, "_m");
    d_lw.println("subroutine " + getPrivateName(func, dim) +
                 "(array, &");
    d_lw.tab();
    d_lw.tab();
    for(i = 1; i <= dim; ++i){
      d_lw.println("i" + i + ", &");
    }
    d_lw.println("val)");
    d_lw.backTab();
    d_lw.println(getImplArrayType(dim) + ", intent(in) :: array");
    for(i = 1; i <= dim; ++i) {
      d_lw.println(d_intType + ", intent(in) :: i" + i);
    }
    d_lw.println(d_implDataType + ", intent(out) :: val");

    if(!Fortran.hasBindC(d_context)) {
      d_lw.println("external " + function);
      d_lw.println("call " + function + "(array, &");
      d_lw.tab();
      for(i = 1; i <= dim; ++i) {
        d_lw.println("i" + i + ", &");
      }
      d_lw.println("val)");
      d_lw.backTab();
    }
    else {
      if(d_access) {
        d_lw.print("if(associated(array%d_data)) val = array%d_data(");
        for(i = 1; i <= dim; ++i) {
          d_lw.print("i" + i);
          if(i < dim) d_lw.print(", ");
        }
        d_lw.println(")");
      }
      else {
        Type type = new Type(d_type);
        String[] args = {"retval", "array", "i1", "i2", "i3", "i4", "i5", "i6", "i7"};
        String[] types = {Fortran.getBindCReturnType(type), ptr_v_t, int32_v_t,
                          int32_v_t, int32_v_t, int32_v_t, int32_v_t, int32_v_t, int32_v_t};
        boolean has_proxy = Fortran.hasBindCProxy(type, Argument.OUT);
        //declare a Bind(C) proxy variable
        Fortran.printBindCProxyDecl(type, Argument.OUT, "bindc_val", d_lw, d_context);
        //spell out the interface
        writeGenericBindCInterface(getMethodName(func + dim),
                                   copyOf(args, 2 + dim),
                                   copyOf(types, 2 + dim),
                                   true);
        //do the actual call
        String lhs = has_proxy ? "bindc_val" : "val";
        d_lw.print(lhs + " = " + getMethodName(func + dim) +"(array%d_array, ");
        for(i = 1; i <= dim; ++i) {
          d_lw.print("i" + i);
          if(i < dim) d_lw.print(", ");
        }
        d_lw.println(")");
        //convert to fortran if necessary
        if(has_proxy) Fortran.printBindC2Fortran(type, "bindc_val", "val",
                                                 Argument.OUT, false, false, d_lw);
      }
    }
    
    d_lw.backTab();
    d_lw.println("end subroutine " + getPrivateName(func,dim));
    d_lw.println();
  }

  private void generateGenericSet(int dim)
    throws UnsupportedEncodingException
  {
    final String function = d_mang.shortArrayName
      (d_implID.getFullName(), "set", "_m");
    d_lw.println("subroutine " + getPrivateName("setg", dim) +
                 "(array, index, val)");
    d_lw.tab();
    d_lw.println(getImplArrayType(dim) + ", intent(inout) :: array");
    d_lw.println(d_intType + ", intent(in), dimension(" + dim +
                 ") :: index");
    d_lw.println(d_implDataType + ", intent(in) :: val");

    if(!Fortran.hasBindC(d_context)) {
      d_lw.println("external " + function);
      d_lw.println("call " + function + 
                   "(array, index, val)");
    }
    else {
      if(d_access) {
        d_lw.print("if(associated(array%d_data)) array%d_data(");
        for(int i = 1; i <= dim; ++i) {
          d_lw.print("index(" + i + ")");
          if(i < dim) d_lw.print(", ");
        }
        d_lw.println(") = val");
      }
      else {
        Type type = new Type(d_type);
        String[] args = {"array", "indices", "value"};
        String[] types = {ptr_v_t, int32_arr_t,
                          Fortran.getBindCType(type, Argument.IN, false) };
        boolean has_proxy = Fortran.hasBindCProxy(type, Argument.IN);
        //declare a Bind(C) proxy variable
        Fortran.printBindCProxyDecl(type, Argument.IN, "bindc_val", d_lw, d_context);
        //spell out the interface
        writeGenericBindCInterface(getMethodName("set"), args, types, false);
        //convert to C if necessary
        if(has_proxy) Fortran.printFortran2BindC(type, "val", "bindc_val",
                                                 Argument.IN, false, false, d_lw);
        //do the actual call
        String rhs = Fortran.getFortran2BindCExpr("val", type, Argument.IN);
        d_lw.println("call " + getMethodName("set") +"(array%d_array, index, " + rhs + ")");
      }
    }
    
    d_lw.backTab();
    d_lw.println("end subroutine " + getPrivateName("setg",dim));
    d_lw.println();
  }

  /**
   * In some cases the bind(C) version calls a C function with the pointer
   * as value attribute instead of actually writing to the argument
   * While this might seem weird, by leaving the intent()-attribute blank,
   * we can carter all needs.
   *
   * Think of writing something like 
   *    call set(get_d_string(h), 0, 'Three')
   * regression/struct/libF03/s_StructTest_Impl.F03:170
   *
   * get_d_string(h) is no l-value so array should be intent(in), right?
   * but the implementation demands intent(inout) because we write to it.
   * Kif, we've got a conundrum.
   */
  private void generateSet(int dim)
    throws UnsupportedEncodingException
  {
    int i;
    final String func = "set";
    final boolean bindc = Fortran.hasBindC(d_context);
    final String function = d_mang.shortArrayName
      (d_implID.getFullName(), func + dim, "_m");
    d_lw.println("subroutine "+getPrivateName(func, dim)+"(array, &");
    d_lw.tab();
    d_lw.tab();
    for(i = 1; i <= dim; ++i){
      d_lw.println("i" + i + ", &");
    }
    d_lw.println("val)");
    d_lw.backTab();

    // see doxygen comment above
    d_lw.println(getImplArrayType(dim) + " :: array");

    for(i = 1; i <= dim; ++i) {
      d_lw.println(d_intType + ", intent(in) :: i" + i);
    }
    d_lw.println(d_implDataType + ", intent(in) :: val");

    if(!bindc) {
      d_lw.println("external " + function);
      d_lw.println("call " + function + 
                   "(array, &");
      d_lw.tab();
      for(i = 1; i <= dim; ++i) {
        d_lw.println("i" + i + ", &");
      }
      d_lw.println("val)");
      d_lw.backTab();
    }
    else {
      if(d_access) {
        d_lw.print("if (associated(array%d_data)) array%d_data(");
        for(i = 1; i <= dim; ++i) {
          d_lw.print("i" + i);
          if(i < dim) d_lw.print(", ");
        }
        d_lw.println(") = val");
      }
      else {
        Type type = new Type(d_type);
        String[] _args = {"array", "i1", "i2", "i3", "i4", "i5", "i6", "i7", "dummy"};
        String[] _types = {ptr_v_t, int32_v_t, int32_v_t, int32_v_t, int32_v_t,
                           int32_v_t, int32_v_t, int32_v_t, "dummy"};
        boolean has_proxy = Fortran.hasBindCProxy(type, Argument.IN);

        String[] args = copyOf(_args, 2 + dim);
        String[] types = copyOf(_types, 2 + dim);
        args[args.length -1] =  new String("value");
        types[args.length -1] = Fortran.getBindCType(type, Argument.IN, false);
        //declare a Bind(C) proxy variable
        Fortran.printBindCProxyDecl(type, Argument.IN, "bindc_val", d_lw, d_context);
        //spell out the interface
        writeGenericBindCInterface(getMethodName(func + dim), args, types, false);
        //convert to C if necessary
        if(has_proxy) Fortran.printFortran2BindC(type, "val", "bindc_val",
                                                 Argument.IN, false, false, d_lw);
        //do the actual call
        String rhs = Fortran.getFortran2BindCExpr("val", type, Argument.IN);
        
        d_lw.print("call " + getMethodName(func + dim) + "(array%d_array, ");
        for(i = 1; i <= dim; ++i) {
          d_lw.print("i" + i + ", ");
        }
        d_lw.println(rhs + ")");
      }
    }
    
    d_lw.backTab();
    d_lw.println("end subroutine " + getPrivateName(func, dim));
    d_lw.println();
  }

  private void generateGetSet(int dim)
    throws UnsupportedEncodingException
  {
    generateGenericGet(dim);
    generateGenericSet(dim);
    generateGet(dim);
    generateSet(dim);
  }

  private void writePrivate(String method)
  {
    int i;
    d_lw.println("private :: &");
    d_lw.tab();
    if(d_is_generic_array) {
      d_lw.print(getPrivateName(method, 0));
    } else {
      d_lw.print(getPrivateName(method, 1));
      for(i = 2; i <= s_maxArray; ++i ){
        d_lw.println(", &");
        d_lw.print(getPrivateName(method, i));
      }
    }
    d_lw.println();
    d_lw.backTab();
    d_lw.println();
  }

  private void writeInterface(String method)
  {
    final String generic = method + "g";
    int i;
    writePrivate(generic);
    writePrivate(method);
    d_lw.println("interface " + method);
    d_lw.tab();
    d_lw.println("module procedure &");
    d_lw.tab();
    for(i = 1; i <= s_maxArray; ++i) {
      d_lw.println(getPrivateName(generic, i) + ", &");
      d_lw.print(getPrivateName(method, i));
      if (i < s_maxArray) {
        d_lw.println(", &");
      }
    }
    d_lw.backTab();
    d_lw.println();
    d_lw.backTab();
    d_lw.println("end interface");
    d_lw.println();
  }

  private void writeSingleInterface(String func, int dim)
  {
    d_lw.println("private :: " + getPrivateName(func, dim));
    d_lw.println();
    d_lw.println("interface " + func);
    d_lw.tab();
    d_lw.println("module procedure " + getPrivateName(func, dim));
    d_lw.backTab();
    d_lw.println("end interface");
    d_lw.println();
  }

  private void writeGenericCastInterfaces()
    throws UnsupportedEncodingException
  {
   
    String typeString = null;
    
    if(d_casttype == USERDEFINED)  //Not a base type or sidl.BaseInterface
      typeString = d_id.getFullName().replace('.','_');
    else
      typeString = "sidl_" + BabelConfiguration.arrayType(d_type);

    d_lw.println();
    d_lw.println("private :: &");
    d_lw.tab();
      
    //Functions that cast FROM a generic array 
    for(int dim = 1; dim <= s_maxArray; ++dim) {
      d_lw.print(d_mang.shortName("cast" + typeString + dim + "dToGeneric",
                                  Fortran.s_privateSuffix));
      if(dim < s_maxArray) d_lw.print(", &"); 
      d_lw.println();
    }
    d_lw.backTab();
    
    String checkHack = Fortran.getStubFile(d_id);
    if (d_type == Type.CLASS && 
        checkHack.equals("sidl_BaseClass_fStub.c") && 
        Fortran.isFortran90(d_context)) { 

      d_lw.println();
      writeCastFunctions("private ::", "castPtrTo" + typeString, true /* +dim */);
    }
    
    if(d_casttype != USERDEFINED) {
      d_lw.println();
      writeCastFunctions("private ::", "castGenericTo" + typeString, true /* +dim */);
    }
    
    if(Fortran.hasBindC(d_context)) {
      d_lw.println();
      writeCastFunctions("private ::", "cast_cptr_to_", true, false /* +dim */, "");
    } else if(d_casttype == BASETYPE && Fortran.isFortran90(d_context)) {
      d_lw.println();
      writeCastFunctions("private ::", "castPtrTo" + typeString, true /* +dim */);
    }
    
    //Interface declaration
    d_lw.println("interface cast");
    d_lw.tab();
    
    writeCastFunctions("module procedure", "cast" + typeString, 
                       false /* arraytype */, true /* +dim */, "ToGeneric");

    if (d_type == Type.CLASS && 
        checkHack.equals("sidl_BaseClass_fStub.c") && 
        Fortran.isFortran90(d_context)) { 
      writeCastFunctions("module procedure", "castPtrTo" + typeString, true /* +dim */);
    }
    
    if(d_casttype != USERDEFINED) {
      writeCastFunctions("module procedure", "castGenericTo" + typeString, true /* +dim */);
    }
    
    if(Fortran.hasBindC(d_context)) {
      writeCastFunctions("module procedure", "cast_cptr_to_", true, false /* +dim */, "");
    } else if (d_casttype == BASETYPE && Fortran.isFortran90(d_context)) {
      writeCastFunctions("module procedure", "castPtrTo" + typeString, true /* +dim */);
    }

    d_lw.backTab();
    d_lw.println("end interface");
    d_lw.println();
  }

  private void writeCastFunctions(String header, String baseName, boolean addDim)
    throws java.io.UnsupportedEncodingException { 
    writeCastFunctions(header, baseName, false, addDim, ""); 
  }

  private void writeCastFunctions(String header,
                                  String baseName, 
                                  boolean useArrayName,
                                  boolean addDim, 
                                  String suffix)
    throws java.io.UnsupportedEncodingException {

    d_lw.println(header + " &");
    d_lw.tab();
    //Functions that cast TO a generic array
    for(int dim = 1; dim <= s_maxArray; ++dim) {
      String arrayName = useArrayName ? Fortran.getArrayName(d_id, dim) : "";
      d_lw.print(d_mang.shortName(baseName + arrayName 
                                  + (addDim ? (dim + "d") : "") 
                                  + suffix,
                                  Fortran.s_privateSuffix));
      if(dim < s_maxArray) d_lw.print(", &"); 
      d_lw.println();
    }
    d_lw.backTab();
  }

  private void writeGenericInterface(String func)
  {
    int i;
    writePrivate(func);
    d_lw.println("interface " + func);
    d_lw.tab();
    d_lw.println("module procedure &");
    d_lw.tab();
 
    if(d_is_generic_array) {
      d_lw.print(getPrivateName(func, 0));
    } else {
      d_lw.print(getPrivateName(func, 1));
      for(i = 2; i <= s_maxArray; ++i) {
        d_lw.println(", &");
        d_lw.print(getPrivateName(func, i));
      }
    }
    d_lw.println();
    d_lw.backTab();
    d_lw.backTab();
    d_lw.println("end interface");
    d_lw.println();
  }

  // this is not called for Bind(C) interfaces
  private void generateAccess(int dim)
    throws UnsupportedEncodingException
  {
    final String function = d_mang.shortArrayName
      (d_implID.getFullName(), "access", "_m");
    d_lw.println("subroutine " + getPrivateName("access", dim) +
                 "(array, ref, low, up, str, index)");
    d_lw.tab();
    d_lw.println("implicit none");
    d_lw.println(getImplArrayType(dim) + ", intent(in) :: array");
    d_lw.println(d_implDataType + ", dimension(1), intent(in) :: ref");
    d_lw.println(d_intType + ", intent(out), dimension(:) :: low, up, str");
    d_lw.println(d_intType + ", intent(out) :: index");

    d_lw.println("external " + function);
    d_lw.println("call " + function +
                 "(array, ref, low, up, str, index)");
    d_lw.backTab();
    d_lw.println("end subroutine " + getPrivateName("access", dim));
  }

  private void generateBorrow(int dim, boolean common)
    throws UnsupportedEncodingException
  {
    final String func = (common ? "cborrow" : "borrow");
    final String function = d_mang.shortArrayName
      (d_implID.getFullName(), func, "_m");
    d_lw.println("subroutine " + getPrivateName(func, dim) +
                 "(first, low, up, astr, res)");
    d_lw.tab();
    d_lw.println(d_implDataType + ", intent(in) :: first");
    d_lw.println(d_intType + ", dimension(" + dim +"), intent(in) :: low, up, astr");
    d_lw.println(getImplArrayType(dim) + ", intent(out) :: res");

    if(!Fortran.hasBindC(d_context)) {
      d_lw.println("external " + function);
      d_lw.println("call " + function + "(first, " + dim + ", low, up, astr, res)");
    }
    else {
      String name = getMethodName(func);
      if(common) name += "_f03";
      String[] args = {"retval", "firstElem", "dimen",
                       "lower", "upper", "stride"};
      String[] types = {ptr_r_t, d_implDataType, int32_v_t,
                        int32_arr_t, int32_arr_t, int32_arr_t};
      d_lw.println("type(c_ptr) :: cptr = c_null_ptr");
      writeGenericBindCInterface(name, args, types, true);
      d_lw.println("cptr = " + name +
                   "(first, " + dim + ", low, up, astr)");
      if(d_access)
        d_lw.println("call cast(cptr, res)");
      else
        d_lw.println("res%d_array = cptr");      
    }
    
    d_lw.backTab();
    d_lw.println("end subroutine " + getPrivateName(func, dim));
    d_lw.println();
  }


  private void generateDeclarations()
    throws UnsupportedEncodingException
  {
    int i;
    if (d_is_base_type) {
      TypeModule.writeArrayType(d_lw, d_id, 
                                (d_access ? d_implDataType : null),
                                d_context);
    }

    if(d_is_generic_array) {  //The methods available in generic arrays.
      for(i = 0; i < s_generic_methods.length ; ++i)
        writeGenericInterface(s_generic_methods[i]);
      
      //cast from a plain cptr
      if(Fortran.hasBindC(d_context)) {
        String cproc_name = d_mang.shortName("cast_cptr_to_" +
                                             "sidl__array",
                                             Fortran.s_privateSuffix);
        d_lw.println("private :: " + cproc_name);
        d_lw.println("interface cast");
        d_lw.tab();
        d_lw.println("module procedure " + cproc_name);
        d_lw.backTab();
        d_lw.println("end interface");
      }
    } else {
      for(i = 0; i < s_constant_methods.length ; ++i)
        writeGenericInterface(s_constant_methods[i]);
      
      writeSingleInterface("create1d",1);
      writeSingleInterface("create2dRow", 2);
      writeSingleInterface("create2dCol", 2);
      
      writeSliceInterface();
      writeInterface("get");
      writeInterface("set");
      
      writeGenericCastInterfaces();
      
      if (d_access && !Fortran.isFortran03(d_context)) {
        writeGenericInterface("access");
      }
      if (d_borrow) {
        writeGenericInterface("borrow");
        writeGenericInterface("cborrow");
      }
    }

    if (Fortran.hasBindC(d_context)) {
      d_lw.generateInclude( Fortran.getArrayBindCdeclFile(d_id) );
    }
  }

  private void generateRef(String func, int dim)
    throws UnsupportedEncodingException
  {
    final String function = d_mang.shortArrayName
      (d_implID.getFullName(), func, "_m");
    d_lw.println("subroutine  " + getPrivateName(func, dim) +
                 "(array)");
    d_lw.tab();
    d_lw.println(getImplArrayType(dim) + ", intent(in) :: array");

    if(!Fortran.hasBindC(d_context)) {
      d_lw.println("external " + function);
      d_lw.println("call " + function + 
                   "(array)");
    }
    // These are already defined in the module 'sidl'.
    // Let's not confuse ifort.
    // else {
    //   String[] args = {"array"};
    //   String[] types = {ptr_v_t};
    //   writeGenericBindCInterface(getMethodName(func), args, types, false);
    //   d_lw.println("call " + getMethodName(func) + "(array%d_array)");
    // }
    
    d_lw.backTab();
    d_lw.println("end subroutine " + getPrivateName(func, dim));
    d_lw.println();
  }

  private void generateRefCounting(int dim)
    throws UnsupportedEncodingException
  {
    generateRef("addRef", dim);
    generateRef("deleteRef", dim);
  }

  private void generateImplementations()
    throws UnsupportedEncodingException
  {
    if(d_is_generic_array) {
      generateSmartCopy("smartCopy", 0);
      generateOrderingTests(0);
      generateSizeQueries(0);
      generateQuery("type", 0, true);
      generateRefCounting(0);
      generateNullRelated(0);

      if(Fortran.hasBindC(d_context)) {
        generateCastFromCPtr(d_type, 0);
      }

    } else {
      for(int i = 1; i <= s_maxArray; ++i) {
        generateCreates(i );
        generateCopy("copy", i);
        generateEnsure(i);
        generateSlice(i);
        generateGetSet(i);

        generateSmartCopy("smartCopy", i);
        generateOrderingTests(i);
        generateSizeQueries(i);
        generateRefCounting(i);
        generateNullRelated(i);
        if (d_access && !Fortran.isFortran03(d_context)) {
          generateAccess(i);
        }
        if (d_borrow) {
          generateBorrow(i, false);
          generateBorrow(i, true);
        }
      }
      generateCasts();  //have their own internal for loop
    }
  }
  
  public void generateStub()
    throws CodeGenerationException
  {
    boolean is_f03 = Fortran.isFortran03(d_context);
    
    try {
      d_lw.println();
      if (d_is_generic_array) {
        d_lw.generateInclude("sidl_array_fAbbrev.h");
        d_lw.println();
      }
      else if(Fortran.hasBindC(d_context)) {
        d_lw.generateInclude(Fortran.getSymbolName(d_implID) + "_fAbbrev.h");
        d_lw.println();
      }
      
      d_lw.println("module " + Fortran.getArrayModule(d_id, d_context));
      d_lw.tab();
      TreeMap emptyMap = new TreeMap();
      d_lw.generateUse("sidl", emptyMap);
      if(!d_is_generic_array && !d_is_base_type)
        d_lw.generateUse(Fortran.getTypeModule(d_id, d_context), emptyMap);

      d_lw.generateUse("sidl_array_type", emptyMap); //Everyone needs the
                                                     //generic module! 
      if(is_f03) d_lw.println("use, intrinsic :: iso_c_binding");

      d_lw.println();
      emptyMap = null;
      
      generateDeclarations();

      d_lw.println();
      d_lw.backTab();
      d_lw.println("contains");
      d_lw.tab();
      d_lw.println();
      d_lw.println();

      generateImplementations();
      d_lw.backTab();
      
      d_lw.println("end module " +  Fortran.getArrayModule(d_id, d_context));
    }
    catch (UnsupportedEncodingException uee) {
      throw new CodeGenerationException(uee.getMessage());
    }
    finally {
      if (d_bindc_decls != null)
        d_bindc_decls.close();
    }
  }
}
