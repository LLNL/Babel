//
// File:        JavaStructSource.java
// Package:     gov.llnl.babel.backend.jdk
// Copyright:   (c) 2009 Lawrence Livermore National Security, LLC
// Revision:    @(#) $Revision$
// Date:        $Date$
// Description: emit some java code for struct support
//
// Copyright (c) 2009, Lawrence Livermore National Security, LLC
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
import gov.llnl.babel.backend.CodeConstants;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.jdk.Java;
import gov.llnl.babel.backend.c.C;
import gov.llnl.babel.backend.writers.LanguageWriterForC;
import gov.llnl.babel.backend.writers.LanguageWriterForJava;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.SymbolTable;
import gov.llnl.babel.symbols.Struct;
import gov.llnl.babel.symbols.SymbolUtilities;
import gov.llnl.babel.symbols.Type;
import gov.llnl.babel.symbols.Argument;
import gov.llnl.babel.symbols.AssertionExpression;
import gov.llnl.babel.symbols.CExprString;
import gov.llnl.babel.backend.rmi.RMI;


import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.HashMap;

/**
 * Class <code>JavaStructSource</code> implements the Java interface
 * for SIDL structs and conversion routines that translate among the
 * Java representation and Babel's IOR. This class generates the Java files
 * and the C JNI files.  It is assumed that all symbols necessary to generate
 * Java code are available in the symbol table.
 */
public class JavaStructSource {
  protected Struct d_struct = null;

  public static final int MODE_IN =    0;
  public static final int MODE_INOUT = 1;
  public static final int MODE_OUT =   2;
  public static final int MODE_RET =   3;
  
  String modes[] = {"IN", "INOUT", "OUT", "RET"};

  private static final HashMap s_ior2java = new HashMap();
  private static final HashMap s_java2ior = new HashMap();
  private static final HashMap s_pack_type = new HashMap();  
  private static final HashMap s_item_holder = new HashMap();

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
    s_ior2java.put(s_boolean,   "#1 = (#3) #2;");
    s_ior2java.put(s_char,      "#1 = (#3) #2;");
    s_ior2java.put(s_dcomplex,  "#1 = sidl_Java_I2J_dcomplex(env, &#2);");
    s_ior2java.put(s_double,    "#1 = (#3) #2;");
    s_ior2java.put(s_fcomplex,  "#1 = sidl_Java_I2J_fcomplex(env, &#2);");
    s_ior2java.put(s_float,     "#1 = (#3) #2;");
    s_ior2java.put(s_int,       "#1 = (#3) #2;");
    s_ior2java.put(s_long,      "#1 = (#3) #2;");
    s_ior2java.put(s_opaque,    "#1 = (#3) POINTER_TO_JLONG(#2);");
    s_ior2java.put(s_string,    "#1 = sidl_Java_I2J_string(env, #2);");
    s_ior2java.put(s_enum,      "#1 = (#3) #2;");
    s_ior2java.put(s_class,     "#1 = sidl_Java_I2J_cls(env, #2, \"#4\", #5); JAVA_CHECK(env);");
    s_ior2java.put(s_interface, "#1 = sidl_Java_I2J_ifc(env, #2, \"#4\", #5); JAVA_CHECK(env);");
 
    s_java2ior.put(s_boolean,   "#1 = (#3) #2;");
    s_java2ior.put(s_char,      "#1 = (#3) #2;");
    s_java2ior.put(s_dcomplex,  "#1 = sidl_Java_J2I_dcomplex(env, #2);");
    s_java2ior.put(s_double,    "#1 = (#3) #2;");
    s_java2ior.put(s_fcomplex,  "#1 = sidl_Java_J2I_fcomplex(env, #2);");
    s_java2ior.put(s_float,     "#1 = (#3) #2;");
    s_java2ior.put(s_int,       "#1 = (#3) #2;");
    s_java2ior.put(s_long,      "#1 = (#3) #2;");
    s_java2ior.put(s_opaque,    "#1 = (#3) JLONG_TO_POINTER(#2);");
    s_java2ior.put(s_string,    "#1 = sidl_Java_J2I_string(env, #2);");
    s_java2ior.put(s_enum,      "#1 = (#3) #2;");
    s_java2ior.put(s_class,     "#1 = (#3) sidl_Java_J2I_cls(env, #2, #5); JAVA_CHECK(env);");
    s_java2ior.put(s_interface, "#1 = (#3) sidl_Java_J2I_ifc(env, #2, \"#4\", #5); JAVA_CHECK(env);");

    s_pack_type.put(s_boolean,   "Bool");
    s_pack_type.put(s_char,      "Char");
    s_pack_type.put(s_dcomplex,  "Dcomplex");
    s_pack_type.put(s_double,    "Double");
    s_pack_type.put(s_fcomplex,  "Fcomplex");
    s_pack_type.put(s_float,     "Float");
    s_pack_type.put(s_int,       "Int");
    s_pack_type.put(s_long,      "Long");
    s_pack_type.put(s_opaque,    "Opaque");
    s_pack_type.put(s_string,    "String");
    s_pack_type.put(s_enum,      "Long");

    s_item_holder.put(s_boolean,  "sidl.Boolean.Holder");
    s_item_holder.put(s_char,     "sidl.Character.Holder");
    s_item_holder.put(s_dcomplex, "sidl.DoubleComplex.Holder");
    s_item_holder.put(s_double,   "sidl.Double.Holder");
    s_item_holder.put(s_fcomplex, "sidl.FloatComplex.Holder");
    s_item_holder.put(s_float,    "sidl.Float.Holder");
    s_item_holder.put(s_int,      "sidl.Integer.Holder");
    s_item_holder.put(s_long,     "sidl.Long.Holder");
    s_item_holder.put(s_opaque,   "sidl.Opaque.Holder");
    s_item_holder.put(s_string,   "sidl.String.Holder");
    s_item_holder.put(s_enum,     "sidl.Long.Holder");
  }
  
  /**
   * The constructor does nothing interesting.  The entry point for
   * the <code>JavaStructSource</code> class is <code>generateCode</code>.
   */
  JavaStructSource(Struct strct) {
    d_struct = strct;
  }

  /**
   * Retrieves the name of the utility function used to convert from Java
   * objects to IOR 
   */
  public static String getJ2IFunctionName(SymbolID id) {
    return "sidl_" + IOR.getSymbolName(id) + "_J2I";
  }

  /**
   * Retrieves the name of the utility function used to convert from Java
   * holder objects to IOR 
   */
  public static String getJ2IHolderFunctionName(SymbolID id) {
    return getJ2IFunctionName(id) + "_holder";
  }

  /**
   * Retrieves the name of the utility function used to convert from IOR
   * to Java objects
   */
  public static String getI2JFunctionName(SymbolID id) {
    return "sidl_" + IOR.getSymbolName(id) + "_I2J";
  }

  /**
   * Retrieves the name of the utility function that releases allocated
   * memory for variable sized raw arrays
   */
  public static String getCleanupFunctionName(SymbolID id) {
    return "sidl_" + IOR.getSymbolName(id) + "_cleanup";
  }
  
  /**
   * Retrieves the name of the utility function used to convert from IOR
   * to Java holder objects
   */
  public static String getI2JHolderFunctionName(SymbolID id) {
    return getI2JFunctionName(id) + "_holder";
  }

  /**
   * Retrieves the name of the utility function used to serialize IOR structs
   */
  public static String getIORSerializerName(SymbolID id) {
    return "RMI_" + IOR.getSymbolName(id) + "_serialize_impl";
  }

  /**
   * Retrieves the name of the utility function used to deserialize IOR structs
   */
  public static String getIORDeserializerName(SymbolID id) {
    return "RMI_" + IOR.getSymbolName(id) + "_deserialize_impl";
  }
  
  private static boolean isInArgument(int mode) {
    return mode == Argument.IN;
  }

  private static boolean isOutArgument(int mode) {
    return mode == Argument.OUT;
  }

  private static boolean isInOutArgument(int mode) {
    return mode == Argument.INOUT;
  }

  private static boolean isReturnArgument(int mode) {
    return !isInArgument(mode) && !isOutArgument(mode) && !isInOutArgument(mode);
  }

  /**
   * Returns a fully qualified Java type for the internal Holder for the
   * given type. Only basic data types are allowed. 
   */
  private static String getItemHolderType(Struct.Item item) {
    Integer T = new Integer(item.getType().getDetailedType());
    return (String) s_item_holder.get(T);
  }
    
  /**
   * Generate a Holder class for object with the specified name
   */
  private void generateHolder(LanguageWriterForJava d_writer, String short_name, String full_name) {
    d_writer.println();
    d_writer.println();
    d_writer.beginBlockComment(true);
    d_writer.println("This is the holder inner class for inout and out arguments for");
    d_writer.println("type <code>" + short_name + "</code>.");
    d_writer.endBlockComment(true);
    d_writer.println("public static class Holder {");
    d_writer.tab();
    d_writer.println("private " + full_name + " d_obj;");
    d_writer.println();

    d_writer.beginBlockComment(true);
    d_writer.println("Create a holder class with an empty holdee object.");
    d_writer.endBlockComment(true);
    d_writer.println("public Holder() {");
    d_writer.tab();
    d_writer.println("d_obj = null;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
 
    d_writer.beginBlockComment(true);
    d_writer.println("Create a holder with the specified object.");
    d_writer.endBlockComment(true);
    d_writer.println("public Holder(" + full_name + " obj) {");
    d_writer.tab();
    d_writer.println("d_obj = obj;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();

    d_writer.beginBlockComment(true);
    d_writer.println("Set the value of the holdee object.");
    d_writer.endBlockComment(true);
    d_writer.println("public void set(" + full_name + " obj) {");
    d_writer.tab();
    d_writer.println("d_obj = obj;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();

    d_writer.beginBlockComment(true);
    d_writer.println("Get the value of the holdee object.");
    d_writer.endBlockComment(true);
    d_writer.println("public " + full_name + " get() {");
    d_writer.tab();
    d_writer.println("return d_obj;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();

    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
    d_writer.println();
  }

  /**
   * Generate Java code for serialization support
   */
  private void generateJavaSerializer(Context d_context, LanguageWriterForJava d_writer) {
    d_writer.println("public void serialize(" +
                     "sidl.io.Serializer pipe, " +
                     "final String name, " +
                     "boolean copyArg) {");
    d_writer.tab();
    for(Iterator i = d_struct.getItems().iterator(); i.hasNext(); ) {
      final Struct.Item item = (Struct.Item) i.next();
      String field_name = item.getName();
      Type type = item.getType();
      String type_name = (String) s_pack_type.get(new Integer(type.getDetailedType()));

      if(type.isStruct()) {
        d_writer.println("if(this." + field_name + " != null) " +
                         "this." + field_name + ".serialize(" +
                         "pipe, name + \"." + field_name + "\", copyArg);");
      }
      else if(type.isArray()) {
        d_writer.writeCommentLine("TODO: not yet implemented: " + field_name);
      }
      else if(type.getDetailedType() == Type.INTERFACE ||
              type.getDetailedType() == Type.CLASS) {

        d_writer.println("if(copyArg) {");
        d_writer.tab();
        
        //clone the object using packSerializable()
        d_writer.println("sidl.io.Serializable _s_" + field_name + " = null;");
        d_writer.println("try {");
        d_writer.tab();
        d_writer.println("if(this." + field_name + " != null) _s_" + field_name +
                         " = (sidl.io.Serializable) this." + field_name + ";");
        d_writer.backTab();
        d_writer.println("} catch (ClassCastException e) {");
        d_writer.tab();
        d_writer.println("sidl.CastException ce  = new sidl.CastException();");
        d_writer.pushLineBreak(false);
        d_writer.println("ce.setNote(\"struct item " +
                         field_name +
                         " cannot be cast to a sidl.io.Serializable.\");");
        d_writer.println("throw ce;");
        d_writer.backTab();
        d_writer.println("}");
        d_writer.println("pipe.packSerializable(" +
                         "name + \"." + field_name + "\", _s_" + field_name + ");");
        
        d_writer.backTab();
        d_writer.println("} else {");
        d_writer.tab();
        
        //serialize the remote URL
        d_writer.println("String _url = new String(\"\");");
        d_writer.println("if(this." + field_name + " != null) _url = this." + field_name +
                         "._getURL();");
        d_writer.println("pipe.packString(" + "name + \"." + field_name + "\", _url);");
        d_writer.backTab();
        d_writer.println("}");
      }
      else {
        d_writer.println("pipe.pack" + type_name + "(" +
                         "name + \"." + field_name + "\", " +
                         "this." + field_name + ");");
      }
    }
    
    d_writer.backTab();
    d_writer.println("}");
  }

  /**
   * Generate Java code for deserialization support
   */
  private void generateJavaDeserializer(Context d_context, LanguageWriterForJava d_writer) {
    d_writer.println("public void deserialize(" +
                     "sidl.io.Deserializer pipe, " +
                     "final String name, " +
                     "boolean copyArg) {");
    d_writer.tab();
    for(Iterator i = d_struct.getItems().iterator(); i.hasNext(); ) {
      final Struct.Item item = (Struct.Item) i.next();
      String field_name = item.getName();
      Type type = item.getType();
      String type_name = (String) s_pack_type.get(new Integer(type.getDetailedType()));

      if(type.isStruct()) {
        d_writer.println("if(this." + field_name + " != null) " +
                         "this." + field_name + ".deserialize(" +
                         "pipe, name + \"." + field_name + "\", copyArg);");
      }
      else if(type.isArray()) {
        d_writer.writeCommentLine("TODO: not yet implemented: " + field_name);
      }
      else if(type.getDetailedType() == Type.INTERFACE ||
              type.getDetailedType() == Type.CLASS) {
        d_writer.println("if(copyArg) {");
        d_writer.tab();
        
        //clone the object using unpackSerializable()
        d_writer.println("{");
        d_writer.tab();
        d_writer.println("sidl.io.Serializable.Holder _s_holder  = " +
                         "new sidl.io.Serializable.Holder();");
        d_writer.println("pipe.unpackSerializable(" +
                         "name + \"." + field_name + "\", _s_holder);");
        //try a babel cast to the particular field type
        String target_type = Java.getJavaFieldType(item);
        String wrapper_type = target_type;
        if(type.getDetailedType() == Type.INTERFACE)
          wrapper_type += ".Wrapper";
        
        d_writer.println("this." + field_name + " = null;");
        d_writer.println("if(_s_holder.get() != null) {");
        d_writer.tab();
        d_writer.println("sidl.io.Serializable _s = _s_holder.get();");
        d_writer.println("this." + field_name + " = (" + target_type + ") " +
                         wrapper_type + "._cast((sidl.io.Serializable.Wrapper) _s);");
        d_writer.backTab();
        d_writer.println("}");
        
        d_writer.backTab();
        d_writer.println("}");
        
        d_writer.backTab();
        d_writer.println("} else {");
        d_writer.tab();
        
        //deserialize the URL and connect to the remote object
        d_writer.println("sidl.String.Holder _url_holder = new sidl.String.Holder();");
        d_writer.println("pipe.unpackString(" + "name + \"." + field_name + "\", _url_holder);");
        d_writer.println("String _url = _url_holder.get();");
        d_writer.println("this." + field_name + " = null;");
        d_writer.println("if(_url != null) this." + field_name + " = (" + target_type + ") " + 
                         wrapper_type + "._connect(_url);");
        d_writer.backTab();
        d_writer.println("}");
      }
      else {
        String holder_name = getItemHolderType(item);
        d_writer.println(holder_name + " _holder_" + field_name + " = new " +
                         holder_name + "();");
        d_writer.println("pipe.unpack" + type_name + "(" +
                         "name + \"." + field_name + "\", " +
                         "_holder_" + field_name + ");");
        d_writer.println("this." + field_name + " = _holder_" + field_name + ".get();");
      }
    }
    
    d_writer.backTab();
    d_writer.println("}");
  }

  /**
   * Generate C code that serializes a struct of the given type
   */
  private void generateRMISerializer(LanguageWriterForC d_writer,
                                     Context d_context) {
    final SymbolID id = d_struct.getSymbolID();
    final String packer_typename = "sidl_io_Serializer";

    d_writer.writeComment("RMI serialization for structs of type" +
                          id.getFullName(), false);
    d_writer.println("void " + getIORSerializerName(id) + " (" +
                     "const " + IOR.getStructName(d_struct) + " *arg, " +
                     "struct " + packer_typename + "__object *serializer, " +
                     "const char *name, " +
                     "sidl_bool copyArg, " +
                     "struct sidl_BaseInterface__object **exception) {");
    d_writer.tab();

    int max_buf_size = 0;
    for(Iterator i = d_struct.getItems().iterator(); i.hasNext(); ) {
      Struct.Item item = (Struct.Item) i.next();
      max_buf_size = Math.max(max_buf_size, 1 + item.getName().length());
    }
    d_writer.println("unsigned name_len = strlen(name);");
    d_writer.println("char *name_buf = malloc(2 + name_len + " + max_buf_size + ");");
    d_writer.println("memcpy(name_buf, name, name_len);");
    d_writer.println("char *p_field = name_buf + name_len;");

    for(Iterator i = d_struct.getItems().iterator(); i.hasNext(); ) {
      Struct.Item item = (Struct.Item) i.next();
      RMI.serializeField(d_writer, d_context, packer_typename, "serializer", item);
    }
    d_writer.println("EXIT:");
    d_writer.println("free(name_buf);");
    d_writer.backTab();
    d_writer.println("}");
  }

  /**
   * Generate C code that serializes a struct of the given type
   */
  private void generateRMIDeserializer(LanguageWriterForC d_writer,
                                       Context d_context) {

    final SymbolID id = d_struct.getSymbolID();
    final String packer_typename = "sidl_io_Deserializer";
    
    d_writer.writeComment("RMI deserialization for structs of type" +
                          id.getFullName(), false);
    d_writer.println("void " + getIORDeserializerName(id) + " (" +
                     IOR.getStructName(d_struct) + " *arg, " +
                     "struct sidl_io_Deserializer__object *rsp, " +
                     "const char *name, " +
                     "sidl_bool copyArg, " +
                     "struct sidl_BaseInterface__object **exception) {");
    d_writer.tab();

    int max_buf_size = 0;
    for(Iterator i = d_struct.getItems().iterator(); i.hasNext(); ) {
      Struct.Item item = (Struct.Item) i.next();
      max_buf_size = Math.max(max_buf_size, 1 + item.getName().length());
    }
    d_writer.println("unsigned name_len = strlen(name);");
    d_writer.println("char *name_buf = malloc(2 + name_len + " + max_buf_size + ");");
    d_writer.println("memcpy(name_buf, name, name_len);");
    d_writer.println("char *p_field = name_buf + name_len;");

    for(Iterator i = d_struct.getItems().iterator(); i.hasNext(); ) {
      Struct.Item item = (Struct.Item) i.next();
      RMI.deserializeField(d_writer, d_context, packer_typename, "rsp", item);
    }
    d_writer.println("EXIT:");
    d_writer.println("free(name_buf);");
    d_writer.backTab();
    d_writer.println("}");
  }
  
  
  /**
   * Generate Java code for SIDL struct support
   */
  public synchronized void generateProxyClass(Context d_context, LanguageWriterForJava d_writer)
    throws CodeGenerationException {
    String file = Java.getStructJavaFile(d_struct);;
    SymbolID id = d_struct.getSymbolID();
    /*
     * Output the banner, package statement, and comment.
     */
    d_writer.writeBanner(d_struct, file, CodeConstants.C_IS_NOT_IMPL,
                         CodeConstants.C_DESC_STUB_PREFIX + id.getFullName());

    d_writer.beginBlockComment(true);
    d_writer.println("This class is used to hold the Java representation");
    d_writer.println("of the SIDL struct " + d_struct.getFullName() + ".");
    d_writer.endBlockComment(true);

    String pkg = SymbolUtilities.getParentPackage(id.getFullName());
    d_writer.println("package " + pkg + ";");
    d_writer.println();
      
    /*
     * Generate the wrapper class a generic Java class with private data members
     */
    d_writer.writeComment(d_struct, true);
    d_writer.println("public class " + Java.getJavaSymbolName(id) + " {");
    d_writer.tab();

    //declare public data members
    for(Iterator i = d_struct.getItems().iterator(); i.hasNext(); ) {
      final Struct.Item item = (Struct.Item) i.next();
      d_writer.println("public " + Java.getJavaFieldType(item) +
                       " " + item.getName() + ";");
    }

    // Generate the holder inner class to be consistent with the rest of the
    // Java bindings
    String short_name = Java.getJavaSymbolName(id);
    String full_name = pkg + "." + short_name;
    generateHolder(d_writer, short_name, full_name);

    // Generate a default constructor
    d_writer.println("public " + Java.getJavaSymbolName(id) + "() { } ");
    
    // Generate a public constructor initializing each of the field so
    // that the object can be initialized using a single JNI call
    d_writer.println("public " + Java.getJavaSymbolName(id) + "(");
    d_writer.tab();
    for(Iterator i = d_struct.getItems().iterator(); i.hasNext(); ) {
      final Struct.Item item = (Struct.Item) i.next();
      String comma = (i.hasNext() ? "," : ") {");
      d_writer.println(Java.getJavaFieldType(item) +  " " + item.getName() + comma);
    }
    d_writer.println();
      
    for(Iterator i = d_struct.getItems().iterator(); i.hasNext(); ) {
      final Struct.Item item = (Struct.Item) i.next();
      d_writer.println("this." + item.getName() + " = " + item.getName() + ";");
    }
      
    d_writer.backTab();
    d_writer.println("}");

    // emit basic (de)serialization support
    if(!d_context.getConfig().getSkipRMI()) {
      d_writer.println();
      generateJavaSerializer(d_context, d_writer);
      d_writer.println();
      generateJavaDeserializer(d_context, d_writer);
    }
      
    d_writer.backTab();
    d_writer.println("}");
  }
  
  /**
   * Generate a header file with prototypse for utility functions used
   * for struct support.
   */
  public synchronized void generateUtilityHeader(Context d_context)
    throws CodeGenerationException {
    final int type = d_struct.getSymbolType();
    final SymbolID id = d_struct.getSymbolID();
    PrintWriter pw = null;
    try {

      //setup the C writer      
      String file = Java.getUtilityHeaderFile(id);
      pw = d_context.getFileManager().createFile(id, type, "IORSRCS", file);
      LanguageWriterForC d_writer = new LanguageWriterForC(pw, d_context);

      //emit file banner 
      d_writer.writeBanner(d_struct, file, CodeConstants.C_IS_NOT_IMPL,
                           CodeConstants.C_UTILITY_HEADER_PREFIX + id.getFullName());

      d_writer.openHeaderGuard(file);
      
      d_writer.generateInclude("sidl_Java.h", false);
      d_writer.generateInclude("sidl_Loader.h", false);
      d_writer.generateInclude("sidl_String.h", false);
      d_writer.generateInclude(IOR.getHeaderFile(id), false);
      d_writer.generateInclude("babel_config.h", false);
      d_writer.println();


      //emit the prototypes
      d_writer.writeComment("convert IOR references for struct " +
                            id.getShortName() + " to Java object of type " +
                            id.getFullName() + ".", false);
      d_writer.println("jobject " + getI2JFunctionName(id) +
                       "(JNIEnv* env, const " + IOR.getStructName(id) +
                       " *, int mode, int is_client);");
      d_writer.println();
      d_writer.println("void " + getI2JHolderFunctionName(id) +
                       "(JNIEnv* env, jobject obj, " + IOR.getStructName(id) +
                       " *value, int mode, int is_client);");
      
      d_writer.println();
      d_writer.println();
      
      d_writer.writeComment("convert Java object references of type " +
                            id.getFullName() +
                            " to Babel's IOR rpresentation.", false);
      d_writer.println("void " + getJ2IFunctionName(id) +
                       "(JNIEnv* env, jobject obj, " +
                       IOR.getStructName(id) +
                       " *, int mode, int is_client);");

      d_writer.println();
      d_writer.println("void " + getJ2IHolderFunctionName(id) +
                       "(JNIEnv* env, jobject obj, " + IOR.getStructName(id) +
                       " *value, int mode, int is_client);");

      d_writer.println();
      d_writer.println("void " + getCleanupFunctionName(id) +
                       "(" + IOR.getStructName(id) + " *p_struct);");
      

      // these functions can (de)serialize plain IOR structs.
      if (!d_context.getConfig().getSkipRMI()) {
        d_writer.println();

        d_writer.writeComment("RMI serialization for structs of type" +
                              id.getFullName(), false);
        d_writer.println("void " + getIORSerializerName(id) + " (" +
                         "const " + IOR.getStructName(d_struct) + " *arg, " +
                         "struct sidl_io_Serializer__object *inv, " +
                         "const char *name, " +
                         "sidl_bool copyArg, " +
                         "struct sidl_BaseInterface__object **ex);");

        d_writer.writeComment("RMI deserialization for structs of type" +
                              id.getFullName(), false);
        d_writer.println("void " + getIORDeserializerName(id) + " (" +
                         IOR.getStructName(d_struct) + " *arg, " +
                         "struct sidl_io_Deserializer__object *rsp, " +
                         "const char *name, " +
                         "sidl_bool copyArg, " +
                         "struct sidl_BaseInterface__object **ex);");
      }

      d_writer.closeHeaderGuard();
      
    } finally {
      if (pw != null) {
        pw.close();
      }
    }
  }

  /**
   * Returns true if the given struct contains a raw array field. Does not
   * recursively check nested structs!
   */
  public static boolean containsRarrayField(Struct s) {
    for(Iterator i = s.getItems().iterator(); i.hasNext(); ) {
      Struct.Item item = (Struct.Item) i.next();
      if(item.getType().isRarray())
        return true;
    }
    return false;
  }

  /**
   * Returns true if the given struct contains a raw array field or a nested
   * struct that might require cleanup
   */
  public static boolean requiresCleanup(Struct s) {
    for(Iterator i = s.getItems().iterator(); i.hasNext(); ) {
      Struct.Item item = (Struct.Item) i.next();
      if(item.getType().isRarray() || item.getType().isStruct())
        return true;
    }
    return false;
  }
  

  /**
   * Method <code>convertJavaToIOR</code> converts between Java objects
   * and IOR arguments. Conversion routines are output to the language writer.
   * This routine retrieves the conversion string from the appropriate hash
   * table and then substitutes the appropriate variable names in the conversion
   * string. This function is currently exclusively used to convert struct fields. 
   */
  public static void convertJavaToIOR(LanguageWriterForC writer, 
                                      Type type,
                                      String dst_name,
                                      String src_name,
                                      int mode,
                                      boolean is_client, 
                                      Context context)
    throws CodeGenerationException 
  {

    //this is called post-call for servers for out/inout/ret arguments
    //and pre-call for clients for in/inout arguments
    
    if(type.isStruct()) {
      writer.println(JavaStructSource.getJ2IFunctionName(type.getSymbolID()) +
                     "(env, " + src_name + ", &" + dst_name + ", " + mode + ", " +
                     (is_client ? "TRUE" : "FALSE") + ");");
    }
    else if(type.isArray()) {
      //raw arrays have to be treated with care
      //note that only in and inout parameters are legal
      if(type.isRarray()) {
        //for the time being, we just clone the data regardless if its a fixed
        //size inner rarray or a dynamically allocated rarray

        //evaluate the total size of the array
        String size_expr = IOR.getRarraySizeExpr(type, "_ior->");
        String lhs = src_name + "_ior";
        Java.printSub(writer, "#1 = (#3) sidl_Java_J2I_borrow_array(env, #2);",
                      Java.getReturnString(type, context) + " " + lhs,
                      src_name, 
                      Java.getReturnString(type, context), "");
        
        //allocate new memory if necessary. 
        String target = dst_name;
        if(!IOR.isFixedSizeRarray(type)) {
          writer.println("if(!" + dst_name + ") " + dst_name + " = malloc(" + size_expr + ");");
          //this should never happen on machines with virtual memory
          writer.println("assert(" + dst_name + " != NULL && \"malloc failed.\");");
        }
        else
          target = "&" + target;
        //do the memcpy
        writer.println("memcpy(" + target + ", " + lhs + "->d_firstElement, " +
                       size_expr + ");");
      }
      else {
        final String array_method[] = {
          "sidl_Java_J2I_borrow_array",  /* in */
          "sidl_Java_J2I_take_array",    /* inout */
          "sidl_Java_J2I_take_array",    /* out */
          "sidl_Java_J2I_take_array",    /* ret */
        };
        
        Java.printSub(writer, "#1 = (#3) #4(env, #2);",
                      dst_name, 
                      src_name, 
                      Java.getReturnString(type, context),
                      array_method[mode]);
      }
    }
    else {
      final boolean needs_addref[] = {
        false, /* in */
        true,  /* inout */
        true,  /* out */
        true,  /* ret */
      };

      String convert = (String) s_java2ior.get(new Integer(type.getDetailedType()));
      assert(convert != null); //all other cases have been handled before
      Java.printSub(writer, convert,
                    dst_name, 
                    src_name, 
                    Java.getReturnString(type, context),
                    type.isSymbol() ? type.getSymbolID().getFullName() : "",
                    (needs_addref[mode] ? "TRUE" : "FALSE"));
    }
  }

  /**
   * Method <code>convertIORToJava</code> converts between IOR objects
   * and Java objects.  Conversion routines are output
   * to the language writer.  This routine retrieves the conversion string
   * from the hash table and then substitutes the appropriate variable names
   * in the conversion string. This function is currently exclusively used
   * to convert struct fields.
   */
  public static void convertIORToJava(LanguageWriterForC writer,
                                      Type type,
                                      String ior_result,
                                      String java_result,
                                      int mode,
                                      boolean is_client,
                                      Context context)
    throws CodeGenerationException {
    //this is called post-call for clients for out/inout/ret arguments
    //and pre-call for servers for in/inout arguments
    
    if(type.isStruct()) {
      writer.println(java_result + " = " +
                     JavaStructSource.getI2JFunctionName(type.getSymbolID()) +
                     "(env, &" + ior_result + ", " + mode + ", " +
                     (is_client ? "TRUE" : "FALSE") + ");");
    }
    else if(type.isRarray()) {
      //create a brand new java array and copy the data over accordingly
      String prefix = java_result;
      int internal_type = type.getArrayType().getType();
      //String sidl_array_name = IOR.getArrayNameWithoutAsterix(internal_type);
      int dim = type.getArrayDimension();

      //declare and initialize some temporary datastructures
      writer.println("int32_t " + prefix + "_lower[" + dim + "], " +
                     prefix + "_upper[" + dim + "];\n");

      List indices = type.getArrayIndexExprs();
      String create_func_name = IOR.getArrayNameForFunctions(internal_type) + "_createCol";

      int x = 0;
      for(Iterator i = indices.iterator(); i.hasNext(); ++x) {
        AssertionExpression ae = (AssertionExpression) i.next();
        writer.println(prefix + "_lower[" + x + "] = 0;");
        writer.println(prefix + "_upper[" + x + "] = " + 
                       ae.accept(new CExprString("_ior->"), null).toString() + "-1;\n");
      }

      //now, create a brand new Java array
      writer.println(Java.getReturnString(type, context) + " " + prefix + "_ior_p  = " + 
                     create_func_name + "(" + dim + ", " + prefix + "_lower, " +
                     prefix + "_upper);");

      String source = ior_result;
      if(IOR.isFixedSizeRarray(type))
        source = "&" + ior_result;
      
      writer.println("memcpy(" + prefix + "_ior_p->d_firstElement, " +
                     source + ", " + IOR.getRarraySizeExpr(type, "_ior->") + ");");

      Java.printSub(writer, "#1 = sidl_Java_I2J_new_array(env, #2, \"#3\");",
                    java_result, 
                    prefix + "_ior_p",
                    Java.getJavaInternalReturnType(type), "");
    }
    else if(type.isArray()) {
      final String array_method[] = {
        "sidl_Java_I2J_new_array_server",  /* in */
        "sidl_Java_I2J_new_array",         /* inout */
        "sidl_Java_I2J_new_array",         /* out */
        "sidl_Java_I2J_new_array",         /* ret */
      };
      
      Java.printSub(writer, "#1 = #3(env, #2, \"#4\");",
                    java_result, 
                    ior_result,
                    array_method[mode], 
                    Java.getJavaInternalReturnType(type));
    } else {
      final boolean needs_addref[] = {
        true,  /* in */
        false, /* inout */
        false, /* out */
        false, /* ret */
      };
      Java.printSub(writer, (String) s_ior2java.get(new Integer(type.getDetailedType())),
                    java_result, 
                    ior_result,
                    Java.getJNIReturnType(type), 
                    Java.getJavaInternalReturnType(type),
                    (needs_addref[mode] ? "TRUE" : "FALSE"));
    }
  }

  /**
   * Generate a set of C utility functions that convert from Babel's IOR to
   * native Java objects and vice versa.
   */
  public synchronized void generateUtilitySource(Context d_context)
    throws CodeGenerationException {
    final int type = d_struct.getSymbolType();
    final SymbolID id = d_struct.getSymbolID();
    PrintWriter pw = null;
    try {
      //setup the C writer      
      String file = Java.getUtilitySourceFile(id);
      pw = d_context.getFileManager().createFile(id, type, "IORSRCS", file);
      LanguageWriterForC d_writer = new LanguageWriterForC(pw, d_context);

      //emit file banner 
      d_writer.writeBanner(d_struct, file, CodeConstants.C_IS_NOT_IMPL,
                           CodeConstants.C_UTILITY_SOURCE_PREFIX + id.getFullName());

      d_writer.generateSystemInclude("string.h");
      d_writer.generateSystemInclude("alloca.h");
      d_writer.generateSystemInclude("assert.h");
      d_writer.generateSystemInclude("stdlib.h");
      d_writer.generateSystemInclude("stddef.h");
      
      d_writer.generateInclude("sidl_Java.h", false);
      d_writer.generateInclude("sidl_Loader.h", false);
      d_writer.generateInclude("sidl_String.h", false);

      if (!d_context.getConfig().getSkipRMI()) {
        d_writer.generateInclude("sidl_io_Serializer.h", true);
        d_writer.generateInclude("sidl_io_Deserializer.h", true);
        d_writer.generateInclude("sidl_io_Serializable_IOR.h", true);
        d_writer.generateInclude("sidlOps.h", true);
        d_writer.generateInclude("sidl_CastException.h", true);
        d_writer.generateInclude("sidl_Exception.h", true);

        SymbolTable table = d_context.getSymbolTable();
        d_writer.generateInclude(C.getHeaderFile(table.lookupSymbol(
                                 "sidl.rmi.Invocation").getSymbolID()), true);
        d_writer.generateInclude(C.getHeaderFile(table.lookupSymbol(
                                 "sidl.rmi.Response").getSymbolID()), true);
        d_writer.generateInclude(C.getHeaderFile(table.lookupSymbol(
                                 "sidl.io.Serializable").getSymbolID()), true);
      }
      
      d_writer.generateInclude(IOR.getHeaderFile(id), false);
      d_writer.generateInclude("babel_config.h", false);

      d_writer.generateInclude(Java.getUtilityHeaderFile(id), false);

      //make sure to include utility headers for referenced types
      for(Iterator i = d_struct.getItems().iterator(); i.hasNext(); ) {
        Struct.Item item = (Struct.Item) i.next();
        if(item.getType().isStruct())
          d_writer.generateInclude(Java.getUtilityHeaderFile(item.getType().getSymbolID()), false);
      }

      //emit a few preprocessor directives for pointer to long conversions
      d_writer.println();
      Java.generatePointerJLongConv(d_writer);

      //emit a small static utility function to cache the JNI class id
      d_writer.println("static jclass __cls_" + IOR.getSymbolName(id) + " = (jclass) NULL;");
      d_writer.println("static jclass sidl_" + IOR.getSymbolName(id) + "_get_class_id(JNIEnv* env) {");
      d_writer.tab();
      d_writer.println("if(__cls_" + IOR.getSymbolName(id) + " == NULL ) {");
      d_writer.tab();
      d_writer.println("jclass cls = (*env)->FindClass(env, \"" +
                       Java.getFullJavaSymbolName(id).replace('.', '/') + "\");");
      d_writer.println("__cls_" + IOR.getSymbolName(id) + " = (*env)->NewGlobalRef(env, cls);");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println("return __cls_" + IOR.getSymbolName(id) + ";");
      d_writer.backTab();
      d_writer.println("}");                 
      
      d_writer.println();

      //generate a small cleanup functions that releases memory allocated
      //for raw array
      d_writer.println("void " + getCleanupFunctionName(id) +
                       "(" + IOR.getStructName(id) + " *p_struct) {");
      d_writer.tab();
      for(Iterator i = d_struct.getItems().iterator(); i.hasNext(); ) {
        Struct.Item item = (Struct.Item) i.next();
        Type t = item.getType();
        String f = "p_struct->" + item.getName();
        if(t.isStruct())
          d_writer.println(JavaStructSource.getCleanupFunctionName(t.getSymbolID()) + "(&" + f + ");");
        else if(t.isRarray() && !IOR.isFixedSizeRarray(t))
          d_writer.println("if(" + f + ") free(" + f + ");");
      }
      d_writer.backTab();
      d_writer.println("}");                 
      d_writer.println();

      
      //emit the code to convert from Java to IOR objectes and vice versa
      generateI2JCode(d_writer, d_context);
      d_writer.println();
      d_writer.println();
      generateJ2ICode(d_writer, d_context);

      //emit (de)serialization function that directly serialze IOR structs
      d_writer.println();
      d_writer.println();

      if (!d_context.getConfig().getSkipRMI()) {
        generateRMISerializer(d_writer, d_context);
        d_writer.println();
        d_writer.println();
        generateRMIDeserializer(d_writer, d_context);
      }
     
    } finally {
      if (pw != null) {
        pw.close();
      }
    }
  }
    
  /**
   * Generate C code that wraps a Babel's IOR representation for structs
   * into a generic Java object
   */
  private void generateI2JCode(LanguageWriterForC d_writer,
                               Context d_context)
    throws CodeGenerationException {

    //final int type = d_struct.getSymbolType();
    final SymbolID id = d_struct.getSymbolID();
    
    /***************************************************************************
     * generate basic conversion function
     * jobject <function_name>(JNIEnv* env,
     *                         <ior_struct_name> *,
     *                         int mode,
     *                         int is_client
     *                        ) 
     */
    d_writer.writeComment("convert IOR references for struct " +
                          id.getShortName() + " to Java objects of type " +
                          id.getFullName() + ".", false);
    d_writer.println("jobject " + getI2JFunctionName(id) + 
                     "(JNIEnv* env, const " + IOR.getStructName(id) +
                     " *_ior, int mode, int is_client) {");
    d_writer.tab();

    StringBuffer s = new StringBuffer();
    StringBuffer a = new StringBuffer();

    boolean print_java_exit = false;
    boolean contains_rarray = containsRarrayField(d_struct);

    d_writer.println("jobject _ret = NULL;");
    
    //declare temporary variables.
    //also, compute the lookup name and signature of the automatically
    //generated constructor
    for(Iterator i = d_struct.getItems().iterator(); i.hasNext(); ) {
      Struct.Item item = (Struct.Item) i.next();
    
      s.append(Java.getDescriptor(Java.getJavaFieldType(item, true)));
      a.append(", _tmp_" + item.getName());

      print_java_exit |=
        item.getType().getDetailedType() == Type.CLASS ||
        item.getType().getDetailedType() == Type.INTERFACE;

      Java.declareJavaVariable(d_writer, item.getType(), " _tmp_" + item.getName());
    }
    
    //before calling the constructor, convert any dependent inner fields.
    //generate a simple jump table that exercises the right conversion for
    //each combination of argument mode and client/server flags
    d_writer.println("if(is_client) { /* client mode */");
    d_writer.tab();

    d_writer.println("switch(mode) {");
    d_writer.tab();

    String mode_tab_client[] = {
      "<invalid>",   /* IN */
      "post-call",   /* INOUT */
      "post-call",   /* OUT */
      "post-call"    /* RET */
    };
    
    for(int mode = 0; mode < modes.length; ++mode) {
      d_writer.println("case " + mode + ": /* " + modes[mode] + " */");
      d_writer.println("{");
      d_writer.tab();

      if(mode_tab_client[mode].compareTo("<invalid>") == 0) {
        d_writer.println("assert(FALSE && \"should be never reached\");");
      }
      else if(contains_rarray && mode != MODE_INOUT) {
        d_writer.println("assert(FALSE && \"unsupported mode for structs with raw arrays.\");");
      }
      else {
        d_writer.writeCommentLine(mode_tab_client[mode]);
        for(Iterator i = d_struct.getItems().iterator(); i.hasNext(); ) {
          Struct.Item item = (Struct.Item) i.next();
          convertIORToJava(d_writer,
                           item.getType(),
                           "_ior->" + item.getName(),
                           " _tmp_" + item.getName(),
                           mode, true /*is_client*/, d_context);
        }
      }
      
      d_writer.println("break;");
      d_writer.backTab();
      d_writer.println("}");
    }
      
    d_writer.backTab();
    d_writer.println("}");

    //server mode 
    d_writer.backTab();
    d_writer.println("} else { /* server mode */");
    d_writer.tab();

    d_writer.println("switch(mode) {");
    d_writer.tab();

    String mode_tab_server[] = {
      "pre-call",  /* IN */
      "pre-call",  /* INOUT */
      "<invalid>", /* OUT */
      "<invalid>"  /* RET */
    };
    
    for(int mode = 0; mode < modes.length; ++mode) {
      d_writer.println("case " + mode + ": /* " + modes[mode] + " */");
      d_writer.println("{");
      d_writer.tab();
      
      if(mode_tab_server[mode].compareTo("<invalid>") == 0) {
        d_writer.println("assert(FALSE && \"should be never reached\");");
      }
      else {
        for(Iterator i = d_struct.getItems().iterator(); i.hasNext(); ) {
          Struct.Item item = (Struct.Item) i.next();
          convertIORToJava(d_writer,
                           item.getType(),
                           "_ior->" + item.getName(),
                           " _tmp_" + item.getName(),
                           mode, false /*is_client*/, d_context);
        }
      }
      d_writer.println("break;");
      d_writer.backTab();
      d_writer.println("}");
    }

    d_writer.backTab();
    d_writer.println("}"); //end switch 
    

    d_writer.backTab();
    d_writer.println("}"); //end if is_client
    
    //String lookup_name = Java.getFullJavaSymbolName(id).replace('.', '/');
    String signature = "(" + s.toString() + ")V";
    String args = a.toString();

    //initialize the java object
    d_writer.println();
    d_writer.writeCommentLine("initialization for struct " + Java.getFullJavaSymbolName(id));
    
    d_writer.println("jclass cls = sidl_" + IOR.getSymbolName(id) + "_get_class_id(env);");
    d_writer.println("static jmethodID mid_ctor = (jmethodID) NULL;");

    d_writer.println("if(mid_ctor == (jmethodID)NULL) {");
    d_writer.tab();
    d_writer.println("mid_ctor = (*env)->GetMethodID(env, cls, \"<init>\",");
    d_writer.printlnUnformatted("\"" + signature + "\");");
    d_writer.backTab();
    d_writer.println("}");
    
    d_writer.println("_ret = (*env)->NewObject(env, cls, mid_ctor" + args + ");");
    if(print_java_exit)
      d_writer.println("JAVA_EXIT:");
    d_writer.println("return _ret;");

    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();

    /***************************************************************************
     * generate holder conversion function
     * void <function_name>_holder(JNIEnv* env,
     *                             jobject obj,
     *                             <ior_struct_name> *value,
     *                             int mode,
     *                             int is_client
     *                            )
     */
    d_writer.println("void " + getI2JHolderFunctionName(id) + 
                     "(JNIEnv* env, jobject obj, " + IOR.getStructName(id) +
                     " *value, int mode, int is_client) {");
    d_writer.tab();

    /* we unconditionally replace the old object for the time being with a
     * freshly created one instead of modifying the original for inout
     * arguments
     */
    String class_descriptor = "L" + SymbolUtilities.getParentPackage(id.getFullName()) +
      "/" + Java.getJavaSymbolName(id); 

    d_writer.println("static int initialized = FALSE;");
    d_writer.println("static jmethodID mid_set = (jmethodID) NULL;");
    d_writer.println("static jclass cls_holder = (jclass) NULL;");

    d_writer.println("if(!initialized) {");
    d_writer.tab();
    d_writer.println("jclass cls = (*env)->GetObjectClass(env, obj);");
    d_writer.println("cls_holder = (*env)->NewGlobalRef(env, cls);");
    d_writer.println("mid_set = (*env)->GetMethodID(env, cls_holder, \"set\",");
    d_writer.printlnUnformatted("\"(" + class_descriptor + ";)V\");");
    d_writer.println("initialized = TRUE;");
    d_writer.backTab();
    d_writer.println("}");
    
    d_writer.println();

    d_writer.println("jobject holdee = " + getI2JFunctionName(id) + "(env, value, mode, is_client);");
    d_writer.println("(*env)->CallVoidMethod(env, obj, mid_set, holdee);");
    d_writer.backTab();
    d_writer.println("}");
  }


  /**
   * Generate C code that converts a Java proxy object for a struct to Babel's
   * IOR representation 
   */
  private void generateJ2ICode(LanguageWriterForC d_writer,
                               Context d_context)
    throws CodeGenerationException {

    //final int type = d_struct.getSymbolType();
    final SymbolID id = d_struct.getSymbolID();
    //final String lookup_name = Java.getFullJavaSymbolName(id).replace('.', '/');

    boolean print_java_exit = false;
    boolean contains_rarray = containsRarrayField(d_struct);
    
    /***************************************************************************
     * generate basic conversion function
     * void <function_name>(JNIEnv* env,
     *                      jobject obj,
     *                      <ior_struct_name> *,
     *                      int mode,
     *                      int is_client
     *                     ) 
     */
    d_writer.writeComment("convert Java object references of type " +
                          id.getFullName() +
                          " to Babel's IOR rpresentation.", false);

    d_writer.println("void " + getJ2IFunctionName(id) + 
                     "(JNIEnv* env, jobject obj, " +
                     IOR.getStructName(id) +
                     " *_ior, int mode, int is_client) {");
    
    d_writer.tab();

    d_writer.println("static int initialized = FALSE;");
    for(Iterator i = d_struct.getItems().iterator(); i.hasNext(); ) {
      Struct.Item item = (Struct.Item) i.next();
      d_writer.println("static jfieldID _fid_" + item.getName() + " = (jfieldID) NULL;");
    }

    d_writer.println("if(!initialized) {");
    d_writer.tab();
    d_writer.println("jclass cls = sidl_" + IOR.getSymbolName(id) + "_get_class_id(env);");
    //obtain a field ID to the particular member
    for(Iterator i = d_struct.getItems().iterator(); i.hasNext(); ) {
      Struct.Item item = (Struct.Item) i.next();
      d_writer.println("_fid_" + item.getName() +
                       "  = (*env)->GetFieldID(env, cls, \"" +
                       item.getName() + "\", \"" +
                       Java.getDescriptor(Java.getJavaFieldType(item, true)) + "\");");
    }
    d_writer.println();
    d_writer.println("initialized = TRUE;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();

    //call the appropriate JNI functions to access the value of fields
    for(Iterator i = d_struct.getItems().iterator(); i.hasNext(); ) {
      Struct.Item item = (Struct.Item) i.next();
      final String return_type = Java.getJavaReturnType(item.getType());
      d_writer.writeCommentLine("field " + item.getName());
      d_writer.println(Java.getJNINativeType(return_type) +
                       " _tmp_" + item.getName() + " = (*env)->" +
                       Java.getJNIFieldAccessor(return_type) +
                       "(env, obj, _fid_" + item.getName() + ");");
      print_java_exit |=
        item.getType().getDetailedType() == Type.CLASS ||
        item.getType().getDetailedType() == Type.INTERFACE;
    }    
    d_writer.println();
    
    //generate a simple jump table that exercises the right conversion for
    //each combination of argument mode and client/server flags
    d_writer.println("if(is_client) { /* client mode */");
    d_writer.tab();

    d_writer.println("switch(mode) {");
    d_writer.tab();

    String mode_tab_client[] = {
      "pre-call",   /* IN */
      "pre-call",   /* INOUT */
      "<invalid>",  /* OUT */
      "<invalid>"   /* RET */
    };
    
    for(int mode = 0; mode < modes.length; ++mode) {
      d_writer.println("case " + mode + ": /* " + modes[mode] + " */");
      d_writer.println("{");
      d_writer.tab();

      if(mode_tab_client[mode].compareTo("<invalid>") == 0) {
        d_writer.println("assert(FALSE && \"should be never reached\");");
      }
      else {
        d_writer.writeCommentLine(mode_tab_client[mode]);
        for(Iterator i = d_struct.getItems().iterator(); i.hasNext(); ) {
          Struct.Item item = (Struct.Item) i.next();
          convertJavaToIOR(d_writer,
                           item.getType(),
                           "_ior->" + item.getName(),
                           "_tmp_" + item.getName(),
                           mode, true /* is_client */,
                           d_context);
        }
      }
      
      d_writer.println("break;");      
      d_writer.backTab();
      d_writer.println("}");
      
    }
      
    d_writer.backTab();
    d_writer.println("}");

    //server mode 
    d_writer.backTab();
    d_writer.println("} else { /* server mode */");
    d_writer.tab();

    d_writer.println("switch(mode) {");
    d_writer.tab();

    String mode_tab_server[] = {
      "<invalid>",  /* IN */
      "post-call",  /* INOUT */
      "post-call",  /* OUT */
      "post-call"   /* RET */
    };
    
    for(int mode = 0; mode < modes.length; ++mode) {
      d_writer.println("case " + mode + ": /* " + modes[mode] + " */");
      d_writer.println("{");      
      d_writer.tab();

      if(mode_tab_server[mode].compareTo("<invalid>") == 0) {
        d_writer.println("assert(FALSE && \"should be never reached\");");
      }
      else if(contains_rarray && mode != MODE_INOUT) {
        d_writer.println("assert(FALSE && \"unsupported mode for structs with raw arrays.\");");
      }
      else {
        for(Iterator i = d_struct.getItems().iterator(); i.hasNext(); ) {
          Struct.Item item = (Struct.Item) i.next();
      
          convertJavaToIOR(d_writer,
                           item.getType(),
                           "_ior->" + item.getName(),
                           "_tmp_" + item.getName(),
                           mode, false /* is_client */,
                           d_context);
        }
      }
      d_writer.println("break;");
      d_writer.backTab();
      d_writer.println("}");      
    }

    d_writer.backTab();
    d_writer.println("}"); //end switch 
    
    d_writer.backTab();
    d_writer.println("}"); //end if is_client
      
    d_writer.println();
    
    if(print_java_exit)
      d_writer.println("JAVA_EXIT:");
    d_writer.println("return;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();

    /***************************************************************************
     * generate holder conversion function
     * void <function_name>_holder(JNIEnv* env,
     *                             jobject obj,
     *                             <ior_struct_name> *value),
     *                             int mode,
     *                             int is_client
     */
    d_writer.println("void " + getJ2IHolderFunctionName(id) + 
                     "(JNIEnv* env, jobject obj, " + IOR.getStructName(id) +
                     " *value, int mode, int is_client) {");
    d_writer.tab();

    String class_descriptor = "L" + SymbolUtilities.getParentPackage(id.getFullName()) +
      "/" + Java.getJavaSymbolName(id); 
    d_writer.println("static int initialized = FALSE;");
    d_writer.println("static jmethodID mid_get = (jmethodID) NULL;");
    d_writer.println("static jclass cls_holder = (jclass) NULL;");

    d_writer.println("if(!initialized) {");
    d_writer.tab();
    d_writer.println("jclass cls = (*env)->GetObjectClass(env, obj);");
    d_writer.println("cls_holder = (*env)->NewGlobalRef(env, cls);");
    d_writer.println("mid_get = (*env)->GetMethodID(env, cls_holder, \"get\",");
    d_writer.printlnUnformatted("\"()" + class_descriptor + ";\");");
    d_writer.println("initialized = TRUE;");
    d_writer.backTab();
    d_writer.println("}");

    d_writer.println();
    d_writer.println("jobject holdee = (*env)->CallObjectMethod(env, obj, mid_get);");
    d_writer.println(getJ2IFunctionName(id) + "(env, holdee, value, mode, is_client);");
    d_writer.backTab();
    d_writer.println("}");
  }
}



