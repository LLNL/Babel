#!/bin/sh
#
# This shell script sets up Intel Fortran 90 with optimization
# for the CASC Linux cluster.

if test `uname -m` == 'x86_64' ; then
  arch=intel64
else
  arch=ia32
fi

if test -f /usr/apps/intel/latest/bin/ifortvars.sh ; then
  . /usr/apps/intel/latest/bin/ifortvars.sh $arch
else
 . /usr/apps/intel/fc/9.1.040/setup.sh
fi

export F77='ifort -Vaxlib'
export F77FLAGS='-O'
export FC='ifort -Vaxlib'
if test "X$DISTCHECK_CONFIGURE_FLAGS" == "X" ; then
  export DISTCHECK_CONFIGURE_FLAGS="--with-F90-vendor=Intel --with-bindc "
else
  export DISTCHECK_CONFIGURE_FLAGS="$DISTCHECK_CONFIGURE_FLAGS --with-F90-vendor=Intel  --with-bindc "
fi
if test "X$CONFIG_FLAGS" == "X" ; then
  export CONFIG_FLAGS="--with-F90-vendor=Intel --with-bindc "
else
  export CONFIG_FLAGS="$CONFIG_FLAGS --with-F90-vendor=Intel --with-bindc "
fi
export FCFLAGS=-O
