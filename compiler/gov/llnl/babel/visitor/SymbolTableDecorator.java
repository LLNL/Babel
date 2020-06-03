package gov.llnl.babel.visitor;

import gov.llnl.babel.BabelConfiguration;
import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.ast.ASTNode;
import gov.llnl.babel.ast.Argument;
import gov.llnl.babel.ast.ArrayType;
import gov.llnl.babel.ast.Assertion;
import gov.llnl.babel.ast.BinaryExpr;
import gov.llnl.babel.ast.BooleanLiteral;
import gov.llnl.babel.ast.CharacterLiteral;
import gov.llnl.babel.ast.ClassType;
import gov.llnl.babel.ast.DComplexLiteral;
import gov.llnl.babel.ast.DoubleLiteral;
import gov.llnl.babel.ast.Ensures;
import gov.llnl.babel.ast.FComplexLiteral;
import gov.llnl.babel.ast.FixedType;
import gov.llnl.babel.ast.FloatLiteral;
import gov.llnl.babel.ast.FromClause;
import gov.llnl.babel.ast.FuncExpr;
import gov.llnl.babel.ast.ImportClause;
import gov.llnl.babel.ast.IntLiteral;
import gov.llnl.babel.ast.InterfaceType;
import gov.llnl.babel.ast.Invariants;
import gov.llnl.babel.ast.Method;
import gov.llnl.babel.ast.MethodName;
import gov.llnl.babel.ast.Name;
import gov.llnl.babel.ast.Package;
import gov.llnl.babel.ast.RArrayType;
import gov.llnl.babel.ast.RequireClause;
import gov.llnl.babel.ast.Requires;
import gov.llnl.babel.ast.SIDLFile;
import gov.llnl.babel.ast.ScopedID;
import gov.llnl.babel.ast.SplicerBlock;
import gov.llnl.babel.ast.SplicerImpl;
import gov.llnl.babel.ast.SplicerList;
import gov.llnl.babel.ast.SplicerImplList;
import gov.llnl.babel.ast.StringLiteral;
import gov.llnl.babel.ast.StructItem;
import gov.llnl.babel.ast.StructType;
import gov.llnl.babel.ast.ThrowsList;
import gov.llnl.babel.ast.Type;
import gov.llnl.babel.ast.UnaryExpr;
import gov.llnl.babel.msg.MsgList;
import gov.llnl.babel.msg.UserMsg2;
import gov.llnl.babel.msg.UserMsg;
import gov.llnl.babel.symbols.AssertionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

/**
 * Iterates over a AST a second time to decorate a primed SymbolTable with all
 * the details about classes, interfaces, and structs
 * 
 * Type resolution is largely performed in this stage.
 * 
 * @see gov.llnl.babel.visitor.SymbolTablePrimer
 * @author kumfert
 */
public class SymbolTableDecorator extends Visitor {

  private class ExprConverter extends Visitor {
    private final int s_unaryMap[] = {
      gov.llnl.babel.symbols.UnaryExpression.NOOP,
      gov.llnl.babel.symbols.UnaryExpression.PLUS,
      gov.llnl.babel.symbols.UnaryExpression.MINUS,
      gov.llnl.babel.symbols.UnaryExpression.COMPLEMENT,
      gov.llnl.babel.symbols.UnaryExpression.IS,
      gov.llnl.babel.symbols.UnaryExpression.NOT
    };

    private final int s_binaryMap[] = {
      gov.llnl.babel.symbols.BinaryExpression.NOOP,
      gov.llnl.babel.symbols.BinaryExpression.PLUS,
      gov.llnl.babel.symbols.BinaryExpression.MINUS,
      gov.llnl.babel.symbols.BinaryExpression.MULTIPLY,
      gov.llnl.babel.symbols.BinaryExpression.DIVIDE,
      gov.llnl.babel.symbols.BinaryExpression.POWER,
      gov.llnl.babel.symbols.BinaryExpression.LESS_THAN,
      gov.llnl.babel.symbols.BinaryExpression.LESS_EQUAL,
      gov.llnl.babel.symbols.BinaryExpression.GREATER_THAN,
      gov.llnl.babel.symbols.BinaryExpression.GREATER_EQUAL,
      gov.llnl.babel.symbols.BinaryExpression.EQUALS,
      gov.llnl.babel.symbols.BinaryExpression.LOGICAL_AND,
      gov.llnl.babel.symbols.BinaryExpression.LOGICAL_OR,
      gov.llnl.babel.symbols.BinaryExpression.LOGICAL_XOR,
      gov.llnl.babel.symbols.BinaryExpression.BITWISE_AND,
      gov.llnl.babel.symbols.BinaryExpression.BITWISE_OR,
      gov.llnl.babel.symbols.BinaryExpression.BITWISE_XOR,
      gov.llnl.babel.symbols.BinaryExpression.SHIFT_LEFT,
      gov.llnl.babel.symbols.BinaryExpression.SHIFT_RIGHT,
      gov.llnl.babel.symbols.BinaryExpression.IF_AND_ONLY_IF,
      gov.llnl.babel.symbols.BinaryExpression.IMPLIES,
      gov.llnl.babel.symbols.BinaryExpression.MODULUS,
      gov.llnl.babel.symbols.BinaryExpression.REMAINDER,
      gov.llnl.babel.symbols.BinaryExpression.NOT_EQUAL
    };

    public Object visitBinaryExpr(BinaryExpr node, Object data)
    {
      try {
        gov.llnl.babel.symbols.AssertionExpression lhs =
          (gov.llnl.babel.symbols.AssertionExpression)
          node.getLHS().accept(this, data);
        gov.llnl.babel.symbols.AssertionExpression rhs =
          (gov.llnl.babel.symbols.AssertionExpression)
          node.getRHS().accept(this, data);
        return new gov.llnl.babel.symbols.BinaryExpression
          (lhs, s_binaryMap[node.getOperator()], rhs, d_context);
      }
      catch (gov.llnl.babel.symbols.AssertionException ae) {
        d_msgs.addMsg(new UserMsg(UserMsg.ERROR, 
                                  "unable to convert expression", 
                                  node));
      }
      return data;
    }

    public Object visitUnaryExpr (UnaryExpr node, Object data)
    {
      try {
        gov.llnl.babel.symbols.AssertionExpression oper =
          (gov.llnl.babel.symbols.AssertionExpression)
          node.getOperand().accept(this, data);
        return new gov.llnl.babel.symbols.UnaryExpression
          (s_unaryMap[node.getOperator()], oper, d_context);
      }
      catch (gov.llnl.babel.symbols.AssertionException ae) {
        d_msgs.addMsg(new UserMsg(UserMsg.ERROR, 
                                  "unable to convert expression", 
                                  node));
      }
      return data;
    }

    public Object visitName(Name name, Object data) {
      try {
        gov.llnl.babel.symbols.IdentifierLiteral il = 
          new gov.llnl.babel.symbols.IdentifierLiteral
          (name.toString(), d_context);
        return il;
      }
      catch (gov.llnl.babel.symbols.AssertionException ae) {
        d_msgs.addMsg(new UserMsg(UserMsg.ERROR, 
                                  "unable to convert identifier",
                                  name));
      }
      return data;
    }

    public Object visitIntLiteral(IntLiteral node, Object data)
    {
      try {
        String image;
        ParseTreeNode ptn = node.getParseTreeNode();
        if (ptn != null) {
          image = ptn.name;
        }
        else {
          image = Integer.toString(node.getInt());
        }
        return new gov.llnl.babel.symbols.IntegerLiteral
          (new Integer(node.getInt()), image, d_context);
      }
      catch (gov.llnl.babel.symbols.AssertionException ae) {
        d_msgs.addMsg(new UserMsg(UserMsg.ERROR, 
                                  "unable to convert integer literal",
                                  node));
      }
      return data;
    }

    public Object visitBooleanLiteral(BooleanLiteral node, Object data)
    {
      try {
        return new gov.llnl.babel.symbols.BooleanLiteral(node.getBoolean(), d_context);
      }
      catch (AssertionException ae) {
        d_msgs.addMsg(new UserMsg(UserMsg.ERROR, 
                                  "unable to convert boolean literal",
                                  node));
      }
      return data;
    }

    public Object visitCharacterLiteral(CharacterLiteral node, Object data)
    {
      try {
        return new gov.llnl.babel.symbols.CharacterLiteral(node.getChar(), 
                                                           d_context);
      }
      catch (AssertionException ae) {
        d_msgs.addMsg(new UserMsg(UserMsg.ERROR, 
                                  "unable to convert character literal",
                                  node));
      }
      return data;
    }

    public Object visitStringLiteral(StringLiteral node, Object data)
    {
      try {
        return new gov.llnl.babel.symbols.StringLiteral(node.getString(),
                                                        d_context);
      }
      catch (AssertionException ae) {
        d_msgs.addMsg(new UserMsg(UserMsg.ERROR, 
                                  "unable to convert string literal",
                                  node));
      }
      return data;
    }

    public Object visitDoubleLiteral(DoubleLiteral node, Object data)
    {
      try {
        return new gov.llnl.babel.symbols.
          DoubleLiteral(new Double(node.getDouble()),
                        node.toString(), d_context);
      }
      catch (AssertionException ae) {
        d_msgs.addMsg(new UserMsg(UserMsg.ERROR, 
                                  "unable to convert double literal",
                                  node));
      }
      return data;
    }

    public Object visitFloatLiteral(FloatLiteral node, Object data)
    {
      try {
        return new gov.llnl.babel.symbols.
          FloatLiteral(new Float(node.getFloat()),
                        node.toString(), d_context);
      }
      catch (AssertionException ae) {
        d_msgs.addMsg(new UserMsg(UserMsg.ERROR, 
                                  "unable to convert float literal",
                                  node));
      }
      return data;
    }

    public Object visitDComplexLiteral(DComplexLiteral node, Object data)
    {
      try {
        return new gov.llnl.babel.symbols.
          DComplexLiteral((gov.llnl.babel.symbols.DoubleLiteral)
                          node.getRealLiteral().accept(this, data),
                          (gov.llnl.babel.symbols.DoubleLiteral)
                          node.getImagLiteral().accept(this, data),
                          d_context);
      }
      catch (AssertionException ae) {
        d_msgs.addMsg(new UserMsg(UserMsg.ERROR, 
                                  "unable to convert dcomplex literal",
                                  node));
      }
      return data;
    }

    public Object visitFComplexLiteral(FComplexLiteral node, Object data)
    {
      try {
        return new gov.llnl.babel.symbols.
          FComplexLiteral((gov.llnl.babel.symbols.FloatLiteral)
                          node.getRealLiteral().accept(this, data),
                          (gov.llnl.babel.symbols.FloatLiteral)
                          node.getImagLiteral().accept(this, data),
                          d_context);
      }
      catch (AssertionException ae) {
        d_msgs.addMsg(new UserMsg(UserMsg.ERROR, 
                                  "unable to convert fcomplex literal",
                                  node));
      }
      return data;
    }

    public Object visitFuncExpr(FuncExpr node, Object data)
    {
      try {
        gov.llnl.babel.symbols.MethodCall mc = new 
          gov.llnl.babel.symbols.MethodCall(node.getName(), d_context);
        if (node.getArguments() != null) {
          Iterator i = node.getArguments().iterator();
          while (i.hasNext()) {
            mc.addArgument((gov.llnl.babel.symbols.AssertionExpression)
                           ((ASTNode)i.next()).accept(this, data));
          }
        }
        return mc;
      }
      catch (AssertionException ae) {
        d_msgs.addMsg(new UserMsg(UserMsg.ERROR, 
                                  "unable to convert method call",
                                  node));
      }
      return data;
    }

  }

  private HierarchySorter d_hs;

  private final static String SIDL_CLASS = "."
      + BabelConfiguration.getBaseClass();

  private final static String SIDL_INTERFACE = "."
      + BabelConfiguration.getBaseInterface();

  /*
   * private final static String SIDL_THROWS_CLASS = "." +
   * BabelConfiguration.getBaseExceptionClass();
   * 
   * private final static String SIDL_THROWS_INTERFACE = "." +
   * BabelConfiguration.getBaseExceptionInterface();
   */
  private final static String SIDL_IMPLICIT_THROW = "."
      + BabelConfiguration.getRuntimeException();

  private gov.llnl.babel.Context d_context;

  protected MsgList d_msgs;

  protected ArrayList d_imports = null;

  protected HashMap d_requires = null; // Package name to RequiresClause.

  public SymbolTableDecorator(MsgList msgs, HierarchySorter hs,
                              gov.llnl.babel.Context context) {
    super();
    d_msgs = msgs;
    d_context = context;
    d_hs = hs;
  }

  public Object visitSIDLFile(SIDLFile node, Object data) {
    // initialize import clauses for proper type resolution particular to
    // this file
    d_imports = new ArrayList();
    d_requires = new HashMap();
    return super.visitSIDLFile(node, data);
  }

  public Object visitImportClause(ImportClause node, Object data) {
    boolean okay_to_add = true;
    String node_typename = node.getScopedID().toString();
    for (ListIterator i = d_imports.listIterator(); i.hasNext();) {
      ImportClause here = (ImportClause) i.next();
      String other_typename = here.getScopedID().toString();
      if (node_typename.equals(other_typename)) {
        okay_to_add = false;
        d_msgs
            .addMsg(new UserMsg2(UserMsg.WARNING, "duplicate import statement",
                node, "ignored in deference to", here));
      }
    }
    if (okay_to_add) {
      d_imports.add(node);
    }
    return data;
  }

  public Object visitRequireClause(RequireClause node, Object data) {
    boolean okay_to_add = true;
    String node_typename = node.getScopedID().toString();
    RequireClause exists = (RequireClause) d_requires.get(node_typename);
    if (exists != null) {
      d_msgs.addMsg(new UserMsg2(UserMsg.WARNING,
          "duplicate requires statement", node, "ignored in deference to",
          exists));
      okay_to_add = false;
    }
    if (okay_to_add) {
      d_requires.put(node_typename, node);
    }
    return data;
  }


  public Object visitClassType(ClassType node, Object data) {
    // first pull original symbol again
    gov.llnl.babel.symbols.Class cls = (gov.llnl.babel.symbols.Class) node
        .getSymbolTableEntry();
    Package pkg = (Package) node.getParent();

    SymbolTablePrimer.copyAttributes(node.getAttributeList(), cls);
    
    // add inherited class (or .sidl.BaseClass by default)
    gov.llnl.babel.symbols.Class parent = null;
    if (node.getExtends() != null) {
      gov.llnl.babel.symbols.Symbol tmp_sym = (gov.llnl.babel.symbols.Symbol) 
        TypeResolver.resolveType(node.getExtends().toString(), 
                                 pkg, node, d_context.getSymbolTable(), d_imports, d_requires, d_msgs);      
      if (tmp_sym == null) {
        d_msgs.addMsg(new UserMsg(UserMsg.ERROR,
                                  "Cannot resolve parent class' type", node.getExtends()));
        return data;
      }
      if(!( tmp_sym instanceof  gov.llnl.babel.symbols.Class)) {
        d_msgs.addMsg(new UserMsg(UserMsg.ERROR,
                                  "Parent symbol is not a class", node.getExtends()));
        return data;
      }
      
      parent = (gov.llnl.babel.symbols.Class) tmp_sym;
      
      d_hs.registerParentChild(parent, cls);
    } else if (!node.getFQN().equals(SIDL_CLASS)) {
      parent = (gov.llnl.babel.symbols.Class) TypeResolver.resolveType(SIDL_CLASS, pkg, node, 
          d_context.getSymbolTable(), d_imports, d_requires, d_msgs);
      if (parent == null) {
        d_msgs.addMsg(new UserMsg(UserMsg.INTERNAL_ERROR,
            "Cannot resolve implicit base class", node.getName()));
        return data;
      }
      d_hs.registerParentChild(parent, cls);
    }

    // NOTE: cannot simply do "cls.setParentClass(parent);"
    // It expects all parent classes to be fully defined.
    // The new parser, therefore, caches this information
    // and does the heirarchical sort and do this after
    // all the methods have been assigned to all the types.

    // iterate over inherited interfaces
    if (node.getImplementsList() != null) {
      for (ListIterator i = node.getImplementsList().listiterator(); i
          .hasNext();) {
        ScopedID id = (ScopedID) i.next();
        gov.llnl.babel.symbols.Symbol tmp_sym = 
          (gov.llnl.babel.symbols.Symbol) TypeResolver.resolveType(id.toString(),pkg, node,
                                                                   d_context.getSymbolTable(), d_imports, d_requires, d_msgs);
        if (tmp_sym == null) {
          d_msgs.addMsg(new UserMsg(UserMsg.ERROR,
              "Cannot resolve inherited interface's type", id));
          return data;
        }
        
        if(!( tmp_sym instanceof  gov.llnl.babel.symbols.Interface)) {
          d_msgs.addMsg(new UserMsg(UserMsg.ERROR,
                                    "Implemented symbol is not an interface", id));
          return data;
        }

        gov.llnl.babel.symbols.Interface iface = (gov.llnl.babel.symbols.Interface) tmp_sym;
        d_hs.registerParentChild(iface, cls);

        //Register this implements-all interface with the Class in the SymbolTable
        //Later we will add all the interfaces methods to the class.  (The interface
        //methods may not yet all be added to the interface.  We aren't sure until 
        //after HierarchicalSorter.commitHierarchy)
        if (node.getImplementsList().isImplementsAll(id)) {
          cls.addImplementsAll(iface);
        }
      }
    }
    // iterate over methods
    Object result = super.visitClassType(node, cls);

    // TODO: check for abstract and other details

    return result;
  }

  public Object visitInterfaceType(InterfaceType node, Object data) {
    // first pull original symbol again
    gov.llnl.babel.symbols.Interface iface = (gov.llnl.babel.symbols.Interface) node
        .getSymbolTableEntry();
    Package pkg = (Package) node.getParent();

    SymbolTablePrimer.copyAttributes(node.getAttributeList(), iface);

    //TODO: remove this if
    if (node.getExtends().isEmpty()) {
      // add sidl.BaseInterface implicitly
      if (!node.getFQN().equals(SIDL_INTERFACE)) {
        gov.llnl.babel.symbols.Interface parent = (gov.llnl.babel.symbols.Interface) TypeResolver.resolveType(
            SIDL_INTERFACE, pkg, node, d_context.getSymbolTable(), d_imports, d_requires, d_msgs);
        if (parent == null) {
          d_msgs.addMsg(new UserMsg(UserMsg.INTERNAL_ERROR,
              "Cannot resolve implicit base class", node.getName()));
          return data;
        }
        d_hs.registerParentChild(parent, iface);
      } // end if ! SIDL_INTERFACE
    } else {
      // add explicit list of interfaces.
      for (ListIterator i = node.getExtends().listiterator(); i.hasNext();) {

        ScopedID id = (ScopedID) i.next();
        gov.llnl.babel.symbols.Symbol tmp_sym = 
          (gov.llnl.babel.symbols.Symbol) TypeResolver.resolveType(id.toString(),pkg, node,
                                                                   d_context.getSymbolTable(), d_imports, d_requires, d_msgs);
        if (tmp_sym == null) {
          d_msgs.addMsg(new UserMsg(UserMsg.ERROR,
              "Cannot resolve inherited interface's type", id));
          return data;
        }
        
        if(!( tmp_sym instanceof  gov.llnl.babel.symbols.Interface)) {
          d_msgs.addMsg(new UserMsg(UserMsg.ERROR,
                                    "Extended symbol is not an interface", id));
          return data;
        }

        gov.llnl.babel.symbols.Interface parent = (gov.llnl.babel.symbols.Interface) tmp_sym;
        d_hs.registerParentChild(parent, iface);
      }
    }
    Object result = super.visitInterfaceType(node, iface);

    return result;
  }

  public Object visitMethod(Method node, Object data) {
    gov.llnl.babel.symbols.Extendable ext = (gov.llnl.babel.symbols.Extendable) data;
    gov.llnl.babel.symbols.Comment comment = null;

    // create a method symbol and bind it to the Extendable
    gov.llnl.babel.symbols.Method method = new gov.llnl.babel.symbols.Method(d_context);
 
    ASTNode tmp_node = node.getParent();
    while(!(tmp_node instanceof Package)) {
      tmp_node = tmp_node.getParent();
    }
    Package pkg = (Package) tmp_node;

    // comments
    if (node.getDocComment() != null) {
      comment = new gov.llnl.babel.symbols.Comment(node.getDocComment()
          .toString().split("\n"));
    } else {
      comment = new gov.llnl.babel.symbols.Comment(null);
    }
    method.setComment(comment);
    SymbolTablePrimer.copyAttributes(node.getAttributeList(), method);
    if (data instanceof gov.llnl.babel.symbols.Interface) {
      method.setDefinitionModifier(gov.llnl.babel.symbols.Method.ABSTRACT);
    }
    // return type
    if (node.getReturnType() == null) {
      method.setReturnType(new gov.llnl.babel.symbols.Type(
          gov.llnl.babel.symbols.Type.VOID));
      method.setReturnCopy(false);
    } else {
      gov.llnl.babel.symbols.Type t = (gov.llnl.babel.symbols.Type) node
          .getReturnType().accept(this, null);
      method.setReturnType(t);
    }

    // determine name
    if (node.getMethodName().getNameExtension() == null) {
      method.setMethodName(node.getMethodName().getShortName().toString());
    } else {
      method.setMethodName(node.getMethodName().getShortName().toString(), node
          .getMethodName().getNameExtension().toString());
    }

    // arguments
    // exceptions
    // assertions
    Object result = super.visitMethod(node, method); // Visit children

    List args = method.getArgumentList();
    int resolvedIndexArgs = 0;
    boolean resolvedLastIndex = false;

    // Here's where we do a semantic check on RARRAYS
    // For loop puts together the set of rarray indices required
    for (Iterator i = args.iterator(); i.hasNext();) {
      gov.llnl.babel.symbols.Argument arg1 = 
        (gov.llnl.babel.symbols.Argument) i.next();
      if (arg1.getType().isRarray()) {
        method.addRarrayIndex(arg1.getType().getArrayIndices());
      }
    }

    if (method.hasRarray()) {
      for (Iterator indexIt = method.getRarrayIndices().iterator(); indexIt
          .hasNext();) {
        String indexName = (String) indexIt.next();
        for (Iterator argIt = args.iterator(); argIt.hasNext();) {
          gov.llnl.babel.symbols.Argument arg1 = (gov.llnl.babel.symbols.Argument) argIt
              .next();
          if (indexName.compareTo((String) arg1.getFormalName()) == 0) {
            if (!(arg1.getType().getType() == gov.llnl.babel.symbols.Type.INT || arg1
                .getType().getType() == gov.llnl.babel.symbols.Type.LONG)) {
              d_msgs.addMsg(new UserMsg(UserMsg.ERROR,
                  "Raw Array extent arguments must be "
                      + "either of type Int or Long.", node));
            }
            if (arg1.getMode() != gov.llnl.babel.symbols.Argument.IN) {
              d_msgs.addMsg(new UserMsg(UserMsg.ERROR,
                  "Raw Array extent arguments must be in arguments", node));
            }

            ++resolvedIndexArgs;
            resolvedLastIndex = true;
            break;
          }
        }
        if (resolvedLastIndex) {
          resolvedLastIndex = false;
        } else {
          d_msgs.addMsg(new UserMsg(UserMsg.ERROR, "Rarray Index argument "
              + indexName + " cannot be resolved, please include it in the "
              + "argument list.", node));
        }
      }
    }
    FromClause fclause = node.getFromClause();
    if(fclause != null) {
      MethodName oldName = fclause.getMethodName();
      gov.llnl.babel.symbols.Method oldMethod = method.cloneMethod();
      if (oldName.getNameExtension() == null) {
        oldMethod.setMethodName(oldName.getShortName().toString());
      } else {
        oldMethod.setMethodName(oldName.getShortName().toString(), 
                             oldName.getNameExtension().toString());
      }


      gov.llnl.babel.symbols.Symbol tmp_sym = (gov.llnl.babel.symbols.Symbol) 
        TypeResolver.resolveType(fclause.getScopedID().toString(), 
                                 pkg, fclause, d_context.getSymbolTable(), d_imports, d_requires, d_msgs);      
      if (tmp_sym == null) {
        d_msgs.addMsg(new UserMsg(UserMsg.ERROR,
                                  "Cannot resolve interface given in from clause", fclause));
        return data;
      }
      
      if(!( tmp_sym instanceof  gov.llnl.babel.symbols.Interface)) {
        d_msgs.addMsg(new UserMsg(UserMsg.ERROR,
                                  "Symbol given in from clause is not an interface", fclause));
        return data;
      }
      ext.addRenamedMethod(method,oldMethod, tmp_sym.getSymbolID());
      
    } else {
      ext.addMethod(method);
    }
    return result;
  }

  public Object visitEnsures(Ensures node, Object data)
  {
    Integer aType = new Integer(gov.llnl.babel.symbols.Assertion.ENSURE);
    gov.llnl.babel.symbols.Method method = 
      (gov.llnl.babel.symbols.Method) data;
    Iterator i = node.getEnsureExprs().iterator();
    while (i.hasNext()) {
      ASTNode n = (ASTNode)i.next();
      gov.llnl.babel.symbols.Assertion a = (gov.llnl.babel.symbols.Assertion)
        n.accept(this, aType);
      try {
        method.addClauseAssertion(a);
      }
      catch (AssertionException ae) {
        d_msgs.addMsg(new UserMsg(UserMsg.ERROR, "unable to convert assertion",
                                  n));
      }
    }
    return data;
  }

  public Object visitRequires(Requires node, Object data)
  {
    Integer aType = new Integer(gov.llnl.babel.symbols.Assertion.REQUIRE);
    gov.llnl.babel.symbols.Method method = 
      (gov.llnl.babel.symbols.Method) data;
    Iterator i = node.getRequireExprs().iterator();
    while (i.hasNext()) {
      ASTNode n = (ASTNode)i.next();
      gov.llnl.babel.symbols.Assertion a = (gov.llnl.babel.symbols.Assertion)
        n.accept(this, aType);
      try {
        method.addClauseAssertion(a);
      }
      catch (AssertionException ae) {
        d_msgs.addMsg(new UserMsg(UserMsg.ERROR, "unable to convert assertion",
                                  n));
      }
    }
    return data;
  }

  public Object visitInvariants(Invariants node, Object data)
  {
    Integer aType = new Integer(gov.llnl.babel.symbols.Assertion.INVARIANT);
    gov.llnl.babel.symbols.Extendable ext = 
      (gov.llnl.babel.symbols.Extendable) data;
    Iterator i = node.getInvariantExprs().iterator();
    while (i.hasNext()) {
      ASTNode n = (ASTNode)i.next();
      gov.llnl.babel.symbols.Assertion a = (gov.llnl.babel.symbols.Assertion)
        n.accept(this, aType);
      try {
        ext.addInvAssertion(a);
      }
      catch (AssertionException ae) {
        d_msgs.addMsg(new UserMsg(UserMsg.ERROR, "unable to convert assertion",
                                  n));
      }
    }
    return data;
  }

  public Object visitAssertion(Assertion node, Object data)
  {
    Integer aType = (Integer)data;
    String name = (node.getName() != null) ? node.getName().toString() : null;
    try {
      gov.llnl.babel.symbols.Assertion a =  
        new gov.llnl.babel.symbols.Assertion
        (aType.intValue(), node.getSource(), name, null);
      a.setExpression((gov.llnl.babel.symbols.AssertionExpression)
                      node.getExpr().accept(new ExprConverter(), null));
      data = a;
    }
    catch (AssertionException ae) {
      d_msgs.addMsg(new UserMsg(UserMsg.ERROR, "unable to convert assertion",
                                node));
    }
    return data;
  }

  public Object visitArgument(Argument node, Object data) {
    gov.llnl.babel.symbols.Method method = (gov.llnl.babel.symbols.Method) data;
    String name = node.getName().toString();

    gov.llnl.babel.symbols.Type t = (gov.llnl.babel.symbols.Type) node
        .getType().accept(this, null);
    int mode = resolveMode(node);

    // create the argument symbol and bind it to the method
    gov.llnl.babel.symbols.Argument arg = new gov.llnl.babel.symbols.Argument(
        mode, t, name);
    SymbolTablePrimer.copyAttributes(node.getAttributeList(), arg);
    method.addArgument(arg);

    // comments
    gov.llnl.babel.symbols.Comment comment = null;
    if (node.getDocComment() != null) {
      comment = new gov.llnl.babel.symbols.Comment(node.getDocComment()
          .toString().split("\n"));
    } else {
      comment = new gov.llnl.babel.symbols.Comment(null);
    }
    arg.setComment(comment);

    return data;
  }

  public Object visitThrowsList(ThrowsList node, Object data) {
    gov.llnl.babel.symbols.Method method = (gov.llnl.babel.symbols.Method) data;
    for (Iterator it = node.iterator(); it.hasNext();) {
      ScopedID id = (ScopedID) it.next();
      gov.llnl.babel.symbols.Type ex_type = (gov.llnl.babel.symbols.Type) id
          .accept(this, data);
      gov.llnl.babel.symbols.SymbolID symid = ex_type.getSymbolID();
      // hack so we know which exceptions are explicit and implicit
      if (symid.getFullName().equals(SIDL_IMPLICIT_THROW.substring(1))) {
        method.addImplicitThrows(symid);
      } else {
        method.addThrows(symid);
      }
    }
    return data;
  }

  public Object visitArrayType(ArrayType node, Object data) {
    Type scalarType = node.getScalarType();
    gov.llnl.babel.symbols.Type s_scalarType = null; // Default generic
    int order = gov.llnl.babel.symbols.Type.UNSPECIFIED;
    if (node.isRowMajor()) {
      order = gov.llnl.babel.symbols.Type.ROW_MAJOR;
    } else if (node.isColMajor()) {
      order = gov.llnl.babel.symbols.Type.COLUMN_MAJOR;
    }
    // Generic arrays have null types
    if (scalarType != null) {
      s_scalarType = (gov.llnl.babel.symbols.Type) scalarType
          .accept(this, data);
    }
    return new gov.llnl.babel.symbols.Type(s_scalarType, node.getDimension(),
        order, d_context);
  }

  public Object visitRArrayType(RArrayType node, Object data) {
    Type scalarType = node.getScalarType();
    LinkedList extents = node.getExtents().getExtents();
    Vector indicies = new Vector(extents.size());
    gov.llnl.babel.symbols.Type s_scalarType = null; // Default generic
    for (Iterator i = extents.iterator(); i.hasNext();) {
      ASTNode extent = (ASTNode)i.next();
      gov.llnl.babel.symbols.AssertionExpression ae = 
        (gov.llnl.babel.symbols.AssertionExpression)
        extent.accept(new ExprConverter(), null);
      indicies.add(ae);
      
    }

    // Generic arrays have null types
    if (scalarType != null) {
      s_scalarType = (gov.llnl.babel.symbols.Type) scalarType
          .accept(this, data);
      // gov.llnl.babel.symbols.Type s_scalarType =
      // visit(scalarType,data);
    } else {
      s_scalarType = null;
    }
    return new gov.llnl.babel.symbols.Type(s_scalarType, node.getDimension(),
        indicies, d_context);
  }

  public Object visitStructType(StructType node, Object data)
  {
    gov.llnl.babel.symbols.Struct s = (gov.llnl.babel.symbols.Struct)
      node.getSymbolTableEntry();
    SymbolTablePrimer.copyAttributes(node.getAttributeList(), s);
    Iterator i = node.getStructItemList().iterator();
    while(i.hasNext()) {
      gov.llnl.babel.symbols.Struct.Item item = 
        (gov.llnl.babel.symbols.Struct.Item)((StructItem)i.next()).accept(this, data);
      if (item != null) {
        s.addItem(item);
      }
    }
    return s;
  }
  
  public Object visitStructItem(StructItem node, Object data)
  {
    String itemName = node.getName().toString();
    gov.llnl.babel.symbols.Type iType = (gov.llnl.babel.symbols.Type)node.getType().accept(this, data);
    if ((iType != null) && (iType.getBasicType() != gov.llnl.babel.symbols.Type.VOID)){
      return new gov.llnl.babel.symbols.Struct.Item(itemName, iType);
    }
    else {
      return null;
    }
  }


  public Object visitFixedType(FixedType node, Object data) {
    String name = node.getTypeName();
    final int i_max = gov.llnl.babel.symbols.Type.s_names.length;
    for (int i = 0; i < i_max; ++i) {
      if (name.equals(gov.llnl.babel.symbols.Type.s_names[i])) {
        return new gov.llnl.babel.symbols.Type(i);
      }
    }
    d_msgs.addMsg(new UserMsg(UserMsg.ERROR, "Unable to resolve Basic Type "
        + name + " from line", node));
    return new gov.llnl.babel.symbols.Type(0); // What type on error?
  }

  public Object visitScopedID(ScopedID node, Object data) {
    // first we need to find the enclosing package
    ASTNode p = node.getParent();
    while (!(p instanceof Package) && p != null) {
      p = p.getParent();
    }
    Package pkg = (Package) p;

    // now resolve the symbol and create a type
    gov.llnl.babel.symbols.Symbol s = TypeResolver.resolveType(node.toString(), pkg, node, 
        d_context.getSymbolTable(), d_imports, d_requires, d_msgs);
    if (s == null) {
      String name = node.toString();
      d_msgs.addMsg(new UserMsg(UserMsg.ERROR, "Cannot resolve type "
          + name, node));
      return new gov.llnl.babel.symbols.Type
        (new gov.llnl.babel.symbols.SymbolID
         (name, new gov.llnl.babel.symbols.Version()), d_context);
    }
    return new gov.llnl.babel.symbols.Type(s.getSymbolID(), d_context);
  }

  int resolveMode(Argument node) {
    String[] modes = gov.llnl.babel.symbols.Argument.getAllowableModes();
    String mode = node.getMode();
    final int i_max = modes.length;
    for (int i = 0; i < i_max; ++i) {
      if (mode.equals(modes[i])) {
        return i;
      }
    }
    return -1;
  }

  public Object visitSplicerList(SplicerList sl, Object data)
  {
    for (Iterator i = sl.getList().iterator(); i.hasNext();) {
      SplicerBlock sb = (SplicerBlock) i.next();
      sb.accept(this, data);
    }
    return data;
  }

  private void addSplicerBlock(gov.llnl.babel.symbols.Class cls, 
                               SplicerBlock sb) 
  {
    String loc  = sb.getLocation();
    String name = sb.getName();

    SplicerImplList list = sb.getSplicerImplList();
    if (list != null) {
      for (Iterator i = list.getList().iterator(); i.hasNext();) {
        SplicerImpl si = (SplicerImpl) i.next();
        cls.addSplicerContents(loc, name, si.getImpl());
      }
    }
  }

  private void addSplicerBlock(gov.llnl.babel.symbols.Method meth, 
                               SplicerBlock sb) 
  {
    String loc  = sb.getLocation();
    String name = sb.getName();

    SplicerImplList list = sb.getSplicerImplList();
    if (list != null) {
      for (Iterator i = list.getList().iterator(); i.hasNext();) {
        SplicerImpl si = (SplicerImpl) i.next();
        meth.addSplicerContents(loc, name, si.getImpl());
      }
    }
  }

  public Object visitSplicerBlock(SplicerBlock sb, Object data)
  {
    if (data instanceof gov.llnl.babel.symbols.Class) {
      addSplicerBlock((gov.llnl.babel.symbols.Class)data, sb);
    } else if (data instanceof gov.llnl.babel.symbols.Method) {
      addSplicerBlock((gov.llnl.babel.symbols.Method)data, sb);
    } else {
      d_msgs.addMsg(new UserMsg(UserMsg.ERROR,
             "Unrecognized symbol object type: Cannot add splicer block (loc=" 
             + sb.getLocation() + ", name tag=" + sb.getName() + ")") );
    }

    return data;
  }

  public Object visitSplicerImplList(SplicerImplList sil, Object data) {
    return data;
  }

  public Object visitSplicerImpl(SplicerImpl impl, Object data) {
    return data;
  }

}
