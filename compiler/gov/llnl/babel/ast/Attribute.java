package gov.llnl.babel.ast;

import gov.llnl.babel.parsers.sidl2.SIDLParserTreeConstants;
import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.visitor.Visitor;

/**
 * General encapsulation of built-in attributes (such as <code>final</code> or
 * <code>abstract</code>) as well as user-defined attributes, including both
 * singletons, or key-value pairs.
 * <p>
 * 
 * Built-in varieties are assumed to have an implicit key with a leading
 * underscore for instance <code>nonblocking</code>, <code>oneway</code>,
 * <code>local</code> are all mutually exclusive values on the key
 * <code>_communication</code>. Use <code>getVisibleKey</code> if you
 * prefer not to be bothered with this implementation detail.
 * 
 * User defined varieties such as <code>%attrib{blue}</code> are assigned to
 * keys with the value of <code>null</code>. Note that keys tend to be
 * mutually exclusive... so <code>%attrib{color,color=blue}</code> will cause
 * a compilation error.
 * <p>
 * 
 * @author kumfert
 * 
 */
public class Attribute extends ASTNode implements Comparable {

  private String d_key;

  private String d_value;

  private boolean d_builtin = false;

  public Attribute(ParseTreeNode src) {
    this(src, null);
  }

  public Attribute(String key, String value) {
    d_key = key;
    d_value = value;
  }

  public Attribute(ParseTreeNode src, ASTNode parent) {
    super(src, parent);

    d_key = src.name;
    d_value = null;
    if (src.getID() == SIDLParserTreeConstants.JJTCUSTOMATTR) {
      int index = src.name.indexOf('=');
      if (index >= 0) {
        d_key = src.name.substring(0, index);
        // remove quotes
        d_value = src.name.substring(index + 2, src.name.length()-1);
      }
      d_builtin = false;
    }
    else {
      d_builtin = true;
    }
  }

  public boolean isBuiltin() {
    return d_builtin;
  }

  public String getKey() {
    return d_key;
  }

  public String getValue() {
    return d_value;
  }

  public int compareTo(Object o) {
    return d_key.compareTo(((Attribute) o).d_key);
  }

  public String toString() {
    if (d_value == null) {
      return d_key;
    } else {
      return d_key + "=\"" + d_value + "\"";
    }
  }

  public Object accept(Visitor v, Object data) {
    return v.visitAttribute( this, data );
  }
}
