BABELDIR   = $(top_builddir)/bin
CLEANFILES=cca-stamp strop-stamp babel.make.depends

all-local: cca-stamp strop-stamp
dist-hook: cca-stamp strop-stamp

cca-stamp : $(srcdir)/../cca.sidl
	$(SHELL) $(BABELDIR)/babel \
	  --text=xml $(srcdir)/../cca.sidl
	touch cca-stamp

strop-stamp: cca-stamp $(srcdir)/../strop.sidl
	$(SHELL) $(BABELDIR)/babel \
	  --text=xml -R. $(srcdir)/../strop.sidl
	touch strop-stamp

clean-local:
	$(RM) -f *.xml
