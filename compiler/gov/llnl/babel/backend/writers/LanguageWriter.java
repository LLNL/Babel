//
// File:        LanguageWriter.java
// Package:     gov.llnl.babel.backend.writers
// Revision:    @(#) $Id: LanguageWriter.java 7188 2011-09-27 18:38:42Z adrian $
// Description: generic language writer for backend code generation
//
// Copyright (c) 2000-2002, Lawrence Livermore National Security, LLC
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

import gov.llnl.babel.BabelConfiguration;
import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeConstants;
import gov.llnl.babel.backend.Utilities;
import gov.llnl.babel.backend.writers.PrettyWriter;
import gov.llnl.babel.symbols.Comment;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.Version;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Abstract class <code>LanguageWriter</code> extends the pretty
 * writer class with additional features for backend code generation.
 * The language writer manages block comments and file headers.
 * Language-specific writers manage formatting for a particular
 * language.
 */
public abstract class LanguageWriter
  extends PrettyWriter
  implements CodeConstants {

  protected Context d_context;

  /**
   * Create a <code>LanguageWriter</code> instance that will send
   * output to the specified print writer object.
   */
  public LanguageWriter(PrintWriter writer, Context context) {
    super(writer);
    d_context = context;
  }

  /**
   * Abstract method to begin a block comment.  If the documentation
   * flag is true, then the block comment is a documentation comment.
   * The precise form of the block comment for a specific language is
   * set by the language writer subclass.
   */
  public abstract void beginBlockComment(boolean is_doc);

  /**
   * Abstract method to end a block comment.  If the documentation flag
   * is true, then the block comment is a documentation comment.  The
   * precise form of the block comment for a specific language is set
   * by the language writer subclass.
   */
  public abstract void endBlockComment(boolean is_doc);

  /**
   * Write the splicer tag as a language-specific comment.
   */
  public abstract void writeSplicerTagLine(String tagLine);



  /**
   * <p>
   * Abstract method to output a comment to the pretty writer stream.
   * The comment is not written in a comment block but only on one line
   * of the output stream.  The comment should not contain any new line
   * characters.  If the comment is null, then nothing is output.</p>
   * <p>
   * It is assumed that comment is safe for inclusion in a comment
   * for any language.</p>
   *
   * @param comment   a single line comment to include in the output
   * @see #safeCommentString
   */
  public abstract void writeCommentLine(String comment);

  /**
   * <p>
   * Write a single-line block comment to the pretty writer stream.
   * If the string is null or contains no characters, then no comment
   * is written.  The documentation flag determines whether this is a
   * documentation comment.</p>
   * <p>
   * It is assumed that comment is safe for inclusion in a comment
   * for any language.</p>
   *
   * @param s      the comment string to include in the output
   * @param is_doc <code>true</code> means that the comment should
   *               be treated as a document-style comment in
   *               languages that have such things.
   * @see   #safeCommentString
   */
  public void writeComment(String s, boolean is_doc) {
    if ((s != null) && (s.length() > 0)) {
      beginBlockComment(is_doc);
      println(s);
      endBlockComment(is_doc);
    }
  }

  /**
   * Convert a string into a string that is safe to include inside
   * a comment. The set of characters that are safe inside a string
   * is language dependent, so this can be overridden. To motivate
   * this method, let me just say that having a
   * <code>*</code><code>/</code> in side a C or Java comment would
   * prematurely end the comment.
   *
   * @param string  the string to make safe for inclusion inside a
   *             comment.
   * @return a string that can safely be included inside a comment.
   */
  public String safeCommentString(String string) {
    return string;
  }

  /**
   * Convert HTML/XML entities as appropriate.
   */
  public String processHTML(String str)
  {
    return str;
  }

  /**
   * Write a comment for a sidl Comment to the pretty writer stream.  
   */
  public void writeComment(Comment comment, boolean is_doc) {
    boolean  found_comment = false;
    String[] lines         = null;

    if (comment != null) {
      lines = comment.getComment();
      if ((lines != null) && (lines.length > 0)) {
        found_comment = true;
      }
    }

    if ( found_comment ) {
      beginBlockComment(is_doc);

      /*
       * Output the symbol comment if it exists.
       */
      if ( found_comment ) {
        for (int l = 0; l < lines.length; l++) {
          println(safeCommentString(processHTML(lines[l])));
        }
      }

      /*
       * Close the comment block.
       */
      endBlockComment(is_doc);
    }
  }

  /**
   * Write a comment for a sidl symbol to the pretty writer stream.  Optionally
   * output the symbol name and version first.
   */
  public void writeComment(Symbol symbol, boolean is_doc, boolean output_sym) {
    boolean  found_comment = false;
    Comment  comment       = symbol.getComment();
    String[] lines         = null;

    if (comment != null) {
      lines = comment.getComment();
      if ((lines != null) && (lines.length > 0)) {
        found_comment = true;
      }
    }

    if ( found_comment || output_sym ) {
      beginBlockComment(is_doc);

      if ( output_sym ) {
        /*
         * Output the name of the symbol as the first line of the comment.
         */
        print("Symbol \"");
        print(symbol.getFullName());
        print("\" (version ");
        print(symbol.getSymbolID().getVersion().getVersionString());
        println(")");
      }

      /*
       * Output the symbol comment if it exists.
       */
      if ( found_comment ) {
        if ( output_sym ) {
          println();
        }
        for (int l = 0; l < lines.length; l++) {
          println(safeCommentString(processHTML(lines[l])));
        }
      }

      /*
       * Close the comment block.
       */
      endBlockComment(is_doc);
    }
  }

  /**
   * Write a comment for a sidl symbol to the pretty writer stream.  If
   * the symbol does not have a comment associated with it, then only the
   * symbol name and version will be printed to the output stream.
   */
  public void writeComment(Symbol symbol, boolean is_doc) {
    writeComment(symbol, is_doc, true);
  }

  /**
   * Write a comment for a sidl method to the pretty writer stream.  Optionally
   * write the method name if the symbol does not have an associated comment.
   */
  public void writeComment(Method method, boolean is_doc, boolean add_default) {
    boolean  found_comment = false;
    Comment  comment       = method.getComment();
    String[] lines         = null;
    if (comment != null) {
      lines = comment.getComment();
      if ((lines != null) && (lines.length > 0)) {
        found_comment = true;
      }
    }

    if ( found_comment || add_default ) {
      beginBlockComment(is_doc);

      if ( found_comment ) {
        for (int l = 0; l < lines.length; l++) {
          println(safeCommentString(lines[l]));
        }
      } else {
        println("Method:  " + method.getShortMethodName() + "[" 
                + method.getNameExtension() + "]");
      }

      endBlockComment(is_doc);
    }
  }

  /**
   * Write a comment for a sidl method to the pretty writer stream.  If
   * the symbol does not have a comment associated with it, then only the
   * method name will be printed to the output stream.
   */
  public void writeComment(Method method, boolean is_doc) {
    writeComment(method, is_doc, true);
  }

  /**
   * Write a comment with an alternative string.  Does nothing if both are null.
   * @param comment a comment or null
   * @param alt an alternate string if comment==null, or null itself
   */
  public void writeComment(Comment comment, String alt) {
    boolean printalt = false;

    if (comment != null ) {
      String[] lines = comment.getComment();
      if ((lines != null) && (lines.length > 0) && 
          (lines[0] != null) && (lines[0].length() > 0)) {
        println();
        beginBlockComment(true);
        for (int l = 0; l < lines.length; l++) {
          println(safeCommentString(lines[l]));
        }
        endBlockComment(true);
      } else {
        printalt = true;
      }
    } else {
      printalt = true;
    }
    if ( printalt && (alt != null) && (alt.length() != 0)) {
      beginBlockComment(true);
      println(alt);
      endBlockComment(true);
    }
  }

  /**
   * Write a file banner for automatically generated files.  The banner
   * is a comment block that provides basic information about the symbol.
   */
  public void writeBanner(Symbol symbol, String file, boolean is_Impl, 
                          String desc) {
    /*
     * Begin the comment header with the file and symbol elements.
     */
    beginBlockComment(false);

    println("File:          " + file);
    print("Symbol:        ");
    println(symbol.getSymbolID().getSymbolName());

    /*
     * Write the symbol type (class, enumeration, interface, or package).
     */
    print("Symbol Type:   ");
    switch (symbol.getSymbolType()) {
    case Symbol.CLASS:
      println("class");
      break;
    case Symbol.ENUM:
      println("enumeration");
      break;
    case Symbol.INTERFACE:
      println("interface");
      break;
    case Symbol.PACKAGE:
      println("package");
      break;
    case Symbol.STRUCT:
      println("struct");
      break;
    }

    /*
     * Write the Babel version, sidl creation date, and the current date.
     */
    print("Babel Version: ");
    println(Version.getFullVersion());

    if (!d_context.getConfig().suppressTimestamps()) 
    {
      print("sidl Created:  ");
      println(symbol.getMetadata().getDateAsString());
      String format = "yyyyMMdd HH:mm:ss zz";
      print("Generated:     ");
      println(new SimpleDateFormat(format).format(new Date()));
    }

    /*
     * If this is one of the sidl build-in files, then generate copyright,
     * release, and revision header lines.
     */
    if (BabelConfiguration.isSIDLBaseClass(symbol)) {
       println("Release:       $N" + "ame:  $");
       println("Revision:      @(" + C_HASH + ") $I" + "d: $");
    }

    /*
     * Output a description of the file (provided by the caller).
     */
    print("Description:   ");
    println(desc);
    println();
    if (BabelConfiguration.isSIDLBaseClass(symbol)) {
       print("Copyright (c) 2000-2002, ");
       println("Lawrence Livermore National Security, LLC.");
       println("Produced at the Lawrence Livermore National Laboratory.");
       println("Written by the Components Team <components@llnl.gov>");
       println("All rights reserved.");
       println();
       println("This file is part of Babel. For more information, see");
       println("http://www.llnl.gov/CASC/components/. Please read the COPYRIGHT file");
       println("for Our Notice and the LICENSE file for the GNU Lesser General Public");
       println("License.");
       println();
       println("This program is free software; you can redistribute it and/or modify it");
       println("under the terms of the GNU Lesser General Public License (as published by");
       println("the Free Software Foundation) version 2.1 dated February 1999.");
       println();
       println("This program is distributed in the hope that it will be useful, but");
       println("WITHOUT ANY WARRANTY; without even the IMPLIED WARRANTY OF");
       println("MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the terms and");
       println("conditions of the GNU Lesser General Public License for more details.");
       println();
       println("You should have received a copy of the GNU Lesser General Public License");
       println("along with this program; if not, write to the Free Software Foundation,");
       println("Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA");
       println();
    }
    print("WARNING: ");
    if (is_Impl) {
      println(C_AUTO_GEN_SPLICER);
    } else {
      println(C_AUTO_GEN_WARNING);
    }

    /*
     * Output other metadata for this symbol in the form "key = value".
     * Sort the metadata in ascending order and also space out the key
     * column so the equals signs line up in a column (ooohh - pretty).
     */
    Set entries = symbol.getMetadata().getMetadataDatabase().entrySet();
    if ((entries != null) && (entries.size() > 0)) {
      List sorted = Utilities.sort(entries);
      int width = Utilities.getWidth(entries);
      println();
      for (Iterator e = sorted.iterator(); e.hasNext(); ) {
        Map.Entry entry = (Map.Entry) e.next();
        String key = (String) entry.getKey();
        if (key != null) {
          if (! ( d_context.getConfig().suppressTimestamps() &&
		  (key.equals("source-url") || key.equals("source-line"))))
	  {
            printAligned(key, width);
            print(" = ");
            println((String) entry.getValue());
          }
        }
      }
    }

    endBlockComment(false);
  }
}
