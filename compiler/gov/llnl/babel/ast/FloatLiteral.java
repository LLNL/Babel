//
// File:        FloatLiteral.java
// Package:     gov.llnl.babel.ast
// Copyright:   (c) 2006 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision: 6171 $
// Date:        $Date: 2007-10-08 16:39:28 -0700 (Mon, 08 Oct 2007) $
// Description: Hold a double literal
// 
package gov.llnl.babel.ast;
import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.visitor.Visitor;

public class FloatLiteral extends NumericLiteral {
  private float d_value = 0;

  private void parseValue(ParseTreeNode src) {
    try {
      String parseString;
      if (src.name.endsWith("f")) {
        parseString = src.name.substring(0,src.name.length()-1);
      }
      else {
        parseString = src.name;
      }
      d_value = Float.parseFloat(parseString);
    }
    catch (NumberFormatException nfe) {
      nfe.printStackTrace();
    }
  }

  public FloatLiteral(ParseTreeNode src, ASTNode parent) {
    super(src,parent);
    parseValue(src);
  }

  public FloatLiteral() {
    super(null, null);
  }

  public float getFloat() {
    return d_value;
  }

  public void setFloat(float value) {
    d_value = value;
  }

  public String toString()
  {
    ParseTreeNode src = getParseTreeNode();
    if (src != null) {
      return src.name;
    }
    else {
      return Float.toString(d_value) + "f";
    }
  }

  public Object accept(Visitor v, Object data)
  {
    return v.visitFloatLiteral(this, data);
  }
}
