#!/bin/sh
#
# This shell script sets up Absoft Fortran 90 with debugging
# for the CASC Linux cluster.

. /usr/apps/Absoft/pro8.0/setup.sh
export FC=f90
export FCFLAGS=-g
if test "X$DISTCHECK_CONFIGURE_FLAGS" == "X" ; then
  export DISTCHECK_CONFIGURE_FLAGS="--with-F90-vendor=Absoft"
else
  export DISTCHECK_CONFIGURE_FLAGS="$DISTCHECK_CONFIGURE_FLAGS --with-F90-vendor=Absoft"
fi
if test "X$CONFIG_FLAGS" == "X" ; then
  export CONFIG_FLAGS="--with-F90-vendor=Absoft"
else
  export CONFIG_FLAGS="$CONFIG_FLAGS --with-F90-vendor=Absoft"
fi
