package gov.llnl.babel.ast;

import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.visitor.Visitor;

public class SplicerImpl extends ASTNode {
  public static final String s_default_text = 
                               "Insert suitable implementation details here...";

  private boolean d_default = false;
  private String  d_impl    = null;

  public SplicerImpl(ParseTreeNode src) {
    super(src, null);
    d_default = true;
  }

  public SplicerImpl(ParseTreeNode src, ASTNode parent) {
    super(src, parent);
    d_default = true;
  }

  public SplicerImpl(String impl) {
    if (impl.equals(s_default_text)) {
      d_default = true;
    } else {
      d_default = false;
      d_impl    = impl;
    }
  }

  public SplicerImpl() {
    d_default = true;
  }

  public String getImpl() {
    return d_impl;
  }

  public void setImpl(String impl) {
    if (impl.equals(s_default_text)) {
      d_default = true;
    } else {
      d_default = false;
      d_impl = impl;
    }
  }

  public boolean isDefault() {
    return d_default;
  }

  public String toString() {
    return d_default ? s_default_text : d_impl;
  }

  public Object accept(Visitor v, Object data) {
    return v.visitSplicerImpl(this, data);
  }

}
