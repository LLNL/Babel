//
// File:        ArrayMethods.java
// Package:     gov.llnl.babel.backend
// Revision:    @(#) $Revision: 7188 $
// Date:        $Date: 2011-09-27 11:38:42 -0700 (Tue, 27 Sep 2011) $
// Description: 
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

package gov.llnl.babel.backend.c;
import gov.llnl.babel.BabelConfiguration;
import gov.llnl.babel.Context;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.writers.LanguageWriter;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.Version;

public class ArrayMethods {
  static private final int s_maxArray = BabelConfiguration.getMaximumArray();
  
  private SymbolID d_id;

  private boolean d_isEnum;

  private boolean d_comments;

  private String d_implArrayType;

  private String d_initDataType;

  private String d_implDataType;

  private SymbolID d_implID;

  public ArrayMethods(SymbolID id, boolean isEnum, Context context) {
    d_id = id;
    d_isEnum = isEnum;
    d_implArrayType = isEnum ? "struct sidl_long__array *" 
                             : "struct sidl_interface__array *";
    d_implDataType = isEnum ? "int64_t" : "struct sidl_BaseInterface__object *";
    d_initDataType = isEnum ? "int64_t" : C.getObjectName(id);
    if (isEnum) {
      d_implID = new SymbolID("sidl.long", new Version());
    } else {
      d_implID = new SymbolID("sidl.interface", new Version());
    }
    d_comments = !context.getConfig().getCommentLocalOnly();
  }

  private String getTypeName() {
    return d_isEnum ? C.getEnumName(d_id) : C.getObjectName(d_id);
  }

  public static String generateCreateName(SymbolID id, String shortType) {
    return C.getSymbolName(id) + "__array_create" + shortType;
  }

  private void generateCreateSig(LanguageWriter lw,
                                 String shortType,
                                 String longType,
                                 String terminator)
  {
    String methodName = generateCreateName(d_id, shortType);
    if (d_comments){
      lw.beginBlockComment(true);
      lw.println("Create a contiguous array of the given dimension with "
        + "specified");
      lw.println("index bounds in " + longType + "-major order. This array");
      lw.println("owns and manages its data.");
      if (!d_isEnum){
        lw.println("This function initializes the contents of the array to");
        lw.println("NULL.");
      }
      lw.endBlockComment(true);
    }
    lw.println(IOR.getArrayName(d_id) + "*");
    lw.println(methodName + "(");
    lw.tab();
    lw.println("int32_t       dimen,");
    lw.println("const int32_t lower[],");
    lw.println("const int32_t upper[])" + terminator);
    lw.backTab();
  }

  public static String generateOneDName(SymbolID id) {
    return IOR.getSymbolName(id) + "__array_create1d";
  }

  private void generateOneDSig(LanguageWriter lw, String terminator) {
    String methodName = generateOneDName(d_id);
    if (d_comments){ 
      lw.beginBlockComment(true);
      lw.println("Create a contiguous one-dimensional array with a lower "
        + "index");
      lw.println("of 0 and an upper index of len-1. This array");
      lw.println("owns and manages its data.");
      if (!d_isEnum){
        lw.println("This function initializes the contents of the array to");
        lw.println("NULL.");
      }
      lw.endBlockComment(true);
    }
    lw.println(IOR.getArrayName(d_id) + "*");
    lw.println(methodName + "(int32_t len)" + terminator);
  }
  
  public static String generateOneDInitName(SymbolID id) {
    return IOR.getSymbolName(id) + "__array_create1dInit";
  }

  private void generateOneDInitSig(LanguageWriter lw, String terminator) {
    String methodName = generateOneDInitName(d_id);
    if (d_comments){ 
      lw.beginBlockComment(true);
      lw.println("Create a dense one-dimensional vector with a lower");
      lw.println("index of 0 and an upper index of len-1. The initial data for "
        + "this");
      lw.println("new array is copied from data. This will increment the "
        + "reference");
      lw.println("count of each non-NULL object/interface reference in data.");
      lw.println();
      lw.println("This array owns and manages its data.");
      lw.endBlockComment(true);
    }
    lw.println(IOR.getArrayName(d_id) + "*");
    lw.println(methodName + "(");
    lw.tab();
    lw.println("int32_t len, ");
    lw.println(d_initDataType + "* data)" + terminator);
    lw.backTab();
  }
  
  public static String generateTwoDName(SymbolID id, String shortType) {
    return IOR.getSymbolName(id) + "__array_create2d" + shortType;
  }

  private void generateTwoDSig(LanguageWriter lw, String shortType,
                               String longType, String terminator)
  {
    String methodName = generateTwoDName(d_id, shortType);
    if (d_comments) {
      lw.beginBlockComment(true);
      lw.println("Create a contiguous two-dimensional array in " + longType 
        + "-major");
      lw.println("order with a lower index of (0,0) and an upper index of");
      lw.println("(m-1,n-1). This array owns and manages its data.");
      if (!d_isEnum){
        lw.println("This function initializes the contents of the array to");
        lw.println("NULL.");
      }
      lw.endBlockComment(true);
    }
    lw.println(IOR.getArrayName(d_id) + "*");
    lw.println(methodName + "(int32_t m, int32_t n)" + terminator);
  }

  public static String generateBorrowName(SymbolID id) {
    return IOR.getSymbolName(id) + "__array_borrow";
  }

  private void generateBorrowSig(LanguageWriter lw, String terminator) {
    String methodName = generateBorrowName(d_id);
    String firstType;
    if (d_isEnum) {
      firstType = "int64_t *";
    } else {
      firstType = C.getObjectName(d_id) + "*";
    }
    if (d_comments) {
      lw.beginBlockComment(true);
      lw.println("Create an array that uses data (memory) from another");
      lw.println("source. The initial contents are determined by the");
      lw.println("data being borrowed.");
      if (d_isEnum){
        lw.println("The borrowed data must be a pointer to int64_t.");
      } else {
        lw.println("Any time an element in the borrowed array is replaced");
        lw.println("via a set call, deleteRef will be called on the");
        lw.println("value being replaced if it is not NULL.");
      }
      lw.endBlockComment(true);
    }
    lw.println(IOR.getArrayName(d_id) + "*");
    lw.println(methodName + "(");
    lw.tab();
    lw.println(firstType + " firstElement,");
    lw.println("int32_t       dimen,");
    lw.println("const int32_t lower[],");
    lw.println("const int32_t upper[],");
    lw.println("const int32_t stride[])" + terminator);
    lw.backTab();
  }
  
  public static String generateSmartCopyName(SymbolID id) {
    return IOR.getSymbolName(id) + "__array_smartCopy";
  }

  private void generateSmartCopySig(LanguageWriter lw, String terminator) {
    String methodName = generateSmartCopyName(d_id);
    if (d_comments) {
      lw.beginBlockComment(true);
      lw.println("If array is borrowed, allocate a new self-sufficient");
      lw.println("array and copy the borrowed array into the new array;");
      lw.println("otherwise, increment the reference count and return");
      lw.println("the array passed in. Use this whenever you want to");
      lw.println("make a copy of a method argument because arrays");
      lw.println("passed into methods aren't guaranteed to exist after");
      lw.println("the method call.");

      lw.endBlockComment(true);
    }
    lw.println(IOR.getArrayName(d_id) + "*");
    lw.println(methodName + "(" );
    lw.tab();
    lw.println(IOR.getArrayName(d_id) + " *array)" + terminator);
    lw.backTab();
  }
  
  public static String generateDelRefName(SymbolID id) {
    return IOR.getSymbolName(id) + "__array_deleteRef";
  }
  
  private void generateDelRefSig(LanguageWriter lw, String terminator) {
    if (d_comments) {
      lw.beginBlockComment(true);
      lw.println("Decrement the array's internal reference count by one.");
      lw.println("If the reference count goes to zero, destroy the array.");
      if (!d_isEnum) {
        lw.println("If the array isn't borrowed, this releases all the");
        lw.println("object references held by the array.");
      }
      lw.endBlockComment(true);
    }
    lw.println("void");
    lw.println(generateDelRefName(d_id) + "(");
    lw.tab();
    lw.println(IOR.getArrayName(d_id) + "* array)" + terminator);
    lw.backTab();
    
  }

  public static String generateAddRefName(SymbolID id) {
    return IOR.getSymbolName(id) + "__array_addRef";
  }
  
  private void generateAddRefSig(LanguageWriter lw, String terminator) {
    if (d_comments){ 
      lw.beginBlockComment(true);
      lw.println("Increment the array's internal reference count by one.");
      lw.endBlockComment(true);
    }
    lw.println("void");
    lw.println(generateAddRefName(d_id) + "(");
    lw.tab();
    lw.println(IOR.getArrayName(d_id) + "* array)" + terminator);
    lw.backTab();
    
  }

  public static String generateGetName(SymbolID id) {
    return IOR.getSymbolName(id) + "__array_get";
  }

  public static String generateGetName(SymbolID id, int num) {
    return generateGetName(id) + num;
  }

  private void generateGetSig(LanguageWriter lw, int num, String terminator) {
    String methodName = generateGetName(d_id, num);
    String arrayName = IOR.getArrayName(d_id) + "*";
    if (d_comments) {
      lw.beginBlockComment(true);
      lw.print("Retrieve element ");
      if (num == 1){
        lw.print("i1");
      }
      else {
        lw.print("(");
        for(int i = 1 ; i <= num; ++i){
          lw.print("i" + i);
          if (i < num) lw.print(",");
        }
        lw.print(")");
      }
      lw.println(" of a(n) " + num + "-dimensional array.");
      lw.endBlockComment(true);
    }
    lw.println(getTypeName());
    lw.println(methodName + "(");
    lw.tab();
    lw.println("const " + arrayName + " array,");
    for(int i = 1; i <= num; ++i){
      lw.println("const int32_t i" + i + 
                 ((i < num) ? "," : (")" + terminator)));
    }
    lw.backTab();
  }

  private void generateGetSig(LanguageWriter lw, String terminator) {
    String methodName = generateGetName(d_id);
    String arrayName = IOR.getArrayName(d_id) + "*";
    if (d_comments) {
      lw.beginBlockComment(true);
      lw.println("Retrieve element indices of an n-dimensional array.");
      lw.println("indices is assumed to have the right number of elements");
      lw.println("for the dimension of array.");
      lw.endBlockComment(true);
    }
    lw.println(getTypeName());
    lw.println(methodName + "(");
    lw.tab();
    lw.println("const " + arrayName + " array,");
    lw.println("const int32_t indices[])" + terminator);
    lw.backTab();
  }

  public static String generateSetName(SymbolID id) {
    return IOR.getSymbolName(id) + "__array_set";
  }

  public static String generateSetName(SymbolID id, int num) {
    return generateSetName(id) + num;
  }

  private void generateSetSig(LanguageWriter lw, int num, String terminator) {
    String methodName = generateSetName(d_id, num);
    String arrayName = IOR.getArrayName(d_id) + "*";
    if (d_comments) {
      lw.beginBlockComment(true);
      lw.print("Set element ");
      if (num == 1){
        lw.print("i1");
      }
      else {
        lw.print("(");
        for(int i = 1 ; i <= num; ++i){
          lw.print("i" + i);
          if (i < num) lw.print(",");
        }
        lw.print(")");
      }
      lw.println(" of a(n) " + num + "-dimensional array to value.");
      lw.endBlockComment(true);
    }
    lw.println("void");
    lw.println(methodName + "(");
    lw.tab();
    lw.println(arrayName + " array,");
    for(int i = 1; i <= num; ++i){
      lw.println("const int32_t i" + i + ",");
    }
    lw.println(getTypeName() + " const value)" + terminator);
    lw.backTab();
  }

  private void generateSetSig(LanguageWriter lw, String terminator) {
    String methodName = generateSetName(d_id);
    String arrayName = IOR.getArrayName(d_id) + "*";
    if (d_comments) {
      lw.beginBlockComment(true);
      lw.print("Set element indices of an n-dimensional array to value.");
      lw.println("indices is assumed to have the right number of elements");
      lw.println("for the dimension of array.");
      lw.endBlockComment(true);
    }
    lw.println("void");
    lw.println(methodName + "(");
    lw.tab();
    lw.println(arrayName + " array,");
    lw.println("const int32_t indices[],");
    lw.println(getTypeName() + " const value)" + terminator);
    lw.backTab();
  }

  public static String generateDimenName(SymbolID id) {
    return IOR.getSymbolName(id) + "__array_dimen";
  }

  private void generateDimenSig(LanguageWriter lw, String terminator) {
    String methodName = generateDimenName(d_id);
    String arrayName = IOR.getArrayName(d_id) + "*";
    if (d_comments) {
      lw.beginBlockComment(true);
      lw.println("Return the dimension of array. If the array pointer is");
      lw.println("NULL, zero is returned.");
      lw.endBlockComment(true);
    }
    lw.println("int32_t");
    lw.println(methodName + "(");
    lw.tab();
    lw.println("const " + arrayName + " array)" + terminator);
    lw.backTab();
  }

  public static String generateBoundName(SymbolID id, String direction) {
    return IOR.getSymbolName(id) + "__array_" + direction;
  }

  private void generateBoundSig(LanguageWriter lw, String direction,
                                String desc, String bad, String terminator)
  {
    String methodName = generateBoundName(d_id, direction);
    String arrayName = IOR.getArrayName(d_id) + "*";
    if (d_comments) {
      lw.beginBlockComment(true);
      lw.println("Return the " + desc +  " of dimension ind.");
      lw.println("If ind is not a valid dimension, " + bad + " is returned.");
      lw.println("The valid range for ind is from 0 to dimen-1.");
      lw.endBlockComment(true);
    }
    lw.println("int32_t");
    lw.println(methodName + "(");
    lw.tab();
    lw.println("const " + arrayName + " array,");
    lw.println("const int32_t ind)" + terminator);
    lw.backTab();
  }

  public static String generateOrderName(SymbolID id, String order) {
    return IOR.getSymbolName(id) + "__array_is"
      + order.substring(0,1).toUpperCase()
      + order.substring(1) + "Order";
  }

  private void generateOrderSig(LanguageWriter lw, String order,
                                String terminator)
  {
    String methodName = generateOrderName(d_id, order);
    String arrayName = IOR.getArrayName(d_id) + "*";
    if (d_comments) {
      lw.beginBlockComment(true);
      lw.println("Return a true value iff the array is a contiguous");
      lw.println(order + "-major ordered array. A NULL array argument");
      lw.println("causes 0 to be returned.");
      lw.endBlockComment(true);
    }
    lw.println("int");
    lw.println(methodName + "(");
    lw.tab();
    lw.println("const " + arrayName + " array)" + terminator);
    lw.backTab();
  }

  public static String generateCopyName(SymbolID id) {
    return IOR.getSymbolName(id) + "__array_copy";
  }

  private void generateCopySig(LanguageWriter lw, String terminator) {
    String methodName = generateCopyName(d_id);
    String arrayName = IOR.getArrayName(d_id) + "*";
    if (d_comments) {
      lw.beginBlockComment(true);
      lw.println("Copy the contents of one array (src) to a second array");
      lw.println("(dest). For the copy to take place, both arrays must");
      lw.println("exist and be of the same dimension. This method will");
      lw.println("not modify dest's size, index bounds, or stride; only");
      lw.println("the array element values of dest may be changed by");
      lw.println("this function. No part of src is ever changed by copy.");
      lw.println();
      lw.println("On exit, dest[i][j][k]... = src[i][j][k]... for all");
      lw.println("indices i,j,k...  that are in both arrays. If dest and");
      lw.println("src have no indices in common, nothing is copied. For");
      lw.println("example, if src is a 1-d array with elements 0-5 and");
      lw.println("dest is a 1-d array with elements 2-3, this function");
      lw.println("will make the following assignments:");
      lw.println("  dest[2] = src[2],");
      lw.println("  dest[3] = src[3].");
      lw.println("The function copied the elements that both arrays have");
      lw.println("in common.  If dest had elements 4-10, this function");
      lw.println("will make the following assignments:");
      lw.println("  dest[4] = src[4],");
      lw.println("  dest[5] = src[5].");
      lw.endBlockComment(true);
    }
    lw.println("void");
    lw.println(methodName + "(");
    lw.tab();
    lw.println("const " + arrayName + " src,");
    lw.println(arrayName + " dest)" + terminator);
    lw.backTab();
  }

  public static String generateSliceName(SymbolID id) {
    return IOR.getSymbolName(id) + "__array_slice";
  }

  private void generateSliceSig(LanguageWriter lw, String terminator) {
    String methodName = generateSliceName(d_id);
    String arrayName = IOR.getArrayName(d_id) + "*";
    if (d_comments) {
      lw.beginBlockComment(true);
      lw.println("Create a sub-array of another array. This resulting");
      lw.println("array shares data with the original array. The new");
      lw.println("array can be of the same dimension or potentially");
      lw.println("less assuming the original array has dimension");
      lw.println("greater than 1.  If you are removing dimension,");
      lw.println("indicate the dimensions to remove by setting");
      lw.println("numElem[i] to zero for any dimension i wthat should");
      lw.println("go away in the new array.  The meaning of each");
      lw.println("argument is covered below.");
      lw.println();
      lw.println("src       the array to be created will be a subset");
      lw.println("          of this array. If this argument is NULL,");
      lw.println("          NULL will be returned. The array returned");
      lw.println("          borrows data from src, so modifying src or");
      lw.println("          the returned array will modify both");
      lw.println("          arrays.");
      lw.println();
      lw.println("dimen     this argument must be greater than zero");
      lw.println("          and less than or equal to the dimension of");
      lw.println("          src. An illegal value will cause a NULL");
      lw.println("          return value.");
      lw.println();
      lw.println("numElem   this specifies how many elements from src");
      lw.println("          should be taken in each dimension. A zero");
      lw.println("          entry indicates that the dimension should");
      lw.println("          not appear in the new array.  This");
      lw.println("          argument should be an array with an entry");
      lw.println("          for each dimension of src.  Passing NULL");
      lw.println("          here will cause NULL to be returned.  If");
      lw.println("          srcStart[i] + numElem[i]*srcStride[i] is");
      lw.println("          greater than upper[i] for src or if");
      lw.println("          srcStart[i] + numElem[i]*srcStride[i] is");
      lw.println("          less than lower[i] for src, NULL will be");
      lw.println("          returned.");
      lw.println();
      lw.println("srcStart  this array holds the coordinates of the");
      lw.println("          first element of the new array. If this");
      lw.println("          argument is NULL, the first element of src");
      lw.println("          will be the first element of the new");
      lw.println("          array. If non-NULL, this argument should");
      lw.println("          be an array with an entry for each");
      lw.println("          dimension of src.  If srcStart[i] is less");
      lw.println("          than lower[i] for the array src, NULL will");
      lw.println("          be returned.");
      lw.println();
      lw.println("srcStride this array lets you specify the stride");
      lw.println("          between elements in each dimension of");
      lw.println("          src. This stride is relative to the");
      lw.println("          coordinate system of the src array. If");
      lw.println("          this argument is NULL, the stride is taken");
      lw.println("          to be one in each dimension.  If non-NULL,");
      lw.println("          this argument should be an array with an");
      lw.println("          entry for each dimension of src.");
      lw.println();
      lw.println("newLower  this argument is like lower in a create");
      lw.println("          method. It sets the coordinates for the");
      lw.println("          first element in the new array.  If this");
      lw.println("          argument is NULL, the values indicated by");
      lw.println("          srcStart will be used. If non-NULL, this");
      lw.println("          should be an array with dimen elements.");
      lw.endBlockComment(true);
    }
    lw.println(arrayName);
    lw.println(methodName + "(");
    lw.tab();
    lw.println(arrayName + " src,");
    lw.println("int32_t        dimen,");
    lw.println("const int32_t  numElem[],");
    lw.println("const int32_t  *srcStart,");
    lw.println("const int32_t  *srcStride,");
    lw.println("const int32_t  *newStart)" + terminator);
    lw.backTab();
  }

  public static String generateEnsureName(SymbolID id) {
    return IOR.getSymbolName(id) + "__array_ensure";
  }

  private void generateEnsureSig(LanguageWriter lw, String terminator) {
    String methodName = generateEnsureName(d_id);
    String arrayName = IOR.getArrayName(d_id) + "*";
    if (d_comments) {
      lw.beginBlockComment(true);
      lw.println("If necessary, convert a general matrix into a matrix");
      lw.println("with the required properties. This checks the");
      lw.println("dimension and ordering of the matrix.  If both these");
      lw.println("match, it simply returns a new reference to the");
      lw.println("existing matrix. If the dimension of the incoming");
      lw.println("array doesn't match, it returns NULL. If the ordering");
      lw.println("of the incoming array doesn't match the specification,");
      lw.println("a new array is created with the desired ordering and");
      lw.println("the content of the incoming array is copied to the new");
      lw.println("array.");
      lw.println();
      lw.println("The ordering parameter should be one of the constants");
      lw.println("defined in enum sidl_array_ordering");
      lw.println("(e.g. sidl_general_order, sidl_column_major_order, or");
      lw.println("sidl_row_major_order). If you specify");
      lw.println("sidl_general_order, this routine will only check the");
      lw.println("dimension because any matrix is sidl_general_order.");
      lw.println();
      lw.println("The caller assumes ownership of the returned reference");
      lw.println("unless it's NULL.");
      lw.endBlockComment(true);
    }
    lw.println(arrayName);
    lw.println(methodName + "(");
    lw.tab();
    lw.println(arrayName + " src,");
    lw.println("int32_t dimen,");
    lw.println("int     ordering)" + terminator);
    lw.backTab();
  }

  public void generateHeader(LanguageWriter lw) {
    int i;

    generateCreateSig(lw, "Col", "column", ";");
    lw.println();

    generateCreateSig(lw, "Row", "row", ";");
    lw.println();

    generateOneDSig(lw, ";");
    lw.println();

    generateOneDInitSig(lw, ";");
    lw.println();

    generateTwoDSig(lw, "Col", "column", ";");
    lw.println();

    generateTwoDSig(lw, "Row", "row", ";");
    lw.println();

    generateBorrowSig(lw, ";");
    lw.println();

    generateSmartCopySig(lw, ";");
    lw.println();

    generateAddRefSig(lw, ";");
    lw.println();

    generateDelRefSig(lw, ";");
    lw.println();

    for(i = 1; i <= s_maxArray; ++i){
      generateGetSig(lw, i, ";");
      lw.println();
    }

    generateGetSig(lw, ";");
    lw.println();

    for(i = 1; i <= s_maxArray; ++i){
      generateSetSig(lw, i, ";");
      lw.println();
    }

    generateSetSig(lw, ";");
    lw.println();

    generateDimenSig(lw, ";");
    lw.println();

    generateBoundSig(lw, "lower", "lower bound", "0", ";");
    lw.println();

    generateBoundSig(lw, "upper", "upper bound", "-1", ";");
    lw.println();

    generateBoundSig(lw, "length", "length", "-1", ";");
    lw.println();

    generateBoundSig(lw, "stride", "stride", "0", ";");
    lw.println();

    generateOrderSig(lw, "column", ";");
    lw.println();

    generateOrderSig(lw, "row", ";");
    lw.println();

    generateSliceSig(lw, ";");
    lw.println();

    generateCopySig(lw, ";");
    lw.println();

    generateEnsureSig(lw, ";");
    lw.println();
  }

  private void generateCreate(LanguageWriter lw, String shortType,
                              String longType)
  {
    generateCreateSig(lw, shortType, longType, "");
    lw.println("{");
    lw.tab();
    lw.println("return (" + IOR.getArrayName(d_id) + "*)" 
       + generateCreateName(d_implID, shortType) + "(dimen, lower, upper);");
    lw.backTab();
    lw.println("}");
  }

  private void generateOneD(LanguageWriter lw) {
    generateOneDSig(lw, "");
    lw.println("{");
    lw.tab();
    lw.println("return (" + IOR.getArrayName(d_id) + "*)" 
      + generateOneDName(d_implID) + "(len);");
    lw.backTab();
    lw.println("}");
  }

  private void generateOneDInit(LanguageWriter lw) {
    generateOneDInitSig(lw, "");
    lw.println("{");
    lw.tab();
    lw.println("return (" + IOR.getArrayName(d_id) + "*)" 
      + generateOneDInitName(d_implID) + "(len, (" + d_implDataType 
      + "*)data);");
    lw.backTab();
    lw.println("}");
  }

  private void generateTwoD(LanguageWriter lw, String shortType,
                            String longType)
  {
    generateTwoDSig(lw, shortType, longType, "");
    lw.println("{");
    lw.tab();
    lw.println("return (" + IOR.getArrayName(d_id) + "*)" +
               generateTwoDName(d_implID, shortType) + "(m, n);");
    lw.backTab();
    lw.println("}");
  }

  private void generateBorrow(LanguageWriter lw) {
    generateBorrowSig(lw, "");
    lw.println("{");
    lw.tab();
    lw.println("return (" + IOR.getArrayName(d_id) + "*)" +
               generateBorrowName(d_implID) + "(");
    lw.tab();
    if (!d_isEnum) {
      lw.println("(struct sidl_BaseInterface__object **)");
    }
    lw.println("firstElement, dimen, lower, upper, stride);");
    lw.backTab();
    lw.backTab();
    lw.println("}");
  }
  
  private void generateSmartCopy(LanguageWriter lw) {
    generateSmartCopySig(lw, "");
    lw.println("{");
    lw.tab();
    lw.println("return (" + IOR.getArrayName(d_id) + "*)");
    lw.tab();
    lw.println(generateSmartCopyName(d_implID) + 
               "((" + d_implArrayType + ")array);");
    lw.backTab();
    lw.backTab();
    lw.println("}");
  }
  
  private void generateDelRef(LanguageWriter lw) {
    generateDelRefSig(lw, "");
    lw.println("{");
    lw.tab();
    lw.println(generateDelRefName(d_implID) + "((" + d_implArrayType 
      + ")array);");
    lw.backTab();
    lw.println("}");
  }

  private void generateAddRef(LanguageWriter lw) {
    generateAddRefSig(lw, "");
    lw.println("{");
    lw.tab();
    lw.println(generateAddRefName(d_implID) + "((" + d_implArrayType 
      + ")array);");
    lw.backTab();
    lw.println("}");
  }

  private void generateGet(LanguageWriter lw, int num) {
    generateGetSig(lw, num, "");
    lw.println("{");
    lw.tab();
    lw.println("return (" + getTypeName() + ")");
    lw.tab();
    lw.println(generateGetName(d_implID, num) + "((const " + d_implArrayType 
      + ")array");
    for(int i = 1 ; i <= num; ++i){
      lw.print(", i" + i);
    }
    lw.println(");");
    lw.backTab();
    lw.backTab();
    lw.println("}");
  }

  private void generateGet(LanguageWriter lw) {
    generateGetSig(lw, "");
    lw.println("{");
    lw.tab();
    lw.println("return (" + getTypeName() + ")");
    lw.tab();
    lw.println(generateGetName(d_implID) + "((const " + d_implArrayType 
      + ")array, indices);");
    lw.backTab();
    lw.backTab();
    lw.println("}");
  }

  private void generateSet(LanguageWriter lw, int num) {
    generateSetSig(lw, num, "");
    lw.println("{");
    lw.tab();
    lw.println(generateSetName(d_implID, num) + "((" + d_implArrayType 
      + ")array");
    for(int i = 1 ; i <= num; ++i){
      lw.print(", i" + i);
    }
    lw.println(", (" + d_implDataType + ")value);");
    lw.backTab();
    lw.println("}");
  }

  private void generateSet(LanguageWriter lw) {
    generateSetSig(lw, "");
    lw.println("{");
    lw.tab();
    lw.println(generateSetName(d_implID) + "((" + d_implArrayType 
      + ")array, indices, (" + d_implDataType + ")value);");
    lw.backTab();
    lw.println("}");
  }

  private void generateDimen(LanguageWriter lw) {
    generateDimenSig(lw, "");
    lw.println("{");
    lw.tab();
    lw.println("return " + generateDimenName(d_implID) + "((" + d_implArrayType
      + ")array);");
    lw.backTab();
    lw.println("}");
  }

  private void generateBound(LanguageWriter lw,
                                String direction,
                                String desc,
                                String bad)
  {
    generateBoundSig(lw, direction, desc, bad, "");
    lw.println("{");
    lw.tab();
    lw.println("return " + generateBoundName(d_implID, direction) + "((" 
      + d_implArrayType + ")array, ind);");
    lw.backTab();
    lw.println("}");
  }

  private void generateOrder(LanguageWriter lw, String order) {
    generateOrderSig(lw, order, "");
    lw.println("{");
    lw.tab();
    lw.println("return " + generateOrderName(d_implID, order) + "((" 
      + d_implArrayType + ")array);");
    lw.backTab();
    lw.println("}");
  }

  private void generateCopy(LanguageWriter lw) {
    generateCopySig(lw, "");
    lw.println("{");
    lw.tab();
    lw.println(generateCopyName(d_implID) + "((const " + d_implArrayType 
      + ")src,");
    lw.printSpaces(generateCopyName(d_implID).length() + 1);
    lw.println("(" + d_implArrayType + ")dest);");
    lw.backTab();
    lw.println("}");
  }

  private void generateSlice(LanguageWriter lw) {
    generateSliceSig(lw, "");
    lw.println("{");
    lw.tab();
    lw.println("return (" + IOR.getArrayName(d_id) + "*)");
    lw.tab();
    lw.println(generateSliceName(d_implID) + "((" + d_implArrayType + ")src,");
    lw.printSpaces(generateSliceName(d_implID).length() + 1);
    lw.println("dimen, numElem, srcStart, srcStride, newStart);");
    lw.backTab();
    lw.backTab();
    lw.println("}");
  }

  private void generateEnsure(LanguageWriter lw) {
    generateEnsureSig(lw, "");
    lw.println("{");
    lw.tab();
    lw.println("return (" + IOR.getArrayName(d_id) + "*)");
    lw.tab();
    lw.println(generateEnsureName(d_implID) + "((" + d_implArrayType 
      + ")src, dimen, ordering);");
    lw.backTab();
    lw.backTab();
    lw.println("}");
  }

  public void generateStub(LanguageWriter lw) {
    int i;
    generateCreate(lw, "Col", "column");
    lw.println();

    generateCreate(lw, "Row", "row");
    lw.println();

    generateOneD(lw);
    lw.println();

    generateOneDInit(lw);
    lw.println();

    generateTwoD(lw, "Col", "column");
    lw.println();

    generateTwoD(lw, "Row", "row");
    lw.println();

    generateBorrow(lw);
    lw.println();

    generateSmartCopy(lw);
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

    generateSlice(lw);
    lw.println();

    generateEnsure(lw);
    lw.println();
  }
}
