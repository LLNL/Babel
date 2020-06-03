//
// File:        CodeSplicer.java
// Package:     gov.llnl.babel.backend
// Revision:    @(#) $Id: CodeSplicer.java 7421 2011-12-16 01:06:06Z adrian $
// Description: splice code from an old user file into a new generated file
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

package gov.llnl.babel.backend;

import gov.llnl.babel.Context;
import gov.llnl.babel.backend.writers.LanguageWriter;
import gov.llnl.babel.backend.writers.LineRedirector;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.SplicerBlock;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class <code>CodeSplicer</code> splices code segments from an existing
 * user file into a new automatically generated file.  The intended use of
 * the code splicer is to preserve user edits to generated code.  The new
 * file replaces the original one; however, the code splicer preserves areas
 * delineated with the pair of strings:
 * <code>
 * DO-NOT-DELETE splicer.begin(symbol)
 * DO-NOT-DELETE splicer.end(symbol)
 * </code>
 * embedded in comments in both the edited and generated files.  The
 * code splicer also tracks which symbols were not used in the newly
 * generated file, and these symbols may be output at the end of the
 * new file to preserve code for the user.
 *
 * IMPORTANT:
 *   After introduction of splicer blocks into the AST and Symbol Table,
 *   the rules for processing changed.  The precedence, from highest to 
 *   least, is now as follows:
 *
 *   1) Legacy file splicer contents (if _any_ are populated);
 *   2) AST contents (which are assumed to have been transferred to the symbol
 *      table; then
 *   3) Default contents.
 */
public class CodeSplicer {
   /*
    * At this point, the underlying assumption is there are only two
    * distinctions made (and simultaneously supported) in the AST/Symbol 
    * Table in terms of the target destination for splicer blocks:  header
    * and source.  That is, the following three use cases are anticipated
    * for populating code splicers in the AST:
    *
    * 1) Header (aka Module for F90) block contents only are used;
    * 2) Source block contents only are used; and
    * 3) Header and source block contents are used.
    * 
    * In all three cases they could be translated from their respective
    * file(s) or could be entered by the user in a GUI.
    */
   public final static String S_HEADER = "header";  
   public final static String S_SOURCE = "source";  

   private final static String s_target = "DO-NOT-DELETE splicer";
   private final static String s_begin  = s_target + ".begin(";
   private final static String s_end    = s_target + ".end(";
   private final static String s_term   = ")";

   /*
    * The following is a temporary hack to deal with the elimination of 
    * splicer 'begin' and 'end' lines from the edits table _and_ the
    * lack of knowledge about the target language.  At issue is splicer
    * 'begin' and 'end' line comment terminators falling on the next
    * line (due to formatting) when populating the edits table from
    * an existing file.  Given currently supported languages (Sept. 2006),
    * only splicer blocks for C have this problem, which arises when the
    * full name exceeds the standard line length.
    */
   private final static String s_comment_begin = "/*";
   private final static String s_comment_end = "*/";

   private SymbolID       d_symbol_id;
   private Map            d_symbol_edits;
   private Set            d_used_symbols;
   private String         d_vpath;
   private String         d_path; 
   private Map            d_splice_bounding_lines;
   private LineRedirector d_lr;
   private boolean        d_source_splicers  = true;
   private boolean        d_prepend_fullname = true;

  private Context         d_context;

   /**
    * Return the code splicer start string for the beginning of a code
    * splice region.  The symbol string must be unique within this file.
    * The symbol may not contain a close parenthesis.
    */
   public static String getBeginString(String symbol) {
      return s_begin + symbol + s_term;
   }

   /**
    * Return the code splicer end string for the closing of a code splice
    * region.  The symbol string must be unique within this file and must
    * match the string used to open the splice region.  The symbol may not
    * contain a close parenthesis.
    */
   public static String getEndString(String symbol) {
      return s_end + symbol + s_term;
   }

   /**
    * Create a new instance of the code splicer class.  The constructor
    * will read data from the buffered reader stream and throw an IO exception
    * if any error condition is encountered while reading from the file.
    * The code splices are stored in a map with keyvalues that are the
    * region symbols and corresponding symbols that are the lines of code
    * retrieved from the file.
    */
   public CodeSplicer(SymbolID id, BufferedReader reader, String vpath,
                      String path, boolean isSource, boolean prependFullName,
                      Context context)
       throws IOException 
   {
      d_context = context;
      d_symbol_id        = id;
      d_source_splicers  = isSource;
      d_prepend_fullname = prependFullName;
      d_symbol_edits     = new HashMap();
      d_used_symbols     = new HashSet();
      if (vpath==null||vpath.equals("")||vpath.equals(".") ) { 
         d_vpath=".";
      } else { 
         d_vpath = vpath;
      }
      d_path = path;
      if (d_path.equals(d_vpath)) { 
	  d_vpath=".";
      }
      d_splice_bounding_lines = new HashMap();
      d_lr = null;
      populateDatabase(reader);
   }

   /**
    * Create a CodeSplicer that does not contain contents obtained from a
    * file.  (See "IMPORTANT" under class comments for CodeSplicer population
    * rules.)  Note that an empty CodeSplicer does not have any stored symbols.
    */
  public CodeSplicer(SymbolID id, boolean isSource, boolean prependFullName,
                     Context context) 
    throws IOException
  {
      d_symbol_id             = id;
      d_context               = context;
      d_source_splicers       = isSource;
      d_prepend_fullname      = prependFullName;
      d_symbol_edits          = new HashMap();
      d_used_symbols          = new HashSet();
      d_vpath                 = ".";
      d_splice_bounding_lines = new HashMap();
      d_lr                    = null;

      populateFromAST();
   }    


   public void setLineRedirector( LineRedirector lr ) { 
      d_lr = lr;
   }

   /** query the current vpath setting
    *  "." indicates no vpath
    */
   public String getVPath() { 
      return d_vpath;
   }

   /** explicitly set the vpath.  
    *  Note that vpath=null, "" or "."
    *  are all handled internally as "."
    */
   public void setVPath( String vpath ) { 
      if (vpath==null||vpath.equals("")||vpath.equals(".") ) { 
         d_vpath=".";
      } else { 
         d_vpath = vpath;
      }
   }  

   /**
    * Query whether the specified symbol exists in the symbol database.
    * The symbol may not contain a close parenthesis and it may not start
    * or end with white space.
    */
   public boolean hasSymbol(String symbol) {
      return d_symbol_edits.containsKey(symbol);
   }

   /**
    * Output symbol information to the specified output print writer.  If
    * the symbol does not exist in the database, then nothing is output to
    * the print stream.  If the symbol exists, then the symbol begin and
    * end comments are output as well as any text between those comments.
    * The symbol may not contain a close parenthesis and it may not start
    * or end with white space.
    */
  public void outputSymbolEdits(String symbol, LanguageWriter writer) { 
      String edits    = (String) d_symbol_edits.get(symbol);

      if (edits != null) {
         if ((!d_vpath.equals(".")) && d_lr != null ) { 
            int[] bounds = (int[]) d_splice_bounding_lines.get(symbol);
            d_lr.redirectBegin(d_vpath, bounds[0]);	
            writer.writeSplicerTagLine(getBeginString(symbol));
            writer.printUnformatted(edits);
            writer.writeSplicerTagLine(getEndString(symbol));
            writer.flushPrintWriter();
            int end=d_lr.getLineCount();
 	    d_lr.redirectEnd(d_path, end);
	 } else { 
            writer.writeSplicerTagLine(getBeginString(symbol));
            writer.printUnformatted(edits);
            writer.writeSplicerTagLine(getEndString(symbol));
         }
         d_used_symbols.add(symbol);
      }
   }

   /**
    * Retrieve the edit string associated with the specified symbol.  If
    * no symbol exists in the database, then return null.  The symbol may
    * not contain a close parenthesis and it may not start or end with
    * white space.
    */
   public String getEditString(String symbol) {
      return (String) d_symbol_edits.get(symbol);
   }

   /**
    * Returns TRUE if there is at least one unused edit in the symbol
    * edit database.  See outputUnusedSymbolEdits() for more information.
    */
   public boolean hasUnusedSymbolEdits() {
     return d_used_symbols.size() < d_symbol_edits.size();
   }

   /**
    * Output the unused edits in the symbol edit database.  These symbols
    * were read from the input file but were not used in the output file.
    * The symbols will be output to the specified output print writer in
    * no particular order.
    *
    * WARNING:  This version outputs raw splicer block tag lines (i.e., 
    * without conversion to language-specific!
    */
   public void outputUnusedSymbolEdits(PrintWriter writer) {
      String currSymbol = null;

      for (Iterator i = d_symbol_edits.entrySet().iterator(); i.hasNext(); ) {
         Map.Entry entry  = (Map.Entry) i.next();
         String    symbol = (String) entry.getKey();
         String    edits  = (String) entry.getValue();
         if (!d_used_symbols.contains(symbol)) {
            if (!symbol.equals(currSymbol)) {
              if (currSymbol != null) {
                writer.write(getEndString(currSymbol));
              } 
              currSymbol = symbol;
              writer.write(getBeginString(symbol));
            }
            writer.print(edits);
         }
      }
      if (currSymbol != null) {
        writer.write(getEndString(currSymbol));
      }
   }

   /**
    * Output the unused edits in the symbol edit database.  These symbols
    * were read from the input file but were not used in the output file.
    * The symbols will be output to the specified output print writer in
    * no particular order.
    *
    * NOTE:  This version relies on the writer to generate the proper,
    * language-specific splicer block name and comment line.
    */
   public void outputUnusedSymbolEdits(LanguageWriter writer) {
      String currSymbol = null;

      writer.disableLineBreak();
      for (Iterator i = d_symbol_edits.entrySet().iterator(); i.hasNext(); ) {
         Map.Entry entry  = (Map.Entry) i.next();
         String    symbol = (String) entry.getKey();
         String    edits  = (String) entry.getValue();
         if (!d_used_symbols.contains(symbol)) {
            if (!symbol.equals(currSymbol)) {
              if (currSymbol != null) {
                writer.writeSplicerTagLine(getEndString(currSymbol));
              } 
              currSymbol = symbol;
              writer.writeSplicerTagLine(getBeginString(symbol));
            }
            writer.printUnformatted(edits);
         }
      }
      if (currSymbol != null) {
        writer.writeSplicerTagLine(getEndString(currSymbol));
      }
      writer.enableLineBreak();
   }

   /**
    * Retrieve a <code>Set</code> of the symbols in the symbol edit
    * database.  Each entry in the set is a string representing a symbol.
    */
   public Set getSymbols() {
      return d_symbol_edits.keySet();
   }

  private void addSymbolEdit(Map m, String sym, String edit)
    throws IOException
  {
    if (!d_symbol_edits.containsKey(sym)) {
      d_symbol_edits.put(sym, edit);
    }
    else {
      throw new IOException("Duplicate definition of splicer block '" +
                            sym + "' in file.");
    }
  }

  /**
   * Populate the symbol edits database from the specified file.  Any
   * input errors will return a <code>IOException</code>.
   */
  private void populateDatabase(BufferedReader reader) throws IOException 
  {
    /*
     * Search for beginning tag lines until we reach the end of the file.
     */
    boolean eof = false;
    int line=0;
    while (!eof) {
      String s = reader.readLine();
      line++;
      if (s == null) {
        eof = true;
      } else {
        
        if (s.indexOf(s_end) >=0 ) {
          // bug #548: end(a) without leading start(a) is an error
          throw new IOException("found \'splicer.end()\' outside of a "
                                + "splicer block\n" + line + ": " + s );
        }
        /*
         * If not EOF check whether this line contains the start string.
         */
        String symbol = extractSymbol(s);
        if (symbol != null) {
          int[] bounds = new int[2];
          bounds[0]=line;
          
          /*
           * WARNING:  Only C currently terminates its comments and that 
           * termination may have inadvertently occurred for long splicer 
           * blocks tag lines generated with line breaks enabled.
           * Hence, to avoid generating a duplicate comment terminator, 
           * it is necessary to check for an unterminated comment.  This 
           * approach to this problem is necessitated by the 'requirement'
           * that this instance not know the target language.  
           */
          boolean skipCCommentTerm = (s.indexOf(s_comment_begin) >= 0) 
            && (s.indexOf(s_comment_end) < 0);
          
          /*
           * Add lines to the writer until the end string is detected.
           */
          StringWriter sw = new StringWriter();
          PrintWriter  pw = new PrintWriter(sw);
          
          /*
           * Since no longer populating edits with splicer tag lines,
           * skip the starting splicer tag (i.e., no pw.print(s + '\n')).
           * The rationale for doing this is to avoid needing to know
           * the target language in order to generate the comment line.
           */
          
          String  end = getEndString(symbol);
          boolean eob = false;
          while (!eob) {
            String b = reader.readLine();
            line++;
            if (b == null) {
              eob = true;
              eof = true;
            } else if (b.indexOf(s_begin) >=0 ) {
              // bug #548: begin(a),begin(b),end(a) is an error
              throw new IOException("encountered \'splicer.begin()\' "
                                    + "inside of a splicer block \"" + symbol 
                                    + "\"\n" + line + ": " + b);
            } else if (b.indexOf(end) >= 0) {
              eob = true;
            } else if (b.indexOf(s_end) >=0) { 
              // bug #548: begin(a),end(b),end(a) is an error
              throw new IOException("encountered \'splicer.end()\' " 
                                    + "inside of a splicer block \"" + symbol 
                                    + "\"\n" + line + ": " + b);
            }
            
            /*
             * Skip the final splicer block tag line for the same
             * reason the begin tag line is skipped.  Also do _not_
             * output the next line if it is expected to terminate
             * the start of a begin splicer block tag 'line' that
             * broke into the next line.
             */
            if (!eob) {
              if (!skipCCommentTerm) {
                pw.print(b + '\n');
              } else {
                skipCCommentTerm = false;
              } 
            }
          }
          pw.flush();
          addSymbolEdit(d_symbol_edits, symbol, sw.toString());
          bounds[1]=line;
          d_splice_bounding_lines.put(symbol,bounds);
        }
      }
    }
    
    populateFromAST();
  }
  
  /**
   * Add contents from the list of splicer blocks to the database.
   */
  private void addSplicerBlockContents(List list) 
    throws IOException
  {
    String namePrefix = d_prepend_fullname ? d_symbol_id.getFullName() + "." 
      : "";
    String name       = null;
    int[]  bounds     = new int[2];
    int    line       = 1;
    
    /*
     * ToDo...Make sure bounds[] are set properly.
     */
    bounds[0]=line;
    if (list != null) {
      for (Iterator biter = list.iterator(); biter.hasNext(); ) {
        SplicerBlock sb = (SplicerBlock) biter.next();
        
        StringWriter sw = new StringWriter();
        PrintWriter  pw = new PrintWriter(sw);
        
        name = namePrefix + sb.getName();
        
        for (Iterator iter = sb.getSplicerContents().iterator();
             iter.hasNext(); )
          {
            String impl = (String) iter.next();
            line++;
            pw.print(impl + '\n');
          }
        pw.flush();
        addSymbolEdit(d_symbol_edits, name, sw.toString());
        bounds[1]=line;
        d_splice_bounding_lines.put(name, bounds);
      }
    }
  }

   /**
    * Populate the symbol edits database from the AST (via the symbol table)
    * iff no splicer contents loaded from other source(s).
    */
   private void populateFromAST() 
     throws IOException
  {
     if (d_symbol_edits.isEmpty()) {
       Symbol sym  = d_context.getSymbolTable().lookupSymbol(d_symbol_id);
       if (sym.isClass()) {
         gov.llnl.babel.symbols.Class cls = (gov.llnl.babel.symbols.Class) sym;
         String loc = d_source_splicers ? S_SOURCE : S_HEADER;

         /*
          * Start with any and all splicer blocks associated with the class.
          */
         addSplicerBlockContents(cls.getSplicerBlocks(loc));

         /*
          * Now go through any and all splicer blocks associated with the 
          * methods.
          */
         List methods = cls.getMethods(true);
         if (methods != null) {
           for (Iterator miter = methods.iterator(); miter.hasNext(); )
           {
             Method meth = (Method) miter.next();
             addSplicerBlockContents(meth.getSplicerBlocks(loc));
           }
         }
       } else if (sym.isStruct()){
          //SPM--need to add splicer info eventually?
       } else {
         System.err.println("WARNING: CodeSplicer.populateFromAST(): "
           + "Unsupported symbol type (" + sym.getSymbolType() + ") for "
           + "transferring splicer contents of " + d_symbol_id.getFullName()
           + " from AST/Symbol Table to CodeSplicer.");
       }
     }
   }

   /**
    * Extract a symbol from the specified string.  If the specified string
    * is not formatted correctly, then return null.
    */
   private String extractSymbol(String s) {
      String symbol = null;

      if (s != null) {
         int front = s.indexOf(s_begin);
         if (front >= 0) {
            front += s_begin.length();
            int back = s.indexOf(")", front);
            if (back >= front) {
               symbol = s.substring(front, back).trim();
            }
         }
      }

      return symbol;
   }

  /**
   * The initial code is an array of lines. Each line will be written
   * inside a block comment
   * @param symbol The string symbol to match.  Should be unique in a file
   * @param writer The LanguageWriter to embed the symbols in appropriate
   * comments
   * @param alt_msg An alternate message to embed in comments only if no
   * information was found by the splicer.
   * @param code A default code chunk that will be included
   *             in a block comment.
   * @param live_code A default code chunk that will be included only if no
   * information was found by the splicer.
   */
  private void writeDefaultComments( String symbol, LanguageWriter writer, 
                                     String alt_msg, String alt_code, 
                                     String[] comment_code, String[] live_code) 
  {
    try {
      writer.pushLineBreak(false);
      if ( hasSymbol( symbol ) ) { 
        outputSymbolEdits( symbol, writer );
      } else { 
        writer.writeSplicerTagLine(getBeginString(symbol));
        if ( alt_msg != null ) { 
            if ( d_context.getConfig().getCCAMode()==true) { 
                writer.writeCommentLine(CodeConstants.C_INSERT_HERE + symbol + 
                                        "} (" + alt_msg + ")");
            } else {
                if (alt_msg.equals("") ) { 
                    writer.writeCommentLine("insert code here");
                } else { 
                    writer.writeCommentLine("insert code here (" + alt_msg + ")");
                }
            }
        }
        if ( alt_code != null) { 
          writer.println(alt_code);
        }
        if (comment_code != null && comment_code.length > 0) { 
          writer.beginBlockComment(false);
          for(int i = 0; i < comment_code.length; ++i) {
            writer.println(comment_code[i]);
          }
          writer.endBlockComment(false);
        }
      
        if (live_code != null && live_code.length > 0) { 
          for(int i = 0; i < live_code.length; ++i) {
            writer.printlnUnformatted(live_code[i]);
          }
        }
        writer.writeSplicerTagLine(getEndString(symbol));
      }
    }
    finally {
      writer.popLineBreak();
    }
  }


  /**
   * The easiest method to envoke to create a spliced region 
   * during code generation.
   * @param symbol The string symbol to match.  Should be unique in a file
   * @param writer The LanguageWriter to embed the symbols in appropriate
   * comments
   * @param alt_msg An alternate message to embed in comments only if no
   * information was found by the splicer.
   */
  public void splice( String symbol, LanguageWriter writer, String alt_msg) {
    writeDefaultComments(symbol, writer, alt_msg, null, new String[0], 
                         new String[0]);
  }

  /**
   * The easiest method to envoke to create a spliced region 
   * during code generation.
   * @param symbol The string symbol to match.  Should be unique in a file
   * @param writer The LanguageWriter to embed the symbols in appropriate
   * comments
   * @param alt_msg An alternate message to embed in comments only if no
   * information was found by the splicer.
   * @param alt_code A default code chunk (used for languages that REQUIRE a 
   *                 return value (or some such)
   * information was found by the splicer.
   */
  public void splice( String symbol, LanguageWriter writer, String alt_msg, 
                      String alt_code) 
  {
    writeDefaultComments(symbol, writer, alt_msg, alt_code, new String[0], 
                         new String[0]);
  }
  
  /**
   * The initial code is an array of lines. Each line will be written
   * inside a block comment
   * @param symbol The string symbol to match.  Should be unique in a file
   * @param writer The LanguageWriter to embed the symbols in appropriate
   * comments
   * @param alt_msg An alternate message to embed in comments only if no
   * information was found by the splicer.
   * @param comment_code A default code chunk that will be included
   *             in a block comment.
   * information was found by the splicer.
   */
  public void splice( String symbol, LanguageWriter writer, String alt_msg, 
                      String[] comment_code) 
  {
    writeDefaultComments(symbol, writer, alt_msg, null,  comment_code, 
                         new String[0]);
  }

  /**
   * The initial code is an array of lines. Each line will be written
   * inside a block comment
   * @param symbol The string symbol to match.  Should be unique in a file
   * @param writer The LanguageWriter to embed the symbols in appropriate
   * comments
   * @param alt_msg An alternate message to embed in comments only if no
   * information was found by the splicer.
   * @param comment_code A default code chunk that will be included in a 
   * comment block only if no information was found by the splicer.
   * @param live_code A default code chunk that will be included only if no
   * information was found by the splicer.
   */
  public void splice( String symbol, LanguageWriter writer, String alt_msg, 
                      String[] comment_code, String[] live_code) 
  {
    writeDefaultComments(symbol, writer, alt_msg, null, comment_code, 
                         live_code);
  }

  private void renameSymbol(Map map, String oldName, String newName)
  {
    Object tmp = map.remove(oldName);
    if (tmp != null) {
      map.put(newName, tmp);
    }
  }

  /**
   * If a symbol by the name oldName exists, rename it to newName.  If the
   * oldName doesn't appear, nothing happens.
   * @param oldName the name of symbol to rename
   * @param newName this is the new name if the old one exists.
   */
  public void renameSymbol(String oldName, String newName)
  {
    renameSymbol(d_splice_bounding_lines, oldName, newName);
    renameSymbol(d_symbol_edits, oldName, newName);
    if (d_used_symbols.remove(oldName)) {
      d_used_symbols.add(newName);
    }
  }
}
