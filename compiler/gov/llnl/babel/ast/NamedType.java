package gov.llnl.babel.ast;

import gov.llnl.babel.parsers.sidl2.ParseTreeNode;

/**
 * Base class for user defined types such as <code>Class</code>,
 * <code>Interface</code>, <code>Enumeration</code> and
 * <code>Package</code>.
 */
public abstract class NamedType extends Type implements INameable {

  protected Name d_name = null;

  protected DocComment d_comment = null;

  protected Version d_version = null;

  private boolean d_user_selected = true;
  
  public NamedType()
  {
  }

  public NamedType(ParseTreeNode src, ASTNode parent, Name name) {
    super(src, parent);
    d_name = name;
    if(src != null) {
      setDocComment(src.doc);
    }
  }

  public Name getName() {
    return d_name;
  }

  public void setName(Name name) {
    d_name = name;
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

  /** get Fully Qualified Name by traversing parent hierarchy */
  public String getFQN() {
    ASTNode parent = getParent();
    if (parent != null) {
      if (parent instanceof NamedType) {
        return ((NamedType) parent).getFQN() + "." + d_name.toString();
      } else if (parent instanceof SIDLFile) {
        return "." + d_name.toString();
      }
    }
    return d_name.toString();
  }

  public Version getVersion() {
    if (d_version == null) {
      ASTNode parent = getParent();
      if (parent != null) {
        if (parent instanceof NamedType) {
          return ((NamedType) parent).getVersion();
        }
      }
    }
    return d_version;
  }

  public void setVersion(Version version) {
    d_version = version;
  }

  public boolean getUserSelected() {
    return d_user_selected;
  }

  public void setUserSelected(boolean userSelected) {
    d_user_selected = userSelected;
  }
}
