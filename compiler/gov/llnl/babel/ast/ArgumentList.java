package gov.llnl.babel.ast;

import gov.llnl.babel.parsers.sidl2.Token;
import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.visitor.Visitor;

public class ArgumentList extends NodeList {

  public ArgumentList(ParseTreeNode src, ASTNode parent) {
    super(src, parent);
  }

  public NodeList cloneEmpty() {
    ArgumentList newList = new ArgumentList(getParseTreeNode(), getParent());
    return newList;
  }

  public boolean addArgument(Argument arg) { 
    Token firstToken = getFirstToken();

    if (firstToken == null ) { 
      setFirstToken(arg.getFirstToken());
    }
    
    if(arg.getLastToken() != null) {
      setLastToken(arg.getLastToken());
    }
    return d_list.add(arg);
  }
  
  public Object accept(Visitor v, Object data) {
    return v.visitArgumentList( this, data );
  }

}
