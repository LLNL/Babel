//
// File:        ElementIterator.java
// Package:     gov.llnl.babel.xml
// Revision:    @(#) $Id: ElementIterator.java 7188 2011-09-27 18:38:42Z adrian $
// Description: iterate over the named children elements of a parent element
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

package gov.llnl.babel.xml;

import java.util.Iterator;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * This iterator will enumerate all of the direct children elements of the
 * parent element with the specified name.  The <code>remove</code> method
 * is not supported.
 */
public class ElementIterator implements Iterator {
   private Element d_next;
   private String  d_name;

   /**
    * Create an iterator that will enumerate the children elements of the
    * parent with the specified name.
    */
   public ElementIterator(Element parent, String name) {
      d_next = null;
      d_name = name;

      if (parent != null) {
         d_next = advance(parent.getFirstChild());
      }
   }

   /**
    * Return true if there is a next matching element and false otherwise.
    */
   public boolean hasNext() {
      return d_next != null;
   }

   /**
    * Return the current element or null if there are no more matching
    * elements.  Advance the iterator to point to the next element.
    */
   public Object next() {
      Element obj = d_next;
      if (d_next != null) {
         d_next = advance(d_next.getNextSibling());
      }
      return obj;
   }

   /**
    * The <code>remove</code> method is not supported and will throw
    * an unsupported operation exception.
    */
   public void remove() {
      throw new UnsupportedOperationException("Cannot remove() from element");
   }

   /**
    * Find the next element starting at <code>n</code> (inclusive) that
    * is both an <code>Element</code> and matches the specified name.
    * If no such element is found, then return null.
    */
   private Element advance(Node n) {
      while (n != null) {
         if ((n instanceof Element) && n.getNodeName().equals(d_name)) {
            break;
         } else {
            n = n.getNextSibling();
         }
      }
      return (Element) n;
   }
}
