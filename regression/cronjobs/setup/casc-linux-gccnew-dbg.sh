#!/bin/sh
#
# This shell script sets up GCC new (using the symbolic link to the
# latest available version of GCC) with debugging for the CASC Linux
# cluster. 

#. /usr/apps/gcc/latest/setup.sh
. setup/gccversion.sh
export CC=gcc
export CFLAGS='-g -Wall -Wtrigraphs'
export CXX=g++
export CXXFLAGS='-g -Wall'
if test "X$CONFIG_FLAGS" == "X" ; then
  export CONFIG_FLAGS="--with-bindc "
else
  export CONFIG_FLAGS="--with-bindc $CONFIG_FLAGS"
fi
