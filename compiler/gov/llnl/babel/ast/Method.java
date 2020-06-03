package gov.llnl.babel.ast;

import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.visitor.Visitor;

/**
 * Has a name, inheritance hierarchy, return type, argument list, exception 
 * list, and is embedded in a <code>Class</code> or <code>Interface</code>
 *
 * NOTE:  In this case, name is special in that name may be fullName, shortName,
 * extension, etc.  by default, name == fullName since this is what is subject 
 * to collision detection.
 */
public class Method extends ASTNode implements INameable,
IAttributable,INameableStruct {

  protected MethodName d_name;
  protected AttributeList d_attribList;
  protected Type d_returnType = null;
  protected DocComment d_comment = null;
  protected ArgumentList d_argList;
  protected ThrowsList d_throwsList;
  protected FromClause d_from;
  protected Requires d_requires = null;
  protected Ensures d_ensures = null;
  protected SplicerList d_splicers = null;

  protected Name d_name2;

  public Method(ParseTreeNode src, ASTNode parent) {
    super(src, parent);
    d_attribList = new AttributeList(null, this);
    d_argList = new ArgumentList(null, this);
    d_throwsList = new ThrowsList(null, this);
    if(src != null) {
      setDocComment(src.doc);
    }
  }

  public Method() {
    super(null, null);
    d_attribList = new AttributeList(null, this);
    d_argList = new ArgumentList(null, this);
    d_throwsList = new ThrowsList(null, this);
  }

  public Object accept(Visitor v, Object data) {
    return v.visitMethod( this, data );
  }

  public Name getName() {
    return d_name.getName();
  }

  public void setName(Name name) {
    if (name instanceof MethodName) { 
      d_name = (MethodName) name; 
    } else {
      if(d_name == null) {
        d_name = new MethodName();
      }
      d_name.setName(name);
    }
  }
  
  public MethodName getMethodName() { 
    return d_name;
  }
  
  public void setMethodName(MethodName name) { 
    d_name = name;
  }
  
  public AttributeList getAttributeList() {
    return d_attribList;
  }

  public void setAttributeList(AttributeList attrib) {
    d_attribList = attrib;
  }

  public ArgumentList getArgumentList() { 
    return d_argList;
  }
  
  public void setArgumentList(ArgumentList argList) { 
    d_argList = argList;
  }
  
  public ThrowsList getThrowsList() { 
    return d_throwsList;
  }
  
  public void setThrowsList(ThrowsList throwsList) { 
    d_throwsList = throwsList;
  }
  
  public Name getName2() {
    return d_name2;
  }

  public void setName2(Name name2) {
    d_name2 = name2;
  }

  public Type getReturnType() { 
    return d_returnType;
  }
  
  public void setReturnType(Type t ) { 
    d_returnType = t;
  }

  public boolean hasDocComment() {
    return d_comment != null;
  }

  public DocComment getDocComment() {
    return d_comment;
  }

  public void setDocComment(String text) {
    if (text == null || text.length() == 0) {
      d_comment = null;
    } else {
      d_comment = new DocComment(text);
    }
  }  

  public void setFromClause(FromClause from) { 
    d_from = from; 
  }
  
  public FromClause getFromClause() { 
    return d_from;
  }
  
  public boolean isRenamedFromAncestor() { 
    return d_from == null;
  }

  public Requires getRequires() {
    return d_requires;
  }

  public Ensures getEnsures() {
    return d_ensures;
  }

  public void setRequires(Requires requires) {
    d_requires = requires;
  }

  public void setEnsures(Ensures ensures) {
    d_ensures = ensures;
  }

  public void addSplicerBlock(SplicerBlock block) {
    if (d_splicers == null) {
      d_splicers = new SplicerList(null, this);
    }
    d_splicers.addSplicerBlock(block);
  }

  public SplicerList getSplicerList() {
    return d_splicers;
  }

}
