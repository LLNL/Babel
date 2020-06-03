//
// File: ParseTree2ASTVisitor
// Package:
// Copyright: (c) 2005 The Lawrence Livermore National Security, LLC
// Release: $Name$
// Revision: @(#) $Id: ParseTree2ASTVisitor.java 6951 2010-09-28 15:45:31Z adrian $
// Description:

package gov.llnl.babel.parsers.sidl2;

import gov.llnl.babel.ast.ASTNode;
import gov.llnl.babel.ast.Argument;
import gov.llnl.babel.ast.ArgumentList;
import gov.llnl.babel.ast.ArrayType;
import gov.llnl.babel.ast.Attribute;
import gov.llnl.babel.ast.AttributeList;
import gov.llnl.babel.ast.ClassType;
import gov.llnl.babel.ast.EnumItem;
import gov.llnl.babel.ast.Enumeration;
import gov.llnl.babel.ast.Extendable;
import gov.llnl.babel.ast.ExtendsList;
import gov.llnl.babel.ast.Ensures;
import gov.llnl.babel.ast.Extents;
import gov.llnl.babel.ast.FixedType;
import gov.llnl.babel.ast.FromClause;
import gov.llnl.babel.ast.IAttributable;
import gov.llnl.babel.ast.INameable;
import gov.llnl.babel.ast.ImplementsList;
import gov.llnl.babel.ast.ImportClause;
import gov.llnl.babel.ast.IntLiteral;
import gov.llnl.babel.ast.InterfaceType;
import gov.llnl.babel.ast.Invariants;
import gov.llnl.babel.ast.Method;
import gov.llnl.babel.ast.MethodList;
import gov.llnl.babel.ast.MethodName;
import gov.llnl.babel.ast.Name;
import gov.llnl.babel.ast.NamedType;
import gov.llnl.babel.ast.Package;
import gov.llnl.babel.ast.RArrayType;
import gov.llnl.babel.ast.RequireClause;
import gov.llnl.babel.ast.Requires;
import gov.llnl.babel.ast.SIDLFile;
import gov.llnl.babel.ast.ScopedID;
import gov.llnl.babel.ast.ScopedIDList;
import gov.llnl.babel.ast.StructItem;
import gov.llnl.babel.ast.StructType;
import gov.llnl.babel.ast.ThrowsList;
import gov.llnl.babel.ast.Type;
import gov.llnl.babel.ast.TypeSearchClause;
import gov.llnl.babel.ast.Version;
import gov.llnl.babel.msg.MsgList;
import gov.llnl.babel.msg.UserMsg;


import java.util.ArrayList;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Iterator;

/**
 * This class turns the JJTree/JavaCC generated parse tree into a bona fide AST.
 * The output AST can then be traversed for syntax checking, cloning,
 * modification, and (eventually) better code generators.
 */
public class ParseTree2ASTVisitor implements SIDLParserVisitor {

  protected Stack d_prefixStack; // items are pushed on from above, typically popped in same context
                                 // children often peek, but never pop
  
  protected Stack d_postfixStack; // items are pushed on by children as return values to parents

  protected LinkedList d_attrs;
  
  protected MsgList d_msgs;

  protected void checkAttrs( String typeName, IAttributable node, Object data, String legal_attrs[] ) { 
    if (data != null && data instanceof AttributeList ) { 
      AttributeList attrs = (AttributeList) data;  
      for (Iterator it = attrs.iterator(); it.hasNext(); ) {
        Attribute a = (Attribute) it.next();
        boolean allowable = false;
        a.setParent( (ASTNode) node); 
        if ( a.isBuiltin() ) { 
          for( int i=0; i<legal_attrs.length; ++i) { 
            if ( a.getKey().equals(legal_attrs[i]) ) { 
              allowable = true;
              break;
            }
          }
        } else { 
          allowable = true;
        }
        if (allowable) {
          node.getAttributeList().addAttribute(a);
        } else {
          d_msgs.addMsg( new UserMsg( UserMsg.WARNING, 
              typeName +" cannot be " + a.getKey() + ", ignoring attribute.", a ));
        }
      }
    }
  }
  
  private boolean checkVersionedPackage(String typeName, NamedType t ) {
    if (t.getParent() instanceof Package) { 
      Package parent = (Package) t.getParent(); 
      if ( parent.getVersion() == null ) {  
        d_msgs.addMsg(new UserMsg(UserMsg.ERROR, typeName + 
              " must appear inside a versioned package.", t ));         
        return false;
      }
    } else { 
      d_msgs.addMsg(new UserMsg(UserMsg.ERROR, typeName + " must appear inside a package.", t));
      return false;
    }
    return true;
  }

  public ParseTree2ASTVisitor(MsgList msgs) {
    d_prefixStack = new Stack();
    d_postfixStack = new Stack();
    d_msgs = msgs;
  }

  private void checkScopedID(String name, ASTNode node)
  {
    if (name.indexOf('[') >= 0) {
      d_msgs.
        addMsg(new UserMsg
               (UserMsg.ERROR, 
                "This name \"" + name + 
                "\" has a method extension, and method extensions are only allowed in a from clause.", 
                node));
    }
  }

  public Object visit(ParseTreeNode src, Object data) {
    Object retval = null;
    switch (src.getID()) {

    case SIDLParserTreeConstants.JJTSTART:
      {
        SIDLFile f = new SIDLFile(src, src.name);
        LinkedList attrs = new LinkedList();
        d_prefixStack.push(f);
        src.childrenAccept(this, attrs);
        retval = d_prefixStack.pop();
        while( d_postfixStack.size() > 0) {
          System.err.println("Internal Error:  postfixStack has lingering item(s)" + d_postfixStack.pop());
        }
      }
      break;

    case SIDLParserTreeConstants.JJTREQUIRE:
      {
        SIDLFile parent = (SIDLFile) d_prefixStack.peek();
        RequireClause c = new RequireClause(src, parent, null, null);
        parent.appendTypeSearchClause(c);
        d_prefixStack.push(c);
        retval = src.childrenAccept(this, data);
        d_prefixStack.pop();
        if ( d_postfixStack.peek() instanceof ScopedID ) { 
          c.setScopedID((ScopedID) d_postfixStack.pop());
        }
      }
      break;

    case SIDLParserTreeConstants.JJTIMPORT:
      {
        SIDLFile parent = (SIDLFile) d_prefixStack.peek();
        ImportClause c = new ImportClause(src, parent, null, null);
        parent.appendTypeSearchClause(c);
        d_prefixStack.push(c);
        retval = src.childrenAccept(this, data);
        d_prefixStack.pop();
        if ( d_postfixStack.peek() instanceof ScopedID ) { 
          c.setScopedID((ScopedID) d_postfixStack.pop());
        }
        if(c.getVersion() != null) {
          RequireClause rc = new RequireClause(src, parent, c.getScopedID(), c.getVersion());
          parent.appendTypeSearchClause(rc);
        }
      }
      break;

    case SIDLParserTreeConstants.JJTNAME:
      {
        INameable t = (INameable) d_prefixStack.peek();
        t.setName(new Name(src, (ASTNode) t));
        src.childrenAccept(this, data);
      }
      break;
      
    case SIDLParserTreeConstants.JJTPACKAGE:
      {
        Package p;
        Object obj = d_prefixStack.peek();
        if (obj instanceof Package) {
          Package parent = (Package) obj;
          p = new Package(src, parent, null, null);
          parent.appendNamedType(p);
        } else { // if ( obj instanceof SIDLFile ) {
          SIDLFile file = (SIDLFile) obj;
          p = new Package(src, file, null, null);
          file.appendPackage(p);
        }
        String legal_attrs[] = {"final"};
        AttributeList a = (AttributeList) data;

        //p.setDocComment(resolveDoc(src));

        a.setParent(p);
        checkAttrs( "Packages", p, a, legal_attrs);
        d_prefixStack.push(p);
        retval = src.childrenAccept(this, data);
        d_prefixStack.pop();
      }
      break;

    case SIDLParserTreeConstants.JJTSCOPEDID:
      {
        Object obj = d_prefixStack.peek();

// Check for scoped ID's that are out Modes.  Save the name.  We will
// check later to see if the name matches any structs with rarrays.
      if ((obj instanceof Argument) && ("out" == ((Argument)obj).getMode())) {
        Argument id = (Argument) obj;
        id.setName2(new Name(src, (ASTNode) id));
      }

        if (obj instanceof Package) {
          Package p = (Package) obj;
          Name pn = new Name(src);
          p.setName(pn);
          checkScopedID(src.name, pn);
        } else if (obj instanceof Extendable ) { 
          ScopedIDList list = (ScopedIDList) data; // extends list or implements list
          ScopedID id = new ScopedID(src, (ASTNode) obj);
          list.add(id);
          checkScopedID(src.name, id);

        } else if (obj instanceof Method ) { 
          Method m = (Method) obj;
          ScopedID id = new ScopedID(src,m);
          //save return type names that are scoped IDs
          m.setName2(new Name(src, (ASTNode) id));
          m.setReturnType(id);
          checkScopedID(src.name, id);

        } else if (obj instanceof ThrowsList ) { 
          ThrowsList tl = (ThrowsList) obj;
          ScopedID id = new ScopedID(src,tl);
          tl.addException(id);
          checkScopedID(src.name, id);

        } else if (obj instanceof FromClause) { 
          FromClause f = (FromClause) obj;
          ScopedID id = new ScopedID(src,f);
          f.setScopedID(id);
          if (src.name.indexOf('[') >= 0) {
            d_msgs.addMsg(new UserMsg(UserMsg.INTERNAL_ERROR,
                                      "This from clause name \"" + src.name +
                                      "\" wasn't converted into a method name with extension.",
                                      id));
          }
        } else { //Import and require statements, object array types, anything else?
          d_postfixStack.push( new ScopedID(src, (ASTNode) obj) );
        }
      }
      break;

    case SIDLParserTreeConstants.JJTVERSION:
      {
        Version v = new Version(src.name);
        Object obj = d_prefixStack.peek();
        if (obj instanceof Package) {
          Package p = (Package) obj;
          p.setVersion(v);
        } else if (obj instanceof TypeSearchClause) {
          TypeSearchClause ts = (TypeSearchClause) obj;
          ts.setVersion(v);
        }
      }
      break;

    case SIDLParserTreeConstants.JJTUSERTYPE:
      {
        AttributeList attrs = new AttributeList(null,null);
        retval = src.childrenAccept(this, attrs);
      }
      break;

    case SIDLParserTreeConstants.JJTTYPEATTRS:
      retval = src.childrenAccept(this, data);
      break;

    case SIDLParserTreeConstants.JJTTYPEATTR:
    case SIDLParserTreeConstants.JJTCUSTOMATTR:
      {
        AttributeList attrs = (AttributeList) data;
        attrs.addAttribute(new Attribute(src, attrs));
        retval = src.childrenAccept(this, data);
      }
      break;

    case SIDLParserTreeConstants.JJTENUM:
      {
        ASTNode parent = (ASTNode) d_prefixStack.peek();
        Enumeration e = new Enumeration(src, parent, null);
        if ( checkVersionedPackage("Enum", e) ) { 
          String legal_attrs[] = {};
          AttributeList a = (AttributeList) data;
          a.setParent(e);
          checkAttrs( "Enums", e, a, legal_attrs);
          d_prefixStack.push(e);
          retval = src.childrenAccept(this, data);
          d_prefixStack.pop();
          ((Package) parent).appendNamedType(e);
        }
      }
      break;

    case SIDLParserTreeConstants.JJTENUMERATOR:
      {
        Enumeration e = (Enumeration) d_prefixStack.peek();
        EnumItem i = new EnumItem(src, e);
        e.addEnumItem(i);
        d_prefixStack.push(i);
        retval = src.childrenAccept(this, data);
        d_prefixStack.pop();
      }
      break;


    case SIDLParserTreeConstants.JJTSTRUCT:
      { 
        ASTNode parent = (ASTNode) d_prefixStack.peek();
        StructType s = new StructType(src, parent, null);
        if ( checkVersionedPackage("Struct", s) ) { 
          String legal_attrs[] = {};
          AttributeList a = (AttributeList) data;
          a.setParent(s);
          checkAttrs( "Structs", s, a, legal_attrs);
          d_prefixStack.push(s);
          retval = src.childrenAccept(this, data);
          d_prefixStack.pop();
          ((Package) parent).appendNamedType(s);
        }
      }
      break;
      
    case SIDLParserTreeConstants.JJTSTRUCTITEM:
      {
        StructType s = (StructType) d_prefixStack.peek();
        StructItem i = new StructItem(src, s);
        s.addStructItem(i);
        d_prefixStack.push(i);
        retval = src.childrenAccept(this, data);
        d_prefixStack.pop();
        if (d_postfixStack.size() > 0 ) { 
          if (d_postfixStack.peek() instanceof Type ) { 
            Type t = (Type) d_postfixStack.pop();
            i.setType(t);
            t.setParent(i); 
          }
        }
      }
      break;
      
    case SIDLParserTreeConstants.JJTINTEGER:
      {
        ASTNode obj = (ASTNode) d_prefixStack.peek();
        IntLiteral ilit = new IntLiteral( src, obj );
        if (obj instanceof EnumItem) {
          EnumItem e = (EnumItem) obj;
          e.setExplicitValue(ilit);
          if (ilit.isLong()) { 
            d_msgs.addMsg( new UserMsg( UserMsg.WARNING, 
                  "Long int literals not supported in enums, dropping L|l suffix.", e.getExplicitValue()));  
          }
        }
      }
      break;

    case SIDLParserTreeConstants.JJTINTERFACE:
      { 
        ASTNode parent = (ASTNode) d_prefixStack.peek();
        InterfaceType iface = new InterfaceType( src, parent, null);
        if ( checkVersionedPackage("Interface", iface) ) { 
          ((Package) parent).appendNamedType(iface);
          AttributeList a = (AttributeList) data;
          a.setParent(iface);
          String legal_attrs[] = {};
          checkAttrs("Interfaces", iface, a, legal_attrs);
          d_prefixStack.push(iface);
          retval = src.childrenAccept(this, data);
          d_prefixStack.pop();
        }
      }
      break;

    case SIDLParserTreeConstants.JJTCLASS:
    { 
      ASTNode parent = (ASTNode) d_prefixStack.peek();

      ClassType cls = new ClassType( src, parent, null);
      if ( checkVersionedPackage("Class", cls) ) { 
        ((Package) parent).appendNamedType(cls);
        AttributeList a = (AttributeList) data;
        a.setParent(cls);
        String legal_attrs[] = {"abstract"};
        checkAttrs("Classes", cls, data, legal_attrs);
        d_prefixStack.push(cls);
        retval = src.childrenAccept(this, data);
        d_prefixStack.pop();
      }
    }
    break;
       
    case SIDLParserTreeConstants.JJTEXTENDSLIST:
      { 
        InterfaceType iface = (InterfaceType) d_prefixStack.peek();
        ExtendsList extendsList = iface.getExtends();
        retval = src.childrenAccept(this, extendsList);
      }
      break;
      

    case SIDLParserTreeConstants.JJTEXTENDSONE:
      { 
        //Stupid hack around that the scopedID visitor expects a ScopedID list in data
        ExtendsList extendsOne = new ExtendsList(src, (ASTNode)d_prefixStack.peek());
        retval = src.childrenAccept(this, extendsOne);
        ClassType cls = (ClassType) d_prefixStack.peek();
        Iterator it = extendsOne.getList().iterator();
        cls.setExtends( (ScopedID) it.next() );
        while ( it.hasNext() ) {
          d_msgs.addMsg(new UserMsg(UserMsg.ERROR,"Classes can extend at most one class", (ScopedID) it.next()));
        }
      }
      break;
      
    case SIDLParserTreeConstants.JJTIMPLEMENTSLIST:
      { 
        ClassType parent = (ClassType) d_prefixStack.peek();
        if ( parent.getImplementsList() == null ) { 
          ImplementsList implementsList = new ImplementsList(src,parent);
          parent.setImplementsList( implementsList );
        }
        retval = src.childrenAccept(this, parent.getImplementsList());
      }
      break;
    
      
    case SIDLParserTreeConstants.JJTIMPLEMENTSALLLIST:
      {
        ClassType parent = (ClassType) d_prefixStack.peek();
        // If the clastype has no implements list, create one
        if ( parent.getImplementsList() == null ) { 
          ImplementsList implementsList = new ImplementsList(src,parent);
          parent.setImplementsList( implementsList );
        }
        //Create a temporary implementslist to get all the implements-all in one place
        ImplementsList tmplist = new ImplementsList(src,parent);
        retval = src.childrenAccept(this, tmplist);
        //Add each interface in the tmp list to the implements-all list (which puts
        //the interface in both the implements and implements all list)
        for(Iterator it = tmplist.iterator(); it.hasNext(); ) { 
          parent.getImplementsList().addImplementsAll( (ScopedID) it.next());
       }
      }
      break;
      
    case SIDLParserTreeConstants.JJTMETHOD:
      {
        Extendable t = (Extendable) d_prefixStack.peek();
        MethodList parent = t.getMethodList();
        Method m = new Method(src,parent);
        parent.addMethod(m);
        AttributeList attrs = m.getAttributeList();
        attrs.setParent(m);
        d_prefixStack.push(m);
        retval = src.childrenAccept(this, attrs);
        d_prefixStack.pop();
      }
      break;
      
    case SIDLParserTreeConstants.JJTMETHODNAME:
      {
        ASTNode node = (ASTNode) d_prefixStack.peek();
        MethodName name = new MethodName(src,node);
        if ( node instanceof Method ) {
          Method m = (Method) node;
          m.setMethodName(name);
        } else if (node instanceof FromClause ) { 
          FromClause f = (FromClause) node;
          f.setMethodName(name);
        }
        d_prefixStack.push(name);
        retval = src.childrenAccept(this,data);
        d_prefixStack.pop();
      }
      break;
      
    case SIDLParserTreeConstants.JJTMETHODATTRS:
      retval = src.childrenAccept(this, data);
      break;

    case SIDLParserTreeConstants.JJTMETHODATTR:
      {
        AttributeList attrs = (AttributeList) data;
        attrs.addAttribute(new Attribute(src, attrs));
        retval = src.childrenAccept(this, data);
      }
      break;
      
    case SIDLParserTreeConstants.JJTRETURNTYPE:
      {
        src.childrenAccept(this, data); 
        if (d_postfixStack.size() > 0 ) { 
          if (d_postfixStack.peek() instanceof Type ) { 
            Type t = (Type) d_postfixStack.pop();
            Method m = (Method) d_prefixStack.peek();
            m.setReturnType(t);
            t.setParent(m); 
          }
        }
      }
      break;

    case SIDLParserTreeConstants.JJTSHORTNAME:
      { 
        MethodName m = (MethodName) d_prefixStack.peek();
        m.setShortName(new Name(src,m));
      }
      break;   
      
    case SIDLParserTreeConstants.JJTEXTENSION:
      {
        MethodName m = (MethodName) d_prefixStack.peek();
        m.setNameExtension(new Name(src, m));
      }
      break;
      
    case SIDLParserTreeConstants.JJTARGLIST:
      {
        retval = src.childrenAccept(this,data);
      }
      break;
      
    case SIDLParserTreeConstants.JJTARG:
      {
        Method m = (Method) d_prefixStack.peek();
        ArgumentList al = m.getArgumentList();
        Argument arg = new Argument(src,al);
        al.addArgument(arg);
        d_prefixStack.push(arg);
        AttributeList attrs = arg.getAttributeList();
        attrs.setParent(arg);
        retval = src.childrenAccept(this,attrs);
        d_prefixStack.pop();
        if (d_postfixStack.peek() instanceof Type ) { 
          arg.setType( (Type) d_postfixStack.pop() );
        }
      }
      break;
    
    case SIDLParserTreeConstants.JJTARGATTRS:
      {
        //AttributeList attrs = new AttributeList(null,(Argument)d_prefixStack.peek());
        retval = src.childrenAccept(this, data);
      }
      break;
      
    case SIDLParserTreeConstants.JJTARGATTR:
      {
        AttributeList attrs = (AttributeList) data;
        attrs.addAttribute(new Attribute(src, attrs));
        retval = src.childrenAccept(this, data);
      }
      break;
      
    case SIDLParserTreeConstants.JJTMODE:
      {
        Argument arg = (Argument) d_prefixStack.peek();
        arg.setMode(src.name);
      }
      break;

    case SIDLParserTreeConstants.JJTARRAY:
      { 
        ASTNode parent = (ASTNode) d_prefixStack.peek();
        ArrayType a = new ArrayType(src,parent);
        d_prefixStack.push(a);
        retval = src.childrenAccept(this, data);
        d_prefixStack.pop();
        if(d_postfixStack.empty() || !(d_postfixStack.peek() instanceof Type)) {
          //Generic Array
          a.setScalarType( (Type) null );
        } else if (d_postfixStack.peek() instanceof Type ) { 
          a.setScalarType( (Type) d_postfixStack.pop() );
        }
        d_postfixStack.push(a);
      }
      break;

    case SIDLParserTreeConstants.JJTRARRAY:
    { 
      ASTNode parent = (ASTNode) d_prefixStack.peek();
      if ((parent instanceof Argument) && ("out" == ((Argument)parent).getMode())) {
          d_msgs.addMsg(new UserMsg(UserMsg.ERROR, "Raw Arrays cannot be \"out\" PArameters", parent));
      }
      RArrayType a = new RArrayType(src,parent);
      d_prefixStack.push(a);
      retval = src.childrenAccept(this, data);
      d_prefixStack.pop();
      
      if (d_postfixStack.peek() instanceof FixedType ) { 
        a.setScalarType( (FixedType) d_postfixStack.pop() );
      }
      d_postfixStack.push(a);
    }
    break;
      
    case SIDLParserTreeConstants.JJTSCALARTYPE:
      { 
        retval=src.childrenAccept(this,data);
      }
      break;
      

    case SIDLParserTreeConstants.JJTDIMENSION:
      {
        ASTNode obj = (ASTNode) d_prefixStack.peek();
        IntLiteral ilit = new IntLiteral( src, obj );
        if ( obj instanceof ArrayType ) { 
          ArrayType t = (ArrayType) obj;
          if ( ilit.isLong()) { 
            d_msgs.addMsg(new UserMsg(UserMsg.WARNING,
                    "Long int literals are not supported in array dimension", ilit));            
          }
          int dim = ilit.getInt();
          if ( dim < 1 || dim > 7) { 
            d_msgs.addMsg(new UserMsg(UserMsg.ERROR,
                "Array dimension must be between 1 and 8 (inclusive)", ilit));
          }
          t.setDimension( ilit.getInt() );
        }
      }
      break;
      
    case SIDLParserTreeConstants.JJTORIENTATION:
      {
        ArrayType a = (ArrayType) d_prefixStack.peek();
        if (src.name == "row-major") { 
          a.setOrientation('r');
        } else if (src.name == "column-major") { 
          a.setOrientation('c');
        } else { 
        }
      }
      break;
      

    case SIDLParserTreeConstants.JJTEXTENTS:
      {
        RArrayType a = (RArrayType) d_prefixStack.peek();
        Extents ext = new Extents(src,a);
        a.setExtents(ext);
        d_prefixStack.push(ext);
        retval = src.childrenAccept(this,data);
        d_prefixStack.pop();
      }
      break;
      
    case SIDLParserTreeConstants.JJTEXTENT:
      Extents e = (Extents) d_prefixStack.peek();
      e.addExtent((ASTNode)(src.jjtAccept(new Expr2ASTVisitor(), e)));
      break;
      
    case SIDLParserTreeConstants.JJTPRIMATIVETYPE:
      {
        ASTNode parent = (ASTNode) d_prefixStack.peek();
        d_postfixStack.push(new FixedType( src, parent, src.name ));
      }
      break;


    case SIDLParserTreeConstants.JJTEXCEPTCLAUSE:
      {
        Method m = (Method) d_prefixStack.peek();
        ThrowsList tl = m.getThrowsList();
        d_prefixStack.push(tl);
        retval = src.childrenAccept(this,data);
        d_prefixStack.pop();
      }
      break;
    

    case SIDLParserTreeConstants.JJTFROMCLAUSE:
      { 
        Method m = (Method) d_prefixStack.peek();
        FromClause f = new FromClause(src,m);
        m.setFromClause(f);
        d_prefixStack.push(f);
        retval = src.childrenAccept(this, data);
        d_prefixStack.pop();
      }
      break;
      
    case SIDLParserTreeConstants.JJTENSURES:
      {
        Method m = (Method) d_prefixStack.peek();
        final int numChildren = src.jjtGetNumChildren();
        ArrayList exprs = new ArrayList(numChildren);
        Ensures ensures = new Ensures(src, m);
        for(int i = 0; i < numChildren; ++i) {
          exprs.add(src.jjtGetChild(i).jjtAccept(new Expr2ASTVisitor(),
                                                 ensures));
        }
        ensures.setEnsureExprs(exprs);
        m.setEnsures(ensures);
      }
      break;
    case SIDLParserTreeConstants.JJTREQUIRES:
      {
        Method m = (Method) d_prefixStack.peek();
        final int numChildren = src.jjtGetNumChildren();
        ArrayList exprs = new ArrayList(numChildren);
        Requires requires = new Requires(src, m);
        for(int i = 0; i < numChildren; ++i) {
          exprs.add(src.jjtGetChild(i).jjtAccept(new Expr2ASTVisitor(),
                                                 requires));
        }
        requires.setRequireExprs(exprs);
        m.setRequires(requires);
      }
      break;
    case SIDLParserTreeConstants.JJTINVARIANTS:
      {
        Extendable extendable = (Extendable) d_prefixStack.peek();
        final int numChildren = src.jjtGetNumChildren();
        ArrayList exprs = new ArrayList(numChildren);
        Invariants invariants = new Invariants( src, extendable );
        for(int i = 0; i < numChildren; ++i) {
          exprs.add(src.jjtGetChild(i).jjtAccept(new Expr2ASTVisitor(),
                                                 invariants));
        }
        invariants.setInvariantExprs(exprs);
	extendable.setInvariants(invariants);
      }
      break;
    case SIDLParserTreeConstants.JJTASSERTION:
    case SIDLParserTreeConstants.JJTADD:
    case SIDLParserTreeConstants.JJTMULT:
    case SIDLParserTreeConstants.JJTNUMBER:
    case SIDLParserTreeConstants.JJTBITWISE:
    case SIDLParserTreeConstants.JJTSHIFT:
    case SIDLParserTreeConstants.JJTGTLT:
    case SIDLParserTreeConstants.JJTAND:
    case SIDLParserTreeConstants.JJTOR:
    case SIDLParserTreeConstants.JJTEQUALITY:
    case SIDLParserTreeConstants.JJTCOMPLEX:
    case SIDLParserTreeConstants.JJTLITERAL:
    case SIDLParserTreeConstants.JJTFUNCARGS:
    case SIDLParserTreeConstants.JJTUNARY:
    case SIDLParserTreeConstants.JJTFUNC:
    case SIDLParserTreeConstants.JJTPOWER:
    case SIDLParserTreeConstants.JJTIMPLICATION:
      retval = src.childrenAccept(this, data);
      break;
    default:
      System.out.println(src + ": acceptor unimplemented in subclass?");
    }
    return retval;
  }
  
  /**
   * Resolve the doc string from the special tokens preceding the node
   * 
   * @param node
   * @return
   */
  protected String resolveDoc( ParseTreeNode node ) { 
    Token t = node.getFirstToken();
    if ( t != null ) { 
      Token tt = t.specialToken;
      if ( tt != null ) { 
        while( tt.specialToken != null ) { 
          tt = tt.specialToken;
          if ( tt.kind == SIDLParserConstants.DOC_COMMENT ) { 
            return tt.image;
          }
        }
      }
    }
    return "";
  }

}
