//
// File:        SwitchDictionary.java
// Package:     gov.llnl.babel
// Copyright:   (c) 2005 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision: 6318 $
// Date:        $Date: 2008-01-31 16:52:49 -0800 (Thu, 31 Jan 2008) $
// Description: Realization of command line switch dictionary
// 

package gov.llnl.babel;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;
import gov.llnl.babel.cli.CommandLineDictionary;
import gov.llnl.babel.cli.CommandLineSwitch;
import gov.llnl.babel.cli.CorruptSymbolException;
import gov.llnl.babel.cli.InvalidArgumentException;
import gov.llnl.babel.cli.InvalidOptionException;
import gov.llnl.babel.cli.NameCollisionException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class SwitchDictionary implements CommandLineDictionary {
  private static final int s_indentColumn = 28;
  private static final int s_maxColumn = 80;
  private boolean d_helpPrinted = false;
  private boolean d_multiMode = false;

  private class HelpOption implements CommandLineSwitch {
    int d_shortForm = 'h';
    final public String getLongName() { return "help"; }
    final public boolean hasRequiredArgument() { return false; }
    final public boolean hasOptionalArgument() { return false; }
    final public String getArgumentName() { return null; }
    final public String getHelpText(){ return "Display usage information and exit."; }
    final public int getShortForm() { return d_shortForm; }
    final public void setShortForm(int value) { d_shortForm = value; }
    final public void processCommandSwitch(String optarg) {
      printHelpText();
    }
    final public boolean isHidden() { return false; }
  }

  private final static int MAX_CHAR = 65535;
 
  /**
   * The next available integer (above all Unicode values) for
   * command line switches.
   */
  private int d_nextAvailableInteger = MAX_CHAR + 1;

  /**
   * This is a map from long form name to {@link
   * gov.llnl.babel.cli.CommandLineSwitch} interface reference. 
   * This is used for checking for name collisions and to provide
   * a sorted list for printing help.
   */
  private TreeMap d_longOptions = new TreeMap();;

  /**
   * This provides a map from short name value or other integer
   * to a {@link gov.llnl.babel.cli.CommandLineSwitch} interface
   * reference.
   */
  private HashMap d_shortOptions = new HashMap();
 
  private static final boolean isValidChar(char ch)
  {
    return (ch == '\001') ||
      (Character.isDefined(ch) && !Character.isWhitespace(ch) &&
       !Character.isISOControl(ch) && (ch != 'W') && (ch != '-') && 
       (ch != ':') && (ch != '?'));
  }

  private final Integer getShortValue(CommandLineSwitch cls)
  {
    int value = cls.getShortForm();
    if ((value <= 0) || (value > MAX_CHAR) ||
        !isValidChar((char)value)) {
      value = d_nextAvailableInteger++;
    }
    Integer intObj = new Integer(value);
    if (d_shortOptions.containsKey(intObj)) {
      /* some other switch is already using that character */
      value = d_nextAvailableInteger++;
      intObj = new Integer(value);
    }
    return intObj;
  }

  public SwitchDictionary(boolean multiMode)
  {
    d_multiMode = multiMode;
    try {
      addCommandLineSwitch(new HelpOption());
    }
    catch (NameCollisionException nce) {
      // this should be impossible
    }
  }
  
  /**
   * Return <code>true</code> iff the help text was printed.
   */
  public boolean getHelpPrinted()
  {
    return d_helpPrinted;
  }
  
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
    throws NameCollisionException
  {
    final String longName = cls.getLongName();
    if (longName != null) {
      CommandLineSwitch currentEntry = 
        (CommandLineSwitch)d_longOptions.get(longName);
      if (currentEntry != null) {
        throw new NameCollisionException(currentEntry);
      }
      d_longOptions.put(longName, cls);
    }
    Integer shortValue = getShortValue(cls);
    cls.setShortForm(shortValue.intValue());
    d_shortOptions.put(shortValue, cls);
  }

  private static int printArg(String arg, int column)
  {
    if (arg != null ) {
      System.out.print('<');
      System.out.print(arg);
      System.out.print('>');
      return column + 2 + arg.length();
    }
    return column;
  }

  private static void indentLine(int startingColumn)
  {
    if (startingColumn >= s_indentColumn) {
      System.out.println();
      startingColumn = 0;
    }
    while (++startingColumn < s_indentColumn) {
      System.out.print(' ');
    }
  }

  private void printSwitchHelp(CommandLineSwitch cls)
  {
    final int shortForm = cls.getShortForm();
    final String longForm = cls.getLongName();
    final String argName = cls.getArgumentName();
    final String helpText = cls.getHelpText();
    int column = 1;
    System.out.print(' ');
    if ((shortForm > 32) &&
        (shortForm <= MAX_CHAR) &&
        Character.isDefined((char)shortForm)) {
      System.out.print('-');
      System.out.print((char)shortForm);
      column += 2;
      column = printArg(argName, column);
      System.out.print(" | ");
      column += 3;
    }
    System.out.print("--");
    System.out.print(longForm);
    column += 2 + longForm.length();
    column = printArg(argName, column);
    System.out.print(' ');
    ++column;
    StringTokenizer tok = new StringTokenizer(helpText);
    String nextWord = null;
    do {
      indentLine(column);
      column = s_indentColumn - 1;
      if (nextWord != null) {
        System.out.print(' ');
        System.out.print(nextWord);
        column += (1 + nextWord.length());
        nextWord = null;
      }
      while (tok.hasMoreTokens()) {
        nextWord = tok.nextToken();
        if ((column + 1 + nextWord.length()) <= s_maxColumn) {
          System.out.print(' ');
          System.out.print(nextWord);
          column += (1 + nextWord.length());
          nextWord = null;
        }
        else break;
      }
    } while (nextWord != null);
    if (nextWord != null) {
      indentLine(column);
      System.out.print(' ');
      System.out.println(nextWord);
    }
    else {
      System.out.println();
    }
  }

  public void printHelpText()
  {
    if (!d_helpPrinted) {
      System.out.println("Usage:  babel [ -h | --help ]");
      System.out.println("   or   babel [ -v | --version ]");
      System.out.println(
         "   or   babel option(s) sidlfilename1 ... sidlfilenameN");
      System.out.println("Babel Version   : " + Version.getFullVersion());
      System.out.println("Java VM Vendor  : " + System.getProperty("java.vm.vendor","Unknown"));
      System.out.println("Java VM Version : " + System.getProperty("java.vm.version","Unknown"));
      System.out.println("Operating System: " + System.getProperty("os.name", "Unknown") + " version " + System.getProperty("os.version","Unknown"));
      System.out.println();
      System.out.println("where help, version, and option(s) are:");
      Iterator i = d_longOptions.values().iterator();
      while (i.hasNext()) {
        CommandLineSwitch cls = (CommandLineSwitch)i.next();
        if (!cls.isHidden()) {
          printSwitchHelp(cls);
        }
      }
      System.out.println();
      System.out.println(
                         "If you have any suggestions or questions not answered by the documentation,");
      System.out.println("please send email to components@llnl.gov.");
      d_helpPrinted = true;
    }
  }

  private static int getArgSpec(CommandLineSwitch cls)
  {
    if (cls.hasRequiredArgument()) return gnu.getopt.LongOpt.REQUIRED_ARGUMENT;
    if (cls.hasOptionalArgument()) return gnu.getopt.LongOpt.OPTIONAL_ARGUMENT;
    return gnu.getopt.LongOpt.NO_ARGUMENT;
  }

  private LongOpt[] prepareLongOpts()
  {
    LongOpt[] longOpts = new LongOpt[d_longOptions.size()];
    Iterator i = d_longOptions.values().iterator();
    int j = 0;
    while (i.hasNext()) {
      CommandLineSwitch cls = (CommandLineSwitch)i.next();
      longOpts[j++] = new LongOpt
        (cls.getLongName(), getArgSpec(cls), null, cls.getShortForm());
    }
    return longOpts;
  }

  private String prepareShortOpts()
  {
    StringBuffer buf = new StringBuffer(d_longOptions.size()+2);
    if (d_multiMode) {
      buf.append('-');
    }
    Iterator i = d_longOptions.values().iterator();
    while (i.hasNext()) {
      CommandLineSwitch cls = (CommandLineSwitch)i.next();
      final int shortForm = cls.getShortForm();
      if ((shortForm < Character.MAX_VALUE) && isValidChar((char)shortForm)) {
        buf.append((char)shortForm);
        if (cls.hasRequiredArgument()) buf.append(':');
        if (cls.hasOptionalArgument()) buf.append("::");
      }
    }
    return buf.toString();
  }

  /**
   * Execute the command line switches from arguments and return
   * the unprocessed command line arguments.
   *
   * @param arguments the command line arguments to execute.
   *
   * @return a non-NULL array containing the remaining unprocessed
   * arguments (i.e., those arguments that aren't part of a command line
   * switch).
   *
   * @exception gov.llnl.babel.cli.InvalidOptionException when this
   * exception is thrown, it indicates that the Babel run should be
   * ended. Assume the error has already been reported to {@link
   * java.lang.System#err}.
   */
  public String[] executeCommandLineSwitches(String[] arguments)
    throws InvalidOptionException
  {
    Getopt g = new Getopt("babel", arguments, prepareShortOpts(),
                          prepareLongOpts());
    int c;
    while ((c = g.getopt()) != -1) {
      Integer iObj = new Integer(c);
      CommandLineSwitch cls = (CommandLineSwitch)d_shortOptions.get(iObj);
      String argValue = g.getOptarg();
      if (cls != null) {
        try {
          cls.processCommandSwitch(argValue);
        }
        catch (InvalidArgumentException iae) {
          System.err.println("Babel: Error processing command line switch " +
                             cls.getLongName());
          System.err.println("Babel: " + iae.getMessage());
        }
        catch (InvalidOptionException ioe) {
          System.err.println("Babel: Command line switch " +
                             cls.getLongName() + " is invalid.");
          System.err.println("Babel: " + ioe.getMessage());
          throw ioe;
        }
        catch (CorruptSymbolException cse) {
          System.err.println("Babel: Unexpected exception.");
        }
      }
      else {
        System.err.println("Babel: Something went wrong could not find option " + c);
      }
    }
    final int nextArg = g.getOptind();
    String [] result;
    if (nextArg < arguments.length) {
      result = new String[arguments.length - nextArg];
      System.arraycopy(arguments, nextArg, result, 0, 
                       arguments.length - nextArg);
    }
    else {
      /* nothing left to process */
      result = new String[0];
    }
    return result;
  }

  private int findReset(final String[] arguments,
                        int            starting)
  {
    while ((starting < arguments.length) &&
           !"--reset".equals(arguments[starting])) {
      ++starting;
    }
    return starting;
  }

  public int executeMultiple(String[] arguments)
  {
    Getopt g = new Getopt("babel", arguments, prepareShortOpts(),
                        prepareLongOpts());
    int c;
    int returnValue = 0;
    while ((c = g.getopt()) != -1) {
      Integer iObj = new Integer(c);
      CommandLineSwitch cls = (CommandLineSwitch)d_shortOptions.get(iObj);
      String argValue = g.getOptarg();
      if (cls != null) {
        try {
          cls.processCommandSwitch(argValue);
        }
        catch (InvalidArgumentException iae) {
          System.err.println("Babel: Error processing command line switch " +
                             cls.getLongName());
          System.err.println("Babel: " + iae.getMessage());
          returnValue = 1;
        }
        catch (InvalidOptionException ioe) {
          System.err.println("Babel: Command line switch " +
                             cls.getLongName() + " is invalid.");
          System.err.println("Babel: " + ioe.getMessage());
          returnValue = 1;
        }
        catch (CorruptSymbolException cse) {
          System.err.println("Babel: Skipping to next --reset argument.");
          returnValue = 1;
          g.setOptind(findReset(arguments,g.getOptind()));;
        }
      }
      else {
        System.err.println("Babel: Something went wrong could not find option " + c);
        returnValue = 1;
      }
    }
    return returnValue;
  }
}
