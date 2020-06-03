##
## File:        Makefile.am
## Package:     Babel Hello examples
## Copyright:   (c) 2000-2001 Lawrence Livermore National Security, LLC
## Revision:    $Revision: 7379 $
## Modified:    $Date: 2011-11-28 09:02:00 -0800 (Mon, 28 Nov 2011) $
## Description: automake makefile for C++2 Hello World client
##

if SUPPORT_CXX
check_PROGRAMS    = $(STATIC_PROGS) $(SHARED_PROGS)
check_SCRIPTS     = runAll.sh
check_LTLIBRARIES = libHelloClient.la
endif

BABELDIR   = $(top_builddir)/bin
INCLUDEDIR = $(top_builddir)/runtime/sidl
LIBSIDLSTUBS  = $(top_builddir)/lib/sidlstubs/Cxx/libsidlstub_cxx.la
PUREBABELGEN   = $(STUBSRCS) $(STUBHDRS) $(IORHDRS) 
SIDLFILES  = $(srcdir)/../Hello.sidl

nodist_libHelloClient_la_SOURCES      = $(PUREBABELGEN)
libHelloClient_la_LIBADD       = $(LIBSIDLSTUBS)
libHelloClient_la_LDFLAGS      = -no-undefined -rpath `pwd`/.libs -release @VERSION@

if SUPPORT_STATIC
if SUPPORT_FORTRAN77
  RUN_STATIC_FORTRAN77 = runCxx2F77
else
  RUN_STATIC_FORTRAN77 = 
endif 

#STATIC_PROGS            = runCxx2C runCxx2Cxx runCxx2Cxx $(RUN_STATIC_FORTRAN77)
STATIC_PROGS            = runCxx2C runCxx2Cxx $(RUN_STATIC_FORTRAN77)

runCxx2C_SOURCES        = helloclient.cxx
runCxx2C_LDFLAGS        = -static-libtool-libs
runCxx2C_LDADD          = libHelloClient.la ../libC/libImpl.la

runCxx2Cxx_SOURCES      = helloclient.cxx
runCxx2Cxx_LDFLAGS      = -static-libtool-libs
runCxx2Cxx_LDADD        = libHelloClient.la ../libCxx/libImpl.la

runCxx2F77_SOURCES      = helloclient.cxx
runCxx2F77_LDFLAGS      = -static-libtool-libs
runCxx2F77_LDADD        = libHelloClient.la ../libF77/libImpl.la $(FLIBS)
endif
if SUPPORT_SHARED
SHARED_PROGS            = runAll
runAll_SOURCES          = helloclient.cxx
runAll_LDFLAGS		= -dynamic
runAll_LDADD            = libHelloClient.la
endif

helloclient.$(OBJEXT) : $(STUBHDRS) $(IORHDRS)

EXTRA_DIST = babel.make
AM_CPPFLAGS   = -I$(INCLUDEDIR) -I$(top_builddir)/lib/sidlstubs/Cxx

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

babel-stamp: $(SIDLFILES)
	@rm -f babel-temp
	@touch babel-temp
	$(SHELL) $(BABELDIR)/babel --client=Cxx \
		$(SIDLFILES)
	@mv -f babel-temp $@

update-babel-make: babel-stamp
	if test "X$(srcdir)" != "X."; then \
	   cp babel.make $(srcdir); \
	fi

CLEANFILES = babel-temp babel-stamp $(PUREBABELGEN) babel.make.package \
	babel.make.depends

clean-local:
	rm -rf ti_files

include $(srcdir)/babel.make

# Make sure Makefile.in is newer than babel.make
dist-hook:
	touch -c $(distdir)/Makefile.in
