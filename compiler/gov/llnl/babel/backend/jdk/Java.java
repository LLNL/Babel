// File:        Java.java
// Package:     gov.llnl.babel.backend.jdk
// Revision:    @(#) $Id: Java.java 7421 2011-12-16 01:06:06Z adrian $
// Description: common Java binding routines shared by Java code generators
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

package gov.llnl.babel.backend.jdk;

import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.Utilities;
import gov.llnl.babel.backend.writers.LanguageWriter;
import gov.llnl.babel.backend.writers.LanguageWriterForC;
import gov.llnl.babel.backend.writers.LanguageWriterForJava;
import gov.llnl.babel.backend.c.C;
import gov.llnl.babel.symbols.Argument;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.Type;
import gov.llnl.babel.symbols.Struct;

import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Class <code>Java</code> contains common Java language binding routines
 * shared by the Java backend code generators.  This class simply collects
 * many common Java binding routines into one place.
 */

public class Java { 

    /**
     *  These Hashmaps contain commonly used lines of codes for generation.
     *  It keeps them all in one place.  Some of those tables used for
     *  The server side of Java are just ones used in the client side
     *  renamed.  Be careful about changing them!
     */

  private static final HashMap s_java_val  = new HashMap();
  private static final HashMap s_java_ref  = new HashMap();
  private static final HashMap s_ior_name  = new HashMap();
  private static final HashMap s_java_arr  = new HashMap();
  private static final HashMap s_java_sig  = new HashMap();
  private static final HashMap s_jni_arg   = new HashMap();
  private static final HashMap s_jni_meth  = new HashMap();
  private static final HashMap s_init_ior  = new HashMap();
  private static final HashMap s_init_jni  = new HashMap();
  private static final HashMap s_init_java = new HashMap();
  private static final HashMap s_in        = new HashMap();
  private static final HashMap s_inout     = new HashMap();
  private static final HashMap s_post      = new HashMap();
  private static final HashMap s_return    = new HashMap();
  private static final HashMap s_server_in = new HashMap();
  private static final HashMap s_server_out = new HashMap();
  private static final HashMap s_server_inout = new HashMap();
  private static final HashMap s_server_post  = new HashMap();
  private static final HashMap s_server_return= new HashMap();

  private static final Integer s_void      = new Integer(Type.VOID);
  private static final Integer s_boolean   = new Integer(Type.BOOLEAN);
  private static final Integer s_char      = new Integer(Type.CHAR);
  private static final Integer s_dcomplex  = new Integer(Type.DCOMPLEX);
  private static final Integer s_double    = new Integer(Type.DOUBLE);
  private static final Integer s_fcomplex  = new Integer(Type.FCOMPLEX);
  private static final Integer s_float     = new Integer(Type.FLOAT);
  private static final Integer s_int       = new Integer(Type.INT);
  private static final Integer s_long      = new Integer(Type.LONG);
  private static final Integer s_opaque    = new Integer(Type.OPAQUE);
  private static final Integer s_string    = new Integer(Type.STRING);
  private static final Integer s_enum      = new Integer(Type.ENUM);
  private static final Integer s_class     = new Integer(Type.CLASS);
  private static final Integer s_interface = new Integer(Type.INTERFACE);
  private static final Integer s_array     = new Integer(Type.ARRAY);
  private static final Integer s_struct    = new Integer(Type.STRUCT);

  static {
    s_java_val.put(s_void,     "void");
    s_java_val.put(s_boolean,  "boolean");
    s_java_val.put(s_char,     "char");
    s_java_val.put(s_dcomplex, "sidl.DoubleComplex");
    s_java_val.put(s_double,   "double");
    s_java_val.put(s_fcomplex, "sidl.FloatComplex");
    s_java_val.put(s_float,    "float");
    s_java_val.put(s_int,      "int");
    s_java_val.put(s_long,     "long");
    s_java_val.put(s_opaque,   "long");
    s_java_val.put(s_string,   "java.lang.String");
    s_java_val.put(s_enum,     "long");

    s_java_ref.put(s_boolean,  "sidl.Boolean$Holder");
    s_java_ref.put(s_char,     "sidl.Character$Holder");
    s_java_ref.put(s_dcomplex, "sidl.DoubleComplex$Holder");
    s_java_ref.put(s_double,   "sidl.Double$Holder");
    s_java_ref.put(s_fcomplex, "sidl.FloatComplex$Holder");
    s_java_ref.put(s_float,    "sidl.Float$Holder");
    s_java_ref.put(s_int,      "sidl.Integer$Holder");
    s_java_ref.put(s_long,     "sidl.Long$Holder");
    s_java_ref.put(s_opaque,   "sidl.Opaque$Holder");
    s_java_ref.put(s_string,   "sidl.String$Holder");
    s_java_ref.put(s_enum,     "sidl.Enum$Holder");
    //    s_java_ref.put(s_array,     "#1$Holder");

    s_ior_name.put(s_boolean,  "bool");
    s_ior_name.put(s_char,     "char");
    s_ior_name.put(s_dcomplex, "dcomplex");
    s_ior_name.put(s_double,   "double");
    s_ior_name.put(s_fcomplex, "fcomplex");
    s_ior_name.put(s_float,    "float");
    s_ior_name.put(s_int,      "int");
    s_ior_name.put(s_long,     "long");
    s_ior_name.put(s_opaque,   "opaque");
    s_ior_name.put(s_string,   "string");
    s_ior_name.put(s_enum,     "long");
    s_ior_name.put(s_class,    "BaseInterface");
    s_ior_name.put(s_interface,    "BaseInterface");

    s_java_arr.put(s_boolean,  "sidl.Boolean$Array#");
    s_java_arr.put(s_char,     "sidl.Character$Array#");
    s_java_arr.put(s_dcomplex, "sidl.DoubleComplex$Array#");
    s_java_arr.put(s_double,   "sidl.Double$Array#");
    s_java_arr.put(s_fcomplex, "sidl.FloatComplex$Array#");
    s_java_arr.put(s_float,    "sidl.Float$Array#");
    s_java_arr.put(s_int,      "sidl.Integer$Array#");
    s_java_arr.put(s_long,     "sidl.Long$Array#");
    s_java_arr.put(s_enum,     "sidl.Enum$Array#");
    s_java_arr.put(s_opaque,   "sidl.Opaque$Array#");
    s_java_arr.put(s_string,   "sidl.String$Array#");

    s_java_sig.put("boolean", "Z");
    s_java_sig.put("char",    "C");
    s_java_sig.put("int",     "I");
    s_java_sig.put("long",    "J");
    s_java_sig.put("float",   "F");
    s_java_sig.put("double",  "D");
    s_java_sig.put("void",    "V");

    s_jni_arg.put("void",             "void");
    s_jni_arg.put("boolean",          "jboolean");
    s_jni_arg.put("char",             "jchar");
    s_jni_arg.put("double",           "jdouble");
    s_jni_arg.put("float",            "jfloat");
    s_jni_arg.put("int",              "jint");
    s_jni_arg.put("long",             "jlong");
    s_jni_arg.put("java.lang.String", "jstring");

    s_jni_meth.put("void",             "");
    s_jni_meth.put("boolean",          "GetBooleanField");
    s_jni_meth.put("char",             "GetCharField");
    s_jni_meth.put("double",           "GetDoubleField");
    s_jni_meth.put("float",            "GetFloatField");
    s_jni_meth.put("int",              "GetIntField");
    s_jni_meth.put("long",             "GetLongField");
    s_jni_meth.put("java.lang.String", "GetObjectField");
    
    s_init_ior.put(s_boolean,   "#1 #2 = FALSE;");
    s_init_ior.put(s_char,      "#1 #2 = (#1) 0;");
    s_init_ior.put(s_dcomplex,  "#1 #2 = { 0.0, 0.0 };");
    s_init_ior.put(s_double,    "#1 #2 = 0.0;");
    s_init_ior.put(s_fcomplex,  "#1 #2 = { 0.0, 0.0 };");
    s_init_ior.put(s_float,     "#1 #2 = 0.0;");
    s_init_ior.put(s_int,       "#1 #2 = 0;");
    s_init_ior.put(s_long,      "#1 #2 = 0;");
    s_init_ior.put(s_opaque,    "#1 #2 = (#1) NULL;");
    s_init_ior.put(s_string,    "#1 #2 = (#1) NULL;");
    s_init_ior.put(s_enum,      "#1 #2 = (#1) 0;");
    s_init_ior.put(s_class,     "#1 #2 = (#1) NULL;");
    s_init_ior.put(s_interface, "#1 #2 = (#1) NULL;");
    s_init_ior.put(s_array,     "#1 #2 = (#1) NULL;");
    s_init_ior.put(s_struct,    "#1 #2 = {0};");

    s_init_jni.put("jboolean", "#1 #2 = JNI_FALSE;");
    s_init_jni.put("jchar",    "#1 #2 = 0;");
    s_init_jni.put("jdouble",  "#1 #2 = 0.0;");
    s_init_jni.put("jfloat",   "#1 #2 = 0.0;");
    s_init_jni.put("jint",     "#1 #2 = 0;");
    s_init_jni.put("jlong",    "#1 #2 = 0;");
    s_init_jni.put("jobject",  "#1 #2 = (#1) NULL;");
    s_init_jni.put("jstring",  "#1 #2 = (#1) NULL;");

    s_init_java.put("boolean", "#1 #2 = false");
    s_init_java.put("char",    "#1 #2 = 0");
    s_init_java.put("double",  "#1 #2 = 0.0;");
    s_init_java.put("float",   "#1 #2 = 0.0;");
    s_init_java.put("int",     "#1 #2 = 0;");
    s_init_java.put("long",    "#1 #2 = 0;");
    s_init_java.put("object",  "#1 #2 = (#1) NULL;");
    s_init_java.put("string",  "#1 #2 = (#1) NULL;");

    s_in.put(s_boolean,   "#1 = (#3) #2;");
    s_in.put(s_char,      "#1 = (#3) #2;");
    s_in.put(s_dcomplex,  "#1 = sidl_Java_J2I_dcomplex(env, #2);");
    s_in.put(s_double,    "#1 = (#3) #2;");
    s_in.put(s_fcomplex,  "#1 = sidl_Java_J2I_fcomplex(env, #2);");
    s_in.put(s_float,     "#1 = (#3) #2;");
    s_in.put(s_int,       "#1 = (#3) #2;");
    s_in.put(s_long,      "#1 = (#3) #2;");
    s_in.put(s_opaque,    "#1 = (#3) JLONG_TO_POINTER(#2);");
    s_in.put(s_string,    "#1 = sidl_Java_J2I_string(env, #2);");
    s_in.put(s_enum,      "#1 = (#3) #2;");
    s_in.put(s_class,     "#1 = (#3) sidl_Java_J2I_cls(env, #2, FALSE);JAVA_CHECK(env);");
    s_in.put(s_interface, "#1 = (#3) sidl_Java_J2I_ifc(env, #2, \"#4\", FALSE);JAVA_CHECK(env);");
    s_in.put(s_array,     "#1 = (#3) sidl_Java_J2I_borrow_array(env, #2);");

    s_inout.put(s_boolean,   "#1 = sidl_Java_J2I_boolean_holder(env, #2);");
    s_inout.put(s_char,      "#1 = sidl_Java_J2I_character_holder(env, #2);");
    s_inout.put(s_dcomplex,  "#1 = sidl_Java_J2I_dcomplex_holder(env, #2);");
    s_inout.put(s_double,    "#1 = sidl_Java_J2I_double_holder(env, #2);");
    s_inout.put(s_fcomplex,  "#1 = sidl_Java_J2I_fcomplex_holder(env, #2);");
    s_inout.put(s_float,     "#1 = sidl_Java_J2I_float_holder(env, #2);");
    s_inout.put(s_int,       "#1 = sidl_Java_J2I_int_holder(env, #2);");
    s_inout.put(s_long,      "#1 = sidl_Java_J2I_long_holder(env, #2);");
    s_inout.put(s_opaque,    "#1 = sidl_Java_J2I_opaque_holder(env, #2);");
    s_inout.put(s_string,    "#1 = sidl_Java_J2I_string_holder(env, #2);");
    s_inout.put(s_enum,      "#1 = (#3) sidl_Java_J2I_long_holder(env, #2);");
    s_inout.put(s_class,     "#1 = (#3) sidl_Java_J2I_cls_holder(env, #2, "
                             + "\"#4\", TRUE);JAVA_CHECK(env);");
    s_inout.put(s_interface, "#1 = (#3) sidl_Java_J2I_ifc_holder(env, #2, "
                             + "\"#4\", TRUE);JAVA_CHECK(env);");
    s_inout.put(s_array,     "#1 = (#3) sidl_Java_J2I_array_holder(env, #2, "
                             + "\"#4\");");

    s_post.put(s_boolean,   "sidl_Java_I2J_boolean_holder(env, #2, #1);");
    s_post.put(s_char,      "sidl_Java_I2J_character_holder(env, #2, #1);");
    s_post.put(s_dcomplex,  "sidl_Java_I2J_dcomplex_holder(env, #2, &#1);");
    s_post.put(s_double,    "sidl_Java_I2J_double_holder(env, #2, #1);");
    s_post.put(s_fcomplex,  "sidl_Java_I2J_fcomplex_holder(env, #2, &#1);");
    s_post.put(s_float,     "sidl_Java_I2J_float_holder(env, #2, #1);");
    s_post.put(s_int,       "sidl_Java_I2J_int_holder(env, #2, #1);");
    s_post.put(s_long,      "sidl_Java_I2J_long_holder(env, #2, #1);");
    s_post.put(s_opaque,    "sidl_Java_I2J_opaque_holder(env, #2, #1);");
    s_post.put(s_string,    "sidl_Java_I2J_string_holder(env, #2, #1);");
    s_post.put(s_enum,      "sidl_Java_I2J_long_holder(env, #2, (int) #1);");
    s_post.put(s_class,     "sidl_Java_I2J_cls_holder(env, #2, #1, \"#4\", FALSE);JAVA_CHECK(env);");
    s_post.put(s_interface, "sidl_Java_I2J_ifc_holder(env, #2, #1, \"#4\", FALSE);JAVA_CHECK(env);");
    s_post.put(s_array,     "#1 = sidl_Java_I2J_new_array(env, #2, \"#4\");");
    //sidl_Java_I2J_set_array(env, #2, #1);");

    s_return.put(s_boolean,   "#1 = (#3) #2;");
    s_return.put(s_char,      "#1 = (#3) #2;");
    s_return.put(s_dcomplex,  "#1 = sidl_Java_I2J_dcomplex(env, &#2);");
    s_return.put(s_double,    "#1 = (#3) #2;");
    s_return.put(s_fcomplex,  "#1 = sidl_Java_I2J_fcomplex(env, &#2);");
    s_return.put(s_float,     "#1 = (#3) #2;");
    s_return.put(s_int,       "#1 = (#3) #2;");
    s_return.put(s_long,      "#1 = (#3) #2;");
    s_return.put(s_opaque,    "#1 = (#3) POINTER_TO_JLONG(#2);");
    s_return.put(s_string,    "#1 = sidl_Java_I2J_string(env, #2);");
    s_return.put(s_enum,      "#1 = (#3) #2;");
    s_return.put(s_class,     "#1 = sidl_Java_I2J_cls(env, #2, \"#4\", FALSE);JAVA_CHECK(env);");
    s_return.put(s_interface, "#1 = sidl_Java_I2J_ifc(env, #2, \"#4\", FALSE);JAVA_CHECK(env);");
    s_return.put(s_array,     "#1 = sidl_Java_I2J_new_array(env, #2, \"#4\");");
    
    s_server_in.put(s_boolean,   "#1 = (#3) #2;");
    s_server_in.put(s_char,      "#1 = (#3) #2;");
    s_server_in.put(s_dcomplex,  "#1 = sidl_Java_I2J_dcomplex(env, &#2);");
    s_server_in.put(s_double,    "#1 = (#3) #2;");
    s_server_in.put(s_fcomplex,  "#1 = sidl_Java_I2J_fcomplex(env, &#2);");
    s_server_in.put(s_float,     "#1 = (#3) #2;");
    s_server_in.put(s_int,       "#1 = (#3) #2;");
    s_server_in.put(s_long,      "#1 = (#3) #2;");
    s_server_in.put(s_opaque,    "#1 = (#3) POINTER_TO_JLONG(#2);");
    s_server_in.put(s_string,    "#1 = sidl_Java_I2J_string(env, #2);");
    s_server_in.put(s_enum,      "#1 = (#3) #2;");
    s_server_in.put(s_class,     "#1 = sidl_Java_I2J_cls(env, #2, \"#4\", TRUE)"
                                 + ";JAVA_CHECK(env);");
    s_server_in.put(s_interface, "#1 = sidl_Java_I2J_ifc(env, #2, \"#4\", TRUE)"
                                 + ";JAVA_CHECK(env);");
    s_server_in.put(s_array,     "#1 = sidl_Java_I2J_new_array_server(env"
                                 + ", #2, \"#4\");");

    s_server_out.put(s_boolean,   "#1 = sidl_Java_create_empty_class(env,"
                                  + " \"sidl.Boolean$Holder\");");
    s_server_out.put(s_char,      "#1 = sidl_Java_create_empty_class(env,"
                                  + " \"sidl.Character$Holder\");");
    s_server_out.put(s_dcomplex,  "#1 = sidl_Java_create_empty_class(env,"
                                  + " \"sidl.DoubleComplex$Holder\");");
    s_server_out.put(s_double,    "#1 = sidl_Java_create_empty_class(env,"
                                  + " \"sidl.Double$Holder\");");
    s_server_out.put(s_fcomplex,  "#1 = sidl_Java_create_empty_class(env,"
                                  + " \"sidl.FloatComplex$Holder\");");
    s_server_out.put(s_float,     "#1 = sidl_Java_create_empty_class(env,"
                                  + " \"sidl.Float$Holder\");");
    s_server_out.put(s_int,       "#1 = sidl_Java_create_empty_class(env,"
                                  + " \"sidl.Integer$Holder\");");
    s_server_out.put(s_long,      "#1 = sidl_Java_create_empty_class(env,"
                                  + " \"sidl.Long$Holder\");");
    s_server_out.put(s_opaque,    "#1 = sidl_Java_create_empty_class(env,"
                                  + " \"sidl.Opaque$Holder\");");
    s_server_out.put(s_string,    "#1 = sidl_Java_create_empty_class(env,"
                                  + " \"sidl.String$Holder\");");
    s_server_out.put(s_enum,      "#1 = sidl_Java_create_empty_class(env,"
                                  + " \"sidl.Enum$Holder\");");
    s_server_out.put(s_class,     "#1 = sidl_Java_create_empty_class(env,"
                                  + " \"#4$Holder\");");
    s_server_out.put(s_interface, "#1 = sidl_Java_create_empty_class(env,"
                                  + " \"#4$Holder\");");
    s_server_out.put(s_array,     "#1 = sidl_Java_create_empty_class(env,"
                                  + " \"#4$Holder\");");

    s_server_inout.put(s_boolean,   "sidl_Java_I2J_boolean_holder(env, #1,"
                                    + " *#2);");
    s_server_inout.put(s_char,      "sidl_Java_I2J_character_holder(env, #"
                                    + "1, *#2);");
    s_server_inout.put(s_dcomplex,  "sidl_Java_I2J_dcomplex_holder(env, #1"
                                    + ", #2);");
    s_server_inout.put(s_double,    "sidl_Java_I2J_double_holder(env, #1, "
                                    + "*#2);");
    s_server_inout.put(s_fcomplex,  "sidl_Java_I2J_fcomplex_holder(env, #1"
                                    + ", #2);");
    s_server_inout.put(s_float,     "sidl_Java_I2J_float_holder(env, #1, *"
                                    + "#2);");
    s_server_inout.put(s_int,       "sidl_Java_I2J_int_holder(env, #1, *#2"
                                    + ");");
    s_server_inout.put(s_long,      "sidl_Java_I2J_long_holder(env, #1, *#"
                                    + "2);");
    s_server_inout.put(s_opaque,    "sidl_Java_I2J_opaque_holder(env, #1, "
                                    + "*#2);");
    s_server_inout.put(s_string,    "sidl_Java_I2J_string_holder(env, #1, "
                                    + "*#2);");
    s_server_inout.put(s_enum,      "sidl_Java_I2J_long_holder(env, #1,(int"
                                    + ") *#2);");
    s_server_inout.put(s_class,     "sidl_Java_I2J_cls_holder(env, #1, *#2"
                                    + ", \"#4\", FALSE);JAVA_CHECK(env);");
    s_server_inout.put(s_interface, "sidl_Java_I2J_ifc_holder(env, #1, *#2"
                                    + ", \"#4\", FALSE);JAVA_CHECK(env);");
    s_server_inout.put(s_array,     "sidl_Java_I2J_array_holder(env, #1, #"
                                    + "2, \"#4\");");

    s_server_post.put(s_boolean,   "*#1 = sidl_Java_J2I_boolean_holder(env"
                                   + ", #2);");
    s_server_post.put(s_char,      "*#1 = sidl_Java_J2I_character_holder(e"
                                   + "nv, #2);");
    s_server_post.put(s_dcomplex,  "*#1 = sidl_Java_J2I_dcomplex_holder(en"
                                   + "v, #2);");
    s_server_post.put(s_double,    "*#1 = sidl_Java_J2I_double_holder(env,"
                                   + " #2);");
    s_server_post.put(s_fcomplex,  "*#1 = sidl_Java_J2I_fcomplex_holder(en"
                                   + "v, #2);");
    s_server_post.put(s_float,     "*#1 = sidl_Java_J2I_float_holder(env, "
                                   + "#2);");
    s_server_post.put(s_int,       "*#1 = sidl_Java_J2I_int_holder(env, #2"
                                   + ");");
    s_server_post.put(s_long,      "*#1 = sidl_Java_J2I_long_holder(env, #"
                                   + "2);");
    s_server_post.put(s_opaque,    "*#1 = sidl_Java_J2I_opaque_holder(env,"
                                   + " #2);");
    s_server_post.put(s_string,    "*#1 = sidl_Java_J2I_string_holder(env,"
                                   + " #2);");
    s_server_post.put(s_enum,      "*#1 = (#3) sidl_Java_J2I_long_holder(en"
                                   + "v, #2);");
    s_server_post.put(s_class,     "*#1 = (#3) sidl_Java_J2I_cls_holder(en"
                                   + "v, #2, \"#4\", TRUE);JAVA_CHECK(env);");
    s_server_post.put(s_interface, "*#1 = (#3) sidl_Java_J2I_ifc_holder(en"
                                   + "v, #2, \"#4\", TRUE);JAVA_CHECK(env);");
    s_server_post.put(s_array,     "#1 = (#3) sidl_Java_J2I_array_holder(e"
                                   + "nv, #2, \"#4\");");

    s_server_return.put(s_boolean,   "#1 = ( (#2) ? TRUE : FALSE);");
    s_server_return.put(s_char,      "#1 = (#3) #2;");
    s_server_return.put(s_dcomplex,  "#1 = sidl_Java_J2I_dcomplex(env, #2)"
                                     + ";");
    s_server_return.put(s_double,    "#1 = (#3) #2;");
    s_server_return.put(s_fcomplex,  "#1 = sidl_Java_J2I_fcomplex(env, #2)"
                                     + ";");
    s_server_return.put(s_float,     "#1 = (#3) #2;");
    s_server_return.put(s_int,       "#1 = (#3) #2;");
    s_server_return.put(s_long,      "#1 = (#3) #2;");
    s_server_return.put(s_opaque,    "#1 = (#3) JLONG_TO_POINTER(#2);");
    s_server_return.put(s_string,    "#1 = sidl_Java_J2I_string(env, #2)"
                                     + ";");
    s_server_return.put(s_enum,      "#1 = (#3) #2;");
    s_server_return.put(s_class,     "#1 = (#3) sidl_Java_J2I_cls(env, #2"
                                     + ", TRUE);JAVA_CHECK(env);");
    s_server_return.put(s_interface, "#1 = (#3) sidl_Java_J2I_ifc(env, #2"
                                     + ", \"#4\", TRUE);JAVA_CHECK(env);");
    s_server_return.put(s_array,     "#1 = (#3) sidl_Java_J2I_take_array("
                                     + "env, #2);");

  }

  /**
   * Generate the Java filename for the client using the specified symbol
   * identifier.  Simply append the suffix ".java" to the symbol name.
   */
  public static String getClientJavaFile(SymbolID id) {
    return id.getShortName() + ".java";
  }

  /**
   * Generate the JNI source filename for the client using the specified
   * symbol identifier. Simply append the suffix "_jniStub.c" to the symbol
   * name.
   */
  public static String getClientJNIFile(SymbolID id) {
    return id.getFullName().replace('.', '_') + "_jniStub.c";
  }

  /**
   * Generate the JNI source filename for the client using the specified
   * symbol identifier. Simply append the suffix "_jniStub.h" to the symbol
   * name.
   */
  public static String getHeaderFile(SymbolID id) {
    return id.getFullName().replace('.', '_') + "_jniStub.h";
  }

  /**
   * Generate the C utility filename for the client using the specified
   * symbol identifier. 
   */
  public static String getUtilitySourceFile(SymbolID id) {
    return getClientJNIFile(id);
  }

  /**
   * Generate the utility header filename for the client using the specified
   * symbol identifier. 
   */
  public static String getUtilityHeaderFile(SymbolID id) {
    return getHeaderFile(id);
  }
  
  /**
   * Generate the Java filename for the server using the specified symbol
   * identifier.  Simply append the suffix ".java" to the symbol name.
   */
  public static String getJavaImplSourceFile(SymbolID id) {
    return id.getShortName() +"_Impl.java";
  }

  /**
   * Generate the JNI source filename for the server using the specified
   * symbol identifier. Simply append the suffix "_jniSkel.c" to the symbol
   * name.
   */
  public static String getServerJNIFile(SymbolID id) {
    return id.getFullName().replace('.', '_') + "_jniSkel.c";
  }

  /**
   * Generate the Java filename for the client using the specified symbol
   * identifier.  Simply append the suffix ".java" to the symbol name.
   */
  public static String getStructJavaFile(SymbolID id) {
    return id.getShortName() + ".java";
  }
    
  /**
   * Return the shortened Java name of a sidl symbol type.  The Java name is
   * the same as the sidl name without the namespace information.  This name is
   * the one used when declaring the Java type.  The fully qualified name is
   * returned by <code>getFullJavaSymbolName</code>.
   */
  public static String getJavaSymbolName(SymbolID id) {
    return id.getShortName();
  }

  /**
   * Return the fully qualified Java name that corresponds to a sidl symbol
   * type.  This name is the same as the sidl name, including all package
   * information.
   */
  public static String getFullJavaSymbolName(SymbolID id) {
    return id.getFullName();
  }

  /**
   *  Return the name of the java server class.  This name is the same as the
   *  client side class name + "_Impl"
   */
  public static String getJavaServerClassName(SymbolID id) {
    return id.getShortName() + "_Impl";
  }

  /**
   *  Return the name of Java Server Methods.  These are the same as the 
   *  names of the methods on the client side + "_Impl"
   */
  public static String getJavaServerMethodName(Method meth) {
    return meth.getLongMethodName() + "_Impl";
  }

  /**
   * Return the name of the JNI registration function.  The registration
   * function is the one invoked whenever a new Java class or interface is
   * loaded.
   */
  public static String getRegisterFunction(SymbolID id) {
    return id.getFullName().replace('.', '_') + "__register";
  }

  /**
   * Return the string name of the Java base class that all automatically
   * generated sidl classes must extend.
   */
  public static String getJavaBaseClass() {
    return "gov.llnl.sidl.BaseClass";
  }

  /**
   * Return the string name of the Java base interface that all automatically
   * generated sidl interfaces must extend.
   */
  public static String getJavaBaseInterface() {
    return "gov.llnl.sidl.BaseInterface";
  }

  /**
   * Return the string name of the Java base array that all automatically
   * generated sidl arrays must extend.
   */
  public static String getJavaBaseArray() {
    return "gov.llnl.sidl.BaseArray";
  }

  /**
   * Return the name of the inner wrapper class for interfaces.
   */
  public static String getInterfaceWrapper() {
    return "Wrapper";
  }

  /**
   * Return the name of the inner holder class used for inout and out
   * method arguments.
   */
  public static String getHolderName() {
    return "Holder";
  }

  /**
   *  Returns a string for the name of the ensure function to call for this
   *  array type.  Used in jniSkel files when an array is defined by the
   *  sidl file to be either column or row major order.
   *
   */
  public static String getEnsureArray(Type arrayType) {
    switch(arrayType.getDetailedType()) {
    case Type.ENUM:
      return "sidl_long__array_ensure";
    case Type.CLASS:
    case Type.INTERFACE:
      return "sidl_interface__array_ensure";
    default:
      return "sidl_" + arrayType.getTypeString() + "__array_ensure";
    }
  }
    
  /**
   * Return the Java type string corresponding to a sidl array.  Array types
   * for primitives are represented in a hash table.  All other array types
   * are the name of the symbol with the "Array#" inner class, where the "#"
   * is the array dimension.
   */
  private static String getJavaArrayType(int dim, Type type) {
    if (null != type) {
      String val = (String)s_java_arr.get(new Integer(type.getDetailedType()));
      if (val == null) {
        val = getFullJavaSymbolName(type.getSymbolID()) + "$Array#"; 
      }
      return val.replace('#', Character.forDigit(dim, 10));
    } else {
      return "gov.llnl.sidl.BaseArray";
    }
  }

  /**
   * Return a string for the Java return type corresponding to the specified
   * sidl type.  This method retains the "$" in the type string for inner
   * classes.  The mapping between sidl types and Java types is fairly simple
   * for most primitive types and objects.
   */
  public static String getJavaInternalReturnType(Type type) {
    int t = type.getDetailedType();
    String val = (String) s_java_val.get(new Integer(t));
    if (val == null) {
      if ((t == Type.CLASS) || (t == Type.INTERFACE) || (t == Type.STRUCT)) {
        val = getFullJavaSymbolName(type.getSymbolID());
      } else if (t == Type.ARRAY) {
        val = getJavaArrayType(type.getArrayDimension(), type.getArrayType());
      }
    }
    return val;
  }

  /**
   * Return a string for the Java return type corresponding to the specified
   * sidl type.  The return string from this routine is a valid Java type.
   * The mapping between sidl type and Java type is fairly simple for most
   * primitive types and object types.
   */
  public static String getJavaReturnType(Type type) {
    return getJavaInternalReturnType(type).replace('$', '.');
  }



  /**
   * Return a string for the Java type corresponding to the specified
   * field. The return string from this routine is a valid Java type.
   * The mapping between sidl type and Java type is fairly simple for most
   * primitive types and object types. 
   * @param f the field to be considered
   * @param internal if true, inner classes are separated by "$" instead of "."
   */
  public static String getJavaFieldType(Struct.Item f, boolean internal) {
    Type type  = f.getType();
    int  t     = type.getDetailedType();

    String val = (String) s_java_val.get(new Integer(t));
    if (val == null) {
      if ((t == Type.CLASS) || (t == Type.INTERFACE) || (t == Type.STRUCT)) {
        val = getFullJavaSymbolName(type.getSymbolID());
      } else if (t == Type.ARRAY) {
        val = getJavaArrayType(type.getArrayDimension(), type.getArrayType());
      }
    }
    
    if(internal)
      return val;
    else
      return val.replace('$', '.');
  }
  
  /**
   * Return a string for the Java type corresponding to the specified
   * field. The return string from this routine is a valid Java type.
   * The mapping between sidl type and Java type is fairly simple for most
   * primitive types and object types.
   */
  public static String getJavaFieldType(Struct.Item f) {
    return getJavaFieldType(f, false);
  }
  
  /**
   * Convert a type string to a JNI descriptor.  Convert the basic types
   * according to the hash table map.  If the type is not one of the basic
   * types, then convert the symbol type according to JNI conventions.
   */
  public static String getDescriptor(String type) {
    String jni = (String) s_java_sig.get(type);
    if (jni == null) {
      jni = "L" + type.replace('.', '/') + ";";
    }
    return jni;
  }

  /**
   * Return a string for the Java argument corresponding to the specified
   * sidl argument.  The mapping between sidl argument and Java argument is
   * fairly simple for most primitive types and object types.  For arguments
   * that are IN, the type is the same as the return type.  For INOUT and OUT
   * arguments, the type is the special holder class.  This routine retains
   * the "$" for inner classes.
   */
  private static String getJavaServerInternalArgument(Argument arg) {
    String  val  = null;
    Type    type = arg.getType();
    int     t    = type.getDetailedType();
    int     m    = arg.getMode();
    Integer T    = new Integer(t);

    if(type.isArray()) {         //Is it an array?
      if(type.isGenericArray()) {  //is it a generic array?
        if(m == Argument.IN) {    //Is it an in arg?
          return getJavaBaseArray();  //Generic array in
        } else {
          return getJavaBaseArray() + "$Holder"; //generic array inout/out
        }
      } else { //It's not a generic array 
        if(m == Argument.IN) {  //getJavaArrayType takes care of both object
                                //a type arrays
          return getJavaArrayType(type.getArrayDimension(), 
                                  type.getArrayType()); //type array in
        } else {
          return getJavaArrayType(type.getArrayDimension(), type.getArrayType())
                   + "$Holder";
        }
      }
    } else {  // It's NOT an array
        if ((t == Type.CLASS) || (t == Type.INTERFACE) || (t == Type.STRUCT)) { 
          if (m == Argument.IN) { //object in
            val = getFullJavaSymbolName(arg.getType().getSymbolID());
          } else {
            val = getFullJavaSymbolName(arg.getType().getSymbolID()) 
                    + "$Holder";
          }
        } else {
          if (m == Argument.IN) {
            val = (String) s_java_val.get(T);  //basic type in
          } else {
            val = (String) s_java_ref.get(T);  //basic type in out
          }
        }
      }
      
      return val;
  }

  /**
   * Return a string for the Java argument corresponding to the specified
   * sidl argument.  The mapping between sidl argument and Java argument is
   * fairly simple for most primitive types and object types.  For arguments
   * that are IN, the type is the same as the return type.  For INOUT and OUT
   * arguments, the type is the special holder class.  This routine retains
   * the "$" for inner classes.
   */
  private static String getJavaInternalArgument(Argument arg) {
    String  val  = null;
    Type    type = arg.getType();
    int     t    = type.getDetailedType();
    int     m    = arg.getMode();
    Integer T    = new Integer(t);
    
    val = (String)((m == Argument.IN) ? s_java_val.get(T) : s_java_ref.get(T));
    if (val == null) {
      if ((t == Type.CLASS) || (t == Type.INTERFACE) || (t == Type.STRUCT)) {
        val = getFullJavaSymbolName(arg.getType().getSymbolID());
        if (m != Argument.IN) {
          val = val + "$" + Java.getHolderName();
        }
      } else if (t == Type.ARRAY) {
        val = getJavaArrayType(type.getArrayDimension(), type.getArrayType());
        if(m != Argument.IN)  //Out and INOUT args need to be passed in holders
          val = val + "$" + Java.getHolderName();
      }
    }

    return val;
  }

  /**
   * Return a string for the Java argument corresponding to the specified
   * sidl argument.  The mapping between sidl argument and Java argument is
   * fairly simple for most primitive types and object types.  For arguments
   * that are IN, the type is the same as the return type.  For INOUT and OUT
   * arguments, the type is the special holder class.  This routine removes
   * the "$" for inner classes and replaces it with a ".".
   */
  public static String getJavaArgument(Argument arg) {
    return getJavaInternalArgument(arg).replace('$', '.');
  }

   public static String getJavaServerArgument(Argument arg) {
    return getJavaServerInternalArgument(arg).replace('$', '.');
  }

  /**
   * Return a string for the Java argument corresponding to the specified
   * sidl argument with a formal name.
   */
  public static String getJavaFormalArgument(Argument arg) {
    return getJavaArgument(arg) + " " + arg.getFormalName();
  }

  /**
   * Convert the method argument list and return type into a Java signature
   * string according to JNI conventions.  See any JNI reference for the type
   * mapping.
   */
  public static String getJavaSignature(Method method) {
    StringBuffer buffer = new StringBuffer();
    buffer.append("(");
    for (Iterator a = method.getArgumentList().iterator(); a.hasNext(); ) {
      Argument arg = (Argument) a.next();
      buffer.append(getDescriptor(getJavaInternalArgument(arg)));
    }
    buffer.append(")");
    Type type = method.getReturnType();
    buffer.append(getDescriptor(getJavaInternalReturnType(type)));
    return buffer.toString();
  }

  /**
   * Convert the method argument list and return type into a Java signature
   * string according to JNI conventions.  See any JNI reference for the type
   * mapping.
   */
  public static String getJavaServerSignature(Method method) {
    StringBuffer buffer = new StringBuffer();
    buffer.append("(");
    for (Iterator a = method.getArgumentList().iterator(); a.hasNext(); ) {
      Argument arg = (Argument) a.next();
      buffer.append(getDescriptor(getJavaServerInternalArgument(arg)));
    }
    buffer.append(")");
    Type type = method.getReturnType();
    buffer.append(getDescriptor(getJavaInternalReturnType(type)));
    return buffer.toString();
  }

  /**
   * Generate a string that will print a default return value (Java) for 
   * given method
   * @param method the method that needs a default return string
   */
  public static String getDefaultReturnValue ( Method method ) {
    // Default return for "objects"
    if (method.getReturnType().getType() >= Type.OPAQUE ||
        method.getReturnType().getType() == Type.FCOMPLEX ||
        method.getReturnType().getType() == Type.DCOMPLEX) {
      return "null";
    } else if (method.getReturnType().getType() > Type.CHAR && 
             method.getReturnType().getType() < Type.OPAQUE) {
      // Default return for numeric types
      return "0";
    } else if (method.getReturnType().getType() == Type.CHAR ){
      // Default return for char
      return "'\\0'";
    } else if (method.getReturnType().getType() == Type.BOOLEAN) {
      // Default return for boolean
      return "false";
    } else {
      // For void or undefined
      return "";
    }
  }

  /**
   * Generate a string that will print a default return value (JNI) for 
   * given method
   * @param method the method that needs a default return string
   */
  public static String getDefaultJNIReturnValue ( Method method ) {
    // Default return for "objects"
    if (method.getReturnType().getDetailedType() == Type.ENUM) {
      return "0";
    } else if (method.getReturnType().getDetailedType() >= Type.OPAQUE) {
      return "NULL";
    } else if (  method.getReturnType().getDetailedType() == Type.FCOMPLEX 
              || method.getReturnType().getDetailedType() == Type.DCOMPLEX) {
      // Default return value for fcomplex and dcomplex
      // Structs should be handled differently ??
      return "_retval";
    } else if (  method.getReturnType().getDetailedType() > Type.CHAR 
              && method.getReturnType().getDetailedType() < Type.OPAQUE) {
      // Default return for numeric types
      return "0";
    } else if (method.getReturnType().getDetailedType() == Type.CHAR ){
      // Default return for char
      return "'\\0'";
    } else if (method.getReturnType().getDetailedType() == Type.BOOLEAN) {
      // Default return for boolean
      return "0";
    } else {
      // For void or undefined
      return "";
    }
  }
    
  /**
   * Return the name of the JNI function corresponding to the specified sidl
   * method.  These names are the static local names used in the JNI stub file.
   */
  public static String getJNIFunction(Method method) {
    return "jni_" + method.getLongMethodName();
  }

  /**
   * Return the name of the JNI function corresponding to the specified sidl
   * method.  These names are the static local names used in the JNI stub file.
   */
  public static String getSuperJNIFunction(Method method) {
    return "jni_super_" + method.getLongMethodName();
  }

  /**
   * Return a string for the JNI native type corresponding to the specified
   * Java type.  Everything that is not a primitive type like integer or
   * boolean is converted into an JNI object.
   */
  public static String getJNINativeType(String type) {
    String jni = (String) s_jni_arg.get(type);
    if (jni == null) {
      jni = "jobject";
    }
    return jni;
  }

  /**
   * Return a string for the JNI field accessor corresponding to the specified
   * Java type.  Everything that is not a primitive type like integer or
   * boolean is accessed using the GetObjectField method.
   */
  public static String getJNIFieldAccessor(String type) {
    String jni = (String) s_jni_meth.get(type);
    if (jni == null) {
      jni = "GetObjectField";
    }
    return jni;
  }

  /**
   * Return a string for the JNI native type corresponding to the specified
   * Java return type.  This method calls <code>getJavaReturnType</code> and
   * converts the resulting strings into its JNI representation.
   */
  public static String getJNIReturnType(Type type) {
    return getJNINativeType(getJavaReturnType(type));
  }

  /**
   * Declare and initialize a variable with an IOR type.  This method converts
   * the sidl type into an IOR type string and substitutes the type string in
   * the initialization phrase from the initialization hash table.
   */
  public static void declareIORVariable(LanguageWriterForC writer, 
                                        Type type, 
                                        String variable,
                                        Context context)
    throws CodeGenerationException 
  {
    printSub(writer, 
             (String)s_init_ior.get(new Integer(type.getDetailedType())),
             getReturnString(type, context), variable);
  }

  /**
   * Declare and initialize a variable with an IOR type.  This method converts
   * the sidl type into an IOR type string and substitutes the type string in
   * the initialization phrase from the initialization hash table. (In JNI)
   */
  public static void declareIORVariable(LanguageWriterForC writer, 
                                        Argument arg,
                                        String prefix,
                                        Context context)
    throws CodeGenerationException 
  {
    Type type = arg.getType();
    printSub(writer, 
             (String)s_init_ior.get(new Integer(type.getDetailedType())),
             getReturnString(type, context), prefix + arg.getFormalName());
  }

  /**
   * Declare and initialize a variable with a Java type.  This method converts
   * the sidl type into a Java type string and substitutes the type string in
   * the initialization phrase from the initialization hash table.  (IN JNI)
   */
  public static void declareJavaVariable(LanguageWriterForC writer, Type type, 
                                         String variable) 
  {
    String rtype = Java.getJNIReturnType(type);
    printSub(writer, (String) s_init_jni.get(rtype), rtype, variable);
  }

  /**
   * Declare and initialize a variable with a Java type.
   */
  public static void declareJavaVariable(LanguageWriterForJava writer, 
                                         Type type, String variable) 
  {
    String rtype = type.getTypeString();
    printSub(writer, (String) s_init_java.get(rtype), rtype, variable);
  }

  /**
   * This is a quick modification of declareJavaVariable (above).
   * Basically, in the Server side JNI all INOUT/OUT variables are
   * Passed in holders, which are all jobjects.  Hence, no need for the 
   * usual hash map approach used above.
   */
  public static void declareServerInOutVariable(LanguageWriterForC writer, 
                                                Type type, String variable) 
  {
    printSub(writer, "#1 #2 = (#1) NULL;", "jobject", variable);
  }

  /**
   * Return a string for the JNI argument corresponding to the specified
   * sidl argument.  This method converts the sidl argument into a Java
   * argument and then converts the Java argument into its JNI native
   * representation.  The formal argument name is prepended with an
   * "_arg_" to prevent name collisions with other argument types.
   */
  public static String getJNIFormalArgument(Argument arg) {
    return getJNINativeType(getJavaArgument(arg)) + " _arg_" 
             + arg.getFormalName();
  }

  /** 
   * Return the name of the given argument temporary variable for use in
   * calls to ensure.   
   */
  public static String getJNIEnsureName(Argument arg) {
    return "_ensure_" + arg.getFormalName();
  }

  /** 
   * Return the name of the given argument temporary variable for use in
   * calls to ensure.   
   */
  public static String getJNIEnsureName(String name) {
    return "_ensure_" + name;
  }

  /**
   * Method <code>preprocessJNIArgument</code> converts between Java arguments
   * and IOR arguments.  Conversion routines are output to the language writer.
   * This routine retrieves the conversion string from the appropriate hash
   * table and then substitutes the appropriate variable names in the conversion
   * string.
   */
  public static void preprocessJNIArgument(LanguageWriterForC writer, 
                                           Argument arg,
                                           String prefix, 
                                           Context context)
    throws CodeGenerationException 
  {
    Type type = arg.getType();
    int mode = arg.getMode();
    String formalName = arg.getFormalName();
    
    if(type.isStruct()) {
      if (mode == Argument.IN) {
        writer.println(JavaStructSource.getJ2IFunctionName(type.getSymbolID()) + 
                       "(env, _arg_" + formalName +
                       ", &" + prefix + formalName +
                       ", " + mode + ", TRUE /* is_client */);");
      }
      else if (mode == Argument.INOUT) {
        writer.println(JavaStructSource.getJ2IHolderFunctionName(type.getSymbolID()) +
                       "(env, _arg_" + formalName + ", &" +
                       prefix + formalName +
                       ", " + mode + ", TRUE /* is_client */);");
      }
    }
    else {
      String convert = null;
      Type arrayType = type.getArrayType();
      Integer arrayTypeInteger = null;
      String arrayTypeString = null;
      Integer detail = new Integer(type.getDetailedType());
      if(type.isArray()) {
        if (null != arrayType) {
          arrayTypeInteger = new Integer(arrayType.getDetailedType());
          
          arrayTypeString = 
            (arrayType.getDetailedType() == Type.CLASS ||
             arrayType.getDetailedType() == Type.INTERFACE)
            ? arrayType.getSymbolID().getFullName() 
            : ((String)s_java_arr.get(arrayTypeInteger)).
            replace('#', Character.forDigit(type.getArrayDimension(), 10));
        } else {
          arrayTypeString = "gov.llnl.sidl.BaseArray";
        }
      }

      if (mode == Argument.IN) {
        convert = (String) s_in.get(detail);
      } else if (mode == Argument.INOUT) {
        convert = (String) s_inout.get(detail);
      }

      if (convert != null) {
        printSub(writer, convert, prefix + formalName, 
                 "_arg_" + formalName, 
                 getReturnString(type, context),
                 type.isSymbol() ? type.getSymbolID().getFullName() 
                 : arrayTypeString);
      }
    }
  }

  /**
   * Method <code>postprocessJNIArgument</code> converts between IOR arguments
   * and Java arguments.  Conversion routines are output to the language writer.
   * This routine retrieves the conversion string from the hash table and then
   * substitutes the appropriate variable names in the conversion string.
   */
  public static void postprocessJNIArgument(LanguageWriterForC writer, 
                                            Argument arg,
                                            String prefix,
                                            Context context)
    throws CodeGenerationException 
  {
    Type type = arg.getType();
    int mode = arg.getMode();
    String formalName = arg.getFormalName();

    if (mode != Argument.IN) {
      if(type.isArray()) {
        /* We get to kind of cheat here.  Since we know the argument is
         * out/inout, and it's an array, we can do away with some 
         * pesky logic required in the other cases
         */
  
        String convert = (String) s_post.get(new Integer(
                                                         type.getDetailedType()));
        Type arrayType = type.getArrayType();
        Integer arrayTypeInteger = null;
        String arrayTypeString = null;
        if (null != arrayType) {
          arrayTypeInteger = new Integer(arrayType.getDetailedType());
          
          arrayTypeString = 
            (arrayType.getDetailedType() == Type.CLASS ||
             arrayType.getDetailedType() == Type.INTERFACE)
            ? arrayType.getSymbolID().getFullName() 
            : ((String)s_java_arr.get(arrayTypeInteger)).
            replace('#', Character.forDigit(type.getArrayDimension(), 10));
        } else {
          arrayTypeString = "gov.llnl.sidl.BaseArray";
        }

        convert = (String) "sidl_Java_I2J_array_holder(env, #1, #2, \"#4\");"; 
        //s_server_inout.get(new Integer(type.getDetailedType()));
        if (convert != null) {
          String fromName = "_arg_" + formalName;
          
          printSub(writer, convert, fromName, prefix + formalName,
                   getReturnString(type, context), 
                   type.isSymbol() ? type.getSymbolID().getFullName() 
                   : arrayTypeString);
        }
      } else if(type.isStruct()) {
        /* call the pregenerated conversion utility function */
        writer.println(JavaStructSource.getI2JHolderFunctionName(type.getSymbolID()) +
                       "(env, _arg_" + formalName + ", &" +
                       prefix + formalName +
                       ", " + mode + ", TRUE /* is_client */);");

        /* if there are raw arrays in the struct, we might have allocated
         * temporary memory that we have to release properly
         */
        if(JavaStructSource.requiresCleanup((Struct)type.getSymbolID()))
          writer.println(JavaStructSource.getCleanupFunctionName(type.getSymbolID()) +
                         "(&" + prefix + formalName + ");");
      }
      else { //if the argument is neither a struct nor an array
        
        String convert = (String) s_post.get(new Integer(type.getDetailedType()));
        if (convert != null) {
          printSub(writer, convert, prefix + formalName,
                   "_arg_" + formalName, 
                   getReturnString(type, context),
                   type.isSymbol() ? type.getSymbolID().getFullName() 
                   : null);
        }
      }
    }
    if (type.getType() == Type.STRING) {
      writer.println("sidl_String_free(" + prefix + formalName + ");");
    }
  }
  
  /**
   * Method <code>postprocessJNIReturn</code> converts between IOR return
   * arguments and Java return arguments.  Conversion routines are output
   * to the language writer.  This routine retrieves the conversion string
   * from the hash table and then substitutes the appropriate variable names
   * in the conversion string.
   */
  public static void postprocessJNIReturn(LanguageWriterForC writer, Type type,
                                          String ior_result, String java_result)
  {
    int t = type.getDetailedType();

    if(type.isStruct()) {
      writer.println(java_result + " = " +
                     JavaStructSource.getI2JFunctionName(type.getSymbolID()) +
                     "(env, &" + ior_result +
                     ", " + JavaStructSource.MODE_RET + ", TRUE /* is_client */);");
    }
    else {
      printSub(writer, (String) s_return.get(new Integer(t)), java_result, 
               ior_result, getJNIReturnType(type), 
               getJavaInternalReturnType(type));
      if (t == Type.STRING)
        writer.println("sidl_String_free(" + ior_result + ");");
    }
  }

  
  /**
   * Method <code>preprocessServerJNIArgument</code> converts IOR arguments to 
   * Java arguments.  Conversion routines are output to the language writer.
   * This routine retrieves the conversion string from the appropriate hash
   * table and then substitutes the appropriate variable names in the conversion
   * string.
   */
  public static void preprocessServerJNIArgument(LanguageWriterForC writer, 
                                                 Argument arg,
                                                 String prefix,
                                                 Context context)
    throws CodeGenerationException 
  {
    Type type = arg.getType();
    String typeName = type.isSymbol() ? type.getSymbolID().getFullName() : " ";
    int mode = arg.getMode();
    String formalName = arg.getFormalName();

    if(type.isStruct()) {
      if (mode == Argument.IN) {
        writer.println(prefix + formalName + " = " +
                       JavaStructSource.getI2JFunctionName(type.getSymbolID()) +
                       "(env, " + formalName +
                       ", " + mode + ", FALSE /* is_client */);");
      }
      else if (mode == Argument.INOUT) {
        //this creates a new (empty) holder class for out arguments
        writer.println(prefix + formalName + " = sidl_Java_create_empty_class(env, \"" +
                       getFullJavaSymbolName(type.getSymbolID()) +
                       "$Holder\");");
        writer.println(JavaStructSource.getI2JHolderFunctionName(type.getSymbolID()) +
                       "(env, " + prefix + formalName + ", " + formalName +
                       ", " + mode + ", FALSE /* is_client */);");
      }
      else if (mode == Argument.OUT) {
        //this creates a new (empty) holder class for out arguments
        writer.println(prefix + formalName + " = sidl_Java_create_empty_class(env, \"" +
                       getFullJavaSymbolName(type.getSymbolID()) +
                       "$Holder\");");
      }
    }
    else {
      String convert = null;
      Integer detail = new Integer(type.getDetailedType());
      Type arrayType = type.getArrayType();
      Integer arrayTypeInteger = null;
      String arrayTypeString = null;

      String returnString  = getReturnString(type, context);

      if(type.isArray()) {
        if (null != arrayType) {
          arrayTypeInteger = new Integer(arrayType.getDetailedType());
      
          arrayTypeString = 
            (arrayType.getDetailedType() == Type.CLASS ||
             arrayType.getDetailedType() == Type.INTERFACE)
            ?  (arrayType.getSymbolID().getFullName() + "$Array" 
                + type.getArrayDimension()) 
            : ((String)s_java_arr.get(arrayTypeInteger)).
            replace('#', Character.forDigit(type.getArrayDimension(), 10));
        } else {
          arrayTypeString = "gov.llnl.sidl.BaseArray";
        }
      }
      if (mode == Argument.IN) {
        convert = (String) s_server_in.get(detail);
      } else if (mode == Argument.INOUT) {
        convert = (String) s_server_out.get(detail);
        if (convert != null) {
          printSub(writer, convert, prefix + formalName, formalName, 
                   returnString, type.isSymbol() ? typeName : arrayTypeString);
        }
        convert = (String) s_server_inout.get(detail);
      } else if (mode == Argument.OUT) {
        convert = (String) s_server_out.get(detail);
      }

      if (convert != null) {
        String fromName = null;
        if(type.hasArrayOrderSpec()) { //If it's an array and has an order
          fromName = Java.getJNIEnsureName(formalName); 
        } else if ( mode == Argument.IN 
                    || ((arrayType == null) && !type.isArray())) {
          //If it's an in argument or is not an array
          fromName = formalName;
        } else { //if it's an array and an out / inout argument
          fromName = "*" + formalName;
        }
     
        printSub(writer, convert, prefix + formalName, fromName, returnString,
                 type.isSymbol() ? typeName : arrayTypeString);
      }
   
      if (type.getType() == Type.STRING && mode == Argument.INOUT)
        writer.println("sidl_String_free(*"+ formalName + ");");
    }
  }

  
  private static boolean hasJavaObjectProxy(int mode,
                                            int type)
  {
    if (mode != Argument.IN) return true;
    switch(type){
    case Type.STRING:
    case Type.STRUCT:
    case Type.CLASS:
    case Type.INTERFACE:
    case Type.ARRAY:
      return true;
    default:
      return false;
    }
  }


  /**
   * Method <code>postprocessServerJNIArgument</code> converts Java arguments to
   * IOR arguments.  Conversion routines are output to the language writer.
   * This routine retrieves the conversion string from the hash table and then
   * substitutes the appropriate variable names in the conversion string.
   */
  public static void postprocessServerJNIArgument(LanguageWriterForC writer, 
                                                  Argument arg, 
                                                  String prefix,
                                                  Context context)
    throws CodeGenerationException 
  {
    Type type = arg.getType();
    int mode = arg.getMode();
    String formalName = arg.getFormalName();
    
    if (mode != Argument.IN) {
      Type arrayType = type.getArrayType();
      Integer arrayTypeInteger = null;
      String arrayTypeString = null;

      if(type.isStruct()) {
        writer.println(JavaStructSource.getJ2IHolderFunctionName(type.getSymbolID()) +
                       "(env, " + prefix + formalName + ", " + formalName +
                       ", " + mode + ", FALSE /* is_client */);");
      }
      else {
        if(type.isArray()) {
          if (null != arrayType) {
            arrayTypeInteger = new Integer(arrayType.getDetailedType());
        
            arrayTypeString = 
              (arrayType.getDetailedType() == Type.CLASS ||
               arrayType.getDetailedType() == Type.INTERFACE)
              ? arrayType.getSymbolID().getFullName() 
              : ((String)s_java_arr.get(arrayTypeInteger)).
              replace('#', Character.forDigit(type.getArrayDimension(), 10));
          } else {
            arrayTypeString = "gov.llnl.sidl.BaseArray";
          }
        }
        String convert = (String) s_server_post.get(new Integer(
                                                                type.getDetailedType()));
        if (convert != null) {
          String toName = null;
          if(type.hasArrayOrderSpec()) {//If it's an array and has an order
            toName = Java.getJNIEnsureName(formalName); 
          } else if(!type.isArray()) {
            //If it's not an array, just use the formal name + prefix
            toName = formalName;
          } else { //if it's an array and an out / inout argument
            toName = "*" + formalName;
          }

          printSub(writer, convert, toName, prefix + formalName, 
                   getReturnString(type, context),
                   type.isSymbol() ? type.getSymbolID().getFullName() 
                   : arrayTypeString);
        }
      }
    }
    
    if (hasJavaObjectProxy(mode, type.getDetailedType())) {
      if (type.isRarray()) {
        writer.println("sidl_Java_destroy_array(env, " + prefix + 
                       formalName + ");");
      }
      else {
        writer.println("(*env)->DeleteLocalRef(env, " + prefix +
                       formalName + ");");
      }
      writer.println("JAVA_CHECK(env);");
    }
  }

  
  /**
   * Method <code>postprocessServerJNIReturn</code> converts from java return
   * arguments to IOR return arguments.  Conversion routines are output
   * to the language writer.  This routine retrieves the conversion string
   * from the hash table and then substitutes the appropriate variable names
   * in the conversion string.
   */
  public static void postprocessServerJNIReturn(LanguageWriterForC writer, 
                                                Type type, 
                                                String java_result, 
                                                String ior_result,
                                                Context context) 
    throws CodeGenerationException
  {
    int t = type.getDetailedType();
    
    if(type.isStruct()) {
      writer.println(JavaStructSource.getJ2IFunctionName(type.getSymbolID()) + 
                     "(env, " + java_result + ", &" + ior_result +
                     ", " + JavaStructSource.MODE_RET + ", FALSE /* is_client */);");
    }
    else {
      printSub(writer, (String) s_server_return.get(new Integer(t)), ior_result, 
               java_result, IOR.getReturnString(type, context),
               getJavaInternalReturnType(type));
    }
       
    if (hasJavaObjectProxy(Argument.IN, t)) {
      writer.println("(*env)->DeleteLocalRef(env, " + java_result + ");");
      writer.println("JAVA_CHECK(env);");
    }
  }

  /**
   * Substitute string tokens of the form #n for two arguments.
   */
  private static String substitute(String s, String s0, String s1) {
    return substitute(s, new String[] { s0, s1 });
  }

  /**
   * Substitute string tokens of the form #n for four arguments.
   */
  private static String substitute(String s, String s0, String s1, String s2, 
                                   String s3) 
  {
    return substitute(s, new String[] { s0, s1, s2, s3 });
  }

  /**
   * Substitute string tokens of the form #n for four arguments.
   */
  private static String substitute(String s, String s0, String s1, String s2, 
                                   String s3, String s4) 
  {
    return substitute(s, new String[] { s0, s1, s2, s3, s4});
  }
  
  /**
   * Substitute certain string tokens in the conversion string with the
   * specified method arguments.  String tokens of the form #n, where n is
   * a nonzero single digit number, are substituted with the corresponding
   * element in the argument array.
   */
  private static String substitute(String s, String[] args) {
    if (s == null) {
      return null;
    }
    StringBuffer sb = new StringBuffer(s);

    int i = 0;
    while (i < sb.length()-1) {
      if (sb.charAt(i) == '#') {
        try {
          int n = Integer.parseInt(String.valueOf(sb.charAt(i+1)))-1;
          if ((n >= 0) && (n < args.length)) {
            sb.replace(i, i+2, args[n]);
          }
        } catch (NumberFormatException ex) {
          // ignore exception - no substitution
        }
      }
      i++;
    }

    return sb.toString();
  }

  /**
   * Print the specified string, s, after conversion with arguments s0 and s1.
   */
  public static void printSub(LanguageWriter lw, String s, String s0, 
                              String s1) 
  {
    lw.println(substitute(s, s0, s1));
  }

  /**
   * Print the specified string, s, after conversion with arguments s0, s1,
   * s2, and s3.
   */
  public static void printSub(LanguageWriter lw, String s, String s0, 
                              String s1, String s2, String s3)
  {
    lw.println(substitute(s, s0, s1, s2, s3));
  }

  /**
   * Print the specified string, s, after conversion with arguments s0, s1,
   * s2, s3, and s4.
   */
  public static void printSub(LanguageWriter lw, String s, String s0, 
                              String s1, String s2, String s3, String s4)
  {
    lw.println(substitute(s, s0, s1, s2, s3, s4));
  }
  
  /**
    * Generate a return string for the specified SIDL type.  Most
    * of the SIDL return strings are listed in the static structures defined
    * at the start of this class.  Symbol types and array types require
    * special processing.
    */
   public static String getReturnString(Type type,
                                        Context context)
     throws CodeGenerationException
   {
     return IOR.getReturnString(type, context, true, false);
   }


  /**
   * Generates include directives for all the Babel clases used in this 
   * Java stub or skel
   * 
   * @param writer Language writer for C
   * @param ext Extendible (Class or Interface) to generate dependencies
   */
  public static Set generateStubIncludes( LanguageWriterForC writer,
                                            Extendable ext) 
    throws CodeGenerationException 
  {
    Set includes = new HashSet();
    includes.addAll(Utilities.sort(ext.getObjectDependencies()));
    /*
    includes.add(ext.getSymbolID()); //add self
    for( Iterator i = ext.getMethods(true).iterator(); i.hasNext(); ) { 
      Method method = (Method) i.next();
      Set argTypes = method.getSymbolReferences();
      //includes.addAll(method.getSymbolReferences());
      for(Iterator j = argTypes.iterator(); j.hasNext();) {
        SymbolID argID = (SymbolID) j.next();
        Symbol symbol = (Symbol) Utilities.lookupSymbol(argID);
        if(symbol.getSymbolType() == Type.CLASS ||
           symbol.getSymbolType() == Type.INTERFACE)
          includes.add(argID);
      }
    }
    */

    if (!includes.isEmpty()){
      writer.writeComment("Includes for all method dependencies.",false);
      
      List entries = Utilities.sort(includes);
      
      for (Iterator i = entries.iterator(); i.hasNext(); ) { 
        String header = Java.getHeaderFile( (SymbolID) i.next());

        writer.generateInclude( header, true );
      }
    }
    
    return includes;
  }

  /**
   * Generates include directives for all struct types referenced by the
   * given Extendable
   * @param writer Language writer for C
   * @param ext Extendible (Class or Interface) to generate dependencies
   * @param context Context Current context
   */
  public static void generateStructIncludes( LanguageWriterForC writer,
                                             Extendable ext,
                                             Context context) 
    throws CodeGenerationException 
  {
    //Set includes = new HashSet();
    
    /* these are some additional includes required for struct support */
    Set seen = new HashSet();
    for(Iterator i = ext.getMethods(true).iterator(); i.hasNext(); ) { 
      Method method = (Method) i.next();
      Set argTypes = method.getSymbolReferences();
      for(Iterator j = argTypes.iterator(); j.hasNext();) {
        SymbolID argID = (SymbolID) j.next();
        Symbol symbol = (Symbol) Utilities.lookupSymbol(context, argID);
        if(symbol.isStruct() && !seen.contains(symbol)) {
          String header = Java.getUtilityHeaderFile(symbol);
          writer.generateInclude(header, true);
          seen.add(symbol);
        }
      }
    }
  }
  
  /**
   * Return an upper bound on the number of Java local object references
   * required in the Java skeleton for the indicated type and mode.
   *
   * @param mode  the argument passing mode IN, INOUT, or OUT
   * @param type  the argument type
   */
  private static int localJavaVars(int mode, Type type)
  {
    switch (type.getDetailedType()) {
    case Type.ARRAY:
    case Type.INTERFACE:
    case Type.CLASS:
    case Type.STRUCT:
    case Type.STRING:
      return (mode == Argument.IN) ? 1 : 2;
    default:
      return 0;
    }
  }

  /**
   * Return an upper bound on the number of Java local object references
   * required in the Java skeleton for the indicated method.
   */
  public static int localJavaVars(Method m)
  {
    int result = 8;             // padding
    result += localJavaVars(Argument.OUT, m.getReturnType());
    Iterator i = m.getArgumentList().iterator();
    while (i.hasNext()) {
      Argument a = (Argument)i.next();
      result += localJavaVars(a.getMode(), a.getType());
    }
    if (!m.getThrows().isEmpty()) {
      ++result;
    }
    return result;
  }

  /**
   * Print a few required preprocessor directives for pointer to long
   * conversions to the given C writer
   */
  public static void generatePointerJLongConv(LanguageWriterForC d_writer) {
    d_writer.writeComment("Convert between jlong and void* pointers.", false);
    try {
      d_writer.pushLineBreak(false);
      d_writer.println("#if (SIZEOF_VOID_P == 8)");
      d_writer.println("#define JLONG_TO_POINTER(x) ((void*)(x))");
      d_writer.println("#define POINTER_TO_JLONG(x) ((jlong)(x))");
      d_writer.println("#else");
      d_writer.println("#define JLONG_TO_POINTER(x) ((void*)(int32_t)(x))");
      d_writer.println("#define POINTER_TO_JLONG(x) ((jlong)(int32_t)(x))");
      d_writer.println("#endif");
      d_writer.println();
      d_writer.println("#ifndef "+C.NULL+"");
      d_writer.println("#define "+C.NULL+" 0");
      d_writer.println("#endif");
      d_writer.println();
    }
    finally {
      d_writer.popLineBreak();
    }
  }

  
}
