//
// File:        Unspecify.java
// Package:     gov.llnl.babel.visitor
// Copyright:   (c) 2006 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision: 6171 $
// Date:        $Date: 2007-10-08 16:39:28 -0700 (Mon, 08 Oct 2007) $
// Description: Packages, classes, interfaces, structs and enums marked 
//              unselected
// 

package gov.llnl.babel.visitor;
import gov.llnl.babel.ast.ClassType;
import gov.llnl.babel.ast.Enumeration;
import gov.llnl.babel.ast.InterfaceType;
import gov.llnl.babel.ast.NamedType;
import gov.llnl.babel.ast.Package;
import gov.llnl.babel.ast.StructType;

public class Unspecify extends Visitor {
  private Object visitNamedType(NamedType node, Object data)
  {
    node.setUserSelected(false);
    return data;
  }

  public Object visitPackage(Package node, Object data)
  {
    visitNamedType(node, data);
    return super.visitPackage(node, data);
  }

  public Object visitClassType(ClassType node, Object data) {
    return visitNamedType(node, data);
  }

  public Object visitInterfaceType(InterfaceType node, Object data) {
    return visitNamedType(node, data);
  }
  
  public Object visitEnumeration(Enumeration node, Object data) {
    return visitNamedType(node, data);
  }

  public Object visitStructType(StructType node, Object data) {
    return visitNamedType(node, data);
  }
}
