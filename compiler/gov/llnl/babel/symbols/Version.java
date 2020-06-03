//
// File:        Version.java
// Package:     gov.llnl.babel.symbols
// Revision:    @(#) $Id: Version.java 7188 2011-09-27 18:38:42Z adrian $
// Description: symbol version description of the form "V1.V2...Vn"
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

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Class <code>Version</code> represents a symbol version of the general
 * form "V1.V2...Vn" where Vi is a non-negative integer.  Trailing zeros
 * in the version number are not significant.  That is, "1.2" is the same
 * as "1.2.0" and "1.2.0.0.0".
 */
public class Version {
   private ArrayList d_version; // array of Integer version numbers

   /**
    * Create a new <code>Version</code> class that will be constructed
    * through member function <code>appendVersionNumber</code>.  An empty
    * version is considered to be version "0".
    */
   public Version() {
      d_version = new ArrayList();
   }

   /**
    * Create a new <code>Version</code> class by parsing a version string
    * of the form "V1.V2...Vn" where each Vi is a non-negative integer.
    * If the version is not of this form, then the constructor throws a
    * <code>NumberFormatException</code>.
    */
   public Version(String version) throws NumberFormatException {
      d_version = new ArrayList();
      if ("0".equals(version)) return;

      StringTokenizer tokens = new StringTokenizer(version, ".");
      while (tokens.hasMoreTokens()) {
         Integer part = null;
         
         part = new Integer(tokens.nextToken());

         if (part.intValue() < 0) {
            throw new NumberFormatException(
               "Negative integer in version \"" + version + "\"");
         }
         
         d_version.add(part);
      }
   }

   /**
    * Add another version number to the existing version description.  If
    * current version is "V1.V2...Vn" and this number is Vm, then the new
    * version description will be "V1.V2...Vn.Vm".  If the version is a
    * non-negative integer, then a <code>NumberFormatException</code> is
    * thrown.
    */
   public void appendVersionNumber(int v) {
      if (v < 0) {
         throw new NumberFormatException(
            "Negative value in version \"" + String.valueOf(v) + "\"");
      }
      d_version.add(new Integer(v));
   }

   /**
    * Return the current version description as a version string.  If no
    * version has been set, then the returned version is "0".  Otherwise,
    * the version is of the form "V1.V2...Vn".
    */
   public String getVersionString() {
      StringBuffer buffer = new StringBuffer();

      int size = d_version.size();
      if (size == 0) {
         buffer.append("0");
      } else {
         for (int i = 0; i < size; i++) {
            if (i > 0) buffer.append(".");
            buffer.append(((Integer) d_version.get(i)).toString());
         }
      }

      return buffer.toString();
   }

   /**
    * Return the number of sub-parts in the version description.  For a
    * version string "V1.V2...Vn", this routine returns the value of n.
    */
   public int getVersionLength() {
      return d_version.size();
   }

  public boolean isUnspecified() {
    return d_version.size() == 0;
  }

   /**
    * Return the version number at a particular location of the version
    * string using zero-based indexing.  If the requested index is less
    * than zero or is equal to or greater than the version length, then
    * zero is returned.
    */
   public int getVersionNumberAt(int i) {
      if ((i < 0) || (i >= d_version.size())) {
         return 0;
      } else {
         return ((Integer) d_version.get(i)).intValue();
      }
   }

   /**
    * Return whether two version objects represent the same version
    * number.  Note that version "1.2.0" is considered the same as
    * "1.2" since trailing zeros are ignored.
    */
   public boolean isSame(Version other) {
      int a = getVersionLength();
      int b = other.getVersionLength();
      int m = (a > b ? a : b);

      for (int i = 0; i < m; i++) {
         if (getVersionNumberAt(i) != other.getVersionNumberAt(i)) {
            return false;
         }
      }

      return true;
   }

   /**
    * Return whether this version is greater than the version in the
    * argument.  Version "W1.W2...Wn" is considered greater than version
    * "V1.V2...Vn" if for some i, Wj equals Vj for all j less than i and
    * Wi is greater than Vi.  If the two versions have different lengths,
    * then the shorter version is padded with zeros.
    */
   public boolean isGreaterThan(Version other) {
      int a = getVersionLength();
      int b = other.getVersionLength();
      int m = (a > b ? a : b);

      for (int i = 0; i < m; i++) {
         if (getVersionNumberAt(i) > other.getVersionNumberAt(i)) {
            return true;
         }
      }

      return false;
   }

   /**
    * Return a hash code for a version.
    */
   public int hashCode() {
      int h = 0, g;
      int lastNonzero = d_version.size();
      while ((lastNonzero > 0) &&
             (((Integer)d_version.get(lastNonzero - 1)).intValue() != 0)) {
         lastNonzero--;
      }
      for(int i = 0; i < lastNonzero; i++) {
         int digit = ((Integer)d_version.get(i)).intValue();
         h = (h << 4) + digit;
         g = h & 0xf0000000;
         if (g != 0) {
            h = h ^ (g >>> 24);
            h = h ^ g;
         }
      }
      return h;
   }
}
