//
// File:        Utilities.java
// Package:     gov.llnl.babel.backend
// Revision:    @(#) $Id: Utilities.java 7188 2011-09-27 18:38:42Z adrian $
// Description: a collection of common utility functions (e.g., sorting)
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

package gov.llnl.babel.backend;

import gov.llnl.babel.BabelConfiguration;
import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.SortComparator;
import gov.llnl.babel.symbols.Argument;
import gov.llnl.babel.symbols.Class;
import gov.llnl.babel.symbols.Enumeration;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Interface;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.SymbolRedefinitionException;
import gov.llnl.babel.symbols.SymbolTable;
import gov.llnl.babel.symbols.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * The <code>Utilities</code> class contains a variety of common
 * utility functions, such as sorting a collection of data or
 * determining the maximum string width of a collection of data.
 */
public class Utilities {
  /**
   * The argument name that holds the object/interface pointer in
   * a call to an object method. It's conceptually like the this pointer in
   * C++. 
   */
  public static final String s_self = "self";

  /**
   * The argument name that holds the exception pointer which a method may
   * throw an exception.
   */
  public static final String s_exception = "_ex";

  /**
   * A local cache of the name of the base exception types.
   */
  private static final String s_exceptionFundamentalType = 
    BabelConfiguration.getBaseExceptionType();

   /**
    * Sort a <code>Collection</code> of objects into ascending order and
    * return the sorted list in an <code>ArrayList</code> object.
    */
   public static ArrayList sort(Collection collection) {
      Object[] objs = collection.toArray();
      Arrays.sort(objs, new SortComparator());

      ArrayList sorted = new ArrayList(objs.length);
      for (int i = 0; i < objs.length; i++) {
         sorted.add(objs[i]);
      }

      return sorted;
   }

   /**
    * Determine the maximum width of a set of objects.  The meaning of
    * "width" depends on the type of object in the collection.  Supported
    * types are strings, map entries with a key string, methods, symbols,
    * and symbol identifiers.
    */
   public static int getWidth(Collection collection) {
      int width = 0;

      for (Iterator i = collection.iterator(); i.hasNext(); ) {
         Object obj = i.next();
         int w = 0;

         if (obj instanceof String) {
            w = ((String) obj).length();
         } else if (obj instanceof Method) {
        	 if (((Method) obj).getCommunicationModifier() == Method.NONBLOCKING ) { 
        		 w = ((Method) obj).getLongMethodName().length() + 5;
        	 } else { 
             w = ((Method) obj).getLongMethodName().length();
        	 }
         } else if (obj instanceof SymbolID) {
            w = ((SymbolID) obj).getFullName().length();
         } else if (obj instanceof Symbol) {
            w = ((Symbol) obj).getFullName().length();
         } else if (obj instanceof Map.Entry) {
            Map.Entry m = (Map.Entry) obj;
            Object key = m.getKey();
            if (key instanceof String) {
               w = ((String) key).length();
            }
         }

         if (w > width) {
            width = w;
         }
      }

      return width;
   }

   /**
    * Extract the unique interfaces from this class.  The unique interfaces
    * are those that belong to this class but do not belong to one of its
    * parents (if they exit).  The returned set consists of objects of the
    * type <code>Interface</code>.
    */
   public static Set getUniqueInterfaces(Class cls) {
      Set unique = new HashSet(cls.getParentInterfaces(true));

      Class parent = cls.getParentClass();
      if (parent != null) {
        unique.removeAll(parent.getParentInterfaces(true));
      }

      return unique;
   }

   /**
    * Extract the unique interfaces from this class and return them as a
    * set of <code>SymbolID</code> objects.
    */
   public static Set getUniqueInterfaceIDs(Class cls) {
      Set unique = getUniqueInterfaces(cls);

      Set ids = new HashSet();
      for (Iterator p = unique.iterator(); p.hasNext(); ) {
         ids.add(((Interface) p.next()));
      }

      return ids;
   }

   /**
    * Return all parents of this class, including both class parents and
    * interface parents.  The return <code>Set</code> contains the symbol
    * identifiers of all parents.
    */
   public static Set getAllParents(Class cls) {
      Set parents = new HashSet();

      Collection interfaces = cls.getParentInterfaces(true);
      for (Iterator p = interfaces.iterator(); p.hasNext(); ) {
         parents.add(((Interface) p.next()));
      }

      Class parent = cls.getParentClass();
      while (parent != null) {
         parents.add(parent);
         parent = parent.getParentClass();
      }

      return parents;
   }

   /**
    * Lookup a symbol entry in the singleton symbol table.  If the symbol
    * does not exist or there is a conflict with an existing symbol, then
    * throw a code generation exception.
    */
  public static Symbol lookupSymbol(Context context,
                                    SymbolID id)
         throws CodeGenerationException {
      Symbol symbol = null;
      try {
         symbol = context.getSymbolTable().resolveSymbol(id);
      } catch (SymbolRedefinitionException ex) {
         throw new CodeGenerationException(ex.getMessage());
      }
      if (symbol == null) {
         throw new CodeGenerationException("Symbol \""
            + id.getSymbolName()
            + "\" not found in symbol repository");
      }
      return symbol;
   }

   /**
    * Lookup a symbol entry in the singleton symbol table by fully
    * qualified name.  If the symbol does not exist, then throw a
    * code generation exception.
    */
   public static Symbol lookupSymbol(Context context,
                                     String fqn)
         throws CodeGenerationException {
      Symbol symbol = context.getSymbolTable().resolveSymbol(fqn);
      if (symbol == null) {
         throw new CodeGenerationException("Symbol \""
            + fqn
            + "\" not found in symbol repository");
      }
      return symbol;
   }

  public static String getEnumInitialization(Context context,
                                             SymbolID enumID)
    throws CodeGenerationException
  {
    Enumeration enumSym = (Enumeration)Utilities.lookupSymbol(context, enumID);
    Iterator i = enumSym.getIterator();
    if (i.hasNext()) {
      String enumValue = (String)i.next();
      return " = " + IOR.getEnumValueSymbol(enumSym,enumValue);
    }
    return "";
  }

  public static String getTypeInitialization(Type type,
                                             Context context)
    throws CodeGenerationException
  {
    switch(type.getDetailedType()) {
    case Type.DCOMPLEX:
    case Type.FCOMPLEX:
      return " = { 0, 0 }";
    case Type.ENUM:
      return Utilities.getEnumInitialization(context, type.getSymbolID());
    case Type.BOOLEAN:
      return " = FALSE";
    case Type.STRUCT:
      return " = {0}";
    default:
      return " = 0";
    }
  }

  /**
   * Convert a collection of <code>SymbolID</code> objects into a collection
   * of <code>Symbol</code> objects using the symbol table.
   *
   * @param  symbolIDs a collection of <code>SymbolID</code> objects.
   * @return a collection of <code>Symbol</code> objects. There is a
   *         one-to-one and onto correspondence between elements in the
   *         return value and elements in <code>symbolIDs</code>.
   * @see gov.llnl.babel.symbols.Symbol
   * @see gov.llnl.babel.symbols.SymbolID
   */
  public static Collection convertIdsToSymbols(Context context,
                                               Collection symbolIDs) {
    /* convert a set of SymbolID's instead a list of Symbols */
    SymbolTable table = context.getSymbolTable();
    ArrayList symbols = new ArrayList(symbolIDs.size());
    Iterator i = symbolIDs.iterator();
    while (i.hasNext()) {
      SymbolID id = (SymbolID)i.next();
      symbols.add(table.lookupSymbol(id));
    }
    Collections.sort(symbols);
    return symbols;
  }

  /**
   * This method allows substring replacement
   * Unfortunately the java.lang.String class has a 
   * replace function for characters but not strings.
   * @param source The string to do the replacement on
   * @param from The substring to match and replace
   * @param to The new string to insert in place of the matched substring
   * @return the new string with all instances of "from" replaced by "to"
   */
  public static String replace( String source, String from , String to ) { 
    StringTokenizer tokens = new StringTokenizer( source, from );
    StringBuffer buf = new StringBuffer();
    int length = tokens.countTokens();
    buf.append( tokens.nextToken() );
    for ( int i=1; i<length; i++ ) { 
      buf.append( to );
      buf.append( tokens.nextToken() );
    }
    return buf.toString();
  }

  /**
   * Return <code>true</code> if and only if this type is implemented in 
   * C by a type that is a pointer.
   *
   * @param t   the type to be evaluated.
   * @return    <code>true</code> is returned if and only if this type
   *            is implemented by a type that is a pointer.
   */
  public static boolean isPointer(Type t)
  {
    switch(t.getDetailedType()) {
    case Type.ARRAY:
    case Type.STRING:
    case Type.OPAQUE:
    case Type.CLASS:
    case Type.INTERFACE:
      return true;
    default:
      return false;
    }
  }

  /**
   * Add extra arguments to the original argument list of a method as needed
   * for the self pointer and the exception argument.  This makes these
   * implicit arguments explicit and prevents having each of these
   * be a special case throughout the code.  The additional arguments
   * are appropriate for the IOR and C binding.
   *
   * @param selfId   the name of the class/interface who owns the method.
   * @param m        the method whose argument list will be extended.
   * @param indices True if the argument list should include rarray
   *                 indices. True from C binding.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *  a catch all exception for problems in the code generation phase.
   */
  public static List extendArgs(SymbolID selfId, 
                                Method m,
                                boolean indices,
                                Context context) 
    throws CodeGenerationException
  {
    
    List origArgs = null;
    if(indices)
      origArgs = m.getArgumentListWithIndices();
    else
      origArgs = m.getArgumentListWithOutIndices();

    int     newSize   = origArgs.size();
    boolean addSelf   = (Method.STATIC != m.getDefinitionModifier());
    if (addSelf)   newSize++;
    boolean addExcept = !m.getThrows().isEmpty();
    if (addExcept) newSize++;

    ArrayList result = new ArrayList(newSize);
    if (addSelf) {
      result.add(new Argument(Argument.IN, new Type(selfId, context), s_self));
    }
    result.addAll(origArgs);
    if (addExcept) {
      Symbol ex = Utilities.lookupSymbol(context, s_exceptionFundamentalType);
      result.add(new Argument(Argument.OUT, 
                              new Type(ex, context), s_exception));
    }
    return result;
  }

  /**
   * Return <code>true</code> if and only if the extendable is
   * a class that is the base exception class, is an interface that is
   * the base exception interface, or it has the base exception class or 
   * interface in its type ancestry.
   */
  public static boolean isException(Symbol sym,
                                    Context context) 
  {
    if (sym instanceof Extendable) {
      Extendable ext = (Extendable)sym;
      try {
        final Symbol exceptionClass = 
          Utilities.lookupSymbol(context,
                                 BabelConfiguration.getBaseExceptionClass());
        final Symbol exceptionInterface = 
          Utilities.lookupSymbol(context,
                                BabelConfiguration.getBaseExceptionInterface());
        SymbolID exceptionCID = (SymbolID) exceptionClass;
        SymbolID exceptionIID = (SymbolID) exceptionInterface;
        return ext.equals(exceptionCID) || ext.equals(exceptionIID)
            || ext.hasParentInterface(exceptionCID, true)
            || ext.hasParentInterface(exceptionIID, true);
      }
      catch (CodeGenerationException cge) {
      }
    }
    return false;
  }

  public static String capitalize(String str)
  {
    if ((str.length() == 0) || Character.isUpperCase(str.charAt(0)))
      return str;
    char [] content = str.toCharArray();
    content[0] = Character.toUpperCase(content[0]);
    return new String(content);
  }
}
