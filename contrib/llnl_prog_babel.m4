## llnl_prog_babel.m4 - Prime configure system for Babel.  -*-Autoconf-*-
## Copyright (c) 2007 Lawrence Livermore National Security, LLC.
## Produced at the Lawrence Livermore National Laboratory.
## Written by Gary Kumfert <kumfert@llnl.gov>

dnl @synopsis LLNL_PROG_BABEL
dnl  
dnl Dumps all kinds of settings from babel-config into your environment
dnl Useful for codes that critcially depend on Babel and want to follow
dnl its installation settings.
dnl
dnl If you use this macro, put it as early as possible in your configure.ac.
dnl It should defintely appear before AC_PROG_CPP, AC_PROG_CC, etc.
dnl 

AC_DEFUN([LLNL_PROG_BABEL],
[
  AC_BEFORE([$0],[AC_PROG_CC])
  _LLNL_PROG_BABEL_CONFIG
  if test $BABEL_CONFIG != no; then 
    AC_PATH_PROG([BABEL],[babel],[no],[`$ac_cv_path_BABEL_CONFIG --bindir`:$PATH])
    _LLNL_DUMP_SETTINGS
    _LLNL_BABEL_MPI
  fi;
])


# _LLNL_PROG_BABEL_CONFIG
# -----------------------
AC_DEFUN([_LLNL_PROG_BABEL_CONFIG],
[
  AC_ARG_VAR([BABEL_BINDIR],[Absolute path to babel and babel-config])
  if test "${BABEL_BINDIR+set}" = set; then
    if test -d $BABEL_BINDIR; then 
      llnl_babel_config_path="$BABEL_BINDIR";
    else 
      AC_MSG_WARN([BABEL_BINDIR=$BABEL_BINDIR is not a valid path])
    fi
  fi

  AC_PATH_PROG([BABEL_CONFIG],[babel-config],[no],
	       [$llnl_babel_config_path:$PATH])  
])

# _LLNL_DUMP_SETTINGS
# -----------------------
AC_DEFUN([_LLNL_DUMP_SETTINGS],
[

LLNL_BABEL_SUBST([BABEL_VERSION],[--version])
LLNL_BABEL_SUBST([BABEL_PREFIX],[--prefix])
LLNL_BABEL_SUBST([BABEL_EPREFIX],[--exec-prefix])
LLNL_BABEL_SUBST([BABEL_INCLDIR],[--includedir])
LLNL_BABEL_SUBST([BABEL_BINDIR],[--bindir])
LLNL_BABEL_SUBST([BABEL_LIBDIR],[--libdir])
LLNL_BABEL_SUBST([BABEL_JARDIR],[--jardir])

AC_SUBST([BABEL_LIBTOOL],[$BABEL_BINDIR/babel-libtool])

LLNL_BABEL_SUBST([INCLUDES],[--includes])

export FPP='gcc -E'
AC_SUBST([FPP])
LLNL_BABEL_WHICH([CC])
LLNL_BABEL_SUBST([BABEL_CFLAGS],[--flags-c])
LLNL_BABEL_SUBST([BABEL_CLIBS],[--libs-c])

LLNL_BABEL_WHICH([CXX])
LLNL_BABEL_SUBST([BABEL_CXXFLAGS],[--flags-c++])
LLNL_BABEL_SUBST([BABEL_CXXLIBS],[--libs-c++])

AC_SUBST([BABEL_WITH_F90],[`$BABEL_CONFIG --with-f90`])
LLNL_BABEL_WHICH([FC])
LLNL_BABEL_SUBST([BABEL_FCFLAGS],[--flags-f90])
LLNL_BABEL_SUBST([BABEL_FCLIBS],[--libs-f90])

LLNL_BABEL_WHICH([PYTHON])
LLNL_BABEL_SUBST([PYTHON_VERSION])
LLNL_BABEL_SUBST([PYTHONINC])
if test "x$PYTHONINC" != x; then
  PYTHON_SUBDIR=`basename $PYTHONINC`
fi
AC_SUBST([PYTHON_SUBDIR])
AC_SUBST([BABEL_PYEXTENSION_INCLDIR],[$BABEL_INCLDIR/$PYTHON_SUBDIR/babel])
AC_SUBST([BABEL_PYEXTENSION_LIBDIR],[$BABEL_INCLDIR/$PYTHON_SUBDIR/site-packages])
LLNL_BABEL_SUBST([PYTHON_SHARED_LIBRARY_DIR])

LLNL_BABEL_WHICH([JAVA])
LLNL_BABEL_SUBST([JAVAFLAGS],[--flags-java])
LLNL_BABEL_WHICH([JAVAC])
LLNL_BABEL_SUBST([JAVACFLAGS],[--flags-javac])
LLNL_BABEL_SUBST([JNIINCLUDES],[--includes-jni])
LLNL_BABEL_SUBST([JNI_LDFLAGS])

LLNL_BABEL_WHICH([F77])

])
# end _LLNL_DUMP_SETTINGS

# _LLNL_BABEL_MPI
# ---------------
AC_DEFUN([_LLNL_BABEL_MPI],
[
  AC_MSG_CHECKING([if babel was configured with MPI support])
  llnl_babel_mpi=`$BABEL_CONFIG --query-var=MPI_VENDOR`;
  if test x$llnl_babel_mpi = x; then
    llnl_babel_mpi=no;
  fi 
  AC_MSG_RESULT([$llnl_babel_mpi])
  if test "$llnl_babel_mpi" != no; then 
      LLNL_BABEL_SUBST([MPI_CC])
      LLNL_BABEL_SUBST([MPI_CC_CFLAGS])
      LLNL_BABEL_SUBST([MPI_CC_LDFLAGS])
      LLNL_BABEL_SUBST([MPI_CXX])
      LLNL_BABEL_SUBST([MPI_CXX_CFLAGS])
      LLNL_BABEL_SUBST([MPI_CXX_LDFLAGS])
      LLNL_BABEL_SUBST([MPI_FC])
      LLNL_BABEL_SUBST([MPI_FC_CFLAGS])
      LLNL_BABEL_SUBST([MPI_FC_LDFLAGS])
      LLNL_BABEL_SUBST([MPI_F77])
      LLNL_BABEL_SUBST([MPI_F77_CFLAGS])
      LLNL_BABEL_SUBST([MPI_F77_LDFLAGS])
  fi
])
# end _LLNL_BABEL_MPI

# LLNL_BABEL_QUERY(my-var,[babel-config-var])
# ---------------
AC_DEFUN([LLNL_BABEL_QUERY],
[AC_REQUIRE([_LLNL_PROG_BABEL_CONFIG])
 m4_ifvaln([$2],
           [case "$2" in
            --*) temp=`$BABEL_CONFIG $2`;;
            *) temp=`$BABEL_CONFIG --query-var=$2`;;
            esac],
	   [temp=`$BABEL_CONFIG --query-var=$1`])
 case $temp in
   @*@) AC_MSG_WARN([$1=$temp is suspicious, overridden]); temp= ;;
   *) ;;
 esac
 $1=$temp
])

# LLNL_BABEL_WHICH(my-var,[babel-config-var])
# --------------------------------------------
AC_DEFUN([LLNL_BABEL_WHICH],
[AC_REQUIRE([_LLNL_PROG_BABEL_CONFIG])
 m4_ifvaln([$2],[var=$2],[var=$1])
 rel=`$BABEL_CONFIG --query-var=$var`;
 abs=`$BABEL_CONFIG --which-var=$var`;
 if test $abs -ef `which $rel`; then
   $1=$rel;
 else
   AC_MSG_WARN([Babel's value of $1 not in your path, overriding with absolute path $abs])
   $1=$abs;
 fi;
])

# _LLNL_BABEL_SUBST(my-var,[babel-config-var])
# --------------------------------------------

AC_DEFUN([LLNL_BABEL_SUBST],
[ m4_ifvaln([$2],
	    [LLNL_BABEL_QUERY([$1],[$2])],
	    [LLNL_BABEL_QUERY([$1])])
AC_SUBST([$1])
])
