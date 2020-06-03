//
// File:        SemanticChecker.java
// Package:     
// Copyright:   (c) 2006 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision: 6201 $
// Date:        $Date: 2007-10-25 23:15:35 -0700 (Thu, 25 Oct 2007) $
// Description: This Visitor does a number of semantic checks on the AST. It
// is done last so that the inheritance hierarchy will be fully represented
// in the SymbolTable. 

package gov.llnl.babel.visitor;

import gov.llnl.babel.Context;
import gov.llnl.babel.msg.MsgList;
import gov.llnl.babel.ast.ClassType;

/**
 * Iterates over a AST a second time to decorate a primed SymbolTable
 * with all the details about classes, interfaces, and structs
 * 
 * Type resolution is largely performed in this stage.
 * 
 * @see gov.llnl.babel.visitor.SymbolTablePrimer
 * @author kumfert
 */
public class SemanticChecker extends Visitor {
  
  protected MsgList d_msgs;

  public SemanticChecker(MsgList msgs, Context context) {
    super();
    d_msgs = msgs;
  }
  
  public Object visitClassType(ClassType node, Object data) { 
    return data;
  }
}
