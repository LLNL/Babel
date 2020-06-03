//
// File:        Version.java
// Package:     gov.llnl.babel
// Revision:    @(#) $Id: Version.java 7188 2011-09-27 18:38:42Z adrian $
// Description: version of the babel compiler
//
// Copyright (c) 2000-2001, Lawrence Livermore National Security, LLC.
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

package gov.llnl.babel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Interface <code>Version</code> contains the version string for the
 * babel compiler.
 */
public class Version {
  private static String s_revision = null;
  private static String s_branch = null;

  public final static String VERSION = "2.0.0";

  public final static String getFullVersion()
  {
    StringBuffer result = new StringBuffer();
    result.append(VERSION);
    synchronized (VERSION) {
      if ((s_revision == null) || (s_branch == null)) {
        ResourceLoader loader= new ResourceLoader();
        InputStream in = null;
        try {
          in = loader.getResourceStream("gov/llnl/babel/revision.txt");
          BufferedReader buf = new BufferedReader(new InputStreamReader(in));
          s_revision = buf.readLine();
          if (s_revision != null) {
            s_branch = buf.readLine();
          }
        }
        catch (IOException ioe) {
          System.err.println("babel: Unable to read revision number and branch information from jar file.");
        }
        finally {
          if (in != null) {
            try {
              in.close();
            }
            catch (IOException ioe) {
            }
          }
        }
      }
      if (s_revision != null) {
        result.append(" (Revision: ");
        result.append(s_revision);
        if (s_branch != null) {
          result.append(" ");
          result.append(s_branch);
        }
        result.append(")");
      }
    }
    return result.toString();
  }
}
