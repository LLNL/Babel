package gov.llnl.babel.ast;

import gov.llnl.babel.parsers.sidl2.Token;
import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.visitor.Visitor;

public class ThrowsList extends ScopedIDList {

  public ThrowsList(ParseTreeNode src, ASTNode parent) {
    super(src, parent);
  }
  
  public boolean addException(ScopedID id) {
    Token firstToken = getFirstToken();

    if (firstToken == null ) { 
      setFirstToken(id.getFirstToken());
    }
    
    if(id.getLastToken() != null) {
      setLastToken(id.getLastToken());
    }

    return d_list.add(id); 
  }
  
  public Object accept(Visitor v, Object data) {
    return v.visitThrowsList(this,data);
  }
}
