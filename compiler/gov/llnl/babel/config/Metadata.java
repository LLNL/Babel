//
// File:        Metadata.java
// Package:     gov.llnl.babel.config
// Revision:    @(#) $Id: Metadata.java 7188 2011-09-27 18:38:42Z adrian $
// Description: metadata description for the machine configuration database
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

package gov.llnl.babel.config;

import gov.llnl.babel.xml.ElementIterator;
import gov.llnl.babel.xml.XMLUtilities;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * The <code>Metadata</code> class contains information from the machine
 * configuration database XML metadata file.  It defines the languages
 * supported by the Babel compiler, the configuration options allowed
 * for each language, and the valid parameters for each option.  A
 * <code>Metadata</code> object is required to confirm the validity
 * of the configurations read from the machine configuration XML files.
 */
public class Metadata {
   private HashMap d_parameters;   // language->options->parameters
   private HashMap d_summaries;    // language->options->summary
   private HashMap d_descriptions; // language->options->description

   /**
    * The constructor for the <code>Metadata</code> class initializes the
    * internal data structures and parses the specified XML document.
    */
   public Metadata(String uri) throws IOException, SAXException {
      d_parameters   = new HashMap();
      d_summaries    = new HashMap();
      d_descriptions = new HashMap();

      parseXML(uri);
   }

   /**
    * Return the valid language names supported by the Babel compiler as
    * a set of strings.
    */
   public Set getValidLanguages() {
      return d_parameters.keySet();
   }

   /**
    * Given a particular language, return the valid configuration option
    * strings supported by that language.  This routine will return null
    * if the language is not supported or if there are no options associated
    * with the language.
    */
   public Set getValidOptions(String language) {
      HashMap options = (HashMap) d_parameters.get(language);
      return (Set) (options == null ? null : options.keySet());
   }

   /**
    * Return the set of valid option parameter string associated with a
    * language and option.  This routine will return null if the language
    * or option are invalid.
    */
   public Set getValidOptionParameters(String language, String option) {
      HashMap options = (HashMap) d_parameters.get(language);
      return (Set) (options == null ? null : options.get(option));
   }

   /**
    * Return a short summary of the option for the specified language.
    * A null will be returned if the language or option are invalid.
    */
   public String getOptionSummary(String language, String option) {
      HashMap options = (HashMap) d_summaries.get(language);
      return (String) (options == null ? null : options.get(option));
   }

   /**
    * Return a long description of the option for the specified language.
    * A null will be returned if the language or option are invalid or if
    * a description was not provided.
    */
   public String getOptionDescription(String language, String option) {
      HashMap options = (HashMap) d_descriptions.get(language);
      return (String) (options == null ? null : options.get(option));
   }

   /**
    * Parse the specified XML document to retrieve metadata configuration
    * information.  This private method builds the internal class data
    * structures based on the XML metadata document.  It first creates
    * a DOM document using the XML DOM parser and then analyzes the
    * structure of that document.
    */
   private void parseXML(String uri) throws IOException, SAXException {

      /*
       * Read the DOM document description from the XML DOM parser..
       * Extract the root element of the document.  If either of these
       * are null (neither should be null, but check anyway), then throw
       * an exception.
       */

      Document document = XMLUtilities.parse(uri);
      if (document == null) {
         throw new SAXException(
            "Error parsing XML metadata description (empty document)");
      }
      
      Element root = (Element) document.getDocumentElement();
      if (root == null) {
         throw new SAXException("No root element in XML metadata document");
      }
      if (!("ConfigurationMetadata".equals(root.getTagName()))) {
         throw new SAXException(
            "Incorrect root element in XML metadata document");
      }

      /*
       * Iterate over all children of the root element that are elements
       * with the tag "Language".
       */

      Iterator languages = new ElementIterator(root, "Language");
      while (languages.hasNext()) {
         Element language = (Element) languages.next();

         /*
          * Extract information from the language element regarding
          * allowed options and associated comments and parameters.
          * If no options are given, then simply add null values to
          * the maps with language name as key.
          */
         
         String language_name = language.getAttribute("name");
         Iterator options = new ElementIterator(language, "Option");

         if (!options.hasNext()) {
            d_parameters  .put(language_name, null);
            d_summaries   .put(language_name, null);
            d_descriptions.put(language_name, null);
         } else {
            HashMap param_map       = new HashMap();
            HashMap summary_map     = new HashMap();
            HashMap description_map = new HashMap();

            while (options.hasNext()) {
               Element option = (Element) options.next();
               String option_name = option.getAttribute("name");

               /*
                * Extract comment information from the Comment element.
                * Each option should have one and only one comment element.
                */

               Element c = XMLUtilities.lookupElement(option, "Comment");
               if (c != null) {
                  String summary = c.getAttribute("summary");
                  String descr   = XMLUtilities.formatChildren(c);
                  summary_map    .put(option_name, summary);
                  description_map.put(option_name, descr);
               }

               /*
                * Extract valid parameters from Parameter elements.  Valid
                * parameter names are stored in a HashSet.
                */

               HashSet param_set = new HashSet();
               Iterator parameters = new ElementIterator(option, "Parameter");
               while (parameters.hasNext()) {
                  Element parameter = (Element) parameters.next();
                  String choice = parameter.getAttribute("choice");
                  param_set.add(choice);
               }

               param_map.put(option_name, param_set);
            }

            /*
             * Add the newly generated hash map information into the
             * member hash maps using the language as key.
             */

            d_parameters  .put(language_name, param_map);
            d_summaries   .put(language_name, summary_map);
            d_descriptions.put(language_name, description_map);
         }
      }
   }
}
