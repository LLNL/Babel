#!/bin/sh
#
# This shell script sets up Lahey Fortran 90 with debugging
# for the CASC Linux cluster.

. /usr/apps/Lahey/lf9562/setup.sh
export FC=lf95
export FCFLAGS=-g
if test "X$DISTCHECK_CONFIGURE_FLAGS" == "X" ; then
  export DISTCHECK_CONFIGURE_FLAGS="--with-F90-vendor=Lahey"
else
  export DISTCHECK_CONFIGURE_FLAGS="$DISTCHECK_CONFIGURE_FLAGS --with-F90-vendor=Lahey"
fi
if test "X$CONFIG_FLAGS" == "X" ; then
  export CONFIG_FLAGS="--with-F90-vendor=Lahey"
else
  export CONFIG_FLAGS="$CONFIG_FLAGS --with-F90-vendor=Lahey"
fi
