//
// File:        URLUtilities.java
// Package:     gov.llnl.babel.url
// Revision:    @(#) $Id: URLUtilities.java 7188 2011-09-27 18:38:42Z adrian $
// Description: a collection of common URL utility functions
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

package gov.llnl.babel.url;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Provider;
import java.security.Security;

/**
 * Utility class <code>URLUtiliites</code> is a collection of common
 * URL utility functions.  All methods are declared as static.  Supported
 * operations include expanding a file URL, reading the data from a URL
 * and returning it as a string, and enabling HTTPS support.
 */
public class URLUtilities {
   private final static String EOL = "\n"; // standard web end-of-line

   private static boolean s_enabled_https = false;

   /**
    * Read data from a properly formatted URL and return it as a string.
    * This method may throw a <code>MalformedURLException</code> if the
    * URL is improperly formatted or an <code>IOException</code> if there
    * is a problem reading the URL data.
    */
   public static String readURL(String url)
         throws MalformedURLException, IOException {
      InputStream is = new URL(url).openStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(is));

      StringBuffer buffer = new StringBuffer();
      String line = null;
      while ((line = reader.readLine()) != null) {
         buffer.append(line);
         buffer.append(EOL);
      }
      is.close();

      return buffer.toString();
   }

   /**
    * Expand an improperly formatted file URL to a properly formatted URL.
    * If the URL passed into this function is invalid, then it is assumed
    * to be a file reference and is expanded as such.
    */
   public static String expandURL(String url) {
	  URL u;
      if ((url != null) && (url.length() > 0)) {
         try {
            u = new URL(url);
         } catch (MalformedURLException ex1) {
            File file = new File(url);
            try {
               u = file.toURL();
               url = u.toString();
            } catch (MalformedURLException ex2) {
            }
         }
      }
      return url;
   }

   /**
    * Try to enable HTTPS support.  This method must be called before any
    * attempt to create or use a <code>URL</code> with an HTTPS protocol.
    * This enables HTTPS through the Sun <code>JSSE</code> library.  If
    * this library does not exist, then HTTPS is not enabled.
    */
   public static void enableHTTPS() {
      if (!s_enabled_https) {
         try {
            System.setProperty(
               "java.protocol.handler.pkgs",
               "com.sun.net.ssl.internal.www.protocol");
            Class f = Class.forName("com.sun.net.ssl.internal.ssl.Provider");
            if ((f != null) && (Security.getProvider("SunJSSE") == null)) {
               Security.addProvider((Provider) f.newInstance());
            }
         } catch (Exception ex) {
         }
         s_enabled_https = true;
      }
   }
}
