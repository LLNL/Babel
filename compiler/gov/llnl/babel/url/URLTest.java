//
// File:        URLTest.java
// Package:     gov.llnl.babel.url
// Revision:    @(#) $Id: URLTest.java 7188 2011-09-27 18:38:42Z adrian $
// Description: unit test for the URL routines
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

import gov.llnl.babel.url.URLUtilities;
import java.io.IOException;

/**
 * Class <code>URLTest</code> is a simple unit test for the URL utilities
 * class.  Each of the arguments is treated as a URI that is retrieved from
 * the source and displayed on standard out.
 */
public class URLTest {
   /**
    * Method <code>main</code> is the entry point for the unit test.
    * Each of the calling arguments is a URI.  The data from the URI
    * is fetched and displayed on standard output.
    */
   public static void main(String argv[]) {
      URLUtilities.enableHTTPS();
      for (int a = 0; a < argv.length; a++) {
         try {
            String url = URLUtilities.expandURL(argv[a]);
            System.out.println(url);
            System.out.println(URLUtilities.readURL(url));
         } catch (IOException ex) {
	    System.err.println("Babel: Error: IOException");
            System.err.println(ex.getMessage());
            System.exit(1);
         }
      }
   }
}
