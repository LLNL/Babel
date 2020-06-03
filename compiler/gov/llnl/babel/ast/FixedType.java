package gov.llnl.babel.ast;

import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.visitor.Visitor;

/**
 * Any built-in type (char, int, long, float, double, fcomplex, dcomplex, string, opaque). 
 */
public class FixedType extends Type {

  String d_type;
  
  public final static String allowableTypes[] = {"bool", "char", "int", "long", 
                                                 "float", "double", 
                                                 "fcomplex", "dcomplex", 
                                                 "string", "opaque" };
  
  public FixedType(ParseTreeNode src, ASTNode parent, String type) {
    super(src, parent);
    setType(type);
  }

  public String getTypeName() { 
    return d_type;
  }
  
  public String toString() { 
    return d_type;
  }
  
  public boolean setType(String type) { 
    for( int i = 0; i<allowableTypes.length; i++) { 
        if (type.equals(allowableTypes[i])) { 
          d_type = type;
          return true;
        }
    }
    return false;
  }
  
  public Object accept(Visitor v, Object data) {
    return v.visitFixedType( this, data );
  }
  
}
