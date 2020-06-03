##
## File:        Makefile.am 
## Package:     Babel makefiles
## Copyright:   (c) 2000-2001 Lawrence Livermore National Security, LLC
## Revision:    $Revision: 6673 $
## Modified:    $Date: 2009-06-29 09:50:53 -0700 (Mon, 29 Jun 2009) $
## Description: automake makefile to build the Babel manuals
##




docdir = $(datadir)/doc/$(PACKAGE)-$(VERSION)/manuals

doc_DATA = users_guide.pdf #ref_manual.pdf ref_manual.ps 

dist-hook:
	cp $(srcdir)/users_guide.pdf $(distdir)
	mkdir -p $(distdir)/html
	mkdir -p $(distdir)/html/users_guide
	cp $(srcdir)/html/users_guide/*.html  \
	  $(srcdir)/html/users_guide/*.png  \
	  $(srcdir)/html/users_guide/*.css  \
	  $(distdir)/html/users_guide	

install-data-hook:
	mkdir -p $(DESTDIR)$(docdir)/html
	mkdir -p $(DESTDIR)$(docdir)/html/users_guide
	( cd $(srcdir)/html/users_guide ; cp *.html $(DESTDIR)$(docdir)/html/users_guide/ )
	( cd $(srcdir)/html/users_guide ; cp *.png  $(DESTDIR)$(docdir)/html/users_guide/ )
	( cd $(srcdir)/html/users_guide ; cp *.css  $(DESTDIR)$(docdir)/html/users_guide/ )

uninstall-hook:
	rm -rf $(DESTDIR)$(docdir)/html/users_guide
