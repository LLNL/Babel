//
// File:        SplicerList.java
// Package:     gov.llnl.babel.symbols
// Revision:    @(#) $Id: SplicerList.java 4618 2005-05-09 22:54:29Z dahlgren $
// Description: sidl symbol representing a collection of splicer blocks
//
// Copyright (c) 2006, Lawrence Livermore National Security, LLC
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

import java.util.ArrayList;
import java.util.Iterator;

/**
 * The <code>SplicerList</code> class is a simple collection of 
 * <code>SplicerBlock</code> instances.
 */
public class SplicerList {
   private ArrayList d_list = null;

   /**
    * Create a new <code>SplicerList</code>.
    */
   public SplicerList() {
     d_list = new ArrayList();
   }

   /**
    * Append the splicer block specified by the location and name.  If the 
    * block does not exist, then a new one is automatically created.
    */
   public void addSplicerBlock(String location, String name) {
      SplicerBlock sb = getSplicerBlock(location, name);
      if (sb == null) {
        sb = new SplicerBlock(location, name);
        d_list.add(sb);
      }
   }

   /**
    * Append the splicer contents to the splicer block specified by the 
    * location and name.  If the block does not exist, then a new one
    * is automatically created.
    */
   public void addSplicerContents(String location, String name, String impl) {
      addSplicerBlock(location, name);
      SplicerBlock sb = getSplicerBlock(location, name);
      sb.addContents(impl);
   }

   /**
    * Return the splicer block with the specified location and name.
    */
   public SplicerBlock getSplicerBlock(String location, String name) {
     SplicerBlock block = null;
     for (Iterator iter = d_list.iterator(); 
          iter.hasNext() && (block == null); ) 
     {
       SplicerBlock sb = (SplicerBlock) iter.next();
       if ( sb.getLocation().equals(location) && sb.getName().equals(name) ) {
         block = sb;
       }
     }
     return block;
   }

   /**
    * Return the contents of the splicer block with the specified location and 
    * name.
    */
   public ArrayList getSplicerContents(String location, String name) {
     return getSplicerBlock(location, name).getSplicerContents();
   }

   /**
    * Return TRUE if splicer block found; otherwise, return FALSE.
    */
  public boolean hasSplicerBlock(String location, String name) {
    return (getSplicerBlock(location, name) != null);
  }

   /**
    * Return a list of splicer block(s) with the specified location.
    */
   public ArrayList getSplicerBlocks(String location) {
     ArrayList blocks = new ArrayList();
     for (Iterator iter = d_list.iterator(); iter.hasNext(); ) {
       SplicerBlock sb = (SplicerBlock) iter.next();
       if (sb.getLocation().equals(location)) {
         blocks.add(sb);
       }
     }
     return blocks.isEmpty() ? null : blocks;
   }

}
