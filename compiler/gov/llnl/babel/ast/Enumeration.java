package gov.llnl.babel.ast;

import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.visitor.Visitor;
import java.util.LinkedList;

/**
 * User-defined type that has no methods, but associates names with fixed
 * values.
 */
public class Enumeration extends AttributedType {

  LinkedList d_items;

  public Enumeration() {
    super(null, null, null);
  }

  public Enumeration(ParseTreeNode src, ASTNode parent, Name name) {
    super(src, parent, name);
    d_items = new LinkedList();
  }

  public String getTypeName() { 
    return "enumeration";
  }
  
  public void addEnumItem(EnumItem i) {
    d_items.add(i);
    i.setParent(this);
  }

  public LinkedList getEnumItemList() {
    return d_items;
  }

  public Object accept(Visitor v, Object data) {
    v.visitEnumeration(this, data);
    return data;
  }
}
