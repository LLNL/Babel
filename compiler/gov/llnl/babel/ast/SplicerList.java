package gov.llnl.babel.ast;

import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.visitor.Visitor;

import java.util.Iterator;

public class SplicerList extends NodeList {

  public SplicerList(ParseTreeNode src, ASTNode parent) {
    super(src, parent);
  }

  public boolean addSplicerBlock(SplicerBlock block) { 
    return d_list.add(block);
  }
  
  public NodeList cloneEmpty() {
    SplicerList newList = new SplicerList(getParseTreeNode(), getParent());
    return newList;
  }

  public boolean hasSplicerBlock(String loc) {
    for (Iterator i = d_list.iterator(); i.hasNext();) {
      SplicerBlock b = (SplicerBlock) i.next();
      if (b.getLocation().equals(loc)) {
        return true;
      }
    }
    return false;
  }

  public boolean hasSplicerBlock(String loc, String name) {
    for (Iterator i = d_list.iterator(); i.hasNext();) {
      SplicerBlock b = (SplicerBlock) i.next();
      if (b.getLocation().equals(loc) && b.getName().equals(name)) {
        return true;
      }
    }
    return false;
  }

  public Object accept(Visitor v, Object data) {
    return v.visitSplicerList(this,data);
  }

}
