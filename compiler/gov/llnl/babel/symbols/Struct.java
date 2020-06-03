package gov.llnl.babel.symbols;

import gov.llnl.babel.Context;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * The <code>Struct</code> contains a list of named types.
 * 
 * @author kumfert
 */
public class Struct extends Symbol {
  /**
   * Holds a list of {@link Item}'s.
   */
  private List d_items;

  public static class Item {
    private String d_name;
    private Type   d_type;

    public Item(String name, Type dtype)
    {
      d_name = name;
      d_type = dtype;
    }

    public String getName() { return d_name; }
    public Type   getType() { return d_type; }
  };

  public Struct(SymbolID id, Comment comment, Context context) {
    super(id, Symbol.STRUCT, comment, context);
    d_items = new ArrayList();
  }

  public Struct(SymbolID id, Comment comment, Metadata metadata, 
                Context context) {
    super(id, Symbol.STRUCT, comment, metadata, context);
    d_items = new ArrayList();
  }

  static private SymbolID getType(Type t)
  {
    SymbolID result = t.getSymbolID();
    if (result == null) {
      if (t.isArray()) {
        result = getType(t.getArrayType());
      }
    }
    return result;
  }

  public boolean hasType(final int type)
  {
    Iterator i = d_items.iterator();
    while (i.hasNext()) {
      Type t = ((Item)i.next()).getType();
      if ((type == t.getDetailedType()) ||
          ((Type.ARRAY == t.getDetailedType()) &&
           (type == t.getArrayType().getDetailedType()))) {
        return true;
      }
    }
    return false;
  }

  public boolean hasTypeEmbedded(final int type)
  {
    Iterator i = d_items.iterator();
    while (i.hasNext()) {
      Type t = ((Item)i.next()).getType();
      final int dt = t.getDetailedType();
      if ((type == dt) ||
          ((Type.ARRAY == dt) &&
           (type == t.getArrayType().getDetailedType()))) return true;
      if (Type.STRUCT == dt) {
        SymbolTable st = d_context.getSymbolTable();
        Symbol sym = st.lookupSymbol(t.getSymbolID());
        if ((sym != null) && (sym instanceof Struct) && ((Struct)sym).hasTypeEmbedded(type))
          return true;
      }
    }
    return false;
  }

  public Set getSymbolReferences() {
    HashSet s = new HashSet();
    Iterator i = d_items.iterator();
    while (i.hasNext()) {
      Type t = ((Item)i.next()).getType();
      SymbolID id = getType(t);
      if (id != null) {
        s.add(id);
      }
      
    }
    return s;
  }

  public Set getAllSymbolReferences() {
    Set result = getSymbolReferences();
    result.add(getSymbolID());
    return result;
  }

  public Set getBasicArrayRefs() {
    return null;
  }
  
  /**
   * 
   * @return ordered list of items. Each element is an {@link Item}.
   */
  public List getItems() { 
    return d_items;
  }

  public int addItem(Item i)
  {
    checkFrozen();
    d_items.add(i);
    return d_items.size();
  }

  public boolean hasArrayReference()
  {
    Iterator i = d_items.iterator();
    while (i.hasNext()) {
      if (((Item)i.next()).getType().isArray()) return true;
    }
    return false;
  }

  /**
   * 
   * @return true if this struct contains an rarray, either directly or via
   * an {@link Item} that contains an rarray item.
   */
  public boolean hasRarrayReference()
  {
    Iterator i = d_items.iterator();
    while (i.hasNext()) {
      Item e = (Item)i.next(); 
      if (e.getType().isRarray()) {
        return true;
      }
      if (e.getType().isStruct()) {
        if (((Struct)d_context.getSymbolTable().
            lookupSymbol(e.getType().getSymbolID())).hasRarrayReference()) {
            return true;
        }
      }
    }
    return false;
  }


  /**
   * Insert a new type and name into the  struct
   * @return new number of items in the struct
   */
  public int addItem( String name, Type type ) { 
    d_items.add(new Item(name, type));
    return d_items.size();
  }

  public void freeze()
  {
    if (!d_frozen) {
      super.freeze();
      d_items = protectList(d_items);
      Iterator i = d_items.iterator();
      while (i.hasNext()) {
        Struct.Item item = (Struct.Item)i.next();
        item.getType().freeze();
      }
    }
  }
}
