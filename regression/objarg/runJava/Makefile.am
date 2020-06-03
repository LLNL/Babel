##
## File:        Makefile.am
## Package:     Babel regression checks
## Revision:    $Revision: 7498 $
## Modified:    $Date: 2012-10-30 12:10:29 -0700 (Tue, 30 Oct 2012) $
## Description: automake makefile for Java regression tests
##
## Copyright (c) 2000-2001, The Regents of the University of Calfornia.
## Produced at the Lawrence Livermore National Laboratory.
## Written by the Components Team <components@llnl.gov>
## UCRL-CODE-2002-054
## All rights reserved.
## 
## This file is part of Babel. For more information, see
## http://www.llnl.gov/CASC/components/. Please read the COPYRIGHT file
## for Our Notice and the LICENSE file for the GNU Lesser General Public
## License.
## 
## This program is free software; you can redistribute it and/or modify it
## under the terms of the GNU Lesser General Public License (as published by
## the Free Software Foundation) version 2.1 dated February 1999.
## 
## This program is distributed in the hope that it will be useful, but
## WITHOUT ANY WARRANTY; without even the IMPLIED WARRANTY OF
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the terms and
## conditions of the GNU Lesser General Public License for more details.
## 
## You should have received a copy of the GNU Lesser General Public License
## along with this program; if not, write to the Free Software Foundation,
## Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

SIDLFILE = $(srcdir)/../objarg.sidl
TESTFILE = ObjargTestDriver.java
JAVAPKG = objarg

AUTOMAKE_OPTIONS = foreign no-dependencies no-installinfo	\
                   no-installman no-texinfo.tex 1.7

OUTPUTSIDL=$(srcdir)/../../output/output.sidl

if SUPPORT_JAVA
check_SCRIPTS = runJava2C.sh runJava2Cxx.sh runJava2F90.sh runJava2F03.sh \
	runJava2Py.sh runJava2Java.sh
check_LTLIBRARIES = libBrtClient.la
SCLFILE=libClient.scl
endif

VERSION=@VERSION@
BABEL=`if test "$(BABELDIR)" != "$(top_builddir)/bin" ; then echo babel | sed '$(transform)' ; else echo babel; fi`
BABELDIR    = $(top_builddir)/bin
if WITH_FASTCALL
BABELFLAGS=--fast-call
else
BABELFLAGS=
endif
INCLUDEDIR  = $(top_builddir)/runtime/sidl
INCLUDEDIR2 = .
STUBINCLUDEDIR = $(top_builddir)/lib/sidlstubs/Java
LIBSIDL     = $(top_builddir)/runtime/sidl/libsidl.la
LIBSYNC     = ../../output/libC/libOutput.la
PUREBABELGEN = $(STUBSRCS) $(STUBHDRS) $(IORHDRS)
CLEANFILES = $(PUREBABELGEN) babel-temp babel-stamp stamp-installcheck \
	babel.make.package java-temp java-stamp babel.make.depends


nodist_libBrtClient_la_SOURCES = $(PUREBABELGEN)
libBrtClient_la_LIBADD       = $(LIBSYNC) $(LIBSIDL) 
libBrtClient_la_LDFLAGS      = -no-undefined -rpath `pwd`/.libs \
			    -release $(VERSION)

EXTRA_DIST = $(TESTFILE) synch_babel.make babel.make $(JAVAPKG)/babel.make \
	synch/synch_babel.make
AM_CPPFLAGS   = -I. -I$(INCLUDEDIR) -I$(INCLUDEDIR2) -I$(STUBINCLUDEDIR) $(JNI_INCLUDES)

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

CP = $(top_builddir)/lib/sidl-$(VERSION).jar:$(top_builddir)/lib/sidlstubs/Java/sidlstub_$(VERSION).jar
if SUPPORT_CYGWIN
  CLASSPATH = `cygpath --path --windows "$(CP):."`
else
  CLASSPATH = $(CP):.
endif
JAVAC_FLAGS = -g -d . -classpath "$(CLASSPATH)"

babel-stamp: $(SIDLFILE) $(TESTFILE) $(OUTPUTSIDL)
	@rm -f babel-temp
	@touch babel-temp
	$(SHELL) $(BABELDIR)/$(BABEL) --client=java \
		$(BABELFLAGS) $(BABELEXTRA) $(SIDLFILE)
	$(SHELL) $(BABELDIR)/$(BABEL) --client=java --make-prefix=synch_  \
	  $(OUTPUTSIDL)
	@mv -f babel-temp babel-stamp

libClient.scl : $(STUBSRCS)
	rm -f libClient.scl
	echo "<?xml version=\"1.0\" ?>" > libClient.scl
	echo "<scl>" >> libClient.scl
	if test `uname` = "Darwin"; then \
	  scope="global"; \
	else \
	  scope="local"; \
	fi ; \
	echo "  <library uri=\""`pwd`"/libBrtClient.la\" scope=\"$$scope\" resolution=\"lazy\" >" >> libClient.scl
	grep __register $(STUBSRCS) /dev/null | awk 'BEGIN {FS=":"} { print $$1}' | sort -u | sed -e 's/_jniStub.c//g' -e 's/_/./g' | awk ' { printf "    <class name=\"%s\" desc=\"java\" />\n", $$1 }' >> libClient.scl
	echo "  </library>" >> libClient.scl
	echo "</scl>" >> libClient.scl

if SUPPORT_JAVA
java-stamp: babel-stamp
	@rm -f java-temp
	@touch java-temp
	if test "X$(srcdir)" = "X."; then				    \
	  $(JAVAC) $(JAVAC_FLAGS) `find . \( -name "*.java" -print \) -o \( -name .svn -prune \)`;	    \
	else								    \
	  $(JAVAC) $(JAVAC_FLAGS) `find $(srcdir) . \( -name "*.java" -print \) -o \( -name .svn -prune \)`; \
	fi
	@mv -f java-temp java-stamp
else
java-stamp:
	touch java-stamp
endif

clean-local:
	rm -f *.a *.so *.scl *.class *.testresult 
	rm -f $(JAVAPKG)/*.class $(JAVAPKG)/*.java
	rm -f synch/*.class synch/*.java
	rm -rf `find . \( \( -name $(JAVAPKG) -o -name synch -o -name .svn -o -name .libs -o -name .deps -o -name CVS -o -name "*.o" -o -name "*.lo" \) -prune \) -o \( -type d ! -name "." -print \)`
	test "X$(srcdir)" = "X." || rm -f babel.make synch_babel.make synch/synch_babel.make \
		$(check_SCRIPTS)

update-babel-make:babel-stamp
	if test "X$(srcdir)" != "X."; then \
	   cp babel.make $(srcdir); \
	   cp $(JAVAPKG)/babel.make $(srcdir)/$(JAVAPKG); \
	   cp synch/babel.make $(srcdir)/synch/babel.make; \
	fi

check-local:
	@if test -f stamp-installcheck; then            \
	  echo $(MAKE) $(AM_MAKEFLAGS) clean;           \
	  $(MAKE) $(AM_MAKEFLAGS) clean;                \
	fi
	$(MAKE) $(AM_MAKEFLAGS)				\
	  $(check_LTLIBRARIES) $(check_PROGRAMS) $(check_SCRIPTS) \
	  java-stamp $(SCLFILE)

installcheck-local:
	@if test ! -f stamp-installcheck; then          \
	  echo $(MAKE) $(AM_MAKEFLAGS) clean;           \
	  $(MAKE) $(AM_MAKEFLAGS) clean;                \
	fi
	$(MAKE) $(AM_MAKEFLAGS)                         \
	  BABELDIR=$(bindir)				\
	  INCLUDEDIR=$(includedir)                  \
	  INCLUDEDIR2=$(exec_prefix)/include            \
	  CP=$(libdir)/sidl-$(VERSION).jar:$(libdir)/sidlstub_$(VERSION).jar \
	  LIBSIDL=$(libdir)/libsidl.la		\
	  $(check_LTLIBRARIES) $(check_PROGRAMS) $(check_SCRIPTS) \
	  java-stamp $(SCLFILE)
	touch stamp-installcheck

# Make sure Makefile.in is newer than babel.make
dist-hook:
	touch -c $(distdir)/Makefile.in

include $(srcdir)/babel.make
