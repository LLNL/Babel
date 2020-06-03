##
## File:        Makefile.am 
## Package:     Babel makefiles
## Copyright:   (c) 2000-2005 Lawrence Livermore National Security, LLC
## Revision:    $Revision: 7487 $
## Modified:    $Date: 2012-10-29 07:54:57 -0700 (Mon, 29 Oct 2012) $
## Description: automake makefile to build the Babel documentation
##




docdir = $(datadir)/doc/$(PACKAGE)-$(VERSION)/compiler-javadoc

JAVADOC=@JAVADOC@
JAVADOCFILES = package-list stylesheet.css *.html

CP = ../../lib/java-getopt-1.0.7.jar:../../lib/xerces-1.1.1.jar
SP = $(top_srcdir)/compiler:$(top_srcdir)/runtime/java

if SUPPORT_CYGWIN
  CLASSPATH  = `cygpath --path --windows "$(CP)"`
  SOURCEPATH = `cygpath --path --windows "$(SP)"`
else
  CLASSPATH  = $(CP)
  SOURCEPATH = $(SP)
endif

BABEL_PACKAGES =				\
	gov.llnl.babel				\
	gov.llnl.babel.ast			\
	gov.llnl.babel.backend			\
	gov.llnl.babel.backend.c		\
	gov.llnl.babel.backend.fortran		\
	gov.llnl.babel.backend.html		\
	gov.llnl.babel.backend.ior		\
	gov.llnl.babel.backend.jdk		\
	gov.llnl.babel.backend.mangler		\
	gov.llnl.babel.backend.matlab		\
	gov.llnl.babel.backend.python		\
	gov.llnl.babel.backend.rmi		\
	gov.llnl.babel.backend.sidl		\
	gov.llnl.babel.backend.ucxx		\
	gov.llnl.babel.backend.writers		\
	gov.llnl.babel.backend.xml		\
	gov.llnl.babel.cli			\
	gov.llnl.babel.config			\
	gov.llnl.babel.msg			\
	gov.llnl.babel.parsers.sidl2		\
	gov.llnl.babel.parsers.xml		\
	gov.llnl.babel.repository		\
	gov.llnl.babel.symbols			\
	gov.llnl.babel.url			\
	gov.llnl.babel.url.cookie		\
	gov.llnl.babel.visitor			\
	gov.llnl.babel.xml			\
	gov.llnl.sidl				\
	sidl

make-javadoc:
	if test -n "$(JAVADOC)" -a -x "`which $(JAVADOC)`"; then \
	   rm -f $(JAVADOCFILES); \
	   rm -rf gov sidl; \
	   CLASSPATH="$(CLASSPATH)"; \
	   SOURCEPATH="$(SOURCEPATH)"; \
	   $(JAVADOC) -source 1.4 -classpath "$$CLASSPATH" \
		  -sourcepath "$$SOURCEPATH" -d . $(BABEL_PACKAGES);\
	fi

package-list: make-javadoc
stylesheet.css : make-javadoc

install-data-local: make-javadoc
	@$(NORMAL_INSTALL)
	$(mkinstalldirs) $(DESTDIR)$(docdir)
	@list=`ls $(JAVADOCFILES)`;				\
	for p in $$list; do					\
	  echo " $(INSTALL_DATA) $$p $(DESTDIR)$(docdir)/$$p";	\
	  $(INSTALL_DATA) $$p $(DESTDIR)$(docdir)/$$p;		\
	done
	if test -d gov; then \
	  cp -r gov/ $(DESTDIR)$(docdir); \
	else \
	  echo "gov javadoc documentation missing (skipped)"; \
	fi
	if test -d sidl; then \
	  cp -r sidl/ $(DESTDIR)$(docdir); \
	else \
	  echo "sidl javadoc documentation missing (skipped)"; \
	fi

uninstall-local:
	@$(NORMAL_UNINSTALL)
	rm -rf $(DESTDIR)$(docdir)

distclean-local:
	@if test "X$(srcdir)" != "X."; then		\
	  echo rm -rf $(JAVADOCFILES) gov sidl;	\
	  rm -rf $(JAVADOCFILES) gov sidl;	\
	fi
	$(RM) -rf resources

maintainer-clean-local:
	rm -rf $(JAVADOCFILES) gov sidl
