
dnl @synopsis LLNL_PROG_PYTHON
dnl
dnl LLNL_PROG_PYTHON tests for an existing Python interpreter. It uses
dnl the environment variable PYTHON and then tries to find python
dnl in standard places.
dnl
dnl @author ?
AC_DEFUN([LLNL_PROG_PYTHON],[
  AC_REQUIRE([AC_EXEEXT])dnl
  AC_CHECK_PROGS(PYTHON, python$EXEEXT)
  if test "x$PYTHON" = x; then
    AC_MSG_WARN([Not building Python support - unable to find Python executable])
  else
    AC_MSG_CHECKING([if $PYTHON is executable])
    if AC_TRY_COMMAND($PYTHON -V) >/dev/null 2>&1; then
      AC_MSG_RESULT([yes])
    else
      AC_MSG_RESULT([no])
      AC_MSG_WARN([Not building Python support - $PYTHON does not run])
      unset PYTHON
    fi
  fi
])

AC_DEFUN([LLNL_WHICH_PYTHON],[
  dnl This messy expression exists because Mac OS X is very sensitive to
  dnl the Python path. If the name is not pointing to the /System/Frameworks...
  dnl type of path, it will get the wrong sys.path leading to other failures.
  dnl Thank you Apple!
  WHICH_PYTHON=`$PYTHON -c 'import sys;import os;name1=sys.exec_prefix+os.path.sep+"bin"+os.path.sep+"python";name2=name1+str(sys.version_info[[0]])+"."+str(sys.version_info[[1]]);print(name1) if (os.path.isfile(name1)) else (name2 if (os.path.isfile(name2)) else sys.executable)'`
  AC_SUBST(WHICH_PYTHON)
  AC_DEFINE_UNQUOTED([PYTHON_EXECUTABLE_NAME],"$WHICH_PYTHON",[The absolute path to the Python executable (especially important for Mac OS X).])
  AC_MSG_NOTICE([Using $WHICH_PYTHON as absolute path to Python.])
])
