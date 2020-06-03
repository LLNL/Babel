//
// File:        Http.java
// Package:     gov.llnl.babel.url.cookie
// Revision:    @(#) $Id: Http.java 7188 2011-09-27 18:38:42Z adrian $
// Description: A class to interface HTTP headers and Cookie objects
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

package gov.llnl.babel.url.cookie;

import gov.llnl.babel.url.cookie.Cookie;
import gov.llnl.babel.url.cookie.CookieCrumbleException;
import gov.llnl.babel.url.cookie.LexicalException;
import gov.llnl.babel.url.cookie.Lexer;
import gov.llnl.babel.url.cookie.Token;
import gov.llnl.babel.url.cookie.CookieJar;
import java.io.IOException;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

public class Http {

   private static Cookie parseNameValue(Lexer lex)
      throws java.io.IOException,
             LexicalException,
             CookieCrumbleException
   {
      Token name = lex.nextToken();
      if (name.getType() == Token.s_TOKEN){
         Token equal = lex.nextToken();
         if (equal.getType() == '='){
            Token value = lex.nextToken();
            if ((value.getType() == Token.s_TOKEN) ||
                (value.getType() == Token.s_QUOTED)){
               return new Cookie(name.getText(),
                                 value.getText());
            }
         }
      }
      return null;
   }

   private static Token parseAttribute(Cookie cookie,
                                       Lexer lex,
                                       HttpURLConnection conn)
      throws java.io.IOException,
             LexicalException,
             CookieCrumbleException
   {
      Token attr = lex.nextToken();
      if (attr.getType() == Token.s_TOKEN){
         if (attr.getText().equals("Discard")) {
            cookie.setDiscardable(true);
         }
         else if (attr.getText().equals("Secure")) {
            cookie.setSecure(true);
         }
         else {
            Token equal = lex.nextToken();
            if (equal.getType() == '='){
               Token value = lex.nextToken();
               if ((value.getType() == Token.s_TOKEN) ||
                   (value.getType() == Token.s_QUOTED)){
                  if (attr.getText().equals("Domain")){
                     if (value.getText().startsWith(".")){
                        cookie.setDomain(value.getText());
                     }
                     else {
                        cookie.setDomain("." + value.getText());
                     }
                  }
                  else if (attr.getText().equals("Max-Age")) {
                     cookie.setDomain(value.getText());
                  }
                  else if (attr.getText().equals("Path")) {
                     cookie.setPath(value.getText());
                  }
                  else if (attr.getText().equals("Version")){
                     cookie.setVersion(value.getText());
                  }
                  else if (attr.getText().equals("Port") &&
                           (value.getType() == Token.s_QUOTED)){
                     cookie.setPorts(value.getText());
                  }
                  else if (attr.getText().equals("Comment")){
                     cookie.setComment(value.getText());
                  }
                  else if (attr.getText().equals("CommentURL")){
                     cookie.setUrlComment(value.getText());
                  }
               }
               else {
                  // bad format
                  return null;
               }
            }
            else { 
               if (attr.getText().equals("Port")){
                  cookie.setPorts(Integer.toString(conn.getURL().getPort()));
               }
               // silently ignore unknown no value attribute
               return equal;
            }
         }
         return lex.nextToken();
      }
      else {
         return attr;
      }
   }

   /**
    * Add header information containing the relevant cookies to
    * the <code>URLConnection</code>.  This will add all cookies from
    * <code>jar</code> that should be sent along with <code>conn</code>
    * according to the rules stated in "Http State Management Mechanism"
    * dated August 27, 1999.
    *
    * @param conn a connection to a URL that hasn't yet connected to the
    *        server.
    * @param jar a collection of <code>Cookie</code>'s.
    * @exception java.io.IOException
    *     when there is I/O there is always the chance of an exception.
    */
   public static void sendCookies(URLConnection conn,
                                  CookieJar jar)
      throws java.io.IOException
   {
      URL url = conn.getURL();
      Iterator cookies = jar.getRelevantCookies(url.getHost(),
                                                url.getFile(),
                                                url.getPort());
      if (cookies != null && cookies.hasNext()){
         StringBuffer cookieValue = new StringBuffer();
         cookieValue.append("$Version=\"1\";");
         while(cookies.hasNext()){
            Cookie c = (Cookie)cookies.next();
            cookieValue.append(c.toString());
            if (cookies.hasNext()){
               cookieValue.append(';');
            }
         }
         conn.setRequestProperty("Cookie", cookieValue.toString());
      }
   }

   /**
    * Parse cookies found in the <code>Set-Cookie2</code>
    * header of this Http connection and put any cookies in the given jar.
    * This method will reject any cookies that aren't valid considering the
    * source and the rules found in the document "HTTP State
    * Management Mechanism" dated August 27, 1999.
    */
   public static void gleanCookies(HttpURLConnection conn,
                                   CookieJar jar)
   {
      String cookieSrc = conn.getHeaderField("Set-Cookie2");
      URL url = conn.getURL();
      String hostname = url.getHost();
      String filename = url.getFile();
      int port = url.getPort();
      if (cookieSrc != null){
         Lexer lex = new Lexer(new StringReader(cookieSrc));
         Token tok;
         try {
            do {
               Cookie cookie = parseNameValue(lex);
               if (cookie != null){
                  tok = lex.nextToken();
                  while (tok != null && tok.getType() == ';'){
                     tok = parseAttribute(cookie, lex, conn);
                  }
                  if (cookie.getDomain() == null){
                     // provide default domain
                     cookie.setDomain(Cookie.
                                      effectiveHostname(hostname));
                  }
                  if (cookie.getPath() == null){
                     int lastSeparator = filename.lastIndexOf('/');
                     if (lastSeparator >= 0){
                        cookie.setPath(filename.
                                       substring(0,lastSeparator+1));
                     }
                  }
                  if (cookie.hasValidDomain(hostname) &&
                      cookie.isRelevant(hostname, filename, port)){
                     jar.add(cookie);
                  }
               } 
               else {
                  tok = null;
               }
            } while ((tok != null) && (tok.getType() == ','));
         }
         catch (IOException ioe){
            ioe.printStackTrace();
         }
         catch (LexicalException le){
            le.printStackTrace();
         }
         catch (CookieCrumbleException cce){
            cce.printStackTrace();
         }
      }
   }
}
