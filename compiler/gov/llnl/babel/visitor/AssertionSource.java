//
// File:        AssertionSource.java
// Package:     gov.llnl.babel.visitor
// Copyright:   (c) 2006 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision: 6171 $
// Date:        $Date: 2007-10-08 16:39:28 -0700 (Mon, 08 Oct 2007) $
// Description: Populate source information for assertions
// 

package gov.llnl.babel.visitor;
import gov.llnl.babel.ast.Assertion;
import gov.llnl.babel.ast.Package;
import gov.llnl.babel.ast.ClassType;
import gov.llnl.babel.ast.InterfaceType;

public class AssertionSource extends Visitor {
  public Object visitAssertion(Assertion node, Object data)
  {
    node.setSource((String)data);
    return data;
  }

  public Object visitPackage(Package node, Object data)
  {
    String prefix = (data != null) ? (String)data : "";
    return super.visitPackage(node, prefix + "." + node.getName().toString());
  }

  public Object visitClassType(ClassType node, Object data)
  {
    String prefix = (data != null) ? (String)data : "";
    return super.visitClassType(node, prefix + "." + node.getName().toString());
  }

  public Object visitInterfaceType(InterfaceType node, Object data)
  {
    String prefix = (data != null) ? (String)data : "";
    return super.visitInterfaceType(node, prefix + "." + node.getName().toString());
  }
}
