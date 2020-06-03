//
// File:        IORHeader.java
// Package:     gov.llnl.babel.backend.ior
// Revision:    @(#) $Id: IORHeader.java 7421 2011-12-16 01:06:06Z adrian $
// Description: generate IOR header to a pretty writer stream
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

package gov.llnl.babel.backend.ior;

import gov.llnl.babel.BabelConfiguration;
import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeConstants;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.fortran.ImplSource;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.ior.IORSource;
import gov.llnl.babel.backend.rmi.RMIIORHeader;
import gov.llnl.babel.backend.SortComparator;
import gov.llnl.babel.backend.Utilities;
import gov.llnl.babel.backend.writers.LanguageWriterForC;
import gov.llnl.babel.symbols.AssertionExpression;
import gov.llnl.babel.symbols.Class;
import gov.llnl.babel.symbols.Comment;
import gov.llnl.babel.symbols.Enumeration;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Interface;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.Package;
import gov.llnl.babel.symbols.Struct;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Class <code>IORHeader</code> generates an IOR header to a language writer
 * output stream.  The constructor takes a language writer stream and method
 * <code>generateCode</code> generates the intermediate object header code
 * for the specified symbol to the output stream.  The language writer output
 * stream is not closed by this object.
 */
public class IORHeader {
   private final static String SIDL_EXCEPTION_INTERFACE = 
     BabelConfiguration.getBaseExceptionInterface();

  private LanguageWriterForC d_writer;
  private boolean d_forwardNote = true;
  private Context d_context;

   /**
    * This is a convenience utility function that writes the symbol
    * header information into the provided language writer output stream.
    * The output stream is not closed on exit.  A code generation
    * exception is thrown if an error is detected.
    */
   public static void generateCode(Symbol symbol, 
                                   LanguageWriterForC writer,
                                   Context context)
      throws CodeGenerationException 
   {
      IORHeader header = new IORHeader(writer, context);
      header.generateCode(symbol);
   }

   /**
    * Create a <code>IORHeader</code> object that will write symbol information
    * to the provided output language writer stream.
    */
   public IORHeader(LanguageWriterForC writer,
                    Context context) {
      d_writer  = writer;
      d_context = context;
   }

   /**
    * Write IOR header information for the provided symbol to the language
    * writer output stream provided in the constructor.  This method does
    * not close the writer output stream and may be called for more than
    * one symbol (although the generated header may not be valid input for
    * the C compiler).  A code generation exception is generated if an error
    * is detected.
    */
   public void generateCode(Symbol symbol) throws CodeGenerationException {
      if (symbol != null) {
         switch (symbol.getSymbolType()) {
         case Symbol.STRUCT:
           generateStruct((Struct) symbol);
           break;
         case Symbol.CLASS:
         case Symbol.INTERFACE:
            generateExtendable((Extendable) symbol);
            break;
         case Symbol.ENUM:
            generateEnumeration((Enumeration) symbol);
            break;
         case Symbol.PACKAGE:
            generatePackage((Package) symbol);
            break;
         }
      }
   }

  private void writeForwardNote()
  {
    if (d_forwardNote) {
      d_writer.println();
      d_writer.beginBlockComment(false);
      d_writer.println("Forward declarations of structs");
      d_writer.endBlockComment(false);
      d_forwardNote = false;
    }
  }

  private void writeStructForward(Struct strct)
  {
    Iterator i = strct.getItems().iterator();
    while (i.hasNext()) {
      final Struct.Item item = (Struct.Item)i.next();
      final Type t = item.getType();
      switch(t.getDetailedType()) {
      case Type.ARRAY:
        writeForwardNote();
        final Type arrayType = t.getArrayType();
        if (null != arrayType) {
          if (arrayType.getType() != Type.SYMBOL){
            d_writer.print(IOR.getArrayNameWithoutAsterix(arrayType.getType()));
            d_writer.println(";");
          }
          else {
            d_writer.println(IOR.getArrayName(arrayType.getSymbolID()) + ";");
          }
        }
        else {
          d_writer.println(IOR.getArrayName(null) + ";");
        }
        break;
      case Type.INTERFACE:
      case Type.CLASS:
        writeForwardNote();
        d_writer.println(IOR.getObjectName(t.getSymbolID()) + ";");
        break;
      }
    }
  }

  private void writeStructIncludes(Struct strct)
  {
    HashSet  set = new HashSet();
    Iterator i   = strct.getItems().iterator();
    while (i.hasNext()) {
      final Struct.Item item = (Struct.Item)i.next();
      final Type        t    = item.getType();
      if (t.getDetailedType() == Type.STRUCT) {
        final String headerFile = IOR.getHeaderFile(t.getSymbolID());
        if (!set.contains(headerFile)) {
          set.add(headerFile);
          d_writer.generateInclude(headerFile, true);
        }
      }
    }
  }

  private String getStructItemType(Type t)
    throws CodeGenerationException
  {
    return (t.getDetailedType() == Type.STRUCT) 
      ? IOR.getStructName(t.getSymbolID())
      : IOR.getReturnString(t, d_context, true, false);
  }

  private int maxStructType(Struct strct)
    throws CodeGenerationException
  {
    int maxwidth = 0;
    Iterator i = strct.getItems().iterator();
    while (i.hasNext()) {
      final Struct.Item item = (Struct.Item)i.next();
      final String      str  = getStructItemType(item.getType());
      final int         len  = str.length();
      if (len > maxwidth) {
        maxwidth = len;
      }
    }
    return maxwidth;
  }

  private void writeStructDefn(Struct strct) 
    throws CodeGenerationException
  {
    final int width = Math.min(maxStructType(strct), 40);
    Iterator  i     = strct.getItems().iterator();
    d_writer.writeComment(strct, false);
    d_writer.println(IOR.getStructName(strct.getSymbolID()) + " {");
    d_writer.tab();
    while (i.hasNext()) {
      final Struct.Item item = (Struct.Item)i.next();
      /* Only Rarrays need special consideration in the IOR file */
      Type tt = item.getType();
      //int ttype = tt.getDetailedType();
      if (tt.isRarray()){
        Type aType = tt.getArrayType();
        if (null != aType) {
            int dims=tt.getArrayDimension();
            if (tt.isNumericArray())
            {

              Iterator indexVar = tt.getArrayIndexExprs().iterator();
              AssertionExpression ae = (AssertionExpression)indexVar.next();
              String checkInt = ae.toString();
              if (ImplSource.isInt(checkInt)){
                StringBuffer cPtrStr = new StringBuffer();
                cPtrStr.append(aType.getTypeName());
                d_writer.printAligned(cPtrStr.toString(),width);
                cPtrStr=new StringBuffer();
                cPtrStr.append(item.getName()+"["+checkInt+"]");

                for(int ij = 1; ij < dims && indexVar.hasNext(); ij++ ) {
                  ae = (AssertionExpression)indexVar.next();
                  checkInt = ae.toString();
                  /* Only need to add dimensions if explicitly defined */
                  if (ImplSource.isInt(checkInt)){
                    cPtrStr.append("["+checkInt+"]");
                  }
                }
                d_writer.println(cPtrStr+";");

              } else {
                StringBuffer cPtrStr = new StringBuffer();
                cPtrStr.append(aType.getTypeName()+"*");
                for (int ij = 1 ; ij < dims ; ij++){
                  cPtrStr.append("*");
                }
                d_writer.printAligned(cPtrStr.toString(),width);
                d_writer.println(item.getName()+";");
              }
            } else {
              System.err.println("ERROR: rarrays must have a numeric Type.");
            }
        }
      } else {
        d_writer.printAligned(getStructItemType(item.getType()), width);
        d_writer.println(" " + item.getName() + ";");
      }
    }
    if (strct.getItems().isEmpty()) {
      d_writer.writeCommentLine("Avoid empty struct");
      d_writer.println("char d_not_empty;");
    }
    d_writer.backTab();
    d_writer.println("};");
    d_writer.println();
  }

   /**
    * Generate the IOR header for a SIDL struct type.  SIDL struct's
    * are mapped to C structs.
    */
  private void generateStruct(Struct strct)
    throws CodeGenerationException
  {
      SymbolID id     = strct.getSymbolID();
      String   header = IOR.getHeaderFile(id);
      
      d_writer.writeBanner(strct, header, CodeConstants.C_IS_NOT_IMPL, 
         CodeConstants.C_DESC_IOR_PREFIX + id.getFullName());
      d_writer.openHeaderGuard(header);
      d_writer.generateInclude("sidlType.h", true);
      if (strct.hasArrayReference()) {
        d_writer.generateInclude("sidlArray.h", true);
      }
      writeStructIncludes(strct);
      writeStructForward(strct);
      d_writer.println();
      writeStructDefn(strct);
      d_writer.closeHeaderGuard();
  }

   /**
    * Generate "standard" macro definitions.
    */
   private void generateDefines(Extendable ext)
    throws CodeGenerationException
   {
       IOR.generateMacroVarNotUsed(d_writer);
   }


   /**
    * Generate the IOR header for a SIDL enumerated type.  Enumerated
    * types are mapped to C enumerated types, which are guaranteed to be
    * cast-compatible with integers and of size int.  See "C: A Reference
    * Manual", by Harbison and Steele.
    */
   private void generateEnumeration(Enumeration enm) {
      SymbolID id     = enm.getSymbolID();
      String   header = IOR.getHeaderFile(id);
      
      d_writer.writeBanner(enm, header, CodeConstants.C_IS_NOT_IMPL, 
         CodeConstants.C_DESC_IOR_PREFIX + id.getFullName());
      d_writer.openHeaderGuard(header);
      d_writer.generateInclude("sidlType.h", true);
      d_writer.openCxxExtern();
      d_writer.writeComment(enm, false);

      d_writer.println();
      d_writer.writeCommentLine("Opaque forward declaration of array struct");
      d_writer.println(IOR.getArrayName(id) + ";");
      d_writer.println();

      /*
       * Write out the enumeration symbol and increase the tab level.
       */
      d_writer.print(IOR.getEnumName(id));
      d_writer.println(" {");
      d_writer.tab();

      /*
       * Output each of the enumerators (with fill space) along with its
       * assigned value.  For pretty output, find the maximum enumerator
       * length and then space over that much to align the equals signs.
       */
      final int maxlength = Utilities.getWidth(enm.getEnumerators()) +
        IOR.getEnumValueSymbol(id, "").length();
      for (Iterator e = enm.getIterator(); e.hasNext(); ) {
         String  name = (String) e.next();
         Comment cmt  = enm.getEnumeratorComment(name);
         d_writer.writeComment(cmt, true);
         d_writer.printAligned(IOR.getEnumValueSymbol(id, name), maxlength);
         d_writer.print(" = ");
         d_writer.print(String.valueOf(enm.getEnumeratorValue(name)));
         if (e.hasNext()) {
            d_writer.print(",");
         }
         d_writer.println();
         if (cmt != null) {
           d_writer.println();
         }
      }

      /*
       * Close the enumeration statement and write out the array structure
       * for the enumerated type.
       */
      d_writer.backTab();
      d_writer.println("};");
      d_writer.println();

      /*
       * Close the C++ extern block and include guard.
       */
      d_writer.closeCxxExtern();
      d_writer.closeHeaderGuard();
   }

   /**
    * Generate an IOR header for a SIDL class or interface description.
    * The header file begins with the standard banner and internal include
    * guard.  This is followed by include statements for external header
    * files, exported symbols defined by the source implementation, and
    * forward declarations.  The entry point vector data structures are
    * defined next, followed by the interface or class object and then
    * the array declaration.  The header concludes with close statements
    * for the header guards.
    */
   private void generateExtendable(Extendable ext)
      throws CodeGenerationException 
   {
      /*
       * Generate the file banner and open the header include guard.
       */
      SymbolID id     = ext.getSymbolID();
      String   header = IOR.getHeaderFile(id);
      
      d_writer.writeBanner(ext, header, CodeConstants.C_IS_NOT_IMPL,
         CodeConstants.C_DESC_IOR_PREFIX + id.getFullName());
         
      d_writer.openHeaderGuard(header);

      /*
       * Include external header files and open the C++ extern block.
       */
      Set defined = generateIncludes(ext);
      generateDefines(ext);
      d_writer.openCxxExtern();

      /*
       * Declare exported symbols and forward declarations.
       */
      generateExportedSymbols(ext);
      defined.add(id);
      generateForwardDeclarations(ext, defined);

      /*
       * Generate invariant clause data, method indices, and method 
       * precondition and postcondition clause data, if necessary.
       */
      if (!ext.isInterface()) {
        generateInvClauseData(ext);
        generateMethodDescData(ext);
      }

      /*
       * Define a preprocessor symbol if fast-calls are enabled for this symbol
       */
      if(d_context.getConfig().getFastCall()) {
        d_writer.writeCommentLine("fast-calls are enabled for this symbol");
        d_writer.printlnUnformatted("#define " + IOR.getNativeEPVGuard(ext));
        d_writer.println();
      }
      
      /*
       * Generate entry point vector(s).
       */
      if (ext.hasStaticMethod(true)) {
         generateEPV(ext, true);
      }
      generateEPV(ext, false);

      if (IOR.generateHookEPVs(ext, d_context)) {
        generatePreHooksEPV(ext, false);
        generatePostHooksEPV(ext, false);
        if (ext.hasStaticMethod(true)) {
          generatePreHooksEPV(ext, true);
          generatePostHooksEPV(ext, true);
        }
      }

      /*
       * Generate the class or interface object itself
       */
      if (ext.isInterface()) {
         generateInterfaceObject((Interface) ext);
      } else {
         IOR.generateControlNStats(d_writer, ext, d_context);
         generateClassObject((Class) ext);
         generateExternalStruct(ext);
         IORSource.generateExternalSignature(d_writer, ext, ";");
         d_writer.println();
      }
      
      /*
       * Generate the external method declarations for this Extendable.
       */
      generateExportedMethods(ext);

      if (!d_context.getConfig().getSkipRMI()) {
        /*
         * For interfaces: Generate the special anonymous class for RMI stuff.
         *
         * For both: forward declare skel RMI Accessor Function
         */
        RMIIORHeader.generateCode(ext, d_writer, d_context);
      }
      
      /*
       * Conclude the header file by closing the C++ extern block and
       * header include guard.
       */

      d_writer.closeCxxExtern();
      d_writer.closeHeaderGuard();
      
   }

   /**
    * Generate the IOR header for a SIDL package description.  The package
    * header file consists of the standard header information along with
    * include statements for all package symbols.
    */
   private void generatePackage(Package p) {
      SymbolID id     = p.getSymbolID();
      String   header = IOR.getHeaderFile(id);
      
      d_writer.writeBanner(p, header, CodeConstants.C_IS_NOT_IMPL,
                           CodeConstants.C_DESC_IOR_PREFIX + id.getFullName());
      d_writer.openHeaderGuard(header);
      d_writer.writeComment(p, false);

      /*
       * Write out the IOR include files for each of the symbols within
       * the package.
       */
      List entries = Utilities.sort(p.getSymbols().keySet());
      for (Iterator i = entries.iterator(); i.hasNext(); ) {
         String include = IOR.getHeaderFile((SymbolID) i.next());
         d_writer.generateInclude(include, true);
      }
      d_writer.println();
      
      d_writer.closeHeaderGuard();
   }

  private void generateExternalStruct(Symbol sym) {
    if (sym instanceof Class) {
      Class          cls        = (Class)sym;
      final SymbolID id         = sym.getSymbolID();
      final String   symbolType = IOR.getSymbolType(sym);
      d_writer.println(IOR.getExternalName(id) + " {");
      d_writer.tab();
      if (!cls.isAbstract()) {
        d_writer.println(symbolType);
        d_writer.print("(*createObject)(" + "void* ddata, ");
        d_writer.println(IOR.getExceptionFundamentalType() + "*_ex);");
        d_writer.println();
      }
      if (cls.hasStaticMethod(true)) {
        d_writer.println(IOR.getSEPVName(id) + "*");
        d_writer.println("(*getStaticEPV)(void);");
      }
      if (cls.getParentClass() != null) { 
          Class parent = cls.getParentClass();
          if ( parent != null ) {
              SymbolID pid = parent.getSymbolID();
              d_writer.print(IOR.getEPVName(pid) + "*");
              d_writer.println("(*getSuperEPV)(void);");
          }
      }
      d_writer.println("int d_ior_major_version;");
      d_writer.println("int d_ior_minor_version;");
      d_writer.backTab();
      d_writer.println("};");
      d_writer.println();
    }
  }

   /**
    * Generate the list of include files required to satisfy data
    * dependencies within this header file.  This includes all parents
    * (for classes) as well as any enumerated types.  These dependencies
    * will be placed in a <code>Set</code> which will be sorted and then
    * output.  If any of the symbols do not exist in the symbol table,
    * then throw a code generation exception.
    */
   private Set generateIncludes(Extendable ext) throws CodeGenerationException {
      /*
       * Create the set of include symbol identifiers and add inheritance
       * parents from interfaces and (if a class) the superclass.
       */
      Set includes = new HashSet();

      if (!ext.isInterface()) {
         Class cls = (Class) ext;
         includes.addAll(Utilities.getUniqueInterfaceIDs(cls));

         Class parent = cls.getParentClass();
         if (parent != null) {
            includes.add(parent.getSymbolID());
         }
      } else {
        Class anonCls = ((Interface)ext).generateAnonymousClass();
        Set   ifcs    = Utilities.getUniqueInterfaceIDs(anonCls);
        ifcs.remove(ext.getSymbolID()); //We don't want to include ourself.
        includes.addAll(ifcs);
      }

      /*
       * Get the list of dependencies and extract enumerated types.
       */
      Set dependencies = ext.getSymbolReferences();
      if ((dependencies != null) && (!dependencies.isEmpty())) {
         for (Iterator i = dependencies.iterator(); i.hasNext(); ) {
            SymbolID id     = (SymbolID) i.next();
            Symbol   symbol = Utilities.lookupSymbol(d_context, id);
            if (symbol.getSymbolType() == Symbol.ENUM) {
               includes.add(id);
            }
         }
      }

      /*
       * Sort the entries and output the list of include files.
       */
      d_writer.generateInclude("sidl_header.h", true);
      
      if (!d_context.getConfig().getSkipRMI()) {
        String oName = IOR.getObjectName(Utilities.lookupSymbol
                                         (d_context, 
                                          "sidl.rmi.InstanceHandle").
                                         getSymbolID());
        d_writer.println(oName + ";");
      }

      if (IOR.generateContractChecks(ext, d_context)) {
        d_writer.generateInclude("sidlAsserts.h", true);
      }
      if (!includes.isEmpty()) {
         List entries = Utilities.sort(includes);
         for (Iterator i = entries.iterator(); i.hasNext(); ) {
            String header = IOR.getHeaderFile((SymbolID) i.next());
            d_writer.generateInclude(header, true);
         }
         d_writer.println();
      }

      return includes;
   }

   /**
    * Generate the exported symbols for an extendable object.  These
    * symbols are the name of the object structure and its constructors
    * and initializers.
    */
  private void generateExportedSymbols(Extendable ext) {
    SymbolID id   = ext.getSymbolID();
    String   type = IOR.getObjectName(id);
    String   sepv = IOR.getSEPVName(id);
  
      d_writer.writeComment(ext, false);

      /*
       * Output the structures that will be declared in this header.
       */
      d_writer.println(IOR.getArrayName(id) + ";");
      d_writer.println(type + ";");
      if (ext.hasStaticMethod(true)) {
         d_writer.println(sepv + ";");
      }
      d_writer.println();
   }
  //JIM
  private void generateExportedMethods(Extendable ext) 
    throws CodeGenerationException 
  {
    SymbolID id = ext.getSymbolID();
    String type = IOR.getObjectName(id);
    String sepv = IOR.getSEPVName(id);

    boolean genContractEPVs = IOR.generateContractEPVs(ext, d_context);
    boolean genHookEPVs     = IOR.generateHookEPVs(ext, d_context);

    /*
     * Output the functions that will be defined in the implementation.
     */
      if (!ext.isAbstract()) {
         d_writer.println("extern " + type + "*");
         d_writer.print(IOR.getNewName(id) + "(void* ddata,");
         d_writer.println(IOR.getExceptionFundamentalType() + "* _ex);");
         d_writer.println();
      }
      boolean doStaticTypes = false;
      if (ext.hasStaticMethod(true)) {
         d_writer.println("extern " + sepv + "*");
         d_writer.println(IOR.getStaticsName(id) + "(void);");
         d_writer.println();

         if (genContractEPVs || genHookEPVs) {
           d_writer.println();
           d_writer.println("extern " + sepv + "*");
           d_writer.println(IOR.getLocalStaticsName(id) + "(int type);");
           doStaticTypes = true;
           d_writer.println();
         }
      }
      if (!ext.isInterface()) {
        Class cls = (Class) ext;
        d_writer.println("extern void " + IOR.getInitName(id) + "(");
        d_writer.tab();
        d_writer.print(type + "* self, void* ddata, ");
        d_writer.println(IOR.getExceptionFundamentalType() + "* _ex);");
        d_writer.backTab();
        d_writer.println();
        
        d_writer.println("extern void " + IOR.getGetEPVsName(id)+"(");
        d_writer.tab();
        IOR.declareEPVsAsArgs(d_writer, cls, d_context, true);
        d_writer.println(");");
        d_writer.backTab();
        d_writer.println();
        
        d_writer.println("extern void " + IOR.getFiniName(id) + "(");
        d_writer.tab();
        d_writer.print(type + "* self, ");
        d_writer.println(IOR.getExceptionFundamentalType() + "* _ex);");
        d_writer.backTab();
        d_writer.println();
        d_writer.print("extern void " + IOR.getVersionName(id));
        d_writer.println("(int32_t *" + "major, int32_t *minor);");
        d_writer.println();
      }
      if (doStaticTypes) {
         d_writer.writeComment("Define static structure options.", false);
         d_writer.print("static const int ");
         d_writer.print(IOR.getStaticTypeOption(id, IOR.SET_PUBLIC));
         d_writer.println(" = 0;");
         int i = 1;
         if (genContractEPVs) {
            d_writer.print("static const int ");
            d_writer.print(IOR.getStaticTypeOption(id, IOR.SET_CONTRACTS));
            d_writer.println(" = " + i + ";");
            i++;
         }

         if (genHookEPVs) {
           d_writer.print("static const int ");
           d_writer.print(IOR.getStaticTypeOption(id, IOR.SET_HOOKS));
           d_writer.println(" = " + i + ";");
         }
         d_writer.println();
      }
   }

   /**
    * Generate the necessary forward declarations to satisfy external data
    * dependencies for the methods defined in interfaces and classes.  The
    * data dependencies are generated by extracting type information from
    * the method signatures and removing any symbols that have already been
    * defined.  If any of the symbols do not exist in the symbol table,
    * then throw a code generation exception.
    */
   private void generateForwardDeclarations(Extendable ext, Set defined)
      throws CodeGenerationException 
   {
      /*
       * Generate the set of dependencies by extracting the data
       * dependencies from each of the methods in the methods list.
       */
      Set references = new HashSet();

      for (Iterator i = ext.getMethods(true).iterator(); i.hasNext(); ) {
         Method method = (Method) i.next();
         references.addAll(method.getSymbolReferences());
         if (!method.getThrows().isEmpty()) {
            Symbol symbol = Utilities.lookupSymbol(d_context, 
                                                   SIDL_EXCEPTION_INTERFACE);
            references.add(symbol.getSymbolID());
         }
      }
      if (!d_context.getConfig().getSkipRMI()) {
        references.add(Utilities.
                       lookupSymbol
                       (d_context, "sidl.rmi.Return").getSymbolID());
        references.add(Utilities.
                       lookupSymbol
                       (d_context, "sidl.rmi.Call").getSymbolID());
      }
      
      

      /*
       * Remove all previously defined types and continue if there are
       * any symbols that remain in the dependency set.
       */
      references.removeAll(defined);
      if (!references.isEmpty()) {

         /*
          * Generate the set of referenced symbols that are interfaces,
          * classes or enums.  These will be output as forward declarations.
          */
         Set declare = new HashSet();
         for (Iterator i = references.iterator(); i.hasNext(); ) {
            SymbolID id     = (SymbolID) i.next();
            Symbol   symbol = Utilities.lookupSymbol(d_context, id);
            int type = symbol.getSymbolType();
            if (type != Symbol.PACKAGE) {
               declare.add(id);
            }
         }

         /*
          * Sort the entries and output the list of forward references.
          */
         if (!declare.isEmpty()) {
            d_writer.writeComment(
               "Forward references for external classes and interfaces.",
               false);
            List entries = Utilities.sort(declare);
            for (Iterator i = entries.iterator(); i.hasNext(); ) {
               SymbolID id = (SymbolID) i.next();
               Symbol symbol = Utilities.lookupSymbol(d_context, id);
               int type = symbol.getSymbolType();
               if (type == Symbol.STRUCT) {
                 d_writer.println(IOR.getStructName(id) + ";");
               }
               else {
                 d_writer.println(IOR.getArrayName(id) + ";");
                 if (type != Symbol.ENUM) {
                   d_writer.println(IOR.getObjectName(id) + ";");
                 }
               }
            }
            d_writer.println();
         }
      }
   }

  /**
   * Generate the actual method entry in the definition of an entry
   * point vector.
   * 
   * @param m           the method to be generated
   * @param self        a string with the type of the object
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception. It can be caused by I/O trouble or
   *    violations of the data type invariants.
   */
  private void generateEPVEntry(Method m, String self) 
    throws CodeGenerationException
  {
    String long_name = m.getLongMethodName();
    d_writer.print(IOR.getReturnString(m.getReturnType(), d_context,
                                       true, false));

    d_writer.print(" (*");
    d_writer.print(IOR.getVectorEntry(long_name));
    d_writer.println(")(");
    d_writer.tab();

    /* 
     * Generate the argument list.
     */
    boolean has_throws = !m.getThrows().isEmpty();
    List    args       = m.getArgumentList();
    IOR.generateArguments(d_writer, d_context,
                          self, args, m.isStatic(), has_throws, 
                          m.getReturnType(), true, true, false, false);
    d_writer.println(");");
    d_writer.backTab();
  }

  /**
   * Generate the native method entry in the definition of an entry
   * point vector.
   * 
   * @param m           the method to be generated
   * @param self        a string with the type of the object
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception. It can be caused by I/O trouble or
   *    violations of the data type invariants.
   */
  private void generateNativeEPVEntry(Method m, String self) 
    throws CodeGenerationException
  {
    String long_name = m.getLongMethodName();
    if (!IOR.isBuiltinMethod(long_name)) {
      d_writer.println("sidl_babel_native_epv_t " +
                       IOR.getNativeVectorEntry(long_name) + ";");
    }
  }
  
  /**
   * Generate the Method entry(ies) in the definition of an entry
   * point vector.
   * 
   * @param m           the method to be generated
   * @param self        a string with the type of the object
   * @param alreadySeen set of method names already generated.
   *                    Each element is a <code>String</code>.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception. It can be caused by I/O trouble or
   *    violations of the data type invariants.
   */
  private void generateEPVMethod(Method m, String self, Set alreadySeen)
    throws CodeGenerationException
  {
    String name = m.getLongMethodName();
    if (!alreadySeen.contains(name)) {
      alreadySeen.add(name);
      generateEPVEntry(m, self);
    }
  }

  /**
   * Generate the matove Method entry(ies) in the definition of an entry
   * point vector.
   * 
   * @param m           the method to be generated
   * @param self        a string with the type of the object
   * @param alreadySeen set of method names already generated.
   *                    Each element is a <code>String</code>.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception. It can be caused by I/O trouble or
   *    violations of the data type invariants.
   */
  private void generateNativeEPVMethod(Method m, String self, Set alreadySeen)
    throws CodeGenerationException
  {
    String name = m.getLongMethodName();
    if (!alreadySeen.contains(name)) {
      alreadySeen.add(name);
      generateNativeEPVEntry(m, self);
    }
  }
  
  /**
   * Generate the EPV entries for the methods locally introduced in an
   * extendable (i.e. interface or class). This will create an EPV
   * entry for each method whose name is not in <code>alreadySeen</code>.
   *
   * @param ext                the class/interface whose parents will be
   *                           asked to write their EPV entries.
   * @param self               the type used for the self pointer.
   * @param doStatic           should <code>static</code> or
   *                           non-<code>static</code> entries be listed?
   * @param methodsAlreadySeen set of method names already included.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception. It can be caused by I/O trouble or
   *    violations of the data type invariants.
   */
  private void generateLocalEntries(Extendable ext, String self, 
                                    boolean doStatic, Set methodsAlreadySeen)
    throws CodeGenerationException
  {
    /*
     * Process the methods in sorted order and output each function
     * pointer prototype in order.
     */
    Set methodsAlreadySeen_save = new HashSet(methodsAlreadySeen);
    
    d_writer.writeCommentLine("Methods introduced in " +
                              ext.getSymbolID().getSymbolName());

    List methods;
    if (doStatic)
      methods = ext.getStaticMethods(false);
    else
      methods = ext.getNonstaticMethods(false);

    for(Iterator m = methods.iterator(); m.hasNext(); ) {
      Method method = (Method) m.next();
      generateEPVMethod(method, self, methodsAlreadySeen);
      if ((method.getCommunicationModifier() == Method.NONBLOCKING) &&
          !d_context.getConfig().getSkipRMI()) {
        Method send = method.spawnNonblockingSend();
        if ( send != null ) { 
          generateEPVMethod(send, self, methodsAlreadySeen );
        } 
        Method recv = method.spawnNonblockingRecv();
        if ( recv != null ) {
                generateEPVMethod(recv, self, methodsAlreadySeen );
        }
      }
    }

    /*
     * There are some nasty reinterpret_cast<>s of the epv to corresponding
     * BaseInterface and BaseObject types, so we have to be careful to
     * generate all native entries _after_ all the regular pointers. 
     */
    if(d_context.getConfig().getFastCall()) {
      d_writer.writeCommentLine("Native entry points for methods introduced in " +
                                ext.getSymbolID().getSymbolName());
      for(Iterator m = methods.iterator(); m.hasNext(); ) {
        Method method = (Method) m.next();
        generateNativeEPVMethod(method, self, methodsAlreadySeen_save);
      }
      d_writer.println();
    }
  }

  /**
   * Generate the implicit/builtin methods for this fundamental
   * class or interface. These methods always appear first in
   * entry point vector (EPV).  Note that the built-in checks and
   * hooks methods are skipped for SIDL symbols.
   *
   * @param ext         the class/interface whose parents will be
   *                    asked to write their EPV entries.
   * @param self        the type used for the self pointer.
   * @param doStatic    should <code>static</code> or
   *                    non-<code>static</code> entries be listed?
   * @param alreadySeen set of method names already included.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception. It can be caused by I/O trouble or
   *    violations of the data type invariants.
   */
  private void generateBuiltinEntries(Extendable ext, String self, 
                                      boolean doStatic, Set alreadySeen)
    throws CodeGenerationException
  {
    final SymbolID id          = ext.getSymbolID();
    final int      numBuiltins = (ext.isInterface() 
                                 ? IOR.INTERFACE_BUILT_IN_METHODS
                                 : IOR.CLASS_BUILT_IN_METHODS);
    d_writer.writeCommentLine("Implicit builtin methods");

    /*
     * Output standard function pointers as appropriate.
     */
    if (doStatic) {
      for (int i = 0; i < numBuiltins; ++i){
        d_writer.writeCommentLine("" + i);
        if (IOR.hasStaticBuiltin(i) &&
            !(IOR.isRMIRelated(i) && d_context.getConfig().getSkipRMI())) {
          Method b = IOR.getBuiltinMethod(i, id, d_context, doStatic);
          generateEPVMethod(b, self, alreadySeen); 
        }
      }
    } else {
      for (int i = 0; i < numBuiltins; ++i){
        if (!(IOR.isRMIRelated(i) && d_context.getConfig().getSkipRMI())) {
          d_writer.writeCommentLine("" + i);
          Method b = IOR.getBuiltinMethod(i, id, d_context, doStatic);
          generateEPVMethod(b, self, alreadySeen); 
        }
      }
    }
  }

  /**
   * Generate the entry point vector entries for the parent class and/or
   * interfaces of <code>ext</code>. This must write the methods in a
   * consistent order.
   *
   * @param ext                the class/interface whose parents will be
   *                           asked to write their EPV entries.
   * @param self               the type used for the self pointer.
   * @param doStatic           should <code>static</code> or
   *                           non-<code>static</code> entries be listed?
   * @param methodsAlreadySeen set of method names already included.
   * @param extAlreadySeen     set of extendables whose methods have
   *                           already been included
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception. It can be caused by I/O trouble or
   *    violations of the data type invariants.
   */
  private void generateParentEntries(Extendable ext, String self,
                                     boolean doStatic, Set methodsAlreadySeen,
                                     Set extAlreadySeen) 
    throws CodeGenerationException
  {
    if (ext instanceof gov.llnl.babel.symbols.Class) {
      generateEPVEntries
        (((gov.llnl.babel.symbols.Class)ext).getParentClass(), self, doStatic, 
        methodsAlreadySeen, extAlreadySeen);
    }
    /*
     * It is critical that we always visit parent interfaces in a canonical
     * order. The precise order is not important. The fact that the same
     * order is used every time is critical!
     */
    Object[] parents = ext.getParentInterfaces(false).toArray();
    Arrays.sort(parents, new SortComparator());
    for(int i = 0; i < parents.length; ++i){
      generateEPVEntries((Extendable)parents[i], self, doStatic, 
                         methodsAlreadySeen, extAlreadySeen);
    }
  }

  private static final boolean isBaseClass(Extendable ext) {
    return ((ext instanceof gov.llnl.babel.symbols.Class) &&
       (((gov.llnl.babel.symbols.Class)ext).getParentClass() == null));
       
  }
  
  private static final boolean hasParents(Extendable ext) {
    return (!isBaseClass(ext)) || (!ext.getParentInterfaces(false).isEmpty());
  }

  /**
   * Generate EPV entries in a top down fashion. Parent entries are listed
   * before local entries. Each method name is listed only once.
   *
   * @param ext                the class/interface whose parents will be
   *                           asked to write their EPV entries.
   * @param self               the type used for the self pointer.
   * @param doStatic           should <code>static</code> or
   *                           non-<code>static</code> entries be listed?
   * @param methodsAlreadySeen set of method names already included.
   * @param extAlreadySeen     a set of extendables whose methodss have
   *                           already been included.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    this is a catch all exception. It can be caused by I/O trouble or
   *    violations of the data type invariants.
   */
  private void generateEPVEntries(Extendable ext, String self, boolean doStatic,
                                  Set methodsAlreadySeen, Set extAlreadySeen) 
    throws CodeGenerationException 
  {
    if ((ext != null) && !extAlreadySeen.contains(ext)) {
      if (hasParents(ext)) {
        generateParentEntries(ext, self, doStatic, methodsAlreadySeen, 
                              extAlreadySeen);
      }
      generateLocalEntries(ext, self, doStatic, methodsAlreadySeen);
      extAlreadySeen.add(ext);
    }
    
  }
  

   /**
    * Generate invariant clause data for contract clause checking purposes.
    */
   private void generateInvClauseData(Extendable ext) 
     throws CodeGenerationException 
   {
     if (IOR.generateContractChecks(ext, d_context)) {
        d_writer.writeComment("Define invariant clause data for interface "
          + "contract enforcement.", false);

        SymbolID id = ext.getSymbolID();

/*
 * ToDo...If the following are made conditional again, be sure the
 * corresponding changes are made to IORSource.java and, perhaps even,
 * the runtime.
 */
//        if (ext.hasInvariants(true)) {
          d_writer.print("static " + IOR.MACRO_VAR_UNUSED + " ");
          d_writer.print(IOR.getInvDescDataStruct(id));
          d_writer.println("{");
          d_writer.tab();
          d_writer.println("int    " + IOR.D_INV_COMPLEXITY + ";");
          d_writer.println("double " + IOR.D_EXEC_INV_TIME + ";");
          d_writer.backTab();
          d_writer.println("} " + IOR.getInvDescDataName(id) + " = {");
          d_writer.tab();
          d_writer.println(ext.getInvDefaultComplexity() + ", 0.0,");
          d_writer.backTab();
          d_writer.println("};");
          d_writer.println();
//        }
     }
   }
  

   /**
    * Generate the method description data, including indices to facilitate 
    * access to the corresponding control information for contract clause
    * checking purposes.  
    */
   private void generateMethodDescData(Extendable ext) 
     throws CodeGenerationException 
   {
     if (IOR.generateContractChecks(ext, d_context)) {
        d_writer.writeComment("Define method description data for interface "
          + "contract enforcement.", false);
        int      num      = 0;
        SymbolID id       = ext.getSymbolID();
        List     methods  = (List)ext.getMethods(true);
        String   typeDecl = "static const int32_t ";
        String   minName  = IOR.getMethodIndex(id, "MIN");
        int      width    = Utilities.getWidth(methods) + typeDecl.length()
                            + minName.length();
        boolean  hasInvs  = ext.hasInvClause(true);

        for (Iterator i = methods.iterator(); i.hasNext(); ) {
           Method method = (Method) i.next();
           if (hasInvs || method.hasPreClause() || method.hasPostClause()) {
              d_writer.printAligned(typeDecl + IOR.getMethodIndex(id, method), 
                                    width);
              d_writer.println(" = " + num + ";");
              num++;
           }
        }
        d_writer.printAligned(typeDecl + minName, width);
        d_writer.println(" = 0;");
        d_writer.printAligned(typeDecl + IOR.getMethodIndex(id, "MAX"), width);
        d_writer.println(" = " + (num-1) + ";");
        d_writer.println();

        d_writer.print("static " + IOR.MACRO_VAR_UNUSED + " ");
        d_writer.print(IOR.getMethodDescDataStruct(id));
        d_writer.println("{");
        d_writer.tab();
        d_writer.println("const char* name;");
        d_writer.println("sidl_bool   " + IOR.D_IS_STATIC + ";");
        d_writer.println("long        " + IOR.D_EST_INTERVAL + ";");
        d_writer.println("int         " + IOR.D_PRE_COMPLEXITY + ";");
        d_writer.println("int         " + IOR.D_POST_COMPLEXITY + ";");
        d_writer.println("double      " + IOR.D_EXEC_METH_TIME + ";");
        d_writer.println("double      " + IOR.D_EXEC_PRE_TIME + ";");
        d_writer.println("double      " + IOR.D_EXEC_POST_TIME + ";");
        d_writer.backTab();
        d_writer.println("} " + IOR.getMethodDescDataName(id) + "[] = {");
        d_writer.tab();
        for (Iterator i = methods.iterator(); i.hasNext(); ) {
           Method method = (Method) i.next();
           if (hasInvs || method.hasPreClause() || method.hasPostClause()) {
              String stat   = method.isStatic() ? "1" : "0";
              d_writer.print("{\"" + method.getLongMethodName() + "\", ");
              d_writer.print(stat + ", 0, " + method.getPreDefaultComplexity());
              d_writer.print(", " + method.getPostDefaultComplexity());
              d_writer.println(", 0.0, 0.0, 0.0},");
           }
        }
        d_writer.backTab();
        d_writer.println("};");
        d_writer.println();
     }
   }

   /**
    * Generate the EPV (entry point vector) data structure for the methods
    * in the interface or class.  If the flag is true, then generate an EPV
    * for the static members of the class.
    *
    * The EPV must be ordered correctly!  The parent entries must appear
    * before child entries.  This is required for runtime features.
    */
   private void generateEPV(Extendable ext, boolean doStatic)
     throws CodeGenerationException 
   {
      /*
       * Generate the name for this entry point vector as well as a pointer
       * to "self" for this structure.  For classes, self will be the object
       * structure whereas for interfaces it is void*.
       */
      final SymbolID id   = ext.getSymbolID();
      String         self = null;
      if (!doStatic) {
        if (ext.isInterface()) {
           self = "void* self";
        } else {
           self = IOR.getObjectName(id) + "* self";
        }
      }

      /*
       * Write a comment block to document the EPV structure.
       */
      String desc = doStatic ? "static " : "";
      d_writer.writeComment("Declare the " + desc 
        + "method entry point vector.", false);

      /*
       * Begin definition of the EPV structure and increase tab indent.
       */
      if (doStatic) {
         d_writer.print(IOR.getSEPVName(id));
      } else {
         d_writer.print(IOR.getEPVName(id));
      }
      d_writer.println(" {");
      d_writer.tab();
      Set methodsAlreadySeen = new HashSet();
      Set extAlreadySeen = new HashSet();

      //Put the methods in from the "From Clauses" into methodsAlreadySeen
      //so we don't generate them.
      for(Iterator ms = ext.getRenamedMethods().iterator(); ms.hasNext();) {
        Method renamedMethod = (Method) ms.next();
        if (ext.lookupMethodByLongName(renamedMethod.getLongMethodName(), false)
                == null) {
          methodsAlreadySeen.add(renamedMethod.getLongMethodName());
        }
      }

      generateBuiltinEntries(ext, self, doStatic, methodsAlreadySeen);
      generateEPVEntries(ext, self, doStatic, methodsAlreadySeen, 
                         extAlreadySeen);
      methodsAlreadySeen = null;
      extAlreadySeen = null;
      d_writer.backTab();
      d_writer.println("};");
      d_writer.println();
   }


   /**
    * Generate the pre EPV (entry point vector for for pre hooks) 
    * data structure for the methods
    * in the interface or class.  If the flag is true, then generate an EPV
    * for the static members of the class.
    *
    * The EPV must be ordered correctly!  The parent entries must appear
    * before child entries.  This is required for runtime features.
    */
   private void generatePreHooksEPV(Extendable ext, boolean doStatic)
     throws CodeGenerationException 
   {
       boolean hasMethod = false;
      /*
       * Generate the name for this entry point vector as well as a pointer
       * to "self" for this structure.  For classes, self will be the object
       * structure whereas for interfaces it is void*.
       */
      final SymbolID id   = ext.getSymbolID();
      String         self = null;
      if (!doStatic) {
        if (ext.isInterface()) {
           self = "void* self";
        } else {
           self = IOR.getObjectName(id) + "* self";
        }
      }

      /*
       * Write a comment block to document the EPV structure.
       */
      String desc = doStatic ? "static " : "";
      d_writer.writeComment("Declare the " + desc 
        + "method pre hooks entry point vector.", false);

      /*
       * Begin definition of the EPV structure and increase tab indent.
       */
      if (doStatic) {
         d_writer.print(IOR.getPreSEPVName(id));
      } else {
         d_writer.print(IOR.getPreEPVName(id));
      }
      d_writer.println(" {");
      d_writer.tab();

      Iterator m = null;
      if (doStatic) {
        m = ext.getStaticMethods(false).iterator();
      } else {
        m = ext.getNonstaticMethods(false).iterator();
      }

      while (m.hasNext()) {
        Method method = (Method) m.next();
        Method pre    = method.spawnPreHook();        
        generateEPVEntry(pre, self);
        hasMethod = true;
      }
      if (!hasMethod) {
        d_writer.writeCommentLine("Avoid empty struct");
        d_writer.println("char d_not_empty;");
      }

      d_writer.backTab();
      d_writer.println("};");
      d_writer.println();
   }

   /**
    * Generate the post EPV (entry point vector for for pre hooks) 
    * data structure for the methods
    * in the interface or class.  If the flag is true, then generate an EPV
    * for the static members of the class.
    *
    * The EPV must be ordered correctly!  The parent entries must appear
    * before child entries.  This is required for runtime features.
    */
   private void generatePostHooksEPV(Extendable ext, boolean doStatic)
     throws CodeGenerationException 
   {
      /*
       * Generate the name for this entry point vector as well as a pointer
       * to "self" for this structure.  For classes, self will be the object
       * structure whereas for interfaces it is void*.
       */
      final SymbolID id   = ext.getSymbolID();
      String         self = null;
      boolean hasMethod = false;
      if (!doStatic) {
        if (ext.isInterface()) {
           self = "void* self";
        } else {
           self = IOR.getObjectName(id) + "* self";
        }
      }

      /*
       * Write a comment block to document the EPV structure.
       */
      String desc = doStatic ? "static " : "";
      d_writer.writeComment("Declare the " + desc 
        + "method post hooks entry point vector.", false);

      /*
       * Begin definition of the EPV structure and increase tab indent.
       */
      if (doStatic) {
        d_writer.print(IOR.getPostSEPVName(id));
      } else {
        d_writer.print(IOR.getPostEPVName(id));
      }
      d_writer.println(" {");
      d_writer.tab();

      Iterator m = null;
      if (doStatic) {
        m = ext.getStaticMethods(false).iterator();
      } else {
        m = ext.getNonstaticMethods(false).iterator();
      }

      while (m.hasNext()) {
        Method method = (Method) m.next();
        Method post   = method.spawnPostHook();        
        generateEPVEntry(post, self);
        hasMethod = true;
      }

      if (!hasMethod) {
        d_writer.writeCommentLine("Avoid empty struct");
        d_writer.println("char d_not_empty;");
      }
      d_writer.backTab();
      d_writer.println("};");
      d_writer.println();
   }

   /*
    * Generate the object data structure for an interface.  This structure
    * contains an EPV, a pointer to the object implementation, and an integer
    * identifier.
    */
   private void generateInterfaceObject(Interface ifc) {
      SymbolID id   = ifc.getSymbolID();
      String   type = IOR.getObjectName(id);

      d_writer.writeComment("Define the interface object structure.", false);

      d_writer.println(type + " {");
      d_writer.tab();

      String epv   = IOR.getEPVName(id);
      int    width = epv.length() + 2;

      d_writer.printAligned(epv + "* ", width);
      d_writer.println(IOR.getEPVVar(IOR.PUBLIC_EPV) + ";");

      d_writer.printAligned("void*", width);
      d_writer.println("d_object;");

      d_writer.backTab();
      d_writer.println("};");
      d_writer.println();
   }


   /**
    * Generate the object data structure for a class.  This structure
    * starts with the parent object (if not null) followed by new interfaces
    * not found in parent objects.  It also contains an EPV, a data pointer,
    * and an integer identifier.
    */
   private void generateClassObject(Class cls) throws CodeGenerationException {
      d_writer.writeComment("Define the class object structure.", false);

      /*
       * Extract unique interfaces that will be output in the object
       * data structure.
       */
      List unique = Utilities.sort(Utilities.getUniqueInterfaceIDs(cls));
      int  width  = Utilities.getWidth(unique) + "struct __object".length();

      /*
       * Extract information about the parent class (if it exists).
       */
      Class    parent = cls.getParentClass();
      SymbolID pid    = null;
      String   ptype  = null;

      if (parent != null) {
         pid   = parent.getSymbolID();
         ptype = IOR.getObjectName(pid);
         if (ptype.length() > width) {
            width = ptype.length();
         }
      }

      /*
       * Extract information about the EPV structure and its width.
       */
      boolean addContracts = IOR.generateContractEPVs(cls, d_context);
      String  ctrls        = null;

      SymbolID id  = cls.getSymbolID();
      String   epv = IOR.getEPVName(id) + "*";
      int      len = epv.length();
      if (len > width) { width = len; }
      if (addContracts) {
        ctrls = IOR.getControlsNStatsStruct(id);
        len   = ctrls.length();
        if (len > width) { width = len; }
      }
      width = width + 1;

      /*
       * Output the object data structure.  Start with the parent class
       * (if it exists) followed by the new interface objects.
       */
      d_writer.println(IOR.getObjectName(id) + " {");
      d_writer.tab();

      if (parent != null) {
         d_writer.printAligned(ptype, width);
         d_writer.println("d_" + IOR.getSymbolName(pid).toLowerCase() + ";");
      }

      for (Iterator i = unique.iterator(); i.hasNext(); ) {
         SymbolID sid = (SymbolID) i.next();
         d_writer.printAligned(IOR.getObjectName(sid), width);
         d_writer.println("d_" + IOR.getSymbolName(sid).toLowerCase() + ";");
      }

      /*
       * Fill in the remainder of the data members in the structure.
       */
      d_writer.printAligned(epv, width);
      d_writer.println(IOR.getEPVVar(IOR.PUBLIC_EPV) + ";");
      if (IOR.generateBaseEPVAttr(cls, d_context)) {
        d_writer.printAligned(epv, width);
        d_writer.println(IOR.getEPVVar(IOR.BASE_EPV) + ";");
      }

      if (addContracts) {
        d_writer.printAligned(ctrls, width);
        d_writer.println(IOR.D_CSTATS + ";");
      }

      d_writer.printAligned("void*", width);
      d_writer.println(IOR.D_DATA + ";");

      d_writer.backTab();
      d_writer.println("};");
      d_writer.println();
   }
}
