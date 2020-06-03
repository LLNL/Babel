//
// File:        NameChecker.java
// Package:     gov.llnl.babel.visitor
// Copyright:   (c) 2007 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision: 6171 $
// Date:        $Date: 2007-10-08 16:39:28 -0700 (Mon, 08 Oct 2007) $
// Description: Report long names with warning messages
// 

package gov.llnl.babel.visitor;

import gov.llnl.babel.ast.ASTNode;
import gov.llnl.babel.ast.Argument;
import gov.llnl.babel.ast.ClassType;
import gov.llnl.babel.ast.Enumeration;
import gov.llnl.babel.ast.InterfaceType;
import gov.llnl.babel.ast.Method;
import gov.llnl.babel.ast.MethodName;
import gov.llnl.babel.ast.Name;
import gov.llnl.babel.ast.INameable;
import gov.llnl.babel.ast.Package;
import gov.llnl.babel.ast.StructItem;
import gov.llnl.babel.ast.StructType;
import gov.llnl.babel.msg.MsgList;
import gov.llnl.babel.msg.UserMsg;

public class NameChecker extends Visitor {
  private MsgList d_msgs;
  
  public NameChecker(MsgList msgs)
  {
    d_msgs = msgs;
  }

  public Object visitMethod(Method node, Object data)
  {
    MethodName name = node.getMethodName();
    if (name != null) {
      if (name.getLongName().length() > 29) {
        d_msgs.addMsg
          (new UserMsg(UserMsg.WARNING,
                       "The method name '" + name.toString() +
                       "' is over 29 characters and may cause errors for the Fortran 90 binding.", node));
      }
    }
    return super.visitMethod(node, data);
  }

  private void checkName(INameable node, String desc, int max)
  {
    final Name name = node.getName();
    if (name != null) {
      final String str = name.toString();
      if (str.length() > max) {
        d_msgs.addMsg
          (new UserMsg(UserMsg.WARNING,
                       "The " + desc + " name '" + str + 
                       "' is longer than " +
                       Integer.toString(max) + 
                       " characters and may cause errors in the Fortran 90 binding.", 
                       (ASTNode)node));
           }
    }
  }

  public Object visitStructType(StructType node, Object data)
  {
    checkName(node, "struct", 31);
    return super.visitStructType(node, data);
  }

  public Object visitStructItem(StructItem node, Object data)
  {
    checkName(node, "element of struct", 26);
    return super.visitStructItem(node, data);
  }

  public Object visitEnumeration(Enumeration node, Object data)
  {
    checkName(node, "enumerated type",31);
    return super.visitEnumeration(node, data);
  }

  public Object visitInterfaceType(InterfaceType node, Object data)
  {
    checkName(node, "interface", 31);
    return super.visitInterfaceType(node, data);
  }

  public Object visitClassType(ClassType node, Object data)
  {
    checkName(node, "class", 31);
    return super.visitClassType(node, data);
  }

  public Object visitPackage(Package node, Object data)
  {
    checkName(node, "package", 31);
    return super.visitPackage(node, data);
  }

  public Object visitArgument(Argument node, Object data)
  {
    checkName(node, "argument", 31);
    return super.visitArgument(node, data);
  }
}
