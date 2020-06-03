package gov.llnl.babel.msg;

import gov.llnl.babel.ast.ASTNode;

/**
 * Encapsulation of a single Error, Warning, or Remark based on a single
 * Token to provide back to the user.
 */
public class UserMsg implements Comparable {
  
  public static final int INTERNAL_ERROR = 0;
  public static final int ERROR = 1;
  public static final int WARNING = 2;
  public static final int REMARK = 3;
  
  public static String s_types[] = {"internal_error", "error", "warning", "remark"};
  
  protected int d_line;
  protected int d_col;
  protected String d_filename;

  protected int d_type;
  protected String d_msg;
  protected ASTNode d_ast_node;
  
  protected int d_verbiosity;
  
  /**
   * User Messages sort by line number first, column number second.
   */
  public int compareTo(Object obj) { 
    UserMsg m = (UserMsg) obj;
    // if filenames don't match decide based on that
    int result = d_filename.compareTo(m.d_filename);
    if ( result != 0 ) { return result; } 
    // else if lines don't match decide based on that
    result = d_line - m.d_line;
    if ( result != 0 ) { return result; } 
    // else decide on column
    result = d_col - m.d_col;
    if ( result != 0 ) { return result; } 
    //else, decide on msg
    return d_msg.compareTo(m.d_msg);
  }

  public UserMsg( int type, String msg, ASTNode ast_node ) { 
    d_type = type;
    d_msg = msg;
    d_ast_node = ast_node;
    if(ast_node != null) {
      d_filename = d_ast_node.getFilename();
      d_line = d_ast_node.getFirstToken().beginLine;
      d_col = d_ast_node.getFirstToken().beginColumn;
    } else {
      d_ast_node = null;
      d_filename = "";
      d_line = 0;
      d_col = 0;
    }
  }

  //For messages that have no ASTnode
  public UserMsg( int type, String msg) { 
    d_type = type;
    d_msg = msg;
    d_ast_node = null;
    d_filename = "";
    d_line = 0;
    d_col = 0;
  }

  public String toString() { 
    StringBuffer buf = new StringBuffer();
    buf.append(d_filename);
    buf.append(':');
    buf.append(d_line);
    buf.append(": ");
    buf.append(s_types[d_type]);
    buf.append(": ");
    buf.append(d_msg);
    if ( d_verbiosity >= 2 && d_ast_node != null) { 
      buf.append('\n');
      buf.append( ErrorFormatter.formatOffendingTokens(d_ast_node.getFilename(), 
          d_ast_node.getFirstToken(), d_ast_node.getLastToken()));
   }
    return buf.toString();
  }
  
  public int getType() { 
    return d_type;
  }
  
  public int getVerbiosity() { 
    return d_verbiosity;
  }
  
  public void setVerbiosity(int verbiosity) { 
    d_verbiosity = verbiosity;
  }
}
