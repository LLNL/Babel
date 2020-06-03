##
## File:        Makefile.am
## Package:     Babel CCA Hello examples
## Copyright:   (c) 2000-2001 Lawrence Livermore National Security, LLC
## Revision:    $Revision: 7379 $
## Modified:    $Date: 2011-11-28 09:02:00 -0800 (Mon, 28 Nov 2011) $
## Description: automake makefile for F77 CCA Hello World client
##

if SUPPORT_FORTRAN77
check_PROGRAMS    = HelloDriver
check_SCRIPTS =  runFortran.sh
check_LTLIBRARIES = libDriver.la
endif 

BABELDIR   = $(top_builddir)/bin
INCLUDEDIR = $(top_builddir)/runtime/sidl
LIBSIDLSTUBS=$(top_builddir)/lib/sidlstubs/F77/libsidlstub_f77.la
PUREBABELGEN = $(STUBSRCS) $(STUBHDRS) $(IORHDRS)
BABELXTRA = $(STUBFORTRANINC) $(STUBDOCS)
CLEANFILES = $(PUREBABELGEN) $(BABELXTRA) babel-stamp babel-temp \
	babel.make.package babel.make.depends

nodist_libDriver_la_SOURCES      = $(PUREBABELGEN)
libDriver_la_LIBADD       = $(LIBSIDLSTUBS)
libDriver_la_LDFLAGS      = -no-undefined -rpath `pwd`/.libs -release @VERSION@
libDriver_la_LINK = $(F77LINK) $(libDriver_la_LDFLAGS)

if SUPPORT_STATIC
SHARED_PROGS             = HelloDriver
HelloDriver_SOURCES      = HelloDriver.f
HelloDriver_LDFLAGS	 = -dynamic
HelloDriver_LDADD        = libDriver.la $(FLIBS)
HelloDriver_LINK	 = $(LINK) $(FMAIN) $(HelloDriver_LDFLAGS)
endif

EXTRA_DIST = babel.make
AM_CPPFLAGS   = -I$(INCLUDEDIR) -I$(top_builddir)/lib/sidlstubs/F77

$(PUREBABELGEN) $(BABELXTRA) : babel-stamp
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

babel-stamp:
	@rm -f babel-temp
	@touch babel-temp	
	$(SHELL) $(BABELDIR)/babel --client=f77 \
	  --repository-path=../repository \
	  decaf.Framework gov.cca
	@mv -f babel-temp $@

clean-local:
	rm -f *.a *.so
	test "X$(srcdir)" = "X." || rm -f runFortran.sh babel.make

include $(srcdir)/babel.make

# Make sure Makefile.in is newer than babel.make
dist-hook:
	touch -c $(distdir)/Makefile.in
