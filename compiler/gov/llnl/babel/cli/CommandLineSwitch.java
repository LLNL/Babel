//
// File:        CommandLineSwitch.java
// Package:     gov.llnl.babel.cli
// Copyright:   (c) 2005 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision: 6212 $
// Date:        $Date: 2007-10-31 17:07:17 -0700 (Wed, 31 Oct 2007) $
// Description: Interface for adding command line switches to Babel
// 

package gov.llnl.babel.cli;

/**
 * Extensions implement this interface to add new command line switches to
 * Babel. Each command line switch has a separate instance.
 */

public interface CommandLineSwitch {
  
  /**
   * Return the long option name, usually one or more words separated by
   * hyphens. On the command line, users will invoke this option by
   * putting this string after two consecutive hyphen. For example, if this
   * method returned "foo-mode", users would invoke it by putting
   * "--foo-mode" on the command line. Try to avoid long names that are
   * likely to be used by other extensions.
   * 
   * @return a non-null string containing one or more words separated by
   * hyphens. The string should not contain any white space characters.
   */
  String getLongName();

  /**
   * Return <code>true</code> if and only if, this command line switch
   * requires an argument. If this method returns <code>true</code>, 
   * hasOptionalArgument should return <code>false</code>.
   *
   * @return <code>true</code> implies that the switch take an optional
   * argument; <code>false</code> implies that it does not.
   */
  boolean hasRequiredArgument();

  /**
   * Return <code>true</code> if and only if, this command line switch
   * has an optional argument. If this method returns <code>true</code>,
   * hasRequiredArgument should return <code>false</code>.
   *
   * @return <code>true</code> implies that the switch takes an optional
   * argument; <code>false</code> implies that it does not.
   */
  boolean hasOptionalArgument();

  /**
   * If the switch has an argument, what name should be used
   * to identify it in the help text. You may refer to this 
   * name in getHelpText.
   *
   * @return null or the name of the optional or required argument.
   */
  String getArgumentName();
  
  /**
   * Return a brief string describing what this command line switch
   * implies or does. This text is presented when the user executes
   * the help command line option. Do not mention the short form,
   * if it's available, the automatically generated text will mention it.
   * 
   * @return a non-null string with no newline characters.
   */
  String getHelpText();

  /**
   * If the command line switch has a one character short form, return
   * the character value as an integer. Since there are fewer options for
   * the short form, do not count on it being available. If you do not
   * care to define a short form, return 0.
   *
   * @return 0 if you do not care to define a short form; otherwise, return
   * the short form character as an int. This value should be strictly
   * less than 65536. The return value should generally be a letter,
   * a digit, or punctuation character -- excluding 'W', '-', ':', and '?'.
   */
  int getShortForm();

  /**
   * If the command line manager cannot support the short form you requested
   * or if you did not specify the short form, this method will be called
   * to set your short form. You must store the value provided by this call
   * and return it when {@link #getShortForm} is called in the future.
   *
   * @param value your object <strong>must</strong> return this value in
   *              future calls to {@link #getShortForm}.
   */
  void setShortForm(int value);


  /**
   * Return <code>true</code> if this command line option should be
   * hidden when the help text is generated.
   *
   * @return <code>true</code> means that this options requests to not
   *         be printed with the help text.
   */
  boolean isHidden();

  /**
   * This method is called when the Babel end user specifies this command
   * line switch on the command line.
   * 
   * @param optarg this argument will be null if the command line switch
   * doesn't take a required argument (i.e., {@link #hasRequiredArgument}
   * is <code>false</code>) or if the command line switch takes an
   * optional argument (i.e., {@link #hasOptionalArgument} is
   * <code>true</code>) and an argument wasn't provided; otherwise, the
   * argument is a non-null string including the text from the command line.
   *
   * @exception gov.llnl.babel.cli.InvalidArgumentException this indicates 
   * that the command line argument provided is wrong somehow. For example,
   * if the argument should be an integer and the input isn't a valid
   * integer, throw this exception.
   * @exception gov.llnl.babel.cli.InvalidOptionException this indicates
   * that the command line option itself is somehow wrong in the context of
   * preceeding switches. For example, two switches may be mutually
   * exclusive, so having both in a command line would cause the second
   * switch to throw this exception.
   */
  void processCommandSwitch(String optarg)
    throws InvalidArgumentException, InvalidOptionException, 
           CorruptSymbolException;
}
