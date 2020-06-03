##
## File:        Makefile.am 
## Package:     gov.llnl.babel
## Revision:    $Revision: 7501 $
## Modified:    $Date: 2012-12-14 08:36:10 -0800 (Fri, 14 Dec 2012) $
## Description: automake makefile for the Babel compiler system
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




JAR     = @JAR@
PACKAGE = @PACKAGE@
VERSION = @VERSION@
JARFILE = ../lib/$(PACKAGE)-$(VERSION).jar
CLEANFILES=babel-stamp babel-check-stamp

#
# Java classpath path information for jar files needed for compilation
#

CP = ../lib/java-getopt-1.0.7.jar:../lib/xerces-2.9.1.jar:../lib/xml-apis.jar

if SUPPORT_CYGWIN
  CLASSPATH = `cygpath --path --windows "$(CP)"`
else
  CLASSPATH = $(CP)
endif

#
# Compile the Babel jar file.  Recompile only if the jar file does not
# exist or if any of the java sources are newer than the jar file.
#

all-local: jarfile babel-stamp
check-local: babel-check-stamp
$(JARFILE): jarfile

jarfile:
	@if $(top_srcdir)/svnrevision.sh --topdir=$(top_srcdir) \
	    --srcfile=$(srcdir)/gov/llnl/babel/revision.txt \
	    --outfile=$(builddir)/gov/llnl/babel/revision.txt \
	   || test ! -f $(JARFILE); then				\
	  $(MAKE) $(AM_MAKEFLAGS) force-jarfile;			\
	else								\
	  newfiles=`find $(srcdir)/gov -name SCCS -prune -o             \
	    -name CVS -prune -o						\
	    -name .svn -prune -o					\
	    -name "*Test.java" -o					\
	    \( -name "*.java" -o -name "*.dtd" -o -name "*.html" -o      \
	       -name "*.css" -o -name "*.[ch]" -o -name "*.make" \)     \
	    -newer $(JARFILE) -print`;			                \
	  if test "x$$newfiles" != "x"; then                         	\
	    $(MAKE) $(AM_MAKEFLAGS) force-jarfile;			\
	  fi								\
	fi

JAVAC_FLAGS = -g -source 1.4 -target 1.4 -d . -classpath "$(CLASSPATH)"

force-jarfile:
	@javafiles=`find $(srcdir)/gov -name SCCS -prune -o 		\
	   -name CVS -prune -o						\
	   -name .svn -prune -o						\
	    -name "*Test.java" -o					\
	   -name "*.java" -print`;					\
	if test "x$$javafiles" != "x"; then				\
	  echo '$(JAVAC) $(JAVACFLAGS) $(JAVAC_FLAGS)' $$javafiles;	\
	  $(JAVAC) $(JAVACFLAGS) $(JAVAC_FLAGS) $$javafiles; 		\
	  compilestat=$$?;						\
	else								\
	  compilestat=0;						\
	fi;								\
	if test $$compilestat = 0; then					\
	  if test "X$(srcdir)" != "X."; then				\
	    dtdfiles=`cd $(srcdir) ; 					\
		find gov -name .svn -prune -o -name '*.dtd' -print`;	\
	    xslfiles=`cd $(srcdir) ; \
		find gov -name .svn -prune -o \( -name '*.html' -o -name "*.css" \) -print`;	\
	    cfiles=`cd $(srcdir) ; find gov -name .svn -prune -o \( -name '*.[ch]' \) -print`;	\
	    makefiles=`cd $(srcdir) ; find gov -name .svn -prune -o \( -name '*.make' \) -print`;	\
	    for f in $$dtdfiles $$cfiles $$makefiles $$xslfiles; do			\
	      echo mkdir -p `dirname $$f`;				\
	      mkdir -p `dirname $$f`;					\
	      echo cp -f $(srcdir)/$$f $$f;				\
	      cp -f $(srcdir)/$$f $$f;					\
	    done;							\
	  fi;								\
	  classfiles=`find gov -name .svn -prune -o \( -name '*.class' -print \) `; \
	  textfiles=`find gov -name .svn -prune -o \( -name "*.dtd" -o -name "*.[ch]" -o -name "*.html" -o -name "*.css" -o -name "*.txt" -o -name "*.make" \) -print`; \
	  echo chmod -f +w $(JARFILE) ;					\
	  chmod -f +w $(JARFILE) ;					\
	  echo  $(JAR) cf $(JARFILE) $$classfiles;			\
	  $(JAR) cf $(JARFILE) $$classfiles;				\
	  echo  $(JAR) uf $(JARFILE) $$textfiles;			\
	  $(JAR) uf $(JARFILE) $$textfiles;				\
	else								\
	  exit 1;							\
	fi

#
# Make the parser.  This can only be done in the source directory, since it
# will over-write the java files.
#

SIDLDIR=$(srcdir)/gov/llnl/babel/parsers/sidl
parser:
	@echo Compiling sidl.jj;			\
	cd $(SIDLDIR); 					\
	../../../../../../bin/javacc sidl.jj ;		\
	perl -p -i -e 's/Enumeration enum/Enumeration e/;' \
			-e 's/enum\./e\./g;' \
			-e 's/ASCII\_CharStream/SimpleCharStream/g;' Parser.java

#
# Make clean and copy distribution files to the distribution directory
#

clean-local:
	@if test -d gov; then				  \
	    find gov -name '*.class' -exec rm -f {} \; ;\
	    if test "X$(srcdir)" != "X."; then \
	      find gov \( -name '*.dtd' -o -name '*.txt' -o -name "*.[ch]" -o -name "*.html" -o -name "*.css" -o -name "*.make" \) -exec rm -f {} \; ;\
	    fi \
	fi

dist-hook: jarfile babel-stamp babel-check-stamp
	dirs=`cd $(srcdir); find gov -type d \( -name CVS -prune -o     \
              -name SCCS -prune -o -name .svn -prune -o -print \)`;	\
	for d in $$dirs; do						\
	  mkdir -p $(distdir)/$$d;					\
	done
	cp -f $(builddir)/gov/llnl/babel/revision.txt \
	   $(distdir)/gov/llnl/babel/revision.txt
	files=`cd $(srcdir); find gov \( -name CVS -prune \) -o 	\
	   \( -name SCCS -prune \) -o \( -name .svn -prune \) -o	\
	   \( -name "*.java" -o 					\
	    -name "*.dtd" -o -name "*.dtd.in" -o -name "sidl.jj" 	\
	    -o -name "SIDLParser.jj*" 					\
	    -o -name "*.html" -o -name "*.css" -o -name "*.make"	\
	    -o -name "*.c" -o   -name "*.h" \) -print`;			\
	for f in $$files; do						\
	  echo cp $(srcdir)/$$f $(distdir)/$$f;				\
	  cp $(srcdir)/$$f $(distdir)/$$f;				\
	done

if WITH_SIDLX
SIDLX_GEN=--vpath ${abs_top_srcdir}/runtime/sidlx \
	  -o ${top_builddir}/runtime/sidlx --touch babel-stamp \
	  ${top_srcdir}/runtime/sidl/sidl.sidl \
	  ${top_srcdir}/runtime/sidlx/sidlx.sidl --server=c
CLIENT_SYMS=sidl sidlx
else
SIDLX_GEN= ${top_srcdir}/runtime/sidl/sidl.sidl ${top_srcdir}/runtime/sidlx/sidlx.sidl
CLIENT_SYMS=sidl
endif


babel-stamp : $(JARFILE)
	if test -d ${top_builddir}/share/repository ; then \
	  $(RM) ${top_builddir}/share/repository/*.xml ; \
	else \
	  mkdir -p ${top_builddir}/share/repository ; \
	fi
	../bin/babel --multi \
	  --no-default-repository --include-referenced --generate-sidl-stdlib \
	  -o ${top_builddir}/runtime/sidl --touch babel-stamp \
	  --vpath ${abs_top_srcdir}/runtime/sidl \
	  ${top_srcdir}/runtime/sidl/sidl.sidl --server=c --reset \
	  --no-default-repository $(SIDLX_GEN) \
	  -o ${top_builddir}/runtime/python --touch babel-src-stamp \
	  --generate-sidl-stdlib --client=python \
	  -o ${top_builddir}/share --touch babel-stamp \
	  -o ${top_builddir}/share/repository --text=xml --reset \
	  --include-referenced sidl \
	  -o ${top_builddir}/lib/sidlstubs/Cxx --touch babel-stamp --client=cxx \
	  -o ${top_builddir}/lib/sidlstubs/F77 --touch babel-stamp --client=f77 \
	  -o ${top_builddir}/lib/sidlstubs/F90 --touch babel-stamp --client=f90 \
	  -o ${top_builddir}/lib/sidlstubs/F03 --touch babel-stamp --client=f03 \
	  -o ${top_builddir}/lib/sidlstubs/Java --touch babel-stamp --client=java  --reset \
	  sidlx --make-prefix=sidlx_ \
	  -o ${top_builddir}/lib/sidlstubs/Cxx --touch sidlx-stamp --client=cxx \
	  -o ${top_builddir}/lib/sidlstubs/F77 --touch sidlx-stamp --client=f77 \
	  -o ${top_builddir}/lib/sidlstubs/F90 --touch sidlx-stamp --client=f90 \
	  -o ${top_builddir}/lib/sidlstubs/F03 --touch sidlx-stamp --client=f03 \
	  -o ${top_builddir}/lib/sidlstubs/Java --touch sidlx-stamp --client=java
	touch babel-stamp

# This target is a redundant performance enhancement, as to not load
# the JVM for every single babel invocation
#
# Run the following bash code to regenerate most of this:
#
# cd regression
# \
  printf "\t; "; \
  for testcase in args arrays contracts enums exceptions inherit objarg ordering overload sorting strings struct; do \
      printf "# ${testcase} --------------------- \n"; \
      printf "\t ../bin/babel --multi \\\\\n"; \
      printf "\t    \${top_srcdir}/regression/${testcase}/${testcase}.sidl \\\\\n"; \
      for libdir in ${testcase}/lib[CFJP]*; do \
        libdir=`basename ${libdir}`; \
        printf "\t    --vpath \${abs_top_srcdir}/regression/${testcase}/${libdir} \\\\\n"; \
        printf "\t    -o \${top_builddir}/regression/${testcase}/${libdir} \\\\\n"; \
        printf "\t    -T babel-stamp --server `echo ${libdir}|cut -c 4-` \\\\\n";  \
      done; \
      for rundir in ${testcase}/run[CFJP]*; do \
        rundir=`basename ${rundir}`; \
        printf "\t    -o \${top_builddir}/regression/${testcase}/${rundir} \\\\\n"; \
        printf "\t    -T babel-stamp --client `echo ${rundir}|cut -c 4-` \\\\\n";  \
      done; \
      printf "\t; "; \
  done
#
#


babel-check-stamp: $(JARFILE) babel-stamp
	../bin/babel --multi \
	  ${top_srcdir}/regression/output/output.sidl \
	  -o ${top_builddir}/regression/output/libC -T babel-stamp \
	  --vpath ${abs_top_srcdir}/regression/output/libC \
	   --server=c --make-prefix synch_  \
	  -o ${top_builddir}/regression/args/libC -T synch-stamp -c c \
	  -o ${top_builddir}/regression/args/libCxx -T synch-stamp -c cxx \
	  -o ${top_builddir}/regression/args/libF77 -T synch-stamp -c f77 \
	  -o ${top_builddir}/regression/args/libF90 -T synch-stamp -c f90 \
	  -o ${top_builddir}/regression/args/libPython -T synch-stamp -c python \
	  -o ${top_builddir}/regression/args/libJava -T synch-stamp -c java \
	  -o ${top_builddir}/regression/args/runJava -T synch-stamp -c java \
	  -o ${top_builddir}/regression/arrays/libC -T synch-stamp -c c \
	  -o ${top_builddir}/regression/arrays/libCxx -T synch-stamp -c cxx \
	  -o ${top_builddir}/regression/arrays/libF77 -T synch-stamp -c f77 \
	  -o ${top_builddir}/regression/arrays/libF90 -T synch-stamp -c f90 \
	  -o ${top_builddir}/regression/arrays/libPython -T synch-stamp -c python \
	  -o ${top_builddir}/regression/arrays/libJava -T synch-stamp -c java \
	  -o ${top_builddir}/regression/arrays/runJava -T synch-stamp -c java \
	  -o ${top_builddir}/regression/enums/libC -T synch-stamp -c c \
	  -o ${top_builddir}/regression/enums/libCxx -T synch-stamp -c cxx \
	  -o ${top_builddir}/regression/enums/libF77 -T synch-stamp -c f77 \
	  -o ${top_builddir}/regression/enums/libF90 -T synch-stamp -c f90 \
	  -o ${top_builddir}/regression/enums/libJava -T synch-stamp -c java \
	  -o ${top_builddir}/regression/enums/runJava -T synch-stamp -c java \
	  -o ${top_builddir}/regression/exceptions/libC -T synch-stamp -c c \
	  -o ${top_builddir}/regression/exceptions/libCxx -T synch-stamp -c cxx \
	  -o ${top_builddir}/regression/exceptions/libF77 -T synch-stamp -c f77 \
	  -o ${top_builddir}/regression/exceptions/libF90 -T synch-stamp -c f90 \
	  -o ${top_builddir}/regression/exceptions/libPython -T synch-stamp -c python \
	  -o ${top_builddir}/regression/exceptions/libJava -T synch-stamp -c java \
	  -o ${top_builddir}/regression/exceptions/runJava -T synch-stamp -c java \
	  -o ${top_builddir}/regression/hooks/libC -T synch-stamp -c c \
	  -o ${top_builddir}/regression/hooks/libCxx -T synch-stamp -c cxx \
	  -o ${top_builddir}/regression/hooks/libF77 -T synch-stamp -c f77 \
	  -o ${top_builddir}/regression/hooks/libF90 -T synch-stamp -c f90 \
	  -o ${top_builddir}/regression/hooks/libPython -T synch-stamp -c python \
	  -o ${top_builddir}/regression/hooks/libJava -T synch-stamp -c java \
	  -o ${top_builddir}/regression/inherit/libC -T synch-stamp -c c \
	  -o ${top_builddir}/regression/inherit/libCxx -T synch-stamp -c cxx \
	  -o ${top_builddir}/regression/inherit/libF77 -T synch-stamp -c f77 \
	  -o ${top_builddir}/regression/inherit/libF90 -T synch-stamp -c f90 \
	  -o ${top_builddir}/regression/inherit/libPython -T synch-stamp -c python \
	  -o ${top_builddir}/regression/inherit/libJava -T synch-stamp -c java \
	  -o ${top_builddir}/regression/inherit/runJava -T synch-stamp -c java \
	  -o ${top_builddir}/regression/objarg/libC -T synch-stamp -c c \
	  -o ${top_builddir}/regression/objarg/libCxx -T synch-stamp -c cxx \
	  -o ${top_builddir}/regression/objarg/libF90 -T synch-stamp -c f90 \
	  -o ${top_builddir}/regression/objarg/libPython -T synch-stamp -c python \
	  -o ${top_builddir}/regression/objarg/libJava -T synch-stamp -c java \
	  -o ${top_builddir}/regression/objarg/runC -T synch-stamp -c c \
	  -o ${top_builddir}/regression/ordering/libC -T synch-stamp -c c \
	  -o ${top_builddir}/regression/ordering/libCxx -T synch-stamp -c cxx \
	  -o ${top_builddir}/regression/ordering/libF77 -T synch-stamp -c f77 \
	  -o ${top_builddir}/regression/ordering/libF90 -T synch-stamp -c f90 \
	  -o ${top_builddir}/regression/ordering/libPython -T synch-stamp -c python \
	  -o ${top_builddir}/regression/ordering/libJava -T synch-stamp -c java \
	  -o ${top_builddir}/regression/ordering/runJava -T synch-stamp -c java \
	  -o ${top_builddir}/regression/overload/libC -T synch-stamp -c c \
	  -o ${top_builddir}/regression/overload/libCxx -T synch-stamp -c cxx \
	  -o ${top_builddir}/regression/overload/libF77 -T synch-stamp -c f77 \
	  -o ${top_builddir}/regression/overload/libF90 -T synch-stamp -c f90 \
	  -o ${top_builddir}/regression/overload/libPython -T synch-stamp -c python \
	  -o ${top_builddir}/regression/overload/libJava -T synch-stamp -c java \
	  -o ${top_builddir}/regression/overload/runJava -T synch-stamp -c java \
	  -o ${top_builddir}/regression/sorting/libC -T synch-stamp -c c \
	  -o ${top_builddir}/regression/sorting/libCxx -T synch-stamp -c cxx \
	  -o ${top_builddir}/regression/sorting/libF77 -T synch-stamp -c f77 \
	  -o ${top_builddir}/regression/sorting/libF90 -T synch-stamp -c f90 \
	  -o ${top_builddir}/regression/sorting/libPython -T synch-stamp -c python \
	  -o ${top_builddir}/regression/sorting/libJava -T synch-stamp -c java \
	  -o ${top_builddir}/regression/sorting/runJava -T synch-stamp -c java \
	  -o ${top_builddir}/regression/strings/libC -T synch-stamp -c c \
	  -o ${top_builddir}/regression/strings/libCxx -T synch-stamp -c cxx \
	  -o ${top_builddir}/regression/strings/libF77 -T synch-stamp -c f77 \
	  -o ${top_builddir}/regression/strings/libF90 -T synch-stamp -c f90 \
	  -o ${top_builddir}/regression/strings/libPython -T synch-stamp -c python \
	  -o ${top_builddir}/regression/strings/libJava -T synch-stamp -c java \
	  -o ${top_builddir}/regression/strings/runJava -T synch-stamp -c java \
	  -o ${top_builddir}/regression/struct/libC -T synch-stamp -c c \
	  -o ${top_builddir}/regression/struct/libCxx -T synch-stamp -c cxx \
	  -o ${top_builddir}/regression/struct/libPython -T synch-stamp -c python \
	 ; # args --------------------- 
	 ../bin/babel --multi \
	    ${top_srcdir}/regression/args/args.sidl \
	    --vpath ${abs_top_srcdir}/regression/args/libC \
	    -o ${top_builddir}/regression/args/libC \
	    -T babel-stamp --server C \
	    --vpath ${abs_top_srcdir}/regression/args/libCxx \
	    -o ${top_builddir}/regression/args/libCxx \
	    -T babel-stamp --server Cxx \
	    --vpath ${abs_top_srcdir}/regression/args/libF03 \
	    -o ${top_builddir}/regression/args/libF03 \
	    -T babel-stamp --server F03 \
	    --vpath ${abs_top_srcdir}/regression/args/libF77 \
	    -o ${top_builddir}/regression/args/libF77 \
	    -T babel-stamp --server F77 \
	    --vpath ${abs_top_srcdir}/regression/args/libF90 \
	    -o ${top_builddir}/regression/args/libF90 \
	    -T babel-stamp --server F90 \
	    --vpath ${abs_top_srcdir}/regression/args/libJava \
	    -o ${top_builddir}/regression/args/libJava \
	    -T babel-stamp --server Java \
	    --vpath ${abs_top_srcdir}/regression/args/libPython \
	    -o ${top_builddir}/regression/args/libPython \
	    -T babel-stamp --server Python \
	    -o ${top_builddir}/regression/args/runC \
	    -T babel-stamp --client C \
	    -o ${top_builddir}/regression/args/runCxx \
	    -T babel-stamp --client Cxx \
	    -o ${top_builddir}/regression/args/runF03 \
	    -T babel-stamp --client F03 \
	    -o ${top_builddir}/regression/args/runF77 \
	    -T babel-stamp --client F77 \
	    -o ${top_builddir}/regression/args/runF90 \
	    -T babel-stamp --client F90 \
	    -o ${top_builddir}/regression/args/runJava \
	    -T babel-stamp --client Java \
	    -o ${top_builddir}/regression/args/runPython \
	    -T babel-stamp --client Python \
	; # arrays --------------------- 
	 ../bin/babel --multi \
	    ${top_srcdir}/regression/arrays/arrays.sidl \
	    --vpath ${abs_top_srcdir}/regression/arrays/libC \
	    -o ${top_builddir}/regression/arrays/libC \
	    -T babel-stamp --server C \
	    --vpath ${abs_top_srcdir}/regression/arrays/libCxx \
	    -o ${top_builddir}/regression/arrays/libCxx \
	    -T babel-stamp --server Cxx \
	    --vpath ${abs_top_srcdir}/regression/arrays/libF03 \
	    -o ${top_builddir}/regression/arrays/libF03 \
	    -T babel-stamp --server F03 \
	    --vpath ${abs_top_srcdir}/regression/arrays/libF77 \
	    -o ${top_builddir}/regression/arrays/libF77 \
	    -T babel-stamp --server F77 \
	    --vpath ${abs_top_srcdir}/regression/arrays/libF90 \
	    -o ${top_builddir}/regression/arrays/libF90 \
	    -T babel-stamp --server F90 \
	    --vpath ${abs_top_srcdir}/regression/arrays/libJava \
	    -o ${top_builddir}/regression/arrays/libJava \
	    -T babel-stamp --server Java \
	    --vpath ${abs_top_srcdir}/regression/arrays/libPython \
	    -o ${top_builddir}/regression/arrays/libPython \
	    -T babel-stamp --server Python \
	    -o ${top_builddir}/regression/arrays/runC \
	    -T babel-stamp --client C \
	    -o ${top_builddir}/regression/arrays/runCxx \
	    -T babel-stamp --client Cxx \
	    -o ${top_builddir}/regression/arrays/runF03 \
	    -T babel-stamp --client F03 \
	    -o ${top_builddir}/regression/arrays/runF77 \
	    -T babel-stamp --client F77 \
	    -o ${top_builddir}/regression/arrays/runF90 \
	    -T babel-stamp --client F90 \
	    -o ${top_builddir}/regression/arrays/runJava \
	    -T babel-stamp --client Java \
	    -o ${top_builddir}/regression/arrays/runPython \
	    -T babel-stamp --client Python \
	; # contracts --------------------- 
	 ../bin/babel --multi \
	    ${top_srcdir}/regression/contracts/contracts.sidl \
	    --vpath ${abs_top_srcdir}/regression/contracts/libC \
	    -o ${top_builddir}/regression/contracts/libC \
	    -T babel-stamp --server C \
	    --vpath ${abs_top_srcdir}/regression/contracts/libCxx \
	    -o ${top_builddir}/regression/contracts/libCxx \
	    -T babel-stamp --server Cxx \
	    --vpath ${abs_top_srcdir}/regression/contracts/libF03 \
	    -o ${top_builddir}/regression/contracts/libF03 \
	    -T babel-stamp --server F03 \
	    --vpath ${abs_top_srcdir}/regression/contracts/libF77 \
	    -o ${top_builddir}/regression/contracts/libF77 \
	    -T babel-stamp --server F77 \
	    --vpath ${abs_top_srcdir}/regression/contracts/libF90 \
	    -o ${top_builddir}/regression/contracts/libF90 \
	    -T babel-stamp --server F90 \
	    --vpath ${abs_top_srcdir}/regression/contracts/libJava \
	    -o ${top_builddir}/regression/contracts/libJava \
	    -T babel-stamp --server Java \
	    --vpath ${abs_top_srcdir}/regression/contracts/libPython \
	    -o ${top_builddir}/regression/contracts/libPython \
	    -T babel-stamp --server Python \
	    -o ${top_builddir}/regression/contracts/runC \
	    -T babel-stamp --client C \
	    -o ${top_builddir}/regression/contracts/runCxx \
	    -T babel-stamp --client Cxx \
	    -o ${top_builddir}/regression/contracts/runF03 \
	    -T babel-stamp --client F03 \
	    -o ${top_builddir}/regression/contracts/runF77 \
	    -T babel-stamp --client F77 \
	    -o ${top_builddir}/regression/contracts/runF90 \
	    -T babel-stamp --client F90 \
	    -o ${top_builddir}/regression/contracts/runJava \
	    -T babel-stamp --client Java \
	    -o ${top_builddir}/regression/contracts/runPython \
	    -T babel-stamp --client Python \
	; # enums --------------------- 
	 ../bin/babel --multi \
	    ${top_srcdir}/regression/enums/enums.sidl \
	    --vpath ${abs_top_srcdir}/regression/enums/libC \
	    -o ${top_builddir}/regression/enums/libC \
	    -T babel-stamp --server C \
	    --vpath ${abs_top_srcdir}/regression/enums/libCxx \
	    -o ${top_builddir}/regression/enums/libCxx \
	    -T babel-stamp --server Cxx \
	    --vpath ${abs_top_srcdir}/regression/enums/libF03 \
	    -o ${top_builddir}/regression/enums/libF03 \
	    -T babel-stamp --server F03 \
	    --vpath ${abs_top_srcdir}/regression/enums/libF77 \
	    -o ${top_builddir}/regression/enums/libF77 \
	    -T babel-stamp --server F77 \
	    --vpath ${abs_top_srcdir}/regression/enums/libF90 \
	    -o ${top_builddir}/regression/enums/libF90 \
	    -T babel-stamp --server F90 \
	    --vpath ${abs_top_srcdir}/regression/enums/libJava \
	    -o ${top_builddir}/regression/enums/libJava \
	    -T babel-stamp --server Java \
	    --vpath ${abs_top_srcdir}/regression/enums/libPython \
	    -o ${top_builddir}/regression/enums/libPython \
	    -T babel-stamp --server Python \
	    -o ${top_builddir}/regression/enums/runC \
	    -T babel-stamp --client C \
	    -o ${top_builddir}/regression/enums/runCxx \
	    -T babel-stamp --client Cxx \
	    -o ${top_builddir}/regression/enums/runF03 \
	    -T babel-stamp --client F03 \
	    -o ${top_builddir}/regression/enums/runF77 \
	    -T babel-stamp --client F77 \
	    -o ${top_builddir}/regression/enums/runF90 \
	    -T babel-stamp --client F90 \
	    -o ${top_builddir}/regression/enums/runJava \
	    -T babel-stamp --client Java \
	    -o ${top_builddir}/regression/enums/runPython \
	    -T babel-stamp --client Python \
	; # exceptions --------------------- 
	 ../bin/babel --multi \
	    ${top_srcdir}/regression/exceptions/exceptions.sidl \
	    --vpath ${abs_top_srcdir}/regression/exceptions/libC \
	    -o ${top_builddir}/regression/exceptions/libC \
	    -T babel-stamp --server C \
	    --vpath ${abs_top_srcdir}/regression/exceptions/libCxx \
	    -o ${top_builddir}/regression/exceptions/libCxx \
	    -T babel-stamp --server Cxx \
	    --vpath ${abs_top_srcdir}/regression/exceptions/libF03 \
	    -o ${top_builddir}/regression/exceptions/libF03 \
	    -T babel-stamp --server F03 \
	    --vpath ${abs_top_srcdir}/regression/exceptions/libF77 \
	    -o ${top_builddir}/regression/exceptions/libF77 \
	    -T babel-stamp --server F77 \
	    --vpath ${abs_top_srcdir}/regression/exceptions/libF90 \
	    -o ${top_builddir}/regression/exceptions/libF90 \
	    -T babel-stamp --server F90 \
	    --vpath ${abs_top_srcdir}/regression/exceptions/libJava \
	    -o ${top_builddir}/regression/exceptions/libJava \
	    -T babel-stamp --server Java \
	    --vpath ${abs_top_srcdir}/regression/exceptions/libPython \
	    -o ${top_builddir}/regression/exceptions/libPython \
	    -T babel-stamp --server Python \
	    -o ${top_builddir}/regression/exceptions/runC \
	    -T babel-stamp --client C \
	    -o ${top_builddir}/regression/exceptions/runCxx \
	    -T babel-stamp --client Cxx \
	    -o ${top_builddir}/regression/exceptions/runF03 \
	    -T babel-stamp --client F03 \
	    -o ${top_builddir}/regression/exceptions/runF77 \
	    -T babel-stamp --client F77 \
	    -o ${top_builddir}/regression/exceptions/runF90 \
	    -T babel-stamp --client F90 \
	    -o ${top_builddir}/regression/exceptions/runJava \
	    -T babel-stamp --client Java \
	    -o ${top_builddir}/regression/exceptions/runPython \
	    -T babel-stamp --client Python \
	; # inherit --------------------- 
	 ../bin/babel --multi \
	    ${top_srcdir}/regression/inherit/inherit.sidl \
	    --vpath ${abs_top_srcdir}/regression/inherit/libC \
	    -o ${top_builddir}/regression/inherit/libC \
	    -T babel-stamp --server C \
	    --vpath ${abs_top_srcdir}/regression/inherit/libCxx \
	    -o ${top_builddir}/regression/inherit/libCxx \
	    -T babel-stamp --server Cxx \
	    --vpath ${abs_top_srcdir}/regression/inherit/libF03 \
	    -o ${top_builddir}/regression/inherit/libF03 \
	    -T babel-stamp --server F03 \
	    --vpath ${abs_top_srcdir}/regression/inherit/libF77 \
	    -o ${top_builddir}/regression/inherit/libF77 \
	    -T babel-stamp --server F77 \
	    --vpath ${abs_top_srcdir}/regression/inherit/libF90 \
	    -o ${top_builddir}/regression/inherit/libF90 \
	    -T babel-stamp --server F90 \
	    --vpath ${abs_top_srcdir}/regression/inherit/libJava \
	    -o ${top_builddir}/regression/inherit/libJava \
	    -T babel-stamp --server Java \
	    --vpath ${abs_top_srcdir}/regression/inherit/libPython \
	    -o ${top_builddir}/regression/inherit/libPython \
	    -T babel-stamp --server Python \
	    -o ${top_builddir}/regression/inherit/runC \
	    -T babel-stamp --client C \
	    -o ${top_builddir}/regression/inherit/runCxx \
	    -T babel-stamp --client Cxx \
	    -o ${top_builddir}/regression/inherit/runF03 \
	    -T babel-stamp --client F03 \
	    -o ${top_builddir}/regression/inherit/runF77 \
	    -T babel-stamp --client F77 \
	    -o ${top_builddir}/regression/inherit/runF90 \
	    -T babel-stamp --client F90 \
	    -o ${top_builddir}/regression/inherit/runJava \
	    -T babel-stamp --client Java \
	    -o ${top_builddir}/regression/inherit/runPython \
	    -T babel-stamp --client Python \
	; # objarg --------------------- 
	 ../bin/babel --multi \
	    ${top_srcdir}/regression/objarg/objarg.sidl \
	    --vpath ${abs_top_srcdir}/regression/objarg/libC \
	    -o ${top_builddir}/regression/objarg/libC \
	    -T babel-stamp --server C \
	    --vpath ${abs_top_srcdir}/regression/objarg/libCxx \
	    -o ${top_builddir}/regression/objarg/libCxx \
	    -T babel-stamp --server Cxx \
	    --vpath ${abs_top_srcdir}/regression/objarg/libF03 \
	    -o ${top_builddir}/regression/objarg/libF03 \
	    -T babel-stamp --server F03 \
	    --vpath ${abs_top_srcdir}/regression/objarg/libF90 \
	    -o ${top_builddir}/regression/objarg/libF90 \
	    -T babel-stamp --server F90 \
	    --vpath ${abs_top_srcdir}/regression/objarg/libJava \
	    -o ${top_builddir}/regression/objarg/libJava \
	    -T babel-stamp --server Java \
	    --vpath ${abs_top_srcdir}/regression/objarg/libPython \
	    -o ${top_builddir}/regression/objarg/libPython \
	    -T babel-stamp --server Python \
	    -o ${top_builddir}/regression/objarg/runC \
	    -T babel-stamp --client C \
	    -o ${top_builddir}/regression/objarg/runCxx \
	    -T babel-stamp --client Cxx \
	    -o ${top_builddir}/regression/objarg/runF03 \
	    -T babel-stamp --client F03 \
	    -o ${top_builddir}/regression/objarg/runJava \
	    -T babel-stamp --client Java \
	    -o ${top_builddir}/regression/objarg/runPython \
	    -T babel-stamp --client Python \
	; # ordering --------------------- 
	 ../bin/babel --multi \
	    ${top_srcdir}/regression/ordering/ordering.sidl \
	    --vpath ${abs_top_srcdir}/regression/ordering/libC \
	    -o ${top_builddir}/regression/ordering/libC \
	    -T babel-stamp --server C \
	    --vpath ${abs_top_srcdir}/regression/ordering/libCxx \
	    -o ${top_builddir}/regression/ordering/libCxx \
	    -T babel-stamp --server Cxx \
	    --vpath ${abs_top_srcdir}/regression/ordering/libF03 \
	    -o ${top_builddir}/regression/ordering/libF03 \
	    -T babel-stamp --server F03 \
	    --vpath ${abs_top_srcdir}/regression/ordering/libF77 \
	    -o ${top_builddir}/regression/ordering/libF77 \
	    -T babel-stamp --server F77 \
	    --vpath ${abs_top_srcdir}/regression/ordering/libF90 \
	    -o ${top_builddir}/regression/ordering/libF90 \
	    -T babel-stamp --server F90 \
	    --vpath ${abs_top_srcdir}/regression/ordering/libJava \
	    -o ${top_builddir}/regression/ordering/libJava \
	    -T babel-stamp --server Java \
	    --vpath ${abs_top_srcdir}/regression/ordering/libPython \
	    -o ${top_builddir}/regression/ordering/libPython \
	    -T babel-stamp --server Python \
	    -o ${top_builddir}/regression/ordering/runC \
	    -T babel-stamp --client C \
	    -o ${top_builddir}/regression/ordering/runCxx \
	    -T babel-stamp --client Cxx \
	    -o ${top_builddir}/regression/ordering/runJava \
	    -T babel-stamp --client Java \
	    -o ${top_builddir}/regression/ordering/runPython \
	    -T babel-stamp --client Python \
	; # overload --------------------- 
	 ../bin/babel --multi \
	    ${top_srcdir}/regression/overload/overload.sidl \
	    --vpath ${abs_top_srcdir}/regression/overload/libC \
	    -o ${top_builddir}/regression/overload/libC \
	    -T babel-stamp --server C \
	    --vpath ${abs_top_srcdir}/regression/overload/libCxx \
	    -o ${top_builddir}/regression/overload/libCxx \
	    -T babel-stamp --server Cxx \
	    --vpath ${abs_top_srcdir}/regression/overload/libF03 \
	    -o ${top_builddir}/regression/overload/libF03 \
	    -T babel-stamp --server F03 \
	    --vpath ${abs_top_srcdir}/regression/overload/libF77 \
	    -o ${top_builddir}/regression/overload/libF77 \
	    -T babel-stamp --server F77 \
	    --vpath ${abs_top_srcdir}/regression/overload/libF90 \
	    -o ${top_builddir}/regression/overload/libF90 \
	    -T babel-stamp --server F90 \
	    --vpath ${abs_top_srcdir}/regression/overload/libJava \
	    -o ${top_builddir}/regression/overload/libJava \
	    -T babel-stamp --server Java \
	    --vpath ${abs_top_srcdir}/regression/overload/libPython \
	    -o ${top_builddir}/regression/overload/libPython \
	    -T babel-stamp --server Python \
	    -o ${top_builddir}/regression/overload/runC \
	    -T babel-stamp --client C \
	    -o ${top_builddir}/regression/overload/runCxx \
	    -T babel-stamp --client Cxx \
	    -o ${top_builddir}/regression/overload/runF03 \
	    -T babel-stamp --client F03 \
	    -o ${top_builddir}/regression/overload/runF77 \
	    -T babel-stamp --client F77 \
	    -o ${top_builddir}/regression/overload/runF90 \
	    -T babel-stamp --client F90 \
	    -o ${top_builddir}/regression/overload/runJava \
	    -T babel-stamp --client Java \
	    -o ${top_builddir}/regression/overload/runPython \
	    -T babel-stamp --client Python \
	; # sorting --------------------- 
	 ../bin/babel --multi \
	    ${top_srcdir}/regression/sorting/sorting.sidl \
	    --vpath ${abs_top_srcdir}/regression/sorting/libC \
	    -o ${top_builddir}/regression/sorting/libC \
	    -T babel-stamp --server C \
	    --vpath ${abs_top_srcdir}/regression/sorting/libCxx \
	    -o ${top_builddir}/regression/sorting/libCxx \
	    -T babel-stamp --server Cxx \
	    --vpath ${abs_top_srcdir}/regression/sorting/libF03 \
	    -o ${top_builddir}/regression/sorting/libF03 \
	    -T babel-stamp --server F03 \
	    --vpath ${abs_top_srcdir}/regression/sorting/libF77 \
	    -o ${top_builddir}/regression/sorting/libF77 \
	    -T babel-stamp --server F77 \
	    --vpath ${abs_top_srcdir}/regression/sorting/libF90 \
	    -o ${top_builddir}/regression/sorting/libF90 \
	    -T babel-stamp --server F90 \
	    --vpath ${abs_top_srcdir}/regression/sorting/libJava \
	    -o ${top_builddir}/regression/sorting/libJava \
	    -T babel-stamp --server Java \
	    --vpath ${abs_top_srcdir}/regression/sorting/libPython \
	    -o ${top_builddir}/regression/sorting/libPython \
	    -T babel-stamp --server Python \
	    -o ${top_builddir}/regression/sorting/runC \
	    -T babel-stamp --client C \
	    -o ${top_builddir}/regression/sorting/runCxx \
	    -T babel-stamp --client Cxx \
	    -o ${top_builddir}/regression/sorting/runF03 \
	    -T babel-stamp --client F03 \
	    -o ${top_builddir}/regression/sorting/runF77 \
	    -T babel-stamp --client F77 \
	    -o ${top_builddir}/regression/sorting/runF90 \
	    -T babel-stamp --client F90 \
	    -o ${top_builddir}/regression/sorting/runJava \
	    -T babel-stamp --client Java \
	    -o ${top_builddir}/regression/sorting/runPython \
	    -T babel-stamp --client Python \
	; # strings --------------------- 
	 ../bin/babel --multi \
	    ${top_srcdir}/regression/strings/strings.sidl \
	    --vpath ${abs_top_srcdir}/regression/strings/libC \
	    -o ${top_builddir}/regression/strings/libC \
	    -T babel-stamp --server C \
	    --vpath ${abs_top_srcdir}/regression/strings/libCxx \
	    -o ${top_builddir}/regression/strings/libCxx \
	    -T babel-stamp --server Cxx \
	    --vpath ${abs_top_srcdir}/regression/strings/libF03 \
	    -o ${top_builddir}/regression/strings/libF03 \
	    -T babel-stamp --server F03 \
	    --vpath ${abs_top_srcdir}/regression/strings/libF77 \
	    -o ${top_builddir}/regression/strings/libF77 \
	    -T babel-stamp --server F77 \
	    --vpath ${abs_top_srcdir}/regression/strings/libF90 \
	    -o ${top_builddir}/regression/strings/libF90 \
	    -T babel-stamp --server F90 \
	    --vpath ${abs_top_srcdir}/regression/strings/libJava \
	    -o ${top_builddir}/regression/strings/libJava \
	    -T babel-stamp --server Java \
	    --vpath ${abs_top_srcdir}/regression/strings/libPython \
	    -o ${top_builddir}/regression/strings/libPython \
	    -T babel-stamp --server Python \
	    -o ${top_builddir}/regression/strings/runC \
	    -T babel-stamp --client C \
	    -o ${top_builddir}/regression/strings/runCxx \
	    -T babel-stamp --client Cxx \
	    -o ${top_builddir}/regression/strings/runF03 \
	    -T babel-stamp --client F03 \
	    -o ${top_builddir}/regression/strings/runF77 \
	    -T babel-stamp --client F77 \
	    -o ${top_builddir}/regression/strings/runF90 \
	    -T babel-stamp --client F90 \
	    -o ${top_builddir}/regression/strings/runJava \
	    -T babel-stamp --client Java \
	    -o ${top_builddir}/regression/strings/runPython \
	    -T babel-stamp --client Python \
	; # struct --------------------- 
	 ../bin/babel --multi \
	    ${top_srcdir}/regression/struct/struct.sidl \
	    --vpath ${abs_top_srcdir}/regression/struct/libC \
	    -o ${top_builddir}/regression/struct/libC \
	    -T babel-stamp --server C \
	    --vpath ${abs_top_srcdir}/regression/struct/libCxx \
	    -o ${top_builddir}/regression/struct/libCxx \
	    -T babel-stamp --server Cxx \
	    --vpath ${abs_top_srcdir}/regression/struct/libF03 \
	    -o ${top_builddir}/regression/struct/libF03 \
	    -T babel-stamp --server F03 \
	    --vpath ${abs_top_srcdir}/regression/struct/libF77 \
	    -o ${top_builddir}/regression/struct/libF77 \
	    -T babel-stamp --server F77 \
	    --vpath ${abs_top_srcdir}/regression/struct/libF90 \
	    -o ${top_builddir}/regression/struct/libF90 \
	    -T babel-stamp --server F90 \
	    --vpath ${abs_top_srcdir}/regression/struct/libJava \
	    -o ${top_builddir}/regression/struct/libJava \
	    -T babel-stamp --server Java \
	    --vpath ${abs_top_srcdir}/regression/struct/libPython \
	    -o ${top_builddir}/regression/struct/libPython \
	    -T babel-stamp --server Python \
	    -o ${top_builddir}/regression/struct/runC \
	    -T babel-stamp --client C \
	    -o ${top_builddir}/regression/struct/runCxx \
	    -T babel-stamp --client Cxx \
	    -o ${top_builddir}/regression/struct/runF03 \
	    -T babel-stamp --client F03 \
	    -o ${top_builddir}/regression/struct/runF77 \
	    -T babel-stamp --client F77 \
	    -o ${top_builddir}/regression/struct/runF90 \
	    -T babel-stamp --client F90 \
	    -o ${top_builddir}/regression/struct/runJava \
	    -T babel-stamp --client Java \
	    -o ${top_builddir}/regression/struct/runPython \
	    -T babel-stamp --client Python \
	; 	touch babel-check-stamp
