#!/bin/bash
## File:        runAll.sh
## Package:     Babel regression driver
## Revision:    $Revision: 7188 $
## Modified:    $Date: 2011-09-27 11:38:42 -0700 (Tue, 27 Sep 2011) $
## Description: regression driver that dynamically loads implementations
##
## Copyright (c) 2000-2002, The Regents of the University of Calfornia.
## Produced at the Lawrence Livermore National Laboratory.
## Written by the Components Team <components@llnl.gov>
## UCRL-CODE-2002-054
## All rights reserved.
##
## This file is part of Babel. For more information, see
## http:##www.llnl.gov/CASC/components/. Please read the COPYRIGHT file
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

#
# If running the regression tests against the installed version under CYGWIN,
# we need to add the DLL location to the default path.
#

prefix=/home/kumfert/research/babel/=inst
exec_prefix=${prefix}
python_version=2.3
libjvm_dir=/usr/java/j2sdk1.4.2_01/jre/lib/i386/client:
libjava_dir=/usr/java/j2sdk1.4.2_01/jre/lib/i386:

if test -f stamp-installcheck; then
  case "`uname`" in
    CYGWIN*)
      PATH="$PATH:${exec_prefix}/bin"; export PATH
    ;;
  esac
fi

#
# Run the tests against the various back-end implementations.
#

echo "TEST_BEGIN F90->C"
echo "COMMENT: Args from C..."
SIDL_DLL_PATH="../libC/libImpl.scl" ; export SIDL_DLL_PATH
./runAll C  || echo "TEST_RESULT BROKEN $?" 

echo "TEST_BEGIN F90->C++"
echo "COMMENT: Args from C++..."
SIDL_DLL_PATH="../libCxx/libImpl.scl" ; export SIDL_DLL_PATH
./runAll C++ || echo "TEST_RESULT BROKEN $?" 

if test "X" = "X"; then
  echo "TEST_BEGIN F90->F77"
  echo "COMMENT: Args from Fortran77..."
  SIDL_DLL_PATH="../libF77/libImpl.scl" ; export SIDL_DLL_PATH
  ./runAll F77 || echo "TEST_RESULT BROKEN $?" 
fi

echo "TEST_BEGIN F90->F90"
echo "COMMENT: Args from Fortran90..."
SIDL_DLL_PATH="../libF90/libImpl.scl" ; export SIDL_DLL_PATH
./runAll F90 || echo "TEST_RESULT BROKEN $?" 

if test "X" = "X"; then
  echo "TEST_BEGIN F90->Python"
  echo "COMMENT: Args from Python..."

  SIDL_DLL_PATH="../libPython/libImpl.scl;../libPython/libImplPy.scl"
  export SIDL_DLL_PATH
  if test "X$LD_LIBRARY_PATH" = "X"; then
    LD_LIBRARY_PATH="../../../runtime/sidl/.libs:$exec_prefix/lib"
  else
    LD_LIBRARY_PATH="../../../runtime/sidl/.libs:$exec_prefix/lib:${LD_LIBRARY_PATH}"
  fi
  export LD_LIBRARY_PATH

  pylib=`$PYTHON -c "import sys; print sys.__dict__.get('lib','lib')"`
  if test "X$PYTHONPATH" = "X"; then
    PYTHONPATH="../libPython:../../../runtime/python:$exec_prefix/$pylib/python$python_version/site-packages"
  else
    PYTHONPATH="../libPython:../../../runtime/python:$exec_prefix/$pylib/python$python_version/site-packages:$PYTHONPATH"
  fi
  export PYTHONPATH

  ./runAll Python || echo "TEST_RESULT BROKEN $?"
fi

if test "X" = "X"; then
  echo "TEST_BEGIN F90->Java"
  echo "COMMENT: Args from Java..."
  if test "X$LD_LIBRARY_PATH" = "X"; then
    LD_LIBRARY_PATH="${libjvm_dir}:${libjava_dir}:../../../runtime/sidl/.libs"
  else
    LD_LIBRARY_PATH="${libjvm_dir}:${libjava_dir}:../../../runtime/sidl/.libs:${LD_LIBRARY_PATH}"
  fi
  export LD_LIBRARY_PATH
  SIDL_DLL_PATH="../libJava/libImpl.scl" ; export SIDL_DLL_PATH
  CLASSPATH="../libJava:../../../runtime/java:." ; export CLASSPATH
  ./runAll Java || echo "TEST_RESULT BROKEN $?"
fi
