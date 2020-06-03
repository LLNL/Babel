//
// File:        ContextAware.java
// Package:     gov.llnl.babel.backend
// Copyright:   (c) 2007 Lawrence Livermore National Security, LLC
// Revision:    @(#) $Revision: 6196 $
// Date:        $Date: 2007-10-24 16:42:37 -0700 (Wed, 24 Oct 2007) $
// Description: Interface for extension modules that are context aware
// 

package gov.llnl.babel.backend;

import gov.llnl.babel.Context;

/**
 * Babel extensions implementing this type will be told the
 * current context.
 */
public interface ContextAware {
  /**
   * Routine to notify extensions of the context.
   */
  public void setContext(Context context);
}
