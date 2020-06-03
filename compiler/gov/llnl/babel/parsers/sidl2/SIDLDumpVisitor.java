  //
  // File:        SIDLDumpVisitor.java
  // Package:     
  // Copyright:   (c) 2005 The Lawrence Livermore National Security, LLC
  // Release:     $Name$
  // Revision:     @(#) $Id: SIDLDumpVisitor.java 6171 2007-10-08 23:39:28Z epperly $
  // Description: 

package gov.llnl.babel.parsers.sidl2;

import java.io.PrintStream;

public class SIDLDumpVisitor implements SIDLParserVisitor { 
  
    protected PrintStream out;

    private int indent = 0;
    
    public SIDLDumpVisitor ( PrintStream o ) { 
        out = o;
    }

    private String indentString() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < indent; ++i) {
            sb.append(" ");
        }
        return sb.toString();
    }

    public Object visit( ParseTreeNode node, Object data) { 
      String doc_com = "";
      if(node.doc == null) {
        doc_com = "";
      } else if(node.doc != null && node.doc.length() != 0) {
        doc_com = " /**" + node.doc + "*/";
      } 
      if ( node.name.equals("") ) { 
        out.println(indentString() + node + doc_com );
      } else { 
        out.println(indentString() + node + "  (" + node.name + ")" + doc_com);
      }
      ++indent;
      data = node.childrenAccept(this, data);
      --indent;
      return data;
    }
}
