package gov.llnl.babel.msg;

import gov.llnl.babel.ast.ASTNode;

/**
 * Encapsulation of a single Error, Warning, or Remark based on two conflicting 
 * tokens.
 */
public class UserMsg2 extends UserMsg {

  protected String d_msg2;
  protected ASTNode d_ast_node2;
  
  protected int d_line2;
  protected int d_col2;
  protected String d_filename2;


  public UserMsg2(int type, String msg, ASTNode ast_node, String msg2, ASTNode ast_node2 ) {
    super(type, msg, ast_node);
    d_msg2 = msg2;
    if(ast_node2 != null) {
      d_ast_node2 = ast_node2;
      d_filename2 = d_ast_node2.getFilename();
      d_line2 = d_ast_node2.getFirstToken().beginLine;
      d_col2 = d_ast_node2.getFirstToken().beginColumn;
    } else {
      d_ast_node2 = null;
      d_filename = "";
      d_line = 0;
      d_col = 0;
    }
  }
  
  public String toString() { 
    StringBuffer buf = new StringBuffer();
    buf.append( super.toString());
    buf.append('\n');
    buf.append(d_filename2);
    buf.append(':');
    buf.append(d_line2);
    buf.append(": (cont'd): ");
    buf.append(d_msg2);
    if ( d_verbiosity >= 2 && d_ast_node2 != null) { 
      buf.append('\n');
      buf.append( ErrorFormatter.formatOffendingTokens(d_ast_node2.getFilename(), 
          d_ast_node2.getFirstToken(), d_ast_node2.getLastToken()));
    }
    return buf.toString();
  } 
}
