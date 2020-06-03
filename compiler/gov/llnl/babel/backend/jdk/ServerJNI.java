//
// File:        ServerJNI.java
// Package:     gov.llnl.babel.backend.jdk
// Revision:    @(#) $Id: ServerJNI.java 7188 2011-09-27 18:38:42Z adrian $
// Description: write Java server (skel) JNI code that links Java with the IOR
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
import gov.llnl.babel.BabelConfiguration;
import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeConstants;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.Utilities;
import gov.llnl.babel.backend.c.C;
import gov.llnl.babel.backend.writers.LanguageWriterForC;
import gov.llnl.babel.symbols.Argument;
import gov.llnl.babel.symbols.Class;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.Type;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Class <code>ServerJNI</code> writes the JNI C code that links the Java
 * server to the IOR. The constructor takes a C language writer stream and
 * method <code>generateCode</code> writes the C JNI code for the specified
 * symbol to the output stream. The language writer output stream is not closed
 * by this object.
 */
public class ServerJNI {

  private LanguageWriterForC d_writer;

  private Extendable d_ext;

  private Context d_context;

  private static final String JRETURN = "_j_retval";

  private static final String CRETURN = "_retval";

  private static final String s_ensureOrderConstant[] = {
    "sidl_general_order", "sidl_column_major_order",
    "sidl_row_major_order" };

  private static final String s_epv = "epv";
  private static final String s_pre_epv = "pre_epv";
  private static final String s_post_epv = "post_epv";
  private static final String s_sepv = "sepv";
  private static final String s_pre_sepv = "pre_sepv";
  private static final String s_post_sepv = "post_sepv";


  /**
   * Create a <code>ServerJNI</code> object that will write symbol
   * information to the provided output language writer stream.
   * 
   * @param ext
   *           an interface or class symbol that needs source file for a Java
   *           extension class.
   * @param writer
   *           the output writer stream
   */
  public ServerJNI(Extendable ext, LanguageWriterForC writer,
                   Context context)
    throws CodeGenerationException
  {
    final String pre = "java.ServerJNI.ServerJNI: ";
    if (ext == null) {
      throw new CodeGenerationException(pre + "Unexpected null extendable "
					+ "object.");
    }
    d_ext = ext;
    d_writer = writer;
    d_context = context;

    SymbolID id = d_ext.getSymbolID();
    String filename = Java.getServerJNIFile(id);
    // Do not allow line breaks on the open parenthesis, just space and comma
    d_writer.setLineBreakString(", ");
    d_writer.writeBanner(d_ext, filename, CodeConstants.C_IS_IMPL,
                         CodeConstants.C_DESC_SJNI_PREFIX + id.getFullName());
    // Banner does not indicate "server" code
  }

  /**
   * Create a <code>ServerJNI</code> object that will write symbol
   * information to a created language writer stream.
   * 
   * @param ext
   *           an interface or class symbol that needs source file for a Java
   *           extension class.
   */
  public ServerJNI(Extendable ext,
                   Context context)
    throws CodeGenerationException 
  {
    final String pre = "java.ServerJNI.ServerJNI: ";
    if (ext == null) {
      throw new CodeGenerationException(pre + "Unexpected null extendable "
					+ "object");
    }

    d_ext = ext;
    d_context = context;
    SymbolID id = d_ext.getSymbolID();
    int type = d_ext.getSymbolType();
    String filename = Java.getServerJNIFile(id);
    d_writer = new LanguageWriterForC((d_context.getFileManager()).
                                      createFile(id, type, "SKELSRCS", 
                                                 filename),
                                      d_context);
    // Do not allow line breaks on the open parenthesis, just space and comma
    d_writer.setLineBreakString(", ");
    d_writer.writeBanner(d_ext, filename, CodeConstants.C_IS_IMPL,
                         CodeConstants.C_DESC_SJNI_PREFIX + id.getFullName());
    // Banner does not indicate "server" code
  }

  /**
   * This is a convenience utility function that writes the JNI server
   * information into the provided language writer output stream. The output
   * stream is not closed on exit. A code generation exception is thrown if an
   * error is detected, such as I/O trouble or a violation
   * 
   * @param symbol
   *           an interface or class symbol that needs source file for a Java
   *           extension class.
   * @param writer
   *           the output writer stream* of data type invariants.
   */
  public static void generateCode(Extendable symbol, LanguageWriterForC writer,
                                  Context context)
    throws CodeGenerationException {
    ServerJNI jni = new ServerJNI(symbol, writer, context);
    jni.generateCode();
  }

  /**
   * This is a convenience utility function that writes the JNI server
   * information into the provided language writer output stream. The output
   * stream is not closed on exit. A code generation exception is thrown if an
   * error is detected, such as I/O trouble or a violation of data type
   * invariants.
   * 
   * @param symbol
   *           an interface or class symbol that needs source file for a Java
   *           extension class.
   */
  public static void generateCode(Extendable symbol,
                                  Context context)
    throws CodeGenerationException {
    ServerJNI jni = new ServerJNI(symbol, context);
    jni.generateCode();
  }

  /**
   * Write Java JNI information for the provided symbol to the language writer
   * output stream provided in the class constructor.
   */
  public synchronized void generateCode() throws CodeGenerationException {
    generateIncludes();
    Java.generatePointerJLongConv(d_writer);
    generateJNISkelData();
    generateInitData();

    generateHandleUnidentifiableException();
    // generate skels to ctor, dtor and sidl defined methods here
    generateCtor();
    generateDtor();
    writeCallLoad();

    /*
     * Output the glue code for all methods in the class or interface. For an
     * interface, we must define all methods. For a class, we only define new
     * methods. (-Still applicable?? - SK)
     */
    List methods = (List) d_ext.getMethods(d_ext.isInterface());
    for (Iterator m = methods.iterator(); m.hasNext();) {
      Method method = (Method) m.next();
      if (d_ext.isInterface() || !method.isAbstract()) {
        if (IOR.generateHookMethods(d_ext, d_context)) {
          Method pre  = method.spawnPreHook();
          Method post = method.spawnPostHook();
          generateMethod(pre);
          generateMethod(method);
          generateMethod(post);
        } else {
          generateMethod(method);
        }
        d_writer.println();
      }
    }

    /*
     * Generate the (de)serializer functions for refered structs used in the
     * IOR files. IMHO, a struct is a "global" declaration and the corresponding
     * support functions should be associated with them. However, for the
     * time being, I'd like to avoid language specific modifications to the
     * IOR. Thus, we just forward calls to the actual implementation.
     */
    generateRMIAccessFunctions();
    
    generateSetEPV();
    if (d_ext.hasStaticMethod(true)) {
      generateSetSEPV();
    }

    if (!d_context.getConfig().getSkipRMI()) {
      writeRMIAccessFunctions();
    }

    d_writer.close();
  }

  /****************************************************************************
   * Private support methods: generateCode() immediate submethods
   ***************************************************************************/

  /**
   * Write support functions for struct (de)serialization
   */
  private void generateRMIAccessFunctions() 
    throws CodeGenerationException
  {
    Class cls = (Class) d_ext;
    SymbolID id = cls.getSymbolID();
    
    Set serializeSIDs = IOR.getStructSymbolIDs(cls, true);
    Set deserializeSIDs = IOR.getStructSymbolIDs(cls, false);

    for(Iterator i = serializeSIDs.iterator(); i.hasNext(); ) {
      SymbolID l_id = (SymbolID) i.next();
      d_writer.println("void");
      d_writer.println(IOR.getSkelSerializationName(id, l_id, true) + 
                       "(const " + IOR.getStructName(l_id) + " *strct, " +
                       "struct sidl_rmi_Return__object *pipe, const char *name, sidl_bool copyArg, " +
                       IOR.getExceptionFundamentalType() + "*exc) {");
      d_writer.tab();
      d_writer.println(JavaStructSource.getIORSerializerName(l_id) +
                       "(strct, sidl_io_Serializer__cast(pipe, exc), name, copyArg, exc);");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();
    }
    
    for(Iterator i = deserializeSIDs.iterator(); i.hasNext(); ) {
      SymbolID l_id = (SymbolID) i.next();
      d_writer.println("void");
      d_writer.println(IOR.getSkelSerializationName(id, l_id, false) + 
                       "(" + IOR.getStructName(l_id) + " *strct, " +
                       "struct sidl_rmi_Call__object *pipe, const char *name, sidl_bool copyArg," +
                       IOR.getExceptionFundamentalType() + " *exc) {");
      d_writer.tab();
      d_writer.println(JavaStructSource.getIORDeserializerName(l_id) +
                       "(strct, sidl_io_Deserializer__cast(pipe, exc), name, copyArg, exc);");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();
    }
  }
  
  /**
   * Prints the necessary includes statements: IOR, sidl_Java, etc. - Not sure
   * what all should be generated here? -SK
   */
  private void generateIncludes() throws CodeGenerationException {
    SymbolID id = d_ext.getSymbolID();
    d_writer.generateInclude("sidl_Java.h", false);
    d_writer.generateInclude("sidl_Loader.h", false);
    d_writer.generateInclude("sidl_String.h", false);
    d_writer.generateInclude(IOR.getHeaderFile(id), false);
    d_writer.generateInclude("babel_config.h", false);
    d_writer.generateInclude("sidl_Exception.h", false);
    d_writer.generateInclude("sidl_BaseException.h", true);
    d_writer.generateInclude("sidl_LangSpecificException.h", true);

    d_writer.generateSystemInclude("stdio.h");
    Java.generateStubIncludes(d_writer, d_ext);
    d_writer.println();
    Java.generateStructIncludes(d_writer, d_ext, d_context);
    d_writer.println();
  }

  /**
   * Generates the static data struct to cache the JNIEnv, the class and method
   * ID's for the Skel.
   */
  private void generateJNISkelData() throws CodeGenerationException {
    d_writer.writeComment("JNISkel data struct to cache JNIEnv, class, and "
                          + "needed MID's", true);
    d_writer.print("static struct ");
    d_writer.println(d_ext.getSymbolID().getFullName().replace('.', '_')
                     + "_jniSkel__data");
    d_writer.println("{");
    d_writer.tab();
    //    d_writer.println("JNIEnv *env;");
    d_writer.println("jclass thisCls;");
    d_writer.println("jmethodID ctorMID;");
    d_writer.println("jmethodID dtorMID;");

    // generate sidl defined MIDs
    List methods = (List) d_ext.getMethods(d_ext.isInterface());
    for (Iterator m = methods.iterator(); m.hasNext();) {
      Method method = (Method) m.next();
      if (!method.isAbstract()) {
        d_writer.println("jmethodID "
                         + Java.getJavaServerMethodName(method) + "MID;");
        if (IOR.generateHookMethods(d_ext, d_context)) {
          Method pre  = method.spawnPreHook();
          Method post = method.spawnPostHook();
          d_writer.println("jmethodID "
                           + Java.getJavaServerMethodName(pre) + "MID;");
          d_writer.println("jmethodID "
                           + Java.getJavaServerMethodName(post) + "MID;");
        }
      }
    }
    d_writer.backTab();
    d_writer.println("} s_data;");
    d_writer.println();
  }

  /**
   * Generates the init method for the static data struct. Method init's all
   * struct memebers
   */
  private void generateInitData() throws CodeGenerationException {
    d_writer.writeComment("Method to initialize struct members", true);
    d_writer.println("static void");
    d_writer.println("init_data(void)");
    d_writer.println("{");
    d_writer.tab();

    // Only run this initialization once per class, not on every new object.
    // Generate all inits for struct data
    generateInitEnv();
    generateInitCls();
    generateJNIMethodID("ctor", null, false);
    generateJNIMethodID("dtor", "()V", false);

    // sidl defined methods
    List methods = (List) d_ext.getMethods(d_ext.isInterface());
    for (Iterator m = methods.iterator(); m.hasNext();) {
      Method method = (Method) m.next();
      if (!method.isAbstract()) {
        if (IOR.generateHookMethods(d_ext, d_context)) {
          Method pre  = method.spawnPreHook();
          Method post = method.spawnPostHook();
          String preDescriptor = Java.getJavaServerSignature(pre);
          String postDescriptor = Java.getJavaServerSignature(post);
          String descriptor = Java.getJavaServerSignature(method);

          String name = pre.getLongMethodName() + "_Impl";
          generateJNIMethodID(name, preDescriptor, pre.isStatic());

          name = method.getLongMethodName() + "_Impl";
          generateJNIMethodID(name, descriptor, method.isStatic());

          name = post.getLongMethodName() + "_Impl";
          generateJNIMethodID(name, postDescriptor, post.isStatic());

        } else {
          String name = method.getLongMethodName() + "_Impl";
          String descriptor = Java.getJavaServerSignature(method);
          generateJNIMethodID(name, descriptor, method.isStatic());
        }
      }
    }
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  /**
   * Writes JNI function that calls back to the Java constructor of this class
   */
  private void generateCtor() throws CodeGenerationException {
    SymbolID id = d_ext.getSymbolID();
    Method m = IOR.getBuiltinMethod(IOR.CONSTRUCTOR, id,d_context,false);
    d_writer.writeComment("Constructor", true);
    // Steps:
    // 1. generate header, named approptiately as in set_epv
    d_writer.println("static void");
    d_writer.print(Java.getJNIFunction(m)
                   + "(");
    d_writer.println("struct " + id.getFullName().replace('.', '_')
                     + "__object *self, "+IOR.getExceptionFundamentalType()+" *_ex)");
    d_writer.println("{");
    d_writer.tab();

    generateInitEnv();
    d_writer.println("if (env != "+C.NULL+") {");
    d_writer.tab();
    d_writer.println("if ((*env)->PushLocalFrame(env, 8) < 0) goto JAVA_EXIT;");
    // 2. Call New Object jni function ("this")
    d_writer.println("{");
    d_writer.tab();
    d_writer.print("jobject this = ");
    String[] args1 = { "env", "s_data.thisCls", "s_data.ctorMID",
                       "POINTER_TO_JLONG(self)" };
    printCallToJNIMethod("NewObject", args1);
    d_writer.println(";");

    // 3. Exception code
    //generateJNIException("");
    //generateSIDLExceptionCatch(m);
    generateExceptionCheck("env");

    // 4. set self_data to NewGlobalReference(this)
    d_writer.print("self->d_data = ");
    String[] args2 = { "env", "this" };
    printCallToJNIMethod("NewGlobalRef", args2);
    d_writer.println(";");

    // 5. Delete local ref to this
    printCallToJNIMethod("DeleteLocalRef", args2);
    d_writer.println(";\n");

    // 6. Exception code
    //generateJNIException("");
    generateExceptionCheck("env");

    d_writer.backTab();
    d_writer.println("}");
    d_writer.println("(*env)->PopLocalFrame(env, NULL);");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println("return;");

    generateSIDLExceptionCatch(m);
    d_writer.backTab();
    d_writer.println("}\n");

    //Generate ctor2, empty method
    m = IOR.getBuiltinMethod(IOR.CONSTRUCTOR2, id,d_context,false);
    d_writer.writeComment("This constructor is called by the IOR when a user passes in"+
                          "their own private data. (new the Impl) Java doesn't need it,"+
                          " so it's empty", true);
    // Steps:
    // 1. generate header, named approptiately as in set_epv
    d_writer.println("static void");
    d_writer.print(Java.getJNIFunction(m)
                   + "(");
    d_writer.println("struct " + id.getFullName().replace('.', '_')
                     + "__object *self, void* ddata, "+IOR.getExceptionFundamentalType()+" *_ex)");
    d_writer.println("{ *_ex = NULL; }");
  }

  /**
   * Writes JNI finction that calls back to the "destructor" method of this
   * class.
   */
  private void generateDtor() throws CodeGenerationException {
    SymbolID id = d_ext.getSymbolID();
    Method m = IOR.getBuiltinMethod(IOR.CONSTRUCTOR, id,d_context,false);
    d_writer.writeComment("Deconstructing method", true);
    // Steps:
    // 1.generate header
    d_writer.println("static void");
    d_writer.print(Java.getJNIFunction(IOR.getBuiltinMethod(IOR.DESTRUCTOR,
                                                            id,d_context))
                   + "(");
    d_writer.println("struct " + id.getFullName().replace('.', '_')
                     + "__object *self, "+IOR.getExceptionFundamentalType()+" *_ex)");
    d_writer.println("{");
    d_writer.tab();

    d_writer.println("jfieldID j_ior = "+C.NULL+";");
    generateInitEnv();
    d_writer.println("if ((*env)->PushLocalFrame(env, 4) < 0) goto JAVA_EXIT;");


    // 2. Call back to the "dtor" method
    String [] args1 = {"env", "(jobject)self->d_data, s_data.dtorMID"};
    printCallToJNIMethod("CallVoidMethod", args1);
    d_writer.println(";");
    generateExceptionCheck("env");

    d_writer
      .println("j_ior = (*env)->GetFieldID(env, s_data.thisCls, \"d_ior\", "
               + "\"J\");");
    d_writer
      .println("(*env)->SetLongField(env, (jobject)self->d_data, j_ior, "
               + "POINTER_TO_JLONG("+C.NULL+"));");
    
    
    // 3. Exception code
    // generateJNIException("");

    // 4. Delete the Global refernce stored in self_data
    /*
     * String [] args2 = {"env", "(jobject)self->d_data"};
     * printCallToJNIMethod("DeleteGlobalRef", args2); d_writer.println(";");
     */
    d_writer.println("(*env)->DeleteGlobalRef(env, (jobject)self->d_data);");
    generateExceptionCheck("env");

    d_writer.println("(*env)->PopLocalFrame(env, NULL);");
    d_writer.println("return;");
    generateSIDLExceptionCatch(m);
    d_writer.backTab();
    d_writer.println("}\n");
  }

  private void writeCallLoad() throws CodeGenerationException {
    final SymbolID id = d_ext.getSymbolID();
    d_writer
      .println("void " + IOR.getSymbolName(id) + "__call_load(void) { ");
    d_writer.tab();
    d_writer.writeCommentLine("FIXME: Not implemented for Java yet");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  /**
   * Generate the method glue code that hooks the C IOR to Java Server.
   * 
   * Output the signature, the glue code to morph IOR arguments to Java
   * arguments, make the call, and then glue code to morph out and return
   * arguments from Java to IOR.
   */
  private void generateMethod(Method method) throws CodeGenerationException {
    generateMethodHeader(method);
    generateMethodBody(method);
  }

  /**
   * Writes the set_epv method needed to set values in epv fundtion table
   */
  private void generateSetEPV() throws CodeGenerationException {
    SymbolID id = d_ext.getSymbolID();
    d_writer.openCxxExtern();
    d_writer.println("void");
    d_writer.print(C.getSetEPVName(id) + "(" + IOR.getEPVName(id) + " *");
    d_writer.print(s_epv);
    if (IOR.generateHookMethods(d_ext, d_context)) {
      d_writer.println(",");
      d_writer.tab();
      d_writer.print(IOR.getPreEPVName(id) + " *" + s_pre_epv + ", ");
      d_writer.println(IOR.getPostEPVName(id) + " *" + s_post_epv + ")");
      d_writer.backTab();
    } else {
      d_writer.println(")");
    }
    d_writer.println("{");
    d_writer.tab();

    d_writer.writeCommentLine("initialize skel data struct");
    d_writer.println("init_data();");
    d_writer.println();

    d_writer.writeCommentLine("initialize builtin methods");
    initializeEPVMethodPointer(IOR.getBuiltinMethod(IOR.CONSTRUCTOR, id, 
                                                    d_context), id, s_epv);
    initializeEPVMethodPointer(IOR.getBuiltinMethod(IOR.CONSTRUCTOR2, id, 
                                                    d_context), id, s_epv); 
    initializeEPVMethodPointer(IOR.getBuiltinMethod(IOR.DESTRUCTOR, id, 
                                                    d_context), id, s_epv);
    d_writer.println();

    d_writer.writeCommentLine("initialize local methods");
    Iterator i = d_ext.getMethods(false).iterator();
    while (i.hasNext()) {
      Method m = (Method) i.next();
      if ((m.getDefinitionModifier() == Method.NORMAL)
          || (m.getDefinitionModifier() == Method.FINAL)) {
        initializeEPVMethodPointer(m, id, s_epv);
          if (IOR.generateHookMethods(d_ext, d_context)) {
            Method pre  = m.spawnPreHook();
            Method post = m.spawnPostHook();
            initializeEPVMethodPointer(pre, id, s_pre_epv);
            initializeEPVMethodPointer(post, id, s_post_epv);
          }
      }
    }
    d_writer.backTab();
    d_writer.println("}");
    d_writer.closeCxxExtern();
    d_writer.println();
  }

  /**
   * Writes the set_sepv method needed to set values in static epv fundtion
   * table.
   */
  private void generateSetSEPV() throws CodeGenerationException {
    SymbolID id = d_ext.getSymbolID();
    d_writer.openCxxExtern();
    d_writer.println("void");
    d_writer.print(C.getSetSEPVName(id) + "(" + IOR.getSEPVName(id));
    d_writer.print(" *" + s_sepv);
    if (IOR.generateHookMethods(d_ext, d_context)) {
      d_writer.println(",");
      d_writer.tab();
      d_writer.print(IOR.getPreSEPVName(id) + " *" + s_pre_sepv + ", ");
      d_writer.println(IOR.getPostSEPVName(id) + " *" + s_post_sepv + ")");
      d_writer.backTab();
    } else {
      d_writer.println(")");
    }

    d_writer.println("{");
    d_writer.tab();

    d_writer.writeCommentLine("initialize skel data struct");
    d_writer.println("init_data();");
    d_writer.println();

    d_writer.writeCommentLine("initialize local methods");
    Iterator i = d_ext.getMethods(false).iterator();
    while (i.hasNext()) {
      Method m = (Method) i.next();
      if (m.getDefinitionModifier() == Method.STATIC) {
        initializeEPVMethodPointer(m, id, s_sepv);
        if (IOR.generateHookMethods(d_ext, d_context)) {
          Method pre  = m.spawnPreHook();
          Method post = m.spawnPostHook();
          initializeEPVMethodPointer(pre, id,s_pre_sepv);
          initializeEPVMethodPointer(post, id,s_post_sepv);
        }
        
      }
    }
    d_writer.backTab();
    d_writer.println("}");
    d_writer.closeCxxExtern();
    d_writer.println();
  }

  /****************************************************************************
   * Private support methods: generateInitData() immediate submethods
   ***************************************************************************/
  private void generateInitEnv() {
    d_writer.println("JNIEnv* env = sidl_Java_getEnv();");
    /*d_writer.println("if (env == "+C.NULL+") {");
    d_writer.tab();
    d_writer.println("return;");
    d_writer.backTab();
    d_writer.println("}");
    */
  }

  private void generateInitCls() {
    d_writer.println("{");
    d_writer.tab();
    d_writer.println("jclass tempCls = "+C.NULL+";");
    d_writer.print("tempCls = ");
    String fullFilename = "\""
      + d_ext.getSymbolID().getFullName().replace('.', '/') + "_Impl\"";
    String[] args = { "env", fullFilename };
    printCallToJNIMethod("FindClass", args);
    d_writer.println(";");
    generateJNIException("");
    d_writer.println("");
    d_writer
      .println("s_data.thisCls = (*env)->NewGlobalRef(env, "
               + "tempCls);");
    d_writer.println("(*env)->DeleteLocalRef(env, tempCls);");
    generateJNIException("");
    d_writer.backTab();
    d_writer.println("}");
  }

  /**
   * Generates code to initialize a jmethodID for the given method. Handles
   * special case for jmethodID for constructors when given "ctor" in the name
   * parameter.
   */
  private void generateJNIMethodID(String name, String descriptor,
                                   boolean isStatic) {
    d_writer.print("s_data." + name + "MID = ");

    String[] args;
    if (name.equals("ctor")) {
      String[] args1 = { "env", "s_data.thisCls", "\"<init>\"",
                         "\"(J)V\"" };
      args = args1;
      printCallToJNIMethod("GetMethodID", args);
    } else {
      String[] args1 = { "env", "s_data.thisCls", "\"" + name + "\"",
                         "\"" + descriptor + "\"" };
      args = args1;
      if (isStatic) {
        printCallToJNIMethod("GetStaticMethodID", args);
      } else {
        printCallToJNIMethod("GetMethodID", args);
      }
    }
    d_writer.println(";");
    generateJNIException("");
  }

  /****************************************************************************
   * Private support methods: generateMethod() all submethods
   ***************************************************************************/
  /**
   * Writes the jni method header comments and signature for the given method.
   * All jni skel methods are static.
   */
  private void generateMethodHeader(Method m) throws CodeGenerationException {
    d_writer.writeComment(m, false);
    d_writer.print("static ");
    d_writer.println(getReturnString(m.getReturnType()));
    d_writer.println(Java.getJNIFunction(m) + "(");
    d_writer.tab();

    // Reference to the self pointer
    if (!m.isStatic())
      d_writer.print("struct "
                     + d_ext.getSymbolID().getFullName().replace('.', '_')
                     + "__object *self");

    // print remaining arguements
    List args = m.getArgumentList();
    if (args.isEmpty() && m.getThrows().isEmpty()) {
      if (m.isStatic()) {
        d_writer.print("void");
      }
      d_writer.println(")");
    } else {
      if (!m.isStatic())
        d_writer.println(",");
      for (Iterator a = args.iterator(); a.hasNext();) {
        Argument arg = (Argument) a.next();
        d_writer.print(IOR.getArgumentWithFormal(arg, d_context, true, false, false));
        if (a.hasNext() || !m.getThrows().isEmpty()) {
          d_writer.println(",");
        } else {
          d_writer.println(")");
        }
      }
    }
    // If this function throws an exception, include the extra argument
    if (!m.getThrows().isEmpty()) {
      printExceptionArgument();
      d_writer.println(")");
    }

    d_writer.backTab();
  }

  /**
   * Generates the implementation for the method. Preprocesses any parameters,
   * makes call back and postprocess the return
   */
  private void generateMethodBody(Method m) throws CodeGenerationException {
    d_writer.println("{");
    d_writer.tab();

    // implementation
    // String [] methodArgs =
    generateSetUpForCallback(m);
    generateCallback(m);
    // Delete local ref's to parameters???
    if (m.getThrows().isEmpty()) {
      generateJNIException(Java.getDefaultJNIReturnValue(m));
    } else {
      generateExceptionCheck("env");
    }

    processOutInout(m);
    generateReturn(m);

    generateSIDLExceptionCatch(m);
    d_writer.backTab();
    d_writer.println("}");
  }

  /**
   * Sets up for the jni callback. Declares local ref to the object if method
   * is instance. Converts all params to appropriate j<type> counter parts.
   * Declares local vars for processing the return value. Return variables from
   * the callbacks are identified as JRETURN and actual jni return values as
   * CRETURN.Returns a string array of the list of identifiers of the
   * appropriate j<type> needed for the callback.
   * 
   * NOTE: strings are trickier, need intermediate value to convert the
   * constant return value returned by the jni call GetStringUTFChars to the
   * variable return value.
   */
  private void generateSetUpForCallback(Method m)
    throws CodeGenerationException {
    List args = m.getArgumentList();
    // String [] methodArgs = new String [args.size()];

    generateInitEnv();

    // Check for instance method, if so declare and init jobject
    if (!m.isStatic()) {
      d_writer.writeCommentLine("Reference to the object");
      d_writer.println("jobject this = (jobject)(self->d_data);");
      d_writer.println();
    }

    // Process args
    declareJNIArgsList(m);

    // Declare needed vars for return
    // declareJNIReturn(m.getReturnType());

    d_writer.println("if ((*env)->PushLocalFrame(env, " +
                     Java.localJavaVars(m) + ") < 0) goto JAVA_EXIT;");

    // Initialize all in/inout j<type> variables from argument list
    if (!args.isEmpty()) {
      d_writer.writeCommentLine("Preprocess JNI variables");
      int i = 0;
      for (Iterator a = args.iterator(); a.hasNext();) {
        Argument arg = (Argument) a.next();
        if (arg.hasArrayOrderSpec())
          generateEnsureCall(arg);
        Java.preprocessServerJNIArgument(d_writer, arg, "_tmp_", d_context);
        i++;
      }
      d_writer.println();
    }

    return;
  }

  /**
   * This method generates a call to ensure. This is only called if a given
   * argument has "arg.hasArrayOrderSpec()" as true. This means that in the
   * sidl file this array was defined to have a certain order.
   */
  private void generateEnsureCall(Argument arg) throws CodeGenerationException {
    Type argType = arg.getType();
    String argName = arg.getFormalName();
    d_writer.print(Java.getJNIEnsureName(arg));
    if (arg.getMode() != Argument.OUT) {
      d_writer.println(" = " + Java.getEnsureArray(argType.getArrayType())
                       + "("
                       + ((arg.getMode() == Argument.IN) ? argName : "*" + argName)
                       + ", " + argType.getArrayDimension() + ", "
                       + s_ensureOrderConstant[argType.getArrayOrder()] + ");");
      if (arg.getMode() == Argument.INOUT) {
        d_writer.println("sidl__array_deleteRef((struct sidl__array *)(*" + argName + "));");
      }
    } else {
      d_writer.println(" = "+C.NULL+";");
    }
  }

  /**
   * This is the same as above, but called from processoutinout. Admittedly,
   * kinda a dumb name and system.
   */
  private void generateOutEnsureCall(Argument arg)
    throws CodeGenerationException {
    Type argType = arg.getType();
    String argName = arg.getFormalName();
    d_writer.print("*" + argName);
    d_writer.println(" = " + Java.getEnsureArray(argType.getArrayType())
                     + "(" + Java.getJNIEnsureName(arg) + ", "
                     + argType.getArrayDimension() + ", "
                     + s_ensureOrderConstant[argType.getArrayOrder()] + ");");
  }

  /**
   * Same as a bove but for the return variable
   */
  private void generateReturnEnsureCall(Type argType, String argName)
    throws CodeGenerationException {
    d_writer.print(Java.getJNIEnsureName(argName));
    d_writer.println(" = " + Java.getEnsureArray(argType.getArrayType())
                     + "(" + argName + ", " + argType.getArrayDimension() + ", "
                     + s_ensureOrderConstant[argType.getArrayOrder()] + ");");
    d_writer.println("sidl__array_deleteRef(" +
                     "(struct sidl__array *)" + argName + ");");
  }

  /**
   * Declares corresponding jni types for the argument list needed for the jni
   * callback. Inserts these names into the provided array. Also, declares any
   * other needed jni types that will be used to initialize these local
   * correspondants to the argument list.
   */
  private void declareJNIArgsList(Method m/* , String [] methodArgs */)
    throws CodeGenerationException {
    List args = m.getArgumentList();
    Type return_type = m.getReturnType();
    // if(!args.isEmpty()) {
    d_writer.writeCommentLine("Declare return and temp variables");

    for (Iterator a = args.iterator(); a.hasNext();) {
      Argument arg = (Argument) a.next();
      if (arg.getMode() == Argument.IN) {
        Java.declareJavaVariable(d_writer, arg.getType(), "_tmp_"
                                 + arg.getFormalName());
      } else {
        Java.declareServerInOutVariable(d_writer, arg.getType(), "_tmp_"
                                        + arg.getFormalName());
      }
      if (arg.hasArrayOrderSpec()) {
        Java.declareIORVariable(d_writer, arg.getType(), Java
                                .getJNIEnsureName(arg), d_context);
      }
    }
    if (return_type.getType() != Type.VOID) {
      Java.declareJavaVariable(d_writer, return_type, JRETURN);
      Java.declareIORVariable(d_writer, return_type, CRETURN, d_context);
      if (return_type.hasArrayOrderSpec())
        Java
          .declareIORVariable(d_writer, return_type, "_ensure_"
                              + CRETURN, d_context);
    }
    // If it throws an exception
    if (!m.getThrows().isEmpty())
      d_writer.println("*_ex = "+C.NULL+";");
    d_writer.println();

  }

  /**
   * Generate a return string for the specified SIDL type. Most of the SIDL
   * return strings are listed in the static structures defined at the start of
   * this class. Symbol types and array types require special processing.
   */
  private String getReturnString(Type type)
    throws CodeGenerationException {
    return IOR.getReturnString(type, d_context, true, false);
  }

  /**
   * Writes the callback to the Java source
   */
  private void generateCallback(Method m) throws CodeGenerationException {
    d_writer.writeCommentLine("Callback to java method");
    // Configure jni callback return type
    Type t = m.getReturnType();
    String jniCallType = getJNICallbackType(t);

    if (t.getDetailedType() != Type.VOID) {
      d_writer.print(JRETURN + " = ");
    }
    // configure args for jni callback
    // We have to send 3 extra args to the Java call, env, this, and the
    // methodID, but it's in a kinda funky order, but that's what up.
    List methodArgs = m.getArgumentList();
    String[] args = new String[(3 + methodArgs.size())];
    args[0] = "env";
    args[2] = "s_data." + Java.getJavaServerMethodName(m) + "MID";
    int i = 3;
    for (Iterator a = methodArgs.iterator(); a.hasNext();) {
      Argument arg = (Argument) a.next();
      args[i] = "_tmp_" + arg.getFormalName();
      ++i;
    }
    // print callback
    if (m.isStatic()) {
      args[1] = "s_data.thisCls";
      printCallToJNIMethod("CallStatic" + jniCallType + "Method", args);
    } else {
      args[1] = "this";
      printCallToJNIMethod("Call" + jniCallType + "Method", args);
    }
    d_writer.println(";");
  }

  /**
   * Fills in the <Type> for the Call<Type>Method jni callback
   */
  private String getJNICallbackType(Type t) {
    String jniCallType = null;
    switch (t.getDetailedType()) {
    case Type.VOID:
      jniCallType = "Void";
      break;
    case Type.ENUM:
      jniCallType = "Long";
      break;
    case Type.OPAQUE:
    case Type.FCOMPLEX:
    case Type.DCOMPLEX:
    case Type.CLASS:
    case Type.INTERFACE:
    case Type.ARRAY:
    case Type.STRING:
    case Type.SYMBOL:
    case Type.STRUCT:
      jniCallType = "Object";
      break;
    case Type.BOOLEAN:
      jniCallType = "Boolean";
      break;
    case Type.CHAR:
    case Type.INT:
    case Type.LONG:
    case Type.FLOAT:
    case Type.DOUBLE:
      jniCallType = Utilities.capitalize(t.getTypeString());
      break;
    default:
    }
    return jniCallType;
  }

  /**
   * Updates the values of the out/inout agrs to the method with those
   * corresponding j<type>s used in the callback.
   */
  private void processOutInout(Method m/* , String [] methodArgs */)
    throws CodeGenerationException {
    List args = m.getArgumentList();
    d_writer.writeCommentLine("Postprocess inout/out args");
    for (Iterator a = args.iterator(); a.hasNext();) {
      Argument arg = (Argument) a.next();
      Java.postprocessServerJNIArgument(d_writer, arg, "_tmp_",
                                        d_context);
      if (arg.hasArrayOrderSpec()) {
        if (arg.getMode() != Argument.IN) {
          generateOutEnsureCall(arg);
        }
        d_writer.println("sidl__array_deleteRef(" +
                         "(struct sidl__array *)" +
                         Java.getJNIEnsureName(arg) + ");");
      }
    }
  }

  /**
   * Writes code to post-process the jni return from the callback. Delete any
   * local refs and return.
   */
  private void generateReturn(Method m) throws CodeGenerationException {
    // STEPS:
    // 1.Convert java return to the jni required return type (Strings are
    // SPECIAL!!)
    Type return_type = m.getReturnType();
    Java.postprocessServerJNIReturn(d_writer, m.getReturnType(), JRETURN,
                                    CRETURN,
                                    d_context);
    // print return
    if (return_type.hasArrayOrderSpec())
      generateReturnEnsureCall(return_type, CRETURN);
    d_writer.println("(*env)->PopLocalFrame(env, NULL);");
    if (m.getReturnType().getDetailedType() != Type.VOID) {
      d_writer.println("return "
                       + (return_type.hasArrayOrderSpec() ? "_ensure_" + CRETURN
                          : CRETURN) + ";");
    } else {
      d_writer.println("return;");
    }
  }

  /**
   * Generates the out argument required for throwing sidl exceptions
   */
  private void printExceptionArgument() {
    d_writer.println(IOR.getExceptionFundamentalType() + 
                     "*_ex");
  }

  /**
   * Genereates code that throws exception, prints error message and returns
   * from the calling method
   */
  private void generateJNIException(String defaultReturn) {
    d_writer.print("if (");
    String[] args = { "env" };
    printCallToJNIMethod("ExceptionCheck", args);
    d_writer.println(") {");
    d_writer.tab();
    printCallToJNIMethod("ExceptionDescribe", args);
    d_writer.println(";");
    d_writer.println("return " + defaultReturn + ";");
    d_writer.backTab();
    d_writer.println("}");
  }

  private void generateExceptionCheck(String env) {
    d_writer.println("JAVA_CHECK("+env+");");
  }

  /**
   * Generates the code to catch possiable sidl exception call after a callback
   * to Java Impl files. First, checks for an exception, in one occured, it
   * checks if it was a sidl exception. If not, it prints and error.
   */
  private void generateSIDLExceptionCatch(Method m) {
    d_writer.println("JAVA_EXIT:");
    //    d_writer.println("if ((*env)->ExceptionCheck(env)) {");
    d_writer.println("{");
    d_writer.tab();
    d_writer.println("jthrowable javaEx = (*env)->ExceptionOccurred("
                     + "env);");
    d_writer.println("(*env)->ExceptionClear(env);");
    d_writer.println("if(sidl_Java_isSIDLException(env, javaEx)) {");
    d_writer.tab();
    d_writer.println("*_ex = sidl_Java_catch_SIDLException(env, javaEx, ");
    d_writer.tab();
    for (Iterator t = m.getThrows().iterator(); t.hasNext();) {
      d_writer.println("\"" + ((SymbolID) t.next()).getFullName() + "\",");
    }
    d_writer.backTab();
    d_writer.println(""+C.NULL+");");
    d_writer.backTab();

    d_writer.println("} ");
    d_writer.println("");
    d_writer.beginBlockComment(true);
    d_writer.println("If _ex is still Null, the exception was not expected.  ");
    d_writer.println("Throw a LangSpecificException instead.  ");
    d_writer.endBlockComment(true);

    d_writer.println("if(*_ex == "+C.NULL+") {");
    d_writer.tab();
    d_writer.println("handleUnidentifiableException(env, javaEx, _ex);");

    d_writer.backTab();
    d_writer.println("}");
    d_writer.writeComment("Return all inout and out arguments a "+C.NULL+"", false);
    List args = m.getArgumentList();
    for (Iterator a = args.iterator(); a.hasNext();) {
      Argument arg = (Argument) a.next();
      //If we throw an exception, set object out args to NULL
      if ((arg.getMode() == Argument.IN) &&
          arg.hasArrayOrderSpec()) {
        d_writer.println("sidl__array_deleteRef(" +
                         "(struct sidl__array *)" +
                         Java.getJNIEnsureName(arg) + ");");
      }
      if ((arg.getMode() == Argument.INOUT) &&
          arg.getType().isRarray()) {
        d_writer.println("sidl_Java_destroy_array(env, " +
                         "_tmp_" +
                         arg.getFormalName() + ");");
      }
      if ((arg.getType().getDetailedType() == Type.ARRAY ||
           arg.getType().getDetailedType() == Type.CLASS || 
           arg.getType().getDetailedType() == Type.INTERFACE) &&
          (arg.getMode() == Argument.INOUT || 
           arg.getMode() == Argument.OUT)) {
        d_writer.println("*" + arg.getFormalName() + " = "+C.NULL+"; ");
      }
    }
    if((m.getReturnType().getDetailedType() == Type.CLASS) ||
       (m.getReturnType().getDetailedType() == Type.INTERFACE)) {
      d_writer.println("_retval = "+C.NULL+"; ");
    }
    
        
    d_writer.println("(*env)->PopLocalFrame(env, NULL);");
    if (m.getReturnType().getDetailedType() != Type.VOID) {
      d_writer.println("return _retval;");
    } else {
      d_writer.println("return;");
    }
    
    d_writer.backTab();
    d_writer.println("} ");
    
  }
  
  private void generateHandleUnidentifiableException() {

    d_writer.println("static void handleUnidentifiableException(JNIEnv* env, jthrowable javaEx, sidl_BaseInterface* _ex) {");
    d_writer.tab();
    
    d_writer.println("sidl_BaseInterface _throwaway =  "+C.NULL+";");
    d_writer.println("sidl_BaseException _be =  "+C.NULL+";");
     d_writer.println("sidl_BaseInterface _tmp_ex = "+C.NULL+";");
    d_writer.println("sidl_LangSpecificException _le = "+
                     "sidl_LangSpecificException__create(&_throwaway);");

    d_writer.println("if(sidl_Java_isSIDLException(env, javaEx)) {");
    d_writer.tab();
    d_writer.println("_tmp_ex = sidl_Java_catch_SIDLException(env, javaEx, \"" +
                     BabelConfiguration.getBaseExceptionInterface()+"\", NULL);");
    d_writer.println("if(_tmp_ex != "+C.NULL+") {");
    d_writer.tab();
    d_writer.println("_be = sidl_BaseException__cast(_tmp_ex, &_throwaway);");
    d_writer.println("sidl_LangSpecificException_setNote(_le, "+
                     "sidl_BaseException_getNote(_be, &_throwaway), &_throwaway);"); 
    d_writer.println("sidl_LangSpecificException_addLine(_le, "+
                     "sidl_BaseException_getTrace(_be, &_throwaway), &_throwaway);");
    d_writer.println("sidl_BaseInterface_deleteRef(_tmp_ex, &_throwaway);");
    d_writer.println("sidl_BaseException_deleteRef(_be, &_throwaway);");


    d_writer.backTab();
    d_writer.println("} else {");
    d_writer.tab();
    d_writer.writeComment("If the thrown object isn't a BaseException either... "
                          , false);
    try {
      d_writer.pushLineBreak(false);
      d_writer.println("sidl_LangSpecificException_setNote(_le, "+
                       "\"Unidentifiable SIDL object thrown as exception from Java\""
                       +", &_throwaway);");
      d_writer.println("sidl_LangSpecificException_addLine(_le, "+
                       "\"in unknown at unknown\", &_throwaway);");
    }
    finally {
      d_writer.popLineBreak();
    }
    d_writer.backTab();
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("} else {");
    d_writer.tab();

    d_writer.println("jclass locCls = (*env)->FindClass(env, \"java/lang/Throwable\");");
    d_writer.println("jmethodID toString = (*env)->GetMethodID(env, locCls, "+
                     "\"toString\", \"()Ljava/lang/String;\");");
    d_writer.println("jstring note = "+C.NULL+";");
    d_writer.println("note = (*env)->CallObjectMethod(env, javaEx, toString);"); 
    d_writer.println("sidl_LangSpecificException_setNote(_le, "+
                     "sidl_Java_J2I_string(env, note), &_throwaway);"); 
    d_writer.println("sidl_LangSpecificException_addLine(_le, "+
                     "\"in unknown at unknown\", &_throwaway);");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println("*_ex = (sidl_BaseInterface) _le;");
    d_writer.backTab();
    d_writer.println("}");

  }

  /**
   * Prints a call to the given JNI method with the given list of arguements
   * through the JNIEnv pointer. Does not terminate call with a semicolon, in
   * case call is nested.
   */
  private void printCallToJNIMethod(String name, String[] args) {
    d_writer.print("(*env)->");
    d_writer.print(name + "(");
    for (int i = 0; i < args.length - 1; i++) {
      d_writer.print(args[i] + ", ");
    }
    d_writer.print(args[args.length - 1] + ")");
  }

  /**
   * For non-<code>static</code> methods, write an assignment statement to
   * initialize a particular membor of the entry point vector. Initialization
   * of <code>static</code> methods appears elsewhere.
   * 
   * @param m
   *           a description of the method to initialize
   * @param id
   *           the name of class that owns the method
   * @param epvname the name of the epv
   */
  private void initializeEPVMethodPointer(Method m, SymbolID id, String epvname) {
    final String methodName = m.getLongMethodName();
    switch (m.getDefinitionModifier()) {
    case Method.FINAL:
    case Method.NORMAL:
    case Method.STATIC:
      d_writer.print(epvname+"->");
      d_writer.print(IOR.getVectorEntry(methodName));
      d_writer.print(" = ");
      d_writer.print(Java.getJNIFunction(m));
      d_writer.println(";");
      break;
    case Method.ABSTRACT:
      d_writer.print(epvname+"->");
      d_writer.print(IOR.getVectorEntry(methodName));
      d_writer.println(" = "+C.NULL+";");
      break;
    default:
      /* do nothing */
      break;
    }
  }

  /**
   * Write functions to call each RMI fconnect and FGetURL function
   * 
   * @param cls
   *           the class for which an routine will be written.
   * @param writer
   *           the output writer to which the routine will be written.
   */
  private void writeRMIAccessFunctions() throws CodeGenerationException {
    Class cls = (Class) d_ext;
    SymbolID id = cls.getSymbolID();
    Set fconnectSIDs = IOR.getFConnectSymbolIDs(cls);
    //Set fcastSIDs = IOR.getFCastSymbolIDs(cls);
    d_writer.printlnUnformatted("#ifdef WITH_RMI");

    d_writer.openCxxExtern();

    for (Iterator i = fconnectSIDs.iterator(); i.hasNext(); ) {  
      SymbolID destination_id = (SymbolID) i.next();
      d_writer.println(C.getSymbolObjectPtr(destination_id) + " " + 
                       IOR.getSkelFConnectName(id,destination_id)+ 
                       "(const char* url, sidl_bool ar, sidl_BaseInterface *_ex) { ");
      d_writer.tab();
      d_writer.println("return "+C.getFullMethodName(destination_id, "_connectI")+"(url, ar, _ex);");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();
      
    }

    /*    for (Iterator i = fcastSIDs.iterator(); i.hasNext(); ) {
      SymbolID destination_id = (SymbolID) i.next();
      d_writer.println(C.getSymbolObjectPtr(destination_id) + " " + 
                       IOR.getSkelFCastName(id,destination_id)+ 
                       "(void* bi, "+ IOR.getExceptionFundamentalType() + "*_ex) { ");
      d_writer.tab();
      d_writer.println("return "+C.getFullMethodName(destination_id, "_rmicast")+"(bi, _ex);");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();

    }
    */
    d_writer.closeCxxExtern();
    d_writer.printlnUnformatted("#endif /*WITH_RMI*/");
  }
}
