#! /usr/bin/env python
#
# Python script to generate boiler plate runJava2x.sh scripts
#
from os import rename, chdir, getcwd
from os.path import walk

humanlang = {
    'Cxx' : 'C++',
    'C' : 'C',
    'F77' : 'F77',
    'F90' : 'F90',
    'F03' : 'F03'
    }

generalscript = """\
#! /bin/bash
## File:        runJava2%(lang)s.in
## Package:     Babel regression tests
## Revision:    $Revision$
## Modified:    $Date$
## Description: script to run Java calling %(hlang)s regression test
## Generated automatically by babel/regression/genrunjava.py
#
## Copyright (c) 2000-2004, Lawrence Livermore National Security, LLC
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

# limit CPU time to 5 minutes
ulimit -t 300
prefix="@prefix@"
exec_prefix="@exec_prefix@"
bindir="@bindir@"
libdir="@libdir@"
JAVA="@JAVA@"
VERSION="@VERSION@"

case "`uname`" in
  CYGWIN*) cygwin=true;;
  *) cygwin=false;;
esac

if test -f stamp-installcheck; then
  CLASSPATH="${libdir}/sidl-${VERSION}.jar:${libdir}/sidlstub_${VERSION}.jar:."
  SIDL_DLL_PATH="%(extra)slibClient.scl;../lib%(lang)s/libImpl.scl;${libdir}/libsidl.scl;${libdir}/libsidlx.scl;../../output/libC/libOutput.scl;${libdir}/libsidlstub_java.scl"
  if $cygwin; then
    JAVA_LIBRARY_PATH="${bindir}"
  else
    JAVA_LIBRARY_PATH="${libdir}"
  fi
  @SHARED_LIB_VAR@="${libdir}:${@SHARED_LIB_VAR@}"
else
  CLASSPATH=../../../lib/sidl-${VERSION}.jar:../../../lib/sidlstubs/Java/sidlstub_${VERSION}.jar:.
  SIDL_DLL_PATH="%(extra)slibClient.scl;../lib%(lang)s/libImpl.scl;../../../runtime/sidl/libsidl.scl;../../../runtime/sidlx/libsidlx.scl;../../output/libC/libOutput.scl;../../../lib/sidlstubs/Java/libsidlstub_java.scl"
  JAVA_LIBRARY_PATH=../../../runtime/sidl/.libs
  @SHARED_LIB_VAR@="../../../runtime/sidl/.libs:../../../runtime/libchasmlite/.libs:../../../runtime/libparsifal/src/.libs:../../../runtime/sidlx/.libs:../../../lib/sidlstubs/%(lang)s/.libs:${@SHARED_LIB_VAR@}"
fi
export @SHARED_LIB_VAR@
SIDL_LIBRARY_NAME=sidl

if $cygwin; then
  CLASSPATH=`cygpath --path --windows "${CLASSPATH}"`
  JAVA_LIBRARY_PATH=`cygpath --path --windows "${JAVA_LIBRARY_PATH}"`
  SIDL_LIBRARY_NAME=cygsidl-`echo ${VERSION} | tr . -`
fi
export SIDL_DLL_PATH;
export CLASSPATH

${JAVA} \\
  -Djava.library.path="${JAVA_LIBRARY_PATH}" \\
  -Dsidl.library.name="${SIDL_LIBRARY_NAME}" \\
  %(testname)s $* &
javapid=$!
trap "echo 'Test timed out' ; kill -9 $javapid" 14
(sleep 90 && kill -s 14 $$ ) <&- >&- 2>&- &
timerpid=$!
wait $javapid
result=$?
kill $timerpid
exit $result
"""

def printScript(out, lang, test):
    if test == "WrapTest":
        extralib="./libImpl.scl;"
    else:
        extralib=""
    print >>out, generalscript % { 'lang' : lang,
                                   'hlang' : humanlang[lang],
                                   'testname' : test,
                                   'extra' : extralib }
javascript="""\
#! /bin/bash
## File:        runJava2Java.sh.in
## Package:     Babel regression tests
## Revision:    $Revision$
## Modified:    $Date$
## Description: script to run Java calling Java regression test
## Generated automatically by babel/regression/genrunjava.py
##
## Copyright (c) 2000-2004, Lawrence Livermore National Security, LLC
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

#limit CPU time to 5 minutes
ulimit -t 300
prefix="@prefix@"
exec_prefix="@exec_prefix@"
bindir="@bindir@"
libdir="@libdir@"
JAVA="@JAVA@"
VERSION="@VERSION@"

case "`uname`" in
  CYGWIN*) cygwin=true;;
  *) cygwin=false;;
esac

if test -f stamp-installcheck; then
  CLASSPATH="${libdir}/sidl-${VERSION}.jar:${libdir}/sidlstub_${VERSION}.jar:../libJava:."
  SIDL_DLL_PATH="%(extra)s../libJava/libImpl.scl;${libdir}/libsidl.scl;${libdir}/libsidlx.scl;../../output/libC/libOutput.scl;${libdir}/libsidlstub_java.scl"
  if $cygwin; then
    JAVA_LIBRARY_PATH="${bindir}"
  else
    JAVA_LIBRARY_PATH="${libdir}"
  fi
  @SHARED_LIB_VAR@="${libdir}:${@SHARED_LIB_VAR@}"
else
  CLASSPATH=../../../lib/sidl-${VERSION}.jar:../../../lib/sidlstubs/Java/sidlstub_${VERSION}.jar:../libJava:.
  SIDL_DLL_PATH="%(extra)s../libJava/libImpl.scl;../../../runtime/sidl/libsidl.scl;../../../runtime/sidlx/libsidlx.scl;../../output/libC/libOutput.scl;../../../lib/sidlstubs/Java/libsidlstub_java.scl"
  JAVA_LIBRARY_PATH=../../../runtime/sidl/.libs
  @SHARED_LIB_VAR@="../../../runtime/sidl/.libs:../../../runtime/libchasmlite/.libs:../../../runtime/libparsifal/src/.libs:../../../runtime/sidlx/.libs:${@SHARED_LIB_VAR@}"
fi
export @SHARED_LIB_VAR@
SIDL_LIBRARY_NAME=sidl

if $cygwin; then
  CLASSPATH=`cygpath --path --windows "${CLASSPATH}"`
  JAVA_LIBRARY_PATH=`cygpath --path --windows "${JAVA_LIBRARY_PATH}"`
  SIDL_LIBRARY_NAME=cygsidl-`echo ${VERSION} | tr . -`
fi
export SIDL_DLL_PATH;
export CLASSPATH

${JAVA} \\
  -Djava.library.path="${JAVA_LIBRARY_PATH}" \\
  -Dsidl.library.name="${SIDL_LIBRARY_NAME}" \\
  %(testname)s $* &
javapid=$!
trap "echo 'Test timed out' ; kill -9 $javapid" 14
(sleep 90 && kill -s 14 $$ ) <&- >&- 2>&- &
timerpid=$!
wait $javapid
result=$?
kill $timerpid
exit $result
"""
def printJava(out, lang, test):
    if test == "WrapTest":
        extralib="./libClient.scl;./libImpl.scl;"
    else:
        extralib=""
    print >>out, javascript % { 'testname' : test, 'extra' : extralib }

pythonscript="""\
#! /bin/bash
## File:        runJava2Py.in
## Package:     Babel regression tests
## Revision:    $Revision$
## Modified:    $Date$
## Description: script to run Java calling Python regression test
## Generated automatically by babel/regression/genrunjava.py
##
## Copyright (c) 2000-2004, Lawrence Livermore National Security, LLC
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

# limit CPU time to 5 minutes
ulimit -t 300
prefix="@prefix@"
exec_prefix="@exec_prefix@"
bindir="@bindir@"
libdir="@libdir@"
python_version="@PYTHON_VERSION@"
VERSION="@VERSION@"
JAVA="@JAVA@"
PYTHON="@PYTHON@"

case "`uname`" in
  CYGWIN*) cygwin=true;;
  *) cygwin=false;;
esac

if test -f stamp-installcheck; then
  RUNTIME_PYTHON=`$PYTHON -c "from distutils.sysconfig import get_python_lib; print get_python_lib(prefix='$exec_prefix',plat_specific=1)"`
  if test "X$PYTHONPATH" = "X"; then
    PYTHONPATH="../libPython:${RUNTIME_PYTHON}"
  else
    PYTHONPATH="../libPython:${RUNTIME_PYTHON}:$PYTHONPATH"
  fi
  CLASSPATH="${libdir}/sidl-${VERSION}.jar:${libdir}/sidlstub_${VERSION}.jar:."
  SIDL_DLL_PATH="%(extra)slibClient.scl;../libPython/libImpl.scl;../libPython/libImplPy.scl;${libdir}/libsidl.scl;${libdir}/libsidlx.scl;../../output/libC/libOutput.scl;${libdir}/libsidlstub_java.scl"
  if $cygwin; then
    JAVA_LIBRARY_PATH="${bindir}"
  else
    JAVA_LIBRARY_PATH="${libdir}"
  fi
  @SHARED_LIB_VAR@="${libdir}:${@SHARED_LIB_VAR@}"
else
  if test "X$PYTHONPATH" = "X"; then
    PYTHONPATH="../libPython:../../../runtime/python"
  else
    PYTHONPATH="../libPython:../../../runtime/python:$PYTHONPATH"
  fi
  CLASSPATH=../../../lib/sidl-${VERSION}.jar:../../../lib/sidlstubs/Java/sidlstub_${VERSION}.jar:.
  SIDL_DLL_PATH="%(extra)slibClient.scl;../libPython/libImpl.scl;../libPython/libImplPy.scl;../../../runtime/sidl/libsidl.scl;../../../runtime/sidlx/libsidlx.scl;../../output/libC/libOutput.scl;../../../lib/sidlstubs/Java/libsidlstub_java.scl"
  JAVA_LIBRARY_PATH=../../../runtime/sidl/.libs
  @SHARED_LIB_VAR@="../../../runtime/sidl/.libs:../../../runtime/libchasmlite/.libs:../../../runtime/libparsifal/src/.libs:../../../runtime/sidlx/.libs:${@SHARED_LIB_VAR@}"
fi
export @SHARED_LIB_VAR@

export PYTHONPATH

SIDL_LIBRARY_NAME=sidl

if $cygwin; then
  CLASSPATH=`cygpath --path --windows "${CLASSPATH}"`
  JAVA_LIBRARY_PATH=`cygpath --path --windows "${JAVA_LIBRARY_PATH}"`
  SIDL_LIBRARY_NAME=cygsidl-`echo ${VERSION} | tr . -`
fi
export SIDL_DLL_PATH;
export CLASSPATH

${JAVA} \\
  -Djava.library.path="${JAVA_LIBRARY_PATH}" \\
  -Dsidl.library.name="${SIDL_LIBRARY_NAME}" \\
  %(testname)s $* &
javapid=$!
trap "echo 'Test timed out' ; kill -9 $javapid" 14
(sleep 90 && kill -s 14 $$ ) <&- >&- 2>&- &
timerpid=$!
wait $javapid
result=$?
kill $timerpid
exit $result
"""

def printPython(out, lang, test):
    if test == "WrapTest":
        extralib="./libImpl.scl;"
    else:
        extralib=""
    print >>out, pythonscript % { 'testname' : test, 'extra' : extralib }

def findJavaTest(names):
    for i in names:
        if i.endswith(".java"):
            return i[:-5]
    return None

def checkDir(arg, dirname, names):
    if (dirname.endswith("runJava")):
        cwd = getcwd()
        try:
            chdir(dirname)
            methods = { 'C' : printScript,
                        'Cxx' : printScript,
                        'F77' : printScript,
                        'F90' : printScript,
                        'F03' : printScript,
                        'Py' : printPython,
                        'Java' : printJava }
            test = findJavaTest(names)
            for i in names:
                if (i.startswith("runJava2") and
                    i.endswith(".sh.in")):
                    lang = i[8:-6]
                    method = methods[lang]
                    if (method <> None):
#                        rename(i, i + ".bak")
                        out = open(i, "w")
                        method(out,lang,test)
                        out.close()
        finally:
            chdir(cwd)

if __name__=='__main__':
    walk('.', checkDir, None)
