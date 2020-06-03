package gov.llnl.babel.ast;

import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.visitor.Visitor;

/**
 * Specifies a type and version to be used in type resolution.
 */
public class RequireClause extends TypeSearchClause {

	public RequireClause(ParseTreeNode src, ASTNode parent, ScopedID id, Version version) {
		super(src, parent, id, version);
	}

	public Object accept(Visitor v, Object data) {
		return v.visitRequireClause( this, data );
	}
}
