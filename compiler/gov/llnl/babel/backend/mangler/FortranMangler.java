//
// File:        FortranMangler.java
// Package:     gov.llnl.babel.backend
// Copyright:   (c) 2002 Lawrence Livermore National Security, LLC
// Revision:    @(#) $Revision: 6171 $
// Date:        $Date: 2007-10-08 16:39:28 -0700 (Mon, 08 Oct 2007) $
// Description: Name mangler for FORTRAN 77/90
// 

package gov.llnl.babel.backend.mangler;
import java.security.NoSuchAlgorithmException;

/**
 * This class provides a {@link gov.llnl.babel.backend.mangler.NameMangler}
 * for the FORTRAN 77, 90, 95 and presumably future FORTRAN standards.
 * The main difference between FortranMangler and {@link
 * gov.llnl.babel.backend.mangler.ShaMangler} is the FortranMangler provides
 * the allowable FORTRAN character set.
 */
public class FortranMangler extends ShaMangler {
  /**
   * This is the set of allowable characters in FORTRAN.  There are
   * 37 characters available in standard FORTRAN names.
   */
  private static final char[] s_fortranChars = {
    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
    'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '_'
  };

  /**
   * Create a name mangler for FORTRAN. This constructor provides the
   * FORTRAN character set.
   *
   * @param maxNameLen   the maximum allowable number of characters in a 
   *                     name. Typically, this is 31 for FORTRAN 90/95,
   *                     and it is set by the FORTRAN standards
   *                     committee.
   * @param maxUnmangled the number of characters out of
   *                     <code>maxNameLen</code> that should be used
   *                     for unmangled content.
   * @exception java.security.NoSuchAlgorithmException
   *    this is thrown when the SHA message digest is unavailable. Sun's
   *    specifications says that it should be available.
   */
  public FortranMangler(int maxNameLen,
                        int maxUnmangled)
    throws NoSuchAlgorithmException
  {
    super(maxNameLen, maxUnmangled, s_fortranChars);
  }

  public static void main(String [] args)
  {
    try {
      FortranMangler fm = new FortranMangler(31, 21);
      System.out.println("normal method = " + 
                         fm.shortName(args[0], args[1], args[2]));
      System.out.println("array method = " +
                         fm.shortArrayName(args[0], args[1], args[2]));
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}
