//
// File:        LineRedirector.java
// Package:     gov.llnl.babel.backend.writers
// Revision:    @(#) $Id: LineRedirector.java 7188 2011-09-27 18:38:42Z adrian $
// Description: C++ language writer for backend code generation
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

package gov.llnl.babel.backend.writers;

/**
 * Interface <code>LineRedirector</code> is implemented by language writers
 * that support #line Preprocessor redirectives, or some similar mechanism
 * for VPATH builds. (e.g. C and C++)
 */
public interface LineRedirector { 

   /**
    * Mark a region where the debugger should be redirected to another file 
    * starting on a specific line
    */
   public void redirectBegin( String path, int line );

   /**
    * End the region where the debugger can just follow this file.
    */
   public void redirectEnd( String path, int line );

    /** 
     * get the current line number being printed.
     */
    public int getLineCount();

}

