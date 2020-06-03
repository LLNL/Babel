##
## File:        Makefile.am
## Package:     Babel examples
## Copyright:   (c) 2000-2001 Lawrence Livermore National Security, LLC
## Revision:    $Revision: 7368 $
## Modified:    $Date: 2011-11-22 15:23:42 -0800 (Tue, 22 Nov 2011) $
## Description: automake makefile for cca libdecaf example
##

AUTOMAKE_OPTIONS = foreign no-dependencies no-installinfo no-installman \
                   no-texinfo.tex 1.7

check_LTLIBRARIES = libdecaf.la

BABELDIR   = $(top_builddir)/bin
INCLUDEDIR = $(top_builddir)/lib/sidlstubs/Cxx
LIBSIDLSTUBS =$(top_builddir)/lib/sidlstubs/Cxx/libsidlstub_cxx.la
CLEANFILES=babel.make.package babel-stamp babel-temp babel.make.depends \
	$(IORHDRS) $(IORSRCS) $(SKELSRCS) $(STUBHDRS) $(STUBSRCS)

libdecaf_la_SOURCES      = $(IMPLSRCS) $(IMPLHDRS)
nodist_libdecaf_la_SOURCES = $(STUBSRCS) $(STUBHDRS) $(IORSRCS) $(IORHDRS) $(SKELSRCS)
libdecaf_la_LIBADD       = $(LIBSIDLSTUBS)
libdecaf_la_LDFLAGS      = -no-undefined -rpath `pwd`/.libs -release @VERSION@ -module 

EXTRA_DIST =  decaf_aux.hxx decaf.sidl babel.make 
AM_CPPFLAGS   = -I. -I$(INCLUDEDIR)

$(IORSRCS) $(STUBSRCS) $(SKELSRCS) $(IMPLSRCS): babel-stamp
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

babel-stamp: $(srcdir)/decaf.sidl
	@rm -f babel-temp
	touch babel-temp
	rm -f ../repository/decaf*.xml
	$(SHELL) $(BABELDIR)/babel --text=xml --repository-path=../repository \
	  --output-directory=../repository $(srcdir)/decaf.sidl
	$(SHELL) $(BABELDIR)/babel --vpath="$(srcdir)" --server=C++ \
	  --repository-path=../repository \
	  decaf gov.cca
	rm -f libdecaf.scl
	echo "<?xml version=\"1.0\" ?>" > libdecaf.scl
	echo "<scl>" >> libdecaf.scl
	echo "  <library uri=\""`pwd`"/libdecaf.la\" scope=\"local\" resolution=\"lazy\" >" >> libdecaf.scl
	grep  __set_epv $(IORSRCS) /dev/null | awk 'BEGIN {FS=":"} { print $$1}' | sort -u | sed -e 's/_IOR.c//g' -e 's/_/./g' | awk ' { printf "    <class name=\"%s\" desc=\"ior/impl\" />\n", $$1 }' >> libdecaf.scl
	echo "  </library>" >> libdecaf.scl
	echo "</scl>" >> libdecaf.scl
	@mv -f babel-temp $@

clean-local:
	rm -f *.a *.so *.scl
	rm -rf ti_files
	test "X$(srcdir)" = "X." || rm -f $(IMPLSRCS) $(IMPLHDRS) babel.make

include $(srcdir)/babel.make


# Make sure Makefile.in is newer than babel.make
dist-hook:
	touch -c $(distdir)/Makefile.in
