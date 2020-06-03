#!/bin/sh
#
# This shell script sets up PGI compilers with debugging
# for the CASC Linux cluster.

. /usr/apps/pgi/latest/setup.sh
export CC='pgcc'
export CFLAGS='-g -O2'
export CXX='pgCC'
export CXXFLAGS='-g -O2'
export F77='pgf77'
export FFLAGS='-g -O2'
export CPP='gcc -E'
export FC='pgf95'
export F90=${FC}
export FCLAGS='-g -O2'
export PATH=/usr/casc/babel/apps/linux_pgi/bin:${PATH}
export LD_LIBRARY_PATH=/usr/casc/babel/apps/linux_pgi/lib:${LD_LIBRARY_PATH}
if test "X$DISTCHECK_CONFIGURE_FLAGS" == "X" ; then
  export DISTCHECK_CONFIGURE_FLAGS="--with-F90-vendor=PGI"
else
  export DISTCHECK_CONFIGURE_FLAGS="$DISTCHECK_CONFIGURE_FLAGS --with-F90-vendor=PGI"
fi
if test "X$CONFIG_FLAGS" == "X" ; then
  export CONFIG_FLAGS="--with-F90-vendor=PGI"
else
  export CONFIG_FLAGS="$CONFIG_FLAGS --with-F90-vendor=PGI"
fi
