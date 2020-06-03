//
// File:        ResourceLoader.java
// Package:     gov.llnl.babel
// Copyright:   (c) 2003 Lawrence Livermore National Security, LLC
// Revision:    @(#) $Revision: 6211 $
// Date:        $Date: 2007-10-29 21:09:40 -0700 (Mon, 29 Oct 2007) $
// Description: Resource loader that works with Kaffe or Sun's JDK
// 

package gov.llnl.babel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Provide a method to get an InputStream for a resource in the Babel
 * Jar file regardless of the JDK being used. Kaffe currently doesn't
 * returns null for getClass().getClassLoader().
 */
public class ResourceLoader {
  final private static String s_babelJar = "babel-" + Version.VERSION  + ".jar";

  final private static Map s_jarFiles = 
    Collections.synchronizedMap(new HashMap());
  
  public InputStream getResourceStream(String resource,
                                       String jarFile)
    throws java.io.IOException
  {
    InputStream in = null;
    ClassLoader loader = getClass().getClassLoader();
    if (loader != null) {
      in = loader.getResourceAsStream(resource);
    }
    if (in == null) {
      in =  searchClassPath(resource, jarFile);
    }
    return in;
  }

  public InputStream getResourceStream(String resource)
    throws java.io.IOException
  {
    return getResourceStream(resource, s_babelJar);
  }

  private InputStream searchClassPath(String name, String jarFileName)
    throws java.io.IOException
  {
    JarFile jarFile;
    jarFile = (JarFile)s_jarFiles.get(jarFileName);
    if (jarFile == null) {
      String classpath = System.getProperty("java.class.path", "");
      StringTokenizer tok = new StringTokenizer(classpath, 
                                                File.pathSeparator);
    
      while (tok.hasMoreTokens()) {
        String path = tok.nextToken();
        if (path.endsWith(jarFileName)) {
          File f = new File(path);
          if (f.isFile()) {
            try {
              JarFile jar = new JarFile(f);
              if (jar != null) {
                jarFile = jar;
                break;
              }
            }
            catch (java.io.IOException ioe) {
              // don't do anything skip to next jar file
            }
          }
        }
      }
      if (jarFile != null) {
        s_jarFiles.put(jarFileName, jarFile);
      }
    }
    if (jarFile != null) {
      JarEntry entry = jarFile.getJarEntry(name);
      if (entry != null) {
        InputStream is = jarFile.getInputStream(entry);
        if (is != null) {
          return is;   
        }
      }
    }
    throw new IOException("Cannot get a ClassLoader to load " + name);
  }
}
