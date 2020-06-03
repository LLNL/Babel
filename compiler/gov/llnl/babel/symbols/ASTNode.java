  //
  // File:        ASTNode.java
  // Package:     gov.llnl.babel.symbols
  // Copyright:   (c) 2005 Lawrence Livermore National Security, LLC
  // Release:     $Name$
  // Revision:     @(#) $Id: ASTNode.java 6426 2008-07-03 16:56:09Z epperly $
  // Description: Base class for all nodes in the AST

package gov.llnl.babel.symbols;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ASTNode implements Cloneable { 
  protected boolean d_frozen = false;

  public void freeze() {
    d_frozen = true;
  }

  public Object clone()
    throws java.lang.CloneNotSupportedException
  {
    ASTNode ast = (ASTNode)super.clone();
    ast.d_frozen = false;
    return ast;
  }

  protected void checkFrozen()
  {
    if (d_frozen) {
      throw new java.lang.UnsupportedOperationException("Attempt to modify a frozen Symbol definition.");
    }
  }

  public List protectList(List l)
  {
    return (l != null) ? (d_frozen ? Collections.unmodifiableList(l) : l) 
      : null;
  }

  public Collection protectCollection(Collection c)
  {
    return (c != null) ? (d_frozen ? Collections.unmodifiableCollection(c) :c) 
      : null;
  }

  public Set protectSet(Set s)
  {
    return (s != null) ? (d_frozen ? Collections.unmodifiableSet(s) : s)
      : null;
  }

  public Map protectMap(Map m)
  {
    return (m != null) ? (d_frozen ? Collections.unmodifiableMap(m) : m)
      : null;
  }
}
