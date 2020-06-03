package gov.llnl.babel.ast;

import gov.llnl.babel.parsers.sidl2.ParseTreeNode;

/**
 * Base class for <code>RequireClause</code> and <code>ImportClase</code>.
 */
public abstract class TypeSearchClause extends ASTNode {

  protected ScopedID d_id;

  protected Version d_version;

  public TypeSearchClause(ParseTreeNode src, ASTNode parent, ScopedID id, Version version) {
    super(src, parent);
    d_id = id;
    d_version = version;
  }

  public Version getVersion() {
    return d_version;
  }

  public void setVersion(Version version) {
    d_version = version;
  }

  public ScopedID getScopedID() {
    return d_id;
  }

  public void setScopedID(ScopedID id) {
    d_id = id;
  }
}
