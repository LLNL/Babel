//
// File:        PrettyWriter.java
// Package:     gov.llnl.babel.backend.writers
// Revision:    @(#) $Id: PrettyWriter.java 7188 2011-09-27 18:38:42Z adrian $
// Description: a pretty writer class to aid in formatting backend output
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

import java.io.PrintWriter;
import java.util.LinkedList;

/**
 * The <code>PrettyWriter</code> class formats output for the backend code
 * generators.  Formatted output is sent to a <code>PrintWriter</code> class.
 * The pretty writer supports two optional formatting modes.  The first mode
 * is "line breaking", in which long lines are broken at a specified column
 * and optional end-of-line or start-of-line continuation strings are printed
 * for the broken lines.  For example, Fortran line breaking would set the
 * break column at 72 with a start-of-line string of "     &" and a null
 * end-of-line string.  Python line breaking might set a line break at column
 * 78 with an end-of-line string of "\" and a null start-of-line string.  In
 * all cases, the continued line is indented one tab level.  The second mode
 * is "block commenting", in which optional line-start or after-tab strings
 * are printed for comment blocks.  For example, a Fortran comment block might
 * set the line-start string to "C" and the after-tab string to null whereas
 * Python would set the line-start string to null and the after-tab string to
 * "#".  In all cases, comment block mode supercedes line breaking mode such
 * that lines are not broken in comment blocks.  Column numbers start with
 * zero as the first column in a line.  Note also that newlines are output
 * as '\n' and not using the print writer default println() command, which
 * adds the extra DOS endo-of-line characters that confuses cygwin utilities
 * like automake.
 */
public class PrettyWriter {
   private static final int BUFSIZE = 80;

   private String       d_block_after_tab;   // after tab string in block
   private boolean      d_block_enabled;     // whether block mode is enabled
   private String       d_block_line_start;  // line start string in block
   private int          d_break_column;      // line break column (zero based)
   private boolean      d_break_enabled;     // whether breaking is enabled
   private String       d_break_line_end;    // line end string after break
   private String       d_break_line_start;  // line start after line break
   private String       d_break_string;      // characters on which to break
   private int          d_cursor;            // position on output line
   private int          d_first_tab;         // number of spaces to begin line
   private int          d_level;             // tab level (must be >= zero)
   private StringBuffer d_line;              // buffered output on this line
   private int          d_tab;               // tab spacing between tab stops
   private int          d_tmp_first_tab;     // temporary tab position
   private PrintWriter  d_writer;            // output print writer
  private boolean      d_strict_break = false; // No lines can be longer
                                               // than the break column.
  private LinkedList d_lineBreakStack = new LinkedList();

   /**
    * Create a new <code>PrettyWriter</code> instance that will send
    * output to the specified print writer object.  The first tab stop
    * is set to column zero with a tab spacing of three.  Both line
    * breaking and block commenting modes are disabled.
    */
   public PrettyWriter(PrintWriter writer) {
      d_block_after_tab   = null;
      d_block_enabled     = false;
      d_block_line_start  = null;
      d_break_column      = 80;
      d_break_enabled     = false;
      d_break_line_end    = null;
      d_break_line_start  = null;
      d_break_string      = ", (";
      d_cursor            = 0;
      d_first_tab         = 0;
      d_level             = 0;
      d_line              = new StringBuffer(BUFSIZE);
      d_tab               = 2;
      d_tmp_first_tab     = 0;
      d_writer            = writer;
   }

   /**
    * Flush and close the associated print writer stream.  No other pretty
    * writer or print writer routines may be called after the print writer
    * stream is closed.
    */
   public void close() {
      if (d_writer != null) {
         d_writer.flush();
         d_writer.close();
         d_writer = null;
      }
   }

   /**
    * Retrieve the underlying print writer stream.  Note that printing
    * directly to the print writer stream should only be done on newline
    * boundaries since direct output will bypass internal pretty writer
    * buffering.
    */
   public PrintWriter getPrintWriter() {
      return d_writer;
   }

   /**
    * Flush the underlying print writer stream.
    */
   public void flushPrintWriter() {
     d_writer.flush();
   }

   /**
    * Define the pretty printer parameters for line breaking.  The
    * column argument specifies the maximum column number (with the left
    * margin starting at column zero).  A column number of 80 means that
    * up to eighty characters can be printed on the line before the line
    * breaking algorithm splits the line.  The start and end strings indicate
    * the continuation strings to be output for continuation lines.  Either
    * or both of these arguments may be null.  For example, in Fortran, the
    * start string will be "     &" and the end string null.  In most shell
    * languages, the start string will be null and the end string "\".
    * This routine automatically enables line breaking.
    */
   public void enableLineBreak(int column, String start, String end) {
      d_break_column     = column;
      d_break_enabled    = true;
      d_break_line_end   = end;
      d_break_line_start = start;

      if (d_break_column < 1) {
        d_break_column = 1;
      }
   }

   /**
    * Enable line breaking mode.  Note that block comment mode takes
    * precedence over line breaking mode.
    */
   public void enableLineBreak() {
      d_break_enabled = true;
   }

   /**
    * Disable line breaking mode.
    */
   public void disableLineBreak() {
      d_break_enabled = false;
   }

  /**
   * Change the line break status to <code>linebreak</code> and save the
   * previous value on a stack.
   */
  public void pushLineBreak(boolean linebreak)
  {
    final Boolean b = Boolean.valueOf(d_break_enabled);
    d_lineBreakStack.addFirst(b);
    d_break_enabled = linebreak;
  }

  /**
   * Restore the line break status to its previous value.
   */
  public void popLineBreak()
  {
    final Boolean b = (Boolean)d_lineBreakStack.removeFirst();
    d_break_enabled = b.booleanValue();
  }

  public void setStrictBreaking()
  {
    d_strict_break = true;
  }

   /**
    * Set the line breaking characters.  Lines will be broken using these
    * characters as separators.  Each character will be tested in order.
    * The default like breaking characters are ", (".  This means that
    * lines will be tested for commas first, then spaces, and finally
    * open parenthesis.  If set to null, then line breaking is disabled.
    */
   public void setLineBreakString(String s) {
      d_break_string = s;
   }

   /**
    * Define the pretty printer parameters for block comment mode.  The
    * first argument defines the start-of-line string and the second defines
    * the after-tab string.  This routine does not turn on block comment
    * mode.
    */
   public void defineBlockComment(String line_start, String after_tab) {
      d_block_after_tab  = after_tab;
      d_block_line_start = line_start;
   }

   /**
    * Enable block comment mode.  This mode takes precedence over line
    * breaking so that comment lines are not broken.
    */
   public void enableBlockComment() {
      d_block_enabled = true;
   }

   /**
    * Disable block comment mode.
    */
   public void disableBlockComment() {
      d_block_enabled = false;
   }

   /**
    * Set the first tab stop for this pretty writer.  The first tab
    * stop must be nonnegative.
    */
   public void setFirstTabStop(int column) {
      d_first_tab     = column >= 0 ? column : 0;
      d_tmp_first_tab = d_first_tab;
   }

   /**
    * Change the current tab level at the beginning of a new lines by
    * the specified positive or negative amount.  If the tab level drops
    * below zero, then it is reset to zero.
    */
   public void changeTabLevel(int delta) {
      d_level += delta;
      if (d_level < 0) d_level = 0;
   }

   /**
    * Increase the tab level by one so that newlines begin at the next
    * tab stop.
    */
   public void tab() {
      changeTabLevel(1);
   }

   /**
    * Decrease the tab level by one so that newlines begin at the previous
    * tab stop.
    */
   public void backTab() {
      changeTabLevel(-1);
   }

   /**
    * Set the tab spacing for this pretty writer.  The tab stop value
    * must be greater than zero.
    */
   public void setTabSpacing(int spacing) {
      d_tab = spacing >= 1 ? spacing : 1;
   }

   /**
    * Set a temporary tab stop at the current cursor location.  This
    * capability is useful for aligning function arguments.  The temporary
    * tab stop may be removed by calling the restore first tab stop method.
    */
   public void setTemporaryFirstTabStop() {
      flushLine();
      d_tmp_first_tab = d_cursor;
   }

   /**
    * Restore the default first tab stop to its original value before the
    * call to <code>setTemporaryFirstTabStop</code>.
    */
   public void restoreFirstTabStop() {
      d_tmp_first_tab = d_first_tab;
   }

   /**
    * Print the specified string to the print writer output stream.
    * Nothing is done if the string argument is null.  If the string
    * contains newlines "\n", then <code>println</code> is called on
    * the substrings between the newlines.
    */
   public void print(String s) {
      if ((s != null) && (s.length() > 0)) {
         int newline = s.indexOf("\n");
         if (newline < 0) {
            d_line.append(s);
         } else {
            println(s.substring(0, newline));
            print(s.substring(newline+1));
         }
      }
   }

   /**
    * Print the specified string followed by a newline to the print writer
    * output stream.  If the argument string is null or empty, then the
    * writer output stream is advanced to the next line.  If the string
    * contains newlines "\n", then <code>println</code> is called on the
    * substrings between the newlines.  This method flushes the output
    * stream.
    */
   public void println(String s) {
      if ((s == null) || (s.length() == 0)) {
         println();
      } else {
         int newline = s.indexOf("\n");
         if (newline < 0) {
            d_line.append(s);
            flushLine();
            d_writer.print('\n');
            d_writer.flush();
            d_cursor = 0;
         } else {
            println(s.substring(0, newline));
            println(s.substring(newline+1));
         }
      }
   }

   /**
    * Print unformatted text to the print writer stream.  Switching
    * between formatted and unformatted text should only be done on
    * newline boundaries.
    */
   public void printUnformatted(String s) {
      if (s != null) {
         d_writer.print(s);
         d_writer.flush();
         d_cursor = 0;
      }
   }

   /**
    * Print unformatted text to the print writer stream.  Switching
    * between formatted and unformatted text should only be done on
    * newline boundaries.
    */
   public void printlnUnformatted(String s) {
      if ((s == null) || (s.length() == 0)) {
         d_writer.print('\n');
      } else {
         d_writer.print(s);
         d_writer.print('\n');
      }
      d_writer.flush();
      d_cursor = 0;
   }

   /**
    * Advance the output stream to the next line.  This method flushes
    * the output stream.
    */
   public void println() {
      if ((d_line.length() > 0) || d_block_enabled) {
         flushLine();
      }
      d_writer.print('\n');
      d_writer.flush();
      d_cursor = 0;
   }

   /**
    * Print the specified number of spaces to the pretty writer.  If the
    * number of spaces is zero or negative, then nothing is done.
    */
   public void printSpaces(int nspaces) {
      if (nspaces > 0) {
         for (int i = 0; i < nspaces; i++) {
            print(" ");
         }
      }
   }

   /**
    * Print the text string in a field of the specified width.  If the
    * text string is less than the field width, the field is filled with
    * spaces (after the text string).
    */
   public void printAligned(String text, int field) {
      if (text == null) {
         printSpaces(field);
      } else {
         print(text);
         printSpaces(field - text.length());
      }
   }

   /**
    * Flush the current line buffer to the output print writer, breaking
    * lines if necessary.  On return, the internal line buffer is reset
    * to empty.
    */
   private void flushLine() {
      /*
       * Begin a new line (if necessary) and advance the cursor to the
       * first tab stop.
       */
      boolean was_line_start = false;
      if (d_cursor == 0) {
         was_line_start = true;
         beginNormalLine();
      }

      /*
       * If line breaking is disabled, thenn output the buffer to the print
       * writer and update the cursor position.  Otherwise, call the line
       * breaking routine.
       */
      if (!d_break_enabled || d_block_enabled || (d_break_string == null)) {
         d_writer.print(d_line.toString());
         d_cursor += d_line.length();
      } else {
         breakLine(0, was_line_start);
      }

      /*
       * Create a new string buffer for the current line.  Unfortunately,
       * there does not seem to be a good way to re-use the current string
       * buffer.
       */

      d_line = new StringBuffer(BUFSIZE);
   }

  private int firstBreakChar(String str, int start)
  {
    final int len = str.length();
    int c;
    for(c = start; c < len; ++c) {
      if (d_break_string.indexOf(str.charAt(c)) >= 0) break;
    }
    return c;
  }

   /**
    * Output remaining data in the line buffer, breaking lines if necessary.
    * The start argument gives the starting location from which to flush the
    * string buffer.  If force is false, this routine can try to go to a new
    * line to print the string; otherwise, some of the string must be printed
    * on this line.
    */
   private void breakLine(int start, boolean force) {
      int length = d_line.length();
      int remain = length - start;
      
      if (remain > 0) {
         /*
          * Check whether the remaining string will fit on the current
          * line.  If so, simply print the string and advance the cursor.
          */
         if ((d_cursor + remain) <= d_break_column) {
            d_writer.print(d_line.substring(start));
            d_cursor += remain;
         } else {
            
            /*
             * Search for a breaking point in the line buffer.
             */
            int index = -1;
            int search = d_break_column - d_cursor;
            index = findBreak(start, search);

            /*
             * If a breaking index was found, then print out data through
             * the specified index and recurse on the remaining string.
             */
            if (index > 0) {
               String fragment = d_line.substring(start, index+1);
               d_writer.print(fragment);
               d_cursor += fragment.length();
               endContinuationLine();
               beginContinuationLine();
               breakLine(firstNonSpace(index+1), true);

            /*
             * No line beaking index was found.  If we need to force
             * output, then just output the line and violate the line
             * breaking request.  Otherwise, start a new line and call
             * the line breaking routine recursively.
             */
            } else {
               if (force) {
                 if (d_strict_break && (search > 0)) {
                   String fragment = d_line.substring(start, start+search);
                   d_writer.print(fragment);
                   d_cursor += fragment.length();
                   endContinuationLine();
                   beginContinuationLine();
                   breakLine(firstNonSpace(start+search), true);
                 }
                 else{
                   String fragment = d_line.substring(start);
                   int firstBreak = firstBreakChar(fragment, search);
                   if (firstBreak < fragment.length()) {
                     d_writer.print(fragment.substring(0, firstBreak));
                     d_cursor += firstBreak;
                     endContinuationLine();
                     beginContinuationLine();
                     breakLine(firstNonSpace(start+firstBreak), true);
                   }
                   else {
                     d_writer.print(fragment);
                     d_cursor += fragment.length();
                   }
                 }
               } else {
                  endContinuationLine();
                  beginContinuationLine();
                  breakLine(firstNonSpace(start), true);
               }
            }
         }
      }
   }

   /**
    * End a continuation line.  Print the end-of-line continuation string
    * if not null and advance the cursor to the next line.
    */
   private void endContinuationLine() {
      if (d_break_line_end != null) {
         if (d_break_column > d_cursor)
           writeSpaces(d_break_column - d_cursor);
         d_writer.print(d_break_line_end);
      }
      d_writer.print('\n');
      d_writer.flush();
      d_cursor = 0;
   }

   /**
    * Search the line buffer for the first non-space character and return
    * that index.  If no non-space characters are found in the buffer, then
    * the buffer length is returned (which is an illegal index entry).
    */
   private int firstNonSpace(int start) {
      int length = d_line.length();
      int index  = length;
      for (int c = start; c < length; c++) {
         if (d_line.charAt(c) != ' ') {
            index = c;
            break;
         }
      }
      return index;
   }

   /**
    * Search the text buffer starting at the specified position for the
    * target character.  Return the index at which the character is found
    * or -1 if the character is not found.
    */
   private int findBreak(int start, int search) {
      int index = -1;
      for (int s = start+search-1; s >= start; s--) {
        if (d_break_string.indexOf(d_line.charAt(s)) >= 0) {
          index = s;
          break;
        }
      }
      return index;
   }

   /**
    * Begin a normal line.  If in block mode and the line start string is
    * not null, then print it.  Then advance to the first tab stop and tab
    * over to the current tab level.  If in block mode and the after tab
    * string is not null, then print it.
    */
   private void beginNormalLine() {
      /*
       * Output the line start string if it is not null and the pretty
       * writer is in block comment mode.
       */
      if (d_block_enabled && (d_block_line_start != null)) {
         d_writer.print(d_block_line_start);
         d_cursor += d_block_line_start.length();
      }

      /*
       * Tab to the correct position based on first tab and tab level.
       */
      int first_column = d_tmp_first_tab + d_level*d_tab;
      writeSpaces(first_column - d_cursor);

      /*
       * Output the after tab string if it is not null and the pretty
       * writer is in block comment mode.
       */
      if (d_block_enabled && (d_block_after_tab != null)) {
         d_writer.print(d_block_after_tab);
         d_cursor += d_block_after_tab.length();
      }
   }

   /**
    * Begin a continuation line.  If the line start string is not null,
    * then print it.  Then advance to the first tab stop and tab over
    * to the current tab level.  Finally, since this is a continuation
    * line, tab an extra column.
    */
   private void beginContinuationLine() {
      /*
       * If the continuation start string is not null, then print it.
       */
      if (d_break_line_start != null) {
         d_writer.print(d_break_line_start);
         d_cursor += d_break_line_start.length();
      }

      /*
       * Tab to the correct position based on first tab and tab level.
       * Tab one more tab level because this is a continuation line.
       */
      int first_column = d_tmp_first_tab + (d_level+1)*d_tab;
      writeSpaces(first_column - d_cursor);
   }

   /**
    * Advance the underlying raw output stream by the specified number
    * of spaces.  If the number of spaces is zero or negative, then nothing
    * is done.
    */
   private void writeSpaces(int nspaces) {
      if (nspaces > 0) {
         for (int i = 0; i < nspaces; i++) {
            d_writer.print(' ');
         }
         d_cursor += nspaces;
      }
   }
}
