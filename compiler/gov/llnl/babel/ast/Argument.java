package gov.llnl.babel.ast;

import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.visitor.Visitor;

/**
 * Unit of transfer in a method, holding the doccomment, attributes, mode, type,
 * and variable name.
 */
public class Argument extends ASTNode implements INameable,
IAttributable,INameableStruct {

  protected DocComment d_comment = null;
  
  protected AttributeList d_attribList;
  
  protected String d_mode;
  
  protected Type d_type;
  
  protected Name d_name;
  protected Name d_name2;
  
  static final String[] s_allowableModes = {"in", "out", "inout"};
  
  public Argument(ParseTreeNode src, ASTNode parent) {
    super(src, parent);
    d_attribList = new AttributeList(null,this);
    if(src != null) {
      setDocComment(src.doc);
    }
  }

  public String getMode() { 
    return d_mode;
  }

  public Name getName2() {
    return d_name2;
  }

  public void setName2(Name name2) {
    d_name2 = name2;
  }
  
  public boolean setMode(String mode) { 
    for(int i=0; i<s_allowableModes.length;++i) { 
      if (mode.equals(s_allowableModes[i])) { 
        d_mode = mode;
        return true;
      }
    }
    return false;
  }
  
  public Name getName() {
    return d_name;
  }

  public void setName(Name name) {
    d_name = name;
  }

  public AttributeList getAttributeList() {
    return d_attribList;
  }

  public void setAttributeList(AttributeList attrib) {
    d_attribList = attrib;
  }

  public boolean hasDocComment() {
    return d_comment != null;
  }

  public DocComment getDocComment() {
    return d_comment;
  }

  public void setDocComment(String text) {
    if (text == null || text.length() == 0) {
      d_comment = null;
    } else {
      d_comment = new DocComment(text);
    }
  }  
  
  public Type getType() { 
    return d_type;
  }
  
  public void setType(Type type) { 
    d_type = type;
  }
  
  public Object accept(Visitor v, Object data) {
    return v.visitArgument( this, data );
  }
}
