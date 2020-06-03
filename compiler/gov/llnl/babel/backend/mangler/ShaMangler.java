//
// File:        NameManger.java
// Package:     gov.llnl.babel.backend
// Copyright:   (c) 2002 Lawrence Livermore National Security, LLC
// Revision:    @(#) $Revision: 6171 $
// Date:        $Date: 2007-10-08 16:39:28 -0700 (Mon, 08 Oct 2007) $
// Description: Name mangler
// 

package gov.llnl.babel.backend.mangler;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

/**
 * This class uses the SHA message digest algorithm to generate
 * the mangled part of a shortened identifier. The message digest of the
 * symbol, method and suffix is converted into a sequence of allowable
 * characters. The mangled part of the name may appear random, but it is
 * completely deterministic and reproducible.
 */
public class ShaMangler implements NameMangler {

  /**
   * This encoding is used when converting Java Unicode strings into
   * a sequence of bytes that can be fed to the message digest.
   */
  private static String s_STANDARD_ENCODING = "ISO-8859-1";
  
  /**
   * Instance of a message digest class.  This is used to create the mangled
   * part.
   */
  private MessageDigest d_digest;

  /**
   * The maximum total length of the string (including unmangled and mangled
   * parts).
   */
  private int d_maxLength;

  /**
   * The maximum length of the unmangled part of the final name. This 
   * must be strictly less than {@link #d_maxLength}.
   */
  private int d_maxUnmangled;

  /**
   * This hold the character set that the manglers allowed to use.
   */
  private char[] d_charSet;

  /**
   * This holds the size of {@link d_charSet} as a {@link
   * java.math.BigInteger}.
   */
  private BigInteger d_charSetSize;

  /**
   * Create an instance of the <code>ShaMangler</code>.
   * 
   * @param maxNameLen   this is the maximum number of characters that
   *                     a name may contain. For Fortran 90 and strict
   *                     ANSI C, this would be 31.  For strict FORTRAN
   *                     77, this would be 6.
   * @param maxUnmangled this indicates how many characters out of
   *                     <code>maxNameLen</code> should be used for 
   *                     unmangled content.
   * @param charSet      this provides the complete set of allowable
   *                     characters that are considered distinct by 
   *                     the language compiler.
   * @exception java.security.NoSuchAlgorithmException
   *   this will only be thrown if the SHA message digest algorithm
   *   is not available.
   */
  public ShaMangler(int maxNameLen,
                    int maxUnmangled,
                    char[] charSet)
    throws NoSuchAlgorithmException
  {
    d_charSet = (char [])charSet.clone();
    d_maxLength = maxNameLen;
    if (d_maxLength < 0) {
      d_maxLength = 0;
    }
    d_maxUnmangled = maxUnmangled;
    if (d_maxUnmangled < 0) {
      d_maxUnmangled = 0;
    }
    d_charSetSize = BigInteger.valueOf(d_charSet.length);
    d_digest = MessageDigest.getInstance("SHA");
  }

  /**
   * Figure out how much of the extendable's full name can be used
   * given the maximum length. This will only break at a period.
   * 
   * @param symbol    the symbol name
   * @param maxLength the maximum number of characters available for the
   *                  symbol part of the name.
   * @return
   */
  private String truncateSymbol(String symbol, 
                                int maxLength)
  {
    if (maxLength > 0) {
      StringBuffer result = new StringBuffer(maxLength);
      StringTokenizer tok = new StringTokenizer(symbol, ".");
      final int len = tok.countTokens();
      String [] tokens = new String[len];
      int i;
      for(i = 0; i < len; ++i) {
        tokens[i] = tok.nextToken();
      }
      for(i = len - 1; i >= 0 && 
            (maxLength - tokens[i].length() - 1) >= 0; --i) {
        maxLength -= (tokens[i].length() + 1);
      }
      // now i is one less than the first symbol that can
      // be included
      if (++i < len) {
        result.append(tokens[i++]);
        for( ; i < len; ++i) {
          result.append('_').append(tokens[i]);
        }
      }
      else {
        // return part of the class/interface name
        result.append(tokens[len-1].substring(0, maxLength));
      }
      return result.toString();
    }
    return "";
  }

  /**
   * Generate the message digest value as a {@link java.math.BigInteger} 
   * using the SHA message digest algorithm.
   *
   * @param symbol the symbol name
   * @param method the method full name
   * @param suffix the suffix
   * @return the message digest value as a {@link java.math.BigInteger}.
   * This value is never <code>null</code>.
   * @exception java.io.UnsupportedEncodingException
   *   this should never happen because ISO-8859-1 is always supposed
   *   to be available.
   */
  private BigInteger calculateDigest(String symbol,
                                     String method,
                                     String array,
                                     String suffix)
    throws UnsupportedEncodingException
  {
    BigInteger result = null;
    byte [] digest = null;
    d_digest.reset();
    d_digest.update(symbol.getBytes(s_STANDARD_ENCODING));
    d_digest.update(method.getBytes(s_STANDARD_ENCODING));
    d_digest.update(array.getBytes(s_STANDARD_ENCODING));
    d_digest.update(suffix.getBytes(s_STANDARD_ENCODING));
    digest = d_digest.digest();
    result = new BigInteger(1,digest);
    return result;
  }

  /**
   * Create the short name from the <code>symbol</code>, <code>method</code>
   * and <code>suffix</code>. This mangler uses the SHA message digest to
   * generate the extra characters to attempt to avoid a symbol conflict.
   *
   * @param symbol  the fully qualified name of the class or interface
   *                holding the method. This name has periods separating
   *                the different package components and class name.
   * @param method  this is the full name of the method. For overloaded
   *                methods, this should be the full method name.
   * @param suffix  this suffix is used to designate what kind of 
   *                kind of function name is being generated (i.e.,
   *                stub, skel, or impl).
   * @return a string whose length is less than or equal to the maximum
   * allowed length. The name will have an unmangled part and a mangled
   * part (assuming it needs to be shortened).
   * @exception java.io.UnsupportedEncodingException
   *   this exception should never occur because the ISO-8859-1 encoding
   *   used by this method is supposed to be available on all JVMs.
   */
  public String shortName(String symbol, String method, String suffix)
    throws UnsupportedEncodingException
  {
    StringBuffer result = new StringBuffer(d_maxLength);
    if (symbol.length() + method.length() + suffix.length() >= d_maxLength) {
      BigInteger digest = calculateDigest(symbol, method, "", suffix);
        
      symbol = 
        truncateSymbol(symbol,
                       d_maxUnmangled - method.length() - suffix.length() - 1);
      method = method.substring
        (0, Math.max(0, Math.min(method.length(),
                                 d_maxUnmangled - suffix.length())));
      suffix = suffix.substring(0, Math.min(suffix.length(), d_maxUnmangled));
      result.append(symbol);
      if (symbol.length() > 0) {
        result.append('_');
      }
      result.append(method);
      if ((symbol.length() == 0) && (suffix.indexOf('_') < 0) &&
          (method.indexOf('_') < 0)) {
        result.append('_');
      }
      int mangledChars = d_maxLength - suffix.length() - result.length();
      while (mangledChars-- > 0) {
        BigInteger[] next = digest.divideAndRemainder(d_charSetSize);
        digest = next[0];
        result.append(d_charSet[next[1].intValue()]);
      }
      result.append(suffix);
    }
    else {
      result.append(symbol.replace('.','_'))
        .append('_').append(method).append(suffix);
    }
    return result.toString();
  }

  /**
   * Create the short name from the <code>symbol</code>, <code>method</code>
   * and <code>suffix</code>. This mangler uses the SHA message digest to
   * generate the extra characters to attempt to avoid a symbol conflict.
   *
   * @param symbol  the fully qualified name of the class or interface
   *                holding the method. This name has periods separating
   *                the different package components and class name.
   * @param method  this is the full name of the method. For overloaded
   *                methods, this should be the full method name.
   * @param suffix  this suffix is used to designate what kind of 
   *                kind of function name is being generated (i.e.,
   *                stub, skel, or impl).
   * @return a string whose length is less than or equal to the maximum
   * allowed length. The name will have an unmangled part and a mangled
   * part (assuming it needs to be shortened).
   * @exception java.io.UnsupportedEncodingException
   *   this exception should never occur because the ISO-8859-1 encoding
   *   used by this method is supposed to be available on all JVMs.
   */
  public String shortArrayName(String symbol, String method, String suffix)
    throws UnsupportedEncodingException
  {
    StringBuffer result = new StringBuffer(d_maxLength);
    if (symbol.length() + method.length() + 7 + suffix.length() >=
        d_maxLength) {
      BigInteger digest = calculateDigest(symbol, method, "_ary_", suffix);
        
      symbol = 
        truncateSymbol(symbol,
                       d_maxUnmangled - method.length() - suffix.length() - 8);
      method = method.substring
        (0, Math.max(0, Math.min(method.length(),
                                 d_maxUnmangled - suffix.length()-
                                 ((symbol.length() > 0) ?  5 : 4))));
      suffix = suffix.substring(0, Math.min(suffix.length(), d_maxUnmangled));
      result.append(symbol);
      if (symbol.length() > 0) {
        result.append("_ary_");
      }
      else {
        result.append("ary_");
      }
      result.append(method);
      int mangledChars = d_maxLength - suffix.length() - result.length();
      while (mangledChars-- > 0) {
        BigInteger[] next = digest.divideAndRemainder(d_charSetSize);
        digest = next[0];
        result.append(d_charSet[next[1].intValue()]);
      }
      result.append(suffix);
    }
    else {
      result.append(symbol.replace('.','_'))
        .append("__array_").append(method).append(suffix);
    }
    return result.toString();
  }

  public String shortName(String symbol, String suffix)
    throws UnsupportedEncodingException
  {
    StringBuffer result = new StringBuffer(d_maxLength);
    if (symbol.length() + suffix.length() >= d_maxLength) {
      BigInteger digest = calculateDigest(symbol, "", "", suffix);
        
      symbol = truncateSymbol(symbol, d_maxUnmangled - suffix.length());
      suffix = suffix.substring(0, Math.min(suffix.length(), d_maxUnmangled));
      result.append(symbol);
      if ((symbol.length() == 0) && (suffix.indexOf('_') < 0)) {
        result.append('_');
      }
      int mangledChars = d_maxLength - suffix.length() - result.length();
      while (mangledChars-- > 0) {
        BigInteger[] next = digest.divideAndRemainder(d_charSetSize);
        digest = next[0];
        result.append(d_charSet[next[1].intValue()]);
      }
      result.append(suffix);
    }
    else {
      result.append(symbol.replace('.','_')).append(suffix);
    }
    return result.toString();
  }
}
