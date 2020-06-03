//
// File:        FComplexLiteral.java
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

public class FComplexLiteral extends NumericLiteral {
  private float d_real = 0;
  private float d_imag = 0;


  public FComplexLiteral(ParseTreeNode src, ASTNode parent) {
    super(src,parent);
  }

  public FComplexLiteral() {
    super(null, null);
  }

  public float getReal() {
    return d_real;
  }

  public FloatLiteral getRealLiteral() {
    ParseTreeNode src = getParseTreeNode();
    if (src != null) {
      return new FloatLiteral((ParseTreeNode)src.jjtGetChild(0), null);
    }
    else {
      FloatLiteral lit = new FloatLiteral();
      lit.setFloat(d_real);
      return lit;
    }
  }
  
  public float getImag() {
    return d_imag;
  }

  public FloatLiteral getImagLiteral() {
    ParseTreeNode src = getParseTreeNode();
    if (src != null) {
      return new FloatLiteral((ParseTreeNode)src.jjtGetChild(1), null);
    }
    else {
      FloatLiteral lit = new FloatLiteral();
      lit.setFloat(d_imag);
      return lit;
    }
  }
  
  public void setComplex(float real, float imag) {
    d_real = real;
    d_imag = imag;
  }

  public String toString()
  {
    return "{ " + Float.toString(d_real) + "f, " 
      + Float.toString(d_imag) + "f }";
  }

  public Object accept(Visitor v, Object data)
  {
    return v.visitFComplexLiteral(this, data);
  }
}
