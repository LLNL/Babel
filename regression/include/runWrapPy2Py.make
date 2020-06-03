## -*- Makefile -*-
## File:        make.runPy2Py
## Package:     Babel array regression tests
## Revision:    $Revision: 5167 $
## Modified:    $Date: 2005-12-20 15:27:00 -0800 (Tue, 20 Dec 2005) $
## Description: automake makefile for python regression tests
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





PYTHONDIR=$(top_builddir)/runtime/python
SIDLPYTHONLIB=$(top_builddir)/runtime/sidl/.libs
SIDLPARSIFALLIB=$(top_builddir)/runtime/libparsifal/src/.libs
SIDLCHASMLITELIB=$(top_builddir)/runtime/libchasmlite/.libs
if WITH_SIDLX
SIDLXPYTHONLIB=$(top_builddir)/runtime/sidlx/.libs
else
SIDLXPYTHONLIB=$(top_builddir)/runtime/sidl/.libs
endif
EXTRA_DIST=runPy2Py.in
CLEANFILES = $(TESTPROG) $(EXTRACLEAN) runPy2Py.sh babel.make.package \
	babel.make.depends runPy2Py.testresult client_babel.make.package \
	client_babel.make.depends

if SERVER_PYTHON
check_SCRIPTS = runPy2Py.sh $(TESTPROG)
runPy2Py.sh : $(srcdir)/runPy2Py.in
	rm -f runPy2Py.sh
	sed -e s%PYTHONDIR%$(PYTHONDIR)%g				\
	  -e s%EXTRAPYTHON%$(PYTHONPATH)%g				\
	  -e s%PYTHONLIB%$(SIDLPYTHONLIB):$(SIDLXPYTHONLIB):$(SIDLPARSIFALLIB):$(SIDLCHASMLITELIB)%g \
	  -e s%SHARED_LIB_VAR%$(SHARED_LIB_VAR)%g			\
	  -e s%PYTHONEXEC%$(PYTHON)%g $(srcdir)/runPy2Py.in > runPy2Py.sh
	chmod +x runPy2Py.sh

$(TESTPROG) : $(srcdir)/../$(TESTPROG)
	rm -f $(TESTPROG)
	cp $(srcdir)/../$(TESTPROG) $(TESTPROG)
installcheck-local : $(check_SCRIPTS)
else
check-local:
	@echo "Python not supported"
endif
