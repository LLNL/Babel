##
## File:        Makefile.am
## Package:     Babel CCA hello example
## Copyright:   (c) 2000-2001 Lawrence Livermore National Security, LLC
## Revision:    $Revision: 7366 $
## Modified:    $Date: 2011-11-21 14:44:43 -0800 (Mon, 21 Nov 2011) $
## Description: automake makefile for python CCA hello world client
##

AUTOMAKE_OPTIONS = foreign no-dependencies no-installinfo	\
                   no-installman no-texinfo.tex 1.7

PYTHONISHFILES = HelloDriver.py runPython.sh

BABELDIR=$(top_builddir)/bin
INCLUDEDIR=$(top_builddir)/runtime/sidl
INCLUDEDIR2=.
PYTHONDIR=$(top_builddir)/runtime/python
PYINCLUDEDIR=$(PYTHONDIR)
SIDLPYTHONLIB=$(top_builddir)/runtime/sidl/.libs
SIDLPARSIFALLIB=$(top_builddir)/runtime/libparsifal/src/.libs
SIDLCHASMLITELIB=$(top_builddir)/runtime/libchasmlite/.libs
PYTHONLIB=@PYTHONLIB@
PYTHONINC=@PYTHONINC@
CLEANFILES=babel.make.package babel.make.depends

check-local: python-support

if SUPPORT_PYTHON
python-support: setup.py

setup.py: 
	$(SHELL) $(BABELDIR)/babel --client=python \
	  --repository-path=../repository gov.cca decaf
	if test "X$(srcdir)" != "X."; then				\
	  for file in $(PYTHONISHFILES); do				\
	    d=`dirname $$file`;						\
	    test -f $$d							\
	    || mkdir -p $$d						\
	    || exit 1;							\
	    cp -pf $(srcdir)/$$file $$file;				\
	  done;								\
	fi
	if ! test -d gov/cca; then mkdir -p gov/cca || exit 1; fi
	env CC=cc $(PYTHON) setup.py \
	  --include-dirs=`cd $(PYINCLUDEDIR) && pwd`		\
	  --include-dirs=`cd $(INCLUDEDIR) && pwd`		\
	  --include-dirs=`cd $(INCLUDEDIR2) && pwd`		\
	  `echo $(PYTHONINC) | sed -e 's/-I/--include-dirs=/g'` \
	  --library-dirs=`cd $(SIDLPYTHONLIB) && pwd`		\
	  --library-dirs=`cd $(SIDLPARSIFALLIB) && pwd`		\
	  --library-dirs=`cd $(SIDLCHASMLITELIB) && pwd`	\
	  $(LIBPARSIFAL_PYSETUP) $(CHASM_PYSETUP) \
	  $(PYTHON_SETUP_ARGS)                                  \
	  build_ext --inplace --force				\
	  --rpath=`cd $(SIDLPYTHONLIB) && pwd`			\
	  --rpath=`cd $(SIDLCHASMLITELIB) && pwd`	\
	  --rpath=`cd $(SIDLPARSIFALLIB) && pwd`

clean-local:
	for dir in gov gov/cca gov/cca/ports decaf decaf/ports ; do			\
	  if test -d $$dir; then					\
	    (cd $$dir;							\
	     rm -f sedscript Makefile Makefile.pre* Setup config.c babel.make.depends ;	\
	     rm -f *.so *.o *.o *~);					\
	  fi								\
	done
	rm -f setup.py

distclean-local:
	for dir in gov gov/cca gov/cca/ports decaf decaf/ports ; do			\
	  if test -d $$dir; then					\
	    (cd $$dir ;							\
	     rm -f *_Module.c *_Module.h Setup* *.py*		\
		babel.make );						\
	  fi								\
	done
	if test -d build; then \
	   rm -rf build; \
	fi
	rm -f *_Module.h
	if test "X$(srcdir)" != "X."; then				\
	  rm -f $(PYTHONISHFILES) babel.make setup.py *_IOR.h *_IOR.c;	\
        fi
else
python-support:
	@echo "Python not supported"
clean-local:
distclean-local:
endif

dist-hook:
	for file in $(PYTHONISHFILES); do				\
	  d=`dirname $(distdir)/$$file`; 				\
	  test -f $$d							\
	  || mkdir -p $$d						\
	  || exit 1;							\
	  cp -pf $(srcdir)/$$file $(distdir)/$$file;			\
	done
