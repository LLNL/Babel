#!/bin/sh
#
# set the GCC version
#
export GCCVERSION=`gcc --version | head -n 1 | awk ' { print $3}'`
