##
## File:        Makefile.am
## Package:     Babel CCA example
## Copyright:   (c) 2000-2001 Lawrence Livermore National Security, LLC
## Revision:    $Revision: 7368 $
## Modified:    $Date: 2011-11-22 15:23:42 -0800 (Tue, 22 Nov 2011) $
## Description: automake makefile for Java CCA hello world driver
##

if SUPPORT_JAVA
check_SCRIPTS     = runJava.sh
check_LTLIBRARIES = libDriver.la
endif

BABEL=`if test "$(BABELDIR)" != "$(top_builddir)/bin" ; then echo babel | sed '$(transform)' ; else echo babel; fi`
BABELDIR   = $(top_builddir)/bin
INCLUDEDIR = $(top_builddir)/runtime/sidl
INCLUDEDIR2 = $(top_builddir)/lib/sidlstubs/Java
LIBSIDL    = $(top_builddir)/runtime/sidl/libsidl.la
SIDLJAR    = $(top_builddir)/lib/sidl-@VERSION@.jar:$(top_builddir)/lib/sidlstubs/Java/sidlstub_@VERSION@.jar
PUREBABELGEN = $(STUBSRCS) $(STUBHDRS) $(IORHDRS) 
CLEANFILES = $(PUREBABELGEN) babel-stamp babel-temp stamp-installcheck  \
	babel.make.package babel.make.depends

nodist_libDriver_la_SOURCES      = $(PUREBABELGEN)
libDriver_la_LIBADD       = $(LIBSIDL) 
libDriver_la_LDFLAGS      = -no-undefined -rpath `pwd`/.libs -release @VERSION@

EXTRA_DIST = babel.make HelloDriver.java
AM_CPPFLAGS   = -I$(INCLUDEDIR) -I$(INCLUDEDIR2) $(JNI_INCLUDES)

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

if SUPPORT_CYGWIN
  CLASSPATH = `cygpath --path --windows "$(SIDLJAR)"`
else
  CLASSPATH = $(SIDLJAR)
endif
JAVAC_FLAGS = -g -d . -classpath "$(CLASSPATH)"

babel-stamp:
	@rm -f babel-temp
	@touch babel-temp
	$(SHELL) $(BABELDIR)/$(BABEL) --client=java \
	  --repository-path=../repository \
	  decaf.Framework gov.cca
	if test "X$(srcdir)" = "X."; then				    \
	  $(JAVAC) $(JAVAC_FLAGS) `find $(srcdir)			    \
	     -name .svn -prune -o					    \
	     -name CVS -prune -o -name SCCS -prune -o 			    \
             -name "*.java" -print`;					    \
	else								    \
	  $(JAVAC) $(JAVAC_FLAGS) `find $(srcdir) . 			    \
	     -name .svn -prune -o					    \
	     -name CVS -prune -o -name SCCS -prune -o			    \
             -name "*.java" -print`;					    \
	fi
	rm -f libDriver.scl
	echo "<?xml version=\"1.0\" ?>" > libDriver.scl
	echo "<scl>" >> libDriver.scl
	echo "  <library uri=\""`pwd`"/libDriver.la\" scope=\"local\" resolution=\"lazy\" >" >> libDriver.scl
	grep  __register $(STUBSRCS) /dev/null | awk 'BEGIN {FS=":"} { print $$1}' | sort -u | sed -e 's/_jniStub.c//g' -e 's/_/./g' | awk ' { printf "    <class name=\"%s\" desc=\"java\" />\n", $$1 }' >> libDriver.scl
	echo "  </library>" >> libDriver.scl
	echo "</scl>" >> libDriver.scl
	@mv babel-temp $@

clean-local:
	rm -f *.a *.so *.scl *.class 
	rm -rf decaf gov sidl 
	test "X$(srcdir)" = "X." || rm -f babel.make $(noinst_SCRIPTS)

include $(srcdir)/babel.make

# Make sure Makefile.in is newer than babel.make
dist-hook:
	touch -c $(distdir)/Makefile.in
