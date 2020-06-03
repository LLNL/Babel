//
// File:        ArrayMethods.java
// Package:     gov.llnl.babel.backend.fortran
// Revision:    @(#) $Revision: 7188 $
// Description: 
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

import gov.llnl.babel.BabelConfiguration;
import gov.llnl.babel.Context;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.c.ArrayMethods;
import gov.llnl.babel.backend.fortran.Fortran;
import gov.llnl.babel.backend.writers.LanguageWriter;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.Version;

public class FortArrayMethods {
  static private final int s_maxArray = BabelConfiguration.getMaximumArray();

  private Context d_context;
  
  private SymbolID d_id;

  private String d_implArrayType;

  private String d_implDataType;

  private String d_valueType;

  private String d_toFortranType;

  private SymbolID d_implID;

  public FortArrayMethods(SymbolID id,
                          boolean isEnum,
                          Context context) {
    d_id = id;
    d_context = context;
    d_implArrayType = isEnum 
      ? "struct sidl_long__array *" 
      : "struct sidl_interface__array *";
    d_toFortranType = isEnum ? "" : "(ptrdiff_t)";
    d_valueType = "int64_t";
    d_implDataType = isEnum 
      ? "" 
      : "(struct sidl_BaseInterface__object *)(ptrdiff_t)";
    if (isEnum) {
      d_implID = new SymbolID("sidl.long", new Version());
    }
    else {
      d_implID = new SymbolID("sidl.interface", new Version());
    }
  }

  private void writeMethodName(LanguageWriter lw,
                               String methodName)
  {
    if(Fortran.hasBindC(d_context)) {
      lw.println(methodName);
    }
    else {
      lw.println(Fortran.getFortranSymbol(d_context) + "(" + methodName.toLowerCase() + ",");
      lw.printSpaces(18);
      lw.println(methodName.toUpperCase() + ",");
      lw.printSpaces(18);
      lw.println(methodName + ")");
    }
  }

  private String generateCreateName(SymbolID id, String shortType)
  {
    return IOR.getSymbolName(id) + "__array_create" + shortType 
           + Fortran.getMethodSuffix(d_context);
  }

  private void generateCreateSig(LanguageWriter lw,
                                 String shortType,
                                 String longType)
  {
    String methodName = generateCreateName(d_id, shortType);
    lw.println("void");
    writeMethodName(lw,methodName);
    lw.tab();
    lw.println("(int32_t *dimen,");
    lw.println(" int32_t lower[],");
    lw.println(" int32_t upper[],");
    lw.println(" int64_t *result)");
    lw.backTab();
  }

  private void generateCreate(LanguageWriter lw,
                              String shortType,
                              String longType)
  {
    generateCreateSig(lw, shortType, longType);
    lw.println("{");
    lw.tab();
    lw.println("*result = (ptrdiff_t)");
    lw.tab();
    lw.println(ArrayMethods.generateCreateName(d_implID, shortType) +
               "(*dimen, lower, upper);");
    lw.backTab();
    lw.backTab();
    lw.println("}");
  }

  private String generateOneDName(SymbolID id)
  {
    return IOR.getSymbolName(id) + "__array_create1d" 
           + Fortran.getMethodSuffix(d_context);
  }

  private void generateOneDSig(LanguageWriter lw)
  {
    String methodName = generateOneDName(d_id);
    lw.println("void");
    writeMethodName(lw, methodName);
    lw.tab();
    lw.println("(int32_t *len, int64_t *result)");
    lw.backTab();
  }
  
  private void generateOneD(LanguageWriter lw)
  {
    generateOneDSig(lw);
    lw.println("{");
    lw.tab();
    lw.println("*result = (ptrdiff_t)" +
               ArrayMethods.generateOneDName(d_implID) + "(*len);");
    lw.backTab();
    lw.println("}");
  }

  private String generateTwoDName(SymbolID id, String shortType)
  {
    return IOR.getSymbolName(id) + "__array_create2d" + shortType 
           + Fortran.getMethodSuffix(d_context);
  }

  private void generateTwoDSig(LanguageWriter lw,
                               String shortType,
                               String longType) 
  {
    String methodName = generateTwoDName(d_id, shortType);
    lw.println("void");
    writeMethodName(lw, methodName);
    lw.tab();
    lw.println("(int32_t *m, int32_t *n, int64_t *result)");
    lw.backTab();
  }

  private void generateTwoD(LanguageWriter lw,
                            String shortType,
                            String longType)
  {
    generateTwoDSig(lw, shortType, longType);
    lw.println("{");
    lw.tab();
    lw.println("*result = (ptrdiff_t)" +
               ArrayMethods.generateTwoDName(d_implID, shortType) + 
               "(*m, *n);");
    lw.backTab();
    lw.println("}");
  }

  private String generateDelRefName(SymbolID id)
  {
    return IOR.getSymbolName(id) + "__array_deleteRef" 
           + Fortran.getMethodSuffix(d_context);
  }
  
  private void generateDelRefSig(LanguageWriter lw)
  {
    lw.println("void");
    writeMethodName(lw,generateDelRefName(d_id));
    lw.tab();
    lw.println("(int64_t *array)");
    lw.backTab();
  }

  private void generateDelRef(LanguageWriter lw)
  {
    generateDelRefSig(lw);
    lw.println("{");
    lw.tab();
    lw.println(ArrayMethods.generateDelRefName(d_implID) + "((" +
               d_implArrayType + ")(ptrdiff_t)*array);");
    lw.backTab();
    lw.println("}");
  }

  private String generateAddRefName(SymbolID id)
  {
    return IOR.getSymbolName(id) + "__array_addRef" + Fortran.getMethodSuffix(d_context);
  }
  
  private void generateAddRefSig(LanguageWriter lw)
  {
    lw.println("void");
    writeMethodName(lw, generateAddRefName(d_id));
    lw.tab();
    lw.println("(int64_t *array)" );
    lw.backTab();
  }

  private void generateAddRef(LanguageWriter lw)
  {
    generateAddRefSig(lw);
    lw.println("{");
    lw.tab();
    lw.println(ArrayMethods.generateAddRefName(d_implID) + "((" +
               d_implArrayType + ")(ptrdiff_t)*array);");
    lw.backTab();
    lw.println("}");
  }

  private String generateGetName(SymbolID id)
  {
    return IOR.getSymbolName(id) + "__array_get" + Fortran.getMethodSuffix(d_context);
  }

  private String generateGetName(SymbolID id, int num)
  {
    return IOR.getSymbolName(id) + "__array_get" + num 
           + Fortran.getMethodSuffix(d_context);
  }

  private void generateGetSig(LanguageWriter lw,
                              int num)
  {
    String methodName = generateGetName(d_id, num);
    lw.println("void");
    writeMethodName(lw, methodName);
    lw.tab();
    lw.println("(int64_t *array, ");
    for(int i = 1; i <= num; ++i){
      lw.println(" int32_t *i" + i + ", ");
    }
    lw.println(" " + d_valueType + " *result)" );
    lw.backTab();
  }

  private void generateGet(LanguageWriter lw,
                           int num)
  {
    generateGetSig(lw, num);
    lw.println("{");
    lw.tab();
    lw.println("*result = " + d_toFortranType);
    lw.tab();
    lw.println(ArrayMethods.generateGetName(d_implID, num) + "((const " +
               d_implArrayType + ")(ptrdiff_t)*array");
    for(int i = 1 ; i <= num; ++i){
      lw.print(", *i" + i);
    }
    lw.println(");");
    lw.backTab();
    lw.backTab();
    lw.println("}");
  }

  private void generateGetSig(LanguageWriter lw)
  {
    String methodName = generateGetName(d_id);
    lw.println("void");
    writeMethodName(lw, methodName);
    lw.tab();
    lw.println("(int64_t *array,");
    lw.println(" int32_t indices[],");
    lw.println(" " + d_valueType + " *result)" );
    lw.backTab();
  }

  private void generateGet(LanguageWriter lw)
  {
    generateGetSig(lw);
    lw.println("{");
    lw.tab();
    lw.println("*result = " + d_toFortranType);
    lw.tab();
    lw.println(ArrayMethods.generateGetName(d_implID) + "((const " +
               d_implArrayType + ")(ptrdiff_t)*array, indices);");
    lw.backTab();
    lw.backTab();
    lw.println("}");
  }

  private String generateSetName(SymbolID id)
  {
    return IOR.getSymbolName(id) + "__array_set" + Fortran.getMethodSuffix(d_context);
  }

  private String generateSetName(SymbolID id, int num)
  {
    return IOR.getSymbolName(id) + "__array_set" + num 
           + Fortran.getMethodSuffix(d_context);
  }

  private void generateSetSig(LanguageWriter lw,
                              int num)
  {
    String methodName = generateSetName(d_id, num);
    lw.println("void");
    writeMethodName(lw, methodName);
    lw.tab();
    lw.println("(int64_t *array,");
    for(int i = 1; i <= num; ++i){
      lw.println(" int32_t *i" + i + ",");
    }
    lw.println(" " + d_valueType + " *value)" );
    lw.backTab();
  }

  private void generateSet(LanguageWriter lw,
                           int num)
  {
    generateSetSig(lw, num);
    lw.println("{");
    lw.tab();
    lw.println(ArrayMethods.generateSetName(d_implID, num) + "((" +
               d_implArrayType + ")(ptrdiff_t)*array");
    for(int i = 1 ; i <= num; ++i){
      lw.print(", *i" + i);
    }
    lw.println(", " + d_implDataType + "*value);");
    lw.backTab();
    lw.println("}");
  }

  private void generateSetSig(LanguageWriter lw)
  {
    String methodName = generateSetName(d_id);
    lw.println("void");
    writeMethodName(lw, methodName);
    lw.tab();
    lw.println("(int64_t *array,");
    lw.println("int32_t indices[],");
    lw.println(d_valueType + " *value)");
    lw.backTab();
  }

  private void generateSet(LanguageWriter lw)
  {
    generateSetSig(lw);
    lw.println("{");
    lw.tab();
    lw.println(ArrayMethods.generateSetName(d_implID) + "((" +
               d_implArrayType + ")(ptrdiff_t)*array, indices, " +
               d_implDataType + "*value);");
    lw.backTab();
    lw.println("}");
  }

  private String generateDimenName(SymbolID id)
  {
    return IOR.getSymbolName(id) + "__array_dimen" + Fortran.getMethodSuffix(d_context);
  }

  private void generateDimenSig(LanguageWriter lw)
  {
    String methodName = generateDimenName(d_id);
    lw.println("void");
    writeMethodName(lw, methodName);
    lw.tab();
    lw.println("(int64_t *array, int32_t *result)");
    lw.backTab();
  }

  private void generateDimen(LanguageWriter lw)
  {
    generateDimenSig(lw);
    lw.println("{");
    lw.tab();
    lw.println("*result =");
    lw.tab();
    lw.println(ArrayMethods.generateDimenName(d_implID) + "((" +
               d_implArrayType + ")(ptrdiff_t)*array);");
    lw.backTab();
    lw.backTab();
    lw.println("}");
  }

  private String generateBoundName(SymbolID id,
                                             String direction)
  {
    return IOR.getSymbolName(id) + "__array_" + direction 
           + Fortran.getMethodSuffix(d_context);
  }

  private void generateBoundSig(LanguageWriter lw,
                                String direction,
                                String desc,
                                String bad)
  {
    String methodName = generateBoundName(d_id, direction);
    lw.println("void");
    writeMethodName(lw, methodName);
    lw.tab();
    lw.println("(int64_t *array,");
    lw.println(" int32_t *ind,");
    lw.println(" int32_t *result)");
    lw.backTab();
  }

  private void generateBound(LanguageWriter lw,
                             String direction,
                             String desc,
                             String bad)
  {
    generateBoundSig(lw, direction, desc, bad);
    lw.println("{");
    lw.tab();
    lw.println("*result = ");
    lw.tab();
    lw.println(ArrayMethods.generateBoundName(d_implID, direction) +
               "((" + d_implArrayType + ")(ptrdiff_t)*array, *ind);");
    lw.backTab();
    lw.backTab();
    lw.println("}");
  }

  private String generateOrderName(SymbolID id,
                                         String order)
  {
    return IOR.getSymbolName(id) + "__array_is"
      + order.substring(0,1).toUpperCase()
      + order.substring(1) + "Order" + Fortran.getMethodSuffix(d_context);
  }

  private void generateOrderSig(LanguageWriter lw,
                                String order)
  {
    String methodName = generateOrderName(d_id, order);
    lw.println("void");
    writeMethodName(lw, methodName);
    lw.tab();
    lw.println("(int64_t *array,");
    lw.println(" SIDL_F" + Fortran.getFortranVersion(d_context) +
               "_Bool *result)");
    lw.backTab();
  }

  private void generateOrder(LanguageWriter lw,
                             String order)
  {
    generateOrderSig(lw, order);
    lw.println("{");
    lw.tab();
    lw.println("*result = " + 
               ArrayMethods.generateOrderName(d_implID, order) +
               "((" + d_implArrayType + ")(ptrdiff_t)*array);");
    lw.backTab();
    lw.println("}");
  }

  private String generateCopyName(SymbolID id)
  {
    return IOR.getSymbolName(id) + "__array_copy" + Fortran.getMethodSuffix(d_context);
  }

  private void generateCopySig(LanguageWriter lw)
  {
    String methodName = generateCopyName(d_id);
    lw.println("void");
    writeMethodName(lw, methodName);
    lw.tab();
    lw.println("(int64_t *src,");
    lw.println(" int64_t *dest)");
    lw.backTab();
  }

  private void generateCopy(LanguageWriter lw)
  {
    generateCopySig(lw);
    lw.println("{");
    lw.tab();
    lw.println(ArrayMethods.generateCopyName(d_implID) +
               "((const " + d_implArrayType + ")(ptrdiff_t)*src,");
    lw.printSpaces(ArrayMethods.generateCopyName(d_implID).length() + 1);
    lw.println("(" + d_implArrayType + ")(ptrdiff_t)*dest);");
    lw.backTab();
    lw.println("}");
  }

  private String generateSliceName(SymbolID id)
  {
    return IOR.getSymbolName(id) + "__array_slice" + Fortran.getMethodSuffix(d_context);
  }

  private void generateSliceSig(LanguageWriter lw)
  {
    String methodName = generateSliceName(d_id);
    lw.println("void");
    writeMethodName(lw, methodName);
    lw.tab();
    lw.println("(int64_t *src,");
    lw.println(" int32_t *dimen,");
    lw.println(" int32_t numElem[],");
    lw.println(" int32_t srcStart[],");
    lw.println(" int32_t srcStride[],");
    lw.println(" int32_t newStart[],");
    lw.println(" int64_t *result)");
    lw.backTab();
  }

  private void generateSlice(LanguageWriter lw)
  {
    generateSliceSig(lw);
    lw.println("{");
    lw.tab();
    lw.println("*result = (ptrdiff_t)");
    lw.tab();
    lw.println(ArrayMethods.generateSliceName(d_implID) +
               "((" + d_implArrayType + ")(ptrdiff_t)*src,");
    lw.tab();
    lw.println("*dimen, numElem, srcStart, srcStride, newStart);");
    lw.backTab();
    lw.backTab();
    lw.backTab();
    lw.println("}");
  }

  private String generateSmartCopyName(SymbolID id)
  {
    return IOR.getSymbolName(id) + "__array_smartCopy" 
           + Fortran.getMethodSuffix(d_context);
  }

  private void generateSmartCopySig(LanguageWriter lw)
  {
    String methodName = generateSmartCopyName(d_id);
    lw.println("void");
    writeMethodName(lw, methodName);
    lw.tab();
    lw.println("(int64_t *src)");
    lw.backTab();
  }

  private void generateSmartCopy(LanguageWriter lw)
  {
    generateSmartCopySig(lw);
    lw.println("{");
    lw.tab();
    lw.println(ArrayMethods.generateSmartCopyName(d_implID) +
               "((" + d_implArrayType + ")(ptrdiff_t)*src);");
    lw.backTab();
    lw.println("}");
  }

  private String generateEnsureName(SymbolID id)
  {
    return IOR.getSymbolName(id) + "__array_ensure" + Fortran.getMethodSuffix(d_context);
  }

  private void generateEnsureSig(LanguageWriter lw)
  {
    String methodName = generateEnsureName(d_id);
    lw.println("void");
    writeMethodName(lw, methodName);
    lw.tab();
    lw.println("(int64_t *src,");
    lw.println(" int32_t *dimen,");
    lw.println(" int     *ordering,");
    lw.println(" int64_t *result)");
    lw.backTab();
  }

  private void generateEnsure(LanguageWriter lw)
  {
    generateEnsureSig(lw);
    lw.println("{");
    lw.tab();
    lw.println("*result = (ptrdiff_t)");
    lw.tab();
    lw.println(ArrayMethods.generateEnsureName(d_implID) +
               "((" + d_implArrayType + ")(ptrdiff_t)*src,");
    lw.println("*dimen, *ordering);");
    lw.backTab();
    lw.backTab();
    lw.println("}");
  }

  private String generateCastName(SymbolID id)
  {
    return IOR.getSymbolName(id) + "__array_cast" + Fortran.getMethodSuffix(d_context);
  }

  private void generateCastSig(LanguageWriter lw)
  {
    String methodName = generateCastName(d_id);
    lw.println("void");
    writeMethodName(lw, methodName);
    lw.tab();
    lw.println("(int64_t *array,");
    lw.println(" int64_t *dimen,");
    lw.println(" struct sidl_fortran_array *result)");
    lw.backTab();
  }

  private void generateCast(LanguageWriter lw)
  {
    generateCastSig(lw);
    lw.println("{");
    lw.tab();
    lw.println("(void)sidl_interface__array_convert2f90(");
    lw.tab();
    lw.println("sidl_interface__array_cast((struct sidl__array *)(ptrdiff_t)*array),");
    lw.backTab();
    lw.println("*dimen, result);");
    lw.backTab();
    lw.println("}");
  }

  public void generateObjectArrayStub(LanguageWriter lw)
  {
    // Special Cast for object arrays
    generateCast(lw);
    lw.println();
  }

  public void generateStub(LanguageWriter lw)
  {
    int i;

    if(Fortran.hasBindC(d_context)) return;
    
    generateCreate(lw, "Col", "column");
    lw.println();

    generateCreate(lw, "Row", "row");
    lw.println();

    generateOneD(lw);
    lw.println();

    generateTwoD(lw, "Col", "column");
    lw.println();

    generateTwoD(lw, "Row", "row");
    lw.println();

    generateAddRef(lw);
    lw.println();

    generateDelRef(lw);
    lw.println();

    for(i = 1; i <= s_maxArray; ++i){
      generateGet(lw, i);
      lw.println();
    }

    generateGet(lw);
    lw.println();

    for(i = 1; i <= s_maxArray; ++i){
      generateSet(lw, i);
      lw.println();
    }

    generateSet(lw);
    lw.println();

    generateDimen(lw);
    lw.println();

    generateBound(lw, "lower", "lower bound", "0");
    lw.println();

    generateBound(lw, "upper", "upper bound", "-1");
    lw.println();

    generateBound(lw, "length", "length", "-1");
    lw.println();

    generateBound(lw, "stride", "stride", "0");
    lw.println();

    generateOrder(lw, "column");
    lw.println();

    generateOrder(lw, "row");
    lw.println();

    generateCopy(lw);
    lw.println();

    generateSmartCopy(lw);
    lw.println();

    generateSlice(lw);
    lw.println();


    generateEnsure(lw);
    lw.println();
  }
}
