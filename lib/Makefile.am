##
## File:        Makefile.am 
## Package:     Babel library directory
## Revision:    $Revision: 7188 $
## Modified:    $Date: 2011-09-27 11:38:42 -0700 (Tue, 27 Sep 2011) $
## Description: automake makefile for the Babel library directory
##
## Copyright (c) 2000-2001, The Regents of the University of Calfornia.
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


SUBDIRS = . sidlstubs
BABELJAR = @PACKAGE@-@VERSION@.jar
SIDLJAR  = sidl-@VERSION@.jar

if SUPPORT_JAVA
SIDLJARDATA=$(SIDLJAR)
else
SIDLJARDATA=
endif


jardir = $(prefix)/lib
jar_DATA = $(BABELJAR)						\
	   $(SIDLJARDATA)					\
	   LICENSE-SAX.html					\
	   java-getopt-1.0.7.jar				\
	   java-getopt.LICENSE					\
	   java-getopt.INFO					\
	   xml-apis.jar						\
	   xerces-2.9.1.jar					\
	   xerces.LICENSE					\
	   xerces.NOTICE					\
	   xerces.INFO

EXTRA_DIST = $(jar_DATA)

$(BABELJAR): 
	if test -f $(SIDLJAR); then chmod +w $(SIDLJAR); fi 
	cd ../compiler && $(MAKE)

$(SIDLJAR): ../runtime/java/$(SIDLJAR)
	if test -f $(SIDLJAR); then chmod +w $(SIDLJAR); fi 
	cp ../runtime/java/$(SIDLJAR) . 

../runtime/java/$(SIDLJAR):
	cd ../runtime/java && $(MAKE) jarfile

install-data-hook:
	-test -n "$(SIDLJARDATA)" && cd $(DESTDIR)$(jardir) && rm -f sidl.jar && ln -s $(SIDLJAR) sidl.jar

uninstall-hook:
	-cd $(DESTDIR)$(jardir) && rm -f sidl.jar

clean-local:
	@if test "X$(srcdir)" != "X."; then	\
	  echo rm -f $(BABELJAR) $(SIDLJAR);	\
	  rm -f $(BABELJAR) $(SIDLJAR);		\
	fi

distclean-local:
	rm -f babel*.jar sidl*.jar
	@if test "X$(srcdir)" != "X."; then	\
	  echo rm -f $(jar_DATA);		\
	  rm -f $(jar_DATA);			\
	fi

maintainer-clean-local:
	rm -f babel*.jar sidl*.jar
