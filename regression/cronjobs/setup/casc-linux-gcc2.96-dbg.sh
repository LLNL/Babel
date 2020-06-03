#!/bin/sh
#
# This shell script sets up GCC 2.96 with debugging
# for the CASC Linux cluster.

. setup/gccversion.sh
export CC=gcc
export CFLAGS='-g -Wall -Wtrigraphs'
export CXX=g++
export CXXFLAGS='-g -Wall'
