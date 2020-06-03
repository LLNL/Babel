package gov.llnl.babel.ast;

import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.visitor.Visitor;

public class SplicerImplList extends NodeList {

  public SplicerImplList(ParseTreeNode src, ASTNode parent) {
    super(src, parent);
  }

  public boolean addSplicerImpl(SplicerImpl impl) { 
    return d_list.add(impl);
  }

  public boolean addSplicerImpl(String impl) { 
    return d_list.add(new SplicerImpl(impl));
  }

  public boolean addDefaultImpl() {
    return addSplicerImpl(getDefaultCommentText());
  }

  public NodeList cloneEmpty() {
    SplicerImplList newList = new SplicerImplList(getParseTreeNode(), getParent());
    return newList;
  }

  public Object accept(Visitor v, Object data) {
    return v.visitSplicerImplList(this,data);
  }

  public static String getDefaultCommentText() {
    return SplicerImpl.s_default_text;
  }
  
}
