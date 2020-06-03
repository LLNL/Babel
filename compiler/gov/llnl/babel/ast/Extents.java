package gov.llnl.babel.ast;

import java.util.LinkedList;

import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.visitor.Visitor;
public class Extents extends ASTNode  {

  protected LinkedList d_list;
  
  public Extents(ParseTreeNode src, ASTNode parent) { 
    super(src,parent);
    d_list = new LinkedList();
  }
  
  public void addExtent(ASTNode expr) {
    if (expr != null) { 
      d_list.add(expr);
    }
  }
  
  /**
   * Return a linked list of {@link ASTNode}'s. Each of these should
   * be an expression.
   */
  public LinkedList getExtents() { 
    return d_list;
  }

  public Object accept(Visitor v, Object data) {
    return v.visitExtents(this,data);
  }
}
