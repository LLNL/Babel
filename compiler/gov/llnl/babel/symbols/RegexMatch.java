//
// File:        RegexMatch.java
// Package:     gov.llnl.babel.symbols
// Revision:    @(#) $Revision: 7188 $
// Date:        $Date: 2011-09-27 11:38:42 -0700 (Tue, 27 Sep 2011) $
// Description: Use java.util.regex if available to match symbol ids
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
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


/**
 * Provide regular expression matching for {@link
 * gov.llnl.babel.symbols.SymbolID} against regular expressions.
 * This class used to use reflection to avoid a compile type dependence
 * on java.util.regex. Now, we assume java.util.regex is available.
 */
public class RegexMatch {
  /**
   * The compiled {@link java.util.regex.Pattern} object.
   */
  private java.util.regex.Pattern d_pattern;

  /**
   * Return <code>true</code> iff the current runtime system has regular
   * expression support.
   */
  public static boolean hasRegex() 
  {
    return true;
  }

  /**
   * Create a regular expression matching object.
   *
   * @param regex  a regular expression to be compiled.
   * @throws gov.llnl.babel.symbols.RegexUnsupportedException this exception
   * indicates that the runtime environment lacks the necessary.
   * @throws gov.llnl.babel.symbols.BadRegexException this exception
   * indicates that the regular expression had a syntax error.  The message
   * tries to indicate where the error occured.
   */
  public RegexMatch(String regex)
    throws RegexUnsupportedException, PatternSyntaxException 
  {
    d_pattern = Pattern.compile(regex);
  }

  private boolean match(String str)
  {
    return d_pattern.matcher(str).matches();
  }

  /**
   * Return <code>true</code> if the {@link SymbolID} matches the
   * compiled regular expression.
   *
   * @param id  the symbol that will be compared with the regular
   * expression.
   * @return <code>true</code> indicates that the {@link SymbolID} matches
   * the regular expression.
   */
  public boolean match(SymbolID id)
  {
    return match(id.getFullName());
  }
}
