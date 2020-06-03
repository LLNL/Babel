//
// File:        LanguageWriterForPython.java
// Package:     gov.llnl.babel.backend.writers
// Revision:    @(#) $Id: LanguageWriterForPython.java 7188 2011-09-27 18:38:42Z adrian $
// Description: Python language writer for backend code generation
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

package gov.llnl.babel.backend.writers;

import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeConstants;
import gov.llnl.babel.backend.writers.LanguageWriter;
import gov.llnl.babel.xml.XMLUtilities;
import java.io.PrintWriter;

/**
 * Class <code>LanguageWriterForPython</code> extends the generic language
 * writer to provide additional capabilities for writing Python code.
 */
public class LanguageWriterForPython extends LanguageWriter 
  implements CodeConstants 
{
   /**
    * Create a <code>LanguageWriterForPython</code> instance that will send
    * output to the specified print writer object.  Set up the output
    * properties of the language writer for Python, which sets the first tab
    * stop at zero, a tab spacing of three, and line breaking at column 78
    * with the Python continuation character.
    */
   public LanguageWriterForPython(PrintWriter writer, Context context) {
      super(writer, context);
      setFirstTabStop(0);
      setTabSpacing(2);
      enableLineBreak(78, null, C_BACKSLASH);
      defineBlockComment(null, C_HASH + " ");
   }

   /**
    * Begin a block comment.  The beginning of comment string is output
    * to the pretty writer stream and block comment mode is set.  This
    * comment block must be terminated by a call to the end comment block
    * method.  There is no difference between documentation comments and
    * regular comments in Python.
    */
   public void beginBlockComment(boolean is_doc) {
      println(C_HASH);
      enableBlockComment();
   }

   /**
    * End a block comment.  This method outputs the end of comment string
    * and sets pretty writer back to normal output mode.  In Python, there
    * is no difference between documentation and regular comments.
    */
   public void endBlockComment(boolean is_doc) {
      disableBlockComment();
      println(C_HASH);
      println();
   }

   /**
    * Write the splicer tag as a language-specific comment.
    */
   public void writeSplicerTagLine(String tagLine) {
     disableLineBreak();
     writeCommentLine(tagLine);
     enableLineBreak();
   }

   /**
    * Output a single-line comment to the pretty writer stream.  The comment
    * should not contain any new line characters.  If the comment is null,
    * then nothing is output.
    */
   public void writeCommentLine(String comment) {
      if (comment != null) {
         printlnUnformatted(C_HASH + " " + comment);
      }
   }

   /**
    * Convert a string into a form in which it can safely be included in 
    * a Python multi-line string. This will backquote the backquote
    * character '\' and the quote character '"'.
    *
    * @param str a string
    * @return a string with the "special" characters quoted.
    */
   public static String toPythonString(String str) {
      final int len = str.length();
      int firstBslash = str.indexOf(C_INT_BACKSLASH);
      int firstQuote = str.indexOf('\"');
      firstBslash = ((firstBslash < 0) ? len : firstBslash);
      firstQuote = ((firstQuote < 0) ? len : firstQuote);
      final int safeChars = Math.min(firstBslash, firstQuote);
      if (safeChars < len) {
         char [] orig = str.toCharArray();
         StringBuffer buf = new StringBuffer(len+1);
         buf.append(orig, 0, safeChars);
         for(int i = safeChars; i < len ; i++){
            switch(orig[i]){
            case C_INT_BACKSLASH:
               buf.append(C_BACKSLASH + C_BACKSLASH);
               break;
            case '\"':
               buf.append(C_BACKSLASH + "\"");
               break;
            default:
               buf.append(orig[i]);
               break;
            }
         }
         str = buf.toString();
      }
      return str;
   }

  public final String processHTML(String str) {
    return XMLUtilities.decodeXMLString(str);
  }

}
