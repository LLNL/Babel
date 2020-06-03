//
// File:        SyntaxChecker.java
// Package:     gov.llnl.babel.visitor
// Copyright:   (c) 2006 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision: 6251 $
// Date:        $Date: 2007-11-12 16:00:20 -0800 (Mon, 12 Nov 2007) $
// Description: Check for illegal things that are allowed by the grammar
// 

package gov.llnl.babel.visitor;

import gov.llnl.babel.ast.ASTNode;
import gov.llnl.babel.ast.Extents;
import gov.llnl.babel.ast.Name;
import gov.llnl.babel.ast.RArrayType;
import gov.llnl.babel.ast.Type;
import gov.llnl.babel.msg.MsgList;
import gov.llnl.babel.msg.UserMsg;

import java.util.HashSet;
import java.util.Iterator;

/**
 * The SIDL grammar allows for somethings that aren't actually legal.
 * This visitor finds and reports those things.
 *
 */
public class SyntaxChecker extends Visitor {
  private MsgList d_msgs;
  private boolean d_arrayExtent = false;
  private int d_varCount = 0;

  private static final HashSet s_rarrayTypes = new HashSet();

  static {
    s_rarrayTypes.add("int");
    s_rarrayTypes.add("long");
    s_rarrayTypes.add("float");
    s_rarrayTypes.add("double");
    s_rarrayTypes.add("fcomplex");
    s_rarrayTypes.add("dcomplex");
    s_rarrayTypes.add("char");  /* should char be allowed? */
  }

  public SyntaxChecker(MsgList msgs) {
    d_msgs = msgs;
  }
  
  public Object visitName(Name node, Object data)
  {
    if (d_arrayExtent) {
      if (++d_varCount > 1) {
        d_msgs.addMsg
          (new UserMsg(UserMsg.ERROR,
                       "Array extent expressions must be extremely simple. They may only make one reference to one argument.",
                       node));
      }
    }
    return data;
  }

  public Object visitRArrayType(RArrayType node, Object data)
  {
    Type scalar = node.getScalarType();
    if (scalar == null) {
      d_msgs.addMsg
        (new UserMsg(UserMsg.ERROR,
                     "rarrays cannot be generic -- they are only for numeric types.", node));
    }
    else if (!s_rarrayTypes.contains(scalar.getTypeName())) {
      d_msgs.addMsg
        (new UserMsg(UserMsg.ERROR,
                     "rarrays of " + scalar.getTypeName() + 
                     " are not allowed; only numeric types can appear inside rarrays.", scalar));
    }
    return super.visitRArrayType(node, data);
  }
  
  public Object visitExtents(Extents node, Object data)
  {
    Iterator i = node.getExtents().iterator();
    try {
      d_arrayExtent = true;
      while (i.hasNext()) {
        d_varCount = 0;
        ASTNode extent = (ASTNode)i.next();
        extent.accept(this, data);
      }
    }
    finally {
      d_arrayExtent = false;
    }
    return data;
  }
}
