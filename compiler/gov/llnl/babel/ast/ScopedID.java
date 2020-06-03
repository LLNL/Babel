package gov.llnl.babel.ast;

import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.visitor.Visitor;

import java.util.LinkedList;
import java.util.Iterator;

/**
 * A list of dot separated identifiers (possibly with leading dot).
 * 
 * A ScopedID should ultimately be resolved to some NamedType
 */
public class ScopedID extends Type implements Comparable {

  LinkedList d_ids;

  private void initName(String name) { 
    d_ids = new LinkedList();
    if (name == null) {
      return;
    }
    int start = 0;
    int len = name.length();
    while (start < len) {
      int end = name.indexOf('.', start);
      if (end == -1) {
        end = len;
      }
      d_ids.add(name.substring(start, end));
      start = end + 1;
    }
  }

  public ScopedID( String name ) { 
    super(null,null);
    initName(name);
  }
  
  public ScopedID(ParseTreeNode src, ASTNode parent) {
    super(src,parent);
    initName(src.name); 
  }
  
  public String getTypeName() { 
    return "scoped-id";
  }
  
  public int compareTo(Object o) {
    if (!(o instanceof ScopedID)) {
      return -1;
    }
    ScopedID sid = (ScopedID) o;

    int minlen = (d_ids.size() < sid.d_ids.size()) ? d_ids.size() : sid.d_ids
      .size();
    Iterator me = d_ids.iterator();
    Iterator they = sid.d_ids.iterator();
    for (int i = 0; i < minlen; i++) {
      String my_string = (String) me.next();
      int diff = my_string.compareTo((String)they.next());
      if (diff != 0) {
        return diff;
      }
    }
    if (me.hasNext()) {
      return 1;
    } else if (they.hasNext()) {
      return -1;
    } else {
      return 0;
    }
  }

  public boolean equals(Object o) {
    return (compareTo(o) == 0);
  }

  public int hashCode() {
    int result = 0, g;
    Iterator i = d_ids.iterator();
    while (i.hasNext()) {
      result = (result << 4) + i.next().toString().hashCode();
      g = (result & 0xf0000000);
      if (g != 0) {
        result = result ^ (g >> 24);
        result = result ^ g;
      }
    }
    return result;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer();
    for (Iterator i = d_ids.iterator(); i.hasNext();) {
      String id = (String) i.next();
      sb.append(id);
      if (i.hasNext()) {
        sb.append(".");
      }
    }
    return sb.toString();
  }

  public Object accept(Visitor v, Object data) {
    return v.visitScopedID(this,data);
  }
}
