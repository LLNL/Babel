##
## File:        Makefile.am 
## Package:     Babel makefiles
## Revision:    $Revision: 7189 $
## Modified:    $Date: 2011-09-27 11:40:37 -0700 (Tue, 27 Sep 2011) $
## Description: automake makefile to build the Babel distribution
##
## Copyright (c) 2000-2002, The Regents of the University of Calfornia.
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

if ENABLE_REGRESSION
if ENABLE_DOCUMENTATION
SUBDIRS = . bin compiler runtime share lib doc examples regression contrib
else
SUBDIRS = . bin compiler runtime share lib examples regression contrib
endif
else
if ENABLE_DOCUMENTATION
SUBDIRS = . bin compiler runtime share lib doc examples contrib
else
SUBDIRS = . bin compiler runtime share lib examples contrib
endif
endif
EXTRA_DIST = ANNOUNCE BUGS CHANGES COPYRIGHT LICENSE INSTALL README.md THANKS svnrevision.sh svnrevision.awk

# these are sometimes left over from the configure tests
DISTCLEANFILES=toolcheck.log

# We got tired about complaints about (innocuous) .nfs files
clean-local:
	sleep 5; find . -name ".nfs*" -exec rm {} \;

# remove babel.make only if its a vpath build
distclean-local:
	if test "X$(srcdir)" != "X."; then\
	    find . -name babel.make -exec rm {} \; ;\
	fi

dist-hook:
	find $(distdir) \( -name "*_Impl.[ch]" -o -name "*_Impl.[ch]xx" \) -exec grep -q "^#line" {} \; -exec sed -i -e '/^#line/d' {} \;

libtool: @LIBTOOL_DEPS@
	$(SHELL) ./config.status --recheck

installcheck-local:
	@if test ! -f $(libdir)/libsidl.la; then	\
	  echo $(MAKE) $(AM_MAKEFLAGS) install;		\
	  $(MAKE) $(AM_MAKEFLAGS) install;		\
	fi

# Convenience targets for the developer
doc: html-local
check-rmi:
	$(MAKE) -C regression check-rmi

html-local:
	$(MAKE) -C doc/html make-javadoc

AUTOHEADER = @AUTOHEADER@ \
             && cd runtime/sidl \
	     && mv -f ignore_me.h.in ignore_me.h.in.bak \
	     && awk -f changeundef.awk ignore_me.h.in.bak \
	        > ignore_me.h.in \
             && rm -f ignore_me.h.in.bak

ACLOCAL_AMFLAGS = -I runtime/m4

pkgconfigdir = $(libdir)/pkgconfig
pkgconfig_DATA = $(builddir)/babel.pc

