//
// File:        Context.java
// Package:     gov.llnl.babel
// Copyright:   (c) 2007 Lawrence Livermore National Security, LLC
// Revision:    @(#) $Revision: 7179 $
// Date:        $Date: 2011-09-26 10:58:12 -0700 (Mon, 26 Sep 2011) $
// Description: Holds objects that are the context of the run
// 

package gov.llnl.babel;

import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.CodeGenerationFactory;
import gov.llnl.babel.backend.Dependencies;
import gov.llnl.babel.backend.FileListener;
import gov.llnl.babel.backend.FileManager;
import gov.llnl.babel.repository.RepositoryException;
import gov.llnl.babel.repository.RepositoryFactory;
import gov.llnl.babel.symbols.SymbolTable;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class Context {
  private SymbolTable d_symtab = null;
  private FileManager d_fileman = null;
  private Dependencies d_depends = null;
  private RepositoryFactory d_repo = null;
  private BabelConfiguration d_config = null;
  private CodeGenerationFactory d_factory = null;
  private File d_default_repo = null;
  private LinkedList d_sidlfiles = null;
  
  public Context() {
    d_symtab = new SymbolTable();
    d_repo = new RepositoryFactory(this);
    d_config = new BabelConfiguration();
    d_factory = new CodeGenerationFactory();
    d_sidlfiles = new LinkedList();
    // leave d_fileman and d_depends null until generating code
  }

  public SymbolTable getSymbolTable() { return d_symtab; }

  public FileManager getFileManager() { return d_fileman; }

  public Dependencies getDependencies() { return d_depends; }

  public RepositoryFactory getRepoFactory() { return d_repo; }

  public BabelConfiguration getConfig() { return d_config; }

  public CodeGenerationFactory getFactory() { return d_factory; }

  public List getSIDLFiles() { return d_sidlfiles; }

  private void initDefaultRepo() 
  {
    if (d_default_repo != null) {
      String path = "";
      try {
        path = d_default_repo.getCanonicalPath();
        d_symtab.addSymbolResolver(d_repo.createRepository(path));
        d_config.addToRepositoryPath(path);
      }
      catch (java.io.IOException ioe) {
        System.err.println("Babel: Error: Cannot get path for default repository: \"" + d_default_repo.getPath() + "\".");
      }
      catch (RepositoryException re) {
        System.err.println("Babel: Warning: Unable to read default repository: " +
                           path);
      }
    }
  }

  public void setDefaultRepository(File defaultRepo) {
    d_default_repo = defaultRepo;
    initDefaultRepo();
  }

  public void removeDefaultRepository() {
    if (d_default_repo != null) {
      String path = "";
      try {
        path = d_default_repo.getCanonicalPath();
        d_config.removeFromRepositoryPath(path);
        d_symtab.removeSymbolResolver(d_repo.createRepository(path));
      }
      catch (java.io.IOException ioe) {
        System.err.println("Babel: Error: Cannot get path for default repository: \"" + d_default_repo.getPath() + "\".");
      }
      catch (RepositoryException re) {
        System.err.println("Babel: Warning: Unable to read default repository: " +
                           path);
      }
    }
  }

  public void prepareFilesAndDependencies() 
    throws CodeGenerationException
  {
    d_fileman = new FileManager(this);
    d_depends = new Dependencies(this);
    d_fileman.addListener(d_depends);
    d_fileman.setFileGenerationRootDirectory(d_config.getOutputDirectory());
    d_fileman.setJavaStylePackageGeneration(d_config.makePackageSubdirs());
    d_fileman.setGlueSubdirGeneration(d_config.makeGlueSubdirs());
    d_fileman.setVPathDirectory(d_config.getVPathDirectory());
  }

  public void deleteFilesAndDependencies() {
    if ((d_depends != null) && (d_fileman != null)) {
      d_fileman.removeListener((FileListener)d_depends);
    }
    d_fileman = null;
    d_depends = null;

  }

  public void reset()
  {
    deleteFilesAndDependencies(); // just in case
    d_symtab = new SymbolTable();
    d_repo = new RepositoryFactory(this);
    d_config = new BabelConfiguration();
    d_factory = new CodeGenerationFactory();
    d_sidlfiles = new LinkedList();
    initDefaultRepo();
  }
}
