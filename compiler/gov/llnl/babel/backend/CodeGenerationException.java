//
// File:        CodeGenerationException.java
// Package:     gov.llnl.babel.backend
// Revision:    @(#) $Id: CodeGenerationException.java 7188 2011-09-27 18:38:42Z adrian $
// Description: exception generated during code generation in back end
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

/**
 * The <code>CodeGenerationException</code> exception class is thrown
 * if an error is detected while generating code in the compiler back
 * end.
 */
public class CodeGenerationException extends Exception {
   /**
    * 
    */
   private static final long serialVersionUID = -302058486968334305L;

   /**
    * Create a new <code>CodeGenerationException</code> exception
    * with the specified message string.
    */
   public CodeGenerationException(String message) {
      super(message);
   }
}
