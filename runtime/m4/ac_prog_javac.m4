

dnl *** Downloaded from http://gnu.wwc.edu/software/ac-archive/Java_Support/ac_prog_javac.m4***
dnl @synopsis AC_PROG_JAVAC
dnl
dnl AC_PROG_JAVAC tests an existing Java compiler. It uses the environment
dnl variable JAVAC then tests in sequence various common Java compilers. For
dnl political reasons, it starts with the free ones.
dnl
dnl If you want to force a specific compiler:
dnl
dnl - at the configure.in level, set JAVAC=yourcompiler before calling
dnl AC_PROG_JAVAC
dnl
dnl - at the configure level, setenv JAVAC
dnl
dnl You can use the JAVAC variable in your Makefile.in, with @JAVAC@.
dnl
dnl *Warning*: its success or failure can depend on a proper setting of the
dnl CLASSPATH env. variable.
dnl
dnl TODO: allow to exclude compilers (rationale: most Java programs cannot compile
dnl with some compilers like guavac).
dnl
dnl Note: This is part of the set of autoconf M4 macros for Java programs.
dnl It is VERY IMPORTANT that you download the whole set, some
dnl macros depend on other. Unfortunately, the autoconf archive does not
dnl support the concept of set of macros, so I had to break it for
dnl submission.
dnl The general documentation, as well as the sample configure.in, is
dnl included in the AC_PROG_JAVA macro.
dnl
dnl @author Stephane Bortzmeyer <bortzmeyer@pasteur.fr>
dnl @version $Id: ac_prog_javac.m4 7397 2011-12-02 16:54:15Z adrian $
dnl
AC_DEFUN([AC_PROG_JAVAC],[
if test "x$JAVAPREFIX" = x; then
  if test "x$JAVAC" = x; then
    AC_PATH_PROGS(JAVAC, javac guavac jikes gcj ecj)
  fi
else
  if test "x$JAVAC" = x; then
    AC_PATH_PROGS(JAVAC, javac guavac jikes gcj ecj,,
	          "${JAVAPREFIX}/bin:${JAVAPREFIX}:${PATH}")
  fi
fi
if test "x$JAVAC" = x; then
  AC_MSG_ERROR([no acceptable Java compiler found in \$PATH])
fi

GC_JOPTS=
AC_CACHE_CHECK([if $JAVAC is a version of gcj], ac_cv_prog_javac_is_gcj, [
if $JAVAC --version 2>&1 | grep gcj >&AS_MESSAGE_LOG_FD ; then
  ac_cv_prog_javac_is_gcj=yes;
  JAVAC="$JAVAC $GCJ_OPTS";
else
  ac_cv_prog_javac_is_gcj=no;
fi
])
AC_SUBST(JAVAC_IS_GCJ, $ac_cv_prog_javac_is_gcj)

AC_PROG_JAVAC_WORKS
AC_PROVIDE([$0])dnl
])


