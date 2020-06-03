//
// File:        CookieJar.java
// Package:     gov.llnl.babel.url.cookie
// Revision:    @(#) $Id: CookieJar.java 7188 2011-09-27 18:38:42Z adrian $
// Description: A collection of Cookie's
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

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * <code>CookieJar</code> is a container for HTTP Cookie's.  This container
 * manages cookie expiration and determining which cookies are relevant to 
 * a particular URL.
 */
public class CookieJar implements Serializable {
   /**
    * 
    */
   private static final long serialVersionUID = -7632996348979064376L;
   private LinkedList d_cookies;

   /**
    * Add a cookie to the jar.
    */
   public void add(Cookie c)
   {
      synchronized(d_cookies) {
         Iterator l = d_cookies.iterator();
         long currentTime = System.currentTimeMillis();
         while(l.hasNext()){
            Cookie old = (Cookie)l.next();
            if (old.hasExpired(currentTime) || old.equals(c)){
               l.remove();
            }
         }
         l = null;
         if (!c.hasExpired(currentTime)){
            d_cookies.add(c);
         }
      }
   }

   /**
    * Select and return cookies from this jar that are appropriate 
    * for the hostname, path and port given.  The document "HTTP State
    * Management Mechanism" dated August 27, 1999 gives detailed rules
    * for deciding whether a cookie should be sent with a particular
    * request.  This method attempts to implement those rules.
    */
   public Iterator getRelevantCookies(String hostname,
                                      String path,
                                      int port)
   {
      LinkedList result = new LinkedList();
      synchronized(d_cookies){
         long currentTime = System.currentTimeMillis();
         Iterator l = d_cookies.iterator();
         while(l.hasNext()){
            Cookie possible = (Cookie)l.next();
            if (possible.hasExpired(currentTime)){
               l.remove();
            }
            else if (possible.isRelevant(hostname, path, port)){
               result.add(possible);
            }
         }
      }
      return result.iterator();
   }


   /**
    * Remove stale cookies from the jar.
    */
   public void removeExpired() {
      long currentTime = System.currentTimeMillis();
      synchronized(d_cookies) {
         Iterator l = d_cookies.iterator();
         while (l.hasNext()){
            Cookie c = (Cookie)l.next();
            if (c.hasExpired(currentTime)){
               l.remove();
            }
         }
      }
   }

   public CookieJar() {
      d_cookies = new LinkedList();
   }
}
