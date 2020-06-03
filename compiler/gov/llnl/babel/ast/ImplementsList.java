package gov.llnl.babel.ast;

import gov.llnl.babel.parsers.sidl2.ParseTreeNode;

import java.util.HashSet;

public class ImplementsList extends ScopedIDList {
  /** names of interfaces that are implements-all are stored here,
   *  Normal implements are stored in the NodeList LinkedList */
  HashSet d_implements_all = null;
  
  public ImplementsList(ParseTreeNode src, ASTNode parent) {
    super(src, parent);
    d_implements_all = new HashSet();
  }

  public ImplementsList()
  {
    super(null, null);
    d_implements_all = new HashSet();
  }

  public void addImplements(ScopedID id) { 
    d_list.add(id);
  }
  
  public void addImplementsAll(ScopedID id) { 
    add(id);
    d_implements_all.add(id);
  }

  public boolean isImplementsAll(ScopedID id) { 
    return d_implements_all.contains(id);
  }
      
}
