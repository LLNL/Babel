//
// File:        CommandLineDictionary.java
// Package:     gov.llnl.babel.cli
// Copyright:   (c) 2005 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision: 6171 $
// Date:        $Date: 2007-10-08 16:39:28 -0700 (Mon, 08 Oct 2007) $
// Description: File to manage all command line switches.
// 

package gov.llnl.babel.cli;

/**
 * This interface is used to add {@link gov.llnl.babel.cli.CommandLineSwitch}
 * objects to the dictionary of all command line switches. Each command
 * line switch must be added during Babel's startup.
 */
public interface CommandLineDictionary {

  /**
   * Add a command line switch to the dictionary of available
   * command line switches. Babel requires that each command
   * line switch have a unique long name. Given that short
   * names are limited, it allocates abbreviated forms on a
   * first come first served basis.
   *
   * @param cls a non-null command line switch to add to the dictionary.
   *
   * @exception gov.llnl.babel.cli.NameCollisionException this indicates
   * that <code>cls</code> has the same long name as a 
   * {@link gov.llnl.babel.cli.CommandLineSwitch} already in the dictionary.
   * This exception is never generated due to collisions in the
   * short form.
   */
  public void addCommandLineSwitch(CommandLineSwitch cls)
    throws NameCollisionException;
}
