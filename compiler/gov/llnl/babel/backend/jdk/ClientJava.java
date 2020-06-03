//
// File:        ClientJava.java
// Package:     gov.llnl.babel.backend.jdk
// Revision:    @(#) $Id: ClientJava.java 7188 2011-09-27 18:38:42Z adrian $
// Description: write client (stub) code that supports Java clients
//
// Copyright (c) 2000-2003, Lawrence Livermore National Security, LLC
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
import gov.llnl.babel.backend.jdk.Java;
import gov.llnl.babel.backend.writers.LanguageWriterForJava;
import gov.llnl.babel.symbols.Argument;
import gov.llnl.babel.symbols.Class;
import gov.llnl.babel.symbols.Struct;
import gov.llnl.babel.symbols.Comment;
import gov.llnl.babel.symbols.Enumeration;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Interface;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.SymbolUtilities;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Class <code>ClientJava</code> writes the Java native code descriptions that
 * will point to the JNI C code written by <code>ClientJNI</code>.  The class
 * constructor takes a language writer and method <code>generateCode</code>
 * writes the Java client code for the specified symbol to the output stream.
 * The language writer output stream is not closed by this object.
 */
public class ClientJava {
  private final static int INTERFACE = 0;
  private final static int WRAPPER   = 1;
  private final static int CLASS     = 2;
  private final static int MAX_DIM = 7;  

  private LanguageWriterForJava d_writer;
  private Context d_context;

  /**
   * This is a convenience utility function that writes the Java client
   * information into the provided language writer output stream.  The
   * output stream is not closed on exit.  A code generation exception
   * is thrown if an error is detected, such as I/O trouble or a violation
   * of data type invariants.
   */
  public static void generateCode(Symbol symbol, 
                                  LanguageWriterForJava writer,
                                  Context context)
    throws CodeGenerationException {
    ClientJava source = new ClientJava(writer, context);
    source.generateCode(symbol);
  }

  /**
   * Create a <code>ClientJava</code> object that will write symbol
   * information to the provided output language writer stream.
   */
  public ClientJava(LanguageWriterForJava writer,
                    Context context) {
    d_writer = writer;
    d_context = context;
  }

  /**
   * Write Java client information for the provided symbol to the language
   * writer output stream provided in the class constructor.  This method
   * does not close the writer output stream.  Code is currently generated
   * only for sidl enumerations, interfaces, and classes, since packages do
   * not require JNI support.
   */
  public void generateCode(Symbol symbol) throws CodeGenerationException {
    if (symbol == null) {
      throw new CodeGenerationException("Unexpected null symbol object");
    }
    switch (symbol.getSymbolType()) {
    case Symbol.CLASS:
      generateExtendable((Extendable) symbol);
      break;
    case Symbol.ENUM:
      generateEnumeration((Enumeration) symbol);
      break;
    case Symbol.INTERFACE:
      generateExtendable((Extendable) symbol);
      break;
    case Symbol.PACKAGE:
      // do nothing for a package
      break;
    case Symbol.STRUCT:
      generateStruct((Struct)symbol);
      break;
    default:
      throw new CodeGenerationException("Unsupported symbol type");
    }
  }

  /**
   * Generate the Java representation for a sidl enumerated type.  This
   * method simply outputs a Java interface with a number of final constants.
   */
  private void generateEnumeration(Enumeration enm) {
    SymbolID id = enm.getSymbolID();
    String file = Java.getClientJavaFile(id);

    /*
     * Output the banner and package statement.
     */
    d_writer.writeBanner(enm, file, CodeConstants.C_IS_NOT_IMPL,
                         CodeConstants.C_DESC_STUB_PREFIX + id.getFullName());
    writeJavaPackage(id);

    /*
     * Output the enumerators as integer types within a public interface.
     */
    d_writer.writeComment(enm, true);
    d_writer.println("public interface " + Java.getJavaSymbolName(id) + " {");
    d_writer.tab();
    int maxlength = Utilities.getWidth(enm.getEnumerators());
    for (Iterator e = enm.getIterator(); e.hasNext(); ) {
      String name = (String) e.next();
      Comment cmt = enm.getEnumeratorComment(name);
      d_writer.writeComment(cmt, true);
      d_writer.print("public final static long ");
      d_writer.printAligned(name, maxlength);
      d_writer.print(" = ");
      d_writer.print(String.valueOf(enm.getEnumeratorValue(name)));
      d_writer.println(";");
      if (cmt != null) {
        d_writer.println();
      }
    }
    d_writer.backTab();
    d_writer.println("}");
  }

  /**
   * Generate the Java client source for a sidl class or interface type.
   * For the most part, the Java source defines the interfaces and classes
   * and the native methods.  All of the real work is done by the JNI code.
   */
  private void generateExtendable(Extendable ext)
    throws CodeGenerationException {
    SymbolID id = ext.getSymbolID();
    String file = Java.getClientJavaFile(id);

    /*
     * Output the banner, package statement, and comment.
     */
    d_writer.writeBanner(ext, file, CodeConstants.C_IS_NOT_IMPL,
                         CodeConstants.C_DESC_STUB_PREFIX + id.getFullName());
    writeJavaPackage(id);
    d_writer.writeComment(ext, true);

    /*
     * If this is an interface, then output all the methods in the interface
     * and generate the interface wrapper inner class that defines the native
     * methods that call the IOR.
     */
    if (ext.isInterface()) {
      /*
       * First output the interface definition with all parent interfaces.
       * All interface inherit from the Java base interface as well as all
       * other sidl interfaces.
       */
      d_writer.println("public interface "
                       + Java.getJavaSymbolName(id)
                       + " extends");
      d_writer.tab();
      
      d_writer.print(Java.getJavaBaseInterface());
      List parents = Utilities.sort(ext.getParentInterfaces(false));
      if ((parents == null) || parents.isEmpty()) {
        d_writer.println();
      } else {
        d_writer.println(",");
        for (Iterator p = parents.iterator(); p.hasNext(); ) {
          Interface i = (Interface) p.next();
          d_writer.print(Java.getFullJavaSymbolName(i.getSymbolID()));
          if (p.hasNext()) {
            d_writer.print(",");
          }
          d_writer.println();
        }
      }
      
      d_writer.backTab();
      d_writer.println("{");
      d_writer.tab();
      

      writeConnect(ext);
      /*
       * Output the interface methods in sorted order.  All methods are
       * abstract.
       */
      List local_methods = (List) ext.getMethods(false);
      for (Iterator m = local_methods.iterator(); m.hasNext(); ) {
        Method method = (Method) m.next();
        expandMethod(method, INTERFACE, false);
        d_writer.println();
      }
      
      if (!d_context.getConfig().getSkipRMI()) {
        //generate _exec method
        expandMethod(IOR.getBuiltinMethod(IOR.EXEC,id,d_context,false), 
                     INTERFACE, false);
        d_writer.println();
      }
      
      //generate _set_hooks method
      expandMethod(IOR.getBuiltinMethod(IOR.HOOKS, id,d_context,false),
                   INTERFACE, false);
      d_writer.println();
      if(ext.hasStaticMethod(true)) {
        expandMethod(IOR.getBuiltinMethod(IOR.HOOKS,  id,d_context,true),
                     INTERFACE, false);
        d_writer.println();

      }

      //writeCastMethod(ext);
      /*
       * Now output the wrapper class that implements the methods via JNI.
       * Begin with a brief documentation blurb.  Make sure to extend the
       * sidl Java base class and implmenet the outer interface.  
       * This is also outputted for abstract classes.
       */
      
      writeWrapperClass(ext);
      
      /*
       * Output the holder class and finlish the interface declaration.
       */
      writeHolderClass(id, false);
      writeObjectArray(ext);
      d_writer.backTab();
      d_writer.println("}");
      
      /*
       * If this is a class, then output the methods in the class as native
       * implementations that call IOR routines.
       */
    } else {
      Class cls = (Class) ext;
      
      /*
       * Output the class documentation and the inheritance information.
       * Note that we must declare the class as abstract if any of the methods
       * are abstract.  If the class does not have a parent, then it must
       * inherit from the sidl Java base class.
       */
      d_writer.print("public ");
      if (ext.isAbstract()) {
        //d_writer.print("abstract ");
      }
      d_writer.println("class " + Java.getJavaSymbolName(id) + " extends");
      d_writer.tab();
      
      Class c = cls.getParentClass();
      if (c == null) {
        d_writer.println(Java.getJavaBaseClass() + " implements");
      } else {
        d_writer.println(Java.getFullJavaSymbolName(c.getSymbolID())
                         + " implements");
      }
      
      List parents = Utilities.sort(ext.getParentInterfaces(true));
      for (Iterator p = parents.iterator(); p.hasNext(); ) {
        Interface i = (Interface) p.next();
        d_writer.print(Java.getFullJavaSymbolName(i.getSymbolID()));
        if (p.hasNext()) {
          d_writer.print(",");
        }
        d_writer.println();
      }
      
      d_writer.backTab();
      d_writer.println("{");
      d_writer.tab();
      
      writeRegisterFunction(id);
      writeConnect(ext);
      /*
       * If not abstract, then output a private native method that returns
       * an IOR reference and also a zero-argument default constructor.
       * Produce a private (inaccessable) constructor if the class is abstract
       */

      if(!cls.isAbstract()) {
        d_writer.writeComment("Default constructor for the class.", true);
        d_writer.print("public ");
        d_writer.println(Java.getJavaSymbolName(id) + "() {");
        d_writer.tab();
        d_writer.println("super(_create_ior());");
        d_writer.backTab();
        d_writer.println("}");
        d_writer.println();
        
        d_writer.writeComment("Private method to create IOR reference.", true);
        d_writer.println("private static native long _create_ior();");
        d_writer.println();
        
        d_writer.writeComment("Private method to create IOR reference.", true);
        d_writer.println("private static native long _create_remote_ior(java.lang.String url);");
        d_writer.println();


        d_writer.writeComment("Protected method to create and IOR, and set the ddata.", true);
        d_writer.println("protected static native long _wrap("+
                         Java.getJavaSymbolName(id)+ " ddata);");
        d_writer.println();


        d_writer.writeComment("Remote constructor for the class.", true);
        d_writer.println("public " + Java.getJavaSymbolName(id) + "(java.lang.String url) {");
        d_writer.tab();
        d_writer.println("super(_create_remote_ior(url));");
        d_writer.backTab();
        d_writer.println("}");
        d_writer.println();


        d_writer.writeComment("Class connector.", true);
        d_writer.println("public static gov.llnl.sidl.BaseClass"
                         //+ Java.getJavaSymbolName(id)
                         + " _connect(java.lang.String url) {");
        d_writer.tab();
        d_writer.println("return (gov.llnl.sidl.BaseClass) new "+Java.getJavaSymbolName(id)+"(_connect_remote_ior(url));");
        d_writer.backTab();
        d_writer.println("}");
        d_writer.println();
      }
      if (!ext.isInterface()) {
        //This method can only exist in classes and wrappers, not interfaces!
        d_writer.writeComment("Private method to connect IOR reference.", true);
        d_writer.println("private static native long _connect_remote_ior(java.lang.String url);");
        d_writer.println();
      }

      /*
       * Output the class constructor that initializes the base object.
       */
      d_writer.writeComment("Public constructor for an existing IOR.", true);
      d_writer.println("public "
                       + Java.getJavaSymbolName(id)
                       + "(long ior) {");
      d_writer.tab();
      d_writer.println("super(ior);");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();

      /*
       * Output all of the methods in CLASS mode.
       */
      List local_methods = null; 

      if(ext.isAbstract())
        local_methods= (List) ext.getAbstractAndLocalMethods(); 
      else
        local_methods= (List) ext.getMethods(false); 

      for (Iterator m = local_methods.iterator(); m.hasNext(); ) {
        Method method = (Method) m.next();
        expandMethod(method, CLASS, false);
        d_writer.println();
      }
      if (!d_context.getConfig().getSkipRMI()) {
        //generate _exec method
        expandMethod(IOR.getBuiltinMethod(IOR.EXEC, id,d_context,false), 
                     CLASS, false);
        d_writer.println();
      }

      //generate _set_hooks method
      expandMethod(IOR.getBuiltinMethod(IOR.HOOKS,id,d_context,false),
                   CLASS, false);
      d_writer.println();
      if(ext.hasStaticMethod(true)) {
        expandMethod(IOR.getBuiltinMethod(IOR.HOOKS,id,d_context,true), 
                     CLASS, false);
        d_writer.println();

      }

      //Generate and super methods.
      Collection overwritten = cls.getOverwrittenClassMethods();
      for(Iterator m = overwritten.iterator(); m.hasNext(); ) {
        Method method = (Method) m.next();
        generateMethod(method, CLASS, true);
        d_writer.println();
      }

      
      writeCastMethod(ext);

      // Output the wrapper class if this class is abstract
      if (ext.isAbstract()) {
        writeWrapperClass(ext);
      }
	    
      /*
       * Write a holder class for inout and out arguments and close the class
       * scope.
       */
      writeHolderClass(id, false);
      writeObjectArray(ext);
      d_writer.backTab();
      d_writer.println("}");
    }
  }

  /**
   * Generate the Java client source for a sidl struct declaration.
   */
  private void generateStruct(Struct strct) throws CodeGenerationException {
    JavaStructSource src = new JavaStructSource(strct);
    src.generateProxyClass(d_context, d_writer);
  }
    
  /**
   * Write the java package information followed by an additional newline.
   */
  private void writeJavaPackage(SymbolID id) {
    String pkg = SymbolUtilities.getParentPackage(id.getFullName());
    d_writer.println("package " + pkg + ";");
    d_writer.println();
  }

  /**
   * Output the static block that will dynamically load the JNI implementation.
   * This block calls the <code>_registerNatives</code> function in the sidl
   * run-time Java base class.
   */
  private void writeRegisterFunction(SymbolID id) {
    d_writer.writeComment("Register the JNI native routines.", false);
    d_writer.println("static {");
    d_writer.tab();
    d_writer.println(Java.getJavaBaseClass()
                     + "._registerNatives(\""
                     + id.getFullName()
                     + "\");");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  /** This generates a static method in every class that casts to the current 
   *  type.
   */
  private void writeCastMethod(Extendable ext) {
    SymbolID id = ext.getSymbolID();
    String name = Java.getFullJavaSymbolName(id);

    d_writer.beginBlockComment(true);
    d_writer.println("");
    d_writer.println("Cast this object to the specified sidl name.  If the cast is invalid,");
    d_writer.println("then return null.  If the cast is successful, then the returned object");
    d_writer.println("can be cast to the proper Java type using a standard Java cast.");
    d_writer.println("");
    d_writer.endBlockComment(true);
    d_writer.println("public static "+BabelConfiguration.getBaseInterface()+
                     " _cast("+Java.getJavaBaseClass()/*BabelConfiguration.getBaseClass()*/+" obj) {");
    d_writer.tab();
    d_writer.println(BabelConfiguration.getBaseInterface()+" cast = null;");
    d_writer.beginBlockComment(false);
    d_writer.println("");
    d_writer.println("Cast this object to the specified type.  If the cast is valid, then");
    d_writer.println("search for the matching Java type.");
    d_writer.println("");
    d_writer.endBlockComment(false);
    d_writer.println("long ior = obj._cast_ior(\""+name+"\");");
    d_writer.println("if (ior != 0) {");
    d_writer.tab();
    d_writer.println("Class java_class = null;");
    if(ext.isInterface()) {
	
      d_writer.println("try {");
      d_writer.tab();
      d_writer.println("java_class = Class.forName(\""+name+"$Wrapper\");");
      d_writer.backTab();
      d_writer.println("} catch (Exception ex) {");
      d_writer.tab();
      d_writer.println("ex.printStackTrace(System.err);     ");
      d_writer.backTab();
      d_writer.println("}");
    } else {
      d_writer.println("try {");
      d_writer.tab();
      d_writer.println("java_class = Class.forName(\""+name+"\");");
      d_writer.backTab();
      d_writer.println("} catch (Exception ex) {");
      d_writer.tab();
      d_writer.println("ex.printStackTrace(System.err);     ");
      d_writer.backTab();
      d_writer.println("}");

    }
    d_writer.println("");
    d_writer.beginBlockComment(false);
    d_writer.println("If we found the class, then create a new instance using the sidl IOR.");
    d_writer.endBlockComment(false);
    d_writer.println("if (java_class != null) {");
    d_writer.tab();
    d_writer.println("Class[]  sigs = new Class[]  { java.lang.Long.TYPE     };");
    d_writer.println("Object[] args = new Object[] { new java.lang.Long(ior) };");
    d_writer.println("try {");
    d_writer.tab();
    d_writer.println("java.lang.reflect.Constructor ctor = java_class.getConstructor(sigs);");
    d_writer.println("cast = ("+BabelConfiguration.getBaseInterface()+") ctor.newInstance(args);");
    d_writer.backTab();
    d_writer.println("} catch (Exception ex) {");
    d_writer.tab();
    d_writer.println("ex.printStackTrace(System.err);");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println("return ("+BabelConfiguration.getBaseInterface()+") cast;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
    d_writer.println();
  }

  private void writeConnect(Extendable ext) {
    //TODO: FIX CONNECT
    /*
      d_writer.writeComment("Private method to create IOR reference.", true);
      d_writer.println("private static native long _connect_ior(String url);");
      d_writer.println();
      
      d_writer.writeComment("Remote connector for this object.", true);
      d_writer.println("public static " + Java.getJavaSymbolName(ext.getSymbolID())+ 
      " _connect(String url) {");
      d_writer.tab();
      d_writer.println("return "+ Java.getJavaSymbolName(ext.getSymbolID())+"(_create_ior(url, true));");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();
    */
  }
    
  /**
   * Write an inner holder class used for out and inout arguments.  The holder
   * class has a single argument - the Java object - which may be get or set
   * by accessor methods.
   */
  private void writeHolderClass(SymbolID id, boolean hasDestroy) {
    String holder = Java.getHolderName();
    String holdee = Java.getFullJavaSymbolName(id).replace('$', '.');;

    d_writer.writeComment("Holder class for inout and out arguments.", true);
    d_writer.println("public static class " + holder + " {");
    d_writer.tab();
    d_writer.println("private " + holdee + " d_obj;");
    d_writer.println();

    d_writer.writeComment("Create a holder with a null holdee object.", true);
    d_writer.println("public " + holder + "() {");
    d_writer.tab();
    d_writer.println("d_obj = null;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();

    d_writer.writeComment("Create a holder with the specified object.", true);
    d_writer.println("public " + holder + "(" + holdee + " obj) {");
    d_writer.tab();
    d_writer.println("d_obj = obj;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();

    d_writer.writeComment("Set the value of the holdee object.", true);
    d_writer.println("public void set(" + holdee + " obj) {");
    d_writer.tab();
    d_writer.println("d_obj = obj;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();

    d_writer.writeComment("Get the value of the holdee object.", true);
    d_writer.println("public " + holdee + " get() {");
    d_writer.tab();
    d_writer.println("return d_obj;");
    d_writer.backTab();
    d_writer.println("}");

    if (hasDestroy) {
      d_writer.println();
      d_writer.writeComment("Method to destroy array.", true);
      d_writer.println("public void destroy() {");
      d_writer.tab();
      d_writer.println(" if (d_obj != null) { d_obj.destroy(); d_obj = null; }");
      d_writer.backTab();
      d_writer.println("}");
    }

    d_writer.backTab();
    d_writer.println("}");
  }

  /** Write inner wrapper class for interfaces and abstract classes
   */
	 
  void writeWrapperClass(Extendable ext) throws CodeGenerationException {
    SymbolID id = ext.getSymbolID();
    d_writer.writeComment("Inner wrapper class that implements interface and abstract class methods.", true);
    if (ext.isInterface()) {
      d_writer.println("public static class " + Java.getInterfaceWrapper());
      d_writer.tab();
      d_writer.println("extends " + Java.getJavaBaseClass());
      d_writer.println("implements " + Java.getFullJavaSymbolName(id));
    } else if(ext.isAbstract())  {
      d_writer.println("public static class " + Java.getInterfaceWrapper());
      d_writer.tab();
      d_writer.println("extends " + Java.getFullJavaSymbolName(id));
    }  else {
      throw new CodeGenerationException("Class is not interface or abstract, should not generate wrapper class!");
    }
    d_writer.backTab();
    d_writer.println("{");
    d_writer.tab();

    /*
     * Output the class constructor that initializes the base objectb .
     */
    writeRegisterFunction(id);

    if (ext.isInterface()) {
      //This method can only exist in classes and wrappers, not interfaces!
      d_writer.writeComment("Private method to connect IOR reference.", true);
      d_writer.println("private static native long _connect_remote_ior(java.lang.String url);");
      d_writer.println();
    }

    d_writer.writeComment("Class constructor for the wrapper class.", true);
    d_writer.println("public "
                     + Java.getInterfaceWrapper()
                     + "(long ior) {");
    d_writer.tab();
    d_writer.println("super(ior);");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();

    d_writer.writeComment("Class connector for the wrapper class.", true);
    d_writer.println("public static gov.llnl.sidl.BaseClass"
                     //+ Java.getInterfaceWrapper()
                     + " _connect(java.lang.String url) {");
    d_writer.tab();
    d_writer.println("return (gov.llnl.sidl.BaseClass) new "+Java.getInterfaceWrapper()+"(_connect_remote_ior(url));");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();

    /*
     * Output the methods in WRAPPER mode.  We need to iterate over all the
     * methods in the interface since we will implement all of them.
     */
    List all_methods = (List) ext.getMethods(true);  
    for (Iterator m = all_methods.iterator(); m.hasNext(); ) {
      Method method = (Method) m.next();
      //For abstract classes we only want abstract methods in the wrapper
      if((ext.isInterface()) && (method.isAbstract())) {  //Used to be isAbstract
        expandMethod(method, WRAPPER, false);
        if (m.hasNext()) {
          d_writer.println();
        }
      } 
    }

    if (ext.isInterface()) {
      if (!d_context.getConfig().getSkipRMI()) {
        //generate _exec method
        expandMethod(IOR.getBuiltinMethod(IOR.EXEC,id,d_context,false),
                     WRAPPER, false);
        d_writer.println();
      }

      //generate _set_hooks method
      expandMethod(IOR.getBuiltinMethod(IOR.HOOKS,id,d_context,false),
                   WRAPPER, false);
      d_writer.println();
      if(ext.hasStaticMethod(true)) {
        expandMethod(IOR.getBuiltinMethod(IOR.HOOKS,id,d_context,true), 
                     WRAPPER, false);
        d_writer.println();
        
      }
    }

    writeCastMethod(ext);
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  /** 
   * A simple helper method that expands also generates send and receive
   * methods for nonblocking methods
   */
  private void expandMethod(Method method, int mode, boolean isSuper) throws CodeGenerationException{
    generateMethod(method,mode,isSuper);
    if ( (method.getCommunicationModifier() == Method.NONBLOCKING) &&
         !d_context.getConfig().getSkipRMI()) { 
      Method send = method.spawnNonblockingSend();
      generateMethod(send,mode,isSuper);
      Method recv = method.spawnNonblockingRecv();
      generateMethod(recv,mode,isSuper);
    }
  }

  /**
   * Generate the method descriptions for a sidl method.  The mode argument
   * determines how the method is to be created.  If INTERFACE, then the method
   * is from an interface description and the method is abstract.  If WRAPPER,
   * then the method is from an interface wrapper and all the methods are
   * implemented as native methods using JNI.  If CLASS, then the method is
   * from a class and non-abstract methods are implemented using native calls.
   */
  private void generateMethod(Method method, int mode, boolean isSuper) {
    d_writer.writeComment(method, true);
	
    /*
     * Output the method visibility, modifier (abstract, final, or static), and
     * whether it is a native method.
     */
    if(isSuper) {
      d_writer.print("protected native ");
    } else {
      d_writer.print("public ");
      if (mode == INTERFACE) {
        d_writer.print("abstract ");
      } else if (method.isAbstract()) {
        d_writer.print("native ");
      } else if (method.isFinal()) {
        d_writer.print("final ");
      } else if (method.isStatic()) {
        d_writer.print("static ");
      }
      if (!method.isAbstract() && mode != INTERFACE) {
        d_writer.print("native ");
      }
    }

    /*
     * Output the return type and method name and begin the argument list.
     */
    if(isSuper) {
    d_writer.print(Java.getJavaReturnType(method.getReturnType())
                   + " super_"
                   + method.getCorrectMethodName()
                   + "(");
    } else {
      d_writer.print(Java.getJavaReturnType(method.getReturnType())
                   + " "
                   + method.getCorrectMethodName()
                   + "(");
    }
    /*
     * Output the argument list and a throws clause.  The logic is a little
     * tricky to make the output look pretty.
     */
    List args = method.getArgumentList();
    Set thrws = method.getExplicitThrows();

    if (args.isEmpty() && thrws.isEmpty()) {
      d_writer.println(");");
    } else {
      if (!args.isEmpty()) {
        d_writer.println();
        d_writer.tab();
        for (Iterator a = args.iterator(); a.hasNext(); ) {
          Argument arg = (Argument) a.next();
          d_writer.print(Java.getJavaFormalArgument(arg));
          if (a.hasNext()) {
            d_writer.println(",");
          }
        }
      }
      if (thrws.isEmpty()) {
        d_writer.println(");");
      } else {
        d_writer.println(") throws");
        if (args.isEmpty()) {
          d_writer.tab();
        }
        for (Iterator t = thrws.iterator(); t.hasNext(); ) {
          SymbolID tid = (SymbolID) t.next();
          if(!method.isImplicitException(tid)) {
            d_writer.print(Java.getFullJavaSymbolName(tid));
            if ( (!SymbolUtilities.isBaseException(tid)) 
                 && (d_context.getSymbolTable().lookupSymbol(tid).isInterface()) ) {
              d_writer.print(".Wrapper");
            }
            d_writer.println(t.hasNext() ? "," : ";");
          }
        }
      }
      d_writer.backTab();
    }
  }

 
  /**
   *  Each sidl object was have an array implementation so that an
   *  array of that type of object may be used and passed around.
   *
   *  Arrays are implemented as public static nested classes of their Objects.
   *  (Since they basically contain sets of those objects)
   *
   *  The Inheritance hierarchy (for java) looks like this:
   *                   sidl.BaseArray
   *                       |
   *              sidl.BaseInterface.Array (nested class of BaseInterface)
   *                       |        \
   *                       |       sidl.BaseInterface.Array1...Array7
   *                       |
   *                 MyObject.Array        (nested class of MyObject)
   *             /   /  /  |  \  \  \       
   *   MyObject.Array1 .......... MyObject.Array7 (nested classes of MyObject, 1)
   *                                              (for each dimension possiable ) 
   *
   *   But since BaseInterface is written with the same functions as all other
   *   interfaces (and classes) we must differentiate this superclass from it's
   *   subclasses.  This function does that, and calls the correct generators.
   *   BaseInterface.Array is also the only object array with a JNI interface
   *   to the Impl side of things, so all calls are made though it.
   */


  private void writeObjectArray(Extendable ext) {
    SymbolID id = ext.getSymbolID();
    String elemType = Java.getFullJavaSymbolName(id);
	
    d_writer.writeComment("Array Class for implementation of Object arrays", true);
    if ((elemType.compareTo(BabelConfiguration.getBaseInterface()) == 0)) {
      //If the class we are generating is the sidl.BaseInterface class, we should
      //call this function, otherwise, the otherone.
      writeBaseInterfaceArray(ext);
    } else {
      writeOtherClassArray(ext);
    }
	
  }

  private void writeDcastCase(int num)
  {
    final String numStr = Integer.toString(num);
    d_writer.println("case " + numStr + ":");
    d_writer.tab();
    d_writer.println("ret = (Array) new sidl.BaseInterface.Array" + numStr +
                     "(get_ior_pointer(), true);");
    d_writer.println("_addRef();");
    d_writer.println("return ret;");
    d_writer.backTab();
  }

  private void writeDcastCase(int num, String elemType)
  {
    final String numStr = Integer.toString(num);
    d_writer.println("case " + numStr + ":");
    d_writer.tab();
    d_writer.println("ret = (Array) new " + elemType + ".Array" + numStr +
                     "(getBaseInterfaceArray());");
    d_writer.println("return ret;");
    d_writer.backTab();
  }

  /**
   *  Here we write out the inital BaseInterface.Array class.  It inherits from sidl.BaseArray
   *  and is the only object array class with a JNI interface.  All other object arrays
   *  inherit from this class, and use it's JNI interface to access their objects.
   *  
   *  At the end of this function, a function that generates classes BaseInterface.Array1..7
   *  is run.  These also inherit from BaseInterface.Array.  (Same function that generates
   *  them for regular object actually.)
   */
    
  private void writeBaseInterfaceArray(Extendable ext) {
    SymbolID id = ext.getSymbolID();
    String elemType = Java.getFullJavaSymbolName(id);

    d_writer.println("public static class Array extends "+Java.getJavaBaseArray()+" {");

    d_writer.tab();
    d_writer.println();
	
    writeRegisterFunction(id);
	
	
    d_writer.writeComment("Construct an empty basic array object.", true);
    d_writer.println("public Array() {");
    d_writer.tab();
    d_writer.println("super();");	
    d_writer.backTab();
    d_writer.println("}");
	
    d_writer.writeComment("Create an array using an IOR pointer",true);
    d_writer.println("public Array(long array, boolean owner) {");
    d_writer.tab();
    d_writer.println("super(array, owner);");	
    d_writer.println("}");
    d_writer.backTab();
	
    d_writer.writeComment("Create an array with the specified dimension, upper and lower bounds"
                          ,true);
    d_writer.println("public Array(int dim, int[] lower, int[] upper, boolean isRow) {");
    d_writer.tab();
    d_writer.println("super();");
    d_writer.println("reallocate(dim, lower, upper, isRow);");	
    d_writer.println("}");
    d_writer.backTab();
	
    /*    d_writer.writeComment("Native routine to get the dimension of this array",true);
    d_writer.println("public native int _dim();");
	
    d_writer.writeComment("Native routine to get lower bound at the specified dimension",true);
    d_writer.println("public native int _lower(int dim);");
	
    d_writer.writeComment("Native routine to get upper bound at the specified dimension",true);
    d_writer.println("public native int _upper(int dim);");
	
    d_writer.writeComment("Native routine to get stride at the specified dimension",true);
    d_writer.println("public native int _stride(int dim);");

    d_writer.writeComment("Native routine returns true is array is columnorder",true);
    d_writer.println("public native boolean _isColumnOrder();");
	
    d_writer.writeComment("Native routine returns true is array is roworder",true);
    d_writer.println("public native boolean _isRowOrder();");
	
    */
    d_writer.writeComment("Native routine to get the value at the specified indices",true);

    d_writer.println("public native " + elemType + 
                     ".Wrapper _get(int i, int j, int k, int l, int m, int n, int o);");
       
    d_writer.writeComment("Native routine to set the value at the specified indices",true);
    d_writer.println("public native void" + 
                     " _set(int i, int j, int k, int l, int m, int n, int o, " +
                     elemType + " value);");
    /******************************
    
    d_writer.beginBlockComment(true);
    d_writer.println("addRef adds a reference to the IOR representation of the");
    d_writer.println("array, it does nothing for the Java object.");
    d_writer.endBlockComment(true);
    d_writer.println("public native void _addRef();");
    d_writer.println("");
    ******************************/

    /*    d_writer.beginBlockComment(true);
    d_writer.println("Deallocate deletes java\'s reference to the array (calls deleteRef)");
    d_writer.println("But does not (nessecarily) case the array to be GCed.");
    d_writer.endBlockComment(true);
    d_writer.println("public native void _deallocate();");
    */
    d_writer.println("");
    d_writer.beginBlockComment(true);
    d_writer.println("Slice returns an array that is <= the orignial array.  It shares");
    d_writer.println("data with the orginal array.  ");
    d_writer.println("dimen gives the number of dimensions in the result array");
    d_writer.println("numElem array gives the number of elements in each dimension");
    d_writer.println("srcStart gives the array index to start the result array at");
    d_writer.println("srcStride gives the stride of the result array\'s elements over");
    d_writer.println("the original array\'s elements.");
    d_writer.println("See the Babel user\'s manual for more information.");
    d_writer.endBlockComment(true);
    d_writer.println("public native Array _slice(int dimen, int[] numElem, int[] srcStart,					int[] srcStride, int[] newStart);  	");
    d_writer.println("");
  
    d_writer.beginBlockComment(true);
    d_writer.println("Method Copy copies the elements of \'this\' to an already existing ");
    d_writer.println("array of the same size.  NOT LIKE clone()!!");
    d_writer.endBlockComment(true);
    d_writer.println("public native void _copy(Array dest);");
    /*
    d_writer.writeComment("Native routine to deallocate the array.",true);
    d_writer.println("public native void _destroy();");
    */
    d_writer.writeComment("Native routine to reallocate the array data. Bad things happen if bad bounds are passed",true);
    d_writer.println("public native void _reallocate(int dim, int[] lower, int[] upper, boolean isRow);");    
    d_writer.println();
    d_writer.beginBlockComment(true);
    d_writer.println("");
    d_writer.println("Automatically casts this array to an array of the correct dimension. ");
    d_writer.println("(You might want to deallocate the original array.  ");
    d_writer.println("Argument dimen determines what dimension array to cast this");
    d_writer.println("array to.  ");
    d_writer.println();
    d_writer.endBlockComment(true);
    d_writer.println("public Array _dcast() {");
    d_writer.tab();
    d_writer.println("try{ ");
    d_writer.tab();
    d_writer.println("int dimen = _dim();");
    d_writer.println("sidl.BaseInterface.Array ret = null;"); 
    d_writer.println("switch (dimen) {");
    for(int j = 1; j <= 7; ++j) {
      writeDcastCase(j);
    }
    d_writer.println("default:");
    d_writer.tab();
    d_writer.println("return null;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("} catch (Exception ex) {");
    d_writer.tab();
    d_writer.println("return null;	");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println();
    d_writer.println("}");
    d_writer.println();

    /***************************/
    //fromArray function
    d_writer.writeComment("Native routine to reallocate the array, and copy a java array of objects into it.",true);
    d_writer.println("public native void _fromArray(Object[] orray, int dim, int[] lower, boolean isRow);");

    //Generate Holder class
    SymbolID aSymbol = new SymbolID(elemType + "$Array", 
                                    id.getVersion());
    writeHolderClass(aSymbol, true);
    
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
        
    //Arrays need to have code up to MAX_DIM dimensions.  This 
    //function generates the code for 1-MAX_DIM dimension of the array.
    writeBaseNumberedObjectArrays(ext);
	
    
  } 

  /**
   *  This function generates the Array nested class for all sidl classes and interfaces aside
   *  from BaseInterface.  This Array class inherits from sidl.BaseInterface.Array. 
   *
   *  It IS exported to the user as a general way to represent arrays of any dimension.
   */

  private void writeOtherClassArray(Extendable ext) {
    SymbolID id = ext.getSymbolID();
    String elemType = Java.getFullJavaSymbolName(id);

    d_writer.println("public static class Array extends "+Java.getJavaBaseArray()+" {");
    d_writer.println("private " +BabelConfiguration.getBaseInterface()+ ".Array a;");
    d_writer.tab();
    d_writer.println();
	
    d_writer.writeComment("Construct an empty basic array object.", true);
    d_writer.println("public Array() {");
    d_writer.tab();
    d_writer.println("super();");
    d_writer.println("a = new "+BabelConfiguration.getBaseInterface()+".Array();");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
    d_writer.println();
    
    d_writer.writeComment("Create an array using an IOR pointer",true);
    d_writer.println("protected Array(long array, boolean owner) {");
    d_writer.tab();
    d_writer.println("super(array, owner);");	
    d_writer.println("a = new "+BabelConfiguration.getBaseInterface()+".Array(array, owner);");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
    d_writer.println();
    
    d_writer.writeComment("Create an array with the specified dimension, upper and lower bounds"
                          ,true);
    d_writer.println("public Array(int dim, int[] lower, int[] upper, boolean isRow) {");
    d_writer.tab();
    d_writer.println("a = new "+BabelConfiguration.getBaseInterface()+".Array(dim,lower,upper, isRow);");
    d_writer.println("d_array = a.get_ior_pointer();"); 
    d_writer.println("d_owner = true;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
    d_writer.println();
    
    d_writer.writeComment("Create an array using an BaseInterface Array",true);
    d_writer.println("protected Array("+BabelConfiguration.getBaseInterface()+".Array core) {");
    d_writer.tab();
    d_writer.println("a = core;");
    d_writer.println("d_array = a.get_ior_pointer();");
    d_writer.println("d_owner = true;");
    d_writer.println("}");
    d_writer.backTab();

    d_writer.writeComment("Return this array's internal BaseInterface Array",true);
    d_writer.println("protected "+BabelConfiguration.getBaseInterface()+".Array "
                     + "getBaseInterfaceArray() {");
    d_writer.tab();
    d_writer.println("return a;");
    d_writer.backTab();
    d_writer.println("}");

    d_writer.println();
    d_writer.println();
    
    d_writer.writeComment("Call to base interface routine to get the dimension of this array",true);
    d_writer.println("public int _dim() {");
    d_writer.tab();
    d_writer.println("return a._dim();");	
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
    d_writer.println();
    
    d_writer.writeComment("Routine to get lower bound at the specified dimension",true);
    d_writer.println("public int _lower(int dim) {");
    d_writer.tab();
    
    d_writer.println("return a._lower(dim);");	
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
    d_writer.println();
    
    d_writer.writeComment("~Native~ routine to get upper bound at the specified dimension",true);
    d_writer.println("public int _upper(int dim) {");
    d_writer.tab();
    
    d_writer.println("return a._upper(dim);");	
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
    d_writer.println();

    d_writer.writeComment("Native routine to get stride at the specified dimension",true);
    d_writer.println("public int _stride(int dim) { ");
    d_writer.tab();    
    d_writer.println("return a._stride(dim);");	
    d_writer.backTab();
    d_writer.println("}");

    d_writer.println();
    d_writer.println();
    d_writer.writeComment("Routine gets the length of the array at the specified dimension",true);
    d_writer.println("public int _length(int dim) {");
    d_writer.tab();
    d_writer.println("return a._length(dim);");
    d_writer.println("}"); 
    d_writer.backTab();

    d_writer.println();
    d_writer.println();

    d_writer.writeComment("Native routine returns true is array is columnorder",true);
    d_writer.println("public boolean _isColumnOrder() {");
    d_writer.tab();    
    d_writer.println("return a._isColumnOrder();");	
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
    d_writer.println();

    d_writer.writeComment("Native routine returns true is array is row order",true);
    d_writer.println("public boolean _isRowOrder() {");
    d_writer.tab();    
    d_writer.println("return a._isRowOrder();");	
    d_writer.backTab();
    d_writer.println("}");
    
    d_writer.println();
    d_writer.println();
    /*****************************************/
    d_writer.writeComment("Routine to get the value at the specified indices",true);
    if(ext.isInterface()) {
      d_writer.println("public "+elemType+".Wrapper _get(int i, int j, int k, int l, int m, int n, int o) {");
      d_writer.tab();
      d_writer.println(elemType + ".Wrapper ret = null;");
      d_writer.println(BabelConfiguration.getBaseInterface() + ".Wrapper preCast = null;");
      d_writer.println("preCast = a._get(i,j,k,l,m,n,o);");
      d_writer.println("if(preCast != null) {");
      d_writer.tab();
      d_writer.println("ret = ("+elemType+".Wrapper) preCast._cast2(\""+elemType+"\");");
      d_writer.backTab();
      d_writer.println("} else {");
      d_writer.tab();
      d_writer.println("return null;");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println("return (" +elemType+ ".Wrapper) ret;");
      d_writer.backTab();
      d_writer.println("}");
    } else {
      d_writer.println("public "+elemType+" _get(int i, int j, int k, int l, int m, int n, int o) {");
      d_writer.tab();
      d_writer.println(elemType + " ret = null;");
      d_writer.println(BabelConfiguration.getBaseInterface() + ".Wrapper preCast = null;");
      d_writer.println("preCast = a._get(i,j,k,l,m,n,o);");
      d_writer.println("if(preCast != null) {");
      d_writer.tab();
      d_writer.println("ret = ("+elemType+") preCast._cast2(\""+elemType+"\");");
      d_writer.backTab();
      d_writer.println("} else {");
      d_writer.tab();
      d_writer.println("return null;");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println("return (" +elemType+ ") ret;");
      d_writer.backTab();
      d_writer.println("}");
    }
    /*********************************/
    d_writer.println();
    d_writer.println();
    
    d_writer.writeComment("Routine to set the value at the specified indices",true);
    d_writer.println("public void" + 
                     " _set(int i, int j, int k, int l, int m, int n, int o, " +
                     elemType + " value) {");
    d_writer.tab();
    
    d_writer.println("a._set(i,j,k,l,m,n,o,value);");	
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
    d_writer.println();
    
    d_writer.writeComment("Routine to deallocate the array.",true);
    d_writer.println("public void _destroy() {");
    d_writer.tab();
    d_writer.println("if(d_owner) {");
    d_writer.tab();
    d_writer.println("d_array = 0;");
    d_writer.backTab();
    d_writer.println("}");    
    d_writer.println("a._destroy();");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
    d_writer.println();
    
    /***************************/
    d_writer.beginBlockComment(true);
    d_writer.println("addRef adds a reference to the IOR representation of the");
    d_writer.println("array, it does nothing for the Java object.");
    d_writer.endBlockComment(true);
    d_writer.println("public void _addRef() {");
    d_writer.tab();
    d_writer.println("a._addRef();");
    d_writer.backTab();
    d_writer.println("}");
    
    /***************************/


    d_writer.println();
    d_writer.println();
    d_writer.writeComment("Routine to call deleteRef on the array.",true);
    d_writer.println("public void _deallocate() {");
    d_writer.tab();
    d_writer.println("a._deallocate();");
    d_writer.backTab();
    d_writer.println("}");
    
    d_writer.println();
    d_writer.println();
    d_writer.writeComment("Routine to return an array based on this one, but slice according to your instructions",true);
    d_writer.println("public "+elemType+".Array _slice(int dimen, int[] numElem, "+ 
                     "int[] srcStart, int[] srcStride, int[] newStart) {");
    d_writer.tab();
    d_writer.println(BabelConfiguration.getBaseInterface()+".Array preCast = null;");
    d_writer.println("preCast = ("+BabelConfiguration.getBaseInterface()+".Array) "+
                     "a._slice(dimen,numElem,srcStart,srcStride,newStart);");
    d_writer.println("return new "+elemType+".Array(preCast);"); 
    d_writer.backTab();
    d_writer.println("}");

    d_writer.println();
    d_writer.println();

    d_writer.writeComment("Copies borrowed arrays, addRefs otherwise.",true);
    d_writer.println("public gov.llnl.sidl.BaseArray _smartCopy() {");
    d_writer.tab();
    d_writer.println(BabelConfiguration.getBaseInterface()+".Array preCast = null;");
    d_writer.println("preCast = ("+BabelConfiguration.getBaseInterface()+".Array) a._smartCopy();");
    d_writer.println("return (gov.llnl.sidl.BaseArray) new "+elemType+".Array(preCast);");
    d_writer.backTab();
    d_writer.println("}");

    d_writer.println();
    d_writer.println();
    d_writer.writeComment("Copies borrowed arrays, addRefs otherwise.",true);
    d_writer.println("public void _copy("+elemType+".Array dest) {");
    d_writer.tab();
    d_writer.println("a._copy(dest.getBaseInterfaceArray());");
    d_writer.backTab();
    d_writer.println("}");

    d_writer.println();
    d_writer.println();

    d_writer.writeComment("Native routine to reallocate the array data. Bad things happen if bad bounds are passed",true);
    d_writer.println("public void _reallocate(int dim, int[] lower, int[] upper, boolean isRow) {");
    d_writer.tab();
    
    d_writer.println("a._reallocate(dim,lower,upper, isRow);");
    d_writer.println("d_array = a.get_ior_pointer();");
    d_writer.println("d_owner = true;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
    d_writer.println();

    /***********************************************/
    d_writer.println("public Array _dcast() {");
    d_writer.tab();
    d_writer.println("try{ ");
    d_writer.tab();
    d_writer.println("int dimen = _dim();");
    d_writer.println(elemType + ".Array ret = null;");
    d_writer.println("switch (dimen) {");
    for(int j = 1 ; j <= 7; ++j) {
      writeDcastCase(j, elemType);
    }
    d_writer.println("default:");
    d_writer.tab();
    d_writer.println("return null;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("} catch (Exception ex) {");
    d_writer.tab();
    d_writer.println("return null;	");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println();
    d_writer.println("}");
    d_writer.println();
    d_writer.println();

    /*******************************************************/
    // fromArray() scary.
    d_writer.writeComment("Native routine to reallocate the array, and copy a java array of objects into it.",true);
    d_writer.println("protected void _fromArray(Object[] orray, int dim, int[] lower, boolean isRow) {");
    d_writer.tab();
    d_writer.println("a._fromArray(orray, dim, lower, isRow);");
    d_writer.println("d_array = a.get_ior_pointer();");
    d_writer.println("d_owner = true;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
    d_writer.println();




    //Generate Holder class
    SymbolID aSymbol = new SymbolID(elemType + "$Array", 
                                    id.getVersion());
    writeHolderClass(aSymbol, true);
    
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
    
    
    //Arrays need to have code up to MAX_DIM dimensions.  This 
    //function generates the code for 1-MAX_DIM dimension of the array.
    writeNormalNumberedObjectArrays(ext);
    
  } 
    

  /**
   *  Each sidl object requires code to handle sidl arrays of that object.  
   *  sidl arrays may (currently) be up to MAX_DIM dimensions.  
   *  This function outputs the code of all 1-MAX_DIM dimensional possibilties
   *
   *  I apologize for how bizarrely complex this is.  I should have divided it 
   *  up with helper functions.  Hopefull I'll have time to fix it later.
   *
   */
  private void writeNormalNumberedObjectArrays(Extendable ext) {
    SymbolID id = ext.getSymbolID(); 

    for(int dim = 1; dim <= MAX_DIM; ++dim) {
      String elemType = Java.getFullJavaSymbolName(id);

      d_writer.writeComment("The implementation of "+elemType+" "+dim+" arrays.",true);
      d_writer.println("public static class Array"+dim+" extends Array {");
      d_writer.tab();
      d_writer.println();
      /***************************************/
      d_writer.writeComment("Construct an empty "+dim+" dimensional array object.",true);
      d_writer.println("public Array"+dim+"() {");
      d_writer.tab();
      d_writer.println("super();");	
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();
      /***************************************/
      d_writer.beginBlockComment(true);
      d_writer.println("");
      d_writer.println("Create a "+dim+" dimensional array using the specified java array.");
      d_writer.println("The lower bounds of the constructed array will start at zero.");
      d_writer.println("An array index out of range exception will be thrown if the ");
      d_writer.println("bounds are invalid.");
      d_writer.endBlockComment(true);
      d_writer.print("public Array"+dim+"("+elemType);
      for(int i = 0; i< dim; ++i) 
        d_writer.print("[]");
      d_writer.println(" array, boolean isRow) {");
      d_writer.tab();

      d_writer.println("super();");
      d_writer.println("fromArray(array, isRow);");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();

      /***************************************/

      d_writer.writeComment("Create an array using an IOR pointer",true);
      d_writer.println("protected Array"+dim+"(long array, boolean owner) {");
      d_writer.tab();
      d_writer.println("super(array, owner);");	
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();
      /****************************************/
      d_writer.writeComment("Create a "+dim+" dimensional array with the specified, upper and lower bounds",true);
      d_writer.print("public Array"+dim+"(");
      printArgs("int", "l", dim);
      d_writer.print(",");
	    
      printArgs("int", "u", dim);
      d_writer.println(", boolean isRow) {");
      d_writer.tab();
	    
      d_writer.print("super("+dim+", new int[] {");
      printArgs("","l",dim);
      d_writer.print("}");
	
      d_writer.print(", new int[] {");
      printArgs("","u",dim);
      d_writer.print("}");
      d_writer.println(", isRow);");
      d_writer.backTab();
      d_writer.println("}");
	    
      
      d_writer.println();
      /*************************************/
      d_writer.writeComment("Create a "+dim+" dimensional array with the specified, upper bounds.  Lower bounds are all 0",true);
      d_writer.print("public Array"+dim+"(");
      printArgs("int","s",dim);
      d_writer.println(", boolean isRow)  {");
      d_writer.tab();
	    
      d_writer.print("super("+dim+", new int[] {");
      //Sorray about this, but this is just like PrintArgs, but
      //0's instead of variables.
      for(int i = 0; i < dim; ++i) {
        d_writer.print(" 0");
        if(i == dim-1) 
          d_writer.print("}");
        else
          d_writer.print(",");
      }

      d_writer.print(", new int[] {");
      //Again sorry, just like print Args, but we need that -1

      for(int i = 0; i < dim; ++i) {
        d_writer.print(" "+ "s" + i +"-1");
		
        if(i != dim-1)
          d_writer.print(",");
      }
      d_writer.print("}");
	
      d_writer.println(", isRow);");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();
      /**********************************************/
      d_writer.writeComment("Create an array using an BaseInterface Array",true);
      d_writer.println("protected Array"+dim+"("+BabelConfiguration.getBaseInterface()+".Array core) {");
      d_writer.tab();
      d_writer.println("super(core);");
      d_writer.println("}");
      d_writer.backTab();
	    

      /*********************************************/
      d_writer.writeComment("Get the element at the specified array w/o bounds checking (Is not written for "+MAX_DIM+" dimensions, as this functionality is inherited from Array",true);
      if(dim != MAX_DIM) {
        if(ext.isInterface()) {
          d_writer.print("public " + elemType +".Wrapper _get(");
          printArgs("int","j",dim);
          d_writer.println(") {");
          d_writer.tab();
	    
          d_writer.print("return ("+elemType+".Wrapper)_get(");
          printArgsEndInZeros("","j",dim,MAX_DIM);
          d_writer.println(");");
			    
          d_writer.backTab();
          d_writer.println("}");
          d_writer.println();
        } else {
          d_writer.print("public " + elemType +" _get(");
          printArgs("int","j",dim);
          d_writer.println(") {");
          d_writer.tab();
	    
          d_writer.print("return ("+elemType+")_get(");
          printArgsEndInZeros("","j",dim,MAX_DIM);
          d_writer.println(");");
			    
          d_writer.backTab();
          d_writer.println("}");
          d_writer.println();
        }
      }
      /*****************************************************************/
      d_writer.writeComment("Get the element at the specified array with bounds checking",true);
      if(ext.isInterface()) {
        d_writer.print("public " + elemType +".Wrapper get(");
	    
        printArgs("int","j",dim);
        d_writer.println(") {");
        d_writer.tab();
	    
        d_writer.print("checkBounds(");
        printArgs("","j",dim);
        d_writer.println(");");

        d_writer.println(elemType + ".Wrapper ret = null;"); 
        d_writer.print("ret = ("+elemType+".Wrapper) _get(");
        printArgsEndInZeros("","j",dim, MAX_DIM);
        d_writer.println(");");
	     
        d_writer.println("return ret;");

        d_writer.println();

        d_writer.backTab();
        d_writer.println("}");
        d_writer.println();
      } else {
        d_writer.print("public " + elemType +" get(");
	    
        printArgs("int","j",dim);
        d_writer.println(") {");
        d_writer.tab();
	    
        d_writer.print("checkBounds(");
        printArgs("","j",dim);
        d_writer.println(");");

        d_writer.println(elemType + " ret = null;"); 
        d_writer.print("ret = ("+elemType+") _get(");
        printArgsEndInZeros("","j",dim, MAX_DIM);
        d_writer.println(");");
	     
        d_writer.println("return ret;"); 


        d_writer.println();

        d_writer.backTab();
        d_writer.println("}");
        d_writer.println();
      }

      /***********************************************************/
      if(dim == 1) {
        d_writer.writeComment("Get the length of the array",true);
        d_writer.println("public int length() {");
        d_writer.tab();
        d_writer.println("return super._length(0);");
        d_writer.backTab();
        d_writer.println("}");
        d_writer.println();
     
      }else{
      d_writer.writeComment("Get the length of the array in the specified dimension",true);
      d_writer.println("public int length(int dim) {");
      d_writer.tab();
      d_writer.println("return super._length(dim);");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();
      }
      /***********************************************/
      d_writer.writeComment("Make a copy of a borrowed array, addref otherwise",true);
      d_writer.println("public " + elemType +".Array"+dim+" smartCopy() {");
      d_writer.tab();
      d_writer.println(elemType+".Array"+dim+" ret = null;");
      d_writer.println(elemType+".Array preCast = null;");
      d_writer.println("preCast = ("+elemType+".Array) _smartCopy();");
      d_writer.println("return new "+elemType+".Array"+dim+
                       "(preCast.getBaseInterfaceArray());");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();

      /***********************************************/
      d_writer.writeComment("Copy elements of this array into a passed in array of exactly the smae size",true);
      d_writer.println("public void copy(" + elemType +".Array"+dim+" dest) {");
      d_writer.tab();
      d_writer.println("_copy((" + elemType +".Array) dest);");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();
      /****************************************************/
	    
      d_writer.writeComment("Reallocate the "+dim+" dimensional array with the specified, upper and lower bounds",true);
      d_writer.print("public void reallocate(");
      printArgs("int","l", dim);
      d_writer.print(",");
      printArgs("int","u", dim);
      d_writer.println(", boolean isRow)  {");
      d_writer.tab();
	    
      d_writer.print("reallocate("+dim+", new int[] {");
      printArgs("","l", dim);
      d_writer.print("}");

      d_writer.print(", new int[] {");
      printArgs("","u", dim);
      d_writer.print("}");

      d_writer.println(", isRow);");
      d_writer.backTab();
      d_writer.println("}");
	    
      d_writer.println();

      /**********************************************/

      d_writer.writeComment("Set the element at the specified array w/o bounds checking",true);
      d_writer.print("public void _set(");
      printArgs("int", "j", dim);
      d_writer.print(",");
      d_writer.println(elemType+" value) {");
      d_writer.tab();

      d_writer.print("_set(");
      printArgsEndInZeros("","j",dim,MAX_DIM);
      d_writer.println(", value);");
	    
      d_writer.backTab();
      d_writer.println("}");
	    
      /*****************************************************************/
      d_writer.writeComment("Set the element at the specified array with bounds checking",true);
      d_writer.print("public void set(");
      printArgs("int", "j", dim);
      d_writer.print(",");
      d_writer.println(elemType+" value) {");
      d_writer.tab();
	    
      d_writer.print("checkBounds(");
      printArgs("","j",dim);
      d_writer.println(");");

      d_writer.print("_set(");
      printArgsEndInZeros("","j",dim,MAX_DIM);
      d_writer.println(", value);");
      d_writer.println();

      d_writer.backTab();
      d_writer.println("}");	    

      /***************************/
      //fromArray method, crazy....
      d_writer.writeComment("Set the element at the specified array with bounds checking",true);
      d_writer.print("public void fromArray( ");
      d_writer.print(elemType);
      for(int i = 0; i < dim; ++i)
        d_writer.print("[]");
      d_writer.println(" orray , boolean inRow) {");
      d_writer.tab();
      d_writer.println("int[] lower = new int[7];");
      for(int i = 0; i<dim; ++i) {
        d_writer.print("lower["+i+"] = orray");
        for(int j = 1; j<i; ++j)
          d_writer.print("[0]");
        d_writer.println(".length-1;");
      }
      d_writer.println("_fromArray(orray, "+dim+", lower, inRow);");
      d_writer.backTab();
      d_writer.println("}");	    
	    

      /************************/
      writeToFromArrayFunctions(id,dim);


      /****************************************************/

      //Generate Holder class
      SymbolID aSymbol = new SymbolID(elemType + "$Array" + dim, 
                                      id.getVersion());
      writeHolderClass(aSymbol, true);
	 
      /****************************************************/
      d_writer.backTab();
      d_writer.println("}");  //Closing the class for this array
    }
  }

  /**
   *  A helper function for array generation.  This function generates
   *  the "arguments" from 0...(dim-1) for either calling or 
   *  delaring functions.  It outputs nothing before the arguments,
   *  and outputs no finishing character (such as ')' ).  
   *  preconditions: programming has outputted the first part of
   *        the function definition/call.  (such as "void foo(")
   *  
   *  The programmer then calls: printArgs("int","j",5);
   *  This function will output "int j0, int j1, int j2, int j3, int j4"
   *
   *  Finally the programmer should finish the output (like ")  {)"
   *
   *  So the total output for that line is: 
   *  void foo(int j0, int j1, int j2, int j3, int j4) {     
   */
  void printArgs(String type, String var, int dim) {
	
    for(int i = 0; i < dim; ++i) {
	    
      d_writer.print(" "+type+" "+ var + i);
	    
      if(i != dim-1)
        d_writer.print(",");
    }
  }

  /**
   *  Basically the same as print args, but prints a series of 
   *  0's from dim..(max-1) ie:
   * 
   *  printArgsEndInZeros("", "s", 3, 7);
   *       will output:
   *  s0, s1, s2, 0, 0, 0, 0 
   */

  void printArgsEndInZeros(String type, String var,int dim, int max) {

    for(int i = 0; i < dim; ++i) {
	
      d_writer.print(" "+type+" "+var+i);
	    
      if(i != dim-1 || dim != max) 
        d_writer.print(","); 
    }
    
    for(int i = dim; i < max; ++i) {
      d_writer.print(" 0");
	
      if(i != max-1)
        d_writer.print(",");
    }

  }

  /**
   *  Each sidl object requires code to handle sidl arrays of that object.  
   *  sidl arrays may (currently) be up to MAX_DIM dimensions.  
   *  This function outputs the code of all 1-MAX_DIM dimensional possibilties
   *
   *  I apologize for how bizarrely complex this is.  I should have divided it 
   *  up with helper functions.  Hopefull I'll have time to fix it later.
   *
   */

  private void writeBaseNumberedObjectArrays(Extendable ext) {
    SymbolID id = ext.getSymbolID(); 
    String elemType = Java.getFullJavaSymbolName(id);
    for(int dim = 1; dim <= MAX_DIM; ++dim) {

      d_writer.writeComment("The implementation of "+elemType+" "+dim+" arrays.",true);
      d_writer.println("public static class Array"+dim+" extends Array {");
      d_writer.tab();
      d_writer.println();
      /***************************************/
      d_writer.writeComment("Construct an empty "+dim+" dimensional array object.",true);
      d_writer.println("public Array"+dim+"() {");
      d_writer.tab();
      d_writer.println("super();");	
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();
      /***************************************/
      d_writer.writeComment("Create an array using an IOR pointer",true);
      d_writer.println("protected Array"+dim+"(long array, boolean owner) {");
      d_writer.tab();
      d_writer.println("super(array, owner);");	
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();
      /****************************************/
      d_writer.writeComment("Create a "+dim+" dimensional array with the specified, upper and lower bounds",true);
      d_writer.print("public Array"+dim+"(");
      printArgs("int", "l", dim);
      d_writer.print(",");
	    
      printArgs("int", "u", dim);
      d_writer.println(", boolean isRow) {");
      d_writer.tab();
	    
      d_writer.print("super("+dim+", new int[] {");
      printArgs("","l",dim);
      d_writer.print("}");
	
      d_writer.print(", new int[] {");
      printArgs("","u",dim);
      d_writer.print("}");
      d_writer.println(", isRow);");
      d_writer.backTab();
      d_writer.println("}");
	    
      
      d_writer.println();
      /*************************************/
      d_writer.writeComment("Create a "+dim+" dimensional array with the specified, upper bounds.  Lower bounds are all 0",true);
      d_writer.print("public Array"+dim+"(");
      printArgs("int","s",dim);
      d_writer.println(", boolean isRow)  {");
      d_writer.tab();
	    
      d_writer.print("super("+dim+", new int[] {");
      //Sorray about this, but this is just like PrintArgs, but
      //0's instead of variables.
      for(int i = 0; i < dim; ++i) {
        d_writer.print(" 0");
        if(i == dim-1) 
          d_writer.print("}");
        else
          d_writer.print(",");
      }

      d_writer.print(", new int[] {");
      //Again sorry, just like print Args, but we need that -1

      for(int i = 0; i < dim; ++i) {
        d_writer.print(" "+ "s" + i +"-1");
		
        if(i != dim-1)
          d_writer.print(",");
      }
      d_writer.print("}");
	
      d_writer.println(", isRow);");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();
      /***************************************/
      d_writer.beginBlockComment(true);
      d_writer.println("");
      d_writer.println("Create a "+dim+" dimensional array using the specified java array.");
      d_writer.println("The lower bounds of the constructed array will start at zero.");
      d_writer.println("An array index out of range exception will be thrown if the ");
      d_writer.println("bounds are invalid.");
      d_writer.endBlockComment(true);
      d_writer.print("public Array"+dim+"("+elemType);
      for(int i = 0; i< dim; ++i) 
        d_writer.print("[]");
      d_writer.println(" array, boolean isRow) {");
      d_writer.tab();
      d_writer.println("super();");
      d_writer.println("fromArray(array, isRow);");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();

      /*******************************************************/
      if(dim == 1) {
      d_writer.writeComment("Get the length of the array",true);
      d_writer.println("public int length() {");
      d_writer.tab();
      d_writer.println("return super._length(0);");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();
     
      }else{
      d_writer.writeComment("Get the length of the array in the specified dimension",true);
      d_writer.println("public int length(int dim) {");
      d_writer.tab();
      d_writer.println("return super._length(dim);");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();
      }

      /*********************************************/
      d_writer.writeComment("Get the element at the specified array w/o bounds checking (Is not written for "+MAX_DIM+" dimensions, as this functionality is inherited from Array",true);
      if(dim != MAX_DIM) {
        d_writer.print("public " + elemType +".Wrapper _get(");
        printArgs("int","j",dim);
        d_writer.println(") {");
        d_writer.tab();
	    
        d_writer.print("return ("+elemType+".Wrapper)_get(");
        printArgsEndInZeros("","j",dim,MAX_DIM);
        d_writer.println(");");
			    
        d_writer.backTab();
        d_writer.println("}");
        d_writer.println();
      }
      /*****************************************************************/
      d_writer.writeComment("Get the element at the specified array with bounds checking",true);
      d_writer.print("public " + elemType +".Wrapper get(");
	    
      printArgs("int","j",dim);
      d_writer.println(") {");
      d_writer.tab();
	    
      d_writer.print("checkBounds(");
      printArgs("","j",dim);
      d_writer.println(");");

      d_writer.println(elemType + ".Wrapper ret = null;"); 
      d_writer.print("ret = ("+elemType+".Wrapper) _get(");
      printArgsEndInZeros("","j",dim, MAX_DIM);
      d_writer.println(");");
	     
      d_writer.println("return ret;"); 

      d_writer.println();

      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();
	 

      /***********************************************/
      d_writer.writeComment("Make a copy of a borrowed array, addref otherwise",true);
      d_writer.println("public " + elemType +".Array"+dim+" smartCopy() {");
      d_writer.tab();
      d_writer.println(elemType+".Array"+dim+" ret = null;");
      d_writer.println(elemType+".Array preCast = null;");
      d_writer.println("preCast = ("+elemType+".Array)_smartCopy();");
      d_writer.println("return new "+elemType+".Array"+dim+
                       "(get_ior_pointer(), true);");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();

      /***********************************************/
      d_writer.writeComment("Copy elements of this array into a passed in array of exactly the smae size",true);
      d_writer.println("public void copy(" + elemType +".Array"+dim+" dest) {");
      d_writer.tab();
      d_writer.println("_copy((" + elemType +".Array) dest);");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();
      /****************************************************/
	    
      d_writer.writeComment("Reallocate the "+dim+" dimensional array with the specified, upper and lower bounds",true);
      d_writer.print("public void reallocate(");
      printArgs("int","l", dim);
      d_writer.print(",");
      printArgs("int","u", dim);
      d_writer.println(", boolean isRow)  {");
      d_writer.tab();
	    
      d_writer.print("reallocate("+dim+", new int[] {");
      printArgs("","l", dim);
      d_writer.print("}");

      d_writer.print(", new int[] {");
      printArgs("","u", dim);
      d_writer.print("}");

      d_writer.println(", isRow);");
      d_writer.backTab();
      d_writer.println("}");
	    
      d_writer.println();

      /**********************************************/

      d_writer.writeComment("Set the element at the specified array w/o bounds checking",true);
      d_writer.print("public void _set(");
      printArgs("int", "j", dim);
      d_writer.print(",");
      d_writer.println(elemType+" value) {");
      d_writer.tab();

      d_writer.print("_set(");
      printArgsEndInZeros("","j",dim,MAX_DIM);
      d_writer.println(", value);");
	    
      d_writer.backTab();
      d_writer.println("}");
	    
      /*****************************************************************/
      d_writer.writeComment("Set the element at the specified array with bounds checking",true);
      d_writer.print("public void set(");
      printArgs("int", "j", dim);
      d_writer.print(",");
      d_writer.println(elemType+" value) {");
      d_writer.tab();
	    
      d_writer.print("checkBounds(");
      printArgs("","j",dim);
      d_writer.println(");");

      d_writer.print("_set(");
      printArgsEndInZeros("","j",dim,MAX_DIM);
      d_writer.println(", value);");
      d_writer.println();

      d_writer.backTab();
      d_writer.println("}");	    

      /************************/

      //fromArray method, crazy....
      d_writer.writeComment("Set the element at the specified array with bounds checking",true);
      d_writer.print("public void fromArray( ");
      d_writer.print(elemType);
      for(int i = 0; i < dim; ++i)
        d_writer.print("[]");
      d_writer.println(" orray , boolean inRow) {");
      d_writer.tab();
      d_writer.println("int[] lower = new int[7];");
      for(int i = 0; i<dim; ++i) {
        d_writer.print("lower["+i+"] = orray");
        for(int j = 1; j<i; ++j)
          d_writer.print("[0]");
        d_writer.println(".length-1;");
      }
      d_writer.println("_fromArray(orray, "+dim+", lower, inRow);");
      d_writer.backTab();
      d_writer.println("}");	    
	    

      /******************************************************/
	    
      writeToFromArrayFunctions(id,dim);

      /****************************************************/

      //Generate Holder class
      SymbolID aSymbol = new SymbolID(elemType + "$Array" + dim, 
                                      id.getVersion());
      writeHolderClass(aSymbol, true);
	 
      /****************************************************/
      d_writer.backTab();
      d_writer.println("}");  //Closing the class for this array
    }
  }

  private void writeToFromArrayFunctions(SymbolID id, int dim) {
    String elemType = Java.getFullJavaSymbolName(id);

    switch(dim) {
    case 1:
      d_writer.beginBlockComment(true);
      d_writer.println("");
      d_writer.println("Convert the sidl array into a Java array.  This method will copy");
      d_writer.println("the sidl array into the Java array.  The resulting Java array will");
      d_writer.println("(obviously) start with a zero lower bound.  If the sidl array is");
      d_writer.println("empty (null), then a null Java array will be returned.");
      d_writer.println("");
      d_writer.endBlockComment(true);
      d_writer.println("public "+elemType+"[] toArray() {");
      d_writer.tab();
      d_writer.println(""+elemType+"[] array = null;");
      d_writer.println("if (!isNull()) {");
      d_writer.tab();
      d_writer.println("checkDimension(1);");
      d_writer.println("int l0 = _lower(0);");
      d_writer.println("int u0 = _upper(0);");
      d_writer.println("array = new "+elemType+"[u0-l0+1];");
      d_writer.println("for (int i = l0; i <= u0; i++) {");
      d_writer.tab();
      d_writer.println("array[i-l0] = _get(i);");
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("return array;");
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("");
      d_writer.println("");
      break;
    case 2: 
      d_writer.beginBlockComment(true);
      d_writer.println("");
      d_writer.println("Convert the sidl array into a Java array.  This method will copy");
      d_writer.println("the sidl array into the Java array.  The resulting Java array will");
      d_writer.println("(obviously) start with a zero lower bound.  If the sidl array is");
      d_writer.println("empty (null), then a null Java array will be returned.");
      d_writer.println("");
      d_writer.endBlockComment(true);
      d_writer.println("public "+elemType+"[][] toArray() {");
      d_writer.tab();
      d_writer.println(""+elemType+"[][] array = null;");
      d_writer.println("if (!isNull()) {");
      d_writer.tab();
      d_writer.println("checkDimension(2);");
      d_writer.println("int l0 = _lower(0);");
      d_writer.println("int u0 = _upper(0);");
      d_writer.println("int l1 = _lower(1);");
      d_writer.println("int u1 = _upper(1);");
      d_writer.println("array = new "+elemType+"[u0-l0+1][];");
      d_writer.println("for (int i = l0; i <= u0; i++) {");
      d_writer.tab();
      d_writer.println("array[i] = new "+elemType+"[u1-l1+1];");
      d_writer.println("for (int j = l1; j <= u1; j++) {");
      d_writer.tab();
      d_writer.println("array[i-l0][j-l1] = _get(i, j);");
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("return array;");
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("");
      break;
    case 3:

      d_writer.beginBlockComment(true);
      d_writer.println("");
      d_writer.println("Convert the sidl array into a Java array.  This method will copy");
      d_writer.println("the sidl array into the Java array.  The resulting Java array will");
      d_writer.println("(obviously) start with a zero lower bound.  If the sidl array is");
      d_writer.println("empty (null), then a null Java array will be returned.");
      d_writer.println("");
      d_writer.endBlockComment(true);
      d_writer.println("public "+elemType+"[][][] toArray() {");
      d_writer.tab();
      d_writer.println(""+elemType+"[][][] array = null;");
      d_writer.println("if (!isNull()) {");
      d_writer.tab();
      d_writer.println("checkDimension(3);");
      d_writer.println("int l0 = _lower(0);");
      d_writer.println("int u0 = _upper(0);");
      d_writer.println("int l1 = _lower(1);");
      d_writer.println("int u1 = _upper(1);");
      d_writer.println("int l2 = _lower(2);");
      d_writer.println("int u2 = _upper(2);");
      d_writer.println("array = new "+elemType+"[u0-l0+1][][];");
      d_writer.println("for (int i = l0; i <= u0; i++) {");
      d_writer.tab();
      d_writer.println("array[i] = new "+elemType+"[u1-l1+1][];");
      d_writer.println("for (int j = l1; j <= u1; j++) {");
      d_writer.tab();
      d_writer.println("array[i][j] = new "+elemType+"[u2-l2+1];");
      d_writer.println("for (int k = l2; k <= u2; k++) {");
      d_writer.tab();
      d_writer.println("array[i-l0][j-l1][k-l2] = _get(i, j, k);");
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("return array;");
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("");
	    
      break;
	    
    case 4:
      d_writer.beginBlockComment(true);
      d_writer.println("");
      d_writer.println("Convert the sidl array into a Java array.  This method will copy");
      d_writer.println("the sidl array into the Java array.  The resulting Java array will");
      d_writer.println("(obviously) start with a zero lower bound.  If the sidl array is");
      d_writer.println("empty (null), then a null Java array will be returned.");
      d_writer.println("");
      d_writer.endBlockComment(true);
      d_writer.println("public "+elemType+"[][][][] toArray() {");
      d_writer.tab();
      d_writer.println(""+elemType+"[][][][] array = null;");
      d_writer.println("if (!isNull()) {");
      d_writer.tab();
      d_writer.println("checkDimension(4);");
      d_writer.println("int l0 = _lower(0);");
      d_writer.println("int u0 = _upper(0);");
      d_writer.println("int l1 = _lower(1);");
      d_writer.println("int u1 = _upper(1);");
      d_writer.println("int l2 = _lower(2);");
      d_writer.println("int u2 = _upper(2);");
      d_writer.println("int l3 = _lower(3);");
      d_writer.println("int u3 = _upper(3);");
      d_writer.println("array = new "+elemType+"[u0-l0+1][][][];");
      d_writer.println("for (int i = l0; i <= u0; i++) {");
      d_writer.tab();
      d_writer.println("array[i] = new "+elemType+"[u1-l1+1][][];");
      d_writer.println("for (int j = l1; j <= u1; j++) {");
      d_writer.tab();
      d_writer.println("array[i][j] = new "+elemType+"[u2-l2+1][];");
      d_writer.println("for (int k = l2; k <= u2; k++) {");
      d_writer.tab();
      d_writer.println("array[i][j][k] = new "+elemType+"[u3-l3+1];");
      d_writer.println("for (int l = l3; l <= u3; l++) {");
      d_writer.tab();
      d_writer.println("array[i-l0][j-l1][k-l2][l-l3] = _get(i, j, k, l);");
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("return array;");
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("");
      break;
    case 5: 
      d_writer.beginBlockComment(true);
      d_writer.println("");
      d_writer.println("Convert the sidl array into a Java array.  This method will copy");
      d_writer.println("the sidl array into the Java array.  The resulting Java array will");
      d_writer.println("(obviously) start with a zero lower bound.  If the sidl array is");
      d_writer.println("empty (null), then a null Java array will be returned.");
      d_writer.println("");
      d_writer.endBlockComment(true);
      d_writer.println("public "+elemType+"[][][][][] toArray() {");
      d_writer.tab();
      d_writer.println(""+elemType+"[][][][][] array = null;");
      d_writer.println("if (!isNull()) {");
      d_writer.tab();
      d_writer.println("checkDimension(5);");
      d_writer.println("int l0 = _lower(0);");
      d_writer.println("int u0 = _upper(0);");
      d_writer.println("int l1 = _lower(1);");
      d_writer.println("int u1 = _upper(1);");
      d_writer.println("int l2 = _lower(2);");
      d_writer.println("int u2 = _upper(2);");
      d_writer.println("int l3 = _lower(3);");
      d_writer.println("int u3 = _upper(3);");
      d_writer.println("int l4 = _lower(4);");
      d_writer.println("int u4 = _upper(4);");
      d_writer.println("array = new "+elemType+"[u0-l0+1][][][][];");
      d_writer.println("for (int i = l0; i <= u0; i++) {");
      d_writer.tab();
      d_writer.println("array[i] = new "+elemType+"[u1-l1+1][][][];");
      d_writer.println("for (int j = l1; j <= u1; j++) {");
      d_writer.tab();
      d_writer.println("array[i][j] = new "+elemType+"[u2-l2+1][][];");
      d_writer.println("for (int k = l2; k <= u2; k++) {");
      d_writer.tab();
      d_writer.println("array[i][j][k] = new "+elemType+"[u3-l3+1][];");
      d_writer.println("for (int l = l3; l <= u3; l++) {");
      d_writer.tab();
      d_writer.println("array[i][j][k][l] = new "+elemType+"[u4-l4+1];");
      d_writer.println("for (int m = l4; m <= u4; m++) {");
      d_writer.tab();
      d_writer.println("array[i-l0][j-l1][k-l2][l-l3][m-l4] = _get(i, j, k, l, m);");
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("return array;");
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("");
      break;
    case 6:
      d_writer.beginBlockComment(true);
      d_writer.println("");
      d_writer.println("Convert the sidl array into a Java array.  This method will copy");
      d_writer.println("the sidl array into the Java array.  The resulting Java array will");
      d_writer.println("(obviously) start with a zero lower bound.  If the sidl array is");
      d_writer.println("empty (null), then a null Java array will be returned.");
      d_writer.println("");
      d_writer.endBlockComment(true);
      d_writer.println("public "+elemType+"[][][][][][] toArray() {");
      d_writer.tab();
      d_writer.println(""+elemType+"[][][][][][] array = null;");
      d_writer.println("if (!isNull()) {");
      d_writer.tab();
      d_writer.println("checkDimension(6);");
      d_writer.println("int l0 = _lower(0);");
      d_writer.println("int u0 = _upper(0);");
      d_writer.println("int l1 = _lower(1);");
      d_writer.println("int u1 = _upper(1);");
      d_writer.println("int l2 = _lower(2);");
      d_writer.println("int u2 = _upper(2);");
      d_writer.println("int l3 = _lower(3);");
      d_writer.println("int u3 = _upper(3);");
      d_writer.println("int l4 = _lower(4);");
      d_writer.println("int u4 = _upper(4);");
      d_writer.println("int l5 = _lower(5);");
      d_writer.println("int u5 = _upper(5);");
      d_writer.println("array = new "+elemType+"[u0-l0+1][][][][][];");
      d_writer.println("for (int i = l0; i <= u0; i++) {");
      d_writer.tab();
      d_writer.println("array[i] = new "+elemType+"[u1-l1+1][][][][];");
      d_writer.println("for (int j = l1; j <= u1; j++) {");
      d_writer.tab();
      d_writer.println("array[i][j] = new "+elemType+"[u2-l2+1][][][];");
      d_writer.println("for (int k = l2; k <= u2; k++) {");
      d_writer.tab();
      d_writer.println("array[i][j][k] = new "+elemType+"[u3-l3+1][][];");
      d_writer.println("for (int l = l3; l <= u3; l++) {");
      d_writer.tab();
      d_writer.println("array[i][j][k][l] = new "+elemType+"[u4-l4+1][];");
      d_writer.println("for (int m = l4; m <= u4; m++) {");
      d_writer.tab();
      d_writer.println("array[i][j][k][l][m] = new "+elemType+"[u4-l4+1];");
      d_writer.println("for (int n = l5; n <= u5; n++) {");
      d_writer.tab();
      d_writer.println("array[i-l0][j-l1][k-l2][l-l3][m-l4][n-l5] = _get(i, j, k, l, m, n);");
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("return array;");
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("");
      break;
    case 7:
      d_writer.beginBlockComment(true);
      d_writer.println("");
      d_writer.println("Convert the sidl array into a Java array.  This method will copy");
      d_writer.println("the sidl array into the Java array.  The resulting Java array will");
      d_writer.println("(obviously) start with a zero lower bound.  If the sidl array is");
      d_writer.println("empty (null), then a null Java array will be returned.");
      d_writer.println("");
      d_writer.endBlockComment(true);
      d_writer.println("public "+elemType+"[][][][][][][] toArray() {");
      d_writer.tab();
      d_writer.println(""+elemType+"[][][][][][][] array = null;");
      d_writer.println("if (!isNull()) {");
      d_writer.tab();
      d_writer.println("checkDimension(6);");
      d_writer.println("int l0 = _lower(0);");
      d_writer.println("int u0 = _upper(0);");
      d_writer.println("int l1 = _lower(1);");
      d_writer.println("int u1 = _upper(1);");
      d_writer.println("int l2 = _lower(2);");
      d_writer.println("int u2 = _upper(2);");
      d_writer.println("int l3 = _lower(3);");
      d_writer.println("int u3 = _upper(3);");
      d_writer.println("int l4 = _lower(4);");
      d_writer.println("int u4 = _upper(4);");
      d_writer.println("int l5 = _lower(5);");
      d_writer.println("int u5 = _upper(5);");
      d_writer.println("int l6 = _lower(6);");
      d_writer.println("int u6 = _upper(6);");
      d_writer.println("array = new "+elemType+"[u0-l0+1][][][][][][];");
      d_writer.println("for (int i = l0; i <= u0; i++) {");
      d_writer.tab();
      d_writer.println("array[i] = new "+elemType+"[u1-l1+1][][][][][];");
      d_writer.println("for (int j = l1; j <= u1; j++) {");
      d_writer.tab();
      d_writer.println("array[i][j] = new "+elemType+"[u2-l2+1][][][][];");
      d_writer.println("for (int k = l2; k <= u2; k++) {");
      d_writer.tab();
      d_writer.println("array[i][j][k] = new "+elemType+"[u3-l3+1][][][];");
      d_writer.println("for (int l = l3; l <= u3; l++) {");
      d_writer.tab();
      d_writer.println("array[i][j][k][l] = new "+elemType+"[u4-l4+1][][];");
      d_writer.println("for (int m = l4; m <= u4; m++) {");
      d_writer.tab();
      d_writer.println("array[i][j][k][l][m] = new "+elemType+"[u4-l4+1][];");
      d_writer.println("for (int n = l5; n <= u5; n++) {");
      d_writer.tab();
      d_writer.println("array[i][j][k][l][m][n] = new "+elemType+"[u5-l5+1];");
      d_writer.println("for (int o = l6; o <= u6; o++) {");
      d_writer.tab();
      d_writer.println("array[i-l0][j-l1][k-l2][l-l3][m-l4][n-l5][o-l6] = _get(i, j, k, l, m, n,o);");
      d_writer.println("");
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.backTab();
      d_writer.println("return array;");
      d_writer.println("}");
      d_writer.backTab();


    }
    
  }

}

