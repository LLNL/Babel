//
// File:        SidlSource.java
// Package:     gov.llnl.babel.backend.sidl
// Revision:    @(#) $Revision: 7421 $
// Description: Generate a sidl file for contents of symbol table
//
// Copyright (c) 2002-2003, Lawrence Livermore National Security, LLC
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


package gov.llnl.babel.backend.sidl;

import gov.llnl.babel.BabelConfiguration;
import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.sidl.ScopedName;
import gov.llnl.babel.backend.sidl.Sidl;
import gov.llnl.babel.backend.writers.LanguageWriter;
import gov.llnl.babel.backend.writers.LanguageWriterForSidl;
import gov.llnl.babel.backend.Utilities;
import gov.llnl.babel.symbols.Argument;
import gov.llnl.babel.symbols.AssertionExpression;
import gov.llnl.babel.symbols.Assertion;
import gov.llnl.babel.symbols.Attributes;
import gov.llnl.babel.symbols.Class;
import gov.llnl.babel.symbols.Comment;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Enumeration;
import gov.llnl.babel.symbols.Interface;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.Package;
import gov.llnl.babel.symbols.Struct;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.SymbolUtilities;
import gov.llnl.babel.symbols.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * This class provides the ability to write SIDL source file for a SIDL
 * class/interface. 
 */
public class SidlSource {

  private Context d_context;

  /**
   * This is the output device.
   */
  private LanguageWriterForSidl d_lw;

  /**
   * Generate an instance to write the SIDL file.
   * 
   * @param writer  the output device to which the SIDL should be written.
   */
  public SidlSource(LanguageWriterForSidl writer,
                    Context context) {
    d_lw    = writer;
    d_context = context;
  }


  /**
   * Generate the SIDL for a struct.
   *
   * @param enm  the SIDL struct to be written.
   * @exception   gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   */
  public void generateCode(Struct st)
    throws CodeGenerationException
  {
    d_lw.writeComment(st, true, false);
    generateCustomAttrs(st, null);
    d_lw.println("struct " + st.getSymbolID().getShortName() + " {");
    d_lw.tab();
    Iterator i = st.getItems().iterator();
    while(i.hasNext()) {
      Struct.Item item = (Struct.Item)i.next();

      Type t = item.getType();
      int type = t.getDetailedType();
      if (type == Type.ARRAY) {
        final Type arrayType = t.getArrayType();
        if (null != arrayType) {
          if (t.isRarray()) {
            if (t.isNumericArray()){
              generateIndices(item);
            }
          } else {
            d_lw.println(item.getType().getTypeString() + " " + item.getName() + ";");
          }
        }
      } else {
        d_lw.println(item.getType().getTypeString() + " " + item.getName() + ";");
      }
    }
    d_lw.backTab();
    d_lw.println("}");
  }

  private void generateIndices(Struct.Item e)
    throws CodeGenerationException
    {

      Type t = e.getType();
      Type aType = t.getArrayType();
      StringBuffer rawString = new StringBuffer();
      Iterator indexVar = t.getArrayIndexExprs().iterator();
      AssertionExpression ae = (AssertionExpression)indexVar.next();
      String checkInt = ae.toString();
      rawString.append(e.getType().getTypeString() + " " +e.getName() + "("+checkInt);

      final int dims=t.getArrayDimension();

      int count=1;
      while(indexVar.hasNext()) {
        ae = (AssertionExpression)indexVar.next();
        checkInt = ae.toString();
        rawString.append(","+checkInt);
        count++;
      }
      rawString.append(");");
      d_lw.println(rawString.toString());

      if (count != dims){
        throw new CodeGenerationException("Babel: Error in Rarray dimensions. Dimensions="+dims+" of type don't matc    h those given in name "+e.getName()+" (dimensions="+count+").");
      }
    }


  private void generateCustomAttrs(Attributes a, String [] builtins)
  {
    if (a.getAttributes().size() > 0) {
      HashSet all = new HashSet(a.getAttributes());
      if (builtins != null) {
        for(int i = 0; i < builtins.length; ++i) {
          all.remove(builtins[i]);
        }
      }
      if (all.size() > 0) {
        Object [] attrs = all.toArray();
        Arrays.sort(attrs);
        d_lw.print("%attrib{");
        for(int i = 0; i < attrs.length; ++i) {
          final String key=attrs[i].toString();
          String value = a.getAttribute(key);
          d_lw.print(" ");
          d_lw.print(key);
          if (value != null) {
            d_lw.print(" = \"");
            d_lw.print(value);
            d_lw.print("\"");
          }
          if ((i+1) < attrs.length) {
            d_lw.print(",");
          }
        }
        d_lw.print(" } ");
      }
    }
  }

  /**
   * Generate the SIDL for the enumeration.
   * 
   * @param enm  the SIDL enumeration to be written.
   * @exception   gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   */
  public void generateCode(Enumeration enm)
    throws CodeGenerationException
  {
    final SymbolID id  = enm.getSymbolID();
    final String ename  = id.getShortName();

    d_lw.println();
    d_lw.writeComment(enm, true, false);
    generateCustomAttrs(enm, null);
    d_lw.println("enum " + ename + " {");
    d_lw.tab();
    int maxlength = Utilities.getWidth(enm.getEnumerators());
    for (Iterator e = enm.getIterator(); e.hasNext(); ) {
      String name = (String) e.next();
      Comment cmt = enm.getEnumeratorComment(name);
      d_lw.writeComment(cmt, true);
      d_lw.printAligned(name, maxlength);
      if (enm.definedByUser(name)) {
        d_lw.print(" = ");
        d_lw.print(String.valueOf(enm.getEnumeratorValue(name)));
      }
      d_lw.println(e.hasNext() ? "," : "");
      if ((cmt != null) && (!cmt.isEmpty()) ) {
        d_lw.println();
      }
    }
    d_lw.backTab();
    d_lw.println("};");
  }


  /**
   * Return indication of whether or not the symbol name is the same as
   * the base class.
   *
   * @param name the symbol name being checked
   * @return     true if the name equals the base class name; else, false
   */
  public static boolean isBaseClassName(String name) {
    boolean isBase = false;
    String  base   = BabelConfiguration.getBaseClass();

    if (  (name.equals(base))
       || (name.equals(SymbolID.getShortName(base))) ) {
      isBase = true;
    }
    return isBase;
  }


  /**
  /**
   * Return indication of whether or not the symbol name is the same as
   * the base interface.
   *
   * @param name the symbol name being checked
   * @return     true if the name equals the base interface name; else, false
   */
  public static boolean isBaseInterfaceName(String name) {
    boolean isBase = false;
    String  base   = BabelConfiguration.getBaseInterface();

    if (  (name.equals(base))
       || (name.equals(SymbolID.getShortName(base))) ) {
      isBase = true;
    }
    return isBase;
  }


  /**
   * Print the parent interfaces for the extendable (i.e., class or interface).
   * Indentation is handled accordingly.
   * 
   * @param ext  the SIDL extendable whose parent interfaces are to be written
   * @exception  gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   */
  public void printParentInterfaces(Extendable ext)
    throws CodeGenerationException
  {
    Collection ifaces       = ext.getParentInterfaces(false);
    boolean    printed_spec = false;
    String     spec, name;
    String     scope        = SymbolUtilities.
                             getParentPackage(ext.getSymbolID().getFullName());

    if ( ext.isInterface() ) {
      spec = " extends ";
    } else {
//
// ToDo...Need to check if this should be implements-all.  That could be
//     ...inferred if all of the parent's methods are implemented locally 
//     ...and if the comments are identical.  (Concern:  since we aren't 
//     ...keeping track of extendable-only methods, how can we tell if the 
//     ...parent's methods are in it only or are from it's parent(s)?  Or 
//     ...does it matter?)
//
      spec = "implements ";
    }

    boolean increased_tab = false;
    for (Iterator i = ifaces.iterator(); i.hasNext(); ) {
      Symbol sym = (Symbol)i.next();
      name = ScopedName.getScopedName(scope, sym.getSymbolID().getFullName());
      if (  ( (ext.isInterface()) && (!isBaseInterfaceName(name)) ) 
         || ( !ext.isInterface() ) ) {
        if (!printed_spec) {
          d_lw.print(spec);
          printed_spec = true;
        }
        d_lw.print(name);
        if (i.hasNext()) {
          d_lw.println(",");
          if (!increased_tab) {
            d_lw.tab();
            increased_tab = true;
          }
        }
      }
    }
    if (printed_spec) {
      d_lw.println();
    }
    if (increased_tab) {
      d_lw.backTab();
    } 
  }


  /**
   * Print the assertion.
   *
   * @param lw  The language writer to which the signature is printed.
   * @param as  The assertion whose contents are being printed.
   */
  private void printAssertion(LanguageWriter lw, Assertion as)
  {
    Comment cmt = as.getComment();
    lw.writeComment(cmt, true);
    lw.println(as.toString() + ";");
  }

  /**
   * Print the list of assertions associated with a contract clause.
   *
   * @param lw      The language writer to which the signature is printed.
   * @param type    The name of the contract clause.
   * @param clause  The list of assertions contained in the clause.
   */
  private void printClause(LanguageWriter lw, String typeStr, List clause)
  {
    if (clause.size() > 0) {
      lw.tab();
      lw.println(typeStr);
      lw.tab();
      for (Iterator i = clause.iterator(); i.hasNext(); ) {
        printAssertion(lw, (Assertion) i.next());
      }
      lw.backTab();
      lw.backTab();
    }
  }

  /**
   * Print the interface contract clause contents.
   *
   * @param lw     The language writer to which the signature is printed.
   * @param type   The type of clause (see Assertion.java).
   * @param clause The list of assertions contained in the clause.
   *                   
   */
  private void printContractClause(LanguageWriter lw, int type, List clause)
  {
    if ( Assertion.isInvariant(type)
       || Assertion.isPrecondition(type)
       || Assertion.isPostcondition(type) ) 
    {
      printClause(lw, Assertion.getTypeName(type), clause);
    }
  }


  /**
   * Print the signature of the method, optionally including the definition
   * modifier, and any associated pre- and post-conditions to the specified 
   * <code>LanguageWriter</code>.  Also, optionally abbreviate the type if 
   * in specified package.
   *
   * @param lw          the language writer to which the signature is printed
   * @param ext         the Extendable for which this method is being generated
   * @param meth        the method whose signature is being printed
   * @param parent_pkg  the string containing the parent package.  When not
   *                    null, it is used to strip the package from the return
   *                    string if it is in the specified package.
   */
  public void printSignaturePlus(LanguageWriter lw, Extendable ext,
                                 Method meth, String parent_pkg)
  {
    lw.print(meth.getSignaturePreface(ext.isInterface(), parent_pkg) + "(");

    int  increased_tabs = 0;
    List args           = meth.getArgumentListWithIndices();
    for (Iterator a = args.iterator(); a.hasNext(); ) {
      // ToDo...Once ScopedName's getScopedName() differs from the
      //     ...SymbolUtilities' getSymbolName method used by the 
      //     ...getArgumentString() method, this (or both) will need to 
      //     ...be changed so this generates the arg string on its own.
      lw.print(((Argument) a.next()).getArgumentString(parent_pkg));
      if (a.hasNext()) {
        lw.println(",");
        if (increased_tabs == 0) {
          lw.tab();
          lw.tab();
          lw.tab();
          increased_tabs = 3;
        }
      }
    }
    lw.print(")");// + meth.getCommunicationModifierString());

    Set exceptions = meth.getExplicitThrows();
    if (!exceptions.isEmpty()) {
      lw.println();
      if (increased_tabs > 0) {
        lw.backTab();
        increased_tabs--;
      } else {
        lw.tab();
        lw.tab();
        increased_tabs = 2;
      }
      lw.print("throws ");
      boolean indentedThrows = false;
      for (Iterator t = exceptions.iterator(); t.hasNext(); ) {
        SymbolID sid = (SymbolID) t.next();
        String fname = sid.getFullName();
        if (parent_pkg != null) {
          lw.print(ScopedName.getScopedName(parent_pkg, fname));
        } else {
          lw.print(fname);
        }
        if (t.hasNext()) {
          lw.println(",");
          if (!indentedThrows) {
            lw.tab();
            indentedThrows = true;
          }
        }
      }
      if (indentedThrows) {
        lw.backTab();
        indentedThrows = false;
      }
    }

    if (ext.methodWasRenamed(meth)) {
      Method oldM = ext.getRenamedMethod(meth);
      SymbolID sid = ext.getRenamedMethodSymbolID(oldM);
      lw.print(" from " + sid.getFullName()+"."+oldM.getShortMethodName());
      String extension = oldM.getNameExtension();
      if ((extension != null) && (extension != "")) {
        lw.print(" [" + extension + "] ");
      }
    }

    boolean noContract = true;
    List    preClause  = meth.getPreClause();
    if (!preClause.isEmpty()) {
      lw.println(";");
      for (int i=increased_tabs; i > 1; i--) {
        lw.backTab();
        increased_tabs--;
      }
      noContract = false;
      printContractClause(lw, Assertion.REQUIRE, preClause);
    }

    List postClause = meth.getPostClause();
    if (!postClause.isEmpty()) {
      if (noContract) {
        noContract = false;
        lw.println(";");
        for (int i=increased_tabs; i > 1; i--) {
          lw.backTab();
          increased_tabs--;
        }
      }
      printContractClause(lw, Assertion.ENSURE, postClause);
    }
    if (noContract) {
      lw.println(";");
    }
    for (int i=increased_tabs; i > 0; i--) {
      lw.backTab();
    }
  }


  /**
   * Print the methods associated with the extendable (i.e., class or 
   * interface).  Indentation is handled accordingly.
   * 
   * @param ext  the SIDL extendable whose methods are to be written
   * @exception  gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   *
   * Assumes the method is sandwiched between the printing of open
   * and close parentheses.
   */
  public void printMethods(Extendable ext)
    throws CodeGenerationException
  {
    Collection methods = ext.getMethods(false);
    String     name    = Sidl.getSymbolName(ext.getSymbolID());

    for (Iterator m = methods.iterator(); m.hasNext(); ) {
      Method meth = (Method)m.next();
      d_lw.writeComment(meth, true, false);
      printSignaturePlus(d_lw, ext, meth,
                         SymbolUtilities.getParentPackage(name));
      if ( m.hasNext() ) {
        d_lw.println();
      }
    }
  }


  /**
   * Generate the SIDL for the class.
   * 
   * @param cls  the SIDL class to be written.
   * @exception  gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   */
  public void generateCode(Class cls)
    throws CodeGenerationException
  {
    String   builtinAttrs[] = { "abstract" };
    SymbolID sid   = cls.getSymbolID();
    String   sname = sid.getShortName();
    String   scope = SymbolUtilities.getParentPackage(sid.getFullName());

    d_lw.println();
    d_lw.writeComment(cls, true, false);
    if (cls.isAbstract()) {
      d_lw.print("abstract ");
    }
    generateCustomAttrs(cls, builtinAttrs);
    d_lw.print("class " + sname + " ");
    Class   parent          = cls.getParentClass();
    boolean printed_extends = false;
    if (parent != null) {
      SymbolID pid   = parent.getSymbolID();
      String   pname = ScopedName.getScopedName(scope, pid.getFullName());
      if ( (pname != null) && (!isBaseClassName(pname)) ) {
        d_lw.println("extends " + pname);
        printed_extends = true;
      }
    }
    if ( printed_extends ) {
      d_lw.tab();
    }
    printParentInterfaces((Extendable)cls);
    if ( printed_extends ) {
      d_lw.backTab();
    }
    d_lw.println("{");
    d_lw.tab();
    printContractClause(d_lw, Assertion.INVARIANT, cls.getInvClause(false));
    printMethods((Extendable)cls);
    d_lw.backTab();
    d_lw.println("}");
  }


  /**
   * Generate the SIDL for the interface.
   * 
   * @param iface the SIDL interface to be written.
   * @exception   gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   */
  public void generateCode(Interface iface)
    throws CodeGenerationException
  {
    String name = iface.getSymbolID().getShortName();

    d_lw.println();
    d_lw.writeComment(iface, true, false);
    generateCustomAttrs(iface, null);
    d_lw.print("interface " + name + " ");
    printParentInterfaces((Extendable)iface);
    d_lw.println("{");
    d_lw.tab();
    printContractClause(d_lw, Assertion.INVARIANT, iface.getInvClause(false));
    printMethods((Extendable)iface);
    d_lw.backTab();
    d_lw.println("}");
  }


  /**
   * Prints the requires statements based on symbols associated with the
   * specified package.
   *
   * @param pkg  the package whose imports/requires list is being output
   */
  public void printRequires(Package pkg)
  {
    ScopedName.printRequires(d_context, pkg, d_lw);
  }


  /**
   * Generate the SIDL for a package.  
   * 
   * @param pkg  the SIDL package whose file is to be written.
   * @exception  gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
   */
  public void generateCode(Package pkg)
    throws CodeGenerationException
  {
    final String builtinAttrs[] = { "final" };
    Collection   symids = pkg.getOrderedSymbolReferences();
    SymbolID     id     = pkg.getSymbolID();
    String       name   = id.getShortName();
    String       vstr   = null;

    /*
     * First generate the requires/versions and imports statements but only
     * if this is the highest level package.
     */
    if ( !SymbolUtilities.hasParentPackage(id) ) {
      printRequires(pkg);
    }

    d_lw.writeComment(pkg, true, false);

    /*
     * NOW we can generate the package!
     */
    if ( pkg.getFinal() ) {
      d_lw.print("final ");
    }
    generateCustomAttrs(pkg, builtinAttrs);
    d_lw.print("package " + name);
    if ( !SymbolUtilities.sameVersionAsParent(d_context, id) ) {
      vstr = SymbolUtilities.getVersionString(id);
      if (vstr != null) {
        d_lw.print(" " + vstr);
      }
    } 
   
    d_lw.println(" {");

    d_lw.tab();
    for (Iterator s = symids.iterator(); s.hasNext(); ) {
      SymbolID sid  = (SymbolID)s.next();
      Symbol sym    = d_context.getSymbolTable().lookupSymbol(sid);
      switch (sym.getSymbolType()) { 
        case Symbol.CLASS:
          generateCode((Class)sym);
          break;
        case Symbol.ENUM:
          generateCode((Enumeration)sym);
          break;
        case Symbol.INTERFACE:
          generateCode((Interface)sym);
          break;
        case Symbol.PACKAGE:
          d_lw.println();
          generateCode((Package)sym);
          d_lw.println();
          break;
        case Symbol.STRUCT:
          generateCode((Struct)sym);
          break;
        default:
          throw new CodeGenerationException("Unknown/Invalid Symbol Type "
                         + "detected during SIDL Package generation");
      }
    }
    d_lw.backTab();
    d_lw.println();
    d_lw.println("}");
  }


  /**
   * Generate the SIDL file only for a package.
   *
   * @exception  gov.llnl.babel.backend.CodeGenerationException
   *   a catch all exception to indicate problems in the code generation
   *   phase.
  */
  public static void generateCode(Symbol                sym,
                                  LanguageWriterForSidl writer,
                                  Context               context)
    throws CodeGenerationException
  {
    if (sym instanceof Package) {
      SidlSource sidlFile = new SidlSource(writer, context);
      sidlFile.generateCode((Package)sym);
    }
  }
}
