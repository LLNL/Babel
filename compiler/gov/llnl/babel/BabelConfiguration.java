//
// File:        BabelConfiguration.java
// Package:     gov.llnl.babel
// Revision:    @(#) $Revision: 7188 $
// Date:        $Date: 2011-09-27 11:38:42 -0700 (Tue, 27 Sep 2011) $
// Description: Store configuration info shared by parsers and backend
// 
// Copyright (c) 2000-2007, Lawrence Livermore National Security, LLC
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

package gov.llnl.babel;

import gov.llnl.babel.symbols.RegexMatch;
import gov.llnl.babel.symbols.SymbolID;

import java.util.LinkedList;
import java.util.List;

/**
 * This class provides access to configuration information (including user
 * options) required by the parsers and backend.
 *
 * This class originally envisaged as a singleton. However, it makes sense
 * to have several of them when trying to do several runs in a single
 * command line.
 */
public class BabelConfiguration implements Cloneable
{
  /*
   * The basic configuration literals.
   */
  private static final String BASE_CLASS     = "sidl.BaseClass";
  private static final String BASE_CLASSINFO = "sidl.ClassInfo";
  private static final String BASE_CLASSINFOI= "sidl.ClassInfoI";
  private static final String BASE_EXCEPTION = "sidl.BaseException";
  private static final String BASE_INTERFACE = "sidl.BaseInterface";
  private static final String RUNTIME_EXCEPTION = "sidl.RuntimeException";
  private static final String MEMORYALLOCATION_EXCEPTION = "sidl.MemAllocException";
  private static final String NOTIMPLEMENTED_EXCEPTION = "sidl.NotImplementedException";
  private static final String LANGSPECIFIC_EXCEPTION = "sidl.LangSpecificException";
  private static final String MAKEFILE_NAME  = "babel.make";
  private static final int    MAXIMUM_ARRAY  = 7;
  private static final String SIDL_EXCEPTION = "sidl.SIDLException";
  private static final String SIDL_RMI_TICKET = "sidl.rmi.Ticket";
  private static final String SIDL_SERIALIZABLE = "sidl.io.Serializable";

  
  public  static final String FUND_EXCEPTION = "sidl.BaseInterface";
  public  static final String PRE_EXCEPTION  = "sidl.PreViolation";
  public  static final String POST_EXCEPTION = "sidl.PostViolation";
  public  static final String INV_EXCEPTION  = "sidl.InvViolation";

  public  static final String CONTRACT_ENFORCER = "sidl.Enforcer";
  public  static final String CONTRACT_POLICY   = "sidl.EnfPolicy";

  public static final String OPTIONAL_RMI = "WITH_RMI";

  /**
   * Static options -- not dependent on command line settings
   * or represent global settings.
   */
  private boolean d_multi_mode             = false;
  private StringBuffer d_repository_path   = new StringBuffer ("");
  private List    d_included_symbols       = new LinkedList();

  /*
   * User options -- extractable from the command line via UserOptions.
   * Each of these must be copied in clone().
   */
  private boolean d_suppress_contracts     = false;
  private boolean d_generate_hooks         = false;
  private boolean d_package_subdirs        = false;
  private boolean d_language_subdir        = false;
  private boolean d_glue_subdirs           = false;
  private boolean d_checkNullIOR           = false;
  private boolean d_suppress_timestamps    = true;
  private boolean d_comment_local_only     = false;
  private boolean d_short_file_names       = false;
  private String  d_output_directory       = ".";
  private String  d_vpath_directory        = null;
  private LinkedList d_excluded_symbols       = new LinkedList();
  private boolean d_exclude_external       = true;
  private String  d_make_prefix            = "";
  private String  d_makefile               = MAKEFILE_NAME;
  private boolean d_suppress_ior           = false;
  private boolean d_suppress_stub          = false;
  private boolean d_rename_splicers        = false;
  private boolean d_cca_mode               = false; 
  private boolean d_protect_last_time_mod  = true;
  private boolean d_genmakefile            = false;
  private boolean d_skiprmi                = false;
  private boolean d_strictProvenance       = false;
  private boolean d_fast_call              = false;

  /*
   * State variables that are deliberately not copied during clone.
   */
  private boolean d_parser_check           = false;
  private boolean d_verbose                = false;
  private boolean d_generate_stdlib        = false;
  private boolean d_generate_client        = false;
  private boolean d_generate_text          = false;
  private boolean d_generate_server        = false;
  private String  d_target_language        = null;
  private boolean d_has_bind_c             = true;

  /**
   *  These are the values returned by calling the _type function 
   *  query on an array.  THESE MUST BE THE SAME AS FOUND IN 
   *  runtime/sidl/sidlArray.h  (They are also used in F77 and F90)
   */
  public static final int sidl_bool_array      = 1;
  public static final int sidl_char_array      = 2;
  public static final int sidl_dcomplex_array  = 3;
  public static final int sidl_double_array    = 4;
  public static final int sidl_fcomplex_array  = 5;
  public static final int sidl_float_array     = 6;
  public static final int sidl_int_array       = 7;
  public static final int sidl_long_array      = 8;
  public static final int sidl_opaque_array    = 9;
  public static final int sidl_string_array    = 10;
  public static final int sidl_interface_array = 11;
    /* an array of sidl.BaseInterface's */

  /** This array corrosponds to the enumeration found in sidlArray.h*/
  private static final String s_ensureOrderConstant[] = {
    "sidl_general_order",
    "sidl_column_major_order",
    "sidl_row_major_order"
  };


  /**
   * Since this is a singleton class, its constructor is protected.
   */
  public BabelConfiguration() {
  }

  /**
   * Make a copy of the configuration.
   */
  public Object clone()
    throws CloneNotSupportedException
  {
    BabelConfiguration result = (BabelConfiguration)super.clone();
    result.d_suppress_contracts = this.d_suppress_contracts;
    result.d_generate_hooks = this.d_generate_hooks;
    result.d_package_subdirs = this.d_package_subdirs;
    result.d_language_subdir = this.d_language_subdir;
    result.d_glue_subdirs = this.d_glue_subdirs;
    result.d_checkNullIOR = this.d_checkNullIOR;
    result.d_suppress_timestamps = this.d_suppress_timestamps;
    result.d_comment_local_only = this.d_comment_local_only;
    result.d_short_file_names = this.d_short_file_names;
    result.d_output_directory = this.d_output_directory;
    result.d_vpath_directory = this.d_vpath_directory;
    result.d_excluded_symbols = (LinkedList)this.d_excluded_symbols.clone();
    result.d_exclude_external = this.d_exclude_external;
    result.d_make_prefix = this.d_make_prefix;
    result.d_makefile = this.d_makefile;
    result.d_suppress_ior = this.d_suppress_ior;
    result.d_suppress_stub = this.d_suppress_stub;
    result.d_rename_splicers = this.d_rename_splicers;
    result.d_cca_mode = this.d_cca_mode;
    result.d_protect_last_time_mod = this.d_protect_last_time_mod;
    result.d_genmakefile = this.d_genmakefile;
    result.d_skiprmi = this.d_skiprmi;
    result.d_strictProvenance = this.d_strictProvenance;
    result.d_fast_call = this.d_fast_call;
    return result;
  }

  public static String arrayType(int type) {
    switch(type) {
    case BabelConfiguration.sidl_bool_array:
      return "bool";
    case BabelConfiguration.sidl_char_array:
      return "char";
    case BabelConfiguration.sidl_dcomplex_array:
      return "dcomplex";
    case BabelConfiguration.sidl_double_array:
      return "double";
    case BabelConfiguration.sidl_fcomplex_array:
      return "fcomplex";
    case BabelConfiguration.sidl_float_array:
      return "float";
    case BabelConfiguration.sidl_int_array:
      return "int";
    case BabelConfiguration.sidl_long_array:
      return "long";
    case BabelConfiguration.sidl_opaque_array:
      return "opaque";
    case BabelConfiguration.sidl_string_array:
      return "string";
    case BabelConfiguration.sidl_interface_array:
      return "BaseInterface";
    default:
      return "BaseInterface";
    }
       
  }

  /**
   * Return the full name of the root of the SIDL type hierarchy.
   */
   public static String getBaseInterface() {
     return BASE_INTERFACE;
  }

  /**
   * Return the full name of the root of the class type hierarchy.
   * This is the one class that does not have a parent class.
   */
  public static String getBaseClass() {
    return BASE_CLASS;
  }

  /**
   * Return the full name of the ClassInfo interface.
   */
  public static String getClassInfo() {
    return BASE_CLASSINFO;
  }

  /**
   * Return the full name of the class implementing ClassInfo interface.
   */
  public static String getClassInfoI() {
    return BASE_CLASSINFOI;
  }

  /**
   * The full name of the base of the exception type hierarchy.
   * All exceptions must extend this type.
   */
  public static String getBaseExceptionInterface() {
    return BASE_EXCEPTION;
  }

  /**
   * Return the full name of the base exception class.
   */
  public static String getBaseExceptionClass() {
    return SIDL_EXCEPTION;
  }

  /**
   * Return the full name of the base exception class.
   */
  public static String getOptionalRmiMacro() {
    return OPTIONAL_RMI;
  }

  /**
   * The full name of the most basic exception type.
   */
  public static String getFundamentalException() { 
    return FUND_EXCEPTION;
  }

  /**
   * The full name of the exception type implicit in all methods.
   */
  public static String getRuntimeException() { 
    return RUNTIME_EXCEPTION;
  }

  /**
   * The full name of the Invariant Violation type implicit in all 
   * methods of classes with invariants.
   */
  public static String getInvariantViolation() { 
    return INV_EXCEPTION;
  }

  /**
   * The full name of the Precondition Violation type implicit in all 
   * methods with preconditions.
   */
  public static String getPreconditionViolation() { 
    return PRE_EXCEPTION;
  }

  /**
   * The full name of the Postcondition Violation type implicit in all 
   * methods with postconditions.
   */
  public static String getPostconditionViolation() { 
    return POST_EXCEPTION;
  }

  public static String getLangSpecific(){ return LANGSPECIFIC_EXCEPTION; }
  public static String getNotImplemented(){ return NOTIMPLEMENTED_EXCEPTION; }
  public static String getMemoryAllocationException(){ return MEMORYALLOCATION_EXCEPTION; }


  /**
   * The full name of the fundamental exception type.
   */
  public static String getBaseExceptionType() {
    return BASE_INTERFACE;
  }

  /**
   * 
   */
  public static String getRMITicket() { 
    return SIDL_RMI_TICKET;
  }

  /**
   * 
   */
  public static String getSerializableType() { 
    return SIDL_SERIALIZABLE;
  }

  
  /**
   * The full name of the file associated with generated makefile fragments.
   */
  public String getMakefileName() {
    return d_makefile;
  }

  /**
   * Should a Makefile be generated.
   */
  public boolean getGenMakefile() {
    return d_genmakefile;
  }

  /**
   * Determine whether a Makefile should be generated.
   */
  void setGenMakefile(boolean value) {
    d_genmakefile = value;
  }

  public boolean getSkipRMI()
  {
    return d_skiprmi;
  }

  void setSkipRMI(boolean value) {
    d_skiprmi = value;
  }

  public boolean getStrictProvenance() {
    return d_strictProvenance;
  }

  void setStrictProvenance(boolean value) {
    d_strictProvenance = value;
  }

  public boolean getFastCall() {
    return d_fast_call;
  }

  void setFastCall(boolean value) {
    d_fast_call = value;
  }
  
  /**
   * Return the maximum array dimension.
   */
  public static int getMaximumArray() {
    return MAXIMUM_ARRAY;
  }

  public void setMultiMode(boolean multi_mode) {
    d_multi_mode = multi_mode;
  }

  public boolean getMultiMode()
  {
    return d_multi_mode;
  }

  /**
   * Return whether the specified symbol belongs to the SIDL namespace.
   */
  public static boolean isSIDLBaseClass(SymbolID id) { 
    boolean is_sidl = false;

    if (id != null) {
      String n = id.getFullName();
      if ((n != null) && (n.equals("sidl") || n.startsWith("sidl."))) {
         is_sidl = true;
      }
    }
    
    return is_sidl;
  }

  /**
   * Set the IOR suppression to <code>true</code> or <code>false</code>.
   */
  public void setSuppressIOR(boolean value)
  {
    d_suppress_ior = value;
  }

  public boolean getSuppressIOR()
  {
    return d_suppress_ior;
  }

  /** Set the stub suppression to <code>true</code> or <code>false</code>.
   */
  public void setSuppressStub(boolean value)
  {
    d_suppress_stub = value;
  }

  public boolean getSuppressStub()
  {
    return d_suppress_stub;
  }

  /** Set the rename splicers flag to <code>true</code> or <code>false</code>.
   */
  public void setRenameSplicers(boolean value)
  {
    d_rename_splicers = value;
  }

  public boolean getRenameSplicers()
  {
    return d_rename_splicers;
  }

  /** Set the rename splicers flag to <code>true</code> or <code>false</code>.
   */
  public void setCCAMode(boolean value)
  {
    d_cca_mode = value;
  }

  public boolean getProtectLastTimeModified()
  {
    return d_protect_last_time_mod;
  }

  public void setProtectLastTimeModified(boolean value)
  {
    d_protect_last_time_mod = value;
  }

  public boolean getCCAMode()
  {
    return d_cca_mode; 
  }

  /**
   * Set contract suppression to <code>true</code> or <code>false</code>.
   */
  public void setSuppressContracts(boolean suppress) {
    d_suppress_contracts = suppress;
  }

  /**
   * Return whether or not contract enforcement code should be generated.
   */
  public boolean generateContracts() {
    return !d_suppress_contracts;
  }
  
  /**
   *
   */
  public static String getArrayOrderName(int i) {
    if(i >=0 && i<= 3) {
      return s_ensureOrderConstant[i];
    }
    return null;
  }

  /**
   * Set the value of the client generation user option.
   */
  public void setGenerateClient(boolean generate) {
    d_generate_client = generate;
  }

  /**
   * Return the value of the client generation user option.
   */
  public boolean generateClient() {
    return d_generate_client;
  }
  
  /**
   * Set the value of the pre/post method hooks generation user option.
   */
  public void setGenerateHooks(boolean generate) {
    d_generate_hooks = generate;
  }

  /**
   * Return the value of the pre/post method hooks generation user option.
   */
  public boolean generateHooks() {
    return d_generate_hooks;
  }
  
  /**
   * Set the value of the server generation user option.
   */
  public void setGenerateServer(boolean generate) {
    d_generate_server = generate;
  }

  /**
   * Return the value of the server generation user option.
   */
  public boolean generateServer() {
    return d_generate_server;
  }
  
  /**
   * Set the value of the text generation user option.
   */
  public void setGenerateText(boolean generate) {
    d_generate_text = generate;
  }

  /**
   * Return the value of the text generation user option.
   */
  public boolean generateText() {
    return d_generate_text;
  }
  
  /**
   * Set the value of the SIDL stdlib generation user option.
   */
  public void setGenerateStdlib(boolean generate) {
    d_generate_stdlib = generate;
  }

  /**
   * Return the value of the SIDL stdlib generation user option.
   */
  public boolean generateStdlib() {
    return d_generate_stdlib;
  }
  
  /**
   * Set the value of the parser check user option.
   */
  public void setParseCheckOnly(boolean parse) {
    d_parser_check = parse;
  }

  /**
   * Return the value of the parser check user option.
   */
  public boolean parseCheckOnly() {
    return d_parser_check;
  }

  /**
   * Set the value of the parser check user option.
   */
  public void setVerbose(boolean verbose) { 
    d_verbose = verbose;
  }

  /**
   * Return the value of the parser check user option.
   */
  public boolean isVerbose() {
    return d_verbose;
  }
  
  /**
   * Set the value of the comment local methods only option.
   */
  public void setCommentLocalOnly(boolean localOnly) {
    d_comment_local_only = localOnly;
  }

  /**
   * Return the value of the comment local methods only option.
   */
  public boolean getCommentLocalOnly() {
    return d_comment_local_only;
  }
  
  /**
   * Set the value of the short file names option.
   */
  public void setShortFileNames(boolean shortFileNames) {
    d_short_file_names = shortFileNames;
  }

  /**
   * Return the value of the short file names option.
   */
  public boolean getShortFileNames() {
    return d_short_file_names;
  }
  
  /**
   * Set the value of the timestamp suppression user option.
   */
  public void setSuppressTimestamps(boolean suppress) {
    d_suppress_timestamps = suppress;
  }

  /**
   * Return the value of the timestamp suppression user option.
   */
  public boolean suppressTimestamps() {
    return d_suppress_timestamps;
  }

  /**
   * Set the value of the make package subdirs user option.
   */
  public void setMakePackageSubdirs(boolean make_subdirs) {
    d_package_subdirs = make_subdirs;
  }

  /**
   * Return the value of the make package subdirs user option.
   */
  public boolean makePackageSubdirs() {
    return d_package_subdirs;
  }
  
  /**
   * Set the value of the package and glue subdirs user option.
   */
  public void setMakeGlueSubdirs(boolean make_glue_subdirs) {
    d_glue_subdirs = make_glue_subdirs;
  }
  
  /**
   * Return the value of the package and glue subdirs user option.
   */
  public boolean makeGlueSubdirs() {
    return d_glue_subdirs;
  }
  
  /**
   * Set the value of the Cxx NullIORException checking option.
   */
  public void setCxxCheckNullIOR(boolean checkNullIOR) {
    d_checkNullIOR = checkNullIOR;
  }

  /**
   * Return the value of the make package subdirs user option.
   */
  public boolean makeCxxCheckNullIOR() {
    return d_checkNullIOR;
  }


  /**
   * Specify whether files for each language should be generated in
   * a separate subdirectory. When the argument is true, all code is
   * placed in a subdirectory whose name corresponds to the language,
   * e.g. c++, f90, etc.
   */
  public void setMakeLanguageSubdir(boolean make_language_subdir) {
    d_language_subdir = make_language_subdir;
  }

  /**
   * Return the value of the language subdirectory user option.
   */
  public boolean makeLanguageSubdir() {
    return d_language_subdir;
  }

  /**
   * Set the value of the output directory user option.
   */
  public void setOutputDirectory(String dir) {
    d_output_directory = dir;
  }

  /**
   * Return the value of the output directory user option.
   */
  public String getOutputDirectory() {
    return d_output_directory;
  }
  
  /**
   * Set the value of the vpath directory user option.
   */
  public void setVPathDirectory(String dir) {
    d_vpath_directory = dir;
  }

  /**
   * Return the value of the vpath directory user option.
   */
  public String getVPathDirectory() {
    return d_vpath_directory;
  }
  
  /**
   * Set the value of the target language user option.
   */
  public void setTargetLanguage(String lang) {
    d_target_language = lang;
  }

  public String getTargetLanguage() {
    return d_target_language;
  }

  /**
   * Set if this Fortran compiler supports the iso_c_binding module
   */
  public void setHasBindC(boolean bindc) {
    d_has_bind_c = bindc;
  }

  public boolean getHasBindC() {
    return d_has_bind_c;
  }

  /**
   * Adds a new entry to the repository path.
   */
  public void addToRepositoryPath(String new_path) {
    if ( d_repository_path.length() == 0 ) {
      d_repository_path.insert (0, new_path);
    } else {
      d_repository_path.insert (0, new_path + ";");
    }
  }

  public void removeFromRepositoryPath(String path) {
    String curPath = getRepositoryPath();
    int index;
    if (curPath.equals(path)) {
      d_repository_path = new StringBuffer();
    }
    else if (curPath.endsWith(";" + path)) {
      d_repository_path = new 
        StringBuffer(curPath.substring(0, curPath.length() - path.length()-1));
    }
    else if (curPath.startsWith(path + ";")) {
      d_repository_path = new StringBuffer(curPath.substring(path.length()+1));
    }
    else if ((index = curPath.indexOf(";" + path + ";")) >= 0) {
      d_repository_path = new StringBuffer
        (curPath.substring(0, index+1) +
         curPath.substring(index+2+path.length()));
    }
  }

  /**
   * Return the value of the repository path.
   */
  public String getRepositoryPath() {
    return d_repository_path.toString();
  }

  /**
   * Add another regular expression to the list of excluded
   * regular expressions.
   */
  public void addExcluded(RegexMatch rm) {
    d_excluded_symbols.add(rm);
  }

  /**
   * Return the list of {@link gov.llnl.babel.symbols.RegexMatch} objects
   * that should be excluded from code generation.
   */
  public List getExcludedList() {
    return d_excluded_symbols;
  }


  /**
   * Add another regular expression to the list of included 
   * regular expressions.
   */
  public void addIncluded(String str) {
    d_included_symbols.add(str);
  }

  /**
   * Return the list of {@link gov.llnl.babel.symbols.RegexMatch} objects
   * that should be included from code generation.
   */
  public List getIncludedList() {
    return d_included_symbols;
  }

  /**
   * Set the value of the exclude external symbol code generation user option.
   */
  public void setExcludeExternal(boolean val) {
    d_exclude_external = val;
  }

  /**
   * Return the value of the exclude external symbol code generation user 
   * option.
   */
  public boolean excludeExternal() {
    return d_exclude_external;
  }
 
  /**
   * Return the make prefix option.
   */
  public String getMakePrefix() {
    return d_make_prefix;
  }

  /**
   * Set the make prefix and makefile name options.
   */
  public void setMakePrefix(String prefix) {
    d_make_prefix = ((prefix != null) ? prefix : "");
    d_makefile = d_make_prefix + MAKEFILE_NAME;
  }
}
