##
## File:        Makefile.am
## Package:     Babel regression checks
## Revision:    $Revision: 7379 $
## Modified:    $Date: 2011-11-28 09:02:00 -0800 (Mon, 28 Nov 2011) $
## Description: automake makefile for C regression tests
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
TESTFILE = objargtest.c

#include $(srcdir)/../../include/runC.make
## BEGIN - REMOVE THIS AND INCLUDE DEFAULT WHEN C AND F77 LIBRARIES CREATED

check_PROGRAMS    = $(STATIC_PROGS) $(SHARED_PROGS)
check_SCRIPTS     = runAll.sh
check_LTLIBRARIES = libBrtClient.la

if SUPPORT_STATIC
if SUPPORT_FORTRAN90
  BABEL_STATIC_F90 = runC2F90
else
  BABEL_STATIC_F90 = 
endif
if SUPPORT_FORTRAN03
  BABEL_STATIC_F03 = runC2F03
else
  BABEL_STATIC_F90 = 
endif
STATIC_PROGS     = runC2C runC2Cxx $(BABEL_STATIC_F90) $(BABEL_STATIC_F03)

# This little snippet forces automake to define $CXXLINK
EXTRA_PROGRAMS  = automake_hack
automake_hack_SOURCES = 
nodist_EXTRA_automake_hack_SOURCES = automake_hack.cc

runC2C_SOURCES        = $(TESTFILE)
runC2C_LDFLAGS        = -static-libtool-libs -export-dynamic
runC2C_LDADD          = libBrtClient.la ../libC/libImpl.la \
			$(LIBSYNC) $(LIBSIDL)

runC2Cxx_SOURCES      = $(TESTFILE)
runC2Cxx_LDFLAGS      = -static-libtool-libs -export-dynamic
runC2Cxx_LDADD        = libBrtClient.la ../libCxx/libImpl.la \
			$(LIBSYNC) $(LIBSIDL)
runC2Cxx_LINK	      = $(CXXLINK) $(runC2Cxx_LDFLAGS)

if SUPPORT_FORTRAN90
runC2F90_SOURCES      =  $(TESTFILE)
runC2F90_LDFLAGS      = -static-libtool-libs -export-dynamic
runC2F90_LDADD        = libBrtClient.la ../libF90/libImpl.la \
			$(LIBSYNC) $(LIBSIDL) $(FCLIBS)
endif
if SUPPORT_FORTRAN03
runC2F03_SOURCES      =  $(TESTFILE)
runC2F03_LDFLAGS      = -static-libtool-libs -export-dynamic
runC2F03_LDADD        = libBrtClient.la ../libF03/libImpl.la \
			$(LIBSYNC) $(LIBSIDL) $(FCLIBS)
endif
endif

if SUPPORT_SHARED
SHARED_PROGS          = runAll
runAll_SOURCES        = $(TESTFILE)
runAll_LDFLAGS	      = -dynamic
runAll_LDADD          = libBrtClient.la $(LIBSYNC) $(LIBSIDL)
endif

nodist_libBrtClient_la_SOURCES      = $(PUREBABELGEN)
libBrtClient_la_LIBADD       = $(LIBSIDLX) $(LIBSIDL)
libBrtClient_la_LDFLAGS      = -no-undefined -rpath `pwd`/.libs -release @VERSION@

$(TESTFILE:.c=.o) : $(STUBHDRS) $(IORHDRS) $(synch_STUBHDRS) \
	$(synch_IORHDRS)

BABEL=`if test "$(BABELDIR)" != "$(top_builddir)/bin" ; then echo babel | sed '$(transform)' ; else echo babel; fi`
BABELDIR    = $(top_builddir)/bin
if WITH_FASTCALL
BABELFLAGS=--fast-call
else
BABELFLAGS=
endif
INCLUDEDIR  = $(top_builddir)/runtime/sidl
if WITH_SIDLX
INCLUDEDIRX  = $(top_builddir)/runtime/sidlx
LIBSIDL =
LIBSIDLX     = $(top_builddir)/runtime/sidlx/libsidlx.la
INSTALLEDSIDLX=$(libdir)/libsidlx.la
else
INCLUDEDIRX  = $(top_builddir)/runtime/sidl
LIBSIDL     = $(top_builddir)/runtime/sidl/libsidl.la
LIBSIDLX     =
INSTALLEDSIDLX=
endif
INCLUDEDIR2 = .
LIBSYNC	    = ../../output/libC/libOutput.la
AM_CPPFLAGS = -I$(INCLUDEDIR) -I$(INCLUDEDIRX) -I$(INCLUDEDIR2) -I../libC
EXTRA_DIST  = babel.make synch_babel.make
PUREBABELGEN = $(STUBSRCS) $(STUBHDRS) $(IORHDRS) \
	$(synch_STUBSRCS) $(synch_STUBHDRS) $(synch_IORHDRS)
CLEANFILES = $(PUREBABELGEN) babel-temp babel-stamp \
	stamp-installcheck babel.make.package babel.make.depends \
	synch_babel.make.depends synch_babel.make.package synch-stamp


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

babel-stamp: $(SIDLFILE)
	@rm -f babel-temp
	@touch babel-temp
	$(SHELL) $(BABELDIR)/$(BABEL) --client=C \
		$(BABELFLAGS) $(SIDLFILE)
	$(SHELL) $(BABELDIR)/$(BABEL) --client=C --make-prefix=synch_ \
		$(OUTPUTSIDL)
	@mv -f babel-temp $@

clean-local:
	rm -f *.a *.so *.testresult
	test "X$(srcdir)" = "X." || rm -f babel.make synch_babel.make

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
	  INCLUDEDIRX=$(includedir)			\
	  INCLUDEDIR2=$(exec_prefix)/include		\
	  LIBSIDL=$(libdir)/libsidl.la			\
	  LIBSIDLX=$(INSTALLEDSIDLX)			\
	  $(check_LTLIBRARIES) $(check_PROGRAMS) $(check_SCRIPTS)
	touch stamp-installcheck

# Make sure Makefile.in is newer than babel.make
dist-hook:
	touch -c $(distdir)/Makefile.in
## END - REMOVE THIS AND INCLUDE DEFAULT WHEN C AND F77 LIBRARIES CREATED
include $(srcdir)/babel.make
include $(srcdir)/synch_babel.make
