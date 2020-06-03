//
// File:        InvalidOptionException.java
// Package:     gov.llnl.babel.cli
// Copyright:   (c) 2005 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision: 6171 $
// Date:        $Date: 2007-10-08 16:39:28 -0700 (Mon, 08 Oct 2007) $
// Description: Indicate that an illegal option appeared on the command line
// 

package gov.llnl.babel.cli;

/**
 * This exception is thrown by a {@link
 * gov.llnl.babel.cli.CommandLineSwitch} when it receives the command line
 * switch is somehow invalid. For example, two switches may be mutually
 * exclusive, so having both in a command line would cause the second
 * switch to throw this exception. Throwing this exception should 
 * normally cause a Babel run to quit immediately.
 */
public class InvalidOptionException extends Exception {

  /**
	 * 
	 */
	private static final long serialVersionUID = -4808370710169157879L;

/**
   * Create an exception to indicate that Babel received an invalid
   * option on the command line. The message should indicate how
   * the command line option was invalid in language that can be
   * understood by a Babel end user.
   * 
   * @param message a message indicating how the option was invalid.
   */
  public InvalidOptionException(String message)
  {
    super(message);
  }
}
