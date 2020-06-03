package gov.llnl.babel.ast;

import gov.llnl.babel.visitor.Visitor;
import gov.llnl.babel.parsers.sidl2.ParseTreeNode;

import java.util.Iterator;

public class AttributeList extends NodeList {

  public AttributeList(ParseTreeNode src, ASTNode parent) {
    super(src, parent);
  }

  public NodeList cloneEmpty() {
    AttributeList newList = new AttributeList(getParseTreeNode(), getParent());
    return newList;
  }

  public boolean addAttribute(Attribute attr) {
    return d_list.add(attr);
  }

  /**
   * Returns true iff a named attribute is
   * 
   * @param builtin
   * @return
   */
  public boolean hasAttribute(String builtin) {
    for (Iterator i = d_list.iterator(); i.hasNext();) {
      Attribute a = (Attribute) i.next();
      if (a.getKey().equals(builtin)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns builtin Attribute by name if it exists, null otherwise.
   * 
   * @param builtin
   * @return
   */
  public Attribute getAttribute(String builtin) {
    for (Iterator i = d_list.iterator(); i.hasNext();) {
      Attribute a = (Attribute) i.next();
      if (a.getKey().equals(builtin)) {
        return a;
      }
    }
    return null;
  }

  /**
   * Check for matching key/value pair of attributes (or key, or value)
   * 
   * @param key
   *          The matching key or asterisk (*) meaning "any"
   * @param value
   *          The matching value or asterisk (*) meaning "any"
   * @return
   * @see Attribute
   */
  public boolean hasAttribute(String key, String value) {
    for (Iterator i = d_list.iterator(); i.hasNext();) {
      Attribute a = (Attribute) i.next();
      if ((key.equals("*") || key.equals(a.getKey()))
          && (value.equals("*") || value.equals(a.getValue()))) {
        return true;
      }
    }
    return false;
  }

  public Object accept(Visitor v, Object data) {
    return v.visitAttributeList(this, data);
  }
}
