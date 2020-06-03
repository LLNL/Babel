package gov.llnl.babel.ast;

import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.visitor.Visitor;

public class ScopedIDList extends NodeList {

  public ScopedIDList(ParseTreeNode src, ASTNode parent) {
    super(src, parent);
  }

  public NodeList cloneEmpty() {
    ScopedIDList newList = new ScopedIDList(getParseTreeNode(), getParent());
    return newList;
  }

  public Object accept(Visitor v, Object data) {
    return v.visitScopedIDList(this,data);
  }

  public void add(ScopedID id) {
    d_list.add(id);
  }

}
