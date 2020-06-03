BABEL=`if test "$(BABELDIR)" != "$(top_builddir)/bin" ; then echo babel | sed '$(transform)' ; else echo babel; fi`
BABELDIR   = $(top_builddir)/bin
INCLUDEDIR = $(top_builddir)/runtime/sidl
LIBSIDL    = $(top_builddir)/runtime/sidl/libsidl.la
EXTRA_DIST = babel.make sidlx_babel.make
AM_CPPFLAGS = -I$(INCLUDEDIR)
PUREBABELGEN=$(STUBSRCS) $(STUBHDRS) $(IORHDRS)
SIDLXBABELGEN=$(sidlx_STUBSRCS) $(sidlx_STUBHDRS) $(sidlx_IORHDRS)
if WITH_SIDLX
ALLSRCS=$(PUREBABELGEN) $(SIDLXBABELGEN)
INSTALLHDRS=$(STUBHDRS) $(sidlx_STUBHDRS)
else
ALLSRCS=$(PUREBABELGEN)
INSTALLHDRS=$(STUBHDRS)
endif

lib_LTLIBRARIES = libsidlstub_cxx.la

cxxincldir = $(includedir)/cxx
nodist_cxxincl_HEADERS = $(INSTALLHDRS)

nodist_libsidlstub_cxx_la_SOURCES = $(ALLSRCS)
libsidlstub_cxx_la_LIBADD   = $(LIBSIDL)
libsidlstub_cxx_la_LDFLAGS  = -no-undefined -release @VERSION@
CLEANFILES=$(PUREBABELGEN) $(SIDLXBABELGEN) \
	babel-stamp babel-temp stamp-installcheck core \
	babel.make.package babel.make.depends sidlx_babel.make.package \
	sidlx_babel.make.depends sidlx-stamp sidlx-temp

$(PUREBABELGEN) : babel-stamp
## cf. http://www.gnu.org/software/automake/manual/automake.html#Multiple-Outputs
## Recover from the removal of $@
	@if test -f $@; then :; else \
	  trap 'rm -rf babel.lock babel-stamp' 1 2 13 15; \
## mkdir is a portable test-and-set
	  if mkdir babel.lock 2>/dev/null; then \
## This code is being executed by the first process.
	    rm -f babel-stamp; \
	    $(MAKE) $(AM_MAKEFLAGS) babel-stamp; \
	    result=$$?; rm -rf babel.lock; exit $$result; \
	  else \
## This code is being executed by the follower processes.
## Wait until the first process is done.
	    while test -d babel.lock; do sleep 1; done; \
## Succeed if and only if the first process succeeded.
	    test -f babel-stamp; \
	  fi; \
	fi

babel-stamp: $(top_srcdir)/runtime/sidl/sidl.sidl
	@rm -f babel-temp
	@touch babel-temp
	$(SHELL) $(BABELDIR)/$(BABEL) --client=Cxx --include-referenced sidl
	@mv -f babel-temp $@

$(SIDLXBABELGEN) : sidlx-stamp
## cf. http://www.gnu.org/software/automake/manual/automake.html#Multiple-Outputs
## Recover from the removal of $@
	@if test -f $@; then :; else \
	  trap 'rm -rf sidlx.lock sidlx-stamp' 1 2 13 15; \
## mkdir is a portable test-and-set
	  if mkdir sidlx.lock 2>/dev/null; then \
## This code is being executed by the first process.
	    rm -f sidlx-stamp; \
	    $(MAKE) $(AM_MAKEFLAGS) sidlx-stamp; \
	    result=$$?; rm -rf sidlx.lock; exit $$result; \
	  else \
## This code is being executed by the follower processes.
## Wait until the first process is done.
	    while test -d sidlx.lock; do sleep 1; done; \
## Succeed if and only if the first process succeeded.
	    test -f sidlx-stamp; \
	  fi; \
	fi

sidlx-stamp: $(top_srcdir)/runtime/sidlx/sidlx.sidl $(top_srcdir)/runtime/sidl/sidl.sidl
	@rm -f sidlx-temp
	@touch sidlx-temp
	$(SHELL) $(BABELDIR)/$(BABEL) --make-prefix=sidlx_ --client=Cxx sidlx
	@mv -f sidlx-temp $@

$(sidlx_STUBSRCS:.cxx=.lo) : $(IORHDRS) $(STUBHDRS)

check-local:
	@if test -f stamp-installcheck; then		\
	  rm -f stamp-installcheck;                     \
	  echo $(MAKE) $(AM_MAKEFLAGS) clean;		\
	  $(MAKE) $(AM_MAKEFLAGS) clean;		\
	fi
	$(MAKE) $(AM_MAKEFLAGS) $(lib_LTLIBRARIES)

installcheck-local:
	@if test ! -f stamp-installcheck; then		\
	  echo $(MAKE) $(AM_MAKEFLAGS) clean;		\
	  $(MAKE) $(AM_MAKEFLAGS) clean;		\
	fi
	touch stamp-installcheck
	$(MAKE) $(AM_MAKEFLAGS)				\
	  BABELDIR=$(bindir)				\
	  INCLUDEDIR=$(includedir)			\
	  INCLUDEDIRX=$(includedir)			\
	  INCLUDEDIR2=$(exec_prefix)/include		\
	  LIBSIDL=$(libdir)/libsidl.la			\
	  LIBSIDLX=$(INSTALLEDSIDLX) $(lib_LTLIBRARIES)

clean-local:
	test "X$(srcdir)" = "X." || rm -f babel.make sidlx_babel.make

include $(srcdir)/babel.make
include $(srcdir)/sidlx_babel.make

# Make sure Makefile.in is newer than babel.make
dist-hook:
	touch -c $(distdir)/Makefile.in
