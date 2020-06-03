//
// File:        ImplSource.java
// Package:     gov.llnl.babel.backend.fortran
// Revision:    @(#) $Revision: 7445 $
// Description: Generate a skeleton implementation in Fortran
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


package gov.llnl.babel.backend.fortran;

import gov.llnl.babel.Context;
import gov.llnl.babel.BabelConfiguration;
import gov.llnl.babel.backend.CodeConstants;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.CodeSplicer;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.Utilities;
import gov.llnl.babel.backend.fortran.Fortran;
import gov.llnl.babel.backend.fortran.StubSource;
import gov.llnl.babel.backend.mangler.FortranMangler;
import gov.llnl.babel.backend.mangler.NameMangler;
import gov.llnl.babel.backend.mangler.NonMangler;
import gov.llnl.babel.backend.writers.LanguageWriterForFortran;
import gov.llnl.babel.symbols.Argument;
import gov.llnl.babel.symbols.Class;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.SymbolTable;
import gov.llnl.babel.symbols.Type;
import gov.llnl.babel.symbols.Version;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * This class provides the ability to write a FORTRAN file with a
 * subroutine template for each method the end user has to implement for
 * a sidl class. The class will retain the previous user provided
 * implementation when overwriting a implementation file.
 */
public class ImplSource 
{
  private static final String s_use = ".use";

  private static final String[] s_intent_spec = {
    ", intent(in)",
    ", intent(inout)",
    ", intent(out)"
  };
  
  /**
   * The code splicer stores the source code from the previous
   * implementation when overwriting an impl file.
   */
  private CodeSplicer          d_splicer;

  private Context              d_context;

  /**
   * Keep track of the local splicer declarations that are written
   * for rarrays since we don't want multiple splicer blocks.
   */
  //private static HashSet spliceWritten = null;

  /**
   * This is the output device.
   */
  private LanguageWriterForFortran d_lw;

  /**
   * Hold a FORTRAN name mangler.
   */
  private NameMangler d_mang;

  /**
   * Generate an instance to generate a FORTRAN implementation template.
   * 
   * @param writer    the output device to which the FORTRAN implementation
   *                  should be written.
   * @param splicer   this stores the previous implementation when one
   *                  exists.
   * @exception java.security.NoSuchAlgorithmException
   *                  thrown when the JVM has not SHA hash algorithm.
   */
  public ImplSource(LanguageWriterForFortran writer,
                    CodeSplicer              splicer,
                    Context                  context)
    throws java.security.NoSuchAlgorithmException
  {
    d_lw      = writer;
    d_splicer = splicer;
    d_context = context;
    if (Fortran.needsAbbrev(context)) {
      d_mang = new FortranMangler(AbbrevHeader.getMaxName(context),
                                  AbbrevHeader.getMaxUnmangled(context));
    }
    else {
      d_mang  =  new NonMangler();
    }
  }

  public static void useStatementsForSupers(Method m, SymbolID id, 
                                            LanguageWriterForFortran writer,
                                            CodeSplicer              splicer,
                                            Context                  context)
  throws CodeGenerationException {
    try{
      writer.tab();
      ImplSource source = new ImplSource(writer, splicer, context);
      source.useStatementsForSupers(m,id);
      writer.backTab();
    } catch (java.security.NoSuchAlgorithmException ex) {
      throw new CodeGenerationException("NoSuchAlgorithmException caught in Impl Source");
    }
  }
  
  public void useStatementsForSupers(Method m, SymbolID id) 
  throws CodeGenerationException {
    addUseForReferences(m, id);
  }

  /**
   * Return the argument declaration, tailored to the specific flavor
   * of FORTRAN.
   *
   * @param a    the argument
   * @param indexExprPrefix an optional prefix for the variables used by the
   *                        index expression. This is useful for struct
   *                        members in F90.
   *
   * @return     the argument declaration string
   */
  public static String getArgumentDeclaration(Argument a,
                                              String indexExprPrefix,
                                              Context context)
    throws CodeGenerationException
  {
    final Type argType = a.getType();
    StringBuffer result = new StringBuffer();

    if (Fortran.hasBindC(context) && a.getFormalName().equals(Fortran.s_self))
      result.append("type(" + Fortran.getImplTypeName(argType.getSymbolID()) + ")");
    else
      result.append(Fortran.getArgumentString(argType, context, false));

    if (Fortran.hasBindC(context) && !a.getFormalName().equals(Fortran.s_self))
      result.append(s_intent_spec[a.getMode()]);
    if (Fortran.isFortran90(context) && !Fortran.hasBindC(context)) {
      if (argType.isRarray()) {
        // see also Comment in fskelSource.java:writeStructCopies()
        result.append(", target, dimension");
        result.append(Fortran.arrayIndices(argType.getArrayIndexExprs(), indexExprPrefix));
      }
      result.append(" :: ");
      result.append(a.getFormalName());
    } else {
      result.append(" :: ");
      result.append(a.getFormalName());
      if(argType.isRarray()) {
        result.append(Fortran.arrayIndices(argType.getArrayIndexExprs(), indexExprPrefix));
      }
    }
    return result.toString();
  }

  private Set extendedReferences(Method m, SymbolID id)
  {
    return extendedReferences(m, id, d_context);
  }
  
  /**
   * Generate the list of referenced symbols (including implied ones like
   * sidl.SIDLException when the method has exceptions).
   */
  private static Set extendedReferences(Method m, SymbolID id, Context d_context)
  {
    Set refs = m.getSymbolReferences();
    HashSet set = new HashSet(refs.size() + 2);
    set.addAll(refs);
    if (!m.getThrows().isEmpty()) {
      Symbol baseInterface = d_context.getSymbolTable().lookupSymbol
        (BabelConfiguration.getBaseInterface());
      set.add(baseInterface.getSymbolID());
    }
    set.add(id);
    return set;
  }

  private static void checkType(Type t, Set result)
    throws CodeGenerationException
  {
    if (Type.ARRAY == t.getDetailedType()) {
      final Type arrayType = t.getArrayType();
      if (null != arrayType) {
        switch (arrayType.getDetailedType()) {
        case Type.ENUM:
        case Type.CLASS:
        case Type.INTERFACE:
        case Type.SYMBOL:
          result.add(arrayType.getSymbolID());
          break;
        case Type.ARRAY:
        case Type.PACKAGE:
          throw new CodeGenerationException
            ("Bad argument type in method");
        case Type.VOID:
          // no action
          break;
        default:
          result.add(new SymbolID("sidl." + arrayType.getTypeString(),
                                  new Version()));
          break;
        }
      }
      else {
        result.add(new SymbolID("sidl.array", new Version()));
      }
    }
  }

  public static Set arrayReferences(Method m)
    throws CodeGenerationException
  {
    Collection args = m.getArgumentList();
    HashSet result = new HashSet(args.size());
    Iterator i = args.iterator();
    while (i.hasNext()) {
      final Type t = ((Argument)i.next()).getType();
      checkType(t, result);
    }
    checkType(m.getReturnType(), result);
    return result;
  }

  public static Set arrayReferences(Class cls, Context d_context)
    throws CodeGenerationException
  {
    HashSet arrays = new HashSet();
    Collection methods = extendMethods(cls, d_context);
    for(Iterator I = methods.iterator(); I.hasNext(); ) {
      Method m = (Method) I.next();
      arrays.addAll(arrayReferences(m));
    }
    return arrays;
  }
  
  public static String uniqueName(String name, int num)
  {
    final String numStr = Integer.toString(num);
    if (name.length() + numStr.length() + 1 <= 31) {
      return name + '_' + numStr;
    }
    return name.substring(0,30 - numStr.length()) + '_' + numStr;
  }

  public static F90Method convertMethod(Method m, SymbolID id, 
                                         Context context)
    throws CodeGenerationException
  {
    List extendedArgs = StubSource.extendArgs(id, m, context, true);
    return new F90Method(m.getShortMethodName(), extendedArgs);
  }

  public Map findCollisions(Map methodsSeen, Symbol sym)
    throws CodeGenerationException
  {
    return findCollisions(methodsSeen, sym, d_context);
  }
  
  public static Map findCollisions(Map methodsSeen,
                                   Symbol sym,
                                   Context d_context)
    throws CodeGenerationException
  {
    HashMap result = new HashMap();
    if (sym instanceof Extendable) {
      final Extendable ext = (Extendable)sym;
      final SymbolID id = ext.getSymbolID();
      Collection methods = ext.getMethods(true);
      Iterator i = methods.iterator();
      while (i.hasNext()) {
        F90Method f90m = convertMethod((Method)i.next(), id, d_context);
        if (methodsSeen.containsKey(f90m)) {
          final Integer value = (Integer)methodsSeen.get(f90m);
          final int newValue = value.intValue() + 1;
          methodsSeen.put(f90m, new Integer(newValue));
          result.put(f90m.getName(), 
                     uniqueName(f90m.getName(), newValue));
        }
        else {
          methodsSeen.put(f90m, new Integer(1));
        }
      }
    }
    return result;
  }

  private static class RefComp implements Comparator
  {
    private Context              d_context;
    
    public RefComp(Context c) {
      d_context = c;
    }
    
    public int compare(Object o1, Object o2)
    {
      final SymbolID sid1 = (SymbolID)o1;
      final SymbolID sid2 = (SymbolID)o2;
      final SymbolTable table = d_context.getSymbolTable();
      final Symbol sym1 = table.lookupSymbol(sid1);
      final Symbol sym2 = table.lookupSymbol(sid2);
      final int depth1 = (sym1 == null) ? -1 : sym1.getDepth();
      final int depth2 = (sym2 == null) ? -1 : sym2.getDepth();
      final int depthCompare = depth1 - depth2;
      if (depthCompare == 0) {
        return sid1.compareTo(sid2);
      }
      return depthCompare;
    }
  }

  private static List sortReferences(Collection refs, Context d_context)
  {
    Object[] objs = refs.toArray();
    Arrays.sort(objs, new RefComp(d_context));
    ArrayList result = new ArrayList(objs.length);
    for(int i = 0; i < objs.length; ++i) {
      result.add(objs[i]);
    }
    return result;
  }

  public static void addUseForReferences(Extendable ext,
                                         Collection methods,
                                         LanguageWriterForFortran d_lw,
                                         Context d_context,
                                         boolean do_types)
    throws CodeGenerationException
  {
    SymbolID id = ext.getSymbolID();
    
    HashSet symbols = new HashSet();
    HashSet arrays = new HashSet();
    for(Iterator I = methods.iterator(); I.hasNext(); ) {
      Method m = (Method) I.next();
      symbols.addAll(extendedReferences(m, id, d_context));
      arrays.addAll(arrayReferences(m));
    }
    addUseForReferences(symbols, arrays, id, d_lw, d_context, do_types);
  }
  
  public void addUseForReferences(Method m, SymbolID id)
    throws CodeGenerationException
  {
    addUseForReferences(m, id, d_lw, d_context, false);
  }
  
  public static void addUseForReferences(Method m,
                                         SymbolID id,
                                         LanguageWriterForFortran d_lw,
                                         Context d_context,
                                         boolean do_types)
    throws CodeGenerationException
  {
    addUseForReferences(extendedReferences(m, id, d_context),
                        do_types ? null : arrayReferences(m),
                        id, d_lw, d_context, do_types);
  }

  public static void addUseForReferences(Collection symbol_refs,
                                         Collection array_refs,
                                         SymbolID id,
                                         LanguageWriterForFortran d_lw,
                                         Context d_context,
                                         boolean do_types)
    throws CodeGenerationException
  {
    List refs = sortReferences(symbol_refs, d_context);
    HashSet appeared = new HashSet(refs.size());
    HashMap methodsSeen = new HashMap();
    if(!do_types) d_lw.generateUse("sidl", methodsSeen);
    Iterator i = refs.iterator();
    while (i.hasNext()) {
      final SymbolID refid = (SymbolID)i.next();
      if (!appeared.contains(refid)) {
        final Map renameMap = 
          findCollisions(methodsSeen,
                         Utilities.lookupSymbol(d_context, refid),
                         d_context);

        boolean useTypeModule = do_types;
        // FIXME: For Structs, we don't generate a type module
        // For now, we are using the module instead
        if (d_context.getSymbolTable().lookupSymbol(refid).getSymbolType() 
            == Type.STRUCT)
          useTypeModule = false;
        String use = useTypeModule ?
          Fortran.getTypeModule(refid, d_context) :
          Fortran.getModule(refid, d_context);
        d_lw.generateUse(use, renameMap);
        appeared.add(refid);
      }
    }
    // do array references now
    if(!do_types) {
      refs = sortReferences(array_refs, d_context);
      appeared.clear();
      i = refs.iterator();
      TreeMap emptyMap = new TreeMap();
      while (i.hasNext()) {
        final SymbolID refid = (SymbolID)i.next();
        if (!appeared.contains(refid)) {
          d_lw.generateUse(Fortran.getArrayModule(refid, d_context),
                           emptyMap);
          appeared.add(refid);
        }
      }
    }
  }
  
  private String[] initialContent(SymbolID id,
                                  String method)
  {
    if (IOR.getBuiltinName(IOR.CONSTRUCTOR).equals(method)) {
      final String fullName = id.getFullName().replace('.', '_');
      String defaultCtor[] = {
        "! boilerplate contructor",
        "type(" + fullName + "_wrap" + ") :: dp",
        "allocate(dp%d_private_data)",
        "! initialize contents of dp%d_private_data here",
        "call " + fullName + "__set_data_m(self, dp)"
      };
      return defaultCtor;
    }
    else if (IOR.getBuiltinName(IOR.DESTRUCTOR).equals(method)) {
      final String fullName = id.getFullName().replace('.', '_');
      String defaultDtor[] = {
        "! boilerplate contructor",
        "type(" + fullName + "_wrap" + ") :: dp",
        "call " + fullName + "__get_data_m(self, dp)",
        "! release resources in dp%d_private_data here",
        "deallocate(dp%d_private_data)",
      };
      return defaultDtor;
    }
    return null;
  }

  /**
   * Write the FORTRAN server side implementation subroutine 
   * that corresponds to a sidl method. This writes the method
   * signature, declares the type of the argument, and then includes
   * a {@link gov.llnl.babel.backend.CodeSplicer CodeSplicer} section and
   * its contents.
   * 
   * @param m     the method whose implementation template is to be
   *              written.
   * @param id    the name of the class that owns this method.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   */
  private void extendAndGenerate(Method m, SymbolID id)
    throws CodeGenerationException
  {
    //spliceWritten = new HashSet();
    boolean is_function  =  Fortran.isFortranFunction(m, d_context);
    boolean is_bindc     =  Fortran.hasBindC(d_context);
    boolean is_f90       =  Fortran.isFortran90(d_context);
    List extendedArgs    =  StubSource.extendArgs(id, m, d_context, true, is_function);
    String splicerTag    =  id.getFullName() + '.' + m.getLongMethodName();
    String splicerTagUse =  splicerTag + s_use;
    String methodImplName = Fortran.getMethodImplName(id, m, d_mang, d_context);
    
    d_lw.println();
    d_lw.println();

    d_lw.writeComment(m, false);

    //function declaration
    if(is_f90) d_lw.print("recursive ");
    d_lw.print(is_function ? "function " : "subroutine ");
    d_lw.print(methodImplName + "(");
    for(Iterator I = extendedArgs.iterator(); I.hasNext(); ) {
      d_lw.print(((Argument)I.next()).getFormalName());
      if (I.hasNext()) d_lw.print(", ");
    }
    d_lw.print(")");

    if(is_function)
      d_lw.println(" result(" + Fortran.s_return + ")");
    else 
      d_lw.println();

    //use statements (F90 only; they are global for Bind(C))
    if(is_f90 || is_bindc) {
      d_lw.tab();
      if(is_f90) {
        addUseForReferences(m, id);
        d_lw.generateUse(id.getFullName().replace('.','_') + "_impl", new TreeMap());
      }
      spliceInitialUseBlock(splicerTagUse);
    }

    //argument declarations
    d_lw.println("implicit none");
    Iterator i = Fortran.reorderArguments(extendedArgs).iterator();
    while (i.hasNext()) {
      Argument a = (Argument)i.next();
      String decl = getArgumentDeclaration(a, "", d_context); 
      if(is_f90) {
        //Retarded special case to make ctor2 work
        if(m.equals(IOR.getBuiltinMethod(IOR.CONSTRUCTOR2, id, d_context)) &&
           a.getType().getType() == Type.OPAQUE && 
           a.getFormalName().equals("private_data")) {
          d_lw.println("type(" + id.getFullName().replace('.', '_') + 
                       "_wrap" + ") :: private_data");
        } else {
          d_lw.println(decl);
        }
        d_lw.writeCommentLine(a.getModeString());
      } else {
        d_lw.writeCommentLine(a.getArgumentString());
        d_lw.println(decl);
      }
    }

    //return type for functions
    if(is_function) {
      d_lw.writeCommentLine(" function result");
      d_lw.println(Fortran.getReturnString(m.getReturnType(), d_context, false) + 
                   " :: " + Fortran.s_return);
    }
    
    d_lw.println();
    if(is_f90) d_lw.backTab();

    //content splicer block
    spliceInitialContent(m, splicerTag);

    //procedure end
    if(is_bindc) d_lw.backTab();
    if(is_function)
      d_lw.println("end function " + methodImplName);
    else if(is_f90 || is_bindc)
      d_lw.println("end subroutine " + methodImplName);
    else
      d_lw.println("end");
  }

  private void spliceInitialUseBlock(String tag)
  {
    final String comments[] = {
      "Add use statements here"
    };
    String [] code;
    final String ccaCode[] = {
      "  ! DO-DELETE-WHEN-IMPLEMENTING exception.begin(" + tag + ")",
      "  use sidl_NotImplementedException",
      "  ! DO-DELETE-WHEN-IMPLEMENTING exception.end(" + tag + ")"
    };
    final String defaultCode[] = null;
    code = d_context.getConfig().getCCAMode() ? ccaCode : defaultCode;
    d_splicer.splice(tag, d_lw, null,  comments, code);
  }

  private void spliceIncludesBlock()
  {
    final String comments[] = {
      "Add additional include statements here"
    };
    String [] code;
    final String ccaCode[] = {
      "  ! DO-DELETE-WHEN-IMPLEMENTING exception.begin(module.include)",
      "  use sidl_NotImplementedException",
      "  ! DO-DELETE-WHEN-IMPLEMENTING exception.end(module.include)"
    };
    final String defaultCode[] = null;
    code = d_context.getConfig().getCCAMode() ? ccaCode : defaultCode;
    d_splicer.splice("module.include", d_lw, null,  comments, code);
  }
  
  private void spliceInitialContent(Method m, String splicerTab) {
    String sName   = m.getShortMethodName();
    String alt_msg          = sName + " method";
    String defaultComment[] = {
      "This method has not been implemented" 
    };
    
    if (  (!sName.endsWith("_pre")) && (!sName.endsWith("_post")) ) {
      if (Fortran.isFortran90(d_context) || Fortran.hasBindC(d_context)) {
        String defaultCode[] = {};
        String notImplExWithCCA[] = {
          "  ! DO-DELETE-WHEN-IMPLEMENTING exception.begin(" + splicerTab + ")",
          "  type(sidl_BaseInterface_t) :: throwaway",
          "  type(sidl_NotImplementedException_t) :: notImpl",
          "  call new(notImpl, exception)",
          "  call setNote(notImpl, \'Not Implemented\', exception)",
          "  call cast(notImpl, exception,throwaway)",
          "  call deleteRef(notImpl,throwaway)",
          "  return",
          "  ! DO-DELETE-WHEN-IMPLEMENTING exception.end(" + splicerTab + ")",
        };
        if (  d_context.getConfig().getCCAMode() == true ) { 
          defaultCode = notImplExWithCCA;
        }
        d_splicer.splice(splicerTab, d_lw, alt_msg, defaultComment, 
                         defaultCode);
      } else {
        String defaultCode[] = {};
        String notImplExWithCCA[] = {
          "C       DO-DELETE-WHEN-IMPLEMENTING exception.begin(" + splicerTab + ")",
          "        integer*8 throwaway",
          "        call sidl_NotImplementedException__create_f",
          "     $      (exception, throwaway)",
          "        if (exception .ne. 0) then",
          "           call sidl_NotImplementedException_setNote_f(",
          "     $         exception,", 
          "     $         \'This method has not been implemented\',",
          "     $         throwaway)",
          "        endif",
          "        return",
          "C       DO-DELETE-WHEN-IMPLEMENTING exception.end(" + splicerTab + ")"
        };
        if ( d_context.getConfig().getCCAMode() == true ) { 
            defaultCode = notImplExWithCCA;
        }
        d_splicer.splice(splicerTab, d_lw, alt_msg, defaultComment, 
                         defaultCode);
      } 
    } else {
      d_splicer.splice(splicerTab, d_lw, alt_msg, defaultComment);
    }

  }
  

  /**
   * Add the builtin methods to the list of methods that must be
   * implemented.
   *
   * @param ext the class whose implementation is being written.
   */
  public static Collection extendMethods(Extendable ext, Context d_context)
    throws CodeGenerationException {
    Collection localMethods = ext.getMethods(false);
    final SymbolID id = ext.getSymbolID();
    ArrayList  extendedMethods = new ArrayList(localMethods.size()+2);
    extendedMethods.
      add(IOR.getBuiltinMethod(IOR.CONSTRUCTOR, id, d_context));
    if(!Fortran.hasBindC(d_context)) {
      extendedMethods.
        add(IOR.getBuiltinMethod(IOR.CONSTRUCTOR2, id, d_context));
    }
    extendedMethods.
      add(IOR.getBuiltinMethod(IOR.DESTRUCTOR, id, d_context));
    extendedMethods.
      add(IOR.getBuiltinMethod(IOR.LOAD, id, d_context));
    extendedMethods.addAll(localMethods);
    return extendedMethods;
  }

  private void addMiscCodeSection(String sectionName)
  {
    d_splicer.splice(sectionName, d_lw, "extra code");
  }

  /**
   * Write #include for all the abbreviation files for
   * referenced symbols.
   */
  private void writeIncludes(Class cls)
    throws CodeGenerationException
  {
    Set seen = new HashSet();
    Iterator i = ModuleSource.extendedReferences(cls, d_context).iterator();
    try {
      d_lw.pushLineBreak(false);
      while (i.hasNext()) {
        SymbolID id = (SymbolID)i.next();
        seen.add(id);
        d_lw.generateInclude(Fortran.getStubNameFile(id) );
      }
      if(Fortran.hasBindC(d_context))
        i = arrayReferences(cls, d_context).iterator();
      else
        i = cls.getBasicArrayRefs().iterator();
      while (i.hasNext()) {
        SymbolID id = (SymbolID)i.next();
        if(Fortran.hasBindC(d_context) && seen.contains(id))
          continue;
        d_lw.generateInclude(Fortran.getStubNameFile(id) );
      }
    }
    finally {
      d_lw.popLineBreak();
    }
  }

  private void generateF03Super(Class cls)
    throws CodeGenerationException
  {
    if(cls.hasOverwrittenMethods()) {
      SymbolID id = cls.getSymbolID();
      SymbolID parent_id = cls.getParentClass().getSymbolID();
      String parent_type = Fortran.getTypeName(parent_id);
      String impl_type = Fortran.getImplTypeName(id);
      String fqn_super = Fortran.getSymbolName(id) + "_super";
    
      d_lw.println("type(" + parent_type + ") function super(self)");
      d_lw.tab();
      d_lw.generateUse(Fortran.getTypeModule(parent_id, d_context), new HashMap());
      d_lw.println("type(" + impl_type + "), intent(in) :: self");
      d_lw.println("external " + fqn_super);
      d_lw.println("call " + fqn_super + "(self, super)");
      d_lw.backTab();
      d_lw.println("end function super");
      d_lw.println();
    }
  }

  private void generateF03Wrap(Class cls)
    throws CodeGenerationException
  {
    SymbolID id = cls.getSymbolID();
    String impl_type = Fortran.getImplTypeName(id);
    String stub_type = Fortran.getTypeName(id);
    String fqn_wrap = Fortran.getSymbolName(id) + "_wrap";

    //don't do anything for abstract classes
    if(cls.isAbstract()) return;
    
    d_lw.println("subroutine wrap(obj, self, exception)");
    d_lw.tab();
    d_lw.println("type(" + impl_type + "), target :: obj");
    d_lw.println("type(" + stub_type + ") :: self");
    d_lw.println("type(sidl_BaseInterface_t) :: exception");
    d_lw.println("external " + fqn_wrap);
    d_lw.println("call " + fqn_wrap + "(obj, self, exception)");
    d_lw.backTab();
    d_lw.println("end subroutine wrap");
    d_lw.println();
  }
  
  private void generateF03ExtendedType(Class cls)
    throws CodeGenerationException
  {
    SymbolID id = cls.getSymbolID();
    String name = cls.getFullName();
    String type_name = Fortran.getTypeName(id);
    String impl_type = Fortran.getImplTypeName(id);
    
    d_lw.println("type, extends(" + type_name + ") :: " + impl_type);
    d_lw.tab();
    d_splicer.splice(name + ".private_data", d_lw, "private data members");
    d_lw.backTab();
    d_lw.println("end type " + impl_type);
    d_lw.println();
  }
  
  /**
   * Generate the implementation FORTRAN file for a sidl class.  The
   * implementation file contains all the subroutines that need to be
   * implemented for the class, and when replacing an implementation file, 
   * the previous implementation is retained.
   * 
   * @param cls    the sidl class whose implementation is to be written.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   */
  public void generateCode(Class cls)
    throws CodeGenerationException
  {
    Collection methods = extendMethods(cls, d_context);
    final SymbolID id = cls.getSymbolID();
    String module_name = null;
    boolean is_f03 = Fortran.isFortran03(d_context);

    d_lw.writeBanner(cls, Fortran.getImplFile(id, d_context), true, 
                     CodeConstants.C_DESC_IMPL_PREFIX + id.getFullName());
    d_lw.println();
    d_lw.writeComment(cls, false);
    d_lw.println();

    if (Fortran.needsAbbrev(d_context)) {
      writeIncludes(cls);
      if(is_f03) spliceIncludesBlock();      
    }

    //for Bind(C) bindings, we put everything in a module and declare a
    //derived type that extends the generic client stub
    if(is_f03) {
      try {
        String name = cls.getFullName();
        module_name = d_mang.shortName(name, "_Impl");

        d_lw.println();
        d_lw.println("module " + module_name);
        d_lw.tab();

        addUseForReferences(cls, methods, d_lw, d_context, false);
        spliceInitialUseBlock("module" + s_use);
        d_lw.println();
        
        d_lw.println();
        generateF03ExtendedType(cls);
        
        d_lw.backTab();
        d_lw.println("contains");
        d_lw.println();
        d_lw.tab();
        generateF03Super(cls);
        generateF03Wrap(cls);
      }
      catch (UnsupportedEncodingException uee) {
        throw new CodeGenerationException("UnsupportedEncodingException: " +
                                          uee.getMessage());
      }
    }
    
    addMiscCodeSection("_miscellaneous_code_start");
    d_lw.println();

    Iterator i = methods.iterator();
    while (i.hasNext()) {
      Method m = (Method)i.next();
      if (!m.isAbstract()) {
        if ((IOR.generateHookMethods(cls, d_context)) && (!m.isBuiltIn())) {
          extendAndGenerate(m.spawnPreHook(), id);
          extendAndGenerate(m, id);
          extendAndGenerate(m.spawnPostHook(false, false), id);
        } else {
          extendAndGenerate(m, id);
        }
      }
    }
    d_lw.println();
    d_lw.println();
    addMiscCodeSection("_miscellaneous_code_end");



    /*
     * Finally, output unreferenced edit blocks, if any.
     */
    if (d_splicer.hasUnusedSymbolEdits()) {
      d_lw.println();
      d_lw.beginBlockComment(true);
      d_lw.println(CodeConstants.C_BEGIN_UNREFERENCED_METHODS);
      d_lw.println(CodeConstants.C_UNREFERENCED_COMMENT1);
      d_lw.println(CodeConstants.C_UNREFERENCED_COMMENT2);
      d_lw.println(CodeConstants.C_UNREFERENCED_COMMENT3);
      d_lw.endBlockComment(true);
      d_splicer.outputUnusedSymbolEdits(d_lw); 
      d_lw.writeCommentLine(CodeConstants.C_END_UNREFERENCED_METHODS);
    }

    if(is_f03) {
      d_lw.println();
      d_lw.backTab();
      d_lw.println("end module " + module_name);
    }    
  }

  /**
   * Generate the implementation FORTRAN file for a sidl class.  The
   * implementation file contains all the subroutines that need to be
   * implemented for the class, and when replacing an implementation file, 
   * the previous implementation is retained.
   *
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   * @exception java.security.NoSuchAlgorithmException
   *   A problem with the name mangler.
  */
  public static void generateCode(Class cls,
                                  LanguageWriterForFortran writer,
                                  CodeSplicer splicer,
                                  Context context)
    throws CodeGenerationException, NoSuchAlgorithmException
  {
    ImplSource source = new ImplSource(writer, splicer, context);
    source.generateCode(cls);
  }

  public static boolean isInt (String s) {

    /**
     * If a String can be parsed into an Integer return true.
     * Used to check r-array indices.
     */
    try {
      int i = Integer.parseInt(s.trim());
      return true;
    } catch (NumberFormatException nfe) {
      return false;
    }
  }
}
