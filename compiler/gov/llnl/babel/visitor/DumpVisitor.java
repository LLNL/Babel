package gov.llnl.babel.visitor;

import gov.llnl.babel.ast.ASTNode;
import gov.llnl.babel.ast.Argument;
import gov.llnl.babel.ast.ArgumentList;
import gov.llnl.babel.ast.ArrayType;
import gov.llnl.babel.ast.Assertion;
import gov.llnl.babel.ast.Attribute;
import gov.llnl.babel.ast.AttributeList;
import gov.llnl.babel.ast.BinaryExpr;
import gov.llnl.babel.ast.BooleanLiteral;
import gov.llnl.babel.ast.CharacterLiteral;
import gov.llnl.babel.ast.ClassType;
import gov.llnl.babel.ast.DComplexLiteral;
import gov.llnl.babel.ast.DoubleLiteral;
import gov.llnl.babel.ast.Ensures;
import gov.llnl.babel.ast.EnumItem;
import gov.llnl.babel.ast.Enumeration;
import gov.llnl.babel.ast.Extents;
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
import gov.llnl.babel.ast.Name;
import gov.llnl.babel.ast.Package;
import gov.llnl.babel.ast.RArrayType;
import gov.llnl.babel.ast.RequireClause;
import gov.llnl.babel.ast.Requires;
import gov.llnl.babel.ast.SIDLFile;
import gov.llnl.babel.ast.ScopedID;
import gov.llnl.babel.ast.SplicerBlock;
import gov.llnl.babel.ast.SplicerImpl;
import gov.llnl.babel.ast.SplicerImplList;
import gov.llnl.babel.ast.SplicerList;
import gov.llnl.babel.ast.StringLiteral;
import gov.llnl.babel.ast.StructItem;
import gov.llnl.babel.ast.StructType;
import gov.llnl.babel.ast.ThrowsList;
import gov.llnl.babel.ast.UnaryExpr;

import java.io.PrintStream;
import java.util.Iterator;

/**
 * PrettyPrints a SIDL AST to a PrintStream (useful for development and
 * debugging)
 */
public class DumpVisitor extends Visitor {

  protected PrintStream d_out;

  private int d_indent = 0;

  public DumpVisitor(PrintStream ps) {
    super();
    d_out = ps;
  }

  private String indentString() {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < d_indent; ++i) {
      sb.append("    ");
    }
    return sb.toString();
  }

  private void printAttributes(AttributeList attrs, boolean withIndent) { 
    if ( attrs == null ) { return; }
    if ( withIndent) { 
      d_out.print(indentString());
    }
    boolean inAttribRun = false;
    for( Iterator i = attrs.iterator(); i.hasNext(); ) {
      Attribute a = (Attribute) i.next();
      if ( a.isBuiltin() ) { 
        if ( inAttribRun ) { 
          d_out.print(" }");
        }
        inAttribRun = false;
      } else { 
        if ( inAttribRun ) { 
          d_out.print(",");
        } else {
          d_out.print(" %attrib{");
        }
        inAttribRun = true;
      }
      d_out.print(" " + a);
    }
    if ( inAttribRun ) { 
      d_out.print(" } ");
    } else { 
      d_out.print(" ");
    }
  }
  
  public Object visitSIDLFile(SIDLFile node, Object data) {
    d_out.println(indentString() + "-------------- begin ("
                  + node.getFilename() + ")-----------");
    super.visitSIDLFile(node, data);
    d_out.println(indentString() + "---------------end file--------------");
    return data;
  }

  public Object visitImportClause(ImportClause node, Object data) {
    d_out.print(indentString() + "import " + node.getScopedID());
    if ((node.getVersion() != null)
        && (node.getVersion().toString().length() > 0)) {
      d_out.print(" version " + node.getVersion());
    }
    d_out.println(";");
    return data;
  }

  public Object visitRequireClause(RequireClause node, Object data) {
    d_out.print(indentString() + "require " + node.getScopedID());
    if (node.getVersion().toString().length() > 0) {
      d_out.print(" version " + node.getVersion());
    }
    d_out.println(";");
    return data;
  }

  public Object visitPackage(Package node, Object data) {
    if (node.hasDocComment()) { 
      d_out.println("/** " + node.getDocComment() +  "*/");
    }
    if ( node.getAttributeList() != null ) { 
      final boolean withIndent = true;
      printAttributes( node.getAttributeList(), withIndent );
    }
    d_out.print("package " + node.getName());
    if (node.getVersion() != null
        && node.getVersion().toString().length() > 0) {
      d_out.print(" version " + node.getVersion());
    }
    d_out.println(" {");
    d_indent++;
    super.visitPackage(node, data);
    d_indent--;
    d_out.println(indentString() + "}");
    return data;
  }
  
  public Object visitEnumeration( Enumeration node, Object data ) {
    if (node.hasDocComment()) { 
      d_out.println("/** " +node.getDocComment()+  "*/");
    }
    printAttributes( node.getAttributeList(), true );
    d_out.println("enum " + node.getName() + " {");
    d_indent++;
    super.visitEnumeration(node, data);
    d_indent--;
    d_out.println(indentString() + "}");
    return data;
  }
  
  public Object visitEnumItem( EnumItem node, Object data ) { 
    d_out.println( indentString() + node.toString() + ", ");
    return data;
  }

  public Object visitStructType( StructType node, Object data ) { 
    if (node.hasDocComment()) { 
      d_out.println("/** " +node.getDocComment()+  "*/");
    }
    d_out.println(indentString() + "struct " + node.getName() + " {");
    d_indent++;
    super.visitStructType(node,data);
    d_indent--;
    d_out.println(indentString() + "}");
    return data;
  }
  
  public Object visitStructItem( StructItem node, Object data) { 
    d_out.println(indentString() + node.getType() + " " + node.getName() + ";");
    return data;
  }
  
  public Object visitInterfaceType(InterfaceType node, Object data) {
    if (node.hasDocComment()) { 
      d_out.println("/** " +node.getDocComment()+  "*/");
    }
    final boolean withIndent = true;
    printAttributes( node.getAttributeList(), withIndent );
    d_out.print("interface " + node.getName());
    if ( node.getExtends().size() > 0 ) { 
      d_out.print(" extends");
      for( Iterator it = node.getExtends().iterator(); it.hasNext(); ) { 
        d_out.print( " " + (ScopedID) it.next() );
      }
    }
    d_out.println(" {");
    d_indent++;
    super.visitInterfaceType(node, data);
    d_indent--;
    d_out.println(indentString() + "}");
    return data;
  }

  public Object visitClassType(ClassType node, Object data) {
    if (node.hasDocComment()) { 
      d_out.println("/** " +node.getDocComment()+  "*/");
    }
    final boolean withIndent = true;
    printAttributes( node.getAttributeList(), withIndent );
    d_out.print("class " + node.getName());
    if ( node.getExtends() != null ) {
      d_out.print(" extends " + node.getExtends() );
    }
    if ( node.getImplementsList() != null && node.getImplementsList().size() > 0 ) { 
      Iterator it = node.getImplementsList().iterator();
      int state=2;
      while( it.hasNext() ) { 
        ScopedID iface = (ScopedID) it.next();
        if ( node.getImplementsList().isImplementsAll(iface) ) { 
          if (state==1) { 
            d_out.print(", ");
          } else { 
            d_out.print(" implements-all ");
          }
          state=1;
        } else { 
          if (state==0) { 
            d_out.print(", ");
          } else { 
            d_out.print(" implements ");
          }
          state=0;
        }
        d_out.print( iface.toString() );
      } // end while
    }
    d_out.println(" {");
    d_indent++;
    super.visitClassType(node, data);
    d_indent--;
    d_out.println(indentString() + "}");
    return data;
  }
  
  public Object visitMethod(Method node, Object data) {
    if (node.hasDocComment()) { 
      d_out.println("/** " +node.getDocComment()+  "*/");
    }
    final boolean withIndent = true;
    printAttributes( node.getAttributeList(), withIndent);
    if (node.getReturnType() == null) { 
      d_out.print("void ");
    } else { 
      d_out.print(node.getReturnType().toString() + " ");
    }
    d_out.print(node.getMethodName().toString());
    node.getArgumentList().accept(this,data);
    node.getThrowsList().accept(this,data);
    if ( node.getFromClause() != null ) { 
      node.getFromClause().accept(this,data);
    }
    if (node.getRequires() != null) {
      d_out.println();
      node.getRequires().accept(this, data);
    }
    if (node.getEnsures() != null ) {
      d_out.println();
      node.getEnsures().accept(this, data);
    }
    if ((node.getRequires() != null) || (node.getEnsures() != null)) {
      d_out.println(indentString() + ";");
    }
    else{
      d_out.println(";");
    }
    if (node.getSplicerList() != null) {
      node.getSplicerList().accept(this, data);
    }
    return data;
  }
  
  public Object visitArgumentList(ArgumentList node, Object data ) { 
    d_out.print("(");
    for(Iterator it = node.iterator(); it.hasNext(); ) { 
      Argument arg = (Argument) it.next();
      arg.accept(this,data);
      if (it.hasNext()) { 
        d_out.print(", ");
      }
    }
    d_out.print(")");
    return data;
  }

  public Object visitArgument(Argument node, Object data) { 
    if (node.hasDocComment()) { 
      d_out.println("/** " +node.getDocComment()+  "*/");
    }
    if ( node.getAttributeList() != null ) { 
      final boolean withIndent=false;
      printAttributes( node.getAttributeList(), withIndent );
    }
    d_out.print( node.getMode() + " ");
    node.getType().accept(this,data);
    d_out.print(" " + node.getName() );
    if ( node.getType() instanceof RArrayType) { 
      RArrayType t = (RArrayType) node.getType();
      d_out.print("(");
      t.getExtents().accept(this,data);
      d_out.print(")");
    }
    return data;
  }

  public Object visitBinaryExpr(BinaryExpr be, Object data) {
    d_out.print("(");
    be.getLHS().accept(this, data);
    d_out.print(")" + BinaryExpr.getOpSIDL(be.getOperator()));
    d_out.print("(");
    be.getRHS().accept(this, data);
    d_out.print(")");
    return data;
  }

  public Object visitUnaryExpr(UnaryExpr ue, Object data) {
    d_out.print(UnaryExpr.getOpSIDL(ue.getOperator()));
    d_out.print("(");
    ue.getOperand().accept(this, data);
    d_out.print(")");
    return data;
  }

  
  public Object visitExtents(Extents node, Object data) { 
    for(Iterator it = node.getExtents().iterator(); it.hasNext(); ) { 
      ASTNode n = (ASTNode) it.next();
      n.accept(this, data);
      if (it.hasNext()) { 
        d_out.print(", ");
      }
    }
    return data;
  }
  
  public Object visitFixedType(FixedType node, Object data) { 
    d_out.print(node.toString());
    return data;
  }
  
  public Object visitArrayType(ArrayType node, Object data) { 
    d_out.print(node.toString());
    return data;
  }
  
  public Object visitRArrayType(RArrayType node, Object data) {
    d_out.print(node.toString());
    return data;
  }
  
  public Object visitScopedID(ScopedID node, Object data) { 
    d_out.print(node.toString());
    return data;
  }
  
  public Object visitThrowsList(ThrowsList node, Object data) { 
    if (node.size() > 0) { 
      d_out.print(" throws ");
      for(Iterator it = node.iterator(); it.hasNext(); ) { 
        ScopedID id = (ScopedID) it.next();
        id.accept(this,data);
        if (it.hasNext()) { 
          d_out.print(", ");
        }
      }
    }
    return data;
  }
  
  public Object visitFromClause(FromClause node, Object data) { 
    if (node != null) { 
      d_out.print(" from " + node.getScopedID() + "." + node.getMethodName() );        
    }
    return data;
  }

  public Object visitIntLiteral(IntLiteral il, Object data) {
    d_out.print(Integer.toString(il.getInt()));
    return data;
  }

  public Object visitBooleanLiteral(BooleanLiteral bl, Object data)
  {
    d_out.print(bl.getBoolean() ? "true" : "false");
    return data;
  }

  public Object visitCharacterLiteral(CharacterLiteral cl, Object data)
  {
    char ch = cl.getChar();
    if (Character.isISOControl(ch) || (ch == '\'') || (ch == '\\')) {
      d_out.print("'\\" + Integer.toOctalString(Character.getNumericValue(ch))
                  + "'");
    }
    else {
      d_out.print("'" + ch + "'");
    }
    return data;
  }

  public Object visitStringLiteral(StringLiteral sl, Object data)
  {
    d_out.print("\"" + sl.getString() + "\"");
    return data;
  }

  public Object visitFComplexLiteral(FComplexLiteral dl, Object data)
  {
    d_out.print(dl.toString());
    return data;
  }

  public Object visitDComplexLiteral(DComplexLiteral dl, Object data)
  {
    d_out.print(dl.toString());
    return data;
  }

  public Object visitFloatLiteral(FloatLiteral dl, Object data)
  {
    d_out.print(dl.toString());
    return data;
  }

  public Object visitDoubleLiteral(DoubleLiteral dl, Object data)
  {
    d_out.print(dl.toString());
    return data;
  }

  public Object visitInvariants(Invariants i, Object data) {
    d_out.println(indentString() + "invariant");
    try {
      ++d_indent;
      return super.visitInvariants(i, data);
    }
    finally {
      --d_indent;
    }
  }

  public Object visitEnsures(Ensures r, Object data) {
    d_out.println(indentString() + "ensure");
    try {
      ++d_indent;
      return super.visitEnsures(r, data);
    }
    finally {
      --d_indent;
    }
  }

  public Object visitRequires(Requires r, Object data) {
    d_out.println(indentString() + "require");
    try {
      ++d_indent;
      return super.visitRequires(r, data);
    }
    finally {
      --d_indent;
    }
  }

  public Object visitFuncExpr(FuncExpr fe, Object data) {
    d_out.print(fe.getName() + "(");
    Iterator i = fe.getArguments().iterator();
    while(i.hasNext()) {
      ((ASTNode)i.next()).accept(this, data);
      if (i.hasNext()) {
        d_out.print(", ");
      }
    }
    d_out.print(")");
    return data;
  }

  public Object visitName(Name n, Object data) {
    d_out.print(n.toString());
    return data;
  }

  public Object visitAssertion(Assertion a, Object data)
  {
    d_out.print(indentString());
    if (a.getName() != null) {
      a.getName().accept(this, data);
      d_out.print(":");
    }
    Object retval = a.getExpr().accept(this, data);
    d_out.println(";");
    return retval;
  }

  public Object visitSplicerList(SplicerList sl, Object data) 
  {
    d_indent++;
    for (Iterator i = sl.getList().iterator(); i.hasNext();) {
      SplicerBlock sb = (SplicerBlock) i.next();
      sb.accept(this, data);
    }
    d_indent--;
    return data;
  }
  
  public Object visitSplicerBlock(SplicerBlock sb, Object data) 
  {
    d_out.print(indentString() + "/** Splicer Block (tagged " + sb.getName());
    d_out.println(" in " + sb.getLocation() + ") */");
    if (sb.getSplicerImplList() != null) {
      sb.getSplicerImplList().accept(this, data);
    }
    d_out.println();
    return data;
  }

  public Object visitSplicerImplList(SplicerImplList sil, Object data) 
  {
    for (Iterator i = sil.getList().iterator(); i.hasNext();) {
      SplicerImpl si = (SplicerImpl) i.next();
      si.accept(this, data);
    }
    return data;
  }
  
  public Object visitSplicerImpl(SplicerImpl impl, Object data) 
  {
    d_out.println(indentString() + impl.toString());
    return data;
  }

}
