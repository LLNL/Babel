//
// File:        CharacterLiteral.java
// Package:     gov.llnl.babel.ast
// Copyright:   (c) 2006 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision: 6171 $
// Date:        $Date: 2007-10-08 16:39:28 -0700 (Mon, 08 Oct 2007) $
// Description: Hold a character literal
// 

package gov.llnl.babel.ast;
import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.visitor.Visitor;

public class CharacterLiteral extends Literal {
  private char d_value = '\0';

  private void parseValue(String image) {
    if (image.length() == 1) {
      d_value = image.charAt(0);
    }
    else if (image.length() == 2) {
      switch(image.charAt(1)) {
      case 'r':   d_value = '\r'; break;
      case 'n':   d_value = '\n'; break;
      case 't':   d_value = '\t'; break; 
      case 'b':   d_value = '\b'; break;
      case 'f':   d_value = '\f'; break;
      case '\\':  d_value = '\\'; break;
      case '0':   d_value = '\0'; break;
      case '\'':  d_value = '\''; break;
      case '"':   d_value = '"'; break;
      }
    }
    else  if (image.length() > 2) {
      try {
        d_value = (char)Integer.parseInt(image.substring(1), 8);
      }
      catch (NumberFormatException nfe) {
        nfe.printStackTrace();
      }
    }
  }

  public CharacterLiteral(ParseTreeNode src, ASTNode parent) {
    super(src, parent);
    String image = src.name.substring(1, src.name.length() - 1);
    parseValue(image);
  }

  public CharacterLiteral() {
    super(null, null);
  }

  public char getChar() { return d_value; }

  public void setChar(char value) { d_value = value; }

  /**
   * Implement the "Visitor" pattern.
   */
  public Object accept(Visitor v, Object data)
  {
    return v.visitCharacterLiteral(this, data);
  }
  
}
