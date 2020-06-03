##
## File:        Makefile.am
## Package:     Babel Hello examples
## Copyright:   (c) 2000-2001 Lawrence Livermore National Security, LLC
## Revision:    $Revision: 7379 $
## Modified:    $Date: 2011-11-28 09:02:00 -0800 (Mon, 28 Nov 2011) $
## Description: automake makefile for C Hello World client
##

check_PROGRAMS    = $(STATIC_PROGS) $(SHARED_PROGS)
check_SCRIPTS     = runAll.sh
check_LTLIBRARIES = libHelloClient.la

nodist_libHelloClient_la_SOURCES      = $(STUBSRCS) $(STUBHDRS)
libHelloClient_la_LIBADD       = $(LIBSIDL)
libHelloClient_la_LDFLAGS      = -no-undefined -rpath `pwd`/.libs -release @VERSION@


BABELDIR   = $(top_builddir)/bin
INCLUDEDIR = $(top_builddir)/runtime/sidl 
if WITH_SIDLX
INCLUDEDIRX = $(top_builddir)/runtime/sidlx
LIBSIDLX    = $(top_builddir)/runtime/sidlx/libsidlx.la
else
INCLUDEDIRX = $(top_builddir)/runtime/sidl
LIBSIDLX    =
endif
LIBSIDL    = $(top_builddir)/runtime/sidl/libsidl.la

# This little snippet forces automake to define $CXXLINK
EXTRA_PROGRAMS  = automake_hack
automake_hack_SOURCES = 
nodist_EXTRA_automake_hack_SOURCES = automake_hack.cc

if SUPPORT_STATIC
if SUPPORT_FORTRAN77
  RUN_STATIC_FORTRAN77 = runC2F77
else
  RUN_STATIC_FORTRAN77 = 
endif 
if SUPPORT_CXX
  RUN_STATIC_CXX = runC2Cxx
else
  RUN_STATIC_CXX =
endif

STATIC_PROGS          = runC2C $(RUN_STATIC_CXX) $(RUN_STATIC_FORTRAN77)

runC2C_SOURCES        = helloclient.c
runC2C_LDFLAGS        = -static-libtool-libs
runC2C_LDADD          = libHelloClient.la ../libC/libImpl.la $(LIBSIDLX)

runC2Cxx_SOURCES      = helloclient.c
runC2Cxx_LDFLAGS      = -static-libtool-libs
runC2Cxx_LDADD        = libHelloClient.la ../libCxx/libImpl.la $(LIBSIDLX)
runC2Cxx_LINK         = $(CXXLINK) $(runC2Cxx_LDFLAGS)

runC2F77_SOURCES      = helloclient.c
runC2F77_LDFLAGS      = -static-libtool-libs
runC2F77_DEPENDENCIES = libHelloClient.la ../libF77/libImpl.la
runC2F77_LDADD        = libHelloClient.la ../libF77/libImpl.la $(LIBSIDLX) $(FLIBS)
endif
if SUPPORT_SHARED
SHARED_PROGS          = runAll
runAll_SOURCES        = helloclient.c
runAll_LDFLAGS	      = -dynamic
runAll_DEPENDENCIES   = libHelloClient.la
runAll_LDADD          = libHelloClient.la $(LIBSIDLX)
endif

helloclient.$(OBJEXT) : $(STUBHDRS) $(IORHDRS)

EXTRA_DIST = babel.make 

AM_CPPFLAGS   = -I. -I$(INCLUDEDIR) -I$(INCLUDEDIRX)

$(STUBSRCS) $(STUBHDRS) $(IORHDRS) : babel-stamp
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
	   --client=C $(srcdir)/../Hello.sidl
	@mv -f babel-temp $@

CLEANFILES = babel-stamp babel-temp $(STUBSRCS) $(STUBHDRS) $(IORHDRS) \
	babel.make.package babel.make.depends

clean-local:
	rm -rf ti_files
	test "X$(srcdir)" = "X." || rm -f babel.make

include $(srcdir)/babel.make

# Make sure Makefile.in is newer than babel.make
dist-hook:
	touch -c $(distdir)/Makefile.in
