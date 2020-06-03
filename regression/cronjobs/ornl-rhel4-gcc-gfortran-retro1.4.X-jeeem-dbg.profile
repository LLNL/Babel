#!/bin/sh
# 
# Profile: ornl-rhel4-gcc-gfortran-retro1.4.X-jeeem-dbg.profile
#

# setup the general Linux flags

export MAIL_SERVER=smtp.ornl.gov
export SH=bash
export PACKAGE=babel14x
export SNAPSHOT_NUMBER=`date '+%Y%m%d'`
export PATH=/usr/local/bin:/bin:/usr/bin:/etc:/usr/etc:/usr/local/lib:/sbin 
export LD_LIBRARY_PATH=/usr/local/lib
export JNI_INCLUDES='/usr/lib/jvm/java-1.5.0-bea-1.5.0.08/include'

host=`hostname`

export SVN=svn
export SVNROOT=svn+ssh://kohl@www.cca-forum.org/svn/babel/trunk
export URL=http://www.cca-forum.org/download/babel
export MAKE=make
export MAKE_FLAGS="-j2 -l 2.1"
export MAIL=mail
export PERL=perl
export ACLOCAL=aclocal
export AUTOMAKE=automake
export AUTOCONF=autoconf
export CONFIG_FLAGS="--enable-auto-disable"
export CD=cd
export MKDIR=mkdir
export MV=mv
export CHMOD=chmod
export CHGRP=chgrp
export TESTGID=babel
export FIND=find

# export PACKAGING_BUILDDIR=${HOME}/babel_scratch
export PACKAGING_BUILDDIR=/home/dumpster/proj/cca/dumpster_babel_testing/babel_scratch
mkdir -p ${PACKAGING_BUILDDIR}

# set the GCC version
export GCCVERSION=`gcc --version | head -n 1 | awk ' { print $3}'`

export CC=gcc
export CFLAGS='-g -Wall -Wtrigraphs'
export CXX=g++
export CXXFLAGS='-g -Wall'

export F77=/usr/bin/gfortran
export FFLAGS=-g

export FC=/usr/bin/gfortran

export PROFILE_NAME="ornl-rhel4-gcc${GCCVERSION}-gfortran-retro1.4.X-jeeem-dbg"

