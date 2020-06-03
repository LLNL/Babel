##
## File:        Makefile.am 
## Package:     Babel makefiles
## Revision:    $Revision: 7188 $
## Modified:    $Date: 2011-09-27 11:38:42 -0700 (Tue, 27 Sep 2011) $
## Description: automake makefile to build the Babel configuration information
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

SUBDIRS = config

VERSION=@VERSION@

all-am: babel-stamp
dist-hook: babel-stamp

babel-stamp : $(top_srcdir)/runtime/sidl/sidl.sidl
	test -d repository || mkdir repository
	rm -f repository/*.xml
	rm -f repository/*.dtd
	cp $(top_srcdir)/compiler/gov/llnl/babel/dtds/html-lite.dtd repository
	sed -e s%@CURRENT_DIR@%`pwd`/repository/%g 		\
	  $(top_srcdir)/compiler/gov/llnl/babel/dtds/sidl.dtd.in \
	   > repository/sidl.dtd
	sed -e s%@CURRENT_DIR@%`pwd`/repository/%g 		\
	  $(top_srcdir)/compiler/gov/llnl/babel/dtds/comment.dtd.in \
	   > repository/comment.dtd
	$(SHELL) ../bin/babel					\
	  --text=xml						\
	  --output-directory=repository    		      \
	  $(top_srcdir)/runtime/sidl/sidl.sidl
if WITH_SIDLX
	$(SHELL) ../bin/babel					\
	  --text=xml						\
	  --output-directory=repository	-! -Rrepository		\
	  $(top_srcdir)/runtime/sidlx/sidlx.sidl
endif
	touch babel-stamp

clean-local:
	rm -rf repository babel-stamp

install-data-local:
	@$(NORMAL_INSTALL)
	$(mkinstalldirs) $(DESTDIR)$(datadir)/$(PACKAGE)-$(VERSION)/repository
	@list=`find repository 					\
		\( -name .svn -prune \) -o			\
		\( -name CVS -prune \) -o \( -name SCCS -prune \) -o \
		\( -name "*.xml" -o -name "*.dtd" \) -print`;		\
	for f in $$list; do					\
	  echo $(INSTALL_DATA) $$f $(DESTDIR)$(datadir)/$(PACKAGE)-$(VERSION)/$$f;	\
	  $(INSTALL_DATA) $$f $(DESTDIR)$(datadir)/$(PACKAGE)-$(VERSION)/$$f;		\
	done

uninstall-local:
	@$(NORMAL_UNINSTALL)
	rm -rf $(DESTDIR)$(datadir)/$(PACKAGE)-$(VERSION)/repository
