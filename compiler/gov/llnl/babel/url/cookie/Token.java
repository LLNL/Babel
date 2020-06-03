//
// File:        Token.java
// Package:     gov.llnl.babel.url.cookie
// Revision:    @(#) $Id: Token.java 7188 2011-09-27 18:38:42Z adrian $
// Description: A token from the Cookie parser
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

/**
 * A class to store each lexical token discovered while parsing the cookie.
 */
final public class Token {
   /**
    * Indicate the end of the stream
    */
   public static final int s_END_OF_STREAM = 0;
   
   /**
    * An identifier (informally a sequence of non-special, non-white
    * space characters).
    */
   public static final int s_TOKEN = 1;

   /**
    * A quoted string
    */
   public static final int s_QUOTED = 2;

   /**
    * An equal character
    */
   public static final int s_EQUAL = '=';

   /**
    * A comma
    */
   public static final int s_COMMA = ',';

   /**
    * A semicolon
    */
   public static final int s_SEMICOLON = ';';

   /**
    * Store the token type
    */
   private int d_type;

   /**
    * Store the token text
    */
   private String d_text;

   /**
    * Return one of the constants defined above to indicate the type of the
    * token.
    */
   public int getType() {
      return d_type;
   }

   /**
    * Return the text of the token.
    */
   public String getText() {
      return d_text;
   }

   /**
    * Create a token
    */
   public Token(int type, String text) {
      d_type = type;
      d_text = text;
   }
};
