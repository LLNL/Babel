##
## File:        Makefile.am
## Package:     Babel CCA Hello examples
## Copyright:   (c) 2000-2001 Lawrence Livermore National Security, LLC
## Revision:    $Revision: 7368 $
## Modified:    $Date: 2011-11-22 15:23:42 -0800 (Tue, 22 Nov 2011) $
## Description: automake makefile for F77 CCA Hello World client
##

if SUPPORT_FORTRAN90
check_PROGRAMS    = HelloDriver
check_LTLIBRARIES = libDriver.la
endif 

BABELDIR    = $(top_builddir)/bin
INCLUDEDIR  = $(top_builddir)/runtime/sidl
INCLUDEDIR2 = $(top_builddir)/lib/sidlstubs/F90
LIBSIDLSTUB_F90= $(top_builddir)/lib/sidlstubs/F90/libsidlstub_f90.la
ALLSRCS     = $(STUBSRCS) $(STUBHDRS) $(IORHDRS) $(BASICMODULESRC) $(TYPEMODULESRCS) $(ARRAYMODULESRCS) $(STUBMODULESRCS)

nodist_libDriver_la_SOURCES      = $(ALLSRCS)
libDriver_la_LIBADD       = $(LIBSIDLSTUB_F90) $(FCLIBS)
libDriver_la_LDFLAGS      = -no-undefined -rpath `pwd`/.libs -release @VERSION@ $(AM_FFLAGS) $(FCFLAGS)
libDriver_la_LINK	  = $(LINK) $(libDriver_la_LDFLAGS)

HelloDriver_SOURCES      = HelloDriver.F90
HelloDriver_DEPENDENCIES = libDriver.la
HelloDriver_LDADD        = libDriver.la $(FCLIBS)
HelloDriver_LDFLAGS	 = $(AM_FFLAGS) $(FCFLAGS) -dynamic
HelloDriver_LINK	 = $(LINK) $(FCMAIN) $(HelloDriver_LDFLAGS)

$(TYPEMODULESRCS:.F90=.lo) : $(BASICMODULEOBJ)
$(ARRAYMODULESRCS:.F90=.lo) : $(TYPEMODULESRCS:.F90=.lo)
$(STUBMODULESRCS:.F90=.lo) : $(ARRAYMODULESRCS:.F90=.lo) $(TYPEMODULESRCS:.F90=.lo)

HelloDriver.$(OBJEXT) : libDriver.la

EXTRA_DIST = babel.make
AM_CPPFLAGS   = -I. -I$(INCLUDEDIR) -I$(INCLUDEDIR2) 
AM_FCFLAGS = $(CHASM_FORTRAN_MFLAG)$(INCLUDEDIR2)

CLEANFILES = babel-stamp babel-temp $(ALLSRCS) babel.make.package \
	babel.make.depends *.mod *.MOD

# Customize some F90 macros since automake (as of 1.9.3) 
# still doesn't include the --tag specifier for libtool
LTPPFCCOMPILE = $(LIBTOOL) --mode=compile --tag=FC $(FC) \
	$(DEFAULT_INCLUDES) $(INCLUDES) \
	$(AM_FCFLAGS) $(FCFLAGS) $(FCFLAGS_f90)
PPFCCOMPILE = $(FC) $(DEFAULT_INCLUDES) $(INCLUDES) \
	$(AM_CPPFLAGS) $(CPPFLAGS) $(AM_FCFLAGS) $(FCFLAGS) $(FCFLAGS_f90)
FCLINK = $(LIBTOOL) --mode=link --tag=FC $(FCLD) $(AM_FFLAGS) $(FCFLAGS) \
        $(AM_LDFLAGS) $(LDFLAGS) -o $@

$(ALLSRCS) : babel-stamp
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

babel-stamp:  ../libdecaf/decaf.sidl ../cca.sidl
	@rm -f babel-temp
	@touch babel-temp
	$(SHELL) $(BABELDIR)/babel --client=f90 \
	  --repository-path=../repository \
	  decaf.Framework gov.cca
	@mv -f babel-temp $@

clean-local:
	test "X$(srcdir)" = "X." || rm -f babel.make

# Override default F90 macros since automake (as of 1.9.3) doesn't 
# really allow us to override preprocessing step.
.F90.o:
	$(CPP) -traditional $(DEFS) $(DEFAULT_INCLUDES) $(INCLUDES)  $(AM_CPPFLAGS) $(CPPFLAGS) \
		-P -o $(@:.o=.tmp) -x c `test -f '$<' || echo '$(srcdir)/'`$<
	sed -e 's/^#pragma.*$$//' < $(@:.o=.tmp) > $(@:.o=$(F90CPPSUFFIX))
	$(PPFCCOMPILE) -c -o $@ $(@:.o=$(F90CPPSUFFIX))
	rm -f $(@:.o=$(F90CPPSUFFIX)) $(@:.o=.tmp)

.F90.obj:
	$(CPP) -traditional $(DEFS) $(DEFAULT_INCLUDES) $(INCLUDES)  $(AM_CPPFLAGS) $(CPPFLAGS) \
		-P -o $(@:.obj=.tmp) -x c `test -f '$<' || echo '$(srcdir)/'`$<
	sed -e 's/^#pragma.*$$//' < $(@:.obj=.tmp) > $(@:.obj=$(F90CPPSUFFIX))
	$(PPFCCOMPILE) -c -o $@ $(@:.obj=$(F90CPPSUFFIX))
	rm -f $(@:.obj=$(F90CPPSUFFIX)) $(@:.obj=.tmp)

.F90.lo:
	$(CPP) -traditional $(DEFS) $(DEFAULT_INCLUDES) $(INCLUDES)  $(AM_CPPFLAGS) $(CPPFLAGS) \
		 -P -o $(@:.lo=.tmp) -x c `test -f '$<' || echo '$(srcdir)/'`$<
	sed -e 's/^#pragma.*$$//' < $(@:.lo=.tmp) > $(@:.lo=$(F90CPPSUFFIX))
	$(LTPPFCCOMPILE) -c -o $@  $(@:.lo=$(F90CPPSUFFIX))
	rm -f $(@:.lo=$(F90CPPSUFFIX)) $(@:.lo=.tmp)

include $(srcdir)/babel.make

# Make sure Makefile.in is newer than babel.make
dist-hook:
	touch -c $(distdir)/Makefile.in
