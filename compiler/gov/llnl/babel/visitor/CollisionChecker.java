package gov.llnl.babel.visitor;

import gov.llnl.babel.ast.ASTNode;
import gov.llnl.babel.ast.Argument;
import gov.llnl.babel.ast.ArgumentList;
import gov.llnl.babel.ast.Attribute;
import gov.llnl.babel.ast.AttributeList;
import gov.llnl.babel.ast.BinaryExpr;
import gov.llnl.babel.ast.BooleanLiteral;
import gov.llnl.babel.ast.CharacterLiteral;
import gov.llnl.babel.ast.ClassType;
import gov.llnl.babel.ast.DComplexLiteral;
import gov.llnl.babel.ast.DoubleLiteral;
import gov.llnl.babel.ast.EnumItem;
import gov.llnl.babel.ast.Enumeration;
import gov.llnl.babel.ast.Extents;
import gov.llnl.babel.ast.FComplexLiteral;
import gov.llnl.babel.ast.FixedType;
import gov.llnl.babel.ast.FloatLiteral;
import gov.llnl.babel.ast.FromClause;
import gov.llnl.babel.ast.ImportClause;
import gov.llnl.babel.ast.IntLiteral;
import gov.llnl.babel.ast.InterfaceType;
import gov.llnl.babel.ast.Method;
import gov.llnl.babel.ast.MethodList;
import gov.llnl.babel.ast.MethodName;
import gov.llnl.babel.ast.Name;
import gov.llnl.babel.ast.Package;
import gov.llnl.babel.ast.RArrayType;
import gov.llnl.babel.ast.RequireClause;
import gov.llnl.babel.ast.SIDLFile;
import gov.llnl.babel.ast.ScopedID;
import gov.llnl.babel.ast.StringLiteral;
import gov.llnl.babel.ast.StructItem;
import gov.llnl.babel.ast.StructType;
import gov.llnl.babel.ast.ThrowsList;
import gov.llnl.babel.ast.Type;
import gov.llnl.babel.ast.UnaryExpr;
import gov.llnl.babel.msg.MsgList;
import gov.llnl.babel.msg.UserMsg2;
import gov.llnl.babel.msg.UserMsg;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Checks for naming collisions two args with same name in same method, 
 * two Types with same name in a packgage, and so on for all names in 
 * the ast.  For enumerations, it also checks for duplicate explicit values
 * and assigns unique values to items otherwise unassigned.  For packages, it
 * also blocks attempts to reopen a final package.  Naturally, it will detect
 * collisions for packages that are closed and reopened.
 * 
 * @author kumfert
 *
 */
public class CollisionChecker extends Visitor {

  private static HashSet s_reservedExtensions = new HashSet();
    
  private static HashMap s_reservedWords = new HashMap();

  protected MsgList d_msgs;
  
  private boolean d_hasRArrays;

  static {                      // static initializer block
    s_reservedExtensions.add("_f"); // FORTRAN 77 stub suffix
    s_reservedExtensions.add("_m"); // FORTRAN 90 stub suffix
    s_reservedExtensions.add("_a"); // alternative FORTRAN 90
    s_reservedExtensions.add("_i"); // F90 impl suffix
    s_reservedExtensions.add("_mi"); // F90 impl suffix
    s_reservedExtensions.add("_fi"); // F77 impl
    s_reservedExtensions.add("_s"); // FORTRAN 90 suffix
    s_reservedExtensions.add("_p"); // FORTRAN 90 suffix

    s_reservedWords.put("abstract",         "Java");
    s_reservedWords.put("and",              "C++ and Python");
    s_reservedWords.put("and_eq",           "C++");
    s_reservedWords.put("asm",              "C and C++");
    s_reservedWords.put("assert",           "Java and Python");
    s_reservedWords.put("auto",             "C and C++");
    s_reservedWords.put("bitand",           "C++");
    s_reservedWords.put("bitor",            "C++");
    s_reservedWords.put("bool",             "C++");
    s_reservedWords.put("boolean",          "Java");
    s_reservedWords.put("break",            "C, C++, Java, and Python");
    s_reservedWords.put("case",             "C, C++, and Java");
    s_reservedWords.put("catch",            "C++ and Java");
    s_reservedWords.put("char",             "C, C++, and Java");
    s_reservedWords.put("class",            "C++ and Java");
    s_reservedWords.put("compl",            "C++");
    s_reservedWords.put("const",            "C, C++, and Java");
    s_reservedWords.put("const_cast",       "C++");
    s_reservedWords.put("continue",         "C, C++, Java, and Python");
    s_reservedWords.put("def",              "Python");
    s_reservedWords.put("default",          "C, C++, and Java");
    s_reservedWords.put("del",              "Python");
    s_reservedWords.put("delete",           "C++");
    s_reservedWords.put("do",               "C, C++, and Java");
    s_reservedWords.put("double",           "C, C++, and Java");
    s_reservedWords.put("dynamic_cast",     "C++");
    s_reservedWords.put("elif",             "Python");
    s_reservedWords.put("else",             "C, C++, Java, and Python");
    s_reservedWords.put("enum",             "C, C++, and Java");
    s_reservedWords.put("except",           "Python");
    s_reservedWords.put("exec",             "Python");
    s_reservedWords.put("explicit",         "C++");
    s_reservedWords.put("export",           "C++");
    s_reservedWords.put("extends",          "Java");
    s_reservedWords.put("extern",           "C and C++");
    s_reservedWords.put("false",            "C++ and Java");
    s_reservedWords.put("final",            "Java");
    s_reservedWords.put("finally",          "Java and Python");
    s_reservedWords.put("float",            "C, C++, and Java");
    s_reservedWords.put("for",              "C, C++, Java, and Python");
    s_reservedWords.put("friend",           "C++");
    s_reservedWords.put("from",             "Python");
    s_reservedWords.put("getURL",           "Babel Builtin");
    s_reservedWords.put("global",           "Python");
    s_reservedWords.put("goto",             "C, C++, and Java");
    s_reservedWords.put("if",               "C, C++, Java, and Python");
    s_reservedWords.put("implements",       "Java");
    s_reservedWords.put("import",           "Java");
    s_reservedWords.put("inline",           "C++");
    s_reservedWords.put("instanceof",       "Java");
    s_reservedWords.put("int",              "C, C++, and Java");
    s_reservedWords.put("interface",        "Java");
    s_reservedWords.put("is",               "Python");
    s_reservedWords.put("islocal",          "Babel Builtin");
    s_reservedWords.put("isremote",         "Babel Builtin");
    s_reservedWords.put("lambda",           "Python");
    s_reservedWords.put("long",             "C, C++, and Java");
    s_reservedWords.put("mutable",          "C++");
    s_reservedWords.put("namespace",        "C++");
    s_reservedWords.put("native",           "Java");
    s_reservedWords.put("new",              "C++ and Java");
    s_reservedWords.put("none",             "Python");
    s_reservedWords.put("not",              "C++ and Python");
    s_reservedWords.put("not_eq",           "C++");
    s_reservedWords.put("null",             "Java");
    s_reservedWords.put("operator",         "C++");
    s_reservedWords.put("or",               "C++ and Python");
    s_reservedWords.put("or_eq",            "C++");
    s_reservedWords.put("package",          "Java");
    s_reservedWords.put("pass",             "Python");
    s_reservedWords.put("print",            "Python");
    s_reservedWords.put("private",          "C++ and Java");
    s_reservedWords.put("protected",        "C++ and Java");
    s_reservedWords.put("public",           "C++ and Java");
    s_reservedWords.put("raise",            "Python");
    s_reservedWords.put("register",         "C and C++");
    s_reservedWords.put("reinterpret_cast", "C++");
    s_reservedWords.put("return",           "C, C++, Java, and Python");
    s_reservedWords.put("self",             "SIDL backends");
    s_reservedWords.put("short",            "C, C++, and Java");
    s_reservedWords.put("signed",           "C and C++");
    s_reservedWords.put("sizeof",           "C and C++");
    s_reservedWords.put("static",           "C, C++, and Java");
    s_reservedWords.put("static_cast",      "C++");
    s_reservedWords.put("strictfp",         "Java");
    s_reservedWords.put("struct",           "C and C++");
    s_reservedWords.put("super",            "Java");
    s_reservedWords.put("switch",           "C, C++, and Java");
    s_reservedWords.put("synchronized",     "Java");
    s_reservedWords.put("template",         "C++");
    s_reservedWords.put("this",             "C++ and Java");
    s_reservedWords.put("throw",            "C++ and Java");
    s_reservedWords.put("throws",           "Java");
    s_reservedWords.put("transient",        "Java");
    s_reservedWords.put("true",             "C++ and Java");
    s_reservedWords.put("try",              "C++, Java, and Python");
    s_reservedWords.put("typedef",          "C and C++");
    s_reservedWords.put("typeid",           "C++");
    s_reservedWords.put("typename",         "C++");
    s_reservedWords.put("union",            "C and C++");
    s_reservedWords.put("unsigned",         "C and C++");
    s_reservedWords.put("using",            "C++");
    s_reservedWords.put("virtual",          "C++");
    s_reservedWords.put("void",             "C, C++, and Java");
    s_reservedWords.put("volatile",         "C, C++, and Java");
    s_reservedWords.put("wchar_t",          "C++");
    s_reservedWords.put("while",            "C, C++, Java, and Python");
    s_reservedWords.put("xor",              "C++");
    s_reservedWords.put("xor_eq",           "C++");
  }
  
  static class Tuple { 
    ASTNode node;
    HashMap members;
    public Tuple( ASTNode n, HashMap m) { 
      node = n; 
      members = m;
    }
  }
  
  protected Map d_currentScope;
  
  public CollisionChecker( MsgList msgs,
                           Map collisions ) {
    super();
    d_msgs = msgs;
    d_currentScope = collisions;
  }
  
  protected AttributeList checkAttributes( AttributeList attrs ) { 
    if ( attrs == null ) { return null; }
    AttributeList ll = (AttributeList) attrs.cloneEmpty();
    HashMap m = new HashMap();
    for ( Iterator i = attrs.iterator(); i.hasNext(); ) { 
      Attribute a = (Attribute) i.next();
      String str_name = a.getKey();
      if ( m.containsKey(str_name) ) {
        Attribute b = (Attribute) m.get( str_name );
        if (a.isBuiltin()) { 
          if ( a.getValue().equals( b.getValue() )) { 
            d_msgs.addMsg( new UserMsg2( UserMsg.WARNING, "redundant built-in attribute \"" + a + "\"", a, 
                                         "is already declared here", b )); 
          } else { 
            d_msgs.addMsg( new UserMsg2( UserMsg.ERROR, "conflicting built-in attribute \"" + a  + "\"", a, 
                                         "cannot be used in conjunction with preceding \"" + b + "\" attribute", b )); 
          }
        } else { 
          if ( a.getValue().equals( b.getValue() )) { 
            d_msgs.addMsg( new UserMsg2( UserMsg.WARNING, "redundant attribute \"" + a + "\"", a, 
                                         "is already declared here", b )); 
          } else { 
            d_msgs.addMsg( new UserMsg2( UserMsg.ERROR, "conflicting attribute \"" + a.getValue() + "\"", a, 
                                         "cannot be used after \"" + b + "\"", b )); 
          }
        }
      } else { 
        m.put(str_name, a);
        ll.addAttribute(a);
      }    
    }
    return ll;
  }
  
  protected void checkName( String scope_name, Name name, HashMap scope ) { 
    String str_name = name.toString();
    String lowercase_name = name.toString().toLowerCase();
    if (s_reservedWords.containsKey(str_name)) { 
      String langs = (String) s_reservedWords.get(str_name);
      d_msgs.addMsg(new UserMsg( UserMsg.ERROR, "invalid identifer: \"" + str_name + 
                                 "\" is a reserved word in " + langs, name ));
    }
    if ( scope.containsKey(lowercase_name) ) {
      d_msgs.addMsg( new UserMsg2( UserMsg.ERROR, "name collision on \"" + lowercase_name + "\"", name, 
                                   "previously used here in same " + scope_name , (ASTNode) scope.get( lowercase_name )));
    } else { 
      scope.put(lowercase_name, name);
    }
  }

  protected void checkNameInPackageScope( Name name, HashMap scope ) { 
    String str_name = name.toString();
    String lowercase_name = name.toString().toLowerCase();
    if (s_reservedWords.containsKey(str_name)) { 
      String langs = (String) s_reservedWords.get(str_name);
      d_msgs.addMsg(new UserMsg( UserMsg.ERROR, "invalid identifer: \"" + str_name + 
                                 "\" is a reserved word in " + langs, name ));
    }
    if ( scope.containsKey(lowercase_name) ) {
      if ( scope.get(lowercase_name) instanceof Tuple ) {
        Tuple t = (Tuple) scope.get(lowercase_name);
        d_msgs.addMsg( new UserMsg2( UserMsg.ERROR, "name collision on \"" + lowercase_name + "\"", name, 
                                     "previously used here in same package", t.node));
      } else { 
        Name n = (Name) scope.get(lowercase_name);
        d_msgs.addMsg( new UserMsg2( UserMsg.ERROR, "name collision on \"" + lowercase_name + "\"", name, 
                                     "previously used here in same package", n ));
      }
    } else { 
      scope.put(lowercase_name, name);
    }
  }

  private void checkUnderbarError(String nodeType, Name name) {
    if (name.toString().indexOf('_') >= 0) {
      d_msgs.addMsg(new UserMsg(UserMsg.ERROR, "The " + nodeType + " name \"" + 
                                name.toString() +
                                "\" is illegal because it has an underbar (_).",
                                name));
    }
  }

  private void checkScopedID(ScopedID node) { 
    String[] item = node.toString().split("\\.");
    for( int i=0; i<item.length; i++ ) { 
      if ( s_reservedWords.containsKey(item[i])) { 
        String langs = (String) s_reservedWords.get(item[i]);
        d_msgs.addMsg(new UserMsg( UserMsg.ERROR, "invalid scoped identifer:\"" + item[i] + 
                                   "\" segment is a reserved word in " + langs, node ));
      }
    }
  }


  
  public Object visitSIDLFile(SIDLFile node, Object data) {
    super.visitSIDLFile(node, d_currentScope);
    return data;
  }
  

  public Object visitImportClause(ImportClause node, Object data) {
    checkScopedID(node.getScopedID());
    return data;
  }

  public Object visitRequireClause(RequireClause node, Object data) {
    checkScopedID(node.getScopedID());
    return data;
  }

  public Object visitPackage(Package node, Object data) {
    node.setAttributeList( checkAttributes( node.getAttributeList() ) );
    String str_name = node.getName().toString();
    String lower_str_name = node.getName().toString().toLowerCase();
    HashMap parent_scope = (HashMap) data;
    boolean collision_allowed = false;
    checkUnderbarError("package", node.getName());
    if ( parent_scope.containsKey(lower_str_name) ) { 
      if ( parent_scope.get(lower_str_name) instanceof Tuple ) { 
        ASTNode n = ((Tuple) parent_scope.get(lower_str_name)).node;
        if ( n instanceof Package ) { 
          Package p = (Package) n;
          //Check if one of the pacakges attempting to be re-entered is final.
          if (node.getAttributeList().hasAttribute("final")) { 
            d_msgs.addMsg( new UserMsg2( UserMsg.ERROR, "final package \"" + str_name + "\"", 
                                         node.getAttributeList().getAttribute("final"), 
                                         "previously declared reentrant", p.getName() ));
            return data;
          } else if ( p.getAttributeList().hasAttribute("final")) { 
            d_msgs.addMsg( new UserMsg2( UserMsg.ERROR, "final package \"" + str_name + "\"", 
                                         p.getAttributeList().getAttribute("final"), 
                                         "cannot be reopened here", node.getName() ));
            return data;
            //Check for differring case, if the names are the same, but the
            //case is different, it's a collision.
          } else if(!str_name.equals(p.getName().toString())) {
            d_msgs.addMsg( new UserMsg2( UserMsg.ERROR, "Unable to determine if package \"" + 
                                         str_name + "\" is meant to reenter ", p.getName(), 
                                         " or not due to differing case.", node.getName() ));
            //If both pacakges are unversioned, or both have the same
            //version, renter the package. Otherwise, it's an illegal collision.
          } else if(node.getVersion() == null) {
            if(p.getVersion() == null) {
              collision_allowed = true;
            } else {
              d_msgs.addMsg( new UserMsg2( UserMsg.ERROR, "Unable to determine if package \"" + 
                                           str_name + "\" is meant to reenter ", p.getName(), 
                                           " or not due to differing versions.", node.getName() ));
            }
          } else if(p.getVersion() == null) {
            d_msgs.addMsg( new UserMsg2( UserMsg.ERROR, "Unable to determine if package \"" + 
                                         str_name + "\" is meant to reenter ", p.getName(), 
                                         " or not due to differing versions.", node.getName() ));
          } else if(!node.getVersion().equals(p.getVersion())) {
            d_msgs.addMsg( new UserMsg2( UserMsg.ERROR, "Unable to determine if package \"" + 
                                         str_name + "\" is meant to reenter ", p.getName(), 
                                         " or not due to differing versions.", node.getName() ));
          } else { 
            collision_allowed = true;
          }
        }
      }
    }
    HashMap my_scope;
    if ( collision_allowed ) { // entering reentrant package
      my_scope = ((Tuple) parent_scope.get(lower_str_name)).members;
    } else { // do the normal thing
      checkNameInPackageScope( node.getName(), parent_scope ); 
      my_scope = new HashMap();
      parent_scope.put(lower_str_name, new Tuple(node,my_scope)); //record my scope for later
    }
    super.visitPackage(node, my_scope);  
    return data;
  }

  public Object visitName(Name n, Object data)
  {
    HashSet result = new HashSet();
    result.add(n);
    return result;
  }

  public Object visitIntLiteral(IntLiteral il, Object data)
  {
    return new HashSet();
  }

  public Object visitDoubleLiteral(DoubleLiteral il, Object data)
  {
    return new HashSet();
  }

  public Object visitFloatLiteral(FloatLiteral l, Object data)
  {
    return new HashSet();
  }

  public Object visitCharacterLiteral(CharacterLiteral il, Object data)
  {
    return new HashSet();
  }

  public Object visitBooleanLiteral(BooleanLiteral il, Object data)
  {
    return new HashSet();
  }

  public Object visitStringLiteral(StringLiteral il, Object data)
  {
    return new HashSet();
  }

  public Object visitFComplexLiteral(FComplexLiteral il, Object data)
  {
    return new HashSet();
  }

  public Object visitDComplexLiteral(DComplexLiteral il, Object data)
  {
    return new HashSet();
  }

  public Object visitBinaryExpr(BinaryExpr be, Object data)
  {
    Set lhsNames = (Set)be.getLHS().accept(this, null);
    Set rhsNames = (Set)be.getRHS().accept(this, null);
    if (lhsNames != null) {
      if (rhsNames != null) {
        lhsNames.addAll(rhsNames);
      }
      return lhsNames;
    }
    return rhsNames;
  }

  public Object visitUnaryExpr(UnaryExpr ue, Object data)
  {
    return ue.getOperand().accept(this, null);
  }

  public Object visitExtents(Extents e, Object data)
  {
    HashSet result = new HashSet();
    Iterator i = e.getExtents().iterator();
    while (i.hasNext()) {
      Set names = (Set)((ASTNode)i.next()).accept(this, data);
      if (names != null) {
        if (names.size() > 1) {
          d_msgs.addMsg(new UserMsg
                        (UserMsg.ERROR,
                         "rarray extent refers to more than one index variable",
                         e));
        }
        result.addAll(names);
      }
    }
    return result;
  }
	
  public Object visitEnumeration(Enumeration node, Object data) {
    checkUnderbarError("enumeration", node.getName());
    checkNameInPackageScope( node.getName(), (HashMap) data );
    HashMap new_scope = new HashMap();
    super.visitEnumeration(node, new_scope);
    // Now create a suitable list of integers for unassigned enums.
    TreeSet s = new TreeSet(); 
    int listsize = node.getEnumItemList().size();
    for( int i=0; i<listsize; i++) { 
      s.add(new Integer(i));
    }
    // Now run through list of EnumItems, checking their explicit values
    // report errors on explicit duplicates, and remove any from set of 
    // candidate numbers 
    HashMap dups = new HashMap(); // will detect duplicates
    for( Iterator i = node.getEnumItemList().iterator(); i.hasNext(); ) {
      EnumItem e = (EnumItem) i.next();
      if ( e.hasValue() ) { 
        Integer value = new Integer( e.getValue() );
        s.remove(value); 
        if (dups.containsKey(value)) { 
          EnumItem duplicate = (EnumItem) dups.get(value);
          if ( e.getExplicitValue() == null || duplicate.getExplicitValue()==null ) { 
            d_msgs.addMsg(new UserMsg2( UserMsg.ERROR, "(internal) duplicate enum values",e,
                                        "previously appeared(generated?!?) here", duplicate));
          } else { 
            d_msgs.addMsg( new UserMsg2( UserMsg.ERROR, "duplicate explicit enum value", e.getExplicitValue(), 
                                         "previously appeared here", duplicate.getExplicitValue() ));
          }
        } else { 
          dups.put(value, e);
        }
      }   
    }
    // Now we simply march through the list of remaining numbers and 
    // assign values to unassigned enum items
    Iterator sorted_remaining_ints = s.iterator();
    for( Iterator i = node.getEnumItemList().iterator(); i.hasNext(); ) {
      EnumItem e = (EnumItem) i.next();
      if ( !e.hasValue() ) { 
        Integer smallest_valid_int = (Integer) sorted_remaining_ints.next();
        e.setValue( smallest_valid_int.intValue());
      }
    }
    return data;
  }
  
  public Object visitEnumItem(EnumItem node, Object data) { 
    checkName( "enumeration", node.getName(), (HashMap) data );
    return data;
  }
  
  public Object visitStructType(StructType node, Object data) { 
    checkUnderbarError("struct", node.getName());
    checkNameInPackageScope( node.getName(), (HashMap) data );
    HashMap new_scope = new HashMap();
    return super.visitStructType(node, new_scope);
  }
  
  public Object visitStructItem(StructItem node, Object data) { 
    checkName( "struct", node.getName(), (HashMap) data );
    return data;
  }
  
  public Object visitClassType(ClassType node, Object data) {
    checkUnderbarError("class", node.getName());
    node.setAttributeList(checkAttributes(node.getAttributeList()));
    checkNameInPackageScope( node.getName(), (HashMap) data );
    if ( node.getExtends() != null ) {
      checkScopedID( node.getExtends() );
    } 
    if ( node.getImplementsList() != null && node.getImplementsList().size() > 0 ) { 
      for( Iterator it = node.getImplementsList().iterator(); it.hasNext(); ) { 
        checkScopedID((ScopedID) it.next() );
      }
    }
    super.visitClassType(node,data);
    return data;
  }

  public Object visitInterfaceType(InterfaceType node, Object data) {
    checkUnderbarError("interface", node.getName());
    node.setAttributeList(checkAttributes(node.getAttributeList()));
    checkNameInPackageScope( node.getName(), (HashMap) data );
    if ( node.getExtends().size() > 0 ) { 
      for( Iterator it = node.getExtends().iterator(); it.hasNext(); ) { 
        checkScopedID((ScopedID) it.next() );
      }
    }    super.visitInterfaceType(node,data);
    return data;
  }

  public Object visitMethodList(MethodList node, Object data) {
    HashMap new_scope = new HashMap();
    super.visitMethodList(node, new_scope);
    return data;
  }

  private void checkMethodExtension(MethodName mn)
  {
    Name ext = mn.getNameExtension();
    if (ext != null) {
      if (s_reservedExtensions.contains(ext.toString())) {
        d_msgs.addMsg(new UserMsg(UserMsg.WARNING, "The method extension \"" + ext.toString() +
                                  "\" matches one used internally by Babel. This may result in a symbol conflict.",
                                  mn));
      }
    }
  }
  
  public Object visitMethod(Method node, Object data ) { 
    ASTNode parent = node.getParent().getParent();
    String scopeName = "scope";
    if ( parent instanceof ClassType ) { 
      scopeName = "class";
    } else if ( parent instanceof InterfaceType ) { 
      scopeName = "interface";
    } 
    checkName( scopeName, node.getMethodName(), (HashMap) data );
    checkMethodExtension(node.getMethodName());
    node.setAttributeList(checkAttributes(node.getAttributeList()));
    if(node.getAttributeList().hasAttribute("oneway")) { 
      if(node.getReturnType() != null) {
        d_msgs.addMsg( new UserMsg( UserMsg.ERROR, "oneway method cannot return any value", node.getReturnType()));
      }
      for(Iterator args = node.getArgumentList().iterator(); args.hasNext();) {
        Argument arg = (Argument) args.next();
        if(!arg.getMode().equals("in")) {
          d_msgs.addMsg( new UserMsg( UserMsg.ERROR, "oneway method cannot return out or inout arguments", arg));
        }
      }
    }
    super.visitMethod(node,node); //Pass in Method as the data 
    return data;
  }
  
  public Object visitArgumentList(ArgumentList node, Object data) { 
    HashMap new_scope = new HashMap();
    d_hasRArrays=false;
    super.visitArgumentList(node, new_scope);
    if (d_hasRArrays) { 
      // 1. gather up all the names of all the extents
      LinkedList names = new LinkedList();
      HashMap args = new HashMap();
      for(Iterator it = node.iterator(); it.hasNext(); ) { 
        Argument arg = (Argument) it.next();
        if ( arg.getType() instanceof RArrayType ) { 
          RArrayType t = (RArrayType) arg.getType();
          Set localRefs = (Set)t.getExtents().accept(this, data);
          if (localRefs != null) {
            names.addAll(localRefs);
          }
        }
        args.put(arg.getName().toString().toLowerCase(), arg);
      }
      // 2. make sure each name is found in the same scope
      for(Iterator iter = names.iterator(); iter.hasNext(); ) { 
        Name n = (Name) iter.next();
        if (! new_scope.containsKey(n.toString().toLowerCase())) { 
          d_msgs.addMsg( new UserMsg( UserMsg.ERROR, "array extent \"" + n + "\" not found in arglist", n));
        } else { 
          Argument arg = (Argument) args.get(n.toString().toLowerCase());
          Type t = (Type) arg.getType();
          FixedType ft = t instanceof FixedType ? (FixedType) t : null;
          if (ft == null ||  !ft.toString().toLowerCase().equals("int") ) { 
            d_msgs.addMsg( new UserMsg2( UserMsg.ERROR, "array extent \"" + n + "\" should be of type int", n, 
                                         "instead of type" + t.getTypeName(), arg.getName()));
          }
        }     
      }
    }
    return data;
  }
  
  public Object visitArgument(Argument node, Object data) { 
    checkName("argument list", node.getName(), (HashMap) data);
    node.setAttributeList(checkAttributes(node.getAttributeList()));
    super.visitArgument(node,data);
    return data;
  }
  
  public Object visitRArrayType(RArrayType node, Object data) { 
    d_hasRArrays = true;
    return data;
  }

  public Object visitThrowsList(ThrowsList node, Object data) { 
    if (node.size() > 0) { 
      for(Iterator it = node.iterator(); it.hasNext(); ) { 
        checkScopedID( (ScopedID) it.next() );
      }
    }
    return data;
  }

  public Object visitFromClause(FromClause node, Object data) {
    Method method = (Method) data;
    checkScopedID(node.getScopedID());
    if(!(method.getMethodName().getShortName().equals(node.getMethodName().getShortName()))) {
      d_msgs.addMsg( new UserMsg( UserMsg.ERROR, 
                                  "From clause renames method short name", method));
    } else if(method.getMethodName().equals(node.getMethodName())) {
      d_msgs.addMsg( new UserMsg( UserMsg.WARNING, 
                                  "Useless from clause, renames method to the same name", method));
    }
    return data;
  }


}
