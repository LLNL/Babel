##
## File:        Makefile.am
## Package:     Babel examples
## Copyright:   (c) 2000-2001 Lawrence Livermore National Security, LLC
## Revision:    $Revision: 7368 $
## Modified:    $Date: 2011-11-22 15:23:42 -0800 (Tue, 22 Nov 2011) $
## Description: automake makefile for cca hello server example
##

if SUPPORT_FORTRAN77
check_LTLIBRARIES = libHelloServer.la
endif

BABEL=`if test "$(BABELDIR)" != "$(top_builddir)/bin" ; then echo babel | sed '$(transform)' ; else echo babel; fi`
BABELDIR   = $(top_builddir)/bin
INCLUDEDIR = $(top_builddir)/runtime/sidl
LIBSIDLSTUB= $(top_builddir)/lib/sidlstubs/F77/libsidlstub_f77.la
PUREBABELGEN = $(STUBSRCS) $(IORSRCS) $(STUBHDRS) $(IORHDRS) $(SKELSRCS) 
HANDTUNEDGEN = $(IMPLSRCS)
CLEANFILES = $(PUREBABELGEN) $(STUBDOCS) $(STUBFORTRANINC) babel-temp \
	babel-stamp stamp-installcheck babel.make.package \
	babel.make.depends

nodist_libHelloServer_la_SOURCES      =  $(PUREBABELGEN)
libHelloServer_la_SOURCES      = $(HANDTUNEDGEN)
libHelloServer_la_LIBADD       = $(LIBSIDLSTUB)
libHelloServer_la_LDFLAGS      = -no-undefined -rpath `pwd`/.libs -release @VERSION@ -module 
libHelloServer_la_LINK		= $(F77LINK) $(libHelloServer_la_LDFLAGS)

EXTRA_DIST = hello-server.sidl babel.make

AM_CPPFLAGS   = -I. -I$(INCLUDEDIR) -I$(top_builddir)/lib/sidlstubs/F77

$(PUREBABELGEN) $(HANDTUNEDGEN) : babel-stamp
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

babel-stamp: $(srcdir)/hello-server.sidl
	@rm -f babel-temp
	@touch babel-temp
	rm -f ../repository/HelloServer*.xml
	$(SHELL) $(BABELDIR)/$(BABEL) --text=xml \
          --repository-path=../repository \
	  --output-directory=../repository $(srcdir)/hello-server.sidl
	$(SHELL) $(BABELDIR)/$(BABEL) --vpath="$(srcdir)" --server=f77 \
          --repository-path=../repository \
	  HelloServer gov.cca.Component gov.cca.Port strop.StringProducerPort \
	  gov.cca.Services gov.cca.TypeMap gov.cca.CCAException \
	  gov.cca.ComponentID gov.cca.ComponentRelease gov.cca.CCAExceptionType \
	  gov.cca.Type gov.cca.TypeMismatchException
	rm -f libHelloServer.scl
	echo "<?xml version=\"1.0\" ?>" > libHelloServer.scl
	echo "<scl>" >> libHelloServer.scl
	echo "  <library uri=\""`pwd`"/libHelloServer.la\" scope=\"local\" resolution=\"lazy\" >" >> libHelloServer.scl
	grep __set_epv $(IORSRCS) /dev/null | awk 'BEGIN {FS=":"} { print $$1}' | sort -u | sed -e 's/_IOR.c//g' -e 's/_/./g' | awk ' { printf "    <class name=\"%s\" desc=\"ior/impl\" />\n", $$1 }' >> libHelloServer.scl
	echo "  </library>" >> libHelloServer.scl
	echo "</scl>" >> libHelloServer.scl
	@mv -f babel-temp $@

clean-local:
	rm -f *.a *.so *.scl
	test "X$(srcdir)" = "X." || rm -f $(IMPLSRCS) $(IMPLHDRS) babel.make

include $(srcdir)/babel.make

# Make sure Makefile.in is newer than babel.make
dist-hook:
	touch -c $(distdir)/Makefile.in
