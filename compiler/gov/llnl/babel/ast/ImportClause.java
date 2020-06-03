package gov.llnl.babel.ast;

import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.visitor.Visitor;

/**
 * Used at the top of a SIDL file to assist in type resolution.
 */
public class ImportClause extends TypeSearchClause {

	public ImportClause(ParseTreeNode src, ASTNode parent, ScopedID id, Version version) {
		super(src, parent, id, version);
	}

	public Object accept(Visitor v, Object data) {
		return v.visitImportClause(this, data);
	}
}
