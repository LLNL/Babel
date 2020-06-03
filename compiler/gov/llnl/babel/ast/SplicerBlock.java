package gov.llnl.babel.ast;

import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.visitor.Visitor;

public class SplicerBlock extends ASTNode {
  /** The target location of the splicer block (e.g., "header", "source") */
  protected String          d_location = null;

  /** The name (extension) associated with the splicer block */
  protected String          d_name   = null;

  /** The container of the list of implementation line(s). */
  protected SplicerImplList d_impl   = null;
  
  public SplicerBlock(ParseTreeNode src, ASTNode parent) {
    super(src, parent);
    d_impl = new SplicerImplList(src, parent);
  }

  public SplicerBlock(ParseTreeNode src, ASTNode parent, String loc, 
                      String name) {
    super(src, parent);
    d_location = loc;
    d_name     = name;
    d_impl = new SplicerImplList(src, parent);
    addDefaultImpl();
  }

  public SplicerBlock(ParseTreeNode src, ASTNode parent, String loc, 
                      String name, String impl) {
    super(src, parent);
    d_location = loc;
    d_name     = name;
    d_impl     = new SplicerImplList(src, parent);
    addSplicerImpl(impl);
  }

  public String getLocation() {
    return d_location;
  }

  public String getName() {
    return d_name;
  }

  public boolean addSplicerImpl(String impl) {
    return d_impl.addSplicerImpl(impl);
  }

  public boolean addSplicerImpl(SplicerImpl impl) {
    return d_impl.addSplicerImpl(impl);
  }

  public boolean addDefaultImpl() {
    return d_impl.addDefaultImpl();
  }

  public SplicerImplList getSplicerImplList() {
    return d_impl;
  }

  public Object accept(Visitor v, Object data) {
    return v.visitSplicerBlock( this, data );
  }

  public static String getDefaultCommentText() {
    return SplicerImplList.getDefaultCommentText();
  }
}
