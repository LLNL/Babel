# -*- Makefile -*-
# Generic F90 Babel wrapper GNU Makefile
# $Id: f90.make 7439 2012-02-04 00:42:23Z adrian $
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
MOD_SUFFIX=.lo

include babel.make
# please name the server library here
LIBNAME=impl
# please name the SIDL file here
SIDLFILE=@SIDLFILE@
# extra include/compile flags 
EXTRAFLAGS=
# extra librarys that the implementation needs to link against
EXTRALIBS=
# library version number
VERSION=0.1.1
# PREFIX specifies the top of the installation directory
PREFIX=/usr/local
# the default installation installs the .la and .scl (if any) into the
# LIBDIR
LIBDIR=$(PREFIX)/lib
# the default installation installs the stub header and IOR header files
# in INCLDIR
INCLDIR=$(PREFIX)/include

# most of the rest of the file should not require editing

ifeq ($(IMPLSRCS),)
  SCLFILE=
  BABELFLAG=--client=f90
  MODFLAG=
else
  SCLFILE=lib$(LIBNAME).scl
  BABELFLAG=--server=f90
  MODFLAG=-module
endif

all : lib$(LIBNAME).la $(SCLFILE)

CC=`babel-config --query-var=CC`
CPP=`babel-config --query-var=CPP`
FC=`babel-config --query-var=FC`
INCLUDES=`babel-config --includes` `babel-config --includes-f90`
CFLAGS=`babel-config --flags-c`
CPPFLAGS=`babel-config --flags-cpp`
FCFLAGS=`babel-config --flags-f90`
MODINCLUDES=`babel-config --includes-f90-mod`
LIBS=`babel-config --libs-f90-client`
F90CPPSUFFIX=`babel-config --query-var=F90CPPSUFFIX`

STUBOBJS=$(STUBSRCS:.c=.lo)
STUBMODULEOBJS=$(STUBMODULESRCS:.F90=.lo)
TYPEMODULEOBJS=$(TYPEMODULESRCS:.F90=.lo)
IOROBJS=$(IORSRCS:.c=.lo)
SKELOBJS=$(SKELSRCS:.c=.lo)
IMPLOBJS=$(IMPLSRCS:.F90=.lo) $(FSKELSRCS:.F90=.lo)
IMPLMODULEOBJS=$(IMPLMODULESRCS:.F90=.lo)
BASICMODULEOBJ=$(BASICMODULESRC:.F90=.lo)
ARRAYMODULEOBJS=$(ARRAYMODULESRCS:.F90=.lo)
ALLOBJS=$(STUBOBJS) $(STUBMODULEOBJS) $(TYPEMODULEOBJS) $(IOROBJS) \
	$(SKELOBJS) $(IMPLOBJS) $(IMPLMODULEOBJS) $(BASICMODULEOBJ) \
	$(ARRAYMODULEOBJS)

PUREBABELGEN=$(ARRAYMODULESRCS) $(BASICMODULESRC) $(STUBMODULESRCS) \
	$(TYPEMODULESRCS) \
	$(IORHDRS) $(IORSRCS) $(STUBSRCS) $(STUBHDRS) $(SKELSRCS)
BABELGEN=$(IMPLSRCS) $(IMPLMODULESRCS)

$(TYPEMODULEOBJS)  : $(BASICMODULEOBJ)
$(ARRAYMODULEOBJS)  : $(TYPEMODULEOBJS)
$(STUBMODULEOBJS) : $(ARRAYMODULEOBJS) $(TYPEMODULEOBJS)
$(IMPLMODULEOBJS) : $(STUBMODULEOBJS)
$(IMPLOBJS) : $(IMPLMODULEOBJS) $(STUBMODULEOBJS)

lib$(LIBNAME).la : $(ALLOBJS)
	babel-libtool --mode=link --tag=FC $(FC) -o lib$(LIBNAME).la \
	  -rpath $(LIBDIR) -release $(VERSION) \
	  -no-undefined $(MODFLAG) \
	  $(FCFLAGS) $(EXTRAFLAGS) $^ $(LIBS) \
	  $(EXTRALIBS)

$(PUREBABELGEN) $(BABELGEN) : babel-stamp
	@if test -f $@; then \
	    touch $@; \
	else \
	    rm -f babel-stamp ; \
	    $(MAKE) babel-stamp; \
	fi

babel-stamp: $(SIDLFILE)
	@rm -f babel-temp
	@touch babel-temp
	babel $(BABELFLAG) $(SIDLFILE) 
	@mv -f babel-temp $@

lib$(LIBNAME).scl : $(IORSRCS)
ifeq ($(IORSRCS),)
	echo "lib$(LIBNAME).scl is not needed for client-side C bindings."
else
	-rm -f $@
	echo '<?xml version="1.0" ?>' > $@
	echo '<scl>' >> $@	
	if test `uname` = "Darwin"; then scope="global"; else scope="local"; \
	   fi ; \
          echo ' <library uri="'`pwd`/lib$(LIBNAME).la'" scope="'"$$scope"'" resolution="lazy" >' >> $@
	grep __set_epv $^ /dev/null | awk 'BEGIN {FS=":"} { print $$1}' | sort -u | sed -e 's/_IOR.c//g' -e 's/_/./g' | awk ' { printf "    <class name=\"%s\" desc=\"ior/impl\" />\n", $$1 }' >>$@
	echo "  </library>" >>$@
	echo "</scl>" >>$@
endif

.SUFFIXES: .lo .F90 .c

.c.lo:
	babel-libtool --mode=compile --tag=CC $(CC) $(INCLUDES) $(CFLAGS) $(EXTRAFLAGS) -c -o $@ $<

.F90.lo:
	$(CPP) $(CPPFLAGS) -traditional $(INCLUDES) -P -o $(@:.lo=.tmp) -x c $<
	sed -e 's/^#pragma.*$$//' < $(@:.lo=.tmp) > $(@:.lo=$(F90CPPSUFFIX))
	babel-libtool --mode=compile --tag=FC $(FC) $(MODINCLUDES) $(FCFLAGS) -c -o $@ $(@:.lo=$(F90CPPSUFFIX))
	rm -f $(@:.lo=$(F90CPPSUFFIX)) $(@:.lo=.tmp)

clean : 
	-rm -f $(PUREBABELGEN) babel-temp babel-stamp *.o *.lo *.mod

realclean : clean
	-rm -f lib$(LIBNAME).la lib$(LIBNAME).scl
	-rm -rf .libs

install : install-libs install-headers install-scl


install-libs : lib$(LIBNAME).la
	-mkdir -p $(LIBDIR)
	babel-libtool --mode=install install -c lib$(LIBNAME).la \
	  $(LIBDIR)/lib$(LIBNAME).la

install-scl : $(SCLFILE)
ifneq ($(IORSRCS),)
	-mkdir -p $(LIBDIR)
	-rm -f $(LIBDIR)/lib$(LIBNAME).scl
	echo '<?xml version="1.0" ?>' > $(LIBDIR)/lib$(LIBNAME).scl
	echo '<scl>' >> $(LIBDIR)/lib$(LIBNAME).scl	
	if test `uname` = "Darwin"; then scope="global"; else scope="local"; \
	   fi ; \
          echo ' <library uri="'$(LIBDIR)/lib$(LIBNAME).la'" scope="'"$$scope"'" resolution="lazy" >' >> $(LIBDIR)/lib$(LIBNAME).scl
	grep __set_epv $^ /dev/null | awk 'BEGIN {FS=":"} { print $$1}' | sort -u | sed -e 's/_IOR.c//g' -e 's/_/./g' | awk ' { printf "    <class name=\"%s\" desc=\"ior/impl\" />\n", $$1 }' >>$(LIBDIR)/lib$(LIBNAME).scl
	echo "  </library>" >>$(LIBDIR)/lib$(LIBNAME).scl
	echo "</scl>" >>$(LIBDIR)/lib$(LIBNAME).scl
endif

install-headers : $(IORHDRS) $(STUBHDRS) $(STUBDOCS)
	-mkdir -p $(INCLDIR)
	for i in $^ ; do \
	  babel-libtool --mode=install cp $$i $(INCLDIR)/$$i ; \
	done

.PHONY: all clean realclean install install-libs install-headers install-scl
