#!/bin/sh
#
# This shell script sets up default GCC with optimization
# for the CASC Linux cluster.

. /usr/apps/gcc/default/setup.sh
. setup/gccversion.sh
export CC=gcc
export CFLAGS='-O -g -Wall -Wtrigraphs'
export CXX=g++
export CXXFLAGS='-O -g -Wall'
