//
// File:        Generator.java
// Package:     gov.llnl.babel
// Copyright:   (c) 2007 Lawrence Livermore National Security, LLC
// Revision:    @(#) $Revision: 7421 $
// Date:        $Date: 2011-12-15 17:06:06 -0800 (Thu, 15 Dec 2011) $
// Description: Manager parsing and generation of files
// 

package gov.llnl.babel;

import gov.llnl.babel.ast.SIDLFile;
import gov.llnl.babel.backend.BuildGenerator;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.CodeGenerator;
import gov.llnl.babel.backend.ContextAware;
import gov.llnl.babel.backend.FileListener;
import gov.llnl.babel.backend.FileManager;
import gov.llnl.babel.msg.ErrorFormatter;
import gov.llnl.babel.msg.MsgList;
import gov.llnl.babel.msg.UserMsg0;
import gov.llnl.babel.msg.UserMsg;
import gov.llnl.babel.parsers.sidl2.ParseTree2ASTVisitor;
import gov.llnl.babel.parsers.sidl2.ParseTreeNode;
import gov.llnl.babel.parsers.sidl2.SIDLDumpVisitor;
import gov.llnl.babel.parsers.sidl2.SIDLParser;
import gov.llnl.babel.parsers.sidl2.SIDLTouchUpVisitor;
import gov.llnl.babel.symbols.RegexMatch;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.SymbolRedefinitionException;
import gov.llnl.babel.url.URLUtilities;
import gov.llnl.babel.visitor.BuiltinAttributeCheck;
import gov.llnl.babel.visitor.CollisionChecker;
import gov.llnl.babel.visitor.DumpVisitor;
import gov.llnl.babel.visitor.HierarchySorter;
import gov.llnl.babel.visitor.NameChecker;
import gov.llnl.babel.visitor.SyntaxChecker;
import gov.llnl.babel.visitor.Unspecify;
import gov.llnl.babel.visitor.Visitor;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 * This is a simple object that manages the parsing and generation of
 * files.
 */
public class Generator {
  private File d_touchIfSuccessful = null;
  
  private Context d_context;
  
  private LinkedList d_astList = new LinkedList();

  private MsgList d_msgs = new MsgList();

  private HashMap d_collisionCheck = new HashMap();

  public Generator(Context context) {
    d_context = context;
  }


  /**
   * Parse a file or a URL. If it's not a file, try resolving it as a 
   * SIDL type from the XML respository.
   *
   * @param url can be a filename, a proper URL, or a SIDL type name.
   */
  public boolean parseOrResolve(String url) {
    try {
      File f = new File(url);
      if (f.exists() && f.isFile() && f.canRead()) {
        d_astList.add(parseURL(f.toURL().toString()));
        d_context.getSymbolTable().setInputFromSIDL();
        d_context.getSIDLFiles().add(url);
      } else if (f.exists() && f.isDirectory()) {
        resolveSymbolByString(url);
      } else if (url.indexOf(':') != -1) {
        // Try it as a URL if there is a colon in the name.
        d_astList.add(parseURL(URLUtilities.expandURL(url)));
        d_context.getSymbolTable().setInputFromSIDL();
      }
      else {
        resolveSymbolByString(url);
      }
    }
    catch (Exception ex) {
      d_msgs.addMsg(convertExceptionToMsg(ex));
    }
    return !d_msgs.fatal_message();
  }

  /**
   * Parse a file but don't include its contents in the list of 
   * symbols to be output.
   *
   * @param sidl  must be a filename or a proper URL (not a symbol name).
   */
  public boolean parseFileAsInclude(String sidl) {
    try {
      SIDLFile ast;
      File f = new File(sidl);
      if (f.exists() && f.isFile() && f.canRead()) {
        ast = parseURL(f.toURL().toString());
        if (ast != null) {
          ast.accept(new Unspecify(), null);
          d_astList.add(ast);
        }
        d_context.getSymbolTable().setInputFromSIDL();
      } else if (sidl.indexOf(':') != -1) {
        // Try it as a URL if there is a colon in the name.
        ast = parseURL(URLUtilities.expandURL(sidl));
        if (ast != null) {
          ast.accept(new Unspecify(), null);
          d_astList.add(ast);
        }
        d_context.getSymbolTable().setInputFromSIDL();
      }
      else {
        d_msgs.addMsg(new UserMsg0
                   (UserMsg.ERROR,
                    "Unable to read include file '" +
                    sidl + "'."));
      }
    }
    catch (Exception ex) {
      d_msgs.addMsg(convertExceptionToMsg(ex));
    }
    return !d_msgs.fatal_message();
  }

  /** 
   *  Ensure that we can resolve sidl.BaseInterface sidl.BaseClass,
   *  sidl.BaseException, and sidl.RuntimeException.  
   */
  private void ensureSIDLisResolveable() {
    if(d_context.getSymbolTable().resolveSymbol("sidl") == null) {
      d_msgs
        .addMsg(new UserMsg0(UserMsg.ERROR,
                             "Unable to resolve the sidl package."));
      return;
    }
    if(d_context.getSymbolTable().resolveSymbol(BabelConfiguration.getBaseClass()) == null) {
      d_msgs
        .addMsg(new UserMsg0(UserMsg.ERROR,
                             "Unable to resolve "+BabelConfiguration.getBaseClass()+
                             "."));
      return;
    }
    if(d_context.getSymbolTable().resolveSymbol(BabelConfiguration.getBaseInterface()) == null) {
      d_msgs
        .addMsg(new UserMsg0(UserMsg.ERROR,
                             "Unable to resolve "+BabelConfiguration.getBaseInterface()+
                             "."));
      return;
    }
    if(d_context.getSymbolTable().resolveSymbol(BabelConfiguration.getBaseExceptionInterface()) == null) {
      d_msgs
        .addMsg(new UserMsg0(UserMsg.ERROR,
                             "Unable to resolve "+BabelConfiguration.getBaseExceptionInterface()+
                             "."));
      return;
    }
    if(d_context.getSymbolTable().resolveSymbol(BabelConfiguration.getRuntimeException()) == null) {
      d_msgs
        .addMsg(new UserMsg0(UserMsg.ERROR,
                             "Unable to resolve "+BabelConfiguration.getRuntimeException()+
                             "."));
      return;
    }

  }

  public boolean resolveSymbols() {
    applyVisitor(new gov.llnl.babel.visitor.AssertionSource(), null);
    applyVisitor(new 
                 gov.llnl.babel.visitor.SymbolTablePrimer(d_msgs, d_context),
                 null);
    HierarchySorter hs = null;
    if (!d_msgs.fatal_message()) {
      hs = new HierarchySorter(d_msgs);
      ensureSIDLisResolveable();
    }
    applyVisitor(new
                 gov.llnl.babel.visitor.SymbolTableDecorator(d_msgs,
                                                             hs,
                                                             d_context), null);
    if (!d_msgs.fatal_message()) {
      hs.commitHierarchy(); // now commit the class/interface hierarchy
    }
    try {
      d_context.getSymbolTable().resolveAllReferences();
    }
    catch (gov.llnl.babel.symbols.SymbolNotFoundException snfe) {
      d_msgs.addMsg(convertExceptionToMsg(snfe));
    }
    catch (gov.llnl.babel.symbols.SymbolRedefinitionException sre) {
      d_msgs.addMsg(convertExceptionToMsg(sre));
    }
    applyVisitor(new
                 gov.llnl.babel.visitor.OverrideChecker(d_msgs), null);
    applyVisitor(new
                 gov.llnl.babel.visitor.InheritenceChecker(d_msgs, d_context),
                 null);
    applyVisitor(new gov.llnl.babel.visitor.StructChecker(d_msgs, d_astList),
                 null);
    System.out.flush();
    if (!d_msgs.fatal_message()) {
      try {
        d_context.getSymbolTable().resolveSymbol("sidl");
        d_context.getSymbolTable().resolveAllReferences();
        d_context.getSymbolTable().resolveAllParents();
        d_context.getSymbolTable().validateContracts();
      }
      catch (Exception ex) {
        d_msgs.addMsg(convertExceptionToMsg(ex));
      }
    }
    if (!d_msgs.fatal_message()) {
      d_context.getSymbolTable().freezeAll();
    }
    return !d_msgs.fatal_message();
  }

  public void applyVisitor(Visitor visitor, Object data)
  {
    Iterator i = d_astList.iterator();
    while(i.hasNext() && !d_msgs.fatal_message()) {
      SIDLFile f = (SIDLFile)i.next();
      f.accept(visitor, data);
    }
  }

  public void generateText(String lang)
  {
    Set symbols = getIncludedSymbols();
    Collection listeners = null;
    if (!d_msgs.fatal_message()) {
      createTouchFile();
      if (d_msgs.fatal_message()) return;
    }
    try {
      d_context.prepareFilesAndDependencies();
      Collection buildGens = getBuildGens(lang, false);
      listeners = initBuildGens(buildGens);
      generateCode(symbols, "text", lang);
      generateMakefiles(buildGens);
      if (symbols.isEmpty()) {
        d_msgs.addMsg
          (new UserMsg0(UserMsg.WARNING,
                        "Warning: Empty symbol table when generating " + lang + " text. May indicate improper command line."));
      }
    }
    catch (CodeGenerationException cge) {
      removeTouchFile();
      d_msgs.addMsg(convertExceptionToMsg(cge));
    }
    catch (RuntimeException re) {
      removeTouchFile();
      d_msgs.addMsg(convertExceptionToMsg(re));
      throw re;
    }
    finally {
      if ((listeners != null) && d_context.getFileManager() != null) {
        d_context.getFileManager().removeListeners(listeners);
      }
      d_context.deleteFilesAndDependencies();
    }
    if (d_msgs.fatal_message()) {
      removeTouchFile();
    }
  }

  public void generateClient(String lang) {
    Set symbols = getIncludedSymbols();
    Collection listeners = null;
    if (!d_msgs.fatal_message()) {
      createTouchFile();
      if (d_msgs.fatal_message()) return;
    }
    try {
      d_context.prepareFilesAndDependencies();
      Collection buildGens = getBuildGens(lang, false);
      listeners = initBuildGens(buildGens);
      if (!d_context.getConfig().getSuppressStub()) {
        generateCode(symbols, "stub", lang);
      }
      if (!d_context.getConfig().getSuppressIOR() || "ior".equals(lang)) {
        generateCode(symbols, "stub", "ior");
      }
      generateMakefiles(buildGens);
      if (symbols.isEmpty()) {
        d_msgs.addMsg
          (new UserMsg0(UserMsg.WARNING,
                        "Warning: Empty symbol table when generating client-side " + lang + ". May indicate improper command line."));
      }
    }
    catch (CodeGenerationException cge) {
      removeTouchFile();
      d_msgs.addMsg(convertExceptionToMsg(cge));
    }
    catch (RuntimeException re) {
      removeTouchFile();
      d_msgs.addMsg(convertExceptionToMsg(re));
      throw re;
    }
    finally {
      if ((listeners != null) && d_context.getFileManager() != null) {
        d_context.getFileManager().removeListeners(listeners);
      }
      d_context.deleteFilesAndDependencies();
    }
    if (d_msgs.fatal_message()) {
      removeTouchFile();
    }
  }

  public void generateServer(String lang) {
    Set symbols = getIncludedSymbols();
    Collection listeners = null;
    if (!d_msgs.fatal_message()) {
      createTouchFile();
      if (d_msgs.fatal_message()) return;
    }
    try {
      d_context.prepareFilesAndDependencies();
      Collection buildGens = getBuildGens(lang, false);
      listeners = initBuildGens(buildGens);
      if (!d_context.getConfig().getSuppressStub()) {
        generateCode(symbols, "stub", lang);
      }
      if (!d_context.getConfig().getSuppressIOR() || "ior".equals(lang)) {
        generateCode(symbols, "stub", "ior");
        generateCode(symbols, "skel", "ior");
      }
      generateCode(symbols, "skel", lang);
      generateMakefiles(buildGens);
      if (symbols.isEmpty()) {
        d_msgs.addMsg
          (new UserMsg0(UserMsg.WARNING,
                        "Warning: Empty symbol table when generating server-side " + lang + ". May indicate improper command line."));
      }
    }
    catch (CodeGenerationException cge) {
      removeTouchFile();
      d_msgs.addMsg(convertExceptionToMsg(cge));
    }
    catch (RuntimeException re) {
      removeTouchFile();
      d_msgs.addMsg(convertExceptionToMsg(re));
      throw re;
    }
    finally {
      if ((listeners != null) && d_context.getFileManager() != null) {
        d_context.getFileManager().removeListeners(listeners);
      }
      d_context.deleteFilesAndDependencies();
    }
    if (d_msgs.fatal_message()) {
      removeTouchFile();
    }
  }

  public boolean hasErrorOccurred() {
    return d_msgs.fatal_message();
  }

  private void generateCode(Set symbols, String type, String language) {
    // TLD-To check contents of symbols...printSymbolNames(symbols);
    try {
      CodeGenerator gen = getCodeGen(language, type);
      if (gen != null) {
        if (gen.getUserSymbolsOnly()) {
          symbols = filterSymbols(symbols);
        }
        gen.generateCode(symbols);
      }
      else {
        d_msgs.addMsg(new 
                   UserMsg0(UserMsg.WARNING,
                            "Warning: Unable to generate the " + type
                            + " sources in the " + language + " language."));
      }
    }
    catch(CodeGenerationException ex) {
      d_msgs.addMsg(convertExceptionToMsg(ex));
    }
  }

  private Set getIncludedSymbols() 
  {
    Set symbols = d_context.getSymbolTable().getSymbolNames();
    if (d_context.getConfig().excludeExternal()) {
      symbols = excludeExternalSymbols(symbols);
    }
    symbols = excludeSymbols(symbols);
    return symbols;
  }

  public static boolean notExcluded(SymbolID id, Context context) {
    if (context.getConfig().getSkipRMI() && 
        id.getFullName().startsWith("sidl.rmi")) {
      return false;
    }
    Iterator i = context.getConfig().getExcludedList()
      .iterator();
    while (i.hasNext()) {
      if (((RegexMatch) i.next()).match(id))
        return false;
    }
    return true;
  }

  private Set excludeSymbols(Collection symbols) {
    Set result = new HashSet(symbols.size());
    Iterator i = symbols.iterator();
    while (i.hasNext()) {
      SymbolID id = (SymbolID) i.next();
      if (notExcluded(id, d_context)) {
        result.add(id);
      }
    }
    return result;
  }

  public static boolean isIncluded(SymbolID id, Context context) {
    final String fullname = id.getFullName();
    Iterator incl = context.getConfig().getIncludedList()
      .iterator();
    while (incl.hasNext()) {
      if (fullname.startsWith((String) incl.next()))
        return true;
    }
    return false;
  }

  private Set excludeExternalSymbols(Collection symbols) {
    Set result = new HashSet(symbols.size());
    if (d_context.getConfig().excludeExternal()) {
      Iterator i = symbols.iterator();
      while (i.hasNext()) {
        SymbolID id = (SymbolID) i.next();
        if (d_context.getSymbolTable().lookupSymbol(id).getUserSpecified()) {
          result.add(id);
        }
        else {
          /*
           * Check if current symbol is contained within the scope of a
           * user-specified (on the command line) symbol.
           */
          if (isIncluded(id, d_context)) {
            d_context.getSymbolTable().lookupSymbol(id).setUserSpecified(true);
            result.add(id);
          }
        }
      }
    }
    else {
      result.addAll(symbols);
    }
    return result;
  }

  private static boolean filterSymbol(SymbolID id) {
    return !BabelConfiguration.isSIDLBaseClass(id);
  }

  /**
   * Depending on the configuration settings, return either all symbols or just
   * the user symbols, those symbols outside the SIDL namespace.
   */
  private  Set filterSymbols(Set symbols) {
    Set result;
    if (d_context.getConfig().generateStdlib()) {
      result = symbols;
    }
    else {
      result = new HashSet(symbols.size());
      Iterator i = symbols.iterator();
      while (i.hasNext()) {
        SymbolID id = (SymbolID) i.next();
        if (filterSymbol(id))
          result.add(id);
      }
    }
    return result;
  }

  /**
   * Generate the appropriate makefile fragments.
   */
  private void generateMakefiles(Collection buildGens) {
    /*
     * Now generate the fragments.
     */
    Iterator i = buildGens.iterator();
    while (i.hasNext()) {
      BuildGenerator mg = (BuildGenerator) i.next();
      try {
        mg.createAll();
      }
      catch(IOException ex) {
        System.err.println("Babel: Warning: Unable to generate makefiles.");
        System.err.println(ex.getMessage());
        ex.printStackTrace(System.err);

      }
    }
  }

  /**
   * Try to parse string as a symbol. Return null if it fails.
   */
  private void resolveSymbolByString(String symbol_string)
  {
    Symbol symbol = null;
    try {
      int split = symbol_string.indexOf("-v");
      boolean hasVersion = ((split > 0) && (split < symbol_string.length()));
      String symbol_name = hasVersion ? symbol_string.substring(0, split)
        : symbol_string;
      String version_name = hasVersion ? symbol_string.substring(split + 2)
        : null;

      if (version_name == null) {
        symbol = d_context.getSymbolTable().resolveSymbol(symbol_name);
      }
      else {
        SymbolID id = new SymbolID(symbol_name, new gov.llnl.babel.symbols.
                                   Version(version_name));
        symbol = d_context.getSymbolTable().resolveSymbol(id);
      }

      if (symbol != null) {
        System.out.println("Babel: Resolved symbol \"" + symbol_string + 
                           "\".");
        symbol.setUserSpecified(true);
        
        if (symbol.isPackage()) {
          /*
           * Add patterns for all symbols specifed on the command line, so
           * that code will be generated for relevant subpackages and the
           * package contents (if the symbol is a package).
           */
          d_context.getConfig().addIncluded(symbol_string + ".");
        }
      }
      else {
        d_msgs.addMsg(new UserMsg0(UserMsg.ERROR,
                                   "Unable to parse file or resolve symbol: \"" +
                                   symbol_string + "\""));
      }
    }
    catch (NumberFormatException nfe) {
      d_msgs.addMsg
        (new UserMsg0(UserMsg.ERROR,
                      "Symbol name '" +
                      symbol_string + 
                      "' has a version specification that cannot be parsed."));
    }
    catch (SymbolRedefinitionException sre) {
      d_msgs.addMsg
        (new UserMsg0(UserMsg.ERROR,
                      "Loading symbol '" +
                      symbol_string +
                      "' from the type respository produced a symbol conflict."));
    }
  }

  /**
   * Open and parse contents of the specified URL using the new parser
   */
  private SIDLFile parseURL(String url) 
    throws MalformedURLException, IOException
  { 
    SIDLFile AST= null;
    URL u = new URL(url);
    InputStream s = null;
    boolean verbose = d_context.getConfig().isVerbose();
    try {
      s = u.openStream();
      SIDLParser parser = new SIDLParser(s);
      ParseTreeNode parseTree = parser.Start();
      parseTree.name = url;
      parseTree.jjtAccept(new SIDLTouchUpVisitor(System.out), null);
      
      if ( verbose ) {
        parseTree.jjtAccept(new SIDLDumpVisitor(System.out), null);
      }
      AST =
        (SIDLFile) parseTree.jjtAccept(new ParseTree2ASTVisitor(d_msgs), null);
      if ( verbose ) { 
        AST.accept(new DumpVisitor(System.out), null); 
      }
      AST.accept(new gov.llnl.babel.visitor.AddDefaultsVisitor(d_msgs),null);
        
      
      AST.accept(new SyntaxChecker(d_msgs), null);
      AST.accept(new CollisionChecker(d_msgs, d_collisionCheck), null);
      AST.accept(new BuiltinAttributeCheck(d_msgs), null);
      AST.accept(new NameChecker(d_msgs), null);
      //AST.accept(new AttributeChecker(d_msgs), null);
      System.out.flush();
      if (d_msgs.fatal_message()) {
        AST = null;
      }
      else if ( verbose ) {
        AST.accept(new DumpVisitor(System.out), null);
      }
    } catch(gov.llnl.babel.parsers.sidl2.ParseException e) {
      ErrorFormatter.formatParseError(e, url);
    }
    finally {
      if (s != null)
        s.close();
    }
    return AST;
  }

  public void clear()
  {
    d_msgs = new MsgList();
    d_astList = new LinkedList();
    d_collisionCheck = new HashMap();
    d_touchIfSuccessful = null;
  }

  public void printErrors(PrintStream w) {
    d_msgs.print(w);
  }

  public void setTouchFile(File f) {
    d_touchIfSuccessful = f;
  }

  private void createTouchFile() {
    if (d_touchIfSuccessful != null) {
      boolean success;
      try {
        success = (d_touchIfSuccessful.createNewFile() ||
                   (d_touchIfSuccessful.delete() &&
                    d_touchIfSuccessful.createNewFile()));
      }
      catch (Exception ioe) {
        File parent;
        try{ 
          success = (((parent = d_touchIfSuccessful.getParentFile()) != null) &&
                     parent.mkdirs() &&
                     d_touchIfSuccessful.createNewFile());
        }
        catch (Exception e2) {
          d_msgs.addMsg(convertExceptionToMsg(e2));
          success = false;
        }
      }
      if (!success) {
        d_msgs.addMsg(new UserMsg0(UserMsg.ERROR,
                                "Unable to create touch file '" +
                                d_touchIfSuccessful.getPath() + "'."));
        d_touchIfSuccessful = null;
      }
    }
  }

  private void removeTouchFile() {
    if (d_touchIfSuccessful != null) {
      if (!d_touchIfSuccessful.delete()) {
        d_msgs.addMsg(new UserMsg0(UserMsg.ERROR,
                                "Unable to delete touch file '" +
                                d_touchIfSuccessful.getPath() +
                                "' for failed run."));
      }
    }
  }

  private Collection initBuildGens(Collection buildGens) {
    Iterator i = buildGens.iterator();
    LinkedList listeners = new LinkedList();
    while (i.hasNext()) {
      BuildGenerator mg = (BuildGenerator)i.next();
      if (mg instanceof FileListener) {
        listeners.add((FileListener)mg);
        d_context.getFileManager().addListener((FileListener)mg);
      }
      if (mg instanceof ContextAware) {
        ((ContextAware)mg).setContext(d_context);
      }
    }
    return listeners;
  }

  private Collection getBuildGens(String myLang, boolean warning) {
    Collection buildGens = d_context.getFactory()
      .getBuildGenerators(myLang);
    if (buildGens.size() == 0 && warning) {
      d_msgs.addMsg(new UserMsg0(UserMsg.WARNING,
                              "No build generator returned for the " +
                              myLang + " language."));
    }
    return buildGens;
  }
  
  private UserMsg convertExceptionToMsg(Throwable t)
  {
    StackTraceElement [] elements = t.getStackTrace();
    StringBuffer buf = new StringBuffer();
    buf.append("Babel: ");
    buf.append(t.getMessage());
    buf.append('\n');
    for(int i = 0; i < elements.length ; ++i) {
      buf.append(elements[i].toString());
      buf.append('\n');
    }
    return new UserMsg0(UserMsg.ERROR, buf.toString());
  }

  /**
   * Instantiate the appropriate code generator associated with the desired
   * language and mode.
   * 
   * @see CodeGenerator
   */
  private CodeGenerator getCodeGen(String myLang, String mode)
    throws CodeGenerationException 
  {
    CodeGenerator cg = d_context.getFactory().getCodeGenerator(myLang, mode);
    if (cg == null) {
      d_msgs.addMsg(new UserMsg0(UserMsg.ERROR,
                              "Warning: No code generator returned for the " +
                              myLang + "language and " + mode + " mode."));
    }
    else {
      cg.setName(myLang);
      cg.setContext(d_context);
    }
    return cg;
  }

  /**
   * Initialize the file generation manager with configuration-specific
   * information as needed.
   * 
   * @see BabelConfiguration
   * @see FileManager
   */
  private void setupFileManager() {
    FileManager fileman = d_context.getFileManager();
    try {
      fileman.setFileGenerationRootDirectory(d_context.getConfig().
                                             getOutputDirectory());
    } catch(CodeGenerationException ex) {
      System.err.
        println("Babel: Warning: Unable to set output directory. Will " +
                "use default.");
      ex.printStackTrace(System.err);
    }

    fileman.setJavaStylePackageGeneration(d_context.getConfig().
                                          makePackageSubdirs());
    fileman.setGlueSubdirGeneration(d_context.getConfig()
                                    .makeGlueSubdirs());

    try {
      fileman.setVPathDirectory(d_context.getConfig()
                                       .getVPathDirectory());
    } catch(CodeGenerationException ex) {
      System.err.println("Babel: Warning: Unable to set VPATH directory.");
      System.err.println(ex.getMessage());
      ex.printStackTrace(System.err);
    }
  }

}
