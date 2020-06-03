#!/bin/sh
#
# This shell script sets up Intel C/C++ with debugging
# for the Thunder

. /usr/local/intel/compiler91_new/bin/iccvars.sh
export PATH=/usr/apps/babel/icc/bin:${PATH}
export LD_LIBRARY_PATH=/usr/apps/babel/icc/lib:${LD_LIBRARY_PATH}
export CC='icc -c99'
export CFLAGS='-g'
export CXX='icpc'
export CXXFLAGS='-g'
export CPP='gcc -E'
