//
// File:        LanguageWriterForFortran.java
// Package:     gov.llnl.babel.backend.writers
// Revision:    @(#) $Id: LanguageWriterForFortran.java 7188 2011-09-27 18:38:42Z adrian $
// Description: Fortran language writer for backend code generation
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

import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeConstants;
import gov.llnl.babel.backend.writers.LanguageWriter;
import gov.llnl.babel.backend.fortran.Fortran;
import gov.llnl.babel.symbols.Comment;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.xml.XMLUtilities;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

/**
 * Class <code>LanguageWriterForFortran</code> extends the generic language
 * writer to provide additional capabilities for writing Fortran code,
 * such as Fortran comment blocks.
 */
public class LanguageWriterForFortran extends LanguageWriter
                                      implements CodeConstants
{
   /**
    * Create a <code>LanguageWriterForFortran</code> instance that will send
    * output to the specified print writer object.  Set up the output
    * properties of the language writer for Fortran. For F77, it sets the
    * first tab at eight, the tab spacing to three, and a line break at
    * column 72.
    */
   public LanguageWriterForFortran(PrintWriter writer, Context context)
  {
      super(writer, context);
      d_context = context;
      if ("f90".equals(context.getConfig().getTargetLanguage())) {
        enableLineBreak(78, null, " &");
        defineBlockComment("", C_COMMENT_F90);
      } else if (Fortran.hasBindC(context)){
        enableLineBreak(78, null, " &");
        defineBlockComment("", C_COMMENT_F90);
      } else {	/* F77 assumed */
        setFirstTabStop(8);
        setTabSpacing(3);
        enableLineBreak(72, "     &", null);
        defineBlockComment(C_COMMENT_F77, null);
        setStrictBreaking();
      }
   }

   /**
    * Begin a block comment.  The comment block is enabled and a blank line
    * is printed.  There is no difference between documentation comments and
    * regular comments in Fortran.
    */
   public void beginBlockComment(boolean is_doc) {
      enableBlockComment();
      println();
   }

   /**
    * End a block comment.  A line is printed to close the comment.
    * An empty line is printed after the comment.  There is no difference
    * between documentation commends and regular comments in Fortran.
    */
   public void endBlockComment(boolean is_doc) {
      println();
      disableBlockComment();
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
         enableBlockComment();
         try {
           pushLineBreak(false);
           println(comment);
         }
         finally {
           popLineBreak();
         }
         disableBlockComment();
      }
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

      println("Method:  " + method.getShortMethodName() + "[" 
              + method.getNameExtension() + "]");
      if ( found_comment ) {
        for (int l = 0; l < lines.length; l++) {
          println(safeCommentString(lines[l]));
        }
      }

      endBlockComment(is_doc);
    }
  }

  public final String processHTML(String str) {
    return XMLUtilities.decodeXMLString(str);
  }

   /**
    * Generate a user include statement for the specified include file.
    * There is no guard flag for the fortran version.
    */
   public void generateInclude(String file) {
      printUnformatted(C_INCLUDE + "\"");
      printUnformatted(file);
      printlnUnformatted("\"");
      d_context.getDependencies().addInput(file);
   }

   /**
    * Generate a system include statement for the specified include file.
    */
   public void generateSystemInclude(String file) {
      printUnformatted(C_INCLUDE + "<");
      printUnformatted(file);
      printlnUnformatted(">");
   }

   private String moduleToFile(String module) {
      return module; // fixme requires fortran brains.
   }
   /**
    * Generate a use statement for the specified string.
    */
   public void generateUse(String module, Map rename) {
     TreeSet set = new TreeSet(rename.keySet());
     Iterator i = set.iterator();
     print("use " + module);
     while (i.hasNext()) {
       String key = i.next().toString();
       print(", ");
       print(rename.get(key).toString());
       print(" => ");
       print(key);
     }
     println();
     d_context.getDependencies().addInput(moduleToFile(module));
   }
}
