export PROFILE_NAME='casc-cygwin'

export SH=bash

export PACKAGE=babel

export SNAPSHOT_NUMBER=`date '+%Y%m%d'`

export PATH=/home/skohn/java/jdk1.3.0_02/bin:/usr/local/bin:/usr/bin:/bin

#export LD_LIBRARY_PATH=/usr/local/lib:/usr/apps/lib

#export CLASSPATH=

export SVN=svn

if test `whoami` == "epperly2"; then
 SVNUSER=epperly@
else
 SVNUSER=
fi
export SVNROOT=svn+ssh://${SVNUSER}www.cca-forum.org/svn/babel/trunk

export MAKE=make
export MAKE_FLAGS="-j2 -l 2.1"

export MAIL=mail

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

export MV=mv

export CHMOD=chmod

export CHGRP=chgrp
export TESTGID=babel

export FIND=find

export PACKAGING_BUILDDIR=/usr/casc_scratch/babel
