//
// File:        Argument.java
// Package:     gov.llnl.babel.symbols
// Revision:    @(#) $Id: Argument.java 7421 2011-12-16 01:06:06Z adrian $
// Description: this class represents a sidl method argument
//
// Copyright (c) 2000-2001, Lawrence Livermore National Security, LLC
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

import gov.llnl.babel.symbols.SymbolUtilities;
import gov.llnl.babel.symbols.ASTNode;
import gov.llnl.babel.symbols.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The <code>Argument</code> class defines a SIDL argument, which consists
 * of a parameter passing mode (IN, INOUT, or OUT with an optional COPY),
 * a type, and a formal parameter name.
 */
public class Argument extends ASTNode implements Attributes {
  private Map d_attributes = new HashMap();
  public static final int IN    = 0; 
  public static final int INOUT = 1; 
  public static final int OUT   = 2; 
  
  private static final String[] s_mode_string = { "in", "inout", "out" };

  private String  d_formal_name;
  private int     d_mode;
  private Type    d_type;
  private Comment   d_comment;

   /**
    * Create an argument object.  
    *
    * @param  copy  The copy flag.
    * @param  mode  The parameter passing mode (IN, OUT, or INOUT).
    * @param  type  The SIDL type of the argument.
    * @param  name  The formal parameter name.
    */
   public Argument(int mode, Type type, String name) {
      d_formal_name = name;
      d_mode        = mode;
      d_type        = type;
      d_comment                = null;
   }

   /**
    * Return TRUE if the copy flag has been set for this method argument; 
    * otherwise, return FALSE.
    */
   public boolean isCopy() {
     return hasAttribute("copy");
   }

   /**
    * Return the value of the argument parameter passing mode.
    */
   public int getMode() {
      return d_mode;
   }

  public static String[] getAllowableModes() {
    return s_mode_string;
  }

   /**
    * Return a string representing the argument mode.
    */
   public final String getModeString() {
     return s_mode_string[d_mode];
   }

   /**
    * Return the formal name of the parameter identifier.
    */
   public String getFormalName() {
      return d_formal_name;
   }

   /**
    * Return the type of the argument.
    */
   public Type getType() {
      return(d_type);
   }

  /**
   * Return TRUE if the type of this argument is an array with an with an 
   * ordering specification. For example, <code>in array&lt;int, 2, 
   * column-major&gt; x</code> would return TRUE.  <code>out array&lt;int, 
   * 2&gt; x</code> would return FALSE because it does not have an ordering
   * specification.  Non-array arguments return FALSE.
   */
  public boolean hasArrayOrderSpec() {
    return d_type.hasArrayOrderSpec();
  }

  private String customAttrStr()
  {
    StringBuffer result = new StringBuffer();
    if (getAttributes().size() > 0) {
      HashSet attrs = new HashSet(getAttributes());
      attrs.remove("copy");
      if (attrs.size() > 0) {
        Object[] sorted = attrs.toArray();
        Arrays.sort(sorted);
        result.append("%attrib{");
        for(int i = 0; i < sorted.length; ++i) {
          final String key = sorted[i].toString();
          final String value = getAttribute(key);
          result.append(' ').append(key);
          if (value != null) {
            result.append(" = \"").append(value).append('"');
          }
          if (i+1 < sorted.length) {
            result.append(',');
          }
        }
        result.append(" }");
      }
    }
    return result.toString();
  }

   /**
    * Return a string representation of the argument for outputting the
    * arguments in a method signature, for example.  Optionally abbreviate 
    * the type if it is declared in the specified package.
    */
   public String getArgumentString(String abbrev_pkg) {
      StringBuffer arg = new StringBuffer();
      
      if (isCopy()) {
         arg.append("copy ");
      }
      arg.append(customAttrStr()).append(' ');
      arg.append(getModeString() + " ");

      String rtype = d_type.getTypeString();
      if (abbrev_pkg != null) {
        arg.append(SymbolUtilities.getSymbolName(rtype, abbrev_pkg));
      } else {
        arg.append(rtype);
      }
      arg.append(" ");
      arg.append(d_formal_name);
      
      if(d_type.isRarray()) {
        arg.append("(" + d_type.getIndexString() + ")");
      }

      return arg.toString();
   }

   /**
    * Return a string representation of the argument for outputting the
    * arguments in a method signature, for example.
    */
   public String getArgumentString() {
      return getArgumentString(null);
   }

  public int hashCode() {
    return d_mode*7 + (isCopy() ? 1 : 0) + d_type.hashCode();
  }

   /**
    * Return TRUE if the specified object is considered the same as this
    * object; otherwise, return FALSE.  Note that the formal name does 
    * not need to match for equality.
    *
    * @param  object  The object being used for comparison.
    */
   public boolean equals(Object object) {
      boolean eq = false;
      if ((object != null) && (object instanceof Argument)) {
         Argument arg = (Argument) object;
         eq = ((isCopy() == arg.isCopy())
            && (d_mode == arg.d_mode)
            && (d_type.equals(arg.d_type)));
      }
      return eq;
   }

  private boolean similarModes(int m1, int m2)
  {
    return (m1 == m2) || ((m1 != Argument.IN) && (m2 != Argument.IN));
  }

  /**
   * This method returns true if two arguments map are similar enough
   * to cause a problem for overloaded functions.
   */
  public boolean similar(Argument arg) {
    return similarModes(d_mode, arg.d_mode) &&
      d_type.similar(arg.d_type);
  }

  /**
   * Set the comment for the method.  
   *
   * @param  comment  The comment associated with the method.  May be null.
   */
  public void setComment(Comment comment) {
    checkFrozen();
    d_comment = comment;
  }
  
  /**
   * Return the comment for the method.  This may be null if there is no
   * comment.
   */
  public Comment getComment() {
    return d_comment;
  }

  public boolean hasAttribute(String key) 
  {
    return d_attributes.containsKey(key);
  }
  
  public String getAttribute(String key) throws UnknownAttributeException
  {
    if (hasAttribute(key)){
      return (String)d_attributes.get(key);
    }
    throw new UnknownAttributeException("Argument " + d_formal_name + 
                                        " has no attribute named " + key);
  }

  public void setAttribute(String key)
  {
    setAttribute(key, null);
  }

  public void setAttribute(String key, String value)
  {
    d_attributes.put(key, value);
  }

  public void removeAttribute(String key)
    throws UnknownAttributeException
  {
    checkFrozen();
    if (hasAttribute(key)) {
      d_attributes.remove(key);
    }
    else {
      throw new UnknownAttributeException("Argument " + d_formal_name + 
                                          " has no attribute named " + key);
    }
  }

  public Set getAttributes()
  {
    return d_attributes.keySet();
  }

  public void freeze() {
    if (!d_frozen) {
      super.freeze();
      d_type.freeze();
      d_attributes = protectMap(d_attributes);
      if (d_comment != null) d_comment.freeze();
    }
  }
}
