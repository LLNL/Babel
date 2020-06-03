//
// File:        MakefileGenerator.java
// Package:     gov.llnl.babel.backend
// Revision:    @(#) $Id: MakefileGenerator.java 7188 2011-09-27 18:38:42Z adrian $
// Description: Builds Makefiles based on information from FileManager.
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

package gov.llnl.babel.backend;

import gov.llnl.babel.Context;
import gov.llnl.babel.ResourceLoader;
import gov.llnl.babel.backend.writers.ChangeWriter;
import gov.llnl.babel.backend.writers.LanguageWriterForMakefiles;
import gov.llnl.babel.backend.fortran.Fortran;
import gov.llnl.babel.symbols.SymbolID;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.List;
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
public class MakefileGenerator implements FileListener, BuildGenerator,
                                          ContextAware
{ 
  private Context d_context;
  private Set d_excludedGroups = null;
  private Map d_dir2group = null;

  public MakefileGenerator() { 
    d_excludedGroups = new TreeSet();
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
   * Add a file group to be excluded from generation.
   * By default no group is excluded when the <code>MakefileGenerator</code>
   * is constructed.
   *
   * @param groupname string name of a file group
   */
  public void excludeGroup( String groupname ) { 
    if ( groupname == null || groupname.equals("") ) { 
      return;
    } else { 
      d_excludedGroups.add(groupname );
    }
  }

  /**
   * Remove a group name from those excluded from generation;
   *
   * @param groupname string name of a file group
   */
  public void readmitExcludedGroup( String groupname ) { 
    if ( groupname == null || groupname.equals("") ) { 
      return;
    } else if ( d_excludedGroups.contains( groupname ) ) { 
      d_excludedGroups.remove( groupname );
    }
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
    String errmsg       = "";
    String makefilename = defaultFilename();

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
  }

  private String generateSIDLFileList()
  {
    StringBuffer buf = new StringBuffer();
    Iterator i = d_context.getSIDLFiles().iterator();
    while (i.hasNext()) {
      buf.append(i.next().toString());
      buf.append(' ');
    }
    return buf.toString().trim();
  }

  private String finalizeMakefile(String contents)
  {
    final String sidlFileList = generateSIDLFileList();
    if ((contents != null) && (contents.length() > 0)) {
      final String sidlmarker="@SIDLFILE@";
      int index = -1;
      while ((index = contents.indexOf(sidlmarker)) >= 0) {
        contents = contents.substring(0,index) + sidlFileList +
          contents.substring(index + sidlmarker.length());
      }
    }
    else {
      contents = null;
    }
    return contents;
  }
  
  /**
   * Create a single makefile in a specific directory.
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
    PrintWriter writer = null;
    try { 
      writer = 
        new PrintWriter( ChangeWriter.createWriter(makefilename, dirname));
      writeMakefile( writer, role2files );

      //For Fortran, we need to introduce dependencies among modules to
      //support parallel make
      final String mod_suffix = "MOD_SUFFIX";
      LanguageWriterForMakefiles lw =
        new LanguageWriterForMakefiles(writer, d_context);
      if(Fortran.isFortran(d_context)) {
        HashMap dir_map = d_context.getDependencies().getDirectoryData(dirname);
        if(dir_map != null) {
          //These are the "roles" we're interested in
          String[] modules = {"STUBMODULESRCS",
                              "TYPEMODULESRCS",
                              "IMPLSRCS",
                              "BASICMODULESRC",
                              "ARRAYMODULESRCS",
                              "FSKELSRCS"};
          Set all_files = new TreeSet();
          for(int i=0; i < modules.length; ++i)
            if(role2files.containsKey(modules[i]))
              all_files.addAll((Set)role2files.get(modules[i]));

          if(all_files.size() > 0) {
            lw.println();
            for(Iterator F = all_files.iterator(); F.hasNext(); ) {
              String ext = "." + Fortran.getImplExtension(d_context);
              String file = (String)F.next();
              String module = new String(file);
              if(module.endsWith(ext))
                module = module.substring(0, module.length() - ext.length());

              // TODO: there are some false dependencies for module "sidl"; find out
              // why so we can drop this
              if(module.compareTo("sidl") == 0)
                continue;
              
              boolean first = true;
              List dep_list = (List)dir_map.get(file);
              if(dep_list != null) {
                Set seen = new TreeSet();
                for(Iterator D = dep_list.iterator(); D.hasNext(); ) {
                  String d = (String) D.next();
                  
                  // Don't emit self-dependencies
                  if(module.equals(d)) continue; 
                  
                  if(!seen.contains(d) &&
                     (all_files.contains(d) || all_files.contains(d + ext))) {
                    seen.add(d);
                    if(first) {
                      lw.print("_deps_" + module + " = ");
                      first = false;
                    }
                    lw.print(" " + d);
                  }
                }
              }
              if(!first) {
                lw.println();
                lw.printlnUnformatted(module + "$(" + mod_suffix + ") : $(addsuffix $(" +
                                      mod_suffix + "), $(_deps_" + module + "))");
                lw.println();
              }
            }
          }
        }
      }
      
    } finally { 
      if ( writer != null ) { 
        writer.close();
      }
    }
    
    if (d_context.getConfig().getGenMakefile() &&
        role2files.containsKey("IORHDRS")) {
      final String makesrc = 
        "gov/llnl/babel/backend/makefiles/" +
        d_context.getConfig().getTargetLanguage() + ".make";
      ResourceLoader rl = new ResourceLoader();
      InputStream in = null;
      byte[] buffer = new byte[1024];
      StringBuffer buf = new StringBuffer();
      int count;
      writer = null;
      try {
        in = rl.getResourceStream(makesrc);
        while ((count = in.read(buffer)) >= 0) {
          buf.append(new String(buffer,0,count));
        }
      }
      catch (IOException ioe) {
        // ignore this exception
      }
      finally {
        if (in != null) {
          in.close();
        }
      }
      String makecontents = finalizeMakefile(buf.toString());
      if (makecontents != null ) {
        try{
          writer = 
            new PrintWriter( ChangeWriter.createWriter("GNUmakefile", dirname));
          writer.write(makecontents);
        }
        finally {
          if (writer != null) {
            writer.close();
          }
        }
      }
    }
  }

  /**
   * Generate the actual make file.
   * This method can be overridden for tools other than make
   * provided that whatever files are generated exist in 
   * the same directory as the sourcecode.
   */
  protected void writeMakefile( PrintWriter pw, Map role2files ) { 
    LanguageWriterForMakefiles writer = 
      new LanguageWriterForMakefiles( pw, d_context );
    final String makePrefix = d_context.getConfig().getMakePrefix();
    
    /*
     * Iterate over each group.  Skip over any group in d_excludedGroups.
     */
    for(Iterator g = role2files.entrySet().iterator(); g.hasNext(); ) { 
      Map.Entry entry = ( Map.Entry ) g.next();
      String groupname = ( String ) entry.getKey();
      if ( d_excludedGroups.contains( groupname ) ) { 
        continue;
      }
      Set files = (Set) entry.getValue();

      writer.print(makePrefix);
      writer.print(groupname + " =");
      for( Iterator f = files.iterator(); f.hasNext(); ) { 
        String s = (String) f.next();
        writer.print(" " + s );
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
    result.add("matlab");
    result.add("rmi");
    result.add("python");
    // result.add("lite"); needs to be by properties too...
    return result;
  }

  public void setContext(Context context) {
    d_context = context;
  }
}
