## -*- Makefile -*-
## File:        make.runC
## Package:     Babel regression checks
## Revision:    $Revision: 5359 $
## Modified:    $Date: 2006-04-06 16:53:03 -0700 (Thu, 06 Apr 2006) $
## Description: automake makefile for run regression tests
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

check_PROGRAMS    = $(STATIC_PROGS) $(SHARED_PROGS)
check_SCRIPTS     = runAll.sh
check_LTLIBRARIES = libBrtClient.la

BABEL=`if test "$(BABELDIR)" != "$(top_builddir)/bin" ; then echo babel | sed '$(transform)' ; else echo babel; fi`
BABELDIR    = $(top_builddir)/bin
INCLUDEDIR  = $(top_builddir)/runtime/sidl
INCLUDEDIR2 = .
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
LIBSYNC     = ../../output/libC/libOutput.la
PUREBABELGEN = $(STUBSRCS) $(RSTUBSRCS) $(SKELSRCS) $(IORSRCS) \
	$(IORHDRS) $(STUBHDRS) $(synch_STUBSRCS) $(synch_STUBHDRS) \
	$(synch_RSTUBSRCS) $(synch_IORHDRS) $(client_STUBSRCS) $(client_STUBHDRS) \
	$(client_RSTUBSRCS) $(client_IORHDRS)
BABELGEN = $(IMPLSRCS) $(IMPLHDRS)
CLEANFILES= $(PUREBABELGEN) $(EXTRACLEAN) libClient.scl babel-stamp babel-temp \
	stamp-installcheck core \
	babel.make.package  synch_babel.make.package \
	babel.make.depends synch_babel.make.depends \
	client_babel.make.package client_babel.make.depends

if SUPPORT_STATIC
if SUPPORT_FORTRAN77
  BABEL_STATIC_F77 = runC2F77
else
  BABEL_STATIC_F77 = 
endif
if SUPPORT_FORTRAN90
  BABEL_STATIC_F90 = runC2F90
else
  BABEL_STATIC_F90 = 
endif
if SUPPORT_FORTRAN03
  BABEL_STATIC_F03 = runC2F03
else
  BABEL_STATIC_F03 = 
endif
STATIC_PROGS = runC2C runC2Cxx $(BABEL_STATIC_F77) $(BABEL_STATIC_F90) $(BABEL_STATIC_F03)

# This little snippet forces automake to define $CXXLINK
EXTRA_PROGRAMS  = automake_hack
automake_hack_SOURCES = 
nodist_EXTRA_automake_hack_SOURCES = automake_hack.cc

runC2C_SOURCES        = $(TESTFILE) $(SOURCE_EXTRAS)
runC2C_LDFLAGS        = -static-libtool-libs -export-dynamic
runC2C_LDADD          = libBrtClient.la ../libC/libImpl.la \
			$(LIBSYNC) $(LIBSIDL) $(LIBSIDLX)

runC2Cxx_SOURCES      = $(TESTFILE) $(SOURCE_EXTRAS)
runC2Cxx_LDFLAGS      = -static-libtool-libs -export-dynamic
runC2Cxx_LDADD        = libBrtClient.la ../libCxx/libImpl.la \
			$(LIBSYNC) $(LIBSIDL) $(LIBSIDLX)
runC2Cxx_LINK	      = $(CXXLINK) $(runC2Cxx_LDFLAGS)

if SUPPORT_FORTRAN77
runC2F77_SOURCES      = $(TESTFILE) $(SOURCE_EXTRAS)
runC2F77_LDFLAGS      = -static-libtool-libs -export-dynamic
runC2F77_LDADD        = libBrtClient.la ../libF77/libImpl.la \
			$(LIBSYNC) $(LIBSIDL) $(FLIBS) $(LIBSIDLX)
endif

if SUPPORT_FORTRAN90
runC2F90_SOURCES      =  $(TESTFILE) $(SOURCE_EXTRAS)
runC2F90_LDFLAGS      = -static-libtool-libs -export-dynamic
runC2F90_LDADD        = libBrtClient.la ../libF90/libImpl.la \
			$(LIBSYNC) $(LIBSIDL) $(FCLIBS) $(LIBSIDLX)
endif

if SUPPORT_FORTRAN03
runC2F03_SOURCES      =  $(TESTFILE) $(SOURCE_EXTRAS)
runC2F03_LDFLAGS      = -static-libtool-libs -export-dynamic
runC2F03_LDADD        = libBrtClient.la ../libF03/libImpl.la \
			$(LIBSYNC) $(LIBSIDL) $(FCLIBS) $(LIBSIDLX)
endif

endif

if SUPPORT_SHARED
SHARED_PROGS          = runAll
runAll_SOURCES        = $(TESTFILE) $(SOURCE_EXTRAS)
runAll_LDFLAGS	      = -dynamic
runAll_LDADD          = libBrtClient.la $(LIBSYNC) $(LIBSIDL) $(LIBSIDLX)
endif

$(TESTFILE:.c=.o) : $(IORHDRS) $(STUBHDRS) $(synch_IORHDRS) $(synch_STUBHDRS) \
	$(client_IORHDRS) $(client_STUBHDRS)

nodist_libBrtClient_la_SOURCES      = $(PUREBABELGEN)
libBrtClient_la_SOURCES      = $(SOURCE_EXTRAS) $(BABELGEN) 
libBrtClient_la_LIBADD       = $(LIBSYNC) $(LIBSIDL) $(LIBSIDLX) $(LIB_EXTRAS)
libBrtClient_la_LDFLAGS      = -no-undefined -rpath `pwd`/.libs \
			    -release @VERSION@

EXTRA_DIST = babel.make client_babel.make
AM_CPPFLAGS   = -I. -I$(INCLUDEDIR) -I$(INCLUDEDIR2) -I$(INCLUDEDIRX)

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

babel-stamp: $(OUTPUTSIDL)
	@rm -f babel-temp
	@touch babel-temp
	$(SHELL) $(BABELDIR)/$(BABEL) --vpath="$(srcdir)" --server=C \
	  $(BABELEXTRA) $(SERVEREXTRA)
	$(SHELL) $(BABELDIR)/$(BABEL) --client=C --make-prefix=client_  \
	  $(BABELEXTRA) $(CLIENTEXTRA) $(OUTPUTSIDL)
	@mv -f babel-temp $@

libClient.scl : $(IORSRCS)
	$(SHELL) $(top_srcdir)/bin/generate_scl libClient.scl `pwd`/libBrtClient.la \
	   $(IORSRCS)

clean-local:
	test "X$(srcdir)" = "X." || rm -f babel.make client_babel.make $(BABELGEN)
	rm -f *.testresult

check-local:
	@if test -f stamp-installcheck; then		\
	  echo $(MAKE) $(AM_MAKEFLAGS) clean;		\
	  $(MAKE) $(AM_MAKEFLAGS) clean;		\
	fi
	$(MAKE) $(AM_MAKEFLAGS)  $(check_LTLIBRARIES) $(check_PROGRAMS) $(check_SCRIPTS) \
	   libClient.scl

installcheck-local:
	@if test ! -f stamp-installcheck; then		\
	  echo $(MAKE) $(AM_MAKEFLAGS) clean;		\
	  $(MAKE) $(AM_MAKEFLAGS) clean;		\
	fi
	$(MAKE) $(AM_MAKEFLAGS)				\
	  BABELDIR=$(bindir)				\
	  INCLUDEDIR=$(includedir)			\
	  INCLUDEDIR2=$(exec_prefix)/include		\
	  INCLUDEDIRX=$(includedir)			\
	  LIBSIDL=$(INSTALLEDSIDL)			\
	  LIBSIDLX=$(INSTALLEDSIDLX)                   \
	  $(check_LTLIBRARIES) $(check_PROGRAMS) $(check_SCRIPTS) libClient.scl
	touch stamp-installcheck

update-babel-make: babel-stamp
	if test "X$(srcdir)" != "X."; then \
	   cp babel.make client_babel.make synch_babel.make $(srcdir); \
	fi

# Make sure Makefile.in is newer than babel.make
dist-hook:
	touch -c $(distdir)/Makefile.in
