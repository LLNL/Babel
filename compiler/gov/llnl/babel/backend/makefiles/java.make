# -*- Makefile -*-
# Generic Python Babel wrapper GNU Makefile
# $Id: java.make 7460 2012-05-01 17:44:48Z adrian $
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
# JARDIR is where the .jar file is installed
JARDIR=$(LIBDIR)
# the default installation installs the stub header and IOR header files
# in INCLDIR
INCLDIR=$(PREFIX)/include

# most of the rest of the file should not require editing

SCLFILE=lib$(LIBNAME).scl
ifeq ($(SKELSRCS),)
  MODFLAGS=
  BABELFLAG=--client=java
else
  MODFLAGS=-module
  BABELFLAG=--server=java
endif
PUREBABELGEN=$(IORHDRS) $(IORSRCS) $(SKELSRCS) $(STUBHDRS) $(STUBSRCS)
IOROBJS=$(IORSRCS:.c=.lo)
STUBOBJS=$(STUBSRCS:.c=.lo)
SKELOBJS=$(SKELSRCS:.c=.lo)

all : lib$(LIBNAME).jar lib$(LIBNAME).la $(SCLFILE)

$(IOROBJS) : $(IORHDRS)
$(STUBOBJS) : $(IORHDRS) $(STUBHDRS)
$(SKELOBJS) : $(IORHDRS) $(STUBHDRS)
JAVA=`babel-config --query-var=JAVA`
JAVAC=`babel-config --query-var=JAVAC`
JAR=`babel-config --query-var=JAR`
JAVACFLAGS=`babel-config --flags-javac`
BVERSION=`babel-config --version`
JARDIR=`babel-config --jardir`
JAVALIBS=`babel-config --libs-java`
CLASSPATH=$(JARDIR)/sidlstub_$(BVERSION).jar:$(JARDIR)/sidl-$(BVERSION).jar
CC=`babel-config --query-var=CC`
CPFLAG=`babel-config --query-var=JAVA_ADDCLASSPATH_FLAG`
INCLUDES=`babel-config --includes-jni`
CFLAGS=`babel-config --flags-c` `babel-config --flags-java`

lib$(LIBNAME).jar : babel-stamp
	$(JAVAC) $(JAVACFLAGS) $(CPFLAG) $(CLASSPATH) `find . -name "*.java" -type f`
	$(JAR) cf $@ `find . -name "*.class" -type f`

$(PUREBABELGEN) : babel-stamp
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

lib$(LIBNAME).la : $(IOROBJS) $(STUBOBJS) $(SKELOBJS)
	babel-libtool --mode=link --tag=CC $(CC) -o $@ \
	  -rpath $(LIBDIR) -release $(VERSION) \
	  -no-undefined $(MODFLAG) \
	  $^ $(JAVALIBS)

lib$(LIBNAME).scl : $(IORSRCS) $(STUBSRCS)
	-rm -f $@
	echo "<?xml version=\"1.0\" ?>" > $@
	echo "<scl>" >> $@
	if test `uname` = "Darwin"; then \
	  scope="global"; \
	else \
	  scope="local"; \
	fi ; \
	echo "  <library uri=\""`pwd`"/lib$(LIBNAME).la\" scope=\"$$scope\" resolution=\"lazy\" >" >> $@
	grep __set_epv $(IORSRCS) /dev/null | awk 'BEGIN {FS=":"} { print $$1}' | sort -u | sed -e 's/_IOR.c//g' -e 's/_/./g' | awk ' { printf "    <class name=\"%s\" desc=\"ior/impl\" />\n", $$1 }' >> $@
	grep __register $(STUBSRCS) $(synch_STUBSRCS) /dev/null | awk 'BEGIN {FS=":"} { print $$1}' | sort -u | sed -e 's/_jniStub.c//g' -e 's/_/./g' | awk ' { printf "    <class name=\"%s\" desc=\"java\" />\n", $$1 }' >> $@
	echo "  </library>" >> $@
	echo "</scl>" >> $@

install : install-scl install-headers install-libs install-jar

install-libs : lib$(LIBNAME).la
	-mkdir -p $(LIBDIR)
	babel-libtool --mode=install install -c lib$(LIBNAME).la \
	  $(LIBDIR)/lib$(LIBNAME).la

install-jar : lib$(LIBNAME).jar
	-mkdir -p $(JARDIR)
	babel-libtool --mode=install cp lib$(LIBNAME).jar $(JARDIR)/lib$(LIBNAME).jar

install-headers : $(IORHDRS) $(STUBHDRS)
	-mkdir -p $(INCLDIR)
	for i in $^ ; do \
	  babel-libtool --mode=install cp $$i $(INCLDIR)/$$i ; \
	done

install-scl : $(IORSRCS) $(STUBSRCS)
	-mkdir -p $(LIBDIR)
	-rm -f $(LIBDIR)/lib$(LIBNAME).scl
	echo "<?xml version=\"1.0\" ?>" > $(LIBDIR)/lib$(LIBNAME).scl
	echo "<scl>" >> $(LIBDIR)/lib$(LIBNAME).scl
	if test `uname` = "Darwin"; then \
	  scope="global"; \
	else \
	  scope="local"; \
	fi ; \
	echo "  <library uri=\"$(LIBDIR)/lib$(LIBNAME).la\" scope=\"$$scope\" resolution=\"lazy\" >" >> $(LIBDIR)/lib$(LIBNAME).scl
	grep __set_epv $(IORSRCS) /dev/null | awk 'BEGIN {FS=":"} { print $$1}' | sort -u | sed -e 's/_IOR.c//g' -e 's/_/./g' | awk ' { printf "    <class name=\"%s\" desc=\"ior/impl\" />\n", $$1 }' >> $(LIBDIR)/lib$(LIBNAME).scl
	grep __register $(STUBSRCS) $(synch_STUBSRCS) /dev/null | awk 'BEGIN {FS=":"} { print $$1}' | sort -u | sed -e 's/_jniStub.c//g' -e 's/_/./g' | awk ' { printf "    <class name=\"%s\" desc=\"java\" />\n", $$1 }' >> $(LIBDIR)/lib$(LIBNAME).scl
	echo "  </library>" >> $(LIBDIR)/lib$(LIBNAME).scl
	echo "</scl>" >> $(LIBDIR)/lib$(LIBNAME).scl

.SUFFIXES: .lo

.c.lo:
	babel-libtool --mode=compile --tag=CC $(CC) $(INCLUDES) $(CFLAGS) $(EXTRAFLAGS) -c -o $@ $<

clean :
	-rm -f `find . -name "*.class" -type f`
	-rm -f babel-temp babel-stamp *.o *.lo
	-rm -f $(PUREBABELGEN)

realclean : clean
	-rm -f lib$(LIBNAME).la lib$(LIBNAME).scl lib$(LIBNAME).jar
	-rm -rf .libs

.PHONY: all clean realclean install-scl install-libs install-headers install install-jar
