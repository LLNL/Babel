##
## File:        Makefile.am
## Package:     Babel examples
## Copyright:   (c) 2000-2001 Lawrence Livermore National Security, LLC
## Revision:    $Revision: 7368 $
## Modified:    $Date: 2011-11-22 15:23:42 -0800 (Tue, 22 Nov 2011) $
## Description: automake makefile for f77 Hello World server
##

AUTOMAKE_OPTIONS = foreign no-dependencies no-installinfo	\
                   no-installman no-texinfo.tex 1.7

if SUPPORT_FORTRAN77
check_LTLIBRARIES = libImpl.la
endif
BABEL=`if test "$(BABELDIR)" != "$(top_builddir)/bin" ; then echo babel | sed '$(transform)' ; else echo babel; fi`
BABELDIR   = $(top_builddir)/bin
INCLUDEDIR = $(top_builddir)/runtime/sidl 
LIBSIDLSTUBS = $(top_builddir)/lib/sidlstubs/F77/libsidlstub_f77.la

BABELGEN=$(IMPLSRCS)
PUREBABELGEN=$(IORSRCS) $(STUBSRCS) $(SKELSRCS) $(IORHDRS) $(STUBFORTRANINC) \
	$(STUBHDRS)
DOCFILES=$(STUBDOCS) 
CLEANFILES=$(PUREBABELGEN) babel-stamp babel-temp libImpl.scl \
	$(DOCFILES) stamp-installcheck core babel.make.package \
	babel.make.depends

libImpl_la_SOURCES      = $(BABELGEN)
nodist_libImpl_la_SOURCES = $(PUREBABELGEN)
libImpl_la_LIBADD       = $(LIBSIDLSTUBS)
libImpl_la_LDFLAGS      = -no-undefined -rpath `pwd`/.libs -release @VERSION@ -module

EXTRA_DIST = babel.make
AM_CPPFLAGS   = -I$(INCLUDEDIR) -I$(top_builddir)/lib/sidlstubs/F77
AM_FFLAGS = $(AM_CPPFLAGS)

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

babel-stamp: $(srcdir)/../Hello.sidl
	@rm -f babel-temp
	@touch babel-temp
	$(SHELL) $(BABELDIR)/$(BABEL) --vpath="$(srcdir)" --server=f77 \
	  $(srcdir)/../Hello.sidl
	$(SHELL) $(top_srcdir)/bin/generate_scl libImpl.scl `pwd`/libImpl.la \
	   $(IORSRCS)
	@mv -f babel-temp $@

clean-local:
	rm -f *.a *.so *.scl
	test "X$(srcdir)" = "X." || rm -f $(IMPLSRCS) babel.make

include $(srcdir)/babel.make

# Make sure Makefile.in is newer than babel.make
dist-hook:
	touch -c $(distdir)/Makefile.in
