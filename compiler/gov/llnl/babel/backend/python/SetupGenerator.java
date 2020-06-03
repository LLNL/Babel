//
// File:        SetupGenerator.java
// Package:     gov.llnl.babel.backend.python
// Revision:    @(#) $Revision: 7188 $
// Date:        $Date: 2011-09-27 11:38:42 -0700 (Tue, 27 Sep 2011) $
// Description: Generate setup.py for Python extension modules
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

package gov.llnl.babel.backend.python;
import gov.llnl.babel.Context;
import gov.llnl.babel.backend.BuildGenerator;
import gov.llnl.babel.backend.ContextAware;
import gov.llnl.babel.backend.FileListener;
import gov.llnl.babel.backend.Utilities;
import gov.llnl.babel.backend.writers.ChangeWriter;
import gov.llnl.babel.backend.writers.LanguageWriter;
import gov.llnl.babel.backend.writers.LanguageWriterForPython;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.Type;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


/**
 * This class writes a <code>setup.py</code> file to build all the 
 * Python extension modules and implementation code.
 */
public class SetupGenerator implements BuildGenerator, FileListener,
                                       ContextAware
{
  private Context d_context;
  private Map d_mod2csrc  = new HashMap();
  private Set d_pymodules  = new TreeSet();
  private Set d_headers = new TreeSet();
  private Set d_packages = new TreeSet();

  private void writeList(LanguageWriter lw,
                         String heading,
                         Iterator i)
  {
      lw.println(heading + " = [");
      lw.tab();
      while (i.hasNext()) {
        lw.print("'" + i.next().toString() + "'");
        lw.println(i.hasNext() ? "," : "");
      }
      lw.backTab();
      lw.println("],");
  }

  private void processArgs(LanguageWriter lw)
  {
    lw.println();
    lw.println("inc_re = compile('^--include-dirs=(.*)$')");
    lw.println("shortinc_re = compile('^-I(.*)$')");
    lw.println("lib_re = compile('^--library-dirs=(.*)$')");
    lw.println("shortlib_re = compile('^-L(.*)$')");
    lw.println("exlib_re = compile('^--extra-library=(.*)$')");
    lw.println("shortexlib_re = compile('^-l(.*)$')");
    lw.println("old_argv = sys.argv");
    lw.println("sys.argv = []");
    lw.println("inc_dirs = ['.']");
    lw.println("lib_dirs = []");
    lw.println("libs = ['sidl']");
    lw.println("if sys.platform[0:3] == \"aix\":");
    lw.tab();
    lw.println("extra_args=[ \"-qlanglvl=extc99\" ]");
    lw.backTab();
    lw.println("else:");
    lw.tab();
    lw.println("extra_args=[ ]");
    lw.backTab();

    lw.println();
    lw.println("for i in old_argv:");
    lw.tab();
    lw.println("m = inc_re.match(i) or shortinc_re.match(i)");
    lw.println("if (m):");
    lw.tab();
    lw.println("if (len(m.group(1))): inc_dirs.extend(m.group(1).split(':'))");
    lw.backTab();
    lw.println("else:");
    lw.tab();
    lw.println("m = lib_re.match(i) or shortlib_re.match(i)");
    lw.println("if (m):");
    lw.tab();
    lw.println("if (len(m.group(1))): lib_dirs.extend(m.group(1).split(':'))");
    lw.backTab();
    lw.println("else:");
    lw.tab();
    lw.println("m = exlib_re.match(i) or shortexlib_re.match(i)");
    lw.println("if (m):");
    lw.tab();
    lw.println("if (len(m.group(1))): libs.extend(m.group(1).split(':'))");
    lw.backTab();
    lw.println("else:");
    lw.tab();
    lw.println("sys.argv.append(i)");
    lw.backTab();
    lw.backTab();
    lw.backTab();
    lw.backTab();
  }

  /**
   * Generate an extended name.
   */
  private String nameExt() {
    StringBuffer result = new StringBuffer();
    Iterator i = d_packages.iterator();
    while (i.hasNext()) {
      String pkg = (String)i.next();
      if (pkg.indexOf('.') < 0) {
        result.append('_');
        result.append(pkg);
      }
    }
    return result.toString();
  }

  /**
   * Generate the setup.py to build the Python extension modules.
   *
   * @exception java.io.IOException this is a exception that contains
   * all the I/O exceptions that occurred during file generation.
   */
  public void createAll()
    throws IOException
  {
    String directory = 
      d_context.getConfig().getOutputDirectory();
    final String filename = d_context.getConfig().getMakePrefix() + 
      "setup.py";
    Writer fw = null;
    Iterator i;
    if (".".equals(directory)) {
      directory = "";
    }
    else {
      File nd = new File(directory);
      if ((!nd.isDirectory()) && (!nd.mkdirs())) {
        throw new IOException("Unable to make directory " +
                              directory + ".");
      }
    }
    // the output directory must end with File.separator if it's nontrivial
    if ((directory != null) && (directory.length() > 0) &&
        !directory.endsWith(File.separator)) {
      directory = directory + File.separator;
    }
    try {
      if (d_context.getConfig().getProtectLastTimeModified()) {
        fw = ChangeWriter.createWriter(filename, directory);
      }
      else {
        fw = new BufferedWriter(new FileWriter(directory + filename));
      }
      LanguageWriterForPython lw = new
        LanguageWriterForPython(new PrintWriter(fw), d_context);
      lw.printlnUnformatted("#! /usr/bin/env python");
      lw.printlnUnformatted("# Build file for Python modules");
      lw.println("import sys");
      lw.println("from re import compile");
      lw.println("from distutils.core import setup, Extension");
      processArgs(lw);
      lw.println("setup(name='llnl_babel" + nameExt() + "',");
      lw.tab();
      lw.println("include_dirs=inc_dirs,");
      // writeList(lw, "py_modules", d_pymodules.iterator());
      writeList(lw, "headers", d_headers.iterator());
      writeList(lw, "packages", d_packages.iterator());
      lw.println("ext_modules = [");
      lw.tab();
      for(i = Utilities.sort(d_mod2csrc.keySet()).iterator(); i.hasNext(); ){
        String module = (String)i.next();
        Set files = (Set)d_mod2csrc.get(module);
        lw.println("Extension('" + module + "',");
        lw.tab();
        lw.print("[");
        for(Iterator j = files.iterator(); j.hasNext(); ){
          lw.print("\"" + j.next().toString() + "\"");
          lw.println(j.hasNext() ? "," : "");
        }
        lw.println("],");
        lw.println("library_dirs=lib_dirs,");
        lw.print("libraries=libs,");
        lw.println("extra_compile_args=extra_args)");
        lw.println(i.hasNext() ? "," : "");
        lw.backTab();
      }
      lw.backTab();
      lw.println("])");
      lw.backTab();
    }
    finally {
      if (null != fw) {
        fw.close();
      }
    }
  }

  private Set getCSources(String module)
  {
    Set result = (Set)d_mod2csrc.get(module);
    if (null == result) {
      result = new TreeSet();
      d_mod2csrc.put(module, result);
    }
    return result;
  }

  private String join(String dir, String file)
  {
    StringBuffer buf = new StringBuffer(dir.length() + file.length());
    final String outputDir = 
      d_context.getConfig().getOutputDirectory();
    if (!".".equals(outputDir) && dir.startsWith(outputDir)) {
      // remove the leading output directory
      dir = dir.substring(outputDir.length());
      if (dir.startsWith(File.separator) ||
          dir.startsWith("/")) {
        dir = dir.substring(1);
      }
                         
    }
    if (File.separatorChar != '/') {
      dir = dir.replace(File.separatorChar, '/');
    }
    return buf.append(dir).append(file).toString();
  }

  /**
   * This method is called by the {@link gov.llnl.babel.backend.FileManager}
   * for each new file it creates. This object caches the information it
   * needs to setup.py creation later.
   * 
   *
   * @param id     the file is related to this symbol ID.
   * @param type   indicates the type of the symbol ID (one of the
   *               constants from {@link gov.llnl.babel.symbols.Type}.
   * @param role   this describes the role the file plays. For example,
   *               the file could be a <code>STUBSRCS</code> file or a
   *               <code>IMPLSRCS</code> file. The role strings used
   *               are determined by the backend.
   * @param dir    the path (relative or absolute) of the directory where
   *               the file will be created.
   * @param name   the name of the file not including any directory
   *               information. The complete name of the file should
   *               be <code>dir + name</code>.
   */
  public void newFile(SymbolID id,
                      int      type,
                      String   role,
                      String   dir,
                      String   name)
  {
    final String module = id.getFullName();
    if ("PYMOD_HDRS".equals(role)) {
      d_headers.add(join(dir, name));
    }
    else if ("PYMOD_SRCS".equals(role)) {
      Set files = getCSources(module);
      files.add(join(dir, name));
    }
    else if ("PYTHONSRC".equals(role)) {
      if (Type.ENUM != type) {
        d_pymodules.add(module + "_Impl");
      }
      else {
        d_pymodules.add(module);
      }
    }
    else if ("PYTHONADMIN".equals(role)) {
      d_packages.add(module);
    }
  }

  public Set getLanguages()
  {
    Set result = new TreeSet();
    result.add("python");
    return result;
  }
  public void setContext(Context context) {
    d_context = context;
  }
}
