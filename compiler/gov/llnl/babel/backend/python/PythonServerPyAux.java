//
// File:        PythonServerPyAux.java
// Package:     gov.llnl.babel.backend.python
// Copyright:   (c) 2009 Lawrence Livermore National Security, LLC
// Revision:    @(#) $Revision: 7421 $
// Date:        $Date: 2011-12-15 17:06:06 -0800 (Thu, 15 Dec 2011) $
// Description: Extra implementation file
// 
// Copyright (c) 2009, Lawrence Livermore National Security, LLC
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

package gov.llnl.babel.backend.python;

import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.python.Python;
import gov.llnl.babel.backend.writers.LanguageWriterForPython;
import gov.llnl.babel.symbols.Class;

public class PythonServerPyAux {
  private Context d_context = null;

  private LanguageWriterForPython d_lw = null;

  private Class d_cls = null;

  public PythonServerPyAux(Class cls, Context context) {
    d_cls = cls;
    d_context = context;
  }

  public synchronized void generateCode()
    throws CodeGenerationException
  {
    final String filename = Python.auxFilename(d_cls);
    try {
      d_lw = Python.createPyWriter(d_cls, filename, "Auxially file for SIDL class " +
                                   d_cls.getFullName() + " in Python.", d_context);
      d_lw.println();
      d_lw.println("import " + d_cls.getFullName());
      d_lw.println();
      d_lw.println("class " + d_cls.getShortName() + ":");
      d_lw.println();
      d_lw.tab();
      d_lw.println("def __init__(self, IORself):");
      d_lw.tab();
      d_lw.println("if (IORself == None):");
      d_lw.tab();
      d_lw.println("self.__IORself = " + d_cls.getFullName() + "." + d_cls.getShortName() +
                   "(impl = self)");
      d_lw.backTab();
      d_lw.println("else:");
      d_lw.tab();
      d_lw.println("self.__IORself = IORself");
      d_lw.backTab();
      d_lw.backTab();
      d_lw.println();
      d_lw.println();
      d_lw.println("def _getStub(self):");
      d_lw.tab();
      d_lw.println("return self.__IORself");
      d_lw.backTab();
      d_lw.backTab();
    }
    finally {
      if (d_lw != null) {
        d_lw.close();
        d_lw = null;
      }
    }
  }
}
