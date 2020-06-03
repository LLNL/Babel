//
// File:        MetadataTest.java
// Package:     gov.llnl.babel.config
// Revision:    @(#) $Id: MetadataTest.java 7188 2011-09-27 18:38:42Z adrian $
// Description: unit test for the Metadata class
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

import gov.llnl.babel.config.Metadata;
import java.util.Iterator;
import java.util.Set;

/**
 * The <code>MetadataTest</code> class is a simple unit test for the
 * <code>Metadata</code> class.  It takes a single command line argument
 * that is a URI of a Babel XML metadata file and reports the configuration
 * data read from that XML file.
 */
public class MetadataTest {

   /**
    * Method <code>main</code> is the entry point for the unit test.
    * It takes one argument which is the URI of a Babel XML metadata
    * file.  The contents of the file are echoed to system output.
    */
   public static void main(String argv[]) {

      /*
       * We only take one command line argument (the URI).
       */

      if (argv.length != 1) {
         System.err.println("usage: gov.llnl.babel.config.MetadataTest URI");
         System.exit(1);
      }

      /*
       * Parse the metadata information from the command line URI.
       */

      Metadata metadata = null;
      
      try {
         metadata = new Metadata(argv[0]);
      } catch (java.io.IOException ex) {
	 System.err.println("Babel: Error: IOException");
         System.err.println(ex.getMessage());
      } catch (org.xml.sax.SAXException ex) {
	 System.err.println("Babel: Error: SAXException");
         System.err.println(ex.getMessage());
      }

      /*
       * If we successfully parsed the metadata file, then output all
       * configuration information from that file.
       */

      if (metadata != null) {

         /*
          * Output all of the currently supported languages.
          */
         
         Set languages = metadata.getValidLanguages();

         System.out.println();
         System.out.println("Currently supported languages:");
         for (Iterator l = languages.iterator(); l.hasNext(); ) {
            String language = (String) l.next();
            System.out.println("   " + language);
         }

         /*
          * Iterate through all the languages and print option information.
          */

         System.out.println();
         for (Iterator l = languages.iterator(); l.hasNext(); ) {
            String language = (String) l.next();
            System.out.println("Options for language " + language + ":");

            /*
             * Retrieve the options for the specified language.  If null
             * is returned, then there are no valid configuration options.
             */
            
            Set options = metadata.getValidOptions(language);
            if (options == null) {
               System.out.println("No valid options");
            } else {

               /*
                * Output the option name, summary, and comment.
                */
               
               for (Iterator o = options.iterator(); o.hasNext(); ) {
                  String option = (String) o.next();
                  System.out.println("Option name: " + option);
                  System.out.println("Summary: " +
                     metadata.getOptionSummary(language, option));
                  System.out.println("Comment:");
                  System.out.println(
                     metadata.getOptionDescription(language, option));

                  /*
                   * Output each of the valid option parameters.
                   */

                  Set parameters =
                     metadata.getValidOptionParameters(language, option);
                  for (Iterator p = parameters.iterator(); p.hasNext(); ) {
                     String param = (String) p.next();
                     System.out.println("Parameter: " + param);
                  }
               }
            }
         }
      }
   }
}
