##
## File:        Makefile.am
## Package:     Babel Hello example
## Copyright:   (c) 2000-2001 Lawrence Livermore National Security, LLC
## Revision:    $Revision: 7368 $
## Modified:    $Date: 2011-11-22 15:23:42 -0800 (Tue, 22 Nov 2011) $
## Description: automake makefile for Java Hello World server
##


if SUPPORT_JAVA
check_LTLIBRARIES = libImpl.la
endif

BABEL=`if test "$(BABELDIR)" != "$(top_builddir)/bin" ; then echo babel | sed '$(transform)' ; else echo babel; fi`
BABELDIR    = $(top_builddir)/bin
INCLUDEDIR  = $(top_builddir)/runtime/sidl
STUBINCLUDEDIR = $(top_builddir)/lib/sidlstubs/Java
LIBSIDL     = $(top_builddir)/runtime/sidl/libsidl.la
JAVAPKG=Hello
SIDL_LIBS   = $(top_builddir)/runtime/java
VERSION=@VERSION@
SIDLJAR=$(top_builddir)/lib/sidl-$(VERSION).jar
SIDLSTUBSJAR=$(top_builddir)/lib/sidlstubs/Java/sidlstub_$(VERSION).jar
SIDLFILE   = $(srcdir)/../Hello.sidl
CLEANFILES = $(PUREBABELGEN) babel-stamp babel-temp babel.make.package \
	babel.make.depends

PUREBABELGEN = $(STUBSRCS) $(STUBHDRS) $(IORSRCS) $(IORHDRS) $(SKELSRCS) 
nodist_libImpl_la_SOURCES      = $(PUREBABELGEN)
libImpl_la_LIBADD       = $(LIBSYNC) $(LIBSIDL)
libImpl_la_LDFLAGS      = $(JNI_LDFLAGS) -no-undefined -rpath `pwd`/.libs \
			  -release @VERSION@  -module

EXTRA_DIST = babel.make $(JAVAPKG)/babel.make
AM_CPPFLAGS   = -I. -I$(INCLUDEDIR) -I$(STUBINCLUDEDIR) $(JNI_INCLUDES)

if SUPPORT_CYGWIN
  CLASSPATH = `cygpath --path --windows "$(SIDLJAR)"`
else
  CLASSPATH = $(SIDLJAR):$(SIDLSTUBSJAR)
endif
JAVAC_FLAGS = -g -d . -classpath "$(CLASSPATH)"

$(nodist_libImpl_la_SOURCES) : babel-stamp
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

babel-stamp: $(SIDLFILE)
	@rm -f babel-temp
	@touch babel-temp
	$(SHELL) $(BABELDIR)/$(BABEL) --vpath="$(srcdir)" --server=java \
	  $(SIDLFILE)
	$(JAVAC) $(JAVAC_FLAGS) `find . -name .svn -prune -o \
	  -name "*.java" -print`;
	rm -f libImpl.scl
	echo "<?xml version=\"1.0\" ?>" > libImpl.scl
	echo "<scl>" >> libImpl.scl
	echo "  <library uri=\""`pwd`"/libImpl.la\" scope=\"local\" resolution=\"lazy\" >" >> libImpl.scl
	grep __set_epv $(IORSRCS) /dev/null | awk 'BEGIN {FS=":"} { print $$1}' | sort -u | sed -e 's/_IOR.c//g' -e 's/_/./g' | awk ' { printf "    <class name=\"%s\" desc=\"ior/impl\" />\n", $$1 }' >> libImpl.scl
	grep __register $(STUBSRCS) /dev/null | awk 'BEGIN {FS=":"} { print $$1}' | sort -u | sed -e 's/_jniStub.c//g' -e 's/_/./g' | awk ' { printf "    <class name=\"%s\" desc=\"java\" />\n", $$1 }' >> libImpl.scl
	echo "  </library>" >> libImpl.scl
	echo "</scl>" >> libImpl.scl
	@mv -f babel-temp $@

clean-local:
	rm -f *.a *.so *.scl 
	if test "X$(srcdir)" != "X."; then		   \
	  rm -f babel.make ;       \
	  rm -rf `find . -type d ! \( -name CVS -o -name .svn -o -name "." -o -name .deps -o -name .libs \)` ;   \
	else						   \
	  rm -f $(JAVAPKG)/*.class;			   \
	  rm -rf sidl;					   \
	fi

check-local:
	@if test -f stamp-installcheck; then		\
	  echo $(MAKE) $(AM_MAKEFLAGS) clean;		\
	  $(MAKE) $(AM_MAKEFLAGS) clean;		\
	fi
	$(MAKE) $(AM_MAKEFLAGS)

include $(srcdir)/babel.make
include $(srcdir)/$(JAVAPKG)/babel.make

# Make sure Makefile.in is newer than babel.make
dist-hook:
	@for file in $(JAVAIMPL); do 			\
	  d=`dirname $(distdir)/$(JAVAPKG)/$$file`;	\
	  test -f $$d || mkdir -p $$d || exit 1;	\
	  echo cp -p $(srcdir)/$(JAVAPKG)/$$file $(distdir)/$(JAVAPKG)/$$file;\
	  cp -p $(srcdir)/$(JAVAPKG)/$$file $(distdir)/$(JAVAPKG)/$$file;\
	done
	touch -c $(distdir)/Makefile.in
