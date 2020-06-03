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
import gov.llnl.babel.ast.DocComment;
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
import gov.llnl.babel.ast.MethodList;
import gov.llnl.babel.ast.Name;
import gov.llnl.babel.ast.NamedType;
import gov.llnl.babel.ast.Package;
import gov.llnl.babel.ast.RArrayType;
import gov.llnl.babel.ast.RequireClause;
import gov.llnl.babel.ast.Requires;
import gov.llnl.babel.ast.SIDLFile;
import gov.llnl.babel.ast.ScopedID;
import gov.llnl.babel.ast.ScopedIDList;
import gov.llnl.babel.ast.SplicerBlock;
import gov.llnl.babel.ast.SplicerImpl;
import gov.llnl.babel.ast.SplicerImplList;
import gov.llnl.babel.ast.SplicerList;
import gov.llnl.babel.ast.StringLiteral;
import gov.llnl.babel.ast.StructItem;
import gov.llnl.babel.ast.StructType;
import gov.llnl.babel.ast.ThrowsList;
import gov.llnl.babel.ast.Type;
import gov.llnl.babel.ast.TypeSearchClause;
import gov.llnl.babel.ast.UnaryExpr;

import java.util.Iterator;

/**
 * 
 * Base class for all visitors over gov.llnl.babel.ast.*
 * 
 * This default implementation simply iterates over everything. Use as a base
 * class and simply override the parts that you are interested in
 */
public class Visitor {

  public Object visitNode(ASTNode node, Object data) {
    return data;
  }

  public Object visitSIDLFile(SIDLFile node, Object data) {
    for (Iterator i = node.getTypeSearchList().iterator(); i.hasNext();) {
      TypeSearchClause tsc = (TypeSearchClause) i.next();
      tsc.accept(this, data);
    }
    for (Iterator i = node.getPackageList().iterator(); i.hasNext();) {
      Package p = (Package) i.next();
      p.accept(this, data);
    }
    return data;
  }

  public Object visitImportClause(ImportClause node, Object data) {
    return data;
  }

  public Object visitRequireClause(RequireClause node, Object data) {
    return data;
  }

  public Object visitPackage(Package node, Object data) {
    node.getAttributeList().accept(this, data);
    for (Iterator i = node.getNamedTypes().iterator(); i.hasNext();) {
      NamedType t = (NamedType) i.next();
      t.accept(this, data);
    }
    return data;
  }

  public Object visitAttribute( Attribute node, Object data ) { 
    return data;
  }
  
  public Object visitAttributeList( AttributeList node, Object data ) { 
    for (Iterator i = node.iterator(); i.hasNext(); ) {
      Attribute a = (Attribute) i.next();
      a.accept(this, data);
    }
    return data;
  }
  
  public Object visitEnumeration( Enumeration node, Object data ) {
    node.getAttributeList().accept(this, data);
    for(Iterator i = node.getEnumItemList().iterator(); i.hasNext(); ) { 
      EnumItem e = (EnumItem) i.next();
      e.accept(this, data);
    }
    return data;
  }
  

  public Object visitEnumItem( EnumItem node, Object data ) {
    return data;
  }
  
  public Object visitClassType(ClassType node, Object data) {
    Invariants inv = node.getInvariants();
    if ( inv != null ) { 
      inv.accept(this, data);
    }
    if (node.getSplicerList() != null) {
      node.getSplicerList().accept(this, data);
    }
    MethodList ml = node.getMethodList();
    ml.accept(this, data);
    node.getAttributeList().accept(this, data);
    return data;
  }

  public Object visitInterfaceType(InterfaceType node, Object data) {
    Invariants inv = node.getInvariants();
    if ( inv != null ) { 
      inv.accept(this, data);
    }
    MethodList ml = node.getMethodList();
    ml.accept(this,data);
    node.getAttributeList().accept(this, data);
    return data;
  }

  public Object visitArrayType(ArrayType node, Object data) {
    Type stype = node.getScalarType();
    if (stype != null) {
      return stype.accept(this, data);
    }
    return data;
  }

  public Object visitRArrayType(RArrayType node, Object data) {
    node.getScalarType().accept(this, data);
    node.getExtents().accept(this, data);
    return data;
  }

  public Object visitExtents(Extents node, Object data) { 
    for(Iterator it=node.getExtents().iterator(); it.hasNext();) { 
      ASTNode n = (ASTNode) it.next();
      n.accept(this,data);
    }
    return data;
  }
  
  public Object visitFixedType(FixedType node, Object data) {
    return new gov.llnl.babel.symbols.Type( gov.llnl.babel.symbols.Type.BOOLEAN);
  }

  public Object visitArgument(Argument node, Object data) {
    Type t = node.getType();
    node.getName().accept(this, data);
    node.getAttributeList().accept(this, data);
    t.accept(this,data);
    return data;
  }

  public Object visitArgumentList(ArgumentList node, Object data ) { 
    for (Iterator it = node.iterator(); it.hasNext(); ) { 
      Argument arg = (Argument) it.next();
      arg.accept(this, data);
    }
    return data;
  }
  
  public Object visitDocComment(DocComment node, Object data) {
    return data;
  }

  public Object visitMethod(Method node, Object data) {
    node.getArgumentList().accept(this, data);
    node.getAttributeList().accept(this, data);
    node.getThrowsList().accept(this,data);
    if ( node.getFromClause() != null ) { 
      node.getFromClause().accept(this,data);
    }
    if ( node.getRequires() != null) {
      node.getRequires().accept(this, data);
    }
    if ( node.getEnsures() != null) {
      node.getEnsures().accept(this, data);
    }
    if (node.getSplicerList() != null) {
      node.getSplicerList().accept(this, data);
    }
    return data;
  }


  public Object visitMethodList( MethodList node, Object data ) { 
    for (Iterator i = node.iterator(); i.hasNext(); ) {
      Method m = (Method) i.next();
      m.accept(this, data);
    }
    return data;
  }

  public Object visitScopedID(ScopedID node, Object data ) { 
    return data;
  }
  
  public Object visitScopedIDList(ScopedIDList node, Object data ) { 
    for (Iterator it = node.iterator(); it.hasNext(); ) { 
      ScopedID id = (ScopedID) it.next();
      id.accept(this, data);
    }
    return data;
  }
  
  public Object visitThrowsList(ThrowsList node, Object data) { 
    for (Iterator it = node.iterator(); it.hasNext(); ) { 
      ScopedID id = (ScopedID) it.next();
      id.accept(this, data);
    }
    return data;
  }
  
  public Object visitFromClause(FromClause node, Object data) { 
    return data;
  }
  
  public Object visitStructType(StructType node, Object data) { 
    node.getAttributeList().accept(this, data);
    for(Iterator i = node.getStructItemList().iterator(); i.hasNext(); ) { 
      StructItem e = (StructItem) i.next();
      e.accept(this, data);
    }
    return data;
  }
  
  public Object visitStructItem(StructItem node, Object data) { 
    return data;
  }

  public Object visitBinaryExpr(BinaryExpr node, Object data) {
    ASTNode lhs = node.getLHS();
    ASTNode rhs = node.getRHS();
    if (lhs != null) {
      lhs.accept(this, data);
    }
    if (rhs != null) {
      rhs.accept(this, data);
    }
    return data;
  }

  public Object visitUnaryExpr(UnaryExpr node, Object data) {
    ASTNode operand = node.getOperand();
    if (operand != null) {
      operand.accept(this, data);
    }
    return data;
  }

  public Object visitFuncExpr(FuncExpr fe, Object data) {
    if (fe.getArguments() != null) {
      Iterator i = fe.getArguments().iterator();
      while(i.hasNext()) {
        ((ASTNode)i.next()).accept(this, data);
      }
    }
    return data;
  }

  public Object visitRequires(Requires r, Object data){
    Iterator i = r.getRequireExprs().iterator();
    while (i.hasNext()) {
      ((ASTNode)i.next()).accept(this, data);
    }
    return data;
  }

  public Object visitEnsures(Ensures r, Object data){
    Iterator i = r.getEnsureExprs().iterator();
    while (i.hasNext()) {
      ((ASTNode)i.next()).accept(this, data);
    }
    return data;
  }

  public Object visitInvariants(Invariants inv, Object data) { 
    Iterator i = inv.getInvariantExprs().iterator();
    while (i.hasNext()) {
      ((ASTNode)i.next()).accept(this, data);
    }
    return data;
  }



  public Object visitIntLiteral(IntLiteral il, Object data) {
    return data;
  }

  public Object visitDoubleLiteral(DoubleLiteral dl, Object data) {
    return data;
  }

  public Object visitFloatLiteral(FloatLiteral dl, Object data) {
    return data;
  }

  public Object visitDComplexLiteral(DComplexLiteral dl, Object data) {
    return data;
  }

  public Object visitFComplexLiteral(FComplexLiteral dl, Object data) {
    return data;
  }

  public Object visitStringLiteral(StringLiteral dl, Object data) {
    return data;
  }

  public Object visitCharacterLiteral(CharacterLiteral dl, Object data) {
    return data;
  }

  public Object visitBooleanLiteral(BooleanLiteral dl, Object data) {
    return data;
  }

  public Object visitName(Name n, Object data) {
    return data;
  }

  public Object visitAssertion(Assertion a, Object data) {
    Name n = a.getName();
    if (n != null) {
      n.accept(this, data);
    }
    return a.getExpr().accept(this, data);
  }

  public Object visitSplicerList( SplicerList node, Object data ) { 
    for (Iterator i = node.iterator(); i.hasNext(); ) {
      SplicerBlock s = (SplicerBlock) i.next();
      s.accept(this, data);
    }
    return data;
  }
  
  public Object visitSplicerBlock( SplicerBlock node, Object data ) { 
    SplicerImplList sil = node.getSplicerImplList();
    return sil.accept(this, data);
  }

  public Object visitSplicerImplList( SplicerImplList node, Object data ) { 
    for (Iterator i = node.iterator(); i.hasNext(); ) {
      SplicerImpl si = (SplicerImpl) i.next();
      si.accept(this, data);
    }
    return data;
  }

  public Object visitSplicerImpl( SplicerImpl node, Object data ) { 
    return data;
  }
  
}
