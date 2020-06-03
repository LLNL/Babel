//
// File:        NameMangler.java
// Package:     gov.llnl.babel.backend.mangler
// Copyright:   (c) 2002 Lawrence Livermore National Security, LLC
// Revision:    @(#) $Revision: 6171 $
// Date:        $Date: 2007-10-08 16:39:28 -0700 (Mon, 08 Oct 2007) $
// Description: An interface for name mangler.
// 
package gov.llnl.babel.backend.mangler;
import java.io.UnsupportedEncodingException;

/**
 * When symbol names are too long for a language, a <code>NameMangler</code>
 * maps long names onto shorter names that are <strong>unlikely</strong> to
 * conflict with other symbol names. When mapping a set of long names onto
 * a set of shorter names, there is a nonzero probably of a symbol conflict.
 */
public interface NameMangler {

  /**
   * Convert the long name for the method into a shorter name. The fullname
   * for the method is the concatenation of <code>symbol</code>, an
   * underscore, <code>method</code>, and <code>suffix</code> with period
   * characters converted to underscore characters. The name mangler will
   * attempt to preserve as much of the fullname. First priority is given to
   * maintaining suffix part of the name, second priority is
   * given to maintaining the method name, and third priority is given
   * maintaining the symbol part of the name.
   *
   * The returned name is guaranteed to have an underscore in it.
   *
   * @param symbol  the fullname of the {@link
   *                gov.llnl.babel.symbols.Symbol}. This name has period
   *                characters between the various parts of the fullname.
   * @param method  the name of the method {@link
   *                gov.llnl.babel.symbols.Method}.  This name has no
   *                periods in it.
   * @param suffix  a string that is tagged onto the end of the method.
   *                Implementation methods may have "_i" as a suffix to
   *                keep them distinct from stubs and skeletons.
   * @exception java.io.UnsupportedEncodingException 
   *                in generating the mangled name, the mangler may need
   *                to convert symbols to a standard byte encoding.
   */
  public String shortName(String symbol, String method, String suffix)
    throws UnsupportedEncodingException;

  /**
   * Convert the long name for the array method into a shorter name. The
   * fullname for the method is the concatenation of <code>symbol</code>, an
   * underscore, "array", underscore, <code>method</code>, and
   * <code>suffix</code> with period characters converted to underscore
   * characters. The name mangler will attempt to preserve as much of the
   * fullname. First priority is given to maintaining suffix part of the
   * nam, second priority is given to maintaining the method name, and
   * third priority is given maintaining the symbol part of the name.
   *
   * The returned name is guaranteed to have an underscore in it.
   *
   * @param symbol  the fullname of the {@link
   *                gov.llnl.babel.symbols.Symbol}. This name has period
   *                characters between the various parts of the fullname.
   * @param method  the name of the method {@link
   *                gov.llnl.babel.symbols.Method}.  This name has no
   *                periods in it.
   * @param suffix  a string that is tagged onto the end of the method.
   *                Implementation methods may have "_i" as a suffix to
   *                keep them distinct from stubs and skeletons.
   * @exception java.io.UnsupportedEncodingException 
   *                in generating the mangled name, the mangler may need
   *                to convert symbols to a standard byte encoding.
   */
  public String shortArrayName(String symbol, String method, String suffix)
    throws UnsupportedEncodingException;

  /**
   * Convert a type name to a short length.
   */
  public String shortName(String symbol, String suffix)
    throws UnsupportedEncodingException;
}
