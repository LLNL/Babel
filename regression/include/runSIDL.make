## -*- Makefile -*-
## File:        make.runSIDL
## Package:     Generic Makefile.am support for running sidl drivers
## Revision:    $Revision: 7188 $
## Modified:    $Date: 2011-09-27 11:38:42 -0700 (Tue, 27 Sep 2011) $
## Description: automake makefile for sidl driver regression tests
##
## Copyright (c) 2003, Lawrence Livermore National Security, LLC.
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

check_SCRIPTS = runSIDL.sh

BABEL=`if test "$(BABELDIR)" != "$(top_builddir)/bin" ; then echo babel | sed '$(transform)' ; else echo babel; fi`
BABELDIR=$(top_builddir)/bin

REPODIR=./xml
REPO2DIR=./xml2
SIDLDIR=./sidl
CLEANFILES=babel-stamp stamp-installcheck babel.make.package \
	babel.make.depends sidl-stamp repo-stamp

babel-stamp: $(check_SCRIPTS) $(REPODIR)/$(GENFILENAME) sidl-stamp
	mkdir -p $(REPO2DIR)
	$(SHELL) $(BABELDIR)/$(BABEL) --text=xml $(BABELEXTRA) \
		--output-directory=$(REPO2DIR)                          \
		$(REPODIR)/$(GENFILENAME)
	$(MAKE) $(AM_MAKEFLAGS)
	chmod a+x $(check_SCRIPTS)
	touch babel-stamp

sidl-stamp : $(SIDLFILE)
	mkdir -p $(SIDLDIR)
	$(SHELL) $(BABELDIR)/$(BABEL) --text=sidl $(BABELEXTRA) \
		--output-directory=$(SIDLDIR) $(SIDLFILE)
	touch sidl-stamp

repo-stamp : $(SIDLFILE)
	mkdir -p $(REPODIR)
	$(SHELL) $(BABELDIR)/$(BABEL) --text=xml $(BABELEXTRA) \
		--output-directory=$(REPODIR) $(SIDLFILE)
	touch repo-stamp

$(REPODIR)/$(GENFILENAME): repo-stamp
	$(SHELL) $(BABELDIR)/$(BABEL) --text=sidl $(BABELEXTRA) \
		--repository-path=$(REPODIR)                 \
		--output-directory=$(REPODIR) $(TESTPKG)

clean-local:
	rm -rf $(REPODIR) $(REPO2DIR) $(SIDLDIR) $(EXTRACLEAN) *.testresult
	test "X$(srcdir)" = "X." || rm -f $(check_SCRIPTS)

check-local:
	@if test -f stamp-installcheck; then		\
	  echo $(MAKE) $(AM_MAKEFLAGS) clean;		\
	  $(MAKE) $(AM_MAKEFLAGS) clean;		\
	fi
	$(MAKE) $(AM_MAKEFLAGS) babel-stamp 


installcheck-local:
	@if test ! -f stamp-installcheck; then		\
	  echo $(MAKE) $(AM_MAKEFLAGS) clean;		\
	  $(MAKE) $(AM_MAKEFLAGS) clean;		\
	fi
	$(MAKE) $(AM_MAKEFLAGS)				\
	  BABELDIR=$(bindir) babel-stamp 
	touch stamp-installcheck


