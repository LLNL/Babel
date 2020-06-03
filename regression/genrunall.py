#! /usr/bin/env python
#
# Python script to generate boiler plate runAll.sh scripts
#

from os import listdir, chdir
from os.path import isdir, isfile
from glob import glob

def printLicense(out):
    print >>out,"""\
## Copyright (c) 2000-2006, Lawrence Livermore National Security, LLC
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

"""
    

def historicalOrder(lang1, lang2):
    order = { 'C' : 0, 'Cxx' : 1, 
              'F77' : 3, 'F90' : 4, 'Python' : 5,
              'Java' : 6, 'F03' : 7 }
    return order[lang1] - order[lang2]

def runLanguage(out, test, srclang, targetlang, dirname, indent=""):
          print >>out, """\
%(indent)secho "TEST_BEGIN %(src)s->%(target)s"
%(indent)secho "COMMENT: %(test)s from %(target)s..."
%(indent)sif test $installcheck = yes; then
%(indent)s  @SHARED_LIB_VAR@="$exec_prefix/lib:${ORIG_LIBPATH}"
%(indent)selse
%(indent)s  @SHARED_LIB_VAR@="../../../lib/sidlstubs/%(dir)s/.libs:${ORIG_LIBPATH}"
%(indent)sfi
%(indent)sexport @SHARED_LIB_VAR@
%(indent)sSIDL_DLL_PATH="../lib%(dir)s/libImpl.scl;../../output/libC/libOutput.scl;../../../runtime/sidlx/libsidlx.scl" ; export SIDL_DLL_PATH
%(indent)s./runAll %(target)s $*
%(dir)sstatus=$?
let "allstatus=allstatus | %(dir)sstatus"
if test $%(dir)sstatus -ne 0 ; then
  echo "TEST_RESULT BROKEN $%(dir)sstatus"
fi
""" % { 'src' : srclang, 'target' : targetlang,
        'test' : test.capitalize(), 'dir' : dirname, 'indent' : indent }

def runFortran(out, test, srclang, targetlang, dirname):
    print >>out, 'if test "X@SUPPORT_FORTRAN' + targetlang[-2:] + '_TRUE@" = "X"; then'
    runLanguage(out, test, srclang, targetlang, dirname, "  ")
    print >>out, """fi

"""

def runPython(out, test, srclang, targetlang, dirname):
    print >>out, """\
if test "X@SERVER_PYTHON_TRUE@" = "X"; then
  echo "TEST_BEGIN %(src)s->Python"
  echo "COMMENT: %(test)s from Python..."

  SIDL_DLL_PATH="../libPython/libImpl.scl;../libPython/libImplPy.scl;../../output/libC/libOutput.scl;../../../runtime/sidlx/libsidlx.scl"
  export SIDL_DLL_PATH
  if test $installcheck = yes; then
    @SHARED_LIB_VAR@="$exec_prefix/lib:${ORIG_LIBPATH}"
    RUNTIME_PYTHON=`$PYTHON -c "from distutils.sysconfig import get_python_lib; print get_python_lib(prefix='$exec_prefix',plat_specific=1) + ':' + get_python_lib(prefix='$exec_prefix')"`
  else
    @SHARED_LIB_VAR@="../../../runtime/sidl/.libs:../../../runtime/libchasmlite/.libs:../../../runtime/libparsifal/src/.libs:../../../runtime/libchasmlite/.libs:${ORIG_LIBPATH}"
    RUNTIME_PYTHON="../../../runtime/python"
  fi
  export @SHARED_LIB_VAR@

  if test "X$PYTHONPATH" = "X"; then
    PYTHONPATH="../libPython:${RUNTIME_PYTHON}"
  else
    PYTHONPATH="../libPython:${RUNTIME_PYTHON}:$PYTHONPATH"
  fi
  export PYTHONPATH

  ./runAll Python $*
  pythonstatus=$?
  let "allstatus=allstatus | pythonstatus"
  if test $pythonstatus -ne 0 ; then
    echo "TEST_RESULT BROKEN $pythonstatus"
  fi
fi
""" % { 'src' : srclang, 'test' : test.capitalize() }

def runJava(out, test, srclang, targetlang, dirname):
    if test.lower() == 'wrapper':
        extralib = ';../libJava/libClient.scl'
    else:
        extralib = ''
    print >>out, """\
if test "X@SUPPORT_JAVA_TRUE@" = "X"; then
  echo "TEST_BEGIN %(src)s->Java"
  echo "COMMENT: %(test)s from Java..."
  if test $installcheck = yes; then
    @SHARED_LIB_VAR@="${libjvm_dir}:${libjava_dir}:${exec_prefix}/lib:${ORIG_LIBPATH}"
    CLASSPATH="../libJava:${exec_prefix}/lib/sidl-${VERSION}.jar:${exec_prefix}/lib/sidlstub_${VERSION}.jar:."
    SIDL_DLL_PATH="../libJava/libImpl.scl%(extra)s;${exec_prefix}/lib/libsidlstub_java.scl;${exec_prefix}/lib/libsidl.scl;../../../runtime/sidlx/libsidlx.scl;../../output/libC/libOutput.scl"
  else
    @SHARED_LIB_VAR@="${libjvm_dir}:${libjava_dir}:../../../runtime/sidl/.libs:../../../runtime/libchasmlite/.libs:../../../runtime/libparsifal/src/.libs:../../../lib/sidlstubs/Java/.libs:${ORIG_LIBPATH}"
    SIDL_DLL_PATH="../libJava/libImpl.scl%(extra)s;../../../lib/sidlstubs/Java/libsidlstub_java.scl;../../../runtime/sidl/libsidl.scl;../../../runtime/sidlx/libsidlx.scl;../../output/libC/libOutput.scl"
    CLASSPATH="../libJava:../../../lib/sidl-${VERSION}.jar:../../../lib/sidlstubs/Java/sidlstub_${VERSION}.jar:." 
  fi
  export @SHARED_LIB_VAR@
  export SIDL_DLL_PATH
  export CLASSPATH
  ./runAll Java $*
  javastatus=$?
  let "allstatus=allstatus | javastatus"
  if test $javastatus -ne 0 ; then
    echo "TEST_RESULT BROKEN $javastatus"
  fi
fi
""" % { 'src' : srclang, 'test' :test.capitalize() , 'extra' : extralib }
    

def generateRunAll(out, test, srclang, targetlangs):
    print >>out, """\
#!/bin/bash
## File:        runAll.sh
## Package:     Babel regression driver
## Revision:    $Revision$
## Modified:    $Date$
## Description: regression driver that dynamically loads implementations
##"""
    printLicense(out)
    print >>out, """\
#
# If running the regression tests against the installed version under CYGWIN,
# we need to add the DLL location to the default path.
#

# limit CPU time to 10 minutes
ulimit -t 600

prefix=@prefix@
exec_prefix=@exec_prefix@
python_version=@PYTHON_VERSION@
VERSION="@VERSION@"
PYTHON="@PYTHON@"
libjvm_dir=@LIBJVM_DIR@
libjava_dir=@LIBJAVA_DIR@
allstatus=0

if test -f stamp-installcheck; then
  installcheck=yes
  case "`uname`" in
    CYGWIN*)
      PATH="$PATH:${bindir}"; export PATH
    ;;
  esac
else
  installcheck=no
fi
ORIG_LIBPATH="${@SHARED_LIB_VAR@}"

#
# Run the tests against the various back-end implementations.
#

"""
    humanLangNames = { 'C' : 'C', 'F77' : 'F77', 'F90' : 'F90',
                       'Cxx' : 'C++', 'Java' : 'Java',
                       'F03' : 'F03', 'Python' : 'Python' }
    methods = { 'C' : runLanguage, 'Cxx' : runLanguage,
                'F77' : runFortran, 'F90' : runFortran,
		'F03' : runFortran, 'Java' : runJava,
                'Python' : runPython }
    langs = targetlangs.keys()
    langs.sort(historicalOrder)
    for target in langs:
        methods[target](out, test, humanLangNames[srclang],
                        humanLangNames[target], target)
    print >>out, "exit $allstatus"

def printPythonHeader(out, targetlang):
    print >>out, """\
#!/bin/bash
# File:        runPy2%(target)s
# Description: Set environment variables and run python
# Revision:    $Revision$
# Date:        $Date$
#
""" % { 'target' : targetlang }
    printLicense(out)
    print >>out, """\
# limit CPU time to 5 minutes
ulimit -t 300
PYTHON=PYTHONEXEC
if test "X$PYTHONPATH" = "X"; then
  PYTHONPATH=PYTHONDIR
else
  PYTHONPATH=PYTHONDIR:${PYTHONPATH}
fi
case "`uname`" in
  CYGWIN*) cygwin=true;;
  *) cygwin=false;;
esac
"""

def printNormalPython(out, targetlang, testprog):
    if "wraptest" == testprog:
        extrapy=';libImpl.scl;libImplPy.scl'
    else:
        extrapy=''
    print >>out, """\
SIDL_DLL_PATH="../lib%(target)s/libImpl.scl;../../output/libC/libOutput.scl%(extrapy)s;../../../runtime/sidl/libsidl.scl;../../../runtime/sidlx/libsidlx.scl"
SHARED_LIB_VAR="PYTHONLIB:SIDLSTUBSLIB:SIDLSTUBSLIB/%(target)s/.libs:${SHARED_LIB_VAR}"
export PYTHONPATH SIDL_DLL_PATH SHARED_LIB_VAR

if $cygwin; then
  PATH=$PATH:PYTHONLIB:PYTHONLIB/../bin
  export PATH
fi

$PYTHON %(program)s $*
""" % { 'program' : testprog, 'target' : targetlang, 'extrapy' : extrapy }

def generateRunPy2Java(out, targetlang, testprog):
    if "wraptest" == testprog:
        extralib=';../lib%(target)s/libClient.scl' % { 'target' : targetlang }
        extrapy=';libImpl.scl;libImplPy.scl'
    else:
        extralib=''
        extrapy=''
    print >>out, """\
libjvm_dir=LIBJVM_DIR
libjava_dir=LIBJAVA_DIR
version=VERSION
exec_prefix=EXEC_PREFIX

if test -f stamp-installcheck; then
  CLASSPATH="../libJava:${exec_prefix}/lib/sidl-${version}.jar:${exec_prefix}/lib/sidlstub_${version}.jar:."
  SIDL_DLL_PATH="../lib%(target)s/libImpl.scl%(extra)s;../../output/libC/libOutput.scl;../../../runtime/sidl/libsidl.scl;../../../runtime/sidlx/libsidlx.scl;${exec_prefix}/lib/libsidlstub_java.scl%(extrapy)s"
  SHARED_LIB_VAR="${libjvm_dir}:${libjava_dir}:${exec_prefix}/lib:PYTHONLIB:${SHARED_LIB_VAR}"
else
  CLASSPATH="../libJava:../../../lib/sidl-${version}.jar:../../../lib/sidlstubs/Java/sidlstub_${version}.jar:." 
  SIDL_DLL_PATH="../lib%(target)s/libImpl.scl%(extra)s;../../output/libC/libOutput.scl;../../../runtime/sidl/libsidl.scl;../../../runtime/sidlx/libsidlx.scl;../../../lib/sidlstubs/Java/libsidlstub_java.scl%(extrapy)s"
  SHARED_LIB_VAR="${libjvm_dir}:${libjava_dir}:../../../lib/sidlstubs/Java/.libs:PYTHONLIB:${SHARED_LIB_VAR}"
fi
export PYTHONPATH SIDL_DLL_PATH SHARED_LIB_VAR CLASSPATH

if $cygwin; then
  PATH=$PATH:PYTHONLIB:PYTHONLIB/../bin
  export PATH
fi

$PYTHON %(program)s
exit $?
""" % { 'target' : targetlang, 'program' : testprog, 'extra' : extralib,
        'extrapy' : extrapy }

def printPy2Py(out, targetlang, testprog):
    if "wraptest" == testprog:
        extrapp=':..'
        extrapy=';../libImpl.scl;../libImplPy.scl'
    else:
        extrapp=''
        extrapy=''
    print >>out, """\
PYTHONPATH=${PYTHONPATH}:../../libPython:../../../../runtime/python%(extrapp)s
SIDL_DLL_PATH="../../libPython/libImpl.scl;../../libPython/libImplPy.scl;../../../../runtime/sidl/libImpl.scl;../../../runtime/sidlx/libsidlx.scl;../../../output/libC/libOutput.scl%(extrapy)s"
SHARED_LIB_VAR="PYTHONLIB:${SHARED_LIB_VAR}"
export PYTHONPATH SIDL_DLL_PATH SHARED_LIB_VAR
 
if $cygwin; then
  PATH=$PATH:PYTHONLIB:PYTHONLIB/../bin
  export PATH
fi
                                                                                
$PYTHON %(program)s
exit $?
""" % { 'program' : testprog, 'extrapy' : extrapy, 'extrapp' : extrapp }


        
def generateRunPython(out, targetlang, testprog):
    printPythonHeader(out, targetlang)
    method = { 'C' : printNormalPython, 'Cxx' : printNormalPython,
               'F90' : printNormalPython, 'F77' : printNormalPython,
               'Python' : printPy2Py, 'F03' : printNormalPython,
               'Java' : generateRunPy2Java }
    method[targetlang](out, targetlang, testprog)


def generatePythonScripts(testprog, lang):
    for l in lang:
        if (l == 'Python'):
            if isfile('Py2Py/runPy2Py.in'):
                newfile = open('Py2Py/runPy2Py.in', 'w')
                generateRunPython(newfile, l, testprog)
        else:
            if isfile('runPy2' + l + '.in'):
                newfile = open('runPy2' + l + '.in', 'w')
                generateRunPython(newfile, l, testprog)
                newfile.close()

def removebackup(list):
    if (0 == len(list)):
        return list
    result = []
    for i in list:
        if not i.endswith("~"):
            result = result + [ i ]
    return result            

if __name__ == '__main__':
    for testdir in listdir('.'):
        if testdir <> '.svn' and isdir(testdir):
            chdir(testdir)
            langs = { }
            for impldir in glob('lib*'):
                if isdir(impldir):
                    langs[impldir[3:]] = 1
            if len(langs):
                for srcdir in glob('run*'):
                    if isdir(srcdir):
                      if srcdir <> "runSIDL":
                          chdir(srcdir)
                          if isfile('runAll.sh.in'):
                              newfile = open('runAll.sh.in', 'w')
                              generateRunAll(newfile, testdir, srcdir[3:], langs)
                              newfile.close()
                          else:
                              print "Skipping " + testdir + "/" + srcdir + "/runAll.sh.in"
                          if srcdir == "runPython":
                              progfiles = removebackup(glob('*test*'))
                              if len(progfiles) == 1:
                                  generatePythonScripts(progfiles[0], langs)
                          chdir('..')
            chdir('..')
