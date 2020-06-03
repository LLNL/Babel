//
// File:        CMangler.java
// Package:     gov.llnl.babel.backend.mangler
// Copyright:   (c) 2002 Lawrence Livermore National Security, LLC
// Revision:    @(#) $Revision: 6171 $
// Date:        $Date: 2007-10-08 16:39:28 -0700 (Mon, 08 Oct 2007) $
// Description: Name mangler for C-like languages.
// 

package gov.llnl.babel.backend.mangler;
import java.security.NoSuchAlgorithmException;

/**
 * The CMangler class provides a {@link
 * gov.llnl.babel.backend.mangler.NameMangler} for the C language. Most
 * C compilers allow arbitrarily large identifiers and consider an
 * arbitrarily large number of characters in resolving symbols too. However,
 * the C standard only promises that only the first 31 characters are
 * significant.  Some brain damaged C compilers may require name mangling.
 */
public class CMangler extends ShaMangler {
  /**
   * This is the set of allowable characters in C/C++.
   */
  private static final char[] s_cChars = {
    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
    'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
    'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '_'
  };
  
  /**
   * Create a name mangler for C. This constructor provides the
   * C character set.
   *
   * @param maxNameLen   the maximum allowable number of characters in a 
   *                     name. Typically, this is 31 for the ANSI C
   *                     standard. 
   * @param maxUnmangled the number of characters out of
   *                     <code>maxNameLen</code> that should be used
   *                     for unmangled content.
   * @exception java.security.NoSuchAlgorithmException
   *    this is thrown when the SHA message digest is unavailable. Sun's
   *    specifications says that it should be available.
   */
  public CMangler(int maxNameLen,
                  int maxUnmangled)
    throws NoSuchAlgorithmException
  {
    super(maxNameLen, maxUnmangled, s_cChars);
  }
}
