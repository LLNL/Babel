package gov.llnl.babel.visitor;

import gov.llnl.babel.ast.ClassType;
import gov.llnl.babel.msg.MsgList;
import gov.llnl.babel.symbols.Interface;
import gov.llnl.babel.symbols.Method;

import java.util.Iterator;

/**
 * This visitor is run after HierarchicalSorter.commitHierarchy to implement all
 * the methods from interfaces that this class has "implements-all" on.
 * 
 * @author leek2
 * 
 */
public class ImplementsAllVisitor extends Visitor {

  protected MsgList d_msgs;

  public ImplementsAllVisitor(MsgList msgs) {
    super();
    
    d_msgs = msgs;
  }
  
  public Object visitClassType(ClassType node, Object data) {
    // first pull original symbol again
    gov.llnl.babel.symbols.Class cls = (gov.llnl.babel.symbols.Class) node
        .getSymbolTableEntry();

    // Add all the methods of the implments-all interfaces to the class here.
    for(Iterator impls = cls.getParentInterfaces(false).iterator();impls.hasNext();){
     Interface iface = (Interface)impls.next(); 
     if (cls.hasImplmentsAll(iface.getSymbolID())) {
      for (Iterator meths = iface.getMethods(true).iterator(); meths
          .hasNext();) {
        Method method = (Method) meths.next();
        Method clone = method.cloneMethod();
        clone.setDefinitionModifier(Method.NORMAL);
        cls.addMethod(clone);
      }
     }
    }
    return data;
  }
}
