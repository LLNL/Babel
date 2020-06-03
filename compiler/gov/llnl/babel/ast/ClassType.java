package gov.llnl.babel.ast;

import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.visitor.Visitor;

/**
 * Fundamental OO type in SIDL.
 */
public class ClassType extends Extendable {

  /** class that this class extends */
  protected ScopedID d_extends = null;

  /** List of interfaces */
  protected ImplementsList d_implements = null;

  /** List of splicer blocks */
  protected SplicerList d_splicers = null;

  /** List of invariants */
  protected Invariants d_invariants = null;

  public ClassType(ParseTreeNode src, ASTNode parent, Name name) {
    super(src, parent, name);
  }

  public ClassType()
  {
    super(null, null, null);
  }

  public String getTypeName() { 
    return "class";
  }
  
  public ImplementsList getImplementsList() {
    return d_implements;
  }

  public void setImplementsList(ImplementsList list) {
    d_implements = list;
  }

  public void setExtends(ScopedID extendsCls) {
    d_extends = extendsCls;
  }

  public ScopedID getExtends() {
    return d_extends;
  }

  public void setInvariants(Invariants invariants) {
    d_invariants = invariants;
  }

  public Invariants getInvariants() {
    return d_invariants;
  }

  public void addSplicerBlock(SplicerBlock block) {
    if (d_splicers == null) {
      d_splicers = new SplicerList(null, this);
    }
    d_splicers.addSplicerBlock(block);
  }

  public SplicerList getSplicerList() {
    return d_splicers;
  }

  public Object accept(Visitor v, Object data) {
    return v.visitClassType(this, data);
  }

}
