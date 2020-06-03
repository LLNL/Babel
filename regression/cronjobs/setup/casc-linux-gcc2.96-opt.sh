#!/bin/sh
#
# This shell script sets up GCC 2.96 with debugging
# for the CASC Linux cluster.

. setup/gccversion.sh
export CC=gcc
export CFLAGS='-O -g -Wall -Wtrigraphs'
export CXX=g++
export CXXFLAGS='-O -g -Wall'
