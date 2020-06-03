##
## File:        Makefile.am 
## Package:     Babel bin directory
## Revision:    $Revision: 7188 $
## Modified:    $Date: 2011-09-27 11:38:42 -0700 (Tue, 27 Sep 2011) $
## Description: automake makefile for the Babel binary directory
## Copyright (c) 2000-2001, The Regents of the University of Calfornia.
## Produced at the Lawrence Livermore National Laboratory.
## Written by the Components Team <components@llnl.gov>
## UCRL-CODE-2002-054
## All rights reserved.
## 
## This file is part of Babel. For more information, see
## http://www.llnl.gov/CASC/components/. Please read the COPYRIGHT file
## for Our Notice and the LICENSE file for the GNU Lesser General Public
## License.
## 
## This program is free software; you can redistribute it and/or modify it
## under the terms of the GNU Lesser General Public License (as published by
## the Free Software Foundation) version 2.1 dated February 1999.
## 
## This program is distributed in the hope that it will be useful, but
## WITHOUT ANY WARRANTY; without even the IMPLIED WARRANTY OF
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the terms and
## conditions of the GNU Lesser General Public License for more details.
## 
## You should have received a copy of the GNU Lesser General Public License
## along with this program; if not, write to the Free Software Foundation,
## Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA




bin_SCRIPTS = babel babel-config babel-libtool babel-cc babel-cxx babel-f77 babel-f90
revisiondir=$(datadir)/$(PACKAGE)-$(VERSION)
revision_DATA=revision.txt
noinst_SCRIPTS = javacc
EXTRA_DIST = javacc copy.sh remove.sh generate_scl generate_pyscl
DISTCLEANFILES=babel-libtool

babel-libtool: ../libtool
	cp ../libtool babel-libtool

revision.txt:
	-$(top_srcdir)/svnrevision.sh --topdir=$(top_srcdir) \
	  --srcfile=$(srcdir)/revision.txt \
	  --outfile=$(builddir)/revision.txt

dist-hook: revision.txt
	cp $< $(distdir)

clean-local:
	if test "X$(srcdir)" != "X."; then \
	  rm -f revision.txt ; \
	fi
