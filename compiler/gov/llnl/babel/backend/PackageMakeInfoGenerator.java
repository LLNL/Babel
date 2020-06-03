//
// File:        MakefileGenerator.java
// Package:     gov.llnl.babel.backend
// Revision:    @(#) $Id: PackageMakeInfoGenerator.java 7188 2011-09-27 18:38:42Z adrian $
// Description: Builds Makefiles based on information from FileManager.
// 
// Copyright (c) 2005, Sandia National Laboratories.
// Produced at SNL/Livermore, California
// Written by Benjamin Allan
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

import gov.llnl.babel.Context;
import gov.llnl.babel.backend.writers.ChangeWriter;
import gov.llnl.babel.backend.writers.LanguageWriterForMakefiles;
import gov.llnl.babel.symbols.SymbolID;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

/**
 * This class is used to generate a makefile at the top
 * of the generated source tree (for an invocation of babel)
 * that tells about the other makefiles/setup.py files in
 * the tree. It thus has to be maintained consistently
 * with Makefile and Setup generators.
 * 
 * Gets the database of generated files from the 
 * <code>FileManager</code>.  It then create makefiles
 * describing the package as generated.
 *
 * It might be nice to expand this into a fully automatic
 * gmake based build generator later.
 *
 * @see gov.llnl.babel.backend.FileManager
 * @see gov.llnl.babel.backend.MakefileGenerator
 * @see gov.llnl.babel.backend.python.SetupGenerator
 */
public class PackageMakeInfoGenerator implements FileListener, BuildGenerator,
                                                 ContextAware
{ 
  private Map d_dir2group = null;
  // lists of directories of various interests
  private Vector stubDirs = new Vector(5);
  private Vector javaDirs = new Vector(5);
  private Vector javaFiles = new Vector(100);
  private Vector implDirs = new Vector(5);
  private Vector iorDirs = new Vector(5);
  private Vector pySetupDirs = new Vector(5);
  private boolean pythonSeen = false;
  private boolean d_anythingSeen = false;
  private String topdirname;
  private Context d_context;

  private boolean baaDebug = false;

  public PackageMakeInfoGenerator() { 
    d_dir2group = new HashMap();
  }

  /**
   * This method is called for each file created by the 
   * {@link gov.llnl.babel.backend.FileManager}.
   */
  public void newFile(SymbolID id,
                      int      type,
                      String   role,
                      String   dir,
                      String   name)
  {
    d_anythingSeen = true;
    if( baaDebug ) {
      System.err.println("Make.newFile(id, " + type + ", " +
        role + ", " +dir + ", " +name + ")" );
    }
    Map role2file = (Map)d_dir2group.get(dir);
    if (null == role2file) {
      role2file = new TreeMap();
      d_dir2group.put(dir, role2file);
    }
    Set files = (Set)role2file.get(role);
    if (null == files) {
      files = new TreeSet();
      role2file.put(role, files);
    }
    files.add(name);
    collectJavaSrc(id,type,role,dir,name);
  }

  /** collect full path names of java files. */
  void collectJavaSrc(SymbolID id,
                      int      type,
                      String   role,
                      String   dir,
                      String   name) {

    if ( role.equals( "JAVAIMPL" ) || role.equals( "STUBJAVA" )) {
      // record javaFiles element
      String jf = dir+name;
      javaFiles.add(jf);
    }

  }

  /**
   * Create all PackageMakeInfos in all the directories registered
   * with the <code>FileManager</code>.  
   * 
   * @see gov.llnl.babel.backend.FileManager
   * @exception IOException the message contained is the concatenation of 
   *            all IOExceptions thrown by createMakefileInDirectory
   */
  public void createAll() 
    throws IOException 
  {
    // short-circuit file generation unless we are in CCA mode
    if ( d_context.getConfig().getCCAMode() == false ) { return ; }
    String errmsg       = "";
    String makefilename = defaultFilename();
    String packagemakefilename = defaultPackageFilename();

    //  Add code to collect stub and impl dir makefile names
    // as part of the iteration and put them out at end.
    // also add code to collect package names.
    pythonSeen = false;
    // forall directories
    for ( Iterator dir = d_dir2group.keySet().iterator(); dir.hasNext(); ) { 
      String dirname = (String) dir.next();
      try { 
        createMakefileInDirectory( makefilename, dirname );
      } catch ( IOException ex ) { 
        errmsg += "\nCreating file \"" + dirname + makefilename + "\"\n" + 
          ex.getMessage();
      }
    }
    if ( !errmsg.equals("") ) { 
      throw new IOException( errmsg );
    }                   
    // now put them out at the end.
    topdirname = d_context.getConfig().getOutputDirectory();
    /*
     * append a trailing File.separator, if none is found
     */
    if ( !topdirname.equals("") && 
         !topdirname.equals(File.separator) &&
         !topdirname.endsWith( File.separator ) ) { 
      topdirname += File.separator;
    }
    PrintWriter writer = null;
    if (d_anythingSeen) {
      try { 
        writer = 
          new PrintWriter( ChangeWriter.createWriter(packagemakefilename, topdirname));
        writePackageMakeInfo( writer, makefilename );
      } finally { 
        if ( writer != null ) { 
          writer.close();
        }
      }
    }
  }
  
  /**
   * Log that we created a single makefile in a specific directory.
   * This will do nothing if there are no files defined
   * in that directory according to the <code>FileManager</code> class.
   * @param makefilename set makefile name, if null or "", defaults to
   * &lt;make-prefix&gt; + "babel.make"
   *
   * @param dirname directory to look for 
   * @see gov.llnl.babel.backend.FileManager
   * @exception IOException if problems are encountered with the file system.
   */
  public void createMakefileInDirectory( String makefilename, 
                                         String dirname ) 
    throws IOException
  { 
    if ( makefilename == null || makefilename.equals("") ) { 
      makefilename = defaultFilename();
    }
    if ( dirname == null ) { 
      dirname = "";
    }

    /*
     * append a trailing File.separator, if none is found
     */
    if ( !dirname.equals("") && 
         !dirname.equals(File.separator) &&
         !dirname.endsWith( File.separator ) ) { 
      dirname += File.separator;
    }

    /*
     * get the groups associated with this directory
     */
    Map role2files = (Map) d_dir2group.get( dirname );
    if ( role2files == null ) { 
      return;
    }
    
    /*
     * now generate the makefile
     */
    try { 
      recordMakefile( role2files, dirname );
    } finally { 
    }
  }

  /**
   * Generate the actual make file.
   * This method can be overridden for tools other than make
   * provided that whatever files are generated exist in 
   * the same directory as the sourcecode.
   */
  protected void recordMakefile(  Map role2files, String dirname ) {     
    /*
     * Iterate over each group. 
     */
    boolean isStubDir = false;
    boolean isImplDir = false;
    boolean isIorDir = false;
    boolean isPySetupDir = false;
    boolean isJavaDir = false;

    // loop on all the groups in the babel.make and
    // add dir to lists according to groupnames found.
    for( Iterator g = role2files.entrySet().iterator(); g.hasNext(); ) { 
      Map.Entry entry = ( Map.Entry ) g.next();
      String groupname = ( String ) entry.getKey();

      // need to check groupname here and append to which vector
	/* current SRC group types as of rev 4543 are:
	impls
		"IMPLMODULESRCS"
		"IMPLSRCS"
	iors
		"IORSRCS"
	stubs
		"RSTUBSRCS"
		"STUBMODULESRCS"
		"STUBSRCS"
		"STUBJAVA"
	stuff we ignore and probably shouldn't.
		"TYPEMODULESRCS"
		"SKELSRCS"
	python module things
		"LAUNCHSRCS"
		"PYMOD_SRCS"
	*/
      if ( groupname.startsWith( "PY") || groupname.equals("LAUNCHSRCS") ) {
        pythonSeen = true;
      }
      // if any accordingly.
      if ( 
           groupname.equals( "IMPLMODULESRCS" ) || 
           groupname.equals( "IMPLSRCS" ) 
         ) {
        isImplDir = true;
        continue;
      }
      if ( 
           groupname.equals( "RSTUBSRCS" ) || 
           groupname.equals( "STUBSRCS" ) || 
           groupname.equals( "STUBMODULESRCS" ) 
         ) {
        isStubDir = true;
        continue;
      }
      if ( 
           groupname.equals( "IORSRCS" )
         ) {
        isIorDir = true;
        continue;
      }
      if ( 
           groupname.equals( "JAVAIMPL" ) || 
           groupname.equals( "STUBJAVA" )
         ) {
        isJavaDir = true;
        continue;
      }
    }
    
    if ( isJavaDir ) {
      javaDirs.add(dirname);
    }
    if ( isStubDir ) {
      stubDirs.add(dirname);
    }
    if ( isPySetupDir ) {
      pySetupDirs.add(dirname);
    }
    if ( isIorDir ) {
      iorDirs.add(dirname);
    }
    if ( isImplDir ) {
      implDirs.add(dirname);
    }
  }

  /**
   * Generate the package make file.
   * This method can be overridden for tools other than make
   * provided that whatever files are generated exist in 
   * the same directory as the sourcecode.
   */
  protected void writePackageMakeInfo( PrintWriter pw, String makefilename) { 
    LanguageWriterForMakefiles writer = 
      new LanguageWriterForMakefiles( pw, d_context );
    final String makePrefix = d_context.getConfig().getMakePrefix();
    final String pySetup = d_context.getConfig().getMakePrefix() +
      "setup.py";
    

    writer.print(makePrefix);
    writer.println("BABEL_MAKEFILE_NAME = " + makefilename);

    if ( stubDirs.size() > 0 ) {
      writer.print(makePrefix);
      writer.print("STUB_SRC_DIRS =");
      for (int i = 0; i < stubDirs.size(); i++) {
        String s = (String)stubDirs.get(i);
        writer.print(" " + dotify(s) );
      }
      writer.println();
    }

    if ( implDirs.size() > 0 ) {
      writer.print(makePrefix);
      writer.print("IMPL_SRC_DIRS =");
      for (int i = 0; i < implDirs.size(); i++) {
        String s = (String)implDirs.get(i);
        writer.print(" " + dotify(s) );
      }
      writer.println();
    }

    if ( iorDirs.size() > 0 ) {
      writer.print(makePrefix);
      writer.print("IOR_SRC_DIRS =");
      for (int i = 0; i < iorDirs.size(); i++) {
        String s = (String)iorDirs.get(i);
        writer.print(" " + dotify(s) );
      }
      writer.println();
    }

    if ( javaDirs.size() > 0 ) {
      writer.print(makePrefix);
      writer.print("JAVA_SRC_DIRS =");
      for (int i = 0; i < javaDirs.size(); i++) {
        String s = (String)javaDirs.get(i);
        writer.print(" " + dotify(s) );
      }
      writer.println();
    }


    if ( javaFiles.size() > 0 ) {
      writer.print(makePrefix);
      writer.print("JAVA_SRC_FILES =");
      for (int i = 0; i < javaFiles.size(); i++) {
        String s = (String)javaFiles.get(i);
        writer.print(" " + dotify(s) );
      }
      writer.println();
    }


    if (pythonSeen) {
      writer.print(makePrefix);
      writer.println("PYTHON_SETUP_NAME = " + pySetup);

      writer.print(makePrefix);
      writer.print("PYSETUP_DIRS =");
      writer.print(" " + dotify(topdirname) );
      writer.println();
    }

  }

  private String dotify(String s) {
    if (s == null || s.equals("") ) {
      return ".";
    }
    return s;
  }
  /**
   * Return the default file name.   This method can be
   * overridden in derived classes if a different default
   * name is preferred.
   * 
   * @return value of the make file name
   */
  protected String defaultFilename() { 
    return d_context.getConfig().getMakefileName();
  }
  
  /**
   * Return the default package file name.   This method can be
   * overridden in derived classes if a different default
   * name is preferred.
   * 
   * @return value of the make file name
   */
  protected String defaultPackageFilename() { 
    return defaultFilename() + ".package";
  }
  
  public Set getLanguages()
  {
    Set result = new TreeSet();
    result.add("c");
    result.add("c++");
    result.add("uc++");
    result.add("cxx");
    result.add("ucxx");
    result.add("f77");
    result.add("f77_31");
    result.add("f90");
    result.add("f03");
    result.add("java");
    result.add("matlab");
    result.add("python");
    result.add("ior");
    result.add("rmi");
    // result.add("lite"); needs to be by properties too
    return result;
  }

  public void setContext(Context context) {
    d_context = context;
  }
}
