package gov.llnl.babel.msg;

import java.io.File;
import java.net.URL;
import java.io.RandomAccessFile;
import gov.llnl.babel.parsers.sidl2.ParseException;
import gov.llnl.babel.parsers.sidl2.SIDLParserConstants;
import gov.llnl.babel.parsers.sidl2.Token;

public class ErrorFormatter {

  /**
   * Generate two lines of text the first with line of specified by the token
   * the second some carets (^) pointing out the actual token
   * @param filename where the token was found (if null, then empty string returned)
   * @param t token to highlight
   * 
   * @return a two line string suitable for error messages or a message explaining 
   *         that the original contents of the file cannot be found.
   */
  public static String formatOffendingToken( String filename, Token t ) { 
    return formatOffendingTokens( filename, t, t);
  }
  
  /**
   * Generate two lines of text; the first with line specified by the token range, 
   * the second some carets (^) point out the offending tokens.  Note if the token
   * range spans more than one line, then only the first line is displayed.
   * 
   * @param filename where the token was found (if null, then empty string returned)
   * @param begin first token to highlight
   * @param end last token to highlight
   * @return 
   * @see #formatOffendingToken
   */
  public static String formatOffendingTokens( String filename, Token begin, Token end ) { 
    if (filename == null || begin == null) { 
      return ""; 
    }
    Token last = null;
    if ( begin == end ) { 
      last = end;
    } else { 
      Token lookahead = begin;
      while( lookahead.beginLine == begin.beginLine && last != end ) { 
          last=lookahead;
          lookahead=lookahead.next;
      }
    }
    
    int line = begin.beginLine;
    int col_begin = begin.beginColumn;
    int col_end = (last==null) ? begin.endColumn : last.endColumn;

    try { 
      File f = new File( filename );
      RandomAccessFile r = new RandomAccessFile(f, "r");
      StringBuffer sb = new StringBuffer();
      String s = "";
      for (int i = 0; i < line; i++) {
        s = r.readLine();
      }
      sb.append(s);
      sb.append('\n');
      for (int i = 1; i < col_begin; i++) {
        sb.append(' ');
      }
      for (int i = col_begin; i <= col_end; i++) {
        sb.append('^');
      }
      if ( last != end ) {
        int truncated_lines = end.endLine - begin.beginLine;
        String line_or_lines = (truncated_lines == 1) ? 
            "one more line" : truncated_lines + " more lines";
        sb.append(" Flagged region extends " + line_or_lines + ".");
      }
      return sb.toString();
    } catch( Exception e ) { 
        return "(Cannot retrieve exact file contents.)\n" + e.getMessage();
    }
  }
  
  public static void formatParseError(ParseException e, String url) {
    System.err.println(url + ":" + e.currentToken.next.beginLine
        + ": error: Invalid SIDL.  Parser threw fatal exception at "
        + SIDLParserConstants.tokenImage[e.currentToken.next.kind]
        + " token.");
    try {
      if (url != null) { 
        URL u = new URL(url);
        String detail = formatOffendingToken( u.getFile(), e.currentToken.next );
        System.err.println(detail);
      }
      e.printStackTrace();
    } catch (Exception ex) {
      System.err.println(ex.getMessage());
      ex.printStackTrace();
      System.exit(2);
    }
  }
}
