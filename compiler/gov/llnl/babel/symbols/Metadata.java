//
// File:        Metadata.java
// Package:     gov.llnl.babel.symbols
// Revision:    @(#) $Id: Metadata.java 7188 2011-09-27 18:38:42Z adrian $
// Description: metadata associated with a symbol (e.g., creation date)
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

package gov.llnl.babel.symbols;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The <code>Metadata</code> class represents the metadata associated with
 * a symbol.  Most of the data is represented as a hash table of keyword
 * value pairs except for the required date attribute.  The string version
 * of the date is stored as "yyyyMMdd HH:mm:ss zzzz".
 */
public class Metadata {
   public final static String FORMAT = "yyyyMMdd HH:mm:ss zz";
   
   private Date    d_date;    // date metadata
   private HashMap d_keyvals; // (keyword,value) pairs

   /**
    * This constructor for the <code>Metadata</code> class takes a date
    * string as an argument and throws a <code>ParseException</code> if
    * it is not in the proper format.
    */
   public Metadata(String date) throws ParseException {
      DateFormat datetime = new SimpleDateFormat(FORMAT);
      d_date              = datetime.parse(date);
      d_keyvals           = new HashMap();
   }

   /**
    * This constructor for the <code>Metadata</code> class takes a date
    * object as an argument.
    */
   public Metadata(Date date) {
      d_date    = date;
      d_keyvals = new HashMap();
   }

   /**
    * Add a (keyword,value) pair to the metadata description.  If the
    * keyword already exists in the metadata database, then the old value
    * will be over-written with the new value.
    */
   public void addMetadata(String keyword, String value) {
      d_keyvals.put(keyword, value);
   }

   /**
    * Return the <code>java.util.Date</code> date associated with this symbol.
    */
   public Date getDate() {
      return d_date;
   }

   /**
    * Return the date associated with this symbol as a string.
    */
   public String getDateAsString() {
      DateFormat datetime = new SimpleDateFormat(FORMAT);
      return datetime.format(d_date);
   }

   /**
    * Retrieve the set of keys from metadata database.  Each of the keys
    * in the <code>Set</code> is a string object.
    */
   public Set getMetadataKeys() {
      return d_keyvals.keySet();
   }

   /**
    * Retrieve the metadata database in the form of a <code>Map</code>.
    * The database consists of (keyword,value) pairs of strings.
    */
   public Map getMetadataDatabase() {
      return d_keyvals;
   }

   /**
    * Retrieve the value associated with the specified keyword.  If the
    * keyword does not exist in the database, then a null is returned.
    */
   public String getMetadataValue(String keyword) {
      return (String) d_keyvals.get(keyword);
   }
}
