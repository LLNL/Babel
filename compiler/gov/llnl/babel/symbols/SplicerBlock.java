//
// File:        SplicerBlock.java
// Package:     gov.llnl.babel.symbols
// Revision:    @(#) $Id: SplicerBlock.java 4618 2005-05-09 22:54:29Z dahlgren $
// Description: sidl symbol representing a collection of splicer contents
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

/**
 * The <code>SplicerBlock</code> class is a simple collection of 
 * <code>SB</code> instances.
 */
public class SplicerBlock {
   private String    d_location = null;
   private String    d_name     = null;
   private ArrayList d_contents = new ArrayList();

   /**
    * Create a new <code>SplicerBlock</code>.
    */
   public SplicerBlock(String location, String name) {
     d_location = location;
     d_name     = name;
   }

   /**
    * Create a new <code>SplicerBlock</code>.
    */
   public SplicerBlock(String location, String name, String impl) {
     d_location = location;
     d_name     = name;
     d_contents.add(impl);
   }

   /**
    * Create a new <code>SplicerBlock</code>.
    */
   public SplicerBlock(String location, String name, boolean def, String impl) {
     d_location = location;
     d_name     = name;
     d_contents.add(impl);
   }

   /**
    * Append the splicer contents.
    */
   public void addContents(String impl) {
     d_contents.add(impl);
   }

   /**
    * Return location of splicer block.
    */
   public String getLocation() {
     return d_location;
   }

   /**
    * Return name of splicer block.
    */
   public String getName() {
     return d_name;
   }

   /**
    * Return the contents of the splicer block as a list of Strings.
    */
   public ArrayList getSplicerContents() {
     return d_contents;
   }

}
