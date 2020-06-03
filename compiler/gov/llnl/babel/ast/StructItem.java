package gov.llnl.babel.ast;

import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.visitor.Visitor;

public class StructItem extends ASTNode implements INameable {

  protected Type d_type;
  
  protected Name d_name;
  
  public StructItem(ParseTreeNode src, ASTNode parent) {
    super(src, parent);
  }

  public Object accept(Visitor v, Object data) {
    return v.visitStructItem(this,data);
  }

  public Name getName() {
    return d_name;
  }

  public void setName(Name name) {
    d_name = name;
  }

  public Type getType() { 
    return d_type;
  }
  
  public void setType(Type type) { 
    d_type = type;
  }
  
}
