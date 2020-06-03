##
## File:        Makefile.am
## Package:     Babel Hello example
## Copyright:   (c) 2000-2001 Lawrence Livermore National Security, LLC
## Revision:    $Revision: 7368 $
## Modified:    $Date: 2011-11-22 15:23:42 -0800 (Tue, 22 Nov 2011) $
## Description: automake makefile for Java hello world client
##

AUTOMAKE_OPTIONS = foreign no-dependencies no-installinfo	\
                   no-installman no-texinfo.tex 1.7

if SUPPORT_JAVA
check_SCRIPTS     = runJava2C.sh runJava2Cxx.sh runJava2F77.sh runJava2Java.sh runJava2Py.sh
check_LTLIBRARIES = libHelloClient.la
endif

nodist_libHelloClient_la_SOURCES      = $(STUBSRCS) $(STUBHDRS) $(IORHDRS) 
libHelloClient_la_LIBADD       = $(LIBSIDL) 
libHelloClient_la_LDFLAGS      = -no-undefined -rpath `pwd`/.libs -release @VERSION@

BABELDIR   = $(top_builddir)/bin
INCLUDEDIR = $(top_builddir)/runtime/sidl 
STUBINCLUDEDIR = $(top_builddir)/lib/sidlstubs/Java
LIBSIDL    = $(top_builddir)/runtime/sidl/libsidl.la
VERSION=@VERSION@
sidlJAR=$(top_builddir)/lib/sidl-$(VERSION).jar
SIDLSTUBSJAR=$(top_builddir)/lib/sidlstubs/Java/sidlstub_$(VERSION).jar
CLEANFILES = $(STUBSRCS) $(STUBHDRS) $(IORHDRS) babel-temp babel-stamp \
	babel.make.package babel.make.depends


EXTRA_DIST = babel.make
AM_CPPFLAGS   = -I$(INCLUDEDIR) -I$(STUBINCLUDEDIR)  $(JNI_INCLUDES)

$(STUBSRCS) : babel-stamp
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
  CLASSPATH = `cygpath --path --windows "$(sidlJAR)"`
else
  CLASSPATH = $(sidlJAR):$(SIDLSTUBSJAR)
endif
JAVAC_FLAGS = -g -d . -classpath "$(CLASSPATH)"

babel-stamp: $(srcdir)/../Hello.sidl
	@rm -f babel-temp
	@touch babel-temp
	$(SHELL) $(BABELDIR)/babel  \
		--client=java $(srcdir)/../Hello.sidl
	if test "X$(srcdir)" = "X."; then				    \
	  $(JAVAC) $(JAVAC_FLAGS) `find $(srcdir)			    \
	     -name .svn -prune -o					    \
	     -name CVS -prune -o -name SCCS -prune -o 			    \
             -name "*.java" -print`;					    \
	else								    \
	  $(JAVAC) $(JAVAC_FLAGS) `find $(srcdir) .			    \
	     -name .svn -prune -o					    \
	     -name CVS -prune -o -name SCCS -prune -o 			    \
             -name "*.java" -print`;					    \
	fi
	rm -f libHelloClient.scl
	echo "<?xml version=\"1.0\" ?>" > libHelloClient.scl
	echo "<scl>" >> libHelloClient.scl
	echo "  <library uri=\""`pwd`"/libHelloClient.la\" scope=\"local\" resolution=\"lazy\" >" >> libHelloClient.scl
	grep  __register $(STUBSRCS) /dev/null | awk 'BEGIN {FS=":"} { print $$1}' | sort -u | sed -e 's/_jniStub.c//g' -e 's/_/./g' | awk ' { printf "    <class name=\"%s\" desc=\"java\" />\n", $$1 }' >> libHelloClient.scl
	echo "  </library>" >> libHelloClient.scl
	echo "</scl>" >> libHelloClient.scl
	@mv -f babel-temp babel-stamp


clean-local:
	rm -f *.a *.so *.scl *.class
	rm -rf Hello sidl
	test "X$(srcdir)" = "X." || rm -f babel.make $(noinst_SCRIPTS)

include $(srcdir)/babel.make

# Make sure Makefile.in is newer than babel.make
dist-hook:
	touch -c $(distdir)/Makefile.in
