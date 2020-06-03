package gov.llnl.babel.ast;

import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.visitor.Visitor;

public class FromClause extends ASTNode implements INameable {

  protected ScopedID d_id;
  
  protected MethodName d_name;
  
  public FromClause(ParseTreeNode src, ASTNode parent) {
    super(src, parent);
    // TODO Auto-generated constructor stub
  }

  public Object accept(Visitor v, Object data) {
    return v.visitFromClause(this,data);
  }

  public Name getName() {
    return d_name;
  }

  public void setName(Name name) {
    if ( name instanceof MethodName) { 
        d_name = (MethodName) name;
    } else { 
        d_name.setName(name);
    }
  }

  public MethodName getMethodName() { 
    return d_name;
  }

  public void setMethodName(MethodName name) { 
    d_name = name;
  }

  public ScopedID getScopedID() { 
    return d_id;
  }
  
  public void setScopedID(ScopedID id) { 
    d_id = id;
  }
}
