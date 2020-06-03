//
// File:        DTDManager.java
// Package:     gov.llnl.babel.parsers.xml
// Revision:    @(#) $Id: DTDManager.java 7188 2011-09-27 18:38:42Z adrian $
// Description: singleton manager for sidl symbol DTDs
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

package gov.llnl.babel.parsers.xml;

import gov.llnl.babel.ResourceLoader;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

/**
 * <code>DTDManager</code> is a singleton class that manages the DTDs for
 * the sidl symbol, comment, and HTML descriptions needed to parse sidl
 * symbols in XML format.  If the application does not explicitly set the
 * path to the sidl DTDs, the the DTD manager will query the system property
 * "gov.llnl.babel.sidl.DTDPath".  This manager also defines the PUBLIC
 * identifiers for symbol and comment DTDs used to validate SIXL XML symbols.
 * The PUBLIC symbol identifier is "-//CCA/sidl Symbol DTD v1.0//EN" and the
 * HTML lite comment identifier is "-//CCA/sidl HTML DTD v1.0//EN".  The DTD
 * manager object can act as a symbol resolver for a SAX parser to interpret
 * these PUBLIC identifiers.
 */
public class DTDManager implements EntityResolver {
  public static final String SYMBOL_PUBLIC_ID =
    "-//CCA//sidl Symbol DTD v1.3//EN";
  public static final String HTML_PUBLIC_ID =
    "-//CCA//sidl HTML DTD v1.0//EN";
  public static final String COMMENT_PUBLIC_ID =
    "-//CCA//sidl Comment DTD v1.0//EN";
  public static final String COMMENT_FILE = "comment.dtd";
  private static final String s_resource_id =
    "gov/llnl/babel/dtds/";

   private static DTDManager s_instance = null;

  ResourceLoader rl = new ResourceLoader();

   /**
    * Create a new instance of the DTD manager.  Although singleton classes
    * do not typically define a public constructor, this implementation does
    * so to support multiple DTD managers in the same application.  Most
    * implementations, however, will not directly create a DTD manager
    * through the constructor and will instead use the singleton functions
    * <code>getInstance</code> and <code>setInstance</code>.
    */
   public DTDManager() {
   }

   /**
    * Return the singleton instance of the DTD manager.  If the symbol
    * table instance has not yet been created, then it will be created
    * by this call.
    */
   public static DTDManager getInstance() {
      if (s_instance == null) {
         s_instance = new DTDManager();
      }
      return s_instance;
   }

   /**
    * Set the singleton instance of the DTD manager.  Use this function
    * only if you want to change the default DTD manager implementation
    * or use multiple DTD managers in the same application.  Otherwise,
    * <code>getInstance</code> will automatically create the DTD manager
    * for you.
    */
   public static void setInstance(DTDManager instance) {
      s_instance = instance;
   }

   /**
    * Provide the implementation of an <code>EntityResolver</code> to
    * convert the symbol PUBLIC identifier into a URL for the symbol DTD.
    */
  public InputSource resolveEntity(String public_id, String system_id) 
    throws java.io.IOException
  {
    /*
     * Check whether any of the public IDs match sidl public IDs.
     */
    InputSource is = null;
    String jarEntry = null;
    String simpleName = null;
    if (SYMBOL_PUBLIC_ID.equals(public_id)) {
      simpleName = "sidl.dtd";
      jarEntry = s_resource_id + simpleName;
    }
    else if (HTML_PUBLIC_ID.equals(public_id)) {
      simpleName = "html-lite.dtd";
      jarEntry = s_resource_id + simpleName;
    }
    else if (COMMENT_PUBLIC_ID.equals(public_id)) {
      simpleName = "comment.dtd";
      jarEntry = s_resource_id  + simpleName;
    }
    if (jarEntry != null) {
      is = new InputSource(rl.getResourceStream(jarEntry));
    } 
    return is;
  }
}
