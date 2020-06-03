//
// File:        Comment.java
// Package:     gov.llnl.babel.symbols
// Revision:    @(#) $Id: Comment.java 7188 2011-09-27 18:38:42Z adrian $
// Description: a documentation comment string as an array of comment lines
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

/**
 * The <code>Comment</code> class represents a documentation comment.
 * The comment is stored as an array of strings, with each array element
 * representing one line of the comment.
 */
public class Comment extends ASTNode {
   private String[] d_comment;

   /**
    * This constructor for the <code>Comment</code> class takes an array of
    * strings, one string for each line of the comment.  The input argument
    * may be null, which indicates "no comment."
    */
   public Comment(String[] comment) {
      d_comment = comment;
   }

   /**
    * Return the comment as an array of strings, with one string for each
    * line of the comment.  The return argument may be null, in which case
    * there is no comment text.
    */
   public String[] getComment() {
      return d_comment;
   }

  /**
   * Return true if the comment is empty (i.e., no lines); otherwise,
   * return false.
   */
  public boolean isEmpty() {
    boolean empty = false;
    String[] lines = d_comment;
    if ((lines == null) || (lines.length == 0)) {
      empty = true;
    }
    return empty;
  }

  public int hashCode() {
    int result = 0;
    if (d_comment != null) {
      for(int i = 0; i < d_comment.length ; ++i) {
        result += d_comment[i].hashCode();
      }
    }
    return result;
  }

  /**
   * Return true if two comments are exactly equal. Two comments are equal
   * if they have the same number of lines, and each corresponding line
   * is equal. This is false if the parameter is not a comment.
   *
   * @param o  the object to test
   */
  public boolean equals(Object o) {
    if (o != null && o instanceof Comment) {
      Comment arg = (Comment)o;
      if (d_comment == arg.d_comment) return true;
      if ((d_comment != null) && (arg.d_comment != null) &&
          (d_comment.length == arg.d_comment.length)) {
        for(int i = 0; i < d_comment.length; ++i ){
          if (!d_comment[i].equals(arg.d_comment[i])) return false;
        }
        return true;
      }
    }
    return false;
  }

  public static Comment combineComments(Comment original,
                                        Comment additional)
  {
    if (original == null) 
      return additional;
    if ((additional == null) || 
        (additional.d_comment == null) ||
        original.equals(additional)) 
      return original;
    if (original.d_comment == null) 
      return additional;
    final int origlen = original.d_comment.length;
    final int addlen = additional.d_comment.length;
    String [] comment = new String[origlen + addlen + 1];
    System.arraycopy(original.d_comment, 0, comment, 0, origlen);
    System.arraycopy(additional.d_comment, 0, comment, origlen+1, addlen);
    // add a blank line between these comments
    comment[origlen] = "";
    return new Comment(comment);
  }
}
