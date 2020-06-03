package gov.llnl.babel.ast;

import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.visitor.Visitor;

public class IntLiteral extends NumericLiteral {

  private int d_value = 0;
  
  private boolean d_isLong = false;
  
  private void parseValue(ParseTreeNode src) { 
    String name = src.name;
    int lastidx = name.length()-1;
    char lastchar = name.charAt(lastidx);
    if ( lastchar == 'L' || lastchar == 'l' ) { 
      name = name.substring(0,lastidx);
      d_isLong = true;
    } 
    Integer intType = Integer.decode(name);
    d_value = intType.intValue();
  }
  
  public IntLiteral(ParseTreeNode src, ASTNode parent) {
    super(src, parent);
    if(src != null) {
      parseValue(src);
    }
  }

  public IntLiteral()  { 
    super(null, null);
  }

  public int getInt() { 
      return d_value;
  }
  
  public boolean isLong() { 
    return d_isLong;
  }

  public void setInt(int value) {
    d_value = value;
    d_isLong = false;
  }

  /**
   * Implement the "Visitor" pattern.
   */
  public Object accept(Visitor v, Object data)
  {
    return v.visitIntLiteral(this, data);
  }
}
