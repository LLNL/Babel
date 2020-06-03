package gov.llnl.babel.ast;

import gov.llnl.babel.parsers.sidl2.ParseTreeNode;

/**
 * Common base class for user defined types that support inheritance such as
 * <code>Class</code>es and <code>Interface</code>s.
 */
public abstract class Extendable extends AttributedType {

  protected MethodList d_methodList;
  protected Invariants d_invariants = null; 

  public Extendable(ParseTreeNode src, ASTNode parent, Name name) {
    super(src, parent, name);
    d_methodList = new MethodList(null,this);
  }

  public MethodList getMethodList() { 
    return d_methodList;
  }
  
  public void setMethodList( MethodList methods ) { 
    d_methodList = methods;
  }
  
  public Invariants getInvariants() { 
    return d_invariants;
  }

  public void setInvariants(Invariants invariants) { 
      d_invariants = invariants;
  }  

}
