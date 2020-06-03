//
// File:        StructModule.java
// Package:     gov.llnl.babel.backend.fortran
// Copyright:   (c) 2003 Lawrence Livermore National Security, LLC
// Revision:    @(#) $Revision: 6171 $
// Date:        $Date: 2007-10-08 17:39:28 -0600 (Mon, 08 Oct 2007) $
// Description: Generate a module containing some derived types
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
import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeConstants;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.CodeSplicer;
import gov.llnl.babel.backend.fortran.Fortran;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.mangler.NameMangler;
import gov.llnl.babel.backend.mangler.NonMangler;
import gov.llnl.babel.backend.writers.LanguageWriterForFortran;
import gov.llnl.babel.symbols.Argument;
import gov.llnl.babel.symbols.AssertionExpression;
import gov.llnl.babel.symbols.Struct;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.Type;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeMap;
/**
 * This class provides the ability to write a BIND(C) module file
 * containing the derived type defined in the SIDL file.
 */
public class StructModule
{
  private LanguageWriterForFortran d_lw;

  private NameMangler d_mang;

  private Struct d_struct;

  private Context d_context;

  private SymbolID d_id;


  /**
   * When Struct Module use cases are being generated, we need to keep track of
   * what use cases have been already generated.
   */
  //private static HashSet useWritten = null;

  public StructModule(LanguageWriterForFortran writer,
                    CodeSplicer              splicer,
                    Struct                   strct,
                      Context                context)
    throws NoSuchAlgorithmException
  {
    d_context = context;
    d_mang = new NonMangler();
    d_lw = writer;
    d_struct = strct;
    d_id = strct.getSymbolID();
  }

  interface SymbolIDProcessor {
    void process(SymbolID id, int type);
    void processArray(SymbolID id, int type);
  };

  /**
   * add all SymbolIDs referenced by the Struct s to the Set ids
   */
  public static void processStructReferences(Struct s, Context context, SymbolIDProcessor p) 
  {
    for(Iterator i = s.getItems().iterator(); i.hasNext(); ) {
        Struct.Item e = (Struct.Item) i.next();
        Type t = e.getType();
        int type = t.getDetailedType();
        SymbolID id = Fortran.getArraySymbolID(t, context);

        if (id != null){
          p.processArray(id, type);
        } else {
           id = t.getSymbolID();
           if (id != null) {
             p.process(id, type);
           }
        }
      }
    }

  private void printRArrayDecl(Type t, String name, String attribute, 
                               boolean genericIndex)
    throws CodeGenerationException
  {
    d_lw.print(Fortran.getArgumentString(t, d_context, false));    
    Iterator indexVar = t.getArrayIndexExprs().iterator();
    AssertionExpression ae = (AssertionExpression)indexVar.next();
    d_lw.print(", ");
    d_lw.print(attribute);
    d_lw.print("dimension(");
    if (genericIndex) {
      d_lw.print(":");
      int count = 1;
      while(indexVar.hasNext()) {
        ae = (AssertionExpression)indexVar.next();
        d_lw.print(", :");
        count++;
      }
    } else d_lw.print(getBindCRarrayShape(t));
    d_lw.println(") :: " + name);
  }

  private String getBindCRarrayShape(Type t) {
    String retval = Fortran.getBindCRarrayShape(t);
    for(Iterator i = d_struct.getItems().iterator(); i.hasNext();) {
      Struct.Item e = (Struct.Item) i.next();
      // FIXME: this is totally unclean
      //        rewrite this on an expression basis!
      // Prefix all subexpressions referencing items
      retval = retval.replaceAll(e.getName(), "s%" + e.getName());
    }
    return retval;
  }
  //private boolean d_check;

  public void generateCode()
    throws CodeGenerationException
  {
    // For Fortran 2003 we also need to include the base class header
    if (Fortran.isFortran03(d_context)) {
      SymbolID bc_id = d_context.getSymbolTable().
        lookupSymbol(BabelConfiguration.getBaseClass()).getSymbolID();
      d_lw.generateInclude( Fortran.getStubNameFile(bc_id) );
    }

    SymbolID bi_id = d_context.getSymbolTable().
      lookupSymbol(BabelConfiguration.getBaseInterface()).getSymbolID();

    //useWritten = new HashSet();

    String structName = d_struct.getFullName();
    d_lw.writeBanner(d_struct, Fortran.getImplModuleFile(d_id, d_context),
                     true,
                     CodeConstants.C_FORTRAN_IMPL_MODULE_PREFIX +
                     d_id.getFullName());
    d_lw.generateInclude( Fortran.getStubNameFile(bi_id));
    d_lw.generateInclude( Fortran.getStubNameFile(d_id));

    // Include the abbrev headers for all complex struct elements
    processStructReferences(d_struct, d_context, new SymbolIDProcessor() {
        public void process(SymbolID id, int type) { 
          d_lw.generateInclude(Fortran.getStubNameFile(id));
        }
        public void processArray(SymbolID id, int type) { process(id, type); }
      });
    
    // FIXME: generate abbrev header for this struct too

    d_lw.println();
    d_lw.println();
    try {
      d_lw.println("module " + d_mang.shortName(structName, ""));
      d_lw.tab();
      d_lw.println("use, intrinsic :: iso_c_binding");

      /* generate use statements for different array types */
      final TreeMap emptyMap = new TreeMap();
      d_lw.generateUse("sidl", emptyMap);
      //d_check = false;

      processStructReferences(d_struct, d_context, new SymbolIDProcessor() {
          public void process(SymbolID id, int type) { 
            /* enums have everything in the type module */
            if (type==Type.ENUM)
              return;
            d_lw.generateUse(Fortran.getModule(id, d_context), emptyMap);
            //if (type==Type.STRUCT)
              //d_check=true;
         }
          public void processArray(SymbolID id, int type) { 
            d_lw.generateUse(Fortran.getArrayModule(id, d_context), emptyMap);
          }
        });

      if (Fortran.hasBindC(d_context) && (!Fortran.isFortran90(d_context))){
        d_lw.println("type, bind(c) :: " + d_mang.shortName(structName, "_t"));
        d_lw.tab();
      } else {
        d_lw.println("type :: " + d_mang.shortName(structName, "_t"));
        d_lw.tab();
      }

      for(Iterator i = d_struct.getItems().iterator(); i.hasNext(); ) {
        Struct.Item e = (Struct.Item) i.next();
        Type t = e.getType();
        int type = t.getDetailedType();
        String name = e.getName();
        Integer objInt = new Integer(type);
        switch(type) {
        case Type.VOID:
          /* do nothing */
          break;
        case Type.ARRAY:
          final Type arrayType = t.getArrayType();
          if (null != arrayType) {
            if (t.isRarray()) {
              if (Fortran.isFortran90(d_context)){
                //final int dims=t.getArrayDimension();
                d_lw.println(Fortran.getReturnString(t, d_context, true)
                             + ", pointer :: "+ name + "(:)");
              } else if (Fortran.hasBindC(d_context)) {
                if (IOR.isFixedSizeRarray(t)) {
                  printRArrayDecl(t, name, "", false);
                } else { 
                  d_lw.println("type(c_ptr) ::  "+name);
                }
              }
            /* deal with SIDL arrays */
            } else {
              /* for now naively treat everything as C_PTR */
              if (Fortran.isFortran03(d_context)) {
                if (arrayType.getDetailedType() <= Type.STRING) {
                  d_lw.println("type(c_ptr) ::  "+name);
                } else {
                  d_lw.println("type(c_ptr) ::  "+name);
                }
              } else {
                //final int dims=t.getArrayDimension();
                d_lw.println(Fortran.getReturnString(t,d_context,true) + " :: "+ name);
              }
            }
          }
          break;
        case Type.STRUCT:
          SymbolID idTest=t.getSymbolID();
          d_lw.println("type("+Fortran.getModule(idTest, d_context)+"_t) :: "+name);
          break;
          /* fall through intended */
        case Type.DOUBLE:
        case Type.FLOAT:
        case Type.INT:
        case Type.LONG:
          default:
            if (Fortran.isFortran03(d_context)){
              d_lw.println(Fortran.getBindCFieldType(t) + " :: "+name);
            } else {
              d_lw.println(Fortran.getReturnString(t,d_context,true) + " :: "+ name);
            }
          }
      }

      d_lw.backTab();
      d_lw.println("end type " + d_mang.shortName(structName, "_t"));

      // Generate struct member getter and setter functions

      // Generate type-bound procedure declarations for get/set in Fortran 2003
      if (Fortran.isFortran03(d_context)) {
        d_lw.println();
        for(Iterator i = d_struct.getItems().iterator(); i.hasNext();) {
          Struct.Item e = (Struct.Item) i.next();

          // FIXME refactor this
          d_lw.println("private :: " + getterName_p(e));
          d_lw.println("interface " + getterName(e));
          d_lw.tab();
          d_lw.println("module procedure " + getterName_p(e));
          d_lw.backTab();
          d_lw.println("end interface ");

          d_lw.println("private :: " + setterName_p(e));
          d_lw.println("interface " + setterName(e));
          d_lw.tab();
          d_lw.println("module procedure " + setterName_p(e));
          d_lw.backTab();
          d_lw.println("end interface ");
        }

        d_lw.println();
        d_lw.println("contains ! Derived type member access functions");
        d_lw.tab();
        d_lw.println();

        for(Iterator i = d_struct.getItems().iterator(); i.hasNext();) {
          Struct.Item e = (Struct.Item) i.next();
          // get_elem(s)
          String fn = getterName_p(e);
          String name = e.getName();
          Type t = e.getType();
          int type = t.getDetailedType();
          String pure = type == Type.STRING || type >= Type.CLASS ? "" : "pure ";

          if (t.isRarray()) {
            d_lw.println(pure + "function " + fn + "(s)");
            d_lw.tab();
            d_lw.println("type(" + d_mang.shortName(structName, "_t") + "), intent(in) :: s");
            if (!IOR.isFixedSizeRarray(t)) {
              printRArrayDecl(t, fn, "pointer, ", true);            
              // we pass the actual fortran memory region.
              d_lw.println("if(c_associated(" + "s%" + name + ")) then");
              d_lw.tab();
              d_lw.println("call c_f_pointer(" + "s%" + name +  ", " +
                           fn + ", [" + getBindCRarrayShape(t) + "])");
              d_lw.backTab();
              d_lw.println("else");
              d_lw.tab();
              d_lw.println("nullify(" + fn + ")");
              d_lw.backTab();
              d_lw.println("end if");
            } else {
              printRArrayDecl(t, fn, "", false);
              d_lw.println(fn + " = s%" + name);
            }
          } else {
            d_lw.println(Fortran.getReturnString(t, d_context, false)
                         + " " + pure + "function " + fn + "(s)");
            d_lw.tab();
            d_lw.println("type(" + d_mang.shortName(structName, "_t") + "), intent(in) :: s");

            if (t.isStruct()) {
              // Structs are Fortran pointers already, no conversion necessary
              d_lw.println(fn + " = s%" + name);
            } else
              Fortran.printBindC2Fortran(t,
                                         "s%" + name,
                                         fn,
                                         Argument.OUT,
                                         true /* emit copy expr */,
                                         false,
                                         d_lw);
          }

          d_lw.backTab();
          d_lw.println("end function " + fn);
          d_lw.println();

          // set_elem(s, val)
          fn = setterName_p(e);
          d_lw.println("subroutine " + fn + "(s, " + name + ")");
          d_lw.tab();
          d_lw.println("type(" + d_mang.shortName(structName, "_t") + "), intent(inout) :: s");
          if (t.isRarray() && !IOR.isFixedSizeRarray(t)) {
            printRArrayDecl(t, name, "target, ", true);
            // FIXME This is ugly:
            // Better find a way to call printBindCProxyDecls for all types
            d_lw.println(Fortran.getArgumentString(t, d_context, false) +
                         ", pointer :: " 
                         + Fortran.getBindCProxyName("s%"+name) 
                         + " => null()");     
          } else {
            d_lw.println(Fortran.getArgumentString(t, d_context, false)
                         + ", intent(in) :: " + name);
          }
          if (t.isStruct() || IOR.isFixedSizeRarray(t))
            d_lw.println("s%" + name + " = " + name);
          else
            Fortran.printFortran2BindC(t, 
                                       name,
                                       "s%" + name,
                                       Argument.IN,
                                       true /* emit copy expr */,
                                       false,
                                       d_lw);
          d_lw.backTab();
          d_lw.println("end subroutine " + fn);
          d_lw.println();
        }

      }
      d_lw.backTab();
      d_lw.backTab();
      d_lw.println("end module " + d_mang.shortName(structName, ""));
    }
    catch (UnsupportedEncodingException uee) {
      throw new CodeGenerationException("UnsupportedEncodingException: " +
                                        uee.getMessage());
    }
  }

  private String getterName(Struct.Item i)   { return "get_" + i.getName(); }
  private String getterName_p(Struct.Item i) { return "get_" + i.getName() + "_p"; }
  private String setterName(Struct.Item i)   { return "set_" + i.getName(); }
  private String setterName_p(Struct.Item i) { return "set_" + i.getName() + "_p"; }

  public static void generateCode(Struct strct,
                                  LanguageWriterForFortran writer,
                                  CodeSplicer splicer,
                                  Context context)
    throws CodeGenerationException, NoSuchAlgorithmException
  {
    StructModule mod = new StructModule(writer, splicer,strct, context);
    mod.generateCode();
  }
}
