package gov.llnl.babel.ast;

import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.visitor.Visitor;

import java.util.List;
import java.util.LinkedList;

/**
 * A named list of <code>TypeSearchClause</code>s followed by <code>Package</code>s.
 */
public class SIDLFile extends ASTNode {

  String d_filename;

  LinkedList d_typeSearchClauses;

  LinkedList d_packages;

  public SIDLFile(ParseTreeNode src, String filename) {
    super(src, null);

    if(filename != null) {
      if ( filename.startsWith("file:") ) { 
        d_filename = filename.substring(5);
      } else {
        d_filename = filename;
      }
    }

    d_typeSearchClauses = new LinkedList();
    d_packages = new LinkedList();
  }

  public String getFilename() {
    return d_filename;
  }

  public List getTypeSearchList() {
    return d_typeSearchClauses;
  }

  public void appendTypeSearchClause(TypeSearchClause tsc) {
    d_typeSearchClauses.add(tsc);
  }

  public List getPackageList() {
    return d_packages;
  }

  public void appendPackage(Package p) {
    d_packages.add(p);
  }

  public Object accept(Visitor v, Object data) {
    return v.visitSIDLFile(this, data);
  }
}
