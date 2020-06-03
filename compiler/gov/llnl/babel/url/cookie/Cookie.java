//
// File:        Cookie.java
// Package:     gov.llnl.babel.url.cookie
// Revision:    @(#) $Id: Cookie.java 7188 2011-09-27 18:38:42Z adrian $
// Description: A class to store a HTTP Cookie for a User Agent
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
import java.util.Date;
import java.util.StringTokenizer;

/**
 * Some WWW servers will not work unless the user agent stores and returns 
 * <i>cookies</i>.  The is part of a package to manage cookies for a Java
 * WWW user agent.  The Java user agent might be a browser, or it might be
 * an automated URL checker (the original application area).  Whatever the
 * case may be, this class stores all the attributes of a Cookie.
 *
 * This implementation is based on the HTTP Working Group INTERNET DRAFT
 * titled "HTTP State Management Mechanism" dated August 27, 1999.
 *
 * By design, this class does not trust its client that much.  It checks to
 * make sure that names and values are legal according to the HTTP/1.1
 * specification (RFC 2616).
 */
public class Cookie implements Serializable {
   /**
	* 
	*/
   private static final long serialVersionUID = 6608089316513898159L;

   /**
    * The cookie's name.
    */
   private String d_name;

   /**
    * The cookie's value.
    */
   private String d_value;

   /**
    * The cookie's comment.
    */
   private String d_comment = null;

   /**
    * The cookie's URL comment.
    */
   private String d_urlComment = null;

   /**
    * The cookie's domain.
    */
   private String d_domain = null;

   /**
    * The cookie's maximum age in delta-seconds.  A negative value means
    * that the user did not specify a maximum age.
    */
   private int d_maxAge = -1;

   /**
    * The cookie's expiration date & time.  Calculated from the
    * Max-Age attribute and the cookie's creation time.
    */
   private Date d_expiration = null;
   
   /**
    * The cookie's path (indicates a subset of the server's URLs).
    */
   private String d_path = null;

   /**
    * The cookie's ports.
    */
   private String d_ports = null;

   /**
    * Whether the cookie requires a secure connection.
    */
   private boolean d_secure = false;

   /**
    * Whether the cookie should disappear when the user agent exists.
    */
   private boolean d_discard = true;

   /**
    * Whether the discard attribute was set explicitly or by default.
    */
   private boolean d_discardExplicit = false;

   /**
    * The cookie description's version.
    */
   private String d_version = "0";

   /**
    * Quote special characters in <code>value</code>.  When you're putting
    * certain value fields into a HTTP/1.1 header, the double quote and
    * backslash characters must be quoted with a backslash.
    *
    * @param value a string of text.
    * @return value with any <code>"</code> or <code>\</code> characters
    * encoded.
    */
   public static String encodeValue(String value)
   {
      final int len = value.length();
      char c;
      StringBuffer buf = new StringBuffer(len+1);
      int i = 0;
      while (i < len){
         c = value.charAt(i);
         if ((c == '\r') && (i < (len - 2)) &&
             (value.charAt(i+1) == '\n') &&
             ((value.charAt(i+2) == '\t') ||
              (value.charAt(i+2) == ' '))){
            buf.append(value.substring(i, i+3));
            i += 3;
         }
         else if ((c == '"') || (c == '\\') || 
                  (Character.isISOControl(c) && (c != '\t'))){
            buf.append('\\').append(c);
            ++i;
         }
         else{
            buf.append(c);
            ++i;
         }
      }
      return buf.toString();
   }
   

   /**
    * Unquote special characters in <code>value</code>.  When you take
    * a value field from a HTTP/1.1 header, you must remove the
    * backslash quoting to get the real value.
    *
    * @param value a string of text.
    * @return value with any <code>"</code> or <code>\</code> characters
    * encoded.
    */
   public static String decodeValue(String value)
   {
      final int firstBackslash = value.indexOf('\\');
      if (firstBackslash >= 0){
         final int len = value.length();
         char c;
         StringBuffer buf = new StringBuffer(len-1);
         buf.append(value.substring(0, firstBackslash));
         for(int i = firstBackslash; i < len; i++){
            c = value.charAt(i);
            if (c == '\\'){
               i++;
               if (i < len){
                  buf.append(value.charAt(i));
               }
            }
         }
         return buf.toString();
      }
      return value;
   }
   

   /**
    * Check if <code>tok</code> is a valid token as defined by the
    * HTTP/1.1 spec.
    * 
    * @param tok a string that may or may not be a valid token.
    * @return <code>true</code> means <code>tok</code> is a valid
    *         HTTP/1.1 token; <code>false</code> means <code>tok
    *         </code> is not a valid token.
    */
   public static boolean validToken(String tok)
   {
      final int len = tok.length();
      for(int i = 0; i < len; i++){
         if (!Lexer.validTokenChar(tok.charAt(i))){
            return false;
         }
      }
      return (len > 0);
   }

   /**
    * Return true if the string is valid as a cookie attribute value.
    * Specifically, this returns true if value satisfies the definition
    * for <code>TEXT</code> in RFC 2616 (the HTTP/1.1 spec).
    * 
    * @return <code>true</code> means that the attribute value is
    * acceptable.
    */
   public static boolean validValue(String value)
   {
      final int len = value.length();
      int state = 0;
      char ch;
      for(int i = 0; i < len ; i++){
         ch = value.charAt(i);
         if  (ch > '\u00ff') return false;
         switch(state){
         case 0:
            if (ch == '\r') { /* error or start of LWS */
               state = 1;
            }
            else if (Character.isISOControl(ch) && (ch != '\t')) {
               return false;
            }
            else if (ch == '\\'){
               state = 3; // backslash seen
            }
            break;
         case 1: /* carriage return seen */
            if (ch == '\n'){ /* possibly second character in an LWS */
               state = 2;
            }
            else{
               return false;
            }
            break;
         case 2: /* carriage return line feed seen */
            if ((ch == '\t') || (ch == ' ')){ /* finish off an LWS */
               state = 0;
            }
            else {
               return false;
            }
            break;
         case 3: // backslash seen
            // any character 0-127 is allowed after a backslash
            if (ch > 127){
               return false;
            }
            state = 0;
            break;
         default:
            return false;
         }
      }
      return (state == 0);
   }

   /**
    * Returns <code>true</code> iff the port list is a valid list of
    * comma separated integers.
    * 
    * @return <code>true</code> means that the attribute value is
    * acceptable.
    */
   public static boolean validPortList(String ports)
   {
      StringTokenizer tok = new StringTokenizer(ports, ",");
      while (tok.hasMoreTokens()){
         String port = tok.nextToken().trim();
         try {
            Integer.parseInt(port);
         }
         catch (NumberFormatException nfe){
            return false;
         }
      }
      return true;
   }

   /**
    * Return the <i>effective host name</i> of a host name.  If
    * <code>hostname</code> doesn't have any periods in it, ".local" is
    * appended. 
    * 
    * @param hostname the original host name
    * @return if <code>hostname</code> doesn't have any periods in it,
    * hostname with ".local" appended is returned; otherwise, hostname is
    * return unmodified.
    */
   public static String effectiveHostname(String hostname)
   {
      if (hostname.indexOf('.') < 0){
         return hostname + ".local";
      }
      return hostname;
   }

   /**
    * The basic cookie constructor (or should I say cutter?).
    *
    * @param name   The cookie's name.  This should be a sequence of
    *               non-special, non-whitespace characters (according to 
    *               the spec.
    * @param value  The cookie's value.  This should be encoded as
    *		    it does into a HTTP/1.1 header.
    * @throws gov.llnl.babel.url.cookie.CookieCrumbleException
    * indicates that name or value is illegal.
    */
   public Cookie(String name,
                 String value)
      throws CookieCrumbleException
   {
      if (!validToken(name)){
         throw new CookieCrumbleException
            ("Proposed cookie has invalid name \"" + name + "\"");
      }
      if (!validValue(value)){
         throw new CookieCrumbleException
            ("Proposed cookie has invalid value \"" + value + "\"");
      }
      d_name = name;
      d_value = value;
   }

   /* INTERROGATORS */

   /**
    * Return the name of the cookie.
    * 
    * @return the name of the cookie
    */
   public String getName()
   {
      return d_name;
   }

   /**
    * Return the value of the cookie.
    *
    * @return value of the cookie
    */
   public synchronized String getValue()
   {
      return d_value;
   }

   /**
    * Return the comment attribute of the cookie.  Note this may be
    * <code>null</code> indicating that the cookie contains no comment.
    *
    * @return <code>null</code> or a valid string containing the comment
    * attribute.
    */
   public synchronized String getComment()
   {
      return d_comment;
   }

   /**
    * Return the URL comment attribute of the cookie.  Note this may be
    * <code>null</code> indicating that the cookie contains no URL comment.
    *
    * @return <code>null</code> or a valid string containing the URL comment
    * attribute.
    */
   public synchronized String getUrlComment()
   {
      return d_urlComment;
   }

   /**
    * Return the domain of this cookie.
    * 
    * @return The domain of this cookie.  If the client doesn't provide
    * the domain, this will be <code>null</code>.
    */
   public synchronized String getDomain()
   {
      return d_domain;
   }

   /**
    * Return the maximum age attribute for the cookie.  A negative return
    * value indicates that the cookie has no maximum age setting.  A
    * non-negative value is the maximum age in seconds from when it was
    * created.
    *
    * @return the maximum age attribute of the cookie or a negative value
    * (indicates no maximum age setting).  This attribute is not useful
    * unless you know when the cookie began.
    */
   public synchronized int getMaxAge()
   {
      return d_maxAge;
   }

   /**
    * Return the expiration date of the cookie.  If the cookie has
    * a maximum age attribute, it should have an expiration date based on
    * the cookie's creation time and maximum age attribute.
    *
    * @return <code>null</code> if no maximum age setting; otherwise,
    * the date and time that the cookie should cease to exist.
    */
   public synchronized Date getExpiration()
   {
      return d_expiration;
   }

   /**
    * Return the path attribute of the cookie.
    *
    * @return a path on a particular server.
    */
   public synchronized String getPath()
   {
      return d_path;
   }

   /**
    * Return the ports that the cookie is for.
    *
    * @return <code>null</code> (means no restriction on ports) or a
    * comma separated list of ports.
    */
   public synchronized String getPorts()
   {
      return d_ports;
   }

   /**
    * Indicate whether the cookie is intended for secure connections only.
    * 
    * @return <code>true</code> means the cookie should only be sent on
    *         a secure channel; <code>false</code> means the cookie can
    *         be sent on secure on unsecured channels.
    */
   public synchronized boolean isSecure()
   {
      return d_secure;
   }

   /**
    * Indicate whether the cookie should be discarded when the user agent
    * exits.
    * 
    * @return <code>true</code> means that the cookie should be discarded
    * when the user agent exits; <code>false</code> means that the cookie
    * should be persisted until it expires.
    */
   public synchronized boolean isDiscardable()
   {
      return d_discard;
   }

   /**
    * Return the cookie specification version number.
    * 
    * @return <code>null</code> if the client hasn't specified the cookie
    *         specification version; otherwise a string holding the
    *         cookie spec. version.
    */
   public synchronized String getVersion()
   {
      return d_version;
   }

   /**
    * Check whether the domain name can be used by a particular host.
    */
   public synchronized boolean hasValidDomain(String hostname)
   {
      String effective = effectiveHostname(hostname);
      return
         (d_domain.equalsIgnoreCase(effective) ||
          ((d_domain.length() > 1) &&
           (effective.length() > d_domain.length()) &&
           (d_domain.charAt(0) == '.') &&
           (effective.indexOf('.') == 
            (effective.length() - d_domain.length())) &&
           effective.regionMatches(true, 
                                   effective.length() - d_domain.length(),
                                   d_domain, 0, d_domain.length())));
   }

   /* MUTATORS */
   
   /**
    * Change the value of the cookie.
    *
    * @param value  This is the value of the cookie.  If the value is
    *               coming from an incomming HTTP/1.1 header, it
    *               should be run through <code>decodeValue</code> before
    *               passing it into here.
    * @throws gov.llnl.babel.url.cookie.CookieCrumbleException
    * indicates that the value is not legal
    */
   public synchronized void setValue(String value)
        throws CookieCrumbleException
   {
      if (validValue(value)){
         d_value = value;
      }
      else {
         throw new CookieCrumbleException
            ("Cookie value is not valid \"" +
             value + "\"");
      }
   }
   
   /**
    * Change the comment attribute of the cookie.  The specification
    * requires the comment to be UTF-8 encoded.
    *
    * @param comment  This is the cookie's comment.  If the comment is
    *               coming from an incomming HTTP/1.1 header, it
    *               should be run through <code>decodeValue</code> before
    *               passing it into here.
    * @throws gov.llnl.babel.url.cookie.CookieCrumbleException
    * indicates that the comment is not a legal value
    */
   public synchronized void setComment(String comment)
        throws CookieCrumbleException
   {
      if (validValue(comment)){
         d_comment = comment;
      }
      else {
         throw new CookieCrumbleException
            ("Cookie comment is not valid \"" +
             comment + "\"");
      }
   }

   /**
    * Change the URL comment attribute of the cookie.
    *
    * @param comment  This is the cookie's URL comment.  If the comment is
    *               coming from an incomming HTTP/1.1 header, it
    *               should be run through <code>decodeValue</code> before
    *               passing it into here.
    * @throws gov.llnl.babel.url.cookie.CookieCrumbleException
    * The URL is not valid.
    */
   public synchronized void setUrlComment(String comment)
        throws CookieCrumbleException
   {
      if (comment == null ||
          (validValue(comment) &&
           comment.startsWith("http:"))){
         d_urlComment = comment;
      }
      else{
         throw new CookieCrumbleException
            ("Cookie comment URL is not valid \"" + comment + "\"");
      }
   }
   
   /** 
    * Set the domain of the request.  This limits which machines will
    * be sent the cookie.
    * 
    * @param domain this should be a domain name, a hostname or .local.
    * @throws gov.llnl.babel.url.cookie.CookieCrumbleException
    *      indicates that the domain is not acceptable
    */
   public synchronized void setDomain(String domain)
        throws CookieCrumbleException
   {
      if (validValue(domain)){
         d_domain = domain;
      }
      else{
         throw new CookieCrumbleException
            ("Cookie domain value is not valid \"" + domain + "\"");
      }
   }

   /** 
    * Set the maximum age and expiration date of the cookie.
    *
    * @param maxAge   a non-negative integer value indicating the
    *                 maximum age in seconds
    * @param created  best estimate of when the cookie was created
    * @throws gov.llnl.babel.url.cookie.CookieCrumbleException
    *    indicates that maxAge or created was invalid
    */
   public synchronized void setMaxAge(int maxAge,
                                      Date created)
      throws CookieCrumbleException
   {
      if (maxAge >= 0 && created != null){
         d_maxAge = maxAge;
         if (maxAge > 0){
            d_expiration = new Date(created.getTime() + 
                                    1000L * (long)maxAge);
            if (!d_discardExplicit){
               d_discard = false;
            }
         }
         else {
            d_expiration = new Date(1); // earliest possible date
         }
      }
      else {
         throw new CookieCrumbleException
            ("Cookie maximum age and/or creation date is illegal maxAge = "
             + maxAge);
      }
   }

   /**
    * Set the path.
    * 
    * @param path the leading elements of a URL path that limits the
    *             scope of the cookie
    * @throws gov.llnl.babel.url.cookie.CookieCrumbleException
    *    indicates that the path is invalid
    */
   public synchronized void setPath(String path)
      throws CookieCrumbleException
   {
      if (validValue(path)){
         d_path = path;
      }
      else {
         throw new CookieCrumbleException
            ("Cookie path is invalid \"" + path + "\"");
      }
   }

   /**
    * Set the ports that are allowed to get the cookie.
    * 
    * @param ports a list of ports that are allowed to send/receive the
    *              cookie. 
    * @throws gov.llnl.babel.url.cookie.CookieCrumbleException
    *    indicates that the path is invalid
    */
   public synchronized void setPorts(String ports)
      throws CookieCrumbleException
   {
      if (validValue(ports) && validPortList(ports)){
         d_ports = ports;
      }
      else {
         throw new CookieCrumbleException 
            ("Cookie port list is not valid \"" + ports + "\"");
      }
   }

   /**
    * Conditionally set the secure attribute of the cookie.
    *
    * @param secure <code>true</code> will make the cookie require a secure
    *               channel; <code>false</code> will allow the cookie
    *               to be communicated via unsecure channels.
    */
   public synchronized void setSecure(boolean secure)
   {
      d_secure = secure;
   }

   /**
    * Conditionally set the discard attribute of the cookie.
    *
    * @param discard <code>true</code> will mark the cookie to be discarded
    *                while the user agent exits; <code>false</code> will
    *                mark the cookie to be persisted by its environment.
    */
   public synchronized void setDiscardable(boolean discard)
   {
      d_discard = discard;
      d_discardExplicit = true;
   }

   /**
    * Set the version of the cookie protocol.
    *
    * @param version Set the version of the protocol used by this cookie.
    */
   public synchronized void setVersion(String version)
   {
      d_version = version;
   }

   /**
    * Check if the cookie has expired.
    *
    * @param currentTime use this as the current time in milliseconds since 
    *        January 1, 1970.
    * @return <code>true</code> means the cookie's expiration time has
    *         past; <code>false</code> means the cookie has not expired.
    */
   public synchronized boolean hasExpired(long currentTime)
   {
      return ((d_expiration != null) &&
              (d_expiration.getTime() <= currentTime));
   }

   /**
    * Return <code>true</code> iff <code>port</code> is allowed to
    * send/receive this cookie.
    *
    * @param port the port whose access should be checked.
    * @return <code>true</code> means that <code>port</code> may receive
    *         the cookie; <code>false</code> means <code>port</code>
    *         must not receive the cookie.
    */
   public synchronized boolean isPortAllowed(int port)
   {
      if (d_ports == null){
         return true;
      }
      else {
         StringTokenizer tok = new StringTokenizer(d_ports, ",");
         while (tok.hasMoreTokens()){
            try {
               if (port == Integer.parseInt(tok.nextToken().trim())){
                  return true;
               }
            }
            catch (NumberFormatException nfe){
               // should not occur (tested this already)
            }
         }
      }
      return false;
   }

   /** 
    * Check if a hostname domain matches this cookie.
    */
   private boolean domainMatch(String hostname)
   {
      String effective = effectiveHostname(hostname);
      if (d_domain.equalsIgnoreCase(effective)){
         return true;
      }
      return 
         ((effective.length() > d_domain.length()) &&
          (d_domain.length() > 1) &&
          (d_domain.charAt(0) == '.') &&
          (effective.regionMatches(true, 
                                   effective.length() -
                                   d_domain.length(),
                                   d_domain, 0, d_domain.length())));
   }

   /**
    * Return true if two cookies are equal.
    */
   public synchronized boolean equals(Cookie c)
   {
      return
         d_name.equals(c.getName()) &&
         d_domain.equalsIgnoreCase(c.getDomain()) &&
         d_path.equals(c.getPath());
   }

   /**
    * Check if the cookie should be sent with a particular request.
    *
    * @param hostname The hostname the sent or may receive the cookie.
    * @param path     The filename of the request.
    * @param port     The port number of the request.
    * @return <code>true</code> means the cookie should be sent to the 
    *         server; <code>false</code> means the cookie  <strong>must
    *         not</strong> be sent to the server.
    */
   public synchronized boolean isRelevant(String hostname,
                                          String path,
                                          int port)
   {
      if ((d_domain == null) ||
          (d_path == null)){
         return false;
      }
      return 
         domainMatch(hostname) &&
         isPortAllowed(port) &&
         path.startsWith(d_path);
   }

   /**
    * Create a string suitable for including in a HTTP "Cookie:" header.
    * This assumes that the version has already been inserted.
    *
    * @return a string suitable to include in a "Cookie:" header
    */
   public synchronized String toString()
   {
      StringBuffer result = new StringBuffer();
      result.append('\t').
         append(d_name).
         append("=\"").
         append(encodeValue(d_value)).
         append("\"; $Path=\"").
         append(encodeValue(d_path)).
         append('"');
      return result.toString();
   }
}
