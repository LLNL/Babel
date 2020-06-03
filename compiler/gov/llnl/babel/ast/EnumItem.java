package gov.llnl.babel.ast;

import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.visitor.Visitor;

/**
 * A single item inside an enumeration. It has a name and (minimally) and
 * implicit value. The value may also be set explicitly. The name and final
 * value should both be unique to the enumeration.
 * 
 * @author kumfert
 * 
 */
public class EnumItem extends ASTNode implements INameable {

  /** The name of the single item in the enumeration */
  private Name d_name = null;

  /** The value the user explicitly assigned to this item (null otherwise) */
  private IntLiteral d_explicitValue = null;

  /** The final, unique value (or null if undetermined) */
  private Integer d_value = null;

  public EnumItem(ParseTreeNode src) {
    super(src, null);
  }

  public EnumItem(ParseTreeNode src, ASTNode parent) {
    super(src, parent);
  }

  public EnumItem() {
  }

  public Name getName() {
    return d_name;
  }

  public void setName(Name name) {
    d_name = name;
  }

  public boolean hasValue() {
    return (d_value != null);
  }

  public int getValue() throws java.lang.NullPointerException {
    return d_value.intValue();
  }

  public void setValue(int i) {
    d_value = new Integer(i);
  }

  public boolean isUserValue() {
    return d_explicitValue != null;
  }

  public IntLiteral getExplicitValue() {
    return d_explicitValue;
  }

  public void setExplicitValue(IntLiteral intLiteral) {
    d_explicitValue = intLiteral;
    d_value = new Integer(intLiteral.getInt());
  }

  public String toString() {
    StringBuffer buf = new StringBuffer(d_name.toString());
    if (d_value != null) {
      buf.append("=");
      buf.append(d_value.toString());
    }
    return buf.toString();
  }

  public Object accept(Visitor v, Object data) {
    v.visitEnumItem(this, data);
    return null;
  }

}
