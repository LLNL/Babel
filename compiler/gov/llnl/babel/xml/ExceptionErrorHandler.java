//
// File:        ExceptionErrorHandler.java
// Package:     gov.llnl.babel.xml
// Revision:    @(#) $Id: ExceptionErrorHandler.java 7188 2011-09-27 18:38:42Z adrian $
// Description: implementation of a SAX error handler that throws exceptions
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

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * The <code>ExceptionErrorHandler</code> class implements an error handler
 * for XML SAX and DOM parsers.  This error handler throws an exception of
 * type <code>SAXException</code> for all warnings, errors, and fatal errors.
 */
public class ExceptionErrorHandler implements ErrorHandler {
   /**
    * The <code>ExceptionErrorHandler</code> constructor does nothing.
    */
   public ExceptionErrorHandler() {
   }

   /**
    * Throw a <code>SAXException</code> if the parser issues a warning.
    * SAX parsers issue a warning to report conditions that are not errors
    * or fatal errors by the XML 1.0 recommendation.  By definition, the
    * parser could recover from a warning.  The default parser action for
    * a warning is to take no action; by throwing an exception, we ensure
    * that all XML documents are up to our high standards of quality.
    */
   public void warning(SAXParseException ex) throws SAXException {
      throw ex;
   }
   /**
    * Throw a <code>SAXException</code> if the parser issues an error.
    * SAX parsers issue an error to report conditions that are errors
    * by the W3C XML recommendation but are recoverable errors.  The
    * default behavior for errors is to take no action; we throw an
    * exception since we want our XML documents to be well-formed and
    * valid.
    */
   public void error(SAXParseException ex) throws SAXException {
      throw ex;
   }

   /**
    * Throw a <code>SAXException</code> if the parser issues a fatal error.
    * SAX parsers issue a fatal error if it cannot continue parsing the XML
    * document.  The default behavior for fatal errors is to throw an
    * exception of type <code>SAXException</code>.
    */
   public void fatalError(SAXParseException ex) throws SAXException {
      throw ex;
   }
}
