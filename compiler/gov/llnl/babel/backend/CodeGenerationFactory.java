//
// File:        CodeGenerationFactory.java
// Package:
// Revision:     @(#) $Id: CodeGenerationFactory.java 7188 2011-09-27 18:38:42Z adrian $
// Description:
//
// Copyright (c) 2000-2002, Lawrence Livermore National Security, LLC
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

import gov.llnl.babel.backend.CodeGenerator;
import gov.llnl.babel.cli.CommandLineExtension;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;


/**
 * This class stores and serves up instances CodeGenerator based
 * on the string name of the code that the user wishes to generate.
 * Classes derived from CodeGenerator will register themselves
 * with this class (usually as part of a static initialization block).
 * If a match based on the string name is not available, this class
 * will try to dynamically load an instance based on some simple
 * naming rules.
 *
 * @see CodeGenerator
 */
public class CodeGenerationFactory {
  private static final String[] s_classList = {
    "gov.llnl.babel.backend.ior.GenerateIORClient",
    "gov.llnl.babel.backend.ior.GenerateIORServer",
    "gov.llnl.babel.backend.c.GenerateClientC",
    "gov.llnl.babel.backend.c.GenerateServerC",
    // baa -- some experiment stuff that really needs to
    // go in via a properties file.
    //"gov.llnl.babel.backend.lite.GenerateClientC",
    //"gov.llnl.babel.backend.lite.GenerateServerC",
    "gov.llnl.babel.backend.python.GenPythonClient",
    "gov.llnl.babel.backend.python.GenPythonServer",
    "gov.llnl.babel.backend.ucxx.GenerateCxxClient",
    "gov.llnl.babel.backend.ucxx.GenerateCxxServer",
    "gov.llnl.babel.backend.fortran.GenerateClient",
    "gov.llnl.babel.backend.fortran.GenerateServer",
    "gov.llnl.babel.backend.jdk.GenerateClientJava",
    "gov.llnl.babel.backend.jdk.GenerateServerJava",
    "gov.llnl.babel.backend.matlab.GenerateMatlabClient",
    "gov.llnl.babel.backend.MakefileGenerator",
    "gov.llnl.babel.backend.PackageMakeInfoGenerator",
    "gov.llnl.babel.backend.python.SetupGenerator",
    "gov.llnl.babel.backend.sidl.GenerateSidl",
    "gov.llnl.babel.backend.html.GenerateDoc",
    "gov.llnl.babel.backend.xml.GenerateXML",
    "gov.llnl.babel.backend.DependenciesGenerator"
  };

  /**
   * map from language string name to CodeGenerator.
   */
  private HashMap m_hashmap;

  /**
   * map from string name to Collection of BuildGenerators.
   */
  private HashMap m_buildmap;

  /**
   * Create a new instance of <code>CodeGenerationFactory</code> and
   * initialize with reasonable defaults.
   */
  public CodeGenerationFactory() {
    m_hashmap = new HashMap();
    m_buildmap = new HashMap();
    for(int i = 0; i < s_classList.length; ++i ){
      addClass(s_classList[i]);
    }
    StringTokenizer tok =
      new StringTokenizer(System.getProperty("babel.extensions", ""), ",");
    while (tok.hasMoreTokens()) {
      addClass(tok.nextToken());
    }
  }

  private void addClass(String className)
  {
    try {
      Class cls = Class.forName(className);
      Object obj = cls.newInstance();
      boolean matches = false;
      if (obj instanceof CodeGenerator) {
        matches = true;
        registerCodeGenerator((CodeGenerator)obj);
      }
      if (obj instanceof BuildGenerator) {
        matches = true;
        registerBuildGenerator((BuildGenerator)obj);
      }
      if (obj instanceof CommandLineExtension) {
        matches = true;
      }
      if (!matches) {
        System.err.println("Babel: Warning: Extension '" +
                           className + 
                           "' has no recognized extension types to register.");
      }
    }
    catch (ClassNotFoundException cnfe) {
      System.err.println("Babel: Error: Unable to find backend '" +
                         className + "'. Check your Java classpath.");
      cnfe.printStackTrace(System.err);
    }
    catch (Exception e) {
      System.err.println("Babel: Error: Unable to instantiate backend '" +
                         className + "'.");
      e.printStackTrace(System.err);
    }
    catch (LinkageError e) {
      System.err.println("Babel: Error: Unable to load backend '" +
                         className + "'. Linkage problem.");
      e.printStackTrace(System.err);
    }
  }
  
  /**
   * Register a CodeGenerators with the factory.
   *
   * @param gen instance of a CodeGenerator;
   */
  public void registerCodeGenerator( CodeGenerator gen)
  {
    Iterator i = gen.getLanguages().iterator();
    final String mode = gen.getType();
    while (i.hasNext()) {
      String lang = (String)i.next();
      String key = hashLanguageAndType(lang, mode);
      m_hashmap.put(key, gen.getClass());
    }
  }

  public void registerBuildGenerator( BuildGenerator gen)
  {
    Iterator i = gen.getLanguages().iterator();
    while (i.hasNext()) {
      String lang = (String)i.next();
      Collection buildGens = (Collection)m_buildmap.get(lang);
      if (null == buildGens){
        buildGens = new LinkedList();
        m_buildmap.put(lang, buildGens);
      }
      buildGens.add(gen.getClass());
    }
  }

  /**
   * Get the build generator appropriate for this language.
   *
   * @return A collection of BuildGenerator interfaces. The
   * elements of the collection are newly created instances.
   * Two calls to getBuildGenerators with the same arguments
   * will return separate instaces of the BuildGenerators.
   */
  public Collection getBuildGenerators(String language)
  {
    Collection buildGens = (Collection)m_buildmap.get(language);
    ArrayList result = 
      new ArrayList((buildGens != null) ? buildGens.size() : 0);
    if (buildGens != null) {
      Iterator i = buildGens.iterator();
      while (i.hasNext()) {
        Class c = (Class)i.next();
        try {
          result.add(c.newInstance());
        }
        catch (Exception e) {
          System.err.println("Babel: Warning: Unexpected trouble creating BuildGenerator.");
          e.printStackTrace();
        }
      }
    }
    return result;
  }

  /**
   * Get a registered codeGenerator from the factory.
   * @param language String name of the language
   * @param mode Usually one of "stub", "skel", "ior", or "text"
   * @return Live CodeGenerator or null if no match found. When
   * not-null, the return value is a new instance of a CodeGenerator (i.e.,
   * two successive calls to getCodeGenerator with the same arguments
   * will return two distinct instances of this CodeGenerator).
   */
  public CodeGenerator getCodeGenerator( String language, String mode )
  {
    String key = hashLanguageAndType( language, mode );
    CodeGenerator result = null;
    Object c = m_hashmap.get(key);
    if (c != null) {
      try {
        result = (CodeGenerator)((Class)c).newInstance();
      }
      catch (Exception e) {
        System.err.println("Babel: Warning: Unexpected trouble creating CodeGenerator.");
        e.printStackTrace();
      }
    }
    return result;
  }

  /**
   * Hash the language and mode into a single (reproducable) string.
   */
  private String hashLanguageAndType( String language, String mode ) {
    String hash = language + "-" + mode;
    return hash.toLowerCase();
  }

  public void printRegisteredGenerators( PrintStream out ) {
    out.println("Registered CodeGenerators = " + m_hashmap.size() );
    for( Iterator it = m_hashmap.keySet().iterator(); it.hasNext(); ) {
      String key = (String)it.next();
      out.println("   key = \"" + key);
    }
  }
}
