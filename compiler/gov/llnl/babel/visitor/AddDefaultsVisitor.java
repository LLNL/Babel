//
// File:        AddDefaultsVisitor.java
// Package:     
// Copyright:   (c) 2006 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision: 6201 $
// Date:        $Date: 2007-10-25 23:15:35 -0700 (Thu, 25 Oct 2007) $
// Description: 
// 
package gov.llnl.babel.visitor;

import gov.llnl.babel.BabelConfiguration;
import gov.llnl.babel.msg.MsgList;
import gov.llnl.babel.ast.ClassType;
import gov.llnl.babel.ast.ImplementsList;
import gov.llnl.babel.ast.InterfaceType;
import gov.llnl.babel.ast.Method;
import gov.llnl.babel.ast.ScopedID;
import java.util.ListIterator;
/**
 * Iterates over a AST a second time to decorate a primed SymbolTable
 * with all the details about classes, interfaces, and structs
 * 
 * Type resolution is largely performed in this stage.
 * 
 * @see gov.llnl.babel.visitor.SymbolTablePrimer
 * @author kumfert
 */
public class AddDefaultsVisitor  extends Visitor {
  
  private final static String SIDL_CLASS
    = "." + BabelConfiguration.getBaseClass();
  
  private final static String SIDL_INTERFACE 
    = "." + BabelConfiguration.getBaseInterface();
  
  private final static String SIDL_IMPLICIT_THROW 
    = "." + BabelConfiguration.getRuntimeException();

  protected MsgList d_msgs;

  public AddDefaultsVisitor(MsgList msgs) {
    super();
    d_msgs = msgs;
  }
  
  /** Add defaults at the Class Level.  
   *  1. If the class is SIDL_BASECLASS, implement SIDL_BASEINTERFACE
   *  2. If the class doesn't inherit anything, extend SIDL_BASECLASS
   *
   * TBD:
   * - Should defaults be assigned for class-level splicer blocks?
   *   What about C's "int ignore;" and F90's "integer :: placeholder"?
   * - If so, then how should the different kind and number of blocks 
   *   for each language be handled?  (For example, C has two splicer
   *   blocks --- _data and _includes --- in it's header while, ignoring
   *   method-specific blocks, Java has four --- _imports, _data, _load
   *   (for static), and _misc.)
   * - What is the most desirable way of handling default comments
   *   in splicer blocks given the different comment syntax for each
   *   language?
   */
  public Object visitClassType(ClassType node, Object data) { 
    if (node.getFQN().equals(SIDL_CLASS)) {
      if(node.getImplementsList().size() == 0) { 
        ImplementsList implementsList = node.getImplementsList();
        implementsList.add(new ScopedID(SIDL_INTERFACE));
      }
    } else if ( node.getExtends() == null ) {
      node.setExtends(new ScopedID(SIDL_CLASS));
    }
    /*
     * TBD...Do we want to load defaults into the AST?  If so, then we need
     *       to consider something like language-specific defaults visitors
     *       to deal with special needs and naming inconsistencies:
     *
    if ( (node.getSplicerList() == null) || node.getSplicerList().isEmpty() ) {
      node.addSplicerBlock(new SplicerBlock(null, node, "header", 
                                            "_includes"));
      node.addSplicerBlock(new SplicerBlock(null, node, "source", 
                                            "_includes"));
    }
     */

    Object result = super.visitClassType(node,data); //Visit children
    return result;
  }
  
  /** Add defaults as the interface level
   *  1. If it's not SIDL_BASEINTERFACE, and it has no extends, extend
   *     SIDL_BASEINTERFACE
   */
  public Object visitInterfaceType(InterfaceType node, Object data) { 
    if (!node.getFQN().equals(SIDL_INTERFACE) && node.getExtends().size() == 0) {
      node.getExtends().add(new ScopedID(SIDL_INTERFACE));
    }
    
    //Visit children
    Object result = super.visitInterfaceType(node,data);
    return result;    
  }


  /** Method level defaults
   *  1. Add implicit Runtime Exception to every throws list
   *  2. Add default splicer block contents --- at least for the
   *     method body.
   *
   * TBD:
   * - What about the default splicer block contents for each F90
   *   method's use?
   */
  public Object visitMethod(Method node, Object data) { 
    ListIterator i_thrws = node.getThrowsList().listiterator();
    boolean has_implicit_ex = false;
    for(; i_thrws.hasNext();) {
      ScopedID id = (ScopedID) i_thrws.next();
      if(id.toString().equals(SIDL_IMPLICIT_THROW)) {
        has_implicit_ex = true;
      }
    }
    if(!has_implicit_ex) {
      i_thrws.add(new ScopedID(SIDL_IMPLICIT_THROW));
    }

    /*
     * TBD...Do we want to load defaults into the AST?  If so, then we need
     *       to consider something like language-specific defaults visitors
     *       to deal with special needs and naming inconsistencies:
     *
    if ( (node.getSplicerList() == null) || node.getSplicerList().isEmpty() ) {
      node.addSplicerBlock(new SplicerBlock(null, node, "source", 
                                           node.getMethodName().getLongName()));
    }
     */
    Object result = super.visitMethod(node,data);  //Visit children
    return result;
  }
}
