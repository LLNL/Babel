package gov.llnl.babel.ast;

import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.visitor.Visitor;

public class MethodName extends Name {
  
  protected Name d_name;

  protected Name d_shortName;
  
  protected Name d_nameExtension;
  
  public Name getName() {
    return d_name;
  }

  public void setName(Name name) {
    d_name = name; 
  }
  
  public Name getShortName() { 
    return d_shortName;
  }
  
  public void setShortName(Name shortName) { 
    d_shortName = shortName;
  }
  
  public Name getNameExtension() { 
    return d_nameExtension;
  }
  
  public void setNameExtension(Name extension) { 
    d_nameExtension = extension;
  }
  
  public MethodName(ParseTreeNode src) {
    super(src);
  }

  public MethodName(ParseTreeNode node, ASTNode parent) {
    super(node, parent);
  }

  public MethodName() {
    super((ParseTreeNode)null);
  }
  
  public String getLongName() {
    if ( d_nameExtension == null ) { 
      return d_shortName.toString();
    } else { 
      return d_shortName.toString() + d_nameExtension.toString();
    }
  }

  public String toString() { 
    if ( d_nameExtension == null ) { 
      return d_shortName.toString();
    } else { 
      return d_shortName.toString() + "[" + d_nameExtension +"]";
    }
  }

  public Object accept(Visitor v, Object data) {
    //return v.visitMethodName( this, data );
    return null;
  }
}
