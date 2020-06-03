//
// File:        ParseSymbolXML.java
// Package:     gov.llnl.babel.parsers.xml
// Revision:    @(#) $Id: ParseSymbolXML.java 7188 2011-09-27 18:38:42Z adrian $
// Description: convert sidl symbol XML/DOM into a sidl symbol object
//
// Copyright (c) 2000-2004, Lawrence Livermore National Security, LLC
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

import gov.llnl.babel.Context;
import gov.llnl.babel.parsers.xml.DTDManager;
import gov.llnl.babel.parsers.xml.ParseSymbolException;
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
import gov.llnl.babel.symbols.SymbolRedefinitionException;
import gov.llnl.babel.symbols.Type;
import gov.llnl.babel.symbols.UnaryExpression;
import gov.llnl.babel.symbols.Version;
import gov.llnl.babel.xml.ElementIterator;
import gov.llnl.babel.xml.XMLUtilities;
import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Class <code>ParseSymbolXML</code> converts an XML document into a SIDL
 * symbol.  Utility function <code>convert</code> takes either an input
 * stream or a validated DOM tree and generates a SIDL symbol.  Any errors
 * in format generate a <code>ParseSymbolException</code>.
 */
public class ParseSymbolXML {
   private static final String EOL = "\n"; // standard web end-of-line

   private Symbol d_symbol;

  private Context d_context;

   /**
    * This is a convenience utility function that converts an XML input
    * stream into a SIDL symbol.  Any errors detected in the XML input
    * are converted into a <code>ParseSymbolException</code>.  Since this
    * method is static, it may be called without explicity creating an
    * object.
    *
    * @param   is  The input stream to use for conversion.
    * @throws  gov.llnl.babel.parsers.xml.ParseSymbolException
    *              The exception raised if a parsing error is encountered.
    */
   public static Symbol convert(InputSource is, Context context) 
     throws ParseSymbolException 
  {
      ParseSymbolXML xml2sym = new ParseSymbolXML(is, context);
      return xml2sym.getSymbol();
   }

   /**
    * This is a convenience utility function that converts a DOM document
    * into a SIDL symbol.  This method assumes that the DOM document is a
    * valid symbol representation.  Any errors detected in the DOM input
    * are converted into a <code>ParseSymbolException</code>.  Since this
    * method is static, it may be called without explicity creating an
    * object.
    *
    * @param   doc  The DOM document to be converted.
    * @throws  gov.llnl.babel.parsers.xml.ParseSymbolException
    *               The exception raised if a parsing error is encountered.
    */
   public static Symbol convert(Document doc, Context context)
     throws ParseSymbolException 
  {
      ParseSymbolXML doc2sym = new ParseSymbolXML(doc, context);
      return doc2sym.getSymbol();
   }

   /**
    * Create an XML input stream to SIDL symbol converter object.  The
    * constructor parses the XML input stream and then converts the DOM
    * structure into a symbol.  The resulting symbol may be read by a call
    * to <code>getSymbol</code>.  Any errors will throw a parse symbol
    * exception.
    *
    * @param   is  The input stream to use for conversion.
    * @throws  gov.llnl.babel.parsers.xml.ParseSymbolException
    *              The exception raised if a parsing error is encountered.
    */
   public ParseSymbolXML(InputSource is,
                         Context context) 
     throws ParseSymbolException 
  {
    d_context = context;
      Document document = null;
      try {
         document = XMLUtilities.parse(is, DTDManager.getInstance());
      } catch (IOException ex) {
         error("ParseSymbolXML: IOException: " + ex.getMessage());
      } catch (SAXException ex) {
         error("ParseSymbolXML: SAXException: " + ex.getMessage());
      }
      parseSymbol(document.getDocumentElement());
   }

   /**
    * Create a DOM document to SIDL symbol converter object.  The DOM
    * document must be a valid representation of a SIDL symbol.  The
    * resulting symbol may be read by a call to <code>getSymbol</code>.
    *
    * @param   doc  The DOM document to be converted.
    * @throws  gov.llnl.babel.parsers.xml.ParseSymbolException
    *               The exception raised if a parsing error is encountered.
    */
   public ParseSymbolXML(Document doc,
                         Context context)
     throws ParseSymbolException 
  {
    d_context = context;
      parseSymbol(doc.getDocumentElement());
   }

   /**
    * Return the SIDL symbol for the XML or DOM given in the constructor.
    */
   public Symbol getSymbol() {
      return d_symbol;
   }

  private void parseAttributes(Element node, Attributes a)
    throws ParseSymbolException
  {
    if (hasChildElement(node, "Attributes")) {
      Element attr = getElement(node, "Attributes");
      Iterator i = new ElementIterator(attr, "Attribute");
      while (i.hasNext()) {
        Element item = (Element)i.next();
        String name = getAttribute(item, "name");
        String value = null;
        if (item.hasAttribute("value")) {
          value = item.getAttribute("value");
        }
        a.setAttribute(name, value);
      }
    }
  }


   /**
    * Parse the DOM element (which must be an element of type "Symbol") and
    * create a <code>Symbol</code> object as a data member of the class.
    *
    * @param   symbol  The Symbol DOM element to be parsed.
    * @throws  gov.llnl.babel.parsers.xml.ParseSymbolException
    *                  The exception raised if a parsing error is encountered.
    */
   private void parseSymbol(Element symbol) throws ParseSymbolException {
      if ((symbol == null) || !("Symbol".equals(symbol.getNodeName()))) {
         error("parseSymbol: No <Symbol> root element found in XML/DOM "
             + "document.");
      }

      SymbolID id = parseSymbolID(getElement(symbol, "SymbolName"));
      Comment  cm = parseComment (getElement(symbol, "Comment"   ));
      Metadata md = parseMetadata(getElement(symbol, "Metadata"  ));

      if (hasChildElement(symbol, "Class")) {
         d_symbol = new Class(id, cm, md, d_context);
         parseClass(getElement(symbol, "Class"));

      } else if (hasChildElement(symbol, "Enumeration")) {
         d_symbol = new Enumeration(id, cm, md, d_context);
         parseEnum(getElement(symbol, "Enumeration"));
         
      } else if (hasChildElement(symbol, "Interface")) {
         d_symbol = new Interface(id, cm, md, d_context);
         parseInterface(getElement(symbol, "Interface"));
         
      } else if (hasChildElement(symbol, "Package")) {
         d_symbol = new Package(id, cm, md, d_context);
         parsePackage(getElement(symbol, "Package"));
      } else if (hasChildElement(symbol, "Struct")) {
        d_symbol = new Struct(id, cm, md, d_context);
        parseStruct(getElement(symbol, "Struct"));
      } else {
        error("parseSymbol: Unknown SIDL symbol type in XML document.");
      }
   }

  private void parseStruct(Element element) throws ParseSymbolException {
    validateSymbolName(element, "Struct");
    Struct s = (Struct) d_symbol;
    parseAttributes(element, s);
    Iterator i = new ElementIterator(element, "StructItem");
    while (i.hasNext()) {
      s.addItem(parseStructItem((Element)i.next()));
    }
  }

  private Struct.Item parseStructItem(Element element) throws ParseSymbolException {
    validateSymbolName(element, "StructItem");
    String name = getAttribute(element, "name");
    Type t = parseType(getElement(element, "Type"));
    return new Struct.Item(name, t);
  }

   /**
    * Convert a DOM class element into a SIDL class symbol.  A class
    * description consists of an extends symbol, an implements block,
    * and a methods block.
    * 
    * @param   element  The DOM class element to be parsed.
    * @throws  gov.llnl.babel.parsers.xml.ParseSymbolException
    *                   The exception raised if a parsing error is encountered.
    */
   private void parseClass(Element element) throws ParseSymbolException {
      validateSymbolName(element, "Class");
      Class cls = (Class) d_symbol;
      parseAttributes(element, cls);

      /*
       * Parse the single optional extends symbol (which must be a class).
       */
      Element extends_element = getElement(element, "Extends");
      Iterator parent = new ElementIterator(extends_element, "SymbolName");
      if (parent.hasNext()) {
         SymbolID id = parseSymbolID((Element) parent.next());
         try {
            Symbol sym = d_context.getSymbolTable().resolveSymbol(id);
            if (sym == null) {
               error("parseClass: Class \"" + id.getSymbolName() 
                   + "\" not found.");
            } else if (sym.getSymbolType() != Symbol.CLASS) {
               error("parseClass: Symbol \"" + id.getSymbolName() 
                   + "\"  not a class.");
            } else {
               cls.setParentClass((Class) sym);
            }
         } catch (SymbolRedefinitionException ex) {
            error("parseClass: SymbolRedefinitionException: "+ex.getMessage());
         }
      }

      /*
       * Parse the implements block of interfaces.
       */
      parseParentInterfaceBlock(element, cls, false);

      /*
       * Note that we do not need to extract the "AllParentClasses" or
       * "AllParentInterfaces" elements from the DOM for code generation
       * purposes.
       */

      /*
       * Parse the methods block and add the methods to the class.
       */
      parseMethodsBlock(element, cls);

      /*
       * Parse the Contract, if present, and add the assertions to the 
       * invariant clause.
       */
      parseInvContractClause(element, cls);
   }

   /**
    * Convert a DOM enumeration element into a SIDL enumeration symbol.
    * Parse each of the enumerator elements and add the enumerator symbol
    * to the enumeration.
    *
    * @param   element  The DOM enumeration element to be parsed.
    * @throws  gov.llnl.babel.parsers.xml.ParseSymbolException
    *                   The exception raised if a parsing error is encountered.
    */
   private void parseEnum(Element element) throws ParseSymbolException {
      validateSymbolName(element, "Enumeration");
      Enumeration enm = (Enumeration) d_symbol;
      parseAttributes(element, enm);

      Iterator enumerators = new ElementIterator(element, "Enumerator");
      while (enumerators.hasNext()) {
         Element entry = (Element) enumerators.next();
         String  name  = getAttribute(entry, "name");
         String  sval  = getAttribute(entry, "value");
         boolean user  = getAttribute(entry, "fromuser").equals("true");
         Comment cmt   = null;
         if (hasChildElement(entry, "Comment")) {
           cmt = parseComment(getElement(entry, "Comment"));
         }
         try {
            enm.addEnumerator(name, Integer.parseInt(sval), user, cmt);
         } catch (NumberFormatException ex) {
            error("parseEnum: Invalid integer value \"" + sval
                + "\" for enumerated type.");
         }
      }
   }

   /**
    * Convert a DOM interface element into a SIDL interface symbol.
    * An interface description consists of an extends block and a
    * methods block.
    *
    * @param   element  The DOM interface element to be parsed.
    * @throws  gov.llnl.babel.parsers.xml.ParseSymbolException
    *                   The exception raised if a parsing error is encountered.
    */
   private void parseInterface(Element element) throws ParseSymbolException {
      validateSymbolName(element, "Interface");
      Interface ifc = (Interface) d_symbol;

      parseAttributes(element, ifc);
      /*
       * Parse the extends block consisting of a collection of interfaces.
       */
      parseParentInterfaceBlock(element, ifc, true);

      /*
       * Note that we do not need to extract the "AllParentInterfaces" 
       * elements from the DOM for code generation purposes.
       */

      /*
       * Parse the methods block and add the methods to the interface.
       */
      parseMethodsBlock(element, ifc);

      /*
       * Parse the Contract, if present, and add the assertions to the 
       * invariant clause.
       */
      parseInvContractClause(element, ifc);
   }

  /**
   * If the version attribute isn't specified, use the parent version.
   *
   * @param  version  The version, if supplied, to be used.
   * @param  parent   The <code>Version</code> to be used by default.
   */
  static private Version chooseVersion(String version, Version parent)
  {
    if (version == null) return parent;
    return new Version(version);
  }

   /**
    * Convert a DOM package element into a SIDL package symbol.  For each
    * of the sub-elements of package <code>PackageSymbol</code>, parse the
    * name and type and create a package entry.
    *
    * @param   element  The DOM package element to be parsed.
    * @throws  gov.llnl.babel.parsers.xml.ParseSymbolException
    *                   The exception raised if a parsing error is encountered.
    */
   private void parsePackage(Element element) throws ParseSymbolException {
      validateSymbolName(element, "Package");
      Package p = (Package) d_symbol;
      Version pkgVersion = p.getSymbolID().getVersion();
      parseAttributes(element, p);

      try {
        Iterator entries = new ElementIterator(element, "PackageSymbol");
        while (entries.hasNext()) {
          Element entry = (Element) entries.next();
          String name = getAttribute(entry, "name");
          String type = getAttribute(entry, "type");
          String version = getAttribute(entry, "version");

          p.addSymbol(new SymbolID (p.getScopedName(name), 
                                    chooseVersion(version, pkgVersion), true),
                      StringXML.fromSymbolXML(type));
        }
      } catch (NumberFormatException nfe) {
        error("parsePackage: Bad version string: " + nfe.getMessage() + ".");
      }
   }

   /**
    * Convert a comment DOM element into a SIDL comment object.
    * Comments consist of a number of comment lines.  This algorithm
    * takes the comment DOM, prints it into a string, and then extracts
    * the comment lines, ignoring the first and last lines, which contain
    * comment tags.
    * 
    * @param   comment  The DOM comment element to be parsed.
    * @throws  gov.llnl.babel.parsers.xml.ParseSymbolException
    *                   The exception raised if a parsing error is encountered.
    */
   private Comment parseComment(Element comment) throws ParseSymbolException {
      validateSymbolName(comment, "Comment");
      /* 
       * Note that it takes the XML encoded string and then encodes it once 
       * more in printing it out.  It actually gets encoded a few more times 
       * (don't know where) so I decode multiple times.
       */
      String s = XMLUtilities.decodeXMLString(XMLUtilities.getXMLString(comment));

      /*
       * Count the number of newlines in the generated text.
       */
      int nlines = 0;
      int index  = s.indexOf(EOL);
      while(index > 0) {
         nlines++;
         index = s.indexOf(EOL, index+1);
      }

      /*
       * If there are comment lines, create a comment line array and extract
       * the comment subsequences from the generated text.
       */
      String[] lines = null;
      if (nlines > 1) {
         lines = new String[nlines-1];
         int start_index = s.indexOf(EOL);
         for (int n = 0; n < nlines-1; n++) {
            int end_index = s.indexOf(EOL, start_index+1);
            lines[n] = s.substring(start_index+1, end_index);
            start_index = end_index;
         }
      }

      return new Comment(lines);
   }
   
   /**
    * Recover the metadata object from its DOM representation.  The element
    * name is <code>Metadata</code> with attribute <code>date</code>.  There
    * may be sub-elements that are (key,value) pairs.
    *
    * @param   m  The metadata DOM element to be converted.
    * @throws  gov.llnl.babel.parsers.xml.ParseSymbolException
    *             The exception raised if a parsing error is encountered.
    */
   private Metadata parseMetadata(Element m) throws ParseSymbolException {
      validateSymbolName(m, "Metadata");

      Metadata metadata = null;
      String date = getAttribute(m, "date");
      try {
         metadata = new Metadata(date);
      } catch (ParseException ex) {
         error("parseMetadata: Invalid date format \"" + date 
             + "\" in metadata element.");
      }

      Iterator entries = new ElementIterator(m, "MetadataEntry");
      while (entries.hasNext()) {
         Element entry = (Element) entries.next();
         String key = getAttribute(entry, "key");
         String val = getAttribute(entry, "value");
         metadata.addMetadata(key, val);
      }

      return metadata;
   }

   /**
    * Recover the parent interface block from its DOM representation.
    *
    * @param   e            The element block being read.
    * @param   ext          The extendable to which the parents are to be 
    *                       added.
    * @param   isInterface  TRUE if the block is associated with an interface;
    *                       FALSE otherwise (i.e., if associated with a class).
    * @throws  gov.llnl.babel.parsers.xml.ParseSymbolException
    *                       The exception raised if a parsing error is 
    *                       encountered.
    */
    private void parseParentInterfaceBlock(Element e, Extendable ext,
                                           boolean isInterface)
      throws ParseSymbolException
    {
      String name;
      if (isInterface) {
        name = "ExtendsBlock";
      } else {
        name = "ImplementsBlock";
      }
      Element  block   = getElement(e, name);
      Iterator parents = new ElementIterator(block, "SymbolName");
      while (parents.hasNext()) {
         SymbolID id = parseSymbolID((Element) parents.next());
         try {
            Symbol sym = d_context.getSymbolTable().resolveSymbol(id);
            if (sym == null) {
               error("parseParentInterfaceBlock: Interface \"" 
                   + id.getSymbolName() + "\" not found.");
            } else if (sym.getSymbolType() != Symbol.INTERFACE) {
               error("parseParentInterfaceBlock: Symbol \"" 
                   + id.getSymbolName() + "\"  not interface.");
            } else {
               ext.addParentInterface((Interface) sym);
            }
         } catch (SymbolRedefinitionException ex) {
            error("parseParentInterfaceBlock: SymbolRedefinitionException: "
                + ex.getMessage());
         }
      }
    }

  private void parseFromClause(Element fromClause, 
                               Extendable ext,
                               Method newMethod)
    throws ParseSymbolException
  {
    Method oldMethod = newMethod.cloneMethod();
    oldMethod.setMethodName(getAttribute(fromClause, "shortname"),
                            getAttribute(fromClause, "extension"));
    try {
      Version parentVersion = new Version(getAttribute(fromClause, "version"));
      SymbolID parentID = new SymbolID(getAttribute(fromClause, "parentname"), parentVersion, true);
      ext.addRenamedMethod(newMethod, oldMethod, parentID);
    }
    catch (NumberFormatException ex) {
      error("parseSymbolID: Invalid version format \"" + getAttribute(fromClause, "version")
            + "\" in symbol name.");
    }
  }

   /**
    * Recover the methods block from its DOM representation.  
    *
    * @param   e    The element block being read.
    * @param   ext  The extendable to which the methods are to be added.
    * @throws  gov.llnl.babel.parsers.xml.ParseSymbolException
    *               The exception raised if a parsing error is encountered.
    */
    private void parseMethodsBlock(Element e, Extendable ext)
      throws ParseSymbolException
    {
      Element  block   = getElement(e, "MethodsBlock");
      Iterator methods = new ElementIterator(block, "Method");
      while (methods.hasNext()) {
        Element method = (Element) methods.next();
        Method m = parseMethod(method, ext.getFullName());
        Element fromClause = XMLUtilities.lookupElement(method, "From");
        //        if (fromClause == null) {
        ext.addMethod(m);
        //}
        //else {
        if (fromClause != null) {
          parseFromClause(fromClause, ext, m);
        }
      }
    }

   /**
    * Recover the invariant contract (clause), if present, from its DOM 
    * representation adding its assertions to the extendable.
    *
    * Assumptions:
    * 1) The extendable implementation of addInvAssertions() will raise an
    *    AssertionException if the assertion is not an invariant.
    *
    * @param   e    The element block being read.
    * @param   ext  The extendable to which the invariant clause's assertions
    *               are to be added.
    * @throws  gov.llnl.babel.parsers.xml.ParseSymbolException
    *               The exception raised if a parsing error is encountered.
    */
   private void parseInvContractClause(Element e, Extendable ext)
      throws ParseSymbolException
   {
      if (hasChildElement(e, "Contract")) {
         Element  list = getElement(e, "Contract");
         Iterator iter = new ElementIterator(list, "Assertion");
         while (iter.hasNext()) {
            try {
               Assertion as  = parseAssertion((Element) iter.next(), 
                                              ext.getFullName());
               ext.addInvAssertion(as);
            } catch (AssertionException aex) {
               error("parseInvContractClause: AssertionException: "
                   + aex.getMessage());
            }
         }
      }
   }

   /**
    * Recover the <code>Method</code> object from its DOM representation.  
    *
    * @param  e     The method element being parsed.
    * @param  name  The fully qualified name of the extendable to which
    *               the method belongs.
    * @throws  gov.llnl.babel.parsers.xml.ParseSymbolException
    *               The exception raised if a parsing error is encountered.
    */
   private Method parseMethod(Element e, String name)
      throws ParseSymbolException 
   {
      validateSymbolName(e, "Method");
      Method method = new Method(d_context);

      parseAttributes(e, method);
      /*
       * Set method name, modifiers, return value, and comment
       */
      method.setMethodName(getAttribute(e, "shortname"), 
                           getAttribute(e, "extension"));
      method.setComment(parseComment(getElement(e, "Comment")));
      method.setReturnType(parseType(getElement(e, "Type")));

      /*
       * Parse the argument list from the XML document
       */
      Element arglist = getElement(e, "ArgumentList");
      Iterator args = new ElementIterator(arglist, "Argument");
      while (args.hasNext()) {
         method.addArgument(parseArgument((Element) args.next()));
      }
      args = method.getArgumentList().iterator();
      while (args.hasNext()) {
        Argument arg = (Argument)args.next();
        if (arg.getType().isRarray()) {
          if (arg.getMode() == Argument.OUT) {
            error("parseMethod: rarray " +
                  arg.getFormalName() + " in an out argument. Rarrays must be in or inout.");
          }
          method.addRarrayIndex(arg.getType().getArrayIndices());
        }
      }

      /*
       * Parse the throws clause from the XML document
       */
      Element throwslist = getElement(e, "ThrowsList");
      Iterator thrws = new ElementIterator(throwslist, "SymbolName");
      while (thrws.hasNext()) {
         method.addThrows(parseSymbolID((Element) thrws.next()));
      }

      Element implthrowslist = getElement(e, "ImplicitThrowsList");
      thrws = new ElementIterator(implthrowslist, "SymbolName");
      while (thrws.hasNext()) {
         method.addImplicitThrows(parseSymbolID((Element) thrws.next()));
      }


      /*
       * Parse the method's Contract, if present, and add any assertions to 
       * the appropriate precondition or postcondition clause.
       *
       * Assumption:
       * 1) It is assumed method's addClauseAssertion() will ensure the
       *    assertion is appropriate for a method and put it in
       *    the appropriate clause (i.e., precondition or postcondition).
       */
      if (hasChildElement(e, "Contract")) {
         Element  alist = getElement(e, "Contract");
         Iterator iter  = new ElementIterator(alist, "Assertion");
         while (iter.hasNext()) {
            try {
               Assertion as  = parseAssertion((Element) iter.next(), name);
               method.addClauseAssertion(as);
            } catch (AssertionException aex) {
               error("parseMethod: AssertionException: " + aex.getMessage());
            }
         }
      }

      return method;
   }

   /**
    * Convert a DOM argument element into a <code>Argument</code> object.
    *
    * @param   e  The DOM argument element to be parsed.
    * @throws  gov.llnl.babel.parsers.xml.ParseSymbolException
    *             The exception raised if a format error is encountered.
    */
   private Argument parseArgument(Element e) throws ParseSymbolException {
      validateSymbolName(e, "Argument");
      int     mode = StringXML.fromModeXML(getAttribute(e, "mode"));
      String  name = getAttribute(e, "name");
      Type    type = parseType(getElement(e, "Type"));
      Argument arg = new Argument(mode, type, name);
      parseAttributes(e, arg);
      return arg;
   }

   /**
    * Convert a DOM type element into a <code>Type</code> object.  
    *
    * @param   e  The DOM type element to be parsed.
    * @throws  gov.llnl.babel.parsers.xml.ParseSymbolException
    *             The exception raised if a format error is encountered.
    */
   private Type parseType(Element e) throws ParseSymbolException {
      validateSymbolName(e, "Type");
      int typeid = StringXML.fromTypeXML(getAttribute(e, "type"));

      Type type = null;
      if (typeid == Type.SYMBOL) {
         type = new Type(parseSymbolID(getElement(e, "SymbolName")), 
                         d_context);
      } else if (typeid == Type.ARRAY) {
         Element array = getElement(e, "Array");
         String sdim   = getAttribute(array, "dim"); // default "0"
         String sorder = getAttribute(array, "order");
         int dim       = 0;
         int order     = StringXML.fromOrderXML(sorder);
         // use lookupElement instead of getElement because "Type" is optional
         Element arrayType = XMLUtilities.lookupElement(array, "Type");
         Element index_elem = XMLUtilities.lookupElement(array, "Index");
         if (null != sdim) {
           try {
             dim = Integer.parseInt(sdim);
           } catch (NumberFormatException ex) {
             error("parseType: Invalid array dimension \"" + sdim + "\"");
           }
         }
         if (null != arrayType) {
           type = new Type(parseType(getElement(array, "Type")), dim, order,
                           d_context);
         } else {
           type = new Type(null, dim, order,
                           d_context); // dimensionless typeless array
         }
         //This if should only occur if the Array is an rarray, and has
         //index variables.
         if(index_elem != null) {
           Iterator iter = new ElementIterator(index_elem, "Expression");
           while (iter.hasNext()) {
             Element expr = (Element)iter.next();
             type.addArrayIndex(parseExpression(expr));
           }
         }
      } else {
         type = new Type(typeid);
      }

      return type;
   }

   /**
    * Convert a DOM symbol element into a <code>SymbolID</code> object.
    *
    * @param   id  The DOM symbol identifier element to be parsed.
    * @throws  gov.llnl.babel.parsers.xml.ParseSymbolException
    *              The exception raised if a format error is encountered.
    */
   private SymbolID parseSymbolID(Element id) throws ParseSymbolException {
      validateSymbolName(id, "SymbolName");
      String name = getAttribute(id, "name");
      String vers = getAttribute(id, "version");

      SymbolID sid = null;
      try {
        sid = new SymbolID(name, new Version(vers), true);
      } catch (NumberFormatException ex) {
         error("parseSymbolID: Invalid version format \"" + vers 
             + "\" in symbol name.");
      }
      return sid;
   }

   /**
    * Convert a DOM assertion element into a <code>Assertion</code> object.
    *
    * @param   e  The assertion element.
    * @throws  gov.llnl.babel.parsers.xml.ParseSymbolException
    *             The exception raised if there are any format or data errors
    *             associated with the assertion element.
    */
   private Assertion parseAssertion(Element e, String source) 
      throws ParseSymbolException 
   {
      validateSymbolName(e, "Assertion");
      Comment   cm   = parseComment (getElement(e, "Comment"));
      String    tag  = getAttribute(e, "tag");
      int       type = StringXML.fromAssertionXML(getAttribute(e, "type"));
      Assertion as   = null;
      try {
         Element             ae     = getElement(e, "Expression");
         AssertionExpression expr   = parseExpression(ae);
         as                         = new Assertion(type, source, tag, cm);
         as.setExpression(expr);
      } catch (AssertionException aex) {
         error("parseAssertion: AssertionException: " + aex.getMessage());
      }
      return as;
   }

   /**
    * Convert a DOM expression element into an <code>AssertionExpression</code>
    * object.  
    *
    * @param   e  The assertion expression element.
    * @throws  gov.llnl.babel.parsers.xml.ParseSymbolException
    *             The exception raise if there are any format or data
    *             errors associated with the assertion expression element.
    */
   private AssertionExpression parseExpression(Element e)
      throws ParseSymbolException
   {
      validateSymbolName(e, "Expression");
      AssertionExpression expr = null;

      boolean parens = getAttribute(e, "parens").equals("true");
      if (hasChildElement(e, "BinaryExpression")) {
         Element be = getElement(e, "BinaryExpression");
         expr       = (AssertionExpression) parseBinaryExpression(be, parens);
      } else if (hasChildElement(e, "ComplexNumber")) {
         Element cn = getElement(e, "ComplexNumber");
         expr       = parseComplexNumber(cn, parens);
      } else if (hasChildElement(e, "MethodCall")) {
         Element mc = getElement(e, "MethodCall");
         expr       = (AssertionExpression)parseMethodCall(mc, parens);
      } else if (hasChildElement(e, "Terminal")) {
         Element se = getElement(e, "Terminal");
         expr       = parseTerminal(se, parens);
      } else if (hasChildElement(e, "UnaryExpression")) {
         Element ue = getElement(e, "UnaryExpression");
         expr       = parseUnaryExpression(ue, parens);
      } else {
         error("parseExpression: Unable to locate a "
             + "recognizable/supported child element of an Assertion "
             + "Expression.");
      }

      return expr;
   }

   /**
    * Convert a DOM binary expression element into a 
    * <code>BinaryExpression</code> object.
    *
    * @param   e       The binary expression element.
    * @param   parens  If TRUE then the assertion expression was contained
    *                  in parentheses; otherwise, it was not.
    * @throws  gov.llnl.babel.parsers.xml.ParseSymbolException
    *                  The exception raise if there are any errors detected
    *                  with the binary expression or its components.
    */
   private BinaryExpression parseBinaryExpression(Element e, boolean parens)
      throws ParseSymbolException
   {
      validateSymbolName(e, "BinaryExpression");
      AssertionExpression lhs  = null;
      AssertionExpression rhs  = null;
      BinaryExpression    expr = null;
      int                 op  = StringXML.fromBinaryOpXML(getAttribute(e,"op"));
      Iterator            iter = new ElementIterator(e, "Expression");
      int                 num = 0;
      while (iter.hasNext()) {
        Element entry = (Element)iter.next();
        num           = num + 1;
        if (num == 1) {
          lhs = parseExpression(entry);
        } else if (num == 2) {
          rhs = parseExpression(entry);
        } else {
           error("parseBinaryExpression: Invalid number of assertion "
               + "expressions detected when attempting to parse a binary "
               + "expression.");
        }
      }

      if ( (lhs != null) && (rhs != null) ) {
        try {
          expr = new BinaryExpression(lhs, op, rhs, d_context);
          expr.setParens(parens);
        } catch (AssertionException aex) {
          error("parseBinaryExpression: AssertionException: "
              + aex.getMessage());
        }
      } else {
        error("parseBinaryExpression: either the LHS or RHS of the binary "
            + "expression did NOT get parsed");
      }

      return expr;
   }

   /**
    * Convert a DOM complex number element into the corresponding complex
    * literal object.
    *
    * @param   e       The complex number element.
    * @param   parens  If TRUE then the assertion expression was contained
    *                  in parentheses; otherwise, it was not.
    * @throws  gov.llnl.babel.parsers.xml.ParseSymbolException
    *                  The exception raise if there are any errors detected
    *                  with the complex number or its components.
    */
   private AssertionExpression parseComplexNumber(Element e, boolean parens)
      throws ParseSymbolException
   {
      validateSymbolName(e, "ComplexNumber");
      AssertionExpression expr = null;
      String              type = getAttribute(e, "type");
      String              real = getAttribute(e, "real");
      String              imag = getAttribute(e, "imaginary");
      try {
        if (type.equals("float")) {
          FloatLiteral    fr = new FloatLiteral(Float.valueOf(real), real, 
                                                d_context);
          FloatLiteral    fi = new FloatLiteral(Float.valueOf(imag), imag,
                                                d_context);
          FComplexLiteral f  = new FComplexLiteral(fr, fi, d_context);
          f.setParens(parens);
          expr = (AssertionExpression)f;
        } else if (type.equals("double")) {
          DoubleLiteral   dr = new DoubleLiteral(Double.valueOf(real), real,
                                                 d_context);
          DoubleLiteral   di = new DoubleLiteral(Double.valueOf(imag), imag,
                                                 d_context);
          DComplexLiteral d  = new DComplexLiteral(dr, di, d_context);
          d.setParens(parens);
          expr = (AssertionExpression)d;
        } else {
          error("parseComplexNumber: unrecongized or unsupported complex type "
              + type + " encountered.");
        }
      } catch (AssertionException aex) {
        error("parseComplexNumber: AssertionException: " + aex.getMessage());
      } catch (NumberFormatException nfe) {
        error("parseComplexNumber: Error parsing real (\"" + real + "\") or "
            + "imaginary (\"" + imag + "\") part: " + nfe.getMessage());
      }

      return expr;
   }

   /**
    * Convert a DOM method call element into a <code>MethodCall</code> object.
    *
    * @param   e       The method call element.
    * @param   parens  If TRUE then the assertion expression was contained
    *                  in parentheses; otherwise, it was not.
    * @throws  gov.llnl.babel.parsers.xml.ParseSymbolException
    *                  The exception raise if there are any errors detected
    *                  with the method call or its components.
    */
   private MethodCall parseMethodCall(Element e, boolean parens)
      throws ParseSymbolException
   {
      validateSymbolName(e, "MethodCall");
      String     name = getAttribute(e, "name");
      MethodCall expr = null;
      try {
        expr = new MethodCall(name, d_context);
        expr.setParens(parens);
        Iterator iter = new ElementIterator(e, "Expression");
        while (iter.hasNext()) {
          expr.addArgument(parseExpression((Element)iter.next()));
        }
      } catch (AssertionException aex) {
        error("parseMethodCall: AssertionException: " + aex.getMessage());
      }

      return expr;
   }

   /**
    * Convert a DOM simple expression element into the appropriate literal
    * or identifier object.
    *
    * @param   e       The simple expression element.
    * @param   parens  If TRUE then the assertion expression was contained
    *                  in parentheses; otherwise, it was not.
    * @throws  gov.llnl.babel.parsers.xml.ParseSymbolException
    *                  The exception raise if there are any errors detected
    *                  with the simple expression or its components.
    */
   private AssertionExpression parseTerminal(Element e, boolean parens)
      throws ParseSymbolException
   {
      validateSymbolName(e, "Terminal");
      AssertionExpression expr  = null;
      String              etype = getAttribute(e, "etype");
      String              value = getAttribute(e, "value");
      try {
        if ("identifier".equals(etype)) {
          expr = new IdentifierLiteral(value, d_context);
          expr.setParens(parens);
        } else {
          switch (StringXML.fromTypeXML(etype)) {
            case Type.BOOLEAN:
              expr = new BooleanLiteral(value.equals("true"), d_context);
              expr.setParens(parens);
              break;
            case Type.CHAR:
              expr = new CharacterLiteral(value.charAt(0), d_context);
              expr.setParens(parens);
              break;
            case Type.DOUBLE:
              expr = new DoubleLiteral(Double.valueOf(value), value, d_context);
              expr.setParens(parens);
              break;
            case Type.FLOAT:
              expr = new FloatLiteral(Float.valueOf(value), value, d_context);
              expr.setParens(parens);
              break;
            case Type.INT:
              expr = new IntegerLiteral(Integer.decode(value), value, d_context);
              expr.setParens(parens);
              break;
            case Type.LONG:
              expr = new LongLiteral(Long.decode(value), value, d_context);
              expr.setParens(parens);
              break;
            case Type.STRING:
              expr = new StringLiteral(value, d_context);
              expr.setParens(parens);
              break;
            case Type.SYMBOL:
              expr = new IdentifierLiteral(value, d_context);
              expr.setParens(parens);
              break;
            case Type.ARRAY:
              expr = new IdentifierLiteral(value, d_context);
              expr.setParens(parens);
              break;
            default:
              error("parseTerminal: Unsupported Type value \""
                  + etype + "\" encountered.");
              break;
          }
        }
      } catch (AssertionException aex) {
        error("parseTerminal: AssertionException: " + aex.getMessage());
      } catch (NumberFormatException nfe) {
        error("parseTerminal: NumberFormatException: "
            + nfe.getMessage());
      }

      return expr;
   }

   /**
    * Convert a DOM unary expression element into a 
    * <code>UnaryExpression</code> object.
    *
    * @param   e       The unary expression element.
    * @param   parens  If TRUE then the assertion expression was contained
    *                  in parentheses; otherwise, it was not.
    * @throws  gov.llnl.babel.parsers.xml.ParseSymbolException
    *                  The exception raise if there are any errors detected
    *                  with the unary expression or its components.
    */
   private UnaryExpression parseUnaryExpression(Element e, boolean parens)
      throws ParseSymbolException
   {
      validateSymbolName(e, "UnaryExpression");
      int             op    = StringXML.fromUnaryOpXML(getAttribute(e, "op"));
      UnaryExpression uexpr = null;
      try {
        AssertionExpression expr  = parseExpression(getElement(e,
                                                       "Expression"));
        uexpr                     = new UnaryExpression(op, expr, d_context);
        uexpr.setParens(parens);
      } catch (AssertionException aex) {
        error("parseUnaryExpression: AssertionException: " + aex.getMessage());
      }

      return uexpr;
   }

   /**
    * Return the specified element node from the parent element.  
    *
    * @param   parent  The DOM parent element.
    * @param   child   The DOM child element.
    * @throws  gov.llnl.babel.parsers.xml.ParseSymbolException
    *                  The exception raised if the child element does not exist.
    */
   private Element getElement(Element parent, String child)
      throws ParseSymbolException 
   {
      Element element = XMLUtilities.lookupElement(parent, child);
      if (element == null) {
         error("getElement: Child element \"" + child 
             + "\" not found in parent \"" + parent.getTagName() + "\".");
      }
      return element;
   }

   /**
    * Return TRUE if the element has a child of the specified name; otherwise,
    * return FALSE.
    *
    * @param   parent  The DOM parent element.
    * @param   child   The DOM child element.
    */
   private boolean hasChildElement(Element parent, String child) {
      return XMLUtilities.lookupElement(parent, child) != null;
   }

   /**
    * Validate the name of the element.  If the element name does not match
    * the specified name, then throw a <code>ParseSymbolException</code>.
    *
    * @param   e     The element in question.
    * @param   name  The name that is supposed to be associated with the 
    *                element.
    * @throws  gov.llnl.babel.parsers.xml.ParseSymbolException
    *                The exception raised if the name of the given element
    *                is not the one specified.
    */
   private void validateSymbolName(Element e, String name)
      throws ParseSymbolException 
   {
     if (e != null) {
       String tag = e.getTagName();
       if (!name.equals(tag)) {
         error("validateSymbolName: Invalid element name \"" + tag 
               + "\" (expected \""+name+"\").");
       }
     }
     else {
       error("validateSymbolName: null " + name + " element.");
     }
   }

   /**
    * Return the specified attribute.
    *
    * @param   e     The element whose attribute is to be extracted.
    * @param   attr  The name of the attribute to be extracted.
    * @throws  gov.llnl.babel.parsers.xml.ParseSymbolException
    *                The exception raised if the attribute is not associated
    *                with the element.
    */
   private String getAttribute(Element e, String attr)
      throws ParseSymbolException 
   {
      String value = e.getAttribute(attr);
      if (value == null) {
         error("getAttribute: Attribute \"" + attr + "\" not found in \"" 
             + e.getTagName() + "\".");
      }
      return value;
   }

   /**
    * Throw an exception of type <code>ParseSymbolException</code> based
    * on the argument message string.
    *
    * @param   message  The message to be used in the exception.
    * @throws  gov.llnl.babel.parsers.xml.ParseSymbolException
    *                   The exception raised if a parsing error is encountered.
    */
   private void error(String message) throws ParseSymbolException {
      throw new ParseSymbolException(message);
   }
}
