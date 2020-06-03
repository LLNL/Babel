#!/bin/sh
# 
# Profile: lc-intel-sunj-dbg
#
. setup/lc-linux-general.sh
export JAVAPREFIX=/usr/lib/jvm/java-1.6.0-sun.x86_64
export PYTHON=/usr/apps/python2.6/bin/python
export PYTHONPATH=/usr/apps/python2.6/lib
VERSION=12.1.273
export CPP='gcc -E'
export CC=icc-$VERSION
export CXX=icpc-$VERSION
export FC=ifort-$VERSION
export F77=ifort-$VERSION
FLAGS='-O0 -g'
export CFLAGS="$FLAGS"
export CXXFLAGS="$FLAGS"
export FFLAGS="$FLAGS"
export FCFLAGS="$FLAGS"
export LD=ld
export LDFLAGS=
. setup/casc-linux-final.sh
export PROFILE_NAME="lc-intel-sunj-dbg"
