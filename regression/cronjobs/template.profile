export SH=bash

export PACKAGE=babel

export SNAPSHOT_NUMBER=1.0

export PATH=/bin:/usr/bin:/usr/local/bin

export LD_LIBRARY_PATH=/lib:/usr/lib:/usr/local/lib

export CLASSPATH=

export CVS=svn

if test `whoami` == "epperly2"; then
 SVNUSER=epperly@
else
 SVNUSER=
fi
export SVNROOT=svn+ssh://${SVNUSER}www.cca-forum.org/svn/babel/trunk

export MAKE=make

export MAKE_FLAGS=""
# export MAKE_FLAGS="-j2 -l 2.1"

export MAIL=Mail

export PERL=perl

export ACLOCAL=aclocal

export AUTOMAKE=automake

export AUTOCONF=autoconf

export CD=cd

export MKDIR=mkdir

export CC=gcc

export CFLAGS=-g

export CXX=g++

export CXXFLAGS=-g

export F77=g77

export FFLAGS=-g

export CHMOD=chmod

export CHGRP=chgrp

# a group ID that should be the group id for all files and directories for the test
export TESTGID=babel

export FIND=find

export PACKAGING_BUILDDIR=/usr/casc/babel/scratch
