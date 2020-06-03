##
## File:        Makefile.am
## Package:     Babel regression checks
## Revision:    $Revision: 7416 $
## Modified:    $Date: 2011-12-13 14:21:25 -0800 (Tue, 13 Dec 2011) $
## Description: automake makefile for output regression library
##
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

check_LTLIBRARIES = libOutput.la

BABEL=`if test "$(BABELDIR)" != "$(top_builddir)/bin" ; then echo babel | sed '$(transform)' ; else echo babel; fi`
BABELDIR    = $(top_builddir)/bin
INCLUDEDIR  = $(top_builddir)/runtime/sidl
if WITH_SIDLX
INCLUDEDIRX  = $(top_builddir)/runtime/sidlx
LIBSIDLX     = $(top_builddir)/runtime/sidlx/libsidlx.la
LIBSIDL      =
INSTALLEDSIDL=
INSTALLEDSIDLX=$(libdir)/libsidlx.la
else
INCLUDEDIRX  = $(top_builddir)/runtime/sidl
LIBSIDLX     =
LIBSIDL      = $(top_builddir)/runtime/sidl/libsidl.la
INSTALLEDSIDL=$(libdir)/libsidl.la
INSTALLEDSIDLX=
endif
INCLUDEDIR2 = .
PUREBABELGEN=$(STUBSRCS) $(RSTUBSRCS) $(SKELSRCS) $(IORSRCS) \
	$(IORHDRS) $(STUBHDRS)
BABELGEN=$(IMPLSRCS) $(IMPLHDRS)
CLEANFILES=$(PUREBABELGEN) libOutput.scl babel-stamp babel-temp \
	core stamp-installcheck babel.make.package babel.make.depends

libOutput_la_SOURCES = $(BABELGEN)
nodist_libOutput_la_SOURCES = $(PUREBABELGEN)
libOutput_la_LIBADD       = $(LIBSIDLX) $(LIBSIDL)
libOutput_la_LDFLAGS      = -no-undefined -rpath `pwd`/.libs \
			  -release @VERSION@

EXTRA_DIST = babel.make synch_babel.make
AM_CPPFLAGS   = -I. -I$(INCLUDEDIR) -I$(INCLUDEDIRX) -I$(INCLUDEDIR2)

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

SIDLFILE = $(srcdir)/../output.sidl

babel-stamp : $(SIDLFILE)
	@rm -f babel-temp
	@touch babel-temp
	$(SHELL) $(BABELDIR)/$(BABEL) --vpath="$(srcdir)" --server=C \
	  $(SIDLFILE)
	@mv -f babel-temp $@

libOutput.scl : $(IORSRCS)
	$(SHELL) $(top_srcdir)/bin/generate_scl libOutput.scl \
	   `pwd`/libOutput.la $(IORSRCS)

clean-local:
	rm -f *.a *.so
	test "X$(srcdir)" = "X." || rm -f $(IMPLSRCS) $(IMPLHDRS) babel.make \
	       synch_babel.make

update-babel-make: babel-stamp
	if test "X$(srcdir)" != "X."; then \
	   cp babel.make $(srcdir); \
	fi

check-local:
	@if test -f stamp-installcheck; then		\
	  echo $(MAKE) $(AM_MAKEFLAGS) clean;		\
	  $(MAKE) $(AM_MAKEFLAGS) clean;		\
	fi
	$(MAKE) $(AM_MAKEFLAGS) $(check_LTLIBRARIES) libOutput.scl

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
	  LIBSIDLX=$(INSTALLEDSIDLX) $(check_LTLIBRARIES) \
	  libOutput.scl
	touch stamp-installcheck 

include $(srcdir)/babel.make

# Make sure Makefile.in is newer than babel.make
dist-hook:
	touch -c $(distdir)/Makefile.in
