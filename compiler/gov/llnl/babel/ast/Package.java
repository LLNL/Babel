package gov.llnl.babel.ast;

import java.util.List;
import java.util.LinkedList;

import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.visitor.Visitor;

/**
 * The only <code>NamedType</code> that contains other types.
 */
public class Package extends NamedType implements IAttributable {

  /** a list of NamedTypes */
  protected LinkedList d_types;

  /** the list of attributes (modifiers) */
  protected AttributeList d_attribList;

  public Package()
  {
    d_attribList = new AttributeList(null, this);
  }

  public Package(ParseTreeNode src, ASTNode parent, Name name, Version version) {
    super(src, parent, name);
    setVersion(version);
    d_types = new LinkedList();
    d_attribList = new AttributeList(null, null);
  }

  public String getTypeName() { 
    return "package";
  } 
  
  public List getNamedTypes() {
    return d_types;
  }

  public void appendNamedType(NamedType type) {
    d_types.add(type);
  }

  public AttributeList getAttributeList() {
    return d_attribList;
  }

  public void setAttributeList(AttributeList attrib) {
    d_attribList = attrib;
  }

  public Object accept(Visitor v, Object data) {
    return v.visitPackage(this, data);
  }
}
