dnl dnl
dnl dnl Process this file with autoconf to produce a configure script.
dnl dnl
dnl testing

dnl
dnl Begin the normal autoconf initialization here...
dnl

############################################################
#
# 0. Variable Initialization
#
enable_static=yes
enable_shared=yes
with_fortran77=yes
with_fortran90=yes
with_fortran03=yes
with_cxx=yes
with_python=yes
with_java=yes
msgs="
	Recap:"


############################################################
#
# Boilerplate Startup Stuff
#
AC_PREREQ(2.64)
AC_INIT(babel, 2.0.1, babel-bugs@cca-forum.org)
CCA_DIAGNOSE_INIT(toolcheck.log)
AC_CONFIG_SRCDIR([COPYRIGHT])
AC_CONFIG_MACRO_DIR([runtime/m4])
AC_CONFIG_AUX_DIR([runtime/config])
AC_CANONICAL_TARGET

AM_INIT_AUTOMAKE([silent-rules foreign no-installinfo no-texinfo.tex 1.11 dist-bzip2 dist-xz])
dnl do `make V=1' or `./configure --disable-silent-rules' for a verbose build
AM_SILENT_RULES([yes])
dnl To regenerate configure and .in files, configure with --enable-maintainer-mode
AM_MAINTAINER_MODE([disable])

AC_CONFIG_HEADERS(runtime/sidl/ignore_me.h)
AC_DEFINE([BABEL_VERSION_HEX],[0x02000010],
          [Hexified form of release number: 2 hex digits each for major, minor, bugfix, and alpha/beta/candidate/distributed])

LLNL_CHECK_AUTOMAKE([1.11.1])
LLNL_CHECK_AUTOCONF([2.68])

############################################################
#
# Additional configure options.
#
LLNL_CROSS_COMPILING
LLNL_AUTO_DISABLE
LLNL_ENABLE_REGRESSION
LLNL_ENABLE_DOCUMENTATION

############################################################
# This anticipates the definition of $ECHO. We need this early on,
# but LT_INIT cannot be run until after the compiler checks.
_LT_PROG_ECHO_BACKSLASH

############################################################
#
# Subdir config
#
AC_CONFIG_SUBDIRS([runtime])

CCA_DIAGNOSE_BEGIN([CC])
AC_PROG_CC
CCA_DIAGNOSE_END([CC])
AC_CHECK_HEADERS([inttypes.h sys/types.h])

############################################################
#
# C Compiler
#
CCA_DIAGNOSE_BEGIN([CC])
LLNL_CONFIRM_BABEL_C_SUPPORT
CCA_DIAGNOSE_END([CC])

CCA_DIAGNOSE_BEGIN([PTHREAD_LIBS])
ACX_PTHREAD([],[])
CCA_DIAGNOSE_END([PTHREAD_LIBS])

############################################################
#
# F90 Compiler
#
CCA_DIAGNOSE_BEGIN([F90])
LLNL_CONFIRM_BABEL_F90_SUPPORT
if test "X$enable_fortran90" = "Xyes"; then #begin real F90 checking
LLNL_CONFIRM_BABEL_F90_SUPPORT2
fi #end real F90 checking
LLNL_CONFIRM_BABEL_F90_SUPPORT3
CCA_DIAGNOSE_END([F90])

############################################################
#
# F03 Compiler
#
CCA_DIAGNOSE_BEGIN([F03])
LLNL_CONFIRM_BABEL_F03_SUPPORT
if test "X$enable_fortran03" = "Xyes"; then #begin real F03 checking
LLNL_CONFIRM_BABEL_F03_SUPPORT2
fi #end real F03 checking
LLNL_CONFIRM_BABEL_F03_SUPPORT3
CCA_DIAGNOSE_END([F03])

LLNL_PURE_STATIC_RUNTIME
# XML
if test "X$enable_pure_static_runtime" != "Xyes" -o "X$enable_shared" = "Xyes"; then
  CCA_DIAGNOSE_BEGIN([XML2])
  LLNL_LIBPARSIFAL_CONFIG
  if test "X$llnl_found_libparsifal" != "Xyes"; then
    LLNL_LIBXML_CONFIG
  fi
  CCA_DIAGNOSE_END([XML2])
else
  CCA_DIAGNOSE_SKIP([XML2])
  AC_MSG_NOTICE([skipping libparsifal/libxml2 configuration due to pure static runtime])
  XML2_CONFIG="no"
fi

LLNL_PROG_TEST_EF

# CCA
CCA_BUNDLE_TAG

############################################################
#
# Python Support.
#
CCA_DIAGNOSE_BEGIN([PYTHON])
LLNL_CONFIRM_BABEL_PYTHON_SUPPORT
CCA_DIAGNOSE_END([PYTHON])

############################################################
#
# Critical Applications.
#
AC_PROG_INSTALL
AC_PROG_CPP
AC_PROG_AWK
AC_PROG_LN_S
AC_PROG_INSTALL
AC_PROG_MAKE_SET

############################################################
#
# F77 Compiler
#
CCA_DIAGNOSE_BEGIN([F77])
LLNL_CONFIRM_BABEL_F77_SUPPORT
CCA_DIAGNOSE_END([F77])

############################################################
#
# Fortran iso_c_binding module
#
dnl CCA_DIAGNOSE_BEGIN([BINDC])
dnl LLNL_ISO_C_BINDING
dnl CCA_DIAGNOSE_END([BINDC])

############################################################
#
# C++ Compiler
#
CCA_DIAGNOSE_BEGIN([CXX])
LLNL_CONFIRM_BABEL_CXX_SUPPORT
LLNL_CONFIRM_BABEL_CXX_SUPPORT2
CCA_DIAGNOSE_END([CXX])

############################################################
#
# Perform some sanity checking on the compiler choices.  We cannot mix
# GNU and non-GNU tools because libtool uses CC flags for CXX.
#
LLNL_PREVENT_CROSS_COMPILATION
LLNL_PREVENT_UNHOLY_GNU_NONGNU_MIX

############################################################
#
# Java Support
#
CCA_DIAGNOSE_BEGIN([JAVAC])
LLNL_CONFIRM_BABEL_JAVA_SUPPORT
CCA_DIAGNOSE_END([JAVAC])

############################################################
#
# Libtool Options.
#
CCA_DIAGNOSE_BEGIN([LIBTOOL])
LT_PREREQ([2.4])
LT_PATH_LD
LT_CONFIG_LTDL_DIR([runtime/libltdl])
LT_INIT([dlopen])
LTDL_INIT([convenience])
AC_SUBST(LIBTOOL_DEPS)
#AC_LIBTOOL_WIN32_DLL
#AC_LIB_LTDL
CCA_DIAGNOSE_END([LIBTOOL])

############################################################
#
# Cygwin Conditional Makefile Support
#
#AC_CANONICAL_HOST
case $host_os in
  *cygwin* ) CYGWIN=yes;;
         * ) CYGWIN=no;;
esac
AM_CONDITIONAL(SUPPORT_CYGWIN, test "X$CYGWIN" = "Xyes")

############################################################
#
# Static/Dynamic Library Details
#
if test "X$llnl_found_libparsifal" = "Xyes" -o "X$XML2_CONFIG" != "Xno" ; then
  llnl_have_xml_parser=yes
else
  llnl_have_xml_parser=no
fi

AC_MSG_NOTICE(['static: $enable_static'])
AC_MSG_NOTICE(['shared: $enable_shared'])
AC_MSG_NOTICE(['have_xml_parser: $llnl_have_xml_parser'])
AM_CONDITIONAL([SUPPORT_STATIC], [test "X$enable_static" = "Xyes"])
AM_CONDITIONAL([SUPPORT_SHARED], [test "X$enable_shared" = "Xyes" -a "X$llnl_have_xml_parser" = "Xyes"])
if test "X$enable_shared" != "Xyes" -o "X$llnl_have_xml_parser" = "Xno"; then
  AC_DEFINE(SIDL_STATIC_LIBRARY, 1, [Define SIDL_STATIC_LIBRARY to force static loading of libraries])
fi
if test "X$enable_static" != "Xyes" -a "X$llnl_have_xml_parser" = "Xyes"; then
  AC_DEFINE(SIDL_DYNAMIC_LIBRARY, 1, [Define SIDL_DYNAMIC_LIBRARY to force dynamic loading of libraries])
fi

############################################################
#
# Add flags for certain compilers and architectures.
#
case "$CXX" in
*KCC)
  CXXFLAGS="$CXXFLAGS --one_instantiation_per_object"
  ;;
*CC)
  case "$host_os" in
  *irix*)
    CXXFLAGS="$CXXFLAGS -LANG:std"
    ;;
  esac
  ;;
esac


builddir=`pwd`
AC_SUBST(builddir)
AC_SUBST(LIBS)
AC_SUBST(PACKAGE)
AC_SUBST(VERSION)
AC_SUBST(PATH)

eval sidllibprefix="$libdir"
case "$sidllibprefix" in
NONE*)
  sidllibprefix="${prefix}/lib"
  ;;
esac
AC_DEFINE_UNQUOTED(SIDLLIBPREFIX,"$sidllibprefix",[Directory where libsidl.scl and libsidlx.scl should be located.])

############################################################
#
# Utility functions.
#
AC_CHECK_PROGS(LATEX2HTML, latex2html /bin/latex2html /usr/bin/latex2html /usr/local/bin/latex2html)
AM_CONDITIONAL(HAVE_LATEX2HTML, test "X$ac_prog_latex2html" != "X")

############################################################
#
# Experimental sidl extensions (Simple Protocol)
#
if test -z "$with_sidlx"; then
  with_sidlx=yes
fi
AC_ARG_WITH(sidlx,   [  --without-sidlx            disable simple RMI protocol (aka libsidlx.{a,so}) ])
AM_CONDITIONAL(WITH_SIDLX, test "X$with_sidlx" != "Xno" )

############################################################
#
# Experimental native function calls (fast-call)
#
AC_ARG_ENABLE([fast-call],
	AS_HELP_STRING([--enable-fast-call], [Enable fast native function calls (experimental)]),
	[ac_fast_call=$enableval], [ac_fast_call='no'])
AM_CONDITIONAL(WITH_FASTCALL, test "X$ac_fast_call" != "Xno" )

############################################################
#
# Experimental MPI extensions
#

LLNL_PROG_MPI

############################################################
#
# Experimental Lock-Free reference counting
#

LLNL_CHECK_FAST_REFCOUNT

############################################################
#
# DMALLOC memory debugger
#
AC_ARG_WITH([dmalloc],
            [AS_HELP_STRING([--with-dmalloc],
              [(debugging) enable the dmalloc memory debugger for the sidl runtime [default=no]])],
            [], [with_dmalloc=no])
AM_CONDITIONAL(WITH_DMALLOC, test "X$with_dmalloc" != "Xno" )
if test "X$with_dmalloc" != "Xno" ; then
   echo dmalloc memory debugging enabled
   CFLAGS="$CFLAGS -DWITH_DMALLOC"
   CXXFLAGS="$CXXFLAGS -DWITH_DMALLOC"
fi

############################################################
#
# Leave intermediate .f90 and .f03 files around for debugging
#
AC_ARG_ENABLE([leave-preprocessed],
	AS_HELP_STRING([--enable-leave-preprocessed], [(debugging) don't delete preprocessed .f90 and .f03 files around for debugging]),
	[ac_leave_intermediate=$enableval], [ac_leave_intermediate='no'])
AM_CONDITIONAL(DELETE_INTERMEDIATE_FILES, test "X$ac_leave_intermediate" == "Xno" )

############################################################
#
# Cleanup from testing and relocation of jarfiles.
#
rm -f conftest.exe conftest conftest.f config/conftest config/conftest.f
test ! -d lib && mkdir lib
for jar in `cd $srcdir/lib; echo *.jar`; do
  test ! -f lib/$jar && cp $srcdir/lib/$jar lib/$jar
done

############################################################
#
# Increase warning level for libraries.  We cannot do this earlier in the
# configure, since it may cause problems with the autoconf tests.
#

if test "X$GCC" = "Xyes"; then
  CFLAGS="$CFLAGS -Wall -Wstrict-prototypes"
fi
if test "X$GXX" = "Xyes"; then
  CXXFLAGS="$CXXFLAGS -Wall"
fi
# Always use the PTHREAD flags if any
CFLAGS="$CFLAGS $PTHREAD_CFLAGS"
LIBS="$PTHREAD_LIBS $LIBS"
CXXFLAGS="$CXXFLAGS $PTHREAD_CFLAGS"

############################################################
#
# config.h header.  Note: use the header generated by runtime/configure.
#
#
AH_TOP([
#error Ignore this file.  Use babel_config.h instead.  This file exists to placate automake.
])

############################################################
#
# File Generation
#
dnl Reproduce this list with
dnl ls -1 bin/babel* examples/*/*/*.sh regression/*/*/*.sh regression/multi/multitest.py
dnl from $builddir

m4_foreach([my_file],m4_split(m4_normalize([
	bin/babel
	bin/babel-cc
	bin/babel-config
	bin/babel-cxx
	bin/babel-f77
	bin/babel-f90
	bin/babel-genmake.py
	examples/cca/runF77/runFortran.sh
	examples/cca/runF90/runFortran.sh
	examples/cca/runJava/runJava.sh
	examples/hello/runC/runAll.sh
	examples/hello/runCxx/runAll.sh
	examples/hello/runF77/runAll.sh
	examples/hello/runJava/runJava2C.sh
	examples/hello/runJava/runJava2Cxx.sh
	examples/hello/runJava/runJava2F77.sh
	examples/hello/runJava/runJava2Java.sh
	examples/hello/runJava/runJava2Py.sh
	regression/args/runC/runAll.sh
	regression/args/runCxx/runAll.sh
	regression/args/runF03/runAll.sh
	regression/args/runF77/runAll.sh
	regression/args/runF90/runAll.sh
	regression/args/runJava/runJava2C.sh
	regression/args/runJava/runJava2Cxx.sh
	regression/args/runJava/runJava2F03.sh
	regression/args/runJava/runJava2F77.sh
	regression/args/runJava/runJava2F90.sh
	regression/args/runJava/runJava2Java.sh
	regression/args/runJava/runJava2Py.sh
	regression/args/runSIDL/runSIDL.sh
	regression/arrays/runC/runAll.sh
	regression/arrays/runCxx/runAll.sh
	regression/arrays/runF03/runAll.sh
	regression/arrays/runF77/runAll.sh
	regression/arrays/runF90/runAll.sh
	regression/arrays/runJava/runJava2C.sh
	regression/arrays/runJava/runJava2Cxx.sh
	regression/arrays/runJava/runJava2F03.sh
	regression/arrays/runJava/runJava2F77.sh
	regression/arrays/runJava/runJava2F90.sh
	regression/arrays/runJava/runJava2Java.sh
	regression/arrays/runJava/runJava2Py.sh
	regression/arrays/runSIDL/runSIDL.sh
	regression/contracts/runC/runAll.sh
	regression/contracts/runCxx/runAll.sh
	regression/contracts/runF03/runAll.sh
	regression/contracts/runF77/runAll.sh
	regression/contracts/runF90/runAll.sh
	regression/contracts/runJava/runJava2C.sh
	regression/contracts/runJava/runJava2Cxx.sh
	regression/contracts/runJava/runJava2F03.sh
	regression/contracts/runJava/runJava2F77.sh
	regression/contracts/runJava/runJava2F90.sh
	regression/contracts/runJava/runJava2Java.sh
	regression/contracts/runJava/runJava2Py.sh
	regression/contracts/runSIDL/runSIDL.sh
	regression/enums/runC/runAll.sh
	regression/enums/runCxx/runAll.sh
	regression/enums/runF03/runAll.sh
	regression/enums/runF77/runAll.sh
	regression/enums/runF90/runAll.sh
	regression/enums/runJava/runJava2C.sh
	regression/enums/runJava/runJava2Cxx.sh
	regression/enums/runJava/runJava2F03.sh
	regression/enums/runJava/runJava2F77.sh
	regression/enums/runJava/runJava2F90.sh
	regression/enums/runJava/runJava2Java.sh
	regression/enums/runSIDL/runSIDL.sh
	regression/exceptions/runC/runAll.sh
	regression/exceptions/runCxx/runAll.sh
	regression/exceptions/runF03/runAll.sh
	regression/exceptions/runF77/runAll.sh
	regression/exceptions/runF90/runAll.sh
	regression/exceptions/runJava/runJava2C.sh
	regression/exceptions/runJava/runJava2Cxx.sh
	regression/exceptions/runJava/runJava2F03.sh
	regression/exceptions/runJava/runJava2F77.sh
	regression/exceptions/runJava/runJava2F90.sh
	regression/exceptions/runJava/runJava2Java.sh
	regression/exceptions/runJava/runJava2Py.sh
	regression/exceptions/runSIDL/runSIDL.sh
	regression/hooks/runC/runAll.sh
	regression/hooks/runCxx/runAll.sh
	regression/hooks/runF03/runAll.sh
	regression/hooks/runF77/runAll.sh
	regression/hooks/runF90/runAll.sh
	regression/hooks/runJava/runJava2C.sh
	regression/hooks/runJava/runJava2Cxx.sh
	regression/hooks/runJava/runJava2F03.sh
	regression/hooks/runJava/runJava2F77.sh
	regression/hooks/runJava/runJava2F90.sh
	regression/hooks/runJava/runJava2Java.sh
	regression/hooks/runJava/runJava2Py.sh
	regression/hooks/runSIDL/runSIDL.sh
	regression/inherit/runC/runAll.sh
	regression/inherit/runCxx/runAll.sh
	regression/inherit/runF03/runAll.sh
	regression/inherit/runF77/runAll.sh
	regression/inherit/runF90/runAll.sh
	regression/inherit/runJava/runJava2C.sh
	regression/inherit/runJava/runJava2Cxx.sh
	regression/inherit/runJava/runJava2F03.sh
	regression/inherit/runJava/runJava2F77.sh
	regression/inherit/runJava/runJava2F90.sh
	regression/inherit/runJava/runJava2Java.sh
	regression/inherit/runJava/runJava2Py.sh
	regression/inherit/runSIDL/runSIDL.sh
	regression/multi/multitest.py
	regression/objarg/runC/runAll.sh
	regression/objarg/runCxx/runAll.sh
	regression/objarg/runF03/runAll.sh
	regression/objarg/runJava/runJava2C.sh
	regression/objarg/runJava/runJava2Cxx.sh
	regression/objarg/runJava/runJava2F03.sh
	regression/objarg/runJava/runJava2F90.sh
	regression/objarg/runJava/runJava2Java.sh
	regression/objarg/runJava/runJava2Py.sh
	regression/objarg/runSIDL/runSIDL.sh
	regression/ordering/runC/runAll.sh
	regression/ordering/runCxx/runAll.sh
	regression/ordering/runJava/runJava2C.sh
	regression/ordering/runJava/runJava2Cxx.sh
	regression/ordering/runJava/runJava2F03.sh
	regression/ordering/runJava/runJava2F77.sh
	regression/ordering/runJava/runJava2F90.sh
	regression/ordering/runJava/runJava2Java.sh
	regression/ordering/runJava/runJava2Py.sh
	regression/ordering/runSIDL/runSIDL.sh
	regression/overload/runC/runAll.sh
	regression/overload/runCxx/runAll.sh
	regression/overload/runF03/runAll.sh
	regression/overload/runF77/runAll.sh
	regression/overload/runF90/runAll.sh
	regression/overload/runJava/runJava2C.sh
	regression/overload/runJava/runJava2Cxx.sh
	regression/overload/runJava/runJava2F03.sh
	regression/overload/runJava/runJava2F77.sh
	regression/overload/runJava/runJava2F90.sh
	regression/overload/runJava/runJava2Java.sh
	regression/overload/runJava/runJava2Py.sh
	regression/overload/runSIDL/runSIDL.sh
	regression/sorting/runC/runAll.sh
	regression/sorting/runCxx/runAll.sh
	regression/sorting/runF03/runAll.sh
	regression/sorting/runF77/runAll.sh
	regression/sorting/runF90/runAll.sh
	regression/sorting/runJava/runJava2C.sh
	regression/sorting/runJava/runJava2Cxx.sh
	regression/sorting/runJava/runJava2F03.sh
	regression/sorting/runJava/runJava2F77.sh
	regression/sorting/runJava/runJava2F90.sh
	regression/sorting/runJava/runJava2Java.sh
	regression/sorting/runJava/runJava2Py.sh
	regression/sorting/runSIDL/runSIDL.sh
	regression/strings/runC/runAll.sh
	regression/strings/runCxx/runAll.sh
	regression/strings/runF03/runAll.sh
	regression/strings/runF77/runAll.sh
	regression/strings/runF90/runAll.sh
	regression/strings/runJava/runJava2C.sh
	regression/strings/runJava/runJava2Cxx.sh
	regression/strings/runJava/runJava2F03.sh
	regression/strings/runJava/runJava2F77.sh
	regression/strings/runJava/runJava2F90.sh
	regression/strings/runJava/runJava2Java.sh
	regression/strings/runJava/runJava2Py.sh
	regression/strings/runSIDL/runSIDL.sh
	regression/struct/runC/runAll.sh
	regression/struct/runCxx/runAll.sh
	regression/struct/runF03/runAll.sh
	regression/struct/runF77/runAll.sh
	regression/struct/runF90/runAll.sh
	regression/struct/runJava/runJava2C.sh
	regression/struct/runJava/runJava2Cxx.sh
	regression/struct/runJava/runJava2F03.sh
	regression/struct/runJava/runJava2F77.sh
	regression/struct/runJava/runJava2F90.sh
	regression/struct/runJava/runJava2Java.sh
	regression/struct/runJava/runJava2Py.sh
	regression/struct/runSIDL/runSIDL.sh
	regression/wrapper/runC/runAll.sh
	regression/wrapper/runCxx/runAll.sh
	regression/wrapper/runF03/runAll.sh
	regression/wrapper/runF77/runAll.sh
	regression/wrapper/runF90/runAll.sh
	regression/wrapper/runJava/runJava2C.sh
	regression/wrapper/runJava/runJava2Cxx.sh
	regression/wrapper/runJava/runJava2F03.sh
	regression/wrapper/runJava/runJava2F77.sh
	regression/wrapper/runJava/runJava2F90.sh
	regression/wrapper/runJava/runJava2Java.sh
	regression/wrapper/runJava/runJava2Py.sh
	regression/wrapper/runSIDL/runSIDL.sh
])),
	[AC_CONFIG_FILES(my_file, [chmod -f +x ]my_file)])



AC_CONFIG_FILES([
	Makefile
	babel.pc
	bin/Makefile
	compiler/Makefile
	contrib/Makefile
	doc/Makefile
	doc/html/Makefile
	doc/manuals/Makefile
	examples/Makefile
	examples/cca/Makefile
	examples/cca/hello-client/Makefile
	examples/cca/hello-server/Makefile
	examples/cca/libdecaf/Makefile
	examples/cca/repository/Makefile
	examples/cca/runCxx/Makefile
	examples/cca/runF77/Makefile
	examples/cca/runF90/Makefile
	examples/cca/runJava/Makefile
	examples/cca/runPython/Makefile
	examples/hello/Makefile
	examples/hello/libC/Makefile
	examples/hello/libCxx/Makefile
	examples/hello/libF77/Makefile
	examples/hello/libJava/Makefile
	examples/hello/libPython/Makefile
	examples/hello/runC/Makefile
	examples/hello/runCxx/Makefile
	examples/hello/runF77/Makefile
	examples/hello/runJava/Makefile
	examples/hello/runPython/Makefile
	lib/Makefile
	lib/sidlstubs/Cxx/Makefile
	lib/sidlstubs/F77/Makefile
	lib/sidlstubs/F90/Makefile
	lib/sidlstubs/F03/Makefile
	lib/sidlstubs/Java/Makefile
	lib/sidlstubs/Makefile
	regression/Makefile
	regression/bos/runbos.sh
	regression/bos/Makefile
	regression/args/Makefile
	regression/args/libC/Makefile
	regression/args/libCxx/Makefile
	regression/args/libF77/Makefile
	regression/args/libF90/Makefile
	regression/args/libF03/Makefile
	regression/args/libJava/Makefile
	regression/args/libPython/Makefile
	regression/args/runC/Makefile
	regression/args/runCxx/Makefile
	regression/args/runF77/Makefile
	regression/args/runF90/Makefile
	regression/args/runF03/Makefile
	regression/args/runJava/Makefile
	regression/args/runPython/Makefile
	regression/args/runPython/Py2Py/Makefile
	regression/args/runSIDL/Makefile
	regression/arrays/Makefile
	regression/arrays/libC/Makefile
	regression/arrays/libCxx/Makefile
	regression/arrays/libF77/Makefile
	regression/arrays/libF90/Makefile
	regression/arrays/libF03/Makefile
	regression/arrays/libJava/Makefile
	regression/arrays/libPython/Makefile
	regression/arrays/runC/Makefile
	regression/arrays/runCxx/Makefile
	regression/arrays/runF77/Makefile
	regression/arrays/runF90/Makefile
	regression/arrays/runF03/Makefile
	regression/arrays/runJava/Makefile
	regression/arrays/runPython/Makefile
	regression/arrays/runPython/Py2Py/Makefile
	regression/arrays/runSIDL/Makefile
	regression/contracts/Makefile
	regression/contracts/libC/Makefile
	regression/contracts/libCxx/Makefile
	regression/contracts/libF77/Makefile
	regression/contracts/libF90/Makefile
	regression/contracts/libF03/Makefile
	regression/contracts/libJava/Makefile
	regression/contracts/libPython/Makefile
	regression/contracts/runC/Makefile
	regression/contracts/runCxx/Makefile
	regression/contracts/runF77/Makefile
	regression/contracts/runF90/Makefile
	regression/contracts/runF03/Makefile
	regression/contracts/runJava/Makefile
	regression/contracts/runSIDL/Makefile
	regression/contracts/runPython/Makefile
	regression/contracts/runPython/Py2Py/Makefile
	regression/construct/Makefile
	regression/enums/Makefile
	regression/enums/libC/Makefile
	regression/enums/libCxx/Makefile
	regression/enums/libF77/Makefile
	regression/enums/libF90/Makefile
	regression/enums/libF03/Makefile
	regression/enums/libJava/Makefile
	regression/enums/libPython/Makefile
	regression/enums/runC/Makefile
	regression/enums/runCxx/Makefile
	regression/enums/runF77/Makefile
	regression/enums/runF90/Makefile
	regression/enums/runF03/Makefile
	regression/enums/runJava/Makefile
	regression/enums/runPython/Makefile
	regression/enums/runPython/Py2Py/Makefile
	regression/enums/runSIDL/Makefile
	regression/exceptions/Makefile
	regression/exceptions/libC/Makefile
	regression/exceptions/libCxx/Makefile
	regression/exceptions/libF77/Makefile
	regression/exceptions/libF90/Makefile
	regression/exceptions/libF03/Makefile
	regression/exceptions/libJava/Makefile
	regression/exceptions/libPython/Makefile
	regression/exceptions/runC/Makefile
	regression/exceptions/runCxx/Makefile
	regression/exceptions/runF77/Makefile
	regression/exceptions/runF90/Makefile
	regression/exceptions/runF03/Makefile
	regression/exceptions/runJava/Makefile
	regression/exceptions/runPython/Makefile
	regression/exceptions/runPython/Py2Py/Makefile
	regression/exceptions/runSIDL/Makefile
	regression/hooks/Makefile
	regression/hooks/libC/Makefile
	regression/hooks/libCxx/Makefile
	regression/hooks/libF77/Makefile
	regression/hooks/libF90/Makefile
	regression/hooks/libF03/Makefile
	regression/hooks/libJava/Makefile
	regression/hooks/libPython/Makefile
	regression/hooks/runC/Makefile
	regression/hooks/runCxx/Makefile
	regression/hooks/runF77/Makefile
	regression/hooks/runF90/Makefile
	regression/hooks/runF03/Makefile
	regression/hooks/runJava/Makefile
	regression/hooks/runPython/Makefile
	regression/hooks/runPython/Py2Py/Makefile
	regression/hooks/runSIDL/Makefile
	regression/inherit/Makefile
	regression/inherit/libC/Makefile
	regression/inherit/libCxx/Makefile
	regression/inherit/libF77/Makefile
	regression/inherit/libF90/Makefile
	regression/inherit/libF03/Makefile
	regression/inherit/libJava/Makefile
	regression/inherit/libPython/Makefile
	regression/inherit/runC/Makefile
	regression/inherit/runCxx/Makefile
	regression/inherit/runF77/Makefile
	regression/inherit/runF90/Makefile
	regression/inherit/runF03/Makefile
	regression/inherit/runJava/Makefile
	regression/inherit/runPython/Makefile
	regression/inherit/runPython/Py2Py/Makefile
	regression/inherit/runSIDL/Makefile
	regression/objarg/Makefile
	regression/objarg/libC/Makefile
	regression/objarg/libCxx/Makefile
	regression/objarg/libF90/Makefile
	regression/objarg/libF03/Makefile
	regression/objarg/libJava/Makefile
	regression/objarg/libPython/Makefile
	regression/objarg/runC/Makefile
	regression/objarg/runCxx/Makefile
	regression/objarg/runF03/Makefile
	regression/objarg/runJava/Makefile
	regression/objarg/runPython/Makefile
	regression/objarg/runPython/Py2Py/Makefile
	regression/objarg/runSIDL/Makefile
	regression/ordering/Makefile
	regression/ordering/libC/Makefile
	regression/ordering/libCxx/Makefile
	regression/ordering/libF77/Makefile
	regression/ordering/libF90/Makefile
	regression/ordering/libF03/Makefile
	regression/ordering/libJava/Makefile
	regression/ordering/libPython/Makefile
	regression/ordering/runC/Makefile
	regression/ordering/runCxx/Makefile
	regression/ordering/runJava/Makefile
	regression/ordering/runPython/Makefile
	regression/ordering/runPython/Py2Py/Makefile
	regression/ordering/runSIDL/Makefile
	regression/output/Makefile
	regression/output/libC/Makefile
	regression/overload/Makefile
	regression/overload/libC/Makefile
	regression/overload/libCxx/Makefile
	regression/overload/libF77/Makefile
	regression/overload/libF90/Makefile
	regression/overload/libF03/Makefile
	regression/overload/libJava/Makefile
	regression/overload/libPython/Makefile
	regression/overload/runC/Makefile
	regression/overload/runCxx/Makefile
	regression/overload/runF77/Makefile
	regression/overload/runF90/Makefile
	regression/overload/runF03/Makefile
	regression/overload/runJava/Makefile
	regression/overload/runPython/Makefile
	regression/overload/runPython/Py2Py/Makefile
	regression/overload/runSIDL/Makefile
	regression/sorting/Makefile
	regression/sorting/libC/Makefile
	regression/sorting/libCxx/Makefile
	regression/sorting/libF77/Makefile
	regression/sorting/libF90/Makefile
	regression/sorting/libF03/Makefile
	regression/sorting/libJava/Makefile
	regression/sorting/libPython/Makefile
	regression/sorting/runC/Makefile
	regression/sorting/runCxx/Makefile
	regression/sorting/runF77/Makefile
	regression/sorting/runF90/Makefile
	regression/sorting/runF03/Makefile
	regression/sorting/runJava/Makefile
	regression/sorting/runPython/Makefile
	regression/sorting/runPython/Py2Py/Makefile
	regression/sorting/runSIDL/Makefile
	regression/strings/Makefile
	regression/strings/libC/Makefile
	regression/strings/libCxx/Makefile
	regression/strings/libF77/Makefile
	regression/strings/libF90/Makefile
	regression/strings/libF03/Makefile
	regression/strings/libJava/Makefile
	regression/strings/libPython/Makefile
	regression/strings/runC/Makefile
	regression/strings/runCxx/Makefile
	regression/strings/runF77/Makefile
	regression/strings/runF90/Makefile
	regression/strings/runF03/Makefile
	regression/strings/runJava/Makefile
	regression/strings/runPython/Makefile
	regression/strings/runPython/Py2Py/Makefile
	regression/strings/runSIDL/Makefile
	regression/struct/Makefile
	regression/struct/libC/Makefile
	regression/struct/libCxx/Makefile
	regression/struct/libF77/Makefile
	regression/struct/libF90/Makefile
	regression/struct/libF03/Makefile
	regression/struct/libPython/Makefile
	regression/struct/libJava/Makefile
	regression/struct/runC/Makefile
	regression/struct/runCxx/Makefile
	regression/struct/runF77/Makefile
	regression/struct/runF90/Makefile
	regression/struct/runF03/Makefile
	regression/struct/runPython/Makefile
	regression/struct/runPython/Py2Py/Makefile
	regression/struct/runJava/Makefile
	regression/struct/runSIDL/Makefile
	regression/wrapper/Makefile
	regression/wrapper/libC/Makefile
	regression/wrapper/libCxx/Makefile
	regression/wrapper/libF77/Makefile
	regression/wrapper/libF90/Makefile
	regression/wrapper/libF03/Makefile
	regression/wrapper/libJava/Makefile
	regression/wrapper/libPython/Makefile
	regression/wrapper/runC/Makefile
	regression/wrapper/runCxx/Makefile
	regression/wrapper/runF77/Makefile
	regression/wrapper/runF90/Makefile
	regression/wrapper/runF03/Makefile
	regression/wrapper/runJava/Makefile
	regression/wrapper/runPython/Makefile
	regression/wrapper/runPython/Py2Py/Makefile
	regression/wrapper/runSIDL/Makefile
	share/Makefile
	share/config/Makefile
])
AC_OUTPUT
#	examples/rmi/Makefile\

