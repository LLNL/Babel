# # Process this file with autoconf to produce a configure script.

# Table of contents:
#  Variable initialization
#  Basic startup stuff
#  Additional configure options.
#  Critical applications
#  C Compiler
#     a.  Libraries (existence)
#     b.  Header Files
#     c.  Typedefs, Structs, Compiler Characteristics
#     d.  Specific Library Functions.
#  F77 Compiler
#     a. Libraries (existence)
#  C++ Compiler
#     a. Libraries (existence)
#     b. Header Files
#  Java Support
#  Python Support
#  F90 Compiler
#     a. Libraries (existence)
#  Libtool Options
#  Cygwin conditional Makefile Support
#  Shared/Dynamic Library Details
#  babel_internal.h headers and footers
#  File Generation

############################################################
#
# Variable initialization
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
# Basic startup stuff
#
AC_INIT(babel-runtime, 2.0.1, babel-bugs@cca-forum.org)
CCA_DIAGNOSE_INIT(toolcheck.log)
AC_CONFIG_SRCDIR([sidl/sidlArray.h])
AC_CONFIG_AUX_DIR([config])
AC_CONFIG_MACRO_DIR([m4])
AC_CANONICAL_TARGET

AM_INIT_AUTOMAKE([silent-rules foreign no-dependencies no-installinfo no-installman no-texinfo.tex 1.11 dist-bzip2 dist-xz])
dnl do `make V=1' or `./configure --disable-silent-rules' for a verbose build
AM_SILENT_RULES([yes])
dnl To regenerate configure and .in files, configure with --enable-maintainer-mode
AM_MAINTAINER_MODE([enable])

AC_CONFIG_HEADERS([sidl/babel_internal.h])
AC_DEFINE([BABEL_VERSION_HEX],[0x02000000],
	[Hexified form of release number: 2 hex digits each for major, minor, bugfix, and alpha/beta/candidate/distributed ])

LLNL_CHECK_AUTOMAKE([1.11.1])
LLNL_CHECK_AUTOCONF([2.68])


############################################################
#
# Additional configure options. (all "yes" by default)
#
LLNL_AUTO_DISABLE
LLNL_CROSS_COMPILING

############################################################
# This anticipates the definition of $ECHO. We need this early on,
# but LT_INIT cannot be run untial after the compiler checks.
_LT_PROG_ECHO_BACKSLASH

AM_CONDITIONAL(BABEL_RUNTIME_ONLY, ! ((grep UCRL-CODE-2002-054 $srcdir/../COPYRIGHT > /dev/null 2>&1) && (test -e ../bin/babel) ))

CCA_DIAGNOSE_BEGIN([CC])
AC_PROG_CC
CCA_DIAGNOSE_END([CC])
AC_CHECK_HEADERS([inttypes.h sys/types.h])

LLNL_RMI_ENABLE

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
AM_CONDITIONAL(BUILD_LIBPARSIFAL, test "X$llnl_build_libparsifal" = "Xlocal" -o "X$llnl_build_libparsifal" = "Xlocal_babel" )


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
# Critical applications.
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
# Java Support
#
CCA_DIAGNOSE_BEGIN([JAVAC])
LLNL_CONFIRM_BABEL_JAVA_SUPPORT
CCA_DIAGNOSE_END([JAVAC])

############################################################
#
# Libtool Options
#
CCA_DIAGNOSE_BEGIN([LIBTOOL])
LT_PREREQ([2.4])
LT_PATH_LD
LT_CONFIG_LTDL_DIR([libltdl])
LT_INIT([dlopen])
LTDL_INIT([convenience])
AC_SUBST(LIBTOOL_DEPS)
#AC_LIBTOOL_WIN32_DLL
#AC_LIB_LTDL
CCA_DIAGNOSE_END([LIBTOOL])

eval sidllibprefix="$libdir"
case "$sidllibprefix" in
NONE*)
  sidllibprefix="${prefix}/lib"
  ;;
esac
AC_DEFINE_UNQUOTED(SIDLLIBPREFIX,"$sidllibprefix",[Directory where libsidl.scl and libsidlx.scl should be located.])

############################################################
#
# Cygwin conditional Makefile Support.
#
case $host_os in
  *cygwin* ) CYGWIN=yes;;
         * ) CYGWIN=no;;
esac
AM_CONDITIONAL(SUPPORT_CYGWIN, test "X$CYGWIN" = "Xyes")

############################################################
#
# Shared/Dynamic Library Details.
#
if test "X$llnl_found_libparsifal" = "Xyes" -o "X$XML2_CONFIG" != "Xno" ; then
  llnl_have_xml_parser=yes
else
  llnl_have_xml_parser=no
fi
AM_CONDITIONAL(SUPPORT_STATIC, test "X$enable_static" = "Xyes")
AM_CONDITIONAL(SUPPORT_SHARED, test "X$enable_shared" = "Xyes" -a "X$llnl_have_xml_parser" = "Xyes")
if test "X$enable_shared" != "Xyes" -o "X$llnl_have_xml_parser" = "Xno"; then
  AC_DEFINE(SIDL_STATIC_LIBRARY, 1, [Define SIDL_STATIC_LIBRARY to force static loading of libraries])
fi
if test "X$enable_static" != "Xyes" -a "X$llnl_have_xml_parser" = "Xyes"; then
  AC_DEFINE(SIDL_DYNAMIC_LIBRARY, 1, [Define SIDL_DYNAMIC_LIBRARY to force dynamic loading of libraries])
fi

############################################################
#
# Experimental sidl extensions (simhandle)
#
if test -z "$with_sidlx"; then
  with_sidlx=yes
fi
AC_ARG_WITH(sidlx,   [  --without-sidlx         disable simple RMI protocol (aka libsidlx.{a,so})])
AM_CONDITIONAL(WITH_SIDLX, test "X$with_sidlx" != "Xno" )

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
              [(debugging) enable the dmalloc memory debugger for the sidl runtime])],
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
# babel_internal.h headers and footers
#
AH_TOP([
#ifndef included_babel_internal_h
#define included_babel_internal_h
])

AH_BOTTOM([
/*
 * Set flags for dynamic or static loading of implementations in Babel stubs.
 * One and only one of SIDL_STATIC_LIBRARY and SIDL_DYNAMIC_LIBRARY may be set.
 * If neither is set, then SIDL_DYNAMIC_LIBRARY is chosen as the default if
 * PIC is set and SIDL_STATIC_LIBRARY is chosen otherwise.  This behavior is
 * consistent with GNU libtool.  In general, we want to generate dynamic
 * loading with shared libraries (indicated by -DPIC in libtool) and static
 * loading with static libraries.
 */
#if (!defined(SIDL_STATIC_LIBRARY) && !defined(SIDL_DYNAMIC_LIBRARY))
#ifdef PIC
#define SIDL_DYNAMIC_LIBRARY
#else
#define SIDL_STATIC_LIBRARY
#endif
#endif

#if (defined(SIDL_STATIC_LIBRARY) && defined(SIDL_DYNAMIC_LIBRARY))
#error Cannot define both SIDL_STATIC_LIBRARY and SIDL_DYNAMIC_LIBRARY
#endif
#if (!defined(SIDL_STATIC_LIBRARY) && !defined(SIDL_DYNAMIC_LIBRARY))
#error Must define one of SIDL_STATIC_LIBRARY or SIDL_DYNAMIC_LIBRARY
#endif

#define UCXX
#define UCXX_LOCAL

/*
 * The USE_DL_IMPORT flag is required for proper Python linking under CYGWIN.
 */
#if defined(__CYGWIN__) && !defined(USE_DL_IMPORT)
#define USE_DL_IMPORT
#endif

#endif
])

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
# File Generation
#
AC_CONFIG_FILES([Makefile
                 m4/Makefile
		 libchasmlite/Makefile
		 libchasmlite/chasmlite_config.h
		 bin/Makefile
		 config/Makefile
                 java/Makefile
                 sidl/Makefile
		 sidlx/Makefile])

if test "X$llnl_build_libparsifal" = "Xlocal" -o "X$llnl_build_libparsifal" = "Xlocal_babel" ; then
  AC_CONFIG_SUBDIRS([libparsifal])
fi
AC_OUTPUT
echo "$msgs
" >&5
echo "$msgs
"
echo "When reporting issues to $PACKAGE_BUGREPORT, please include the"
echo "output from babel-config --version-full."
