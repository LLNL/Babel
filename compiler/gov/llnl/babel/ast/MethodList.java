package gov.llnl.babel.ast;

import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.visitor.Visitor;

public class MethodList extends NodeList {

  public MethodList(ParseTreeNode src, ASTNode parent) {
    super(src, parent);
  }

  public boolean addMethod(Method method) { 
    return d_list.add(method);
  }
  
  public NodeList cloneEmpty() {
    MethodList newList = new MethodList(getParseTreeNode(), getParent());
    return newList;
  }

  public Object accept(Visitor v, Object data) {
    return v.visitMethodList(this,data);
  }

}
