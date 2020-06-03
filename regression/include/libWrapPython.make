## -*- Makefile -*-
## File:        make.libPython
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

EXTRA_DIST = babel.make client_babel.make $(PYTHONIMPL)/babel.make  \
	$(PYTHONIMPL)/client_babel.make 

BABELGEN = $(IORSRCS) $(IORHDRS) $(SKELSRCS) $(LAUNCHSRCS) $(PYMOD_HDRS) \
	$(client_IORHDRS) $(client_IORSRCS) $(client_PYMOD_HDRS) \
	setup.py client_setup.py
CLEANFILES = $(BABELGEN) $(EXTRACLEAN) babel-stamp babel-temp \
	libImplPy.scl libImpl.scl core stamp-installcheck \
	babel.make.package client_babel.make.package \
	babel.make.depends client_babel.make.depends

PYTHONLIB = @PYTHONLIB@
PYTHONINC = @PYTHONINC@
PYTHONDIR=$(top_builddir)/runtime/python
SIDLPYTHONLIB=$(top_builddir)/runtime/sidl/.libs
SIDLPARSIFALLIB=$(top_builddir)/runtime/libparsifal/src/.libs
SIDLCHASMLITELIB=$(top_builddir)/runtime/libchasmlite/.libs
if SUPPORT_FORTRAN90
CHASMLITEDIR=`cd $(SIDLCHASMLITELIB) && pwd`
else
CHASMLITEDIR=$(SIDLCHASMLITELIB)
endif

libImpl_la_LDFLAGS        = -no-undefined -rpath `pwd`/.libs -release @VERSION@ -module
libImplPy_la_LDFLAGS      = -no-undefined -rpath `pwd`/.libs -release @VERSION@ -module

if SERVER_PYTHON
check_LTLIBRARIES = libImpl.la libImplPy.la
SCLFILES=libImpl.scl libImplPy.scl

nodist_libImpl_la_SOURCES = $(IORSRCS) $(IORHDRS) $(LAUNCHSRCS) $(SOURCE_EXTRAS)
libImpl_la_LIBADD	  = $(LIBSIDL)

nodist_libImplPy_la_SOURCES = $(SKELSRCS) $(SOURCE_EXTRAS)
libImplPy_la_LIBADD       = -L@PYTHON_SHARED_LIBRARY_DIR@	\
			    -lpython@PYTHON_VERSION@@PYTHON_ABIFLAGS@ $(LIBSIDL)

BABEL=`if test "$(BABELDIR)" != "$(top_builddir)/bin" ; then echo babel | sed '$(transform)' ; else echo babel; fi`
BABELDIR     = $(top_builddir)/bin
INCLUDEDIR   = $(top_builddir)/runtime/sidl
INCLUDEDIR2  = .
PYINCLUDEDIR = $(top_builddir)/runtime/python
PYINCLUDEDIR2 = .
LIBSIDL      = $(top_builddir)/runtime/sidl/libsidl.la

AM_CPPFLAGS   = -I. -I$(INCLUDEDIR) -I$(INCLUDEDIR2) \
		-I$(PYINCLUDEDIR) -I$(PYINCLUDEDIR2) $(PYTHONINC)

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

babel-stamp : $(SIDLFILE) $(OUTPUTSIDL)
	test -d $(PYTHONIMPL) || mkdir -p $(PYTHONIMPL) || exit 1
	@rm -f babel-temp
	@touch babel-temp
	$(SHELL) $(BABELDIR)/$(BABEL) --vpath="$(srcdir)" --server=python \
	  $(BABELEXTRA) $(SERVEREXTRA)
	$(SHELL) $(BABELDIR)/$(BABEL) --client=python --make-prefix=client_ \
	  $(BABELEXTRA) $(CLIENTEXTRA)
	env CC=cc $(PYTHON) setup.py build_ext	\
	  --include-dirs=`cd $(INCLUDEDIR) && pwd`		\
	  --include-dirs=`cd $(INCLUDEDIR2) && pwd`		\
	  --include-dirs=`cd $(PYINCLUDEDIR) && pwd`		\
	  --include-dirs=`cd $(PYINCLUDEDIR2) && pwd`		\
	  `echo $(PYTHONINC) | sed -e 's/-I/--include-dirs=/g'` \
	  --library-dirs=`cd $(SIDLPYTHONLIB) && pwd`		\
	  --rpath=`cd $(SIDLPYTHONLIB) && pwd`			\
	  --library-dirs=`cd $(SIDLPARSIFALLIB) && pwd`		\
	  --library-dirs=$(CHASMLITEDIR)			\
	  --rpath=`cd $(SIDLPARSIFALLIB) && pwd`			\
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
	  --rpath=`cd $(SIDLPYTHONLIB) && pwd`			\
	  --library-dirs=`cd $(SIDLPARSIFALLIB) && pwd`		\
	  --library-dirs=$(CHASMLITEDIR)			\
	  --rpath=`cd $(SIDLPARSIFALLIB) && pwd`			\
	  --rpath=$(CHASMLITEDIR)				\
	  $(LIBPARSIFAL_PYSETUP) $(CHASM_PYSETUP) \
	  $(PYTHON_SETUP_ARGS) 					\
	  --inplace
	@mv -f babel-temp babel-stamp

libImpl.scl : $(IORSRCS)
	$(SHELL) $(top_srcdir)/bin/generate_scl libImpl.scl `pwd`/libImpl.la \
	   $(IORSRCS)

libImplPy.scl : $(IORSRCS)
	$(SHELL) $(top_srcdir)/bin/generate_pyscl libImplPy.scl \
	   `pwd`/libImplPy.la $(IORSRCS)

update-babel-make: babel-stamp
	if test "X$(srcdir)" != "X."; then \
	   cp babel.make client_babel.make $(srcdir); \
	fi

check-local:
	@if test -f stamp-installcheck; then		\
	  echo $(MAKE) $(AM_MAKEFLAGS) clean;		\
	  $(MAKE) $(AM_MAKEFLAGS) clean;		\
	fi
	$(MAKE) $(AM_MAKEFLAGS) $(check_LTLIBRARIES) $(SCLFILES)

installcheck-local:
	@if test ! -f stamp-installcheck; then		\
	  echo $(MAKE) $(AM_MAKEFLAGS) clean;		\
	  $(MAKE) $(AM_MAKEFLAGS) clean;		\
	fi
	$(MAKE) $(AM_MAKEFLAGS)				\
	  BABELDIR=$(bindir)				\
	  INCLUDEDIR=$(includedir)			\
	  INCLUDEDIR2=$(exec_prefix)/include 		\
	  PYINCLUDEDIR=$(prefix)/include/python$(PYTHON_VERSION)/llnl_babel \
	  PYINCLUDEDIR2=$(prefix)/include/python$(PYTHON_VERSION)/llnl_babel_sidl_sidlx \
	  LIBSIDL=$(libdir)/libsidl.la			\
	  SIDLPYTHONLIB=$(libdir)			\
	  SIDLPARSIFALLIB=$(libdir)			\
	  SIDLCHASMLITELIB=$(libdir)			\
	  PYTHONDIR=`$(PYTHON) -c "from distutils.sysconfig import get_python_lib; print get_python_lib(prefix='$(exec_prefix)',plat_specific=1)"` \
	  $(check_LTLIBRARIES) $(SCLFILES)
	touch stamp-installcheck
else
check-local:
installcheck-local:
endif

clean-local:
	rm -rf build
	test "X$(srcdir)" = "X." || rm -rf babel.make client_babel.make 
	@if test -d $(sidlSUBDIR); then					\
	  cd $(sidlSUBDIR);						\
	  echo rm -f sedscript Makefile Makefile.pre Setup config.c;	\
	  rm -f sedscript Makefile Makefile.pre Setup config.c;		\
	  echo rm -f Setup.in *.so *.o *~ *.c *.h __init__.*;		\
	  rm -f Setup.in *.so *.o *~ *.c *.h __init__.*;		\
	fi

#	rm -f synch/*.o synch/*.so synch/*~ synch/*.pyc synch/*.a synch/*.dylib
#	@for file in $(client_PYMOD_SRCS) $(client_PYTHONADMIN) $(client_PYTHONSRC); do \
#	  rm -f wrapper/$$file; \
#	done

dist-hook:
	@for file in $(PYTHONSRC); do		\
	  d=`dirname $(distdir)/$(PYTHONIMPL)/$$file`;			\
	  test -f $$d || mkdir -p $$d || exit 1;			\
	  echo cp -p $(srcdir)/$(PYTHONIMPL)/$$file $(distdir)/$(PYTHONIMPL)/$$file;\
	  cp -p $(srcdir)/$(PYTHONIMPL)/$$file $(distdir)/$(PYTHONIMPL)/$$file;\
	done
	touch -c $(distdir)/Makefile.in

distclean-local: clean-local
	test "X$(srcdir)" = "X." || rm -rf $(PYTHONIMPL)

