//
// File:        WebRepository.java
// Package:     gov.llnl.babel.repository
// Revision:    @(#) $Id: WebRepository.java 7188 2011-09-27 18:38:42Z adrian $
// Description: manages sidl symbol XML in the Alexandria web repository
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

package gov.llnl.babel.repository;

import gov.llnl.babel.Context;
import gov.llnl.babel.parsers.xml.ParseSymbolException;
import gov.llnl.babel.parsers.xml.ParseSymbolXML;
import gov.llnl.babel.parsers.xml.SymbolToDOM;
import gov.llnl.babel.repository.Repository;
import gov.llnl.babel.repository.RepositoryException;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.url.CookieConnection;
import gov.llnl.babel.url.HttpException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Set;
import org.xml.sax.InputSource;

/**
 * The <code>WebRepository</code> class implements an interface to the
 * Alexandria web repository for sidl symbols in XML.  The URI in the class
 * constructor points to the Alexandria database and must use either the
 * HTTP or HTTPS (if supported by the JVM) protocols.  The username and
 * password of the account must be passed as arguments in the URI as
 * follows: https://www.machine.org?username=USERNAME&password=PASSWORD.
 */
public class WebRepository implements Repository {
   private String d_web_url;              // base of Alexandria repository
   private CookieConnection d_connection; // connection preserves cookie state
   private final static String d_encoding = "ISO-8859-1";
   
   private final static String LOGIN_PATH = "alexandria/jsp/entry.jsp";
   private final static String sidl_PATH  = "alexandria/type";

  private Context d_context;

   /**
    * The constructor takes a web repository URI with the username and
    * password specified as arguments.
    */
   public WebRepository(String repository,
                        Context context)
     throws RepositoryException 
  {
      /*
       * Extract the username and password indices and check validity.
       */
    d_context = context;
      int u_index = repository.indexOf("?username=");
      int p_index = repository.indexOf("&password=");
      if ((u_index < 0) || (p_index <= u_index)) {
         throw new RepositoryException(
            "Invalid format for username and password " +
            "(http://www.machine.org/?username=USERNAME&password=PASSWORD)");
      }

      /*
       * Parse the web URL, username, and password from the input URI.
       */
      d_web_url = repository.substring(0, u_index);
      if (!d_web_url.endsWith("/")) {
         d_web_url += "/";
      }
      String username = repository.substring(u_index+10, p_index);
      String password = repository.substring(p_index+10);

      /*
       * Create a cookie connection and log into the repository.
       */
      d_connection = new CookieConnection();
      login(username, password);
   }

   /**
    * Log into the web repository using the specified username and password.
    * The username and password will be URL-encoded.  If any error is detected
    * during the login, then throw a repository exception.
 * @throws UnsupportedEncodingException
    */
   private void login(String username, String password)
         throws RepositoryException {
      /*
       * Create the payload to send to the login page.
       */
      StringBuffer payload = new StringBuffer();
      try {
        payload.append("name=");
        payload.append(URLEncoder.encode(username, d_encoding));
        payload.append("&password=");
        payload.append(URLEncoder.encode(password, d_encoding));
        payload.append("&machine=true");
  	
		/*
		 * Post the username and password and catch any exceptions.  The
		 * post method will cache the cookie that authorizes the connection.
		 */
         d_connection.postToURL(
            d_web_url + LOGIN_PATH,
            "application/x-www-form-urlencoded",
            new ByteArrayInputStream(payload.toString().getBytes()));
      } catch (UnsupportedEncodingException e) {
		throw new RepositoryException("Encoding Error");
      } catch (HttpException ex) {
         throw new RepositoryException(
            "Unable to log into web repository (check username and password)");
      } catch (IOException ex) {
         throw new RepositoryException("I/O error: " + ex.getMessage());
      }
   }

   /**
    * Look up the symbol based on the fully qualified name and version in
    * the web repository.  If the symbol name is not found, the versions
    * do not match, or there is a problem with the XML format, then null
    * is returned.
    */
   public Symbol lookupSymbol(SymbolID id) {
     if (id.getVersion().isUnspecified()) 
       return lookupSymbol(id.getFullName());
     try {
   	  StringBuffer url = new StringBuffer();
      url.append(d_web_url);
      url.append(sidl_PATH);
      url.append("/");
      url.append(URLEncoder.encode(id.getFullName(), d_encoding));
      url.append("?version=");
      url.append(URLEncoder.encode(id.getVersion().getVersionString(), d_encoding));
      return parseSymbolURL(url.toString());
	} catch (UnsupportedEncodingException e) {
		return null;
	}
   }

   /**
    * Look up a symbol based on the fully qualified name and retrieve the
    * most recent version that matches the symbol name.  If the symbol name
    * is not found or the XML file is invalid, then null is returned.
    */
   public Symbol lookupSymbol(String fqn) {
      StringBuffer url = new StringBuffer();
      url.append(d_web_url);
      url.append(sidl_PATH);
      url.append("/");
      try {
		url.append(URLEncoder.encode(fqn, d_encoding));
      } catch (UnsupportedEncodingException e) {
      	return null;
      }
      return parseSymbolURL(url.toString());
   }

   /**
    * Parse the repository URL corresponding to the symbol identifier and
    * return the corresponding symbol object.  Return null if any errors are
    * detected.
    */
   private Symbol parseSymbolURL(String url) {
      Symbol symbol = null;
      try {
        if (d_context.getConfig().isVerbose()) {
          System.out.println("Trying to parse XML from " + url);
        }
         InputStream istream = d_connection.getFromURL(url);
         symbol = ParseSymbolXML.convert(new InputSource(istream), d_context);
         istream.close();
      } catch (IOException ex) {
        if (d_context.getConfig().isVerbose()) {
          ex.printStackTrace();
        }
      } catch (ParseSymbolException ex) {
        if (d_context.getConfig().isVerbose()) {
          ex.printStackTrace();
        }
      }
      return symbol;
   }

   /**
    * Write the symbols in the <code>Set</code> from the symbol table to
    * the web repository.  Each set entry is a <code>SymbolID</code> of
    * the symbol to be written.
    */
   public void writeSymbols(Set symbol_names) 
     throws RepositoryException 
  {
      for (Iterator i = symbol_names.iterator(); i.hasNext(); ) {
         writeSymbol((SymbolID) i.next());
      }
   }

   /**
    * Write a particular symbol to the web repository.  An exception
    * may be thrown if (1) the symbol does not exist in the symbol table
    * or (2) there is an error writing the repository.
    */
   private void writeSymbol(SymbolID id) 
     throws RepositoryException 
  {
      Symbol symbol = d_context.getSymbolTable().lookupSymbol(id);
      if (symbol == null) {
         throw new RepositoryException("Symbol \""
            + id.getSymbolName()
            + "\" not found in symbol table");
      }

      String xml = SymbolToDOM.convertToString(symbol, d_context);
      try {
         d_connection.postToURL(
            d_web_url + sidl_PATH,
            "application/xml",
            new ByteArrayInputStream(xml.getBytes()));
      } catch (HttpException ex) {
         String msg = null;
         switch (ex.getResponseCode()) {
         case HttpURLConnection.HTTP_BAD_REQUEST:
            msg = "Post does not match symbol DTD";
            break;
         case HttpURLConnection.HTTP_CONFLICT:
            msg = "Symbol conflicts with existing one in database";
            break;
         case HttpURLConnection.HTTP_INTERNAL_ERROR:
            msg = "Server error (web server or SQL database)";
            break;
         default:
            msg = ex.getMessage();
            break;
         }
         throw new RepositoryException(id.getSymbolName() + ": " + msg);
      } catch (IOException ex) {
         throw new RepositoryException(
            id.getSymbolName() + ": " + ex.getMessage());
      }
   }
}
