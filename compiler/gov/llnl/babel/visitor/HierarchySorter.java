package gov.llnl.babel.visitor;

import gov.llnl.babel.BabelConfiguration;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Class;
import gov.llnl.babel.symbols.Interface;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.msg.MsgList;
import gov.llnl.babel.msg.UserMsg;
import gov.llnl.babel.msg.UserMsg0;


import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Iterator;

/**
 * This class is used by SymbolTableDecorator to collect a hierarchy
 * of modified types, and add their lineage to the SymbolTable in
 * a strict topilogical order.
 * 
 * @author kumfert
 *
 */
public class HierarchySorter {

  private final static String SIDL_INTERFACE = 
      BabelConfiguration.getBaseInterface();
  
  /**
   * A map of FQN to the actual symbol.
   */
  HashMap d_name2symbol;
  
  /** 
   * A map from FQN to an integer count. When int==0, then type is ready 
   * Count is only of unresolved *immediate* ancestors, not all ancestors.
   */
  HashMap d_unresolvedAncestors; 
  
  /** 
   * A map from FQN to a Set of descendants FQNs 
   */
  HashMap d_children;
  
  /**
   * A map from FQN to a LinkedList of list of parents
   */
  HashMap d_parents;
  MsgList d_msgs;

  public HierarchySorter(MsgList msgs) {
    super();
    d_name2symbol = new HashMap();
    d_unresolvedAncestors = new HashMap();
    d_children = new HashMap();
    d_parents = new HashMap();
    d_msgs = msgs;
  }

  /**
   * Check for method conflicts between a parent and a child.  Most of these
   * sorts of checks are done in visitor/OverrideChecker, but if there's a
   * longmethodname conflict the new one will simply overwrite the old one
   * in the symbol table, so we can't detect the conflict later in OverrideChecker.
   * @param Extendable parent
   * @param Extendable child
   */
  void checkMethods(Extendable parent, Extendable child) {
    for(Iterator pms = parent.getMethods(true).iterator(); pms.hasNext();) {
      Method pm = (Method) pms.next();
      //Check first if the new method has been renamed
      String oldName = parent.getFullName()+"."+pm.getLongMethodName();
      Method cm = child.getNewMethod(oldName);
      if(cm == null) {
        cm = child.lookupMethodByLongName(pm.getLongMethodName(), true);
      }
      if(cm != null) {
        if(!cm.sameSignature(pm, false)) {
          d_msgs
            .addMsg(new UserMsg0(UserMsg.ERROR,"Signature conflict between method \n\"" 
                                 + pm.getSignature(false,null) + "\"\n from \"" + 
                                 parent.getFullName() + 
                                 "\" and method\n\"" + cm.getSignature(false,null) 
                                 + "\"\n from \"" + child.getFullName() +"\"."));
        }
      }
    }
  }

  /**
   * Increments the number of unresolved ancestors for type
   * @param name fully qualified name of the class or interface
   * @return new number of unresolved ancestors
   */
  private int incrUnresolvedAncestors( String name ) { 
    int new_count = ((Integer) d_unresolvedAncestors.get(name)).intValue() + 1;
    d_unresolvedAncestors.put(name, new Integer(new_count) );
    return new_count;
  }
  
  /**
   * Decrements the number of unresolved ancestors for type
   * @param name fully qualified name of the class or interface
   * @return new number of unresolved ancestors
   */
  private int decrUnresolvedAncestors( String name ) { 
    int new_count = ((Integer) d_unresolvedAncestors.get(name)).intValue() - 1;
    d_unresolvedAncestors.put(name, new Integer(new_count) );
    return new_count;
  }
  
  /**
   * Register an inheritance relationship to be revealed to SymbolTable later.
   * 
   * @param parent
   * @param child
   */
  public void registerParentChild( Extendable parent, Extendable child ) { 
    String parent_name = parent.getFullName();
    String child_name = child.getFullName();
    
    // first make sure these symbols are known
    d_name2symbol.put(parent_name, parent);
    d_name2symbol.put(child_name, child);

    // next update d_unresolvedAncestors
    if ( !d_unresolvedAncestors.containsKey(child_name) ) { 
      d_unresolvedAncestors.put(child_name, new Integer(0) );
    }
    if ( parent.getParents(true).size() == 0 && (!parent_name.equals(SIDL_INTERFACE))) {
      incrUnresolvedAncestors(child_name);
    }
    
    // now populate list of children 
    if ( d_children.containsKey(parent_name)) { 
      LinkedList l = (LinkedList) d_children.get(parent_name);
      l.add(child_name);
    } else { 
      LinkedList l = new LinkedList();
      l.add(child_name);
      d_children.put(parent_name,l);
    }
    
    // finally populate list of parents
    if ( d_parents.containsKey(child_name)) { 
      LinkedList l = (LinkedList) d_parents.get(child_name);
      l.add(parent_name);
    } else { 
      LinkedList l = new LinkedList();
      l.add(parent_name);
      d_parents.put(child_name,l);
    }
  }
  
  private void resolveHierarchyOf(String child_name) {
    Extendable child_symbol = (Extendable) d_name2symbol.get(child_name);
    if (child_symbol instanceof Class) {
      Class child = (Class) child_symbol;
      for (Iterator i = ((LinkedList) d_parents.get(child_name)).iterator(); i
          .hasNext();) {
        String parent_name = (String) i.next();
        Extendable parent = (Extendable) d_name2symbol.get(parent_name);
        checkMethods(parent,child);
        if (parent instanceof Class) {
          child.setParentClass((Class) parent);
        } else if (parent instanceof Interface) {
          child.addParentInterface((Interface) parent);
          // We need to add the methods for implments-all here, so the children
          // of this class get them
          if (child.hasImplmentsAll(parent.getSymbolID())) {
            for (Iterator meths = parent.getMethods(true).iterator(); meths.hasNext();) {
              Method method = (Method) meths.next();
              Method defined = child.lookupMethodByLongName(method.getLongMethodName(),
                  false);
              if (defined == null) {  
                Method exist = child.lookupMethodByLongName(method.getLongMethodName(),
                  true);
                if (exist.isAbstract()) {
                  Method clone = method.cloneMethod();
                  clone.setDefinitionModifier(Method.NORMAL);
                  child.addMethod(clone);
                }
              }
            }
          }
        }
      }
    } else if (child_symbol instanceof Interface) {
      for (Iterator i = ((LinkedList) d_parents.get(child_name)).iterator(); i
          .hasNext();) {
        String parent_name = (String) i.next();
        Interface parent = (Interface) d_name2symbol.get(parent_name);
        child_symbol.addParentInterface((Interface) parent);
 
      }
    }
    // now that child is resolved, need to (a) remove it from d_unresolvedAncestors
    // (b) decrement all its grandchildren
    if ( d_children.containsKey(child_name) ) { 
      for( Iterator i = ((LinkedList) d_children.get(child_name)).iterator(); i.hasNext(); ) { 
        String grandchild_name = (String) i.next();
        decrUnresolvedAncestors(grandchild_name);
      }
    }
    d_unresolvedAncestors.remove(child_name);
  }

  public void commitHierarchy() { 
    while (d_unresolvedAncestors.size() > 0 ) { 
      // find a child with no unresolved ancestors (...meaning we are ready to resolve the child)
      String child = null;
      for (Iterator i = d_unresolvedAncestors.entrySet().iterator(); i.hasNext(); ) { 
        Map.Entry e = (Map.Entry) i.next();
        if ( ((Integer) e.getValue()).intValue() == 0 ) { 
          child = (String) e.getKey();
          break;
        }
      }
      if ( child == null ) { 
        //If child == null, that means no symbol is ready to be resolved. 
        //Which means no child has all of it's ancestors resolved.
        //In other words, there must be a circular dependecy.
        //WISH: This error message just lists all the unresolveable symbols,
        //if anyone needs it, it would be easy to add a cycle check to old
        //get the actual types involved in the cycle.
        StringBuffer buf = new StringBuffer();
        buf.append("A cyclic inheritence dependecy has been detected.  It may involve the following types:\n");
        for(Iterator i = d_unresolvedAncestors.keySet().iterator(); i.hasNext();) {
          buf.append('.');
          buf.append((String)i.next());
          buf.append('\n');
        }
        d_msgs.addMsg( new UserMsg0(UserMsg.ERROR, buf.toString()));
        return;
      }
      d_unresolvedAncestors.remove(child);
      
      resolveHierarchyOf(child); 
    } // end while loop
  } // end commitHierarchy()
}
