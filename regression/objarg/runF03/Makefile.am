##
## File:        Makefile.am
## Package:     Babel regression checks
## Revision:    $Revision$
## Modified:    $Date$
## Description: automake makefile for f03 regression tests
##
## Copyright (c) 2000-2002, The Regents of the University of Calfornia.
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

SIDLFILE = $(srcdir)/../objarg.sidl
TESTFILE = objargtest.F03

OUTPUTSIDL=$(srcdir)/../../output/output.sidl

if SUPPORT_FORTRAN03

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
INCLUDEDIR  = $(top_builddir)/runtime/sidl
INCLUDEDIR2 = $(top_builddir)/lib/sidlstubs/F03
LIBSIDLSTUB = $(top_builddir)/lib/sidlstubs/F03/libsidlstub_f03.la
LIBSYNC     = ../../output/libC/libOutput.la
PUREBABELGEN = $(IORSRCS) $(ARRAYMODULESRCS) $(IORHDRS) \
	$(SKELSRCS) $(STUBHDRS) $(STUBMODULESRCS) $(STUBSRCS) \
	$(TYPEMODULESRCS) $(BASICMODULESRC)
SYNCHBABELGEN = $(synch_TYPEMODULESRCS) $(synch_ARRAYMODULESRCS) \
	$(synch_IORHDRS) $(synch_STUBHDRS) \
	$(synch_STUBMODULESRCS) $(synch_STUBSRCS) 

if SUPPORT_STATIC
if SUPPORT_FORTRAN90
  BABEL_STATIC_F90  = runF032F90
else
  BABEL_STATIC_F90  = 
endif

endif

STATIC_PROGS      = runF032C runF032Cxx $(BABEL_STATIC_F90) \
		    runF032F03

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

runF032F03_SOURCES      = $(TESTFILE) $(SOURCE_EXTRAS)
runF032F03_LDFLAGS      = -static-libtool-libs -export-dynamic
runF032F03_LDADD        = ../libF03/libImpl.la libBrtClient.la \
			  $(LIBSYNC) $(FCLIBS) 
runF032F03_LINK		= $(LINK) $(FCMAIN) $(runF032F03_LDFLAGS)

if SUPPORT_FORTRAN90
runF032F90_SOURCES      = $(TESTFILE) $(SOURCE_EXTRAS)
runF032F90_LDFLAGS      = -static-libtool-libs -export-dynamic
runF032F90_LDADD        = libBrtClient.la ../libF90/libImpl.la \
			   $(LIBSYNC) $(FCLIBS) 
runF032F90_LINK		= $(LINK) $(FCMAIN) $(runF032F90_LDFLAGS)
endif

endif

CLEANFILES=$(PUREBABELGEN) $(SYNCHBABELGEN) $(EXTRACLEAN) \
	synch-stamp synch-temp babel-stamp babel-temp \
	stamp-installcheck core babel.make.package \
	babel.make.depends synch_babel.make.depends \
	synch_babel.make.package

if SUPPORT_SHARED
SHARED_PROGS            = runAll
runAll_SOURCES          = $(TESTFILE) $(SOURCE_EXTRAS)
runAll_LDFLAGS		= -dynamic
runAll_LDADD            = libBrtClient.la $(LIBSYNC) \
			  $(FCLIBS) 
runAll_LINK      	= $(LINK) $(FCMAIN)  $(runAll_LDFLAGS)

endif

nodist_libBrtClient_la_SOURCES      = $(PUREBABELGEN) $(SYNCHBABELGEN) \
	$(SOURCE_EXTRAS)
libBrtClient_la_LIBADD       = $(LIBSIDLSTUB) $(LIBSYNC) $(FCLIBS)
libBrtClient_la_LDFLAGS      = -no-undefined -rpath `pwd`/.libs \
			    -release @VERSION@
libBrtClient_la_LINK = $(LINK) $(libBrtClient_la_LDFLAGS)

$(TESTFILE:.F03=.o) : libBrtClient.la

EXTRA_DIST = babel.make 
AM_CPPFLAGS   = -I. -I$(INCLUDEDIR) -I$(INCLUDEDIR2)
AM_FCFLAGS    = $(CHASM_FORTRAN_MFLAG)$(INCLUDEDIR2)

STUBOBJS = $(STUBSRCS:.c=.lo)
BASICMODULEOBJ = $(BASICMODULESRC:.F03=.lo)
STUBMODULEOBJS = $(STUBMODULESRCS:.F03=.lo)
ARRAYMODULEOBJS = $(ARRAYMODULESRCS:.F03=.lo)
TYPEMODULEOBJS = $(TYPEMODULESRCS:.F03=.lo)
libBrtClient.la $(STUBOBJS) $(ARRAYMODULEOBJS) $(STUBMODULEOBJS) $(TYPEMODULEOBJS): babel-stamp

$(TYPEMODULEOBJS) $(synch_TYPEMODULESRCS:.F03=.lo) : $(BASICMODULEOBJ)
$(ARRAYMODULEOBJS) : $(TYPEMODULEOBJS)
$(STUBMODULEOBJS) : $(ARRAYMODULEOBJS) $(TYPEMODULEOBJS)
$(synch_ARRAYMODULESRCS:.F03=.lo) : $(synch_TYPEMODULESRCS:.F03=.lo)
$(synch_STUBMODULESRCS:.F03=.lo) : $(synch_ARRAYMODULESRCS:.F03=.lo) $(synch_TYPEMODULESRCS:.F03=.lo)
$(IMPLMODULESRCS:.F03=.lo) : $(STUBMODULEOBJS) $(synch_STUBMODULESRCS:.F03=.lo)

# This little snippet forces automake to define $CXXLINK
EXTRA_PROGRAMS  = automake_hack
automake_hack_SOURCES = 
nodist_EXTRA_automake_hack_SOURCES = automake_hack.cc

PPFCCOMPILE = $(FC) $(DEFAULT_INCLUDES) $(INCLUDES) \
	$(AM_FCFLAGS) $(FCFLAGS) $(FCFLAGS_f90)
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
	$(SHELL) $(BABELDIR)/$(BABEL) --client=f03 $(BABELEXTRA) \
		$(BABELFLAGS) $(SIDLFILE) 
	@mv -f babel-temp $@

$(SYNCHBABELGEN) : synch-stamp
## cf. http://www.gnu.org/software/automake/manual/automake.html#Multiple-Outputs
## Recover from the removal of $@
	@if test -f $@; then :; else \
	  trap 'rm -rf synch.lock synch-stamp' 1 2 13 15; \
## mkdir is a portable test-and-set
	  if mkdir synch.lock 2>/dev/null; then \
## This code is being executed by the first process.
	    rm -f synch-stamp; \
	    $(MAKE) $(AM_MAKEFLAGS) synch-stamp; \
	    result=$$?; rm -rf synch.lock; exit $$result; \
	  else \
## This code is being executed by the follower processes.
## Wait until the first process is done.
	    while test -d synch.lock; do sleep 1; done; \
## Succeed if and only if the first process succeeded.
	    test -f synch-stamp; \
	  fi; \
	fi

synch-stamp: $(OUTPUTSIDL)
	@rm -f synch-temp
	@touch synch-temp
	$(SHELL) $(BABELDIR)/$(BABEL) --client=f03 --make-prefix=synch_  \
	  $(OUTPUTSIDL)
	@mv -f synch-temp $@

update-babel-make:babel-stamp synch-stamp
	if test "X$(srcdir)" != "X."; then \
	   cp babel.make synch_babel.make $(srcdir); \
	fi

clean-local:
	rm -f *.a *.so *.mod *.testresult *$(F03CPPSUFFIX)
	test "X$(srcdir)" = "X." || rm -f babel.make synch_babel.make

# Override default F03 macros since automake (as of 1.9.3) doesn't 
# really allow us to override preprocessing step.
.F03.o:
	$(AM_V_GEN)$(CPP) -traditional -D__FORTRAN03__ $(DEFS) $(DEFAULT_INCLUDES) $(INCLUDES)  $(AM_CPPFLAGS) $(CPPFLAGS) \
		-P -o $(@:.o=.tmp) -x c `test -f '$<' || echo '$(srcdir)/'`$< && \
	sed -e 's/^#pragma.*$$//' < $(@:.o=.tmp) > $(@:.o=$(F03CPPSUFFIX))
	$(my_v_ppfc)$(PPFCCOMPILE) -c -o $@ $(@:.o=$(F03CPPSUFFIX))
if DELETE_INTERMEDIATE_FILES # debugging
	@rm -f $(@:.o=$(F03CPPSUFFIX)) $(@:.o=.tmp) 
endif

.F03.obj:
	$(fc_verbose) \
	$(CPP) -traditional -D__FORTRAN03__ $(DEFS) $(DEFAULT_INCLUDES) $(INCLUDES)  $(AM_CPPFLAGS) $(CPPFLAGS) \
		-P -o $(@:.obj=.tmp) -x c `test -f '$<' || echo '$(srcdir)/'`$< && \
	sed -e 's/^#pragma.*$$//' < $(@:.obj=.tmp) > $(@:.obj=$(F03CPPSUFFIX))
	$(my_v_ppfc)$(PPFCCOMPILE) -c -o $@ $(@:.obj=$(F03CPPSUFFIX))
if DELETE_INTERMEDIATE_FILES # debugging
	@rm -f $(@:.obj=$(F03CPPSUFFIX)) $(@:.obj=.tmp)
endif

.F03.lo:
	$(fc_verbose) \
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
	$(MAKE) $(AM_MAKEFLAGS) $(check_LTLIBRARIES) $(check_PROGRAMS) $(check_SCRIPTS) 

installcheck-local:
	@if test ! -f stamp-installcheck; then		\
	  echo $(MAKE) $(AM_MAKEFLAGS) clean;		\
	  $(MAKE) $(AM_MAKEFLAGS) clean;		\
	fi
	$(MAKE) $(AM_MAKEFLAGS)				\
	  BABELDIR=$(bindir)				\
	  INCLUDEDIR=$(includedir)			\
	  INCLUDEDIR2=$(includedir)/f03		\
	  LIBSIDLSTUB=$(libdir)/libsidlstub_f03.la	\
	  $(check_LTLIBRARIES) $(check_PROGRAMS) $(check_SCRIPTS)
	touch stamp-installcheck

# Make sure Makefile.in is newer than babel.make
dist-hook:
	touch -c $(distdir)/Makefile.in

include $(srcdir)/babel.make
include $(srcdir)/synch_babel.make
