//
// File:        PrettyWriterTest.java
// Package:     gov.llnl.babel.backend.writers
// Revision:    @(#) $Id: PrettyWriterTest.java 7188 2011-09-27 18:38:42Z adrian $
// Description: unit test for the pretty writer routines
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

import gov.llnl.babel.backend.writers.PrettyWriter;
import java.io.PrintWriter;

/**
 * Class <code>PrettyWriterTest</code> is a simple unit test for the pretty
 * writer class.  Several different coding styles are output to the standard
 * output to test the formatting of the pretty writer.
 */
public class PrettyWriterTest {
   /**
    * Method <code>main</code> is the entry point for the unit test.
    * It ignores the command line arguments and outputs to the standard
    * output a series of code snippets formatted using the pretty writer.
    */
   public static void main(String argv[]) {
      PrintWriter writer = new PrintWriter(System.out);

      /*
       * Fortran formatting for a simple program.
       */
      PrettyWriter fwriter = new PrettyWriter(writer);
      fwriter.setFirstTabStop(8);
      fwriter.setTabSpacing(3);
      fwriter.defineBlockComment("C", null);
      fwriter.enableLineBreak(72, "     &", null);

      fwriter.println("program test");
      fwriter.enableBlockComment();
      fwriter.println("This is a test of the commenting features");
      fwriter.disableBlockComment();
      fwriter.println("do i = 1, 100");
      fwriter.tab();
      fwriter.println("print *, i");
      fwriter.print("call longargs(");
      int nargs = 100;
      for (int i = 0; i < nargs; i++) {
         fwriter.print(String.valueOf(i));
         if (i != nargs-1) {
            fwriter.print(", ");
         }
      }
      fwriter.println(")");
      fwriter.backTab();
      fwriter.println("end do");
      fwriter.println();
      fwriter.println("stop");
      fwriter.println("end");

      System.out.println();

      /*
       * C/C++ formatting for a simple program
       */
      PrettyWriter cwriter = new PrettyWriter(writer);
      cwriter.setFirstTabStop(0);
      cwriter.setTabSpacing(3);
      cwriter.defineBlockComment(null, " * ");
      cwriter.enableLineBreak(80, null, null);

      cwriter.println("/*");
      cwriter.enableBlockComment();
      cwriter.println("This is a block comment in C or C++ code.");
      cwriter.println("I hope it looks pretty.");
      cwriter.disableBlockComment();
      cwriter.println(" */");
      cwriter.print("int main(");
      cwriter.setTemporaryFirstTabStop();
      cwriter.println("int argc,");
      cwriter.println("char **argv)");
      cwriter.restoreFirstTabStop();
      cwriter.println("{");
      cwriter.tab();
      cwriter.println("/*");
      cwriter.enableBlockComment();
      cwriter.println("Not much to do here except exit...");
      cwriter.disableBlockComment();
      cwriter.println(" */");
      cwriter.println("exit(0);");
      cwriter.println("return 0;");
      cwriter.backTab();
      cwriter.println("}");
   }
}
