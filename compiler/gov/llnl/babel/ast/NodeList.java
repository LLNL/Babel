package gov.llnl.babel.ast;

import gov.llnl.babel.parsers.sidl2.ParseTreeNode;

import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.ListIterator;

public abstract class NodeList extends ASTNode {

  protected LinkedList d_list = null;
  
  public NodeList(ParseTreeNode src, ASTNode parent) {
    super(src, parent);
    d_list = new LinkedList();
  }

  public abstract NodeList cloneEmpty();
  
  public int size() {
    return d_list.size();
  }

  public void clear() {
    d_list.clear();
  }

  public boolean isEmpty() {
    return d_list.isEmpty();
  }

  public Iterator iterator() { 
    return d_list.iterator();
  }
  
  public ListIterator listiterator() { 
    return d_list.listIterator();
  }

  public List getList() {
    return d_list;
  }
}
