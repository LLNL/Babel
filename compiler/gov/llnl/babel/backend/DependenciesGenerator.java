//
// File:        DependenciesGenerator.java
// Package:     gov.llnl.babel.backend
// Revision:    @(#) $Id: DependenciesGenerator.java 7188 2011-09-27 18:38:42Z adrian $
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
import java.util.List;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * This class is used to generate makefiles but may
 * be amenable to extension to other types of build files.
 * 
 * gets the database of generated files from the 
 * <code>FileManager</code>.  It then create makefiles
 *
 * @see gov.llnl.babel.backend.FileManager
 */
public class DependenciesGenerator implements FileListener, BuildGenerator,
                                              ContextAware
{ 
  private Context d_context = null;
  private Map d_dir2group = null;
  private boolean debug=false;

  public DependenciesGenerator() { 
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
  }

  /**
   * Create all Makefiles in all the directories registered
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
    String dependfilename = defaultDependencyFilename();

    // forall directories
    for ( Iterator dir = d_dir2group.keySet().iterator(); dir.hasNext(); ) { 
      String dirname = (String) dir.next();
      try { 
        createMakefileInDirectory( dependfilename, dirname );
      } catch ( IOException ex ) { 
        errmsg += "\nCreating file \"" + dirname + dependfilename + "\"\n" + 
          ex.getMessage();
      }
    }
    if ( !errmsg.equals("") ) { 
      throw new IOException( errmsg );
    }                   
  }
  
  /**
   * Create a single makefile in a specific directory.
   * This will do nothing if there are no files defined
   * in that directory according to the <code>FileManager</code> class.
   * @param dependfilename set makefile name, if null or "", defaults to
   * &lt;make-prefix&gt; + "babel.make"
   *
   * @param dirname directory to look for 
   * @see gov.llnl.babel.backend.FileManager
   * @exception IOException if problems are encountered with the file system.
   */
  public void createMakefileInDirectory( String dependfilename, 
                                         String dirname ) 
    throws IOException
  { 
    if ( dependfilename == null || dependfilename.equals("") ) { 
      dependfilename = defaultDependencyFilename();
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
    PrintWriter writer = null;
    try { 
      writer = 
        new PrintWriter( ChangeWriter.createWriter(dependfilename, dirname));
      HashMap fileData = d_context.getDependencies().getDirectoryData(dirname);
      if (fileData != null){
        writeMakefile( writer, role2files, fileData );
      }
    } finally { 
      if ( writer != null ) { 
        writer.close();
      }
    }
  }

  /**
   * Generate the actual make file.
   * This method can be overridden for tools other than make
   * provided that whatever files are generated exist in 
   * the same directory as the sourcecode.
   * @param fileData: keyed by filename(leaf) and valued by prerequisite filenames
   * in a List.
   */
  protected void writeMakefile( PrintWriter pw, Map role2files, HashMap fileData ) { 
    LanguageWriterForMakefiles writer = 
      new LanguageWriterForMakefiles( pw, d_context );
    final String makePrefix = d_context.getConfig().getMakePrefix();
    
    /*
     * Iterate over each group. 
     */
    for( Iterator g = role2files.entrySet().iterator(); g.hasNext(); ) { 
      Map.Entry entry = ( Map.Entry ) g.next();
      String groupname = ( String ) entry.getKey();
      Set files = (Set) entry.getValue();

      writer.printUnformatted("# "+ makePrefix);
      writer.printlnUnformatted(groupname + " dependencies.");
      for( Iterator f = files.iterator(); f.hasNext(); ) { 
        String targetName = (String) f.next();
        if (debug){
            System.out.println("Target Name = " + targetName);
            System.out.println("Group Name = " + groupname);
        }
        
        List depList = (List)fileData.get(targetName);
        if (depList != null && depList.size() > 0) {
          Iterator k = depList.iterator();
          while(k.hasNext()) {
            String prereq = (String)k.next();
            // assume the list format one per line rather than longlined.
            writer.println(targetName + ": " + prereq);
          }
          writer.println("");
        }
      }
      writer.println();
    }
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
   * Return the default file name.   This method can be
   * overridden in derived classes if a different default
   * name is preferred.
   * 
   * @return value of the make file name
   */
  protected String defaultDependencyFilename() { 
    return defaultFilename() + ".depends";
  }
  protected String defaultPackageDependencyFilename() { 
    return defaultFilename() + ".package.depends";
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
    result.add("ior");
    result.add("rmi");
    result.add("python");
    result.add("xml");
    return result;
  }

  public void setContext(Context context) {
    d_context = context;
  }
}
