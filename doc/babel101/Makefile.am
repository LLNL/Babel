##
## File:        Makefile.am 
## Package:     Babel makefiles
## Copyright:   (c) 2000-2001 Lawrence Livermore National Security, LLC
## Revision:    $Revision: 6171 $
## Modified:    $Date: 2007-10-08 16:39:28 -0700 (Mon, 08 Oct 2007) $
## Description: automake makefile to build the Babel tutorial
##




docdir = $(datadir)/doc/$(PACKAGE)-$(VERSION)/babel101

docdata = \
  Babel101.html \
  babelcmd.html \
  grammar.html \
  hypre.html \
  SIDLF77.html \
  SIDLCxx.html \
  SIDLPython.html \
  hello/Makefile \
  hello/hello.c \
  hello/hello.sidl \
  hello/lib/Makefile \
  hello/lib/Hello_World_Impl.cc

doc_DATA = $(docdata)
EXTRA_DIST = $(docdata)

#install-data-am:
#	@$(NORMAL_INSTALL)
#	$(mkinstalldirs) $(DESTDIR)$(docdir)/hello/lib
#	@for p in $(docdata); do					\
#	  echo "$(INSTALL_DATA) $(srcdir)/$$p $(DESTDIR)$(docdir)/$$p";	\
#	  $(INSTALL_DATA) $(srcdir)/$$p $(DESTDIR)$(docdir)/$$p;	\
#	done
#uninstall-am: uninstall-data-am
#uninstall-data-am:
#	rm -rf $(DESTDIR)$(docdir)
