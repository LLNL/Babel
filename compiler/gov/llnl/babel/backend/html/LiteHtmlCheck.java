//
// File:        LiteHtmlCheck.java
// Package:     gov.llnl.alexandria.checkcontent
// Release:     $Name:  $
// Revision:    @(#) $Id: LiteHtmlCheck.java 7421 2011-12-16 01:06:06Z adrian $
// Description: Validate text as a fragment of XHTML lite.
// 
// Copyright (c) 2000, Lawrence Livermore National Security, LLC.
// Produced at the Lawrence Livermore National Laboratory.
// Written by the Components Team <components@llnl.gov>
// UCRL-CODE-2002-050
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

import gov.llnl.babel.parsers.xml.DTDManager;
import java.io.IOException;
import java.io.StringReader;
import java.util.Hashtable;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;


public class LiteHtmlCheck {

  private static final int EXPANSION_SPACE = 100;
  //private static final int IO_BUFFER_SIZE = 256;

  private static Hashtable s_allowed_protocols = new Hashtable(9);

  static {
    final String protocols[] = {
      "http",
      "ftp",
      "news",
      "nntp",
      "mailto",
      "wais",
      "prospero",
      "telnet",
      "gopher"
    };
    for(int i = 0; i < protocols.length ; i++){
      s_allowed_protocols.put(protocols[i], protocols[i]);
    }
  }

  /** 
   * Check if the string is a reference to a tag or a network protocol
   * (e.g. http, ftp, gopher, nntp, ...) as opposed to scripting language
   * references.  Presently, this just checks the start of the URL.
   */
  public static boolean isSafeUrl(String url)
  {
    int mark = 0, point;
    if (url.startsWith("#")){
      /* reference to local anchor */
      return true;
    }
    else {
      if (url.startsWith("url:")) {
        /* a URL may begin with url: */
        mark += 4;
      }
      point = url.indexOf(':', mark);
      if (point > mark){
        return s_allowed_protocols.contains(url.substring(mark,point));
      }
    }
    return false;
  }

  /**
   * A private class to receive SAX parsing events that looks for
   * "a" and "img" elements.  When it finds one, it checks to make
   * sure it is a safe URL reference.  Other elements are ignored.
   *
   * @author Tom Epperly
   * @version $Revision: 7421 $
   */
  private static class CheckLinks extends DefaultHandler {
    /**
     * The XML parser calls this at the start of an element.
     * This checks for "a" or "img" elements.  When it finds one,
     * it checks the type of link involved.
     */
    final public void startElement(String namespaceURI, String localName, 
                                   String rawName, Attributes atts)
      throws SAXException
    {
      String elem = null;
      String link = null;
      if ("a".equals(localName)){
        elem = localName;
        link = atts.getValue("", "href");
      }
      else if ("img".equals(localName)){
        elem = localName;
        link = atts.getValue("", "src");
      }
      if ((elem != null) && (link != null)){
        link = link.trim();
        if (!isSafeUrl(link)){
          throw new SAXException
            ("Element " + elem + 
             " does not have a safe URL reference");
        }
      }
    }

    /**
     * Throw the exception passed as an argument.
     *
     * @param exception a parse exception.
     * @exception org.xml.sax.SAXException
     *       throw the exception that was passed in as
     *       <code>exception</code>.
     */
    final public void error(SAXParseException exception)
      throws SAXException
    {
      throw exception;
    }
    
    /**
     * Throw the exception passed as an argument.
     *
     * @param exception a parse exception.
     * @exception org.xml.sax.SAXException
     *       throw the exception that was passed in as
     *       <code>exception</code>.
     */
    final public void fatalError(SAXParseException exception)
      throws SAXException
    {
      throw exception;
    }

    /**
     * Throw the warning exception passed as an argument.
     *
     * @param exception a parse exception.
     * @exception org.xml.sax.SAXException
     *       throw the exception that was passed in as
     *       <code>exception</code>.
     */
    final public void warning(SAXParseException exception)
      throws SAXException
    {
      throw exception;
    }
  }

  private final static class CheckResolver extends CheckLinks {
    private EntityResolver d_entityResolver;

    /**
     * Change the EntityResolver 
     */
    public CheckResolver(EntityResolver er) {
      d_entityResolver = er;
    }

    public InputSource resolveEntity(String publicId, String systemId)
      throws SAXException
    {
      try {
        return d_entityResolver.resolveEntity(publicId, systemId);
      }
      catch (IOException ioe) {
        throw new SAXException(ioe);
      }
    }
  }

  /**
   * Check if a string is a valid HTML lite fragment.  HTML lite is a
   * subset of XHTML with many of the basic formatting constructs that
   * intentionally excludes embedded scripts, objects or potentially
   * hostile content.
   */
  public static boolean isValidHtmlLiteFrag(String htmlfrag)
  {
    SAXParserFactory spf = SAXParserFactory.newInstance();
    StringBuffer buf = new StringBuffer(htmlfrag.length() + 
                                        EXPANSION_SPACE);
    buf.append("<?xml version=\"1.0\"?>\n<!DOCTYPE PureHtmlLite PUBLIC \"-//CCA//sidl HTML DTD v1.0//EN\"  \"html-lite.dtd\">\n<PureHtmlLite>").
      append(htmlfrag).append("</PureHtmlLite>");
    InputSource input = null;
    spf.setValidating(true);
    SAXParser xmlparser = null;
    try {
      input = new InputSource(new StringReader(buf.toString()));
      xmlparser = spf.newSAXParser();
      xmlparser.getXMLReader().setEntityResolver(DTDManager.getInstance());
      xmlparser.parse(input, new CheckResolver(DTDManager.getInstance()));
      return true;
    }
    catch (SAXException se){
    }
    catch (IOException ioe){
      ioe.printStackTrace();
    }
    catch (ParserConfigurationException pce) {
      pce.printStackTrace();
    }
    return false;
  }
}
