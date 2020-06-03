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

import gov.llnl.babel.msg.MsgList;
import gov.llnl.babel.msg.UserMsg;
import gov.llnl.babel.ast.ClassType;
import gov.llnl.babel.ast.InterfaceType;
import gov.llnl.babel.ast.Extendable;
import gov.llnl.babel.ast.MethodList;
import gov.llnl.babel.ast.Method;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collection;

/**
 * Iterates over the AST with the full decorated Sybol table in order to check
 * that all the method overwrites were done correctly.
 * 
 * Type resolution is largely performed in this stage.
 * 
 * @see gov.llnl.babel.visitor.SymbolTablePrimer
 * @author kumfert
 */
public class OverrideChecker extends Visitor {
  
  protected MsgList d_msgs;

  public OverrideChecker(MsgList msgs) {
    super();
    d_msgs = msgs;
  }
  
  private Method findMethod(Extendable ext, String longName) {
    MethodList mlist = ext.getMethodList();
    for(Iterator i = mlist.iterator(); i.hasNext();) {
      Method m = (Method) i.next();
      if(m.getMethodName().getLongName().equals(longName)) {
        return m;
      }
    }
    return null;
  }

  private void seperateFromMethods(gov.llnl.babel.symbols.Extendable ext, 
                                   ArrayList fromMethods, 
                                   ArrayList normalMethods) {
    ArrayList localMethods  = (ArrayList) ext.getMethods(false);
    //If no methods have been renamed, just skip the seperation
    if(ext.getNewMethods().size() == 0) {
      normalMethods.addAll(localMethods);
      return;
    }
    for(Iterator lms = localMethods.iterator(); lms.hasNext();) {
      gov.llnl.babel.symbols.Method lm = (gov.llnl.babel.symbols.Method) lms.next();
      if(ext.methodWasRenamed(lm)) {
        fromMethods.add(lm);
      } else {
        normalMethods.add(lm);
      }
    }
  }

  public Object visitClassType(ClassType node, Object data) {
    gov.llnl.babel.symbols.Class thisClass = (gov.llnl.babel.symbols.Class) node
      .getSymbolTableEntry();
    gov.llnl.babel.symbols.Class parent = thisClass.getParentClass();
    ArrayList allMethods = (ArrayList) thisClass.getMethods(true);
    ArrayList fromMethods = new ArrayList();
    ArrayList normalMethods = new ArrayList();
    seperateFromMethods(thisClass, fromMethods, normalMethods);

    if (parent != null) {
      for(Iterator lms = normalMethods.iterator(); lms.hasNext();) {
        gov.llnl.babel.symbols.Method lm = (gov.llnl.babel.symbols.Method) lms.next();
        gov.llnl.babel.symbols.Method existing = parent.lookupMethodByLongName(lm.getLongMethodName(), true);
        if (existing != null) {
          /*
           * If the existing method is final, then we cannot redefine it.
           */
          if (existing.isFinal()) {
            d_msgs
              .addMsg(new UserMsg(UserMsg.ERROR, "Redefinition of final method. Method "+
                                  lm.getLongMethodName()+
                                  " redefines final method in parent class "+
                                  parent.getFullName() , findMethod(node, lm.getLongMethodName())));
            continue;
          }
          
          /*
           * If the existing method is not abstract, then the new method cannot be
           * abstract.
           */
          if (!existing.isAbstract() && lm.isAbstract()) {
            d_msgs
              .addMsg(new UserMsg(UserMsg.ERROR, "Abstract method "+lm.getLongMethodName()+
                                  " redefines final method in parent class "+
                                  parent.getFullName() , findMethod(node, lm.getLongMethodName())));
            continue;
          }
          
          if (!lm.sameSignature(existing)) {
            d_msgs
              .addMsg(new UserMsg(UserMsg.ERROR,"Signature conflict between method \"" 
                                  + lm.getLongMethodName() + "\" from \"" + thisClass.getFullName() + 
                                  "\" and existing ancestor method \"" + existing.getLongMethodName() 
                                  + "\" from \"" + parent.getFullName() +"\".", findMethod(node, lm.getLongMethodName())));
            continue;
          }
          
          if(lm.getCommunicationModifier() != existing.getCommunicationModifier()) {
            d_msgs
              .addMsg(new UserMsg(UserMsg.ERROR,"Communication modifier conflict. Method \"" 
                                  + lm.getLongMethodName() + "\" from \"" + thisClass.getFullName() + 
                                  "\" is declared "+lm.getCommunicationModifierString()+" but existing " 
                                  + "ancestor method \"" + existing.getLongMethodName() 
                                  + "\" from \"" + parent.getFullName() +"\" is decalred "+
                                  existing.getCommunicationModifierString()+".", 
                                  findMethod(node, lm.getLongMethodName())));
            continue;
          }
          
          // Static methods cannot be redefined
          if(lm.isStatic()) {
            d_msgs
              .addMsg(new UserMsg(UserMsg.ERROR,"Static method \"" 
                                  + lm.getLongMethodName() + "\" from \"" + thisClass.getFullName() + 
                                  "\" attempts to redefine existing " 
                                  + "ancestor method \"" + existing.getLongMethodName() 
                                  + "\" from \"" + parent.getFullName() +"\".", 
                                  findMethod(node, lm.getLongMethodName())));
            
            continue;
          } 
          if(existing.isStatic()) {
            d_msgs
              .addMsg(new UserMsg(UserMsg.ERROR,"Method \""+ lm.getLongMethodName() + "\" from \"" 
                                  + thisClass.getFullName() + 
                                  "\" attempts to redifine static ancestor method \"" 
                                  + existing.getLongMethodName() 
                                  + "\" from \"" + parent.getFullName() +"\".", 
                                  findMethod(node, lm.getLongMethodName())));
            continue;
          }
        }
        
        /*
         * Now check to make sure no conflicts with existing/parent method with
         * same short name and same base signature.
         */
        Collection methods = parent.lookupMethodByShortName(lm.getShortMethodName(),
                                                            true);
        if (methods != null) {
          for (Iterator i = methods.iterator(); i.hasNext(); ) {
            existing = (gov.llnl.babel.symbols.Method) i.next();
            if (existing.sameBaseSignature(lm)) {
              /*
               * If the existing method is final, then we cannot redefine it.
               */
              if (existing.isFinal()) {
                d_msgs
                  .addMsg(new UserMsg(UserMsg.ERROR, "Method "+lm.getLongMethodName()+
                                      " redefines final method in parent class "+
                                      parent.getFullName() , findMethod(node, lm.getLongMethodName())));
                continue;
              }
              
              /*
               * If the existing method is not abstract, then the new method
               * cannot be abstract.
               */
              if (!existing.isAbstract() && lm.isAbstract()) {
                d_msgs
                  .addMsg(new UserMsg(UserMsg.ERROR, "Abstract method "+lm.getLongMethodName()+
                                      " redefines final method in parent class "+
                                      parent.getFullName() , findMethod(node, lm.getLongMethodName())));
                continue;
              }
            }
          }
        }
      }
    }

    //Test From clause Methods
    if (parent != null) {
      for(Iterator lms = fromMethods.iterator(); lms.hasNext();) {
        gov.llnl.babel.symbols.Method newM = (gov.llnl.babel.symbols.Method) lms.next();
        gov.llnl.babel.symbols.Method oldM = thisClass.getRenamedMethod(newM);
        gov.llnl.babel.symbols.SymbolID fromID = thisClass.getRenamedMethodSymbolID(oldM);
        if(parent.getSymbolID().equals(fromID)) {
          d_msgs
            .addMsg(new UserMsg(UserMsg.ERROR, "From clause renames method inherited from class.", 
                                findMethod(node, newM.getLongMethodName())));
        }
      }
    }
    for(Iterator lms = fromMethods.iterator(); lms.hasNext();) {
      gov.llnl.babel.symbols.Method newM = (gov.llnl.babel.symbols.Method) lms.next();
      gov.llnl.babel.symbols.Method oldM = thisClass.getRenamedMethod(newM);
      gov.llnl.babel.symbols.SymbolID fromID = thisClass.getRenamedMethodSymbolID(oldM);
      
      gov.llnl.babel.symbols.Interface parentI = thisClass.getParentInterface(fromID, true);
      gov.llnl.babel.symbols.Method existing = parentI.lookupMethodByLongName(oldM.getLongMethodName(), false);
      if(existing != null) {  
        if (!newM.sameSignature(existing, false)) {
          d_msgs
            .addMsg(new UserMsg(UserMsg.ERROR,"Signature conflict between method \"" 
                                + newM.getLongMethodName() + "\" from \"" + thisClass.getFullName() + 
                                "\" and existing ancestor method \"" + existing.getLongMethodName() 
                                + "\" from \"" + parentI.getFullName() +"\".", node));
          continue;
        } 
      }
    }

    //Done with From Clause stuff
    //Check that we don't have 2 methods with the same short name and arg lists
    for(Iterator ams = allMethods.iterator(); ams.hasNext();) {
      gov.llnl.babel.symbols.Method cur_m = (gov.llnl.babel.symbols.Method) ams.next();
      Collection s_methods = thisClass.lookupMethodByShortName(cur_m.getShortMethodName(),
                                                            true);
      if (s_methods != null && !d_msgs.fatal_message()) {
        for (Iterator s = s_methods.iterator(); s.hasNext(); ) {
          gov.llnl.babel.symbols.Method existing = 
            (gov.llnl.babel.symbols.Method) s.next();
          //If they have the same signatures, just ignore them, they're
          //either the same method or inherited
          if(!d_msgs.fatal_message() && !cur_m.sameSignature(existing)) {
            //However, if they only differ on name extension or throws
            if(cur_m.overloadCollision(existing)) {
              d_msgs.addMsg( new UserMsg( UserMsg.ERROR, "Overloaded methods must have sufficiently different "
                                          + "argument lists.  Method " + cur_m.getLongMethodName() 
                                          + " has the same short name and argument list as " + 
                                          existing.getLongMethodName(), node));
              
            }
          }
        }
      }
    }


    //abstract classes
    if(thisClass.isAbstract() && !thisClass.getAbstractModifier()) {
      d_msgs
      .addMsg(new UserMsg(UserMsg.ERROR, "Non abstract class "+ thisClass.getFullName() 
          + " has abstract methods.", node));
    } else if(!thisClass.isAbstract() && thisClass.getAbstractModifier()) {
      d_msgs  
      .addMsg(new UserMsg(UserMsg.ERROR, "Abstract class "+ thisClass.getFullName() 
          + " has no abstract methods.", node));
    }
    
    //Make sure the methods don't have the same names as the class
    gov.llnl.babel.symbols.Method longm = thisClass.lookupMethodByLongName(thisClass
        .getSymbolID().getShortName(), true);
    boolean shortm = thisClass.hasMethodByShortName(thisClass.getSymbolID()
        .getShortName(), true);
    if (longm != null) {
      d_msgs
          .addMsg(new UserMsg(UserMsg.ERROR, "Method "
              + longm.getLongMethodName() + " has the same name as class "
              + thisClass.getFullName() + ", which it exists in.  This is illegal.",
              node));
    } else if (shortm) {
      d_msgs.addMsg(new UserMsg(UserMsg.ERROR, "Method "
          + thisClass.getSymbolID().getShortName()
          + " has the same short name as class " + thisClass.getFullName()
          + ", which it exists in.  This is illegal.", node));

    }
    return data;
  } 
  
  public Object visitInterfaceType(InterfaceType node, Object data) {
    gov.llnl.babel.symbols.Interface thisI = (gov.llnl.babel.symbols.Interface) node
        .getSymbolTableEntry();
    ArrayList allMethods = (ArrayList) thisI.getMethods(true); 
    ArrayList fromMethods = new ArrayList();
    ArrayList normalMethods = new ArrayList();

    seperateFromMethods(thisI, fromMethods, normalMethods);

    //Check from clauses
    for(Iterator lms = fromMethods.iterator(); lms.hasNext();) {
      gov.llnl.babel.symbols.Method newM = (gov.llnl.babel.symbols.Method) lms.next();
      gov.llnl.babel.symbols.Method oldM = thisI.getRenamedMethod(newM);
      gov.llnl.babel.symbols.SymbolID fromID = thisI.getRenamedMethodSymbolID(oldM);
      
      gov.llnl.babel.symbols.Interface parentI = thisI.getParentInterface(fromID, true);
      gov.llnl.babel.symbols.Method existing = parentI.lookupMethodByLongName(oldM.getLongMethodName(), false);
      if(existing != null) {  
        if (!newM.sameSignature(existing, false)) {
          d_msgs
            .addMsg(new UserMsg(UserMsg.ERROR,"Signature conflict between method \"" 
                                + newM.getLongMethodName() + "\" from \"" + thisI.getFullName() + 
                                "\" and existing ancestor method \"" + existing.getLongMethodName() 
                                + "\" from \"" + parentI.getFullName() +"\".", node));
          continue;
        } 
      }
    }

    //Check that we don't have 2 methods with the same short name and arg lists
    for(Iterator ams = allMethods.iterator(); ams.hasNext();) {
      gov.llnl.babel.symbols.Method cur_m = (gov.llnl.babel.symbols.Method) ams.next();
      Collection s_methods = thisI.lookupMethodByShortName(cur_m.getShortMethodName(),
                                                            true);
      if (s_methods != null && !d_msgs.fatal_message()) {
        for (Iterator s = s_methods.iterator(); s.hasNext(); ) {
          gov.llnl.babel.symbols.Method existing = 
            (gov.llnl.babel.symbols.Method) s.next();
          //If they have the same signatures, just ignore them, they're
          //either the same method or inherited
          if(!d_msgs.fatal_message() && !cur_m.sameSignature(existing)) {
            //However, if they only differ on name extension or throws
            if(cur_m.overloadCollision(existing)) {
              d_msgs.addMsg( new UserMsg( UserMsg.ERROR, "Overloaded methods must have sufficiently different "
                                          + "argument lists.  Method " + cur_m.getLongMethodName() 
                                          + " has the same short name and argument list as " + 
                                          existing.getLongMethodName(), node));
            }
          }
        }
      }
    }

    //check methods don't have same name as the interface
    gov.llnl.babel.symbols.Method longm = thisI.lookupMethodByLongName(thisI
      .getSymbolID().getShortName(), true); 
  boolean shortm = thisI.hasMethodByShortName(thisI.getSymbolID()
      .getShortName(), true); 
  if (longm != null) {  
    d_msgs.addMsg(new UserMsg(UserMsg.ERROR, "Method "
        + longm.getLongMethodName() + " has the same name as interface "
        + thisI.getFullName() + ", which it exists in.  This is illegal.",
        node));
  } else if (shortm) {
    d_msgs.addMsg(new UserMsg(UserMsg.ERROR, "Method "
        + thisI.getSymbolID().getShortName()
        + " has the same short name as interface " + thisI.getFullName()
        + ", which it exists in.  This is illegal.", node));

  }
  return data;
  }
}
