#!/bin/sh
#
# This shell script sets up GNU F77 with optimization
# for the CASC Linux cluster.

export F77=g77
export FFLAGS='-O -g'
