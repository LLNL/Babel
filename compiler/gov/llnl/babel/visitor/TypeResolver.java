package gov.llnl.babel.visitor;

import gov.llnl.babel.ast.ASTNode;
import gov.llnl.babel.ast.ImportClause;
import gov.llnl.babel.ast.Package;
import gov.llnl.babel.ast.RequireClause;
import gov.llnl.babel.msg.MsgList;
import gov.llnl.babel.msg.UserMsg;
import gov.llnl.babel.symbols.SymbolTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class TypeResolver {

  /**
   * A new implementation of type resolution that obeys the leading dot feature
   * even though it is not integrated into the logic of the underlying symbol
   * table
   * 
   * @param name
   *          An identifier as found in a SIDL file (version to be inferred)
   * @param context
   *          The package in which reference to the symbol is made
   * @param err_node
   *          A back-up node to report the error on. (If contex is null)
   * @return appropriate symbol
   */

  public static gov.llnl.babel.symbols.Symbol resolveType(String name,
      Package context, ASTNode err_node, SymbolTable st, ArrayList imports,
      HashMap requires, MsgList msgs) {
    gov.llnl.babel.symbols.Symbol result = null;
    Package cur_scope = context;
    String prefix = null;
    ImportClause cur_import = null;
    // If no leading dot...
    if (name.charAt(0) != '.') {
      // ... first try in most refined scope and work upwards
      // In this case, restrict statements play no part
//&& (cur_scope.getVersion() != null) 
      while ((cur_scope != null) && (result == null)) {
        prefix = cur_scope.getFQN().substring(1);
        result = st.resolveSymbol(prefix + "." + name);
        if (result != null) {
          return result;
        }
        ASTNode parent = cur_scope.getParent();
        cur_scope = (parent instanceof Package) ? (Package) parent : null;
      }

      // Try imports if the symbol was not found in scope
      if ((result == null) && (!imports.isEmpty())) {
        // for each import, check for the symbol
        for (Iterator i = imports.iterator(); i.hasNext() && result == null;) {
          cur_import = (ImportClause) i.next();
          prefix = cur_import.getScopedID().toString();
          result = st.resolveSymbol(prefix + "." + name);
        }
        if (result != null) {
          // Check version against restrictions, if it fails, error
          RequireClause rc = (RequireClause) requires.get(prefix);
          if (rc == null) {
            return result; // No requires on this symbol
          }
          String r_version = rc.getVersion().toString();
          gov.llnl.babel.symbols.Version rv = new gov.llnl.babel.symbols.Version(
              r_version);
          gov.llnl.babel.symbols.Version sv = result.getSymbolID().getVersion();
          if (sv.isSame(rv)) {
            return result;
          } else {
            msgs.addMsg(new UserMsg(UserMsg.ERROR,
                "Unable to resolve required package " + prefix + " "
                    + rv.getVersionString() + " from imported", cur_import));
            return null;
          }
        }
      }
    } 

    //If we can't resolve it in scope, (or it leads with a ".") try it as a fully qualified name  
     if(result == null) {
       result = resolveFQN(name, (context != null) ? context : err_node, st, requires, msgs);
     }
    
    return result;
  }

  public static gov.llnl.babel.symbols.Symbol resolveFQN(String name, ASTNode err_node, 
      SymbolTable st, HashMap requires, MsgList msgs) {
    gov.llnl.babel.symbols.Symbol result = null;
    String prefix = null;
    
    if (name.charAt(0) == '.') { // if dot, remove it and treat string as FQN.
      name = name.substring(1);
    }

    result = st.resolveSymbol(name);
    if (result != null) {
      if(requires == null) {
        return result; // No requires at this point
      }
      // Check version against requiress, if it fails, error
      int last_dot = name.lastIndexOf('.');
      prefix = name.substring(0, last_dot);
      RequireClause rc = (RequireClause) requires.get(prefix);
      if (rc == null) {
        return result; // No requires on this symbol
      }
      String r_version = rc.getVersion().toString();
      gov.llnl.babel.symbols.Version rv = new gov.llnl.babel.symbols.Version(
          r_version);
      gov.llnl.babel.symbols.Version sv = result.getSymbolID().getVersion();
      if (sv.isSame(rv)) {
        return result;
      } else {
        msgs.addMsg(new UserMsg(UserMsg.ERROR,
            "Unable to resolve required package", rc));
        return null;
      }
    } else {
      msgs.addMsg(new UserMsg(UserMsg.ERROR, "Unable to resolve type " + name,
          err_node));
    }

  return result;
  } 
}


