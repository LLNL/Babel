//
// File:        F90Method.java
// Package:     gov.llnl.babel.backend.fortran
// Copyright:   (c) 2005 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision: 7188 $
// Date:        $Date: 2011-09-27 11:38:42 -0700 (Tue, 27 Sep 2011) $
// Description: Used to find symbol conflicts for multiple use statements
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

package gov.llnl.babel.backend.fortran;

import gov.llnl.babel.symbols.Argument;
import gov.llnl.babel.symbols.Type;
import java.util.Iterator;
import java.util.List;

/**
 * This class is used to find symbols that will collide when
 * use'ing multiple F90 modules.
 */
class F90Method implements Comparable {
  private String d_methodName;
  private List   d_arguments;

  public F90Method(String methodName,
                   List   arguments)
  {
    d_methodName = methodName;
    d_arguments = arguments;
  }

  public boolean equals(Object o)
  {
    return compareTo(o) == 0;
  }

  private static int compareArrays(Type t1,
                                   Type t2)
  {
    final Type a1 = t1.getArrayType();
    final Type a2 = t2.getArrayType();
    if ((a1 == null) && (a2 == null)) return 0;
    if (a1 == null) return -1;
    if (a2 == null) return 1;
    final int typeCompare = compareTypes(t1, t2);
    if (typeCompare == 0) {
      return t1.getArrayDimension() - t2.getArrayDimension();
    }
    return typeCompare;
  }

  private static int compareTypes(Type t1, Type t2)
  {
    final int codeCompare = t1.getDetailedType() - t2.getDetailedType();
    if (codeCompare == 0) {
      switch(t1.getDetailedType()) {
      case Type.CLASS:
      case Type.INTERFACE:
        return t1.getSymbolID().compareTo(t2.getSymbolID());
      case Type.ARRAY:
        return compareArrays(t1, t2);
      }
    }
    return codeCompare;
  }

  public int compareTo(Object o)
  {
    F90Method method = (F90Method)o;
    final int nameCompare = d_methodName.compareTo(method.d_methodName);
    if (nameCompare == 0) {
      final int lengthCompare = d_arguments.size() - method.d_arguments.size();
      if (lengthCompare == 0) {
        Iterator i = d_arguments.iterator();
        Iterator j = method.d_arguments.iterator();
        while (i.hasNext() && j.hasNext()) {
          final int typeCompare = 
            compareTypes(((Argument)i.next()).getType(), 
                         ((Argument)j.next()).getType());
          if (typeCompare != 0) return typeCompare;
        }
      }
      return lengthCompare;
    }
    return nameCompare;
  }

  private static int typeHashCode(Type t)
  {
    if (t != null) {
      switch(t.getDetailedType()) {
      case Type.INTERFACE:
      case Type.CLASS:
        return t.getSymbolID().hashCode();
      case Type.ENUM:
        return Type.LONG;
      case Type.ARRAY:
        Type a = t.getArrayType();
        if (a == null) return 133;
        return typeHashCode(a)*t.getArrayDimension();
      default:
        return t.getDetailedType()*97;
      }
    }
    return 0;
  }

  public int hashCode()
  {
    int result = d_methodName.hashCode();
    Iterator i = d_arguments.iterator();
    while (i.hasNext()) {
      Argument a = (Argument)i.next();
      result += typeHashCode(a.getType());
    }
    return result;
  }

  public String getName()
  {
    return d_methodName;
  }
}
