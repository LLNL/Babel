//
// File:        CodeConstants.java
// Package:     gov.llnl.babel.backend
// Revision:    @(#) $Id: CodeConstants.java 7188 2011-09-27 18:38:42Z adrian $
// Description: basic constants associated with generated source code
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

package gov.llnl.babel.backend;

/**
 * Interface <code>CodeConstants</code> contains basic constants associated
 * with generated source code.  
 *
 * NOTE: Initially, the constants are primarily those that caused parsing 
 * problems with Rational Rose 7.0.9517 reverse engineering.
 */
public interface CodeConstants 
{
  public final static String C_AUTO_GEN_WARNING     =
    "Automatically generated; changes will be lost";
  public final static String C_AUTO_GEN_SPLICER     =
    "Automatically generated; only changes within splicers preserved";
  public final static String C_BEGIN_UNREFERENCED_METHODS =
    "================= BEGIN UNREFERENCED METHOD(S) ================";
  public final static String C_UNREFERENCED_COMMENT1      =
    "The following code segment(s) belong to unreferenced method(s).";
  public final static String C_UNREFERENCED_COMMENT2      =
    "This can result from a method rename/removal in the sidl file.";
  public final static String C_UNREFERENCED_COMMENT3      =
    "Move or remove the code in order to compile cleanly.";
  public final static String C_END_UNREFERENCED_METHODS   = 
    "================== END UNREFERENCED METHOD(S) =================";

  public final static String C_BACKSLASH            = "\\";
  public final static String C_HASH                 = "#";
  public final static String C_SLASH                = "//";

  public final static String C_COMMENT_OPEN         = "/*";
  public final static String C_COMMENT_CLOSE        = "*/";
  public final static String C_COMMENT_DOC_OPEN     = C_COMMENT_OPEN + "*";
  public final static String C_COMMENT_HASH         = C_HASH + " ";
  public final static String C_COMMENT_SLASH        = C_SLASH + " ";
  public final static String C_COMMENT_SUBSEQUENT   = " * ";
  public final static String C_COMMENT_F77          = "C";
  public final static String C_COMMENT_F90          = "! ";

  public final static String C_DEFINE               = "#define ";

  public final static String C_DESC_IMPL_PREFIX     =
    "Server-side implementation for ";
  public final static String C_DESC_IOR_PREFIX      =
    "Intermediate Object Representation for ";
  public final static String C_DESC_STUB_PREFIX     =
    "Client-side glue code for ";
  public final static String C_DESC_HEADER_PREFIX   =
    "Client-side header code for ";
  public final static String C_DESC_SKEL_PREFIX     =
    "Server-side glue code for ";
  public final static String C_DESC_FSKEL_PREFIX     =
    "Server-side Fortran glue code for ";
  public final static String C_DESC_CJNI_PREFIX     =
    "Client-side JNI glue code for ";
  public final static String C_DESC_SJNI_PREFIX     =
    "Server-side JNI glue code for ";

  public final static String C_UTILITY_HEADER_PREFIX   =
    "Protoypes for utility functions for  ";
  public final static String C_UTILITY_SOURCE_PREFIX     =
    "Implementation of utility functions for ";
  
  public final static String C_FORTRAN_DESC_STUB_PREFIX =
    "Client-side documentation text for ";
  public final static String C_FORTRAN_MODULE_PREFIX =
    "Client-side module for ";
  public final static String C_FORTRAN_TYPE_MODULE_PREFIX =
    "Client-side module for ";
  public final static String C_FORTRAN_IMPL_MODULE_PREFIX =
    "Server-side private data module for ";
  public final static String C_F77_IMPL_EXTENSION     = "f";
  public final static String C_F7731_IMPL_EXTENSION   = "fpp";
  public final static String C_F90_IMPL_EXTENSION     = "F90";
  public final static String C_F77_METHOD_SUFFIX      = "_f";
  public final static String C_F90_METHOD_SUFFIX      = "_m";
  public final static String C_F03_METHOD_SUFFIX      = "_m";
  public final static String C_F90_ALT_SUFFIX         = "_a";
  public final static String C_F03_ALT_SUFFIX         = "_a_c";
  public final static String C_F77_IMPL_METHOD_SUFFIX = "_fi";
  public final static String C_F90_IMPL_METHOD_SUFFIX = "_mi";
  public final static String C_F03_IMPL_METHOD_SUFFIX = "_impl";
  public final static int    C_F77_VERSION            = 77;
  public final static int    C_F90_VERSION            = 90;
  public final static int    C_F03_VERSION            = 2003;
  public final static String C_F03_IMPL_EXTENSION     = "F03";
  public final static String C_F03_BINDC_SUFFIX       = "_c";

  public final static String C_GUARD_CLOSE            = "#endif";
  public final static String C_GUARD_OPEN             = "#ifndef ";
  public final static String MATLAB_COMMENT_OPEN      = "% ";

  /*
   * The following IS_IMPL-related values represent flags that can be
   * used to distinguish between whether or not the call is being made
   * on behalf of an IMPL file.  They are used solely for enhancing
   * the readability of the call.
   */
  public final static boolean C_IS_IMPL             = true;
  public final static boolean C_IS_NOT_IMPL         = false;

  public final static String C_IFDEFINE_CLOSE       = "#endif";
  public final static String C_IFDEFINE_OPEN        = "#ifdef ";
  public final static String C_IFDEFINE_CXX         = C_IFDEFINE_OPEN 
                                                      + "__cplusplus";

  public final static String C_INCLUDE              = "#include ";

  public final static String C_INSERT_HERE = "Insert-Code-Here {";

  public final static int    C_INT_BACKSLASH        = '\\';
}
