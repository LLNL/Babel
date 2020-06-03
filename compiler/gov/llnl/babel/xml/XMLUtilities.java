//
// File:        XMLUtilities.java
// Package:     gov.llnl.babel.xml
// Revision:    @(#) $Id: XMLUtilities.java 7188 2011-09-27 18:38:42Z adrian $
// Description: a collection of common XML utility functions
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

package gov.llnl.babel.xml;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Utility class <code>XMLUtiliites</code> is a collection of common
 * XML utility functions.  All methods are declared as static.  This
 * class is the primary interface to the XML parsers.  The current
 * implementation uses the Xerces parsers from the Apache project.
 */
public class XMLUtilities {
   private final static String EOL = "\n";

  private static DocumentBuilderFactory s_factory = null;

  static {
    try {
      s_factory = 
       DocumentBuilderFactory.newInstance();
    }
    catch (FactoryConfigurationError e) {
    }
  }

  public static DocumentBuilder createDocBuilder() {
    try {
      s_factory.setValidating(true);
      DocumentBuilder db = s_factory.newDocumentBuilder();
      db.setErrorHandler(new ExceptionErrorHandler());
      return db;
    }
    catch (ParserConfigurationException e) {
    }
    return null;
  }

   /**
    * Parse and validate the XML document represented by the string URI.
    * An <code>IOException</code> will be thrown if the supplied URI is
    * not valid.  A <code>SAXException</code> will be thrown if there was
    * an error parsing the XML.  The return value is the DOM representation
    * of the parsed document.
    */
   public static Document parse(String uri) throws IOException, SAXException {
     DocumentBuilder db = createDocBuilder();
     return db.parse(uri);
   }

   /**
    * Parse and validate the XML document represented by the specified
    * input source.  The entity resolver is used to redirect public and
    * system identifiers.  An <code>IOException</code> will be thrown
    * if the input source is not valid and a <code>SAXException</code>
    * will be thrown if there is an error parsing the XML.  The return
    * value is the DOM representation of the parsed document.
    */
   public static Document parse(InputSource is, EntityResolver er)
         throws IOException, SAXException {
     DocumentBuilder db = createDocBuilder();
     db.setEntityResolver(er);
     return db.parse(is);
   }

   /**
    * Check whether the XML fragment is well-formed.  No validation is done
    * using a DTD.  If no error is detected, then the DOM tree is returned.
    * Null is returned if any error or warning is detected during the parse.
    */
   public static Document checkXML(String frag) {
      StringBuffer text = new StringBuffer();
      text.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+EOL);
      text.append(frag);

      Document document = null;
      try {
        DocumentBuilder db = createDocBuilder();
        InputSource is = new InputSource(new StringReader(text.toString()));
        document = db.parse(is);
      } catch (SAXException ex) {
      } catch (IOException ex) {
      }

      return document;
   }

   /**
    * Validate the provided XML fragment with the specified start element
    * against the DTD public identifier.  If no error is detected, then the DOM tree is
    * returned.  Null is returned any error or warning is detected during
    * the parsing and validation.
    */
   public static Document validateXML(String dtdPublic, String dtdFile,
                                      EntityResolver er,
                                      String start, String frag) {
      StringBuffer text = new StringBuffer();
      text.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+EOL);
      text.append("<!DOCTYPE " + start + " PUBLIC \"" + dtdPublic + "\" " +
                  "\"" + dtdFile + "\">"+EOL);
      text.append(frag);

      Document document = null;
      try {
        DocumentBuilder db = createDocBuilder();
        InputSource is = new InputSource(new StringReader(text.toString()));
        document = db.parse(is);
      } catch (SAXException ex) {
      } catch (IOException ex) {
      }

      return document;
   }

   /**
    * Normalize an XML string by replacing less than, greater than,
    * ampersand, and double quote by their XML entity representatives.
    */
   public static String encodeXMLString(String s) {
      StringBuffer buffer = new StringBuffer();

      int length = (s == null ? 0 : s.length());
      for (int i = 0; i < length; i++) {
         char ch = s.charAt(i);
         switch (ch) {
         case '<':
            buffer.append("&lt;");
            break;
         case '>':
            buffer.append("&gt;");
            break;
         case '&':
            buffer.append("&amp;");
            break;
         case '"':
            buffer.append("&quot;");
            break;
         default:
            buffer.append(ch);
            break;
         }
      }
      
      return buffer.toString();
   }

   /**
    * Transform &amp;, &lt; &gt; &quot; back into &,<,>,".
    */
   public static String decodeXMLString(String s) {
      StringBuffer buffer = new StringBuffer();
      final String quot = "&quot;";
      final String amp = "&amp;";
      final String lt = "&lt;";
      final String gt = "&gt;";

      int length = (s == null ? 0 : s.length());
      int i = 0;
      while ( i < length ) { 
         char ch = s.charAt(i);
	 if (ch == '&') {
	     if ( s.regionMatches(i, quot, 0, quot.length() ) ) { 
		 buffer.append('\"');
		 i += quot.length();
	     } else if ( s.regionMatches(i, amp, 0, amp.length() ) ) { 
		 buffer.append('&');
		 i += amp.length();
	     } else if ( s.regionMatches(i, gt, 0, gt.length())) { 
		 buffer.append('>');
		 i += gt.length();
	     } else if ( s.regionMatches(i, lt, 0, lt.length())) { 
		 buffer.append('<');
		 i += lt.length();
	     } else { 
		 buffer.append(ch);
		 i++;
	     }
	 } else { 
	     buffer.append(ch);
	     i++;
	 }
      }
      return buffer.toString();
   }

   /**
    * Search the children of the specified parent element and return the
    * first element that matches the specified element name.  If no such
    * matching element can be found, then return null.
    */
   public static Element lookupElement(Element e, String name) {
      Element match = null;
      if (e != null) {
         Node t = e.getFirstChild();
         while (t != null) {
            if ((t instanceof Element) && (t.getNodeName().equals(name))) {
               match = (Element) t;
               break;
            } else {
               t = t.getNextSibling();
            }
         }
      }
      return match;
   }

   /**
    * Recursively copy the DOM tree using the specified document as the
    * root document factory.  Only element and text nodes are supported.
    */
   public static Node cloneDOM(Node node, Document document) {
      Node clone = null;
      if (node != null) {
         switch (node.getNodeType()) {

         /*
          * Clone the attributes and children of an element node.
          */
         case Node.ELEMENT_NODE:
            clone = document.createElement(node.getNodeName());

            NamedNodeMap attrs = node.getAttributes();
            int nattrs = (attrs == null ? 0 : attrs.getLength());
            for (int a = 0; a < nattrs; a++) {
               Attr attr = (Attr) attrs.item(a);
               ((Element)clone).setAttribute(attr.getName(), attr.getValue());
            }

            Node c = node.getFirstChild();
            while (c != null) {
               clone.appendChild(cloneDOM(c, document));
               c = c.getNextSibling();
            }
            break;

         /*
          * Clone the text data for CDATA and text nodes.
          */
         case Node.CDATA_SECTION_NODE:
         case Node.TEXT_NODE:
            clone = document.createTextNode(node.getNodeValue());
            break;

         /*
          * Ignore all other node types.
          */
         default:
            break;
         }
      }
      return clone;
   }

   /**
    * Recursively write the XML DOM representation into a print writer
    * output stream.  The DOM tree is output without any formatting.
    */
   public static void writeDOM(Node node, PrintWriter writer) {
      if (node != null) {
         switch (node.getNodeType()) {

         /*
          * Insert the XML header for a document node, including the
          * document type if it exists.
          */
         case Node.DOCUMENT_NODE:
            writer.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+EOL);
            
            DocumentType doctype = ((Document) node).getDoctype();
            if (doctype != null) {
               writer.print("<!DOCTYPE ");
               writer.print(doctype.getName());
               String public_id = doctype.getPublicId();
               String system_id = doctype.getSystemId();
               if (public_id != null) {
                  writer.print(" PUBLIC \"");
                  writer.print(public_id);
                  writer.print("\" \"");
               } else {
                  writer.print(" SYSTEM \"");
               }
               if (system_id != null) {
                  writer.print(system_id);
               }
               writer.print("\">" + EOL);
            }
            
            writeDOM(((Document) node).getDocumentElement(), writer);
            break;

         /*
          * Append an opening element tag, any attributes, children,
          * and a closing element tag.
          */
         case Node.ELEMENT_NODE:
            writer.print("<");
            writer.print(node.getNodeName());
            
            NamedNodeMap attrs = node.getAttributes();
            int length = (attrs == null ? 0 : attrs.getLength());
            for (int i = 0; i < length; i++) {
               Attr attr = (Attr) attrs.item(i);
               writer.print(" ");
               writer.print(attr.getName());
               writer.print("=\"");
               writer.print(encodeXMLString(attr.getValue()));
               writer.print("\"");
            }

            Node c = node.getFirstChild();
            if (c == null) {
               writer.print("/>");
            } else {
               writer.print(">");
               while (c != null) {
                  writeDOM(c, writer);
                  c = c.getNextSibling();
               }
               writer.print("</" + node.getNodeName() + ">");
            }
            break;

         /*
          * Append entity references as "&entity-name;".
          */
         case Node.ENTITY_REFERENCE_NODE:
            writer.print("&");
            writer.print(node.getNodeName());
            writer.print(";");
            break;

         /*
          * Append normalized string data
          */
         case Node.CDATA_SECTION_NODE:
         case Node.TEXT_NODE:
            writer.print(encodeXMLString(node.getNodeValue()));
            break;

         /*
          * Ignore all other node types.
          */
         default:
            break;
         }
      }
   }

   /**
    * Return a string representation of the XML DOM document.
    */
   public static String getXMLString(Node node) {
      StringWriter writer = new StringWriter();
      writeDOM(node, new PrintWriter(writer));
      return writer.toString();
   }

   /**
    * Return a string representation of the XML DOM document under the
    * specified element root.  This string will have both starting and
    * ending newlines removed.  If the generated string consists of only
    * whitespace, then a null string is returned.  A common use of this
    * method is to constuct the HTML text associated with a comment element.
    */
   public static String formatChildren(Node root) {
      StringWriter sw = new StringWriter();
      PrintWriter  pw = new PrintWriter(sw);

      if (root != null) {
         Node c = root.getFirstChild();
         while (c != null) {
            writeDOM(c, pw);
            c = c.getNextSibling();
         }
      }

      return trimWhiteSpaceLines(sw.toString());
   }

   /**
    * Remove white space lines from the beginning and end of the string.
    * The returned string does <em>not</em> end with an end-of-line
    * terminator.  A null string is returned if the string contains only
    * whitespace.
    */
   public static String trimWhiteSpaceLines(String s) {
      int start = 0;
      int end   = s.length()-1;

      /*
       * Start the string after the first newline before non-whitespace.
       */
      for (int i = 0; i <= end; i++) {
         if (s.charAt(i) == '\n') {
            start = i+1;
         } else if (s.charAt(i) > ' ') {
            break;
         }
      }

      /*
       * End the string before the last newline following non-whitespace.
       */
      for (int i = end; i >= start; i--) {
         if (s.charAt(i) == '\n') {
            end = i-1;
         } else if (s.charAt(i) > ' ') {
            break;
         }
      }

      /*
       * Return the substring between start and end if it is not empty.
       */
      String t = null;
      if (end >= start) {
         t = s.substring(start, end+1);
         if (isWhitespace(t)) {
            t = null;
         }
      }
      return t;
   }

   /**
    * Return whether the string contains only whitespace.  Whitespace is
    * defined as any character whose encoding value is less than or equal
    * to a space.
    */
   public static boolean isWhitespace(String s) {
      if (s != null) {
         int length = s.length();
         for (int i = 0; i < length; i++) {
            if (s.charAt(i) > ' ') return false;
         }
      }
      return true;
   }
}
