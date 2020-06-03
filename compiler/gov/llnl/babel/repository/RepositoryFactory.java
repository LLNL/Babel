//
// File:        RepositoryFactory.java
// Package:     gov.llnl.babel.repository
// Revision:    @(#) $Id: RepositoryFactory.java 7188 2011-09-27 18:38:42Z adrian $
// Description: singleton factory for creating repository interfaces
//
// Copyright (c) 2000-2001, Lawrence Livermore National Security, LLC
// Produced at the Lawrence Livermore National Laboratory.
// Written by the Components Team <components@llnl.gov>
// UCRL-CODE-2002-054
// All rights reserved.
// 
// This file is part of Babel. For more information, see
// http://www.llnl.gov/CASC/components/. Please read the COPYRIGHT file
// for Our Notice and the LICENSE file for the GNU Lesser General Public
// License.
// 
// This program is free software; you can redistribute it and/or modify it
// under the terms of the GNU Lesser General Public License (as published by
// the Free Software Foundation) version 2.1 dated February 1999.
// 
// This program is distributed in the hope that it will be useful, but
// WITHOUT ANY WARRANTY; without even the IMPLIED WARRANTY OF
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the terms and
// conditions of the GNU Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public License
// along with this program; if not, write to the Free Software Foundation,
// Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

package gov.llnl.babel.repository;

import gov.llnl.babel.Context;
import gov.llnl.babel.repository.FileRepository;
import gov.llnl.babel.repository.Repository;
import gov.llnl.babel.repository.RepositoryException;
import gov.llnl.babel.repository.WebRepository;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * <code>RepositoryFactory</code> is a singleton factory that manages the
 * creation of repository interfaces based on a repository URI.  A URI that
 * starts with "http:" or "https:" specifies a web repository.  A URI that
 * starts with "file:" creates a file repository.  All other URIs are assumed
 * to be file repositories.
 */
public class RepositoryFactory {
  private Context d_context;

   /**
    * Create a new instance of the repository factory.  Although singleton
    * classes do not typically define a public constructor, this version does
    * so to support multiple repository factories in the same application.
    * Most implementations, however, will not directly create a factory
    * through the constructor and will instead use the singleton functions
    * <code>getInstance</code> and <code>setInstance</code>.
    */
   public RepositoryFactory(Context context) {
     d_context = context;
   }

   /**
    * Create a new repository object based on the URI.  Web repositories
    * are created using a URL starting with "http:" or "https:" and file
    * repositories are created using a URL starting with "file:" or any
    * other prefix.  A <code>RepositoryException</code> is thrown if any
    * error is detected in creating the repository.
    */
   public Repository createRepository(String uri) throws RepositoryException {
      Repository repository = null;
      try {
         URL url = new URL(uri);
         String protocol = url.getProtocol();
         if ((protocol == null) || (protocol.equals(""))) {
            repository = new FileRepository(uri, d_context);
         } else if (protocol.equals("http") || protocol.equals("https")) {
            repository = new WebRepository(uri, d_context);
         } else if (protocol.equals("file")) {
            repository = new FileRepository(url.getFile(), d_context);
         } else {
            repository = new FileRepository(uri, d_context);
         }
      } catch (MalformedURLException ex) {
         repository = new FileRepository(uri, d_context);
      }
      return repository;
   }
}
