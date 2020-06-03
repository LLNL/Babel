
check_PROGRAMS = checkCtorDtor


OUTPUTSIDL=$(srcdir)/../output/output.sidl

BABEL=`if test "$(BABELDIR)" != "$(top_builddir)/bin" ; then echo babel | sed '$(transform)' ; else echo babel; fi`
BABELDIR    = $(top_builddir)/bin
INCLUDEDIR  = $(top_builddir)/runtime/sidl
INCLUDEDIR2 = .
if WITH_SIDLX
INCLUDEDIRX  = $(top_builddir)/runtime/sidlx
LIBSIDLX     = $(top_builddir)/runtime/sidlx/libsidlx.la
INSTALLEDSIDLX = $(libdir)/libsidlx.la
else
INCLUDEDIRX  = $(INCLUDEDIR)
LIBSIDLX     = 
INSTALLEDSIDLX = 
endif
LIBSYNC     = ../output/libC/libOutput.la
include babel.make
include synch_babel.make
PUREBABELGEN = $(STUBSRCS) $(RSTUBSRCS) $(SKELSRCS) $(IORSRCS) \
	$(IORHDRS) $(STUBHDRS) $(synch_STUBSRCS) $(synch_STUBHDRS) \
	$(synch_RSTUBSRCS) $(synch_IORHDRS)

BABELGEN = $(IMPLSRCS) $(IMPLHDRS)

CLEANFILES=$(PUREBABELGEN) $(EXTRACLEAN) libImpl.scl babel-stamp babel-temp \
	stamp-installcheck core \
	babel.make.package  synch_babel.make.package \
	babel.make.depends synch_babel.make.depends

EXTRA_DIST=babel.make synch_babel.make construct.sidl
AM_CPPFLAGS   = -I. -I$(INCLUDEDIR) -I$(INCLUDEDIR2) -I$(INCLUDEDIRX)

$(BABELGEN) $(PUREBABELGEN) : babel-stamp
	@if test -f $@; then \
	    touch $@; \
	else \
	    rm -f babel-stamp; \
	    $(MAKE) $(AM_MAKEFLAGS) babel-stamp; \
	fi

checkCtorDtor_SOURCES=$(BABELGEN) $(PUREBABELGEN) driver.c
checkCtorDtor_LDFLAGS=-static-libtool-libs
checkCtorDtor_LDADD=$(LIBSYNC) $(LIBSIDLX)

driver.o : $(IORHDRS) $(STUBHDRS) $(synch_IORHDRS) $(synch_STUBHDRS) 


babel-stamp : $(SIDLFILE) $(OUTPUTSIDL)
	@rm -f babel-temp
	@touch babel-temp
	$(SHELL) $(BABELDIR)/$(BABEL) --vpath="$(srcdir)" --server=C \
	  $(BABELEXTRA) $(srcdir)/construct.sidl
	$(SHELL) $(BABELDIR)/$(BABEL) --client=C --make-prefix=synch_  \
	  $(BABELEXTRA) $(OUTPUTSIDL)
	@mv babel-temp $@

clean-local:
	test "X$(srcdir)" = "X." || rm -f $(IMPLSRCS) $(IMPLHDRS) babel.make \
	       synch_babel.make *.testresult

check-local:
	@if test -f stamp-installcheck; then		\
	  echo $(MAKE) $(AM_MAKEFLAGS) clean;		\
	  $(MAKE) $(AM_MAKEFLAGS) clean;		\
	fi
	$(MAKE) $(AM_MAKEFLAGS)  $(check_PROGRAMS)

installcheck-local:
	@if test ! -f stamp-installcheck; then		\
	  echo $(MAKE) $(AM_MAKEFLAGS) clean;		\
	  $(MAKE) $(AM_MAKEFLAGS) clean;		\
	fi
	$(MAKE) $(AM_MAKEFLAGS)				\
	  BABELDIR=$(bindir)				\
	  INCLUDEDIR=$(includedir)			\
	  INCLUDEDIR2=$(exec_prefix)/include		\
	  INCLUDEDIRX=$(includedir)			\
	  LIBSIDLX=$(INSTALLEDSIDLX)                   \
	  $(check_PROGRAMS)
	touch stamp-installcheck

update-babel-make: babel-stamp
	if test "X$(srcdir)" != "X."; then \
	   cp babel.make synch_babel.make $(srcdir); \
	fi
# Make sure Makefile.in is newer than babel.make
dist-hook:
	touch -c $(distdir)/Makefile.in
