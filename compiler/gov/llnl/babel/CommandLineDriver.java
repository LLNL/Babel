
//
// File:        CommandLineDriver.java
// Package:     gov.llnl.babel
// Revision:    $Revision: 7421 $
// Modified:    $Date: 2011-12-15 17:06:06 -0800 (Thu, 15 Dec 2011) $
// Description: main driver for the babel IDL compiler
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

import gov.llnl.babel.Context;
import gov.llnl.babel.backend.BuildGenerator;
import gov.llnl.babel.backend.CodeGenerationFactory;
import gov.llnl.babel.backend.CodeGenerator;
import gov.llnl.babel.cli.CommandLineDictionary;
import gov.llnl.babel.cli.CommandLineExtension;
import gov.llnl.babel.cli.InvalidArgumentException;
import gov.llnl.babel.cli.InvalidOptionException;
import gov.llnl.babel.cli.NameCollisionException;
import gov.llnl.babel.symbols.SymbolID;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * This class is the main driver for running Babel from the command line.
 */
public class CommandLineDriver {
 /**
  * The command line parser.
  */
  private UserOptions d_option_parser;

  private Context d_context;

  private Generator d_generator;

  private SwitchDictionary d_dict;

  private class IncludeFile extends UserOptions.RequiredArgSwitch {
    public IncludeFile() {
      super('I', "include",
            "Read types from a SIDL file, but don't generate code for them.",
            "url");
    }
    final public void processCommandSwitch(String optarg) 
      throws InvalidArgumentException
    {
      if (optarg != null) {
        try {
          d_generator.parseFileAsInclude(optarg);
        }
        catch (Exception e) {
          throw new InvalidArgumentException
            ("Problems reading from include file " + optarg + ": " +
             e.getMessage());
        }
      }
      else {
        throw new InvalidArgumentException
          ("A valid filename or url must be provided.");
      }
    }
  }


  /**
   * The protected singleton constructor instantiates the options parser,
   * <code>UserOptions</code>.
   * 
   * @see UserOptions
   */
  public  CommandLineDriver(Context context, boolean multiMode) {
    d_context = context;
    d_generator = new Generator(d_context);
    d_dict = new SwitchDictionary(multiMode);
    d_option_parser = multiMode ? new UserOptions(d_context, d_generator)
      : new UserOptions(d_context);
  }
  
  /**
   * Extract the options from the command line arguments and perform associated
   * set up as appropriate.
   * 
   * @see BabelConfiguration
   */
  public String[] processCommandline(String args[]) {
    try {
      d_option_parser.registerCommandLineSwitches(d_dict);
      d_dict.addCommandLineSwitch(new IncludeFile());
    } catch(NameCollisionException nce) {
      System.err.
        println
        ("Babel: Internal Babel command line switches have a name space conflict!");
      return null;
    }
    loadExtensions(d_dict);
    String[] remainingArgs;
    try {
      remainingArgs = d_dict.executeCommandLineSwitches(args);
    }
    catch(InvalidOptionException ioe) {
      return null;
    }
    if (d_dict.getHelpPrinted() || d_option_parser.getVersionPrinted()) {
      return remainingArgs;
    }

    if (d_context.getConfig().generateStdlib()) {
      if (d_context.getConfig().generateClient()
          && "python".equals(d_context.getConfig()
                             .getTargetLanguage())) {
        System.err.
          println
          ("Babel: Warning: Allowing concurrent request for python client and SIDL standard library generation.");
      }
      else {
        if (!(d_context.getConfig().generateServer()
              && "c".equals(d_context.getConfig().
                            getTargetLanguage()))) {
          System.err.
            println
            ("Babel: Error: --generate-sidl-stdlib only makes sense with --server=c or --client=python.");
          return null;
        }
      }
    }

    return remainingArgs;
  }

  public int generateCode(String[] remainingArgs) {
    
    /*
     * Now continue processing the remainder of the arguments and create
     * associated processing elements as needed.
     */
    try {
      resolveRemainingArgs(remainingArgs, 0);
    }
    catch(CollectionException ex) {
      if (d_generator.hasErrorOccurred()) {
        d_generator.printErrors(System.err);
      }
      System.err.println("Babel: FATAL: Unable to resolve remaining args.");
      ex.printStackTrace(System.err);
      return 1;
    }
/*
    catch (CodeGenerationException cgEx) {
      if (d_generator.hasErrorOccurred()) {
        d_generator.printErrors(System.err);
      }
      System.err.println("Babel: FATAL: Unable to resolve remaining args.");
      cgEx.printStackTrace(System.err);
      return 1;
    }
*/
    if (d_generator.hasErrorOccurred()) {
      d_generator.printErrors(System.err);
      return 1;
    }

    /*
     * Stop now if only checking parsing.
     */
    if (d_context.getConfig().parseCheckOnly()) {
      d_generator.printErrors(System.err);
      return 0;
    }

    /*
     * Generate the source code.
     */
    if (d_context.getConfig().generateText()) {
      d_generator.generateText(d_context.getConfig()
                               .getTargetLanguage());
    }
    else {
      if (d_context.getConfig().generateClient()) {
        d_generator.generateClient(d_context.getConfig()
                                   .getTargetLanguage());
      }
      else {
        d_generator.generateServer(d_context.getConfig()
                                   .getTargetLanguage());
      }
    }
    d_generator.printErrors(System.err);
    
    return d_generator.hasErrorOccurred() ? 1 : 0;
  }

  

  private CollectionException addToCollection(CollectionException ce,
                                              String uri, Exception ex) {
    if (ce == null) {
      ce = new CollectionException(uri, ex);
    }
    else {
      ce.addException(uri, ex);
    }
    return ce;
  }

  /**
   * Process the remainder of the command line, assuming the contents reflect
   * one or more SIDL files.
   */
  private void resolveRemainingArgs(String args[], int firstUnusedArg)
  throws CollectionException {
    CollectionException ce = null;
    /*
     * First parse the remaining arguments.
     */
    if(args != null) {
      for (int i=firstUnusedArg; i< args.length; i++ ) { 
        try {
          d_generator.parseOrResolve(args[i]);
        } catch(Exception ex) { 
          System.err.println(ex.getMessage());
          ex.printStackTrace();
          ce = addToCollection(ce, args[i], ex);
        }
      }
    }
    if (d_generator.hasErrorOccurred() || !d_generator.resolveSymbols()) {
      d_generator.printErrors(System.err);
    }
  }


  /**
   * Print all symbols to output. This is obviously intended only to facilitate
   * debugging.
   */
  public void printSymbolNames(Set symbols) {
    System.out.println("Symbol set:");
    Iterator i = symbols.iterator();
    while (i.hasNext()) {
      SymbolID id = (SymbolID) i.next();
      System.out.println("  " + id.getFullName());
    }
  }

  public int processMultiple(String[] args)
  {
    try {
      d_option_parser.registerCommandLineSwitches(d_dict);
      d_dict.addCommandLineSwitch(new IncludeFile());
    } catch(NameCollisionException nce) {
      System.err.
        println
        ("Babel: Internal Babel command line switches have a name space conflict!");
      return 1;
    }
    loadExtensions(d_dict);
    return d_dict.executeMultiple(args);
  }

  /**
   * Load and create an instance of each class in listed in the comma separated
   * list stored in the Java property <code>babel.extensions</code>.
   */
  public void loadExtensions(CommandLineDictionary dict) {
    StringTokenizer tok =
      new StringTokenizer(System.getProperty("babel.extensions", ""), ",");

    while (tok.hasMoreTokens()) {
      final String className = tok.nextToken();
      try {
        boolean matches = false;
        final Class cls = Class.forName(className);
        final Object obj = cls.newInstance();
        if (obj instanceof CodeGenerator) {
          matches = true;
        }
        if (obj instanceof BuildGenerator) {
          matches = true;
        }
        if (obj instanceof CommandLineExtension) {
          ((CommandLineExtension) obj).registerCommandLineSwitches(dict);
          matches = true;
        }
        if (!matches) {
          System.err.println("Babel: Warning: Extension " + className
                             + " has no recognized extensions to register.");
        }
      }
      catch(NameCollisionException nce) {
        System.err.
          println("Babel: Warning: Problem with command line extension " +
                  className + ".");
        System.err.println("Babel: Warning: " + nce.getMessage());
      }
      catch(ClassNotFoundException cnfe) {
        System.err.println("Babel: Warning: Unable to load extension "
                           + className + ".");
      }
      catch(LinkageError le) {
        System.err.println("Babel: Warning: Unable to load extension "
                           + className +
                           " due to dependencies on other classes.");
      }
      catch(IllegalAccessException iae) {
        System.err.
          println
          ("Babel: Warning: Unable to create an instance of extension " +
           className + ".");
      }
      catch(InstantiationException ie) {
        System.err.
          println("Babel: Warning: Cannot create an instance of extension " +
                  className +
                  " because it's an interface or abstract class.");
      }
      catch(SecurityException se) {
        System.err.println("Babel: Warning: Extension " + className
                           + " violates the current Java security policy.");
      }
    }
  }

  private boolean printHelpAndBail()
  {
    return (d_option_parser.getNumRequired() == 0);
  }

  private static boolean isMultiRun(final String[] args)
  {
    for(int i = 0; i < args.length; ++i) {
      if ("--multi".equals(args[i])) return true;
    }
    return false;
  }

  private static int mainDriver(String args[]) {
    int returnValue = 1;
    Context context = new Context();
    
    if (isMultiRun(args)) {
      /* setup for multi-mode command line processing */
      CommandLineDriver myDriver = new CommandLineDriver(context, true);
      returnValue = myDriver.processMultiple(args);
    }
    else {
      /*
       * Set up the configuration parameters based on command line arguments.
       */
      CommandLineDriver myDriver = new CommandLineDriver(context, false);
      String[] remainingArgs = myDriver.processCommandline(args);
      
      if (myDriver.printHelpAndBail() ||
          (myDriver.d_option_parser.getVersionPrinted() &&
           (remainingArgs.length != 0))) {
        if (!myDriver.d_dict.getHelpPrinted()) {
          myDriver.d_dict.printHelpText();
        }
        remainingArgs = null;
        returnValue = 1;
      }
      else if(remainingArgs != null && (remainingArgs.length != 0)) {
        returnValue = myDriver.generateCode(remainingArgs);
      } 
      else {
        if (myDriver.d_dict.getHelpPrinted() ||
            myDriver.d_option_parser.getVersionPrinted()) {
          returnValue = 0;
        }
      }
    }
    return returnValue;
  }

  private static boolean startDemon(String args[]) {
    final int len = args.length;
    return (args.length >= 2) && "--demon".equals(args[len-2]) &&
      (new File(args[len-1])).canRead();
  }

  private static String readCmd(FileReader stream)
    throws IOException
  {
    char [] buffer = new char[128];
    StringBuffer buf = new StringBuffer();
    int len;
    while ((len = stream.read(buffer)) >= 0) {
      buf.append(buffer, 0, len);
    }
    return (buf.length() > 0) ? buf.toString() : null;
  }

  private static int demonDriver(String args[]) {
    int returnValue = 0, count = 0;
    File namedPipe = new File(args[args.length-1]);
    FileReader cmdStream = null;
    String line;
    System.out.println("Starting Babel demon...");
    while (true) {
      try {
        System.out.println("Reading command line " + Integer.toString(count));
        ++count;
        cmdStream = new FileReader(namedPipe);
        if ((line = readCmd(cmdStream)) != null) {
          StringTokenizer tok = new StringTokenizer(line);
          final int numArgs = tok.countTokens();
          if (numArgs > 0) {
            String [] subArgs = new String[numArgs];
            for(int i = 0; i < subArgs.length; ++i) {
              subArgs[i] = tok.nextToken();
            }
            if ("--stop-demon".equals(subArgs[numArgs-1])) {
              break;
            }
            else {
              int ignored = mainDriver(subArgs);
            }
          }
        }
      }
      catch (Throwable th) {
        th.printStackTrace();
        returnValue = 1;
      }
      finally {
        if (cmdStream != null ) {
          try {
            cmdStream.close();
          }
          catch (IOException ioe) {
            ioe.printStackTrace();
            returnValue = 1;
          }
        }
      }
    }
    System.out.println("Babel demon stopped");
    return returnValue;
  }

  /****************************************************************************
   * Main babel entry point.
   */
  public static void main(String args[]) {
    int returnValue = 0;
    if (startDemon(args)) {
      returnValue = demonDriver(args);
    }
    else {
      returnValue = mainDriver(args);
    }
    /* workaround for JNI bug in Linux 1.3.1 JVM */
    Runtime.getRuntime().exit(returnValue);
  }
}
