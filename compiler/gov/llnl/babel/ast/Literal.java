package gov.llnl.babel.ast;

import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.visitor.Visitor;

public class Literal extends ASTNode {

  public Literal(ParseTreeNode src, ASTNode parent) {
    super(src, parent);
    // TODO Auto-generated constructor stub
  }

  public Object accept(Visitor v, Object data) {
    // TODO Auto-generated method stub
    return null;
  }

}
