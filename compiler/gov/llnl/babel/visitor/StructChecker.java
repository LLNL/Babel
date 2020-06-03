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

import gov.llnl.babel.ast.Name;
import gov.llnl.babel.ast.SIDLFile;
import gov.llnl.babel.ast.StructItem;
import gov.llnl.babel.ast.StructType;
import gov.llnl.babel.msg.MsgList;
import gov.llnl.babel.msg.UserMsg;

import java.util.Iterator;
import java.util.LinkedList;

public class StructChecker extends Visitor {
  private MsgList d_msgs;
  private LinkedList internalList;
  
  public StructChecker(MsgList msgs,LinkedList d_astList)
  {
    d_msgs = msgs;
    internalList=d_astList;
  }

  public Object visitStructItem(StructItem node, Object data)
  {
    String name = node.getName().toString();
    if (name.length() > 24) {
      d_msgs.addMsg
        (new UserMsg(UserMsg.ERROR,
                     "Struct item name \"" +
                     name + "\" exceeds the limit of 24 characters.", node));
    }
    return data;
  }


  public Object visitStructType(StructType node, Object data)
  {
    Name testName;

    gov.llnl.babel.symbols.Struct s = (gov.llnl.babel.symbols.Struct)
      node.getSymbolTableEntry();

    if (s.hasRarrayReference()) {
      testName=node.getName();
      for(Iterator it = internalList.iterator(); it.hasNext() && !d_msgs.fatal_message(); ) { 
        SIDLFile f = (SIDLFile) it.next();
        f.accept(new gov.llnl.babel.visitor.StructScopedIDCheck(d_msgs,testName),null);
      }
    }

    return super.visitStructType(node, data);
  }

}
