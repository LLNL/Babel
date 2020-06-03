#!/bin/sh
# 
# Profile: lc-gcc-ibmj-dbg
#
. setup/lc-linux-general.sh
export JAVAPREFIX=/usr/lib/jvm/java-1.5.0-ibm.x86_64
export PYTHON=/usr/apps/python2.6/bin/python
export PYTHONPATH=/usr/apps/python2.6/lib
VERSION=4.4.4
export CPP='gcc -E'
export CC=gcc-$VERSION
export CXX=g++-$VERSION
export FC=gfortran-$VERSION
export F77=gfortran-$VERSION
FLAGS='-O0 -g -pipe'
export CFLAGS="$FLAGS"
export CXXFLAGS="$FLAGS"
export FFLAGS="$FLAGS"
export FCFLAGS="$FLAGS"
export LD=ld
export LDFLAGS=
. setup/casc-linux-final.sh
export PROFILE_NAME="lc-gcc-ibmj-dbg"
