//
// File:        ClientJNI.java
// Package:     gov.llnl.babel.backend.jdk
// Revision:    @(#) $Id: ClientJNI.java 7421 2011-12-16 01:06:06Z adrian $
// Description: write Java client (stub) JNI code that links Java with the IOR
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
import gov.llnl.babel.backend.Utilities;
import gov.llnl.babel.backend.CodeConstants;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.LevelComparator;
import gov.llnl.babel.backend.jdk.Java;
import gov.llnl.babel.backend.rmi.RMI;
import gov.llnl.babel.backend.rmi.RMIStubSource;
import gov.llnl.babel.backend.writers.LanguageWriterForC;
import gov.llnl.babel.symbols.Argument;
import gov.llnl.babel.symbols.Class;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Class <code>ClientJNI</code> writes the JNI C code that links the Java
 * client to the IOR.  The constructor takes a C language writer stream and
 * method <code>generateCode</code> writes the C JNI code for the specified
 * symbol to the output stream.  The language writer output stream is not
 * closed by this object.
 */
public class ClientJNI {
  private LanguageWriterForC d_writer;
  private Context d_context;

  /**
   * This is a convenience utility function that writes the JNI client
   * information into the provided language writer output stream.  The
   * output stream is not closed on exit.  A code generation exception
   * is thrown if an error is detected, such as I/O trouble or a violation
   * of data type invariants.
   */
  public static void generateCode(Extendable symbol, 
                                  LanguageWriterForC writer,
                                  Context context)
    throws CodeGenerationException {
    ClientJNI jni = new ClientJNI(writer, context);
    jni.generateCode(symbol);
  }

  /**
   * Create a <code>ClientJNI</code> object that will write symbol
   * information to the provided output language writer stream.
   */
  public ClientJNI(LanguageWriterForC writer,
                   Context context) {
    d_writer = writer;
    d_context = context;
  }

  /**
   * Return the number of methods that the client stub declares.
   */
  private int countMethods(Extendable ext)
  {
    final boolean isInterface = ext.isInterface();
    Collection methods = null;
    if(ext.isInterface()) {
      methods = (List) ext.getMethods(true);
    } else if (ext.isAbstract()) {
      methods = (List) ext.getAbstractAndLocalMethods();
    } else {
      methods = (List) ext.getMethods(false);
    }

    int count;
    if (isInterface || ext.isAbstract()) {
      count = methods.size();
    } else {
      count = 0;
      Iterator i = methods.iterator();
      while (i.hasNext()) {
        Method m = (Method)i.next();
        if (!m.isAbstract()) {
          ++count;
        }
      }
    }
    return count;
  }

  /**
   * Write Java JNI information for the provided symbol to the language
   * writer output stream provided in the class constructor.  This method
   * does not close the writer output stream.  Code is currently generated
   * only for sidl interfaces and classes, since enumerations and packages
   * do not require JNI support.
   */
  public void generateCode(Extendable ext) throws CodeGenerationException {
    if (ext == null) {
      throw new CodeGenerationException("Unexpected null extendable object");
    }

    /*
     * Output the file banner.  Include the IOR and sidl Java include files.
     */
    SymbolID id = ext.getSymbolID();
    String file = Java.getClientJNIFile(id);
    d_writer.writeBanner(ext, file, CodeConstants.C_IS_NOT_IMPL,
                         CodeConstants.C_DESC_CJNI_PREFIX + id.getFullName());

    d_writer.generateInclude("sidl_Java.h", false);
    d_writer.generateInclude("sidl_Loader.h", false);
    d_writer.generateInclude("sidl_String.h", false);
    d_writer.generateInclude(IOR.getHeaderFile(id), false);
    d_writer.generateInclude("babel_config.h", false);
    Java.generateStubIncludes(d_writer, ext);
    d_writer.println();
    Java.generateStructIncludes(d_writer, ext, d_context);
    d_writer.println();

    /* Output some preprocessor definitions for struct (de)serialization*/
    if (!d_context.getConfig().getSkipRMI()) {
      Set            references = ext.getSymbolReferences();
      if (references != null ) {
        Iterator i = references.iterator();
        while (i.hasNext()) {
          final SymbolID s_id  = (SymbolID)i.next();
          final Symbol   sym = Utilities.lookupSymbol(d_context, s_id);
          if (sym.getSymbolType() == Symbol.STRUCT) {
            d_writer.printUnformatted("#define RMI_" + IOR.getSymbolName(s_id));
            d_writer.printlnUnformatted("_serialize(s, serializer, name, copy, ex) \\");
            d_writer.printUnformatted("  " + JavaStructSource.getIORSerializerName(s_id));
            d_writer.printlnUnformatted("(s, sidl_io_Serializer__cast(serializer, ex), name, copy, ex)");
            d_writer.printUnformatted("#define RMI_" + IOR.getSymbolName(s_id));
            d_writer.printlnUnformatted("_deserialize(s, deserializer, name, copy, ex) \\");
            d_writer.printUnformatted("  " + JavaStructSource.getIORDeserializerName(s_id));
            d_writer.printlnUnformatted("(s, sidl_io_Deserializer__cast(deserializer, ex), name, copy, ex)");
          }
        }
      }
      d_writer.println();
    }
    
    /* Output defines that convert between pointes and jlongs. */
    Java.generatePointerJLongConv(d_writer);

    d_writer.println();
    if (!d_context.getConfig().getSkipRMI()) {
      //No Language specific RMI initialization for C
      d_writer.printlnUnformatted("#define "+RMI.LangSpecificInit());
      d_writer.println();

      /*
       * Output standard function stubs for methods in the object.
       */

      RMIStubSource.generateCode(ext, d_writer, d_context);
    }

    d_writer.writeComment(
                          "Function to extract IOR reference from the Java object.", false);
    d_writer.println("static " + IOR.getObjectName(id) + "* _get_ior(");
    d_writer.tab();
    d_writer.println("JNIEnv* env,");
    d_writer.println("jobject obj)");
    d_writer.backTab();
    d_writer.println("{");
    d_writer.tab();
    d_writer.println("void* ptr = NULL;");
    d_writer.println("static jmethodID mid = (jmethodID) NULL;");
    d_writer.println();
    d_writer.println("if (mid == (jmethodID) NULL) {");
    d_writer.tab();
    d_writer.println("jclass cls = (*env)->GetObjectClass(env, obj);");
    d_writer.println(
                     "mid = (*env)->GetMethodID(env, cls, \"_get_ior\", \"()J\");");
    d_writer.println("(*env)->DeleteLocalRef(env, cls);");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
    d_writer.println(
                     "ptr = JLONG_TO_POINTER((*env)->CallLongMethod(env, obj, mid));");
    d_writer.println("return (" + IOR.getObjectName(id) + "*) ptr;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
    // }

    /*
     * If this is a non-abstract class, then output a static pointer to
     * the IOR external data structure (for the constructor pointer).
     */
    if (!ext.isAbstract()) {
      d_writer.writeComment("External reference to IOR methods.", false);
      d_writer.println("static const "
                       + IOR.getExternalName(id)
                       + "* s_external = NULL;");
      d_writer.println();
      
      //If it's not abstract, it MUST be a class.
      Class cls = (Class) ext;
      if(cls.hasOverwrittenMethods()) {
        d_writer.writeComment("External reference to IOR methods.", false);
        d_writer.println("static const "
                       + IOR.getEPVName(cls.getParentClass().getSymbolID())
                       + "* superEPV = NULL;");
 
      }
    }

    /*
     * If this is a class with static methods, then output a pointer to the
     * static EPV structure that will be initialized at class loading.
     */
    if (ext.hasStaticMethod(true)) {
      d_writer.writeComment("External reference to static EPV.", false);
      d_writer.println("static const "
                       + IOR.getSEPVName(id)
                       + "* s_sepv = NULL;");
      d_writer.println();
    }

    /*
     * If this is a non-abstract class, then output the function that will
     * create an instance of the object and return the pointer as a jlong
     * reference.
     *
     * Now also includes the _create_ior to do this for remote objects
     */
    if (!ext.isAbstract()) {
      d_writer.writeComment(
                            "Create object instance and return reference.", false);
      d_writer.println("static jlong jni__create_ior(");
      d_writer.tab();
      d_writer.println("JNIEnv* env,");
      d_writer.println("jclass  cls)");
      d_writer.backTab();
      d_writer.println("{");
      d_writer.tab();
      d_writer.println(IOR.getExceptionFundamentalType() +
                       "_ex = NULL;");
      d_writer.println(
                       "jlong _res = POINTER_TO_JLONG((*s_external->createObject)(NULL, &_ex));");
      d_writer.println("(void) env;");
      d_writer.println("(void) cls;");
      generateCheckRuntimeException(Type.LONG);
      d_writer.println("return _res;");
      d_writer.backTab();
      d_writer.println();
      d_writer.println("}"); 
      d_writer.println();

    
      d_writer.writeComment("Create a remote object instance"+
                            " and return reference.", false);
      d_writer.println("static jlong jni__create_remote_ior(");
      d_writer.tab();
      d_writer.println("JNIEnv* env,");
      d_writer.println("jclass  cls,");
      d_writer.println("jstring url)");
      d_writer.backTab();
      d_writer.println("{");
      d_writer.tab();
      d_writer.println("jlong _res = 0;");


      d_writer.println();
      if (!d_context.getConfig().getSkipRMI()) {
        d_writer.printlnUnformatted("#ifdef WITH_RMI");
        d_writer.println();

        d_writer.println("struct sidl_BaseInterface__object *_ex = NULL;");
        d_writer.println("char* _tmp_url = sidl_Java_J2I_string(env, url);");      
        d_writer.println("_res = POINTER_TO_JLONG("+IOR.getSymbolName(id)+
                         "__remoteCreate(_tmp_url, &_ex));");
        generateCheckRuntimeException(Type.LONG);

        d_writer.println();
        d_writer.printlnUnformatted("#endif /*WITH_RMI*/");
      }
      d_writer.println();
      d_writer.println("(void) env;");
      d_writer.println("(void) cls;");

      d_writer.println("return _res;");


      d_writer.backTab();
      d_writer.println();
      d_writer.println("}"); 
      d_writer.println();
    
      if(!IOR.isSIDLSymbol(id)) {
        d_writer.writeComment("Create object instance and set ddata to be"+
                              " the passed in object.", false);
        d_writer.println("static jlong jni__wrap(");
        d_writer.tab();
        d_writer.println("JNIEnv* env,");
        d_writer.println("jclass  cls,");
        d_writer.println("jobject obj)");
        d_writer.backTab();
        d_writer.println("{");
        d_writer.tab();
        d_writer.println(IOR.getExceptionFundamentalType() +
                         "_ex = NULL;");
        d_writer.println("jlong _res = 0;");
        d_writer.println("void* g_data = (void*)(*env)->NewGlobalRef(env, obj);");
        
        d_writer.println(IOR.getObjectName(id) +
                         " *_ptr = (*s_external->createObject)(g_data, &_ex);");
        d_writer.println("(void) env;");
        d_writer.println("(void) cls;");
        generateCheckRuntimeException(Type.LONG);
        
        //d_writer.println("(*_ptr->d_epv->f_addRef)(_ptr, &_ex);");
        d_writer.println("_res = POINTER_TO_JLONG(_ptr);");  
        d_writer.println("return _res;");  
        d_writer.backTab(); 
        d_writer.println();
        d_writer.println("}");  
        d_writer.println();
      }
    }
    
    d_writer.writeComment("Connect to a remote object instance"+
                          " and return reference.", false);
    d_writer.println("static jlong jni__connect_remote_ior(");
    d_writer.tab();
    d_writer.println("JNIEnv* env,");
    d_writer.println("jclass  cls,");
    d_writer.println("jstring url)");
    d_writer.backTab();
    d_writer.println("{");
    d_writer.tab();
    d_writer.println("jlong _res = 0;");

    d_writer.println();
    if (!d_context.getConfig().getSkipRMI()) {
      d_writer.printlnUnformatted("#ifdef WITH_RMI");
      d_writer.println();

      d_writer.println("struct sidl_BaseInterface__object *_ex = NULL;");
      d_writer.println("char* _tmp_url = sidl_Java_J2I_string(env, url);");
    
      d_writer.println("_res = POINTER_TO_JLONG("+IOR.getSymbolName(id)+
                       "__remoteConnect(_tmp_url, 1, &_ex));");
      generateCheckRuntimeException(Type.LONG);

      d_writer.println();
      d_writer.printlnUnformatted("#endif /*WITH_RMI*/");
      d_writer.println();
    }
    d_writer.println("(void) env;");
    d_writer.println("(void) cls;");
    
    d_writer.println("return _res;");
    d_writer.backTab();
    d_writer.println();
    d_writer.println("}"); 
    d_writer.println();



    /*
     * Output the glue code for all methods in the class or interface.  For
     * an interface, we must define all methods.  For a class, we only define
     * new methods.
     */
    List methods = null;
    if(ext.isInterface()) {
      methods = (List) ext.getMethods(true);
    } else if (ext.isAbstract()) {
      methods = (List) ext.getAbstractAndLocalMethods();
    } else {
      methods = (List) ext.getMethods(false);
    }
    
    for (Iterator m = methods.iterator(); m.hasNext(); ) {
      Method method = (Method) m.next();
      generateMethod(ext, method, false);
      d_writer.println();
      if ( (method.getCommunicationModifier() == Method.NONBLOCKING ) &&
           !d_context.getConfig().getSkipRMI()) { 
        Method send = method.spawnNonblockingSend();
        generateMethod(ext, send,false);
        Method recv = method.spawnNonblockingRecv();
        generateMethod(ext,recv,false);
      }
    }
    if (!d_context.getConfig().getSkipRMI()) {
      //Generate EXEC builtin
      generateMethod(ext, IOR.getBuiltinMethod(IOR.EXEC,id,d_context,false), 
                     false);
    }
      
    //generate _set_hooks method
    generateMethod(ext, IOR.getBuiltinMethod(IOR.HOOKS,id,d_context,false), false);
    d_writer.println();
    if(ext.hasStaticMethod(true)) {
      generateMethod(ext, IOR.getBuiltinMethod(IOR.HOOKS,id,d_context,true), false);
      d_writer.println();
      
    }


    if(!ext.isAbstract()) {
      Class cls = (Class) ext;
      Collection overwritten = cls.getOverwrittenClassMethods();
      for(Iterator m = overwritten.iterator(); m.hasNext(); ) {
        Method method = (Method) m.next();
        generateMethod(ext, method, true);
        d_writer.println();
        if ( (method.getCommunicationModifier() == Method.NONBLOCKING ) &&
             !d_context.getConfig().getSkipRMI()) { 
          Method send = method.spawnNonblockingSend();
          generateMethod(ext, send,false);
          Method recv = method.spawnNonblockingRecv();
          generateMethod(ext,recv,false);
        } 
      }
    }


    /**
     *  If the symbol is of type sidl.BaseInterface, special JNI functions must be
     *  written for Object Array support.  (And registered, but thatis in a bit) 
     */

    String fSymName = Java.getFullJavaSymbolName(id);
    
    if(fSymName.compareTo(BabelConfiguration.getBaseInterface()) == 0) {
      generateBaseInterfaceArrayFunctions(id);
    }


    /*
     * Output the registration method.  This is the only external symbol.
     * First output the method signature and the arguments.
     */
    /*
     * Overwritten = supers.  These are registered twice (the old one and
     * the new one) so we must count them twice.
     */
    Collection overwritten = null;
    int numOverwritten = 0;
    if(!ext.isAbstract()) {
      Class cls = (Class) ext;
      overwritten = cls.getOverwrittenClassMethods();
      numOverwritten = overwritten.size();
    } else {
      //This is totally just so I can pretend it's not null.
      overwritten = (Collection) new ArrayList();
    }
    
    Collection nonblocking = new ArrayList();
    int numNonblocking = 0;
    if (!d_context.getConfig().getSkipRMI()) {
      for (Iterator m = methods.iterator(); m.hasNext(); ) {
        Method method = (Method) m.next();
        if ( method.getCommunicationModifier() == Method.NONBLOCKING ) { 
          numNonblocking += 2;
          nonblocking.add(method.spawnNonblockingSend());
          nonblocking.add(method.spawnNonblockingRecv());
        }
      }
    }


    final int nmethods = countMethods(ext) + (ext.isAbstract() ? 3 : 5) + 
      numOverwritten + numNonblocking + (ext.hasStaticMethod(true) ? 1 : 0) +
      ((!IOR.isSIDLSymbol(id) && !ext.isAbstract()) ? 1 : 0);
    
    d_writer.writeComment("Register JNI methods with the Java JVM.", false);
    d_writer.println("void " + Java.getRegisterFunction(id) + "(JNIEnv* env)");
    d_writer.println("{");
    d_writer.tab();
    if (nmethods > 0) {
      d_writer.println("JNINativeMethod methods[" +
                       Integer.toString(nmethods) + "];");
      d_writer.println("jclass cls;");
      d_writer.println();

      /*
       * If this is a non-abstract class, then output the code that dynamically
       * loads the external reference.  If there is an error, then throw an
       * unsatisfied link error.
       */
      if (!ext.isAbstract()) {
        if (BabelConfiguration.isSIDLBaseClass(id)) {
          d_writer.println("s_external = " + IOR.getExternalFunc(id) + "();");
        } else {
          final String ext_name = IOR.getExternalName(id);
          d_writer.printlnUnformatted("#ifdef SIDL_STATIC_LIBRARY");
          d_writer.println("s_external = " + IOR.getExternalFunc(id) + "();");
          d_writer.printlnUnformatted("#else");
          d_writer.println("s_external = ("+ext_name+"*)sidl_dynamicLoadIOR(\""+id.getFullName()+"\",\""+IOR.getExternalFunc(id)+"\") ;");
          d_writer.println("sidl_checkIORVersion(\"" + id.getFullName() + 
                       "\", s_external->d_ior_major_version, s_external->d_ior_minor_version, " +
                       Integer.toString(IOR.MAJOR_VERSION) + ", " +
                       Integer.toString(IOR.MINOR_VERSION) + ");");
          d_writer.printlnUnformatted("#endif");
          d_writer.println();
          //The superEPV stuff should really be here, but we need to work
          //out some IOR problems, so it's have to go in the super methods
          //for now.
          
        }
      }
      
      /*
       * If this class has a static method, then initialize the static pointer.
       */
      if (ext.hasStaticMethod(true)) {
        d_writer.println("s_sepv = (*(s_external->getStaticEPV))();");
        d_writer.println();
      }

      /*
       * Initialize the method array of names, signatures, and pointers in
       * preparation for registering the methods.
       */
      int idx = 0;

      d_writer.println("methods["+idx+"].name      = \"_connect_remote_ior\";");
      d_writer.println("methods["+idx+"].signature = \"(Ljava/lang/String;)J\";");
      d_writer.println("methods["+idx+"].fnPtr     = (void *)jni__connect_remote_ior;");
      ++idx;


      if (!ext.isAbstract()) {
        
        d_writer.println("methods["+idx+"].name      = \"_create_remote_ior\";");
        d_writer.println("methods["+idx+"].signature = \"(Ljava/lang/String;)J\";");
        d_writer.println("methods["+idx+"].fnPtr     = (void *)jni__create_remote_ior;");
        ++idx;

        if(!IOR.isSIDLSymbol(id)) {
          d_writer.println("methods["+idx+"].name      = \"_wrap\";");
          d_writer.println("methods["+idx+"].signature = \"("+Java.getDescriptor(id.getFullName())+")J\";");
          d_writer.println("methods["+idx+"].fnPtr     = (void *)jni__wrap;");
          ++idx;
        }

        d_writer.println("methods["+idx+"].name      = \"_create_ior\";");
        d_writer.println("methods["+idx+"].signature = \"()J\";");
        d_writer.println("methods["+idx+"].fnPtr     = (void *)jni__create_ior;");
        ++idx;
      }

      if (!d_context.getConfig().getSkipRMI()) {
        //Builtins
        generateRegistryEntry(IOR.getBuiltinMethod(IOR.EXEC, id, d_context, false),
                              idx, false);
        ++idx;      
      }
      generateRegistryEntry(IOR.getBuiltinMethod(IOR.HOOKS, id, d_context, false),
                            idx, false);
      ++idx;      
      if (ext.hasStaticMethod(true)) {
        generateRegistryEntry(IOR.getBuiltinMethod(IOR.HOOKS, id, d_context, true),
                              idx, false);
        ++idx;      
      }


      Collection allButSupers =new ArrayList();
      allButSupers.addAll(methods);
      allButSupers.addAll(nonblocking);
      
      for (Iterator m = allButSupers.iterator(); m.hasNext(); ) {
        Method method = (Method) m.next();
        generateRegistryEntry(method, idx, false);
        ++idx;
        
      }
      d_writer.println();
      
      //Output supers!
      for (Iterator m = overwritten.iterator(); m.hasNext(); ) {
        Method method = (Method) m.next();
        generateRegistryEntry(method, idx, true);
        ++idx;
      }
      d_writer.println();


      /*
       * Register the methods with the Java virtual machine.
       */
      String lookup_name = Java.getFullJavaSymbolName(id).replace('.', '/');
      if (ext.isInterface()) {
        lookup_name = lookup_name + "$Wrapper";
      }
      d_writer.println("cls = (*env)->FindClass(env, \"" + lookup_name + "\");");
      d_writer.println("if (cls) {");
      d_writer.tab();
      d_writer.println("(*env)->RegisterNatives(env, cls, methods, "
                       + String.valueOf(nmethods)
                       + ");");
      d_writer.println("(*env)->DeleteLocalRef(env, cls);");



      d_writer.backTab();
      d_writer.println("}");
      /**
       *  If the symbol is of type sidl.BaseInterface, special JNI functions must be
       *  registered for Object Array support.   
       */
      
      String fSymName1 = Java.getFullJavaSymbolName(id);
      
      if(fSymName1.compareTo(BabelConfiguration.getBaseInterface()) == 0) {
        registerBaseInterfaceArrayFunctions(id);
      }
    }
    else {
      d_writer.writeComment("Intentionally empty: no methods to register",
                            false);
    }
    d_writer.backTab();
    d_writer.println("}");
  }

  private void generateRegistryEntry(Method method, int idx, boolean isSuper) {
    String prefix = "methods[" + Integer.toString(idx) + "].";
    String methodPrefix = "";
    if(isSuper) { methodPrefix = "super_"; }
    d_writer.println(prefix
                     + "name      = \""
                     + methodPrefix + method.getCorrectMethodName()
                     + "\";");
    try {
      d_writer.pushLineBreak(false);
      d_writer.println(prefix
                       + "signature = \""
                       + Java.getJavaSignature(method)
                       + "\";");
    }
    finally {
      d_writer.popLineBreak();
    }
    d_writer.println(prefix
                     + "fnPtr     = (void *)"
                     + (isSuper ? Java.getSuperJNIFunction(method) : Java.getJNIFunction(method))
                     + ";");
  }

  /**
   * Generate the method glue code that hooks the Java to the C IOR.  Output
   * the signature, the glue code to morph Java arguments to IOR arguments,
   * make the call, and then glue code to morph out and return arguments from
   * IOR to Java.
   */
  private void generateMethod(Extendable ext, Method method, boolean isSuper)
    throws CodeGenerationException {
    boolean throws_exception = !method.getThrows().isEmpty();
    SymbolID id = ext.getSymbolID();
    /*
     * Output the method signature and the agrument list.  Static methods do
     * not take an object reference but rather a class reference.  All user
     * arguments are prefixed with "_arg_" and the temporary variables are
     * prefixed with "_tmp_" to avoid name collisions with other arguments.
     */
    d_writer.writeComment(method, false);

    Type return_type = method.getReturnType();
    d_writer.println("static " + Java.getJNIReturnType(return_type));
 
    if(isSuper)
      d_writer.println(Java.getSuperJNIFunction(method) + "(");
    else
      d_writer.println(Java.getJNIFunction(method) + "(");

    d_writer.tab();
    d_writer.println("JNIEnv* env,");
    if (!method.isStatic()) {
      d_writer.print("jobject obj");
    } else {
      d_writer.print("jclass  cls");
    }

    /*
     * Declare the remaining arguments in the argument list.
     */
    List args = method.getArgumentList();
    d_writer.println(args.isEmpty() ? ")" : ",");

    for (Iterator a = args.iterator(); a.hasNext(); ) {
      Argument arg = (Argument) a.next();
      d_writer.print(Java.getJNIFormalArgument(arg));
      d_writer.println(a.hasNext() ? "," : ")");
    }

    /*
     * Done with the argument list - decrease indentation, print the curly
     * brace, and then indent for the method implementation.
     */
    d_writer.backTab();
    d_writer.println("{");
    d_writer.tab();

    /*
     * Begin the method body with the declarations required for the return
     * type, object reference (if not a static method), and the temporary
     * variables used to translate Java constructs to C.
     */
    d_writer.writeComment("Declare return and temporary variables.", false);
    if (!method.isStatic()) {
      d_writer.println(IOR.getObjectName(id) + "* _ior = NULL;");
    }
    for (Iterator a = args.iterator(); a.hasNext(); ) {
      Argument arg = (Argument) a.next();
      Java.declareIORVariable(d_writer, arg, "_tmp_", d_context);
    }
    if (return_type.getType() != Type.VOID) {
      Java.declareIORVariable(d_writer, return_type, "_ior_res", d_context);
      Java.declareJavaVariable(d_writer, return_type, "_res");
    }
    
    if (throws_exception) {
      d_writer.println(IOR.getExceptionFundamentalType() +
                       "_ex = NULL;");
    }
    d_writer.println();

    /*
     * Grab the pointer to the IOR object and pre-process all of the method
     * arguments to convert data from Java to C.
     */
    d_writer.writeComment("Preprocess Java types and convert into IOR.", false);
    if (!method.isStatic()) {
      d_writer.println("_ior = _get_ior(env, obj);");
    }
    if(isSuper) {
      d_writer.writeCommentLine("Prep the superEPV");
      d_writer.println("superEPV = s_external->getSuperEPV();");
      d_writer.println();
    }
    for (Iterator a = args.iterator(); a.hasNext(); ) {
      Argument arg = (Argument) a.next();
      /**Check that OUT/INOUT args are not null (bad if they are)
       */
      checkNull(arg, return_type);
      Java.preprocessJNIArgument(d_writer, arg, "_tmp_", d_context);
    }
    d_writer.println();

    /*
     * Call the IOR method.  If there is a return type, then store the return
     * value in <code>_ior_result</code>.  We need to lookup the appropriate
     * function pointer in the structure and then pass the appropriate this
     * pointer.  Pass each of the arguments to the output method.
     */
    d_writer.writeComment("Call the IOR method through the EPV.", false);
    if (return_type.getType() != Type.VOID) {
      d_writer.print("_ior_res = ");
    }
    d_writer.print("(*(");

    if(isSuper)
      d_writer.print("superEPV");
    else if(method.isStatic())
      d_writer.print("s_sepv");
    else
      d_writer.print("_ior->d_epv");

    //    d_writer.print(method.isStatic() ? "s_sepv" : "_ior->d_epv");
    d_writer.print("->f_" + method.getLongMethodName() + "))(");
    if (method.isStatic() && args.isEmpty() && !throws_exception) {
      d_writer.println(");");
    } else {
      d_writer.println();
      d_writer.tab();
      if (isSuper) {
        Class cls = (Class) ext;
        d_writer.print("("+IOR.getObjectName(cls.getParentClass().getSymbolID())+"*)_ior");
        d_writer.println(args.isEmpty() && !throws_exception ? ");" : ",");
      } else if (!method.isStatic()) {
        d_writer.print(ext.isInterface()/*method.isAbstract()*/ ? "_ior->d_object" : "_ior");
        d_writer.println(args.isEmpty() && !throws_exception ? ");" : ",");
      }
      for (Iterator a = args.iterator(); a.hasNext(); ) {
        Argument arg = (Argument) a.next();
        if (arg.getType().isStruct() || arg.getMode() != Argument.IN) {
          d_writer.print("&");
        }
        d_writer.print("_tmp_" + arg.getFormalName());
        d_writer.println((a.hasNext() || throws_exception) ? "," : ");");
      }
      if (throws_exception) {
        d_writer.println("&_ex);");
      }
      d_writer.backTab();
    }
    d_writer.println();

    /* (Hackish)
     * reset the SEPV if it changes
     */
    if(method.equals(IOR.getBuiltinMethod(IOR.HOOKS, id, d_context, true))) {
      d_writer.println("s_sepv = (*(s_external->getStaticEPV))();");
    }

    /*
     * Post-process the method arguments if necessary.  This will involve
     * processing the INOUT and OUT arguments and the return argument.
     */
    d_writer.writeComment("Postprocess OUT, INOUT, returns, and exceptions.", false);

    if (throws_exception) {
      Object [] exceptions = method.getThrows().toArray();
      Arrays.sort(exceptions, new LevelComparator(d_context.getSymbolTable()));

      d_writer.println("if(_ex) {");
      d_writer.tab();
      d_writer.println("sidl_Java_CheckException(");
      d_writer.tab();
      d_writer.println("env,");
      d_writer.println("_ex,");
      for( int i=0; i<exceptions.length; ++i) {
        SymbolID exid = (SymbolID)exceptions[i];
        d_writer.println("\"" + exid.getFullName() + "\",");
      }
      //for (Iterator t = exceptions.iterator(); t.hasNext(); ) {
      //  d_writer.println("\"" + ((SymbolID) t.next()).getFullName() + "\",");
      //}
      d_writer.println("NULL);");
      d_writer.backTab();
      if (return_type.getType() != Type.VOID) {
        d_writer.println();
        d_writer.println("return _res;");
      } else {
        d_writer.println("return;");
      }
      d_writer.backTab();
      d_writer.println("}");
    }
    for (Iterator a = args.iterator(); a.hasNext(); ) {
      Argument arg = (Argument) a.next();
      Java.postprocessJNIArgument(d_writer, arg, "_tmp_", d_context);
    }
    if (return_type.getType() != Type.VOID) {
      Java.postprocessJNIReturn(d_writer, return_type, "_ior_res", "_res");
    }

    if (return_type.getType() != Type.VOID) {
      d_writer.println();
      d_writer.println("return _res;");
    }
    
    //Check if any of the arguments are objects (if they are, exceptions may
    //be thrown.
    boolean objectArgs = false;
    if(return_type.getDetailedType() == Type.CLASS ||
       return_type.getDetailedType() == Type.INTERFACE) {
      objectArgs = true;
    } else {
      for (Iterator a = args.iterator(); a.hasNext(); ) {
        Argument arg = (Argument) a.next();
        if(arg.getType().getDetailedType() == Type.CLASS ||
           arg.getType().getDetailedType() == Type.INTERFACE) {
          objectArgs = true;
          break;
        }
      }
    }

    if(objectArgs) {
      d_writer.println("JAVA_EXIT:");
      if (return_type.getType() != Type.VOID) {
        d_writer.println();
        d_writer.println("return _res;");
      } else {
        d_writer.println("return;");
      }
    }

    /*
     * Done with the method body.  Decrease tab level and close the curly brace.
     */
    d_writer.backTab();
    d_writer.println("}");
  }

  /**
   *  The function writes code to check if the argument in question in an 
   *  INOUT or OUT argument.  These types of arguments must be passed in 
   *  a Holder argument.  These types of arguments must be passed in a Holder
   *  class.  This Holder class may NOT be Null.  If it is null,
   *  this code will throw a NullPointerException.
   */

  private void checkNull(Argument arg, Type retType) {
    if (arg.getMode() == Argument.INOUT) {
	    
      d_writer.println("if(_arg_"+ arg.getFormalName() + "== NULL) {" );
      d_writer.tab();
      d_writer.println("jclass newExcCls = (*env)->FindClass(env, \"java/lang/RuntimeException\");");
      d_writer.println("(*env)->ThrowNew(env, newExcCls, \"Null Holder Sent as INOUT Argument\");");
	    

      if(retType.getType() != Type.VOID) {
        d_writer.println("return 0;");
      } else {
        d_writer.println("return;");
      }
      d_writer.println("}");
      d_writer.backTab();
    } else if (arg.getMode() == Argument.OUT) {

      d_writer.println("if(_arg_"+ arg.getFormalName() + "== NULL) {" );
      d_writer.tab();
      d_writer.println("jclass newExcCls = (*env)->FindClass(env, \"java/lang/RuntimeException\");");
      d_writer.println("(*env)->ThrowNew(env, newExcCls, \"Null Holder Sent as OUT Argument\");");

      if(retType.getType() != Type.VOID) {
        d_writer.println("return 0;");
      } else {
        d_writer.println("return;");
      }
	    
      d_writer.println("}");
      d_writer.backTab();
	    
    }
  }
    
  /**
   *  These functions (generateBaseInterfaceArrayFunctions(SymbolID id) and 
   *  registerBaseInterfaceArrayFunctions(SymbolID id) are specific functions
   *  ONLY for sidl.BaseInterface.  The handle all Client Side Java Object arrays
   *  by delegation.  (All java object arrays actually contain a BaseInterface
   *  array that actually contains the data and handles everything.)
   *  This code is based on the code generated by babel/runtime/sidl/gen-java-arrays.sh
   *  I generated it there, then used a lame perl script to port it to the
   *  writer.println() format, so  Remember to change these concurrently.
   *
   *  There are, however, two changes.  _get and _set had to be modified to work
   *  with objects.
   *
   *  Anyway, as a one time thing, this code kinda sucks, and is not at all general.
   *  Be warned.
   */
    
  private void  generateBaseInterfaceArrayFunctions(SymbolID id) {
	


	
    String generalSidlName = BabelConfiguration.getBaseInterface().replace('.', '_');
    String sidlBaseClass = BabelConfiguration.getBaseClass().replace('.', '_');

    d_writer.beginBlockComment(true);
    d_writer.println("Local utility function to extract the array pointer from the Java object.");
    d_writer.println("Extract the d_array long data member and convert it to a pointer.");
    d_writer.endBlockComment(true);
    d_writer.println("static struct "+generalSidlName+"__array* "+generalSidlName+"__getptr(");
    d_writer.println("JNIEnv* env,");
    d_writer.println("jobject obj)");
    d_writer.println("{");
    d_writer.tab();

    d_writer.println("void* ptr = NULL;");
    d_writer.println("static jfieldID s_array_field = NULL;");
    d_writer.println("");
    d_writer.println("if (s_array_field == NULL) {");
    d_writer.tab();
    d_writer.println("jclass cls = (*env)->GetObjectClass(env, obj);");
    d_writer.println("s_array_field = (*env)->GetFieldID(env, cls, \"d_array\", \"J\");");
    d_writer.println("(*env)->DeleteLocalRef(env, cls);");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println("");
    d_writer.println("ptr = JLONG_TO_POINTER((*env)->GetLongField(env, obj, s_array_field));");
    d_writer.println("return (struct "+generalSidlName+"__array*) ptr;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println("");

    /*******************************/
    d_writer.beginBlockComment(true);
    d_writer.println("Local utility function to set the array pointer on the Java object.");
    d_writer.println("Convert the pointer to a long value and set the d_array data member.");
    d_writer.endBlockComment(true);
    d_writer.println("static void "+generalSidlName+"__setptr(");
    d_writer.println("JNIEnv* env,");
    d_writer.println("jobject obj,");
    d_writer.println("struct "+generalSidlName+"__array* array)");
    d_writer.println("{");
    d_writer.tab();
    d_writer.println("static jfieldID s_array_field = NULL;");
    d_writer.println("");
    d_writer.println("if (s_array_field == NULL) {");
    d_writer.tab();
    d_writer.println("jclass cls = (*env)->GetObjectClass(env, obj);");
    d_writer.println("s_array_field = (*env)->GetFieldID(env, cls, \"d_array\", \"J\");");
    d_writer.println("(*env)->DeleteLocalRef(env, cls);");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println("");
    d_writer.println("(*env)->SetLongField(env, obj, s_array_field, POINTER_TO_JLONG(array));");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println("");

    /**********************************/
    /**
     *  _get native function interface.  This is somewhat interesting becuase
     *  it becomes rather complex.  First, understand that we have two seperate
     *  object models going on here.  The Java object and the sidl object (IOR).
     *  The Java object hold a pointer to the IOR representation (the sidl object)
     *  This sidl object is what is actually stored in the Array, so, when we  
     *  _set an object in the array, we actually take the IOR pointer out of
     *  the Java object and store the pointer in the sidl array.
     *
     *  Therefore when we GET the object, we must create a Java object of the 
     *  correct type to hold the IOR pointer (sidl object.)  This consists of
     *  getting the pointer from the array, casting the pointer to BaseClass,
     *  createing a Java object of type BaseClass, and putting the pointer there.
     *  We then pass back the new object, where, in the Java code it is babel 
     *  casted to the same type as the array.  (Java babel cast creates a 
     *  new Java object as well as casting the IOR pointer.)
     *  The user might have to Java cast it again to the desired type.
     *  
     */
    d_writer.beginBlockComment(true);	
    d_writer.println("Native routine to fetch the specified value from the array.  The");
    d_writer.println("specified array index/indices must be lie between the array lower");
    d_writer.println("upper bounds (inclusive).  Invalid indices will have unpredictable");
    d_writer.println("(but almost certainly bad) results.");
    d_writer.endBlockComment(true);
    d_writer.println("static jobject "+generalSidlName+"__get(");
    d_writer.println("JNIEnv* env,");
    d_writer.println("jobject obj,");
    d_writer.println("jint i,");
    d_writer.println("jint j,");
    d_writer.println("jint k,");
    d_writer.println("jint l,");
    d_writer.println("jint m,");
    d_writer.println("jint n,");
    d_writer.println("jint o)");
    d_writer.println("{");
    d_writer.tab();
    d_writer.println("struct "+generalSidlName+"__array* array = "+generalSidlName+"__getptr(env, obj);");
    d_writer.println("struct "+generalSidlName+"__object* value = NULL;");
    d_writer.println("struct "+sidlBaseClass+"__object* bclass = NULL;");
    d_writer.println("jobject _res = NULL;");

    d_writer.println(IOR.getExceptionFundamentalType() +
                     "_ex = NULL, *_ex2 = NULL;");
    d_writer.println("int32_t a[7];");
    d_writer.println("a[0] = i;");
    d_writer.println("a[1] = j;");
    d_writer.println("a[2] = k;");
    d_writer.println("a[3] = l;");
    d_writer.println("a[4] = m;");
    d_writer.println("a[5] = n;");
    d_writer.println("a[6] = o;"); 
    d_writer.println();
	
    d_writer.println("value = "+generalSidlName+"__array_get(array, a);");
    d_writer.println("bclass = "+sidlBaseClass+"__cast(value, &_ex);");
    d_writer.println("if (value) {");
    d_writer.tab();
    d_writer.println(generalSidlName + "_deleteRef(value, &_ex2);");
    d_writer.backTab();
    d_writer.println("}");
    generateCheckRuntimeException(Type.SYMBOL);
    d_writer.println("_ex = _ex2;");
    generateCheckRuntimeException(Type.SYMBOL);

    d_writer.println("_res = sidl_Java_I2J_ifc(env, bclass, \""+BabelConfiguration.getBaseInterface()+"\", FALSE);");
    d_writer.println("return _res;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println("");
    /************************/
    d_writer.beginBlockComment(true);
    d_writer.println("Native routine to set the specified value in the array.  The");
    d_writer.println("specified array index/indices must be lie between the array lower");
    d_writer.println("upper bounds (inclusive).  Invalid indices will have unpredictable");
    d_writer.println("(but almost certainly bad) results.");
    d_writer.endBlockComment(true);

    d_writer.println("static void "+generalSidlName+"__set(");
    d_writer.println("JNIEnv* env,");
    d_writer.println("jobject obj,");
    d_writer.println("jint i,");
    d_writer.println("jint j,");
    d_writer.println("jint k,");
    d_writer.println("jint l,");
    d_writer.println("jint m, ");
    d_writer.println("jint n, ");
    d_writer.println("jint o,");
    d_writer.println("jobject value)");
    d_writer.println("{");
    d_writer.tab();
    d_writer.println("struct "+generalSidlName+"__array* array = "+generalSidlName+"__getptr(env, obj);");
    d_writer.println();
    d_writer.println("sidl_BaseInterface vpointer = sidl_Java_J2I_cls(env, value, FALSE);");
    d_writer.println("int32_t a[7];");
    d_writer.println("a[0] = i;");
    d_writer.println("a[1] = j;");
    d_writer.println("a[2] = k;");
    d_writer.println("a[3] = l;");
    d_writer.println("a[4] = m;");
    d_writer.println("a[5] = n;");
    d_writer.println("a[6] = o;"); 

    d_writer.println(""+generalSidlName+"__array_set(array, a, vpointer);");
	
    d_writer.backTab();
    d_writer.println("}");

    /***************************/
    d_writer.beginBlockComment(true);
    d_writer.println("Native routine to reallocate data in the array.  The specified array");
    d_writer.println("dimension and indices must match and be within valid ranges (e.g., the");
    d_writer.println("upper bounds must be greater than or equal to lowe rbounds.  Invalid");
    d_writer.println("indices will have unpredictable (but almost certainly bad) results.");
    d_writer.println("This routine will deallocate the existing array data if it is not null.");
    d_writer.endBlockComment(true);
    d_writer.println("static void "+generalSidlName+"__reallocate(");
    d_writer.println("JNIEnv* env,");
    d_writer.println("jobject obj,");
    d_writer.println("jint dim,");
    d_writer.println("jarray lower,");
    d_writer.println("jarray upper,");
    d_writer.println("jboolean isRow)");
    d_writer.println("{");
    d_writer.tab();
    d_writer.println("jint l["+BabelConfiguration.getMaximumArray()+"];");
    d_writer.println("jint u["+BabelConfiguration.getMaximumArray()+"];");
    d_writer.println("struct "+generalSidlName+"__array* array = NULL;");
    d_writer.println("struct "+generalSidlName+"__array* orig = sidl_BaseInterface__getptr(env,obj);");
    d_writer.println("int32_t i, lenl, lenu = 0;");
    d_writer.println("");
    d_writer.println("for(i=0; i < "+BabelConfiguration.getMaximumArray()+"; ++i) {");
    d_writer.tab();
    d_writer.println("l[i] = 0;");
    d_writer.println("u[i] = 0;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println("lenl = (*env)->GetArrayLength(env, lower);");
    d_writer.println("lenu = (*env)->GetArrayLength(env, upper);");
    d_writer.println("");
    d_writer.println("");
    d_writer.println("sidl_BaseInterface__array_deleteRef(orig);");
    d_writer.println("(*env)->GetIntArrayRegion(env, lower, 0, lenl, (int32_t*)l);");
    d_writer.println("(*env)->GetIntArrayRegion(env, upper, 0, lenu, (int32_t*)u);");
    d_writer.println("");
    d_writer.println("");
    d_writer.println("if(isRow) ");
    d_writer.tab();
    d_writer.println("array = "+generalSidlName+"__array_createRow((int32_t) dim, (int32_t*) l, (int32_t*) u);");
    d_writer.backTab();
    d_writer.println("else");
    d_writer.tab();
    d_writer.println("array = "+generalSidlName+"__array_createCol((int32_t) dim, (int32_t*) l, (int32_t*) u);");
    d_writer.backTab();
    d_writer.println("");
    d_writer.println("");
    d_writer.println(generalSidlName+"__setptr(env, obj, array);");
    d_writer.backTab();
    d_writer.println("}");

    /*********************************************/
    d_writer.println("");
    d_writer.beginBlockComment(true);
    d_writer.println("");
    d_writer.println("Native function copies every element of an array to an array of the");
    d_writer.println("same dimensionality, size, and type ");
      d_writer.println("");
    d_writer.endBlockComment(true);
    d_writer.println("static void "+generalSidlName+"__copy(");
    d_writer.println("JNIEnv* env,");
    d_writer.println("jobject obj,");
    d_writer.println("jobject dest)");
    d_writer.println("{");
    d_writer.tab();
    d_writer.println("struct "+generalSidlName+"__array* csrc = "+generalSidlName+"__getptr(env, obj);");
    d_writer.println("struct "+generalSidlName+"__array* cdest = "+generalSidlName+"__getptr(env, dest);");
    d_writer.println("");
    d_writer.println("if(csrc && cdest) {");
    d_writer.tab();
    d_writer.println(""+generalSidlName+"__array_copy(csrc,cdest);");
    d_writer.println("} ");
    d_writer.backTab();
    d_writer.println("");
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("");
    d_writer.beginBlockComment(true);
    d_writer.println("");
    d_writer.println("Native function slices arrays in various ways");
    d_writer.println("");
    d_writer.endBlockComment(true);
    /************************************/
    d_writer.println("static jobject "+generalSidlName+"__slice(");
    d_writer.println("JNIEnv* env,");
    d_writer.println("jobject obj, ");
    d_writer.println("jint dimen, ");
    d_writer.println("jintArray numElem, ");
    d_writer.println("jintArray srcStart, ");
    d_writer.println("jintArray srcStride,");
    d_writer.println("jintArray newStart)");
    d_writer.println("{");
    d_writer.tab();
    d_writer.println("struct "+generalSidlName+"__array* array = "+generalSidlName+"__getptr(env, obj);");
    d_writer.println("struct "+generalSidlName+"__array* ret_ptr = NULL;");
    d_writer.println("jobject ret_array = NULL;");
    d_writer.println("int cnumElem[7];");
    d_writer.println("int csrcStart[7];");
    d_writer.println("int csrcStride[7];");
    d_writer.println("int cnewStart[7]; ");
    d_writer.println("int i = 0; ");
    d_writer.println("for(i = 0; i < 7; ++i) {  /*Make sure the array are clean*/ ");
    d_writer.tab();
    d_writer.println("cnumElem[i] = 0;");
    d_writer.println("csrcStart[i] = 0;");
    d_writer.println("csrcStride[i] = 0;");
    d_writer.println("cnewStart[i] = 0;");
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("");
    d_writer.println("if(numElem == NULL)");
    d_writer.println("return NULL;  /*If numElem is NULL, we need to return Null, that\'s bad.*/");
    d_writer.println("");
    d_writer.println("(*env)->GetIntArrayRegion(env, numElem, 0,");
    d_writer.println("(*env)->GetArrayLength(env, numElem), cnumElem);  ");
    d_writer.println("");
    d_writer.println("if(srcStart != NULL)");
    d_writer.println("(*env)->GetIntArrayRegion(env, srcStart, 0, ");
    d_writer.println("(*env)->GetArrayLength(env, srcStart), csrcStart);  ");
    d_writer.println("");
    d_writer.println("if(srcStride != NULL)");
    d_writer.println("(*env)->GetIntArrayRegion(env, srcStride, 0, ");
    d_writer.println("(*env)->GetArrayLength(env, srcStride), csrcStride);  ");
    d_writer.println("");
    d_writer.println("if(newStart != NULL)");
    d_writer.println("(*env)->GetIntArrayRegion(env, newStart, 0, ");
    d_writer.println("(*env)->GetArrayLength(env, newStart), cnewStart);    ");
    d_writer.println("");
    d_writer.println("");
    d_writer.println("if (array != NULL) {");
    d_writer.tab();
    d_writer.println("ret_ptr = "+generalSidlName+"__array_slice(array, dimen, cnumElem, csrcStart, csrcStride, cnewStart);");
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("");
    d_writer.println("if(ret_ptr != NULL) {  ");
    d_writer.tab();
    d_writer.println("ret_array = sidl_Java_I2J_new_array(env,ret_ptr, \"sidl.BaseInterface$Array\");");
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("");
    d_writer.println("return ret_array;");
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("");
    d_writer.println("");
    d_writer.beginBlockComment(true);
    d_writer.println("");
    d_writer.println("Native routine to decrement the current array reference count.");
    d_writer.println("");
    d_writer.endBlockComment(true);
    /*******************************/

    d_writer.println("");
    d_writer.beginBlockComment(true);
    d_writer.println("");
    d_writer.println("Native routine converts Java array of object to sidl array");
    d_writer.println("I\'m sorry it\'s so complex, but the nature of working with the");
    d_writer.println("JNI is such that I couldn\'t think of anyway but a big case statement");
    d_writer.println("to make this work efficently");
    d_writer.println("");
    d_writer.endBlockComment(true);
    d_writer.println("static void "+generalSidlName+"__fromArray(");
    d_writer.println("JNIEnv* env,");
    d_writer.println("jobject obj,");
    d_writer.println("jobjectArray orray,");
    d_writer.println("jint dim,");
    d_writer.println("jintArray _upper,");
    d_writer.println("jboolean isRow)");
    d_writer.println("{");
    d_writer.tab();
    d_writer.println("int i,j,k,l,m,n,o;");
    d_writer.println("jint upper[7];");
    d_writer.println("jint lower[7] = {0,0,0,0,0,0,0};");
    d_writer.println("jclass exceptionClass = NULL;");
    d_writer.println("jintArray _lower = (*env)->NewIntArray(env, 7);");
    d_writer.println("(*env)->GetIntArrayRegion(env, _upper, 0, 7, upper);");
    d_writer.println("(*env)->SetIntArrayRegion(env, _lower, 0, 7, lower);");
    d_writer.println(""+generalSidlName+"__reallocate(env, obj, dim, _lower, _upper, isRow);");
    d_writer.println("(*env)->DeleteLocalRef(env, _lower);");
    d_writer.println("");
    d_writer.println("switch(dim) {");
    d_writer.tab();
    d_writer.println("case 1:");
    d_writer.println("for(i = 0; i <= upper[0]; ++i) {");
    d_writer.tab();
    d_writer.println("jobject tmp = (*env)->GetObjectArrayElement(env,orray, i);");
    d_writer.println(""+generalSidlName+"__set(env,obj,i,0,0,0,0,0,0,tmp);");
    d_writer.println("(*env)->DeleteLocalRef(env, tmp);");
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("break;");
    d_writer.println("case 2:");
    d_writer.println("for(j = 0; j <= upper[0]; ++j) {");
    d_writer.tab();
    d_writer.println("jobjectArray orray1 = (*env)->GetObjectArrayElement(env,orray, j);");
    d_writer.println("for(i = 0; i <= upper[1]; ++i) {");
    d_writer.tab();
    d_writer.println("jobject tmp = (*env)->GetObjectArrayElement(env,orray1, i);");
    d_writer.println(""+generalSidlName+"__set(env,obj,j,i,0,0,0,0,0,tmp);");
    d_writer.println("(*env)->DeleteLocalRef(env, tmp);");
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("(*env)->DeleteLocalRef(env, orray1);");
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("break;");
    d_writer.println("case 3:");
    d_writer.println("for(k = 0; k <= upper[0]; ++k) {");
    d_writer.tab();
    d_writer.println("jobjectArray orray1 = (*env)->GetObjectArrayElement(env,orray, k);");
    d_writer.println("for(j = 0; j <= upper[1]; ++j) {");
    d_writer.tab();
    d_writer.println("jobjectArray orray2 = (*env)->GetObjectArrayElement(env,orray1, j);");
    d_writer.println("for(i = 0; i <= upper[2]; ++i) {");
    d_writer.tab();
    d_writer.println("jobject tmp = (*env)->GetObjectArrayElement(env,orray2, i);");
    d_writer.println(""+generalSidlName+"__set(env,obj,k,j,i,0,0,0,0,tmp);");
    d_writer.println("(*env)->DeleteLocalRef(env, tmp);");
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("(*env)->DeleteLocalRef(env, orray2);");
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("(*env)->DeleteLocalRef(env, orray1);");
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("");
    d_writer.println("break;");
    d_writer.println("case 4:");
    d_writer.println("for(l = 0; l <= upper[0]; ++l) {");
    d_writer.tab();
    d_writer.println("jobjectArray orray1 = (*env)->GetObjectArrayElement(env,orray, l);");
    d_writer.println("for(k = 0; k <= upper[1]; ++k) {");
    d_writer.tab();
    d_writer.println("jobjectArray orray2 = (*env)->GetObjectArrayElement(env,orray1, k);");
    d_writer.println("for(j = 0; j <= upper[2]; ++j) {");
    d_writer.tab();
    d_writer.println("jobjectArray orray3 = (*env)->GetObjectArrayElement(env,orray2, j);");
    d_writer.println("for(i = 0; i <= upper[3]; ++i) {");
    d_writer.tab();
    d_writer.println("jobject tmp = (*env)->GetObjectArrayElement(env,orray3, i);");
    d_writer.println(""+generalSidlName+"__set(env,obj,l,k,j,i,0,0,0,tmp);");
    d_writer.println("(*env)->DeleteLocalRef(env, tmp);");
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("(*env)->DeleteLocalRef(env, orray3);");
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("(*env)->DeleteLocalRef(env, orray2);");
    d_writer.println("}   ");	
    d_writer.backTab();
    d_writer.println("(*env)->DeleteLocalRef(env, orray1);");
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("(*env)->DeleteLocalRef(env, orray);");
    d_writer.println("");
    d_writer.println("break;");
    d_writer.println("case 5:");
    d_writer.println("for(m = 0; m <= upper[0]; ++m) {");
    d_writer.tab();
    d_writer.println("jobjectArray orray1 = (*env)->GetObjectArrayElement(env,orray, m);   ");
    d_writer.println("for(l = 0; l <= upper[1]; ++l) {");
    d_writer.tab();
    d_writer.println("jobjectArray orray2 = (*env)->GetObjectArrayElement(env,orray1, l);");
    d_writer.println("for(k = 0; k <= upper[2]; ++k) {");
    d_writer.tab();
    d_writer.println("jobjectArray orray3 = (*env)->GetObjectArrayElement(env,orray2, k);");
    d_writer.println("for(j = 0; j <= upper[3]; ++j) {");
    d_writer.tab();
    d_writer.println("jobjectArray orray4 = (*env)->GetObjectArrayElement(env,orray3, j);");
    d_writer.println("for(i = 0; i <= upper[4]; ++i) {");
    d_writer.tab();
    d_writer.println("jobject tmp = (*env)->GetObjectArrayElement(env,orray4, i);");
    d_writer.println(""+generalSidlName+"__set(env,obj,m,l,k,j,i,0,0,tmp);");
    d_writer.println("(*env)->DeleteLocalRef(env, tmp);");
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("(*env)->DeleteLocalRef(env, orray4);");
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("(*env)->DeleteLocalRef(env, orray3);");
    d_writer.println("}   ");
    d_writer.backTab();
    d_writer.println("(*env)->DeleteLocalRef(env, orray2);");
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("(*env)->DeleteLocalRef(env, orray1);");
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("(*env)->DeleteLocalRef(env, orray);");
    d_writer.println("");
    d_writer.println("break;");
    d_writer.println("case 6:");
    d_writer.println("for(n = 0; n <= upper[0]; ++n) {");
    d_writer.tab();
    d_writer.println("jobjectArray orray1 = (*env)->GetObjectArrayElement(env,orray, n);   ");
    d_writer.println("for(m = 0; m <= upper[1]; ++m) {");
    d_writer.tab();
    d_writer.println("jobjectArray orray2 = (*env)->GetObjectArrayElement(env,orray1, m);   ");
    d_writer.println("for(l = 0; l <= upper[2]; ++l) {");
    d_writer.tab();
    d_writer.println("jobjectArray orray3 = (*env)->GetObjectArrayElement(env,orray2, l);");
    d_writer.println("for(k = 0; k <= upper[3]; ++k) {");
    d_writer.tab();
    d_writer.println("jobjectArray orray4 = (*env)->GetObjectArrayElement(env,orray3, k);");
    d_writer.println("for(j = 0; j <= upper[4]; ++j) {");
    d_writer.tab();
    d_writer.println("jobjectArray orray5 = (*env)->GetObjectArrayElement(env,orray4, j);");
    d_writer.println("for(i = 0; i <= upper[5]; ++i) {");
    d_writer.tab();
    d_writer.println("jobject tmp = (*env)->GetObjectArrayElement(env,orray5, i);");
    d_writer.println(""+generalSidlName+"__set(env,obj,n,m,l,k,j,i,0,tmp);");
    d_writer.println("(*env)->DeleteLocalRef(env, tmp);");
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("(*env)->DeleteLocalRef(env, orray5);");
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("(*env)->DeleteLocalRef(env, orray4);");
    d_writer.println("}   ");
    d_writer.backTab();
    d_writer.println("(*env)->DeleteLocalRef(env, orray3);");
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("(*env)->DeleteLocalRef(env, orray2);");
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("(*env)->DeleteLocalRef(env, orray1);");
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("(*env)->DeleteLocalRef(env, orray);");
    d_writer.println("");
    d_writer.println("break;");
    d_writer.println("case 7:");
    d_writer.println("for(o = 0; o <= upper[0]; ++o) {");
    d_writer.tab();
    d_writer.println("jobjectArray orray1 = (*env)->GetObjectArrayElement(env,orray, o);   ");
    d_writer.println("for(n = 0; n <= upper[1]; ++n) {");
    d_writer.tab();
    d_writer.println("jobjectArray orray2 = (*env)->GetObjectArrayElement(env,orray1, n);   ");
    d_writer.println("for(m = 0; m <= upper[2]; ++m) {");
    d_writer.tab();
    d_writer.println("jobjectArray orray3 = (*env)->GetObjectArrayElement(env,orray2, m);   ");
    d_writer.println("for(l = 0; l <= upper[3]; ++l) {");
    d_writer.tab();
    d_writer.println("jobjectArray orray4 = (*env)->GetObjectArrayElement(env,orray3, l);");
    d_writer.println("for(k = 0; k <= upper[4]; ++k) {");
    d_writer.tab();
    d_writer.println("jobjectArray orray5 = (*env)->GetObjectArrayElement(env,orray4, k);");
    d_writer.println("for(j = 0; j <= upper[5]; ++j) {");
    d_writer.tab();
    d_writer.println("jobjectArray orray6 = (*env)->GetObjectArrayElement(env,orray5, j);");
    d_writer.println("for(i = 0; i <= upper[6]; ++i) {");
    d_writer.tab();
    d_writer.println("jobject tmp = (*env)->GetObjectArrayElement(env,orray6, i);");
    d_writer.println(""+generalSidlName+"__set(env,obj,o,n,m,l,k,j,i,tmp);");
    d_writer.println("(*env)->DeleteLocalRef(env, tmp);");
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("(*env)->DeleteLocalRef(env, orray6);");
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("(*env)->DeleteLocalRef(env, orray5);");
    d_writer.println("}   ");
    d_writer.backTab();
    d_writer.println("(*env)->DeleteLocalRef(env, orray4);");
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("(*env)->DeleteLocalRef(env, orray3);");
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("(*env)->DeleteLocalRef(env, orray2);");
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("(*env)->DeleteLocalRef(env, orray1);");
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("(*env)->DeleteLocalRef(env, orray);");
    d_writer.println("break;");
    d_writer.println("default:");
    d_writer.println("exceptionClass = (*env)->FindClass(env, \"java/lang/IllegalArgumentException\");");
    try {
      d_writer.pushLineBreak(false);
      d_writer.println("(*env)->ThrowNew(env, exceptionClass,\"JNI:_fromArray: Array dimensions must be between 1-7!\");");
    }
    finally {
      d_writer.popLineBreak();
    }
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.backTab();

  }

  private void registerBaseInterfaceArrayFunctions(SymbolID id) {

    String generalSidlName = BabelConfiguration.getBaseInterface().replace('.', '_');
    String lookupName = BabelConfiguration.getBaseInterface().replace('.', '/');
    d_writer.println("{");
    d_writer.tab();
    d_writer.println("JNINativeMethod methods1[6];");
    d_writer.println("jclass cls1;");
    d_writer.println("");

    d_writer.println("methods1[0].name      = \"_get\";");
    d_writer.println("methods1[0].signature = \"(IIIIIII)L"+lookupName+"$Wrapper;\";");
    d_writer.println("methods1[0].fnPtr     = (void *)"+generalSidlName+"__get;");
    d_writer.println("methods1[1].name      = \"_set\";");
    d_writer.println("methods1[1].signature = \"(IIIIIIIL"+lookupName+";)V\";");
    d_writer.println("methods1[1].fnPtr     = (void *)"+generalSidlName+"__set;");
    d_writer.println("methods1[2].name      = \"_reallocate\";");
    d_writer.println("methods1[2].signature = \"(I[I[IZ)V\";");
    d_writer.println("methods1[2].fnPtr     = (void *)"+generalSidlName+"__reallocate;");
    d_writer.println("methods1[3].name      = \"_copy\";");
    d_writer.println("methods1[3].signature = \"(L"+lookupName+"$Array;)V\";");
    d_writer.println("methods1[3].fnPtr     = (void *)"+generalSidlName+"__copy;  ");
    d_writer.println("methods1[4].name      = \"_slice\";");
    d_writer.println("methods1[4].signature = \"(I[I[I[I[I)L"+lookupName+"$Array;\";");
    d_writer.println("methods1[4].fnPtr     = (void *)"+generalSidlName+"__slice;    ");
    d_writer.println("methods1[5].name      = \"_fromArray\";");
    d_writer.println("methods1[5].signature = \"([Ljava/lang/Object;I[IZ)V\";");
    d_writer.println("methods1[5].fnPtr     = (void *)"+generalSidlName+"__fromArray;");
    d_writer.println("");
	


    d_writer.println("");
    d_writer.println("cls1 = (*env)->FindClass(env, \""+lookupName+"$Array\");");
    d_writer.println("if (cls1) {");
    d_writer.tab();
    d_writer.println("(*env)->RegisterNatives(env, cls1, methods1, 6);");
    d_writer.println("(*env)->DeleteLocalRef(env, cls1);");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("}");
	
  }

  private void generateCheckRuntimeException(int rtype) {
    
    d_writer.println("if(_ex) {");
    d_writer.tab();
    d_writer.println("sidl_Java_CheckException(");
    d_writer.tab();
    d_writer.println("env,");
    d_writer.println("_ex,");
    
    SymbolID exid = IOR.getRuntimeException(d_context);
    d_writer.println("\"" + exid.getFullName() + "\",");
    
    d_writer.println("NULL);");
    d_writer.backTab();
    if (rtype != Type.VOID) {
      d_writer.println();
      d_writer.println("return _res;");
    } else {
      d_writer.println("return;");
    }
    d_writer.backTab();
    d_writer.println("}");
    
  }
}
