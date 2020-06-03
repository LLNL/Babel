## -*- Makefile -*-
## File:        make.wrapJava
## Package:     Babel regression checks
## Revision:    $Revision: 5340 $
## Modified:    $Date: 2006-04-04 14:35:32 -0700 (Tue, 04 Apr 2006) $
## Description: automake makefile for C regression tests
##
## Copyright (c) 2000-2002, Lawrence Livermore National Security, LLC.
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





OUTPUTSIDL=$(srcdir)/../../output/output.sidl

if SUPPORT_JAVA
check_LTLIBRARIES = libImpl.la
SCLFILES=libImpl.scl libClient.scl
endif

VERSION=@VERSION@
BABEL=`if test "$(BABELDIR)" != "$(top_builddir)/bin" ; then echo babel | sed '$(transform)' ; else echo babel; fi`
BABELDIR    = $(top_builddir)/bin
INCLUDEDIR  = $(top_builddir)/runtime/sidl
INCLUDEDIR2 = .
STUBINCLUDEDIR = $(top_builddir)/lib/sidlstubs/Java
LIBSYNC     = ../../output/libC/libOutput.la
CLEANFILES = $(PUREBABELGEN) $(EXTRACLEAN) babel-stamp babel-temp \
	java-stamp java-temp \
	babel.make.package synch_babel.make.package \
	babel.make.depends synch_babel.make.depends \
	client_babel.make.package client_babel.make.depends
PUREBABELGEN = $(STUBSRCS) $(STUBHDRS) $(IORSRCS) $(IORHDRS) $(SKELSRCS) \
	$(synch_STUBSRCS) $(synch_STUBHDRS) $(synch_IORHDRS) \
	$(client_STUBSRCS) $(client_STUBHDRS) $(client_IORHDRS)

nodist_libImpl_la_SOURCES      = $(PUREBABELGEN) $(SOURCE_EXTRAS)
libImpl_la_LIBADD       = $(LIBSYNC)
libImpl_la_LDFLAGS      = $(JNI_LDFLAGS) -no-undefined -rpath `pwd`/.libs \
			  -release $(VERSION) -module

EXTRA_DIST = babel.make synch_babel.make $(JAVAPKG)/babel.make \
	synch/synch_babel.make  $(JAVAPKG)/client_babel.make \
	client_babel.make $(TESTFILE)
AM_CPPFLAGS   = -I. -I$(INCLUDEDIR) -I$(INCLUDEDIR2) -I$(STUBINCLUDEDIR) $(JNI_INCLUDES)


CP = $(top_builddir)/lib/sidl-$(VERSION).jar:$(top_builddir)/lib/sidlstubs/Java/sidlstub_$(VERSION).jar
if SUPPORT_CYGWIN
  CLASSPATH = `cygpath --path --windows "$(CP):."`
else
  CLASSPATH = $(CP):.
endif
JAVAC_FLAGS = -g -source 1.4 -target 1.4 -d . -classpath "$(CLASSPATH)"

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

babel-stamp: $(SIDLFILE) $(OUTPUTSIDL) $(TESTFILE)
	@rm -f babel-temp
	@touch babel-temp
	$(SHELL) $(BABELDIR)/$(BABEL) --vpath="$(srcdir)" --server=java \
	  $(BABELEXTRA) $(SIDLFILE) $(SERVEREXTRA)
	$(SHELL) $(BABELDIR)/$(BABEL) --client=java --make-prefix=synch_ \
	  $(BABELEXTRA) $(OUTPUTSIDL) 
	$(SHELL) $(BABELDIR)/$(BABEL) --client=java --make-prefix=client_ \
	  $(BABELEXTRA) $(CLIENTEXTRA)
	@mv -f babel-temp $@

libImpl.scl : $(IORSRCS)
	rm -f libImpl.scl
	echo "<?xml version=\"1.0\" ?>" > libImpl.scl
	echo "<scl>" >> libImpl.scl
	if test `uname` = "Darwin"; then \
	  scope="global"; \
	else \
	  scope="local"; \
	fi ; \
	echo "  <library uri=\""`pwd`"/libImpl.la\" scope=\"$$scope\" resolution=\"lazy\" >" >> libImpl.scl
	grep __set_epv $(IORSRCS) /dev/null | awk 'BEGIN {FS=":"} { print $$1}' | sort -u | sed -e 's/_IOR.c//g' -e 's/_/./g' | awk ' { printf "    <class name=\"%s\" desc=\"ior/impl\" />\n", $$1 }' >> libImpl.scl
	echo "  </library>" >> libImpl.scl
	echo "</scl>" >> libImpl.scl  #end libImpl


libClient.scl : $(STUBSRCS) $(synch_STUBSRCS) $(client_STUBSRCS)
	rm -f libClient.scl
	echo "<?xml version=\"1.0\" ?>" > libClient.scl
	echo "<scl>" >> libClient.scl
	if test `uname` = "Darwin"; then \
	  scope="global"; \
	else \
	  scope="local"; \
	fi ; \
	echo "  <library uri=\""`pwd`"/libImpl.la\" scope=\"$$scope\" resolution=\"lazy\" >" >> libClient.scl
	grep __register $(STUBSRCS) $(synch_STUBSRCS) $(client_STUBSRCS) /dev/null | awk 'BEGIN {FS=":"} { print $$1}' | sort -u | sed -e 's/_jniStub.c//g' -e 's/_/./g' | awk ' { printf "    <class name=\"%s\" desc=\"java\" />\n", $$1 }' >> libClient.scl
	echo "  </library>" >> libClient.scl
	echo "</scl>" >> libClient.scl

if SUPPORT_JAVA
java-stamp: babel-stamp
	@rm -f java-temp
	@touch java-temp
	if test "X$(srcdir)" != "X." ; then					\
	  if test -f "$(srcdir)/$(TESTFILE)" ; then				\
	    cp -f "$(srcdir)/$(TESTFILE)" . ;					\
	  fi									\
	fi
	$(JAVAC) $(JAVAC_FLAGS) `find . -name "*.java" -print`;		
else
java-stamp:
	touch java-stamp
endif

update-babel-make:babel-stamp
	if test "X$(srcdir)" != "X."; then \
	   cp babel.make synch_babel.make client_babel.make $(srcdir); \
	   cp $(JAVAPKG)/babel.make  $(JAVAPKG)/client_babel.make $(srcdir)/$(JAVAPKG); \
	   cp synch/synch_babel.make $(srcdir)/synch; \
	fi

clean-local:
	rm -f babel-stamp *.a *.so *.scl stamp-installcheck core *.testresult
	rm -f $(IORHDRS) $(IORSRCS) $(SKELSRCS) $(STUBHDRS) $(STUBSRCS)
	rm -f $(synch_IORHDRS) $(synch_IORSRCS) $(synch_SKELSRCS) \
	      $(synch_STUBHDRS) $(synch_STUBSRCS)
	rm -f $(client_IORHDRS) $(client_IORSRCS) $(client_SKELSRCS) \
	      $(client_STUBHDRS) $(client_STUBSRCS)
	if test "X$(srcdir)" != "X."; then		   \
	  rm -f $(IMPLSRCS) $(IMPLHDRS) babel.make synch_babel.make client_babel.make;   \
	  rm -rf `find . -type d ! \( -name CVS -o -name .svn -o -name "." -o -name .libs -o -name .deps \)`;		   \
	  rm -f $(TESTFILE) ; \
	else						   \
	  if test -d $(JAVAPKG); then 			   \
	    for file in $(STUBJAVA) $(synch_STUBJAVA) $(client_STUBJAVA); do	   \
	      rm -f $(JAVAPKG)/$$file;			   \
	    done;						   \
	    rm -f $(JAVAPKG)/*.class;			   \
	  fi ;						   \
	  rm -rf sidl;					   \
	fi						   
	rm -f ./*.class;

check-local:
	@if test -f stamp-installcheck; then		\
	  echo $(MAKE) $(AM_MAKEFLAGS) clean;		\
	  $(MAKE) $(AM_MAKEFLAGS) clean;		\
	fi
	$(MAKE) $(AM_MAKEFLAGS) $(check_LTLIBRARIES) java-stamp $(SCLFILES)

installcheck-local:
	@if test ! -f stamp-installcheck; then		\
	  echo $(MAKE) $(AM_MAKEFLAGS) clean;		\
	  $(MAKE) $(AM_MAKEFLAGS) clean;		\
	fi
	$(MAKE) $(AM_MAKEFLAGS)				\
	  BABELDIR=$(bindir)				\
	  INCLUDEDIR=$(includedir)			\
	  INCLUDEDIR2=$(exec_prefix)/include		\
	  CP=$(libdir)/sidl-$(VERSION).jar:$(libdir)/sidlstub_$(VERSION).jar \
	  $(check_LTLIBRARIES) java-stamp \
	  $(SCLFILES)
	touch stamp-installcheck

# Make sure Makefile.in is newer than babel.make
dist-hook:
	@for file in $(JAVAIMPL); do 			\
	  d=`dirname $(distdir)/$(JAVAPKG)/$$file`;	\
	  test -f $$d || mkdir -p $$d || exit 1;	\
	  echo cp -p $(srcdir)/$(JAVAPKG)/$$file $(distdir)/$(JAVAPKG)/$$file;\
	  cp -p $(srcdir)/$(JAVAPKG)/$$file $(distdir)/$(JAVAPKG)/$$file;\
	done
	touch -c $(distdir)/Makefile.in

distclean-local:clean-local
	test "X$(srcdir)" = "X." || rm -rf $(JAVAPKG)

