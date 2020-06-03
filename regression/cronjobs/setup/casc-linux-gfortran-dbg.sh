#!/bin/sh
#
# Setup for GNU gfortran with debugging

export F77=gfortran
export F77FLAGS=-g
export FC=gfortran
export FCFLAGS=-g
if test "X$DISTCHECK_CONFIGURE_FLAGS" == "X" ; then
  export DISTCHECK_CONFIGURE_FLAGS="--with-F90-vendor=GNU"
else
  export DISTCHECK_CONFIGURE_FLAGS="$DISTCHECK_CONFIGURE_FLAGS --with-F90-vendor=GNU"
fi
if test "X$CONFIG_FLAGS" == "X" ; then
  export CONFIG_FLAGS="--with-F90-vendor=GNU"
else
  export CONFIG_FLAGS="$CONFIG_FLAGS --with-F90-vendor=GNU"
fi
