##
## File:        Makefile.am
## Package:     Babel Hello examples
## Copyright:   (c) 2000-2001 Lawrence Livermore National Security, LLC
## Revision:    $Revision: 7379 $
## Modified:    $Date: 2011-11-28 09:02:00 -0800 (Mon, 28 Nov 2011) $
## Description: automake makefile for F77 Hello World client
##

LIBSIDLSTUBS = $(top_builddir)/lib/sidlstubs/F77/libsidlstub_f77.la

if SUPPORT_FORTRAN77
check_PROGRAMS    = $(STATIC_PROGS) $(SHARED_PROGS)
check_SHARED      = runAll.sh
check_LTLIBRARIES = libHelloClient.la
endif


# This little snippet forces automake to define $CXXLINK
EXTRA_PROGRAMS  = automake_hack
automake_hack_SOURCES = 
nodist_EXTRA_automake_hack_SOURCES = automake_hack.cc

nodist_libHelloClient_la_SOURCES      = $(PUREBABELGEN)
libHelloClient_la_LIBADD       = $(LIBSIDLSTUBS)
libHelloClient_la_LDFLAGS      = -no-undefined -rpath `pwd`/.libs -release @VERSION@
libHelloClient_la_LINK = $(F77LINK) $(libHelloClient_la_LDFLAGS)

BABELDIR   = $(top_builddir)/bin
INCLUDEDIR = $(top_builddir)/runtime/sidl 
PUREBABELGEN = $(STUBSRCS) $(STUBHDRS) $(IORHDRS)
BABELXTRA = $(STUBDOCS) $(STUBFORTRANINC)
CLEANFILES = $(PUREBABELGEN) $(BABELXTRA) babel-stamp babel-temp \
	babel.make.package babel.make.depends

if SUPPORT_STATIC
STATIC_PROGS       = runF772C runF772Cxx runF772F77

runF772C_SOURCES        = helloclient.f
runF772C_LDFLAGS        = -static-libtool-libs
runF772C_LDADD          = libHelloClient.la ../libC/libImpl.la $(LIBSIDLSTUBS) $(FLIBS)
runF772C_LINK		= $(LINK)  $(FMAIN) $(runF772C_LDFLAGS)
# use C linker instead of F77 to avoid problem when compiling with Intel's
# icc with optimization (undefined _intel_fast_memcpy symbol)

runF772Cxx_SOURCES      = helloclient.f
runF772Cxx_LDFLAGS      = -static-libtool-libs
runF772Cxx_LDADD        = libHelloClient.la ../libCxx/libImpl.la $(LIBSIDLSTUBS) $(FLIBS)
runF772Cxx_LINK         = $(CXXLINK) $(FMAIN) $(runF772Cxx_LDFLAGS)

runF772F77_SOURCES      = helloclient.f
runF772F77_LDFLAGS      = -static-libtool-libs
runF772F77_LDADD        =  libHelloClient.la ../libF77/libImpl.la $(LIBSIDLSTUBS) $(FLIBS)
runF772F77_LINK		= $(LINK)  $(FMAIN) $(runF772F77_LDFLAGS)

endif
if SUPPORT_SHARED
SHARED_PROGS            = runAll
runAll_SOURCES          = helloclient.f
runAll_LDFLAGS		= -dynamic
runAll_LDADD            = libHelloClient.la $(LIBSIDLSTUBS) $(FLIBS)
runAll_LINK		= $(LINK) $(FMAIN) $(runAll_LDFLAGS)
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


babel-stamp: $(srcdir)/../Hello.sidl
	@rm -f babel-temp
	@touch babel-temp
	$(SHELL) $(BABELDIR)/babel \
	   --client=f77 $(srcdir)/../Hello.sidl
	@mv -f babel-temp $@

update-babel-make:babel-stamp
	if test "X$(srcdir)" != "X."; then \
	   cp babel.make $(srcdir); \
	fi

clean-local:
	rm -f *.a *.so
	rm -rf ti_files
	test "X$(srcdir)" = "X." || rm -f babel.make

include $(srcdir)/babel.make

# Make sure Makefile.in is newer than babel.make
dist-hook:
	touch -c $(distdir)/Makefile.in
