//
// File:        UserOptions.java
// Package:     gov.llnl.babel
// Revision:     @(#) $Id: UserOptions.java 7421 2011-12-16 01:06:06Z adrian $
// Description: singleton Map for configuring general, user-related options
// 
// Copyright (c) 2000-2007, Lawrence Livermore National Security, LLC
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

import gov.llnl.babel.Generator;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.CodeGenerationFactory;
import gov.llnl.babel.backend.CodeGenerator;
import gov.llnl.babel.cli.CommandLineDictionary;
import gov.llnl.babel.cli.CommandLineExtension;
import gov.llnl.babel.cli.CommandLineSwitch;
import gov.llnl.babel.cli.CorruptSymbolException;
import gov.llnl.babel.cli.InvalidArgumentException;
import gov.llnl.babel.cli.InvalidOptionException;
import gov.llnl.babel.cli.NameCollisionException;
import gov.llnl.babel.repository.RepositoryException;
import gov.llnl.babel.repository.RepositoryFactory;
import gov.llnl.babel.symbols.RegexMatch;
import gov.llnl.babel.symbols.RegexUnsupportedException;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.regex.PatternSyntaxException;

/**
 * Manages the
 * wide variety of settings available to a user.  The configuration 
 * options are read in through an array of strings (args).  Newly
 * specified arguments always replace the values of the previously 
 * specified ones with the exception of the repository path where
 * new arguments are appended to the existing list.
 *
 * For developers, to add a new internal Babel option. Create an inner
 * subclass in UserOptions with a name ending in <code>Switch</code>.
 * This class uses reflection to automatically add all inner classes
 * as command line switches.
 */

public class UserOptions implements CommandLineExtension
{ 
  private Context d_context;

  private int d_numRequired = 0;
  private boolean d_versionSet = false;

  private Generator d_gen = null;

  private static abstract class BasicSwitch implements CommandLineSwitch
  {
    private int d_shortForm;
    private String d_longName;
    private String d_helpText;
    public BasicSwitch(int shortForm, String longName, String helpText) {
      d_shortForm = shortForm;
      d_longName = longName;
      d_helpText = helpText;
    }
    
    final public String getLongName() { return d_longName; }
    final public int    getShortForm() { return d_shortForm; }
    final public void   setShortForm(int v) { d_shortForm = v; }
    final public String getHelpText() { return d_helpText; }
    public String getArgumentName() { return null; }
    public boolean hasRequiredArgument() { return false; }
    public boolean hasOptionalArgument() { return false; }
    public boolean isHidden() { return false; }
  }

  public class FastCallSwitch extends BasicSwitch {
    public FastCallSwitch() { 
      super('f', "fast-call",
            "Enable experimental inter-language optimizations."); }
    final public void processCommandSwitch(String optarg) {
      if(d_context.getConfig().generateHooks()) {
        System.err.println("Babel: Warning: -i/--generate-hooks overrides -f/--fast-call.");
      }
      else {
        System.err.println("Babel: Warning: -f/--fast-call is experimental.");
        d_context.getConfig().setFastCall(true);
      }
    }
  }
  
  public class ExcludeExternalSwitch extends BasicSwitch {
    public ExcludeExternalSwitch() { 
      super('E', "exclude-external",
            "Code is generated only for the symbol hierarchies rooted at the symbols specified on the command line."); }
    final public void processCommandSwitch(String optarg) {
      System.err.println("Babel: Note: -E/--exclude-external is now the default behavior.");
      d_context.getConfig().setExcludeExternal(true);
    }
    final public boolean isHidden() { return true; }
  }

  public class IncludeReferencedSwitch extends BasicSwitch {
    public IncludeReferencedSwitch() { 
      super(0, "include-referenced",
            "Code is generated for symbols referenced by those listed on the command line."); }
    final public void processCommandSwitch(String optarg) {
      d_context.getConfig().setExcludeExternal(false);
    }
  }

  public class GenerateHooksSwitch extends BasicSwitch {
    public GenerateHooksSwitch() { 
      super('i', "generate-hooks", "Generate pre-/post-method hooks"); 
    }
    final public void processCommandSwitch(String optarg) {
      if(d_context.getConfig().getFastCall()) {
        System.err.println("Babel: Warning: -i/--generate-hooks overrides -f/--fast-call.");
        d_context.getConfig().setFastCall(false);
      }
      d_context.getConfig().setGenerateHooks(true);
    }
  }

  public class ProvenanceSwitch extends BasicSwitch {
    public ProvenanceSwitch() {
      super(0,"strict-provenance", "Use to cleanly separate generated and developer code and avoid overwriting impl files.");
    }
    final public void processCommandSwitch(String optarg) {
      d_context.getConfig().setStrictProvenance(true);
    }
  }

  public class MultiSwitch extends BasicSwitch {
    public MultiSwitch() { 
      super(0, "multi", "Use to generate multiple targets in one run. This causes Babel to process arguments in a strict left to right approach.");
    }
    final public void processCommandSwitch(String optarg) {
    }
  }

  public class LanguageSubdirSwitch extends BasicSwitch {
    public LanguageSubdirSwitch() { 
      super('l', "language-subdirs",
            "Generate code in a language-specific subdirectory.");
    }
    final public void processCommandSwitch(String optarg) 
    {
      d_context.getConfig().setMakeLanguageSubdir(true);
    }
  }

  public class LanguageSubdirOffSwitch extends BasicSwitch {
    public LanguageSubdirOffSwitch() { 
      super(0, "language-subdirs-off",
            "Turn off --language-subdirs and restore default behavior.");
    }
    final public void processCommandSwitch(String optarg) 
    {
      d_context.getConfig().setMakeLanguageSubdir(false);
    }
  }

  public class GenerateMakefileSwitch extends BasicSwitch {
    public GenerateMakefileSwitch() {
      super(0, "makefile",
            "Generate a sample GNU Makefile");
    }
    final public void processCommandSwitch(String optarg)
    {
      if (d_context.getConfig().makeGlueSubdirs()) {
        System.err.println
          ("Babel: Warning: the --makefile option does not work correctly with the --hide-glue option.");
      }
      d_context.getConfig().setGenMakefile(true);
    }
  }

  public class GenerateSubdirSwitch extends BasicSwitch {
    public GenerateSubdirSwitch() { 
      super('g', "generate-subdirs",
            "Postpend Java-style package subdirs."); 
    }
    final public void processCommandSwitch(String optarg) {
      d_context.getConfig().setMakePackageSubdirs(true);
    }
  }

  public class GenerateSubdirOffSwitch extends BasicSwitch {
    public GenerateSubdirOffSwitch() { 
      super(0, "generate-subdirs-off",
            "Turn off --generate-subdirs and restore default behavior."); 
    }
    final public void processCommandSwitch(String optarg) 
    {
      d_context.getConfig().setMakePackageSubdirs(false);
    }
  }

  public class ParseCheckSwitch extends BasicSwitch {
    public ParseCheckSwitch() { 
      super('p', "parse-check",
            "Parse the sidl file but do not generate code.");
    }
    final public void processCommandSwitch(String optarg) 
      throws InvalidOptionException, CorruptSymbolException
    {
      d_context.getSymbolTable().setTargetIsXML(false);
      d_context.getConfig().setParseCheckOnly(true);
      if (d_gen == null) {
        if (d_numRequired++ > 0) {
          throw new InvalidOptionException("Only one of the following options or its equivalent is allowed: --help, --version, --client, --server, --text or --parse-check unless you use the --multi option.");
        }
      }
      else {
        if (!d_gen.resolveSymbols()) {
          d_gen.printErrors(System.err);
          throw new CorruptSymbolException();
        }
        d_gen.printErrors(System.err);
        d_gen.clear();
      }
    }
  }

  public class VerboseSwitch extends BasicSwitch {
    public VerboseSwitch() { 
      super(0, "verbose",
            "Turn on verbose debugging text");
    }
    final public void processCommandSwitch(String optarg) 
      throws InvalidOptionException
    {
      d_context.getConfig().setVerbose(true);
    }
  }
  
  public class HideGlueSwitch extends BasicSwitch {
    public HideGlueSwitch() { 
      super('u', "hide-glue",
            "Put \"glue\" (not modifiable by user) generate code in a glue/ subdirectory.");
    }
    final public void processCommandSwitch(String optarg) {
      if (d_context.getConfig().getGenMakefile()) {
        System.err.println
          ("Babel: Warning: the --hide-glue option makes the Makefile generated by the --makefile option incorrect.");
      }
      d_context.getConfig().setMakeGlueSubdirs(true);
    }
  }

  public class HideGlueOffSwitch extends BasicSwitch {
    public HideGlueOffSwitch() { 
      super(0, "hide-glue-off",
            "Turn off --hide-glue and restore default behavior.");
    }
    final public void processCommandSwitch(String optarg) {
      d_context.getConfig().setMakeGlueSubdirs(false);
    }
  }

  public class VersionSwitch extends BasicSwitch {
    public VersionSwitch() { 
      super('v', "version",
            "Display the version information and exit.");
    }
    final public void processCommandSwitch(String optarg) 
      throws InvalidOptionException
    {
      if (d_gen == null ) ++d_numRequired;
      d_versionSet = true;
      System.out.println( "Babel version " + Version.getFullVersion());
    }
  }

  public class CxxIORExceptionSwitch extends BasicSwitch {
    public CxxIORExceptionSwitch() { 
      super('x', "cxx-ior-exception",
            "Include IOR pointer checking in C++ stubs; throw a NullIORException if IOR is Null.");
    }
    final public void processCommandSwitch(String optarg) {
      d_context.getConfig().setCxxCheckNullIOR(true);
    }
  }

  public class GenerateSidlStdlibSwitch extends BasicSwitch {
    public GenerateSidlStdlibSwitch() { 
      super(0, "generate-sidl-stdlib",
            "Regenerate only the SIDL standard library.");
    }

    final public void processCommandSwitch(String optarg) 
      throws InvalidOptionException
    {
      d_context.getConfig().setGenerateStdlib(true);
    }
  }

  public class NoDefaultRepositorySwitch extends BasicSwitch {
    public NoDefaultRepositorySwitch() { 
      super('!', "no-default-repository",
            "Prohibit use of default repository to resolve symbols.");
    }
    final public void processCommandSwitch(String optarg) {
      d_context.removeDefaultRepository();
    }
  }

  public class SuppressTimestampSwitch extends BasicSwitch {
    public SuppressTimestampSwitch() { 
      super(0, "suppress-timestamp",
            "Suppress timestamps in generated files.");
    }
    final public void processCommandSwitch(String optarg) {
      System.err.println("Babel: Note: --suppress-timestamp is now the default behavior.");
      d_context.getConfig().setSuppressTimestamps(true);
    }
    final public boolean isHidden() { return true; }
  }

  public class TimestampSwitch extends BasicSwitch {
    public TimestampSwitch() { 
      super(0, "timestamp",
            "Generate timestamps in generated files.");
    }
    final public void processCommandSwitch(String optarg) {
      d_context.getConfig().setSuppressTimestamps(false);
    }
  }

  public class CommentLocalOnlySwitch extends BasicSwitch {
    public CommentLocalOnlySwitch() { 
      super(0, "comment-local-only",
            "Only comment locally defined methods in stub.");
    }
    final public void processCommandSwitch(String optarg) {
      d_context.getConfig().setCommentLocalOnly(true);
    }
  }

  public class ShortFileNamesSwitch extends BasicSwitch {
    public ShortFileNamesSwitch() { 
      super(0, "short-file-names",
            "Use in conjuction with -g to generate file names that don't include package names.");
    }
    final public void processCommandSwitch(String optarg) {
      d_context.getConfig().setShortFileNames(true);
    }
  }

  public class SuppressIORSwitch extends BasicSwitch {
    public SuppressIORSwitch() { 
      super(0, "suppress-ior",
            "Refrain from generating IOR source or header files.");
    }
    final public void processCommandSwitch(String optarg) {
      d_context.getConfig().setSuppressIOR(true);
    }
  }

  public class SuppressStubSwitch extends BasicSwitch {
    public SuppressStubSwitch() { 
      super(0, "suppress-stubs",
            "Refrain from generating stub source or header files.");
    }
    final public void processCommandSwitch(String optarg) {
      d_context.getConfig().setSuppressStub(true);
    }
  }

  public class RenameSplicerSwitch extends BasicSwitch {
    public RenameSplicerSwitch() { 
      super(0, "rename-splicers",
            "Automatically rename splicer blocks whose name have changed from previous Babel releases.");
    }
    final public void processCommandSwitch(String optarg) {
      d_context.getConfig().setRenameSplicers(true);
    }
  }

  public class CCAModeSwitch extends BasicSwitch {
    public CCAModeSwitch() { 
      super(0, "cca-mode",
            "Enables generation of CCA support files and default splicer blocks.");
    }
    final public void processCommandSwitch(String optarg) {
      d_context.getConfig().setCCAMode(true);
    }
  }

  public static abstract class RequiredArgSwitch extends BasicSwitch 
  {
    private String d_argName;
    public RequiredArgSwitch(int shortForm, String longName, String helpText,
                             String argName){
      super(shortForm, longName, helpText);
      d_argName = argName;
    }
    public boolean hasRequiredArgument() { return true; }
    public String getArgumentName() { return d_argName; }
  }

  public class SuppressContractsSwitch extends BasicSwitch
  {
    public SuppressContractsSwitch() {
      super(0, "suppress-contracts", 
            "Refrain from generating contract enforcement from SIDL specs.");
    }

    final public void processCommandSwitch(String optarg) {
      d_context.getConfig().setSuppressContracts(true);
    }
  }

  public class ClientSwitch extends RequiredArgSwitch
  {
    public ClientSwitch() {
      super('c', "client", 
            "Generate only client code in the specified language (C | C++ | F77 | F90 | F03 | Java | Python).", "lang");
    }
    
    /**
     * Check if the specified client-side language is supported.
     */
    private String validClientLanguage( String lang ) 
      throws InvalidArgumentException
    { 
      CodeGenerationFactory factory = d_context.getFactory();
      CodeGenerator cg = factory.getCodeGenerator(lang, "stub");
      d_context.getSymbolTable().setTargetIsXML(false);
      if (null != cg) {
        try {
          cg.setName(lang);
        }
        catch (CodeGenerationException cge) {
          throw new InvalidArgumentException(cge.getMessage());
        }
        return cg.getName();
      }
      return null;
    }
    
    final public void processCommandSwitch(String optarg) 
      throws InvalidArgumentException, InvalidOptionException,
             CorruptSymbolException
    {
      optarg = optarg.toLowerCase();
      optarg = validClientLanguage(optarg);
      if (null != optarg) {
        d_context.getConfig().setGenerateClient(true);
        d_context.getConfig().setTargetLanguage(optarg);
        if (d_gen == null) {
          if (d_numRequired++ > 0) {
            throw new InvalidOptionException("Only one of the following options or its equivalent is allowed: --help, --version, --client, --server, --text, or --parse-check unless you use the --multi option.");
          }
        }
        else {
          if (d_gen.resolveSymbols()) {
            d_gen.generateClient(optarg);
          }
          else {
            d_gen.printErrors(System.err);
            throw new CorruptSymbolException();
          }
          d_gen.printErrors(System.err);
          d_gen.clear();
        }
      }
      else {
        throw new InvalidArgumentException
          ("Unsupported language specified for client option.");
      }
    }
  }

  public class ExcludeSwitch extends RequiredArgSwitch
  {
    public ExcludeSwitch() {
      super('e', "exclude", 
            "Symbols matching the regular expression are excluded from code generation.", "regex");
    }

    final public void processCommandSwitch(String optarg) 
      throws InvalidArgumentException
    {
      try {
        d_context.getConfig().addExcluded(new RegexMatch(optarg));
      }
      catch(RegexUnsupportedException rue) {
        throw new InvalidArgumentException
          ("The --exclude feature requires a Java runtime environment with the 1.4 library or higher for regular expression support.");
      }
      catch(PatternSyntaxException PSE) { 
        throw new InvalidArgumentException
          ("The regular expression is invalid: " + PSE.getMessage());
      }
    }
  }

  public class MakePrefixSwitch extends RequiredArgSwitch
  {
    public MakePrefixSwitch() {
      super('m', "make-prefix",
            "<prefix> is prepended to the name of babel.make and the symbols defined internally to allow Babel to be run multiple times in a single directory.", "prefix");
    }

    final public void processCommandSwitch(String optarg) 
    {
      d_context.getConfig().setMakePrefix(optarg);
    }
  }

  public class OutputDirectorySwitch extends RequiredArgSwitch
  {
    public OutputDirectorySwitch() {
      super('o', "output-directory",
            "Set Babel output directory (\'.\' default).", "dir");
    }

    final public void processCommandSwitch(String optarg) 
    {
      d_context.getConfig().setOutputDirectory(optarg);
    }
  }

  public class RepositoryPathSwitch extends RequiredArgSwitch
  {
    public RepositoryPathSwitch() {
      super('R', "repository-path",
            "Set semicolon-separated URL list used to resolve symbols.", 
            "path");
    }

    final public void processCommandSwitch(String optarg) 
      throws InvalidArgumentException
    {
      String shortname = optarg;
      String apath = shortname;
      RepositoryFactory the_factory = d_context.getRepoFactory();
      boolean absolutePath = false;
      java.io.File[] roots = java.io.File.listRoots();

      //directory ends with sperator? 
      if(!shortname.endsWith(""+java.io.File.separatorChar)) 
        shortname = shortname + java.io.File.separatorChar;
      //Figure out if we have an absolute path by checking that the path
      //begins with a root.  ( '/' in Unix, "C:\" or "D:\" in Windows)
      for(int i = 0; i < roots.length; ++i) {
        if (shortname.startsWith(roots[i].toString())) {
          absolutePath=true;
          break;
        }
      }
      
      if (absolutePath) { //If it's an absolute path, good
        try {
          d_context.getConfig().addToRepositoryPath(shortname);
          d_context.getSymbolTable().
            addSymbolResolver(the_factory.createRepository(shortname));
        }
        catch (RepositoryException ex) {
         System.err.println
             ("Babel: Warning: skipping repository path \"" +
              apath + "\". Will use default.");
        }
      } 
      else { // if the path is realitive, make it absolute
        java.io.File file = new java.io.File(".");
        try {
          apath = file.getCanonicalPath() +
              java.io.File.separatorChar + shortname;
          d_context.getConfig().addToRepositoryPath(apath);
          d_context.getSymbolTable().
              addSymbolResolver(the_factory.createRepository(apath));
        } 
        catch (java.io.IOException e) {
         System.err.println
            ("Babel: Warning: Unable to get path to current directory! Any XML output may contain an incorrect path!");
         System.err.println
             ("Babel: Warning: skipping repository path \"" +
              apath + "\". Will use default.");
        }
        catch (RepositoryException re) {
         System.err.println
             ("Babel: Warning: skipping repository path \"" +
              apath + "\". Will use default.");
        }
      }
    }
  }

  public class DefaultRepositoryPathSwitch extends RequiredArgSwitch
  {
    public DefaultRepositoryPathSwitch() {
      super(0, "default-repository-path",
            "Set the default repository path",
            "path");
    }

    final public void processCommandSwitch(String optarg) 
      throws InvalidArgumentException
    {
      String shortname = optarg;
      String apath = shortname;
      //RepositoryFactory the_factory = d_context.getRepoFactory();
      boolean absolutePath = false;
      java.io.File[] roots = java.io.File.listRoots();

      //directory ends with sperator? 
      if(!shortname.endsWith(""+java.io.File.separatorChar)) 
        shortname = shortname + java.io.File.separatorChar;
      //Figure out if we have an absolute path by checking that the path
      //begins with a root.  ( '/' in Unix, "C:\" or "D:\" in Windows)
      for(int i = 0; i < roots.length; ++i) {
        if (shortname.startsWith(roots[i].toString())) {
          absolutePath=true;
          break;
        }
      }
      
      if (absolutePath) { //If it's an absolute path, good
        d_context.setDefaultRepository(new File(shortname));
      } 
      else { // if the path is realitive, make it absolute
        try {
          java.io.File file = new java.io.File(".");
          apath = file.getCanonicalPath() +
            java.io.File.separatorChar + shortname;
          d_context.setDefaultRepository(new File(apath));
        }
        catch (java.io.IOException ioe) {
         System.err.println
            ("Babel: Warning: Unable to get path to current directory! Any XML output may contain an incorrect path!");
         System.err.println
             ("Babel: Warning: skipping default repository path \"" +
              apath + "\".");
          
        }
      }
    }

    public final boolean isHidden() { return true; }
  }

  public class ServerSwitch extends RequiredArgSwitch
  {
    public ServerSwitch() {
      super('s', "server", 
            "Generate server (and client code) in the specified language (C | C++ | F77 | F90 | F03 | Java | Python).", "lang");
    }

    /**
     * Check if the specified client-side language is supported.
     */
    private String validServerLanguage( String lang ) 
      throws InvalidArgumentException
    { 
      CodeGenerationFactory factory = d_context.getFactory();
      CodeGenerator cg = factory.getCodeGenerator(lang, "skel");
      d_context.getSymbolTable().setTargetIsXML(false);
      if (null != cg) {
        try {
          cg.setName(lang);
        }
        catch (CodeGenerationException cge) {
          throw new InvalidArgumentException(cge.getMessage());
        }
        return cg.getName();
      }
      return null;
    }

    final public void processCommandSwitch(String optarg) 
      throws InvalidArgumentException, InvalidOptionException,
             CorruptSymbolException
    {
      optarg = optarg.toLowerCase();
      optarg = validServerLanguage(optarg);
      if (null != optarg) {
        d_context.getConfig().setGenerateServer(true);
        d_context.getConfig().setTargetLanguage(optarg);
        if (d_gen == null) {
          if (d_numRequired++ > 0) {
            throw new InvalidOptionException("Only one of the following options or its equivalent is allowed: --help, --version, --client, --server, --text, or --parse-check unless you use the --multi option.");
          }
        }
        else {
          if (d_gen.resolveSymbols()) {
            d_gen.generateServer(optarg);
          }
          else {
            d_gen.printErrors(System.err);
            throw new CorruptSymbolException();
          }
          d_gen.printErrors(System.err);
          d_gen.clear();
        }
      }
      else {
        throw new InvalidArgumentException
          ("Unsupported language specified for server option.");
      }
    }
  }

  public class TextSwitch extends RequiredArgSwitch
  {
    public TextSwitch() {
      super('t', "text", 
            "Generate text in specified form (XML | SIDL | HTML).", "form");
    }

    /**
     * Check if the specified client-side language is supported.
     */
    private String validTextForm( String lang ) 
      throws InvalidArgumentException
    { 
      CodeGenerationFactory factory = d_context.getFactory();
      CodeGenerator cg = factory.getCodeGenerator(lang, "text");
      if (null != cg) {
        try {
          cg.setName(lang);
          d_context.getSymbolTable().
            setTargetIsXML("xml".equals(cg.getName()));
        }
        catch (CodeGenerationException cge) {
          throw new InvalidArgumentException(cge.getMessage());
        }
        return cg.getName();
      }
      return null;
    }

    final public void processCommandSwitch(String optarg) 
      throws InvalidArgumentException, InvalidOptionException,
             CorruptSymbolException
    {
      optarg = optarg.toLowerCase();
      optarg = validTextForm(optarg); 
      if (null != optarg) {
        d_context.getConfig().setGenerateText(true);
        d_context.getConfig().setTargetLanguage(optarg);
        if (d_gen == null) {
          if (d_numRequired++ > 0 && !d_context.getConfig().getMultiMode()) {
            throw new InvalidOptionException("Only one of the following options or its equivalent is allowed: --help, --version, --client, --server, --text, or --parse-check unless you use the --multi option.");
          }
        }
        else {
          if (d_gen.resolveSymbols()) {
            d_gen.generateText(optarg);
          }
          else {
            d_gen.printErrors(System.err);
            throw new CorruptSymbolException();
          }
          d_gen.printErrors(System.err);
          d_gen.clear();
        }
      }
      else {
        throw new InvalidArgumentException
          ("Unsupported text form specified for text option.");
      }
    }
  }

  public class VpathSwitch extends RequiredArgSwitch
  {
    public VpathSwitch() {
      super('V', "vpath", 
            "Prepend alternative search path for reading Impl splicer blocks. Does not affect where Impls are generated. NOTE: --vpath=. is a no-op for autoconf/automake. If you really want current directory, use another argument like `pwd` or even ./.", "path");
    }

    final public void processCommandSwitch(String optarg) 
    {
      d_context.getConfig().setVPathDirectory(optarg);
    }
  }

  public class MultiResetOption extends BasicSwitch
  {
    public MultiResetOption() {
      super(0, "reset",
            "Only reset the symbol table to empty and the settings to their default.");
    }

    final public void processCommandSwitch(String optarg)
    {
      d_context.reset();
    }
  }

  public class ParseResolveOption extends RequiredArgSwitch
  {
    public ParseResolveOption() {
      super(1, null, null, null);
    }

    final public void processCommandSwitch(String optarg) 
    {
      d_gen.parseOrResolve(optarg);
    }

    final public boolean isHidden() { return true; }
  }

  public class TouchOption extends RequiredArgSwitch
  {
    public TouchOption() {
      super('T', "touch", "Touch the named file in the next target generation succeeds.", "file");
    }

    final public void processCommandSwitch(String optarg) 
    {
      String outdir = d_context.getConfig().getOutputDirectory();
      if (outdir == null) {
        outdir = ".";
      }
      if (!outdir.endsWith(File.separator)) {
        outdir = outdir + File.separator;
      }
      File touch = new File(outdir + optarg);
      d_gen.setTouchFile(touch);
      d_context.getConfig().setProtectLastTimeModified(false);
    }

    final public boolean isHidden() { return true; }
  }

  /**
   * Since this is a singleton class, the constructor is protected.
   */
  public UserOptions(Context context) {
    d_context = context;
  }

  public UserOptions(Context context, Generator gen)
  {
    d_context = context;
    d_gen = gen;
  }

  public void registerCommandLineSwitches(CommandLineDictionary dict)
    throws NameCollisionException
  {
    Class myClass = getClass();
    Class[] myClasses= myClass.getClasses();
    Class[] paramList = new Class[1];
    Object[] args = new Object[1];
    paramList[0] = myClass;
    args[0] = this;
    int i;
    for(i = 0; i < myClasses.length; ++i) {
      Class innerClass = myClasses[i];
      if (innerClass.getName().endsWith("Switch") &&
          !innerClass.isInterface()) {
        try {
          Constructor ctor = innerClass.getConstructor(paramList);
          Object o = ctor.newInstance(args);
          if (o instanceof CommandLineSwitch) {
            dict.addCommandLineSwitch((CommandLineSwitch)o);
          }
        }
        catch (NoSuchMethodException nsme) {
          // ignore
        }
        catch (InvocationTargetException ite) {
          System.err.println("InvocationTargetException: " + ite.getMessage());
        }
        catch (IllegalArgumentException iarg) {
          System.err.println("IllegalArgumentException: " + iarg.getMessage());
        }
        catch (InstantiationException ie) {
          System.err.println("InstantiationException: " + ie.getMessage());
        }
        catch (IllegalAccessException iae) {
          System.err.println("IllegalAccessException: " + iae.getMessage());
        }
      }
    }
    if (d_gen != null) {
      dict.addCommandLineSwitch(new MultiResetOption());
      dict.addCommandLineSwitch(new ParseResolveOption());
      dict.addCommandLineSwitch(new TouchOption());
    }
  }

  public void reset()
  {
    d_numRequired = 0;
    d_versionSet = false;
  }

  /**
   * Return the number of required elements on the command line.
   */
  public int getNumRequired() { return d_numRequired; }

  /**
   * Return <code>true</code> iff the version option appeared and it
   * was printed.
   */
  public boolean getVersionPrinted() { return d_versionSet; }
}
