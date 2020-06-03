//
// File:        GenerateDeprecatedClient.java
// Package:     gov.llnl.babel.backend.cxx
// Copyright:   (c) 2005 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision: 7188 $
// Date:        $Date: 2011-09-27 11:38:42 -0700 (Tue, 27 Sep 2011) $
// Description: Generate a warning if the user does --client=cxx
// 
// Copyright (c) 2005, Lawrence Livermore National Security, LLC
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

package gov.llnl.babel.backend.cxx;

import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.CodeGenerator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Generate a warning if for --client=cxx or --client=c++.
 */
public class GenerateDeprecatedServer implements CodeGenerator {
  public GenerateDeprecatedServer() {}

  public void generateCode(Set symbols) throws CodeGenerationException {
    throw new CodeGenerationException("The C++/CXX binding has been renamed to DC++/DCXX to indicate that it is the *deprecated* C++ binding. In the future, C++/CXX will refer to the next generation C++ binding currently called UC++/UCXX.");
  }

  public String getType() {
    return "skel";
  }

  public boolean getUserSymbolsOnly() {
    return true;
  }

  public Set getLanguages() {
    Set result = new TreeSet();
    result.add("c++");
    result.add("cxx");
    return result;
  }

  public void setName(String name)
    throws CodeGenerationException
 {
    if (! getLanguages().contains(name)) {
      throw new CodeGenerationException
        ("\"" +name + "\" is not a valid name for the cxx generator.");
    }
  }

  public String getName() { return "cxx"; }
  
}
