package gov.llnl.babel.ast;

import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.visitor.Visitor;

/**
 * Array of any <code>FixedType</code>, <code>Extendable</code>, or
 * <code>Enumeration</code>.
 */
public class ArrayType extends Type {

  //If scalar type is null, this is a generic array
  Type d_scalarType;
  
  int d_dimension;
  
  char d_rcm; // row/column major 'r' = row, 'c' = column, '?'=don't care
  
  public ArrayType(ParseTreeNode node, ASTNode parent) {
    super(node, parent);
    d_dimension=1;
    d_rcm='?';
  }

  public String getTypeName() { 
    return "array";
  }
  
  public Object accept(Visitor v, Object data) {
    return v.visitArrayType( this, data );
  }

  public void setScalarType( Type t ) { 
    d_scalarType = t;
  }
  
  public Type getScalarType() { 
    return d_scalarType;
  }
  
  public void setDimension( int dim ) { 
    d_dimension = dim;
  }
  
  public int getDimension() { 
     return d_dimension;
  }
  
  public boolean setOrientation(char c) { 
    if (c=='r' || c=='c' || c=='?') { 
      d_rcm = c;
      return true;
    }
    return false;
  }
  
  public boolean isRowMajor() { 
    return d_rcm == 'r';
  }
  
  public boolean isColMajor() { 
    return d_rcm == 'c';
  }
  
  public boolean isPacked() { 
    return d_rcm != '?';
  }
  
  public String toString() { 
	  
    StringBuffer sb = new StringBuffer("array<");
    if(d_scalarType != null) {
    	sb.append(d_scalarType.toString());
    }
    if ( d_dimension != 1 || d_rcm != '?') { 
      sb.append("," + d_dimension);
    }
    if ( d_rcm != '?') { 
      sb.append(","  + (d_rcm=='r' ? "row-major" : "column-major") );
    }
    sb.append(">");
    return sb.toString();
  }
  
}
