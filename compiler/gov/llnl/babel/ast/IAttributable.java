package gov.llnl.babel.ast;

/**
 * Interface for any ASTNode that may contain a list of Attributes
 */
public interface IAttributable {

  public AttributeList getAttributeList();
  
  public void setAttributeList( AttributeList attrib );

}
