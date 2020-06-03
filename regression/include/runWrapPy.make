## -*- Makefile -*-
## File:        make.runPy
## Package:     Generic Makefile.am support for running Python drivers
## Revision:    $Revision: 5359 $
## Modified:    $Date: 2006-04-06 16:53:03 -0700 (Thu, 06 Apr 2006) $
## Description: automake makefile for python driver regression tests
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

EXTRA_DIST = $(TMP_EXTRA_DIST) babel.make client_babel.make $(PYTHONIMPL)/babel.make \
	$(PYTHONIMPL)/client_babel.make 

BABELGEN = $(IORSRCS) $(IORHDRS) $(SKELSRCS) $(LAUNCHSRCS) $(PYMOD_HDRS) \
	$(client_IORHDRS) $(client_IORSRCS) $(client_PYMOD_HDRS) \
	setup.py client_setup.py
CLEANFILES = $(BABELGEN) $(EXTRACLEAN) babel-stamp babel-temp \
	runPy2C runPy2Cxx runPy2UCxx runPy2F77 runPy2F90 runPy2F03 runPy2Java \
	libImplPy.scl libImpl.scl core stamp-installcheck \
	babel.make.package client_babel.make.package \
	babel.make.depends client_babel.make.depends

VERSION=@VERSION@
BABEL=`if test "$(BABELDIR)" != "$(top_builddir)/bin" ; then echo babel | sed '$(transform)' ; else echo babel; fi`
BABELDIR=$(top_builddir)/bin
INCLUDEDIR=$(top_builddir)/runtime/sidl
INCLUDEDIR2=.
PYTHONDIR=$(top_builddir)/runtime/python
PYINCLUDEDIR=$(PYTHONDIR)
PYINCLUDEDIR2=.
SIDLPYTHONLIB=$(top_builddir)/runtime/sidl/.libs
SIDLPARSIFALLIB=$(top_builddir)/runtime/libparsifal/src/.libs
SIDLCHASMLITELIB=$(top_builddir)/runtime/libchasmlite/.libs
if SUPPORT_FORTRAN90
CHASMLITEDIR=`cd $(SIDLCHASMLITELIB) && pwd`
else
CHASMLITEDIR=$(SIDLCHASMLITELIB)
endif
if WITH_SIDLX
SIDLXPYTHONLIB=$(top_builddir)/runtime/sidlx/.libs
else
# use a harmless value
SIDLXPYTHONLIB=$(top_builddir)/runtime/sidl/.libs
endif
SIDLSTUBSLIB=$(top_builddir)/lib/sidlstubs
PYTHONLIB = @PYTHONLIB@
PYTHONINC=@PYTHONINC@

LIBSIDL      = $(top_builddir)/runtime/sidl/libsidl.la

AM_CPPFLAGS   = -I. -I$(INCLUDEDIR) -I$(INCLUDEDIR2) \
		-I$(PYINCLUDEDIR) -I$(PYINCLUDEDIR2) $(PYTHONINC)

libImpl_la_LDFLAGS        = -no-undefined -rpath `pwd`/.libs -release @VERSION@ -module
libImplPy_la_LDFLAGS      = -no-undefined -rpath `pwd`/.libs -release @VERSION@ -module

if SERVER_PYTHON
SCLFILES=libImpl.scl libImplPy.scl
check_LTLIBRARIES = libImpl.la libImplPy.la

nodist_libImpl_la_SOURCES = $(IORSRCS) $(IORHDRS) $(LAUNCHSRCS) $(SOURCE_EXTRAS)
libImpl_la_LIBADD	  = $(LIBSIDL)

nodist_libImplPy_la_SOURCES = $(SKELSRCS) $(SOURCE_EXTRAS)
libImplPy_la_LIBADD       = -L@PYTHON_SHARED_LIBRARY_DIR@	\
			    -lpython@PYTHON_VERSION@@PYTHON_ABIFLAGS@ $(LIBSIDL)


if SUPPORT_FORTRAN77
PY2F77_DRIVER=runPy2F77
else
PY2F77_DRIVER=
endif

if SUPPORT_FORTRAN90
PY2F90_DRIVER=runPy2F90
else
PY2F90_DRIVER=
endif
if SUPPORT_FORTRAN03
PY2F03_DRIVER=runPy2F03
else
PY2F03_DRIVER=
endif
check_SCRIPTS = $(DRIVERSCRIPTS)

if SERVER_PYTHON
SUBDIRS = Py2Py
endif

if SUPPORT_PYTHON

$(BABELGEN) : babel-stamp
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

python-support: babel-stamp $(DRIVERSCRIPTS) $(check_LTLIBRARIES)

babel-stamp : $(TESTPROG) $(SIDLFILE) $(OUTPUTSIDL)
	@if test "X$(srcdir)" != "X."; then				\
	  for file in $(PYTHONISHFILES); do				\
	    d=`dirname $$file`;						\
	    test -f $$d							\
	    || mkdir -p $$d						\
	    || exit 1;							\
	    echo cp -p $(srcdir)/$$file $$file;				\
	    cp -p $(srcdir)/$$file $$file;				\
	    echo chmod +x $$file;					\
	    chmod +x $$file;						\
	  done;								\
	fi
	test -f $(PYTHONIMPL)						 \
	|| mkdir -p $(PYTHONIMPL)					 \
	|| exit 1
	$(SHELL) $(BABELDIR)/$(BABEL) --vpath="$(srcdir)" --server=python \
		$(BABELEXTRA) $(SERVEREXTRA) 
	$(SHELL) $(BABELDIR)/$(BABEL) --client=python $(BABELEXTRA) \
		--make-prefix=client_ $(CLIENTEXTRA) $(OUTPUTSIDL)
	env CC=cc $(PYTHON) setup.py build_ext	\
	  --include-dirs=`cd $(INCLUDEDIR) && pwd`		\
	  --include-dirs=`cd $(INCLUDEDIR2) && pwd`		\
	  --include-dirs=`cd $(PYINCLUDEDIR) && pwd`		\
	  --include-dirs=`cd $(PYINCLUDEDIR2) && pwd`		\
	  --library-dirs=`cd $(SIDLPYTHONLIB) && pwd`		\
	  `echo $(PYTHONINC) | sed -e 's/-I/--include-dirs=/g'` \
	  --library-dirs=`cd $(SIDLXPYTHONLIB) && pwd`		\
	  --rpath=`cd $(SIDLPYTHONLIB) && pwd`		\
	  --library-dirs=`cd $(SIDLPARSIFALLIB) && pwd`		\
	  --library-dirs=$(CHASMLITEDIR)			\
	  --rpath=`cd $(SIDLPARSIFALLIB) && pwd`		\
	  --rpath=$(CHASMLITEDIR)				\
	  $(LIBPARSIFAL_PYSETUP) $(CHASM_PYSETUP) \
	  $(PYTHON_SETUP_ARGS) 					\
	  --inplace
	env CC=cc $(PYTHON) client_setup.py build_ext	\
	  --include-dirs=`cd $(INCLUDEDIR) && pwd`		\
	  --include-dirs=`cd $(INCLUDEDIR2) && pwd`		\
	  --include-dirs=`cd $(PYINCLUDEDIR) && pwd`		\
	  --include-dirs=`cd $(PYINCLUDEDIR2) && pwd`		\
	  `echo $(PYTHONINC) | sed -e 's/-I/--include-dirs=/g'` \
	  --library-dirs=`cd $(SIDLPYTHONLIB) && pwd`		\
	  --library-dirs=`cd $(SIDLXPYTHONLIB) && pwd`		\
	  --rpath=`cd $(SIDLPYTHONLIB) && pwd`		\
	  --library-dirs=`cd $(SIDLPARSIFALLIB) && pwd`		\
	  --library-dirs=$(CHASMLITEDIR)			\
	  --rpath=`cd $(SIDLPARSIFALLIB) && pwd`		\
	  --rpath=$(CHASMLITEDIR)				\
	  $(LIBPARSIFAL_PYSETUP) $(CHASM_PYSETUP) \
	  $(PYTHON_SETUP_ARGS) 					\
	  --inplace
	touch babel-stamp

libImpl.scl : $(IORSRCS)
	$(SHELL) $(top_srcdir)/bin/generate_scl libImpl.scl `pwd`/libImpl.la \
	   $(IORSRCS)

libImplPy.scl : $(IORSRCS)
	$(SHELL) $(top_srcdir)/bin/generate_pyscl libImplPy.scl \
	   `pwd`/libImplPy.la $(IORSRCS)

runPy2C: $(srcdir)/runPy2C.in
	rm -f runPy2C
	sed -e s%PYTHONDIR%$(PYTHONDIR)%g				\
	  -e s%PYTHONLIB%$(SIDLPYTHONLIB):$(SIDLXPYTHONLIB):$(SIDLPARSIFALLIB):$(SIDLCHASMLITELIB)%g \
	  -e s%SIDLSTUBSLIB%$(SIDLSTUBSLIB)%g \
	  -e s%SHARED_LIB_VAR%$(SHARED_LIB_VAR)%g			\
	  -e s%PYTHONEXEC%$(PYTHON)%g $(srcdir)/runPy2C.in > runPy2C
	chmod +x runPy2C

runPy2Cxx: $(srcdir)/runPy2Cxx.in
	rm -f runPy2Cxx
	sed -e s%PYTHONDIR%$(PYTHONDIR)%g				\
	  -e s%PYTHONLIB%$(SIDLPYTHONLIB):$(SIDLXPYTHONLIB):$(SIDLPARSIFALLIB):$(SIDLCHASMLITELIB)%g \
	  -e s%SIDLSTUBSLIB%$(SIDLSTUBSLIB)%g \
	  -e s%SHARED_LIB_VAR%$(SHARED_LIB_VAR)%g			\
	  -e s%PYTHONEXEC%$(PYTHON)%g $(srcdir)/runPy2Cxx.in > runPy2Cxx
	chmod +x runPy2Cxx

runPy2Java: $(srcdir)/runPy2Java.in
	rm -f runPy2Java
	sed -e s%PYTHONDIR%$(PYTHONDIR)%g				\
	  -e s%PYTHONLIB%$(SIDLPYTHONLIB):$(SIDLXPYTHONLIB):$(SIDLPARSIFALLIB):$(SIDLCHASMLITELIB)%g \
	  -e s%SIDLSTUBSLIB%$(SIDLSTUBSLIB)%g \
	  -e s%LIBJAVA_DIR%$(LIBJAVA_DIR)%g                             \
	  -e s%LIBJVM_DIR%$(LIBJVM_DIR)%g                               \
	  -e s%EXEC_PREFIX%$(exec_prefix)%g				\
	  -e s%VERSION%$(VERSION)%g					\
	  -e s%SHARED_LIB_VAR%$(SHARED_LIB_VAR)%g			\
	  -e s%PYTHONEXEC%$(PYTHON)%g $(srcdir)/runPy2Java.in > runPy2Java
	chmod +x runPy2Java


if SUPPORT_FORTRAN77
runPy2F77: $(srcdir)/runPy2F77.in
	rm -f runPy2F77
	sed -e s%PYTHONDIR%$(PYTHONDIR)%g				\
	  -e s%PYTHONLIB%$(SIDLPYTHONLIB)::$(SIDLXPYTHONLIB):$(SIDLPARSIFALLIB):$(SIDLCHASMLITELIB)%g \
	  -e s%SIDLSTUBSLIB%$(SIDLSTUBSLIB)%g \
	  -e s%SHARED_LIB_VAR%$(SHARED_LIB_VAR)%g			\
	  -e s%PYTHONEXEC%$(PYTHON)%g $(srcdir)/runPy2F77.in > runPy2F77
	chmod +x runPy2F77
endif

if SUPPORT_FORTRAN90
runPy2F90: $(srcdir)/runPy2F90.in
	rm -f runPy290F
	sed -e s%PYTHONDIR%$(PYTHONDIR)%g				\
	  -e s%PYTHONLIB%$(SIDLPYTHONLIB)::$(SIDLXPYTHONLIB):$(SIDLPARSIFALLIB):$(SIDLCHASMLITELIB)%g \
	  -e s%SIDLSTUBSLIB%$(SIDLSTUBSLIB)%g \
	  -e s%SHARED_LIB_VAR%$(SHARED_LIB_VAR)%g			\
	  -e s%PYTHONEXEC%$(PYTHON)%g $(srcdir)/runPy2F90.in > runPy2F90
	chmod +x runPy2F90
endif

if SUPPORT_FORTRAN03
runPy2F03: $(srcdir)/runPy2F03.in
	rm -f runPy203F
	sed -e s%PYTHONDIR%$(PYTHONDIR)%g				\
	  -e s%PYTHONLIB%$(SIDLPYTHONLIB)::$(SIDLXPYTHONLIB):$(SIDLPARSIFALLIB):$(SIDLCHASMLITELIB)%g \
	  -e s%SIDLSTUBSLIB%$(SIDLSTUBSLIB)%g \
	  -e s%SHARED_LIB_VAR%$(SHARED_LIB_VAR)%g			\
	  -e s%PYTHONEXEC%$(PYTHON)%g $(srcdir)/runPy2F03.in > runPy2F03
	chmod +x runPy2F03
endif


clean-local: clean-generic
	rm -f *setup.py *.testresult
	rm -rf build synch
	test "X$(srcdir)" = "X." || rm -rf *babel.make*  $(TESTPROG)    \
		$(sidlSUBDIR)/*.py $(sidlSUBDIR)/*.pyc $(sidlSUBDIR)/*babel.make*
	@if test -d $(sidlSUBDIR); then					\
	  cd $(sidlSUBDIR);						\
	  echo rm -f sedscript Makefile Makefile.pre Setup config.c babel.make.depends;	\
	  rm -f sedscript Makefile Makefile.pre Setup config.c babel.make.depends;		\
	  echo rm -f Setup.in *.so *.o *~ *.c *.h __init__.*;		\
	  rm -f Setup.in *.so *.o *~ *.c *.h __init__.*; 		\
	fi

distclean-local:clean-local
	test "X$(srcdir)" = "X." || rm -rf $(sidlSUBDIR) $(PYTHONISHFILES)

check-local:
	@if test -f stamp-installcheck; then		\
	  echo $(MAKE) $(AM_MAKEFLAGS) clean-local;	\
	  $(MAKE) $(AM_MAKEFLAGS) clean-local;		\
	fi
	$(MAKE) $(AM_MAKEFLAGS) python-support $(SCLFILES)

installcheck-local:
	@if test ! -f stamp-installcheck; then		\
	  echo $(MAKE) $(AM_MAKEFLAGS) clean-local;	\
	  $(MAKE) $(AM_MAKEFLAGS) clean-local;		\
	fi
	$(MAKE) $(AM_MAKEFLAGS)				\
	  BABELDIR=$(bindir)				\
	  INCLUDEDIR=$(includedir)			\
	  INCLUDEDIR2=$(exec_prefix)/include		\
	  PYINCLUDEDIR=$(prefix)/include/python$(PYTHON_VERSION)/llnl_babel \
	  PYINCLUDEDIR2=$(prefix)/include/python$(PYTHON_VERSION)/llnl_babel_sidl_sidlx \
	  PYTHONDIR=`$(PYTHON) -c "from distutils.sysconfig import get_python_lib; print get_python_lib(prefix='$(exec_prefix)',plat_specific=1)"` \
	  SIDLPYTHONLIB=$(libdir) SIDLXPYTHONLIB=$(libdir) \
	  SIDLPARSIFALLIB=$(libdir) \
	  SIDLCHASMLITELIB=$(libdir) \
	  SIDLSTUBSLIB=$(libdir) python-support $(SCLFILES)
	touch stamp-installcheck
else
clean-local:
installcheck-local:
endif
endif

dist-hook:
	@for file in $(PYTHONISHFILES); do				\
	  d=`dirname $(distdir)/$$file`; 				\
	  test -f $$d							\
	  || mkdir -p $$d						\
	  || exit 1;							\
	  echo cp -p $(srcdir)/$$file $(distdir)/$$file;		\
	  cp -p $(srcdir)/$$file $(distdir)/$$file;			\
	done
	@for file in $(PYTHONSRC); do		\
	  d=`dirname $(distdir)/$(PYTHONIMPL)/$$file`;			\
	  test -f $$d || mkdir -p $$d || exit 1;			\
	  echo cp -p $(srcdir)/$(PYTHONIMPL)/$$file $(distdir)/$(PYTHONIMPL)/$$file;	\
	  cp -p $(srcdir)/$(PYTHONIMPL)/$$file $(distdir)/$(PYTHONIMPL)/$$file;	\
	done
	touch -c $(distdir)/Makefile.in


update-babel-make: babel-stamp
	if test "X$(srcdir)" != "X."; then \
	   cp babel.make client_babel.make $(srcdir); \
	fi
