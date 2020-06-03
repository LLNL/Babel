package gov.llnl.babel.ast;

import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.visitor.Visitor;

/**
 * A special case of <code>Array</code> where user deals with raw memory.
 */
public class RArrayType extends ArrayType {
  
  protected Extents d_extents;
  
  public RArrayType(ParseTreeNode src, ASTNode parent) {
    super(src, parent);
    d_rcm='c';
  }
  
  public String getTypeName() { 
    return "raw-array";
  }
  
  public Extents getExtents() { 
    return d_extents;
  }
  
  public void setExtents( Extents ext ) { 
    d_extents = ext;
  }
  
  public String toString() { 
    StringBuffer sb = new StringBuffer("rarray<" + d_scalarType.toString());
    if ( d_dimension != 1 ) { 
      sb.append("," + d_dimension);
    }
    sb.append(">");
    
    return sb.toString();
  }
  
  public Object accept(Visitor v, Object data) {
    return v.visitRArrayType( this, data );
  }
}
