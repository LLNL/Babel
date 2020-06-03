#!/bin/sh
# 
# Profile: lc-pgi-sunj-opt
#
. setup/lc-linux-general.sh
export JAVAPREFIX=/usr/lib/jvm/java-1.6.0-sun.x86_64
export PYTHON=/usr/apps/python2.6/bin/python
export PYTHONPATH=/usr/apps/python2.6/lib
#VERSION=11.5
export CC=babel_pgcc-$VERSION #this strips -Wc, so non-libtool builds still work
export CXX=babel_pgCC-$VERSION
export FC=babel_pgf95-$VERSION
export F77=babel_pgf95-$VERSION
VERSION=10.9
export CC=pgcc-$VERSION
export CXX=pgCC-$VERSION
export FC=pgf95-$VERSION
export F77=pgf95-$VERSION
FLAGS='-O3 -fPIC -g -Melf -Mdwarf3 -traceback'
export CPP='gcc -E'
export CFLAGS="$FLAGS"
export CXXFLAGS="$FLAGS"
export FFLAGS="$FLAGS"
export FCFLAGS="$FLAGS"
export LD=ld
export LDFLAGS= #"-Wc,-nomp -Wl,-lgcc_eh"
export CONFIG_FLAGS="--with-F90-vendor=PGI"
. setup/casc-linux-final.sh
export PROFILE_NAME="lc-pgi-sunj-opt"
