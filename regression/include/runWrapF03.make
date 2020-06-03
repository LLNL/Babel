## -*- Makefile -*-
## File:        make.runF03
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
MOD_SUFFIX=.lo

OUTPUTSIDL=$(srcdir)/../../output/output.sidl

if SUPPORT_FORTRAN03
SCLFILE=libClient.scl

check_PROGRAMS    = $(STATIC_PROGS) $(SHARED_PROGS)
check_SCRIPTS     = runAll.sh
check_LTLIBRARIES = libBrtClient.la

BABEL=`if test "$(BABELDIR)" != "$(top_builddir)/bin" ; then echo babel | sed '$(transform)' ; else echo babel; fi`
BABELDIR    = $(top_builddir)/bin
INCLUDEDIR  = $(top_builddir)/runtime/sidl
INCLUDEDIR2 = $(top_builddir)/lib/sidlstubs/F03
LIBSIDLSTUB = $(top_builddir)/lib/sidlstubs/F03/libsidlstub_f03.la
LIBSYNC     = ../../output/libC/libOutput.la
BABELGEN = $(IMPLMODULESRCS) $(IMPLSRCS)
PUREBABELGEN = $(IORSRCS) $(TYPEMODULESRCS) $(ARRAYMODULESRCS) $(IORHDRS) \
	$(SKELSRCS) $(FSKELSRCS) $(STUBHDRS) $(STUBMODULESRCS) $(STUBSRCS) \
	$(client_TYPEMODULESRCS) $(client_ARRAYMODULESRCS) $(client_IORHDRS) \
	$(client_STUBHDRS) \
	$(client_STUBMODULESRCS) $(client_STUBSRCS) 
CLEANFILES=$(PUREBABELGEN) $(EXTRACLEAN) babel-stamp \
	stamp-installcheck core \
	babel-temp libClient.scl \
	babel.make.package client_babel.make.package \
	babel.make.depends client_babel.make.depends

$(TYPEMODULESRCS:.F03=.lo) $(client_TYPEMODULESRCS:.F90=.lo) : $(BASICMODULESRC:.F03=.lo)
$(ARRAYMODULESRCS:.F03=.lo)  : $(TYPEMODULESRCS:.F03=.lo)
$(STUBMODULESRCS:.F03=.lo) : $(ARRAYMODULESRCS:.F03=.lo) $(TYPEMODULESRCS:.F03=.lo)
$(client_ARRAYMODULESRCS:.F03=.lo) : $(client_TYPEMODULESRCS:.F03=.lo)
$(client_STUBMODULESRCS:.F03=.lo) : $(client_ARRAYMODULESRCS:.F03=.lo) \
	$(client_TYPEMODULESRCS:.F03=.lo) \
	$(TYPEMODULESRCS:.F03=.lo)
$(IMPLMODULESRCS:.F03=.lo) : $(STUBMODULESRCS:.F03=.lo) \
	$(client_STUBMODULESRCS:.F03=.lo)
$(IMPLSRCS:.F03=.lo) : $(IMPLMODULESRCS:.F03=.lo) \
	$(STUBMODULESRCS:.F03=.lo) \
	$(client_STUBMODULESRCS:.F03=.lo)

if SUPPORT_STATIC
if SUPPORT_FORTRAN77
  STATIC_TEMP_F77 = runF032F77
else
  STATIC_TEMP_F77 =
endif
if SUPPORT_FORTRAN90
  STATIC_TEMP_F90 = runF032F90
else
  STATIC_TEMP_F90 =
endif
STATIC_PROGS      = runF032C runF032Cxx $(STATIC_TEMP_F77) $(STATIC_TEMP_F90) runF032F03

runF032C_SOURCES        = $(TESTFILE) $(SOURCE_EXTRAS)
runF032C_LDFLAGS        = -static-libtool-libs -export-dynamic
runF032C_LDADD          = libBrtClient.la ../libC/libImpl.la \
			  $(LIBSYNC) $(FCLIBS) 
runF032C_LINK		= $(LINK) $(FCMAIN) $(runF032C_LDFLAGS)

runF032Cxx_SOURCES      = $(TESTFILE) $(SOURCE_EXTRAS)
runF032Cxx_LDFLAGS      = -static-libtool-libs -export-dynamic
runF032Cxx_LDADD        = libBrtClient.la ../libCxx/libImpl.la \
			  $(LIBSYNC) $(FCLIBS) 
runF032Cxx_LINK        =  $(LIBTOOL) --tag=CXX --mode=link $(CXXLD) $(AM_CXXFLAGS) \
	$(CXXFLAGS) $(AM_LDFLAGS) $(runF032Cxx_LDFLAGS) -o $@ $(FCMAIN) 


if SUPPORT_FORTRAN77
runF032F77_SOURCES      = $(TESTFILE) $(SOURCE_EXTRAS)
runF032F77_LDFLAGS      = -static-libtool-libs -export-dynamic
runF032F77_LDADD        = libBrtClient.la ../libF77/libImpl.la \
			  $(LIBSYNC) $(FLIBS) $(FCLIBS) 
runF032F77_LINK		= $(LINK) $(FCMAIN) $(runF032F77_LDFLAGS)
endif

if SUPPORT_FORTRAN90
runF032F90_SOURCES      = $(TESTFILE) $(SOURCE_EXTRAS)
runF032F90_LDFLAGS      = -static-libtool-libs -export-dynamic
runF032F90_LDADD        = libBrtClient.la ../libF90/libImpl.la \
			   $(LIBSYNC) $(FCLIBS) 
runF032F90_LINK		= $(LINK) $(FCMAIN) $(runF032F90_LDFLAGS)
endif

runF032F03_SOURCES      = $(TESTFILE) $(SOURCE_EXTRAS)
runF032F03_LDFLAGS      = -static-libtool-libs -export-dynamic
runF032F03_LDADD        = libBrtClient.la ../libF03/libImpl.la \
			  $(LIBSYNC) $(FCLIBS) 
runF032F03_LINK		= $(LINK) $(FCMAIN) $(runF032F03_LDFLAGS)
endif

if SUPPORT_SHARED
SHARED_PROGS            = runAll
runAll_SOURCES          = $(TESTFILE) $(SOURCE_EXTRAS)
runAll_LDFLAGS		= -dynamic
runAll_LDADD            = libBrtClient.la $(LIBSYNC) \
			  $(FCLIBS) 
runAll_LINK      	= $(LINK) $(FCMAIN) $(runAll_LDFLAGS)

endif

nodist_libBrtClient_la_SOURCES      = $(PUREBABELGEN)
libBrtClient_la_SOURCES      = $(BABELGEN) $(SOURCE_EXTRAS)
libBrtClient_la_LIBADD       = $(LIBSIDLSTUB) $(LIBSYNC) $(FCLIBS)
libBrtClient_la_LDFLAGS      = -no-undefined -rpath `pwd`/.libs \
			    -release @VERSION@
libBrtClient_la_LINK = $(LINK) $(libBrtClient_la_LDFLAGS)

$(TESTFILE:.F03=.o) : libBrtClient.la

EXTRA_DIST = babel.make client_babel.make
AM_CPPFLAGS   = -I. -I$(INCLUDEDIR) -I$(INCLUDEDIR2)
AM_FCFLAGS    = $(CHASM_FORTRAN_MFLAG)$(INCLUDEDIR2)

STUBOBJS = $(STUBSRCS:.c=.lo)
BASICMODULEOBJ = $(BASICMODULESRC:.F03=.lo)
STUBMODULEOBJS = $(STUBMODULESRCS:.F03=.lo)
ARRAYMODULEOBJS = $(ARRAYMODULESRCS:.F03=.lo)
TYPEMODULEOBJS = $(TYPEMODULESRCS:.F03=.lo)
libBrtClient.la $(STUBOBJS) $(ARRAYMODULEOBJS) $(STUBMODULEOBJS) $(TYPEMODULEOBJS): babel-stamp

$(TYPEMODULEOBJS) : $(BASICMODULEOBJ)
$(ARRAYMODULEOBJS) :  $(TYPEMODULEOBJS)
$(STUBMODULEOBJS): $(TYPEMODULEOBJS) $(ARRAYMODULEOBJS)

# This little snippet forces automake to define $CXXLINK
EXTRA_PROGRAMS  = automake_hack
automake_hack_SOURCES = 
nodist_EXTRA_automake_hack_SOURCES = automake_hack.cc
endif

PPFCCOMPILE = $(FC) $(DEFAULT_INCLUDES) $(INCLUDES) \
	$(AM_FCFLAGS) $(FCFLAGS) $(FCFLAGS_f03)
# Customize some F03 macros since automake (as of 1.9.3) 
# still doesn't include the --tag specifier for libtool
my_v_ppfc = $(my_v_ppfc_$(V))
my_v_ppfc_ = $(my_v_ppfc_$(AM_DEFAULT_VERBOSITY))
my_v_ppfc_0 = @echo "  PPFC03 $@";
LTPPFCCOMPILE = $(my_v_ppfc)$(LIBTOOL) --silent --mode=compile --tag=FC $(FC) \
	$(DEFAULT_INCLUDES) $(INCLUDES) \
	$(AM_FCFLAGS) $(FCFLAGS) $(FCFLAGS_f03)
FCLINK = $(AM_V_PPFCLD)$(LIBTOOL) --silent --mode=link --tag=FC $(FCLD) $(AM_FFLAGS) $(FCFLAGS) \
	$(AM_LDFLAGS) $(LDFLAGS) -o $@

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
	$(SHELL) $(BABELDIR)/$(BABEL) --vpath="$(srcdir)" --server=f03 \
		$(BABELEXTRA) $(SERVEREXTRA) 
	$(SHELL) $(BABELDIR)/$(BABEL) --client=f03 \
		--make-prefix=client_ $(CLIENTEXTRA) $(BABELEXTRA) \
		$(OUTPUTSIDL)
	@mv -f babel-temp $@

libClient.scl : $(IORSRCS)
	$(SHELL) $(top_srcdir)/bin/generate_scl libClient.scl `pwd`/libBrtClient.la \
		$(IORSRCS)

update-babel-make:babel-stamp
	if test "X$(srcdir)" != "X."; then \
	   cp babel.make client_babel.make $(srcdir); \
	fi

clean-local:
	rm -f *.a *.so *.mod *.testresult *$(F03CPPSUFFIX)
	test "X$(srcdir)" = "X." || rm -f babel.make client_babel.make $(BABELGEN)

# Override default F03 macros since automake (as of 1.9.3) doesn't 
# really allow us to override preprocessing step.
.F03.o:
	$(AM_V_GEN) \
	$(CPP) -traditional -D__FORTRAN03__ $(DEFS) $(DEFAULT_INCLUDES) $(INCLUDES)  $(AM_CPPFLAGS) $(CPPFLAGS) \
		-P -o $(@:.o=.tmp) -x c `test -f '$<' || echo '$(srcdir)/'`$< && \
	sed -e 's/^#pragma.*$$//' < $(@:.o=.tmp) > $(@:.o=$(F03CPPSUFFIX)) 
	$(my_v_ppfc)$(PPFCCOMPILE) -c -o $@ $(@:.o=$(F03CPPSUFFIX))
if DELETE_INTERMEDIATE_FILES # debugging
	@rm -f $(@:.o=$(F03CPPSUFFIX)) $(@:.o=.tmp) 
endif

.F03.obj:
	$(AM_V_GEN) \
	$(CPP) -traditional -D__FORTRAN03__ $(DEFS) $(DEFAULT_INCLUDES) $(INCLUDES)  $(AM_CPPFLAGS) $(CPPFLAGS) \
		-P -o $(@:.obj=.tmp) -x c `test -f '$<' || echo '$(srcdir)/'`$< && \
	sed -e 's/^#pragma.*$$//' < $(@:.obj=.tmp) > $(@:.obj=$(F03CPPSUFFIX)) 
	$(my_v_ppfc)$(PPFCCOMPILE) -c -o $@ $(@:.obj=$(F03CPPSUFFIX))
if DELETE_INTERMEDIATE_FILES # debugging
	@rm -f $(@:.obj=$(F03CPPSUFFIX)) $(@:.obj=.tmp)
endif

.F03.lo:
	$(AM_V_GEN) \
	$(CPP) -traditional -D__FORTRAN03__ $(DEFS) $(DEFAULT_INCLUDES) $(INCLUDES)  $(AM_CPPFLAGS) $(CPPFLAGS) \
		-P -o $(@:.lo=.tmp) -x c `test -f '$<' || echo '$(srcdir)/'`$< && \
	sed -e 's/^#pragma.*$$//' < $(@:.lo=.tmp) > $(@:.lo=$(F03CPPSUFFIX)) 
	$(LTPPFCCOMPILE) -c -o $@  $(@:.lo=$(F03CPPSUFFIX))
if DELETE_INTERMEDIATE_FILES # debugging
	@rm -f $(@:.lo=$(F03CPPSUFFIX)) $(@:.lo=.tmp)
endif

check-local:
	@if test -f stamp-installcheck; then		\
	  echo $(MAKE) $(AM_MAKEFLAGS) clean;		\
	  $(MAKE) $(AM_MAKEFLAGS) clean;		\
	fi
	$(MAKE) $(AM_MAKEFLAGS) $(check_LTLIBRARIES) $(check_PROGRAMS) $(check_SCRIPTS) \
	   $(SCLFILE)

installcheck-local:
	@if test ! -f stamp-installcheck; then		\
	  echo $(MAKE) $(AM_MAKEFLAGS) clean;		\
	  $(MAKE) $(AM_MAKEFLAGS) clean;		\
	fi
	$(MAKE) $(AM_MAKEFLAGS)				\
	  BABELDIR=$(bindir)				\
	  INCLUDEDIR=$(includedir)			\
	  INCLUDEDIR2=$(includedir)/f03			\
	  LIBSIDLSTUB=$(libdir)/libsidlstub_f03.la	\
	  $(check_LTLIBRARIES) $(check_PROGRAMS) $(check_SCRIPTS) \
	  $(SCLFILE)
	touch stamp-installcheck

# Make sure Makefile.in is newer than babel.make
dist-hook:
	touch -c $(distdir)/Makefile.in
