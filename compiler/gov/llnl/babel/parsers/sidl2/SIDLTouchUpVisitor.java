//
// File:        SIDLTouchUpVisitor.java
// Package:     
// Copyright:   (c) 2005 The Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:     @(#) $Id: SIDLTouchUpVisitor.java 7037 2011-01-07 21:46:04Z tdahlgren $
// Description: 

package gov.llnl.babel.parsers.sidl2;

import java.io.PrintStream;

/**
 * 
 * This class does some final touch up on the parse tree after generation.
 * Its main job is to ensure the appropriate ParseTreeNode instances have 
 * their name and doc fields set.
 *
 */
public class SIDLTouchUpVisitor implements SIDLParserVisitor { 
  
  protected PrintStream out;

  public SIDLTouchUpVisitor ( PrintStream o ) { 
    out = o;
  }

  /**
   * Resolve the string-name of the ParseTreeNode instance
   * 
   * @param node the node whos name needs to be determined
   * @param listall flag signifying special case where name may be spread across tokens
   * @return string name to set node.name to 
   */
  protected String resolveName( ParseTreeNode node, boolean listall ) { 
    if ( node.getFirstToken() == null ) { 
      return "";
    } else if ( !node.name.equals("") ) { 
      return node.name ;
    } else if ( node.getFirstToken() == node.getLastToken() ) {
      return node.getFirstToken().image;
    } else if (listall == true ) { 
      StringBuffer buf = new StringBuffer();
      Token head = new Token();
      head.next = node.getFirstToken(); 
      Token end = node.getLastToken();
      while ( head != end ) { 
        head=head.next;
        if ( head==null || head.image == null ) { break; }
        buf.append( head.image );
      }
      return buf.toString();
    } else { 
      return "";
    }
  }
    
  /**
   * Resolve the doc string from the special tokens preceding the node
   * 
   * @param node
   * @return
   */
  protected String resolveDoc( ParseTreeNode node ) { 
    Token t = node.getFirstToken();
    if ( t != null ) { 
      Token tt = t.specialToken;
      if ( tt != null ) { 
        while( tt.specialToken != null ) { 
          tt = tt.specialToken;
          if ( tt.kind == SIDLParserConstants.DOC_COMMENT ) {
            //Now parse out any "/*" "*/" or "*"
            String ret = tt.image.replaceAll("/[*]", "");
            String tmp = ret.replaceAll("[*]/", "");
            ret = tmp.replaceAll("[*]","");
            tmp = ret.replaceAll("\n[\t ]+","\n");
            ret = tmp.replaceAll("^[\t ]*$","");
            tmp = ret.replaceAll("^\n","");
            ret = tmp.replaceAll("^[\t ]*$","");

            return ret;
          }
        }
      }
    }
    return "";
  }

  /**
   * Split the method name and possible method extension off from the
   * end of a ScopedID node. Convert the method name part into a 
   * method name node.
   */
  private void splitOffMethodName(ParseTreeNode fromClause) {
    if ((fromClause.getID() == SIDLParserTreeConstants.JJTFROMCLAUSE)&&
        (fromClause.jjtGetNumChildren() == 1))
    {
      ParseTreeNode scopedID = (ParseTreeNode)fromClause.jjtGetChild(0);
      final Token lastToken = scopedID.getLastToken();
      Token ultimate = scopedID.getFirstToken();
      Token penultimate = null;
      Token antepenultimate = null;
      if ((ultimate != null) && (ultimate != lastToken)) {
        while (ultimate.next != lastToken) {
          antepenultimate = penultimate;
          penultimate = ultimate;
          ultimate = ultimate.next;
        }
        if ((penultimate != null) &&
            (lastToken.kind == SIDLParserConstants.IDENTIFIER)) {
          ParseTreeNode methodName = new 
            ParseTreeNode(SIDLParserTreeConstants.JJTMETHODNAME);
          ParseTreeNode shortName = new
            ParseTreeNode(SIDLParserTreeConstants.JJTSHORTNAME);
          scopedID.setLastToken(penultimate);
          methodName.setFirstToken(lastToken);
          shortName.setFirstToken(lastToken);
          methodName.setLastToken(lastToken);
          shortName.setLastToken(lastToken);
          methodName.jjtSetParent(fromClause);
          shortName.jjtSetParent(methodName);
          fromClause.jjtAddChild(methodName, 1);
          methodName.jjtAddChild(shortName, 0);
        }
        else if ((antepenultimate != null) && 
                 (lastToken.kind == SIDLParserConstants.EXTENSION)) {
          ParseTreeNode methodName = new 
            ParseTreeNode(SIDLParserTreeConstants.JJTMETHODNAME);
          ParseTreeNode shortName = new
            ParseTreeNode(SIDLParserTreeConstants.JJTSHORTNAME);
          ParseTreeNode extension = new
            ParseTreeNode(SIDLParserTreeConstants.JJTEXTENSION);
          scopedID.setLastToken(antepenultimate);
          methodName.setFirstToken(ultimate);
          shortName.setFirstToken(ultimate);
          extension.setFirstToken(lastToken);
          methodName.setLastToken(lastToken);
          shortName.setLastToken(ultimate);
          extension.setLastToken(lastToken);
          methodName.jjtSetParent(fromClause);
          shortName.jjtSetParent(methodName);
          extension.jjtSetParent(methodName);
          fromClause.jjtAddChild(methodName, 1);
          methodName.jjtAddChild(shortName, 0);
          methodName.jjtAddChild(extension, 1);
        }
      }
    }
  }

  public Object visit(ParseTreeNode node, Object data) {
    switch( node.getID() ) { 
    case SIDLParserTreeConstants.JJTMULT:
    case SIDLParserTreeConstants.JJTADD:
    case SIDLParserTreeConstants.JJTARRAY:
    case SIDLParserTreeConstants.JJTRARRAY:
    case SIDLParserTreeConstants.JJTARGATTRS:
    case SIDLParserTreeConstants.JJTMETHODATTRS:
    case SIDLParserTreeConstants.JJTRETURNTYPE:
    case SIDLParserTreeConstants.JJTSCALARTYPE:
    case SIDLParserTreeConstants.JJTASSERTION:
    case SIDLParserTreeConstants.JJTENSURES:
    case SIDLParserTreeConstants.JJTREQUIRES:
    case SIDLParserTreeConstants.JJTINVARIANTS:
    case SIDLParserTreeConstants.JJTSHIFT:
    case SIDLParserTreeConstants.JJTGTLT:
    case SIDLParserTreeConstants.JJTAND:
    case SIDLParserTreeConstants.JJTOR:
    case SIDLParserTreeConstants.JJTEQUALITY:
    case SIDLParserTreeConstants.JJTCOMPLEX:
    case SIDLParserTreeConstants.JJTLITERAL:
    case SIDLParserTreeConstants.JJTFUNCARGS:
    case SIDLParserTreeConstants.JJTUNARY:
    case SIDLParserTreeConstants.JJTFUNC:
    case SIDLParserTreeConstants.JJTPOWER:
    case SIDLParserTreeConstants.JJTIMPLICATION:
    case SIDLParserTreeConstants.JJTTYPEATTRS:
      // no touch-up needed in these cases
      break;
    		
    case SIDLParserTreeConstants.JJTSTART:
    case SIDLParserTreeConstants.JJTSHORTNAME:
    case SIDLParserTreeConstants.JJTPRIMATIVETYPE:
    case SIDLParserTreeConstants.JJTARGLIST:
    case SIDLParserTreeConstants.JJTEXCEPTCLAUSE:
    case SIDLParserTreeConstants.JJTEXTENDSONE:
    case SIDLParserTreeConstants.JJTEXTENDSLIST:
    case SIDLParserTreeConstants.JJTIMPLEMENTSLIST:
    case SIDLParserTreeConstants.JJTENUMERATOR:
    case SIDLParserTreeConstants.JJTIMPLEMENTSALLLIST:
    case SIDLParserTreeConstants.JJTSTRUCTITEM:
    case SIDLParserTreeConstants.JJTIMPORT:
    case SIDLParserTreeConstants.JJTREQUIRE:
    case SIDLParserTreeConstants.JJTVERSION:
      // Simple name contained in main token
      node.name = resolveName( node, false );
      break;


    case SIDLParserTreeConstants.JJTFROMCLAUSE:
      node.name = resolveName( node, false );
      splitOffMethodName(node);
      break;
        
    case SIDLParserTreeConstants.JJTEXTENSION:
      // Simple name contained in main token (sans braces)
      String tmp = resolveName( node, false );
      node.name = tmp.replaceAll("\\[|\\]","");
      break;
        
    case SIDLParserTreeConstants.JJTNAME:
    case SIDLParserTreeConstants.JJTMETHODNAME:
    case SIDLParserTreeConstants.JJTMODE:
    case SIDLParserTreeConstants.JJTARGATTR:
    case SIDLParserTreeConstants.JJTCUSTOMATTR:
    case SIDLParserTreeConstants.JJTMETHODATTR:
    case SIDLParserTreeConstants.JJTDIMENSION:
    case SIDLParserTreeConstants.JJTORIENTATION:
    case SIDLParserTreeConstants.JJTSCOPEDID:
    case SIDLParserTreeConstants.JJTTYPEATTR:
    case SIDLParserTreeConstants.JJTINTEGER:
    case SIDLParserTreeConstants.JJTNUMBER:
    case SIDLParserTreeConstants.JJTEXTENTS:
    case SIDLParserTreeConstants.JJTEXTENT:
      // extended name may span multiple tokens
      node.name = resolveName( node, true );
      break;
	    
    case SIDLParserTreeConstants.JJTARG:
    case SIDLParserTreeConstants.JJTMETHOD:
      // simple name contained in main token
      // may include a doc-comment
      node.name = resolveName( node, false );
      node.doc = resolveDoc( node );
      break;

    case SIDLParserTreeConstants.JJTUSERTYPE:
      String s =  resolveDoc( node ); // pass doc down to child
      if ( s != null && s.length() > 0 ) { 
        data = s;
      }
      break;
        
    case SIDLParserTreeConstants.JJTCLASS:
    case SIDLParserTreeConstants.JJTINTERFACE:
    case SIDLParserTreeConstants.JJTENUM:
    case SIDLParserTreeConstants.JJTSTRUCT:
    case SIDLParserTreeConstants.JJTPACKAGE:
      if ( data instanceof String ) { 
        node.doc = (String) data;
        data = null;
      }
      node.name = resolveName( node, false );   
      break; 
        
    default:
      out.println( node + ": acceptor unimplemented in subclass?");
    } 
    return node.childrenAccept( this, data );
  }
}
