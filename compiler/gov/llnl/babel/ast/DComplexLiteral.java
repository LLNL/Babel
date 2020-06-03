//
// File:        DComplexLiteral.java
// Package:     gov.llnl.babel.ast
// Copyright:   (c) 2006 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision: 6171 $
// Date:        $Date: 2007-10-08 16:39:28 -0700 (Mon, 08 Oct 2007) $
// Description: Hold a dcomplex literal
// 
package gov.llnl.babel.ast;
import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.visitor.Visitor;

public class DComplexLiteral extends NumericLiteral {
  double d_real = 0;
  double d_imag = 0;

  public DComplexLiteral(ParseTreeNode src, ASTNode parent) {
    super(src,parent);
  }

  public DComplexLiteral() {
    super(null, null);
  }

  public double getReal() {
    return d_real;
  }

  public DoubleLiteral getRealLiteral() {
    ParseTreeNode src = getParseTreeNode();
    if (src != null) {
      return new DoubleLiteral((ParseTreeNode)src.jjtGetChild(0), null);
    }
    else {
      DoubleLiteral lit = new DoubleLiteral();
      lit.setDouble(d_real);
      return lit;
    }
  }

  public double getImag() {
    return d_imag;
  }

  public DoubleLiteral getImagLiteral() {
    ParseTreeNode src = getParseTreeNode();
    if (src != null) {
      return new DoubleLiteral((ParseTreeNode)src.jjtGetChild(1), null);
    }
    else {
      DoubleLiteral lit = new DoubleLiteral();
      lit.setDouble(d_imag);
      return lit;
    }
  }

  public void setComplex(double real, double imag) {
    d_real = real;
    d_imag = imag;
  }

  public String toString()
  {
    return "{ " + Double.toString(d_real) + ", " 
      + Double.toString(d_imag) + "}";
  }

  public Object accept(Visitor v, Object data)
  {
    return v.visitDComplexLiteral(this, data);
  }
}
