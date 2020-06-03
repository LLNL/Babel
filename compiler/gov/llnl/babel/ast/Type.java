package gov.llnl.babel.ast;

import gov.llnl.babel.parsers.sidl2.ParseTreeNode;

/**
 * Common base class for all types; built-in and user-defined.
 */
public abstract class Type extends ASTNode {

  public Type()
  {
  }
  
  public Type(ParseTreeNode src, ASTNode parent) {
    super(src, parent);
  }
  
  public abstract String getTypeName();

}
