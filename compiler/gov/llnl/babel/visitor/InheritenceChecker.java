//
// File:        SemanticChecker.java
// Package:     
// Copyright:   (c) 2006 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision: 5295 $
// Date:        $Date: 2006-03-24 14:02:42 -0800 (Fri, 24 Mar 2006) $
// Description: This Visitor does a number of semantic checks on the AST. It
// is done last so that the inheritance hierarchy will be fully represented
// in the SymbolTable. 

package gov.llnl.babel.visitor;

import gov.llnl.babel.BabelConfiguration;
import gov.llnl.babel.Context;
import gov.llnl.babel.msg.MsgList;
import gov.llnl.babel.msg.UserMsg;
import gov.llnl.babel.ast.ASTNode;
import gov.llnl.babel.ast.ClassType;

import java.util.List;
import java.util.Iterator;
import java.util.Set;

/**
 * Iterates over the AST with the full decorated Sybol table in order to check
 * that all the method overwrites were done correctly.
 * 
 * Type resolution is largely performed in this stage.
 * 
 * @see gov.llnl.babel.visitor.SymbolTablePrimer
 * @author kumfert
 */
public class InheritenceChecker extends Visitor {
  
  private final static String SIDL_THROWS_INTERFACE = 
    BabelConfiguration.getBaseExceptionInterface();
  
  private final static String SIDL_SERIALIZABLE = 
  BabelConfiguration.getSerializableType();
  
  private Context d_context;
  
  protected MsgList d_msgs;

  public InheritenceChecker(MsgList msgs, Context context) {
    super();
    d_msgs = msgs;
    d_context = context;
  }

  private void checkMethod(gov.llnl.babel.symbols.Method m, ASTNode node) {
    //Check that thrown types inherit from BaseException
    Set thrws = m.getThrows();
    for(Iterator thrwsI = thrws.iterator(); thrwsI.hasNext();) {
      gov.llnl.babel.symbols.SymbolID exID = (gov.llnl.babel.symbols.SymbolID) thrwsI.next();
      gov.llnl.babel.symbols.Extendable ex = (gov.llnl.babel.symbols.Extendable)d_context.getSymbolTable().lookupSymbol(exID);
      gov.llnl.babel.symbols.Symbol baseex = d_context.getSymbolTable().lookupSymbol(SIDL_THROWS_INTERFACE);
      gov.llnl.babel.symbols.SymbolID baseexID = baseex.getSymbolID();
      if(!ex.hasParentInterface(baseexID, true) && !exID.equals(baseexID)) {
        d_msgs.addMsg( new UserMsg( UserMsg.ERROR, "Thown Exceptions must implmement \""+SIDL_THROWS_INTERFACE+"\". " +
            "Type "+baseexID.getFullName()+" thrown from "+m.getLongMethodName()+" does not.", node )); 
        
      }
    }

    //Check that copy arguments inherit from sidl.io.Serializable
    List args = m.getArgumentList();
    for(Iterator argsI = args.iterator(); argsI.hasNext();) {
      gov.llnl.babel.symbols.Argument arg = (gov.llnl.babel.symbols.Argument) argsI.next();
      if(arg.isCopy()) {
        gov.llnl.babel.symbols.Type t = arg.getType();
        if(t.getDetailedType() == gov.llnl.babel.symbols.Type.CLASS || 
           t.getDetailedType() == gov.llnl.babel.symbols.Type.INTERFACE) {
          gov.llnl.babel.symbols.SymbolID exID = t.getSymbolID();
          gov.llnl.babel.symbols.Extendable ex = (gov.llnl.babel.symbols.Extendable)d_context.getSymbolTable().lookupSymbol(exID);
          gov.llnl.babel.symbols.Symbol baseex = d_context.getSymbolTable().lookupSymbol(SIDL_SERIALIZABLE);
          gov.llnl.babel.symbols.SymbolID baseexID = baseex.getSymbolID();
          if(!ex.hasParentInterface(baseexID, true)&& !exID.equals(baseexID)) {
          d_msgs.addMsg( new UserMsg( UserMsg.ERROR, "Copy arguments must implmement \""+SIDL_THROWS_INTERFACE+"\". " +
              "Argument "+arg.getArgumentString()+" of method "+m.getLongMethodName()+" does not.", node )); 
          }
        }
      }
    }

    
  }

  public Object visitClassType(ClassType node, Object data) {
    gov.llnl.babel.symbols.Class cls = (gov.llnl.babel.symbols.Class) node
      .getSymbolTableEntry();
        
    List methods = cls.getMethods(true);
    for(Iterator methI = methods.iterator(); methI.hasNext();) {
      gov.llnl.babel.symbols.Method m = (gov.llnl.babel.symbols.Method)methI.next();
      checkMethod(m,node);
    }
    
    return data;
  }

}
