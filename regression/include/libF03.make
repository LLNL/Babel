## -*- Makefile-automake -*-
## File:        make.libF03
## Package:     Babel regression checks
## Revision:    $Revision: 6212 $
## Modified:    $Date: 2007-10-31 18:07:17 -0600 (Wed, 31 Oct 2007) $
## Description: automake makefile for f03 regression tests
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

# This introduces module dependencies for extendables to ensure
# that referenced types are compiled before classes/interfaces
# referring to them
MOD_SUFFIX=.lo

OUTPUTSIDL=$(srcdir)/../../output/output.sidl

if SUPPORT_FORTRAN03
check_LTLIBRARIES = libImpl.la
SCLFILE=libImpl.scl

BABEL=`if test "$(BABELDIR)" != "$(top_builddir)/bin" ; then echo babel | sed '$(transform)' ; else echo babel; fi`
BABELDIR    = $(top_builddir)/bin
if WITH_FASTCALL
BABELFLAGS=--fast-call
else
BABELFLAGS=
endif
INCLUDEDIR  = $(top_builddir)/runtime/sidl
INCLUDEDIR2 = $(top_builddir)/lib/sidlstubs/F03
LIBSIDLSTUB= $(top_builddir)/lib/sidlstubs/F03/libsidlstub_f03.la
LIBSYNC     = ../../output/libC/libOutput.la

PUREBABELGEN = $(IORSRCS) $(TYPEMODULESRCS) $(ARRAYMODULESRCS) $(IORHDRS) \
	$(SKELSRCS) $(FSKELSRCS) $(STUBHDRS) $(STUBMODULESRCS) $(STUBSRCS)
SYNCHBABELGEN = $(synch_TYPEMODULESRCS) $(synch_ARRAYMODULESRCS) \
	$(synch_IORHDRS) $(synch_STUBHDRS) \
	$(synch_STUBMODULESRCS) $(synch_STUBSRCS) 
BABELGEN = $(IMPLMODULESRCS) $(IMPLSRCS)


nodist_libImpl_la_SOURCES = $(PUREBABELGEN) $(SYNCHBABELGEN)
dist_libImpl_la_SOURCES = $(BABELGEN) $(SOURCE_EXTRAS)
libImpl_la_LIBADD = $(LIBSIDLSTUB) $(LIBSYNC) $(FCLIBS)
libImpl_la_LDFLAGS      = -no-undefined -rpath `pwd`/.libs \
			  -release @VERSION@ -module
libImpl_la_LINK = $(LINK) $(libImpl_la_LDFLAGS)
#ensure that the stubs modules are compiled before the impl modules
$(TYPEMODULESRCS:.F03=.lo) $(synch_TYPEMODULESRCS:.F03=.lo) : $(BASICMODULESRC:.F03=.lo)
$(ARRAYMODULESRCS:.F03=.lo)  : $(TYPEMODULESRCS:.F03=.lo)
$(STUBMODULESRCS:.F03=.lo) : $(ARRAYMODULESRCS:.F03=.lo) $(TYPEMODULESRCS:.F03=.lo)
$(synch_ARRAYMODULESRCS:.F03=.lo) : $(synch_TYPEMODULESRCS:.F03=.lo)
$(synch_STUBMODULESRCS:.F03=.lo) : $(synch_ARRAYMODULESRCS:.F03=.lo) \
	$(synch_TYPEMODULESRCS:.F03=.lo)
$(IMPLMODULESRCS:.F03=.lo) : $(STUBMODULESRCS:.F03=.lo) \
	$(synch_STUBMODULESRCS:.F03=.lo)
$(IMPLSRCS:.F03=.lo) : $(IMPLMODULESRCS:.F03=.lo) $(STUBMODULESRCS:.F03=.lo) \
	$(synch_STUBMODULESRCS:.F03=.lo)

AM_CPPFLAGS   = -I. -I$(INCLUDEDIR) -I$(INCLUDEDIR2)
AM_FCFLAGS    = $(CHASM_FORTRAN_MFLAG)$(INCLUDEDIR2)

endif

CLEANFILES=$(PUREBABELGEN) $(SYNCHBABELGEN) $(EXTRACLEAN) \
	babel-stamp babel-temp synch-stamp synch-temp \
	stamp-installcheck core libImpl.scl \
	babel.make.depends synch_babel.make.depends \
	babel.make.package synch_babel.make.package

EXTRA_DIST = babel.make synch_babel.make 

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

babel-stamp: $(SIDLFILE)
	@rm -f babel-temp
	@touch babel-temp
	$(SHELL) $(BABELDIR)/$(BABEL) --vpath="$(srcdir)" --server=F03 \
	  $(BABELFLAGS) $(BABELEXTRA) $(SIDLFILE)
	@mv -f babel-temp $@

libImpl.scl: $(IORSRCS)
	$(SHELL) $(top_srcdir)/bin/generate_scl libImpl.scl `pwd`/libImpl.la \
	   $(IORSRCS)

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
	$(SHELL) $(BABELDIR)/$(BABEL) --client=F03 --make-prefix=synch_ \
	  $(OUTPUTSIDL)
	@mv -f synch-temp $@

update-babel-make: babel-stamp synch-stamp
	if test "X$(srcdir)" != "X."; then \
	   cp babel.make synch_babel.make $(srcdir); \
	fi

clean-local:
	rm -f *.a *.so *.mod *$(F03CPPSUFFIX)
	test "X$(srcdir)" = "X." || rm -f $(IMPLSRCS) $(IMPLMODULESRCS) \
	   babel.make synch_babel.make

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
	$(MAKE) $(AM_MAKEFLAGS) $(check_LTLIBRARIES) $(SCLFILE)

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
	  $(check_LTLIBRARIES) $(SCLFILE)
	touch stamp-installcheck

# Make sure Makefile.in is newer than babel.make
dist-hook:
	touch -c $(distdir)/Makefile.in
