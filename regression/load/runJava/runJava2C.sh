#!/bin/bash
## File:        runJava2C.in
## Package:     Babel regression tests
## Revision:    $Revision: 7188 $
## Modified:    $Date: 2011-09-27 11:38:42 -0700 (Tue, 27 Sep 2011) $
## Description: script to run Java calling C regression test
##
## Copyright (c) 2000-2002, The Regents of the University of Calfornia.
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

prefix=/usr/local
exec_prefix=${prefix}
JAVA="java"

case "`uname`" in
  CYGWIN*) cygwin=true;;
  *) cygwin=false;;
esac

if test -f stamp-installcheck; then
  CLASSPATH="${prefix}/lib/sidl-0.9.7.jar:."
  SIDL_DLL_PATH="libClient.scl;../libC/libImpl.scl;${exec_prefix}/lib/libsidl.scl;../../output/libC/libOutput.scl"
  if $cygwin; then
    JAVA_LIBRARY_PATH="${exec_prefix}/bin"
  else
    JAVA_LIBRARY_PATH="${exec_prefix}/lib"
  fi
else
  CLASSPATH=../../../lib/sidl-0.9.7.jar:.
  SIDL_DLL_PATH="libClient.scl;../libC/libImpl.scl;../../../runtime/sidl/libsidl.scl;../../output/libC/libOutput.scl"
  JAVA_LIBRARY_PATH=../../../runtime/sidl/.libs
fi
sidl_LIBRARY_NAME=sidl

if $cygwin; then
  CLASSPATH=`cygpath --path --windows "${CLASSPATH}"`
  JAVA_LIBRARY_PATH=`cygpath --path --windows "${JAVA_LIBRARY_PATH}"`
  sidl_LIBRARY_NAME=cygsidl-`echo 0.9.7 | tr . -`
fi
export SIDL_DLL_PATH;
export CLASSPATH

${JAVA} \
  -Djava.library.path="${JAVA_LIBRARY_PATH}" \
  -Dsidl.library.name="${sidl_LIBRARY_NAME}" \
  ArgsTest
