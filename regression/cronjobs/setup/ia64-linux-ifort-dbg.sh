#!/bin/sh
#
# This shell script sets up Intel Fortran 90 with debugging
# for Thunder
. /usr/local/intel/compiler91_new/bin/ifortvars.sh
export FC='ifort -Vaxlib'
export FCFLAGS=-g
if test "X$DISTCHECK_CONFIGURE_FLAGS" == "X" ; then
  export DISTCHECK_CONFIGURE_FLAGS="--with-F90-vendor=Intel --with-arch=ia64"
else
  export DISTCHECK_CONFIGURE_FLAGS="$DISTCHECK_CONFIGURE_FLAGS --with-F90-vendor=Intel --with-arch=ia64"
fi
if test "X$CONFIG_FLAGS" == "X" ; then
  export CONFIG_FLAGS="--with-F90-vendor=Intel --with-arch=ia64"
else
  export CONFIG_FLAGS="$CONFIG_FLAGS --with-F90-vendor=Intel --with-arch=ia64"
fi

