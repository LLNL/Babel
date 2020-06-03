//
// File:        Lexer.java
// Package:     gov.llnl.babel.url.cookie
// Revision:    @(#) $Id: Lexer.java 7188 2011-09-27 18:38:42Z adrian $
// Description: A lexer for the cookie header
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

import gov.llnl.babel.url.cookie.LexicalException;
import gov.llnl.babel.url.cookie.Token;
import java.io.Reader;

/**
 * A class to convert text from the value of a HTTP Set-Cookie2 to a stream
 * of lexical tokens.
 */
public class Lexer {
   /**
    * The lexer is scanning for the beginning of the next token.
    */
   private static final int s_BETWEEN = 0;

   /**
    * The lexer is scanning a TOKEN type token.
    */
   private static final int s_TOKEN = 1;

   /**
    * The lexer is scanning a quoted string type token.
    */
   private static final int s_STRING = 2;

   /**
    * The lexer saw a backslash in a quoted string.
    */
   private static final int s_STRING_QUOTE = 3;

   /**
    * The lexer saw an unquoted carriage return in a quoted string.
    */
   private static final int s_STRING_CR = 4;

   /**
    * The lexer saw an unquoted carriage return newline in a quoted string.
    */
   private static final int s_STRING_NL = 5;

   /**
    * This stream of characters is translated into a stream of tokens.
    */
   private Reader d_charSource = null;

   /**
    * The current lexical state.
    */
   private int d_state = s_BETWEEN;

   /**
    * The current text.
    */
   private char [] d_text;

   /**
    * The current number of characters.
    */
   private int d_charCount = 0;

   /**
    * The lexer can buffer one character here
    */
   private int d_pushBack;

   /**
    * This becomes true when d_pushBack is storing the next character.
    */
   private boolean d_pushed = false;

   /**
    * Create a lexer and give provide it with a stream of characters.
    */
   public Lexer(Reader charSource)
   {
      d_charSource = charSource;
      d_text = new char[256];
   }

   private void pushBack(int ch)
   {
      d_pushBack = ch;
      d_pushed = true;
      d_charCount--;
   }

   private void reset()
   {
      d_charCount = 0;
      d_state = s_BETWEEN;
   }

   /**
    * Get the next character and if it's not the end of stream, add it to
    * the text.
    */
   private int getNextChar()
      throws java.io.IOException
   {
      int ch;
      if (d_pushed){
         ch = d_pushBack;
         d_pushed = false;
      }
      else{
         ch = d_charSource.read();
      }
      if (ch >= 0){
         if (d_charCount >= d_text.length){
            // allocate more space for the current text
            char [] newarray = 
               new char[d_text.length + (d_text.length >> 1)];
            System.arraycopy(d_text, 0, newarray, 0, d_text.length);
            d_text = newarray;
         }
         d_text[d_charCount++] = (char)ch;
      }
      return ch;
   }

   /**
    * Return <code>true</code> iff <code>ch</code> is a valid character in a 
    * token.
    *
    * @param ch the character to check
    * @return <code>true</code> means <code>ch</code> is a valid character
    *         in a token
    */
   public static boolean validTokenChar(char ch)
   {
      return !((ch >= '\u007f') || 
               (ch < '\u0020') ||
               ((ch >= ':') && (ch <= '@')) ||
               ((ch >= '[') && (ch <= ']')) ||
               (ch == '(') || (ch == ')') || (ch == ',') ||
               (ch == '"') || (ch == '/') || (ch == '{') || (ch == '}') ||
               Character.isWhitespace(ch));
   }

   /**
    * Find the next token.
    *
    * @throws java.io.IOException
    *      If the character source issues an IOException, you will know.
    */
   public Token nextToken() throws java.io.IOException, LexicalException {
      int ch;
      reset();
      for(;;){
         ch = getNextChar();
         switch(d_state){
         case s_BETWEEN:
            if ((ch == '=') || (ch == ',') || (ch == ';')) {
               return new Token(ch, new String(d_text, d_charCount - 1, 1));
            }
            else if (ch == '"'){
               d_charCount--;
               d_state = s_STRING;
            }
            else if (Character.isWhitespace((char)ch)){
               d_charCount--;     // don't accumulate white space characters
            }
            else if (validTokenChar((char)ch)){
               d_state = s_TOKEN; // start scanning a token
            }
            else if (ch == -1){
               return new Token(Token.s_END_OF_STREAM, "");
            }
            else {
               throw new LexicalException
                  ("Unacceptable character in cookie stream '" + ch +
                   "'  '\\u" + Integer.toHexString(ch) + "'");
            }
            break;
         case s_TOKEN:
            if (!validTokenChar((char)ch)){
               pushBack(ch);
               return new Token(Token.s_TOKEN,
                                new String(d_text, 0, d_charCount));
            }
            break;
         case s_STRING:
            if (ch == '\\'){     // next character quoted
               d_state = s_STRING_QUOTE;
            }
            else if (ch == '"'){ // end of string
               return new Token(Token.s_QUOTED, 
                                new String(d_text, 0, d_charCount - 1));
            }
            else if (ch == '\r'){ // beginning of LWS?
               d_state = s_STRING_CR;
            }
            else if (Character.isISOControl((char)ch) && (ch != '\t')){
               throw new LexicalException
                  ("Unacceptable character in quoted string '" + ch +
                   "' '\\u" + Integer.toHexString(ch) + "'");
            }
            else if (ch == -1){
               throw new LexicalException
                  ("Unterminated quoted string end of string encountered");
            }
            break;
         case s_STRING_QUOTE:
            if ((ch >= 0) && (ch <= 127)) {
               d_state = s_STRING;
            }
            else {
               throw new LexicalException
                  ("Bad character following backslash in quoted string.");
            }
            break;
         case s_STRING_CR:
            if (ch == '\n'){
               d_state = s_STRING_NL;
            }
            else{
               throw new LexicalException
                  ("Unquoted carriage return without newline following.");
            }
            break;
         case s_STRING_NL:
            if ((ch == '\t') || (ch == ' ')){
               d_state = s_STRING;
            }
            else{
               throw new LexicalException
                  ("Unquoted carriage return newline without tab or space following");
            }
            break;
         default:
            throw new LexicalException
               ("Unexpected state in cookie lexer = " + d_state);
         }
      }
   }
}
