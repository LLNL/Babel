//
// File:        Type.java
// Package:     gov.llnl.babel.symbols
// Revision:    @(#) $Id: Type.java 7188 2011-09-27 18:38:42Z adrian $
// Description: class that represents a sidl type
//
// Copyright (c) 2000-2007, Lawrence Livermore National Security, LLC
// Produced at the Lawrence Livermore National Laboratory.
// Written by the Components Team <components@llnl.gov>
// UCRL-CODE-2002-054
// All rights reserved.
// 
// This file is part of Babel. For more information, see
// http://www.llnl.gov/CASC/components/. Please read the COPYRIGHT file
// for Our Notice and the LICENSE file for the GNU Lesser General Public
// License.
// 
// This program is free software; you can redistribute it and/or modify it
// under the terms of the GNU Lesser General Public License (as published by
// the Free Software Foundation) version 2.1 dated February 1999.
// 
// This program is distributed in the hope that it will be useful, but
// WITHOUT ANY WARRANTY; without even the IMPLIED WARRANTY OF
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the terms and
// conditions of the GNU Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public License
// along with this program; if not, write to the Free Software Foundation,
// Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

package gov.llnl.babel.symbols;

import gov.llnl.babel.Context;
import gov.llnl.babel.symbols.ASTNode;	
import gov.llnl.babel.symbols.AssertionExpression;
import gov.llnl.babel.symbols.CExprString;
import gov.llnl.babel.symbols.IdentifierLiteral;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * The <code>Type</code> class defines a SIDL type, such as a primitive
 * type (boolean, char, dcomplex, double, fcomplex, float, int, long, opaque,
 * and string), arrays, and user-defined types (enum, interface, or class).
 * A <code>Type</code> object may also be created for a void return type
 * from a method.
 */
public class Type extends ASTNode {
   public final static int VOID      =  0;
   public final static int BOOLEAN   =  1;
   public final static int CHAR      =  2;
   public final static int DCOMPLEX  =  3;
   public final static int DOUBLE    =  4;
   public final static int FCOMPLEX  =  5;
   public final static int FLOAT     =  6;
   public final static int INT       =  7;
   public final static int LONG      =  8;
   public final static int OPAQUE    =  9;
   public final static int STRING    = 10;
   public final static int ENUM      = Symbol.ENUM;
   public final static int STRUCT    = Symbol.STRUCT;
   public final static int CLASS     = Symbol.CLASS;
   public final static int INTERFACE = Symbol.INTERFACE;
   public final static int PACKAGE   = Symbol.PACKAGE;
   public final static int SYMBOL    = 16;
   public final static int ARRAY     = 17;
   
   public final static int MIN_TYPE_IND = 0;
   public final static int MAX_TYPE_IND = INTERFACE;

   public final static int MIN_RARRAY_TYPE_IND = DCOMPLEX;
   public final static int MAX_RARRAY_TYPE_IND = LONG;
  
   public final static String s_names[] = {
      "void", "bool", "char", "dcomplex", "double", "fcomplex",
      "float", "int",  "long", "opaque",   "string", "enum",
      "struct", "interface", "class"
   };

   public final static int UNSPECIFIED  = 0;
   public final static int COLUMN_MAJOR = 1;
   public final static int ROW_MAJOR    = 2;

   private final static String s_order[] = {
      "unspecified", "column-major", "row-major"
   };

   private int      d_type;        // integer description of type (from above)
   private SymbolID d_symbol_id;   // symbol identifier if SYMBOL
   private Type     d_array_type;  // type of array if ARRAY
   private int      d_array_dim;   // dimension of array if ARRAY
   private int      d_array_order; // order of array if ARRAY
   private List d_rarray_indices; // The list of index expressions
                                // Each element is a AssertionExpression
  private Context d_context = null; // context

   /**
    * Create a new primitive type (boolean, char, dcomplex, double, fcomplex,
    * float, int, long, opaque, and string).  Use the other constructors to
    * create a symbol or array type.
    *
    * @param  type  The value of this primitive type object.
    */
   public Type(int type) {
      d_type        = type;
      d_symbol_id   = null;
      d_array_type  = null;
      d_array_dim   = 0;
      d_array_order = UNSPECIFIED;
      d_rarray_indices = null;	
   }

   /**
    * Create a new type given a user-defined symbol identifier.
    * This symbol identifier will represent an enum, interface,
    * or class.
    *
    * @param  id  The identifier of with this symbol object.
    */
   public Type(SymbolID id, Context context) {
      d_type        = SYMBOL;
      d_symbol_id   = id;
      d_array_type  = null;
      d_array_dim   = 0;
      d_array_order = UNSPECIFIED;
      d_rarray_indices = null;
      d_context = context;
   }
 
   /**
    * Create a new array.
    *
    * @param  type   The type of this array object.
    * @param  dim    The dimension of this array.
    * @param  order  The desired storage order of this array.
    */
  public Type(Type type, int dim, int order, Context context) {
    d_type        = ARRAY;
    d_symbol_id   = null;
    d_array_type  = type;
    d_array_dim   = dim;
    d_array_order = order;
    d_rarray_indices = null;	
    d_context        = context;
  }

   /**
    * Create a new array provided the array type, dimension, and order.
    *
    * @param indices this can be a ArrayList of String's or
    * AssertionExpression's. Any String elements are converted
    * to AssertionExpression's.
    */
  public Type(Type type, int dim, Vector indices, Context context) {
    d_type        = ARRAY;
    d_symbol_id   = null;
    d_array_type  = type;
    d_array_dim   = dim;
    d_array_order = COLUMN_MAJOR;
    d_context     = context;
    if (indices != null) {
      final int indexSize = indices.size();
      int i;
      d_rarray_indices = new ArrayList(indexSize);
      for(i = 0 ; i < indexSize; ++i) {
        Object o = indices.get(i);
        if (o instanceof String) {
          try {
            d_rarray_indices.add(new IdentifierLiteral((String)o, context));
          }
          catch (AssertionException ae) {
            // TODO: this should never happen
          }
        }
        else {
          d_rarray_indices.add((AssertionExpression)o);
        }
      }
    }
    else {
      d_rarray_indices = null;
    }
  }
  
  /**
   * Explicit constructor.
   */
  public Type(SymbolID id, int type, Type arr_type, 
              int dim, int order, Context context) {
    d_type        = type;
    d_symbol_id   = id;
    d_array_type  = arr_type;
    d_array_dim   = dim;
    d_array_order = order;
    d_rarray_indices = null;
    d_context = context;
  }

   /**
    * Return the integer that identifies the type of this type.
    * If the type is ENUM, STRUCT, INTERFACE, or CLASS, it is returned
    * as the generic SYMBOL (for historical purposes).
    */
   public int getType() {
     int type = getDetailedType();
     if (type == ENUM || type == STRUCT || type == INTERFACE ||
         type == CLASS || type == PACKAGE) { 
       type = SYMBOL;
     }
     return type;
   }

   /*
    * Return the integer that identifies the basic type of this instance,
    * regardless of whether it is generically a SYMBOL.
    */
  public Symbol getSymbol() {
    Symbol s = d_context.getSymbolTable().lookupSymbol(d_symbol_id);
    return s;
  }

   /*
    * Return the integer that identifies the basic type of this instance,
    * regardless of whether it is generically a SYMBOL.
    */
  public int getBasicType() {
    return d_type;
  }

  /**
   * Return the name of the type.  
   *
   * Assumption:
   * 1) The type attribute is valid and in range.
   */
  public String getTypeName() {
    String name = null;
    int    type = getDetailedType();
    if ( (MIN_TYPE_IND <= type) && (type <= MAX_TYPE_IND) ) {
      name = s_names[type];
    } else if (type == ARRAY) {
      name = "array";
    }
    return name;
  }

  /**
   * Return the name of the type given the specified type value or, if
   * not recognized, simply a null string.
   */
  public static String getTypeName(int type) {
    String name = null;
    if ( (MIN_TYPE_IND <= type) && (type <= MAX_TYPE_IND) )  {
      name = s_names[type];
    } else if (type == ARRAY) {
      name = "array";
    }
    return name;
  }

  /**
   * Return the integer that identifies the type of this type.
   * If type == SYMBOL, this method will try to further refine
   * the type to one of (CLASS, INTERFACE, ENUM, STRUCT).  This may, 
   * in fact, fail if the type is undefined (via a forward reference)
   * and then SYMBOL is returned.
   */ 
  public int getDetailedType() { 
    if ( d_type == SYMBOL ) { 
      // try to further refine the type information
      try { 
        Symbol s = d_context.getSymbolTable().lookupSymbol(d_symbol_id);
        if (s != null) {
          int symbolType = s.getSymbolType();
          switch (symbolType) { 
          case Symbol.CLASS:
          case Symbol.ENUM:
          case Symbol.STRUCT:
          case Symbol.INTERFACE:
          case Symbol.PACKAGE:
            d_type = symbolType;
            break;
          }
        }
      } catch ( Exception e ) { 
        // if all else fails, it remains a symbol
      }          
    }
    return d_type;
  }

  /**
   * Return TRUE if the type is one of the standard primitive types,
   * including strings; otherwise, return FALSE.
   */
  public boolean isPrimitive() {
     return d_type <= STRING;
  }

  /**
   * Return TRUE if the type is a string; otherwise, return FALSE.
   */
  public boolean isString() {
     return d_type == STRING;
  }

  /**
   * Return TRUE if the type is a symbol; otherwise, return FALSE.
   */
  public boolean isSymbol() {
     return getType() == SYMBOL;
  }

   /**
    * Return TRUE if the type is an array; otherwise, return FALSE.
    */
  public boolean isArray() {
     return d_type == ARRAY;
  }

   /**
    * Return TRUE if the type is an struct; otherwise, return FALSE.
    */
  public boolean isStruct() {
      // Since d_type might be SYMBOL, we call getDetailedType() to
      // resolve the actual type first
      return getDetailedType() == STRUCT;
  }

   /**
    * If this type is an array, then return the dimension. A zero array
    * dimension indicates an array with no type, no dimension and no
    * ordering specified.
    */
   public int getArrayDimension() {
      return d_array_dim;
   }

   /**
    * Return the storage order, if this is an array; otherwise, return zero.
    */
   public int getArrayOrder() {
      return d_array_order;
   }

  /**
   * Return <code>true</code> iff this type is a generic array type.
   */
  public boolean isGenericArray() {
    return (ARRAY == d_type) && (null == d_array_type);
  }

   /**
    * If this is an array, return the array type. A <code>null</code>
    * array type means that this array is a generic array reference
    * with no dimension, no type and not ordering specified.
    */
   public Type getArrayType() {
      return d_array_type;
   }

   /**
    * Return TRUE if the type is a numeric array; otherwise, return FALSE.
    */
   public boolean isNumericArray() {
      int type = (d_array_type != null) ? d_array_type.getBasicType() : -1;
      return (  (d_type == ARRAY)
             && (  (type == INT     ) || (type == LONG    )
                || (type == FLOAT   ) || (type == DOUBLE  )
                || (type == FCOMPLEX) || (type == DCOMPLEX) ) );
   }

   /**
    * Returns the array type name or the null string if not applicable.
    */
   public String getArrayTypeName() {
      String name;
      if (d_type == ARRAY) {
        int at = d_array_type.getBasicType();
        if ( ((BOOLEAN <= at) && (at <= OPAQUE)) || (at == ENUM) ) {
          name = s_names[at];
        } else {
          name = "";
        }
      } else {
        name = "";
      }
      return name;
   }

  /**
   * This adds an index variable on the end of the index vector.
   * It converts it into an AssertionExpression.
   */
   public void addArrayIndex(String s) {
     checkFrozen();
     if(d_rarray_indices == null)
       d_rarray_indices = new ArrayList();
     try {
       d_rarray_indices.add(new IdentifierLiteral(s, d_context));
     }
     catch (AssertionException ae) {
     }
   }	

  /**
   * This adds an index variable on the end of the index vector.
   */
   public void addArrayIndex(AssertionExpression s) {
     checkFrozen();
     if(d_rarray_indices == null)
       d_rarray_indices = new ArrayList();
     d_rarray_indices.add(s);
   }	

   /**
    * This returns the set of indices needed for this rarray.  (A set of Strings
    * corresponding to argument names of type int or long). If this Type is 
    * not an rarray this function returns null.
    *
    * @return this returned list is a list of String objects.
    */
   public Collection getArrayIndices() {

     if (d_rarray_indices == null) return null;
     HashSet result = new HashSet();
     Iterator i = d_rarray_indices.iterator();
     while (i.hasNext()) {
       ((AssertionExpression)i.next()).accept(new RarrayIndices(), result);
     }
     return result;
   }	

  /**
   * Return the list of rarray expressions.
   */
   public List getArrayIndexExprs() {
     return (d_rarray_indices != null) ? 
       Collections.unmodifiableList(d_rarray_indices) : null;
   }	

   /**
    * Returns true if this Type is an rarray, false otherwise.
    */
   public boolean isRarray() {
      return (d_rarray_indices != null);
   }	


  /**
   * Return <code>true</code> if and only if the type is an array with an
   * ordering specification.  For example, array&lt;int,2,column-major&gt;
   * would return <code>true</code>; array&lt;int, 2&gt; would return
   * <code>false</code>.  For non-array types, this always returns
   * <code>false</code>. 
   */
  public boolean hasArrayOrderSpec() {
    return (d_type == ARRAY) && (d_array_order != UNSPECIFIED);
  }

  public int hashCode()
  {
    switch(d_type){
    case CLASS:
    case INTERFACE:
    case PACKAGE:
    case SYMBOL:
      return d_symbol_id.hashCode();
    case ARRAY:
      return d_array_dim* 
        ( (d_array_type != null) ?  d_array_type.hashCode() : 1) +
        d_array_order;
    default:
      return d_type;
    }
  }

   /**
    * Return the symbol identifier if this is a symbol type; otherwise,
    * return null.
    */
   public SymbolID getSymbolID() {
      return d_symbol_id;
   }

   /**
    * Return a string representation of the type for printing out the
    * types in a method signature.
    */
   public String getTypeString() {
      StringBuffer s    = new StringBuffer();
      int          type = getType();
      if ( type == SYMBOL ) {
         s.append(d_symbol_id.getFullName());
      } else if ( type == ARRAY ) {
        if (d_array_type == null) {
          s.append("array<>");
        }
        else{
          if(isRarray())
            s.append("rarray<" + d_array_type.getTypeString());
          else
            s.append("array<" + d_array_type.getTypeString());
          if (d_array_dim > 1) {
            s.append("," + d_array_dim);
          }
          if ( (UNSPECIFIED < d_array_order) && (d_array_order <= ROW_MAJOR) 
               && !isRarray()) {  //Rarrays don't specify array order.
            s.append("," + s_order[d_array_order]);
          } 
          s.append(">");
        }
      } else if ((type >= VOID) && (type <= STRING)) {
         s.append(s_names[d_type]);
      }
      return s.toString();
   }

   /**
    * Return a string representation of the list of index variables.
    * This string is the names of the index variables (in order) seperated
    * by commas.
    */
   public String getIndexString() {
     StringBuffer index_list = new StringBuffer();
     if(d_rarray_indices == null || d_rarray_indices.size() < 1)
       System.err.println("ERROR: rarray has 0 index variables");
     for(Iterator i = d_rarray_indices.iterator(); i.hasNext();) {
       AssertionExpression ae = (AssertionExpression)i.next();
       index_list.append(ae.accept(new CExprString(), null).toString());
       if(i.hasNext())
         index_list.append(",");
     }
     return index_list.toString();
   }

   /**
    * Return TRUE if the specified object is considered to be the same as this
    * object; otherwise, return FALSE.
    */
   public boolean equals(Object object) {
      boolean eq = false;
      if ((object != null) && (object instanceof Type)) {
         Type t = (Type) object;
         final int my_type = getType();
         final int your_type = t.getType();
         if (my_type == your_type) {
           if ((my_type == SYMBOL) ||
               (my_type == CLASS) ||
               (my_type == ENUM) ||
               (my_type == STRUCT ) ||
               (my_type == PACKAGE)) {
             eq = d_symbol_id.equals(t.d_symbol_id);
           } else if (my_type == ARRAY) {
             if((d_array_type == null) || (t.d_array_type == null)) {
               if((d_array_type == null) && (t.d_array_type == null)) {
                 eq = true;
               } else {
                 eq = false;
               }
             } else {
               eq = ( d_array_type.equals(t.d_array_type)
                      && (d_array_dim   == t.d_array_dim  )
                      && (d_array_order == t.d_array_order)  );
             }
           } else {
             eq = true;
           }
         }
      }
      return eq;
   }

  /**
   * Used to detect overloading collisions.
   */
  public boolean similar(Type t1)
  {
    if (equals(t1)) return true;
    if ((getType() == ARRAY) && (t1.getType() == ARRAY)) {
      if (d_array_type != null){
        /* do not require dimension & order match */
        return d_array_type.equals(t1.d_array_type);
      }
    }
    return false;
  }

  public void freeze()
  {
    if (!d_frozen) {
      super.freeze();
      if (d_symbol_id != null) d_symbol_id.freeze();
      if (d_array_type != null) d_array_type.freeze();
      d_rarray_indices = protectList(d_rarray_indices);
    }
  }
}
