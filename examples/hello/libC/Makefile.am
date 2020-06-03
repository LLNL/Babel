##
## File:        Makefile.am
## Package:     Babel Hello example
## Copyright:   (c) 2000-2001 Lawrence Livermore National Security, LLC
## Revision:    $Revision: 7368 $
## Modified:    $Date: 2011-11-22 15:23:42 -0800 (Tue, 22 Nov 2011) $
## Description: automake makefile for C Hello World server
##

check_LTLIBRARIES = libImpl.la

BABELDIR   = $(top_builddir)/bin
INCLUDEDIR = $(top_builddir)/runtime/sidl
LIBSIDL    = $(top_builddir)/runtime/sidl/libsidl.la
PUREBABELGEN = $(IORSRCS) $(SKELSRCS) $(STUBSRCS) $(STUBHDRS) $(IORHDRS) 
BABELGEN = $(IMPLSRCS) $(IMPLHDRS)
CLEANFILES = $(PUREBABELGEN) libImpl.scl babel-stamp babel-temp \
	babel.make.package babel.make.depends

nodist_libImpl_la_SOURCES = $(PUREBABELGEN)
dist_libImpl_la_SOURCES = $(BABELGEN)
libImpl_la_LIBADD         = $(LIBSIDL) 
libImpl_la_LDFLAGS      = -no-undefined -rpath `pwd`/.libs -release @VERSION@ -module

EXTRA_DIST = babel.make
AM_CPPFLAGS   = -I$(INCLUDEDIR)

$(PUREBABELGEN) $(BABELGEN) libImpl.scl : babel-stamp
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

babel-stamp : $(srcdir)/../Hello.sidl
	@rm -f babel-temp
	@touch babel-temp
	$(SHELL) $(BABELDIR)/babel --vpath="$(srcdir)" --server=C \
	  $(srcdir)/../Hello.sidl
	$(SHELL) $(top_srcdir)/bin/generate_scl libImpl.scl `pwd`/libImpl.la \
	   $(IORSRCS)
	@mv babel-temp $@

clean-local:
	test "X$(srcdir)" = "X." || rm -f $(IMPLHDRS) $(IMPLSRCS) babel.make

include $(srcdir)/babel.make

# Make sure Makefile.in is newer than babel.make
dist-hook:
	touch -c $(distdir)/Makefile.in
