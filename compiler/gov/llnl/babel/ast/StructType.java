package gov.llnl.babel.ast;

import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.visitor.Visitor;
import java.util.LinkedList;

public class StructType extends AttributedType {

  protected LinkedList d_structItems;

  public StructType(ParseTreeNode src, ASTNode parent, Name name) {
    super(src, parent, name);
    d_structItems = new LinkedList();
  }

  public String getTypeName() { 
    return "struct";
  }
  
  public LinkedList getStructItemList() { 
    return d_structItems;
  }
  
  public void addStructItem(StructItem i) { 
    d_structItems.add(i);
    i.setParent(this);
  }

  public Object accept(Visitor v, Object data) {
    return v.visitStructType(this,data);
  }

}
