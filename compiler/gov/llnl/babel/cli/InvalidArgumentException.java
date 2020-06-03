//
// File:        InvalidArgumentData.java
// Package:     gov.llnl.babel.cli
// Copyright:   (c) 2005 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision: 6171 $
// Date:        $Date: 2007-10-08 16:39:28 -0700 (Mon, 08 Oct 2007) $
// Description: Exception indicates bad input to command line switch.
// 

package gov.llnl.babel.cli;

/**
 * This exception is thrown by a {@link
 * gov.llnl.babel.cli.CommandLineSwitch} when
 * it receives an argument that's invalid. The message should be designed
 * to be meaningful to an end user.
 */
public class InvalidArgumentException extends Exception { 

   /**
    * 
    */
	private static final long serialVersionUID = 1907762212216390593L;

	/**
	 * Create an exception to indicate to a Babel end user how the
	 *	command line argument was invalid. If possible, the message
	 * should indicate how the argument was wrong. Babel will 
	 * write "Babel: Error processing command line switch <em>long form</em>"
	 * with a newline where <em>long form</em> is replaced by the 
	 * name of the long option before writing the message.
	 *
	 * @param message a message that will be shown to the Babel
	 * end user.
	 */
	public InvalidArgumentException(String message)	{
		super(message);
	}
}
