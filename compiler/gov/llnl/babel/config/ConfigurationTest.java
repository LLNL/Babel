//
// File:        ConfigurationTest.java
// Package:     gov.llnl.babel.config
// Revision:    @(#) $Id: ConfigurationTest.java 7188 2011-09-27 18:38:42Z adrian $
// Description: unit test for the Configuration class
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

import gov.llnl.babel.config.Configuration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Class <code>ConfigurationTest</code> is a simple unit test for the
 * <code>Configuration</code> class.  It takes one or two command line
 * arguments.  If one argument is provided, then that argument must be
 * the URI of the configuration XML file; no validation against the
 * configuration metadata file will be performned.  If two arguments are
 * provided, then the first is the URI of the metadata XML file and the
 * second is the URI of the configuration XML file.  The configuration
 * information in the configuration XML file will be reported to the
 * system output.
 */
public class ConfigurationTest {

   /**
    * Method <code>main</code> is the entry point for the unit test.
    * It takes one or two arguments (see the class documentation).
    * The contents of the configuration XML file are echoed to the
    * system output.
    */
   public static void main(String argv[]) {

      /*
       * We only take one or two command line arguments (the URIs).
       */

      String metadata_uri      = null;
      String configuration_uri = null;
      
      if (argv.length == 1) {
         configuration_uri = argv[0];
      } else if (argv.length == 2) {
         metadata_uri      = argv[0];
         configuration_uri = argv[1];
      } else {
         System.err.println(
            "usage: gov.llnl.babel.config.ConfigurationTest " +
            "[metadata-uri] configuration-uri");
         System.exit(1);
      }

      /*
       * Parse the configuration information from the command line URI.
       */

      Configuration config = new Configuration();

      if (metadata_uri != null) {
         try {
            config.setMetadataDescription(metadata_uri);
         } catch (java.io.IOException ex) {
            System.err.println("Babel: Error: Invalid metadata URI");
            System.err.println(ex.getMessage());
         } catch (org.xml.sax.SAXException ex) {
            System.err.println("Babel: Error: Metadata format error");
            System.err.println(ex.getMessage());
         }
      }

      try {
         config.readConfiguration(configuration_uri);
      } catch (java.io.IOException ex) {
         System.err.println("Babel: Error: Invalid configuration URI");
         System.err.println(ex.getMessage());
      } catch (gov.llnl.babel.config.InvalidConfiguration ex) {
         System.err.println("Babel: Error: Configuration format error");
         System.err.println(ex.getMessage());
      }

      /*
       * Output information from the configuration file.  First output
       * all of the currently supported languages.
       */

      Set languages = config.getLanguages();

      if (languages.isEmpty()) {
         System.out.println("No languages are supported");
      } else {
         System.out.println();
         System.out.println("Currently supported languages:");
         for (Iterator l = languages.iterator(); l.hasNext(); ) {
            String language = (String) l.next();
            System.out.println("   " + language);
         }
      }

      /*
       * Iterate through all of the languages and output the profile
       * information for each language.
       */

      System.out.println();
      for (Iterator l = languages.iterator(); l.hasNext(); ) {
         String language = (String) l.next();
         System.out.println("Profiles for language " + language + ":");

         Set profiles = config.getProfileNames(language);
         if ((profiles == null) || (profiles.isEmpty())) {
            System.out.println("No profiles defined");
         } else {
            for (Iterator p = profiles.iterator(); p.hasNext(); ) {
               String  profile_name = (String) p.next();
               Profile profile = config.getProfile(language, profile_name);

               /*
                * Output information from the profile.  Note that some
                * of these entries might be null.
                */

               System.out.println("Profile name: " + profile_name);
               System.out.println("Summary: " + profile.getSummary());
               System.out.println("Description:");
               System.out.println(profile.getDescription());
               System.out.println("CPU: " + profile.getCPU());
               System.out.println("OS: " + profile.getOS());
               System.out.println("OS Release: " + profile.getOSRelease());
               System.out.println("Compiler: " + profile.getCompilerName());
               System.out.println("Version: " + profile.getCompilerVersion());

               /*
                * Output configuration options.
                */
               
               System.out.println("Configuration options:");
               Map options = profile.getConfiguration();
               if ((options == null) || (options.isEmpty())) {
                  System.out.println("No options given");
               } else {
                  Set keys = options.keySet();
                  for (Iterator k = keys.iterator(); k.hasNext(); ) {
                     String key   = (String) k.next();
                     String value = (String) options.get(key);
                     System.out.println("\"" + key + "\"=\"" + value + "\"");
                  }
               }
               System.out.println();
            }
         }
      }
   }
}
