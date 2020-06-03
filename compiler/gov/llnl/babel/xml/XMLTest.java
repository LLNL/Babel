//
// File:        XMLTest.java
// Package:     gov.llnl.babel.xml
// Revision:    @(#) $Id: XMLTest.java 7188 2011-09-27 18:38:42Z adrian $
// Description: unit test for the XML routines
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

import gov.llnl.babel.url.URLUtilities;
import gov.llnl.babel.xml.XMLUtilities;
import java.io.IOException;
import org.w3c.dom.Document;

/**
 * Class <code>XMLTest</code> is a simple unit test for the XML utilities
 * class.  It may be called in two ways.  If called with only one argument
 * representing an XML document URI, then that XML document is parsed and
 * echoed back to standard output.  The main routine may also be called with
 * three arguments: a DTD URI, a start element name for the XML document,
 * and the URI of the XML document.  This test code will parse the document,
 * validate it against the DTD, and then output it to the standard output.
 */
public class XMLTest {

   /**
    * Method <code>main</code> is the entry point for the unit test.
    * See the class documentation for calling arguments.
    */
   public static void main(String argv[]) {

      /*
       * Check that we are called with one or three command line arguments.
       */
      
      if ((argv.length != 1) && (argv.length != 3)) {
         System.err.println(
            "usage: gov.llnl.babel.xml.XMLTest " +
            "[DTD-URI start-element] XML-URI");
         System.exit(1);
      }

      /**
       * Read in XML data from the source specified in the XML URL argument.
       */

      String xml = null;
      try {
         String xml_uri = argv.length == 1 ? argv[0] : argv[2];
         xml = URLUtilities.readURL(URLUtilities.expandURL(xml_uri));
      } catch (IOException ex) {
	 System.err.println("Babel: Error: IOException");
         System.err.println(ex.getMessage());
         System.exit(1);
      }

      /*
       * Read the document and validate or check (depending on arguments).
       */

      Document document = null;
      if (argv.length == 1) {
         document = XMLUtilities.checkXML(xml);
      }

      if (document == null) {
         System.out.println("Invalid XML document");
      } else {
         System.out.println(XMLUtilities.getXMLString(document));
      }
   }
}
