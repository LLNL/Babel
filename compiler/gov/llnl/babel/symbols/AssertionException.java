//
// File:        AssertionException.java
// Package:     gov.llnl.babel.symbols
// Revision:    @(#) $Id $
// Description: exception thrown when an assertion or its expression is invalid.
//
// Copyright (c) 2003-2004, Lawrence Livermore National Security, LLC
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
 * A <code>AssertionException</code> is thrown if an assertion or its 
 * expression is determined to be invalid.
 */
public class AssertionException extends Exception {
   /**
	* 
	*/
	private static final long serialVersionUID = -7327461824018572753L;

   /**
    * Create a new exception object with the specified message.
    */
   public AssertionException(String msg) {
      super(msg);
   }

   /**
    * Create a new exception object with the specified preface and message.
    */
   public AssertionException(String preface, String msg) {
      super(preface + ": " + msg);
   }
}
