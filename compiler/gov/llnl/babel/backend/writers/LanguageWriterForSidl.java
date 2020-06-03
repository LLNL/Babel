//
// File:        LanguageWriterForSidl.java
// Package:     gov.llnl.babel.backend.writers
// Revision:    @(#) $Id: LanguageWriterForSidl.java 7188 2011-09-27 18:38:42Z adrian $
// Description: sidl language writer for backend code generation
//
// Copyright (c) 2002, Lawrence Livermore National Security, LLC
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
import gov.llnl.babel.backend.writers.LanguageWriterForC;
import java.io.PrintWriter;

/**
 * Class <code>LanguageWriterForSidl</code> extends the generic language
 * writer to provide additional capabilities for writing sidl code.
 */
public class LanguageWriterForSidl extends LanguageWriter 
  implements CodeConstants 
{
   /**
    * Create a <code>LanguageWriterForSidl</code> instance that will send
    * output to the specified print writer object.  Set up the output
    * properties of the language writer for sidl, which sets the first tab
    * stop at zero, a tab spacing of three, and line breaking at column
    * eighty.
    */
   public LanguageWriterForSidl(PrintWriter writer, Context context) {
      super(writer, context);
      setFirstTabStop(0);
      setTabSpacing(2);
      enableLineBreak(80, null, null);
      defineBlockComment(null, C_COMMENT_SUBSEQUENT);
   }

   /**
    * Begin a block comment.  The beginning of comment string is output
    * to the pretty writer stream and the block comment mode is enabled.
    * The comment block is closed by calling the end comment block method.
    * Documentation comments add an additional asterisk after the open
    * comment.
    */
   public void beginBlockComment(boolean is_doc) {
      if (is_doc) {
         println(C_COMMENT_DOC_OPEN);
      } else {
         println(C_COMMENT_OPEN);
      }
      enableBlockComment();
   }

   /**
    * End a block comment.  This method disables comment block output and
    * prints the end of comment string.  Documentation comments do not add
    * a blank line whereas regular comments add a blank line after the
    * comment.
    */
   public void endBlockComment(boolean is_doc) {
      disableBlockComment();
      println(" " + C_COMMENT_CLOSE);
      if (!is_doc) {
         println();
      }
   }

  /**
   * (Is this appropriate?) Write the splicer tag as a language-specific 
   * comment.
   */
  public void writeSplicerTagLine(String tagLine) {
   /* No op */
  }


  /**
   * Convert a string into something that is safe to include inside a sidl
   * comment string.
   */
  public final String safeCommentString(String str) {
    return LanguageWriterForC.toSafeComment(str);
  }

   /**
    * Output a single-line comment to the pretty writer stream.  The comment
    * should not contain any new line characters.  If the comment is null,
    * then nothing is output.
    */
   public void writeCommentLine(String comment) {
      if (comment != null) {
         println(C_SLASH + " " + comment);
      }
   }
}
