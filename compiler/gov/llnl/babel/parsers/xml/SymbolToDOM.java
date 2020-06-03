//
// File:        SymbolToDOM.java
// Package:     gov.llnl.babel.parsers.xml
// Revision:    @(#) $Id: SymbolToDOM.java 7188 2011-09-27 18:38:42Z adrian $
// Description: convert sidl symbols into an XML DOM document
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

package gov.llnl.babel.parsers.xml;

import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.Context;
import gov.llnl.babel.parsers.xml.DTDManager;
import gov.llnl.babel.parsers.xml.StringXML;
import gov.llnl.babel.symbols.Argument;
import gov.llnl.babel.symbols.Assertion;
import gov.llnl.babel.symbols.AssertionException;
import gov.llnl.babel.symbols.AssertionExpression;
import gov.llnl.babel.symbols.Attributes;
import gov.llnl.babel.symbols.BinaryExpression;
import gov.llnl.babel.symbols.BooleanLiteral;
import gov.llnl.babel.symbols.CharacterLiteral;
import gov.llnl.babel.symbols.Class;
import gov.llnl.babel.symbols.Comment;
import gov.llnl.babel.symbols.DComplexLiteral;
import gov.llnl.babel.symbols.DoubleLiteral;
import gov.llnl.babel.symbols.Enumeration;
import gov.llnl.babel.symbols.ExprVisitor;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.FComplexLiteral;
import gov.llnl.babel.symbols.FloatLiteral;
import gov.llnl.babel.symbols.IdentifierLiteral;
import gov.llnl.babel.symbols.IntegerLiteral;
import gov.llnl.babel.symbols.Interface;
import gov.llnl.babel.symbols.LongLiteral;
import gov.llnl.babel.symbols.Metadata;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.MethodCall;
import gov.llnl.babel.symbols.Package;
import gov.llnl.babel.symbols.StringLiteral;
import gov.llnl.babel.symbols.Struct;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.Type;
import gov.llnl.babel.symbols.UnaryExpression;
import gov.llnl.babel.xml.XMLUtilities;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

/**
 * Class <code>SymbolToDOM</code> converts a SIDL symbol into an XML DOM
 * document.  Utility function <code>convert</code>takes a symbol and returns
 * a DOM document.  The DOM representation is formatted with white space such
 * that a straight-forward DOM printer will generate pretty XML output.
 */
public class SymbolToDOM {
   private static final String EOL    = "\n";  // standard web end-of-line
   private static final String INDENT = "   "; // amount of each indent level

   private Document d_document;
   private int      d_indent;
  private Context d_context;

   /**
    * This is a convenience utility function that converts the symbol into
    * a DOM document.  Since this method is static, it may be called without
    * explicitly creating an instance of object <code>SymbolToDOM</code>.
    *
    * @param  symbol  The symbol to be converted.
    */
   public static Document convert(Symbol symbol, Context context) {
      SymbolToDOM sym2dom = new SymbolToDOM(symbol, context);
      return sym2dom.getDocument();
   }

   /**
    * This is a convenience utilility function that converts the symbol
    * directly into a string.
    *
    * @param  symbol  The symbol to be converted.
    */
   public static String convertToString(Symbol symbol, Context context) {
      return XMLUtilities.getXMLString(convert(symbol, context));
   }

  public String getDTDPath(String dtd) {
    String repoPath = d_context.getConfig().getRepositoryPath();
    for( StringTokenizer tokenizer = new StringTokenizer( repoPath, ";" );
         tokenizer.hasMoreTokens(); ) {
      String path = tokenizer.nextToken();
      if ( (path == null) || (path.equals("")) || (path.equals("null")) ) { 
        continue;
      }
      java.io.File sidlfile = new java.io.File(path+dtd);
      if(sidlfile.exists())
        return path;
    }
    
    return ""; //DTD doesn't exist in any repository directory
  }
  


   /**
    * Create a symbol to DOM converter object.  The constructor creates a
    * DOM document node with the appropriate document type to validate the
    * symbol input.  The resulting document may be read by a call to method
    * <code>getDocument</code>.
    *
    * @param  symbol  The symbol associated with an instance of this class.
    */
  public SymbolToDOM(Symbol symbol, Context context) {

    d_context = context;
    DOMImplementation dom = XMLUtilities.createDocBuilder().
      getDOMImplementation();
    DocumentType doctype = dom.createDocumentType("Symbol", DTDManager.SYMBOL_PUBLIC_ID, 
                                                  getDTDPath("sidl.dtd")+"sidl.dtd");
      d_document = dom.createDocument(null, "Symbol", doctype);

      if (symbol != null) {
         d_indent = 0;
         Element docsymbol = d_document.getDocumentElement();
         switch (symbol.getSymbolType()) {
         case Symbol.STRUCT:
           convertStruct((Struct) symbol, docsymbol);
           break;
         case Symbol.CLASS:
           convertClass((Class) symbol, docsymbol);
           break;
         case Symbol.ENUM:
           convertEnumeration((Enumeration) symbol, docsymbol);
           break;
         case Symbol.INTERFACE:
           convertInterface((Interface) symbol, docsymbol);
           break;
         case Symbol.PACKAGE:
           convertPackage((Package) symbol, docsymbol);
           break;
         }
         docsymbol.appendChild(d_document.createTextNode(EOL));
      }
   }

   /**
    * Return the DOM document for the symbol specified in the constructor.
    */
   public Document getDocument() {
      return d_document;
   }

  private void convertAttributes(Attributes a, Element parent)
  {
    Iterator i = a.getAttributes().iterator();
    if (i.hasNext()) {
      Element attributes_element = createElement("Attributes");
      while(i.hasNext()) {
        String key = (String)i.next();
        String value = a.getAttribute(key);
        Element elem = createElement("Attribute");
        elem.setAttribute("name", key);
        if (value != null) {
          elem.setAttribute("value", value);
        }
        appendChild(elem, attributes_element);
      }
      appendChild(attributes_element, parent);
    }
  }

   /**
    * Convert a SIDL class description into its DOM representation.
    * A class consists of an extends symbol, an implements block of
    * symbols, and a methods block.
    *
    * @param  cls     The class to be converted.
    * @param  parent  The parent node of the corresponding DOM representation.
    */
   private void convertClass(Class cls, Node parent) {
      convertSymbolID(cls.getSymbolID(), parent);
      convertMetadata(cls.getMetadata(), parent);
      convertComment (cls.getComment() , parent);

      Element class_element = createElement("Class");
      convertAttributes(cls, class_element);

      /*
       * Write the extends block of a single class identifier.
       */
      Element extends_element = createElement("Extends");
      if (cls.getParentClass() != null) {
         convertSymbolID(cls.getParentClass().getSymbolID(), extends_element);
      }
      appendChild(extends_element, class_element);

      /*
       * Write the implements block of symbol identifiers.
       */
      convertParentInterfaces(cls, class_element, false, false);

      /*
       * Write all parents of this class - all interfaces and classes.
       */
      Element all_classes_element = createElement("AllParentClasses");
      Class parent_class = cls.getParentClass();
      while (parent_class != null) {
         convertSymbolID(parent_class.getSymbolID(), all_classes_element);
         parent_class = parent_class.getParentClass();
      }
      appendChild(all_classes_element, class_element);
      
      convertParentInterfaces(cls, class_element, true, false);

      /*
       * Write the methods block of class methods.
       */
      convertMethods(cls, class_element);

      /*
       * Write the invariant contract clause.
       */
      convertInvClause(cls, class_element);
      
      appendChild(class_element, parent);
   }

   /**
    * Convert a SIDL enumeration symbol into its DOM representation.  An
    * enumeration consists of the standard symbol header followed by a list
    * of the enumerator symbols.
    *
    * @param  e       The enumaration to be converted.
    * @param  parent  The parent node of the corresponding DOM representation.
    */
   private void convertEnumeration(Enumeration e, Node parent) {
      convertSymbolID(e.getSymbolID(), parent);
      convertMetadata(e.getMetadata(), parent);
      convertComment (e.getComment() , parent);

      Element enumeration_element = createElement("Enumeration");
      convertAttributes(e, enumeration_element);
      for (Iterator i = e.getIterator(); i.hasNext(); ) {
         String n = (String) i.next();
         Element enumerator_element = createElement("Enumerator");
         enumerator_element.setAttribute("name", n);
         enumerator_element.setAttribute(
            "value", String.valueOf(e.getEnumeratorValue(n)));
         enumerator_element.setAttribute(
            "fromuser", String.valueOf(e.definedByUser(n)));
         Comment ecmt = e.getEnumeratorComment(n);
         if ((ecmt != null) && (!ecmt.isEmpty())) {
           convertComment(ecmt, enumerator_element);
         }
         appendChild(enumerator_element, enumeration_element);
      }
      appendChild(enumeration_element, parent);
   }

   /**
    * Convert a SIDL interface description into its DOM representation.
    * An interface consists of the standard symbol header followed by
    * an extends block and then a methods block.
    *
    * @param  ifc     The interface to be converted.
    * @param  parent  The parent node of the corresponding DOM representation.
    */
   private void convertInterface(Interface ifc, Node parent) {
      convertSymbolID(ifc.getSymbolID(), parent);
      convertMetadata(ifc.getMetadata(), parent);
      convertComment (ifc.getComment() , parent);

      Element interface_element = createElement("Interface");

      convertAttributes(ifc, interface_element);
      /*
       * Write the extends block of symbol identifiers.
       */
      convertParentInterfaces(ifc, interface_element, false, true);

      /*
       * Write all parent interfaces of this interface.
       */
      convertParentInterfaces(ifc, interface_element, true, true);

      /*
       * Write the methods block of interface methods.
       */
      convertMethods(ifc, interface_element);
      
      /*
       * Write the invariant assertions list.
       */
      convertInvClause(ifc, interface_element);
      
      appendChild(interface_element, parent);
   }

   /**
    * Convert a SIDL parent interface list into its appropriate DOM 
    * representation.
    *
    * @param  ext          The class or interface whose parents are being
    *                      converted.
    * @param  parent       The parent node of the corresponding DOM 
    *                      representation.
    * @param  doAll        TRUE if all parent interfaces are to be converted;
    *                      FALSE otherwise.
    * @param  isInterface  TRUE if ext is an interface; FALSE otherwise.
    */
   private void convertParentInterfaces(Extendable ext, Node parent,
                                        boolean doAll, boolean isInterface)
   {
      Element list_elem;
      if (doAll) {
         list_elem = createElement("AllParentInterfaces");
      } else {
         if (isInterface) {
            list_elem = createElement("ExtendsBlock");
         } else {
            list_elem = createElement("ImplementsBlock");
         }
      }
      Collection parents = ext.getParentInterfaces(doAll);
      for (Iterator p = parents.iterator(); p.hasNext(); ) {
         Interface i = (Interface) p.next();
         convertSymbolID(i.getSymbolID(), list_elem);
      }
      appendChild(list_elem, parent);
   }

   /**
    * Convert a SIDL methods list into its DOM representation.
    *
    * @param  ext     The class or interface whose methods are being converted.
    * @param  parent  The parent node of the corresponding DOM representation.
    */
   private void convertMethods(Extendable ext, Node parent) {
      Element methodblock_element = createElement("MethodsBlock");
      for (Iterator m = ext.getMethods(false).iterator(); m.hasNext(); ) {
         convertMethod((Method) m.next(), ext, methodblock_element);
      }
      appendChild(methodblock_element, parent);
   }

   /**
    * Convert a SIDL invariants clause, if present, into its DOM representation.
    *
    * @param  ext     The class or interface whose invariants clause is being 
    *                 converted.
    * @param  parent  The parent node of the corresponding DOM representation.
    */
   private void convertInvClause(Extendable ext, Node parent) {
      try {
        convertContract(ext.getInvClause(), parent);
      } catch (CodeGenerationException cge) {
        /*
         * ToDo:  What should we do with this if is is raised?
         *   It should only happen if we have to get invariants
         *   from parent(s).
         */
      }
   }

   /**
    * Convert a SIDL package symbol into its DOM representation.  A package
    * consists of the standard symbol header followed by a list of the package
    * contents.
    *
    * @param  p       The package to be converted.
    * @param  parent  The parent node of the corresponding DOM representation.
    */
   private void convertPackage(Package p, Node parent) {
      convertSymbolID(p.getSymbolID(), parent);
      convertMetadata(p.getMetadata(), parent);
      convertComment (p.getComment() , parent);

      Element package_element = createElement("Package");
      convertAttributes(p, package_element);
      Collection children = p.getOrderedSymbolReferences();
      Map map = p.getSymbols();
      for (Iterator i = children.iterator(); i.hasNext(); ) {
         SymbolID entry = (SymbolID) i.next();
         Integer intValue =(Integer)map.get(entry);
         Element packagesymbol_element = createElement("PackageSymbol");
         packagesymbol_element.setAttribute(
            "name", entry.getShortName());
         packagesymbol_element.setAttribute
           ("type", StringXML.toSymbolXML(intValue.intValue()));
         packagesymbol_element.setAttribute
           ("version", entry.getVersion().getVersionString());
                                           
         appendChild(packagesymbol_element, package_element);
      }
      appendChild(package_element, parent);
   }

   /**
    * Convert a SIDL comment into its DOM representation.  Comments
    * consist of a number of comment lines.  Comments may contain XML
    * formatting elements if the comment is well-formed and follows
    * the comment DTD.
    *
    * @param  c       The comment to be converted.
    * @param  parent  The parent node of the corresponding DOM representation.
    */
   private void convertComment(Comment c, Node parent) {
      Element comment_element = null;
      /*
       * If the comment is empty, then return an empty comment element.
       */
      
      if ((null == c) || (c.isEmpty())) {
         comment_element = createElement("Comment");
      } else {
         String[] lines = c.getComment();

        /*
         * First, check whether the comment is a well-formed as defined
         * by the comment DTD.  If the comment DTD does not exist or if
         * the comment is not valid, then write the comment as a text node.
         */

        StringBuffer buffer = new StringBuffer();
        buffer.append("<Comment>");
        for (int i = 0; i < lines.length; i++) {
          buffer.append(EOL);
          buffer.append(lines[i]);
        }
        buffer.append("</Comment>");

        Document document = XMLUtilities.validateXML
          (DTDManager.COMMENT_PUBLIC_ID, getDTDPath(DTDManager.COMMENT_FILE)+DTDManager.COMMENT_FILE,
           DTDManager.getInstance(), "Comment", buffer.toString());

        if (document != null) {
          Element root = document.getDocumentElement();
          comment_element =
            (Element) XMLUtilities.cloneDOM(root, d_document);
          d_indent++;
        }

        /*
         * If we have not been able to create a valid comment element as
         * a DOM structure, then create a text comment node.
         */
         
        if (comment_element == null) {
          comment_element = createElement("Comment");
          Text text = d_document.createTextNode("");
          for (int i = 0; i < lines.length; i++) {
            text.appendData(EOL);
            text.appendData(lines[i]);
          }
          comment_element.appendChild(text);
        }
      }

      appendChild(comment_element, parent);
   }

   /**
    * Convert metadata into its DOM representation.  The element name is
    * <code>Metadata</code> with attribute <code>date</code>.  There may
    * sub-elements that are (key,value) pairs.
    *
    * @param  m       The metadata to be converted.
    * @param  parent  The parent node of the corresponding DOM representation.
    */
   private void convertMetadata(Metadata m, Node parent) {
      Element metadata_element = createElement("Metadata");
      if ( d_context.getConfig().suppressTimestamps() ) {
        metadata_element.setAttribute("date", "20010101 00:00:00 GMT");
      } else { 
        metadata_element.setAttribute("date", m.getDateAsString());
      }

      Set entries = m.getMetadataDatabase().entrySet();
      for (Iterator i = entries.iterator(); i.hasNext(); ) {
         Map.Entry entry = (Map.Entry) i.next();
         Element data_element = createElement("MetadataEntry");
         data_element.setAttribute("key", (String) entry.getKey());
         data_element.setAttribute("value", (String) entry.getValue());
         appendChild(data_element, metadata_element);
      }
      appendChild(metadata_element, parent);
   }

   /**
    * Convert a SIDL method description into a DOM representation.
    * The element name is <code>Method</code> and contains a comment,
    * return type, argument list, and throws list.
    *
    * @param  m       The method to be converted.
    * @param  ext     To determine if method requires a from clause
    * @param  parent  The parent node of the corresponding DOM representation.
    */
   private void convertMethod(Method m, Extendable ext, Node parent) {
      /*
       * Create a method node and add attributes
       */
      Element method_element = createElement("Method");
      method_element.setAttribute("shortname", m.getShortMethodName());
      method_element.setAttribute("extension", m.getNameExtension());
      convertAttributes(m, method_element);

      /*
       * Create the comment and return type elements
       */
      convertComment(m.getComment(), method_element);
      convertType(m.getReturnType(), method_element);

      /*
       * Create the argument list element
       */
      Element arglist_element = createElement("ArgumentList");
      List args = m.getArgumentListWithIndices();
      for (Iterator a = args.iterator(); a.hasNext(); ) {
         convertArgument((Argument) a.next(), arglist_element);
      }
      appendChild(arglist_element, method_element);

      /*
       * Create the explicit throws list element
       */
      Element throwslist_element = createElement("ThrowsList");
      for (Iterator t = m.getExplicitThrows().iterator(); t.hasNext(); ) {
         convertSymbolID((SymbolID) t.next(), throwslist_element);
      }
      appendChild(throwslist_element, method_element);

      /*
       * Create the implicit throws list element
       */
      Element implthrowslist_element = createElement("ImplicitThrowsList");
      for (Iterator t = m.getImplicitThrows().iterator(); t.hasNext(); ) {
         convertSymbolID((SymbolID) t.next(), implthrowslist_element);
      }
      appendChild(implthrowslist_element, method_element);

      Method renamedMethod = ext.getRenamedMethod(m);
      if (renamedMethod != null) {
        Element fromClause = createElement("From");
        SymbolID parentID = ext.getRenamedMethodSymbolID(renamedMethod);
        fromClause.setAttribute("shortname", renamedMethod.getShortMethodName());
        fromClause.setAttribute("extension", renamedMethod.getNameExtension());
        fromClause.setAttribute("parentname", parentID.getFullName());
        fromClause.setAttribute("parentversion", parentID.getVersion().getVersionString());
        appendChild(fromClause, method_element);
      }

      /*
       * Create the lists of preconditions followed by postconditions, if any.
       */
      convertContract(m, method_element);

      appendChild(method_element, parent);
   }

   /**
    * Convert a SIDL argument description into a DOM representation.
    * The element name is <code>Argument</code> with the attributes
    * <code>copy</code>, <code>mode</code>, and <code>name</code>.
    *
    * @param  arg     The argument to be converted.
    * @param  parent  The parent node of the corresponding DOM representation.
    */
   private void convertArgument(Argument arg, Node parent) {
      Element arg_element = createElement("Argument");
      arg_element.setAttribute("mode", StringXML.toModeXML(arg.getMode()));
      arg_element.setAttribute("name", arg.getFormalName());
      convertAttributes(arg, arg_element);
      convertType(arg.getType(), arg_element);
      appendChild(arg_element, parent);
   }


  /**
   * Convert a SIDL struct description into a DOM reprentation.
   */
  private void convertStruct(Struct s, Node parent) {
    convertSymbolID(s.getSymbolID(), parent);
    convertMetadata(s.getMetadata(), parent);
    convertComment (s.getComment() , parent);
    Element struct_element = createElement("Struct");
    convertAttributes(s, struct_element);
    Iterator i = s.getItems().iterator();
    while(i.hasNext()) {
      Struct.Item item = (Struct.Item)i.next();
      convertStructItem(item, struct_element);
    }
    appendChild(struct_element, parent);
  }

  private void convertStructItem(Struct.Item item, Node parent)
  {
    Element struct_item = createElement("StructItem");
    struct_item.setAttribute("name", item.getName());
    convertType(item.getType(), struct_item);
    appendChild(struct_item, parent);
  }

   /**
    * Convert a SIDL type description into a DOM representation.  The
    * element is <code>Type</code> with attribute <code>type</code>.
    *
    * @param  type    The type to be converted.
    * @param  parent  The parent node of the corresponding DOM representation.
    */
   private void convertType(Type type, Node parent) {
      Element type_element = createElement("Type");
      int typeid = type.getType();
      type_element.setAttribute("type", StringXML.toTypeXML(typeid));

      if (typeid == Type.SYMBOL) {
         convertSymbolID(type.getSymbolID(), type_element);
      } else if (typeid == Type.ARRAY) {
         Element array_element = createElement("Array");
         array_element.setAttribute(
            "order", StringXML.toOrderXML(type.getArrayOrder()));
         if (null != type.getArrayType()) {
           array_element.setAttribute("dim", 
                                      String.valueOf(type.getArrayDimension()));
           convertType(type.getArrayType(), array_element);
           //Here's a little hack to slip in rarray support in xml
           //If an Index element exists, this array is an rarray
           //and index element contains a comma separated list of the index
           //variables for this rarray.
           if(type.isRarray()) {
             Element index_element = createElement("Index");
             Iterator i = type.getArrayIndexExprs().iterator();
             while (i.hasNext()) {
               AssertionExpression ae = (AssertionExpression)i.next();
               convertExpr(ae, index_element);
             }
             appendChild(index_element, array_element);
           }
         }
         appendChild(array_element, type_element);
 
      }

      appendChild(type_element, parent);
   }

   /**
    * Convert a symbol identifier into a DOM representation.  The element
    * name is <code>SymbolName</code> with attributes <code>name</code>
    * and <code>version</code>.
    *
    * @param  id      The symbol to be converted.
    * @param  parent  The parent node of the corresponding DOM representation.
    */
   private void convertSymbolID(SymbolID id, Node parent) {
      Element symbol_element = createElement("SymbolName");
      symbol_element.setAttribute("name", id.getFullName());
      symbol_element.setAttribute(
         "version", id.getVersion().getVersionString());
      appendChild(symbol_element, parent);
   }

   /**
    * Convert the method's precondition and postcondition clauses into
    * their DOM representation.
    *
    * @param  meth    The method whose contracts are being converted.
    * @param  parent  The parent node of the corresponding DOM representation.
    */
   private void convertContract(Method meth, Node parent) {
      LinkedList list = new LinkedList(meth.getPreClause());
      list.addAll(meth.getPostClause());
      convertContract(list, parent);
   }

   /**
    * Convert a contract [clause], specified as a <code>List</code> of 
    * assertions, into its DOM representation.
    *
    * @param  list    The list of SIDL <code>Assertion</code> entries
    *                 that form the basis for the contract [clause].
    * @param  parent  The parent node of the corresponding DOM representation.
    */
   private void convertContract(List list, Node parent) {
      if (!list.isEmpty()) {
         Element list_elem = createElement("Contract");
         for (Iterator i = list.iterator(); i.hasNext(); ) {
            convertAssertion((Assertion) i.next(), list_elem);
         }
         appendChild(list_elem, parent);
      }
   }

   /**
    * Convert an <code>Assertion</code> into its DOM representation.
    *
    * @param  as      The <code>Assertion</code> to be converted.
    * @param  parent  The parent node of the corresponding DOM representation.
    */
   private void convertAssertion(Assertion as, Node parent) {
      if (as != null) {
         Element e = createElement("Assertion");
         e.setAttribute("tag", as.getTag());
         e.setAttribute("type", StringXML.toAssertionXML(as.getType()));
         convertComment(as.getComment(), e);
         try {
           convertAssertionExpression(as.getExpression(), e);
         } catch (AssertionException ex) {
           throw new RuntimeException(ex);
         }

         appendChild(e, parent);
      }
   }

  private class ExprToDOM extends ExprVisitor {

    
    public Object visitBinaryExpression(BinaryExpression be, Object parent) {
      Element e = createElement("BinaryExpression");
      e.setAttribute("op", StringXML.toBinaryOpXML(be.getOp()));
      Element child = createElement("Expression");
      be.getLeftExpression().accept(this, child);
      appendChild(child, e);
      child = createElement("Expression");
      be.getRightExpression().accept(this, child);
      appendChild(child, e);
      appendChild(e, (Node)parent);
      return parent;
    }
  
    public Object visitBooleanLiteral(BooleanLiteral bl, Object parent) {
      Element e  = createElement("Terminal");
      e.setAttribute("etype", StringXML.toTypeXML(Type.BOOLEAN));
      e.setAttribute("value", bl.getValue() ? "true" : "false");
      appendChild(e, (Node)parent);
      return parent;
    }

    public Object visitCharacterLiteral(CharacterLiteral cl, Object parent) {
      Element e  = createElement("Terminal");
      e.setAttribute("etype", StringXML.toTypeXML(Type.CHAR));
      e.setAttribute("value", String.valueOf(cl.getValue()));
      appendChild(e, (Node)parent);
      return parent;
    }
    
    public Object visitDComplexLiteral(DComplexLiteral dcl, Object parent) {
      Element e  = createElement("ComplexNumber");
      e.setAttribute("type", "float");
      e.setAttribute("real", dcl.getRealImage());
      e.setAttribute("imaginary", dcl.getImaginaryImage());

      appendChild(e, (Node)parent);
      return parent;
    }

    public Object visitDoubleLiteral(DoubleLiteral dl, Object parent) {
      Element e  = createElement("Terminal");
      e.setAttribute("etype", StringXML.toTypeXML(Type.DOUBLE));
      e.setAttribute("value", dl.getImage());
      appendChild(e, (Node)parent);
      return parent;
    }
  
    public Object visitFComplexLiteral(FComplexLiteral fcl, Object parent) {
      Element e  = createElement("ComplexNumber");
      e.setAttribute("type", "float");
      e.setAttribute("real", fcl.getRealImage());
      e.setAttribute("imaginary", fcl.getImaginaryImage());
      appendChild(e, (Node)parent);
      
      return parent;
    }

    public Object visitFloatLiteral(FloatLiteral fl, Object parent) {
      Element e  = createElement("Terminal");
      e.setAttribute("etype", StringXML.toTypeXML(Type.FLOAT));
      e.setAttribute("value", fl.getImage());

      appendChild(e, (Node)parent);
      return parent;
    }

    public Object visitIdentifierLiteral(IdentifierLiteral il, Object parent) {
      Element e  = createElement("Terminal");
      e.setAttribute("etype", "identifier");
      e.setAttribute("value", il.getIdentifier());
      appendChild(e, (Node)parent);
      return parent;
    }

    public Object visitIntegerLiteral(IntegerLiteral il, Object parent) {
      Element e  = createElement("Terminal");
      e.setAttribute("etype", StringXML.toTypeXML(Type.INT));
      e.setAttribute("value", il.getImage());
      appendChild(e, (Node)parent);
      return parent;
    }

    public Object visitLongLiteral(LongLiteral ll, Object parent) {
      Element e  = createElement("Terminal");
      e.setAttribute("etype", StringXML.toTypeXML(Type.LONG));
      e.setAttribute("value", ll.getImage());
      appendChild(e, (Node)parent);
      return parent;
    }

    public Object visitMethodCall(MethodCall mc, Object parent) {
      Element e  = createElement("MethodCall");
      e.setAttribute("name", mc.getMethodName());
      Iterator i = mc.getArguments().iterator();
      while (i.hasNext()) {
        AssertionExpression ae = (AssertionExpression)i.next();
        Element child = createElement("Expression");
        ae.accept(this, child);
        appendChild(child, e);
      }
      appendChild(e, (Node)parent);
      return parent;
    }

    public Object visitStringLiteral(StringLiteral sl, Object parent) {
      Element e  = createElement("Terminal");
      e.setAttribute("etype", StringXML.toTypeXML(Type.STRING));
      e.setAttribute("value", sl.getValue());
      appendChild(e, (Node)parent);
      return parent;
    }

    public Object visitUnaryExpression(UnaryExpression ue, Object parent) {
      Element e  = createElement("UnaryExpression");
      e.setAttribute("op", StringXML.toUnaryOpXML(ue.getOp()));
      Element child = createElement("Expression");
      ue.getExpression().accept(this,child);
      appendChild(child, e);
      appendChild(e, (Node)parent);
      return parent;
    }
  }

  public Node convertExpr(AssertionExpression ae, Node parent) {
    Element e = createElement("Expression");
    ae.accept(new ExprToDOM(), e);
    appendChild(e, parent);
    return e;
  }


   /**
    * Convert an <code>AssertionExpression</code> into its DOM representation.
    *
    * @param  ae      The <code>AssertionExpression</code> to be converted.
    * @param  parent  The parent node of the corresponding DOM representation.
    * @throws gov.llnl.babel.symbols.AssertionException
    *                 The exception raised if the assertion expression has
    *                 not been validated.
    */
   private void convertAssertionExpression(AssertionExpression ae, Node parent)
      throws AssertionException
   {
     if (ae != null) {
       if (ae.isValid()) {
         convertExpr(ae, parent);
       } else {
         throw new AssertionException("convertAssertionExpression: Cannot "
                        + "perform conversion on the unvalidated assertion "
                        + "expression \"" + ae.toString() + "\".");
       }
     }
   }

   /**
    * Return the indent string, which is a character return plus spaces that
    * put the cursor at the appropriate indentation level.
    */
   private String getIndentString() {
      StringBuffer indent = new StringBuffer();
      indent.append(EOL);
      for (int i = 0; i < d_indent; i++) {
         indent.append(INDENT);
      }
      return indent.toString();
   }

   /**
    * Create a new element and increase the indentation level.
    *
    * @param  name  The name of the DOM element to be created.
    */
   private Element createElement(String name) {
      d_indent++;
      return d_document.createElement(name);
   }

   /**
    * Append the child to the parent with the proper formatting.
    *
    * @param  child   The child element to be appended.
    * @param  parent  The parent node of the corresponding DOM representation.
    */
   private void appendChild(Element child, Node parent) {
      String indent = getIndentString();
      parent.appendChild(d_document.createTextNode(indent));
      if (child.hasChildNodes()) {
         child.appendChild(d_document.createTextNode(indent));
      }
      parent.appendChild(child);
      d_indent--;
   }
}
