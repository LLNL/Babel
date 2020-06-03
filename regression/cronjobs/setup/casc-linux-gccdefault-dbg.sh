#!/bin/sh
#
# This shell script sets up default GCC with debugging
# for the CASC Linux cluster.

. /usr/apps/gcc/default/setup.sh
. setup/gccversion.sh
export CC=gcc
export CFLAGS='-g -Wall -Wtrigraphs -std=iso9899:199409'
export CXX=g++
export CXXFLAGS='-g -Wall'
