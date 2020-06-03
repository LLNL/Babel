##
## File:        Makefile.am
## Package:     Babel regression checks
## Revision:    $Revision: 7379 $
## Modified:    $Date: 2011-11-28 09:02:00 -0800 (Mon, 28 Nov 2011) $
## Description: automake makefile for UC++ regression tests
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

OUTPUTSIDL=$(srcdir)/../../output/output.sidl
SIDLFILE = $(srcdir)/../objarg.sidl
TESTFILE = objargtest.cxx

#include $(srcdir)/../../include/runUCxx.make
## BEGIN - REMOVE THIS AND INCLUDE DEFAULT WHEN C AND F77 LIBRARIES CREATED

check_PROGRAMS    = $(STATIC_PROGS) $(SHARED_PROGS)
check_SCRIPTS     = runAll.sh
check_LTLIBRARIES = libBrtClient.la

BABEL=`if test "$(BABELDIR)" != "$(top_builddir)/bin" ; then echo babel | sed '$(transform)' ; else echo babel; fi`
BABELDIR    = $(top_builddir)/bin
if WITH_FASTCALL
BABELFLAGS=--fast-call
else
BABELFLAGS=
endif
INCLUDEDIR  = $(top_builddir)/lib/sidlstubs/Cxx
INCLUDEDIR2 = .
LIBSIDL     = $(top_builddir)/runtime/sidl/libsidl.la
LIBSIDLSTUB = $(top_builddir)/lib/sidlstubs/Cxx/libsidlstub_cxx.la
LIBSYNC	    = ../../output/libC/libOutput.la
AM_CPPFLAGS = -I. -I$(INCLUDEDIR) -I$(INCLUDEDIR2)
EXTRA_DIST  = babel.make
PUREBABELGEN = $(STUBSRCS) $(STUBHDRS) $(IORHDRS)
CLEANFILES =  $(PUREBABELGEN) babel-temp babel-stamp \
	stamp-installcheck core babel.make.package runAll \
	babel.make.depends

if SUPPORT_STATIC
if SUPPORT_FORTRAN90
  BABEL_STATIC_F90 = runCxx2F90
else
  BABEL_STATIC_F90 = 
endif
if SUPPORT_FORTRAN03
  BABEL_STATIC_F03 = runCxx2F03
else
  BABEL_STATIC_F03 = 
endif

STATIC_PROGS       = runCxx2C runCxx2Cxx $(BABEL_STATIC_F90) $(BABEL_STATIC_F03)

runCxx2C_SOURCES        = $(TESTFILE)
runCxx2C_LDFLAGS        = -static-libtool-libs -export-dynamic
runCxx2C_LDADD          = libBrtClient.la ../libC/libImpl.la \
			  $(LIBSYNC) $(LIBSIDL)

runCxx2Cxx_SOURCES      = $(TESTFILE)
runCxx2Cxx_LDFLAGS      = -static-libtool-libs -export-dynamic
runCxx2Cxx_LDADD        = libBrtClient.la ../libCxx/libImpl.la \
			 $(LIBSYNC) $(LIBSIDL)

if SUPPORT_FORTRAN90
runCxx2F90_SOURCES      = $(TESTFILE)
runCxx2F90_LDFLAGS      = -static-libtool-libs -export-dynamic
runCxx2F90_LDADD        = libBrtClient.la ../libF90/libImpl.la \
			  $(LIBSYNC) $(LIBSIDL) $(FCLIBS)
endif
if SUPPORT_FORTRAN03
runCxx2F03_SOURCES      = $(TESTFILE)
runCxx2F03_LDFLAGS      = -static-libtool-libs -export-dynamic
runCxx2F03_LDADD        = libBrtClient.la ../libF03/libImpl.la \
			  $(LIBSYNC) $(LIBSIDL) $(FCLIBS)
endif
endif
if SUPPORT_SHARED
SHARED_PROGS            = runAll
runAll_SOURCES          = $(TESTFILE)
runAll_LDFLAGS		= -dynamic
runAll_LDADD            = libBrtClient.la $(LIBSYNC) $(LIBSIDL)
endif

$(TESTFILE:.cxx=.o) : $(STUBHDRS) $(IORHDRS)

nodist_libBrtClient_la_SOURCES      = $(PUREBABELGEN)
libBrtClient_la_LIBADD       = $(LIBSIDLSTUB) $(LIBSIDL)
libBrtClient_la_LDFLAGS      = -no-undefined -rpath `pwd`/.libs -release @VERSION@

$(PUREBABELGEN) : babel-stamp 
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


babel-stamp: $(SIDLFILE) $(OUTPUTSIDL)
	@rm -f babel-temp
	@touch babel-temp
	$(SHELL) $(BABELDIR)/$(BABEL) --client=Cxx \
		 $(BABELFLAGS) $(SIDLFILE) $(OUTPUTSIDL)
	@mv -f babel-temp babel-stamp

clean-local:
	rm -f *.a *.so *.testresult
	rm -rf ti_files
	test "X$(srcdir)" = "X." || rm -f babel.make

check-local:
	@if test -f stamp-installcheck; then		\
	  echo $(MAKE) $(AM_MAKEFLAGS) clean;		\
	  $(MAKE) $(AM_MAKEFLAGS) clean;		\
	fi
	$(MAKE) $(AM_MAKEFLAGS)				\
	  $(check_LTLIBRARIES) $(check_PROGRAMS) $(check_SCRIPTS)

installcheck-local:
	@if test ! -f stamp-installcheck; then		\
	  echo $(MAKE) $(AM_MAKEFLAGS) clean;		\
	  $(MAKE) $(AM_MAKEFLAGS) clean;		\
	fi
	$(MAKE) $(AM_MAKEFLAGS)				\
	  BABELDIR=$(bindir)				\
	  INCLUDEDIR=$(includedir)			\
	  INCLUDEDIR2=$(includedir)/cxx		\
	  LIBSIDL=$(libdir)/libsidl.la			\
	  LIBSIDLSTUB=$(libdir)/libsidlstub_cxx.la 	\
	  $(check_LTLIBRARIES) $(check_PROGRAMS) $(check_SCRIPTS)
	touch stamp-installcheck

# Make sure Makefile.in is newer than babel.make
dist-hook:
	touch -c $(distdir)/Makefile.in
## END - REMOVE THIS AND INCLUDE DEFAULT WHEN C AND F77 LIBRARIES CREATED
include $(srcdir)/babel.make
