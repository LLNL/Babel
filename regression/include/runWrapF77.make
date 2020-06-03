## -*- Makefile -*-
## File:        make.runF77
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

if SUPPORT_FORTRAN77
SCLFILE=libClient.scl


check_PROGRAMS    = $(STATIC_PROGS) $(SHARED_PROGS)
check_SCRIPTS     = runAll.sh
check_LTLIBRARIES = libBrtClient.la 

BABEL=`if test "$(BABELDIR)" != "$(top_builddir)/bin" ; then echo babel | sed '$(transform)' ; else echo babel; fi`
BABELDIR    = $(top_builddir)/bin
INCLUDEDIR  = $(top_builddir)/runtime/sidl
INCLUDEDIR2 = $(top_builddir)/lib/sidlstubs/F77
LIBSIDLSTUB = $(top_builddir)/lib/sidlstubs/F77/libsidlstub_f77.la
LIBSYNC     = ../../output/libC/libOutput.la
BABELGEN=$(IMPLSRCS) 
PUREBABELGEN=$(STUBSRCS) $(STUBFORTRANINC) $(STUBHDRS) $(IORSRCS) $(IORHDRS) \
	$(SKELSRCS) $(client_STUBSRCS) $(client_STUBHDRS) $(client_IORHDRS) \
	$(client_STUBFORTRANINC)
CLEANFILES=$(PUREBABELGEN) $(EXTRACLEAN) $(DOCFILES) babel-stamp \
	stamp-installcheck core \
	babel-temp libClient.scl \
	babel.make.package client_babel.make.package \
	babel.make.depends client_babel.make.depends
DOCFILES = $(STUBDOCS) $(client_STUBDOCS)


if SUPPORT_STATIC
if SUPPORT_FORTRAN90
  BABEL_STATIC_F90  = runF772F90
else
  BABEL_STATIC_F90  = 
endif
if SUPPORT_FORTRAN03
  BABEL_STATIC_F03  = runF772F03
else
  BABEL_STATIC_F03  = 
endif
STATIC_PROGS       = runF772C runF772Cxx runF772F77 $(BABEL_STATIC_F90) $(BABEL_STATIC_F03)

runF772C_SOURCES        = $(TESTFILE) $(SOURCE_EXTRAS)
runF772C_LDFLAGS        = -static-libtool-libs -export-dynamic
runF772C_LDADD          = libBrtClient.la ../libC/libImpl.la \
			  $(LIBSYNC) $(FLIBS)
runF772C_LINK		= $(LINK) $(FMAIN) $(runF772C_LDFLAGS)
# use C linker instead of F77 to avoid problem when compiling with Intel's
# icc with optimization (undefined _intel_fast_memcpy symbol)

runF772Cxx_SOURCES      = $(TESTFILE) $(SOURCE_EXTRAS)
runF772Cxx_LDFLAGS      = -static-libtool-libs -export-dynamic
runF772Cxx_LDADD        = libBrtClient.la ../libCxx/libImpl.la \
			  $(LIBSYNC) $(FLIBS)
runF772Cxx_LINK	 	= $(CXXLINK) $(FMAIN) $(runF772Cxx_LDFLAGS)

runF772F77_SOURCES      = $(TESTFILE) $(SOURCE_EXTRAS)
runF772F77_LDFLAGS      = -static-libtool-libs -export-dynamic
runF772F77_LDADD        = libBrtClient.la ../libF77/libImpl.la \
			  $(LIBSYNC) $(FLIBS)
runF772F77_LINK		= $(LINK) $(FMAIN) $(runF772F77_LDFLAGS)
# use C linker instead of F77 to avoid problem when compiling with Intel's
# icc with optimization (undefined _intel_fast_memcpy symbol)

if SUPPORT_FORTRAN90
runF772F90_SOURCES      = $(TESTFILE) $(SOURCE_EXTRAS)
runF772F90_LDFLAGS      = -static-libtool-libs -export-dynamic
runF772F90_LDADD        = libBrtClient.la ../libF90/libImpl.la \
			  $(LIBSYNC) $(FLIBS) $(FCLIBS)
runF772F90_LINK		= $(LINK) $(FMAIN) $(runF772F90_LDFLAGS)
# use C linker instead of F77 to avoid problem when compiling with Intel's
# icc with optimization (undefined _intel_fast_memcpy symbol)
endif
if SUPPORT_FORTRAN03
runF772F03_SOURCES      = $(TESTFILE) $(SOURCE_EXTRAS)
runF772F03_LDFLAGS      = -static-libtool-libs -export-dynamic
runF772F03_LDADD        = libBrtClient.la ../libF03/libImpl.la \
			  $(LIBSYNC) $(FLIBS) $(FCLIBS)
runF772F03_LINK		= $(LINK) $(FMAIN) $(runF772F03_LDFLAGS)
# use C linker instead of F77 to avoid problem when compiling with Intel's
# icc with optimization (undefined _intel_fast_memcpy symbol)
endif
endif

if SUPPORT_SHARED
SHARED_PROGS            = runAll
runAll_SOURCES          = $(TESTFILE) $(SOURCE_EXTRAS)
runAll_LDFLAGS		= -dynamic
runAll_LDADD            = libBrtClient.la $(LIBSYNC) $(FLIBS)
runAll_LINK		= $(LINK) $(FMAIN) $(runAll_LDFLAGS)
endif

nodist_libBrtClient_la_SOURCES = $(PUREBABELGEN)
libBrtClient_la_SOURCES = $(BABELGEN) $(SOURCE_EXTRAS)
libBrtClient_la_LIBADD       = $(LIBSYNC) $(LIBSIDLSTUB) $(FLIBS)
libBrtClient_la_LDFLAGS      = -no-undefined -rpath `pwd`/.libs \
			    -release @VERSION@
libBrtClient_la_LINK = $(LINK) $(libBrtClient_la_LDFLAGS)

EXTRA_DIST = babel.make client_babel.make 
AM_CPPFLAGS   = -I. -I$(INCLUDEDIR) -I$(INCLUDEDIR2)
AM_FFLAGS=$(AM_CPPFLAGS)

$(TESTFILE:.f=.o) $(TESTFILE:.f=.lo) : $(STUBFORTRANINC) $(client_STUBFORTRANINC)

$(BABELGEN) $(PUREBABELGEN) $(DOCFILES) : babel-stamp
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


# This little snippet forces automake to define $CXXLINK
EXTRA_PROGRAMS  = automake_hack
automake_hack_SOURCES = 
nodist_EXTRA_automake_hack_SOURCES = automake_hack.cc
endif

babel-stamp: $(OUTPUTSIDL)
	@rm -f babel-temp
	@touch babel-temp
	$(SHELL) $(BABELDIR)/$(BABEL) --vpath="$(srcdir)" --server=f77 \
		$(BABELEXTRA) $(SERVEREXTRA) 
	$(SHELL) $(BABELDIR)/$(BABEL) --client=f77 \
		--make-prefix=client_ $(CLIENTEXTRA) $(BABELEXTRA) \
		$(OUTPUTSIDL)
	@mv -f babel-temp $@

libClient.scl : $(IORSRCS)
	$(SHELL) $(top_srcdir)/bin/generate_scl libClient.scl `pwd`/libBrtClient.la \
		$(IORSRCS)

clean-local:
	test "X$(srcdir)" = "X." || rm -f babel.make client_babel.make $(BABELGEN)
	rm -f *.testresult

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
	$(MAKE) $(AM_MAKEFLAGS)  $(check_LTLIBRARIES) $(check_PROGRAMS) $(check_SCRIPTS) \
	  $(SCLFILE)

installcheck-local:
	@if test ! -f stamp-installcheck; then		\
	  echo $(MAKE) $(AM_MAKEFLAGS) clean;		\
	  $(MAKE) $(AM_MAKEFLAGS) clean;		\
	fi
	$(MAKE) $(AM_MAKEFLAGS)				\
	  BABELDIR=$(bindir)				\
	  INCLUDEDIR=$(includedir)			\
	  INCLUDEDIR2=$(includedir)/f77			\
	  LIBSIDLSTUB=$(libdir)/libsidlstub_f77.la	\
	  $(check_LTLIBRARIES) $(check_PROGRAMS) $(check_SCRIPTS) \
	  $(SCLFILE)
	touch stamp-installcheck

# Make sure Makefile.in is newer than babel.make
dist-hook:
	touch -c $(distdir)/Makefile.in
