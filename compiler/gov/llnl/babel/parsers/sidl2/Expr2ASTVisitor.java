//
// File:        Expr2ASTVisitor.java
// Package:     gov.llnl.babel.parsers.sidl2
// Copyright:   (c) 2006 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision$
// Date:        $Date$
// Description: Convert an expression parse tree to an expression ASTNode
// 

package gov.llnl.babel.parsers.sidl2;

import gov.llnl.babel.ast.ASTNode;
import gov.llnl.babel.ast.Assertion;
import gov.llnl.babel.ast.BinaryExpr;
import gov.llnl.babel.ast.BooleanLiteral;
import gov.llnl.babel.ast.CharacterLiteral;
import gov.llnl.babel.ast.DComplexLiteral;
import gov.llnl.babel.ast.DoubleLiteral;
import gov.llnl.babel.ast.FComplexLiteral;
import gov.llnl.babel.ast.FloatLiteral;
import gov.llnl.babel.ast.FuncExpr;
import gov.llnl.babel.ast.IntLiteral;
import gov.llnl.babel.ast.Name;
import gov.llnl.babel.ast.StringLiteral;
import gov.llnl.babel.ast.UnaryExpr;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Expr2ASTVisitor implements SIDLParserVisitor {
  private static HashMap s_stringToBinary;
  private static HashMap s_stringToUnary;
  private static Pattern s_intPattern = Pattern.compile("[+-]?\\d+[lL]?");
  private static String  s_basicFloat = 
    "[-+]?(\\d+\\.?\\d*|\\.\\d+)([eE][-+]?\\d+)?";
  private static Pattern s_floatPattern;
  private static Pattern s_doublePattern;
  
  static {
    s_stringToBinary = new HashMap();
    s_stringToBinary.put("+", new Integer(BinaryExpr.PLUS));
    s_stringToBinary.put("-", new Integer(BinaryExpr.MINUS));
    s_stringToBinary.put("*", new Integer(BinaryExpr.MULTIPLY));
    s_stringToBinary.put("/", new Integer(BinaryExpr.DIVIDE));
    s_stringToBinary.put("**", new Integer(BinaryExpr.POWER));
    s_stringToBinary.put("<", new Integer(BinaryExpr.LESSTHAN));
    s_stringToBinary.put("<=", new Integer(BinaryExpr.LESSTHANEQ));
    s_stringToBinary.put(">", new Integer(BinaryExpr.GREATERTHAN));
    s_stringToBinary.put(">=", new Integer(BinaryExpr.GREATERTHANEQ));
    s_stringToBinary.put("==", new Integer(BinaryExpr.EQUAL));
    s_stringToBinary.put("and", new Integer(BinaryExpr.LOGICAL_AND));
    s_stringToBinary.put("or", new Integer(BinaryExpr.LOGICAL_OR));
    s_stringToBinary.put("xor", new Integer(BinaryExpr.LOGICAL_XOR));
    s_stringToBinary.put("&", new Integer(BinaryExpr.BITWISE_AND));
    s_stringToBinary.put("|", new Integer(BinaryExpr.BITWISE_OR));
    s_stringToBinary.put("^", new Integer(BinaryExpr.BITWISE_XOR));
    s_stringToBinary.put("<<<", new Integer(BinaryExpr.SHIFT_LEFT));
    s_stringToBinary.put(">>>", new Integer(BinaryExpr.SHIFT_RIGHT));
    s_stringToBinary.put("iff", new Integer(BinaryExpr.IFF));
    s_stringToBinary.put("implies", new Integer(BinaryExpr.IMPLIES));
    s_stringToBinary.put("%", new Integer(BinaryExpr.MODULUS));
    s_stringToBinary.put("rem", new Integer(BinaryExpr.REMAINDER));
    s_stringToBinary.put("!=", new Integer(BinaryExpr.NOT_EQUAL));

    s_stringToUnary = new HashMap();
    s_stringToUnary.put("+", new Integer(UnaryExpr.PLUS));
    s_stringToUnary.put("-", new Integer(UnaryExpr.MINUS));
    s_stringToUnary.put("~", new Integer(UnaryExpr.COMPLEMENT));
    s_stringToUnary.put("is", new Integer(UnaryExpr.IS));
    s_stringToUnary.put("not", new Integer(UnaryExpr.NOT));

    s_floatPattern = Pattern.compile(s_basicFloat + "[fF]");
    s_doublePattern = Pattern.compile(s_basicFloat + "[dD]?");  
  }

  private static BinaryExpr makeBinary(String op, ParseTreeNode node,
                                       ASTNode lhs, ASTNode rhs)
  {
    BinaryExpr result = new BinaryExpr(node, null);
    result.setLHS(lhs);
    result.setRHS(rhs);
    result.setOperator(((Integer)s_stringToBinary.get(op)).intValue());
    return result;
  }

  private static double getDouble(ASTNode n)
  {
    if (n instanceof DoubleLiteral) {
      return ((DoubleLiteral)n).getDouble();
    }
    else if (n instanceof FloatLiteral) {
      return ((FloatLiteral)n).getFloat();
    }
    else if (n instanceof IntLiteral) {
      return ((IntLiteral)n).getInt();
    }
    return Double.NaN;
  }

  private static float getFloat(ASTNode n)
  {
    if (n instanceof FloatLiteral) {
      return ((FloatLiteral)n).getFloat();
    }
    else if (n instanceof IntLiteral) {
      return ((IntLiteral)n).getInt();
    }
    return Float.NaN;
  }

  private Object complexType(ParseTreeNode node,
                             ASTNode real,
                             ASTNode imag,
                             ASTNode parent)
  {
    if ((real instanceof DoubleLiteral) ||
        (imag instanceof DoubleLiteral)) {
      DComplexLiteral lit = new DComplexLiteral(node, parent);
      lit.setComplex(getDouble(real),getDouble(imag));
      return lit;
    }
    else {
      FComplexLiteral lit = new FComplexLiteral(node, parent);
      lit.setComplex(getFloat(real), getFloat(imag));
      return lit;
    }
  }

  private static boolean isInt(String str)
  {
    Matcher m = s_intPattern.matcher(str);
    return m.matches();
  }

  private static boolean isFloat(String str)
  {
    Matcher m = s_floatPattern.matcher(str);
    return m.matches();
  }

  private static boolean isDouble(String str)
  {
    Matcher m = s_doublePattern.matcher(str);
    return m.matches();
  }

  private Object pickNumberType(ParseTreeNode node,
                                ASTNode parent)
  {
    final String numStr = node.name;
    if (isInt(numStr)) {
      return new IntLiteral(node, parent);
    }
    else if (isFloat(numStr)) {
      return new FloatLiteral(node, parent);
    }
    else if (isDouble(numStr)) {
      return new DoubleLiteral(node, parent);
    }
    else {
      return null;
    }
  }
  
  public Object visit(ParseTreeNode node, Object parent)
  {
    switch(node.getID()) {
      // literals and terminals
    case SIDLParserTreeConstants.JJTCOMPLEX:
      if (node.jjtGetNumChildren() == 2) {
        ASTNode real = (ASTNode)node.jjtGetChild(0).
          jjtAccept(this, (ASTNode)parent);
        ASTNode imag = (ASTNode)node.jjtGetChild(1).
          jjtAccept(this, (ASTNode)parent);
        return complexType(node, real, imag, (ASTNode)parent);
      }
      break;
    case SIDLParserTreeConstants.JJTNUMBER:
      return pickNumberType(node, (ASTNode)parent);
    case SIDLParserTreeConstants.JJTFUNCARGS:
      {
        final int numChildren = node.jjtGetNumChildren();
        ArrayList args = new ArrayList(numChildren);
        for(int i = 0; i < numChildren; ++i) {
          args.add(node.jjtGetChild(i).jjtAccept(this, (ASTNode)parent));
        }
        return args;
      }
    case SIDLParserTreeConstants.JJTFUNC:
      {
        FuncExpr fe = new FuncExpr(node, (ASTNode)parent);
        fe.setName(node.name);
        if (node.jjtGetNumChildren() > 0) {
           fe.setArguments((List)node.jjtGetChild(0).jjtAccept(this, fe));
        }
        return fe;
      }
    case SIDLParserTreeConstants.JJTLITERAL:
      if (node.jjtGetNumChildren() == 1) {
        return node.jjtGetChild(0).jjtAccept(this, (ASTNode)parent);
      } else if ("true".equals(node.name) || "false".equals(node.name)) {
        return new BooleanLiteral(node, (ASTNode)parent);
      } else if ("null".equals(node.name) || "pure".equals(node.name) ||
                 "result".equals(node.name)) {
        return new Name(node, (ASTNode)parent);
      } else if (node.name.length() > 1) {
        if (node.name.charAt(0) == '\'') {
          return new CharacterLiteral(node, (ASTNode)parent);
        } else if (node.name.charAt(0) == '"') {
          return new StringLiteral(node, (ASTNode)parent);
        }
      }
      break;
    case SIDLParserTreeConstants.JJTNAME:
      return new Name(node, (ASTNode)parent);
    case SIDLParserTreeConstants.JJTINTEGER:
      return new IntLiteral(node, (ASTNode)parent);

      // binary expressions
    case SIDLParserTreeConstants.JJTPOWER:  // this is right associative
      {
        BinaryExpr rhs = null;
        final int numOps = node.jjtGetNumChildren() - 1;
        if (numOps > 0) {
          StringTokenizer tok = new StringTokenizer(node.name, "\\");
          rhs = makeBinary(tok.nextToken(), node,
                           (ASTNode)
                           (node.jjtGetChild(numOps-1).jjtAccept(this, null)),
                           (ASTNode)
                           (node.jjtGetChild(numOps).jjtAccept(this, null)));
          for(int i = numOps - 2; i >= 0; --i) {
            rhs = makeBinary(tok.nextToken(), node, (ASTNode)
                             (node.jjtGetChild(i).jjtAccept(this, null)), rhs);
            
          }
          rhs.setParent((ASTNode)parent);
        }
        return rhs;
      }
    case SIDLParserTreeConstants.JJTAND:
    case SIDLParserTreeConstants.JJTADD:
    case SIDLParserTreeConstants.JJTMULT:
    case SIDLParserTreeConstants.JJTBITWISE:
    case SIDLParserTreeConstants.JJTSHIFT:
    case SIDLParserTreeConstants.JJTGTLT:
    case SIDLParserTreeConstants.JJTOR:
    case SIDLParserTreeConstants.JJTEQUALITY:
    case SIDLParserTreeConstants.JJTIMPLICATION:
      {
        BinaryExpr lhs = null;
        final int numOps = node.jjtGetNumChildren() - 1;
        if (numOps > 0) {
          StringTokenizer tok = new StringTokenizer(node.name, "\\");
          lhs = makeBinary(tok.nextToken(), node,
                           (ASTNode)
                           (node.jjtGetChild(0).jjtAccept(this, null)),
                           (ASTNode)
                       (node.jjtGetChild(1).jjtAccept(this, null)));
          for(int i = 2; i <= numOps; ++i) {
            lhs = makeBinary(tok.nextToken(), node, lhs, (ASTNode)
                             (node.jjtGetChild(i).jjtAccept(this, null)));
            
          }
          lhs.setParent((ASTNode)parent);
        }
        return lhs;
      }

      case SIDLParserTreeConstants.JJTUNARY:
        {
          UnaryExpr ue = new UnaryExpr(node, (ASTNode)parent);
          ue.setOperator(((Integer)s_stringToUnary.get(node.name)).intValue());
          ue.setOperand((ASTNode)node.jjtGetChild(0).jjtAccept(this, ue));
          return ue;
        }

      // miscellaneous
    case SIDLParserTreeConstants.JJTEXTENT:
      return node.jjtGetChild(0).jjtAccept(this, parent);
    case SIDLParserTreeConstants.JJTASSERTION:
      {
        Assertion a = new Assertion(node, (ASTNode)parent);
        if (2 == node.jjtGetNumChildren()) {
          a.setName((Name)node.jjtGetChild(0).jjtAccept(this, a));
          a.setExpr((ASTNode)node.jjtGetChild(1).jjtAccept(this, a));
        }
        else {
          a.setExpr((ASTNode)node.jjtGetChild(0).jjtAccept(this, a));
        }
        return a;
      }
    default:
      System.out.println("Expr2ASTVisitor doesn't handle " + node +
                         ".");
      break;
    }
    return null;
  }
}
