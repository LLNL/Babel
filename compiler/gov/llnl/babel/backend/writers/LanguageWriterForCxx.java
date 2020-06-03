//
// File:        LanguageWriterForCxx.java
// Package:     gov.llnl.babel.backend.writers
// Revision:    @(#) $Id: LanguageWriterForCxx.java 7188 2011-09-27 18:38:42Z adrian $
// Description: C++ language writer for backend code generation
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
import gov.llnl.babel.backend.writers.LanguageWriterForC;
import java.io.PrintWriter;

/**
 * Class <code>LanguageWriterForCxx</code> extends the generic language
 * writer to provide additional capabilities for writing C++ code.  This
 * class manages writing comment blocks as well as include files.
 */
public class LanguageWriterForCxx extends LanguageWriterForC 
  implements CodeConstants
{
   /**
    * Create a <code>LanguageWriterForCxx</code> instance that will send
    * output to the specified print writer object.  Set up the output
    * properties of the language writer for C++, which sets the first tab
    * stop at zero, a tab spacing of three, and line breaking at column
    * eighty.
    */
   public LanguageWriterForCxx(PrintWriter writer, Context context) {
      super(writer, context);
      defineBlockComment("",C_COMMENT_SLASH);
      enableLineBreak(80,null,null);
   }

   /**
    * Create a <code>LanguageWriterForCxx</code> instance that will send
    * output to the specified print writer object.  Set up the output
    * properties of the language writer for C++, which sets the first tab
    * stop at zero, a tab spacing of three, and line breaking at column
    * eighty.
    */
   public LanguageWriterForCxx(PrintWriter writer, 
                               LineCountingFilterWriter lcfw,
                               Context context) 
  {
      super(writer, lcfw, context);
      defineBlockComment("",C_COMMENT_SLASH);
      enableLineBreak(80,null,null);
   }

   /**
    * Begin a block comment.  The beginning of comment string is output
    * to the pretty writer stream and the block comment mode is enabled.
    * The comment block is closed by calling the end comment block method.
    * Documentation comments use traditional slash-asterisk-asterisk
    * method, but regular comments use C++ slash-slash
    */
   public void beginBlockComment(boolean is_doc) {
      if (is_doc) {
         println(C_COMMENT_DOC_OPEN);
         defineBlockComment("", C_COMMENT_SUBSEQUENT );
      } else {
         println(C_COMMENT_SLASH);
      }
      enableBlockComment();
   }

   /**
    * End a block comment.  This method disables comment block output and
    * prints a blank comment string.  The end of a cocumentation comment
    * also signifies resetting to the tradtional slash-slash approach for
    * C++
    */
   public void endBlockComment(boolean is_doc) {
      disableBlockComment();
      if (is_doc) { 
        println(" " + C_COMMENT_CLOSE );
        defineBlockComment("",C_COMMENT_SLASH);
      } else { 
        println(C_COMMENT_SLASH);
      }
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
        try {
          pushLineBreak(false);
          println(C_COMMENT_SLASH + comment);
        }
        finally {
          popLineBreak();
        }
      }
   }

  /** 
   * Start a comment that is made visually prominent
   */
  public void beginBoldComment() { 
    println();
    println("//////////////////////////////////////////////////");
    beginBlockComment(false);
  }  

  public void endBoldComment() { 
    endBlockComment(false);
    println();
  }  
  
  public String getIncludeGuard(String file) {
    if (file.endsWith(".hh")) {
      int index = file.length();
      index -= 3;
      return "included_" + file.substring(0, index) + "_hh";
    } 
    else if (file.endsWith(".hxx")) {
      int index = file.length();
      index -= 4;
      return "included_" + file.substring(0, index) + "_hxx";
    }
    else { 
      return super.getIncludeGuard( file );
    }

  }

}
