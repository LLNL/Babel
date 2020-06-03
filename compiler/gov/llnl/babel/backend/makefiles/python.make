# -*- Makefile -*-
# Generic Python Babel wrapper GNU Makefile
# $Id: python.make 7366 2011-11-21 22:44:43Z adrian $
#
# Copyright (c) 2008, Lawrence Livermore National Security, LLC.
# Produced at the Lawrence Livermore National Laboratory.
# Written by the Components Team <components@llnl.gov>
# UCRL-CODE-2002-054
# All rights reserved.
# 
# This file is part of Babel. For more information, see
# http://www.llnl.gov/CASC/components/. Please read the COPYRIGHT file
# for Our Notice and the LICENSE file for the GNU Lesser General Public
# License.
# 
# This program is free software; you can redistribute it and/or modify it
# under the terms of the GNU Lesser General Public License (as published by
# the Free Software Foundation) version 2.1 dated February 1999.
# 
# This program is distributed in the hope that it will be useful, but
# WITHOUT ANY WARRANTY; without even the IMPLIED WARRANTY OF
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the terms and
# conditions of the GNU Lesser General Public License for more details.
# 
# You should have received a copy of the GNU Lesser General Public License
# along with this program; if not, write to the Free Software Foundation,
# Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
#
# This Makefile uses GNU make extensions, so it may not work with
# other implementations of make.

include babel.make
# please name the server library here
LIBNAME=impl
# please name the SIDL file here
SIDLFILE=@SIDLFILE@
# extra include/compile flags
EXTRAFLAGS=
# library version number
VERSION=0.1.1
# PREFIX specifies the top of the installation directory
PREFIX=/usr/local
# the default installation installs the .la and .scl (if any) files into
# LIBDIR
LIBDIR=$(PREFIX)/lib
# the default installation installs the stub header and IOR header files
# in INCLDIR
INCLDIR=$(PREFIX)/include

# most of the rest of the file should not require editing

ifeq ($(LAUNCHSRCS),)
  LAUNCHLIB=
  SKELLIB=
  SCLFILE=
  BABELFLAG=--client=python
else
  LAUNCHLIB=lib$(LIBNAME)1.la
  SKELLIB=lib$(LIBNAME)2.la
  SCLFILE=lib$(LIBNAME).scl
  BABELFLAG=--server=python
endif

all : python-stamp $(LAUNCHLIB) $(SKELLIB) $(SCLFILE)

PYTHON=`babel-config --query-var=WHICH_PYTHON`
SETUPFLAGS=`babel-config --includes-py | sed -e 's/-I/--include-dirs=/g'` `babel-config --includes | sed -e 's/-I/--include-dirs=/g'` --library-dirs=`babel-config --libdir` --rpath=`babel-config --libdir` $(CHASMLIB) $(PARSIFALLIB)
CC=`babel-config --query-var=CC`
INCLUDES=`babel-config --includes-py` `babel-config --includes`
CFLAGS=`babel-config --flags-c`
CHASMLIB=`babel-config --query-var=CHASM_PYSETUP`
PARSIFALLIB=`babel-config --query-var=LIBPARSIFAL_PYSETUP`

IOROBJS=$(IORSRCS:.c=.lo)
LAUNCHOBJS=$(LAUNCHSRCS:.c=.lo)
SKELOBJS=$(SKELSRCS:.c=.lo)
PUREBABELGEN=setup.py $(PYMOD_HDRS) $(IORHDRS) $(IORSRCS) $(LAUNCHSRCS) \
	$(SKELSRCS)

$(PUREBABELGEN) : babel-stamp
	@if test -f $@; then \
	    touch $@; \
	else \
	    rm -f babel-stamp ; \
	    $(MAKE) babel-stamp ; \
	fi

babel-stamp : $(SIDLFILE)
	@rm -f babel-temp
	@touch babel-temp
	babel $(BABELFLAG) $(SIDLFILE)
	@mv -f babel-temp $@

python-stamp : setup.py $(IORHDRS) $(PYMOD_HDRS)
	env CC=cc $(PYTHON) setup.py build_ext --inplace $(SETUPFLAGS)
	@touch python-stamp

$(IOROBJS) : $(IORHDRS)
$(LAUNCHOBJS) : $(IORHDRS)
$(SKELOBJS) : $(IORHDRS) $(PYMOD_HDRS)

lib$(LIBNAME)1.la : $(IOROBJS) $(LAUNCHOBJS)
ifeq ($(LAUNCHSRCS),)
	echo "$@ is not needed for client-side Python bindings."
else
	babel-libtool --mode=link --tag=CC $(CC) -o $@ \
	  -rpath $(LIBDIR) -release $(VERSION) \
	  -no-undefined -module \
	  $(CFLAGS) $(EXTRAFLAGS) $^ `babel-config --libs-python-client`
endif

lib$(LIBNAME)2.la : $(SKELOBJS)
ifeq ($(LAUNCHSRCS),)
	echo "$@ is not needed for client-side Python bindings."
else
	babel-libtool --mode=link $(CC) -o $@ \
	  -rpath $(LIBDIR) -release $(VERSION) \
	  -no-undefined -module \
	  $(CFLAGS) $(EXTRAFLAGS) $^ `babel-config --libs-python` \
	  `babel-config --libdir`/libsidl.la
endif

lib$(LIBNAME).scl : $(IORSRCS)
ifeq ($(LAUNCHSRCS),)
	echo "lib$(LIBNAME).scl is not needed for client-side C bindings."
else
	-rm -f $@
	echo '<?xml version="1.0" ?>' > $@
	echo '<scl>' >> $@	
	if test `uname` = "Darwin"; then scope="global"; else scope="local"; \
	   fi ; \
	  echo '  <library uri="'`pwd`/lib$(LIBNAME)1.la'" scope="'"$$scope"'" resolution="lazy" >' >> $@
	grep __set_epv $^ /dev/null | awk 'BEGIN {FS=":"} { print $$1}' | sort -u | sed -e 's/_IOR.c//g' -e 's/_/./g' | awk ' { printf "    <class name=\"%s\" desc=\"ior/impl\" />\n", $$1 }' >>$@
	echo "  </library>" >>$@
	if test `uname` = "Darwin"; then scope="global"; else scope="local"; \
	   fi ; \
	  echo '  <library uri="'`pwd`/lib$(LIBNAME)2.la'" scope="'"$$scope"'" resolution="lazy" >' >> $@
	grep __set_epv $^ /dev/null | awk 'BEGIN {FS=":"} { print $$1}' | sort -u | sed -e 's/_IOR.c//g' -e 's/_/./g' | awk ' { printf "    <class name=\"%s\" desc=\"python/impl\" />\n", $$1 }' >>$@
	echo "  </library>" >>$@
	echo "</scl>" >>$@
endif

install-scl : $(IORSRCS)
ifeq ($(LAUNCHSRCS),)
	echo "lib$(LIBNAME).scl is not needed for client-side C bindings."
else
	-mkdir -p $(LIBDIR)
	-rm -f $(LIBDIR)/lib$(LIBNAME).scl
	echo '<?xml version="1.0" ?>' > $(LIBDIR)/lib$(LIBNAME).scl
	echo '<scl>' >> $(LIBDIR)/lib$(LIBNAME).scl	
	if test `uname` = "Darwin"; then scope="global"; else scope="local"; \
	   fi ; \
	  echo '  <library uri="'$(LIBDIR)/lib$(LIBNAME)1.la'" scope="'"$$scope"'" resolution="lazy" >' >> $(LIBDIR)/lib$(LIBNAME).scl
	grep __set_epv $^ /dev/null | awk 'BEGIN {FS=":"} { print $$1}' | sort -u | sed -e 's/_IOR.c//g' -e 's/_/./g' | awk ' { printf "    <class name=\"%s\" desc=\"ior/impl\" />\n", $$1 }' >>$(LIBDIR)/lib$(LIBNAME).scl
	echo "  </library>" >>$(LIBDIR)/lib$(LIBNAME).scl
	if test `uname` = "Darwin"; then scope="global"; else scope="local"; \
	   fi ; \
	  echo '  <library uri="'$(LIBDIR)/lib$(LIBNAME)2.la'" scope="'"$$scope"'" resolution="lazy" >' >> $(LIBDIR)/lib$(LIBNAME).scl
	grep __set_epv $^ /dev/null | awk 'BEGIN {FS=":"} { print $$1}' | sort -u | sed -e 's/_IOR.c//g' -e 's/_/./g' | awk ' { printf "    <class name=\"%s\" desc=\"python/impl\" />\n", $$1 }' >>$(LIBDIR)/lib$(LIBNAME).scl
	echo "  </library>" >>$(LIBDIR)/lib$(LIBNAME).scl
	echo "</scl>" >>$(LIBDIR)/lib$(LIBNAME).scl
endif


.SUFFIXES: .lo

.c.lo:
	babel-libtool --mode=compile --tag=CC $(CC) $(INCLUDES) $(CFLAGS) $(EXTRAFLAGS) -c -o $@ $<

clean :
	-rm -f $(PUREBABELGEN) babel-temp babel-stamp python-temp python-stamp *.o *.lo

realclean : clean
	-rm -f lib$(LIBNAME)1.la lib$(LIBNAME)2.la lib$(LIBNAME).scl
	-rm -rf .libs build

install : install-libs install-headers install-python install-scl

install-python : python-stamp
	$(PYTHON) setup.py install --prefix=$(PREFIX) --library-dirs=`babel-config --libdir`

install-libs : $(LAUNCHLIB) $(SKELLIB)
	-mkdir -p $(LIBDIR)
	for i in $^ ; do \
	  babel-libtool --mode=install install -c $$i $(LIBDIR)/$$i ; \
	done

install-headers : $(IORHDRS) $(PYMOD_HDRS)
	-mkdir -p $(INCLDIR)
	for i in $^ ; do \
	  babel-libtool --mode=install cp $$i $(INCLDIR)/$$i ; \
	done

.PHONY: all install-headers install-libs install-python install-scl install clean realclean
