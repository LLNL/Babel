//
// File:        TypeModule.java
// Package:     gov.llnl.babel.backend.fortran
// Copyright:   (c) 2003 Lawrence Livermore National Security, LLC
// Revision:    @(#) $Revision: 7421 $
// Date:        $Date: 2011-12-15 17:06:06 -0800 (Thu, 15 Dec 2011) $
// Description: Generate an F90 module containing a derived type
// 

package gov.llnl.babel.backend.fortran;

import gov.llnl.babel.BabelConfiguration;
import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeConstants;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.writers.LanguageWriter;
import gov.llnl.babel.backend.writers.LanguageWriterForFortran;
import gov.llnl.babel.backend.SortComparator;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.symbols.Type;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Enumeration;
import gov.llnl.babel.symbols.Comment;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.Version;
import gov.llnl.babel.symbols.Method;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

/**
 * This class generates a FORTRAN 90 module holding the derived type for
 * client-side users.
 *
 * The type is held separately to avoid circular dependencies.
 *
 * If d_sym is null, we generate a type for generic arrays.  Since this is 
 * A special case, the code is now full of little hacks to deal with it.
 */
public class TypeModule {
  static private final int s_maxArray = BabelConfiguration.getMaximumArray();

  /**
   * This holds the output device where the file will be written.
   */
  private LanguageWriterForFortran d_lw;

  /**
   * This is the extendable (i.e., class or interface) that is being
   * generated.
   */
  private Symbol d_sym;

  public TypeModule(LanguageWriterForFortran writer,
                    Symbol sym)
  {
    d_lw = writer;
    d_sym = sym;
  }

  private void describeFile(Context context)
  {
    d_lw.beginBlockComment(false);
    d_lw.println("This file contains a FORTRAN " +
                 Fortran.getFortranVersion(context) +
                 " derived type for the");
    if(d_sym == null)
      d_lw.println("sidl type sidl_array.");
    else
      d_lw.println("sidl type " + d_sym.getFullName() + ".");
    d_lw.endBlockComment(false);
  }

   static void writeArrayType(LanguageWriter lw,
                             SymbolID id,
                             String implDataType,
                             Context context)
  {

    for(int i = 1; i <= s_maxArray; ++i) {

      lw.println("type " + Fortran.getArrayName(id, i));
      lw.tab();
      if (Fortran.hasBindC(context)) {
          lw.println("type(c_ptr) :: d_array = c_null_ptr");
      } else {
        lw.println("sequence");
        lw.println("integer (kind=sidl_arrayptr) :: d_array");
      }
      if (implDataType != null){
        lw.println(implDataType + ", pointer, &");
        lw.tab();
        lw.print("dimension(:");
        for(int j = 1; j < i; ++j) {
          lw.print(",:");
        }
        lw.println(") :: d_data");
        lw.backTab();
      }
      
      lw.backTab();
      lw.println("end type " + Fortran.getArrayName(id, i));
      lw.println();
    }
  }

  private void writeGenericArrayType(SymbolID id,
                                    Context context)
  {
    d_lw.println("type sidl__array");
    d_lw.tab();
    if (Fortran.hasBindC(context)) {
      d_lw.println("type(c_ptr) :: d_array = c_null_ptr");
    } else { 
      d_lw.println("sequence");
      d_lw.println("integer (kind=sidl_arrayptr) :: d_array");
    }
    d_lw.backTab();
    d_lw.println("end type sidl__array");
    d_lw.println();
  
  }

  private void declareType(Extendable ext, SymbolID id, Context context) {
    d_lw.println();
    d_lw.println("type " + Fortran.getTypeName(id));
    d_lw.tab();
    if(Fortran.hasBindC(context)) {
      d_lw.println("type(c_ptr) :: d_ior = c_null_ptr");
      d_lw.println("type(" + Fortran.getBindCEPVTypeName(id, false) +
                   "), pointer :: d_epv => null()");
      if(ext.isInterface())
        d_lw.println("type(c_ptr) :: d_object = c_null_ptr");
    }
    else {
      d_lw.println("sequence");
      d_lw.println("integer (kind=sidl_iorptr) :: d_ior");
    }
    d_lw.backTab();
    d_lw.println("end type " + Fortran.getTypeName(id));
  }

  private void generateCacheEPV(Extendable ext)
  {
    SymbolID id = ext.getSymbolID();    
    d_lw.println("subroutine cache_epv_s(self)");
    d_lw.tab();
    d_lw.println("use, intrinsic :: iso_c_binding");
    d_lw.println("implicit none");
    d_lw.println("class(" + Fortran.getTypeName(id) + ") :: self");
    d_lw.println("type(c_ptr) :: cptr = c_null_ptr");
    d_lw.println("interface");
    d_lw.tab();
    {
      String bindc_name = Fortran.getSymbolName(id) + "_getEPV";
      d_lw.println("type(c_ptr) function get_epv_from_ptr(ior) bind(c, name=\"" +
                   bindc_name + "\")");
      d_lw.tab();
      d_lw.println("use iso_c_binding");
      d_lw.println("type(c_ptr), value :: ior");
      d_lw.backTab();
      d_lw.println("end function get_epv_from_ptr");
    }
    if(ext.isInterface()) {
      String bindc_name = Fortran.getSymbolName(id) + "_getObject";
      d_lw.println("type(c_ptr) function get_obj_from_ptr(ior) bind(c, name=\"" +
                   bindc_name + "\")");
      d_lw.tab();
      d_lw.println("use iso_c_binding");
      d_lw.println("type(c_ptr), value :: ior");
      d_lw.backTab();
      d_lw.println("end function get_obj_from_ptr");
    }
    d_lw.backTab();
    d_lw.println("end interface");
    d_lw.println("self%d_epv => null()");
    d_lw.println("if(c_associated(self%d_ior)) then");
    d_lw.tab();
    if(ext.isInterface())
      d_lw.println("self%d_object = get_obj_from_ptr(self%d_ior)");
    d_lw.println("cptr = get_epv_from_ptr(self%d_ior)");
    d_lw.println("if(c_associated(cptr)) then");
    d_lw.tab();
    d_lw.println("call c_f_pointer(cptr, self%d_epv)");
    d_lw.backTab();
    d_lw.println("endif");
    d_lw.backTab();
    d_lw.println("endif");
    d_lw.backTab();
    d_lw.println("end subroutine cache_epv_s");
    d_lw.println();
  }

  private void generateEPVMethod(Method m, Set alreadySeen)
    throws CodeGenerationException
  {
    String name = m.getLongMethodName();
    if (!alreadySeen.contains(name)) {
      alreadySeen.add(name);
      d_lw.println("type(c_funptr) :: " +
                   IOR.getVectorEntry(m.getLongMethodName()));
    }
  }

  private void generateParentEntries(Extendable ext,
                                     boolean doStatic,
                                     Set methodsAlreadySeen,
                                     Set extAlreadySeen,
                                     Context d_context) 
    throws CodeGenerationException
  {
    if (ext instanceof gov.llnl.babel.symbols.Class) {
      generateEPVEntries
        (((gov.llnl.babel.symbols.Class)ext).getParentClass(), doStatic, 
         methodsAlreadySeen, extAlreadySeen, d_context);
    }
    /*
     * It is critical that we always visit parent interfaces in a canonical
     * order. The precise order is not important. The fact that the same
     * order is used every time is critical!
     */
    Object[] parents = ext.getParentInterfaces(false).toArray();
    Arrays.sort(parents, new SortComparator());
    for(int i = 0; i < parents.length; ++i){
      generateEPVEntries((Extendable)parents[i], doStatic,
                         methodsAlreadySeen, extAlreadySeen, d_context);
    }
  }

  private void generateLocalEntries(Extendable ext, 
                                    boolean doStatic,
                                    Set methodsAlreadySeen,
                                    Context d_context)
    throws CodeGenerationException
  {
    /*
     * Process the methods in sorted order and output each function
     * pointer prototype in order.
     */
    //Set methodsAlreadySeen_save = new HashSet(methodsAlreadySeen);
    d_lw.writeCommentLine("Methods introduced in " +
                          ext.getSymbolID().getSymbolName());
    List methods = doStatic
      ? ext.getStaticMethods(false) : ext.getNonstaticMethods(false);
    for(Iterator m = methods.iterator(); m.hasNext(); ) {
      Method method = (Method) m.next();
      generateEPVMethod(method, methodsAlreadySeen);
      if ((method.getCommunicationModifier() == Method.NONBLOCKING) &&
          !d_context.getConfig().getSkipRMI()) {
        Method send = method.spawnNonblockingSend();
        Method recv = method.spawnNonblockingRecv();
        if(send != null) generateEPVMethod(send, methodsAlreadySeen);
        if(recv != null) generateEPVMethod(recv, methodsAlreadySeen);
      }
    }
    //TODO: add support for fast-calls
    // if(d_context.getConfig().getFastCall()) {
    //   d_writer.writeCommentLine("Native entry points for methods introduced in " +
    //                             ext.getSymbolID().getSymbolName());
    //   for(Iterator m = methods.iterator(); m.hasNext(); ) {
    //     Method method = (Method) m.next();
    //     generateNativeEPVMethod(method, self, methodsAlreadySeen_save);
    //   }
    //   d_writer.println();
    // }
  }

  private static final boolean isBaseClass(Extendable ext) {
    return ((ext instanceof gov.llnl.babel.symbols.Class) &&
       (((gov.llnl.babel.symbols.Class)ext).getParentClass() == null));
  }
  
  private static final boolean hasParents(Extendable ext) {
    return (!isBaseClass(ext)) || (!ext.getParentInterfaces(false).isEmpty());
  }
  
  private void generateEPVEntries(Extendable ext,
                                  boolean doStatic,
                                  Set methodsAlreadySeen,
                                  Set extAlreadySeen,
                                  Context d_context) 
    throws CodeGenerationException 
  {
    if ((ext != null) && !extAlreadySeen.contains(ext)) {
      if (hasParents(ext)) {
        generateParentEntries(ext, doStatic, methodsAlreadySeen, 
                              extAlreadySeen, d_context);
      }
      generateLocalEntries(ext, doStatic, methodsAlreadySeen, d_context);
      extAlreadySeen.add(ext);
    }
  }

  
  private void generateEPVType(Extendable ext, boolean doStatic, Context d_context)
    throws CodeGenerationException
  {
    SymbolID id = ext.getSymbolID();
    String name = Fortran.getBindCEPVTypeName(id, doStatic);
    Set methodsAlreadySeen = new HashSet();
    Set extAlreadySeen = new HashSet();
    
    d_lw.writeCommentLine("This is a Fortran type that is supposed to be interoperable");
    d_lw.writeCommentLine("with the corresponding C entry point vector.");
    
    d_lw.println("type, bind(c) :: " + name);
    d_lw.tab();
    
    for(Iterator ms = ext.getRenamedMethods().iterator(); ms.hasNext();) {
      Method renamedMethod = (Method) ms.next();
      if (ext.lookupMethodByLongName(renamedMethod.getLongMethodName(), false) == null) {
        methodsAlreadySeen.add(renamedMethod.getLongMethodName());
      }
    }

    //generate builtin entries
    final int numBuiltins = (ext.isInterface()
                             ? IOR.INTERFACE_BUILT_IN_METHODS
                             : IOR.CLASS_BUILT_IN_METHODS);
    if(doStatic) {
      for(int i = 0; i < numBuiltins; ++i) {
        if(IOR.hasStaticBuiltin(i) &&
           !(IOR.isRMIRelated(i) && d_context.getConfig().getSkipRMI())) {
          Method b = IOR.getBuiltinMethod(i, id, d_context, doStatic);
          generateEPVMethod(b, methodsAlreadySeen); 
        }
      }
    } else {
      for(int i = 0; i < numBuiltins; ++i) {
        if (!(IOR.isRMIRelated(i) && d_context.getConfig().getSkipRMI())) {
          Method b = IOR.getBuiltinMethod(i, id, d_context, doStatic);
          generateEPVMethod(b, methodsAlreadySeen); 
        }
      }
    }

    //generate user-defined entries
    generateEPVEntries(ext, doStatic, methodsAlreadySeen, extAlreadySeen, d_context);
 
    d_lw.backTab();
    d_lw.println("end type");

    
    
    d_lw.println();
  }

  private void generateHooksEPVType(Extendable ext,
                                    boolean doStatic,
                                    boolean do_pre,
                                    Context d_context)
    throws CodeGenerationException
  {
    SymbolID id = ext.getSymbolID();
    String name = do_pre ?
      Fortran.getBindCPreEPVTypeName(id, doStatic) :
      Fortran.getBindCPostEPVTypeName(id, doStatic);
    
    String comment = do_pre ? "pre-hook" : "post-hook";
    if(doStatic) comment = "static " + comment;
    d_lw.writeCommentLine("This is a Fortran type that is supposed to be interoperable");
    d_lw.writeCommentLine("with the corresponding C " + comment + " entry point vector.");
    
    d_lw.println("type, bind(c) :: " + name);
    d_lw.tab();

    Iterator M = doStatic ?
      ext.getStaticMethods(false).iterator() :
      ext.getNonstaticMethods(false).iterator();
    boolean is_empty = true;
    while(M.hasNext()) {
      is_empty=false;
      Method method = (Method) M.next();
      Method hook   = do_pre ? method.spawnPreHook() : method.spawnPostHook();
      d_lw.println("type(c_funptr) :: " +
                   IOR.getVectorEntry(hook.getLongMethodName()));
    }
    if(is_empty) d_lw.println("integer(c_int) :: avoid_empty_type");
    d_lw.backTab();
    d_lw.println("end type");
    d_lw.println();
  }
  
  private void writeModule(Context context)
    throws CodeGenerationException
  {
    SymbolID id = null;
    if(d_sym == null)
      id = new SymbolID("sidl.array", new Version());
    else
      id = d_sym.getSymbolID();

    if(Fortran.hasBindC(context) && d_sym instanceof Enumeration) {
      //For F03, pretty much everything is merged in one file. However,
      //we generate separte modules to maintain source code compatibility.
      String module = Fortran.getModule(id, context);
      d_lw.println("module " + module);      
      d_lw.tab();
      d_lw.generateUse("sidl", new TreeMap());
      d_lw.println("use, intrinsic :: iso_c_binding");
      d_lw.println();
      // d_lw.println("enum, bind(c)");
      // d_lw.tab();
      Enumeration enm = (Enumeration) d_sym;
      Iterator I = enm.getEnumerators().iterator();
      while(I.hasNext()) {
        String sym = (String)I.next();
        Comment cmt = enm.getEnumeratorComment(sym);
        if(cmt != null) d_lw.writeComment(cmt, true);
        // d_lw.println("enumerator :: " + sym + " = " +
        // enm.getEnumeratorValue(sym));
        d_lw.print(Fortran.getArgumentString(new Type(id, context), context,false));
        d_lw.print(", parameter :: ");
        d_lw.print(sym);
        d_lw.println(" = " + enm.getEnumeratorValue(sym));
        
        if(cmt != null) d_lw.println();
      }
      // d_lw.backTab();
      // d_lw.println("end enum");
      d_lw.println();
      d_lw.backTab();
      d_lw.println("end module " + module);
      d_lw.println();
    }
    
    d_lw.println("module " + Fortran.getTypeModule(id, context));
    d_lw.tab();
    d_lw.generateUse("sidl", new TreeMap());
    if(Fortran.hasBindC(context))
      d_lw.println("use, intrinsic :: iso_c_binding");

    if(Fortran.hasBindC(context) && d_sym instanceof Extendable) {
      Extendable ext = (Extendable) d_sym;
      d_lw.println();
      
      if(ext.hasStaticMethod(true)) generateEPVType(ext, true, context);
      generateEPVType(ext, false, context);

      if(IOR.generateHookEPVs(ext, context)) {
        generateHooksEPVType(ext, false, false, context);
        generateHooksEPVType(ext, false, true,  context);

        if(ext.hasStaticMethod(true)) {
          generateHooksEPVType(ext, true, false, context);
          generateHooksEPVType(ext, true, true,  context);
        }
      }
    }
    
    //There are only array types for generic arrays! (d_sym == null)
    if (d_sym != null && d_sym instanceof Extendable) {
      declareType((Extendable) d_sym, id, context);
      d_lw.println();
    }

    //Write generic arrays, or write normal ones.
    if (d_sym == null) {
      writeGenericArrayType(id, context);
    } else {
      String implDataType = null;
      if (context.getSymbolTable().lookupSymbol(id).getSymbolType() == Type.ENUM) {
        implDataType = Fortran.getReturnString(new Type(Type.LONG), context, false);
      }
      writeArrayType(d_lw, id, implDataType, context);
    }
    
    if(Fortran.hasBindC(context) && d_sym instanceof Extendable) {
      d_lw.println();
      d_lw.println("private :: cache_epv_s");
      d_lw.println("interface cache_epv");
      d_lw.tab();
      d_lw.println("module procedure cache_epv_s");
      d_lw.backTab();
      d_lw.println("end interface cache_epv");
      d_lw.println();
      d_lw.backTab();
      d_lw.println("contains");
      d_lw.println();
      d_lw.tab();
      d_lw.tab();
      generateCacheEPV((Extendable) d_sym);
      d_lw.backTab();
    }
    d_lw.backTab();
    d_lw.println("end module " + Fortran.getTypeModule(id, context));
    
  }

  public void generateCode(Context context)
    throws CodeGenerationException
  {
    SymbolID id = null;
    if(d_sym == null)
      id = new SymbolID("sidl.array", new Version());
    else
      id = d_sym.getSymbolID();
    
    if(d_sym != null) 
      d_lw.writeBanner(d_sym, Fortran.getTypeFile(id, context), false,
                       CodeConstants.C_FORTRAN_TYPE_MODULE_PREFIX +
                       id.getFullName());

    try {
      d_lw.pushLineBreak(false);
      d_lw.generateInclude( Fortran.getStubNameFile(id) );
    }
    finally {
      d_lw.popLineBreak();
    }
    d_lw.println();
    describeFile(context);
    writeModule(context);
  }

  /**
   * Generate the FORTRAN 90 type module for a sidl extendable (i.e., class
   * or interface).
   *
   * @exception gov.llnl.babel.backend.CodeGenerationException
   * a catch all exception to indicate problems in the code generation
   * phase.
   */
  public static void generateCode(Symbol sym,
                                  LanguageWriterForFortran writer,
                                  Context context)
    throws CodeGenerationException
  {
    TypeModule tm = new TypeModule(writer, sym);
    tm.generateCode(context);
  }
}
