//
// File:        CommandLineExtension.java
// Package:     gov.llnl.babel.cli
// Copyright:   (c) 2005 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision: 6171 $
// Date:        $Date: 2007-10-08 16:39:28 -0700 (Mon, 08 Oct 2007) $
// Description: Extensions to add command line switches implement this.
// 

package gov.llnl.babel.cli;

/**
 * Babel extensions wishing to add command line switches implement this
 * interface. Babel checks each extension class to see if it implements
 * this interface. If an extension does, it calls {@link
 * #registerCommandLineSwitches} with the current dictionary.
 */
public interface CommandLineExtension {

  /**
   * This method should register all command line switches in the
   * dictionary. Babel will call this method exactly once on 
   * all extensions it loads.
   *
   * @param dict the command line switch dictionary in which you register
   *  your command line extensions.
   *
   *
   * @exception gov.llnl.babel.cli.NameCollisionException
   * If your extension doesn't know what to do with the exception
   * thrown by calls to {@link
   * gov.llnl.babel.cli.CommandLineDictionary#addCommandLineSwitch},
   * you can let Babel handle it.
   */
  public void registerCommandLineSwitches(CommandLineDictionary dict)
    throws NameCollisionException;
}
