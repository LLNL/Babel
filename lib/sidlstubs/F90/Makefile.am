
BABEL=`if test "$(BABELDIR)" != "$(top_builddir)/bin" ; then echo babel | sed '$(transform)' ; else echo babel; fi`
BABELGEN=$(BASICMODULESRC) $(TYPEMODULESRCS) $(ARRAYMODULESRCS) $(STUBMODULESRCS) $(STUBSRCS) $(STUBHDRS) $(IORHDRS)
SIDLXGEN=$(sidlx_BASICMODULESRC) $(sidlx_TYPEMODULESRCS) $(sidlx_ARRAYMODULESRCS) $(sidlx_STUBMODULESRCS) $(sidlx_STUBSRCS) $(sidlx_STUBHDRS) $(sidlx_IORHDRS)

if SUPPORT_FORTRAN90

BABELDIR   = $(top_builddir)/bin
INCLUDEDIR = $(top_builddir)/runtime/sidl
LIBSIDL    = $(top_builddir)/runtime/sidl/libsidl.la
EXTRA_DIST = babel.make sidlx_babel.make
AM_CPPFLAGS = -I$(INCLUDEDIR)

if WITH_SIDLX
ALLSRCS=$(BABELGEN) $(SIDLXGEN)
INSTALLHDRS=$(STUBHDRS) $(sidlx_STUBHDRS)
else
ALLSRCS=$(BABELGEN)
INSTALLHDRS=$(STUBHDRS)
endif


lib_LTLIBRARIES = libsidlstub_f90.la

f90incldir = $(includedir)/f90
nodist_f90incl_HEADERS = $(INSTALLHDRS)

nodist_libsidlstub_f90_la_SOURCES      = $(ALLSRCS) 
libsidlstub_f90_la_LIBADD       = $(LIBSIDL) $(FCLIBS) $(top_builddir)/runtime/sidl/sidlf90array.lo
libsidlstub_f90_la_LDFLAGS      = -no-undefined -release @VERSION@ $(AM_FFLAGS) $(FCFLAGS)
libsidlstub_f90_la_LINK	        = $(LINK) $(libsidlstub_f90_la_LDFLAGS)

endif
CLEANFILES = $(BABELGEN) $(SIDLXGEN) \
	babel-stamp stamp-installcheck babel-temp \
	babel.make.package babel.make.depends \
	sidlx_babel.make.package sidlx_babel.make.depends \
	sidlx-stamp sidlx-temp

# Customize some F90 macros since automake (as of 1.9.3)
# still doesn't include the --tag specifier for libtool
LTPPFCCOMPILE = $(AM_V_PPFC)$(LIBTOOL) --silent --mode=compile --tag=FC $(FC) \
	$(DEFAULT_INCLUDES) $(INCLUDES) \
	$(AM_FCFLAGS) $(FCFLAGS) $(FCFLAGS_f90)


$(TYPEMODULESRCS:.F90=.lo) : $(BASICMODULESRC:.F90=.lo)
$(ARRAYMODULESRCS:.F90=.lo) : $(TYPEMODULESRCS:.F90=.lo)
$(STUBMODULESRCS:.F90=.lo) : $(ARRAYMODULESRCS:.F90=.lo) $(TYPEMODULESRCS:.F90=.lo)
$(IMPLMODULESRCS:.F90=.lo) : $(STUBMODULESRCS:.F90=.lo)

$(sidlx_TYPEMODULESRCS:.F90=.lo) : $(BASICMODULESRC:.F90=.lo) $(sidlx_BASICMODULESRC:.F90=.lo)
$(sidlx_ARRAYMODULESRCS:.F90=.lo) : $(TYPEMODULESRCS:.F90=.lo) $(sidlx_TYPEMODULESRCS:.F90=.lo)
$(sidlx_STUBMODULESRCS:.F90=.lo) : $(ARRAYMODULESRCS:.F90=.lo) $(TYPEMODULESRCS:.F90=.lo) $(sidlx_ARRAYMODULESRCS:.F90=.lo) $(sidlx_TYPEMODULESRCS:.F90=.lo)
$(sidlx_IMPLMODULESRCS:.F90=.lo) : $(STUBMODULESRCS:.F90=.lo) $(sidlx_STUBMODULESRCS:.F90=.lo)

$(BABELGEN) : babel-stamp
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
	$(SHELL) $(BABELDIR)/$(BABEL) --client=F90 --include-referenced sidl
	@mv -f babel-temp $@

$(SIDLXGEN) : sidlx-stamp
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
	$(SHELL) $(BABELDIR)/$(BABEL) --client=F90 --make-prefix=sidlx_ sidlx
	@mv -f sidlx-temp $@

check-local:
	@if test -f stamp-installcheck; then		\
	  rm -f stamp-installcheck ; 			\
	  echo $(MAKE) $(AM_MAKEFLAGS) clean ;		\
	  $(MAKE) $(AM_MAKEFLAGS) clean ;		\
	  echo $(MAKE) $(AM_MAKEFLAGS) check ;		\
	  $(MAKE) $(AM_MAKEFLAGS) check ;		\
	fi

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
	  LIBSIDL=$(libdir)/libsidl.la		\
	  LIBSIDLX=$(INSTALLEDSIDLX) $(lib_LTLIBRARIES)

# * is used in the for loop instead of *.mod to avoid the situation
# where *.mod does not match any files. Using * with case works
# when --disable-fortran90 is specified.
install-data-local:
	test -z "$(f90incldir)" || $(mkdir_p) "$(DESTDIR)$(f90incldir)"
	@for p in *; do \
	  case "$$p" in \
	    *.mod) \
		echo " $(INSTALL_HEADER) '$$p' '$(DESTDIR)$(f90incldir)'"; \
		$(INSTALL_HEADER) "$$p" "$(DESTDIR)$(f90incldir)";; \
	  esac \
	done

uninstall-local:
	$(RM) $(DESTDIR)$(f90incldir)/*.mod;

clean-local:
	test "X$(srcdir)" = "X." || rm -f babel.make sidlx_babel.make
	rm -f *.mod

# Override default F90 macros since automake (as of 1.9.3) doesn't
# really allow us to override preprocessing step.
.F90.o:
	$(AM_V_GEN) \
	$(CPP) -traditional $(DEFS) $(DEFAULT_INCLUDES) $(INCLUDES) $(AM_CPPFLAGS) $(CPPFLAGS) \
		-P -o $(@:.o=.tmp) -x c `test -f '$<' || echo '$(srcdir)/'`$< && \
	sed -e 's/^#pragma.*$$//' < $(@:.o=.tmp) > $(@:.o=$(F90CPPSUFFIX))
	$(AM_V_PPFC)$(PPFCCOMPILE) -c -o $@ $(@:.o=$(F90CPPSUFFIX))
if DELETE_INTERMEDIATE_FILES # debugging
	@rm -f $(@:.o=$(F90CPPSUFFIX)) $(@:.o=.tmp)
endif

.F90.obj:
	$(AM_V_GEN) \
	$(CPP) -traditional $(DEFS) $(DEFAULT_INCLUDES) $(INCLUDES) $(AM_CPPFLAGS) $(CPPFLAGS) \
		-P -o $(@:.obj=.tmp) -x c `test -f '$<' || echo '$(srcdir)/'`$< && \
	sed -e 's/^#pragma.*$$//' < $(@:.obj=.tmp) > $(@:.obj=$(F90CPPSUFFIX))
	$(AM_V_PPFC)$(PPFCCOMPILE) -c -o $@ $(@:.obj=$(F90CPPSUFFIX))
if DELETE_INTERMEDIATE_FILES # debugging
	@rm -f $(@:.obj=$(F90CPPSUFFIX)) $(@:.obj=.tmp)
endif

.F90.lo:
	$(AM_V_GEN) \
	$(CPP) -traditional $(DEFS) $(DEFAULT_INCLUDES) $(INCLUDES) $(AM_CPPFLAGS) $(CPPFLAGS) \
		 -P -o $(@:.lo=.tmp) -x c `test -f '$<' || echo '$(srcdir)/'`$< && \
	sed -e 's/^#pragma.*$$//' < $(@:.lo=.tmp) > $(@:.lo=$(F90CPPSUFFIX))
	$(LTPPFCCOMPILE) -c -o $@  $(@:.lo=$(F90CPPSUFFIX))
if DELETE_INTERMEDIATE_FILES # debugging
	@rm -f $(@:.lo=$(F90CPPSUFFIX)) $(@:.lo=.tmp)
endif

include $(srcdir)/babel.make
include $(srcdir)/sidlx_babel.make

# Make sure Makefile.in is newer than babel.make
dist-hook:
	touch -c $(distdir)/Makefile.in
