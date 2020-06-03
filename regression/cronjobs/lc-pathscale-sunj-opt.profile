#!/bin/sh
# 
# Profile: lc-pathscale-sunj-opt
#
. setup/lc-linux-general.sh
export JAVAPREFIX=/usr/lib/jvm/java-1.6.0-sun.x86_64
export PYTHON=/usr/apps/python2.6/bin/python
export PYTHONPATH=/usr/apps/python2.6/lib
VERSION=3.2.99
export CC=pathcc-$VERSION
export CXX=pathCC-$VERSION
export FC=pathf90-$VERSION
export F77=pathf90-$VERSION
FLAGS='-O3 -g -fPIC'
export CPP='gcc -E'
export CFLAGS="$FLAGS"
export CXXFLAGS="$FLAGS"
export FFLAGS="$FLAGS"
export FCFLAGS="$FLAGS"
export LD=ld
export LDFLAGS=""
export CONFIG_FLAGS="--with-F90-vendor=PathScale"
. setup/casc-linux-final.sh
export PROFILE_NAME="lc-pathscale-sunj-opt"
