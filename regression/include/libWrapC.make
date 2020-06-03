## -*- Makefile -*-
## File:        make.wrapC
## Package:     Babel regression checks
## Revision:    $Revision: 5349 $
## Modified:    $Date: 2006-04-06 10:30:10 -0700 (Thu, 06 Apr 2006) $
## Description: automake makefile for C regression tests
##
## Copyright (c) 2000-2002, Lawrence Livermore National Security, LLC.
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

OUTPUTSIDL=$(srcdir)/../../output/output.sidl

check_LTLIBRARIES = libImpl.la

BABEL=`if test "$(BABELDIR)" != "$(top_builddir)/bin" ; then echo babel | sed '$(transform)' ; else echo babel; fi`
BABELDIR    = $(top_builddir)/bin
INCLUDEDIR  = $(top_builddir)/runtime/sidl
if WITH_SIDLX
INCLUDEDIRX  = $(top_builddir)/runtime/sidlx
LIBSIDLX     = $(top_builddir)/runtime/sidlx/libsidlx.la
LIBSIDL      =
INSTALLEDSIDLX = $(libdir)/libsidlx.la
INSTALLEDSIDL=
else
INCLUDEDIRX  = $(INCLUDEDIR)
LIBSIDLX     = 
LIBSIDL     = $(top_builddir)/runtime/sidl/libsidl.la
INSTALLEDSIDLX = 
INSTALLEDSIDL=$(libdir)/libsidl.la
endif
INCLUDEDIR2 = .
LIBSYNC     = ../../output/libC/libOutput.la

PUREBABELGEN = $(STUBSRCS) $(RSTUBSRCS) $(SKELSRCS) $(IORSRCS) \
	$(IORHDRS) $(STUBHDRS) $(synch_STUBSRCS) $(synch_STUBHDRS) \
	$(synch_RSTUBSRCS) $(synch_IORHDRS) $(client_STUBSRCS) $(client_STUBHDRS) \
	$(client_RSTUBSRCS) $(client_IORHDRS)
BABELGEN = $(IMPLSRCS) $(IMPLHDRS)
CLEANFILES=$(PUREBABELGEN) $(EXTRACLEAN) libImpl.scl babel-stamp babel-temp \
	stamp-installcheck core \
	babel.make.package  synch_babel.make.package \
	babel.make.depends synch_babel.make.depends \
	client_babel.make.package client_babel.make.depends

nodist_libImpl_la_SOURCES = $(PUREBABELGEN) $(SOURCE_EXTRAS)
libImpl_la_SOURCES = $(BABELGEN) $(SOURCE_EXTRAS)
libImpl_la_LIBADD         = $(LIBSIDLX) $(LIBSIDL) $(LIB_EXTRAS)
libImpl_la_LDFLAGS      = -no-undefined -rpath `pwd`/.libs \
			  -release @VERSION@ -module

EXTRA_DIST = babel.make synch_babel.make client_babel.make 
AM_CPPFLAGS = -I. -I$(INCLUDEDIR) -I$(INCLUDEDIRX) -I$(INCLUDEDIR2)


$(BABELGEN) $(PUREBABELGEN) : babel-stamp
## cf. http://www.gnu.org/software/automake/manual/automake.html#Multiple-Outputs
## Recover from the removal of $@
	@if test -f $@; then :; else \
	  trap 'rm -rf babel.lock babel-stamp' 1 2 13 15; \
## mkdir is a portable test-and-set
	  if mkdir babel.lock 2>/dev/null; then \
## This code is being executed by the first process.
	    rm -f babel-stamp; \
	    $(MAKE) $(AM_MAKEFLAGS) babel-stamp; \
	    result=$$?; rm -rf babel.lock; exit $$result; \
	  else \
## This code is being executed by the follower processes.
## Wait until the first process is done.
	    while test -d babel.lock; do sleep 1; done; \
## Succeed if and only if the first process succeeded.
	    test -f babel-stamp; \
	  fi; \
	fi

babel-stamp : $(SIDLFILE) $(OUTPUTSIDL)
	@rm -f babel-temp
	@touch babel-temp
	$(SHELL) $(BABELDIR)/$(BABEL) --vpath="$(srcdir)" --server=C \
	  $(BABELEXTRA) $(SERVEREXTRA)
	$(SHELL) $(BABELDIR)/$(BABEL) --client=C --make-prefix=synch_  \
	  $(BABELEXTRA) $(OUTPUTSIDL)
	$(SHELL) $(BABELDIR)/$(BABEL) --client=C --make-prefix=client_  \
	  $(BABELEXTRA) $(CLIENTEXTRA)
	@mv babel-temp $@

libImpl.scl : $(IORSRCS)
	$(SHELL) $(top_srcdir)/bin/generate_scl libImpl.scl `pwd`/libImpl.la \
	   $(IORSRCS)

update-babel-make: babel-stamp
	if test "X$(srcdir)" != "X."; then \
	   cp babel.make synch_babel.make client_babel.make $(srcdir); \
	fi

clean-local:
	test "X$(srcdir)" = "X." || rm -f $(IMPLSRCS) $(IMPLHDRS) babel.make \
	       synch_babel.make client_babel.make

check-local:
	@if test -f stamp-installcheck; then		\
	  echo $(MAKE) $(AM_MAKEFLAGS) clean;		\
	  $(MAKE) $(AM_MAKEFLAGS) clean;		\
	fi
	$(MAKE) $(AM_MAKEFLAGS) $(check_LTLIBRARIES) libImpl.scl

installcheck-local:
	@if test ! -f stamp-installcheck; then		\
	  echo $(MAKE) $(AM_MAKEFLAGS) clean;		\
	  $(MAKE) $(AM_MAKEFLAGS) clean;		\
	fi
	$(MAKE) $(AM_MAKEFLAGS)				\
	  BABELDIR=$(bindir)				\
	  INCLUDEDIR=$(includedir)			\
	  INCLUDEDIRX=$(includedir)			\
	  INCLUDEDIR2=$(exec_prefix)/include		\
	  LIBSIDL=$(INSTALLEDSIDL)			\
	  LIBSIDLX=$(INSTALLEDSIDLX) $(check_LTLIBRARIES) libImpl.scl
	touch stamp-installcheck

# Make sure Makefile.in is newer than babel.make
dist-hook:
	touch -c $(distdir)/Makefile.in
