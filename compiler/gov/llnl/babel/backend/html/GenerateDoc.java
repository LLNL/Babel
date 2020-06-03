//
// File:        GenerateDoc.java
// Package:     gov.llnl.babel.backend.html
// Revision:    @(#) $Id: GenerateDoc.java 7421 2011-12-16 01:06:06Z adrian $
// Description: generate C client code based on a set of symbol identifiers
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

package gov.llnl.babel.backend.html;

import gov.llnl.babel.Context;
import gov.llnl.babel.ResourceLoader;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.CodeGenerator;
import gov.llnl.babel.backend.Utilities;
import gov.llnl.babel.symbols.Argument;
import gov.llnl.babel.symbols.AssertionExpression;
import gov.llnl.babel.symbols.Class;
import gov.llnl.babel.symbols.Comment;
import gov.llnl.babel.symbols.Enumeration;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.Package;
import gov.llnl.babel.symbols.Struct;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.Type;
import gov.llnl.babel.xml.XMLUtilities;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Class <code>GenerateDoc</code> is the main entry point for the html
 * documentation backend. This code generates documentation for the
 * symbols on the command line.
 */
public class GenerateDoc implements CodeGenerator {

  static final String [] s_bar = {
    "Package", "Class", "Tree", "Deprecated", "Index", "Help"
  };

  private Context d_context = null;

  /**
   * The constructor does nothing interesting.  The entry point for
   * the <code>GenerateDoc</code> class is <code>generateCode</code>.
   */
  public GenerateDoc() { }

  private static String filename(SymbolID id)
  {
    return id.getSymbolName().replace('.','_') + ".html";
  }

  private void writeHeader(PrintWriter out,
                           boolean strict)
  {
    if (strict) {
      out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\"");
      out.println("        \"http://www.w3.org/TR/html4/strict.dtd\">");
    }
    else {
      out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"");
      out.println("        \"http://www.w3.org/TR/html4/loose.dtd\">");
    }
    out.println("<html>");
    out.println("<head>");
    out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
    out.println("<link type=\"text/css\" title=\"style\" rel=\"stylesheet\" href=\"stylesheet.css\">");
  }

  private String optionalLink(String text, HashMap urls)
  {
    Object uri = urls.get(text);
    if (uri != null) {
      return "<a href=\"" + uri.toString() + "\">" + text + "</a>";
    }
    return text;
  }

  private void printNavBar(PrintWriter out, HashMap urls, String selected)
  {
    out.println("<!--========Start of NAVBAR=======-->");
    out.println("<table width=\"100%\" cellspacing=\"0\" cellpadding=\"1\" border=\"0\">");
    out.println("  <tr>");
    out.println("    <td  class=\"NavBarCell1\" bgcolor=\"#EEEEFF\">");
    out.println("      <a name=\"navbar_top_firstrow\"></a>");
    out.println("      <table cellspacing=\"5\" cellpadding=\"0\" border=\"0\">");
    out.println("        <tr valign=\"top\" align=\"center\">");
    for(int i = 0; i < s_bar.length; ++i) {
      final boolean isTheOne = s_bar[i].equals(selected);
      out.println("          <td class=\"" +
                  (isTheOne ? "NavBarCell1Rev" : "NavBarCell1") + 
                  "\" bgcolor=\"" + 
                  (isTheOne ? "#FFFFFF" : "#EEEEFF") +
                  "\"><div class=\"" +
                  (isTheOne ? "NavBarFont1Rev" : "NavBarFont1") +
                  "\">&nbsp;" + 
                  optionalLink(s_bar[i], urls) + 
                  "&nbsp;</div></td>");
    }
    out.println("        </tr>");
    out.println("      </table>");
    out.println("    </td>");
    out.println("    <td class=\"NavBarCell1\" bgcolor=\"#EEEEFF\">&nbsp;</td>");
    out.println("  </tr>");
    out.println("  <tr>");
    out.println("    <td  class=\"NavBarCell2\" bgcolor=\"white\">");
    out.println("       " +
                optionalLink("PREV", urls) + "&nbsp;&nbsp;" +
                optionalLink("NEXT", urls));
    out.println("    </td>");
    out.println("    <td  class=\"NavBarCell2\" bgcolor=\"white\">");
    out.println("       <a target=\"_top\" href=\"index.html\"><b>FRAMES</b></a>");
    out.println("       &nbsp;&nbsp;<b>NO FRAMES</b>");
    out.println("       <script type=\"text/javascript\"></script>");
    out.println("       <noscript><a href=\"allclasses-noframe.html\"><b>All Clases</b></a>");
    out.println("       </noscript>");
    out.println("    </td>");
    out.println("  </tr>");
    out.println("</table>");
    out.println("<!--========End of NAVBAR=======-->");
  }

  private void listType(PrintWriter out,
                        String heading,
                        boolean frame,
                        ArrayList sids)
  {
    if (! sids.isEmpty()) {
      out.println("<div class=\"FrameHeadingFont\">" +
                  heading + "</div>");
      out.println("<div class=\"FrameItemFont\">");
      for (Iterator s = sids.iterator(); s.hasNext(); ) {
        Symbol symbol = (Symbol) s.next();
        out.println("<a " +
                    (frame ? "target=\"classFrame\" " : "" ) +
                    "href=\"" + filename(symbol) +
                    "\">" + symbol.getFullName() + "</a><br>");
      }
      out.println("</div>");
    }
  }

  private ArrayList filter(Collection sids, 
                           int type)
  {
    ArrayList result = new ArrayList(sids.size());
    for (Iterator s = sids.iterator(); s.hasNext();) {
      SymbolID id = (SymbolID)s.next();
      Symbol symbol = ((id instanceof Symbol) ? (Symbol)id : 
                       d_context.getSymbolTable().lookupSymbol(id));
      if (symbol.getSymbolType() == type) {
        result.add(symbol);
      }
    }
    return result;
  }
                        
  private void writeList(String filename,
                         ArrayList sids,
                         boolean frame)
    throws CodeGenerationException
  {
    PrintWriter out = null;
    try {
      out = new PrintWriter
        (new FileWriter(d_context.getConfig().getOutputDirectory() +
                        File.separator + filename));
      writeHeader(out, !frame);
      out.println("<title>All Classes</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("<div class=\"FrameHeadingFont\">All Classes</div>");
      out.println("<div><br></div>");
      listType(out, "Interfaces", frame, filter(sids, Symbol.INTERFACE));
      listType(out, "Classes", frame, filter(sids, Symbol.CLASS));
      listType(out, "Structs", frame, filter(sids, Symbol.STRUCT));
      listType(out, "Enumerations", frame, filter(sids, Symbol.ENUM));
      out.println("</body>");
      out.println("</html>");
    }
    catch (java.io.IOException ioe) {
      throw new CodeGenerationException("Unable to create file \"" + filename
                                        + "\": " + ioe.getMessage());
    }
    finally {
      if (out != null) {
        out.close();
      }
    }
  }

  /**
   * Try if there are no blank lines and all lines do not start with white
   * space.
   */
  private boolean oneParagraph(String [] lines,
                               String htmlStr)
  {
    if (lines.length > 1) {
      int leadingBlanks;
      int trailingBlanks;
      for(leadingBlanks = 0 ; leadingBlanks < lines.length; ++leadingBlanks) {
        if (lines[leadingBlanks].trim().length() > 0) break;
      }
      for(trailingBlanks = lines.length - 1 ; trailingBlanks > leadingBlanks; --trailingBlanks) {
        if (lines[trailingBlanks].trim().length() > 0) break;
      }
      for(int i = leadingBlanks; i <= trailingBlanks; ++i) {
        if ((lines[i].trim().length() == 0) || Character.isWhitespace(lines[i].charAt(0))) return false;
      }
    }
    return LiteHtmlCheck.isValidHtmlLiteFrag("<p>" + htmlStr + "</p>");
  }

  private void printComment(PrintWriter out,
                            Comment com)
  {
    int i;
    if ((com != null) && !com.isEmpty()) {
      StringBuffer buf = new StringBuffer();
      final String [] lines = com.getComment();
      for(i = 0; i < lines.length; ++i) {
        buf.append(lines[i].trim());
        buf.append('\n');
      }
      String htmlStr = buf.toString();
      if (htmlStr.trim().length() > 0) {
        if (LiteHtmlCheck.isValidHtmlLiteFrag(htmlStr)) {
          out.print(htmlStr);
        }
        else if (oneParagraph(lines, htmlStr)) {
          out.print("<p>" + htmlStr + "</p>");
        }
        else {
          out.print("<pre>");
          for(i = 0; i < lines.length; ++i) {
            out.println(XMLUtilities.encodeXMLString(lines[i]));
          }
          out.println("</pre>");
        }
      }
      else {
        out.println("&nbsp;");
      }
    }
    else {
      out.println("&nbsp;");
    }
  }

  private void tabulateList(PrintWriter out,
                            ArrayList   list,
                            String      desc)
  {
    if (!list.isEmpty()) {
      Collections.sort(list);
      out.println("<table width=\"100%\" cellpadding=\"2\" border=\"1\">");
      out.println("  <tr class=\"TableHeadingColor\" bgcolor=\"#CCCCFF\">");
      out.println("    <td  colspan=\"2\"><div class=\"TableHeadingFont\">");
      out.println("      " + desc + " Summary</div>");
      out.println("    </td>");
      out.println("  </tr>");
      for(Iterator i = list.iterator(); i.hasNext(); ) {
        Symbol sym = (Symbol)i.next();
        out.println("  <tr>");
        out.println("    <td width=\"15%\">");
        out.println("      <a href=\"" + filename(sym) +
                    "\">" + sym.getFullName() + "</a>");
        out.println("    </td>");
        out.println("    <td>");
        printComment(out, sym.getComment());
        out.println("    </td>");
        out.println("  </tr>");
      }
      out.println("</table>");
      out.println("<br>");
    }
  }

  private void writePackage(PrintWriter out,
                            Package pkg,
                            Set sids)
  {
    Set refs = new TreeSet(pkg.getSymbolReferences());
    refs.retainAll(sids);       // set intersection
    out.println("<table width=\"100%\" cellpadding=\"3\" border=\"0\">");
    out.println("  <tr>");
    out.println("    <td width=\"20%\">");
    out.println("      <h2><a name=\"" +
                pkg.getFullName() + "\">" + pkg.getFullName() + "</a></h2>");
    out.println("    </td>");
    out.println("    <td>");
    printComment(out, pkg.getComment());
    out.println("    </td>");
    out.println("  </tr>");
    out.println("</table>");
    out.println("<br>");
    tabulateList(out,filter(refs, Symbol.INTERFACE), "Interface");
    tabulateList(out,filter(refs, Symbol.CLASS), "Class");
    tabulateList(out,filter(refs, Symbol.STRUCT), "Struct");
    tabulateList(out,filter(refs, Symbol.ENUM), "Enumeration");
  }

  private void packageSummary(ArrayList pkgs, Set sids)
    throws CodeGenerationException
  {
    PrintWriter out = null;
    try {
      out = new PrintWriter
        (new FileWriter(d_context.getConfig().getOutputDirectory() +
                        File.separator + "package-summary.html"));
      writeHeader(out, false);
      out.println("<title>Package Summary</title>");
      out.println("</head>");
      out.println("<body>");
      printNavBar(out, new HashMap(), "Package");
      out.println("<hr>");
      for(Iterator i = pkgs.iterator(); i.hasNext(); ) {
        Package pkg = (Package)i.next();
        writePackage(out, pkg, sids);
      }
      out.println("<hr>");
      out.println("</body>");
      out.println("</html>");
    }
    catch (java.io.IOException ioe) {
      throw new CodeGenerationException
        ("Unable to create file \"package-summary.html\": " 
         + ioe.getMessage());
    }
    finally {
      if (out != null) {
        out.close();
      }
    }
  }

  private static class SymComp implements java.util.Comparator {
    public int compare(Object o1, Object o2)
    {
      final Symbol s1 = (Symbol)o1;
      final int t1 = s1.getSymbolType();
      final Symbol s2 = (Symbol)o2;
      final int t2 = s2.getSymbolType();
      if (t1 == t2) return s1.compareTo(s2);
      if ((t1 == Symbol.INTERFACE) || (t2 == Symbol.PACKAGE)) return -1;
      if ((t1 == Symbol.PACKAGE) || (t2 == Symbol.INTERFACE)) return 1;
      if ((t1 == Symbol.CLASS) || (t2 == Symbol.ENUM)) return -1;
      return 1;
    }

  };

  private Symbol getNonPackage(ArrayList list,
                               int ind)
  {
    if (ind < list.size()) {
      Symbol sym = (Symbol)list.get(ind);
      return (sym.getSymbolType() != Symbol.PACKAGE) ? sym : null;
    }
    return null;
  }

  private String packageName(SymbolID id)
  {
    String name = id.getFullName();
    int ind = name.lastIndexOf(SymbolID.SCOPE);
    if (ind >= 0) {
      name = name.substring(0, ind);
    }
    return name;
  }

  private void documentSymbol(Symbol sym,
                              Symbol prev,
                              Symbol next, 
                              Set    sids)
    throws CodeGenerationException
  {
    String filename = filename(sym);
    PrintWriter out = null;
    try {
      out = new PrintWriter
        (new FileWriter(d_context.getConfig().getOutputDirectory() +
                        File.separator + filename));
      writeHeader(out, false);
      out.println("<title>" + sym.getSymbolName() + "</title>");
      out.println("</head>");
      out.println("<body>");
      HashMap urls = new HashMap();
      if (prev != null) {
        urls.put("PREV", filename(prev));
      }
      if (next != null ) {
        urls.put("NEXT", filename(next));
      }
      urls.put("Package", "package-summary.html#" + packageName(sym));
      printNavBar(out, urls, "Class");
      out.println("<h2>" + sym.getSymbolTypeString() +  " " +
                  sym.getFullName() + "</h2>");
      if (sym instanceof Extendable) {
        documentExtendable(out,(Extendable)sym, sids);
      }
      else if (sym instanceof Enumeration) {
        documentEnumeration(out, (Enumeration)sym);
      }
      else if (sym instanceof Struct) {
        documentStruct(out, (Struct)sym, sids);
      }
      out.println("</body>");
      out.println("</html>");
    }
    catch (java.io.IOException ioe) {
      throw new CodeGenerationException("Unable to create file \"" + filename
                                        + "\": " + ioe.getMessage());
    }
    finally {
      if (out != null) {
        out.close();
      }
    }
  }

  private String linkSymbol(SymbolID id, Set sids)
  {
    if (sids.contains(id)) {
      return "<a href=\"" + filename(id) +
        "\">" +id.getFullName() + "</a>";
    }
    else {
      return id.getFullName();
    }
  }


  private void writeType(PrintWriter out,
                         Type t, 
                         Set sids)
  {
    switch(t.getDetailedType()) {
    case Type.ENUM:
    case Type.STRUCT:
    case Type.CLASS:
    case Type.INTERFACE:
      out.print(linkSymbol(t.getSymbolID(), sids));
      break;
    case Type.ARRAY:
      Type arrayType = t.getArrayType();
      if (arrayType == null){ 
        out.print("array&lt;&gt;");
      }
      else {
        if (t.isRarray()) {
          out.print("rarray&lt;");
          writeType(out, arrayType, sids);
          if (t.getArrayDimension() > 1) {
            out.print(", " + Integer.toString(t.getArrayDimension()));
          }
          out.print("&gt;");
        }
        else {
          out.print("array&lt;");
          writeType(out, arrayType, sids);
          if (t.getArrayDimension() > 1) {
            out.print(", " + Integer.toString(t.getArrayDimension()));
          }
          switch(t.getArrayOrder()) {
          case Type.COLUMN_MAJOR:
            out.print(", column-major");
            break;
          case Type.ROW_MAJOR:
            out.print(", row-major");
            break;
          }
          out.print("&gt;");
        }
      }
      break;
    default:
      out.print(t.getTypeString());
    }
  }

  private String rarrayIndices(Type t)
  {
    if (t.isRarray()) {
      StringBuffer buf = new StringBuffer();
      buf.append("(");
      Iterator i = t.getArrayIndexExprs().iterator();
      while (i.hasNext()) {
        AssertionExpression ae = (AssertionExpression)i.next();
        buf.append(ae.toString());
        if (i.hasNext()) {
          buf.append(", ");
        }
      }
      buf.append(")");
      return buf.toString();
    }
    else {
      return "";
    }
  }

  private static class MethComp implements  java.util.Comparator {
    public int compare(Object o1, Object o2)
    {
      Method m1 = (Method)o1;
      Method m2 = (Method)o2;
      return m1.getLongMethodName().compareTo(m2.getLongMethodName());
    }
  }

  private String methodName(Method m)
  {
    String ext = m.getNameExtension();
    return ((ext != null) && (ext.length() > 0)) ? 
      (m.getShortMethodName() + "[" + m.getNameExtension() + "]") :
      m.getShortMethodName();
  }


  private void methodSummary(PrintWriter out,
                             int index,
                             Method m,
                             Set sids)
  {
    out.println("  <tr class=\"TableRowColor\" bgcolor=\"#FFFFFF\">");
    out.print("    <td width=\"15%\" align=\"right\">");
    writeType(out, m.getReturnType(), sids);
    out.println("</td>");
    out.print("    <td><a href=\"#" + Integer.toString(index) +
              "\">" + methodName(m) + "</a>(");
    Iterator i = m.getArgumentListWithIndices().iterator();
    while (i.hasNext()) {
      Argument a = (Argument)i.next();
      out.print(a.getModeString() + " ");
      writeType(out, a.getType(), sids);
      out.print(" " + a.getFormalName() + rarrayIndices(a.getType()));
      if (i.hasNext()) {
        out.print(", ");
      }
    }
    out.println(")</td>");
    out.println("  </tr>");
  }

  private void methodDetail(PrintWriter out,
                            int index,
                            Method m,
                            Set sids)
  {
    out.println("  <tr class=\"TableRowColor\" bgcolor=\"#FFFFFF\">");
    out.print("    <td><p>");
    writeType(out, m.getReturnType(), sids);
    out.print(" <a name=\"" + Integer.toString(index) +
              "\"><b>" + methodName(m) + "</b></a>(");
    Iterator i = m.getArgumentListWithIndices().iterator();
    while (i.hasNext()) {
      Argument a = (Argument)i.next();
      out.print(a.getModeString() + " ");
      writeType(out, a.getType(), sids);
      out.print(" " + a.getFormalName() + rarrayIndices(a.getType()));
      if (i.hasNext()) {
        out.print(", ");
      }
    }
    out.println(")</p>");
    printComment(out, m.getComment());
    out.println("</td>");
    out.println("  </tr>");
  }

  private void documentExtendable(PrintWriter out,
                                  Extendable ext,
                                  Set sids)
  {
    final boolean isClass = ext instanceof Class;
    final boolean isInterface = !isClass;
    ArrayList parents = new ArrayList(ext.getParentInterfaces(isClass));
    if (isClass && ((Class)ext).getParentClass() != null ) {
      Class parent = ((Class)ext).getParentClass();
      if (parent != null) {
        out.println("<h2>extends " + linkSymbol(parent,sids) + "</h2>");
      }
    }
    if (!parents.isEmpty()) {
      if (isClass) {
        out.println("<h3>All implemented interfaces:</h3>");
        out.println("<p>");
      }
      else {
        out.println("<h2>extends ");
      }
    }
    Collections.sort(parents);
    Iterator i = parents.iterator();
    while (i.hasNext()) {
      out.print(linkSymbol((SymbolID)i.next(), sids));
      if (i.hasNext()) {
        out.print(", ");
      }
    }
    if (!parents.isEmpty()) {
      if (isInterface) {
        out.println("</h2>");
      }
      else {
        out.println("</p>");
      }
    }
    out.println("<ul>");
    if (ext.hasAttribute("source-url")) {
      out.println("  <li>Source: " +
                  ext.getAttribute("source-url") + "</li>");
    }
    out.println("  <li>Version: " +
                ext.getVersion().getVersionString() + "</li>");
    out.println("</ul>");
    out.println("<hr>");
    printComment(out, ext.getComment());
    ArrayList methods = new ArrayList(ext.getMethods(false));
    Collections.sort(methods, new MethComp());
    final int length = methods.size();
    out.println("<table width=\"100%\" cellpadding=\"2\" border=\"1\">");
    out.println("  <tr class=\"TableHeadingColor\" bgcolor=\"#CCCCFF\">");
    out.println("    <td  colspan=\"2\"><div class=\"TableHeadingFont\">");
    out.println("      Method Summary</div>");
    out.println("    </td>");
    out.println("  </tr>");
    for(int j = 0; j < length; ++j) {
      methodSummary(out, j, (Method)methods.get(j), sids);
    }
    out.println("</table><br>");
    out.println("<table width=\"100%\" cellpadding=\"2\" border=\"1\">");
    out.println("  <tr class=\"TableHeadingColor\" bgcolor=\"#CCCCFF\">");
    out.println("    <td><div class=\"TableHeadingFont\">");
    out.println("      Method Detail</div>");
    out.println("    </td>");
    out.println("  </tr>");
    for(int j = 0; j < length; ++j) {
      methodDetail(out, j, (Method)methods.get(j), sids);
    }
    out.println("</table><br><hr>");
  }

  private void documentEnumeration(PrintWriter out,
                                   Enumeration enm)
  {
    out.println("<ul>");
    if (enm.hasAttribute("source-url")) {
      out.println("  <li>Source: " +
                  enm.getAttribute("source-url") + "</li>");
    }
    out.println("  <li>Version: " +
                enm.getVersion().getVersionString() + "</li>");
    out.println("</ul>");
    out.println("<hr>");
    printComment(out, enm.getComment());
    out.println("<table width=\"100%\" cellpadding=\"2\" border=\"1\">");
    out.println("  <tr class=\"TableHeadingColor\" bgcolor=\"#CCCCFF\">");
    out.println("    <td><div class=\"TableHeadingFont\">Name</div>");
    out.println("    </td>");
    out.println("    <td><div class=\"TableHeadingFont\">Value</div>");
    out.println("    </td>");
    out.println("    <td><div class=\"TableHeadingFont\">User defined</div>");
    out.println("    </td>");
    out.println("    <td><div class=\"TableHeadingFont\">Comment</div>");
    out.println("    </td>");
    out.println("  </tr>");
    Iterator i = enm.getIterator();
    while (i.hasNext()) {
      String name = (String)i.next();
      int val = enm.getEnumeratorValue(name);
      boolean userDefined = enm.definedByUser(name);
      Comment com = enm.getEnumeratorComment(name);
      out.println("  <tr class=\"TableRowColor\" bgcolor=\"#FFFFFF\">");
      out.println("    <td>" + name + "</td>");
      out.println("    <td>" + Integer.toString(val) + "</td>");
      out.println("    <td>" + Boolean.toString(userDefined) + "</td>");
      out.println("    <td>");
      printComment(out, com);
      out.println("    </td>");
      out.println("  </tr>");
    }
    out.println("</table>");
    out.println("<hr>");
  }

  private void documentStruct(PrintWriter out,
                              Struct strct,
                              Set sids)
  {
    out.println("<ul>");
    if (strct.hasAttribute("source-url")) {
      out.println("  <li>Source: " +
                  strct.getAttribute("source-url") + "</li>");
    }
    out.println("  <li>Version: " + 
                strct.getVersion().getVersionString() + "</li>");
    out.println("</ul>");
    out.println("<hr>");
    printComment(out, strct.getComment());
    out.println("<table width=\"100%\" cellpadding=\"2\" border=\"1\">");
    out.println("  <tr class=\"TableHeadingColor\" bgcolor=\"#CCCCFF\">");
    out.println("    <td colspan=\"2\"><div class=\"TableHeadingFont\">");
    out.println("      Field Summary</div>");
    out.println("    </td>");
    out.println("  </tr>");
    out.println("  <tr class=\"TableHeadingColor\" bgcolor=\"#CCCCFF\">");
    out.println("    <td><div class=\"TableHeadingFont\">");
    out.println("      Type</div>");
    out.println("    </td>");
    out.println("    <td><div class=\"TableHeadingFont\">");
    out.println("      Name</div>");
    out.println("    </td>");
    out.println("  </tr>");
    for(Iterator i = strct.getItems().iterator(); i.hasNext(); ) {
      Struct.Item item = (Struct.Item)i.next();
      out.println("  <tr class=\"TableRowColor\" bgcolor=\"#FFFFFF\">");
      out.print("    <td>");
      writeType(out, item.getType(), sids);
      out.println("</td>");
      out.println("    <td>" + item.getName() +
                  rarrayIndices(item.getType()) + "</td>");
      out.println("  </tr>");
    }
    out.println("</table>");
    out.println("<hr>");
  }

  private void copyStream(String filename)
    throws CodeGenerationException
  {
    final String context = "gov/llnl/babel/backend/html/" + filename;
    ResourceLoader rl = new ResourceLoader();
    InputStream in = null;
    byte[] buffer = new byte[1024];
    int count;
    FileWriter out = null;
    try {
      try {
        out =new FileWriter(d_context.getConfig().getOutputDirectory() +
                            File.separator + filename);
        in = rl.getResourceStream(context);
        while ((count = in.read(buffer)) >= 0) {
          String str = new String(buffer, 0, count);
          out.write(str);
        }
      }
      finally {
        if (in != null) in.close();
        if (out != null) out.close();
      }
    }
    catch (java.io.IOException ioe) {
      throw new CodeGenerationException(ioe.getMessage());
    }
  }

  private void copyFixedContent()
    throws CodeGenerationException
  {
    copyStream("index.html");
    copyStream("stylesheet.css");
  }

  /**
   * Generate HTML documentation for each symbol identifier in the set
   * argument.  This routine assumes that all necessary symbol references
   * are available in the symbol table.
   */
  public void generateCode(Set symbols) throws CodeGenerationException {
    ArrayList sortedSymbols = new ArrayList
      (Utilities.convertIdsToSymbols(d_context, symbols));
    Collections.sort(sortedSymbols, new SymComp());
    copyFixedContent();
    writeList("allclasses-frame.html", sortedSymbols, true);
    writeList("allclasses-noframe.html", sortedSymbols, false);
    packageSummary(filter(sortedSymbols, Symbol.PACKAGE), symbols);
    final int length = sortedSymbols.size();
    if (length > 0) {
      Symbol prev = null;
      Symbol sym = null;
      Symbol next = getNonPackage(sortedSymbols, 0);
      for(int i = 0; i < length && (next != null); ++i) {
        prev = sym;
        sym = next;
        next = getNonPackage(sortedSymbols, i+1);
        documentSymbol(sym, prev, next, symbols);
      }
    }
  }


  public String getType() {
    return "text";
  }

  public boolean getUserSymbolsOnly() {
    return false;
  }

  public Set getLanguages() {
    Set result = new TreeSet();
    result.add("html");
    return result;
  }

  public void setName(String name)
    throws CodeGenerationException
  {
    if (!"html".equals(name)) {
      throw new CodeGenerationException
        ("\"" +name + "\" is not a valid name for the html generator.");
    }
  }


  public String getName() { return "html"; }

  public void setContext(Context context) {
    d_context = context;
  }
}
