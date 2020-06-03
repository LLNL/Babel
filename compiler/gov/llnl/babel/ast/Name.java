package gov.llnl.babel.ast;

import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.visitor.Visitor;

/**
 * A user defined name for things... can be applied to <code>NamedType</code>s
 *  and <code>Argument</code>s.
 * 
 * @author kumfert
 *
 */
public class Name extends ASTNode {

  protected String d_name;
  
  /**
   * This unusual (for a child of <code>Node</code>) constructor does not 
   * require a parent node (it is assumed that this will be determined later)
   * 
   * @param src
   * @see #setParent(ASTNode)
   */
  public Name(ParseTreeNode src) {
    this(src, null);
  }

  public Name(String name) {
    d_name = name;
  }

  public Name(ParseTreeNode node, ASTNode parent) { 
    super(node, parent);
    if(node != null) {
      d_name = node.name;
    }
  }
  
  public String toString() { 
    return d_name;
  }

  public boolean equals(Object o) {
      return ((o != null) && (o instanceof Name)) ?
        (((Name)o).toString().equals(this.toString())) : false;
  }

  public int hashCode() {
    return d_name.hashCode();
  }
    
  public Object accept(Visitor v, Object data) {
    return v.visitName( this, data );
  }
}
