//
// File:        CookieConnection.java
// Package:     gov.llnl.babel.url
// Revision:    @(#) $Id: CookieConnection.java 7188 2011-09-27 18:38:42Z adrian $
// Description: an HTTP connection object that caches cookies
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

import gov.llnl.babel.url.cookie.CookieJar;
import gov.llnl.babel.url.cookie.Http;
import gov.llnl.babel.url.HttpException;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Class <code>CookieConnection</code> caches cookies for a HTTP or HTTPS
 * connection.  It provides methods to post and get data and maintains cookie
 * state.
 */
public class CookieConnection {
   private CookieJar d_cookie_jar;

   /**
    * Create a new cookie connection and initialize the cookie state to be
    * empty.  Cookies will be added during gets and posts to the connection.
    */
   public CookieConnection() {
      d_cookie_jar = new CookieJar();
   }

   /**
    * Open a GET connection to the specified URL and return the associated
    * input stream, which must be closed by the callee.  If this is an HTTP
    * or HTTPS URL, then cookies are cached across multiple connections.
    * An <code>IOException</code> is thrown if there is an I/O error or if
    * the status response from the HTTP server indicates an error.
    */
   public InputStream getFromURL(String url_string)
         throws HttpException, IOException {
      URL url = new URL(url_string);
      URLConnection connection = url.openConnection();

      /*
       * If this is an HTTP connection, set the method to GET and put
       * the cookies into the output stream.
       */
      if (connection instanceof HttpURLConnection) {
         HttpURLConnection http = (HttpURLConnection) connection;
         http.setRequestMethod("GET");
         Http.sendCookies(http, d_cookie_jar);
      }

      /*
       * Connect to the remote resource.
       */
      connection.connect();

      /*
       * If this is an HTTP connection, then remove cookies from the
       * response and check the response error code.
       */
      if (connection instanceof HttpURLConnection) {
         HttpURLConnection http = (HttpURLConnection) connection;
         Http.gleanCookies(http, d_cookie_jar);
         int response = http.getResponseCode();
         if (response != HttpURLConnection.HTTP_OK) {
            throw new HttpException(response, "HTTP Error ("
               + String.valueOf(response)
               + "): "
               + http.getResponseMessage());
         }
      }

      return connection.getInputStream();
   }

   /**
    * Open a POST connection to the specified URL and submit the specified
    * input stream with the associated content type.  If this is an HTTP or
    * HTTPS URL, then cookies are cached across multiple connections.  An
    * <code>IOException</code> is thrown if there is an I/O error or if the
    * status response from the HTTP server indicates an error.  The content
    * stream is closed after its data is transferred to the server.
    */
   public void postToURL(
         String url_string,
         String content_type,
         InputStream content) throws HttpException, IOException {
      /*
       * Create URL and connection objects and set connection properties.
       */
      URL url = new URL(url_string);
      URLConnection connection = url.openConnection();
      connection.setRequestProperty("Content-type", content_type);
      connection.setDoOutput(true);
      connection.setDoInput(true);

      /*
       * If this is an HTTP connection, set the method to PUT and put
       * the cookies into the output stream.
       */
      if (connection instanceof HttpURLConnection) {
         HttpURLConnection http = (HttpURLConnection) connection;
         http.setRequestMethod("POST");
         Http.sendCookies(http, d_cookie_jar);
      }

      /*
       * Connect to the remote resource and send data.
       */
      OutputStream out = connection.getOutputStream();
      connection.connect();
      copyStream(content, out);
      out.close();
      content.close();

      /*
       * If this is an HTTP connection, then remove cookies from the
       * response and check the response error code.
       */
      if (connection instanceof HttpURLConnection) {
         HttpURLConnection http = (HttpURLConnection) connection;
         Http.gleanCookies(http, d_cookie_jar);
         int response = http.getResponseCode();
         if (response != HttpURLConnection.HTTP_OK) {
            throw new HttpException(response, "HTTP Error ("
               + String.valueOf(response)
               + "): "
               + http.getResponseMessage());
         }
      }
   }

   /**
    * Private method <code>copyStream</code> copies data from the input
    * stream into the output stream.
    */
   private static void copyStream(
         InputStream in, OutputStream out) throws IOException {
      int bytes_read;
      byte[] buffer = new byte[1024];
      while((bytes_read = in.read(buffer)) >= 0) {
         out.write(buffer, 0, bytes_read);
      }
   }
}
