EXTRA_DIST = babel-life.tar.gz java-makefile.tar.gz babel-rmi.tar.gz sidltojava nopoe llnl_prog_babel.m4 installcheck.pbs

babel-life.tar.gz:
	(cd $(srcdir) ; $(AMTAR) chof - --exclude=CVS babel-life ) | GZIP=$(GZIP_ENV) gzip -c >babel-life.tar.gz

java-makefile.tar.gz:
	(cd $(srcdir) ; $(AMTAR) chof - --exclude=CVS java-makefile ) | GZIP=$(GZIP_ENV) gzip -c >java-makefile.tar.gz

babel-rmi.tar.gz:
	(cd $(srcdir) ; $(AMTAR) chof - --exclude=CVS babel-rmi ) | GZIP=$(GZIP_ENV) gzip -c >babel-rmi.tar.gz
