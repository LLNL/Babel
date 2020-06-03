
package gov.llnl.babel.backend.jdk;

import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeConstants;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.CodeSplicer;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.Utilities;
import gov.llnl.babel.backend.writers.LanguageWriterForJava;
import gov.llnl.babel.symbols.Argument;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.SymbolUtilities;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Create and write a source file for a Java extension class to wrap a 
 * BABEL extendable in a Java object. 
 */
public class JavaImplSource {
  
  private Extendable d_ext = null;
  private LanguageWriterForJava d_writer = null;
  private CodeSplicer d_splicer = null;
  private String d_classname = null;  // So files will be named with _Impl
                                      // suffix, for naming collisions
  private Context d_context;
  
  /**
   * Create an object capable of generating the source file for Java implementation
   *
   * @param ext   an interface or class symbol that needs source
   *              file for a Java extension class.
   */
  public JavaImplSource(Extendable ext,
                        Context context)
    throws CodeGenerationException 
  {
    if (ext == null) {
      throw new CodeGenerationException("Unexpected null extendable object");
    }
    
    d_ext= ext;
    d_context = context;
      
    SymbolID id = d_ext.getSymbolID();
    d_classname = Java.getJavaServerClassName(id); 
      
    int type = d_ext.getSymbolType();
    String filename = Java.getJavaImplSourceFile(id);
      
    // Set up the writer and the splicer
    try{
      // To generate _Impl file in correct directory
      d_context.getFileManager().setJavaStylePackageGeneration(true);
        
      d_splicer = d_context.getFileManager().getCodeSplicer(id, type, filename,
                                                           true, true);
      d_writer = new LanguageWriterForJava
        ((d_context.getFileManager()).createFile(id, type, "JAVAIMPL",
                                                 filename), d_context);
      d_context.getFileManager().setJavaStylePackageGeneration(false);
      d_writer.writeBanner(d_ext, filename,true, CodeConstants.C_DESC_IMPL_PREFIX + id.getFullName());
        
    } catch(java.io.IOException e){
      if (d_writer != null) {
        d_writer.close();
        d_writer = null;
      }
      throw new CodeGenerationException("IOException : " + e.getMessage() );
    }

  }
  
  /**
   * Writes Java implementation based on the provided symbol
   *
   * @param ext   an interface or class symbol that needs source
   *              file for a Java extension class.
   */
  public static void generateCode(Extendable ext,
                                  Context context) 
    throws CodeGenerationException
  {
    /** 
     * We don't generate Impl files for Interfaces or Abstact Classes
     */

    JavaImplSource source = new JavaImplSource(ext, context);
    source.generateCode();
    
  }    
  
  /**
   * Add splicer block.
   *
   * @param ext         splicer block-specific name extension.
   * @param method      Either a method instance, if splicer is for a method,
   *                    or null.
   * @param addDefaults TRUE if splicer defaults to be added for method;
   *                    otherwise, FALSE.
   * @param defComment  Default comment (for non-method splicer blocks).
   */
  public void addSplicerBlock(String ext, Method method, boolean addDefaults,
                              String defComment)
  {
    String name  = d_ext.getSymbolID().getFullName() + "." + ext;
    String defaultComment[] = { "This method has not been implemented" };
    String defaultCode[] = {};
    String notImplExWithCCA[] = {
      "    // DO-DELETE-WHEN-IMPLEMENTING exception.begin(" + name + ")",
      "    sidl.NotImplementedException noImpl = new sidl.NotImplementedException();",
      "    noImpl.setNote(\"This method has not been implmented.\");",
      "    sidl.RuntimeException.Wrapper rex = (sidl.RuntimeException.Wrapper) sidl.RuntimeException.Wrapper._cast(noImpl);",
      "    throw rex;",
      "    // DO-DELETE-WHEN-IMPLEMENTING exception.end(" + name + ")"
    };
    //                     "return " + Java.getDefaultReturnValue(method) + ";");
    if ( d_context.getConfig().getCCAMode() == true ) { 
        defaultCode = notImplExWithCCA;
    }
    if (method != null) {
      String mName = method.getShortMethodName();
      if (addDefaults) {
        d_splicer.splice(name, d_writer, mName, defaultComment, defaultCode);
      } else {
        d_splicer.splice(name, d_writer, mName);
      }
    } else {
      d_splicer.splice(name, d_writer, defComment);
    }
    d_writer.println();
  }

  /**
   * Writes Java implimentation based on the symbol given to the constructor
   */
  public synchronized void generateCode() 
    throws CodeGenerationException 
  {
    writePackageImports();
    writeClassBeginning();
    writeCtorDtor();
    writeSIDLDefinedMethods();
    addSplicerBlock("_misc", null, false, "miscellaneous");
    writeClassEnd();
    checkSplicer();
	
    d_writer.close();	
  }



  /********************************************************************************************
   *                           Private support methods
   ********************************************************************************************/

  /**
   * Writes the necessary package and import statements needed
   */
  private void writePackageImports() {
    writePackage();
    writeImports();
    addSplicerBlock("_imports", null, false, "additional imports");
  }
    
  private void writePackage() {
    String pkg = SymbolUtilities.getParentPackage(d_ext.getSymbolID().getFullName());
    d_writer.println("package " + pkg + ";");
    d_writer.println();
  }
    
  private void writeImports() {
    Iterator i = Utilities.sort(d_ext.getSymbolReferences()).iterator();
    while (i.hasNext()) {
      SymbolID id = (SymbolID) i.next();
      if (! id.getFullName().equals(d_ext.getSymbolID().getFullName())) {
        d_writer.println("import " + id.getFullName() + ";");
      }
    }
    //d_writer.println("import " + BabelConfiguration.getNotImplemented() + ";");
    d_writer.println();
  }
    
  /**
   * Writes class declaration and splicer block for private data
   */
  private void writeClassBeginning() {
    d_writer.writeComment(d_ext, true);
    d_writer.println("public class " + d_classname + " extends " + d_ext.getSymbolID().getShortName());
    d_writer.println("{");
    d_writer.println();
    d_writer.tab();
    addSplicerBlock("_data", null, false, "private data");
    d_writer.println();
    d_writer.println("static { ");
    addSplicerBlock("_load", null, false, "class initialization");
    d_writer.println("}");
    d_writer.println();
  }
    
   
    
  /**
   * Writes Java constructor and a "destructor", method that can be utilized to 
   * "destruct" a Java object.
   */
  private void writeCtorDtor() {
    String name = d_ext.getSymbolID().getShortName();
    // Constructor
    d_writer.writeComment("User defined constructor", true);
    d_writer.println("public " +d_classname + "(long IORpointer){");
    d_writer.tab();
    d_writer.println("super(IORpointer);");
    addSplicerBlock(name, null, false, "constructor");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();

    if(!d_ext.isAbstract() && !IOR.isSIDLSymbol(d_ext.getSymbolID()) ) {
      d_writer.writeComment("Back door constructor", true);
      d_writer.println("public " +d_classname + "(){");
      d_writer.tab();
      d_writer.println("d_ior = _wrap(this);");
      addSplicerBlock("_wrap", null, false, "_wrap");
      d_writer.backTab();
      d_writer.println("}");
      d_writer.println();
    }

    // "destructor"
    d_writer.writeComment("User defined destructing method", true);
    d_writer.println("public void dtor() throws Throwable{");
    d_writer.tab(); 
    addSplicerBlock("_dtor", null, false, "destructor");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();		     

    d_writer.writeComment("finalize method "
                          +"(Only use this if you're sure you need it!)", 
                          true);
    d_writer.println("public void finalize() throws Throwable{");
    d_writer.tab(); 
    addSplicerBlock("finalize", null, false, "finalize");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();	
  }
    
  /**
   * Writes both static and nonstatic methods defined in the sidl file
   */
  private void writeSIDLDefinedMethods() throws CodeGenerationException {
    List static_methods = (List) d_ext.getStaticMethods(false);
    if (static_methods.size() > 0) {
      d_writer.writeCommentLine("user defined static methods:");
      for (Iterator m = static_methods.iterator(); m.hasNext(); ) {
        Method method = (Method) m.next();
        if (IOR.generateHookMethods(d_ext, d_context)) {
          Method pre  = method.spawnPreHook();
          Method post = method.spawnPostHook();
          generateMethod(pre, false);
          generateMethod(method, true);
          generateMethod(post, false);
        } else {
          generateMethod(method, true);
        }
      }
    } 
    else {
      d_writer.writeCommentLine("user defined static methods: (none)");
    }
	
    d_writer.println();
    List nonstatic_methods = (List) d_ext.getNonstaticMethods(false);
    if (nonstatic_methods.size() > 0) {
      d_writer.writeCommentLine("user defined non-static methods:");
      for (Iterator m = nonstatic_methods.iterator(); m.hasNext(); ) {
        Method method = (Method) m.next();
        if (!method.isAbstract()) {
          if (IOR.generateHookMethods(d_ext, d_context)) {
            Method pre  = method.spawnPreHook();
            Method post = method.spawnPostHook();
            generateMethod(pre,false);
            generateMethod(method,true);
            generateMethod(post,false);
          } else {
            generateMethod(method,true);
          }
        }
      }
    } 
    else {
      d_writer.writeCommentLine("user defined non-static methods: (none)");
    }
    d_writer.println();
  }
    
  /**
   * Writes complete method header and comments for given parameter. 
   * Includes a default return if necessary.
   */
  private void generateMethod( Method method, boolean defaultSplicer ) 
  {
    if ( method == null ) { return; }
	
    generateMethodBeginning( method );
	
    if (method.getArgumentList().size() > 0) {
      generateArgumentList(method);  
    } 
    else {
      d_writer.println(" () ");
    }

    if(method.getThrows().size() > 0) {
      generateThrowsList(method); 
    }
    generateMethodBody(method, defaultSplicer); 
  }


  private void generateMethodBeginning ( Method method )
  {
    d_writer.writeComment (method, true);
    d_writer.print("public ");
    if(method.isStatic()){
      d_writer.print("static ");
    }
    d_writer.print(Java.getJavaReturnType(method.getReturnType()) + " ");
    d_writer.print(Java.getJavaServerMethodName(method));
  }

  private void generateArgumentList( Method method )
  {
    if ( method == null ) { return; }
   
    List args = method.getArgumentList();

    d_writer.println(" (");
    d_writer.tab();
    for( Iterator a = args.iterator(); a.hasNext(); ) { 
      Argument arg = (Argument) a.next();
      d_writer.print("/*" + arg.getModeString() + "*/ " +
                     Java.getJavaServerArgument( arg ) + " " + 
                     arg.getFormalName());
      if ( a.hasNext() ) { 
        d_writer.println(",");
      }
    }
    d_writer.println(" ) ");
    d_writer.backTab();
  }
    
  private void generateThrowsList( Method method )  
  {
    if ( method == null ) { return; }	
 
    Set exceptions = method.getThrows();
	
    d_writer.print( "throws " );
    d_writer.tab();
    for( Iterator e = exceptions.iterator(); e.hasNext(); ) { 
      SymbolID id = (SymbolID) e.next();
      d_writer.print( Java.getFullJavaSymbolName(id) );
      if ( (!SymbolUtilities.isBaseException(id)) 
           && (d_context.getSymbolTable().lookupSymbol(id).isInterface()) ) {
            d_writer.print(".Wrapper");
          }
      if ( e.hasNext() ) { 
        d_writer.println(", ");
      }
    }
    d_writer.println();
    d_writer.backTab();
	
  }
    

  private void generateMethodBody ( Method method, boolean defaultSplicer ) 
  {
    d_writer.println("{");
    d_writer.tab();
    addSplicerBlock(method.getLongMethodName(), method, defaultSplicer, null);
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();

  }

  
  /**
   * Closes out class
   */
  private void writeClassEnd() 
  {
    String name = d_ext.getSymbolID().getShortName();
    d_writer.backTab();
    d_writer.print("} "); // end of class
    d_writer.writeCommentLine("end class " + name );
    d_writer.println();
  }

  /**
   * Print any remaining symbols in the splicer
   */
  private void checkSplicer() 
  {
    if (d_splicer.hasUnusedSymbolEdits()) {
      d_writer.beginBlockComment(true);
      d_writer.println(CodeConstants.C_BEGIN_UNREFERENCED_METHODS);
      d_writer.println(CodeConstants.C_UNREFERENCED_COMMENT1);
      d_writer.println(CodeConstants.C_UNREFERENCED_COMMENT2);
      d_writer.println(CodeConstants.C_UNREFERENCED_COMMENT3);
      d_writer.endBlockComment(true);
      d_splicer.outputUnusedSymbolEdits( d_writer );
      d_writer.writeCommentLine(CodeConstants.C_END_UNREFERENCED_METHODS);
    }
  }
}
