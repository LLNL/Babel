#!/bin/sh
#
# This shell script sets up GCC new (using the symbolic link to the
# latest available version of GCC) with optimization for the CASC Linux
# cluster. 

# . /usr/apps/gcc/latest/setup.sh
. setup/gccversion.sh
export CC=gcc
export CFLAGS='-O -g -Wall -Wtrigraphs'
export CXX=g++
export CXXFLAGS='-O -g -Wall'
