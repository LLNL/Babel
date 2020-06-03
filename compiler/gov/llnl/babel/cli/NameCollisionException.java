//
// File:        NameCollisionException.java
// Package:     gov.llnl.babel.cli
// Copyright:   (c) 2005 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision: 6171 $
// Date:        $Date: 2007-10-08 16:39:28 -0700 (Mon, 08 Oct 2007) $
// Description: Exception indicates two identically named switches.
// 

package gov.llnl.babel.cli;

/**
 * This exception is thrown by {@link
 * gov.llnl.babel.cli.CommandLineDictionary#addCommandLineSwitch} when the
 * client attempts to add a switch that has the same name as an
 * entry already in the dictionary.
 */
public class NameCollisionException extends Exception {
  /**
	 * 
	 */
	private static final long serialVersionUID = 8591645719084802741L;
/**
   * Hold the entry that was in conflict with the new value.
   */
  private CommandLineSwitch d_cls;
  
  /**
   * Create an exception indicating that there is a name space
   * collision between command line switches. The exception
   * holds the dictionary entry that the new item conflicted 
   * with.
   *
   * @param cls  the command line switch that the new entry
   *           conflicted with
   */
  public NameCollisionException(CommandLineSwitch cls) {
    super("new dictionary entry conflicts with " + cls.getLongName());
    d_cls = cls;
  }

  /**
   * Return the item in the dictionary with which the new entry conflicts.
   */
  public CommandLineSwitch getConflict() 
  {
    return d_cls;
  }
}
