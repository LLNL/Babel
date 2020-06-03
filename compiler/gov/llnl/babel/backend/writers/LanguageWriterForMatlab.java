//
// File:        LanguageWriterForMatlab.java
// Package:     gov.llnl.babel.backend.writers
// Revision:    @(#) $Id: LanguageWriterForMatlab.java 7188 2011-09-27 18:38:42Z adrian $
// Description: C language writer for backend code generation
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
import gov.llnl.babel.backend.writers.LineRedirector;
import gov.llnl.babel.backend.writers.LineCountingFilterWriter;
import gov.llnl.babel.xml.XMLUtilities;
import java.io.File;
import java.io.PrintWriter;

/**
 * Class <code>LanguageWriterForMatlab</code> extends the generic language
 * writer to provide additional capabilities for writing C code.  This
 * class manages writing comment blocks as well as include files and
 * C++ external declaration guards.
 */
public class LanguageWriterForMatlab extends LanguageWriter 
    implements CodeConstants, LineRedirector
{

    protected boolean d_skipIncludeGuard = false;

    protected LineCountingFilterWriter d_lcfw = null;

   /**
    * Create a <code>LanguageWriterForMatlab</code> instance that will send
    * output to the specified print writer object.  Set up the output
    * properties of the language writer for C, which sets the first tab
    * stop at zero, a tab spacing of three, and line breaking at column
    * eighty.
    */
   public LanguageWriterForMatlab(PrintWriter writer, Context context) {
      super(writer, context);
      d_lcfw = null;
      setFirstTabStop(0);
      setTabSpacing(2);
      enableLineBreak(80, null, null );
      defineBlockComment(null, C_COMMENT_SUBSEQUENT);
   }


// this file is copied from LanguageWriterForC and modified 
// the functions below is not used. might be removed later.






   /**
    * Create a <code>LanguageWriterForMatlab</code> instance that will send
    * output to the specified print writer object.  Set up the output
    * properties of the language writer for C, which sets the first tab
    * stop at zero, a tab spacing of three, and line breaking at column
    * eighty.
    */
   public LanguageWriterForMatlab(PrintWriter writer, 
                                  LineCountingFilterWriter lcfw,
                                  Context context)
  {
      super(writer, context);
      d_lcfw = lcfw;
      setFirstTabStop(0);
      setTabSpacing(2);
      enableLineBreak(80, null, null );
      defineBlockComment(null, C_COMMENT_SUBSEQUENT);
   }

   /**
    * (Is this appropriate?) Write the splicer tag as a language-specific 
    * comment.
    */
   public void writeSplicerTagLine(String tagLine) {
     disableLineBreak();
     writeCommentLine(tagLine);
     enableLineBreak();
   }

   /**
    * Turns openHeaderGuard & closeHeaderGuard into no-ops.
    * Used for package headers to address Roundup issue 93.
    */
   public void skipIncludeGuard() { 
       d_skipIncludeGuard=true;
   }

   /**
    * Mark a region where the debugger should be redirected to another file 
    * starting on a specific line.
    */
   public void redirectBegin( String path, int line ) { 
     if (File.separatorChar == '\\') {
       path = path.replace('\\', '/');
     }
     printlnUnformatted("#line " + line + " \"" + path + "\"");
   }

   /**
    * End the region where the debugger can just follow this file.
    */
   public void redirectEnd( String path, int line ) {
     if (File.separatorChar == '\\') {
       path = path.replace('\\', '/');
     }
     printlnUnformatted("#line " + line + " \"" + path + "\"");
   }

   /**
    * get current line (returns -1 iff lfcw == null )
    */
    public int getLineCount() { 
        if ( d_lcfw == null ) { 
            return -1;
        } else { 
            return d_lcfw.getLineCount();
        }
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
    * Output a single-line comment to the pretty writer stream.  The comment
    * should not contain any new line characters.  If the comment is null,
    * then nothing is output.
    */
   public void writeCommentLine(String comment) {
      if (comment != null) {
         println(MATLAB_COMMENT_OPEN + " " + comment);
      }
   }

  
   /**
    * Generate a user include statement for the specified include file.
    * If the guard flag is true, then the include statement will contain
    * redundant external include guards.
    */
   public void generateInclude(String file, boolean guards) {
      if (guards) {
         printUnformatted(C_GUARD_OPEN);
         printlnUnformatted(getIncludeGuard(file));
      }
      printUnformatted(C_INCLUDE + "\"");
      printUnformatted(file);
      printlnUnformatted("\"");
      if (guards) {
         printlnUnformatted(C_GUARD_CLOSE);
      }
      d_context.getDependencies().addInput(file);
   }

   /**
    * Generate a system include statement for the specified include file.
    */
   public void generateSystemInclude(String file) {
      printUnformatted(C_INCLUDE + "<");
      printUnformatted(file);
      printlnUnformatted(">");
      // Dependencies.addInput(file); might revisit. policy is no logging now.
   }

   /**
    * Open the header include guard at the top of the header file.
    */
   public void openHeaderGuard(String file) {
      if ( ! d_skipIncludeGuard ) { 
	  String guard = getIncludeGuard(file);
	  printlnUnformatted(C_GUARD_OPEN + guard);
	  printlnUnformatted(C_DEFINE + guard);
	  println();
      }
   }

   /**
    * Close the header guard at the end of the header file.
    */
   public void closeHeaderGuard() {
       if ( ! d_skipIncludeGuard ) { 
	   printlnUnformatted(C_GUARD_CLOSE);
       }
   }

   /**
    * Open the C++ extern block at the top of the header file.
    */
   public void openCxxExtern() {
      printlnUnformatted(C_IFDEFINE_CXX);
      println("extern \"C\" {");
      printlnUnformatted(C_GUARD_CLOSE);
      println();
   }

   /**
    * Close the C++ extern block at the end of the header file.
    */
   public void closeCxxExtern() {
      printlnUnformatted(C_IFDEFINE_CXX);
      println("}");
      printlnUnformatted(C_IFDEFINE_CLOSE);
   }

   /**
    * Generate the include guard from the specified file name.  The guard
    * is the file name (without extension, if it exists) with an "included_"
    * prefix.
    */
   public String getIncludeGuard(String file) {
      int index = file.length();
      String suffix = "";
      if (file.endsWith(".h")) {
         index -= 2;
         suffix = "_h";
      }
      return "included_" + file.substring(0, index) + suffix;
   }

  /**
   * Convert a string into a string that can safely be included inside
   * a C string. It will convert a backslash to a double backslash.
   * It will convert a quote into a backslash quote.
   * It will convert control characters into a backslashed character.
   *
   * @param str   a string to make safe for inclusion in a string
   * @return      a string with the special characters changed to
   *              a safe form.
   */
  public static String toSafeString(String str) {
    final int len = str.length();
    StringBuffer buf = new StringBuffer(len);
    for(int i = 0; i < len; i++) {
      char ch = str.charAt(i);
      switch(ch) {
      case C_INT_BACKSLASH:
      case '\"':
        buf.append((char)C_INT_BACKSLASH);
        buf.append(ch);
        break;
      case '\r':
        buf.append(C_BACKSLASH + "r");
        break;
      case '\n':
        buf.append(C_BACKSLASH + "n");
        break;
      case '\b':
        buf.append(C_BACKSLASH + "b");
        break;
      case '\t':
        buf.append(C_BACKSLASH + "t");
        break;
      case '\f':
        buf.append(C_BACKSLASH + "f");
        break;
      default:
        if (ch >= 0 && ch <= 26) {
          String oct = Integer.toOctalString(ch);
          int leadingZeros = 3 - oct.length();
          buf.append((char)C_INT_BACKSLASH);
          while (leadingZeros-- > 0) {
            buf.append('0');
          }
          buf.append(oct);
        }
        else {
          buf.append(ch);
        }
        break;
      }
    }
    return buf.toString();
  }
  
  /**
   * Convert a string into a string that can safely be included inside
   * a C comment. This will insert a space between a slash '/' and an
   * asterisk '*' to prevent opening a recursive comment or prematurely
   * ending a comment.
   * 
   * @param str a string to make safe for inclusion in a comment
   * @return    the string with starting and ending comments changed
   *            to something safe.
   */
  public final String safeCommentString(String str) {
    return toSafeComment(str);
  }

   /**
    * Convert a string into a string that can safely be included inside
    * a C comment. This will insert a space between a slash '/' and an
    * asterisk '*' to prevent opening a recursive comment or prematurely
    * ending a comment.
    * 
    * @param str  a string to make safe for inclusion in a comment
    * @return     a string with starting and ending comments changed
    *             to something safe.
    */
   public static String toSafeComment(String str) {
      final int len = str.length();
      int startComment = str.indexOf(C_COMMENT_OPEN);
      int endComment = str.indexOf(C_COMMENT_CLOSE);
      startComment = ((startComment < 0) ? len : startComment);
      endComment = ((endComment < 0) ? len : endComment);
      final int safeChars = Math.min(startComment, endComment);
      if (safeChars < len) {
         char [] orig = str.toCharArray();
         StringBuffer buf = new StringBuffer(len + 1);
         buf.append(orig, 0, safeChars);
         for(int i = safeChars; i < (len - 1); i++){
            buf.append(orig[i]);
            if ((orig[i] == '*' && orig[i+1] == '/') ||
                (orig[i] == '/' && orig[i+1] == '*')){
               buf.append(' ');
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
