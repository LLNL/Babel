//
// File:        DoubleLiteral.java
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

public class DoubleLiteral extends NumericLiteral {
  private double d_value = 0;

  private void parseValue(ParseTreeNode src) {
    try {
      d_value = Double.parseDouble(src.name);
    }
    catch (NumberFormatException nfe) {
      nfe.printStackTrace();
    }
  }

  public DoubleLiteral(ParseTreeNode src, ASTNode parent) {
    super(src,parent);
    parseValue(src);
  }

  public DoubleLiteral() {
    super(null, null);
  }

  public double getDouble() {
    return d_value;
  }

  public void setDouble(double value) {
    d_value = value;
  }

  public String toString()
  {
    ParseTreeNode src = getParseTreeNode();
    if (src != null) {
      return src.name;
    }
    else {
      return Double.toString(d_value);
    }
  }

  public Object accept(Visitor v, Object data)
  {
    return v.visitDoubleLiteral(this, data);
  }
}
