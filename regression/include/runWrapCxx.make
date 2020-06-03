## -*- Makefile -*-
## File:        make.runCxx
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
INCLUDEDIR  = $(top_builddir)/lib/sidlstubs/Cxx
INCLUDEDIR2 = .
LIBSIDLSTUB = $(top_builddir)/lib/sidlstubs/Cxx/libsidlstub_cxx.la
LIBSYNC     = ../../output/libC/libOutput.la
BABELGEN=$(IMPLSRCS) $(IMPLHDRS)
PUREBABELGEN=$(STUBSRCS) $(STUBHDRS) $(IORSRCS) $(IORHDRS) $(SKELSRCS) \
	$(client_STUBSRCS) $(client_STUBHDRS) $(client_IORHDRS)
CLEANFILES=$(PUREBABELGEN) $(EXTRACLEAN) babel-stamp \
	stamp-installcheck core \
	babel-temp libClient.scl \
	babel.make.package client_babel.make.package \
	babel.make.depends client_babel.make.depends

EXTRA_DIST = babel.make client_babel.make 
AM_CPPFLAGS   = -I. -I$(INCLUDEDIR) -I$(INCLUDEDIR2)

if SUPPORT_STATIC
if SUPPORT_FORTRAN77
  BABEL_STATIC_F77  = runCxx2F77 
else
  BABEL_STATIC_F77  = 
endif
if SUPPORT_FORTRAN90
  BABEL_STATIC_F90  = runCxx2F90
else
  BABEL_STATIC_F90  = 
endif
if SUPPORT_FORTRAN03
  BABEL_STATIC_F03  = runCxx2F03
else
  BABEL_STATIC_F03  = 
endif
STATIC_PROGS       = runCxx2C runCxx2Cxx runCxx2Cxx $(BABEL_STATIC_F77) $(BABEL_STATIC_F90) $(BABEL_STATIC_F03)

runCxx2C_SOURCES        = $(TESTFILE) $(SOURCE_EXTRAS)
runCxx2C_LDFLAGS        = -static-libtool-libs -export-dynamic
runCxx2C_LDADD          = libBrtClient.la ../libC/libImpl.la \
			  $(LIBSYNC)

runCxx2Cxx_SOURCES      = $(TESTFILE) $(SOURCE_EXTRAS)
runCxx2Cxx_LDFLAGS      = -static-libtool-libs -export-dynamic
runCxx2Cxx_LDADD        = libBrtClient.la ../libCxx/libImpl.la \
			  $(LIBSYNC)

if SUPPORT_FORTRAN77
runCxx2F77_SOURCES      = $(TESTFILE) $(SOURCE_EXTRAS)
runCxx2F77_LDFLAGS      = -static-libtool-libs -export-dynamic
runCxx2F77_LDADD        = libBrtClient.la ../libF77/libImpl.la \
			  $(LIBSYNC) $(FLIBS)
endif

if SUPPORT_FORTRAN90
runCxx2F90_SOURCES      = $(TESTFILE) $(SOURCE_EXTRAS)
runCxx2F90_LDFLAGS      = -static-libtool-libs -export-dynamic
runCxx2F90_LDADD        = libBrtClient.la ../libF90/libImpl.la \
			  $(LIBSYNC) $(FCLIBS)
endif

if SUPPORT_FORTRAN03
runCxx2F03_SOURCES      = $(TESTFILE) $(SOURCE_EXTRAS)
runCxx2F03_LDFLAGS      = -static-libtool-libs -export-dynamic
runCxx2F03_LDADD        = libBrtClient.la ../libF03/libImpl.la \
			  $(LIBSYNC) $(FCLIBS)
endif

endif
if SUPPORT_SHARED
SHARED_PROGS            = runAll
runAll_SOURCES          = $(TESTFILE) $(SOURCE_EXTRAS)
runAll_LDFLAGS		= -dynamic
runAll_LDADD            = libBrtClient.la $(LIBSYNC)
endif

$(TESTFILE:.cxx=.o) : $(IORHDRS) $(STUBHDRS) $(synch_IORHDRS) $(synch_STUBHDRS) \
	$(client_IORHDRS) $(client_STUBHDRS)

nodist_libBrtClient_la_SOURCES      = $(PUREBABELGEN) 
libBrtClient_la_SOURCES      = $(BABELGEN) $(SOURCE_EXTRAS)
libBrtClient_la_LIBADD       = $(LIBSIDLSTUB)
libBrtClient_la_LDFLAGS      = -no-undefined -rpath `pwd`/.libs \
			    -release @VERSION@

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
	$(SHELL) $(BABELDIR)/$(BABEL) --vpath="$(srcdir)" --server=C++ \
		$(BABELEXTRA) $(SERVEREXTRA) 
	$(SHELL) $(BABELDIR)/$(BABEL) --client=Cxx $(BABELEXTRA) \
		--make-prefix=client_ $(CLIENTEXTRA) $(OUTPUTSIDL) 
	@mv -f babel-temp $@

libClient.scl : $(IORSRCS)
	$(SHELL) $(top_srcdir)/bin/generate_scl libClient.scl `pwd`/libBrtClient.la \
		$(IORSRCS)
clean-local:
	rm -f *.a *.so *.testresult
	rm -rf ti_files
	test "X$(srcdir)" = "X." || rm -f $(BABELGEN) \
		babel.make client_babel.make

update-babel-make: babel-stamp
	if test "X$(srcdir)" != "X."; then \
	   cp client_babel.make $(srcdir); \
	   cp babel.make $(srcdir); \
	fi

check-local:
	@if test -f stamp-installcheck; then		\
	  echo $(MAKE) $(AM_MAKEFLAGS) clean;		\
	  $(MAKE) $(AM_MAKEFLAGS) clean;		\
	fi
	$(MAKE) $(AM_MAKEFLAGS) $(check_LTLIBRARIES) $(check_PROGRAMS) $(check_SCRIPTS) \
	   libClient.scl

installcheck-local:
	@if test ! -f stamp-installcheck; then		\
	  echo $(MAKE) $(AM_MAKEFLAGS) clean;		\
	  $(MAKE) $(AM_MAKEFLAGS) clean;		\
	fi
	$(MAKE) $(AM_MAKEFLAGS)				\
	  BABELDIR=$(bindir)				\
	  INCLUDEDIR=$(includedir)/cxx			\
	  LIBSIDLSTUB=$(libdir)/libsidlstub_cxx.la	\
	$(check_LTLIBRARIES) $(check_PROGRAMS) $(check_SCRIPTS) libClient.scl
	touch stamp-installcheck

# Make sure Makefile.in is newer than babel.make
dist-hook:
	touch -c $(distdir)/Makefile.in
