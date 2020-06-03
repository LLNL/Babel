//
// File:        BuiltinAttributeCheck.java
// Package:     gov.llnl.babel.visitor
// Copyright:   (c) 2006 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision: 6171 $
// Date:        $Date: 2007-10-08 16:39:28 -0700 (Mon, 08 Oct 2007) $
// Description: Check Babel AST for conflicting attributes
// 

package gov.llnl.babel.visitor;

import gov.llnl.babel.ast.Argument;
import gov.llnl.babel.ast.Attribute;
import gov.llnl.babel.ast.ClassType;
import gov.llnl.babel.ast.Enumeration;
import gov.llnl.babel.ast.InterfaceType;
import gov.llnl.babel.ast.Method;
import gov.llnl.babel.ast.Package;
import gov.llnl.babel.ast.StructType;
import gov.llnl.babel.msg.MsgList;
import gov.llnl.babel.msg.UserMsg;
import gov.llnl.babel.msg.UserMsg2;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class BuiltinAttributeCheck extends Visitor {
  static HashSet s_allBuiltins = new HashSet(); // all builtin attributes
  static HashSet s_pkgBuiltins = new HashSet(); // package builtin
                                                // attributes
  static HashSet s_clsBuiltins = new HashSet(); // class builtin attributes
  static HashSet s_ifcBuiltins = new HashSet(); // interface builtin
                                                // attributes
  static HashSet s_enmBuiltins = new HashSet(); // enumeration builtin
                                                // attributes
  static HashSet s_strBuiltins = new HashSet(); // struct builtin attributes
  static HashSet s_mtdBuiltins = new HashSet(); // method builtin attributes
  static HashSet s_argBuiltins = new HashSet(); // argument builtin
                                                // attributes
  static HashSet s_methodDispatch = new HashSet();
  static HashSet s_methodCommunication = new HashSet();


  static {
    s_pkgBuiltins.add("final");
    
    s_clsBuiltins.add("abstract");
    
    s_methodDispatch.add("final");
    s_methodDispatch.add("static");
    s_methodDispatch.add("abstract");
    s_methodCommunication.add("local");
    s_methodCommunication.add("nonblocking");
    s_methodCommunication.add("oneway");
    s_mtdBuiltins.addAll(s_methodDispatch);
    s_mtdBuiltins.addAll(s_methodCommunication);
    s_mtdBuiltins.add("copy");
    
    s_argBuiltins.add("copy");

    s_allBuiltins.addAll(s_pkgBuiltins);
    s_allBuiltins.addAll(s_clsBuiltins);
    s_allBuiltins.addAll(s_ifcBuiltins);
    s_allBuiltins.addAll(s_enmBuiltins);
    s_allBuiltins.addAll(s_strBuiltins);
    s_allBuiltins.addAll(s_mtdBuiltins);
    s_allBuiltins.addAll(s_argBuiltins);
  }

  private MsgList d_msgs;


  private class BasicAttributeChecker {
    private Set       d_builtins;
    private String    d_nodeType;
    private HashMap   d_accumulate = new HashMap();

    public BasicAttributeChecker(String nodeType,
                                 Set builtins){
      d_nodeType = nodeType;
      d_builtins = builtins;
    }

    void checkAttribute(Attribute a)
    {
      final String key = a.getKey();
      if (s_allBuiltins.contains(key) && !d_builtins.contains(key)) {
        d_msgs.addMsg(new UserMsg
                      (UserMsg.WARNING, "This " + d_nodeType +
                       " has attribute " + key + 
                       " which is a builtin Babel attribute in other contexts.",
                       a));
      }
      if (d_accumulate.containsKey(key)) {
        d_msgs.addMsg
          (new UserMsg2
           (UserMsg.WARNING, "Redundant declaration of " +
            d_nodeType + " attribute " + key
            + ".", a, "", (Attribute)d_accumulate.get(key)));
      }
      d_accumulate.put(key, a);
    }
  }

  private class MethodAttributeChecker extends BasicAttributeChecker {
    HashMap d_dispatch = new HashMap();
    HashMap d_communication = new HashMap();

    public MethodAttributeChecker() {
      super("method", s_mtdBuiltins);
    }

    void checkExclusive(Attribute a,
                        Set exclusive, 
                        Map accumulate)
    {
      String key = a.getKey();
      if (exclusive.contains(key)) {
        if (accumulate.size() > 0) {
          Iterator i = accumulate.entrySet().iterator();
          while (i.hasNext()) {
            Map.Entry entry = (Map.Entry)i.next();
            d_msgs.addMsg
              (new UserMsg2(UserMsg.ERROR, "The method attribute " + key +
                            " conflicts with a previously declared attribute.",
                            a, "These attributes are mutually exclusive.",
                            (Attribute)entry.getValue()));
          }
        }
        accumulate.put(key, a);
      }
    }

    void checkAttribute(Attribute a)
    {
      super.checkAttribute(a);
      checkExclusive(a, s_methodDispatch, d_dispatch);
      checkExclusive(a, s_methodCommunication, d_communication);
    }
  }

  public BuiltinAttributeCheck(MsgList msgs)
  {
    d_msgs = msgs;
  }

  public Object visitAttribute(Attribute node, Object data)
  {
    BasicAttributeChecker check = (BasicAttributeChecker)data;
    check.checkAttribute(node);
    return check;
  }

  public Object visitMethod(Method node, Object data)
  {
    return super.visitMethod(node, new MethodAttributeChecker());
  }

  public Object visitArgument(Argument node, Object data)
  {
    return super.visitArgument
      (node, new BasicAttributeChecker("argument", s_argBuiltins));
  }

  public Object visitPackage(Package node, Object data)
  {
    return super.visitPackage
      (node, new BasicAttributeChecker("package", s_pkgBuiltins));
  }

  public Object visitClassType(ClassType node, Object data)
  {
    return super.visitClassType
      (node, new BasicAttributeChecker("class", s_clsBuiltins));
  }

  public Object visitInterfaceType(InterfaceType node, Object data)
  {
    return super.visitInterfaceType
      (node, new BasicAttributeChecker("interface", s_ifcBuiltins));
  }

  public Object visitEnumeration(Enumeration node, Object data)
  {
    return super.visitEnumeration
      (node, new BasicAttributeChecker("enum", s_enmBuiltins));
  }

  public Object visitStructType(StructType node, Object data)
  {
    return super.visitStructType
      (node, new BasicAttributeChecker("struct", s_strBuiltins));
  }
}
