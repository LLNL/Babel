//
// File:        StringXML.java
// Package:     gov.llnl.babel.parsers.xml
// Revision:    @(#) $Id: StringXML.java 7188 2011-09-27 18:38:42Z adrian $
// Description: simple utility class to convert between enums and XML strings
//
// Copyright (c) 2000-2004, Lawrence Livermore National Security, LLC
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

package gov.llnl.babel.parsers.xml;

import gov.llnl.babel.parsers.xml.ParseSymbolException;
import gov.llnl.babel.symbols.Argument;
import gov.llnl.babel.symbols.Assertion;
import gov.llnl.babel.symbols.BinaryExpression;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.Type;
import gov.llnl.babel.symbols.UnaryExpression;
import java.util.HashMap;

/**
 * Class <code>StringXML</code> is a simple utility class that converts
 * between symbol types (integers or enumerated identifiers) and symbol
 * strings.  Invalid integer identifiers or string types will result in
 * exceptions or null return values.
 */
public class StringXML {
  private static final HashMap s_symbol_int2xml = new HashMap();
  private static final HashMap s_symbol_xml2int = new HashMap();

  private static final HashMap s_type_int2xml = new HashMap();
  private static final HashMap s_type_xml2int = new HashMap();

  private static final HashMap s_mode_int2xml = new HashMap();
  private static final HashMap s_mode_xml2int = new HashMap();

  private static final HashMap s_def_int2xml = new HashMap();
  private static final HashMap s_def_xml2int = new HashMap();

  private static final HashMap s_com_int2xml = new HashMap();
  private static final HashMap s_com_xml2int = new HashMap();

  private static final HashMap s_order_int2xml = new HashMap();
  private static final HashMap s_order_xml2int = new HashMap();

  private static final HashMap s_assert_int2xml = new HashMap();
  private static final HashMap s_assert_xml2int = new HashMap();

  private static final HashMap s_unary_int2xml = new HashMap();
  private static final HashMap s_unary_xml2int = new HashMap();

  private static final HashMap s_binary_int2xml = new HashMap();
  private static final HashMap s_binary_xml2int = new HashMap();

  static {
    s_symbol_int2xml.put(new Integer(Symbol.CLASS),     "class");
    s_symbol_int2xml.put(new Integer(Symbol.ENUM),      "enum");
    s_symbol_int2xml.put(new Integer(Symbol.INTERFACE), "interface");
    s_symbol_int2xml.put(new Integer(Symbol.PACKAGE),   "package");
    s_symbol_int2xml.put(new Integer(Symbol.STRUCT),   "struct");

    s_symbol_xml2int.put("class",     new Integer(Symbol.CLASS));
    s_symbol_xml2int.put("enum",      new Integer(Symbol.ENUM));
    s_symbol_xml2int.put("interface", new Integer(Symbol.INTERFACE));
    s_symbol_xml2int.put("package",   new Integer(Symbol.PACKAGE));
    s_symbol_xml2int.put("struct",   new Integer(Symbol.STRUCT));

    s_type_int2xml.put(new Integer(Type.VOID),     "void");
    s_type_int2xml.put(new Integer(Type.BOOLEAN),  "boolean");
    s_type_int2xml.put(new Integer(Type.CHAR),     "char");
    s_type_int2xml.put(new Integer(Type.DCOMPLEX), "dcomplex");
    s_type_int2xml.put(new Integer(Type.DOUBLE),   "double");
    s_type_int2xml.put(new Integer(Type.FCOMPLEX), "fcomplex");
    s_type_int2xml.put(new Integer(Type.FLOAT),    "float");
    s_type_int2xml.put(new Integer(Type.INT),      "integer");
    s_type_int2xml.put(new Integer(Type.LONG),     "long");
    s_type_int2xml.put(new Integer(Type.OPAQUE),   "opaque");
    s_type_int2xml.put(new Integer(Type.STRING),   "string");
    s_type_int2xml.put(new Integer(Type.SYMBOL),   "symbol");
    s_type_int2xml.put(new Integer(Type.ARRAY),    "array");

    s_type_xml2int.put("void",     new Integer(Type.VOID));
    s_type_xml2int.put("boolean",  new Integer(Type.BOOLEAN));
    s_type_xml2int.put("char",     new Integer(Type.CHAR));
    s_type_xml2int.put("dcomplex", new Integer(Type.DCOMPLEX));
    s_type_xml2int.put("double",   new Integer(Type.DOUBLE));
    s_type_xml2int.put("fcomplex", new Integer(Type.FCOMPLEX));
    s_type_xml2int.put("float",    new Integer(Type.FLOAT));
    s_type_xml2int.put("integer",  new Integer(Type.INT));
    s_type_xml2int.put("long",     new Integer(Type.LONG));
    s_type_xml2int.put("opaque",   new Integer(Type.OPAQUE));
    s_type_xml2int.put("string",   new Integer(Type.STRING));
    s_type_xml2int.put("symbol",   new Integer(Type.SYMBOL));
    s_type_xml2int.put("array",    new Integer(Type.ARRAY));

    s_mode_int2xml.put(new Integer(Argument.IN),    "in");
    s_mode_int2xml.put(new Integer(Argument.INOUT), "inout");
    s_mode_int2xml.put(new Integer(Argument.OUT),   "out");

    s_mode_xml2int.put("in",    new Integer(Argument.IN));
    s_mode_xml2int.put("inout", new Integer(Argument.INOUT));
    s_mode_xml2int.put("out",   new Integer(Argument.OUT));

    s_def_int2xml.put(new Integer(Method.NORMAL),   "normal");
    s_def_int2xml.put(new Integer(Method.ABSTRACT), "abstract");
    s_def_int2xml.put(new Integer(Method.FINAL),    "final");
    s_def_int2xml.put(new Integer(Method.STATIC),   "static");

    s_def_xml2int.put("normal",   new Integer(Method.NORMAL));
    s_def_xml2int.put("abstract", new Integer(Method.ABSTRACT));
    s_def_xml2int.put("final",    new Integer(Method.FINAL));
    s_def_xml2int.put("static",   new Integer(Method.STATIC));

    s_com_int2xml.put(new Integer(Method.NORMAL), "normal");
    s_com_int2xml.put(new Integer(Method.LOCAL),  "local");
    s_com_int2xml.put(new Integer(Method.ONEWAY), "oneway");
    s_com_int2xml.put(new Integer(Method.NONBLOCKING), "nonblocking");

    s_com_xml2int.put("normal", new Integer(Method.NORMAL));
    s_com_xml2int.put("local",  new Integer(Method.LOCAL));
    s_com_xml2int.put("oneway", new Integer(Method.ONEWAY));
    s_com_xml2int.put("nonblocking", new Integer(Method.NONBLOCKING));

    s_order_int2xml.put(new Integer(Type.UNSPECIFIED),  "unspecified");
    s_order_int2xml.put(new Integer(Type.COLUMN_MAJOR), "column-major");
    s_order_int2xml.put(new Integer(Type.ROW_MAJOR),    "row-major"); 

    s_order_xml2int.put("unspecified",  new Integer(Type.UNSPECIFIED));
    s_order_xml2int.put("column-major", new Integer(Type.COLUMN_MAJOR));
    s_order_xml2int.put("row-major",    new Integer(Type.ROW_MAJOR));

    s_assert_int2xml.put(new Integer(Assertion.INVARIANT),    "invariant");
    s_assert_int2xml.put(new Integer(Assertion.REQUIRE),      "require"); 
    s_assert_int2xml.put(new Integer(Assertion.REQUIRE_ELSE),"require_else");
    s_assert_int2xml.put(new Integer(Assertion.ENSURE),       "ensure"); 
    s_assert_int2xml.put(new Integer(Assertion.ENSURE_THEN),  "ensure_then"); 

    s_assert_xml2int.put("invariant",    new Integer(Assertion.INVARIANT));
    s_assert_xml2int.put("require",      new Integer(Assertion.REQUIRE));
    s_assert_xml2int.put("require_else",new Integer(Assertion.REQUIRE_ELSE));
    s_assert_xml2int.put("ensure",       new Integer(Assertion.ENSURE));
    s_assert_xml2int.put("ensure_then",  new Integer(Assertion.ENSURE_THEN));

    s_unary_int2xml.put(new Integer(UnaryExpression.COMPLEMENT), 
                                                               "complement");
    s_unary_int2xml.put(new Integer(UnaryExpression.IS),         "is");
    s_unary_int2xml.put(new Integer(UnaryExpression.MINUS),      "minus"); 
    s_unary_int2xml.put(new Integer(UnaryExpression.NOT),        "not"); 
    s_unary_int2xml.put(new Integer(UnaryExpression.PLUS),       "plus"); 

    s_unary_xml2int.put("complement",  
                                    new Integer(UnaryExpression.COMPLEMENT));
    s_unary_xml2int.put("is",          new Integer(UnaryExpression.IS));
    s_unary_xml2int.put("minus",       new Integer(UnaryExpression.MINUS));
    s_unary_xml2int.put("not",         new Integer(UnaryExpression.NOT));
    s_unary_xml2int.put("plus",        new Integer(UnaryExpression.PLUS));

    s_binary_int2xml.put(new Integer(BinaryExpression.LOGICAL_AND),        "and");
    s_binary_int2xml.put(new Integer(BinaryExpression.DIVIDE),     "divide");
    s_binary_int2xml.put(new Integer(BinaryExpression.EQUALS),     "equals"); 
    s_binary_int2xml.put(new Integer(BinaryExpression.GREATER_EQUAL),
                                                            "greater_equal");
    s_binary_int2xml.put(new Integer(BinaryExpression.GREATER_THAN),
                                                             "greater_than");
    s_binary_int2xml.put(new Integer(BinaryExpression.IF_AND_ONLY_IF),"iff");
    s_binary_int2xml.put(new Integer(BinaryExpression.IMPLIES),   "implies");
    s_binary_int2xml.put(new Integer(BinaryExpression.LESS_EQUAL), 
                                                               "less_equal");
    s_binary_int2xml.put(new Integer(BinaryExpression.LESS_THAN),  
                                                                "less_than");
    s_binary_int2xml.put(new Integer(BinaryExpression.MINUS),      "minus"); 
    s_binary_int2xml.put(new Integer(BinaryExpression.MODULUS),   "modulus");
    s_binary_int2xml.put(new Integer(BinaryExpression.MULTIPLY), "multiply");
    s_binary_int2xml.put(new Integer(BinaryExpression.NOT_EQUAL),  
                                                                "not_equal");
    s_binary_int2xml.put(new Integer(BinaryExpression.LOGICAL_OR),         "or"); 
    s_binary_int2xml.put(new Integer(BinaryExpression.PLUS),       "plus"); 
    s_binary_int2xml.put(new Integer(BinaryExpression.POWER),      "power"); 
    s_binary_int2xml.put(new Integer(BinaryExpression.REMAINDER),  
                                                                "remainder");
    s_binary_int2xml.put(new Integer(BinaryExpression.SHIFT_LEFT), 
                                                               "shift_left");
    s_binary_int2xml.put(new Integer(BinaryExpression.SHIFT_RIGHT),
                                                              "shift_right");
    s_binary_int2xml.put(new Integer(BinaryExpression.LOGICAL_XOR),        "xor"); 
    s_binary_int2xml.put(new Integer(BinaryExpression.BITWISE_XOR),        "bit-xor"); 
    s_binary_int2xml.put(new Integer(BinaryExpression.BITWISE_AND),        "bit-and"); 
    s_binary_int2xml.put(new Integer(BinaryExpression.BITWISE_OR),        "bit-or"); 

    s_binary_xml2int.put("and",       new Integer(BinaryExpression.LOGICAL_AND));
    s_binary_xml2int.put("divide",    new Integer(BinaryExpression.DIVIDE));
    s_binary_xml2int.put("equals",    new Integer(BinaryExpression.EQUALS));
    s_binary_xml2int.put("greater_equal", 
                                new Integer(BinaryExpression.GREATER_EQUAL));
    s_binary_xml2int.put("greater_than", 
                                 new Integer(BinaryExpression.GREATER_THAN));
    s_binary_xml2int.put("iff",   
                               new Integer(BinaryExpression.IF_AND_ONLY_IF));
    s_binary_xml2int.put("implies",   new Integer(BinaryExpression.IMPLIES));
    s_binary_xml2int.put("less_equal",
                                   new Integer(BinaryExpression.LESS_EQUAL));
    s_binary_xml2int.put("less_than", 
                                    new Integer(BinaryExpression.LESS_THAN));
    s_binary_xml2int.put("minus",     new Integer(BinaryExpression.MINUS));
    s_binary_xml2int.put("modulus",   new Integer(BinaryExpression.MODULUS));
    s_binary_xml2int.put("multiply", new Integer(BinaryExpression.MULTIPLY));
    s_binary_xml2int.put("not_equal", 
                                    new Integer(BinaryExpression.NOT_EQUAL));
    s_binary_xml2int.put("or",        new Integer(BinaryExpression.LOGICAL_OR));
    s_binary_xml2int.put("plus",      new Integer(BinaryExpression.PLUS));
    s_binary_xml2int.put("power",     new Integer(BinaryExpression.POWER));
    s_binary_xml2int.put("remainder", 
                                    new Integer(BinaryExpression.REMAINDER));
    s_binary_xml2int.put("shift_left",
                                   new Integer(BinaryExpression.SHIFT_LEFT));
    s_binary_xml2int.put("shift_right", 
                                  new Integer(BinaryExpression.SHIFT_RIGHT));
    s_binary_xml2int.put("xor", new Integer(BinaryExpression.LOGICAL_XOR));
    s_binary_xml2int.put("bit-xor", new Integer(BinaryExpression.BITWISE_XOR)); 
    s_binary_xml2int.put("bit-and", new Integer(BinaryExpression.BITWISE_AND)); 
    s_binary_xml2int.put("bit-or", new Integer(BinaryExpression.BITWISE_OR)); 
  }

  /**
   * Convert the <code>Symbol</code> type integer into an XML string.
   * A null string is returned if the integer type is invalid.
   */
  public static String toSymbolXML(int symbol) {
    return (String) s_symbol_int2xml.get(new Integer(symbol));
  }

  /**
   * Convert the <code>Symbol</code> XML type string into its integer type.
   * An exception is thrown if the string type is invalid.
   */
  public static int fromSymbolXML(String symbol) throws ParseSymbolException {
    return lookup(s_symbol_xml2int, symbol);
  }

  /**
   * Convert the <code>Type</code> type integer into an XML string.
   * A null string is returned if the integer type is invalid.
   */
  public static String toTypeXML(int type) {
    return (String) s_type_int2xml.get(new Integer(type));
  }

  /**
   * Convert the <code>Type</code> XML type string into its integer type.
   * An exception is thrown if the string type is invalid.
   */
  public static int fromTypeXML(String type) throws ParseSymbolException {
    return lookup(s_type_xml2int, type);
  }

  /**
   * Convert the argument mode integer into an XML string.
   * A null string is returned if the integer type is invalid.
   */
  public static String toModeXML(int mode) {
    return (String) s_mode_int2xml.get(new Integer(mode));
  }

  /**
   * Convert the argument XML mode string into an integer.
   * An exception is thrown if the string type is invalid.
   */
  public static int fromModeXML(String mode) throws ParseSymbolException {
    return lookup(s_mode_xml2int, mode);
  }

  /**
   * Convert the definition modifier integer into an XML string.
   * A null string is returned if the integer type is invalid.
   */
  public static String toDefXML(int modifier) {
    return (String) s_def_int2xml.get(new Integer(modifier));
  }

  /**
   * Conver the definition modifier XML into its integer value.
   * An exception is thrown if the string type is invalid.
   */
  public static int fromDefXML(String modifier) throws ParseSymbolException {
    return lookup(s_def_xml2int, modifier);
  }

  /**
   * Convert the communication modifier integer into an XML string.
   * A null string is returned if the integer type is invalid.
   */
  public static String toComXML(int modifier) {
    return (String) s_com_int2xml.get(new Integer(modifier));
  }

  /**
   * Convert the communication modifier XML into its integer value.
   * An exception is thrown if the string type is invalid.
   */
  public static int fromComXML(String modifier) throws ParseSymbolException {
    return lookup(s_com_xml2int, modifier);
  }

  /**
   * Convert the array order integer into an XML string.
   * A null string is returned if the integer type is invalid.
   */
  public static String toOrderXML(int order) {
    return (String) s_order_int2xml.get(new Integer(order));
  }

  /**
   * Convert the array order XML into its integer value.
   * An exception is thrown if the string type is invalid.
   */
  public static int fromOrderXML(String order) throws ParseSymbolException {
    return lookup(s_order_xml2int, order);
  }

  /**
   * Convert the assertion integer into an XML string.
   * A null string is returned if the integer is invalid.
   */
  public static String toAssertionXML(int assertion) {
    return (String) s_assert_int2xml.get(new Integer(assertion));
  }

  /**
   * Convert the assertion XML into its integer value.
   * An exception is thrown if the string type is invalid.
   */
  public static int fromAssertionXML(String assertion) 
    throws ParseSymbolException 
  {
    return lookup(s_assert_xml2int, assertion);
  }

  /**
   * Convert the unary expression operator integer into an XML string.
   * A null string is returned if the integer is invalid.
   */
  public static String toUnaryOpXML(int literal) {
    return (String) s_unary_int2xml.get(new Integer(literal));
  }

  /**
   * Convert the unary expression operator XML into its integer value.
   * An exception is thrown if the string type is invalid.
   */
  public static int fromUnaryOpXML(String literal) 
    throws ParseSymbolException 
  {
    return lookup(s_unary_xml2int, literal);
  }

  /**
   * Convert the binary expression operator integer into an XML string.
   * A null string is returned if the integer is invalid.
   */
  public static String toBinaryOpXML(int literal) {
    return (String) s_binary_int2xml.get(new Integer(literal));
  }

  /**
   * Convert the binary expression operator XML into its integer value.
   * An exception is thrown if the string type is invalid.
   */
  public static int fromBinaryOpXML(String literal) 
    throws ParseSymbolException 
  {
    return lookup(s_binary_xml2int, literal);
  }

  /**
   * Convert the string into an integer using the specified hash map.  If the
   * string does not exist in the hash map, then throw an exception.
   */
  private static int lookup(HashMap h, String s) throws ParseSymbolException {
    Integer i = (Integer) h.get(s);
    if (i == null) {
      throw new ParseSymbolException(
        "Unknown string type \"" + s + "\" in XML");
    }
    return i.intValue();
  }
}
