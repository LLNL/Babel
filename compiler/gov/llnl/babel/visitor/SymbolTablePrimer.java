package gov.llnl.babel.visitor;

import gov.llnl.babel.Context;
import gov.llnl.babel.ast.AttributeList;
import gov.llnl.babel.ast.Attribute;
import gov.llnl.babel.ast.ClassType;
import gov.llnl.babel.ast.InterfaceType;
import gov.llnl.babel.ast.Enumeration;
import gov.llnl.babel.ast.EnumItem;
import gov.llnl.babel.ast.Package;
import gov.llnl.babel.ast.StructType;
import gov.llnl.babel.msg.MsgList;
import gov.llnl.babel.msg.UserMsg;
import java.util.HashMap;
import java.util.Iterator;

/**
 * This AST visitor does the first pass on initializing the older SymbolTable.
 * Empty Classes, Interfaces, (and eventually structs) will be added.  
 * Enums are also added, but since they make no reference to other types they
 * are entered in their entirety here. 
 * 
 * A second pass is needed later to add all the details about inheritance, 
 * methods, arguements, etc.  The reason for the two separate passes is to
 * make the type resolution more permissive than the original single-pass
 * model of the original parser and the existing SymbolTable.
 * 
 * @see gov.llnl.babel.visitor.SymbolTableDecorator
 * @author kumfert
 *
 */
public class SymbolTablePrimer extends Visitor {

  private static HashMap d_typeNameToValue = 
    new HashMap(gov.llnl.babel.symbols.Type.s_names.length);

  static {
    for(int i = 0; i < gov.llnl.babel.symbols.Type.s_names.length; ++i) {
      d_typeNameToValue.put(gov.llnl.babel.symbols.Type.s_names[i],
                            new gov.llnl.babel.symbols.Type(i));
    }
  }

  protected MsgList d_msgs;
  private Context d_context;
  
  public SymbolTablePrimer(MsgList msgs, Context context) {
    super();
    d_msgs = msgs;
    d_context = context;
  }
  
  public Object visitPackage(Package node, Object data) { 
    gov.llnl.babel.symbols.SymbolTable symbolTable = 
      d_context.getSymbolTable();
    String fqn = node.getFQN().substring(1);// substring removes leading dot
    gov.llnl.babel.symbols.Version version = null;
    if(node.getVersion() != null) {
      version = new 
        gov.llnl.babel.symbols.Version(node.getVersion().toString());
    } else {
      version = new 
        gov.llnl.babel.symbols.Version();
    }
    gov.llnl.babel.symbols.SymbolID id = new gov.llnl.babel.symbols.SymbolID(fqn, version);
    gov.llnl.babel.symbols.Comment comment = null; 
    if (node.getDocComment() != null ) { 
      comment = new gov.llnl.babel.symbols.Comment(node.getDocComment().toString().split("\n"));
    } else {
      comment = new gov.llnl.babel.symbols.Comment(null);
    }
    gov.llnl.babel.symbols.Symbol sym = symbolTable.lookupSymbol(id);
    gov.llnl.babel.symbols.Package pkg = null;
    if( sym != null && sym instanceof gov.llnl.babel.symbols.Package) {
      pkg = (gov.llnl.babel.symbols.Package) sym;
    } else {
      pkg = new gov.llnl.babel.symbols.Package(id,comment, d_context);
    }
    node.setSymbolTableEntry(pkg);
    pkg.setUserSpecified(node.getUserSelected());
    copyAttributes(node.getAttributeList(), pkg);

    try { 
      symbolTable.putSymbol(pkg);
      pkg.addMetadata("source-url", node.getFilename()); // TODO: Why does code die if this is commented?
    } catch ( gov.llnl.babel.symbols.SymbolRedefinitionException ex ) { 
      try {
        pkg = (gov.llnl.babel.symbols.Package)symbolTable.lookupSymbol(id);
        node.setSymbolTableEntry(pkg);
        pkg.setUserSpecified(node.getUserSelected());
        if (checkAttributes(node.getAttributeList(), pkg)) {
          d_msgs.addMsg( new UserMsg( UserMsg.ERROR, "symbol redefinition " + ex.getMessage(), node.getName()));
        }
      }
      catch (NullPointerException npe) {
        d_msgs.addMsg( new UserMsg( UserMsg.ERROR, "symbol redefinition " + ex.getMessage(), node.getName()));
      }
      catch (ClassCastException cce) { 
        d_msgs.addMsg( new UserMsg( UserMsg.ERROR, "symbol redefinition " + ex.getMessage(), node.getName()));
      }
    }
    //Add to parent package
    if(data instanceof gov.llnl.babel.symbols.Package) {
      gov.llnl.babel.symbols.Package p_pkg = (gov.llnl.babel.symbols.Package)data;
      p_pkg.addSymbol(pkg.getSymbolID(), gov.llnl.babel.symbols.Symbol.PACKAGE);
    }
    
    return super.visitPackage(node,pkg);  //Pass this package as the data
  }


  public Object visitInterfaceType(InterfaceType node, Object data) {
    gov.llnl.babel.symbols.Package pkg = (gov.llnl.babel.symbols.Package)data;
    String fqn = node.getFQN().substring(1); // substring removes leading dot;
    gov.llnl.babel.symbols.Version version = new 
      gov.llnl.babel.symbols.Version(node.getVersion().toString());
    gov.llnl.babel.symbols.SymbolID id = new gov.llnl.babel.symbols.SymbolID(fqn, version);
    gov.llnl.babel.symbols.Comment comment = null; 
    if (node.getDocComment() != null ) { 
      comment = new gov.llnl.babel.symbols.Comment(node.getDocComment().toString().split("\n"));
    } else {
      comment = new gov.llnl.babel.symbols.Comment(null);
    }
    
    gov.llnl.babel.symbols.Interface iface = new gov.llnl.babel.symbols.Interface(id,comment, d_context);
    node.setSymbolTableEntry(iface);
    iface.setUserSpecified(node.getUserSelected());
    try { 
      d_context.getSymbolTable().putSymbol(iface);
      pkg.addSymbol(iface.getSymbolID(), gov.llnl.babel.symbols.Symbol.INTERFACE);
    } catch (gov.llnl.babel.symbols.SymbolRedefinitionException ex) { 
      d_msgs.addMsg( new UserMsg( UserMsg.ERROR, "symbol redefinition " + ex.getMessage(), node.getName()));
    }
    // cls.addMetadata("babel-version",gov.llnl.babel.Version.VERSION);
    iface.addMetadata("source-url", node.getFilename()); // TODO: why does code die if this is commented?
    // cls.addMetadata("source-line", ""+node.getFirstToken().beginLine);
    // super.visitInterfaceType(node,data); // no need to recur below classes

    return data;
  }
  
  
  public Object visitClassType(ClassType node, Object data) {
    gov.llnl.babel.symbols.Package pkg = (gov.llnl.babel.symbols.Package)data;
    String fqn = node.getFQN().substring(1); // substring removes leading dot;
    gov.llnl.babel.symbols.Version version = new 
      gov.llnl.babel.symbols.Version(node.getVersion().toString());
    gov.llnl.babel.symbols.SymbolID id = new gov.llnl.babel.symbols.SymbolID(fqn, version);
    gov.llnl.babel.symbols.Comment comment = null; 
    if (node.getDocComment() != null ) { 
      comment = new gov.llnl.babel.symbols.Comment(node.getDocComment().toString().split("\n"));
    } else {
      comment = new gov.llnl.babel.symbols.Comment(null);
    }
    gov.llnl.babel.symbols.Class cls = new gov.llnl.babel.symbols.Class(id,comment, d_context);
    node.setSymbolTableEntry(cls);
    cls.setUserSpecified(node.getUserSelected());
    try { 
      d_context.getSymbolTable().putSymbol(cls);
      pkg.addSymbol(cls.getSymbolID(), gov.llnl.babel.symbols.Symbol.CLASS);
    } catch (gov.llnl.babel.symbols.SymbolRedefinitionException ex) { 
      d_msgs.addMsg( new UserMsg( UserMsg.ERROR, "symbol redefinition " + ex.getMessage(), node.getName()));
    }
    // cls.addMetadata("babel-version",gov.llnl.babel.Version.VERSION);
    cls.addMetadata("source-url", node.getFilename()); // TODO: Why does code die if this is commented?
    // cls.addMetadata("source-line", ""+node.getFirstToken().beginLine);
    // super.visitClassType(node,data); // no need to recur below classes

    return data;
  }

  public Object visitStructType(StructType node, Object data)
  {
    gov.llnl.babel.symbols.Package pkg = (gov.llnl.babel.symbols.Package)data;
    String fqn = node.getFQN().substring(1); // substring removes leading
                                             // dot;
    gov.llnl.babel.symbols.Version version = new
      gov.llnl.babel.symbols.Version(node.getVersion().toString());
    gov.llnl.babel.symbols.SymbolID id = new 
      gov.llnl.babel.symbols.SymbolID(fqn, version);
    gov.llnl.babel.symbols.Comment comment;
    if (node.getDocComment() != null ) {
      comment = new gov.llnl.babel.symbols.Comment(node.getDocComment().toString().split("\n"));
    }
    else {
      comment = new gov.llnl.babel.symbols.Comment(null);
    }
    gov.llnl.babel.symbols.Struct s = new gov.llnl.babel.symbols.Struct(id, comment, d_context);
    node.setSymbolTableEntry(s);
    s.setUserSpecified(node.getUserSelected());
    try {
      d_context.getSymbolTable().putSymbol(s);
      pkg.addSymbol(s.getSymbolID(), gov.llnl.babel.symbols.Symbol.STRUCT);
    }
    catch (gov.llnl.babel.symbols.SymbolRedefinitionException ex) {
      d_msgs.addMsg( new UserMsg( UserMsg.ERROR, "symbol redefinition " + ex.getMessage(), 
                                  node.getName()));
    }
    s.addMetadata("source-url", node.getFilename()); // TODO: Why does code
                                                     // die if this is
                                                     // commented?
    return s;
  }

  static boolean checkAttributes(AttributeList al,
                                 gov.llnl.babel.symbols.Attributes a)
  {
    try {
      // ensure that all attributes in al are in a
      Iterator i = al.iterator();
      while (i.hasNext()) {
        Attribute attrib = (Attribute)i.next();
        final String value = attrib.getValue();
        final String comp = a.getAttribute(attrib.getKey());
        if (value != null) {
          if (!value.equals(comp)) return true;
        }
        else {
          if (comp != null) return true;
        }
      }
      // ensure that all attributes in a are in al
      i = a.getAttributes().iterator();
      while (i.hasNext()) {
        final String key = (String)i.next();
        Attribute attrib = al.getAttribute(key);
        if (attrib == null) return true;
        final String value = attrib.getValue();
        final String comp = a.getAttribute(key);
        if (value != null) {
          if (!value.equals(comp)) return true;
        }
        else {
          if (comp != null) return true;
        }
      }
    }
    catch (gov.llnl.babel.symbols.UnknownAttributeException uae) {
      return true;
    }
    return false;
  }

  static void copyAttributes(AttributeList al,
                             gov.llnl.babel.symbols.Attributes a)
  {
    Iterator i = al.iterator();
    while (i.hasNext()) {
      Attribute attrib = (Attribute)i.next();
      a.setAttribute(attrib.getKey(), attrib.getValue());
    }
  }

  public Object visitEnumeration(Enumeration node, Object data) {
    gov.llnl.babel.symbols.Package pkg = (gov.llnl.babel.symbols.Package)data;
    String fqn = node.getFQN().substring(1); // substring removes leading dot;
    gov.llnl.babel.symbols.Version version = new 
      gov.llnl.babel.symbols.Version(node.getVersion().toString());
    gov.llnl.babel.symbols.SymbolID id = new gov.llnl.babel.symbols.SymbolID(fqn, version);
    gov.llnl.babel.symbols.Comment comment = null; 
    if (node.getDocComment() != null ) { 
      comment = new gov.llnl.babel.symbols.Comment(node.getDocComment().toString().split("\n"));
    } else {
      comment = new gov.llnl.babel.symbols.Comment(null);
    }
    gov.llnl.babel.symbols.Enumeration e = new gov.llnl.babel.symbols.Enumeration(id,comment, d_context);
    copyAttributes(node.getAttributeList(), e);
    node.setSymbolTableEntry(e);
    e.setUserSpecified(node.getUserSelected());
    try { 
      d_context.getSymbolTable().putSymbol(e);
      pkg.addSymbol(e.getSymbolID(), gov.llnl.babel.symbols.Symbol.ENUM);
    } catch (gov.llnl.babel.symbols.SymbolRedefinitionException ex) { 
      d_msgs.addMsg( new UserMsg( UserMsg.ERROR, "symbol redefinition " + ex.getMessage(), node.getName()));
    }
    // e.addMetadata("babel-version",gov.llnl.babel.Version.VERSION);
    // e.addMetadata("source-url", node.getFilename());
    // e.addMetadata("source-line", ""+node.getFirstToken().beginLine);
    super.visitEnumeration(node, e); // no need to recur below classes
    return data;
  }
  
  public Object visitEnumItem(EnumItem node, Object data) {
    gov.llnl.babel.symbols.Enumeration e = (gov.llnl.babel.symbols.Enumeration) data;
    gov.llnl.babel.symbols.Comment comment = new gov.llnl.babel.symbols.Comment(null);
    
    e.addEnumerator(node.getName().toString(), node.getValue(), node.isUserValue(), comment);
    return null;
  }
}
