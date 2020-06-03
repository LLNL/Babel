package gov.llnl.babel.ast;

import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.visitor.Visitor;

/**
 * Has no implementation, and all methods are virtual.
 */
public class InterfaceType extends Extendable {
  
  /** a list of ScopedID's */
  protected ExtendsList d_extends = null;
  
  public InterfaceType(ParseTreeNode src, ASTNode parent, Name name) {
    super(src, parent, name);
    d_extends = new ExtendsList(src, parent);
  }

  public InterfaceType()
  {
    super(null, null, null);
    d_extends = new ExtendsList(null, null);
  }

  public String getTypeName() { 
    return "interface";
  }
  
  /** returns a linked list of ScopedID's */
  public ExtendsList getExtends() { 
    return d_extends;
  }
  
  public Object accept(Visitor v, Object data) {
    return v.visitInterfaceType( this, data );
  }
}
