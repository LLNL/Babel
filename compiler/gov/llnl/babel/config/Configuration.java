//
// File:        Configuration.java
// Package:     gov.llnl.babel.config
// Revision:    @(#) $Id: Configuration.java 7188 2011-09-27 18:38:42Z adrian $
// Description: machine configuration information for the Babel compiler
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
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * Class <code>Configuration</code> contains configuration information
 * from the machine configuration database for the Babel compiler.  It
 * defines the languages supported by the Babel compiler, the valid
 * configuration profiles for each language, and the parameter choices
 * for each option.  An optional <code>Metadata</code> object may be
 * provided to validate the correctness of the configuration information.
 */
public class Configuration {
   private HashMap  d_profiles;   // language->profilename->profile
   private Metadata d_metadata;   // metadata description for validation

   /**
    * The constructor for the <code>Configuration</code> class
    * initializes the profile database.  Machine configuration
    * profiles are read into the configuration object using method
    * <code>readConfiguration</code>.
    */
   public Configuration() {
      d_profiles = new HashMap();
      d_metadata = null;
   }

   /**
    * Return the languages supported by the Babel compiler as a set of
    * strings.  This return argument will never be null, although the
    * map may not contain any entries.
    */
   public Set getLanguages() {
      return d_profiles.keySet();
   }

   /**
    * Given a particular language, return the valid profile names
    * available for that language.  This routine will return null
    * if the language is not supported.  The set of profile names
    * will be empty if no profiles are defined for the language.
    */
   public Set getProfileNames(String language) {
      HashMap profiles = (HashMap) d_profiles.get(language);
      return (Set) (profiles == null ? null : profiles.keySet());
   }

   /**
    * Return the profile associated with a language and a profile
    * name.  This routine will return null if the language or the
    * profile name is invalid.
    */
   public Profile getProfile(String language, String profile_name) {
      HashMap profiles = (HashMap) d_profiles.get(language);
      return (Profile) (profiles == null ? null : profiles.get(profile_name));
   }

   /**
    * Return the configuration information associated with a language
    * and a profile name.  The configuration information is held in a
    * Map of (keyword,value) pairs, where both keyword and value are
    * strings.  This routine will return null if the language or the
    * profile name is invalid.
    */
   public Map getConfiguration(String language, String profile_name) {
      Profile profile = getProfile(language, profile_name);
      return (profile == null ? null : profile.getConfiguration());
   }

   /**
    * Provide the metadata description that will be used to validate
    * the configuration input file.  Although the XML DTD provides
    * some amount of validation, the metadata description is required
    * to verify that all options exist and the chosen values for each
    * option are valid.  An <code>IOException</code> will be thrown if
    * the URI is invalid and a <code>SAXException</code> will be thrown
    * if a parse error occurs while reading the XML metadata file.
    */
   public void setMetadataDescription(String URI)
         throws IOException, SAXException {
      d_metadata = new Metadata(URI);
   }

   /**
    * Profide the metadata description that will be used to validate
    * the configuration input file.  If null, then validation will be
    * disabled.
    */
   public void setMetadataDescription(Metadata metadata) {
      d_metadata = metadata;
   }

   /**
    * Validate the profile against the metadata description set by a
    * previous call to <code>setMetadataDescription</code>.  If no
    * previous metadata profile was set, then no validation will be
    * performed.  If the profile is invalid, then an exception of type
    * <code>InvalidConfigutation</code> will be thrown.
    */
   public void validateProfile(Profile profile) throws InvalidConfiguration {
      if (d_metadata != null) {
         String language = profile.getLanguage();

         /*
          * Check that the profile language is supported.
          */

         Set valid_languages = d_metadata.getValidLanguages();
         if (!valid_languages.contains(language)) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("Language ");
            buffer.append("\"" + language + "\"");
            buffer.append(" is not supported (Supported languages:");
            appendSetStrings(valid_languages, buffer);
            buffer.append(")");
            throw new InvalidConfiguration(buffer.toString());
         }

         /*
          * For each option in the profile, check that the option is
          * valid and that the parameter selected for the option lies
          * in the set of valid options.  Note that we need to be
          * careful if either the configuration or metadata valid
          * options are null.
          */

         Map config  = profile.getConfiguration();
         Set options = (config == null) ? new HashSet() : config.keySet();

         Set valid_options = d_metadata.getValidOptions(language);
         if (valid_options == null) valid_options = new HashSet();

         for (Iterator o = options.iterator(); o.hasNext(); ) {
            String option = (String) o.next();
            if (!valid_options.contains(option)) {
               StringBuffer buffer = new StringBuffer();
               buffer.append("Option ");
               buffer.append("\"" + option + "\"");
               buffer.append(" is not supported (Supported options:");
               appendSetStrings(valid_options, buffer);
               buffer.append(")");
               throw new InvalidConfiguration(buffer.toString());
            }

            String parameter = (String) config.get(option);
            Set valid_parameters =
               d_metadata.getValidOptionParameters(language, option);
            if (!valid_parameters.contains(parameter)) {
               StringBuffer buffer = new StringBuffer();
               buffer.append("Parameter ");
               buffer.append("\"" + parameter + "\"");
               buffer.append(" is not supported for option ");
               buffer.append("\"" + option + "\"");
               buffer.append(" (Supported parameters:");
               appendSetStrings(valid_parameters, buffer);
               buffer.append(")");
               throw new InvalidConfiguration(buffer.toString());
            }
         }

         /**
          * Finally, verify that every one of the valid options
          * appears in the set of specified options.
          */

         for (Iterator o = valid_options.iterator(); o.hasNext(); ) {
            String valid = (String) o.next();
            if (!options.contains(valid)) {
               StringBuffer buffer = new StringBuffer();
               buffer.append("Option ");
               buffer.append("\"" + valid +"\"");
               buffer.append(" is not specified (Required options:");
               appendSetStrings(valid_options, buffer);
               buffer.append(")");
               throw new InvalidConfiguration(buffer.toString());
            }
         }
      }
   }

   /**
    * Iterate over the members of a set (which are strings) and append
    * the strings into a <code>StringBuffer</code>.  If the set is null
    * or is empty, then the string " none" is appended to the buffer.
    */
   private void appendSetStrings(Set set, StringBuffer buffer) {
      if ((set == null) || set.isEmpty()) {
         buffer.append(" none");
      } else {
         for (Iterator i = set.iterator(); i.hasNext(); ) {
            buffer.append(" \"" + (String) i.next() + "\"");
         }
      }
   }

   /**
    * Parse the specified XML document to retrieve machine configuration
    * information.  This private method builds the internal class data
    * structures based on the XML machine configuration document.  It
    * first creates a DOM document using the XML DOM parser and then
    * analyzes the structure of that document.  Finally, it checks the
    * validity of that document using <code>validateProfile</code>.
    */
   public void readConfiguration(String uri)
         throws IOException, InvalidConfiguration {

      /*
       * Read the DOM document description from the XML DOM parser.
       * Extract the root element of the document.  If either of these
       * are null (neither should be null, but check anyway), then
       * throw an exception of type <code>InvalidConfiguration</code>.
       */

      Document document = null;
      try {
         document = XMLUtilities.parse(uri);
      } catch (SAXException ex) {
         throw new InvalidConfiguration(
            "Error while parsing XML configuration file: " + ex.getMessage());
      }
      if (document == null) {
         throw new InvalidConfiguration("Empty XML configuration document");
      }
      
      Element root = (Element) document.getDocumentElement();
      if (root == null) {
         throw new InvalidConfiguration(
            "No root element in XML configuration document");
      }
      if (!("Configuration".equals(root.getTagName()))) {
         throw new InvalidConfiguration(
            "Incorrect root element in XML configuration document");
      }

      /*
       * Iterate over all children of the root element that are elements
       * with the tag "Language".
       */

      Iterator languages = new ElementIterator(root, "Language");
      while (languages.hasNext()) {
         Element language      = (Element) languages.next();
         String  language_name = language.getAttribute("name");

         /*
          * Extract option selections from the language element.
          * Create a <code>HashMap</code> of (option,parameter)
          * pairs that describe the selections.  This map will be
          * shared by all profiles within this language section.
          * There should be only zero or one element with a tag name
          * of Options.  There will be many Selection elements within
          * the Options element.
          */

         HashMap configuration = new HashMap();
         Element option = XMLUtilities.lookupElement(language, "Options");
         if (option != null) {
            Iterator selections = new ElementIterator(option, "Selection");
            while (selections.hasNext()) {
               Element selection = (Element) selections.next();
               String option_name = selection.getAttribute("option");
               String value_name  = selection.getAttribute("value");
               configuration.put(option_name, value_name);
            }
         }

         /*
          * Now loop over all profiles for this language and extract
          * profile information for the configuration.
          */

         Map profile_map = (Map) d_profiles.get(language_name);
         if (profile_map == null) profile_map = new HashMap();

         Iterator profiles = new ElementIterator(language, "Profile");
         while (profiles.hasNext()) {
            Element profile      = (Element) profiles.next();
            String  profile_name = profile.getAttribute("name");
            Profile new_profile  = new Profile(profile_name, language_name);

            new_profile.setConfiguration(configuration);

            /*
             * Extract the description from the DOM tree.
             */

            Element desc = XMLUtilities.lookupElement(profile, "Description");
            if (desc != null) {
               String  summary   = desc.getAttribute("summary");
               String  htmldescr = XMLUtilities.formatChildren(desc);
               new_profile.setSummary(summary);
               new_profile.setDescription(htmldescr);
            }

            /*
             * Extract the CPU, OS, and release information if it exists.
             */

            Element arch = XMLUtilities.lookupElement(profile,"Architecture");
            if (arch != null) {
               String  cpu     = arch.getAttribute("cpu");
               String  os      = arch.getAttribute("os");
               String  release = arch.getAttribute("release");
               new_profile.setCPU(cpu);
               new_profile.setOS(os);
               new_profile.setOSRelease(release);
            }

            /*
             * Extract the compiler and version information if it exists.
             */

            Element comp = XMLUtilities.lookupElement(profile, "Compiler");
            if (comp != null) {
               String compiler_name = comp.getAttribute("name");
               String compiler_vers = comp.getAttribute("version");
               new_profile.setCompilerName(compiler_name);
               new_profile.setCompilerVersion(compiler_vers);
            }

            /*
             * Now that we've built a profile, validate it and add it
             * to the list of profiles for this language.
             */

            validateProfile(new_profile);
            profile_map.put(profile_name, new_profile);
         }

         /*
          * Add the profiles to the current language.
          */

         d_profiles.put(language_name, profile_map);
      }
   }
}
