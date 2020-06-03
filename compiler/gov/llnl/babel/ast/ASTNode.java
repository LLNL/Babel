package gov.llnl.babel.ast;

import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.parsers.sidl2.Token;
import gov.llnl.babel.visitor.Visitor;
import gov.llnl.babel.symbols.Symbol;

/**
 * Base class for all AST nodes
 */
public abstract class ASTNode {

  public static final long ILLEGAL_NODEID = -1;

  private Token d_firstToken = null;

  private Token d_lastToken = null;

  private ASTNode d_parent = null;

  private Symbol d_symbol = null;

  private ParseTreeNode d_src = null;

  private long nodeId = ILLEGAL_NODEID;


  public ASTNode() {
  }
  
  /**
   * 
   * @param src
   *          the ParseTreeNode useful for debugging (or null) for generated
   *          code
   * @param parent
   *          the parent ASTNode.
   */
  public ASTNode(ParseTreeNode src, ASTNode parent) {
    d_src = src;
    if (src != null) {
      d_firstToken = src.getFirstToken();
      d_lastToken = src.getLastToken();
    }
    d_parent = parent;
  }

  public Token getFirstToken() {
    return d_firstToken;
  }

  public Token getLastToken() {
    return d_lastToken;
  }

  protected void setFirstToken(Token token) {
    d_firstToken = token;
  }

  protected void setLastToken(Token token) {
    d_lastToken = token;
  }

  /**
   * If we have information about the sourcecode, traverse parent pointers until
   * we get to a node that knows its filename. Return null if information is not
   * available (or in the case of generated ASTs, not applicable)
   * 
   * @return
   */
  public String getFilename() {
    // traverse up parent pointers until
    // we get to a node of type SIDLFile
    // (as
    if (d_parent != null ) { // && d_firstToken != null) {
      return d_parent.getFilename();
    } else {
      return null;
    }
  }

  public void setParent(ASTNode node) {
    d_parent = node;
  }

  public ASTNode getParent() {
    return d_parent;
  }

  public void setParseTreeNode(ParseTreeNode src) {
    d_src = src;
  }

  public ParseTreeNode getParseTreeNode() {
    return d_src;
  }

  public void setSymbolTableEntry( Symbol symbol ) { 
    d_symbol = symbol;
  }
  
  public Symbol getSymbolTableEntry() { 
    return d_symbol;
  }

  public void setNodeId(long id) {
    nodeId = id;
  }
  
  public long getNodeId() {
    return nodeId;
  }

  /**
   * implements "Visitor Pattern"
   * 
   * @param v
   *          The visitor class
   * @param data
   *          Optional extra data
   * @return Optional extra data
   */
  abstract public Object accept(Visitor v, Object data);
}
