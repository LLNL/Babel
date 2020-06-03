//
// File:        NameChecker.java
// Package:     gov.llnl.babel.visitor
// Copyright:   (c) 2007 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision: 5885 $
// Date:        $Date: 2007-02-28 15:54:54 -0700 (Wed, 28 Feb 2007) $
// Description: Report long names with warning messages
// 

package gov.llnl.babel.visitor;

import gov.llnl.babel.ast.Argument;
import gov.llnl.babel.ast.Method;
import gov.llnl.babel.ast.Name;
import gov.llnl.babel.ast.Type;
import gov.llnl.babel.msg.MsgList;
import gov.llnl.babel.msg.UserMsg;

public class StructScopedIDCheck extends Visitor {
  private MsgList d_msgs;
  private Name check;
  
  public StructScopedIDCheck(MsgList msgs,Name inName)
  {
    d_msgs = msgs;
    check=inName;
  }

  public Object visitArgument(Argument node, Object data)
  {
    if (node.getMode()=="out"){
      Type T = node.getType();
      if (T.getTypeName().equals("scoped-id")){
        if ((node.getName2()).equals(check)){
          d_msgs.addMsg
            (new UserMsg(UserMsg.ERROR,
              "Struct with Rarray is OUT mode!",node));
        }
      }
    }
    return super.visitArgument(node, data);
  }

  public Object visitMethod(Method node, Object data)
  {
      if (node.getName2() != null){
        if ((node.getName2()).equals(check)){
          d_msgs.addMsg
            (new UserMsg(UserMsg.ERROR,
                        "Found Struct with Rarray as function return type!",node));
        }
      }
    return super.visitMethod(node, data);
  }
}
